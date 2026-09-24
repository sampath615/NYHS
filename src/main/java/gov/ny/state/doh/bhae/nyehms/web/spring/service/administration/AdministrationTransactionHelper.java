package gov.ny.state.doh.bhae.nyehms.web.spring.service.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsWorkerRegionsViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
public class AdministrationTransactionHelper {
			
	@Autowired
	AdministrationService administrationService ;
	
	@Transactional
	public void terminateUserFromHCSAndDB(long workerId, String legacyUid, BindingResult bindingResult, UserObject userObject)throws DataRetrievalFailureException {
			
		int statusCode = administrationService.terminateUserFromDB(workerId, bindingResult, userObject);
		
		if(statusCode == 0){
			administrationService.terminateUserFromHCS(legacyUid, bindingResult, userObject);
		}
	}

	@Transactional
	public void creatingNewUserInHcsAndNyehms(
			NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO,
			BindingResult bindingResult, UserObject userObject) {
		int statusCode = administrationService.creatingNewUserInNyehms(nyehmsWorkerRegionsViewVO, bindingResult, userObject);
		
		if(statusCode == 0 && (nyehmsWorkerRegionsViewVO.getUserTypeCode().equals("APP") || nyehmsWorkerRegionsViewVO.getUserTypeCode().equals("APP_BACKEND") 
				|| nyehmsWorkerRegionsViewVO.getUserTypeCode().equals("FACILITY"))){
			administrationService.creatingNewUserInHcs(nyehmsWorkerRegionsViewVO, bindingResult, userObject);
		}
		
	}
	
	
}
