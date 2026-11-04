package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.review.NBSReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NBSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class NBSReviewService {
	
	
	private static Logger log = LoggerFactory.getLogger(NBSReviewService.class);
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	private NBSReviewDAO nbsReviewDAO;	
	
	public List<JSONObject> getNbsMnemonicRecords(String birthYear, BindingResult bindingResult, UserObject userObject){
		
		Map<String, String> queryParameters = nbsReviewDAO.getPerinatalReviewQuery("INFANT_MATCH");
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		StringBuilder sqlQuery = new StringBuilder(queryParameters.get("SCREEN_SQL"));
		
		if(!"ALL".equals(birthYear)) {
			sqlQuery.append(" and TO_CHAR(BIRTH_DATE, 'YYYY') = '").append(birthYear).append("'");
		}
		
		if(StringUtils.isNotEmpty(queryParameters.get("ORDERBY"))) {
			sqlQuery.append(queryParameters.get("ORDERBY"));
		}
		 
		inputParameterMap.put("SQL_QUERY", sqlQuery.toString());
		
		
		
		return nbsReviewDAO.getPerinatalReviewRecords(inputParameterMap);
		
	}

	public List<JSONObject> getNonNbsMatchClimsRecords(String birthYear, BindingResult bindingResult, UserObject userObject){
		
		
		Map<String, String> queryParameters = nbsReviewDAO.getPerinatalReviewQuery("CLIMS_NONMATCH");
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		StringBuilder sqlQuery = new StringBuilder(queryParameters.get("SCREEN_SQL"));
		
		if(!"ALL".equals(birthYear)) {
			sqlQuery.append("and TO_CHAR(DOB, 'YYYY') = '").append(birthYear).append("' ");
		}
		
		if(StringUtils.isNotEmpty(queryParameters.get("ORDERBY"))) {
			sqlQuery.append(queryParameters.get("ORDERBY"));
		}
		
		inputParameterMap.put("SQL_QUERY", sqlQuery.toString());
		inputParameterMap.put("SQL_ORDERBY", queryParameters.get("ORDERBY"));
		
		
		return nbsReviewDAO.getPerinatalReviewRecords(inputParameterMap);
	}
	
	public List<JSONObject> getNbsMatchReviewRecords(BindingResult bindingResult, UserObject userObject){
		
		
		Map<String, String> queryParameters = nbsReviewDAO.getPerinatalReviewQuery("MOTHER_MATCH");
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		StringBuilder sqlQuery = new StringBuilder(queryParameters.get("SCREEN_SQL"));
		
		if(StringUtils.isNotEmpty(queryParameters.get("ORDERBY"))) {
			sqlQuery.append(queryParameters.get("ORDERBY"));
		}
		 
		inputParameterMap.put("SQL_QUERY", sqlQuery.toString());		
		
		return nbsReviewDAO.getPerinatalReviewRecords(inputParameterMap);
	}
	
	public List<JSONObject> getNbsIndRecords(String birthYear, BindingResult bindingResult, UserObject userObject){
		
		Map<String, String> queryParameters = nbsReviewDAO.getPerinatalReviewQuery("PERI_EXPO");
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		StringBuilder sqlQuery = new StringBuilder(queryParameters.get("SCREEN_SQL"));
		
		if(!"ALL".equals(birthYear)) {
			sqlQuery.append("and TO_CHAR(birthdt, 'YYYY') = '").append(birthYear).append("' ");
		}
		
		if(StringUtils.isNotEmpty(queryParameters.get("ORDERBY"))) {
			sqlQuery.append(queryParameters.get("ORDERBY"));
		}
		 
		inputParameterMap.put("SQL_QUERY", sqlQuery.toString());
		
		return nbsReviewDAO.getPerinatalReviewRecords(inputParameterMap);
	}
	
	public NBSDocVO getNBSDoc(int docId, String reviewType, BindingResult bindingResult, UserObject userObject) {
		
		 
		 if("NBS_IND".equals(reviewType)) {
			 return nbsReviewDAO.getIndNbsDoc(docId);
		}else {
			return nbsReviewDAO.getNBSDoc(docId);
		}
	 
	
	}
	
	public List<String> getPendingNbsBirthYearList(String screenCode){
		
		
		Map<String, String> queryParameters = nbsReviewDAO.getPerinatalReviewQuery(screenCode);
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		StringBuilder sqlQuery = new StringBuilder(queryParameters.get("SCREEN_SQL"));
		
		if(StringUtils.isNotEmpty(queryParameters.get("ORDERBY"))) {
			sqlQuery.append(queryParameters.get("ORDERBY"));
		}
		 
		inputParameterMap.put("SQL_QUERY", sqlQuery.toString());
		
		return nbsReviewDAO.getNBSYearListToReview(inputParameterMap);
	}
		
	public void matchNBSDocumentToStateno(long documentId, String stateno, String reviewComments, BindingResult bindingResult, UserObject userObject) {
			 Map<String, Object> inOutParameterMap = new HashMap<>();
			 
			 inOutParameterMap.put("p_documentId", documentId);
			 
			 String climsPatientId  = nbsReviewDAO.getClimsPatientId(stateno);
			 
			 inOutParameterMap.put("climsPatientId", climsPatientId);
			 
			 inOutParameterMap.put("stateno", stateno);
			 
			 inOutParameterMap.put("p_reviewComments", reviewComments);
			 
			 inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
			 
			 int resultCount = nbsReviewDAO.matchNBSDocumentToStateno(inOutParameterMap);
			 
			  if(resultCount > 0 && CommonUtil.isNotNullAndEmpty(climsPatientId)) {
				  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the STATENO and PatientId details", bindingResult);
				  
			  }else if(resultCount > 0 && CommonUtil.isNullOrEmpty(climsPatientId)) {
				  commonExceptionHandler.inspectDBTransacionResult(0, "There is no PatientId, updated the STATENO details", bindingResult);
				  
			  }else {
				  commonExceptionHandler.inspectDBTransacionResult(0, "This document already reviewed, please reload the page" , bindingResult);
				  
			  }
	}
	
	@Transactional
	public void updateNBSExposureStatus(long documentId, String exposureStatus, String comments, BindingResult bindingResult,
			UserObject userObject) {
		
			Map<String, Object> inOutParameterMap = new HashMap<>();
			 
			 inOutParameterMap.put("p_documentId", documentId);
			 
			 inOutParameterMap.put("exposureStatus", exposureStatus);
			 
			 inOutParameterMap.put("comments", comments);
			 
			 inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
			 
			 int resultCount = 0;
			 
			 if("N".equals(exposureStatus)) {
				 inOutParameterMap.put("currentProcessStatus", 18);
			 }else {
				 inOutParameterMap.put("currentProcessStatus", 5);
			 }
			 
			 
			 if("Y".equals(exposureStatus)) {
				 
				 	nbsReviewDAO.moveDocToStdNBS(inOutParameterMap);
				 
				 
					Integer statusCode=(Integer)inOutParameterMap.get("p_error_cd");
					
					String transactionMessage=statusCode==null?"Successfully updated the exposure status":(String)inOutParameterMap.get("p_err_msg");
					
					if(statusCode != null && statusCode != 0) {
						log.error("Failed to update the exposure status for DocumentId {}",documentId);
						log.error(transactionMessage);
						
						throw new DataRetrievalFailureException("Failed to update the exposure status "+transactionMessage);
					}
				 
			 }
			 
			 resultCount = nbsReviewDAO.updateNBSExposureStatus(inOutParameterMap);
			 
			 if(resultCount > 0) {
				  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the exposure status", bindingResult);
				  
			  }else {
				  commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the exposure status, please reload the page" , bindingResult);
				  
			  }
			 
			 
	}
	
	public void processNBSDocument(long documentId, String stateno, Long clusterId, String match_cd, String comments,  
			BindingResult bindingResult, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("DOCUMENT_ID", documentId);
		inputParameterMap.put("STATENO", stateno);
		
		inputParameterMap.put("p_comments", comments);
		inputParameterMap.put("p_worker_id", userObject.getWorkerId());
		 
		int resultCount = nbsReviewDAO.updateNBSMotherStatenoDetails(inputParameterMap);
		 
		 if(resultCount > 0) {
			  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the Mother STATENO details", bindingResult);
			  
		  }else {
			  commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the STATENO, please reload the page and try" , bindingResult);
			  
		  }
	}
	
	public Map<String, Object> getClimsDocumentDetailsForMatchReview(Integer documentId, BindingResult bindingResult,
			UserObject userObject){
		Map<String, Object> documentDetailsToReview = new HashMap<>();
		
		try{
		
				
				documentDetailsToReview = nbsReviewDAO.getClimsDocumentDetailsForMatchReview(documentId);

				
				if(documentDetailsToReview != null){
					
					documentDetailsToReview.put("STATUS", "SUCCESS");
					
					
				}else{
					
					documentDetailsToReview = new HashMap<String, Object>();
					
					documentDetailsToReview.put("STATUS", "FAILURE");
					documentDetailsToReview.put("ERROR_MESSAGE", "No documents awaiting for review with the selected criteria.");
					
					return documentDetailsToReview;
				}
		
				
			
			
		
		}catch(Exception exception){
			log.error("Excpetion occured while loading the Climns document for Review");
			log.error(exception.getMessage());
						
			documentDetailsToReview.put("STATUS", "FAILURE");
			documentDetailsToReview.put("ERROR_MESSAGE", "Failed to load the Clims documents for review, please contact helpline");
		}
		
		return documentDetailsToReview;
	}
	
	public Map<String, Object> getNBSDocumentDetailsForMatchReview(Integer documentId, BindingResult bindingResult,
			UserObject userObject){
		Map<String, Object> documentDetailsToReview = new HashMap<>();
		
		try{
		
				
				documentDetailsToReview = nbsReviewDAO.getNBSDocumentDetailsForMatchReview(documentId);

				
				if(documentDetailsToReview != null){
					
					documentDetailsToReview.put("STATUS", "SUCCESS");
					
					
				}else{
					
					documentDetailsToReview = new HashMap<String, Object>();
					
					documentDetailsToReview.put("STATUS", "FAILURE");
					documentDetailsToReview.put("ERROR_MESSAGE", "No documents awaiting for review with the selected criteria.");
					
					return documentDetailsToReview;
				}
		
				
			
			
		
		}catch(Exception exception){
			log.error("Excpetion occured while loading the NBS document for Review");
			log.error(exception.getMessage());
						
			documentDetailsToReview.put("STATUS", "FAILURE");
			documentDetailsToReview.put("ERROR_MESSAGE", "Failed to load the NBS documents for review, please contact helpline");
		}
		
		return documentDetailsToReview;
	}
	
	
	public Map<String, Boolean> checkForClimsRecId(String stateno, BindingResult bindingResult, UserObject userObject){
		
		Map<String, Boolean> climsRecData = new HashMap<>();
		
		
		String climsPatientId  = nbsReviewDAO.getClimsPatientId(stateno);
		
		if(CommonUtil.isNotNullAndEmpty(climsPatientId)) {
			climsRecData.put("hasClimsData", true);
		}else {
			climsRecData.put("hasClimsData", false);
		}
		
		return climsRecData;
		 
	}
	
	public JSONObject getHivFinalStatusList(){
		List<Map<String, String>> hivFinalStatusDetails = nbsReviewDAO.getHivFinalStatusList();
		
		JSONObject hivFinalStatusJsonObj = new JSONObject();
		
		for (Map<String, String> map : hivFinalStatusDetails) {
			
			hivFinalStatusJsonObj.put(map.get("STATUSCD"), map.get("STATUSDESC"));
			
		}
		
		return hivFinalStatusJsonObj;
		
	}
	
	public void updateHivFinalStatus(Integer documentId, String hivFinalStatus, String reviewComments,
			BindingResult bindingResult, UserObject userObject) {

		int resultCount = 0;
		Map<String, Object> inputParameterMap = new HashMap<>();
		inputParameterMap.put("p_documentId", documentId);
		inputParameterMap.put("p_reviewComments", reviewComments);
		inputParameterMap.put("p_hivFinalStatus", hivFinalStatus);
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		
		try{
		
			resultCount = nbsReviewDAO.updateHivFinalStatus(inputParameterMap);
			
		}catch(Exception exception){
			log.error("Excpetion occured while updating the HIV final status information.  DocumentId: {}",documentId);
			log.error(exception.getMessage());
		}
		
		
		 if(resultCount > 0) {
			  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the data", bindingResult);
			  
		  }else {
			  commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the data, please contact helpline" , bindingResult);
			  
		  }
		
	}
	
	public void updateStdNbsReviewComments(Integer documentId, String reviewComments, BindingResult bindingResult,
			UserObject userObject) {
		int resultCount = 0;
		Map<String, Object> inputParameterMap = new HashMap<>();
		inputParameterMap.put("p_documentId", documentId);
		inputParameterMap.put("p_reviewComments", reviewComments);
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		
		try{
		
			resultCount = nbsReviewDAO.updateStdNbsReviewComments(inputParameterMap);
			
		}catch(Exception exception){
			log.error("Excpetion occured while updating the review comments.  DocumentId: {}",documentId);
			log.error(exception.getMessage());
		}
		
		
		 if(resultCount > 0) {
			  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the review comments", bindingResult);
			  
		  }else {
			  commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the data, please contact helpline" , bindingResult);
			  
		  }
	}
	
	public void updateClimsReviewComments(Integer documentId, String reviewComments, BindingResult bindingResult,
			UserObject userObject) {
		int resultCount = 0;
		Map<String, Object> inputParameterMap = new HashMap<>();
		inputParameterMap.put("p_documentId", documentId);
		inputParameterMap.put("p_reviewComments", reviewComments);
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		
		try{
		
			resultCount = nbsReviewDAO.updateClimsReviewComments(inputParameterMap);
			
		}catch(Exception exception){
			log.error("Excpetion occured while updating the review comments.  DocumentId: {}",documentId);
			log.error(exception.getMessage());
		}
		
		
		 if(resultCount > 0) {
			  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the review comments", bindingResult);
			  
		  }else {
			  commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the data, please contact helpline" , bindingResult);
			  
		  }
	}
	
	public void updateRawNBSData(Integer documentId, String reviewComments, BindingResult bindingResult, UserObject userObject) {
		int resultCount = 0;
		Map<String, Object> inputParameterMap = new HashMap<>();
		inputParameterMap.put("p_documentId", documentId);
		inputParameterMap.put("p_reviewComments", reviewComments);
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		
		try{
		
			resultCount = nbsReviewDAO.updateRawNBSData(inputParameterMap);
			
		}catch(Exception exception){
			log.error("Excpetion occured while updating the NBS review comments.  DocumentId: {}",documentId);
			log.error(exception.getMessage());
		}
		
		
		 if(resultCount > 0) {
			  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the data", bindingResult);
			  
		  }else {
			  commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the data, please contact helpline" , bindingResult);
			  
		  }
	}
	
	public List<Map<String, Object>> getNBSSearchViewResults(String labId, String firstName, String lastName, String labNo,
			String medRecordNumber, String dateOfBirth, String pfi, String gender, String weight, BindingResult bindingResult, UserObject userObject){
		Map<String, String> inputParameterMap = new HashMap<>();
		if(CommonUtil.isNotNullAndEmpty(labId)) {
			inputParameterMap.put("p_labId", labId.replaceAll("\\*", "%"));			
		}
		if(CommonUtil.isNotNullAndEmpty(firstName)) {
			inputParameterMap.put("p_firstName", firstName.replaceAll("\\*", "%"));			
		}
		if(CommonUtil.isNotNullAndEmpty(lastName)) {
			inputParameterMap.put("p_lastName", lastName.replaceAll("\\*", "%"));			
		}
		if(CommonUtil.isNotNullAndEmpty(labNo)) {
			inputParameterMap.put("p_labNo", labNo.replaceAll("\\*", "%"));			
		}
		if(CommonUtil.isNotNullAndEmpty(medRecordNumber)) {
			inputParameterMap.put("p_medRecordNumber", medRecordNumber.replaceAll("\\*", "%"));			
		}
		if(CommonUtil.isNotNullAndEmpty(pfi)) {
			inputParameterMap.put("p_pfi", pfi.replaceAll("\\*", "%"));			
		}
		if(CommonUtil.isNotNullAndEmpty(gender)) {
			inputParameterMap.put("p_gender", gender);			
		}
		if(CommonUtil.isNotNullAndEmpty(weight)) {
			inputParameterMap.put("p_weight", weight);			
		}
		if(CommonUtil.isNotNullAndEmpty(dateOfBirth)) {
			inputParameterMap.put("p_dateOfBirth", dateOfBirth);			
		}
		
		return nbsReviewDAO.getNBSSearchViewResults(inputParameterMap);
	}
	
	public void matchClimsDocumentToNBS(String patientId, long nbsDocumentId, String reviewComments,
			BindingResult bindingResult, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		inputParameterMap.put("p_patientId", patientId);
		inputParameterMap.put("p_nbsDocumentId", nbsDocumentId);
		inputParameterMap.put("p_reviewComments", reviewComments);
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		
		
		int resultCount = nbsReviewDAO.matchClimsDocumentToNBS(inputParameterMap);
		
		if(resultCount > 0) {
			
			nbsReviewDAO.updateClimsDocumentToStdNBS(inputParameterMap);
			
		}
		
		 if(resultCount > 0) {
			  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully matched CLIMS document to NBS document", bindingResult);
			  
		  }else {
			  commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to match CLIMS document with NBS document, please refresh the page" , bindingResult);
			  
		  }
		
	}
	
	public List<String> getClimsNonMatchBirthYearList(){
		
		Map<String, String> queryParameters = nbsReviewDAO.getPerinatalReviewQuery("CLIMS_MATCH_YR");
		
		Map<String, String> inputParameterMap = new HashMap<>();
		

		StringBuilder sqlQuery = new StringBuilder(queryParameters.get("SCREEN_SQL"));
		
		if(StringUtils.isNotEmpty(queryParameters.get("ORDERBY"))) {
			sqlQuery.append(queryParameters.get("ORDERBY"));
		}
		 
		inputParameterMap.put("SQL_QUERY", sqlQuery.toString());
		
		
		return nbsReviewDAO.getNBSYearListToReview(inputParameterMap);
	}
}
