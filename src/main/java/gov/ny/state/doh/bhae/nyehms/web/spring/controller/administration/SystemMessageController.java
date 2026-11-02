package gov.ny.state.doh.bhae.nyehms.web.spring.controller.administration;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.PortalSystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SystemMessageService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/*
 * Spring controller class for system message
 */
@RequiredArgsConstructor
@Controller
public class SystemMessageController {
	/*
	 * Reference to GetGridObject
	 */
	private final GetGridObject getGridObject;
	/*
	 * Reference to SystemMessageService 
	 */
	private final SystemMessageService systemMessageService;
	
	/*
	 * Redirect to system message manage page
	 */
	@RequestMapping("/getSystemMessageManagingPage")
	public String getSystemMessageManagingPage(Map<String, Object> map, HttpServletRequest request) {	
		
		return "administration/SystemMessageManage";
	}
	
	/**
	 * This method is used to get all System messages 
	 * @param statusFlag this flag will decide wheather to bring Active messages or In Active Messages
	 *              Active: Currently inprogress messages and Future messages
	 *              In Active: Deleted messages and Retired messages
	 * @param request HTTP request reference
	 * @return jsonResponse (Preparing all System messages as JSON Object)   
	 */
	@RequestMapping(value="/getSystemMessages.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getSystemMessages(String statusFlag, HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(systemMessageService.getSystemMessages(statusFlag, bindingResult, userObject));
		
		
		return jsonResponse;
	}
	
	/**
	 * This method is used to get the History of a specified System Message 
	 * 
	 *  History: When the message edited or deleted or updates in dates
	 *  
	 * @param SystemMessage which holds the input parameters information like Message Id
	 * @param request HTTP request reference
	 * @return jsonResponse (Preparing all History messages as JSON Object)   
	 */
	@RequestMapping(value="/getSystemMessageArchives.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getSystemMessageArchives(@ModelAttribute("SystemMessage") SystemMessage systemMessage, HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(systemMessageService.getSystemMessageArchives(systemMessage, bindingResult, userObject));
		return jsonResponse;
	}
	
	/**
	 * This method is used to Save a new System Message
	 *  
	 * @param SystemMessage which holds the input parameters information like Message start Date, End Date and Content
	 * @param request HTTP request reference
	 * @return targetMap(Map) which holds the Response of current Transaction  
	 */
	@RequestMapping(value="/saveSystemMessage.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> saveSystemMessage(@ModelAttribute("SystemMessage") SystemMessage systemMessage, HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		systemMessageService.saveSystemMessageDetails(systemMessage , bindingResult, userObject);
		
		return targetMap;
	}
	
	/**
	 * This method is used to update the Existing Message
	 * 
	 * We can update the Active messages only
	 * 
	 *  If the System message is in Progress:  we can't change the start date, we can edit the End Date or Message Content
	 *  If it is a future message: We can edit start date, end date and Message content
	 *  
	 *  We can't edit any In Active message(Deleted messages and Retired messages)
	 *  
	 * @param SystemMessage which holds the input parameters information like Message start Date, End Date and Content
	 * @param request HTTP request reference
	 * @return targetMap(Map) which holds the Response of current Transaction  
	 */
	@RequestMapping(value="/updateSystemMessage.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateSystemMessage(@ModelAttribute("SystemMessage") SystemMessage systemMessage, HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		systemMessageService.updateSystemMessageDetails(systemMessage , bindingResult, userObject);
		
		return targetMap;
	}
	
	
	/**
	 * This method is used to get all Active Inprogress System messages(This Inprogress messages which includes Confidential security training message also, if any)
	 * 
	 * In the Front end will scroll all these InProgress messages
	 * 
	 * For Every five minutes through AJAX call will call this method to update the Current InProgress messages
	 *  
	 *  
	 *  
	 * @param request HTTP request reference
	 * @return String (Sending all the messages as a String by separating the messages with ':') 
	 */
	@RequestMapping(value="/getInProgressProcessingSystemMessages.json", method = RequestMethod.POST,  produces = "application/json")
	public @ResponseBody Map<Object, Object> getInProgressProcessingSystemMessages(HttpServletRequest request) {		
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		systemMessageService.getInProgressActiveSystemMessages(request, bindingResult);
		
		return targetMap;
	}
	
	// Portal code 4/20/2021
	
	@RequestMapping("/getPortalSystemMessagePage")
	public String getPortalSystemMessagePage(Map<String, Object> map, HttpServletRequest request) {	
		
		map.put("moduleNames", systemMessageService.getPortalModuleNames());
		return "administration/PortalSystemMessage";
	}
	
	@RequestMapping(value="/getPortalSystemMessages.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getPortalSystemMessages(String statusFlag, HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(systemMessageService.getPortalSystemMessages(statusFlag, bindingResult, userObject));
		
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/savePortalSystemMessage.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> savePortalSystemMessage(@ModelAttribute("SystemMessage") PortalSystemMessage systemMessage, HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		systemMessageService.savePortalSystemMessageDetails(systemMessage , bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/updatePortalSystemMessage.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updatePortalSystemMessage(@ModelAttribute("SystemMessage") PortalSystemMessage systemMessage, HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		systemMessageService.updatePortalSystemMessageDetails(systemMessage , bindingResult, userObject);
		
		return targetMap;
	}

	
}
