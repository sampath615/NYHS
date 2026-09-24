package gov.ny.state.doh.bhae.nyehms.web.spring.validation.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TBDocVO;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TBDocValidator implements Validator {	
	
	@Override
	public boolean supports(Class c) {		
		return TBDocVO.class.equals(c);
	}	
	
	@Override
	public void validate(Object command, Errors errors) {
		//TBDocVO tbDocVO = (TBDocVO)command;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lname","error.lNameRequired");
	}

}
