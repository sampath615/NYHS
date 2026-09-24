package gov.ny.state.doh.bhae.nyehms.web.spring.validation.document;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.SSDMDocVO;

@Component
public class SSDMDocValidator implements Validator{
	

	
	@Override
	public boolean supports(Class c) {		
		return SSDMDocVO.class.equals(c);
	}	
	
	@Override
	public void validate(Object command, Errors errors) {
		//SSDMDocVO SSDMDocVO = (SSDMDocVO)command;		
		//Handle All Empty Fields
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lname","error.lNameRequired");
	}

}
