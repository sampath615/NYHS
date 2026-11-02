package gov.ny.state.doh.bhae.nyehms.web.spring.controller.uploads;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ClusterDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads.FileUploadService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@RequiredArgsConstructor
@Controller
public class FileUploadController {
	

	private final FileUploadService fileUploadService;
	
	private final CommonExceptionHandler commonExceptionHandler;
	
	private final ClusterDocumentService clusterDocumentService;
	private final CommonDocumentService commonDocumentService;
	
	@RequestMapping("/updatedDispositions")
	public String updatedDispositionsPage(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("resultMap");
	
	
	
		session.removeAttribute("resultMessage");
	
		
		return "uploads/updatedDispositions";
	}
	
	
	@RequestMapping("/updateHIVWithNYCData")
	public String updateHIVWithNYCData(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("resultMap");
	
	
	
		session.removeAttribute("resultMessage");
	
		
		return "uploads/updateHIVWithNYCData";
	}
	
	
	
	
	@RequestMapping("/uploadRecordSearch")
	public String uploadRecordSearch(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("resultMap");
	
	
	
		session.removeAttribute("resultMessage");
	
		
		return "uploads/uploadRecordSearchSTATENOS";
	}
	
	
	@RequestMapping("/getStatenoHistory")
	public String getStatenoHistory(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("TRANSACTION_RESULT");
		
		session.removeAttribute("TRANSACTION_MESSAGE");
	
		
		return "uploads/uploadUtilityForStatenoHistory";
	}
	
	@RequestMapping("/getCurrentStateno")
	public String getCurrentStateno(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("TRANSACTION_RESULT");
	
		session.removeAttribute("TRANSACTION_MESSAGE");
		
		return "uploads/uploadUtilityForGetCurrentSTATENO";
	}
	
	
	
	@RequestMapping(value="/getCurrentStatenoDataFile", method = RequestMethod.POST)
	public ModelAndView  getCurrentStatenoDataFile(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, HttpServletResponse response, UserObject userObject){
		
		Map<String, String> targetMap = new HashMap<>();
		
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		HttpSession session = request.getSession();
		if(userObject==null) userObject= new UserObject();
		
		
		Map<String, String> currentStatenoData =  fileUploadService.getCurrentStatenoDataFile(fileData, bindingResult, userObject);
		
		String TRANSACTION_RESULT = (String)targetMap.get("TRANSACTION_RESULT");
		
		ModelAndView mav = null;
		
		if("TRANSACTION_FAILURE".equals(TRANSACTION_RESULT)){
			
			mav = new ModelAndView("redirect:getCurrentStateno");
			
			session.setAttribute("TRANSACTION_RESULT", targetMap.get("TRANSACTION_RESULT"));
			
			session.setAttribute("TRANSACTION_MESSAGE", targetMap.get("TRANSACTION_MESSAGE"));
			
		}
		else{
			
			mav = new ModelAndView("generateCurrentStatenoData");
			
	        mav.addObject("currentStatenoData", currentStatenoData);
	        
			
		}
		

		
		
        return mav;
           		
	}
	
	
	@RequestMapping(value="/getSTATENOHistoryDataFile", method = RequestMethod.POST)
	public ModelAndView  getSTATENOHistoryDataFile(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, HttpServletResponse response, UserObject userObject){
		
		Map<String, String> targetMap = new HashMap<>();
		
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		HttpSession session = request.getSession();
		if(userObject==null) userObject= new UserObject();
		
		
		Map<String, List<String>> statenoHistoryData =  fileUploadService.getSTATENOHistoryDataFile(fileData, bindingResult, userObject);
		
		String TRANSACTION_RESULT = (String)targetMap.get("TRANSACTION_RESULT");
		
		ModelAndView mav = null;
		
		if("TRANSACTION_FAILURE".equals(TRANSACTION_RESULT)){
			
			mav = new ModelAndView("redirect:getStatenoHistory");
			
			session.setAttribute("TRANSACTION_RESULT", targetMap.get("TRANSACTION_RESULT"));
			
			session.setAttribute("TRANSACTION_MESSAGE", targetMap.get("TRANSACTION_MESSAGE"));
			
		}
		else{
			
			mav = new ModelAndView("generateStatenoHistoryData");
			
	        mav.addObject("statenoHistoryData", statenoHistoryData);
	        
			
		}
		

		
		
        return mav;
           		
	}
	
	
	@RequestMapping(value="/uploadRecordSearchSTATENOS", method = RequestMethod.POST)
	public String  uploadRecordSearchSTATENOS(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, Map<Object, Object> map, UserObject userObject) throws IOException{
		
		HttpSession session = request.getSession();

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		
		session.removeAttribute("resultMap");
		
		session.removeAttribute("resultMessage");
		
		Map<Object, Object> scanResultMap = fileUploadService.validateAndScanFile(fileData, bindingResult);
		
		if(scanResultMap != null && scanResultMap.size() > 0 ){
			
			session.setAttribute("resultMessage", scanResultMap.get("TRANSACTION_MESSAGE"));
			
			return "uploads/uploadRecordSearchSTATENOS";
			
		}  
		
		Map<String, String> resultMap = fileUploadService.uploadRecordSearchFile(fileData, bindingResult, userObject);
		
		if(resultMap != null && resultMap.size() > 0 ){
			
			map.put("resultMap", resultMap);
			
			session.setAttribute("resultMap", resultMap);

			
		}else{
			
			session.setAttribute("resultMap", null);
		}
		
		
		session.setAttribute("resultMessage", map.get("TRANSACTION_MESSAGE"));
		
		return "uploads/uploadRecordSearchSTATENOS";
	}
	
	
	@RequestMapping(value="/updatedHIVDataWithNYCData", method = RequestMethod.POST)
	public String  updatedHIVDataWithNYCData(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, Map<Object, Object> map, UserObject userObject) throws IOException{
		
		HttpSession session = request.getSession();

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		
		session.removeAttribute("resultMap");
		
		session.removeAttribute("resultMessage");
		
		Map<Object, Object> scanResultMap = fileUploadService.validateAndScanFile(fileData, bindingResult);
		
		if(scanResultMap != null && scanResultMap.size() > 0 ){
			
			session.setAttribute("resultMessage", scanResultMap.get("TRANSACTION_MESSAGE"));
			
			return "uploads/updateHIVWithNYCData";
			
		}  
		
		fileUploadService.updatedHIVDataWithNYCData(fileData, bindingResult, userObject);
		
		
		session.setAttribute("resultMessage", map.get("TRANSACTION_MESSAGE"));
		
		return "uploads/updateHIVWithNYCData";
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/uploadRIDRData")
	public String uploadRIDRData(Map<String, Object> map, HttpServletRequest request) {

		
		return "uploads/uploadRIDRData";
	}
	
	
	@RequestMapping(value="/uploadRIDRData", method = RequestMethod.POST)
	public String  uploadRIDRData(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, Map<Object, Object> map) throws IOException{
		
		HttpSession session = request.getSession();
		
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		
		Map<Object, Object> resultMap = fileUploadService.validateAndScanFile(fileData, bindingResult);
		
		if(resultMap != null && resultMap.size() > 0 ){
			
			session.setAttribute("resultMessage", map.get("TRANSACTION_MESSAGE"));
			
		} else {

			fileUploadService.uploadRIDRData(fileData, bindingResult, userObject);
		}
		
		return "uploads/uploadRIDRData";
	}
	
	
	
	
	
	@RequestMapping(value="/updatedDispositionsResults.pdf", method = RequestMethod.GET, produces="application/pdf")
	public ModelAndView  updatedDispositionsResults(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, UserObject userObject){
		
		response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename= Updated Messages");

		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		HttpSession session = request.getSession();
		if(userObject==null) userObject= new UserObject();
		
		
		Map<Object, Object> resultMap =  (Map<Object, Object>)session.getAttribute("resultMap");
		
		String resultMessage = (String)session.getAttribute("resultMessage");;
				
		ModelAndView mav = new ModelAndView("updatedMessageResultsPdfView");
		
        mav.addObject("resultMap", resultMap);
        
        mav.addObject("resultMessage", resultMessage);
        
        
        return mav;
           		
	}
	
	
	@RequestMapping(value="/uploadedRecordSearchStatnos.pdf", method = RequestMethod.GET, produces="application/pdf")
	public ModelAndView  uploadedRecordSearchStatnos(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, UserObject userObject){
		
		response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename= Updated Messages");

		
		HttpSession session = request.getSession();
		if(userObject==null) userObject= new UserObject();
		
		
		Map<Object, Object> resultMap =  (Map<Object, Object>)session.getAttribute("resultMap");
		
		String resultMessage = (String)session.getAttribute("resultMessage");;
				
		ModelAndView mav = new ModelAndView("uploadedRecordSearchStatnosPdfView");
		
        mav.addObject("resultMap", resultMap);
        
        mav.addObject("resultMessage", resultMessage);
        
        
        return mav;
           		
	}
	
	
	
	
	@RequestMapping("/uploadCluster")
	public String uploadCluster(Map<String, Object> map, HttpServletRequest request) {

		map.put("clusterUploadVO", new ClusterUploadVO());
		map.put("relatednessData", clusterDocumentService.getRelatednessData());
		map.put("moleTimeFrameData", clusterDocumentService.getMoleTimeFrameData());
		
		map.put("transCategData", clusterDocumentService.getTransmissionCategoryData());
		map.put("codedSexAtBirthValues", commonDocumentService.getCodedSexAtBirthValues());
		map.put("eharsRaceValues", clusterDocumentService.getEharsRaceValues());
		map.put("eharsEthnicityValues", clusterDocumentService.getEharsEthnicityValues());
		
		HttpSession session = request.getSession();
		session.removeAttribute("resultMap");
		session.removeAttribute("resultMessage");
		
		return "uploads/uploadCluster";
	}
	
	@RequestMapping(value="/uploadCluster", method = RequestMethod.POST)
	public String  uploadCluster(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, 
			@ModelAttribute("clusterUploadVO") ClusterUploadVO clusterUploadVO, Map<Object, Object> map, BindingResult result) throws IOException {
	
		HttpSession session = request.getSession();
		session.removeAttribute("resultMap");
		session.removeAttribute("resultMessage");		
		
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		
		boolean uploadSucess = false;
		
		String uploadType = clusterUploadVO.getUploadType();
		String clusterType = clusterUploadVO.getClusterType();
		boolean isClusterIdExist = false;
		String clusterId = null;
		Map<Object, Object> resultMap = new HashMap<>();
		
		if (CommonUtil.isNotNullAndEmpty(uploadType) && uploadType.equalsIgnoreCase("N")) {
			clusterUploadVO.setExistingClusterId("");
			
			clusterId = request.getParameter("clusterId");
			isClusterIdExist = fileUploadService.validateClusterId(clusterId);
			if (isClusterIdExist) {
				commonExceptionHandler.inspectDBTransacionResult(-1, "ClusterId : " + clusterId + " already exist. Please provide a unique cluster id.", bindingResult);
			} 			
			
		} else if (CommonUtil.isNotNullAndEmpty(uploadType) && uploadType.equalsIgnoreCase("E")) {
			clusterUploadVO.setClusterId("");
		} else {
			commonExceptionHandler.inspectDBTransacionResult(-1, "No upload Type is selected, please select upload Type", bindingResult);
			return "uploads/uploadCluster";
		}
		
		if (uploadType.equalsIgnoreCase("N") && isClusterIdExist) {
			
			commonExceptionHandler.inspectDBTransacionResult(-1, "ClusterId : " + clusterId + " already exist. Please provide a unique cluster id.", bindingResult);
			
		} else if (clusterType.equals("CDC_M") || clusterType.equals("BHAE_M")){
		
			clusterUploadVO.setUploadClusterFile(fileData.getOriginalFilename());
			
			Map<Object, Object> scanResultMap = fileUploadService.validateAndScanFile(fileData, bindingResult);
			
			if(scanResultMap != null && scanResultMap.size() > 0 ){
				session.setAttribute("resultMessage", map.get("TRANSACTION_MESSAGE"));
				return "uploads/uploadCluster";
			} 
			
			resultMap = fileUploadService.uploadClusterData(fileData, clusterUploadVO, bindingResult, userObject, session);
			
			if(resultMap != null && resultMap.size() > 0 ){
				map.put("resultMap", resultMap);
				session.setAttribute("resultMap", resultMap);
			}else{
				session.setAttribute("resultMap", null);
			}			
			
		} else if (clusterType.equals("BHAE_TS")){
			
			uploadSucess = fileUploadService.insertTSCriteria(clusterUploadVO, bindingResult, userObject, session);
		}
		
		if ( (clusterType.equals("BHAE_TS") && uploadSucess) || ( !clusterType.equals("BHAE_TS") && resultMap.size() == 0 ))  {
			map.put("clusterUploadVO", new ClusterUploadVO());
		} else {
			if(uploadType.equalsIgnoreCase("E") && CommonUtil.isNotNullAndEmpty(clusterUploadVO.getClusterType())){
				List<JSONObject> clusterIdsObj = clusterDocumentService.getExistingClusterIdsByType(clusterUploadVO.getClusterType());
				map.put("clusterIdsObj", clusterIdsObj);
				map.put("clusterUploadVO", clusterUploadVO);
			}
		}
		
		map.put("relatednessData", clusterDocumentService.getRelatednessData());
		map.put("moleTimeFrameData", clusterDocumentService.getMoleTimeFrameData());
		
		map.put("transCategData", clusterDocumentService.getTransmissionCategoryData());
		map.put("codedSexAtBirthValues", commonDocumentService.getCodedSexAtBirthValues());
		map.put("eharsRaceValues", clusterDocumentService.getEharsRaceValues());
		map.put("eharsEthnicityValues", clusterDocumentService.getEharsEthnicityValues());
		
		return "uploads/uploadCluster";
	}	
	
	
	
	
	@RequestMapping("/uploadPSConfirmedStds")
	public String uploadPSConfirmedStdsPage(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.removeAttribute("resultMap");
		session.removeAttribute("resultMessage");
		
		return "uploads/uploadPSConfirmedStds";
	}
	
	
	@RequestMapping(value="/uploadPSConfirmedStdsFile", method = RequestMethod.POST)
	public String  uploadPSConfirmedStdsFile(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, Map<Object, Object> map, UserObject userObject) throws IOException{
		
		HttpSession session = request.getSession();

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		session.removeAttribute("resultMap");
		session.removeAttribute("resultMessage");
		
		Map<Object, Object> scanResultMap = fileUploadService.validateAndScanFile(fileData, bindingResult);
		
		if(scanResultMap != null && scanResultMap.size() > 0 ){
			session.setAttribute("resultMessage", scanResultMap.get("TRANSACTION_MESSAGE"));
			return "uploads/uploadPSConfirmedStds";
		}
		
		Map<Object, Object> resultMap = fileUploadService.uploadPSConfirmedStdsFile(fileData, bindingResult, userObject);
		
		if(resultMap != null && resultMap.size() > 0 ){
			map.put("resultMap", resultMap);
			session.setAttribute("resultMap", resultMap);
		}else{
			session.setAttribute("resultMap", null);
		}
				
		return "uploads/uploadPSConfirmedStds";
	}
	
	@RequestMapping(value="/psConfirmedSTDDataErrors.pdf", method = RequestMethod.GET, produces="application/pdf")
	public ModelAndView  psConfirmedSTDDataErrors(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, UserObject userObject){
		
		response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename= PS Confirmed STDs Data Errors");

		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		HttpSession session = request.getSession();
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> resultMap =  (Map<Object, Object>)session.getAttribute("resultMap");
		String resultMessage = (String)session.getAttribute("resultMessage");;
		ModelAndView mav = new ModelAndView("psConfirmedSTDErrorPdfView");
        mav.addObject("resultMap", resultMap);
        mav.addObject("resultMessage", resultMessage);
        
        return mav;
	}
	
	@RequestMapping("/uploadNYCSurvReject")
	public String uploadNYCSurvRejectPage(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.removeAttribute("resultMap");
		session.removeAttribute("resultMessage");
		
		return "uploads/uploadNYCSurvReject";
	}
	
	
	@RequestMapping(value="/uploadNYCSurvRejectFile", method = RequestMethod.POST)
	public String  uploadNYCSurvRejectFile(HttpServletRequest request, @RequestParam("fileData") MultipartFile fileData, Map<Object, Object> map, UserObject userObject) throws IOException{
		
		HttpSession session = request.getSession();

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		session.removeAttribute("resultMap");
		session.removeAttribute("resultMessage");
		
		Map<Object, Object> scanResultMap = fileUploadService.validateAndScanFile(fileData, bindingResult);
		
		if(scanResultMap != null && scanResultMap.size() > 0 ){
			session.setAttribute("resultMessage", scanResultMap.get("TRANSACTION_MESSAGE"));
			return "uploads/uploadNYCSurvReject";
		}
		
		Map<Object, Object> resultMap = fileUploadService.uploadNYCSurvRejectFile(fileData, bindingResult, userObject);
		
		if(resultMap != null && resultMap.size() > 0 ){
			map.put("resultMap", resultMap);
			session.setAttribute("resultMap", resultMap);
		}else{
			session.setAttribute("resultMap", null);
		}
				
		return "uploads/uploadNYCSurvReject";
	}	
}