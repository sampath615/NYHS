package gov.ny.state.doh.bhae.nyehms.web.spring.service.transaction;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabFacilitiesVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LabReportsTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


/**
* This class is service interface between the user data to Database(MyBatis related configuration)
* 
* It renders the data from Database in the form List, Int, Sting etc uinsg MyBatis configuation file 
*  
* @author  
* @version 
*/
@RequiredArgsConstructor
@Service
public class TransactionService {

	private final LabReportsTransactionHelper labReportsTransactionHelper;
	
	
	/*
	 * Spring dependency injected reference to CommonExceptionHandler
	 */
	
	private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
	
	
	public List<LabFacilitiesVO> getFacilitiesDataAndContactsAssociatedForClia(BindingResult bindingResult, UserObject userObject, String clia){
	
		List<LabFacilitiesVO> facilitiesList =  labReportsTransactionHelper.getFacilitiesDataAndContactsAssociatedForClia(bindingResult, userObject, clia);
		
		return facilitiesList;
	}
	
	
	
	
	
	
	public void addNewLabLoincDetailsToLookup(BindingResult bindingResult, UserObject userObject, LabLoincMappingVO labLoincMappingVO){
		labReportsTransactionHelper.addNewLabLoincDetailsToLookup(bindingResult, userObject, labLoincMappingVO);
	}
	
	
	
	
	
	
	
}
