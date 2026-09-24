package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.UnStandardizedProviderService;
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

/**
 * 
 * @author axp21
 * The Spring Web MVC controller class which define handler methods for all
 * unstandardized provider operations.  
 *
 */
@Controller
public class UnStandardizedProviderController {
	/*
	 * Reference to service locator by means of dependency injection
	 */
	@Autowired
	UnStandardizedProviderService unStandardizedProviderService;
	
	@Autowired
	GetGridObject getGridObject;
	/**
	 * Handler method for moving provider data to facility. Accepts json request 
	 * via HTTP POST method
	 * @param request HTTP request reference
	 * @return result after the transaction   
	 */
	@RequestMapping(value="/flagProviderDataAsFacilty.json", method=RequestMethod.POST,produces = "application/json") 
	public @ResponseBody Map<Object,Object> flagProviderDataAsFacilty(@RequestParam long providerId,HttpServletRequest request){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		unStandardizedProviderService.flagProviderDataAsFacilty(providerId,bindingResult,userObject);
		return targetMap;
	}

	@RequestMapping(value="/flagSelectedRawProviderAsFacilty.json", method=RequestMethod.POST,produces = "application/json") 
	public @ResponseBody Map<Object,Object> flagSelectedRawProviderAsFacilty(@RequestParam("selectedRowIds[]") String[] selectedRowIds,HttpServletRequest request){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		unStandardizedProviderService.flagSelectedRawProviderAsFacilty(selectedRowIds,bindingResult,userObject);
		return targetMap;
	}
	
	/**
	 * Handler method for moving provider data to facility. Accepts json request 
	 * via HTTP POST method
	 * @param searchFor this parameter gives information about the page which 
	 * requested the provider details search. Based on this parameter value, we need to
	 * filter the orphan addresses from the search result as follows
	 * For searchFor = 1 - this value is passed from the orphan address page where we
	 * dont need to show the Unstandardized provider(the imaginary parent under which 
	 * orphan addresses are grouped and shown) in the provider search results because 
	 * an orphan address cannot be associated to this Unstandardized provider.
	 * For searchFor = 3 - this value is passed from the raw address page where we 
	 * need to show the Unstandardized provider(the imaginary parent under which 
	 * orphan addresses are grouped and shown) in the provider search results.      
	 * @param request HTTP request reference
	 * @return result after the transaction   
	 */

	@RequestMapping(value="/getSrchProviderDetailsWithAddressList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getSrchProviderDetailsWithAddressList(@ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO 
			,@RequestParam int searchFor, @RequestParam String searchFromNPI, HttpServletRequest request){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();

		Map<String, String> dummyMindingMap= new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		//implemented new method to retrieve list of unique providers and Provider address map with search crietria
		GridObject jsonResponse = getGridObject.GetGrid(unStandardizedProviderService.getSrchProviderDetailsWithAddressList(subProviderAndAddressVO , searchFor, searchFromNPI, dummyBindingResult, userObject));
		return jsonResponse;
	}
	
	
	
	
}
