package gov.ny.state.doh.bhae.nyehms.web.spring.controller.review;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.ReportProperties;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RiaDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftFollowupDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.RiaReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
@Controller
public class RiaController {

	@Autowired
	private ReportProperties reportProperties;
	
	@Autowired
	private RiaReviewService riaReviewService;
	@Autowired
	ACRFDocumentService acrfFDocumentService;
	@Autowired
	CommonDocumentService commonDocumentService;
	@Autowired
	CommonService commonService;
	@Autowired
	LaftFollowupDocumentService laftFollowupDocumentService;
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;

	@RequestMapping(value="/getReviewRiaPage")	
	public String getReviewRiaPage(HttpServletRequest request, Map<String, Object> map) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		List<Map<String, String>> laftFollowupReasons = laftFollowupDocumentService.getLaftFollowupReasons();
		map.put("laftFollowupReasons", laftFollowupReasons);
		
		Map<String, List<Map<String, String>>> riaDropDownList = riaReviewService.getRiaCodedDropDownData();
		
		map.put("riaDropDownList", riaDropDownList);
		
		map.put("REPORT_NAME", reportProperties.riaReviewName());
		
		return "review/reviewRiaPage";
	}
	
	@RequestMapping(value="/riaReport/{reportType}")	
	public String riaEligiblityReport(HttpServletRequest request, Map<String, Object> map, @PathVariable String reportType, UserObject userObject) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		Map<String, String> countyMap=commonService.getCountyCodes(bindingResult, userObject, request.getSession().getId());
		map.put("countyMap", countyMap);
		
		Map<String, List<Map<String, String>>> riaDropDownList = riaReviewService.getRiaCodedDropDownData();
		
		map.put("riaDropDownList", riaDropDownList);
		
		map.put("reportType", reportType);
		
		map.put("REPORT_NAME", reportProperties.names().get(reportType));
		
		return "review/ria/riaReport";
	}
	
	@RequestMapping(value="/riaReportByFollowup")	
	public String riaReportByFollowup(HttpServletRequest request, Map<String, Object> map, UserObject userObject) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		Map<String, String> countyMap=commonService.getCountyCodes(bindingResult, userObject, request.getSession().getId());
		map.put("countyMap", countyMap);
		
		Map<String, List<Map<String, String>>> riaDropDownList = riaReviewService.getRiaCodedDropDownData();
		
		map.put("riaDropDownList", riaDropDownList);
		
		return "review/ria/riaReport";
	}
	
	
	
	
	
	@RequestMapping(value = "/getRiaEligibilityReport.html")
	public String getRiaEligibilityReport(@RequestParam String riaFlaggedFromDate, @RequestParam String riaFlaggedToDate, @RequestParam("riaCounty[]") String[] riaCounty, @RequestParam String reportType, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, Integer> riaEligibilityReport = riaReviewService.getRiaEligibilityReport(riaFlaggedFromDate, riaFlaggedToDate, riaCounty, reportType, bindingResult, userObject);
		
		map.put("riaEligibilityReport", riaEligibilityReport);
		
		return "review/ria/riaEligibilityReport";
	}
	
	@RequestMapping(value = "/getRiaFollowUpReport.html")
	public String getRiaFollowUpReport(@RequestParam String riaFlaggedFromDate, @RequestParam String riaFlaggedToDate, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, String> riaFollowUpReport = riaReviewService.getRiaFollowUpReport(riaFlaggedFromDate, riaFlaggedToDate, bindingResult, userObject);
		
		map.put("riaFollowUpReport", riaFollowUpReport);
		
		return "review/ria/riaFollowupReport";
	}
	
	@RequestMapping(value = "/getRiaTreatmentReport.html")
	public String getRiaTreatmentReport(@RequestParam String riaFlaggedFromDate, @RequestParam String riaFlaggedToDate, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, String> riaTreatmentReport = riaReviewService.getRiaTreatmentReport(riaFlaggedFromDate, riaFlaggedToDate, bindingResult, userObject);
		
		map.put("riaTreatmentReport", riaTreatmentReport);
		
		return "review/ria/riaTreatmentReport";
	}
	
	
	
	
	
	@RequestMapping(value="/viewRiaReviewPage")	
	public String viewRiaReviewPage(HttpServletRequest request, Map<String, Object> map) {
		
		return "review/reviewRiaPage";
	}
	
	@RequestMapping(value="/getRiaEligibilityRecordsByReport.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getRiaEligibilityRecordsByReport(HttpServletRequest request, @RequestParam String riaFlaggedFromDate, @RequestParam String riaFlaggedToDate, @RequestParam("riaCounty[]") String[] riaCounty, @RequestParam String PARAMETER_NAME, @RequestParam String PARAMETER_VALUE, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return riaReviewService.getRiaEligibilityRecordsByReport(riaFlaggedFromDate, riaFlaggedToDate, riaCounty, PARAMETER_NAME, PARAMETER_VALUE, bindingResult, userObject);

	}
	
	@RequestMapping(value="/getRiaReportByFollowup.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getRiaReportByFollowup(HttpServletRequest request, @RequestParam String riaFlaggedFromDate, @RequestParam String riaFlaggedToDate,  @RequestParam String PARAMETER_NAME, @RequestParam String PARAMETER_VALUE, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return riaReviewService.getRiaReportByFollowup(riaFlaggedFromDate, riaFlaggedToDate, PARAMETER_NAME, PARAMETER_VALUE, bindingResult, userObject);

	}
	
	@RequestMapping(value="/getRiaReportByTreatment.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getRiaReportByTreatment(HttpServletRequest request, @RequestParam String riaFlaggedFromDate, @RequestParam String riaFlaggedToDate,  @RequestParam String PARAMETER_NAME, @RequestParam String PARAMETER_VALUE, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return riaReviewService.getRiaReportByTreatment(riaFlaggedFromDate, riaFlaggedToDate, PARAMETER_NAME, PARAMETER_VALUE, bindingResult, userObject);

	}
	
	
	
	
	
	
	@RequestMapping(value="/getRiaRecordsForReview.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getRiaRecordsForReview(HttpServletRequest request, @RequestParam String PARAMETER_NAME, @RequestParam String PARAMETER_VALUE, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return riaReviewService.getRiaRecordsForReview(PARAMETER_NAME, PARAMETER_VALUE, bindingResult, userObject);

	}
	
	@RequestMapping(value = "/getRiaDoc.html")
	public String getRiaDoc(@RequestParam int docId, @RequestParam(required=false) String actionType, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		RiaDocVO riaDocVO = riaReviewService.getRiaDoc(docId, actionType, bindingResult, userObject,
				request.getSession().getId());
		
		Map<String, List<Map<String, String>>> riaDropDownList = riaReviewService.getRiaCodedDropDownData();
		
		map.put("riaDropDownList", riaDropDownList);
		

		Map<String, List<Map<String, String>>> riaFollowUpDropdownList = riaReviewService.getRiaFollowUpDropDownData();
		
		map.put("riaFollowUpDropdownList", riaFollowUpDropdownList);
		
		List<JSONObject> codedArvsMedications = commonDocumentService.getCodedArvs();
		
		map.put("codedArvsMedications", codedArvsMedications);
		
		List<Map<String, String>> stateCodes = acrfFDocumentService.getCodedValues("SITE_CD");
		map.put("stateCodes", stateCodes);
		Map<String, String> codedCountries = commonDocumentService.getEharsCodedCountryValues();
		map.put("codedCountries", codedCountries);
		
		List<Map<String, String>> subFacilities = acrfFDocumentService.getStandardFacilityDetails();
		map.put("subFacilities", subFacilities);
		
		Map<String, Object> nyehmsStatenoDetails = null;
		
		if(CommonUtil.isNotNullAndEmpty(riaDocVO.getCOMPLETE_DATE())) {
			nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
					userObject);
		}else {
			nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(riaDocVO.getLAB_DOC_ID(),
					userObject);
		}
		
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		
		map.put("stdRiaDocVO", riaDocVO);
		return "document/RiaDocument";
	}
	
	@RequestMapping(value="/getNyehmsDataForRiaDoc.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> getNyehmsDataForRiaDoc(HttpServletRequest request, int docId){
		
		return riaReviewService.getNyehmsDataForRiaDoc(docId);
		
	}
	
	
	@RequestMapping(value = "/updateRiaDocData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Object, Object> updateRiaDocData(@RequestParam Map<String, String> data,  @RequestParam Integer DOCUMENT_ID,
			@ModelAttribute("riaDocVO") RiaDocVO riaDocVO,
			HttpServletRequest request, UserObject userObject) {
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		riaReviewService.updateRiaDocData(data, DOCUMENT_ID, riaDocVO.getFollowUpDocList(), riaDocVO.getRiaProviderFollowupList(), bindingResult, userObject);

		return targetMap;
	}
	
	@RequestMapping(value = "/closeRiaDoc.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Object, Object> closeRiaDoc(@RequestParam Integer DOCUMENT_ID,
			@ModelAttribute("riaDocVO") RiaDocVO riaDocVO,
			HttpServletRequest request, UserObject userObject) {
		
		
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		riaReviewService.closeRiaDoc(riaDocVO, DOCUMENT_ID, bindingResult, userObject);

		return targetMap;
	}
	
	
	@RequestMapping(value="/getRiaFieldProperties.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<String> getRiaFieldProperties(HttpServletRequest request){
		
		return riaReviewService.getRiaFieldProperties();
		
	}
	
	@RequestMapping(value="/verifyRiaActivity.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> verifyRiaActivity(@RequestParam Integer DOCUMENT_ID, HttpServletRequest request, UserObject userObject){
		
		
		return riaReviewService.verifyRiaActivity(DOCUMENT_ID, userObject);
		
	}
	
	@RequestMapping(value="/statenosFlaggedForRia.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> statenosFlaggedForRia(@RequestParam String stateno1, @RequestParam String stateno2, HttpServletRequest request, UserObject userObject){
		
		
		return riaReviewService.statenosFlaggedForRia(stateno1, stateno2, userObject);
		
	}
	
	
	
	@RequestMapping(value="/getRiaDocActivity.html", method = RequestMethod.POST)	
	public String getRiaDocActivity(Map<String, Object> map,
			@RequestParam Integer DOCUMENT_ID ,
			HttpServletRequest request, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
				
		List<Map<String, String>> riaActivity = riaReviewService.getRiaDocActivity(DOCUMENT_ID,  bindingResult,  userObject);

		map.put("riaActivity", riaActivity);
		
		return "review/showRiaActivity";
	}
	
	@RequestMapping(value="/riaActivityReport")
	public String riaActivityReport(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();

		return "reports/ria/RiaActivityReport";
	
	}
	
	@RequestMapping(value="/getRiaActivityReportData.json", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>>  getRiaActivityReportData(@RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult dummyBindingResult = BindingResults.newDummy();

		return riaReviewService.getRiaActivityReportData(fromDate, toDate, dummyBindingResult, userObject, null);
		
	}
	
	@RequestMapping(value="/generateRiaActivityReportData.xlsx", method = RequestMethod.GET)
	public ModelAndView  generateRiaActivityReportData(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, @RequestParam String fromDate, @RequestParam String toDate, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		ModelAndView mav = new ModelAndView("riaActivityReportSpreadsheet");
		
		List<Map<String, String>> riaActivityRecords = riaReviewService.getRiaActivityReportData(fromDate, toDate, dummyBindingResult, userObject, mav);
		
		
		
		mav.addObject("fromDate", fromDate);
		mav.addObject("toDate", toDate);
		mav.addObject("riaActivityRecords", riaActivityRecords);
		
        return mav;
           		
	}
	
	@RequestMapping(value = "/riaNoLinkedtoCareReport")
	public String getRiaNoLinkedtoCareReport(HttpServletRequest request, Map<String, Object> map) {

		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);

		List<Map<String, String>> laftFollowupReasons = laftFollowupDocumentService.getLaftFollowupReasons();
		map.put("laftFollowupReasons", laftFollowupReasons);

		Map<String, List<Map<String, String>>> riaDropDownList = riaReviewService.getRiaCodedDropDownData();
		map.put("riaDropDownList", riaDropDownList);

		return "review/ria/riaNoLinkedtoCareReport";
	}
	
	@RequestMapping(value = "/riaNoLinkedtoCareDataReport.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getRiaNoLinkedtoCareDataReport(@RequestParam String riaFlaggedFromDate,
			@RequestParam String riaFlaggedToDate, HttpServletRequest request, Map<String, Object> map, UserObject userObject) {


		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		List<JSONObject> rianoLinkedtoCareDataReport = riaReviewService
				.getRiaNoLinkedtoCareDataReport(riaFlaggedFromDate, riaFlaggedToDate, bindingResult, userObject);

		return rianoLinkedtoCareDataReport;

	}
}
