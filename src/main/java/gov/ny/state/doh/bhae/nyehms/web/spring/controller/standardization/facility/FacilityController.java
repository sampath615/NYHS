package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.FacilityService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

@Controller
public class FacilityController {

	@Autowired
	private FacilityService facilityService;
	@Autowired
	GetGridObject getGridObject;
	@Autowired
	CommonUtil commonUtil;

	//controllers for New Page Raw Standard 	

	@RequestMapping("/getFacilityStdznHome")
	public String getFacilityStdznHome (Map<Object, Object> map, HttpServletRequest request) {		
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		//Map<String, String> facilityTypeCodes = facilityService.getFacilityTypeCodes(bindingResult, userObject);
		//map.put("facilityTypeCodes", facilityTypeCodes);
		SubFacilityVO subFacilityVO= new SubFacilityVO();
		map.put("subFacilityVO", subFacilityVO);
		//For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "standardization/facility/NewPageRawStd";
	}
	
	@RequestMapping("/FacilityAddPage")
	public String facilityAddPage(Map<String, Object> map, HttpServletRequest request) {
		
		SubFacilityVO subFacilityVO= new SubFacilityVO();
		map.put("subFacilityVO", subFacilityVO);
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		//Map<String, String> facilityTypeCodes = facilityService.getFacilityTypeCodes(bindingResult, userObject);
		//map.put("facilityTypeCodes", facilityTypeCodes);
		
		//For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "standardization/facility/FacilityAddPage";
	}
	
	@RequestMapping(value="/ChangeRawFacility", method = RequestMethod.POST)
	public String ChangeRawFacility(Map<String, Object> map, HttpServletRequest request) {
		
		SubFacilityVO subFacilityVO= new SubFacilityVO();
		map.put("subFacilityVO", subFacilityVO);
		
		//For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "standardization/facility/ChangeRawFacility";
	}
	
	
	@RequestMapping(value="/unableStandardizeFacilityRecord.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object>  unableStandardizeFacilityRecord(HttpServletRequest request, @RequestParam long recordId, @RequestParam String effectedTable ){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		facilityService.unableStandardizeFacilityRecord(recordId, effectedTable, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	/*
	 * Getting Raw Facilities to display it on New Page 
	 */
	@RequestMapping(value="/getRawSubFacilityList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getRawSubFacilityList(@RequestParam String  pageType, HttpServletRequest request, @RequestParam(value="_search", required = false) boolean _search){

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
		
		GridObject jsonResponse = getGridObject.GetGrid(facilityService.getRawSubFacilityList(bindingResult, pageType, _search, searchField, searchString, searchOper, userObject));

		return jsonResponse;
	}
	
	
	@RequestMapping(value="/addNewStdFacility", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewStdFacility(@ModelAttribute("subFacilityVO") SubFacilityVO subFacilityVO, HttpServletRequest request, BindingResult bindingResult){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		bindingResult=new MapBindingResult(targetMap, "targetMap");

		facilityService.addNewStdFacilityAndRawFacility(subFacilityVO, bindingResult, userObject);

		return targetMap;
	}
	
	@RequestMapping(value="/getSubFacilityInfo.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> getSubFacilityInfo(@RequestParam String facilityId, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult=new MapBindingResult(targetMap, "targetMap");

		Map<String, String> subFacility = facilityService.getSubFacilityInfo(facilityId, bindingResult, userObject);

		return subFacility;
	}
	
	
	
	/*
	 * Creating New Standardization facility
	 */
	@RequestMapping(value="/insertNewStdFacility", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> insertNewStdFacility(@ModelAttribute("subFacilityVO") SubFacilityVO subFacilityVO, @RequestParam long  rawSubFacilityId, HttpServletRequest request, BindingResult bindingResult){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		bindingResult=new MapBindingResult(targetMap, "targetMap");

		facilityService.insertNewStdFacility(subFacilityVO,rawSubFacilityId, bindingResult, userObject);

		return targetMap;
	}
	
	/*
	 * Linking the raw Facility to standard facility
	 */
	@RequestMapping(value="/updateStandardize", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateStandardize(@RequestParam long  subFacilityId, @RequestParam long  rawSubFacilityId, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		facilityService.updateStandardize(rawSubFacilityId, subFacilityId, bindingResult, userObject);

		return targetMap;
	}





	/*
	 * Manual Review Controllers
	 * 
	 * getting the potential matches for the selected raw Facility
	 
	//TODO change logic
	@RequestMapping(value="/getPotentialRawFacList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getPotentialRawFacList(@RequestParam long  rawSubFacilityId, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<MasterFacilityDetailsVO> SubFacilityList = new ArrayList<MasterFacilityDetailsVO>();
		List<Long> rawPotentialMatchIdS = facilityService.getPotentialRawFacList(rawSubFacilityId, bindingResult, userObject, request.getSession().getId());
		if(rawPotentialMatchIdS.size() > 0){
			List<Long> standardIdsPot = facilityService.getStdIdsForRawIds(rawPotentialMatchIdS, bindingResult, userObject, request.getSession().getId());
			SubFacilityList = facilityService.getSubFacListForSubIds(standardIdsPot, bindingResult, userObject, request.getSession().getId());
			session.setAttribute("rawIdsSearchFor", rawPotentialMatchIdS);
		}
		
		GridObject jsonResponse = getGridObject.GetGrid(SubFacilityList);

		return jsonResponse;
	}
	*/

	
	
	
	//controllers for Main Search 	

	@RequestMapping("/getMainSearchPage")
	public String getMainSearchPage (Map<String, Object> map, HttpServletRequest request) {		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		//Map<String, String> facilityTypeCodes = facilityService.getFacilityTypeCodes(bindingResult, userObject);
		//map.put("facilityTypeCodes", facilityTypeCodes);
		SubFacilityVO subFacilityVO= new SubFacilityVO();
		map.put("subFacilityVO", subFacilityVO);
		
		return "standardization/facility/MainSearchPage";
	}


	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getRawListByStdFacilityId.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getRawListByStdFacilityId(@ModelAttribute("rawSubFacilityVO") RawSubFacilityVO rawSubFacilityVO, @RequestParam int  searchFor, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Long> rawIds = new ArrayList<Long>();
		if(searchFor == 3){//search for raw
			//possible raw id's to select from
			rawIds = (List<Long>) session.getAttribute("rawIdsSearchFor");
		}
		
		GridObject jsonResponse = getGridObject.GetGrid(facilityService.getRawListByStdFacilityId(rawSubFacilityVO.getSubFacilityId(), rawIds, searchFor, bindingResult, userObject));

		return jsonResponse;
	}
	
	@RequestMapping(value="/unlinkFacilities.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> unlinkFacilities(@RequestParam String  facWorkType, @RequestParam("facilityList[]") long[]  facilityList, @RequestParam long parentId, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		String commaSeparatedVals = commonUtil.convertToCommaSeparated(facilityList);
		
		facilityService.unlinkFacilities(facWorkType, commaSeparatedVals, parentId, bindingResult, userObject);

		return targetMap;
	}
	
	@RequestMapping(value="/mergeSubFacilities.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> mergeSubFacilities(@RequestParam("toBeMergedSubArray[]") long[]  toBeMergedSubArray, @RequestParam long selToBeMergedSub, @RequestParam long selToBeMergedParent, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		toBeMergedSubArray = ArrayUtils.removeElement(toBeMergedSubArray, selToBeMergedSub);
		String commaSeparatedVals = commonUtil.convertToCommaSeparated(toBeMergedSubArray);
		
		facilityService.mergeSubFacilities(commaSeparatedVals, selToBeMergedSub, selToBeMergedParent, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/mergeGrandFacilities.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> mergeGrandFacilities(@RequestParam("toBeMergedGrandArray[]") long[]  toBeMergedGrandArray, @RequestParam long selToBeMergedGrand, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		toBeMergedGrandArray = ArrayUtils.removeElement(toBeMergedGrandArray, selToBeMergedGrand);
		String commaSeparatedVals = commonUtil.convertToCommaSeparated(toBeMergedGrandArray);
		
		facilityService.mergeGrandFacilities(commaSeparatedVals, selToBeMergedGrand, bindingResult, userObject);
		return targetMap;
	}
	
	
	/*
	 * Adding New Provider and Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param subProviderAndAddressVO the value object which holds the provider address
	 * @param request HTTPRequest reference 
	 * @return Map containing transaction result message
	 */
	@RequestMapping(value="/addRawAndSubFacilityDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addRawAndSubProviderAddressDetails(@RequestParam long masterFacilityId, @ModelAttribute("subFacilityVO") SubFacilityVO subFacilityVO, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		facilityService.addRawAndSubProviderAddressDetails(masterFacilityId, subFacilityVO, bindingResult, userObject);
		return targetMap;
	}
	
	/*
	 * Adding New Provider and Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param subProviderAndAddressVO the value object which holds the provider address
	 * @param request HTTPRequest reference 
	 * @return Map containing transaction result message
	 */
	@RequestMapping(value="/addFacilityDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addProviderDetails(@ModelAttribute("masterFacilityVO")  MasterFacilityVO masterFacilityVO, @ModelAttribute("subFacilityVO") SubFacilityVO subFacilityVO, HttpServletRequest request){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		facilityService.addFacilityDetails(masterFacilityVO, subFacilityVO, bindingResult, userObject);
		return targetMap;
	}
}
