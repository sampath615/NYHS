package gov.ny.state.doh.bhae.nyehms.web.spring.dao.person;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldDocsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.TypedRowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;

/**
 * The result maps of the former PersonDAO mapper that its procedures' cursors (and those of MergeUnMergePersonDAO)
 * were mapped with: named columns to properties, the other columns auto-mapped, as {@link Rows#resultMap}. Every
 * property of these VOs is text, so the cursors are read as text ({@code NativeStatements.textCursor}), as MyBatis
 * read every column of them with getString.
 */
final class PersonResultMaps {

	private PersonResultMaps() {
	}

	/** personViewMappingResult: a person's current details. */
	static final TypedRowMapper<PersonViewVO> PERSON_VIEW = Rows.resultMap(PersonViewVO.class,
			"FIRST_NAME", "FNAME", "MIDDLE_NAME", "MNAME", "LAST_NAME", "LNAME", "SUFFIX", "SUFFIX", "CURRENT_GENDER", "GENDER",
			"BIRTH_SEX", "birthSex", "STATE_OF_DEATH", "stateOfDeath", "DATE_OF_DEATH", "dateOfDeath", "SSN", "SSN", "NYSID", "NYSID",
			"DIN", "DIN", "MED_RECORD", "MED_RECORD_NUMBER", "MEDICAID_NUMBER", "MEDICAID_NUMBER", "INSURANCE", "INSURANCE_NUMBER",
			"BIRTH_DATE", "DOB", "BIRTH_COUNTRY", "BIRTH_COUNTRY", "ETHNICITY", "ETHNICITY", "RACE", "race", "ADDRESS", "PERSON_ADDRESS",
			"STATE", "PERSON_STATE", "CITY", "PERSON_CITY", "COUNTRY", "country", "COUNTY", "PERSON_COUNTY", "ZIPCODE", "PERSON_ZIP",
			"plus4", "PERSON_PLUS4", "HOME_PHONE", "homePhone", "CELL_PHONE", "cellPhone", "WORK_PHONE", "workPhone",
			"EMERGENCY_CONTACT", "emerPhone", "PAGER", "pager", "EMAIL", "email", "WEB", "web",
			"FIRST_NAME_EXISTS", "firstNameExists", "LAST_NAME_EXISTS", "lastNameExists", "MIDDLE_NAME_EXISTS", "middleNameExists",
			"SUFFIX_EXISTS", "suffixExists", "CURRENT_GENDER_EXISTS", "currentGenderExists", "BIRTH_SEX_EXISTS", "birthSexExists",
			"STATE_OF_DEATH_EXISTS", "sodExists", "DATE_OF_DEATH_EXISTS", "dodExists", "SSN_EXISTS", "ssnExists", "NYSID_EXISTS", "nysidExists",
			"DIN_EXISTS", "dinExists", "MED_RECORD_EXISTS", "medRecordExists", "INSURANCE_EXISTS", "insuranceExists",
			"BIRTH_DATE_EXISTS", "birthDateExists", "BIRTH_COUNTRY_EXISTS", "birthCountryExists", "ETHNICITY_EXISTS", "ethnicityExists",
			"MEDICAID_NUMBER_EXISTS", "MEDICAID_NUMBER_EXISTS", "HOME_PHONE_EXISTS", "homePhoneExists", "ADDRESS_EXISTS", "addressExists",
			"BAD_MERGE_FLAG", "badMergeFlag", "FLAG_STATENO_EXISTS", "recordSearchFlag", "FLAG_COMMENTS", "recordSearchComments",
			"FLAG_SENT_CDC", "sentToCDC", "ras_doc_exists", "rasDocActive", "STATENO_FLAGGED_EARLIER", "statenoFlaggedEarlier",
			"NON_CASE_FLAG_PREV", "nonCaseHistory", "NON_CASE_FLAG_NEW", "nonCaseDocStatus", "DUPLICATE_STATENO", "DUPLICATE_STATENO");

	/** personDocHistoryVOResultMap: a document of the person's timeline. */
	static final TypedRowMapper<PersonDocHistoryVO> PERSON_DOC_HISTORY = Rows.resultMap(PersonDocHistoryVO.class,
			"DOCUMENT_ID", "documentId", "DOCUMENT_TYPE_CODE", "documentType", "ASSOCIATION_DATE", "associationDate",
			"FIRST_NAME", "firstName", "LAST_NAME", "lastName", "DATE_OF_BIRTH", "dateOfBirth", "SSN", "ssn", "ETHNICITY", "ethnicity",
			"RACE", "race", "ADDRESS", "address");

	/** personRaceMappingResult: the race flags of a person. */
	static final TypedRowMapper<PersonRaceVO> PERSON_RACE = Rows.resultMap(PersonRaceVO.class,
			"RACE_WHITE", "RACE_WHITE", "RACE_BLACK", "RACE_BLACK", "RACE_ASIAN", "RACE_ASIAN", "RACE_INDIAN", "RACE_INDIAN",
			"RACE_PACIFIC", "RACE_PACIFIC", "RACE_MULTIPLE", "RACE_MULTIPLE", "RACE_UNKNOWN", "RACE_UNKNOWN", "RACE_OTHER", "RACE_OTHER",
			"RACE_OTHER_TEXT", "RACE_OTHER_TEXT", "RACE_ASIAN_SUB", "raceAsianSub", "RACE_PACIFIC_SUB", "racePacificSub");

	/** personRisksMappingResult: the risk factors of a person. */
	static final TypedRowMapper<PersonRisksVO> PERSON_RISKS = Rows.resultMap(PersonRisksVO.class,
			"SWM", "swm", "SWF", "swf", "IDU", "idu", "SEX_PARTNER_IDU", "sexPartnerIdu", "SEX_PARTNER_HIV", "sexPartnerHiv",
			"RECV_BLOOD_PROD", "recvBloodProd", "PERINATAL", "perinatal", "OTHER", "other", "UNKNOWN", "unknown",
			"SEX_PARTNER_MSM", "sexPartnerMsm", "RECV_CLOTTING_FACTOR", "recvClottingFactor", "SEX_PARTNER_HEMOPHILIAC", "sexPartnerHemophiliac",
			"SEX_PARTNER_TRANSFUSION", "sexPartnerTransfusion", "SEX_PARTNER_TRANSPLANT", "sexPartnerTransplant", "RECV_TRANSPLANT", "recvTransplant",
			"HEALTHCARE_WORKER", "healthCareWorker", "RISK_OTHER_TXT", "riskOtherTxt", "COUNTRY_RECV_BLOOD_PROD", "countryRecvBloodProd",
			"DATE_RECV_BLOOD_PROD", "dateRecvBloodProd", "DATE_RECV_CLOTTING_FACTOR", "dateRecvClottingFactor",
			"CLOTTING_FACTOR_DISORDER", "cloattingFactorDisorder", "OCCUPATIONAL_SETTING", "occupationalSetting");

	/** PersonFieldVOResultMap: a value a field of the person had, with its id. */
	static final TypedRowMapper<PersonFieldVO> PERSON_FIELD = Rows.resultMap(PersonFieldVO.class,
			"FIELD_ID", "fieldId", "COLUMN_VALUE", "field");

	/** PersonFieldDocsVOResultMap: a document that gave a field's value. */
	static final TypedRowMapper<PersonFieldDocsVO> PERSON_FIELD_DOCS = Rows.resultMap(PersonFieldDocsVO.class,
			"COLUMN_VALUE", "field", "DOCUMENT_ID", "documentId", "DOCUMENT_TYPE", "documentType", "A_DATE", "startDate",
			"DOC_EDITED_FLAG", "docEditiedFlag");

	/** personHistoryData (result type String): the COLUMN_VALUE column of each row. */
	static final RowMapper<String> COLUMN_VALUE = row -> (String) row.get("COLUMN_VALUE");
}
