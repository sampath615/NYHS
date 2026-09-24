package gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads;


import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadPSConfirmedSTDVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.RecordSearchReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 * This class provides helper methods for Person level functions.
 * 
 * @author SKA03
 */
@Component
public class FileUploadTransactionHelper {

	private static Logger log = LoggerFactory.getLogger(FileUploadTransactionHelper.class);
	
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	RecordSearchReviewService recordSearchReviewService;
	
	@Autowired
	CommonDocumentService commonDocumentService;
	
	@Autowired
	CommonService commonService;
	
	
	
	@Autowired
	SendEmail sendEmail;
	
//	@Transactional
//	public void uploadUpdatedDispositionsCodes(
//			Set<Integer> returnMessageDocIds, List<Integer> docIdsDateOfDeathNull,
//			List<UpdatedDispositionsVO> updatedDispositionsList, Map<Object, Object> resultMap,
//			BindingResult bindingResult, UserObject userObject) {
//
//		
//		boolean isError = false;
//		
//		String transactionMessage = null;
//		
//		
//		
//		if(docIdsDateOfDeathNull != null && docIdsDateOfDeathNull.size() > 0){
//			
//			
//			
//			List<Map<String, Object>> DocIdsDateOfDeathNotNull = new ArrayList<Map<String, Object>>();
//			
//			
//			int totalDocIdSize = docIdsDateOfDeathNull.size();
//			
//			 for (int i = 0; i < totalDocIdSize; i += 1000) {
//				 	
//				 	List<Map<String, Object>> DocIdsDateOfDeathNotNull_temp =  fileUploadService.checkExistingDateOfDeathForMessageDocIds(docIdsDateOfDeathNull.subList(i, Math.min(totalDocIdSize, i + 1000)));
//			        
//				 	if(DocIdsDateOfDeathNotNull_temp != null && DocIdsDateOfDeathNotNull_temp.size() > 0){
//				 		
//				 		DocIdsDateOfDeathNotNull.addAll(DocIdsDateOfDeathNotNull_temp);
//				 	}
//			}
//			 
//			
//			if(DocIdsDateOfDeathNotNull != null && DocIdsDateOfDeathNotNull.size() > 0){
//				
//				
//				
//				StringBuilder dodNullNotificationMailContent = new StringBuilder("<br/><br/><br/><table border='1'><tr><th>STATENO</th><th>DOCUMENT_ID</th><th>TRIGGER_MESSAGE_DOC_ID</th></tr>");
//				
//				for (Map<String, Object> docIdInfo : DocIdsDateOfDeathNotNull) {
//					dodNullNotificationMailContent.append("<tr><td>");
//					dodNullNotificationMailContent.append(docIdInfo.get("STATENO"));
//					dodNullNotificationMailContent.append("</td><td>");
//					dodNullNotificationMailContent.append(docIdInfo.get("DOCUMENT_ID"));
//					dodNullNotificationMailContent.append("</td><td>");
//					dodNullNotificationMailContent.append(docIdInfo.get("FOLLOW_UP_DOC_ID"));
//					dodNullNotificationMailContent.append("</td></tr>");
//				}
//				
//				dodNullNotificationMailContent.append("</table>");
//				
//				
//				sendEmailToPsMessageTeam("Updated Partner Service Return Message Modified Vital Status from Dead to Alive", "Vital Status Modified for the following STATENO/PS_Message_Doc_ID/Trigger_Message_Doc_ID: "+dodNullNotificationMailContent, userObject.getEmailAddress(), "PS_MSG_DEAD_DISPO");
//				
//				
//			}
//			
//			
//		}
//		
//		
//		
//		
//		List<JSONObject> recordSearchDispositionCodesList = commonDocumentService.getDispositionCodeValues();
//		
//		
//		Map<Integer, String> recordSearchDispositionObj = new HashMap<Integer, String>();
//		
//		for (JSONObject dispositionCode : recordSearchDispositionCodesList) {
//			
//			if("Y".equals((String)dispositionCode.get("RECORD_SEARCH_FLAG"))){
//				
//				recordSearchDispositionObj.put(((BigDecimal)dispositionCode.get("DISPOSITION_CODE")).intValue(), (String)dispositionCode.get("DISPOSITION_DESC"));
//				
//			}
//			
//		}
//		
//		
//		for (UpdatedDispositionsVO updatedDispositionsVO : updatedDispositionsList) {
//			
//			int returnMessageDocId =  updatedDispositionsVO.getDOCUMENT_ID();
//			
//				
//			try{
//				
//				
//				
//				int resultCount = fileUploadService.checkForDuplicateDispositionCode(updatedDispositionsVO);
//				
//				if(resultCount == 0){
//				
//					fileUploadService.insertUpdatedDispositionCode(updatedDispositionsVO);
//					
//					
//					if(recordSearchDispositionObj.get(updatedDispositionsVO.getLATEST_DISPOSITION_CODE()) != null){
//						
//						updatedDispositionsVO.setDISPOSITION_DESC("Record Search triggered for Disposition code "+ recordSearchDispositionObj.get(updatedDispositionsVO.getLATEST_DISPOSITION_CODE()));
//						
//						updatedDispositionsVO.setCREATE_UID(Integer.toString(userObject.getWorkerId()));
//						
//						recordSearchReviewService.insertAutoRecordSearchEntry(updatedDispositionsVO);
//						
//						
//					}
//				}
//				
//				
////			}catch(SQLException exception){
////				
////				log.error("returnMessageDocId  : "+returnMessageDocId );
////				log.error(exception.getMessage());
////				throw new DataRetrievalFailureException("Failed while uploading the PS Update return Messages ");
//				
//			}catch(Exception exception){
//				isError = true;
//				
//				log.error(returnMessageDocId+"              "+exception.getMessage());
//				
//				String errorMessage = exception.getMessage();
//				
//				
//				String[] rootCauseMessages =  errorMessage.split("\\*\\*\\*");
//				
//				if(rootCauseMessages != null && rootCauseMessages.length > 1){
//					resultMap.put(returnMessageDocId, rootCauseMessages[1]);
//				}else{
//					resultMap.put(returnMessageDocId, "Failed to upload the return Message");
//				}
//				
//				
//				
//				
//			}
//				
//		
//			
//		}
//		
//		
//		int totalMessages = returnMessageDocIds.size();
//		int failedMessages = resultMap.size(); 
//		if(failedMessages == totalMessages){
//			transactionMessage = "All messages failed upload";
//		}else if(isError){
//			transactionMessage = "The following messages failed upload";
//		}else{
//			
////			if(resultMap.size() > 0){
////				isError = true;
////				transactionMessage = "Few MessageDocIds Doesn't exist in the system.";
////			}else{
//				
//				transactionMessage = "All messages were successfully uploaded";
//				
//			//}
//			
//		}
//		
//		
//		
//		
//		
//		
//		if(resultMap.size() > 0){
//			
//			
//			
//			StringBuilder mailContent = new StringBuilder();
//			
//			mailContent.append(totalMessages - failedMessages);
//			mailContent.append(" PS Return messages stored successfully out of ");
//			mailContent.append(totalMessages);
//			mailContent.append(" messages.");
//			
//			
//			mailContent.append("<br/><br/><br/><h2 style='font-weight:bold'>Failed Messages</h2><br/><br/>");
//			
//			
//			mailContent.append("<table border='1'><tr><th>DOCUMENT_ID</td><th>Failed Message</th></tr>");
//			
//			for (Object document_id : resultMap.keySet()) {
//				
//				mailContent.append("<tr><td>");
//				mailContent.append(document_id);
//				mailContent.append("</td><td>");
//				mailContent.append(resultMap.get(document_id));
//				mailContent.append("</td></tr>");
//				
//				
//			}
//			
//			mailContent.append("</table>");
//			
//			
//			sendEmailToPsMessageTeam("Update PS return Message upload status :  ", mailContent.toString(), userObject.getEmailAddress(), "PS_MSG_UPLOAD");
//			
//			
//		}else{
//			sendEmailToPsMessageTeam("Update PS return Message upload status", "Successfully uploaded all the Messages", userObject.getEmailAddress(), "PS_MSG_UPLOAD");
//		}
//		
//		
//		if(isError){
//			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
//		}else{
//			commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
//		}
//		
//	}	
	
	
	private void sendEmailToPsMessageTeam(String subject, String mailContent, String userAddress, String columnName){
		
		try {
			//String[] recipientEmailAddressesTo = toAddressList.split(",");
			//String[] recipientEmailAddressesCC = null;
			
			//String ccList = ccAddressList;
			
			//if(CommonUtil.isNotNullAndEmpty(ccList)){
			//	recipientEmailAddressesCC = ccList.split(",");
			//}
			
			//if (recipientEmailAddressesTo.length > 0) {
			//	sendEmail.sendSSLMessage(
			//			recipientEmailAddressesTo,
			//			subject, mailContent,
			//			 EnvironmentProperties.envProperties.getProperty("helpLineFrom"), recipientEmailAddressesCC,
			//			CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			//} else {
			//	log.error(mailContent);
			//}
			sendEmail.processEmail(userAddress, subject, mailContent, columnName, CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
		} catch (MessagingException messagingException) {
			
			log.error(ExceptionUtils.getStackFrames(messagingException).toString());
			
			throw new DataRetrievalFailureException(subject+"      :         "+mailContent);
			
		}
		
	}

	@Transactional
	public void uploadRidrData(String fileName, List<Map<String, Object>> ridrDataList, UserObject userObject) throws SQLException {
		
		fileUploadService.uploadRidrDataAsBatch(fileName, ridrDataList, userObject.getWorkerId());
		
		log.error("uploaded the CDC RIDR file and checking for Duplicates");
		
		fileUploadService.identifyCDCRidrDuplicates(fileName);
		
	}


	public void uploadRecordSearchStateno(
			Map<String, String> uploadRecordSearchStatenoMap,
			Map<String, String> resultMap, BindingResult bindingResult, UserObject userObject) {

		
		for (String stateno : uploadRecordSearchStatenoMap.keySet()) {
			
			String currentStateno = commonService.getCurrentActiveStateNo(stateno);
				
			if (CommonUtil.isNullOrEmpty(currentStateno) || currentStateno.equals("-1")) {
				
				resultMap.put(stateno, "STATENO is not active.");
				
				continue;
			}else{
				
				
				boolean isError = recordSearchReviewService.changeRecordSearchStatus(currentStateno, uploadRecordSearchStatenoMap.get(stateno), "Upload", "Y", bindingResult, userObject);
				
				if(isError){
					
					
					Map<String, String> dummyResultMap =  (Map<String, String>)bindingResult.getTarget();
					
					resultMap.put(stateno, dummyResultMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
				}
			}
			
		}
		
		

		if(resultMap.size() > 0){
			commonExceptionHandler.inspectDBTransacionResult(-1, "The following STATENOS failed while as flagging as Record Search", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "All messages were successfully uploaded", bindingResult);
		}
		
		
		
		
		
		
		
		
		if(resultMap.size() > 0){
			
			
			
			StringBuilder mailContent = new StringBuilder();
			
			mailContent.append(" Below STATENOs failed to flag as Record Search ");
			
			
			mailContent.append("<br/><br/><br/><h2 style='font-weight:bold'>Failed STATENOS</h2><br/><br/>");
			
			
			mailContent.append("<table border='1'><tr><th>STATENO</td><th>Failed Message</th></tr>");
			
			for (Object stateno : resultMap.keySet()) {
				
				mailContent.append("<tr><td>");
				mailContent.append(stateno);
				mailContent.append("</td><td>");
				mailContent.append(resultMap.get(stateno));
				mailContent.append("</td></tr>");
				
				
			}
			
			mailContent.append("</table>");
			
			
			sendEmailToPsMessageTeam("Record Search File upload status :  ", mailContent.toString(), userObject.getEmailAddress(), "RECORD_SEARCH_UPLOAD");
			
			
		}else{
			sendEmailToPsMessageTeam("Record Search File upload status", "Successfully uploaded all the STATENOS", userObject.getEmailAddress(), "RECORD_SEARCH_UPLOAD");
		}
		
		
	}
	
	@Transactional
	public void uploadPSConfirmedSTDData(String fileName, List<Map<String, Object>> stdConfirmDataList, UserObject userObject) throws SQLException {
		
		fileUploadService.uploadPSConfirmedSTDData(fileName, stdConfirmDataList , userObject.getWorkerId());
		
		fileUploadService.moveSTDConfirmFromTempToRaw();
		
	}		
	
	@Transactional
	public void uploadNYCSurvRejectData(List<UploadNYCSurvRejectVO> nycSurvRejectList) throws SQLException {
		
		fileUploadService.uploadNYCSurvRejectData(nycSurvRejectList);
		
	}		
	
}



