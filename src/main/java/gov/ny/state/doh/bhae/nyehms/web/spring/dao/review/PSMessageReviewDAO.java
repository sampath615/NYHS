package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public interface PSMessageReviewDAO {

	List<JSONObject> getPsMessageReviewDocuments(Map<String, Object> inOutParameterMap);

	int deletePsMessage(Map<Object, Object> inOutParameterMap);

	int processPsMessage(Map<Object, Object> inOutParameterMap);

	void savePSMessageComments(Map<Object, Object> inOutParameterMap);
	
	void saveLAFTMessageComments(Map<Object, Object> inOutParameterMap);
	
	List<Map<String, String>> getPSMessageComments(Integer dOCUMENT_ID);

	void updateReviewComments(Map<Object, Object> inOutParameterMap);

	List<String> getCountyCodes();

	List<Map<String, Object>> getPsMessageReport(Map<Object, Object> inputParameterMap);

	void getPSDispositionReport(Map<String, Object> inputParameterMap);

	void getPSOOCExpsReport(Map<String, Object> inputParameterMap);

	//List<Map<String, String>> getLAFTCommentsDetails(Integer document_id);
	
	
		
}
