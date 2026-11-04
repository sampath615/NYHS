package gov.ny.state.doh.bhae.nyehms.web.spring.validation.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;

@Component
public class ACRFValidator implements Validator {
	
	private static Logger log = LoggerFactory.getLogger(ACRFValidator.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();

	@Override
	public boolean supports(Class c) {		
		return AcrfFormVO.class.equals(c);
	}

	@Override
	public void validate(
			Object command,
			Errors errors)
	{
		AcrfFormVO acrfFormVO = (AcrfFormVO)command;
		//Handle All Empty Fields
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rawAcrfDoc.lname","error.lNameRequired");
		//Check if the Lab Test Result is not selected when Test is selected.
		RawAcrfLabsVO rawAcrfLabs;
		for(int i=0; i<acrfFormVO.getRawAcrfLabs().size(); i++){			
			rawAcrfLabs = acrfFormVO.getRawAcrfLabs().get(i);			
			log.debug("{} ****************************  Test Code ::: {}",nyehmsLog,rawAcrfLabs.getAcrfTestCode());
			log.debug("{} ****************************  Result Code :: {}",nyehmsLog,rawAcrfLabs.getAcrfResultCode());
			if( !rawAcrfLabs.getAcrfTestCode().equals("0") && rawAcrfLabs.getAcrfResultCode().equals("0")){
				errors.rejectValue("rawAcrfLabs["+i+"].acrfResultCode","error.testResultCodeRequired");	
			}			
		}	
	}

}
