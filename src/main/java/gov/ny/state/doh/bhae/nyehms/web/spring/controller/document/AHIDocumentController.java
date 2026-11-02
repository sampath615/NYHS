package gov.ny.state.doh.bhae.nyehms.web.spring.controller.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHICiStiVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHINegInd900TestHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AhiAlertTrackingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.AHIDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.DocumentReviewTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@RequiredArgsConstructor
@Controller
public class AHIDocumentController {
	
	private static final Logger log = LoggerFactory.getLogger(AHIDocumentController.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();
	
	private final GetGridObject getGridObject;
	
	private final DocumentReviewTransactionHelper documentReviewTransactionHelper;
	private final AHIDocumentService ahiDocumentService;
	private final ACRFDocumentService acrfFDocumentService;
	private final CommonDocumentService commonDocumentService;
	private final DocumentTransactionHelper documentTransactionHelper;
	private final PersionService personService;

	@RequestMapping(value = "/getAHIForm.html")
	public String getAHIDocAsHTML(@RequestParam(required=false) Integer docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		log.debug("{}  incoming AHI Doc ID is {}", nyehmsLog, docId);
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		
		List<Map<String, String>> ahiCiStiConditionList = ahiDocumentService.getAHICISTIConditionList();
		List<Map<String, String>> ahiCIReasonPatientTestedList = ahiDocumentService.getAHICIReasonPatientTestedList();
		List<Map<String, String>> ahiNegInd900TestList = ahiDocumentService.getAHINegInd900TestTypeList();
		List<Map<String, String>> subFacilities = acrfFDocumentService.getStandardFacilityDetails();
		Map<String, String> codedCountries = commonDocumentService.getEharsCodedCountryValues();
		
		AHIDocVO aHIDocVO = null;
		String stateNo = null;
		Map<String, Object> nyehmsStatenoDetails = null;
		if (docId == null) {
			aHIDocVO = new AHIDocVO();
			
			List<AHICiStiVO> ahiCiStiList = new ArrayList<>();
			
			ahiCiStiList.add(new AHICiStiVO());
			
			List<AHINegInd900TestHistoryVO> ahiNegInd900TestHistoryList = new ArrayList<>();
			
			ahiNegInd900TestHistoryList.add(new AHINegInd900TestHistoryVO());
			
			String origin = request.getParameter("origin");
			
			if("Print".equals(origin)){
				ahiCiStiList.add(new AHICiStiVO());
				ahiCiStiList.add(new AHICiStiVO());
				
				
			}
			
			aHIDocVO.setCiStiConditionList(ahiCiStiList);
			aHIDocVO.setNegInd900TestHisList(ahiNegInd900TestHistoryList);
			
			stateNo = request.getParameter("stateNo");
			
			nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfStateno(null, stateNo, null, userObject);
			
		} else {	// To fetch existing AHI Form
			log.debug("{}  AHI Form VO is being fecthed from DB",nyehmsLog);
			aHIDocVO = ahiDocumentService.getAHIFormVO(docId, bindingResult, userObject);
			stateNo = personService.getNyehmsStatenoForTheDocument(docId);
			
			nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfStateno(docId, stateNo, null, userObject);
		}
		aHIDocVO.setSTATENO(stateNo);
		
		
		map.put("aHIDocVO", aHIDocVO);
		map.put("ahiCiStiConditionList", ahiCiStiConditionList);
		map.put("ahiCIReasonPatientTestedList", ahiCIReasonPatientTestedList);
		map.put("ahiNegInd900TestList", ahiNegInd900TestList);
		map.put("subFacilities", subFacilities);		
		map.put("codedCountries", codedCountries);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);

		return "document/AHIDocument";
	}
	
	@RequestMapping(value="/saveAHIForm", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> saveAHIForm(
			@ModelAttribute("aHIDocVO") AHIDocVO aHIDocVO, HttpServletRequest request, BindingResult result
			, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		if(aHIDocVO.getDOCUMENT_ID() == null){
			ahiDocumentService.saveAHIForm(aHIDocVO, bindingResult, userObject);
		}else{
			ahiDocumentService.storeEditedAHIStdDocument(aHIDocVO, bindingResult, userObject);
		}
		
		
		return targetMap;
	}
	
	
	
	@RequestMapping(value="/changeAhiFlagStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Object, Object> changeAhiFlagStatus(HttpServletRequest request, @RequestParam boolean ahiFlagStatus, @RequestParam int assignmentId, @RequestParam String comments, @RequestParam String documentType,
			@RequestParam String ahiDocIdForAssignment, @RequestParam int ahiAlertSeqId, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		String stateNo = request.getParameter("stateNo");
		
		documentReviewTransactionHelper.changeAhiFlagStatusForAssignment(stateNo, assignmentId, ahiFlagStatus,  comments, documentType,  
								ahiDocIdForAssignment, ahiAlertSeqId, userObject, bindingResult);
		
		return targetMap;
	
	}
	
	@RequestMapping(value="/validateAHIDataWithACRF.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Object, Object> validateAHIDataWithACRF(HttpServletRequest request, @RequestParam int acrfDocId, @RequestParam int ahiDocId, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		String documentType = request.getParameter("documentType");
		
		int resultCount = ahiDocumentService.validateAHIDataWithACRF(acrfDocId, ahiDocId, documentType, bindingResult, userObject);
		
		if(resultCount == 0){
			targetMap.put("DATA_SAME", false);
		}else{
			targetMap.put("DATA_SAME", true);
		}
		
		return targetMap;
	
	}

	@RequestMapping("/getAHIFlaggedStatenoDetails")
	public String getAHIFlaggedStatenoDetails(Map<String, Object> map) {
		
		map.put("ahiAlertTrackingVO", new AhiAlertTrackingVO());
		map.put("alertSrcMap", ahiDocumentService.getAhiAlertSrc());
		map.put("yesNoMap", ahiDocumentService.getYesNo());
		map.put("activePassiveMap", ahiDocumentService.getActivePassive());
		map.put("psMsgTypeMap", ahiDocumentService.getPSMessageType());
		
		List<Map<String, String>> ahiPrfFollowupList = ahiDocumentService.getAhiPrfFollowup();
		
		map.put("ahiPrfFollowupMap", ahiPrfFollowupList);
		
		return "reports/AHIFlaggedStatenoDetails";
	}

	@RequestMapping(value="/loadAHIAlertData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject getAHIAlertData(HttpServletRequest request, @RequestParam String dataType, @RequestParam String dataSubType, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(ahiDocumentService.getAhiAlertData(userObject, bindingResult, dataType, dataSubType));
		
		return jsonResponse;

	}	
	@RequestMapping(value="/saveAHIAlertTrackingForm", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> saveAHIAlertTrackingForm(
			@ModelAttribute("AhiAlertTrackingVO") AhiAlertTrackingVO ahiAlertTrackingVO, HttpServletRequest request, BindingResult result, UserObject userObject) {
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		ahiDocumentService.saveAHIAlertTrackingForm(ahiAlertTrackingVO, bindingResult, userObject);

		return targetMap;
	}	
	
	@RequestMapping("/getAHIAlertDetails")
	public String getAHIAlertDetails(@RequestParam("ahiAlertSeqId") int ahiAlertSeqId, Map<String, Object> map, HttpServletRequest request) {
		
		AhiAlertTrackingVO ahiAlertTrackingVO = ahiDocumentService.getAhiAlertDetails(ahiAlertSeqId);
		
		
		map.put("alertSrcMap", ahiDocumentService.getAhiAlertSrc());
		map.put("yesNoMap", ahiDocumentService.getYesNo());
		map.put("activePassiveMap", ahiDocumentService.getActivePassive());
		map.put("psMsgTypeMap", ahiDocumentService.getPSMessageType());
		
		List<Map<String, String>> ahiPrfFollowupList = ahiDocumentService.getAhiPrfFollowup();
		map.put("ahiPrfFollowupMap", ahiPrfFollowupList);
		
		map.put("ahiAlertTrackingVO", ahiAlertTrackingVO);
		
		return "reports/AHIAlertTrackingForm";
	}
	
	@RequestMapping(value="/updateAHIAlertStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateAHIAlertStatus(@ModelAttribute("AhiAlertTrackingVO") AhiAlertTrackingVO ahiAlertTrackingVO, HttpServletRequest request, BindingResult result, UserObject userObject) {
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		ahiDocumentService.updateAhiAlertStatus(ahiAlertTrackingVO, userObject, bindingResult);

		return targetMap;
	}	
	
}
