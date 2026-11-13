package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.timestamp;

import java.util.Map;

import org.hibernate.type.StandardBasicTypes;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BeanPaths;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;

/**
 * The SQL of the {@link HL7DocumentDAO} statements that stay native, taken unchanged from the former MyBatis mapper
 * ({@code #{...}} replaced by {@code ?1, ?2, ...}; generated from the mapper XML); why each one stays native is said at
 * its DAO method. The {@code ...Args} methods give the values bound to a statement, in order, typed as the mapper's
 * {@code jdbcType} bound a null ({@code VARCHAR} and {@code CLOB} a text, {@code DECIMAL} / {@code INTEGER} a number,
 * {@code DATE} a date, {@code TIMESTAMP} a timestamp); a value that is not null is bound as it is, as MyBatis bound it by
 * its Java type (a {@code java.util.Date} with {@code jdbcType=DATE} as its day only, as MyBatis' DateOnlyTypeHandler did).
 * A property path ({@code a.b.c}) is read as MyBatis read it ({@link BeanPaths}). The former {@code <foreach>} inserts
 * ({@code union all} of one select per item) are the SQL of ONE item, with {@code ...ItemArgs(map, item)}: the DAO runs
 * it once per item.
 */
final class HL7DocumentSql {

	private HL7DocumentSql() {
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

	/** The SQL of {@code insertRawHL7DocData}, as the former mapper had it (59 bound values). */
	static final String RAW_HL7_DOC_DATA = """
			    insert into RAW_HL7_DOC (
			      DOCUMENT_ID,
			CURRENT_PROCESS_STATUS,
			MSHSENDINGFACILITYCODE,
			MSHSENDINGFACILITYNAME,
			NYC_SHIP_DATE,
			PIDADDRESSLINE1,
			PIDADDRESSLINE2,
			PIDCITY,
			PIDCOUNTRYCODE,
			PIDCOUNTRYOFORIGIN,--10
			PIDDATEOFBIRTHRAW,
			PIDDIN,
			PIDETHNICGROUPCODE,
			PIDFACILITYIDEXTERNAL,
			PIDFACILITYIDINTERNAL,
			PIDFIRSTNAME,
			PIDHOMEPHONE,
			PIDLASTNAME,
			PIDMEDICAIDNUMBER,--20
			PIDMEDICALRECORDNUMBER,
			PIDMIDDLENAME,
			PIDMPI,
			PIDNAMESUFFIX,
			PIDNYSID,
			PIDPATIENTACCOUNTNUM,
			PIDPATIENTIDEXTERNAL,
			PIDPATIENTIDINTERNAL,
			PIDRACECODERAW,
			PIDSEXCODE,--30
			PIDSSN,
			PIDSTATE,
			PIDTYPEOFINSURANCE,
			PIDVACCINETRIALIND,
			PIDZIPCODE,
			MSHCREATEDATE,
			PIDDEATHDATE,
			PIDDEATHIND,
			MSHMESSAGECONTROLID,
			MSHMESSAGEDATERAW,--40
			MSHMESSAGETYPE,
			PIDPATIENTIDLIST,
			PV1PROVIDERFIRSTNAME,
			PV1PROVIDERLASTNAME,
			PV1PROVIDERAUTH,
			PV1ATTENDINGPROVIDERLIST,
			PIDRACECODESYSTEM,
			MSHSENDINGFACILITYCODESYSTEM,
			PIDVETERANSTATUS,
			PIDWORKPHONE,--50
			UPHN_TRANX_IN_ID,
			PV1PROVIDERID,
			PV1PROVIDERMIDDLENAME,
			MSHMESSAGECLASS,
			EVNEVENTTYPE,
			EVNEVENTDATE,
			PV1REFERRINGPROVIDERLIST,
			SEGMENTPROFILE,
			RACECODEDESC,
			PIDNOTE)
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
			?10,--10
			?11,
			?12,
			?13,
			?14,
			?15,
			?16,
			?17,
			?18,
			?19,--20
			?20,
			?21,
			?22,
			?23,
			?24,
			?25,
			?26,
			?27,
			?28,
			?29,--30
			?30,
			?31,
			?32,
			?33,
			?34,
			FN_TO_DATE(substr(?35,0,8),'MM/DD/YYYY'),
			?36,
			?37,
			?38,
			?39,--40
			   ?40,
			?41,
			?42,
			?43,
			?44,
			?45,
			?46,
			?47,
			?48,
			?49,--50
			?50,
			?51,
			?52,
			?53,
			?54,
			?55,
			?56,
			?57,
			?58,
			?59
			      )
			""";

	/** The values of {@link #RAW_HL7_DOC_DATA}, in order. */
	static Object[] insertRawHL7DocDataArgs(RawHL7DocVO vo) {
		return new Object[] {
				number(BeanPaths.get(vo, "documentId")), number(BeanPaths.get(vo, "currentProcessStatus")), text(BeanPaths.get(vo, "theMshVO.mshSendingFacilityCode")),
				text(BeanPaths.get(vo, "theMshVO.mshSendingFacilityName")), date(BeanPaths.get(vo, "nycShipDate")), text(BeanPaths.get(vo, "thePidVO.patientAddress.pidAddressLine1")),
				text(BeanPaths.get(vo, "thePidVO.patientAddress.pidAddressLine2")), text(BeanPaths.get(vo, "thePidVO.patientAddress.pidCity")), text(BeanPaths.get(vo, "thePidVO.patientAddress.pidCountryCode")),
				text(BeanPaths.get(vo, "thePidVO.patientAddress.pidCountryOfOrigin")), text(BeanPaths.get(vo, "thePidVO.pidDateOfBirthRaw")), text(BeanPaths.get(vo, "thePidVO.pidDin")),
				text(BeanPaths.get(vo, "thePidVO.pidEthnicGroupCode")), text(BeanPaths.get(vo, "thePidVO.pidFacilityIdExternal")), text(BeanPaths.get(vo, "thePidVO.pidFacilityIdInternal")),
				text(BeanPaths.get(vo, "thePidVO.patientName.firstName")), text(BeanPaths.get(vo, "thePidVO.pidHomePhone")), text(BeanPaths.get(vo, "thePidVO.patientName.lastName")),
				text(BeanPaths.get(vo, "thePidVO.pidMedicaidNumber")), text(BeanPaths.get(vo, "thePidVO.pidMedicalRecordnumber")), text(BeanPaths.get(vo, "thePidVO.patientName.middleName")),
				text(BeanPaths.get(vo, "thePidVO.pidMpi")), text(BeanPaths.get(vo, "thePidVO.patientName.nameSuffix")), text(BeanPaths.get(vo, "thePidVO.pidNysid")),
				text(BeanPaths.get(vo, "thePidVO.pidPatientAccountNumber")), text(BeanPaths.get(vo, "thePidVO.pidPatientIdExternal")), text(BeanPaths.get(vo, "thePidVO.pidPatientIdInternal")),
				text(BeanPaths.get(vo, "thePidVO.pidRaceCodeRaw")), text(BeanPaths.get(vo, "thePidVO.pidSexCode")), text(BeanPaths.get(vo, "thePidVO.pidSsn")),
				text(BeanPaths.get(vo, "thePidVO.patientAddress.pidState")), text(BeanPaths.get(vo, "thePidVO.pidTypeOfInsurance")), text(BeanPaths.get(vo, "thePidVO.pidVaccinceTrailInd")),
				text(BeanPaths.get(vo, "thePidVO.patientAddress.pidZipCode")), date(BeanPaths.get(vo, "theMshVO.mshCreateDate")), text(BeanPaths.get(vo, "thePidVO.pidDeathDate")),
				text(BeanPaths.get(vo, "thePidVO.pidDeathInd")), text(BeanPaths.get(vo, "theMshVO.mshMessageControlId")), text(BeanPaths.get(vo, "theMshVO.mshCreateDate")),
				text(BeanPaths.get(vo, "mshMessageType")), text(BeanPaths.get(vo, "thePidVO.pidPatientIDList")), text(BeanPaths.get(vo, "thePv1VO.providerName.firstName")),
				text(BeanPaths.get(vo, "thePv1VO.providerName.lastName")), text(BeanPaths.get(vo, "thePv1VO.pv1ProviderAuth")), text(BeanPaths.get(vo, "thePv1VO.pv1AttendingProviderList")),
				text(BeanPaths.get(vo, "thePidVO.pidRaceCodeSystem")), text(BeanPaths.get(vo, "theMshVO.mshSendingFacilityCodeSystem")), text(BeanPaths.get(vo, "thePidVO.pidVeteranStatus")),
				text(BeanPaths.get(vo, "thePidVO.pidWorkPhone")), number(BeanPaths.get(vo, "uphnId")), text(BeanPaths.get(vo, "thePv1VO.pv1ProviderID")),
				text(BeanPaths.get(vo, "thePv1VO.providerName.middleName")), text(BeanPaths.get(vo, "mshMessageClass")), text(BeanPaths.get(vo, "evnEventType")),
				text(BeanPaths.get(vo, "evnEventDate")), text(BeanPaths.get(vo, "thePv1VO.pv1ReferringProviderList")), text(BeanPaths.get(vo, "segmentProfile")),
				text(BeanPaths.get(vo, "thePidVO.pidRaceCodeDesc")), text(BeanPaths.get(vo, "thePidVO.pidNote"))
		};
	}

	/** The SQL of {@code insertRawHL7OBR}, for one item of labtestVO (28 bound values). */
	static final String RAW_HL7_OBR_ITEM = """
			INSERT
			INTO RAW_HL7_LAB_TEST
			( HL7_LAB_TEST_ID,
			HL7_DOC_ID,
			UPHN_TRANX_IN_ID,
			OBRSETID,
			OBRACCESSIONNUM,
			OBRCALLBACKPHONE,
			OBRCOLLECTIONDATE,
			OBROBSERVATIONDATE,
			OBRPROVIDERFIRSTNAME,
			OBRPROVIDERLASTNAME,
			OBRPROVIDERMIDDLENAME,
			OBRPROVIDERNAMESUFFIX,
			OBRPROVIDERFACILITYID,
			OBRPROVIDERLICENSEID,
			OBRPROVIDERNPI,
			OBRPROVIDERIDLIST,
			OBRRESULTREPORTDATE,
			OBRSPECIMENSOURCECODE,
			OBRSPECIMENSOURCENAME,
			OBRLOINCDESC,
			OBRLOINCCODE,
			OBRTESTCODE,
			OBRTESTDESC,
			OBRTESTCODINGSYSTEM,
			OBRALTTESTCODE,
			OBRALTTESTDESC,
			OBRALTTESTCODINGSYSTEM,
			OBRNOTE
			)
			
			
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
				?28 from dual
			""";

	/** The values of {@link #RAW_HL7_OBR_ITEM} for one item of the former collection, in order. */
	static Object[] insertRawHL7OBRItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(BeanPaths.get(item, "hl7labTestId")), number(map.get("hl7DocId")), number(BeanPaths.get(item, "uphnTranxInId")),
				number(BeanPaths.get(item, "obrSetId")), text(BeanPaths.get(item, "obrAccessionNum")), text(BeanPaths.get(item, "obrcallBackPhone")),
				date(BeanPaths.get(item, "obrCollectionDate")), date(BeanPaths.get(item, "obrObservationDate")), text(BeanPaths.get(item, "obrProviderFirstName")),
				text(BeanPaths.get(item, "obrProviderLastName")), text(BeanPaths.get(item, "obrProviderMiddleName")), text(BeanPaths.get(item, "obrProviderNameSuffix")),
				text(BeanPaths.get(item, "obrProviderFacilityId")), text(BeanPaths.get(item, "obrProviderLicenseId")), text(BeanPaths.get(item, "obrProviderNpi")),
				text(BeanPaths.get(item, "obrProviderIdList")), date(BeanPaths.get(item, "obrResultReportDate")), text(BeanPaths.get(item, "obrSpecimenSourceCode")),
				text(BeanPaths.get(item, "obrSpecimenSourcename")), text(BeanPaths.get(item, "obrLoincDesc")), text(BeanPaths.get(item, "obrLoincCode")),
				text(BeanPaths.get(item, "obrTestCode")), text(BeanPaths.get(item, "obrTestDesc")), text(BeanPaths.get(item, "obrTestCodingSystem")),
				text(BeanPaths.get(item, "obrAltTestCode")), text(BeanPaths.get(item, "obrAltTestDesc")), text(BeanPaths.get(item, "obrAltTestCodingSystem")),
				text(BeanPaths.get(item, "obrNote"))
		};
	}

	/** The SQL of {@code insertRawHL7OBX}, for one item of labResultVO (18 bound values). */
	static final String RAW_HL7_OBX_ITEM = """
			INSERT INTO RAW_HL7_LAB_RESULT
			(
			HL7_LAB_RESULT_ID,
			HL7_DOC_ID,
			HL7_LAB_TEST_ID,
			UPHN_TRANX_IN_ID,
			OBXSETID,
			OBXLOINCDESC,
			OBXLOINCCODE,
			OBXTESTCODE,
			OBXTESTDESC,
			OBXTESTCODINGSYSTEM,
			OBXALTTESTCODE,
			OBXALTTESTDESC,
			OBXALTTESTCODINGSYSTEM,
			OBXUNITOFMEASURE,
			OBXOBSERVATIONRESULTTEXT,
			OBXOBSERVATIONVALUETYPE,
			OBXNOTE,
			OBXREFERENCERANGE,
			OBXANORMALFLAG
			)
			select
			raw_hl7_lab_result_seq.NEXTVAL,d.* from (
			
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
				TO_CLOB(?14),
				?15,
				?16,
				?17,
				?18 from dual
			
			)d
			""";

	/** The values of {@link #RAW_HL7_OBX_ITEM} for one item of the former collection, in order. */
	static Object[] insertRawHL7OBXItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(map.get("hl7DocId")), number(BeanPaths.get(item, "hl7labTestId")), number(BeanPaths.get(item, "uphnTranxInId")),
				number(BeanPaths.get(item, "obxSetId")), text(BeanPaths.get(item, "obxLoincDesc")), text(BeanPaths.get(item, "obxLoincCode")),
				text(BeanPaths.get(item, "obxTestCode")), text(BeanPaths.get(item, "obxTestDesc")), text(BeanPaths.get(item, "obxTestCodingSystem")),
				text(BeanPaths.get(item, "obxAltTestCode")), text(BeanPaths.get(item, "obxAltTestDesc")), text(BeanPaths.get(item, "obxAltTestCodingSystem")),
				text(BeanPaths.get(item, "obxUnitOfMeasure")), text(BeanPaths.get(item, "obxObservationResultText")), text(BeanPaths.get(item, "obxObservationValueType")),
				text(BeanPaths.get(item, "obxNote")), text(BeanPaths.get(item, "obxReferenceRange")), text(BeanPaths.get(item, "obxAbnormalFlag"))
		};
	}

	/** The SQL of {@code insertHL7FromRAWToSTD}, as the former mapper had it (58 bound values). */
	static final String HL7_FROM_RAW_TO_STD = """
			insert into
			STD_HL7_DOC
			(
			DOCUMENT_ID,
			UPHN_TRANX_IN_ID,
			DIN,
			ETHNICITY,
			FNAME,
			GENDER,
			LNAME,
			MED_RECORD_NUMBER,
			MNAME,
			MPI,--10
			NYSID,
			PERSON_ADDRESS,
			PERSON_CITY,
			PERSON_COUNTRY,
			PERSON_EXTERNAL_ID,
			PERSON_INTERNAL_ID,
			PERSON_PHONE,
			PERSON_PLUS4,
			PERSON_STATE,
			PERSON_ZIP,--20
			PROVIDER_FNAME,
			PROVIDER_ID,
			PROVIDER_ID_AUTH,
			PROVIDER_LNAME,
			RACE_ASIAN,
			RACE_BLACK,
			RACE_INDIAN,
			RACE_OTHER,
			RACE_PACIFIC,--30
			RACE_WHITE,
			SSN,
			SUFFIX,
			BIRTH_COUNTRY,
			CURRENT_PROCESS_STATUS,
			DATE_OF_BIRTH,
			DATE_RECEIVED,
			FACILITY_CODE,
			FACILITY_NAME,
			HL7_MESSAGE_TYPE,--40
			INSURANCE_NUMBER,
			MEDICAID_NUMBER,
			RACE_UNKNOWN,
			VETERAN_STATUS,
			DOB_TEXT,
			CCD_MATCH_DOC_ID,
			CCD_MATCH_STATENO,
			MATCH_PROCESS_DATE,
			PERSON_COUNTY,
			FACILITY_ADDRESS,
			FACILITY_CITY,
			FACILITY_STATE,
			FACILITY_Zip,
			RACE_UNTRANSLATABLE,
			RACE_OTHER_TEXT,
			FACILITY_COUNTY,
			EVENT_DATE,
			EVENT_TYPE,
			ENCOUNTER_ID
			)
			values (
			?1,
			?2,
			?3,
			upper(trim(?4)),
			upper(trim(?5)),
			upper(trim(?6)),
			upper(trim(?7)),
			?8,
			upper(trim(?9)),
			?10,--10
			?11,
			upper(trim(?12)),
			upper(trim(?13)),
			upper(trim(?14)),
			upper(trim(?15)),
			upper(trim(?16)),
			?17,
			?18,
			upper(trim(?19)),
			upper(trim(?20)),--20
			upper(trim(?21)),
			?22,
			?23,
			upper(trim(?24)),
			upper(trim(?25)),
			upper(trim(?26)),
			upper(trim(?27)),
			upper(trim(?28)),
			upper(trim(?29)),--30
			upper(trim(?30)),
			?31,
			upper(trim(?32)),
			?33,
			?34,
			FN_TO_DATE(?35,'MM/DD/YYYY'),
			?36,
			?37,
			?38,
			?39,--40
			?40,
			?41,
			upper(trim(?42)),
			?43,
			TO_CHAR(FN_TO_DATE(?44,'MM/DD/YYYY'),'MM/DD/YYYY'),
			?45,
			?46,
			?47,
			?48,
			?49,
			?50,
			?51,
			?52,
			upper(trim(?53)),
			upper(trim(?54)),
			?55,
			FN_TO_DATE(substr(?56,0,8),'MM/DD/YYYY'),
			?57,
			?58
			)
			""";

	/** The values of {@link #HL7_FROM_RAW_TO_STD}, in order. */
	static Object[] insertHL7FromRAWToSTDArgs(Map<?, ?> map) {
		return new Object[] {
				number(map.get("DOCUMENT_ID")), number(map.get("UPHN_TRANX_IN_ID")), text(map.get("DIN")),
				text(map.get("ETHNICITY")), text(map.get("FNAME")), text(map.get("GENDER")),
				text(map.get("LNAME")), text(map.get("MED_RECORD_NUMBER")), text(map.get("MNAME")),
				text(map.get("MPI")), text(map.get("NYSID")), text(map.get("PERSON_ADDRESS")),
				text(map.get("PERSON_CITY")), text(map.get("PERSON_COUNTRY")), text(map.get("PERSON_EXTERNAL_ID")),
				text(map.get("PERSON_INTERNAL_ID")), text(map.get("PERSON_PHONE")), text(map.get("PERSON_PLUS4")),
				text(map.get("PERSON_STATE")), text(map.get("PERSON_ZIP")), text(map.get("PROVIDER_FNAME")),
				number(map.get("PROVIDER_ID")), text(map.get("PROVIDER_ID_AUTH")), text(map.get("PROVIDER_LNAME")),
				text(map.get("RACE_ASIAN")), text(map.get("RACE_BLACK")), text(map.get("RACE_INDIAN")),
				text(map.get("RACE_OTHER")), text(map.get("RACE_PACIFIC")), text(map.get("RACE_WHITE")),
				text(map.get("SSN")), text(map.get("SUFFIX")), text(map.get("BIRTH_COUNTRY")),
				text(map.get("CURRENT_PROCESS_STATUS")), date(map.get("DOB_TEXT")), date(map.get("DATE_RECEIVED")),
				text(map.get("FACILITY_CODE")), text(map.get("FACILITY_NAME")), text(map.get("HL7_MESSAGE_TYPE")),
				text(map.get("INSURANCE_NUMBER")), text(map.get("MEDICAID_NUMBER")), text(map.get("RACE_UNKNOWN")),
				text(map.get("VETERAN_STATUS")), date(map.get("DOB_TEXT")), number(map.get("CCD_MATCH_DOC_ID")),
				text(map.get("CCD_MATCH_STATENO")), date(map.get("MATCH_PROCESS_DATE")), text(map.get("PERSON_COUNTY")),
				text(map.get("FACILITY_ADDRESS")), text(map.get("FACILITY_CITY")), text(map.get("FACILITY_STATE")),
				text(map.get("FACILITY_ZIP")), text(map.get("RACE_UNTRANSLATABLE")), text(map.get("RACE_OTHER_TEXT")),
				text(map.get("FACILITY_COUNTY")), date(map.get("EVENT_DATE")), text(map.get("EVENT_TYPE")),
				text(map.get("ENCOUNTER_ID"))
		};
	}

	/** The SQL of {@code insertDG1List}, for one item of dg1VO (9 bound values). */
	static final String DG1_ITEM = """
			INSERT
			INTO RAW_HL7_ADT_DG1
			(
			HL7_DOC_ID,
			UPHN_TRANX_IN_ID,
			DX_CODING_METHOD,
			DX_CODE_ID,
			DX_CODE_TEXT,
			DX_CODE_SYSTEM,
			DX_DESC,
			DX_DATE_TEXT,
			DX_TYPE
			)
			
			
				select ?1,
				?2,
				?3,
				?4,
				?5,
				?6,
				?7,
				?8,
				?9 from dual
			""";

	/** The values of {@link #DG1_ITEM} for one item of the former collection, in order. */
	static Object[] insertDG1ListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(map.get("hl7DocId")), number(BeanPaths.get(item, "uphnId")), text(BeanPaths.get(item, "dxCodingMethod")),
				text(BeanPaths.get(item, "dxCodeId")), text(BeanPaths.get(item, "dxCodetext")), text(BeanPaths.get(item, "dxCodeSystem")),
				text(BeanPaths.get(item, "dxDesc")), text(BeanPaths.get(item, "dxDateText")), text(BeanPaths.get(item, "dxType"))
		};
	}

	/** The SQL of {@code insertORCList}, for one item of orcVO (37 bound values). */
	static final String ORC_ITEM = """
			INSERT
			INTO RAW_HL7_PHARM
			(
			HL7_DOC_ID,
			UPHN_TRANX_IN_ID,
			ORDER_CONTROL,
			PLACER_ORDER_NUMBER,
			FILLER_ORDER_NUMBER,
			PLACER_GROUP_NUMBER,
			ORDER_STATUS,
			DATETIME_OF_TRANSACTION,
			ENTERING_ORGANIZATION,
			ORDER_PROVIDER_FNAME,--10
			ORDER_PROVIDER_LNAME,
			ORDER_PROVIDER_MNAME,
			ORDER_PROVIDER_ID,
			ORDER_EFFECTIVE_DATETIME,
			ORDERING_FACILITY_NAME,
			ORDERING_FACILITY_ADDRESS1,
			ORDERING_FACILITY_ADDRESS2,
			ORDERING_FACILITY_CITY,
			ORDERING_FACILITY_STATE,
			ORDERING_FACILITY_ZIPCODE,--20
			ORDERING_FACILITY_COUNTY,
			ORDERING_FACILITY_COUNTRY,
			ORDERING_FACILITY_PHONE,
			ORDERING_PROVIDER_ADDRESS1,
			ORDERING_PROVIDER_ADDRESS2,
			ORDERING_PROVIDER_CITY,
			ORDERING_PROVIDER_STATE,
			ORDERING_PROVIDER_ZIPCODE,
			ORDERING_PROVIDER_COUNTY,
			ORDERING_PROVIDER_COUNTRY,--30
			ORDER_TYPE,
			RXO_MED_NAME,
			RXC_MED_NAME,
			RXE_MED_NAME,
			RXE_ORIGINAL_ORDER_DATE,
			RXE_PHARM_REFILL,
			RXE_DISP_PHARM
			)
			
				select ?1,
				?2,
				?3,
				?4,
				?5,
				?6,
				?7,
				?8,
				?9,
				?10,--10
				?11,
				?12,
				?13,
				?14,
				?15,
				?16,
				?17,
				?18,
				?19,
				?20,--20
				?21,
				?22,
				?23,
				?24,
				?25,
				?26,
				?27,
				?28,
				?29,
				?30,--30
				?31,
				?32,
				?33,
				?34,
				?35,
				?36,
				?37 from dual
			""";

	/** The values of {@link #ORC_ITEM} for one item of the former collection, in order. */
	static Object[] insertORCListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(map.get("hl7DocId")), number(BeanPaths.get(item, "uphnId")), text(BeanPaths.get(item, "orderControl")),
				text(BeanPaths.get(item, "placerOrderNumber")), text(BeanPaths.get(item, "fillerOrderNumber")), text(BeanPaths.get(item, "placerGroupNumber")),
				text(BeanPaths.get(item, "orderStatus")), text(BeanPaths.get(item, "dateTimeOfTransaction")), text(BeanPaths.get(item, "enteringOrganization")),
				text(BeanPaths.get(item, "orderProviderFname")), text(BeanPaths.get(item, "orderProviderLname")), text(BeanPaths.get(item, "orderProviderMname")),
				text(BeanPaths.get(item, "orderProviderId")), text(BeanPaths.get(item, "orderEffectiveDateTime")), text(BeanPaths.get(item, "orderingFacilityName")),
				text(BeanPaths.get(item, "orderingFacilityAddress1")), text(BeanPaths.get(item, "orderingFacilityAddress2")), text(BeanPaths.get(item, "orderingFacilityCity")),
				text(BeanPaths.get(item, "orderingFacilityState")), text(BeanPaths.get(item, "orderingFacilityZipCode")), text(BeanPaths.get(item, "orderingFacilityCounty")),
				text(BeanPaths.get(item, "orderingFacilityCountry")), text(BeanPaths.get(item, "orderingFacilityPhone")), text(BeanPaths.get(item, "orderingProviderAddress1")),
				text(BeanPaths.get(item, "orderingProviderAddress2")), text(BeanPaths.get(item, "orderingProviderCity")), text(BeanPaths.get(item, "orderingProviderState")),
				text(BeanPaths.get(item, "orderingProviderZipCode")), text(BeanPaths.get(item, "orderingProviderCounty")), text(BeanPaths.get(item, "orderingProviderCountry")),
				text(BeanPaths.get(item, "orderType")), text(BeanPaths.get(item, "rxoMedName")), text(BeanPaths.get(item, "rxcMedName")),
				text(BeanPaths.get(item, "rxeMedName")), text(BeanPaths.get(item, "rxeoriginalOrderDate")), text(BeanPaths.get(item, "rxePharmRefill")),
				text(BeanPaths.get(item, "rxeDispPharm"))
		};
	}

	/** The SQL of {@code insertINSList}, for one item of insVO (47 bound values). */
	static final String INS_ITEM = """
			INSERT
			INTO RAW_HL7_INSURANCE
			(
			HL7_DOC_ID,
			UPHN_TRANX_IN_ID,
			SET_ID,
			PLAN_ID,
			COMPANY_ID,
			COMPANY_NAME,
			COMPANY_ADDRESS1,
			COMPANY_ADDRESS2,
			COMPANY_CITY,
			COMPANY_STATE,
			COMPANY_ZIPCODE,
			COMPANY_COUNTY,
			COMPANY_COUNTRY,
			COMPANY_PHONE,
			GROUP_NUMBER,
			GROUP_NAME,
			INSURED_GROUP_EMP_ID,
			INSURED_GROUP_EMP_NAME,
			PLAN_EFFECTIVE_DATE,
			PLAN_EXPIRATION_DATE,
			PLAN_TYPE,
			INSURED_FNAME,
			INSURED_LNAME,
			INSURED_MNAME,
			INSURED_RELATIONSHIP,
			INSURED_DOB_TEXT,
			INSURED_ADDRESS1,
			INSURED_ADDRESS2,
			INSURED_CITY,
			INSURED_STATE,
			INSURED_ZIPCODE,
			INSURED_COUNTY,
			INSURED_COUNTRY,
			POLICY_NUMBER,
			SSN,
			EMPLOYER_NAME,
			MEDICAID_FNAME,
			MEDICAID_LNAME,
			MEDICAID_MNAME,
			MEDICAID_CASE_NUMBER,
			MARITAL_STATUS,
			NATIONALITY,
			ETHNIC_GROUP,
			JOB_TITLE,
			JOB_STATUS,
			INSURED_PHONE_NUMBER,
			RACE
			)
			
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
				substr(?16,0,100),
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
				?41,
				?42,
				?43,
				?44,
				?45,
				?46,
				?47 from dual
			""";

	/** The values of {@link #INS_ITEM} for one item of the former collection, in order. */
	static Object[] insertINSListItemArgs(Map<?, ?> map, Object item) {
		return new Object[] {
				number(map.get("hl7DocId")), number(BeanPaths.get(item, "uphnId")), number(BeanPaths.get(item, "setId")),
				text(BeanPaths.get(item, "planId")), text(BeanPaths.get(item, "companyId")), text(BeanPaths.get(item, "companyName")),
				text(BeanPaths.get(item, "companyAddress1")), text(BeanPaths.get(item, "companyAddress2")), text(BeanPaths.get(item, "companyCity")),
				text(BeanPaths.get(item, "companyState")), text(BeanPaths.get(item, "companyZipCode")), text(BeanPaths.get(item, "companyCounty")),
				text(BeanPaths.get(item, "companyCountry")), text(BeanPaths.get(item, "companyPhone")), text(BeanPaths.get(item, "groupNumber")),
				text(BeanPaths.get(item, "groupName")), text(BeanPaths.get(item, "insuredGroupEmpId")), text(BeanPaths.get(item, "insuredGroupEmpName")),
				text(BeanPaths.get(item, "planEffectiveDate")), text(BeanPaths.get(item, "planExpirationDate")), text(BeanPaths.get(item, "planType")),
				text(BeanPaths.get(item, "insuredFName")), text(BeanPaths.get(item, "insuredLName")), text(BeanPaths.get(item, "insuredMName")),
				text(BeanPaths.get(item, "insuredRelationShip")), text(BeanPaths.get(item, "insuredDOBText")), text(BeanPaths.get(item, "insuredAddress1")),
				text(BeanPaths.get(item, "insuredAddress2")), text(BeanPaths.get(item, "insuredCity")), text(BeanPaths.get(item, "insuredState")),
				text(BeanPaths.get(item, "insuredZipCode")), text(BeanPaths.get(item, "insuredCounty")), text(BeanPaths.get(item, "insuredCountry")),
				text(BeanPaths.get(item, "policyNumber")), text(BeanPaths.get(item, "ssn")), text(BeanPaths.get(item, "employerName")),
				text(BeanPaths.get(item, "medicaidFname")), text(BeanPaths.get(item, "medicaidLname")), text(BeanPaths.get(item, "medicaidMname")),
				text(BeanPaths.get(item, "medicaidCaseNumber")), text(BeanPaths.get(item, "maritalStatus")), text(BeanPaths.get(item, "nationality")),
				text(BeanPaths.get(item, "ethnicgroup")), text(BeanPaths.get(item, "jobTitle")), text(BeanPaths.get(item, "jobStatus")),
				text(BeanPaths.get(item, "insuredPhoneNumber")), text(BeanPaths.get(item, "race"))
		};
	}

	/** The SQL of {@code getHL7LabTestSequences}, as the former mapper had it (1 bound values). */
	static final String HL7_LAB_TEST_SEQUENCES = """
			SELECT raw_hl7_lab_test_seq.NEXTVAL FROM dual CONNECT BY LEVEL <= ?1
			""";

	/** The SQL of {@code updateCurrentProcessStatusInRaw}, as the former mapper had it (0 bound values). */
	static final String UPDATE_CURRENT_PROCESS_STATUS_IN_RAW = """
			DECLARE
			            v_doc_id NUM_ARR;
			            v_status VARCHAR2_NTT;
			            CURSOR LCU IS SELECT r.document_id , CASE WHEN  R.PIDMPI IS NULL THEN 18
			                                                      WHEN c.document_id IS NULL THEN 37
			                                      				  WHEN C.flux_MATCH_CD IN ('N','C_N','P_N') THEN 36
			                                      				  WHEN C.flux_MATCH_CD = 'C' THEN 37 ELSE 1 END process_status
			          	              FROM RAW_HL7_DOC R , STD_CCD_DOC C
			                          WHERE R.CURRENT_PROCESS_STATUS IN (1,37,4)  -- ADD 36 later
			          	 	 	 	  AND  R.PIDMPI=C.MPI (+);
			BEGIN
					OPEN LCU;
					LOOP
			   					FETCH LCU
			    				BULK COLLECT INTO v_doc_id,v_status LIMIT  10000;
			    				FORALL indx IN INDICES OF v_doc_id
			    				UPDATE RAW_HL7_DOC
			    				SET CURRENT_PROCESS_STATUS = v_status(indx)
			    				WHERE document_id = v_doc_id(indx);
			    				EXIT WHEN v_doc_id.COUNT=0;
			    		COMMIT;
					END LOOP;
					CLOSE LCU;
			END ;
			""";

	/** The SQL of {@code updateCPSinRAWfromNonMatchToMatchCCD}, as the former mapper had it (0 bound values). */
	static final String UPDATE_CPS_IN_RAW_FROM_NON_MATCH = """
			UPDATE raw_hl7_doc rhd
								SET rhd.current_process_status = 1
					WHERE EXISTS (SELECT 1
			              		FROM std_ccd_doc std
			             	WHERE std.mpi = rhd.pidmpi
			               	AND std.flux_match_cd IN ('M','C_M','P_M')
			            )
						AND rhd.current_process_status = 36
			""";

	/** The SQL of {@code updateCurrentProcessStatusInRawForMPIChange}, as the former mapper had it (0 bound values). */
	static final String UPDATE_CPS_IN_RAW_FOR_MPI_CHANGE = """
			Update RAW_HL7_DOC
				   set current_process_status = 41
				   where current_process_status = 37
				   and pidMpi in (select distinct old_mpi from mpi_change where exists (select 1 from raw_hl7_doc h where h.pidMPI=mpi_change.old_mpi)
								  and not exists (select 1 from std_ccd_doc s where s.MPI=mpi_change.old_mpi )
					  		      and exists (select 1 from std_ccd_doc s2 where s2.MPI=mpi_change.current_mpi and s2.matched_stateno is not null)
							 union
								  select distinct current_mpi from mpi_change where exists (select 1 from raw_hl7_doc h where h.pidMPI=mpi_change.current_mpi)
								  and not exists (select 1 from std_ccd_doc s where s.MPI=mpi_change.current_mpi)
								  and exists (select 1 from std_ccd_doc s2 where s2.MPI=mpi_change.old_mpi and s2.matched_stateno is not null))
			""";

	/** The SQL of {@code getStdHL7Doc}, as the former mapper had it (1 bound values). */
	static final String STD_HL7_DOC = """
			SELECT *
			                FROM (
			                SELECT  UPPER(trim(FIRST_VALUE (std.fname IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING))) fName,
			                        COUNT (DISTINCT std.fname) OVER (PARTITION BY std.ccd_match_doc_id) fname_count,
			                        UPPER(trim(FIRST_VALUE (std.mname IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING))) mName,
			                        COUNT (DISTINCT std.mname) OVER (PARTITION BY std.ccd_match_doc_id) mname_count,
			                        UPPER(trim(FIRST_VALUE (std.lname IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING))) lName,
			                        COUNT (DISTINCT std.lname) OVER (PARTITION BY std.ccd_match_doc_id) lname_count,
			                        CASE FIRST_VALUE (std.ethnicity IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING)
			                                                      WHEN '01' THEN 'HISPANIC'
			                                                      WHEN '02' THEN 'NOT HISPANIC'
			                                                      WHEN '09' THEN 'UNKNOWN' END as ethinicity,
			                        COUNT (DISTINCT std.ethnicity) OVER (PARTITION BY std.ccd_match_doc_id) ethnicity_count,
			                        FIRST_VALUE (std.mpi IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) mpi,
			                        COUNT (DISTINCT std.mpi) OVER (PARTITION BY std.ccd_match_doc_id) mpi_count,
			                        FIRST_VALUE (std.ssn IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) ssn,
			                        COUNT (DISTINCT std.ssn) OVER (PARTITION BY std.ccd_match_doc_id) ssn_count,
			                        TO_CHAR(FIRST_VALUE (std.date_of_birth IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING),'MM/DD/YYYY') dobText,
			                        COUNT (DISTINCT std.date_of_birth) OVER (PARTITION BY std.ccd_match_doc_id) dob_count,
			                        FIRST_VALUE (std.gender IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) gender,
			                        COUNT (DISTINCT std.gender) OVER (PARTITION BY std.ccd_match_doc_id) gender_count,
			                        FIRST_VALUE (std.MED_RECORD_NUMBER IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) medRecordNos,
			                        COUNT (DISTINCT std.MED_RECORD_NUMBER) OVER (PARTITION BY std.ccd_match_doc_id) mrn_count,
			                        FIRST_VALUE (PDC.STATENO IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) ccdMatchedStateNo,
			                        TO_CHAR(MIN(STD.DATE_RECEIVED) OVER (PARTITION BY std.ccd_match_doc_id),'MM/DD/YYYY') startDate,
			                        TO_CHAR(MAX(STD.DATE_RECEIVED) OVER (PARTITION BY std.ccd_match_doc_id),'MM/DD/YYYY') endDate
			                     FROM STD_HL7_DOC std
			                     LEFT OUTER JOIN PERSON_DOC_ASSOCIATION PDC on (STD.ccd_match_doc_id= PDC.DOCUMENT_ID and PDC.DISSASSOCIATION_DATE is null)
			                     where std.ccd_match_doc_id = ?1 AND STD.CURRENT_PROCESS_STATUS = 10
			                     )
			                     WHERE rownum < 2
			""";

	/** The SQL of {@code getHL7Diagnosis}, as the former mapper had it (3 bound values). */
	static final String HL7_DIAGNOSIS = """
			select  Distinct ADT.DX_CODE_ID dxCodeId,
			                     UPPER(trim(ADT.DX_CODE_TEXT)) dxCodeText,
			                     TO_DATE(substr(REPLACE(trim(ADT.DX_DATE_TEXT), '""', NULL),0,8),'YYYYMMDD') diagnosisDate,
			                     NVL(TO_CHAR(TO_DATE(substr(REPLACE(trim(ADT.DX_DATE_TEXT), '""', NULL),0,8),'YYYYMMDD'),'MM/DD/YYYY'),'') dxDateText,
			                     UPPER(trim(NVL(std.PROVIDER_FNAME,'')|| ' '|| NVL(std.PROVIDER_LNAME,''))) as providerName,
			                     UPPER(trim(STD.FACILITY_NAME)) facName
			              from raw_hl7_adt_dg1 ADT, STD_HL7_DOC STD
			              where STD.DOCUMENT_ID = ADT.HL7_DOC_ID
			              and STD.ccd_match_doc_id = ?1
			              AND STD.CURRENT_PROCESS_STATUS = 10
			              and STD.DATE_RECEIVED >= TO_DATE(?2, 'MM/DD/YYYY')
			  		       and STD.DATE_RECEIVED <= TO_DATE(?3, 'MM/DD/YYYY')
			              and ADT.DX_DATE_TEXT is not null
			           ORDER BY diagnosisDate
			""";

	/** The values of {@link #HL7_DIAGNOSIS}, in order. */
	static Object[] getHL7DiagnosisArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("docId"), map.get("startDate"), map.get("endDate")
		};
	}

	/** The SQL of {@code getHL7Medications}, as the former mapper had it (3 bound values). */
	static final String HL7_MEDICATIONS = """
			select distinct TO_DATE(substr(PHARM.DATETIME_OF_TRANSACTION,0,8),'YYYYMMDD') medicationDate,
							TO_CHAR(TO_DATE(substr(PHARM.DATETIME_OF_TRANSACTION,0,8),'YYYYMMDD'),'MM/DD/YYYY') dateTimeofTransaction,
			           					UPPER(trim(NVL(PHARM.ORDER_PROVIDER_FNAME,'')|| ' '|| NVL(PHARM.ORDER_PROVIDER_LNAME,''))) as orderProviderName,
			           					UPPER(trim(PHARM.ORDERING_FACILITY_NAME)) orderingFacilityName,
			           					UPPER(trim(nvl(PHARM.RXE_MED_NAME, PHARM.RXO_MED_NAME))) medicationName
			from raw_hl7_pharm PHARM, STD_HL7_DOC STD
			WHERE STD.DOCUMENT_ID = PHARM.HL7_DOC_ID
			and STD.CCD_MATCH_DOC_ID =  ?1
			AND STD.CURRENT_PROCESS_STATUS = 10
			 and STD.DATE_RECEIVED >= TO_DATE(?2, 'MM/DD/YYYY')
			  		    	 and STD.DATE_RECEIVED <= TO_DATE(?3, 'MM/DD/YYYY')
			and ( PHARM.DATETIME_OF_TRANSACTION is not null
			        		  or PHARM.ORDER_PROVIDER_FNAME is not null
			        		  or PHARM.ORDER_PROVIDER_LNAME is not null
			        		  or PHARM.ORDERING_FACILITY_NAME is not null
			        		  or PHARM.RXE_MED_NAME is not null
			        		  or PHARM.RXO_MED_NAME is not null) ORDER BY medicationDate
			""";

	/** The values of {@link #HL7_MEDICATIONS}, in order. */
	static Object[] getHL7MedicationsArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("docId"), map.get("startDate"), map.get("endDate")
		};
	}

	/** The SQL of {@code getHL7Encounter}, as the former mapper had it (3 bound values). */
	static final String HL7_ENCOUNTER = """
					select distinct STD.DATE_RECEIVED encounterDate,
							TO_CHAR((STD.DATE_RECEIVED),'MM/DD/YYYY') DATERECEIVED,
			                UPPER(trim(HET.EVENT_DESCRIPTION)) EVNEVENTTYPE,
			                UPPER(trim(STD.FACILITY_NAME)) FACILITYNAME,
			                RHD.PIDPATIENTACCOUNTNUM ENCOUNTERID,
			                UPPER(trim(NVL(STD.PROVIDER_FNAME,'')|| ' '|| NVL(STD.PROVIDER_LNAME,''))) as PROVIDERNAME
			from RAW_HL7_DOC RHD, STD_HL7_DOC STD, CODED_HL7_EVENT_TYPE HET
			WHERE STD.DOCUMENT_ID = RHD.DOCUMENT_ID
			AND RHD.evneventtype = HET.EVENT_TYPE
			AND STD.CCD_MATCH_DOC_ID =  ?1
			AND STD.CURRENT_PROCESS_STATUS = 10
					 and STD.DATE_RECEIVED >= TO_DATE(?2, 'MM/DD/YYYY')
			    		    	 and STD.DATE_RECEIVED <= TO_DATE(?3, 'MM/DD/YYYY')
					 and (STD.DATE_RECEIVED is not null
			                  or HET.EVENT_DESCRIPTION is not null
			                  or STD.FACILITY_NAME is not null
			                  or RHD.PIDPATIENTACCOUNTNUM is not null
			                  or STD.PROVIDER_FNAME is not null
			                  or STD.PROVIDER_LNAME is not null
			                  ) ORDER BY encounterDate
			""";

	/** The values of {@link #HL7_ENCOUNTER}, in order. */
	static Object[] getHL7EncounterArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("docId"), map.get("startDate"), map.get("endDate")
		};
	}

	/** The SQL of {@code getHL7LabTestList}, as the former mapper had it (3 bound values). */
	static final String HL7_LAB_TEST_LIST = """
				SELECT HL7_LAB_TEST_ID as hl7labTestId,
					   OBRCOLLECTIONDATE,
				   TO_CHAR(OBRCOLLECTIONDATE,'MM/DD/YYYY') as obrCollectionDateStr,
				   TO_CHAR(OBROBSERVATIONDATE,'MM/DD/YYYY') as obrObservationDateStr,
				   UPPER(trim(NVL(OBRPROVIDERFIRSTNAME,'')|| ' '|| NVL(OBRPROVIDERLASTNAME,''))) as obrProviderName,
				   COALESCE(OBRLOINCCODE, OBRTESTCODE, OBRALTTESTCODE) as obrLoincCode,
				   UPPER(trim(COALESCE(OBRLOINCDESC, OBRTESTDESC, OBRALTTESTDESC))) obrLoincDesc
			FROM RAW_HL7_LAB_TEST RHLT, STD_HL7_DOC STD
			WHERE STD.CCD_MATCH_DOC_ID = ?1
			AND STD.CURRENT_PROCESS_STATUS = 10
			   		    and STD.DATE_RECEIVED >= TO_DATE(?2, 'MM/DD/YYYY')
			   		    and STD.DATE_RECEIVED <= TO_DATE(?3, 'MM/DD/YYYY')
			AND RHLT.HL7_DOC_ID = STD.DOCUMENT_ID
			AND(OBRCOLLECTIONDATE is not null or OBROBSERVATIONDATE is not null or OBRPROVIDERFIRSTNAME is not null or OBRPROVIDERLASTNAME is not null)
			order by OBRCOLLECTIONDATE, obrLoincCode
			""";

	/** The values of {@link #HL7_LAB_TEST_LIST}, in order. */
	static Object[] getHL7LabTestListArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("docId"), map.get("startDate"), map.get("endDate")
		};
	}

	/** The SQL of {@code getHL7LabResultList}, as the former mapper had it (1 bound values). */
	static final String HL7_LAB_RESULT_LIST = """
				SELECT HL7_LAB_TEST_ID as hl7labTestId,
				   UPPER(trim(COALESCE(obxloinccode, obxtestcode, obxalttestcode))) as obxLoincCode,
				   UPPER(trim(NVL(OBXOBSERVATIONRESULTTEXT,''))) ||' '|| UPPER(trim(NVL(OBXUNITOFMEASURE,''))) as obxObservationResultText,
				   UPPER(trim(COALESCE(obxloincdesc, obxtestdesc, obxalttestdesc))) obxLoincDesc
			FROM RAW_HL7_LAB_RESULT
			WHERE HL7_LAB_TEST_ID = ?1
			""";

	/** The SQL of {@code getHL7RaceVO}, as the former mapper had it (1 bound values). */
	static final String HL7_RACE = """
			WITH data AS
			        (
			        select Distinct ccd_match_doc_id,
			                CASE WHEN SUM(RACE_ASIAN) OVER (PARTITION BY ccd_match_doc_id) > 0 THEN 1 ELSE NULL END raceAsian,
			                CASE WHEN SUM(RACE_BLACK) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceBlack,
			                CASE WHEN SUM(RACE_INDIAN) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceIndian,
			                CASE WHEN SUM(RACE_OTHER) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceOther,
			                CASE WHEN SUM(RACE_PACIFIC) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END racePacific,
			                CASE WHEN SUM(RACE_WHITE) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceWhite,
			                CASE WHEN SUM(RACE_UNKNOWN) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceUnknown,
			                RACE_OTHER_TEXT raceOtherText
			        from std_hl7_doc
			        where ccd_match_doc_id = ?1
			        AND CURRENT_PROCESS_STATUS = 10
			        )
			        SELECT Distinct ccd_match_doc_id,
			                        raceAsian,
			                        raceBlack,
			                        raceIndian,
			                        raceOther,
			                        racePacific,
			                        raceWhite,
			                        raceUnknown,
			                        LISTAGG (raceOtherText, ', ') WITHIN GROUP (ORDER BY ccd_match_doc_id) raceOtherText
			        FROM data
			        GROUP BY ccd_match_doc_id,
			                raceAsian,
			                raceBlack,
			                raceIndian,
			                raceOther,
			                racePacific,
			                raceWhite,
			                raceUnknown
			""";

	/** The SQL of {@code getFilteredHL7Doc}, as the former mapper had it (3 bound values). */
	static final String FILTERED_HL7_DOC = """
			SELECT *
			                FROM (
			                SELECT  UPPER(trim(FIRST_VALUE (std.fname IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING))) fName,
			                        COUNT (DISTINCT std.fname) OVER (PARTITION BY std.ccd_match_doc_id) fname_count,
			                        UPPER(trim(FIRST_VALUE (std.mname IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING))) mName,
			                        COUNT (DISTINCT std.mname) OVER (PARTITION BY std.ccd_match_doc_id) mname_count,
			                        UPPER(trim(FIRST_VALUE (std.lname IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING))) lName,
			                        COUNT (DISTINCT std.lname) OVER (PARTITION BY std.ccd_match_doc_id) lname_count,
			                        CASE FIRST_VALUE (std.ethnicity IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING)
			                                                      WHEN '01' THEN 'HISPANIC'
			                                                      WHEN '02' THEN 'NOT HISPANIC'
			                                                      WHEN '09' THEN 'UNKNOWN' END as ethinicity,
			                        COUNT (DISTINCT std.ethnicity) OVER (PARTITION BY std.ccd_match_doc_id) ethnicity_count,
			                        FIRST_VALUE (std.mpi IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) mpi,
			                        COUNT (DISTINCT std.mpi) OVER (PARTITION BY std.ccd_match_doc_id) mpi_count,
			                        FIRST_VALUE (std.ssn IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) ssn,
			                        COUNT (DISTINCT std.ssn) OVER (PARTITION BY std.ccd_match_doc_id) ssn_count,
			                        TO_CHAR(FIRST_VALUE (std.date_of_birth IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING),'MM/DD/YYYY') dobText,
			                        COUNT (DISTINCT std.date_of_birth) OVER (PARTITION BY std.ccd_match_doc_id) dob_count,
			                        FIRST_VALUE (std.gender IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) gender,
			                        COUNT (DISTINCT std.gender) OVER (PARTITION BY std.ccd_match_doc_id) gender_count,
			                        FIRST_VALUE (std.MED_RECORD_NUMBER IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) medRecordNos,
			                        COUNT (DISTINCT std.MED_RECORD_NUMBER) OVER (PARTITION BY std.ccd_match_doc_id) mrn_count,
			                        FIRST_VALUE (PDC.STATENO IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id
			                                                                              ORDER BY std.DATE_RECEIVED DESC
			                                                                              RANGE BETWEEN UNBOUNDED PRECEDING
			                                                                              AND UNBOUNDED FOLLOWING) ccdMatchedStateNo,
			                        TO_CHAR(MIN(STD.DATE_RECEIVED) OVER (PARTITION BY std.ccd_match_doc_id),'MM/DD/YYYY') startDate,
			                        TO_CHAR(MAX(STD.DATE_RECEIVED) OVER (PARTITION BY std.ccd_match_doc_id),'MM/DD/YYYY') endDate
			                     FROM STD_HL7_DOC std--, PERSON_DOC_ASSOCIATION PDC
			                     LEFT OUTER JOIN PERSON_DOC_ASSOCIATION PDC on (STD.ccd_match_doc_id= PDC.DOCUMENT_ID and PDC.DISSASSOCIATION_DATE is null)
			                     WHERE std.ccd_match_doc_id = ?1
			                     	   AND STD.CURRENT_PROCESS_STATUS = 10
			                           and STD.DATE_RECEIVED >= TO_DATE(?2, 'MM/DD/YYYY')
			                           and STD.DATE_RECEIVED <= TO_DATE(?3, 'MM/DD/YYYY')
			                     )
			                     WHERE rownum < 2
			""";

	/** The values of {@link #FILTERED_HL7_DOC}, in order. */
	static Object[] getFilteredHL7DocArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("docId"), map.get("startDate"), map.get("endDate")
		};
	}

	/** The SQL of {@code getFilteredRaceVO}, as the former mapper had it (3 bound values). */
	static final String FILTERED_RACE = """
			WITH data AS
			        (
			        select Distinct ccd_match_doc_id,
			                CASE WHEN SUM(RACE_ASIAN) OVER (PARTITION BY ccd_match_doc_id) > 0 THEN 1 ELSE NULL END raceAsian,
			                CASE WHEN SUM(RACE_BLACK) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceBlack,
			                CASE WHEN SUM(RACE_INDIAN) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceIndian,
			                CASE WHEN SUM(RACE_OTHER) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceOther,
			                CASE WHEN SUM(RACE_PACIFIC) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END racePacific,
			                CASE WHEN SUM(RACE_WHITE) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceWhite,
			                CASE WHEN SUM(RACE_UNKNOWN) OVER (PARTITION BY ccd_match_stateno) > 0 THEN 1 ELSE NULL END raceUnknown,
			                RACE_OTHER_TEXT raceOtherText
			        from std_hl7_doc STD
			        where STD.ccd_match_doc_id = ?1
			        AND STD.CURRENT_PROCESS_STATUS = 10
			        and STD.DATE_RECEIVED >= TO_DATE(?2, 'MM/DD/YYYY')
			   		and STD.DATE_RECEIVED <= TO_DATE(?3, 'MM/DD/YYYY')
			        )
			        SELECT Distinct ccd_match_doc_id,
			                        raceAsian,
			                        raceBlack,
			                        raceIndian,
			                        raceOther,
			                        racePacific,
			                        raceWhite,
			                        raceUnknown,
			                        LISTAGG (raceOtherText, ', ') WITHIN GROUP (ORDER BY ccd_match_doc_id) raceOtherText
			        FROM data
			        GROUP BY ccd_match_doc_id,
			                raceAsian,
			                raceBlack,
			                raceIndian,
			                raceOther,
			                racePacific,
			                raceWhite,
			                raceUnknown
			""";

	/** The values of {@link #FILTERED_RACE}, in order. */
	static Object[] getFilteredRaceVOArgs(Map<?, ?> map) {
		return new Object[] {
				map.get("docId"), map.get("startDate"), map.get("endDate")
		};
	}

	/** The SQL of {@code insertMPIPairsCheckingHL7andCCD}, as the former mapper had it (0 bound values). */
	static final String INSERT_MPI_PAIRS = """
			INSERT INTO  mpi_change(CURRENT_MPI, OLD_MPI, DATE_RECEIVED)
			select Distinct h.mpi as CURRENT_MPI, c.mpi as OLD_MPI, sysdate
			 from std_hl7_doc h, std_ccd_doc c where h.current_process_status in (5,60) and
					((replace(h.fname,' ',null)=replace(c.fname,' ',null) and replace(h.lname,' ',null)=replace(c.lname,' ',null)) or
			        (replace(h.lname,' ',null)=replace(c.fname,' ',null) and replace(h.fname,' ',null)=replace(c.lname,' ',null)))
			       and h.dob_text=c.dob_text and h.mpi != c.mpi and c.CURRENT_PROCESS_STATUS IN (10, 14)
			       and not exists (select 1 from mpi_change m where m.old_mpi||m.current_mpi = h.mpi||c.mpi)
			       and not exists (select 1 from mpi_change m where m.current_mpi||m.old_mpi = h.mpi||c.mpi)
			""";

	/** The SQL of {@code getTotalCountForEachCPS}, as the former mapper had it (2 bound values). */
	static final String TOTAL_COUNT_FOR_EACH_CPS = """
			WITH data AS
			(
			SELECT Trim(regexp_substr(?1, '[^,]+', 1, level)) process_status
			FROM dual
			CONNECT BY regexp_substr(?2, '[^,]+', 1, level) IS NOT NULL
			)
			SELECT b.process_status, COUNT(a.document_id) count
			FROM std_hl7_doc a, data b
			WHERE a.current_process_status(+) = b.process_status
			GROUP BY process_status
			""";

	/** The values of {@link #TOTAL_COUNT_FOR_EACH_CPS}, in order. */
	static Object[] getTotalCountForEachCPSArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("p_process_status")), text(map.get("p_process_status"))
		};
	}

	/** The SQL of {@code updateCPSinSTDNotMatchOnParameters}, as the former mapper had it (0 bound values). */
	static final String UPDATE_CPS_IN_STD_NOT_MATCH = """
			UPDATE STD_HL7_DOC SET CURRENT_PROCESS_STATUS = 60 WHERE CURRENT_PROCESS_STATUS = 5
			""";

	/** The SQL of {@code deleteDuplicatesRowsInRawHL7Insuarance}, as the former mapper had it (0 bound values). */
	static final String DELETE_DUPLICATE_RAW_HL7_INSURANCE = """
			DELETE FROM raw_hl7_insurance
			 WHERE rowid NOT IN (
					SELECT Min(rowid) row_id FROM raw_hl7_insurance GROUP BY plan_id, company_id, company_name, company_address1, company_address2, company_city, company_state, company_zipcode, group_number,
					plan_type, insured_fname, insured_lname, insured_dob_text, insured_address1, insured_address2, insured_city, policy_number, ssn,
					medicaid_fname, medicaid_lname, medicaid_case_number, insured_phone_number, plan_effective_date, plan_expiration_date
					)
			""";

	/** The SQL of {@code updateCPSAndDOCidinRAWHL7fromSTDHL7DOC}, as the former mapper had it (0 bound values). */
	static final String UPDATE_CPS_AND_DOC_ID_IN_RAW_HL7 = """
			UPDATE raw_hl7_doc r SET
			r.current_process_status = 18,
			r.ccd_match_doc_id = (SELECT s.ccd_match_doc_id FROM std_hl7_doc s WHERE r.document_id = s.document_id and s.current_process_status = 14)
			WHERE r.document_id IN (SELECT s.document_id FROM std_hl7_doc s WHERE r.document_id = s.document_id and s.current_process_status = 14)
			""";

	/** The SQL of {@code deleteRowsFromSTDHL7AfterBackedUpToRawHL7}, as the former mapper had it (0 bound values). */
	static final String DELETE_STD_HL7_BACKED_UP = """
			delete from std_hl7_doc where current_process_status = 14
			""";
}
