package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.SearchManageFacilityService;
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
import org.springframework.web.bind.annotation.ResponseBody;
/*
 * The controller for searching and managing facilities
 */
@Controller
public class SearchManageFacilityController {
	/*
	 * Reference to SearchManageFacilityService by Spring dependency injection
	 */
	@Autowired
	SearchManageFacilityService searchManageFacilityService;
	/*
	 * Reference to GetGridObject by Spring dependency injection
	 */
	@Autowired
	GetGridObject getGridObject;
	/**
	 * Handler method for getting Master facilities, their sub facilities and nested raw facilities
	 * by passing the Master facility search criteria
	 * @param masterFacilityVO object to hold the MasterFacility search criteria 
	 * @param request HttpServletRequest reference
	 * @return GridObject GridObject which returns the search results   
	 */ 
	@RequestMapping(value="/getFacilityDetailsByMasterFacility.json", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody GridObject  getFacilityDetailsByMasterFacility(@ModelAttribute("masterFacilityVO") MasterFacilityVO masterFacilityVO , HttpServletRequest request){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<String, Object> targetMap = new HashMap<String, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		//implemented new method to retrieve list Provider and its Provider Address, Raw Provider Address with Provider Search Criteria
		GridObject jsonResponse = getGridObject.GetGrid(searchManageFacilityService.getFacilityDetailsByMasterFacility(masterFacilityVO , bindingResult, userObject));
		return jsonResponse;
	}
	/**
	 * Handler method for getting Master facilities, their sub facilities and nested raw facilities
	 * by passing the Sub facility search criteria
	 * @param subFacilityVO object to hold the SubFacility search criteria
	 * @param request HttpServletRequest reference
	 * @return GridObject GridObject which returns the search results   
	 */ 
	@RequestMapping(value="/getFacilityDetailsBySubFacility.json", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody GridObject  getFacilityDetailsBySubFacility(@ModelAttribute("subFacilityVO") SubFacilityVO subFacilityVO , HttpServletRequest request){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<String, String> targetMap= new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(searchManageFacilityService.getFacilityDetailsBySubFacility(subFacilityVO , bindingResult, userObject));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="/getFacilityDetailsForAssignmentByRawFacility.json", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody GridObject  getFacilityDetailsForAssignmentBySubFacility(@ModelAttribute("rawSubFacilityVO") RawSubFacilityVO rawSubFacilityVO , HttpServletRequest request){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<String, String> targetMap= new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(searchManageFacilityService.getFacilityDetailsForAssignmentByRawFacility(rawSubFacilityVO , bindingResult, userObject));
		
		return jsonResponse;
	}
	
	
	/**
	 * Handler method for getting Master facilities, their sub facilities and nested raw facilities
	 * by passing the Raw facility search criteria
	 * @param rawSubFacilityVO object to hold the RawSubFacility search criteria
	 * @param request HttpServletRequest reference
	 * @return GridObject GridObject which returns the search results   
	 */ 
	@RequestMapping(value="/getFacilityDetailsByRawSubFacility.json", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody GridObject  getFacilityDetailsByRawSubFacility(@ModelAttribute("rawSubFacilityVO") RawSubFacilityVO rawSubFacilityVO , HttpServletRequest request){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<String, String> targetMap= new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(searchManageFacilityService.getFacilityDetailsByRawSubFacility(rawSubFacilityVO , bindingResult, userObject));
		return jsonResponse;
	}
}
