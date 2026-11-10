package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.timestamp;

import java.util.Map;

import org.hibernate.type.StandardBasicTypes;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.LabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;

/**
 * The SQL of the {@link LabDocumentDAO} statements that stay native, taken unchanged from the former MyBatis mapper
 * ({@code #{...}} replaced by {@code ?1, ?2, ...}; generated from the mapper XML); why each one stays native is said at
 * its DAO method. {@code ${...}} names are left in the text: the DAO replaces them with allow-listed table names. The
 * {@code ...Args} methods give the values bound to a statement, in order, typed as the mapper's {@code jdbcType} bound
 * a null ({@code VARCHAR} and {@code CLOB} a text, {@code DECIMAL} a number, {@code DATE} a date, {@code TIMESTAMP} a
 * timestamp); a value that is not null is bound as it is, as MyBatis bound it by its Java type (a {@code java.util.Date}
 * with {@code jdbcType=DATE} as its day only, as MyBatis' DateOnlyTypeHandler did). A value without a jdbcType is bound
 * as it is.
 */
final class LabDocumentSql {

	private LabDocumentSql() {
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

	/** The SQL of {@code getLabDoc}, as the former mapper had it (1 bound values). */
	static final String GET_LAB_DOC = """
				SELECT STD1.DOCUMENT_ID,
			        STD1.LAB_SRC_ID,
			        STD1.FNAME,
			       STD1.LNAME,
			STD1.MNAME,
			STD1.SUFFIX,
			STD1.DOB_TEXT,
			STD1.AGE,
			STD1.BIRTH_COUNTRY,
			
			       	RAW1.PIDADDRESSLINE1 || ' ' || RAW1.PIDADDRESSLINE2
			          AS RAWPERSONADDRESS,
			
			
			       STD1.PERSON_CITY,
			       STD1.PERSON_STATE,
			       STD1.PERSON_ZIP,
			       STD1.PERSON_PLUS4,
			       STD1.PERSON_COUNTY,
			       STD1.PERSON_PHONE,
			       STD1.GENDER,
			       STD1.RACE_ASIAN,
			       STD1.RACE_BLACK,
			       STD1.RACE_INDIAN,
			       STD1.RACE_MULTIPLE,
			       STD1.RACE_OTHER,
			       STD1.RACE_PACIFIC,
			       STD1.RACE_UNKNOWN,
			       STD1.RACE_WHITE,
			       STD1.ETHNICITY,
			       STD1.MEDICAID_NUMBER,
			       STD1.MED_RECORD_NUMBER,
			       STD1.INSURANCE_CD,
			       STD1.INSURANCE_NUMBER,
			       STD1.DIN,
			       STD1.NYSID,
			       STD1.SSN,
			       STD1.PROV_LNAME,
			       STD1.PROV_FNAME,
			       STD1.PROV_MNAME,
			       STD1.PROV_SUFFIX,
			       STD1.PROV_NPI,
			       STD1.FACILITY_NPI,
			       RAW1.ZLRPROVIDERADDRESSLINE1 || ' ' || RAW1.ZLRPROVIDERADDRESSLINE2
			          AS RAWPROVADDRESS,
			       STD1.PROV_CITY,
			       STD1.PROV_STATE,
			       STD1.PROV_ZIP,
			       STD1.PROV_PLUS4,
			       STD1.PROV_PHONE,
			       STD1.PROV_COUNTY,
			       STD1.FACILITY_NAME,
			       RAW1.ZLRFACILITYADDRESSLINE1 || ' ' || RAW1.ZLRFACILITYADDRESSLINE2
			          AS RAWFACILITYADDRESS,
			       STD1.FACILITY_CITY,
			       STD1.FACILITY_STATE,
			       STD1.FACILITY_ZIP,
			       STD1.FACILITY_PLUS4,
			       STD1.FACILITY_COUNTY,
			       STD1.FACILITY_PHONE,
			       STD1.OBSERVATION_DATE,
			       STD1.COLLECTION_DATE,
			       STD1.RESULT_REPORT_DATE,
			       STD1.LOINC,
			       STD1.LOINC_DESC,
			       STD1.TEST_RESULT,
			       STD1.UNIT_MEASURE_CODE,
			       STD1.PERSON_INTERNAL_ID,
			       STD1.PERSON_EXTERNAL_ID,
			       STD1.SENDING_FACILITY_NAME,
			       STD1.SPECIMEN_SOURCE_CD,
			       STD1.SPECIMEN_SOURCE_NAME,
			       STD1.REFERRING_FACILITY,
			       STD1.TESTING_FACILITY,
			       STD1.PFI,
			       STD1.DATE_RECEIVED,
			       PDOC.STATENO,
			       PDOC.DISCARD_STATUS,
			       STD1.HIV_VACCINE_RECIP,
			       STD1.RESIDE_OUTSIDE_USA,
			       STD1.AREA_OF_BIRTH,
			       STD1.LENGTH_RESIDENCY_IN_USA,
			       STD1.SENDING_FACILITY_CLIA,
			       STD1.NYC_SHIP_DATE,
			       STD1.PERSON_COUNTRY,
			       STD1.ACCESSION_NUMBER,
			       STD1.MEDICAID_NUMBER,
			       STD1.LOCAL_TEST_CODE,
			       STD1.LOCAL_TEST_DESC,
			       STD1.FACILITY_ADDRESS,
			       STD1.PERSON_ADDRESS,
			       STD1.PROV_ADDRESS,
			       RAW1.MSHLABPFI PFI,
			       STD1.EHARS_IMPORT_DATE,
			       STD1.NYC_SHIP_DATE NYC_SHIP_DATE,
			       STD1.AGE_CATEGORY,
			STD1.CURRENT_PROCESS_STATUS,
			STD1.EHARS_STATUS_CD,
			STD1.FACILITY_CENSUS_TRACT,
			STD1.FACILITY_LATITUDE,
			STD1.FACILITY_LONGITUDE,
			STD1.MATCH_CD,
			STD1.INTERPRET_CD,
			STD1.MATCH_PASS,
			STD1.MATCH_WEIGHT,
			STD1.MATCHED_STATENO,
			STD1.PERSON_CENSUS_TRACT,
			STD1.PERSON_LATITUDE,
			STD1.PERSON_LONGITUDE,
			STD1.PROV_CENSUS_TRACT,
			STD1.PROV_LATITUDE,
			STD1.PROV_LONGITUDE,
			STD1.QS_PARENT_DOC_ID,
			STD1.REVIEW_DATE,
			STD1.REVIEW_UID,
			STD1.CALC_COLLECTION_DATE,
			RAW1.OBXOBSERVATIONRESULTTEXT RAW_OBXOBSERVATIONRESULTTEXT,
			RAW1.OBXNOTE RAW_OBXNOTE,
			D.PARENT_DOCUMENT_ID
			  FROM ${raw_table_name} RAW1
			       INNER JOIN ${std_table_name} STD1
			          ON (STD1.document_id = RAW1.document_id and  RAW1.DOCUMENT_ID=?1)
			       INNER JOIN document_locator D
			          ON raw1.document_id=d.document_id
			       LEFT OUTER JOIN person_doc_association PDOC
			          ON (RAW1.document_id = PDOC.document_id
			              AND PDOC.dissassociation_date IS NULL)
			""";

	/** The SQL of {@code getHivLabDoc}, as the former mapper had it (1 bound values). */
	static final String GET_HIV_LAB_DOC = """
			SELECT *
			               FROM
			                           RAW_HIV_ECLRS_DOC
			               WHERE
			                           DOCUMENT_ID=?1
			""";

	/** The SQL of {@code getRawDataAsObject}, as the former mapper had it (1 bound values). */
	static final String GET_RAW_DATA = """
			SELECT *
			               FROM
			                           RAW_HIV_ECLRS_DOC
			               WHERE
			                           DOCUMENT_ID=?1
			""";

	/** The SQL of {@code getStdDataAsObject}, as the former mapper had it (1 bound values). */
	static final String GET_STD_DATA = """
			SELECT *
			               FROM
			                           STD_HIV_ECLRS_DOC
			               WHERE
			                           DOCUMENT_ID=?1
			""";

	/** The SQL of {@code insertLabDocData}, as the former mapper had it (78 bound values). */
	static final String INSERT_LAB_DOC = """
			INSERT INTO RAW_LAB_DOC (
					  DOCUMENT_ID, LAB_SRC_ID, FNAME,
				      LNAME, MNAME, SUFFIX,
				      DOB_TEXT, AGE, SSN,
				      DIN, NYSID, MED_RECORD_NUMBER,
				      INSURANCE_CD, INSURANCE_NUMBER, ETHNICITY,
				      GENDER, RACE_ASIAN, RACE_BLACK,
				      RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER,
				      RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE,
				      BIRTH_COUNTRY, PERSON_ADDRESS, PERSON_CITY,
				      PERSON_STATE, PERSON_ZIP, PERSON_PLUS4,
				      PERSON_COUNTY, PERSON_PHONE, FACILITY_NAME,
				      FACILITY_ADDRESS, FACILITY_CITY, FACILITY_STATE,
				      FACILITY_ZIP, FACILITY_PLUS4, FACILITY_PHONE,
				      PROV_LNAME, PROV_ADDRESS, PROV_CITY,
				      PROV_STATE, PROV_ZIP, PROV_PLUS4,
				      PROV_PHONE, DATE_RECEIVED, DRAW_DATE,
				      COLLECTION_DATE, RESULT_REPORT_DATE, LOINC,
				      LOINC_DESC, TEST_RESULT, TEST_RESULT_RAW,
				      UNIT_MEASURE_CODE, REFERRING_FACILITY, SENDING_FACILITY_CLIA,
				      SPECIMEN_SOURCE_CD, SPECIMEN_SOURCE_NAME, TESTING_FACILITY,
				      HIV_VACCINE_RECIP, OTHER_IMMUNE_INFECTION,
				      RESIDE_OUTSIDE_USA, AREA_OF_BIRTH, LAST_NEG_HIV_TEST_DATE,
				      LENGTH_RESIDENCY_IN_USA, SENDING_FACILITY_NAME,
				      DATE_SENT, PERSON_EXTERNAL_ID, PERSON_INTERNAL_ID,
				      PERSON_COUNTRY, ACCESSION_NUMBER, PROV_FNAME,
				      PROV_MNAME, PROV_SUFFIX, MEDICAID_NUMBER,
				      LOCAL_TEST_CODE, LOCAL_TEST_DESC)
			  VALUES (
			  			  ?1, ?2, ?3,
				      ?4, ?5, ?6,
				      ?7, ?8, ?9,
				      ?10, ?11, ?12,
				      ?13, ?14, ?15,
				      ?16, ?17, ?18,
				      ?19, ?20, ?21,
				      ?22, ?23, ?24,
				      ?25, ?26, ?27,
				      ?28, ?29, ?30,
				      ?31, ?32, ?33,
				      ?34, ?35, ?36,
				      ?37, ?38, ?39,
				      ?40, ?41, ?42,
				      ?43, ?44, ?45,
				      ?46, TO_DATE(?47, 'MM/DD/YYYY'),
				      ?48,
				      ?49, ?50, ?51,
				      ?52, ?53, ?54,
				      ?55, ?56, ?57,
				      ?58, ?59, ?60,
				      ?61, ?62,
				      ?63, ?64, ?65,
				      ?66, ?67,
				      TO_DATE(?68, 'MM/DD/YYYY'), ?69, ?70,
				      ?71, ?72, ?73,
				      ?74, ?75, ?76,
				      ?77, ?78)
			""";

	/** The values of {@link #INSERT_LAB_DOC}, in order. */
	static Object[] insertLabDocDataArgs(LabDocVO vo) {
		return new Object[] {
				number(vo.getDocumentId()), number(vo.getLabSrcId()), text(vo.getFname()), text(vo.getLname()),
				text(vo.getMname()), text(vo.getSuffix()), text(vo.getDobText()), text(vo.getAge()),
				text(vo.getSsn()), text(vo.getDin()), text(vo.getNysid()), text(vo.getMedRecordNumber()),
				text(vo.getInsuranceCd()), text(vo.getInsuranceNumber()), text(vo.getEthnicity()), text(vo.getGender()),
				text(vo.isRaceAsian()), text(vo.isRaceBlack()), text(vo.isRaceIndian()), text(vo.isRaceMultiple()),
				text(vo.isRaceOther()), text(vo.isRacePacific()), text(vo.isRaceUnknown()), text(vo.isRaceWhite()),
				text(vo.getBirthCountry()), text(vo.getPersonAddress()), text(vo.getPersonCity()), text(vo.getPersonState()),
				text(vo.getPersonZip()), text(vo.getPersonPlus4()), text(vo.getPersonCounty()), text(vo.getPersonPhone()),
				text(vo.getFacilityName()), text(vo.getFacilityAddress()), text(vo.getFacilityCity()), text(vo.getFacilityState()),
				text(vo.getFacilityZip()), text(vo.getFacilityPlus4()), text(vo.getFacilityPhone()), text(vo.getProvLname()),
				text(vo.getProvAddress()), text(vo.getProvCity()), text(vo.getProvState()), text(vo.getProvZip()),
				text(vo.getProvPlus4()), text(vo.getProvPhone()), timestamp(vo.getDateReceived()), text(vo.getDrawDate()),
				text(vo.getCollectionDate()), text(vo.getResultReportDate()), text(vo.getLoinc()), text(vo.getLoincDesc()),
				text(vo.getTestResult()), text(vo.getTestResultRaw()), text(vo.getUnitMeasureCode()), text(vo.getReferringFacility()),
				text(vo.getSendingFacilityClia()), text(vo.getSpecimenSourceCd()), text(vo.getSpecimenSourceName()), text(vo.getTestingFacility()),
				text(vo.getHivVaccineRecip()), text(vo.getOtherImmuneInfection()), text(vo.getResideOutsideUsa()), text(vo.getAreaOfBirth()),
				text(vo.getLastNegHivTestDate()), text(vo.getLengthResidencyInUsa()), text(vo.getSendingFacilityName()), timestamp(vo.getDateSent()),
				text(vo.getPersonExternalId()), text(vo.getPersonInternalId()), text(vo.getPersonCountry()), text(vo.getAccessionNumber()),
				text(vo.getProvFname()), text(vo.getProvMname()), text(vo.getProvSuffix()), text(vo.getMedicaidNumber()),
				text(vo.getLocalTestCode()), text(vo.getLocalTestDesc())
		};
	}

	/** The SQL of {@code insertRawHivDocument}, as the former mapper had it (224 bound values). */
	static final String INSERT_RAW_HIV_DOCUMENT = """
			insert into RAW_HIV_ECLRS_DOC(MSHFILECONTENTTYPE, MSHDELETEDIND, PIDSEXCODE, PIDRACECODE, PIDRACEASIAN, PIDRACEBLACK, PIDRACENATIVEAMERALASKAN, PIDRACEOTHER, PIDRACEPACIFIC, PIDRACEUNKNOWN, PIDRACEWHITE, PIDMARITALSTATUSCODE, PIDDEATHIND, PIDUSBORNIND, PIDVACCINETRIALIND, PIDNONHUMANIND, PIDDELETEDIND, OBRRESULTSTATUSCODE, OBRDELETEDIND, OBXRESULTSTATUSCODE, OBXPROCESSEDIND, OBXLEADPURPOSE, OBXDELETEDIND, PIDSMSA, PIDNOTE, OBRRELEVANTCLINICALINFO, OBRNOTE, OBXOBSERVATIONRESULTTEXT, OBXNOTE, PROCESS_DATE, NYC_SHIP_DATE, PIDLONGITUDE, PIDLATITUDE, DOCUMENT_ID, INTERPRETATION_STD_DOC_ID, CURRENT_PROCESS_STATUS, HASH_UNIQUE_NUMBER, MSHMESSAGEMASTERKEY, PIDPATIENTKEY, PIDMESSAGEMASTERKEY, OBROBSERVATIONREQUESTKEY, ZLROBSERVATIONREQUESTKEY, OBRPATIENTKEY, OBRPATIENTKEYORIG, OBRINITRESKEY, OBXOBSERVATIONRESULTKEY, OBXOBSERVATIONREQUESTKEY, OBXOLDESTDUPLICATERESULTKEY, PIDCOUNTYCODE, OBXCOUNTYCODE, OBXDISEASE, OBXDISEASEGROUP, MSHMESSAGEDATE, MSHCREATEDATE, MSHDELETEDATE, PIDCREATEDATE, PIDDATEOFBIRTH, PIDDEATHDATE, PIDUPDATEDATE, PIDDELETEDATE, OBRCREATEDATE, OBRCOLLECTIONDATE, OBRRESULTREPORTDATE, OBRDELETEDATE, OBROBSERVATIONDATE, OBRRECEIVEDDATE, OBXOBSERVATIONDATE, OBXPROCESSEDDATE, OBXDELETEDATE, PROGRAMCODEUPDATEDATE, OBXPROGRAMCODEUPDATEDATE, OBXCREATEDATE, OBXANALYSISDATE, MSHSENDINGFACILITYCLIA, MSHLABPFI, PIDFACILITYIDALT, OBRLOINCCODE, OBRSPECIMENSOURCECODE, OBRPROVIDERIDTYPECODE, OBRINITRESLOINCCODE, OBRPROVIDERIDTYPECODEALT1, OBRPROVIDERIDTYPECODEALT2, OBXSETID, OBXLOINCCODE, OBXSNOMEDCODE, OBXSENDINGFACILITYCLIA, PIDASSIGNAUTHDESC, OBRPLACERSORDERNUM, OBRFILLERSORDERNUM, OBXREFERENCERANGE, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, PIDCOUNTRYCODE, ZLRNEXTOFKINADDRESSLINE1, ZLRNEXTOFKINADDRESSLINE2, ZLRNEXTOFKINCITY, ZLRNEXTOFKINSTATE, ZLRNEXTOFKINZIPCODE, ZLRNEXTOFKINCOUNTRY, PIDEMAIL, ZLRPROVIDERADDRESSLINE1, ZLRPROVIDERADDRESSLINE2, ZLRPROVIDERCITY, ZLRPROVIDERSTATE, ZLRPROVIDERZIPCODE, ZLRPROVIDERCOUNTRY, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2, ZLRFACILITYCITY, ZLRFACILITYSTATE, ZLRFACILITYZIPCODE, ZLRFACILITYCOUNTRY, OBRPROVIDEREMAIL, PIDNYSID, OBRSNOMEDCODE, MSHFILENAME, PIDSSN, MSHSENDINGFACILITYNAME, PIDCOUNTYGAZCODEORIG, PIDTYPEOFINSURANCE, MSHMESSAGECONTROLID, PIDPATIENTIDEXTERNAL, PIDPATIENTIDINTERNAL, PIDPATIENTIDALT, PIDPATIENTACCOUNTNUM, PIDMOTHERSID, PIDMEDICAIDNUMBER, ZLRAGE, OBRINITRESLOCALTESTCODE, OBRINITRESSUBID, OBXOBSERVATIONSUBID, OBXLOCALTESTCODE, OBXICD_CODETYPE, OBXICD_CODE, PIDASSIGNAUTHOID, OBRLOCALCODE, OBRANALYSISDESC, OBRPATHOLOGISTID, OBRPATHOLOGISTLASTNAME, OBRPATHOLOGISTFIRSTNAME, OBRPATHOLOGISTMIDDLENAME, OBRPATHOLOGISTNAMESUFFIX, OBRINITRESPLACERSORDERNUM, OBRINITRESACCESSIONNUM, OBRACCESSIONNUM, PIDCOUNTYCODEORIGINAL, PIDMEDICALRECORDNUMBER, PIDRACECODERAW, OBRSPECIMENSOURCENAME, OBRINITRESLOINCDESC, OBRINITRESLOCALTESTDESC, OBRINITRESOBSERVATIONTEXT, OBXLOINCDESC, OBXSNOMEDDESC, OBXLOCALTESTDESC, OBXLOCALRESULTDESC, OBXICD_DESC, MSHMESSAGEDATERAW, PIDDATEOFBIRTHRAW, PIDDEATHDATERAW, OBRCOLLECTIONDATERAW, OBRRESULTREPORTDATERAW, OBROBSERVATIONDATERAW, OBRRECEIVEDDATERAW, OBXOBSERVATIONDATERAW, OBXANALYSISDATERAW, MSHPROCESSINGID, MSHSOURCETYPE, PIDETHNICGROUPCODE, OBXVALUETYPE, PIDFACILITYIDEXTERNAL, PIDFACILITYIDINTERNAL, PIDCOUNTRYOFORIGIN, PIDUPDATEID, OBXLOGONID, PROGRAMCODEUPDATEID, OBXPROGRAMCODEUPDATEID, PIDSETID, OBRSETID, OBXPROGRAMCODE, PIDHOMEPHONE, PIDWORKPHONE, ZLRNEXTOFKINLASTNAME, ZLRNEXTOFKINFIRSTNAME, ZLRNEXTOFKINMIDDLENAME, ZLRNEXTOFKINNAMESUFFIX, ZLRNEXTOFKINRELATIONSHIP, ZLRNEXTOFKINPHONE, PIDCELLPHONE, OBRCALLBACKPHONE, OBRCALLBACKPHONEALT, ZLRFACILITYPHONE, OBRPROVIDERFAX, OBXLOCALRESULTCODE, OBXLOINCDESCSHORT, OBXOBSERVATIONRESULTTEXTSHORT, PIDLASTNAME, PIDFIRSTNAME, PIDMIDDLENAME, PIDNAMESUFFIX, OBXABNORMALFLAG, PIDLENGTHOFRESIDENCY, PIDPATIENTLANGUAGE, OBXUNITOFMEASURE, OBXPRODUCERCLIAID, OBXPRODUCERLABNAME, OBXOBSERVATIONMETHOD, PIDDIN, MSHVERSIONID, OBRPROVIDERID, OBRPROVIDERLASTNAME, OBRPROVIDERFIRSTNAME, OBRPROVIDERMIDDLENAME, OBRPROVIDERNAMESUFFIX, OBRPROVIDERIDALT1, OBRPROVIDERIDALT2, ZLRFACILITYCODE, ZLRFACILITYNAME, REVIEW_UID, REVIEW_DATE, CREATE_UID, CREATE_DATE)
			 values(trim(upper(?1)),trim(upper(?2)),trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),trim(upper(?6)),trim(upper(?7)),trim(upper(?8)),trim(upper(?9)),trim(upper(?10)),trim(upper(?11)),trim(upper(?12)),trim(upper(?13)),trim(upper(?14)),trim(upper(?15)),trim(upper(?16)),trim(upper(?17)),trim(upper(?18)),trim(upper(?19)),trim(upper(?20)),trim(upper(?21)),trim(upper(?22)),trim(upper(?23)),trim(upper(?24)),trim(upper(?25)),trim(upper(?26)),trim(upper(?27)),trim(upper(?28)),trim(upper(?29)),SYSDATE,?30,?31,?32,?33,?34,?35,?36,?37,?38,?39,?40,?41,?42,?43,?44,?45,?46,?47,?48,?49,?50,?51,?52,?53,?54,?55,?56,?57,?58,?59,?60,?61,?62,?63,?64,?65,?66,?67,?68,?69,?70,?71,?72,trim(upper(?73)),trim(upper(?74)),trim(upper(?75)),trim(upper(?76)),trim(upper(?77)),trim(upper(?78)),trim(upper(?79)),trim(upper(?80)),trim(upper(?81)),trim(upper(?82)),trim(upper(?83)),trim(upper(?84)),trim(upper(?85)),trim(upper(?86)),trim(upper(?87)),trim(upper(?88)),trim(upper(?89)),trim(upper(?90)),trim(upper(?91)),trim(upper(?92)),trim(upper(?93)),trim(upper(?94)),trim(upper(?95)),trim(upper(?96)),trim(upper(?97)),trim(upper(?98)),trim(upper(?99)),trim(upper(?100)),trim(upper(?101)),trim(upper(?102)),trim(upper(?103)),trim(upper(?104)),trim(upper(?105)),trim(upper(?106)),trim(upper(?107)),trim(upper(?108)),trim(upper(?109)),trim(upper(?110)),trim(upper(?111)),trim(upper(?112)),trim(upper(?113)),trim(upper(?114)),trim(upper(?115)),trim(upper(?116)),trim(upper(?117)),trim(upper(?118)),trim(upper(?119)),trim(upper(?120)),trim(upper(?121)),trim(upper(?122)),trim(upper(?123)),trim(upper(?124)),trim(upper(?125)),trim(upper(?126)),trim(upper(?127)),trim(upper(?128)),trim(upper(?129)),trim(upper(?130)),trim(upper(?131)),trim(upper(?132)),trim(upper(?133)),trim(upper(?134)),trim(upper(?135)),trim(upper(?136)),trim(upper(?137)),trim(upper(?138)),trim(upper(?139)),trim(upper(?140)),trim(upper(?141)),trim(upper(?142)),trim(upper(?143)),trim(upper(?144)),trim(upper(?145)),trim(upper(?146)),trim(upper(?147)),trim(upper(?148)),trim(upper(?149)),trim(upper(?150)),trim(upper(?151)),trim(upper(?152)),trim(upper(?153)),trim(upper(?154)),trim(upper(?155)),trim(upper(?156)),trim(upper(?157)),trim(upper(?158)),trim(upper(?159)),trim(upper(?160)),trim(upper(?161)),trim(upper(?162)),trim(upper(?163)),trim(upper(?164)),trim(upper(?165)),trim(upper(?166)),trim(upper(?167)),trim(upper(?168)),trim(upper(?169)),trim(upper(?170)),trim(upper(?171)),trim(upper(?172)),trim(upper(?173)),trim(upper(?174)),trim(upper(?175)),trim(upper(?176)),trim(upper(?177)),trim(upper(?178)),trim(upper(?179)),trim(upper(?180)),trim(upper(?181)),trim(upper(?182)),trim(upper(?183)),trim(upper(?184)),trim(upper(?185)),trim(upper(?186)),trim(upper(?187)),trim(upper(?188)),trim(upper(?189)),trim(upper(?190)),trim(upper(?191)),trim(upper(?192)),trim(upper(?193)),trim(upper(?194)),trim(upper(?195)),trim(upper(?196)),trim(upper(?197)),trim(upper(?198)),trim(upper(?199)),trim(upper(?200)),trim(upper(?201)),trim(upper(?202)),trim(upper(?203)),trim(upper(?204)),trim(upper(?205)),trim(upper(?206)),trim(upper(?207)),trim(upper(?208)),trim(upper(?209)),trim(upper(?210)),trim(upper(?211)),trim(upper(?212)),trim(upper(?213)),trim(upper(?214)),trim(upper(?215)),trim(upper(?216)),trim(upper(?217)),trim(upper(?218)),trim(upper(?219)),trim(upper(?220)),trim(upper(?221)),?222,trim(upper(?223)),?224)
			""";

	/** The values of {@link #INSERT_RAW_HIV_DOCUMENT}, in order. */
	static Object[] insertRawHivDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("MSHFILECONTENTTYPE")), text(map.get("MSHDELETEDIND")), text(map.get("PIDSEXCODE")), text(map.get("PIDRACECODE")),
				text(map.get("PIDRACEASIAN")), text(map.get("PIDRACEBLACK")), text(map.get("PIDRACENATIVEAMERALASKAN")), text(map.get("PIDRACEOTHER")),
				text(map.get("PIDRACEPACIFIC")), text(map.get("PIDRACEUNKNOWN")), text(map.get("PIDRACEWHITE")), text(map.get("PIDMARITALSTATUSCODE")),
				text(map.get("PIDDEATHIND")), text(map.get("PIDUSBORNIND")), text(map.get("PIDVACCINETRIALIND")), text(map.get("PIDNONHUMANIND")),
				text(map.get("PIDDELETEDIND")), text(map.get("OBRRESULTSTATUSCODE")), text(map.get("OBRDELETEDIND")), text(map.get("OBXRESULTSTATUSCODE")),
				text(map.get("OBXPROCESSEDIND")), text(map.get("OBXLEADPURPOSE")), text(map.get("OBXDELETEDIND")), text(map.get("PIDSMSA")),
				text(map.get("PIDNOTE")), text(map.get("OBRRELEVANTCLINICALINFO")), text(map.get("OBRNOTE")), text(map.get("OBXOBSERVATIONRESULTTEXT")),
				text(map.get("OBXNOTE")), text(map.get("NYC_SHIP_DATE")), number(map.get("PIDLONGITUDE")), number(map.get("PIDLATITUDE")),
				number(map.get("DOCUMENT_ID")), number(map.get("INTERPRETATION_STD_DOC_ID")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("HASH_UNIQUE_NUMBER")),
				number(map.get("MSHMESSAGEMASTERKEY")), number(map.get("PIDPATIENTKEY")), number(map.get("PIDMESSAGEMASTERKEY")), number(map.get("OBROBSERVATIONREQUESTKEY")),
				number(map.get("ZLROBSERVATIONREQUESTKEY")), number(map.get("OBRPATIENTKEY")), number(map.get("OBRPATIENTKEYORIG")), number(map.get("OBRINITRESKEY")),
				number(map.get("OBXOBSERVATIONRESULTKEY")), number(map.get("OBXOBSERVATIONREQUESTKEY")), number(map.get("OBXOLDESTDUPLICATERESULTKEY")), number(map.get("PIDCOUNTYCODE")),
				number(map.get("OBXCOUNTYCODE")), number(map.get("OBXDISEASE")), number(map.get("OBXDISEASEGROUP")), text(map.get("MSHMESSAGEDATE")),
				text(map.get("MSHCREATEDATE")), text(map.get("MSHDELETEDATE")), text(map.get("PIDCREATEDATE")), text(map.get("PIDDATEOFBIRTH")),
				text(map.get("PIDDEATHDATE")), text(map.get("PIDUPDATEDATE")), text(map.get("PIDDELETEDATE")), text(map.get("OBRCREATEDATE")),
				text(map.get("OBRCOLLECTIONDATE")), text(map.get("OBRRESULTREPORTDATE")), text(map.get("OBRDELETEDATE")), text(map.get("OBROBSERVATIONDATE")),
				text(map.get("OBRRECEIVEDDATE")), text(map.get("OBXOBSERVATIONDATE")), text(map.get("OBXPROCESSEDDATE")), text(map.get("OBXDELETEDATE")),
				text(map.get("PROGRAMCODEUPDATEDATE")), text(map.get("OBXPROGRAMCODEUPDATEDATE")), text(map.get("OBXCREATEDATE")), text(map.get("OBXANALYSISDATE")),
				text(map.get("MSHSENDINGFACILITYCLIA")), text(map.get("MSHLABPFI")), text(map.get("PIDFACILITYIDALT")), text(map.get("OBRLOINCCODE")),
				text(map.get("OBRSPECIMENSOURCECODE")), text(map.get("OBRPROVIDERIDTYPECODE")), text(map.get("OBRINITRESLOINCCODE")), text(map.get("OBRPROVIDERIDTYPECODEALT1")),
				text(map.get("OBRPROVIDERIDTYPECODEALT2")), text(map.get("OBXSETID")), text(map.get("OBXLOINCCODE")), text(map.get("OBXSNOMEDCODE")),
				text(map.get("OBXSENDINGFACILITYCLIA")), text(map.get("PIDASSIGNAUTHDESC")), text(map.get("OBRPLACERSORDERNUM")), text(map.get("OBRFILLERSORDERNUM")),
				text(map.get("OBXREFERENCERANGE")), text(map.get("PIDADDRESSLINE1")), text(map.get("PIDADDRESSLINE2")), text(map.get("PIDCITY")),
				text(map.get("PIDSTATE")), text(map.get("PIDZIPCODE")), text(map.get("PIDCOUNTRYCODE")), text(map.get("ZLRNEXTOFKINADDRESSLINE1")),
				text(map.get("ZLRNEXTOFKINADDRESSLINE2")), text(map.get("ZLRNEXTOFKINCITY")), text(map.get("ZLRNEXTOFKINSTATE")), text(map.get("ZLRNEXTOFKINZIPCODE")),
				text(map.get("ZLRNEXTOFKINCOUNTRY")), text(map.get("PIDEMAIL")), text(map.get("ZLRPROVIDERADDRESSLINE1")), text(map.get("ZLRPROVIDERADDRESSLINE2")),
				text(map.get("ZLRPROVIDERCITY")), text(map.get("ZLRPROVIDERSTATE")), text(map.get("ZLRPROVIDERZIPCODE")), text(map.get("ZLRPROVIDERCOUNTRY")),
				text(map.get("ZLRFACILITYADDRESSLINE1")), text(map.get("ZLRFACILITYADDRESSLINE2")), text(map.get("ZLRFACILITYCITY")), text(map.get("ZLRFACILITYSTATE")),
				text(map.get("ZLRFACILITYZIPCODE")), text(map.get("ZLRFACILITYCOUNTRY")), text(map.get("OBRPROVIDEREMAIL")), text(map.get("PIDNYSID")),
				text(map.get("OBRSNOMEDCODE")), text(map.get("MSHFILENAME")), text(map.get("PIDSSN")), text(map.get("MSHSENDINGFACILITYNAME")),
				text(map.get("PIDCOUNTYGAZCODEORIG")), text(map.get("PIDTYPEOFINSURANCE")), text(map.get("MSHMESSAGECONTROLID")), text(map.get("PIDPATIENTIDEXTERNAL")),
				text(map.get("PIDPATIENTIDINTERNAL")), text(map.get("PIDPATIENTIDALT")), text(map.get("PIDPATIENTACCOUNTNUM")), text(map.get("PIDMOTHERSID")),
				text(map.get("PIDMEDICAIDNUMBER")), text(map.get("ZLRAGE")), text(map.get("OBRINITRESLOCALTESTCODE")), text(map.get("OBRINITRESSUBID")),
				text(map.get("OBXOBSERVATIONSUBID")), text(map.get("OBXLOCALTESTCODE")), text(map.get("OBXICD_CODETYPE")), text(map.get("OBXICD_CODE")),
				text(map.get("PIDASSIGNAUTHOID")), text(map.get("OBRLOCALCODE")), text(map.get("OBRANALYSISDESC")), text(map.get("OBRPATHOLOGISTID")),
				text(map.get("OBRPATHOLOGISTLASTNAME")), text(map.get("OBRPATHOLOGISTFIRSTNAME")), text(map.get("OBRPATHOLOGISTMIDDLENAME")), text(map.get("OBRPATHOLOGISTNAMESUFFIX")),
				text(map.get("OBRINITRESPLACERSORDERNUM")), text(map.get("OBRINITRESACCESSIONNUM")), text(map.get("OBRACCESSIONNUM")), text(map.get("PIDCOUNTYCODEORIGINAL")),
				text(map.get("PIDMEDICALRECORDNUMBER")), text(map.get("PIDRACECODERAW")), text(map.get("OBRSPECIMENSOURCENAME")), text(map.get("OBRINITRESLOINCDESC")),
				text(map.get("OBRINITRESLOCALTESTDESC")), text(map.get("OBRINITRESOBSERVATIONTEXT")), text(map.get("OBXLOINCDESC")), text(map.get("OBXSNOMEDDESC")),
				text(map.get("OBXLOCALTESTDESC")), text(map.get("OBXLOCALRESULTDESC")), text(map.get("OBXICD_DESC")), text(map.get("MSHMESSAGEDATERAW")),
				text(map.get("PIDDATEOFBIRTHRAW")), text(map.get("PIDDEATHDATERAW")), text(map.get("OBRCOLLECTIONDATERAW")), text(map.get("OBRRESULTREPORTDATERAW")),
				text(map.get("OBROBSERVATIONDATERAW")), text(map.get("OBRRECEIVEDDATERAW")), text(map.get("OBXOBSERVATIONDATERAW")), text(map.get("OBXANALYSISDATERAW")),
				text(map.get("MSHPROCESSINGID")), text(map.get("MSHSOURCETYPE")), text(map.get("PIDETHNICGROUPCODE")), text(map.get("OBXVALUETYPE")),
				text(map.get("PIDFACILITYIDEXTERNAL")), text(map.get("PIDFACILITYIDINTERNAL")), text(map.get("PIDCOUNTRYOFORIGIN")), text(map.get("PIDUPDATEID")),
				text(map.get("OBXLOGONID")), text(map.get("PROGRAMCODEUPDATEID")), text(map.get("OBXPROGRAMCODEUPDATEID")), text(map.get("PIDSETID")),
				text(map.get("OBRSETID")), text(map.get("OBXPROGRAMCODE")), text(map.get("PIDHOMEPHONE")), text(map.get("PIDWORKPHONE")),
				text(map.get("ZLRNEXTOFKINLASTNAME")), text(map.get("ZLRNEXTOFKINFIRSTNAME")), text(map.get("ZLRNEXTOFKINMIDDLENAME")), text(map.get("ZLRNEXTOFKINNAMESUFFIX")),
				text(map.get("ZLRNEXTOFKINRELATIONSHIP")), text(map.get("ZLRNEXTOFKINPHONE")), text(map.get("PIDCELLPHONE")), text(map.get("OBRCALLBACKPHONE")),
				text(map.get("OBRCALLBACKPHONEALT")), text(map.get("ZLRFACILITYPHONE")), text(map.get("OBRPROVIDERFAX")), text(map.get("OBXLOCALRESULTCODE")),
				text(map.get("OBXLOINCDESCSHORT")), text(map.get("OBXOBSERVATIONRESULTTEXTSHORT")), text(map.get("PIDLASTNAME")), text(map.get("PIDFIRSTNAME")),
				text(map.get("PIDMIDDLENAME")), text(map.get("PIDNAMESUFFIX")), text(map.get("OBXABNORMALFLAG")), text(map.get("PIDLENGTHOFRESIDENCY")),
				text(map.get("PIDPATIENTLANGUAGE")), text(map.get("OBXUNITOFMEASURE")), text(map.get("OBXPRODUCERCLIAID")), text(map.get("OBXPRODUCERLABNAME")),
				text(map.get("OBXOBSERVATIONMETHOD")), text(map.get("PIDDIN")), text(map.get("MSHVERSIONID")), text(map.get("OBRPROVIDERID")),
				text(map.get("OBRPROVIDERLASTNAME")), text(map.get("OBRPROVIDERFIRSTNAME")), text(map.get("OBRPROVIDERMIDDLENAME")), text(map.get("OBRPROVIDERNAMESUFFIX")),
				text(map.get("OBRPROVIDERIDALT1")), text(map.get("OBRPROVIDERIDALT2")), text(map.get("ZLRFACILITYCODE")), text(map.get("ZLRFACILITYNAME")),
				text(map.get("REVIEW_UID")), text(map.get("REVIEW_DATE")), text(map.get("CREATE_UID")), text(map.get("CREATE_DATE"))
		};
	}

	/** The SQL of {@code insertRawStdDocument}, as the former mapper had it (228 bound values). */
	static final String INSERT_RAW_STD_DOCUMENT = """
			 	insert into RAW_STD_ECLRS_DOC(PIDNOTE, OBRRELEVANTCLINICALINFO, OBRNOTE, OBXOBSERVATIONRESULTTEXT, OBXNOTE, PROCESS_DATE, PIDLONGITUDE, PIDLATITUDE, DOCUMENT_ID, CURRENT_PROCESS_STATUS, HASH_UNIQUE_NUMBER, MSHMESSAGEMASTERKEY, PIDPATIENTKEY, PIDMESSAGEMASTERKEY, OBROBSERVATIONREQUESTKEY, ZLROBSERVATIONREQUESTKEY, OBRPATIENTKEY, OBRPATIENTKEYORIG, OBRINITRESKEY, OBXOBSERVATIONRESULTKEY, OBXOBSERVATIONREQUESTKEY, OBXOLDESTDUPLICATERESULTKEY, PIDCOUNTYCODE, OBXCOUNTYCODE, OBXDISEASE, OBXDISEASEGROUP, MSHMESSAGEDATE, MSHCREATEDATE, MSHDELETEDATE, PIDCREATEDATE, PIDDATEOFBIRTH, PIDDEATHDATE, PIDUPDATEDATE, PIDDELETEDATE, OBRCREATEDATE, OBRCOLLECTIONDATE, OBRRESULTREPORTDATE, OBRDELETEDATE, OBROBSERVATIONDATE, OBRRECEIVEDDATE, OBXOBSERVATIONDATE, OBXPROCESSEDDATE, OBXDELETEDATE, PROGRAMCODEUPDATEDATE, OBXPROGRAMCODEUPDATEDATE, OBXCREATEDATE, OBXANALYSISDATE, MSHFILECONTENTTYPE, MSHDELETEDIND, PIDSEXCODE, PIDRACECODE, PIDRACEASIAN, PIDRACEBLACK, PIDRACENATIVEAMERALASKAN, PIDRACEOTHER, PIDRACEPACIFIC, PIDRACEUNKNOWN, PIDRACEWHITE, PIDMARITALSTATUSCODE, PIDDEATHIND, PIDUSBORNIND, PIDVACCINETRIALIND, PIDNONHUMANIND, PIDDELETEDIND, OBRRESULTSTATUSCODE, OBRDELETEDIND, OBXRESULTSTATUSCODE, OBXPROCESSEDIND, OBXLEADPURPOSE, OBXDELETEDIND, MAPPABLEIND, MSHSENDINGFACILITYCLIA, MSHLABPFI, PIDFACILITYIDEXTERNAL, PIDFACILITYIDINTERNAL, PIDFACILITYIDALT, OBRSNOMEDCODE, OBRLOINCCODE, OBRSPECIMENSOURCECODE, OBRPROVIDERIDTYPECODE, OBRINITRESLOINCCODE, OBRPROVIDERIDTYPECODEALT1, OBRPROVIDERIDTYPECODEALT2, OBXSETID, OBXLOINCCODE, OBXSNOMEDCODE, OBXSENDINGFACILITYCLIA, CLASS, TESTTYPE, RESULT, SPECIMENSOURCE, PIDASSIGNAUTHDESC, OBRPLACERSORDERNUM, OBRFILLERSORDERNUM, OBXREFERENCERANGE, REASONNOTMAPPED, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, PIDCOUNTRYCODE, ZLRNEXTOFKINADDRESSLINE1, ZLRNEXTOFKINADDRESSLINE2, ZLRNEXTOFKINCITY, ZLRNEXTOFKINSTATE, ZLRNEXTOFKINZIPCODE, ZLRNEXTOFKINCOUNTRY, PIDEMAIL, ZLRPROVIDERADDRESSLINE1, ZLRPROVIDERADDRESSLINE2, ZLRPROVIDERCITY, ZLRPROVIDERSTATE, ZLRPROVIDERZIPCODE, ZLRPROVIDERCOUNTRY, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2, ZLRFACILITYCITY, ZLRFACILITYSTATE, ZLRFACILITYZIPCODE, ZLRFACILITYCOUNTRY, OBRPROVIDEREMAIL, PIDMEDICAIDNUMBER, PIDNYSID, MSHFILENAME, PIDSSN, MSHSENDINGFACILITYNAME, PIDCOUNTYGAZCODEORIG, PIDTYPEOFINSURANCE, MSHMESSAGECONTROLID, PIDPATIENTIDEXTERNAL, PIDPATIENTIDINTERNAL, PIDPATIENTIDALT, PIDPATIENTACCOUNTNUM, PIDMOTHERSID, ZLRAGE, OBRINITRESLOCALTESTCODE, OBRINITRESSUBID, OBXOBSERVATIONSUBID, OBXLOCALTESTCODE, OBXICD_CODETYPE, OBXICD_CODE, PIDASSIGNAUTHOID, OBRLOCALCODE, OBRANALYSISDESC, OBRPATHOLOGISTID, OBRPATHOLOGISTLASTNAME, OBRPATHOLOGISTFIRSTNAME, OBRPATHOLOGISTMIDDLENAME, OBRPATHOLOGISTNAMESUFFIX, OBRACCESSIONNUM, OBRINITRESPLACERSORDERNUM, OBRINITRESACCESSIONNUM, PIDCOUNTYCODEORIGINAL, PIDMEDICALRECORDNUMBER, CDESSMAPPEDVALUES, PIDRACECODERAW, OBRSPECIMENSOURCENAME, OBRINITRESLOINCDESC, OBRINITRESLOCALTESTDESC, OBRINITRESOBSERVATIONTEXT, OBXLOINCDESC, OBXSNOMEDDESC, OBXLOCALTESTDESC, OBXLOCALRESULTDESC, OBXICD_DESC, MSHMESSAGEDATERAW, PIDDATEOFBIRTHRAW, PIDDEATHDATERAW, OBRCOLLECTIONDATERAW, OBRRESULTREPORTDATERAW, OBROBSERVATIONDATERAW, OBRRECEIVEDDATERAW, OBXOBSERVATIONDATERAW, OBXANALYSISDATERAW, MSHPROCESSINGID, MSHSOURCETYPE, PIDETHNICGROUPCODE, OBXVALUETYPE, OBXPROGRAMCODE, PIDCOUNTRYOFORIGIN, PIDUPDATEID, OBXLOGONID, PROGRAMCODEUPDATEID, OBXPROGRAMCODEUPDATEID, OBRSPECIMENDESC, PIDSETID, PIDSMSA, OBRSETID, PIDHOMEPHONE, PIDWORKPHONE, ZLRNEXTOFKINLASTNAME, ZLRNEXTOFKINFIRSTNAME, ZLRNEXTOFKINMIDDLENAME, ZLRNEXTOFKINNAMESUFFIX, ZLRNEXTOFKINRELATIONSHIP, ZLRNEXTOFKINPHONE, PIDCELLPHONE, OBRCALLBACKPHONE, OBRCALLBACKPHONEALT, ZLRFACILITYPHONE, OBRPROVIDERFAX, OBXLOCALRESULTCODE, OBXLOINCDESCSHORT, OBXOBSERVATIONRESULTTEXTSHORT, PIDLASTNAME, PIDFIRSTNAME, PIDMIDDLENAME, PIDNAMESUFFIX, OBXABNORMALFLAG, PIDLENGTHOFRESIDENCY, PIDPATIENTLANGUAGE, OBXUNITOFMEASURE, OBXPRODUCERCLIAID, OBXPRODUCERLABNAME, OBXOBSERVATIONMETHOD, PIDDIN, MSHVERSIONID, OBRPROVIDERID, OBRPROVIDERLASTNAME, OBRPROVIDERFIRSTNAME, OBRPROVIDERMIDDLENAME, OBRPROVIDERNAMESUFFIX, OBRPROVIDERIDALT1, OBRPROVIDERIDALT2, ZLRFACILITYCODE, ZLRFACILITYNAME, CREATE_UID, CREATE_DATE)
			values(trim(upper(?1)),trim(upper(?2)),trim(upper(?3)),trim(upper(?4)),trim(upper(?5)),SYSDATE,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26,?27,?28,?29,?30,?31,?32,?33,?34,?35,?36,?37,?38,?39,?40,?41,?42,?43,?44,?45,?46,trim(upper(?47)),trim(upper(?48)),trim(upper(?49)),trim(upper(?50)),trim(upper(?51)),trim(upper(?52)),trim(upper(?53)),trim(upper(?54)),trim(upper(?55)),trim(upper(?56)),trim(upper(?57)),trim(upper(?58)),trim(upper(?59)),trim(upper(?60)),trim(upper(?61)),trim(upper(?62)),trim(upper(?63)),trim(upper(?64)),trim(upper(?65)),trim(upper(?66)),trim(upper(?67)),trim(upper(?68)),trim(upper(?69)),trim(upper(?70)),trim(upper(?71)),trim(upper(?72)),trim(upper(?73)),trim(upper(?74)),trim(upper(?75)),trim(upper(?76)),trim(upper(?77)),trim(upper(?78)),trim(upper(?79)),trim(upper(?80)),trim(upper(?81)),trim(upper(?82)),trim(upper(?83)),trim(upper(?84)),trim(upper(?85)),trim(upper(?86)),trim(upper(?87)),trim(upper(?88)),trim(upper(?89)),trim(upper(?90)),trim(upper(?91)),trim(upper(?92)),trim(upper(?93)),trim(upper(?94)),trim(upper(?95)),trim(upper(?96)),trim(upper(?97)),trim(upper(?98)),trim(upper(?99)),trim(upper(?100)),trim(upper(?101)),trim(upper(?102)),trim(upper(?103)),trim(upper(?104)),trim(upper(?105)),trim(upper(?106)),trim(upper(?107)),trim(upper(?108)),trim(upper(?109)),trim(upper(?110)),trim(upper(?111)),trim(upper(?112)),trim(upper(?113)),trim(upper(?114)),trim(upper(?115)),trim(upper(?116)),trim(upper(?117)),trim(upper(?118)),trim(upper(?119)),trim(upper(?120)),trim(upper(?121)),trim(upper(?122)),trim(upper(?123)),trim(upper(?124)),trim(upper(?125)),trim(upper(?126)),trim(upper(?127)),trim(upper(?128)),trim(upper(?129)),trim(upper(?130)),trim(upper(?131)),trim(upper(?132)),trim(upper(?133)),trim(upper(?134)),trim(upper(?135)),trim(upper(?136)),trim(upper(?137)),trim(upper(?138)),trim(upper(?139)),trim(upper(?140)),trim(upper(?141)),trim(upper(?142)),trim(upper(?143)),trim(upper(?144)),trim(upper(?145)),trim(upper(?146)),trim(upper(?147)),trim(upper(?148)),trim(upper(?149)),trim(upper(?150)),trim(upper(?151)),trim(upper(?152)),trim(upper(?153)),trim(upper(?154)),trim(upper(?155)),trim(upper(?156)),trim(upper(?157)),trim(upper(?158)),trim(upper(?159)),trim(upper(?160)),trim(upper(?161)),trim(upper(?162)),trim(upper(?163)),trim(upper(?164)),trim(upper(?165)),trim(upper(?166)),trim(upper(?167)),trim(upper(?168)),trim(upper(?169)),trim(upper(?170)),trim(upper(?171)),trim(upper(?172)),trim(upper(?173)),trim(upper(?174)),trim(upper(?175)),trim(upper(?176)),trim(upper(?177)),trim(upper(?178)),trim(upper(?179)),trim(upper(?180)),trim(upper(?181)),trim(upper(?182)),trim(upper(?183)),trim(upper(?184)),trim(upper(?185)),trim(upper(?186)),trim(upper(?187)),trim(upper(?188)),trim(upper(?189)),trim(upper(?190)),trim(upper(?191)),trim(upper(?192)),trim(upper(?193)),trim(upper(?194)),trim(upper(?195)),trim(upper(?196)),trim(upper(?197)),trim(upper(?198)),trim(upper(?199)),trim(upper(?200)),trim(upper(?201)),trim(upper(?202)),trim(upper(?203)),trim(upper(?204)),trim(upper(?205)),trim(upper(?206)),trim(upper(?207)),trim(upper(?208)),trim(upper(?209)),trim(upper(?210)),trim(upper(?211)),trim(upper(?212)),trim(upper(?213)),trim(upper(?214)),trim(upper(?215)),trim(upper(?216)),trim(upper(?217)),trim(upper(?218)),trim(upper(?219)),trim(upper(?220)),trim(upper(?221)),trim(upper(?222)),trim(upper(?223)),trim(upper(?224)),trim(upper(?225)),trim(upper(?226)),trim(upper(?227)),?228)
			""";

	/** The values of {@link #INSERT_RAW_STD_DOCUMENT}, in order. */
	static Object[] insertRawStdDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("PIDNOTE")), text(map.get("OBRRELEVANTCLINICALINFO")), text(map.get("OBRNOTE")), text(map.get("OBXOBSERVATIONRESULTTEXT")),
				text(map.get("OBXNOTE")), number(map.get("PIDLONGITUDE")), number(map.get("PIDLATITUDE")), number(map.get("DOCUMENT_ID")),
				number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("HASH_UNIQUE_NUMBER")), number(map.get("MSHMESSAGEMASTERKEY")), number(map.get("PIDPATIENTKEY")),
				number(map.get("PIDMESSAGEMASTERKEY")), number(map.get("OBROBSERVATIONREQUESTKEY")), number(map.get("ZLROBSERVATIONREQUESTKEY")), number(map.get("OBRPATIENTKEY")),
				number(map.get("OBRPATIENTKEYORIG")), number(map.get("OBRINITRESKEY")), number(map.get("OBXOBSERVATIONRESULTKEY")), number(map.get("OBXOBSERVATIONREQUESTKEY")),
				number(map.get("OBXOLDESTDUPLICATERESULTKEY")), number(map.get("PIDCOUNTYCODE")), number(map.get("OBXCOUNTYCODE")), number(map.get("OBXDISEASE")),
				number(map.get("OBXDISEASEGROUP")), text(map.get("MSHMESSAGEDATE")), text(map.get("MSHCREATEDATE")), text(map.get("MSHDELETEDATE")),
				text(map.get("PIDCREATEDATE")), text(map.get("PIDDATEOFBIRTH")), text(map.get("PIDDEATHDATE")), text(map.get("PIDUPDATEDATE")),
				text(map.get("PIDDELETEDATE")), text(map.get("OBRCREATEDATE")), text(map.get("OBRCOLLECTIONDATE")), text(map.get("OBRRESULTREPORTDATE")),
				text(map.get("OBRDELETEDATE")), text(map.get("OBROBSERVATIONDATE")), text(map.get("OBRRECEIVEDDATE")), text(map.get("OBXOBSERVATIONDATE")),
				text(map.get("OBXPROCESSEDDATE")), text(map.get("OBXDELETEDATE")), text(map.get("PROGRAMCODEUPDATEDATE")), text(map.get("OBXPROGRAMCODEUPDATEDATE")),
				text(map.get("OBXCREATEDATE")), text(map.get("OBXANALYSISDATE")), text(map.get("MSHFILECONTENTTYPE")), text(map.get("MSHDELETEDIND")),
				text(map.get("PIDSEXCODE")), text(map.get("PIDRACECODE")), text(map.get("PIDRACEASIAN")), text(map.get("PIDRACEBLACK")),
				text(map.get("PIDRACENATIVEAMERALASKAN")), text(map.get("PIDRACEOTHER")), text(map.get("PIDRACEPACIFIC")), text(map.get("PIDRACEUNKNOWN")),
				text(map.get("PIDRACEWHITE")), text(map.get("PIDMARITALSTATUSCODE")), text(map.get("PIDDEATHIND")), text(map.get("PIDUSBORNIND")),
				text(map.get("PIDVACCINETRIALIND")), text(map.get("PIDNONHUMANIND")), text(map.get("PIDDELETEDIND")), text(map.get("OBRRESULTSTATUSCODE")),
				text(map.get("OBRDELETEDIND")), text(map.get("OBXRESULTSTATUSCODE")), text(map.get("OBXPROCESSEDIND")), text(map.get("OBXLEADPURPOSE")),
				text(map.get("OBXDELETEDIND")), text(map.get("MAPPABLEIND")), text(map.get("MSHSENDINGFACILITYCLIA")), text(map.get("MSHLABPFI")),
				text(map.get("PIDFACILITYIDEXTERNAL")), text(map.get("PIDFACILITYIDINTERNAL")), text(map.get("PIDFACILITYIDALT")), text(map.get("OBRSNOMEDCODE")),
				text(map.get("OBRLOINCCODE")), text(map.get("OBRSPECIMENSOURCECODE")), text(map.get("OBRPROVIDERIDTYPECODE")), text(map.get("OBRINITRESLOINCCODE")),
				text(map.get("OBRPROVIDERIDTYPECODEALT1")), text(map.get("OBRPROVIDERIDTYPECODEALT2")), text(map.get("OBXSETID")), text(map.get("OBXLOINCCODE")),
				text(map.get("OBXSNOMEDCODE")), text(map.get("OBXSENDINGFACILITYCLIA")), text(map.get("CLASS")), text(map.get("TESTTYPE")),
				text(map.get("RESULT")), text(map.get("SPECIMENSOURCE")), text(map.get("PIDASSIGNAUTHDESC")), text(map.get("OBRPLACERSORDERNUM")),
				text(map.get("OBRFILLERSORDERNUM")), text(map.get("OBXREFERENCERANGE")), text(map.get("REASONNOTMAPPED")), text(map.get("PIDADDRESSLINE1")),
				text(map.get("PIDADDRESSLINE2")), text(map.get("PIDCITY")), text(map.get("PIDSTATE")), text(map.get("PIDZIPCODE")),
				text(map.get("PIDCOUNTRYCODE")), text(map.get("ZLRNEXTOFKINADDRESSLINE1")), text(map.get("ZLRNEXTOFKINADDRESSLINE2")), text(map.get("ZLRNEXTOFKINCITY")),
				text(map.get("ZLRNEXTOFKINSTATE")), text(map.get("ZLRNEXTOFKINZIPCODE")), text(map.get("ZLRNEXTOFKINCOUNTRY")), text(map.get("PIDEMAIL")),
				text(map.get("ZLRPROVIDERADDRESSLINE1")), text(map.get("ZLRPROVIDERADDRESSLINE2")), text(map.get("ZLRPROVIDERCITY")), text(map.get("ZLRPROVIDERSTATE")),
				text(map.get("ZLRPROVIDERZIPCODE")), text(map.get("ZLRPROVIDERCOUNTRY")), text(map.get("ZLRFACILITYADDRESSLINE1")), text(map.get("ZLRFACILITYADDRESSLINE2")),
				text(map.get("ZLRFACILITYCITY")), text(map.get("ZLRFACILITYSTATE")), text(map.get("ZLRFACILITYZIPCODE")), text(map.get("ZLRFACILITYCOUNTRY")),
				text(map.get("OBRPROVIDEREMAIL")), text(map.get("PIDMEDICAIDNUMBER")), text(map.get("PIDNYSID")), text(map.get("MSHFILENAME")),
				text(map.get("PIDSSN")), text(map.get("MSHSENDINGFACILITYNAME")), text(map.get("PIDCOUNTYGAZCODEORIG")), text(map.get("PIDTYPEOFINSURANCE")),
				text(map.get("MSHMESSAGECONTROLID")), text(map.get("PIDPATIENTIDEXTERNAL")), text(map.get("PIDPATIENTIDINTERNAL")), text(map.get("PIDPATIENTIDALT")),
				text(map.get("PIDPATIENTACCOUNTNUM")), text(map.get("PIDMOTHERSID")), text(map.get("ZLRAGE")), text(map.get("OBRINITRESLOCALTESTCODE")),
				text(map.get("OBRINITRESSUBID")), text(map.get("OBXOBSERVATIONSUBID")), text(map.get("OBXLOCALTESTCODE")), text(map.get("OBXICD_CODETYPE")),
				text(map.get("OBXICD_CODE")), text(map.get("PIDASSIGNAUTHOID")), text(map.get("OBRLOCALCODE")), text(map.get("OBRANALYSISDESC")),
				text(map.get("OBRPATHOLOGISTID")), text(map.get("OBRPATHOLOGISTLASTNAME")), text(map.get("OBRPATHOLOGISTFIRSTNAME")), text(map.get("OBRPATHOLOGISTMIDDLENAME")),
				text(map.get("OBRPATHOLOGISTNAMESUFFIX")), text(map.get("OBRACCESSIONNUM")), text(map.get("OBRINITRESPLACERSORDERNUM")), text(map.get("OBRINITRESACCESSIONNUM")),
				text(map.get("PIDCOUNTYCODEORIGINAL")), text(map.get("PIDMEDICALRECORDNUMBER")), text(map.get("CDESSMAPPEDVALUES")), text(map.get("PIDRACECODERAW")),
				text(map.get("OBRSPECIMENSOURCENAME")), text(map.get("OBRINITRESLOINCDESC")), text(map.get("OBRINITRESLOCALTESTDESC")), text(map.get("OBRINITRESOBSERVATIONTEXT")),
				text(map.get("OBXLOINCDESC")), text(map.get("OBXSNOMEDDESC")), text(map.get("OBXLOCALTESTDESC")), text(map.get("OBXLOCALRESULTDESC")),
				text(map.get("OBXICD_DESC")), text(map.get("MSHMESSAGEDATERAW")), text(map.get("PIDDATEOFBIRTHRAW")), text(map.get("PIDDEATHDATERAW")),
				text(map.get("OBRCOLLECTIONDATERAW")), text(map.get("OBRRESULTREPORTDATERAW")), text(map.get("OBROBSERVATIONDATERAW")), text(map.get("OBRRECEIVEDDATERAW")),
				text(map.get("OBXOBSERVATIONDATERAW")), text(map.get("OBXANALYSISDATERAW")), text(map.get("MSHPROCESSINGID")), text(map.get("MSHSOURCETYPE")),
				text(map.get("PIDETHNICGROUPCODE")), text(map.get("OBXVALUETYPE")), text(map.get("OBXPROGRAMCODE")), text(map.get("PIDCOUNTRYOFORIGIN")),
				text(map.get("PIDUPDATEID")), text(map.get("OBXLOGONID")), text(map.get("PROGRAMCODEUPDATEID")), text(map.get("OBXPROGRAMCODEUPDATEID")),
				text(map.get("OBRSPECIMENDESC")), text(map.get("PIDSETID")), text(map.get("PIDSMSA")), text(map.get("OBRSETID")),
				text(map.get("PIDHOMEPHONE")), text(map.get("PIDWORKPHONE")), text(map.get("ZLRNEXTOFKINLASTNAME")), text(map.get("ZLRNEXTOFKINFIRSTNAME")),
				text(map.get("ZLRNEXTOFKINMIDDLENAME")), text(map.get("ZLRNEXTOFKINNAMESUFFIX")), text(map.get("ZLRNEXTOFKINRELATIONSHIP")), text(map.get("ZLRNEXTOFKINPHONE")),
				text(map.get("PIDCELLPHONE")), text(map.get("OBRCALLBACKPHONE")), text(map.get("OBRCALLBACKPHONEALT")), text(map.get("ZLRFACILITYPHONE")),
				text(map.get("OBRPROVIDERFAX")), text(map.get("OBXLOCALRESULTCODE")), text(map.get("OBXLOINCDESCSHORT")), text(map.get("OBXOBSERVATIONRESULTTEXTSHORT")),
				text(map.get("PIDLASTNAME")), text(map.get("PIDFIRSTNAME")), text(map.get("PIDMIDDLENAME")), text(map.get("PIDNAMESUFFIX")),
				text(map.get("OBXABNORMALFLAG")), text(map.get("PIDLENGTHOFRESIDENCY")), text(map.get("PIDPATIENTLANGUAGE")), text(map.get("OBXUNITOFMEASURE")),
				text(map.get("OBXPRODUCERCLIAID")), text(map.get("OBXPRODUCERLABNAME")), text(map.get("OBXOBSERVATIONMETHOD")), text(map.get("PIDDIN")),
				text(map.get("MSHVERSIONID")), text(map.get("OBRPROVIDERID")), text(map.get("OBRPROVIDERLASTNAME")), text(map.get("OBRPROVIDERFIRSTNAME")),
				text(map.get("OBRPROVIDERMIDDLENAME")), text(map.get("OBRPROVIDERNAMESUFFIX")), text(map.get("OBRPROVIDERIDALT1")), text(map.get("OBRPROVIDERIDALT2")),
				text(map.get("ZLRFACILITYCODE")), text(map.get("ZLRFACILITYNAME")), text(map.get("CREATE_UID")), text(map.get("CREATE_DATE"))
		};
	}

	/** The SQL of {@code insertStdHivDocument}, as the former mapper had it (109 bound values). */
	static final String INSERT_STD_HIV_DOCUMENT = """
			 	insert into STD_HIV_ECLRS_DOC(OBSERVATION_DATE, COLLECTION_DATE, RESULT_REPORT_DATE, EHARS_IMPORT_DATE, DATE_RECEIVED, REVIEW_DATE, NYC_SHIP_DATE, DOCUMENT_ID, LAB_SRC_ID, CURRENT_PROCESS_STATUS, MATCH_PASS, QS_PARENT_DOC_ID, AGE_CATEGORY, MATCH_WEIGHT, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, EHARS_STATUS_CD, DOB_TEXT, PERSON_LATITUDE, PROV_LATITUDE, LOINC, SPECIMEN_SOURCE_CD, REVIEW_UID, SENDING_FACILITY_CLIA, FACILITY_LATITUDE, INTERPRET_CD, PERSON_ADDRESS, MATCHED_STATENO, PERSON_COUNTRY, PERSON_LONGITUDE, PROV_LONGITUDE, FACILITY_LONGITUDE, PROV_LNAME, FACILITY_NAME, SENDING_FACILITY_NAME, PERSON_STATE, GENDER, INSURANCE_CD, PROV_STATE, FACILITY_STATE, MATCH_CD, HIV_VACCINE_RECIP, RESIDE_OUTSIDE_USA, AREA_OF_BIRTH, MEDICAID_NUMBER, LOCAL_TEST_CODE, PROV_ADDRESS, FACILITY_ADDRESS, ACCESSION_NUMBER, MED_RECORD_NUMBER, INSURANCE_NUMBER, NYSID, SPECIMEN_SOURCE_NAME, LOCAL_TEST_DESC, PERSON_COUNTY, ETHNICITY, PROV_COUNTY, FACILITY_COUNTY, LENGTH_RESIDENCY_IN_USA, PERSON_CITY, PROV_CITY, FACILITY_CITY, REFERRING_FACILITY, TESTING_FACILITY, PFI, STATENO, PERSON_PLUS4, PROV_PLUS4, FACILITY_PLUS4, BIRTH_COUNTRY, PERSON_PHONE, PROV_PHONE, FACILITY_PHONE, LOINC_DESC, TEST_RESULT, MNAME, SUFFIX, FNAME, LNAME, AGE, PERSON_ZIP, PROV_ZIP, FACILITY_ZIP, UNIT_MEASURE_CODE, PERSON_INTERNAL_ID, PERSON_EXTERNAL_ID, PERSON_CENSUS_TRACT, PROV_CENSUS_TRACT, FACILITY_CENSUS_TRACT, DIN, PROV_FNAME, PROV_MNAME, PROV_SUFFIX, SSN, MATCHED_PROCESS_DATE,CALC_COLLECTION_DATE,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, CREATE_UID, CREATE_DATE, FLUX_MATCHED_PROCESS_DATE)
			values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,trim(upper(?15)),trim(upper(?16)),trim(upper(?17)),trim(upper(?18)),trim(upper(?19)),trim(upper(?20)),trim(upper(?21)),trim(upper(?22)),trim(upper(?23)),trim(upper(?24)),trim(upper(?25)),trim(upper(?26)),trim(upper(?27)),trim(upper(?28)),trim(upper(?29)),trim(upper(?30)),trim(upper(?31)),trim(upper(?32)),trim(upper(?33)),trim(upper(?34)),trim(upper(?35)),trim(upper(?36)),trim(upper(?37)),trim(upper(?38)),trim(upper(?39)),trim(upper(?40)),trim(upper(?41)),trim(upper(?42)),trim(upper(?43)),trim(upper(?44)),trim(upper(?45)),trim(upper(?46)),trim(upper(?47)),trim(upper(?48)),trim(upper(?49)),trim(upper(?50)),trim(upper(?51)),trim(upper(?52)),trim(upper(?53)),trim(upper(?54)),trim(upper(?55)),trim(upper(?56)),trim(upper(?57)),trim(upper(?58)),trim(upper(?59)),trim(upper(?60)),trim(upper(?61)),trim(upper(?62)),trim(upper(?63)),trim(upper(?64)),trim(upper(?65)),trim(upper(?66)),trim(upper(?67)),trim(upper(?68)),trim(upper(?69)),trim(upper(?70)),trim(upper(?71)),trim(upper(?72)),trim(upper(?73)),trim(upper(?74)),trim(upper(?75)),trim(upper(?76)),trim(upper(?77)),trim(upper(?78)),trim(upper(?79)),trim(upper(?80)),trim(upper(?81)),trim(upper(?82)),trim(upper(?83)),trim(upper(?84)),trim(upper(?85)),trim(upper(?86)),trim(upper(?87)),trim(upper(?88)),trim(upper(?89)),trim(upper(?90)),trim(upper(?91)),trim(upper(?92)),trim(upper(?93)),trim(upper(?94)),trim(upper(?95)),trim(upper(?96)),trim(upper(?97)),trim(upper(?98)),trim(upper(?99)),trim(upper(?100)),?101,?102,?103,?104,?105,?106,trim(upper(?107)),?108,?109)
			""";

	/** The values of {@link #INSERT_STD_HIV_DOCUMENT}, in order. */
	static Object[] insertStdHivDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				date(map.get("OBSERVATION_DATE")), date(map.get("COLLECTION_DATE")), date(map.get("RESULT_REPORT_DATE")), date(map.get("EHARS_IMPORT_DATE")),
				date(map.get("DATE_RECEIVED")), date(map.get("REVIEW_DATE")), date(map.get("NYC_SHIP_DATE")), number(map.get("DOCUMENT_ID")),
				number(map.get("LAB_SRC_ID")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("MATCH_PASS")), number(map.get("QS_PARENT_DOC_ID")),
				number(map.get("AGE_CATEGORY")), number(map.get("MATCH_WEIGHT")), text(map.get("RACE_ASIAN")), text(map.get("RACE_BLACK")),
				text(map.get("RACE_INDIAN")), text(map.get("RACE_MULTIPLE")), text(map.get("RACE_OTHER")), text(map.get("RACE_PACIFIC")),
				text(map.get("RACE_UNKNOWN")), text(map.get("RACE_WHITE")), text(map.get("EHARS_STATUS_CD")), text(map.get("DOB_TEXT")),
				text(map.get("PERSON_LATITUDE")), text(map.get("PROV_LATITUDE")), text(map.get("LOINC")), text(map.get("SPECIMEN_SOURCE_CD")),
				text(map.get("REVIEW_UID")), text(map.get("SENDING_FACILITY_CLIA")), text(map.get("FACILITY_LATITUDE")), text(map.get("INTERPRET_CD")),
				text(map.get("PERSON_ADDRESS")), text(map.get("MATCHED_STATENO")), text(map.get("PERSON_COUNTRY")), text(map.get("PERSON_LONGITUDE")),
				text(map.get("PROV_LONGITUDE")), text(map.get("FACILITY_LONGITUDE")), text(map.get("PROV_LNAME")), text(map.get("FACILITY_NAME")),
				text(map.get("SENDING_FACILITY_NAME")), text(map.get("PERSON_STATE")), text(map.get("GENDER")), text(map.get("INSURANCE_CD")),
				text(map.get("PROV_STATE")), text(map.get("FACILITY_STATE")), text(map.get("MATCH_CD")), text(map.get("HIV_VACCINE_RECIP")),
				text(map.get("RESIDE_OUTSIDE_USA")), text(map.get("AREA_OF_BIRTH")), text(map.get("MEDICAID_NUMBER")), text(map.get("LOCAL_TEST_CODE")),
				text(map.get("PROV_ADDRESS")), text(map.get("FACILITY_ADDRESS")), text(map.get("ACCESSION_NUMBER")), text(map.get("MED_RECORD_NUMBER")),
				text(map.get("INSURANCE_NUMBER")), text(map.get("NYSID")), text(map.get("SPECIMEN_SOURCE_NAME")), text(map.get("LOCAL_TEST_DESC")),
				text(map.get("PERSON_COUNTY")), text(map.get("ETHNICITY")), text(map.get("PROV_COUNTY")), text(map.get("FACILITY_COUNTY")),
				text(map.get("LENGTH_RESIDENCY_IN_USA")), text(map.get("PERSON_CITY")), text(map.get("PROV_CITY")), text(map.get("FACILITY_CITY")),
				text(map.get("REFERRING_FACILITY")), text(map.get("TESTING_FACILITY")), text(map.get("PFI")), text(map.get("STATENO")),
				text(map.get("PERSON_PLUS4")), text(map.get("PROV_PLUS4")), text(map.get("FACILITY_PLUS4")), text(map.get("BIRTH_COUNTRY")),
				text(map.get("PERSON_PHONE")), text(map.get("PROV_PHONE")), text(map.get("FACILITY_PHONE")), text(map.get("LOINC_DESC")),
				text(map.get("TEST_RESULT")), text(map.get("MNAME")), text(map.get("SUFFIX")), text(map.get("FNAME")),
				text(map.get("LNAME")), text(map.get("AGE")), text(map.get("PERSON_ZIP")), text(map.get("PROV_ZIP")),
				text(map.get("FACILITY_ZIP")), text(map.get("UNIT_MEASURE_CODE")), text(map.get("PERSON_INTERNAL_ID")), text(map.get("PERSON_EXTERNAL_ID")),
				text(map.get("PERSON_CENSUS_TRACT")), text(map.get("PROV_CENSUS_TRACT")), text(map.get("FACILITY_CENSUS_TRACT")), text(map.get("DIN")),
				text(map.get("PROV_FNAME")), text(map.get("PROV_MNAME")), text(map.get("PROV_SUFFIX")), text(map.get("SSN")),
				date(map.get("MATCHED_PROCESS_DATE")), date(map.get("CALC_COLLECTION_DATE")), text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")),
				text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), text(map.get("CREATE_UID")), text(map.get("CREATE_DATE")),
				date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertStdStdDocument}, as the former mapper had it (119 bound values). */
	static final String INSERT_STD_STD_DOCUMENT = """
			insert into STD_STD_ECLRS_DOC(CREATE_UID, DRAW_DATE, OBSERVATION_DATE, COLLECTION_DATE, RESULT_REPORT_DATE, DATE_SENT, REVIEW_DATE, DATE_RECEIVED, CREATE_DATE, EHARS_IMPORT_DATE, NYC_SHIP_DATE, DOCUMENT_ID, LAB_SRC_ID, AGE_CATEGORY, CURRENT_PROCESS_STATUS, QS_PARENT_DOC_ID, MATCH_WEIGHT, MATCH_PASS, CRBATCHNUM, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, PROCESSED, EHARS_STATUS_CD, DOB_TEXT, PERSON_LATITUDE, PROV_LATITUDE, FACILITY_LATITUDE, LOINC, SPECIMEN_SOURCE_CD, PROVIDER_TYPE, LAST_NEG_HIV_TEST_DATE, SENDING_FACILITY_CLIA, REVIEW_UID, PERSON_ADDRESS, INTERPRET_CD, MATCHED_STATENO, PERSON_COUNTRY, PERSON_LONGITUDE, PROV_LONGITUDE, FACILITY_LONGITUDE, PROV_LNAME, FACILITY_NAME, SENDING_FACILITY_NAME, PERSON_STATE, PERSON_CENSUS_TRACT_SUFFIX, GENDER, INSURANCE_CD, PROV_STATE, FACILITY_STATE, HIV_VACCINE_RECIP, OTHER_IMMUNE_INFECTION, RESIDE_OUTSIDE_USA, PROV_CENSUS_TRACT_SUFFIX, FACILITY_CENSUS_TRACT_SUFFIX, CASEKIND, AREA_OF_BIRTH, MEDICAID_NUMBER, LOCAL_TEST_CODE, PROV_ADDRESS, FACILITY_ADDRESS, ACCESSION_NUMBER, NYSID, INSURANCE_NUMBER, MED_RECORD_NUMBER, SPECIMEN_SOURCE_NAME, TEST_RESULT_RAW, LOCAL_TEST_DESC, PERSON_COUNTY, ETHNICITY, PROV_COUNTY, FACILITY_COUNTY, LENGTH_RESIDENCY_IN_USA, PERSON_CITY, PROV_CITY, FACILITY_CITY, REFERRING_FACILITY, TESTING_FACILITY, PERSON_PLUS4, PROV_PLUS4, FACILITY_PLUS4, MATCH_CD, BIRTH_COUNTRY, PERSON_PHONE, PROV_PHONE, FACILITY_PHONE, LOINC_DESC, TEST_RESULT, MNAME, SUFFIX, FNAME, LNAME, AGE, PERSON_ZIP, PROV_ZIP, FACILITY_ZIP, UNIT_MEASURE_CODE, PERSON_INTERNAL_ID, PERSON_EXTERNAL_ID, PERSON_CENSUS_TRACT, PROV_CENSUS_TRACT, FACILITY_CENSUS_TRACT, DIN, RECB, PROV_FNAME, PROV_MNAME, PROV_SUFFIX, SSN, MATCHED_PROCESS_DATE,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
			values(trim(upper(?1)),?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,trim(upper(?20)),trim(upper(?21)),trim(upper(?22)),trim(upper(?23)),trim(upper(?24)),trim(upper(?25)),trim(upper(?26)),trim(upper(?27)),trim(upper(?28)),trim(upper(?29)),trim(upper(?30)),trim(upper(?31)),trim(upper(?32)),trim(upper(?33)),trim(upper(?34)),trim(upper(?35)),trim(upper(?36)),trim(upper(?37)),trim(upper(?38)),trim(upper(?39)),trim(upper(?40)),trim(upper(?41)),trim(upper(?42)),trim(upper(?43)),trim(upper(?44)),trim(upper(?45)),trim(upper(?46)),trim(upper(?47)),trim(upper(?48)),trim(upper(?49)),trim(upper(?50)),trim(upper(?51)),trim(upper(?52)),trim(upper(?53)),trim(upper(?54)),trim(upper(?55)),trim(upper(?56)),trim(upper(?57)),trim(upper(?58)),trim(upper(?59)),trim(upper(?60)),trim(upper(?61)),trim(upper(?62)),trim(upper(?63)),trim(upper(?64)),trim(upper(?65)),trim(upper(?66)),trim(upper(?67)),trim(upper(?68)),trim(upper(?69)),trim(upper(?70)),trim(upper(?71)),trim(upper(?72)),trim(upper(?73)),trim(upper(?74)),trim(upper(?75)),trim(upper(?76)),trim(upper(?77)),trim(upper(?78)),trim(upper(?79)),trim(upper(?80)),trim(upper(?81)),trim(upper(?82)),trim(upper(?83)),trim(upper(?84)),trim(upper(?85)),trim(upper(?86)),trim(upper(?87)),trim(upper(?88)),trim(upper(?89)),trim(upper(?90)),trim(upper(?91)),trim(upper(?92)),trim(upper(?93)),trim(upper(?94)),trim(upper(?95)),trim(upper(?96)),trim(upper(?97)),trim(upper(?98)),trim(upper(?99)),trim(upper(?100)),trim(upper(?101)),trim(upper(?102)),trim(upper(?103)),trim(upper(?104)),trim(upper(?105)),trim(upper(?106)),trim(upper(?107)),trim(upper(?108)),trim(upper(?109)),trim(upper(?110)),trim(upper(?111)),trim(upper(?112)),trim(upper(?113)),?114,?115,?116,?117,?118,?119)
			""";

	/** The values of {@link #INSERT_STD_STD_DOCUMENT}, in order. */
	static Object[] insertStdStdDocumentArgs(Map<?, ?> map) {
		return new Object[] {
				text(map.get("CREATE_UID")), date(map.get("DRAW_DATE")), date(map.get("OBSERVATION_DATE")), date(map.get("COLLECTION_DATE")),
				date(map.get("RESULT_REPORT_DATE")), date(map.get("DATE_SENT")), date(map.get("REVIEW_DATE")), date(map.get("DATE_RECEIVED")),
				date(map.get("CREATE_DATE")), date(map.get("EHARS_IMPORT_DATE")), date(map.get("NYC_SHIP_DATE")), number(map.get("DOCUMENT_ID")),
				number(map.get("LAB_SRC_ID")), number(map.get("AGE_CATEGORY")), number(map.get("CURRENT_PROCESS_STATUS")), number(map.get("QS_PARENT_DOC_ID")),
				number(map.get("MATCH_WEIGHT")), number(map.get("MATCH_PASS")), number(map.get("CRBATCHNUM")), text(map.get("RACE_ASIAN")),
				text(map.get("RACE_BLACK")), text(map.get("RACE_INDIAN")), text(map.get("RACE_MULTIPLE")), text(map.get("RACE_OTHER")),
				text(map.get("RACE_PACIFIC")), text(map.get("RACE_UNKNOWN")), text(map.get("RACE_WHITE")), text(map.get("PROCESSED")),
				text(map.get("EHARS_STATUS_CD")), text(map.get("DOB_TEXT")), text(map.get("PERSON_LATITUDE")), text(map.get("PROV_LATITUDE")),
				text(map.get("FACILITY_LATITUDE")), text(map.get("LOINC")), text(map.get("SPECIMEN_SOURCE_CD")), text(map.get("PROVIDER_TYPE")),
				text(map.get("LAST_NEG_HIV_TEST_DATE")), text(map.get("SENDING_FACILITY_CLIA")), text(map.get("REVIEW_UID")), text(map.get("PERSON_ADDRESS")),
				text(map.get("INTERPRET_CD")), text(map.get("MATCHED_STATENO")), text(map.get("PERSON_COUNTRY")), text(map.get("PERSON_LONGITUDE")),
				text(map.get("PROV_LONGITUDE")), text(map.get("FACILITY_LONGITUDE")), text(map.get("PROV_LNAME")), text(map.get("FACILITY_NAME")),
				text(map.get("SENDING_FACILITY_NAME")), text(map.get("PERSON_STATE")), text(map.get("PERSON_CENSUS_TRACT_SUFFIX")), text(map.get("GENDER")),
				text(map.get("INSURANCE_CD")), text(map.get("PROV_STATE")), text(map.get("FACILITY_STATE")), text(map.get("HIV_VACCINE_RECIP")),
				text(map.get("OTHER_IMMUNE_INFECTION")), text(map.get("RESIDE_OUTSIDE_USA")), text(map.get("PROV_CENSUS_TRACT_SUFFIX")), text(map.get("FACILITY_CENSUS_TRACT_SUFFIX")),
				text(map.get("CASEKIND")), text(map.get("AREA_OF_BIRTH")), text(map.get("MEDICAID_NUMBER")), text(map.get("LOCAL_TEST_CODE")),
				text(map.get("PROV_ADDRESS")), text(map.get("FACILITY_ADDRESS")), text(map.get("ACCESSION_NUMBER")), text(map.get("NYSID")),
				text(map.get("INSURANCE_NUMBER")), text(map.get("MED_RECORD_NUMBER")), text(map.get("SPECIMEN_SOURCE_NAME")), text(map.get("TEST_RESULT_RAW")),
				text(map.get("LOCAL_TEST_DESC")), text(map.get("PERSON_COUNTY")), text(map.get("ETHNICITY")), text(map.get("PROV_COUNTY")),
				text(map.get("FACILITY_COUNTY")), text(map.get("LENGTH_RESIDENCY_IN_USA")), text(map.get("PERSON_CITY")), text(map.get("PROV_CITY")),
				text(map.get("FACILITY_CITY")), text(map.get("REFERRING_FACILITY")), text(map.get("TESTING_FACILITY")), text(map.get("PERSON_PLUS4")),
				text(map.get("PROV_PLUS4")), text(map.get("FACILITY_PLUS4")), text(map.get("MATCH_CD")), text(map.get("BIRTH_COUNTRY")),
				text(map.get("PERSON_PHONE")), text(map.get("PROV_PHONE")), text(map.get("FACILITY_PHONE")), text(map.get("LOINC_DESC")),
				text(map.get("TEST_RESULT")), text(map.get("MNAME")), text(map.get("SUFFIX")), text(map.get("FNAME")),
				text(map.get("LNAME")), text(map.get("AGE")), text(map.get("PERSON_ZIP")), text(map.get("PROV_ZIP")),
				text(map.get("FACILITY_ZIP")), text(map.get("UNIT_MEASURE_CODE")), text(map.get("PERSON_INTERNAL_ID")), text(map.get("PERSON_EXTERNAL_ID")),
				text(map.get("PERSON_CENSUS_TRACT")), text(map.get("PROV_CENSUS_TRACT")), text(map.get("FACILITY_CENSUS_TRACT")), text(map.get("DIN")),
				text(map.get("RECB")), text(map.get("PROV_FNAME")), text(map.get("PROV_MNAME")), text(map.get("PROV_SUFFIX")),
				text(map.get("SSN")), date(map.get("MATCHED_PROCESS_DATE")), text(map.get("FLUX_MATCH_CD")), text(map.get("FLUX_POT_STATENO")),
				text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertRawEditedHIVLabDocumentWithUpdateData}, as the former mapper had it (222 bound values). */
	static final String INSERT_RAW_EDITED_HIV_DOCUMENT = """
			insert into RAW_HIV_ECLRS_DOC(ZLRFACILITYCODE, ZLRFACILITYNAME, OBRPROVIDERID, OBRPROVIDERLASTNAME, OBRPROVIDERFIRSTNAME, OBRPROVIDERMIDDLENAME, OBRPROVIDERNAMESUFFIX, OBRPROVIDERIDALT1, OBRPROVIDERIDALT2, MSHVERSIONID, REVIEW_UID, PIDDIN, PIDPATIENTLANGUAGE, OBXUNITOFMEASURE, OBXPRODUCERCLIAID, OBXPRODUCERLABNAME, OBXOBSERVATIONMETHOD, PIDLENGTHOFRESIDENCY, OBXABNORMALFLAG, PIDLASTNAME, PIDFIRSTNAME, PIDMIDDLENAME, PIDNAMESUFFIX, OBXLOINCDESCSHORT, OBXOBSERVATIONRESULTTEXTSHORT, PIDHOMEPHONE, PIDWORKPHONE, ZLRNEXTOFKINLASTNAME, ZLRNEXTOFKINFIRSTNAME, ZLRNEXTOFKINMIDDLENAME, ZLRNEXTOFKINNAMESUFFIX, ZLRNEXTOFKINRELATIONSHIP, ZLRNEXTOFKINPHONE, PIDCELLPHONE, OBRCALLBACKPHONE, OBRCALLBACKPHONEALT, ZLRFACILITYPHONE, OBRPROVIDERFAX, OBXLOCALRESULTCODE, PIDSETID, OBRSETID, OBXPROGRAMCODE, PIDFACILITYIDEXTERNAL, PIDFACILITYIDINTERNAL, PIDCOUNTRYOFORIGIN, PIDUPDATEID, OBXLOGONID, PROGRAMCODEUPDATEID, OBXPROGRAMCODEUPDATEID, MSHPROCESSINGID, MSHSOURCETYPE, OBXVALUETYPE, MSHMESSAGEDATERAW, PIDDATEOFBIRTHRAW, PIDDEATHDATERAW, OBRCOLLECTIONDATERAW, OBRRESULTREPORTDATERAW, OBROBSERVATIONDATERAW, OBRRECEIVEDDATERAW, OBXOBSERVATIONDATERAW, OBXANALYSISDATERAW, PIDRACECODERAW, OBRSPECIMENSOURCENAME, OBRINITRESLOINCDESC, OBRINITRESLOCALTESTDESC, OBRINITRESOBSERVATIONTEXT, OBXLOINCDESC, OBXSNOMEDDESC, OBXLOCALTESTDESC, OBXLOCALRESULTDESC, OBXICD_DESC, PIDCOUNTYCODEORIGINAL, PIDMEDICALRECORDNUMBER, OBRACCESSIONNUM, OBRINITRESPLACERSORDERNUM, OBRINITRESACCESSIONNUM, PIDASSIGNAUTHOID, OBRLOCALCODE, OBRANALYSISDESC, OBRPATHOLOGISTID, OBRPATHOLOGISTLASTNAME, OBRPATHOLOGISTFIRSTNAME, OBRPATHOLOGISTMIDDLENAME, OBRPATHOLOGISTNAMESUFFIX, MSHMESSAGECONTROLID, PIDPATIENTIDEXTERNAL, PIDPATIENTIDINTERNAL, PIDPATIENTIDALT, PIDPATIENTACCOUNTNUM, PIDMOTHERSID, PIDMEDICAIDNUMBER, ZLRAGE, OBRINITRESLOCALTESTCODE, OBRINITRESSUBID, OBXOBSERVATIONSUBID, OBXSNOMEDCODE, OBXLOCALTESTCODE, OBXICD_CODETYPE, OBXICD_CODE, PIDCOUNTYGAZCODEORIG, PIDTYPEOFINSURANCE, MSHSENDINGFACILITYNAME, PIDSSN, MSHFILENAME, OBRSNOMEDCODE, PIDNYSID, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, PIDCOUNTRYCODE, ZLRNEXTOFKINADDRESSLINE1, ZLRNEXTOFKINADDRESSLINE2, ZLRNEXTOFKINCITY, ZLRNEXTOFKINSTATE, ZLRNEXTOFKINZIPCODE, ZLRNEXTOFKINCOUNTRY, PIDEMAIL, ZLRPROVIDERADDRESSLINE1, ZLRPROVIDERADDRESSLINE2, ZLRPROVIDERCITY, ZLRPROVIDERSTATE, ZLRPROVIDERZIPCODE, ZLRPROVIDERCOUNTRY, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2, ZLRFACILITYCITY, ZLRFACILITYSTATE, ZLRFACILITYZIPCODE, ZLRFACILITYCOUNTRY, OBRPROVIDEREMAIL, PIDASSIGNAUTHDESC, OBRPLACERSORDERNUM, OBRFILLERSORDERNUM, OBXREFERENCERANGE, MSHSENDINGFACILITYCLIA, MSHLABPFI, PIDFACILITYIDALT, PIDETHNICGROUPCODE, OBRLOINCCODE, OBRSPECIMENSOURCECODE, OBRPROVIDERIDTYPECODE, OBRINITRESLOINCCODE, OBRPROVIDERIDTYPECODEALT1, OBRPROVIDERIDTYPECODEALT2, OBXSETID, OBXLOINCCODE, OBXSENDINGFACILITYCLIA, PIDNOTE, OBRRELEVANTCLINICALINFO, OBRNOTE, OBXOBSERVATIONRESULTTEXT, OBXNOTE, PIDSMSA, MSHFILECONTENTTYPE, MSHDELETEDIND, PIDSEXCODE, PIDRACECODE, PIDRACEASIAN, PIDRACEBLACK, PIDRACENATIVEAMERALASKAN, PIDRACEOTHER, PIDRACEPACIFIC, PIDRACEUNKNOWN, PIDRACEWHITE, PIDMARITALSTATUSCODE, PIDDEATHIND, PIDUSBORNIND, PIDVACCINETRIALIND, PIDNONHUMANIND, PIDDELETEDIND, OBRRESULTSTATUSCODE, OBRDELETEDIND, OBXRESULTSTATUSCODE, OBXPROCESSEDIND, OBXLEADPURPOSE, OBXDELETEDIND, MSHMESSAGEDATE, MSHCREATEDATE, MSHDELETEDATE, PIDCREATEDATE, PIDDATEOFBIRTH, PIDDEATHDATE, PIDUPDATEDATE, PIDDELETEDATE, OBRCREATEDATE, OBRCOLLECTIONDATE, OBRRESULTREPORTDATE, OBRDELETEDATE, OBROBSERVATIONDATE, OBRRECEIVEDDATE, OBXOBSERVATIONDATE, OBXPROCESSEDDATE, OBXDELETEDATE, PROGRAMCODEUPDATEDATE, OBXPROGRAMCODEUPDATEDATE, OBXCREATEDATE, OBXANALYSISDATE, OBXDISEASE, OBXDISEASEGROUP, PIDCOUNTYCODE, OBXCOUNTYCODE, MSHMESSAGEMASTERKEY, PIDPATIENTKEY, PIDMESSAGEMASTERKEY, OBROBSERVATIONREQUESTKEY, ZLROBSERVATIONREQUESTKEY, OBRPATIENTKEY, OBRPATIENTKEYORIG, OBRINITRESKEY, OBXOBSERVATIONRESULTKEY, OBXOBSERVATIONREQUESTKEY, OBXOLDESTDUPLICATERESULTKEY, DOCUMENT_ID, INTERPRETATION_STD_DOC_ID, CURRENT_PROCESS_STATUS, HASH_UNIQUE_NUMBER, PIDLONGITUDE, PIDLATITUDE, PROCESS_DATE, NYC_SHIP_DATE, REVIEW_DATE)
			values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), trim(upper(?41)), trim(upper(?42)), trim(upper(?43)), trim(upper(?44)), trim(upper(?45)), trim(upper(?46)), trim(upper(?47)), trim(upper(?48)), trim(upper(?49)), trim(upper(?50)), trim(upper(?51)), trim(upper(?52)), trim(upper(?53)), trim(upper(?54)), trim(upper(?55)), trim(upper(?56)), trim(upper(?57)), trim(upper(?58)), trim(upper(?59)), trim(upper(?60)), trim(upper(?61)), trim(upper(?62)), trim(upper(?63)), trim(upper(?64)), trim(upper(?65)), trim(upper(?66)), trim(upper(?67)), trim(upper(?68)), trim(upper(?69)), trim(upper(?70)), trim(upper(?71)), trim(upper(?72)), trim(upper(?73)), trim(upper(?74)), trim(upper(?75)), trim(upper(?76)), trim(upper(?77)), trim(upper(?78)), trim(upper(?79)), trim(upper(?80)), trim(upper(?81)), trim(upper(?82)), trim(upper(?83)), trim(upper(?84)), trim(upper(?85)), trim(upper(?86)), trim(upper(?87)), trim(upper(?88)), trim(upper(?89)), trim(upper(?90)), trim(upper(?91)), trim(upper(?92)), trim(upper(?93)), trim(upper(?94)), trim(upper(?95)), trim(upper(?96)), trim(upper(?97)), trim(upper(?98)), trim(upper(?99)), trim(upper(?100)), trim(upper(?101)), trim(upper(?102)), trim(upper(?103)), trim(upper(?104)), trim(upper(?105)), trim(upper(?106)), trim(upper(?107)), null, trim(upper(?108)), trim(upper(?109)), trim(upper(?110)), trim(upper(?111)), trim(upper(?112)), trim(upper(?113)), trim(upper(?114)), trim(upper(?115)), trim(upper(?116)), trim(upper(?117)), trim(upper(?118)), trim(upper(?119)), trim(upper(?120)), trim(upper(?121)), trim(upper(?122)), trim(upper(?123)), trim(upper(?124)), trim(upper(?125)), trim(upper(?126)), trim(upper(?127)), trim(upper(?128)), trim(upper(?129)), trim(upper(?130)), trim(upper(?131)), trim(upper(?132)), trim(upper(?133)), trim(upper(?134)), trim(upper(?135)), trim(upper(?136)), trim(upper(?137)), trim(upper(?138)), trim(upper(?139)), trim(upper(?140)), trim(upper(?141)), trim(upper(?142)), trim(upper(?143)), trim(upper(?144)), trim(upper(?145)), trim(upper(?146)), trim(upper(?147)), trim(upper(?148)), trim(upper(?149)), trim(upper(?150)), trim(upper(?151)), trim(upper(?152)), trim(upper(?153)), trim(upper(?154)), trim(upper(?155)), trim(upper(?156)), trim(upper(?157)), trim(upper(?158)), trim(upper(?159)), trim(upper(?160)), trim(upper(?161)), trim(upper(?162)), trim(upper(?163)), trim(upper(?164)), trim(upper(?165)), trim(upper(?166)), trim(upper(?167)), trim(upper(?168)), trim(upper(?169)), trim(upper(?170)), trim(upper(?171)), trim(upper(?172)), trim(upper(?173)), trim(upper(?174)), trim(upper(?175)), trim(upper(?176)), trim(upper(?177)), trim(upper(?178)), trim(upper(?179)), trim(upper(?180)), trim(upper(?181)), ?182, ?183, ?184, ?185, ?186, ?187, ?188, ?189, ?190, ?191, ?192, ?193, ?194, ?195, ?196, ?197, ?198, ?199, ?200, ?201, ?202, ?203, ?204, ?205, ?206, ?207, ?208, ?209, ?210, ?211, ?212, ?213, ?214, ?215, ?216, ?217, ?218, ?219, ?220, ?221, ?222)
			""";

	/** The values of {@link #INSERT_RAW_EDITED_HIV_DOCUMENT}, in order. */
	static Object[] insertRawEditedHIVLabDocumentWithUpdateDataArgs(Map<?, ?> map) {
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
				text(map.get("ZLRNEXTOFKINCOUNTRY")), text(map.get("PIDEMAIL")), text(map.get("ZLRPROVIDERADDRESSLINE1")), text(map.get("ZLRPROVIDERADDRESSLINE2")),
				text(map.get("ZLRPROVIDERCITY")), text(map.get("ZLRPROVIDERSTATE")), text(map.get("ZLRPROVIDERZIPCODE")), text(map.get("ZLRPROVIDERCOUNTRY")),
				text(map.get("ZLRFACILITYADDRESSLINE1")), text(map.get("ZLRFACILITYADDRESSLINE2")), text(map.get("ZLRFACILITYCITY")), text(map.get("ZLRFACILITYSTATE")),
				text(map.get("ZLRFACILITYZIPCODE")), text(map.get("ZLRFACILITYCOUNTRY")), text(map.get("OBRPROVIDEREMAIL")), text(map.get("PIDASSIGNAUTHDESC")),
				text(map.get("OBRPLACERSORDERNUM")), text(map.get("OBRFILLERSORDERNUM")), text(map.get("OBXREFERENCERANGE")), text(map.get("MSHSENDINGFACILITYCLIA")),
				text(map.get("MSHLABPFI")), text(map.get("PIDFACILITYIDALT")), text(map.get("PIDETHNICGROUPCODE")), text(map.get("OBRLOINCCODE")),
				text(map.get("OBRSPECIMENSOURCECODE")), text(map.get("OBRPROVIDERIDTYPECODE")), text(map.get("OBRINITRESLOINCCODE")), text(map.get("OBRPROVIDERIDTYPECODEALT1")),
				text(map.get("OBRPROVIDERIDTYPECODEALT2")), text(map.get("OBXSETID")), text(map.get("OBXLOINCCODE")), text(map.get("OBXSENDINGFACILITYCLIA")),
				text(map.get("PIDNOTE")), text(map.get("OBRRELEVANTCLINICALINFO")), text(map.get("OBRNOTE")), text(map.get("OBXOBSERVATIONRESULTTEXT")),
				text(map.get("OBXNOTE")), text(map.get("PIDSMSA")), text(map.get("MSHFILECONTENTTYPE")), text(map.get("MSHDELETEDIND")),
				text(map.get("PIDSEXCODE")), text(map.get("PIDRACECODE")), text(map.get("PIDRACEASIAN")), text(map.get("PIDRACEBLACK")),
				text(map.get("PIDRACENATIVEAMERALASKAN")), text(map.get("PIDRACEOTHER")), text(map.get("PIDRACEPACIFIC")), text(map.get("PIDRACEUNKNOWN")),
				text(map.get("PIDRACEWHITE")), text(map.get("PIDMARITALSTATUSCODE")), text(map.get("PIDDEATHIND")), text(map.get("PIDUSBORNIND")),
				text(map.get("PIDVACCINETRIALIND")), text(map.get("PIDNONHUMANIND")), text(map.get("PIDDELETEDIND")), text(map.get("OBRRESULTSTATUSCODE")),
				text(map.get("OBRDELETEDIND")), text(map.get("OBXRESULTSTATUSCODE")), text(map.get("OBXPROCESSEDIND")), text(map.get("OBXLEADPURPOSE")),
				text(map.get("OBXDELETEDIND")), text(map.get("MSHMESSAGEDATE")), text(map.get("MSHCREATEDATE")), text(map.get("MSHDELETEDATE")),
				text(map.get("PIDCREATEDATE")), date(map.get("PIDDATEOFBIRTH")), text(map.get("PIDDEATHDATE")), text(map.get("PIDUPDATEDATE")),
				text(map.get("PIDDELETEDATE")), text(map.get("OBRCREATEDATE")), date(map.get("OBRCOLLECTIONDATE")), date(map.get("OBRRESULTREPORTDATE")),
				text(map.get("OBRDELETEDATE")), text(map.get("OBROBSERVATIONDATE")), text(map.get("OBRRECEIVEDDATE")), date(map.get("OBXOBSERVATIONDATE")),
				text(map.get("OBXPROCESSEDDATE")), text(map.get("OBXDELETEDATE")), text(map.get("PROGRAMCODEUPDATEDATE")), text(map.get("OBXPROGRAMCODEUPDATEDATE")),
				text(map.get("OBXCREATEDATE")), text(map.get("OBXANALYSISDATE")), number(map.get("OBXDISEASE")), number(map.get("OBXDISEASEGROUP")),
				number(map.get("PIDCOUNTYCODE")), number(map.get("OBXCOUNTYCODE")), number(map.get("MSHMESSAGEMASTERKEY")), number(map.get("PIDPATIENTKEY")),
				number(map.get("PIDMESSAGEMASTERKEY")), number(map.get("OBROBSERVATIONREQUESTKEY")), number(map.get("ZLROBSERVATIONREQUESTKEY")), number(map.get("OBRPATIENTKEY")),
				number(map.get("OBRPATIENTKEYORIG")), number(map.get("OBRINITRESKEY")), number(map.get("OBXOBSERVATIONRESULTKEY")), number(map.get("OBXOBSERVATIONREQUESTKEY")),
				number(map.get("OBXOLDESTDUPLICATERESULTKEY")), number(map.get("DOCUMENT_ID")), number(map.get("INTERPRETATION_STD_DOC_ID")), number(map.get("CURRENT_PROCESS_STATUS")),
				number(map.get("HASH_UNIQUE_NUMBER")), number(map.get("PIDLONGITUDE")), number(map.get("PIDLATITUDE")), date(map.get("PROCESS_DATE")),
				date(map.get("NYC_SHIP_DATE")), date(map.get("REVIEW_DATE"))
		};
	}

	/** The SQL of {@code updateHashUniqueNumber}, as the former mapper had it (1 bound values). */
	static final String UPDATE_HASH_UNIQUE_NUMBER = """
			update raw_hiv_Eclrs_Doc set HASH_UNIQUE_NUMBER = ORA_HASH(TO_CHAR(SUBSTR(TO_CLOB(mshsendingfacilityname|| mshsendingfacilityclia|| mshprocessingid|| mshversionid|| mshlabpfi|| mshfilecontenttype|| mshsourcetype|| pidsetid|| pidpatientidexternal|| pidfacilityidexternal|| pidpatientidinternal|| pidfacilityidinternal|| pidpatientidalt|| pidfacilityidalt|| pidlastname|| pidfirstname|| pidmiddlename|| pidnamesuffix|| piddateofbirthraw|| piddateofbirth|| pidsexcode|| pidracecode|| pidraceasian|| pidraceblack|| pidracenativeameralaskan|| pidraceother|| pidracepacific|| pidraceunknown|| pidracewhite|| pidracecoderaw|| pidaddressline1|| pidaddressline2|| pidcity|| pidstate|| pidzipcode|| pidcountrycode|| pidcountycodeoriginal|| pidhomephone|| pidworkphone|| pidpatientlanguage|| pidmaritalstatuscode|| pidpatientaccountnum|| pidssn|| pidmothersid|| pidethnicgroupcode|| piddeathdateraw|| piddeathdate|| piddeathind|| pidcountygazcodeorig|| pidmedicaidnumber|| piddin|| pidnysid|| pidusbornind|| pidcountryoforigin|| pidlengthofresidency|| pidtypeofinsurance|| pidvaccinetrialind|| pidcountycode|| pidsmsa|| pidlongitude|| pidlatitude|| pidnonhumanind|| obrplacersordernum|| obraccessionnum|| obrsnomedcode|| obrloinccode|| obrlocalcode|| obranalysisdesc|| obrcollectiondateraw|| obrcollectiondate|| obrspecimensourcecode|| obrspecimensourcename|| obrproviderid|| obrproviderlastname|| obrproviderfirstname|| obrprovidermiddlename|| obrprovidernamesuffix|| obrcallbackphone|| obrcallbackphonealt|| obrpathologistid|| obrpathologistlastname|| obrpathologistfirstname|| obrpathologistmiddlename|| obrpathologistnamesuffix|| obrfillersordernum|| obrobservationdateraw|| obrobservationdate|| obrreceiveddateraw|| obrreceiveddate|| obxvaluetype|| obxloinccode|| obxloincdesc|| obxloincdescshort|| obxsnomedcode|| obxsnomeddesc|| obxobservationresulttextshort|| obxunitofmeasure|| obxreferencerange|| obxabnormalflag|| obxobservationdateraw|| obxobservationdate|| obxproducercliaid|| obxproducerlabname|| obxobservationmethod|| obxprogramcode|| obxdisease|| obxlocaltestcode|| obxlocaltestdesc|| obxlocalresultcode|| obxlocalresultdesc|| obxsendingfacilityclia|| obxcountycode|| obxanalysisdateraw|| obxanalysisdate|| zlrprovideraddressline1|| zlrprovideraddressline2|| zlrprovidercity|| zlrproviderstate|| zlrproviderzipcode|| zlrprovidercountry|| zlrfacilitycode|| zlrfacilityname|| zlrfacilityaddressline1|| zlrfacilityaddressline2|| zlrfacilitycity|| zlrfacilitystate|| zlrfacilityzipcode|| zlrfacilitycountry|| zlrfacilityphone|| zlrage|| zlrnextofkinlastname|| zlrnextofkinfirstname|| zlrnextofkinmiddlename|| zlrnextofkinnamesuffix|| zlrnextofkinrelationship|| zlrnextofkinaddressline1|| zlrnextofkinaddressline2|| zlrnextofkincity|| zlrnextofkinzipcode|| zlrnextofkincountry|| zlrnextofkinphone|| pidmedicalrecordnumber)||obxobservationresulttext||obrrelevantclinicalinfo, 0,4000))) where document_id = ?1
			""";

	/** The SQL of {@code insertStdEditedHivLabDocumentWithUpdateData}, as the former mapper had it (107 bound values). */
	static final String INSERT_STD_EDITED_HIV_DOCUMENT = """
			insert into STD_HIV_ECLRS_DOC(SSN, PROV_FNAME, PROV_MNAME, PROV_SUFFIX, DIN, PERSON_CENSUS_TRACT, PROV_CENSUS_TRACT, FACILITY_CENSUS_TRACT, PERSON_CITY, UNIT_MEASURE_CODE, PERSON_INTERNAL_ID, PERSON_EXTERNAL_ID, AGE, PERSON_ZIP, PROV_ZIP, FACILITY_ZIP, FNAME, LNAME, MNAME, SUFFIX, LOINC_DESC, TEST_RESULT, BIRTH_COUNTRY, PERSON_PHONE, PROV_PHONE, FACILITY_PHONE, PERSON_PLUS4, PROV_PLUS4, FACILITY_PLUS4, PROV_CITY, FACILITY_CITY, REFERRING_FACILITY, TESTING_FACILITY, PFI, STATENO, PERSON_COUNTY, ETHNICITY, PROV_COUNTY, FACILITY_COUNTY, LENGTH_RESIDENCY_IN_USA, SPECIMEN_SOURCE_NAME, LOCAL_TEST_DESC, MED_RECORD_NUMBER, INSURANCE_NUMBER, NYSID, ACCESSION_NUMBER, PROV_ADDRESS, FACILITY_ADDRESS, AREA_OF_BIRTH, MEDICAID_NUMBER, LOCAL_TEST_CODE, PERSON_STATE, GENDER, INSURANCE_CD, PROV_STATE, FACILITY_STATE, MATCH_CD, HIV_VACCINE_RECIP, RESIDE_OUTSIDE_USA, SENDING_FACILITY_NAME, FACILITY_NAME, PROV_LNAME, PERSON_LONGITUDE, PROV_LONGITUDE, FACILITY_LONGITUDE, PERSON_COUNTRY, INTERPRET_CD, PERSON_ADDRESS, MATCHED_STATENO, DOB_TEXT, PERSON_LATITUDE, PROV_LATITUDE, LOINC, SPECIMEN_SOURCE_CD, REVIEW_UID, SENDING_FACILITY_CLIA, FACILITY_LATITUDE, RACE_ASIAN, RACE_BLACK, RACE_INDIAN, RACE_MULTIPLE, RACE_OTHER, RACE_PACIFIC, RACE_UNKNOWN, RACE_WHITE, EHARS_STATUS_CD, MATCH_WEIGHT, DOCUMENT_ID, LAB_SRC_ID, CURRENT_PROCESS_STATUS, MATCH_PASS, QS_PARENT_DOC_ID, AGE_CATEGORY, OBSERVATION_DATE, COLLECTION_DATE, RESULT_REPORT_DATE, EHARS_IMPORT_DATE, DATE_RECEIVED, REVIEW_DATE, NYC_SHIP_DATE, MATCHED_PROCESS_DATE,CALC_COLLECTION_DATE,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE)
				values(trim(upper(?1)), trim(upper(?2)), trim(upper(?3)), trim(upper(?4)), trim(upper(?5)), trim(upper(?6)), trim(upper(?7)), trim(upper(?8)), trim(upper(?9)), trim(upper(?10)), trim(upper(?11)), trim(upper(?12)), trim(upper(?13)), trim(upper(?14)), trim(upper(?15)), trim(upper(?16)), trim(upper(?17)), trim(upper(?18)), trim(upper(?19)), trim(upper(?20)), trim(upper(?21)), trim(upper(?22)), trim(upper(?23)), trim(upper(?24)), trim(upper(?25)), trim(upper(?26)), trim(upper(?27)), trim(upper(?28)), trim(upper(?29)), trim(upper(?30)), trim(upper(?31)), trim(upper(?32)), trim(upper(?33)), trim(upper(?34)), trim(upper(?35)), trim(upper(?36)), trim(upper(?37)), trim(upper(?38)), trim(upper(?39)), trim(upper(?40)), trim(upper(?41)), trim(upper(?42)), trim(upper(?43)), trim(upper(?44)), trim(upper(?45)), trim(upper(?46)), trim(upper(?47)), trim(upper(?48)), trim(upper(?49)), trim(upper(?50)), trim(upper(?51)), trim(upper(?52)), trim(upper(?53)), trim(upper(?54)), trim(upper(?55)), trim(upper(?56)), trim(upper(?57)), trim(upper(?58)), trim(upper(?59)), trim(upper(?60)), trim(upper(?61)), trim(upper(?62)), trim(upper(?63)), trim(upper(?64)), trim(upper(?65)), trim(upper(?66)), trim(upper(?67)), trim(upper(?68)), trim(upper(?69)), trim(upper(?70)), trim(upper(?71)), trim(upper(?72)), trim(upper(?73)), trim(upper(?74)), trim(upper(?75)), trim(upper(?76)), trim(upper(?77)), trim(upper(?78)), trim(upper(?79)), trim(upper(?80)), trim(upper(?81)), trim(upper(?82)), trim(upper(?83)), trim(upper(?84)), trim(upper(?85)), trim(upper(?86)), ?87, ?88, ?89, ?90, ?91, ?92, ?93, ?94, ?95, ?96, ?97, ?98, ?99, ?100, ?101,?102,?103,?104,?105,?106,?107)
			""";

	/** The values of {@link #INSERT_STD_EDITED_HIV_DOCUMENT}, in order. */
	static Object[] insertStdEditedHivLabDocumentWithUpdateDataArgs(Map<?, ?> map) {
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
				text(map.get("FLUX_MATCHED_STATENO")), number(map.get("FLUX_QS_PARENT_DOC_ID")), date(map.get("FLUX_MATCHED_PROCESS_DATE"))
		};
	}

	/** The SQL of {@code insertStandardEcrlsRecord}, as the former mapper had it (81 bound values). */
	static final String INSERT_STANDARD_ECLRS_RECORD = """
			insert into STD_HIV_ECLRS_DOC(DOCUMENT_ID,LAB_SRC_ID,FNAME,LNAME,MNAME,SUFFIX,DOB_TEXT,BIRTH_COUNTRY,PERSON_PHONE,MED_RECORD_NUMBER,INSURANCE_CD,INSURANCE_NUMBER,NYSID,SSN,PROV_LNAME,PROV_FNAME,PROV_MNAME,PROV_SUFFIX,PROV_PHONE,FACILITY_NAME,FACILITY_PHONE,COLLECTION_DATE,OBSERVATION_DATE,RESULT_REPORT_DATE,LOINC_DESC,TEST_RESULT,PERSON_INTERNAL_ID,PERSON_EXTERNAL_ID,SENDING_FACILITY_NAME,SPECIMEN_SOURCE_CD,SPECIMEN_SOURCE_NAME,REFERRING_FACILITY,TESTING_FACILITY, PFI, SENDING_FACILITY_CLIA,PERSON_COUNTRY,ACCESSION_NUMBER,MEDICAID_NUMBER,LOCAL_TEST_DESC,AGE,PERSON_ZIP,GENDER,RACE_ASIAN,RACE_BLACK,RACE_INDIAN,RACE_MULTIPLE,RACE_OTHER,RACE_PACIFIC,RACE_UNKNOWN,RACE_WHITE,DIN,LOINC,UNIT_MEASURE_CODE,CURRENT_PROCESS_STATUS,ETHNICITY, INTERPRET_CD, AGE_CATEGORY, LENGTH_RESIDENCY_IN_USA, DATE_RECEIVED,PERSON_ADDRESS,PERSON_CITY,PERSON_STATE,PROV_ADDRESS,PROV_CITY,PROV_STATE,PROV_ZIP,FACILITY_ADDRESS,FACILITY_CITY,FACILITY_STATE,FACILITY_ZIP, MATCHED_STATENO, MATCH_CD, HIV_VACCINE_RECIP, CALC_COLLECTION_DATE, PROV_NPI, FACILITY_NPI,FLUX_MATCH_CD, FLUX_POT_STATENO, FLUX_MATCHED_STATENO, FLUX_QS_PARENT_DOC_ID, FLUX_MATCHED_PROCESS_DATE) values(
			?1,?2,upper(trim(?3)),upper(trim(?4)),upper(trim(?5)),upper(trim(?6)),trim(?7),upper(trim(?8)),trim(?9),upper(trim(?10)),upper(trim(?11)),upper(trim(?12)),upper(trim(?13)),trim(?14),upper(trim(?15)),upper(trim(?16)),upper(trim(?17)),upper(trim(?18)),trim(?19),upper(trim(?20)),trim(?21),?22,?23,?24,upper(trim(?25)),upper(trim(?26)),upper(trim(?27)),upper(trim(?28)),upper(trim(?29)),upper(trim(?30)),upper(trim(?31)),upper(trim(?32)),upper(trim(?33)),upper(trim(?34)),upper(trim(?35)),upper(trim(?36)),upper(trim(?37)),upper(trim(?38)),upper(trim(?39)),upper(trim(?40)),trim(?41),?42,?43,?44,?45,?46,?47,?48,?49,?50,?51,?52,?53,?54,?55,?56,?57, upper(trim(?58)), ?59,upper(trim(?60)),upper(trim(?61)),upper(trim(?62)),upper(trim(?63)),upper(trim(?64)),upper(trim(?65)),trim(?66),upper(trim(?67)),upper(trim(?68)),upper(trim(?69)),trim(?70), ?71, ?72, upper(trim(?73)),?74,upper(trim(?75)) ,upper(trim(?76)),?77,?78,?79,?80,?81)
			""";

	/** The values of {@link #INSERT_STANDARD_ECLRS_RECORD}, in order. */
	static Object[] insertStandardEcrlsRecordArgs(StdEcrlsLabDocVO vo) {
		return new Object[] {
				vo.getDOCUMENT_ID(), vo.getLAB_SRC_ID(), text(vo.getFNAME()), text(vo.getLNAME()),
				text(vo.getMNAME()), text(vo.getSUFFIX()), text(vo.getDOB_TEXT()), text(vo.getBIRTH_COUNTRY()),
				text(vo.getPERSON_PHONE()), text(vo.getMED_RECORD_NUMBER()), text(vo.getINSURANCE_CD()), text(vo.getINSURANCE_NUMBER()),
				text(vo.getNYSID()), text(vo.getSSN()), text(vo.getPROV_LNAME()), text(vo.getPROV_FNAME()),
				text(vo.getPROV_MNAME()), text(vo.getPROV_SUFFIX()), text(vo.getPROV_PHONE()), text(vo.getFACILITY_NAME()),
				text(vo.getFACILITY_PHONE()), date(vo.getCOLLECTION_DATE()), date(vo.getOBSERVATION_DATE()), date(vo.getRESULT_REPORT_DATE()),
				text(vo.getLOINC_DESC()), text(vo.getTEST_RESULT()), text(vo.getPERSON_INTERNAL_ID()), text(vo.getPERSON_EXTERNAL_ID()),
				text(vo.getSENDING_FACILITY_NAME()), text(vo.getSPECIMEN_SOURCE_CD()), text(vo.getSPECIMEN_SOURCE_NAME()), text(vo.getREFERRING_FACILITY()),
				text(vo.getTESTING_FACILITY()), text(vo.getPFI()), text(vo.getSENDING_FACILITY_CLIA()), text(vo.getPERSON_COUNTRY()),
				text(vo.getACCESSION_NUMBER()), text(vo.getMEDICAID_NUMBER()), text(vo.getLOCAL_TEST_DESC()), text(vo.getAGE()),
				text(vo.getPERSON_ZIP()), text(vo.getGENDER()), text(vo.getRACE_ASIAN()), text(vo.getRACE_BLACK()),
				text(vo.getRACE_INDIAN()), text(vo.getRACE_MULTIPLE()), text(vo.getRACE_OTHER()), text(vo.getRACE_PACIFIC()),
				text(vo.getRACE_UNKNOWN()), text(vo.getRACE_WHITE()), text(vo.getDIN()), text(vo.getLOINC()),
				text(vo.getUNIT_MEASURE_CODE()), vo.getCURRENT_PROCESS_STATUS(), text(vo.getETHNICITY()), text(vo.getINTERPRET_CD()),
				number(vo.getAGE_CATEGORY()), text(vo.getLENGTH_RESIDENCY_IN_USA()), date(vo.getDATE_RECEIVED()), text(vo.getPERSON_ADDRESS()),
				text(vo.getPERSON_CITY()), text(vo.getPERSON_STATE()), text(vo.getPROV_ADDRESS()), text(vo.getPROV_CITY()),
				text(vo.getPROV_STATE()), text(vo.getPROV_ZIP()), text(vo.getFACILITY_ADDRESS()), text(vo.getFACILITY_CITY()),
				text(vo.getFACILITY_STATE()), text(vo.getFACILITY_ZIP()), text(vo.getMATCHED_STATENO()), text(vo.getMATCH_CD()),
				text(vo.getHIV_VACCINE_RECIP()), date(vo.getCALC_COLLECTION_DATE()), text(vo.getPROV_NPI()), text(vo.getFACILITY_NPI()),
				text(vo.getFLUX_MATCH_CD()), text(vo.getFLUX_POT_STATENO()), text(vo.getFLUX_MATCHED_STATENO()), number(vo.getFLUX_QS_PARENT_DOC_ID()),
				date(vo.getFLUX_MATCHED_PROCESS_DATE())
		};
	}

	/**
	 * The SQL of {@code getMultiSpotDocuments}, as the former mapper had it; {@code %1$s} is the PFI filter (the former
	 * {@code <if test="pfiList != null">}, {@link #MULTI_SPOT_PFI_FILTER} with its placeholders, or nothing) and {@code %2$s} the
	 * placeholders of the LOINC codes (the former {@code <foreach>}).
	 */
	static final String MULTI_SPOT_DOCUMENTS = """
				WITH grouped_loinc_data AS
			(
			SELECT Distinct PFI, LOINC_1, LOINC_2, LOINC_3, LOINC_4, GROUP_COUNT GROUPED_LOINC
			  FROM CODED_LOINC_GROUPING
			WHERE (end_date is null or end_Date > SYSDATE)
			
			
			 %1$s
			
			                                                    and (
			
			                                                    (LOINC_1, LOINC_2, nvl(LOINC_3, 'X'), nvl(LOINC_4, 'X')) in
			
			
			
			                                                    ( select %2$s from dual)
			
			
			))
			
					  SELECT REFRENCE.RANKING, REFRENCE.DOCUMENT_ID, REFRENCE.OBXLOINCCODE, REFRENCE.MSHLABPFI, REFRENCE.MSHSENDINGFACILITYCLIA, REFRENCE.PIDLASTNAME, REFRENCE.PIDFIRSTNAME, REFRENCE.PIDMIDDLENAME, REFRENCE.PIDNAMESUFFIX, TO_CHAR(REFRENCE.PIDDATEOFBIRTH, 'MM/DD/YYYY') PIDDATEOFBIRTH, REFRENCE.PIDSSN, TO_CHAR(REFRENCE.PIDDEATHDATE, 'MM/DD/YYYY') PIDDEATHDATE, REFRENCE.PIDMEDICALRECORDNUMBER, REFRENCE.PIDPATIENTACCOUNTNUM, REFRENCE.PIDTYPEOFINSURANCE, REFRENCE.PIDNYSID, REFRENCE.PIDDIN, REFRENCE.PIDADDRESSLINE1, REFRENCE.PIDADDRESSLINE2, REFRENCE.PIDCITY, REFRENCE.PIDSTATE, REFRENCE.PIDZIPCODE, REFRENCE.PIDHOMEPHONE, REFRENCE.PIDSEXCODE, REFRENCE.PIDRACEASIAN, REFRENCE.PIDRACEBLACK, REFRENCE.PIDRACENATIVEAMERALASKAN, REFRENCE.PIDRACEOTHER, REFRENCE.PIDRACEPACIFIC, REFRENCE.PIDRACEUNKNOWN, REFRENCE.PIDRACEWHITE, REFRENCE.PIDETHNICGROUPCODE, REFRENCE.PIDFACILITYIDINTERNAL, REFRENCE.PIDFACILITYIDEXTERNAL, REFRENCE.PIDCOUNTRYOFORIGIN, REFRENCE.PIDLENGTHOFRESIDENCY, REFRENCE.OBRPROVIDERLASTNAME, REFRENCE.OBRPROVIDERFIRSTNAME, REFRENCE.OBRPROVIDERMIDDLENAME, TO_CHAR(REFRENCE.OBRCOLLECTIONDATE, 'MM/DD/YYYY') OBRCOLLECTIONDATE, REFRENCE.OBRACCESSIONNUM, REFRENCE.ZLRPROVIDERADDRESSLINE1, REFRENCE.ZLRPROVIDERADDRESSLINE2, REFRENCE.ZLRPROVIDERCITY, REFRENCE.ZLRPROVIDERSTATE, REFRENCE.ZLRPROVIDERZIPCODE, REFRENCE.OBRCALLBACKPHONE, REFRENCE.ZLRFACILITYNAME, REFRENCE.ZLRFACILITYADDRESSLINE1, REFRENCE.ZLRFACILITYADDRESSLINE2, REFRENCE.ZLRFACILITYCITY, REFRENCE.ZLRFACILITYSTATE, REFRENCE.ZLRFACILITYZIPCODE, REFRENCE.ZLRFACILITYPHONE, REFRENCE.ZLRNEXTOFKINRELATIONSHIP, REFRENCE.ZLRNEXTOFKINLASTNAME, REFRENCE.ZLRNEXTOFKINFIRSTNAME, REFRENCE.ZLRNEXTOFKINMIDDLENAME, REFRENCE.ZLRNEXTOFKINADDRESSLINE1, REFRENCE.ZLRNEXTOFKINADDRESSLINE2, REFRENCE.ZLRNEXTOFKINCITY, REFRENCE.ZLRNEXTOFKINSTATE, REFRENCE.ZLRNEXTOFKINZIPCODE, REFRENCE.ZLRNEXTOFKINPHONE
			    FROM (SELECT MAX (DOCUMENT_ID)
			                    OVER (PARTITION BY MSHSENDINGFACILITYCLIA,
			                                       PIDLASTNAME,
			                                       PIDFIRSTNAME,
			                                       PIDMIDDLENAME,
			                                       PIDNAMESUFFIX,
			                                       PIDDATEOFBIRTH,
			                                       PIDSSN,
			                                       PIDDEATHDATE,
			                                       PIDMEDICALRECORDNUMBER,
			                                       PIDPATIENTACCOUNTNUM,
			                                       PIDTYPEOFINSURANCE,
			                                       PIDNYSID,
			                                       PIDDIN,
			                                       PIDADDRESSLINE1,
			                                       PIDADDRESSLINE2,
			                                       PIDCITY,
			                                       PIDSTATE,
			                                       PIDZIPCODE,
			                                       PIDHOMEPHONE,
			                                       PIDSEXCODE,
			                                       PIDRACEASIAN,
			                                       PIDRACEBLACK,
			                                       PIDRACENATIVEAMERALASKAN,
			                                       PIDRACEOTHER,
			                                       PIDRACEPACIFIC,
			                                       PIDRACEUNKNOWN,
			                                       PIDRACEWHITE,
			                                       PIDETHNICGROUPCODE,
			                                       PIDFACILITYIDINTERNAL,
			                                       PIDFACILITYIDEXTERNAL,
			                                       PIDCOUNTRYOFORIGIN,
			                                       PIDLENGTHOFRESIDENCY,
			                                       OBRPROVIDERLASTNAME,
			                                       OBRPROVIDERFIRSTNAME,
			                                       OBRPROVIDERMIDDLENAME,
			                                       OBRCOLLECTIONDATE,
			                                       OBRACCESSIONNUM,
			                                       ZLRPROVIDERADDRESSLINE1,
			                                       ZLRPROVIDERADDRESSLINE2,
			                                       ZLRPROVIDERCITY,
			                                       ZLRPROVIDERSTATE,
			                                       ZLRPROVIDERZIPCODE,
			                                       OBRCALLBACKPHONE,
			                                       ZLRFACILITYNAME,
			                                       ZLRFACILITYADDRESSLINE1,
			                                       ZLRFACILITYADDRESSLINE2,
			                                       ZLRFACILITYCITY,
			                                       ZLRFACILITYSTATE,
			                                       ZLRFACILITYZIPCODE,
			                                       ZLRFACILITYPHONE,
			                                       ZLRNEXTOFKINRELATIONSHIP,
			                                       ZLRNEXTOFKINLASTNAME,
			                                       ZLRNEXTOFKINFIRSTNAME,
			                                       ZLRNEXTOFKINMIDDLENAME,
			                                       ZLRNEXTOFKINADDRESSLINE1,
			                                       ZLRNEXTOFKINADDRESSLINE2,
			                                       ZLRNEXTOFKINCITY,
			                                       ZLRNEXTOFKINSTATE,
			                                       ZLRNEXTOFKINZIPCODE,
			                                       ZLRNEXTOFKINPHONE
			                          ORDER BY
			                             MSHSENDINGFACILITYCLIA,
			                             PIDLASTNAME,
			                             PIDFIRSTNAME,
			                             PIDMIDDLENAME,
			                             PIDNAMESUFFIX,
			                             PIDDATEOFBIRTH,
			                             PIDSSN,
			                             PIDDEATHDATE,
			                             PIDMEDICALRECORDNUMBER,
			                             PIDPATIENTACCOUNTNUM,
			                             PIDTYPEOFINSURANCE,
			                             PIDNYSID,
			                             PIDDIN,
			                             PIDADDRESSLINE1,
			                             PIDADDRESSLINE2,
			                             PIDCITY,
			                             PIDSTATE,
			                             PIDZIPCODE,
			                             PIDHOMEPHONE,
			                             PIDSEXCODE,
			                             PIDRACEASIAN,
			                             PIDRACEBLACK,
			                             PIDRACENATIVEAMERALASKAN,
			                             PIDRACEOTHER,
			                             PIDRACEPACIFIC,
			                             PIDRACEUNKNOWN,
			                             PIDRACEWHITE,
			                             PIDETHNICGROUPCODE,
			                             PIDFACILITYIDINTERNAL,
			                             PIDFACILITYIDEXTERNAL,
			                             PIDCOUNTRYOFORIGIN,
			                             PIDLENGTHOFRESIDENCY,
			                             OBRPROVIDERLASTNAME,
			                             OBRPROVIDERFIRSTNAME,
			                             OBRPROVIDERMIDDLENAME,
			                             OBRCOLLECTIONDATE,
			                             OBRACCESSIONNUM,
			                             ZLRPROVIDERADDRESSLINE1,
			                             ZLRPROVIDERADDRESSLINE2,
			                             ZLRPROVIDERCITY,
			                             ZLRPROVIDERSTATE,
			                             ZLRPROVIDERZIPCODE,
			                             OBRCALLBACKPHONE,
			                             ZLRFACILITYNAME,
			                             ZLRFACILITYADDRESSLINE1,
			                             ZLRFACILITYADDRESSLINE2,
			                             ZLRFACILITYCITY,
			                             ZLRFACILITYSTATE,
			                             ZLRFACILITYZIPCODE,
			                             ZLRFACILITYPHONE,
			                             ZLRNEXTOFKINRELATIONSHIP,
			                             ZLRNEXTOFKINLASTNAME,
			                             ZLRNEXTOFKINFIRSTNAME,
			                             ZLRNEXTOFKINMIDDLENAME,
			                             ZLRNEXTOFKINADDRESSLINE1,
			                             ZLRNEXTOFKINADDRESSLINE2,
			                             ZLRNEXTOFKINCITY,
			                             ZLRNEXTOFKINSTATE,
			                             ZLRNEXTOFKINZIPCODE,
			                             ZLRNEXTOFKINPHONE)
			                    RANKING,
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
			         REFRENCE.RANKING
			""";

	/** The PFI filter of {@link #MULTI_SPOT_DOCUMENTS}; {@code %s} the placeholders of the PFIs. */
	static final String MULTI_SPOT_PFI_FILTER = """
			and PFI in (
			
						%s
			
					)
			""";
}
