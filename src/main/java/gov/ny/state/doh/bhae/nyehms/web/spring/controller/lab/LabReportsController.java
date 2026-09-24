package gov.ny.state.doh.bhae.nyehms.web.spring.controller.lab;

import java.util.ArrayList;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/*
 *Author		: Hema Madda

 *Date Created 	: 07/30/2014
 *Description 	: This class create the Reports
 */

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.ClinicalLaboratoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FeedbackReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabFacilitiesVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabInstrumentDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabInteractionHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLocalCodesMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabNewContactVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabOpsReportsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabPermitsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabSubmissionTypeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabTestMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.LabDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LabReportsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.ProviderService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.transaction.TransactionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;


@Controller
public class LabReportsController {
	

	@Autowired
	private LabReportsService reportsService;
	
	@Autowired
	GetGridObject getGridObject;

	@Autowired
	private ProviderService providerService;
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private LabDocumentService labDocumentService; 

	
	@RequestMapping("/getReports")
	public String getPersonMerge(Map<String, Object> map, HttpServletRequest request) {
		return "reports/Reports";
		
	}
	
	@RequestMapping("/getLabFacilityDetailsPage")
	public String getLabFacilityDetailsPage(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi, @RequestParam String clia) {
		
		LabFacilitiesVO labFacilitiesVO = new LabFacilitiesVO();
		
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		JSONObject physicalFacilityDetails = reportsService.geLabPhysicalFacilityDetails(pfi, clia, bindingResult, userObject);
		
		map.put("physicalFacilityDetails",physicalFacilityDetails);

		map.put("labFacilitiesVO", labFacilitiesVO);
		
		return "reports/LabFacility";
		
	}
	
	@RequestMapping("/getLastImportedEclrsDate")
	public @ResponseBody String getLastImportedEclrsDate(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		String lastEcrlsImportDate = reportsService.getlastEcrlsImportDate(pfi, bindingResult, userObject);
		
		if (lastEcrlsImportDate.isEmpty()) {
			lastEcrlsImportDate = "Not Found";
		}
		
		return lastEcrlsImportDate;
		
	}
	
	@RequestMapping("/addLabFacility")
	public String addLabFacility(Map<String, Object> map, HttpServletRequest request) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		return "reports/add/addLabFacility";
		
	}
	
	@RequestMapping("/addFacilityToClia")
	public String facilityDetails(Map<String, Object> map, HttpServletRequest request) {
		return "reports/add/facilityDetails";
		
	}
	
	@RequestMapping("/addSubmissionType")
	public String addSubmissionType(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {
		
		String transactionResult = "";
		
		String transactionMessage = "";
		JSONObject submissionTypes = reportsService.getSubmissionTypes(pfi);
		
		map.put("submissionTypes", submissionTypes);
		
		
		if(submissionTypes.size() == 0){
			transactionResult = "Failure";
			transactionMessage = "No Submission data types available to add.";
		}else{
			transactionResult = "Success";
			
		}
		
		map.put("transactionResult", transactionResult);
		map.put("transactionMessage", transactionMessage);
		
		JSONObject labLisVendors = reportsService.getLabLisVendors();
		map.put("labLisVendors", labLisVendors);
		
		return "reports/add/addSubmissionType";
		
	}
	
	
	@RequestMapping("/addLabPermit")
	public String addLabPermit(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {
		
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Map<String, String>> permitCodeAndDescriptionMap = reportsService.getActiveAssociatedPermitCodeAndDescriptionDetails(pfi, bindingResult, userObject);
		
		LabPermitsVO labPermitVO = new LabPermitsVO();
		
		map.put("labPermitVO", labPermitVO);
		
		
		map.put("permitCodeAndDescriptionMap", permitCodeAndDescriptionMap);
		
		return "reports/addLabPermit";
		
	}
	
	@RequestMapping("/editLabPermit")
	public String editLabPermit(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("assignReassignVO") LabPermitsVO labPermitVO) {
		
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Map<String, String>> permitCodeAndDescriptionMap = reportsService.getPermitCodesAndDescriptionsForEdit(bindingResult, userObject);
		
		map.put("labPermitVO", labPermitVO);
		
		map.put("permitCodeAndDescriptionMap", permitCodeAndDescriptionMap);
		
		List<Map<String, String>> vlDetectionLevelsCodesAndDescriptionsMap = reportsService.getVLDetectionLevelsCodesAndDescriptions(bindingResult, userObject);
		map.put("vlDetectionLevelsCodesAndDescriptionsMap", vlDetectionLevelsCodesAndDescriptionsMap);

		List<Map<String, String>> testKitCodesAndDescriptionsMap = reportsService.getTestKitCodesAndDescriptions(bindingResult, userObject);
		map.put("testKitCodesAndDescriptionsMap", testKitCodesAndDescriptionsMap);

		List<Map<String, String>> instrumentsCodesAndDescriptionsMap = reportsService.getInstrumentCodesAndDescriptions(bindingResult, userObject);
		map.put("instrumentsCodesAndDescriptionsMap", instrumentsCodesAndDescriptionsMap);

		return "reports/editLabPermit";
		
	}
	
	@RequestMapping("/addLabCommunication")
	public String addLabCommunication(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {	
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");	
		
		List<JSONObject> communicationTypes = reportsService.getInteractionContactTypes(bindingResult, userObject);		
		map.put("communicationTypes", communicationTypes);
		
		LabInteractionHistoryVO labInteractionHistoryVO = new LabInteractionHistoryVO();
		map.put("labInteractionHistoryVO", labInteractionHistoryVO);
	
		return "reports/addLabCommunication";		
	}
	
	@RequestMapping("/updateLabCommunication")
	public String updateLabCommunication(Map<String, Object> map, HttpServletRequest request, @RequestParam String labInteractionId) {
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");	
		
		List<JSONObject> communicationTypes = reportsService.getInteractionContactTypes(bindingResult, userObject);		
		map.put("communicationTypes", communicationTypes);
		
		LabInteractionHistoryVO labInteractionHistoryVO = reportsService.getLabCommunication(labInteractionId, bindingResult, userObject);
		map.put("labInteractionHistoryVO", labInteractionHistoryVO);
	
		return "reports/updateLabCommunication";
	}
	
	
	@RequestMapping("/addLabInstrument")
	public String addLabInstrument(Map<String, Object> map, HttpServletRequest request, @RequestParam long labPermitId, @RequestParam String permitDescription, @RequestParam String permitCode) {
		
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		List<Map<String, String>> instrumentCodeAndDescriptionMap = reportsService.getActiveAssociatedInstrumentCodeAndDescriptionDetails(labPermitId, bindingResult, userObject);
		
		map.put("instrumentCodeAndDescriptionMap", instrumentCodeAndDescriptionMap);
		
		map.put("currentSelectedPermitCodeDescription", permitDescription);
		map.put("currentSelectedPermitCode", permitCode);
		
		LabInstrumentDetailsVO labInstrumentDetailsVO = new LabInstrumentDetailsVO();
		
		map.put("labInstrumentDetailsVO", labInstrumentDetailsVO);
		
		return "reports/addLabInstrument";
		
	}
	
	
	@RequestMapping("/addLabLoinc")
	public String addLabLoinc(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {
		
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, String> loincCodeAndDescriptionMap = reportsService.getActiveAndNonAssociatedLabLoincDescriptionDetails(pfi, bindingResult, userObject);		
		LabLoincMappingVO  labLoincMappingVO  = new LabLoincMappingVO();		
		map.put("labLoincMappingVO", labLoincMappingVO);	
		map.put("loincCodeAndDescriptionMap", loincCodeAndDescriptionMap);
		
		List<Map<String, String>> vlDetectionLevelsCodesAndDescriptionsMap = reportsService.getVLDetectionLevelsCodesAndDescriptions(bindingResult, userObject);
		map.put("vlDetectionLevelsCodesAndDescriptionsMap", vlDetectionLevelsCodesAndDescriptionsMap);
		
		List<Map<String, String>> testKitCodesAndDescriptionsMap = reportsService.getTestKitCodesAndDescriptions(bindingResult, userObject);
		map.put("testKitCodesAndDescriptionsMap", testKitCodesAndDescriptionsMap);

		List<Map<String, String>> instrumentsCodesAndDescriptionsMap = reportsService.getInstrumentCodesAndDescriptions(bindingResult, userObject);
		map.put("instrumentsCodesAndDescriptionsMap", instrumentsCodesAndDescriptionsMap);		
		
		return "reports/addLabLoinc";
		
	}
	
	
	@RequestMapping("/editLabLoinc")
	public String editLabLoinc(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labLoincMappingVO") LabLoincMappingVO labLoincMappingVO) {
		
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, String> loincCodeAndDescriptionMap = reportsService.getActiveAssociatedLabLoincDescriptionDetails(labLoincMappingVO.getPfi(), bindingResult, userObject);		
		map.put("labLoincMappingVO", labLoincMappingVO);	
		map.put("loincCodeAndDescriptionMap", loincCodeAndDescriptionMap);
		
		List<Map<String, String>> vlDetectionLevelsCodesAndDescriptionsMap = reportsService.getVLDetectionLevelsCodesAndDescriptions(bindingResult, userObject);
		map.put("vlDetectionLevelsCodesAndDescriptionsMap", vlDetectionLevelsCodesAndDescriptionsMap);
		

		List<Map<String, String>> testKitCodesAndDescriptionsMap = reportsService.getTestKitCodesAndDescriptions(bindingResult, userObject);
		map.put("testKitCodesAndDescriptionsMap", testKitCodesAndDescriptionsMap);

		List<Map<String, String>> instrumentsCodesAndDescriptionsMap = reportsService.getInstrumentCodesAndDescriptions(bindingResult, userObject);
		map.put("instrumentsCodesAndDescriptionsMap", instrumentsCodesAndDescriptionsMap);		
		
		return "reports/editLabLoinc";
		
	}
	
	@RequestMapping("/addLabLocalCode")
	public String addLabLocalCode(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, String> loincCodeAndDescriptionMap = reportsService.getActiveAssociatedLabLoincDescriptionDetails(pfi, bindingResult, userObject);
		
		List<Long> selectedLoincCodesOfLocalCodes = null;
		
		if(request.getParameter("localCodeMapId") != null){
			long localCodeMapId = Long.parseLong(request.getParameter("localCodeMapId")); 
			selectedLoincCodesOfLocalCodes = reportsService.getSelectedLoincCodesOfLocalCode(localCodeMapId, bindingResult, userObject);
		}
		map.put("selectedLoincCodesOfLocalCodes", selectedLoincCodesOfLocalCodes);
		
		
		LabLoincMappingVO  labLoincMappingVO  = new LabLoincMappingVO();
		
		map.put("labLoincMappingVO", labLoincMappingVO);
		
		map.put("loincCodeAndDescriptionMap", loincCodeAndDescriptionMap);
		
		
		
		return "reports/addLabLocalCode";
		
	}
	
	@RequestMapping("/addLabLocalTestCode")
	public String addLabLocalTestCode(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		//Map<String, String> testCodeAndDescriptionMap = reportsService.getActiveAssociatedLabLoincDescriptionDetails(pfi, bindingResult, userObject);
		
		List<Long> selectedLoincCodesOfLocalCodes = null;
		
		if(request.getParameter("localCodeMapId") != null){
			long localCodeMapId = Long.parseLong(request.getParameter("localCodeMapId")); 
			selectedLoincCodesOfLocalCodes = reportsService.getSelectedLoincCodesOfLocalCode(localCodeMapId, bindingResult, userObject);
		}
		map.put("selectedLoincCodesOfLocalCodes", selectedLoincCodesOfLocalCodes);
		
		
		LabTestMappingVO labTestMappingVO  = new LabTestMappingVO();
		
		map.put("labTestMappingVO", labTestMappingVO);
		
		//map.put("testCodeAndDescriptionMap", testCodeAndDescriptionMap);
			
		return "reports/addLabLocalTestCode";
		
	}

	
	/*
	@RequestMapping("/editLabLocalCode")
	public String editLabLocalCode(Map<String, Object> map, HttpServletRequest request, @RequestParam String localCodeMapId, @RequestParam long pfi) {
		
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		//Map<String, String> loincCodeAndDescriptionMap = reportsService.getActiveAssociatedLabLoincDescriptionDetails(pfi, bindingResult, userObject);
		
		
		List<LabLocalCodesMappingVO> labLocalCodeDetailsList = (List<LabLocalCodesMappingVO>)session.getAttribute("currentLabLocalCodeDetails");
		
		//List<LabLoincMappingVO> associatedLoincMappingDetailsList = getLabLoincDetailsOfLocalCodeFromList(labLocalCodeDetailsList, localCodeMapId);
		
		LabLoincMappingVO  labLoincMappingVO  = new LabLoincMappingVO();
		
		map.put("labLoincMappingVO", labLoincMappingVO);
		
		Map<String, String> loincCodeAndDescriptionMap = reportsService.getActiveAssociatedLabLoincDescriptionDetails(pfi, bindingResult, userObject);
		
		map.put("loincCodeAndDescriptionMap", loincCodeAndDescriptionMap);
		
		return "reports/addLabLocalCode";
		
	}*/
	
	
	@RequestMapping(value="/getFeedbackMedicalReport.html", method = RequestMethod.POST)
	public String getFeedbackMedicalReport(@RequestParam String  providerId,@RequestParam String  startDate, 
			@RequestParam String  endDate, HttpServletRequest request, Map<String, Object> map){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		

		FeedbackReportVO feedbackReportVO = reportsService.getFeedbackMedicalReport(providerId, startDate, endDate, bindingResult, userObject);
		
		map.put("feedbackReportVO", feedbackReportVO);
		
		return "reports/MedicalProviderFeedback";
	}


	@RequestMapping("/getWorkerMetricsReport")
	public String getWorkerMetricsReport(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return "reports/workerMetrics/workerMetrics";
		
	}


//	@RequestMapping(value="/getLabReport", method = RequestMethod.POST)
//	public String getLabReport(@RequestParam String  workerId,@RequestParam String  startDate, 

	
	@RequestMapping(value="/getMatchedProviderDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getMatchedProviderDetails(Map<String, Object> map, HttpServletRequest request,@RequestParam  String providerName) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		GridObject jsonResponse = getGridObject.GetGrid(providerService.getActiveSubProviders(bindingResult, userObject, providerName));

		return jsonResponse;
		
		
	}
	
	
	@RequestMapping(value="/changeCliaForPfi.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> changeCliaForPfi(Map<String, Object> map, HttpServletRequest request,@RequestParam  String clia, @RequestParam String pfi) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		reportsService.changeCliaForPfi(bindingResult, userObject, clia, pfi);

		return targetMap;
		
		
	}
	
	@RequestMapping(value="/getLoincsForPfi.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<String> getLoincsForPfi(Map<String, Object> map, HttpServletRequest request,@RequestParam  String pfi) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return reportsService.getLoincsForPfi(bindingResult, userObject, pfi);
		
		
	}
	
	
	@RequestMapping(value="/getLoincReferenceValues.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONObject getLoincReferenceValues(Map<String, Object> map, HttpServletRequest request,@RequestParam  String loincCode) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return reportsService.getLoincReferenceValues(bindingResult, userObject, loincCode);
		
		
	}
	
	
	
	
	
	
	@RequestMapping("/getLabReports")
	public String getLabReports(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		
		LabFacilitiesVO labFacilitiesVO = new LabFacilitiesVO();
		map.put("labFacilitiesVO", labFacilitiesVO);
		
		
		JSONObject approvalStatusJsonObj = reportsService.getApprovalStatus(bindingResult, userObject);
		
		//containing Reporting Status Map details(Key/Value) 
		session.setAttribute("reportingStatusDetails", approvalStatusJsonObj);
		
		JSONObject pfiContactTypeObj = reportsService.getPFIContactTypeMap(bindingResult, userObject);
		session.setAttribute("pfiContactTypeDetails", pfiContactTypeObj);
		
		session.setAttribute("currentWorkerId", userObject.getWorkerId());
		
		session.setAttribute("labApprovalStatusMap", reportsService.getLabApprovalStatusMap(bindingResult, userObject));
		
		session.setAttribute("refersDTATestingToList", reportsService.getRefersDTATestingToMap(bindingResult, userObject));
		
		return "reports/LabReports";
		
	}
	
	
	
	@RequestMapping(value="/getClinicalLaboratoriesWithFacilities.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getClinicalLaboratoriesWithFacilities(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labFacilitiesVO") LabFacilitiesVO labFacilitiesVO, @RequestParam String clia) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<ClinicalLaboratoryVO> labSearchResult = reportsService.getLabSearchResults(bindingResult, userObject, clia, labFacilitiesVO);
				
		GridObject jsonResponse = getGridObject.GetGrid(labSearchResult);
		
		return jsonResponse;
	
	}

	
	@RequestMapping(value="/getLabDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody LabDetailsVO getLabDetails(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		LabDetailsVO labDetailsVO = reportsService.getLabDetails(bindingResult, userObject, pfi);
		
		
		//Map<String, List<String>> permitAndInstrumentMap = getPermitAndInstrumentDetails(labDetailsVO.getLabPermitsVO());
		
		session.setAttribute("currentLabLoincCodeDetails", labDetailsVO.getLabLoincMappingVO());
		
		session.setAttribute("currentLabLocalCodeDetails", labDetailsVO.getLabLocalCodesMappingVO());
		
		//map.put("permitAndInstrumentMap", permitAndInstrumentMap);
		
		return labDetailsVO;

	}

	@RequestMapping(value="/updateLabPermitDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabPermitDetails(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labPermitsVO") LabPermitsVO labPermitsVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		reportsService.updateLabPermitDetails(bindingResult, userObject, labPermitsVO);

		return targetMap;

	}
	
	
	@RequestMapping(value="/addNewContactToFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewContactToFacility(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labNewContactVO") LabNewContactVO labNewContactVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		int rowId = reportsService.addNewContactToFacility(bindingResult, userObject, labNewContactVO);
		
		targetMap.put("ROW_ID", rowId);

		return targetMap;
		
		
	}
	
	@RequestMapping(value="/addNewSubmissionTypeToFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewSubmissionTypeToFacility(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labSubmissionTypeVO") LabSubmissionTypeVO labSubmissionTypeVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		int rowId = reportsService.addNewSubmissionTypeToFacility(bindingResult, userObject, labSubmissionTypeVO);
		
		targetMap.put("ROW_ID", rowId);

		return targetMap;
		
		
	}
	
	@RequestMapping(value="/updateLabSubmissionTypeDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabSubmissionTypeDetails(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labSubmissionTypeVO") LabSubmissionTypeVO labSubmissionTypeVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		reportsService.updateLabSubmissionTypeDetails(bindingResult, userObject, labSubmissionTypeVO);
		

		return targetMap;
		
		
	}
	
	@RequestMapping(value="/updateLabContactDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabContactDetails(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labNewContactVO") LabNewContactVO labNewContactVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		reportsService.updateLabContactDetails(bindingResult, userObject, labNewContactVO);
		

		return targetMap;
		
		
	}
	
	@RequestMapping(value="/updateLabCommunicationDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabCommunicationDetails(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labInteractionHistoryVO") LabInteractionHistoryVO labInteractionHistoryVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		reportsService.updateLabCommunicationDetails(bindingResult, userObject, labInteractionHistoryVO);
		
		return targetMap;
	}
	
	@RequestMapping(value="/loadPFIContacts.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> loadPFIContacts(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		List<JSONObject> pfiContactDetails = reportsService.getLabContactsForPFI(pfi, bindingResult, userObject);
		

		return pfiContactDetails;
		
		
	}
	
	@RequestMapping(value="/loadPFISubmissionTypesDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> loadPFISubmissionTypesDetails(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		List<JSONObject> pFISubmissionTypesDetails = reportsService.getsubmissionDataTypesForPFI(pfi, bindingResult, userObject);
		

		return pFISubmissionTypesDetails;
		
		
	}

	
	

	@RequestMapping(value="/updateLabInstrumentDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabInstrumentDetails(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labInstrumentDetailsVO") LabInstrumentDetailsVO labInstrumentDetailsVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		reportsService.updateLabInstrumentDetails(bindingResult, userObject, labInstrumentDetailsVO);

		return targetMap;

	}
	
	
	@RequestMapping(value="/updateLabLoincCodeDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabLoincCodeDetails(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labLoincMappingVO") LabLoincMappingVO labLoincMappingVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		reportsService.updateLoincCodeDetails(bindingResult, userObject, labLoincMappingVO);


		return targetMap;

	}
	
	
	
	@RequestMapping(value="/updateLabLocalCodeDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabLocalCodeDetails(@ModelAttribute("labLocalCodesMappingVO") LabLocalCodesMappingVO labLocalCodesMappingVO, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		reportsService.updateLabLocalCodeDetails(labLocalCodesMappingVO, bindingResult, userObject);
		
		return targetMap;

	}
	
	
	@RequestMapping(value="/updateLabLocalCodeLoincDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabLocalCodeLoincDetails(@ModelAttribute("labLocalCodesMappingVO") LabLoincMappingVO labLoincMappingVO, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		reportsService.updateLabLocalCodeLoincDetails(labLoincMappingVO, bindingResult, userObject);
		
		return targetMap;

	}
	
	@RequestMapping(value="/updateLabLocalCodeMappingDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabLocalCodeMappingDetails(@ModelAttribute("labTestMappingVO") LabTestMappingVO labTestMappingVO, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		reportsService.updateLabLocalCodeMappingDetails(labTestMappingVO, bindingResult, userObject);
		
		return targetMap;

	}

	
	
	@RequestMapping(value="/addNewPermitToFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewPermitToFacility(HttpServletRequest request, @ModelAttribute("labPermitsVO") LabPermitsVO labPermitsVO ) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		reportsService.addNewPermitToFacility(labPermitsVO, bindingResult, userObject);
		
		return targetMap;

	}

	@RequestMapping(value="/addNewCommunicationToFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewCommunicationToFacility(HttpServletRequest request, @ModelAttribute("labInteractionHistoryVO") LabInteractionHistoryVO labInteractionHistoryVO ) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.addNewCommunicationToFacility(labInteractionHistoryVO, bindingResult, userObject);
		
		return targetMap;

	}
	
	@RequestMapping(value="/addNewInstrumentToPermit.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewInstrumentToPermit(@RequestParam long labPermitId,@RequestParam long instrumentId, @RequestParam String dateValidFrom, @RequestParam(required = false) String dateValidTill, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		reportsService.addNewInstrumentToPermit(labPermitId, instrumentId, dateValidFrom, dateValidTill, bindingResult, userObject);
		
		return targetMap;

	}
	
	
	@RequestMapping(value="/addNewLabLoincToFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewLabLoincToFacility(HttpServletRequest request, @ModelAttribute("labLoincMappingVO") LabLoincMappingVO labLoincMappingVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.addNewLabLoincToFacility(labLoincMappingVO, bindingResult, userObject);
		
		return targetMap;

	}
	
	@RequestMapping(value="/updateLabLoincToFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLabLoincToFacility(HttpServletRequest request, @ModelAttribute("labLoincMappingVO") LabLoincMappingVO labLoincMappingVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.updateLabLoincToFacility(labLoincMappingVO, bindingResult, userObject);
		
		return targetMap;

	}

	@RequestMapping(value="/addNewLabTestToFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewLabTestToFacility(@RequestParam long localCodeMapId, @RequestParam  String testDescription, @RequestParam String dateValidFrom, @RequestParam(required = false) String dateValidTill, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.addNewLabTestToFacility(localCodeMapId, testDescription, dateValidFrom, dateValidTill, bindingResult, userObject);
		
		return targetMap;

	}
	
	
	@RequestMapping(value="/addNewLabLocalCodeToFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewLabLocalCodeToFacility(@RequestParam String pfi,@RequestParam  String localCode,@RequestParam("loincCodes[]")  long[] loincCodes, @RequestParam String dateValidFrom, @RequestParam(required = false) String dateValidTill, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		//GridObject jsonResponse = getGridObject.GetGrid(reportsService.getLabDetails(bindingResult, userObject, pfi));
		
		reportsService.addNewLabLocalCodeAndLoincDetailsToFacility(pfi, localCode, loincCodes, dateValidFrom, dateValidTill, bindingResult, userObject);
		
		return targetMap;

	}
	
	

	@RequestMapping(value="/updateLoincDetailsOfLocalCode.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateLoincDetailsOfLocalCode(@RequestParam long localCodeMapId,@RequestParam  String pfi,@RequestParam("loincCodes[]")  long[] loincCodes, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		reportsService.updateLoincDetailsOfLocalCode(localCodeMapId, pfi,  loincCodes, bindingResult, userObject);
		
		return targetMap;

	}
	
	
	
	@RequestMapping(value="/saveLabFacilityDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> saveLabFacilityDetails(Map<String, Object> map, HttpServletRequest request, @ModelAttribute("labFacilitiesVO") LabFacilitiesVO labFacilitiesVO) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.saveLabFacilityDetails(bindingResult, userObject, labFacilitiesVO);
				
		return targetMap;
	
	}

	@RequestMapping(value="/getInteractionHistory.html", method = RequestMethod.POST)
	public String getInteractionHistory(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<JSONObject> communicationTypes = reportsService.getInteractionContactTypes(bindingResult, userObject);
		
		map.put("communicationTypes", communicationTypes);
		
		return "reports/labInteractionHistory";
	
	}

	
	
	
	@RequestMapping(value="/getLabInteractionHistoryData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<LabInteractionHistoryVO> getLabInteractionHistoryData(HttpServletRequest request, @RequestParam String pfi){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		List<LabInteractionHistoryVO> interactionData = reportsService.getInterationHistory(pfi, bindingResult, userObject);
		
		return interactionData;
		
	}
	
	@RequestMapping(value="/getLabCommunicationHistoryData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<LabInteractionHistoryVO> getLabCommunicationHistoryData(HttpServletRequest request, @RequestParam String pfi){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		List<LabInteractionHistoryVO> interactionData = reportsService.getCommunicationHistory(pfi, bindingResult, userObject);
		
		return interactionData;
		
	}
	
	
	@RequestMapping(value="/addLabInteractionNotes.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addLabInteractionNotes(HttpServletRequest request, @RequestParam String pfi, @RequestParam String interactionNotes, @RequestParam String interactionContactType){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.addLabInteractionNotes(pfi, interactionNotes, interactionContactType, bindingResult, userObject);
		return targetMap;
	}
	
	
	@RequestMapping(value="/addNewAlternativeContact.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewAlternativeContact(HttpServletRequest request, @RequestParam String pfi, @RequestParam String alternativeContactName, @RequestParam String alternativeContactPhone, @RequestParam String alternativeContactEmail, @RequestParam String alternativeContactFax){

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.addNewAlternativeContactTOPFI(pfi, alternativeContactName, alternativeContactPhone, alternativeContactEmail, alternativeContactFax, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	

	@RequestMapping(value="/addLabDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addLabDetails(Map<String, Object> map, HttpServletRequest request, @RequestParam String clia) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		JsonArray facilityDetails = null;
		
		List<LabFacilitiesVO> facilityDetailsObject = new ArrayList<LabFacilitiesVO>();
		if(request.getParameter("facilityDetails")!=null){
			JsonParser parser = new JsonParser();
			facilityDetails = (JsonArray)parser.parse(request.getParameter("facilityDetails"));
			Gson gson = new Gson();  
			for (JsonElement jsonElement : facilityDetails) {
				LabFacilitiesVO labFacilitiesVO = gson.fromJson(jsonElement.toString(), LabFacilitiesVO.class);
				if("Y".equals(labFacilitiesVO.getIsNewRecord())){
					facilityDetailsObject.add(labFacilitiesVO);
				}
			}
		}
                // changing clia to uppercase, so that when inserting into database it will be stored as uppercase
                // Since at the time of searching we are converting the search parameter to uppercase.
		reportsService.addNewLabDetails(bindingResult, userObject,clia.toUpperCase(), facilityDetailsObject);
		return targetMap;
	
	}

	
	@RequestMapping(value="/getClinicalLabarotories.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getClinicalLabarotories(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<String> clinicalLabaratoriesList = reportsService.getClinicalLabarotories(bindingResult, userObject);
		
		
		GridObject jsonResponse =getGridObject.GetGrid(clinicalLabaratoriesList);
		
		return jsonResponse;
	
	}
	
	@RequestMapping(value="/getFacilitiesDataForClia.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getFacilitiesDataForClia(Map<String, Object> map, HttpServletRequest request, @RequestParam String clia) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<LabFacilitiesVO> facilitiesListForClia = transactionService.getFacilitiesDataAndContactsAssociatedForClia(bindingResult, userObject, clia);
		
		
		GridObject jsonResponse = getGridObject.GetGrid(facilitiesListForClia);
		
		return jsonResponse;
	
	}
	

	@RequestMapping(value="/checkPFIExistence.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> checkPFIExistence(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.checkPFIExistence(bindingResult, userObject, pfi);
		
		return targetMap;
	
	}
	
	@RequestMapping(value="/deleteContactForPFI.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> deleteContactForPFI(Map<String, Object> map, HttpServletRequest request, @RequestParam String contactId) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.deleteContactForPFI(bindingResult, userObject, contactId);
		
		return targetMap;
	
	}
	

	@RequestMapping(value="/deleteSubmissionType.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> deleteSubmissionType(Map<String, Object> map, HttpServletRequest request, @RequestParam String submissionTypeId, 
			@RequestParam String pfi) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		reportsService.deleteSubmissionType(bindingResult, userObject, submissionTypeId, pfi);
		
		return targetMap;
	
	}
	
	@RequestMapping(value="/multiSpotDocuments")
	public String multiSpotDocuments(Map<String, Object> map) {

		List<Map<String, String>> loincPfiCombinedData =  labDocumentService.getLabCombiningDocData();
		
		map.put("loincPfiCombinedData", loincPfiCombinedData);
		
		return "lab/MultiSpotDocumentsReview";
	
	}
	
	@RequestMapping(value="/loadMultiSpotDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<String, Object>> loadMultiSpotDocuments(Map<String, Object> map, HttpServletRequest request, @RequestParam String pfi, @RequestParam String loincs) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return labDocumentService.loadMultiSpotDocuments(bindingResult, userObject, pfi, loincs);
	
	}
	
	
	@RequestMapping(value="/processMultiSpotDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> processMultiSpotDocuments(Map<String, Object> map, HttpServletRequest request, @RequestParam("multiSpotDocuments[]")  int[] multiSpotDocuments, @RequestParam int retainDocumentId, @RequestParam int loincGroupId) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		labDocumentService.processMultiSpotDocuments(bindingResult, userObject, multiSpotDocuments, retainDocumentId, loincGroupId);
		
		return targetMap;
	
	}
	
	@RequestMapping("/nysNycLabCommunication")
	public String nysNycLabCommunication(Map<String, Object> map, HttpServletRequest request) {
		
		return "reports/lab/NycNYSLabCommunication";
	}
	
	@RequestMapping(value="/getNycNysLabCommReportData.json", method = RequestMethod.GET,  produces = "application/json")
	public @ResponseBody List<LabOpsReportsVO> getNycNysLabCommReportData(@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String nycServiceFlag, HttpServletRequest request, Map<String, Object> map){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> dummyMindingMap= new HashMap<Object, Object>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		List<LabOpsReportsVO> nycNysCommReportList  = reportsService.getNycNysCommReportData(fromDate, toDate, nycServiceFlag, dummyBindingResult, userObject) ;
		
		return nycNysCommReportList;
	}
	
	@RequestMapping("/labListByApprovalStatus")
	public String labListByApprovalStatus(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		JSONObject approvalStatusJsonObj = reportsService.getApprovalStatus(bindingResult, userObject);

		//containing Reporting Status Map details(Key/Value) 
		map.put("approvalStatusList", approvalStatusJsonObj);
		
		return "reports/lab/LabListByApprovalStatus";
	}	
	
	@RequestMapping(value="/getLabListByApporvalStatusData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getLabListByApporvalStatusData(HttpServletRequest request, @RequestParam String approvalStatus){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		GridObject jsonResponse = getGridObject.GetGrid( reportsService.getListOfLabsByApprovalStatus(userObject, bindingResult, approvalStatus));
		
		return jsonResponse;

	}	
	
	@RequestMapping("/countOfLabsByApprovalStatus")
	public String countOfLabsByApprovalStatus(Map<String, Object> map, HttpServletRequest request) {
		
		return "reports/lab/CountOfLabsByApprovalStatus";
	}	
	
	@RequestMapping(value="/loadCountOfLabsByApporvalStatusData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getCountOfLabsByApporvalStatusData(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(reportsService.getCountOfLabsByApprovalStatus(userObject, bindingResult));
		
		return jsonResponse;

	}		

}
