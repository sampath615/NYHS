package gov.ny.state.doh.bhae.nyehms.web.spring.controller.lab; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftDetailsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.ProviderService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

/*
 *Author		: Sampath Anumolu

 *Date Created 	: 02/12/2015
 *Description 	: This class used to Load the Document Errors and fixing it
 */

@Controller
public class LaftDetailsController {
	@Autowired
	GetGridObject getGridObject;

	@Autowired
	private LaftDetailsService laftDetailsService;
	
	@Autowired
	private ProviderService providerService;
	
	/*
	 * Redirect to provider, facility, person search page 
	 */
	@RequestMapping("/getProvFacSearchGridPage")
	public String getProvFacSearchGridPage(Map<String, Object> map, HttpServletRequest request) {
		
		return "lab/ProvFacSearchPage";
	}
	

	
	/**
	 * Loading the Document Errors page. In this page will show the Documents which got 
	 * failed while Validating the Lab document(Validation will fail which any mismatches with the existing data like Loinc, Accepted test result differences etc) 
	 */
	@RequestMapping("/getLabErrors")
	public String getLabErrors(Map<String, Object> pMap) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		
		pMap.put("addressFieldVO", addressFieldVO);
		
		pMap.put("hasProvRefData", "Y");

		return "lab/LabErrors";
		
	}
	
	@RequestMapping(value="/getMasterProvRecords.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getMasterProvRecords(HttpServletRequest request){
		
		return providerService.getMasterProvRecords();
		
	}
	
	@RequestMapping(value="/getMasterProvData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONObject getMasterProvData(HttpServletRequest request, @RequestParam String providerId){
		
		return providerService.getMasterProvData(providerId);
		
	}
	
	

	
	
	/**
	 * Loading the Document Errors page. 
	 * This page shows Documents which failed validation and are flagged deleted. 
	 */
	@RequestMapping("/getLabDeletedErrors")
	public String getLabDeletedErrors(Map<String, Object> pMap) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		pMap.put("addressFieldVO", addressFieldVO);
		return "lab/LabDeletedErrors";	
	}
	
		
	
	@RequestMapping("/manageAddresses")
	public String manageAddresses(Map<String, Object> pMap) {
		
		return "lab/manageAddress";
		
	}
	
	
	
	/**
	 * Loading the HED details page. In this page will load all the documents related Standardized, Holded, NO MATCH, CLERICAL REVIEW .
	 * 
	 * We can do Hold, Unhold, Delete, Edit document(s) in this page
	 * @return
	 */
	@RequestMapping("/getHEDDetails")
	public String getHEDDetails() {
		return "lab/HEDDetails";
		
	}
	
	
	
	/**
	 * Loading the Lab Document Ids which got failed in Validation process 
	 * @param request Request Object
	 * @return List of Document Ids
	 */
	@RequestMapping(value="/loadValidationFailedLabDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject loadValidationFailedLabDocuments(@RequestParam String documentType, HttpServletRequest request, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		GridObject jsonResponse = getGridObject.GetGrid(laftDetailsService.getValidationFailedLabDocumentDetails(documentType, bindingResult, userObject));
		
		return jsonResponse;
	}
	
	
	/**
	 * Loading the Lab Document Ids which failed the validation process and were flagged deleted 
	 * @param request Request Object
	 * @return List of Document Ids
	 */
	@RequestMapping(value="/loadValidationFailedDeletedLabDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject loadValidationFailedDeletedLabDocuments(@RequestParam String documentType, HttpServletRequest request, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		GridObject jsonResponse = getGridObject.GetGrid(laftDetailsService.getValidationFailedDeletedLabDocumentDetails(documentType, bindingResult, userObject));
		
		return jsonResponse;
	}
	
	
	
	
	
	
	@RequestMapping(value="/updateLabNotes.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  Map<Object, Object> updateLabNotes(HttpServletRequest request, @RequestParam long id, @RequestParam String notes, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		laftDetailsService.updateLabNotes(id, notes, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/byPassDocumentError.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  Map<Object, Object> byPassDocumentError(HttpServletRequest request, @RequestParam String documentId, @RequestParam String errorCodes, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		laftDetailsService.byPassDocumentError(documentId, errorCodes, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	
	/**
	 * This method will get the list of documents which are in Standardized, Holded, NO MATCH, CLERICAL REVIEW status.
	 * @param request
	 * @return list of documents
	 */
	@RequestMapping(value="/loadHEDDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject loadHEDDetails(HttpServletRequest request, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		GridObject jsonResponse = getGridObject.GetGrid(laftDetailsService.getHEDStatusDocuments(bindingResult, userObject));
		
		return jsonResponse;
	}
	
	
	/**
	 * This method is used to update the Document status. Will use this method in Lab Errors, Lab Deleted Errors and Lab HED details pages.
	 * 
	 * In Lab Errors page we can do Delete, Edit and Reprise the document(s)
	 * In Lab Deleted Errors page we can Undelete the documents(s)
	 * In HED Details page we can do Delete, Edit, Hold and Unhold the document(s)
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateDocumentsStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateDocumentsStatus(HttpServletRequest request, @RequestParam("documentIds[]")  long[] documentIds, @RequestParam(value="comments", required = false) String comments, @RequestParam String documentType, @RequestParam String actionName, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		laftDetailsService.updateSelectedDocumentStatus(documentIds, comments, actionName, documentType, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/addNotesToProcessErrors.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  Map<Object, Object> addNotesToProcessErrors(HttpServletRequest request, @RequestParam("documentIds[]")  long[] documentIds, @RequestParam(value="notes", required = false) String notes, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		laftDetailsService.addNotesToProcessErrors(documentIds, notes, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/updateUnitsOfMeasure.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  Map<Object, Object> updateUnitsOfMeasure(HttpServletRequest request, @RequestParam("documentIds[]")  int[] documentIds, @RequestParam(value="units", required = false) String units, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		laftDetailsService.updateUnitsOfMeasure(documentIds, units, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	
	@RequestMapping(value="/updateProviderAddress.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  Map<Object, Object> updateProviderAddress(HttpServletRequest request, @RequestParam("documentIds[]")  int[] documentIds,
			@ModelAttribute("addressFieldVO") AddressFieldVO addressFieldVO,
			@RequestParam(value="firstName", required = false) String firstName,
			@RequestParam(value="lastName", required = false) String lastName,
			@RequestParam(value="middleName", required = false) String middleName,
			@RequestParam(value="suffix", required = false) String suffix,
			@RequestParam(value="phone", required = false) String phone,
			@RequestParam(value="npi", required = false) String npi, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		laftDetailsService.updateProviderAddress(documentIds, addressFieldVO, firstName, lastName, middleName, suffix, phone, npi, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/updateRawLabAddressDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  Map<Object, Object> updateRawLabAddressDetails(HttpServletRequest request, @RequestParam("documentIds[]")  int[] documentIds,
			@ModelAttribute("addressFieldVO") AddressFieldVO addressFieldVO, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		laftDetailsService.updateRawLabAddressDetails(documentIds, addressFieldVO, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/getAddressSameAsEntries.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  List<JSONObject> getAddressSameAsEntries(HttpServletRequest request, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return laftDetailsService.getAddressSameAsEntries(bindingResult, userObject);
		
	}	
	
	
	
	@RequestMapping(value="/addRawProviderFacilityAddressSameAsResidence.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  Map<Object, Object> addRawProviderFacilityAddressSameAsResidence(HttpServletRequest request, @RequestParam String rawAddress, @RequestParam String addressFilterType, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		laftDetailsService.addRawProviderFacilityAddressSameAsResidence(rawAddress, addressFilterType, bindingResult, userObject);
		
		return targetMap;
		
	}
	
	
	@RequestMapping(value="/updateAddressSameAsStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  Map<Object, Object> updateAddressSameAsStatus(HttpServletRequest request, @RequestParam String id, @RequestParam String FLAG_ACTIVE, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		laftDetailsService.updateAddressSameAsStatus(id, FLAG_ACTIVE, bindingResult, userObject);
		
		return targetMap;
		
	}
		
	
	@RequestMapping("/getErrorReportsByLab")
	public String getErrorReportsByLab(Map<String, Object> map, HttpServletRequest request) {
		
		
		map.put("category", "FACILITY");
		
		
		return "reports/LabErrorsReport";
	}
	
	@RequestMapping("/getErrorReportsByType")
	public String getErrorReportsByType(Map<String, Object> map, HttpServletRequest request) {
		
		map.put("category", "ERROR");
		
		return "reports/LabErrorsReport";
	}
	
	@RequestMapping("/getErrorReportsByStatus")
	public String getErrorReportsByStatus(Map<String, Object> map, HttpServletRequest request) {
		
		map.put("category", "reportByStatus");
		
		return "reports/LabErrorsReport";
	}
	
	@RequestMapping("/getErrorStatusReportsByLab")
	public String getErrorStatusReportsByLab(Map<String, Object> map, HttpServletRequest request) {
		
		map.put("category", "statusReportByLab");
		//LabErrorStatusReportByLab
		return "reports/LabErrorsReport";
	}

	
	@RequestMapping(value="/getlabErrorReports.html", method = RequestMethod.POST)
	public String  getlabErrorReports(@RequestParam String category, @RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();

		BindingResult dummyBindingResult = BindingResults.newDummy();

		
		if("ERROR".equals(category)){
			Map<String, Map<String, Map<String, Integer>>> errorReportsByCategory =  laftDetailsService.getLabErrorReportsByType(dummyBindingResult, userObject);
			
			map.put("errorReportsByCategory", errorReportsByCategory);
			
			return "reports/labErrors/errorReportsByDocument";
			
		}else if("FACILITY".equals(category)){
		
			List<Map<String, Object>> errorReportsByFacilityName =  laftDetailsService.getLabErrorReportsByFacilityName(dummyBindingResult, userObject);
			
			map.put("errorReportsByFacilityName", errorReportsByFacilityName);
			
			return "reports/labErrors/errorReportsByFacilityName";
		}else if("reportByStatus".equals(category)){

			Map<String, Map<String, Map<String, Map<String, Integer>>>> errorReportsByStatus =  laftDetailsService.getLabErrorReportsByStatus(fromDate, toDate, map, dummyBindingResult, userObject);
			
			map.put("errorReportsByStatus", errorReportsByStatus);
			
			return "reports/labErrors/errorReportsByStatus";	
		}else{
			
			
			List<Map<String, Object>> errorStatusReportByLab = laftDetailsService.getLabErrorStatusReportByLab(fromDate, toDate, dummyBindingResult, userObject);
			
			map.put("errorStatusReportByLab", errorStatusReportByLab);
			
			
			return "reports/labErrors/errorStatusReportByLab";	
		}
	}
	
	@RequestMapping(value="/getDocumentLabErrorsByCategory.html", method = RequestMethod.POST)
	public String  getDocumentLabErrorsByCategory(@RequestParam String documentId, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();

		BindingResult dummyBindingResult = BindingResults.newDummy();

		List<Map<String, String>> labProcessErrors = laftDetailsService.getDocumentLabErrorsByCategory(documentId, dummyBindingResult, userObject);
			
		map.put("labProcessErrors", labProcessErrors);
		
		return "reports/labErrors/errorsByCategory";	
		
	}
	
	
	

	
	@RequestMapping(value="/generateLabErrorReport.xlsx", method = RequestMethod.GET)
	public ModelAndView generateLabErrorReport(
			@RequestParam String category, @RequestParam String fromDate, @RequestParam String toDate, 
			HttpServletRequest request, 
			HttpServletResponse response,
			Map<String, Object> map, UserObject userObject)
	{
		
		Cookie myCookie =
      		  new Cookie("filedownload", "false");
		
		response.addCookie(myCookie);
		
		boolean isError = false;
		
		ModelAndView mav = null;
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		if("ERROR".equals(category)){
			Map<String, Map<String, Map<String, Integer>>> errorReportsByCategory =  laftDetailsService.getLabErrorReportsByType(bindingResult, userObject);


			if(errorReportsByCategory != null && errorReportsByCategory.size() > 0 ){
			
				mav = new ModelAndView("generateLabErrorReportByErrorCode");
				
		        mav.addObject("errorReportsByCategory", errorReportsByCategory);
		        
				
			}else{
				isError = true;
			}
			
			
			
			
		}else if("FACILITY".equals(category)){
		
			List<Map<String, Object>> errorReportsByFacilityName =  laftDetailsService.getLabErrorReportsByFacilityName(bindingResult, userObject);
			
			
	        if(errorReportsByFacilityName != null && errorReportsByFacilityName.size() > 0 ){
				
	        	mav = new ModelAndView("generateLabErrorReportByFacility");
				
		        mav.addObject("errorReportsByFacilityName", errorReportsByFacilityName);
				
			}else{
				isError = true;
			}
			
		}else if("reportByStatus".equals(category)){
		
			Map<String, Map<String, Map<String, Map<String, Integer>>>> errorReportsByStatus =  laftDetailsService.getLabErrorReportsByStatus(fromDate, toDate, map, bindingResult, userObject);
			
			
	        if(errorReportsByStatus != null && errorReportsByStatus.size() > 0 ){
				
	        	mav = new ModelAndView("generateLabErrorReportByStatus");
				
		        mav.addObject("errorReportsByStatus", errorReportsByStatus);
		        		        
		        mav.addObject("weeklyTotalByUser", map.get("weeklyTotalByUser"));
		        
		        mav.addObject("workerNames", map.get("workerNames"));
		        
		        mav.addObject("totalStatusCountByUser", map.get("totalStatusCountByUser"));
				
			}else{
				isError = true;
			}
			
		}else{
			List<Map<String, Object>> errorStatusReportByLab = laftDetailsService.getLabErrorStatusReportByLab(fromDate, toDate, bindingResult, userObject);
			
			if(errorStatusReportByLab != null && errorStatusReportByLab.size() > 0 ){
				
	        	mav = new ModelAndView("generateLabErrorStatusReportByFacility");
				
		        mav.addObject("errorStatusReportByLab", errorStatusReportByLab);
				
			}else{
				isError = true;
			}

		}
			
		
		
		Cookie myCookie2 = null;
		
		Cookie myCookie1 = null;
				
		if(!isError){
			
			mav.addObject("fromDate", fromDate);
			
			mav.addObject("toDate", toDate);
			
			
			myCookie1 = new Cookie("TRANSACTION_MESSAGE", "Successfully generated the Error Report.");
			


	        myCookie2 =
	        		  new Cookie("TRANSACTION_RESULT", AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName());
	        
	        
		}else{
			
			
			myCookie1 = new Cookie("TRANSACTION_MESSAGE", "There is no data to generate the Error Report.");
			


	        myCookie2 =
	        		  new Cookie("TRANSACTION_RESULT", AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());	 
	        

			map.put("category", category);
	        
			mav = new ModelAndView("reports/LabErrorsReport");
			
	        
		}
		
        myCookie =
        		  new Cookie("filedownload", "true");
        		
        
        response.addCookie(myCookie);
        

        response.addCookie(myCookie1);
        

        response.addCookie(myCookie2);
        
        return mav;
		
	}

}
