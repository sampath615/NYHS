package gov.ny.state.doh.bhae.nyehms.web.spring.service.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.PortalSystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessageArchive;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
/*
 * Service interface to handle the system messages
 */
public interface SystemMessageService {
	/*
	 * Get the list of system messages
	 * @param bindingResult the spring BindingResult reference
	 * @param userObject the UserObject reference
	 * @return List of SystemMessage instances
	 */
	public List<SystemMessage> getSystemMessages(String statusFlag, BindingResult bindingResult,UserObject userObject);
	/*
	 * Save the system message
	 * @param systemMessage system message details
	 * @param bindingResult the spring BindingResult reference
	 * @param userObject the UserObject reference
	 */
	 public void  saveSystemMessageDetails(SystemMessage systemMessage, BindingResult bindingResult, UserObject userObject);
	 /*
	  * Update the system message
      * @param systemMessage system message details
	  * @param bindingResult the spring BindingResult reference
	  * @param userObject the UserObject reference
	  */
	 public void  updateSystemMessageDetails(SystemMessage systemMessage, BindingResult bindingResult, UserObject userObject);
	 /*
	  * Get the system message archives (message histories)
	  * @param bindingResult the spring BindingResult reference
	  * @param userObject the UserObject reference
	  * @return List of SystemMessageArchive instances
	  */
	 public List<SystemMessageArchive> getSystemMessageArchives(SystemMessage systemMessage, BindingResult bindingResult, UserObject userObject);
	 
	 public void getInProgressActiveSystemMessages(HttpServletRequest request, BindingResult bindingResult);
	 
	 public List<PortalSystemMessage> getPortalSystemMessages(String statusFlag, BindingResult bindingResult,UserObject userObject);
	 
	 public List<JSONObject> getPortalModuleNames();
	  
	 public void  savePortalSystemMessageDetails(PortalSystemMessage systemMessage, BindingResult bindingResult, UserObject userObject);
	 
	 public void  updatePortalSystemMessageDetails(PortalSystemMessage systemMessage, BindingResult bindingResult, UserObject userObject);
}
