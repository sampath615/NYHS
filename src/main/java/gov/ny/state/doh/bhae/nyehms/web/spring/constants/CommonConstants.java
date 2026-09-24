package gov.ny.state.doh.bhae.nyehms.web.spring.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Created the file for common Constant variables to access across the NYEHMS application
 * 
 * @author ska03
 *
 */
public class CommonConstants {
	
	
	/**
	 * Common failure exception message
	 */
	public static final String TRANSACTION_FAILED_MESSAGE = "Exception occured, please contact helpline."; 
	/**
	 * Subject for Scheduler job status 
	 */
	
	
	/**
	 * Subject for Scheduler job status 
	 */
	
	
	/**
	 * Subject for Scheduler job status 
	 */
	
	
	/**
	 * Subject for doing Scheduler job manually 
	 */
	/**
	 * Response Content Type : plain text 
	 */
	public static final String RESPONSE_CONTENT_TYPE_PLAIN = "text/plain";
	
	/**
	 * Response Content Type : html 
	 */
	public static final String RESPONSE_CONTENT_TYPE_HTML = "text/html";
	/**
	 * Permission restricted page 
	 */
	public static final String PERMISSIONS_NOT_AVAILABLE_FOR_PAGE = "You don't have permission to access this page.";
	/**
	 * Trigger Scheduler job 
	 */
	public static final String TRIGGER_SCHEDULER = "trigger scheduler job";
	/**
	 * pause Scheduler job 
	 */
	public static final String TRIGGER_PAUSE = "pause scheduler job";
	/**
	 * Resume Scheduler Job 
	 */
	public static final String TRIGGER_RESUME = "resume scheduler job";
	
	
	
	
	public static final int RAW_NEW_RECORD_PROCESS_STATUS = 1;
	
	public static final int RAW_DUPLICATION_RECORD_PROCESS_STATUS = 2;
	
	public static final int RAW_UNIQUE_RECORD_PROCESS_STATUS = 3;
	
	public static final int RAW_RECORD_VALIDATION_ERRORS_PROCESS_STATUS = 4;
	
	public static final int RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS = 5;
	
	public static final int RAW_RECORD_REPRISE_PROCESS_STATUS = 6;
	
	public static final int RAW_RECORD_SOFT_REPRISE_PROCESS_STATUS = 15;
	
	public static final int RAW_RECORD_CLIA_PFI_ERROR_PROCESS_STATUS = 7;
	
	public static final int RAW_RECORD_PROFICIENCY_ERROR_PROCESS_STATUS = 8;
	
	public static final int RAW_RECORD_DELETED_PROCESS_STATUS = 33;
	
	public static final int DOCUMENT_DELETED_PROCESS_STATUS = 9;
	
	
	public static final int RAW_RECORD_MULTI_RECORDS_PROCESS_STATUS = 11;
	
	
	public static final int STOPPED_AT_RAW_LAYER_PROCESS_STATUS = 18;
	
	public static final int STOPPED_AT_RAW_NOT_HAVING_SUFFICIENT_INFO = 43;
	
	
	public static final int RAW_RECORD_MULTISTEP_DELETED_PROCESS_STATUS = 19;
	
	public static final int DOCUMENT_EDITED_PROCESS_STATUS = 20;
	
	public static final int RAW_DOCUMENT_EDITED_PROCESS_STATUS = 44;
	
	public static final int RAW_STD_BY_PASS_DONOR_VALIDATION = 21;
	
	public static final int AIRS_AGENCY_NOT_AVAILABE = 28;
	
	public static final int AIRS_AGENCY_NOT_IN_REFERNCE_TABLE = 25;
	
	public static final int AIRS_LNAME_NOT_AVAILABE = 26;
	
	public static final int AIRS_TC_ID_NOT_AVAILABLE = 27;	
	
	public static final int AIRS_DATE_CREATED_NOT_AVAILABLE = 29;
	
	public static final int RAW_HIV_MULTISTEP_ERROR_DOCUMENT = 30;
	
	public static final int RAW_HIV_REFERRAL_LAB_OOS_STATUS = 46;
	
	public static final int AIRS_URN_NOT_AVAILABLE = 32;
	
	public static final String DEFAULT_PFI = "-999";
	
	
	public static final String TEST_TYPE_RESISTANCE = "RESISTANCE";
	
	public static final String TEST_TYPE_CD4 = "CD4";
	
	public static final String TEST_TYPE_VL = "VL";
	
	
	
	
	
	
	
	public static final String STD_NO_RESULTS = "NO RESULT";
	
	public static final String STD_RESULTS_POSITIVE = "POS";
	
	public static final String STD_RESULTS_REACTIVE = "REACTIVE";
	
	public static final String STD_RESULTS_NEGATIVE = "NEG";
	
	
	
	
	public static final String STD_RESULTS_INDETERMINATE = "IND";
	
	public static final String STD_RESULTS_INTERPRETATION_LESSTHAN = "<";
	
	public static final String STD_RESULTS_INTERPRETATION_EQUAL = "=";
	
	public static final String STD_RESULTS_INTERPRETATION_GREATER = ">";
	
	
	
	
	public static final int QS_DUPLICATED_DATA_FIELDS_COUNT = 4;
	
	
	public static final int QS_PROVIDER_MAPPING_RECORD_LENGTH = 850;
	
	public static final int QS_FACILITY_MAPPING_RECORD_LENGTH = 397;
	
	public static final int QS_MATCH_DATA_DOCUMENTID_POSITION = 0;
	
	public static final int QS_MATCH_DATA_STATENO_POSITION = 2;
	
	public static final int QS_MATCH_DATA_PERSON_ADDRESS_POSITION = 15;
	
	public static final int QS_MATCH_DATA_PERSON_CITY_POSITION = 16;
	
	public static final int QS_MATCH_DATA_PERSON_STATE_POSITION = 17;
	
	public static final int QS_MATCH_DATA_PERSON_ZIP_POSITION = 18;
	
	public static final int QS_MATCH_DATA_PERSON_PLUS4_POSITION = 19;
	
	public static final int QS_MATCH_DATA_PERSON_COUNTY_POSITION = 20;
	
	public static final int QS_MATCH_DATA_PERSON_CENCUS_TRACT_POSITION = 21;
	
	public static final int QS_MATCH_DATA_PERSON_LATITUDE_POSITION = 22;
	
	public static final int QS_MATCH_DATA_PERSON_LONGITUDE_POSITION = 23;
	
	public static final int QS_MATCH_DATA_PROVIDER_ADDRESS_POSITION = 24;
	
	public static final int QS_MATCH_DATA_PROVIDER_CITY_POSITION = 25;
	
	public static final int QS_MATCH_DATA_PROVIDER_STATE_POSITION = 26;
	
	public static final int QS_MATCH_DATA_PROVIDER_ZIP_POSITION = 27;
	
	public static final int QS_MATCH_DATA_PROVIDER_PLUS4_POSITION = 28;
	
	public static final int QS_MATCH_DATA_PROVIDER_COUNTY_POSITION = 29;
	
	public static final int QS_MATCH_DATA_PROVIDER_CENCUS_TRACT_POSITION = 30;
	
	public static final int QS_MATCH_DATA_PROVIDER_LATITUDE_POSITION = 31;
	
	public static final int QS_MATCH_DATA_PROVIDER_LONGITUDE_POSITION = 32;
	
	public static final int QS_MATCH_DATA_FACILITY_ADDRESS_POSITION = 33;
	
	public static final int QS_MATCH_DATA_FACILITY_CITY_POSITION = 34;
	
	public static final int QS_MATCH_DATA_FACILITY_STATE_POSITION = 35;
	
	public static final int QS_MATCH_DATA_FACILITY_ZIP_POSITION = 36;
	
	public static final int QS_MATCH_DATA_FACILITY_PLUS4_POSITION = 37;
	
	public static final int QS_MATCH_DATA_FACILITY_COUNTY_POSITION = 38;
	
	public static final int QS_MATCH_DATA_FACILITY_CENCUS_TRACT_POSITION = 39;
	
	public static final int QS_MATCH_DATA_FACILITY_LATITUDE_POSITION = 40;
	
	public static final int QS_MATCH_DATA_FACILITY_LONGITUDE_POSITION = 41;
	
	public static final int QS_MATCH_DATA_MATCH_WEIGHT_POSITION = 43;
	
	public static final int QS_MATCH_DATA_MATCH_PASS_POSITION = 44;
	
	public static final int QS_MATCH_DATA_FLAG_MATCH_CD_POSITION = 45;
	
	public static final int QS_MATCH_DATA_DOCUMENT_TYPE_POSITION = 42;
	
	public static final String STATUS_ACTION_NAME = "Action Name";
	
	public static final String TRANSACTION_STATUS = "Status";
	
	public static final String TRANSACTION_STATUS_SUCCESS = "SUCCESS";
	
	public static final String TRANSACTION_STATUS_FAILURE = "FAILURE";
	
	public static final String NO_DATA_RETRIEVED = "NO DATA RETRIEVED";
	
	public static final String TRANSACTION_STATUS_WAITING = "WAITING";
	
	public static final String TRANSACTION_MESSAGE = "Transaction Message";
	
	public static final String COMMENTS = "Comments";
	
	public static final String FAILED_DOCUMENTS = "Failed Documents";
	
	public static final String OBXOBSERVATIONRESULTKEY = "OBXOBSERVATIONRESULTKEY";
	
	
	public static final String NEW_HIV_ECLRS_DATA = "HIV ECLRS Data";
	
	
	
	public static final String NEW_STD_ECLRS_DATA = "STD ECLRS Data";
	
	public static final String REASSIGN_ECLRS_DATA = "reassigned ECLRS Data";
	
	public static final String RAW_REPRISE_DATA = "Failed and Reprised Raw Data";
	
	public static final String TOTAL_RECORDS = "Total Records";
	public static final String LOAD_WEEKLY_STATUS_JOB = "LOAD_WEEKLY_STATUS_JOB";
	public static final String LOAD_MONTHLY_LAB_REPORT_JOB = "LOAD_MONTHLY_LAB_REPORT_JOB";
	public static final String LOAD_WEEKLY_LAB_REPORT_JOB = "LOAD_WEEKLY_LAB_REPORT_JOB";
	public static final String LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER = "LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER";
	public static final String LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_FOR_BOTH_JOBS = "LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_FOR_BOTH_JOBS";
	public static final String LOAD_QUALITY_STAGE_SCHEDULER = "LOAD_QUALITY_STAGE_SCHEDULER";
	public static final String LOAD_HL7_QUALITY_STAGE_SCHEDULER = "LOAD_HL7_QUALITY_STAGE_SCHEDULER";
	public static final String LOAD_EHARS_DATA = "LOAD_EHARS_DATA";
	public static final String LOAD_PRF_REVIEW_STATUS_SCHEDULER ="LOAD_PRF_REVIEW_STATUS_SCHEDULER";
	public static final String LOAD_DOCUMENT_REMINDER_STATUS_SCHEDULER ="LOAD_DOCUMENT_REMINDER_STATUS_SCHEDULER";
	public static final String LOAD_NYEHMS_LOGIN_REMINDER_SCHEDULER ="LOAD_NYEHMS_LOGIN_REMINDER_SCHEDULER";
	public static final String LOAD_NYEHMS_TRAINING_REMINDER_SCHEDULER ="LOAD_NYEHMS_TRAINING_REMINDER_SCHEDULER";
	public static final String JAROT_IP = "JAROT_IP";
	public static final String JAROT_USERNAME = "JAROT_USERNAME";
	public static final String JAROT_PASSWORD = "JAROT_PASSWORD";
	
	public static final Set<String> NY_CITY_COUNTIES = new HashSet<String>(Arrays.asList(
		     new String[] {"061", "005", "047", "081", "085"}
		));
	
	
	public static final String FIRST_JOB = "job1";
	public static final String SECOND_JOB = "job2";
	
	public static final String CLOSED_STATUS = "CLOSED";
	public static final String CLS_ADM_STATUS = "CLS_ADM";
	
	public static final String REFERRAL_LAB_ERROR = "Referral lab";
	
	public static final String DETECTED = "DETECTED";
	public static final String HIV_RNA_DETECTED = "HIV-1 RNA DETECTED";
	
	public static final String NOT_DETECTED = "NOT DETECTED";

	public static final Object LOAD_MONTHLY_LAB_REPORT_BY_REGION_JOB = "LOAD_MONTHLY_LAB_REPORT_BY_REGION_JOB";
}
