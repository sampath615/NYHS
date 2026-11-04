package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import jakarta.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * Settings every JSP can read as ${NAME} (application scope).
 * Replaces scriptlets that read EnvironmentProperties directly.
 */
@Component
public class JspGlobals implements ServletContextAware {

    private final AppProperties appProperties;

    public JspGlobals(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        // assignment/AssignmentsListCriteria.jsp
        servletContext.setAttribute("ASSIGNMENT_30_DAY_NOTE", appProperties.assignment30DayNote());
    }
}
