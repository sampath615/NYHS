package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LAFT_LAB_RECON_SUMM_REC;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface LaftCountReconcilleService {
	
	public Map<String, String> getPFICollectionPeriods(String userEnteredPfi);
	
	public List<JSONObject> getReconcileDashboardData(UserObject userObject, BindingResult bindingResult, String reconcileId);
	
	public List<JSONObject> getCurOpenSurveysData(UserObject userObject, BindingResult bindingResult, String reconcileId, String userId, String labReconciled);
	
	public Map<String, Object> getLabReportedSurveyData(UserObject userObject, BindingResult bindingResult, String reconcileId, String pfi);
	
	public String updateLabReconcileData(String agGridDataStr, String reconId, String pfi, String labClia, 
			String contactDate, String reconciled, String accessionCompare, String bhaeComments, String workerId, BindingResult bindingResult, UserObject userObject);
	
	public void insertEditedSurveyData(List<LAFT_LAB_RECON_SUMM_REC> surveyDataList, Map<Object, Object> inputData, BindingResult bindingResult, UserObject userObject);
	
	public List<JSONObject> getSendSurveyEmailData(BindingResult bindingResult, UserObject userObject);
	
	public String sendSurveyEmailToLab(String surveyEmailData, String surveyLink, String surveyCompleteDt, String hcsUserId) throws MessagingException, ParseException;
	
	public 	Map<Object, Object> uploadLaftSurveyResponseFile(MultipartFile uploadSurveyResponseFile, String collectionPeriod, BindingResult bindingResult, UserObject userObject) throws IOException;
	
	void uploadLaftSurveyResponseData(String fileName, List<Map<String, Object>> laftSurveyResponseDataList, String collectionPeriod, String userId) throws SQLException;
	
	public List<JSONObject> getAccessionList(String reconId, String pfi) throws MessagingException, ParseException;	
	
	public String refreshNyehmsLabCount(String reconId, String pfi, String workerId) throws MessagingException, ParseException;
	
	public Map<String, Object> saveAddReconcilePFI(String reconId, String pfi, String laftUserId, String workerId, BindingResult bindingResult, UserObject userObject);
	
	public void deleteReconcilePFI(String reconId, String pfi, String workerId, BindingResult bindingResult, UserObject userObject);
	
}
