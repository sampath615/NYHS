package gov.ny.state.doh.bhae.nyehms.web.spring.controller.lab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 *Author		: Hema Madda


 *Date Created 	: 07/30/2014
 *Description 	: This class create the Reports
 */

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.AcceptedTestResultDomainVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LoincResultDomainMappingService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.transaction.TransactionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;


@Controller
public class LabLoincResultDomainMappingController {
	

	@Autowired
	private LoincResultDomainMappingService loincResultDomainMappingService;
	
	@Autowired
	GetGridObject getGridObject;
	

	@Autowired
	private TransactionService transactionService;
	

	@RequestMapping("/addNewLoinc.html")
	public String addNewLoinc(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		
		
		return "reports/addNewLoinc";
	}
	
	@RequestMapping(value="/getLoincDescriptionsForTestType.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<String> getLoincDescriptionsForTestType(HttpServletRequest request, @RequestParam String testType, UserObject userObject){
		
		

		List<String> loincDescriptionDetails =loincResultDomainMappingService.getLoincDescriptionsForTestType(userObject, testType);
		
		return loincDescriptionDetails;
	}
	
	@RequestMapping(value="/getLoincUnitOfMeasurementsForTestType.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<String> getLoincUnitOfMeasurementsForTestType(HttpServletRequest request, @RequestParam String loincDescription, @RequestParam String testType, UserObject userObject) {
		
		

		List<String> loincDescriptionDetails = loincResultDomainMappingService.getLoincUnitOfMeasurementsForTestType(userObject, loincDescription, testType);
		
		return loincDescriptionDetails;
	}
	
	
	@RequestMapping("/getLoincToDomainDetails")
	public String getPersonMerge(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		
		HttpSession session = request.getSession();

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		List<JSONObject> eharsTestResults = loincResultDomainMappingService.getEharsTestResults(bindingResult, userObject);
		
		
		session.setAttribute("eharsTestResults", eharsTestResults);
		
		
		List<String> loincTestTypes = loincResultDomainMappingService.getCompleteLoincTestTypes(userObject);
		
		session.setAttribute("loincTestTypes", loincTestTypes);
		
		List<String> unitsOfMeasurements = loincResultDomainMappingService.getLoincUnitOfMeasurementsForTestType(userObject, null, null);
		
		
		map.put("unitsOfMeasurements", unitsOfMeasurements);
		
		
		return "reports/LoincToDomainDetails";
	}
	
	@RequestMapping("/addTestResults")
	public String addTestResults(Map<String, Object> map, HttpServletRequest request) {
		return "reports/add/addTestResults";
	}
	
	@RequestMapping(value="/getLoincAndDescriptionSearchResults.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<LabLoincMappingVO> getLoincAndDescriptionSearchResults(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labLoincMappingVO") LabLoincMappingVO labLoincMappingVO, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		List<LabLoincMappingVO> loincCodeAndDescriptionDetailsList = loincResultDomainMappingService.getLoincAndDescriptionDetails(bindingResult, userObject, labLoincMappingVO);

		return loincCodeAndDescriptionDetailsList;
	}
	
	@RequestMapping(value="/getAtrDomainsAssoicatedToLoincCode.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getAtrDomainsAssoicatedToLoincCode(Map<String, Object> map, HttpServletRequest request,@RequestParam String loincCode, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		GridObject jsonResponse = getGridObject.GetGrid(loincResultDomainMappingService.getAtrDomainsAssoicatedToLoincCode(loincCode, bindingResult, userObject));

		return jsonResponse;
	}
	
	@RequestMapping(value="/addNewLabLoincDetailsToLookup.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewLabLoincDetailsToLookup(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labLoincMappingVO") LabLoincMappingVO labLoincMappingVO, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		transactionService.addNewLabLoincDetailsToLookup(bindingResult, userObject, labLoincMappingVO);
		
		//loincResultDomainMappingService.addNewLabLoincDetailsToLookup(bindingResult, userObject, loincCode, loincDescription);
		
		return targetMap;
	
	}
	
	
	@RequestMapping(value="/addNewAcceptableTestResults.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewAcceptableTestResults(@ModelAttribute("acceptedTestResultDomainVO") AcceptedTestResultDomainVO acceptedTestResultDomainVO,HttpServletRequest request, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		loincResultDomainMappingService.addNewAcceptableTestResultsAndAssociateToLoincCode(acceptedTestResultDomainVO, bindingResult, userObject);

		return targetMap;
	}
	
	@RequestMapping(value="/updateLoincAndTestResultsDomainStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLoincAndTestResultsDomainStatus(@RequestParam String flagActive, @RequestParam("loincAtrDomainMapId")  long loincAtrDomainMapId,HttpServletRequest request, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		loincResultDomainMappingService.updateLoincAndTestResultsDomainStatus(loincAtrDomainMapId, flagActive, bindingResult, userObject);

		return targetMap;
	}
	
	
	@RequestMapping(value="/getLoincAndUnitsOfMeasurementCombination.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> getLoincAndUnitsOfMeasurementCombination(@RequestParam String loincCd, HttpServletRequest request, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return loincResultDomainMappingService.getLoincAndUnitsOfMeasurementCombination(loincCd, bindingResult, userObject);

	}
	
	
	@RequestMapping(value="/changeLoincMultiSpotDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> changeLoincMultiSpotDocuments(HttpServletRequest request, @RequestParam("documentIds[]")  Integer[] documentIds, @RequestParam String documentType, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		loincResultDomainMappingService.changeLoincMultiSpotDocuments(documentIds, documentType, CommonConstants.RAW_DOCUMENT_EDITED_PROCESS_STATUS,bindingResult, userObject);
		
		return targetMap;
	}

}