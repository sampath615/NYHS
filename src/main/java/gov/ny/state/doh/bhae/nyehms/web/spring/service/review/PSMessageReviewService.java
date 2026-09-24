package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface PSMessageReviewService {

	List<JSONObject> getPsMessageReviewDocuments(String RESOLVED_STATUS, String PARAMETER_NAME,String MSG_TYPE_NAME, BindingResult bindingResult, UserObject userObject);

	void updatePsMessageReviewStatus(long triggerDocId, String actionName, String comments, BindingResult bindingResult,
			UserObject userObject);

	List<Map<String, String>> savePSMessageComments(String comments, Integer dOCUMENT_ID, UserObject userObject);

	String getPsMessageReport(String fromDate, String toDate, String reportType,
			BindingResult dummyBindingResult, UserObject userObject, ModelAndView mav);

	List<Map<String, String>> getPSDispositionReport(String fromDate, String toDate, String messageType, String messageCounty,
			String messageDisposition, BindingResult dummyBindingResult, UserObject userObject, Object object);

	List<Map<String, String>> getPSOOCExpsReport(String fromDate, String toDate, String messageCounty,
			BindingResult dummyBindingResult, UserObject userObject, Object object);

	List<Map<String, String>> saveLAFTMessageComments(String comments, String reason, Integer document_id,
			UserObject userObject);

}
