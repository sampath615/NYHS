package gov.ny.state.doh.bhae.nyehms.web.spring.util;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;


@Component
public class CommonExceptionHandler {	
	@Autowired
	private SendEmail sendEmail;
	@Autowired
	private CommonDAO commonDAO;	
	@Autowired	
	DateUtil dateUtil;
		
	private static Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();	
	
	//common tasks when exception occurs in the system
	public void handleException(Exception exception, UserObject userObject, String sessionId){		
				
		String emailMessage="Error occured for user :"+userObject.getUserId()+
							" for session ID :"+sessionId+
							" at "+dateUtil.getSystemDateTime()+
							" Error Description is ::: "+ exception.toString();		
		//Send the email
		Map<Object, Object> messageParametersAndValues = new HashMap<>();		
		messageParametersAndValues.put("p_log_date", new Timestamp(new Date().getTime()));
		messageParametersAndValues.put("p_user_id", userObject.getUserId());
		messageParametersAndValues.put("p_session_id", sessionId);		
		messageParametersAndValues.put("p_log_message", exception.getMessage());
		messageParametersAndValues.put("p_log_error", exception.toString());
		messageParametersAndValues.put("p_log_stacktrace", ExceptionUtils.getStackTrace(exception));
		messageParametersAndValues.put("logStackFrames", ExceptionUtils.getStackFrames(exception));
		//Store the error to DB.
		try{
			commonDAO.writeLogToDB(messageParametersAndValues);
		}catch(Exception e){
			log.error(nyehmsLog+" Error::: "+e.getMessage());
		}
		try{
			sendEmail.sendHTMLMessage(messageParametersAndValues);
		}catch(MessagingException me){
			log.error(nyehmsLog+" Error::: "+me.getMessage());
		}
	}
	
	//Populate Binding Object with user friendly message when Transaction fails/DB connection/ SQL Errors.
	public void populateBindObjTrsnFail(BindingResult result){		
		result.reject("error.transactionFailed");
	}
	
	public void addBindingResultMessage(BindingResult bindingResult,Object messageKey, Object messageValue){
		Map<Object,Object> resultMap=((Map<Object,Object>)bindingResult.getTarget());
			resultMap.put(messageKey, messageValue);
	}
	
	public void inspectDBTransacionResult(Integer transactionstatusCode, String transactionMessage, BindingResult bindingResult){
		
	}
}
