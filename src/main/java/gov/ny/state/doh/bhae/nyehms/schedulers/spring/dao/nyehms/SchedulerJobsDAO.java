package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsReAssignDocs;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsStdDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MailStatusVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MultiStepDocumentsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MultiStepGroupedLoincsVO;



/**
 * This DAO is used to give the required information for the Quartz scheduler
 * @author ska03
 *
 */
public interface SchedulerJobsDAO {
	
	/**
	 *
	 * @param userId The value of userObject.getUserId() from the
	 * LEGACY_UID column of the NYEHMS_WORKERS table.
	 * @return 1 if the user ID is in the USERS_HAVING_SCHEDULERS_ACCESS
	 * parameter in the LAB_SCHEDULER_PARAMETERS table; 0 if not.
	 */
	public int havingPermissionsForAccessingQuarzScheduler(String userId);
	/**
	 * Get the Raw HIV list which needs to be clean
	 * @return List of Raw HIV Records
	 */
	public List<EcrlsLabDocVO> getRawEclrsDocsList();
	
	/**
	 * This method is used to get all the Active Coordinators available in the region.
	 * 
	 * For Every scheduler action(Manual/Regular) will send the Mail status to Coordinators.
	 * @return
	 */
	public String[] getRecipientsEmailsForRawHIVCleanedStatus();
	
	
	public int addValidationError(Map<Object, Object> inputOutputParamters);
	
	public int insertHivRawEcrlsData(EcrlsLabDocVO ecrlsLabDocVO);
	
	public int insertStdRawEcrlsData(EcrlsLabDocVO ecrlsLabDocVO);
	
	public int insertIntoDocumentLocator(int documentId);
	
	public int checkForExistingCliaMatches(Map<Object, Object> inputOutputParamters);
	
	public int checkForExistingCliaAndPFIMatches(Map<Object, Object> inputOutputParamters);
	
	public List<Map<String, String>> checkForExistingProcessFilterMatch(Map<Object, Object> inputOutputParamters);
	
	public int checkForLoincAndPfiMapping(Map<Object, Object> inputOutputParamters);
	
	public Map<Object, Object> getSubFacilitiesListForThePatientAddress(String patientAddress);
	
	public Map<Object, Object> getSubProvidersListForThePatientAddress(String patientAddress);
	
	public List<String> getMatchesForRaceCode(Map<Object, Object> inputOutputParamters);
	
	public List<String> getMatchesForEthnicityCode(Map<Object, Object> inputOutputParamters);
	
	public String getStandardizedGender(String sexCode);
	
	public List<String> getLoincsForPfiLocalCodeMatch(String localCodeMapId);
	
	public Map<String, String> checkForLoincAndUnitsOfMeasurementCombination(Map<Object, Object> inputOutputParamters);
	
	public List<Map<String, String>> checkForLoincAndObservationResultTextCombination(Map<Object, Object> inputOutputParamters);
	
	public List<Map<String, String>> checkForLoincAndObservationResultRangeCombination(Map<Object, Object> inputOutputParamters);
	
	public String getlocalCodeMapIdForPfiLocalCodeMatch(Map<Object, Object> inputOutputParamters);
	
	public int makeDocumentAsInactivate(int documentId);
	
	public int writeErrorCode(Map<Object, Object> inputOutputParamters);
	
	public Map<String, Object> getLastInsertedECLRSRecordId(String recordType);
	
	public List<EcrlsLabDocVO> getRawHIVDocsList();
	
	public List<EcrlsLabDocVO> getRawStdDocsList();
		
	public Map<String, String> getCodeTestTypesForLoinc(Map<Object, Object>  inputOutputParamters);
	
	public List<String> getStandardLivePeopleData(Map<Object, Object> inputOutputParameterMap);
	
	public List<String> getStandardDeathRecordsData(Map<Object, Object> inputOutputParameterMap);
	
	public List<String> getStandardCasaRecords(Map<Object, Object> inputOutputParameterMap);
	
	public int insertSchedulerLog(Map<Object, Object>  inOutParameterMap);
	
	public List<MailStatusVO> getDocumentDuplicatedStatus();
	
	public List<String> getLatestAllJaroData();
	
	public List<String> getStandardizedUnitsOfMeasurement(String unitsOfMeasurement);
	
	public String getStandardInterpretationCode(Map<Object, Object> inputOutputParamters);
	
	public List<EcrlsReAssignDocs> getReAssignDocList(String recordType);
	
	public int updateReAssignDocProcessedStatus(Map<Object, Object> inOutParameterMap);
	
	public int updateDocumentProcessStatusLog(Map<Object, Object> inOutParameterMap);
	
	public int storeDocumentMatchResults(Map<Object, Object> inOutParameterMap);
	
	public int storeDocumentMatchResultsToHIVStandardTable(Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToPRFStandardTable(Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToNYCPRFStandardTable(Map<String, Object> documentMatchData);
	
	public int storeDocumentMatchResultsToSTDStandardTable(Map<String, Object> documentMatchData);
	
	public int storeDuplicateDocumentSetResults(Map<Object, Object> inputOutputParameterMap);
	
	public void loadDocumentMatchSummary();
	
	public void clearMatchResultsTable();
	
	public List<Map<String, String>> getSchedulerMetaData();
	
	public int deleteChildDocumentsFromMatchResults(Map<Object, Object> inputOutputParameterMap);
	
	public int disableFlagInErrorTable(int _documentID);
	
	public int updateSchedulerMetaData(Map<Object, Object> inputOutputParameterMap);
	
	public String getMetaDataValue(String parameterName);
	
	public int loadClericalReviewRecordsForQSProcess(Map<Object, Object> inputOutputParameterMap);
	
	public int checkDocumentTraversedThroughSoftCheckValidations(int document_ID);
	
//	public List<MultiStepGroupedLoincsVO> getmultiStepPfiGroupCountDetails();
	
	public int checkForReferenceLoincDocumentsCount(
			Map<Object, Object> inputOutputParameterMap);
	public int changeDocumentsStatusToMultiStepCompressFlag();
	
	public List<MultiStepDocumentsVO> getMultiStepDocuments();
	
	public int associateParentDocumentForMultiStepDocuments(
			Map<Object, Object> inputOutputParameterMap);
	public int generateHashNumberForNewRecords(Map<Object, Object> inputOutputParameterMap);
	public int checkingDuplicatesInNewRawHIVRecords(Map<Object, Object> inputOutputParameterMap);
	public int checkingDuplicatesInEntireDataWithNewRawHIVData(Map<Object, Object> inputOutputParameterMap);
//	public int getMultiStepGroupLoincCount(
//			Map<Object, Object> inputOutputParameterMap);
	public int standardizeRawProviderAddressIdsWithSubProviderId(
			Map<Object, Object> inputOutputParameterMap);
	public int standardizeRawFacilityIdsWithSubFacilityId(
			Map<Object, Object> inputOutputParameterMap);
	public int isSchedulerRanToday(String schedulerName);
	
	public int updateSchedulerRanDate(String schedulerName);
	public String getStandardInterpretationCodeUsingDescription(
			Map<Object, Object> inputOutputParameterMap);
	public String checkSpecimenSourceNameExistance(String specimenSourceName);
	
	public String getCountyNameByCity(String _city);
	
	public int writeRawStdRecordDataToStdTable(EcrlsStdDocVO ecrlsStdDocVO);
	public int updateTheRawStdRecordToStandardize(int document_ID);
	public int updateTheRawRecordToStandardize(Map<Object, Object> inputOutputParameterMap);
	public int deleteDocumentInErrorTable(
			Map<Object, Object> inputOutputParameterMap);
	public Timestamp getDateValueFromString(String _dateValue);
	
	public List<Map<Object,Object>> getRawDocsList(Map<Object, Object> inputOutputParamters);
	public int storeDocumentMatchResultsToAIRSStandardTable(
			Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToMEDStandardTable(
			Map<String, Object> documentMatchData);
	public void storeDocumentMatchResultsToMEDProvidersStandardTable(
			Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToCCDStandardTable(
			Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToADAPStandardTable(
			Map<String, Object> documentMatchData);
	public String getFlagChoseProviderForADAP(
			Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToDEATHStandardTable(
			Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToNycDEATHStandardTable(
			Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToCASAStandardTable(
			Map<String, Object> documentMatchData);	
	public int writeErrorCodesMasterDetail(
			Map<Object, Object> inputOutputParamters);
	public int storeDocumentMatchResultsToEPRFOOCStandardTable(
			Map<String, Object> documentMatchData);
	public void checkDuplicatesinRawDocTable(
			Map<Object, Object> inputOutputParameterMap);
	public int storeDocumentMatchResultsToSSDMStandardTable(Map<String, Object> inputOutputParameterMap);
	public int storeDocumentMatchResultsToNycIJRStdTable(
			Map<String, Object> documentMatchData);
	public int checkAddressIsCanBeRes(Map<String, String> inputParamters);
	public List<String> getStandardSSDMRecordsData(
			Map<Object, Object> inputOutputParameterMap);
	public int storeDocumentMatchResultsToICCRStdTable(Map<String, Object> documentMatchData);
	
	
	/*CCD and HIE_DTH data*/
	public long getLastInsertedCCDRecordId(String recordType);
	
	public long getLastInsertedHIEDTHRecordId(String recordType);
	
	public List<Map<Object, Object>> getNewRawDiagnosisList();
	
	public List<Map<Object, Object>> getRawReviewedDiagnosisList();
	
	/**/
	public String getUnderCustodyForDinDoccs(
			Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToDinDoccsStandardTable(
			Map<String, Object> documentMatchData);
	
	public List<Map<String, Object>> getHivErrorsByCategoryAndCount();
	public int isSchedulerRanYesterday(String schedulerName);
	public int storeDocumentMatchResultsToTBStdTable(
			Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToSYPHStdTable(
			Map<String, Object> documentMatchData);
	public void updateSchedulerLogWithPidUpdateDate(
			Map<Object, Object> inOutParameterMap);
	public List<Map<String, Object>> getNyehmsDocumentCurrentStatus(
			Map<String, Object> inputParameterMap);
	public int updatePIDUpdatedDetailsInRaw(
			Map<String, Object> inputParameterMap);
	public int updatePIDUpdatedDetailsInStd(Map<String, Object> inputParameterMap);
	public int createNewRawHIVDocumentWithUpdatedPIDData(
			Map<String, Object> rawTableInputParameterMap);
	public int createNewStdHIVDocumentWithUpdatedPIDData(
			Map<String, Object> stdTableInputParameterMap);
	public String checkPersonAddressGotEdited(
			Map<String, Object> inputParameterMap);
	public int createNewStdStdDocumentWithUpdatedPIDData(
			Map<String, Object> stdTableInputParameterMap);
	public int createNewRawSTDDocumentWithUpdatedPIDData(
			Map<String, Object> rawTableInputParameterMap);
	public int logPIDProcessError(Map<String, Object> inputParameter);
	
	public List<Map<String, Object>> getMultiStepDocumentDetails(
			Integer multiStepDocumentId);
	public int checkIfDocumentUpdatedWithPidDetails(
			Map<String, Object> inputParameterMap);
	public List<Long> getErroredPIDDocumentsList(String recordType);
	public void updatePIDErrorDocumentStatus(String recordType);
	public void checkDuplicatesinStdDocTable(
			Map<String, String> inputParameterMap);
	//public List<Map<String, Object>> clericalReviewNonCaseDocumentDetails();
	//public List<Map<String, Object>> getMatchedNonCaseDocumentDetails();
	public void addFileNameToAssignmentReportsTable(Map<String, String> inputParameterMap);
	public int storeDocumentMatchResultsToSTDConfStdTable(
			Map<String, Object> documentMatchData);
	public Integer checkForSTDConfirmDocuments();
	public void updateTheMatchCuttoffForSTDConfClericals();
	public List<Map<String, Object>> getNonMatchMatchListWithRegistry();
	public void updateTheStatenoForSTDConfNonMatches(
			Map<String, Object> documentDetails);
	public void updateMatchCodeForLabDeathRecords(Map<String, String> inputParameters);
	public int storeDocumentMatchResultsToHL7StdTable(Map<String, Object> documentMatchData);
	public int storeDocumentMatchResultsToCOPHIStdTable(Map<String, Object> documentMatchData);
	public List<Map<String, String>> getDataFluxQSMatchSummary();
	public String getDocumentError(int documentId);
	public List<Map<String, String>> getDataFluxDetailedStatus(String CYCLE_TYPE);
	public List<Map<String, Object>> getAssignmentDetailedStatus();
	public List<Map<String, Object>> getAhiReportList();
	public List<Map<String, Object>> getPSMessageReport();
	public List<Map<String, Object>> getPSMessageNYCReport();
	public Map<String, String> getLoincCombinationData(Map<String, String> inputParameterMap);
	public Map<String, String> getLoincResultCombinationData(Map<String, String> inputParameterMap);
	public int removeMultiProcessStatus(Map<String, String> inputParameterMap);
	public void doHivLabUpdates();
}



