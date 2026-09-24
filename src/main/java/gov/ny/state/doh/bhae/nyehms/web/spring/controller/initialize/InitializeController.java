package gov.ny.state.doh.bhae.nyehms.web.spring.controller.initialize;

import java.io.IOException;
import java.security.Key;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.InitFailureReasonsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserInitObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserAuthorization;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.initilize.InitializeService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.InitializeUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

/**
 * This controller is called from applet to check user has valid permissions
 * (MAC Address, JRE version, initialize session
 *
 * @author vxp06
 */
@Controller
public class InitializeController {

	private static Logger log = LoggerFactory.getLogger(InitializeController.class);

    private String nyehmsDebugLog = AppConstants.nyehmLogDebugString.getName();
    
    @Autowired
	UserAuthorization userAuthorization;
    
    @Autowired
    InitializeService initializeService;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    DateUtil dateUtil;

    Map<String, String> targetMap = new HashMap<String, String>();
    BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

    InitializeUtil initializeUtil = new InitializeUtil();
    
    String browserName = "";

    /**
     * This method checks for userLocked, active MAC address list, user role, if
     * they are present in system then user is allowed to proceed otherwise they
     * are redirected to error page.
     *
     * @param request
     * @param response
     * @param map
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/appInitialize")
    public String appInitialize(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) throws ServletException, IOException {
        log.debug("appInitialize inside appInitialize method ");
        UserInitObject userInitObj = new UserInitObject();
        HttpSession session = null;
        List<InitFailureReasonsVO> initFailReasons = null;
        StringBuilder failReasons = new StringBuilder();
        StringBuilder failReasonDescription = new StringBuilder();

        browserName = request.getAttribute("browserName") != null  ? (String) request.getAttribute("browserName") : "";
        
        if (log.isDebugEnabled()) {
            log.debug(nyehmsDebugLog + " App Initialization is starting...........................");
        }
        session = request.getSession();
        Object linkClickTime = new Timestamp(new Date().getTime());
        userInitObj.setLinkClickTime(linkClickTime);

        log.debug(nyehmsDebugLog + " App Initialization Link Click Time. {}",linkClickTime);

        initFailReasons = initializeService.getInitFailReasons(bindingResult, userInitObj, request.getSession().getId());

        userInitObj.setInitFailReasons(initFailReasons);
        

        
        session.setAttribute("staticPath", "/hpn/ctrldocs/nyehms");
        session.setAttribute("contextPath", request.getContextPath());
        
        if (initializeUtil.readCookies(request, userInitObj)) {
        	
        	
        	UserObject userObject = userAuthorization.getUserObject(userInitObj.getUserId());
            
            if(userObject == null){
            	log.error("User object is null for {}",userInitObj.getUserId());
            	map.put("ErrorMessage", "Failed to find the user information. Please contact helpLine");
                return "initialize/InitializeError";
            }
            
            
            if(log.isDebugEnabled() && userObject != null){
            	log.debug("{} Printing the username from session", userObject.getUserId());
            }
            session.setAttribute("UserObj", userObject);

            userInitObj.setHasCookiesRead(true);
            log.debug("{} App Initialization cookies read successfully.", nyehmsDebugLog);

            String userLocked = initializeService.getUserLockInfo(bindingResult, userInitObj, request.getSession().getId());
            boolean hasUserLocked = initializeUtil.checkUserLocked(userLocked);
            userInitObj.setHasLocked(hasUserLocked);
            log.debug("{} User locked for App : {}",nyehmsDebugLog, userInitObj.isHasLocked());
            
            if (hasUserLocked) {
                log.debug("{}  User is locked, can not access the application.", nyehmsDebugLog);
                initializeUtil.initFailReasonConcat(failReasons, failReasonDescription, initFailReasons.get(3 - 1));
                userInitObj.setInitializeFailReasons(failReasons.toString());
                userInitObj.setInitializeFailReasonsDescrption(failReasonDescription.toString());
                session.setAttribute("userInitObj", userInitObj);
                insertUserNyehmsAccessAttempts(request, userInitObj);
                return "initialize/InitializeError";
            }

            // Check for user's active role into nyehms  DB  -- USER_ROLE_MAPPING table should have one entry for the user
            int activeRoleCount = initializeService.getActiveRoleCount(bindingResult, userInitObj, request.getSession().getId());
            boolean hasActiveRole = initializeUtil.checkUserActiveRole(activeRoleCount);
            userInitObj.setHasActiveRole(hasActiveRole);
            log.debug(nyehmsDebugLog + "User have Active Role for App : {}", userInitObj.isHasActiveRole());

            if (!hasActiveRole) {
                log.debug("{}  User do not have active role for App.", nyehmsDebugLog);
                initializeUtil.initFailReasonConcat(failReasons, failReasonDescription, initFailReasons.get(2 - 1));
                userInitObj.setInitializeFailReasons(failReasons.toString());
                userInitObj.setInitializeFailReasonsDescrption(failReasonDescription.toString());
                session.setAttribute("userInitObj", userInitObj);
                insertUserNyehmsAccessAttempts(request, userInitObj);
                return "initialize/InitializeError";
            }

			//Check the User Training Date
            session.setAttribute("userInitObj", userInitObj);
            
            
            map.put("userInitObj", userInitObj);
            // Start the applet and Read User's MAC Address, Operating System and Java Version, pass these variable to java code back

            //return "";

        } else {
            log.debug(nyehmsDebugLog + " HCS Cookie Reading Fails.");
            userInitObj.setHasCookiesRead(false);
            initializeUtil.initFailReasonConcat(failReasons, failReasonDescription, initFailReasons.get(12 - 1));
            userInitObj.setInitializeFailReasons(failReasons.toString());
            userInitObj.setInitializeFailReasonsDescrption(failReasonDescription.toString());
            session.setAttribute("userInitObj", userInitObj);
            insertUserNyehmsAccessAttempts(request, userInitObj);
            return "initialize/InitializeError";
        }
//    }
//
//    /**
//     * This method checks MAC address, JRE version and OS and if they are
//     * correct then user is allowed to log in otherwise user is redirected to
//     * error page.
//     *
//     * @param request
//     * @param response
//     * @return
//     * @throws ServletException
//     * @throws IOException
//     */
//    @RequestMapping("/appInitializeContinue")
//    public String appInitializeContinue(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) throws ServletException, IOException {



        log.debug(nyehmsDebugLog + " Applet Passed .....App Entry is contiune.................:.");


//                                // since at the time of getting back JREVersion from URL
//                    // its replacing + with a space
//                    // we also tried encodeURL and decodeURL but did not work.
//                    // this simple fix takes care of it

        // Error in reading the computer. Applet passed some variable as null or blank

//        userInitObj.setLoginMACAddress(userLoginMACAddress);
        userInitObj.setHasUserCompRead(true);
        log.debug(nyehmsDebugLog + "httpRequest.getContextPath() User's Computer Read : {}",userInitObj.isHasUserCompRead());

        // checking user's MAC address with Active DB MAC Address


        // Get Browser name
        String userBrowserNameVer = initializeUtil.getUserBrowserName(request);
        String[] temp = userBrowserNameVer.split("-");
        String userBrowserName = temp[0];
        userInitObj.setBrowserName(userBrowserName);
        log.debug(nyehmsDebugLog + "User Web browser Name is {}",userBrowserName);

        String userBrowserVer = temp[1];
        userInitObj.setBrowserVer(userBrowserVer);
        log.debug(nyehmsDebugLog + "User Web browser Ver is : {}", userBrowserVer);

        // Check for valid browser, if firefox check for XPI
        boolean hasValidBrowser = initializeUtil.CheckValidBrowser(request);
        userInitObj.setHasValidBrowser(hasValidBrowser);
        log.debug(nyehmsDebugLog + "User Web browser is valid :	{}",hasValidBrowser);

        // check for firefox XPI and version
        int userBrowserVerNum = 0;
        if (hasValidBrowser) {

            String userAgentStr = request.getHeader("user-agent");

            String[] token = userAgentStr.split("/");
            userBrowserVer = token[token.length - 1];
            
            // Check for XPI installation

            //userInitObj.setHasXPI(hasFFXPI);
            userInitObj.setBrowserVer(userBrowserVer);

            //List<FirefoxVersionVO> FirefoxVersions = initializeService.getValidFirefoxVer(bindingResult, userInitObj, request.getSession().getId());

            //isValidFirefoxVer = initializeUtil.checkValidityOfFirefox(FirefoxVersions, userInitObj);

            //isValidFirefoxVer
            
        }
        



        if (!hasValidBrowser) {
            initializeUtil.initFailReasonConcat(failReasons, failReasonDescription, initFailReasons.get(5 - 1));
            userInitObj.setInitializeFailReasons(failReasons.toString());
            userInitObj.setInitializeFailReasonsDescrption(failReasonDescription.toString());
        }
        //if (hasValidBrowser && !isValidFirefoxVer) {
        if (hasValidBrowser) {

            initializeUtil.initFailReasonConcat(failReasons, failReasonDescription, initFailReasons.get(6 - 1));

            String latestFireFoxVersionNumber = initializeService.getLatestFirefoxVersion(bindingResult, userInitObj, request.getSession().getId());
            if (latestFireFoxVersionNumber != null) {
                failReasonDescription.append(" ").append(latestFireFoxVersionNumber);
            }
            userInitObj.setInitializeFailReasons(failReasons.toString());
            userInitObj.setInitializeFailReasonsDescrption(failReasonDescription.toString());
        }
        
        

//        if ((!hasValidBrowser || !isValidFirefoxVer || !isAuthorizedOS || !isAuthorizedJRE || !hasFFXPI)) {
        if (!hasValidBrowser) {
        	session.setAttribute("userInitObj", userInitObj);
            insertUserNyehmsAccessAttempts(request, userInitObj);
            map.put("hasValidBrowser", hasValidBrowser);
            map.put("ErrorMessage", "This computer is not authorized to use NYEHMS.Please contact administrator.");
            return "initialize/InitializeError";
        }

        userInitObj.setInitializeFailReasons("");
        session.setAttribute("userInitObj", userInitObj);
        insertUserNyehmsAccessAttempts(request, userInitObj);
        //return "redirect:login";
        return "initialize/CryptoCard";
    }

    /**
     * This method will redirect the request to the error page.
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/appInitAppletErr")
    public String appInitAppletErr(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserInitObject userInitObj = null;
        if (session.getAttribute("userInitObj") != null) {
            userInitObj = (UserInitObject) session.getAttribute("userInitObj");
        } else {

            return "initialize/InitializeError";
        }

        List<InitFailureReasonsVO> initFailReasons = userInitObj.getInitFailReasons();
        String appletErrorCd = request.getParameter("errorNum") != null ? request.getParameter("errorNum").trim() : "";

        log.debug(nyehmsDebugLog + " Applet Failed .....appInitAppletErr .......:. {}", appletErrorCd);
        userInitObj.setAppletErrorCd(appletErrorCd);

        if (appletErrorCd.equals("1")) {
            userInitObj.setInitializeFailReasons(initFailReasons.get(13 - 1).getShort_desc());
        } else if (appletErrorCd.equals("2")) {
            userInitObj.setInitializeFailReasons(initFailReasons.get(11 - 1).getShort_desc());
        } else if (appletErrorCd.equals("3")) {
            userInitObj.setInitializeFailReasons(initFailReasons.get(14 - 1).getShort_desc());
        } else if (appletErrorCd.equals("4")) {
            userInitObj.setInitializeFailReasons(initFailReasons.get(15 - 1).getShort_desc());
        }

        session.setAttribute("userInitObj", userInitObj);
        insertUserNyehmsAccessAttempts(request, userInitObj);
        return "initialize/InitializeError";
    }

    /**
     * This method keeps track of the number of access attempts to the nyehms
     * application.
     *
     * @param request
     * @param userInitObj
     */
    public void insertUserNyehmsAccessAttempts(HttpServletRequest request, UserInitObject userInitObj) {

        log.debug(nyehmsDebugLog + "User initialization attempt is going to be insert into DB.");
        initializeService.insertUserAccessAttemp(bindingResult, userInitObj, request.getSession().getId());
        log.debug(nyehmsDebugLog + "User initialization attempt is inserted into DB successfully.");

        if (!userInitObj.getInitializeFailReasons().equals("")) {

            String emailMessage = "Initialization error occured for user :" + userInitObj.getUserId()
                    + "  for session ID :" + request.getSession().getId() + "  at " + dateUtil.getSystemDateTime()
                    + "  Error Description is : " + userInitObj.getInitializeFailReasonsDescrption();

            //Send the email
            sendEmail.sendDefaultEMail(emailMessage);
        }
    }

    /**
     * This method decrypts the encrypted parameters ( MAC address, JRE version,
     * OS name) at applet. We are using AES algorithm and key specified inside
     * the method is same in applet.
     *
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptedData) throws Exception {
        String ALGO = "AES";
        byte[] keyValue
                = new byte[]{'T', 'G', 'E', 'B', 'D', 'W', 't', 'S', 'e', 'F', 'r', 'e', 'X', 'K', 'R', 'y'};
        Key key = new SecretKeySpec(keyValue, ALGO);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = DatatypeConverter.parseBase64Binary(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);

        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
}