package gov.ny.state.doh.bhae.nyehms.web.spring.validation.document;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ADAPDocVO;

@Component
public class ADAPDocValidator implements Validator{
	

	
	@Override
	public boolean supports(Class c) {		
		return ADAPDocVO.class.equals(c);
	}	
	
	@Override
	public void validate(Object command, Errors errors) {
		//ADAPDocVO adapDocVO = (ADAPDocVO)command;		
		//Handle All Empty Fields
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lname","error.lNameRequired");
	}

}
