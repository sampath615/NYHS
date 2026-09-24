package gov.ny.state.doh.bhae.nyehms.web.spring.controller.dashboard;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.dashboard.GraphDataService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This is the controller for the Graph Data
 *
 * @author vxp06
 */
@Controller
public class GraphDataController {

	
	
	
	
	
	
	
    @Autowired
    private GraphDataService graphDataService;
    
    
    
    @RequestMapping(value = "/getWorkerTallyCounts", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody List<JSONObject> getWorkerTallyCounts(HttpServletRequest request) {
    	
    	
    	
    	
    	HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
    	
    	
    	return graphDataService.getWorkerTallyCounts(userObject);
    	
    	
    	
    }

}
