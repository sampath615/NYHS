package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.AssignmentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.PRFDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;


/**
* This class is service interface between the user data to Database(MyBatis related configuration)
* 
* It renders the data from Database in the form List, Int, Sting etc uinsg MyBatis configuation file 
*  
* @author  
* @version 
*/
@Service
public class DocumentsReviewService {

	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

	

	@Autowired
	private DocumentReviewTransactionHelper documentReviewTransactionHelper;
	
	private static Logger log = LoggerFactory.getLogger(DocumentsReviewService.class);
	
	
	@Autowired
    PRFDocumentDAO pRFDocumentDAO;
	

	@Autowired
    AssignmentDAO assignmentDAO;
	
	/**
	 * {@inheritDoc}
	 */
	public List<JSONObject> getprfReviewDocumentsData(BindingResult bindingResult, UserObject userObject){
		
		List<JSONObject> prfReviewDocumentsList =  pRFDocumentDAO.getprfReviewDocumentsData();
		
		return prfReviewDocumentsList;
	}
	
	public void reviewAndUpdateAndFlagNonAsCaseTheStatusOfClosedAssignment(String colsureCategory, String stateno,int assignmentId,
			String status, BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		String transactionMessage = null;
		String nonCaseTransactionMessage = null;
		try{
		nonCaseTransactionMessage =	documentReviewTransactionHelper.reviewAndUpdateAndFlagNonAsCaseTheStatusOfClosedAssignment(colsureCategory, stateno,assignmentId, status, bindingResult,userObject);
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = "Failed to " + status + " the Assignment";
			log.error(assignmentId + "    " + transactionMessage);

			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			transactionMessage = "Failed to "+status+" the Assignment";
			log.error(assignmentId+"       "+transactionMessage);
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			if(CommonUtil.isNotNullAndEmpty(nonCaseTransactionMessage) ) {
				commonExceptionHandler.inspectDBTransacionResult(-1, nonCaseTransactionMessage, bindingResult);
			}else {
				commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage  +", Please contact helpline.", bindingResult);
			}
			
		}else{
			if(CommonUtil.isNotNullAndEmpty(nonCaseTransactionMessage)) {
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully "+status+"d the Assignment" + nonCaseTransactionMessage, bindingResult);
			}else {
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully "+status+"d the Assignment.", bindingResult);
			}
			
		}
	}
	
	public int updateTheReviewStatusInSurvAssignment(int assignmentId,
			UserObject userObject){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<Object, Object>();
		
		inputOutputParameterMap.put("assignmentId", assignmentId);
		inputOutputParameterMap.put("workerId", userObject.getWorkerId());
		
		return assignmentDAO.updateTheReviewStatusInSurvAssignment(inputOutputParameterMap);
	}
	
	
	public int addAssignmentActivity(int assignmentId,
			String comments, String activityType, UserObject userObject){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<Object, Object>();
		
		inputOutputParameterMap.put("assignmentId", assignmentId);
		inputOutputParameterMap.put("activityType", activityType);
		inputOutputParameterMap.put("comments", comments);
		inputOutputParameterMap.put("workerId", userObject.getWorkerId());
		
		return assignmentDAO.addAssignmentActivity(inputOutputParameterMap);
	}
	
	public void agreeAndDeleteDocumentForClosedAssignment(int assignmentId,
			int documentId, String documentType, BindingResult bindingResult,
			UserObject userObject){
		
		boolean isError = false; 
		String transactionMessage = null;
		try{
			documentReviewTransactionHelper.agreeAndDeleteDocumentForClosedAssignment(assignmentId, documentId, documentType, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = "Failed to Agree the Closed Assignment";
			log.error(assignmentId+"    "+transactionMessage);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			transactionMessage = "Failed to Agree the Closed Assignment";
			log.error(assignmentId+"       "+transactionMessage);
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage+", Please contact helpline.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully Agreed and deleted the document for the Closed Assignment", bindingResult);
		}
	}
	
	public List<Map<String, String>> getVlDocumentReviewCategories(){
		return assignmentDAO.getVlDocumentReviewCategories();
	}
	
	
	public List<JSONObject> getVlStatenosToReview(String reviewCategory, 
			BindingResult dummyBindingResult, UserObject userObject){
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("reviewCategory", reviewCategory);
		return assignmentDAO.getVlStatenosToReview(inputParameterMap);
	}
	
	public void updateVlDocumentStatus(String stateno, String reviewCategory, String requestComments, BindingResult bindingResult,
			UserObject userObject) {
		try{
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			
			inputParameterMap.put("stateno", stateno);
			inputParameterMap.put("reviewCategory", reviewCategory);
			inputParameterMap.put("requestComments", requestComments);
			inputParameterMap.put("workerId", userObject.getWorkerId());
			
			
			int resultCount = assignmentDAO.updateVlDocumentStatus(inputParameterMap);
			
			if(resultCount > 0) {
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the VL document status", bindingResult);
			}else {
				commonExceptionHandler.inspectDBTransacionResult(-1, "Unable to update the VL document status, please refersh the page", bindingResult);
			}
			
			
		}catch(Exception exception){
			log.error("Failed to update VL document status: {}, stateno: {}", reviewCategory, stateno);
			log.error(exception.getMessage());
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update VL document status, please refresh. If the error still persists, please contact helpline", bindingResult);
		}
		
	}
	
}
