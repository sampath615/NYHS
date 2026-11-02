package gov.ny.state.doh.bhae.nyehms.web.spring.controller.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterPartnerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.ClusterPartnerReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
/*
 * Spring controller class for system message
 */
@RequiredArgsConstructor
@Controller
public class ClusterPartnerReviewController {
	
	private final ClusterPartnerReviewService clusterPartnerReviewService;
	
	private final CommonDocumentService commonDocumentService;
	
	private final DocumentTransactionHelper documentTransactionHelper;
	
	
	
	@RequestMapping("/getClusterPartnerReview")
	public String getClusterPartnerReview(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		//session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		map.put("DOCUMENT_TYPE", "CLUST_PART");
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<String> clusterPartnerIdList = clusterPartnerReviewService.getClusterPartnerIdList("ALL", bindingResult, userObject);
		
		map.put("clusterPartnerIdList", clusterPartnerIdList);
		
		return "person/ClusterPartnerclericalReview";
	}
	
	@RequestMapping(value="/getClusterPartnerIdList.json",  produces = "application/json")
	public @ResponseBody List<String> getClusterPartnerIdList(HttpServletRequest request, @RequestParam String statusCode, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		List<String> clusterPartnerIdList = clusterPartnerReviewService.getClusterPartnerIdList(statusCode, bindingResult, userObject);
		
		return clusterPartnerIdList;
	}
	
	@RequestMapping(value = "/getClusterPartnerReviewDoc.html")
	public String getClusterPartnerReviewDoc(@RequestParam int docId,
			Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		boolean isStdDocument = false;
		
		ClusterPartnerDocVO clusterPartnerDocVO = clusterPartnerReviewService.getClusterPartnerDocData(docId);
		
		if (clusterPartnerDocVO != null  && CommonUtil.isNotNullAndEmpty(clusterPartnerDocVO.getSTATENO())) {
			Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
					userObject);
			map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
			
			List<Map<String, String>>  frNumbers = clusterPartnerReviewService.getFRNumbers(clusterPartnerDocVO.getDOCUMENT_ID());
			map.put("FR_NUMBERS", frNumbers);
			
			isStdDocument = true;
		}
		
		List<Map<String, Object>> clusterPartnerRefData = clusterPartnerReviewService.getClusterPartnerRefData();
		
		map.put("documentRefData", clusterPartnerRefData);
		
		List<JSONObject> dispositionCodeValues = commonDocumentService.getDispositionCodeValues(false);
		map.put("dispositionCodeValues", dispositionCodeValues);
		
		String clusterId = request.getParameter("clusterId");
		
		Map<String, String> psMessageReasonDetails = clusterPartnerReviewService.getPsMessageDetails(docId, clusterId, userObject);
		
		map.put("psMessageReasonDetails", psMessageReasonDetails);
		map.put("isStdDocument", isStdDocument);
		map.put("clusterPartnerDocVO", clusterPartnerDocVO);
		return "document/ClusterPartnerDocument";
	}
	

	
	@RequestMapping(value="/resolveClusterFiles")	
	public String resolveClusterFiles(HttpServletRequest request, Map<String, Object> map, UserObject userObject) {
		
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<String> clusterPartnerIdList = clusterPartnerReviewService.getClusterPartnerIdList("ALL", bindingResult, userObject);
		
		map.put("clusterPartnerIdList", clusterPartnerIdList);
		
		return "person/resolveClusterFile";
	}
	
	
	@RequestMapping(value="/updateClusterFile", method = RequestMethod.POST)
	public String updateClusterFile(HttpServletRequest request, Map<String, Object> map, @RequestParam String clusterPartnerId, @RequestParam String clusterFileResolveDate, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clusterPartnerReviewService.updateClusterFile(clusterPartnerId, clusterFileResolveDate, bindingResult, userObject);
		
		List<String> clusterPartnerIdList = clusterPartnerReviewService.getClusterPartnerIdList("ALL", bindingResult, userObject);
		
		map.put("clusterPartnerIdList", clusterPartnerIdList);
		
		map.put("TRANSACTION_RESULT", targetMap.get("TRANSACTION_RESULT"));
		
		map.put("TRANSACTION_MESSAGE", targetMap.get("TRANSACTION_MESSAGE"));

		return "person/resolveClusterFile";

	}
	
	

	@RequestMapping(value="/getClusterPartnerDocumentIdReviewDetails.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> getClusterPartnerDocumentIdReviewDetails(HttpServletRequest request, //@RequestParam String qualityStageStatus,
			@RequestParam String clusterPartnerId, UserObject userObject){
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, Object> documentAndItsPotentialMatches = clusterPartnerReviewService.getClusterPartnerDocumentIdReviewDetails(
				"ALL", clusterPartnerId, bindingResult, userObject);
		
		return documentAndItsPotentialMatches;
	}
	
	@RequestMapping(value="/discardClericalReviewDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> discardClericalReviewDocument(HttpServletRequest request, @RequestParam long documentId, UserObject userObject) {


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clusterPartnerReviewService.discardClericalReviewDocument(documentId, bindingResult, userObject);

		return targetMap;

	}
	
	@RequestMapping(value="/processClusterPartnerDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> processClusterPartnerDocument(HttpServletRequest request, @RequestParam long documentId, @RequestParam(required = false) String stateno,
			String datafluxMatchCd, @RequestParam String INS_CLUSTER, UserObject userObject) {
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clusterPartnerReviewService.processClusterPartnerDocument(documentId, stateno, datafluxMatchCd, INS_CLUSTER, bindingResult, userObject);
		

		return targetMap;
		 
		 
	}
	
	@RequestMapping(value="/triggerClusterPSMessage.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> triggerClusterPSMessage(HttpServletRequest request, @RequestParam long documentId, @RequestParam String reason, @RequestParam(required = false) String otherReason, 
			@ModelAttribute("addressFieldVO") AddressFieldVO addressFieldVO, UserObject userObject) {
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clusterPartnerReviewService.triggerClusterPSMessage(documentId, reason, otherReason, addressFieldVO, bindingResult, userObject);
		

		return targetMap;
		 
		 
	}
	
	@RequestMapping(value="/rejectClusterPSMessage.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> rejectClusterPSMessage(HttpServletRequest request, @RequestParam long documentId, @RequestParam String rejectReason, @RequestParam(required = false) String OtherRejectReason, UserObject userObject) {
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clusterPartnerReviewService.rejectClusterPSMessage(documentId, rejectReason, OtherRejectReason, bindingResult, userObject);
		

		return targetMap;
		 
		 
	}
	
	@RequestMapping("/clusterPartnerPSReview")
	public String clusterPartnerPSReview(Map<String, Object> map, HttpServletRequest request) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "review/clusterPartner/clusterPartnerPSReview";
	}
	
	@RequestMapping(value="/psMessageReasons.html",
			method = RequestMethod.POST,
			produces = "text/html")
	public String psMessageReasons(
			Map<String, Object> map, HttpServletRequest request, @RequestParam String reasonType)
	{
		Map<String, String> inputParameterMap = new HashMap<>();
		inputParameterMap.put("REASON_TYPE", reasonType);
		
		List<Map<String, String>> psMessageReasonList = clusterPartnerReviewService.getPSMessageReasons(inputParameterMap);
		
		map.put("PS_REASON_LIST", psMessageReasonList);
		
		return "review/clusterPartner/psMessageReason";

	}
	
	
	
	
	@RequestMapping(value="/getClusterNoPSRecords.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<String, Object>> getClusterNoPSRecords(HttpServletRequest request, UserObject userObject) {
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return clusterPartnerReviewService.getClusterNoPSRecords(bindingResult, userObject);
		 
	}
	
	
}
