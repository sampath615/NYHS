package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ApplicationConfig {

    /** Bean Validation (messages in ValidationMessages.properties). */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * Message bundle "messages" (messages_en.properties). Declared explicitly:
     * Spring Boot only creates a MessageSource by itself when a plain
     * messages.properties exists, and this project only has messages_en.properties.
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

}
