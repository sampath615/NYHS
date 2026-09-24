package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;



public interface ViralLoadReportsService {
	
	public List<JSONObject> loadNdvlReportData(BindingResult dummyBindingResult, UserObject userObject);

	public List<JSONObject> getNoAdditionalReportData(String dateRange, BindingResult dummyBindingResult,
			UserObject userObject, Object object);

	public List<JSONObject> getVLObxNotes(String stateno, BindingResult bindingResult, UserObject userObject);
}