package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

public interface DocumentsReviewService {

	public List<JSONObject> getprfReviewDocumentsData(BindingResult bindingResult, UserObject userObject);

	public void reviewAndUpdateAndFlagNonAsCaseTheStatusOfClosedAssignment(String colsureCategory, String stateNo, int assignmentId,
			String status, BindingResult bindingResult, UserObject userObject);

	public int updateTheReviewStatusInSurvAssignment(int assignmentId,
			UserObject userObject);

	public int addAssignmentActivity(int assignmentId,
			String comments, String activityType, UserObject userObject);

	public void agreeAndDeleteDocumentForClosedAssignment(int assignmentId,
			int documentId, String documentType, BindingResult bindingResult,
			UserObject userObject);
	
	public List<JSONObject> getVlStatenosToReview(String reviewCategory, 
			BindingResult dummyBindingResult, UserObject userObject);

	public void updateVlDocumentStatus(String stateno, String reviewCategory, String requestComments, BindingResult bindingResult,
			UserObject userObject);

	public List<Map<String, String>> getVlDocumentReviewCategories();

	
}
