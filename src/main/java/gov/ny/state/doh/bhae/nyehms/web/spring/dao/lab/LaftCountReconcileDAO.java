package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

public interface LaftCountReconcileDAO {
	
	public List<JSONObject> getPFICountCollectionPeriod(Map<String, Object> inputParameterMap);
	
	public List<JSONObject> getReconcileDashboardData(Map<Object,Object> map);
	
	public List<JSONObject> getCurOpenSurveysData(Map<Object,Object> map);
	
	public JSONObject getLabDetails(String pfi);
	
	public void updateLabReconcileData(Map<Object, Object> map) throws DataAccessException;
	
	
	public int insertEditSurveyData(Map<String, Object> map) throws DataAccessException;

	public List<JSONObject> getSurveyEmailRecipients(Map<String, Object>  emailMap);
	
	public void updateSurveyEmailSendData(Map<String, Object> map) throws DataAccessException;
	
	public int getLaftSurveyResponseFilenameCt(String uploadFileName);
	
	public int insertLaftSurveyResponseData(Map<String, Object> inputParameterMap)throws DataAccessException;
	
	public void processUploadedSurveyResponse(Map<Object, Object> map) throws DataAccessException;
	
	public List<JSONObject> getAccessionList(Map<Object,Object> map);
	
	public void refreshNyehmsLabCount(Map<Object, Object> map) throws DataAccessException;
	
	public void saveAddReconcilePFI(Map<String, Object> map) throws DataAccessException;
	
	public void deleteReconcilePFI(Map<String, Object> map) throws DataAccessException;
	
	public List<String> getDuplicatePFIFromSurveyResponse(String collectionPeriod);
	
}
