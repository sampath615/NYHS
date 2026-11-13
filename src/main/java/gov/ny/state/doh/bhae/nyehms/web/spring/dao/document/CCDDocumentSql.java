package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.timestamp;

import java.util.Map;

import org.hibernate.type.StandardBasicTypes;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BeanPaths;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;

/**
 * The SQL of the {@link CCDDocumentDAO} statements that stay native, taken unchanged from the former MyBatis mapper
 * ({@code #{...}} replaced by {@code ?1, ?2, ...}; generated from the mapper XML); why each one stays native is said at
 * its DAO method. The {@code ...Args} methods give the values bound to a statement, in order, typed as the mapper's
 * {@code jdbcType} bound a null ({@code VARCHAR} and {@code CLOB} a text, {@code DECIMAL} / {@code INTEGER} a number,
 * {@code DATE} a date, {@code TIMESTAMP} a timestamp); a value that is not null is bound as it is, as MyBatis bound it by
 * its Java type (a {@code java.util.Date} with {@code jdbcType=DATE} as its day only, as MyBatis' DateOnlyTypeHandler did).
 * A property path ({@code a.b.c}) is read as MyBatis read it ({@link BeanPaths}). The former {@code <foreach>} inserts
 * ({@code union all} of one select per item) are the SQL of ONE item, with {@code ...ItemArgs(map, item)}: the DAO runs
 * it once per item.
 */
final class CCDDocumentSql {

	private CCDDocumentSql() {
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

	/** The SQL of {@code insertRawCcdDocData}, as the former mapper had it (18 bound values). */
	static final String RAW_CCD_DOC_DATA = """
			insert into raw_ccd_doc (
			  DOCUMENT_ID,
			  UPHN_TRANX_IN_ID,
			  MPI,
			  FNAME,
			  MNAME,
			  LNAME,
			  DOB_TEXT,
			  GENDER,
			  PERSON_PHONE_HOME,
			  PERSON_PHONE_WORK,
			  PERSON_PHONE_CELL,
			  RACE_DESC,
			  RACE_CODE,
			  ETHNICITY,
			  LANGUAGE,
			  CURRENT_PROCESS_STATUS,
			  HIE_NAME,
			  PAYLOADNAME)
			values (
			  ?1,
			  ?2,
			  ?3,
			  ?4,
			  ?5,
			  ?6,
			  ?7,
			  ?8,
			  ?9,
			  ?10,
			  ?11,
			  ?12,
			  ?13,
			  ?14,
			  ?15,
			  ?16,
			  ?17,
			  ?18)
			""";

	/** The values of {@link #RAW_CCD_DOC_DATA}, in order. */
	static Object[] insertRawCcdDocDataArgs(RawCCDPersonDocVO vo) {
		return new Object[] {
				number(BeanPaths.get(vo, "DOCUMENT_ID")), number(BeanPaths.get(vo, "UPHN_TRANX_IN_ID")), text(BeanPaths.get(vo, "MPI")),
				text(BeanPaths.get(vo, "name.firstName")), text(BeanPaths.get(vo, "name.middleName")), text(BeanPaths.get(vo, "name.lastName")),
				text(BeanPaths.get(vo, "DOB_TEXT")), text(BeanPaths.get(vo, "GENDER")), text(BeanPaths.get(vo, "PERSON_PHONE_HOME")),
				text(BeanPaths.get(vo, "PERSON_PHONE_WORK")), text(BeanPaths.get(vo, "PERSON_PHONE_CELL")), text(BeanPaths.get(vo, "RACE_DESC")),
				text(BeanPaths.get(vo, "RACE_CODE")), text(BeanPaths.get(vo, "ETHNICITY")), text(BeanPaths.get(vo, "LANGUAGE")),
				number(BeanPaths.get(vo, "CURRENT_PROCESS_STATUS")), text(BeanPaths.get(vo, "hieName")), text(BeanPaths.get(vo, "payloadname"))
		};
	}

	/** The SQL of {@code insertCcdAddressList}, for one item of addressVO (12 bound values). */
	static final String CCD_ADDRESS_ITEM = """
			insert into raw_ccd_person_address (
			PERSON_ADDRESS_ID,
			CCD_DOC_ID,
			ADDRESS_USE,
			PERSON_ADDRESS_1,
			PERSON_ADDRESS_2,
			PERSON_CITY,
			PERSON_STATE,
			PERSON_ZIP,
			PERSON_COUNTRY,
			USEABLE_PERIOD_LOW,
			USEABLE_PERIOD_HIGH,
			UPHN_TRANX_IN_ID,
			ADDRESS_SEQ_NO)
			select
			raw_ccd_person_address_seq.NEXTVAL,d.* from (
			
				select ?1,
				?2,
				?3,
				?4,
				?5,
				?6,
				?7,
				?8,
				?9,
				?10,
				?11,
				?12 from dual
			
			)d
			""";

	/** The values of {@link #CCD_ADDRESS_ITEM} for one item of the former collection, in order. */
	static Object[] insertCcdAddressListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(map.get("ccdDocId")), text(BeanPaths.get(item, "theCCDAddrObject.ccdUse")), text(BeanPaths.get(item, "theCCDAddrObject.street1")),
				text(BeanPaths.get(item, "theCCDAddrObject.street2")), text(BeanPaths.get(item, "theCCDAddrObject.city")), text(BeanPaths.get(item, "theCCDAddrObject.state")),
				text(BeanPaths.get(item, "theCCDAddrObject.zip")), text(BeanPaths.get(item, "theCCDAddrObject.country")), text(BeanPaths.get(item, "useableStartTime")),
				text(BeanPaths.get(item, "useableEndTime")), number(BeanPaths.get(item, "uphnId")), number(BeanPaths.get(item, "addressSeqNo"))
		};
	}

	/** The SQL of {@code insertCcdProvidersList}, for one item of providerVO (18 bound values). */
	static final String CCD_PROVIDER_ITEM = """
			insert into raw_ccd_provider (
			provider_id,
			ccd_doc_id,
			provider_type_code,
			provider_type_description,
			provider_lname,
			provider_fname,
			provider_mname,
			provider_address_1,
			provider_address_2,
			provider_city,
			provider_state,
			provider_zip,
			provider_phone,
			start_date_text,
			end_date_text,
			uphn_tranx_in_id,
			provider_seq_no,
			provider_assigned_id,
			provider_assigned_id_auth)
			select
			raw_ccd_provider_info_seq.NEXTVAL,d.* from (
			
				select ?1,
				?2,
				?3,
				?4,
				?5,
				?6,
				?7,
				?8,
				?9,
				?10,
				?11,
				?12,
				?13,
				?14,
				?15,
				?16,
				?17,
				?18 from dual
			
			)d
			""";

	/** The values of {@link #CCD_PROVIDER_ITEM} for one item of the former collection, in order. */
	static Object[] insertCcdProvidersListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(map.get("ccdDocId")), text(BeanPaths.get(item, "providerTypeCode")), text(BeanPaths.get(item, "providerTypeDesc")),
				text(BeanPaths.get(item, "providerName.lastName")), text(BeanPaths.get(item, "providerName.firstName")), text(BeanPaths.get(item, "providerName.middleName")),
				text(BeanPaths.get(item, "address.street1")), text(BeanPaths.get(item, "address.street2")), text(BeanPaths.get(item, "address.city")),
				text(BeanPaths.get(item, "address.state")), text(BeanPaths.get(item, "address.zip")), text(BeanPaths.get(item, "phone")),
				text(BeanPaths.get(item, "startDateText")), text(BeanPaths.get(item, "endDateText")), number(BeanPaths.get(item, "uphnId")),
				number(BeanPaths.get(item, "providerSeqNo")), text(BeanPaths.get(item, "providerAssignedID")), text(BeanPaths.get(item, "providerAssignedIDAuth"))
		};
	}

	/** The SQL of {@code insertCcdPayersList}, for one item of payerVO (41 bound values). */
	static final String CCD_PAYER_ITEM = """
			insert into raw_ccd_payer (
			PAYER_ID,
			CCD_DOC_ID,
			POLICY_ID,
			POLICY_NAME,
			POLICY_ADDRESS1,
			POLICY_ADDRESS2,
			POLICY_CITY,
			POLICY_STATE,
			POLICY_ZIPCODE,
			POLICY_COUNTRY,
			POLICY_COUNTY,
			POLICY_PHONE,
			MEMBER_ID,
			MEMBER_FNAME,
			MEMBER_MNAME,
			MEMBER_LNAME,
			MEMBER_ADDRESS1,
			MEMBER_ADDRESS2,
			MEMBER_CITY,
			MEMBER_STATE,
			MEMBER_ZIPCODE,
			MEMBER_COUNTRY,
			MEMBER_COUNTY,
			MEMBER_PHONE,
			MEMBER_DOB_TEXT,
			HOLDER_ID,
			HOLDER_FNAME,
			HOLDER_MNAME,
			HOLDER_LNAME,
			HOLDER_ADDRESS1,
			HOLDER_ADDRESS2,
			HOLDER_CITY,
			HOLDER_STATE,
			HOLDER_ZIPCODE,
			HOLDER_COUNTRY,
			HOLDER_COUNTY,
			HOLDER_PHONE,
			HOLDER_DOB_TEXT,
			START_DATE_TEXT,
			END_DATE_TEXT,
			UPHN_TRANX_IN_ID,
			PAYER_SEQ_NO)
			select
			raw_ccd_payer_seq.NEXTVAL,d.* from (
			
				select ?1,
				?2,
				?3,
				?4,
				?5,
				?6,
				?7,
				?8,
				?9,
				?10,
				?11,
				?12,
				?13,
				?14,
				?15,
				?16,
				?17,
				?18,
				?19,
				?20,
				?21,
				?22,
				?23,
				?24,
				?25,
				?26,
				?27,
				?28,
				?29,
				?30,
				?31,
				?32,
				?33,
				?34,
				?35,
				?36,
				?37,
				?38,
				?39,
				?40,
				?41 from dual
			
			)d
			""";

	/** The values of {@link #CCD_PAYER_ITEM} for one item of the former collection, in order. */
	static Object[] insertCcdPayersListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(map.get("ccdDocId")), text(BeanPaths.get(item, "policyId")), text(BeanPaths.get(item, "policyName")),
				text(BeanPaths.get(item, "policyAddress.street1")), text(BeanPaths.get(item, "policyAddress.street2")), text(BeanPaths.get(item, "policyAddress.city")),
				text(BeanPaths.get(item, "policyAddress.state")), text(BeanPaths.get(item, "policyAddress.zip")), text(BeanPaths.get(item, "policyAddress.country")),
				text(BeanPaths.get(item, "policyAddress.county")), text(BeanPaths.get(item, "policyPhone")), text(BeanPaths.get(item, "memberId")),
				text(BeanPaths.get(item, "memberName.firstName")), text(BeanPaths.get(item, "memberName.middleName")), text(BeanPaths.get(item, "memberName.lastName")),
				text(BeanPaths.get(item, "memberAddress.street1")), text(BeanPaths.get(item, "memberAddress.street2")), text(BeanPaths.get(item, "memberAddress.city")),
				text(BeanPaths.get(item, "memberAddress.state")), text(BeanPaths.get(item, "memberAddress.zip")), text(BeanPaths.get(item, "memberAddress.country")),
				text(BeanPaths.get(item, "memberAddress.county")), text(BeanPaths.get(item, "memberPhone")), text(BeanPaths.get(item, "memberDobText")),
				text(BeanPaths.get(item, "holderId")), text(BeanPaths.get(item, "holderName.firstName")), text(BeanPaths.get(item, "holderName.middleName")),
				text(BeanPaths.get(item, "holderName.lastName")), text(BeanPaths.get(item, "holderAddress.street1")), text(BeanPaths.get(item, "holderAddress.street2")),
				text(BeanPaths.get(item, "holderAddress.city")), text(BeanPaths.get(item, "holderAddress.state")), text(BeanPaths.get(item, "holderAddress.zip")),
				text(BeanPaths.get(item, "holderAddress.country")), text(BeanPaths.get(item, "holderAddress.county")), text(BeanPaths.get(item, "holderPhone")),
				text(BeanPaths.get(item, "holderDobText")), text(BeanPaths.get(item, "startDateText")), text(BeanPaths.get(item, "endDateText")),
				number(BeanPaths.get(item, "uphnId")), number(BeanPaths.get(item, "payerSeqNo"))
		};
	}

	/** The SQL of {@code insertCcdLabTestList}, for one item of labVO (20 bound values). */
	static final String CCD_LAB_TEST_ITEM = """
			insert into raw_ccd_lab_test (
			ccd_lab_test_id,
			ccd_doc_id,
			test_code,
			test_code_codesystem,
			test_code_display_name,
			test_date,
			test_encounter_number,
			specimen,
			test_comments,
			test_facility_code,
			test_facility_codesystem,
			uphn_tranx_in_id,
			lab_test_seq_no,
			FILLER_ID_ROOT,
			FILLER_ID_EXTENSION,
			FILLER_ID_AUTH,
			PLACER_ID_ROOT,
			PLACER_ID_EXTENSION,
			PLACER_ID_AUTH,
			TEST_CODE_SYSTEM_NAME)
			select d.*
			from (
			
				select ?1,
				?2,
				substr(?3,0,160),
				?4,
				?5,
				?6,
				?7,
				?8,
				?9,
				?10,
				?11,
				?12,
				?13 ,
				?14,
				?15,
				?16,
				?17,
				?18,
				?19,
				?20 from dual
			
			)d
			""";

	/** The values of {@link #CCD_LAB_TEST_ITEM} for one item of the former collection, in order. */
	static Object[] insertCcdLabTestListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(BeanPaths.get(item, "ccdLabTestId")), number(map.get("ccdDocId")), text(BeanPaths.get(item, "testCode")),
				text(BeanPaths.get(item, "testCodeCodeSystem")), text(BeanPaths.get(item, "testCodeDisplayName")), text(BeanPaths.get(item, "testDate")),
				text(BeanPaths.get(item, "testEncounterNumber")), text(BeanPaths.get(item, "specimen")), text(BeanPaths.get(item, "testComments")),
				text(BeanPaths.get(item, "testFacilityCode")), text(BeanPaths.get(item, "testFacilityCodeSystem")), number(BeanPaths.get(item, "uphnId")),
				number(BeanPaths.get(item, "testSeqNo")), text(BeanPaths.get(item, "filleridRoot")), text(BeanPaths.get(item, "fillerIdExtension")),
				text(BeanPaths.get(item, "fillerIdAuth")), text(BeanPaths.get(item, "placerIdRoot")), text(BeanPaths.get(item, "placerIdExtension")),
				text(BeanPaths.get(item, "placerIdAuth")), text(BeanPaths.get(item, "testCodeSystemName"))
		};
	}

	/** The SQL of {@code insertCcdLabResultsList}, for one item of labResultVO (15 bound values). */
	static final String CCD_LAB_RESULT_ITEM = """
			insert into raw_ccd_lab_result (
			CCD_LAB_RESULT_ID,
			CCD_LAB_TEST_ID,
			CCD_DOC_ID,
			COMPONENT_CODE,
			COMPONENT_CODESYSTEM,
			COMPONENT_DISPLAY_NAME,
			RESULT_DATE_TEXT,
			RESULT_VALUE,
			RESULT_STATUS,
			REFERENCE_RANGE_LOW,
			REFERENCE_RANGE_HIGH,
			RESULT_COMMENTS,
			UPHN_TRANX_IN_ID,
			LAB_TEST_SEQ_NO,
			LAB_RESULT_SEQ_NO,
			COMPONENT_CODESYSTEM_NAME)
			select
			raw_ccd_lab_result_seq.NEXTVAL,d.*
			from (
			
				select ?1,
				?2,
				?3,
				?4,
				?5,
				?6,
				substr(?7,0,4000),
				?8,
				?9,
				?10,
				substr(?11,0,4000),
				?12,
				?13,
				?14,
				?15 from dual
			
			)d
			""";

	/** The values of {@link #CCD_LAB_RESULT_ITEM} for one item of the former collection, in order. */
	static Object[] insertCcdLabResultsListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(BeanPaths.get(item, "ccdLabTestId")), number(map.get("ccdDocId")), text(BeanPaths.get(item, "componentCode")),
				text(BeanPaths.get(item, "componentCodeSystem")), text(BeanPaths.get(item, "componentDisplayName")), text(BeanPaths.get(item, "resultDateText")),
				text(BeanPaths.get(item, "resultValue")), text(BeanPaths.get(item, "resultStatus")), text(BeanPaths.get(item, "referenceRangeLow")),
				text(BeanPaths.get(item, "referenceRangeHigh")), text(BeanPaths.get(item, "resultComments")), number(BeanPaths.get(item, "uphnId")),
				number(BeanPaths.get(item, "testSeqNo")), number(BeanPaths.get(item, "resultSeqNo")), text(BeanPaths.get(item, "componentCodeSystemName"))
		};
	}

	/** The SQL of {@code insertRawCcdLabResult}, as the former mapper had it (15 bound values). */
	static final String RAW_CCD_LAB_RESULT = """
			insert into raw_ccd_lab_result (
						   CCD_LAB_RESULT_ID,
			  CCD_LAB_TEST_ID,
			  CCD_DOC_ID,
			  COMPONENT_CODE,
			  COMPONENT_CODESYSTEM,
			  COMPONENT_DISPLAY_NAME,
			  RESULT_DATE_TEXT,
			  RESULT_VALUE,
			  RESULT_STATUS,
			  REFERENCE_RANGE_LOW,
			  REFERENCE_RANGE_HIGH,
			  RESULT_COMMENTS,
			  uphn_tranx_in_id,
			  lab_test_seq_no,
			  lab_result_seq_no,
			  COMPONENT_CODESYSTEM_NAME)
			values (
			  raw_ccd_lab_result_seq.NEXTVAL,
			  ?1,
			  ?2,
			  ?3,
			  ?4,
			  ?5,
			  ?6,
			 substr(?7,0,4000),
			  ?8,
			  ?9,
			  ?10,
			 substr(?11,0,4000),
			  ?12,
			  ?13,
			  ?14,
			  ?15
			  )
			""";

	/** The values of {@link #RAW_CCD_LAB_RESULT}, in order. */
	static Object[] insertRawCcdLabResultArgs(RawCCDLabResultVO vo) {
		return new Object[] {
				number(BeanPaths.get(vo, "ccdLabTestId")), number(BeanPaths.get(vo, "ccdDocID")), text(BeanPaths.get(vo, "componentCode")),
				text(BeanPaths.get(vo, "componentCodeSystem")), text(BeanPaths.get(vo, "componentDisplayName")), text(BeanPaths.get(vo, "resultDateText")),
				text(BeanPaths.get(vo, "resultValue")), text(BeanPaths.get(vo, "resultStatus")), text(BeanPaths.get(vo, "referenceRangeLow")),
				text(BeanPaths.get(vo, "referenceRangeHigh")), text(BeanPaths.get(vo, "resultComments")), number(BeanPaths.get(vo, "uphnId")),
				number(BeanPaths.get(vo, "testSeqNo")), number(BeanPaths.get(vo, "resultSeqNo")), text(BeanPaths.get(vo, "componentCodeSystemName"))
		};
	}

	/** The SQL of {@code insertCcdMedsList}, for one item of medVO (14 bound values). */
	static final String CCD_MED_ITEM = """
			insert into raw_ccd_med (
			med_id,
			ccd_doc_id,
			ordered_medication_name,
			start_date_text,
			stop_date_text,
			dosage_value,
			dosage_unit,
			filled_medication_name,
			current_medication,
			ordering_prov_fname,
			ordering_prov_mname,
			ordering_prov_lname,
			frequency,
			uphn_tranx_in_id,
			med_seq_no)
			select raw_ccd_med_seq.NEXTVAL,d.* from (
			
				select ?1,
				?2,
				?3,
				?4,
				?5,
				?6,
				?7,
				?8,
				?9,
				?10,
				?11,
				?12,
				?13,
				?14 from dual
			
			)d
			""";

	/** The values of {@link #CCD_MED_ITEM} for one item of the former collection, in order. */
	static Object[] insertCcdMedsListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(map.get("ccdDocId")), text(BeanPaths.get(item, "orderedMedicationName")), text(BeanPaths.get(item, "startDateText")),
				text(BeanPaths.get(item, "stopDateText")), text(BeanPaths.get(item, "dosageValue")), text(BeanPaths.get(item, "dosageUnit")),
				text(BeanPaths.get(item, "filledMedicationName")), text(BeanPaths.get(item, "currentMedication")), text(BeanPaths.get(item, "orderingProvName.firstName")),
				text(BeanPaths.get(item, "orderingProvName.middleName")), text(BeanPaths.get(item, "orderingProvName.lastName")), text(BeanPaths.get(item, "frequency")),
				number(BeanPaths.get(item, "uphnId")), number(BeanPaths.get(item, "medSeqNo"))
		};
	}

	/** The SQL of {@code insertCcdProblemsList}, for one item of problemVO (14 bound values). */
	static final String CCD_PROBLEM_ITEM = """
			insert into raw_ccd_problem (
			problem_doc_id,
			ccd_doc_id,
			problem_date_text,
			provider_fname,
			provider_mname,
			provider_lname,
			facility_name,
			problem_code,
			problem_code_system_name,
			problem_display_name,
			encounter_id,
			problem_category,
			uphn_tranx_in_id,
			problem_seq_no,
			PROBLEM_CODE_SYSTEM)
			select
			raw_ccd_problem_seq.NEXTVAL,d.* from (
			
				select ?1,
				?2,
				substr(?3,0,120),
				?4,
				substr(trim(?5),0,120),
				?6,
				?7,
				?8,
				substr(?9,0,200),
				?10,
				?11,
				?12,
				?13,
				?14 from dual
			
			)d
			""";

	/** The values of {@link #CCD_PROBLEM_ITEM} for one item of the former collection, in order. */
	static Object[] insertCcdProblemsListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(map.get("ccdDocId")), text(BeanPaths.get(item, "problemDateText")), text(BeanPaths.get(item, "providerName.firstName")),
				text(BeanPaths.get(item, "providerName.middleName")), text(BeanPaths.get(item, "providerName.lastName")), text(BeanPaths.get(item, "facilityName")),
				text(BeanPaths.get(item, "problemCode")), text(BeanPaths.get(item, "problemCodeSystemName")), text(BeanPaths.get(item, "problemDisplayName")),
				text(BeanPaths.get(item, "encounterId")), text(BeanPaths.get(item, "problemCategory")), number(BeanPaths.get(item, "uphnId")),
				number(BeanPaths.get(item, "problemSeqNo")), text(BeanPaths.get(item, "problemCodeSystem"))
		};
	}

	/** The SQL of {@code insertCcdEncounterList}, for one item of encounterVO (19 bound values). */
	static final String CCD_ENCOUNTER_ITEM = """
			insert into raw_ccd_encounter(
			encounter_doc_id,
			ccd_doc_id,
			encounter_id,
			start_date_text,
			end_date_text,
			encounter_type,
			provider_fname,
			provider_mname,
			provider_lname,
			facility_name,
			facility_dept,
			admission_type,
			uphn_tranx_in_id,
			encounter_seq_no,
			PARTICIPANT_FACILITY_NAME,
			FACILITY_TYPE_CODE,
			FAC_TYPE_CODESYSTEM,
			FAC_TYPE_CODESYSTEM_NAME,
			FACILITY_TYPE)
			select d.*
			from (
			
				select ?1,
				?2,
				?3,
				?4,
				?5,
				?6,
				?7,
				?8,
				?9,
				?10,
				?11,
				?12,
				?13,
				?14,
				?15,
				?16,
				?17,
				?18,
				?19
				from dual
			
			)d
			""";

	/** The values of {@link #CCD_ENCOUNTER_ITEM} for one item of the former collection, in order. */
	static Object[] insertCcdEncounterListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(BeanPaths.get(item, "encounterDocId")), number(map.get("ccdDocId")), text(BeanPaths.get(item, "encounterId")),
				text(BeanPaths.get(item, "startDateText")), text(BeanPaths.get(item, "endDateText")), text(BeanPaths.get(item, "encounterType")),
				text(BeanPaths.get(item, "providerName.firstName")), text(BeanPaths.get(item, "providerName.middleName")), text(BeanPaths.get(item, "providerName.lastName")),
				text(BeanPaths.get(item, "facilityName")), text(BeanPaths.get(item, "facilityDept")), text(BeanPaths.get(item, "admissionType")),
				number(BeanPaths.get(item, "uphnId")), number(BeanPaths.get(item, "encounterSeqNo")), text(BeanPaths.get(item, "participantFacilityName")),
				text(BeanPaths.get(item, "facilityTypeCode")), text(BeanPaths.get(item, "facilityTypeCodeSystem")), text(BeanPaths.get(item, "facilityTypeCodeSystemName")),
				text(BeanPaths.get(item, "facilityType"))
		};
	}

	/** The SQL of {@code insertCcdDiagnosisList}, for one item of diagnosisVO (9 bound values). */
	static final String CCD_DIAGNOSIS_ITEM = """
			insert into raw_ccd_diagnosis (
			diagnosis_id,
			encounter_doc_id,
			diagnosis_text,
			diagnosis_code,
			diagnosis_code_system_code,
			diagnosis_code_system_oid,
			uphn_tranx_in_id,
			ccd_doc_id,
			encounter_seq_no,
			diagnosis_seq_no)
			select
			raw_ccd_diagnosis_seq.NEXTVAL,d.* from (
			
				select ?1,
				?2,
				?3,
				?4,
				?5,
				?6,
				?7,
				?8,
				?9 from dual
			
			)d
			""";

	/** The values of {@link #CCD_DIAGNOSIS_ITEM} for one item of the former collection, in order. */
	static Object[] insertCcdDiagnosisListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(BeanPaths.get(item, "encounterDocId")), text(BeanPaths.get(item, "diagnosisText")), text(BeanPaths.get(item, "diagnosisCode")),
				text(BeanPaths.get(item, "diagnosisCodeSystemCode")), text(BeanPaths.get(item, "diagnosisCodeSystemOid")), number(BeanPaths.get(item, "uphnId")),
				number(BeanPaths.get(item, "ccdDocID")), number(BeanPaths.get(item, "encounterSeqNo")), number(BeanPaths.get(item, "diagnosisSeqNo"))
		};
	}

	/** The SQL of {@code insertCCDFromRAWToSTD}, as the former mapper had it (47 bound values). */
	static final String CCD_FROM_RAW_TO_STD = """
			insert into
			STD_CCD_DOC
			(PERSON_ADDRESS_ID,ADDRESS_USE,PERSON_ADDRESS,PERSON_CITY,PERSON_STATE,
			PERSON_ZIP,PERSON_COUNTRY,USEABLE_PERIOD_LOW,USEABLE_PERIOD_HIGH,
			DOCUMENT_ID,FNAME,MNAME,LNAME,SUFFIX,DOB_TEXT,GENDER,PERSON_PHONE_HOME,
			LANGUAGE,ETHNICITY,PERSON_PHONE_CELL,
			RACE_WHITE,RACE_BLACK,RACE_ASIAN,RACE_INDIAN,RACE_PACIFIC,RACE_UNKNOWN,
			RACE_OTHER,RACE_OTHER_TEXT,
			PERSON_PLUS4,PERSON_COUNTY,PERSON_PHONE,
			PERSON_LATITUDE,PERSON_LONGITUDE,PERSON_CENSUS_TRACT,PERSON_PHONE_WORK,
			CREATE_DATE,MPI,MATCH_CD,CURRENT_PROCESS_STATUS,
			MATCH_PASS,MATCH_WEIGHT,MATCHED_STATENO,QS_PARENT_DOC_ID,MATCHED_PROCESS_DATE,UPHN_TRANX_IN_ID,HIE_NAME,RACE_UNTRANSLATABLE)
			values (
			?1,
			upper(trim(?2)),
			upper(trim(?3)),
			upper(trim(?4)),
			upper(trim(?5)),
			upper(trim(?6)),
			upper(trim(?7)),
			TO_CHAR(TO_DATE(substr(?8,0,8),
			'YYYYMMDD'),'MM/DD/YYYY'),
			upper(trim(?9)),
			?10,
			upper(trim(?11)),
			upper(trim(?12)),
			upper(trim(?13)),
			upper(trim(?14)),
			TO_CHAR(TO_DATE(substr(?15,0,8),
			'YYYYMMDD'),'MM/DD/YYYY'),
			upper(trim(?16)),
			?17,
			upper(trim(?18)),
			upper(trim(?19)),
			?20,
			upper(trim(?21)),
			upper(trim(?22)),
			upper(trim(?23)),
			upper(trim(?24)),
			upper(trim(?25)),
			upper(trim(?26)),
			upper(trim(?27)),
			upper(trim(?28)),
			?29,
			upper(trim(?30)),
			?31,
			upper(trim(?32)),
			upper(trim(?33)),
			upper(trim(?34)),
			?35,
			?36,
			?37,
			upper(trim(?38)),
			?39,
			upper(trim(?40)),
			?41,
			upper(trim(?42)),
			?43,
			?44,
			?45,
			?46,
			upper(trim(?47))
			)
			""";

	/** The values of {@link #CCD_FROM_RAW_TO_STD}, in order. */
	static Object[] insertCCDFromRAWToSTDArgs(Map<?, ?> map) {
		return new Object[] {
				number(map.get("PERSON_ADDRESS_ID")), text(map.get("ADDRESS_USE")), text(map.get("PERSON_ADDRESS")),
				text(map.get("PERSON_CITY")), text(map.get("PERSON_STATE")), text(map.get("PERSON_ZIP")),
				text(map.get("PERSON_COUNTRY")), text(map.get("USEABLE_PERIOD_LOW")), text(map.get("USEABLE_PERIOD_HIGH")),
				number(map.get("DOCUMENT_ID")), text(map.get("FNAME")), text(map.get("MNAME")),
				text(map.get("LNAME")), text(map.get("SUFFIX")), text(map.get("DOB_TEXT")),
				text(map.get("GENDER")), text(map.get("PERSON_PHONE_HOME")), text(map.get("LANGUAGE")),
				text(map.get("ETHNICITY")), text(map.get("PERSON_PHONE_CELL")), text(map.get("RACE_WHITE")),
				text(map.get("RACE_BLACK")), text(map.get("RACE_ASIAN")), text(map.get("RACE_INDIAN")),
				text(map.get("RACE_PACIFIC")), text(map.get("RACE_UNKNOWN")), text(map.get("RACE_OTHER")),
				text(map.get("RACE_OTHER_TEXT")), text(map.get("PERSON_PLUS4")), text(map.get("PERSON_COUNTY")),
				text(map.get("PERSON_PHONE")), text(map.get("PERSON_LATITUDE")), text(map.get("PERSON_LONGITUDE")),
				text(map.get("PERSON_CENSUS_TRACT")), text(map.get("PERSON_PHONE_WORK")), date(map.get("CREATE_DATE")),
				text(map.get("MPI")), text(map.get("MATCH_CD")), number(map.get("CURRENT_PROCESS_STATUS")),
				text(map.get("MATCH_PASS")), number(map.get("MATCH_WEIGHT")), text(map.get("MATCHED_STATENO")),
				number(map.get("QS_PARENT_DOC_ID")), date(map.get("MATCHED_PROCESS_DATE")), number(map.get("UPHN_TRANX_IN_ID")),
				text(map.get("HIE_NAME")), text(map.get("RACE_UNTRANSLATABLE"))
		};
	}

	/** The SQL of {@code insertDiagnosisFromRAWToSTD}, as the former mapper had it (11 bound values). */
	static final String DIAGNOSIS_FROM_RAW_TO_STD = """
			insert
			into
			STD_CCD_DIAGNOSIS
			(DIAGNOSIS_ID,
			ENCOUNTER_DOC_ID,
			DIAGNOSIS_TEXT,
			DIAGNOSIS_CODE,
			DIAGNOSIS_CODE_SYSTEM_CODE,
			DIAGNOSIS_CODE_SYSTEM_OID,
			UPHN_TRANX_IN_ID,
			CCD_DOC_ID,
			ENCOUNTER_SEQ_NO,
			DIAGNOSIS_SEQ_NO,
			ENCOUNTER_DATE)
			values (
			?1,
			?2,
			?3,
			?4,
			?5,
			?6,
			?7,
			?8,
			?9,
			?10,
			FN_TO_DATE(substr(?11,0,8),'MM/DD/YYYY')
			)
			""";

	/** The values of {@link #DIAGNOSIS_FROM_RAW_TO_STD}, in order. */
	static Object[] insertDiagnosisFromRAWToSTDArgs(Map<?, ?> map) {
		return new Object[] {
				number(map.get("DIAGNOSIS_ID")), number(map.get("ENCOUNTER_DOC_ID")), text(map.get("DIAGNOSIS_TEXT")),
				text(map.get("DIAGNOSIS_CODE")), text(map.get("DIAGNOSIS_CODE_SYSTEM_CODE")), text(map.get("DIAGNOSIS_CODE_SYSTEM_OID")),
				number(map.get("UPHN_TRANX_IN_ID")), number(map.get("CCD_DOC_ID")), number(map.get("ENCOUNTER_SEQ_NO")),
				number(map.get("DIAGNOSIS_SEQ_NO")), date(map.get("START_DATE_TEXT"))
		};
	}

	/** The SQL of {@code getCCDDoc}, as the former mapper had it (1 bound values). */
	static final String CCD_DOC = """
			SELECT  std.DOCUMENT_ID,
				 std.FNAME,
				 std.MNAME,
				 std.LNAME,
			       			 std.DOB_TEXT,
				 std.GENDER,
				 std.ETHNICITY,
				 std.RACE_WHITE,
				 std.RACE_BLACK,
				 std.RACE_ASIAN,
				 std.RACE_INDIAN,
				 std.RACE_PACIFIC,
				 std.RACE_UNKNOWN,
				 std.RACE_OTHER,
				 std.RACE_OTHER_TEXT,
				 std.LANGUAGE,
				 std.PERSON_ADDRESS,
				 std.PERSON_CITY,
				 std.PERSON_STATE,
				 std.PERSON_ZIP,
				 std.PERSON_PLUS4,
				 std.PERSON_COUNTY,
				 std.PERSON_PHONE,
				 std.PERSON_ADDRESS_ID,
				 std.PERSON_COUNTRY,
				 std.PERSON_PHONE_HOME,
				 std.PERSON_PHONE_CELL,
				 std.PERSON_PHONE_WORK,
				 std.ADDRESS_USE,
				 std.USEABLE_PERIOD_LOW,
				 std.USEABLE_PERIOD_HIGH,
				 std.CREATE_DATE,
				 std.MPI,
				 raw1.UPHN_TRANX_IN_ID
					 FROM STD_CCD_DOC  std
			         LEFT OUTER JOIN RAW_CCD_DOC raw1 ON std.DOCUMENT_ID = raw1.DOCUMENT_ID
			         WHERE std.DOCUMENT_ID = ?1
			""";

	/** The SQL of {@code getStdCCDDoc}, as the former mapper had it (1 bound values). */
	static final String STD_CCD_DOC = """
			SELECT  std.DOCUMENT_ID AS documentId,
				 std.FNAME AS fName,
				 std.MNAME AS mName,
				 std.LNAME AS lName,
			       		     std.DOB_TEXT AS dobText,
				 std.GENDER AS gender,
				 std.ETHNICITY AS ethinicity,
				 std.RACE_WHITE AS raceWhite,
				 std.RACE_BLACK AS raceBlack,
				 std.RACE_ASIAN AS raceAsian,
				 std.RACE_INDIAN AS raceIndian,
				 std.RACE_PACIFIC AS racePacific,
				 std.RACE_UNKNOWN AS raceUnknown,
				 std.RACE_OTHER AS raceOther,
				 std.RACE_OTHER_TEXT AS raceOtherText,
				 std.LANGUAGE AS language,
				 std.PERSON_ADDRESS AS personAddress,
				 std.PERSON_CITY AS personCity,
				 std.PERSON_STATE AS personState,
				 std.PERSON_ZIP AS personZip,
				 std.PERSON_PLUS4 AS personPlus4,
				 std.PERSON_COUNTY AS personCounty,
				 std.PERSON_ADDRESS_ID AS personAddressId,
				 std.PERSON_COUNTRY AS personCountry,
				 std.PERSON_PHONE_HOME AS personPhoneHome,
				 std.PERSON_PHONE_CELL AS personPhoneCell,
				 std.PERSON_PHONE_WORK AS personPhoneWork,
				 std.ADDRESS_USE AS addressUse,
				 std.USEABLE_PERIOD_LOW AS useablePeriodLow,
				 std.USEABLE_PERIOD_HIGH AS useablePeriodHigh,
				 std.CREATE_DATE AS createDate,
				 std.EHARS_IMPORT_DATE as eharsImportDate,
			       			 std.EHARS_STATUS_CD as eharsStatusCd,
				 PDA.STATENO as stateno,
				 PDA.DISCARD_STATUS,
				 std.MPI AS mpi,
				 std.UPHN_TRANX_IN_ID
					 FROM STD_CCD_DOC std
			         LEFT OUTER JOIN person_doc_association PDA  ON (std.document_id = PDA.document_id AND PDA.dissassociation_date IS NULL)
			         WHERE STD.DOCUMENT_ID = ?1
			""";

	/** The SQL of {@code getAllCcdCountIndex}, as the former mapper had it (0 bound values). */
	static final String ALL_CCD_COUNT_INDEX = """
			SELECT * from (
			SELECT DOC.DOCUMENT_ID , DOC.UPHN_TRANX_IN_ID,
			   COALESCE( ADDRESS.DOC_ID, 0 ) AS ADDR,
			   COALESCE( PROVIDER.DOC_ID, 0 ) AS PROV,
			   COALESCE( PAYERS.DOC_ID, 0) AS PAYER,
			   COALESCE( LAB.DOC_ID, 0) AS LABS,
			   COALESCE( ENC.DOC_ID, 0) AS ENCOUNTER,
			   COALESCE( MEDS.DOC_ID, 0) AS MED,
			   COALESCE( DIAGS.DOC_ID, 0) AS DIAG,
			   COALESCE( LAB_RESULT.DOC_ID, 0) AS LAB_RES,
			   COALESCE( PROBLEM.DOC_ID, 0) AS PROB
			FROM RAW_CCD_DOC DOC
			LEFT JOIN
			  ( SELECT CCD_DOC_ID, COUNT(*) AS DOC_ID FROM
			    RAW_CCD_PERSON_ADDRESS
			    GROUP BY CCD_DOC_ID ) ADDRESS
			ON DOC.DOCUMENT_ID = ADDRESS.CCD_DOC_ID
			LEFT JOIN
			  ( SELECT CCD_DOC_ID, COUNT(*) AS DOC_ID FROM
			    RAW_CCD_PROVIDER
			    GROUP BY CCD_DOC_ID ) PROVIDER
			ON DOC.DOCUMENT_ID = PROVIDER.CCD_DOC_ID
			LEFT JOIN
			  ( SELECT CCD_DOC_ID, COUNT(*) AS DOC_ID FROM
			    RAW_CCD_PAYER
			    GROUP BY CCD_DOC_ID ) PAYERS
			ON DOC.DOCUMENT_ID = PAYERS.CCD_DOC_ID
			LEFT JOIN
			  ( SELECT CCD_DOC_ID, COUNT(*) AS DOC_ID FROM
			    RAW_CCD_LAB_TEST
			    GROUP BY CCD_DOC_ID ) LAB
			ON DOC.DOCUMENT_ID = LAB.CCD_DOC_ID
			LEFT JOIN
			  ( SELECT CCD_DOC_ID, COUNT(*) AS DOC_ID FROM
			    RAW_CCD_ENCOUNTER
			    GROUP BY CCD_DOC_ID ) ENC
			ON DOC.DOCUMENT_ID = ENC.CCD_DOC_ID
			LEFT JOIN
			  ( SELECT CCD_DOC_ID, COUNT(*) AS DOC_ID FROM
			    RAW_CCD_MED
			    GROUP BY CCD_DOC_ID ) MEDS
			ON DOC.DOCUMENT_ID = MEDS.CCD_DOC_ID
			LEFT JOIN
			  ( SELECT CCD_DOC_ID, COUNT(*) AS DOC_ID FROM
			    RAW_CCD_DIAGNOSIS
			    GROUP BY CCD_DOC_ID) DIAGS
			ON DOC.DOCUMENT_ID = DIAGS.CCD_DOC_ID
			LEFT JOIN
			  ( SELECT CCD_DOC_ID, COUNT(*) AS DOC_ID FROM
			    RAW_CCD_LAB_RESULT
			    GROUP BY CCD_DOC_ID ) LAB_RESULT
			ON DOC.DOCUMENT_ID = LAB_RESULT.CCD_DOC_ID
			LEFT JOIN
			  ( SELECT CCD_DOC_ID, COUNT(*) AS DOC_ID FROM
			    RAW_CCD_PROBLEM
			    GROUP BY CCD_DOC_ID ) PROBLEM
			ON DOC.DOCUMENT_ID = PROBLEM.CCD_DOC_ID
			ORDER BY DOC.DOCUMENT_ID ASC
			)
			where rownum <= 1000
			""";

	/** The SQL of {@code getEncounterSequences}, as the former mapper had it (1 bound values). */
	static final String ENCOUNTER_SEQUENCES = """
			SELECT RAW_CCD_ENCOUNTER_SEQ.NEXTVAL FROM dual CONNECT BY LEVEL <= ?1
			""";

	/** The SQL of {@code getLabTestSequences}, as the former mapper had it (1 bound values). */
	static final String LAB_TEST_SEQUENCES = """
			SELECT RAW_CCD_LAB_TEST_SEQ.NEXTVAL FROM dual CONNECT BY LEVEL <= ?1
			""";

	/** The SQL of {@code getNewRawCCDList}, as the former mapper had it (0 bound values). */
	static final String NEW_RAW_CCD_LIST = """
			SELECT *
				FROM (
				SELECT 	DOCUMENT_ID,
			   					   	FNAME,
				        MNAME,
				        LNAME,
				        DOB_TEXT,
				        GENDER,
				        PERSON_PHONE_HOME,
				        LANGUAGE,
				        ETHNICITY,
				        RACE_DESC,
				        DATE_CREATED,
				        PERSON_PHONE_WORK,
				        RACE_CODE,
				        PERSON_PHONE_CELL,
				        R.UPHN_TRANX_IN_ID,
				        MPI,
				        IS_DUPLICATE,
				        REMOVED,
				        PROCESS_DATE,
				        CURRENT_PROCESS_STATUS,
				        CLEARTEXT_PAYLOAD,
				        HIE_NAME,
				        PAYLOADNAME,
				        PERSON_ADDRESS_ID,
				        CCD_DOC_ID,
				        ADDRESS_USE,
				        PERSON_ADDRESS_1,
				        PERSON_ADDRESS_2,
				        PERSON_CITY,
				        PERSON_STATE,
				        PERSON_ZIP,
				        PERSON_COUNTRY,
				        USEABLE_PERIOD_LOW,
				        USEABLE_PERIOD_HIGH,
				        ADDRESS_SEQ_NO,
				       ROW_NUMBER () OVER (PARTITION BY c.ccd_doc_id ORDER BY c.address_use DESC, c.address_seq_no) rn
				  FROM raw_ccd_doc r, raw_ccd_person_address c
				WHERE r.document_id = c.ccd_doc_id(+)
				   AND r.current_process_status IN (1,4)
				)
				WHERE rn = 1
			""";

	/** The SQL of {@code getCCDAddressList}, as the former mapper had it (1 bound values). */
	static final String CCD_ADDRESS_LIST = """
				SELECT distinct
			                         UPPER(trim(replace(PERSON_ADDRESS_1,'"','')))||' '||UPPER(trim(replace(PERSON_ADDRESS_2,'"',''))) PERSONADDRESS,
			                         UPPER(trim(replace(PERSON_CITY,'"',''))) city,
			                         UPPER(trim(replace(PERSON_STATE,'"',''))) state,
			                         NVL(trim(PERSON_ZIP),'') zip
			               FROM RAW_CCD_PERSON_ADDRESS
			WHERE CCD_DOC_ID =  ?1
			and (PERSON_ADDRESS_1 is not null
				or PERSON_ADDRESS_2 is not null
				or PERSON_CITY is not null
				or PERSON_STATE is not null
				or PERSON_ZIP is not null) order by PERSONADDRESS
			""";

	/** The SQL of {@code getCCDProvidersList}, as the former mapper had it (1 bound values). */
	static final String CCD_PROVIDERS_LIST = """
			SELECT DISTINCT TO_DATE(substr(START_DATE_TEXT,0,8),'YYYYMMDD') startdate,
							TO_CHAR(TO_DATE(substr(START_DATE_TEXT,0,8),'YYYYMMDD'),'MM/DD/YYYY') startDateText,
			       			TO_CHAR(TO_DATE(substr(END_DATE_TEXT,0,8),'YYYYMMDD'),'MM/DD/YYYY') endDateText,
			       			UPPER(trim(NVL(PROVIDER_FNAME,'')|| ' '|| NVL(PROVIDER_MNAME,'')|| ' '||NVL(PROVIDER_LNAME,''))) as providerFullName,
			       			UPPER(trim(REPLACE(NVL(PROVIDER_ADDRESS_1,''),'"','')|| ' '|| NVL(PROVIDER_ADDRESS_2,'')|| ' '|| NVL(PROVIDER_CITY,'')|| ' '|| NVL(PROVIDER_STATE,'')|| ' '|| NVL(PROVIDER_ZIP,''))) as providerAddress,
			       			REPLACE(REPLACE(REPLACE(NVL(PROVIDER_PHONE,''), 'mailto:', ''), 'tel:', ''),'ext:','') phone
			FROM RAW_CCD_PROVIDER where CCD_DOC_ID = ?1
			AND ( START_DATE_TEXT is not null
			               OR PROVIDER_FNAME is not null or PROVIDER_LNAME is not null
			               or PROVIDER_ADDRESS_1 is not null
			               or END_DATE_TEXT is not null
			               or PROVIDER_PHONE is not null
			               )  ORDER BY startdate
			""";

	/** The SQL of {@code getCCDProblemsList}, as the former mapper had it (1 bound values). */
	static final String CCD_PROBLEMS_LIST = """
			SELECT DISTINCT UPPER(trim(FACILITY_NAME)) facilityName,
							UPPER(trim(NVL(PROVIDER_FNAME,'')|| ' '|| NVL(PROVIDER_LNAME,''))) as providerFullName,
							UPPER(trim(PROBLEM_CATEGORY)) problemCategory,
			                     		PROBLEM_CODE problemCode,
			                     		UPPER(trim(PROBLEM_DISPLAY_NAME)) problemDisplayName,
			                     		ENCOUNTER_ID encounterId
			           FROM RAW_CCD_PROBLEM where CCD_DOC_ID = ?1
			""";

	/** The SQL of {@code getCCDEncountersList}, as the former mapper had it (1 bound values). */
	static final String CCD_ENCOUNTERS_LIST = """
			WITH data AS
			                  (
			                  SELECT ENCOUNTER_DOC_ID encounterDocId,
			                         TO_DATE(substr(START_DATE_TEXT,0,8),'YYYYMMDD') START_DATE,
			                         TO_CHAR(TO_DATE(substr(START_DATE_TEXT,0,8),'YYYYMMDD'),'MM/DD/YYYY') startDateText,
			                         TO_CHAR(TO_DATE(substr(END_DATE_TEXT,0,8),'YYYYMMDD'),'MM/DD/YYYY') endDateText,
			                         ENCOUNTER_TYPE encounterType,
			                         UPPER(trim(NVL(PROVIDER_FNAME,'')|| ' '||NVL(PROVIDER_LNAME,''))) as providerFullName,
			                         ENCOUNTER_ID encounterId,
			                         FACILITY_TYPE facilityType,
			                         CASE
			                              WHEN FACILITY_NAME LIKE '%.%.%.%' AND PARTICIPANT_FACILITY_NAME  LIKE '%.%.%.%'  THEN NULL
			                              WHEN PARTICIPANT_FACILITY_NAME  LIKE '%.%.%.%' THEN FACILITY_NAME
			                              WHEN FACILITY_NAME IN ('HWORKS','eClinicalWorks','AFMP','DOHCHS','LIFQHC','WFRCHC','eCW: BRKHC - Boriken Health Center') THEN PARTICIPANT_FACILITY_NAME
			                              WHEN FACILITY_NAME IN ('NYCDOHMH Community Healthcare Network','NYCDOHMH-Project Renewal','NYDOHMH - Help PSI Services Corp','eCW: Brightpoint Health') THEN PARTICIPANT_FACILITY_NAME
			                              WHEN FACILITY_NAME IN ('%eCW: Pediatrics 2000%','NYPL','NYUMC','SSFMA','DRCGC','DRETF','Prominis Medical Services') THEN PARTICIPANT_FACILITY_NAME
			                              WHEN FACILITY_NAME is null THEN PARTICIPANT_FACILITY_NAME
			                              ELSE FACILITY_NAME
			                         END AS text
			                  FROM RAW_CCD_ENCOUNTER a WHERE CCD_DOC_ID = ?1
			                  )
			                  SELECT DISTINCT a.start_date,a.startDateText,a.endDateText, UPPER(trim(Nvl(b.facility_name, a.text))) facilitydetails,UPPER(trim(a.facilityType)) facilityType,UPPER(trim(a.providerFullName)) providerFullName,UPPER(trim(a.encounterType)) encounterType,a.encounterId,a.encounterDocId
			                  FROM data a, CODED_HIE_FACILITY b
			                  WHERE a.text = b.FACILITY_CODE(+) order by a.START_DATE
			""";

	/** The SQL of {@code getDiagnosisList}, as the former mapper had it (1 bound values). */
	static final String DIAGNOSIS_LIST = """
			Select distinct  UPPER(trim(DIAGNOSIS_TEXT)) diagnosisText,
							 UPPER(trim(DIAGNOSIS_CODE)) diagnosisCode
			   from raw_ccd_diagnosis
			   where  ENCOUNTER_DOC_ID = ?1
			   and (DIAGNOSIS_TEXT is not null
			   or DIAGNOSIS_CODE is not null)
			""";

	/** The SQL of {@code getCCDLabTestsList}, as the former mapper had it (1 bound values). */
	static final String CCD_LAB_TESTS_LIST = """
				select  CCD_LAB_TEST_ID ccdLabTestId,
			    				TO_DATE(substr(TEST_DATE,0,8),'YYYYMMDD') testdateorder,
			    				TO_CHAR(TO_DATE(substr(TEST_DATE,0,8),'YYYYMMDD'),'MM/DD/YYYY') testdate,
			    				TEST_CODE testCode,
			    		CASE
			            		WHEN UPPER(trim(nvl(TEST_CODE_DISPLAY_NAME, TEST_COMMENTS))) LIKE '.%'  THEN substr(UPPER(trim(nvl(TEST_CODE_DISPLAY_NAME, TEST_COMMENTS))), 2,length(UPPER(trim(nvl(TEST_CODE_DISPLAY_NAME, TEST_COMMENTS))))-0)
			            		ELSE UPPER(trim(nvl(TEST_CODE_DISPLAY_NAME, TEST_COMMENTS)))
			    		END testCodeDisplayName,
			    				TEST_ENCOUNTER_NUMBER testEncounterNumber,
			    				UPPER(trim(SPECIMEN)) specimen
			from raw_ccd_lab_test WHERE CCD_DOC_ID = ?1 order by testdateorder, testCode
			""";

	/** The SQL of {@code getLabResultsList}, as the former mapper had it (1 bound values). */
	static final String LAB_RESULTS_LIST = """
			select resultDateText,
			 					   componentCode,
			 					   componentDisplayName,
			 					   resultValue,
			 					   resultStatus from
			(select TO_DATE(substr(RESULT_DATE_TEXT,0,8),'YYYYMMDD') resultdate,
				   TO_CHAR(TO_DATE(substr(RESULT_DATE_TEXT,0,8),'YYYYMMDD'),'MM/DD/YYYY') resultDateText,
			                     COMPONENT_CODE componentCode,
			                     UPPER(trim(COMPONENT_DISPLAY_NAME)) componentDisplayName,
			                     UPPER(trim(RESULT_VALUE)) resultValue,
			                     UPPER(trim(RESULT_STATUS)) resultStatus,
			                     UPPER(trim(RESULT_COMMENTS)) resultComments
			               from raw_ccd_lab_result WHERE CCD_LAB_TEST_ID = ?1 order by resultdate)
			""";

	/** The SQL of {@code getCCDMedicationsList}, as the former mapper had it (1 bound values). */
	static final String CCD_MEDICATIONS_LIST = """
			select DISTINCT TO_DATE(substr(START_DATE_TEXT,0,8),'YYYYMMDD') startdate,
				 TO_CHAR(TO_DATE(substr(START_DATE_TEXT,0,8),'YYYYMMDD'),'MM/DD/YYYY') startDateText,
			      TO_CHAR(TO_DATE(substr(STOP_DATE_TEXT,0,8),'YYYYMMDD'),'MM/DD/YYYY') stopDateText,
			      UPPER(trim(ORDERED_MEDICATION_NAME)) orderedMedicationName,
			      UPPER(trim(FILLED_MEDICATION_NAME)) filledMedicationName,
			      UPPER(trim(NVL(ORDERING_PROV_FNAME,'')|| ' '||NVL(ORDERING_PROV_LNAME,''))) as providerFullName
			from raw_ccd_med WHERE CCD_DOC_ID = ?1 ORDER BY startdate
			""";

	/** The SQL of {@code getCCDPayersList}, as the former mapper had it (1 bound values). */
	static final String CCD_PAYERS_LIST = """
			select DISTINCT UPPER(trim(POLICY_NAME)) policyName,
			               NVL(REPLACE(MEMBER_ID,'-',''),'') memberId,
			               UPPER(trim(NVL(MEMBER_FNAME,'')|| ' '||NVL(MEMBER_LNAME,''))) memberFullName,
			               TO_CHAR(TO_DATE(substr(MEMBER_DOB_TEXT,0,8),'YYYYMMDD'),'MM/DD/YYYY') memberDobText
			        from raw_ccd_payer WHERE CCD_DOC_ID = ?1
			""";

	/**
	 * The SQL of {@code addUphnRecord}, as the former mapper had it except that the key, formerly
	 * {@code UPHN_TRANX_IN_SEQ_ID.nextval} in the insert and returned as its generated key, is {@code ?1}: the DAO takes
	 * it from the sequence first ({@link #NEXT_UPHN_TRANX_IN_ID}) to give it back to the caller (4 bound values).
	 */
	static final String ADD_UPHN_RECORD = """
			insert into UPHN_TRANX_IN (UPHN_TRANX_IN_ID, PAYLOADNAME, ORGNAME, TOPIC, RECEIVEDTIME, LASTUPDATETIME) values(?1,?2, ?3, 'DEERPROD', ?4,  SYSDATE)
			""";

	/** The next key of UPHN_TRANX_IN (the sequence the former insert used). */
	static final String NEXT_UPHN_TRANX_IN_ID = "select UPHN_TRANX_IN_SEQ_ID.nextval from dual";

	/** The values of {@link #ADD_UPHN_RECORD}, in order: the key, then the mapper's values. */
	static Object[] addUphnRecordArgs(Object uphnId, Map<?, ?> map) {
		return new Object[] {
				uphnId, text(map.get("p_fileName")), text(map.get("p_hieName")), text(map.get("p_dateCreated"))
		};
	}

	/** The SQL of {@code updateUphnRecord}, as the former mapper had it (2 bound values). */
	static final String UPDATE_UPHN_RECORD = """
			update UPHN_TRANX_IN set PAYLOADBINARYCONTENT = ?1 where UPHN_TRANX_IN_ID = ?2
			""";

	/** The values of {@link #UPDATE_UPHN_RECORD}, in order. */
	static Object[] updateUphnRecordArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("p_ccdDocument"), map.get("uphnId")
		};
	}
}
