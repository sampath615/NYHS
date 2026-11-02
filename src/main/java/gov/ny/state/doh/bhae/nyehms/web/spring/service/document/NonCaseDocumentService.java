package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.NonCaseDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NonCaseDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersonTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

/*
 * Author 	: Sampath Anumolu
 * Date 	: 11/28/17
 */

@Service
public class NonCaseDocumentService {

	private static final Logger log = LoggerFactory.getLogger(NonCaseDocumentService.class);
	


	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	NonCaseDocumentDAO nonCaseDocumentDAO;
	
	@Autowired
	CommonDocumentService commonDocumentService;
	
	@Autowired
	PersonTransactionHelper personTransactionHelper;
	
	@Transactional
	public void createNonCaseDocument(int triggeringDocId, String comments,
			int reason, BindingResult bindingResult, UserObject userObject){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("triggeringDocId", triggeringDocId);
		inputParameterMap.put("flagActive", "Y");
		
		int resultCount = nonCaseDocumentDAO.updateNonCaseStatusInPDOC(inputParameterMap);
		
		if(resultCount == 0){
			
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Input Document is not in Active Status", bindingResult);
			
			return;
			
		}
		
		inputParameterMap.put("comments", comments);
		inputParameterMap.put("reason", reason);
		inputParameterMap.put("workerId", userObject.getWorkerId());
		inputParameterMap.put("workerFullName", userObject.getUserFullName());
		
		
		
		nonCaseDocumentDAO.createNonCaseDocument(inputParameterMap);
		
	}
	
	public NonCaseDocVO getNonCaseDoc(int docId){
		return nonCaseDocumentDAO.getNonCaseDoc(docId);
	}
	
	public void addCommentsToNonCaseDoc(int triggeringDocId, String comments,
			BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false;
		
		try{
			
			
			Map<String, Object> inputParameterMap = new HashMap<>();
			
			inputParameterMap.put("triggeringDocId", triggeringDocId);
			inputParameterMap.put("comments", comments);
			inputParameterMap.put("workerFullName", userObject.getUserFullName());
			
			
			nonCaseDocumentDAO.addCommentsToNonCaseDoc(inputParameterMap);
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to add the comments to Noncase doc {}",triggeringDocId);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to add the comments to Noncase doc {}",triggeringDocId);
			log.error(exception.getMessage());
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to add the comments to Non Case Document. Please contact helpline.", bindingResult);
		}else if(!isError && !bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully added the comments to Non Case document", bindingResult);
		}
		
		
		
	}
	
	public void changeNonCaseActiveFlagStatus(int triggeringDocId,
			String flagActive, BindingResult bindingResult,
			UserObject userObject){
		
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("triggeringDocId", triggeringDocId);
		inputParameterMap.put("flagActive", flagActive);
		
		int resultCount = nonCaseDocumentDAO.updateNonCaseStatusInPDOC(inputParameterMap);
		
		if(resultCount == 0){
			
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Input Document is not in Active Status", bindingResult);
			
			return;
			
		}
		
		inputParameterMap.put("workerId", userObject.getWorkerId());
		
		nonCaseDocumentDAO.changeNonCaseActiveFlagStatus(inputParameterMap);
		
	}
	
	public List<Map<String, String>> getNonCaseReasons(){
		return nonCaseDocumentDAO.getNonCaseReasons();
	}

	public String flagAsNonCase(FlaggedStatenoVO flaggedStatenoVO, String reason, BindingResult bindingResult,
			UserObject userObject) {

		StringBuilder transactionMessage = new StringBuilder();

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_stateno", flaggedStatenoVO.getStateno1());
		inOutParameterMap.put("p_flagged_by", userObject.getWorkerId());
		inOutParameterMap.put("p_request_comments", flaggedStatenoVO.getRequestComments());
		inOutParameterMap.put("p_reason", reason);

		int resultCount = checkForActiveNonCaseStateno(flaggedStatenoVO.getStateno1());

		if (resultCount > 0) {
			transactionMessage.append("STATENO already flagged as Non case, please reload the page");
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage.toString(), bindingResult);

			return transactionMessage.toString();
		}

		String eharsStateno = commonDocumentService.getEharsStateno(flaggedStatenoVO.getStateno1());
		if (CommonUtil.isNotNullAndEmpty(eharsStateno)) {
			inOutParameterMap.put("p_eharsStateno", eharsStateno);
		}

		Map<String, String> nycStatenoDetails = commonDocumentService
				.getNYCStatenoDetails(flaggedStatenoVO.getStateno1());

		String nycStateno = null;
		String nycCityno = null;

		if (nycStatenoDetails != null && CommonUtil.isNotNullAndEmpty(nycStatenoDetails.get("EHARS_STATENO"))) {
			nycStateno = nycStatenoDetails.get("EHARS_STATENO");
			inOutParameterMap.put("p_nycStateno", nycStatenoDetails.get("EHARS_STATENO"));
		}

		if (nycStatenoDetails != null && CommonUtil.isNotNullAndEmpty(nycStatenoDetails.get("CITYNO"))) {
			nycCityno = nycStatenoDetails.get("CITYNO");
			inOutParameterMap.put("p_cityno", nycStatenoDetails.get("CITYNO"));
		}

		resultCount = nonCaseDocumentDAO.flagAsNonCase(inOutParameterMap);

		if (resultCount > 0) {

			if (CommonUtil.isNotNullAndEmpty(flaggedStatenoVO.getNonCaseFlag())
					&& flaggedStatenoVO.getNonCaseFlag().equals("Y")) {
				transactionMessage.append(" and flagged as a Non-Case.<br/>");
				if (CommonUtil.isNotNullAndEmpty(eharsStateno)) {

					transactionMessage.append("This person is known as eHARS STATENO ").append(eharsStateno)
							.append(" in ROS eHARS, please delete this person and all documents from ROS eHARS.<br/>");
				}

			} else {
				transactionMessage.append(
						"<span class='bold'>**Please ensure that you have discarded and/or deleted documents that incorrectly caused this to appear as a case in NYEHMS</span><br/><br/>");

				if (CommonUtil.isNotNullAndEmpty(eharsStateno)) {

					transactionMessage.append("This person is known as eHARS STATENO ").append(eharsStateno).append(
							" in ROS eHARS, please delete this person and all documents from ROS eHARS.<br/><br/>");
				}

				if (CommonUtil.isNotNullAndEmpty(nycStateno) || CommonUtil.isNotNullAndEmpty(nycCityno)) {

					transactionMessage.append("This persons is known as ");

					if (CommonUtil.isNotNullAndEmpty(nycStateno)) {
						transactionMessage.append("eHARS STATENO ").append(nycStateno);
					}

					if (CommonUtil.isNotNullAndEmpty(nycStateno) && CommonUtil.isNotNullAndEmpty(nycCityno)) {
						transactionMessage.append(" and ");
					}

					if (CommonUtil.isNotNullAndEmpty(nycCityno)) {
						transactionMessage.append("CITYNO  ").append(nycCityno);
					}

					transactionMessage.append(
							" in NYC eHARS, this information will be stored for follow-up communication with NYC<br/><br/>");

					if (CommonUtil.isNullOrEmpty(nycCityno) && CommonUtil.isNullOrEmpty(nycStateno)
							&& CommonUtil.isNullOrEmpty(eharsStateno)) {
						transactionMessage.append(
								"(if the person is not in either eHARS) This STATENO does not exist in either eHARS, no further follow-up is necessary.");
					}

				}
			}

			commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage.toString(), bindingResult);
		} else {
			transactionMessage.append("Unable to flag stateno as Non Case");
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage.toString(), bindingResult);
		}

		return transactionMessage.toString();
	}

	public int unflagStatenoAsNonCase(FlaggedStatenoVO flaggedStatenoVO, UserObject userObject) {
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("p_current_stateno", flaggedStatenoVO.getStateno2());
		inOutParameterMap.put("p_flagged_stateno", flaggedStatenoVO.getStateno1());
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		inOutParameterMap.put("p_review_comments",
				flaggedStatenoVO.getRequestComments());

		return nonCaseDocumentDAO.unflagAsNonCase(inOutParameterMap);
	}
	
	public String unflagAsNonCase(FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult, UserObject userObject) {
		
		boolean isError = false;
		String newStateno = null;
		try{
			
			String eharsStateno = commonDocumentService.getEharsStateno(flaggedStatenoVO.getStateno1());
			
			if(CommonUtil.isNotNullAndEmpty(eharsStateno)) {
				newStateno = personTransactionHelper.unflagAsNonCase(flaggedStatenoVO, bindingResult, userObject);
			}else {
				newStateno = flaggedStatenoVO.getStateno1();
				flaggedStatenoVO.setStateno2(newStateno);
				unflagStatenoAsNonCase(flaggedStatenoVO, userObject);
			}
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to unflag from noncase status {}",flaggedStatenoVO.getStateno1());
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to unflag from noncase status {}",flaggedStatenoVO.getStateno1());
			log.error(exception.getMessage());
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Unable to unflag the Non case status", bindingResult);
		}else if(!isError && !bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(0, "Unflagged from Non Case status", bindingResult);
		}
		
		return newStateno;
		
	}
	
	@Transactional
	public void discardNonCaseDocument(Map<String, Object> inputParameterMap, int reason, BindingResult bindingResult, UserObject userObject){
		
		
		
		if(reason != 1) {
			int resultCount = nonCaseDocumentDAO.updateNonCaseStatusInPDOC(inputParameterMap);
			if(resultCount == 0){
				commonExceptionHandler.inspectDBTransacionResult(-1,
						"Input Document is not in Active Status", bindingResult);
				return;
				
			}
		}
		
		nonCaseDocumentDAO.discardNonCaseDocument(inputParameterMap);
		
	}
	
	public List<Map<String, String>> getNonCaseDocHistory(String stateNo, BindingResult bindingResult,
			UserObject userObject){
		return nonCaseDocumentDAO.getNonCaseDocHistory(stateNo);
	}

	public Map<String, String> getDocDelDiscardHistory(int documentId, BindingResult bindingResult, UserObject userObject) {
		
		return nonCaseDocumentDAO.getDocDelDiscardHistory(documentId);
	}
	
	public List<Map<String, String>> getNonCaseEharsStatenos(String nonCasePernitalExpOnly, BindingResult bindingResult, UserObject userObject){
		
		Map<String, String> inputParameterMap = new HashMap<>();
		inputParameterMap.put("p_nonCasePernitalExpOnly", nonCasePernitalExpOnly);
		
		return nonCaseDocumentDAO.getNonCaseEharsStatenos(inputParameterMap);
		
		
	}
	
	public List<Map<String, String>> getNonCaseFlaggedHistory(String FROM_DATE, String TO_DATE, String ACTIVE_FLAG, BindingResult bindingResult, UserObject userObject){
		Map<String, String> inputParameterMap = new HashMap<>();
		inputParameterMap.put("FROM_DATE", FROM_DATE);
		inputParameterMap.put("TO_DATE", TO_DATE);
		inputParameterMap.put("ACTIVE_FLAG", ACTIVE_FLAG);
		return nonCaseDocumentDAO.getNonCaseFlaggedHistory(inputParameterMap);
	}

	public Map<Object, Object> getReasonCodeForNonCase(String colsureCategory, BindingResult bindingResult,
			UserObject userObject) {
		
		return nonCaseDocumentDAO.getReasonCodeForNonCase(colsureCategory);
	}

	public int checkForActiveNonCaseStateno(String stateno) {

		return nonCaseDocumentDAO.checkForActiveNonCaseStateno(stateno);
	}

}
