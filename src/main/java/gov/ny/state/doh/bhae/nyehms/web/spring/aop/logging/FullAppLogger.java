package gov.ny.state.doh.bhae.nyehms.web.spring.aop.logging;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


/*
 *Author		: Srikar Kummamuri
 *Date Created 	: Mar-14-2012
 *Description 	: This will handle all logging related to application.
 */

@Component
@Aspect
public class FullAppLogger {
	
	private static Logger log = LoggerFactory.getLogger(FullAppLogger.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Around("execution(* gov.ny.state.doh.bhae.nyehms.web.spring..*.*(..))")
	public Object AppLogger(ProceedingJoinPoint joinPoint) throws Throwable {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object returnObj = joinPoint.proceed();
						
		stopWatch.stop();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(joinPoint.getTarget().getClass().getName());
		//stringBuffer.append(".");
		//stringBuffer.append(joinPoint.getSignature().getName());
		//stringBuffer.append("(");
		Object[] methodArrgs = joinPoint.getArgs();
		for (int i = 0; i < methodArrgs.length; i++) {
			//stringBuffer.append(methodArrgs[i]).append(",");
		}
		if (methodArrgs.length > 0) {
			//stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		}
		//stringBuffer.append(")");
		stringBuffer.append(" execution time: ");
		stringBuffer.append(stopWatch.getTotalTimeMillis());
		stringBuffer.append(" ms");	
		
		/*
		 * Explicitly use Log4j Logging to MyBatis logging.
		 * This is required as MyBatis first looks for SL4J and then Log4J. 
		 * As we also have SL4J in path 
		 * used by Spring Validation, unless
		 * we specifically use Log4J, MyBatis wont log the statements out as
		 * we are using Log4j (log4j.xml).   
		 */
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		
		//log.debug("***--NYEHMS METHOD EXECUTION TIME--***: "+ stringBuffer.toString());
		return returnObj;
	}	
}
