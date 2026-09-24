package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NBSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface NBSReviewService {

	List<JSONObject> getNbsMnemonicRecords(String birthYear, BindingResult bindingResult, UserObject userObject);

	NBSDocVO getNBSDoc(int docId, String reviewType, BindingResult bindingResult, UserObject userObject);

	void matchNBSDocumentToStateno(long documentId, String stateno, String reviewComments, BindingResult bindingResult, UserObject userObject);
	

	@Transactional
	void updateNBSExposureStatus(long documentId, String exposureStatus, String comments, BindingResult bindingResult,
			UserObject userObject);

	List<String> getPendingNbsBirthYearList(String screenCode);

	List<JSONObject> getNbsIndRecords(String birthYear, BindingResult bindingResult, UserObject userObject);

	void processNBSDocument(long documentId, String stateno, Long clusterId, String match_cd, String comments, 
			BindingResult bindingResult, UserObject userObject);

	List<JSONObject> getNbsMatchReviewRecords(BindingResult bindingResult, UserObject userObject);

	Map<String, Object> getNBSDocumentDetailsForMatchReview(Integer documentId, BindingResult bindingResult,
			UserObject userObject);

	Map<String, Boolean> checkForClimsRecId(String stateno, BindingResult bindingResult, UserObject userObject);

	JSONObject getHivFinalStatusList();

	void updateHivFinalStatus(Integer documentId, String hivFinalStatus, String reviewComments,
			BindingResult bindingResult, UserObject userObject);

	void updateRawNBSData(Integer documentId, String reviewComments, BindingResult bindingResult, UserObject userObject);

	List<JSONObject> getNonNbsMatchClimsRecords(String BIRTH_YEAR, BindingResult bindingResult, UserObject userObject);

	Map<String, Object> getClimsDocumentDetailsForMatchReview(Integer documentId, BindingResult bindingResult,
			UserObject userObject);

	List<Map<String, Object>> getNBSSearchViewResults(String labId, String firstName, String lastName, String labNo,
			String medRecordNumber, String dateOfBirth, String pfi, String gender, String weight, BindingResult bindingResult, UserObject userObject);

	void matchClimsDocumentToNBS(String patientId, long nbsDocumentId, String reviewComments,
			BindingResult bindingResult, UserObject userObject);

	void updateClimsReviewComments(Integer documentId, String reviewComments, BindingResult bindingResult,
			UserObject userObject);

	void updateStdNbsReviewComments(Integer id, String rEVIEW_COMMENTS, BindingResult bindingResult,
			UserObject userObject);

	List<String> getClimsNonMatchBirthYearList();
	
}
