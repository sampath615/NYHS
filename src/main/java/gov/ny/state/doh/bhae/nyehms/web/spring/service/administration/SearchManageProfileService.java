package gov.ny.state.doh.bhae.nyehms.web.spring.service.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration.SearchManageProfileDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.ModuleAndPermissionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
/*
 * The service implementation class for search manage profile
 */
@Service
public class SearchManageProfileService {
	/*
	 * Reference to SearchManageProfileDAO
	 */
	@Autowired
	SearchManageProfileDAO searchManageProfileDAO;
	/*
	 * Get the permissions grouped by module and sub modules
	 * @param roleCode the role code for which the permissions are needed
	 * @param bindingResult the BindingResult reference
	 * @param userObject the UserObject reference 
	 * @return List of ModuleAndPermissionsVO instances
	 * @see gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SearchManageProfileService#getModuleAndPermissionsByRole(long, org.springframework.validation.BindingResult, gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject)
	 */
	public List<ModuleAndPermissionsVO> getModuleAndPermissionsByRole(long roleCode,BindingResult bindingResult,UserObject userObject){
			Map<Object,Object> inOutParameterMap=new HashMap<>(); 
			List<ModuleAndPermissionsVO> moduleAndPermissionsByRole;
			inOutParameterMap.put("p_role_cd", roleCode);
			
			String roleName = searchManageProfileDAO.getRoleName(roleCode);
			if(CommonUtil.isNotNullAndEmpty(roleName)) {
				inOutParameterMap.put("p_roleName", roleName.replaceAll("[^A-Za-z]", "_").toUpperCase());
			}
			
			
			
			moduleAndPermissionsByRole = searchManageProfileDAO.getModuleAndPermissions(inOutParameterMap);
			return moduleAndPermissionsByRole;
	}
	
	
	/*
	 * Get the permissions grouped by module and sub modules
	 * @param roleCode the role code for which the permissions are needed
	 * @param bindingResult the BindingResult reference
	 * @param userObject the UserObject reference 
	 * @return List of ModuleAndPermissionsVO instances
	 */
	public List<ModuleAndPermissionsVO> getModuleAndPermissionsByWorkerRegionId(long workerRegionId,BindingResult bindingResult,UserObject userObject, boolean withFlagSuggestedIndicator){
			Map<Object,Object> inOutParameterMap=new HashMap<>(); 
			List<ModuleAndPermissionsVO> moduleAndPermissionsByRole;
			inOutParameterMap.put("p_worker_region_id", workerRegionId);
			
			String roleName = searchManageProfileDAO.getRoleNameByWorkerRegionId(workerRegionId);
			if(CommonUtil.isNotNullAndEmpty(roleName)) {
				inOutParameterMap.put("p_roleName", roleName.replaceAll("[^A-Za-z]", "_").toUpperCase());
			}
			
			if(withFlagSuggestedIndicator){
				
				moduleAndPermissionsByRole = searchManageProfileDAO.getModuleAndPermissionsByWorkerRegionId(inOutParameterMap);
			}else{
				moduleAndPermissionsByRole = searchManageProfileDAO.getModuleAndPermissionsByWorkerRegionIdWithOutFlagSugg(inOutParameterMap);
			}
			return moduleAndPermissionsByRole;
	}
	
	
	public List<String> getGraphsHierarchy(BindingResult bindingResult, UserObject userObject){
		
		Map<Object,Object> inOutParameterMap=new HashMap<>();
		
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		
		List<String> graphsHierarchyList = searchManageProfileDAO.getGraphsHierarchy(inOutParameterMap);
		
		return graphsHierarchyList;
		
	}
}
