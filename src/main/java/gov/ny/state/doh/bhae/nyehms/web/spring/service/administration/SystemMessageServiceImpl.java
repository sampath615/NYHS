package gov.ny.state.doh.bhae.nyehms.web.spring.service.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration.SystemMessageDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.PortalSystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessageArchive;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
/*
 * The service implementation class for System message
 */
@Service
public class SystemMessageServiceImpl implements SystemMessageService {
	/*
	 * Reference to SystemMessageDAO
	 */
	@Autowired
	SystemMessageDAO systemMessageDAO;
	/*
	 * Reference to DateUtil
	 */
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	/**
	 * Get the list of system messages
	 * @param bindingResult the spring BindingResult reference
	 * @param userObject the UserObject reference
	 * @return List of SystemMessage instances
	 * @see gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SystemMessageService#getSystemMessages(org.springframework.validation.BindingResult, gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject)
	 */
	@Override
	public List<SystemMessage> getSystemMessages(String statusFlag, BindingResult bindingResult,
			UserObject userObject) {
		// Status Flag will decide whether to render Active or InActive message
		if(statusFlag.equals("Active")){
			return systemMessageDAO.getActiveSystemMessages();
		}else{
			return systemMessageDAO.getInActiveSystemMessages();
		}
	}
	/**
	 * inserting the new system message in to the DB
	 * @param systemMessage system message details
	 * @param bindingResult the spring BindingResult reference
	 * @param userObject the UserObject reference
	 * @see gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SystemMessageService#saveSystemMessageDetails(gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessage, org.springframework.validation.BindingResult, gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject)
	 */
	@Override
    public void  saveSystemMessageDetails(SystemMessage systemMessage, BindingResult bindingResult, UserObject userObject){
		Map<Object,Object> messageParametersAndValues = new HashMap<Object, Object>();
		
		messageParametersAndValues.put("messageBody", systemMessage.getMessageBody());
		messageParametersAndValues.put("effectiveEndDate", new Date(systemMessage.getEffectiveEndDate()));
		messageParametersAndValues.put("effectiveStartDate", new Date(systemMessage.getEffectiveStartDate()));
		messageParametersAndValues.put("createdBy", userObject.getWorkerId());
		messageParametersAndValues.put("lastUpdatedBy", userObject.getWorkerId());
		
		 int noOfRowsEffected = systemMessageDAO.saveSystemMessageDetails(messageParametersAndValues);
		
		String transactionMessage= noOfRowsEffected > 0 ? "Successfully created the message":"Unable to create the message";
		commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
    }
	/**
	 * Update the existing system message in to DB using MessageId 
     * @param systemMessage system message details
	 * @param bindingResult the spring BindingResult reference
	 * @param userObject the UserObject reference
	 * @see gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SystemMessageService#updateSystemMessageDetails(gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessage, org.springframework.validation.BindingResult, gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject)
	 */
	@Override
    public void  updateSystemMessageDetails(SystemMessage systemMessage, BindingResult bindingResult, UserObject userObject){
		Map<Object,Object> messageParametersAndValues = new HashMap<Object, Object>();
		
		messageParametersAndValues.put("messageBody", systemMessage.getMessageBody());
		messageParametersAndValues.put("messageId", systemMessage.getMessageId());
		messageParametersAndValues.put("deleted", systemMessage.getDeleted());
		messageParametersAndValues.put("effectiveEndDate", new Date(systemMessage.getEffectiveEndDate()));
		messageParametersAndValues.put("effectiveStartDate", new Date(systemMessage.getEffectiveStartDate()));
		messageParametersAndValues.put("createdBy", userObject.getWorkerId());
		messageParametersAndValues.put("lastUpdatedBy", userObject.getWorkerId());
		
		int noOfRowsEffected = systemMessageDAO.updateSystemMessageDetails(messageParametersAndValues);
		
		String transactionMessage= noOfRowsEffected > 0 ? "Successfully updated the message":"Unable to updated the message";
		commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
    }
	/**
     * Get the system message archives (message histories)
	 * @param bindingResult the spring BindingResult reference
	 * @param userObject the UserObject reference
     * @return List of SystemMessageArchive instances
	 * @see gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SystemMessageService#getSystemMessageArchives(org.springframework.validation.BindingResult, gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject)
	 */
	@Override
	public List<SystemMessageArchive> getSystemMessageArchives(SystemMessage systemMessage, BindingResult result, UserObject userObject) {
		// TODO Auto-generated method stub
		return systemMessageDAO.getSystemMessageArchives(systemMessage);
	}
	
	
	
	/**
     * Get the current In progress system messages
	 * @param request the HttpRequest reference
     * @return String of inProgress messages
	 * 
	 */
	@Override
	public void getInProgressActiveSystemMessages(HttpServletRequest request, BindingResult bindingResult) {
		List<SystemMessage> inProgressSystemMessagesList =  systemMessageDAO.getInProgressActiveSystemMessages();
		
		StringBuilder inProgressMessage = new StringBuilder("");
		
		HttpSession session = request.getSession();
		//Appending confidential security training message also to In progress messages
		String trainingStatusMessage = (String)session.getAttribute("confidentialityAndSecurityTrainingDateStatusMessage");
		if(trainingStatusMessage != null &&  !trainingStatusMessage.equals("")){
			inProgressMessage.append(session.getAttribute("confidentialityAndSecurityTrainingDateStatusMessage")).append("    |    ");
		}
		for (SystemMessage systemMessage : inProgressSystemMessagesList) {
			inProgressMessage.append(systemMessage.getMessageBody()).append("    |    ");
		}
		
		commonExceptionHandler.inspectDBTransacionResult(0, inProgressMessage.toString(), bindingResult);
	}
	@Override
	public List<PortalSystemMessage> getPortalSystemMessages(String statusFlag, BindingResult bindingResult, UserObject userObject) {

		// Status Flag will decide whether to render Active or InActive message
		if(statusFlag.equals("Active")){
			return systemMessageDAO.getActivePortalSystemMessages();
		}else{
			return systemMessageDAO.getInActivePortalSystemMessages();
		}
	
	}
	
	@Override
	public List<JSONObject> getPortalModuleNames() {
		
		List<JSONObject> moduleNames = systemMessageDAO.getPortalModuleNames();

		return moduleNames;
	}
	
	@Override
    public void  savePortalSystemMessageDetails(PortalSystemMessage systemMessage, BindingResult bindingResult, UserObject userObject){
		Map<Object,Object> messageParametersAndValues = new HashMap<Object, Object>();
		
		messageParametersAndValues.put("ADMIN", "");
		messageParametersAndValues.put("PRF_DE", "");
		messageParametersAndValues.put("OOC_INQ", "");
		messageParametersAndValues.put("DNLTC_INQ", "");
		messageParametersAndValues.put("SNP_ID", "");
		messageParametersAndValues.put("AIRS_EPI", "");		
		messageParametersAndValues.put("endDate", "");
		
		switch (systemMessage.getModule_name()) {
			case "ADMIN_PAGE" : messageParametersAndValues.put("ADMIN", "Y"); break;
			case "EPRF_DE" : messageParametersAndValues.put("PRF_DE", "Y"); break;
			case "OOC_INQ" : messageParametersAndValues.put("OOC_INQ", "Y"); break;
			case "DNLTC_INQ" : messageParametersAndValues.put("DNLTC_INQ", "Y"); break;
			case "SNP_ID" : messageParametersAndValues.put("SNP_ID", "Y"); break;
			case "AIRS_EPI" : messageParametersAndValues.put("AIRS_EPI", "Y"); break;
		}
		messageParametersAndValues.put("displayMsg", systemMessage.getDisplay_msg());
		messageParametersAndValues.put("startDate", new Date(systemMessage.getStart_date()));
		if (CommonUtil.isNotNullAndEmpty(systemMessage.getEnd_date())) {
			messageParametersAndValues.put("endDate", new Date(systemMessage.getEnd_date()));
		}
		messageParametersAndValues.put("create_by", userObject.getUserId());
		messageParametersAndValues.put("update_by", userObject.getUserId());
		
		 int noOfRowsEffected = systemMessageDAO.savePortalSystemMessageDetails(messageParametersAndValues);
		
		String transactionMessage= noOfRowsEffected > 0 ? "Successfully created the message":"Unable to create the message";
		commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
    }
	@Override
	public void updatePortalSystemMessageDetails(PortalSystemMessage systemMessage, BindingResult bindingResult, UserObject userObject) {
		Map<Object,Object> messageParametersAndValues = new HashMap<Object, Object>();
		
		messageParametersAndValues.put("ADMIN", "");
		messageParametersAndValues.put("PRF_DE", "");
		messageParametersAndValues.put("OOC_INQ", "");
		messageParametersAndValues.put("DNLTC_INQ", "");
		messageParametersAndValues.put("SNP_ID", "");
		messageParametersAndValues.put("AIRS_EPI", "");		
//		messageParametersAndValues.put("endDate", "");
		
		switch (systemMessage.getModule_name()) {
			case "ADMIN_PAGE" : messageParametersAndValues.put("ADMIN", "Y"); break;
			case "EPRF_DE" : messageParametersAndValues.put("PRF_DE", "Y"); break;
			case "OOC_INQ" : messageParametersAndValues.put("OOC_INQ", "Y"); break;
			case "DNLTC_INQ" : messageParametersAndValues.put("DNLTC_INQ", "Y"); break;
			case "SNP_ID" : messageParametersAndValues.put("SNP_ID", "Y"); break;
			case "AIRS_EPI" : messageParametersAndValues.put("AIRS_EPI", "Y"); break;
		}
		
		messageParametersAndValues.put("displayMsg", systemMessage.getDisplay_msg());
		messageParametersAndValues.put("startDate", new Date(systemMessage.getStart_date()));
		if (systemMessage.getEnd_date().length() == 1) {
			messageParametersAndValues.put("endDate", "");
		} else {
			messageParametersAndValues.put("endDate", new Date(systemMessage.getEnd_date()));
		}
		messageParametersAndValues.put("create_by", userObject.getUserId());
		messageParametersAndValues.put("update_by", userObject.getUserId());	
		messageParametersAndValues.put("msgId", systemMessage.getMsgId());
		messageParametersAndValues.put("flagDeleted", systemMessage.getFlag_deleted());
		
		int noOfRowsEffected = systemMessageDAO.updatePortalSystemMessageDetails(messageParametersAndValues);
		
		String transactionMessage= noOfRowsEffected > 0 ? "Successfully updated the message":"Unable to updated the message";
		commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
		
	}	
}
