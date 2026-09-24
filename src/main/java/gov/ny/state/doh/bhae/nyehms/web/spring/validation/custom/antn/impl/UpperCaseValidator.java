package gov.ny.state.doh.bhae.nyehms.web.spring.validation.custom.antn.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import gov.ny.state.doh.bhae.nyehms.web.spring.validation.custom.antn.UpperCase;

/*
 * This is a sample custom spring jsr 303 validator. 
 * The intention is to use this one as sample going forward and code custom validators.
 * This class is dependent on the annotation UpperCase, that was written in other package
 * gov.ny.state.doh.bhae.nyehms.web.spring.validation.custom.antn.
 * 
 * Usage : Simple use the annotation as @UpperCase on any bean field that needs to be validated.
 * 
 * Ex:
 * 
 * @UpperCase * private String lastName
 * 
 * This will ensure the field lastName in the bean is validated using @UpperCase validation.
 */

public class UpperCaseValidator implements ConstraintValidator<UpperCase, String> {

	public void initialize(UpperCase constraintAnnotation) {
		// nothing to do
	}

	public boolean isValid(
			String object,
			ConstraintValidatorContext constraintContext)
	{
		if (object == null)
			return true;
		return object.equals(object.toUpperCase());
	}

}
