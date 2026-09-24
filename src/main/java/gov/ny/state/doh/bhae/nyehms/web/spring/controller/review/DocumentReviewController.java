package gov.ny.state.doh.bhae.nyehms.web.spring.controller.review;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsListVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFDocumentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.LabDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NonCaseDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.PRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.DocumentsReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

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
/*
 * Spring controller class for system message
 */
@Controller
public class DocumentReviewController {
	/*
	 * Reference to GetGridObject
	 */
	@Autowired
	GetGridObject getGridObject;
	
	@Autowired
	DocumentsReviewService documentsReviewService;
	
	@Autowired private NonCaseDocumentService nonCaseDocumentService;
	
	@Autowired
	CommonDocumentService commonDocumentService;
	

	@Autowired
	AssignmentService assignmentService;
	
	
	@Autowired
	PRFDocumentService pRFDocumentService;
	
	@Autowired
	LabDocumentService labDocumentService;
	

	
	@RequestMapping("/getPRFReview")
	public String getPRFReview(Map<String, Object> map, HttpServletRequest request) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		
		map.put("addressFieldVO", addressFieldVO);

		
		return "review/document/prfReview";
		
	}
	
	
	@RequestMapping("/getLabQualAcrfView")
	public String getLabQualAcrfView(
			Map<String, Object> pMap)
	{	
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		pMap.put("addressFieldVO", addressFieldVO);
		return "review/document/acrf/labQualAcrfView";
	}
	
	
	
	@RequestMapping("/getAssignmentsReview")
	public String getAssignmentsReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		List<Map<String,String>> closureCategoryList = assignmentService.getAssignmentClosureStatus(bindingResult, userObject, userObject.getSessionId(), false, "R");

		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		List<Map<String, String>> nonCaseReasons = nonCaseDocumentService.getNonCaseReasons();
		map.put("nonCaseReasons", nonCaseReasons);
		
		map.put("closureCategoryList", closureCategoryList);
		
		
		return "assignment/assignmentsReview";
		
	}
	
	
	
	
	/**
	 * Loading the Lab Document Ids which got failed in Validation process 
	 * @param request Request Object
	 * @return List of Document Ids
	 */
	@RequestMapping(value="/getprfReviewDocumentsData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getprfReviewDocumentsData(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return documentsReviewService.getprfReviewDocumentsData(bindingResult, userObject);

	}
	

	@RequestMapping(value="/getStatenoAndSourceCodeFromStdDocData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> getStatenoAndSourceCodeFromStdDocData(@RequestParam String documentType, @RequestParam int documentId, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		return commonDocumentService.getStatenoAndSourceCodeFromStdDocData(documentType, documentId, userObject);

	}
	
	
	@RequestMapping(value = "/reviewAndUpdateAndFlagNonAsCaseTheStatusOfClosedAssignment.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> reviewAndUpdateAndFlagNonAsCaseTheStatusOfClosedAssignment(
			@RequestParam int assignmentId, @RequestParam String status, @RequestParam String colsureCategory,
			@RequestParam String stateNo, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");

		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		documentsReviewService.reviewAndUpdateAndFlagNonAsCaseTheStatusOfClosedAssignment(colsureCategory, stateNo,
				assignmentId, status, bindingResult, userObject);

		return targetMap;

	}
	
	
	
	@RequestMapping(value="/agreeAndDeleteDocumentForClosedAssignment.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> agreeAndDeleteDocumentForClosedAssignment(@RequestParam int assignmentId,@RequestParam int documentId, @RequestParam String documentType, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		documentsReviewService.agreeAndDeleteDocumentForClosedAssignment(assignmentId, documentId, documentType, bindingResult, userObject);
		
		return targetMap;

	}
	
	
	
	
	
	/**
	 * Loading the Lab Document Ids which got failed in Validation process 
	 * @param request Request Object
	 * @return List of Document Ids
	 */
	@RequestMapping(value="/loadClosedAssignmentsForReview.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<AssignmentsListVO> loadClosedAssignmentsForReview(HttpServletRequest request, @RequestParam String closureCategory){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<AssignmentsListVO> asmtCursorOut = assignmentService.loadClosedAssignmentsForReview(closureCategory,  bindingResult,
						userObject, request.getSession().getId());
		
		return asmtCursorOut;

	}
	
	
	
	
	@RequestMapping(value="/updateRawPrfDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateRawPrfDocument(HttpServletRequest request, @ModelAttribute("pRFDocumentVO") PRFDocumentVO pRFDocumentVO, @RequestParam boolean isValidationFailed ){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		pRFDocumentService.updateRawPrfDocument(pRFDocumentVO, isValidationFailed, bindingResult, userObject);
				
		return targetMap;

	}
	
	
	@RequestMapping(value="/valiatePrfDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> valiatePrfDocument(HttpServletRequest request, @ModelAttribute("pRFDocumentVO") PRFDocumentVO pRFDocumentVO ){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		pRFDocumentService.isValidPrfDoc(pRFDocumentVO, bindingResult);;
				
		return targetMap;

	}
	
	@RequestMapping(value="/deleteRawPrfDoc.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> deleteRawPrfDoc(HttpServletRequest request, @RequestParam int  docId){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		pRFDocumentService.deleteRawPrfDoc(docId, bindingResult, userObject);
				
		return targetMap;

	}
	
	@RequestMapping(value="/processRawPrfDoc.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> processRawPrfDoc(HttpServletRequest request, @RequestParam int  docId){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		pRFDocumentService.processRawPrfDoc(docId, bindingResult, userObject);
				
		return targetMap;

	}
	
	@RequestMapping(value="/moveRawToStdByStateno.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> moveRawToStdByStateno(@RequestParam int documentId, String stateno, @RequestParam(required = false) String requestComments, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		labDocumentService.moveRawToStdByStateno(documentId, stateno, requestComments, bindingResult, userObject);
				
		return targetMap;

	}
		
	
	
	/**
	 * This method is used to update the Document status. Will use this method in Lab Errors and Lab HED details pages.
	 * 
	 * In Lab Errors page we can do Delete, Edit and Reprise the document(s)
	 * In HED Details page we can do Delete, Edit, Hold and Unhold the document(s)
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteMultiSpotDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> deleteMultiSpotDocuments(HttpServletRequest request, @RequestParam("documentIds[]")  Long[] documentIds, @RequestParam String documentType){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		commonDocumentService.updateDocumentsStatus(documentIds, documentType, CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS,bindingResult, userObject);
		
		return targetMap;
	}
	
	
	
	@RequestMapping(value="/vlReview")	
	public String vlReview(HttpServletRequest request, Map<String, Object> map) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		
		
		List<Map<String, String>> reviewCategories = documentsReviewService.getVlDocumentReviewCategories();
		
		map.put("reviewCategories", reviewCategories);
		
		
		return "review/viralLoadReview";
	}
	
	@RequestMapping(value="/getVlStatenosToReview.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	GridObject getVlStatenosToReview(HttpServletRequest request, @RequestParam(required = false) String reviewCategory) {
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> dummyMindingMap= new HashMap<Object, Object>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		 
		GridObject jsonResponse = getGridObject.GetGrid( documentsReviewService.getVlStatenosToReview(reviewCategory, dummyBindingResult, userObject));
		
		return jsonResponse;
		
	
	}
	
	@RequestMapping(value="/updateVlDocumentStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateVlDocumentStatus(HttpServletRequest request, @RequestParam  String stateno, @RequestParam String reviewCategory,
			@RequestParam String requestComments){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		documentsReviewService.updateVlDocumentStatus(stateno, reviewCategory, requestComments, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	
}
