package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.text;

import java.util.Map;

import org.hibernate.type.StandardBasicTypes;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsStdDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;

/**
 * The SQL of the {@link SchedulerJobsDAO} statements that stay native, taken unchanged from the former MyBatis mapper
 * (shared fragments expanded, {@code #{...}} replaced by {@code ?1, ?2, ...}); why each one stays native is said at
 * its DAO method. {@code ${...}} names are left in the text: the DAO replaces them with allow-listed table and column
 * names. The {@code ...Args} methods give the values bound to a statement, in order, typed as the mapper's
 * {@code jdbcType} bound them (a null text as VARCHAR, a null number as NUMERIC, a {@code java.util.Date} with
 * {@code jdbcType=DATE} as its day only, as MyBatis' DateOnlyTypeHandler bound it); a value without a jdbcType is bound
 * as it is.
 */
final class SchedulerJobsSql {

	private SchedulerJobsSql() {
	}

	/**
	 * A value bound as {@code #{x,jdbcType=DATE}}: a {@code java.util.Date} (the class itself) as its day only (MyBatis'
	 * DateOnlyTypeHandler, setDate), any other value as it is (a Timestamp kept its time), null as a DATE null.
	 */
	static Param date(Object value) {
		if (value != null && value.getClass() == java.util.Date.class) {
			return NativeStatements.param(new java.sql.Date(((java.util.Date) value).getTime()), StandardBasicTypes.DATE);
		}
		return NativeStatements.param(value, StandardBasicTypes.DATE);
	}

	/** A value bound as {@code #{x,jdbcType=TIMESTAMP}}. */
	static Param timestamp(Object value) {
		return NativeStatements.timestamp(value);
	}

	/** The columns the former nested result map of {@code getMultiStepDocuments} mapped onto each document (no other column was mapped). */
	static final java.util.List<String> MULTI_STEP_DOCUMENT_COLUMNS = java.util.List.of(
			"DOCUMENT_ID", "RANKING", "MSHFILECONTENTTYPE", "MSHDELETEDIND", "PIDSEXCODE", "PIDRACECODE",
			"PIDRACEASIAN", "PIDRACEBLACK", "PIDRACENATIVEAMERALASKAN", "PIDRACEOTHER", "PIDRACEPACIFIC",
			"PIDRACEUNKNOWN", "PIDRACEWHITE", "PIDMARITALSTATUSCODE", "PIDDEATHIND", "PIDUSBORNIND", "PIDDELETEDIND",
			"PIDVACCINETRIALIND", "PIDSMSA", "PIDNONHUMANIND", "OBRRESULTSTATUSCODE", "OBRDELETEDIND",
			"OBXRESULTSTATUSCODE", "OBXLEADPURPOSE", "OBXDELETEDIND", "OBXPROCESSEDIND", "PIDNOTE",
			"OBRRELEVANTCLINICALINFO", "OBRNOTE", "OBXOBSERVATIONRESULTTEXT", "OBXNOTE", "PIDLONGITUDE", "PIDLATITUDE",
			"CURRENT_PROCESS_STATUS", "INTERPRETATION_STD_DOC_ID", "HASH_UNIQUE_NUMBER", "MSHMESSAGEMASTERKEY",
			"PIDPATIENTKEY", "PIDMESSAGEMASTERKEY", "OBROBSERVATIONREQUESTKEY", "ZLROBSERVATIONREQUESTKEY",
			"OBRPATIENTKEY", "OBRPATIENTKEYORIG", "OBRINITRESKEY", "OBXOBSERVATIONRESULTKEY",
			"OBXOBSERVATIONREQUESTKEY", "OBXOLDESTDUPLICATERESULTKEY", "PIDCOUNTYCODE", "OBXCOUNTYCODE", "OBXDISEASE",
			"OBXDISEASEGROUP", "MSHMESSAGEDATE", "MSHCREATEDATE", "MSHDELETEDATE", "PIDCREATEDATE", "PIDDATEOFBIRTH",
			"PIDDEATHDATE", "PIDUPDATEDATE", "PIDDELETEDATE", "OBRCREATEDATE", "OBRCOLLECTIONDATE",
			"OBRRESULTREPORTDATE", "OBRDELETEDATE", "OBROBSERVATIONDATE", "OBRRECEIVEDDATE", "OBXOBSERVATIONDATE",
			"OBXPROCESSEDDATE", "OBXDELETEDATE", "PROGRAMCODEUPDATEDATE", "OBXPROGRAMCODEUPDATEDATE", "OBXCREATEDATE",
			"OBXANALYSISDATE", "MSHSENDINGFACILITYNAME", "MSHSENDINGFACILITYCLIA", "MSHMESSAGEDATERAW",
			"MSHMESSAGECONTROLID", "MSHPROCESSINGID", "MSHVERSIONID", "MSHLABPFI", "MSHFILENAME", "MSHSOURCETYPE",
			"PIDSETID", "PIDPATIENTIDEXTERNAL", "PIDFACILITYIDEXTERNAL", "PIDPATIENTIDINTERNAL",
			"PIDFACILITYIDINTERNAL", "PIDPATIENTIDALT", "PIDFACILITYIDALT", "PIDLASTNAME", "PIDFIRSTNAME",
			"PIDMIDDLENAME", "PIDNAMESUFFIX", "PIDDATEOFBIRTHRAW", "PIDRACECODERAW", "PIDADDRESSLINE1",
			"PIDADDRESSLINE2", "PIDCITY", "PIDSTATE", "PIDZIPCODE", "PIDCOUNTRYCODE", "PIDCOUNTYCODEORIGINAL",
			"PIDHOMEPHONE", "PIDWORKPHONE", "PIDPATIENTLANGUAGE", "PIDPATIENTACCOUNTNUM", "PIDSSN", "PIDMOTHERSID",
			"PIDETHNICGROUPCODE", "PIDDEATHDATERAW", "PIDCOUNTYGAZCODEORIG", "PIDMEDICAIDNUMBER", "PIDDIN",
			"PIDCOUNTRYOFORIGIN", "PIDLENGTHOFRESIDENCY", "PIDTYPEOFINSURANCE", "PIDUPDATEID", "ZLRAGE",
			"ZLRNEXTOFKINLASTNAME", "ZLRNEXTOFKINFIRSTNAME", "ZLRNEXTOFKINMIDDLENAME", "ZLRNEXTOFKINNAMESUFFIX",
			"ZLRNEXTOFKINRELATIONSHIP", "ZLRNEXTOFKINADDRESSLINE1", "ZLRNEXTOFKINADDRESSLINE2", "ZLRNEXTOFKINCITY",
			"ZLRNEXTOFKINSTATE", "ZLRNEXTOFKINZIPCODE", "ZLRNEXTOFKINCOUNTRY", "ZLRNEXTOFKINPHONE",
			"PIDMEDICALRECORDNUMBER", "PIDASSIGNAUTHOID", "PIDASSIGNAUTHDESC", "PIDNYSID", "PIDEMAIL", "PIDCELLPHONE",
			"OBRSETID", "OBRPLACERSORDERNUM", "OBRACCESSIONNUM", "OBRSNOMEDCODE", "OBRLOINCCODE", "OBRLOCALCODE",
			"OBRANALYSISDESC", "OBRCOLLECTIONDATERAW", "OBRSPECIMENSOURCECODE", "OBRSPECIMENSOURCENAME",
			"OBRPROVIDERID", "OBRPROVIDERLASTNAME", "OBRPROVIDERFIRSTNAME", "OBRPROVIDERMIDDLENAME",
			"OBRPROVIDERNAMESUFFIX", "OBRCALLBACKPHONE", "OBRCALLBACKPHONEALT", "OBRRESULTREPORTDATERAW",
			"OBRPATHOLOGISTID", "OBRPATHOLOGISTLASTNAME", "OBRPATHOLOGISTFIRSTNAME", "OBRPATHOLOGISTMIDDLENAME",
			"OBRPATHOLOGISTNAMESUFFIX", "OBRPROVIDERIDTYPECODE", "OBRINITRESLOINCCODE", "OBRINITRESLOINCDESC",
			"OBRINITRESLOCALTESTCODE", "OBRINITRESLOCALTESTDESC", "OBRINITRESSUBID", "OBRINITRESOBSERVATIONTEXT",
			"OBRINITRESPLACERSORDERNUM", "OBRINITRESACCESSIONNUM", "OBRFILLERSORDERNUM", "OBROBSERVATIONDATERAW",
			"OBRRECEIVEDDATERAW", "ZLRPROVIDERADDRESSLINE1", "ZLRPROVIDERADDRESSLINE2", "ZLRPROVIDERCITY",
			"ZLRPROVIDERSTATE", "ZLRPROVIDERZIPCODE", "ZLRPROVIDERCOUNTRY", "ZLRFACILITYCODE", "ZLRFACILITYNAME",
			"ZLRFACILITYADDRESSLINE1", "ZLRFACILITYADDRESSLINE2", "ZLRFACILITYCITY", "ZLRFACILITYSTATE",
			"ZLRFACILITYZIPCODE", "ZLRFACILITYCOUNTRY", "ZLRFACILITYPHONE", "OBRPROVIDERIDALT1",
			"OBRPROVIDERIDTYPECODEALT1", "OBRPROVIDERIDALT2", "OBRPROVIDERIDTYPECODEALT2", "OBRPROVIDERFAX",
			"OBRPROVIDEREMAIL", "OBXSETID", "OBXVALUETYPE", "OBXLOINCCODE", "OBXLOINCDESC", "OBXLOINCDESCSHORT",
			"OBXOBSERVATIONSUBID", "OBXSNOMEDCODE", "OBXSNOMEDDESC", "OBXOBSERVATIONRESULTTEXTSHORT",
			"OBXUNITOFMEASURE", "OBXREFERENCERANGE", "OBXABNORMALFLAG", "OBXOBSERVATIONDATERAW", "OBXPRODUCERCLIAID",
			"OBXPRODUCERLABNAME", "OBXOBSERVATIONMETHOD", "OBXPROGRAMCODE", "OBXLOGONID", "PROGRAMCODEUPDATEID",
			"OBXPROGRAMCODEUPDATEID", "OBXLOCALTESTCODE", "OBXLOCALTESTDESC", "OBXLOCALRESULTCODE",
			"OBXLOCALRESULTDESC", "OBXSENDINGFACILITYCLIA", "OBXANALYSISDATERAW", "OBXICD_CODETYPE", "OBXICD_DESC",
			"OBXICD_CODE");

	/** The SQL of {@code getRawHIVDocsList}, as the former mapper had it. */
	static final String GET_RAW_HIVDOCS_LIST = """
			select  * from RAW_HIV_ECLRS_DOC where CURRENT_PROCESS_STATUS in (1,6,3,15)""";

	/** The SQL of {@code getRawStdDocsList}, as the former mapper had it. */
	static final String GET_RAW_STD_DOCS_LIST = """
			select * from RAW_STD_ECLRS_DOC where CURRENT_PROCESS_STATUS in (1,3)""";

	/** The SQL of {@code getRawDocsList}, as the former mapper had it. */
	static final String GET_RAW_DOCS_LIST = """
			select * from ${rawTableName} where CURRENT_PROCESS_STATUS in (1,6,15)""";

	/** The SQL of {@code getDocumentDuplicatedStatus}, as the former mapper had it. */
	static final String GET_DOCUMENT_DUPLICATED_STATUS = """
			select OBXLOINCCODE, MSHFILENAME, total_by_filename, sum_current from (
			SELECT OBXLOINCCODE,MSHFILENAME , SUM(total_count) OVER ( PARTITION BY MSHFILENAME ORDER BY MSHFILENAME) total_by_filename,sum_current
			FROM (
			SELECT lb.OBXLOINCCODE,lb.MSHFILENAME,
			COUNT(*)total_count ,SUM(decode(lb.CURRENT_PROCESS_STATUS,2,1,0)) sum_current
			FROM RAW_HIV_ECLRS_DOC  lb
			WHERE process_date >= trunc(SYSDATE)
			GROUP BY  lb.OBXLOINCCODE,lb.MSHFILENAME))  where sum_current > 0 order by MSHFILENAME""";

	/** The SQL of {@code loadClericalReviewRecordsForQSProcess}, as the former mapper had it. */
	static final String LOAD_CLERICAL_REVIEW_RECORDS_FOR_QSPROCESS = """
			update ${tableName} set CURRENT_PROCESS_STATUS  = 5 where MATCH_CD in ('C', 'P')""";

	/** The SQL of {@code updateTheRawRecordToStandardize}, as the former mapper had it. */
	static final String UPDATE_THE_RAW_RECORD_TO_STANDARDIZE = """
			update ${rawTableName} set CURRENT_PROCESS_STATUS  = 5, PROCESS_DATE = SYSDATE where DOCUMENT_ID = ?1""";

	/** The values bound to {@link #UPDATE_THE_RAW_RECORD_TO_STANDARDIZE}, in order. */
	static Object[] updateTheRawRecordToStandardizeArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("documentId")
		};
	}

	/** The SQL of {@code getDateValueFromString}, as the former mapper had it. */
	static final String GET_DATE_VALUE_FROM_STRING = """
			select FN_TO_DATE(?1,'MM/DD/YYYY') from dual""";

	/** The values bound to {@link #GET_DATE_VALUE_FROM_STRING}, in order. */
	static Object[] getDateValueFromStringArgs(String p) {
		return new Object[] {
			p
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToHIVStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_HIVSTANDARD_TABLE = """
			update STD_HIV_ECLRS_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_PLUS4 = ?5,PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,PERSON_LONGITUDE = ?8,PERSON_CENSUS_TRACT = ?9,PROV_ADDRESS = ?10,PROV_CITY = ?11,PROV_STATE = ?12,
			PROV_ZIP = ?13,PROV_PLUS4 = ?14,PROV_COUNTY = ?15,PROV_LATITUDE = ?16,PROV_LONGITUDE = ?17,PROV_CENSUS_TRACT =?18,
			FACILITY_ADDRESS = ?19,FACILITY_CITY = ?20,FACILITY_STATE = ?21,FACILITY_ZIP = ?22,FACILITY_PLUS4 = ?23,FACILITY_COUNTY = ?24,
			FACILITY_CENSUS_TRACT = ?25, FACILITY_LATITUDE = ?26,FACILITY_LONGITUDE = ?27, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?28""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_HIVSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToHIVStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerPlus4"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityPlus4"),
			p.get("facilityCounty"),
			p.get("facilityCensusTract"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToTBStdTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_TBSTD_TABLE = """
			update STD_TB_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_PLUS4 = ?5,PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,PERSON_LONGITUDE = ?8,PERSON_CENSUS_TRACT = ?9,PROV_ADDRESS = ?10,PROV_CITY = ?11,PROV_STATE = ?12,
			PROV_ZIP = ?13,PROV_PLUS4 = ?14,PROV_COUNTY = ?15,PROV_LATITUDE = ?16,PROV_LONGITUDE = ?17,PROV_CENSUS_TRACT =?18,
			FACILITY_ADDRESS = ?19,FACILITY_CITY = ?20,FACILITY_STATE = ?21,FACILITY_ZIP = ?22,FACILITY_PLUS4 = ?23,FACILITY_COUNTY = ?24,
			FACILITY_CENSUS_TRACT = ?25, FACILITY_LATITUDE = ?26,FACILITY_LONGITUDE = ?27, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?28""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_TBSTD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToTBStdTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerPlus4"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityPlus4"),
			p.get("facilityCounty"),
			p.get("facilityCensusTract"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToSYPHStdTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_SYPHSTD_TABLE = """
			update STD_SYPHILIS_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_COUNTY = ?5,
			PERSON_LATITUDE = ?6,PERSON_LONGITUDE = ?7,PERSON_CENSUS_TRACT = ?8,PROV_ADDRESS = ?9,PROV_CITY = ?10,PROV_STATE = ?11,
			PROV_ZIP = ?12,PROV_COUNTY = ?13,PROV_LATITUDE = ?14,PROV_LONGITUDE = ?15,PROV_CENSUS_TRACT =?16, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?17""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_SYPHSTD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToSYPHStdTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToCOPHIStdTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_COPHISTD_TABLE = """
			update RAW_COPHI_MATCH set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_COUNTY = ?5,
			PERSON_LATITUDE = ?6,PERSON_LONGITUDE = ?7,PERSON_CENSUS_TRACT = ?8, MATCHED_PROCESS_DATE  = SYSDATE where ID =	?9""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_COPHISTD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToCOPHIStdTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToSSDMStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_SSDMSTANDARD_TABLE = """
			update STD_SSDM_DOC set MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?1""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_SSDMSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToSSDMStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("documentId")
		};
	}

	/** The SQL of {@code getFlagChoseProviderForADAP}, as the former mapper had it. */
	static final String GET_FLAG_CHOSE_PROVIDER_FOR_ADAP = """
			select FLAG_CHOSEN_PROVIDER from STD_ADAP_DOC where DOCUMENT_ID =	?1""";

	/** The values bound to {@link #GET_FLAG_CHOSE_PROVIDER_FOR_ADAP}, in order. */
	static Object[] getFlagChoseProviderForADAPArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("documentId")
		};
	}

	/** The SQL of {@code getUnderCustodyForDinDoccs}, as the former mapper had it. */
	static final String GET_UNDER_CUSTODY_FOR_DIN_DOCCS = """
			select UNDER_CUSTODY from STD_DIN_DOCCS_DOC where DOCUMENT_ID =	?1""";

	/** The values bound to {@link #GET_UNDER_CUSTODY_FOR_DIN_DOCCS}, in order. */
	static Object[] getUnderCustodyForDinDoccsArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToADAPStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_ADAPSTANDARD_TABLE = """
			update STD_ADAP_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_PLUS4 = ?5,PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,PERSON_LONGITUDE = ?8,PERSON_CENSUS_TRACT = ?9,
			${PROV_ADDRESS} = ?10,${PROV_CITY} = ?11,${PROV_STATE} = ?12,
			${PROV_ZIP} = ?13,${PROV_PLUS4} = ?14,${PROV_COUNTY} = ?15,${PROV_LATITUDE} = ?16,${PROV_LONGITUDE} = ?17,${PROV_CENSUS_TRACT} =?18, MATCHED_PROCESS_DATE  = SYSDATE
			where DOCUMENT_ID =	?19""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_ADAPSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToADAPStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerPlus4"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToDinDoccsStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_DIN_DOCCS_STANDARD_TABLE = """
			update STD_DIN_DOCCS_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_PLUS4 = ?5,PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,PERSON_LONGITUDE = ?8,PERSON_CENSUS_TRACT = ?9,
			${FACILITY_ADDRESS} = ?10,${FACILITY_CITY} = ?11,${FACILITY_STATE} = ?12,
			${FACILITY_ZIP} = ?13,${FACILITY_PLUS4} = ?14,${FACILITY_COUNTY} = ?15,${FACILITY_LATITUDE} = ?16,${FACILITY_LONGITUDE} = ?17,${FACILITY_CENSUS_TRACT} =?18, MATCHED_PROCESS_DATE  = SYSDATE
			where DOCUMENT_ID =	?19""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_DIN_DOCCS_STANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToDinDoccsStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityPlus4"),
			p.get("facilityCounty"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("facilityCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToCCDStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_CCDSTANDARD_TABLE = """
			update STD_CCD_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_PLUS4 = ?5,PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,PERSON_LONGITUDE = ?8,PERSON_CENSUS_TRACT = ?9, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?10""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_CCDSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToCCDStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToAIRSStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_AIRSSTANDARD_TABLE = """
			update STD_AIRS_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_PLUS4 = ?5,PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,PERSON_LONGITUDE = ?8,PERSON_CENSUS_TRACT = ?9,FACILITY_ADDRESS = ?10,FACILITY_CITY = ?11,FACILITY_STATE = ?12,FACILITY_ZIP = ?13,FACILITY_PLUS4 = ?14,FACILITY_COUNTY = ?15,
			FACILITY_CENSUS_TRACT = ?16, FACILITY_LATITUDE = ?17,FACILITY_LONGITUDE = ?18, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?19""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_AIRSSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToAIRSStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityPlus4"),
			p.get("facilityCounty"),
			p.get("facilityCensusTract"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToDEATHStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_DEATHSTANDARD_TABLE = """
			update STD_DEATH_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_PLUS4 = ?5,PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,PERSON_LONGITUDE = ?8,PERSON_CENSUS_TRACT = ?9,FACILITY_ADDRESS = ?10,FACILITY_CITY = ?11,FACILITY_STATE = ?12,FACILITY_ZIP = ?13,FACILITY_PLUS4 = ?14,FACILITY_COUNTY = ?15,
			FACILITY_CENSUS_TRACT = ?16, FACILITY_LATITUDE = ?17,FACILITY_LONGITUDE = ?18, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?19""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_DEATHSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToDEATHStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityPlus4"),
			p.get("facilityCounty"),
			p.get("facilityCensusTract"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToEPRFOOCStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_EPRFOOCSTANDARD_TABLE = """
			update STD_EPRF_OOC_UPLOAD set PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4, PERSON_COUNTY = ?5,
			PERSON_LATITUDE = ?6,PERSON_LONGITUDE = ?7,PERSON_CENSUS_TRACT = ?8,FACILITY_ADDRESS = ?9,FACILITY_CITY = ?10,FACILITY_STATE = ?11,FACILITY_ZIP = ?12,FACILITY_COUNTY = ?13,
			FACILITY_CENSUS_TRACT = ?14, FACILITY_LATITUDE = ?15,FACILITY_LONGITUDE = ?16,
			PROV_ADDRESS = ?17,PROV_CITY = ?18,PROV_STATE = ?19,
			PROV_ZIP = ?20,PROV_COUNTY = ?21,PROV_LATITUDE = ?22,PROV_LONGITUDE = ?23,PROV_CENSUS_TRACT =?24, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?25""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_EPRFOOCSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToEPRFOOCStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityCounty"),
			p.get("facilityCensusTract"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToNycDEATHStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_NYC_DEATHSTANDARD_TABLE = """
			update STD_NYC_DEATH_DOC
			set
			PERSON_ADDRESS = ?1,
			PERSON_CITY = ?2,
			PERSON_STATE = ?3,
			PERSON_ZIP = ?4,
			PERSON_PLUS4 = ?5,
			PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,
			PERSON_LONGITUDE = ?8,
			PERSON_CENSUS_TRACT = ?9,
			FACILITY_ADDRESS = ?10,
			FACILITY_CITY = ?11,
			FACILITY_STATE = ?12,
			FACILITY_ZIP = ?13,
			FACILITY_PLUS4 = ?14,
			FACILITY_COUNTY = ?15,
			FACILITY_CENSUS_TRACT = ?16,
			FACILITY_LATITUDE = ?17,
			FACILITY_LONGITUDE = ?18, MATCHED_PROCESS_DATE  = SYSDATE
			where DOCUMENT_ID =  ?19""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_NYC_DEATHSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToNycDEATHStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityPlus4"),
			p.get("facilityCounty"),
			p.get("facilityCensusTract"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToCASAStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_CASASTANDARD_TABLE = """
			update STD_CASA_DOC set  PERSON_ZIP = ?1, PERSON_COUNTY = ?2, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?3""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_CASASTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToCASAStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personZip"),
			p.get("personCounty"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToPRFStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_PRFSTANDARD_TABLE = """
			update STD_PRF_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_PLUS4 = ?5,PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,PERSON_LONGITUDE = ?8,PERSON_CENSUS_TRACT = ?9,FACILITY_ADDRESS = ?10,FACILITY_CITY = ?11,FACILITY_STATE = ?12,FACILITY_ZIP = ?13,FACILITY_PLUS4 = ?14,FACILITY_COUNTY = ?15,
			FACILITY_CENSUS_TRACT = ?16, FACILITY_LATITUDE = ?17,FACILITY_LONGITUDE = ?18, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?19""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_PRFSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToPRFStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityPlus4"),
			p.get("facilityCounty"),
			p.get("facilityCensusTract"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToNYCPRFStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_NYCPRFSTANDARD_TABLE = """
			update STD_NYCPRF_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_COUNTY = ?5,
			PERSON_LATITUDE = ?6,PERSON_LONGITUDE = ?7,PERSON_CENSUS_TRACT = ?8,PROV_ADDRESS = ?9,PROV_CITY = ?10,PROV_STATE = ?11,
			PROV_ZIP = ?12,PROV_COUNTY = ?13,PROV_LATITUDE = ?14,PROV_LONGITUDE = ?15,PROV_CENSUS_TRACT =?16, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?17""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_NYCPRFSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToNYCPRFStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToHL7StdTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_HL7_STD_TABLE = """
			update STD_HL7_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_COUNTY = ?5,
			PERSON_LATITUDE = ?6,PERSON_LONGITUDE = ?7,PERSON_CENSUS_TRACT = ?8,FACILITY_ADDRESS = ?9,FACILITY_CITY = ?10,FACILITY_STATE = ?11,FACILITY_ZIP = ?12,FACILITY_COUNTY = ?13,
			FACILITY_CENSUS_TRACT = ?14, FACILITY_LATITUDE = ?15,FACILITY_LONGITUDE = ?16 where DOCUMENT_ID =	?17""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_HL7_STD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToHL7StdTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityCounty"),
			p.get("facilityCensusTract"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToMEDProvidersStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_MEDPROVIDERS_STANDARD_TABLE = """
			update STD_MEDICAID_DOC_PROVIDERS set PROV_ADDRESS = ?1,PROV_CITY = ?2,PROV_STATE = ?3,
			PROV_ZIP = ?4,PROV_COUNTY = ?5,PROV_LATITUDE = ?6,PROV_LONGITUDE = ?7,PROV_CENSUS_TRACT =?8 where MEDICAID_DOC_ID =	?9""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_MEDPROVIDERS_STANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToMEDProvidersStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToMEDStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_MEDSTANDARD_TABLE = """
			update STD_MEDICAID_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_COUNTY = ?5,
			PERSON_LATITUDE = ?6,PERSON_LONGITUDE = ?7,PERSON_CENSUS_TRACT = ?8, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?9""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_MEDSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToMEDStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToNycIJRStdTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_NYC_IJRSTD_TABLE = """
			update STD_NYCIJR_DOC set  CUR_PERSON_ADDRESS = ?1,CUR_PERSON_CITY = ?2,CUR_PERSON_STATE = ?3,CUR_PERSON_ZIP = ?4,CUR_PERSON_COUNTY = ?5,
			CUR_PERSON_LATITUDE = ?6,CUR_PERSON_LONGITUDE = ?7,CUR_PERSON_CENSUS_TRACT = ?8,PROV_ADDRESS = ?9,PROV_CITY = ?10,PROV_STATE = ?11,
			PROV_ZIP = ?12,PROV_COUNTY = ?13,PROV_LATITUDE = ?14,PROV_LONGITUDE = ?15,PROV_CENSUS_TRACT =?16, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?17""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_NYC_IJRSTD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToNycIJRStdTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToICCRStdTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_ICCRSTD_TABLE = """
			update STD_ICCR_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_COUNTY = ?5,
			PERSON_LATITUDE = ?6,PERSON_LONGITUDE = ?7,PERSON_CENSUS_TRACT = ?8,PROV_ADDRESS = ?9,PROV_CITY = ?10,PROV_STATE = ?11,
			PROV_ZIP = ?12,PROV_COUNTY = ?13,PROV_LATITUDE = ?14,PROV_LONGITUDE = ?15,PROV_CENSUS_TRACT =?16, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?17""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_ICCRSTD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToICCRStdTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToSTDConfStdTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_STDCONF_STD_TABLE = """
			update STD_STD_CONFIRMED_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_COUNTY = ?5,
			PERSON_LATITUDE = ?6,PERSON_LONGITUDE = ?7,PERSON_CENSUS_TRACT = ?8,PROV_ADDRESS = ?9,PROV_CITY = ?10,PROV_STATE = ?11,
			PROV_ZIP = ?12,PROV_COUNTY = ?13,PROV_LATITUDE = ?14,PROV_LONGITUDE = ?15,PROV_CENSUS_TRACT =?16,
			MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?17""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_STDCONF_STD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToSTDConfStdTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code storeDocumentMatchResultsToSTDStandardTable}, as the former mapper had it. */
	static final String STORE_DOCUMENT_MATCH_RESULTS_TO_STDSTANDARD_TABLE = """
			update STD_STD_ECLRS_DOC set  PERSON_ADDRESS = ?1,PERSON_CITY = ?2,PERSON_STATE = ?3,PERSON_ZIP = ?4,PERSON_PLUS4 = ?5,PERSON_COUNTY = ?6,
			PERSON_LATITUDE = ?7,PERSON_LONGITUDE = ?8,PERSON_CENSUS_TRACT = ?9,PROV_ADDRESS = ?10,PROV_CITY = ?11,PROV_STATE = ?12,
			PROV_ZIP = ?13,PROV_PLUS4 = ?14,PROV_COUNTY = ?15,PROV_LATITUDE = ?16,PROV_LONGITUDE = ?17,PROV_CENSUS_TRACT =?18,
			FACILITY_ADDRESS = ?19,FACILITY_CITY = ?20,FACILITY_STATE = ?21,FACILITY_ZIP = ?22,FACILITY_PLUS4 = ?23,FACILITY_COUNTY = ?24,
			FACILITY_CENSUS_TRACT = ?25, FACILITY_LATITUDE = ?26,FACILITY_LONGITUDE = ?27, MATCHED_PROCESS_DATE  = SYSDATE where DOCUMENT_ID =	?28""";

	/** The values bound to {@link #STORE_DOCUMENT_MATCH_RESULTS_TO_STDSTANDARD_TABLE}, in order. */
	static Object[] storeDocumentMatchResultsToSTDStandardTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("personAddress"),
			p.get("personCity"),
			p.get("personState"),
			p.get("personZip"),
			p.get("personPlus4"),
			p.get("personCounty"),
			p.get("personLatitude"),
			p.get("personLongitude"),
			p.get("personCensusTract"),
			p.get("providerAddress"),
			p.get("providerCity"),
			p.get("providerState"),
			p.get("providerZip"),
			p.get("providerPlus4"),
			p.get("providerCounty"),
			p.get("providerLatitude"),
			p.get("providerLongitude"),
			p.get("providerCensusTract"),
			p.get("facilityAddress"),
			p.get("facilityCity"),
			p.get("facilityState"),
			p.get("facilityZip"),
			p.get("facilityPlus4"),
			p.get("facilityCounty"),
			p.get("facilityCensusTract"),
			p.get("facilityLatitude"),
			p.get("facilityLongitude"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code writeRawStdRecordDataToStdTable}, as the former mapper had it. */
	static final String WRITE_RAW_STD_RECORD_DATA_TO_STD_TABLE = """
			insert into STD_STD_ECLRS_DOC(DOCUMENT_ID,LAB_SRC_ID,FNAME,MNAME,LNAME,SUFFIX,DOB_TEXT,AGE,DIN,NYSID,SSN,BIRTH_COUNTRY,PERSON_ADDRESS,PERSON_CITY,PERSON_STATE,PERSON_ZIP,PERSON_PHONE,PERSON_COUNTRY,GENDER,RACE_ASIAN,RACE_BLACK,RACE_INDIAN,RACE_OTHER,RACE_PACIFIC,RACE_UNKNOWN,RACE_WHITE,ETHNICITY,MED_RECORD_NUMBER,INSURANCE_CD,PROV_FNAME,PROV_MNAME,PROV_LNAME,PROV_SUFFIX,PROV_ADDRESS,PROV_CITY,PROV_STATE,PROV_ZIP,PROV_PHONE,FACILITY_NAME,FACILITY_ADDRESS,FACILITY_CITY,FACILITY_STATE,FACILITY_ZIP,FACILITY_PHONE,OBSERVATION_DATE,COLLECTION_DATE,RESULT_REPORT_DATE,LOINC,LOINC_DESC,TEST_RESULT,UNIT_MEASURE_CODE,PERSON_INTERNAL_ID,PERSON_EXTERNAL_ID,SENDING_FACILITY_NAME,SPECIMEN_SOURCE_CD,SPECIMEN_SOURCE_NAME,REFERRING_FACILITY,TESTING_FACILITY,HIV_VACCINE_RECIP,LENGTH_RESIDENCY_IN_USA,SENDING_FACILITY_CLIA,ACCESSION_NUMBER,MEDICAID_NUMBER,LOCAL_TEST_CODE,LOCAL_TEST_DESC,CURRENT_PROCESS_STATUS,CREATE_UID, CREATE_DATE, DATE_RECEIVED, AGE_CATEGORY, CALC_COLLECTION_DATE, PROV_NPI, FACILITY_NPI) values(
			?1,?2,upper(trim(?3)),upper(trim(?4)),upper(trim(?5)),upper(trim(?6)),trim(?7),?8,upper(trim(?9)),upper(trim(?10)),trim(?11),upper(trim(?12)),upper(trim(?13)),upper(trim(?14)),upper(trim(?15)),trim(?16),trim(?17),upper(trim(?18)),upper(trim(?19)),?20,?21,?22,?23,?24,?25,?26,?27,upper(trim(?28)),upper(trim(?29)),upper(trim(?30)),upper(trim(?31)),upper(trim(?32)),upper(trim(?33)),upper(trim(?34)),upper(trim(?35)),upper(trim(?36)),upper(trim(?37)),upper(trim(?38)),upper(trim(?39)),upper(trim(?40)),upper(trim(?41)),upper(trim(?42)),upper(trim(?43)),upper(trim(?44)),?45,?46,?47,upper(trim(?48)),upper(trim(?49)),upper(trim(?50)),upper(trim(?51)),upper(trim(?52)),upper(trim(?53)),upper(trim(?54)),upper(trim(?55)),upper(trim(?56)),upper(trim(?57)),upper(trim(?58)),upper(trim(?59)),upper(trim(?60)),upper(trim(?61)),upper(trim(?62)),upper(trim(?63)),upper(trim(?64)),upper(trim(?65)),?66, ?67, SYSDATE, ?68, ?69, ?70
			,upper(trim(?71)) ,upper(trim(?72))
			)""";

	/** The values bound to {@link #WRITE_RAW_STD_RECORD_DATA_TO_STD_TABLE}, in order. */
	static Object[] writeRawStdRecordDataToStdTableArgs(EcrlsStdDocVO p) {
		return new Object[] {
			p.getDOCUMENT_ID(),
			p.getLAB_SRC_ID(),
			text(p.getFNAME()),
			text(p.getMNAME()),
			text(p.getLNAME()),
			text(p.getSUFFIX()),
			text(p.getDOB_TEXT()),
			text(p.getAGE()),
			text(p.getDIN()),
			text(p.getNYSID()),
			text(p.getSSN()),
			text(p.getBIRTH_COUNTRY()),
			text(p.getPERSON_ADDRESS()),
			text(p.getPERSON_CITY()),
			text(p.getPERSON_STATE()),
			text(p.getPERSON_ZIP()),
			text(p.getPERSON_PHONE()),
			text(p.getPERSON_COUNTRY()),
			text(p.getCURRENT_GENDER()),
			text(p.getRACE_ASIAN()),
			text(p.getRACE_BLACK()),
			text(p.getRACE_INDIAN()),
			text(p.getRACE_OTHER()),
			text(p.getRACE_PACIFIC()),
			text(p.getRACE_UNKNOWN()),
			text(p.getRACE_WHITE()),
			text(p.getETHNICITY()),
			text(p.getMED_RECORD_NUMBER()),
			text(p.getINSURANCE_CD()),
			text(p.getPROV_FNAME()),
			text(p.getPROV_MNAME()),
			text(p.getPROV_LNAME()),
			text(p.getPROV_SUFFIX()),
			text(p.getPROV_ADDRESS()),
			text(p.getPROV_CITY()),
			text(p.getPROV_STATE()),
			text(p.getPROV_ZIP()),
			text(p.getPROV_PHONE()),
			text(p.getFACILITY_NAME()),
			text(p.getFACILITY_ADDRESS()),
			text(p.getFACILITY_CITY()),
			text(p.getFACILITY_STATE()),
			text(p.getFACILITY_ZIP()),
			text(p.getFACILITY_PHONE()),
			date(p.getOBSERVATION_DATE()),
			date(p.getCOLLECTION_DATE()),
			date(p.getRESULT_REPORT_DATE()),
			text(p.getLOINC()),
			text(p.getLOINC_DESC()),
			text(p.getTEST_RESULT()),
			text(p.getUNIT_MEASURE_CODE()),
			text(p.getPERSON_INTERNAL_ID()),
			text(p.getPERSON_EXTERNAL_ID()),
			text(p.getSENDING_FACILITY_NAME()),
			text(p.getSPECIMEN_SOURCE_CD()),
			text(p.getSPECIMEN_SOURCE_NAME()),
			text(p.getREFERRING_FACILITY()),
			text(p.getTESTING_FACILITY()),
			text(p.getHIV_VACCINE_RECIP()),
			text(p.getLENGTH_RESIDENCY_IN_USA()),
			text(p.getSENDING_FACILITY_CLIA()),
			text(p.getACCESSION_NUMBER()),
			text(p.getMEDICAID_NUMBER()),
			text(p.getLOCAL_TEST_CODE()),
			p.getLOCAL_TEST_DESC(),
			p.getCURRENT_PROCESS_STATUS(),
			p.getCREATE_UID(),
			date(p.getDATE_RECEIVED()),
			number(p.getAGE_CATEGORY()),
			date(p.getCALC_COLLECTION_DATE()),
			text(p.getPROV_NPI()),
			text(p.getFACILITY_NPI())
		};
	}

	/** The SQL of {@code getLastInsertedECLRSRecordId}, as the former mapper had it. */
	static final String GET_LAST_INSERTED_ECLRSRECORD_ID = """
			WITH data AS
			(
			SELECT NVL (OBX_MAX, 0) LAST_MAX_OBX_KEY,
			NVL (LAST_PIDUPDATEDATE, '01-Jan-2017') LAST_PIDUPDATEDATE
			,rank() over (order by NVL (OBX_MAX, 0) desc) rn
			FROM LAB_ECLRS_LOAD_LOG
			WHERE HIV_STD = ?1
			)
			SELECT * from data where rn = 1""";

	/** The values bound to {@link #GET_LAST_INSERTED_ECLRSRECORD_ID}, in order. */
	static Object[] getLastInsertedECLRSRecordIdArgs(String p) {
		return new Object[] {
			p
		};
	}

	/** The SQL of {@code getMultiStepDocuments}, as the former mapper had it. */
	static final String GET_MULTI_STEP_DOCUMENTS = """
			WITH grouped_loinc_data AS
			(
			SELECT Distinct PFI, LOINC_1, LOINC_2, LOINC_3, LOINC_4, GROUP_COUNT GROUPED_LOINC , LOINC_GROUP_ID
			FROM CODED_LOINC_GROUPING
			WHERE (end_date is null or end_Date > SYSDATE)
			)
			SELECT REFRENCE.*
			FROM (SELECT MAX (DOCUMENT_ID)
			OVER (PARTITION BY trim(upper(MSHSENDINGFACILITYCLIA)),
			trim(upper(PIDLASTNAME)),
			trim(upper(PIDFIRSTNAME)),
			trim(upper(PIDMIDDLENAME)),
			trim(upper(PIDNAMESUFFIX)),
			trim(upper(PIDDATEOFBIRTH)),
			trim(upper(PIDSSN)),
			trim(upper(PIDDEATHDATE)),
			trim(upper(PIDMEDICALRECORDNUMBER)),
			trim(upper(PIDPATIENTACCOUNTNUM)),
			trim(upper(PIDTYPEOFINSURANCE)),
			trim(upper(PIDNYSID)),
			trim(upper(PIDDIN)),
			trim(upper(PIDADDRESSLINE1)),
			trim(upper(PIDADDRESSLINE2)),
			trim(upper(PIDCITY)),
			trim(upper(PIDSTATE)),
			trim(upper(PIDZIPCODE)),
			trim(upper(PIDHOMEPHONE)),
			trim(upper(PIDSEXCODE)),
			trim(upper(PIDRACEASIAN)),
			trim(upper(PIDRACEBLACK)),
			trim(upper(PIDRACENATIVEAMERALASKAN)),
			trim(upper(PIDRACEOTHER)),
			trim(upper(PIDRACEPACIFIC)),
			trim(upper(PIDRACEUNKNOWN)),
			trim(upper(PIDRACEWHITE)),
			trim(upper(PIDETHNICGROUPCODE)),
			trim(upper(PIDFACILITYIDINTERNAL)),
			trim(upper(PIDFACILITYIDEXTERNAL)),
			trim(upper(PIDCOUNTRYOFORIGIN)),
			trim(upper(PIDLENGTHOFRESIDENCY)),
			trim(upper(OBRPROVIDERLASTNAME)),
			trim(upper(OBRPROVIDERFIRSTNAME)),
			trim(upper(OBRPROVIDERMIDDLENAME)),
			trim(upper(OBRCOLLECTIONDATE)),
			trim(upper(OBRACCESSIONNUM)),
			trim(upper(ZLRPROVIDERADDRESSLINE1)),
			trim(upper(ZLRPROVIDERADDRESSLINE2)),
			trim(upper(ZLRPROVIDERCITY)),
			trim(upper(ZLRPROVIDERSTATE)),
			trim(upper(ZLRPROVIDERZIPCODE)),
			trim(upper(OBRCALLBACKPHONE)),
			trim(upper(ZLRFACILITYNAME)),
			trim(upper(ZLRFACILITYADDRESSLINE1)),
			trim(upper(ZLRFACILITYADDRESSLINE2)),
			trim(upper(ZLRFACILITYCITY)),
			trim(upper(ZLRFACILITYSTATE)),
			trim(upper(ZLRFACILITYZIPCODE)),
			trim(upper(ZLRFACILITYPHONE)),
			trim(upper(ZLRNEXTOFKINRELATIONSHIP)),
			trim(upper(ZLRNEXTOFKINLASTNAME)),
			trim(upper(ZLRNEXTOFKINFIRSTNAME)),
			trim(upper(ZLRNEXTOFKINMIDDLENAME)),
			trim(upper(ZLRNEXTOFKINADDRESSLINE1)),
			trim(upper(ZLRNEXTOFKINADDRESSLINE2)),
			trim(upper(ZLRNEXTOFKINCITY)),
			trim(upper(ZLRNEXTOFKINSTATE)),
			trim(upper(ZLRNEXTOFKINZIPCODE)),
			trim(upper(ZLRNEXTOFKINPHONE)),
			l.GROUPED_LOINC,
			l.LOINC_GROUP_ID
			ORDER BY
			trim(upper(MSHSENDINGFACILITYCLIA)),
			trim(upper(PIDLASTNAME)),
			trim(upper(PIDFIRSTNAME)),
			trim(upper(PIDMIDDLENAME)),
			trim(upper(PIDNAMESUFFIX)),
			trim(upper(PIDDATEOFBIRTH)),
			trim(upper(PIDSSN)),
			trim(upper(PIDDEATHDATE)),
			trim(upper(PIDMEDICALRECORDNUMBER)),
			trim(upper(PIDPATIENTACCOUNTNUM)),
			trim(upper(PIDTYPEOFINSURANCE)),
			trim(upper(PIDNYSID)),
			trim(upper(PIDDIN)),
			trim(upper(PIDADDRESSLINE1)),
			trim(upper(PIDADDRESSLINE2)),
			trim(upper(PIDCITY)),
			trim(upper(PIDSTATE)),
			trim(upper(PIDZIPCODE)),
			trim(upper(PIDHOMEPHONE)),
			trim(upper(PIDSEXCODE)),
			trim(upper(PIDRACEASIAN)),
			trim(upper(PIDRACEBLACK)),
			trim(upper(PIDRACENATIVEAMERALASKAN)),
			trim(upper(PIDRACEOTHER)),
			trim(upper(PIDRACEPACIFIC)),
			trim(upper(PIDRACEUNKNOWN)),
			trim(upper(PIDRACEWHITE)),
			trim(upper(PIDETHNICGROUPCODE)),
			trim(upper(PIDFACILITYIDINTERNAL)),
			trim(upper(PIDFACILITYIDEXTERNAL)),
			trim(upper(PIDCOUNTRYOFORIGIN)),
			trim(upper(PIDLENGTHOFRESIDENCY)),
			trim(upper(OBRPROVIDERLASTNAME)),
			trim(upper(OBRPROVIDERFIRSTNAME)),
			trim(upper(OBRPROVIDERMIDDLENAME)),
			trim(upper(OBRCOLLECTIONDATE)),
			trim(upper(OBRACCESSIONNUM)),
			trim(upper(ZLRPROVIDERADDRESSLINE1)),
			trim(upper(ZLRPROVIDERADDRESSLINE2)),
			trim(upper(ZLRPROVIDERCITY)),
			trim(upper(ZLRPROVIDERSTATE)),
			trim(upper(ZLRPROVIDERZIPCODE)),
			trim(upper(OBRCALLBACKPHONE)),
			trim(upper(ZLRFACILITYNAME)),
			trim(upper(ZLRFACILITYADDRESSLINE1)),
			trim(upper(ZLRFACILITYADDRESSLINE2)),
			trim(upper(ZLRFACILITYCITY)),
			trim(upper(ZLRFACILITYSTATE)),
			trim(upper(ZLRFACILITYZIPCODE)),
			trim(upper(ZLRFACILITYPHONE)),
			trim(upper(ZLRNEXTOFKINRELATIONSHIP)),
			trim(upper(ZLRNEXTOFKINLASTNAME)),
			trim(upper(ZLRNEXTOFKINFIRSTNAME)),
			trim(upper(ZLRNEXTOFKINMIDDLENAME)),
			trim(upper(ZLRNEXTOFKINADDRESSLINE1)),
			trim(upper(ZLRNEXTOFKINADDRESSLINE2)),
			trim(upper(ZLRNEXTOFKINCITY)),
			trim(upper(ZLRNEXTOFKINSTATE)),
			trim(upper(ZLRNEXTOFKINZIPCODE)),
			trim(upper(ZLRNEXTOFKINPHONE)),
			l.GROUPED_LOINC,
			l.LOINC_GROUP_ID) RANKING,
			R.*
			FROM RAW_HIV_ECLRS_DOC R, grouped_loinc_data l
			WHERE     CURRENT_PROCESS_STATUS IN (11)
			AND r.MSHLABPFI = l.pfi
			AND Regexp_Like(l.loinc_1||l.loinc_2||l.loinc_3||l.loinc_4, r.OBXLOINCCODE)
			AND l.GROUPED_LOINC > 0
			) REFRENCE
			ORDER BY REFRENCE.MSHSENDINGFACILITYCLIA,
			REFRENCE.PIDLASTNAME,
			REFRENCE.PIDFIRSTNAME,
			REFRENCE.PIDMIDDLENAME,
			REFRENCE.PIDNAMESUFFIX,
			REFRENCE.PIDDATEOFBIRTH,
			REFRENCE.PIDSSN,
			REFRENCE.PIDDEATHDATE,
			REFRENCE.PIDMEDICALRECORDNUMBER,
			REFRENCE.PIDPATIENTACCOUNTNUM,
			REFRENCE.PIDTYPEOFINSURANCE,
			REFRENCE.PIDNYSID,
			REFRENCE.PIDDIN,
			REFRENCE.PIDADDRESSLINE1,
			REFRENCE.PIDADDRESSLINE2,
			REFRENCE.PIDCITY,
			REFRENCE.PIDSTATE,
			REFRENCE.PIDZIPCODE,
			REFRENCE.PIDHOMEPHONE,
			REFRENCE.PIDSEXCODE,
			REFRENCE.PIDRACEASIAN,
			REFRENCE.PIDRACEBLACK,
			REFRENCE.PIDRACENATIVEAMERALASKAN,
			REFRENCE.PIDRACEOTHER,
			REFRENCE.PIDRACEPACIFIC,
			REFRENCE.PIDRACEUNKNOWN,
			REFRENCE.PIDRACEWHITE,
			REFRENCE.PIDETHNICGROUPCODE,
			REFRENCE.PIDFACILITYIDINTERNAL,
			REFRENCE.PIDFACILITYIDEXTERNAL,
			REFRENCE.PIDCOUNTRYOFORIGIN,
			REFRENCE.PIDLENGTHOFRESIDENCY,
			REFRENCE.OBRPROVIDERLASTNAME,
			REFRENCE.OBRPROVIDERFIRSTNAME,
			REFRENCE.OBRPROVIDERMIDDLENAME,
			REFRENCE.OBRCOLLECTIONDATE,
			REFRENCE.OBRACCESSIONNUM,
			REFRENCE.ZLRPROVIDERADDRESSLINE1,
			REFRENCE.ZLRPROVIDERADDRESSLINE2,
			REFRENCE.ZLRPROVIDERCITY,
			REFRENCE.ZLRPROVIDERSTATE,
			REFRENCE.ZLRPROVIDERZIPCODE,
			REFRENCE.OBRCALLBACKPHONE,
			REFRENCE.ZLRFACILITYNAME,
			REFRENCE.ZLRFACILITYADDRESSLINE1,
			REFRENCE.ZLRFACILITYADDRESSLINE2,
			REFRENCE.ZLRFACILITYCITY,
			REFRENCE.ZLRFACILITYSTATE,
			REFRENCE.ZLRFACILITYZIPCODE,
			REFRENCE.ZLRFACILITYPHONE,
			REFRENCE.ZLRNEXTOFKINRELATIONSHIP,
			REFRENCE.ZLRNEXTOFKINLASTNAME,
			REFRENCE.ZLRNEXTOFKINFIRSTNAME,
			REFRENCE.ZLRNEXTOFKINMIDDLENAME,
			REFRENCE.ZLRNEXTOFKINADDRESSLINE1,
			REFRENCE.ZLRNEXTOFKINADDRESSLINE2,
			REFRENCE.ZLRNEXTOFKINCITY,
			REFRENCE.ZLRNEXTOFKINSTATE,
			REFRENCE.ZLRNEXTOFKINZIPCODE,
			REFRENCE.ZLRNEXTOFKINPHONE,
			REFRENCE.RANKING""";

	/** The SQL of {@code changeDocumentsStatusToMultiStepCompressFlag}, as the former mapper had it. */
	static final String CHANGE_DOCUMENTS_STATUS_TO_MULTI_STEP_COMPRESS_FLAG = """
			UPDATE raw_hiv_eclrs_doc
			SET current_process_status = 11, PROCESS_DATE = SYSDATE
			WHERE document_id IN (
			SELECT b.document_id
			FROM raw_hiv_eclrs_doc b, coded_loinc_grouping a
			WHERE b.current_process_status = 1
			AND a.pfi = b.mshlabpfi
			AND a.group_count > 0 AND (a.end_date IS NULL OR a.end_date > sysdate)
			AND Regexp_Like(a.loinc_1||a.loinc_2||a.loinc_3||a.loinc_4, b.obxloinccode))""";

	/** The SQL of {@code generateHashNumberForNewRecords}, as the former mapper had it. */
	static final String GENERATE_HASH_NUMBER_FOR_NEW_RECORDS = """
			UPDATE ${tableName}
			SET hash_unique_number =
			ORA_HASH (${duplicateColumns})
			WHERE current_process_status = 1""";

	/** The SQL of {@code checkingDuplicatesInNewRawHIVRecords}, as the former mapper had it. */
	static final String CHECKING_DUPLICATES_IN_NEW_RAW_HIVRECORDS = """
			UPDATE ${tableName} r
			SET current_process_status = 2 , PROCESS_DATE = SYSDATE
			WHERE (r.document_id, r.current_process_status, 2) IN (SELECT document_id,
			1
			AS current_status,
			DECODE (rn,
			1, 1,
			2)
			AS cnt_one_or_more
			FROM (SELECT document_id,
			ROW_NUMBER ()
			OVER (
			PARTITION BY hash_unique_number
			ORDER BY
			document_id)
			AS rn
			FROM ${tableName} r
			WHERE current_process_status =
			1))""";

	/** The SQL of {@code checkingDuplicatesInEntireDataWithNewRawHIVData}, as the former mapper had it. */
	static final String CHECKING_DUPLICATES_IN_ENTIRE_DATA_WITH_NEW_RAW_HIVDATA = """
			UPDATE ${tableName} r1
			SET current_process_status = 2 , PROCESS_DATE = SYSDATE
			WHERE     current_process_status = 1
			AND EXISTS
			(SELECT 1
			FROM ${tableName} r
			WHERE     current_process_status <> 2
			AND r.document_id <> r1.document_id
			AND r.hash_unique_number = r1.hash_unique_number)""";

	/** The SQL of {@code writeErrorCode}, as the former mapper had it. */
	static final String WRITE_ERROR_CODE = """
			update ${rawTableName} set CURRENT_PROCESS_STATUS = ?1, PROCESS_DATE = SYSDATE    where DOCUMENT_ID =	?2""";

	/** The values bound to {@link #WRITE_ERROR_CODE}, in order. */
	static Object[] writeErrorCodeArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("statusCode"),
			p.get("documentId")
		};
	}

	/** The SQL of {@code getLoincsForPfiLocalCodeMatch}, as the former mapper had it. */
	static final String GET_LOINCS_FOR_PFI_LOCAL_CODE_MATCH = """
			SELECT DISTINCT(LOINC_CD)  FROM LAB_LOINC_MAPPING  WHERE  ?1 >= START_DATE and (END_DATE is null or END_DATE >= ?2) and LAB_LOINC_MAP_ID IN (SELECT LAB_LOINC_MAP_ID   FROM LAB_LOCAL_CD_LOINC_MAPPING  WHERE  LAB_LOCAL_CD_MAP_ID = ?3 and ?4 >= START_DATE  and (END_DATE is null or END_DATE >= ?5))""";

	/** The values bound to {@link #GET_LOINCS_FOR_PFI_LOCAL_CODE_MATCH}, in order. */
	static Object[] getLoincsForPfiLocalCodeMatchArgs(String p) {
		return new Object[] {
			p,
			p,
			p,
			p,
			p
		};
	}

	/** The SQL of {@code checkForLoincAndUnitsOfMeasurementCombination}, as the former mapper had it. */
	static final String CHECK_FOR_LOINC_AND_UNITS_OF_MEASUREMENT_COMBINATION = """
			select DESCRIPTION, TEST_TYPE, UNIT_OF_RESULT from CODED_LAB_TEST_TYPE where TEST_TYPE_CD =  (select max(TEST_TYPE_CD) from LAB_LOINC where  LOINC_CD = ?1)""";

	/** The values bound to {@link #CHECK_FOR_LOINC_AND_UNITS_OF_MEASUREMENT_COMBINATION}, in order. */
	static Object[] checkForLoincAndUnitsOfMeasurementCombinationArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("loincCode")
		};
	}

	/** The SQL of {@code checkForLoincAndObservationResultTextCombination}, as the former mapper had it. */
	static final String CHECK_FOR_LOINC_AND_OBSERVATION_RESULT_TEXT_COMBINATION = """
			select CESR.RESULTS_STD, CESR.RESULT_INTERPRETATION  from LOINC_ACCPTD_TST_RESULT_DOMAIN LATRD,  ACCPTD_TST_RESULT_DOMAIN  ATRD, CODED_EHARS_STD_RESULTS CESR where LATRD.LOINC_CD = ?1
			and LATRD.ATR_DOMAIN_CD = ATRD.ATR_DOMAIN_CD  and (UPPER(ATRD.STRING_VALUE) = ?2 or UPPER(CESR.RESULTS_STD) = ?3) and LATRD.RESULTS_STD_CD = CESR.RESULTS_STD_CD and LATRD.FLAG_ACTIVE = 'Y' and ATRD.FLAG_ACTIVE = 'Y'
			and rownum < 2""";

	/** The values bound to {@link #CHECK_FOR_LOINC_AND_OBSERVATION_RESULT_TEXT_COMBINATION}, in order. */
	static Object[] checkForLoincAndObservationResultTextCombinationArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("loincCode"),
			p.get("oBXOBSERVATIONRESULTTEXT"),
			p.get("oBXOBSERVATIONRESULTTEXT")
		};
	}

	/** The SQL of {@code checkForLoincAndObservationResultRangeCombination}, as the former mapper had it. */
	static final String CHECK_FOR_LOINC_AND_OBSERVATION_RESULT_RANGE_COMBINATION = """
			select CESR.RESULTS_STD, CESR.RESULT_INTERPRETATION, TO_CHAR(ATRD.RANGE_LOWER_BOUND) RANGE_LOWER_BOUND, TO_CHAR(ATRD.RANGE_UPPER_BOUND) RANGE_UPPER_BOUND  from LOINC_ACCPTD_TST_RESULT_DOMAIN LATRD,  ACCPTD_TST_RESULT_DOMAIN  ATRD, CODED_EHARS_STD_RESULTS CESR where LATRD.LOINC_CD = ?1
			and LATRD.ATR_DOMAIN_CD = ATRD.ATR_DOMAIN_CD  and (?2 between ATRD.RANGE_LOWER_BOUND and  ATRD.RANGE_UPPER_BOUND)    and LATRD.RESULTS_STD_CD = CESR.RESULTS_STD_CD and LATRD.FLAG_ACTIVE = 'Y' and ATRD.FLAG_ACTIVE = 'Y'""";

	/** The values bound to {@link #CHECK_FOR_LOINC_AND_OBSERVATION_RESULT_RANGE_COMBINATION}, in order. */
	static Object[] checkForLoincAndObservationResultRangeCombinationArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("loincCode"),
			p.get("oBXOBSERVATIONRESULTTEXT")
		};
	}

	/** The SQL of {@code getHivErrorsByCategoryAndCount}, as the former mapper had it. */
	static final String GET_HIV_ERRORS_BY_CATEGORY_AND_COUNT = """
			SELECT
			coded_err.ERROR_DESC errorDesc, count(*) count
			FROM
			nyehms_process_errors err,
			NYEHMS_PROCESS_ERRORS_DETAIL err_dtl
			,CODED_PROCESS_ERRORS   coded_err
			WHERE err.document_type = 'HIV_LAB'
			and  err.CREATE_DATE  > sysdate - 1
			and err.ACTIVE_FLAG = 'Y'
			and err.PROCESS_STATUS in (4,7,8)
			and err.ERROR_ID = err_dtl.ERROR_ID
			and coded_err.ERROR_CODE  = err_dtl.ERROR_CODE
			group by coded_err.ERROR_DESC""";

	/** The SQL of {@code insertHivRawEcrlsData}, as the former mapper had it. */
	static final String INSERT_HIV_RAW_ECRLS_DATA = """
			insert into RAW_HIV_ECLRS_DOC(DOCUMENT_ID,
			MSHFILECONTENTTYPE,
			MSHDELETEDIND,
			PIDSEXCODE,
			PIDRACECODE,
			PIDRACEASIAN,
			PIDRACEBLACK,
			PIDRACENATIVEAMERALASKAN,
			PIDRACEOTHER,
			PIDRACEPACIFIC,
			PIDRACEUNKNOWN,
			PIDRACEWHITE,
			PIDMARITALSTATUSCODE,
			PIDDEATHIND,
			PIDUSBORNIND,
			PIDDELETEDIND,
			PIDVACCINETRIALIND,
			PIDSMSA,
			PIDNONHUMANIND,
			OBRRESULTSTATUSCODE,
			OBRDELETEDIND,
			OBXRESULTSTATUSCODE,
			OBXLEADPURPOSE,
			OBXDELETEDIND,
			OBXPROCESSEDIND,
			PIDNOTE,
			OBRRELEVANTCLINICALINFO,
			OBRNOTE,
			OBXOBSERVATIONRESULTTEXT,
			OBXNOTE,
			PIDLONGITUDE,
			PIDLATITUDE,
			CURRENT_PROCESS_STATUS,
			INTERPRETATION_STD_DOC_ID,
			HASH_UNIQUE_NUMBER,
			MSHMESSAGEMASTERKEY,
			PIDPATIENTKEY,
			PIDMESSAGEMASTERKEY,
			OBROBSERVATIONREQUESTKEY,
			ZLROBSERVATIONREQUESTKEY,
			OBRPATIENTKEY,
			OBRPATIENTKEYORIG,
			OBRINITRESKEY,
			OBXOBSERVATIONRESULTKEY,
			OBXOBSERVATIONREQUESTKEY,
			OBXOLDESTDUPLICATERESULTKEY,
			PIDCOUNTYCODE,
			OBXCOUNTYCODE,
			OBXDISEASE,
			OBXDISEASEGROUP,
			MSHMESSAGEDATE,
			MSHCREATEDATE,
			MSHDELETEDATE,
			PIDCREATEDATE,
			PIDDATEOFBIRTH,
			PIDDEATHDATE,
			PIDUPDATEDATE,
			PIDDELETEDATE,
			OBRCREATEDATE,
			OBRCOLLECTIONDATE,
			OBRRESULTREPORTDATE,
			OBRDELETEDATE,
			OBROBSERVATIONDATE,
			OBRRECEIVEDDATE,
			OBXOBSERVATIONDATE,
			OBXPROCESSEDDATE,
			OBXDELETEDATE,
			PROGRAMCODEUPDATEDATE,
			OBXPROGRAMCODEUPDATEDATE,
			OBXCREATEDATE,
			OBXANALYSISDATE,
			MSHSENDINGFACILITYNAME,
			MSHSENDINGFACILITYCLIA,
			MSHMESSAGEDATERAW,
			MSHMESSAGECONTROLID,
			MSHPROCESSINGID,
			MSHVERSIONID,
			MSHLABPFI,
			MSHFILENAME,
			MSHSOURCETYPE,
			PIDSETID,
			PIDPATIENTIDEXTERNAL,
			PIDFACILITYIDEXTERNAL,
			PIDPATIENTIDINTERNAL,
			PIDFACILITYIDINTERNAL,
			PIDPATIENTIDALT,
			PIDFACILITYIDALT,
			PIDLASTNAME,
			PIDFIRSTNAME,
			PIDMIDDLENAME,
			PIDNAMESUFFIX,
			PIDDATEOFBIRTHRAW,
			PIDRACECODERAW,
			PIDADDRESSLINE1,
			PIDADDRESSLINE2,
			PIDCITY,
			PIDSTATE,
			PIDZIPCODE,
			PIDCOUNTRYCODE,
			PIDCOUNTYCODEORIGINAL,
			PIDHOMEPHONE,
			PIDWORKPHONE,
			PIDPATIENTLANGUAGE,
			PIDPATIENTACCOUNTNUM,
			PIDSSN,
			PIDMOTHERSID,
			PIDETHNICGROUPCODE,
			PIDDEATHDATERAW,
			PIDCOUNTYGAZCODEORIG,
			PIDMEDICAIDNUMBER,
			PIDDIN,
			PIDCOUNTRYOFORIGIN,
			PIDLENGTHOFRESIDENCY,
			PIDTYPEOFINSURANCE,
			PIDUPDATEID,
			ZLRAGE,
			ZLRNEXTOFKINLASTNAME,
			ZLRNEXTOFKINFIRSTNAME,
			ZLRNEXTOFKINMIDDLENAME,
			ZLRNEXTOFKINNAMESUFFIX,
			ZLRNEXTOFKINRELATIONSHIP,
			ZLRNEXTOFKINADDRESSLINE1,
			ZLRNEXTOFKINADDRESSLINE2,
			ZLRNEXTOFKINCITY,
			ZLRNEXTOFKINSTATE,
			ZLRNEXTOFKINZIPCODE,
			ZLRNEXTOFKINCOUNTRY,
			ZLRNEXTOFKINPHONE,
			PIDMEDICALRECORDNUMBER,
			PIDASSIGNAUTHOID,
			PIDASSIGNAUTHDESC,
			PIDNYSID,
			PIDEMAIL,
			PIDCELLPHONE,
			OBRSETID,
			OBRPLACERSORDERNUM,
			OBRACCESSIONNUM,
			OBRSNOMEDCODE,
			OBRLOINCCODE,
			OBRLOCALCODE,
			OBRANALYSISDESC,
			OBRCOLLECTIONDATERAW,
			OBRSPECIMENSOURCECODE,
			OBRSPECIMENSOURCENAME,
			OBRPROVIDERID,
			OBRPROVIDERLASTNAME,
			OBRPROVIDERFIRSTNAME,
			OBRPROVIDERMIDDLENAME,
			OBRPROVIDERNAMESUFFIX,
			OBRCALLBACKPHONE,
			OBRCALLBACKPHONEALT,
			OBRRESULTREPORTDATERAW,
			OBRPATHOLOGISTID,
			OBRPATHOLOGISTLASTNAME,
			OBRPATHOLOGISTFIRSTNAME,
			OBRPATHOLOGISTMIDDLENAME,
			OBRPATHOLOGISTNAMESUFFIX,
			OBRPROVIDERIDTYPECODE,
			OBRINITRESLOINCCODE,
			OBRINITRESLOINCDESC,
			OBRINITRESLOCALTESTCODE,
			OBRINITRESLOCALTESTDESC,
			OBRINITRESSUBID,
			OBRINITRESOBSERVATIONTEXT,
			OBRINITRESPLACERSORDERNUM,
			OBRINITRESACCESSIONNUM,
			OBRFILLERSORDERNUM,
			OBROBSERVATIONDATERAW,
			OBRRECEIVEDDATERAW,
			ZLRPROVIDERADDRESSLINE1,
			ZLRPROVIDERADDRESSLINE2,
			ZLRPROVIDERCITY,
			ZLRPROVIDERSTATE,
			ZLRPROVIDERZIPCODE,
			ZLRPROVIDERCOUNTRY,
			ZLRFACILITYCODE,
			ZLRFACILITYNAME,
			ZLRFACILITYADDRESSLINE1,
			ZLRFACILITYADDRESSLINE2,
			ZLRFACILITYCITY,
			ZLRFACILITYSTATE,
			ZLRFACILITYZIPCODE,
			ZLRFACILITYCOUNTRY,
			ZLRFACILITYPHONE,
			OBRPROVIDERIDALT1,
			OBRPROVIDERIDTYPECODEALT1,
			OBRPROVIDERIDALT2,
			OBRPROVIDERIDTYPECODEALT2,
			OBRPROVIDERFAX,
			OBRPROVIDEREMAIL,
			OBXSETID,
			OBXVALUETYPE,
			OBXLOINCCODE,
			OBXLOINCDESC,
			OBXLOINCDESCSHORT,
			OBXOBSERVATIONSUBID,
			OBXSNOMEDCODE,
			OBXSNOMEDDESC,
			OBXOBSERVATIONRESULTTEXTSHORT,
			OBXUNITOFMEASURE,
			OBXREFERENCERANGE,
			OBXABNORMALFLAG,
			OBXOBSERVATIONDATERAW,
			OBXPRODUCERCLIAID,
			OBXPRODUCERLABNAME,
			OBXOBSERVATIONMETHOD,
			OBXPROGRAMCODE,
			OBXLOGONID,
			PROGRAMCODEUPDATEID,
			OBXPROGRAMCODEUPDATEID,
			OBXLOCALTESTCODE,
			OBXLOCALTESTDESC,
			OBXLOCALRESULTCODE,
			OBXLOCALRESULTDESC,
			OBXSENDINGFACILITYCLIA,
			OBXANALYSISDATERAW,
			OBXICD_CODETYPE,
			OBXICD_DESC,
			OBXICD_CODE,
			CREATE_UID,
			CREATE_DATE
			) values(
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
			?47,
			?48,
			?49,
			?50,
			?51,
			?52,
			?53,
			?54,
			?55,
			?56,
			?57,
			?58,
			?59,
			?60,
			?61,
			?62,
			?63,
			?64,
			?65,
			?66,
			?67,
			?68,
			?69,
			?70,
			?71,
			?72,
			?73,
			?74,
			?75,
			?76,
			?77,
			?78,
			?79,
			?80,
			?81,
			?82,
			?83,
			?84,
			?85,
			?86,
			?87,
			?88,
			?89,
			?90,
			?91,
			?92,
			?93,
			?94,
			?95,
			?96,
			?97,
			?98,
			?99,
			?100,
			?101,
			?102,
			?103,
			?104,
			?105,
			?106,
			?107,
			?108,
			?109,
			?110,
			?111,
			?112,
			?113,
			?114,
			?115,
			?116,
			?117,
			?118,
			?119,
			?120,
			?121,
			?122,
			?123,
			?124,
			?125,
			?126,
			?127,
			?128,
			?129,
			?130,
			?131,
			?132,
			?133,
			?134,
			?135,
			?136,
			?137,
			?138,
			?139,
			?140,
			?141,
			?142,
			?143,
			?144,
			?145,
			?146,
			?147,
			?148,
			?149,
			?150,
			?151,
			?152,
			?153,
			?154,
			?155,
			?156,
			?157,
			?158,
			?159,
			?160,
			?161,
			?162,
			?163,
			?164,
			?165,
			?166,
			?167,
			?168,
			?169,
			?170,
			?171,
			?172,
			?173,
			?174,
			?175,
			?176,
			?177,
			?178,
			?179,
			?180,
			?181,
			?182,
			?183,
			?184,
			?185,
			?186,
			?187,
			?188,
			?189,
			?190,
			?191,
			?192,
			?193,
			?194,
			?195,
			?196,
			?197,
			?198,
			?199,
			?200,
			?201,
			?202,
			?203,
			?204,
			?205,
			?206,
			?207,
			?208,
			?209,
			?210,
			?211,
			?212,
			?213,
			?214,
			?215,
			?216,
			?217,
			?218,
			?219,
			?220,
			?221)""";

	/** The values bound to {@link #INSERT_HIV_RAW_ECRLS_DATA}, in order. */
	static Object[] insertHivRawEcrlsDataArgs(EcrlsLabDocVO p) {
		return new Object[] {
			number(p.getDOCUMENT_ID()),
			text(p.getMSHFILECONTENTTYPE()),
			text(p.getMSHDELETEDIND()),
			text(p.getPIDSEXCODE()),
			text(p.getPIDRACECODE()),
			text(p.getPIDRACEASIAN()),
			text(p.getPIDRACEBLACK()),
			text(p.getPIDRACENATIVEAMERALASKAN()),
			text(p.getPIDRACEOTHER()),
			text(p.getPIDRACEPACIFIC()),
			text(p.getPIDRACEUNKNOWN()),
			text(p.getPIDRACEWHITE()),
			text(p.getPIDMARITALSTATUSCODE()),
			text(p.getPIDDEATHIND()),
			text(p.getPIDUSBORNIND()),
			text(p.getPIDDELETEDIND()),
			text(p.getPIDVACCINETRIALIND()),
			text(p.getPIDSMSA()),
			text(p.getPIDNONHUMANIND()),
			text(p.getOBRRESULTSTATUSCODE()),
			text(p.getOBRDELETEDIND()),
			text(p.getOBXRESULTSTATUSCODE()),
			text(p.getOBXLEADPURPOSE()),
			text(p.getOBXDELETEDIND()),
			text(p.getOBXPROCESSEDIND()),
			text(p.getPIDNOTE()),
			text(p.getOBRRELEVANTCLINICALINFO()),
			text(p.getOBRNOTE()),
			text(p.getOBXOBSERVATIONRESULTTEXT()),
			text(p.getOBXNOTE()),
			number(p.getPIDLONGITUDE()),
			number(p.getPIDLATITUDE()),
			number(p.getCURRENT_PROCESS_STATUS()),
			number(p.getINTERPRETATION_STD_DOC_ID()),
			number(p.getHASH_UNIQUE_NUMBER()),
			number(p.getMSHMESSAGEMASTERKEY()),
			number(p.getPIDPATIENTKEY()),
			number(p.getPIDMESSAGEMASTERKEY()),
			number(p.getOBROBSERVATIONREQUESTKEY()),
			number(p.getZLROBSERVATIONREQUESTKEY()),
			number(p.getOBRPATIENTKEY()),
			number(p.getOBRPATIENTKEYORIG()),
			number(p.getOBRINITRESKEY()),
			number(p.getOBXOBSERVATIONRESULTKEY()),
			number(p.getOBXOBSERVATIONREQUESTKEY()),
			number(p.getOBXOLDESTDUPLICATERESULTKEY()),
			number(p.getPIDCOUNTYCODE()),
			number(p.getOBXCOUNTYCODE()),
			number(p.getOBXDISEASE()),
			number(p.getOBXDISEASEGROUP()),
			text(p.getMSHMESSAGEDATE()),
			text(p.getMSHCREATEDATE()),
			text(p.getMSHDELETEDATE()),
			text(p.getPIDCREATEDATE()),
			text(p.getPIDDATEOFBIRTH()),
			text(p.getPIDDEATHDATE()),
			text(p.getPIDUPDATEDATE()),
			text(p.getPIDDELETEDATE()),
			text(p.getOBRCREATEDATE()),
			text(p.getOBRCOLLECTIONDATE()),
			text(p.getOBRRESULTREPORTDATE()),
			text(p.getOBRDELETEDATE()),
			text(p.getOBROBSERVATIONDATE()),
			text(p.getOBRRECEIVEDDATE()),
			text(p.getOBXOBSERVATIONDATE()),
			text(p.getOBXPROCESSEDDATE()),
			text(p.getOBXDELETEDATE()),
			text(p.getPROGRAMCODEUPDATEDATE()),
			text(p.getOBXPROGRAMCODEUPDATEDATE()),
			text(p.getOBXCREATEDATE()),
			text(p.getOBXANALYSISDATE()),
			text(p.getMSHSENDINGFACILITYNAME()),
			text(p.getMSHSENDINGFACILITYCLIA()),
			text(p.getMSHMESSAGEDATERAW()),
			text(p.getMSHMESSAGECONTROLID()),
			text(p.getMSHPROCESSINGID()),
			text(p.getMSHVERSIONID()),
			text(p.getMSHLABPFI()),
			text(p.getMSHFILENAME()),
			text(p.getMSHSOURCETYPE()),
			text(p.getPIDSETID()),
			text(p.getPIDPATIENTIDEXTERNAL()),
			text(p.getPIDFACILITYIDEXTERNAL()),
			text(p.getPIDPATIENTIDINTERNAL()),
			text(p.getPIDFACILITYIDINTERNAL()),
			text(p.getPIDPATIENTIDALT()),
			text(p.getPIDFACILITYIDALT()),
			text(p.getPIDLASTNAME()),
			text(p.getPIDFIRSTNAME()),
			text(p.getPIDMIDDLENAME()),
			text(p.getPIDNAMESUFFIX()),
			text(p.getPIDDATEOFBIRTHRAW()),
			text(p.getPIDRACECODERAW()),
			text(p.getPIDADDRESSLINE1()),
			text(p.getPIDADDRESSLINE2()),
			text(p.getPIDCITY()),
			text(p.getPIDSTATE()),
			text(p.getPIDZIPCODE()),
			text(p.getPIDCOUNTRYCODE()),
			text(p.getPIDCOUNTYCODEORIGINAL()),
			text(p.getPIDHOMEPHONE()),
			text(p.getPIDWORKPHONE()),
			text(p.getPIDPATIENTLANGUAGE()),
			text(p.getPIDPATIENTACCOUNTNUM()),
			text(p.getPIDSSN()),
			text(p.getPIDMOTHERSID()),
			text(p.getPIDETHNICGROUPCODE()),
			text(p.getPIDDEATHDATERAW()),
			text(p.getPIDCOUNTYGAZCODEORIG()),
			text(p.getPIDMEDICAIDNUMBER()),
			text(p.getPIDDIN()),
			text(p.getPIDCOUNTRYOFORIGIN()),
			text(p.getPIDLENGTHOFRESIDENCY()),
			text(p.getPIDTYPEOFINSURANCE()),
			text(p.getPIDUPDATEID()),
			text(p.getZLRAGE()),
			text(p.getZLRNEXTOFKINLASTNAME()),
			text(p.getZLRNEXTOFKINFIRSTNAME()),
			text(p.getZLRNEXTOFKINMIDDLENAME()),
			text(p.getZLRNEXTOFKINNAMESUFFIX()),
			text(p.getZLRNEXTOFKINRELATIONSHIP()),
			text(p.getZLRNEXTOFKINADDRESSLINE1()),
			text(p.getZLRNEXTOFKINADDRESSLINE2()),
			text(p.getZLRNEXTOFKINCITY()),
			text(p.getZLRNEXTOFKINSTATE()),
			text(p.getZLRNEXTOFKINZIPCODE()),
			text(p.getZLRNEXTOFKINCOUNTRY()),
			text(p.getZLRNEXTOFKINPHONE()),
			text(p.getPIDMEDICALRECORDNUMBER()),
			text(p.getPIDASSIGNAUTHOID()),
			text(p.getPIDASSIGNAUTHDESC()),
			text(p.getPIDNYSID()),
			text(p.getPIDEMAIL()),
			text(p.getPIDCELLPHONE()),
			text(p.getOBRSETID()),
			text(p.getOBRPLACERSORDERNUM()),
			text(p.getOBRACCESSIONNUM()),
			text(p.getOBRSNOMEDCODE()),
			text(p.getOBRLOINCCODE()),
			text(p.getOBRLOCALCODE()),
			text(p.getOBRANALYSISDESC()),
			text(p.getOBRCOLLECTIONDATERAW()),
			text(p.getOBRSPECIMENSOURCECODE()),
			text(p.getOBRSPECIMENSOURCENAME()),
			text(p.getOBRPROVIDERID()),
			text(p.getOBRPROVIDERLASTNAME()),
			text(p.getOBRPROVIDERFIRSTNAME()),
			text(p.getOBRPROVIDERMIDDLENAME()),
			text(p.getOBRPROVIDERNAMESUFFIX()),
			text(p.getOBRCALLBACKPHONE()),
			text(p.getOBRCALLBACKPHONEALT()),
			text(p.getOBRRESULTREPORTDATERAW()),
			text(p.getOBRPATHOLOGISTID()),
			text(p.getOBRPATHOLOGISTLASTNAME()),
			text(p.getOBRPATHOLOGISTFIRSTNAME()),
			text(p.getOBRPATHOLOGISTMIDDLENAME()),
			text(p.getOBRPATHOLOGISTNAMESUFFIX()),
			text(p.getOBRPROVIDERIDTYPECODE()),
			text(p.getOBRINITRESLOINCCODE()),
			text(p.getOBRINITRESLOINCDESC()),
			text(p.getOBRINITRESLOCALTESTCODE()),
			text(p.getOBRINITRESLOCALTESTDESC()),
			text(p.getOBRINITRESSUBID()),
			text(p.getOBRINITRESOBSERVATIONTEXT()),
			text(p.getOBRINITRESPLACERSORDERNUM()),
			text(p.getOBRINITRESACCESSIONNUM()),
			text(p.getOBRFILLERSORDERNUM()),
			text(p.getOBROBSERVATIONDATERAW()),
			text(p.getOBRRECEIVEDDATERAW()),
			text(p.getZLRPROVIDERADDRESSLINE1()),
			text(p.getZLRPROVIDERADDRESSLINE2()),
			text(p.getZLRPROVIDERCITY()),
			text(p.getZLRPROVIDERSTATE()),
			text(p.getZLRPROVIDERZIPCODE()),
			text(p.getZLRPROVIDERCOUNTRY()),
			text(p.getZLRFACILITYCODE()),
			text(p.getZLRFACILITYNAME()),
			text(p.getZLRFACILITYADDRESSLINE1()),
			text(p.getZLRFACILITYADDRESSLINE2()),
			text(p.getZLRFACILITYCITY()),
			text(p.getZLRFACILITYSTATE()),
			text(p.getZLRFACILITYZIPCODE()),
			text(p.getZLRFACILITYCOUNTRY()),
			text(p.getZLRFACILITYPHONE()),
			text(p.getOBRPROVIDERIDALT1()),
			text(p.getOBRPROVIDERIDTYPECODEALT1()),
			text(p.getOBRPROVIDERIDALT2()),
			text(p.getOBRPROVIDERIDTYPECODEALT2()),
			text(p.getOBRPROVIDERFAX()),
			text(p.getOBRPROVIDEREMAIL()),
			text(p.getOBXSETID()),
			text(p.getOBXVALUETYPE()),
			text(p.getOBXLOINCCODE()),
			text(p.getOBXLOINCDESC()),
			text(p.getOBXLOINCDESCSHORT()),
			text(p.getOBXOBSERVATIONSUBID()),
			text(p.getOBXSNOMEDCODE()),
			text(p.getOBXSNOMEDDESC()),
			text(p.getOBXOBSERVATIONRESULTTEXTSHORT()),
			text(p.getOBXUNITOFMEASURE()),
			text(p.getOBXREFERENCERANGE()),
			text(p.getOBXABNORMALFLAG()),
			text(p.getOBXOBSERVATIONDATERAW()),
			text(p.getOBXPRODUCERCLIAID()),
			text(p.getOBXPRODUCERLABNAME()),
			text(p.getOBXOBSERVATIONMETHOD()),
			text(p.getOBXPROGRAMCODE()),
			text(p.getOBXLOGONID()),
			text(p.getPROGRAMCODEUPDATEID()),
			text(p.getOBXPROGRAMCODEUPDATEID()),
			text(p.getOBXLOCALTESTCODE()),
			text(p.getOBXLOCALTESTDESC()),
			text(p.getOBXLOCALRESULTCODE()),
			text(p.getOBXLOCALRESULTDESC()),
			text(p.getOBXSENDINGFACILITYCLIA()),
			text(p.getOBXANALYSISDATERAW()),
			text(p.getOBXICD_CODETYPE()),
			text(p.getOBXICD_DESC()),
			text(p.getOBXICD_CODE()),
			text(p.getCREATE_UID()),
			text(p.getCREATE_DATE())
		};
	}

	/** The SQL of {@code insertStdRawEcrlsData}, as the former mapper had it. */
	static final String INSERT_STD_RAW_ECRLS_DATA = """
			insert into RAW_STD_ECLRS_DOC(DOCUMENT_ID,
			MSHFILECONTENTTYPE,
			MSHDELETEDIND,
			PIDSEXCODE,
			PIDRACECODE,
			PIDRACEASIAN,
			PIDRACEBLACK,
			PIDRACENATIVEAMERALASKAN,
			PIDRACEOTHER,
			PIDRACEPACIFIC,
			PIDRACEUNKNOWN,
			PIDRACEWHITE,
			PIDMARITALSTATUSCODE,
			PIDDEATHIND,
			PIDUSBORNIND,
			PIDDELETEDIND,
			PIDVACCINETRIALIND,
			PIDSMSA,
			PIDNONHUMANIND,
			OBRRESULTSTATUSCODE,
			OBRDELETEDIND,
			OBXRESULTSTATUSCODE,
			OBXLEADPURPOSE,
			OBXDELETEDIND,
			OBXPROCESSEDIND,
			PIDNOTE,
			OBRRELEVANTCLINICALINFO,
			OBRNOTE,
			OBXOBSERVATIONRESULTTEXT,
			OBXNOTE,
			PIDLONGITUDE,
			PIDLATITUDE,
			CURRENT_PROCESS_STATUS,
			HASH_UNIQUE_NUMBER,
			MSHMESSAGEMASTERKEY,
			PIDPATIENTKEY,
			PIDMESSAGEMASTERKEY,
			OBROBSERVATIONREQUESTKEY,
			ZLROBSERVATIONREQUESTKEY,
			OBRPATIENTKEY,
			OBRPATIENTKEYORIG,
			OBRINITRESKEY,
			OBXOBSERVATIONRESULTKEY,
			OBXOBSERVATIONREQUESTKEY,
			OBXOLDESTDUPLICATERESULTKEY,
			PIDCOUNTYCODE,
			OBXCOUNTYCODE,
			OBXDISEASE,
			OBXDISEASEGROUP,
			MSHMESSAGEDATE,
			MSHCREATEDATE,
			MSHDELETEDATE,
			PIDCREATEDATE,
			PIDDATEOFBIRTH,
			PIDDEATHDATE,
			PIDUPDATEDATE,
			PIDDELETEDATE,
			OBRCREATEDATE,
			OBRCOLLECTIONDATE,
			OBRRESULTREPORTDATE,
			OBRDELETEDATE,
			OBROBSERVATIONDATE,
			OBRRECEIVEDDATE,
			OBXOBSERVATIONDATE,
			OBXPROCESSEDDATE,
			OBXDELETEDATE,
			PROGRAMCODEUPDATEDATE,
			OBXPROGRAMCODEUPDATEDATE,
			OBXCREATEDATE,
			OBXANALYSISDATE,
			MSHSENDINGFACILITYNAME,
			MSHSENDINGFACILITYCLIA,
			MSHMESSAGEDATERAW,
			MSHMESSAGECONTROLID,
			MSHPROCESSINGID,
			MSHVERSIONID,
			MSHLABPFI,
			MSHFILENAME,
			MSHSOURCETYPE,
			PIDSETID,
			PIDPATIENTIDEXTERNAL,
			PIDFACILITYIDEXTERNAL,
			PIDPATIENTIDINTERNAL,
			PIDFACILITYIDINTERNAL,
			PIDPATIENTIDALT,
			PIDFACILITYIDALT,
			PIDLASTNAME,
			PIDFIRSTNAME,
			PIDMIDDLENAME,
			PIDNAMESUFFIX,
			PIDDATEOFBIRTHRAW,
			PIDRACECODERAW,
			PIDADDRESSLINE1,
			PIDADDRESSLINE2,
			PIDCITY,
			PIDSTATE,
			PIDZIPCODE,
			PIDCOUNTRYCODE,
			PIDCOUNTYCODEORIGINAL,
			PIDHOMEPHONE,
			PIDWORKPHONE,
			PIDPATIENTLANGUAGE,
			PIDPATIENTACCOUNTNUM,
			PIDSSN,
			PIDMOTHERSID,
			PIDETHNICGROUPCODE,
			PIDDEATHDATERAW,
			PIDCOUNTYGAZCODEORIG,
			PIDMEDICAIDNUMBER,
			PIDDIN,
			PIDCOUNTRYOFORIGIN,
			PIDLENGTHOFRESIDENCY,
			PIDTYPEOFINSURANCE,
			PIDUPDATEID,
			ZLRAGE,
			ZLRNEXTOFKINLASTNAME,
			ZLRNEXTOFKINFIRSTNAME,
			ZLRNEXTOFKINMIDDLENAME,
			ZLRNEXTOFKINNAMESUFFIX,
			ZLRNEXTOFKINRELATIONSHIP,
			ZLRNEXTOFKINADDRESSLINE1,
			ZLRNEXTOFKINADDRESSLINE2,
			ZLRNEXTOFKINCITY,
			ZLRNEXTOFKINSTATE,
			ZLRNEXTOFKINZIPCODE,
			ZLRNEXTOFKINCOUNTRY,
			ZLRNEXTOFKINPHONE,
			PIDMEDICALRECORDNUMBER,
			PIDASSIGNAUTHOID,
			PIDASSIGNAUTHDESC,
			PIDNYSID,
			PIDEMAIL,
			PIDCELLPHONE,
			OBRSETID,
			OBRPLACERSORDERNUM,
			OBRACCESSIONNUM,
			OBRSNOMEDCODE,
			OBRLOINCCODE,
			OBRLOCALCODE,
			OBRANALYSISDESC,
			OBRCOLLECTIONDATERAW,
			OBRSPECIMENSOURCECODE,
			OBRSPECIMENSOURCENAME,
			OBRPROVIDERID,
			OBRPROVIDERLASTNAME,
			OBRPROVIDERFIRSTNAME,
			OBRPROVIDERMIDDLENAME,
			OBRPROVIDERNAMESUFFIX,
			OBRCALLBACKPHONE,
			OBRCALLBACKPHONEALT,
			OBRRESULTREPORTDATERAW,
			OBRPATHOLOGISTID,
			OBRPATHOLOGISTLASTNAME,
			OBRPATHOLOGISTFIRSTNAME,
			OBRPATHOLOGISTMIDDLENAME,
			OBRPATHOLOGISTNAMESUFFIX,
			OBRPROVIDERIDTYPECODE,
			OBRINITRESLOINCCODE,
			OBRINITRESLOINCDESC,
			OBRINITRESLOCALTESTCODE,
			OBRINITRESLOCALTESTDESC,
			OBRINITRESSUBID,
			OBRINITRESOBSERVATIONTEXT,
			OBRINITRESPLACERSORDERNUM,
			OBRINITRESACCESSIONNUM,
			OBRFILLERSORDERNUM,
			OBROBSERVATIONDATERAW,
			OBRRECEIVEDDATERAW,
			ZLRPROVIDERADDRESSLINE1,
			ZLRPROVIDERADDRESSLINE2,
			ZLRPROVIDERCITY,
			ZLRPROVIDERSTATE,
			ZLRPROVIDERZIPCODE,
			ZLRPROVIDERCOUNTRY,
			ZLRFACILITYCODE,
			ZLRFACILITYNAME,
			ZLRFACILITYADDRESSLINE1,
			ZLRFACILITYADDRESSLINE2,
			ZLRFACILITYCITY,
			ZLRFACILITYSTATE,
			ZLRFACILITYZIPCODE,
			ZLRFACILITYCOUNTRY,
			ZLRFACILITYPHONE,
			OBRPROVIDERIDALT1,
			OBRPROVIDERIDTYPECODEALT1,
			OBRPROVIDERIDALT2,
			OBRPROVIDERIDTYPECODEALT2,
			OBRPROVIDERFAX,
			OBRPROVIDEREMAIL,
			OBXSETID,
			OBXVALUETYPE,
			OBXLOINCCODE,
			OBXLOINCDESC,
			OBXLOINCDESCSHORT,
			OBXOBSERVATIONSUBID,
			OBXSNOMEDCODE,
			OBXSNOMEDDESC,
			OBXOBSERVATIONRESULTTEXTSHORT,
			OBXUNITOFMEASURE,
			OBXREFERENCERANGE,
			OBXABNORMALFLAG,
			OBXOBSERVATIONDATERAW,
			OBXPRODUCERCLIAID,
			OBXPRODUCERLABNAME,
			OBXOBSERVATIONMETHOD,
			OBXPROGRAMCODE,
			OBXLOGONID,
			PROGRAMCODEUPDATEID,
			OBXPROGRAMCODEUPDATEID,
			OBXLOCALTESTCODE,
			OBXLOCALTESTDESC,
			OBXLOCALRESULTCODE,
			OBXLOCALRESULTDESC,
			OBXSENDINGFACILITYCLIA,
			OBXANALYSISDATERAW,
			OBXICD_CODETYPE,
			OBXICD_DESC,
			OBXICD_CODE,
			CDESSMAPPEDVALUES,
			CLASS,
			MAPPABLEIND,
			OBRSPECIMENDESC,
			REASONNOTMAPPED,
			RESULT,
			SPECIMENSOURCE,
			TESTTYPE,
			CREATE_UID,
			CREATE_DATE
			) values(
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
			?47,
			?48,
			?49,
			?50,
			?51,
			?52,
			?53,
			?54,
			?55,
			?56,
			?57,
			?58,
			?59,
			?60,
			?61,
			?62,
			?63,
			?64,
			?65,
			?66,
			?67,
			?68,
			?69,
			?70,
			?71,
			?72,
			?73,
			?74,
			?75,
			?76,
			?77,
			?78,
			?79,
			?80,
			?81,
			?82,
			?83,
			?84,
			?85,
			?86,
			?87,
			?88,
			?89,
			?90,
			?91,
			?92,
			?93,
			?94,
			?95,
			?96,
			?97,
			?98,
			?99,
			?100,
			?101,
			?102,
			?103,
			?104,
			?105,
			?106,
			?107,
			?108,
			?109,
			?110,
			?111,
			?112,
			?113,
			?114,
			?115,
			?116,
			?117,
			?118,
			?119,
			?120,
			?121,
			?122,
			?123,
			?124,
			?125,
			?126,
			?127,
			?128,
			?129,
			?130,
			?131,
			?132,
			?133,
			?134,
			?135,
			?136,
			?137,
			?138,
			?139,
			?140,
			?141,
			?142,
			?143,
			?144,
			?145,
			?146,
			?147,
			?148,
			?149,
			?150,
			?151,
			?152,
			?153,
			?154,
			?155,
			?156,
			?157,
			?158,
			?159,
			?160,
			?161,
			?162,
			?163,
			?164,
			?165,
			?166,
			?167,
			?168,
			?169,
			?170,
			?171,
			?172,
			?173,
			?174,
			?175,
			?176,
			?177,
			?178,
			?179,
			?180,
			?181,
			?182,
			?183,
			?184,
			?185,
			?186,
			?187,
			?188,
			?189,
			?190,
			?191,
			?192,
			?193,
			?194,
			?195,
			?196,
			?197,
			?198,
			?199,
			?200,
			?201,
			?202,
			?203,
			?204,
			?205,
			?206,
			?207,
			?208,
			?209,
			?210,
			?211,
			?212,
			?213,
			?214,
			?215,
			?216,
			?217,
			?218,
			?219,
			?220,
			?221,
			?222,
			?223,
			?224,
			?225,
			?226,
			?227,
			?228
			)""";

	/** The values bound to {@link #INSERT_STD_RAW_ECRLS_DATA}, in order. */
	static Object[] insertStdRawEcrlsDataArgs(EcrlsLabDocVO p) {
		return new Object[] {
			number(p.getDOCUMENT_ID()),
			text(p.getMSHFILECONTENTTYPE()),
			text(p.getMSHDELETEDIND()),
			text(p.getPIDSEXCODE()),
			text(p.getPIDRACECODE()),
			text(p.getPIDRACEASIAN()),
			text(p.getPIDRACEBLACK()),
			text(p.getPIDRACENATIVEAMERALASKAN()),
			text(p.getPIDRACEOTHER()),
			text(p.getPIDRACEPACIFIC()),
			text(p.getPIDRACEUNKNOWN()),
			text(p.getPIDRACEWHITE()),
			text(p.getPIDMARITALSTATUSCODE()),
			text(p.getPIDDEATHIND()),
			text(p.getPIDUSBORNIND()),
			text(p.getPIDDELETEDIND()),
			text(p.getPIDVACCINETRIALIND()),
			text(p.getPIDSMSA()),
			text(p.getPIDNONHUMANIND()),
			text(p.getOBRRESULTSTATUSCODE()),
			text(p.getOBRDELETEDIND()),
			text(p.getOBXRESULTSTATUSCODE()),
			text(p.getOBXLEADPURPOSE()),
			text(p.getOBXDELETEDIND()),
			text(p.getOBXPROCESSEDIND()),
			text(p.getPIDNOTE()),
			text(p.getOBRRELEVANTCLINICALINFO()),
			text(p.getOBRNOTE()),
			text(p.getOBXOBSERVATIONRESULTTEXT()),
			text(p.getOBXNOTE()),
			number(p.getPIDLONGITUDE()),
			number(p.getPIDLATITUDE()),
			number(p.getCURRENT_PROCESS_STATUS()),
			text(p.getHASH_UNIQUE_NUMBER()),
			number(p.getMSHMESSAGEMASTERKEY()),
			number(p.getPIDPATIENTKEY()),
			number(p.getPIDMESSAGEMASTERKEY()),
			number(p.getOBROBSERVATIONREQUESTKEY()),
			number(p.getZLROBSERVATIONREQUESTKEY()),
			number(p.getOBRPATIENTKEY()),
			number(p.getOBRPATIENTKEYORIG()),
			number(p.getOBRINITRESKEY()),
			number(p.getOBXOBSERVATIONRESULTKEY()),
			number(p.getOBXOBSERVATIONREQUESTKEY()),
			number(p.getOBXOLDESTDUPLICATERESULTKEY()),
			number(p.getPIDCOUNTYCODE()),
			number(p.getOBXCOUNTYCODE()),
			number(p.getOBXDISEASE()),
			number(p.getOBXDISEASEGROUP()),
			text(p.getMSHMESSAGEDATE()),
			text(p.getMSHCREATEDATE()),
			text(p.getMSHDELETEDATE()),
			text(p.getPIDCREATEDATE()),
			text(p.getPIDDATEOFBIRTH()),
			text(p.getPIDDEATHDATE()),
			text(p.getPIDUPDATEDATE()),
			text(p.getPIDDELETEDATE()),
			text(p.getOBRCREATEDATE()),
			text(p.getOBRCOLLECTIONDATE()),
			text(p.getOBRRESULTREPORTDATE()),
			text(p.getOBRDELETEDATE()),
			text(p.getOBROBSERVATIONDATE()),
			text(p.getOBRRECEIVEDDATE()),
			text(p.getOBXOBSERVATIONDATE()),
			text(p.getOBXPROCESSEDDATE()),
			text(p.getOBXDELETEDATE()),
			text(p.getPROGRAMCODEUPDATEDATE()),
			text(p.getOBXPROGRAMCODEUPDATEDATE()),
			text(p.getOBXCREATEDATE()),
			text(p.getOBXANALYSISDATE()),
			text(p.getMSHSENDINGFACILITYNAME()),
			text(p.getMSHSENDINGFACILITYCLIA()),
			text(p.getMSHMESSAGEDATERAW()),
			text(p.getMSHMESSAGECONTROLID()),
			text(p.getMSHPROCESSINGID()),
			text(p.getMSHVERSIONID()),
			text(p.getMSHLABPFI()),
			text(p.getMSHFILENAME()),
			text(p.getMSHSOURCETYPE()),
			text(p.getPIDSETID()),
			text(p.getPIDPATIENTIDEXTERNAL()),
			text(p.getPIDFACILITYIDEXTERNAL()),
			text(p.getPIDPATIENTIDINTERNAL()),
			text(p.getPIDFACILITYIDINTERNAL()),
			text(p.getPIDPATIENTIDALT()),
			text(p.getPIDFACILITYIDALT()),
			text(p.getPIDLASTNAME()),
			text(p.getPIDFIRSTNAME()),
			text(p.getPIDMIDDLENAME()),
			text(p.getPIDNAMESUFFIX()),
			text(p.getPIDDATEOFBIRTHRAW()),
			text(p.getPIDRACECODERAW()),
			text(p.getPIDADDRESSLINE1()),
			text(p.getPIDADDRESSLINE2()),
			text(p.getPIDCITY()),
			text(p.getPIDSTATE()),
			text(p.getPIDZIPCODE()),
			text(p.getPIDCOUNTRYCODE()),
			text(p.getPIDCOUNTYCODEORIGINAL()),
			text(p.getPIDHOMEPHONE()),
			text(p.getPIDWORKPHONE()),
			text(p.getPIDPATIENTLANGUAGE()),
			text(p.getPIDPATIENTACCOUNTNUM()),
			text(p.getPIDSSN()),
			text(p.getPIDMOTHERSID()),
			text(p.getPIDETHNICGROUPCODE()),
			text(p.getPIDDEATHDATERAW()),
			text(p.getPIDCOUNTYGAZCODEORIG()),
			text(p.getPIDMEDICAIDNUMBER()),
			text(p.getPIDDIN()),
			text(p.getPIDCOUNTRYOFORIGIN()),
			text(p.getPIDLENGTHOFRESIDENCY()),
			text(p.getPIDTYPEOFINSURANCE()),
			text(p.getPIDUPDATEID()),
			text(p.getZLRAGE()),
			text(p.getZLRNEXTOFKINLASTNAME()),
			text(p.getZLRNEXTOFKINFIRSTNAME()),
			text(p.getZLRNEXTOFKINMIDDLENAME()),
			text(p.getZLRNEXTOFKINNAMESUFFIX()),
			text(p.getZLRNEXTOFKINRELATIONSHIP()),
			text(p.getZLRNEXTOFKINADDRESSLINE1()),
			text(p.getZLRNEXTOFKINADDRESSLINE2()),
			text(p.getZLRNEXTOFKINCITY()),
			text(p.getZLRNEXTOFKINSTATE()),
			text(p.getZLRNEXTOFKINZIPCODE()),
			text(p.getZLRNEXTOFKINCOUNTRY()),
			text(p.getZLRNEXTOFKINPHONE()),
			text(p.getPIDMEDICALRECORDNUMBER()),
			text(p.getPIDASSIGNAUTHOID()),
			text(p.getPIDASSIGNAUTHDESC()),
			text(p.getPIDNYSID()),
			text(p.getPIDEMAIL()),
			text(p.getPIDCELLPHONE()),
			text(p.getOBRSETID()),
			text(p.getOBRPLACERSORDERNUM()),
			text(p.getOBRACCESSIONNUM()),
			text(p.getOBRSNOMEDCODE()),
			text(p.getOBRLOINCCODE()),
			text(p.getOBRLOCALCODE()),
			text(p.getOBRANALYSISDESC()),
			text(p.getOBRCOLLECTIONDATERAW()),
			text(p.getOBRSPECIMENSOURCECODE()),
			text(p.getOBRSPECIMENSOURCENAME()),
			text(p.getOBRPROVIDERID()),
			text(p.getOBRPROVIDERLASTNAME()),
			text(p.getOBRPROVIDERFIRSTNAME()),
			text(p.getOBRPROVIDERMIDDLENAME()),
			text(p.getOBRPROVIDERNAMESUFFIX()),
			text(p.getOBRCALLBACKPHONE()),
			text(p.getOBRCALLBACKPHONEALT()),
			text(p.getOBRRESULTREPORTDATERAW()),
			text(p.getOBRPATHOLOGISTID()),
			text(p.getOBRPATHOLOGISTLASTNAME()),
			text(p.getOBRPATHOLOGISTFIRSTNAME()),
			text(p.getOBRPATHOLOGISTMIDDLENAME()),
			text(p.getOBRPATHOLOGISTNAMESUFFIX()),
			text(p.getOBRPROVIDERIDTYPECODE()),
			text(p.getOBRINITRESLOINCCODE()),
			text(p.getOBRINITRESLOINCDESC()),
			text(p.getOBRINITRESLOCALTESTCODE()),
			text(p.getOBRINITRESLOCALTESTDESC()),
			text(p.getOBRINITRESSUBID()),
			text(p.getOBRINITRESOBSERVATIONTEXT()),
			text(p.getOBRINITRESPLACERSORDERNUM()),
			text(p.getOBRINITRESACCESSIONNUM()),
			text(p.getOBRFILLERSORDERNUM()),
			text(p.getOBROBSERVATIONDATERAW()),
			text(p.getOBRRECEIVEDDATERAW()),
			text(p.getZLRPROVIDERADDRESSLINE1()),
			text(p.getZLRPROVIDERADDRESSLINE2()),
			text(p.getZLRPROVIDERCITY()),
			text(p.getZLRPROVIDERSTATE()),
			text(p.getZLRPROVIDERZIPCODE()),
			text(p.getZLRPROVIDERCOUNTRY()),
			text(p.getZLRFACILITYCODE()),
			text(p.getZLRFACILITYNAME()),
			text(p.getZLRFACILITYADDRESSLINE1()),
			text(p.getZLRFACILITYADDRESSLINE2()),
			text(p.getZLRFACILITYCITY()),
			text(p.getZLRFACILITYSTATE()),
			text(p.getZLRFACILITYZIPCODE()),
			text(p.getZLRFACILITYCOUNTRY()),
			text(p.getZLRFACILITYPHONE()),
			text(p.getOBRPROVIDERIDALT1()),
			text(p.getOBRPROVIDERIDTYPECODEALT1()),
			text(p.getOBRPROVIDERIDALT2()),
			text(p.getOBRPROVIDERIDTYPECODEALT2()),
			text(p.getOBRPROVIDERFAX()),
			text(p.getOBRPROVIDEREMAIL()),
			text(p.getOBXSETID()),
			text(p.getOBXVALUETYPE()),
			text(p.getOBXLOINCCODE()),
			text(p.getOBXLOINCDESC()),
			text(p.getOBXLOINCDESCSHORT()),
			text(p.getOBXOBSERVATIONSUBID()),
			text(p.getOBXSNOMEDCODE()),
			text(p.getOBXSNOMEDDESC()),
			text(p.getOBXOBSERVATIONRESULTTEXTSHORT()),
			text(p.getOBXUNITOFMEASURE()),
			text(p.getOBXREFERENCERANGE()),
			text(p.getOBXABNORMALFLAG()),
			text(p.getOBXOBSERVATIONDATERAW()),
			text(p.getOBXPRODUCERCLIAID()),
			text(p.getOBXPRODUCERLABNAME()),
			text(p.getOBXOBSERVATIONMETHOD()),
			text(p.getOBXPROGRAMCODE()),
			text(p.getOBXLOGONID()),
			text(p.getPROGRAMCODEUPDATEID()),
			text(p.getOBXPROGRAMCODEUPDATEID()),
			text(p.getOBXLOCALTESTCODE()),
			text(p.getOBXLOCALTESTDESC()),
			text(p.getOBXLOCALRESULTCODE()),
			text(p.getOBXLOCALRESULTDESC()),
			text(p.getOBXSENDINGFACILITYCLIA()),
			text(p.getOBXANALYSISDATERAW()),
			text(p.getOBXICD_CODETYPE()),
			text(p.getOBXICD_DESC()),
			text(p.getOBXICD_CODE()),
			text(p.getCDESSMAPPEDVALUES()),
			text(p.getCLASS()),
			text(p.getMAPPABLEIND()),
			text(p.getOBRSPECIMENDESC()),
			text(p.getREASONNOTMAPPED()),
			text(p.getRESULT()),
			text(p.getSPECIMENSOURCE()),
			text(p.getTESTTYPE()),
			text(p.getCREATE_UID()),
			text(p.getCREATE_DATE())
		};
	}

	/** The SQL of {@code getNyehmsDocumentCurrentStatus}, as the former mapper had it. */
	static final String GET_NYEHMS_DOCUMENT_CURRENT_STATUS = """
			select RAW1.DOCUMENT_ID, RAW1.CURRENT_PROCESS_STATUS CURRENT_PROCESS_STATUS_RAW, STD.CURRENT_PROCESS_STATUS CURRENT_PROCESS_STATUS_STD, STD.FLUX_MATCH_CD MATCH_CD, STD.EHARS_IMPORT_DATE,
			NVL(RAW1.PIDADDRESSLINE1, '') PIDADDRESSLINE1, NVL(RAW1.PIDADDRESSLINE2, '') PIDADDRESSLINE2, NVL(RAW1.PIDCITY, '') PIDCITY, NVL(RAW1.PIDSTATE, '') PIDSTATE, NVL(RAW1.PIDZIPCODE, '')  PIDZIPCODE
			from ${RAW_TABLE} RAW1, ${STD_TABLE} STD where RAW1.document_id = STD.DOCUMENT_ID(+) and RAW1.OBXOBSERVATIONRESULTKEY = ?1
			order
			by case when RAW1.CURRENT_PROCESS_STATUS = 1 or RAW1.CURRENT_PROCESS_STATUS = 3 or RAW1.CURRENT_PROCESS_STATUS = 4 or RAW1.CURRENT_PROCESS_STATUS = 7 or
			RAW1.CURRENT_PROCESS_STATUS = 11 or RAW1.CURRENT_PROCESS_STATUS = 6 or RAW1.CURRENT_PROCESS_STATUS = 15 or RAW1.CURRENT_PROCESS_STATUS = 30 or RAW1.CURRENT_PROCESS_STATUS = 8 or
			RAW1.CURRENT_PROCESS_STATUS = 18 then 1
			when RAW1.CURRENT_PROCESS_STATUS = 2 then 2
			when RAW1.CURRENT_PROCESS_STATUS = 19 then 3
			when RAW1.CURRENT_PROCESS_STATUS = 20 then 4
			when RAW1.CURRENT_PROCESS_STATUS = 5 then 5
			else 6
			end""";

	/** The values bound to {@link #GET_NYEHMS_DOCUMENT_CURRENT_STATUS}, in order. */
	static Object[] getNyehmsDocumentCurrentStatusArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("obxObservationResultKey")
		};
	}

	/** The SQL of {@code updatePIDUpdatedDetailsInRaw}, as the former mapper had it. */
	static final String UPDATE_PIDUPDATED_DETAILS_IN_RAW = """
			update ${RAW_TABLE} set CURRENT_PROCESS_STATUS = ?1, PIDADDRESSLINE1 = ?2, PIDADDRESSLINE2 = ?3, PIDCITY = ?4,
			PIDSTATE = ?5, PIDZIPCODE = ?6, PIDUPDATEDATE = ?7, PIDUPDATEID = ?8,
			hash_unique_number = ORA_HASH (TO_CHAR(SUBSTR(TO_CLOB(mshsendingfacilityname|| mshsendingfacilityclia|| mshprocessingid|| mshversionid|| mshlabpfi|| mshfilecontenttype|| mshsourcetype|| pidsetid|| pidpatientidexternal|| pidfacilityidexternal|| pidpatientidinternal|| pidfacilityidinternal|| pidpatientidalt|| pidfacilityidalt|| pidlastname|| pidfirstname|| pidmiddlename|| pidnamesuffix|| piddateofbirthraw|| piddateofbirth|| pidsexcode|| pidracecode|| pidraceasian|| pidraceblack|| pidracenativeameralaskan|| pidraceother|| pidracepacific|| pidraceunknown|| pidracewhite|| pidracecoderaw|| ?9|| ?10|| ?11|| ?12|| ?13|| pidcountrycode|| pidcountycodeoriginal|| pidhomephone|| pidworkphone|| pidpatientlanguage|| pidmaritalstatuscode|| pidpatientaccountnum|| pidssn|| pidmothersid|| pidethnicgroupcode|| piddeathdateraw|| piddeathdate|| piddeathind|| pidcountygazcodeorig|| pidmedicaidnumber|| piddin|| pidnysid|| pidusbornind|| pidcountryoforigin|| pidlengthofresidency|| pidtypeofinsurance|| pidvaccinetrialind|| pidcountycode|| pidsmsa|| pidlongitude|| pidlatitude|| pidnonhumanind|| obrplacersordernum|| obraccessionnum|| obrsnomedcode|| obrloinccode|| obrlocalcode|| obranalysisdesc|| obrcollectiondateraw|| obrcollectiondate|| obrspecimensourcecode|| obrspecimensourcename|| obrproviderid|| obrproviderlastname|| obrproviderfirstname|| obrprovidermiddlename|| obrprovidernamesuffix|| obrcallbackphone|| obrcallbackphonealt|| obrpathologistid|| obrpathologistlastname|| obrpathologistfirstname|| obrpathologistmiddlename|| obrpathologistnamesuffix|| obrfillersordernum|| obrobservationdateraw|| obrobservationdate|| obrreceiveddateraw|| obrreceiveddate|| obxvaluetype|| obxloinccode|| obxloincdesc|| obxloincdescshort|| obxsnomedcode|| obxsnomeddesc|| obxobservationresulttextshort|| obxunitofmeasure|| obxreferencerange|| obxabnormalflag|| obxobservationdateraw|| obxobservationdate|| obxproducercliaid|| obxproducerlabname|| obxobservationmethod|| obxprogramcode|| obxdisease|| obxlocaltestcode|| obxlocaltestdesc|| obxlocalresultcode|| obxlocalresultdesc|| obxsendingfacilityclia|| obxcountycode|| obxanalysisdateraw|| obxanalysisdate|| zlrprovideraddressline1|| zlrprovideraddressline2|| zlrprovidercity|| zlrproviderstate|| zlrproviderzipcode|| zlrprovidercountry|| zlrfacilitycode|| zlrfacilityname|| zlrfacilityaddressline1|| zlrfacilityaddressline2|| zlrfacilitycity|| zlrfacilitystate|| zlrfacilityzipcode|| zlrfacilitycountry|| zlrfacilityphone|| zlrage|| zlrnextofkinlastname|| zlrnextofkinfirstname|| zlrnextofkinmiddlename|| zlrnextofkinnamesuffix|| zlrnextofkinrelationship|| zlrnextofkinaddressline1|| zlrnextofkinaddressline2|| zlrnextofkincity|| zlrnextofkinzipcode|| zlrnextofkincountry|| zlrnextofkinphone|| pidmedicalrecordnumber)||obxobservationresulttext||obrrelevantclinicalinfo, 0,4000)))
			where DOCUMENT_ID = ?14""";

	/** The values bound to {@link #UPDATE_PIDUPDATED_DETAILS_IN_RAW}, in order. */
	static Object[] updatePIDUpdatedDetailsInRawArgs(Map<?, ?> p) {
		return new Object[] {
			number(p.get("CURRENT_PROCESS_STATUS")),
			text(p.get("PIDADDRESSLINE1")),
			text(p.get("PIDADDRESSLINE2")),
			text(p.get("PIDCITY")),
			text(p.get("PIDSTATE")),
			text(p.get("PIDZIPCODE")),
			text(p.get("PIDUPDATEDATE")),
			text(p.get("PIDUPDATEID")),
			text(p.get("PIDADDRESSLINE1")),
			text(p.get("PIDADDRESSLINE2")),
			text(p.get("PIDCITY")),
			text(p.get("PIDSTATE")),
			text(p.get("PIDZIPCODE")),
			number(p.get("CURRENT_DOCUMENT_ID"))
		};
	}

	/** The SQL of {@code updatePIDUpdatedDetailsInStd}, as the former mapper had it. */
	static final String UPDATE_PIDUPDATED_DETAILS_IN_STD = """
			update ${STD_TABLE} set CURRENT_PROCESS_STATUS = ?1, PERSON_ADDRESS = pkg_standardization.fn_improve_std_address(?2), PERSON_CITY = ?3,
			PERSON_STATE = ?4, PERSON_ZIP = ?5, PERSON_COUNTY = ?6 where DOCUMENT_ID = ?7""";

	/** The values bound to {@link #UPDATE_PIDUPDATED_DETAILS_IN_STD}, in order. */
	static Object[] updatePIDUpdatedDetailsInStdArgs(Map<?, ?> p) {
		return new Object[] {
			number(p.get("CURRENT_PROCESS_STATUS")),
			text(p.get("PERSON_ADDRESS")),
			text(p.get("PERSON_CITY")),
			text(p.get("PERSON_STATE")),
			text(p.get("PERSON_ZIP")),
			text(p.get("PERSON_COUNTY")),
			number(p.get("CURRENT_DOCUMENT_ID"))
		};
	}

	/** The SQL of {@code createNewRawHIVDocumentWithUpdatedPIDData}, as the former mapper had it. */
	static final String CREATE_NEW_RAW_HIVDOCUMENT_WITH_UPDATED_PIDDATA = """
			insert into RAW_HIV_ECLRS_DOC(DOCUMENT_ID, MSHMESSAGEMASTERKEY, MSHSENDINGFACILITYNAME, MSHSENDINGFACILITYCLIA, MSHMESSAGEDATERAW, MSHMESSAGEDATE, MSHMESSAGECONTROLID, MSHPROCESSINGID, MSHVERSIONID, MSHLABPFI, MSHCREATEDATE, MSHFILENAME, MSHFILECONTENTTYPE, MSHSOURCETYPE, MSHDELETEDIND, MSHDELETEDATE, PIDPATIENTKEY, PIDMESSAGEMASTERKEY, PIDSETID, PIDCREATEDATE, PIDPATIENTIDEXTERNAL, PIDFACILITYIDEXTERNAL, PIDPATIENTIDINTERNAL, PIDFACILITYIDINTERNAL, PIDPATIENTIDALT, PIDFACILITYIDALT, PIDLASTNAME, PIDFIRSTNAME, PIDMIDDLENAME, PIDNAMESUFFIX, PIDDATEOFBIRTHRAW, PIDDATEOFBIRTH, PIDSEXCODE, PIDRACECODERAW, PIDRACECODE, PIDRACEASIAN, PIDRACEBLACK, PIDRACENATIVEAMERALASKAN, PIDRACEOTHER, PIDRACEPACIFIC, PIDRACEUNKNOWN, PIDRACEWHITE, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, PIDCOUNTRYCODE, PIDCOUNTYCODEORIGINAL, PIDHOMEPHONE, PIDWORKPHONE, PIDPATIENTLANGUAGE, PIDMARITALSTATUSCODE, PIDPATIENTACCOUNTNUM, PIDSSN, PIDMOTHERSID, PIDETHNICGROUPCODE, PIDDEATHDATERAW, PIDDEATHDATE, PIDDEATHIND, PIDCOUNTYGAZCODEORIG, PIDMEDICAIDNUMBER, PIDDIN, PIDUSBORNIND, PIDCOUNTRYOFORIGIN, PIDLENGTHOFRESIDENCY, PIDTYPEOFINSURANCE, PIDVACCINETRIALIND, PIDNOTE, PIDCOUNTYCODE, PIDSMSA, PIDLONGITUDE, PIDLATITUDE, PIDNONHUMANIND, PIDUPDATEID, PIDUPDATEDATE, PIDDELETEDIND, PIDDELETEDATE, ZLRAGE, ZLRNEXTOFKINLASTNAME, ZLRNEXTOFKINFIRSTNAME, ZLRNEXTOFKINMIDDLENAME, ZLRNEXTOFKINNAMESUFFIX, ZLRNEXTOFKINRELATIONSHIP, ZLRNEXTOFKINADDRESSLINE1, ZLRNEXTOFKINADDRESSLINE2, ZLRNEXTOFKINCITY, ZLRNEXTOFKINSTATE, ZLRNEXTOFKINZIPCODE, ZLRNEXTOFKINCOUNTRY, ZLRNEXTOFKINPHONE, PIDMEDICALRECORDNUMBER, PIDASSIGNAUTHOID, PIDASSIGNAUTHDESC, PIDNYSID, PIDEMAIL, PIDCELLPHONE, OBROBSERVATIONREQUESTKEY, ZLROBSERVATIONREQUESTKEY, OBRPATIENTKEY, OBRSETID, OBRCREATEDATE, OBRPLACERSORDERNUM, OBRACCESSIONNUM, OBRSNOMEDCODE, OBRLOINCCODE, OBRLOCALCODE, OBRANALYSISDESC, OBRCOLLECTIONDATERAW, OBRCOLLECTIONDATE, OBRRELEVANTCLINICALINFO, OBRSPECIMENSOURCECODE, OBRSPECIMENSOURCENAME, OBRPROVIDERID, OBRPROVIDERLASTNAME, OBRPROVIDERFIRSTNAME, OBRPROVIDERMIDDLENAME, OBRPROVIDERNAMESUFFIX, OBRCALLBACKPHONE, OBRCALLBACKPHONEALT, OBRRESULTREPORTDATERAW, OBRRESULTREPORTDATE, OBRRESULTSTATUSCODE, OBRPATHOLOGISTID, OBRPATHOLOGISTLASTNAME, OBRPATHOLOGISTFIRSTNAME, OBRPATHOLOGISTMIDDLENAME, OBRPATHOLOGISTNAMESUFFIX, OBRNOTE, OBRDELETEDIND, OBRDELETEDATE, OBRPATIENTKEYORIG, OBRPROVIDERIDTYPECODE, OBRINITRESLOINCCODE, OBRINITRESLOINCDESC, OBRINITRESLOCALTESTCODE, OBRINITRESLOCALTESTDESC, OBRINITRESSUBID, OBRINITRESKEY, OBRINITRESOBSERVATIONTEXT, OBRINITRESPLACERSORDERNUM, OBRINITRESACCESSIONNUM, OBRFILLERSORDERNUM, OBROBSERVATIONDATERAW, OBROBSERVATIONDATE, OBRRECEIVEDDATERAW, OBRRECEIVEDDATE, ZLRPROVIDERADDRESSLINE1, ZLRPROVIDERADDRESSLINE2, ZLRPROVIDERCITY, ZLRPROVIDERSTATE, ZLRPROVIDERZIPCODE, ZLRPROVIDERCOUNTRY, ZLRFACILITYCODE, ZLRFACILITYNAME, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2, ZLRFACILITYCITY, ZLRFACILITYSTATE, ZLRFACILITYZIPCODE, ZLRFACILITYCOUNTRY, ZLRFACILITYPHONE, OBRPROVIDERIDALT1, OBRPROVIDERIDTYPECODEALT1, OBRPROVIDERIDALT2, OBRPROVIDERIDTYPECODEALT2, OBRPROVIDERFAX, OBRPROVIDEREMAIL, OBXOBSERVATIONRESULTKEY, OBXOBSERVATIONREQUESTKEY, OBXSETID, OBXVALUETYPE, OBXLOINCCODE, OBXLOINCDESC, OBXLOINCDESCSHORT, OBXOBSERVATIONSUBID, OBXSNOMEDCODE, OBXSNOMEDDESC, OBXOBSERVATIONRESULTTEXT, OBXOBSERVATIONRESULTTEXTSHORT, OBXUNITOFMEASURE, OBXREFERENCERANGE, OBXABNORMALFLAG, OBXRESULTSTATUSCODE, OBXOBSERVATIONDATERAW, OBXOBSERVATIONDATE, OBXPRODUCERCLIAID, OBXPRODUCERLABNAME, OBXOBSERVATIONMETHOD, OBXPROGRAMCODE, OBXNOTE, OBXDISEASE, OBXDISEASEGROUP, OBXPROCESSEDIND, OBXPROCESSEDDATE, OBXLOGONID, OBXLEADPURPOSE, OBXDELETEDIND, OBXDELETEDATE, PROGRAMCODEUPDATEDATE, PROGRAMCODEUPDATEID, OBXPROGRAMCODEUPDATEDATE, OBXPROGRAMCODEUPDATEID, OBXLOCALTESTCODE, OBXLOCALTESTDESC, OBXLOCALRESULTCODE, OBXLOCALRESULTDESC, OBXSENDINGFACILITYCLIA, OBXCREATEDATE, OBXCOUNTYCODE, OBXOLDESTDUPLICATERESULTKEY, OBXANALYSISDATERAW, OBXANALYSISDATE, OBXICD_CODETYPE, OBXICD_DESC, OBXICD_CODE, INTERPRETATION_STD_DOC_ID, CURRENT_PROCESS_STATUS, HASH_UNIQUE_NUMBER, PROCESS_DATE, NYC_SHIP_DATE, REVIEW_UID, REVIEW_DATE, CREATE_UID, CREATE_DATE)
			select ?1, MSHMESSAGEMASTERKEY, MSHSENDINGFACILITYNAME, MSHSENDINGFACILITYCLIA, MSHMESSAGEDATERAW, MSHMESSAGEDATE, MSHMESSAGECONTROLID, MSHPROCESSINGID, MSHVERSIONID, MSHLABPFI, MSHCREATEDATE, MSHFILENAME, MSHFILECONTENTTYPE, MSHSOURCETYPE, MSHDELETEDIND, MSHDELETEDATE, PIDPATIENTKEY, PIDMESSAGEMASTERKEY, PIDSETID, PIDCREATEDATE, PIDPATIENTIDEXTERNAL, PIDFACILITYIDEXTERNAL, PIDPATIENTIDINTERNAL, PIDFACILITYIDINTERNAL, PIDPATIENTIDALT, PIDFACILITYIDALT, PIDLASTNAME, PIDFIRSTNAME, PIDMIDDLENAME, PIDNAMESUFFIX, PIDDATEOFBIRTHRAW, PIDDATEOFBIRTH, PIDSEXCODE, PIDRACECODERAW, PIDRACECODE, PIDRACEASIAN, PIDRACEBLACK, PIDRACENATIVEAMERALASKAN, PIDRACEOTHER, PIDRACEPACIFIC, PIDRACEUNKNOWN, PIDRACEWHITE, ?2, ?3, ?4, ?5, ?6, PIDCOUNTRYCODE, PIDCOUNTYCODEORIGINAL, PIDHOMEPHONE, PIDWORKPHONE, PIDPATIENTLANGUAGE, PIDMARITALSTATUSCODE, PIDPATIENTACCOUNTNUM, PIDSSN, PIDMOTHERSID, PIDETHNICGROUPCODE, PIDDEATHDATERAW, PIDDEATHDATE, PIDDEATHIND, PIDCOUNTYGAZCODEORIG, PIDMEDICAIDNUMBER, PIDDIN, PIDUSBORNIND, PIDCOUNTRYOFORIGIN, PIDLENGTHOFRESIDENCY, PIDTYPEOFINSURANCE, PIDVACCINETRIALIND, PIDNOTE, PIDCOUNTYCODE, PIDSMSA, PIDLONGITUDE, PIDLATITUDE, PIDNONHUMANIND, ?7, ?8, PIDDELETEDIND, PIDDELETEDATE, ZLRAGE, ZLRNEXTOFKINLASTNAME, ZLRNEXTOFKINFIRSTNAME, ZLRNEXTOFKINMIDDLENAME, ZLRNEXTOFKINNAMESUFFIX, ZLRNEXTOFKINRELATIONSHIP, ZLRNEXTOFKINADDRESSLINE1, ZLRNEXTOFKINADDRESSLINE2, ZLRNEXTOFKINCITY, ZLRNEXTOFKINSTATE, ZLRNEXTOFKINZIPCODE, ZLRNEXTOFKINCOUNTRY, ZLRNEXTOFKINPHONE, PIDMEDICALRECORDNUMBER, PIDASSIGNAUTHOID, PIDASSIGNAUTHDESC, PIDNYSID, PIDEMAIL, PIDCELLPHONE, OBROBSERVATIONREQUESTKEY, ZLROBSERVATIONREQUESTKEY, OBRPATIENTKEY, OBRSETID, OBRCREATEDATE, OBRPLACERSORDERNUM, OBRACCESSIONNUM, OBRSNOMEDCODE, OBRLOINCCODE, OBRLOCALCODE, OBRANALYSISDESC, OBRCOLLECTIONDATERAW, OBRCOLLECTIONDATE, OBRRELEVANTCLINICALINFO, OBRSPECIMENSOURCECODE, OBRSPECIMENSOURCENAME, OBRPROVIDERID, OBRPROVIDERLASTNAME, OBRPROVIDERFIRSTNAME, OBRPROVIDERMIDDLENAME, OBRPROVIDERNAMESUFFIX, OBRCALLBACKPHONE, OBRCALLBACKPHONEALT, OBRRESULTREPORTDATERAW, OBRRESULTREPORTDATE, OBRRESULTSTATUSCODE, OBRPATHOLOGISTID, OBRPATHOLOGISTLASTNAME, OBRPATHOLOGISTFIRSTNAME, OBRPATHOLOGISTMIDDLENAME, OBRPATHOLOGISTNAMESUFFIX, OBRNOTE, OBRDELETEDIND, OBRDELETEDATE, OBRPATIENTKEYORIG, OBRPROVIDERIDTYPECODE, OBRINITRESLOINCCODE, OBRINITRESLOINCDESC, OBRINITRESLOCALTESTCODE, OBRINITRESLOCALTESTDESC, OBRINITRESSUBID, OBRINITRESKEY, OBRINITRESOBSERVATIONTEXT, OBRINITRESPLACERSORDERNUM, OBRINITRESACCESSIONNUM, OBRFILLERSORDERNUM, OBROBSERVATIONDATERAW, OBROBSERVATIONDATE, OBRRECEIVEDDATERAW, OBRRECEIVEDDATE, ZLRPROVIDERADDRESSLINE1, ZLRPROVIDERADDRESSLINE2, ZLRPROVIDERCITY, ZLRPROVIDERSTATE, ZLRPROVIDERZIPCODE, ZLRPROVIDERCOUNTRY, ZLRFACILITYCODE, ZLRFACILITYNAME, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2, ZLRFACILITYCITY, ZLRFACILITYSTATE, ZLRFACILITYZIPCODE, ZLRFACILITYCOUNTRY, ZLRFACILITYPHONE, OBRPROVIDERIDALT1, OBRPROVIDERIDTYPECODEALT1, OBRPROVIDERIDALT2, OBRPROVIDERIDTYPECODEALT2, OBRPROVIDERFAX, OBRPROVIDEREMAIL, OBXOBSERVATIONRESULTKEY, OBXOBSERVATIONREQUESTKEY, OBXSETID, OBXVALUETYPE, OBXLOINCCODE, OBXLOINCDESC, OBXLOINCDESCSHORT, OBXOBSERVATIONSUBID, OBXSNOMEDCODE, OBXSNOMEDDESC, OBXOBSERVATIONRESULTTEXT, OBXOBSERVATIONRESULTTEXTSHORT, OBXUNITOFMEASURE, OBXREFERENCERANGE, OBXABNORMALFLAG, OBXRESULTSTATUSCODE, OBXOBSERVATIONDATERAW, OBXOBSERVATIONDATE, OBXPRODUCERCLIAID, OBXPRODUCERLABNAME, OBXOBSERVATIONMETHOD, OBXPROGRAMCODE, OBXNOTE, OBXDISEASE, OBXDISEASEGROUP, OBXPROCESSEDIND, OBXPROCESSEDDATE, OBXLOGONID, OBXLEADPURPOSE, OBXDELETEDIND, OBXDELETEDATE, PROGRAMCODEUPDATEDATE, PROGRAMCODEUPDATEID, OBXPROGRAMCODEUPDATEDATE, OBXPROGRAMCODEUPDATEID, OBXLOCALTESTCODE, OBXLOCALTESTDESC, OBXLOCALRESULTCODE, OBXLOCALRESULTDESC, OBXSENDINGFACILITYCLIA, OBXCREATEDATE, OBXCOUNTYCODE, OBXOLDESTDUPLICATERESULTKEY, OBXANALYSISDATERAW, OBXANALYSISDATE, OBXICD_CODETYPE, OBXICD_DESC, OBXICD_CODE, INTERPRETATION_STD_DOC_ID, CURRENT_PROCESS_STATUS,
			ORA_HASH (TO_CHAR(SUBSTR(TO_CLOB(mshsendingfacilityname|| mshsendingfacilityclia|| mshprocessingid|| mshversionid|| mshlabpfi|| mshfilecontenttype|| mshsourcetype|| pidsetid|| pidpatientidexternal|| pidfacilityidexternal|| pidpatientidinternal|| pidfacilityidinternal|| pidpatientidalt|| pidfacilityidalt|| pidlastname|| pidfirstname|| pidmiddlename|| pidnamesuffix|| piddateofbirthraw|| piddateofbirth|| pidsexcode|| pidracecode|| pidraceasian|| pidraceblack|| pidracenativeameralaskan|| pidraceother|| pidracepacific|| pidraceunknown|| pidracewhite|| pidracecoderaw|| ?9|| ?10|| ?11|| ?12|| ?13|| pidcountrycode|| pidcountycodeoriginal|| pidhomephone|| pidworkphone|| pidpatientlanguage|| pidmaritalstatuscode|| pidpatientaccountnum|| pidssn|| pidmothersid|| pidethnicgroupcode|| piddeathdateraw|| piddeathdate|| piddeathind|| pidcountygazcodeorig|| pidmedicaidnumber|| piddin|| pidnysid|| pidusbornind|| pidcountryoforigin|| pidlengthofresidency|| pidtypeofinsurance|| pidvaccinetrialind|| pidcountycode|| pidsmsa|| pidlongitude|| pidlatitude|| pidnonhumanind|| obrplacersordernum|| obraccessionnum|| obrsnomedcode|| obrloinccode|| obrlocalcode|| obranalysisdesc|| obrcollectiondateraw|| obrcollectiondate|| obrspecimensourcecode|| obrspecimensourcename|| obrproviderid|| obrproviderlastname|| obrproviderfirstname|| obrprovidermiddlename|| obrprovidernamesuffix|| obrcallbackphone|| obrcallbackphonealt|| obrpathologistid|| obrpathologistlastname|| obrpathologistfirstname|| obrpathologistmiddlename|| obrpathologistnamesuffix|| obrfillersordernum|| obrobservationdateraw|| obrobservationdate|| obrreceiveddateraw|| obrreceiveddate|| obxvaluetype|| obxloinccode|| obxloincdesc|| obxloincdescshort|| obxsnomedcode|| obxsnomeddesc|| obxobservationresulttextshort|| obxunitofmeasure|| obxreferencerange|| obxabnormalflag|| obxobservationdateraw|| obxobservationdate|| obxproducercliaid|| obxproducerlabname|| obxobservationmethod|| obxprogramcode|| obxdisease|| obxlocaltestcode|| obxlocaltestdesc|| obxlocalresultcode|| obxlocalresultdesc|| obxsendingfacilityclia|| obxcountycode|| obxanalysisdateraw|| obxanalysisdate|| zlrprovideraddressline1|| zlrprovideraddressline2|| zlrprovidercity|| zlrproviderstate|| zlrproviderzipcode|| zlrprovidercountry|| zlrfacilitycode|| zlrfacilityname|| zlrfacilityaddressline1|| zlrfacilityaddressline2|| zlrfacilitycity|| zlrfacilitystate|| zlrfacilityzipcode|| zlrfacilitycountry|| zlrfacilityphone|| zlrage|| zlrnextofkinlastname|| zlrnextofkinfirstname|| zlrnextofkinmiddlename|| zlrnextofkinnamesuffix|| zlrnextofkinrelationship|| zlrnextofkinaddressline1|| zlrnextofkinaddressline2|| zlrnextofkincity|| zlrnextofkinzipcode|| zlrnextofkincountry|| zlrnextofkinphone|| pidmedicalrecordnumber)||obxobservationresulttext||obrrelevantclinicalinfo, 0,4000)))
			, PROCESS_DATE, NYC_SHIP_DATE, REVIEW_UID, REVIEW_DATE, '900', sysdate from RAW_HIV_ECLRS_DOC where DOCUMENT_ID = ?14""";

	/** The values bound to {@link #CREATE_NEW_RAW_HIVDOCUMENT_WITH_UPDATED_PIDDATA}, in order. */
	static Object[] createNewRawHIVDocumentWithUpdatedPIDDataArgs(Map<?, ?> p) {
		return new Object[] {
			number(p.get("NEW_DOCUMENT_ID")),
			text(p.get("PIDADDRESSLINE1")),
			text(p.get("PIDADDRESSLINE2")),
			text(p.get("PIDCITY")),
			text(p.get("PIDSTATE")),
			text(p.get("PIDZIPCODE")),
			text(p.get("PIDUPDATEID")),
			text(p.get("PIDUPDATEDATE")),
			text(p.get("PIDADDRESSLINE1")),
			text(p.get("PIDADDRESSLINE2")),
			text(p.get("PIDCITY")),
			text(p.get("PIDSTATE")),
			text(p.get("PIDZIPCODE")),
			number(p.get("CURRENT_DOCUMENT_ID"))
		};
	}

	/** The SQL of {@code createNewStdHIVDocumentWithUpdatedPIDData}, as the former mapper had it. */
	static final String CREATE_NEW_STD_HIVDOCUMENT_WITH_UPDATED_PIDDATA = """
			insert into STD_HIV_ECLRS_DOC(DOCUMENT_ID, INTERPRET_CD, LAB_SRC_ID, FNAME, LNAME, MNAME, SUFFIX, DOB_TEXT, AGE, BIRTH_COUNTRY, PERSON_ADDRESS, PERSON_CITY, PERSON_STATE, PERSON_ZIP, PERSON_PLUS4, PERSON_COUNTY, PERSON_PHONE, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_CENSUS_TRACT, GENDER, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, ETHNICITY, MED_RECORD_NUMBER, INSURANCE_CD, INSURANCE_NUMBER, DIN, NYSID, SSN, PROV_LNAME, PROV_ADDRESS, PROV_CITY, PROV_STATE, PROV_ZIP, PROV_PLUS4, PROV_PHONE, PROV_COUNTY, PROV_LATITUDE, PROV_LONGITUDE, PROV_CENSUS_TRACT, FACILITY_NAME, FACILITY_ADDRESS, FACILITY_CITY, FACILITY_STATE, FACILITY_ZIP, FACILITY_PLUS4, FACILITY_COUNTY, FACILITY_PHONE, OBSERVATION_DATE, COLLECTION_DATE, RESULT_REPORT_DATE, LOINC, LOINC_DESC, TEST_RESULT, UNIT_MEASURE_CODE, PERSON_INTERNAL_ID, PERSON_EXTERNAL_ID, SENDING_FACILITY_NAME, SPECIMEN_SOURCE_CD, SPECIMEN_SOURCE_NAME, REFERRING_FACILITY, TESTING_FACILITY, EHARS_IMPORT_DATE, EHARS_STATUS_CD, MATCH_CD, DATE_RECEIVED, REVIEW_DATE, REVIEW_UID, STATENO, PROV_FNAME, PROV_MNAME, PROV_SUFFIX, HIV_VACCINE_RECIP, RESIDE_OUTSIDE_USA, AREA_OF_BIRTH, LENGTH_RESIDENCY_IN_USA, SENDING_FACILITY_CLIA, NYC_SHIP_DATE, PERSON_COUNTRY, ACCESSION_NUMBER, MEDICAID_NUMBER, LOCAL_TEST_CODE, LOCAL_TEST_DESC, FACILITY_CENSUS_TRACT, FACILITY_LATITUDE, FACILITY_LONGITUDE, CURRENT_PROCESS_STATUS, MATCHED_STATENO, MATCH_WEIGHT, MATCH_PASS, QS_PARENT_DOC_ID, AGE_CATEGORY, MATCHED_PROCESS_DATE, CALC_COLLECTION_DATE,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE, CREATE_UID, CREATE_DATE, PFI)
			select ?1, INTERPRET_CD, LAB_SRC_ID, FNAME, LNAME, MNAME, SUFFIX, DOB_TEXT, AGE, BIRTH_COUNTRY, pkg_standardization.fn_improve_std_address(?2), ?3, ?4, ?5, PERSON_PLUS4, ?6, PERSON_PHONE, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_CENSUS_TRACT, GENDER, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, ETHNICITY, MED_RECORD_NUMBER, INSURANCE_CD, INSURANCE_NUMBER, DIN, NYSID, SSN, PROV_LNAME, PROV_ADDRESS, PROV_CITY, PROV_STATE, PROV_ZIP, PROV_PLUS4, PROV_PHONE, PROV_COUNTY, PROV_LATITUDE, PROV_LONGITUDE, PROV_CENSUS_TRACT, FACILITY_NAME, FACILITY_ADDRESS, FACILITY_CITY, FACILITY_STATE, FACILITY_ZIP, FACILITY_PLUS4, FACILITY_COUNTY, FACILITY_PHONE, OBSERVATION_DATE, COLLECTION_DATE, RESULT_REPORT_DATE, LOINC, LOINC_DESC, TEST_RESULT, UNIT_MEASURE_CODE, PERSON_INTERNAL_ID, PERSON_EXTERNAL_ID, SENDING_FACILITY_NAME, SPECIMEN_SOURCE_CD, SPECIMEN_SOURCE_NAME, REFERRING_FACILITY, TESTING_FACILITY, EHARS_IMPORT_DATE, EHARS_STATUS_CD, MATCH_CD, DATE_RECEIVED, REVIEW_DATE, REVIEW_UID, STATENO, PROV_FNAME, PROV_MNAME, PROV_SUFFIX, HIV_VACCINE_RECIP, RESIDE_OUTSIDE_USA, AREA_OF_BIRTH, LENGTH_RESIDENCY_IN_USA, SENDING_FACILITY_CLIA, NYC_SHIP_DATE, PERSON_COUNTRY, ACCESSION_NUMBER, MEDICAID_NUMBER, LOCAL_TEST_CODE, LOCAL_TEST_DESC, FACILITY_CENSUS_TRACT, FACILITY_LATITUDE, FACILITY_LONGITUDE, CURRENT_PROCESS_STATUS, MATCHED_STATENO, MATCH_WEIGHT, MATCH_PASS, QS_PARENT_DOC_ID, AGE_CATEGORY, MATCHED_PROCESS_DATE, CALC_COLLECTION_DATE, FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE, '900', sysdate, PFI from STD_HIV_ECLRS_DOC where DOCUMENT_ID = ?7""";

	/** The values bound to {@link #CREATE_NEW_STD_HIVDOCUMENT_WITH_UPDATED_PIDDATA}, in order. */
	static Object[] createNewStdHIVDocumentWithUpdatedPIDDataArgs(Map<?, ?> p) {
		return new Object[] {
			number(p.get("NEW_DOCUMENT_ID")),
			text(p.get("PERSON_ADDRESS")),
			text(p.get("PERSON_CITY")),
			text(p.get("PERSON_STATE")),
			text(p.get("PERSON_ZIP")),
			text(p.get("PERSON_COUNTY")),
			number(p.get("CURRENT_DOCUMENT_ID"))
		};
	}

	/** The SQL of {@code createNewRawSTDDocumentWithUpdatedPIDData}, as the former mapper had it. */
	static final String CREATE_NEW_RAW_STDDOCUMENT_WITH_UPDATED_PIDDATA = """
			insert into RAW_STD_ECLRS_DOC(DOCUMENT_ID, MSHMESSAGEMASTERKEY, MSHSENDINGFACILITYNAME, MSHSENDINGFACILITYCLIA, MSHMESSAGEDATERAW, MSHMESSAGEDATE, MSHMESSAGECONTROLID, MSHPROCESSINGID, MSHVERSIONID, MSHLABPFI, MSHCREATEDATE, MSHFILENAME, MSHFILECONTENTTYPE, MSHSOURCETYPE, MSHDELETEDIND, MSHDELETEDATE, PIDPATIENTKEY, PIDMESSAGEMASTERKEY, PIDSETID, PIDCREATEDATE, PIDPATIENTIDEXTERNAL, PIDFACILITYIDEXTERNAL, PIDPATIENTIDINTERNAL, PIDFACILITYIDINTERNAL, PIDPATIENTIDALT, PIDFACILITYIDALT, PIDLASTNAME, PIDFIRSTNAME, PIDMIDDLENAME, PIDNAMESUFFIX, PIDDATEOFBIRTHRAW, PIDDATEOFBIRTH, PIDSEXCODE, PIDRACECODERAW, PIDRACECODE, PIDRACEASIAN, PIDRACEBLACK, PIDRACENATIVEAMERALASKAN, PIDRACEOTHER, PIDRACEPACIFIC, PIDRACEUNKNOWN, PIDRACEWHITE, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, PIDCOUNTRYCODE, PIDCOUNTYCODEORIGINAL, PIDHOMEPHONE, PIDWORKPHONE, PIDPATIENTLANGUAGE, PIDMARITALSTATUSCODE, PIDPATIENTACCOUNTNUM, PIDSSN, PIDMOTHERSID, PIDETHNICGROUPCODE, PIDDEATHDATERAW, PIDDEATHDATE, PIDDEATHIND, PIDCOUNTYGAZCODEORIG, PIDMEDICAIDNUMBER, PIDDIN, PIDUSBORNIND, PIDCOUNTRYOFORIGIN, PIDLENGTHOFRESIDENCY, PIDTYPEOFINSURANCE, PIDVACCINETRIALIND, PIDNOTE, PIDCOUNTYCODE, PIDSMSA, PIDLONGITUDE, PIDLATITUDE, PIDNONHUMANIND, PIDUPDATEID, PIDUPDATEDATE, PIDDELETEDIND, PIDDELETEDATE, ZLRAGE, ZLRNEXTOFKINLASTNAME, ZLRNEXTOFKINFIRSTNAME, ZLRNEXTOFKINMIDDLENAME, ZLRNEXTOFKINNAMESUFFIX, ZLRNEXTOFKINRELATIONSHIP, ZLRNEXTOFKINADDRESSLINE1, ZLRNEXTOFKINADDRESSLINE2, ZLRNEXTOFKINCITY, ZLRNEXTOFKINSTATE, ZLRNEXTOFKINZIPCODE, ZLRNEXTOFKINCOUNTRY, ZLRNEXTOFKINPHONE, PIDMEDICALRECORDNUMBER, PIDASSIGNAUTHOID, PIDASSIGNAUTHDESC, PIDNYSID, PIDEMAIL, PIDCELLPHONE, OBROBSERVATIONREQUESTKEY, ZLROBSERVATIONREQUESTKEY, OBRPATIENTKEY, OBRSETID, OBRCREATEDATE, OBRPLACERSORDERNUM, OBRACCESSIONNUM, OBRSNOMEDCODE, OBRLOINCCODE, OBRLOCALCODE, OBRANALYSISDESC, OBRCOLLECTIONDATERAW, OBRCOLLECTIONDATE, OBRRELEVANTCLINICALINFO, OBRSPECIMENSOURCECODE, OBRSPECIMENSOURCENAME, OBRSPECIMENDESC, OBRPROVIDERID, OBRPROVIDERLASTNAME, OBRPROVIDERFIRSTNAME, OBRPROVIDERMIDDLENAME, OBRPROVIDERNAMESUFFIX, OBRCALLBACKPHONE, OBRCALLBACKPHONEALT, OBRRESULTREPORTDATERAW, OBRRESULTREPORTDATE, OBRRESULTSTATUSCODE, OBRPATHOLOGISTID, OBRPATHOLOGISTLASTNAME, OBRPATHOLOGISTFIRSTNAME, OBRPATHOLOGISTMIDDLENAME, OBRPATHOLOGISTNAMESUFFIX, OBRNOTE, OBRDELETEDIND, OBRDELETEDATE, OBRPATIENTKEYORIG, OBRPROVIDERIDTYPECODE, OBRINITRESLOINCCODE, OBRINITRESLOINCDESC, OBRINITRESLOCALTESTCODE, OBRINITRESLOCALTESTDESC, OBRINITRESSUBID, OBRINITRESKEY, OBRINITRESOBSERVATIONTEXT, OBRINITRESPLACERSORDERNUM, OBRINITRESACCESSIONNUM, OBRFILLERSORDERNUM, OBROBSERVATIONDATERAW, OBROBSERVATIONDATE, OBRRECEIVEDDATERAW, OBRRECEIVEDDATE, ZLRPROVIDERADDRESSLINE1, ZLRPROVIDERADDRESSLINE2, ZLRPROVIDERCITY, ZLRPROVIDERSTATE, ZLRPROVIDERZIPCODE, ZLRPROVIDERCOUNTRY, ZLRFACILITYCODE, ZLRFACILITYNAME, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2, ZLRFACILITYCITY, ZLRFACILITYSTATE, ZLRFACILITYZIPCODE, ZLRFACILITYCOUNTRY, ZLRFACILITYPHONE, OBRPROVIDERIDALT1, OBRPROVIDERIDTYPECODEALT1, OBRPROVIDERIDALT2, OBRPROVIDERIDTYPECODEALT2, OBRPROVIDERFAX, OBRPROVIDEREMAIL, OBXOBSERVATIONRESULTKEY, OBXOBSERVATIONREQUESTKEY, OBXSETID, OBXVALUETYPE, OBXLOINCCODE, OBXLOINCDESC, OBXLOINCDESCSHORT, OBXOBSERVATIONSUBID, OBXSNOMEDCODE, OBXSNOMEDDESC, OBXOBSERVATIONRESULTTEXT, OBXOBSERVATIONRESULTTEXTSHORT, OBXUNITOFMEASURE, OBXREFERENCERANGE, OBXABNORMALFLAG, OBXRESULTSTATUSCODE, OBXOBSERVATIONDATERAW, OBXOBSERVATIONDATE, OBXPRODUCERCLIAID, OBXPRODUCERLABNAME, OBXOBSERVATIONMETHOD, OBXPROGRAMCODE, OBXNOTE, OBXDISEASE, OBXDISEASEGROUP, OBXPROCESSEDIND, OBXPROCESSEDDATE, OBXLOGONID, OBXLEADPURPOSE, OBXDELETEDIND, OBXDELETEDATE, PROGRAMCODEUPDATEDATE, PROGRAMCODEUPDATEID, OBXPROGRAMCODEUPDATEDATE, OBXPROGRAMCODEUPDATEID, OBXLOCALTESTCODE, OBXLOCALTESTDESC, OBXLOCALRESULTCODE, OBXLOCALRESULTDESC, OBXSENDINGFACILITYCLIA, OBXCREATEDATE, OBXCOUNTYCODE, OBXOLDESTDUPLICATERESULTKEY, OBXANALYSISDATERAW, OBXANALYSISDATE, OBXICD_CODETYPE, OBXICD_DESC, OBXICD_CODE, CDESSMAPPEDVALUES, MAPPABLEIND, REASONNOTMAPPED, CLASS, TESTTYPE, RESULT, SPECIMENSOURCE, CURRENT_PROCESS_STATUS, HASH_UNIQUE_NUMBER, PROCESS_DATE, CREATE_UID, CREATE_DATE)
			select ?1, MSHMESSAGEMASTERKEY, MSHSENDINGFACILITYNAME, MSHSENDINGFACILITYCLIA, MSHMESSAGEDATERAW, MSHMESSAGEDATE, MSHMESSAGECONTROLID, MSHPROCESSINGID, MSHVERSIONID, MSHLABPFI, MSHCREATEDATE, MSHFILENAME, MSHFILECONTENTTYPE, MSHSOURCETYPE, MSHDELETEDIND, MSHDELETEDATE, PIDPATIENTKEY, PIDMESSAGEMASTERKEY, PIDSETID, PIDCREATEDATE, PIDPATIENTIDEXTERNAL, PIDFACILITYIDEXTERNAL, PIDPATIENTIDINTERNAL, PIDFACILITYIDINTERNAL, PIDPATIENTIDALT, PIDFACILITYIDALT, PIDLASTNAME, PIDFIRSTNAME, PIDMIDDLENAME, PIDNAMESUFFIX, PIDDATEOFBIRTHRAW, PIDDATEOFBIRTH, PIDSEXCODE, PIDRACECODERAW, PIDRACECODE, PIDRACEASIAN, PIDRACEBLACK, PIDRACENATIVEAMERALASKAN, PIDRACEOTHER, PIDRACEPACIFIC, PIDRACEUNKNOWN, PIDRACEWHITE, ?2, ?3, ?4, ?5, ?6, PIDCOUNTRYCODE, PIDCOUNTYCODEORIGINAL, PIDHOMEPHONE, PIDWORKPHONE, PIDPATIENTLANGUAGE, PIDMARITALSTATUSCODE, PIDPATIENTACCOUNTNUM, PIDSSN, PIDMOTHERSID, PIDETHNICGROUPCODE, PIDDEATHDATERAW, PIDDEATHDATE, PIDDEATHIND, PIDCOUNTYGAZCODEORIG, PIDMEDICAIDNUMBER, PIDDIN, PIDUSBORNIND, PIDCOUNTRYOFORIGIN, PIDLENGTHOFRESIDENCY, PIDTYPEOFINSURANCE, PIDVACCINETRIALIND, PIDNOTE, PIDCOUNTYCODE, PIDSMSA, PIDLONGITUDE, PIDLATITUDE, PIDNONHUMANIND, ?7, ?8, PIDDELETEDIND, PIDDELETEDATE, ZLRAGE, ZLRNEXTOFKINLASTNAME, ZLRNEXTOFKINFIRSTNAME, ZLRNEXTOFKINMIDDLENAME, ZLRNEXTOFKINNAMESUFFIX, ZLRNEXTOFKINRELATIONSHIP, ZLRNEXTOFKINADDRESSLINE1, ZLRNEXTOFKINADDRESSLINE2, ZLRNEXTOFKINCITY, ZLRNEXTOFKINSTATE, ZLRNEXTOFKINZIPCODE, ZLRNEXTOFKINCOUNTRY, ZLRNEXTOFKINPHONE, PIDMEDICALRECORDNUMBER, PIDASSIGNAUTHOID, PIDASSIGNAUTHDESC, PIDNYSID, PIDEMAIL, PIDCELLPHONE, OBROBSERVATIONREQUESTKEY, ZLROBSERVATIONREQUESTKEY, OBRPATIENTKEY, OBRSETID, OBRCREATEDATE, OBRPLACERSORDERNUM, OBRACCESSIONNUM, OBRSNOMEDCODE, OBRLOINCCODE, OBRLOCALCODE, OBRANALYSISDESC, OBRCOLLECTIONDATERAW, OBRCOLLECTIONDATE, OBRRELEVANTCLINICALINFO, OBRSPECIMENSOURCECODE, OBRSPECIMENSOURCENAME, OBRSPECIMENDESC, OBRPROVIDERID, OBRPROVIDERLASTNAME, OBRPROVIDERFIRSTNAME, OBRPROVIDERMIDDLENAME, OBRPROVIDERNAMESUFFIX, OBRCALLBACKPHONE, OBRCALLBACKPHONEALT, OBRRESULTREPORTDATERAW, OBRRESULTREPORTDATE, OBRRESULTSTATUSCODE, OBRPATHOLOGISTID, OBRPATHOLOGISTLASTNAME, OBRPATHOLOGISTFIRSTNAME, OBRPATHOLOGISTMIDDLENAME, OBRPATHOLOGISTNAMESUFFIX, OBRNOTE, OBRDELETEDIND, OBRDELETEDATE, OBRPATIENTKEYORIG, OBRPROVIDERIDTYPECODE, OBRINITRESLOINCCODE, OBRINITRESLOINCDESC, OBRINITRESLOCALTESTCODE, OBRINITRESLOCALTESTDESC, OBRINITRESSUBID, OBRINITRESKEY, OBRINITRESOBSERVATIONTEXT, OBRINITRESPLACERSORDERNUM, OBRINITRESACCESSIONNUM, OBRFILLERSORDERNUM, OBROBSERVATIONDATERAW, OBROBSERVATIONDATE, OBRRECEIVEDDATERAW, OBRRECEIVEDDATE, ZLRPROVIDERADDRESSLINE1, ZLRPROVIDERADDRESSLINE2, ZLRPROVIDERCITY, ZLRPROVIDERSTATE, ZLRPROVIDERZIPCODE, ZLRPROVIDERCOUNTRY, ZLRFACILITYCODE, ZLRFACILITYNAME, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2, ZLRFACILITYCITY, ZLRFACILITYSTATE, ZLRFACILITYZIPCODE, ZLRFACILITYCOUNTRY, ZLRFACILITYPHONE, OBRPROVIDERIDALT1, OBRPROVIDERIDTYPECODEALT1, OBRPROVIDERIDALT2, OBRPROVIDERIDTYPECODEALT2, OBRPROVIDERFAX, OBRPROVIDEREMAIL, OBXOBSERVATIONRESULTKEY, OBXOBSERVATIONREQUESTKEY, OBXSETID, OBXVALUETYPE, OBXLOINCCODE, OBXLOINCDESC, OBXLOINCDESCSHORT, OBXOBSERVATIONSUBID, OBXSNOMEDCODE, OBXSNOMEDDESC, OBXOBSERVATIONRESULTTEXT, OBXOBSERVATIONRESULTTEXTSHORT, OBXUNITOFMEASURE, OBXREFERENCERANGE, OBXABNORMALFLAG, OBXRESULTSTATUSCODE, OBXOBSERVATIONDATERAW, OBXOBSERVATIONDATE, OBXPRODUCERCLIAID, OBXPRODUCERLABNAME, OBXOBSERVATIONMETHOD, OBXPROGRAMCODE, OBXNOTE, OBXDISEASE, OBXDISEASEGROUP, OBXPROCESSEDIND, OBXPROCESSEDDATE, OBXLOGONID, OBXLEADPURPOSE, OBXDELETEDIND, OBXDELETEDATE, PROGRAMCODEUPDATEDATE, PROGRAMCODEUPDATEID, OBXPROGRAMCODEUPDATEDATE, OBXPROGRAMCODEUPDATEID, OBXLOCALTESTCODE, OBXLOCALTESTDESC, OBXLOCALRESULTCODE, OBXLOCALRESULTDESC, OBXSENDINGFACILITYCLIA, OBXCREATEDATE, OBXCOUNTYCODE, OBXOLDESTDUPLICATERESULTKEY, OBXANALYSISDATERAW, OBXANALYSISDATE, OBXICD_CODETYPE, OBXICD_DESC, OBXICD_CODE, CDESSMAPPEDVALUES, MAPPABLEIND, REASONNOTMAPPED, CLASS, TESTTYPE, RESULT, SPECIMENSOURCE, CURRENT_PROCESS_STATUS,
			ORA_HASH (TO_CHAR(SUBSTR(TO_CLOB(mshsendingfacilityname|| mshsendingfacilityclia|| mshprocessingid|| mshversionid|| mshlabpfi|| mshfilecontenttype|| mshsourcetype|| pidsetid|| pidpatientidexternal|| pidfacilityidexternal|| pidpatientidinternal|| pidfacilityidinternal|| pidpatientidalt|| pidfacilityidalt|| pidlastname|| pidfirstname|| pidmiddlename|| pidnamesuffix|| piddateofbirthraw|| piddateofbirth|| pidsexcode|| pidracecode|| pidraceasian|| pidraceblack|| pidracenativeameralaskan|| pidraceother|| pidracepacific|| pidraceunknown|| pidracewhite|| pidracecoderaw|| ?9|| ?10|| ?11|| ?12|| ?13|| pidcountrycode|| pidcountycodeoriginal|| pidhomephone|| pidworkphone|| pidpatientlanguage|| pidmaritalstatuscode|| pidpatientaccountnum|| pidssn|| pidmothersid|| pidethnicgroupcode|| piddeathdateraw|| piddeathdate|| piddeathind|| pidcountygazcodeorig|| pidmedicaidnumber|| piddin|| pidnysid|| pidusbornind|| pidcountryoforigin|| pidlengthofresidency|| pidtypeofinsurance|| pidvaccinetrialind|| pidcountycode|| pidsmsa|| pidlongitude|| pidlatitude|| pidnonhumanind|| obrplacersordernum|| obraccessionnum|| obrsnomedcode|| obrloinccode|| obrlocalcode|| obranalysisdesc|| obrcollectiondateraw|| obrcollectiondate|| obrspecimensourcecode|| obrspecimensourcename|| obrproviderid|| obrproviderlastname|| obrproviderfirstname|| obrprovidermiddlename|| obrprovidernamesuffix|| obrcallbackphone|| obrcallbackphonealt|| obrpathologistid|| obrpathologistlastname|| obrpathologistfirstname|| obrpathologistmiddlename|| obrpathologistnamesuffix|| obrfillersordernum|| obrobservationdateraw|| obrobservationdate|| obrreceiveddateraw|| obrreceiveddate|| obxvaluetype|| obxloinccode|| obxloincdesc|| obxloincdescshort|| obxsnomedcode|| obxsnomeddesc|| obxobservationresulttextshort|| obxunitofmeasure|| obxreferencerange|| obxabnormalflag|| obxobservationdateraw|| obxobservationdate|| obxproducercliaid|| obxproducerlabname|| obxobservationmethod|| obxprogramcode|| obxdisease|| obxlocaltestcode|| obxlocaltestdesc|| obxlocalresultcode|| obxlocalresultdesc|| obxsendingfacilityclia|| obxcountycode|| obxanalysisdateraw|| obxanalysisdate|| zlrprovideraddressline1|| zlrprovideraddressline2|| zlrprovidercity|| zlrproviderstate|| zlrproviderzipcode|| zlrprovidercountry|| zlrfacilitycode|| zlrfacilityname|| zlrfacilityaddressline1|| zlrfacilityaddressline2|| zlrfacilitycity|| zlrfacilitystate|| zlrfacilityzipcode|| zlrfacilitycountry|| zlrfacilityphone|| zlrage|| zlrnextofkinlastname|| zlrnextofkinfirstname|| zlrnextofkinmiddlename|| zlrnextofkinnamesuffix|| zlrnextofkinrelationship|| zlrnextofkinaddressline1|| zlrnextofkinaddressline2|| zlrnextofkincity|| zlrnextofkinzipcode|| zlrnextofkincountry|| zlrnextofkinphone|| pidmedicalrecordnumber)||obxobservationresulttext||obrrelevantclinicalinfo, 0,4000)))
			, PROCESS_DATE, '900', sysdate from RAW_STD_ECLRS_DOC where DOCUMENT_ID = ?14""";

	/** The values bound to {@link #CREATE_NEW_RAW_STDDOCUMENT_WITH_UPDATED_PIDDATA}, in order. */
	static Object[] createNewRawSTDDocumentWithUpdatedPIDDataArgs(Map<?, ?> p) {
		return new Object[] {
			number(p.get("NEW_DOCUMENT_ID")),
			text(p.get("PIDADDRESSLINE1")),
			text(p.get("PIDADDRESSLINE2")),
			text(p.get("PIDCITY")),
			text(p.get("PIDSTATE")),
			text(p.get("PIDZIPCODE")),
			text(p.get("PIDUPDATEID")),
			text(p.get("PIDUPDATEDATE")),
			text(p.get("PIDADDRESSLINE1")),
			text(p.get("PIDADDRESSLINE2")),
			text(p.get("PIDCITY")),
			text(p.get("PIDSTATE")),
			text(p.get("PIDZIPCODE")),
			number(p.get("CURRENT_DOCUMENT_ID"))
		};
	}

	/** The SQL of {@code createNewStdStdDocumentWithUpdatedPIDData}, as the former mapper had it. */
	static final String CREATE_NEW_STD_STD_DOCUMENT_WITH_UPDATED_PIDDATA = """
			insert into STD_STD_ECLRS_DOC(DOCUMENT_ID, LAB_SRC_ID, FNAME, MNAME, LNAME, SUFFIX, DOB_TEXT, AGE, DIN, NYSID, SSN, BIRTH_COUNTRY, PERSON_ADDRESS, PERSON_CITY, PERSON_STATE, PERSON_ZIP, PERSON_PLUS4, PERSON_COUNTY, PERSON_PHONE, PERSON_COUNTRY, PERSON_CENSUS_TRACT, PERSON_CENSUS_TRACT_SUFFIX, PERSON_LATITUDE, PERSON_LONGITUDE, GENDER, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, ETHNICITY, INSURANCE_CD, INSURANCE_NUMBER, RISK01, RISK02, RISK03, RISK04, RISK05, RISK06, RISK07, RISK08, RISK09, RISK10, RISK11, RISK12, RISK13, RISK14, RISK15, RISK16, PROV_FNAME, PROV_MNAME, PROV_LNAME, PROV_SUFFIX, PROV_ADDRESS, PROV_CITY, PROV_STATE, PROV_ZIP, PROV_PLUS4, PROV_PHONE, PROV_COUNTY, PROV_CENSUS_TRACT, PROV_CENSUS_TRACT_SUFFIX, PROV_LATITUDE, PROV_LONGITUDE, FACILITY_NAME, FACILITY_ADDRESS, FACILITY_CITY, FACILITY_STATE, FACILITY_ZIP, FACILITY_PLUS4, FACILITY_COUNTY, FACILITY_PHONE, FACILITY_CENSUS_TRACT, FACILITY_CENSUS_TRACT_SUFFIX, FACILITY_LATITUDE, FACILITY_LONGITUDE, DRAW_DATE, OBSERVATION_DATE, COLLECTION_DATE, RESULT_REPORT_DATE, LOINC, LOINC_DESC, TEST_RESULT, INTERPRET_CD, UNIT_MEASURE_CODE, PERSON_INTERNAL_ID, PERSON_EXTERNAL_ID, SENDING_FACILITY_NAME, SPECIMEN_SOURCE_CD, SPECIMEN_SOURCE_NAME, REFERRING_FACILITY, TESTING_FACILITY, CASEKIND, CRBATCHNUM, PROCESSED, PROVIDER_TYPE, RECB, TEST_RESULT_RAW, HIV_VACCINE_RECIP, OTHER_IMMUNE_INFECTION, RESIDE_OUTSIDE_USA, AREA_OF_BIRTH, LAST_NEG_HIV_TEST_DATE, LENGTH_RESIDENCY_IN_USA, SENDING_FACILITY_CLIA, DATE_SENT, ACCESSION_NUMBER, MEDICAID_NUMBER, LOCAL_TEST_CODE, LOCAL_TEST_DESC, REVIEW_UID, REVIEW_DATE, DATE_RECEIVED, CREATE_UID, CREATE_DATE, EHARS_STATUS_CD, EHARS_IMPORT_DATE, NYC_SHIP_DATE, AGE_CATEGORY, CURRENT_PROCESS_STATUS, QS_PARENT_DOC_ID, MATCH_CD, MATCH_WEIGHT, MATCH_PASS, MATCHED_STATENO, MED_RECORD_NUMBER, MATCHED_PROCESS_DATE, CALC_COLLECTION_DATE, PREVIOUS_MATCHED_STATENO,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			select ?1, LAB_SRC_ID, FNAME, MNAME, LNAME, SUFFIX, DOB_TEXT, AGE, DIN, NYSID, SSN, BIRTH_COUNTRY, pkg_standardization.fn_improve_std_address(?2), ?3, ?4, ?5, PERSON_PLUS4, ?6, PERSON_PHONE, PERSON_COUNTRY, PERSON_CENSUS_TRACT, PERSON_CENSUS_TRACT_SUFFIX, PERSON_LATITUDE, PERSON_LONGITUDE, GENDER, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, ETHNICITY, INSURANCE_CD, INSURANCE_NUMBER, RISK01, RISK02, RISK03, RISK04, RISK05, RISK06, RISK07, RISK08, RISK09, RISK10, RISK11, RISK12, RISK13, RISK14, RISK15, RISK16, PROV_FNAME, PROV_MNAME, PROV_LNAME, PROV_SUFFIX, PROV_ADDRESS, PROV_CITY, PROV_STATE, PROV_ZIP, PROV_PLUS4, PROV_PHONE, PROV_COUNTY, PROV_CENSUS_TRACT, PROV_CENSUS_TRACT_SUFFIX, PROV_LATITUDE, PROV_LONGITUDE, FACILITY_NAME, FACILITY_ADDRESS, FACILITY_CITY, FACILITY_STATE, FACILITY_ZIP, FACILITY_PLUS4, FACILITY_COUNTY, FACILITY_PHONE, FACILITY_CENSUS_TRACT, FACILITY_CENSUS_TRACT_SUFFIX, FACILITY_LATITUDE, FACILITY_LONGITUDE, DRAW_DATE, OBSERVATION_DATE, COLLECTION_DATE, RESULT_REPORT_DATE, LOINC, LOINC_DESC, TEST_RESULT, INTERPRET_CD, UNIT_MEASURE_CODE, PERSON_INTERNAL_ID, PERSON_EXTERNAL_ID, SENDING_FACILITY_NAME, SPECIMEN_SOURCE_CD, SPECIMEN_SOURCE_NAME, REFERRING_FACILITY, TESTING_FACILITY, CASEKIND, CRBATCHNUM, PROCESSED, PROVIDER_TYPE, RECB, TEST_RESULT_RAW, HIV_VACCINE_RECIP, OTHER_IMMUNE_INFECTION, RESIDE_OUTSIDE_USA, AREA_OF_BIRTH, LAST_NEG_HIV_TEST_DATE, LENGTH_RESIDENCY_IN_USA, SENDING_FACILITY_CLIA, DATE_SENT, ACCESSION_NUMBER, MEDICAID_NUMBER, LOCAL_TEST_CODE, LOCAL_TEST_DESC, REVIEW_UID, REVIEW_DATE, DATE_RECEIVED, '900', sysdate, EHARS_STATUS_CD, EHARS_IMPORT_DATE, NYC_SHIP_DATE, AGE_CATEGORY, CURRENT_PROCESS_STATUS, QS_PARENT_DOC_ID, MATCH_CD, MATCH_WEIGHT, MATCH_PASS, MATCHED_STATENO, MED_RECORD_NUMBER, MATCHED_PROCESS_DATE, CALC_COLLECTION_DATE, PREVIOUS_MATCHED_STATENO, FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE from STD_STD_ECLRS_DOC where DOCUMENT_ID = ?7""";

	/** The values bound to {@link #CREATE_NEW_STD_STD_DOCUMENT_WITH_UPDATED_PIDDATA}, in order. */
	static Object[] createNewStdStdDocumentWithUpdatedPIDDataArgs(Map<?, ?> p) {
		return new Object[] {
			number(p.get("NEW_DOCUMENT_ID")),
			text(p.get("PERSON_ADDRESS")),
			text(p.get("PERSON_CITY")),
			text(p.get("PERSON_STATE")),
			text(p.get("PERSON_ZIP")),
			text(p.get("PERSON_COUNTY")),
			number(p.get("CURRENT_DOCUMENT_ID"))
		};
	}

	/** The SQL of {@code checkPersonAddressGotEdited}, as the former mapper had it. */
	static final String CHECK_PERSON_ADDRESS_GOT_EDITED = """
			select 'Y' from ${STD_TABLE} A, ${STD_TABLE} B where A.DOCUMENT_ID = ?1 and B.DOCUMENT_ID = ?2
			and A.PERSON_ADDRESS = B.PERSON_ADDRESS and A.PERSON_CITY = B.PERSON_CITY
			and A.PERSON_STATE = B.PERSON_STATE
			and A.PERSON_ZIP = B.PERSON_ZIP""";

	/** The values bound to {@link #CHECK_PERSON_ADDRESS_GOT_EDITED}, in order. */
	static Object[] checkPersonAddressGotEditedArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("OLD_DOCUMENT_ID"),
			p.get("NEW_DOCUMENT_ID")
		};
	}

	/** The SQL of {@code checkIfDocumentUpdatedWithPidDetails}, as the former mapper had it. */
	static final String CHECK_IF_DOCUMENT_UPDATED_WITH_PID_DETAILS = """
			select count(*) from ${RAW_TABLE} where OBXOBSERVATIONRESULTKEY = ?1 and PIDUPDATEDATE is not null""";

	/** The values bound to {@link #CHECK_IF_DOCUMENT_UPDATED_WITH_PID_DETAILS}, in order. */
	static Object[] checkIfDocumentUpdatedWithPidDetailsArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("obxObservationResultKey")
		};
	}

	/** The SQL of {@code checkDuplicatesinStdDocTable}, as the former mapper had it. */
	static final String CHECK_DUPLICATESIN_STD_DOC_TABLE = """
			UPDATE ${STD_TABLE}
			SET CURRENT_PROCESS_STATUS =  38
			WHERE ROWID IN (SELECT rwid
			FROM (SELECT S.ROWID rwid,
			m.flux_match_cd,
			ROW_NUMBER ()
			OVER (
			PARTITION BY S.document_id,
			M.SSN,
			M.hash_number
			ORDER BY m.document_id)
			rwnum,
			COUNT (
			*)
			OVER (
			PARTITION BY S.document_id,
			M.SSN,
			M.hash_number)
			cnt
			FROM ${STD_TABLE} S, ${STD_TABLE} M
			WHERE     s.CURRENT_PROCESS_STATUS = 5
			AND M.ROWID != s.ROWID
			AND M.SSN = s.SSN
			AND M.hash_number = s.hash_number)
			WHERE flux_match_cd IN ('M', 'C_M', 'P_M', 'N_M', 'M_M') AND rwnum = cnt)""";

	/** The SQL of {@code addFileNameToAssignmentReportsTable}, as the former mapper had it. */
	static final String ADD_FILE_NAME_TO_ASSIGNMENT_REPORTS_TABLE = """
			insert into coded_display_file_name(FILE_NAME, DOCUMENT_TYPE_CODE, DISPLAY_NAME, LAST_UPDATED_BY, LAST_UPDATED_DATE)
			select ${fileName}, ?1, ${fileName}, '900', SYSDATE from
			(select distinct ${fileName} from ${stdTableName} std where current_process_STatus = 5 and not exists (select 1 from coded_display_file_name where document_type_Code = ?2
			and FILE_NAME = std.${fileName}))""";

	/** The values bound to {@link #ADD_FILE_NAME_TO_ASSIGNMENT_REPORTS_TABLE}, in order. */
	static Object[] addFileNameToAssignmentReportsTableArgs(Map<?, ?> p) {
		return new Object[] {
			p.get("documentType"),
			p.get("documentType")
		};
	}

	/** The SQL of {@code getDataFluxQSMatchSummary}, as the former mapper had it. */
	static final String GET_DATA_FLUX_QSMATCH_SUMMARY = """
			select DOC_TYPE, MATCH_CD, to_char(count(*)) DATA_FLUX from nyehms_sasdb_owner.er_person_clusters_Stage group by DOC_TYPE, MATCH_CD order by DOC_TYPE, MATCH_CD""";

	/** The SQL of {@code getDataFluxDetailedStatus}, as the former mapper had it. */
	static final String GET_DATA_FLUX_DETAILED_STATUS = """
			WITH data AS
			(
			SELECT job_id
			, To_Char(job_exec_start_dtm, 'MM/DD/YYYY HH:MI:SS  AM') exec_start_date
			, To_Char(job_exec_end_dtm, 'MM/DD/YYYY HH:MI:SS  AM') exec_end_date
			,error_Desc
			FROM nyehms_sasdb_owner.er_audit_job_exec job_exec
			WHERE cycle_id =   (
			SELECT Max(cycle_id)
			FROM nyehms_sasdb_owner.er_audit_cycle where CYCLE_TYPE = ?1
			)
			)
			SELECT Distinct a.job_num, a.job_type, a.job_id, a.job_desc, b.exec_start_date, b.exec_end_date, nvl(b.error_Desc, '') error_Desc
			, CASE
			WHEN exec_end_date IS NOT NULL
			THEN 'PASSED'
			ELSE 'FAILED'
			END status
			FROM nyehms_sasdb_owner.er_audit_job a, data b
			WHERE a.job_id = b.job_id(+)
			and a.job_type like '%'|| ?2 ||'%'
			ORDER BY a.job_num, b.exec_start_date""";

	/** The values bound to {@link #GET_DATA_FLUX_DETAILED_STATUS}, in order. */
	static Object[] getDataFluxDetailedStatusArgs(String p) {
		return new Object[] {
			p,
			p
		};
	}

	/** The SQL of {@code getAssignmentDetailedStatus}, as the former mapper had it. */
	static final String GET_ASSIGNMENT_DETAILED_STATUS = """
			SELECT Nvl(r.region_description, 'TOTAL') region, COUNT(*) assignments
			FROM surveillance_assignment s, coded_region r
			WHERE Trunc(s.create_date) = (select trunc(JOB_EXEC_START_DTM) from nyehms_sasdb_owner.ER_AUDIT_JOB_EXEC where cycle_id = (SELECT Max(cycle_id)
			FROM nyehms_sasdb_owner.er_audit_cycle where CYCLE_TYPE = 'ER_CYCLE') and JOB_ID = 'PROCESS_ASSIGNMENTS')
			AND s.current_region_cd = r.region_code
			GROUP BY ROLLUP(r.region_description)""";

	/** The SQL of {@code getAhiReportList}, as the former mapper had it. */
	static final String GET_AHI_REPORT_LIST = """
			SELECT s.stateno, TRIGGERING_DOC_ID as "TRIGGERING DOC ID" , R.REGION_DESCRIPTION REGION , CASE WHEN EXTRACT(DAY FROM  s.CREATE_DATE )  = EXTRACT(DAY FROM AHI_UPDATE_DATE ) THEN 'NEW' ELSE 'CHANGED TO AHI '  END  as "AHI STATUS",
			nu.FIRST_NAME || ' '|| nu.LAST_NAME  as "WORKER ASSINGNED TO",
			nu2.FIRST_NAME || ' '|| nu2.LAST_NAME as "SUPERVISOR WHO ASSIGNED"
			FROM SURVEILLANCE_ASSIGNMENT s , coded_region R , WORKER_ASSIGNMENT W , USER_REGION U, NYEHMS_USERS NU , NYEHMS_USERS NU2
			WHERE   s.AHI_UPDATE_DATE  >  (select trunc(JOB_EXEC_START_DTM) from nyehms_sasdb_owner.ER_AUDIT_JOB_EXEC where cycle_id = (SELECT Max(cycle_id)
			FROM nyehms_sasdb_owner.er_audit_cycle where CYCLE_TYPE = 'ER_CYCLE') and JOB_ID = 'PROCESS_ASSIGNMENTS')
			AND      r.REGION_CODE =S.CURRENT_REGION_CD
			AND  CURRENT_WORKER_ASSIGNMENT_ID = W.WORKER_ASSIGNMENT_ID (+)
			AND W.WORKER_REGION_ID =u.WORKER_REGION_ID (+)
			AND U.WORKER_ID =Nu.worker_id (+)
			AND W.ASSIGNED_BY = Nu2.worker_id (+)
			ORDER BY s.CREATE_DATE""";

	/** The SQL of {@code getPSMessageReport}, as the former mapper had it. */
	static final String GET_PSMESSAGE_REPORT = """
			SELECT Nvl(trigger_msg_type,'Total') "Message Type", COUNT(*) "Count"
			FROM nyehms_std_message_sent
			WHERE Trunc(create_date) = (select trunc(JOB_EXEC_START_DTM) from nyehms_sasdb_owner.ER_AUDIT_JOB_EXEC where cycle_id = (SELECT Max(cycle_id)
			FROM nyehms_sasdb_owner.er_audit_cycle where CYCLE_TYPE = 'ER_CYCLE') and JOB_ID = 'PROCESS_PS_MESSAGE')
			GROUP BY ROLLUP(trigger_msg_type)""";

	/** The SQL of {@code getPSMessageNYCReport}, as the former mapper had it. */
	static final String GET_PSMESSAGE_NYCREPORT = """
			SELECT a.trigger_doc_id "TRIGGERING DOC ID", a.trigger_msg_type "MESSAGE TYPE", a.stateno "STATENO", c.county_name "COUNTY"
			FROM nyehms_std_message_sent a, std_hiv_eclrs_doc s, coded_county c
			WHERE a.trigger_doc_id = s.document_id
			AND Trunc(a.message_entered_date) = (select trunc(JOB_EXEC_START_DTM) from nyehms_sasdb_owner.ER_AUDIT_JOB_EXEC where cycle_id = (SELECT Max(cycle_id)
			FROM nyehms_sasdb_owner.er_audit_cycle where CYCLE_TYPE = 'ER_CYCLE') and JOB_ID = 'PROCESS_PS_MESSAGE')
			AND Nvl(s.person_county, '999') NOT IN ('005', '047', '061', '081', '085', '998', '999')
			AND (Nvl(s.prov_county, '999') IN ('005', '047', '061', '081', '085') OR Nvl(s.facility_county, '999') IN ('005', '047', '061', '081', '085'))
			AND a.process_ind != 'R'
			AND s.person_county = c.county_fips_code
			UNION ALL
			SELECT a.trigger_doc_id "TRIGGERING DOC ID", a.trigger_msg_type "MESSAGE TYPE", a.stateno "STATENO", c.county_name "COUNTY"
			FROM nyehms_std_message_sent a, std_prf_doc s, coded_county c
			WHERE a.trigger_doc_id = s.document_id
			AND Trunc(a.message_entered_date) = (select trunc(JOB_EXEC_START_DTM) from nyehms_sasdb_owner.ER_AUDIT_JOB_EXEC where cycle_id = (SELECT Max(cycle_id)
			FROM nyehms_sasdb_owner.er_audit_cycle where CYCLE_TYPE = 'ER_CYCLE') and JOB_ID = 'PROCESS_PS_MESSAGE')
			AND Nvl(s.person_county, '999') NOT IN ('005', '047', '061', '081', '085', '998', '999')
			AND Nvl(s.facility_county, '999') IN ('005', '047', '061', '081', '085')
			AND a.process_ind != 'R'
			AND s.person_county = c.county_fips_code
			UNION ALL
			SELECT a.trigger_doc_id "TRIGGERING DOC ID", a.trigger_msg_type "MESSAGE TYPE", a.stateno "STATENO", c.county_name "COUNTY"
			FROM nyehms_std_message_sent a, std_nycijr_doc s, coded_county c
			WHERE a.trigger_doc_id = s.document_id
			AND Trunc(a.message_entered_date) = (select trunc(JOB_EXEC_START_DTM) from nyehms_sasdb_owner.ER_AUDIT_JOB_EXEC where cycle_id = (SELECT Max(cycle_id)
			FROM nyehms_sasdb_owner.er_audit_cycle where CYCLE_TYPE = 'ER_CYCLE') and JOB_ID = 'PROCESS_PS_MESSAGE')
			AND Nvl(s.cur_person_county, '999') NOT IN ('005', '047', '061', '081', '085', '998', '999')
			AND Nvl(s.prov_county, '999') IN ('005', '047', '061', '081', '085')
			AND a.process_ind != 'R'
			AND s.cur_person_county = c.county_fips_code""";
}
