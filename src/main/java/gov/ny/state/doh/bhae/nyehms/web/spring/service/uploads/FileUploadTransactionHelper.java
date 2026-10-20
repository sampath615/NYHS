package gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads;


import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.RecordSearchReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	CommonService commonService;
	
	
	
	@Autowired
	SendEmail sendEmail;
	
//	@Transactional
//	public void uploadUpdatedDispositionsCodes(
//			Set<Integer> returnMessageDocIds, List<Integer> docIdsDateOfDeathNull,
//			List<UpdatedDispositionsVO> updatedDispositionsList, Map<Object, Object> resultMap,
	
	
	private void sendEmailToPsMessageTeam(String subject, String mailContent, String userAddress, String columnName){
		
		try {
			
			//String ccList = ccAddressList;
			
			
			//if (recipientEmailAddressesTo.length > 0) {
			//	sendEmail.sendSSLMessage(
			//			recipientEmailAddressesTo,
			//			subject, mailContent,
			//			 EnvironmentProperties.envProperties.getProperty("helpLineFrom"), recipientEmailAddressesCC,
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



