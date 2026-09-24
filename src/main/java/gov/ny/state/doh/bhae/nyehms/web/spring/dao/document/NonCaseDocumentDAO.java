package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NonCaseDocVO;
import java.util.List;
import java.util.Map;


public interface NonCaseDocumentDAO {

	public int createNonCaseDocument(Map<String, Object> inputParameterMap);

	public NonCaseDocVO getNonCaseDoc(int docId);

	public void addCommentsToNonCaseDoc(Map<String, Object> inputParameterMap);

	public void changeNonCaseActiveFlagStatus(
			Map<String, Object> inputParameterMap);

	public List<Map<String, String>> getNonCaseReasons();

	public int flagAsNonCase(Map<Object, Object> inOutParameterMap);

	public int unflagAsNonCase(Map<Object, Object> inOutParameterMap);

	public int checkForActiveNonCaseStateno(String stateno);
	
	public int updateNonCaseStatusInPDOC(Map<String, Object> inputParameterMap);

	public int discardNonCaseDocument(Map<String, Object> inputParameterMap);

	public List<Map<String, String>> getNonCaseDocHistory(String stateNo);

	public String hasEharsStatenoPopulated(String stateno1);
	
	public Map<String, String> getDocDelDiscardHistory(int documentId);

	public List<Map<String, String>> getNonCaseEharsStatenos(Map<String, String> inputParameterMap);

	public List<Map<String, String>> getNonCaseFlaggedHistory(Map<String, String> inputParameterMap);

	public Map<Object, Object> getReasonCodeForNonCase(String colsureCategory);


}