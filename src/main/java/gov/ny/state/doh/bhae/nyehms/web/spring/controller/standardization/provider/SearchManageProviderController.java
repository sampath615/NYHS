package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.SearchManageProviderService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchManageProviderController {
	@Autowired
	SearchManageProviderService searchManageProviderService;
	
	@Autowired
	GetGridObject getGridObject;
	
	/**
	 * Handler method for rendering Providers details and its Provider Addresses, Raw Addresses 
	 * If SearchFor parameter is 1: then this method will call.
	 * 
	 * @param SubProviderVO holding the Provider details related Search criteria
	 * @param request Http request reference
	 * @return GridObject Having the search resulted data   
	 */ 
	
	@RequestMapping(value="/getProviderDetailsByProvider.json", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody GridObject  getProviderDetailsByProvider(@ModelAttribute("providerVO") SubProviderVO providerVO , HttpServletRequest request, UserObject userObject){
		
		
		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		//implemented new method to retrieve list Provider and its Provider Address, Raw Provider Address with Provider Search Criteria
		GridObject jsonResponse = getGridObject.GetGrid(searchManageProviderService.getProviderDetailsByProvider(providerVO , bindingResult, userObject));
		return jsonResponse;
	
		
	}
	
	/**
	 * Handler method for rendering Providers details and its Provider Addresses, Raw Addresses 
	 * If SearchFor parameter is 2: then this method will call.
	 * 
	 * @param SubProviderAndAddressVO holding the Provider Address related Search criteria
	 * @param request Http request reference
	 * @return GridObject Having the search resulted data   
	 */ 
	@RequestMapping(value="/getProviderDetailsByProviderAddress.json", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody GridObject  getProviderDetailsByProviderAddress(@ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO , HttpServletRequest request, UserObject userObject){
		
		

		Map<String, String> targetMap= new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		//implemented new method to retrieve list Provider and its Provider Address, Raw Provider Address with Provider Address Search Criteria
		GridObject jsonResponse = getGridObject.GetGrid(searchManageProviderService.getProviderDetailsByProviderAddress(subProviderAndAddressVO , bindingResult, userObject));
		return jsonResponse;
		
	}
	
	/**
	 * Handler method for rendering Providers details and its Provider Addresses, Raw Addresses 
	 * If SearchFor parameter is 3: then this method will call.
	 * 
	 * @param RawProviderAddressVO holding the Raw Provider Address details related Search criteria
	 * @param request Http request reference
	 * @return GridObject Having the search resulted data   
	 */ 
	@RequestMapping(value="/getProviderDetailsByRawProviderAddress.json", method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody GridObject  getProviderDetailsByRawProviderAddress(@ModelAttribute("rawProviderAddressVO") RawProviderAddressVO rawProviderAddressVO , HttpServletRequest request, UserObject userObject){
		

		Map<String, String> targetMap= new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		//implemented new method to retrieve list Provider and its Provider Address, Raw Provider Address with Raw Provider Address Search Criteria
		GridObject jsonResponse = getGridObject.GetGrid(searchManageProviderService.getProviderDetailsByRawProviderAddress(rawProviderAddressVO , bindingResult, userObject));
		return jsonResponse;
	
	}
	
	
	@RequestMapping(value="/getRawProviderDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getRawProviderDetails(@ModelAttribute("rawProviderAddressVO") RawProviderAddressVO rawProviderAddressVO , HttpServletRequest request, UserObject userObject){
		if(userObject==null) userObject= new UserObject();

		BindingResult dummyBindingResult = BindingResults.newDummy();
		//implemented new method to retrieve list of unique providers and Provider address map with search crietria
		GridObject jsonResponse = getGridObject.GetGrid(searchManageProviderService.getRawProviderDetails(rawProviderAddressVO , dummyBindingResult, userObject));
		
		return jsonResponse;
		
	}
}
