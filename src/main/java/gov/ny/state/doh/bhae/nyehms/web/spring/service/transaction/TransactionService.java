package gov.ny.state.doh.bhae.nyehms.web.spring.service.transaction;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabFacilitiesVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;



public interface TransactionService {
	

	public List<LabFacilitiesVO> getFacilitiesDataAndContactsAssociatedForClia(BindingResult bindingResult, UserObject userObject, String clia);
	
	public void addNewLabLoincDetailsToLookup(BindingResult bindingResult, UserObject userObject, LabLoincMappingVO labLoincMappingVO);
	
}
