package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftFollowupDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LaftFollowupDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

@RequiredArgsConstructor
@Service
public class LaftFollowupDocumentService {
	
	private static final Logger log = LoggerFactory.getLogger(LaftFollowupDocumentService.class);
	
	private final LaftFollowupDocumentDAO laftFollowupDocumentDAO;
	private final CommonExceptionHandler commonExceptionHandler;
	private final SendEmail sendEmail;

	public List<Map<String, String>> getLaftFollowupReasons() {

		return laftFollowupDocumentDAO.getLaftFollowupReasons();
	}

	@Transactional
	public void flagLaftFollowupDocument(int triggeringDocId, String triggeringStateno, String comments, int reason, BindingResult bindingResult, UserObject userObject) {
		
		Map<String, Object> inputOutputParamters = new HashMap<>();
		
		inputOutputParamters.put("triggeringDocId", triggeringDocId);
		inputOutputParamters.put("triggeringStateno", triggeringStateno);
		inputOutputParamters.put("comments", comments);
		inputOutputParamters.put("reason", reason);
		inputOutputParamters.put("workerId", userObject.getWorkerId());
		
		int resultCount = laftFollowupDocumentDAO.flagLaftFollowupDocument(inputOutputParamters);
		
		// insert initial comments here in NYEHMS_LAFT_FOLLOWUP_COMMENTS for initial
		if (resultCount == 0) {
			throw new DataRetrievalFailureException("Failed to get the followupId id ");
		} else {
			BigDecimal _value = (BigDecimal) inputOutputParamters.get("followupId");
			int followupId = _value.intValue();

			if (followupId == 0) {
				throw new DataRetrievalFailureException(
						"Failed to get the followupId id, unable to get the followupId for ");
			}
			
			inputOutputParamters.put("followupIdPk", followupId);
			inputOutputParamters.put("initialComments", "Y");
			
			resultCount = laftFollowupDocumentDAO.LaftFollowupCommentsUpdate(inputOutputParamters);			
		}
		
		if(resultCount == 1){
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully flagged the Document for LAFT followup.", bindingResult);
		}else {
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to flagged the Document for LAFT followup, Please contact helpline.", bindingResult);
		}
		
		
	}

	public int isLaftFollowupActive(int documentId) {
		
		return laftFollowupDocumentDAO.isLaftFollowupActive(documentId);
	}

	public List<LaftFollowupDetailsVO> getLAFTFollowupDetails(int documentId, UserObject userObject) {
		
		List<LaftFollowupDetailsVO> laftFollowupDetailsDataList = laftFollowupDocumentDAO.getLAFTFollowupDetails(documentId);
		
		for (LaftFollowupDetailsVO laftFollowupDetailsDocument : laftFollowupDetailsDataList) {
			
			List<String> flaggedFollowupDocumentComments = laftFollowupDocumentDAO.getFlaggedLaftFollowupDocumentComments(laftFollowupDetailsDocument.getFollowupIdPk());
			
			String flaggedFollowupDocumentComment = "";
			for (String documentComment : flaggedFollowupDocumentComments) {
					flaggedFollowupDocumentComment += documentComment + "<br><br>";
			}
			laftFollowupDetailsDocument.setReviewComments(flaggedFollowupDocumentComment);
			
			List<String> flaggedFollowupIniComments = laftFollowupDocumentDAO.getFlaggedLaftFollowupInitialComments(laftFollowupDetailsDocument.getFollowupIdPk());
			String flaggedFollowupIniComment = "";
			for (String iniComment : flaggedFollowupIniComments) {
				
				flaggedFollowupIniComment += iniComment + "<br><br>";
				
			}
			laftFollowupDetailsDocument.setInitialComments(flaggedFollowupIniComment);		
			
			laftFollowupDetailsDocument.setLoginUserId(userObject.getWorkerId());
		}
		return laftFollowupDetailsDataList;		
		
	}

	public List<LaftFollowupDetailsVO> getFlaggedLaftFollowupData(UserObject userObject, BindingResult bindingResult) {
		
		List<LaftFollowupDetailsVO> flaggedFollowupDataList = laftFollowupDocumentDAO.getFlaggedLaftFollowupData();
		
		for (LaftFollowupDetailsVO flaggedFollowupDocument : flaggedFollowupDataList) {
			
			List<String> flaggedFollowupDocumentComments = laftFollowupDocumentDAO.getFlaggedLaftFollowupDocumentComments(flaggedFollowupDocument.getFollowupIdPk());
			
			String flaggedFollowupDocumentComment = "";
			for (String documentComment : flaggedFollowupDocumentComments) {
				flaggedFollowupDocumentComment += documentComment + "<br><br>";
			}
			flaggedFollowupDocument.setReviewComments(flaggedFollowupDocumentComment);
			
			List<String> flaggedFollowupIniComments = laftFollowupDocumentDAO.getFlaggedLaftFollowupInitialComments(flaggedFollowupDocument.getFollowupIdPk());
			String flaggedFollowupIniComment = "";
			for (String iniComment : flaggedFollowupIniComments) {
				flaggedFollowupIniComment += iniComment + "<br><br>";
			}
			flaggedFollowupDocument.setInitialComments(flaggedFollowupIniComment);			
		}
		return flaggedFollowupDataList;
	}

	public List<Map<String, String>> getLaftFollowupResolutions() {
		
		return laftFollowupDocumentDAO.getLaftFollowupResolutions();
	}

	@Transactional
	public void resolveLaftFollowupDocuments(int[] followupIdsPk, String resolutionId, String resolutionComments,	UserObject userObject, BindingResult bindingResult) {

		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("workerFullName", userObject.getUserFullName());
		inputParameterMap.put("workerId", userObject.getWorkerId());
		inputParameterMap.put("resolutionId", resolutionId);
		inputParameterMap.put("comments", resolutionComments);
		inputParameterMap.put("initialComments", null);
		
		String message = "LAFT has commented on follow-up for <br><br>";
		String flaggedByEmail = "";
		
		for (int followupIdPk : followupIdsPk) {
			
			inputParameterMap.put("followupIdPk", followupIdPk);
			
			if (CommonUtil.isNotNullAndEmpty(resolutionId)) {
				laftFollowupDocumentDAO.resolveLaftFollowupDocuments(inputParameterMap);
			}
			
				laftFollowupDocumentDAO.LaftFollowupCommentsUpdate(inputParameterMap);
			
				Map<String, Object> emailObj = laftFollowupDocumentDAO.getResolveAndCommentsData(followupIdPk);
				
				message +=  " Document ID : " + emailObj.get("DOCUMENT_ID") + " on STATENO : " + emailObj.get("STATENO") + "<br>";
				flaggedByEmail += emailObj.get("EMAIL_ADDR") + ",";
		}
		
		sendEmailOnResolveComments(flaggedByEmail, message, userObject);
	}
	
	public void sendEmailOnResolveComments(String flaggedByEmail, String message, UserObject userObject) {
		{
			
			String subject = "LAFT Follow-up comments added";
			
			message += "<br><b>DO NOT respond to this email. </b> <br>" + 
					"Please review the stateno in NYEHMS to determine if additional information is needed. Your response should be entered in the comment section of the LAFT follow-up document in question.\r\n" + 
					"<br>";
			
			String userEmailAddress = userObject.getEmailAddress();
			
			userEmailAddress = flaggedByEmail + userEmailAddress;
			
			try {
		
			sendEmail.processEmail(userEmailAddress, subject, message, "LAFT_RESOLVE_COMMENTS", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			} catch (MessagingException messagingException) {
				
				log.error(ExceptionUtils.getStackFrames(messagingException).toString());
				
				throw new DataRetrievalFailureException(subject+"      :         "+message);
				
			}
		}
	}

	public List<LaftFollowupDetailsVO> getFlaggedLaftResolvedData(UserObject userObject, BindingResult bindingResult) {
		
		List<LaftFollowupDetailsVO> flaggedResolvedDataList = laftFollowupDocumentDAO.getFlaggedLaftResolvedData();
		
		for (LaftFollowupDetailsVO flaggedResolvedDocument : flaggedResolvedDataList) {
			
			List<String> flaggedResolvedDocumentComments = laftFollowupDocumentDAO.getFlaggedLaftFollowupDocumentComments(flaggedResolvedDocument.getFollowupIdPk());
			String flaggedFollowupDocumentComment = "";
			for (String documentComment : flaggedResolvedDocumentComments) {
				flaggedFollowupDocumentComment += documentComment + "<br><br>";
			}
			flaggedResolvedDocument.setReviewComments(flaggedFollowupDocumentComment);

			List<String> flaggedResolvedIniComments = laftFollowupDocumentDAO.getFlaggedLaftFollowupInitialComments(flaggedResolvedDocument.getFollowupIdPk());
			String flaggedFollowupIniComment = "";
			for (String iniComment : flaggedResolvedIniComments) {
				flaggedFollowupIniComment += iniComment + "<br><br>";
			}
			flaggedResolvedDocument.setInitialComments(flaggedFollowupIniComment);			
		}

		return flaggedResolvedDataList;
	}
	
	public void updateLaftFollowupDocumentId(int flaggedDocumentId, int newDocumentId) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("flaggedDocumentId", flaggedDocumentId);
		inputOutputParameterMap.put("newDocumentId", newDocumentId);
		laftFollowupDocumentDAO.updateLaftFollowupDocumentId(inputOutputParameterMap);
	}	
	
	public void updateLaftFollowupResolutionForDeletedDocs(int documentId, UserObject userObject) {
		
		Map<Object, Object> inputParameterMap = new HashMap<>();
		inputParameterMap.put("documentId", documentId);
		inputParameterMap.put("workerId", userObject.getWorkerId());
		laftFollowupDocumentDAO.updateLaftFollowupResolutionForDeletedDocs(inputParameterMap);
		
	}

	public void addInitialReviewComments(int followupId, String comments, BindingResult bindingResult, UserObject userObject) {
		
		Map<String, Object> inputParamters = new HashMap<>();
		
		inputParamters.put("followupIdPk", followupId);
		inputParamters.put("workerId", userObject.getWorkerId());
		inputParamters.put("comments", comments);
		inputParamters.put("initialComments", "Y");
			
			int resultCount = laftFollowupDocumentDAO.LaftFollowupCommentsUpdate(inputParamters);		
			
			if(resultCount == 1){
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully added initial review comments.", bindingResult);
			} else {
				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to add initial review comments, Please contact helpline.", bindingResult);
			}			
	}		

}
