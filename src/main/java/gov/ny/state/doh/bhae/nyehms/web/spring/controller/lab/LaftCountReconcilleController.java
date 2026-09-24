package gov.ny.state.doh.bhae.nyehms.web.spring.controller.lab;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.ClepAuditResultsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftCountReconcilleService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads.FileUploadService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@Controller
public class LaftCountReconcilleController {
	
	@Autowired GetGridObject getGridObject;
	@Autowired	LaftCountReconcilleService laftCountReconcilleService;
	@Autowired	ClepAuditResultsService clepAuditResultsService;
	@Autowired	private FileUploadService fileUploadService;
	
	@RequestMapping("/laftCountReconcile")
	public String getLaftCountReconcile(Map<String, Object> map, HttpServletRequest request) {
		
    	map.put("collPeriodList", laftCountReconcilleService.getPFICollectionPeriods("ALL"));
    	
		return "lab/LaftCountReconcile";
		
	}
	
	@RequestMapping(value="/loadReconcileDashboardData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getReconcileDashboardData(HttpServletRequest request, @RequestParam String reconcileId){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(laftCountReconcilleService.getReconcileDashboardData(userObject, bindingResult, reconcileId));
		return jsonResponse;
	}	
	
	
	@RequestMapping(value="/getCurOpenSurvey.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getCurOpenSurveyData(HttpServletRequest request, @RequestParam String reconcileId, String userId, String labReconciled){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(laftCountReconcilleService.getCurOpenSurveysData(userObject, bindingResult, reconcileId,userId, labReconciled));
		return jsonResponse;
	}	
	
	@RequestMapping(value="/getLabReportedSurveyData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> getLabReportedSurveyData(HttpServletRequest request, @RequestParam String reconcileId, String pfi){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map <String, Object> map = laftCountReconcilleService.getLabReportedSurveyData(userObject, bindingResult, reconcileId, pfi);
		
		return map;
	}

	@RequestMapping(value="/updateLabReconcileData", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String updateLabReconcileData(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
	//	String workerId = Integer.toString(userObject.getWorkerId());
		
        if (request.getParameter("agGridData") == null) {
            return ("agGridData is null");
        }
        String agGridDataStr = request.getParameter("agGridData");
		String reconId = request.getParameter("reconId");
		String pfi = request.getParameter("pfi");
		String labClia = request.getParameter("labClia");
		String laftUserId = request.getParameter("laftUserId");
		String contactDate = request.getParameter("contactDate");
		String reconciled = request.getParameter("reconciled");
		String accessionCompare = request.getParameter("accessionCompare");
		String bhaeComments = request.getParameter("bhaeComments");
		
		String result = laftCountReconcilleService.updateLabReconcileData(agGridDataStr, reconId, pfi, labClia, contactDate, 
				reconciled, accessionCompare, bhaeComments, laftUserId, bindingResult, userObject);
		
		return result;
	}	
	
	@RequestMapping("/laftSendSurveyEmail")
	public String getSendSurveyEmail(Map<String, Object> map, HttpServletRequest request) {
		
		return "lab/LaftSendSurveyEmailList";
		
	}	
	
	@RequestMapping(value="/loadSendSurveyEmailData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getSendSurveyEmailData(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(laftCountReconcilleService.getSendSurveyEmailData(bindingResult, userObject));
		return jsonResponse;
	}	
	
	@RequestMapping(value="/sendSurveyEmailToLab", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String sendSurveyEmailToLab(HttpServletRequest request) throws MessagingException, ParseException{
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

        if (request.getParameter("surveyEmailData") == null) {
            return ("surveyEmailData is null");
        }
        String surveyEmailData = request.getParameter("surveyEmailData");
        String surveyLink = request.getParameter("surveyLink");
        String surveyCompleteDt = request.getParameter("surveyCompleteDt");
        
        String result = laftCountReconcilleService.sendSurveyEmailToLab(surveyEmailData, surveyLink, surveyCompleteDt, userObject.getUserId());
		
		return result;
	}	
	
	@RequestMapping("/uploadLaftSurveyResponse")
	public String uploadLaftSurveyResponsePage(Map<String, Object> map, HttpServletRequest request) {
		
    	map.put("collPeriodList", laftCountReconcilleService.getPFICollectionPeriods("ALL"));
    	
		return "lab/LaftSurveyResponseUpload";
	}	
	
	@RequestMapping(value="/uploadLaftSurveyResponseFile", method = RequestMethod.POST)
	public String  uploadLaftSurveyResponseFile(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, Map<Object, Object> map) throws IOException{
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		session.removeAttribute("resultMap");
		session.removeAttribute("resultMessage");
		
		String collectionPeriod = request.getParameter("collectionPeriod");
		
		Map<Object, Object> scanResultMap = fileUploadService.validateAndScanFile(fileData, bindingResult);
		
		if(scanResultMap != null && scanResultMap.size() > 0 ){
			session.setAttribute("resultMessage", scanResultMap.get("TRANSACTION_MESSAGE"));
			return "lab/LaftSurveyResponseUpload";
		}
		
		Map<Object, Object> resultMap = laftCountReconcilleService.uploadLaftSurveyResponseFile(fileData, collectionPeriod, bindingResult, userObject);
		
		if(resultMap != null && resultMap.size() > 0 ){
			map.put("resultMap", resultMap);
			session.setAttribute("resultMap", resultMap);
		}else{
			session.setAttribute("resultMap", null);
		}
		
    	map.put("collPeriodList", laftCountReconcilleService.getPFICollectionPeriods("ALL"));
				
		return "lab/LaftSurveyResponseUpload";
	}	
	
	
	@RequestMapping(value="/getAccessionList.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject downloadAccessionList(HttpServletRequest request) throws MessagingException, ParseException {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

        String reconId = request.getParameter("reconId");
        String pfi = request.getParameter("pfi");
        
		GridObject jsonResponse = getGridObject.GetGrid(laftCountReconcilleService.getAccessionList(reconId, pfi));
		return jsonResponse;	
	}	
	
	@RequestMapping(value="/refreshNyehmsLabCount", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String refreshNyehmsLabCount(HttpServletRequest request) throws MessagingException, ParseException{
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

        String reconId = request.getParameter("reconId");
        String pfi = request.getParameter("pfi");
        String workerId = request.getParameter("workerId");
        
        String result = laftCountReconcilleService.refreshNyehmsLabCount(reconId, pfi,workerId);
		
		return result;
	}		
	
	@RequestMapping("/addReconcilePfi")
	public String addReconcilePfiPage(Map<String, Object> map, HttpServletRequest request) {
		
    	map.put("collPeriodList", laftCountReconcilleService.getPFICollectionPeriods("ALL"));
    	
		return "lab/LaftReconcileAddPfi";
	}	
	
	@RequestMapping(value="/saveAddReconcilePFI", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> saveAddReconcilePFI(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		String workerId = Integer.toString(userObject.getWorkerId());
		
		String reconId = request.getParameter("reconId");
		String pfi = request.getParameter("pfi");
		String laftUserId = request.getParameter("laftUserId");
		
		Map<String, Object> map = laftCountReconcilleService.saveAddReconcilePFI(reconId, pfi, laftUserId, workerId, bindingResult, userObject);
		
		return map;
	}	

	@RequestMapping(value="/deleteReconcilePFI", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<Object, Object> deleteReconcilePFI(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		String workerId = Integer.toString(userObject.getWorkerId());
		
		String reconId = request.getParameter("reconId");
		String pfi = request.getParameter("pfi");
		
		laftCountReconcilleService.deleteReconcilePFI(reconId, pfi, workerId, bindingResult, userObject);
		
		return targetMap;
	}	
}
