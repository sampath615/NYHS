package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CommonDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.review.ClusterPartnerReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterPartnerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PartnerInvestigationVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class ClusterPartnerReviewServiceImpl implements ClusterPartnerReviewService{
	
	
	private static Logger log = LoggerFactory.getLogger(ClusterPartnerReviewServiceImpl.class);

	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	private ClusterPartnerReviewDAO clusterPartnerReviewDAO;	
	
	@Autowired
	private CommonDocLabsDAO commonDocLabsDAO;
		
	@Autowired
	ClusterPartnerReviewTransactionHelper clusterPartnerReviewTransactionHelper;
	
	@Override
	public List<String> getClusterPartnerIdList(String statusCode, BindingResult bindingResult, UserObject userObject){
		Map<String, String> inputParameterMap = new HashMap<String, String>();
		inputParameterMap.put("STATUS_CODE", statusCode);
		
		return clusterPartnerReviewDAO.getClusterPartnerIdList(inputParameterMap);
	}

	@Override
	public Map<String, Object> getClusterPartnerDocumentIdReviewDetails(String qualityStageStatus,
			String clusterPartnerId, BindingResult bindingResult, UserObject userObject) {
		
		Map<String, Object> documentDetailsToReview = new HashMap<String, Object>();
		
		try{
		
			Map<Object, Object> inOutParamaterMap = new HashMap<Object, Object>();
			
			
			inOutParamaterMap.put("clusterPartnerId", clusterPartnerId);
			inOutParamaterMap.put("filterCriteriaForStatusCode", qualityStageStatus);
	
			
			int documentsCountToReview = 0;
			
			documentsCountToReview = clusterPartnerReviewDAO.getTotalClusterParterDocCountToReview(inOutParamaterMap);

			
			
			if(documentsCountToReview == 0){
				documentDetailsToReview.put("STATUS", "FAILURE");
				documentDetailsToReview.put("ERROR_MESSAGE", "No documents awaiting for review with the selected criteria.");
				
				return documentDetailsToReview;
			}else{
				

					documentDetailsToReview = clusterPartnerReviewDAO.getClusterPartnerDocumentsToReview(inOutParamaterMap);
				
			
				
				
//				if("RESISTANCE".equals(documentType) || "ADAP".equals(documentType)){
//					documentToReview = clericalReviewDAO.getQualityStagedDocumentToReviewByParent(inOutParamaterMap);
//				}else{
//					documentToReview = clericalReviewDAO.getQualityStagedDocumentToReview(inOutParamaterMap);
//				}
				
				if(documentDetailsToReview != null){
					
					documentDetailsToReview.put("documentsToReview", documentsCountToReview);
//					String matchCd = (String)documentToReview.get("MATCH_CD");
//					BigDecimal document_id = (BigDecimal)documentToReview.get("DOCUMENT_ID");
					
//					documentDetailsToReview.put("MATCH_CD", matchCd);
//					documentDetailsToReview.put("DOCUMENT_ID", document_id);
					
//					List<String> statenoList = null;
//					if("C".equals(matchCd)){
//						statenoList = clericalReviewDAO.getStatnosForClericalDocument(document_id);
//						
//						if(statenoList == null){
//							log.error("Failed to get the Clerical Matching stateno list from RAW_DOCUMENT_MATCH_RESULTS for the DocumentId : "+document_id
//									);
//							log.error(document_id+"  don't have matching potentian statenos");
//							
//							throw new DataRetrievalFailureException("unable to load the "+documentType+" document for review");
//						}
//						
//						documentDetailsToReview.put("STATENO_LIST", statenoList);
//					}else if("M".equals(matchCd)){
//						
//						inOutParamaterMap.put("DOCUMENT_ID", document_id);
//						
//						statenoList = clericalReviewDAO.getMatchingStatnoForDocument(inOutParamaterMap);
//						
//						if(statenoList == null){
//							log.error("Failed to get the Matching stateno from STD table for the DocumentId : "+document_id
//									);
//							log.error(document_id+"  don't have matching stateno");
//							
//							throw new DataRetrievalFailureException("unable to load the "+documentType+" document for review");
//						}
//						
//						
//						documentDetailsToReview.put("STATENO_LIST", statenoList);
//					}
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
			log.error("Excpetion occured while loading the Cluster Partner document for Review");
			log.error(exception.getMessage());
						
			documentDetailsToReview.put("STATUS", "FAILURE");
			documentDetailsToReview.put("ERROR_MESSAGE", "Failed to load the Cluster Partner documents for review, please contact helpline");
		}
		
		return documentDetailsToReview;
	}
	
	@Override
	public void discardClericalReviewDocument(long documentId, BindingResult bindingResult, UserObject userObject) {
		
		 Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
		 
		 inOutParameterMap.put("documentId", documentId);
		 inOutParameterMap.put("workerId", userObject.getWorkerId());
		 		
		int resultCount = clusterPartnerReviewDAO.discardClericalReviewDocument(inOutParameterMap);
		 
		//int resultCountStageTable = clusterPartnerReviewDAO.discardClericalReviewDocumentInStageTable(inOutParameterMap);
		
		if(resultCount == 0 
			//	|| resultCountStageTable == 0
				) {
			commonExceptionHandler.inspectDBTransacionResult(0, "Unable to discard the document ("+documentId+")", bindingResult);
		}else {
			commonExceptionHandler.inspectDBTransacionResult(0, "Discarded the document ("+documentId+")", bindingResult);
		}
		
	}
	
	@Override
	@Transactional
	public void triggerClusterPSMessage(long documentId, String reason, String otherReason, AddressFieldVO addressFieldVO, BindingResult bindingResult, UserObject userObject) {
		boolean isError = false;	
		String transactionMessage = null;
		try {	
			
			Integer partnerId = clusterPartnerReviewDAO.getClusterPartnerId(documentId);
			
			clusterPartnerReviewTransactionHelper.triggerClusterPSMessage(partnerId, documentId, reason, otherReason, addressFieldVO, userObject);
			
			transactionMessage = "Successfully created the PS message";
		
		}catch(DataAccessException dataAccessException){
			isError = true; 
			transactionMessage = dataAccessException.getMessage();
			log.error(transactionMessage+"  DOCUMENT_ID:"+documentId);
			log.error(transactionMessage);
		}catch(Exception exception){
			isError = true;
			transactionMessage = "Failed to create the PS message, please contact helpline";
			log.error(transactionMessage+"  DOCUMENT_ID:"+documentId);
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage+", Please contact helpline.", bindingResult);
		}else{
			
			commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
		}
	}
	
	public void updateClusterPartnerPsMessageReasons(Map<String, Object> inOutParameterMap) {
		
		
		int resultCount = clusterPartnerReviewDAO.updateClusterPSMessageReasonDetails(inOutParameterMap);
		
		if(resultCount == 0) {
			log.error("Failed to trigger the PS message. PS message already triggered or Partner information is missing.  document {}",inOutParameterMap.get("p_cluster_documentId"));
			throw new DataRetrievalFailureException("PS message already triggered for this document, please refresh the page");
		}
	}
	
	public void processTriggerClusterPSMessage(Map<String, Object> inOutParameterMap) {
		
		clusterPartnerReviewDAO.processClusterparterDocumentToClusterTables(inOutParameterMap);
		
		Integer statusCode=(Integer)inOutParameterMap.get("p_code_out");
		
		if(statusCode!=0) {
			log.error((String)inOutParameterMap.get("p_msg_out"));
			throw new DataRetrievalFailureException("Failed to create the PS message, please contact helpline");
		}
	}
	
	@Override
	public void rejectClusterPSMessage(long documentId, String rejectReason, String otherReason, BindingResult bindingResult,
			UserObject userObject) {
		Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
		inOutParameterMap.put("p_document_id", documentId);
		inOutParameterMap.put("p_rejectReason", rejectReason);
		inOutParameterMap.put("p_otherReason", otherReason);
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		
		int resultCount = clusterPartnerReviewDAO.rejectClusterPSMessage(inOutParameterMap);
		
		if(resultCount > 0) {
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully rejected the PS Message", bindingResult);

		}else {
			log.error("Failed to reject the PS message. Document is already updated with the Reject reason.  document {}",documentId);
			log.error("Reject Reason {}",rejectReason);
			commonExceptionHandler.inspectDBTransacionResult(-1, "Document is already updated with the Reject reason, please refresh the page", bindingResult);

		}
		
	}
	
	@Override
	public void processClusterPartnerDocument(long documentId, String stateno, 
			String match_cd,  String INS_CLUSTER, BindingResult bindingResult, UserObject userObject) {
		
		boolean isError = false; 
		String transactionMessage = null;
		
		String reviewStatus = null;
		try{
			if(CommonUtil.isNotNullAndEmpty(stateno)) {
				reviewStatus = "Match";	
				transactionMessage = "Successfully matched the document("+documentId+") to the stateno : "+stateno;
			}else {
				reviewStatus = "Not a match";	
				transactionMessage = "Successfully updated the document("+documentId+") status as Not a match";
				
			}
			
			clusterPartnerReviewTransactionHelper.processClusterPartnerDocument(documentId, stateno, match_cd, reviewStatus, INS_CLUSTER, bindingResult, userObject);
			
		}catch(DataAccessException dataAccessException){
			isError = true; 
			transactionMessage = dataAccessException.getMessage();
			log.error("{}  DOCUMENT_ID: {}   stateno: {}",transactionMessage,documentId,stateno);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			transactionMessage = "Failed to update the Cluster partner document status to "+reviewStatus;
			log.error("{}  DOCUMENT_ID: {}   stateno: {}",transactionMessage,documentId,stateno);
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage+", Please contact helpline.", bindingResult);
		}else{
			
			commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
		}
		 
	}
	
	@Override
	public int updateClusterPartnerMatchSummary(Map<String, Object> inOutParameterMap) {
		int resultCount = clusterPartnerReviewDAO.updateClusterPartnerMatchSummary(inOutParameterMap);
		return resultCount;
	}
	
//	@Override
//	public int checkForExistingMatches(Map<String, Object> inOutParameterMap) {
//		return clusterPartnerReviewDAO.checkForExistingMatches(inOutParameterMap);
//	}
	
//	@Override
//	public int updateWithExistingClusterDetails(Map<String, Object> inOutParameterMap) {
//		return clusterPartnerReviewDAO.updateWithExistingClusterDetails(inOutParameterMap);
//	}
	
	@Override
	public void processClusterparterDocumentToClusterTables(Map<String, Object> inOutParameterMap) {
		clusterPartnerReviewDAO.processClusterparterDocumentToClusterTables(inOutParameterMap);
		
		
	}
	
	@Override
	public ClusterPartnerDocVO getClusterPartnerDocData(int documentId) {
		
		String documentType = commonDocLabsDAO.getDocumentType(documentId);
		
		if("CLUST_PART".equals(documentType)) {
			ClusterPartnerDocVO  clusterPartnerDocVO=  clusterPartnerReviewDAO.getClusterPartnerDocData(documentId);
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			inputParameterMap.put("DOCUMENT_ID", clusterPartnerDocVO.getDOCUMENT_ID());
			
			List<PartnerInvestigationVO> partnerInvestigationList = clusterPartnerReviewDAO.getPartnerInvestigationDetails(inputParameterMap);
			
			clusterPartnerDocVO.setPartnerInvestigationList(partnerInvestigationList);
			
			return clusterPartnerDocVO;
			
		}else {
			

			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			inputParameterMap.put("PARTNER_ID", documentId);

			ClusterPartnerDocVO  clusterPartnerDocVO = clusterPartnerReviewDAO.getClusterPartnerReviewDocData(inputParameterMap);
			
			inputParameterMap.put("PARTNER_CLUSTER_ID", clusterPartnerDocVO.getPARTNER_CLUSTER_ID());
			inputParameterMap.put("CLUSTER_ID", clusterPartnerDocVO.getCLUSTER_ID());
			
			List<PartnerInvestigationVO> partnerInvestigationList = clusterPartnerReviewDAO.getPartnerInvestigationDetails(inputParameterMap);
			
			clusterPartnerDocVO.setPartnerInvestigationList(partnerInvestigationList);
			
			return clusterPartnerDocVO;
		}
	}
	
	@Override

	public List<Map<String, String>> getFRNumbers(Integer document_ID){
		return clusterPartnerReviewDAO.getFRNumbers(document_ID);
	}
	
	@Override
	public List<Map<String, Object>> getClusterPartnerRefData(){
		return clusterPartnerReviewDAO.getClusterPartnerRefData();
	}
	
	@Override
	public List<Map<String, Object>> getClusterNoPSRecords(BindingResult bindingResult, UserObject userObject){
		return clusterPartnerReviewDAO.getClusterNoPSRecords();
	}
		
	@Override
	public List<Map<String, String>> getPSMessageReasons(Map<String, String> inputParameterMap){
		return clusterPartnerReviewDAO.getPSMessageReasons(inputParameterMap);
	}
	
	@Override
	public void updateClusterPartnerDocumentAddress(Map<String, Object> inputOutputParameters) {
		int resultCount = clusterPartnerReviewDAO.updateClusterPartnerDocumentAddress(inputOutputParameters);
		if(resultCount == 0) {
			throw new DataRetrievalFailureException("Failed to update the new address details, please contact helpline");
		}
	}
	
	@Override
	public void updateDocumentAddressInAttrTable(Map<String, Object> inOutParameterMap) {
		clusterPartnerReviewDAO.updateDocumentAddressInAttrTable(inOutParameterMap);
	}

	@Override
	public Map<String, String> getPsMessageDetails(int docId, String clusterId, UserObject userObject){
		
		Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
		inOutParameterMap.put("p_docId", docId);
		inOutParameterMap.put("p_clusterId", clusterId);
		
		return clusterPartnerReviewDAO.getPsMessageDetails(inOutParameterMap);
	}
	
	
	@Override
	public void updateClusterFile(String clusterPartnerId, String clusterFileResolveDate, BindingResult bindingResult,
			UserObject userObject) {
		
		Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
		inOutParameterMap.put("p_clusterPartnerId", clusterPartnerId);
		inOutParameterMap.put("p_date", clusterFileResolveDate);
		inOutParameterMap.put("p_workerId", userObject.getWorkerId());
		
		int resultCount = clusterPartnerReviewDAO.updateClusterFile(inOutParameterMap);
		
		if(resultCount == 0) {
			commonExceptionHandler.inspectDBTransacionResult(0, "Failed to update the cluster resolve date, please contact helpline", bindingResult);
		}else {
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the resolve date for Cluster:"+clusterPartnerId, bindingResult);
		}
			
	}
	
}
