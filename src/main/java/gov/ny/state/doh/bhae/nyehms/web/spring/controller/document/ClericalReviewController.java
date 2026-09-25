package gov.ny.state.doh.bhae.nyehms.web.spring.controller.document;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ADAPDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ClericalReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.EPRFOOCDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NycIJRDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
/*
 * Spring controller class for system message
 */
@Controller
public class ClericalReviewController {
	/*
	 * Reference to GetGridObject
	 */
	
	@Autowired
	ClericalReviewService clericalReviewService;
	
	@Autowired
	NycIJRDocumentService nycIJRDocumentService;
	

	@Autowired
	ADAPDocumentService adapDocumentService;
	
	@Autowired
	PersionService personService;

	@Autowired
	EPRFOOCDocumentService  ePRFOOCDocumentService;
	@RequestMapping("/getClericalReview")
	public String getClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		String reviewType = "C";
		
		List<String> clericalReviewDocTypes = clericalReviewService.getAvailabeDocumentTypes(reviewType);
		
		map.put("reviewType", reviewType);
		
		map.put("clericalReviewDocTypes", clericalReviewDocTypes);
		
		return "person/clericalReview";
		
	}
	
	@RequestMapping("/getDataFluxClericalReview")
	public String getDataFluxClericalReview(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		String reviewType = "C";
		
		
		
		List<String> clericalReviewDocTypes = clericalReviewService.getDatafluxAvailabeDocumentTypes(reviewType, userObject);
		
		map.put("reviewType", reviewType);
		
		map.put("clericalReviewDocTypes", clericalReviewDocTypes);
		
		return "person/DataFluxClericalReview";
		
	}
	
	
	
	@RequestMapping("/getOOCDatafluxClericalReview")
	public String getOOCDatafluxClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		session.setAttribute("FILTER_CRITERIA_FILE_TYPE", null);
		
		map.put("DOCUMENT_TYPE", "EPRF_OOC");
		
		return "person/ClericalReviewDatafluxStatus";
		
	}
	
	@RequestMapping("/getNycIJRDataFluxClericalReview")
	public String getNycIJRDataFluxClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "NYC_IJR");
		
		List<String> fileNames = clericalReviewService.getDatafluxFileNamesToReview("NYC_IJR");
		

		if(fileNames != null && fileNames.size() > 1){
			fileNames.add(0, "ALL");
		}
		
		map.put("fileNames", fileNames);
		
		return "person/ClericalReviewDatafluxStatus";
		
	}
	
	@RequestMapping("/getICCRDataFluxClericalReview")
	public String getICCRDataFluxClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "ICCR");
		
		return "person/ClericalReviewDatafluxStatus";
		
	}
	
	@RequestMapping("/getQualCareDataFluxClericalReview")
	public String getQualCareDataFluxClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		List<String> fileNames = clericalReviewService.getDatafluxFileNamesToReview("QUAL_CARE");
		

		if(fileNames != null && fileNames.size() > 1){
			fileNames.add(0, "ALL");
		}
		
		map.put("fileNames", fileNames);

		
		map.put("DOCUMENT_TYPE", "QUAL_CARE");
		
		return "person/ClericalReviewDatafluxStatus";
		
	}
	
	
	@RequestMapping("/getResistanceDataFluxClericalReview")
	public String getResistanceDataFluxClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "RESISTANCE");
		
		
		
		return "person/ClericalReviewDatafluxStatus";
		
	}
	
	@RequestMapping("/getADAPDataFluxClericalReview")
	public String getADAPDataFluxClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "ADAP");
		
		List<String> fileNames = adapDocumentService.getFileNames();
		
		
		if(fileNames != null && fileNames.size() > 1){
			fileNames.add(0, "ALL");
		}
		map.put("fileNames", fileNames);
		
		
		return "person/ClericalReviewDatafluxStatus";
		
	}
	
	
	
	
	@RequestMapping(value="/getDataFluxClericalReviewAvailabeDocumentTypes.json",  produces = "application/json")
	public @ResponseBody List<String> getDataFluxClericalReviewAvailabeDocumentTypes(HttpServletRequest request, @RequestParam String reviewType, UserObject userObject){
		
		
		List<String> clericalReviewDocTypes = clericalReviewService.getDatafluxAvailabeDocumentTypes(reviewType, userObject);
		
		return clericalReviewDocTypes;
	}
	
	@RequestMapping(value="/getDatafluxDocumentIdAndPotentialMatchesForStatenos.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> getDatafluxDocumentIdAndPotentialMatchesForStatenos(HttpServletRequest request, @RequestParam String documentType, @RequestParam String reviewType, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
 
		Map<String, Object> documentAndItsPotentialMatches = clericalReviewService.getDatafluxDocumentIdAndPotentialMatchesForStatenos(documentType, reviewType, bindingResult, userObject);
		
		return documentAndItsPotentialMatches;
	}
	
	@RequestMapping(value="/matchDocumentToStatenoInDataFlux.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> matchDocumentToStatenoInDataFlux(HttpServletRequest request, @RequestParam long documentId, @RequestParam String stateno, @RequestParam(required = false) Long fuzzyClusterId, @RequestParam(required = false) Long clusterId, @RequestParam String datafluxMatchCd, @RequestParam String documentType, @RequestParam(required = false) String comments, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clericalReviewService.reviewDatafluxStagedDocuments(documentId, stateno, clusterId, fuzzyClusterId, datafluxMatchCd, documentType, comments, bindingResult, userObject);
		
		//matchDocumentToStatenoInDataFlux(documentId, stateno, fuzzyClusterId, bindingResult, userObject);

		return targetMap;

	}
	
	@RequestMapping(value="/noMatchForDocumentToStatenoInDataFlux.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> noMatchForDocumentToStatenoInDataFlux(HttpServletRequest request, @RequestParam long documentId, @RequestParam(required = false) long fuzzyClusterId, @RequestParam(required = false) Long clusterId, @RequestParam String datafluxMatchCd, String documentType, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clericalReviewService.reviewDatafluxStagedDocuments(documentId, null, clusterId, fuzzyClusterId, datafluxMatchCd, documentType, null, bindingResult, userObject);
		
		//clericalReviewService.noMatchForDocumentToStatenoInDataFlux(documentId, fuzzyClusterId, bindingResult, userObject);

		return targetMap;

	}
	
	
	@RequestMapping("/getClericalDeathReview")
	public String getClericalDeathReview(Map<String, Object> map, HttpServletRequest request) {
		
		String reviewType = "P";
		
		List<String> clericalReviewDocTypes = clericalReviewService.getAvailabeDocumentTypes(reviewType);
		
		map.put("reviewType", reviewType);
		
		map.put("clericalReviewDocTypes", clericalReviewDocTypes);
		
		return "person/clericalReview";
		
	}
	
	@RequestMapping("/getDatafluxClericalDeathReview")
	public String getDatafluxClericalDeathReview(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		String reviewType = "P";
		
		
		List<String> clericalReviewDocTypes = clericalReviewService.getDatafluxAvailabeDocumentTypes(reviewType, userObject);
		
		map.put("reviewType", reviewType);
		
		map.put("clericalReviewDocTypes", clericalReviewDocTypes);
		
		return "person/DataFluxClericalReview";
		
	}
	
	
	
	
	@RequestMapping(value="/getClericalReviewAvailabeDocumentTypes.json",  produces = "application/json")
	public @ResponseBody List<String> getClericalReviewAvailabeDocumentTypes(HttpServletRequest request, @RequestParam String reviewType){
		
		List<String> clericalReviewDocTypes = clericalReviewService.getAvailabeDocumentTypes(reviewType);
		
		return clericalReviewDocTypes;
	}
	
	
	
	@RequestMapping("/getOOCClericalReview")
	public String getEPRFOOCClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		session.setAttribute("FILTER_CRITERIA_FILE_TYPE", null);
		
		map.put("DOCUMENT_TYPE", "EPRF_OOC");
		
		return "person/ClericalReviewQSStatus";
		
	}
	

	@RequestMapping("/getNycIJRClericalReview")
	public String getNycIJRClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "NYC_IJR");
		
		List<String> fileNames = nycIJRDocumentService.getFileNames();
		

		if(fileNames != null && fileNames.size() > 1){
			fileNames.add(0, "ALL");
		}
		
		map.put("fileNames", fileNames);
		
		return "person/ClericalReviewQSStatus";
		
	}
	
	@RequestMapping("/getICCRClericalReview")
	public String getICCRClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "ICCR");
		
		return "person/ClericalReviewQSStatus";
		
	}
	
	@RequestMapping("/getResistanceClericalReview")
	public String getResistanceClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "RESISTANCE");
		
		
		
		return "person/ClericalReviewQSStatus";
		
	}
	
	
	
	
	
	@RequestMapping("/getADAPClericalReview")
	public String getADAPClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "ADAP");
		
		List<String> fileNames = adapDocumentService.getFileNames();
		
		
		if(fileNames != null && fileNames.size() > 1){
			fileNames.add(0, "ALL");
		}
		map.put("fileNames", fileNames);
		
		
		return "person/ClericalReviewQSStatus";
		
	}
	
	
	
	@RequestMapping("/getSearchViewForClericalReview.html")
	public String getSearchViewForClericalReview(Map<String, Object> map, HttpServletRequest request) {
		

		List<Map<String, String>> originCategories = personService.getOriginCategories();
		
		map.put("originCategories", originCategories);
		
		return "search/personSearchTabDetail";
		
	}
	
	
	
	
	@RequestMapping(value="/getDocumentIdAndPotentialMatchesForStatenos.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> getDocumentIdAndPotentialMatchesForStatenos(HttpServletRequest request, @RequestParam String documentType, @RequestParam String reviewType, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
 
		Map<String, Object> documentAndItsPotentialMatches = clericalReviewService.getDocumentIdAndPotentialMatchesForStatenos(documentType, reviewType, bindingResult, userObject);
		
		return documentAndItsPotentialMatches;
	}
	
	
	@RequestMapping(value="/getOOCDocumentFileNames.json",  produces = "application/json")
	public @ResponseBody List<JSONObject> getOOCDocumentFileNames(HttpServletRequest request, @RequestParam String fileType, @RequestParam int processStatus, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		List<JSONObject> fileNamesByFileType = ePRFOOCDocumentService.getOOCDocumentFileNames(fileType, processStatus, bindingResult, userObject);
		
		return fileNamesByFileType;
	}
	
	
	@RequestMapping(value="/getQualityStageStatusCodesForTheFileName.json",  produces = "application/json")
	public @ResponseBody List<String> getQualityStageStatusCodesForTheFileName(HttpServletRequest request, @RequestParam String fileName, @RequestParam String documentType, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		List<String> qualitStageStatusCodes = clericalReviewService.getQualityStageStatusCodesForTheFileName(fileName, documentType, bindingResult, userObject);
		
		return qualitStageStatusCodes;
	}
	
	
	
	
	@RequestMapping(value="/getDocumentIdAndPotentialMatchesForStatenosForQSReview.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> getDocumentIdAndPotentialMatchesForStatenosForQSReview(HttpServletRequest request, UserObject userObject){
		HttpSession session = request.getSession();
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		String FILTER_CRITERIA_STATUS = (String)session.getAttribute("FILTER_CRITERIA_STATUS");
		String FILTER_CRITERIA_FILE_NAME = (String)session.getAttribute("FILTER_CRITERIA_FILE_NAME");
		String FILTER_CRITERIA_FILE_TYPE = (String)session.getAttribute("FILTER_CRITERIA_FILE_TYPE");
		String QS_REVIEW_DOCUMENT_TYPE = (String)session.getAttribute("QS_REVIEW_DOCUMENT_TYPE");

		Map<String, Object> documentAndItsPotentialMatches = clericalReviewService.getDocumentIdAndPotentialMatchesForStatenosForQSReview(FILTER_CRITERIA_STATUS, FILTER_CRITERIA_FILE_NAME, FILTER_CRITERIA_FILE_TYPE, QS_REVIEW_DOCUMENT_TYPE, bindingResult, userObject);
		
		return documentAndItsPotentialMatches;
	}
	
	@RequestMapping(value="/getDocumentIdAndPotentialMatchesForStatenosForDFReview.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> getDocumentIdAndPotentialMatchesForStatenosForDFReview(HttpServletRequest request, UserObject userObject){
		HttpSession session = request.getSession();
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		String FILTER_CRITERIA_STATUS = (String)session.getAttribute("FILTER_CRITERIA_STATUS");
		String FILTER_CRITERIA_FILE_NAME = (String)session.getAttribute("FILTER_CRITERIA_FILE_NAME");
		String FILTER_CRITERIA_FILE_TYPE = (String)session.getAttribute("FILTER_CRITERIA_FILE_TYPE");
		String QS_REVIEW_DOCUMENT_TYPE = (String)session.getAttribute("QS_REVIEW_DOCUMENT_TYPE");

		Map<String, Object> documentAndItsPotentialMatches = clericalReviewService.getDocumentIdAndPotentialMatchesForStatenosForDFReview(FILTER_CRITERIA_STATUS, FILTER_CRITERIA_FILE_NAME, FILTER_CRITERIA_FILE_TYPE, QS_REVIEW_DOCUMENT_TYPE, bindingResult, userObject);
		
		return documentAndItsPotentialMatches;
	}
	
	@RequestMapping(value="/getDFStatusCodesForTheFileName.json",  produces = "application/json")
	public @ResponseBody List<String> getDFStatusCodesForTheFileName(HttpServletRequest request, @RequestParam String fileName, @RequestParam String documentType, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		List<String> qualitStageStatusCodes = clericalReviewService.getDFStatusCodesForTheFileName(fileName, documentType, bindingResult, userObject);
		
		return qualitStageStatusCodes;
	}
	
	
	
	
	@RequestMapping(value="/storeDocumentCategorySearchCriteriaForClericalView")
	public @ResponseBody String storeDocumentCategorySearchCriteriaForClericalView(HttpServletRequest request, @RequestParam String qualityStageStatus, @RequestParam String documentFileName, @RequestParam String documentFileType, @RequestParam  String documentType){
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", qualityStageStatus);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", documentFileName);
		session.setAttribute("FILTER_CRITERIA_FILE_TYPE", documentFileType);
		session.setAttribute("QS_REVIEW_DOCUMENT_TYPE", documentType);
		
		
		return "SUCCESS";
	}
	
	
	@RequestMapping(value="/matchDocumentToStateno.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> matchDocumentToStateno(HttpServletRequest request, @RequestParam long documentId, @RequestParam String stateno, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clericalReviewService.matchDocumentToStateno(documentId, stateno, bindingResult, userObject);

		return targetMap;

	}
	
	@RequestMapping(value="/noMatchForDocumentToStateno.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> noMatchForDocumentToStateno(HttpServletRequest request, @RequestParam long documentId, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clericalReviewService.noMatchForADocument(documentId, bindingResult, userObject);

		return targetMap;

	}
	
	@RequestMapping(value="/qualityStagedDocumentManualReview.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> qualityStagedDocumentManualReview(HttpServletRequest request, @RequestParam long documentId, @RequestParam(required = false) String stateno, @RequestParam String matchCode, @RequestParam String documentType, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clericalReviewService.qualityStagedDocumentManualReview(documentId, stateno, matchCode, documentType, bindingResult, userObject);

		return targetMap;

	}
	
	
}
