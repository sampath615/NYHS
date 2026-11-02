package gov.ny.state.doh.bhae.nyehms.web.spring.controller.dashboard;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.dashboard.GraphDataService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This is the controller for the Graph Data
 *
 * @author vxp06
 */
@RequiredArgsConstructor
@Controller
public class GraphDataController {

	
	
	
	
	
	
	
    private final GraphDataService graphDataService;
    
    
    
    @RequestMapping(value = "/getWorkerTallyCounts", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody List<JSONObject> getWorkerTallyCounts(HttpServletRequest request, UserObject userObject) {
    	
    	
    	
    	
    	
    	
    	return graphDataService.getWorkerTallyCounts(userObject);
    	
    	
    	
    }

}
