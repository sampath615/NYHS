package gov.ny.state.doh.bhae.nyehms.web.spring.service.dashboard;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.json.simple.JSONObject;

/**
 * This interface provides method for showing graphs on dashboard on homepage
 * 
 * @author vxp06
 */
public interface GraphDataService {
    
    
	public List<JSONObject> getWorkerTallyCounts(UserObject userObject);
    
}
