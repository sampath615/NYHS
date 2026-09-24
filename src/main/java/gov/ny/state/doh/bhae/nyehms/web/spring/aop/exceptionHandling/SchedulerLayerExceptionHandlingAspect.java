package gov.ny.state.doh.bhae.nyehms.web.spring.aop.exceptionHandling;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/*
 * Spring AOP based Aspect class for managing exception handling on behalf of the service layer business methods. 
 * Though the advice are defined separate for read and write methods, Spring AOP gives enough flexibility to swap the advice roles or scale monitoring
 * by adjusting the PointCut definitions with each advice. Hence for any future requirements please re-define or add new PointCuts if the current ones look insufficient
 */
@Component
@Aspect
public class SchedulerLayerExceptionHandlingAspect {
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(SchedulerLayerExceptionHandlingAspect.class);
	/*
	 * Reference to SendEmail Java Mail component
	 */
	/*
	 * Reference to CommonExceptionHandler
	 */
	/*
	 * Reference to CommonDAO
	 */
	
	
	/*
	 * An AfterThrowing advice which will handle any exceptions thrown by the service layer read methods
	 * This method intercept the business method and read its argument values to log error, write to the database and send brief error email message to system administrator
	 * @param joinPoint the JoinPoint reference
	 * @param throwable the Throwable reference
	 */
	@AfterThrowing(pointcut="execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers..*(..))",
			throwing="throwable")
	public void readMethodExceptionHandlingAdvice(JoinPoint joinPoint,Throwable throwable){
	
		log.error(joinPoint.getSignature()+"          \n"+
//				AppConstants.nyehmLogErrorString.getName()+" /n"+ExceptionUtils.getStackFrames(throwable)[1]);
		AppConstants.nyehmLogErrorString.getName()+" /n"+CommonUtil.getStackTrace(throwable));
		
			//log.error("Send mail failed inside "+joinPoint.getSignature()+" with error "+ExceptionUtils.getStackFrames(messagingException));
	}
}
