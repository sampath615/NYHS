package gov.ny.state.doh.bhae.nyehms.web.spring.service.administration;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsRole;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsUsersVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsWorkerRegionsViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.UserRegionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface AdministrationService {
	
	//Get Admin Search region Description.
	public Map<String, String> getSrchregionTypes(BindingResult result, UserObject userObject);
	//Get Admin Search role Description.
	public List<NyehmsRole> getSrchroleTypes(BindingResult result, UserObject userObject);
	//Get Admin Search Status Description.
	//public List<String> getSrchstatusTypes(BindingResult result, UserObject userObject);
	
    public List<NyehmsWorkerRegionsViewVO>  getSearchUsersList(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult result, UserObject userObject);
    
    
    public List<UserRegionVO> getRegionRoleByUserIdList(long workerId,  BindingResult result, UserObject userObject);
    
    
    //Adding new region/role for existing user
    public void  addingNewRegionRole(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult result, UserObject userObject);
   
    //retire region/role for existing user 
    public void updateOrRetireRegionRole(UserRegionVO userRegionVO, BindingResult result, UserObject userObject);

    
    //Validating the user ID in Database
    public void  validateUser(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult result, UserObject userObject);
    
    
    public int  creatingNewUserInNyehms(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult result, UserObject userObject);
    public void  creatingNewUserInHcs(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult result, UserObject userObject);
    

    //save user details of manageProfile module in DB
    public void  saveUserDetails(NyehmsUsersVO nyehmsUsersVO, BindingResult result, UserObject userObject);
    
    
    public List<NyehmsUsersVO> getUsersMacAddressAndRegionDetails(long workerId, BindingResult bindingResult, UserObject userObject);
    
    public void terminateUserFromHCS(String legacyUid, BindingResult bindingResult, UserObject userObject);
    
    public int terminateUserFromDB(long workerId, BindingResult bindingResult, UserObject userObject);
    
    public void  terminateUserFromHCSAndDB(long workerId, String legacyUid, BindingResult bindingResult, UserObject userObject);
	public void creatingNewUserInHcsAndNyehms(
			NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO,
			BindingResult bindingResult, UserObject userObject);
	public List<Map<String, String>> getEmailNotificationList(String aCTION_NAME, BindingResult bindingResult, UserObject userObject);
	public List<String> getUsersEmailList(BindingResult bindingResult, UserObject userObject);
	public Map<String, String> getWorkerIdUsingEmail(String emailAddress);
	public void saveUpdatedNotifications(String aCTION_NAME, String tO_LIST, String cC_LIST, BindingResult bindingResult, UserObject userObject);
	public List<String> getUserShortNameList(BindingResult bindingResult, UserObject userObject);
	public Map<String, String> getUserTypes(BindingResult result, UserObject userObject);
	public Map<String, String> getUserFacilityNames(BindingResult result, UserObject userObject);
	public List<JSONObject> getUserCommentsList(String workerId, BindingResult bindingResult, UserObject userObject);
	public List<JSONObject> getSCReportData(String fromDate,String  toDate, BindingResult dummyBindingResult, UserObject userObject);
	public NyehmsUsersVO getUserDetailsData(int workerId, UserObject userObject);	
	public List<JSONObject> getTrainerList(BindingResult bindingResult,
			UserObject userObject);
	public List<JSONObject> getTeleCommuteReportData(String teleCommute, String fromDate, String toDate,
			BindingResult dummyBindingResult, UserObject userObject);
	public List<JSONObject> getBureauList(BindingResult bindingResult, UserObject userObject);
	public List<JSONObject> getTrainingAttstData(BindingResult dummyBindingResult,
			UserObject userObject);
	public List<JSONObject> getTeleworkAttstData(BindingResult dummyBindingResult,
			UserObject userObject);
	public List<JSONObject> getRegOfficeList(BindingResult bindingResult, UserObject userObject);
}
