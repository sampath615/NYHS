package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.timestamp;

import java.util.Map;

import org.hibernate.type.StandardBasicTypes;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;

/**
 * The SQL of the {@link DocumentEditLabsDAO} statements that stay native, taken unchanged from the former MyBatis mapper
 * ({@code #{...}} replaced by {@code ?1, ?2, ...}; generated from the mapper XML); why each one stays native is said at
 * its DAO method. The {@code ...Args} methods give the values bound to a statement, in order, typed as the mapper's
 * {@code jdbcType} bound a null ({@code VARCHAR} and {@code CLOB} a text, {@code DECIMAL} a number, {@code DATE} a date,
 * {@code TIMESTAMP} a timestamp); a value that is not null is bound as it is, as MyBatis bound it by its Java type (a
 * {@code java.util.Date} with {@code jdbcType=DATE} as its day only, as MyBatis' DateOnlyTypeHandler did). A value without
 * a jdbcType is bound as it is. A statement with one simple parameter binds it as {@code ?1} wherever the mapper named it.
 */
final class DocumentEditLabsSql {

	private DocumentEditLabsSql() {
	}

	/**
	 * A value bound as {@code #{x,jdbcType=DATE}}: a {@code java.util.Date} (the class itself) as its day only (MyBatis'
	 * DateOnlyTypeHandler, setDate), any other value as it is (a Timestamp keeps its time), null as a DATE null.
	 */
	static Param date(Object value) {
		if (value != null && value.getClass() == java.util.Date.class) {
			return NativeStatements.param(new java.sql.Date(((java.util.Date) value).getTime()), StandardBasicTypes.DATE);
		}
		return NativeStatements.param(value, StandardBasicTypes.DATE);
	}

	/** The SQL of {@code insertRawEditedTbDocument}, as the former mapper had it (57 bound values). */
	static final String RAW_EDITED_TB_DOCUMENT = """
			insert into RAW_TB_DOC(PROVIDERSTATECODE, STATUSATDIAGNOSIS, HOSP_PFI, DIN, STREET, FACILITY_ADDRESS, PROVIDERCITY, ATYPICAL, DISTYPE, OI_HIST, ZIPCODE, FACILITY_ZIP, PROVIDERFNAME, PROVIDERLNAME, PLUS4, FACILITY_PLUS4, LOCALITY, FACILITY_CITY, HOSP_CHART, COUNTY, FACILITY_COUNTY, TB_FLAG_HIST, AREA_CD, LASTNAME, MI, PROVIDERADDRESS, FIRSTNAME, PHONE, FACILITY_PHONE, CASESTATUS, ETHNICITY_CD, RACE_CD, STATE, FACILITY_STATE, RACE_ASIAN_CD, RACE_POLY_CD, PROVIDERPHONE, FACILITY_NAME, DOB_DATE, PROVIDERZIPCODE, DEATH_DATE, TB_FILE, SEX, RACE_ASIAN, RACE_BLACK, RACE_AMERIND, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, CLINICALMANAGEA, DOCUMENT_ID, SERIALNO, CURRENT_PROCESS_STATUS, RECEIVED_DATE, DIAGNOSIS_DATE, PROCESS_DATE)
			values(upper(trim(?1)), upper(trim(?2)), upper(trim(?3)), upper(trim(?4)), upper(trim(?5)), upper(trim(?6)), upper(trim(?7)), upper(trim(?8)), upper(trim(?9)), upper(trim(?10)), upper(trim(?11)), upper(trim(?12)), upper(trim(?13)), upper(trim(?14)), upper(trim(?15)), upper(trim(?16)), upper(trim(?17)), upper(trim(?18)), upper(trim(?19)), upper(trim(?20)), upper(trim(?21)), upper(trim(?22)), upper(trim(?23)), upper(trim(?24)), upper(trim(?25)), upper(trim(?26)), upper(trim(?27)), upper(trim(?28)), upper(trim(?29)), upper(trim(?30)), upper(trim(?31)), upper(trim(?32)), upper(trim(?33)), upper(trim(?34)), upper(trim(?35)), upper(trim(?36)), upper(trim(?37)), upper(trim(?38)), upper(trim(?39)), upper(trim(?40)), upper(trim(?41)), ?42, upper(trim(?43)), upper(trim(?44)), upper(trim(?45)), upper(trim(?46)), upper(trim(?47)), upper(trim(?48)), upper(trim(?49)), upper(trim(?50)), upper(trim(?51)), ?52, ?53, ?54, ?55, FN_TO_DATE(?56,'MM/DD/YYYY'), ?57)
			""";

	/** The values of {@link #RAW_EDITED_TB_DOCUMENT}, in order. */
	static Object[] insertRawEditedTbDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("PROVIDERSTATECODE")), text(map.get("STATUSATDIAGNOSIS")), text(map.get("HOSP_PFI")), text(map.get("DIN")),
				text(map.get("STREET")), text(map.get("FACILITY_ADDRESS")), text(map.get("PROVIDERCITY")), text(map.get("ATYPICAL")),
				text(map.get("DISTYPE")), text(map.get("OI_HIST")), text(map.get("ZIPCODE")), text(map.get("FACILITY_ZIP")),
				text(map.get("PROVIDERFNAME")), text(map.get("PROVIDERLNAME")), text(map.get("PLUS4")), text(map.get("FACILITY_PLUS4")),
				text(map.get("LOCALITY")), text(map.get("FACILITY_CITY")), text(map.get("HOSP_CHART")), text(map.get("COUNTY")),
				text(map.get("FACILITY_COUNTY")), text(map.get("TB_FLAG_HIST")), text(map.get("AREA_CD")), text(map.get("LASTNAME")),
				text(map.get("MI")), text(map.get("PROVIDERADDRESS")), text(map.get("FIRSTNAME")), text(map.get("PHONE")),
				text(map.get("FACILITY_PHONE")), text(map.get("CASESTATUS")), text(map.get("ETHNICITY_CD")), text(map.get("RACE_CD")),
				text(map.get("STATE")), text(map.get("FACILITY_STATE")), text(map.get("RACE_ASIAN_CD")), text(map.get("RACE_POLY_CD")),
				text(map.get("PROVIDERPHONE")), text(map.get("FACILITY_NAME")), text(map.get("DOB_DATE")), text(map.get("PROVIDERZIPCODE")),
				text(map.get("DEATH_DATE")), text(map.get("TB_FILE")), text(map.get("SEX")), text(map.get("RACE_ASIAN")),
				text(map.get("RACE_BLACK")), text(map.get("RACE_AMERIND")), text(map.get("RACE_OTHER")), text(map.get("RACE_PACIFIC")),
				text(map.get("RACE_UNKNOWN")), text(map.get("RACE_WHITE")), text(map.get("CLINICALMANAGEA")), number(map.get("DOCUMENT_ID")),
				number(map.get("SERIALNO")), number(map.get("CURRENT_PROCESS_STATUS")), date(map.get("RECEIVED_DATE")), date(map.get("DIAGNOSIS_DATE")),
				date(map.get("PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedTbDocument}, as the former mapper had it (83 bound values). */
	static final String STD_EDITED_TB_DOCUMENT = """
			insert into STD_TB_DOC(VITAL_STATUS_AT_DIAGNOSIS, HOSP_PFI, DIN, PERSON_ADDRESS, FACILITY_ADDRESS, PROV_CITY, PERSON_CENSUS_TRACT, FACILITY_CENSUS_TRACT, PROV_CENSUS_TRACT, ATYPICAL_MYCO, DISEASE_TYPE, PERSON_ZIP, FACILITY_ZIP, PROV_ZIP, PROV_FNAME, PROV_LNAME, MATCH_CD, REVIEW_UID, EHARS_STATUS_CD, PERSON_PLUS4, FACILITY_PLUS4, MATCH_PASS, PROV_PLUS4, PERSON_CITY, FACILITY_CITY, HOSP_CHART, PERSON_COUNTY, FACILITY_COUNTY, PROV_COUNTY, LNAME, MI, PROV_ADDRESS, RAW_PROVIDER_ADDRESS, FNAME, PERSON_PHONE, FACILITY_PHONE, CASE_STATUS, ETHNICITY, PERSON_STATE, FACILITY_STATE, PROV_STATE, RACE_CD, RACE_ASIAN_CD, RACE_POLY_CD, PROV_PHONE, FACILITY_NAME, PERSON_LONGITUDE, FACILITY_LONGITUDE, PROV_LONGITUDE, DOB_TEXT, PERSON_LATITUDE, FACILITY_LATITUDE, MATCHED_STATENO, CREATE_UID, DOD_TEXT, TB_FILE, PROV_LATITUDE, BIRTH_SEX, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, HIV_IND, MATCH_WEIGHT, DOCUMENT_ID, SERIALNO, CURRENT_PROCESS_STATUS, QS_PARENT_DOC_ID, RECEIVED_DATE, REVIEW_DATE, EHARS_IMPORT_DATE, DX_DATE, CREATE_DATE, NYC_SHIP_DATE, MATCHED_PROCESS_DATE,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			values(upper(trim(?1)), upper(trim(?2)), upper(trim(?3)), upper(trim(?4)), upper(trim(?5)), upper(trim(?6)), upper(trim(?7)), upper(trim(?8)), upper(trim(?9)), upper(trim(?10)), upper(trim(?11)), upper(trim(?12)), upper(trim(?13)), upper(trim(?14)), upper(trim(?15)), upper(trim(?16)), upper(trim(?17)), upper(trim(?18)), upper(trim(?19)), upper(trim(?20)), upper(trim(?21)), upper(trim(?22)), upper(trim(?23)), upper(trim(?24)), upper(trim(?25)), upper(trim(?26)), upper(trim(?27)), upper(trim(?28)), upper(trim(?29)), upper(trim(?30)), upper(trim(?31)), upper(trim(?32)), upper(trim(?33)), upper(trim(?34)), upper(trim(?35)), upper(trim(?36)), upper(trim(?37)), upper(trim(?38)), upper(trim(?39)), upper(trim(?40)), upper(trim(?41)), upper(trim(?42)), upper(trim(?43)), upper(trim(?44)), upper(trim(?45)), upper(trim(?46)), upper(trim(?47)), upper(trim(?48)), upper(trim(?49)), upper(trim(?50)), upper(trim(?51)), upper(trim(?52)), upper(trim(?53)), upper(trim(?54)), upper(trim(?55)), ?56, upper(trim(?57)), upper(trim(?58)), upper(trim(?59)), upper(trim(?60)), upper(trim(?61)), upper(trim(?62)), upper(trim(?63)), upper(trim(?64)), upper(trim(?65)), upper(trim(?66)), ?67, ?68, ?69, ?70, ?71, ?72, ?73, ?74, FN_TO_DATE(?75,'MM/DD/YYYY'),?76, ?77, ?78,?79,?80,?81,?82,?83)
			""";

	/** The values of {@link #STD_EDITED_TB_DOCUMENT}, in order. */
	static Object[] insertStdEditedTbDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("VITAL_STATUS_AT_DIAGNOSIS")), text(map.get("HOSP_PFI")), text(map.get("DIN")), text(map.get("PERSON_ADDRESS")),
				text(map.get("FACILITY_ADDRESS")), text(map.get("PROV_CITY")), text(map.get("PERSON_CENSUS_TRACT")), text(map.get("FACILITY_CENSUS_TRACT")),
				text(map.get("PROV_CENSUS_TRACT")), text(map.get("ATYPICAL_MYCO")), text(map.get("DISEASE_TYPE")), text(map.get("PERSON_ZIP")),
				text(map.get("FACILITY_ZIP")), text(map.get("PROV_ZIP")), text(map.get("PROV_FNAME")), text(map.get("PROV_LNAME")),
				text(map.get("MATCH_CD")), text(map.get("REVIEW_UID")), text(map.get("EHARS_STATUS_CD")), text(map.get("PERSON_PLUS4")),
				text(map.get("FACILITY_PLUS4")), text(map.get("MATCH_PASS")), text(map.get("PROV_PLUS4")), text(map.get("PERSON_CITY")),
				text(map.get("FACILITY_CITY")), text(map.get("HOSP_CHART")), text(map.get("PERSON_COUNTY")), text(map.get("FACILITY_COUNTY")),
				text(map.get("PROV_COUNTY")), text(map.get("LNAME")), text(map.get("MI")), text(map.get("PROV_ADDRESS")),
				text(map.get("RAW_PROVIDER_ADDRESS")), text(map.get("FNAME")), text(map.get("PERSON_PHONE")), text(map.get("FACILITY_PHONE")),
				text(map.get("CASE_STATUS")), text(map.get("ETHNICITY")), text(map.get("PERSON_STATE")), text(map.get("FACILITY_STATE")),
				text(map.get("PROV_STATE")), text(map.get("RACE_CD")), text(map.get("RACE_ASIAN_CD")), text(map.get("RACE_POLY_CD")),
				text(map.get("PROV_PHONE")), text(map.get("FACILITY_NAME")), text(map.get("PERSON_LONGITUDE")), text(map.get("FACILITY_LONGITUDE")),
				text(map.get("PROV_LONGITUDE")), text(map.get("DOB_TEXT")), text(map.get("PERSON_LATITUDE")), text(map.get("FACILITY_LATITUDE")),
				text(map.get("MATCHED_STATENO")), text(map.get("CREATE_UID")), text(map.get("DOD_TEXT")), text(map.get("TB_FILE")),
				text(map.get("PROV_LATITUDE")), text(map.get("BIRTH_SEX")), text(map.get("RACE_ASIAN")), text(map.get("RACE_BLACK")),
				text(map.get("RACE_INDIAN")), text(map.get("RACE_OTHER")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_UNKNOWN")),
				text(map.get("RACE_WHITE")), text(map.get("HIV_IND")), number(map.get("MATCH_WEIGHT")), number(map.get("DOCUMENT_ID")),
				number(map.get("SERIALNO")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("QS_PARENT_DOC_ID")), date(map.get("RECEIVED_DATE")),
				date(map.get("REVIEW_DATE")), date(map.get("EHARS_IMPORT_DATE")), date(map.get("DX_DATE")), date(map.get("CREATE_DATE")),
				date(map.get("NYC_SHIP_DATE")), date(map.get("MATCHED_PROCESS_DATE")), text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")),
				text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedAirsDocument}, as the former mapper had it (112 bound values). */
	static final String RAW_EDITED_AIRS_DOCUMENT = """
			INSERT INTO RAW_AIRS_DOC(AIRS_VERSION, EXTRALOGID, GENDER, I_ILL, PREV_I_ILL, D_DIAG, PREV_D_DIAG, RACE_INDIAN, RACE_BLACK, RACE_ASIAN, RACE_PACIFIC, RACE_WHITE, RACE_OTHER, RACE_UNKNOWN, RECORD_TYPE, CTR_FLAG, TC_ID, DOB, I_DIAG_DT, INFORMED_OF_DIAG_DT, PREV_I_DIAG_DT, D_DIAG_DT, PREV_D_DIAG_DT, SIG_DT, ADMISS_DT, DOD_TEXT, DATE_RECV_BLD_PROD, DT_PT_INTERVIEW, DT_FST_POS_HIV, ARV_6_MO_START, ARV_6_MO_END, DT_FST_HIV_EVER, DT_LAST_NEGATIVE, CREATE_UID, DT_CREATED, DT_MODIFIED, CSV_FILENAME, STATE, INFORMED_OF_DIAG, ARV_6_MO, ARV_CURRENT_TEST, EVER_TEST_NEGATIVE, AIRS_STATUS_CD, ARV1_CODE, ARV2_CODE, ARV3_CODE, PROV_ST, SEX, PROVIDER_CD, AGE, NUM_PARTNERS, NUMBER_TEST_2_YRS, CITY, PROV_CITY, AGENCY_ID, ZIPCODE, PROV_ZIP, URN, SSN, CREATE_DATE, PROCESS_DATE, DOCUMENT_ID, ID, AIRS_SRC_ID, CURRENT_PROCESS_STATUS, HASH_UNIQUE_NUMBER, CASE_NO, PROVNAME, ETHNICCD, RISK01, RISK02, RISK03, RISK04, RISK05, RISK06, RISK07, RISK08, RISK09, FNAME, MEDRECNO, TELEPHONE, PROV_PHONE, AKA, FED_ID, LNAME, MNAME, IS_MEDICAID, ARV_6_MO_NAME4, BIRTH_COUNTRY, COUNTRY_BIRTH, ARV_6_MO_NAME, ARV_6_MO_NAME2, ARV_6_MO_NAME3, EUCI, PROVIDER_DESCRIPTION, ADDRESS, PROV_ADDR, MEDICAIDNO, DUNS_ID, CTR_VERSION, CTR_FORMID, HISPANIC_DET, BLACK_DET, ASIAN_DET, HAWAIIAN_DET, WHITE_DET, GENDER_WRITEIN, INITIAL_SELF_POS, INITIAL_SELF_POS_DT, RECENT_SELF_NEG, RECENT_SELF_NEG_DT, SEX_AT_BIRTH, CTR_PRELIMINARY_POSITIVE)
			 values(trim(upper(?1)),trim(upper(?2)),trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),trim(upper(?6)),trim(upper(?7)),trim(upper(?8)),trim(upper(?9)),trim(upper(?10)),trim(upper(?11)),trim(upper(?12)),trim(upper(?13)),trim(upper(?14)),trim(upper(?15)),trim(upper(?16)),trim(upper(?17)),trim(upper(?18)),trim(upper(?19)),trim(upper(?20)),trim(upper(?21)),trim(upper(?22)),trim(upper(?23)),trim(upper(?24)),trim(upper(?25)),trim(upper(?26)),trim(upper(?27)),trim(upper(?28)),trim(upper(?29)),trim(upper(?30)),trim(upper(?31)),trim(upper(?32)),trim(upper(?33)),trim(upper(?34)),trim(upper(?35)),trim(upper(?36)),?37,trim(upper(?38)),trim(upper(?39)),trim(upper(?40)),trim(upper(?41)),trim(upper(?42)),trim(upper(?43)),trim(upper(?44)),trim(upper(?45)),trim(upper(?46)),trim(upper(?47)),trim(upper(?48)),trim(upper(?49)),trim(upper(?50)),trim(upper(?51)),trim(upper(?52)),trim(upper(?53)),trim(upper(?54)),trim(upper(?55)),trim(upper(?56)),trim(upper(?57)),trim(upper(?58)),trim(upper(?59)),?60,SYSDATE,?61,?62,?63,?64,?65,trim(upper(?66)),trim(upper(?67)),trim(upper(?68)),trim(upper(?69)),trim(upper(?70)),trim(upper(?71)),trim(upper(?72)),trim(upper(?73)),trim(upper(?74)),trim(upper(?75)),trim(upper(?76)),trim(upper(?77)),trim(upper(?78)),trim(upper(?79)),trim(upper(?80)),trim(upper(?81)),trim(upper(?82)),trim(upper(?83)),trim(upper(?84)),trim(upper(?85)),trim(upper(?86)),trim(upper(?87)),trim(upper(?88)),trim(upper(?89)),trim(upper(?90)),trim(upper(?91)),trim(upper(?92)),trim(upper(?93)),trim(upper(?94)),trim(upper(?95)),trim(upper(?96)),trim(upper(?97)),trim(upper(?98)),trim(upper(?99)),trim(upper(?100)),trim(upper(?101)),trim(upper(?102)),trim(upper(?103)),trim(upper(?104)),trim(upper(?105)),trim(upper(?106)),trim(upper(?107)),trim(upper(?108)),trim(upper(?109)),trim(upper(?110)),trim(upper(?111)), ?112)
			""";

	/** The values of {@link #RAW_EDITED_AIRS_DOCUMENT}, in order. */
	static Object[] insertRawEditedAirsDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("AIRS_VERSION")), text(map.get("EXTRALOGID")), text(map.get("GENDER")), text(map.get("I_ILL")),
				text(map.get("PREV_I_ILL")), text(map.get("D_DIAG")), text(map.get("PREV_D_DIAG")), text(map.get("RACE_INDIAN")),
				text(map.get("RACE_BLACK")), text(map.get("RACE_ASIAN")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_WHITE")),
				text(map.get("RACE_OTHER")), text(map.get("RACE_UNKNOWN")), text(map.get("RECORD_TYPE")), text(map.get("CTR_FLAG")),
				text(map.get("TC_ID")), text(map.get("DOB")), text(map.get("I_DIAG_DT")), text(map.get("INFORMED_OF_DIAG_DT")),
				text(map.get("PREV_I_DIAG_DT")), text(map.get("D_DIAG_DT")), text(map.get("PREV_D_DIAG_DT")), text(map.get("SIG_DT")),
				text(map.get("ADMISS_DT")), text(map.get("DOD_TEXT")), text(map.get("DATE_RECV_BLD_PROD")), text(map.get("DT_PT_INTERVIEW")),
				text(map.get("DT_FST_POS_HIV")), text(map.get("ARV_6_MO_START")), text(map.get("ARV_6_MO_END")), text(map.get("DT_FST_HIV_EVER")),
				text(map.get("DT_LAST_NEGATIVE")), text(map.get("CREATE_UID")), text(map.get("DT_CREATED")), text(map.get("DT_MODIFIED")),
				text(map.get("CSV_FILENAME")), text(map.get("STATE")), text(map.get("INFORMED_OF_DIAG")), text(map.get("ARV_6_MO")),
				text(map.get("ARV_CURRENT_TEST")), text(map.get("EVER_TEST_NEGATIVE")), text(map.get("AIRS_STATUS_CD")), text(map.get("ARV1_CODE")),
				text(map.get("ARV2_CODE")), text(map.get("ARV3_CODE")), text(map.get("PROV_ST")), text(map.get("SEX")),
				text(map.get("PROVIDER_CD")), text(map.get("AGE")), text(map.get("NUM_PARTNERS")), text(map.get("NUMBER_TEST_2_YRS")),
				text(map.get("CITY")), text(map.get("PROV_CITY")), text(map.get("AGENCY_ID")), text(map.get("ZIPCODE")),
				text(map.get("PROV_ZIP")), text(map.get("URN")), text(map.get("SSN")), date(map.get("CREATE_DATE")),
				number(map.get("DOCUMENT_ID")), number(map.get("ID")), number(map.get("AIRS_SRC_ID")), number(map.get("CURRENT_PROCESS_STATUS")),
				number(map.get("HASH_UNIQUE_NUMBER")), text(map.get("CASE_NO")), text(map.get("PROVNAME")), text(map.get("ETHNICCD")),
				text(map.get("RISK01")), text(map.get("RISK02")), text(map.get("RISK03")), text(map.get("RISK04")),
				text(map.get("RISK05")), text(map.get("RISK06")), text(map.get("RISK07")), text(map.get("RISK08")),
				text(map.get("RISK09")), text(map.get("FNAME")), text(map.get("MEDRECNO")), text(map.get("TELEPHONE")),
				text(map.get("PROV_PHONE")), text(map.get("AKA")), text(map.get("FED_ID")), text(map.get("LNAME")),
				text(map.get("MNAME")), text(map.get("IS_MEDICAID")), text(map.get("ARV_6_MO_NAME4")), text(map.get("BIRTH_COUNTRY")),
				text(map.get("COUNTRY_BIRTH")), text(map.get("ARV_6_MO_NAME")), text(map.get("ARV_6_MO_NAME2")), text(map.get("ARV_6_MO_NAME3")),
				text(map.get("EUCI")), text(map.get("PROVIDER_DESCRIPTION")), text(map.get("ADDRESS")), text(map.get("PROV_ADDR")),
				text(map.get("MEDICAIDNO")), text(map.get("DUNS_ID")), text(map.get("CTR_VERSION")), text(map.get("CTR_FORMID")),
				text(map.get("HISPANIC_DET")), text(map.get("BLACK_DET")), text(map.get("ASIAN_DET")), text(map.get("HAWAIIAN_DET")),
				text(map.get("WHITE_DET")), text(map.get("GENDER_WRITEIN")), text(map.get("INITIAL_SELF_POS")), text(map.get("INITIAL_SELF_POS_DT")),
				text(map.get("RECENT_SELF_NEG")), text(map.get("RECENT_SELF_NEG_DT")), text(map.get("SEX_AT_BIRTH")), text(map.get("CTR_PRELIMINARY_POSITIVE"))
		};
	}

	/** The SQL of {@code insertStdEditedAirsDocument}, as the former mapper had it (127 bound values). */
	static final String STD_EDITED_AIRS_DOCUMENT = """
			INSERT INTO STD_AIRS_DOC(CURRENT_GENDER, RECORD_TYPE, CTR_FLAG, BIRTH_SEX, EHARS_STATUS_CD, MATCHED_STATENO, TC_ID, PERSON_LATITUDE, FACILITY_LATITUDE, SIG_DATE_TEXT, ADMISSION_DATE_TEXT, DOD_TEXT, RECV_BLOOD_PROD_DATE_TEXT, INIT_HIV_DIAG_DATE_TEXT, INFORMED_HIV_DATE_TEXT, PREV_HIV_DIAG_DATE_TEXT, INIT_AIDS_DIAG_DATE_TEXT, PREV_AIDS_DIAG_DATE_TEXT, ARV_6_MO_START_DATE_TEXT, ARV_6_MO_END_DATE_TEXT, FIRST_HIV_TEST_EVER_DATE_TEXT, LAST_NEG_DATE_TEXT, CREATE_UID, PERSON_LONGITUDE, FACILITY_LONGITUDE, PERSON_STATE, FACILITY_STATE, ETHNICITY, ARV_6_MO, ARV1_CODE, ARV2_CODE, ARV3_CODE, ARV_CURRENT_TEST, EVER_TEST_NEG, PROVIDER_CD, PERSON_COUNTY, FACILITY_COUNTY, NUMBER_TEST_2_YRS, PERSON_CITY, FACILITY_CITY, PERSON_PLUS4, FACILITY_PLUS4, PERSON_ZIP, FACILITY_ZIP, PERSON_CENSUS_TRACT, FACILITY_CENSUS_TRACT, REVIEW_UID, URN, AGENCY_ID, SSN, EHARS_IMPORT_DATE, DOB, PT_INTERVIEW_DATE, FIRST_POS_HIV_DATE, FILE_CREATED_DATE, PAT_REC_MOD_DATE, REVIEW_DATE, CREATE_DATE, NYC_SHIP_DATE, DOCUMENT_ID, AIRS_SRC_ID, AIRS_ID, RACE_INDIAN, RACE_BLACK, RACE_ASIAN, RACE_PACIFIC, RACE_WHITE, RACE_OTHER, RACE_UNKNOWN, NUM_PARTNERS, INIT_HIV_DIAG, INFORMED_HIV_POS, PREV_HIV_DIAG, INIT_AIDS_DIAG, PREV_AIDS_DIAG, CURRENT_PROCESS_STATUS, QS_PARENT_DOC_ID, MATCH_WEIGHT, AGE, CASE_NO, SWM, SWF, IDU, SEX_PARTNER_IDU, SEX_PARTNER_HIV, RECV_BLOOD_PROD, PERINATAL, RISK_OTHER, RISK_UNKNOWN, FNAME, MED_RECORD_NUMBER, PERSON_PHONE, FACILITY_PHONE, FED_ID, EHARS_AIRS_UID, LNAME, MNAME, ALT_NAME, IS_MEDICAID, MATCH_CD, ARV_6_MO_NAME4, MATCH_PASS, BIRTH_COUNTRY, BIRTH_COUNTRY_OTHER, ARV_6_MO_NAME, ARV_6_MO_NAME2, ARV_6_MO_NAME3, EUCI, PROVIDER_DESCRIPTION, PERSON_ADDRESS, FACILITY_ADDRESS, MEDICAID_NUMBER, FACILITY_NAME, DUNS_ID, MATCHED_PROCESS_DATE, AIRS_CALC_FILE_DATE, AIRS_VERSION, EXTRALOGID ,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE, CTR_VERSION, CTR_FORMID, PERSON_PHONE_TEXT, CTR_PRELIMINARY_POSITIVE)
			 values(trim(upper(?1)),trim(upper(?2)),trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),trim(upper(?6)),trim(upper(?7)),trim(upper(?8)),trim(upper(?9)),trim(upper(?10)),trim(upper(?11)),trim(upper(?12)),trim(upper(?13)),trim(upper(?14)),trim(upper(?15)),trim(upper(?16)),trim(upper(?17)),trim(upper(?18)),trim(upper(?19)),trim(upper(?20)),trim(upper(?21)),trim(upper(?22)),trim(upper(?23)),trim(upper(?24)),trim(upper(?25)),trim(upper(?26)),trim(upper(?27)),trim(upper(?28)),trim(upper(?29)),trim(upper(?30)),trim(upper(?31)),trim(upper(?32)),trim(upper(?33)),trim(upper(?34)),trim(upper(?35)),trim(upper(?36)),trim(upper(?37)),trim(upper(?38)),trim(upper(?39)),trim(upper(?40)),trim(upper(?41)),trim(upper(?42)),trim(upper(?43)),trim(upper(?44)),trim(upper(?45)),trim(upper(?46)),trim(upper(?47)),trim(upper(?48)),trim(upper(?49)),trim(upper(?50)),?51,FN_TO_DATE(?52,'MM/DD/YYYY'),FN_TO_DATE(?53,'MM/DD/YYYY'),FN_TO_DATE(?54,'MM/DD/YYYY'),?55,?56,?57,?58,?59,?60,?61,?62,?63,?64,?65,?66,?67,?68,?69,?70,?71,?72,?73,?74,?75,?76,?77,?78,?79,trim(upper(?80)),trim(upper(?81)),trim(upper(?82)),trim(upper(?83)),trim(upper(?84)),trim(upper(?85)),trim(upper(?86)),trim(upper(?87)),trim(upper(?88)),trim(upper(?89)),trim(upper(?90)),trim(upper(?91)),trim(upper(?92)),trim(upper(?93)),trim(upper(?94)),trim(upper(?95)),trim(upper(?96)),trim(upper(?97)),trim(upper(?98)),trim(upper(?99)),trim(upper(?100)),trim(upper(?101)),trim(upper(?102)),trim(upper(?103)),trim(upper(?104)),trim(upper(?105)),trim(upper(?106)),trim(upper(?107)),trim(upper(?108)),trim(upper(?109)),trim(upper(?110)),trim(upper(?111)),trim(upper(?112)),trim(upper(?113)),trim(upper(?114)),?115,?116,trim(upper(?117)),trim(upper(?118)),?119,?120,?121,?122,?123,trim(upper(?124)),trim(upper(?125)),trim(upper(?126)), ?127)
			""";

	/** The values of {@link #STD_EDITED_AIRS_DOCUMENT}, in order. */
	static Object[] insertStdEditedAirsDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("CURRENT_GENDER")), text(map.get("RECORD_TYPE")), text(map.get("CTR_FLAG")), text(map.get("BIRTH_SEX")),
				text(map.get("EHARS_STATUS_CD")), text(map.get("MATCHED_STATENO")), text(map.get("TC_ID")), text(map.get("PERSON_LATITUDE")),
				text(map.get("FACILITY_LATITUDE")), text(map.get("SIG_DATE_TEXT")), text(map.get("ADMISSION_DATE_TEXT")), text(map.get("DOD_TEXT")),
				text(map.get("RECV_BLOOD_PROD_DATE_TEXT")), text(map.get("INIT_HIV_DIAG_DATE_TEXT")), text(map.get("INFORMED_HIV_DATE_TEXT")), text(map.get("PREV_HIV_DIAG_DATE_TEXT")),
				text(map.get("INIT_AIDS_DIAG_DATE_TEXT")), text(map.get("PREV_AIDS_DIAG_DATE_TEXT")), text(map.get("ARV_6_MO_START_DATE_TEXT")), text(map.get("ARV_6_MO_END_DATE_TEXT")),
				text(map.get("FIRST_HIV_TEST_EVER_DATE_TEXT")), text(map.get("LAST_NEG_DATE_TEXT")), text(map.get("CREATE_UID")), text(map.get("PERSON_LONGITUDE")),
				text(map.get("FACILITY_LONGITUDE")), text(map.get("PERSON_STATE")), text(map.get("FACILITY_STATE")), text(map.get("ETHNICITY")),
				text(map.get("ARV_6_MO")), text(map.get("ARV1_CODE")), text(map.get("ARV2_CODE")), text(map.get("ARV3_CODE")),
				text(map.get("ARV_CURRENT_TEST")), text(map.get("EVER_TEST_NEG")), text(map.get("PROVIDER_CD")), text(map.get("PERSON_COUNTY")),
				text(map.get("FACILITY_COUNTY")), text(map.get("NUMBER_TEST_2_YRS")), text(map.get("PERSON_CITY")), text(map.get("FACILITY_CITY")),
				text(map.get("PERSON_PLUS4")), text(map.get("FACILITY_PLUS4")), text(map.get("PERSON_ZIP")), text(map.get("FACILITY_ZIP")),
				text(map.get("PERSON_CENSUS_TRACT")), text(map.get("FACILITY_CENSUS_TRACT")), text(map.get("REVIEW_UID")), text(map.get("URN")),
				text(map.get("AGENCY_ID")), text(map.get("SSN")), date(map.get("EHARS_IMPORT_DATE")), date(map.get("DOB")),
				date(map.get("PT_INTERVIEW_DATE")), date(map.get("FIRST_POS_HIV_DATE")), date(map.get("FILE_CREATED_DATE")), date(map.get("PAT_REC_MOD_DATE")),
				date(map.get("REVIEW_DATE")), date(map.get("CREATE_DATE")), date(map.get("NYC_SHIP_DATE")), number(map.get("DOCUMENT_ID")),
				number(map.get("AIRS_SRC_ID")), number(map.get("AIRS_ID")), number(map.get("RACE_INDIAN")), number(map.get("RACE_BLACK")),
				number(map.get("RACE_ASIAN")), number(map.get("RACE_PACIFIC")), number(map.get("RACE_WHITE")), number(map.get("RACE_OTHER")),
				number(map.get("RACE_UNKNOWN")), text(map.get("NUM_PARTNERS")), number(map.get("INIT_HIV_DIAG")), number(map.get("INFORMED_HIV_POS")),
				number(map.get("PREV_HIV_DIAG")), number(map.get("INIT_AIDS_DIAG")), number(map.get("PREV_AIDS_DIAG")), number(map.get("CURRENT_PROCESS_STATUS")),
				number(map.get("QS_PARENT_DOC_ID")), number(map.get("MATCH_WEIGHT")), number(map.get("AGE")), text(map.get("CASE_NO")),
				text(map.get("SWM")), text(map.get("SWF")), text(map.get("IDU")), text(map.get("SEX_PARTNER_IDU")),
				text(map.get("SEX_PARTNER_HIV")), text(map.get("RECV_BLOOD_PROD")), text(map.get("PERINATAL")), text(map.get("RISK_OTHER")),
				text(map.get("RISK_UNKNOWN")), text(map.get("FNAME")), text(map.get("MED_RECORD_NUMBER")), text(map.get("PERSON_PHONE")),
				text(map.get("FACILITY_PHONE")), text(map.get("FED_ID")), text(map.get("EHARS_AIRS_UID")), text(map.get("LNAME")),
				text(map.get("MNAME")), text(map.get("ALT_NAME")), text(map.get("IS_MEDICAID")), text(map.get("MATCH_CD")),
				text(map.get("ARV_6_MO_NAME4")), text(map.get("MATCH_PASS")), text(map.get("BIRTH_COUNTRY")), text(map.get("BIRTH_COUNTRY_OTHER")),
				text(map.get("ARV_6_MO_NAME")), text(map.get("ARV_6_MO_NAME2")), text(map.get("ARV_6_MO_NAME3")), text(map.get("EUCI")),
				text(map.get("PROVIDER_DESCRIPTION")), text(map.get("PERSON_ADDRESS")), text(map.get("FACILITY_ADDRESS")), text(map.get("MEDICAID_NUMBER")),
				text(map.get("FACILITY_NAME")), text(map.get("DUNS_ID")), date(map.get("MATCHED_PROCESS_DATE")), date(map.get("AIRS_CALC_FILE_DATE")),
				text(map.get("AIRS_VERSION")), text(map.get("EXTRALOGID")), text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")),
				text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE")), text(map.get("CTR_VERSION")),
				text(map.get("CTR_FORMID")), text(map.get("PERSON_PHONE_TEXT")), text(map.get("CTR_PRELIMINARY_POSITIVE"))
		};
	}

	/** The SQL of {@code insertRawEditedNycIJRDocument}, as the former mapper had it (92 bound values). */
	static final String RAW_EDITED_NYC_IJR_DOCUMENT = """
				insert into RAW_NYCIJR_DOC(DIN, ADDRESS, MNAME, PROV_ADDR, PROVNAME, SIGNATURE, FNAME_COMPLETER, MI_COMPLETER, LNAME_COMPLETER, TITLE_COMPLETER, ALT_LNAME, EMAIL_COMPLETER, PROV_DEPARTMENT, CONTACT_LNAME, CONTACT_FNAME, CONTACT_MNAME, CONTACT_TITLE, CUR_STREET_ADDRESS1, CITY, PROV_CITY, CUR_CITY_NAME, HF_NAME2, ETHNICCD, FILE_NAME, LNAME, FNAME, MEDRECNO, RISK08, RACE_WHITE, RACE_UNKNOWN, RACE_PACIFIC, RACE_OTHER, RACE_MULTIPLE, RACE_INDIAN, RACE_BLACK, RACE_ASIAN, TELEPHONE, CELLPHONE, WORKPHONE, PROV_PHONE, BIRTH_COUNTRY, COUNTRY_RECV_BLD_PROD, CONTACT_PHONE, CUR_COUNTY_NAME, CUR_PHONE, RSH_PHONE, RACE_CD, FILE_DT, AIDSDXDT, CREATE_UID, NUMBEROFPARTNERS, NYC_IJR_SRC_ID, DOCUMENT_ID, CURRENT_PROCESS_STATUS, PROCESS_DATE, CREATE_DATE, GENDER, ZIPCODE, PROV_ZIP, CUR_ZIP_CD, NO_PART, D_DIAG, STATE, PROV_ST, CUR_STATE_CD, SSN, CITYNO, STATENO, HIVDXDT, DOB, I_DIAG_DT, D_DIAG_DT, SIG_DT, INFORMED_OF_DIAG_DT, ADMISS_DT, DT_COMPLETED, DATE_RECV_BLD_PROD, INDX_INTERVIEWED_DT, NYSID, I_ILL, RISK01, RISK02, RISK03, RISK04, RISK05, RISK06, RISK07, RISK09, INFORMED_OF_DIAG, PNAP_REQUESTED, RISK10, INDX_INTERVIEWED)
			values(trim(upper(?1)),trim(upper(?2)),trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),trim(upper(?6)),trim(upper(?7)),trim(upper(?8)),trim(upper(?9)),trim(upper(?10)),trim(upper(?11)),trim(upper(?12)),trim(upper(?13)),trim(upper(?14)),trim(upper(?15)),trim(upper(?16)),trim(upper(?17)),trim(upper(?18)),trim(upper(?19)),trim(upper(?20)),trim(upper(?21)),trim(upper(?22)),trim(upper(?23)),?24,trim(upper(?25)),trim(upper(?26)),trim(upper(?27)),trim(upper(?28)),trim(upper(?29)),trim(upper(?30)),trim(upper(?31)),trim(upper(?32)),trim(upper(?33)),trim(upper(?34)),trim(upper(?35)),trim(upper(?36)),trim(upper(?37)),trim(upper(?38)),trim(upper(?39)),trim(upper(?40)),trim(upper(?41)),trim(upper(?42)),trim(upper(?43)),trim(upper(?44)),trim(upper(?45)),trim(upper(?46)),trim(upper(?47)),trim(upper(?48)),trim(upper(?49)),trim(upper(?50)),?51,?52,?53,?54,?55,?56,trim(upper(?57)),trim(upper(?58)),trim(upper(?59)),trim(upper(?60)),trim(upper(?61)),trim(upper(?62)),trim(upper(?63)),trim(upper(?64)),trim(upper(?65)),trim(upper(?66)),trim(upper(?67)),trim(upper(?68)),trim(upper(?69)),trim(upper(?70)),trim(upper(?71)),trim(upper(?72)),trim(upper(?73)),trim(upper(?74)),trim(upper(?75)),trim(upper(?76)),trim(upper(?77)),trim(upper(?78)),trim(upper(?79)),trim(upper(?80)),trim(upper(?81)),trim(upper(?82)),trim(upper(?83)),trim(upper(?84)),trim(upper(?85)),trim(upper(?86)),trim(upper(?87)),trim(upper(?88)),trim(upper(?89)),trim(upper(?90)),trim(upper(?91)),trim(upper(?92)))
			""";

	/** The values of {@link #RAW_EDITED_NYC_IJR_DOCUMENT}, in order. */
	static Object[] insertRawEditedNycIJRDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("DIN")), text(map.get("ADDRESS")), text(map.get("MNAME")), text(map.get("PROV_ADDR")),
				text(map.get("PROVNAME")), text(map.get("SIGNATURE")), text(map.get("FNAME_COMPLETER")), text(map.get("MI_COMPLETER")),
				text(map.get("LNAME_COMPLETER")), text(map.get("TITLE_COMPLETER")), text(map.get("ALT_LNAME")), text(map.get("EMAIL_COMPLETER")),
				text(map.get("PROV_DEPARTMENT")), text(map.get("CONTACT_LNAME")), text(map.get("CONTACT_FNAME")), text(map.get("CONTACT_MNAME")),
				text(map.get("CONTACT_TITLE")), text(map.get("CUR_STREET_ADDRESS1")), text(map.get("CITY")), text(map.get("PROV_CITY")),
				text(map.get("CUR_CITY_NAME")), text(map.get("HF_NAME2")), text(map.get("ETHNICCD")), text(map.get("FILE_NAME")),
				text(map.get("LNAME")), text(map.get("FNAME")), text(map.get("MEDRECNO")), text(map.get("RISK08")),
				text(map.get("RACE_WHITE")), text(map.get("RACE_UNKNOWN")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_OTHER")),
				text(map.get("RACE_MULTIPLE")), text(map.get("RACE_INDIAN")), text(map.get("RACE_BLACK")), text(map.get("RACE_ASIAN")),
				text(map.get("TELEPHONE")), text(map.get("CELLPHONE")), text(map.get("WORKPHONE")), text(map.get("PROV_PHONE")),
				text(map.get("BIRTH_COUNTRY")), text(map.get("COUNTRY_RECV_BLD_PROD")), text(map.get("CONTACT_PHONE")), text(map.get("CUR_COUNTY_NAME")),
				text(map.get("CUR_PHONE")), text(map.get("RSH_PHONE")), text(map.get("RACE_CD")), text(map.get("FILE_DT")),
				text(map.get("AIDSDXDT")), text(map.get("CREATE_UID")), number(map.get("NUMBEROFPARTNERS")), number(map.get("NYC_IJR_SRC_ID")),
				number(map.get("DOCUMENT_ID")), number(map.get("CURRENT_PROCESS_STATUS")), date(map.get("PROCESS_DATE")), date(map.get("CREATE_DATE")),
				text(map.get("GENDER")), text(map.get("ZIPCODE")), text(map.get("PROV_ZIP")), text(map.get("CUR_ZIP_CD")),
				text(map.get("NO_PART")), text(map.get("D_DIAG")), text(map.get("STATE")), text(map.get("PROV_ST")),
				text(map.get("CUR_STATE_CD")), text(map.get("SSN")), text(map.get("CITYNO")), text(map.get("STATENO")),
				text(map.get("HIVDXDT")), text(map.get("DOB")), text(map.get("I_DIAG_DT")), text(map.get("D_DIAG_DT")),
				text(map.get("SIG_DT")), text(map.get("INFORMED_OF_DIAG_DT")), text(map.get("ADMISS_DT")), text(map.get("DT_COMPLETED")),
				text(map.get("DATE_RECV_BLD_PROD")), text(map.get("INDX_INTERVIEWED_DT")), text(map.get("NYSID")), text(map.get("I_ILL")),
				text(map.get("RISK01")), text(map.get("RISK02")), text(map.get("RISK03")), text(map.get("RISK04")),
				text(map.get("RISK05")), text(map.get("RISK06")), text(map.get("RISK07")), text(map.get("RISK09")),
				text(map.get("INFORMED_OF_DIAG")), text(map.get("PNAP_REQUESTED")), text(map.get("RISK10")), text(map.get("INDX_INTERVIEWED"))
		};
	}

	/** The SQL of {@code insertStdEditedNycIJRDocument}, as the former mapper had it (120 bound values). */
	static final String STD_EDITED_NYC_IJR_DOCUMENT = """
			insert into STD_NYCIJR_DOC(CREATE_UID, PROV_SIGNED_DATE, COMPLETED_DATE, FILE_DATE, CREATE_DATE, REVIEW_DT, MATCHED_PROCESS_DATE, EHARS_IMPORT_DATE, NYC_SHIP_DATE, DOCUMENT_ID, CURRENT_PROCESS_STATUS, AGE, MATCH_PASS, MATCH_WEIGHT, QS_PARENT_DOC_ID, NYC_IJR_SRC_ID, NUMBEROFPARTNERS, CURRENT_GENDER, INIT_HIV_DIAG, INIT_AIDS_DIAG, INFORMED_HIV_POS, INDX_INTERVIEWED, SWM, SWF, IDU, SEX_PARTNER_IDU, SEX_PARTNER_HIV, RECV_BLOOD_PROD, PERINATAL, RISK_OTHER, RISK_UNKNOWN, SEX_PARTNER_MSM, PS_REQUESTED, PROCESSED, CITYNO, NYC_SENT_STATENO, DOB_TEXT, NYSID, ADMISSION_DATE_TEXT, INIT_HIV_DIAG_DATE_TEXT, INIT_AIDS_DIAG_DATE_TEXT, INFORMED_HIV_DATE_TEXT, INDX_INTERVIEWED_DATE_TEXT, RECV_BLOOD_PROD_DATE_TEXT, MATCHED_STATENO, HIV_DX_DATE_TEXT, AIDS_DX_DATE_TEXT, DX_PERSON_LATITUDE, CUR_PERSON_LATITUDE, PROV_LATITUDE, RACE_CD, SSN, DX_PERSON_LONGITUDE, CUR_PERSON_LONGITUDE, PROV_LONGITUDE, BIRTH_COUNTRY, DX_PERSON_TELEPHONE, PERSON_CELLPHONE, PERSON_WORKPHONE, CUR_PERSON_COUNTY_NAME, CUR_PERSON_PHONE, RECV_BLOOD_PROD_COUNTRY, CONTACT_PHONE, RSH_PHONE, ETHNICITY, DX_PERSON_STATE, CUR_PERSON_STATE, PROV_STATE, RACE_WHITE, RACE_UNKNOWN, RACE_PACIFIC, RACE_OTHER, RACE_MULTIPLE, RACE_INDIAN, RACE_BLACK, RACE_ASIAN, MED_RECORD_NUMBER, PROV_PHONE, RISK_OTHER_TEXT, LNAME, FNAME, MNAME, FILE_NAME, DX_PERSON_COUNTY, CUR_PERSON_COUNTY, PROV_COUNTY, MATCH_CD, HIV_FACILITY_NAME, DX_PERSON_CITY, CUR_PERSON_CITY, PROV_CITY, DX_PERSON_ZIP, CUR_PERSON_ZIP, PROV_ZIP, DX_PERSON_CENSUS_TRACT, CUR_PERSON_CENSUS_TRACT, PROV_CENSUS_TRACT, ALT_LNAME, DX_PERSON_ADDRESS, CUR_PERSON_ADDRESS, PROV_ADDRESS, PROV_NAME, PROV_DEPARTMENT, PROV_SIGNED, COMPLETER_FNAME, COMPLETER_MI, COMPLETER_LNAME, COMPLETER_TITLE, COMPLETER_EMAIL, CONTACT_LNAME, CONTACT_FNAME, CONTACT_MNAME, CONTACT_TITLE, DIN, REVIEW_UID,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			values(trim(upper(?1)), FN_TO_DATE(?2,'MM/DD/YYYY'), FN_TO_DATE(?3,'MM/DD/YYYY'), ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), trim(upper(?41)), trim(upper(?42)), trim(upper(?43)), trim(upper(?44)), trim(upper(?45)), trim(upper(?46)), trim(upper(?47)), trim(upper(?48)), trim(upper(?49)), trim(upper(?50)), trim(upper(?51)), trim(upper(?52)), trim(upper(?53)), trim(upper(?54)), trim(upper(?55)), trim(upper(?56)), trim(upper(?57)), trim(upper(?58)), trim(upper(?59)), trim(upper(?60)), trim(upper(?61)), trim(upper(?62)), trim(upper(?63)), trim(upper(?64)), trim(upper(?65)), trim(upper(?66)), trim(upper(?67)), trim(upper(?68)), trim(upper(?69)), trim(upper(?70)), trim(upper(?71)), trim(upper(?72)), trim(upper(?73)), trim(upper(?74)), trim(upper(?75)), trim(upper(?76)), trim(upper(?77)), trim(upper(?78)), trim(upper(?79)), trim(upper(?80)), trim(upper(?81)), trim(upper(?82)),?83, trim(upper(?84)), trim(upper(?85)), trim(upper(?86)), trim(upper(?87)), trim(upper(?88)), trim(upper(?89)), trim(upper(?90)), trim(upper(?91)), trim(upper(?92)), trim(upper(?93)), trim(upper(?94)), trim(upper(?95)), trim(upper(?96)), trim(upper(?97)), trim(upper(?98)), trim(upper(?99)), trim(upper(?100)), trim(upper(?101)), trim(upper(?102)), trim(upper(?103)), trim(upper(?104)), trim(upper(?105)), trim(upper(?106)), trim(upper(?107)), trim(upper(?108)), trim(upper(?109)), trim(upper(?110)), trim(upper(?111)), trim(upper(?112)), trim(upper(?113)), trim(upper(?114)), trim(upper(?115)),?116,?117,?118,?119,?120)
			""";

	/** The values of {@link #STD_EDITED_NYC_IJR_DOCUMENT}, in order. */
	static Object[] insertStdEditedNycIJRDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("CREATE_UID")), date(map.get("PROV_SIGNED_DATE")), date(map.get("COMPLETED_DATE")), date(map.get("FILE_DATE")),
				date(map.get("CREATE_DATE")), date(map.get("REVIEW_DT")), date(map.get("MATCHED_PROCESS_DATE")), date(map.get("EHARS_IMPORT_DATE")),
				date(map.get("NYC_SHIP_DATE")), number(map.get("DOCUMENT_ID")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("AGE")),
				number(map.get("MATCH_PASS")), number(map.get("MATCH_WEIGHT")), number(map.get("QS_PARENT_DOC_ID")), number(map.get("NYC_IJR_SRC_ID")),
				number(map.get("NUMBEROFPARTNERS")), text(map.get("CURRENT_GENDER")), text(map.get("INIT_HIV_DIAG")), text(map.get("INIT_AIDS_DIAG")),
				text(map.get("INFORMED_HIV_POS")), text(map.get("INDX_INTERVIEWED")), text(map.get("SWM")), text(map.get("SWF")),
				text(map.get("IDU")), text(map.get("SEX_PARTNER_IDU")), text(map.get("SEX_PARTNER_HIV")), text(map.get("RECV_BLOOD_PROD")),
				text(map.get("PERINATAL")), text(map.get("RISK_OTHER")), text(map.get("RISK_UNKNOWN")), text(map.get("SEX_PARTNER_MSM")),
				text(map.get("PS_REQUESTED")), text(map.get("PROCESSED")), text(map.get("CITYNO")), text(map.get("NYC_SENT_STATENO")),
				text(map.get("DOB_TEXT")), text(map.get("NYSID")), text(map.get("ADMISSION_DATE_TEXT")), text(map.get("INIT_HIV_DIAG_DATE_TEXT")),
				text(map.get("INIT_AIDS_DIAG_DATE_TEXT")), text(map.get("INFORMED_HIV_DATE_TEXT")), text(map.get("INDX_INTERVIEWED_DATE_TEXT")), text(map.get("RECV_BLOOD_PROD_DATE_TEXT")),
				text(map.get("MATCHED_STATENO")), text(map.get("HIV_DX_DATE_TEXT")), text(map.get("AIDS_DX_DATE_TEXT")), text(map.get("DX_PERSON_LATITUDE")),
				text(map.get("CUR_PERSON_LATITUDE")), text(map.get("PROV_LATITUDE")), text(map.get("RACE_CD")), text(map.get("SSN")),
				text(map.get("DX_PERSON_LONGITUDE")), text(map.get("CUR_PERSON_LONGITUDE")), text(map.get("PROV_LONGITUDE")), text(map.get("BIRTH_COUNTRY")),
				text(map.get("DX_PERSON_TELEPHONE")), text(map.get("PERSON_CELLPHONE")), text(map.get("PERSON_WORKPHONE")), text(map.get("CUR_PERSON_COUNTY_NAME")),
				text(map.get("CUR_PERSON_PHONE")), text(map.get("RECV_BLOOD_PROD_COUNTRY")), text(map.get("CONTACT_PHONE")), text(map.get("RSH_PHONE")),
				text(map.get("ETHNICITY")), text(map.get("DX_PERSON_STATE")), text(map.get("CUR_PERSON_STATE")), text(map.get("PROV_STATE")),
				text(map.get("RACE_WHITE")), text(map.get("RACE_UNKNOWN")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_OTHER")),
				text(map.get("RACE_MULTIPLE")), text(map.get("RACE_INDIAN")), text(map.get("RACE_BLACK")), text(map.get("RACE_ASIAN")),
				text(map.get("MED_RECORD_NUMBER")), text(map.get("PROV_PHONE")), text(map.get("RISK_OTHER_TEXT")), text(map.get("LNAME")),
				text(map.get("FNAME")), text(map.get("MNAME")), text(map.get("FILE_NAME")), text(map.get("DX_PERSON_COUNTY")),
				text(map.get("CUR_PERSON_COUNTY")), text(map.get("PROV_COUNTY")), text(map.get("MATCH_CD")), text(map.get("HIV_FACILITY_NAME")),
				text(map.get("DX_PERSON_CITY")), text(map.get("CUR_PERSON_CITY")), text(map.get("PROV_CITY")), text(map.get("DX_PERSON_ZIP")),
				text(map.get("CUR_PERSON_ZIP")), text(map.get("PROV_ZIP")), text(map.get("DX_PERSON_CENSUS_TRACT")), text(map.get("CUR_PERSON_CENSUS_TRACT")),
				text(map.get("PROV_CENSUS_TRACT")), text(map.get("ALT_LNAME")), text(map.get("DX_PERSON_ADDRESS")), text(map.get("CUR_PERSON_ADDRESS")),
				text(map.get("PROV_ADDRESS")), text(map.get("PROV_NAME")), text(map.get("PROV_DEPARTMENT")), text(map.get("PROV_SIGNED")),
				text(map.get("COMPLETER_FNAME")), text(map.get("COMPLETER_MI")), text(map.get("COMPLETER_LNAME")), text(map.get("COMPLETER_TITLE")),
				text(map.get("COMPLETER_EMAIL")), text(map.get("CONTACT_LNAME")), text(map.get("CONTACT_FNAME")), text(map.get("CONTACT_MNAME")),
				text(map.get("CONTACT_TITLE")), text(map.get("DIN")), text(map.get("REVIEW_UID")), text(map.get("FLUX_MATCH_CD")),
				text(map.get("FLUX_POT_STATENO")), text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedNDIDocument}, as the former mapper had it (86 bound values). */
	static final String RAW_EDITED_NDI_DOCUMENT = """
				insert into RAW_NDI_DOC(SEX, NDI_MIDDLE, NDI_LAST, NDI_BIR_MO, NDI_BIR_DAY, NDI_SEX, NDI_ST_RES, EXACT, CLASS, STATUS, NDIFILE_CAT, SURV_METHOD, NIF1, NIF2, NIF3, NIF4, NIF5, NIF6, NIF7, NIF8, NIF9, NIF10, NIF11, NIF12, NIF13, NIF14, NIF15, NIF16, NIF17, NIF18, NIF19, NIF20, STATENO, DOB_TEXT, NDI_DOD_TEXT, RECV_DT, CREATEID, STATE_RES, STATE_DEATH, MONTH_DEATH, DAY_DEATH, NDI_FIRST, UNIQ_NO, NDI_BIR_YEAR, MATCH_SEQ, HIVCAUSE, TDISCREP_CAT, DOC_TYPE, YEAR_DEATH, ICD_CODE, COD1, COD2, COD3, COD4, COD5, COD6, COD7, COD8, COD9, COD10, COD11, COD12, COD13, COD14, COD15, COD16, COD17, COD18, COD19, COD20, DEATH_CERT, SOURCE, SSN, NDI_SSN, CREATEDT, PROCESS_DATE, DOCUMENT_ID, CURRENT_PROCESS_STATUS, SCORE_RANK, SCORE, PROB_SCORE, TIME_DISCREP, FNAME, MNAME, LNAME, NDI_FILE)
			values(trim(upper(?1)),trim(upper(?2)),trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),trim(upper(?6)),trim(upper(?7)),trim(upper(?8)),trim(upper(?9)),trim(upper(?10)),trim(upper(?11)),trim(upper(?12)),trim(upper(?13)),trim(upper(?14)),trim(upper(?15)),trim(upper(?16)),trim(upper(?17)),trim(upper(?18)),trim(upper(?19)),trim(upper(?20)),trim(upper(?21)),trim(upper(?22)),trim(upper(?23)),trim(upper(?24)),trim(upper(?25)),trim(upper(?26)),trim(upper(?27)),trim(upper(?28)),trim(upper(?29)),trim(upper(?30)),trim(upper(?31)),trim(upper(?32)),trim(upper(?33)),trim(upper(?34)),trim(upper(?35)),trim(upper(?36)),trim(upper(?37)),trim(upper(?38)),trim(upper(?39)),trim(upper(?40)),trim(upper(?41)),trim(upper(?42)),trim(upper(?43)),trim(upper(?44)),trim(upper(?45)),trim(upper(?46)),trim(upper(?47)),trim(upper(?48)),trim(upper(?49)),trim(upper(?50)),trim(upper(?51)),trim(upper(?52)),trim(upper(?53)),trim(upper(?54)),trim(upper(?55)),trim(upper(?56)),trim(upper(?57)),trim(upper(?58)),trim(upper(?59)),trim(upper(?60)),trim(upper(?61)),trim(upper(?62)),trim(upper(?63)),trim(upper(?64)),trim(upper(?65)),trim(upper(?66)),trim(upper(?67)),trim(upper(?68)),trim(upper(?69)),trim(upper(?70)),trim(upper(?71)),trim(upper(?72)),trim(upper(?73)),trim(upper(?74)),?75,?76,?77,?78,?79,?80,?81,?82,trim(upper(?83)),trim(upper(?84)),trim(upper(?85)),?86)
			""";

	/** The values of {@link #RAW_EDITED_NDI_DOCUMENT}, in order. */
	static Object[] insertRawEditedNDIDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SEX")), text(map.get("NDI_MIDDLE")), text(map.get("NDI_LAST")), text(map.get("NDI_BIR_MO")),
				text(map.get("NDI_BIR_DAY")), text(map.get("NDI_SEX")), text(map.get("NDI_ST_RES")), text(map.get("EXACT")),
				text(map.get("CLASS")), text(map.get("STATUS")), text(map.get("NDIFILE_CAT")), text(map.get("SURV_METHOD")),
				text(map.get("NIF1")), text(map.get("NIF2")), text(map.get("NIF3")), text(map.get("NIF4")),
				text(map.get("NIF5")), text(map.get("NIF6")), text(map.get("NIF7")), text(map.get("NIF8")),
				text(map.get("NIF9")), text(map.get("NIF10")), text(map.get("NIF11")), text(map.get("NIF12")),
				text(map.get("NIF13")), text(map.get("NIF14")), text(map.get("NIF15")), text(map.get("NIF16")),
				text(map.get("NIF17")), text(map.get("NIF18")), text(map.get("NIF19")), text(map.get("NIF20")),
				text(map.get("STATENO")), text(map.get("DOB_TEXT")), text(map.get("NDI_DOD_TEXT")), text(map.get("RECV_DT")),
				text(map.get("CREATEID")), text(map.get("STATE_RES")), text(map.get("STATE_DEATH")), text(map.get("MONTH_DEATH")),
				text(map.get("DAY_DEATH")), text(map.get("NDI_FIRST")), text(map.get("UNIQ_NO")), text(map.get("NDI_BIR_YEAR")),
				text(map.get("MATCH_SEQ")), text(map.get("HIVCAUSE")), text(map.get("TDISCREP_CAT")), text(map.get("DOC_TYPE")),
				text(map.get("YEAR_DEATH")), text(map.get("ICD_CODE")), text(map.get("COD1")), text(map.get("COD2")),
				text(map.get("COD3")), text(map.get("COD4")), text(map.get("COD5")), text(map.get("COD6")),
				text(map.get("COD7")), text(map.get("COD8")), text(map.get("COD9")), text(map.get("COD10")),
				text(map.get("COD11")), text(map.get("COD12")), text(map.get("COD13")), text(map.get("COD14")),
				text(map.get("COD15")), text(map.get("COD16")), text(map.get("COD17")), text(map.get("COD18")),
				text(map.get("COD19")), text(map.get("COD20")), text(map.get("DEATH_CERT")), text(map.get("SOURCE")),
				text(map.get("SSN")), text(map.get("NDI_SSN")), date(map.get("CREATEDT")), date(map.get("PROCESS_DATE")),
				number(map.get("DOCUMENT_ID")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("SCORE_RANK")), number(map.get("SCORE")),
				number(map.get("PROB_SCORE")), number(map.get("TIME_DISCREP")), text(map.get("FNAME")), text(map.get("MNAME")),
				text(map.get("LNAME")), text(map.get("NDI_FILE"))
		};
	}

	/** The SQL of {@code insertStdEditedNDIDocument}, as the former mapper had it (75 bound values). */
	static final String STD_EDITED_NDI_DOCUMENT = """
			insert into STD_NDI_DOC(MNAME, LNAME, FNAME, NDI_FILE, STATENO, TIME_DISCREP, DOCUMENT_ID, CURRENT_PROCESS_STATUS, RECEIVED_DATE, CREATE_DATE, NYC_SHIP_DATE, SSN, NDI_DOD, DEATH_CERT, YEAR_DEATH, ICD_CODE, COD1, COD2, COD3, COD4, COD5, COD6, COD7, COD8, COD9, COD10, COD11, COD12, COD13, COD14, COD15, COD16, COD17, COD18, COD19, COD20, HIVCAUSE, TDISCREP_CAT, DOC_TYPE, UNIQ_NO, STATE_RES, STATE_DEATH, MONTH_DEATH, DAY_DEATH, DOB_TEXT, CREATE_UID, EHARS_IMPORT_DATE, BIRTH_SEX, SURV_METHOD, NIF1, NIF2, NIF3, NIF4, NIF5, NIF6, NIF7, NIF8, NIF9, NIF10, NIF11, NIF12, NIF13, NIF14, NIF15, NIF16, NIF17, NIF18, NIF19, NIF20, EHARS_STATUS_CD,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), ?4, trim(upper(?5)), ?6, ?7, ?8, ?9, ?10, ?11, trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), trim(upper(?41)), trim(upper(?42)), trim(upper(?43)), trim(upper(?44)), trim(upper(?45)), trim(upper(?46)), trim(upper(?47)), trim(upper(?48)), trim(upper(?49)), trim(upper(?50)), trim(upper(?51)), trim(upper(?52)), trim(upper(?53)), trim(upper(?54)), trim(upper(?55)), trim(upper(?56)), trim(upper(?57)), trim(upper(?58)), trim(upper(?59)), trim(upper(?60)), trim(upper(?61)), trim(upper(?62)), trim(upper(?63)), trim(upper(?64)), trim(upper(?65)), trim(upper(?66)), trim(upper(?67)), trim(upper(?68)), trim(upper(?69)), trim(upper(?70)),?71,?72,?73,?74,?75)
			""";

	/** The values of {@link #STD_EDITED_NDI_DOCUMENT}, in order. */
	static Object[] insertStdEditedNDIDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("MNAME")), text(map.get("LNAME")), text(map.get("FNAME")), text(map.get("NDI_FILE")),
				text(map.get("STATENO")), number(map.get("TIME_DISCREP")), number(map.get("DOCUMENT_ID")), number(map.get("CURRENT_PROCESS_STATUS")),
				date(map.get("RECEIVED_DATE")), date(map.get("CREATE_DATE")), date(map.get("NYC_SHIP_DATE")), text(map.get("SSN")),
				text(map.get("NDI_DOD")), text(map.get("DEATH_CERT")), text(map.get("YEAR_DEATH")), text(map.get("ICD_CODE")),
				text(map.get("COD1")), text(map.get("COD2")), text(map.get("COD3")), text(map.get("COD4")),
				text(map.get("COD5")), text(map.get("COD6")), text(map.get("COD7")), text(map.get("COD8")),
				text(map.get("COD9")), text(map.get("COD10")), text(map.get("COD11")), text(map.get("COD12")),
				text(map.get("COD13")), text(map.get("COD14")), text(map.get("COD15")), text(map.get("COD16")),
				text(map.get("COD17")), text(map.get("COD18")), text(map.get("COD19")), text(map.get("COD20")),
				text(map.get("HIVCAUSE")), text(map.get("TDISCREP_CAT")), text(map.get("DOC_TYPE")), text(map.get("UNIQ_NO")),
				text(map.get("STATE_RES")), text(map.get("STATE_DEATH")), text(map.get("MONTH_DEATH")), text(map.get("DAY_DEATH")),
				text(map.get("DOB_TEXT")), text(map.get("CREATE_UID")), text(map.get("EHARS_IMPORT_DATE")), text(map.get("BIRTH_SEX")),
				text(map.get("SURV_METHOD")), text(map.get("NIF1")), text(map.get("NIF2")), text(map.get("NIF3")),
				text(map.get("NIF4")), text(map.get("NIF5")), text(map.get("NIF6")), text(map.get("NIF7")),
				text(map.get("NIF8")), text(map.get("NIF9")), text(map.get("NIF10")), text(map.get("NIF11")),
				text(map.get("NIF12")), text(map.get("NIF13")), text(map.get("NIF14")), text(map.get("NIF15")),
				text(map.get("NIF16")), text(map.get("NIF17")), text(map.get("NIF18")), text(map.get("NIF19")),
				text(map.get("NIF20")), text(map.get("EHARS_STATUS_CD")), text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")),
				text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedSSDMDocument}, as the former mapper had it (20 bound values). */
	static final String RAW_EDITED_SSDM_DOCUMENT = """
				insert into RAW_SSDM_DOC(V_OR_P_CODE, UPDATE_STATUS, SSN, ZIP, SSDM_FILE, BTHYEAR, LASTNAME, ORIGLNAME, NSUFFIX, FIRSTNAME, MIDDLENAME, STZ, BTHDATE, DTHDATE, DOCUMENT_ID, SSDM_SRC_ID, AGEDEATH, CURRENT_PROCESS_STATUS, PROCESS_DATE, RECEIVED_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), ?5, trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), ?15, ?16, ?17, ?18, ?19, ?20)
			""";

	/** The values of {@link #RAW_EDITED_SSDM_DOCUMENT}, in order. */
	static Object[] insertRawEditedSSDMDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("V_OR_P_CODE")), text(map.get("UPDATE_STATUS")), text(map.get("SSN")), text(map.get("ZIP")),
				text(map.get("SSDM_FILE")), text(map.get("BTHYEAR")), text(map.get("LASTNAME")), text(map.get("ORIGLNAME")),
				text(map.get("NSUFFIX")), text(map.get("FIRSTNAME")), text(map.get("MIDDLENAME")), text(map.get("STZ")),
				text(map.get("BTHDATE")), text(map.get("DTHDATE")), number(map.get("DOCUMENT_ID")), number(map.get("SSDM_SRC_ID")),
				number(map.get("AGEDEATH")), number(map.get("CURRENT_PROCESS_STATUS")), date(map.get("PROCESS_DATE")), date(map.get("RECEIVED_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedSSDMDocument}, as the former mapper had it (35 bound values). */
	static final String STD_EDITED_SSDM_DOCUMENT = """
			insert into STD_SSDM_DOC(SSN, PERSON_ZIP_AT_DEATH, SSDM_FILE, MATCH_PASS, BIRTH_YEAR, LNAME, ORIG_LNAME, SUFFIX, MATCH_CD, FNAME, MNAME, PERSON_COUNTY_AT_DEATH, PERSON_STATE_AT_DEATH, REVIEW_UID, DOB_TEXT, DOD_TEXT, MATCHED_STATENO, EHARS_STATUS_CD, MATCH_WEIGHT, DOCUMENT_ID, SSDM_SRC_ID, AGE_AT_DEATH, CURRENT_PROCESS_STATUS, QS_PARENT_DOC_ID, REVIEW_DATE, CREATE_DATE, EHARS_IMPORT_DATE, MATCHED_PROCESS_DATE, REVIEW_DT, CREATE_UID,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			values(trim(upper(?1)), trim(upper(?2)), ?3, trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), ?19, ?20, ?21, ?22, ?23, ?24, ?25, ?26, ?27, ?28, ?29, trim(upper(?30)),?31,?32,?33,?34,?35)
			""";

	/** The values of {@link #STD_EDITED_SSDM_DOCUMENT}, in order. */
	static Object[] insertStdEditedSSDMDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("PERSON_ZIP_AT_DEATH")), text(map.get("SSDM_FILE")), text(map.get("MATCH_PASS")),
				text(map.get("BIRTH_YEAR")), text(map.get("LNAME")), text(map.get("ORIG_LNAME")), text(map.get("SUFFIX")),
				text(map.get("MATCH_CD")), text(map.get("FNAME")), text(map.get("MNAME")), text(map.get("PERSON_COUNTY_AT_DEATH")),
				text(map.get("PERSON_STATE_AT_DEATH")), text(map.get("REVIEW_UID")), text(map.get("DOB_TEXT")), text(map.get("DOD_TEXT")),
				text(map.get("MATCHED_STATENO")), text(map.get("EHARS_STATUS_CD")), number(map.get("MATCH_WEIGHT")), number(map.get("DOCUMENT_ID")),
				number(map.get("SSDM_SRC_ID")), number(map.get("AGE_AT_DEATH")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("QS_PARENT_DOC_ID")),
				date(map.get("REVIEW_DATE")), date(map.get("CREATE_DATE")), date(map.get("EHARS_IMPORT_DATE")), date(map.get("MATCHED_PROCESS_DATE")),
				date(map.get("REVIEW_DT")), text(map.get("CREATE_UID")), text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")),
				text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedInsVitalDocument}, as the former mapper had it (7 bound values). */
	static final String RAW_EDITED_INS_VITAL_DOCUMENT = """
				insert into RAW_INS_DEATH_DOC(MBR_ID, DECEASED_STATUS, INS_DEATH_FILE_NAME, DATE_OF_DEATH, DOCUMENT_ID, CURRENT_PROCESS_STATUS, PROCESS_DATE)
			values(trim(upper(?1)),trim(upper(?2)),?3,trim(upper(?4)),?5,?6,?7)
			""";

	/** The values of {@link #RAW_EDITED_INS_VITAL_DOCUMENT}, in order. */
	static Object[] insertRawEditedInsVitalDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("MBR_ID")), text(map.get("DECEASED_STATUS")), text(map.get("INS_DEATH_FILE_NAME")), text(map.get("DATE_OF_DEATH")),
				number(map.get("DOCUMENT_ID")), number(map.get("CURRENT_PROCESS_STATUS")), date(map.get("PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedInsVitalDocument}, as the former mapper had it (29 bound values). */
	static final String STD_EDITED_INS_VITAL_DOCUMENT = """
			insert into STD_INS_DEATH_DOC(RACE_INDIAN, RACE_BLACK, RACE_ASIAN, RACE_PACIFIC, RACE_WHITE, RACE_MULTIPLE, RACE_OTHER, RACE_UNKNOWN, CREATE_UID, DATE_OF_DEATH, EHARS_IMPORT_DATE, CREATE_DATE, DOCUMENT_ID, CURRENT_PROCESS_STATUS, MNAME, DOB_TEXT, CURRENT_GENDER, MATCHED_STATENO, FNAME, LNAME, INS_DEATH_FILE_NAME, MEDICAID_NUMBER, DECEASED_STATUS, SSN,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), FN_TO_DATE(?10,'MM/DD/YYYY'), ?11, ?12, ?13, ?14, trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), ?21, trim(upper(?22)), trim(upper(?23)), trim(upper(?24)),?25,?26,?27,?28,?29)
			""";

	/** The values of {@link #STD_EDITED_INS_VITAL_DOCUMENT}, in order. */
	static Object[] insertStdEditedInsVitalDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("RACE_INDIAN")), text(map.get("RACE_BLACK")), text(map.get("RACE_ASIAN")), text(map.get("RACE_PACIFIC")),
				text(map.get("RACE_WHITE")), text(map.get("RACE_MULTIPLE")), text(map.get("RACE_OTHER")), text(map.get("RACE_UNKNOWN")),
				text(map.get("CREATE_UID")), date(map.get("DATE_OF_DEATH")), date(map.get("EHARS_IMPORT_DATE")), date(map.get("CREATE_DATE")),
				number(map.get("DOCUMENT_ID")), number(map.get("CURRENT_PROCESS_STATUS")), text(map.get("MNAME")), text(map.get("DOB_TEXT")),
				text(map.get("CURRENT_GENDER")), text(map.get("MATCHED_STATENO")), text(map.get("FNAME")), text(map.get("LNAME")),
				text(map.get("INS_DEATH_FILE_NAME")), text(map.get("MEDICAID_NUMBER")), text(map.get("DECEASED_STATUS")), text(map.get("SSN")),
				text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")), text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")),
				date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedCasaDocument}, as the former mapper had it (60 bound values). */
	static final String STD_EDITED_CASA_DOCUMENT = """
			insert into STD_CASA_DOC(SSN, NARROW, CITY, PERSON_ZIP, MATCH_PASS, FNAME, LNAME, FINCRG, PERSON_COUNTY, MATCH_CD, RATE, RATE1, CASA_FILE, ETHNICITY, STATE, MEDICAID_NUMBER, MATCHED_STATENO, DOB_TEXT, ELIG_DATE, REVIEW_UID, GENDER, MNAME, RACE_INDIAN, RACE_BLACK, RACE_ASIAN, RACE_PACIFIC, RACE_WHITE, RACE_MULTIPLE, RACE_OTHER, RACE_UNKNOWN, EHARS_STATUS_CD, DOCUMENT_ID, INPAT, CLAIMCOUNT, OUTPAT, DIAGALLCLAIMS, HIVHOSPICE, HIVSNP, ARV, OUTP_ADIOI, OUTPSERV_DIAG, SPEC_POSS, SPEC_ADI, ARV_POSS, ARV_ADI, ARV_OUTPAT, PROCEDURES, OUTPAT_SELECT, MATCH_WEIGHT, CURRENT_PROCESS_STATUS, QS_PARENT_DOC_ID, REVIEW_DT, EHARS_IMPORT_DATE, CREATE_DATE, MATCHED_PROCESS_DATE,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), ?13, trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), ?32, ?33, ?34, ?35, ?36, ?37, ?38, ?39, ?40, ?41, ?42, ?43, ?44, ?45, ?46, ?47, ?48, ?49, ?50, ?51, ?52, ?53, ?54, ?55,?56,?57,?58,?59,?60)
			""";

	/** The values of {@link #STD_EDITED_CASA_DOCUMENT}, in order. */
	static Object[] insertStdEditedCasaDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("NARROW")), text(map.get("CITY")), text(map.get("PERSON_ZIP")),
				text(map.get("MATCH_PASS")), text(map.get("FNAME")), text(map.get("LNAME")), text(map.get("FINCRG")),
				text(map.get("PERSON_COUNTY")), text(map.get("MATCH_CD")), text(map.get("RATE")), text(map.get("RATE1")),
				text(map.get("CASA_FILE")), text(map.get("ETHNICITY")), text(map.get("STATE")), text(map.get("MEDICAID_NUMBER")),
				text(map.get("MATCHED_STATENO")), text(map.get("DOB_TEXT")), text(map.get("ELIG_DATE")), text(map.get("REVIEW_UID")),
				text(map.get("GENDER")), text(map.get("MNAME")), text(map.get("RACE_INDIAN")), text(map.get("RACE_BLACK")),
				text(map.get("RACE_ASIAN")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_WHITE")), text(map.get("RACE_MULTIPLE")),
				text(map.get("RACE_OTHER")), text(map.get("RACE_UNKNOWN")), text(map.get("EHARS_STATUS_CD")), number(map.get("DOCUMENT_ID")),
				number(map.get("INPAT")), number(map.get("CLAIMCOUNT")), number(map.get("OUTPAT")), number(map.get("DIAGALLCLAIMS")),
				number(map.get("HIVHOSPICE")), number(map.get("HIVSNP")), number(map.get("ARV")), number(map.get("OUTP_ADIOI")),
				number(map.get("OUTPSERV_DIAG")), number(map.get("SPEC_POSS")), number(map.get("SPEC_ADI")), number(map.get("ARV_POSS")),
				number(map.get("ARV_ADI")), number(map.get("ARV_OUTPAT")), number(map.get("PROCEDURES")), number(map.get("OUTPAT_SELECT")),
				number(map.get("MATCH_WEIGHT")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("QS_PARENT_DOC_ID")), date(map.get("REVIEW_DT")),
				date(map.get("EHARS_IMPORT_DATE")), date(map.get("CREATE_DATE")), date(map.get("MATCHED_PROCESS_DATE")), text(map.get("FLUX_MATCH_CD")),
				text(map.get("FLUX_POT_STATENO")), text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedCasaDocument}, as the former mapper had it (54 bound values). */
	static final String RAW_EDITED_CASA_DOCUMENT = """
			insert into RAW_CASA_DOC(SSN, MDCD_CRG, NARROW, ZIP, FNAME, LNAME, FINCRG, COUNTY_FIPS, RATE, RATE1, CASA_FILE, MEDICAID_NUMBER, MDCD_INPAT, MDCD_OUTPAT, MDCD_DIAGALLCLAIMS, MDCD_HOSPICE, MDCD_SNP, MDCD_ARV, MDCD_OUTP_ADIOI, MDCD_OUTPSERV_DIAG, MDCD_SPEC_POSS, MDCD_SPEC_ADI, MDCD_ARV_POSS, MDCD_ARV_ADI, MDCD_ARV_OUTPAT, MDCD_PROC, MDCD_OUTPAT_SELECT, DOB, ELIG_DATE, GENDER, MNAME, RACE, DOCUMENT_ID, INPAT, CLAIMCOUNT, OUTPAT, DIAGALLCLAIMS, HIVHOSPICE, HIVSNP, ARV, OUTP_ADIOI, OUTPSERV_DIAG, SPEC_POSS, SPEC_ADI, ARV_POSS, ARV_ADI, ARV_OUTPAT, PROCEDURES, OUTPAT_SELECT, CURRENT_PROCESS_STATUS, HH_1386, HH_1387, CREATE_DATE, PROCESS_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), ?11, trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), ?33, ?34, ?35, ?36, ?37, ?38, ?39, ?40, ?41, ?42, ?43, ?44, ?45, ?46, ?47, ?48, ?49, ?50, ?51, ?52, ?53, ?54)
			""";

	/** The values of {@link #RAW_EDITED_CASA_DOCUMENT}, in order. */
	static Object[] insertRawEditedCasaDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("MDCD_CRG")), text(map.get("NARROW")), text(map.get("ZIP")),
				text(map.get("FNAME")), text(map.get("LNAME")), text(map.get("FINCRG")), text(map.get("COUNTY_FIPS")),
				text(map.get("RATE")), text(map.get("RATE1")), text(map.get("CASA_FILE")), text(map.get("MEDICAID_NUMBER")),
				text(map.get("MDCD_INPAT")), text(map.get("MDCD_OUTPAT")), text(map.get("MDCD_DIAGALLCLAIMS")), text(map.get("MDCD_HOSPICE")),
				text(map.get("MDCD_SNP")), text(map.get("MDCD_ARV")), text(map.get("MDCD_OUTP_ADIOI")), text(map.get("MDCD_OUTPSERV_DIAG")),
				text(map.get("MDCD_SPEC_POSS")), text(map.get("MDCD_SPEC_ADI")), text(map.get("MDCD_ARV_POSS")), text(map.get("MDCD_ARV_ADI")),
				text(map.get("MDCD_ARV_OUTPAT")), text(map.get("MDCD_PROC")), text(map.get("MDCD_OUTPAT_SELECT")), text(map.get("DOB")),
				text(map.get("ELIG_DATE")), text(map.get("GENDER")), text(map.get("MNAME")), text(map.get("RACE")),
				number(map.get("DOCUMENT_ID")), number(map.get("INPAT")), number(map.get("CLAIMCOUNT")), number(map.get("OUTPAT")),
				number(map.get("DIAGALLCLAIMS")), number(map.get("HIVHOSPICE")), number(map.get("HIVSNP")), number(map.get("ARV")),
				number(map.get("OUTP_ADIOI")), number(map.get("OUTPSERV_DIAG")), number(map.get("SPEC_POSS")), number(map.get("SPEC_ADI")),
				number(map.get("ARV_POSS")), number(map.get("ARV_ADI")), number(map.get("ARV_OUTPAT")), number(map.get("PROCEDURES")),
				number(map.get("OUTPAT_SELECT")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("HH_1386")), number(map.get("HH_1387")),
				date(map.get("CREATE_DATE")), date(map.get("PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedEprfOOCDocument}, as the former mapper had it (102 bound values). */
	static final String STD_EDITED_EPRF_OOC_DOCUMENT = """
			insert into STD_EPRF_OOC_UPLOAD(SSN, NYSID, FACILITY_NAME, DIN, SUFFIX, PERSON_ZIP, PROV_ZIP, FACILITY_ZIP, PROV_FNAME, PROV_LNAME, PERSON_CITY, PROV_CITY, FACILITY_CITY, CURRENT_GENDER, MATCH_CD, PERSON_COUNTY, FACILITY_COUNTY, PROV_COUNTY, MEDICAID_NUMBER, RACE_CD, DOB, PERSON_PHONE, PROV_LICENSE, PROV_PHONE, FACILITY_PHONE, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_CENSUS_TRACT, PROV_LATITUDE, PROV_LONGITUDE, PROV_CENSUS_TRACT, FACILITY_LATITUDE, FACILITY_LONGITUDE, FACILITY_CENSUS_TRACT, ETHNICITY, PERSON_STATE, PROV_STATE, FACILITY_STATE, MED_RECORD_NUMBER, PROV_NPI, FACILITY_NPI, FNAME, MNAME, LNAME, PERSON_ADDRESS, PROV_ADDRESS, FACILITY_ADDRESS, UPLOAD_FILENAME, LAST_VIRAL_LOAD_RESULT, MATCHED_STATENO, PREF_NAME, UPLOAD_ID, REVIEW_UID, BIRTH_SEX, FILE_TYPE_CD, RACE_INDIAN, RACE_BLACK, RACE_ASIAN, RACE_PACIFIC, RACE_WHITE, RACE_MULTIPLE, RACE_OTHER, RACE_UNKNOWN, MATCH_WEIGHT, DOCUMENT_ID, LAST_CD4_RESULT_COUNT, LAST_CD4_RESULT_PERCENT, CURRENT_PROCESS_STATUS, MATCH_PASS, QS_PARENT_DOC_ID, PROV_LAST_VISIT_DATE, LAST_VIRAL_LOAD_DATE, LAST_CD4_DATE, UPLOAD_DT, REVIEW_DT, MATCHED_PROCESS_DATE, EHARS_IMPORT_DATE, NYC_SHIP_DATE, UPLOAD_FILE_ID, QS_MATCH_CD, CONFIRMATORY_RESULT_REPORTED, SUBMITTING_PROVIDER_PRESENT, REVIEW_COMMENT, INTERNAL_CONCLUSION, CARE_STATUS, COMPLETE_DATE, NYS_EHARS_STATENO, NYC_EHARS_STATENO, NYC_CITY_NO, LAST_HIV_LAB_TYPE, LAST_HIV_LAB_DATE, LAST_HIV_LAB_RESULT, MONTHS_SINCE_LAST_HIV_LAB, NYS_VITAL_STATUS, NYC_VITAL_STATUS, MEETS_CASE_DEFINITION_IN, MEETS_CDC_ELIG_ID,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), trim(upper(?41)), trim(upper(?42)), trim(upper(?43)), trim(upper(?44)), trim(upper(?45)), trim(upper(?46)), trim(upper(?47)), ?48, trim(upper(?49)), trim(upper(?50)), trim(upper(?51)), trim(upper(?52)), trim(upper(?53)), trim(upper(?54)), trim(upper(?55)), trim(upper(?56)), trim(upper(?57)), trim(upper(?58)), trim(upper(?59)), trim(upper(?60)), trim(upper(?61)), trim(upper(?62)), trim(upper(?63)), trim(upper(?64)), ?65, ?66, ?67, ?68, ?69, ?70, FN_TO_DATE(?71,'MM/DD/YYYY'), FN_TO_DATE(?72,'MM/DD/YYYY'), FN_TO_DATE(?73,'MM/DD/YYYY'), ?74, ?75, ?76, ?77, ?78, ?79, trim(upper(?80)), trim(upper(?81)), trim(upper(?82)), trim(upper(?83)), trim(upper(?84)), trim(upper(?85)), ?86, trim(upper(?87)), trim(upper(?88)), trim(upper(?89)), trim(upper(?90)), ?91, trim(upper(?92)), trim(?93), trim(upper(?94)), trim(upper(?95)), trim(upper(?96)), trim(upper(?97)),?98,?99,?100,?101,?102)
			""";

	/** The values of {@link #STD_EDITED_EPRF_OOC_DOCUMENT}, in order. */
	static Object[] insertStdEditedEprfOOCDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("NYSID")), text(map.get("FACILITY_NAME")), text(map.get("DIN")),
				text(map.get("SUFFIX")), text(map.get("PERSON_ZIP")), text(map.get("PROV_ZIP")), text(map.get("FACILITY_ZIP")),
				text(map.get("PROV_FNAME")), text(map.get("PROV_LNAME")), text(map.get("PERSON_CITY")), text(map.get("PROV_CITY")),
				text(map.get("FACILITY_CITY")), text(map.get("CURRENT_GENDER")), text(map.get("MATCH_CD")), text(map.get("PERSON_COUNTY")),
				text(map.get("FACILITY_COUNTY")), text(map.get("PROV_COUNTY")), text(map.get("MEDICAID_NUMBER")), text(map.get("RACE_CD")),
				text(map.get("DOB")), text(map.get("PERSON_PHONE")), text(map.get("PROV_LICENSE")), text(map.get("PROV_PHONE")),
				text(map.get("FACILITY_PHONE")), text(map.get("PERSON_LATITUDE")), text(map.get("PERSON_LONGITUDE")), text(map.get("PERSON_CENSUS_TRACT")),
				text(map.get("PROV_LATITUDE")), text(map.get("PROV_LONGITUDE")), text(map.get("PROV_CENSUS_TRACT")), text(map.get("FACILITY_LATITUDE")),
				text(map.get("FACILITY_LONGITUDE")), text(map.get("FACILITY_CENSUS_TRACT")), text(map.get("ETHNICITY")), text(map.get("PERSON_STATE")),
				text(map.get("PROV_STATE")), text(map.get("FACILITY_STATE")), text(map.get("MED_RECORD_NUMBER")), text(map.get("PROV_NPI")),
				text(map.get("FACILITY_NPI")), text(map.get("FNAME")), text(map.get("MNAME")), text(map.get("LNAME")),
				text(map.get("PERSON_ADDRESS")), text(map.get("PROV_ADDRESS")), text(map.get("FACILITY_ADDRESS")), text(map.get("UPLOAD_FILENAME")),
				text(map.get("LAST_VIRAL_LOAD_RESULT")), text(map.get("MATCHED_STATENO")), text(map.get("PREF_NAME")), text(map.get("UPLOAD_ID")),
				text(map.get("REVIEW_UID")), text(map.get("BIRTH_SEX")), text(map.get("FILE_TYPE_CD")), text(map.get("RACE_INDIAN")),
				text(map.get("RACE_BLACK")), text(map.get("RACE_ASIAN")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_WHITE")),
				text(map.get("RACE_MULTIPLE")), text(map.get("RACE_OTHER")), text(map.get("RACE_UNKNOWN")), text(map.get("MATCH_WEIGHT")),
				number(map.get("DOCUMENT_ID")), number(map.get("LAST_CD4_RESULT_COUNT")), number(map.get("LAST_CD4_RESULT_PERCENT")), number(map.get("CURRENT_PROCESS_STATUS")),
				number(map.get("MATCH_PASS")), number(map.get("QS_PARENT_DOC_ID")), date(map.get("PROV_LAST_VISIT_DATE")), date(map.get("LAST_VIRAL_LOAD_DATE")),
				date(map.get("LAST_CD4_DATE")), date(map.get("UPLOAD_DT")), date(map.get("REVIEW_DT")), date(map.get("MATCHED_PROCESS_DATE")),
				date(map.get("EHARS_IMPORT_DATE")), date(map.get("NYC_SHIP_DATE")), number(map.get("UPLOAD_FILE_ID")), text(map.get("QS_MATCH_CD")),
				text(map.get("CONFIRMATORY_RESULT_REPORTED")), text(map.get("SUBMITTING_PROVIDER_PRESENT")), text(map.get("REVIEW_COMMENT")), text(map.get("INTERNAL_CONCLUSION")),
				text(map.get("CARE_STATUS")), date(map.get("COMPLETE_DATE")), text(map.get("NYS_EHARS_STATENO")), text(map.get("NYC_EHARS_STATENO")),
				text(map.get("NYC_CITY_NO")), text(map.get("LAST_HIV_LAB_TYPE")), date(map.get("LAST_HIV_LAB_DATE")), text(map.get("LAST_HIV_LAB_RESULT")),
				number(map.get("MONTHS_SINCE_LAST_HIV_LAB")), text(map.get("NYS_VITAL_STATUS")), text(map.get("NYC_VITAL_STATUS")), text(map.get("MEETS_CASE_DEFINITION_IN")),
				text(map.get("MEETS_CDC_ELIG_ID")), text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")), text(map.get("FLUX_MATCHED_STATENO")),
				number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedEprfOOCDocument}, as the former mapper had it (52 bound values). */
	static final String RAW_EDITED_EPRF_OOC_DOCUMENT = """
			insert into RAW_EPRF_OOC_UPLOAD(SSN, NYSID, FACILITY_NAME, DIN, SUFFIX, ZIPCODE, PROV_ZIP, FACILITY_ZIP, LAST_VIRAL_LOAD_RESULT, PROV_FNAME, PROV_LNAME, CITY, PROV_CITY, FACILITY_CITY, GENDER, CIN, RACE_CD, DOB, TELEPHONE, PROV_LICENSE, PROV_PHONE, FACILITY_PHONE, ETHNICCD, STATE, PROV_STATE, FACILITY_STATE, MEDRECNO, PROV_NPI, FACILITY_NPI, FNAME, MNAME, LNAME, ADDRESS, PROV_ADDR, FACILITY_ADDR, UPLOAD_FILENAME, PREF_NAME, UPLOAD_ID, BIRTH_SEX, FILE_TYPE_CD, DOCUMENT_ID, LAST_CD4_RESULT_COUNT, LAST_CD4_RESULT_PERCENT, CURRENT_PROCESS_STATUS, PROV_LAST_VISIT_DATE, LAST_VIRAL_LOAD_DATE, LAST_CD4_DATE, UPLOAD_DT, PROCESS_DATE, REVIEW_ID, REVIEW_DATE, UPLOAD_FILE_ID)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), ?36, trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), ?41, ?42, ?43, ?44, FN_TO_DATE(?45,'MM/DD/YYYY'), FN_TO_DATE(?46,'MM/DD/YYYY'), FN_TO_DATE(?47,'MM/DD/YYYY'), ?48, ?49, trim(upper(?50)), ?51, trim(upper(?52)))
			""";

	/** The values of {@link #RAW_EDITED_EPRF_OOC_DOCUMENT}, in order. */
	static Object[] insertRawEditedEprfOOCDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("NYSID")), text(map.get("FACILITY_NAME")), text(map.get("DIN")),
				text(map.get("SUFFIX")), text(map.get("ZIPCODE")), text(map.get("PROV_ZIP")), text(map.get("FACILITY_ZIP")),
				text(map.get("LAST_VIRAL_LOAD_RESULT")), text(map.get("PROV_FNAME")), text(map.get("PROV_LNAME")), text(map.get("CITY")),
				text(map.get("PROV_CITY")), text(map.get("FACILITY_CITY")), text(map.get("GENDER")), text(map.get("CIN")),
				text(map.get("RACE_CD")), text(map.get("DOB")), text(map.get("TELEPHONE")), text(map.get("PROV_LICENSE")),
				text(map.get("PROV_PHONE")), text(map.get("FACILITY_PHONE")), text(map.get("ETHNICCD")), text(map.get("STATE")),
				text(map.get("PROV_STATE")), text(map.get("FACILITY_STATE")), text(map.get("MEDRECNO")), text(map.get("PROV_NPI")),
				text(map.get("FACILITY_NPI")), text(map.get("FNAME")), text(map.get("MNAME")), text(map.get("LNAME")),
				text(map.get("ADDRESS")), text(map.get("PROV_ADDR")), text(map.get("FACILITY_ADDR")), text(map.get("UPLOAD_FILENAME")),
				text(map.get("PREF_NAME")), text(map.get("UPLOAD_ID")), text(map.get("BIRTH_SEX")), text(map.get("FILE_TYPE_CD")),
				number(map.get("DOCUMENT_ID")), number(map.get("LAST_CD4_RESULT_COUNT")), number(map.get("LAST_CD4_RESULT_PERCENT")), number(map.get("CURRENT_PROCESS_STATUS")),
				date(map.get("PROV_LAST_VISIT_DATE")), date(map.get("LAST_VIRAL_LOAD_DATE")), date(map.get("LAST_CD4_DATE")), date(map.get("UPLOAD_DT")),
				date(map.get("PROCESS_DATE")), text(map.get("REVIEW_ID")), date(map.get("REVIEW_DATE")), text(map.get("UPLOAD_FILE_ID"))
		};
	}

	/** The SQL of {@code insertStdEditedNycLabDocument}, as the former mapper had it (91 bound values). */
	static final String STD_EDITED_NYC_LAB_DOCUMENT = """
			insert into STD_NYC_EHARS_LAB_DOC(PERSON_CITY, PERSON_COUNTY, LAB_TEST_NAME, PERSON_ADDRESS1, PERSON_ADDRESS2, CREATE_UID, LAB_UNITS, FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_COUNTRY, ETHNICITY, RACE_UNKNOWN, RACE_PACIFIC, RACE_INDIAN, RACE_BLACK, RACE_WHITE, ADDRESS_TYPE_CODE, PERSON_COUNTRY, MATCH_CODE, RACE_ASIAN, FACILITY_UID, EHARS_UID, DEATH_STATE, CURRENT_GENDER, PERSON_STATE, EVENT_CODE, CURRENT_PROCESS_STATUS, SSN, LAB_RESULT, NYEHMS_STATENO, EHARS_STATENO, CITYNO, RECEIVED_DATE, NYSID, DOB_TEXT, DOD_TEXT, ADDRESS_DATE_TEXT, PERSON_ZIP, FIRST_TRANSFUSION_DATE_TEXT, LAST_TRANSFUSION_DATE_TEXT, XLAB_HIV_DATE_TEXT, COLLECTION_DATE_TEXT, DOCTOR_DIAGNOSIS_DATE_TEXT, CANDLUNG_DATE_TEXT, CANDE_DATE_TEXT, CERVCANC_DATE_TEXT, COCCI_DATE_TEXT, CRYPTOCO_DATE_TEXT, CRYPTOSP_DATE_TEXT, CMV_DATE_TEXT, CMVRET_DATE_TEXT, HIVNCEPH_DATE_TEXT, HERPES_DATE_TEXT, HISTO_DATE_TEXT, ISO_DATE_TEXT, KS_DATE_TEXT, BURKL_DATE_TEXT, LYMPHIM_DATE_TEXT, LYMPHB_DATE_TEXT, MYCOAV_DATE_TEXT, MYCOPULM_DATE_TEXT, MYCODISM_DATE_TEXT, MYCOTH_DATE_TEXT, PCP_DATE_TEXT, PNEUM_DATE_TEXT, PML_DATE_TEXT, SALMNELA_DATE_TEXT, TOXO_DATE_TEXT, WASTING_DATE_TEXT, BIRTH_SEX, VITAL_STATUS, SWM, SWF, IDU, SEX_PARTNER_IDU, SEX_PARTNER_MSM, SEX_PARTNER_HEMOPHILIAC, SEX_PARTNER_TRANSFUSION, SEX_PARTNER_TRANSPLANT, SEX_PARTNER_HIV, RECV_BLOOD_PROD, RECV_TRANSPLANT, HEALTHCARE_WORKER, OCCUPATION, RISK_OTHER, INTERPRETATION, DOCTOR_DIAGNOSIS, EHARS_STATUS_CD, DOCUMENT_ID, CREATE_DATE, EHARS_IMPORT_DATE)
			values(trim(upper(?1)),  trim(upper(?2)),  trim(upper(?3)),  trim(upper(?4)),  null,  trim(upper(?5)),  trim(upper(?6)),  trim(upper(?7)),  trim(upper(?8)),  trim(upper(?9)),  trim(upper(?10)),  trim(upper(?11)),  trim(upper(?12)),  trim(upper(?13)),  trim(upper(?14)),  trim(upper(?15)),  trim(upper(?16)),  trim(upper(?17)),  trim(upper(?18)),  trim(upper(?19)),  trim(upper(?20)),  trim(upper(?21)),  trim(upper(?22)),  trim(upper(?23)),  trim(upper(?24)),  trim(upper(?25)),  trim(upper(?26)),  trim(upper(?27)),  trim(upper(?28)),  trim(upper(?29)),  trim(upper(?30)),  trim(upper(?31)),  trim(upper(?32)),  trim(upper(?33)),  trim(upper(?34)),  trim(upper(?35)),  trim(upper(?36)),  trim(upper(?37)),  trim(upper(?38)),  trim(upper(?39)),  trim(upper(?40)),  trim(upper(?41)),  trim(upper(?42)),  trim(upper(?43)),  trim(upper(?44)),  trim(upper(?45)),  trim(upper(?46)),  trim(upper(?47)),  trim(upper(?48)),  trim(upper(?49)),  trim(upper(?50)),  trim(upper(?51)),  trim(upper(?52)),  trim(upper(?53)),  trim(upper(?54)),  trim(upper(?55)),  trim(upper(?56)),  trim(upper(?57)),  trim(upper(?58)),  trim(upper(?59)),  trim(upper(?60)),  trim(upper(?61)),  trim(upper(?62)),  trim(upper(?63)),  trim(upper(?64)),  trim(upper(?65)),  trim(upper(?66)),  trim(upper(?67)),  trim(upper(?68)),  trim(upper(?69)),  trim(upper(?70)),  trim(upper(?71)),  trim(upper(?72)),  trim(upper(?73)),  trim(upper(?74)),  trim(upper(?75)),  trim(upper(?76)),  trim(upper(?77)),  trim(upper(?78)),  trim(upper(?79)),  trim(upper(?80)),  trim(upper(?81)),  trim(upper(?82)),  trim(upper(?83)),  trim(upper(?84)),  trim(upper(?85)),  trim(upper(?86)),  trim(upper(?87)),  trim(upper(?88)),  ?89,  ?90,  ?91)
			""";

	/** The values of {@link #STD_EDITED_NYC_LAB_DOCUMENT}, in order. */
	static Object[] insertStdEditedNycLabDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("PERSON_CITY")), text(map.get("PERSON_COUNTY")), text(map.get("LAB_TEST_NAME")), text(map.get("PERSON_ADDRESS1")),
				text(map.get("CREATE_UID")), text(map.get("LAB_UNITS")), text(map.get("FIRST_NAME")), text(map.get("MIDDLE_NAME")),
				text(map.get("LAST_NAME")), text(map.get("BIRTH_COUNTRY")), text(map.get("ETHNICITY")), text(map.get("RACE_UNKNOWN")),
				text(map.get("RACE_PACIFIC")), text(map.get("RACE_INDIAN")), text(map.get("RACE_BLACK")), text(map.get("RACE_WHITE")),
				text(map.get("ADDRESS_TYPE_CODE")), text(map.get("PERSON_COUNTRY")), text(map.get("MATCH_CODE")), text(map.get("RACE_ASIAN")),
				text(map.get("FACILITY_UID")), text(map.get("EHARS_UID")), text(map.get("DEATH_STATE")), text(map.get("CURRENT_GENDER")),
				text(map.get("PERSON_STATE")), text(map.get("EVENT_CODE")), text(map.get("CURRENT_PROCESS_STATUS")), text(map.get("SSN")),
				text(map.get("LAB_RESULT")), text(map.get("NYEHMS_STATENO")), text(map.get("EHARS_STATENO")), text(map.get("CITYNO")),
				text(map.get("RECEIVED_DATE")), text(map.get("NYSID")), text(map.get("DOB_TEXT")), text(map.get("DOD_TEXT")),
				text(map.get("ADDRESS_DATE_TEXT")), text(map.get("PERSON_ZIP")), text(map.get("FIRST_TRANSFUSION_DATE_TEXT")), text(map.get("LAST_TRANSFUSION_DATE_TEXT")),
				text(map.get("XLAB_HIV_DATE_TEXT")), text(map.get("COLLECTION_DATE_TEXT")), text(map.get("DOCTOR_DIAGNOSIS_DATE_TEXT")), text(map.get("CANDLUNG_DATE_TEXT")),
				text(map.get("CANDE_DATE_TEXT")), text(map.get("CERVCANC_DATE_TEXT")), text(map.get("COCCI_DATE_TEXT")), text(map.get("CRYPTOCO_DATE_TEXT")),
				text(map.get("CRYPTOSP_DATE_TEXT")), text(map.get("CMV_DATE_TEXT")), text(map.get("CMVRET_DATE_TEXT")), text(map.get("HIVNCEPH_DATE_TEXT")),
				text(map.get("HERPES_DATE_TEXT")), text(map.get("HISTO_DATE_TEXT")), text(map.get("ISO_DATE_TEXT")), text(map.get("KS_DATE_TEXT")),
				text(map.get("BURKL_DATE_TEXT")), text(map.get("LYMPHIM_DATE_TEXT")), text(map.get("LYMPHB_DATE_TEXT")), text(map.get("MYCOAV_DATE_TEXT")),
				text(map.get("MYCOPULM_DATE_TEXT")), text(map.get("MYCODISM_DATE_TEXT")), text(map.get("MYCOTH_DATE_TEXT")), text(map.get("PCP_DATE_TEXT")),
				text(map.get("PNEUM_DATE_TEXT")), text(map.get("PML_DATE_TEXT")), text(map.get("SALMNELA_DATE_TEXT")), text(map.get("TOXO_DATE_TEXT")),
				text(map.get("WASTING_DATE_TEXT")), text(map.get("BIRTH_SEX")), text(map.get("VITAL_STATUS")), text(map.get("SWM")),
				text(map.get("SWF")), text(map.get("IDU")), text(map.get("SEX_PARTNER_IDU")), text(map.get("SEX_PARTNER_MSM")),
				text(map.get("SEX_PARTNER_HEMOPHILIAC")), text(map.get("SEX_PARTNER_TRANSFUSION")), text(map.get("SEX_PARTNER_TRANSPLANT")), text(map.get("SEX_PARTNER_HIV")),
				text(map.get("RECV_BLOOD_PROD")), text(map.get("RECV_TRANSPLANT")), text(map.get("HEALTHCARE_WORKER")), text(map.get("OCCUPATION")),
				text(map.get("RISK_OTHER")), text(map.get("INTERPRETATION")), text(map.get("DOCTOR_DIAGNOSIS")), text(map.get("EHARS_STATUS_CD")),
				number(map.get("DOCUMENT_ID")), date(map.get("CREATE_DATE")), date(map.get("EHARS_IMPORT_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedNycLabDocument}, as the former mapper had it (91 bound values). */
	static final String RAW_EDITED_NYC_LAB_DOCUMENT = """
			insert into RAW_NYC_EHARS_LAB_DOC(REVIEW_UID, PERSON_CITY, PERSON_COUNTY, LAB_TEST_NAME, PERSON_ADDRESS1, PERSON_ADDRESS2, CREATE_UID, LAB_UNITS, FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_COUNTRY, ETHNICITY, RACE1, RACE2, RACE3, RACE4, RACE5, ADDRESS_TYPE_CODE, PERSON_COUNTRY, MATCH_CD, FACILITY_UID, EHARS_UID, DEATH_STATE, CURRENT_GENDER, PERSON_STATE, EVENT_CODE, CURRENT_PROCESS_STATUS, SSN, LAB_RESULT, NYEHMS_STATENO, EHARS_STATENO, CITYNO, RECEIVED_DATE, NYSID, DOB_TEXT, DOD_TEXT, ADDRESS_DATE_TEXT, PERSON_ZIPCODE, XLAB_HIV_DATE_TEXT, COLLECTION_DATE_TEXT, DOCTOR_DIAGNOSIS_DATE_TEXT, CANDLUNG_DATE_TEXT, CANDE_DATE_TEXT, CERVCANC_DATE_TEXT, COCCI_DATE_TEXT, CRYPTOCO_DATE_TEXT, CRYPTOSP_DATE_TEXT, CMV_DATE_TEXT, CMVRET_DATE_TEXT, HIVNCEPH_DATE_TEXT, HERPES_DATE_TEXT, HISTO_DATE_TEXT, ISO_DATE_TEXT, KS_DATE_TEXT, BURKL_DATE_TEXT, LYMPHIM_DATE_TEXT, LYMPHB_DATE_TEXT, MYCOAV_DATE_TEXT, MYCOPULM_DATE_TEXT, MYCODISM_DATE_TEXT, MYCOTH_DATE_TEXT, PCP_DATE_TEXT, PNEUM_DATE_TEXT, PML_DATE_TEXT, SALMNELA_DATE_TEXT, TOXO_DATE_TEXT, WASTING_DATE_TEXT, BIRTH_SEX, VITAL_STATUS, SEX_MALE, SEX_FEMALE, IDU, SEX_IDU, SEX_BISEXUAL_MALE, SEX_HEMOPHILIAC, SEX_TRANSFUSION, SEX_TRANSPLANT, SEX_AIDS_HIV, RECEIVED_TRANSFUSION, FIRST_TRANSFUSION_DATE_TEXT, LAST_TRANSFUSION_DATE_TEXT, RECEIVED_TRANSPLANT, HEALTH_WORKER, OCCUPATION, RISK_OTHER, INTERPRETATION, DOCTOR_DIAGNOSIS, DOCUMENT_ID, REVIEW_DATE, CREATE_DATE, PROCESS_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), null, trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), trim(upper(?41)), trim(upper(?42)), trim(upper(?43)), trim(upper(?44)), trim(upper(?45)), trim(upper(?46)), trim(upper(?47)), trim(upper(?48)), trim(upper(?49)), trim(upper(?50)), trim(upper(?51)), trim(upper(?52)), trim(upper(?53)), trim(upper(?54)), trim(upper(?55)), trim(upper(?56)), trim(upper(?57)), trim(upper(?58)), trim(upper(?59)), trim(upper(?60)), trim(upper(?61)), trim(upper(?62)), trim(upper(?63)), trim(upper(?64)), trim(upper(?65)), trim(upper(?66)), trim(upper(?67)), trim(upper(?68)), trim(upper(?69)), trim(upper(?70)), trim(upper(?71)), trim(upper(?72)), trim(upper(?73)), trim(upper(?74)), trim(upper(?75)), trim(upper(?76)), trim(upper(?77)), trim(upper(?78)), trim(upper(?79)), trim(upper(?80)), trim(upper(?81)), trim(upper(?82)), trim(upper(?83)), trim(upper(?84)), trim(upper(?85)), trim(upper(?86)), trim(upper(?87)), ?88, ?89, ?90, ?91)
			""";

	/** The values of {@link #RAW_EDITED_NYC_LAB_DOCUMENT}, in order. */
	static Object[] insertRawEditedNycLabDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("REVIEW_UID")), text(map.get("PERSON_CITY")), text(map.get("PERSON_COUNTY")), text(map.get("LAB_TEST_NAME")),
				text(map.get("PERSON_ADDRESS1")), text(map.get("CREATE_UID")), text(map.get("LAB_UNITS")), text(map.get("FIRST_NAME")),
				text(map.get("MIDDLE_NAME")), text(map.get("LAST_NAME")), text(map.get("BIRTH_COUNTRY")), text(map.get("ETHNICITY")),
				text(map.get("RACE1")), text(map.get("RACE2")), text(map.get("RACE3")), text(map.get("RACE4")),
				text(map.get("RACE5")), text(map.get("ADDRESS_TYPE_CODE")), text(map.get("PERSON_COUNTRY")), text(map.get("MATCH_CD")),
				text(map.get("FACILITY_UID")), text(map.get("EHARS_UID")), text(map.get("DEATH_STATE")), text(map.get("CURRENT_GENDER")),
				text(map.get("PERSON_STATE")), text(map.get("EVENT_CODE")), text(map.get("CURRENT_PROCESS_STATUS")), text(map.get("SSN")),
				text(map.get("LAB_RESULT")), text(map.get("NYEHMS_STATENO")), text(map.get("EHARS_STATENO")), text(map.get("CITYNO")),
				text(map.get("RECEIVED_DATE")), text(map.get("NYSID")), text(map.get("DOB_TEXT")), text(map.get("DOD_TEXT")),
				text(map.get("ADDRESS_DATE_TEXT")), text(map.get("PERSON_ZIPCODE")), text(map.get("XLAB_HIV_DATE_TEXT")), text(map.get("COLLECTION_DATE_TEXT")),
				text(map.get("DOCTOR_DIAGNOSIS_DATE_TEXT")), text(map.get("CANDLUNG_DATE_TEXT")), text(map.get("CANDE_DATE_TEXT")), text(map.get("CERVCANC_DATE_TEXT")),
				text(map.get("COCCI_DATE_TEXT")), text(map.get("CRYPTOCO_DATE_TEXT")), text(map.get("CRYPTOSP_DATE_TEXT")), text(map.get("CMV_DATE_TEXT")),
				text(map.get("CMVRET_DATE_TEXT")), text(map.get("HIVNCEPH_DATE_TEXT")), text(map.get("HERPES_DATE_TEXT")), text(map.get("HISTO_DATE_TEXT")),
				text(map.get("ISO_DATE_TEXT")), text(map.get("KS_DATE_TEXT")), text(map.get("BURKL_DATE_TEXT")), text(map.get("LYMPHIM_DATE_TEXT")),
				text(map.get("LYMPHB_DATE_TEXT")), text(map.get("MYCOAV_DATE_TEXT")), text(map.get("MYCOPULM_DATE_TEXT")), text(map.get("MYCODISM_DATE_TEXT")),
				text(map.get("MYCOTH_DATE_TEXT")), text(map.get("PCP_DATE_TEXT")), text(map.get("PNEUM_DATE_TEXT")), text(map.get("PML_DATE_TEXT")),
				text(map.get("SALMNELA_DATE_TEXT")), text(map.get("TOXO_DATE_TEXT")), text(map.get("WASTING_DATE_TEXT")), text(map.get("BIRTH_SEX")),
				text(map.get("VITAL_STATUS")), text(map.get("SEX_MALE")), text(map.get("SEX_FEMALE")), text(map.get("IDU")),
				text(map.get("SEX_IDU")), text(map.get("SEX_BISEXUAL_MALE")), text(map.get("SEX_HEMOPHILIAC")), text(map.get("SEX_TRANSFUSION")),
				text(map.get("SEX_TRANSPLANT")), text(map.get("SEX_AIDS_HIV")), text(map.get("RECEIVED_TRANSFUSION")), text(map.get("FIRST_TRANSFUSION_DATE_TEXT")),
				text(map.get("LAST_TRANSFUSION_DATE_TEXT")), text(map.get("RECEIVED_TRANSPLANT")), text(map.get("HEALTH_WORKER")), text(map.get("OCCUPATION")),
				text(map.get("RISK_OTHER")), text(map.get("INTERPRETATION")), text(map.get("DOCTOR_DIAGNOSIS")), number(map.get("DOCUMENT_ID")),
				date(map.get("REVIEW_DATE")), date(map.get("CREATE_DATE")), date(map.get("PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedNycEharsDocument}, as the former mapper had it (43 bound values). */
	static final String RAW_EDITED_NYC_EHARS_DOCUMENT = """
			insert into RAW_NYC_EHARS_DOC(SSN, CURRENT_ADDRESS, CURRENT_ZIP_CODE, CURRENT_CITY, LAST_NAME, CURRENT_COUNTY_FIPS, BIRTH_COUNTRY, CURRENT_GENDER, FIRST_NAME, MIDDLE_NAME, NYC_EHARS_UID, CURRENT_STATE, ETHNICITY, NYEHMS_STATENO, CITYNO, DOB_TEXT, CURRENT_ADDRESS_DATE, DOD_TEXT, CREATEID, BIRTH_SEX, VITAL_STATUS, RACE_INDIAN, RACE_ASIAN, RACE_BLACK, RACE_PACIFIC, RACE_WHITE, SWM, SWF, IDU, SEX_IDU, TRANSFUSION, SEX_BISEXUAL_MALE, CLOTTING_FACTOR, SEX_TRANSFUSION, SEX_TRANSPLANT, TRANSPLANT, HCW_RISK, EHARS_STATUS_CD, DOCUMENT_ID, CURRENT_PROCESS_STATUS, CREATE_DATE, EHARS_IMPORT_DATE, PROCESS_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), ?39, ?40, ?41, ?42, ?43)
			""";

	/** The values of {@link #RAW_EDITED_NYC_EHARS_DOCUMENT}, in order. */
	static Object[] insertRawEditedNycEharsDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("CURRENT_ADDRESS")), text(map.get("CURRENT_ZIP_CODE")), text(map.get("CURRENT_CITY")),
				text(map.get("LAST_NAME")), text(map.get("CURRENT_COUNTY_FIPS")), text(map.get("BIRTH_COUNTRY")), text(map.get("CURRENT_GENDER")),
				text(map.get("FIRST_NAME")), text(map.get("MIDDLE_NAME")), text(map.get("NYC_EHARS_UID")), text(map.get("CURRENT_STATE")),
				text(map.get("ETHNICITY")), text(map.get("NYEHMS_STATENO")), text(map.get("CITYNO")), text(map.get("DOB_TEXT")),
				text(map.get("CURRENT_ADDRESS_DATE")), text(map.get("DOD_TEXT")), text(map.get("CREATEID")), text(map.get("BIRTH_SEX")),
				text(map.get("VITAL_STATUS")), text(map.get("RACE_INDIAN")), text(map.get("RACE_ASIAN")), text(map.get("RACE_BLACK")),
				text(map.get("RACE_PACIFIC")), text(map.get("RACE_WHITE")), text(map.get("SWM")), text(map.get("SWF")),
				text(map.get("IDU")), text(map.get("SEX_IDU")), text(map.get("TRANSFUSION")), text(map.get("SEX_BISEXUAL_MALE")),
				text(map.get("CLOTTING_FACTOR")), text(map.get("SEX_TRANSFUSION")), text(map.get("SEX_TRANSPLANT")), text(map.get("TRANSPLANT")),
				text(map.get("HCW_RISK")), text(map.get("EHARS_STATUS_CD")), number(map.get("DOCUMENT_ID")), number(map.get("CURRENT_PROCESS_STATUS")),
				number(map.get("CREATE_DATE")), date(map.get("EHARS_IMPORT_DATE")), date(map.get("PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedNycEharsDocument}, as the former mapper had it (46 bound values). */
	static final String STD_EDITED_NYC_EHARS_DOCUMENT = """
			insert into STD_NYC_EHARS_DOC(HIV_CATEG, AIDS_CATEG, HIV_DX_DT, AIDS_DX_DT, SSN, PERSON_ADDRESS, PERSON_ZIP, PERSON_CITY, LNAME, PERSON_COUNTY, BIRTH_COUNTRY, CURRENT_GENDER, FNAME, MNAME, NYC_EHARS_UID, PERSON_STATE, ETHNICITY, NYEHMS_STATENO, CITYNO, DOB_TEXT, PERSON_ADDRESS_DATE, DOD_TEXT, CREATEID, BIRTH_SEX, VITAL_STATUS, RACE_INDIAN, RACE_ASIAN, RACE_BLACK, RACE_PACIFIC, RACE_WHITE, SWM, SWF, IDU, SEX_PARTNER_IDU, RECV_BLOOD_PROD, SEX_PARTNER_MSM, RECV_CLOTTING_FACTOR, SEX_PARTNER_TRANSFUSION, SEX_PARTNER_TRANSPLANT, RECV_TRANSPLANT, HEALTHCARE_WORKER, EHARS_STATUS_CD, DOCUMENT_ID, CURRENT_PROCESS_STATUS, CREATE_DT, EHARS_IMPORT_DATE)
				values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), trim(upper(?41)), trim(upper(?42)), ?43, ?44, ?45, ?46)
			""";

	/** The values of {@link #STD_EDITED_NYC_EHARS_DOCUMENT}, in order. */
	static Object[] insertStdEditedNycEharsDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("HIV_CATEG")), text(map.get("AIDS_CATEG")), text(map.get("HIV_DX_DT")), text(map.get("AIDS_DX_DT")),
				text(map.get("SSN")), text(map.get("PERSON_ADDRESS")), text(map.get("PERSON_ZIP")), text(map.get("PERSON_CITY")),
				text(map.get("LNAME")), text(map.get("PERSON_COUNTY")), text(map.get("BIRTH_COUNTRY")), text(map.get("CURRENT_GENDER")),
				text(map.get("FNAME")), text(map.get("MNAME")), text(map.get("NYC_EHARS_UID")), text(map.get("PERSON_STATE")),
				text(map.get("ETHNICITY")), text(map.get("NYEHMS_STATENO")), text(map.get("CITYNO")), text(map.get("DOB_TEXT")),
				text(map.get("PERSON_ADDRESS_DATE")), text(map.get("DOD_TEXT")), text(map.get("CREATEID")), text(map.get("BIRTH_SEX")),
				text(map.get("VITAL_STATUS")), text(map.get("RACE_INDIAN")), text(map.get("RACE_ASIAN")), text(map.get("RACE_BLACK")),
				text(map.get("RACE_PACIFIC")), text(map.get("RACE_WHITE")), text(map.get("SWM")), text(map.get("SWF")),
				text(map.get("IDU")), text(map.get("SEX_PARTNER_IDU")), text(map.get("RECV_BLOOD_PROD")), text(map.get("SEX_PARTNER_MSM")),
				text(map.get("RECV_CLOTTING_FACTOR")), text(map.get("SEX_PARTNER_TRANSFUSION")), text(map.get("SEX_PARTNER_TRANSPLANT")), text(map.get("RECV_TRANSPLANT")),
				text(map.get("HEALTHCARE_WORKER")), text(map.get("EHARS_STATUS_CD")), number(map.get("DOCUMENT_ID")), number(map.get("CURRENT_PROCESS_STATUS")),
				date(map.get("CREATE_DT")), date(map.get("EHARS_IMPORT_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedHIVLabDocument}, as the former mapper had it (222 bound values). */
	static final String RAW_EDITED_HIV_LAB_DOCUMENT = """
			insert into RAW_HIV_ECLRS_DOC(ZLRFACILITYCODE, ZLRFACILITYNAME, OBRPROVIDERID, OBRPROVIDERLASTNAME, OBRPROVIDERFIRSTNAME, OBRPROVIDERMIDDLENAME, OBRPROVIDERNAMESUFFIX, OBRPROVIDERIDALT1, OBRPROVIDERIDALT2, MSHVERSIONID, REVIEW_UID, PIDDIN, PIDPATIENTLANGUAGE, OBXUNITOFMEASURE, OBXPRODUCERCLIAID, OBXPRODUCERLABNAME, OBXOBSERVATIONMETHOD, PIDLENGTHOFRESIDENCY, OBXABNORMALFLAG, PIDLASTNAME, PIDFIRSTNAME, PIDMIDDLENAME, PIDNAMESUFFIX, OBXLOINCDESCSHORT, OBXOBSERVATIONRESULTTEXTSHORT, PIDHOMEPHONE, PIDWORKPHONE, ZLRNEXTOFKINLASTNAME, ZLRNEXTOFKINFIRSTNAME, ZLRNEXTOFKINMIDDLENAME, ZLRNEXTOFKINNAMESUFFIX, ZLRNEXTOFKINRELATIONSHIP, ZLRNEXTOFKINPHONE, PIDCELLPHONE, OBRCALLBACKPHONE, OBRCALLBACKPHONEALT, ZLRFACILITYPHONE, OBRPROVIDERFAX, OBXLOCALRESULTCODE, PIDSETID, OBRSETID, OBXPROGRAMCODE, PIDFACILITYIDEXTERNAL, PIDFACILITYIDINTERNAL, PIDCOUNTRYOFORIGIN, PIDUPDATEID, OBXLOGONID, PROGRAMCODEUPDATEID, OBXPROGRAMCODEUPDATEID, MSHPROCESSINGID, MSHSOURCETYPE, OBXVALUETYPE, MSHMESSAGEDATERAW, PIDDATEOFBIRTHRAW, PIDDEATHDATERAW, OBRCOLLECTIONDATERAW, OBRRESULTREPORTDATERAW, OBROBSERVATIONDATERAW, OBRRECEIVEDDATERAW, OBXOBSERVATIONDATERAW, OBXANALYSISDATERAW, PIDRACECODERAW, OBRSPECIMENSOURCENAME, OBRINITRESLOINCDESC, OBRINITRESLOCALTESTDESC, OBRINITRESOBSERVATIONTEXT, OBXLOINCDESC, OBXSNOMEDDESC, OBXLOCALTESTDESC, OBXLOCALRESULTDESC, OBXICD_DESC, PIDCOUNTYCODEORIGINAL, PIDMEDICALRECORDNUMBER, OBRACCESSIONNUM, OBRINITRESPLACERSORDERNUM, OBRINITRESACCESSIONNUM, PIDASSIGNAUTHOID, OBRLOCALCODE, OBRANALYSISDESC, OBRPATHOLOGISTID, OBRPATHOLOGISTLASTNAME, OBRPATHOLOGISTFIRSTNAME, OBRPATHOLOGISTMIDDLENAME, OBRPATHOLOGISTNAMESUFFIX, MSHMESSAGECONTROLID, PIDPATIENTIDEXTERNAL, PIDPATIENTIDINTERNAL, PIDPATIENTIDALT, PIDPATIENTACCOUNTNUM, PIDMOTHERSID, PIDMEDICAIDNUMBER, ZLRAGE, OBRINITRESLOCALTESTCODE, OBRINITRESSUBID, OBXOBSERVATIONSUBID, OBXSNOMEDCODE, OBXLOCALTESTCODE, OBXICD_CODETYPE, OBXICD_CODE, PIDCOUNTYGAZCODEORIG, PIDTYPEOFINSURANCE, MSHSENDINGFACILITYNAME, PIDSSN, MSHFILENAME, OBRSNOMEDCODE, PIDNYSID, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, PIDCOUNTRYCODE, ZLRNEXTOFKINADDRESSLINE1, ZLRNEXTOFKINADDRESSLINE2, ZLRNEXTOFKINCITY, ZLRNEXTOFKINSTATE, ZLRNEXTOFKINZIPCODE, ZLRNEXTOFKINCOUNTRY, PIDEMAIL, ZLRPROVIDERADDRESSLINE1, ZLRPROVIDERADDRESSLINE2, ZLRPROVIDERCITY, ZLRPROVIDERSTATE, ZLRPROVIDERZIPCODE, ZLRPROVIDERCOUNTRY, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2, ZLRFACILITYCITY, ZLRFACILITYSTATE, ZLRFACILITYZIPCODE, ZLRFACILITYCOUNTRY, OBRPROVIDEREMAIL, PIDASSIGNAUTHDESC, OBRPLACERSORDERNUM, OBRFILLERSORDERNUM, OBXREFERENCERANGE, MSHSENDINGFACILITYCLIA, MSHLABPFI, PIDFACILITYIDALT, PIDETHNICGROUPCODE, OBRLOINCCODE, OBRSPECIMENSOURCECODE, OBRPROVIDERIDTYPECODE, OBRINITRESLOINCCODE, OBRPROVIDERIDTYPECODEALT1, OBRPROVIDERIDTYPECODEALT2, OBXSETID, OBXLOINCCODE, OBXSENDINGFACILITYCLIA, PIDNOTE, OBRRELEVANTCLINICALINFO, OBRNOTE, OBXOBSERVATIONRESULTTEXT, OBXNOTE, PIDSMSA, MSHFILECONTENTTYPE, MSHDELETEDIND, PIDSEXCODE, PIDRACECODE, PIDRACEASIAN, PIDRACEBLACK, PIDRACENATIVEAMERALASKAN, PIDRACEOTHER, PIDRACEPACIFIC, PIDRACEUNKNOWN, PIDRACEWHITE, PIDMARITALSTATUSCODE, PIDDEATHIND, PIDUSBORNIND, PIDVACCINETRIALIND, PIDNONHUMANIND, PIDDELETEDIND, OBRRESULTSTATUSCODE, OBRDELETEDIND, OBXRESULTSTATUSCODE, OBXPROCESSEDIND, OBXLEADPURPOSE, OBXDELETEDIND, MSHMESSAGEDATE, MSHCREATEDATE, MSHDELETEDATE, PIDCREATEDATE, PIDDATEOFBIRTH, PIDDEATHDATE, PIDUPDATEDATE, PIDDELETEDATE, OBRCREATEDATE, OBRCOLLECTIONDATE, OBRRESULTREPORTDATE, OBRDELETEDATE, OBROBSERVATIONDATE, OBRRECEIVEDDATE, OBXOBSERVATIONDATE, OBXPROCESSEDDATE, OBXDELETEDATE, PROGRAMCODEUPDATEDATE, OBXPROGRAMCODEUPDATEDATE, OBXCREATEDATE, OBXANALYSISDATE, OBXDISEASE, OBXDISEASEGROUP, PIDCOUNTYCODE, OBXCOUNTYCODE, MSHMESSAGEMASTERKEY, PIDPATIENTKEY, PIDMESSAGEMASTERKEY, OBROBSERVATIONREQUESTKEY, ZLROBSERVATIONREQUESTKEY, OBRPATIENTKEY, OBRPATIENTKEYORIG, OBRINITRESKEY, OBXOBSERVATIONRESULTKEY, OBXOBSERVATIONREQUESTKEY, OBXOLDESTDUPLICATERESULTKEY, DOCUMENT_ID, INTERPRETATION_STD_DOC_ID, CURRENT_PROCESS_STATUS, HASH_UNIQUE_NUMBER, PIDLONGITUDE, PIDLATITUDE, PROCESS_DATE, NYC_SHIP_DATE, REVIEW_DATE, CREATE_UID, CREATE_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), trim(upper(?41)), trim(upper(?42)), trim(upper(?43)), trim(upper(?44)), trim(upper(?45)), trim(upper(?46)), trim(upper(?47)), trim(upper(?48)), trim(upper(?49)), trim(upper(?50)), trim(upper(?51)), trim(upper(?52)), trim(upper(?53)), trim(upper(?54)), trim(upper(?55)), trim(upper(?56)), trim(upper(?57)), trim(upper(?58)), trim(upper(?59)), trim(upper(?60)), trim(upper(?61)), trim(upper(?62)), trim(upper(?63)), trim(upper(?64)), trim(upper(?65)), trim(upper(?66)), trim(upper(?67)), trim(upper(?68)), trim(upper(?69)), trim(upper(?70)), trim(upper(?71)), trim(upper(?72)), trim(upper(?73)), trim(upper(?74)), trim(upper(?75)), trim(upper(?76)), trim(upper(?77)), trim(upper(?78)), trim(upper(?79)), trim(upper(?80)), trim(upper(?81)), trim(upper(?82)), trim(upper(?83)), trim(upper(?84)), trim(upper(?85)), trim(upper(?86)), trim(upper(?87)), trim(upper(?88)), trim(upper(?89)), trim(upper(?90)), trim(upper(?91)), trim(upper(?92)), trim(upper(?93)), trim(upper(?94)), trim(upper(?95)), trim(upper(?96)), trim(upper(?97)), trim(upper(?98)), trim(upper(?99)), trim(upper(?100)), trim(upper(?101)), trim(upper(?102)), trim(upper(?103)), trim(upper(?104)), trim(upper(?105)), trim(upper(?106)), trim(upper(?107)), null, trim(upper(?108)), trim(upper(?109)), trim(upper(?110)), trim(upper(?111)), trim(upper(?112)), trim(upper(?113)), trim(upper(?114)), trim(upper(?115)), trim(upper(?116)), trim(upper(?117)), trim(upper(?118)), trim(upper(?119)), null, trim(upper(?120)), trim(upper(?121)), trim(upper(?122)), trim(upper(?123)), trim(upper(?124)), null, trim(upper(?125)), trim(upper(?126)), trim(upper(?127)), trim(upper(?128)), trim(upper(?129)), trim(upper(?130)), trim(upper(?131)), trim(upper(?132)), trim(upper(?133)), trim(upper(?134)), trim(upper(?135)), trim(upper(?136)), trim(upper(?137)), trim(upper(?138)), trim(upper(?139)), trim(upper(?140)), trim(upper(?141)), trim(upper(?142)), trim(upper(?143)), trim(upper(?144)), trim(upper(?145)), trim(upper(?146)), trim(upper(?147)), trim(upper(?148)), trim(upper(?149)), trim(upper(?150)), trim(upper(?151)), trim(upper(?152)), trim(upper(?153)), trim(upper(?154)), trim(upper(?155)), trim(upper(?156)), trim(upper(?157)), trim(upper(?158)), trim(upper(?159)), trim(upper(?160)), trim(upper(?161)), trim(upper(?162)), trim(upper(?163)), trim(upper(?164)), trim(upper(?165)), trim(upper(?166)), trim(upper(?167)), trim(upper(?168)), trim(upper(?169)), trim(upper(?170)), trim(upper(?171)), trim(upper(?172)), trim(upper(?173)), trim(upper(?174)), trim(upper(?175)), trim(upper(?176)), trim(upper(?177)), trim(upper(?178)), trim(upper(?179)), FN_TO_DATE(?180,'MM/DD/YYYY'), ?181, ?182, ?183, ?184, FN_TO_DATE(?185,'MM/DD/YYYY'), FN_TO_DATE(?186,'MM/DD/YYYY'), ?187, ?188, ?189, FN_TO_DATE(?190,'MM/DD/YYYY'), ?191, ?192, ?193, ?194, ?195, ?196, ?197, ?198, ?199, ?200, ?201, ?202, ?203, ?204, ?205, ?206, ?207, ?208, ?209, ?210, ?211, ?212, ?213, ?214, ?215, ?216, ?217, ?218, ?219, ?220,trim(upper(?221)),?222)
			""";

	/** The values of {@link #RAW_EDITED_HIV_LAB_DOCUMENT}, in order. */
	static Object[] insertRawEditedHIVLabDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("ZLRFACILITYCODE")), text(map.get("ZLRFACILITYNAME")), text(map.get("OBRPROVIDERID")), text(map.get("OBRPROVIDERLASTNAME")),
				text(map.get("OBRPROVIDERFIRSTNAME")), text(map.get("OBRPROVIDERMIDDLENAME")), text(map.get("OBRPROVIDERNAMESUFFIX")), text(map.get("OBRPROVIDERIDALT1")),
				text(map.get("OBRPROVIDERIDALT2")), text(map.get("MSHVERSIONID")), text(map.get("REVIEW_UID")), text(map.get("PIDDIN")),
				text(map.get("PIDPATIENTLANGUAGE")), text(map.get("OBXUNITOFMEASURE")), text(map.get("OBXPRODUCERCLIAID")), text(map.get("OBXPRODUCERLABNAME")),
				text(map.get("OBXOBSERVATIONMETHOD")), text(map.get("PIDLENGTHOFRESIDENCY")), text(map.get("OBXABNORMALFLAG")), text(map.get("PIDLASTNAME")),
				text(map.get("PIDFIRSTNAME")), text(map.get("PIDMIDDLENAME")), text(map.get("PIDNAMESUFFIX")), text(map.get("OBXLOINCDESCSHORT")),
				text(map.get("OBXOBSERVATIONRESULTTEXTSHORT")), text(map.get("PIDHOMEPHONE")), text(map.get("PIDWORKPHONE")), text(map.get("ZLRNEXTOFKINLASTNAME")),
				text(map.get("ZLRNEXTOFKINFIRSTNAME")), text(map.get("ZLRNEXTOFKINMIDDLENAME")), text(map.get("ZLRNEXTOFKINNAMESUFFIX")), text(map.get("ZLRNEXTOFKINRELATIONSHIP")),
				text(map.get("ZLRNEXTOFKINPHONE")), text(map.get("PIDCELLPHONE")), text(map.get("OBRCALLBACKPHONE")), text(map.get("OBRCALLBACKPHONEALT")),
				text(map.get("ZLRFACILITYPHONE")), text(map.get("OBRPROVIDERFAX")), text(map.get("OBXLOCALRESULTCODE")), text(map.get("PIDSETID")),
				text(map.get("OBRSETID")), text(map.get("OBXPROGRAMCODE")), text(map.get("PIDFACILITYIDEXTERNAL")), text(map.get("PIDFACILITYIDINTERNAL")),
				text(map.get("PIDCOUNTRYOFORIGIN")), text(map.get("PIDUPDATEID")), text(map.get("OBXLOGONID")), text(map.get("PROGRAMCODEUPDATEID")),
				text(map.get("OBXPROGRAMCODEUPDATEID")), text(map.get("MSHPROCESSINGID")), text(map.get("MSHSOURCETYPE")), text(map.get("OBXVALUETYPE")),
				text(map.get("MSHMESSAGEDATERAW")), text(map.get("PIDDATEOFBIRTHRAW")), text(map.get("PIDDEATHDATERAW")), text(map.get("OBRCOLLECTIONDATERAW")),
				text(map.get("OBRRESULTREPORTDATERAW")), text(map.get("OBROBSERVATIONDATERAW")), text(map.get("OBRRECEIVEDDATERAW")), text(map.get("OBXOBSERVATIONDATERAW")),
				text(map.get("OBXANALYSISDATERAW")), text(map.get("PIDRACECODERAW")), text(map.get("OBRSPECIMENSOURCENAME")), text(map.get("OBRINITRESLOINCDESC")),
				text(map.get("OBRINITRESLOCALTESTDESC")), text(map.get("OBRINITRESOBSERVATIONTEXT")), text(map.get("OBXLOINCDESC")), text(map.get("OBXSNOMEDDESC")),
				text(map.get("OBXLOCALTESTDESC")), text(map.get("OBXLOCALRESULTDESC")), text(map.get("OBXICD_DESC")), text(map.get("PIDCOUNTYCODEORIGINAL")),
				text(map.get("PIDMEDICALRECORDNUMBER")), text(map.get("OBRACCESSIONNUM")), text(map.get("OBRINITRESPLACERSORDERNUM")), text(map.get("OBRINITRESACCESSIONNUM")),
				text(map.get("PIDASSIGNAUTHOID")), text(map.get("OBRLOCALCODE")), text(map.get("OBRANALYSISDESC")), text(map.get("OBRPATHOLOGISTID")),
				text(map.get("OBRPATHOLOGISTLASTNAME")), text(map.get("OBRPATHOLOGISTFIRSTNAME")), text(map.get("OBRPATHOLOGISTMIDDLENAME")), text(map.get("OBRPATHOLOGISTNAMESUFFIX")),
				text(map.get("MSHMESSAGECONTROLID")), text(map.get("PIDPATIENTIDEXTERNAL")), text(map.get("PIDPATIENTIDINTERNAL")), text(map.get("PIDPATIENTIDALT")),
				text(map.get("PIDPATIENTACCOUNTNUM")), text(map.get("PIDMOTHERSID")), text(map.get("PIDMEDICAIDNUMBER")), text(map.get("ZLRAGE")),
				text(map.get("OBRINITRESLOCALTESTCODE")), text(map.get("OBRINITRESSUBID")), text(map.get("OBXOBSERVATIONSUBID")), text(map.get("OBXSNOMEDCODE")),
				text(map.get("OBXLOCALTESTCODE")), text(map.get("OBXICD_CODETYPE")), text(map.get("OBXICD_CODE")), text(map.get("PIDCOUNTYGAZCODEORIG")),
				text(map.get("PIDTYPEOFINSURANCE")), text(map.get("MSHSENDINGFACILITYNAME")), text(map.get("PIDSSN")), text(map.get("MSHFILENAME")),
				text(map.get("OBRSNOMEDCODE")), text(map.get("PIDNYSID")), text(map.get("PIDADDRESSLINE1")), text(map.get("PIDCITY")),
				text(map.get("PIDSTATE")), text(map.get("PIDZIPCODE")), text(map.get("PIDCOUNTRYCODE")), text(map.get("ZLRNEXTOFKINADDRESSLINE1")),
				text(map.get("ZLRNEXTOFKINADDRESSLINE2")), text(map.get("ZLRNEXTOFKINCITY")), text(map.get("ZLRNEXTOFKINSTATE")), text(map.get("ZLRNEXTOFKINZIPCODE")),
				text(map.get("ZLRNEXTOFKINCOUNTRY")), text(map.get("PIDEMAIL")), text(map.get("ZLRPROVIDERADDRESSLINE1")), text(map.get("ZLRPROVIDERCITY")),
				text(map.get("ZLRPROVIDERSTATE")), text(map.get("ZLRPROVIDERZIPCODE")), text(map.get("ZLRPROVIDERCOUNTRY")), text(map.get("ZLRFACILITYADDRESSLINE1")),
				text(map.get("ZLRFACILITYCITY")), text(map.get("ZLRFACILITYSTATE")), text(map.get("ZLRFACILITYZIPCODE")), text(map.get("ZLRFACILITYCOUNTRY")),
				text(map.get("OBRPROVIDEREMAIL")), text(map.get("PIDASSIGNAUTHDESC")), text(map.get("OBRPLACERSORDERNUM")), text(map.get("OBRFILLERSORDERNUM")),
				text(map.get("OBXREFERENCERANGE")), text(map.get("MSHSENDINGFACILITYCLIA")), text(map.get("MSHLABPFI")), text(map.get("PIDFACILITYIDALT")),
				text(map.get("PIDETHNICGROUPCODE")), text(map.get("OBRLOINCCODE")), text(map.get("OBRSPECIMENSOURCECODE")), text(map.get("OBRPROVIDERIDTYPECODE")),
				text(map.get("OBRINITRESLOINCCODE")), text(map.get("OBRPROVIDERIDTYPECODEALT1")), text(map.get("OBRPROVIDERIDTYPECODEALT2")), text(map.get("OBXSETID")),
				text(map.get("OBXLOINCCODE")), text(map.get("OBXSENDINGFACILITYCLIA")), text(map.get("PIDNOTE")), text(map.get("OBRRELEVANTCLINICALINFO")),
				text(map.get("OBRNOTE")), text(map.get("OBXOBSERVATIONRESULTTEXT")), text(map.get("OBXNOTE")), text(map.get("PIDSMSA")),
				text(map.get("MSHFILECONTENTTYPE")), text(map.get("MSHDELETEDIND")), text(map.get("PIDSEXCODE")), text(map.get("PIDRACECODE")),
				text(map.get("PIDRACEASIAN")), text(map.get("PIDRACEBLACK")), text(map.get("PIDRACENATIVEAMERALASKAN")), text(map.get("PIDRACEOTHER")),
				text(map.get("PIDRACEPACIFIC")), text(map.get("PIDRACEUNKNOWN")), text(map.get("PIDRACEWHITE")), text(map.get("PIDMARITALSTATUSCODE")),
				text(map.get("PIDDEATHIND")), text(map.get("PIDUSBORNIND")), text(map.get("PIDVACCINETRIALIND")), text(map.get("PIDNONHUMANIND")),
				text(map.get("PIDDELETEDIND")), text(map.get("OBRRESULTSTATUSCODE")), text(map.get("OBRDELETEDIND")), text(map.get("OBXRESULTSTATUSCODE")),
				text(map.get("OBXPROCESSEDIND")), text(map.get("OBXLEADPURPOSE")), text(map.get("OBXDELETEDIND")), text(map.get("MSHMESSAGEDATE")),
				text(map.get("MSHCREATEDATE")), text(map.get("MSHDELETEDATE")), text(map.get("PIDCREATEDATE")), date(map.get("PIDDATEOFBIRTH")),
				timestamp(map.get("PIDDEATHDATE")), timestamp(map.get("PIDUPDATEDATE")), timestamp(map.get("PIDDELETEDATE")), timestamp(map.get("OBRCREATEDATE")),
				date(map.get("OBRCOLLECTIONDATE")), date(map.get("OBRRESULTREPORTDATE")), timestamp(map.get("OBRDELETEDATE")), timestamp(map.get("OBROBSERVATIONDATE")),
				timestamp(map.get("OBRRECEIVEDDATE")), date(map.get("OBXOBSERVATIONDATE")), timestamp(map.get("OBXPROCESSEDDATE")), timestamp(map.get("OBXDELETEDATE")),
				timestamp(map.get("PROGRAMCODEUPDATEDATE")), timestamp(map.get("OBXPROGRAMCODEUPDATEDATE")), timestamp(map.get("OBXCREATEDATE")), timestamp(map.get("OBXANALYSISDATE")),
				number(map.get("OBXDISEASE")), number(map.get("OBXDISEASEGROUP")), number(map.get("PIDCOUNTYCODE")), number(map.get("OBXCOUNTYCODE")),
				number(map.get("MSHMESSAGEMASTERKEY")), number(map.get("PIDPATIENTKEY")), number(map.get("PIDMESSAGEMASTERKEY")), number(map.get("OBROBSERVATIONREQUESTKEY")),
				number(map.get("ZLROBSERVATIONREQUESTKEY")), number(map.get("OBRPATIENTKEY")), number(map.get("OBRPATIENTKEYORIG")), number(map.get("OBRINITRESKEY")),
				number(map.get("OBXOBSERVATIONRESULTKEY")), number(map.get("OBXOBSERVATIONREQUESTKEY")), number(map.get("OBXOLDESTDUPLICATERESULTKEY")), number(map.get("DOCUMENT_ID")),
				number(map.get("INTERPRETATION_STD_DOC_ID")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("HASH_UNIQUE_NUMBER")), number(map.get("PIDLONGITUDE")),
				number(map.get("PIDLATITUDE")), date(map.get("PROCESS_DATE")), date(map.get("NYC_SHIP_DATE")), date(map.get("REVIEW_DATE")),
				text(map.get("CREATE_UID")), text(map.get("CREATE_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedHivLabDocument}, as the former mapper had it (109 bound values). */
	static final String STD_EDITED_HIV_LAB_DOCUMENT = """
			insert into STD_HIV_ECLRS_DOC(SSN, PROV_FNAME, PROV_MNAME, PROV_SUFFIX, DIN, PERSON_CENSUS_TRACT, PROV_CENSUS_TRACT, FACILITY_CENSUS_TRACT, PERSON_CITY, UNIT_MEASURE_CODE, PERSON_INTERNAL_ID, PERSON_EXTERNAL_ID, AGE, PERSON_ZIP, PROV_ZIP, FACILITY_ZIP, FNAME, LNAME, MNAME, SUFFIX, LOINC_DESC, TEST_RESULT, BIRTH_COUNTRY, PERSON_PHONE, PROV_PHONE, FACILITY_PHONE, PERSON_PLUS4, PROV_PLUS4, FACILITY_PLUS4, PROV_CITY, FACILITY_CITY, REFERRING_FACILITY, TESTING_FACILITY, PFI, STATENO, PERSON_COUNTY, ETHNICITY, PROV_COUNTY, FACILITY_COUNTY, LENGTH_RESIDENCY_IN_USA, SPECIMEN_SOURCE_NAME, LOCAL_TEST_DESC, MED_RECORD_NUMBER, INSURANCE_NUMBER, NYSID, ACCESSION_NUMBER, PROV_ADDRESS, FACILITY_ADDRESS, AREA_OF_BIRTH, MEDICAID_NUMBER, LOCAL_TEST_CODE, PERSON_STATE, GENDER, INSURANCE_CD, PROV_STATE, FACILITY_STATE, MATCH_CD, HIV_VACCINE_RECIP, RESIDE_OUTSIDE_USA, SENDING_FACILITY_NAME, FACILITY_NAME, PROV_LNAME, PERSON_LONGITUDE, PROV_LONGITUDE, FACILITY_LONGITUDE, PERSON_COUNTRY, INTERPRET_CD, PERSON_ADDRESS, MATCHED_STATENO, DOB_TEXT, PERSON_LATITUDE, PROV_LATITUDE, LOINC, SPECIMEN_SOURCE_CD, REVIEW_UID, SENDING_FACILITY_CLIA, FACILITY_LATITUDE, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, EHARS_STATUS_CD, MATCH_WEIGHT, DOCUMENT_ID, LAB_SRC_ID, CURRENT_PROCESS_STATUS, MATCH_PASS, QS_PARENT_DOC_ID, AGE_CATEGORY, OBSERVATION_DATE, COLLECTION_DATE, RESULT_REPORT_DATE, EHARS_IMPORT_DATE, DATE_RECEIVED, REVIEW_DATE, NYC_SHIP_DATE, MATCHED_PROCESS_DATE,CALC_COLLECTION_DATE,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, CREATE_UID, CREATE_DATE, FLUX_MATCHED_PROCESS_DATE)
				values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), trim(upper(?41)), trim(upper(?42)), trim(upper(?43)), trim(upper(?44)), trim(upper(?45)), trim(upper(?46)), trim(upper(?47)), trim(upper(?48)), trim(upper(?49)), trim(upper(?50)), trim(upper(?51)), trim(upper(?52)), trim(upper(?53)), trim(upper(?54)), trim(upper(?55)), trim(upper(?56)), trim(upper(?57)), trim(upper(?58)), trim(upper(?59)), trim(upper(?60)), trim(upper(?61)), trim(upper(?62)), trim(upper(?63)), trim(upper(?64)), trim(upper(?65)), trim(upper(?66)), trim(upper(?67)), trim(upper(?68)), trim(upper(?69)), trim(upper(?70)), trim(upper(?71)), trim(upper(?72)), trim(upper(?73)), trim(upper(?74)), trim(upper(?75)), trim(upper(?76)), trim(upper(?77)), trim(upper(?78)), trim(upper(?79)), trim(upper(?80)), trim(upper(?81)), trim(upper(?82)), trim(upper(?83)), trim(upper(?84)), trim(upper(?85)), trim(upper(?86)), ?87, ?88, ?89, ?90, ?91, ?92, ?93, FN_TO_DATE(?94,'MM/DD/YYYY'), FN_TO_DATE(?95,'MM/DD/YYYY'), FN_TO_DATE(?96,'MM/DD/YYYY'), ?97, ?98, ?99, ?100, ?101,?102,?103,?104,?105,?106,trim(upper(?107)),?108,?109)
			""";

	/** The values of {@link #STD_EDITED_HIV_LAB_DOCUMENT}, in order. */
	static Object[] insertStdEditedHivLabDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("PROV_FNAME")), text(map.get("PROV_MNAME")), text(map.get("PROV_SUFFIX")),
				text(map.get("DIN")), text(map.get("PERSON_CENSUS_TRACT")), text(map.get("PROV_CENSUS_TRACT")), text(map.get("FACILITY_CENSUS_TRACT")),
				text(map.get("PERSON_CITY")), text(map.get("UNIT_MEASURE_CODE")), text(map.get("PERSON_INTERNAL_ID")), text(map.get("PERSON_EXTERNAL_ID")),
				text(map.get("AGE")), text(map.get("PERSON_ZIP")), text(map.get("PROV_ZIP")), text(map.get("FACILITY_ZIP")),
				text(map.get("FNAME")), text(map.get("LNAME")), text(map.get("MNAME")), text(map.get("SUFFIX")),
				text(map.get("LOINC_DESC")), text(map.get("TEST_RESULT")), text(map.get("BIRTH_COUNTRY")), text(map.get("PERSON_PHONE")),
				text(map.get("PROV_PHONE")), text(map.get("FACILITY_PHONE")), text(map.get("PERSON_PLUS4")), text(map.get("PROV_PLUS4")),
				text(map.get("FACILITY_PLUS4")), text(map.get("PROV_CITY")), text(map.get("FACILITY_CITY")), text(map.get("REFERRING_FACILITY")),
				text(map.get("TESTING_FACILITY")), text(map.get("PFI")), text(map.get("STATENO")), text(map.get("PERSON_COUNTY")),
				text(map.get("ETHNICITY")), text(map.get("PROV_COUNTY")), text(map.get("FACILITY_COUNTY")), text(map.get("LENGTH_RESIDENCY_IN_USA")),
				text(map.get("SPECIMEN_SOURCE_NAME")), text(map.get("LOCAL_TEST_DESC")), text(map.get("MED_RECORD_NUMBER")), text(map.get("INSURANCE_NUMBER")),
				text(map.get("NYSID")), text(map.get("ACCESSION_NUMBER")), text(map.get("PROV_ADDRESS")), text(map.get("FACILITY_ADDRESS")),
				text(map.get("AREA_OF_BIRTH")), text(map.get("MEDICAID_NUMBER")), text(map.get("LOCAL_TEST_CODE")), text(map.get("PERSON_STATE")),
				text(map.get("GENDER")), text(map.get("INSURANCE_CD")), text(map.get("PROV_STATE")), text(map.get("FACILITY_STATE")),
				text(map.get("MATCH_CD")), text(map.get("HIV_VACCINE_RECIP")), text(map.get("RESIDE_OUTSIDE_USA")), text(map.get("SENDING_FACILITY_NAME")),
				text(map.get("FACILITY_NAME")), text(map.get("PROV_LNAME")), text(map.get("PERSON_LONGITUDE")), text(map.get("PROV_LONGITUDE")),
				text(map.get("FACILITY_LONGITUDE")), text(map.get("PERSON_COUNTRY")), text(map.get("INTERPRET_CD")), text(map.get("PERSON_ADDRESS")),
				text(map.get("MATCHED_STATENO")), text(map.get("DOB_TEXT")), text(map.get("PERSON_LATITUDE")), text(map.get("PROV_LATITUDE")),
				text(map.get("LOINC")), text(map.get("SPECIMEN_SOURCE_CD")), text(map.get("REVIEW_UID")), text(map.get("SENDING_FACILITY_CLIA")),
				text(map.get("FACILITY_LATITUDE")), text(map.get("RACE_ASIAN")), text(map.get("RACE_BLACK")), text(map.get("RACE_INDIAN")),
				text(map.get("RACE_MULTIPLE")), text(map.get("RACE_OTHER")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_UNKNOWN")),
				text(map.get("RACE_WHITE")), text(map.get("EHARS_STATUS_CD")), number(map.get("MATCH_WEIGHT")), number(map.get("DOCUMENT_ID")),
				number(map.get("LAB_SRC_ID")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("MATCH_PASS")), number(map.get("QS_PARENT_DOC_ID")),
				number(map.get("AGE_CATEGORY")), date(map.get("OBSERVATION_DATE")), date(map.get("COLLECTION_DATE")), date(map.get("RESULT_REPORT_DATE")),
				date(map.get("EHARS_IMPORT_DATE")), date(map.get("DATE_RECEIVED")), date(map.get("REVIEW_DATE")), date(map.get("NYC_SHIP_DATE")),
				date(map.get("MATCHED_PROCESS_DATE")), date(map.get("CALC_COLLECTION_DATE")), text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")),
				text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), text(map.get("CREATE_UID")), text(map.get("CREATE_DATE")),
				date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedStdDocument}, as the former mapper had it (229 bound values). */
	static final String RAW_EDITED_STD_DOCUMENT = """
				insert into RAW_STD_ECLRS_DOC(ZLRFACILITYCODE, ZLRFACILITYNAME, OBRPROVIDERID, OBRPROVIDERLASTNAME, OBRPROVIDERFIRSTNAME, OBRPROVIDERMIDDLENAME, OBRPROVIDERNAMESUFFIX, OBRPROVIDERIDALT1, OBRPROVIDERIDALT2, MSHVERSIONID, PIDDIN, PIDPATIENTLANGUAGE, OBXUNITOFMEASURE, OBXPRODUCERCLIAID, OBXPRODUCERLABNAME, OBXOBSERVATIONMETHOD, PIDLENGTHOFRESIDENCY, OBXABNORMALFLAG, PIDLASTNAME, PIDFIRSTNAME, PIDMIDDLENAME, PIDNAMESUFFIX, OBXLOINCDESCSHORT, OBXOBSERVATIONRESULTTEXTSHORT, PIDHOMEPHONE, PIDWORKPHONE, ZLRNEXTOFKINLASTNAME, ZLRNEXTOFKINFIRSTNAME, ZLRNEXTOFKINMIDDLENAME, ZLRNEXTOFKINNAMESUFFIX, ZLRNEXTOFKINRELATIONSHIP, ZLRNEXTOFKINPHONE, PIDCELLPHONE, OBRCALLBACKPHONE, OBRCALLBACKPHONEALT, ZLRFACILITYPHONE, OBRPROVIDERFAX, OBXLOCALRESULTCODE, PIDSETID, PIDSMSA, OBRSETID, OBRSPECIMENDESC, PIDCOUNTRYOFORIGIN, PIDUPDATEID, OBXLOGONID, PROGRAMCODEUPDATEID, OBXPROGRAMCODEUPDATEID, MSHPROCESSINGID, MSHSOURCETYPE, OBXVALUETYPE, OBXPROGRAMCODE, MSHMESSAGEDATERAW, PIDDATEOFBIRTHRAW, PIDDEATHDATERAW, OBRCOLLECTIONDATERAW, OBRRESULTREPORTDATERAW, OBROBSERVATIONDATERAW, OBRRECEIVEDDATERAW, OBXOBSERVATIONDATERAW, OBXANALYSISDATERAW, PIDRACECODERAW, OBRSPECIMENSOURCENAME, OBRINITRESLOINCDESC, OBRINITRESLOCALTESTDESC, OBRINITRESOBSERVATIONTEXT, OBXLOINCDESC, OBXSNOMEDDESC, OBXLOCALTESTDESC, OBXLOCALRESULTDESC, OBXICD_DESC, CDESSMAPPEDVALUES, PIDCOUNTYCODEORIGINAL, PIDMEDICALRECORDNUMBER, OBRACCESSIONNUM, OBRINITRESPLACERSORDERNUM, OBRINITRESACCESSIONNUM, PIDASSIGNAUTHOID, OBRLOCALCODE, OBRANALYSISDESC, OBRPATHOLOGISTID, OBRPATHOLOGISTLASTNAME, OBRPATHOLOGISTFIRSTNAME, OBRPATHOLOGISTMIDDLENAME, OBRPATHOLOGISTNAMESUFFIX, MSHMESSAGECONTROLID, PIDPATIENTIDEXTERNAL, PIDPATIENTIDINTERNAL, PIDPATIENTIDALT, PIDPATIENTACCOUNTNUM, PIDMOTHERSID, ZLRAGE, OBRINITRESLOCALTESTCODE, OBRINITRESSUBID, OBXOBSERVATIONSUBID, OBXSNOMEDCODE, OBXLOCALTESTCODE, OBXICD_CODETYPE, OBXICD_CODE, PIDCOUNTYGAZCODEORIG, PIDTYPEOFINSURANCE, MSHSENDINGFACILITYNAME, PIDSSN, MSHFILENAME, PIDNYSID, PIDMEDICAIDNUMBER, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, PIDCOUNTRYCODE, ZLRNEXTOFKINADDRESSLINE1, ZLRNEXTOFKINADDRESSLINE2, ZLRNEXTOFKINCITY, ZLRNEXTOFKINSTATE, ZLRNEXTOFKINZIPCODE, ZLRNEXTOFKINCOUNTRY, PIDEMAIL, ZLRPROVIDERADDRESSLINE1, ZLRPROVIDERADDRESSLINE2, ZLRPROVIDERCITY, ZLRPROVIDERSTATE, ZLRPROVIDERZIPCODE, ZLRPROVIDERCOUNTRY, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2, ZLRFACILITYCITY, ZLRFACILITYSTATE, ZLRFACILITYZIPCODE, ZLRFACILITYCOUNTRY, OBRPROVIDEREMAIL, REASONNOTMAPPED, PIDASSIGNAUTHDESC, OBRPLACERSORDERNUM, OBRFILLERSORDERNUM, OBXREFERENCERANGE, MSHSENDINGFACILITYCLIA, MSHLABPFI, PIDFACILITYIDEXTERNAL, PIDFACILITYIDINTERNAL, PIDFACILITYIDALT, PIDETHNICGROUPCODE, OBRSNOMEDCODE, OBRLOINCCODE, OBRSPECIMENSOURCECODE, OBRPROVIDERIDTYPECODE, OBRINITRESLOINCCODE, OBRPROVIDERIDTYPECODEALT1, OBRPROVIDERIDTYPECODEALT2, OBXSETID, OBXLOINCCODE, OBXSENDINGFACILITYCLIA, CLASS, TESTTYPE, RESULT, SPECIMENSOURCE, MSHFILECONTENTTYPE, MSHDELETEDIND, PIDSEXCODE, PIDRACECODE, PIDRACEASIAN, PIDRACEBLACK, PIDRACENATIVEAMERALASKAN, PIDRACEOTHER, PIDRACEPACIFIC, PIDRACEUNKNOWN, PIDRACEWHITE, PIDMARITALSTATUSCODE, PIDDEATHIND, PIDUSBORNIND, PIDVACCINETRIALIND, PIDNONHUMANIND, PIDDELETEDIND, OBRRESULTSTATUSCODE, OBRDELETEDIND, OBXRESULTSTATUSCODE, OBXPROCESSEDIND, OBXLEADPURPOSE, OBXDELETEDIND, MAPPABLEIND, MSHMESSAGEDATE, MSHCREATEDATE, MSHDELETEDATE, PIDCREATEDATE, PIDDATEOFBIRTH, PIDDEATHDATE, PIDUPDATEDATE, PIDDELETEDATE, OBRCREATEDATE, OBRCOLLECTIONDATE, OBRRESULTREPORTDATE, OBRDELETEDATE, OBROBSERVATIONDATE, OBRRECEIVEDDATE, OBXOBSERVATIONDATE, OBXPROCESSEDDATE, OBXDELETEDATE, PROGRAMCODEUPDATEDATE, OBXPROGRAMCODEUPDATEDATE, OBXCREATEDATE, OBXANALYSISDATE, OBXDISEASE, OBXDISEASEGROUP, PIDCOUNTYCODE, OBXCOUNTYCODE, MSHMESSAGEMASTERKEY, PIDPATIENTKEY, PIDMESSAGEMASTERKEY, OBROBSERVATIONREQUESTKEY, ZLROBSERVATIONREQUESTKEY, OBRPATIENTKEY, OBRPATIENTKEYORIG, OBRINITRESKEY, OBXOBSERVATIONRESULTKEY, OBXOBSERVATIONREQUESTKEY, OBXOLDESTDUPLICATERESULTKEY, DOCUMENT_ID, CURRENT_PROCESS_STATUS, HASH_UNIQUE_NUMBER, PIDLONGITUDE, PIDLATITUDE, PROCESS_DATE, PIDNOTE, OBRRELEVANTCLINICALINFO, OBRNOTE, OBXOBSERVATIONRESULTTEXT, OBXNOTE, CREATE_UID, CREATE_DATE)
			values(upper(trim(?1)), upper(trim(?2)), upper(trim(?3)), upper(trim(?4)), upper(trim(?5)), upper(trim(?6)), upper(trim(?7)), upper(trim(?8)), upper(trim(?9)), upper(trim(?10)), upper(trim(?11)), upper(trim(?12)), upper(trim(?13)), upper(trim(?14)), upper(trim(?15)), upper(trim(?16)), upper(trim(?17)), upper(trim(?18)), upper(trim(?19)), upper(trim(?20)), upper(trim(?21)), upper(trim(?22)), upper(trim(?23)), upper(trim(?24)), upper(trim(?25)), upper(trim(?26)), upper(trim(?27)), upper(trim(?28)), upper(trim(?29)), upper(trim(?30)), upper(trim(?31)), upper(trim(?32)), upper(trim(?33)), upper(trim(?34)), upper(trim(?35)), upper(trim(?36)), upper(trim(?37)), upper(trim(?38)), upper(trim(?39)), upper(trim(?40)), upper(trim(?41)), upper(trim(?42)), upper(trim(?43)), upper(trim(?44)), upper(trim(?45)), upper(trim(?46)), upper(trim(?47)), upper(trim(?48)), upper(trim(?49)), upper(trim(?50)), upper(trim(?51)), upper(trim(?52)), upper(trim(?53)), upper(trim(?54)), upper(trim(?55)), upper(trim(?56)), upper(trim(?57)), upper(trim(?58)), upper(trim(?59)), upper(trim(?60)), upper(trim(?61)), upper(trim(?62)), upper(trim(?63)), upper(trim(?64)), upper(trim(?65)), upper(trim(?66)), upper(trim(?67)), upper(trim(?68)), upper(trim(?69)), upper(trim(?70)), upper(trim(?71)), upper(trim(?72)), upper(trim(?73)), upper(trim(?74)), upper(trim(?75)), upper(trim(?76)), upper(trim(?77)), upper(trim(?78)), upper(trim(?79)), upper(trim(?80)), upper(trim(?81)), upper(trim(?82)), upper(trim(?83)), upper(trim(?84)), upper(trim(?85)), upper(trim(?86)), upper(trim(?87)), upper(trim(?88)), upper(trim(?89)), upper(trim(?90)), upper(trim(?91)), upper(trim(?92)), upper(trim(?93)), upper(trim(?94)), upper(trim(?95)), upper(trim(?96)), upper(trim(?97)), upper(trim(?98)), upper(trim(?99)), upper(trim(?100)), upper(trim(?101)), upper(trim(?102)), upper(trim(?103)), upper(trim(?104)), upper(trim(?105)), upper(trim(?106)), upper(trim(?107)), upper(trim(?108)), upper(trim(?109)), upper(trim(?110)), upper(trim(?111)), upper(trim(?112)), upper(trim(?113)), upper(trim(?114)), upper(trim(?115)), upper(trim(?116)), upper(trim(?117)), upper(trim(?118)), upper(trim(?119)), upper(trim(?120)), upper(trim(?121)), upper(trim(?122)), upper(trim(?123)), upper(trim(?124)), upper(trim(?125)), upper(trim(?126)), upper(trim(?127)), upper(trim(?128)), upper(trim(?129)), upper(trim(?130)), upper(trim(?131)), upper(trim(?132)), upper(trim(?133)), upper(trim(?134)), upper(trim(?135)), upper(trim(?136)), upper(trim(?137)), upper(trim(?138)), upper(trim(?139)), upper(trim(?140)), upper(trim(?141)), upper(trim(?142)), upper(trim(?143)), upper(trim(?144)), upper(trim(?145)), upper(trim(?146)), upper(trim(?147)), upper(trim(?148)), upper(trim(?149)), upper(trim(?150)), upper(trim(?151)), upper(trim(?152)), upper(trim(?153)), upper(trim(?154)), upper(trim(?155)), upper(trim(?156)), upper(trim(?157)), upper(trim(?158)), upper(trim(?159)), upper(trim(?160)), upper(trim(?161)), upper(trim(?162)), upper(trim(?163)), upper(trim(?164)), upper(trim(?165)), upper(trim(?166)), upper(trim(?167)), upper(trim(?168)), upper(trim(?169)), upper(trim(?170)), upper(trim(?171)), upper(trim(?172)), upper(trim(?173)), upper(trim(?174)), upper(trim(?175)), upper(trim(?176)), upper(trim(?177)), upper(trim(?178)), upper(trim(?179)), upper(trim(?180)), ?181, ?182, ?183, ?184, FN_TO_DATE(?185,'MM/DD/YYYY'), ?186, ?187, ?188, ?189, FN_TO_DATE(?190,'MM/DD/YYYY'), FN_TO_DATE(?191,'MM/DD/YYYY'), ?192, ?193, ?194, FN_TO_DATE(?195,'MM/DD/YYYY'), ?196, ?197, ?198, ?199, ?200, ?201, ?202, ?203, ?204, ?205, ?206, ?207, ?208, ?209, ?210, ?211, ?212, ?213, ?214, ?215, ?216, ?217, ?218, ?219, ?220, ?221, ?222, upper(trim(?223)), upper(trim(?224)), upper(trim(?225)), upper(trim(?226)), upper(trim(?227)),trim(upper(?228)),?229)
			""";

	/** The values of {@link #RAW_EDITED_STD_DOCUMENT}, in order. */
	static Object[] insertRawEditedStdDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("ZLRFACILITYCODE")), text(map.get("ZLRFACILITYNAME")), text(map.get("OBRPROVIDERID")), text(map.get("OBRPROVIDERLASTNAME")),
				text(map.get("OBRPROVIDERFIRSTNAME")), text(map.get("OBRPROVIDERMIDDLENAME")), text(map.get("OBRPROVIDERNAMESUFFIX")), text(map.get("OBRPROVIDERIDALT1")),
				text(map.get("OBRPROVIDERIDALT2")), text(map.get("MSHVERSIONID")), text(map.get("PIDDIN")), text(map.get("PIDPATIENTLANGUAGE")),
				text(map.get("OBXUNITOFMEASURE")), text(map.get("OBXPRODUCERCLIAID")), text(map.get("OBXPRODUCERLABNAME")), text(map.get("OBXOBSERVATIONMETHOD")),
				text(map.get("PIDLENGTHOFRESIDENCY")), text(map.get("OBXABNORMALFLAG")), text(map.get("PIDLASTNAME")), text(map.get("PIDFIRSTNAME")),
				text(map.get("PIDMIDDLENAME")), text(map.get("PIDNAMESUFFIX")), text(map.get("OBXLOINCDESCSHORT")), text(map.get("OBXOBSERVATIONRESULTTEXTSHORT")),
				text(map.get("PIDHOMEPHONE")), text(map.get("PIDWORKPHONE")), text(map.get("ZLRNEXTOFKINLASTNAME")), text(map.get("ZLRNEXTOFKINFIRSTNAME")),
				text(map.get("ZLRNEXTOFKINMIDDLENAME")), text(map.get("ZLRNEXTOFKINNAMESUFFIX")), text(map.get("ZLRNEXTOFKINRELATIONSHIP")), text(map.get("ZLRNEXTOFKINPHONE")),
				text(map.get("PIDCELLPHONE")), text(map.get("OBRCALLBACKPHONE")), text(map.get("OBRCALLBACKPHONEALT")), text(map.get("ZLRFACILITYPHONE")),
				text(map.get("OBRPROVIDERFAX")), text(map.get("OBXLOCALRESULTCODE")), text(map.get("PIDSETID")), text(map.get("PIDSMSA")),
				text(map.get("OBRSETID")), text(map.get("OBRSPECIMENDESC")), text(map.get("PIDCOUNTRYOFORIGIN")), text(map.get("PIDUPDATEID")),
				text(map.get("OBXLOGONID")), text(map.get("PROGRAMCODEUPDATEID")), text(map.get("OBXPROGRAMCODEUPDATEID")), text(map.get("MSHPROCESSINGID")),
				text(map.get("MSHSOURCETYPE")), text(map.get("OBXVALUETYPE")), text(map.get("OBXPROGRAMCODE")), text(map.get("MSHMESSAGEDATERAW")),
				text(map.get("PIDDATEOFBIRTHRAW")), text(map.get("PIDDEATHDATERAW")), text(map.get("OBRCOLLECTIONDATERAW")), text(map.get("OBRRESULTREPORTDATERAW")),
				text(map.get("OBROBSERVATIONDATERAW")), text(map.get("OBRRECEIVEDDATERAW")), text(map.get("OBXOBSERVATIONDATERAW")), text(map.get("OBXANALYSISDATERAW")),
				text(map.get("PIDRACECODERAW")), text(map.get("OBRSPECIMENSOURCENAME")), text(map.get("OBRINITRESLOINCDESC")), text(map.get("OBRINITRESLOCALTESTDESC")),
				text(map.get("OBRINITRESOBSERVATIONTEXT")), text(map.get("OBXLOINCDESC")), text(map.get("OBXSNOMEDDESC")), text(map.get("OBXLOCALTESTDESC")),
				text(map.get("OBXLOCALRESULTDESC")), text(map.get("OBXICD_DESC")), text(map.get("CDESSMAPPEDVALUES")), text(map.get("PIDCOUNTYCODEORIGINAL")),
				text(map.get("PIDMEDICALRECORDNUMBER")), text(map.get("OBRACCESSIONNUM")), text(map.get("OBRINITRESPLACERSORDERNUM")), text(map.get("OBRINITRESACCESSIONNUM")),
				text(map.get("PIDASSIGNAUTHOID")), text(map.get("OBRLOCALCODE")), text(map.get("OBRANALYSISDESC")), text(map.get("OBRPATHOLOGISTID")),
				text(map.get("OBRPATHOLOGISTLASTNAME")), text(map.get("OBRPATHOLOGISTFIRSTNAME")), text(map.get("OBRPATHOLOGISTMIDDLENAME")), text(map.get("OBRPATHOLOGISTNAMESUFFIX")),
				text(map.get("MSHMESSAGECONTROLID")), text(map.get("PIDPATIENTIDEXTERNAL")), text(map.get("PIDPATIENTIDINTERNAL")), text(map.get("PIDPATIENTIDALT")),
				text(map.get("PIDPATIENTACCOUNTNUM")), text(map.get("PIDMOTHERSID")), text(map.get("ZLRAGE")), text(map.get("OBRINITRESLOCALTESTCODE")),
				text(map.get("OBRINITRESSUBID")), text(map.get("OBXOBSERVATIONSUBID")), text(map.get("OBXSNOMEDCODE")), text(map.get("OBXLOCALTESTCODE")),
				text(map.get("OBXICD_CODETYPE")), text(map.get("OBXICD_CODE")), text(map.get("PIDCOUNTYGAZCODEORIG")), text(map.get("PIDTYPEOFINSURANCE")),
				text(map.get("MSHSENDINGFACILITYNAME")), text(map.get("PIDSSN")), text(map.get("MSHFILENAME")), text(map.get("PIDNYSID")),
				text(map.get("PIDMEDICAIDNUMBER")), text(map.get("PIDADDRESSLINE1")), text(map.get("PIDADDRESSLINE2")), text(map.get("PIDCITY")),
				text(map.get("PIDSTATE")), text(map.get("PIDZIPCODE")), text(map.get("PIDCOUNTRYCODE")), text(map.get("ZLRNEXTOFKINADDRESSLINE1")),
				text(map.get("ZLRNEXTOFKINADDRESSLINE2")), text(map.get("ZLRNEXTOFKINCITY")), text(map.get("ZLRNEXTOFKINSTATE")), text(map.get("ZLRNEXTOFKINZIPCODE")),
				text(map.get("ZLRNEXTOFKINCOUNTRY")), text(map.get("PIDEMAIL")), text(map.get("ZLRPROVIDERADDRESSLINE1")), text(map.get("ZLRPROVIDERADDRESSLINE2")),
				text(map.get("ZLRPROVIDERCITY")), text(map.get("ZLRPROVIDERSTATE")), text(map.get("ZLRPROVIDERZIPCODE")), text(map.get("ZLRPROVIDERCOUNTRY")),
				text(map.get("ZLRFACILITYADDRESSLINE1")), text(map.get("ZLRFACILITYADDRESSLINE2")), text(map.get("ZLRFACILITYCITY")), text(map.get("ZLRFACILITYSTATE")),
				text(map.get("ZLRFACILITYZIPCODE")), text(map.get("ZLRFACILITYCOUNTRY")), text(map.get("OBRPROVIDEREMAIL")), text(map.get("REASONNOTMAPPED")),
				text(map.get("PIDASSIGNAUTHDESC")), text(map.get("OBRPLACERSORDERNUM")), text(map.get("OBRFILLERSORDERNUM")), text(map.get("OBXREFERENCERANGE")),
				text(map.get("MSHSENDINGFACILITYCLIA")), text(map.get("MSHLABPFI")), text(map.get("PIDFACILITYIDEXTERNAL")), text(map.get("PIDFACILITYIDINTERNAL")),
				text(map.get("PIDFACILITYIDALT")), text(map.get("PIDETHNICGROUPCODE")), text(map.get("OBRSNOMEDCODE")), text(map.get("OBRLOINCCODE")),
				text(map.get("OBRSPECIMENSOURCECODE")), text(map.get("OBRPROVIDERIDTYPECODE")), text(map.get("OBRINITRESLOINCCODE")), text(map.get("OBRPROVIDERIDTYPECODEALT1")),
				text(map.get("OBRPROVIDERIDTYPECODEALT2")), text(map.get("OBXSETID")), text(map.get("OBXLOINCCODE")), text(map.get("OBXSENDINGFACILITYCLIA")),
				text(map.get("CLASS")), text(map.get("TESTTYPE")), text(map.get("RESULT")), text(map.get("SPECIMENSOURCE")),
				text(map.get("MSHFILECONTENTTYPE")), text(map.get("MSHDELETEDIND")), text(map.get("PIDSEXCODE")), text(map.get("PIDRACECODE")),
				text(map.get("PIDRACEASIAN")), text(map.get("PIDRACEBLACK")), text(map.get("PIDRACENATIVEAMERALASKAN")), text(map.get("PIDRACEOTHER")),
				text(map.get("PIDRACEPACIFIC")), text(map.get("PIDRACEUNKNOWN")), text(map.get("PIDRACEWHITE")), text(map.get("PIDMARITALSTATUSCODE")),
				text(map.get("PIDDEATHIND")), text(map.get("PIDUSBORNIND")), text(map.get("PIDVACCINETRIALIND")), text(map.get("PIDNONHUMANIND")),
				text(map.get("PIDDELETEDIND")), text(map.get("OBRRESULTSTATUSCODE")), text(map.get("OBRDELETEDIND")), text(map.get("OBXRESULTSTATUSCODE")),
				text(map.get("OBXPROCESSEDIND")), text(map.get("OBXLEADPURPOSE")), text(map.get("OBXDELETEDIND")), text(map.get("MAPPABLEIND")),
				date(map.get("MSHMESSAGEDATE")), date(map.get("MSHCREATEDATE")), date(map.get("MSHDELETEDATE")), date(map.get("PIDCREATEDATE")),
				date(map.get("PIDDATEOFBIRTH")), date(map.get("PIDDEATHDATE")), date(map.get("PIDUPDATEDATE")), date(map.get("PIDDELETEDATE")),
				date(map.get("OBRCREATEDATE")), date(map.get("OBRCOLLECTIONDATE")), date(map.get("OBRRESULTREPORTDATE")), date(map.get("OBRDELETEDATE")),
				date(map.get("OBROBSERVATIONDATE")), date(map.get("OBRRECEIVEDDATE")), date(map.get("OBXOBSERVATIONDATE")), date(map.get("OBXPROCESSEDDATE")),
				date(map.get("OBXDELETEDATE")), date(map.get("PROGRAMCODEUPDATEDATE")), date(map.get("OBXPROGRAMCODEUPDATEDATE")), date(map.get("OBXCREATEDATE")),
				date(map.get("OBXANALYSISDATE")), number(map.get("OBXDISEASE")), number(map.get("OBXDISEASEGROUP")), number(map.get("PIDCOUNTYCODE")),
				number(map.get("OBXCOUNTYCODE")), number(map.get("MSHMESSAGEMASTERKEY")), number(map.get("PIDPATIENTKEY")), number(map.get("PIDMESSAGEMASTERKEY")),
				number(map.get("OBROBSERVATIONREQUESTKEY")), number(map.get("ZLROBSERVATIONREQUESTKEY")), number(map.get("OBRPATIENTKEY")), number(map.get("OBRPATIENTKEYORIG")),
				number(map.get("OBRINITRESKEY")), number(map.get("OBXOBSERVATIONRESULTKEY")), number(map.get("OBXOBSERVATIONREQUESTKEY")), number(map.get("OBXOLDESTDUPLICATERESULTKEY")),
				number(map.get("DOCUMENT_ID")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("HASH_UNIQUE_NUMBER")), number(map.get("PIDLONGITUDE")),
				number(map.get("PIDLATITUDE")), date(map.get("PROCESS_DATE")), text(map.get("PIDNOTE")), text(map.get("OBRRELEVANTCLINICALINFO")),
				text(map.get("OBRNOTE")), text(map.get("OBXOBSERVATIONRESULTTEXT")), text(map.get("OBXNOTE")), text(map.get("CREATE_UID")),
				text(map.get("CREATE_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedStdDocument}, as the former mapper had it (139 bound values). */
	static final String STD_EDITED_STD_DOCUMENT = """
			insert into STD_STD_ECLRS_DOC(SSN, PROV_FNAME, PROV_MNAME, PROV_SUFFIX, RECB, DIN, PERSON_CENSUS_TRACT, PROV_CENSUS_TRACT, FACILITY_CENSUS_TRACT, PERSON_CITY, UNIT_MEASURE_CODE, PERSON_INTERNAL_ID, PERSON_EXTERNAL_ID, AGE, PERSON_ZIP, PROV_ZIP, FACILITY_ZIP, FNAME, LNAME, MNAME, SUFFIX, LOINC_DESC, TEST_RESULT, BIRTH_COUNTRY, PERSON_PHONE, PROV_PHONE, FACILITY_PHONE, PERSON_PLUS4, PROV_PLUS4, FACILITY_PLUS4, MATCH_CD, PROV_CITY, FACILITY_CITY, REFERRING_FACILITY, TESTING_FACILITY, PERSON_COUNTY, ETHNICITY, PROV_COUNTY, FACILITY_COUNTY, LENGTH_RESIDENCY_IN_USA, SPECIMEN_SOURCE_NAME, TEST_RESULT_RAW, LOCAL_TEST_DESC, NYSID, INSURANCE_NUMBER, MED_RECORD_NUMBER, ACCESSION_NUMBER, PROV_ADDRESS, FACILITY_ADDRESS, PROV_CENSUS_TRACT_SUFFIX, FACILITY_CENSUS_TRACT_SUFFIX, CASEKIND, AREA_OF_BIRTH, MEDICAID_NUMBER, LOCAL_TEST_CODE, PERSON_STATE, PERSON_CENSUS_TRACT_SUFFIX, GENDER, INSURANCE_CD, RISK01, RISK02, RISK03, RISK04, RISK05, RISK06, RISK07, RISK08, RISK09, RISK10, RISK11, RISK12, RISK13, RISK14, RISK15, RISK16, PROV_STATE, FACILITY_STATE, HIV_VACCINE_RECIP, OTHER_IMMUNE_INFECTION, RESIDE_OUTSIDE_USA, SENDING_FACILITY_NAME, FACILITY_NAME, PROV_LNAME, PERSON_LONGITUDE, PROV_LONGITUDE, FACILITY_LONGITUDE, PERSON_COUNTRY, PERSON_ADDRESS, INTERPRET_CD, MATCHED_STATENO, DOB_TEXT, PERSON_LATITUDE, PROV_LATITUDE, FACILITY_LATITUDE, LOINC, SPECIMEN_SOURCE_CD, PROVIDER_TYPE, LAST_NEG_HIV_TEST_DATE, SENDING_FACILITY_CLIA, REVIEW_UID, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, PROCESSED, EHARS_STATUS_CD, CRBATCHNUM, DOCUMENT_ID, LAB_SRC_ID, AGE_CATEGORY, CURRENT_PROCESS_STATUS, QS_PARENT_DOC_ID, MATCH_WEIGHT, MATCH_PASS, DRAW_DATE, OBSERVATION_DATE, COLLECTION_DATE, RESULT_REPORT_DATE, DATE_SENT, REVIEW_DATE, DATE_RECEIVED, CREATE_DATE, EHARS_IMPORT_DATE, NYC_SHIP_DATE, MATCHED_PROCESS_DATE, CREATE_UID, CALC_COLLECTION_DATE,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			values(upper(trim(?1)), upper(trim(?2)), upper(trim(?3)), upper(trim(?4)), upper(trim(?5)), upper(trim(?6)), upper(trim(?7)), upper(trim(?8)), upper(trim(?9)), upper(trim(?10)), upper(trim(?11)), upper(trim(?12)), upper(trim(?13)), upper(trim(?14)), upper(trim(?15)), upper(trim(?16)), upper(trim(?17)), upper(trim(?18)), upper(trim(?19)), upper(trim(?20)), upper(trim(?21)), upper(trim(?22)), upper(trim(?23)), upper(trim(?24)), upper(trim(?25)), upper(trim(?26)), upper(trim(?27)), upper(trim(?28)), upper(trim(?29)), upper(trim(?30)), upper(trim(?31)), upper(trim(?32)), upper(trim(?33)), upper(trim(?34)), upper(trim(?35)), upper(trim(?36)), upper(trim(?37)), upper(trim(?38)), upper(trim(?39)), upper(trim(?40)), upper(trim(?41)), upper(trim(?42)), upper(trim(?43)), upper(trim(?44)), upper(trim(?45)), upper(trim(?46)), upper(trim(?47)), upper(trim(?48)), upper(trim(?49)), upper(trim(?50)), upper(trim(?51)), upper(trim(?52)), upper(trim(?53)), upper(trim(?54)), upper(trim(?55)), upper(trim(?56)), upper(trim(?57)), upper(trim(?58)), upper(trim(?59)), upper(trim(?60)), upper(trim(?61)), upper(trim(?62)), upper(trim(?63)), upper(trim(?64)), upper(trim(?65)), upper(trim(?66)), upper(trim(?67)), upper(trim(?68)), upper(trim(?69)), upper(trim(?70)), upper(trim(?71)), upper(trim(?72)), upper(trim(?73)), upper(trim(?74)), upper(trim(?75)), upper(trim(?76)), upper(trim(?77)), upper(trim(?78)), upper(trim(?79)), upper(trim(?80)), upper(trim(?81)), upper(trim(?82)), upper(trim(?83)), upper(trim(?84)), upper(trim(?85)), upper(trim(?86)), upper(trim(?87)), upper(trim(?88)), upper(trim(?89)), upper(trim(?90)), upper(trim(?91)), upper(trim(?92)), upper(trim(?93)), upper(trim(?94)), upper(trim(?95)), upper(trim(?96)), upper(trim(?97)), upper(trim(?98)), upper(trim(?99)), upper(trim(?100)), upper(trim(?101)), upper(trim(?102)), upper(trim(?103)), upper(trim(?104)), upper(trim(?105)), upper(trim(?106)), upper(trim(?107)), upper(trim(?108)), upper(trim(?109)), upper(trim(?110)), ?111, ?112, ?113, ?114, ?115, ?116, ?117, ?118, ?119, FN_TO_DATE(?120,'MM/DD/YYYY'), FN_TO_DATE(?121,'MM/DD/YYYY'), FN_TO_DATE(?122,'MM/DD/YYYY'), ?123, ?124, ?125, ?126, ?127, ?128, ?129, upper(trim(?130)), COALESCE(FN_TO_DATE(?131,'MM/DD/YYYY'), FN_TO_DATE(?132,'MM/DD/YYYY'), FN_TO_DATE(?133,'MM/DD/YYYY') , ?134),?135,?136,?137,?138,?139)
			""";

	/** The values of {@link #STD_EDITED_STD_DOCUMENT}, in order. */
	static Object[] insertStdEditedStdDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("PROV_FNAME")), text(map.get("PROV_MNAME")), text(map.get("PROV_SUFFIX")),
				text(map.get("RECB")), text(map.get("DIN")), text(map.get("PERSON_CENSUS_TRACT")), text(map.get("PROV_CENSUS_TRACT")),
				text(map.get("FACILITY_CENSUS_TRACT")), text(map.get("PERSON_CITY")), text(map.get("UNIT_MEASURE_CODE")), text(map.get("PERSON_INTERNAL_ID")),
				text(map.get("PERSON_EXTERNAL_ID")), text(map.get("AGE")), text(map.get("PERSON_ZIP")), text(map.get("PROV_ZIP")),
				text(map.get("FACILITY_ZIP")), text(map.get("FNAME")), text(map.get("LNAME")), text(map.get("MNAME")),
				text(map.get("SUFFIX")), text(map.get("LOINC_DESC")), text(map.get("TEST_RESULT")), text(map.get("BIRTH_COUNTRY")),
				text(map.get("PERSON_PHONE")), text(map.get("PROV_PHONE")), text(map.get("FACILITY_PHONE")), text(map.get("PERSON_PLUS4")),
				text(map.get("PROV_PLUS4")), text(map.get("FACILITY_PLUS4")), text(map.get("MATCH_CD")), text(map.get("PROV_CITY")),
				text(map.get("FACILITY_CITY")), text(map.get("REFERRING_FACILITY")), text(map.get("TESTING_FACILITY")), text(map.get("PERSON_COUNTY")),
				text(map.get("ETHNICITY")), text(map.get("PROV_COUNTY")), text(map.get("FACILITY_COUNTY")), text(map.get("LENGTH_RESIDENCY_IN_USA")),
				text(map.get("SPECIMEN_SOURCE_NAME")), text(map.get("TEST_RESULT_RAW")), text(map.get("LOCAL_TEST_DESC")), text(map.get("NYSID")),
				text(map.get("INSURANCE_NUMBER")), text(map.get("MED_RECORD_NUMBER")), text(map.get("ACCESSION_NUMBER")), text(map.get("PROV_ADDRESS")),
				text(map.get("FACILITY_ADDRESS")), text(map.get("PROV_CENSUS_TRACT_SUFFIX")), text(map.get("FACILITY_CENSUS_TRACT_SUFFIX")), text(map.get("CASEKIND")),
				text(map.get("AREA_OF_BIRTH")), text(map.get("MEDICAID_NUMBER")), text(map.get("LOCAL_TEST_CODE")), text(map.get("PERSON_STATE")),
				text(map.get("PERSON_CENSUS_TRACT_SUFFIX")), text(map.get("GENDER")), text(map.get("INSURANCE_CD")), text(map.get("RISK01")),
				text(map.get("RISK02")), text(map.get("RISK03")), text(map.get("RISK04")), text(map.get("RISK05")),
				text(map.get("RISK06")), text(map.get("RISK07")), text(map.get("RISK08")), text(map.get("RISK09")),
				text(map.get("RISK10")), text(map.get("RISK11")), text(map.get("RISK12")), text(map.get("RISK13")),
				text(map.get("RISK14")), text(map.get("RISK15")), text(map.get("RISK16")), text(map.get("PROV_STATE")),
				text(map.get("FACILITY_STATE")), text(map.get("HIV_VACCINE_RECIP")), text(map.get("OTHER_IMMUNE_INFECTION")), text(map.get("RESIDE_OUTSIDE_USA")),
				text(map.get("SENDING_FACILITY_NAME")), text(map.get("FACILITY_NAME")), text(map.get("PROV_LNAME")), text(map.get("PERSON_LONGITUDE")),
				text(map.get("PROV_LONGITUDE")), text(map.get("FACILITY_LONGITUDE")), text(map.get("PERSON_COUNTRY")), text(map.get("PERSON_ADDRESS")),
				text(map.get("INTERPRET_CD")), text(map.get("MATCHED_STATENO")), text(map.get("DOB_TEXT")), text(map.get("PERSON_LATITUDE")),
				text(map.get("PROV_LATITUDE")), text(map.get("FACILITY_LATITUDE")), text(map.get("LOINC")), text(map.get("SPECIMEN_SOURCE_CD")),
				text(map.get("PROVIDER_TYPE")), text(map.get("LAST_NEG_HIV_TEST_DATE")), text(map.get("SENDING_FACILITY_CLIA")), text(map.get("REVIEW_UID")),
				text(map.get("RACE_ASIAN")), text(map.get("RACE_BLACK")), text(map.get("RACE_INDIAN")), text(map.get("RACE_MULTIPLE")),
				text(map.get("RACE_OTHER")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_UNKNOWN")), text(map.get("RACE_WHITE")),
				text(map.get("PROCESSED")), text(map.get("EHARS_STATUS_CD")), number(map.get("CRBATCHNUM")), number(map.get("DOCUMENT_ID")),
				number(map.get("LAB_SRC_ID")), number(map.get("AGE_CATEGORY")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("QS_PARENT_DOC_ID")),
				number(map.get("MATCH_WEIGHT")), number(map.get("MATCH_PASS")), date(map.get("DRAW_DATE")), date(map.get("OBSERVATION_DATE")),
				date(map.get("COLLECTION_DATE")), date(map.get("RESULT_REPORT_DATE")), date(map.get("DATE_SENT")), date(map.get("REVIEW_DATE")),
				date(map.get("DATE_RECEIVED")), date(map.get("CREATE_DATE")), date(map.get("EHARS_IMPORT_DATE")), date(map.get("NYC_SHIP_DATE")),
				date(map.get("MATCHED_PROCESS_DATE")), text(map.get("CREATE_UID")), date(map.get("COLLECTION_DATE")), date(map.get("OBSERVATION_DATE")),
				date(map.get("RESULT_REPORT_DATE")), date(map.get("DATE_RECEIVED")), text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")),
				text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedClusterDocument}, as the former mapper had it (13 bound values). */
	static final String RAW_EDITED_CLUSTER_DOCUMENT = """
			INSERT INTO RAW_CLUSTER_DOC (DOCUMENT_ID,CLUSTER_ID,EHARS_STATENO,RSH_RES_COUNTY,RSH_RES_STATE_CD,CUR_COUNTY,CUR_STATE_CD,UPDATED_EHARS_STATENO,LAST_EHARS_STATENO_UPDATE_UID,LAST_EHARS_STATENO_UPDATE_DATE,CURRENT_PROCESS_STATUS,CREATE_UID,CREATE_DATE,PROCESS_DATE)
			VALUES(?1,?2,trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),trim(upper(?6)),trim(upper(?7)),trim(upper(?8)),trim(upper(?9)),?10,?11,upper(trim(?12)),SYSDATE,?13)
			""";

	/** The values of {@link #RAW_EDITED_CLUSTER_DOCUMENT}, in order. */
	static Object[] insertRawEditedClusterDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				number(map.get("DOCUMENT_ID")), number(map.get("CLUSTER_ID")), text(map.get("EHARS_STATENO")), text(map.get("RSH_RES_COUNTY")),
				text(map.get("RSH_RES_STATE_CD")), text(map.get("CUR_COUNTY")), text(map.get("CUR_STATE_CD")), text(map.get("UPDATED_EHARS_STATENO")),
				text(map.get("LAST_EHARS_STATENO_UPDATE_UID")), date(map.get("LAST_EHARS_STATENO_UPDATE_DATE")), number(map.get("CURRENT_PROCESS_STATUS")), text(map.get("CREATE_UID")),
				date(map.get("PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedClusterDocument}, as the former mapper had it (12 bound values). */
	static final String STD_EDITED_CLUSTER_DOCUMENT = """
			INSERT INTO STD_CLUSTER_DOC (DOCUMENT_ID,CLUSTER_ID,EHARS_STATENO_AT_UPLOAD,NYEHMS_STATENO_AT_UPLOAD,FIRST_NAME,MIDDLE_NAME,LAST_NAME,DOB,CLUSTER_DISPOSITION_CODE,CLUSTER_DISPOSITION_COMMENTS,CURRENT_PROCESS_STATUS,CREATE_UID,CREATE_DATE)
			VALUES(?1,?2,trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),trim(upper(?6)),trim(upper(?7)),?8,trim(upper(?9)),trim(upper(?10)),?11,upper(trim(?12)),SYSDATE)
			""";

	/** The values of {@link #STD_EDITED_CLUSTER_DOCUMENT}, in order. */
	static Object[] insertStdEditedClusterDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				number(map.get("DOCUMENT_ID")), number(map.get("CLUSTER_ID")), text(map.get("EHARS_STATENO_AT_UPLOAD")), text(map.get("NYEHMS_STATENO_AT_UPLOAD")),
				text(map.get("FIRST_NAME")), text(map.get("MIDDLE_NAME")), text(map.get("LAST_NAME")), date(map.get("DOB")),
				text(map.get("CLUSTER_DISPOSITION_CODE")), text(map.get("CLUSTER_DISPOSITION_COMMENTS")), number(map.get("CURRENT_PROCESS_STATUS")), text(map.get("CREATE_UID"))
		};
	}

	/** The SQL of {@code insertRawEditedOldCasesDocument}, as the former mapper had it (34 bound values). */
	static final String RAW_EDITED_OLD_CASES_DOCUMENT = """
			INSERT INTO RAW_OLD_CASES_DOC (SSN, DIN, ADDRESS, SUFFIX, ZIP, CITY, MNAME, LNAME, FNAME, STATE, RACE, ETHNICITY, MED_RECORD_NUMBER, COMMENTS, MATCHED_STATENO, DOB_TEXT, DOD_TEXT, CREATE_UID, CURRENT_GENDER, RACE_INDIAN, RACE_BLACK, RACE_ASIAN, RACE_PACIFIC, RACE_WHITE, RACE_MULTIPLE, RACE_OTHER, RACE_UNKNOWN, DOCUMENT_ID, OLDCASE_SRC_ID, CURRENT_PROCESS_STATUS, DATE_OF_BIRTH, DATE_OF_DEATH, CREATE_DATE, PROCESS_DATE)
			VALUES(trim(upper(?1)),trim(upper(?2)),trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),trim(upper(?6)),trim(upper(?7)),trim(upper(?8)),trim(upper(?9)),trim(upper(?10)),trim(upper(?11)),trim(upper(?12)),trim(upper(?13)),trim(upper(?14)),trim(upper(?15)),trim(upper(?16)),trim(upper(?17)),trim(upper(?18)),trim(upper(?19)),trim(upper(?20)),trim(upper(?21)),trim(upper(?22)),trim(upper(?23)),trim(upper(?24)),trim(upper(?25)),trim(upper(?26)),trim(upper(?27)),?28,?29,?30,?31,fn_to_date(trim(upper(?32)),'MM/DD/YYYY'),?33,?34)
			""";

	/** The values of {@link #RAW_EDITED_OLD_CASES_DOCUMENT}, in order. */
	static Object[] insertRawEditedOldCasesDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("DIN")), text(map.get("ADDRESS")), text(map.get("SUFFIX")),
				text(map.get("ZIP")), text(map.get("CITY")), text(map.get("MNAME")), text(map.get("LNAME")),
				text(map.get("FNAME")), text(map.get("STATE")), text(map.get("RACE")), text(map.get("ETHNICITY")),
				text(map.get("MED_RECORD_NUMBER")), text(map.get("COMMENTS")), text(map.get("MATCHED_STATENO")), text(map.get("DOB_TEXT")),
				text(map.get("DOD_TEXT")), text(map.get("CREATE_UID")), text(map.get("CURRENT_GENDER")), text(map.get("RACE_INDIAN")),
				text(map.get("RACE_BLACK")), text(map.get("RACE_ASIAN")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_WHITE")),
				text(map.get("RACE_MULTIPLE")), text(map.get("RACE_OTHER")), text(map.get("RACE_UNKNOWN")), number(map.get("DOCUMENT_ID")),
				number(map.get("OLDCASE_SRC_ID")), number(map.get("CURRENT_PROCESS_STATUS")), date(map.get("DATE_OF_BIRTH")), text(map.get("DOD_TEXT")),
				date(map.get("CREATE_DATE")), date(map.get("PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertStdEditedOldCasesDocument}, as the former mapper had it (33 bound values). */
	static final String STD_EDITED_OLD_CASES_DOCUMENT = """
			INSERT INTO STD_OLD_CASES_DOC (SSN, DIN, ADDRESS, SUFFIX, ZIP, CITY, MNAME, LNAME, FNAME, STATE, RACE, ETHNICITY, MED_RECORD_NUMBER, COMMENTS, MATCHED_STATENO, DOB_TEXT, DOD_TEXT, CREATE_UID, CURRENT_GENDER, RACE_INDIAN, RACE_BLACK, RACE_ASIAN, RACE_PACIFIC, RACE_WHITE, RACE_MULTIPLE, RACE_OTHER, RACE_UNKNOWN, DOCUMENT_ID, OLDCASE_SRC_ID, CURRENT_PROCESS_STATUS, DATE_OF_BIRTH, DATE_OF_DEATH, CREATE_DATE)
			VALUES(trim(upper(?1)),trim(upper(?2)),trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),trim(upper(?6)),trim(upper(?7)),trim(upper(?8)),trim(upper(?9)),trim(upper(?10)),trim(upper(?11)),trim(upper(?12)),trim(upper(?13)),trim(upper(?14)),trim(upper(?15)),trim(upper(?16)),trim(upper(?17)),trim(upper(?18)),trim(upper(?19)),trim(upper(?20)),trim(upper(?21)),trim(upper(?22)),trim(upper(?23)),trim(upper(?24)),trim(upper(?25)),trim(upper(?26)),trim(upper(?27)),?28,?29,?30,?31,fn_to_date(trim(upper(?32)),'MM/DD/YYYY'),?33)
			""";

	/** The values of {@link #STD_EDITED_OLD_CASES_DOCUMENT}, in order. */
	static Object[] insertStdEditedOldCasesDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("SSN")), text(map.get("DIN")), text(map.get("ADDRESS")), text(map.get("SUFFIX")),
				text(map.get("ZIP")), text(map.get("CITY")), text(map.get("MNAME")), text(map.get("LNAME")),
				text(map.get("FNAME")), text(map.get("STATE")), text(map.get("RACE")), text(map.get("ETHNICITY")),
				text(map.get("MED_RECORD_NUMBER")), text(map.get("COMMENTS")), text(map.get("MATCHED_STATENO")), text(map.get("DOB_TEXT")),
				text(map.get("DOD_TEXT")), text(map.get("CREATE_UID")), text(map.get("CURRENT_GENDER")), text(map.get("RACE_INDIAN")),
				text(map.get("RACE_BLACK")), text(map.get("RACE_ASIAN")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_WHITE")),
				text(map.get("RACE_MULTIPLE")), text(map.get("RACE_OTHER")), text(map.get("RACE_UNKNOWN")), number(map.get("DOCUMENT_ID")),
				number(map.get("OLDCASE_SRC_ID")), number(map.get("CURRENT_PROCESS_STATUS")), date(map.get("DATE_OF_BIRTH")), text(map.get("DOD_TEXT")),
				date(map.get("CREATE_DATE"))
		};
	}

	/** The SQL of {@code insertEditedTrkAliasDocument}, as the former mapper had it (15 bound values). */
	static final String EDITED_TRK_ALIAS_DOCUMENT = """
			INSERT INTO TRACKING_USER_ENTERED_ALIAS (DOCUMENT_ID, ALIAS_ID, STATENO, REPSRCCD, ALIAS_NO, FNAME, MNAME, LNAME, SSN, DOB_TEXT, DOB, SUFFIX, CREATEID, CREATEDT, COMMENTS, CURRENT_PROCESS_STATUS)
			VALUES(?1,?2,trim(upper(?3)),trim(upper(?4)),?5,trim(upper(?6)),trim(upper(?7)),trim(upper(?8)),trim(upper(?9)),trim(upper(?10)),?11,trim(upper(?12)),trim(upper(?13)),SYSDATE,trim(upper(?14)),?15)
			""";

	/** The values of {@link #EDITED_TRK_ALIAS_DOCUMENT}, in order. */
	static Object[] insertEditedTrkAliasDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				number(map.get("DOCUMENT_ID")), number(map.get("ALIAS_ID")), text(map.get("STATENO")), text(map.get("REPSRCCD")),
				number(map.get("ALIAS_NO")), text(map.get("FNAME")), text(map.get("MNAME")), text(map.get("LNAME")),
				text(map.get("SSN")), text(map.get("DOB_TEXT")), date(map.get("DOB")), text(map.get("SUFFIX")),
				text(map.get("CREATE_UID")), text(map.get("COMMENTS")), number(map.get("CURRENT_PROCESS_STATUS"))
		};
	}
}
