package gov.ny.state.doh.bhae.nyehms.web.spring.controller.review;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NBSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.NBSReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
/*
 * Spring controller class for system message
 */
@Controller
public class NBSReviewController {
	/*
	 * Reference to GetGridObject
	 */
	@Autowired
	GetGridObject getGridObject;
	
	@Autowired
	NBSReviewService nbsReviewService;
	
	@RequestMapping(value="/checkForClimsRecId.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Boolean> checkForClimsRecId(@RequestParam String stateno, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return nbsReviewService.checkForClimsRecId(stateno, bindingResult, userObject);
	}
	
	@RequestMapping(value="/getNbsMnemonicRecords.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getNbsMnemonicRecords(@RequestParam String BIRTH_YEAR, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return nbsReviewService.getNbsMnemonicRecords(BIRTH_YEAR, bindingResult, userObject);
	}
	
	
	@RequestMapping(value="/getNonNbsMatchClimsRecords.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getNonNbsMatchClimsRecords(HttpServletRequest request, @RequestParam String BIRTH_YEAR)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return nbsReviewService.getNonNbsMatchClimsRecords(BIRTH_YEAR, bindingResult, userObject);
	}
		
	@RequestMapping(value="/getNbsMatchReviewRecords.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getNbsMatchReviewRecords(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return nbsReviewService.getNbsMatchReviewRecords(bindingResult, userObject);
	}
	
	@RequestMapping(value="/getNbsIndRecords.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getNbsIndRecords(@RequestParam String BIRTH_YEAR, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return nbsReviewService.getNbsIndRecords(BIRTH_YEAR, bindingResult, userObject);
	}
	
	
	@RequestMapping(value = "/getNBSDoc.html")
	public String getNBSDocAsHTML(@RequestParam int docId, @RequestParam(required = false) String reviewType, Map<String, Object> map, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		NBSDocVO nbsDocVO = nbsReviewService.getNBSDoc(docId, reviewType, bindingResult, userObject);
//		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
		map.put("stdNBSDocVO", nbsDocVO);
		return "document/NBSDocument";
	}
	
	
	@RequestMapping(value="/matchNBSDocumentToStateno.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> matchNBSDocumentToStateno(HttpServletRequest request, @RequestParam long documentId, @RequestParam String stateno, @RequestParam String reviewComments) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		nbsReviewService.matchNBSDocumentToStateno(documentId, stateno, reviewComments, bindingResult, userObject);
		
		return targetMap;

	}
	
	@RequestMapping(value="/matchClimsDocumentToNBS.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> matchClimsDocumentToNBS(HttpServletRequest request, @RequestParam(required = false) String patientId, @RequestParam long nbsDocumentId, @RequestParam String reviewComments) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		nbsReviewService.matchClimsDocumentToNBS(patientId, nbsDocumentId, reviewComments, bindingResult, userObject);
		
		return targetMap;

	}
	
	@RequestMapping(value="/updateNBSExposureStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateNBSExposureStatus(HttpServletRequest request, @RequestParam long documentId, @RequestParam String exposureStatus, @RequestParam String comments) {

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		nbsReviewService.updateNBSExposureStatus(documentId, exposureStatus, comments, bindingResult, userObject);
		
		return targetMap;

	}
	
	@RequestMapping("/getNBSReview")
	public String getNBSReview(Map<String, Object> map, HttpServletRequest request) {
		
		map.put("DOCUMENT_TYPE", "NBS");
		
		map.put("REVIEW_TYPE", "NBS_HIV");
		
		JSONObject hivFinalStatusList = nbsReviewService.getHivFinalStatusList();
		
		map.put("hivFinalStatusList", hivFinalStatusList);
		
		List<String> birthYearList = nbsReviewService.getPendingNbsBirthYearList("INFANT_MATCH_YR");
		
		map.put("BIRTH_YEARS", birthYearList);
		
		map.put("TAB_NAME", "Infant Match Review");
		
		return "review/nbs/nbsToNyehmsReview";
		
	}
	
	
	@RequestMapping("/getINDGIReview")
	public String getINDGIReview(Map<String, Object> map, HttpServletRequest request) {
		
		map.put("DOCUMENT_TYPE", "NBS");
		
		map.put("REVIEW_TYPE", "NBS_IND");
		
		map.put("hivFinalStatusList", new JSONObject());
		
		map.put("TAB_NAME", "Perinatal Exposure Review");
		
		List<String> birthYearList = nbsReviewService.getPendingNbsBirthYearList("PERI_EXPO_YR");
		
		map.put("BIRTH_YEARS", birthYearList);
		
		return "review/nbs/nbsToNyehmsReview";
		
	}
	
	
	@RequestMapping("/getNBSDataFluxClericalReview")
	public String getResistanceDataFluxClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "NBS");
		
		map.put("TAB_NAME", "Mother Match Review");
		
		
		
		return "review/nbs/nbsClericalReviewDatafluxStatus";
		
	}
	
	@RequestMapping("/getNBSClimsMatchReview")
	public String getNBSClimsMatchReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "CLIMS");
		
		List<String> birthYearList = nbsReviewService.getClimsNonMatchBirthYearList();
		
		map.put("BIRTH_YEARS", birthYearList);
		
		return "review/nbs/climsClericalReviewDatafluxStatus";
		
	}
	
	
	
	@RequestMapping(value="/getNBSSearchViewResults.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<String, Object>> getNBSSearchViewResults(
			HttpServletRequest request,
			@RequestParam(required = false) String labId, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String dateOfBirth, 
			@RequestParam(required = false) String labNo, @RequestParam(required = false) String medRecordNumber
			, @RequestParam(required = false) String pfi, @RequestParam(required = false) String gender, @RequestParam(required = false) String weight
			)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return nbsReviewService.getNBSSearchViewResults(labId, firstName, lastName, labNo, medRecordNumber, dateOfBirth, pfi, gender, weight, bindingResult, userObject);		
	}
	
	@RequestMapping("/getNBSSearchReview")
	public String getNBSSearchReview() {
		return "review/nbs/nbsSearchView";
	}
	
	
	
	@RequestMapping(value="/getNBSDocumentDetailsForMatchReview.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> getNBSDocumentDetailsForMatchReview(HttpServletRequest request, @RequestParam Integer documentId){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, Object> documentAndItsPotentialMatches = nbsReviewService.getNBSDocumentDetailsForMatchReview(documentId, bindingResult, userObject);
		
		return documentAndItsPotentialMatches;
	}
	
	@RequestMapping(value="/getClimsDocumentDetailsForMatchReview.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> getClimsDocumentDetailsForMatchReview(HttpServletRequest request, @RequestParam Integer documentId){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, Object> documentAndItsPotentialMatches = nbsReviewService.getClimsDocumentDetailsForMatchReview(documentId, bindingResult, userObject);
		
		return documentAndItsPotentialMatches;
	}
	
	
	
	@RequestMapping(value="/updateHivFinalStatus.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> updateHivFinalStatus(HttpServletRequest request, @RequestParam Integer id, @RequestParam String HIV_FINAL_STATUS, @RequestParam String REVIEW_COMMENTS){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<String, Object> targetMap= new HashMap<String, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		nbsReviewService.updateHivFinalStatus(id, HIV_FINAL_STATUS, REVIEW_COMMENTS, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/updateClimsReviewComments.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> updateClimsReviewComments(HttpServletRequest request, @RequestParam Integer id, @RequestParam String REVIEW_COMMENTS){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<String, Object> targetMap= new HashMap<String, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		nbsReviewService.updateClimsReviewComments(id, REVIEW_COMMENTS, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/updateStdNbsReviewComments.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> updateStdNbsReviewComments(HttpServletRequest request, @RequestParam Integer id, @RequestParam String REVIEW_COMMENTS){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<String, Object> targetMap= new HashMap<String, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		nbsReviewService.updateStdNbsReviewComments(id, REVIEW_COMMENTS, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	
	
	
	@RequestMapping(value="/updateRawNBSData.json",  produces = "application/json")
	public @ResponseBody Map<String, Object> updateRawNBSData(HttpServletRequest request, @RequestParam Integer id, @RequestParam String REVIEW_COMMENTS){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<String, Object> targetMap= new HashMap<String, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		nbsReviewService.updateRawNBSData(id, REVIEW_COMMENTS, bindingResult, userObject);
		
		return targetMap;
	}
	
}

