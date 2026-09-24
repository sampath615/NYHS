package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RiaDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaFollowupDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaProviderFollowupVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface RiaReviewService {

	List<JSONObject> getRiaRecordsForReview(String PARAMETER_NAME, String PARAMETER_VALUE, BindingResult bindingResult, UserObject userObject);

	RiaDocVO getRiaDoc(int docId, String actionType, BindingResult bindingResult, UserObject userObject, String id);

	Map<String, List<Map<String, String>>> getRiaCodedDropDownData();

	Map<String, Object> getNyehmsDataForRiaDoc(int docId);

	List<String> getRiaFieldProperties();

	Map<String, String> getRiaFieldColumnProperties(String columnName);

	void closeRiaDoc(RiaDocVO riaDoc, Integer dOCUMENT_ID, BindingResult bindingResult, UserObject userObject);

	Map<String, List<Map<String, String>>> getRiaFollowUpDropDownData();

	void updateRiaDocData(Map<String, String> data, Integer DOCUMENT_ID, List<RiaFollowupDocVO> followUpDocList,
			List<RiaProviderFollowupVO> provFollowUpList, BindingResult bindingResult, UserObject userObject);

	int updateRiaDocData(RiaDocVO riaDoc, UserObject userObject);

	void updateRiaFollowupData(List<RiaFollowupDocVO> followUpDocList, Integer DOCUMENT_ID, UserObject userObject);
	
	int updateRiaDocDataToDB(Map<String, String> data, Integer DOCUMENT_ID, BindingResult bindingResult, UserObject userObject);

	Map<String, String> verifyRiaActivity(Integer DOCUMENT_ID, UserObject userObject);

	List<Map<String, String>> getRiaDocActivity(Integer dOCUMENT_ID, BindingResult bindingResult,
			UserObject userObject);

	Map<String, Object> statenosFlaggedForRia(String stateno1, String stateno2, UserObject userObject);

	Map<String, Integer> getRiaEligibilityReport(String riaFlaggedFromDate, String riaFlaggedToDate, String[] personCounty, String reportType, BindingResult bindingResult, UserObject userObject);

	List<JSONObject> getRiaEligibilityRecordsByReport(String riaFlaggedFromDate, String riaFlaggedToDate, String[] riaCounty, String pARAMETER_NAME,
			String pARAMETER_VALUE, BindingResult bindingResult, UserObject userObject);

	Map<String, String> getRiaFollowUpReport(String riaFlaggedFromDate, String riaFlaggedToDate, BindingResult bindingResult,
			UserObject userObject);

	List<JSONObject> getRiaReportByFollowup(String riaFlaggedFromDate, String riaFlaggedToDate, String pARAMETER_NAME, String pARAMETER_VALUE,
			BindingResult bindingResult, UserObject userObject);

	Map<String, String> getRiaTreatmentReport(String riaFlaggedFromDate, String riaFlaggedToDate,
			BindingResult bindingResult, UserObject userObject);

	List<JSONObject> getRiaReportByTreatment(String riaFlaggedFromDate, String riaFlaggedToDate, String pARAMETER_NAME,
			String pARAMETER_VALUE, BindingResult bindingResult, UserObject userObject);

	List<Map<String, String>> getRiaActivityReportData(String fromDate, String toDate, BindingResult dummyBindingResult,
			UserObject userObject, Object object);

	void updateRiaProvFollowupData(List<RiaProviderFollowupVO> provFollowUpList, Integer dOCUMENT_ID,
			UserObject userObject);


	List<JSONObject>  getRiaNoLinkedtoCareDataReport(String riaFlaggedFromDate, String riaFlaggedToDate,
			BindingResult bindingResult, UserObject userObject);
}
