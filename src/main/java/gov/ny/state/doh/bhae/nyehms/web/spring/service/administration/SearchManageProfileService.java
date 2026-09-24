package gov.ny.state.doh.bhae.nyehms.web.spring.service.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.ModuleAndPermissionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;
/*
 * Service interface for Search manage profile
 */
public interface SearchManageProfileService {
	/*
	 * Get the permissions grouped by module and sub modules
	 * @param roleCode the role code for which the permissions are needed
	 * @param bindingResult the BindingResult reference
	 * @param userObject the UserObject reference 
	 * @return List of ModuleAndPermissionsVO instances
	 */
	public List<ModuleAndPermissionsVO> getModuleAndPermissionsByWorkerRegionId(long workerRegionId,BindingResult bindingResult,UserObject userObject, boolean withFlagSuggestedIndicator);
	
	
		public List<ModuleAndPermissionsVO> getModuleAndPermissionsByRole
	(long roleCode,BindingResult bindingResult,UserObject userObject);
		
		public List<String> getGraphsHierarchy(BindingResult bindingResult, UserObject userObject);

}
