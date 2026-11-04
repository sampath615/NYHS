package gov.ny.state.doh.bhae.nyehms.web.spring.controller.administration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsRole;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsUsersVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsWorkerRegionsViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.UserRegionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.AdministrationService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.transaction.TransactionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@Controller
public class AdminController {

	/*	private Log log = LogFactory.getLog(this.getClass());
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();*/

	@Autowired private AdministrationService administrationService ;
	@Autowired         GetGridObject getGridObject;
	@Autowired         DateUtil dateUtil;
	@Autowired         TransactionService transactionService;

	/**
	 * This method is used to load the initial variables like Regions and Roles and 
	 * redirect the request to Administration home page. 
	 * @param map to store the current request variables
	 * @param request HTTP request reference
	 * @return String redirecting to Administration.jsp   
	 */

	@RequestMapping("/getAdminPage")
	public String getAdminPage(
			Map<String, Object> map,
			HttpServletRequest request, UserObject userObject)
	{	
		NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO =new NyehmsWorkerRegionsViewVO();
		map.put("nyehmsWorkerRegionsViewVO", nyehmsWorkerRegionsViewVO);
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		Map<String, String> regionTypes = administrationService.getSrchregionTypes(bindingResult, userObject);
		map.put("regionTypes", regionTypes);
		List<NyehmsRole> nyehmsRole = administrationService.getSrchroleTypes(bindingResult, userObject);
		map.put("nyehmsRole", nyehmsRole);
		Map<String, String> userTypes = administrationService.getUserTypes(bindingResult, userObject);
		map.put("userTypes", userTypes);	
		Map<String, String> userFacilityNames = administrationService.getUserFacilityNames(bindingResult, userObject);
		map.put("userFacilityNames", userFacilityNames);
		List<JSONObject>  trainerList = administrationService.getTrainerList(bindingResult, userObject);
		map.put("trainerList", trainerList);
		List<JSONObject>  bureauList = administrationService.getBureauList(bindingResult, userObject);
		map.put("bureauList", bureauList);
		List<JSONObject>  regOfficeList = administrationService.getRegOfficeList(bindingResult, userObject);
		map.put("regOfficeList", regOfficeList);
		
		
		String startDate = dateUtil.getCurrentDate();
		map.put("startDate", startDate);
		return "administration/Administration";
	}

	/**
	 * Get the Users list depends on users search criteria 
	 * @param NyehmsWorkerRegionsViewVO which holds the user information
	 * @param request HTTP request reference
	 * @return GridObject search criteria results in JSON format     
	 */
	@RequestMapping(value="/getSearchUsersList.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody GridObject getSearchUsersList(
			@ModelAttribute("nyehmsWorkerRegionsViewVO") NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(
				administrationService.getSearchUsersList(
						nyehmsWorkerRegionsViewVO , bindingResult, userObject));
		return jsonResponse;
	}
	
	@RequestMapping(value="/getUserCommentsList.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody List<JSONObject> getUserCommentsList(@RequestParam String workerId,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return 
				administrationService.getUserCommentsList(workerId,
						 bindingResult, userObject);
	}
	
	
	

	/**
	 * Get the User's Region role information 
	 * @param workerId Every user will have different workerIds
	 * (depends on region & Role worker id will be assigned) 
	 * @param request HTTP request reference
	 * @return GridObject user's region role information in JSON format     
	 */
	@RequestMapping(value="/getRegionRoleByUserIdList.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody GridObject getRegionRoleByUserIdList(
			@RequestParam long  workerId,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(
				administrationService.getRegionRoleByUserIdList(
						workerId , bindingResult, userObject));
		return jsonResponse;
	}

	/**
	 * Get the User's Region role information
	 * page: Search manage page 
	 * @param workerId Every user will have different workerIds
	 * (depends on region & Role worker id will be assigned) 
	 * @param request HTTP request reference
	 * @return GridObject user's region role information in JSON format     
	 */
	@RequestMapping(value="/getManageRegionRoleList.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody GridObject getManageRegionRoleList(
			@RequestParam long  workerId, HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(
				administrationService.getRegionRoleByUserIdList(
						workerId , bindingResult, userObject));
		return jsonResponse;
	}

	/**
	 * Add a new region role to the user in the database
	 * @param workerId Every user will have different workerIds
	 * (depends on region & Role worker id will be assigned) 
	 * @param request HTTP request reference
	 * @return the basic Map
	 */
	@RequestMapping(value="/addingNewRegionRole.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> addingNewRegionRole(
			@ModelAttribute("nyehmsWorkerRegionsViewVO") NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		administrationService.addingNewRegionRole(
				nyehmsWorkerRegionsViewVO , bindingResult, userObject);
		return targetMap;
	}

	/**
	 * This method is used to update the Region role permissions or EndDate
	 * @param userRegionVO which holds the region role information 
	 * @param request HTTP request reference
	 * @return targetMap which holds the response object(containing success/Failure and its respective message)     
	 */
	@RequestMapping(value="/updateOrRetireRegionRole.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> retireRegionRole(
			@ModelAttribute("userRegionVO") UserRegionVO userRegionVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		administrationService.updateOrRetireRegionRole(userRegionVO , bindingResult, userObject);
		return targetMap;
	}



	/**
	 * This method is used to load the initial variables like Regions, Roles
	 * and redirect the request to VerifyUser.jsp
	 * @param map to store the current request variable
	 * @param request HTTP request reference
	 * @return String redirecting to VerifyUser.jsp   
	 */
	@RequestMapping("/getNewUserPage")
	public String getNewUserPage(
			Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{	
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO =new NyehmsWorkerRegionsViewVO();
		map.put("nyehmsWorkerRegionsViewVO", nyehmsWorkerRegionsViewVO);
		Map<String, String> regionTypes = administrationService.getSrchregionTypes(bindingResult, userObject);
		map.put("regionTypes", regionTypes);
		Map<String, String> userTypes = administrationService.getUserTypes(bindingResult, userObject);
		map.put("userTypes", userTypes);
		Map<String, String> userFacilityNames = administrationService.getUserFacilityNames(bindingResult, userObject);
		map.put("userFacilityNames", userFacilityNames);
		List<NyehmsRole> nyehmsRole = administrationService.getSrchroleTypes(bindingResult, userObject);
		map.put("nyehmsRole", nyehmsRole);
		List<JSONObject>  trainerList = administrationService.getTrainerList(bindingResult, userObject);
		map.put("trainerList", trainerList);
		
		List<JSONObject>  bureauList = administrationService.getBureauList(bindingResult, userObject);
		map.put("bureauList", bureauList);
		List<JSONObject>  regOfficeList = administrationService.getRegOfficeList(bindingResult, userObject);
		map.put("regOfficeList", regOfficeList);
		
		String startDate = dateUtil.getCurrentDate();
		map.put("startDate", startDate);
		return "administration/VerifyUser";
	}

	/**
	 * This method is used to load the initial variables like Regions, Roles and
	 * redirect the request to the Search/manage page 
	 * @param nyehmsWorkerRegionsViewVO to store the Worker Region information
	 * @param map to store the current request variable
	 * @param request HTTP request reference
	 * @return String redirecting to ManageProfile.jsp   
	 */
	@RequestMapping("/getManageProfile")
	public String getManageProfile (
			@ModelAttribute("nyehmsWorkerRegionsViewVO") NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO,
			Map<String, Object> map,
			HttpServletRequest request, UserObject userObject)
	{	
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		Map<String, String> regionTypes = administrationService.getSrchregionTypes(bindingResult, userObject);
		map.put("regionTypes", regionTypes);
		List<NyehmsRole> nyehmsRole = administrationService.getSrchroleTypes(bindingResult, userObject);
		map.put("nyehmsRole", nyehmsRole);
		map.put("managenyehmsWorkerRegionsViewVO", nyehmsWorkerRegionsViewVO);
		return "administration/ManageProfile";
	}

	/**
	 * This method is used to validate the UserId, wheather the userid is invalid,
	 * valid, or already exists. 
	 * @param nyehmsWorkerRegionsViewVO to store the Worker Region information
	 * @param request HTTP request reference
	 * @return map  storing result information(userid status)   
	 */
	@RequestMapping(value="/validateUser.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<String, Object> validateUser(
			@ModelAttribute("nyehmsWorkerRegionsViewVO") NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		administrationService.validateUser(nyehmsWorkerRegionsViewVO , bindingResult, userObject);
		Map<String, Object> map = new HashMap<>();
		if(bindingResult.hasErrors()){
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error : errors){
				if(error.getObjectName().equals("UsersExists")){
					map.put("transactionResult", "UsersExists");
					map.put("workerId", nyehmsWorkerRegionsViewVO.getWorkerId());
					map.put("userFirstName", nyehmsWorkerRegionsViewVO.getFirstName());
					map.put("userLastName", nyehmsWorkerRegionsViewVO.getLastName());
				}
				if(error.getObjectName().equals("InvalidUser")){
					map.put("transactionResult", "InvalidUser");
				}
				if(error.getObjectName().equals("SQLError")){
					map.put("transactionResult", "fail");
				}
			}
		}else{
			map.put("transactionResult", "success");
			map.put("userFirstName", nyehmsWorkerRegionsViewVO.getFirstName());
			map.put("userLastName", nyehmsWorkerRegionsViewVO.getLastName());
		}
		return map;
	}

	/**
	 * This method is used to create the User in the Database. 
	 * @param nyehmsWorkerRegionsViewVO to store the user's information
	 * @param request HTTP request reference
	 * @return map  storing result information(user created status)   
	 */
	@RequestMapping(value="/creatingNewUser.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<String, Object> creatingNewUser(
			@ModelAttribute("nyehmsWorkerRegionsViewVO") NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<String, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		administrationService.creatingNewUserInHcsAndNyehms(
				nyehmsWorkerRegionsViewVO , bindingResult, userObject);
		return targetMap;
	}

	/**
	 * This method is used to store the user's basic information like first name, Last Name, Email and Training Date 
	 * @param NyehmsUsersVO to store the user's information
	 * @param request HTTP request reference
	 * @return map  storing result information(user's update status)   
	 */
	@RequestMapping(value="/saveUserDetails.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<String, Object> saveUserDetails(
			@ModelAttribute("nyehmsUsersVO") NyehmsUsersVO nyehmsUsersVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<String, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		administrationService.saveUserDetails(nyehmsUsersVO , bindingResult, userObject);
		return targetMap;
	}

	/**
	 * This method is used to Terminate the User
	 * @param workerId refers to the User
	 * @param legacyUid refers to the User's Id 
	 * @param request HTTP request reference
	 * @return map  storing result information(user's termination status)   
	 */
	@RequestMapping(value="/terminateUser.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<String, Object> terminateUser(
			@RequestParam long workerId,
			@RequestParam String legacyUid,
			HttpServletRequest request, UserObject userObject)
	{
		Map<String, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		administrationService.terminateUserFromHCSAndDB(
				workerId,legacyUid, bindingResult, userObject);
		return targetMap;
	}

	/**
	 * This method is used to get the all Mac address details associated to the WorkerId
	 * @param workerId refers to the User's Active record
	 * @param request HTTP request reference
	 * @return jsonResponse storing Mac address details, start date and End Date    
	 */
	@RequestMapping(value="/getUsersMacAddressAndRegionDetails.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody GridObject getUsersMacAddressAndRegionDetails(
			@RequestParam long workerId,
			HttpServletRequest request, UserObject userObject)
	{
		Map<String, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(
				administrationService.getUsersMacAddressAndRegionDetails(
						workerId , bindingResult, userObject));
		return jsonResponse;

	}

	
	
	@RequestMapping("/getSystemNotificationsManagingPage")
	public String getSystemNotificationsManagingPage(
			Map<String, Object> map,
			HttpServletRequest request)
	{	
		return "administration/systemNotifications";
	}
	
	@RequestMapping(value="/getEmailNotificationList.html",
			method = RequestMethod.POST,
			produces = "text/html")
	public String getEmailNotificationList(
			@RequestParam String ACTION_NAME, Map<String, Object> map,
			HttpServletRequest request, UserObject userObject)
	{
		Map<String, String> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		List<Map<String, String>> notificationList = administrationService.getEmailNotificationList(ACTION_NAME , bindingResult, userObject);
		
		map.put("notificationList", notificationList);
		
		map.put("notificationListSize", notificationList.size());
		
		return "administration/showNotificationList";

	}
	
	@RequestMapping(value="/addUserNotification.html",
			method = RequestMethod.POST,
			produces = "text/html")
	public String addUserNotification(
			Map<String, Object> map, HttpServletRequest request)
	{
		return "administration/addUserNotification";

	}
	
	
	@RequestMapping(value="/getUsersEmailList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<String> getUsersEmailList(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return administrationService.getUsersEmailList(bindingResult, userObject);
	
	}
	
	@RequestMapping(value="/getWorkerId.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> getWorkerId(Map<String, Object> map, HttpServletRequest request, @RequestParam String emailAddress, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return administrationService.getWorkerIdUsingEmail(emailAddress);
	
	}
	
	@RequestMapping(value="/saveUpdatedNotifications.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> saveUpdatedNotifications(Map<String, Object> map, HttpServletRequest request, @RequestParam String COLUMN_NAME, @RequestParam(required=false)  String TO_LIST, @RequestParam(required=false)  String CC_LIST, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		administrationService.saveUpdatedNotifications(COLUMN_NAME, TO_LIST, CC_LIST, bindingResult, userObject); 

		
		return targetMap;
	
	}
	
	@RequestMapping(value="/getUserShortNameList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<String> getUserShortNameList(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return administrationService.getUserShortNameList(bindingResult, userObject);
	}	
	
	
	@RequestMapping(value="/getAdminModuleReport")	
	public String getAdminModuleReport(HttpServletRequest request, Map<String, Object> map) {
		
		return "administration/AdminModuleReport";
	}
	
	@RequestMapping(value="/trainingAttestationReport")	
	public String trainingAttestationReport(HttpServletRequest request, Map<String, Object> map) {
		
		return "administration/trainingAttestationReport";
	}
	
	@RequestMapping(value="/getTrainingAttstData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getTrainingAttstData(HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		GridObject jsonResponse = getGridObject.GetGrid( administrationService.getTrainingAttstData(dummyBindingResult, userObject));
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/teleworkAttestationReport")	
	public String teleworkAttestationReport(HttpServletRequest request, Map<String, Object> map) {
		
		return "administration/teleworkAttestationReport";
	}
	
	@RequestMapping(value="/getTeleworkAttstData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getTeleworkAttstData(HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		GridObject jsonResponse = getGridObject.GetGrid( administrationService.getTeleworkAttstData(dummyBindingResult, userObject));
		
		return jsonResponse;
	}
	
	
	
	@RequestMapping(value="/getSCReportData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject  getSCReportData(@RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		GridObject jsonResponse = getGridObject.GetGrid( administrationService.getSCReportData(fromDate, toDate, dummyBindingResult, userObject));
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/getTeleCommuteReportData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject  getTeleCommuteReportData(@RequestParam String teleCommute, @RequestParam(required=false) String fromDate, @RequestParam(required=false) String toDate, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		GridObject jsonResponse = getGridObject.GetGrid( administrationService.getTeleCommuteReportData(teleCommute, fromDate, toDate, dummyBindingResult, userObject));
		
		return jsonResponse;
	}
	
	@RequestMapping("/getUserDetailsData.html")
	public String getUserDetailsData(@RequestParam("workerId") int workerId, Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		
		NyehmsUsersVO userDetailsVO = administrationService.getUserDetailsData(workerId, userObject);
		map.put("userDetailsVO", userDetailsVO);
		
		return "administration/displayUserDetailData";
	}	
	
}
