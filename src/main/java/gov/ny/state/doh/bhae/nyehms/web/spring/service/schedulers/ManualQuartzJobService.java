package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.quartz.JobDataMap;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsReAssignDocs;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsStdDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MultiStepDocumentsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MultiStepGroupedLoincsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MultiStepProcessVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 * This service interface is used to get the information required for Scheduler
 * jobs and to control the Scheduler job
 * 
 * @author ska03
 * 
 */
public interface ManualQuartzJobService {


	/**
	 * This method is used to check the Permissions to access the Quartz controller page.
	 * 
	 * If user is having a Active Coordinator Role, then will give the access to show the page.
	 * 
	 * @param workerId 
	 * @return
	 */
	/**
	 * This method is used to check the Permissions to access the Quartz controller page.
	 * 
	 * If user is having a Active Coordinator Role, then will give the access to show the page.
	 * @param result Binding result object
	 * @param userObject logged in user object
	 */
	public Integer getPermissionsAccessForManualQuartzJob(BindingResult result,
			UserObject userObject);

	/**
	 * This method is used to get all the Active Coordinators Email addresses available in the region.
	 * 
	 * For Every scheduler action(Manual/Regular) will send the Mail status to Coordinators.
	 * @return List of reciepent Email address
	 */
	public String[] getRecipientsEmailsForRawHIVCleanedStatus();
	
	/**
	 * This method is used to control the Scheduler job like pausing, resuming
	 * and Triggering manually 
	 * 
	 * At the end of transaction will send the consolidated status to the Coordinators available in the application.
	 * 
	 * Who is trying to control the Scheduler manually, that information also will send it to the all the Coordinators.
	 * 
	 * @param request Request object
	 * @param bindingResult Storing the transaction information
	 * @param userObject Logged in user object
	 * @param jobName Scheduler name
	 * @param actionName  Pause/Resume/Trigger
	 */
	public String triggerSchedulerJob(BindingResult bindingResult, String jobName,
			String actionName, JobDataMap jobDataMap);
	
	public boolean handleDateValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes, StdEcrlsLabDocVO stdEcrlsLabDocVO);
	
	public boolean handleAddressValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes, StdEcrlsLabDocVO stdEcrlsLabDocVO);
	
	//public boolean handleProficiencyValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes, Map<String, String> schedulerMetaData);
	
	public boolean handleSoftCheckValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes, StdEcrlsLabDocVO stdEcrlsLabDocVO, Map<String, String> schedulerMetaData);
	
	public boolean handleProcessStatusValidationErrors(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes, Map<String, String> schedulerMetaData);
	
	public boolean handleRaceEthnicityValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes, StdEcrlsLabDocVO stdEcrlsLabDocVO);
	
	public void handleRaceEthnicityValidationForStdDocument(EcrlsLabDocVO ecrlsLabDocVO, EcrlsStdDocVO stdEcrlsLabDocVO);
	
	public List<EcrlsLabDocVO> getEclrsRawDatatoClean(String recordType, long lastInsertedId, int fetchRowCount);
	
	//public List<UphnTranxInVo> getRawCCDDatatoClean(String recordType, long lastInsertedId, int fetchRowCount);
	
	//public List<UphnTranxInVo> getRawEXPDatatoClean(String recordType, long lastInsertedId, int fetchRowCount);
	
	public int writeRawEcrlsData(String recordsType, EcrlsLabDocVO ecrlsLabDocVO);
	
	public int locateInDocumentLocator(int documentId);
	
	public String checkForDuplicates(String recordType, List<JSONObject> schedulerStatusObject, String duplicateColumns);
	
	public boolean validateCliaAndPFi(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes);
	
	public boolean handleLabDataValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes, StdEcrlsLabDocVO stdEcrlsLabDocVO, Map<String, String> schedulerMetaData);
	
	public void handleDinValidation(EcrlsLabDocVO ecrlsLabDocVO, StdEcrlsLabDocVO stdEcrlsLabDocVO);
	
	public void handleAgeValidation(EcrlsLabDocVO ecrlsLabDocVO, StdEcrlsLabDocVO stdEcrlsLabDocVO);
	
	public void handleSSNValidation(EcrlsLabDocVO ecrlsLabDocVO, StdEcrlsLabDocVO stdEcrlsLabDocVO);
	
	public int addValidationError(Number documentId, String errors, int currentProcessStatus, boolean havingSoftCheckValidationErrors, Number previousProcessStatus, String documentType);
	
	public int writeErrorCode(int statusCode, Number documentId, String documentType);
	
	//public void InsertHitErrorlog(int uphnId,String decryptedCCDString, String error);
	
//	public long getLastInsertedEXPRecordId(String recordType);
	
	public int makeDocumentAsInactivate(int documentId);
	
	public List<EcrlsLabDocVO> runSchedulerFromNyehms();
	
	public String writeStandardDataToExport01(List<JSONObject> schedulerStatusObject, Map<String, String> schedulerMetaData, String RUN_SECOND_JOB_FOR_DEAD, List<String> schedulerNotificationObj);
	
	public void callingShellProcess(List<JSONObject> schedulerStatusObject, Map<String, String> schedulerMetaData, String processName, String actionName, List<String> schedulerNotificationObj);
	
	public void getNewDocId(Map<Object,Object> map);
	
	public void sendDuplicatedDocumentStatus(Map<String, String> schedulerMetaData);
	
	public List<EcrlsReAssignDocs> getReAssignDocList(String recordType);
	
	public List<EcrlsLabDocVO> getReAssignDocuments(String recordType);
	
	public int updateReAssignDocProcessedStatus(long OBXOBSERVATIONRESULTKEY, long OBROBSERVATIONREQUESTKEY);
	
	public int updateDocumentProcessStatusLog(int failedDocumentIdsCount, long OBXOBSERVATIONRESULTKEY);
	
	
	public JSONObject checkQualityStageJobStatus(Map<String, String> schedulerMetaData, String logPath, String fileName, String actionName, String successMessageIdentification, String waitTimeParameter, JSONObject statusObject, String jobName);
	
	public boolean loadQualityStageMatchResults(List<JSONObject> schedulerStatusObject, Map<String, Map<String, Object>> qualityStageMatchResultsData, Map<String, String> schedulerMetaData, String jobName, Map<String, Map<String, String>> zipCodeReferenceMap);
	
	public boolean loadQualityStageDuplicateRecords(
			List<JSONObject> schedulerStatusObject,
			Map<String, String> parentRecords,
			Map<String, List<String>> duplicatedChildRecords,
			Map<String, String> schedulerMetaData,
			String jobName);
	
	public int storeDocumentMatchResults(List<Map<String, String>> documentQualityStageData);
	
	public String loadDataToReferenceFile(List<JSONObject> schedulerStatusObject, Map<String, String> schedulerMetaData, String queryName, String outputFileName, String actionName, String recordLength, String referenceIdPosition, String dependentSchedulers, boolean appendToExistingFile) ;
	
	public void sendSchedulerDetailedProcessStatus(List<JSONObject> schedulerStatusObject, String subject, Map<String, String> schedulerMetaData);
	
	public int storeDocumentMatchResultsToStandardTable(Map<String, Object> documentMatchData);

	public int storeDuplicateDocumentSetDataToMatchResultsTable(String parentDocumentId, List<String> duplicateDocumentIds);

	public String loadDocumentMatchSummary(List<JSONObject> schedulerStatusObject);
	
	public boolean handleRawEclrsRecordValidations(EcrlsLabDocVO ecrlsLabDocVO, StringBuilder failedToValidateDocuments, Map<String, String> schedulerMetaData);
	
	public Map<String, String> getSchedulerMetaData();
	
	
	//public void archiveMatchResultsData(List<JSONObject> schedulerStatusObject, List<String> schedulerNotificationObj);
	
	public String loadQualityStageDataJob(List<JSONObject> schedulerStatusObject, Map<String, String> schedulerMetaData, String jobName, boolean finalStageOfLoadingResults, List<String> schedulerNotificationObj, boolean needToArchieve, List<Map<String, Object>> nonCaseDocumentDetails);
	
	public int deleteChildDocumentsFromMatchResults(List<String> duplicateDocumentIds);
	
	public int disableFlagInErrorTable(int _documentID);
	
	public void updateSchedulerMetaData(String parameterName, String parameterValue, BindingResult bindingResult);
	
	public String getMetaDataValue(String parameterName);
	
	public void loadClericalReviewRecordsForQSProcess(List<JSONObject> schedulerStatusObject, String recordType);
	
//	public List<MultiStepGroupedLoincsVO> getmultiStepPfiGroupCountDetails();
	
	public List<MultiStepDocumentsVO> getMultiStepProcessDocumentDetails();
	

	public String checkMultiStepDocumentsInRawHIVRecords(List<JSONObject> schedulerStatusObject, String recordType);

	public int checkForReferenceLoincDocumentsCount(
			MultiStepProcessVO multiStepProcessVO);

	public int changeDocumentsStatusToMultiStepCompressFlag();

	public boolean createNewDocAndWriteRawEcrlsData(String recordType, EcrlsLabDocVO ecrlsLabDocVO);

	public int associateParentDocumentForMultiStepDocuments(List<Integer> multiStepDocumentIds,
			int document_ID, String flagStatus);
	
	public List<Map<String, String>> getObxObservationResultsMapping(String oBXOBSERVATIONRESULTTEXT, String loincCode, Boolean isNumeric);

	public EcrlsLabDocVO getUpdatedMultiProcessDocument(
			List<EcrlsLabDocVO> multiStepProcessDocuments, int retainDocumentId, List<String> errors, int geeniusRecordsCount, String pfi);

	public String generateHashNumberForNewRecords(String recordType, List<JSONObject> schedulerStatusObject, String duplicateColumns);
	
	public String checkingDuplicatesInNewRawHIVRecords(String recordType, List<JSONObject> schedulerStatusObject);

	public String checkingDuplicatesInEntireDataWithNewRawHIVData(String recordType, List<JSONObject> schedulerStatusObject);

//	public int getMultiStepGroupLoincCount(String pfi, String loincCode);

	public boolean loadRawProviderOrFacilityMappingResults(
			List<JSONObject> schedulerStatusObject,
			Map<String, String> schedulerMetaData, Map<Integer, List<Integer>> subToRawMapping, String fileName, String actionName, int rawIndex, int subIndex, int recordLength);

	public void storingStandardizeRawResultsInDatabase(
			Integer subProviderAddressID, List<Integer> list,
			List<String> failureComments, String moduleName);

	public boolean isSchedulerRanToday(String schedulerName, List<JSONObject> schedulerStatusObject,String processName,Map<String, String> schedulerMetaData);

	public boolean updateSchedulerRanDate(String schedulerName,
			List<JSONObject> schedulerStatusObject, String processName,
			Map<String, String> schedulerMetaData, String dependentSchedulers);

	public List<EcrlsLabDocVO> getRawStdUniqueRecordsList();

	public int writeRawStdRecordDataToStdTable(EcrlsLabDocVO ecrlsLabDocVO);

	public int updateTheRawStdRecordToStandardize(int document_ID);

	public int updateTheRawRecordToStandardize(BigDecimal document_ID, String documentType);
	
	public void sendNotificationEmail(Map<String, String> schedulerMetaData, String subject, String mailConent, String responseType);

	public int deleteDocumentInErrorTable(int currentDocumentId, String requestComments, int processStatus, UserObject userObject);

	public void prepareDefaultProcessQueryConditions(
			Map<String, String> schedulerMetaData);

	public List<Map<Object, Object>> getRawDocsList(String documentType);

	public void writeErrorCodesMasterDetail(int processErrorId,
			List<Integer> errorCodes);

	public String checkDuplicatesinRawDocTable(String recordType,
			List<JSONObject> schedulerStatusObject);
	
	public Map<String, Object> getLastInsertedECLRSRecordId(String recordType);
	/*Section ENDS*/

	public void sendNotificationEmailToUsers(Map<String, String> schedulerMetaData, List<String> schedulerNotificationObj);

	public List<Map<String, Object>> getHivErrorsByCategoryAndCount();

	public boolean isSchedulerRanYesterday(String string,
			List<JSONObject> schedulerStatusObject, String string2,
			Map<String, String> schedulerMetaData);

	public List<EcrlsLabDocVO> getPIDUpdatedRecordsFromEclrs(String recordType,
			Timestamp lAST_PIDUPDATEDATE, long lastInsertedMaxObxObservationResultKey, long currentMaxObxObservationResultKey);

	public List<Map<String, Object>> getNyehmsDocumentCurrentStatus(String recordType, 
			long obxobservationresultkey);

	public void updateRawDocumentData(String recordType, Integer cURRENT_PROCESS_STATUS_RAW, Map<String, Object> rawTableInputParameterMap);

	public void updateStdPidDocumentData(String recordType, Integer cURRENT_PROCESS_STATUS_STD, Map<String, Object> stdTableInputParameterMap);

	public void createNewRawDocumentWithUpdatedPIDData(String recordType, 
			Map<String, Object> rawTableInputParameterMap);

	public void createNewStdDocumentWithUpdatedPIDData(String recordType, 
			Map<String, Object> stdTableInputParameterMap);

	public String checkPersonAddressGotEdited(String recordType, Integer oldDocumentId,
			Integer documentId);

	public void logPIDProcessError(String recodType, Long obxobservationresultkey,
			String errorMessage);
	
	public List<Map<String, Object>> getMultiStepDocumentDetails(
			Integer multiStepDocumentId);

	public int checkIfDocumentUpdatedWithPidDetails(String recordType,
			Long oBXOBSERVATIONRESULTKEY);
	
	public void writeEcrlsPidUpdatedRecordsToNyehms(String recordType,
			List<EcrlsLabDocVO> ecrlsLabDocList, String origin,
			List<JSONObject> schedulerStatusObject,
			List<String> schedulerNotificationObj);

	public List<Long> getErroredPIDDocumentsList(String recordType);

	public List<EcrlsLabDocVO> getErroredPIDDocumentsListFromEclrs(
			List<Long> erroredDocumentsList, String recordType);

	public void checkDuplicatesinStdDocTable(String documentType);

	public void addFileNameToAssignmentReportsTable(String documentType, Map<String, String> schedulerMetaData);

	public int documentInitialValidation(BigDecimal documentId,
			Map<Object, Object> docVO, String documentType);


	public String getDocumentError(int currentDocumentId);

	public String getTestType(String obxloinccode);

	public void loadHIVDocumentReferenceVariable(Map<String, String> schedulerMetaData);

	public void resetHIVDocumentReferenceVariable();

	public void sendHl7CCDRematchingRecordsDetails(Map<String, String> schedulerMetaData,
			Map<String,String> statusObject, List<Map<String,String>> recordsDetailsList);

	public Map<String, String> getLoincCombinationData(Map<String, String> inputParameterMap);

	public Map<String, String> getLoincResultCombinationData(Map<String, String> inputParameterMap);

	public int removeMultiProcessStatus();

	public void doHivLabUpdates();
}
