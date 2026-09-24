package gov.ny.state.doh.bhae.nyehms.web.spring.controller.review;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.EPRFFollowUpDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.EPRFOOCDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.DocumentReviewTransactionHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
/*
 * Spring controller class for system message
 */
@Controller
public class ProviderPortalReviewController {

	
	@Autowired
	EPRFOOCDocumentService ePRFOOCDocumentService;
	
	@Autowired
	DocumentReviewTransactionHelper documentReviewTransactionHelper;

	
	@RequestMapping("/providerPortalFileReview")
	public String providerPortalFileReview(Map<String, Object> map, HttpServletRequest request) {
		
		
		return "review/eprf/ProviderPortalFileReview";
		
	}
	
	
	@RequestMapping("/getOOCDNLTCFollowUp")
	public String getOOCDNLTCFollowUp(Map<String, Object> map, HttpServletRequest request) {
		
		
		
		List<Map<String, String>> oocDnltcFiles = ePRFOOCDocumentService.getOOCDNLTCFileNames("FollowUp");
		
		map.put("oocDnltcFiles", oocDnltcFiles);
		

		HttpSession session = request.getSession();
		
		List<Map<String, String>> internalConclusionList = ePRFOOCDocumentService.getInternalConclusionList();
		
		List<Map<String, String>> careStatusList = ePRFOOCDocumentService.getCareStatusList();
		
		session.setAttribute("internalConclusionList", internalConclusionList);
		
		session.setAttribute("careStatusList", careStatusList);
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "review/eprf/OOCDNLTCFollowUp";
		
	}
	
	
	@RequestMapping("/getOOCDNLTCReview")
	public String getOOCDNLTCReview(Map<String, Object> map, HttpServletRequest request) {
		
		
		List<Map<String, String>> oocDnltcFiles = ePRFOOCDocumentService.getOOCDNLTCFileNames("Review");
		
		map.put("oocDnltcFiles", oocDnltcFiles);
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		
		return "review/eprf/OOCDNLTCFollowUp";
		
	}
	
	
	@RequestMapping("/generateSNPFile")
	public String generateSNPFile(Map<String, Object> map, HttpServletRequest request) {
		
		

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		List<Map<String, String>> snpFiles = ePRFOOCDocumentService.getSNPFileNames();
		
		map.put("snpFiles", snpFiles);
		
		return "review/eprf/generateSNPFile";
		
	}

	

	
	@RequestMapping(value="/getOOCDocumentsToReview.json", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getOOCDocumentsToReview(HttpServletRequest request, Map<String, Object> map, @RequestParam  String documentFileName, @RequestParam String documentFileType){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return  ePRFOOCDocumentService.getOOCDocumentsToReview(documentFileName, documentFileType, bindingResult, userObject);
		
		
		//return "review/ProviderPortalReviewTable";
	}
	
	@RequestMapping(value="/updateOOCDocumentReviewStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateOOCDocumentReviewStatus(HttpServletRequest request, @RequestParam String documentFileName, @RequestParam String documentFileType, @RequestParam int processStatus, @RequestParam String facilityCategory){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		documentReviewTransactionHelper.updateOOCDocumentReviewStatus(documentFileName, documentFileType, processStatus, facilityCategory, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	@RequestMapping(value="/loadSNPData.html", method = RequestMethod.POST)
	public String loadSNPData(HttpServletRequest request, Map<String, Object> map, @RequestParam  String documentFileName){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		List<Map<String, Object>> SNPData =  ePRFOOCDocumentService.loadSNPData(documentFileName, bindingResult, userObject);
		
		map.put("docList", SNPData);
		
		session.setAttribute("docList", SNPData);
		
		session.setAttribute("documentFileName", documentFileName);
		
		return "review/eprf/SNPData";
	}
	
	
	@RequestMapping(value="/getOOCDNLTCReviewData.html", method = RequestMethod.POST)
	public String getOOCDNLTCReviewData(HttpServletRequest request, Map<String, Object> map, @RequestParam  String documentFileName, @RequestParam(required = false) String origin){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		String redirectPage =  documentReviewTransactionHelper.getOOCDNLTCReviewData(map, documentFileName, origin, bindingResult, request);
		
		
		
		return redirectPage;
	}
	
	
	@RequestMapping(value="/snpFeedbackFile.xlsx", method = RequestMethod.GET)
	public ModelAndView  snpFeedbackFile(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map){
		

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		
		List<Map<String, Object>> docList =  (List<Map<String, Object>>)session.getAttribute("docList");
		
		String documentFileName = (String)session.getAttribute("documentFileName");;
				
		ModelAndView mav = new ModelAndView("downloadFeedbackSNPDataView");
		
        mav.addObject("docList", docList);
        
        mav.addObject("documentFileName", documentFileName);
        
        
        return mav;
           		
	}
	
	
	@RequestMapping(value="/updateEprfFollowUpData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateEprfFollowUpData(HttpServletRequest request, @ModelAttribute("ePRFFollowUpDocVO") EPRFFollowUpDocVO ePRFFollowUpDocVO ) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		ePRFOOCDocumentService.updateEprfFollowUpData(ePRFFollowUpDocVO, bindingResult, userObject);
		
		return targetMap;

	}
	
	
	@RequestMapping(value="/updateEprfFollowUpFileData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateEprfFollowUpFileData(HttpServletRequest request, @ModelAttribute("ePRFFollowUpDocVO") EPRFFollowUpDocVO ePRFFollowUpDocVO ) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		ePRFOOCDocumentService.updateEprfFollowUpFileData(ePRFFollowUpDocVO, bindingResult, userObject);
		
		return targetMap;

	}
	
	
	
	
	
	@RequestMapping(value="/sendEmailToBureauDirector.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> sendEmailToBureauDirector(HttpServletRequest request, @RequestParam String message, @RequestParam String documentFileName) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		documentReviewTransactionHelper.sendEmailToBureauDirector(message, documentFileName,  bindingResult, userObject);
		
		return targetMap;

	}
	
	
	
	@RequestMapping(value="/generateEPRFSummarySpreadsheet.xlsx", method = RequestMethod.GET)
	public ModelAndView  generateEPRFSummarySpreadsheet(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, @RequestParam String documentFileName){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		
		List<EPRFFollowUpDocVO> ePRFFollowUpDocVOList = ePRFOOCDocumentService.getCompleteEprfDocDataWithHivInfo(documentFileName);
		
		//List<Map<String, Object>> docList =  (List<Map<String, Object>>)session.getAttribute("docList");
		
		ModelAndView mav = new ModelAndView("generateEPRFSummarySpreadsheet");
		
        mav.addObject("ePRFFollowUpDocVOList", ePRFFollowUpDocVOList);
        
        mav.addObject("documentFileName", documentFileName);
        
        
        return mav;
           		
	}
	
}
