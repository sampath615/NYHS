package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.AHIDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CommonDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHICiStiVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHINegInd900TestHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AHIAlertPSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AhiAlertTrackingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;



@Service
public class AHIDocumentServiceImpl implements AHIDocumentService {
	
	@Autowired private CommonExceptionHandler commonExceptionHandler;	
	@Autowired private DocumentTransactionHelper documentTransactionHelper;
	@Autowired private AHIDocumentDAO ahiDocumentDAO;
	@Autowired SendEmail sendEmail;
	@Autowired CommonService commonService;
	
	@Autowired
	CommonDocLabsDAO commonDocLabsDAO;
	
	private static Logger log = LoggerFactory.getLogger(AHIDocumentServiceImpl.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();

	@Override
	public List<Map<String, String>> getAHICISTIConditionList() {
		return ahiDocumentDAO.getAHICISTIConditionList();
	}

	@Override
	public List<Map<String, String>> getAHICIReasonPatientTestedList() {
		return ahiDocumentDAO.getAHICIReasonPatientTestedList();
	}

	@Override
	public List<Map<String, String>> getAHINegInd900TestTypeList() {
		return ahiDocumentDAO.getAHINegInd900TestTypeList();
	}
	
	
	@Override
	public void insertAHIDocument(AHIDocVO ahiDocVo, BindingResult result, UserObject userObject) {
		
		int resultCount  = ahiDocumentDAO.insertAHIDocData(ahiDocVo);
		
		if(resultCount == 0){
			
			log.error("Failed while storing AHI Document data for STATENO {}",ahiDocVo.getSTATENO());
			
			throw new DataRetrievalFailureException("Error while saving AHI Document, please contact the Helpline.");
		}
		
		List<AHICiStiVO> ahiCIStiList =  ahiDocVo.getCiStiConditionList();
		
		
		
		if(ahiCIStiList != null && ahiCIStiList.size() > 0){
			
			List<AHICiStiVO> finalAHICIStiList = new ArrayList<AHICiStiVO>();

		
			for (AHICiStiVO ahiCiStiVO : ahiCIStiList) {
				if(CommonUtil.isNotNullAndEmpty(ahiCiStiVO.getCI_STI_CONDITION_CODE()) || ((!"../../....".equals(ahiCiStiVO.getCI_STI_CONDITION_DX_DATE())) && CommonUtil.isNotNullAndEmpty(ahiCiStiVO.getCI_STI_CONDITION_DX_DATE())) ) {
					finalAHICIStiList.add(ahiCiStiVO);
				}
			}
			
			
			if(finalAHICIStiList != null  && finalAHICIStiList.size() > 0){
				
				Map<String, Object> inputParameterMap  = new HashMap<String, Object>();
				
				inputParameterMap.put("AHI_DOCUMENT_ID", ahiDocVo.getDOCUMENT_ID());
				
				inputParameterMap.put("finalAHICIStiList", finalAHICIStiList);
				
				resultCount = ahiDocumentDAO.insertAHICiStiData(inputParameterMap);
				
	
				if(resultCount == 0){
					
					log.error("Failed while storing AHI STI Condition data for STATENO {}",ahiDocVo.getSTATENO());
					
					throw new DataRetrievalFailureException("Error while saving AHI Document, please contact the Helpline.");
				}
				
				
			}
		}
		

		List<AHINegInd900TestHistoryVO> ahiNegInd900TestHistoryList =  ahiDocVo.getNegInd900TestHisList();
		
		
		if(ahiNegInd900TestHistoryList != null && ahiNegInd900TestHistoryList.size() > 0){

		List<AHINegInd900TestHistoryVO> finalAhiNegInd900TestHistoryList = new ArrayList<AHINegInd900TestHistoryVO>();
		
		
			for(AHINegInd900TestHistoryVO ahiNegInd900TestHistory: ahiNegInd900TestHistoryList){
				if( ((!"../../....".equals(ahiNegInd900TestHistory.getLAST_NEG_IND_DATE())) && CommonUtil.isNotNullAndEmpty(ahiNegInd900TestHistory.getLAST_NEG_IND_DATE()) ) || CommonUtil.isNotNullAndEmpty(ahiNegInd900TestHistory.getNEG_IND_RESULT())
						|| CommonUtil.isNotNullAndEmpty(ahiNegInd900TestHistory.getNEG_IND_TEST_TYPE_CODE()) || CommonUtil.isNotNullAndEmpty(ahiNegInd900TestHistory.getNEG_IND_TEST_TYPE_OTHER())
						|| CommonUtil.isNotNullAndEmpty(ahiNegInd900TestHistory.getNEG_IND_LAB_REPORT()) || CommonUtil.isNotNullAndEmpty(ahiNegInd900TestHistory.getNEG_IND_LAST_TEST_PROV_NAME())
						|| CommonUtil.isNotNullAndEmpty(ahiNegInd900TestHistory.getNEG_IND_PROV_NAME_UNKNOWN())) {
					finalAhiNegInd900TestHistoryList.add(ahiNegInd900TestHistory);
				}
			}
			
			
	
			if(finalAhiNegInd900TestHistoryList != null  && finalAhiNegInd900TestHistoryList.size() > 0){
				
				Map<String, Object> inputParameterMap  = new HashMap<String, Object>();
				
				inputParameterMap.put("AHI_DOCUMENT_ID", ahiDocVo.getDOCUMENT_ID());
				
				inputParameterMap.put("finalAhiNegInd900TestHistoryList", finalAhiNegInd900TestHistoryList);
				
				resultCount = ahiDocumentDAO.insertAHINegInd900TestHistoryData(inputParameterMap);
				
	
				if(resultCount == 0){
					
					log.error("Failed while storing AHI Test History data for STATENO {}",ahiDocVo.getSTATENO());
					
					throw new DataRetrievalFailureException("Error while saving AHI Document, please contact the Helpline.");
				}
				
				
			}
		}
	}
	

	@Override
	public void sendEmail(BindingResult result, UserObject userObject) throws MessagingException {
		
		String subject = EnvironmentProperties.envProperties.getProperty("EMAIL_AHI_DOCUMENT_SUBJECT");
		
		//String[] recipientEmailAddresses = address_to.split(",");
		
		//String message = subject;
		
		//sendEmail.sendSSLMessage(recipientEmailAddresses, subject, message, userObject.getEmailAddress(), address_cc.split(","), CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
		sendEmail.processEmail(null, subject, subject, "AHI_NOTIFICATION", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
		
	}

	@Override
	public int changeAhiFlagStatusForAssignment(int assignmentId,
			boolean ahiFlagStatus, String documentType, UserObject userObject,
			BindingResult bindingResult){
		
		String transactionMessage = null;

			
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("assignmentId", assignmentId);
		if(ahiFlagStatus){
			
			
//					
//					commonExceptionHandler.inspectDBTransacionResult(-1,
			
			
			
			inputParameterMap.put("ahiFlagStatus", "Y");
		}else{
			inputParameterMap.put("ahiFlagStatus", "N");
		}
		
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		
		int resultCount = ahiDocumentDAO.changeAhiFlagStatusForAssignment(inputParameterMap);
		
		if(resultCount == 0){
			

			transactionMessage = "Failed to change the AHI status, This Assignment was already closed.";
			
			log.error("{}         Assignment ID  : {}",transactionMessage,assignmentId);
			
			commonExceptionHandler.inspectDBTransacionResult(-1,
					transactionMessage, bindingResult);
			
		}else{
			
			commonExceptionHandler.inspectDBTransacionResult(0,
					"AHI flag successfully updated.", bindingResult);
		}
		
		
		
		return resultCount;
		

		
	}
	
		@Override
	public void sendEmailNotificationToAHIUsers(UserObject userObject, String subject, String content){
		try {
			
			
			sendEmail.processEmail(null, subject, content, "ASSIGNMENT_AHI", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			
			
//				sendEmail.sendSSLMessage(
//						recipientEmailAddressesTo,
//						subject, content,
//						 EnvironmentProperties.envProperties.getProperty("helpLineFrom"), recipientEmailAddressesCC,
		} catch (MessagingException messagingException) {
			
			log.error(nyehmsLog + "   Failed to send the Email notification to AHI Users   ");
			
			log.error(ExceptionUtils.getStackFrames(messagingException).toString());
			
			throw new DataRetrievalFailureException(content);
		}
	}
	
	@Override
	public void saveAHIForm(AHIDocVO aHIDocVO, BindingResult bindingResult,
			UserObject userObject){
		
		try{
			
			
			documentTransactionHelper.insertAHIDocumentAndAssociateToStateno(aHIDocVO, null, bindingResult, userObject);
		
			commonExceptionHandler.inspectDBTransacionResult(0, "AHI Form saved successfully.", bindingResult);
			
		}catch(DataAccessException exception){
			
    		
    		log.error("Error while Saving AHI Form.  {}",exception.getMessage());
    		
    		commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, exception.getMessage(), bindingResult);
    		
    		
		} catch (Exception exception) {
			
			log.error("Error while Saving AHI Form.  {}",exception.getMessage());
    		
    		commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, "Error while Saving AHI Form, please contact the Helpline.", bindingResult);
		}

		
		
		
	}
		
	@Override
	public AHIDocVO getAHIFormVO(int documentId, BindingResult result, UserObject userObject) {
		
		boolean rollbackFlag = false;
		AHIDocVO ahiDocVo = null;
		log.debug("{} fetching for docID : {}",nyehmsLog, documentId);
		try {
			ahiDocVo = ahiDocumentDAO.getAHIDoc(documentId);
			
			List<AHICiStiVO> aHICiStiVOList = getCiStiConditions(documentId, result, userObject);
					
			if(aHICiStiVOList == null || aHICiStiVOList.size() == 0){
				
				aHICiStiVOList = new ArrayList<AHICiStiVO>();
				
				aHICiStiVOList.add(new AHICiStiVO());
				
			}
			
			ahiDocVo.setCiStiConditionList(aHICiStiVOList);
			
			List<AHINegInd900TestHistoryVO> aHINegInd900TestHistoryVOList = getNegInd900TestHistoryData(documentId, result, userObject);
			
			if(aHINegInd900TestHistoryVOList == null || aHINegInd900TestHistoryVOList.size() == 0){
				
				aHINegInd900TestHistoryVOList = new ArrayList<AHINegInd900TestHistoryVO>();
				
				aHINegInd900TestHistoryVOList.add(new AHINegInd900TestHistoryVO());
			}
					
			
			ahiDocVo.setNegInd900TestHisList(aHINegInd900TestHistoryVOList);
		} catch (DataAccessException e) {
			rollbackFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} catch (Exception e) {
			rollbackFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} finally {
			if (rollbackFlag) {
				commonExceptionHandler.populateBindObjTrsnFail(result);
			}
		}

		return ahiDocVo;
	}
	
	private List<AHICiStiVO> getCiStiConditions(int documentId, BindingResult result, UserObject userObject) {
		boolean errorFlag=false;
		List<AHICiStiVO>  ciStiConditionList = new ArrayList<AHICiStiVO>();
		try{
			ciStiConditionList=ahiDocumentDAO.getCiStiConditionData(documentId);
		}catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return ciStiConditionList;
	}
	
	private List<AHINegInd900TestHistoryVO> getNegInd900TestHistoryData(int documentId, BindingResult result, UserObject userObject) {
		boolean errorFlag=false;
		List<AHINegInd900TestHistoryVO>  negInd900TestHistoryList = new ArrayList<AHINegInd900TestHistoryVO>();
		try{
			negInd900TestHistoryList=ahiDocumentDAO.getNegInd900TestHistoryData(documentId);
		}catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return negInd900TestHistoryList;
	}
	
	
	@Override
	public void storeEditedAHIStdDocument(AHIDocVO ahiDocVO, BindingResult bindingResult, UserObject userObject) {
		
		
		boolean isError = false;
		
		int documentId = ahiDocVO.getDOCUMENT_ID();
		
		try{
			
			documentTransactionHelper.storeEditedAHIStdDocument(documentId, ahiDocVO, "AHI", bindingResult, userObject);
			
			
			
			
		} catch (DataAccessException e) {
			isError = true;
			log.error("Document Id   {}",documentId);
			log.error(nyehmsLog+" Failed while storing the edited document " + e.getMessage());
			
			commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
			
		} catch (Exception e) {
			isError = true;
			log.error("Document Id  {}",documentId);
			log.error(nyehmsLog+"  Failed while storing the edited document  " + e.getMessage());
			
			
			commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
		}
		
		if(isError  && (bindingResult.getAllErrors() == null || bindingResult.getAllErrors().size() == 0) ){
			
			commonExceptionHandler.inspectDBTransacionResult(-1, "Error while saving document, please contact the Helpline. ", bindingResult);

		}
		
	}
	
	@Override
	public int validateAHIDataWithACRF(int acrfDocId, int ahiDocId, String documentType, 
			BindingResult bindingResult, UserObject userObject){
		

		boolean isError = false;
		
		int resultCount = 0;
		try{
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			
			inputParameterMap.put("acrfDocId", acrfDocId);
			inputParameterMap.put("ahiDocId", ahiDocId);
			inputParameterMap.put("tableName", LabCommonUtils.getRawTableName(documentType));
			
			
			resultCount =  ahiDocumentDAO.validateAHIDataWithACRF(inputParameterMap);
			
			
			
			
			
		} catch (Exception e) {
			isError = true;
			
			log.error("ACRF DocId : {} ;  ahiDocId  :  {}",acrfDocId,ahiDocId);
			
			log.error("{} Failed while validating AHI Data with ACRF Data {}",nyehmsLog, e.getMessage());
			
			commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
			
			
			throw new DataRetrievalFailureException("Failed while validating AHI Data with ACRF Data ");
			
			
		} 
	
		return resultCount;
	}

	@Override
	public List<AhiAlertTrackingVO> getAhiFlaggedStatenoData(UserObject userObject, BindingResult bindingResult, String followupReq, String followupComp) {
		
		List<AhiAlertTrackingVO> ahiFlaggeddataList = ahiDocumentDAO.getAhiFlaggedStatenoData();
		List<AhiAlertTrackingVO> ahiFollowupDataList = new ArrayList<AhiAlertTrackingVO>();
		
		if (followupReq.equals("Y")) {
			for (AhiAlertTrackingVO currData : ahiFlaggeddataList) {
				if (CommonUtil.isNotNullAndEmpty(currData.getAhiFlag()) &&  currData.getAhiFlag().equals("Y") && CommonUtil.isNullOrEmpty(currData.getAlertCompletedDate())) {
					ahiFollowupDataList.add(currData);
				}
			} 
		} else if (followupReq.equals("N")) {
			for (AhiAlertTrackingVO currData : ahiFlaggeddataList) {
				if (CommonUtil.isNotNullAndEmpty(currData.getAhiFlag()) && CommonUtil.isNotNullAndEmpty(currData.getAlertCompletedDate())) {
					
					if (followupComp.equals("NR") && currData.getAhiFlag().equalsIgnoreCase("N")) {
						ahiFollowupDataList.add(currData);
					} else if (followupComp.equals("ALL")) {
						ahiFollowupDataList.add(currData);
					}
				}
			} 
		}
		
		return ahiFollowupDataList;
	}

	@Override
	public List<Map<String, String>> getAhiAlertSrc() {
		return ahiDocumentDAO.getAhiAlertSrc();
	}

	@Override
	public List<Map<String, String>> getYesNo() {
		return ahiDocumentDAO.getYesNo();
	}

	@Override
	public List<Map<String, String>> getActivePassive() {
		return ahiDocumentDAO.getActivePassive();
	}

	@Override
	public List<Map<String, String>> getAhiPrfFollowup() {
		return ahiDocumentDAO.getAhiPrfFollowup();
	}

	@Override
	public List<Map<String, String>> getPSMessageType() {
		return ahiDocumentDAO.getPSMessageType();
	}

	@Override
	public void saveAHIAlertTrackingForm(AhiAlertTrackingVO ahiAlertTrackingVO, BindingResult bindingResult, UserObject userObject) {
		try{
			
			documentTransactionHelper.updateAhiAlertTrackingData(ahiAlertTrackingVO, userObject, bindingResult);
		
			commonExceptionHandler.inspectDBTransacionResult(0, "AHI Alert Tracking data saved successfully.", bindingResult);
			
		}catch(DataAccessException exception){
			
    		log.error("Error while Saving AHI Alert Tracking Form.  {}",exception.getMessage());
    		
    		commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, exception.getMessage(), bindingResult);
    		
		} catch (Exception exception) {
			
			log.error("Error while Saving AHI Alert Tracking Form.  {}",exception.getMessage());
    		
    		commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, "Error while Saving AHI Alert Tracking Form, please contact the Helpline.", bindingResult);
		}
		
	}

	@Override
	public void updateAhiAlertTracking(AhiAlertTrackingVO ahiAlertTrackingVO, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("prfCollection", ahiAlertTrackingVO.getPrfCollection());
		inputParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		inputParameterMap.put("ahiAlertSeqId", ahiAlertTrackingVO.getAhiAlertSeqId());
		inputParameterMap.put("alertCompletedBy", null);
		
		if (ahiAlertTrackingVO.getFollowupStepTakenCode().equals("5") || ahiAlertTrackingVO.getFollowupStepTakenCode().equals("13")) {
			inputParameterMap.put("alertCompletedBy", userObject.getWorkerId());
		}
		
		ahiDocumentDAO.updateAhiAlertTrackingData(inputParameterMap);
		
	}

	@Override
	public void insertAhiAlertFollowup(AhiAlertTrackingVO ahiAlertTrackingVO, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("followupStepTakenCode", ahiAlertTrackingVO.getFollowupStepTakenCode());
		inputParameterMap.put("userNotes", ahiAlertTrackingVO.getUserNotes());
		inputParameterMap.put("userNotesBy", userObject.getWorkerId());
		inputParameterMap.put("ahiAlertSeqId", ahiAlertTrackingVO.getAhiAlertSeqId());
		
		ahiDocumentDAO.insertAhiAlertTrackingFollowupData(inputParameterMap);
		
	}
	@Override
	public AhiAlertTrackingVO getAhiAlertDetails(int ahiAlertSeqId) {
		
		AhiAlertTrackingVO ahiAlertTrackingVO = ahiDocumentDAO.getAhiAlertDetails(ahiAlertSeqId);
		
		List<AHIAlertPSMessageVO> psMessageList = ahiDocumentDAO.getAHIAlertPSMessageInfo(ahiAlertTrackingVO.getStateno());
		ahiAlertTrackingVO.setPsMessageList(psMessageList);
		
		return ahiAlertTrackingVO;
	}

//	@Override
	
	@Override
	public List<AhiAlertTrackingVO> getAhiAlertData(UserObject userObject, BindingResult bindingResult, String dataType, String dataSubType) {

		List<AhiAlertTrackingVO> ahiAlertdataList = ahiDocumentDAO.getAhiFlaggedStatenoData();
		List<AhiAlertTrackingVO> ahiDisplayDataList = new ArrayList<AhiAlertTrackingVO>();
		
		
		if (dataType.equals("UR")) {	// AHI Alert data need to be review   Status = OPEN
			for (AhiAlertTrackingVO currData : ahiAlertdataList) {
				if (CommonUtil.isNotNullAndEmpty(currData.getAhiAlertStatus()) && currData.getAhiAlertStatus().trim().equals("OPEN")
						&& currData.getAhiFlag().equals("Y")) {
					ahiDisplayDataList.add(currData);
				}
			} 
		} else if (dataType.equals("UF")) {	// AHI Alert data which is un-flagged    AHI_FLAG == N
			for (AhiAlertTrackingVO currData : ahiAlertdataList) {
				if (CommonUtil.isNotNullAndEmpty(currData.getAhiFlag()) &&  currData.getAhiFlag().equals("N") ) {
					ahiDisplayDataList.add(currData);
				}
			} 
		} else if (dataType.equals("FR")) { // AHI Alert data which follow-up required  AHI_FLAG == Y and Complete_Date = null and Status == REVIEWED
			for (AhiAlertTrackingVO currData : ahiAlertdataList) {
				if (CommonUtil.isNotNullAndEmpty(currData.getAhiFlag()) &&  currData.getAhiFlag().equals("Y") 
						&& CommonUtil.isNullOrEmpty(currData.getAlertCompletedDate())
						&& CommonUtil.isNotNullAndEmpty(currData.getAhiAlertStatus()) && currData.getAhiAlertStatus().trim().equals("REVIEWED")) {
					ahiDisplayDataList.add(currData);
				}
			} 
		} else if (dataType.equals("FC")) {	// AHI Alert data which follow-up completed 
			if (dataSubType.equals("ALL")) {					//	AHI_FLAG != null && Complete_Date != null
				for (AhiAlertTrackingVO currData : ahiAlertdataList) {
					if (CommonUtil.isNotNullAndEmpty(currData.getAhiFlag()) && CommonUtil.isNotNullAndEmpty(currData.getAlertCompletedDate()) ) {
						ahiDisplayDataList.add(currData);
					}
				}
			} else	if (dataSubType.equals("PRF_REC")) {			//	AHI_FLAG != null && Complete_Date != null && PRF_COLLECTION_DATE != null
				for (AhiAlertTrackingVO currData : ahiAlertdataList) {
					if (CommonUtil.isNotNullAndEmpty(currData.getAhiFlag()) && CommonUtil.isNotNullAndEmpty(currData.getAlertCompletedDate()) 
							&& CommonUtil.isNotNullAndEmpty(currData.getPrfCollectionDate()) ){
						ahiDisplayDataList.add(currData);
					}
				}
				
			} else if (dataSubType.equals("PRF_NOT_REC")) {			//	AHI_FLAG != null && Complete_Date != null && PRF_COLLECTION_DATE == null
				for (AhiAlertTrackingVO currData : ahiAlertdataList) {
					if (CommonUtil.isNotNullAndEmpty(currData.getAhiFlag()) && CommonUtil.isNotNullAndEmpty(currData.getAlertCompletedDate()) 
							&& CommonUtil.isNullOrEmpty(currData.getPrfCollectionDate()) ){
						ahiDisplayDataList.add(currData);
					}
				}
			} 
		}		
		
		return ahiDisplayDataList;
	
	}

	@Override
	public void updateAhiAlertStatus(AhiAlertTrackingVO ahiAlertTrackingVO, UserObject userObject, BindingResult bindingResult) {
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("ahiAlertReviewedBy", userObject.getWorkerId());
		inputParameterMap.put("ahiAlertSeqId", ahiAlertTrackingVO.getAhiAlertSeqId());
		
		int noOfRowsEffected = ahiDocumentDAO.updateAhiAlertStatus(inputParameterMap);
		String transactionMessage= noOfRowsEffected > 0 ? "Successfully updated the AHI alert status.":"Unable to update AHI alert status";
		
		commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
		
	}	
}
