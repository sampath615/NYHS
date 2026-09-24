package gov.ny.state.doh.bhae.nyehms.web.spring.controller.lab;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultAccessionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPUploadSurveyVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.ClepAuditResultsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads.FileUploadService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@Controller
public class ClepAuditResultsController {
	
	@Autowired	ClepAuditResultsService clepAuditResultsService;
	
	@Autowired	private FileUploadService fileUploadService;
	
	@Autowired	GetGridObject getGridObject;
	
    @RequestMapping("/submitLabRptResults")
    public String getLabProficiencyDetails(Map<String, Object> map, HttpServletRequest request) {
    	
    	UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
    	
    	CLEPLabResultVO clepLabResultVO = new CLEPLabResultVO();
    	
    	List<CLEPLabResultAccessionVO> clepAccessionList = new ArrayList<CLEPLabResultAccessionVO>();
    	for (int i=0; i<8; i++) {
    		CLEPLabResultAccessionVO cLEPLabResultAccessionVO = new CLEPLabResultAccessionVO();
    		cLEPLabResultAccessionVO.setLaftStaff(userObject.getUserId());
    		clepAccessionList.add(cLEPLabResultAccessionVO);
    	}
    	
    	clepLabResultVO.setClepLabResultAccessionList(clepAccessionList);
    	
    	map.put("clepLabResultVO", clepLabResultVO);
    	
    	return "lab/SubmitCLEPLabResults";
    }
	
    @RequestMapping(value="/getPFILabDataInfo.json", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody JSONObject getPFILabDataInfo(HttpServletRequest request, String userEnteredPfi) {
    	
    	JSONObject jObj = clepAuditResultsService.getPFILabDataInfo(userEnteredPfi);
    	
    	return jObj;
    }
    
    @RequestMapping(value="/seachAccessionNoinDB.json", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody JSONObject seachAccessionNoinDB(HttpServletRequest request, String accessionNo) {
    	
    	JSONObject jObj = clepAuditResultsService.seachAccessionNoinDB(accessionNo);
    	
    	return jObj;
    }
    
	@RequestMapping(value="/saveCLEPLabResultForm", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> saveCLEPLabResultForm(
			@ModelAttribute("submitClepLabResultData") CLEPLabResultVO clepLabResultVO, HttpServletRequest request, BindingResult result){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		clepAuditResultsService.saveCLEPLabResultForm(clepLabResultVO, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping("/reviewClepLabRptResults")
	public String reviewClepLabRptResults(Map<String, Object> map, HttpServletRequest request) {
		
    	UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
    	
    	CLEPLabResultVO clepLabResultVO = new CLEPLabResultVO();
    	
    	List<CLEPLabResultAccessionVO> clepAccessionList = new ArrayList<CLEPLabResultAccessionVO>();
    	for (int i=0; i<8; i++) {
    		CLEPLabResultAccessionVO cLEPLabResultAccessionVO = new CLEPLabResultAccessionVO();
    		cLEPLabResultAccessionVO.setLaftStaff(userObject.getUserId());
    		clepAccessionList.add(cLEPLabResultAccessionVO);
    	}
    	
    	clepLabResultVO.setClepLabResultAccessionList(clepAccessionList);
    	
    	map.put("clepLabResultVO", clepLabResultVO);
		
		return "lab/ReviewCLEPLabResults";
	}	
	
	@RequestMapping(value="/loadClepLabResultForReview.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getClepLabResultForReview(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(clepAuditResultsService.getClepLabResultForReview(bindingResult, userObject));
		
		return jsonResponse;

	}	
	
	@RequestMapping("/getClepSurveyData.html")
	public String getClepSurveyData(@RequestParam("surveyNo") int surveyNo, Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		CLEPLabResultVO CLEPLabSurveyData = clepAuditResultsService.getClepSurveyData(surveyNo, userObject);
		
		map.put("clepLabResultVO", CLEPLabSurveyData);
		
		return "lab/CLEPLabResultsForm";
	}
	
	@RequestMapping("/uploadHIVSurvey")
	public String uploadHIVSurveyPage(Map<String, Object> map, HttpServletRequest request) {
		
		return "lab/uploadHIVSurvey";
	}	
	
	
	@RequestMapping(value="/uploadHIVSurveysFile", method = RequestMethod.POST)
	public String  uploadHIVSurveysFile(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, Map<Object, Object> map) throws IOException{
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		session.removeAttribute("resultMap");
		session.removeAttribute("resultMessage");
		
		Map<Object, Object> scanResultMap = fileUploadService.validateAndScanFile(fileData, bindingResult);
		
		if(scanResultMap != null && scanResultMap.size() > 0 ){
			session.setAttribute("resultMessage", scanResultMap.get("TRANSACTION_MESSAGE"));
			return "lab/uploadHIVSurvey";
		}
		
		Map<Object, Object> resultMap = clepAuditResultsService.uploadHIVSurveysFile(fileData, bindingResult, userObject);
		
		if(resultMap != null && resultMap.size() > 0 ){
			map.put("resultMap", resultMap);
			session.setAttribute("resultMap", resultMap);
		}else{
			session.setAttribute("resultMap", null);
		}
				
		return "lab/uploadHIVSurvey";
	}
	
	@RequestMapping("/followupHIVSurvey")
	public String followupHIVSurvey(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		map.put("uploadedHIVSurveyFilenames", clepAuditResultsService.getUploadedHIVSurveyFilenames(userObject, bindingResult));
		
		return "lab/FollowupHIVSurvey";
	}		
	
	@RequestMapping(value="/getUploadedHIVSurveyData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getUploadedHIVSurveyData(@RequestParam String uploadedFilename, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(clepAuditResultsService.getUploadedHIVSurveyData(uploadedFilename, bindingResult, userObject));
		
		return jsonResponse;

	}	
	
	@RequestMapping("/getClepSurveyUploadData.html")
	public String getClepSurveyUploadData(@RequestParam("uploadNo") int uploadNo, Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		CLEPUploadSurveyVO clepUploadSurveyVO = clepAuditResultsService.getClepSurveyUploadData(uploadNo, userObject);
		
		map.put("clepUploadSurveyVO", clepUploadSurveyVO);
		
		return "lab/ClepSurveyFollowupForm";
	}
	
	@RequestMapping(value="/saveFollowupHIVSurveyData", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> saveFollowupHIVSurveyData(
			@ModelAttribute("submitClepSurveyFollowupData") CLEPUploadSurveyVO clepUploadSurveyVO, HttpServletRequest request, BindingResult result){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		clepAuditResultsService.saveFollowupHIVSurveyData(clepUploadSurveyVO, bindingResult, userObject);
		
		return targetMap;
	}	
	
	@RequestMapping("/labRecruitmentAssessment")
	public String labRecruitmentAssessment(Map<String, Object> map, HttpServletRequest request) {
		
    	UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
    	
		return "lab/LabRecruitmentAssessment";
	}	
	
	@RequestMapping(value="/loadLabRecruitmentAssessmentData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getloadLabRecruitmentAssessmentData(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(clepAuditResultsService.getLabRecruitmentAssessmentForFollowup(bindingResult, userObject));
		
		return jsonResponse;

	}	
	
	@RequestMapping("/getLabRecruitmentAssessmentData.html")
	public String getLabRecruitmentAssessmentData(@RequestParam("surveyNo") int surveyNo, Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		CLEPLabResultVO clepLabResultVO = clepAuditResultsService.getLabRecruitmentAssessmentData(surveyNo, userObject);
		
		List<Map<String, String>> recruitAssessmentOutcomeList = clepAuditResultsService.getRecruitAssessmentOutcome();
		map.put("recruitAssessmentOutcomeList", recruitAssessmentOutcomeList);
		map.put("clepLabResultVO", clepLabResultVO);
		
		return "lab/LabRecruitmentAssessmentFollowupForm";
	}
	
	@RequestMapping(value="/saveRecruitAssessFollowupData", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> saveRecruitAssessFollowupData(
			@ModelAttribute("submitLabRecruitmentAssessmentFollowupData") CLEPLabResultVO clepLabResultVO, HttpServletRequest request, BindingResult result){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		clepAuditResultsService.saveRecruitAssessFollowupData(clepLabResultVO, bindingResult, userObject);
		
		return targetMap;
	}		
	
	@RequestMapping(value="/clepSurveyAuditReport")	
	public String getSurveyAuditReport(HttpServletRequest request, Map<String, Object> map) {
		
		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		
		return "lab/CLEPSurveyAuditReport";
	}		
	
	@RequestMapping(value="/getSurveyAuditReportData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject  getSurveyAuditReportData(@RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> dummyMindingMap= new HashMap<Object, Object>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		GridObject jsonResponse = getGridObject.GetGrid( clepAuditResultsService.getSurveyAuditReportData(fromDate, toDate, dummyBindingResult, userObject));
		
		return jsonResponse;
	}
}
