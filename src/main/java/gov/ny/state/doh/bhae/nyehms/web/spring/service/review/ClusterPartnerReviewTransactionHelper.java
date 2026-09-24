package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class ClusterPartnerReviewTransactionHelper {
	
	private static Logger log = LoggerFactory.getLogger(ClusterPartnerReviewTransactionHelper.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	ClusterPartnerReviewService clusterPartnerReviewService;
	
	@Autowired
	CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	CommonDocumentService commonDocumentService;
	
	@Autowired
	DocumentTransactionHelper documentTransactionHelper; 
	
	
	
	
	@Transactional
	public void processClusterPartnerDocument(long documentId, String stateno, String match_cd, String reviewStatus, 
			String INS_CLUSTER, BindingResult bindingResult, UserObject userObject) {
		
		
		Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
		
		inOutParameterMap.put("p_document_id", documentId);
		
		inOutParameterMap.put("ACTUAL_MATCH_CD", match_cd);
		 
		 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 17);
		 
		 inOutParameterMap.put("stateno", stateno);
		 
		 inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		 
		 inOutParameterMap.put("p_ins_cluster", INS_CLUSTER);
		 
		 inOutParameterMap.put("p_triggerPsMessage", "N");
		 
		 if(CommonUtil.isNotNullAndEmpty(stateno) && "Y".equals(INS_CLUSTER)) {
			 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_M");
			 
			 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 17);
			 
			 //int existingMatches = clusterPartnerReviewService.checkForExistingMatches(inOutParameterMap);
			 
			 
		 }else if(CommonUtil.isNotNullAndEmpty(stateno) && "N".equals(INS_CLUSTER)) {
			 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_M");
			 
			 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 48);
			 
		 }else if(CommonUtil.isNullOrEmpty(stateno) && "N".equals(INS_CLUSTER)) { 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_N");
			 
			 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 48);
		 }else if(CommonUtil.isNullOrEmpty(stateno) && "Y".equals(INS_CLUSTER)) { 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_N");
			 
			 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 17);
		 }

		 int resultCount =  clusterPartnerReviewService.updateClusterPartnerMatchSummary(inOutParameterMap);
		
		if(resultCount==0){
			throw new DataRetrievalFailureException("Document is already reviewed, please refesh the page");
		}else if(CommonUtil.isNotNullAndEmpty(stateno)) {
			 commonExceptionHandler.inspectDBTransacionResult(0, "Document mapped to STATENO:"+stateno, bindingResult);
		}else if(CommonUtil.isNullOrEmpty(stateno) ) {
			 commonExceptionHandler.inspectDBTransacionResult(0, "Marked document as Not a match", bindingResult);
		}
		
		if("N".equals(INS_CLUSTER)) {
			return;
		}
		
		
		clusterPartnerReviewService.processClusterparterDocumentToClusterTables(inOutParameterMap);
		
		Integer statusCode=(Integer)inOutParameterMap.get("p_code_out");
		
		if(statusCode==0) {
			return;

		}else {
			log.error((String)inOutParameterMap.get("p_msg_out"));
			throw new DataRetrievalFailureException("Failed to update the Cluster partner document status to "+reviewStatus);

		}
		 
		 
		
			 
		
		
		
	}

	@Transactional
	public void triggerClusterPSMessage(int partnerId, long documentId, String reason, String otherReason,
			AddressFieldVO addressFieldVO, UserObject userObject) {
		
		Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
		
		inOutParameterMap.put("p_document_id", partnerId);
		inOutParameterMap.put("p_cluster_documentId", documentId);
		
		inOutParameterMap.put("p_triggerPsMessage", "Y");
		inOutParameterMap.put("p_reason", reason);
		inOutParameterMap.put("p_otherReason", otherReason);
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		
		if(CommonUtil.isNotNullAndEmpty(addressFieldVO.getAddress())){
			
			inOutParameterMap.put("p_new_address", addressFieldVO.getAddress());
			inOutParameterMap.put("p_new_city", addressFieldVO.getCity());
			inOutParameterMap.put("p_new_state", addressFieldVO.getState());
			inOutParameterMap.put("p_new_county_fips", addressFieldVO.getCountyFipsCd());
			inOutParameterMap.put("p_new_zip", addressFieldVO.getZip());
			
			clusterPartnerReviewService.updateClusterPartnerDocumentAddress(inOutParameterMap);
			
			clusterPartnerReviewService.updateDocumentAddressInAttrTable(inOutParameterMap);
		}
		
		clusterPartnerReviewService.updateClusterPartnerPsMessageReasons(inOutParameterMap);
		clusterPartnerReviewService.processTriggerClusterPSMessage(inOutParameterMap);
	}

	
}
