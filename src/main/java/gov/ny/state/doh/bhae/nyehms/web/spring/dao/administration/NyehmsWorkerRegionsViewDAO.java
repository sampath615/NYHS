package gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsRole;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsUsersVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.UserRegionVO;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

public interface NyehmsWorkerRegionsViewDAO {
	
	public List<Map<String, String>> getSearchRegionDescription()throws DataAccessException;

	public List<NyehmsRole> getSearchRoleDescription()throws DataAccessException;
	
	public List<String> getSearchstatusDescription()throws DataAccessException;
	
	public void getUsersList(Map<Object,Object> inOutParameterMap);
	
	public List<UserRegionVO> getRegionRoleByUserIdList(long workerId);
	
	
	/*public NyehmsWorkerRegionsViewVO isUserExists(String legacyUid);*/
	
	public String isUserExists(String legacyUid);
	
	//creating  New User
	public void creatingNewUser(Map<Object,Object> map);
	
	
	 //Adding new region/role for existing user
	public void addingNewRegionRole(Map<Object,Object> map);
	
	//retire region/role for existing user 
	public void retireRegionRole(Map<Object,Object> inOutParameterMap);
	
	
	//save user details of manageProfile module in DB
	public int saveUserDetails(Map<Object,Object> inOutParameterMap);
	
	//Terminating the User
	public void terminateUser(Map<Object,Object> map);
	
	public void getUsersMacAddressAndRegionDetails(Map<Object,Object> inOutParameterMap);
	
	public void editUserPermissions(Map<Object,Object> inOutParameterMap);
	
	public List<Map<String, String>> getUserTypes()throws DataAccessException;
	
	public List<Map<String, String>> getUserFacilityNames()throws DataAccessException;
	
	public List<JSONObject> getAdminModuleReportData(Map<Object,Object> map);
	
	public NyehmsUsersVO getUserDetailsData(int workerId);

	public List<JSONObject> getTeleCommuteReportData(Map<Object, Object> inputData);

	public List<JSONObject> getNonTeleCommuteReportData(Map<Object, Object> inputData);

	public List<JSONObject> getTrainingAttstData();

	public List<JSONObject> getTeleworkAttstData();
	
}
