package gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.ModuleAndPermissionsVO;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
/*
 * The DAO for search manage profile
 */
public interface SearchManageProfileDAO {
	/*
	 * Get the permissions grouped by module and sub modules
	 * @param inOutParameterMap the in out parameter to call the stored procedure
	 * @return List of ModuleAndPermissionsVO instances
	 */
	public List<ModuleAndPermissionsVO> getModuleAndPermissions(Map<Object,Object> inOutParameterMap);
	
	public List<ModuleAndPermissionsVO> getModuleAndPermissionsByWorkerRegionId(Map<Object,Object> inOutParameterMap);
	
	public List<ModuleAndPermissionsVO> getModuleAndPermissionsByWorkerRegionIdWithOutFlagSugg(Map<Object,Object> inOutParameterMap);
	
	public List<String> getGraphsHierarchy(Map<Object,Object> inOutParameterMap);

	public List<Map<String, String>> getEmailNotificationList(Map<String, String> inputParameterMap);

	public List<String> getUsersEmailList();

	public List<String> getUserShortNameList();

	public String getWorkerIdUsingEmail(String emailAddress);

	public void clearNotifications(Map<String, Object> inputParameterMap);

	public void saveToUpdatedNotifications(Map<String, Object> inputParameterMap);
	
	public void saveCCUpdatedNotifications(Map<String, Object> inputParameterMap);
	

	public String getRoleName(long roleCode);

	public String getRoleNameByWorkerRegionId(long workerRegionId);

	public List<JSONObject> getUserCommentsList(String workerId);
	
	public void saveUserComments(Map<Object, Object> inOutParameterMap);

	public List<JSONObject> getTrainerList();

	public List<JSONObject> getBureauList();

	public List<JSONObject> getRegOfficeList();
}
