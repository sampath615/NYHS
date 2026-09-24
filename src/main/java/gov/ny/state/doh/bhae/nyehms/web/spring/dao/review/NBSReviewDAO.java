package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NBSDocVO;

public interface NBSReviewDAO {

	List<JSONObject> getPerinatalReviewRecords(Map<String, String> inputParameterMap);

	NBSDocVO getNBSDoc(int docId);

	int matchNBSDocumentToStateno(Map<String, Object> inOutParameterMap);

	NBSDocVO getIndNbsDoc(int docId);

	int updateNBSExposureStatus(Map<String, Object> inOutParameterMap);

	void moveDocToStdNBS(Map<String, Object> inOutParameterMap);

	List<String> getNBSYearListToReview(Map<String, String> inputParameterMap);

	int updateNBSMotherStatenoDetails(Map<String, Object> inputParameterMap);

	String getClimsPatientId(String stateno);

	Map<String, Object> getNBSDocumentDetailsForMatchReview(Integer documentId);

	List<Map<String, String>> getHivFinalStatusList();

	int updateHivFinalStatus(Map<String, Object> inputParameterMap);

	int updateRawNBSData(Map<String, Object> inputParameterMap);

	Map<String, Object> getClimsDocumentDetailsForMatchReview(Integer documentId);

	List<Map<String, Object>> getNBSSearchViewResults(Map<String, String> inputParameterMap);

	int matchClimsDocumentToNBS(Map<String, Object> inputParameterMap);

	int updateClimsReviewComments(Map<String, Object> inputParameterMap);

	int updateStdNbsReviewComments(Map<String, Object> inputParameterMap);

	Map<String, String> getPerinatalReviewQuery(String screenName);

	void updateClimsDocumentToStdNBS(Map<String, Object> inputParameterMap);

	List<String> getClimsNonMatchBirthYearList();
	
	
}
