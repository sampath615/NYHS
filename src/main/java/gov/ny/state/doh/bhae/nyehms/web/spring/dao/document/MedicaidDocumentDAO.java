package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.decimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.integer;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.timestamp;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocArvsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocProvidersVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdMedicaidDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawMedicaidDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

/**
 * The Medicaid (and insurance death) documents: the screen's document, antiretrovirals and providers, the import's
 * standardized record and providers, and the document copy (formerly a MyBatis mapper with the same name and methods).
 * <p>
 * JPQL over RawMedicaidDoc, StdMedicaidDoc, RawMedicaidDocArvs, RawMedicaidDocProviders, StdMedicaidDocProviders,
 * CodedCosCodeReference and PersonDocAssociation; the records are inserted as entities (their write transformers keep
 * the former UPPER/TRIM in the database), the copies of providers and antiretrovirals are HQL insert ... select, the copied provider an HQL insert with values.
 * <p>
 * Native (the former SQL, the Oracle {@code (+)} join written as the equal LEFT JOIN, run with JDBC on the persistence
 * context's connection): {@link
 * #getRawMedicaidDocAndProvidersData} and {@link #getStdMedicaidDocAndProvidersData} read whole rows ({@code select *}
 * of a document and its providers: the columns of these tables are not all mapped, and the generic document copy uses
 * every column it gets). <b>Kept as before:</b> both read one row, so a document with more than one provider fails
 * (more than one row), and a copy writes the first provider only.
 *
 * @author Srikar Kummamuri
 */
@Repository
@RequiredArgsConstructor
public class MedicaidDocumentDAO {

	private final JpqlStatements jpql;

	@PersistenceContext
	private EntityManager entityManager;

	/** The document as the screen reads it (the former columns and aliases). */
	private static final String DOCUMENT = """
			select std.documentId as DOCUMENT_ID, std.dobText as DOB_TEXT, std.ssn as SSN, std.fname as FNAME, std.lname as LNAME,
			std.mname as MNAME, std.currentGender as CURRENT_GENDER, std.raceIndian as RACE_INDIAN, std.raceBlack as RACE_BLACK,
			std.raceAsian as RACE_ASIAN, std.racePacific as RACE_PACIFIC, std.raceWhite as RACE_WHITE, std.raceMultiple as RACE_MULTIPLE,
			std.raceOther as RACE_OTHER, std.raceUnknown as RACE_UNKNOWN, std.ethnicity as ETHNICITY, std.personAddress as PERSON_ADDRESS,
			std.personCity as PERSON_CITY, std.personState as PERSON_STATE, std.personZip as PERSON_ZIP, std.personCounty as PERSON_COUNTY,
			std.criteria1 as CRITERIA_1, std.criteria2 as CRITERIA_2, std.criteria3 as CRITERIA_3, std.criteria4 as CRITERIA_4,
			std.criteria5 as CRITERIA_5, std.criteria6 as CRITERIA_6, std.criteria7 as CRITERIA_7, std.criteria8 as CRITERIA_8,
			std.criteria9 as CRITERIA_9, std.criteria10 as CRITERIA_10, std.medicaidNumber as MEDICAID_NUMBER, std.vitalStatus as VITAL_STATUS,
			std.nycShipDate as NYC_SHIP_DATE, std.eharsImportDate as EHARS_IMPORT_DATE, std.matchedProcessDate as MATCHED_PROCESS_DATE,
			p.stateno as STATENO, p.discardStatus as DISCARD_STATUS, std.dodText as DOD_TEXT, std.createDate as CREATE_DATE,
			raw1.mailAddrStr as raw_address, raw1.cin as CIN
			from RawMedicaidDoc raw1
			join StdMedicaidDoc std on std.documentId = raw1.documentId and raw1.documentId = ?1
			left join PersonDocAssociation p on raw1.documentId = p.documentId and p.dissassociationDate is null""";

	/**
	 * The document (received and standardized record, current person match), mapped to the VO by column name as MyBatis
	 * did.
	 *
	 * @return the VO, or null if there is no such document; more than one current match fails, as before
	 */
	public MedicaidDocVO getMedicaidDoc(int docId) throws DataAccessException {
		return jpql.one(DOCUMENT, Rows.bean(MedicaidDocVO.class), docId);
	}

	/** The antiretrovirals of a received record. */
	public List<MedicaidDocArvsVO> getArvsForMedicaidDoc(int docId) throws DataAccessException {
		return jpql.list("""
				select a.cin as CIN, a.prescribedArvs as PRESCRIBED_ARVS, a.arvMos as ARV_MOS, a.arvYos as ARV_YOS, a.planType as PLAN_TYPE,
				a.medicaidFile as MEDICAID_FILE
				from RawMedicaidDocArvs a where a.medicaidDocId = ?1""", Rows.bean(MedicaidDocArvsVO.class), docId);
	}

	/** The providers of a standardized record with their category of service, the primary one first, then the latest. */
	public List<MedicaidDocProvidersVO> getProvidersListForMedicaidDoc(int docId) throws DataAccessException {
		return jpql.list("""
				select p.medicaidNumber as MEDICAID_NUMBER, p.provName as PROV_NAME, p.npi as NPI, p.provAddress as PROV_ADDRESS,
				p.provCity as PROV_CITY, p.provState as PROV_STATE, p.provZip as PROV_ZIP, p.provCounty as PROV_COUNTY,
				p.serviceDate as SERVICE_DATE, c.provCosCodeDesc as PROV_COS_CODE_DESC, p.primaryInd as PRIMARY_IND
				from StdMedicaidDocProviders p
				left join CodedCosCodeReference c on p.categoryOfServiceCd = c.provCosCode
				where p.medicaidDocId = ?1
				order by case when p.primaryInd = 'Y' then 0 else 1 end, p.serviceDate desc""", Rows.bean(MedicaidDocProvidersVO.class), docId);
	}

	/**
	 * Inserts the standardized record of a document (the import's or the copy's map, value per column name).
	 *
	 * @return 1
	 */
	@Transactional
	public int writeStdDocData(Map<Object, Object> stdDocData) {
		Map<Object, Object> m = stdDocData;
		return jpql.insert(StdMedicaidDoc.builder()
				.raceIndian(text(m.get("RACE_INDIAN")))
				.raceBlack(text(m.get("RACE_BLACK")))
				.raceAsian(text(m.get("RACE_ASIAN")))
				.racePacific(text(m.get("RACE_PACIFIC")))
				.raceWhite(text(m.get("RACE_WHITE")))
				.raceMultiple(text(m.get("RACE_MULTIPLE")))
				.raceOther(text(m.get("RACE_OTHER")))
				.raceUnknown(text(m.get("RACE_UNKNOWN")))
				.matchCd(text(m.get("MATCH_CD")))
				.createDate(date(m.get("CREATE_DATE")))
				.reviewDate(timestamp(m.get("REVIEW_DATE")))
				.eharsImportDate(timestamp(m.get("EHARS_IMPORT_DATE")))
				.nycShipDate(timestamp(m.get("NYC_SHIP_DATE")))
				.documentId(integer(m.get("DOCUMENT_ID")))
				.currentProcessStatus(decimal(m.get("CURRENT_PROCESS_STATUS")))
				.qsParentDocId(decimal(m.get("QS_PARENT_DOC_ID")))
				.matchWeight(decimal(m.get("MATCH_WEIGHT")))
				.mname(text(m.get("MNAME")))
				.criteria1(text(m.get("CRITERIA_1")))
				.criteria2(text(m.get("CRITERIA_2")))
				.criteria3(text(m.get("CRITERIA_3")))
				.criteria4(text(m.get("CRITERIA_4")))
				.criteria5(text(m.get("CRITERIA_5")))
				.criteria6(text(m.get("CRITERIA_6")))
				.criteria7(text(m.get("CRITERIA_7")))
				.criteria8(text(m.get("CRITERIA_8")))
				.criteria9(text(m.get("CRITERIA_9")))
				.criteria10(text(m.get("CRITERIA_10")))
				.eharsStatusCd(text(m.get("EHARS_STATUS_CD")))
				.dobText(text(m.get("DOB_TEXT")))
				.currentGender(text(m.get("CURRENT_GENDER")))
				.ethnicity(text(m.get("ETHNICITY")))
				.personLatitude(text(m.get("PERSON_LATITUDE")))
				.dodText(text(m.get("DOD_TEXT")))
				.createUid(text(m.get("CREATE_UID")))
				.stateno(text(m.get("STATENO")))
				.medicaidNumber(text(m.get("MEDICAID_NUMBER")))
				.personLongitude(text(m.get("PERSON_LONGITUDE")))
				.personState(text(m.get("PERSON_STATE")))
				.personCounty(text(m.get("PERSON_COUNTY")))
				.personCity(text(m.get("PERSON_CITY")))
				.matchPass(text(m.get("MATCH_PASS")))
				.fname(text(m.get("FNAME")))
				.lname(text(m.get("LNAME")))
				.personZip(text(m.get("PERSON_ZIP")))
				.personCensusTract(text(m.get("PERSON_CENSUS_TRACT")))
				.personAddress(text(m.get("PERSON_ADDRESS")))
				.vitalStatus(text(m.get("VITAL_STATUS")))
				.reviewUid(text(m.get("REVIEW_UID")))
				.ssn(text(m.get("SSN")))
				.matchedProcessDate(date(m.get("MATCHED_PROCESS_DATE")))
				.medicaidFile(text(m.get("MEDICAID_FILE")))
				.fluxMatchCd(text(m.get("FLUX_MATCH_CD")))
				.fluxPotStateno(text(m.get("FLUX_POT_STATENO")))
				.fluxMatchedStateno(text(m.get("FLUX_MATCHED_STATENO")))
				.fluxQsParentDocId(decimal(m.get("FLUX_QS_PARENT_DOC_ID")))
				.fluxMatchedProcessDate(date(m.get("FLUX_MATCHED_PROCESS_DATE")))
				.build());
	}

	/**
	 * Copies the received providers of a document to its standardized providers (address lines joined with a space,
	 * the ZIP code cut to 5 characters, PRIMARY_IND 'Y').
	 *
	 * @return the number of rows inserted
	 */
	@Transactional
	public int storeRawMedicaidProvidersDataInStd(int intValue) {
		// SUBSTR(POSTAL_CD, 0, 5) of the former SQL: Oracle reads position 0 as 1
		return jpql.update("""
				insert into StdMedicaidDocProviders (medicaidDocId, medicaidNumber, provName, npi, provAddress, provCity, provZip, serviceDate,
				categoryOfServiceCd, createUid, createDate, primaryInd)
				select p.medicaidDocId, p.cin, p.provName, p.npi, p.provAddrStr || ' ' || p.provAddrLine2, p.provAddrCity, substring(p.postalCd, 1, 5),
				p.srvDt, p.cosCd, p.createUid, p.createDate, 'Y'
				from RawMedicaidDocProviders p where p.medicaidDocId = ?1""", intValue);
	}

	/**
	 * Inserts a received record (the document copy); PROCESS_DATE the database's SYSDATE.
	 *
	 * @return 1
	 */
	@Transactional
	public int insertRawMedDocument(Map<Object, Object> rawDocData) {
		Map<Object, Object> m = rawDocData;
		return jpql.insert(RawMedicaidDoc.builder()
				.createDate(date(m.get("CREATE_DATE")))
				.documentId(integer(m.get("DOCUMENT_ID")))
				.currentProcessStatus(decimal(m.get("CURRENT_PROCESS_STATUS")))
				.mi(text(m.get("MI")))
				.criteria1(text(m.get("CRITERIA_1")))
				.criteria2(text(m.get("CRITERIA_2")))
				.criteria3(text(m.get("CRITERIA_3")))
				.criteria4(text(m.get("CRITERIA_4")))
				.criteria5(text(m.get("CRITERIA_5")))
				.criteria6(text(m.get("CRITERIA_6")))
				.criteria7(text(m.get("CRITERIA_7")))
				.criteria8(text(m.get("CRITERIA_8")))
				.criteria9(text(m.get("CRITERIA_9")))
				.criteria10(text(m.get("CRITERIA_10")))
				.birthDt(text(m.get("BIRTH_DT")))
				.sexCd(text(m.get("SEX_CD")))
				.raceCd(text(m.get("RACE_CD")))
				.ethnicCd(text(m.get("ETHNIC_CD")))
				.deathDate(text(m.get("DEATH_DATE")))
				.createUid(text(m.get("CREATE_UID")))
				.cin(text(m.get("CIN")))
				.postalCd(text(m.get("POSTAL_CD")))
				.mailAddrCity(text(m.get("MAIL_ADDR_CITY")))
				.mailAddrStr(text(m.get("MAIL_ADDR_STR")))
				.firstName(text(m.get("FIRST_NAME")))
				.lastName(text(m.get("LAST_NAME")))
				.medicaidFile(text(m.get("MEDICAID_FILE")))
				.vitalStatus(text(m.get("VITAL_STATUS")))
				.ssn(text(m.get("SSN")))
				.build());
	}

	/**
	 * Inserts a received provider (the document copy: the first row of the copied document and its providers).
	 * <p>
	 * An HQL insert with values, not an entity: the row's SKEY is that of the copied provider and is null when the
	 * copied document had no provider, and that row is written as before.
	 *
	 * @return 1
	 */
	@Transactional
	public int insertRawMedProvidersData(Map<Object, Object> rawDocData) {
		Map<Object, Object> m = rawDocData;
		return jpql.update("""
				insert into RawMedicaidDocProviders (createUid, srvDt, createDate, skey, medicaidDocId, cosCd, cin, npi, postalCd, provAddrCity,
				provAddrStr, provAddrLine2, provName)
				values (trim(upper(?1)), ?2, ?3, ?4, ?5, trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)),
				trim(upper(?11)), trim(upper(?12)), trim(upper(?13)))""",
				text(m.get("CREATE_UID")), date(m.get("SRV_DT")),
				date(m.get("CREATE_DATE")), longValue(m.get("SKEY")), integer(m.get("MEDICAID_DOC_ID")),
				text(m.get("COS_CD")), text(m.get("CIN")), text(m.get("NPI")), text(m.get("POSTAL_CD")), text(m.get("PROV_ADDR_CITY")),
				text(m.get("PROV_ADDR_STR")), text(m.get("PROV_ADDR_LINE_2")), text(m.get("PROV_NAME")));
	}

	/**
	 * A received record with its provider, all columns of both tables (the first of a repeated column name), as a map
	 * keyed by column name (a key only when its value is set).
	 *
	 * @return the map, or null if there is no such document; more than one provider fails, as before
	 */
	@Transactional(readOnly = true)
	public Map<Object, Object> getRawMedicaidDocAndProvidersData(int documentId) {
		return wholeRow("select * from RAW_MEDICAID_DOC med left join RAW_MEDICAID_DOC_PROVIDERS prov on med.DOCUMENT_ID = prov.MEDICAID_DOC_ID"
				+ " where med.DOCUMENT_ID = ?", documentId);
	}

	/**
	 * A standardized record with its provider, all columns of both tables (the first of a repeated column name), as a
	 * map keyed by column name (a key only when its value is set).
	 *
	 * @return the map, or null if there is no such document; more than one provider fails, as before
	 */
	@Transactional(readOnly = true)
	public Map<Object, Object> getStdMedicaidDocAndProvidersData(int documentId) {
		return wholeRow("select * from STD_MEDICAID_DOC med left join STD_MEDICAID_DOC_PROVIDERS prov on med.DOCUMENT_ID = prov.MEDICAID_DOC_ID"
				+ " where med.DOCUMENT_ID = ?", documentId);
	}

	/**
	 * The row of a {@code select *} over a document and its providers, as a map (Rows.map rules: the first of a repeated
	 * column name, null values left out, a row of nulls null), every column read with getObject (a CLOB as its text).
	 * <p>
	 * Native, on the persistence context's connection (in the caller's transaction): whole rows of two tables whose
	 * columns are not all mapped (the generic document copy uses every column it gets), and the two tables share column
	 * names (CREATE_DATE, CREATE_UID, CIN, ...), which Hibernate's native queries refuse.
	 *
	 * @throws IncorrectResultSizeDataAccessException for more than one row (a document with several providers), as before
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private Map<Object, Object> wholeRow(String sql, int documentId) {
		List<Map<String, Object>> rows = entityManager.unwrap(Session.class).doReturningWork(connection -> {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, documentId);
				try (ResultSet resultSet = statement.executeQuery()) {
					ResultSetMetaData metaData = resultSet.getMetaData();
					List<String> labels = new ArrayList<>();
					for (int column = 1; column <= metaData.getColumnCount(); column++) {
						labels.add(metaData.getColumnLabel(column));
					}
					List<Map<String, Object>> results = new ArrayList<>();
					while (resultSet.next()) {
						List<Object> values = new ArrayList<>();
						for (int column = 1; column <= labels.size(); column++) {
							Object value = resultSet.getObject(column);
							values.add(value instanceof Clob clob ? clob.getSubString(1, (int) clob.length()) : value);
						}
						results.add(Rows.map().map(new Row(labels, values)));
					}
					return results;
				}
			}
		});
		if (rows.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, rows.size());
		}
		return rows.isEmpty() ? null : (Map) rows.get(0);
	}

	/**
	 * Copies the antiretrovirals of a received record to a new document (the copy); CREATE_DATE the database's SYSDATE.
	 *
	 * @param rawDocData oldDocumentId, newDocumentId, createId (the worker id, written as text)
	 */
	@Transactional
	public void insertRawMedicaidArvsData(Map<Object, Object> rawDocData) {
		jpql.update("""
				insert into RawMedicaidDocArvs (skey, medicaidDocId, cin, prescribedArvs, arvMos, arvYos, planType, medicaidFile, createUid, createDate)
				select a.skey, cast(?1 as Integer), a.cin, a.prescribedArvs, a.arvMos, a.arvYos, a.planType, a.medicaidFile, cast(?2 as String), sysdate
				from RawMedicaidDocArvs a where a.medicaidDocId = ?3""",
				integer(rawDocData.get("newDocumentId")), text(rawDocData.get("createId")), integer(rawDocData.get("oldDocumentId")));
	}
}
