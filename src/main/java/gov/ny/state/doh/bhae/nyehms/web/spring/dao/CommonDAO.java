package gov.ny.state.doh.bhae.nyehms.web.spring.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.webservice.oauth.WebServiceConstants;

public interface CommonDAO {
	public void writeLogToDB(Map<Object,Object> map)throws DataAccessException;	
	public int getPermission(Map<Object,Object> map)throws DataAccessException;
	public UserObject getUserObject(String userId)throws DataAccessException;
	public List<AddressFieldVO> getAddressDropDownInfo(AddressFieldVO addressFieldVO)throws DataAccessException;
	public List<Map<String, String>> getStateCodes()throws DataAccessException;
	public List<Map<String, String>> getCountyCodes()throws DataAccessException;
	
	public Date getCNSTrainingDate(String userId)throws DataAccessException;
	
	public List<Map<String, String>>  getUserPermissions(Map<Object,Object> map)throws DataAccessException;
	
	public int setLoginTimeStamp(int workerId)throws DataAccessException;
	
	public String getImprovedStandardAddress(Map<Object,Object> map)throws DataAccessException;
	
	public void setClientSession()throws DataAccessException;
	
	public int getAddressFieldsCombination(AddressFieldVO addressFieldVO)throws DataAccessException;
	
	public JSONObject getDocumentData(Map<Object, Object> inputParameters);
	public String getSoundexValue(String name);
	public List<Map<String, String>> getCountryCodes();
	public List<JSONObject> getPsMessageTypes();
	
	public String getCurrentActiveStateNo(String _stateno);
	
	public int insertNyehmsAgreedTime(Map<String, Object> inputParameter);
	
	public Map<String, String> getCountyNameByZip(String _zip);
	public int checkNYSEharsDataExists();
	public int checkNYCEharsDataExists();
	public List<Map<String, String>> getZipCodeReferenceData();
	
	public List<Map<String, String>> getNotificationList(Map<String, String> inputParameterMap);
	public String getEharsStateno(String stateno);
	public Map<String, String> getNYCStatenoDetails(String stateno1);
	
	public List<JSONObject> getReciepientDetails() throws DataAccessException;
	
	public List<Map<String, String>> getCRFReqActivityList() throws DataAccessException;
	public List<String> getDocumentListForReminder();
	public List<Map<String, String>> nyehmsInactiveUserDetailsList();
	
	public WebServiceConstants getWebServiceConstants(Map<Object,Object> map);
	public List<Map<String, String>> nyehmsTrainingUsersExpireList();
	public List<Map<String, String>> getCountyCodeReferenceData();
	public String getCryptoImageName();
}
