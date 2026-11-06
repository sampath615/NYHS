package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ADAPDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawProviderAddress;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdAdapDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.NyehmsAdapNycFeedback;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawAdapDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.RowBuilder;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AdapDocRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AdapNycFileSummaryRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.EharsPersonNycRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawProviderAddressRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.StdAdapDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NyehmsAdapNycFeedbackRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

/**
 * The ADAP (AIDS Drug Assistance Program) documents (formerly a MyBatis mapper with the same name and methods): the
 * import and the document copy, the document screen and its risk view, and the NYC file exchange (the extract shipped
 * to NYC, the upload and review of NYC's feedback, the summaries). Through Spring Data repositories over StdAdapDoc,
 * RawAdapDoc, NyehmsAdapNycFeedback, EharsPersonNyc and RawProviderAddress.
 * <p>
 * Native SQL (unchanged, values bound) only for: the NYC extract and the discrepancy file (generateNYCData,
 * generateADAPNYCDiscrepancyFile) - a six-table Oracle (+) join whose unqualified END_DATE belongs to a table that is
 * not known, wrapped in {@code select distinct *}, its rows maps typed by the columns' database types; and the two
 * summaries (GROUP BY ROLLUP). Done in Java: the provider hierarchy and the pick of one address per NPI (the former
 * ROW_NUMBER), the street concatenation (Oracle's || of NULL), the TRIM of the uploaded values, the VO mapping
 * (MyBatis' auto-mapping rules, {@link Rows}). Kept in the database: UPPER/TRIM of the imported values (entity write
 * transformers), SYSDATE.
 * <p>
 * Bug kept as it was: the shipped-records count of generateADAPNYCShippedMatchSummary counts the file
 * 'nyctestFile.dbf', not the given file.
 *
 * @author Srikar Kummamuri
 */
@Repository
@RequiredArgsConstructor
public class ADAPDocumentDAO {

	/** The former adapNycExtractQuery fragment (native: see the class comment). */
	private static final String NYC_EXTRACT = """
			select distinct * from (
				SELECT to_char(a.DOCUMENT_ID) DOCUMENT_ID,
			          a.ADAP_SRC_ID,
			          p.stateno NYEHMS_STATENO,
			          a.FNAME,
			          a.LNAME,
			          a.MNAME,
			          a.SUFFIX,
			          a.DOB_TEXT,
			          a.SSN,
			          a.MEDICAID_NUMBER,
			          a.ADAP_ID,
			          a.CURRENT_GENDER,
			          a.BIRTH_SEX,
			          a.ETHNICCD,
			          a.RACE_CD,
			          a.RACE_INDIAN,
			          a.RACE_BLACK,
			          a.RACE_ASIAN,
			          a.RACE_PACIFIC,
			          a.RACE_WHITE,
			          a.RACE_MULTIPLE,
			          a.RACE_OTHER,
			          a.RACE_UNKNOWN,
			          a.CARE_OF,
			          a.PERSON_ADDRESS,
			          a.PERSON_CITY,
			          a.PERSON_STATE,
			          a.PERSON_ZIP,
			          a.PERSON_PLUS4,
			          a.PERSON_COUNTY,
			          a.PERSON_CENSUS_TRACT,
			          a.PERSON_LATITUDE,
			          a.PERSON_LONGITUDE,
			          a.PERSON_PHONE,
			          a.CERT_PROV_FNAME,
			          a.CERT_PROV_MNAME,
			          a.CERT_PROV_LNAME,
			          a.CERT_PROV_ADDRESS,
			          a.CERT_PROV_CITY,
			          a.CERT_PROV_STATE,
			          a.CERT_PROV_ZIP,
			          a.CERT_PROV_PLUS4,
			          a.CERT_PROV_COUNTY,
			          a.CERT_PROV_CENSUS_TRACT,
			          a.CERT_PROV_LATITUDE,
			          a.CERT_PROV_LONGITUDE,
			          a.CERT_PROV_PHONE,
			          a.CERT_PROV_NPI,
			          a.PCP_PROV_FNAME,
			          a.PCP_PROV_MNAME,
			          a.PCP_PROV_LNAME,
			          a.PCP_PROV_ADDRESS,
			          a.PCP_PROV_CITY,
			          a.PCP_PROV_STATE,
			          a.PCP_PROV_ZIP,
			          a.PCP_PROV_PLUS4,
			          a.PCP_PROV_COUNTY,
			          a.PCP_PROV_CENSUS_TRACT,
			          a.PROV_CENSUS_TRACT_SUFFIX_PCP,
			          a.PCP_PROV_LATITUDE,
			          a.PCP_PROV_LONGITUDE,
			          a.PCP_PROV_PHONE,
			          a.PCP_PROV_NPI,
			          a.RX_PROV_FNAME,
			          a.RX_PROV_MNAME,
			          a.RX_PROV_LNAME,
			          a.RX_PROVIDER_TYPE,
			          a.RX_PROV_ADDRESS,
			          a.RX_PROV_CITY,
			          a.RX_PROV_STATE,
			          a.RX_PROV_ZIP,
			          a.RX_PROV_PLUS4,
			          a.RX_PROV_COUNTY,
			          a.RX_PROV_CENSUS_TRACT,
			          a.RX_PROV_LATITUDE,
			          a.RX_PROV_LONGITUDE,
			          a.RX_PROV_PHONE,
			          a.RX_PROV_NPI,
			          a.FLAG_CHOSEN_PROVIDER,
			          to_char(a.RAW_PROVIDER_ADDRESS_ID) RAW_PROVIDER_ADDRESS_ID,
			          a.FIRST_HIV_TEST_DATE_TEXT,
			          a.ADAP_FILE,
			          a.SWM,
			          a.SWF,
			          a.IDU,
			          a.SEX_PARTNER_IDU,
			          a.SEX_PARTNER_HIV,
			          a.RECV_BLOOD_PROD,
			          a.PERINATAL,
			          a.RISK_OTHER,
			          a.RISK09,
			          a.RISK10,
			          a.RISK11,
			          a.RISK12,
			          a.RISK13,
			          a.RISK14,
			          a.RISK15,
			          a.HEALTHCARE_WORKER,
			          to_char(a.MATCH_WEIGHT) MATCH_WEIGHT,
			          a.MATCH_PASS,
			          a.flux_MATCH_CD MATCH_CD,
			          TO_CHAR(a.RECV_DT, 'MM/DD/YYYY') RECV_DT,
			          a.REVIEW_UID,
			          TO_CHAR(a.REVIEW_DT, 'MM/DD/YYYY') REVIEW_DT,
			          a.EHARS_STATUS_CD,
			          /*TO_CHAR(a.NYC_SHIP_DT, 'MM/DD/YYYY') NYC_SHIP_DT,*/
			          NULL as NYC_SHIP_DT,
			          a.CITYNO,
			          a.ASSESS_ASSIGN,
			          TO_CHAR(a.EHARS_IMPORT_DATE, 'MM/DD/YYYY') EHARS_IMPORT_DATE,
			          to_char(a.CURRENT_PROCESS_STATUS) CURRENT_PROCESS_STATUS,
			          to_char(a.QS_PARENT_DOC_ID) QS_PARENT_DOC_ID,
			          TO_CHAR(a.RECEIVED_DATE, 'MM/DD/YYYY') RECEIVED_DATE,
			          a.CREATE_UID,
			          TO_CHAR(a.CREATE_DATE, 'MM/DD/YYYY') CREATE_DATE,
			          CASE
			             WHEN e.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE e.EHARS_STATENO
			          END
			             AS NYS_EHARS_STATENO,
			          CASE
			             WHEN e.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE e.HIV_CDC
			          END
			             AS NYS_HIV_CDC,
			          CASE
			             WHEN e.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE e.AIDS_CDC
			          END
			             AS NYS_AIDS_CDC,
			          CASE
			             WHEN e.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE e.HIV_AIDS_CDC_ELIG
			          END
			             AS NYS_HIV_AIDS_CDC_ELIG,
			          CASE
			             WHEN n.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE n.EHARS_STATENO
			          END
			             AS NYC_EHARS_STATENO,
			          CASE
			             WHEN n.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE n.CITYNO
			          END
			             AS NYC_CITYNO,
			          CASE
			             WHEN n.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE n.HIV_CDC
			          END
			             AS NYC_HIV_CDC,
			          CASE
			             WHEN n.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE n.AIDS_CDC
			          END
			             AS NYC_AIDS_CDC,
			          CASE
			             WHEN n.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE n.HIV_AIDS_CDC
			          END
			             AS NYC_HIV_AIDS_CDC,
			          CASE
			             WHEN n.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE n.HIV_CDC_ELIG
			          END
			             AS NYC_HIV_CDC_ELIG,
			          CASE
			             WHEN n.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE n.AIDS_CDC_ELIG
			          END
			             AS NYC_AIDS_CDC_ELIG,
			          CASE
			             WHEN n.STATUS_FLAG IN ('D', 'M', 'P') THEN NULL
			             ELSE n.HIV_AIDS_CDC_ELIG
			          END
			             AS NYC_HIV_AIDS_CDC_ELIG,
			             CASE
			                when flag_chosen_provider='RX' then  RX_PROV_FNAME
			                when flag_chosen_provider='PCP' then PCP_PROV_FNAME
			                when flag_chosen_provider='CERT' then CERT_PROV_FNAME
			                end
			                as PROV_FNAME,
			             CASE
			                when flag_chosen_provider='RX' then  RX_PROV_MNAME
			                when flag_chosen_provider='PCP' then PCP_PROV_MNAME
			                when flag_chosen_provider='CERT' then CERT_PROV_MNAME
			                end
			                as PROV_MNAME,
			            CASE
			                when flag_chosen_provider='RX' then  RX_PROV_LNAME
			                when flag_chosen_provider='PCP' then PCP_PROV_LNAME
			                when flag_chosen_provider='CERT' then CERT_PROV_LNAME
			                end
			                as PROV_LNAME,
			             CASE
			                when flag_chosen_provider='RX' then  RX_PROV_ADDRESS
			                when flag_chosen_provider='PCP' then PCP_PROV_ADDRESS
			                when flag_chosen_provider='CERT' then CERT_PROV_ADDRESS
			                end
			                as PROV_ADDR,
			         CASE
			                when flag_chosen_provider='RX' then  RX_PROV_CITY
			                when flag_chosen_provider='PCP' then PCP_PROV_CITY
			                when flag_chosen_provider='CERT' then CERT_PROV_CITY
			                end
			                as PROV_CITY,
			        CASE
			                when flag_chosen_provider='RX' then  RX_PROV_STATE
			                when flag_chosen_provider='PCP' then PCP_PROV_STATE
			                when flag_chosen_provider='CERT' then CERT_PROV_STATE
			                end
			                as PROV_ST,
			                 CASE
			                when flag_chosen_provider='RX' then  RX_PROV_ZIP
			                when flag_chosen_provider='PCP' then PCP_PROV_ZIP
			                when flag_chosen_provider='CERT' then CERT_PROV_ZIP
			                end
			                as PROV_ZIP,
			                        CASE
			                when flag_chosen_provider='RX' then  RX_PROV_COUNTY
			                when flag_chosen_provider='PCP' then PCP_PROV_COUNTY
			                when flag_chosen_provider='CERT' then CERT_PROV_COUNTY
			                end
			                as COUNTY_PROV,
			                         CASE
			                when flag_chosen_provider='RX' then  RX_PROV_PLUS4
			                when flag_chosen_provider='PCP' then PCP_PROV_PLUS4
			                when flag_chosen_provider='CERT' then CERT_PROV_PLUS4
			                end
			                as PROV_PLUS4,
			                           CASE
			                when flag_chosen_provider='RX' then  RX_PROV_CENSUS_TRACT
			                when flag_chosen_provider='PCP' then PCP_PROV_CENSUS_TRACT
			                when flag_chosen_provider='CERT' then CERT_PROV_CENSUS_TRACT
			                end
			                as TRACT_CENSUS_PROV,
			                                    CASE
			                when flag_chosen_provider='RX' then  RX_PROV_LATITUDE
			                when flag_chosen_provider='PCP' then PCP_PROV_LATITUDE
			                when flag_chosen_provider='CERT' then CERT_PROV_LATITUDE
			                end
			                as PROV_LAT,
			                                              CASE
			                when flag_chosen_provider='RX' then  RX_PROV_LONGITUDE
			                when flag_chosen_provider='PCP' then PCP_PROV_LONGITUDE
			                when flag_chosen_provider='CERT' then CERT_PROV_LONGITUDE
			                end
			                as PROV_LONG,
			                                             CASE
			                when flag_chosen_provider='RX' then  RX_PROV_PHONE
			                when flag_chosen_provider='PCP' then PCP_PROV_PHONE
			                when flag_chosen_provider='CERT' then CERT_PROV_PHONE
			                end
			                as PROV_PHONE ,
			                 R.PROVIDER_FNAME  LATEST_PROVIDER_FNAME,
			                  R.PROVIDER_MNAME LATEST_PROVIDER_MNAME,
			                  R.PROVIDER_LNAME LATEST_PROVIDER_LNAME,
			                  R.ADDRESS LATEST_PROVIDER_ADDRESS,
			                  R.CITY LATEST_PROVIDER_CITY,
			                  R.STATE LATEST_PROVIDER_STATE,
			                  R.ZIPCODE LATEST_PROVIDER_ZIPCODE,
			                  R.COUNTY_FIPS LATEST_PROVIDER_COUNTY_FIPS
			      FROM NYEHMS_EHARS_STATENO_MAP E,
			          EHARS_PERSON_NYC N,
			          ASSIGNMENT_ADDRESS B,
			          RAW_PROVIDER_ADDRESS r,
			          Person_doc_association p,
			          std_adap_doc a
			    WHERE
			            a.document_id = p.document_id and
			           p.DISSASSOCIATION_DATE is null and
			           p.stateno = E.nyehms_stateno(+)
			          AND p.stateno = n.nyehms_stateno(+)
			           AND p.SURV_ASSIGNMENT_ID = B.document_id(+)
			          AND B.PROVIDER_ADDRESS_ID = r.RAW_PROVIDER_ADDRESS_ID(+)
			          AND  END_DATE is null
			""";

	/** The end of the NYC extract queries: the records with a NYC provider of persons not CDC-eligible in eHARS. */
	private static final String NYC_EXTRACT_END = """
			)
			       where
			          COUNTY_PROV in ('005','047','061','081','085') and
			    (nyc_hiv_aids_cdc_elig ='N' or nyc_hiv_aids_cdc_elig is null)
			    and
			    (nys_hiv_aids_cdc_elig ='N' or nys_hiv_aids_cdc_elig is null)
			""";

	/** The former generateADAPNYCShippedMatchSummary (native: GROUP BY ROLLUP). Bug kept: the first count's file name. */
	private static final String SHIPPED_MATCH_SUMMARY = """
			select 'Records Shipped to NYC' STATUS, TO_CHAR(count(*)) COUNT from std_Adap_doc where adap_file = 'nyctestFile.dbf' and NYC_SHIPPED_BY is not null
			union
			select Nvl(REVIEW_STATUS,'NYC Feedback Records Received') STATUS, TO_CHAR(count(REVIEW_STATUS)) COUNT
			from NYEHMS_ADAP_NYC_FEEDBACK
			where ADAP_FILE = ?1
			group by rollup(REVIEW_STATUS) order by STATUS desc
			""";

	/** The former generateADAPNYCQualityStageMatchSummary (native: GROUP BY ROLLUP). */
	private static final String QUALITY_STAGE_MATCH_SUMMARY = """
			select Nvl(flux_match_Cd,'Total') STATUS, TO_CHAR(count(flux_match_Cd))  COUNT
			from std_Adap_doc
			where ADAP_FILE = ?1
			group by rollup(flux_match_Cd) order by STATUS desc
			""";

	/** The NYC counties (FIPS codes) whose providers' records go to NYC. */
	private static final List<String> NYC_COUNTIES = List.of("005", "047", "061", "081", "085");

	private final NativeStatements statements;
	private final StdAdapDocRepository stdDocs;
	private final NyehmsAdapNycFeedbackRepository feedback;
	private final EharsPersonNycRepository nycPersons;
	private final RawProviderAddressRepository providerAddresses;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The ADAP document screen: the standardized record with its received street and apartment (STREET_RAW), the
	 * person it is associated with (MATCHED_STATENO, DISCARD_STATUS) and its document's PARENT_DOCUMENT_ID.
	 *
	 * @param docId the document id
	 * @return the document, or null if there is none; more than one row (several open associations) fails, as before
	 */
	public ADAPDocVO getADAPDoc(int docId) throws DataAccessException {
		AdapDocRow row = one(stdDocs.findDocument(docId));
		if (row == null) {
			return null;
		}
		RowBuilder builder = new RowBuilder()
				.put("street_raw", oracleConcat(oracleConcat(row.street(), " "), row.apt()))
				.columns(row.doc(), StdAdapDoc.class, "DOCUMENT_ID")
				.put("MATCHED_STATENO", row.stateno())
				.put("DISCARD_STATUS", row.discardStatus())
				.columns(row.doc(), StdAdapDoc.class, "ADAP_ID", "SSN", "FNAME", "MNAME", "SUFFIX", "LNAME", "DOB_TEXT", "CURRENT_GENDER",
						"BIRTH_SEX", "RACE_CD", "ETHNICCD", "CARE_OF", "PERSON_CITY", "PERSON_STATE", "PERSON_ZIP", "PERSON_COUNTY",
						"PERSON_ADDRESS", "CERT_PROV_NPI", "FIRST_HIV_TEST_DATE_TEXT", "RACE_ASIAN", "RACE_BLACK", "RACE_INDIAN",
						"RACE_MULTIPLE", "RACE_OTHER", "RACE_PACIFIC", "RACE_UNKNOWN", "RACE_WHITE", "IDU", "RECV_BLOOD_PROD",
						"HEALTHCARE_WORKER", "PERINATAL", "SEX_PARTNER_IDU", "SWM", "SWF", "SEX_PARTNER_HIV", "RISK_OTHER",
						"MEDICAID_NUMBER", "PERSON_PHONE", "ADAP_FILE", "RECEIVED_DATE", "RX_PROV_NPI", "PCP_PROV_NPI", "RX_PROV_FNAME",
						"RX_PROV_MNAME", "RX_PROV_LNAME", "RX_PROV_CITY", "RX_PROV_STATE", "RX_PROV_ZIP", "RX_PROV_PLUS4",
						"RX_PROV_COUNTY", "RX_PROV_ADDRESS", "RX_PROV_PHONE", "PCP_PROV_FNAME", "PCP_PROV_MNAME", "PCP_PROV_LNAME",
						"PCP_PROV_CITY", "PCP_PROV_ADDRESS", "PCP_PROV_STATE", "PCP_PROV_ZIP", "PCP_PROV_PLUS4", "PCP_PROV_COUNTY",
						"PCP_PROV_PHONE", "CERT_PROV_FNAME", "CERT_PROV_MNAME", "CERT_PROV_LNAME", "CERT_PROV_STATE", "CERT_PROV_CITY",
						"CERT_PROV_ZIP", "CERT_PROV_PLUS4", "CERT_PROV_COUNTY", "CERT_PROV_ADDRESS", "CERT_PROV_PHONE",
						"FLAG_CHOSEN_PROVIDER", "EHARS_IMPORT_DATE", "CREATE_DATE", "NYC_SHIP_DT")
				.put("PARENT_DOCUMENT_ID", row.parentDocumentId());
		return Rows.bean(ADAPDocVO.class).map(builder.build());
	}

	/**
	 * Inserts a standardized record from the import's (or the copy's) map, keys as the former insert read them.
	 *
	 * @return 1
	 */
	@Transactional
	public int writeStdDocData(Map<Object, Object> stdDocData) {
		Map<Object, Object> m = stdDocData;
		insert(StdAdapDoc.builder()
				.birthSex(text(m.get("BIRTH_SEX")))
				.currentGender(text(m.get("CURRENT_GENDER")))
				.eharsStatusCd(text(m.get("EHARS_STATUS_CD")))
				.assessAssign(text(m.get("ASSESS_ASSIGN")))
				.dobText(text(m.get("DOB_TEXT")))
				.personLatitude(text(m.get("PERSON_LATITUDE")))
				.certProvLatitude(text(m.get("CERT_PROV_LATITUDE")))
				.pcpProvLatitude(text(m.get("PCP_PROV_LATITUDE")))
				.rxProviderType(text(m.get("RX_PROVIDER_TYPE")))
				.rxProvLatitude(text(m.get("RX_PROV_LATITUDE")))
				.firstHivTestDateText(text(m.get("FIRST_HIV_TEST_DATE_TEXT")))
				.reviewUid(text(m.get("REVIEW_UID")))
				.cityno(text(m.get("CITYNO")))
				.personLongitude(text(m.get("PERSON_LONGITUDE")))
				.certProvLongitude(text(m.get("CERT_PROV_LONGITUDE")))
				.pcpProvLongitude(text(m.get("PCP_PROV_LONGITUDE")))
				.rxProvLongitude(text(m.get("RX_PROV_LONGITUDE")))
				.certProvNpi(text(m.get("CERT_PROV_NPI")))
				.pcpProvNpi(text(m.get("PCP_PROV_NPI")))
				.rxProvNpi(text(m.get("RX_PROV_NPI")))
				.personState(text(m.get("PERSON_STATE")))
				.certProvState(text(m.get("CERT_PROV_STATE")))
				.pcpProvState(text(m.get("PCP_PROV_STATE")))
				.provCensusTractSuffixPcp(text(m.get("PROV_CENSUS_TRACT_SUFFIX_PCP")))
				.rxProvState(text(m.get("RX_PROV_STATE")))
				.personCounty(text(m.get("PERSON_COUNTY")))
				.personCity(text(m.get("PERSON_CITY")))
				.certProvCity(text(m.get("CERT_PROV_CITY")))
				.pcpProvCity(text(m.get("PCP_PROV_CITY")))
				.rxProvCity(text(m.get("RX_PROV_CITY")))
				.personPlus4(text(m.get("PERSON_PLUS4")))
				.personCensusTract(text(m.get("PERSON_CENSUS_TRACT")))
				.certProvPlus4(text(m.get("CERT_PROV_PLUS4")))
				.certProvCensusTract(text(m.get("CERT_PROV_CENSUS_TRACT")))
				.pcpProvPlus4(text(m.get("PCP_PROV_PLUS4")))
				.pcpProvCensusTract(text(m.get("PCP_PROV_CENSUS_TRACT")))
				.rxProvPlus4(text(m.get("RX_PROV_PLUS4")))
				.rxProvCensusTract(text(m.get("RX_PROV_CENSUS_TRACT")))
				.careOf(text(m.get("CARE_OF")))
				.adapFile(text(m.get("ADAP_FILE")))
				.personZip(text(m.get("PERSON_ZIP")))
				.certProvZip(text(m.get("CERT_PROV_ZIP")))
				.pcpProvZip(text(m.get("PCP_PROV_ZIP")))
				.rxProvZip(text(m.get("RX_PROV_ZIP")))
				.ssn(text(m.get("SSN")))
				.reviewDt(date(m.get("REVIEW_DT")))
				.eharsImportDate(date(m.get("EHARS_IMPORT_DATE")))
				.receivedDate(date(m.get("RECEIVED_DATE")))
				.createDate(date(m.get("CREATE_DATE")))
				.documentId(longValue(m.get("DOCUMENT_ID")))
				.rawProviderAddressId(longValue(m.get("RAW_PROVIDER_ADDRESS_ID")))
				.matchWeight(decimal(m.get("MATCH_WEIGHT")))
				.currentProcessStatus(integer(m.get("CURRENT_PROCESS_STATUS")))
				.qsParentDocId(longValue(m.get("QS_PARENT_DOC_ID")))
				.raceIndian(text(m.get("RACE_INDIAN")))
				.raceBlack(text(m.get("RACE_BLACK")))
				.raceAsian(text(m.get("RACE_ASIAN")))
				.racePacific(text(m.get("RACE_PACIFIC")))
				.raceWhite(text(m.get("RACE_WHITE")))
				.raceMultiple(text(m.get("RACE_MULTIPLE")))
				.raceOther(text(m.get("RACE_OTHER")))
				.raceUnknown(text(m.get("RACE_UNKNOWN")))
				.createUid(text(m.get("CREATE_UID")))
				.matchedStateno(text(m.get("MATCHED_STATENO")))
				.medicaidNumber(text(m.get("MEDICAID_NUMBER")))
				.ethniccd(text(m.get("ETHNICCD")))
				.raceCd(text(m.get("RACE_CD")))
				.swm(text(m.get("SWM")))
				.swf(text(m.get("SWF")))
				.idu(text(m.get("IDU")))
				.sexPartnerIdu(text(m.get("SEX_PARTNER_IDU")))
				.sexPartnerHiv(text(m.get("SEX_PARTNER_HIV")))
				.recvBloodProd(text(m.get("RECV_BLOOD_PROD")))
				.perinatal(text(m.get("PERINATAL")))
				.riskOther(text(m.get("RISK_OTHER")))
				.healthcareWorker(text(m.get("HEALTHCARE_WORKER")))
				.fname(text(m.get("FNAME")))
				.personPhone(text(m.get("PERSON_PHONE")))
				.certProvFname(text(m.get("CERT_PROV_FNAME")))
				.certProvMname(text(m.get("CERT_PROV_MNAME")))
				.certProvCounty(text(m.get("CERT_PROV_COUNTY")))
				.certProvPhone(text(m.get("CERT_PROV_PHONE")))
				.pcpProvFname(text(m.get("PCP_PROV_FNAME")))
				.pcpProvMname(text(m.get("PCP_PROV_MNAME")))
				.pcpProvCounty(text(m.get("PCP_PROV_COUNTY")))
				.pcpProvPhone(text(m.get("PCP_PROV_PHONE")))
				.rxProvFname(text(m.get("RX_PROV_FNAME")))
				.rxProvMname(text(m.get("RX_PROV_MNAME")))
				.rxProvCounty(text(m.get("RX_PROV_COUNTY")))
				.rxProvPhone(text(m.get("RX_PROV_PHONE")))
				.certProvAddress(text(m.get("CERT_PROV_ADDRESS")))
				.pcpProvAddress(text(m.get("PCP_PROV_ADDRESS")))
				.rxProvAddress(text(m.get("RX_PROV_ADDRESS")))
				.lname(text(m.get("LNAME")))
				.mname(text(m.get("MNAME")))
				.adapId(text(m.get("ADAP_ID")))
				.certProvLname(text(m.get("CERT_PROV_LNAME")))
				.pcpProvLname(text(m.get("PCP_PROV_LNAME")))
				.rxProvLname(text(m.get("RX_PROV_LNAME")))
				.adapSrcId(text(m.get("ADAP_SRC_ID")))
				.matchCd(text(m.get("MATCH_CD")))
				.flagChosenProvider(text(m.get("FLAG_CHOSEN_PROVIDER")))
				.matchPass(text(m.get("MATCH_PASS")))
				.suffix(text(m.get("SUFFIX")))
				.personAddress(text(m.get("PERSON_ADDRESS")))
				.matchedProcessDate(date(m.get("MATCHED_PROCESS_DATE")))
				.fluxMatchCd(text(m.get("FLUX_MATCH_CD")))
				.fluxPotStateno(text(m.get("FLUX_POT_STATENO")))
				.fluxMatchedStateno(text(m.get("FLUX_MATCHED_STATENO")))
				.fluxQsParentDocId(longValue(m.get("FLUX_QS_PARENT_DOC_ID")))
				.fluxMatchedProcessDate(date(m.get("FLUX_MATCHED_PROCESS_DATE")))
				.build());
		return 1;
	}

	/**
	 * The addresses of the providers with the given NPIs ({@code npis}, a collection; not flipped to a facility), one per
	 * NPI: the address of the highest rank (NY with a county 3, NY without county 2, another state 1, no state 0), of
	 * those the latest (highest RAW_PROVIDER_ADDRESS_ID), as the former ROW_NUMBER() picked it; with its HIERARCHY
	 * (4: NY outside NYC, 3: NYC, 2: NY without county, 1: another state, 0: none) as text and RWNUM 1.
	 *
	 * @return maps with the former column labels (null values left out); by NPI
	 * @throws InvalidDataAccessApiUsageException if there are no NPIs (the former SQL was invalid then)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getSubProviderAddressesForTheNPS(Map<Object, Object> inputParameters) {
		Object npis = inputParameters.get("npis");
		if (!(npis instanceof Collection<?> collection) || collection.isEmpty()) {
			throw new InvalidDataAccessApiUsageException("No NPIs given: " + npis);
		}
		Map<String, RawProviderAddress> best = new TreeMap<>(java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder()));
		List<String> values = new ArrayList<>();
		for (Object npi : collection) {
			values.add(npi == null ? null : npi.toString());
		}
		for (RawProviderAddress address : providerAddresses.findByFlippedToFacilityDateIsNullAndNpiIn(values)) {
			RawProviderAddress current = best.get(address.getNpi());
			if (current == null || isPreferred(address, current)) {
				best.put(address.getNpi(), address);
			}
		}
		List<Map<String, String>> rows = new ArrayList<>();
		for (RawProviderAddress a : best.values()) {
			Map row = Rows.map().map(new RowBuilder()
					.columns(a, RawProviderAddress.class, "PROVIDER_FNAME", "PROVIDER_MNAME", "PROVIDER_LNAME", "PROVIDER_SUFFIX", "NPI",
							"COUNTY_FIPS", "ADDRESS", "CITY", "STATE", "ZIPCODE", "PLUS4", "PHONE")
					.put("HIERARCHY", Integer.toString(hierarchy(a)))
					.put("RWNUM", BigDecimal.ONE)
					.build());
			rows.add(row);
		}
		return rows;
	}

	/** The former HIERARCHY: 4 NY outside NYC, 3 NYC, 2 NY without county, 1 another state, else 0 (SQL NULL rules). */
	private static int hierarchy(RawProviderAddress a) {
		String state = a.getState();
		String county = a.getCountyFips();
		if ("NY".equals(state) && county != null && !NYC_COUNTIES.contains(county)) {
			return 4;
		}
		if ("NY".equals(state) && county != null) {
			return 3;
		}
		if ("NY".equals(state)) {
			return 2;
		}
		return state != null ? 1 : 0;
	}

	/** The former ROW_NUMBER order's rank: 3 NY with a county, 2 NY without county, 1 another state, else 0. */
	private static int rank(RawProviderAddress a) {
		if ("NY".equals(a.getState())) {
			return a.getCountyFips() != null ? 3 : 2;
		}
		return a.getState() != null ? 1 : 0;
	}

	/** Whether a comes before b in the former ROW_NUMBER order (rank DESC, RAW_PROVIDER_ADDRESS_ID DESC nulls first). */
	private static boolean isPreferred(RawProviderAddress a, RawProviderAddress b) {
		if (rank(a) != rank(b)) {
			return rank(a) > rank(b);
		}
		Long x = a.getRawProviderAddressId(), y = b.getRawProviderAddressId();
		if (x == null || y == null) {
			return x == null && y != null;
		}
		return x > y;
	}

	/**
	 * Inserts a received record from the import's (or the copy's) map, keys as the former insert read them.
	 *
	 * @return 1
	 */
	@Transactional
	public int insertRawADAPDocument(Map<Object, Object> rawDocData) {
		Map<Object, Object> m = rawDocData;
		insert(RawAdapDoc.builder()
				.trans(text(m.get("TRANS")))
				.spwa(text(m.get("SPWA")))
				.smale(text(m.get("SMALE")))
				.sivdu(text(m.get("SIVDU")))
				.sfemale(text(m.get("SFEMALE")))
				.sex(text(m.get("SEX")))
				.rother(text(m.get("ROTHER")))
				.race(text(m.get("RACE")))
				.mater(text(m.get("MATER")))
				.ivdu(text(m.get("IVDU")))
				.hispanic(text(m.get("HISPANIC")))
				.health(text(m.get("HEALTH")))
				.yrfirst(text(m.get("YRFIRST")))
				.provnpi(text(m.get("PROVNPI")))
				.pfirst(text(m.get("PFIRST")))
				.lastRx(text(m.get("LAST_RX")))
				.lastPc(text(m.get("LAST_PC")))
				.dob(text(m.get("DOB")))
				.mcdNumber(text(m.get("MCD_NUMBER")))
				.first(text(m.get("FIRST")))
				.pt1(text(m.get("PT1")))
				.plast(text(m.get("PLAST")))
				.last(text(m.get("LAST")))
				.city(text(m.get("CITY")))
				.state(text(m.get("STATE")))
				.pstate(text(m.get("PSTATE")))
				.pcity(text(m.get("PCITY")))
				.street(text(m.get("STREET")))
				.careof(text(m.get("CAREOF")))
				.adapFile(text(m.get("ADAP_FILE")))
				.zip(text(m.get("ZIP")))
				.pzip(text(m.get("PZIP")))
				.rawId(text(m.get("ID")))
				.apt(text(m.get("APT")))
				.ssn(text(m.get("SSN")))
				.recvDt(date(m.get("RECV_DT")))
				.documentId(longValue(m.get("DOCUMENT_ID")))
				.currentProcessStatus(integer(m.get("CURRENT_PROCESS_STATUS")))
				.pstreet(text(m.get("PSTREET")))
				.build());
		return 1;
	}

	/** The ADAP files with records waiting for review (status 17, not reviewed). */
	public List<String> getFileNames() {
		return stdDocs.findFileNamesToReview();
	}

	/**
	 * The ADAP files none of whose records is in process or shipped by a worker: with {@code nycShipStatus}
	 * 'notShipped' those whose records are not shipped to NYC, otherwise those shipped that have no NYC feedback yet.
	 */
	public List<String> getFileNameNotShippedToNYC(Map<String, Object> inParameterMap) {
		return "notShipped".equals(inParameterMap.get("nycShipStatus")) ? stdDocs.findFileNamesNotShipped()
				: stdDocs.findFileNamesShippedWithoutFeedback();
	}

	/**
	 * The NYC extract ({@code nycShiStatus} 'notShippedAllFiles': the records of every file not shipped yet;
	 * 'notShipped': those of {@code fileName} not shipped yet; otherwise those of {@code fileName} shipped by a worker).
	 *
	 * @return the rows as maps with the former column labels (null values left out)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> generateNYCData(Map<String, Object> inParameterMap) {
		Object status = inParameterMap.get("nycShiStatus");
		if ("notShippedAllFiles".equals(status)) {
			return (List) statements.list(NYC_EXTRACT + """
					and a.NYC_SHIP_DT  is null
					""" + NYC_EXTRACT_END, Rows.map());
		}
		if ("notShipped".equals(status)) {
			return (List) statements.list(NYC_EXTRACT + """
					and a.adap_file = ?1 and
					a.NYC_SHIP_DT  is null
					""" + NYC_EXTRACT_END, Rows.map(), inParameterMap.get("fileName"));
		}
		return (List) statements.list(NYC_EXTRACT + """
				and a.adap_file = ?1 and
				a.NYC_SHIPPED_BY  is not null
				""" + NYC_EXTRACT_END, Rows.map(), inParameterMap.get("fileName"));
	}

	/**
	 * Marks the extracted records ({@code adapNYCData}, maps with DOCUMENT_ID) as shipped to NYC now by
	 * {@code workerId}; unless {@code nycShiStatus} is 'notShippedAllFiles', only those of {@code fileName}.
	 *
	 * @return the number of records changed
	 * @throws InvalidDataAccessApiUsageException if there are no records (the former SQL was invalid then)
	 */
	@Transactional
	public int updateNYCDateForTheFile(Map<String, Object> inParameterMap) {
		Object data = inParameterMap.get("adapNYCData");
		if (!(data instanceof Collection<?> documents) || documents.isEmpty()) {
			throw new InvalidDataAccessApiUsageException("No documents given: " + data);
		}
		List<Long> documentIds = new ArrayList<>();
		for (Object document : documents) {
			documentIds.add(longValue(((Map<?, ?>) document).get("DOCUMENT_ID")));
		}
		String workerId = text(inParameterMap.get("workerId"));
		if (!"notShippedAllFiles".equals(inParameterMap.get("nycShiStatus"))) {
			return stdDocs.markShippedToNyc(workerId, text(inParameterMap.get("fileName")), documentIds);
		}
		return stdDocs.markShippedToNyc(workerId, documentIds);
	}

	/**
	 * Inserts the rows of an uploaded NYC return file ({@code fileDataList}, maps keyed by the former column names),
	 * uploaded by {@code workerId} now (the database's SYSDATE): the text values trimmed (as Oracle's TRIM: spaces, and
	 * an empty result is NULL), ADAP_FILE that of the ADAP document STDRPTS_ID.
	 *
	 * @return the number of rows inserted
	 * @throws InvalidDataAccessApiUsageException if there are no rows (the former SQL was invalid then)
	 */
	@Transactional
	public int uploadNYCReturnFileData(Map<String, Object> inputParameterMap) {
		Object data = inputParameterMap.get("fileDataList");
		if (!(data instanceof Collection<?> rows) || rows.isEmpty()) {
			throw new InvalidDataAccessApiUsageException("No file data given: " + data);
		}
		String workerId = text(inputParameterMap.get("workerId"));
		List<NyehmsAdapNycFeedback> feedbackRows = new ArrayList<>();
		for (Object row : rows) {
			Map<?, ?> f = (Map<?, ?>) row;
			Long stdrptsId = longValue(f.get("STDRPTS_ID"));
			feedbackRows.add(NyehmsAdapNycFeedback.builder()
					.adapFile(adapFileOf(stdrptsId))
					.insNo(trim(f.get("INS_NO")))
					.stdrptsId(stdrptsId)
					.stateno(trim(f.get("STATENO")))
					.fname(trim(f.get("FNAME")))
					.lname(trim(f.get("LNAME")))
					.mname(trim(f.get("MNAME")))
					.suffix(trim(f.get("SUFFIX")))
					.dob(trim(f.get("DOB")))
					.ssn(trim(f.get("SSN")))
					.medrecno(trim(f.get("MEDRECNO")))
					.address(trim(f.get("ADDRESS")))
					.city(trim(f.get("CITY")))
					.state(trim(f.get("STATE")))
					.zip(trim(f.get("ZIP")))
					.plus4(trim(f.get("PLUS4")))
					.countyRes(trim(f.get("COUNTY_RES")))
					.tractCens(trim(f.get("TRACT_CENS")))
					.resLat(trim(f.get("RES_LAT")))
					.resLong(trim(f.get("RES_LONG")))
					.provFname(trim(f.get("PROV_FNAME")))
					.provMname(trim(f.get("PROV_MNAME")))
					.provLname(trim(f.get("PROV_LNAME")))
					.provAddre(trim(f.get("PROV_ADDRE")))
					.provCity(trim(f.get("PROV_CITY")))
					.provState(trim(f.get("PROV_STATE")))
					.provZip(trim(f.get("PROV_ZIP")))
					.provPlus4(trim(f.get("PROV_PLUS4")))
					.countyPro(trim(f.get("COUNTY_PRO")))
					.provPhone(trim(f.get("PROV_PHONE")))
					.provLatit(trim(f.get("PROV_LATIT")))
					.provLongi(trim(f.get("PROV_LONGI")))
					.tractCen0(trim(f.get("TRACT_CEN0")))
					.matchCd(trim(f.get("MATCH_CD")))
					.mtchPass(decimal(f.get("MTCH_PASS")))
					.raceCd(trim(f.get("RACE_CD")))
					.ethniccd(trim(f.get("ETHNICCD")))
					.gender(trim(f.get("GENDER")))
					.eventDt(trim(f.get("EVENT_DT")))
					.risk01(trim(f.get("RISK01")))
					.risk02(trim(f.get("RISK02")))
					.risk03(trim(f.get("RISK03")))
					.risk04(trim(f.get("RISK04")))
					.risk05(trim(f.get("RISK05")))
					.risk06(trim(f.get("RISK06")))
					.risk07(trim(f.get("RISK07")))
					.risk08(trim(f.get("RISK08")))
					.risk09(trim(f.get("RISK09")))
					.raceInd(trim(f.get("RACE_IND")))
					.raceBla(trim(f.get("RACE_BLA")))
					.raceAsi(trim(f.get("RACE_ASI")))
					.racePac(trim(f.get("RACE_PAC")))
					.raceWhi(trim(f.get("RACE_WHI")))
					.raceMul(trim(f.get("RACE_MUL")))
					.raceOth(trim(f.get("RACE_OTH")))
					.raceUnk(trim(f.get("RACE_UNK")))
					.nycShipD(trim(f.get("NYC_SHIP_D")))
					.recvDt(trim(f.get("RECV_DT")))
					.nycnumber(trim(f.get("NYCNUMBER")))
					.uploadUid(workerId)
					.reviewedId(text(f.get("REVIEWED_ID")))
					.reviewedDate(date(f.get("REVIEWED_DATE")))
					.reviewStatus(text(f.get("REVIEW_STATUS")))
					.build());
		}
		for (NyehmsAdapNycFeedback feedbackRow : feedbackRows) {
			insert(feedbackRow);
		}
		return feedbackRows.size();
	}

	/** The ADAP_FILE of an ADAP document, as the former scalar subquery (null if none; more than one fails). */
	private String adapFileOf(Long documentId) {
		if (documentId == null) {
			return null;
		}
		List<String> files = stdDocs.findAdapFile(documentId);
		if (files.size() > 1) {
			throw new IncorrectResultSizeDataAccessException("More than one STD_ADAP_DOC row for " + documentId, 1, files.size());
		}
		return files.isEmpty() ? null : files.get(0);
	}

	/** The ADAP documents whose NYC feedback is not reviewed yet. */
	public List<Integer> getNCYADAPDocumentsToREview() {
		List<Integer> ids = new ArrayList<>();
		for (Long id : feedback.findUnreviewedDocumentIds()) {
			ids.add(id == null ? null : Math.toIntExact(id));
		}
		return ids;
	}

	/** The risks NYC returned for an ADAP document (null if there is none; several rows fail, as before). */
	public PersonRisksVO loadADAPRiskContentForNYCDocument(int documentId) {
		NyehmsAdapNycFeedback f = one(feedback.findByStdrptsId((long) documentId));
		if (f == null) {
			return null;
		}
		return Rows.bean(PersonRisksVO.class).map(new RowBuilder()
				.columns(f, NyehmsAdapNycFeedback.class, "RISK01 swm", "RISK02 swf", "RISK03 idu", "RISK04 sexPartnerIdu", "RISK05 sexPartnerHiv",
						"RISK06 recvBloodProd", "RISK07 perinatal", "RISK08 other", "RISK09 unknown")
				.build());
	}

	/** The risks of a standardized ADAP record (null if there is none). */
	public PersonRisksVO loadADAPRiskContentForNYEHMSDocument(int documentId) {
		StdAdapDoc doc = stdDocs.findById((long) documentId).orElse(null);
		if (doc == null) {
			return null;
		}
		return Rows.bean(PersonRisksVO.class).map(new RowBuilder()
				.columns(doc, StdAdapDoc.class, "SWM swm", "SWF swf", "IDU idu", "SEX_PARTNER_IDU sexPartnerIdu", "SEX_PARTNER_HIV sexPartnerHiv",
						"RECV_BLOOD_PROD recvBloodProd", "PERINATAL perinatal", "RISK_OTHER other", "RISK09 unknown", "RISK10 sexPartnerMsm",
						"RISK12 sexPartnerHemophiliac", "RISK13 sexPartnerTransfusion", "RISK14 sexPartnerTransplant", "RISK15 recvTransplant",
						"HEALTHCARE_WORKER healthCareWorker")
				.build());
	}

	/**
	 * Records the review of an ADAP document's NYC feedback not reviewed yet ({@code documentId}, {@code reviewCd},
	 * {@code cityno}, {@code reviewComments}, by {@code workerId}, now).
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateNYCADAPDocumentMatchStatus(Map<String, Object> inOutParameterMap) {
		Map<String, Object> m = inOutParameterMap;
		return feedback.review(text(m.get("workerId")), text(m.get("reviewCd")), text(m.get("cityno")), text(m.get("reviewComments")),
				longValue(m.get("documentId")));
	}

	/** The files all of whose NYC feedback is reviewed. */
	public List<String> getADAPNYCFileNames() {
		return feedback.findReviewedFileNames();
	}

	/** The NYC feedback of a file: STATENO, CITYNO (NYC's number), REVIEWED_CITYNO, REVIEW_STATUS, REVIEW_COMMENTS. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> generateNYCFileSummary(String fileName) {
		List<Map<String, String>> rows = new ArrayList<>();
		for (AdapNycFileSummaryRow r : feedback.findFileSummary(fileName)) {
			Map row = Rows.map().map(new RowBuilder().put("STATENO", r.stateno()).put("CITYNO", r.cityno())
					.put("REVIEWED_CITYNO", r.reviewedCityno()).put("REVIEW_STATUS", r.reviewStatus()).put("REVIEW_COMMENTS", r.reviewComments())
					.build());
			rows.add(row);
		}
		return rows;
	}

	/** The counts of a file's quality stage match codes (STATUS, COUNT; 'Total' for all). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> generateADAPNYCQualityStageMatchSummary(String fileName) {
		return (List) statements.list(QUALITY_STAGE_MATCH_SUMMARY, Rows.map(), fileName);
	}

	/**
	 * The number of NYC persons with the city number ({@code cityno}) that are the person the ADAP document
	 * ({@code documentId}) is associated with.
	 */
	public int compareNYCCitynoWithNYEHMS(Map<String, Object> inputParameterMap) {
		Long documentId = longValue(inputParameterMap.get("documentId"));
		return (int) nycPersons.countPersonsOfDocument(documentId == null ? null : Math.toIntExact(documentId),
				text(inputParameterMap.get("cityno")));
	}

	/** The ADAP NYC document screen: a NYC feedback row as an ADAP document (null if none; several rows fail). */
	public ADAPDocVO getADAPNYCDoc(int docId) {
		NyehmsAdapNycFeedback f = one(feedback.findByStdrptsId((long) docId));
		if (f == null) {
			return null;
		}
		return Rows.bean(ADAPDocVO.class).map(new RowBuilder()
				.columns(f, NyehmsAdapNycFeedback.class, "STDRPTS_ID DOCUMENT_ID", "STATENO MATCHED_STATENO", "SSN", "FNAME", "MNAME", "SUFFIX", "LNAME",
						"DOB DOB_TEXT", "GENDER CURRENT_GENDER", "RACE_CD", "ETHNICCD", "CITY PERSON_CITY", "STATE PERSON_STATE",
						"ZIP PERSON_ZIP", "COUNTY_RES PERSON_COUNTY", "ADDRESS PERSON_ADDRESS", "PROV_FNAME RX_PROV_FNAME",
						"PROV_MNAME RX_PROV_MNAME", "PROV_LNAME RX_PROV_LNAME", "PROV_CITY RX_PROV_CITY", "PROV_STATE RX_PROV_STATE",
						"PROV_ZIP RX_PROV_ZIP", "PROV_PLUS4 RX_PROV_PLUS4", "COUNTY_PRO RX_PROV_COUNTY", "PROV_ADDRE RX_PROV_ADDRESS",
						"PROV_PHONE RX_PROV_PHONE", "RACE_ASI RACE_ASIAN", "RACE_BLA RACE_BLACK", "RACE_IND RACE_INDIAN",
						"RACE_MUL RACE_MULTIPLE", "RACE_OTH RACE_OTHER", "RACE_PAC RACE_PACIFIC", "RACE_UNK RACE_UNKNOWN",
						"RACE_WHI RACE_WHITE", "RISK03 IDU", "RISK06 RECV_BLOOD_PROD", "RISK07 PERINATAL", "RISK04 SEX_PARTNER_IDU",
						"RISK01 SWM", "RISK02 SWF", "RISK05 SEX_PARTNER_HIV", "RISK08 RISK_OTHER", "MEDRECNO", "NYC_SHIP_D")
				.build());
	}

	/** The counts of a file's NYC feedback by review status (STATUS, COUNT); bug kept: see the class comment. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> generateADAPNYCShippedMatchSummary(String fileName) {
		return (List) statements.list(SHIPPED_MATCH_SUMMARY, Rows.map(), fileName);
	}

	/** The NYC extract of the records of {@code fileName} that NYC disagreed with (review status 'D'). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> generateADAPNYCDiscrepancyFile(Map<String, Object> inParameterMap) {
		return (List) statements.list(NYC_EXTRACT + """
				and exists   (select 1  from NYEHMS_ADAP_NYC_FEEDBACK where ADAP_FILE = ?1 and STDRPTS_ID = a.document_id and REVIEW_STATUS = 'D')
				""" + NYC_EXTRACT_END, Rows.map(), inParameterMap.get("fileName"));
	}

	/** 1 if a NYC person has the city number, else 0 (a null number: 0). */
	public int checkCityNoExists(String cityno) {
		return cityno != null && nycPersons.existsByCityno(cityno) ? 1 : 0;
	}

	/** Inserts an entity at once and detaches it (rows with a repeating declared id can follow; no stale reads). */
	private void insert(Object entity) {
		entityManager.persist(entity);
		entityManager.flush();
		entityManager.detach(entity);
	}

	private static <T> T one(List<T> rows) {
		if (rows.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, rows.size());
		}
		return rows.isEmpty() ? null : rows.get(0);
	}

	/** Oracle's ||: a NULL operand is an empty text, and the result is never empty here. */
	private static String oracleConcat(String a, String b) {
		String text = (a == null ? "" : a) + (b == null ? "" : b);
		return text.isEmpty() ? null : text;
	}

	/** Oracle's TRIM of a bound value: leading and trailing spaces removed, an empty result NULL. */
	private static String trim(Object value) {
		String text = text(value);
		if (text == null) {
			return null;
		}
		int start = 0, end = text.length();
		while (start < end && text.charAt(start) == ' ') {
			start++;
		}
		while (end > start && text.charAt(end - 1) == ' ') {
			end--;
		}
		return start == end ? null : text.substring(start, end);
	}

	/**
	 * A value written to a text column, as Oracle converted what MyBatis bound: text as it is ('' is NULL in Oracle),
	 * a number as its text (no trailing zeros, 0.5 as ".5"). A date would have been formatted with the session's NLS
	 * format, so it is refused.
	 */
	private static String text(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String text) {
			return text.isEmpty() ? null : text;
		}
		if (value instanceof java.util.Date) {
			throw new IllegalArgumentException("A date where text is expected: " + value);
		}
		if (value instanceof Number number) {
			String text = new BigDecimal(number.toString()).stripTrailingZeros().toPlainString();
			if (text.startsWith("0.")) {
				return text.substring(1);
			}
			return text.startsWith("-0.") ? "-" + text.substring(2) : text;
		}
		return value.toString();
	}

	/**
	 * A value written to a date column as MyBatis bound #{x,jdbcType=DATE}: a java.util.Date (the class itself) and a
	 * java.sql.Date as their day only, a Timestamp with its time; null and '' as null.
	 */
	private static Timestamp date(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		if (value.getClass() == java.util.Date.class || value instanceof java.sql.Date) {
			return Timestamp.valueOf(new java.sql.Date(((java.util.Date) value).getTime()).toLocalDate().atStartOfDay());
		}
		if (value instanceof java.util.Date d) {
			return new Timestamp(d.getTime());
		}
		throw new IllegalArgumentException("Not a date: " + value);
	}

	/** A value written to a number column: a number as it is, a text as the number it holds; null or '' as null. */
	private static BigDecimal decimal(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		return value instanceof BigDecimal number ? number : new BigDecimal(value.toString().trim());
	}

	/** A whole-number column (a fraction fails rather than being cut). */
	private static Integer integer(Object value) {
		BigDecimal number = decimal(value);
		return number == null ? null : number.intValueExact();
	}

	/** A whole-number column (a fraction fails rather than being cut). */
	private static Long longValue(Object value) {
		BigDecimal number = decimal(value);
		return number == null ? null : number.longValueExact();
	}
}
