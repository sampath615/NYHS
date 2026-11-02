package gov.ny.state.doh.bhae.nyehms.web.spring.controller.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ADAPDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.DocumentReviewTransactionHelper;

@RequiredArgsConstructor
@Controller
public class ADAPNYCFeedBackController {
	

	
	private final ADAPDocumentService aDAPDocumentService;
	

	private final DocumentReviewTransactionHelper documentReviewTransactionHelper;
	
	@RequestMapping("/createNYCFile")
	public String createNYCFile(Map<String, Object> map, HttpServletRequest request) {
		
		List<String> fileNames = aDAPDocumentService.getFileNameNotShippedToNYC("notShipped");
		
		map.put("fileNames", fileNames);

		
		return "review/adap/createNYCFilePage";
		
	}
	
	@RequestMapping("/nycFileSummary")
	public String nycFileSummary(Map<String, Object> map, HttpServletRequest request) {
		
		List<String> fileNames = aDAPDocumentService.getADAPNYCFileNames();
		
		
		map.put("fileNames", fileNames);

		
		return "review/adap/nycFileSummaryPage";
		
	}
	
	
	
	@RequestMapping("/uploadNYCFile")
	public String uploadNYCFile(Map<String, Object> map, HttpServletRequest request) {
		

		
		return "review/adap/uploadNYCFilePage";
		
	}
	
	@RequestMapping("/reviewNYCFile")
	public String reviewNYCFile(Map<String, Object> map, HttpServletRequest request) {
		
		List<String> fileNames = aDAPDocumentService.getFileNameNotShippedToNYC("Shipped");
		
		map.put("fileNames", fileNames);

		
		return "review/adap/reviewNYCFilePage";
		
	}	
	
	@RequestMapping(value="/getADAPDocumentIdToReview.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> getADAPDocumentIdToReview(HttpServletRequest request, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
 
		Map<String, Object> documentAndItsDetails = aDAPDocumentService.getADAPDocumentIdToReview(bindingResult, userObject);
		
		return documentAndItsDetails;
	}
	
	@RequestMapping(value="/updateNYCADAPDocumentMatchStatus.json",  produces = "application/json")
	public @ResponseBody Map<Object, Object> updateNYCADAPDocumentMatchStatus(HttpServletRequest request, @RequestParam int documentId, @RequestParam String reviewCd, @RequestParam(required = false) String cityno, @RequestParam(required = false) String comments, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
 
		aDAPDocumentService.updateNYCADAPDocumentMatchStatus(documentId, reviewCd, cityno, comments, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/checkCityNoExists.json",  produces = "application/json")
	public @ResponseBody Map<Object, Object> checkCityNoExists(HttpServletRequest request, @RequestParam String cityno, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
 
		aDAPDocumentService.checkCityNoExists(cityno, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	/**
	 * 
	 * @param pMap
	 * @param stateNo
	 * @param startDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loadADAPRiskContent.html", method = RequestMethod.POST)	
	public String loadADAPRiskContent(
			Map<String, Object> pMap,
			@RequestParam int documentId,
			@RequestParam String origin,
			HttpServletRequest request, UserObject userObject)
	{
		HttpSession session =request.getSession();
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		PersonRisksVO personRisksVO = null;
		
		personRisksVO = aDAPDocumentService.loadADAPRiskContent(documentId,  origin,  bindingResult,  userObject,  session.getId());

		
		if(personRisksVO == null){
			personRisksVO = new PersonRisksVO();
			
			return "person/NoPersonRisksView";
		}
		//storing the objects in Map scope to access these variables in the JSP
		pMap.put("personRisksObj", personRisksVO);
		return "person/PersonRisksView";
	}
	
	
	
	@RequestMapping(value="/generateADAPNYCFile.xlsx", method = RequestMethod.GET)
	public ModelAndView generateADAPNYCFile(
			@RequestParam String fileName, @RequestParam String nycShiStatus,
			HttpServletRequest request, 
			HttpServletResponse response)
	{
		
		Cookie myCookie =
      		  new Cookie("filedownload", "false");
		
		response.addCookie(myCookie);
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Map<String, String>> adapNYCData = documentReviewTransactionHelper.generateADAPNYCFile(fileName, nycShiStatus, bindingResult, userObject);
		
		
		ModelAndView mav = null;
		
		Cookie myCookie2 = null;
		
		
		String TRANSACTION_RESULT = (String)targetMap.get("TRANSACTION_RESULT");
		
		if("TRANSACTION_SUCCESS".equals(TRANSACTION_RESULT)){
			
			mav = new ModelAndView("generateADAPNYCDataFile");
			
	        mav.addObject("adapNYCData", adapNYCData);
	        
	        mav.addObject("filename", fileName);
	        
	        
		}
		else{
			
			
			
			mav = new ModelAndView("redirect:createNYCFile");
			
	        
		}
		
        myCookie =
        		  new Cookie("filedownload", "true");
        		
		
        Cookie myCookie1 =
      		  new Cookie("TRANSACTION_MESSAGE", CommonUtil.encodeCookieValue((String)targetMap.get("TRANSACTION_MESSAGE")));
                
        

        myCookie2 =
        		  new Cookie("TRANSACTION_RESULT", TRANSACTION_RESULT);
        
        response.addCookie(myCookie);
        

        response.addCookie(myCookie1);
        

        response.addCookie(myCookie2);
        
        return mav;
		
	}
	
	
	
	@RequestMapping(value="/generateADAPNYCDiscrepancyFile.xlsx", method = RequestMethod.GET)
	public ModelAndView generateADAPNYCFile(
			@RequestParam String fileName, 
			HttpServletRequest request, 
			HttpServletResponse response)
	{
		
		Cookie myCookie =
      		  new Cookie("filedownload", "false");
		
		response.addCookie(myCookie);
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Map<String, String>> adapNYCData = documentReviewTransactionHelper.generateADAPNYCDiscrepancyFile(fileName, bindingResult, userObject);
		
		
		ModelAndView mav = null;
		
		Cookie myCookie2 = null;
		
		
		String TRANSACTION_RESULT = (String)targetMap.get("TRANSACTION_RESULT");
		
		if("TRANSACTION_SUCCESS".equals(TRANSACTION_RESULT)){
			
			mav = new ModelAndView("generateADAPNYCDataFile");
			
	        mav.addObject("adapNYCData", adapNYCData);
	        
	        mav.addObject("filename", fileName);
	        
	        
		}
		else{
			
			
			
			mav = new ModelAndView("redirect:createNYCFile");
			
	        
		}
		
        myCookie =
        		  new Cookie("filedownload", "true");
        		
		
        Cookie myCookie1 =
      		  new Cookie("TRANSACTION_MESSAGE",CommonUtil.encodeCookieValue((String)targetMap.get("TRANSACTION_MESSAGE")));
                
        

        myCookie2 =
        		  new Cookie("TRANSACTION_RESULT", TRANSACTION_RESULT);
        
        response.addCookie(myCookie);
        

        response.addCookie(myCookie1);
        

        response.addCookie(myCookie2);
        
        return mav;
		
	}
	
	
	
	
	@RequestMapping(value="/generateNYCFileSummary.xlsx", method = RequestMethod.GET)
	public ModelAndView generateNYCFileSummary(
			@RequestParam String fileName, 
			HttpServletRequest request, 
			HttpServletResponse response, UserObject userObject)
	{
		
		Cookie myCookie =
      		  new Cookie("filedownload", "false");
		
		response.addCookie(myCookie);
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Map<String, String>> nycFileSummaryData = aDAPDocumentService.generateNYCFileSummary(fileName, bindingResult, userObject);
		
		List<Map<String, String>> nycShippedMatchSummaryData = aDAPDocumentService.generateADAPNYCShippedMatchSummary(fileName, bindingResult, userObject);
		
		List<Map<String, String>> generateADAPNYCQualityStageMatchSummary = aDAPDocumentService.generateADAPNYCQualityStageMatchSummary(fileName, bindingResult, userObject);
		
		
		
		
		ModelAndView mav = null;
		
		Cookie myCookie2 = null;
		
		
		String TRANSACTION_RESULT = (String)targetMap.get("TRANSACTION_RESULT");
		
		if("TRANSACTION_SUCCESS".equals(TRANSACTION_RESULT)){
			
			mav = new ModelAndView("generateADAPNYCSummaryFile");
			
	        mav.addObject("nycFileSummaryData", nycFileSummaryData);
	        
	        
	        mav.addObject("nycShippedMatchSummaryData", nycShippedMatchSummaryData);
	        
	        mav.addObject("generateADAPNYCQualityStageMatchSummary", generateADAPNYCQualityStageMatchSummary);
	        
	        
	        
	        mav.addObject("filename", fileName);
	        
	        
		}else{
			
			mav = new ModelAndView("nycFileSummaryPage");
			
	        
		}
		
        myCookie =
        		  new Cookie("filedownload", "true");
        		
		
        Cookie myCookie1 =
      		  new Cookie("TRANSACTION_MESSAGE",CommonUtil.encodeCookieValue((String)targetMap.get("TRANSACTION_MESSAGE")));
                
        

        myCookie2 =
        		  new Cookie("TRANSACTION_RESULT", TRANSACTION_RESULT);
        
        response.addCookie(myCookie);
        

        response.addCookie(myCookie1);
        

        response.addCookie(myCookie2);
        
        return mav;
		
	}
	
	
	
	
	@RequestMapping(value="/uploadNYCReturnFileData", method = RequestMethod.POST)
	public String  uploadNYCReturnFileData(HttpServletRequest request, @RequestParam("uploadedFile") MultipartFile uploadedFile, Map<Object, Object> map, UserObject userObject){
		

		BindingResult bindingResult = new MapBindingResult(map, "targetMap");
		

		documentReviewTransactionHelper.uploadNYCReturnFileData(uploadedFile, bindingResult, userObject);
		
		

		
		return "review/adap/uploadNYCFilePage";
		
	}
	
	
	
	@RequestMapping(value="/getFileNamesNotShippedToNYC.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<String> getFileNamesNotShippedToNYC(HttpServletRequest request){
		
		
		return aDAPDocumentService.getFileNameNotShippedToNYC("notShipped");
		
		

	}
}
