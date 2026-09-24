package gov.ny.state.doh.bhae.nyehms.web.spring.util;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.FirefoxVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.InitFailureReasonsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.JREVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserInitObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InitializeUtil {
	
	private static Logger log = LoggerFactory.getLogger(InitializeUtil.class);
	private String nyehmsDebugLog = AppConstants.nyehmLogDebugString.getName();
	
	HttpSession session = null;
	
	public boolean readCookies(HttpServletRequest request, UserInitObject userInitObj) {
		
		String userId = "";
		String hinRole = "";
		String userEnvStage = null;
		boolean userPermission = false;
		String userPerm = "";
		String serverName = request.getServerName();
		Cookie[] cookies = request.getCookies();
		Cookie thisCookie = null;
		String token;
		session = request.getSession();
		
		log.debug("Start reading the cookies of the environment : {}", serverName);
		
		if (serverName.equals("aids170043") || serverName.equals("10.50.77.143") || serverName.equals("127.0.0.1") || serverName.equals("localhost") ) {
			userId = "SKA03";
			userEnvStage = "localhost";
			userPermission = true;
		} 
		else {
			for(int i=0; i <cookies.length; i++) {
				
				thisCookie = cookies[i];
				if (thisCookie.getName().equalsIgnoreCase("PermCookie")) {
					
					StringTokenizer st = new StringTokenizer(thisCookie.getValue(), "|");
					
					while(st.hasMoreTokens()) {
						token = st.nextToken();
						if (token.startsWith("HINUSER")) {
							int start = token.indexOf(":");
							userId = token.substring(start+1, token.length());
						}
						if (token.startsWith("STAGE")) {
							int start = token.indexOf(":");
							userEnvStage = token.substring(start+1, token.length());
						}
						if (token.startsWith("HINPERM1")) {
							int start = token.indexOf(":");
							
							hinRole = token.substring(start+1, token.length());
							if (hinRole.endsWith("ehmsuser")) {
								userPermission = true;
								userPerm = "ehmsuser";
							}
						}
					}
					
				}
				
				// code for WaarpCookie
				String fullname = null;
				if (thisCookie.getName().equalsIgnoreCase("WaarpCookie")) {
					String WaarpCookie = thisCookie.getValue();
					WaarpCookie = WaarpCookie.replaceAll("%7C", "|");
					WaarpCookie = WaarpCookie.replaceAll("%3A", ":");
					WaarpCookie = WaarpCookie.replaceAll("%20", " ");
					WaarpCookie = WaarpCookie.replaceAll("%40", "@");
					
					StringTokenizer st = new StringTokenizer(WaarpCookie, "|");
					int start;
					while (st.hasMoreTokens()) {                 
						token=st.nextToken();
						
						if(token.startsWith("FULLNAME")){
							start = token.indexOf(":");
							fullname = token.substring(start+1, token.length());
							String[] names = fullname.split(" ");
							
							if(names.length == 3){
								userInitObj.setUserFirstName(names[0]);
								userInitObj.setUserMiddleName(names[1]);
								userInitObj.setUserLastName(names[2]);
							}else{
								userInitObj.setUserFirstName(names[0]);
								userInitObj.setUserMiddleName("");
								userInitObj.setUserLastName(names[1]);
							}
							
						}
					}
				} 
			} // end for loop
		}
		
		if (!userPermission) {
			log.debug("User Do not have permission for NYEHMS application on : {}", userEnvStage);
		}
		
		// set the user object 
		if (! (userId == null || userId.equals(""))) {
			userInitObj.setUserId(userId.toUpperCase());
			userInitObj.setUserEnv(userEnvStage);
			userInitObj.setHasHinPerm(userPermission);
			userInitObj.setUserPerm(userPerm);
		} else {
			return false;
		}
		
		session.setAttribute("userInitObj", userInitObj);
		return true;
		
	}
	
	public boolean checkUserActiveMAC(List<String> activeMACAddressesList, UserInitObject userInitObj, HttpServletRequest request) {
		
		session = request.getSession();
		boolean hasActiveMACAddress = false;
		String macAddresses = "";
		
		if (activeMACAddressesList.size() > 0) {
			hasActiveMACAddress = true;
			
			for (int i=0; i<activeMACAddressesList.size(); i++) {
				if (i==0) {
					macAddresses = activeMACAddressesList.get(0);
				} else {
					macAddresses += "/" + activeMACAddressesList.get(i);
				}
			}
		}
		userInitObj.setActiveMACAddresses(macAddresses);
		session.setAttribute("userInitObj", userInitObj);
		
		log.debug(nyehmsDebugLog + " User active MAC Addresses : " + userInitObj.getActiveMACAddresses());
		return hasActiveMACAddress;
	}
	
	public boolean checkUserLocked(String userLocked) {
		
		boolean isUserLocked = false;
		if (userLocked != null && userLocked.equalsIgnoreCase("Y")) {
			isUserLocked = true;
		}
		return isUserLocked;
	}
	
	public boolean checkUserActiveRole(int activeRoleCount) {
		
		boolean isActiveRole = false;
		if (activeRoleCount > 0) {
			isActiveRole = true;
		}
		
		return isActiveRole;
	}
	
	public boolean checkValidityOfMacAddress(UserInitObject userInitObj) {
		
		boolean isValidMAC = false;
		
		if (!userInitObj.getLoginMACAddress().equals("") && userInitObj.getLoginMACAddress() != null ) {
			String userLoginMacAddress = userInitObj.getLoginMACAddress();
			log.debug(nyehmsDebugLog + " userLoginMacAddress  " + userLoginMacAddress);
			if (userLoginMacAddress.length() == 17) {
				// Get User MAC Address from NYEHMS DB and match with user's current MAC address. If both MAC address match user is accessing the application 
				// from authorized computer, otherwise user is not accessing the application from authorized computer
				String userActiveMACAddresses = userInitObj.getActiveMACAddresses();
				log.debug(nyehmsDebugLog + " userActiveMACAddresses  " + userActiveMACAddresses);
				if (userActiveMACAddresses.length() > 17) {
					StringTokenizer st = new StringTokenizer(userActiveMACAddresses, "/");
						String token;
						while(st.hasMoreTokens()) {
							token = st.nextToken();
							if (token.equalsIgnoreCase(userLoginMacAddress)) {
								isValidMAC = true;
							}
						}
				} else if (userActiveMACAddresses.length() == 17 && userActiveMACAddresses.equalsIgnoreCase(userLoginMacAddress)) {
					isValidMAC = true;
				}
			}
		}
		
		return isValidMAC;
	}
	
	public boolean CheckValidBrowser(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");

		if (userAgent.indexOf("Firefox") > -1 || userAgent.indexOf("NYEHMSFIREFOX") > -1) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getUserBrowserName(HttpServletRequest request) {
		
		log.debug(nyehmsDebugLog + " Users browser userAgent is : " + request.getHeader("User-Agent"));
        String browsername = "";
        String browserversion = "";
        String userAgent = request.getHeader("User-Agent");
        
        if(userAgent.contains("NYEHMSFIREFOX")){

            String subsString = userAgent.substring( userAgent.indexOf("NYEHMSFIREFOX"));
            String Info[] = (subsString.split(" ")[0]).split("/");
            browsername = Info[0];
            browserversion = Info[1];
       }
       else if(userAgent.contains("MSIE")){
            String subsString = userAgent.substring( userAgent.indexOf("MSIE"));
            String Info[] = (subsString.split(";")[0]).split(" ");
            browsername = Info[0];
            browserversion = Info[1];
        }
       else if(userAgent.contains("Firefox")){

            String subsString = userAgent.substring( userAgent.indexOf("Firefox"));
            String Info[] = (subsString.split(" ")[0]).split("/");
            browsername = Info[0];
            browserversion = Info[1];
       }
       else if(userAgent.contains("Chrome")){

            String subsString = userAgent.substring( userAgent.indexOf("Chrome"));
            String Info[] = (subsString.split(" ")[0]).split("/");
            browsername = Info[0];
            browserversion = Info[1];
       }
       else if(userAgent.contains("Opera")){

            String subsString = userAgent.substring( userAgent.indexOf("Opera"));
            String Info[] = (subsString.split(" ")[0]).split("/");
            browsername = Info[0];
            browserversion = Info[1];
       }
       else if(userAgent.contains("Safari")){

            String subsString = userAgent.substring( userAgent.indexOf("Safari"));
            String Info[] = (subsString.split(" ")[0]).split("/");
            browsername = Info[0];
            browserversion = Info[1];
       } 
       else {
    	   
           	browsername = "Unknown";
           	browserversion = "Unknown";

       }
  
        return browsername + "-" + browserversion;

	}
	
	public boolean checkValidityOfOS(List<String> OSNames,  UserInitObject userInitObj) {
		
		boolean isValidOS = false;
		String userOSName = userInitObj.getOS();
		
		for (int i=0; i<OSNames.size(); i++) {
			if (userOSName.equalsIgnoreCase(OSNames.get(i))) {
				isValidOS = true;
				break;
			}
		}
		
		return isValidOS;
	}

	public boolean checkValidityOfJRE(List<JREVersionVO> JREVersions, UserInitObject userInitObj) {
		
		boolean isValidJRE = false;
		String userJRE = userInitObj.getJREVersion();
		String dbJREVerNo = "";
		String dbJRERejectFlag="";
		
		for (int i=0; i<JREVersions.size(); i++) {
			
			dbJREVerNo = JREVersions.get(i).getJREVersionNo() != null ? JREVersions.get(i).getJREVersionNo().trim() : "";
			dbJRERejectFlag = JREVersions.get(i).getRejectFlag() != null ? JREVersions.get(i).getRejectFlag().trim() : "";
			
			if (userJRE.equalsIgnoreCase(dbJREVerNo) && dbJRERejectFlag.equals("Y")) {
				isValidJRE = false;
				break;
			}
			if (userJRE.startsWith(dbJREVerNo) && !dbJRERejectFlag.equals("Y")) {
				isValidJRE = true;
			}
		}
		
		return isValidJRE;
	}
	
	public boolean checkValidityOfFirefox(List<FirefoxVersionVO> firefoxVersions, UserInitObject userInitObj) {
		
		boolean isValidFirefox = false;
		String userFirefoxVer = userInitObj.getBrowserVer();
		String dbFirefoxVer = "";
		String dbFirefoxRejectFlag="";
		
		for (int i=0; i<firefoxVersions.size(); i++) {
			
			dbFirefoxVer = firefoxVersions.get(i).getFirefoxVersionNo() != null ? firefoxVersions.get(i).getFirefoxVersionNo().trim() : "";
			dbFirefoxRejectFlag = firefoxVersions.get(i).getRejectFlag() != null ? firefoxVersions.get(i).getRejectFlag().trim() : "";
			
			if (userFirefoxVer.equalsIgnoreCase(dbFirefoxVer) && dbFirefoxRejectFlag.equals("Y")) {
				isValidFirefox = false;
				break;
			}else if(userFirefoxVer.startsWith(dbFirefoxVer) && !dbFirefoxRejectFlag.equals("Y")) {
				isValidFirefox = true;
			}
		}
		
		return isValidFirefox;
	}
	
	public String booleanToChar(boolean boolValue) {
		
		if (boolValue) {
			return "Y";
		} else {
			return "";
		}
	}
	
	public void initFailReasonConcat(StringBuilder initFailReasonStr, StringBuilder initFailReasonDescription, InitFailureReasonsVO initFailureReasonsVO) {
		
		if (initFailReasonStr.length() > 0 ) {
			initFailReasonStr = initFailReasonStr.append(","); 
			initFailReasonDescription = initFailReasonDescription.append(",");		
			
		}
		initFailReasonStr = initFailReasonStr.append(initFailureReasonsVO.getShort_desc());
		initFailReasonDescription = initFailReasonDescription.append(initFailureReasonsVO.getDescription()); 
		
	}

}
