package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.SchedulerNotificationVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.AIRSDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HITCommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.LabDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers.PreMatchHITSchedulerHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;
import org.springframework.stereotype.Component;

/**
 * Contains the methods that execute the PRE-MATCH document processing
 * <ul>
 * <li>Gather together all the new records for the day (plus weekend on Monday)
 * <li>Deduplicate, validate, otherwise prepare
 * <li>Send the person-identifiable data over to JAROT
 * <li>Initiate the Quality Stage run on JaroT
 * </ul>
 * 
 * @author ska03
 */
@RequiredArgsConstructor
@Component
public class RawRecordsCleaner {

	private static final Logger log = LoggerFactory.getLogger(RawRecordsCleaner.class);
	private final QuartzSchedulerTransactionHelper quartzSchedulerTransactionHelper;
	private final ManualQuartzJobService manualQuartzJobService;
	private final LabDocumentService labDocumentService;
	private final AIRSDocumentService airsDocumentService;
	private final PreMatchHITSchedulerHelper preMatchHITSchedulerHelper;

	private final HITCommonDocumentService hitCommonDocumentService;

	private final SchedulerNotificationVO schedulerNotificationVO;
	
//	@Autowired
//	DatafluxJobExce datafluxJobExce;

	/**
	 * Obtains new documents submitted to the various sources since the last run
	 * of the processing. See the document NYEHMS Data Flows for details. These
	 * are documents of the following types (not an exhaustive list):
	 * <ul>
	 * <li>ECLRS database HIV and STD lab test results
	 * <li>CCD, HIE_DTH and HL7 documents in UPHN_TRANX_IN.PAYLOADBINARYCONTENT
	 * <li>PRF: Provider reports of HIV diagnoses
	 * <li>AIRS
	 * <li>Medicaid submissions
	 * <li>ADAP
	 * <li>DTH
	 * <li>NYC_DTH
	 * <li>CASA
	 * </ol>
	 * This process loads the ECLRS documents into the RAW tables from a view on
	 * the ECLRS database, and also any new CCD, HIE_DTH or HL7 documents from
	 * UPHN_TRANX_IN.PAYLOADBINARYCONTENT to RAW_CCD_DOC and other RAW_CCD_XXX
	 * tables, or RAW_HIE_DEATH_DOC, or RAW_HL7_DOC; the other document types
	 * have already been loaded into the RAW tables by manual SQL Loader
	 * procedures or by manual entry via the NYEHMS web app. Then some of the
	 * RAW tables are checked for duplicate entries; one copy of each new record
	 * moves forward to validation; valid records are inserted into the
	 * appropriate STD_XXX_DOC table with a CURRENT_PROCESS_STATUS of 5; any
	 * STD_XXX_DOC rows that were matched before but still have a MATCH_CD of
	 * "C" (are still awaiting clerical review) have their status also set to 5
	 * so they can go through the QS match again and perhaps receive an "M" this
	 * time based on new information; thus avoiding the need for clerical
	 * review.
	 * <p>
	 * At the end of this process, most new documents remain viable. The
	 * person-identifying information with CURRENT_PROCESS_STATUS = 5 from all
	 * those documents is copied over the network to the JAROT machine, in the
	 * EXPORT01 file. Already on JAROT from an earlier step is a large
	 * "reference" file called ALLJARO containing the person-identifying
	 * information for all persons already in the NYEHMS database. The last step
	 * of this method is to perform a Secure Shell (SSH) remote connection to
	 * JARO and initiate the Quality Stage program that will try to match new
	 * persons to persons already in the database.
	 * <p>
	 * Finally, if indicated, it will send a detailed job status email.
	 * 
	 * @param RUN_SECOND_JOB_FOR_DEAD
	 *            : Defines the current process is Job1/Job2
	 */

	protected void cleanNyehmsRawRecords(String initiator, String RUN_SECOND_JOB_FOR_DEAD) {
		// the "synchronized" below prevents any hiccup in the scheduler from
		// starting a second instance of this method while the first one is
		// running.
		synchronized (this) {

			String runningServerIpAddress = null;
			try {
				runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
				log.error(runningServerIpAddress);
			} catch (UnknownHostException e) {
				log.error("Unable to get the Server Ip Address");
			}
			// To avoid local environment scheduler calls, for local environment
			// this property will be set it to "N"
			if ("N".equals(EnvironmentProperties.envProperties.getProperty("loadSchedulers"))) {
				log.error("You are running locally with a loadSchedulers property of N");
			} else {
				// schedulerStatusObject : is used to track the complete job
				// status (SUCCESS/FAILURE)
				List<JSONObject> schedulerStatusObject = new ArrayList<>();
				// This metadata is loaded from
				// LAB_SCHEDULER_PARAMETERS(Metadata table).
				// Most of the Scheduler input parameters will be inputed from
				// Metadata table
				Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
				/*
				 * Below two checks are for extra cautious (Checking the Last
				 * ran date and Server Ip address check) Daily Schedulers will
				 * be run only one time. if we want to load it again we need to
				 * change the respective date(LAST_ALL_JARO_SCHEDULER_RUN_DATE)
				 * to previous date.
				 */

				/*
				 * Daily will match the documents against Live people , Live and
				 * Dead People
				 * 
				 * Documents matching against Live people : HIV_LAB, STD_LAB,
				 * PRF, NYC_PRF, EPRF_OOC, NYC_IJR Documents matching against
				 * Live and Dead people : ADAP, SSDM, DEATH, NYC_DEATH,
				 * MEDICAID, DOCCS, AIRS. (Airs will run daily, other documents
				 * will run occassionally)
				 * 
				 * Two Jobs will run daily.
				 * 
				 * First Job can be Live or (Live and Dead)
				 * 
				 * Second Job will be Dead
				 * 
				 * 
				 */
				String qualityStageExecuteCommand = "QUALITY_STAGE_SCHEDULER_RUN_COMMAND_" + CommonConstants.SECOND_JOB;
				String qualityStageRunDateParameterName = "LAST_QUALITY_STAGE_SCHEDULER_RUN_DATE_"
						+ CommonConstants.SECOND_JOB;
				String executeQualityStageProcessActionName = "Quality stage match process for Second Job";
				if (!"Y".equals(RUN_SECOND_JOB_FOR_DEAD)) {
					qualityStageExecuteCommand = "QUALITY_STAGE_SCHEDULER_RUN_COMMAND_" + CommonConstants.FIRST_JOB;
					qualityStageRunDateParameterName = "LAST_QUALITY_STAGE_SCHEDULER_RUN_DATE_"
							+ CommonConstants.FIRST_JOB;
					executeQualityStageProcessActionName = "Quality stage match process for First Job";
				}
				boolean isSchedulerRanToday = manualQuartzJobService.isSchedulerRanToday(
						qualityStageRunDateParameterName, schedulerStatusObject, "Quality Stage", schedulerMetaData);
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				// Checking Server IP address, scheduler calling from and is
				// Scheduler ran today.
				log.error("Scheduler ran today {}", isSchedulerRanToday);
				if (!isSchedulerRanToday
						&& "Y".equals(schedulerMetaData.get(CommonConstants.LOAD_QUALITY_STAGE_SCHEDULER))
						&& expectedServerIpAddress.equals(runningServerIpAddress)) {
					// Updating the Scheduler Ran data in metadata table
					manualQuartzJobService.updateSchedulerRanDate(qualityStageRunDateParameterName,
							schedulerStatusObject, "Quality Stage", schedulerMetaData,
							"QUALITY_STAGE_DEPENDENT_SCHEDULER");
					/*
					 * Storing the Status object in Application context.
					 * 
					 * This status will be high level, which will be sending it
					 * to the few users.
					 */

					List<String> schedulerNotificationObj = schedulerNotificationVO.getSchedulerStatusObj();
					
					schedulerNotificationObj.clear();
					
					StringBuilder statusMailSubject = new StringBuilder("PRE-MATCH ON ");
					statusMailSubject.append(EnvironmentProperties.envProperties.getProperty("environmentName"));
					statusMailSubject.append(" BY ");
					if (CommonUtil.isNullOrEmpty(initiator)) {
						statusMailSubject.append("QUARTZ");
						initiator = "QUARTZ";
					} else {
						statusMailSubject.append("MANUAL <").append(initiator).append(">");
					}
					statusMailSubject.append(" DB ");
					statusMailSubject.append(schedulerMetaData.get("DATABASE_NAME"));
					// Sending notification mail that the RAW records clean
					// process started
					if (!"Y".equals(RUN_SECOND_JOB_FOR_DEAD)) {
						// If its a first Job
						manualQuartzJobService.sendNotificationEmail(schedulerMetaData,
								"START " + statusMailSubject.toString(),
								"Successfully called the First Job pre match process",
								CommonConstants.RESPONSE_CONTENT_TYPE_PLAIN);
						
						quartzSchedulerTransactionHelper.loadZipCodeReferenceMap();
						
						loadDocumentsFromRawToStd(schedulerMetaData, schedulerStatusObject, initiator,
								schedulerNotificationObj);
						
						manualQuartzJobService.sendNotificationEmailToUsers(schedulerMetaData, schedulerNotificationObj);
					}
					String transactionStatus = "";
					
					//datafluxJobExce.runRemoteServerJob();
					
//						// Loading all the Standardized STD records into
//						// EXPORT01 file
//						transactionStatus = manualQuartzJobService.writeStandardDataToExport01(schedulerStatusObject,
//							boolean isAlljaroSchedulerRanToday = manualQuartzJobService.isSchedulerRanToday(
//									"LAST_ALL_JARO_SCHEDULER_RUN_DATE", schedulerStatusObject, "ALLJARO",
//								// Calling the Quality stage process for Matches
//								// and Standardize data
//								///////////////////////////////////////////////////
//								// //
//								// Initiate Quality Stage Person Match on JAROT
//								// //
//								// //
//								///////////////////////////////////////////////////
//								manualQuartzJobService.callingShellProcess(schedulerStatusObject, schedulerMetaData,
//										qualityStageExecuteCommand, executeQualityStageProcessActionName,
//								statusObject.put("MESSAGE",
//								schedulerNotificationObj
//										.add("ALLJARO File is not latest, Stopping the Complete Quality stage process");
//								manualQuartzJobService.updateSchedulerMetaData(
//										"LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER", "N", null);
//								manualQuartzJobService.updateSchedulerMetaData(
//										"LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_FOR_BOTH_JOBS", "N", null);
//								manualQuartzJobService.sendNotificationEmailToUsers(schedulerMetaData,
					if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_SEND_DETAILED_NOTIFICATION"))) {
						log.error("Sending the status email");
						// Sending the notification email
						manualQuartzJobService.sendSchedulerDetailedProcessStatus(schedulerStatusObject,
								"END " + statusMailSubject.toString(), schedulerMetaData);
					} else {
						log.error("Not sending the status email.");
					}
					/*
					 * If the Second Job failed will load the First Job match
					 * results.
					 */
//					if ("Y".equals(RUN_SECOND_JOB_FOR_DEAD)
//							&& CommonConstants.TRANSACTION_STATUS_FAILURE.equals(transactionStatus)) {
//						manualQuartzJobService.updateSchedulerMetaData(
//								"LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_FOR_BOTH_JOBS", "N", null);
//						quartzSchedulerTransactionHelper.loadQualityStageMatchResultsJob(initiator,
//								schedulerStatusObject, schedulerMetaData, CommonConstants.FIRST_JOB, true,
				}
			}
		}
	}

	private void loadDocumentsFromRawToStd(Map<String, String> schedulerMetaData,
			List<JSONObject> schedulerStatusObject, String initiator, List<String> schedulerNotificationObj) {
		// Load data from ECLRS.vAllH to RAW_HIV_ECLRS_DOC to STD_HIV_ECLRS_DOC
		
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_STD_CONF_RECORDS"))) {
			LabCommonUtils.updateCodedRiskValue("X", "X");
			cleanRawLabRecords("STD_CONF", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, true);
		}
		LabCommonUtils.updateCodedRiskValue("X", "Y");
		
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_ECLRS_HIV_RECORDS"))) {
			cleanHivRawLabRecords("HIV", schedulerMetaData, schedulerStatusObject, schedulerNotificationObj);
		}
		// Load data from ECLRS.vAllS to RAW_STD_ECLRS_DOC to STD_STD_ECLRS_DOC
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_ECLRS_STD_RECORDS"))) {
			cleanStdRawLabRecords("STD", schedulerMetaData, schedulerStatusObject, schedulerNotificationObj);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_PRF_RECORDS"))) {
			cleanRawLabRecords("PRF", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, false);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_NYC_PRF_RECORDS"))) {
			cleanRawLabRecords("NYC_PRF", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj , false, false);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_AIRS_RECORDS"))) {
			// Stopping No RECORDS status documents in RAW layer ?
			airsDocumentService.stopAirsNORecordsAtRaw(schedulerStatusObject);
			airsDocumentService.repriseAgencyErrorDocuments();
			cleanRawLabRecords("AIRS", schedulerMetaData, schedulerStatusObject, true, schedulerNotificationObj, false, false);
			airsDocumentService.checkForActiveErrors(true);
		}
		// Load data from RAW_MEDICAID_DOC to STD_MEDICAID_DOC
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_MEDICAID_RECORDS"))) {
			cleanRawLabRecords("INS", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, true);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_ADAP_RECORDS"))) {
			cleanRawLabRecords("ADAP", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, true);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_DEATH_RECORDS"))) {
			cleanRawLabRecords("DTH", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, true, true);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_NYC_DEATH_RECORDS"))) {
			cleanRawLabRecords("NYC_DTH", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, true, true);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_CASA_RECORDS"))) {
			cleanRawLabRecords("CASA", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, false);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_EPRF_OOC_RECORDS"))) {
			cleanRawLabRecords("EPRF_OOC", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, false);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_SSDM_RECORDS"))) {
			cleanSSDMRecords("SSDM", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_NYC_IJR_RECORDS"))) {
			cleanRawLabRecords("NYC_IJR", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, false);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_SYPH_RECORDS"))) {
			cleanRawLabRecords("SYPH", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, false);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_DOCCS_RECORDS"))) {
			cleanRawLabRecords("DOCCS", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, true);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_TB_RECORDS"))) {
			cleanRawLabRecords("TB", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, true);
		}
		
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_ICCR_RECORDS"))) {
			cleanRawLabRecords("ICCR", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, false);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_QUAL_CARE_RECORDS"))) {
			cleanRawLabRecords("QUAL_CARE", schedulerMetaData, schedulerStatusObject, false, schedulerNotificationObj, false, false);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_CCD_RECORDS"))) {
			cleanHITRecords("CCD", schedulerMetaData, schedulerStatusObject, initiator);
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_HIE_DTH_RECORDS"))) {
			cleanHITRecords("HIE_DTH", schedulerMetaData, schedulerStatusObject, initiator);
		}
	}

	private void cleanSSDMRecords(String documentType, Map<String, String> schedulerMetaData,
			List<JSONObject> schedulerStatusObject, boolean b, List<String> schedulerNotificationObj) {

		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_STORE_RAW_RECORDS_TO_STD_FOR_" + documentType))) {
			// Get the Raw document list
			log.error("Fetched the Raw data for : {}", documentType);
			// Move the Raw documents to Std document layer
			String transactionStatus = quartzSchedulerTransactionHelper
					.writeAllRawRecordsToStdLayer(schedulerStatusObject, documentType, schedulerNotificationObj);
			log.error("Written the Raw {}  data to STD",documentType);

			if (CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)) {

				quartzSchedulerTransactionHelper.checkDuplicatesInStdTable(schedulerStatusObject, documentType,
						schedulerNotificationObj);

			}

		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_CHANGE_CLERICAL_TO_NON_CLERICAL"))) {
			log.error("Started changing the status from  Clerical to Non Clerical");
			// Loading Clerical review documents part of the Quality stage
			// process.
			manualQuartzJobService.loadClericalReviewRecordsForQSProcess(schedulerStatusObject, documentType);
		} else {
			log.error("Skipping the process to change the status from  Clerical to Non Clerical");
		}

	}

	/**
	 * 
	 * This method is used to move the records from Raw doc table to Std doc
	 * table.
	 * 
	 * Will format the Raw data and store it into Std table.
	 * 
	 * @param recordType
	 *            Document Type (One of the values of
	 *            CODED_DOCUMENT_TYPE.DOCUMENT_TYPE_CODE)
	 * @param schedulerMetaData
	 *            {@link java.util.Map} of LAB_SCHEDULER_PARAMETER.PARAMTER_NAME
	 *            to LAB_SCHEDULER_PARAMETER.PARAMTER_VALUE
	 * @param schedulerStatusObject
	 *            A map-like object to contain a status message for each stage
	 *            of processing.
	 * @param needToCheckDuplicates
	 *            : Need to check the duplicates or not. For few documents in
	 *            Raw layer we will have duplicate records. need to identify the
	 *            duplicate record and retire those records from the doc locator
	 *            table
	 */
	private void cleanRawLabRecords(String documentType, Map<String, String> schedulerMetaData,
			List<JSONObject> schedulerStatusObject, boolean needToCheckDuplicatesInRaw,
			List<String> schedulerNotificationObj, boolean needToCheckDuplicatesinSTD, boolean addFileToAssignmentReports) {

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		if (needToCheckDuplicatesInRaw) {
			// Checking the duplicates
			transactionStatus = manualQuartzJobService.checkForDuplicates(documentType, schedulerStatusObject,
					schedulerMetaData.get(documentType + "_DUPLICATED_COLUMNS"));
			log.error("checking the duplicates status {}", transactionStatus);
			if (CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)) {
				schedulerNotificationObj.add(documentType + "   : Successfully completed the deduplication process");
			} else {
				schedulerNotificationObj.add(documentType + "   : Failed to perform Duplication process");
			}
		}
		if (CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)
				&& "Y".equals(schedulerMetaData.get("REQUIRED_TO_STORE_RAW_RECORDS_TO_STD_FOR_" + documentType))) {
			// Get the Raw document list
			List<Map<Object, Object>> docList = manualQuartzJobService.getRawDocsList(documentType);
			log.error("Fetched the Raw data for : {}", documentType);
			
			
			quartzSchedulerTransactionHelper.loadDocumentReferenceVariables(documentType, schedulerMetaData);
			
			
			// Move the Raw documents to Std document layer
			quartzSchedulerTransactionHelper.writeRawRecordsToStdLayer(docList, schedulerStatusObject, documentType,
					schedulerNotificationObj);
			log.error("Written the Raw {} data to STD", documentType);
			
			
			quartzSchedulerTransactionHelper.resetDocumentReferenceVariables(documentType);
			
			if(addFileToAssignmentReports && docList != null && docList.size() > 0){
				
				manualQuartzJobService.addFileNameToAssignmentReportsTable(documentType, schedulerMetaData);
			}
			
			
		}
		
		if (needToCheckDuplicatesinSTD) {

			quartzSchedulerTransactionHelper.checkDuplicatesInStdTable(schedulerStatusObject, documentType,
					schedulerNotificationObj);
		}
		
		
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_CHANGE_CLERICAL_TO_NON_CLERICAL"))) {
			log.error("Started changing the status from  Clerical to Non Clerical");
			// Loading Clerical review documents part of the Quality stage
			// process.
			manualQuartzJobService.loadClericalReviewRecordsForQSProcess(schedulerStatusObject, documentType);
		} else {
			log.error("Skipping the process to change the status from  Clerical to Non Clerical");
		}
	}

	/**
	 * This method is used to load STD Records in to the RAW table from
	 * ECLRS_POST and Reassign tables It checks for duplicates and moves record
	 * from RAW_XXX_DOC to STD_XXX_DOC
	 * 
	 * @param recordType
	 *            Document Type (One of the values of
	 *            CODED_DOCUMENT_TYPE.DOCUMENT_TYPE_CODE)
	 * @param schedulerMetaData
	 *            {@link java.util.Map} of LAB_SCHEDULER_PARAMETER.PARAMTER_NAME
	 *            to LAB_SCHEDULER_PARAMETER.PARAMTER_VALUE
	 * @param schedulerStatusObject
	 *            A map-like object to contain a status message for each stage
	 *            of processing.
	 */
	protected void cleanStdRawLabRecords(String recordType, Map<String, String> schedulerMetaData,
			List<JSONObject> schedulerStatusObject, List<String> schedulerNotificationObj) {

		log.error("Started fetching the STD ECLRS data");
		

		long maxObxObservationResultKey = 0;
		
		Map<String, Object> lastEclrsFetchData  = manualQuartzJobService.getLastInsertedECLRSRecordId(recordType);
		
		long lastMaxObservationResultKeyInserted = (Long)lastEclrsFetchData.get("LAST_MAX_OBX_KEY");
		Timestamp LAST_PIDUPDATEDATE = (Timestamp)lastEclrsFetchData.get("LAST_PIDUPDATEDATE");
		
		// Loading the data from ECLRS
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_ECRLS_DATA"))) {
			List<EcrlsLabDocVO> rawEcrlsLabDocList = quartzSchedulerTransactionHelper.copyDataFromEcrlsToNyehms(schedulerStatusObject,
					CommonConstants.NEW_STD_ECLRS_DATA, recordType, schedulerMetaData, schedulerNotificationObj, lastMaxObservationResultKeyInserted);
			
			if (rawEcrlsLabDocList != null && rawEcrlsLabDocList.size() > 0) {
				
				maxObxObservationResultKey = rawEcrlsLabDocList.get(0).getMaxOBXOBSERVATIONRESULTKEY();
				
				quartzSchedulerTransactionHelper.copyPIDUpdatedRecordsToNyehms(schedulerStatusObject, CommonConstants.NEW_STD_ECLRS_DATA, "STD_LAB", schedulerMetaData,
						schedulerNotificationObj, lastMaxObservationResultKeyInserted, LAST_PIDUPDATEDATE, maxObxObservationResultKey);
				
			}
			
		}
		
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_STD_PID_ERROR_DOCUMENTS"))) {
			log.error("Started processing PID Error documents. Loading again from ECLRS to NYEHMS");
			quartzSchedulerTransactionHelper.loadPIDErrorDocumentsFromECLRSToNyehms(schedulerStatusObject, CommonConstants.NEW_STD_ECLRS_DATA, "STD_LAB", schedulerMetaData, schedulerNotificationObj);
		}
		
		
		// Loading the ECLRS Reassign table data
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_ECRLS_REASSIGN_DATA"))) {
			log.error("Started fetching the ECRLS reassign data");
			quartzSchedulerTransactionHelper.copyReAssignHivDocDataFromEcrlsToNyehms(schedulerStatusObject,
					CommonConstants.REASSIGN_ECLRS_DATA, recordType, schedulerNotificationObj);
		} else {
			log.error("Skipping Load Eclrs Reassign data");
		}
		
		
		
		// Checking for Duplicates
		String transactionStatus = manualQuartzJobService.checkForDuplicates(recordType, schedulerStatusObject,
				schedulerMetaData.get(recordType + "_DUPLICATED_COLUMNS"));
		if (CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)) {
			schedulerNotificationObj.add(recordType + "   : Successfully completed the deduplication process");
		} else {
			schedulerNotificationObj.add(recordType + "   : Failed to perform Duplication process");
		}
		log.error("checking the duplicates status {}", transactionStatus);
		if (CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)
				&& "Y".equals(schedulerMetaData.get("REQUIRED_TO_STORE_STD_RECORDS_IN_STD_LAYER"))) {
			// Load the Raw Std data (Current_process_Status = 1)
			List<EcrlsLabDocVO> rawEcrlsLabDocList = manualQuartzJobService.getRawStdUniqueRecordsList();
			log.error("Standard records Length {}", rawEcrlsLabDocList.size());
			// Writing Raw STD data to STD table
			quartzSchedulerTransactionHelper.writeRawStdRecordsToStdLayer(rawEcrlsLabDocList, schedulerStatusObject,
					schedulerNotificationObj);
		}

		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_CHANGE_CLERICAL_TO_NON_CLERICAL"))) {
			log.error("Started changing the status from  Clerical to Non Clerical");
			manualQuartzJobService.loadClericalReviewRecordsForQSProcess(schedulerStatusObject, recordType);
		} else {
			log.error("Skipping the process to change the status from  Clerical to Non Clerical");
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_RESTAGE_STD_NON_MATCHES"))) {
			log.error("Started changing the status STD Non matches to ");
			labDocumentService.restageStdDocumentNonMatches(schedulerStatusObject, schedulerMetaData);
		} else {
			log.error("Skipping the process to change the status from  Clerical to Non Clerical");
		}

		labDocumentService.rollbackStdDocumentNonMatches(schedulerMetaData);
	}

	/**
	 * Copies new HIV results from ECLRS to NYEHMS, then checks for duplicates
	 * both within the new records and then within the whole table.
	 * 
	 * @param recordType
	 *            "HIV" or "STD", the type to be copied and checked for
	 *            duplicates
	 * @param schedulerMetaData
	 *            Input parameter map for the process Each of the following may
	 *            be "Y" or "N": REQUIRED_TO_LOAD_ECRLS_DATA
	 *            REQUIRED_TO_LOAD_ECRLS_REASSIGN_DATA
	 *            REQUIRED_TO_PERFORM_MULTI_STEP_PROCESS
	 *            REQUIRED_TO_RUN_HIV_RECORDS_VALIDATION_PROCESS
	 *            REQUIRED_TO_CHANGE_CLERICAL_TO_NON_CLERICAL
	 *            REQUIRED_TO_SEND_DUPLICATION_STATUS
	 * @param schedulerStatusObject
	 *            Object containing status codes for results of various steps.
	 */
	protected void cleanHivRawLabRecords(String recordType, Map<String, String> schedulerMetaData,
			List<JSONObject> schedulerStatusObject, List<String> schedulerNotificationObj) {

		long maxObxObservationResultKey = 0;
		// Load HIV data from ECLRS
		
		Map<String, Object> lastEclrsFetchData  = manualQuartzJobService.getLastInsertedECLRSRecordId(recordType);
		
		long lastMaxObservationResultKeyInserted = (Long)lastEclrsFetchData.get("LAST_MAX_OBX_KEY");
		Timestamp LAST_PIDUPDATEDATE = (Timestamp)lastEclrsFetchData.get("LAST_PIDUPDATEDATE");
		
		
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_ECRLS_DATA"))) {
			log.error("Started fetching the ECLRS data");
			List<EcrlsLabDocVO> rawEcrlsLabDocList = quartzSchedulerTransactionHelper.copyDataFromEcrlsToNyehms(
					schedulerStatusObject, CommonConstants.NEW_HIV_ECLRS_DATA, recordType, schedulerMetaData,
					schedulerNotificationObj, lastMaxObservationResultKeyInserted);
			if (rawEcrlsLabDocList != null && rawEcrlsLabDocList.size() > 0) {
				maxObxObservationResultKey = rawEcrlsLabDocList.get(0).getMaxOBXOBSERVATIONRESULTKEY();
				
				quartzSchedulerTransactionHelper.copyPIDUpdatedRecordsToNyehms(schedulerStatusObject, CommonConstants.NEW_HIV_ECLRS_DATA, "HIV_LAB", schedulerMetaData,
						schedulerNotificationObj, lastMaxObservationResultKeyInserted, LAST_PIDUPDATEDATE, maxObxObservationResultKey);
			}
			
			
		} else {
			log.error("Skipping to Load {}  Eclrs data", recordType);
		}
		
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_HIV_PID_ERROR_DOCUMENTS"))) {
			
			log.error("Started processing PID Error documents. Loading again from ECLRS to NYEHMS");
			
			quartzSchedulerTransactionHelper.loadPIDErrorDocumentsFromECLRSToNyehms(schedulerStatusObject, CommonConstants.NEW_HIV_ECLRS_DATA, "HIV_LAB", schedulerMetaData, schedulerNotificationObj);
		}
		
		
		// Loading HIV data from Reassign table
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_ECRLS_REASSIGN_DATA"))) {
			log.error("Started fetching the ECLRS reassign data");
			quartzSchedulerTransactionHelper.copyReAssignHivDocDataFromEcrlsToNyehms(schedulerStatusObject,
					CommonConstants.REASSIGN_ECLRS_DATA, recordType, schedulerNotificationObj);
		} else {
			log.error("Skipping Load ECLRS Reassign data");
		}
		
		
		
		
		
		
		
		
		
		log.error("Started checking  duplicates");
		// Checking the Duplicates
		String transactionStatus = manualQuartzJobService.checkForDuplicates(recordType, schedulerStatusObject,
				schedulerMetaData.get(recordType + "_DUPLICATED_COLUMNS"));
		if (CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)) {
			schedulerNotificationObj.add(recordType + "   : Successfully completed the deduplication process");
		} else {
			schedulerNotificationObj.add(recordType + "   : Failed to perform Duplication process");
		}
		if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_PERFORM_MULTI_STEP_PROCESS"))) {
			log.error("Started checking MULTI STEP documents");
			// Checking for the MULTISTEP documents
			if (CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)) {
				transactionStatus = manualQuartzJobService.checkMultiStepDocumentsInRawHIVRecords(schedulerStatusObject,
						recordType);
				if (CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)) {
					schedulerNotificationObj.add(recordType + "   : Successfully completed the Multistep process");
				} else {
					schedulerNotificationObj.add(recordType + "   : Failed to perform Multistep process");
				}
			}
		} else {
			log.error("Skipping to run Multi step process");
		}
		if (CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)
				&& "Y".equals(schedulerMetaData.get("REQUIRED_TO_RUN_HIV_RECORDS_VALIDATION_PROCESS"))) {
			log.error(
					"Started fetching the Reprised, New Eclrs, Eclrs reassign and failed raw documents for validation purpose");
			// Load NEW HIV data
			manualQuartzJobService.doHivLabUpdates();	
			
			List<EcrlsLabDocVO> uniquerawHivDocList = quartzSchedulerTransactionHelper
					.copyDataFromNyehms(schedulerStatusObject, CommonConstants.RAW_REPRISE_DATA, recordType);
			
			quartzSchedulerTransactionHelper.loadDocumentReferenceVariables("HIV_LAB", schedulerMetaData);
			
			log.error("HIV unitsofmeasure and other lab updtaes");
		
			
			log.error("Started validating the Reprised, New Eclrs, Eclrs reassign and failed raw documents");
			// Validating the Unique HIV documents
			quartzSchedulerTransactionHelper.validateAndUpdateTheDocumentStatus(uniquerawHivDocList,
					schedulerStatusObject, schedulerMetaData, maxObxObservationResultKey, schedulerNotificationObj);
			
			quartzSchedulerTransactionHelper.resetDocumentReferenceVariables("HIV_LAB");
			
			
			if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_CHANGE_CLERICAL_TO_NON_CLERICAL"))) {
				log.error("Started changing the status from  Clerical to Non Clerical");
				// Changing the Clerical review records status to 5 to match
				// these records again with Quality stage
				manualQuartzJobService.loadClericalReviewRecordsForQSProcess(schedulerStatusObject, recordType);
			} else {
				log.error("Skipping the process to change the status from  Clerical to Non Clerical");
			}
			if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_SEND_DUPLICATION_STATUS"))) {
				log.error("started the process to send Duplication status");
				// Sending the Duplicate documents list
				manualQuartzJobService.sendDuplicatedDocumentStatus(schedulerMetaData);
			} else {
				log.error("Skipping the process to send Duplication status");
			}
		}
	}

	protected void cleanHITRecords(String recordType, Map<String, String> schedulerMetaData,
			List<JSONObject> schedulerStatusObject, String initiator)
	{
		try {
			hitCommonDocumentService.extractNewPayloadsFromUphnToRaw(schedulerStatusObject, recordType,
					schedulerMetaData, initiator);
			// HIT_ERROR_REPRISE_CODE_ADDED DATE : 10/17/2017
			hitCommonDocumentService.extractHitErrorFromUphnToRaw(schedulerStatusObject, recordType, schedulerMetaData,
					initiator);
			preMatchHITSchedulerHelper.writeRawHitRecordsToStd(recordType, schedulerMetaData, schedulerStatusObject);
			if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_CHANGE_CLERICAL_TO_NON_CLERICAL"))) {
				log.error("Started changing the status from  Clerical to Non Clerical");
				// Loading Clerical review documents part of the Quality stage
				// process.
				manualQuartzJobService.loadClericalReviewRecordsForQSProcess(schedulerStatusObject, "CCD");
				manualQuartzJobService.loadClericalReviewRecordsForQSProcess(schedulerStatusObject, "HIE_DTH");
			} else {
				log.error("Skipping the process to change the status from  Clerical to Non Clerical");
			}
		} catch (Exception e) {
			log.error("Exception processing at Raw Records Cleaner : {}",CommonUtil.getStackTrace(e).substring(0, 200));
		}
	}
}