package gov.ny.state.doh.bhae.nyehms.web.spring.controller.initialize;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserInitObject;

import java.io.IOException;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.service.safenet.SafeNetAuthResult;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.safenet.SafeNetAuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is controller for authenticating cryptocard
 *
 * @author rzp05
 */
@Slf4j
@Controller
public class CryptoController {

    private static final String SESSION_USER = "userInitObj";
    private static final String CRYPTO_VIEW = "initialize/CryptoCard";
    private static final String ALLOW_ACCESS_VIEW = "initialize/AllowApplicationAccess";

	@Autowired
	private AppProperties appProperties;

    @Autowired
    private SafeNetAuthService  safeNetAuthService;

    /**
     * Validates the crypto card PIN + passcode for the user in session and
     * returns the next page based on the outcome.
     */
    @RequestMapping("/cryptoCall")
    public String cryptoCall(HttpServletRequest request, Map<String, Object> map) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInitObject userInitObj = (UserInitObject) session.getAttribute(SESSION_USER);

        // Direct hit on the URL, or the session expired / app restarted: no user to authenticate
        if(userInitObj == null || userInitObj.getUserId() == null) {
            log.warn("cryptoCall without an initialized user in session (direct access or expired session)");
            map.put("TRANSACTION_MESSAGE", "Your session has expired. Please log in again through the Health Commerce System.");

            return CRYPTO_VIEW;
        }

        String userId = userInitObj.getUserId();

        SafeNetAuthResult result;

        if(appProperties.byPass()){
            log.warn("Crypto card check BYPASSED for user [{}] (byPass=true)", userId);
            result = SafeNetAuthResult.SUCCESS;
        }else{
            // PIN + passcode is digits only; do not uppercase or log it
            result = safeNetAuthService.authenticate(userId, request.getParameter("cryptocardnum"));

        }

        if(result.isSuccess()){
            userInitObj.setCryptoCardAuthenticationSuccess(true);
            session.setAttribute("userInitObj", userInitObj);
            //acknowledge
            map.put("ACKNOWLEDGEMENT_MESSAGE", appProperties.acknowledgementMessage());

            return ALLOW_ACCESS_VIEW;
        }

            userInitObj.setCryptoCardAuthenticationSuccess(false);

        // Only count real credential failures, not outages or blank input
        if (result == SafeNetAuthResult.FAILED) {
            userInitObj.setCryptoFailCt(userInitObj.getCryptoFailCt() + 1);
        }

        session.setAttribute(SESSION_USER, userInitObj);
        map.put("TRANSACTION_MESSAGE", result.getUserMessage());
        return CRYPTO_VIEW;
    }

}
