package gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public class LabCommonUtils {
	
	private static final String EQUALS = "=";
    private static final String QUOTE = "'";
    private static final String NOT_EQUALS = "!=";
    private static final String SPACE = " ";
    private static final String PERCENTAGE = "%";
    private static final String NOT_LIKE = "NOT LIKE";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";

    private static final Map<String, String> codedBirthSexValues = new HashMap<String, String>();
    private static final Map<String, String> codedCurrentGenderValues = new HashMap<String, String>();
    private static final Map<String, String> codedEthinicityValues = new HashMap<String, String>();
    private static final Map<String, String> codedRaceDataValues = new HashMap<String, String>();
    private static final Map<String, String> codedRiskDataValues = new HashMap<String, String>();
    private static final Map<String, String> codedTestDataValues = new HashMap<String, String>();
    
    private static final Map<String, String> stdHivColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawHivColumnReferances = new HashMap<String, String>();
    
    private static final Map<String, String> rawStdConfColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdStdConfColumnReferances = new HashMap<String, String>();
    
    private static final Map<String, String> rawStdColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdStdColumnReferances = new HashMap<String, String>();
    
    private static final Map<String, String> rawMedColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdMedColumnReferances = new HashMap<String, String>();
    
    private static final Map<String, String> rawDeathColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdDeathColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawNycDeathColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdNycDeathColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawAirsColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdAirsColumnReferances = new HashMap<String, String>();
    
    private static final Map<String, String> rawAdapColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdAdapColumnReferances = new HashMap<String, String>();
    public static final Map<String, String> oldNycRaceColumnMap = new HashMap<String, String>();

    private static final Map<String, String> stdMedOldColumnReferances = new HashMap<String, String>();
    
    private static final Map<String, String> rawPrfColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdPrfColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawNycPrfColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdNycPrfColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdOldCaseColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawSsdmColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdSsdmColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawTbColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdTbColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawSyphColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdSyphColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdDoccsColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawEprfOOCColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdEprfOOCColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawQualCareColumnReferances = new HashMap<String, String>();
    
    private static final Map<String, String> rawNycIJRColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdNycIJRColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawICCRColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdICCRColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> stdOldColumnReferances = new HashMap<String, String>();
    private static final Map<String, String> rawCASAColumnReferances = new HashMap<String, String>();
    
    
    private static final Map<String, String> rawTableNames = new HashMap<String, String>();

    private static final StringBuilder deathStandardDocumentsQuery = new StringBuilder("");
    private static final StringBuilder liveStandardDocumentsQuery = new StringBuilder("");
    private static final StringBuilder ssdmStandardDocumentsQuery = new StringBuilder("");
    
    private static final List<String> RAW_CCD_DOC_LIST = new ArrayList<String>();
    private static final List<String> UPHN_TRANX_IN_LIST = new ArrayList<String>();
    private static final List<String> RAW_CCD_PERSON_ADDRESS_LIST = new ArrayList<String>();
    private static final List<String> RAW_CCD_PROVIDER_LIST = new ArrayList<String>();
    private static final List<String> RAW_CCD_PAYER_LIST = new ArrayList<String>();
    private static final List<String> RAW_CCD_LAB_TEST_LIST = new ArrayList<String>();
    private static final List<String> RAW_CCD_ENCOUNTER_LIST = new ArrayList<String>();
    private static final List<String> RAW_CCD_MED_LIST = new ArrayList<String>();
    private static final List<String> RAW_CCD_DIAGNOSIS_LIST = new ArrayList<String>();
    private static final List<String> RAW_CCD_LAB_RESULT_LIST = new ArrayList<String>();
    private static final List<String> RAW_CCD_PROBLEM_LIST = new ArrayList<String>();
    
    static{
    	
    	//RAW_CCD_DOC
    	RAW_CCD_DOC_LIST.add("DOCUMENT_ID");
    	RAW_CCD_DOC_LIST.add("FNAME");
    	RAW_CCD_DOC_LIST.add("MNAME");
    	RAW_CCD_DOC_LIST.add("LNAME");
    	RAW_CCD_DOC_LIST.add("DOB_TEXT");
    	RAW_CCD_DOC_LIST.add("GENDER");
    	RAW_CCD_DOC_LIST.add("PERSON_PHONE_HOME");
    	RAW_CCD_DOC_LIST.add("LANGUAGE");
    	RAW_CCD_DOC_LIST.add("ETHNICITY");
    	RAW_CCD_DOC_LIST.add("RACE_DESC");
    	RAW_CCD_DOC_LIST.add("DATE_CREATED");
    	RAW_CCD_DOC_LIST.add("PERSON_PHONE_WORK");
    	RAW_CCD_DOC_LIST.add("RACE_CODE");
    	RAW_CCD_DOC_LIST.add("PERSON_PHONE_CELL");
    	RAW_CCD_DOC_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_DOC_LIST.add("MPI");
    	RAW_CCD_DOC_LIST.add("IS_DUPLICATE");
    	RAW_CCD_DOC_LIST.add("REMOVED");
    	RAW_CCD_DOC_LIST.add("PROCESS_DATE");
    	RAW_CCD_DOC_LIST.add("CURRENT_PROCESS_STATUS");
    	RAW_CCD_DOC_LIST.add("CLEARTEXT_PAYLOAD");
    	RAW_CCD_DOC_LIST.add("HIE_NAME");
    	
    	//UPHN_TRANX_IN
    	UPHN_TRANX_IN_LIST.add("UPHN_TRANX_IN_ID");    	
    	UPHN_TRANX_IN_LIST.add("PAYLOADBINARYCONTENT");
    	UPHN_TRANX_IN_LIST.add("PAYLOADSIZE");
    	UPHN_TRANX_IN_LIST.add("PAYLOADNAME");
    	UPHN_TRANX_IN_LIST.add("UUID");
    	UPHN_TRANX_IN_LIST.add("PAYLOAD_TRACKING_ID");
    	UPHN_TRANX_IN_LIST.add("SENDERID");
    	UPHN_TRANX_IN_LIST.add("ORGNAME");
    	UPHN_TRANX_IN_LIST.add("TOPIC");
    	UPHN_TRANX_IN_LIST.add("TRANSPORT");
    	UPHN_TRANX_IN_LIST.add("ARGUMENTS");
    	UPHN_TRANX_IN_LIST.add("RECEIVEDTIME");
    	UPHN_TRANX_IN_LIST.add("LASTUPDATETIME");
    	UPHN_TRANX_IN_LIST.add("LASTREADTIME");
    	
    	
    	//RAW_CCD_PERSON_ADDRESS
    	RAW_CCD_PERSON_ADDRESS_LIST.add("PERSON_ADDRESS_ID");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("CCD_DOC_ID");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("ADDRESS_USE");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("PERSON_ADDRESS_1");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("PERSON_ADDRESS_2");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("PERSON_CITY");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("PERSON_STATE");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("PERSON_ZIP");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("PERSON_COUNTRY");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("USEABLE_PERIOD_LOW");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("USEABLE_PERIOD_HIGH");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_PERSON_ADDRESS_LIST.add("ADDRESS_SEQ_NO");
    	
    	//RAW_CCD_PROVIDER
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_ID");
    	RAW_CCD_PROVIDER_LIST.add("CCD_DOC_ID");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_TYPE_CODE");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_TYPE_DESCRIPTION");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_LNAME");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_FNAME");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_MNAME");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_ADDRESS_1");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_ADDRESS_2");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_CITY");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_STATE");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_ZIP");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_PHONE");
    	RAW_CCD_PROVIDER_LIST.add("START_DATE_TEXT");
    	RAW_CCD_PROVIDER_LIST.add("END_DATE_TEXT");
    	RAW_CCD_PROVIDER_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_PROVIDER_LIST.add("PROVIDER_SEQ_NO");
    	
    	//RAW_CCD_PAYER
    	RAW_CCD_PAYER_LIST.add("PAYER_ID");
    	RAW_CCD_PAYER_LIST.add("CCD_DOC_ID");
    	RAW_CCD_PAYER_LIST.add("POLICY_ID");
    	RAW_CCD_PAYER_LIST.add("POLICY_NAME");
    	RAW_CCD_PAYER_LIST.add("POLICY_ADDRESS1");
    	RAW_CCD_PAYER_LIST.add("POLICY_ADDRESS2");
    	RAW_CCD_PAYER_LIST.add("POLICY_CITY");
    	RAW_CCD_PAYER_LIST.add("POLICY_STATE");
    	RAW_CCD_PAYER_LIST.add("POLICY_ZIPCODE");
    	RAW_CCD_PAYER_LIST.add("POLICY_COUNTRY");
    	RAW_CCD_PAYER_LIST.add("POLICY_COUNTY");
    	RAW_CCD_PAYER_LIST.add("POLICY_PHONE");
    	RAW_CCD_PAYER_LIST.add("MEMBER_ID");
    	RAW_CCD_PAYER_LIST.add("MEMBER_FNAME");
    	RAW_CCD_PAYER_LIST.add("MEMBER_MNAME");
    	RAW_CCD_PAYER_LIST.add("MEMBER_LNAME");
    	RAW_CCD_PAYER_LIST.add("MEMBER_ADDRESS1");
    	RAW_CCD_PAYER_LIST.add("MEMBER_ADDRESS2");
    	RAW_CCD_PAYER_LIST.add("MEMBER_CITY");
    	RAW_CCD_PAYER_LIST.add("MEMBER_STATE");
    	RAW_CCD_PAYER_LIST.add("MEMBER_ZIPCODE");
    	RAW_CCD_PAYER_LIST.add("MEMBER_COUNTRY");
    	RAW_CCD_PAYER_LIST.add("MEMBER_COUNTY");
    	RAW_CCD_PAYER_LIST.add("MEMBER_PHONE");
    	RAW_CCD_PAYER_LIST.add("MEMBER_DOB_TEXT");
    	RAW_CCD_PAYER_LIST.add("HOLDER_ID");
    	RAW_CCD_PAYER_LIST.add("HOLDER_ADDRESS1");
    	RAW_CCD_PAYER_LIST.add("HOLDER_ADDRESS2");
    	RAW_CCD_PAYER_LIST.add("HOLDER_CITY");
    	RAW_CCD_PAYER_LIST.add("HOLDER_STATE");
    	RAW_CCD_PAYER_LIST.add("HOLDER_ZIPCODE");
    	RAW_CCD_PAYER_LIST.add("HOLDER_COUNTRY");
    	RAW_CCD_PAYER_LIST.add("HOLDER_COUNTY");
    	RAW_CCD_PAYER_LIST.add("HOLDER_PHONE");
    	RAW_CCD_PAYER_LIST.add("HOLDER_DOB_TEXT");
    	RAW_CCD_PAYER_LIST.add("START_DATE_TEXT");
    	RAW_CCD_PAYER_LIST.add("END_DATE_TEXT");
    	RAW_CCD_PAYER_LIST.add("HOLDER_FNAME");
    	RAW_CCD_PAYER_LIST.add("HOLDER_MNAME");
    	RAW_CCD_PAYER_LIST.add("HOLDER_LNAME");
    	RAW_CCD_PAYER_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_PAYER_LIST.add("PAYER_SEQ_NO");
    	
    	//RAW_CCD_LAB_TEST
    	
    	RAW_CCD_LAB_TEST_LIST.add("CCD_LAB_TEST_ID");
    	RAW_CCD_LAB_TEST_LIST.add("CCD_DOC_ID");
    	RAW_CCD_LAB_TEST_LIST.add("TEST_CODE");
    	RAW_CCD_LAB_TEST_LIST.add("TEST_CODE_CODESYSTEM");
    	RAW_CCD_LAB_TEST_LIST.add("TEST_CODE_DISPLAY_NAME");
    	RAW_CCD_LAB_TEST_LIST.add("TEST_DATE");
    	RAW_CCD_LAB_TEST_LIST.add("TEST_ENCOUNTER_NUMBER");
    	RAW_CCD_LAB_TEST_LIST.add("SPECIMEN");
    	RAW_CCD_LAB_TEST_LIST.add("TEST_COMMENTS");
    	RAW_CCD_LAB_TEST_LIST.add("TEST_FACILITY_CODE");
    	RAW_CCD_LAB_TEST_LIST.add("TEST_FACILITY_CODESYSTEM");
    	RAW_CCD_LAB_TEST_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_LAB_TEST_LIST.add("LAB_TEST_SEQ_NO");
    	
    	//RAW_CCD_ENCOUNTER
    	RAW_CCD_ENCOUNTER_LIST.add("ENCOUNTER_DOC_ID");
    	RAW_CCD_ENCOUNTER_LIST.add("CCD_DOC_ID");
    	RAW_CCD_ENCOUNTER_LIST.add("START_DATE_TEXT");
    	RAW_CCD_ENCOUNTER_LIST.add("END_DATE_TEXT");
    	RAW_CCD_ENCOUNTER_LIST.add("ENCOUNTER_TYPE");
    	RAW_CCD_ENCOUNTER_LIST.add("PROVIDER_FNAME");
    	RAW_CCD_ENCOUNTER_LIST.add("PROVIDER_MNAME");
    	RAW_CCD_ENCOUNTER_LIST.add("PROVIDER_LNAME");
    	RAW_CCD_ENCOUNTER_LIST.add("FACILITY_NAME");
    	RAW_CCD_ENCOUNTER_LIST.add("FACILITY_DEPT");
    	RAW_CCD_ENCOUNTER_LIST.add("ENCOUNTER_ID");
    	RAW_CCD_ENCOUNTER_LIST.add("ADMISSION_TYPE");
    	RAW_CCD_ENCOUNTER_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_ENCOUNTER_LIST.add("ENCOUNTER_SEQ_NO");
    	
    	//RAW_CCD_MED
    	RAW_CCD_MED_LIST.add("MED_ID");
    	RAW_CCD_MED_LIST.add("CCD_DOC_ID");
    	RAW_CCD_MED_LIST.add("ORDERED_MEDICATION_NAME");
    	RAW_CCD_MED_LIST.add("START_DATE_TEXT");
    	RAW_CCD_MED_LIST.add("STOP_DATE_TEXT");
    	RAW_CCD_MED_LIST.add("DOSAGE_VALUE");
    	RAW_CCD_MED_LIST.add("DOSAGE_UNIT");
    	RAW_CCD_MED_LIST.add("FILLED_MEDICATION_NAME");
    	RAW_CCD_MED_LIST.add("CURRENT_MEDICATION");
    	RAW_CCD_MED_LIST.add("ORDERING_PROV_FNAME");
    	RAW_CCD_MED_LIST.add("ORDERING_PROV_MNAME");
    	RAW_CCD_MED_LIST.add("ORDERING_PROV_LNAME");
    	RAW_CCD_MED_LIST.add("FREQUENCY");
    	RAW_CCD_MED_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_MED_LIST.add("MED_SEQ_NO");
    	
    	//RAW_CCD_DIAGNOSIS
    	RAW_CCD_DIAGNOSIS_LIST.add("DIAGNOSIS_ID");
    	RAW_CCD_DIAGNOSIS_LIST.add("ENCOUNTER_DOC_ID");
    	RAW_CCD_DIAGNOSIS_LIST.add("DIAGNOSIS_TEXT");
    	RAW_CCD_DIAGNOSIS_LIST.add("DIAGNOSIS_CODE");
    	RAW_CCD_DIAGNOSIS_LIST.add("DIAGNOSIS_CODE_SYSTEM_CODE");
    	RAW_CCD_DIAGNOSIS_LIST.add("DIAGNOSIS_CODE_SYSTEM_OID");
    	RAW_CCD_DIAGNOSIS_LIST.add("CCD_DOC_ID");
    	RAW_CCD_DIAGNOSIS_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_DIAGNOSIS_LIST.add("ENCOUNTER_SEQ_NO");
    	RAW_CCD_DIAGNOSIS_LIST.add("DIAGNOSIS_SEQ_NO");
    	
    	//RAW_CCD_LAB_RESULT
    	RAW_CCD_LAB_RESULT_LIST.add("CCD_LAB_RESULT_ID");
    	RAW_CCD_LAB_RESULT_LIST.add("CCD_LAB_TEST_ID");
    	RAW_CCD_LAB_RESULT_LIST.add("CCD_DOC_ID");
    	RAW_CCD_LAB_RESULT_LIST.add("COMPONENT_CODE");
    	RAW_CCD_LAB_RESULT_LIST.add("COMPONENT_CODESYSTEM");
    	RAW_CCD_LAB_RESULT_LIST.add("COMPONENT_DISPLAY_NAME");
    	RAW_CCD_LAB_RESULT_LIST.add("RESULT_DATE_TEXT");
    	RAW_CCD_LAB_RESULT_LIST.add("RESULT_VALUE");
    	RAW_CCD_LAB_RESULT_LIST.add("RESULT_STATUS");
    	RAW_CCD_LAB_RESULT_LIST.add("REFERENCE_RANGE_LOW");
    	RAW_CCD_LAB_RESULT_LIST.add("REFERENCE_RANGE_HIGH");
    	RAW_CCD_LAB_RESULT_LIST.add("RESULT_COMMENTS");
    	RAW_CCD_LAB_RESULT_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_LAB_RESULT_LIST.add("LAB_TEST_SEQ_NO");
    	RAW_CCD_LAB_RESULT_LIST.add("LAB_RESULT_SEQ_NO");
    	
    	//RAW_CCD_PROBLEM
    	RAW_CCD_PROBLEM_LIST.add("PROBLEM_DOC_ID");    	
    	RAW_CCD_PROBLEM_LIST.add("CCD_DOC_ID");
    	RAW_CCD_PROBLEM_LIST.add("PROBLEM_DATE_TEXT");
    	RAW_CCD_PROBLEM_LIST.add("PROVIDER_FNAME");
    	RAW_CCD_PROBLEM_LIST.add("PROVIDER_MNAME");
    	RAW_CCD_PROBLEM_LIST.add("PROVIDER_LNAME");
    	RAW_CCD_PROBLEM_LIST.add("FACILITY_NAME");
    	RAW_CCD_PROBLEM_LIST.add("PROBLEM_CODE");
    	RAW_CCD_PROBLEM_LIST.add("PROBLEM_CODE_SYSTEM_NAME");
    	RAW_CCD_PROBLEM_LIST.add("PROBLEM_DISPLAY_NAME");
    	RAW_CCD_PROBLEM_LIST.add("ENCOUNTER_ID");
    	RAW_CCD_PROBLEM_LIST.add("PROBLEM_CATEGORY");
    	RAW_CCD_PROBLEM_LIST.add("UPHN_TRANX_IN_ID");
    	RAW_CCD_PROBLEM_LIST.add("PROBLEM_SEQ_NO");
    	
    	codedBirthSexValues.put("1", "M");
    	codedBirthSexValues.put("2", "F");
    	codedBirthSexValues.put("M", "M");
    	codedBirthSexValues.put("F", "F");
    	codedBirthSexValues.put("MALE", "M");
    	codedBirthSexValues.put("FEMALE", "F");
    	codedBirthSexValues.put("UNKOWN", "U");
    	codedBirthSexValues.put("UK", "U");
    	codedBirthSexValues.put("I", "I");
    	codedBirthSexValues.put("U", "U");
    	
    	//Static references are there in NYCPRFDocumentDAO.xml
    	codedCurrentGenderValues.put("M", "M");
    	codedCurrentGenderValues.put("MALE", "M");
    	codedCurrentGenderValues.put("MAL", "M");
    	codedCurrentGenderValues.put("FEMALE", "F");
    	codedCurrentGenderValues.put("FML", "F");
    	codedCurrentGenderValues.put("F", "F");
    	codedCurrentGenderValues.put("T", "T");
    	codedCurrentGenderValues.put("U", "U");
    	codedCurrentGenderValues.put("R", "R");
    	codedCurrentGenderValues.put("X", "X");
    	codedCurrentGenderValues.put("Y", "Y");
    	codedCurrentGenderValues.put("H", "H");
    	codedCurrentGenderValues.put("1", "M");
    	codedCurrentGenderValues.put("2", "F");
    	codedCurrentGenderValues.put("MTF", "X");
    	codedCurrentGenderValues.put("TGW", "X");
    	codedCurrentGenderValues.put("FTM", "Y");
    	codedCurrentGenderValues.put("TGM", "Y");
    	codedCurrentGenderValues.put("A", "A");
    	codedCurrentGenderValues.put("U", "U");
    	codedCurrentGenderValues.put("UK", "U");
    	codedCurrentGenderValues.put("OTH", "T");
    	codedCurrentGenderValues.put("B", "B");
    	codedCurrentGenderValues.put("C", "C");
    	codedCurrentGenderValues.put("Q", "Q");

    	//Static references are there in NYCPRFDocumentDAO.xml
    	codedEthinicityValues.put("01", "01");
    	codedEthinicityValues.put("HISPANIC", "01");
    	codedEthinicityValues.put("H", "01");
    	codedEthinicityValues.put("02", "02");
    	codedEthinicityValues.put("NOT HISPANIC", "02");
    	codedEthinicityValues.put("NH", "02");
    	codedEthinicityValues.put("09", "09");
    	codedEthinicityValues.put("Y", "01");
    	codedEthinicityValues.put("N", "02");
    	codedEthinicityValues.put("UN", "09");
    	codedEthinicityValues.put("UNK", "09");
    	codedEthinicityValues.put("U", "09");
    	codedEthinicityValues.put("UK", "09");
    	
    	
    	//Added by MXM69 for CCD RAW-TO-STD
    	codedEthinicityValues.put("1", "01");
    	codedEthinicityValues.put("2", "02");
    	codedEthinicityValues.put("9", "09");
    	codedEthinicityValues.put("UNKNOWN", "09");
    	codedEthinicityValues.put("Unknown", "09");
    	codedEthinicityValues.put("H", "01");
    	codedEthinicityValues.put("PR", "01");
    	codedEthinicityValues.put("NON", "02");
    	codedEthinicityValues.put("NOT HISPANIC OR LATINO", "02");
    	codedEthinicityValues.put("HISPANIC", "01");
    	codedEthinicityValues.put("HISPANIC OR LATINO", "01");
    	codedEthinicityValues.put("HISPANIC OR", "01");
    	codedEthinicityValues.put("PUERTO RICAN", "01");
    	codedEthinicityValues.put("2135-2", "01");
    	codedEthinicityValues.put("2186-5", "02");
    	codedEthinicityValues.put("HISPANIC/LATINO", "01");
    	codedEthinicityValues.put("NON-HISPANIC/LATINO" , "02");
    	codedEthinicityValues.put("NON-HISPANIC" , "02");
    	//codedEthinicityValues.put("0", "01");Hispanic/Latino
    	
    	codedRaceDataValues.put("1", "1");
    	codedRaceDataValues.put("-1", "1");
    	codedRaceDataValues.put("0", "0");
    	codedRaceDataValues.put("Y", "1");
    	codedRaceDataValues.put("N", "0");
    	
    	codedRiskDataValues.put("Y", "Y");
    	codedRiskDataValues.put("1", "Y");
    	codedRiskDataValues.put("X", "Y");
    	codedRiskDataValues.put("N", "N");
    	codedRiskDataValues.put("U", "U");
    	codedRiskDataValues.put("UN", "U");
    	codedRiskDataValues.put("UNK", "U");
    	codedRiskDataValues.put("UK", "U");
    	codedRiskDataValues.put("YES", "Y");
    	codedRiskDataValues.put("NO", "N");
    	codedRiskDataValues.put("UNSPECIFIED TYPE OF SEX", "U");
    	codedRiskDataValues.put("R", "R");
    	codedRiskDataValues.put("REFUSED", "R");
    	codedRiskDataValues.put("D", "D");        	
    	codedRiskDataValues.put("DID NOT ASK", "D");
    	codedRiskDataValues.put("O", "O");        	
    	codedRiskDataValues.put("ORAL SEX ONLY", "O");
//    	codedRiskDataValues.put("X", "X");    	
    	
    	codedTestDataValues.put("Y", "Y");
    	codedTestDataValues.put("YES", "Y");
    	codedTestDataValues.put("NO", "N");
    	codedTestDataValues.put("N", "N");
    	codedTestDataValues.put("REF", "R");
    	codedTestDataValues.put("R", "R");
    	codedTestDataValues.put("U", "U");
    	codedTestDataValues.put("UN", "U");
    	codedTestDataValues.put("UNK", "U");
    	codedTestDataValues.put("UNKNOWN", "U");
    	codedTestDataValues.put("NEGATIVE", "N");
    	codedTestDataValues.put("POSITIVE", "P");
    	
    	rawHivColumnReferances.put("FACILITY_NAME", "ZLRFACILITYNAME");
    	rawHivColumnReferances.put("FACILITY_ADDRESS", "ZLRFACILITYADDRESSLINE1");
    	rawHivColumnReferances.put("FACILITY_ADDRESS_2", "ZLRFACILITYADDRESSLINE2");
    	rawHivColumnReferances.put("FACILITY_CITY", "ZLRFACILITYCITY");
    	rawHivColumnReferances.put("FACILITY_STATE", "ZLRFACILITYSTATE");
    	rawHivColumnReferances.put("FACILITY_ZIPCODE", "ZLRFACILITYZIPCODE");
    	rawHivColumnReferances.put("FACILITY_PHONE", "ZLRFACILITYPHONE");
    	
    	rawHivColumnReferances.put("PROVIDER_ADDRESS", "ZLRPROVIDERADDRESSLINE1");
    	rawHivColumnReferances.put("PROVIDER_CITY", "ZLRPROVIDERCITY");
    	rawHivColumnReferances.put("PROVIDER_STATE", "ZLRPROVIDERSTATE");
    	rawHivColumnReferances.put("PROVIDER_ZIPCODE", "ZLRPROVIDERZIPCODE");
    	rawHivColumnReferances.put("PROVIDER_PHONE", "OBRCALLBACKPHONE");
    	rawHivColumnReferances.put("PROVIDER_FNAME", "OBRPROVIDERFIRSTNAME");
    	rawHivColumnReferances.put("PROVIDER_LNAME", "OBRPROVIDERLASTNAME");
    	rawHivColumnReferances.put("PROVIDER_MNAME", "OBRPROVIDERMIDDLENAME");
    	rawHivColumnReferances.put("PROVIDER_SUFFIX", "OBRPROVIDERNAMESUFFIX");
    	
    	stdHivColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	stdHivColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	stdHivColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	stdHivColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	stdHivColumnReferances.put("FACILITY_PHONE", "FACILITY_PHONE");
    	stdHivColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	stdHivColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	stdHivColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	stdHivColumnReferances.put("PROVIDER_LNAME", "PROV_LNAME");
    	stdHivColumnReferances.put("PROVIDER_ADDRESS", "PROV_ADDRESS");
    	stdHivColumnReferances.put("PROVIDER_CITY", "PROV_CITY");
    	stdHivColumnReferances.put("PROVIDER_STATE", "PROV_STATE");
    	stdHivColumnReferances.put("PROVIDER_ZIPCODE", "PROV_ZIP");
    	stdHivColumnReferances.put("PROVIDER_PLUS4", "PROV_PLUS4");
    	stdHivColumnReferances.put("PROVIDER_COUNTY", "PROV_COUNTY");
    	stdHivColumnReferances.put("PROVIDER_PHONE", "PROV_PHONE");
    	stdHivColumnReferances.put("PROVIDER_FNAME", "PROV_FNAME");
    	stdHivColumnReferances.put("PROVIDER_MNAME", "PROV_MNAME");
    	stdHivColumnReferances.put("PROVIDER_SUFFIX", "PROV_SUFFIX");
    	stdHivColumnReferances.put("SOURCE_ID", "LAB_SRC_ID");
    	stdHivColumnReferances.put("SOURCE_ID", "LAB_SRC_ID");
    	stdHivColumnReferances.put("CREATE_DATE", "DATE_RECEIVED");
    	
    	rawSyphColumnReferances.put("FNAME", "FIRSTNAME");
    	rawSyphColumnReferances.put("LNAME", "LASTNAME");
    	
    	rawQualCareColumnReferances.put("LNAME", "LAST_NAME");
    	rawQualCareColumnReferances.put("FNAME", "FIRST_NAME");
    	
    	rawStdConfColumnReferances.put("FNAME", "FNAME");
    	rawStdConfColumnReferances.put("LNAME", "LNAME");
    	
    	stdStdConfColumnReferances.put("FILE_NAME", "FILENAME");
    	stdStdConfColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");    	
    	stdStdConfColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	stdStdConfColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	stdStdConfColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	stdStdConfColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	stdStdConfColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	stdStdConfColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	stdStdConfColumnReferances.put("FACILITY_PHONE", "FACILITY_PHONE");
    	
    	rawCASAColumnReferances.put("FNAME", "FNAME");
    	rawCASAColumnReferances.put("LNAME", "LNAME");
    	
    	rawPrfColumnReferances.put("FNAME", "FNAME");
    	rawPrfColumnReferances.put("LNAME", "LNAME");
    	
    	rawPrfColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	rawPrfColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	rawPrfColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	rawPrfColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	rawPrfColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	rawPrfColumnReferances.put("FACILITY_DEPARTMENT", "FACILITY_DEPT");
    	rawPrfColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	rawPrfColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	rawPrfColumnReferances.put("FACILITY_TYPE", "FACILITY_TYPE");
    	rawPrfColumnReferances.put("FACILITY_TYPE_OTHER_TEXT", "FACILITY_TYPE_OTHER_TEXT");
    	
    	
    	
    	stdPrfColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	stdPrfColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	stdPrfColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	stdPrfColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	stdPrfColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	stdPrfColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	stdPrfColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	stdPrfColumnReferances.put("FACILITY_DEPARTMENT", "FACILITY_DEPT");
    	stdPrfColumnReferances.put("SOURCE_ID", "PRF_SRC_ID");
    	stdPrfColumnReferances.put("CREATE_DATE", "CREATE_DATE");
    	stdPrfColumnReferances.put("FACILITY_TYPE", "FACILITY_TYPE");
    	stdPrfColumnReferances.put("FACILITY_TYPE_OTHER_TEXT", "FACILITY_TYPE_OTHER_TEXT");
    	
    	
    	

    	rawStdColumnReferances.put("FACILITY_NAME", "ZLRFACILITYNAME");
    	rawStdColumnReferances.put("FACILITY_ADDRESS", "ZLRFACILITYADDRESSLINE1");
    	rawStdColumnReferances.put("FACILITY_ADDRESS_2", "ZLRFACILITYADDRESSLINE2");
    	rawStdColumnReferances.put("FACILITY_CITY", "ZLRFACILITYCITY");
    	rawStdColumnReferances.put("FACILITY_STATE", "ZLRFACILITYSTATE");
    	rawStdColumnReferances.put("FACILITY_ZIPCODE", "ZLRFACILITYZIPCODE");
    	rawStdColumnReferances.put("FACILITY_PHONE", "ZLRFACILITYPHONE");
    	
    	rawStdColumnReferances.put("PROVIDER_ADDRESS", "ZLRPROVIDERADDRESSLINE1");
    	rawStdColumnReferances.put("PROVIDER_CITY", "ZLRPROVIDERCITY");
    	rawStdColumnReferances.put("PROVIDER_STATE", "ZLRPROVIDERSTATE");
    	rawStdColumnReferances.put("PROVIDER_ZIPCODE", "ZLRPROVIDERZIPCODE");
    	rawStdColumnReferances.put("PROVIDER_PHONE", "OBRCALLBACKPHONE");
    	rawStdColumnReferances.put("PROVIDER_FNAME", "OBRPROVIDERFIRSTNAME");
    	rawStdColumnReferances.put("PROVIDER_LNAME", "OBRPROVIDERLASTNAME");
    	rawStdColumnReferances.put("PROVIDER_MNAME", "OBRPROVIDERMIDDLENAME");
    	rawStdColumnReferances.put("PROVIDER_SUFFIX", "OBRPROVIDERNAMESUFFIX");
    	
    	stdStdColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	stdStdColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	stdStdColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	stdStdColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	stdStdColumnReferances.put("FACILITY_PHONE", "FACILITY_PHONE");
    	stdStdColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	stdStdColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	stdStdColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	
    	stdStdColumnReferances.put("PROVIDER_LNAME", "PROV_LNAME");
    	stdStdColumnReferances.put("PROVIDER_ADDRESS", "PROV_ADDRESS");
    	stdStdColumnReferances.put("PROVIDER_CITY", "PROV_CITY");
    	stdStdColumnReferances.put("PROVIDER_STATE", "PROV_STATE");
    	stdStdColumnReferances.put("PROVIDER_ZIPCODE", "PROV_ZIP");
    	stdStdColumnReferances.put("PROVIDER_PLUS4", "PROV_PLUS4");
    	stdStdColumnReferances.put("PROVIDER_COUNTY", "PROV_COUNTY");
    	stdStdColumnReferances.put("PROVIDER_PHONE", "PROV_PHONE");
    	stdStdColumnReferances.put("PROVIDER_FNAME", "PROV_FNAME");
    	stdStdColumnReferances.put("PROVIDER_MNAME", "PROV_MNAME");
    	stdStdColumnReferances.put("PROVIDER_SUFFIX", "PROV_SUFFIX");
    	stdStdColumnReferances.put("SOURCE_ID", "LAB_SRC_ID");
    	stdStdColumnReferances.put("CREATE_DATE", "DATE_RECEIVED");
    	
    	
    	rawTbColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	rawTbColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	//rawTbColumnReferances.put("FACILITY_ADDRESS_2", "ZLRFACILITYADDRESSLINE2");
    	rawTbColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	rawTbColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	rawTbColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	rawTbColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	rawTbColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	rawTbColumnReferances.put("FACILITY_PHONE", "FACILITY_PHONE");
    	
    	rawTbColumnReferances.put("PROVIDER_ADDRESS", "PROVIDERADDRESS");
    	rawTbColumnReferances.put("PROVIDER_CITY", "PROVIDERCITY");
    	rawTbColumnReferances.put("PROVIDER_STATE", "PROVIDERSTATECODE");
    	rawTbColumnReferances.put("PROVIDER_ZIPCODE", "PROVIDERZIPCODE");
    	rawTbColumnReferances.put("PROVIDER_PHONE", "PROVIDERPHONE");
    	rawTbColumnReferances.put("PROVIDER_FNAME", "PROVIDERFNAME");
    	rawTbColumnReferances.put("PROVIDER_LNAME", "PROVIDERLNAME");
    	
    	stdTbColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	stdTbColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	stdTbColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	stdTbColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	stdTbColumnReferances.put("FACILITY_PHONE", "FACILITY_PHONE");
    	stdTbColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	stdTbColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	stdTbColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	
    	stdTbColumnReferances.put("PROVIDER_LNAME", "PROV_LNAME");
    	stdTbColumnReferances.put("PROVIDER_ADDRESS", "PROV_ADDRESS");
    	stdTbColumnReferances.put("PROVIDER_CITY", "PROV_CITY");
    	stdTbColumnReferances.put("PROVIDER_STATE", "PROV_STATE");
    	stdTbColumnReferances.put("PROVIDER_ZIPCODE", "PROV_ZIP");
    	stdTbColumnReferances.put("PROVIDER_PLUS4", "PROV_PLUS4");
    	stdTbColumnReferances.put("PROVIDER_COUNTY", "PROV_COUNTY");
    	stdTbColumnReferances.put("PROVIDER_PHONE", "PROV_PHONE");
    	stdTbColumnReferances.put("PROVIDER_FNAME", "PROV_FNAME");
    	
    	
    	
    	
    	
    	
    	rawMedColumnReferances.put("FNAME", "FIRST_NAME");
    	rawMedColumnReferances.put("LNAME", "LAST_NAME");
    	
    	rawMedColumnReferances.put("PROVIDER_FNAME", "PROV_NAME");
    	rawMedColumnReferances.put("PROVIDER_ADDRESS", "PROV_ADDR_STR");
    	rawMedColumnReferances.put("PROVIDER_ADDRESS_2", "PROV_ADDR_LINE_2");
    	rawMedColumnReferances.put("PROVIDER_CITY", "PROV_ADDR_CITY");
    	//rawMedColumnReferances.put("PROVIDER_STATE", "PROV_STATE");
    	rawMedColumnReferances.put("PROVIDER_ZIPCODE", "POSTAL_CD");
    	//rawMedColumnReferances.put("PROVIDER_COUNTY", "PROV_COUNTY");
    	rawMedColumnReferances.put("PROVIDER_NPI", "NPI");
    	
    	stdMedColumnReferances.put("PROVIDER_FNAME", "PROV_NAME");
    	stdMedColumnReferances.put("PROVIDER_ADDRESS", "PROV_ADDRESS");
    	stdMedColumnReferances.put("PROVIDER_CITY", "PROV_CITY");
    	stdMedColumnReferances.put("PROVIDER_COUNTY", "PROV_COUNTY");
    	stdMedColumnReferances.put("PROVIDER_STATE", "PROV_STATE");
    	stdMedColumnReferances.put("PROVIDER_ZIPCODE", "PROV_ZIP");
    	stdMedColumnReferances.put("PROVIDER_NPI", "NPI");
    	stdMedColumnReferances.put("FILE_NAME", "MEDICAID_FILE");
    	
    	    	
    	rawAirsColumnReferances.put("FNAME", "FNAME");
    	rawAirsColumnReferances.put("LNAME", "LNAME");
    	
       	rawAirsColumnReferances.put("FACILITY_ADDRESS", "PROV_ADDR");
    	rawAirsColumnReferances.put("FACILITY_CITY", "PROV_CITY");
    	rawAirsColumnReferances.put("FACILITY_STATE", "PROV_ST");
    	rawAirsColumnReferances.put("FACILITY_ZIPCODE", "PROV_ZIP");
    	rawAirsColumnReferances.put("FACILITY_PHONE", "PROV_PHONE");
    	rawAirsColumnReferances.put("FACILITY_NAME", "PROVNAME");
    	rawAirsColumnReferances.put("FACILITY_TYPE", "PROVIDER_DESCRIPTION");
    	
    	
    	stdAirsColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	stdAirsColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	stdAirsColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	stdAirsColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	stdAirsColumnReferances.put("FACILITY_PHONE", "FACILITY_PHONE");
    	stdAirsColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	stdAirsColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	stdAirsColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	stdAirsColumnReferances.put("FACILITY_TYPE", "PROVIDER_DESCRIPTION");
    	stdAirsColumnReferances.put("SOURCE_ID", "AIRS_SRC_ID");
    	stdAirsColumnReferances.put("FILE_NAME", "ADAP_FILE");
    	
    	
    	rawAdapColumnReferances.put("FNAME", "FIRST");
    	rawAdapColumnReferances.put("LNAME", "LAST");
    	rawAdapColumnReferances.put("PROVIDER_NPI", "PROVNPI");
    	
    	stdAdapColumnReferances.put("PROVIDER_ADDRESS", "RX_PROV_ADDRESS");
    	stdAdapColumnReferances.put("PROVIDER_CITY", "RX_PROV_CITY");
    	stdAdapColumnReferances.put("PROVIDER_COUNTY", "RX_PROV_COUNTY");
    	stdAdapColumnReferances.put("PROVIDER_FNAME", "RX_PROV_FNAME");
    	stdAdapColumnReferances.put("PROVIDER_MNAME", "RX_PROV_MNAME");
    	stdAdapColumnReferances.put("PROVIDER_LNAME", "RX_PROV_LNAME");
    	stdAdapColumnReferances.put("PROVIDER_NPI", "RX_PROV_NPI");
    	stdAdapColumnReferances.put("PROVIDER_PHONE", "RX_PROV_PHONE");
    	stdAdapColumnReferances.put("PROVIDER_PLUS4", "RX_PROV_PLUS4");
    	stdAdapColumnReferances.put("PROVIDER_STATE", "RX_PROV_STATE");
    	stdAdapColumnReferances.put("PROVIDER_ZIPCODE", "RX_PROV_ZIP");
    	stdAdapColumnReferances.put("SOURCE_ID", "ADAP_SRC_ID");
    	stdAdapColumnReferances.put("FILE_NAME", "ADAP_FILE");

    	rawDeathColumnReferances.put("FNAME", "FIRST");
    	rawDeathColumnReferances.put("LNAME", "LAST");
    	rawDeathColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDR");
    	rawDeathColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	rawDeathColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	rawDeathColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	rawDeathColumnReferances.put("FACILITY_PHONE", "FACILITY_PHONE");
    	rawDeathColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	rawDeathColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	rawDeathColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	rawDeathColumnReferances.put("FACILITY_TYPE", "FACILITY_TYPE");
    	rawDeathColumnReferances.put("PFI", "PFI");
    	
    	stdDeathColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	stdDeathColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	stdDeathColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	stdDeathColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	stdDeathColumnReferances.put("FACILITY_PHONE", "FACILITY_PHONE");
    	stdDeathColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	stdDeathColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	stdDeathColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	stdDeathColumnReferances.put("FACILITY_TYPE", "FACILITY_TYPE");
    	stdDeathColumnReferances.put("PFI", "PFI");
    	stdDeathColumnReferances.put("SOURCE_ID", "DEATH_SRC_ID");
    	stdDeathColumnReferances.put("FILE_NAME", "DEATH_FILE");

    	rawNycDeathColumnReferances.put("FNAME", "FIRST");
    	rawNycDeathColumnReferances.put("LNAME", "LAST");
    	rawNycDeathColumnReferances.put("PFI", "PFI");

    	stdNycDeathColumnReferances.put("FACILITY_ADDRESS", "FACILITY_ADDRESS");
    	stdNycDeathColumnReferances.put("FACILITY_CITY", "FACILITY_CITY");
    	stdNycDeathColumnReferances.put("FACILITY_STATE", "FACILITY_STATE");
    	stdNycDeathColumnReferances.put("FACILITY_ZIPCODE", "FACILITY_ZIP");
    	stdNycDeathColumnReferances.put("FACILITY_PHONE", "FACILITY_PHONE");
    	stdNycDeathColumnReferances.put("FACILITY_PLUS4", "FACILITY_PLUS4");
    	stdNycDeathColumnReferances.put("FACILITY_COUNTY", "FACILITY_COUNTY");
    	stdNycDeathColumnReferances.put("FACILITY_NAME", "FACILITY_NAME");
    	stdNycDeathColumnReferances.put("FACILITY_TYPE", "FACILITY_TYPE");
    	stdNycDeathColumnReferances.put("PFI", "PFI");
    	stdNycDeathColumnReferances.put("FILE_NAME", "DEATH_FILE");
    	
		oldNycRaceColumnMap.put("1", "RACE_WHITE");
		oldNycRaceColumnMap.put("2", "RACE_BLACK");
		oldNycRaceColumnMap.put("3", "RACE_INDIAN");
		oldNycRaceColumnMap.put("4", "RACE_ASIAN");
		oldNycRaceColumnMap.put("5", "RACE_ASIAN");
		oldNycRaceColumnMap.put("7", "RACE_ASIAN");
		oldNycRaceColumnMap.put("8", "RACE_ASIAN");
		oldNycRaceColumnMap.put("A", "RACE_ASIAN");
		oldNycRaceColumnMap.put("B", "RACE_ASIAN");
		oldNycRaceColumnMap.put("C", "RACE_ASIAN");
		oldNycRaceColumnMap.put("6", "RACE_PACIFIC");
		oldNycRaceColumnMap.put("C", "RACE_PACIFIC");
		oldNycRaceColumnMap.put("E", "RACE_PACIFIC");	
 
    	stdMedOldColumnReferances.put("SOURCE_ID", "MEDICAID_SRC_ID"); 
    	stdMedOldColumnReferances.put("FILE_NAME", "MEDICAID_FILE");
    	
    	
    	rawNycPrfColumnReferances.put("FNAME", "FNAME");
    	rawNycPrfColumnReferances.put("LNAME", "LNAME");
    	
        stdNycPrfColumnReferances.put("SOURCE_ID", "PRF_SRC_ID");
        stdNycPrfColumnReferances.put("CREATE_DATE", "CREATE_DATE");
        stdOldCaseColumnReferances.put("SOURCE_ID", "OLDCASE_SRC_ID");
        
        
        rawSsdmColumnReferances.put("FNAME", "FIRSTNAME");
        rawSsdmColumnReferances	.put("LNAME", "LASTNAME");
        stdSsdmColumnReferances.put("SOURCE_ID", "SSDM_SRC_ID");
        
        rawTbColumnReferances.put("FNAME", "FIRSTNAME");
        rawTbColumnReferances.put("LNAME", "LASTNAME");
        
        stdTbColumnReferances.put("SOURCE_ID", "SERIALNO");
        stdTbColumnReferances.put("FILE_NAME", "TB_FILE");
        stdDoccsColumnReferances.put("FILE_NAME", "DOCCS_FILE");
        
        rawEprfOOCColumnReferances.put("FNAME", "FNAME");
        rawEprfOOCColumnReferances.put("LNAME", "LNAME");
        
        stdEprfOOCColumnReferances.put("FILE_NAME", "UPLOAD_FILENAME");
        rawNycIJRColumnReferances.put("FNAME", "FNAME");
        rawNycIJRColumnReferances.put("LNAME", "LNAME");
        stdNycIJRColumnReferances.put("FILE_NAME", "FILE_NAME");
        stdOldColumnReferances.put("FILE_NAME", "STD_FILE");
        
        rawICCRColumnReferances.put("FNAME", "FIRST_NAME");
        rawICCRColumnReferances.put("MNAME", "MIDDLE_NAME");
        rawICCRColumnReferances.put("LNAME", "LAST_NAME");
        
        /*
         * It would be possible to write a method that would create this string based
         * on tables of lengths and column names and source code snippets, etc.,
         * but it might not be worth the time.
         */

        deathStandardDocumentsQuery.append("  SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || NVL((LPAD (std.SUFFIX, 5, ' ')),'     ') || NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') || NVL((LPAD (trim(raw1.PIDADDRESSLINE1)||' '||trim(raw1.PIDADDRESSLINE2), 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.SSN, 9, ' ')),'         ') || NVL((LPAD (trim(raw1.ZLRPROVIDERADDRESSLINE1)||' '||trim(raw1.ZLRPROVIDERADDRESSLINE2), 62, ' ')),'                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (trim(raw1.ZLRFACILITYADDRESSLINE1)||' '||trim(raw1.ZLRFACILITYADDRESSLINE2), 62, ' ')),'                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),'  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.NYSID,9, ' ')), '         ') || NVL((LPAD (std.DIN, 7, ' ')),'       ') || NVL((LPAD (std.MEDICAID_NUMBER, 20, ' ')),'                    ')|| NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.GENDER, 1, ' ')),' ') || NVL((LPAD (std.LAB_SRC_ID, 20, ' ')),'                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'HIV_LAB   ' AS output FROM std_hiv_eclrs_doc std, raw_hiv_eclrs_doc raw1, DOCUMENT_LOCATOR doc where std.current_process_status = '5'  and raw1.DOCUMENT_ID = std.DOCUMENT_ID and raw1.document_id = doc.document_id and NVL(doc.TEST_TYPE_CD, 'XX') = 'RESISTANCE'");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || NVL((LPAD (std.SUFFIX, 5, ' ')),'     ') || NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') || NVL((LPAD (trim(raw1.PIDADDRESSLINE1)||' '||trim(raw1.PIDADDRESSLINE2), 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.SSN, 9, ' ')),'         ') || NVL((LPAD (trim(raw1.ZLRPROVIDERADDRESSLINE1)||' '||trim(raw1.ZLRPROVIDERADDRESSLINE2), 62, ' ')),'                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (trim(raw1.ZLRFACILITYADDRESSLINE1)||' '||trim(raw1.ZLRFACILITYADDRESSLINE2), 62, ' ')),'                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),'  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.NYSID,9, ' ')), '         ') || NVL((LPAD (std.DIN, 7, ' ')),'       ') || NVL((LPAD (std.MEDICAID_NUMBER, 20, ' ')),'                    ')|| NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.GENDER, 1, ' ')),' ') || NVL((LPAD (std.LAB_SRC_ID, 20, ' ')),'                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'HIV_LAB   ' AS output FROM std_hiv_eclrs_doc std, raw_hiv_eclrs_doc raw1, DOCUMENT_LOCATOR doc where std.current_process_status = '5'  and raw1.DOCUMENT_ID = std.DOCUMENT_ID and raw1.document_id = doc.document_id and NVL(doc.TEST_TYPE_CD, 'XX') != 'RESISTANCE'");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || NVL((LPAD (std.SUFFIX, 5, ' ')),'     ') || NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') || NVL((LPAD (trim(raw1.PIDADDRESSLINE1)||' '||trim(raw1.PIDADDRESSLINE2), 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.SSN, 9, ' ')),'         ') || NVL((LPAD (trim(raw1.ZLRPROVIDERADDRESSLINE1)||' '||trim(raw1.ZLRPROVIDERADDRESSLINE2), 62, ' ')),'                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (trim(raw1.ZLRFACILITYADDRESSLINE1)||' '||trim(raw1.ZLRFACILITYADDRESSLINE2), 62, ' ')),'                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),'  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.NYSID,9, ' ')), '         ') || NVL((LPAD (std.DIN, 7, ' ')),'       ') || NVL((LPAD (std.MEDICAID_NUMBER, 20, ' ')),'                    ') || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.GENDER, 1, ' ')),' ') || NVL((LPAD (std.LAB_SRC_ID, 20, ' ')),'                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'STD_LAB   ' AS output FROM std_std_eclrs_doc std, raw_std_eclrs_doc raw1 where std.current_process_status = '5'  and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || NVL((LPAD (std.SUFFIX, 5, ' ')),'     ') || NVL((LPAD (std.DOB, 10, ' ')),'          ') || NVL((LPAD (trim(raw1.ADDRESS), 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.SSN, 9, ' ')),'         ') || NVL((LPAD (trim(raw1.PROV_ADDR), 62, ' ')),'                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (trim(raw1.FACILITY_ADDR), 62, ' ')),'                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),'  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.NYSID,9, ' ')), '         ') || NVL((LPAD (std.DIN, 7, ' ')),'       ') || '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') || '                    ' || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'EPRF_OOC  ' AS output FROM STD_EPRF_OOC_UPLOAD std, RAW_EPRF_OOC_UPLOAD raw1 where std.current_process_status = '5'  and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
        deathStandardDocumentsQuery.append("  UNION  ALL   SELECT                                             '                              '  || NVL((LPAD (    FNAME, 20, ' ')), '                    ') || NVL((LPAD (    LNAME, 25, ' ')),'                         ') || NVL((LPAD (    MNAME, 25, ' ')),'                         ') || NVL((LPAD (    SUFFIX, 5, ' ')),'     ') || NVL((LPAD (    DOB_TEXT, 10, ' ')),                  '          ') || NVL((LPAD (TRIM(PERSON_ADDRESS), 62, ' ')),                                                                                             '                                                              ') || NVL((LPAD (    PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (    PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (    PERSON_ZIP, 5, ' ')),'     ') || NVL((LPAD (    PERSON_PLUS4, 4, ' ')),'    ') || NVL((LPAD (    SSN, 9, ' ')),'         ') || NVL ((LPAD (CASE WHEN FLAG_CHOSEN_PROVIDER = 'PCP' THEN PCP_PROV_ADDRESS WHEN FLAG_CHOSEN_PROVIDER = 'RX' THEN RX_PROV_ADDRESS WHEN FLAG_CHOSEN_PROVIDER = 'CERT' THEN CERT_PROV_ADDRESS ELSE RX_PROV_ADDRESS END, 62,' ')), '                                                              ')|| NVL((LPAD (CASE WHEN FLAG_CHOSEN_PROVIDER = 'PCP' THEN PCP_PROV_CITY  WHEN FLAG_CHOSEN_PROVIDER = 'RX' THEN RX_PROV_CITY WHEN FLAG_CHOSEN_PROVIDER = 'CERT' THEN CERT_PROV_CITY ELSE RX_PROV_CITY END, 30, ' ')), '                              ') || NVL ((LPAD (CASE WHEN FLAG_CHOSEN_PROVIDER = 'PCP' THEN PCP_PROV_STATE WHEN FLAG_CHOSEN_PROVIDER = 'RX' THEN RX_PROV_STATE WHEN FLAG_CHOSEN_PROVIDER = 'CERT' THEN CERT_PROV_STATE ELSE RX_PROV_STATE END, 2, ' ')), '  ') || NVL ((LPAD (CASE WHEN FLAG_CHOSEN_PROVIDER = 'PCP' THEN PCP_PROV_ZIP WHEN FLAG_CHOSEN_PROVIDER = 'RX' THEN RX_PROV_ZIP WHEN FLAG_CHOSEN_PROVIDER = 'CERT' THEN CERT_PROV_ZIP ELSE RX_PROV_ZIP END, 5, ' ')), '     ')|| NVL ((LPAD(CASE WHEN FLAG_CHOSEN_PROVIDER = 'PCP' THEN PCP_PROV_PLUS4 WHEN FLAG_CHOSEN_PROVIDER = 'RX' THEN RX_PROV_PLUS4 WHEN FLAG_CHOSEN_PROVIDER = 'CERT' THEN CERT_PROV_PLUS4 ELSE RX_PROV_PLUS4 END,  4, ' ')), '    ')||                                                                                                                    '                                                              '  ||                                                                                                              '                              '  ||                                                                                                               '  '  ||                                                                                                           '     '  ||                                                                                                               '    '  ||                             '         ' ||                              '       ' || NVL((LPAD (    MEDICAID_NUMBER, 20, ' ')),'                    ')|| NVL((LPAD (    ETHNICCD,  2, ' ')),'  ') || NVL((LPAD (    CURRENT_GENDER, 1, ' ')),' ') || NVL((LPAD (    ADAP_SRC_ID,  20, ' ')), '                    ') || NVL((LPAD (    DOCUMENT_ID, 20, ' ')),'                    ') || 'ADAP      ' AS output FROM std_ADAP_doc                                  where     current_process_Status =  5");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')), '                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (TO_CHAR(std.DOB, 'MM/DD/YYYY'), 10, ' ')),'          ') || NVL((LPAD (raw1.ADDRESS , 62, ' ')),                                                                                                    '                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') ||                                       '    '  || NVL((LPAD (std.SSN, 9, ' ')),'         ') ||                                                                                                                                                                                                                              '                                                              ' ||                                                                                                                                                                                                                   '                              '  ||                                                                                                                                                                                                                      '  '  ||                                                                                                                                                                                                              '     ' ||                                                                                                                                                                                                                      '    ' || NVL((LPAD (trim(raw1.PROV_ADDR), 62, ' ')),                                                                        '                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),                                                                     '                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),                                                                      '  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),                                                                    '     ') ||                                                                                                               '    '  ||                             '         ' ||                              '       ' || NVL((LPAD (std.MEDICAID_NUMBER, 20, ' ')),'                    ')|| NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') || NVL((LPAD (std.AIRS_SRC_ID,  20, ' ')), '                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'AIRS      ' AS output FROM STD_AIRS_DOC std, raw_AIRS_doc raw1           where std.current_process_status = '5' and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
		deathStandardDocumentsQuery.append(" UNION ALL SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')), '                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || NVL((LPAD (std.SUFFIX, 5, ' ')),'     ') || NVL((LPAD (std.DOB_TEXT, 10, ' ')),                  '          ') || NVL((LPAD (trim(raw1.RES_APT_NO)||' '||trim(raw1.RES_STREET_NO)||' '||trim(raw1.RES_STREET)||' '||trim(raw1.RES_STREET_TYPE), 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') ||                                       '    '  || NVL((LPAD (std.SSN, 9, ' ')),'         ') ||                                                                                                                                                                                                                              '                                                              ' ||                                                                                                                                                                                                                   '                              '  ||                                                                                                                                                                                                                      '  '  ||                                                                                                                                                                                                              '     ' ||                                                                                                                                                                                                                      '    ' || NVL((LPAD (trim(std.ORIG_FACILITY_ADDRESS), 62, ' ')),                                                             '                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),                                                                     '                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),                                                                      '  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),                                                                    '     ') ||                                                                                                               '    '  ||                             '         ' ||                              '       ' ||                                           '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') || NVL((LPAD (std.DEATH_SRC_ID, 20, ' ')), '                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'DTH       ' AS output FROM std_death_doc std, raw_death_doc raw1         where std.current_process_status = '5' and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')), '                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || NVL((LPAD (std.SUFFIX, 5, ' ')),'     ') || NVL((LPAD (std.DOB_TEXT, 10, ' ')),                  '          ') || NVL((LPAD (trim(raw1.RES_APT_NO)||' '||trim(raw1.RES_STREET_NO)||' '||trim(raw1.RES_STREET)||' '||trim(raw1.RES_STREET_TYPE), 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || NVL((LPAD (std.PERSON_PLUS4, 4, ' ')),'    ') || NVL((LPAD (std.SSN, 9, ' ')),'         ') ||                                                                                                                                                                                                                              '                                                              ' ||                                                                                                                                                                                                                   '                              '  ||                                                                                                                                                                                                                      '  '  ||                                                                                                                                                                                                              '     ' ||                                                                                                                                                                                                                      '    ' || NVL((LPAD (trim(std.ORIG_FACILITY_ADDRESS), 62, ' ')),                                                             '                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),                                                                     '                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),                                                                      '  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),                                                                    '     ') || NVL((LPAD (std.FACILITY_PLUS4, 4, ' ')),                                                                      '    ') ||                             '         ' ||                              '       ' ||                                           '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') ||                                         '                    '  || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'NYC_DTH   ' AS output FROM STD_NYC_DEATH_DOC std, RAW_NYC_DEATH_DOC raw1 where std.current_process_status = '5' and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT                                             '                              '  || NVL((LPAD (std.FNAME, 20, ' ')), '                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (std.DOB_TEXT, 10, ' ')),                  '          ') ||                                                                                                                                         '                                                              '  ||                                       '                              '  ||                                       '  '  || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') ||                                       '    '  || NVL((LPAD (std.SSN, 9, ' ')),'         ') ||                                                                                                                                                                                                                              '                                                              ' ||                                                                                                                                                                                                                   '                              '  ||                                                                                                                                                                                                                      '  '  ||                                                                                                                                                                                                              '     ' ||                                                                                                                                                                                                                      '    ' ||                                                                                                                    '                                                              '  ||                                                                                                              '                              '  ||                                                                                                               '  '  ||                                                                                                           '     '  ||                                                                                                               '    '  ||                             '         ' ||                              '       ' || NVL((LPAD (std.MEDICAID_NUMBER, 20, ' ')),'                    ')|| NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.GENDER,         1, ' ')),' ') ||                                         '                    '  || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'CASA      ' AS output FROM std_casa_doc std                              where std.current_process_status = '5'");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT                                             '                              '  || NVL((LPAD (    FNAME, 20, ' ')), '                    ') || NVL((LPAD (    LNAME, 25, ' ')),'                         ') || NVL((LPAD (    MNAME, 25, ' ')),'                         ') || NVL((LPAD (    SUFFIX, 5, ' ')),'     ') || NVL((LPAD (    DOB_TEXT, 10, ' ')),                  '          ') || NVL((LPAD (PERSON_ADDRESS, 62, ' ')),                                                                                                   '                                                              ') || NVL((LPAD (    PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (    PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (    PERSON_ZIP, 5, ' ')),'     ') || NVL((LPAD (PERSON_PLUS4, 4, ' ')),    '    ') || NVL((LPAD (    SSN, 9, ' ')),'         ') ||                                                                                                                                                                                                                              '                                                              ' ||                                                                                                                                                                                                                   '                              '  ||                                                                                                                                                                                                                      '  '  ||                                                                                                                                                                                                              '     ' ||                                                                                                                                                                                                                      '    ' || NVL((LPAD (CASE WHEN UNDER_CUSTODY = 'Y' THEN CURRENT_FACILITY_ADDRESS ELSE OWNING_FACILITY_ADDRESS END, 62,' ')), '                                                              ') || NVL((LPAD (CASE WHEN UNDER_CUSTODY = 'Y' THEN CURRENT_FACILITY_CITY ELSE OWNING_FACILITY_CITY END, 30, ' ')),'                              ') || NVL((LPAD (CASE WHEN UNDER_CUSTODY = 'Y' THEN CURRENT_FACILITY_STATE ELSE OWNING_FACILITY_STATE END, 2, ' ')),'  ') || NVL((LPAD (CASE WHEN UNDER_CUSTODY = 'Y' THEN CURRENT_FACILITY_ZIP ELSE OWNING_FACILITY_ZIP END, 5, ' ')),'     ') || NVL((LPAD (CASE WHEN UNDER_CUSTODY = 'Y' THEN CURRENT_FACILITY_PLUS4 ELSE OWNING_FACILITY_PLUS4 END, 4, ' ')),'    ') ||  NVL((LPAD (NYSID,9, ' ')), '         ')|| NVL((LPAD (    DIN, 7, ' ')),'       ')|| NVL((LPAD     (MEDICAID_NUMBER, 20, ' ')),'                    ')|| NVL((LPAD (    ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (    CURRENT_GENDER, 1, ' ')),' ') ||                                         '                    '  || NVL((LPAD (    DOCUMENT_ID, 20, ' ')),'                    ') || 'DIN_DOCCS ' AS output FROM STD_DIN_DOCCS_DOC                             where     current_process_status = '5'");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT                                             '                              '  || NVL((LPAD (std.FNAME, 20, ' ')), '                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MI, 25, ' ')),   '                         ') ||                                 '     '  || NVL((LPAD (std.DOB_TEXT, 10, ' ')),                  '          ') || NVL((LPAD (trim(raw1.STREET), 62, ' ')),                                                                                                '                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || NVL((LPAD (std.PERSON_PLUS4, 4, ' ')),'    ') ||                              '         '  || NVL((LPAD (trim(std.RAW_PROVIDER_ADDRESS), 62, ' ')),                                                                                                                                                                        '                                                              ')|| NVL((LPAD (std.PROV_CITY, 30, ' ')),                                                                                                                                                                          '                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),                                                                                                                                                                             '  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),                                                                                                                                                                       '     ')|| NVL((LPAD (std.PROV_PLUS4, 4, ' ')),                                                                                                                                                                             '    ')|| NVL((LPAD (trim(raw1.FACILITY_ADDRESS), 62, ' ')),                                                                 '                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),                                                                     '                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),                                                                      '  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),                                                                    '     ') || NVL((LPAD (std.FACILITY_PLUS4, 4, ' ')),                                                                      '    ') ||                             '         ' || NVL((LPAD (std.DIN, 7, ' ')),'       ')||                                           '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.BIRTH_SEX, 1, ' ')),     ' ') || NVL((LPAD (std.SERIALNO, 20, ' ')),     '                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'TB        ' AS output FROM STD_TB_DOC std, raw_TB_DOC raw1               where std.current_process_status = '5' and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT                                             '                              '  || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') || NVL((LPAD (raw1.MAIL_ADDR_STR, 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.SSN, 9, ' ')),'         ')||NVL((LPAD (trim(prov.PROV_ADDR_STR)||' '||trim(prov.PROV_ADDR_LINE_2), 62, ' ')), '                                                              ') || NVL((LPAD (prov.PROV_ADDR_CITY, 30, ' ')),'                              ') || '  ' || NVL((LPAD (prov.POSTAL_CD, 5, ' ')),'     ') || '    ' || '                                                              ' || '                              ' || '  ' || '     ' || '    ' || '         ' || '       ' || NVL((LPAD (std.MEDICAID_NUMBER, 20, ' ')),'                    ')|| NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') || '                    ' || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'INS       ' AS output FROM STD_MEDICAID_DOC std, RAW_MEDICAID_DOC raw1, RAW_MEDICAID_DOC_PROVIDERS prov where std.current_process_status = '5' and raw1.DOCUMENT_ID = std.DOCUMENT_ID and std.DOCUMENT_ID = prov.MEDICAID_DOC_ID(+)");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT                                             '                              '  || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') || NVL((LPAD (trim(std.PERSON_ADDRESS), 62, ' ')),                                                                                         '                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || NVL((LPAD (std.PERSON_PLUS4, 4, ' ')),'    ') ||                              '         '  ||                                                                                                                                                                                                                              '                                                              ' ||                                                                                                                                                                                                                   '                              '  ||                                                                                                                                                                                                                      '  '  ||                                                                                                                                                                                                              '     ' ||                                                                                                                                                                                                                      '    ' ||                                                                                                                    '                                                              '  ||                                                                                                              '                              '  ||                                                                                                               '  '  ||                                                                                                           '     '  ||                                                                                                               '    '  ||                             '         ' ||                              '       ' ||                                           '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.GENDER,         1, ' ')),' ') ||                                         '                    '  || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'CCD       ' AS output FROM std_ccd_doc std                               where std.current_process_Status =  5 ");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  '                              ' || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || '     ' || NVL((LPAD (TO_CHAR(std.DOB,'MM/DD/YYYY'), 10, ' ')),'          ') || NVL((LPAD (trim(raw1.STREET), 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || '         ' || NVL((LPAD (trim(raw1.PROVIDERADDRESS), 62, ' ')),'                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || '                                                              ' || '                              ' || '  ' || '     ' || '    ' ||  '         ' || '       ' || '                    '|| NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.BIRTH_SEX, 1, ' ')),' ') || '                    ' || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'SYPH      ' AS output FROM STD_SYPHILIS_DOC std, RAW_SYPHILIS_DOC raw1 where std.current_process_status = '5'  and raw1.DOCUMENT_ID = std.DOCUMENT_ID ");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  '                              ' || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || '     ' || NVL((LPAD (TO_CHAR(std.DOB,'MM/DD/YYYY'), 10, ' ')),'          ') || NVL((LPAD (trim(std.PERSON_ADDRESS), 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || '         ' || NVL((LPAD (trim(std.PROV_ADDRESS), 62, ' ')),'                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || '                                                              ' || '                              ' || '  ' || '     '|| '    ' || '         ' || '       ' || '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') || '                    ' || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'STD_CONF  ' AS output FROM STD_STD_CONFIRMED_DOC std, RAW_STD_CONFIRMED_DOC raw1 where std.current_process_status = '5'  and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || NVL((LPAD (std.SUFFIX, 5, ' ')),'     ') || NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') || NVL((LPAD (raw1.PERSON_ADDRESS, 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || '         ' || '                                                              ' || '                              ' || '  ' || '     ' || '    ' || NVL((LPAD (raw1.FACILITY_ADDRESS, 62, ' ')),'                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),'  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),'     ') || '    ' || '         ' || NVL((LPAD (std.DIN, 7, ' ')),'       ') || '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.GENDER, 1, ' ')),' ') || NVL((LPAD (std.PRF_SRC_ID, 20, ' ')),'                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'PRF       ' AS output FROM std_prf_doc std, raw_PRF_DOC raw1 where std.current_process_status = '5' and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (TO_CHAR(std.DOB, 'MM/DD/YYYY'), 10, ' ')),'          ') || NVL((LPAD (raw1.ADDRESS, 62, ' ')),'                                                              ') 											   || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.SSN, 9, ' ')),'         ') || NVL((LPAD (trim(raw1.PROADD), 62, ' ')),'                                                              ')  														   || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || '                                                              ' 																								    || '                              ' 										 || '  ' 										  || '     ' 									    || '    ' || '         ' 								 || NVL((LPAD (std.DIN, 7, ' ')),'       ') || '                    ' 							         || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') || NVL((LPAD (std.PRF_SRC_ID, 20, ' ')),'                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'NYC_PRF   ' AS output FROM std_nycprf_doc std, raw_nycprf_doc raw1 where std.current_process_status = '5' and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
        deathStandardDocumentsQuery.append(" UNION ALL SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') || NVL((LPAD (trim(raw1.CUR_STREET_ADDRESS1), 62, ' ')),'                                                              ') || NVL((LPAD (std.CUR_PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.CUR_PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.CUR_PERSON_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.SSN, 9, ' ')),'         ') || NVL((LPAD (trim(raw1.PROV_ADDR), 62, ' ')),'                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || '                                                              ' || '                              ' || '  ' || '     ' || '    ' || NVL((LPAD (std.NYSID,9, ' ')), '         ') || NVL((LPAD (std.DIN, 7, ' ')),'       ') || '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') || NVL((LPAD (std.NYC_IJR_SRC_ID, 20, ' ')),'                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'NYC_IJR   ' AS output FROM STD_NYCIJR_DOC std, RAW_NYCIJR_DOC raw1 where std.current_process_status = '5'  and raw1.DOCUMENT_ID = std.DOCUMENT_ID"); 
        deathStandardDocumentsQuery.append(" UNION ALL  SELECT                                             '                              '  || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (to_char(std.DOB,'MM/DD/YYYY'), 10, ' ')),     '          ') || NVL((LPAD (trim(raw1.PERSON_ADDRESS), 62, ' ')),     '                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),    '                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),    '  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),    '     ') || '    ' ||                              '         '  || NVL((LPAD (trim(raw1.PROV_ADDRESS), 62, ' ')),  '                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || '                                                              ' || '                              ' || '  ' || '     ' || '    ' ||                                '         '  ||                              '       '  || '                    ' ||                                     '  '  ||                                           ' '  ||                                          '                    '  || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'ICCR      ' AS output FROM STD_ICCR_DOC std, RAW_ICCR_DOC raw1 where std.current_process_Status =  5  and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
        
        
        ssdmStandardDocumentsQuery.append(" SELECT NVL((LPAD (std.FNAME, 20, ' ')),'                    ') ||NVL((LPAD (std.LNAME, 25, ' ')),'                         ') ||NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||'     ' ||NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') ||NVL((LPAD (std.SSN, 9, ' ')),'         ')||NVL((LPAD (std.SSDM_SRC_ID, 20, ' ')),'                    ') ||NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') ||'SSDM      ' AS output FROM STD_SSDM_DOC std where std.current_process_status = 5");
        
        liveStandardDocumentsQuery.append(" SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') || NVL((LPAD (std.SUFFIX, 5, ' ')),'     ') || NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') || NVL((LPAD (raw1.PERSON_ADDRESS, 62, ' ')),'                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || '         ' || '                                                              ' || '                              ' || '  ' || '     ' || '    ' || NVL((LPAD (raw1.FACILITY_ADDRESS, 62, ' ')),'                                                              ') || NVL((LPAD (std.FACILITY_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.FACILITY_STATE, 2, ' ')),'  ') || NVL((LPAD (std.FACILITY_ZIP, 5, ' ')),'     ') || '    ' || '         ' || NVL((LPAD (std.DIN, 7, ' ')),'       ') || '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.GENDER, 1, ' ')),' ') || NVL((LPAD (std.PRF_SRC_ID, 20, ' ')),'                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'PRF       ' AS output FROM std_prf_doc std, raw_PRF_DOC raw1 where std.current_process_status = '5' and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
		liveStandardDocumentsQuery.append(" UNION SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (TO_CHAR(std.DOB, 'MM/DD/YYYY'), 10, ' ')),'          ') || NVL((LPAD (raw1.ADDRESS, 62, ' ')),'                                                              ') 											   || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.SSN, 9, ' ')),'         ') || NVL((LPAD (trim(raw1.PROADD), 62, ' ')),'                                                              ')  														   || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || '                                                              ' 																								    || '                              ' 										 || '  ' 										  || '     ' 									    || '    ' || '         ' 								 || NVL((LPAD (std.DIN, 7, ' ')),'       ') || '                    ' 							         || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') || NVL((LPAD (std.PRF_SRC_ID, 20, ' ')),'                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'NYC_PRF   ' AS output FROM std_nycprf_doc std, raw_nycprf_doc raw1 where std.current_process_status = '5' and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
		liveStandardDocumentsQuery.append("  UNION     SELECT  NVL((LPAD(std.MED_RECORD_NUMBER, 30, ' ')),'                              ') || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (std.DOB_TEXT, 10, ' ')),'          ') || NVL((LPAD (trim(raw1.CUR_STREET_ADDRESS1), 62, ' ')),'                                                              ') || NVL((LPAD (std.CUR_PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.CUR_PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.CUR_PERSON_ZIP, 5, ' ')),'     ') || '    ' || NVL((LPAD (std.SSN, 9, ' ')),'         ') || NVL((LPAD (trim(raw1.PROV_ADDR), 62, ' ')),'                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || '                                                              ' || '                              ' || '  ' || '     ' || '    ' || NVL((LPAD (std.NYSID,9, ' ')), '         ') || NVL((LPAD (std.DIN, 7, ' ')),'       ') || '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.CURRENT_GENDER, 1, ' ')),' ') || NVL((LPAD (std.NYC_IJR_SRC_ID, 20, ' ')),'                    ') || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'NYC_IJR   ' AS output FROM STD_NYCIJR_DOC std, RAW_NYCIJR_DOC raw1 where std.current_process_status = '5'  and raw1.DOCUMENT_ID = std.DOCUMENT_ID"); 
		liveStandardDocumentsQuery.append("  UNION     SELECT                                             '                              '  || NVL((LPAD (std.FNAME, 20, ' ')),'                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (to_char(std.DOB,'MM/DD/YYYY'), 10, ' ')),     '          ') || NVL((LPAD (trim(raw1.PERSON_ADDRESS), 62, ' ')),     '                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),    '                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),    '  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),    '     ') || '    ' ||                              '         '  || NVL((LPAD (trim(raw1.PROV_ADDRESS), 62, ' ')),  '                                                              ') || NVL((LPAD (std.PROV_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PROV_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PROV_ZIP, 5, ' ')),'     ') || '    ' || '                                                              ' || '                              ' || '  ' || '     ' || '    ' ||                                '         '  ||                              '       '  || '                    ' ||                                     '  '  ||                                           ' '  ||                                          '                    '  || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'ICCR      ' AS output FROM STD_ICCR_DOC std, RAW_ICCR_DOC raw1 where std.current_process_Status =  5  and raw1.DOCUMENT_ID = std.DOCUMENT_ID");
		
        //liveStandardDocumentsQuery.append(" UNION SELECT                                             '                              '  || NVL((LPAD (std.FNAME, 20, ' ')), '                    ') || NVL((LPAD (std.LNAME, 25, ' ')),'                         ') || NVL((LPAD (std.MNAME, 25, ' ')),'                         ') ||                                 '     '  || NVL((LPAD (std.DOB_TEXT, 10, ' ')),                  '          ') || NVL((LPAD (trim(std.PERSON_ADDRESS), 62, ' ')),                                                                                         '                                                              ') || NVL((LPAD (std.PERSON_CITY, 30, ' ')),'                              ') || NVL((LPAD (std.PERSON_STATE, 2, ' ')),'  ') || NVL((LPAD (std.PERSON_ZIP, 5, ' ')),'     ') || NVL((LPAD (std.PERSON_PLUS4, 4, ' ')),'    ') ||                              '         '  ||                                                                                                                                                                                                                              '                                                              ' ||                                                                                                                                                                                                                   '                              '  ||                                                                                                                                                                                                                      '  '  ||                                                                                                                                                                                                              '     ' ||                                                                                                                                                                                                                      '    ' ||                                                                                                                    '                                                              '  ||                                                                                                              '                              '  ||                                                                                                               '  '  ||                                                                                                           '     '  ||                                                                                                               '    '  ||                             '         ' ||                              '       ' ||                                           '                    ' || NVL((LPAD (std.ETHNICITY, 2, ' ')),'  ') || NVL((LPAD (std.GENDER,         1, ' ')),' ') ||                                         '                    '  || NVL((LPAD (std.DOCUMENT_ID, 20, ' ')),'                    ') || 'CCD       ' AS output FROM std_ccd_doc std                               where std.current_process_Status =  5 ");
        
		rawTableNames.put("HIV",             "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("VL",              "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("SCREEN",          "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("DIFFERENTIATING", "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("WB",              "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("IFA",             "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("CD4",             "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("RESISTANCE",      "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("P24",             "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("CULTURE",         "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("HIV_LAB",         "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("ECLRS",           "RAW_HIV_ECLRS_DOC");
		rawTableNames.put("STD",             "RAW_STD_ECLRS_DOC");
		rawTableNames.put("STD_LAB",         "RAW_STD_ECLRS_DOC");
		rawTableNames.put("TB",              "RAW_TB_DOC");
		rawTableNames.put("SYPH",            "RAW_SYPHILIS_DOC");
		rawTableNames.put("DTH",             "RAW_DEATH_DOC");
		rawTableNames.put("CASA",            "RAW_CASA_DOC");
		rawTableNames.put("DOCCS",           "RAW_DOCCS_DOC");
		rawTableNames.put("DIN_DOCCS",       "RAW_DIN_DOCCS_DOC");
		rawTableNames.put("PRF",             "RAW_PRF_DOC");
		rawTableNames.put("NYC_PRF",         "RAW_NYCPRF_DOC");
		rawTableNames.put("ACRF",            "RAW_ACRF_DOC");
		rawTableNames.put("ACCURINT_INDIVIDUAL",            "RAW_ACRF_DOC");
		rawTableNames.put("MISSING_DATA",            "RAW_ACRF_DOC");
		rawTableNames.put("RIDR_ACRF",            "RAW_ACRF_DOC");
		rawTableNames.put("MMP",            "RAW_ACRF_DOC");
		rawTableNames.put("RECORD_SEARCH",            "RAW_ACRF_DOC");
		rawTableNames.put("LN_BATCH",            "RAW_ACRF_DOC");
		rawTableNames.put("OOS_TESTING",            "RAW_ACRF_DOC");
		rawTableNames.put("PCRF",            "RAW_PCRF_DOC");
		rawTableNames.put("AIRS",            "RAW_AIRS_DOC");
		rawTableNames.put("ADAP",            "RAW_ADAP_DOC");
		rawTableNames.put("SSDM",            "RAW_SSDM_DOC");
		rawTableNames.put("NDI",             "RAW_NDI_DOC");
		rawTableNames.put("NYC_IJR",         "RAW_NYCIJR_DOC");
		rawTableNames.put("NYC_DTH",         "RAW_NYC_DEATH_DOC");
		rawTableNames.put("MED",             "RAW_MEDICAID_DOC");
		rawTableNames.put("INS",             "RAW_MEDICAID_DOC");
		rawTableNames.put("OLD_CASES",       "RAW_OLD_CASES_DOC");
		rawTableNames.put("MED_OLD",         "RAW_MED_OLD_DOC");
		rawTableNames.put("INS_OLD",         "RAW_MED_OLD_DOC");
		rawTableNames.put("EPRF_OOC",        "RAW_EPRF_OOC_UPLOAD");
		rawTableNames.put("PROVIDER_SNP",       "RAW_EPRF_OOC_UPLOAD");
		rawTableNames.put("PROVIDER_DNLTC",       "RAW_EPRF_OOC_UPLOAD");
		rawTableNames.put("PROVIDER_OOC",       "RAW_EPRF_OOC_UPLOAD");
		rawTableNames.put("CCD",             "RAW_CCD_DOC");
		rawTableNames.put("HL7",             "RAW_HL7_DOC");
		rawTableNames.put("HIE_DEATH",       "RAW_HIE_DEATH_DOC");
		rawTableNames.put("HIE_DTH",       "RAW_HIE_DEATH_DOC");
		rawTableNames.put("NYC_DEMO",       "RAW_NYC_EHARS_DOC");
		rawTableNames.put("NYC_LAB",       "RAW_NYC_EHARS_LAB_DOC");
		rawTableNames.put("NDI_NEW",       "RAW_NDI_CAUSE_DOC");
		rawTableNames.put("NDI_CAUSE",       "RAW_NDI_CAUSE_DOC");
		rawTableNames.put("INS_VITAL",       "RAW_INS_DEATH_DOC");
		rawTableNames.put("STD_OLD",       "RAW_STD_OLD_DOC");
		rawTableNames.put("CLUSTER", 	"RAW_CLUSTER_DOC");
		rawTableNames.put("STD_CONF", 	"RAW_STD_CONFIRMED_DOC");
		rawTableNames.put("BLACK_BOX", 	"RAW_BLACK_BOX_DOC");
		rawTableNames.put("ICCR",         "RAW_ICCR_DOC");
		rawTableNames.put("QUAL_CARE",         "RAW_QUALITY_CARE_DOC");
    } // End of the static initializer
    
     /**
     * For NYC Death Documents in years 1999 to 2002 the raw file had 
     * a single RACE column (D23).  This must be used to populate 6
     * of the 8 race columns in STD_NYC_DEATH_DOC with zero or one,
     * leaving the other two (RACE_MULTIPLE and RACE_OTHER) zero.
     * @param race The contents of RAW_NYC_DEATH_DOC.RACE for rows with
     * a value of DEATH_FILE in nyc_dth99, nyc_dth00, nyc_dth01, nycdth02.
     * @return One of: RACE_WHITE, RACE_BLACK, RACE_INDIAN, RACE_ASIAN,
     * RACE_PACIFIC, or RACE_UNKNOWN.
     */
    public static String getOldNycRaceColumn (String race) {
    	String raceColumnToSetToOne = oldNycRaceColumnMap.get(race);
    	if (raceColumnToSetToOne == null) {raceColumnToSetToOne = "RACE_UNKNOWN";};
    	return raceColumnToSetToOne;
    }
    
   
    
	/**
	 * This method is used to concatenate the conditions
	 * 
	 *  the output will be like below: 
	 *  
	 *  FIRST NAME like '%FIRST%' and FACILITY_NAME = 'TEST FACILITY'
	 *  
	 *  this will return the only the individual set 
	 *  
	 *  
	 * @param jsonArray list of column names and matching values
	 * @return
	 */
	public static String getQueryCondition(List<JSONObject> jsonArray){

		StringBuilder queryString =  new StringBuilder();
        for (JSONObject jsonElement : jsonArray) {
        	String queryCondition = getFilterCondition(jsonElement);
        	String fieldName = getFieldName(jsonElement.get("FIELD_NAME").toString());
        	if(queryCondition != "" && fieldName != ""){
        		queryString.append(LEFT_BRACKET).append("UPPER(").append(fieldName).append(")").append(SPACE).append(queryCondition).append(RIGHT_BRACKET);
        		if(jsonElement.get("OPERATOR") != null){
            		queryString.append(SPACE).append(jsonElement.get("OPERATOR"));
            	}
        	}
        }
        return queryString.toString();
	}
    
    private static  String getFilterCondition(JSONObject jsonElement){
    	
    	String condition = jsonElement.get("CONDITION").toString();
    	
    	String value = null;
    	
    	if(jsonElement.get("VALUE") != null){
    		value =  jsonElement.get("VALUE").toString().toUpperCase();
    	}
    	
    	StringBuffer _operatorCondition = new StringBuffer("");
  	if("LIKE".equalsIgnoreCase(condition)){
				_operatorCondition.append("LIKE").append(SPACE).append(QUOTE).append(PERCENTAGE).append(value).append(PERCENTAGE).append(QUOTE);
    	}else if("STARTWITH".equals(condition)){
				_operatorCondition.append("LIKE").append(SPACE).append(QUOTE).append(value).append(PERCENTAGE).append(QUOTE);
    	}else if("EQUAL".equals(condition)){
    			if("".equals(value) || value == null ){
    				_operatorCondition.append(" is null ");
    			}else{
    				_operatorCondition.append(EQUALS).append(SPACE).append(QUOTE).append(value).append(QUOTE);
    			}
    	}else if("NOTEQUAL".equals(condition)){
    		if("".equals(value) || value == null ){
    			_operatorCondition.append("is not null ");
    		}else{
				_operatorCondition.append(NOT_EQUALS).append(SPACE).append(QUOTE).append(value).append(QUOTE);
    		}
    	}else if("NOTSTARTWITH".equals(condition)){
				_operatorCondition.append(NOT_LIKE).append(SPACE).append(QUOTE).append(value).append(PERCENTAGE).append(QUOTE);
    	}else if("NOTLIKE".equals(condition)){
				_operatorCondition.append(NOT_LIKE).append(SPACE).append(QUOTE).append(PERCENTAGE).append(value).append(PERCENTAGE).append(QUOTE);
    	}
    return _operatorCondition.toString();
    }
    
    private static  String getFieldName(String fieldName){
         
    	if("FIRST NAME".equals(fieldName)){
    		return "PIDFIRSTNAME";
    	}else if("LAST NAME".equals(fieldName)){
    		return "PIDLASTNAME";
    	}else if("ACCESSION NUM".equals(fieldName)){
    		return "OBRACCESSIONNUM";
    	}else if("OBSERVATION RESULT TEXT".equals(fieldName)){
    		return "TO_CHAR(OBXOBSERVATIONRESULTTEXT)";
    	}else if("FACILITY NAME".equals(fieldName)){
    		return "ZLRFACILITYNAME";
    	}else if("PROVIDER FIRST NAME".equals(fieldName)){
    		return "OBRPROVIDERFIRSTNAME";
    	}else if("MIDDLE NAME".equals(fieldName)){
    		return "PIDMIDDLENAME";
    	}else if("INTERNAL ID".equals(fieldName)){
    		return "PIDPATIENTIDINTERNAL";
    	}else if("PROVIDER LAST NAME".equals(fieldName)){
    		return "OBRPROVIDERLASTNAME";
    	}else if("DOB".equals(fieldName)){
    		return  "to_char(PIDDATEOFBIRTH, 'MM/DD/YYYY')";
    	}else if("FACILITY ADDRESS LINE1".equals(fieldName)){
    		return "ZLRFACILITYADDRESSLINE1";
    	}else if("FACILITY ADDRESS LINE2".equals(fieldName)){
    		return "ZLRFACILITYADDRESSLINE2";
    	}else if("PROVIDER ADDRESS LINE1".equals(fieldName)){
    		return "ZLRPROVIDERADDRESSLINE1";
    	}else if("PROVIDER ADDRESS LINE2".equals(fieldName)){
    		return "ZLRPROVIDERADDRESSLINE2";
    	}else if("FACILITY CITY".equals(fieldName)){
    		return "ZLRFACILITYCITY";
    	}else if("PROVIDER CITY".equals(fieldName)){
    		return "ZLRPROVIDERCITY";
    	}else if("LOINC".equals(fieldName)){
    		return "OBXLOINCCODE";
    	}else if("PATIENT ADDRESS LINE1".equals(fieldName)){
    		return "PIDADDRESSLINE1";
    	}else if("PATIENT ADDRESS LINE2".equals(fieldName)){
    		return "PIDADDRESSLINE2";
    	}else if("PATIENT CITY".equals(fieldName)){
    		return "PIDCITY";
    	}else if("PATIENT STATE".equals(fieldName)){
    		return "PIDSTATE";
    	}
    	else{
    		return "";
    	}
    	
    }
    
    public static String getRawTableNameFast(String documentType) {
    	return rawTableNames.get(documentType);
    }
	
	public static String getRawTableName(String documentType){

		String tableName = null;
		
		if("HIV".equals(documentType)  || "VL".equals(documentType) || "SCREEN".equals(documentType) || "DIFFERENTIATING".equals(documentType) || "WB".equals(documentType) || "IFA".equals(documentType) || "CD4".equals(documentType) || "RESISTANCE".equals(documentType) || "P24".equals(documentType) || "CULTURE".equals(documentType) || "HIV_LAB".equals(documentType) || "ECLRS".equals(documentType)){
			tableName = "RAW_HIV_ECLRS_DOC"; 
		}else if("STD_LAB".equals(documentType) || "STD".equals(documentType)){
			tableName = "RAW_STD_ECLRS_DOC";
		}else if ("TB".equals(documentType)) {
			tableName = "RAW_TB_DOC";
		}else if ("SYPH".equals(documentType)) {
			tableName = "RAW_SYPHILIS_DOC";	
		} else if ("DTH".equals(documentType)) {
			tableName = "RAW_DEATH_DOC";
		} else if ("CASA".equals(documentType)) {
			tableName = "RAW_CASA_DOC";
		} else if ("DOCCS".equals(documentType)) {
			tableName = "RAW_DOCCS_DOC";
		} else if ("DIN_DOCCS".equals(documentType)) {
			tableName = "RAW_DIN_DOCCS_DOC";
		} else if ("PRF".equals(documentType)) {
			tableName = "RAW_PRF_DOC";
		} else if ("NYC_PRF".equals(documentType)) {
			tableName = "RAW_NYCPRF_DOC";
		} else if ("ACRF".equals(documentType) || "ACCURINT_INDIVIDUAL".equals(documentType) || "MISSING_DATA".equals(documentType) || "RIDR_ACRF".equals(documentType) || "MMP".equals(documentType)|| "RECORD_SEARCH".equals(documentType)|| "OOS_TESTING".equals(documentType) || "LN_BATCH".equals(documentType)) {
			tableName = "RAW_ACRF_DOC";
		} else if ("PCRF".equals(documentType)) {
			tableName = "RAW_PCRF_DOC";
		} else if ("AIRS".equals(documentType)) {
			tableName = "RAW_AIRS_DOC";
		} else if ("ADAP".equals(documentType)) {
			tableName = "RAW_ADAP_DOC";
		}else if("SSDM".equals(documentType)){
			tableName = "RAW_SSDM_DOC";
		} else if ("NDI".equals(documentType)) {
			tableName = "RAW_NDI_DOC";
		} else if ("NYC_IJR".equals(documentType)) {
			tableName = "RAW_NYCIJR_DOC";
		} else if ("NYC_DTH".equals(documentType)) {
			tableName = "RAW_NYC_DEATH_DOC";
		} else if ("MED".equals(documentType) || "INS".equals(documentType)) {
			tableName = "RAW_MEDICAID_DOC";
		}else if("OLD_CASES".equals(documentType)){
			tableName = "RAW_OLD_CASES_DOC";
		}else if("MED_OLD".equals(documentType) || "INS_OLD".equals(documentType)){
			tableName = "RAW_MED_OLD_DOC";
		}else if("EPRF_OOC".equals(documentType) || "PROVIDER_SNP".equals(documentType) | "PROVIDER_DNLTC".equals(documentType) || "PROVIDER_OOC".equals(documentType)){
			tableName = "RAW_EPRF_OOC_UPLOAD";
		}else if("CCD".equals(documentType)){
			tableName = "RAW_CCD_DOC";
		}else if("HL7".equals(documentType)){
			tableName = "RAW_HL7_DOC";
		}else if("EXP".equals(documentType)){
			tableName = "RAW_EXP_DOC";
		}else if("NYC_DEMO".equals(documentType)){
			tableName = "RAW_NYC_EHARS_DOC";
		}else if("NYC_LAB".equals(documentType)){
			tableName = "RAW_NYC_EHARS_LAB_DOC";
		}else if("NDI_NEW".equals(documentType) || "NDI_CAUSE".equals(documentType)){
			tableName = "RAW_NDI_CAUSE_DOC";
		}else if("INS_VITAL".equals(documentType)){
			tableName = "RAW_INS_DEATH_DOC";
		}else if("STD_OLD".equals(documentType)){
			tableName = "RAW_STD_OLD_DOC";
		}else if("HIE_DTH".equals(documentType)){
			tableName = "RAW_HIE_DEATH_DOC";
		}else if("CLUSTER".equals(documentType)){
			tableName = "RAW_CLUSTER_DOC";
		}else if("STD_CONF".equals(documentType)){
			tableName = "RAW_STD_CONFIRMED_DOC";
		}else if("BLACK_BOX".equals(documentType)){
			tableName = "RAW_BLACK_BOX_DOC";
		}else if ("ICCR".equals(documentType)) {
			tableName = "RAW_ICCR_DOC";
		}else if ("QUAL_CARE".equals(documentType)) {
			tableName = "RAW_QUALITY_CARE_DOC";
		}
		
		
		return tableName;
	}
	
	
	public static String getStdTableName(String documentType){
		String tableName = null;
		
		if("HIV".equals(documentType)  || "VL".equals(documentType) || "SCREEN".equals(documentType) || "DIFFERENTIATING".equals(documentType) || "WB".equals(documentType) || "IFA".equals(documentType) || "CD4".equals(documentType) || "RESISTANCE".equals(documentType) || "P24".equals(documentType) || "CULTURE".equals(documentType) || "HIV_LAB".equals(documentType) || "ECLRS".equals(documentType)){
			tableName = "STD_HIV_ECLRS_DOC"; 
		}else if("STD_LAB".equals(documentType) || "STD".equals(documentType)){
			tableName = "STD_STD_ECLRS_DOC";
		} else if ("TB".equals(documentType)) {
			tableName = "STD_TB_DOC";
		} else if ("SYPH".equals(documentType)) {
			tableName = "STD_SYPHILIS_DOC";	
		} else if ("DTH".equals(documentType)) {
			tableName = "STD_DEATH_DOC";
		} else if ("CASA".equals(documentType)) {
			tableName = "STD_CASA_DOC";
		} else if ("DOCCS".equals(documentType)) {
			tableName = "STD_DOCCS_DOC";
		} else if ("DIN_DOCCS".equals(documentType)) {
			tableName = "STD_DIN_DOCCS_DOC";
		} else if ("PRF".equals(documentType)) {
			tableName = "STD_PRF_DOC";
		} else if ("NYC_PRF".equals(documentType)) {
			tableName = "STD_NYCPRF_DOC";
		} else if ("ACRF".equals(documentType) || "ACCURINT_INDIVIDUAL".equals(documentType) || "MISSING_DATA".equals(documentType) || "RIDR_ACRF".equals(documentType) || "MMP".equals(documentType)|| "RECORD_SEARCH".equals(documentType)|| "OOS_TESTING".equals(documentType) || "LN_BATCH".equals(documentType)) {
			tableName = "STD_ACRF_DOC";
		} else if ("AIRS".equals(documentType)) {
			tableName = "STD_AIRS_DOC";
		} else if ("ADAP".equals(documentType)) {
			tableName = "STD_ADAP_DOC";
		}else if("SSDM".equals(documentType)){
			tableName = "STD_SSDM_DOC";
		} else if ("NDI".equals(documentType)) {
			tableName = "STD_NDI_DOC";
		} else if ("NYC_IJR".equals(documentType)) {
			tableName = "STD_NYCIJR_DOC";
		} else if ("NYC_DTH".equals(documentType)) {
			tableName = "STD_NYC_DEATH_DOC";
		} else if ("MED".equals(documentType) || "INS".equals(documentType)) {
			tableName = "STD_MEDICAID_DOC";
		}else if("OLD_CASES".equals(documentType)){
			tableName = "STD_OLD_CASES_DOC";
		}else if("MED_OLD".equals(documentType) || "INS_OLD".equals(documentType)){
			tableName = "STD_MED_OLD_DOC";
		}else if("EPRF_OOC".equals(documentType) || "PROVIDER_SNP".equals(documentType) || "PROVIDER_DNLTC".equals(documentType) || "PROVIDER_OOC".equals(documentType)){
			tableName = "STD_EPRF_OOC_UPLOAD";
		}else if ("CCD".equals(documentType)) {
			tableName = "STD_CCD_DOC";
		}else if ("HL7".equals(documentType)) {
			tableName = "STD_HL7_DOC";
		}else if ("EXP".equals(documentType)) {
			tableName = "STD_EXP_DOC";
		}else if("NYC_DEMO".equals(documentType)){
			tableName = "STD_NYC_EHARS_DOC";
		}else if("NYC_LAB".equals(documentType)){
			tableName = "STD_NYC_EHARS_LAB_DOC";
		}else if("NDI_NEW".equals(documentType)  || "NDI_CAUSE".equals(documentType) ){
			tableName = "STD_NDI_CAUSE_DOC";
		}else if("INS_VITAL".equals(documentType)){
			tableName = "STD_INS_DEATH_DOC";
		}else if("STD_OLD".equals(documentType)){
			tableName = "STD_STD_OLD_DOC";
		}else if("HIE_DTH".equals(documentType)){
			tableName = "STD_HIE_DEATH_DOC";
		}else if("AHI".equals(documentType)){
			tableName = "STD_AHI_DOC";
		}else if("CLUSTER".equals(documentType)){
			tableName = "STD_CLUSTER_DOC";			
		}else if("CLUST_PART".equals(documentType)){
			tableName = "STD_CLUSTER_PARTNERS";			
		}else if("STD_CONF".equals(documentType)){
			tableName = "STD_STD_CONFIRMED_DOC";
		}else if("PCRF".equals(documentType)){
			tableName = "STD_PCRF_DOC";
		}else if("BLACK_BOX".equals(documentType)){
			tableName = "STD_BLACK_BOX_DOC";
		}else if ("ICCR".equals(documentType)) {
			tableName = "STD_ICCR_DOC";
		}else if ("QUAL_CARE".equals(documentType)) {
			tableName = "STD_QUALITY_CARE_DOC";
		}else if ("TRK_ALIAS".equals(documentType)) {
			tableName = "TRACKING_USER_ENTERED_ALIAS";
		}
		
		return tableName;
	}
	
	
	public static String checkRiskData(String _inputValue){
		if(CommonUtil.isNotNullAndEmpty(_inputValue)){
			_inputValue = _inputValue.trim().toUpperCase();
			return codedRiskDataValues.get(_inputValue);
		}else{
			return _inputValue;
		}
	}
	

	public static String checkTestDataValues(String _inputValue){
		if(CommonUtil.isNotNullAndEmpty(_inputValue)){
			_inputValue = _inputValue.trim().toUpperCase();
			return codedTestDataValues.get(_inputValue);
		}else{
			return _inputValue;
		}
	}
	
	
	
	
	public static String checkRaceData(String _inputValue){
		if(CommonUtil.isNotNullAndEmpty(_inputValue)){
			_inputValue = _inputValue.trim().toUpperCase();
			
			String _codedRaceValue = codedRaceDataValues.get(_inputValue);
			
			if(CommonUtil.isNullOrEmpty(_codedRaceValue)){
				return "0";
			}else{
				return _codedRaceValue;
			}
		}else{
			return "0";
		}
	}
	
	public static String checkEthnicityData(String _inputValue){
		if(CommonUtil.isNotNullAndEmpty(_inputValue)){
			_inputValue = _inputValue.trim().toUpperCase();
			return codedEthinicityValues.get(_inputValue);
		}else{
			return null;
		}
	}
	
	public static String checkBirthSexData(String _inputValue){
		if(CommonUtil.isNotNullAndEmpty(_inputValue)){
			_inputValue = _inputValue.trim().toUpperCase();
			return codedBirthSexValues.get(_inputValue);
		}else{
			return null;
		}
	}
	
	public static String checkCurrentGenderData(String _inputValue){
		if(CommonUtil.isNotNullAndEmpty(_inputValue)){
			_inputValue = _inputValue.trim().toUpperCase();
				return codedCurrentGenderValues.get(_inputValue);
		}else{
			return null;
		}
	}

	public static Map<String, String> getStdReferenceColumnNames(
			String documentType) {
		
		Map<String, String> docTableReferenceColumnName = null;
		
		if("HIV".equals(documentType)  || "VL".equals(documentType) || "SCREEN".equals(documentType) || "DIFFERENTIATING".equals(documentType) || "WB".equals(documentType) || "IFA".equals(documentType) || "CD4".equals(documentType) || "RESISTANCE".equals(documentType) || "P24".equals(documentType) || "CULTURE".equals(documentType) || "HIV_LAB".equals(documentType) || "ECLRS".equals(documentType)){
			return stdHivColumnReferances; 
		}else if("STD_LAB".equals(documentType) || "STD".equals(documentType)){
			return stdStdColumnReferances;
		}else if("DTH".equals(documentType)){
			return stdDeathColumnReferances;
		}else if("NYC_DTH".equals(documentType)){
			return stdNycDeathColumnReferances;
		}else if("ADAP".equals(documentType)){
			return stdAdapColumnReferances;
		}else if("AIRS".equals(documentType)){
			return stdAirsColumnReferances;
		}else if("MED".equals(documentType) || "INS".equals(documentType)){
			return stdMedColumnReferances;
		}else if ("TB".equals(documentType)) {
			return stdTbColumnReferances;
		}else if ("SYPH".equals(documentType)) {
			return stdSyphColumnReferances;	
		} else if ("PRF".equals(documentType)) {
			return stdPrfColumnReferances;
		} else if ("NYC_PRF".equals(documentType)) {
			return stdNycPrfColumnReferances;
		} else if("SSDM".equals(documentType)){
			return stdSsdmColumnReferances;
		} else if("OLD_CASES".equals(documentType)){
			return stdOldCaseColumnReferances;
		}else if("MED_OLD".equals(documentType) || "INS_OLD".equals(documentType)){
			return stdMedOldColumnReferances;
		} else if("NYC_IJR".equals(documentType)){
			return stdNycIJRColumnReferances;
		}else if("EPRF_OOC".equals(documentType)){
			return stdEprfOOCColumnReferances;
		}else if("DOCCS".equals(documentType)){
			return stdDoccsColumnReferances;
		}else if("STD_OLD".equals(documentType)){
			return stdOldColumnReferances;
		}else if("STD_CONF".equals(documentType)){
			return stdStdConfColumnReferances;
		} else if ("ICCR".equals(documentType)) {
			return stdICCRColumnReferances;
		}
		
		return docTableReferenceColumnName;
	}
	
	public static Map<String, String> getRawReferenceColumnNames(
			String documentType) {
		
		Map<String, String> docTableReferenceColumnName = null;
		
		if("HIV".equals(documentType)  || "VL".equals(documentType) || "SCREEN".equals(documentType) || "DIFFERENTIATING".equals(documentType) || "WB".equals(documentType) || "IFA".equals(documentType) || "CD4".equals(documentType) || "RESISTANCE".equals(documentType) || "P24".equals(documentType) || "CULTURE".equals(documentType) || "HIV_LAB".equals(documentType) || "ECLRS".equals(documentType)){
			return rawHivColumnReferances; 
		}else if("STD_LAB".equals(documentType) || "STD".equals(documentType)){
			return rawStdColumnReferances;
		}else if("DTH".equals(documentType)){
			return rawDeathColumnReferances;
		}else if("NYC_DTH".equals(documentType)){
			return rawNycDeathColumnReferances;
		}else if("ADAP".equals(documentType)){
			return rawAdapColumnReferances;
		}else if("AIRS".equals(documentType)){
			return rawAirsColumnReferances;
		}else if("MED".equals(documentType) || "INS".equals(documentType)){
			return rawMedColumnReferances;
		}else if("PRF".equals(documentType)){
			return rawPrfColumnReferances;
		}else if("NYC_PRF".equals(documentType)){
			return rawNycPrfColumnReferances;
		}else if("CASA".equals(documentType)){
			return rawCASAColumnReferances;
		}else if("EPRF_OOC".equals(documentType)){
			return rawEprfOOCColumnReferances;
		}else if("NYC_IJR".equals(documentType)){
			return rawNycIJRColumnReferances;
		}else if("TB".equals(documentType)){
			return rawTbColumnReferances;
		}else if("STD_CONF".equals(documentType)){
			return rawStdConfColumnReferances;
		}else if("SYPH".equals(documentType)){
			return rawSyphColumnReferances;
		}else if("ICCR".equals(documentType)){
			return rawICCRColumnReferances;
		}else if("QUAL_CARE".equals(documentType)){
			return rawQualCareColumnReferances;
		}
		
		return docTableReferenceColumnName;
	}

	public static String getDocumentType(String documentType) {
		if("HIV".equals(documentType)  || "VL".equals(documentType) || "SCREEN".equals(documentType) || "DIFFERENTIATING".equals(documentType) || "WB".equals(documentType) || "IFA".equals(documentType) || "CD4".equals(documentType) || "RESISTANCE".equals(documentType) || "P24".equals(documentType) || "CULTURE".equals(documentType) || "HIV_LAB".equals(documentType) || "ECLRS".equals(documentType)){
			return "HIV_LAB"; 
		}else if("STD_LAB".equals(documentType) || "STD".equals(documentType)){
			return "STD_LAB";
		}else if("PROVIDER_OOC".equals(documentType) || "PROVIDER_SNP".equals(documentType) || "PROVIDER_DNLTC".equals(documentType)){
			return "EPRF_OOC";
		}else if("MMP".equals(documentType) || "RECORD_SEARCH".equals(documentType) || "ACCURINT_INDIVIDUAL".equals(documentType) || "MISSING_DATA".equals(documentType) || "OOS_TESTING".equals(documentType) || "RIDR_ACRF".equals(documentType) || "LN_BATCH".equals(documentType)){
			return "ACRF";
		}else{
			return documentType;
		}
		
	}

	/**
	 * Returns the correct query for one of three cases: SSDM alone,
	 * "Live" document types, "Death" document types
	 * <p>As of 12/29/2016 the document types are as follows:
	 * <p>Live Document Types
	 * <ul>
	 * <li>NYCIJR</li>
	 * <li>STD_LAB (ECLRS)</li>
	 * <li>HIV_LAB (ECLRS)</li>
	 * <li>PRF</li>
	 * <li>MEDICAID</li>
	 * <li>NYCPRF</li>
	 * <li>EPRF_OOC</li>
	 * </ul>
	 * <p>Death Document Types
	 * <ul>
	 * <li>ADAP</li>
	 * <li>DTH</li>
	 * <li>NYC_DTH</li>
	 * <li>AIRS</li>
	 * <li>CASA</li>
	 * <li>DIN_DOCCS</li>
	 * </ul>
	 * 
	 * @param isDeathDocType [only if "isSSDM" != "Y"] 
	 * "Y" or something else; if "Y" then the query for Death document types
	 * is returned; if something else then the query for Live document types is
	 * returned
	 * @param isSSDM "Y" or something else; if "Y" return
	 * the query for SSDM document type; if something else then the
	 * "isDeathDocType" argument will be considered.
	 * @return The appropriate query for rows to write to EXPORT01 as 
	 * determined by the parameter logic described above.
	 */
	public static String getQueryToLoadStdDataForQsProcess(
			String isDeathDocType,
			String isSSDM){
		if("Y".equals(isSSDM)){
			return ssdmStandardDocumentsQuery.toString();
		}else if("Y".equals(isDeathDocType)){
			return deathStandardDocumentsQuery.toString();
		}else{
			return liveStandardDocumentsQuery.toString();
		}
	}
	
	
	
	
	public static List<String> getCCDDocList(String tableName){
		
		if("RAW_CCD_DOC".equals(tableName))
		{
			return RAW_CCD_DOC_LIST;
		}
		else if("RAW_CCD_PERSON_ADDRESS".equals(tableName))
		{
			return RAW_CCD_PERSON_ADDRESS_LIST;
		}
		else if("UPHN_TRANX_IN".equals(tableName))
		{
			return UPHN_TRANX_IN_LIST;
		}
		else if("RAW_CCD_PROVIDER".equals(tableName))
		{
			return RAW_CCD_PROVIDER_LIST;
		}
		else if("RAW_CCD_PAYER".equals(tableName))
		{
			return RAW_CCD_PAYER_LIST;
		}
		else if("RAW_CCD_LAB_TEST".equals(tableName))
		{
			return RAW_CCD_LAB_TEST_LIST;
		}
		else if("RAW_CCD_ENCOUNTER".equals(tableName))
		{
			return RAW_CCD_ENCOUNTER_LIST;
		}
		else if("RAW_CCD_MED".equals(tableName))
		{
			return RAW_CCD_MED_LIST;
		}
		else if("RAW_CCD_DIAGNOSIS".equals(tableName))
		{
			return RAW_CCD_DIAGNOSIS_LIST;
		}
		else if("RAW_CCD_LAB_RESULT".equals(tableName))
		{
			return RAW_CCD_LAB_RESULT_LIST;
		}
		else if("RAW_CCD_PROBLEM".equals(tableName))
		{
			return RAW_CCD_PROBLEM_LIST;
		}
		else
		{
			return null;
		}
	}
	
	
	public static java.util.Date getDerivedCollectionDate(java.util.Date COLLECTIONDATE, java.util.Date OBSERVATION_DATE, java.util.Date RESULT_REPORT_DATE, java.util.Date DATE_RECEIVED){
		
		//If still collection_Date is null then take the Observation date
		if(COLLECTIONDATE == null){
			COLLECTIONDATE = OBSERVATION_DATE;
		}
		
		//If the collection_date is still null need to take the Result_report_date in hierarchy
		if(COLLECTIONDATE == null){
			
			COLLECTIONDATE = RESULT_REPORT_DATE;
		}
		
		//If still collection_Date is still null then take the Recieved date
		if(COLLECTIONDATE == null){
			COLLECTIONDATE = DATE_RECEIVED;
		}
		
		
		return COLLECTIONDATE;
	}
	
	
	public static Map<String, String> convertColumnListMapObj(List<Map<String, String>> stdToRawColumnsList, String keyColumn, String valueColumn){
		
		Map<String, String> documentColumnMappingObj = new HashMap<String, String>();
		
		for (Map<String, String> map : stdToRawColumnsList) {
			 
			documentColumnMappingObj.put(map.get(keyColumn), map.get(valueColumn));
			
		}
		
		return documentColumnMappingObj;
	}
	
	public static void updateCodedRiskValue(String parameterName, String parameterValue) {
		codedRiskDataValues.put(parameterName, parameterValue);
	}

}

