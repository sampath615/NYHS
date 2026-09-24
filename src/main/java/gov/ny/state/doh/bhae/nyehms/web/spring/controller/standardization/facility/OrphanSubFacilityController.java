package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.OrphanSubFacilityService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class OrphanSubFacilityController {
	/*
	 * The spring injected GetGridObject reference
	 */
	@Autowired
	GetGridObject getGridObject;
	/*
	 * The spring injected reference of the service implementation 
	 */
	@Autowired
	OrphanSubFacilityService orphanSubFacilityService; 
	/*
	 * Get a list of orphan sub facilities 
	 * @param request HTTPRequest reference
	 * @return GridObject the GridObject instance which holds the orphan addresses 
	 */
	@RequestMapping(value="/getOrphanSubFacilities.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getOrphanSubFacilities(HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		GridObject jsonResponse = getGridObject.GetGrid(orphanSubFacilityService.getOrphanSubFacilities(bindingResult, userObject));
		return jsonResponse;
	}
	/*
	 * This method links an orphan sub facility to a master facility. 
	 * After this operation the orphan sub facility is associated the master facility and no longer an orphan
	 * @param subFacilityId the orphan sub facility for which master facility linking is needed
	 * @param masterFacilityId the master facility to which this orphan sub facility should be linked
 	 * @param request HTTPRequest reference
 	 * @return String the transaction result
	 */
	@RequestMapping(value="/linkOrphanSubFacilityToMasterFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object,Object> linkOrphanSubFacilityToMasterFacility(@RequestParam long  subFacilityId, @RequestParam long  masterFacilityId,HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		orphanSubFacilityService.linkOrphanSubFacilityToMasterFacility(subFacilityId,masterFacilityId, bindingResult, userObject);
		return targetMap;
	}
	/*
	 * This method links a raw sub facility to an orphan sub facility. 
	 * Linking a raw sub facility to an orphan sub facility can not be completed 
	 * unless the orphan gets associated to a master facility. Hence this operation first expect the user
	 * to select a master facility and link the orphan sub facility to this master facility. After this sub-operation
	 * is successfully completed, the raw sub facility is linked to the orphan sub facility.
	 * Please see {@link #linkOrphanSubFacilityToMasterFacility(@RequestParam long  subFacilityId, @RequestParam long  masterFacilityId,HttpServletRequest request)    
	 * After this operation the orphan sub facility is associated to this master facility and no longer an orphan
	 * and the raw sub facility is associated to orphan sub facility and no longer a raw sub facility.
	 * @param subFacilityId the orphan sub facility for which master facility linking is needed
	 * @param masterFacilityId the master facility to which this orphan sub facility should be linked
	 * @param rawSubFacilityId the raw sub facility which needs to be linked to the orphan sub facility
 	 * @param request HTTPRequest reference
 	 * @return String the transaction result
	 */
	@RequestMapping(value="/linkRawToMasterAndOrphanSubFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> linkRawToMasterAndOrphanSubFacility(@RequestParam long  subFacilityId, @RequestParam long  masterFacilityId,@RequestParam long  rawSubFacilityId, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		orphanSubFacilityService.linkRawToMasterAndOrphanSubFacility(subFacilityId,masterFacilityId,rawSubFacilityId,bindingResult, userObject);
		return targetMap;
	}
	/*
	 * Adding New master facility and  link it to orphan sub facility.
	 * @param subFacilityId the sub facility id which needs to be linked
	 * @param masterFacilityVO the value object which holds the master facility details
	 * @param request HTTPRequest reference 
	 * @return String transaction result message
	 */
	@RequestMapping(value="/addNewMasterFacilityAndLinkOrphanSubFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewMasterFacilityAndLinkOrphanSubFacility(@RequestParam long  subFacilityId, @ModelAttribute("masterFacilityVO") MasterFacilityVO masterFacilityVO, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		orphanSubFacilityService.addNewMasterFacilityAndLinkOrphanSubFacility(subFacilityId, masterFacilityVO, bindingResult, userObject);
		return targetMap;
	}
	/*
	 * This method links a raw sub facility to an orphan sub facility. 
	 * Linking a raw sub facility to an orphan sub facility can not be completed 
	 * unless the orphan gets associated to a master facility. Hence this operation first expect the user
	 * to create a master facility and link the orphan sub facility to this master facility. After this sub-operation
	 * is successfully completed, the raw sub facility is linked to the orphan sub facility.
	 * Please see {@link #linkRawToOrphanSubFacility(@RequestParam long  subFacilityId, @RequestParam long  masterFacilityId,@RequestParam long  rawSubFacilityId, HttpServletRequest request)    
	 * After this operation a new master facility is created and the orphan sub facility is associated to this master sub facility and no longer an orphan.
	 * The raw sub facility is associated to orphan sub facility and no longer a raw sub facility.
	 * @param subFacilityId the orphan sub facility for which parent master facility linking is needed
	 * @param rawSubFacilityId the raw sub facility which needs to be linked to the orphan sub facility
 	 * @param masterFacilityVO the parent master facility to be created
 	 * @param request HTTPRequest reference
 	 * @return String the transaction result
	 */
	@RequestMapping(value="/addNewMasterFacilityAndLinkRawToOrphanSubFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewMasterFacilityAndLinkRawToOrphanSubFacility(@RequestParam long  subFacilityId, long rawSubFacilityId, @ModelAttribute("masterFacilityVO") MasterFacilityVO masterFacilityVO,HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		orphanSubFacilityService.addNewMasterFacilityAndLinkRawToOrphanSubFacility(subFacilityId,rawSubFacilityId,masterFacilityVO,bindingResult, userObject);
		return targetMap;
	}

}
