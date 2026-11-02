package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.ViralLoadReportsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;



@RequiredArgsConstructor
@Service
public class ViralLoadReportsService {

	private final ViralLoadReportsDAO viralLoadReportsDAO;
	

	
	public List<JSONObject> loadNdvlReportData(BindingResult dummyBindingResult, UserObject userObject){
		return viralLoadReportsDAO.loadNdvlReportData();
	}
	
	public List<JSONObject> getNoAdditionalReportData(String dateRange, BindingResult dummyBindingResult,
			UserObject userObject, Object object){
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		String[] dateRanges = dateRange.split("-");
		
		if(dateRanges != null && dateRanges.length > 1) {
			inputParameterMap.put("p_dateRangeFrom", dateRanges[0]);
			inputParameterMap.put("p_dateRangeTo", dateRanges[1]);
		}else {
			inputParameterMap.put("p_dateRange", dateRange);
		}
		
		return viralLoadReportsDAO.getNoAdditionalReportData(inputParameterMap);
		
		
	}
	
	public List<JSONObject> getVLObxNotes(
			String stateno, BindingResult result,
			UserObject userObject) {

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_stateno", stateno);
		
		return viralLoadReportsDAO.getVLObxNotes(inOutParameterMap);
	}
	
}


