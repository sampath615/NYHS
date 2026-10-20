package gov.ny.state.doh.bhae.nyehms.web.spring.controller.initialize;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.ActiveStage;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserInitObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is controller for authenticating cryptocard
 *
 * @author vxp06
 */
@Controller
public class CryptoController {

	@Autowired
	private ActiveStage activeStage;

	@Autowired
	private AppProperties appProperties;

	private static Logger log = LoggerFactory.getLogger(CryptoController.class);

    @Autowired
    private CommonService commonService;

    /**
     * This method will validate crypto card number and user id and return
     * corresponding page based on validation outcome.
     *
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/cryptoCall")
    public String cryptoCall(HttpServletRequest request, Map<String, Object> map) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInitObject userInitObj = (UserInitObject) session.getAttribute("userInitObj");
        boolean cryptoAuth = false;

        // get the crypto card number from the request
        String cryptoNumber = request.getParameter("cryptocardnum").toUpperCase();

       // if the cryptocard number is empty then return it to Cryptocard page.
        if (cryptoNumber.isEmpty() || cryptoNumber.equals("")) {
             map.put("TRANSACTION_MESSAGE", "Incorrect cryptocard number");
            return "initialize/CryptoCard";
        }
        
        // send the parameters to the service for validation
        cryptoAuth = commonService.authenticateUser(request, userInitObj.getUserId(), cryptoNumber);
        
        boolean onDev = "DEV".equals(activeStage.name());
        if (cryptoAuth || onDev) {
        	userInitObj.setCryptoCardAuthenticationSuccess(true);
        	session.setAttribute("userInitObj", userInitObj);
            log.debug("Authenticated successfully : {}", userInitObj.getUserId());

            //return "forward:/web/login?user=" + userInitObj.getUserId();
            
            map.put("ACKNOWLEDGEMENT_MESSAGE", appProperties.acknowledgementMessage());
            
            return "initialize/AllowApplicationAccess";
            
        } else {
        	userInitObj.setCryptoCardAuthenticationSuccess(false);
            int cryptoFailCt = userInitObj.getCryptoFailCt();
            log.debug("Crypto card authentication failed: {}", cryptoFailCt);
            cryptoFailCt++;
            session.setAttribute("userInitObj", userInitObj);
            map.put("TRANSACTION_MESSAGE", "Incorrect cryptocard number");
            return "initialize/CryptoCard";
        }
    }

}
