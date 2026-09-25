package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.provider;
import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.ProviderManualReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProviderManualReviewController {
	@Autowired
	private ProviderManualReviewService providerManualReviewService;
	@Autowired
	GetGridObject getGridObject;
	@RequestMapping(value="/matchRawProviderAddress.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object,Object> matchRawProviderAddress(long newRawProviderAddressId,long matchingRawProviderAddressId,HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		providerManualReviewService.matchRawProviderAddress(newRawProviderAddressId, matchingRawProviderAddressId, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/unMatchRawProvider.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object,Object> unMatchRawProvider(long newRawProviderAddressId,HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		providerManualReviewService.unMatchRawProviderAddress(newRawProviderAddressId, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/getPotentialProviderDetailsListByRawProvider.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getPotentialProviderDetailsListByRawProvider(long rawProviderAddressId,HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(providerManualReviewService.getPotentialProviderDetailsListByRawProvider(rawProviderAddressId, bindingResult, userObject));
		return jsonResponse;
	}
	
	
	@RequestMapping(value="/getClericalReviewRawProviderAddressList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getClericalReviewRawProviderAddressList(HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(providerManualReviewService.getClericalReviewRawProviderAddressDetailsList(bindingResult, userObject));
		return jsonResponse;
	}
	

	@RequestMapping("/getProviderManualReview")
	public String getProviderManualReview (Map<String, Object> map, HttpServletRequest request) {	
		return "standardization/provider/ManualReviewPage";
	}
}
