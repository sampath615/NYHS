package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterPartnerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/*
 * ACRF JSP is treated as 3 parts for all CRUD operations. 
 * One is ACRF Doc table itself 2nd is ACRF Labs table and 3rd ACRF CD4 Table.
 * So we define CRUD (only select, insert, update)) operation separately for three tables even though 
 * it is single operation from user view.
 * This is done to modularize code so that each table CRUD can be handles separately in future.
 */	

public interface ClusterPartnerReviewService {
	
	public List<String> getClusterPartnerIdList(String statusCode, BindingResult bindingResult, UserObject userObject);

	public Map<String, Object> getClusterPartnerDocumentIdReviewDetails(String qualityStageStatus,
			String clusterPartnerId, BindingResult bindingResult, UserObject userObject);

	public void discardClericalReviewDocument(long documentId, BindingResult bindingResult, UserObject userObject);

	public void processClusterPartnerDocument(long documentId, String stateno,
			String match_cd, String INS_CLUSTER, BindingResult bindingResult, UserObject userObject);

	public int updateClusterPartnerMatchSummary(Map<String, Object> inOutParameterMap);

	public void processClusterparterDocumentToClusterTables(Map<String, Object> inOutParameterMap);

//	public int updateWithExistingClusterDetails(Map<String, Object> inOutParameterMap);

//	public int checkForExistingMatches(Map<String, Object> inOutParameterMap);



	public ClusterPartnerDocVO getClusterPartnerDocData(int documentId);

	public List<Map<String, String>> getFRNumbers(Integer document_ID);

	public List<Map<String, Object>> getClusterPartnerRefData();

	public List<Map<String, Object>> getClusterNoPSRecords(BindingResult bindingResult, UserObject userObject);

	public void triggerClusterPSMessage(long documentId, String reason, String otherReason, AddressFieldVO addressFieldVO, BindingResult bindingResult, UserObject userObject);

	public void rejectClusterPSMessage(long documentId, String rejectReason, String otherReason, BindingResult bindingResult,
			UserObject userObject);

	public List<Map<String, String>> getPSMessageReasons(Map<String, String> inputParameterMap);

	public void updateClusterPartnerPsMessageReasons(Map<String, Object> inOutParameterMap);

	public void processTriggerClusterPSMessage(Map<String, Object> inOutParameterMap);

	public void updateClusterPartnerDocumentAddress(Map<String, Object> inputOutputParameters);

	public void updateDocumentAddressInAttrTable(Map<String, Object> inOutParameterMap);

	public Map<String, String> getPsMessageDetails(int docId, String clusterId, UserObject userObject);

	public void updateClusterFile(String clusterPartnerId, String clusterFileResolveDate, BindingResult bindingResult,
			UserObject userObject);

}
