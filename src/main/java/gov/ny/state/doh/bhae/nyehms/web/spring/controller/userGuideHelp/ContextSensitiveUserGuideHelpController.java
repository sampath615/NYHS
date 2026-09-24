package gov.ny.state.doh.bhae.nyehms.web.spring.controller.userGuideHelp;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * This controller is the single point entry for all context sensitive help manual
 * requests from the tracking application. 
 * It defines handler method which will respond with the resource references based on the 
 * context sensitive help parameter in the request
 * The resource references are JSP template files and the resources should be preferably
 * an HTML page or another JSP page embedded in the JSP template.
 * However, Any servlet response compatible content type can be embedded as a resource.      
 */
@Controller
public class ContextSensitiveUserGuideHelpController {
	/*
	 * This method returns the context sensitive help page as a JSP resource page.
	 * The incoming json request post the context sensitive resource key as a request parameter
	 * which is used by this method to process the help resource as a response.
	 * The returned resource may contain help contents for the user interface elements group
	 * and the deep linking pertaining to the exact user interface element is performed
	 * at the client side script
	 * @param helpPageReference the context specific reference key to process the help resource
	 * @return returns the JSP page with the help contents inside.    
	 */
	@RequestMapping(value="/help/getHelpPage", method = RequestMethod.POST)
	public String getHelpPage(@RequestParam String helpPageReference){
		return helpPageReference;
	}
	
	/**
	 * Handler method for moving provider data to facility. Accepts json request 
	 * via HTTP POST method
	 * @param request HTTP request reference
	 * @return result after the transaction   
	 */
	@RequestMapping("/getUserGuide")
	public String getUserGuide(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return "userguide/UserGuide";
		
	}

	/**
	 * Handler method for moving provider data to facility. Accepts json request 
	 * via HTTP POST method
	 * @param request HTTP request reference
	 * @return result after the transaction   
	 */

	@RequestMapping("/getWebUserGuide")
	public String getwebUserGuide(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return "userguide/webUserGuideContent";
		
	}
	
	@RequestMapping("/getWebUserGuideFields")
	public String getWebUserGuideFields(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return "userguide/webUserGuideFieldContent";
		
	}
}
