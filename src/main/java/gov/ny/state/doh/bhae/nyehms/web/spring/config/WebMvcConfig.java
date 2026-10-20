package gov.ny.state.doh.bhae.nyehms.web.spring.config;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import tools.jackson.databind.json.JsonMapper;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObjectArgumentResolver;
/**
 * Spring MVC settings on top of Spring Boot's own MVC configuration.
 * <p>
 * Replaces mvc:annotation-driven (message converter + argument resolver), the
 * BeanNameViewResolver, the JSP view resolver and the multipartResolver of
 * spring-config.xml.
 * <ul>
 * <li>JSP view resolver: spring.mvc.view.prefix / spring.mvc.view.suffix in
 * application.yml (Spring Boot creates the InternalResourceViewResolver).</li>
 * <li>Multipart uploads: Spring Boot registers the StandardServletMultipartResolver;
 * the size limits are spring.servlet.multipart.* in application.yml.</li>
 * </ul>
 * Do not add @EnableWebMvc here: it switches Spring Boot's MVC configuration off.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /** Spring Boot's Jackson 3 mapper, configured by spring.jackson.* in application.yml. */
    private final JsonMapper jsonMapper;

    public WebMvcConfig(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    /**
     * Lets controllers declare a UserObject parameter (logged in user from the session).
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserObjectArgumentResolver());
    }

    /**
     * Jackson JSON converter placed BEFORE the default converters, as the old
     * mvc:message-converters block did. The position matters: it decides how a
     * handler that returns a plain String is written when the browser accepts
     * anything, so keep it a "custom" converter (custom converters go first).
     * <p>
     * Uses Spring Boot's JsonMapper, so the JSON looks exactly as it did with Jackson 2
     * (spring.jackson.use-jackson2-defaults and the date setting in application.yml).
     */
    @Override
    public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
        builder.addCustomConverter(new JacksonJsonHttpMessageConverter(jsonMapper));
    }

    /**
     * PDF/Excel download views: the view name returned by the controller is the
     * bean name of a view declared in {@link ReportViewConfig}. Order 1 = asked
     * before the JSP resolver.
     * <p>
     * The bean name "viewResolver" is deliberate, do not rename it. Spring Boot
     * only adds its own ContentNegotiatingViewResolver when no bean has this name,
     * and that resolver would pick the JSP view instead of the PDF/Excel view for
     * a normal browser request.
     */
    @Bean
    public BeanNameViewResolver viewResolver() {
        BeanNameViewResolver viewResolver = new BeanNameViewResolver();
        viewResolver.setOrder(1);
        return viewResolver;
    }
}
