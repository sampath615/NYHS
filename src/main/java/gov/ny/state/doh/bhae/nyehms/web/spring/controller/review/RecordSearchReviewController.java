package gov.ny.state.doh.bhae.nyehms.web.spring.controller.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.RecordSearchResultsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.DocumentReviewTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.RecordSearchReviewService;
/*
 * Spring controller class for system message
 */
@Controller
public class RecordSearchReviewController {

	
	@Autowired
	RecordSearchReviewService recordSearchReviewService;
		
	@Autowired
	DocumentReviewTransactionHelper documentReviewTransactionHelper;
	/**
	 * 
	 * @param stateno
	 * @param requestComments
	 * @param flagStatus
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/changeRecordSearchStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> changeRecordSearchStatus(
			@RequestParam String stateno,
			@RequestParam String requestComments,
			@RequestParam String flagStatus,
			@RequestParam String flagRequired,
			HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		if("Y".equals(flagRequired)){
			recordSearchReviewService.changeRecordSearchStatus(stateno, requestComments, "Manual-Warning", flagStatus, bindingResult, userObject);
		}else{
			recordSearchReviewService.changeRecordSearchStatus(stateno, requestComments, "Manual", flagStatus, bindingResult, userObject);
		}
		return targetMap;
	}
	
	
	@RequestMapping(value="/changeRecordSearchStatusForMultipleStatenos.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> changeRecordSearchStatusForMultipleStatenos(
			@RequestParam("statenoList[]")  String[] statenoList,
			@RequestParam String requestComments,
			HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		recordSearchReviewService.changeRecordSearchStatusForMultipleStatenos(statenoList, requestComments, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/discardCDCFeedbackRecords.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> discardCDCFeedbackRecords(
			@RequestParam("cdcRecords[]")  String[] cdcRecords,
			HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		recordSearchReviewService.discardCDCFeedbackRecords(cdcRecords, bindingResult, userObject);
		return targetMap;
	}
	
	
	
	
	
	@RequestMapping(value="/generateCDCFile.xlsx", method = RequestMethod.POST)
	public ModelAndView generateCDCFile(
			@RequestParam String statenoList, @RequestParam String cdcFileName, 
			HttpServletRequest request, 
			HttpServletResponse response)
	{
		
		Cookie myCookie =
      		  new Cookie("filedownload", "false");
		
		response.addCookie(myCookie);
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Map<String, String>> cdcReturnData = documentReviewTransactionHelper.generateCDCReturnData(statenoList, cdcFileName, bindingResult, userObject);
		
		
		ModelAndView mav = null;
		
		Cookie myCookie2 = null;
		
		
		String TRANSACTION_RESULT = (String)targetMap.get("TRANSACTION_RESULT");
		
		if("TRANSACTION_SUCCESS".equals(TRANSACTION_RESULT)){
			
			mav = new ModelAndView("generateCDCReturnFile");
			
	        mav.addObject("cdcReturnData", cdcReturnData);
	        
	        mav.addObject("filename", cdcFileName);
	        
	        
		}else{
			
			mav = new ModelAndView("reviewRecordSearch");
			
	        
		}
		
        myCookie =
        		  new Cookie("filedownload", "true");
        		
		
        Cookie myCookie1 =
      		  new Cookie("TRANSACTION_MESSAGE", (String)targetMap.get("TRANSACTION_MESSAGE"));
                
        

        myCookie2 =
        		  new Cookie("TRANSACTION_RESULT", TRANSACTION_RESULT);
        
        response.addCookie(myCookie);
        

        response.addCookie(myCookie1);
        

        response.addCookie(myCookie2);
        
        return mav;
		
	}
	

	@RequestMapping(value="/downloadCDCReturnFile.xlsx", method = RequestMethod.GET)
	public ModelAndView downloadCDCReturnFile(@RequestParam String cdcFileName, 
			HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Map<String, String>> cdcReturnData = recordSearchReviewService.downloadCDCReturnFile(cdcFileName, bindingResult, userObject);
				
		ModelAndView mav = new ModelAndView("generateCDCReturnFile");
		
        mav.addObject("cdcReturnData", cdcReturnData);
        
        mav.addObject("filename", cdcFileName);
        
        
        return mav;
		
	}
	
	/**
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/reviewRecordSearch")	
	public String reviewRecordSearch(
			HttpServletRequest request,
			Map<String, Object> map)
	{
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		
		map.put("addressFieldVO", addressFieldVO);
		
		return "review/recordSearchReview";
	}
	
	@RequestMapping(value="/cdcPendingFile")	
	public String cdcPendingFile(
			HttpServletRequest request,
			Map<String, Object> map)
	{
		

		List<Map<String, String>> fileNames= recordSearchReviewService.getCDCPendingReturnFileNames();
		
		map.put("fileNames", fileNames);
		
		
		return "review/cdcPendingFile";
	}
	
	
	@RequestMapping(value="/uploadReturnFile")	
	public String uploadReturnFile(
			HttpServletRequest request,
			Map<String, Object> map)
	{
		
		List<Map<String, String>> fileNames= recordSearchReviewService.getCDCPendingReturnFileNames();
		
		map.put("fileNames", fileNames);
		
		return "review/uploadReturnFile";
	}
	
	
	@RequestMapping(value="/resolveCDCFeedback")	
	public String resolveCDCFeedback(
			HttpServletRequest request,
			Map<String, Object> map)
	{
		
		List<String> stateCodes = recordSearchReviewService.getCDCReportingStateCodesToProcess();
		
		map.put("stateCodes", stateCodes);
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "review/resolveCDCFeedback";
	}
	
	
	@RequestMapping(value="/getRecordSearchViewResults.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RecordSearchResultsVO> getRecordSearchViewResults(
			HttpServletRequest request, @RequestParam String stateValue)
	{
		return recordSearchReviewService.getRecordSearchViewResults(stateValue);
	}
	
	
	
	
	
	@RequestMapping(value="/getCDCPendingReturnData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<String, String>> getCDCPendingReturnData(
			HttpServletRequest request, @RequestParam String cdcFileName)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return recordSearchReviewService.getCDCPendingReturnData(cdcFileName, bindingResult, userObject);
	}
	
	
	
	
	@RequestMapping(value="/uploadCDCFileData", method = RequestMethod.POST)
	public String  uploadCDCFileData(HttpServletRequest request, @RequestParam String inputFileName, @RequestParam("uploadedFile") MultipartFile  uploadedFile, Map<Object, Object> map){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		

		documentReviewTransactionHelper.uploadCDCFileData(inputFileName, uploadedFile, bindingResult, userObject);
		
		
		List<Map<String, String>> fileNames = recordSearchReviewService.getCDCPendingReturnFileNames();
		
		map.put("fileNames", fileNames);
		
		
		
		return "review/uploadReturnFile";
	}
	
	
	

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getRecordSearchStatenos.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getRecordSearchStatenos(
			HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return recordSearchReviewService.getRecordSearchStatenos(bindingResult, userObject);
	}

	/**
	 * 
	 * @param stateno
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getRecordSearchStatenoFlagHistory.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<String, String>> getRecordSearchStatenoFlagHistory(
			@RequestParam String stateno,
			HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return recordSearchReviewService.getRecordSearchStatenoFlagHistory(stateno, bindingResult, userObject);
	}
	
	@RequestMapping(value="/rejectCDCFile.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> rejectCDCFile(
			@RequestParam String cdcFileName,
			HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		documentReviewTransactionHelper.rejectCDCFile(cdcFileName, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	
	@RequestMapping(value="/getRecordSearchStatenoHistory.html", method = RequestMethod.POST)	
	public String getRecordSearchStatenoHistory(
			Map<String, Object> pMap,
			@RequestParam String stateNo,
			HttpServletRequest request)
	{
		HttpSession session =request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
				
		List<Map<String, String>> recordSearchStatenoHistory = recordSearchReviewService.getRecordSearchStatenoHistory(stateNo,  bindingResult,  userObject);

		pMap.put("recordSearchStatenoHistory", recordSearchStatenoHistory);
		
		return "review/RecordSearchStatenoHistory";
	}
}
