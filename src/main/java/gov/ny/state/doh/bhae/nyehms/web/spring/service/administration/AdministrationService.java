package gov.ny.state.doh.bhae.nyehms.web.spring.service.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.ActiveStage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration.NyehmsWorkerRegionsViewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration.SearchManageProfileDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.HCSUserInfo;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.HCSUserPermInfo;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsRole;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsUsersVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsWorkerRegionsViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.UserRegionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.hcswebservice.NyhemsHCSWebServiceOp;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.webservice.oauth.WebServiceConstants;


@Service
public class AdministrationService {

	@Autowired
	private ActiveStage activeStage;
	
	private static final Logger log = LoggerFactory.getLogger(AdministrationService.class);
	
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	private static final String APP_NAME_ABBREVATION = "nyehms";
	private static final String WEB_SERVICE_PERMCODE = "ehmsuser";
	private static final String ADD_NEW_REGION_ROLE_SUCCESS_MESSAGE = "New region role has been added";
	private final String CREATE_USER_SUCCESS_MESSAGE="User created succesfully";
	private static final String TERMINATE_USER_SUCCESS_MESSAGE = "User has been terminated successfully";
	private static final String UPDATE_REGION_PERMISSIONS_SUCCESS_MESSAGE = "Successfully updated the permissions";
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	@Autowired
	private NyehmsWorkerRegionsViewDAO nyehmsWorkerRegionsViewDAO ;
	@Autowired
	private SearchManageProfileDAO searchManageProfileDAO;
	@Autowired
	NyhemsHCSWebServiceOp nyhemsHCSWebServiceOp;
	@Autowired
	CommonDAO commonDAO;
	
	@Autowired
	private DateUtil dateUtil; 
	//success message while creating an user
	//Service to Get the  Admin Search region Description.
	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	AdministrationTransactionHelper administrationTransactionHelper;
	
	
	/**
	 * Returns the Map containing Region Code as Key and Region description as values
	 * 
	 * This data will render all the Region codes in the Database 
	 *
	 * @param  bindingResult  using to store error related information
	 * @param userObject, sessionID  used to track Exception related information 
	 * 
	 * @return regionTypes(Map) of Region codes
	 */
	public Map<String, String> getSrchregionTypes(BindingResult result, UserObject userObject){
			boolean errorFlag=false;
			Map<String, String> regionTypes = new LinkedHashMap<>();
			
				List<Map<String, String>> regionTypesList = nyehmsWorkerRegionsViewDAO.getSearchRegionDescription();
				for(Map<String, String> regionTypeMap : regionTypesList){
					regionTypes.put(regionTypeMap.get("regionCode"), regionTypeMap.get("regionDescription"));
				}
			return regionTypes;
		}
		
   
	/**
	 * Returns the Map containing Role Code as Key and Role description as values
	 * 
	 * This data will render all the Role codes in the Database 
	 *
	 * @param  bindingResult  using to store error related information
	 * @param userObject, sessionID  used to track Exception related information 
	 * 
	 * @return regionTypes(Map) of Role codes
	 */	
	public List<NyehmsRole> getSrchroleTypes(BindingResult result, UserObject userObject){
			boolean errorFlag=false;
			List<NyehmsRole> nyehmsRoles= new ArrayList<>();
				nyehmsRoles = nyehmsWorkerRegionsViewDAO.getSearchRoleDescription();
			return nyehmsRoles;
		}
		
		
	//Not using this method commenting temporarily
	/*
	 * Returns the all user status available in the database 
	 *
	 * @param  bindingResult  using to store error related information
	 * @param userObject, sessionID  used to track Exception related information 
	 * 
	 * @return statusTypesList(List) of Status types
	
	@Override	
	public List<String> getSrchstatusTypes(BindingResult result, UserObject userObject){

			boolean errorFlag=false;
			List<String> statusTypesList = new ArrayList<String>();
				 statusTypesList = nyehmsWorkerRegionsViewDAO.getSearchstatusDescription();
			return statusTypesList;
	}
	 */
	
	
	

	/**
	 * Returns the complete User's list containing the First name,
	 * Last name, middle name, Email Address, Region code, role code and UserId(Shortname)
	 * 
	 * This data will render depends on the search critera
	 * 
	 * User can search with using Wild Character also
	 * 
	 * Ex: If user id start with 's' then user can search with  's*'
	 * 
	 * This wild card will be applicable for all the fields  
	 *
	 * @param  nyehmsWorkerRegionsViewVO  User object related bean 
	 * @param  bindingResult  using to store error related information
	 * @param userObject, sessionID  used to track Exception related information
	 * 
	 * 
	 * @return   nyehmsWorkerRegionsViewVOList   List of users
	 */
	public List<NyehmsWorkerRegionsViewVO>  getSearchUsersList(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult result, UserObject userObject){
		List<NyehmsWorkerRegionsViewVO> nyehmsWorkerRegionsViewVOList = new ArrayList<>();
			Map<Object, Object> inOutParameterMap = new HashMap<>();
			inOutParameterMap.put("p_shortname", nyehmsWorkerRegionsViewVO.getLegacyUid());
			inOutParameterMap.put("p_fname", nyehmsWorkerRegionsViewVO.getFirstName());
			inOutParameterMap.put("p_lname",nyehmsWorkerRegionsViewVO.getLastName());	
			inOutParameterMap.put("p_mname", nyehmsWorkerRegionsViewVO.getMiddleName());
			inOutParameterMap.put("p_email", nyehmsWorkerRegionsViewVO.getEmailAddress());
			inOutParameterMap.put("p_region_cd", nyehmsWorkerRegionsViewVO.getRegionCode());
			inOutParameterMap.put("p_role_cd", nyehmsWorkerRegionsViewVO.getRoleCode());
			inOutParameterMap.put("p_users_out",nyehmsWorkerRegionsViewVOList);
			nyehmsWorkerRegionsViewDAO.getUsersList(inOutParameterMap);
			nyehmsWorkerRegionsViewVOList= (List<NyehmsWorkerRegionsViewVO>) inOutParameterMap.get("p_users_out");
		return nyehmsWorkerRegionsViewVOList;
	}

	
	/**
	 * Returns the complete Region roles code list for a user(Worker Id)
	 * 
	 * This method will be used when user wants to know the Region role code associated to
	 * User then this method will be used.
	 *  
	 * Every User will have a WorkerId. User can have a multiple worker ids but only one
	 * workerId will be Active remaining will be retired.
	 * 
	 * @param  workerId  User's unique identification 
	 * @param  bindingResult  using to store error related information
	 * @param userObject, sessionID  used to track Exception related information
	 * 
	 * 
	 * @return   nyehmsWorkerRegionsViewVOList List of UserRegionVO(Region role list)
	 */
	public List<UserRegionVO> getRegionRoleByUserIdList(long workerId,  BindingResult result, UserObject userObject){

		List<UserRegionVO> nyehmsWorkerRegionsViewVOList = new ArrayList<>();
		
		nyehmsWorkerRegionsViewVOList = nyehmsWorkerRegionsViewDAO.getRegionRoleByUserIdList(workerId);
		
		return nyehmsWorkerRegionsViewVOList;
	}
	
	 //Adding new region/role for existing user
	/**
	 * This method is used to add a new Region role and its Permissions to the Existing User (Worker Id)
	 * 
	 * When you add any Regionrole then the start date will be created date
	 * 
	 * 
	 * @param  NyehmsWorkerRegionsViewVO which holds the Region and Role information and Permissions associated to this Role.  
	 * @param  bindingResult  using to store error related information and Database transaction status
	 * @param userObject, sessionID  used to track Exception related information
	 * 
	 */
	public void  addingNewRegionRole(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		
		Map<Object, Object> inOutParamaterMap = new HashMap<>();
		inOutParamaterMap.put("p_user_id", nyehmsWorkerRegionsViewVO.getWorkerId());
		inOutParamaterMap.put("p_region_code", nyehmsWorkerRegionsViewVO.getRegionCode());
		inOutParamaterMap.put("p_role_code",nyehmsWorkerRegionsViewVO.getRoleCode());
		inOutParamaterMap.put("p_created_by", userObject.getWorkerId());
		inOutParamaterMap.put("p_permissions_list",commonUtil.convertToCommaSeparated(nyehmsWorkerRegionsViewVO.getPermissions()));
		nyehmsWorkerRegionsViewDAO.addingNewRegionRole(inOutParamaterMap);
		statusCode=(Integer)inOutParamaterMap.get("p_code_out");
		transactionMessage=statusCode==0?ADD_NEW_REGION_ROLE_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}

    
    // validating the userID in DB
    /**
	 * This method is used to Validate the User Status
	 * 
	 * If user is Active and not exist and New user can be created
	 * 
	 * If you user is not Exists will throw the Error back.
	 * 
	 * If the User is Active and Exists already will show some message with hyperlink(This message will be written in JAVAScript)
	 * 
	 * @param  nyehmsWorkerRegionsViewVO  which holds User's short name. Once the Userid validated user's basic information will be set to the Object
	 * @param  bindingResult  using to store error related information and Database transaction status(validation status)
	 * @param userObject, sessionID  used to track Exception related information
	 */
	public void  validateUser(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult result, UserObject userObject){
		boolean errorFlag=false;
		String workerId = null;
		String userStage = activeStage.name();
		try {
			
			
			workerId = nyehmsWorkerRegionsViewDAO.isUserExists(nyehmsWorkerRegionsViewVO.getLegacyUid().toLowerCase()); // check for  user in nyehms
							
			

			// calling DAO for getting clientsecret, base url, endpoint url
			Map<Object, Object> wsMap = new HashMap<>();
			wsMap.put("userStage", userStage); // userStage
			wsMap.put("function", "profile");

			WebServiceConstants wsConstants = commonDAO.getWebServiceConstants(wsMap);

			if (workerId != null) {
				nyehmsWorkerRegionsViewVO.setWorkerId(Integer.parseInt(workerId));
				result.addError(new ObjectError("UsersExists", "User is Active"));
				return;
			}

			HCSUserInfo userInfo = nyhemsHCSWebServiceOp.getHCSUserInfo(nyehmsWorkerRegionsViewVO.getLegacyUid(), wsConstants);

			if (workerId == null && userInfo == null) {
				// user does not exist in hcs
				result.addError(new ObjectError("InvalidUser", "User is InValid"));
			} else {
				nyehmsWorkerRegionsViewVO.setFirstName(userInfo.getFirstName()); // hcs user exists
				nyehmsWorkerRegionsViewVO.setLastName(userInfo.getLastName()); // hcs user exists
				if (workerId != null) nyehmsWorkerRegionsViewVO.setWorkerId(Integer.parseInt(workerId));
			}
		}catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog +" - "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog +" - "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
		}finally{						
			if(errorFlag){
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));	
			}
		}
	}
    
	public void terminateUserFromHCS(String legacyUid, BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		boolean errorFlag=false;
			String userStage = activeStage.name();
			WebServiceConstants wsConstants = null;
		try {
    		
				//fetching parameters from db for WebServiceConstants
				Map<Object, Object> wsMap = new HashMap<>();
				wsMap.put("userStage", userStage); // userStage
				wsMap.put("function", "removePermForUser");
				
				wsConstants = commonDAO.getWebServiceConstants(wsMap);
	    		
				//passing the parameters to webservices
				HCSUserPermInfo hcsUserPermInfo = new HCSUserPermInfo();
				
				hcsUserPermInfo.setEcommerceId(legacyUid.toLowerCase());
				hcsUserPermInfo.setApplAbbr(APP_NAME_ABBREVATION);
				hcsUserPermInfo.setPermCode(WEB_SERVICE_PERMCODE);
				hcsUserPermInfo.setClassCode("all");
				hcsUserPermInfo.setGrpCode("all");
				hcsUserPermInfo.setModifiedBy(userObject.getUserId().toLowerCase());
				
				
	    		//Executing the Webservice related to Termination 
				 JSONObject jsonObj = nyhemsHCSWebServiceOp.removeNyehmsPermForUser(hcsUserPermInfo, APP_NAME_ABBREVATION, wsConstants);
	    		
				 
				 if(jsonObj != null && jsonObj.get("msg").toString().equals("SUCCESS")) {
					commonExceptionHandler.inspectDBTransacionResult(0, TERMINATE_USER_SUCCESS_MESSAGE, bindingResult);
				 }else {
					 errorFlag = true;
				 }
	    	}catch (DataAccessException e) {
				errorFlag = true;
				log.error(nyehmsLog +" - {}", e.getMessage());
				commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
			} 
			catch (Exception e1) {
	    		errorFlag=true;
	    		log.error(nyehmsLog +" - {}", e1.getMessage());
	    		commonExceptionHandler.handleException(e1, userObject,userObject.getSessionId());
	    		//e1.printStackTrace();
	    	}
	    	if(errorFlag){
	    		transactionMessage = "Failed to terminate the user :" + legacyUid +", Please contact helpline.";
	    		throw new DataRetrievalFailureException(transactionMessage);
	    		//commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
	    	}
		}
    
 
    
	/*
	 * Creating New USer 
	 * @see gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.AdministrationService#creatingNewUser(gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsWorkerRegionsViewVO, org.springframework.validation.BindingResult, gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject, java.lang.String)
	 */
    /**
   	 * This method is used to create the User
   	 * 
   	 * @param  nyehmsWorkerRegionsViewVO  holds the users details including Mac address and Region roles and their permissions
   	 * @param  bindingResult  using to store error related information and Database transaction status(user insertion status)
   	 * @param userObject, sessionID  used to track Exception related information
   	 */
	public void  creatingNewUserInHcs(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult bindingResult, UserObject userObject){
		boolean errorFlag=false;
		WebServiceConstants wsConstants = null;
		String userStage = activeStage.name();
		try {
			
			// calling DAO for getting client secret, base url, endpoint url
						Map<Object, Object> wsMap = new HashMap<>();
						wsMap.put("userStage", userStage); // userStage
						wsMap.put("function", "permissions");

						wsConstants = commonDAO.getWebServiceConstants(wsMap);
			
			//Calling a HCS Webservice of permission to check the User status
			HCSUserPermInfo permUserInfo = nyhemsHCSWebServiceOp.getHCSUserPermInfo(nyehmsWorkerRegionsViewVO.getLegacyUid(), wsConstants);

			if(permUserInfo == null || StringUtils.isBlank(permUserInfo.getUserName())){
			
	    		HCSUserPermInfo hcsUserPermInfo = new HCSUserPermInfo();
	    		
	    		hcsUserPermInfo.setEcommerceId(nyehmsWorkerRegionsViewVO.getLegacyUid().toLowerCase());
	    		hcsUserPermInfo.setApplAbbr(APP_NAME_ABBREVATION);
	    		hcsUserPermInfo.setPermCode(WEB_SERVICE_PERMCODE);
	    		hcsUserPermInfo.setClassCode("all");
	    		hcsUserPermInfo.setGrpCode("all");
	    		hcsUserPermInfo.setModifiedBy(userObject.getUserId().toLowerCase());
	    		
	    		//reusing wsMap to set function to call ws constants from DB
	    		wsMap.put("function", "addPermForUser");
	    		wsConstants = commonDAO.getWebServiceConstants(wsMap);
				
	    	 JSONObject jsonObj = nyhemsHCSWebServiceOp.addNyehmsPermForUser(hcsUserPermInfo, APP_NAME_ABBREVATION, wsConstants);// expecting msg as success
				
				
				if(jsonObj != null && jsonObj.get("msg").toString().equals("SUCCESS")) {
					commonExceptionHandler.inspectDBTransacionResult(0, CREATE_USER_SUCCESS_MESSAGE, bindingResult);
				}else {
					errorFlag = true;
				}
				
		     }else {
		    	 log.info("User already has Nyehms Permission.");
		     }
		
		}catch (Exception e1) {
			errorFlag=true;
			log.error(nyehmsLog +" - "+ e1.getMessage());
			commonExceptionHandler.handleException(e1, userObject,userObject.getSessionId());
		}
		if(errorFlag){
			throw new DataRetrievalFailureException("Webservice failure while creating new User :" + nyehmsWorkerRegionsViewVO.getLegacyUid());
		}
	}
	public int  creatingNewUserInNyehms(NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO, BindingResult bindingResult, UserObject userObject){	
		
			String transactionMessage=null;
			Integer statusCode=null;
			
		    //If the User is Active will insert the Users information in to the DB
			Timestamp effectiveStartDate = dateUtil.getSqlDateTimeStamp(nyehmsWorkerRegionsViewVO.getEffectiveStartDate());
			
			Timestamp lastConfidentialityAndSecurityTrainingDate = dateUtil.getSqlDateTimeStamp(nyehmsWorkerRegionsViewVO.getLastConfidentialityAndSecurityTrainingDate());
			
			Timestamp vpnApproveDate = dateUtil.getSqlDateTimeStamp(nyehmsWorkerRegionsViewVO.getVpnApproveDate());
			Timestamp telecommuteTrngDate = dateUtil.getSqlDateTimeStamp(nyehmsWorkerRegionsViewVO.getTelecommuteTrainingDate());
			
//			String macAddress =nyehmsWorkerRegionsViewVO.getMacAddress().toUpperCase();
			Map<Object, Object> inOutParameterMap = new HashMap<>();
			inOutParameterMap.put("p_legacy_uid", nyehmsWorkerRegionsViewVO.getLegacyUid().toUpperCase());
			inOutParameterMap.put("p_first_name", nyehmsWorkerRegionsViewVO.getFirstName());
			inOutParameterMap.put("p_last_name", nyehmsWorkerRegionsViewVO.getLastName());
			inOutParameterMap.put("p_middle_name", nyehmsWorkerRegionsViewVO.getMiddleName());
			inOutParameterMap.put("p_start_date", effectiveStartDate);
			inOutParameterMap.put("p_email_addr", nyehmsWorkerRegionsViewVO.getEmailAddress());
			inOutParameterMap.put("p_last_conf_secure_trng_date", lastConfidentialityAndSecurityTrainingDate);
//			inOutParameterMap.put("p_mac_address", macAddress);
			inOutParameterMap.put("p_region_code", nyehmsWorkerRegionsViewVO.getRegionCode());
			inOutParameterMap.put("p_role_code", nyehmsWorkerRegionsViewVO.getRoleCode());
			inOutParameterMap.put("p_created_by",userObject.getWorkerId());
			inOutParameterMap.put("p_permissions_list",commonUtil.convertToCommaSeparated(nyehmsWorkerRegionsViewVO.getPermissions()));
			inOutParameterMap.put("p_vpn",nyehmsWorkerRegionsViewVO.getVpnAccess());
			inOutParameterMap.put("p_vpn_approve_date",vpnApproveDate);
			inOutParameterMap.put("p_telecommute",nyehmsWorkerRegionsViewVO.getTelecommute());
			inOutParameterMap.put("p_telecommute_trng_date",telecommuteTrngDate);
			inOutParameterMap.put("p_user_type",nyehmsWorkerRegionsViewVO.getUserTypeCode());
			inOutParameterMap.put("p_facility_group_cd",nyehmsWorkerRegionsViewVO.getUserFacilityGroupCode());
			
			inOutParameterMap.put("p_bureau_start_date",nyehmsWorkerRegionsViewVO.getBureauStartDate());
			inOutParameterMap.put("p_position_start_date",nyehmsWorkerRegionsViewVO.getPositionStartDate());
			inOutParameterMap.put("p_pc_access_at_location",nyehmsWorkerRegionsViewVO.getPcAccessAtLocation());
			inOutParameterMap.put("p_bureau_name",nyehmsWorkerRegionsViewVO.getBureauName());
			inOutParameterMap.put("p_reg_office",nyehmsWorkerRegionsViewVO.getRegOffice());
			inOutParameterMap.put("p_remote_terminal_access",nyehmsWorkerRegionsViewVO.getRemoteTerminalAccess());
			
			inOutParameterMap.put("p_tlcmmtTrngAtstn",nyehmsWorkerRegionsViewVO.getTlcmmtTrngAtstn());
			inOutParameterMap.put("p_cnfSecTrngAtstn",nyehmsWorkerRegionsViewVO.getCnfSecTrngAtstn());
			inOutParameterMap.put("p_tlcmmtTrngBy",nyehmsWorkerRegionsViewVO.getTlcmmtTrngBy());
			inOutParameterMap.put("p_cnfSecTrngBy",nyehmsWorkerRegionsViewVO.getCnfSecTrngBy());
			
			inOutParameterMap.put("p_comments",nyehmsWorkerRegionsViewVO.getUserComments());
				
			nyehmsWorkerRegionsViewDAO.creatingNewUser(inOutParameterMap);
			statusCode=(Integer)inOutParameterMap.get("p_code_out");
			transactionMessage=statusCode==0?CREATE_USER_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
			if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
			
			
			
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
			
			return statusCode;
	}
    
    
	/**
	 * This method is used to Save the user's basic information like first name, middle name, Email, CNS Date
	 * 
	 * @param  nyehmsUsersVO  User's basic information
	 * @param  bindingResult  using to store error related information and Database transaction status(user termination status)
	 * @param userObject, sessionID  used to track Exception related information  and Database transaction status(insertion status)
	 */
    //save user details of manageProfile module in DB
    public void  saveUserDetails(NyehmsUsersVO nyehmsUsersVO, BindingResult bindingResult, UserObject userObject){
			
			Map<Object, Object> inOutParameterMap = new HashMap<>();
			inOutParameterMap.put("firstName", nyehmsUsersVO.getFirstName());
			inOutParameterMap.put("lastName", nyehmsUsersVO.getLastName());
			inOutParameterMap.put("emailAddress", nyehmsUsersVO.getEmailAddress());
			
			inOutParameterMap.put("bureauStartDate", nyehmsUsersVO.getBureauStartDate());
			inOutParameterMap.put("positionStartDate", nyehmsUsersVO.getPositionStartDate());
			inOutParameterMap.put("pcAccessAtLocation", nyehmsUsersVO.getPcAccessAtLocation());
			inOutParameterMap.put("bureauName", nyehmsUsersVO.getBureauName());
			inOutParameterMap.put("regOffice", nyehmsUsersVO.getRegOffice());
			inOutParameterMap.put("remoteTerminalAccess", nyehmsUsersVO.getRemoteTerminalAccess());
			
			inOutParameterMap.put("lastConfidentialityAndSecurityTrainingDate", nyehmsUsersVO.getLastConfidentialityAndSecurityTrainingDate());
			inOutParameterMap.put("isLocked", nyehmsUsersVO.getIsLocked());
			inOutParameterMap.put("workerId", nyehmsUsersVO.getWorkerId());
			inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
			
			inOutParameterMap.put("p_tlcmmtTrngAtstn",nyehmsUsersVO.getTlcmmtTrngAtstn());
			inOutParameterMap.put("p_cnfSecTrngAtstn",nyehmsUsersVO.getCnfSecTrngAtstn());
			inOutParameterMap.put("p_tlcmmtTrngBy",nyehmsUsersVO.getTlcmmtTrngBy());
			inOutParameterMap.put("p_cnfSecTrngBy",nyehmsUsersVO.getCnfSecTrngBy());
			
			inOutParameterMap.put("vpnAccess", nyehmsUsersVO.getVpnAccess());
			inOutParameterMap.put("vpnApproveDate", nyehmsUsersVO.getVpnApproveDate());
			inOutParameterMap.put("telecommute", nyehmsUsersVO.getTelecommute());
			inOutParameterMap.put("telecommuteTrainingDate", nyehmsUsersVO.getTelecommuteTrainingDate());
			inOutParameterMap.put("userType", nyehmsUsersVO.getUserType());
		
			int noOfRowsEffected = nyehmsWorkerRegionsViewDAO.saveUserDetails(inOutParameterMap);
			String transactionMessage= noOfRowsEffected > 0 ? "Successfully updated the User details":"Unable to update the User details";
			
			if(StringUtils.isNotBlank(nyehmsUsersVO.getUserComments())){
				inOutParameterMap.put("comments", nyehmsUsersVO.getUserComments());
				inOutParameterMap.put("activityBy", userObject.getUserId());
				if(noOfRowsEffected>0) {
					searchManageProfileDAO.saveUserComments(inOutParameterMap);
				}
			}
			
			commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
    }
	
	public int terminateUserFromDB(long workerId, BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		
		//Terminating the User from the database
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_user_id", workerId);
		inOutParameterMap.put("p_terminated_by",userObject.getWorkerId());	
		log.error("Terminating USer");
		nyehmsWorkerRegionsViewDAO.terminateUser(inOutParameterMap);
		statusCode=(Integer)inOutParameterMap.get("p_code_out");
		log.error("Terminating USer {}",statusCode);
		transactionMessage=statusCode==0?TERMINATE_USER_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		log.error("Terminating USer {}",transactionMessage);
		if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		return statusCode;
	}
	
	
    /**
	 * This method is used to get the User details, Mac Address details, Region role details
	 * 
	 * When user is coming from search page, if user is trying to manage any user then we need to load all the
	 * details at a time.
	 * 
	 * We are getting these three information in 3 cursors
	 * 
	 * p_user_basic_out: User's basic information (First name, last name, email, CNS date)
	 * p_region_roles_out: Region and roles associated to the User
	 * p_mac_addresses_out: Mac address details associated to the user
	 * 
	 * @param  workerId  User unique Identification (Active User's Id)  
	 * @param  bindingResult  using to store error related information and Database transaction status(update status)
	 * @param userObject, sessionID  used to track Exception related information
	 * 
	 * @return   userDetails List of NyehmsUsersVO(Users complete details)
	 */
   	public List<NyehmsUsersVO>  getUsersMacAddressAndRegionDetails(long workerId, BindingResult bindingResult, UserObject userObject){
   		
   			Map<Object, Object> inOutParameterMap = new HashMap<>();
   			
   			inOutParameterMap.put("p_worker_id", workerId);
   			nyehmsWorkerRegionsViewDAO.getUsersMacAddressAndRegionDetails(inOutParameterMap);
   			List<NyehmsUsersVO> userDetails = (List<NyehmsUsersVO>)inOutParameterMap.get("p_user_basic_out");
   			
   			NyehmsUsersVO nyehmsUsersVO = userDetails.get(0);
   			
   			List<UserRegionVO> userRegionDetails = (List<UserRegionVO>)inOutParameterMap.get("p_region_roles_out");
   			
//   			List<UserMacAssociationVO> userMacAddressDetails = (List<UserMacAssociationVO>)inOutParameterMap.get("p_mac_addresses_out");
   			
   			nyehmsUsersVO.setUserRegionDetails(userRegionDetails);
   			
//   			nyehmsUsersVO.setUserMacAddressDetails(userMacAddressDetails);
   			
   			return userDetails;
   		
   	}
    
    
    
	/**
	 * This method is used to update the Region role code
	 * 
	 * Updating the Permissions or Retiring the Region role
	 * 
	 *  Retiring the Region role:
	 *  If the Effective End date is todays date means that retiring Region role. User will not have any access in the Region for that Role 
	 *  If the Effective End date is Future date then user will have access in that Region till to the Future date.
	 *  	 
	 *  
	 *  Every User can have multiple roles in different Regions. Every regionrole will be differentiated through WorkerregionId
	 *   
	 *  If we Terminate any user all the WorkerRegionId will get retired    
	 *   
	 *  UserId: ska03
	 *  WorkerId: 129462, 129461 (user may have one workerregionId or more, Can have one or more Active at a time)
	 *  
	 * @param  userRegionVO  containing workerRegionId, Permissions, Effective End date  
	 * @param  bindingResult  using to store error related information and Database transaction status(update status)
	 * @param userObject, sessionID  used to track Exception related information
	 * 
	 * 
	 */
	public void updateOrRetireRegionRole(UserRegionVO userRegionVO,
			BindingResult result, UserObject userObject){
		
    	Map<Object, Object> inOutParameterMap = new HashMap<>();
    	
		Integer statusCode=null;
		String transactionMessage=null;
		
		
		if(userRegionVO.getEndDate() != null && userRegionVO.getEndDate() != ""){
			
			
			Timestamp workerRegionEndDate = dateUtil.getSqlDateTimeStamp(userRegionVO.getEndDate());
			
			
			inOutParameterMap.put("p_user_region_id", userRegionVO.getWorkerRegionId());
			inOutParameterMap.put("P_END_DATE", workerRegionEndDate);
			inOutParameterMap.put("p_terminated_by", userObject.getWorkerId());
			
			nyehmsWorkerRegionsViewDAO.retireRegionRole(inOutParameterMap);
			
			statusCode=(Integer)inOutParameterMap.get("p_code_out");
			transactionMessage=statusCode==0?"Successfully updated the Region Role retired date.":(String)inOutParameterMap.get("p_msg_out");
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, result);
		}else{
	
			
			inOutParameterMap.put("p_worker_region_id", userRegionVO.getWorkerRegionId());
			inOutParameterMap.put("p_updated_by",userObject.getWorkerId());	
			inOutParameterMap.put("p_permissions_list",commonUtil.convertToCommaSeparated(userRegionVO.getPermissions()));
			nyehmsWorkerRegionsViewDAO.editUserPermissions(inOutParameterMap);
			statusCode=(Integer)inOutParameterMap.get("p_code_out");
			transactionMessage=statusCode==0?UPDATE_REGION_PERMISSIONS_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, result);
		}
	}

    
    /**
	 * This method is used to Terminate the User
	 * 
	 * If we terminate any user, the Region roles and Mac address details associated to that userId will get Retired
	 * 
	 * Once we terminate user will not have any access to any thing
	 * 
	 * @param  workerId  User unique Identification (Active User's Id)
	 * @param  legacyUid  User shortname
	 * @param  bindingResult  using to store error related information and Database transaction status(user termination status)
	 * @param userObject, sessionID  used to track Exception related information
	 */
    //Terminating the user
	public void  terminateUserFromHCSAndDB(long workerId, String legacyUid, BindingResult bindingResult, UserObject userObject){
		String transactionMessage = null;
		boolean isError = false; 
		try{
			administrationTransactionHelper.terminateUserFromHCSAndDB(workerId, legacyUid, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Exception occured while terminating the user {}",transactionMessage);
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while terminating the user {}",exception.getMessage());
			transactionMessage = "Unable to terminate the user, please contact helpline.";
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
		
	}
	public void creatingNewUserInHcsAndNyehms(
			NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO,
			BindingResult bindingResult, UserObject userObject){
		String transactionMessage = null;
		boolean isError = false; 
		try{
			administrationTransactionHelper.creatingNewUserInHcsAndNyehms(nyehmsWorkerRegionsViewVO, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Exception occured while Creating new user {}",transactionMessage);
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while Creating new user {}",exception.getMessage());
			transactionMessage = "Unable to create new user, please contact helpline.";
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	}
	
	public List<Map<String, String>> getEmailNotificationList(String aCTION_NAME, BindingResult bindingResult, UserObject userObject){
	
		Map<String, String> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("ACTION_NAME", aCTION_NAME);
		
		List<Map<String, String>> emailNotificationList = searchManageProfileDAO.getEmailNotificationList(inputParameterMap);
		
		return emailNotificationList;
		
	}
	
	public List<String> getUsersEmailList(BindingResult bindingResult, UserObject userObject){
		return searchManageProfileDAO.getUsersEmailList();
	}
	
	public Map<String, String> getWorkerIdUsingEmail(String emailAddress){
		String workerId = searchManageProfileDAO.getWorkerIdUsingEmail(emailAddress);
		
		Map<String, String> userDetails = new HashMap<>();
		
		userDetails.put("workerId", workerId);
		
		return userDetails;
		
	}
	
	public void saveUpdatedNotifications(String aCTION_NAME, String tO_LIST, String cC_LIST, BindingResult bindingResult, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("ACTION_NAME", aCTION_NAME);
		
		
		
		
		searchManageProfileDAO.clearNotifications(inputParameterMap);
		
		if(tO_LIST != null && tO_LIST.length() > 0) {
			
			inputParameterMap.put("TO_LIST", tO_LIST.split(","));
			
			searchManageProfileDAO.saveToUpdatedNotifications(inputParameterMap);
		}
		if(cC_LIST != null && cC_LIST.length() > 0) {
			
			inputParameterMap.put("CC_LIST", cC_LIST.split(","));
			
			searchManageProfileDAO.saveCCUpdatedNotifications(inputParameterMap);
		}
		
		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the Notifications", bindingResult);
		
		
		
	}

	public List<String> getUserShortNameList(BindingResult bindingResult, UserObject userObject){
		return searchManageProfileDAO.getUserShortNameList();
	}


	public Map<String, String> getUserTypes(BindingResult result, UserObject userObject) {
		// TODO Auto-generated method stub 
		Map<String, String> userTypes = new LinkedHashMap<>();
		
		List<Map<String, String>> userTypesList = nyehmsWorkerRegionsViewDAO.getUserTypes();
		for(Map<String, String> userTypeMap : userTypesList){
			userTypes.put(userTypeMap.get("userTypeCode"), userTypeMap.get("userTypeDesc"));
		}
	return userTypes;
	}

	public Map<String, String> getUserFacilityNames(BindingResult result, UserObject userObject) {
		// TODO Auto-generated method stub 
		Map<String, String> userFacilityNames = new LinkedHashMap<>();
		
		List<Map<String, String>> userFacilityNamesList = nyehmsWorkerRegionsViewDAO.getUserFacilityNames();
		for(Map<String, String> userFacilityNameMap : userFacilityNamesList){
			userFacilityNames.put(userFacilityNameMap.get("userFacilityGroupCode"), userFacilityNameMap.get("userFacilityGroupDesc"));
		}
	return userFacilityNames;
	}
	
	public List<JSONObject> getUserCommentsList(String workerId, BindingResult bindingResult, UserObject userObject){
		return searchManageProfileDAO.getUserCommentsList(workerId);
	}

	public List<JSONObject> getSCReportData(String fromDate, String toDate, BindingResult dummyBindingResult, UserObject userObject) {
		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_from_date", fromDate);
		inputData.put("p_to_date", toDate);
		
		List<JSONObject> adminModuleReportData = nyehmsWorkerRegionsViewDAO.getAdminModuleReportData(inputData);
		
		return adminModuleReportData;
	}
	
	public List<JSONObject> getTrainingAttstData(BindingResult dummyBindingResult, UserObject userObject) {
		
		List<JSONObject> adminModuleReportData = nyehmsWorkerRegionsViewDAO.getTrainingAttstData();
		
		return adminModuleReportData;
	}
	
	public List<JSONObject> getTeleworkAttstData(BindingResult dummyBindingResult, UserObject userObject) {

		List<JSONObject> adminModuleReportData = nyehmsWorkerRegionsViewDAO.getTeleworkAttstData();
		
		return adminModuleReportData;
	}
	
	public List<JSONObject> getTeleCommuteReportData(String teleCommute, String fromDate, String toDate, BindingResult dummyBindingResult, UserObject userObject) {
		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_teleCommute", teleCommute);
		inputData.put("p_from_date", fromDate);
		inputData.put("p_to_date", toDate);
		
		List<JSONObject> adminModuleReportData = null;
		
		if("Yes".equals(teleCommute)) {
			adminModuleReportData = nyehmsWorkerRegionsViewDAO.getTeleCommuteReportData(inputData);
		}else {
			adminModuleReportData = nyehmsWorkerRegionsViewDAO.getNonTeleCommuteReportData(inputData);
		}
		
		return adminModuleReportData;
	}

	public NyehmsUsersVO getUserDetailsData(int workerId, UserObject userObject) {
		NyehmsUsersVO userDetailsData = nyehmsWorkerRegionsViewDAO.getUserDetailsData(workerId);
		return userDetailsData;
	}
	
	public List<JSONObject> getTrainerList(BindingResult bindingResult,
			UserObject userObject){
		
		return searchManageProfileDAO.getTrainerList();
	}
	
	public List<JSONObject> getBureauList(BindingResult bindingResult, UserObject userObject){
		
		return searchManageProfileDAO.getBureauList();
	}


	public List<JSONObject> getRegOfficeList(BindingResult bindingResult, UserObject userObject) {
		// TODO Auto-generated method stub
		return searchManageProfileDAO.getRegOfficeList();
	}
	
	
}
