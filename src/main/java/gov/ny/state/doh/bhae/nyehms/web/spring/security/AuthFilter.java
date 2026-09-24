package gov.ny.state.doh.bhae.nyehms.web.spring.security;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
	
	private String nyehmsDebugLog=AppConstants.nyehmLogDebugString.getName();
	
	protected FilterConfig filterConfig;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	public void destroy() {
		this.filterConfig = null;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws java.io.IOException, ServletException {
		
		
		//Enable MyBatis Logging
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		
		
		
		

		HttpSession session = ((HttpServletRequest)request).getSession(false);
		
		
		if (session != null && session.getAttribute("UserObj")!=null) {	
			
			chain.doFilter(request, response);
			
		}else{
			

			String stage = System.getProperty("stage");
						
			String requestURL=((HttpServletRequest)request).getRequestURL().toString();
			
			if((!"LOCAL".equals(stage)  &&	requestURL.contains("/doh2/applinks/nyehms/index.jsp")) || ("LOCAL".equals(stage)  &&	requestURL.contains("/doh2/applinks/nyehms/web/login"))){
				

				if(log.isDebugEnabled()){
					log.debug("Loading the Intial page for the Application");
				}
				
				session = ((HttpServletRequest)request).getSession(true);
				
				UserObject UserObj = new UserObject();
				
				session.setAttribute("UserObj", UserObj);
				
				chain.doFilter(request, response);
				
			}else{
				
				if(log.isDebugEnabled()){
					log.debug("Session timedout redirecting it home page : {}",requestURL );
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/web/logout");

				dispatcher.forward(request, response);
				
			}
			
			
		}
	}
	
}
