package gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.PortalSystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessageArchive;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
/*
 * The DAO interface for system message
 */
public interface SystemMessageDAO {
	public List<SystemMessage> getActiveSystemMessages();
	/*
	 * Get the list of system messages
	 * @return List of SystemMessage instances
	 */
	public List<SystemMessage> getInActiveSystemMessages();
	/*
	 * Save the system message
	 * @param messageParametersAndValues the message details in a map
	 */
	public int saveSystemMessageDetails(Map<Object, Object>  messageParametersAndValues);
	 /*
	  * Update the system message
      * @param messageParametersAndValues the message details in a map
	  */
	public int updateSystemMessageDetails(Map<Object, Object>  messageParametersAndValues);
	 /*
	  * Get the system message archives (message histories)
	  * @return List of SystemMessageArchive instances
	  */
	public List<SystemMessageArchive> getSystemMessageArchives(SystemMessage systemMessage);
	
	
	
	public List<SystemMessage> getInProgressActiveSystemMessages();
	
	public List<PortalSystemMessage> getActivePortalSystemMessages();

	public List<PortalSystemMessage> getInActivePortalSystemMessages();
	
	public List<JSONObject> getPortalModuleNames();
	
	public int savePortalSystemMessageDetails(Map<Object, Object>  messageParametersAndValues);	
	
	public int updatePortalSystemMessageDetails(Map<Object, Object>  messageParametersAndValues);
	
	
}
