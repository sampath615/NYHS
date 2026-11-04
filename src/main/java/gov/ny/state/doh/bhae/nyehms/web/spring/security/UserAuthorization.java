package gov.ny.state.doh.bhae.nyehms.web.spring.security;

import java.util.HashMap;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;


@Component
public class UserAuthorization {
	
	@Autowired
	CommonDAO commonDAO;
	
	@Autowired
	CommonExceptionHandler commonExceptionHandler;
	
	private static Logger log = LoggerFactory.getLogger(UserAuthorization.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
		
	public boolean isPermitted(UserObject userObject, String module, int permissionId, String sessionId){
		boolean permitted=false;
		//Make a DAO Call to see if asked permission is available?
		Map map = new HashMap();		
		map.put("workeId", userObject.getWorkerId());
		map.put("moduleName", module);
		map.put("permissionId", permissionId);
		boolean errorFlag=false;
		int result=0;
		try{
			result=commonDAO.getPermission(map);		
		}catch (DataAccessException e) {			
			log.error(nyehmsLog+" {}",e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionId);			
		} catch (Exception e) {			
			log.error(nyehmsLog+" {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionId);			
		}
		
		if(result>0) permitted=true;
		
		log.error(" User : {} permission for : {} on module : {} is : {}",userObject.getUserName(),permissionId,module, permitted);
		
		
		return permitted;
	}
	
	
	public UserObject getUserObject(String userId){
		UserObject userObject=null;
		log.debug(nyehmsLog+" --------------- Starting to get User Object from DB... ");			
		try{
			userObject= commonDAO.getUserObject(userId);
		}catch (DataAccessException e) {			
			log.error(nyehmsLog+" {}",e.getMessage());				
		} catch (Exception e) {			
			log.error(nyehmsLog+" {}", e.getMessage());						
		}
	return userObject;
	}
	
	
	

}
