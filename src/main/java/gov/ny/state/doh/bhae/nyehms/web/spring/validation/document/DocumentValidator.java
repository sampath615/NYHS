package gov.ny.state.doh.bhae.nyehms.web.spring.validation.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.LabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TBDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import gov.ny.state.doh.bhae.nyehms.web.spring.validation.document.LabDocValidator;
import gov.ny.state.doh.bhae.nyehms.web.spring.validation.document.TBDocValidator;

/*
 * Author : Srikar Kummamuri
 * Date : 1/17/2013
 * Description: This is a common Validator for all document controller methods. 
 * This Validator checks for incoming object type and calls individual Validator for each bean type.
 * 
 */


@Component
public class DocumentValidator implements Validator {	
	
	private static final Logger log = LoggerFactory.getLogger(DocumentValidator.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	
	@Override
	public boolean supports(Class c) {		
		return true;
	}	
		
	@Override
	public void validate(Object command, Errors errors) {
		
		log.debug(nyehmsLog+"************************* Incoming Object is {}",command.getClass());
		
		if(command instanceof TBDocVO){
			log.debug(nyehmsLog+" ------ Calling tbDocValidator");
			TBDocValidator tbDocValidator = new TBDocValidator();
			tbDocValidator.validate(command, errors);
		}else if(command instanceof LabDocVO){
			log.debug(nyehmsLog+"-------- Calling labDocValidator");
			LabDocValidator labDocValidator = new LabDocValidator();
			labDocValidator.validate(command, errors);
		}
	}
}
