package gov.ny.state.doh.bhae.nyehms.web.spring.aop.intercepting;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import weblogic.servlet.internal.ServletRequestImpl;
/*
 * Aspect to intercept the Controller layer methods 
 */
@Component
@Aspect
public class ControllerLayerInterceptingAspect {
	/*
	 * A before advice which intercepts any controller methods (JoinPoints) to read the sessionId from HttpServeltRequest and set the same in UserObject 
	 */
	@Before(value="execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization..*.*(..)) || execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.controller.administration..*.*(..)) || execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.controller.person..*.*(..))  || execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.controller.reports..*.*(..)) || execution(public * gov.ny.state.doh.bhae.nyehms.web.spring.controller.messageBox..*.*(..))")
	public void controllerMethodAdvice(JoinPoint proceedingJoinPoint){
		UserObject userObject=null;
		HttpServletRequest httpServletRequest=null;
		HttpSession httpSession=null;
		Object[] arguments=proceedingJoinPoint.getArgs();
			for(int i=0;i<arguments.length;i++){
				if(arguments[i] != null && arguments[i].getClass()==ServletRequestImpl.class){
					httpServletRequest=(HttpServletRequest)arguments[i];
					httpSession=httpServletRequest.getSession();
					userObject=(UserObject)httpSession.getAttribute("UserObj");
					if(userObject != null && httpSession != null){
						userObject.setSessionId(httpSession.getId());
					}
				}
			}
	}
}
