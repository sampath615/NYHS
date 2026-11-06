package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.decimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.integer;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AIRSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAirsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsAgencyErrorRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsAgencyRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.AirsDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawAirsDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * The AIRS documents: the received record of a document, the agencies, the standardized record written by the import
 * and the import's status updates (formerly a MyBatis mapper with the same name and methods), through Spring Data
 * repositories over StdAirsDoc, RawAirsDoc and CodedAirsAgencies and one JPQL query (the received record as the
 * screen's VO). No native SQL.
 * <p>
 * The import's insert keeps its conversions in the database (StdAirsDoc's write transformers: UPPER/TRIM, FN_TO_DATE,
 * TO_CHAR(FN_TO_DATE(...))). PROCESS_DATE of the "no records" update stays the database's SYSDATE; "processed today"
 * of the agency error count is the day of the application server's clock.
 *
 * @author vxp06
 */
@Repository
@RequiredArgsConstructor
public class AIRSDocumentDAO {

	private final JpqlStatements jpql;
	private final AirsDocRepository stdDocs;
	private final RawAirsDocRepository rawDocs;

	/**
	 * Inserts the standardized record of a received AIRS record (the import's map: the RAW_AIRS_DOC columns, recoded).
	 *
	 * @return 1
	 */
	@Transactional
	public int writeRawAIRSRecordDataToStdTable(Map<Object, Object> docVO) {
		Map<Object, Object> m = docVO;
		stdDocs.saveAndFlush(StdAirsDoc.builder()
				.documentId(integer(m.get("DOCUMENT_ID")))
				.airsSrcId(decimal(m.get("AIRS_SRC_ID")))
				.airsId(decimal(m.get("ID")))
				.tcId(text(m.get("TC_ID")))
				.urn(text(m.get("URN")))
				.agencyId(text(m.get("AGENCY_ID")))
				.fname(text(m.get("FNAME")))
				.lname(text(m.get("LNAME")))
				.mname(text(m.get("MNAME")))
				.dobWritten(text(m.get("DOB")))
				.medRecordNumber(text(m.get("MEDRECNO")))
				.personAddress(text(m.get("ADDRESS")))
				.personCity(text(m.get("CITY")))
				.personState(text(m.get("STATE")))
				.personZip(text(m.get("ZIPCODE")))
				.personPhone(text(m.get("TELEPHONE")))
				.facilityAddress(text(m.get("PROV_ADDR")))
				.facilityCity(text(m.get("PROV_CITY")))
				.facilityState(text(m.get("PROV_ST")))
				.facilityZip(text(m.get("PROV_ZIP")))
				.facilityName(text(m.get("PROVNAME")))
				.facilityPhone(text(m.get("PROV_PHONE")))
				.ethnicity(text(m.get("ETHNICCD")))
				.birthCountry(text(m.get("BIRTH_COUNTRY")))
				.birthCountryOther(text(m.get("BIRTH_COUNTRY_OTHER")))
				.currentGender(text(m.get("GENDER")))
				.age(text(m.get("AGE")))
				.ssn(text(m.get("SSN")))
				.raceIndian(text(m.get("RACE_INDIAN")))
				.raceBlack(text(m.get("RACE_BLACK")))
				.raceAsian(text(m.get("RACE_ASIAN")))
				.racePacific(text(m.get("RACE_PACIFIC")))
				.raceWhite(text(m.get("RACE_WHITE")))
				.raceOther(text(m.get("RACE_OTHER")))
				.raceUnknown(text(m.get("RACE_UNKNOWN")))
				.swm(text(m.get("SWM")))
				.swf(text(m.get("SWF")))
				.idu(text(m.get("IDU")))
				.sexPartnerIdu(text(m.get("SEX_PARTNER_IDU")))
				.sexPartnerHiv(text(m.get("SEX_PARTNER_HIV")))
				.recvBloodProd(text(m.get("RECV_BLOOD_PROD")))
				.perinatal(text(m.get("PERINATAL")))
				.riskOther(text(m.get("RISK_OTHER")))
				.riskUnknown(text(m.get("RISK_UNKNOWN")))
				.sigDateText(text(m.get("SIG_DT")))
				.admissionDateText(text(m.get("ADMISS_DT")))
				.dodText(text(m.get("DOD_TEXT")))
				.recvBloodProdDateText(text(m.get("RECV_BLOOD_PROD_DATE_TEXT")))
				.numPartners(text(m.get("NUM_PARTNERS")))
				.ptInterviewDateWritten(text(m.get("DT_PT_INTERVIEW")))
				.firstPosHivDateWritten(text(m.get("DT_FST_POS_HIV")))
				.initHivDiag(text(m.get("I_ILL")))
				.initHivDiagDateText(text(m.get("I_DIAG_DT")))
				.informedHivPos(text(m.get("INFORMED_OF_DIAG")))
				.informedHivDateText(text(m.get("INFORMED_OF_DIAG_DT")))
				.prevHivDiag(text(m.get("PREV_I_ILL")))
				.prevHivDiagDateText(text(m.get("PREV_I_DIAG_DT")))
				.initAidsDiag(text(m.get("D_DIAG")))
				.initAidsDiagDateText(text(m.get("D_DIAG_DT")))
				.prevAidsDiag(text(m.get("PREV_D_DIAG")))
				.prevAidsDiagDateText(text(m.get("PREV_D_DIAG_DT")))
				.arv6Mo(text(m.get("ARV_6_MO")))
				.arv1Code(text(m.get("ARV1_CODE")))
				.arv6MoName(text(m.get("ARV_6_MO_NAME")))
				.arv2Code(text(m.get("ARV2_CODE")))
				.arv6MoName2(text(m.get("ARV_6_MO_NAME2")))
				.arv3Code(text(m.get("ARV3_CODE")))
				.arv6MoName3(text(m.get("ARV_6_MO_NAME3")))
				.arv6MoName4(text(m.get("ARV_6_MO_NAME4")))
				.arv6MoStartDateText(text(m.get("ARV_6_MO_START")))
				.arv6MoEndDateText(text(m.get("ARV_6_MO_END")))
				.arvCurrentTest(text(m.get("ARV_CURRENT_TEST")))
				.firstHivTestEverDateText(text(m.get("DT_FST_HIV_EVER")))
				.numberTest2Yrs(text(m.get("NUMBER_TEST_2_YRS")))
				.everTestNeg(text(m.get("EVER_TEST_NEGATIVE")))
				.lastNegDateText(text(m.get("DT_LAST_NEGATIVE")))
				.fileCreatedDate(text(m.get("DT_CREATED")))
				.patRecModDate(text(m.get("DT_MODIFIED")))
				.recordType(text(m.get("RECORD_TYPE")))
				.ctrFlag(text(m.get("CTR_FLAG")))
				.euci(text(m.get("EUCI")))
				.altName(text(m.get("AKA")))
				.caseNo(text(m.get("CASE_NO")))
				.isMedicaid(text(m.get("IS_MEDICAID")))
				.medicaidNumber(text(m.get("MEDICAID_NUMBER")))
				.birthSex(text(m.get("BIRTH_SEX")))
				.fedId(text(m.get("FED_ID")))
				.dunsId(text(m.get("DUNS_ID")))
				.providerCd(text(m.get("PROVIDER_CD")))
				.providerDescription(text(m.get("PROVIDER_DESCRIPTION")))
				.currentProcessStatus(decimal(m.get("CURRENT_PROCESS_STATUS")))
				.createUid(text(m.get("CREATE_UID")))
				.createDate(date(m.get("CREATE_DATE")))
				.airsCalcFileDateTime(date(m.get("GREATER_DATE")))
				.airsVersion(text(m.get("AIRS_VERSION")))
				.extralogid(text(m.get("EXTRALOGID")))
				.ctrVersion(text(m.get("CTR_VERSION")))
				.ctrFormid(text(m.get("CTR_FORMID")))
				.ctrPreliminaryPositive(text(m.get("CTR_PRELIMINARY_POSITIVE")))
				.build());
		return 1;
	}

	/** The received record of a document as the screen reads it (the former query's columns and aliases). */
	private static final String RAW_DOCUMENT = """
			select r.documentId as DOCUMENT_ID, r.fname as FNAME, r.mname as MNAME, r.lname as LNAME, r.aka as ALT_NAME, r.dob as DOB,
			r.ssn as SSN, r.age as AGE, r.birthCountry as BIRTH_COUNTRY_OTHER, r.countryBirth as BIRTH_COUNTRY, r.dodText as DOD_TEXT,
			r.address as ADDRESS, r.city as PERSON_CITY, r.state as PERSON_STATE, r.zipcode as PERSON_ZIP, r.telephone as PERSON_PHONE_TEXT,
			r.raceWhite as RACE_WHITE, r.raceBlack as RACE_BLACK, r.raceAsian as RACE_ASIAN, r.raceIndian as RACE_INDIAN,
			r.racePacific as RACE_PACIFIC, r.raceOther as RACE_OTHER, r.raceUnknown as RACE_UNKNOWN, r.ethniccd as ETHNICITY,
			r.sex as BIRTH_SEX, r.gender as CURRENT_GENDER, r.risk01 as SEX_WITH_MALE, r.risk02 as SEX_WITH_FEMALE, r.risk03 as IDU,
			r.risk04 as SEX_WITH_IDU, r.risk06 as RECV_BLOOD_TRANSFUSION, r.risk05 as SEX_WITH_HIV, r.risk07 as PERINATAL_RISK,
			r.risk08 as OTHER_RISK, r.risk09 as UNKOWN_RISK, r.tcId as TC_ID, r.urn as URN, r.medicaidno as MEDICAID_NUMBER,
			r.caseNo as CASE_NO, r.medrecno as MED_RECORD_NUMBER, r.fedId as FED_ID, r.dunsId as DUNS_ID,
			r.admissDt as ADMISSION_DATE_TEXT, r.dateRecvBldProd as RECV_BLOOD_PROD_DATE_TEXT, r.iDiagDt as INIT_HIV_DIAG_DATE_TEXT,
			r.informedOfDiag as INFORMED_HIV_POS, r.informedOfDiagDt as INFORMED_HIV_DATE_TEXT, r.prevIDiagDt as PREV_HIV_DIAG_DATE_TEXT,
			r.dDiag as INIT_AIDS_DIAG, r.dDiagDt as INIT_AIDS_DIAG_DATE_TEXT, r.prevDDiag as PREV_AIDS_DIAG,
			r.prevDDiagDt as PREV_AIDS_DIAG_DATE_TEXT,
			function('FN_TO_DATE' as Timestamp, r.dtPtInterview, 'MM/DD/YYYY') as PT_INTERVIEW_DATE,
			function('FN_TO_DATE' as Timestamp, r.dtFstPosHiv, 'MM/DD/YYYY') as FIRST_POS_HIV_DATE,
			r.arv6Mo as ARV_6_MO, r.arv6MoName as ARV_6_MO_NAME, r.arv6MoStart as ARV_6_MO_START_DATE_TEXT,
			r.arv6MoEnd as ARV_6_MO_END_DATE_TEXT, r.arvCurrentTest as ARV_CURRENT_TEST, r.dtFstHivEver as FIRST_HIV_TEST_EVER_DATE_TEXT,
			r.numberTest2Yrs as NUMBER_TEST_2_YRS, r.everTestNegative as EVER_TEST_NEG, r.dtLastNegative as LAST_NEG_DATE_TEXT,
			r.provname as FACILITY_NAME, r.providerDescription as PROVIDER_DESCRIPTION, r.agencyId as AGENCY_ID,
			r.provCity as FACILITY_CITY, r.provSt as FACILITY_STATE, r.provZip as FACILITY_ZIP, r.provAddr as PROV_ADDR,
			'RAW' as DOCUMENT_SOURCE_TABLE
			from RawAirsDoc r where r.documentId = ?1""";

	/**
	 * The received record of a document (DOCUMENT_SOURCE_TABLE 'RAW'; the interview and first positive dates read with
	 * the database function FN_TO_DATE), mapped to the VO by column name as MyBatis did.
	 *
	 * @return the VO, or null if there is no such document
	 */
	public AIRSDocVO getRawAIRSDocument(int documentID) {
		// TELEPHONE was selected twice (PERSON_PHONE_TEXT, FACILITY_PHONE); JPQL selects it once and the row gets both labels
		return jpql.one(RAW_DOCUMENT, row -> AIRS_DOC.map(withFacilityPhone(row)), documentID);
	}

	private static final RowMapper<AIRSDocVO> AIRS_DOC = Rows.bean(AIRSDocVO.class);

	/** The row with FACILITY_PHONE added (the value of PERSON_PHONE_TEXT, the same column). */
	private static Row withFacilityPhone(Row row) {
		List<String> labels = new ArrayList<>(row.labels());
		List<Object> values = new ArrayList<>(row.values());
		labels.add("FACILITY_PHONE");
		values.add(row.get("PERSON_PHONE_TEXT"));
		return new Row(labels, values);
	}

	/**
	 * An agency that is not retired, as a map: AGENCY_ID, LAST_UPGRADE_DATE (a key only when its value is set).
	 *
	 * @return the map, or null if there is no such agency; more than one fails, as before
	 */
	public Map<String, Object> getLastUpgradedDate(String aGENCY_ID) {
		List<AirsAgencyRow> rows = rawDocs.findActiveAgency(aGENCY_ID);
		if (rows.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, rows.size());
		}
		if (rows.isEmpty()) {
			return null;
		}
		Map<String, Object> agency = new HashMap<>();
		putIfSet(agency, "AGENCY_ID", rows.get(0).agencyId());
		putIfSet(agency, "LAST_UPGRADE_DATE", rows.get(0).lastUpgradeDate());
		return agency.isEmpty() ? null : agency;
	}

	/**
	 * Stops the waiting records that hold no data (TC_ID 'NO_RECORDS' or URN 'NO_RECORD') at the raw layer (status 18).
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int stopAirsNORecordsAtRaw() {
		return rawDocs.stopNoRecords();
	}

	/**
	 * The records stopped for an unknown agency (status 25), counted per agency and creation day, ordered by agency, as
	 * maps: AGENCY_ID, CREATE_DATE (MM/DD/YYYY), TOTAL_COUNT (a key only when its value is set).
	 *
	 * @param inputParameterMap checkForToday: true for the records processed today only
	 */
	public List<Map<String, Object>> checkForActiveErrors(Map<String, Boolean> inputParameterMap) {
		List<AirsAgencyErrorRow> rows;
		if (Boolean.TRUE.equals(inputParameterMap.get("checkForToday"))) {
			LocalDate today = LocalDate.now();
			rows = rawDocs.countAgencyErrorsProcessedBetween(Timestamp.valueOf(today.atStartOfDay()), Timestamp.valueOf(today.plusDays(1).atStartOfDay()));
		} else {
			rows = rawDocs.countAgencyErrors();
		}
		List<Map<String, Object>> errors = new ArrayList<>();
		for (AirsAgencyErrorRow row : rows) {
			Map<String, Object> error = new HashMap<>();
			putIfSet(error, "AGENCY_ID", row.agencyId());
			putIfSet(error, "CREATE_DATE", row.createDate());
			putIfSet(error, "TOTAL_COUNT", row.totalCount() == null ? null : BigDecimal.valueOf(row.totalCount()));    // a NUMBER, as getObject read it
			errors.add(error.isEmpty() ? null : error);
		}
		return errors;
	}

	/** The records stopped for an unknown agency whose agency is now in the reference table (and not retired), once each. */
	public List<Long> getDocumentIdsHasValidAgency() {
		List<Long> documentIds = new ArrayList<>();
		for (Integer documentId : rawDocs.findDocumentIdsWithValidAgency()) {
			documentIds.add(documentId == null ? null : documentId.longValue());
		}
		return documentIds;
	}

	private static void putIfSet(Map<String, Object> map, String key, Object value) {
		if (value != null) {
			map.put(key, value);
		}
	}
}
