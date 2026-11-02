package gov.ny.state.doh.bhae.nyehms.web.spring.controller.person;

/*
 *Author		: Srikar Kummamuri
 *Date Created 	: Feb-02-2012
 *Description 	: This will handle all requests and responses related to Person View
 */

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentEditService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RequiredArgsConstructor
@Controller
public class PersonController {
	
	private final PersionService personService;
	private final AssignmentService assignmentService;
	private final ACRFDocumentService aCRFDocumentService;
	private final CommonDocumentEditService commonDocumentEditService;
	private final GetGridObject getGridObject;
	private final DateUtil dateUtil;
	
	private static final Logger log = LoggerFactory.getLogger(PersonController.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();

	/**
	 * Annotation that identifies methods which initialize the WebDataBinder
	 * which will be used for populating command and form object arguments of
	 * annotated handler methods. 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * This method will redirect the page to Person search page
	 * @return personSearchTabDetail.jsp
	 */
	@RequestMapping(value="/getPersonSearchView.html")	
	public String getPersonSearchView(Map<String, Object> map)
	{
		return "search/personSearchTabDetail";
	}
	/**
	 * This method is used to get the Person details, Risk details and Documents
	 * associated with the Person. All these three details will be stored in
	 * pMap object.  Then this method will redirect to PersonView.jsp to display Person view and
	 * Document time line from the "pMap" object
	 * @param pMap which is used to store the Person view, Risk and Document timeline details 
	 * @param stateNo 
	 * @param endDate The latest date for which a document will be returned; only
	 * documents with an association date prior to or equal to this date will
	 * be returned.
	 * @param request Request object
	 * @return redirecting the page to PersonView.jsp
	 */
	@RequestMapping(value="/personViewDiv.html", method = RequestMethod.POST)	
	public String returnPersonViewDiv(
			Map<String, Object> pMap,
			@RequestParam String stateNo,
			@RequestParam String endDate,
			HttpServletRequest request, UserObject userObject)
	{
		HttpSession session =request.getSession();
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		log.debug(nyehmsLog+" stateNo is {} :: End Date is ::: {}", stateNo,endDate);
		PersonViewVO personViewVO= personService.getPersonViewData(stateNo, endDate, bindingResult, userObject, session.getId());
		List<String> statenoHistoryObj = personService.getStatenoHistory(stateNo, bindingResult, userObject);
		personViewVO.setbIndicatedDate(endDate);		
		personViewVO.setStateNo(stateNo);
		PersonRaceVO personRaceVO = personService.getPersonRaceData(stateNo, endDate, bindingResult, userObject, session.getId());
		if(personRaceVO == null){
			personRaceVO = new PersonRaceVO();
			if(log.isDebugEnabled())
				log.debug("No Person race is available for the Stateno: {}", stateNo);
		}
		personViewVO.setPersonRaceVO(personRaceVO);
		String statenoClusterIds = personService.getClusterIds(stateNo, bindingResult, userObject);
		personViewVO.setClusterIds(statenoClusterIds);
		//storing the objects in Map scope to access these variables in the JSP
		pMap.put("personViewObj", personViewVO);
		pMap.put("statenoHistoryObj", statenoHistoryObj);
		return "person/PersonView";
	}
	/**
	 * 
	 * @param pMap
	 * @param stateNo
	 * @param startDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/personRiskContent.html", method = RequestMethod.POST)	
	public String personRiskContent(
			Map<String, Object> pMap,
			@RequestParam String stateNo,
			@RequestParam(required = false) String startDate,
			HttpServletRequest request, UserObject userObject)
	{
		HttpSession session =request.getSession();
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		log.debug(nyehmsLog+ " stateNo is {}  :: Date is ::: {}",stateNo,startDate);
		List<PersonRisksVO> cursorRisksOut = null;
		if(stateNo != null && !"".equals(stateNo)){
			cursorRisksOut = personService.getPersonRisksData(stateNo,  startDate,  bindingResult,  userObject,  session.getId());
		}
		PersonRisksVO personRisksVO = null;
		if(cursorRisksOut == null || cursorRisksOut.size() == 0 || cursorRisksOut.get(0) == null ){
			personRisksVO = new PersonRisksVO();
			if(log.isDebugEnabled())
				log.debug("No Person risk is available for the Stateno: {}",stateNo);
			return "person/NoPersonRisksView";
		}else{
			personRisksVO = (PersonRisksVO)cursorRisksOut.get(0);
		}
		//storing the objects in Map scope to access these variables in the JSP
		pMap.put("personRisksObj", personRisksVO);
		return "person/PersonRisksView";
	}


	/**
	 * 
	 * @param stateNo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPersonDocumentDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<PersonDocHistoryVO> getDocumentDetails(@RequestParam String stateNo, HttpServletRequest request , UserObject userObject)
	{
		if(userObject==null) userObject= new UserObject();
		BindingResult dummyBindingResult = BindingResults.newDummy();
		String documentId = request.getParameter("documentId");
		String documentType = request.getParameter("documentType");
		List<PersonDocHistoryVO> personDocHistoryList = null;
		if(stateNo != null && !"".equals(stateNo)){
			personDocHistoryList = personService.getPersonDocumentDetails(stateNo, documentType, dummyBindingResult, userObject, request.getSession().getId());
		}else if(documentId != null){
			personDocHistoryList = personService.getDocumentDetails(documentId, documentType, dummyBindingResult, userObject, request.getSession().getId());
		}
		return personDocHistoryList;
	}

	/**
	 * 
	 * @param stateNo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getHIVDocumentDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<PersonDocHistoryVO> getHIVDocumentDetails(@RequestParam String stateNo, HttpServletRequest request , UserObject userObject)
	{
		if(userObject==null) userObject= new UserObject();
		BindingResult dummyBindingResult = BindingResults.newDummy();
		List<PersonDocHistoryVO> personDocHistoryList = null;
		if(stateNo != null && !"".equals(stateNo)){
			personDocHistoryList = personService.getHIVDocumentDetails(
					stateNo, dummyBindingResult, userObject, request.getSession().getId());
		}
		return personDocHistoryList;
	}
	
	/**
	 * This method will return the list of values available for the specified
	 * field name in a JSON string. It will gather all the values from the list
	 * of documents associated with the stateno.
	 * 
	 * @param stateNo State number
	 * @param fieldName Field name
	 * @param callingAppName origin from person page or search page
	 * @param request Request object
	 * @return List of values available for the Field name
	 */
	@RequestMapping(value="/getFieldValsWithBestIndicator.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject returnFieldVals(
			@RequestParam String stateNo,
			@RequestParam String fieldName,
			@RequestParam String callingAppName,
			HttpServletRequest request , UserObject userObject)
	{
		if(userObject==null) userObject= new UserObject();
		BindingResult dummyBindingResult = BindingResults.newDummy();
		GridObject jsonResponse =  getGridObject.GetGrid(personService.getFieldValsWithBestIndicator(
				stateNo, fieldName,  callingAppName, dummyBindingResult, userObject, request.getSession().getId()));
		return jsonResponse;
	}

	/**
	 * This method will return the List of documents (where the field name value
	 *  equal to  "fieldVal" ) associated with the stateno
	 * 
	 * @param stateNo State number
	 * @param fieldName Field name
	 * @param fieldVal Field value
	 * @param callingAppName origin from person page or search page
	 * @param request Request object
	 * @return List of documents (Document Id, Type and Recieved date)
	 */
	@RequestMapping(value="/getFieldDocsWithBestIndicator.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject returnFieldDocs(
			@RequestParam String stateNo,
			@RequestParam String fieldName,
			@RequestParam String fieldVal,
			@RequestParam String callingAppName,
			HttpServletRequest request , UserObject userObject)
	{
		if(userObject==null) userObject= new UserObject();
		BindingResult dummyBindingResult = BindingResults.newDummy();
		GridObject jsonResponse =  getGridObject.GetGrid(personService.getFieldDocsWithBestIndicator(
				stateNo, fieldName,  fieldVal, callingAppName, dummyBindingResult, userObject, request.getSession().getId()));
		return jsonResponse;
	}

	/**
	 * This function is used to delete the document. This delete function will be available in the Doc viewer. 
	 * 
	 * This delete function will be enabled when we come from Person search.
	 * 
	 * To Delete the document user should have Permission for that specified document type.
	 * @param documentId Document id
	 * @param request Request object
	 * @return Transaction status(Success/Failure)
	 */
	@RequestMapping(value="/deleteDocument.json", method = RequestMethod.POST, produces = "application/json")
	public@ResponseBody Map<Object, Object> deleteDocument(
			@RequestParam int documentId,
			@RequestParam String documentType,
			@RequestParam Integer discardReason,
			@RequestParam String discardReasonDescription,
			@RequestParam String comments,
			@RequestParam String stateno,
			@RequestParam(required = false) String deleteFlag,
			HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		personService.deleteDocument(documentId, documentType, discardReason, discardReasonDescription, comments, stateno, deleteFlag, bindingResult, userObject);
		
		return targetMap;	
	}
	/**
	 * 
	 * @param stateNo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getEharsStateno", method = RequestMethod.POST)
	public @ResponseBody String getEharsStateno(
			@RequestParam String stateNo,
			HttpServletRequest request)
	{		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		List<String> eharsStatenos =  personService.getEharsStateno(stateNo, bindingResult, userObject);
		if(eharsStatenos != null && eharsStatenos.size() > 0){
			return eharsStatenos.get(0);
		}else{
			return null;
		}
	}

	@RequestMapping(value="/getEharsStatenos.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONObject getEharsStateno(
			@RequestParam("statenos[]")  String[] statenos,
			HttpServletRequest request)
	{		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return personService.getEharsStatenosMapping(statenos, bindingResult, userObject);
	}

	/**
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateStdDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateStdDocument(
			@RequestParam Map<String, String> params,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		personService.updateStdDocument(params, bindingResult, userObject);
		return targetMap;
	}

	/**
	 * This method is called to add new ACRF form.
	 *
	 * @param acrfFormVO
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/addACRFFormAndAssociateToStateno", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> addACRFFormAndAssociateToStateno(
			@ModelAttribute("acrfFormVO") AcrfFormVO acrfFormVO,
			@RequestParam String testType,
			HttpServletRequest request,
			Map<String, Object> map,
			@RequestParam String stateno, UserObject userObject)
	{
		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		String acrfReferenceRowId = request.getParameter("acrfReferenceRowId");
		aCRFDocumentService.addACRFFormAndAssociateToStateno(acrfReferenceRowId, acrfFormVO, testType, stateno, bindingResult, userObject);
		return targetMap;
	}

	/**
	 * 
	 * @param documentId
	 * @param stateno
	 * @param documentType
	 * @param messageType
	 * @param psMessageForce
	 * @param softErrorCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/triggerPsMessage.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> triggerPsMessage(
			@RequestParam int documentId,
			@RequestParam String stateno,
			@RequestParam String documentType,
			@RequestParam String messageType,
			@RequestParam(required = false) String psMessageForce, 
			@RequestParam(required = false) String softErrorCode,
			@RequestParam(required = false) String reason, 
			@RequestParam(required = false) String otherReason,
			@ModelAttribute("addressFieldVO") AddressFieldVO addressFieldVO,
			HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		personService.triggerPsMessage(documentId, stateno, documentType, messageType, psMessageForce, softErrorCode, addressFieldVO, reason, otherReason, bindingResult, userObject);
		return targetMap;
	}

	/**
	 * 
	 * @param documentId
	 * @param stateno
	 * @param documentType
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPsMessageType.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> getPsMessageType(
			@RequestParam int documentId,
			@RequestParam String stateno,
			@RequestParam String documentType,
			HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		personService.getPsMessageType(documentId, stateno, documentType, bindingResult, userObject);
		return targetMap;
	}

	/**
	 * 
	 * @param allRequestParams
	 * @param DOCUMENT_ID
	 * @param DOCUMENT_TYPE_CODE
	 * @param SOFT_ERROR_ONLY
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/editStdDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> editStdDocument(
			@RequestParam Map<String, String> allRequestParams,
			@RequestParam int DOCUMENT_ID,
			@RequestParam String DOCUMENT_TYPE_CODE,
			@RequestParam String SOFT_ERROR_ONLY,
			HttpServletRequest request, UserObject userObject)
	{		
		//, @RequestParam int DOCUMENT_ID, @RequestParam String DOCUMENT_TYPE_CODE
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		commonDocumentEditService.storeEditRawAndStdDocument(allRequestParams, DOCUMENT_ID, DOCUMENT_TYPE_CODE, SOFT_ERROR_ONLY, bindingResult, userObject);
		return targetMap;
	}
	
	@RequestMapping(value="/getNotAssociatedClusterIds.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<String> getNotAssociatedClusterIds(HttpServletRequest request, String stateno, UserObject userObject) {
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<String> clusterIdDetails = personService.getNotAssociatedClusterIds(userObject, bindingResult, stateno);
		
		// Show this data in the dropdown 
		return clusterIdDetails;
	}	
	
	
	@RequestMapping("/addStatenoToCluster")
	public @ResponseBody Map<Object, Object> addStatenoToCluster(HttpServletRequest request, String stateno, String clusterDetails, String identificationMethod, String identificationDate, long demoDocumentId, UserObject userObject) {
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		personService.associateStateNoToCluster(userObject, bindingResult, stateno, clusterDetails, identificationMethod, identificationDate, demoDocumentId);

		return targetMap;
	}	
	
	/**
	 *  this method will fetch the information about eHARS Person Data and display it on the TAB.
	 * @param stateNo
	 * @param request
	 * @param nyehmsStateNo
	 * @return
	 */
	@RequestMapping("/eHarsPersonViewTab.html")
	public String geteHarsPersonViewData(@RequestParam("stateno") String stateno,@RequestParam("nyehmsStateNo")  String nyehmsStateNo, HttpServletRequest request, Map<String, Object> map, UserObject userObject) {
		
		if (userObject == null)
			userObject = new UserObject();

		BindingResult dummyBindingResult = BindingResults.newDummy();

	
		Map<String, String> eharPersonDataObj = assignmentService.loadEharsPersonViewDetails(stateno,
				dummyBindingResult, userObject, request.getSession().getId());
		
		
		Map<String, String> eharsDataMap = personService.geteHarsPersonDataforTab(eharPersonDataObj,dummyBindingResult,userObject,nyehmsStateNo);

		map.put("eharPersonDataObj", eharsDataMap);

		return "person/ehars/eHarsPersonView";
	}

	/**
	 * this method will fetch the information about the RIDR summary on the eHARS person view TAB.
	 * @param 'ridr values' in pipe format
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="/eHarsPersonDuplicateReviewSummary.html", method = RequestMethod.POST)	
	public String eHarsPersonDuplicateReviewSummary(Map<String, Object> pMap, @RequestParam String ridrValues, HttpServletRequest request)
	{
		List<Map<String,String>> stateNoSummary = 	personService.getDuplicateReviewSummary(ridrValues);

		pMap.put("duplicateReviewSummary", stateNoSummary);
		
		return "person/ehars/displayReviewSummary";
	}
	
}
