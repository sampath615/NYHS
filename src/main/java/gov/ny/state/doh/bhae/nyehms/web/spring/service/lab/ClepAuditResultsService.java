package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPUploadSurveyVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface ClepAuditResultsService {
	
	public JSONObject getPFILabDataInfo(String userEnteredPfi);
	
	public JSONObject seachAccessionNoinDB(String accessionNo);

	public void saveCLEPLabResultForm(CLEPLabResultVO clepLabResultVO, BindingResult bindingResult,	UserObject userObject);
	
	public void insertCLEPLabResultData(CLEPLabResultVO clepLabResultVO, BindingResult bindingResult,	UserObject userObject);
	
	public List<JSONObject> getClepLabResultForReview(BindingResult bindingResult, UserObject userObject);
	
	public CLEPLabResultVO getClepSurveyData(int surveyNo, UserObject userObject);
	
	public 	Map<Object, Object> uploadHIVSurveysFile(MultipartFile uploadHIVSurveyFile,BindingResult bindingResult, UserObject userObject) throws IOException;
	
	void uploadClepHIVSurveyData(String fileName, List<Map<String, Object>> clepHIVSurveyDataList, String userId) throws SQLException;
	
	public List<String> getUploadedHIVSurveyFilenames(UserObject userObject, BindingResult result);
	
	public List<JSONObject> getUploadedHIVSurveyData(String uploadedFilename, BindingResult bindingResult, UserObject userObject);
	
	public CLEPUploadSurveyVO getClepSurveyUploadData(int uploadNo, UserObject userObject);
	
	public void saveFollowupHIVSurveyData(CLEPUploadSurveyVO clepUploadSurveyVO, BindingResult bindingResult,	UserObject userObject);
	
	public List<JSONObject> getLabRecruitmentAssessmentForFollowup(BindingResult bindingResult, UserObject userObject);
	
	public CLEPLabResultVO getLabRecruitmentAssessmentData(int surveyNo, UserObject userObject);	
	
	public List<Map<String, String>> getRecruitAssessmentOutcome();
	
	public void saveRecruitAssessFollowupData(CLEPLabResultVO clepLabResultVO, BindingResult bindingResult,	UserObject userObject);
	
	public List<JSONObject> getSurveyAuditReportData(String fromDate,String  toDate, BindingResult dummyBindingResult, UserObject userObject);
}
