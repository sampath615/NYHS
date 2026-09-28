package gov.ny.state.doh.bhae.nyehms.web.spring.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
Author : Srikar Kummamuri
Date Created : January-31-2012
Description : This class is used to for all common implementations across the applications
Ex: Login, logout, errorpage etc..
*/

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.JsonObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserInitObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserAuthorization;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;


@Controller
public class CommonController {	
	
	@Autowired UserAuthorization userAuthorization;
	@Autowired GetGridObject     getGridObject;
	@Autowired CommonService     commonService;
		
	private static Logger log = LoggerFactory.getLogger(CommonController.class);
	
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	UserObject userObject=null;
	
	/**
	 * login method is used to check the "userObject" in the session, if the object is not available will redirect to Crypto Card/Login page
	 * If the object is available will load the initialize parameters and validating the User training date and User Initial Object 
	 * 
	 * If the User training date is expired then the the page will redirect login page with the Expired error message
	 * 
	 * While logging the user will load the UserInitObject(regarding user initialize parameters and user login issues). If this object is not loaded will redirect to the 
	 * Initialize method.
	 * 
	 * via HTTP POST method
	 * @param map java.util.Map reference 
	 * @param request HTTP request reference
	 * @param response HTTP response reference
	 */
	
	@RequestMapping("/login")
	public String login(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
	{	
		HttpSession session = ((HttpServletRequest)request).getSession(true);
		String returnString="";
		String user=request.getParameter("user");
		log.debug(nyehmsLog+"-- User from request :: {}",user);
		
		//Checking the User parameter from the request
		if(user==null || user.equalsIgnoreCase("null")){
			//checking the UserObject from the session
			if(session.getAttribute("UserObj") != null){
				userObject = (UserObject)session.getAttribute("UserObj");
			}else{
				userObject = null;
			}
		}else{		
			//If the request Parameter is coming from the request then loading the User Object from the backend
			userObject=userAuthorization.getUserObject(user);
		}
		log.debug(nyehmsLog+" User Object ::: {}",userObject);
		//Validate the user against DB		
		if(userObject!=null && "Y".equals(EnvironmentProperties.envProperties.getProperty("byPass"))){
			session.setAttribute("UserObj", userObject);
			//If the User initialize object is not loaded loading the method to validate the USer details  
			Map<String, String> targetMap= new HashMap<>();
			BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
			//set the User login date in the Date base to show the current logged in date for the next log in
			commonService.setLoginDate(userObject.getWorkerId(), bindingResult);
			//Checking the User training date
			int statusCode = commonService.getTrainingDate(userObject.getUserId(), bindingResult);
			if(statusCode < 0){
				//If the training date is already expired
				map.put("ErrorMessage", targetMap.get("TRANSACTION_MESSAGE"));
				return "initialize/InitializeError";
			}else{
				//If the training date is expiring less than 45 days.
				if(targetMap.get("TRANSACTION_MESSAGE") != null)
					session.setAttribute("confidentialityAndSecurityTrainingDateStatusMessage", targetMap.get("TRANSACTION_MESSAGE"));
			}
			log.debug(nyehmsLog+" User Worker ID ::: {}",userObject.getWorkerId());
			//Loading the States map from database
			Map<String, String> stateMap = commonService.getStateCodes(bindingResult, userObject, request.getSession().getId());
			//Loading the County map from database
			Map<String, String> countyMap = commonService.getCountyCodes(bindingResult, userObject, request.getSession().getId());
			session.setAttribute("stateMap", stateMap);
			Map<String, String> countryMap = commonService.getCountryCodes(bindingResult, userObject, request.getSession().getId());
			session.setAttribute("countryMap", countryMap);
			session.setAttribute("countyMap", countyMap);
			JSONObject userPermissionsList = commonService.getUserPermissions(userObject.getWorkerId(), null, bindingResult);
			session.setAttribute("userPermissions", userPermissionsList);
			if(userPermissionsList.get("98") != null){
				List<JSONObject> psMessageTypes = commonService.getPsMessageTypes();
				session.setAttribute("psMessageTypes", psMessageTypes);
			}
			if(userPermissionsList.get("214") != null){
				JSONObject crfNeededActivities = commonService.getCRFReqActivityList();
				session.setAttribute("crfNeededActivities", crfNeededActivities);
			}
			session.setAttribute("staticPath", "/hpn/ctrldocs/nyehms");
			return "redirect:getAssignmentsTally";
		}else{
			if(user != null){
				map.put("TRANSACTION_MESSAGE","User not available");
			}
			returnString = EnvironmentProperties.envProperties.getProperty("loginPage");
			//returnString = "login/Login";
		}
		return returnString;
	}

	/**
	 * Handler method for moving provider data to facility. Accepts json request 
	 * via HTTP POST method
	 * @param request HTTP request reference
	 * @return result after the transaction   
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response)
	{	
		HttpSession session = ((HttpServletRequest)request).getSession(true);
		if(session != null){
			session.removeAttribute("UserObj");
		}
		if(log.isDebugEnabled()) {
			log.debug("Removed the user object from session");
		}
		try {
			if(log.isDebugEnabled()) {
				log.debug("Redirecting the page to HCL Application page");
			}
			response.sendRedirect(EnvironmentProperties.envProperties.getProperty("loggout_url"));
			return "";
		} catch (IOException e) {
			log.error("Unable to rediect the page to HCS home page while logout  {}",e.getMessage());
		}
		return EnvironmentProperties.envProperties.getProperty("initialLoadPage");
	}

	/**
	 * Handler method for moving provider data to facility. Accepts json request 
	 * via HTTP POST method
	 * @param request HTTP request reference
	 * @return result after the transaction   
	 */
	@RequestMapping(value="/getAddressDropDownInfo.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonObject getAddressDropDownInfoAsJson(
			@RequestParam String fieldName,
			@RequestParam String city,
			@RequestParam String county,
			@RequestParam String state,
			@RequestParam String zip,
			HttpServletRequest request, UserObject userObject)
	{
		Map<String, String> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		AddressFieldVO addressFieldVO= new AddressFieldVO();				
		addressFieldVO.setFieldName(fieldName.toUpperCase());
		addressFieldVO.setCity(city);
		addressFieldVO.setCounty(county);		
		addressFieldVO.setState(state);		
		addressFieldVO.setZip(zip);
		if(addressFieldVO.getCountry().equals("Unknown") || addressFieldVO.getCountry().equals("")){
			addressFieldVO.setCountry("");
		}
		JsonObject jsonResponse = getGridObject.GetJsonObject(commonService.getAddressDropDownInfo(addressFieldVO, bindingResult, userObject, request.getSession().getId()));
		return jsonResponse;
	}

	/**
	 * Handler method for moving provider data to facility. Accepts json request 
	 * via HTTP POST method
	 * @param request HTTP request reference
	 * @return result after the transaction   
	 */
	@RequestMapping("/errorPage")
	public String errorPage(HttpServletRequest request)
	{	
		return "common/errorPage";
	}

	/**
	 * Handler method for moving provider data to facility. Accepts json request 
	 * via HTTP POST method
	 * @param request HTTP request reference
	 * @return result after the transaction   
	 */
	@RequestMapping(value="/getImprovedStandardAddress", method = RequestMethod.POST, produces = "application/html")
	public @ResponseBody String getImprovedStandardAddress(
			@RequestParam String customAddress,
			HttpServletRequest request)
	{	
		String improvedCustomAddress = commonService.getImprovedStandardAddress(customAddress);
		return improvedCustomAddress;
	}

	/**
	 * Checks the combination for address fields 
	 * via HTTP POST method
	 * @param request HTTP request reference
	 * @return result after the transaction   
	 */
	@RequestMapping(value="/getAddressFieldsCombination.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> getAddressFieldsCombination(
			@ModelAttribute("addressFieldVO") AddressFieldVO addressFieldVO,
			HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		commonService.getAddressFieldsCombination(addressFieldVO, bindingResult, userObject);
		return targetMap;
	}

	@RequestMapping(value="/getSoundexValue", method = RequestMethod.POST, produces = "application/html")
	public @ResponseBody String getSoundexValue(
			@RequestParam String name,
			HttpServletRequest request)
	{
		String soundexValue = commonService.getSoundexValue(name);
		return soundexValue;
	}
	
	@RequestMapping(value="/getCurrentActiveStateNo", method = RequestMethod.POST, produces = "application/html")
	public @ResponseBody String getCurrentActiveStateNo(
			@RequestParam String stateno,
			HttpServletRequest request)
	{
		String current_stateno = commonService.getCurrentActiveStateNo(stateno);
		return current_stateno;
	}
	
	
	
	
	@RequestMapping(value="/agreeNYEHMSTANDC")
	public String agreeNYEHMSTANDC(
			HttpServletRequest request, Map<String, Object> map)
	{		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<>();

		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		HttpSession session = ((HttpServletRequest)request).getSession(true);
		
		int resultCount = commonService.insertNyehmsAgreedTime(userObject);
        
		if(resultCount  > 0){
			
			
				
			
			if(session.getAttribute("UserObj") != null){
				userObject = (UserObject)session.getAttribute("UserObj");
			}
			
			UserInitObject userInitObj = (UserInitObject)session.getAttribute("userInitObj");
			
			if(userObject!=null && userInitObj != null && userInitObj.isCryptoCardAuthenticationSuccess()){
				//If the User initialize object is not loaded loading the method to validate the USer details  
				//set the User login date in the Date base to show the current logged in date for the next log in
				commonService.setLoginDate(userObject.getWorkerId(), bindingResult);
				//Checking the User training date
				int statusCode = commonService.getTrainingDate(userObject.getUserId(), bindingResult);
				if(statusCode < 0){
					//If the training date is already expired
					map.put("ErrorMessage", targetMap.get("TRANSACTION_MESSAGE"));
					return "initialize/InitializeError";
				}else{
					//If the training date is expiring less than 45 days.
					if(targetMap.get("TRANSACTION_MESSAGE") != null)
						session.setAttribute("confidentialityAndSecurityTrainingDateStatusMessage", targetMap.get("TRANSACTION_MESSAGE"));
				}
				log.debug(nyehmsLog+" User Worker ID ::: {}", userObject.getWorkerId());
				//Loading the States map from database
				Map<String, String> stateMap = commonService.getStateCodes(bindingResult, userObject, request.getSession().getId());
				//Loading the County map from database
				Map<String, String> countyMap = commonService.getCountyCodes(bindingResult, userObject, request.getSession().getId());
				session.setAttribute("stateMap", stateMap);
				Map<String, String> countryMap = commonService.getCountryCodes(bindingResult, userObject, request.getSession().getId());
				session.setAttribute("countryMap", countryMap);
				session.setAttribute("countyMap", countyMap);
				JSONObject userPermissionsList = commonService.getUserPermissions(userObject.getWorkerId(), null, bindingResult);
				session.setAttribute("userPermissions", userPermissionsList);
				if(userPermissionsList.get("98") != null){
					List<JSONObject> psMessageTypes = commonService.getPsMessageTypes();
					session.setAttribute("psMessageTypes", psMessageTypes);
				}
				if(userPermissionsList.get("214") != null){
					JSONObject crfNeededActivities = commonService.getCRFReqActivityList();
					session.setAttribute("crfNeededActivities", crfNeededActivities);
				}

				session.setAttribute("staticPath", "/hpn/ctrldocs/nyehms");
				return "redirect:getAssignmentsTally";
			
			
			
			}else{
				
				map.put("ErrorMessage", "User not available or not authenticated");
				
				
				 return "initialize/InitializeError";
			}
		}else{
			
			map.put("ErrorMessage", "Currently facing few difficulties to login into NYEHMS, Please contact helpline.");
			
			
			 return "initialize/InitializeError";
		}
        
        
	}
	
    
    @RequestMapping(value = "/getReciepientDetails.json",  method = RequestMethod.POST, produces = "application/json")
    	public @ResponseBody List<JSONObject> getReciepientDetails(HttpServletRequest request) {
            
            List<JSONObject> reciepientDetails = commonService.getReciepientDetails();            
            return reciepientDetails;
        
    }
    
    @RequestMapping(value="/getPsMessageTypes.html",
			method = RequestMethod.POST,
			produces = "text/html")
	public String getPsMessageTypes(
			Map<String, Object> map, HttpServletRequest request)
	{
    	List<JSONObject> psMessageTypes = commonService.getPsMessageTypes();
		
		map.put("psMessageTypes", psMessageTypes);
		
		return "common/psMessageType";

	}
}
