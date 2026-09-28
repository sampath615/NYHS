package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CancerDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.ClericalReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.DocumentReviewTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/*
 * ACRF JSP is treated as 3 parts for all CRUD operations. 
 * One is ACRF Doc table itself 2nd is ACRF Labs table and 3rd ACRF CD4 Table.
 * So we define CRUD (only select, insert, update)) operation separately for three tables even though 
 * it is single operation from user view.
 * This is done to modularize code so that each table CRUD can be handles separately in future.
 */	
@Service
public class ClericalReviewService {
	
	
	private static Logger log = LoggerFactory.getLogger(ClericalReviewService.class);
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	private ClericalReviewDAO clericalReviewDAO;	
	
	@Autowired
	private CancerDocumentDAO cancerDocumentDAO;
		
	@Autowired
	DocumentReviewTransactionHelper documentReviewTransactionHelper;
	
	
	public Map<String, Object> getDocumentIdAndPotentialMatchesForStatenos(String documentTypeToReview, String reviewType, BindingResult result, UserObject userObject) {
		
		
		 Map<String, Object> inOutParameterMap = new HashMap<>();
		 
		 inOutParameterMap.put("documentToReview", documentTypeToReview);
		 
		 inOutParameterMap.put("reviewType", reviewType);
			
		
		 List<Integer> clericalDocuments = clericalReviewDAO.getClericalDocuments(inOutParameterMap);
		 
		 Random randomNumber = new Random();
		 
		 Integer documentId = null;
		  
		  if(clericalDocuments.size() > 0){
		 
			  documentId = clericalDocuments.get(randomNumber.nextInt(clericalDocuments.size()));
			  
			  inOutParameterMap.put("documentId", documentId);
					  
			  inOutParameterMap = clericalReviewDAO.getDocumentIdAndPotentialMatchesForStatenos(inOutParameterMap);
		  }
		 
		 
		 if(inOutParameterMap.size() > 0){
			 
			 String documentType = (String)inOutParameterMap.get("documentType");
			 
			 if(CommonUtil.isNotNullAndEmpty(documentType) && ("DOCCS".equals(documentType) || "DIN_DOCCS".equals(documentType))){
				 
				 //BigDecimal documentId = (BigDecimal)inOutParameterMap.get("documentId");
				 
				 List<String> potentialStatenos = clericalReviewDAO.getPotentialStatenosForDinDoccsDocument(documentId);
				 
				 inOutParameterMap.put("stateno", potentialStatenos);
			 }
			 
			 inOutParameterMap.put("totalDocuments", clericalDocuments.size());
			 return inOutParameterMap;
		 }else{
			 return new HashMap<String, Object>();
		 }
		 
		
		
	}
	
	public Map<String, Object> getDocumentIdAndPotentialMatchesForStatenosForQSReview(String filterCriteriaForStatusCode, String filterCriteriaForFileName, String filterCriteriaForFileType, String documentType,  
			BindingResult bindingResult, UserObject userObject){
		
		
		Map<String, Object> documentDetailsToReview = new HashMap<>();
		
		try{
		
			Map<Object, Object> inOutParamaterMap = new HashMap<>();
			
			inOutParamaterMap.put("filterCriteriaForStatusCode", filterCriteriaForStatusCode);
			inOutParamaterMap.put("filterCriteriaForFileName", filterCriteriaForFileName);
			inOutParamaterMap.put("filterCriteriaForFileType", filterCriteriaForFileType);
			inOutParamaterMap.put("tableName", LabCommonUtils.getStdTableName(documentType));
			inOutParamaterMap.put("documentType", documentType);
			
			
			Map<String, String> stdReferenceColumnNames = LabCommonUtils.getStdReferenceColumnNames(documentType);
			
			inOutParamaterMap.put("fileName", stdReferenceColumnNames.get("FILE_NAME"));
			
			
			int documentsCountToReview = 0;
			
			
			
			if("RESISTANCE".equals(documentType) || "ADAP".equals(documentType)){
				documentsCountToReview = clericalReviewDAO.getDocumentsCountToReviewWithSearchCriteriaByParent(inOutParamaterMap);
			}else{
				documentsCountToReview = clericalReviewDAO.getDocumentsCountToReviewWithSearchCriteria(inOutParamaterMap);
			}
			
			
			if(documentsCountToReview == 0){
				documentDetailsToReview.put("STATUS", "FAILURE");
				documentDetailsToReview.put("ERROR_MESSAGE", "No documents awaiting for review with the selected criteria.");
			}else{
				Map<String, Object> documentToReview =  null;
				
				if("RESISTANCE".equals(documentType) || "ADAP".equals(documentType)){
					documentToReview = clericalReviewDAO.getQualityStagedDocumentToReviewByParent(inOutParamaterMap);
				}else{
					documentToReview = clericalReviewDAO.getQualityStagedDocumentToReview(inOutParamaterMap);
				}
				
				if(documentToReview != null){
					String matchCd = (String)documentToReview.get("MATCH_CD");
					BigDecimal document_id = (BigDecimal)documentToReview.get("DOCUMENT_ID");
					
					documentDetailsToReview.put("MATCH_CD", matchCd);
					documentDetailsToReview.put("DOCUMENT_ID", document_id);
					
					List<String> statenoList = null;
					if("C".equals(matchCd)){
						statenoList = clericalReviewDAO.getStatnosForClericalDocument(document_id);
						
						if(statenoList == null){
							log.error("Failed to get the Clerical Matching stateno list from RAW_DOCUMENT_MATCH_RESULTS for the DocumentId : {}",document_id
									);
							log.error("{}  don't have matching potentian statenos", document_id);
							
							throw new DataRetrievalFailureException("unable to load the "+documentType+" document for review");
						}
						
						documentDetailsToReview.put("STATENO_LIST", statenoList);
					}else if("M".equals(matchCd)){
						
						inOutParamaterMap.put("DOCUMENT_ID", document_id);
						
						statenoList = clericalReviewDAO.getMatchingStatnoForDocument(inOutParamaterMap);
						
						if(statenoList == null){
							log.error("Failed to get the Matching stateno from STD table for the DocumentId : {}",document_id
									);
							log.error("{}  don't have matching stateno", document_id);
							
							throw new DataRetrievalFailureException("unable to load the "+documentType+" document for review");
						}
						
						
						documentDetailsToReview.put("STATENO_LIST", statenoList);
					}
					documentDetailsToReview.put("STATUS", "SUCCESS");
					
					documentDetailsToReview.put("documentsToReview", documentsCountToReview);
					
				}else{
					documentDetailsToReview.put("STATUS", "FAILURE");
					documentDetailsToReview.put("ERROR_MESSAGE", "No documents awaiting for review with the selected criteria.");
				}
			}
				
			
			
		
		}catch(Exception exception){
			log.error("Excpetion occured while loading the {} document for Review",documentType);
			log.error(exception.getMessage());
						
			documentDetailsToReview.put("STATUS", "FAILURE");
			documentDetailsToReview.put("ERROR_MESSAGE", "Failed to load the "+documentType+" documents for review, please contact helpline");
		}
		
		return documentDetailsToReview;
	}
	
	public Map<String, Object> getDocumentIdAndPotentialMatchesForStatenosForDFReview(String filterCriteriaForStatusCode, String filterCriteriaForFileName, String filterCriteriaForFileType, String documentType,  
			BindingResult bindingResult, UserObject userObject){
		
		Map<String, Object> documentDetailsToReview = new HashMap<>();
		
		try{
		
			Map<Object, Object> inOutParamaterMap = new HashMap<>();
			
			
			inOutParamaterMap.put("filterCriteriaForFileName", filterCriteriaForFileName);
			inOutParamaterMap.put("filterCriteriaForFileType", filterCriteriaForFileType);
			//inOutParamaterMap.put("tableName", LabCommonUtils.getStdTableName(documentType));
			
			if("RESISTANCE".equals(documentType)) {
				inOutParamaterMap.put("filterCriteriaForStatusCode", "N");
			}else if("CANCER".equals(documentType)) {
				inOutParamaterMap.put("filterCriteriaForStatusCode", "C");
			}else {
				inOutParamaterMap.put("filterCriteriaForStatusCode", filterCriteriaForStatusCode);
			}
			
			inOutParamaterMap.put("documentType", LabCommonUtils.getDocumentType(documentType));
			
			//Map<String, String> stdReferenceColumnNames = LabCommonUtils.getStdReferenceColumnNames(documentType);
			
			//inOutParamaterMap.put("fileName", stdReferenceColumnNames.get("FILE_NAME"));
			
			
			int documentsCountToReview = 0;
			
			if("CANCER".equals(documentType)) {
				documentsCountToReview = cancerDocumentDAO.getTotalCancerDocCount();
			}else {
				documentsCountToReview = clericalReviewDAO.getTotalDocCountToReviewInDataFluxStage(inOutParamaterMap);
			}
			
			
			
			if(documentsCountToReview == 0){
				documentDetailsToReview.put("STATUS", "FAILURE");
				documentDetailsToReview.put("ERROR_MESSAGE", "No documents awaiting for review with the selected criteria.");
				
				return documentDetailsToReview;
			}else{
				//Map<String, Object> documentToReview =  null;
				if("CANCER".equals(documentType)) {
					documentDetailsToReview = cancerDocumentDAO.getCancerDocumentToReview(inOutParamaterMap);
				}else {
					documentDetailsToReview = clericalReviewDAO.getDatafluxStagedDocumentToReview(inOutParamaterMap);
				}
				
				
				
				if(documentDetailsToReview != null){
					
					documentDetailsToReview.put("documentsToReview", documentsCountToReview);
					
					
//							log.error("Failed to get the Clerical Matching stateno list from RAW_DOCUMENT_MATCH_RESULTS for the DocumentId : "+document_id
//							log.error("Failed to get the Matching stateno from STD table for the DocumentId : "+document_id
					documentDetailsToReview.put("STATUS", "SUCCESS");
					
					//documentDetailsToReview.put("documentsToReview", documentsCountToReview);
					
				}else{
					
					documentDetailsToReview = new HashMap<String, Object>();
					
					documentDetailsToReview.put("STATUS", "FAILURE");
					documentDetailsToReview.put("ERROR_MESSAGE", "No documents awaiting for review with the selected criteria.");
					
					return documentDetailsToReview;
				}
			}
				
			
			
		
		}catch(Exception exception){
			log.error("Excpetion occured while loading the {} document for Review",documentType);
			log.error(exception.getMessage());
						
			documentDetailsToReview.put("STATUS", "FAILURE");
			documentDetailsToReview.put("ERROR_MESSAGE", "Failed to load the "+documentType+" documents for review, please contact helpline");
		}
		
		return documentDetailsToReview;
	}
	
	
	public void  matchDocumentToStateno(long documentId, String stateno, BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		try{
			Map<Object, Object> inOutParamaterMap = new HashMap<>();
			inOutParamaterMap.put("p_raw_document_id", documentId);
			inOutParamaterMap.put("p_mtch_stateno", stateno);
			inOutParamaterMap.put("p_worker_id", userObject.getWorkerId());
			
			clericalReviewDAO.matchDocumentToStateno(inOutParamaterMap);
			
			statusCode=(Integer)inOutParamaterMap.get("p_code_out");
			transactionMessage=statusCode==0?"Document mapped to STATENO:"+stateno:(String)inOutParamaterMap.get("p_msg_out");
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		}catch(Exception e){
			log.error("Unable to match the document ({}) to stateno : {}",documentId,stateno);
			log.error(e.getMessage());
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to match the document ("+documentId+") to stateno, please contact helpline", bindingResult);
		}
		
	}
	
	public void  noMatchForADocument(long documentId, BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		try{
			Map<Object, Object> inOutParamaterMap = new HashMap<>();
			inOutParamaterMap.put("p_raw_document_id", documentId);
			inOutParamaterMap.put("p_worker_id", userObject.getWorkerId());
			
			clericalReviewDAO.noMatchForADocument(inOutParamaterMap);
			
			statusCode=(Integer)inOutParamaterMap.get("p_code_out");
			
			if(statusCode==0){
				
				String newStateNo = (String)inOutParamaterMap.get("p_stateno_out");
				
				if(newStateNo == null){
					transactionMessage = "Document non matched.";
				}else{
					transactionMessage = "A new STATENO "+newStateNo+" was generated by the system and the document was associated to it";
				}
				
			}else{
				transactionMessage = (String)inOutParamaterMap.get("p_msg_out");
			}
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		}catch(Exception e){
			log.error("{}  Failed, while updating the document status to Not a match", documentId);
			log.error(e.getMessage());
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the document("+documentId+") status to Not a match, please contact helpline", bindingResult);
		}
		
	}
	
	public void qualityStagedDocumentManualReview(long documentId, String stateno,
			String matchCode, String documentType, BindingResult bindingResult, UserObject userObject){
		
		boolean isErroredOut = false;
		try{
			Integer statusCode=null;
			
			Map<Object, Object> inOutParamaterMap = new HashMap<>();
			inOutParamaterMap.put("p_raw_document_id", documentId);
			inOutParamaterMap.put("p_mtch_stateno", stateno);
			inOutParamaterMap.put("p_mtch_cd", matchCode);
			inOutParamaterMap.put("documentType", documentType);
			inOutParamaterMap.put("p_worker_id", userObject.getWorkerId());
			
			if("EPRF_OOC".equals(documentType)){
				clericalReviewDAO.qualityStagedDocumentManualReviewForEPRFOOC(inOutParamaterMap);
				
				
				
				//ePRFOOChDocumentDAO.updateFileMatchReviewDate(documentId);
				
				
			}else if("ADAP".equals(documentType)){
				clericalReviewDAO.qualityStagedDocumentManualReviewForADAP(inOutParamaterMap);
			}else if("RESISTANCE".equals(documentType)){
				clericalReviewDAO.qualityStagedDocumentManualReviewForResistance(inOutParamaterMap);
			}else if("NYC_IJR".equals(documentType)){
				clericalReviewDAO.qualityStagedDocumentManualReviewForNycIJR(inOutParamaterMap);
			}else{
				clericalReviewDAO.qualityStagedDocumentManualReviewForICCR(inOutParamaterMap);
			}
			
			
			statusCode=(Integer)inOutParamaterMap.get("p_code_out");
			
			if(statusCode == 0){
				
				if("N".equals(matchCode) || "C_N".equals(matchCode) || "P_N".equals(matchCode)){
					commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the document("+documentId+") status as Not a match", bindingResult);
				}else{
					commonExceptionHandler.inspectDBTransacionResult(0, "Successfully matched the document("+documentId+") to the stateno : "+stateno, bindingResult);
				}
				
				
				
			}else{
				isErroredOut = true;
				log.error("{}  Failed to update the document match code status to {}",documentId, matchCode);
				log.error(inOutParamaterMap.get("p_code_out").toString());
			}
		}catch(Exception e){
			log.error("{}  Failed to update the document match code status to {}", documentId,matchCode);
			log.error(e.getMessage());
			isErroredOut = true;
		}
		
		if(isErroredOut){
			if("N".equals(matchCode) || "C_N".equals(matchCode) || "P_N".equals(matchCode)){
				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the document("+documentId+") status to Not a match, please contact helpline", bindingResult);
			}else{
				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to match the document ("+documentId+") to stateno, please contact helpline", bindingResult);
			}
		}
		
	}
	
	public List<String> getQualityStageStatusCodesForTheFileName(
			String fileName, String documentType, BindingResult bindingResult,
			UserObject userObject){
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		Map<String, String> stdColumnNames = LabCommonUtils.getStdReferenceColumnNames(documentType); 
		
		inputParameterMap.put("fileName", fileName);
		
		inputParameterMap.put("fileNameRefColumn", stdColumnNames.get("FILE_NAME"));
		
		inputParameterMap.put("stdTableName", LabCommonUtils.getStdTableName(documentType));
		
		return clericalReviewDAO.getQualityStageStatusCodesForTheFileName(inputParameterMap);
		
	}
	
	public List<String> getAvailabeDocumentTypes(String reviewType){
		
		if("P".equals(reviewType)){
			
			List<String> documentTypesToReview =  clericalReviewDAO.getClericalLabDeathReviewAvailabeDocumentTypes();
			
			return documentTypesToReview;
			
			
		}else{
			List<String> documentTypesToReview =  clericalReviewDAO.getClericalReviewAvailabeDocumentTypes();
			
			if(documentTypesToReview != null && documentTypesToReview.size() > 1){
				
				documentTypesToReview.add("ALL");
			}
			
			return documentTypesToReview;
		}
		
	}
	
	public List<String> getDatafluxAvailabeDocumentTypes(String reviewType, UserObject userObject){
		

		Map<String, Object> inputParameterMap = new HashMap<>();
		inputParameterMap.put("p_module_id", "16");
		inputParameterMap.put("p_worker_id", userObject.getWorkerId());
		inputParameterMap.put("p_reviewType", reviewType);
		
		List<String> documentTypesToReview =  clericalReviewDAO.getDatafluxAvailabeDocumentTypes(inputParameterMap);
		
		
		
		if(documentTypesToReview != null && documentTypesToReview.size() > 1){
			
			documentTypesToReview.add("ALL");
		}
		
		return documentTypesToReview;
	}
	
	public List<String> getDatafluxFileNamesToReview(String docType){
		
		return clericalReviewDAO.getDatafluxFileNamesToReview(docType);
	}
	
	public Map<String, Object> getDatafluxDocumentIdAndPotentialMatchesForStatenos(String documentTypeToReview,
			String reviewType, BindingResult bindingResult, UserObject userObject){
		
		Map<String, Object> inOutParameterMap = new HashMap<>();
		
		List<Integer> clericalDocuments = null;
		
		try {
		 
		 inOutParameterMap.put("documentToReview", documentTypeToReview);
		 
		 inOutParameterMap.put("reviewType", reviewType);
		 if("CLUST_PART".equals(documentTypeToReview)) {
			 
			 clericalDocuments = clericalReviewDAO.getClusterPartnerReviewDocuments(inOutParameterMap);
			 
		 }else {

			 clericalDocuments = clericalReviewDAO.getDatafluxClericalDocuments(inOutParameterMap);
		 }
		 
		 Random randomNumber = new Random();
		 
		 Integer documentId = null;
		  
		  if(clericalDocuments.size() > 0){
		 
			  documentId = clericalDocuments.get(randomNumber.nextInt(clericalDocuments.size()));
			  
			  inOutParameterMap.put("documentId", documentId);
			  
			  //log.error(inOutParameterMap);
			  
			  if("CLUST_PART".equals(documentTypeToReview)) {
					 
				  inOutParameterMap = clericalReviewDAO.getClusterPartnerReviewDocumentDetails(inOutParameterMap);
					 
			  }else {
			  
				  inOutParameterMap = clericalReviewDAO.getDatafluxClericalReviewDocumentDetails(inOutParameterMap);
			  }
			  
			  //log.error(inOutParameterMap);
					  
			  
			  
		  }
		 
		}catch(Exception e){
			log.error("Failed, while retreiving the clerical document information");
			log.error(e.getMessage());
		}
		 
		 if(inOutParameterMap.size() > 0){
			 
			 inOutParameterMap.put("totalDocuments", clericalDocuments.size());
			 return inOutParameterMap;
		 }else{
			 return new HashMap<String, Object>();
		 }
		
	}
	
	public void reviewDatafluxStagedDocuments(long documentId, String stateno, Long clusterId, Long fuzzyClusterId, String match_cd, String documentType, String comments,
			BindingResult bindingResult, UserObject userObject) {
		
		
		try{
			
			documentReviewTransactionHelper.reviewDatafluxStagedDocuments(documentId, stateno, clusterId, fuzzyClusterId, match_cd, documentType, comments,  
					bindingResult, userObject);
		}catch(DataAccessException dataAccessException) {
			log.error("Failed to update the Match status for the document ({})",documentId);
			log.error(dataAccessException.getMessage());
			
			if(((Map<Object,Object>)bindingResult.getTarget()).size() == 0) {
				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the Match status for the document ("+documentId+")", bindingResult);
			}
		}catch(Exception e){
			log.error("Failed to update the Match status for the document ({})",documentId);
			log.error(e.getMessage());
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the Match status for the document ("+documentId+")", bindingResult);
		}
	}
	
	public void updateMatchResultsToDatafluxReviewDocument(long documentId, String stateno, Long clusterId,
			Long fuzzyClusterId, String match_cd, BindingResult bindingResult, UserObject userObject) {
		
		
		Integer statusCode = null;
		String transactionMessage = null;
		
		if(fuzzyClusterId  ==null && clusterId == null) {
			log.error("Cluster id is missing while updating the match status for the input document  : {}",documentId);
			throw new DataRetrievalFailureException("Cluster id is missing while updating the match status for the input document");
		}
		
		Map<String, Object> inOutParameterMap = new HashMap<>();
		
		if("C".equals(match_cd)) {
			inOutParameterMap.put("p_fuzzy_cluster_id", fuzzyClusterId);
		}else if("P".equals(match_cd) || "M".equals(match_cd)) {
			inOutParameterMap.put("p_cluster_id", clusterId);
		}else if("N".equals(match_cd)) {
			
			if(clusterId != null) {
				inOutParameterMap.put("p_cluster_id", clusterId);
			}else {
				inOutParameterMap.put("p_fuzzy_cluster_id", fuzzyClusterId);
				inOutParameterMap.put("p_document_id", documentId);
			}
			
		}else {
			log.error("Invalid Match_cd status in Dataflux for the document  : {}",documentId);
			throw new DataRetrievalFailureException("Failed to update the match status, Invalid match status for the document");
		}
		 
		 inOutParameterMap.put("ACTUAL_MATCH_CD", match_cd);
		 
		 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 17);
		 
		 String validDocumentToProcess = null;
		 
		 if(CommonUtil.isNotNullAndEmpty(stateno)) {
			 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_M");
			 
		 }else {
			 
			 validDocumentToProcess = clericalReviewDAO.isTheDocumentValidToProcess(inOutParameterMap);
			 
			 if(CommonUtil.isNullOrEmpty(validDocumentToProcess)) {
				 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 14);
			 }
			 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_N");
			 
		 }
		 
		 inOutParameterMap.put("stateno", stateno);
		 
		 inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		
		 clericalReviewDAO.updateMatchDetailsInStageTable(inOutParameterMap);
		 
		 clericalReviewDAO.processDatafluxStageDocument(inOutParameterMap);
		 
		 statusCode=(Integer)inOutParameterMap.get("p_code_out");
		 
		  if(statusCode == 99) {
			  
			  transactionMessage = (String)inOutParameterMap.get("p_err_msg");
			  
			  log.error(transactionMessage);;
			  
			  //commonExceptionHandler.inspectDBTransacionResult(0, (String)inOutParameterMap.get("p_user_msg"), bindingResult);
			  
			  throw new DataRetrievalFailureException((String)inOutParameterMap.get("p_user_msg"));
			  
		  }else {
			  
			  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully Matched the document("+documentId+") to stateno ("+stateno+")", bindingResult);
			  
		  }
		 
	}
	
	public void matchDocumentToStatenoInDataFlux(long documentId, String stateno, long fuzzyClusterId,
			BindingResult bindingResult, UserObject userObject) {
		Integer statusCode = null;
		String transactionMessage = null;
			
		
			Map<String, Object> inOutParameterMap = new HashMap<>();
			 
			 inOutParameterMap.put("p_fuzzy_cluster_id", fuzzyClusterId);
			 
			 inOutParameterMap.put("stateno", stateno);
			 
			 inOutParameterMap.put("matchCd", "C_M");
			 
			 inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
			 
			 clericalReviewDAO.updateMatchDetailsInStageTable(inOutParameterMap);
			 
			 clericalReviewDAO.processDatafluxStageDocument(inOutParameterMap);
			 
			 statusCode=(Integer)inOutParameterMap.get("p_code_out");
			 
			  if(statusCode == 99) {
				  
				  transactionMessage = (String)inOutParameterMap.get("p_err_msg");
				  
				  String user_msg =  (String)inOutParameterMap.get("p_user_msg");
				  log.error(transactionMessage);;
				  
				  log.error(user_msg);;
				  
				  commonExceptionHandler.inspectDBTransacionResult(0, user_msg, bindingResult);
				  
				  throw new DataRetrievalFailureException(transactionMessage);
				  
			  }else {
				  
				  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully Matched the document("+documentId+") to stateno ("+stateno+")", bindingResult);
				  
			  }
						
			
		
		 
	}
	
	public void noMatchForDocumentToStatenoInDataFlux(long documentId, long fuzzyClusterId, String documentType, BindingResult bindingResult,
			UserObject userObject) {
		
		try {
			 Map<String, Object> inOutParameterMap = new HashMap<>();
			 
			 inOutParameterMap.put("fuzzyClusterId", fuzzyClusterId);
			 
			 inOutParameterMap.put("matchCd", "C_N");
			 
			 inOutParameterMap.put("worker_id", userObject.getWorkerId());
			 
			 clericalReviewDAO.updateNotaMatchDetailsInStageTable(inOutParameterMap);
				 
			 clericalReviewDAO.updateNotaMatchDetailsInClustersTable(inOutParameterMap);
			 
			 commonExceptionHandler.inspectDBTransacionResult(0, "document ("+documentId+") marked as Not a Match", bindingResult);
		}catch(Exception e){
			log.error("{}  Failed, while updating the document status to Not a match", documentId);
			log.error(e.getMessage());
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the document("+documentId+") status to Not a match, please contact helpline", bindingResult);
		}
		
	}
	
	public List<String> getDFStatusCodesForTheFileName(String fileName, String documentType,
			BindingResult bindingResult, UserObject userObject){
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("fileName", fileName);
		
		inputParameterMap.put("documentType", documentType);
		
		return clericalReviewDAO.getDFStatusCodesForTheFileName(inputParameterMap);
		
	}
}
