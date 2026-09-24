package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultAccessionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPUploadSurveyVO;

public interface ClepAuditResultsDAO {
	
	public JSONObject getPFILabDataInfo(String userEnteredPfi);
	
	public JSONObject seachAccessionNoinDB(String accessionNo);
	
	public int saveLaftClepAuditSurveyData(CLEPLabResultVO clepLabResultVO) throws DataAccessException;
	
	public int saveLaftClepAccessionData(Map<String, Object> clepLabAccessionDataMap) throws DataAccessException;
	
	public List<JSONObject> getClepLabResultForReview();
	
	public CLEPLabResultVO getClepSurveyData(int surveyNo);
	
	public List<CLEPLabResultAccessionVO> getClepSurveyAccession(int surveyNo);
	
	public int updateLaftClepAccessionData(Map<String, Object> clepLabAccessionDataMap) throws DataAccessException;
	
	public int updateSurveyReviewComplete(Map<String, Object> map) throws DataAccessException;
	
	public int insertCLEPHIVSurveyData(Map<String, Object> inputParameterMap)throws DataAccessException;
	
	public int getHivSurveyFilenameCt(String uploadFileName);
	
	public List<JSONObject> getUploadedHIVSurveyFilenames();
	
	public List<JSONObject> getUploadedHIVSurveyData(Map<String, Object> inputParameterMap);
	
	public CLEPUploadSurveyVO getClepSurveyUploadData(int uploadNo);
	
	public int updateFollowupHIVSurveyData(Map<String, Object> map) throws DataAccessException;

	public List<JSONObject> getLabRecruitmentAssessmentForFollowup();
	
	public CLEPLabResultVO getLabRecruitmentAssessmentData(int surveyNo);
	
	public List<Map<String, String>> getRecruitAssessmentOutcomeList();
	
	public int updateRecruitmentAssessmentFollowupData(Map<String, Object> map) throws DataAccessException;
	
	public List<JSONObject> getSurveyAuditReportData(Map<Object,Object> map);
	
	public int updateLaftClepAuditSurveyData(CLEPLabResultVO clepLabResultVO) throws DataAccessException;
	
}
