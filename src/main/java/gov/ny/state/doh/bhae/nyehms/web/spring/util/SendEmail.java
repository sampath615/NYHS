package gov.ny.state.doh.bhae.nyehms.web.spring.util;

import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;

/**
 * This class is used to send the Emails in different formats
 * 
 * @author ska03
 */
@Component
public class SendEmail {

	private static final String SMTP_HOST_NAME = "mailhost.health.state.ny.us";
	private static final String emailSubjectTxt = "Error in NYEHMS Run Time";
	private static final String emailFromAddress = "hinweb@health.state.ny.gov";
	
	@Autowired CommonService commonService;
	
	private static Logger log = LoggerFactory.getLogger(SendEmail.class);

	
	/**
	 * This method is used to send an Email with a default subject, default
	 * from address, default email host, and a list of recipients taken
	 * from the environment properties file.
	 * This method will be used when we get any validation errors while loading the NYEHMS
	 * Crypto card page
	 * 
	 * @param emailBody
	 */
	public void sendDefaultEMail(String emailBody)
	{
		try {
			String[] sendTo = EnvironmentProperties.envProperties.getProperty("error_email_notifications_to").split(",");
			new SendEmail().sendSSLMessage(sendTo, System.getProperty("stage")+" : "+emailSubjectTxt, emailBody,
					emailFromAddress, null, null,
					CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
		} catch (Exception e) {
			log.error("Email Sent failed.");
		}
	}

	/**
	 * This method is used to send a fully customized email, although
	 * the SMTP_HOST_NAME is still hard-coded.
	 * 
	 * @param recipients An array of email address Strings
	 * @param subject Email subject
	 * @param message Body message
	 * @param from From Address
	 * @param cc An array of CC Strings
	 * @param bcc An array of BCC Strings
	 * @param messageType Email content type
	 * @throws MessagingException
	 */
	public void sendSSLMessage(String recipients[], String subject,
			String message, String from, String cc[],  String bcc[],  String messageType)
			throws MessagingException 
	{
		boolean debug = true;
		Properties props = new Properties();
		
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		
		
		InternetAddress[] addressTo = null;
		
		if(recipients != null && recipients.length > 0) {
			
			addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			
		}
		
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		if(cc != null && cc.length > 0){
			InternetAddress[] addressCC = new InternetAddress[cc.length];
			for (int i = 0; i < cc.length; i++) {
				addressCC[i] = new InternetAddress(cc[i]);
			}
			msg.setRecipients(Message.RecipientType.CC, addressCC);
		}

		if(bcc != null && bcc.length > 0){
			InternetAddress[] addressBCC = new InternetAddress[bcc.length];
			for (int i = 0; i < bcc.length; i++) {
				addressBCC[i] = new InternetAddress(bcc[i]);
			}
			msg.setRecipients(Message.RecipientType.BCC, addressBCC);
		}
		
		msg.setSubject(subject);
		msg.setContent(message, messageType);
		Transport.send(msg);
	}

	/**
	 * This method is used when we get any errors in the applications, will send
	 * that error to the Admin with detailed Exception details
	 * 
	 * @param messageParametersAndValues
	 * @throws MessagingException
	 */
	public void sendHTMLMessage(Map<Object, Object> messageParametersAndValues)
			throws MessagingException
	{
		boolean debug = true;

		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(emailFromAddress);
		msg.setFrom(addressFrom);
		String[] sendTo = EnvironmentProperties.envProperties.getProperty("error_email_notifications_to").split(",");
		InternetAddress[] addressTo = new InternetAddress[sendTo.length];
		for (int i = 0; i < sendTo.length; i++) {
			addressTo[i] = new InternetAddress(sendTo[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		String stage = System.getProperty("stage");
		msg.setSubject(stage+" : "+emailSubjectTxt);
		String[] exceptionMessagestack = (String[]) messageParametersAndValues
				.get("logStackFrames");
		StringBuilder stringBuilder = new StringBuilder("<HTML><BODY>");
		stringBuilder
				.append("<B> An error has occured while accessing NYEHMS, please see details below </B><BR>");
		stringBuilder.append("<TABLE border='1'");
		stringBuilder.append("<TR><TD>User</TD><TD>"
				+ messageParametersAndValues.get("p_user_id") + "</TD></TR>");
		stringBuilder.append("<TR><TD>Date</TD><TD>"
				+ messageParametersAndValues.get("p_log_date") + "</TD></TR>");
		stringBuilder.append("<TR><TD>Error point</TD><TD>"
				+ exceptionMessagestack[1] + "</TD></TR>");
		stringBuilder.append("<TR><TD>Error type</TD><TD>"
				+ messageParametersAndValues.get("p_log_error") + "</TD></TR>");
		stringBuilder.append("<TR><TD>Error log ref#</TD><TD>"
				+ messageParametersAndValues.get("appLogId") + "</TD></TR>");
		stringBuilder.append("</TABLE></BODY></HTML>");
		msg.setContent(stringBuilder.toString(), "text/html");
		Transport.send(msg);
	}

	public void processEmail(String userEmailAddress, String subject, String message, String NotificationIdentifier,
			String messageType) throws MessagingException {

		Map<String, String> notificationList = commonService.getNotificationList(NotificationIdentifier);

		String[] recipientEmailAddressesTo = null;

		if (notificationList.get("T") != null) {
			recipientEmailAddressesTo = notificationList.get("T").split(",");
		}

		if (message.contains("HL7 Manual Process Initiated by")) {
			recipientEmailAddressesTo = null;
		}

		if (message.contains("LAFT has commented on follow-up")) {
			recipientEmailAddressesTo = userEmailAddress.split(",");
		} else if (CommonUtil.isNotNullAndEmpty(userEmailAddress)) {
			recipientEmailAddressesTo = ArrayUtils.addAll(recipientEmailAddressesTo, userEmailAddress);

		}

		String[] recipientEmailAddressesCC = null;

		if (notificationList.get("C") != null) {
			recipientEmailAddressesCC = notificationList.get("C").split(",");
		}
		if ((recipientEmailAddressesTo != null && recipientEmailAddressesTo.length > 0)
				|| (recipientEmailAddressesCC != null && recipientEmailAddressesCC.length > 0)) {
			sendSSLMessage(recipientEmailAddressesTo, subject, message,
					EnvironmentProperties.envProperties.getProperty("helpLineFrom"), recipientEmailAddressesCC, null,
					messageType);
		} else {
			log.error("Didn't find Email Notification list, sent to Nyehms helpline");

			log.error(message);

			sendSSLMessage(EnvironmentProperties.envProperties.getProperty("helpLineTo").split(","), subject, message,
					EnvironmentProperties.envProperties.getProperty("helpLineFrom"), recipientEmailAddressesCC, null,
					messageType);

		}

	}
	
	public void sendemailAttachmentsByIdentifier(String subject,
			String message, String messageType, String NotificationIdentifier, MimeBodyPart attachPart)
			throws MessagingException 
	{
		boolean debug = true;
		Properties props = new Properties();
		
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(EnvironmentProperties.envProperties.getProperty("helpLineFrom"));
		msg.setFrom(addressFrom);
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");
		
		
		Map<String, String> notificationList = commonService.getNotificationList(NotificationIdentifier);

		String[] recipientEmailAddressesTo = null;

		if (notificationList.get("T") != null) {
			recipientEmailAddressesTo = notificationList.get("T").split(",");
		}

		if (message.contains("HL7 Manual Process Initiated by")) {
			recipientEmailAddressesTo = null;
		}
		
		InternetAddress[] addressTo = null;
		
		if(recipientEmailAddressesTo != null &&recipientEmailAddressesTo.length > 0) {
			
			addressTo = new InternetAddress[recipientEmailAddressesTo.length];
			for (int i = 0; i < recipientEmailAddressesTo.length; i++) {
				addressTo[i] = new InternetAddress(recipientEmailAddressesTo[i]);
			}
			
		}
		
		msg.setRecipients(Message.RecipientType.TO, addressTo);


		String[] recipientEmailAddressesCC = null;

		if (notificationList.get("C") != null) {
			recipientEmailAddressesCC = notificationList.get("C").split(",");
			
			InternetAddress[] addressCC = new InternetAddress[recipientEmailAddressesCC.length];
			for (int i = 0; i < recipientEmailAddressesCC.length; i++) {
				addressCC[i] = new InternetAddress(recipientEmailAddressesCC[i]);
			}
			msg.setRecipients(Message.RecipientType.CC, addressCC);
		}
				
		BodyPart messageBodyPart2 = new MimeBodyPart();  
		messageBodyPart2.setText(message);  
	    
	    
		Multipart multipart = new MimeMultipart();  
		multipart.addBodyPart(messageBodyPart);
		multipart.addBodyPart(attachPart);
	    //multipart.addBodyPart(messageBodyPart2);  
	    
		msg.setSubject(subject);
		msg.setContent(multipart);
		Transport.send(msg);
	}
	
	
	public void sendemailAttachments(String recipients[], String subject,
			String message, String from, String cc[],  String messageType, MimeBodyPart attachPart)
			throws MessagingException 
	{
		boolean debug = true;
		Properties props = new Properties();
		
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");
		
		
		InternetAddress[] addressTo = null;
		
		if(recipients != null && recipients.length > 0) {
			
			addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			
		}
		
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		if(cc != null && cc.length > 0){
			InternetAddress[] addressCC = new InternetAddress[cc.length];
			for (int i = 0; i < cc.length; i++) {
				addressCC[i] = new InternetAddress(cc[i]);
			}
			msg.setRecipients(Message.RecipientType.CC, addressCC);
		}
		
		BodyPart messageBodyPart2 = new MimeBodyPart();  
		messageBodyPart2.setText(message);  
	    
	    
		Multipart multipart = new MimeMultipart();  
		multipart.addBodyPart(messageBodyPart);
		multipart.addBodyPart(attachPart);
	    //multipart.addBodyPart(messageBodyPart2);  
	    
		msg.setSubject(subject);
		msg.setContent(multipart);
		Transport.send(msg);
	}

}
