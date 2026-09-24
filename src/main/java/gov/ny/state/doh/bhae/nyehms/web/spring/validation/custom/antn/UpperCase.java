package gov.ny.state.doh.bhae.nyehms.web.spring.validation.custom.antn;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import gov.ny.state.doh.bhae.nyehms.web.spring.validation.custom.antn.impl.UpperCaseValidator;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UpperCaseValidator.class)
@Documented
public @interface UpperCase {

    String message() default "{validator.uppercase}";

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

}

