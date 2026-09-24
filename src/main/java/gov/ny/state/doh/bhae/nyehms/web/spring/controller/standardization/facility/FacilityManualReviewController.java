package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.facility;
import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.FacilityManualReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FacilityManualReviewController {
	@Autowired
	private FacilityManualReviewService facilityManualReviewService;
	@Autowired
	GetGridObject getGridObject;
	@RequestMapping(value="/matchRawFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object,Object> matchRawFacility(long newRawFacilityId,long matchingRawFacilityId,HttpServletRequest request){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		facilityManualReviewService.matchRawFacility(newRawFacilityId, matchingRawFacilityId, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/unMatchRawFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object,Object> unMatchRawFacility(long newRawFacilityId,HttpServletRequest request){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		facilityManualReviewService.unMatchRawFacility(newRawFacilityId, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/getPotentialRawFacList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getPotentialRawFacList(long rawFacilityId,HttpServletRequest request){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(facilityManualReviewService.getFacilityDetailsForManualReview(rawFacilityId, bindingResult, userObject));
		return jsonResponse;
	}

	@RequestMapping("/getFacilityManualReview")
	public String getFacilityManualReview (Map<String, Object> map, HttpServletRequest request) {		
		
		HttpSession session = request.getSession();
		
		return "standardization/facility/ManualReviewPage";
	}
}
