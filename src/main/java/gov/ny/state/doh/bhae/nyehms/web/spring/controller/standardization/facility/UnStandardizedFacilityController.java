package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.UnStandardizedFacilityService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author axp21
 * The Spring Web MVC controller class which define handler methods for all
 * unstandardized facility operations.  
 *
 */
@RequiredArgsConstructor
@Controller
public class UnStandardizedFacilityController {
	/*
	 * Reference to service interface by means of dependency injection
	 */
	private final UnStandardizedFacilityService unStandardizedFacilityService;
	
	private final GetGridObject getGridObject;
	/**
	 * Handler method for moving facility data to provider. 
	 * @param facilityId the facility to be moved as provider
	 * @param request HTTP request reference
	 * @return transaction success or failure message after the transaction   
	 */
	@RequestMapping(value="/flagFacilityAsProvider.json", method=RequestMethod.POST,produces = "application/json") 
	public @ResponseBody Map<Object,Object> flagFacilityAsProvider(@RequestParam long facilityId,HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		unStandardizedFacilityService.flagFacilityAsProvider(facilityId,bindingResult,userObject);
		return targetMap;
	}
	
	/**
	 * Get the facility and sub facility addresses based on the search criteria.
	 * @param subFacilityVO the model which holds the search attribute values
	 * @param searchFor this parameter gives information about the page which 
	 * requested the facility details search. Based on this parameter value, we need to
	 * filter the orphan addresses from the search result as follows
	 * If searchfor is passed from the orphan address page where we
	 * dont need to show the Unstandardized facility(the imaginary parent under which 
	 * orphan addresses are grouped and shown) in the facility search results because 
	 * an orphan address cannot be associated to this Unstandardized facility.
	 * If searchFor is passed from the raw address page, we 
	 * need to show the Unstandardized facility(the imaginary parent under which 
	 * orphan addresses are grouped and shown) in the facility search results.      
	 * @param request HTTP request reference
	 * @return list of  facility and sub facility addresses encapsulated in a GridObject   
	 */

	@RequestMapping(value="/getMasterFacilitiesWithSubFacilities.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getMasterFacilitiesWithSubFacilities(@ModelAttribute("masterFacilityVO") MasterFacilityVO masterFacilityVO,@ModelAttribute("subFacilityVO") SubFacilityVO  subFacilityVO,@RequestParam String searchFor, HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject gridObject = getGridObject.GetGrid(unStandardizedFacilityService.getMasterFacilitiesWithSubFacilities(masterFacilityVO,subFacilityVO , searchFor, bindingResult, userObject));
		return gridObject;
	}
	/*
	 * The method to standardize a raw sub facility by creating a new master facility
	 * and sub facility
	 * @param masterFacilityVO the master facility details
	 * @param subFacilityVO the sub facility details
	 * @param rawSubFacilityId the raw sub facility to be standardized
	 * @param request the HTTPRequest reference
	 */
	@RequestMapping(value="/standardizeRawByCreatingMasterAndSubFacilities.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> standardizeRawByCreatingMasterAndSubFacilities(@ModelAttribute("masterFacilityVO") MasterFacilityVO masterFacilityVO,@ModelAttribute("subFacilityVO") SubFacilityVO  subFacilityVO,@RequestParam long  rawSubFacilityId, HttpServletRequest request, BindingResult result, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		unStandardizedFacilityService.standardizeRawByCreatingMasterAndSubFacilities(masterFacilityVO, subFacilityVO, rawSubFacilityId, bindingResult, userObject);
		return targetMap;
	}
}
