package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.provider;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.ProviderService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@Controller
public class ProviderController {

	@Autowired
	private ProviderService providerService;
	@Autowired
	GetGridObject getGridObject;
	@Autowired
	CommonUtil commonUtil;
	@Autowired CommonService     commonService;

	@RequestMapping("/ProviderStdznHome")
	public String getProviderStdznHome (Map<String, Object> map, HttpServletRequest request) {

		//For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "standardization/provider/ProviderStdznHome";
	}
	
	@RequestMapping("/ProviderAddPage")
	public String ProviderAddPage(Map<String, Object> map, HttpServletRequest request) {

		//For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "standardization/provider/ProviderAddPage";
	}
	
	@RequestMapping("/ChangeRawProvider")
	public String ChangeRawProvider(Map<String, Object> map, HttpServletRequest request) {

		//For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "standardization/provider/ChangeRawProvider";
	}
	
	@RequestMapping(value="/addStandardAddress.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object>  addStandardAddress(HttpServletRequest request, @ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.addingAddressToProvider(-999, subProviderAndAddressVO, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	@RequestMapping(value="/unableStandardizeProviderRecord.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object>  unableStandardizeProviderRecord(HttpServletRequest request, @RequestParam long recordId, @RequestParam String effectedTable ){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.unableStandardizeProviderRecord(recordId, effectedTable, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	
	/*
	 * Getting Raw Provider Address 
	 */
	@RequestMapping(value="/getRawProvAddrsList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getRawProvAddrsList(HttpServletRequest request, @RequestParam(value="_search", required = false) boolean _search){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		String searchField = null;
		String searchString = null;
		String searchOper = null;
		if(_search){
			searchField = request.getParameter("searchField");
			searchString = request.getParameter("searchString");
			searchOper = request.getParameter("searchOper");
		}
		
		GridObject jsonResponse = getGridObject.GetGrid(providerService.getRawProvAddrsList(bindingResult, _search, searchField, searchString, searchOper, userObject));
		
		return jsonResponse;
	}

	/*
	 * linking Raw Provider To Sub Provider Address ID
	 * @param rawProviderAddressId the raw provider to be standardized
	 * @param providerAddressId the target provider to which this raw provider address should be linked
	 * @param request HTTPRequest reference 
	 * @return Map containing transaction result message
	 */
	@RequestMapping(value="/linkRawProvToSubProvAddrs.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> linkRawProvToSubProvAddrs(@RequestParam long  rawProviderAddressId, @RequestParam long  providerAddressId, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.linkRawProvToSubProvAddrs(rawProviderAddressId, providerAddressId, bindingResult, userObject);
		return targetMap;
	}

	/*
	 * Adding New Provider and Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param subProviderAndAddressVO the value object which holds the provider address
	 * @param request HTTPRequest reference 
	 * @return Map containing transaction result message
	 */
	@RequestMapping(value="/addNewSubProvAddrsAndlinking.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewSubProvAddrsAndlinking(@RequestParam long  rawProviderAddressId, @ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.addNewSubProvAddrsAndlinking(rawProviderAddressId, subProviderAndAddressVO, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/addNewProvNAddrsAndlinking.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewProvNAddrsAndlinking(@RequestParam("rawProviderAddressIds[]") String[]  rawProviderAddressIds, @ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.addNewProvNAddrsAndlinking(rawProviderAddressIds, subProviderAndAddressVO, bindingResult, userObject);
		return targetMap;
	}
	
	/*
	 * Adding New Provider and Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param subProviderAndAddressVO the value object which holds the provider address
	 * @param request HTTPRequest reference 
	 * @return Map containing transaction result message
	 */
	@RequestMapping(value="/addRawAndSubProviderAddressDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addRawAndSubProviderAddressDetails(@RequestParam long providerId, @ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.addRawAndSubProviderAddressDetails(providerId, subProviderAndAddressVO, bindingResult, userObject);
		return targetMap;
	}
	
	/*
	 * Adding New Provider and Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param subProviderAndAddressVO the value object which holds the provider address
	 * @param request HTTPRequest reference 
	 * @return Map containing transaction result message
	 */
	@RequestMapping(value="/addProviderDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addProviderDetails(@ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.addProviderDetails(subProviderAndAddressVO, bindingResult, userObject);
		return targetMap;
	}
	
	
	
	/*
	 * Adding New Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param providerId the provider to which the new address should be linked to
	 * @param subProviderAndAddressVO the value object which holds the provider address
	 * @param request HTTPRequest reference 
	 * @return Map containing transaction result message
	 */
	@RequestMapping(value="/addingAddrsAndlinking.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addingAddrsAndlinking(@RequestParam long  rawProviderAddressId, @RequestParam long  providerId,  @ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.addingAddrsAndlinking(rawProviderAddressId,providerId, subProviderAndAddressVO, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/addingProvAddrsAndlinking.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addingProvAddrsAndlinking(@RequestParam("rawProviderAddressIds[]") String[]  rawProviderAddressIds, @RequestParam long  providerId,  @ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.addingProvAddrsAndlinking(rawProviderAddressIds,providerId, subProviderAndAddressVO, bindingResult, userObject);
		return targetMap;
	}

	/*
	 * Below are the Provider Search page Controllers and Ajax calls 
	 *  
	 */
	@RequestMapping("/ProviderStdznSearchPage")
	public String ProviderStdznSearchPage (Map<String, Object> map, HttpServletRequest request) {	
		
		//For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);

		return "standardization/provider/ProviderStdznSearchPage";
	}

	//get the Raw Provider Addresses List using Sub Provider Address ID(Sub Grid)	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getRawProvAdrsListBySubprovAdrsId.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getRawProvAdrsListBySubprovAdrsId(@ModelAttribute("rawProviderAddressVO") RawProviderAddressVO rawProviderAddressVO, @RequestParam int  searchFor, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<String, String> targetMap= new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		GridObject jsonResponse = getGridObject.GetGrid(providerService.getRawProvAdrsListBySubprovAdrsId(rawProviderAddressVO.getSubProviderAddressId(),bindingResult, userObject));

		return jsonResponse;
	}	
	
	@RequestMapping(value="/unlinkProviderAddress.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> unlinkFacilities(@RequestParam String  provWorkType, @RequestParam("provAddrsList[]") long[]  provAddrsList, 
			@RequestParam long parentId, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		String commaSeparatedVals = commonUtil.convertToCommaSeparated(provAddrsList);
		providerService.unlinkProviderAddress(provWorkType, commaSeparatedVals, parentId, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/mergeSubProvAddrs.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> mergeSubFacilities(@RequestParam("toBeMergedSubAddrsArray[]") long[]  toBeMergedSubAddrsArray, 
			@RequestParam long selToBeMergedSubAddrs, @RequestParam long selToBeMergedParent, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		toBeMergedSubAddrsArray = ArrayUtils.removeElement(toBeMergedSubAddrsArray, selToBeMergedSubAddrs);
		String commaSeparatedVals = commonUtil.convertToCommaSeparated(toBeMergedSubAddrsArray);
		providerService.mergeSubProvAddrs(commaSeparatedVals, selToBeMergedSubAddrs, selToBeMergedParent, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/mergeSubProviders.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> mergeGrandFacilities(@RequestParam("toBeMergedSubProvArray[]") long[]  toBeMergedSubProvArray, 
			@RequestParam long selToBeMergedSubProv, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		toBeMergedSubProvArray = ArrayUtils.removeElement(toBeMergedSubProvArray, selToBeMergedSubProv);
		String commaSeparatedVals = commonUtil.convertToCommaSeparated(toBeMergedSubProvArray);
		providerService.mergeSubProviders(commaSeparatedVals, selToBeMergedSubProv, bindingResult, userObject);
		return targetMap;
	}

	@RequestMapping("/NewProviderStdznHome")
	public String getNewProviderStdznHome (Map<String, Object> map, HttpServletRequest request) {

		//For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "standardization/provider/ProviderStdznHomeNew";
	}
	
	@RequestMapping(value="/getRawProvAddrsNewList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getRawProvAddrsNewList(HttpServletRequest request, @RequestParam boolean selected, @RequestParam("selectedIds[]") long[] selectedIds){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(providerService.getRawProvAddrsNewList(bindingResult, selected, selectedIds, userObject));
		return jsonResponse;
	}
	
	@RequestMapping(value="/linkRawProvidersToSubProvAddrs.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> linkRawProvidersToSubProvAddrs(@RequestParam("rawProvSelectedAddresses[]") String[]  rawProvSelectedAddresses, @RequestParam long  providerAddressId, @RequestParam String fromNPIRegistry, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		providerService.linkRawProvidersToSubProvAddrs(rawProvSelectedAddresses, providerAddressId, bindingResult, userObject);
		return targetMap;
	}	
	
}
