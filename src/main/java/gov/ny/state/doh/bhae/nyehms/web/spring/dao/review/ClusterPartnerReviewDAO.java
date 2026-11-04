package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterPartnerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PartnerInvestigationVO;

public interface ClusterPartnerReviewDAO {

	List<String> getClusterPartnerIdList(Map<String, String> inputParameterMap);
	
	public int getTotalClusterParterDocCountToReview(Map<Object, Object> inOutParamaterMap);
	
	public Map<String, Object> getClusterPartnerDocumentsToReview(Map<Object, Object> inOutParamaterMap);
	
	public int discardClericalReviewDocument(Map<String, Object> inOutParameterMap);

	public int discardClericalReviewDocumentInStageTable(Map<String, Object> inOutParameterMap);




	public int updateClusterPartnerMatchSummary(Map<String, Object> inOutParameterMap);
	
//	public int updateWithExistingClusterDetails(Map<String, Object> inOutParameterMap);	

	public void processClusterparterDocumentToClusterTables(Map<String, Object> inOutParameterMap);

//	int checkForExistingMatches(Map<String, Object> inOutParameterMap);
	


	public ClusterPartnerDocVO getClusterPartnerDocData(int documentId);

	public ClusterPartnerDocVO getClusterPartnerReviewDocData(Map<String, Object> inOutParameterMap);

	List<Map<String, String>> getFRNumbers(Integer document_ID);

	List<PartnerInvestigationVO> getPartnerInvestigationDetails(Map<String, Object> inputParameterMap);
	
	List<Map<String, Object>> getClusterPartnerRefData();

	List<Map<String, Object>> getClusterNoPSRecords();

	Integer getClusterPartnerId(long documentId);

	int rejectClusterPSMessage(Map<String, Object> inOutParameterMap);

	List<Map<String, String>> getPSMessageReasons(Map<String, String> inputParameterMap);

	int updateClusterPSMessageReasonDetails(Map<String, Object> inOutParameterMap);

	int updateClusterPartnerDocumentAddress(Map<String, Object> inputOutputParameters);

	void updateDocumentAddressInAttrTable(Map<String, Object> inOutParameterMap);

	Map<String, String> getPsMessageDetails(Map<String, Object> inOutParameterMap);

	int updateClusterFile(Map<String, Object> inOutParameterMap);
	
}
