package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//import gov.ny.state.doh.bhae.hit.ccd.dom.CCDDocumentServiceImpl;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitLoadLogVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MultiStepDocumentsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.exception.NyehmsException;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ADAPDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.AIRSDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.BirthDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CASADocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentEditService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DeathDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DoccsDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.EPRFOOCDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ICCRDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.LabDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.MedicaidDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NycDeathDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NycIJRDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NycPRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.PRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.QualCareDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.SSDMDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.STDConfDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.SYPHDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.TBDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftFollowupDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;
/**
 * This class provides helper methods for document functions.
 * 
 * @author vxp06
 */
@Component
public class QuartzSchedulerTransactionHelper {

	private static Logger log = LoggerFactory.getLogger(QuartzSchedulerTransactionHelper.class);

	@Autowired private ManualQuartzJobService    manualQuartzJobService;
	@Autowired private AIRSDocumentService       airsDocumentService;
	@Autowired private PRFDocumentService        prfDocumentService;
	@Autowired private NycPRFDocumentService     nycPrfDocumentService;
	@Autowired private MedicaidDocumentService   medicaidDocumentService;
	@Autowired private ADAPDocumentService       adapDocumentService;
	@Autowired private DeathDocumentService      deathDocumentService;
	@Autowired private NycDeathDocumentService   nycDeathDocumentService;
	@Autowired private EPRFOOCDocumentService    ePRFOOCDocumentService;
	@Autowired private SSDMDocumentService       ssdmDocumentService;
	@Autowired private NycIJRDocumentService     nycIJRDocumentService;
	@Autowired private LabDocumentService        labDocumentService;
	@Autowired private DocumentTransactionHelper documentTransactionHelper;
	@Autowired private CASADocumentService       cASADocumentService;
	@Autowired private DoccsDocumentService      doccsDocumentService;
	@Autowired private TBDocumentService         tbDocumentService;
	@Autowired private SYPHDocumentService       syphDocumentService;
	@Autowired private STDConfDocumentService         stdConfDocumentService;
	@Autowired private CommonDocumentService     commonDocumentService;
	@Autowired private CommonDocumentEditService commonDocumentEditService;
	@Autowired private CommonService      commonService;
	@Autowired private BirthDocumentService birthDocumentService;
	@Autowired private ICCRDocumentService 		iccrDocumentService; 	
	@Autowired private QualCareDocumentService qualCareDocumentService;
	@Autowired LaftFollowupDocumentService laftFollowupDocumentService;	
	
	private Map<String, Map<String, String>> zipCodeReferenceMap = new HashMap<>();
	
	/**
	 * 
	 * This method validates new, non-duplicate rows of RAW_HIV_ECLRS_DOC
	 * (CURRENT_PROCESS_STATUS = 1)
	 * 
	 * If the document passes all the validations the document information is
	 * standardized and inserted into STD_HIV_ECLRS_DOC,
	 * and RAW_HIV_ECLRS_DOC.CURRENT_PROCESS_STATUS is updated to 5.
	 * 
	 * If the document fails the validations, CURRENT_PROCESS_STATUS is
	 * updated to 4 and the validation errors are inserted into
	 * NYEHMS_PROCESS_ERROR and NYEHMS_PROCESS_ERRORS_DETAIL tables.
	 * 
	 * @param uniqueRecordsList A List of EcrlsLabDocVO objects that have
	 * already passed the duplicate test.
	 * @param schedulerStatusObject The accumulator of status messages
	 * @param schedulerMetaData A Map containing LAB_SCHEDULER_PARAMETERS
	 * (PARAMTER_NAME, PARAMTER_VALUE).
	 * @param maxObxObservationResultKey The largest value of 
	 * obxObservationResultKey in this set of ECLRS rows.
	 */
	public void validateAndUpdateTheDocumentStatus(
			List<EcrlsLabDocVO> uniqueRecordsList,
			List<JSONObject> schedulerStatusObject,
			Map<String, String> schedulerMetaData,
			long maxObxObservationResultKey,
			List<String> schedulerNotificationObj)
	{
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
		String transactionMessage = null;
		StringBuilder failedToValidateDocuments = new StringBuilder("");
		int totalDocuments = uniqueRecordsList.size();

		if (totalDocuments == 0) { //No documents available to validate
			log.error("No data available to validate");
			return;
		}
		int failedDocumentsCount = 0 ;
		List<Integer> validDocumentIds = new ArrayList<>();
		JSONObject statusObject = new JSONObject();
		try{
			//preparing the Default process query conditions
			manualQuartzJobService.prepareDefaultProcessQueryConditions(schedulerMetaData);
			statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Validating unique Raw HIV data.");
			//Validating HIV documents one by one
			for (EcrlsLabDocVO ecrlsLabDocVO : uniqueRecordsList) {
				boolean validationStatus = manualQuartzJobService.handleRawEclrsRecordValidations(
						ecrlsLabDocVO,
						failedToValidateDocuments,
						schedulerMetaData);
				if(validationStatus){
					validDocumentIds.add(ecrlsLabDocVO.getDOCUMENT_ID());
				}
			}
			failedDocumentsCount = totalDocuments - validDocumentIds.size(); 
			if(totalDocuments > 0 && maxObxObservationResultKey != 0){
				manualQuartzJobService.updateDocumentProcessStatusLog(failedDocumentsCount , maxObxObservationResultKey);
			}
			transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
			transactionMessage = "Successfully performed the validation process for total documents "+totalDocuments;
		} catch (DataAccessException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "DataAccessException while validating raw hiv documents data:      "+e.getMessage();
			log.error(transactionMessage);
		}catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception while validating raw hiv documents data:      "+e.getMessage();
			log.error(transactionMessage);
		}
		schedulerNotificationObj.add("HIV   : "+validDocumentIds.size()+"  Documents moved from Raw to STD");
		if(failedDocumentsCount > 0){
			List<Map<String, Object>> validationFailedDocumentDetails= manualQuartzJobService.getHivErrorsByCategoryAndCount();
			StringBuilder errorsByCategory = new StringBuilder();
			errorsByCategory.append("HIV   : "+failedDocumentsCount+"  Documents failed the validation");
			errorsByCategory.append("<table style='margin:20px' border='1' cellpaddingspan='0' cellspacing='0'>");
			errorsByCategory.append("<tr  style='background:tan'><th width='400px'>Error Category</th><th width='50px'>Count</th></tr>");
			for (Map<String, Object> errorCategory : validationFailedDocumentDetails) {
				errorsByCategory.append("<tr><td>");
				errorsByCategory.append(errorCategory.get("ERRORDESC"));
				errorsByCategory.append("</td><td>");
				errorsByCategory.append(errorCategory.get("COUNT"));
				errorsByCategory.append("</td></tr>");	
			}
			errorsByCategory.append("</table>");
			schedulerNotificationObj.add(errorsByCategory.toString());
		}
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		statusObject.put("Total Documents", totalDocuments);
		statusObject.put("Total Valid Documents", validDocumentIds.size());
		if(!"".equals(failedToValidateDocuments.toString())){
			statusObject.put(CommonConstants.COMMENTS, failedToValidateDocuments);
		}
		schedulerStatusObject.add(statusObject);
	}

	/**
	 * Extracts new rows from ECLRS view vAllH (HIV) or vAllS (STD) based on
	 * the record in LAB_ECLRS_LOAD_LOG, then for each one inserts a row
	 * into RAW_HIV_ECLRS_DOC or RAW_STD_ECLRS_DOC, respectively.
	 * @param schedulerStatusObject The accumulator of processing status messages.
	 * @param origin One of "HIV ECLRS Data" or "STD ECLRS Data"
	 * @param recordType One of "HIV" or "STD"
	 * @param schedulerMetaData A Map containing LAB_SCHEDULER_PARAMETERS
	 * (PARAMTER_NAME, PARAMTER_VALUE).
	 * @return
	 */
	public List<EcrlsLabDocVO> copyDataFromEcrlsToNyehms(
			List<JSONObject> schedulerStatusObject,
			String origin,
			String recordType,
			Map<String, String> schedulerMetaData,
			List<String> schedulerNotificationObj, long lastInsertedRowId)
	{

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
		String transactionMessage = null;
		JSONObject statusObject = new JSONObject();
		List<EcrlsLabDocVO>  ecrlsLabDocList = null;

		try {
			
			log.error("Last inserted ECLRS id  {}",lastInsertedRowId);
			int fetchRowCount = Integer.parseInt(schedulerMetaData.get("ECLRS_RECORDS_FETCH_COUNT"));
			ecrlsLabDocList = manualQuartzJobService.getEclrsRawDatatoClean(
					recordType,
					lastInsertedRowId,
					fetchRowCount);
			if(ecrlsLabDocList.size() > 0){
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "Successfully rendered the "+origin+" and stored the information in NYEHMS ECLRS Log.";
			}else{
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "No new "+origin+" available in ECLRS database to process the records";
			}
			log.error(transactionMessage);
		} catch (DataAccessException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("DataAccessException while getting the {} from ECLRS :  {}",origin,transactionMessage);
		}catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception while getting the {} from ECLRS :  {}",origin,transactionMessage);
		}
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Get the "+origin+" from Eclrs and updating the PID information");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
		
		return writeEcrlsHIVDocsToNyehms(recordType, ecrlsLabDocList, origin , schedulerStatusObject, schedulerNotificationObj);
		
	}
	
	/**
	 * Method to read the UPHN_TRANX_IN table using JDBC so as to get a 
	 * ResultSet, presumed to do lazy loading to prevent too much data being
	 * read into memory at once. Currently necessary for CCD, unclear for
	 * HL7.
	 * @param c java.sql.Connection that has been opened for this purpose
	 * @param lastCheckedUtii The last UPHN_TRANX_IN_ID from UPHN_TRANX_IN of this 
	 * recordType that was read, in an earlier run.
	 * @param maxUtiiToCheck The highest UPHN_TRANX_IN_ID that we will check in
	 * this run.
	 * @param rowFetchLimit The maximum number of rows of UPHN_TRANX_IN of this
	 * recordType that we want to retrieve in this run.
	 * @param recordType One of "CCD", "HL7", "EXP"
	 * @return A ResultSet containing the results of this query.
	 */
	public ResultSet getUphnRowsWithJdbc(
			Connection c,
			int lastCheckedUtii,
			int maxUtiiToCheck,
			int rowFetchLimit,
			String recordType,
			HitLoadLogVO theNewHitLoadLogVO) {
		
		String ccdTopicAndClause = " and topic ='DEERPROD' and payloadname not like '%.csv' ";
		String expTopicAndClause = " and topic ='DEERPROD' and payloadname like '%.csv' ";
		String hl7TopicAndClause = " and topic = 'DEERHL7PROD' ";
		String topicAndClause = "";
		if ("CCD".equals(recordType)) {
			topicAndClause = ccdTopicAndClause;
		} else if ("HIE_DTH".equals(recordType)) {
			topicAndClause = expTopicAndClause;
		}else if ("HL7".equals(recordType)) {
			topicAndClause = hl7TopicAndClause;
		}
		String sqlString = 
				"with ordered_uphn_tranx_in as \n"
				+ "(select uphn_tranx_in_id, topic, payloadsize, payloadname,\n"
				+ "payloadbinarycontent, receivedtime \n"
				+ "from uphn_tranx_in order by uphn_tranx_in_id) \n "
				+ "select ordered_uphn_tranx_in.*, \n"
				+ "min(uphn_tranx_in_id) over(partition by 1 ) as minUtiiRetrieved, \n"
				+ "max(uphn_tranx_in_id) over(partition by 1 ) as maxUtiiRetrieved, \n"
				+ "count(*) over (partition by 1) rowRetrievedCount\n"
				+ " from ordered_uphn_tranx_in"
				+ " where uphn_tranx_in_id > " + lastCheckedUtii
				+ "\n and uphn_tranx_in_id <= " + maxUtiiToCheck 
				+ topicAndClause
				+ "\n and rownum <= " + rowFetchLimit
				+ "\n order by uphn_tranx_in_id ASC";
		log.error(sqlString);
//		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = c.createStatement();
			rs = s.executeQuery(sqlString);
			log.error("Obtained ResultSet from UPHN_TRANX_IN from {} to {}  for document type {} with fetch limit {}",lastCheckedUtii,maxUtiiToCheck,recordType,rowFetchLimit);
		} catch (SQLException e) {
			// ?CommonExceptionHandler?
		} catch (Exception e) {
			// ?CommonExceptionHandler?
		}
		return rs;
	}
	
	
	public void copyReAssignHivDocDataFromEcrlsToNyehms(List<JSONObject> schedulerStatusObject, String origin, String recordType, List<String> schedulerNotificationObj){
		
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
		String transactionMessage = null;
		
		JSONObject statusObject = new JSONObject();
		
		List<EcrlsLabDocVO>  ecrlsLabDocList = new ArrayList<>();
		
		try {
			//List<EcrlsReAssignDocs> reAssignHivDocList = manualQuartzJobService.getReAssignDocList(recordType);
			
			//if(reAssignHivDocList.size() > 0){
			ecrlsLabDocList = manualQuartzJobService.getReAssignDocuments( recordType);
			//}
			
			if(ecrlsLabDocList.size() > 0){
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "Successfully rendered the Reassigned ECLRS data.";
			}else{
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "No Reassigned Ecrls data available";
			}
			
			log.error(transactionMessage);
			
		} catch (DataAccessException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("DataAccessException while getting the data from ECLRS :  {}",transactionMessage);
		}catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception while getting the data from ECLRS :  {}",transactionMessage);
		}
		
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Get the "+origin+" data and updating the Reassign table");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		
		schedulerStatusObject.add(statusObject);
		
		writeEcrlsHIVDocsToNyehms(recordType, ecrlsLabDocList, origin, schedulerStatusObject, schedulerNotificationObj);
		
		
	}
	
	/**
	 * 
	 * @param schedulerStatusObject
	 * @param origin
	 * @param recordType
	 * @return
	 */
	public List<EcrlsLabDocVO> copyDataFromNyehms(
			List<JSONObject> schedulerStatusObject,
			String origin,
			String recordType)
	{
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
		String transactionMessage = null;
		JSONObject statusObject = new JSONObject();
		List<EcrlsLabDocVO> ecrlsLabDocList = new ArrayList<>();
		try{
			ecrlsLabDocList = manualQuartzJobService.runSchedulerFromNyehms();
			if(ecrlsLabDocList.size() > 0){
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "Successfully rendered the Failed and Reprised Raw Documents.";
			}else{
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "No Raw data available with Failed and Reprised document status.";
			}
			log.error(transactionMessage);
		} catch (DataAccessException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("DataAccessException while getting the data from ECLRS :  {}",transactionMessage);
		}catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception while getting the data from ECLRS :  {}",transactionMessage);
		}
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Get the "+origin+" from NYEHMS Raw table");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
		return ecrlsLabDocList;
	}
	
	
	

	/**
	 * Takes the List of ECLRS Value Objects from vAllH or vAllS and tries 
	 * to write each one to RAW_HIV_ECLRS_DOC or RAW_STD_ECLRS_DOC; if there
	 * is a failure during the INSERT then the VO is removed from the List.
	 * @param recordType One of "HIV" or "STD"
	 * @param ecrlsLabDocList The list of new EcrlsLabDocVO
	 * @param originFrom One of "HIV ECLRS Data" or "STD ECLRS Data"
	 * @param schedulerStatusObject
	 * @return  The same list of EcrlsLabDocVO as input, except that any 
	 * that failed to insert into the RAW_XXX_ECLRS_DOC table have been removed.
	 */
	public List<EcrlsLabDocVO> writeEcrlsHIVDocsToNyehms(
			String recordType,
			List<EcrlsLabDocVO>  ecrlsLabDocList,
			String originFrom,
			List<JSONObject> schedulerStatusObject,
			List<String> schedulerNotificationObj){
		
		JSONObject statusObject = new JSONObject();
		String transactionMessage = null;		
		List<String> failureDocuments = new ArrayList<>();
		if(ecrlsLabDocList == null || ecrlsLabDocList.size() == 0){
			log.error("No data available to write {} to NYEHMS raw table",originFrom);
			return ecrlsLabDocList;
		}
		Iterator<EcrlsLabDocVO> rawEclrsListIterator = ecrlsLabDocList.iterator();
		while (rawEclrsListIterator.hasNext()) {
			EcrlsLabDocVO ecrlsLabDocVO = rawEclrsListIterator.next();
			boolean transactionStatus=false;
			try {
				transactionStatus = writeRawEcrlsData(recordType, ecrlsLabDocVO, originFrom);
			} catch (DataAccessException e) {
				transactionStatus = false;
				transactionMessage = ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY()+" :  "+e.getMessage();
				failureDocuments.add(transactionMessage);
				log.error("unable to write the {} in NYEHMS Raw table :  {}",originFrom,transactionMessage);
			} catch (NyehmsException e) {
				transactionStatus = false;
				transactionMessage = ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY()+" :  "+e.getMessage();
				failureDocuments.add(transactionMessage);
				log.error("unable to write the {} in NYEHMS Raw table :  {}",originFrom,transactionMessage);
			}catch(Exception e){
				transactionStatus = false;
				transactionMessage = ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY()+" :  "+e.getMessage();
				failureDocuments.add(transactionMessage);
				log.error("unable to write the {} in NYEHMS Raw table :  {}",originFrom,transactionMessage);
			}
			 if(!transactionStatus){
				 rawEclrsListIterator.remove();
			 }
		}
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Writing the "+originFrom+" to Nyehms database.");
		if(failureDocuments.size() == 0){
			statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_SUCCESS);
			transactionMessage = "Successfully wrote all the "+originFrom+" to Nyehms database";
			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
			log.error(transactionMessage);
			
			schedulerNotificationObj.add(recordType+"  :  Successfully wrote "+originFrom+"("+ecrlsLabDocList.size()+") to Raw table");
		}else{
			statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_FAILURE);
			if(ecrlsLabDocList.size() == 0){
				transactionMessage = "All Documents were failed while writing "+originFrom+" to Raw table";
				log.error(transactionMessage);
				statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
				schedulerNotificationObj.add(recordType+"  :  Failed to write all the "+originFrom+" to Raw table");
				
			}else{
				transactionMessage = "Few Documents were failed while writing "+originFrom+" to Raw table";
				log.error(transactionMessage);
				statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
				
				schedulerNotificationObj.add(recordType+"  :  Successfully wrote "+originFrom+"("+ecrlsLabDocList.size()+") to Raw table");
				schedulerNotificationObj.add(recordType+"  :  Failed to write few "+originFrom+"("+failureDocuments.size()+") to Raw table");
				
			}
			statusObject.put(CommonConstants.FAILED_DOCUMENTS, failureDocuments.toString());
		}
		log.error(transactionMessage);
		schedulerStatusObject.add(statusObject);
		return ecrlsLabDocList;
	}
	
	/**
	 * Gets a new row in DOCUMENT_LOCATOR for either "HIV" or "STD" depending
	 * on recordType, sets the DOCUMENT_ID field based on the DOCUMENT_ID
	 * value of the new DOCUMENT_LOCATOR row, then inserts the VO into
	 * the appropriate table.
	 * 
	 * @param recordType One of "HIV" or "STD"
	 * @param ecrlsLabDocVO The Value Object containing one ECLRS row.
	 * @param originFrom One of "HIV ECLRS Data" or "STD ECLRS Data"
	 * @return True if the row was inserted successfully; false if not.
	 * @throws DataAccessException
	 * @throws NyehmsException
	 */
	@Transactional
	public boolean writeRawEcrlsData(
			String recordType,
			EcrlsLabDocVO  ecrlsLabDocVO,
			String originFrom)
					throws DataAccessException, NyehmsException
	{
		boolean transactionStatus = false;
		Map<Object, Object> map = new HashMap<>();
		if("STD".equals(recordType) || "STD_LAB".equals(recordType)){
			map.put("p_document_type", "STD_LAB");
		}else{
			
			String testType = manualQuartzJobService.getTestType(ecrlsLabDocVO.getOBXLOINCCODE());
			map.put("p_test_type", testType);
			map.put("p_document_type", "HIV_LAB");
		}
		manualQuartzJobService.getNewDocId(map);
		int newDocId = (Integer) map.get("p_new_doc_id");
		if(newDocId != 0){
			ecrlsLabDocVO.setDOCUMENT_ID(newDocId);
			int insertResultCount = manualQuartzJobService.writeRawEcrlsData(recordType, ecrlsLabDocVO);
			if(insertResultCount > 0){
				transactionStatus = true;
			}
		}
		if(CommonConstants.REASSIGN_ECLRS_DATA.equals(originFrom) && transactionStatus){
			manualQuartzJobService.updateReAssignDocProcessedStatus(ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY(), ecrlsLabDocVO.getOBROBSERVATIONREQUESTKEY());
		}
		return transactionStatus;
	}

//	/**
//	 * Gets the new row in DOCUMENT_LOCATOR for one CCD, then inserts
//	 * rows as appropriate into all the RAW_CCD_XXX tables.
//	 * @param theCCDVO The value object for one CCD from
//	 * UPHN_TRANX_IN.PAYLOAD_BINARY_CONTENT
//	 * @return
//	 * @throws DataAccessException If something happens in the MyBatis
//	 * database modification classes
//	 * @throws NyehmsException ?
//	 */
//	@Transactional
	
//	/**
//	 * Gets the new row in DOCUMENT_LOCATOR for one EXP, then inserts
//	 * rows as appropriate into all the RAW_HIT_DEXXX_DXX tables.
//	 * @param theEXPVO The value object for one EXP from
//	 * UPHN_TRANX_IN.PAYLOAD_BINARY_CONTENT
//	 * @return
//	 * @throws DataAccessException If something happens in the MyBatis
//	 * database modification classes
//	 * @throws NyehmsException ?
//	 */
//	@Transactional

	/**
	 * 
	 * @param ecrlsLabDocVO
	 * @param errors
	 * @param errorCodes
	 * @param stdEcrlsLabDocVO
	 * @param schedulerMetaData
	 * @return
	 */
	@Transactional
	public String validatingRawRecord(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes, StdEcrlsLabDocVO stdEcrlsLabDocVO, Map<String, String> schedulerMetaData){
	
		String errorMessageStatus = "";
		manualQuartzJobService.handleProcessStatusValidationErrors(ecrlsLabDocVO, errors, errorCodes, schedulerMetaData);
		manualQuartzJobService.handleDateValidation(ecrlsLabDocVO,errors, errorCodes, stdEcrlsLabDocVO);
		manualQuartzJobService.handleAddressValidation(ecrlsLabDocVO, errors, errorCodes, stdEcrlsLabDocVO);
		manualQuartzJobService.handleRaceEthnicityValidation(ecrlsLabDocVO, errors, errorCodes, stdEcrlsLabDocVO);
		manualQuartzJobService.handleDinValidation(ecrlsLabDocVO, stdEcrlsLabDocVO);
		manualQuartzJobService.handleAgeValidation(ecrlsLabDocVO, stdEcrlsLabDocVO);
		manualQuartzJobService.handleSSNValidation(ecrlsLabDocVO, stdEcrlsLabDocVO);
		manualQuartzJobService.handleLabDataValidation(ecrlsLabDocVO, errors, errorCodes, stdEcrlsLabDocVO, schedulerMetaData);
		
		if(errors.size() > 0){
			
			errorMessageStatus = "HARD";
			
		}
		 
		
		boolean havingSoftCheckValidationErrors = manualQuartzJobService.handleSoftCheckValidation(ecrlsLabDocVO, errors, errorCodes, stdEcrlsLabDocVO, schedulerMetaData);
		
		
		if(havingSoftCheckValidationErrors && "HARD".equals(errorMessageStatus)){
			errorMessageStatus = "HARD & SOFT";
		}else if(havingSoftCheckValidationErrors){
			errorMessageStatus = "SOFT";
		}
		
		
		return errorMessageStatus;
		
	}
	
	/**
	 * This method is used to validate the HIV record.
	 * 
	 * While validating the record first two conditions
	 * <ol>
	 * <li> Checking CLIA and PFI combination in the database 
	 * <li>Validating the HIV record against the Filter type coniditions
	 * </ol>
	 * If the document fails these two validations then its a hard stop and
	 * no further validations are performed. Update the Raw table with respective status 
	 * and loads the error into ERROR tables
	 * else the document will be validated against all the validations.
	 * If the document is failing the any validation then it will update Raw document with VALIDATION FAILED status (4) and loads the ERROR tables
	 * If the document passes all the validations it will move the record to Standard layer with "5" current_process_Status
	 * There is soft check validation: For the first time validation only we will log the Error, if the same document is coming for validation again (Once you reprise the document, it will be validate again) 
	 * then will ignore thosse soft check errors (Example: First name, last name contains invalid characters, Collection date to create date is over 60 days etc)
	 * If the document failed the validation 
	 * 
	 * @param ecrlsLabDocVO : hiv document
	 * @param failedToValidateDocuments : if we unable to validate the document against all the validations (Any exception while doing this method), this document will be added to the list
	 * @param schedulerMetaData
	 * @return
	 */
	@Transactional
	public boolean handleRawEclrsRecordValidations(EcrlsLabDocVO ecrlsLabDocVO, StringBuilder failedToValidateDocuments, Map<String, String> schedulerMetaData){
			boolean transactionStatus = false;
			List<String> errors =  new ArrayList<>();
			List<Integer> errorCodes =  new ArrayList<>();
			StdEcrlsLabDocVO stdEcrlsLabDocVO = new StdEcrlsLabDocVO();
			boolean havingSoftCheckValidationErrors = false;
			int currentProcessStatus = 0;
			int _documentID = ecrlsLabDocVO.getDOCUMENT_ID();
			
				if(ecrlsLabDocVO.getCURRENT_PROCESS_STATUS() == CommonConstants.RAW_RECORD_REPRISE_PROCESS_STATUS || ecrlsLabDocVO.getCURRENT_PROCESS_STATUS() == CommonConstants.RAW_RECORD_SOFT_REPRISE_PROCESS_STATUS){
					//if it is a reprised document then will inactive the record in the Error table.
					manualQuartzJobService.disableFlagInErrorTable(_documentID);
				}
				
				//Validating CLIA and PFI combination in the database
				boolean cliaAnaPFINotFound = manualQuartzJobService.validateCliaAndPFi(ecrlsLabDocVO, errors, errorCodes);
				if(!cliaAnaPFINotFound){
						String errorStatus = validatingRawRecord(ecrlsLabDocVO, errors, errorCodes, stdEcrlsLabDocVO, schedulerMetaData);
						
						if(errorStatus.indexOf("SOFT")  != -1){
							havingSoftCheckValidationErrors = true;
						}
						if(errors.size() > 0){
							
							if(log.isDebugEnabled()){
								log.debug("{}  has validation errors",_documentID);
							}
							currentProcessStatus = CommonConstants.RAW_RECORD_VALIDATION_ERRORS_PROCESS_STATUS;
						}else{
							
							currentProcessStatus = CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS;
							
						}
						
					
				}else{

			if(log.isDebugEnabled()){
				log.debug("{}  Terminating the validations. Its Clia PFI validation failed.",_documentID);
			}
			currentProcessStatus = CommonConstants.RAW_RECORD_CLIA_PFI_ERROR_PROCESS_STATUS;
		}
		if(currentProcessStatus != CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS){
			String errorDesc = errors.toString();
			errorDesc = errorDesc.replace("[", "");
			errorDesc = errorDesc.replace("]", "");
			int processErrorId = manualQuartzJobService.addValidationError(
					_documentID,
					errorDesc,
					currentProcessStatus,
					havingSoftCheckValidationErrors,
					ecrlsLabDocVO.getCURRENT_PROCESS_STATUS(),
					"HIV_LAB");
			manualQuartzJobService.writeErrorCodesMasterDetail(processErrorId, errorCodes);
			manualQuartzJobService.writeErrorCode(currentProcessStatus, _documentID, "HIV_LAB");
		}else if(currentProcessStatus == CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS){
			transactionStatus = moveRawDocumentToStd(ecrlsLabDocVO, stdEcrlsLabDocVO, CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS, CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS, transactionStatus);
		}
		return transactionStatus;
	}
	
	/**
	 * 
	 * @param ecrlsLabDocVO
	 * @param stdEcrlsLabDocVO
	 * @param currentProcessStatus
	 * @param transactionStatus
	 * @return
	 */
	@Transactional
	public boolean moveRawDocumentToStd(
			EcrlsLabDocVO ecrlsLabDocVO, 
			StdEcrlsLabDocVO stdEcrlsLabDocVO,
			int rawCurrentProcessStatus, int stdCurrentProcessStatus, 
			boolean transactionStatus)
	{
		int documentId = ecrlsLabDocVO.getDOCUMENT_ID();
		labDocumentService.writeStandardDataFromRaw(ecrlsLabDocVO, stdEcrlsLabDocVO);
		ecrlsLabDocVO.setCURRENT_PROCESS_STATUS(rawCurrentProcessStatus);
		stdEcrlsLabDocVO.setCURRENT_PROCESS_STATUS(stdCurrentProcessStatus);
		manualQuartzJobService.writeErrorCode(rawCurrentProcessStatus, documentId, "HIV_LAB");
		commonDocumentService.writeDocumentTestType(documentId, ecrlsLabDocVO.getTESTTYPE());
		int resultCount = labDocumentService.writeStandardDataToDatabase(stdEcrlsLabDocVO);
		if(resultCount > 0){
			String testType = ecrlsLabDocVO.getTESTTYPE();
			if (CommonConstants.TEST_TYPE_RESISTANCE.equalsIgnoreCase(testType)){

				resultCount = labDocumentService.insertStandardHIVResistanceResult(ecrlsLabDocVO.getDOCUMENT_ID(), ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXT(), ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY(), stdEcrlsLabDocVO.getCALC_COLLECTION_DATE());
				
			}
			transactionStatus = true;
		}
		return transactionStatus;
	}
	
	/**
	 * 
	 * @param recordType
	 * @param schedulerStatusObject
	 * @param statusObject
	 * @param duplicateColumns
	 * @return
	 */
	@Transactional
	public String checkForDuplicates(
			String recordType,
			List<JSONObject> schedulerStatusObject,
			JSONObject statusObject,
			String duplicateColumns)
	{
		log.error("Started checking the duplicates in Raw {} records",recordType);
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
		String _hashNumberGeneratingStatus = manualQuartzJobService.generateHashNumberForNewRecords(recordType, schedulerStatusObject, duplicateColumns);
		if(!CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(_hashNumberGeneratingStatus )){
			throw new DataRetrievalFailureException("Failed to generate the Hash unique number for new Raw "+recordType+" Records. Stopped the Current scheduler completely");
		}
		transactionStatus = manualQuartzJobService.checkDuplicatesinRawDocTable(recordType, schedulerStatusObject);
		return transactionStatus;
	}
	
	/**
	 * 
	 * @param recordType
	 * @param schedulerStatusObject
	 * @param statusObject
	 * @return
	 */
	@Transactional
	public String checkAndUpdateMultiStepHIVDocuments(
			String recordType,
			List<JSONObject> schedulerStatusObject,
			JSONObject statusObject)
	{
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;
		log.error("Started checking multi step documents");
		StringBuilder successComments = new StringBuilder();
		List<String> errors = new ArrayList<>();
		int resultCount = manualQuartzJobService.changeDocumentsStatusToMultiStepCompressFlag();
		log.error("multi step documents count {}",resultCount);
		
		
		resultCount =  manualQuartzJobService.removeMultiProcessStatus();
		log.error("Removed multi step process status and set the status to 3 count {}",resultCount);
		
		
		//if(resultCount > 0){
			List<MultiStepDocumentsVO>  multiStepDocuments = manualQuartzJobService.getMultiStepProcessDocumentDetails();
			
			for (MultiStepDocumentsVO multiStepDocumentsVO : multiStepDocuments) {
				
				List<EcrlsLabDocVO> multiStepDocumentsForPFI = multiStepDocumentsVO.getMultiStepDocuments();
				
				
				
				String errorMessage = processMultispotDocuments(multiStepDocumentsForPFI, errors, 0, 0);
				
				if(StringUtils.isNotBlank(errorMessage)) {
					continue;
				}
				
			}
			
			
		transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		if(!"".equals(successComments.toString())){
			statusObject.put("Transaction Messages", successComments.toString());
		}
		if(errors.size() > 0){
			statusObject.put("Errors", errors.toString());
		}
		schedulerStatusObject.add(statusObject);
		return transactionStatus;
	}
	
	
	public String processMultispotDocuments(List<EcrlsLabDocVO> multiStepDocumentsForPFI, List<String> errors, int retainDocumentId, int loincGroupId) {
		TreeSet<String> loincCodes = new TreeSet<>();
		List<Integer> documentIds = new ArrayList<>();
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		Integer newDocumentId = null; 
		
		EcrlsLabDocVO ecrlsLabDocVO = null;
		
		for (EcrlsLabDocVO _ecrlsLabDocVO : multiStepDocumentsForPFI) {
			
			loincCodes.add(_ecrlsLabDocVO.getOBXLOINCCODE());
			
			inputParameterMap.put("PFI", _ecrlsLabDocVO.getMSHLABPFI());
			documentIds.add(_ecrlsLabDocVO.getDOCUMENT_ID());
			
			if(retainDocumentId == _ecrlsLabDocVO.getDOCUMENT_ID()) {
				ecrlsLabDocVO = _ecrlsLabDocVO;
			}
			
			
		}
		
		
		
		int index = 1;
				
		for (String loincCode : loincCodes) {
			inputParameterMap.put("LOINC_"+index, loincCode);
			inputParameterMap.put(loincCode, Integer.toString(index));
			
			index++;
		}
		
		Map<String, String> pfiLoincData = manualQuartzJobService.getLoincCombinationData(inputParameterMap);
		
		if(pfiLoincData == null || pfiLoincData.size() == 0) {
			
			String errorMessage = "Loinc pfi combination doesn't exist. Loinc Codes:  "+loincCodes.toArray()+"  pfi: "+inputParameterMap.get("PFI"); 
			
			log.error(errorMessage);
			
			return errorMessage;
		}
		
		int groupCount = Integer.parseInt(pfiLoincData.get("GROUP_COUNT"));
		String newDocumentFlag = pfiLoincData.get("NEW_DOCUMENT");
		String activeOldDocumentFlag = pfiLoincData.get("DONT_RETIRE_OLD");
		String processTogetherFlag = pfiLoincData.get("PROCESS_TOGETHER");
		
		if(loincCodes.size() < groupCount) {
			
			String errorMessage = "Documents are missing to process";
			log.error(errorMessage);
			return errorMessage;
		}
		
		if("Y".equals(newDocumentFlag)) {
			
			boolean resultCodeError = false;
			
			for (EcrlsLabDocVO _ecrlsLabDocVO : multiStepDocumentsForPFI) {
				String resultCode = null;
				
				resultCode = getResultStandardCode(_ecrlsLabDocVO, errors);
				
				if(resultCode == null) {
					resultCodeError = true;
				}
				
				inputParameterMap.put("TEST_RESULT_"+inputParameterMap.get(_ecrlsLabDocVO.getOBXLOINCCODE()), resultCode);
			}
			
			if(resultCodeError && errors.size() > 0) {
				log.error(errors.get(0));
				return errors.get(0);
			}
			
			
			Map<String, String> resultData = manualQuartzJobService.getLoincResultCombinationData(inputParameterMap);
			
			if(resultData == null || resultData.size() == 0) {
				String errorMessage = "Loinc and PFI combination doesn't exist";
				log.error(errorMessage);
				return errorMessage;
			}
			
			if(ecrlsLabDocVO == null || retainDocumentId == 0) {
				ecrlsLabDocVO = multiStepDocumentsForPFI.get(0);
			}
			
			if(CommonUtil.isNotNullAndEmpty(resultData.get("NEW_LOINC_CODE"))) {
				ecrlsLabDocVO.setOBXLOINCCODE(resultData.get("NEW_LOINC_CODE"));
			}
			if(CommonUtil.isNotNullAndEmpty(resultData.get("NEW_LOINC_DESC"))) {
				ecrlsLabDocVO.setOBXLOINCDESC(resultData.get("NEW_LOINC_DESC"));
			}
			if(CommonUtil.isNotNullAndEmpty(resultData.get("NEW_LOINC_DESC_SHORT"))) {
				ecrlsLabDocVO.setOBXLOINCDESCSHORT(resultData.get("NEW_LOINC_DESC"));
			}
			if(CommonUtil.isNotNullAndEmpty(resultData.get("FINAL_TEST_RESULT"))) {
				ecrlsLabDocVO.setOBXOBSERVATIONRESULTTEXT(resultData.get("FINAL_TEST_RESULT"));
			}

			ecrlsLabDocVO.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_UNIQUE_RECORD_PROCESS_STATUS);
			boolean  newDocumentCreateStatus = manualQuartzJobService.createNewDocAndWriteRawEcrlsData("HIV_LAB", ecrlsLabDocVO);
			if(!newDocumentCreateStatus){
				throw new DataRetrievalFailureException("Unable to create new multi step document for PFI :  "+inputParameterMap.get("PFI"));
			}
			
			newDocumentId = ecrlsLabDocVO.getDOCUMENT_ID();
					
			int updatedDocumentsCount = manualQuartzJobService.associateParentDocumentForMultiStepDocuments(documentIds, newDocumentId, activeOldDocumentFlag);
			if(updatedDocumentsCount != documentIds.size()){
				 throw new DataRetrievalFailureException("Unable to associate the parent documents Id for Geenius document created. PFI :  "+inputParameterMap.get("PFI"));
			}
			
			if("N".equals(activeOldDocumentFlag)) {
				updatedDocumentsCount = labDocumentService.updateStatusCodeForHivDocuments(documentIds, CommonConstants.RAW_RECORD_MULTISTEP_DELETED_PROCESS_STATUS);
				if(updatedDocumentsCount != documentIds.size()){
					 throw new DataRetrievalFailureException("Unable to update documents("+documentIds+") status to 19");
				}	
			}else {
				updatedDocumentsCount = labDocumentService.updateStatusCodeForHivDocuments(documentIds, CommonConstants.RAW_UNIQUE_RECORD_PROCESS_STATUS);
				if(updatedDocumentsCount != documentIds.size()){
					 throw new DataRetrievalFailureException("Unable to move documents("+documentIds+") to Standard layer");
				}
				
			}
			
			
		}else if("Y".equals(processTogetherFlag)) {
			
			int updatedDocumentsCount = labDocumentService.updateStatusCodeForHivDocuments(documentIds, CommonConstants.RAW_UNIQUE_RECORD_PROCESS_STATUS);
			if(updatedDocumentsCount != documentIds.size()){
				 throw new DataRetrievalFailureException("Unable to move documents("+documentIds+") to Standard layer");
			}
			
		}
		
		return null;
	}
	

	/**
	 * This method is used to Standardize the unstandardized Raw provider addresses
	 * and RAW_facilities data
	 * 
	 * @param schedulerStatusObject : track the each activity status (Success/Failure
	 * documents processed , documents failed, comments, messages, etc)
	 * @param schedulerMetaData : contains metadata(Scheduler metadata loaded from
	 * LAB_SCHEDULER_PARAMETERS) required for the JOB
	 */
	public void mapProviderFacilityQsResults(
			List<JSONObject> schedulerStatusObject,
			Map<String, String> schedulerMetaData,
			List<String> schedulerNotificationObj)
	{
		String transactionMessage = null;
		JSONObject finalStatusObject = new JSONObject();
		
		//Loading the Unstandardized RAW facilities data to a file in JAROT
		String transactionStatus = manualQuartzJobService.loadDataToReferenceFile(
				schedulerStatusObject,
				schedulerMetaData,
				"ORPHAN_RAW_FACILITIES_SQL",
				"ORPHAN_RAW_FACILITIES_FILE_PATH",
				"Loading Orphan Raw Facilities",
				"ORPHAN_RAW_FACILITIES_RECORD_LENGTH",
				"ORPHAN_RAW_FACILITY_REFERENCE_ID_POSITION",
				null, false);
		
		if(CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)){
			//Loading the standardized Sub Facility data to a file in JAROT
			transactionStatus = manualQuartzJobService.loadDataToReferenceFile(
					schedulerStatusObject,
					schedulerMetaData,
					"REFERENCE_SUB_FACILITIES_SQL",
					"REFERENCE_SUB_FACILITIES_FILE_PATH",
					"Loading Standard Sub Facilities",
					"REFERENCE_SUB_FACILITIES_RECORD_LENGTH",
					"REFERENCE_SUB_FACILITY_REFERENCE_ID_POSITION",
					null, false);
		}
		if(CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)){
			//Loading the Unstandardized RAW Providers data to a file in JAROT
			transactionStatus = manualQuartzJobService.loadDataToReferenceFile (
					schedulerStatusObject,
					schedulerMetaData,
					"ORPHAN_RAW_PROVIDERS_SQL",
					"ORPHAN_RAW_PROVIDERS_FILE_PATH",
					"Loading Orphan Raw Providers",
					"ORPHAN_RAW_PROVIDERS_RECORD_LENGTH",
					"ORPHAN_RAW_PROVIDER_REFERENCE_ID_POSITION",
					null, false);
		}
		if(CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)){
			//Loading the standardized Providers address data to a file in JAROT
			transactionStatus = manualQuartzJobService.loadDataToReferenceFile(
					schedulerStatusObject,
					schedulerMetaData,
					"REFERENCE_SUB_PROVIDERS_SQL",
					"REFERENCE_SUB_PROVIDERS_FILE_PATH",
					"Loading Standard Sub Providers",
					"REFERENCE_SUB_PROVIDERS_RECORD_LENGTH",
					"REFERENCE_SUB_PROVIDER_REFERENCE_ID_POSITION",
					null, false);
		}
		if(CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)){
			//Calling the Quality stage  job process
			manualQuartzJobService.callingShellProcess(
					schedulerStatusObject,
					schedulerMetaData,
					"PROVIDER_FACILITY_QS_PROCESS",
					"Provider and Facility Quality stage process",
					schedulerNotificationObj);
		}
		if(CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)){
			JSONObject statusObject = new JSONObject();
			//Checking the Quality Job status
			statusObject = manualQuartzJobService.checkQualityStageJobStatus(
					schedulerMetaData,
					"QUALITY_STAGE_PROV_FAC_RESULTS_LOGS_PATH",
					"QUALITY_STAGE_PROV_FAC_RESULTS_LOG_NAME",
					"Provider and Facility mapping results",
					"PROV_FACI_QUALITY_STAGE_SCHEULER_SUCCESS_MESSAGE",
					"PROVIDER_FAILITY_MATCH_WAIT_TIME",
					statusObject,
					"");
			schedulerStatusObject.add(statusObject);
			transactionStatus = statusObject.get(CommonConstants.TRANSACTION_STATUS).toString();
			log.error("Quality stage job status {}",transactionStatus);
			//If the process is successful
			if(CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)){
				Map<Integer, List<Integer>> subToRawMapping = new HashMap<>();
				//Loading the Standardized Raw PRovider data in to subToRawMapping object
				boolean isError = manualQuartzJobService.loadRawProviderOrFacilityMappingResults(
						schedulerStatusObject, schedulerMetaData, subToRawMapping,
						"PROVIDER_MAPPING_RESULTS_FILE_NAME", "Raw Provider results",
						405, 831, CommonConstants.QS_PROVIDER_MAPPING_RECORD_LENGTH);
				String standardizingRawProviderRecordsStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
				String standardizingRawFacilityRecordsStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
				if(!isError){
					//Updating the Sub Provider address record in the Raw Provider address table
					standardizingRawProviderRecordsStatus = standardizingRawRecordsInNyehms(
							schedulerStatusObject, subToRawMapping,
							"Standardizing Raw Provider Addresses with Sub Provider Adresses",
							"PROVIDER");
				}
				subToRawMapping = new HashMap<Integer, List<Integer>>();
				//Loading the Standized Raw PRovider data in to subToRawMapping object
				isError = manualQuartzJobService.loadRawProviderOrFacilityMappingResults(
						schedulerStatusObject, schedulerMetaData, subToRawMapping,
						"FACILITY_MAPPING_RESULTS_FILE_NAME", "Raw Facility results",
						181, 378, CommonConstants.QS_FACILITY_MAPPING_RECORD_LENGTH);
				if(!isError) {
					//Updating the Sub Facility id in the Raw Facility table
					standardizingRawFacilityRecordsStatus = standardizingRawRecordsInNyehms(
							schedulerStatusObject, subToRawMapping,
							"Standardizing Raw Facility with Sub Facility Details",
							"FACILITY");
				}
				if(CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(standardizingRawFacilityRecordsStatus)
						&& CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(standardizingRawProviderRecordsStatus)){
					transactionMessage = "Succesfully standardized the Raw records.";
					transactionStatus =  CommonConstants.TRANSACTION_STATUS_SUCCESS;
				} else {
					transactionMessage = "Problems occured while standardizing the Raw records.";
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
				}
			}else{
				transactionMessage = "Error occured while checking the Raw Provider and Facility matching process.";
			}
		}else{
			transactionMessage = "Stopped the Provider and Facility mapping process, due to Exceptions.";
		}
		log.error(transactionMessage);
		//Storing the current status SUCCESS/FAILURE
		finalStatusObject.put(CommonConstants.STATUS_ACTION_NAME, "Final status of Provider and Facility mapping process");
		finalStatusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		finalStatusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(finalStatusObject);
	}

	/**
	 * 
	 * This method is used to Standardize the Raw PRovider/Facility records with  Sub Facility/PRovider ids
	 * 
	 * @param schedulerStatusObject : : track the each and every activity status (Success/Failure
	 *            documents processed , documents failed, comments, messages
	 *            etc)
	 * @param subToRawMapping : Which is holding the Sub PRovider/Facility record id to Raw Provider/Facilities ids
	 * @param actionName : current action name
	 * @param moduleName : Facility/Provider
	 * @return SUCCESS/FAILURE
	 */
	public String standardizingRawRecordsInNyehms(
			List<JSONObject> schedulerStatusObject,
			Map<Integer, List<Integer>> subToRawMapping,
			String actionName, String moduleName)
	{
		List<String> failureComments = new ArrayList<>();
		JSONObject statusObject = new JSONObject();
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;
		
		for (Integer subProviderAddressID : subToRawMapping.keySet()) {
			manualQuartzJobService.storingStandardizeRawResultsInDatabase(subProviderAddressID, subToRawMapping.get(subProviderAddressID), failureComments, moduleName);
		}
		int failedDocumentsSize = failureComments.size();
		int totalDocumentsSize = subToRawMapping.size(); 
		if(totalDocumentsSize == 0){
			transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
			transactionMessage = "There is no Raw "+moduleName+" data to Standardize.";
		}else if(failedDocumentsSize == totalDocumentsSize){
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Unable to Standardize all the Raw "+moduleName+" records with Sub "+moduleName+" id";
		}else if(failedDocumentsSize > 0 && totalDocumentsSize > 0){
			transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
			transactionMessage = "Unable to Standardize few the Raw "+moduleName+" records with Sub "+moduleName+" id";
		}else if(failedDocumentsSize == 0 && totalDocumentsSize > 0){
			transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
			transactionMessage = "Successfully Standardized all the Raw "+moduleName+" records with Sub "+moduleName+" id";
		}
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Storing Raw Standardize records");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
		
		return transactionStatus;
	}

	/**
	 * 
	 * @param rawEcrlsLabDocList
	 * @param schedulerStatusObject
	 * @param schedulerNotificationObj
	 */
	public void writeRawStdRecordsToStdLayer(
			List<EcrlsLabDocVO> rawEcrlsLabDocList,
			List<JSONObject> schedulerStatusObject,
			List<String> schedulerNotificationObj)
	{
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "Successfully stored all the Raw Std records in the Standard layer";
		List<String> failedToValidateDocuments = new ArrayList<>();
		int totalDocuments = rawEcrlsLabDocList.size();
		log.error("Writing Raw Std data to Std table");

		if (totalDocuments == 0) {
			log.error("No data available to store in Standard Std table.");
			return;
		}
		List<Integer> validDocumentIds = new ArrayList<>();
		JSONObject statusObject = new JSONObject();
		statusObject.put(CommonConstants.STATUS_ACTION_NAME,
				"Storing unique Raw STD data in Standard table.");
		for (EcrlsLabDocVO ecrlsLabDocVO : rawEcrlsLabDocList) {
			try {
				int insertStatus = writeRawStdRecordDataToStdTable(ecrlsLabDocVO);
				if(insertStatus == 0){
					throw new DataRetrievalFailureException("DataAccessException while storing the STD record");
				}
			} catch (DataAccessException e) {
				String _exceptionMessage = ecrlsLabDocVO.getDOCUMENT_ID()
						+" DataAccessException while storing the STD record :  "
						+e.getMessage();
				failedToValidateDocuments.add(_exceptionMessage.toString());
				log.error(_exceptionMessage);
			} catch (Exception e) {
				String _exceptionMessage = ecrlsLabDocVO.getDOCUMENT_ID()
						+" Exception while storing the STD record :  " 
						+e.getMessage();
				failedToValidateDocuments.add(_exceptionMessage.toString());
				log.error(_exceptionMessage);
			}
		}
		if(failedToValidateDocuments.size() == totalDocuments){
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "All the documents were failed while storing all the Raw Std records in the Standard layer";
			schedulerNotificationObj.add("STD  :  All documents("+totalDocuments+") moved from Raw to STD");
		} else if(failedToValidateDocuments.size() > 0){
			transactionMessage = "A few documents failed to be stored in the Standard layer";
			schedulerNotificationObj.add("STD  :  "
			+(totalDocuments-failedToValidateDocuments.size())
			+" documents moved from Raw to STD");
			schedulerNotificationObj.add("STD  :  "
			+ failedToValidateDocuments.size()
			+ " documents failed while moving from Raw to STD");
			statusObject.put(CommonConstants.COMMENTS, failedToValidateDocuments.toString());
		}
		log.error(transactionMessage);
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
	}
	
	/**
	 * 
	 * @param ecrlsLabDocVO
	 * @return
	 */
	@Transactional
	private int writeRawStdRecordDataToStdTable(
			EcrlsLabDocVO ecrlsLabDocVO)
	{
		int resultCount = manualQuartzJobService.writeRawStdRecordDataToStdTable(ecrlsLabDocVO);
		if(resultCount != 0){
			resultCount = manualQuartzJobService.updateTheRawStdRecordToStandardize(ecrlsLabDocVO.getDOCUMENT_ID());
		}
		return resultCount;
	}
	
	/**
	 * 
	 * @param docList A List of rows from the appropriate database RAW_XXX_DOC 
	 * table; each row as a simple Map<Object, Object> of the column names
	 * and values.
	 * @param schedulerStatusObject List to add return messages to
	 * @param documentType One of: "PRF", "AIRS", "MED", "ADAP", "DTH",
	 * "NYC_DTH", "CASA"
	 */
	public void writeRawRecordsToStdLayer(
			List<Map<Object, Object>> docList,
			List<JSONObject> schedulerStatusObject,
			String documentType,
			List<String> schedulerNotificationObj)
	{
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "Successfully stored all the Raw "+documentType+" records in the Standard layer";
		List<String> failedToValidateDocuments = new ArrayList<>();
		int totalDocuments = docList.size();
		JSONObject statusObject = new JSONObject();
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Storing Raw "+documentType+" data in Standard table.");
		if (totalDocuments == 0) {
			transactionMessage = "No new data available to store the data in Standard "+documentType+" table.";
			log.error(transactionMessage);
			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
			schedulerStatusObject.add(statusObject);
			return;
		}
		
		for (Map<Object, Object> docVO : docList) {
			try{
					writeRawRecordDataToStdTable(docVO, documentType);
			}catch (DataAccessException e) {
				String _exceptionMessage = docVO.get("DOCUMENT_ID")+" DataAccessException while storing the "+documentType+" record :  " +e.getMessage();
				failedToValidateDocuments.add(_exceptionMessage.toString());
				log.error(_exceptionMessage);
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String _exceptionMessage = docVO.get("DOCUMENT_ID")
						+" Exception while storing the "+documentType
						+" record :  " +e.getMessage()
				        + "Stacktrace: " + sw.toString();
				failedToValidateDocuments.add(_exceptionMessage.toString());
				log.error(_exceptionMessage);
			}
		}
		int totalDocumentsProcessed = totalDocuments-failedToValidateDocuments.size();
		schedulerNotificationObj.add(documentType+"   :  "+ totalDocumentsProcessed+" documents moved from Raw to STD");
		if(failedToValidateDocuments.size() == totalDocuments) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "All the documents were failed while storing all the Raw "+documentType+" records in the Standard layer";
			schedulerNotificationObj.add(documentType
					+"   :  All the documents("+totalDocuments
					+") failed while moving from Raw to STD");
		} else if(failedToValidateDocuments.size() > 0) {
			schedulerNotificationObj.add(documentType+"   :  "+ failedToValidateDocuments.size()+" documents failed while moving from Raw to STD");
			transactionMessage = "Few documents were failed while storing all the Raw "+documentType+" records in the Standard layer";
		}
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		statusObject.put(CommonConstants.TOTAL_RECORDS, totalDocuments);
		if(failedToValidateDocuments.size() > 0){
			statusObject.put(CommonConstants.FAILED_DOCUMENTS, failedToValidateDocuments);
			
		}
		statusObject.put("Documents processed", totalDocumentsProcessed);
		schedulerStatusObject.add(statusObject);
	}
	
	/**
	 * 
	 * @param docVO
	 * @param documentType
	 * @return
	 */
	@Transactional
	private int writeRawRecordDataToStdTable(Map<Object, Object> docVO, String documentType){
		//int resultCount = airsDocumentService.writeRawAIRSRecordDataToStdTable(docVO);
		// Sanjay Have to add code to writeRawCASADocDataToStdTable
		int resultCount = 0;
		BigDecimal documentId = (BigDecimal) docVO.get("DOCUMENT_ID");
		
		
		if(!"DOCCS".equals(documentType)){
			resultCount = manualQuartzJobService.documentInitialValidation(documentId, docVO, documentType);
			
			if(resultCount > 0){
				return resultCount;
			}
		}else{
			resultCount = doccsDocumentService.documentInitialValidation(documentId);
			
			if(resultCount > 0){
				throw new DataRetrievalFailureException("Invalid First Name and Last Name for the Doccs Document.");
			}
			
		}
		
		
		if("PRF".equals(documentType)){
			resultCount = prfDocumentService.validateAndWriteRawPRFRecordDataToStdTable(docVO, documentId);
		}else if("NYC_PRF".equals(documentType)){
			resultCount = nycPrfDocumentService.writeNYCPRFDocAndPartnersData(docVO, documentId);
		}else if("MED".equals(documentType) || "INS".equals(documentType)){
			resultCount = medicaidDocumentService.writeRawMedicaidRecordDataToStdTable(docVO, documentId);
		}else if("ADAP".equals(documentType)){
			resultCount = adapDocumentService.writeRawADAPRecordDataToStdTable(docVO, documentId);
		}else if("DTH".equals(documentType)){
			resultCount = deathDocumentService.writeRawDeathDocDataToStdTable(docVO, documentId, zipCodeReferenceMap);
		}else if("NYC_DTH".equals(documentType)){
			resultCount = nycDeathDocumentService.writeNycRawDeathDocDataToStdTable(docVO, documentId, zipCodeReferenceMap);
		}else if("EPRF_OOC".equals(documentType)){
			resultCount = ePRFOOCDocumentService.writeRawePRFOOCDocData(docVO, documentId);
		}else if("SSDM".equals(documentType)){
			resultCount = ssdmDocumentService.writeRawSSDMDocDataToStdTable(docVO, documentId);
		}else if("NYC_IJR".equals(documentType)){
			resultCount = nycIJRDocumentService.writeRawNycIJRDocDataToStdTable(docVO, documentId);
		}else if("CASA".equals(documentType)){
			resultCount = cASADocumentService.validateAndwriteRawCasaDocDataToStdTable(docVO, documentId);
		}else if("DOCCS".equals(documentType)){
			resultCount = doccsDocumentService.writeRawDoccsAndDinDocDataToStdTable(docVO, documentId);
		}else if("AIRS".equals(documentType)){
			resultCount = airsDocumentService.validateAndWriteRawAIRSRecordDataToStdTable(docVO, documentId);
		}else if("SYPH".equals(documentType)){
			resultCount = syphDocumentService.writeRawSYPHDocDataToStdTable(docVO, documentId);	
		}else if("TB".equals(documentType)){
			resultCount = tbDocumentService.writeRawTBDocDataToStdTable(docVO, documentId);
		}else if("STD_CONF".equals(documentType)){
			resultCount = stdConfDocumentService.writeRawSTDConfDocDataToStdTable(docVO, documentId);
		}else if("ICCR".equals(documentType)){
			resultCount = iccrDocumentService.writeRawICCRDocDataToStdTable(docVO, documentId);
		}else if("QUAL_CARE".equals(documentType)){
			resultCount = qualCareDocumentService.writeRawQualCareDocDataToStdTable(docVO, documentId);
		}
		
		if(resultCount != 0){
			resultCount = manualQuartzJobService.updateTheRawRecordToStandardize(
					documentId, documentType);
		}
		return resultCount;
	}

	/**
	 * 
	 * @param ecrlsLabDocVO
	 * @param errors
	 * @return
	 */
	@Transactional
	public String getResultStandardCode(EcrlsLabDocVO ecrlsLabDocVO,
			List<String> errors)
	{
		String obxObservationResultText = ecrlsLabDocVO
				.getOBXOBSERVATIONRESULTTEXT().trim().toUpperCase();
		String loincCode = ecrlsLabDocVO.getOBXLOINCCODE();
		List<Map<String, String>> resultObjs =
				manualQuartzJobService.getObxObservationResultsMapping(
				obxObservationResultText, loincCode, null);
		StringBuilder errorMessage = new StringBuilder("");
		List<Integer> errorCodes = new ArrayList<>();
		if (resultObjs == null || resultObjs.size() == 0) {
			errorMessage.append(" obxObservationresultText(")
			.append(obxObservationResultText)
			.append(") needs to be standardized for LOINC : ")
			.append(loincCode);
			errorCodes.add(29);
		} else if (resultObjs.size() > 1) {
			errorMessage.append(" Multiple Result standard codes assoicated to the LOINC(")
			.append(loincCode).append(") and obxObservationresultText( ")
			.append(obxObservationResultText).append(")");
			errorCodes.add(28);
		} else if (resultObjs.size() == 1) {
			Map<String, String> standardResults = resultObjs.get(0);
			if (standardResults.get("RESULTS_STD") != null) {
				return standardResults.get("RESULTS_STD");
			}
		}
		if(CommonUtil.isNotNullAndEmpty(errorMessage.toString())){
			int document_id = ecrlsLabDocVO.getDOCUMENT_ID();
			errors.add("DocumentId : "
					+ document_id
					+ errorMessage.toString());
			int processErrorId = manualQuartzJobService.addValidationError(
					document_id, errorMessage.toString(),
					CommonConstants.RAW_HIV_MULTISTEP_ERROR_DOCUMENT,
					false, null, "HIV_LAB");
			manualQuartzJobService.writeErrorCodesMasterDetail(
					processErrorId, errorCodes);
			manualQuartzJobService.writeErrorCode(
					CommonConstants.RAW_HIV_MULTISTEP_ERROR_DOCUMENT,
					document_id, "HIV_LAB");
		}
		return null;
	}

	/**
	 * 
	 * @param initiator
	 * @param schedulerStatusObject
	 * @param schedulerMetaData
	 * @param jobName
	 * @param finalStageOfLoadingResults
	 * @param schedulerNotificationObj
	 * @param needToArchieve
	 */
	public void loadQualityStageMatchResultsJob(
			String initiator, List<JSONObject> schedulerStatusObject,
			Map<String, String> schedulerMetaData, String jobName,
			boolean finalStageOfLoadingResults, List<String> schedulerNotificationObj
			, boolean needToArchieve)
	{
		//Sending notification mail that the Quality stage match results Process started
//		manualQuartzJobService.sendNotificationEmail(schedulerMetaData,
//				"START "+statusMailSubject.toString(),
//				"Successfully called the post match process",
//				CommonConstants.RESPONSE_CONTENT_TYPE_PLAIN);
		/**************************************
		 *                                    *
		 *  RUN THE POST-MATCH PROCESSING     *
		 *                                    * 
		 **************************************/
		
		List<Map<String, Object>> nonCaseDocumentDetails = new ArrayList<>();
		
		manualQuartzJobService.loadQualityStageDataJob(
				schedulerStatusObject, schedulerMetaData, jobName,
				finalStageOfLoadingResults, schedulerNotificationObj, true, nonCaseDocumentDetails);
//		if ("Y".equals(schedulerMetaData
//			//Step4, step5, Step6
//			mapProviderFacilityQsResults(
//			/* Send Email */
//			manualQuartzJobService.sendSchedulerDetailedProcessStatus( 
//					schedulerStatusObject, "END "+statusMailSubject.toString(),
	}

	
	public String writeAllRawRecordsToStdLayer(
			List<JSONObject> schedulerStatusObject, String documentType,
			List<String> schedulerNotificationObj) {
		
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "Successfully stored all the Raw "+documentType+" records in the Standard layer";
		
		JSONObject statusObject = new JSONObject();
		
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Storing Raw "+documentType+" data in Standard table.");
		
		
		
		boolean isError = false;
		
		int totalDocuments =0;
		
		try{
			
			ssdmDocumentService.documentInitialValidation();
			
			totalDocuments = ssdmDocumentService.writeAllRawSSDMDocDataToStdTable();
		//totalDocuments= 
		}catch (DataAccessException e) {
			
			isError = true; 
			String _exceptionMessage = " DataAccessException while storing the "+documentType+" records in STD Layer :  " +e.getMessage();
			
			log.error(_exceptionMessage);
		}catch (Exception e) {
			
			isError = true;
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String _exceptionMessage = 
					" Exception while storing the "+documentType
					+" records in STD Layer :  " +e.getMessage()
			        + "Stacktrace: " + sw.toString();
			
			log.error(_exceptionMessage);
		}
		
	
	
		if(isError){
			
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "All the documents were failed while storing Raw SSDM "+documentType+" records in the Standard layer";
			schedulerNotificationObj.add(documentType
					+"   :  All the documents("+totalDocuments
					+") failed while moving from Raw to STD");
			
			
		}else if(totalDocuments == 0){
			transactionMessage = "No new data available to store the data in Standard "+documentType+" table.";
			log.error(transactionMessage);
			
		}else{
			
			//transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "All the documents were failed while storing all the Raw "+documentType+" records in the Standard layer";
			schedulerNotificationObj.add(documentType
					+"   :  All the documents("+totalDocuments
					+") failed while moving from Raw to STD");
		}
	
		
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		
		statusObject.put("Total Documents processed", totalDocuments);
		
		schedulerStatusObject.add(statusObject);
		
		return transactionStatus;
	}

	public void checkDuplicatesInStdTable(List<JSONObject> schedulerStatusObject,
			String documentType, List<String> schedulerNotificationObj) {
		
		JSONObject statusObject = new JSONObject();

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;

		statusObject.put(CommonConstants.STATUS_ACTION_NAME,
				"Checking the duplicates for STD " + documentType + " Document");

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		boolean isError = false;
		
		try{
			
			
			manualQuartzJobService.checkDuplicatesinStdDocTable(documentType);

		//totalDocuments= 
		}catch (DataAccessException e) {
			
			isError = true; 
			String _exceptionMessage = " DataAccessException while checking the duplicates "+documentType+" records in STD Layer :  " +e.getMessage();
			
			log.error(_exceptionMessage);
		}catch (Exception e) {
			
			isError = true;
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String _exceptionMessage = 
					" Exceptio while checking the duplicates "+documentType+" records in STD Layer :   " +e.getMessage()
			        + "Stacktrace: " + sw.toString();
			
			log.error(_exceptionMessage);
		}

		
		if (isError) {
			transactionMessage = "Failed to perform the Duplicate process";
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			
		} else {
			transactionMessage = "Successfully formed the Duplicate process";
		}

		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

		schedulerStatusObject.add(statusObject);

	}

	public void copyPIDUpdatedRecordsToNyehms(
			List<JSONObject> schedulerStatusObject, String origin,
			String recordType, Map<String, String> schedulerMetaData,
			List<String> schedulerNotificationObj,
			long lastInsertedMaxObxObservationResultKey, Timestamp LAST_PIDUPDATEDATE, long currentMaxObxObservationResultKey) {
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
		String transactionMessage = null;
		JSONObject statusObject = new JSONObject();
		List<EcrlsLabDocVO>  ecrlsLabDocList = null;

		try {
			
			log.error("Last fetched PID Update date  {}",LAST_PIDUPDATEDATE);

			ecrlsLabDocList = manualQuartzJobService.getPIDUpdatedRecordsFromEclrs(
					recordType, LAST_PIDUPDATEDATE, lastInsertedMaxObxObservationResultKey, currentMaxObxObservationResultKey);
			
			
			
			if(ecrlsLabDocList != null && ecrlsLabDocList.size() > 0){
				
				log.error("successfully fetched the PID Update records from ECRLS. Total records {}",ecrlsLabDocList.size());
				
				manualQuartzJobService.writeEcrlsPidUpdatedRecordsToNyehms(recordType, ecrlsLabDocList, origin , schedulerStatusObject, schedulerNotificationObj);
				
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "Successfully rendered the PID Updated "+recordType+" records and written to NYEHMS Database.";
			}else{
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "No new PID Updated"+recordType+" records available in ECLRS database to process the records";
				
			}
			log.error(transactionMessage);
		} catch (DataAccessException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("DataAccessException while getting the PID Updated {} records from ECLRS : {}",recordType,transactionMessage);
		}catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception while getting the PID Updated {} records from ECLRS : {}",recordType,transactionMessage);
		}
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Get the "+origin+" from Eclrs and updating the PID information");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
		
		
		
	}
	
	public void loadPIDErrorDocumentsFromECLRSToNyehms(
			List<JSONObject> schedulerStatusObject, String origin,
			String recordType, Map<String, String> schedulerMetaData,
			List<String> schedulerNotificationObj) {
		
		
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
		String transactionMessage = null;
		
		JSONObject statusObject = new JSONObject();
		
		List<EcrlsLabDocVO>  ecrlsLabDocList = new ArrayList<>();
		
		try {
			List<Long> erroredDocumentsList = manualQuartzJobService.getErroredPIDDocumentsList(recordType);
			
			if(erroredDocumentsList.size() > 0){
				
				log.error("Total PID Error documents need to Process {}",erroredDocumentsList.size());
				
				ecrlsLabDocList = manualQuartzJobService.getErroredPIDDocumentsListFromEclrs(erroredDocumentsList, recordType);
				
				log.error("Fetched the PID Error documents data from ECLRS");
				
				manualQuartzJobService.writeEcrlsPidUpdatedRecordsToNyehms(recordType, ecrlsLabDocList, origin , schedulerStatusObject, schedulerNotificationObj);
			
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "Successfully Processed PID Error Documents.";
			}else{
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "There is no PID Error Documents found.";
			}
			
			log.error(transactionMessage);
			
		} catch (DataAccessException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("DataAccessException while getting the PID Updated data from ECLRS :  {}",transactionMessage);
		}catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception while getting the PID Updated data from ECLRS : {}",transactionMessage);
		}
		
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Get the PID Error "+origin+" from ECLRS and updating in NYEHMS Database");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		
		schedulerStatusObject.add(statusObject);
		
	}

	

	@Transactional
	public boolean writePIDUpdatedRawEcrlsData(String recordType,
			EcrlsLabDocVO ecrlsLabDocVO, String origin, List<String> successDocuments) throws DataAccessException, NyehmsException {
		
		Long OBXOBSERVATIONRESULTKEY = ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY();
		
		
		int resultCount = manualQuartzJobService.checkIfDocumentUpdatedWithPidDetails(recordType, OBXOBSERVATIONRESULTKEY);
		
		if(resultCount > 0){
		
			successDocuments.add(OBXOBSERVATIONRESULTKEY+"  : was already updated with PID details");
			
			return true;
		}
		
		
		
		
		List<Map<String, Object>> documentStatusDetails = manualQuartzJobService.getNyehmsDocumentCurrentStatus(recordType, OBXOBSERVATIONRESULTKEY);
		
		
		if(documentStatusDetails  == null || documentStatusDetails.size() == 0){
			
			
			log.error("{} : OBXOBSERVATIONRESULTKEY doesm't exist, creating as new Document ",OBXOBSERVATIONRESULTKEY);
			
			ecrlsLabDocVO.setCREATE_UID("900");
			ecrlsLabDocVO.setCREATE_DATE(new Date(Calendar.getInstance().getTimeInMillis()));
			
			boolean transactionStatus = writeRawEcrlsData(recordType, ecrlsLabDocVO, null);
			
			if(!transactionStatus){
			
				throw new DataRetrievalFailureException("Failed to insert the new Updated PID Record");
				
			}else{
				

				successDocuments.add(OBXOBSERVATIONRESULTKEY+"  : Successfully created as a new Document");
				
				log.error("{} : Successfully created as a new Document",OBXOBSERVATIONRESULTKEY);
				
			}
			
		}

		Map<Integer, Integer> currentProcessStatusDocumentIdMap = new HashMap<>();
		
		List<Integer> documentIdsList = new ArrayList<>();
		
		for (Map<String, Object> map : documentStatusDetails) {
		
			currentProcessStatusDocumentIdMap.put((Integer)map.get("CURRENT_PROCESS_STATUS_RAW"), (Integer)map.get("DOCUMENT_ID"));
			
			documentIdsList.add((Integer)map.get("DOCUMENT_ID"));
		}
		
		
		if(currentProcessStatusDocumentIdMap.containsKey(19)){
			
			Integer multiStepDocumentId = labDocumentService.getMultiStepDocumentId(currentProcessStatusDocumentIdMap.get(19));
			
			if(!documentIdsList.contains(multiStepDocumentId)){
				
				
				List<Map<String, Object>> multiStepDocumentDetails = manualQuartzJobService.getMultiStepDocumentDetails(multiStepDocumentId);
				
				documentStatusDetails.addAll(multiStepDocumentDetails);
				
				

				for (Map<String, Object> map : multiStepDocumentDetails) {
				
					currentProcessStatusDocumentIdMap.put((Integer)map.get("CURRENT_PROCESS_STATUS_RAW"), (Integer)map.get("DOCUMENT_ID"));
					
				}
				
			}
			
			
		}
		
		
		
		
		//RAW1.DOCUMENT_ID, RAW1.CURRENT_PROCESS_STATUS , STD.CURRENT_PROCESS_STATUS , STD.MATCH_CD
		
		Integer oldDocumentId = null;
		
		
		for (Map<String, Object> map : documentStatusDetails) {
			
			Integer documentId = (Integer)map.get("DOCUMENT_ID");
			
			Integer CURRENT_PROCESS_STATUS_RAW = (Integer)map.get("CURRENT_PROCESS_STATUS_RAW");
			
			Integer CURRENT_PROCESS_STATUS_STD = (Integer)map.get("CURRENT_PROCESS_STATUS_STD");
			
			Timestamp EHARS_IMPORT_DATE = (Timestamp)map.get("EHARS_IMPORT_DATE");
			
			String match_cd = (String)map.get("MATCH_CD");
			
			
			String PIDADDRESSLINE1 = (String)map.get("PIDADDRESSLINE1");
			
			String PIDADDRESSLINE2 = (String)map.get("PIDADDRESSLINE2");
			
			String PIDCITY = (String)map.get("PIDCITY");
			
			String PIDSTATE = (String)map.get("PIDSTATE");
			
			String PIDZIPCODE = (String)map.get("PIDZIPCODE");
			
			
			boolean isDataChanged = false;
			
			
			Map<String, Object> stdTableInputParameterMap = new HashMap<>();
			
			Map<String, Object> rawTableInputParameterMap = new HashMap<>();
			
			
			stdTableInputParameterMap.put("CURRENT_DOCUMENT_ID", documentId);
			
			rawTableInputParameterMap.put("CURRENT_DOCUMENT_ID", documentId);
			
			boolean isAllPersonDataBlank = false;
			
			
			if(CommonUtil.isNullOrEmpty(PIDADDRESSLINE1) && CommonUtil.isNullOrEmpty(PIDADDRESSLINE2) && CommonUtil.isNullOrEmpty(PIDCITY) && CommonUtil.isNullOrEmpty(PIDSTATE) && CommonUtil.isNullOrEmpty(PIDZIPCODE)){
				
				isAllPersonDataBlank = true;
				
			}
			
			
			//done
			
			if((CURRENT_PROCESS_STATUS_RAW == 1 || CURRENT_PROCESS_STATUS_RAW == 3 || CURRENT_PROCESS_STATUS_RAW == 4 || CURRENT_PROCESS_STATUS_RAW == 7 || CURRENT_PROCESS_STATUS_RAW == 11 
					|| CURRENT_PROCESS_STATUS_RAW == 6 || CURRENT_PROCESS_STATUS_RAW == 15 || CURRENT_PROCESS_STATUS_RAW == 30|| CURRENT_PROCESS_STATUS_RAW == 18)){
				
				isDataChanged = isPidUpdatedAddressSameAsNyehmsAddress(ecrlsLabDocVO, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, stdTableInputParameterMap, rawTableInputParameterMap);
				
				if(isDataChanged){
					manualQuartzJobService.updateRawDocumentData(recordType, CURRENT_PROCESS_STATUS_RAW, rawTableInputParameterMap);
					
					successDocuments.add(OBXOBSERVATIONRESULTKEY+"  : Successfully updated the PID Update details in RAW table");
					
				}else{
					successDocuments.add("Person Address not changed for "+OBXOBSERVATIONRESULTKEY);
					
					log.error("Person Address not changed for {}",OBXOBSERVATIONRESULTKEY);
				}
				
			}else if(CURRENT_PROCESS_STATUS_RAW == 2  || CURRENT_PROCESS_STATUS_RAW == 8 ){
				//done
				isDataChanged = isPidUpdatedAddressSameAsNyehmsAddress(ecrlsLabDocVO, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, stdTableInputParameterMap, rawTableInputParameterMap);
				
				if(isDataChanged){
					manualQuartzJobService.updateRawDocumentData(recordType, 1 , rawTableInputParameterMap);
					
					successDocuments.add(OBXOBSERVATIONRESULTKEY+"  : Successfully updated the PID Update details in RAW table and updated the Current Process Status to 1");
					
				}else{
					successDocuments.add("Person Address not changed for "+OBXOBSERVATIONRESULTKEY);
					
					log.error("Person Address not changed for {}",OBXOBSERVATIONRESULTKEY);
				}
				
			}
			else if(CURRENT_PROCESS_STATUS_RAW == 20 && currentProcessStatusDocumentIdMap.containsKey(5)){
				
				if(oldDocumentId == null || oldDocumentId > documentId){
					oldDocumentId =documentId;
				}
				
				continue;
			}else if(CURRENT_PROCESS_STATUS_RAW == 19 ){
				
				continue;
				
			}else if(CURRENT_PROCESS_STATUS_RAW == 20){
				//done
				continue;
			}
			
			
			else if(CURRENT_PROCESS_STATUS_RAW == 5){
				
				isDataChanged = isPidUpdatedAddressSameAsNyehmsAddress(ecrlsLabDocVO, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, PIDZIPCODE, stdTableInputParameterMap, rawTableInputParameterMap);
				
				if(!isDataChanged){
					
					successDocuments.add("Person Address not changed for "+OBXOBSERVATIONRESULTKEY);
					
					log.error("Person Address not changed for {}",OBXOBSERVATIONRESULTKEY);
					
					continue;
				}
				
				
				
				
				if(CURRENT_PROCESS_STATUS_STD == 5 || CURRENT_PROCESS_STATUS_STD == 14 || CURRENT_PROCESS_STATUS_STD == 17 || CommonUtil.isNullOrEmpty(match_cd) || "C".equals(match_cd) || "P".equals(match_cd) ){
					//done
					manualQuartzJobService.updateRawDocumentData(recordType, CURRENT_PROCESS_STATUS_RAW , rawTableInputParameterMap);
					
					manualQuartzJobService.updateStdPidDocumentData(recordType, CURRENT_PROCESS_STATUS_STD , stdTableInputParameterMap);
					
					successDocuments.add(OBXOBSERVATIONRESULTKEY+"  : Successfully updated the PID Update details in RAW and STD tables");
					
					continue;
					
				}else if(CURRENT_PROCESS_STATUS_STD == 10){
					
					
					String isOriginalPersonAddressIsSameWithEdited = null;
					
					if(currentProcessStatusDocumentIdMap.containsKey(20) && oldDocumentId != null){
						
						isOriginalPersonAddressIsSameWithEdited = manualQuartzJobService.checkPersonAddressGotEdited(recordType, oldDocumentId, documentId);
						
					}else{
						isOriginalPersonAddressIsSameWithEdited = "Y";
					}
					
					boolean editTheParentDocument = false;
					
					if((isAllPersonDataBlank || ("Y".equals(isOriginalPersonAddressIsSameWithEdited)) )  && EHARS_IMPORT_DATE == null){
						editTheParentDocument = true;
					}
					
					
					String documentType = commonDocumentService.getSubDocumentType(documentId);
					
					if(CommonUtil.isNullOrEmpty(documentType)){
						documentType = recordType;
					}
					
					String parentDocumentType = LabCommonUtils.getDocumentType(documentType);
					
					Map<Object, Object> inputOutputParameters = new HashMap<>();
					inputOutputParameters.put("p_document_type", parentDocumentType);
					inputOutputParameters.put("p_new_doc_id", "");	
					
					if(editTheParentDocument){
						inputOutputParameters.put("p_parent_doc_id", documentId);
					}
					
					
					try {
						commonDocumentService.getNewDocId(inputOutputParameters);
					} catch (DataAccessException e) {
						throw new DataRetrievalFailureException("Failed to create a new Lab document for "
								+documentId+"        :   "+e.getMessage());
					} 
					int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");
					
					
					if(!documentType.equals(parentDocumentType)){
						
						
						commonDocumentService.writeDocumentTestType(newDocumentId, documentType);
					}
					
					
					rawTableInputParameterMap.put("NEW_DOCUMENT_ID", newDocumentId);
					stdTableInputParameterMap.put("NEW_DOCUMENT_ID", newDocumentId);
					
					manualQuartzJobService.createNewRawDocumentWithUpdatedPIDData(recordType, rawTableInputParameterMap);
					
					manualQuartzJobService.createNewStdDocumentWithUpdatedPIDData(recordType, stdTableInputParameterMap);
					
					if(CommonConstants.TEST_TYPE_RESISTANCE.equals(documentType)){
						
						labDocumentService.insertStandardHIVResistanceResultFromOldDocument(newDocumentId, documentId);
					}
					
					
					UserObject userObject = new UserObject();
					
					userObject.setWorkerId(900);
					userObject.setUserId("NYEHMS");
					
					Map<Object, Object> personDocData =  commonDocumentService.getPersonDocData(documentId);
					
					
					if(editTheParentDocument){
						documentTransactionHelper.deleteStdDocumentExistence(documentId, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, "DISASSOC_EDIT",  userObject);
					}
					
					commonDocumentService.moveStdDocumentToDocAttrTables((String)personDocData.get("STATENO"), newDocumentId, userObject, parentDocumentType, null);
					
					if(editTheParentDocument){
					
						BigDecimal PS_ASSIGN_STATUS = (BigDecimal)personDocData.get("PS_ASSESS_STATUS");
						BigDecimal SURV_ASSESS_STATUS = (BigDecimal)personDocData.get("SURV_ASSESS_STATUS");
						BigDecimal SURV_ASSIGNMENT_ID = (BigDecimal)personDocData.get("SURV_ASSIGNMENT_ID");
						Map<String, Object> inputParameterMap = new HashMap<>();
						if(PS_ASSIGN_STATUS != null && PS_ASSIGN_STATUS.intValue() != 0){
							commonDocumentService.updateTriggeringDocumentIdInNyehmsMessage(documentId, newDocumentId);
							inputParameterMap.put("PS_ASSIGN_STATUS", PS_ASSIGN_STATUS);
						}
						
						String DISCARD_STATUS = (String)personDocData.get("DISCARD_STATUS");
						
						if(CommonUtil.isNotNullAndEmpty(DISCARD_STATUS)){
							
							commonDocumentService.createNonCaseWithNewDocument(documentId, newDocumentId);
							
							inputParameterMap.put("DISCARD_STATUS", DISCARD_STATUS);
						}
						
						laftFollowupDocumentService.updateLaftFollowupDocumentId(documentId, newDocumentId);
						
						if(SURV_ASSESS_STATUS != null && SURV_ASSESS_STATUS.intValue() == 1){
							inputParameterMap.put("SURV_ASSESS_STATUS", SURV_ASSESS_STATUS);
							inputParameterMap.put("SURV_ASSIGNMENT_ID", SURV_ASSIGNMENT_ID);
							List<Integer> assignmentIds = commonDocumentEditService.getAssignmentsCreatedForDocument(documentId);
							if(assignmentIds == null || assignmentIds.size()  == 0){
								throw new DataRetrievalFailureException("No Assignments found for the Triggering Document  "+documentId);
							}
							commonDocumentEditService.updateTriggeringDocumentIdInAssignments(assignmentIds, newDocumentId);
							commonDocumentEditService.addEditedAssignmentActivity(assignmentIds, userObject);
						}
						if(inputParameterMap.size() > 0){
							inputParameterMap.put("DOCUMENT_ID", newDocumentId);
							commonDocumentEditService.updatePersonDocAssociationData(inputParameterMap);
						}
						
						
						successDocuments.add(OBXOBSERVATIONRESULTKEY+"  : Successfully edited the existing document with new PID Update details in RAW and STD tables");
					
					}else{
						
						successDocuments.add(OBXOBSERVATIONRESULTKEY+"  : Successfully created a new Document with the new PID Update details.");
						
					}
					
					
				
				}
				
				
				
			}else {
				throw new DataRetrievalFailureException("There is no valid Process Status for this obxObservationResultKey");
			}
			
			
			
		}
		
		
		
		return true;
	}
	
	
	private boolean isPidUpdatedAddressSameAsNyehmsAddress(EcrlsLabDocVO ecrlsLabDocVO, String PIDADDRESSLINE1, String PIDADDRESSLINE2, String PIDCITY, String PIDSTATE, String PIDZIPCODE, Map<String, Object> stdTableInputParameterMap, Map<String, Object> rawTableInputParameterMap){
		
		if(!(CommonUtil.isNullOrEmpty(PIDADDRESSLINE1) ? CommonUtil.isNullOrEmpty(ecrlsLabDocVO.getPIDADDRESSLINE1()) :  PIDADDRESSLINE1.trim().equalsIgnoreCase(ecrlsLabDocVO.getPIDADDRESSLINE1().trim())) 
			|| !(CommonUtil.isNullOrEmpty(PIDADDRESSLINE2) ? CommonUtil.isNullOrEmpty(ecrlsLabDocVO.getPIDADDRESSLINE2()) :  PIDADDRESSLINE2.trim().equalsIgnoreCase(ecrlsLabDocVO.getPIDADDRESSLINE2().trim()))
				|| !(CommonUtil.isNullOrEmpty(PIDCITY) ? CommonUtil.isNullOrEmpty(ecrlsLabDocVO.getPIDCITY()) :  PIDCITY.trim().equalsIgnoreCase(ecrlsLabDocVO.getPIDCITY().trim()))
					|| !(CommonUtil.isNullOrEmpty(PIDZIPCODE) ? CommonUtil.isNullOrEmpty(ecrlsLabDocVO.getPIDZIPCODE()) :  PIDZIPCODE.trim().equalsIgnoreCase(ecrlsLabDocVO.getPIDZIPCODE().trim()))
						|| !(CommonUtil.isNullOrEmpty(PIDSTATE) ? CommonUtil.isNullOrEmpty(ecrlsLabDocVO.getPIDSTATE()) :  PIDSTATE.trim().equalsIgnoreCase(ecrlsLabDocVO.getPIDSTATE().trim())))
				{
			
			
			String PERSON_ADDRESS = CommonUtil
					.addressValidation(ecrlsLabDocVO.getPIDADDRESSLINE1() + " " + ecrlsLabDocVO.getPIDADDRESSLINE2());
			
			stdTableInputParameterMap.put("PERSON_ADDRESS", PERSON_ADDRESS.replaceAll("\\s+", " "));
			
			String PERSON_ZIP = CommonUtil.formatZipCode(ecrlsLabDocVO.getPIDZIPCODE());
			
			if(CommonUtil.isNullOrEmpty(PERSON_ZIP) && CommonUtil.isNullOrEmpty(ecrlsLabDocVO.getPIDADDRESSLINE1())){
				throw new DataRetrievalFailureException("There is no valid Address found");
			}else if("00000".equals(PERSON_ZIP)){
				
				throw new DataRetrievalFailureException("Person Zip code is not valid");
			
			}else{
			
				Map<String, String> zipCodeValues = commonService.getCountyNameByZip(PERSON_ZIP);
				
				
				if (zipCodeValues != null) {
					
					String _state = zipCodeValues.get("STATE_CD");
					String _county = zipCodeValues.get("COUNTY_FIPS_CD");
					if(CommonUtil.isNotNullAndEmpty(_state) && !"NY".equals(_state)) {
						_county = "998";
					}else if(CommonUtil.isNullOrEmpty(_state)) {
						_county = "999";
					}
					stdTableInputParameterMap.put("PERSON_COUNTY", _county);
					stdTableInputParameterMap.put("PERSON_STATE", _state);
					stdTableInputParameterMap.put("PERSON_CITY", zipCodeValues.get("CITY"));
					stdTableInputParameterMap.put("PERSON_ZIP", PERSON_ZIP);
				}else{
					
					stdTableInputParameterMap.put("PERSON_COUNTY", "999");
					
					if(CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getPIDSTATE()) && ecrlsLabDocVO.getPIDSTATE().length() == 2){
						stdTableInputParameterMap.put("PERSON_STATE", ecrlsLabDocVO.getPIDSTATE());
					}

					stdTableInputParameterMap.put("PERSON_CITY", ecrlsLabDocVO.getPIDCITY());
					//stdTableInputParameterMap.put("PERSON_ZIP", "");
					
					log.error("Person Zip code not valid for {}",ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY());
				}
			}
			
			
			

			
			
			rawTableInputParameterMap.put("PIDADDRESSLINE1", ecrlsLabDocVO.getPIDADDRESSLINE1());
			rawTableInputParameterMap.put("PIDADDRESSLINE2", ecrlsLabDocVO.getPIDADDRESSLINE2());
			rawTableInputParameterMap.put("PIDCITY", ecrlsLabDocVO.getPIDCITY());
			rawTableInputParameterMap.put("PIDSTATE", ecrlsLabDocVO.getPIDSTATE());
			rawTableInputParameterMap.put("PIDZIPCODE", ecrlsLabDocVO.getPIDZIPCODE());
			rawTableInputParameterMap.put("PIDUPDATEDATE", ecrlsLabDocVO.getPIDUPDATEDATE());
			rawTableInputParameterMap.put("PIDUPDATEID", ecrlsLabDocVO.getPIDUPDATEID());
			
			
			
			
			
			return true;
			
		}
		
		
		return false;
	}

	public void loadZipCodeReferenceMap() {
		zipCodeReferenceMap = commonService.getZipCodeReferenceData();
	}

	public void loadDocumentReferenceVariables(String documentType, Map<String, String> schedulerMetaData) {
		if("STD_CONF".equals(documentType)){
			stdConfDocumentService.loadSTDConfReferenceVariables();
		}else if("HIV_LAB".equals(documentType)){
			manualQuartzJobService.loadHIVDocumentReferenceVariable(schedulerMetaData);
		}else if("ADAP".equals(documentType)){
			adapDocumentService.loadReferenceVariables();
		}else if("DOCCS".equals(documentType)){
			doccsDocumentService.loadReferenceVariables();
		}
		
	}

	public void resetDocumentReferenceVariables(String documentType) {
		if("STD_CONF".equals(documentType)){
			stdConfDocumentService.resetDocumentReferenceVariables();
		}else if("HIV_LAB".equals(documentType)){
			manualQuartzJobService.resetHIVDocumentReferenceVariable();
		}else if("ADAP".equals(documentType)){
			adapDocumentService.resetDocumentReferenceVariables();
		}else if("DOCCS".equals(documentType)){
			doccsDocumentService.resetDocumentReferenceVariables();
		}
		
	}

	@Transactional
		
	public int storeDocumentMatchResultsToBirthTables(Map<String, Object> documentMatchData, String documentType) {
		
		String birthMatchId = (String)documentMatchData.get("documentId"); 
		
		int resultCount = birthDocumentService.birthDocumentExists(birthMatchId, documentType);
		
		if(resultCount > 0) {
			
			return birthDocumentService.updateBirthDocumentMatchResults(documentMatchData, documentType);
			
		}else {
			
			Map<Object, Object> inputOutputParameters = new HashMap<>();
			inputOutputParameters.put("p_document_type", documentType);
			inputOutputParameters.put("p_new_doc_id", "");	
			
			commonDocumentService.getNewDocId(inputOutputParameters);
			
			int bthDocumentId=(Integer)inputOutputParameters.get("p_new_doc_id");
			
			if("BTH_MOM".equals(documentType)) {
			
				return birthDocumentService.insertRawBirthMomDataToStdTable(bthDocumentId, birthMatchId, documentMatchData);
			}else {
				return birthDocumentService.insertRawBirthInfantDataToStdTable(bthDocumentId, birthMatchId, documentMatchData);
			}
			
		}
		
		
				
		
		
	}
}
