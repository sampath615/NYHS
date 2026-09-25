package gov.ny.state.doh.bhae.nyehms.web.spring.controller.report;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.report.SearchHistoryService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@Controller
public class SearchHistoryController {
	
	@Autowired GetGridObject getGridObject;
	@Autowired SearchHistoryService searchHistoryService;
	
	@RequestMapping(value="/searchHistoryReport")	
	public String getSearchHistoryReport(HttpServletRequest request, Map<String, Object> map) {
		
		Map<String, String> workers = searchHistoryService.getSearchedWorkerNames();
		map.put("workers", workers);
		
		return "reports/SearchHistoryReport";
	}
	
	@RequestMapping(value="/getSearchHistoryReportData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject  getSearchHistoryReportData(@RequestParam String searchType, @RequestParam String fromDate,
							@RequestParam String toDate, @RequestParam int worker, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		Map<Object, Object> dummyMindingMap= new HashMap<Object, Object>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		GridObject jsonResponse = getGridObject.GetGrid(searchHistoryService.getSearchHistoryReportData(fromDate, toDate, worker, dummyBindingResult, userObject));
		
		return jsonResponse;
	}	


}
