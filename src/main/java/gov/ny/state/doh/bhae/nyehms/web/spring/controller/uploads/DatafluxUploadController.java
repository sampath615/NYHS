package gov.ny.state.doh.bhae.nyehms.web.spring.controller.uploads;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads.DatafluxUploadServiceImpl;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

@Controller
public class DatafluxUploadController {
	
	
	@Autowired
	private DatafluxUploadServiceImpl datafluxUploadServiceImpl;
	
	
	
	@RequestMapping(value = "/loadExcelData")
	public String loadExcelData(HttpServletRequest request, Map<String, Object> map) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Checking the Permissions for the user
		
		return "scheduler/loadExcelData";
		
	}
	
	@RequestMapping(value="/uploadDatafluxMatchingData", method = RequestMethod.POST)
	public String getLoincDescriptionsForTestType(HttpServletRequest request, @RequestParam Map<String, String> data, @RequestParam("fileData") MultipartFile fileData) throws ParseException, JsonParseException, JsonMappingException, IOException{
		
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		Map<String, String> targetMap = new HashMap<String, String>();
		
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");

		boolean isErroredOut = datafluxUploadServiceImpl.uploadDatafluxMatchingData(userObject, data, fileData, bindingResult);
		
		
		session.setAttribute("TRANSACTION_RESULT", targetMap.get("TRANSACTION_RESULT"));
		
		session.setAttribute("TRANSACTION_MESSAGE", targetMap.get("TRANSACTION_MESSAGE"));
		
		return "scheduler/loadExcelData";
	}
	
	

	
	@RequestMapping(value="/readFileColumnNames", method = RequestMethod.POST)
	public String  readFileColumnNames(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, HttpServletResponse response, Map<String, Object> map){
		
		Map<String, String> targetMap = new HashMap<String, String>();
		
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		
		List<String> matchingColumnNames =  datafluxUploadServiceImpl.readColumnNamesFromFile(fileData, bindingResult, userObject);
		
		String TRANSACTION_RESULT = (String)targetMap.get("TRANSACTION_RESULT");
				
		if("TRANSACTION_FAILURE".equals(TRANSACTION_RESULT)){
			
			session.setAttribute("TRANSACTION_RESULT", targetMap.get("TRANSACTION_RESULT"));
			
			session.setAttribute("TRANSACTION_MESSAGE", targetMap.get("TRANSACTION_MESSAGE"));
			
		}
		else{
			
			map.put("columnList", matchingColumnNames);
			
			String[] DATAFLUX_MATCH_COLUMNS = EnvironmentProperties.envProperties.getProperty("DATAFLUX_MATCH_COLUMNS").split(",");
			
			map.put("MATCH_COLUMNS_LIST", DATAFLUX_MATCH_COLUMNS);
			
		}
		return "scheduler/loadExcelData";
           		
	}
	
	
}