package gov.ny.state.doh.bhae.nyehms.web.spring.controller.administration;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.ModuleAndPermissionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SearchManageProfileService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/*
 * Spring controller class for search manage profile
 */
@Controller
public class SearchManageProfileController {
	/*
	 * GetGridObject reference
	 */
	@Autowired
	GetGridObject getGridObject;
	/*
	 * SearchManageProfileService reference
	 */
	@Autowired
	private SearchManageProfileService searchManageProfileService;
	/*
	 * Get the permissions grouped by module and sub modules
	 * @param roleCode the role code for which the permissions are needed
	 * @param request the HttpServletRequest reference
	 * @return List of ModuleAndPermissionsVO instances
	 */
	@RequestMapping(value="/getModuleAndPermissions.json", method = RequestMethod.POST)
	public  String getModuleAndPermissions(@RequestParam long roleCode, Map<String, Object> resultMap, HttpServletRequest request, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult bindingResult = new MapBindingResult(resultMap, "resultMap");
		
		List<ModuleAndPermissionsVO>  moduleAndPermissionsList= searchManageProfileService.getModuleAndPermissionsByRole(roleCode ,bindingResult, userObject);
		
		resultMap.put("moduleAndPermissionsList", moduleAndPermissionsList);
		
		return "administration/Permissions";
	}

	@RequestMapping(value="/getModuleAndPermissionsByWorkerRegionId.json", method = RequestMethod.POST)
	public  @ResponseBody GridObject getModuleAndPermissionsByWorkerRegionId(@RequestParam long workerRegionId, Map<String, Object> resultMap, HttpServletRequest request, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult bindingResult = new MapBindingResult(resultMap, "resultMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(searchManageProfileService.getModuleAndPermissionsByWorkerRegionId(workerRegionId ,bindingResult, userObject, false));

		return jsonResponse;
	}
	
	
	@RequestMapping(value="/getModuleAndPermissionsHTMLContentWorkerRegionId.json", method = RequestMethod.POST)
	public  String  getModuleAndPermissionsHTMLContentWorkerRegionId(@RequestParam long workerRegionId, Map<String, Object> resultMap, HttpServletRequest request, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult bindingResult = new MapBindingResult(resultMap, "resultMap");
		
		List<ModuleAndPermissionsVO>  moduleAndPermissionsList=  searchManageProfileService.getModuleAndPermissionsByWorkerRegionId(workerRegionId ,bindingResult, userObject, true);

		resultMap.put("moduleAndPermissionsList", moduleAndPermissionsList);
		
		
		return "administration/Permissions";
		
	}

}
