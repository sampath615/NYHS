package gov.ny.state.doh.bhae.nyehms.web.spring.controller.helpLine;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.MailProperties;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

/**
 * This controller listens for web requests to send email. This is called from
 * home page Helpline and cryptocard page.
 * 
 * @author vxp06
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class EmailController {

	private final CommonExceptionHandler commonExceptionHandler;
	private final SendEmail sendEmail;
	private final MailProperties mailProperties;
	/**
	 * This method will return helpline.jsp page
	 * 
	 * @return
	 */
	@RequestMapping("/helpline")
	public String helpline() {

		return "email/helpLine";
	}

	/**
	 * This method is called from cryptocard page. This will send helpline email
	 * to user. Email address can be changed by changing value of SendTo array
	 * in this method.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/processEmail", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> processEmail(HttpServletRequest request, UserObject userObject) {

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Integer statusCode = 0;
		String transactionMessage = "Mail successfully sent to NYEHMS Helpdesk"; 
		String[] cc = new String[1];

		String[] sendTo = mailProperties.helpLine().to().split(",");
		
		String  emailSubjectTxt = request.getParameter("emailSubject");
		
		if(emailSubjectTxt == null || "".equals(emailSubjectTxt)){
			emailSubjectTxt = mailProperties.helpLine().subject();
		}
		
		String emailMsgTxt = request.getParameter("emailMessageTxt");
		String emailFromAddress = null;
		
		if(userObject == null){
			log.error("userObject is null");
		}
		
		if(userObject != null && userObject.getEmailAddress() != null){
			emailFromAddress = userObject.getEmailAddress();
			cc[0] = emailFromAddress;
		}else if (userObject != null && userObject.getUserId() != null) {
			emailFromAddress = userObject.getUserId() + "@health.ny.gov";
			cc[0] = emailFromAddress;
		} else {
			emailFromAddress = mailProperties.helpLine().from();
		}
		
		try {
			sendEmail.sendSSLMessage(sendTo, emailSubjectTxt,
					emailMsgTxt, emailFromAddress, cc,  null,
					CommonConstants.RESPONSE_CONTENT_TYPE_PLAIN);
			
		} catch (MessagingException ex) {
			statusCode = -1;
			transactionMessage = "Failed to send email, Please contact helpLine";
			log.error("Failed to send email {}", ex.getMessage());
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		return targetMap;
	}
}
