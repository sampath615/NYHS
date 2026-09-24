package gov.ny.state.doh.bhae.nyehms.web.spring.domain.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * This method used to maintain the Application context scope.
 * 
 * @author ska03
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext context;
 
    public ApplicationContext getApplicationContext() {
        return context;
    }
 
    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }
}