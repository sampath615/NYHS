package gov.ny.state.doh.bhae.nyehms.web.spring.controller.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.PerinatalService;
@RequiredArgsConstructor
@Controller
public class PerinatalController {
	
	private final PerinatalService perinatalService;

	@RequestMapping(value="/loadFacilityMonitoringReport")	
	public String loadFacilityMonitoringReport(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
		
		
		
		//perinatalService.loadFacilityMonitoringReport();
		
		return "reports/Perinatal/FacilityMonitoringReport";
	}
	
	
	
	@RequestMapping(value="/getFacilityNamesByBirthCriteria.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<String, String>> getFacilityNamesByBirthCriteria(HttpServletRequest request, 
			@RequestParam String birthFirstYear,
			@RequestParam String birthFirstQuarter,
			@RequestParam String birthSecondYear,
			@RequestParam String birthSecondQuarter
			, UserObject userObject){
		

		Map<String, String> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return perinatalService.getFacilityNamesByBirthCriteria(birthFirstYear, birthFirstQuarter, birthSecondYear, birthSecondQuarter, bindingResult, userObject);

	}
	
	
	
	@RequestMapping(value="/loadPerinatalFacilityReport.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> loadPerinatalFacilityReport(HttpServletRequest request, @RequestParam String birthFirstYear,
			@RequestParam String birthFirstQuarter,
			@RequestParam String birthSecondYear,
			@RequestParam String birthSecondQuarter, @RequestParam String facilityName, @RequestParam String letterDate, UserObject userObject){
		

		Map<String, String> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		 return perinatalService.loadFacilityMonitoringReport(birthFirstYear, birthFirstQuarter, birthSecondYear, birthSecondQuarter, facilityName, letterDate, bindingResult, userObject);
		 

	}
	
}
