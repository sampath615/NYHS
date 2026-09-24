package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RiaDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaFollowupDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaProviderFollowupVO;

public interface RiaRecordsReviewDAO {

	List<JSONObject> getRiaRecordsForReview(Map<String, String> inputParameterMap);

	RiaDocVO getRiaDoc(int docId);

	List<Map<String, String>> getRiaCodedDropDownData();

	Map<String, Object> getNyehmsDataforRiaDoc(int docId);

	List<String> getRiaFieldProperties();

	int closeRiaDoc(RiaDocVO riaDocData);

	List<Map<String, String>> getRiaFieldColumnProperties();

	int saveRiaDoc(Map<String, String> inputParameterMap);

	List<Map<String, String>> getRiaFollowUpDropDownData();

	List<RiaFollowupDocVO> getRiaFollowUpData(int docId);

	void insertFollowUpData(Map<String, Object> inputParameterMap);

	String getNextFollowUpId();

	void insertInterventions(Map<String, Object> interventionMap);

	List<String> getExistingInterventions(Map<String, Object> interventionMap);

	void addRiaActivityType(Map<String, Object> inputParameterMap);

	List<Map<String, String>> getRiaAcitity(Map<String, Integer> inputParameterMap);

	List<Map<String, String>> getRiaDocActivity(Integer dOCUMENT_ID);

	int statenosFlaggedForRia(Map<String, Object> inputParameterMap);

	Map<String, Integer> getRiaEligibilityReport(Map<String, Object> inputParameterMap);

	List<JSONObject> getRiaEligibilityRecordsByReport(Map<String, Object> inputParameterMap);

	List<Map<String, String>> getRiaFollowUpReport(Map<String, Object> inputParameterMap);

	List<JSONObject> getRiaReportByFollowup(Map<String, Object> inputParameterMap);

	List<Map<String, String>> getRiaTreatmentReport(Map<String, Object> inputParameterMap);

	List<JSONObject> getRiaReportByTreatment(Map<String, Object> inputParameterMap);


	List<Map<String, String>> getRiaActivityReportData(Map<String, String> inputParameterMap);
	List<RiaProviderFollowupVO> getRiaProviderFollowUpData(int docId);

	void insertProvFollowUpData(Map<String, Object> parameterMap);

	void updateProvFollowUpData(Map<String, Object> parameterMap);

	void deleteProvFollowUpData(Map<String, Object> parameterMap);

	List<JSONObject> getRiaNoLinkedtoCareDataReport(Map<String, Object> inputParameterMap);
	
	
	
		
}
