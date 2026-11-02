package gov.ny.state.doh.bhae.nyehms.web.spring.aop.exceptionHandling;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
/*
 * Spring AOP based Aspect class for managing exception handling on behalf of the service layer business methods. 
 * Though the advice are defined separate for read and write methods, Spring AOP gives enough flexibility to swap the advice roles or scale monitoring
 * by adjusting the PointCut definitions with each advice. Hence for any future requirements please re-define or add new PointCuts if the current ones look insufficient
 */
@RequiredArgsConstructor
@Component
@Aspect
public class ServiceLayerExceptionHandlingAspect {
	/*
	 * Reference to Log
	 */
	private static final Logger log = LoggerFactory.getLogger(ServiceLayerExceptionHandlingAspect.class);
	/*
	 * Reference to SendEmail Java Mail component
	 */
	private final SendEmail sendEmail;
	/*
	 * Reference to CommonExceptionHandler
	 */
	private final CommonExceptionHandler commonExceptionHandler;
	/*
	 * Reference to CommonDAO
	 */
	private final CommonDAO commonDAO;
	/*
	 * An Around advice to monitor the execution of all service layer write methods. If the write method encounter an exception during its execution this advice take the help of
	 * AfterThrowing advice as defined below @see public void readMethodExceptionHandlingAdvice(JoinPoint,Throwable)
	 * @param proceedingJoinPoint ProceedingJoinPoint reference which should be used with an Around advice   
	 */
	@Around(value="execution(public void gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization..*.*(..))")
	public void writeMethodExceptionHandlingAdvice(ProceedingJoinPoint proceedingJoinPoint){
		try{
			proceedingJoinPoint.proceed();
		}catch(Throwable throwable){
			log.error("Exception occurred  while processing the information  {}", throwable.getMessage());
		}
	}
	/*
	 * An AfterThrowing advice which will handle any exceptions thrown by the service layer read methods
	 * This method intercept the business method and read its argument values to log error, write to the database and send brief error email message to system administrator
	 * @param joinPoint the JoinPoint reference
	 * @param throwable the Throwable reference
	 */
	@AfterThrowing(pointcut="execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization..*.*(..)) || execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.service.administration..*.*(..)) || execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.service.person..*.*(..))  || execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.service.reports..*(..)) || execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.service.lab..*(..)) || execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.service.scan..*.*(..))",
			throwing="throwable")
	public void readMethodExceptionHandlingAdvice(JoinPoint joinPoint,Throwable throwable){
		Object[] arguments=joinPoint.getArgs();
		MapBindingResult mapBindingResult=null;
		UserObject userObject=null;
		String sessionId=null;
		for(int i=0;i<arguments.length;i++){
			if(arguments[i].getClass()==MapBindingResult.class){
				mapBindingResult=(MapBindingResult)arguments[i];
			}else if(arguments[i].getClass()==UserObject.class){
				userObject=(UserObject)arguments[i];
				sessionId=userObject.getSessionId(); 
			}
		}
		
		if(mapBindingResult != null && mapBindingResult.getTargetMap() != null && mapBindingResult.getTargetMap().get(AppConstants.NYEHM_DB_TRANSACTION_ERROR_MESSAGE.getName()) == null){
			commonExceptionHandler.inspectDBTransacionResult(-1, CommonConstants.TRANSACTION_FAILED_MESSAGE, mapBindingResult);
			log.error(AppConstants.nyehmLogErrorString.getName()+"  "+					
			mapBindingResult.getTargetMap().get(AppConstants.NYEHM_DB_TRANSACTION_ERROR_CODE.getName())+"  "+
					mapBindingResult.getTargetMap().get(AppConstants.NYEHM_DB_TRANSACTION_ERROR_MESSAGE.getName()));
		}
		
		log.error(ExceptionUtils.getStackFrames(throwable)[1]);
		Map<Object,Object> messageParametersAndValues=prepareMessageParameterValues(userObject, throwable);
		commonDAO.writeLogToDB(messageParametersAndValues);
		try{
			sendEmail.sendHTMLMessage(messageParametersAndValues);
		}catch(MessagingException messagingException){
			log.error("Send mail failed inside {} with error {}",joinPoint.getSignature(),ExceptionUtils.getStackFrames(messagingException));
			//log.error("Send mail failed inside "+joinPoint.getSignature()+" with error "+ExceptionUtils.getStackFrames(messagingException));
		}
		
	}
	/*
	 * An Around advice to inspect the results returned from the database procedure call. This advice monitors execution of 
	 * gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler.inspectDBTransacionResult(Integer transactionstatusCode, String transactionMessage, BindingResult bindingResult)
	 * and reads the argument values for the exception handling process
	 * @param proceedingJoinPoint ProceedingJoinPoint reference which should be used with an Around advice   
 	 */
	@Around(value="execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler.inspectDBTransacionResult(..))")
	public void dbTransactionResultHandlingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		Object args[]=proceedingJoinPoint.getArgs();
		Integer transactionStatusCode=(Integer)args[0];
		String transactionMessage=(String)args[1];
		BindingResult bindingResult=(BindingResult)args[2];
		if(transactionStatusCode==0){
			commonExceptionHandler.addBindingResultMessage(bindingResult,AppConstants.NYEHM_TRANSACTION_RESULT.getName(), AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName());
			commonExceptionHandler.addBindingResultMessage(bindingResult,AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), transactionMessage);
		}
		else{
			bindingResult.addError(new ObjectError(AppConstants.NYEHM_DB_TRANSACTION_ERROR_MESSAGE.getName(), transactionMessage));
			commonExceptionHandler.addBindingResultMessage(bindingResult,AppConstants.NYEHM_TRANSACTION_RESULT.getName(), AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
			commonExceptionHandler.addBindingResultMessage(bindingResult,AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), transactionMessage);
			commonExceptionHandler.addBindingResultMessage(bindingResult, AppConstants.NYEHM_DB_TRANSACTION_ERROR_CODE.getName(), Integer.toString(transactionStatusCode));
			commonExceptionHandler.addBindingResultMessage(bindingResult, AppConstants.NYEHM_DB_TRANSACTION_ERROR_MESSAGE.getName(), AppConstants.NYEHM_USER_FRIENDLY_ERROR_MESSAGE.getName());
		}
		proceedingJoinPoint.proceed();
	}
	/*
	 * Helper method to assemble the error message and user details so that the Persistence handler and Email handler can consume this easily
	 * @param userObject the UserObject reference 
	 * @param throwable the Throwable reference
	 */
	private Map<Object,Object> prepareMessageParameterValues(UserObject userObject,Throwable throwable){
		Map<Object, Object> messageParametersAndValues = new HashMap<>();		
		messageParametersAndValues.put("p_log_date", new Timestamp(new Date().getTime()));
		
		if(userObject == null) {
			messageParametersAndValues.put("p_user_id", "NYEHMS_APP");
		}else {
			messageParametersAndValues.put("p_user_id", userObject.getUserId());
			messageParametersAndValues.put("p_session_id", userObject.getSessionId());
		}
		
		messageParametersAndValues.put("p_log_message", throwable.getMessage());
		messageParametersAndValues.put("p_log_error", throwable.toString());
		messageParametersAndValues.put("p_log_stacktrace", ExceptionUtils.getStackTrace(throwable));
		messageParametersAndValues.put("logStackFrames", ExceptionUtils.getStackFrames(throwable));
		return messageParametersAndValues;
	}
}
