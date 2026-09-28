package gov.ny.state.doh.bhae.nyehms.web.spring.service.initilize;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.initialize.InitializeDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.FirefoxVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.InitFailureReasonsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.JREVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserAccessAttemptVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserInitObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.InitializeUtil;

@Service
public class InitializeService {
	
	private static Logger log = LoggerFactory.getLogger(InitializeService.class);

	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	InitializeUtil initializeUtil = new InitializeUtil();
	
	@Autowired
	InitializeDAO initializeDAO;
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;	

	public List<String> getActiveMACAddressesList(BindingResult result, UserInitObject userInitObject, String sessionID){
	
		boolean errorFlag=false;
		List<String> activeMACAddresses  = new ArrayList<>();
		try {
			activeMACAddresses = initializeDAO.getActiveMACAddressesList(userInitObject.getUserId());	
		} catch(DataAccessException dae){
			errorFlag = true;
			log.error(nyehmsLog+" DataAccessException Occured : "+dae.toString());	
			commonExceptionHandler.handleException(dae, userInitObject, sessionID);
		} catch(Exception ex){
			errorFlag = true;
			log.error(nyehmsLog+" General Exception Occured : "+ex.toString());	
			commonExceptionHandler.handleException(ex, userInitObject, sessionID);
		} finally {	
			if(errorFlag){					
				result.addError(new ObjectError("Error", "Some Exception Occured. Please try again "));
				log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");
			}
		}
		return activeMACAddresses;
		
	}

	public String getUserLockInfo(BindingResult result, UserInitObject userInitObject, String sessionID) {

		boolean errorFlag=false;
		String userLocked = "N";
		try {
			userLocked = initializeDAO.getUserLockInfo(userInitObject.getUserId());	
		} catch(DataAccessException dae){
			errorFlag = true;
			log.error(nyehmsLog+" DataAccessException Occured : "+dae.toString());
			commonExceptionHandler.handleException(dae, userInitObject, sessionID);
		} catch(Exception ex){
			errorFlag = true;
			log.error(nyehmsLog+" General Exception Occured : "+ex.toString());	
			commonExceptionHandler.handleException(ex, userInitObject, sessionID);
		} finally {	
			if(errorFlag){					
				result.addError(new ObjectError("Error", "Some Exception Occured. Please try again "));
				log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");
			}
		}
		
		return userLocked;
	}

	public int getActiveRoleCount(BindingResult result, UserInitObject userInitObject, String sessionID) {
		
		boolean errorFlag=false;
		int roleCount = 0;
		try {
			roleCount = initializeDAO.getActiveRoleCount(userInitObject.getUserId());	
		} catch(DataAccessException dae){
			errorFlag = true;
			log.error(nyehmsLog+" DataAccessException Occured : "+dae.toString());
			commonExceptionHandler.handleException(dae, userInitObject, sessionID);
		} catch(Exception ex){
			errorFlag = true;
			log.error(nyehmsLog+" General Exception Occured : "+ex.toString());	
			commonExceptionHandler.handleException(ex, userInitObject, sessionID);
		} finally {	
			if(errorFlag){					
				result.addError(new ObjectError("Error", "Some Exception Occured. Please try again "));
				log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");
			}
		}
		
		return roleCount;
	}

	public List<JREVersionVO> getActiveJREs(BindingResult result, UserInitObject userInitObject, String sessionID) {
		boolean errorFlag=false;
		List<JREVersionVO> activeJREs  = null;
		try {
			activeJREs = initializeDAO.getActiveJREs();	
		} catch(DataAccessException dae){
			errorFlag = true;
			log.error(nyehmsLog+" DataAccessException Occured : "+dae.toString());	
			commonExceptionHandler.handleException(dae, userInitObject, sessionID);
		} catch(Exception ex){
			errorFlag = true;
			log.error(nyehmsLog+" General Exception Occured : "+ex.toString());	
			commonExceptionHandler.handleException(ex, userInitObject, sessionID);
		} finally {	
			if(errorFlag){					
				result.addError(new ObjectError("Error", "Some Exception Occured. Please try again "));
				log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");
			}
		}
		return activeJREs;
	}

	public List<String> getActiveOSs(BindingResult result, UserInitObject userInitObject, String sessionID) {

		boolean errorFlag=false;
		List<String> activeOSs  = new ArrayList<>();
		try {
			activeOSs = initializeDAO.getActiveOSs();	
		} catch(DataAccessException dae){
			errorFlag = true;
			log.error(nyehmsLog+" DataAccessException Occured : "+dae.toString());	
			commonExceptionHandler.handleException(dae, userInitObject, sessionID);
		} catch(Exception ex){
			errorFlag = true;
			log.error(nyehmsLog+" General Exception Occured : "+ex.toString());	
			commonExceptionHandler.handleException(ex, userInitObject, sessionID);
		} finally {	
			if(errorFlag){					
				result.addError(new ObjectError("Error", "Some Exception Occured. Please try again "));
				log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");
			}
		}
		return activeOSs;
		
	}
	
	public String getLatestFirefoxVersion(BindingResult result, UserInitObject userInitObject, String sessionID) {

		boolean errorFlag=false;
		String latestFirefoxVersionNumber  = null;
		try {
			latestFirefoxVersionNumber = initializeDAO.getLatestFirefoxVersion();	
		} catch(DataAccessException dae){
			errorFlag = true;
			log.error(nyehmsLog+" DataAccessException Occured : "+dae.toString());
			commonExceptionHandler.handleException(dae, userInitObject, sessionID);
		} catch(Exception ex){
			errorFlag = true;
			log.error(nyehmsLog+" General Exception Occured : "+ex.toString());	
			commonExceptionHandler.handleException(ex, userInitObject, sessionID);
		} finally {	
			if(errorFlag){					
				result.addError(new ObjectError("Error", "Some Exception Occured. Please try again "));
				log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");
			}
		}
		
		return latestFirefoxVersionNumber;
	}
	
	public String getLatestJavaVersionNumber(BindingResult result, UserInitObject userInitObject, String sessionID) {

		boolean errorFlag=false;
		String latestJavaVersionNumber  = null;
		try {
			latestJavaVersionNumber = initializeDAO.getLatestJavaVersionNumber();	
		} catch(DataAccessException dae){
			errorFlag = true;
			log.error(nyehmsLog+" DataAccessException Occured : "+dae.toString());
			commonExceptionHandler.handleException(dae, userInitObject, sessionID);
		} catch(Exception ex){
			errorFlag = true;
			log.error(nyehmsLog+" General Exception Occured : "+ex.toString());	
			commonExceptionHandler.handleException(ex, userInitObject, sessionID);
		} finally {	
			if(errorFlag){					
				result.addError(new ObjectError("Error", "Some Exception Occured. Please try again "));
				log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");
			}
		}
		
		return latestJavaVersionNumber;
	}
	
	
	
	
	public List<FirefoxVersionVO> getValidFirefoxVer(BindingResult result, UserInitObject userInitObject, String sessionID) {

		boolean errorFlag=false;
		List<FirefoxVersionVO> validFirefoxVer  = null;
		try {
			validFirefoxVer = initializeDAO.getValidFirefoxVer();	
		} catch(DataAccessException dae){
			errorFlag = true;
			log.error(nyehmsLog+" DataAccessException Occured : "+dae.toString());
			commonExceptionHandler.handleException(dae, userInitObject, sessionID);
		} catch(Exception ex){
			errorFlag = true;
			log.error(nyehmsLog+" General Exception Occured : "+ex.toString());	
			commonExceptionHandler.handleException(ex, userInitObject, sessionID);
		} finally {	
			if(errorFlag){					
				result.addError(new ObjectError("Error", "Some Exception Occured. Please try again "));
				log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");
			}
		}
		
		return validFirefoxVer;
	}

	public void insertUserAccessAttemp(BindingResult result, UserInitObject userInitObject, String sessionID) {
		
		try{	
			UserAccessAttemptVO userAccessAttemptVO = new UserAccessAttemptVO();
			
			userAccessAttemptVO.setUserId(userInitObject.getUserId());
			userAccessAttemptVO.setHcsRole(userInitObject.getUserPerm());
			userAccessAttemptVO.setLinkClickTime(userInitObject.getLinkClickTime());
			userAccessAttemptVO.setActiveMACAddresses(userInitObject.getActiveMACAddresses());
			userAccessAttemptVO.setActiveNyehmsRole(initializeUtil.booleanToChar(userInitObject.isHasActiveRole()));
			userAccessAttemptVO.setUserLocked(initializeUtil.booleanToChar(userInitObject.isHasLocked()));
			userAccessAttemptVO.setAttempMACAddress(userInitObject.getLoginMACAddress());
			userAccessAttemptVO.setBrowserName(userInitObject.getBrowserName());
			userAccessAttemptVO.setBrowserVersion(userInitObject.getBrowserVer());
			userAccessAttemptVO.setXPIPlugin(initializeUtil.booleanToChar(userInitObject.isHasXPI()));
			userAccessAttemptVO.setXPIVersion(userInitObject.getXPIVer());
			userAccessAttemptVO.setOSName(userInitObject.getOS());
			userAccessAttemptVO.setJREVersion(userInitObject.getJREVersion());
			userAccessAttemptVO.setRecordInsertTime(new Timestamp(new Date().getTime()));
			userAccessAttemptVO.setAttemptFailedReasons(userInitObject.getInitializeFailReasons());
			
			initializeDAO.insertUserAccessAttemp(userAccessAttemptVO);
		}catch (DataAccessException dae) {
			log.error(nyehmsLog+" SQLException:: "+dae.toString());	
			commonExceptionHandler.handleException(dae, userInitObject,sessionID);				
		} catch (Exception ex) {			
			log.error(nyehmsLog+" General Exception:: "+ex.toString());			
			commonExceptionHandler.handleException(ex, userInitObject,sessionID);
		}/*finally{						
			result.addError(new ObjectError("Error", "Some error Occured. Please try again."));
			log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");			
		}*/
	
		
	}

	public List<InitFailureReasonsVO> getInitFailReasons(BindingResult result, UserInitObject userInitObject, String sessionID) {

		boolean errorFlag=false;
		List<InitFailureReasonsVO> initFailureReasons  = null;
		try {
			initFailureReasons = initializeDAO.getInitFailReasons();	
		} catch(DataAccessException dae){
			errorFlag = true;
			log.error(nyehmsLog+" DataAccessException Occured : "+dae.toString());
			commonExceptionHandler.handleException(dae, userInitObject, sessionID);
		} catch(Exception ex){
			errorFlag = true;
			log.error(nyehmsLog+" General Exception Occured : "+ex.toString());	
			commonExceptionHandler.handleException(ex, userInitObject, sessionID);
		} finally {	
			if(errorFlag){					
				result.addError(new ObjectError("Error", "Some Exception Occured. Please try again "));
				log.error(nyehmsLog+" Exception thrown and added to Binding Result Object---- ");
			}
		}
		
		return initFailureReasons;
	}

}
