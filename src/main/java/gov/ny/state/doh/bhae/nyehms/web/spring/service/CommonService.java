package gov.ny.state.doh.bhae.nyehms.web.spring.service;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

public interface CommonService{	
	public List<String> getAddressDropDownInfo(AddressFieldVO addressFieldVO, BindingResult result, UserObject userObject, String sessionID);	
	public Map<String, String> getStateCodes(BindingResult result, UserObject userObject, String sessionID);
	public Map<String, String> getCountyCodes(BindingResult result, UserObject userObject, String sessionID);	
    public boolean authenticateUser(HttpServletRequest request, String userid, String cryptoNumber);
    public int getTrainingDate(String userId,BindingResult bindingResult);
    
    public JSONObject getUserPermissions(int workerId, String workerRegionId, BindingResult bindingResult);
    
    public void setLoginDate(int workerId, BindingResult bindingResult);
    
    public String getImprovedStandardAddress(String customAddress);
    
    public void getAddressFieldsCombination(AddressFieldVO addressFieldVO, 
			BindingResult bindingResult, 
			UserObject userObject);
	public JSONObject getDocumentData(String documentId, String documentType,
			BindingResult dummyBindingResult, UserObject userObject);
	public String getSoundexValue(String name);
	public Map<String, String> getCountryCodes(BindingResult bindingResult,
			UserObject userObject, String id);
	public List<JSONObject> getPsMessageTypes();
	
	public String getCurrentActiveStateNo(String _stateno);
	
	public Map<String, String> getCountyNameByZip(String pERSON_ZIP);
	public int insertNyehmsAgreedTime(UserObject userObject);
	
	
	public void checkNYSEharsDataExists();
	
	public void checkNYCEharsDataExists();
	public Map<String, Map<String, String>> getZipCodeReferenceData();
	
	public Map<String, String> getNotificationList(String columnName);
	
	public void sendStatusSummary(List<Map<String, Object>> assignmentList, String headers, String columnNames,
			String emailIdentifier, String subject, String bodyMessage, String noDataMsg) ;
	
    public List<JSONObject> getReciepientDetails();
    
	public JSONObject getCRFReqActivityList() ;
	public List<String> getDocumentListForReminder();
	public List<Map<String, String>> nyehmsInactiveUserDetailsList();
	public List<Map<String, String>> nyehmsTrainingUsersExpireList();
	public Map<String, String> getCountyCodeReferenceData();
}
