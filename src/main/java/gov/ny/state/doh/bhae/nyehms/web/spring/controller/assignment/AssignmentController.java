package gov.ny.state.doh.bhae.nyehms.web.spring.controller.assignment;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * This is the controller for the Assignment
 *
 * @author ska03
 */
import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentActivityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentUpdateVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsAssignReassignVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsHomeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsListVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.ReabAssignmentsAssignVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ADAPDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFDocumentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.AdministrationService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SearchManageProfileService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentReportsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ADAPDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.LabDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.MedicaidDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NonCaseDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.PRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftFollowupDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@Controller
public class AssignmentController {

	private static Logger log = LoggerFactory.getLogger(AssignmentController.class);
	
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired private NonCaseDocumentService nonCaseDocumentService;
	@Autowired
	private ACRFDocumentService acrfFDocumentService;
	
	
	@Autowired
	private AssignmentReportsService assignmentReportsService;
	
	@Autowired
	private AdministrationService administrationService;
	
	@Autowired
	private LabDocumentService labDocumentService;
	
	@Autowired
	private ADAPDocumentService aDAPDocumentService;
	
	@Autowired
	private PRFDocumentService pRFDocumentService;
	
	@Autowired
	private MedicaidDocumentService medicaidDocumentService;

	@Autowired
	CommonDocumentService commonDocumentService;
	@Autowired
	private DocumentTransactionHelper documentTransactionHelper;
	@Autowired
	private AssignmentTransactionHelper assignmentTransactionHelper;	
	
	@Autowired 
	private LaftFollowupDocumentService laftFollowupDocumentService;
	
	@Autowired
	private SearchManageProfileService searchManageProfileService;

	@Autowired
	CommonService commonService;
	@Autowired
	GetGridObject getGridObject;
	@Autowired
	DateUtil dateUtil;

	private static final Map<Integer, String> monthMap = new HashMap<Integer, String>();
    
    
    
    static{
    	monthMap.put(0, "JAN");
    	monthMap.put(1, "FEB");
    	monthMap.put(2, "MAR");
    	monthMap.put(3, "APR");
    	monthMap.put(4, "MAY");
    	monthMap.put(5, "JUN");
    	monthMap.put(6, "JUL");
    	monthMap.put(7, "AUG");
    	monthMap.put(8, "SEP");
    	monthMap.put(9, "OCT");
    	monthMap.put(10, "NOV");
    	monthMap.put(11, "DEC");
    	
    }
    	
	/**
	 * This method is used to load the NYEHMS home page. This method load basic
	 * information required to load the Grpahs on home page
	 * 
	 * @param request
	 *            Request object
	 * @param map
	 *            which stores the Assignment home page required data
	 * @return redirecting to Assignment home
	 */
	@RequestMapping("/getAssignmentsTally")
	public String getAssignmentsAsHTML(HttpServletRequest request,
			Map<String, Object> map) {

		
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		// Get the Worker Regions List for logged in worker id.
//		List<AssignmentWorkerRegionsVO> assignmentWorkerRegionsVOList = assignmentService
//				.getAssignmentWorkerRegions(userObject.getWorkerId() + "",
//						dummyBindingResult, userObject, request.getSession()
//								.getId());

		List<String> graphsHierarchy = searchManageProfileService
				.getGraphsHierarchy(dummyBindingResult, userObject);

		map.put("graphsHierarchy", graphsHierarchy);
//			regionMap.put(assignmentWorkerRegionsVO.getWorkerRegionId(),


//		session.setAttribute("assignmentWorkerRegionsVOList",
//				assignmentWorkerRegionsVOList);
		
		
		return "assignment/AssignmentsHome";
	}
	
	
	@RequestMapping(value="/getAssignmentReports")	
	public String getAssignmentReports(HttpServletRequest request, Map<String, Object> map) {
		
		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");
		
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		
		Map<String, String> regionsList = administrationService.getSrchregionTypes(dummyBindingResult, userObject);
		
		
		map.put("regionsList", regionsList);
		
		return "assignment/AssignmentsReports";
	}
	
	
	@RequestMapping(value="/getAssignmentReportsByDocument")	
	public String getAssignmentReportsByDocument(HttpServletRequest request, Map<String, Object> map) {
		
		return "assignment/reports/AssignmentReportsByDocument";
	}
	
	@RequestMapping(value="/getAssignmentAHIReports")	
	public String getAssignmentAHIReports(HttpServletRequest request, Map<String, Object> map) {
		
		
		return "assignment/reports/AssignmentReportsByAHI";
		
	}
	
	@RequestMapping(value="/getAssignmentAHIReport", method = RequestMethod.POST)	
	public String getAssignmentAHIReport(HttpServletRequest request, Map<String, Object> map, @RequestParam String ahiFlag) {
		
		
		List<Map<String, String>> ahiReportMap = assignmentReportsService.getAssignmentAHIReports(ahiFlag);
		
		map.put("ahiReportMap", ahiReportMap);
		
		return "assignment/reports/ahiAssignmentReportTable";
		
	}
	
	
	
	
	
	@RequestMapping(value="/unknownOutOfStateProviders")	
	public String unknownOutOfStateProviders(HttpServletRequest request, Map<String, Object> map) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		
		List<String> documentTypes = commonDocumentService.getDocumentTypes(userObject);
		
		map.put("documentTypes", documentTypes);
		
		map.put("actionName", "unknowOOSProviders");
		
		map.put("title", "Unknown / OOS Providers");
		
		return "assignment/unknownOutOfStateProviders";
	}
	
	

	@RequestMapping(value="/oosProviderFollowUp")	
	public String oosProviderFollowUp(HttpServletRequest request, Map<String, Object> map) {
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		List<String> documentTypes = commonDocumentService.getDocumentTypes(userObject);
		
		map.put("documentTypes", documentTypes);
		
		map.put("actionName", "oosProviderFollowUp");
		
		map.put("title", "OOS Provider Follow-Up");
		
		return "assignment/unknownOutOfStateProviders";
	}
	
	
	@RequestMapping(value="/getunknownOutOfStateProviderDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<PersonDocHistoryVO> getunknownOutOfStateProviderDocuments(HttpServletRequest request, @RequestParam String documentType, @RequestParam String  processCode) {
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> dummyMindingMap= new HashMap<Object, Object>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		List<PersonDocHistoryVO> personDocHistoryList = null;
		 
		personDocHistoryList = assignmentService.getunknownOutOfStateProviderDocuments(documentType, processCode, dummyBindingResult, userObject);
		
			
		
		return personDocHistoryList;
	
	}
	
	@RequestMapping(value="/processAssignmentdocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Object, Object> processAssignmentdocument(HttpServletRequest request, @RequestParam int documentId, @RequestParam String survAssessStatus, @RequestParam String documentType) {
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		documentTransactionHelper.processOOSUnknowndocument(documentId, 3, survAssessStatus, documentType,  userObject, bindingResult);
		
		return targetMap;
	
	}
	
	
	@RequestMapping(value="/followUpOOSDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Object, Object> followUpOOSDocument(HttpServletRequest request, @RequestParam int documentId, @RequestParam String documentType) {
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		commonDocumentService.updateSurvellianceAssignmentStatus(documentId, 11, userObject, bindingResult);
		
		return targetMap;
	
	}
	
	
	@RequestMapping(value="/getAssignmentStatusByACRF.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, String> getAssignmentStatusByACRF(HttpServletRequest request, @RequestParam int acrfDocId) {
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		return assignmentService.getAssignmentStatusByACRF(acrfDocId,  userObject, bindingResult);
	}
	

	/**
	 * This method will redirect to the Assignment search criteria page. This
	 * method will pass the basic information to load the page passing Role
	 * codes associated to the user, Start date, End date etc
	 * 
	 * @param map
	 *            which stores the required data
	 * @param request
	 *            Request object
	 * @return redirecting to Assignment search criteria page
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getAssignmentsList")
	public String getAssignmentsList(Map<String, Object> map,
			HttpServletRequest request) {
		// Home page --> Assignment Landing Page
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		int workerRoleCode = 0;

		Map<String, String> regionMapforSelectedRole = null;

		Map<String, String> userRoleCodes = assignmentService.getUserRoleCodes(
				dummyBindingResult, userObject);
		// TODO: Redirect to Error page if map is null

		// regionMapforSelectedRole =
		// assignmentService.getRegionsMapForSelectedRole(roleCode,
		// dummyBindingResult, userObject);
		// TODO: Redirect to Error page if map is null


		String endDate = dateUtil.getCurrentDate();
		String startDate = dateUtil.get30DaysPriorDate();

		// for Supervisor or Worker
		Map<String, String> assignmentTypes = assignmentService
				.getAssignmentStatusByRole(workerRoleCode, dummyBindingResult,
						userObject, request.getSession().getId());

		session.setAttribute("userObject", userObject);

		List<Map<String, String>> laftFollowupReasons = laftFollowupDocumentService.getLaftFollowupReasons();
		map.put("laftFollowupReasons", laftFollowupReasons);	

		// For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);

		AssignmentsHomeVO assignmentsHomeVO = new AssignmentsHomeVO();
		map.put("assignmentsHomeVO", assignmentsHomeVO);
		map.put("assignmentTypes", assignmentTypes);

		
		map.put("userRoleCodes", userRoleCodes);
		
		
		map.put("regionMapforSelectedRole", regionMapforSelectedRole);

		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		List<Map<String, String>> nonCaseReasons = nonCaseDocumentService.getNonCaseReasons();
		map.put("nonCaseReasons", nonCaseReasons);
		
		return "assignment/AssignmentsTabPage";
	}

	/**
	 * This method will return the matched list of Assignments for the
	 * Assignment search criteria
	 * 
	 * @param assignmentsHomeVO
	 *            which holds the Assignment search criteria
	 * @param request
	 *            Request object
	 * @return list of Assignments
	 */
	@RequestMapping(value = "/viewWorkerAssignments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	GridObject returnAssignmentsAsJson(
			@ModelAttribute("assignmentsHomeVO") AssignmentsHomeVO assignmentsHomeVO,
			HttpServletRequest request) {
		// JQGrid for Assignments List
		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		// TODO more params handle (if super each worker or all) (if worker
		// worker id's) and also accommodate for region change
		List<AssignmentsListVO> asmtCursorOut = assignmentService
				.getAssignmentsList(assignmentsHomeVO, dummyBindingResult,
						userObject, request.getSession().getId());

		return getGridObject.GetGrid(asmtCursorOut);
	}
	
	@RequestMapping(value = "/getPriorityAssignments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	GridObject getPriorityAssignments(
			@ModelAttribute("assignmentsHomeVO") AssignmentsHomeVO assignmentsHomeVO,
			HttpServletRequest request,
			@RequestParam(required = false) String priorityType) {
		// JQGrid for Assignments List
		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		// TODO more params handle (if super each worker or all) (if worker
		// worker id's) and also accommodate for region change
		List<AssignmentsListVO> asmtCursorOut = assignmentService
				.getPriorityAssignmentsList(priorityType, assignmentsHomeVO, dummyBindingResult,
						userObject, request.getSession().getId());

		return getGridObject.GetGrid(asmtCursorOut);
	}

	/**
	 * This method used to do the Assignment Activities like Assigning, Re
	 * Assigning, Requesting, Revoking etc
	 * 
	 * @param assignReassignVO
	 *            assignment information (Assignment id, worker id, supervisor
	 *            id)
	 * @param map
	 *            which stores the required data
	 * @param request
	 *            Request object
	 * @return Transaction status (Success/failure)
	 */
	@RequestMapping(value = "/assignReassign.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, String> requestReassign(
			@ModelAttribute("assignReassignVO") AssignmentsAssignReassignVO assignReassignVO, @RequestParam(required = false) String reviewed, 
			Map<String, Object> map, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(targetMap,
				"Sample");

		System.out.println("assignmentIdsArr : "
				+ assignReassignVO.getAssignmentIds());

		assignmentService.assignReassign(assignReassignVO, reviewed, dummyBindingResult,
				userObject, request.getSession().getId());
		// Map<String, Object> map = new HashMap<String, Object>();

		if (dummyBindingResult.hasErrors()) {
			map.put("transactionResult", "failure");
		} else {
			map.put("transactionResult", "success");
		}

		return targetMap;
	}
	
	
	@RequestMapping(value = "/changeAssignmentFacilityDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, String> changeAssignmentFacilityDetails(@RequestParam int assignmentId, @RequestParam String origin, @RequestParam(required = false) String closureCategory, @RequestParam long supervisorRegionId, @ModelAttribute("rawSubFacilityVO") RawSubFacilityVO rawSubFacilityVO,@RequestParam String addressType, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		
		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap,
				"Sample");
		
		assignmentService.changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(assignmentId, origin, closureCategory, supervisorRegionId, rawSubFacilityVO.getRawSubFacilityId(), addressType, rawSubFacilityVO.getCountyFips(), userObject, bindingResult);
		
		return targetMap;
	}
	
	@RequestMapping(value = "/changeAssignmentFacilityDetailsByNewRawFacility.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, String> changeAssignmentFacilityDetailsByNewRawFacility(@RequestParam int assignmentId, @RequestParam String origin, @RequestParam(required = false) String closureCategory, @RequestParam long supervisorRegionId, @ModelAttribute("rawSubFacilityVO") RawSubFacilityVO rawSubFacilityVO, @RequestParam String addressType, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		
		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap,
				"Sample");

		assignmentService.changeAssignmentAddressDetailsByNewRawFacility(assignmentId, origin, closureCategory, supervisorRegionId, rawSubFacilityVO, addressType, userObject, bindingResult);
		

		return targetMap;
	}
	
	
	@RequestMapping(value = "/changeAssignmentProviderDetails.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, String> changeAssignmentProviderDetails(@RequestParam int assignmentId, @RequestParam String origin, @RequestParam(required = false) String closureCategory, @RequestParam long supervisorRegionId, @ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO,@RequestParam String addressType, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		
		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap,
				"Sample");
		
		assignmentService.changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(assignmentId, origin, closureCategory, supervisorRegionId, subProviderAndAddressVO.getRawProviderAddressId(), addressType, subProviderAndAddressVO.getCountyFips(), userObject, bindingResult);

		return targetMap;
	}
	
	@RequestMapping(value = "/changeAssignmentAddressDetailsByNewRawProvider.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, String> changeAssignmentAddressDetailsByNewRawProvider(@RequestParam int assignmentId, @RequestParam String origin, @RequestParam(required = false) String closureCategory, @RequestParam long supervisorRegionId, @ModelAttribute("subProviderAndAddressVO") SubProviderAndAddressVO subProviderAndAddressVO, @RequestParam String addressType, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		
		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap,
				"Sample");

		assignmentService.changeAssignmentAddressDetailsByNewRawProvider(assignmentId, origin, closureCategory, supervisorRegionId, subProviderAndAddressVO, addressType, userObject, bindingResult);
		

		return targetMap;
	}
	
	@RequestMapping(value = "/triggerSurvAssignment.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, String> triggerSurvAssignment(@RequestParam int documentId, @RequestParam String documentType, @RequestParam String triggerAssignmentCheckStatus, @RequestParam String ahiFlag, @RequestParam String stateNo, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		
		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap,
				"Sample");

		assignmentService.triggerSurvAssignment(documentId, documentType, triggerAssignmentCheckStatus, ahiFlag, stateNo, userObject, bindingResult);
		

		return targetMap;
	}
	
	
	
	/**
	 * This method is used to load the Assignment details, Assignment Activities
	 * of an Assignment
	 * 
	 * @param assignmentId
	 *            Assignment Id
	 * @param map
	 *            which stores the required data
	 * @param request
	 *            Request object
	 * @return redirecting to Assignment details page to load the Assignment
	 *         Details and Assignment Activities
	 */
	@RequestMapping("/getAssignmentsDetails")
	public String getAssignmentsDetails(
			@RequestParam("assignmentId") long assignmentId, @RequestParam(required = false) String workerRoleCode,
			@RequestParam(required = false) String reabstract, Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		AssignmentDetailsVO assignmentDetailsVO = assignmentService
				.getAssignmentsDetails(assignmentId, dummyBindingResult,
						userObject, request.getSession().getId());
		
		List<Map<String, String>> closureCategoryMap = null;
		List<Map<String, String>> reabClosureCategoryMap = null;
		
		if (CommonUtil.isNotNullAndEmpty(reabstract) && reabstract.equals("Y")) {
			
			List<Map<String, String>> reabActivityMap = assignmentService.getReabActivities(dummyBindingResult, userObject, request.getSession().getId());
			map.put("reabActivityMap", reabActivityMap);
			
			String workerRegionId = assignmentService.getWorkerCORegionId(userObject.getWorkerId(), workerRoleCode);
			JSONObject userPermissionsList = commonService.getUserPermissions(userObject.getWorkerId(), workerRegionId,dummyBindingResult);
			request.getSession().setAttribute("userPermissions_workerRegion", userPermissionsList);
			
			closureCategoryMap = assignmentService.getAssignmentClosureStatus(dummyBindingResult, userObject, request.getSession().getId(), true, null);
			reabClosureCategoryMap = assignmentService.getAssignmentClosureStatus(dummyBindingResult, userObject, request.getSession().getId(), false, "ALL");
		} else {
			reabstract = "N";
			if(CommonUtil.isNotNullAndEmpty(assignmentDetailsVO.getClosureCategory())){
				closureCategoryMap = assignmentService.getAssignmentClosureStatus(dummyBindingResult, userObject, request.getSession().getId(), true, null);
			}else{
				closureCategoryMap = assignmentService.getAssignmentClosureStatus(dummyBindingResult, userObject, request.getSession().getId(), false, "ALL");
			}
		}
		assignmentDetailsVO.setReabstract(reabstract);
		
		map.put("closureCategoryMap", closureCategoryMap);
		map.put("reabClosureCategoryMap", reabClosureCategoryMap);
		
		List<AssignmentActivityVO> assignmentViewedCommentDetails = assignmentService.getAssignmentViewedCommentDetails(assignmentId, dummyBindingResult, userObject, userObject.getSessionId());
		List<AssignmentActivityVO> assignmentEditActionCommentDetails = assignmentService
				.getAssignmentEditActionCommentDetails(assignmentId, dummyBindingResult,
						userObject, request.getSession().getId(), reabstract);
		map.put("assignmentDetailsVO", assignmentDetailsVO);
		map.put("assignmentViewedCommentDetails", assignmentViewedCommentDetails);
		map.put("assignmentEditActionCommentDetails", assignmentEditActionCommentDetails);

		session.setAttribute("userObject", userObject);

		return "assignment/AssignmentDetails";
	}
	
	@RequestMapping("/getAssignmentsDetailsInDialog")
	public String getAssignmentsDetailsInDialog(
			@RequestParam("assignmentId") long assignmentId, @RequestParam(required = false) String reabstract, 
			Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		AssignmentDetailsVO assignmentDetailsVO = assignmentService
				.getAssignmentsDetails(assignmentId, dummyBindingResult,
						userObject, request.getSession().getId());
		
		List<AssignmentActivityVO> assignmentViewedCommentDetails = assignmentService.getAssignmentViewedCommentDetails(assignmentId, dummyBindingResult, userObject, userObject.getSessionId());
		List<AssignmentActivityVO> assignmentEditActionCommentDetails = assignmentService
				.getAssignmentEditActionCommentDetails(assignmentId, dummyBindingResult,
						userObject, request.getSession().getId(), reabstract);
		
		List<Map<String, String>> closureCategoryMap = null;
		
		if(CommonUtil.isNotNullAndEmpty(assignmentDetailsVO.getClosureCategory())){
			closureCategoryMap = assignmentService.getAssignmentClosureStatus(dummyBindingResult, userObject, request.getSession().getId(), true, null);
		}else{
			closureCategoryMap = assignmentService.getAssignmentClosureStatus(dummyBindingResult, userObject, request.getSession().getId(), false, "ALL");
		}

		map.put("closureCategoryMap", closureCategoryMap);

		map.put("assignmentDetailsVO", assignmentDetailsVO);
		map.put("assignmentViewedCommentDetails", assignmentViewedCommentDetails);
		map.put("assignmentEditActionCommentDetails", assignmentEditActionCommentDetails);

		return "assignment/AssignmentsPageInDialog";
	}
	
	

	/**
	 * This method is used to update the comments of an Assignment or to Close
	 * an Assignment
	 * 
	 * @param assignmentUpdateVO
	 *            containing Assignment information, Comments, Closure
	 *            Category(If Available)
	 * @param request
	 *            Request object
	 * @return transaction status
	 */
	@RequestMapping(value = "/updateAssignment.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> updateAssignment(
			@ModelAttribute("assignmentUpdateVO") AssignmentUpdateVO assignmentUpdateVO, @ModelAttribute("ahiDocData") AHIDocVO ahiDocData,
			HttpServletRequest request) {
		
		
		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, Object> dummyMindingMap = new HashMap<String, Object>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		assignmentService.updateOrClosedAssignment(ahiDocData, assignmentUpdateVO, dummyBindingResult, userObject);

		
		if(CommonUtil.isNotNullAndEmpty(assignmentUpdateVO.getClosureCategory()) && CommonUtil.isNotNullAndEmpty(ahiDocData.getFNAME())){
			dummyMindingMap.put("AHI_AVAILABLE", true);
		}else if(CommonUtil.isNotNullAndEmpty(assignmentUpdateVO.getClosureCategory())){
			dummyMindingMap.put("CLOSE_ASSIGNMENT", true);
		}else{
			dummyMindingMap.put("COMMENTS", true);
		}
		
		
		return dummyMindingMap;
	}
	
	
	@RequestMapping(value = "/getWorkerListByRegion.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Integer, String> getWorkerListByRegion(
			@RequestParam String regionCode,
			HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		Map<Integer, String> workers = assignmentService.getWorkerNamesByRegion(regionCode,
				dummyBindingResult, userObject, request.getSession()
						.getId());
		
		return workers;
	}
	
	/**
	 * This method is used to load the Ehars data for stateno associated to the
	 * selected Assignment. This data will come from the SQL server database
	 * 
	 * @param stateno
	 *            associated to the Selected Assignment
	 * @param request
	 *            Request object
	 * @param map
	 *            which stores the required data
	 * @return redirecting to eharsPersonViewDetails to load the ehars data in a
	 *         table format
	 */
/*	@RequestMapping(value = "/loadEharsPersonViewDetails.html", method = RequestMethod.POST)
	public String loadEharsPersonViewDetails(
			@RequestParam("stateno") String stateno,
			HttpServletRequest request, Map<String, Object> map) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		Map<String, String> eharPersonDataObj = assignmentService
				.loadEharsPersonViewDetails(stateno, dummyBindingResult,
						userObject, request.getSession().getId());

		map.put("eharPersonDataObj", eharPersonDataObj);

		return "person/ehars/eharsPersonViewDetails";
	}*/
	
	

	@RequestMapping(value="/getAssignmentReportMatrix.html", method = RequestMethod.POST)
	public String  getAssignmentReportMatrix(@RequestParam String region, @RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();

		List<String> regionNames = new ArrayList<String>();
		
		
		String completeFromDate = null;
		String completeToDate = null;
		
		List<String>  monthNames = new ArrayList<String>();
		
		Calendar end_calender = Calendar.getInstance();
		Calendar start_calender = Calendar.getInstance();
		
		
		try {
			
		    
	        DateFormat formatter;
	        
	        formatter = new SimpleDateFormat("MM/yyyy");
	        
	        
	        end_calender.setTime(formatter.parse(toDate));
	        end_calender.set(Calendar.DAY_OF_MONTH, end_calender.getActualMaximum(Calendar.DAY_OF_MONTH));
	        
	        
	        
	        start_calender.setTime(formatter.parse(fromDate));
	        
	        

	        int diffYear = end_calender.get(Calendar.YEAR) - start_calender.get(Calendar.YEAR);
	        int diffMonth = diffYear * 12 + end_calender.get(Calendar.MONTH) - start_calender.get(Calendar.MONTH)+1;
	        
	        String errorMessage = null;
	        
	        if(diffMonth <= 0 ){
	        	errorMessage = "Please check the Date Range.";
	        }
	        
	        if(errorMessage != null){
	        	map.put("errorMessage", errorMessage);
	        	
	        	return "assignment/AssignmentsReportsMatrix";
	        	
	        }
	        
	        
	        formatter = new SimpleDateFormat("MM/dd/yyyy");
			
			
			completeFromDate = formatter.format(start_calender.getTime());
			completeToDate = formatter.format(end_calender.getTime());
			
			
		} catch (ParseException e) {
			log.error("Unable to parse the Assignment Report dates formDate : {}",fromDate);
			log.error("To Date {}",toDate);
			log.error(e.getMessage());
		}
		
		while (start_calender.getTimeInMillis() < end_calender.getTimeInMillis()) {
			monthNames.add(monthMap.get(start_calender.get(Calendar.MONTH)) + start_calender.get(Calendar.YEAR));
			start_calender.add(Calendar.MONTH, 1);
	    }
		
		String toDateMonth = monthMap.get(end_calender.get(Calendar.MONTH)) + end_calender.get(Calendar.YEAR);
		
		if(monthNames.indexOf(toDateMonth) == -1){
			monthNames.add(toDateMonth);
		}
		
		
		Map<String, String> dummyMindingMap= new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");

		Map<String, Map<String, Map<String, Map<String, Integer>>>> assignmentReportsMatrix =  assignmentReportsService.getAssignmentReportMatrix(completeFromDate, completeToDate, region, dummyBindingResult, userObject);
		
		map.put("assignmentReportsMatrixObj", assignmentReportsMatrix);
		
		Map<String, Map<String, Map<String, Map<String, Integer>>>> openAssignmentReportsByWorker =  assignmentReportsService.getAssignmentStatusReportByWorkerId(region, dummyBindingResult, userObject);
		
		//map.put("assignmentReportsMatrixObj", assignmentReportsMatrix);
		
		
		map.put("openAssignmentReportsByWorker", openAssignmentReportsByWorker);
		
		
		Map<String, String> regionsList = administrationService.getSrchregionTypes(dummyBindingResult, userObject);
		
		if("ALL".equals(region)){
			regionNames.add("ALL");
			regionNames.addAll(regionsList.keySet());
		}else{
			regionNames.add(region);
		}
		
		regionsList.put("ALL", "ALL Regions");
		map.put("regionNames", regionNames);
		
		map.put("monthNames", monthNames);
		
		
		map.put("regionsList", regionsList);
		
		return "assignment/AssignmentsReportsMatrix";
		
	}
	
	
	@RequestMapping(value="/getAssignmentReportByDocumentMatrix.html", method = RequestMethod.POST)
	public String  getAssignmentReportByDocumentMatrix(@RequestParam String fileName, @RequestParam String fromDate, @RequestParam String toDate, @RequestParam String documentType, HttpServletRequest request, Map<String, Object> map){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();

		Map<String, String> dummyMindingMap= new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");

		List<Map<String, String>> assignmentReportsMatrixByRegion =  assignmentReportsService.getAssignmentStatusReportByDocument(fromDate, toDate, fileName, documentType, dummyBindingResult, userObject);
		
		map.put("assignmentReportsMatrixByRegion", assignmentReportsMatrixByRegion);
		
		
		List<Map<String, String>> assignmentReportsMatrixByEharsStatus =  assignmentReportsService.getAssignmentStatusReportByEharsStatus(fromDate, toDate, fileName, documentType, dummyBindingResult, userObject);
		
		map.put("assignmentReportsMatrixByEharsStatus", assignmentReportsMatrixByEharsStatus);
		
		return "assignment/reports/AssignmentsReportsMatrixByDocument";
		
	}
	
	
	
	@RequestMapping(value="/getAHIAssignmentsByRegionAndCategory.html", method = RequestMethod.POST)
	public String  getAHIAssignmentsByRegionAndCategory(@RequestParam String regionId, @RequestParam String assignmentStatus, @RequestParam String documentType, @RequestParam String ahiFlag, HttpServletRequest request, Map<String, Object> map){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();

		Map<String, String> dummyMindingMap= new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");

		List<Map<String, String>> assignmentDocuments =  assignmentReportsService.getAHIAssignmentsByRegionAndCategory(regionId, assignmentStatus, documentType, ahiFlag, dummyBindingResult, userObject);
		
		map.put("assignmentDocuments", assignmentDocuments);
		
		//documentName = documentName.replaceAll("(?i)aids", "950").replaceAll("(?i)hiv", "900").replaceAll("(?i)adap", "HUCP");
		
		map.put("documentDisplayName", documentType);
		
		return "assignment/reports/AHIAssignmentsMatrix";
		
	}

	
	
	@RequestMapping(value="/getAssignmentDocumentsByStatusByDocumentByCategory.html", method = RequestMethod.POST)
	public String  getAssignmentDocumentsByStatusByDocumentByCategoryInHtml(@RequestParam String regionId, @RequestParam String assignmentStatus, @RequestParam String fileName, @RequestParam String fromDate, @RequestParam String toDate, @RequestParam String documentType, @RequestParam String eharsStatus, @RequestParam String category, @RequestParam String documentName, HttpServletRequest request, Map<String, Object> map){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();

		Map<String, String> dummyMindingMap= new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");

		List<Map<String, String>> assignmentDocuments =  assignmentReportsService.getAssignmentDocumentsByStatusByDocumentByRegion(regionId, assignmentStatus, fromDate, toDate, fileName, documentType, eharsStatus, category, dummyBindingResult, userObject);
		
		map.put("assignmentDocuments", assignmentDocuments);
		
		documentName = documentName.replaceAll("(?i)aids", "950").replaceAll("(?i)hiv", "900").replaceAll("(?i)adap", "HUCP");
		
		map.put("documentDisplayName", documentName);
		
		return "assignment/reports/AssignmentsDocumentsMatrix";
		
	}
	
	
	@RequestMapping(value="/getAssignmentDocumentsByStatusByDocumentByCategory.pdf", method = RequestMethod.POST, produces="application/pdf")
	public ModelAndView  getAssignmentDocumentsByStatusByDocumentByCategoryInPdf(@RequestParam String documentName, @RequestParam String regionId, @RequestParam String regionName, @RequestParam String assignmentStatusDisplayName, @RequestParam String assignmentStatus, @RequestParam String fileName, @RequestParam String fileDisplayName, @RequestParam String fromDate, @RequestParam String toDate, @RequestParam String documentType, @RequestParam String eharsStatus, @RequestParam String category, HttpServletRequest request, HttpServletResponse response, Map<String, Object> map){
		
		response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename= Assignment Report");

		Map<String, String> dummyMindingMap= new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		List<Map<String, String>> assignmentDocuments =  assignmentReportsService.getAssignmentDocumentsByStatusByDocumentByRegion(regionId, assignmentStatus, fromDate, toDate, fileName, documentType, eharsStatus, category, dummyBindingResult, userObject);

		
		ModelAndView mav = new ModelAndView("assignmentReportsByDocumentPdfView");
		
        mav.addObject("assignmentDocuments", assignmentDocuments);
        
        mav.addObject("documentType", documentName);
        
        
        if(CommonUtil.isNotNullAndEmpty(fileName)){
        	mav.addObject("filerCondition", fileDisplayName);
        }else{
        	mav.addObject("filerCondition", fromDate +" - "+toDate);
        }
        
        
        
        if("eHars".equals(category)){
        	mav.addObject("filterByCategory", eharsStatus);
        	mav.addObject("filterByCategoryColumnName", "eHARS Status");
        }else{
        	mav.addObject("filterByCategory", regionName);
        	mav.addObject("filterByCategoryColumnName", "Region");
        }
        
        
        
        mav.addObject("assignmentStatusDisplayName", assignmentStatusDisplayName);
        
        return mav;
        
        		
	}
	
	
	
	
	@RequestMapping(value = "/getFileNamesForDocumentType.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getFileNamesForDocumentType(
			@RequestParam String documentType, HttpServletRequest request) {


		List<JSONObject> fileNamesObj = commonDocumentService.getFileNamesForDocumentType(documentType);
		
		return fileNamesObj;
	}
	
	@RequestMapping(value = "/updateDocumentFileDisplayName.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> updateDocumentFileDisplayName(
			@RequestParam String fileName, @RequestParam String displayName, String documentType, HttpServletRequest request) {
		 
		
		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(
				targetMap, "Sample");
		
		
		assignmentService.updateDocumentFileDisplayName(fileName, displayName, documentType, userObject, bindingResult);
		
		return targetMap;
	}
	
	
	
	
	@RequestMapping(value = "/closeNeedsAssignment.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String>   closeNeedsAssignment(
			@ModelAttribute("assignReassignVO") AssignmentsAssignReassignVO assignReassignVO,  HttpServletRequest request) {


		
		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(
				targetMap, "Sample");
		
		
		assignmentService.closeNeedsAssignment(assignReassignVO, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	/**
	 * This method is used to get the Activities associated to the Assignment.
	 * This function will call when we give new comment we need reload the
	 * Assignment comments history and Assignment Activities
	 * 
	 * @param assignmentId
	 *            selected Assignment id
	 * @param request
	 *            Request object
	 * @param map
	 *            which stores the required data
	 * @return redirecting to assignmentActivity page to load the Assignment
	 *         comments and Activites
	 */
	@RequestMapping(value = "/loadAssignmentCommentDetails", method = RequestMethod.POST)
	public String loadAssignmentCommentDetails(
			@RequestParam("assignmentId") long assignmentId, @RequestParam(required = false) String reabstract, 
			HttpServletRequest request, Map<String, Object> map) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		List<AssignmentActivityVO> assignmentViewedCommentDetails = assignmentService
				.getAssignmentViewedCommentDetails(assignmentId, dummyBindingResult,
						userObject, request.getSession().getId());
		List<AssignmentActivityVO> assignmentEditActionCommentDetails = assignmentService
				.getAssignmentEditActionCommentDetails(assignmentId, dummyBindingResult,
						userObject, request.getSession().getId(), reabstract);

		map.put("assignmentViewedCommentDetails", assignmentViewedCommentDetails);
		map.put("assignmentEditActionCommentDetails", assignmentEditActionCommentDetails);

		return "assignment/assignmentActivity";
	}
	
	@RequestMapping(value = "/getAssignmentTriggeringDocumentDetails.html", method = RequestMethod.POST)
	public String getAssignmentTriggeringDocumentDetails(@RequestParam int assignmentId,
			HttpServletRequest request, Map<String, Object> map) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");
		
		

		//@RequestParam int documentId, @RequestParam String documentType, 
		
		Map<String, Object> documentDetails =  assignmentService.getTriggeringDocumentDetails(assignmentId);
		
		
		int documentId = ((BigDecimal)documentDetails.get("DOCUMENT_ID")).intValue();
		String documentType = (String)documentDetails.get("DOCUMENT_TYPE_CODE");
		

		Map<String, String> assignmentAndAddressDetails = assignmentService.getAssignmentAndAddressDetails(assignmentId, dummyBindingResult, userObject);
				
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(documentId, userObject);
    	
    	map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);

    	map.put("assignmentAndAddressDetails", assignmentAndAddressDetails);
    	
    	
    	
    	if("ADAP".equals(documentType)){
    		ADAPDocVO aDAPDocVO = aDAPDocumentService.getADAPDoc(documentId, dummyBindingResult, userObject, request.getSession().getId());
    		map.put("stdADAPDocVO", aDAPDocVO);
            
    		return "assignment/document/TriggeringADAPDocument";

    	}else if("PRF".equals(documentType)){
    		PRFDocumentVO pRFDocumentVO = pRFDocumentService.getPRFDocument(documentId);
    		map.put("pRFDocumentVO", pRFDocumentVO);
            
    		return "assignment/document/TriggeringPRFDocument";

    	}else if("INS".equals(documentType)){
    		MedicaidDocVO medicaidDocVO = medicaidDocumentService.getMedicaidDoc(documentId, dummyBindingResult, userObject, userObject.getSessionId());
    		map.put("medicaidDocVO", medicaidDocVO);
            
    		return "assignment/document/TriggeringINSDocument";

    	}else{
    		StdEcrlsLabDocVO stdLabDocVO = labDocumentService.getLabDoc(documentId, documentType, dummyBindingResult, userObject, request.getSession().getId());
    		map.put("stdLabDocVO", stdLabDocVO);
            
    		return "assignment/document/TriggeringLabDocument";
    	}
    	
		
	}
	
	

	/**
	 * This method is used to get Regions list associated to selected role
	 * 
	 * @param roleCode
	 *            (Worker/Supervisor/Co viewer/Coordinator)
	 * @param request
	 *            Request object
	 * @param map
	 *            which stores the required data
	 * @return list of Regions
	 */
	@RequestMapping(value = "/getAssignmentRegionList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Map<String, String>> getAssignmentRegionList(
			@RequestParam Integer roleCode, HttpServletRequest request,
			Map<String, Object> map) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		List<Map<String, String>> regionMapforSelectedRole = assignmentService
				.getRegionsMapForSelectedRole(roleCode, dummyBindingResult,
						userObject);

		return regionMapforSelectedRole;

	}

	/**
	 * This method will redirect to the Assignment search criteria page. This
	 * method will pass the basic information to load the page passing Role
	 * codes associated to the user, Start date, End date etc
	 * 
	 * @param roleCode
	 *            selected role code(Worker/Supervisor/Co viewer/Coordinator)
	 * @param regionCode
	 *            selected Region code
	 * @param workerRegionId
	 * @param request
	 *            Request object
	 * @param map
	 *            which stores the required data
	 * @return redirecting to AssignmentsSearchCriteria page to load the
	 *         remaining search criteria fields
	 */
	@RequestMapping(value = "/getAssignmentWorkersListInRegion", method = RequestMethod.POST)
	public String getAssignmentWorkersListInRegion(@RequestParam int roleCode,
			@RequestParam String regionCode,
			@RequestParam String workerRegionId, HttpServletRequest request,
			Map<String, Object> map) {

		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(
				dummyMindingMap, "Sample");

		Map<Integer, String> workers = null;

		int selectedUserId = 0;
		if (roleCode == 2) {
			selectedUserId = userObject.getWorkerId();
			workers = new HashMap<Integer, String>();
			workers.put(selectedUserId, userObject.getUserFullName());
		} else {
			selectedUserId = -1;
			workers = assignmentService.getWorkerNamesByRegion(regionCode,
					dummyBindingResult, userObject, request.getSession()
							.getId());
		}

		// for Supervisor or Worker
		Map<String, String> assignmentTypes = assignmentService
				.getAssignmentStatusByRole(roleCode, dummyBindingResult,
						userObject, request.getSession().getId());

		map.put("assignmentTypes", assignmentTypes);

		JSONObject userPermissionsList = commonService
				.getUserPermissions(userObject.getWorkerId(), workerRegionId,
						dummyBindingResult);

		request.getSession().setAttribute("userPermissions_workerRegion",
				userPermissionsList);

		// map.put("userPermissions_workerRegion", userPermissionsList);

		String endDate = dateUtil.getCurrentDate();

		String startDate = dateUtil.get30DaysPriorDate();

		map.put("startDate", startDate);
		map.put("endDate", endDate);

		map.put("workers", workers);

		AssignmentsHomeVO assignmentsHomeVO = new AssignmentsHomeVO();

		assignmentsHomeVO.setWorkerId(selectedUserId);

		map.put("assignmentsHomeVO", assignmentsHomeVO);

		return "assignment/AssignmentsSearchCriteria";
	}
	
	private byte[] parseAssignmentsByRegionByStatus(List<Map<String, String>> assignmentsList, String documentType, String regionName, String assignmentStatusDisplayName){
		
		StringBuilder htmlContent = new StringBuilder("<html><body><div style='margin:0px auto'><div style='margin-top:100px;' ><span>"+documentType+" "+assignmentStatusDisplayName+" "+regionName+"</span></div><table><thead style='font-size:12px;'><tr><th >STATENO</th>");
		
		
		
		boolean isRegionAvailable = false;
		boolean isAssignmentStatusAvailable = false;
		
		if(CommonUtil.isNotNullAndEmpty(regionName)){
			isRegionAvailable = true;
		}
		
		if(!isRegionAvailable){
			htmlContent.append("<th>Region</th>");
		}
		
		
		if(CommonUtil.isNullOrEmpty(assignmentStatusDisplayName)){
			isAssignmentStatusAvailable = true;
		}
		
		if(!isAssignmentStatusAvailable){
			htmlContent.append("<th>Assignment Status</th>");
		}
		
		
		
		
		htmlContent.append("<th>Worker Name</th></tr></thead><tbody>");
		if(assignmentsList != null && assignmentsList.size() > 0){
			for (Map<String, String> assignment : assignmentsList) {
				htmlContent.append("<tr><td style='width:150px'>").append(assignment.get("STATENO")).append("</td>");
				
				if(!isRegionAvailable){
					htmlContent.append("<td style='width:200px'>").append(assignment.get("REGION_DESCRIPTION")).append("</td>");
				}
				
				if(!isAssignmentStatusAvailable){
					htmlContent.append("<td style='width:505px'>").append(assignment.get("ASSIGNMENT_STATUS")).append("</td>");
				}
				htmlContent.append("<td style='width:250px'>").append(assignment.get("WORKER_NAME")).append("</td></tr>");
			}
		}else{
			htmlContent.append("<tr><td style='width:250px'>No Assignment data available.</td></tr>");
		}
		htmlContent.append("</tbody></table></div>");
		
		return htmlContent.toString().getBytes();
	}
	
	
	@RequestMapping(value="/getAssignmentsClosureStatusReport")	
	public String getAssignmentsClosureStatusReport(HttpServletRequest request, Map<String, Object> map) {
		
		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		
		Map<String, String> regionsList = administrationService.getSrchregionTypes(dummyBindingResult, userObject);
		map.put("regionsList", regionsList);
		
		return "assignment/reports/AssignmentsClosureStatusReport";
	}	
	
	@RequestMapping(value="/getAssignmentClosureStatusReportData.json", method = RequestMethod.POST,  produces = "application/json")
	public @ResponseBody List<JSONObject> getAssignmentClosureStatusReportData(@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String regionCode, HttpServletRequest request, Map<String, Object> map){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> dummyMindingMap= new HashMap<Object, Object>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		List<JSONObject> jsonResponse  = assignmentReportsService.getAssignmentClosureStatusReport(fromDate, toDate, regionCode, dummyBindingResult, userObject);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/getReabstractAssignments")	
	public String getReabstractAssignments(HttpServletRequest request, Map<String, Object> map) {
		
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();
		
		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");

		map.put("userRoleCodes", assignmentService.getUserCORoleCodes(dummyBindingResult, userObject));
		
		map.put("workers", assignmentService.getReabstractionWorkerNamesByRegion("9", dummyBindingResult, userObject, request.getSession().getId()));
		
		session.setAttribute("userObject", userObject);

		// For State and County DropDown
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);

		AssignmentsHomeVO assignmentsHomeVO = new AssignmentsHomeVO();
		map.put("assignmentsHomeVO", assignmentsHomeVO);
		
		Map<String, String> regionsList = administrationService.getSrchregionTypes(dummyBindingResult, userObject);
		map.put("regionsList", regionsList);		
		
		return "assignment/ReabstractAssignments";
	}	
	
	@RequestMapping(value = "/getReabstractAssignmentStatusByRole.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getReabstractAssignmentStatusByRole(@RequestParam int roleCode, HttpServletRequest request) {
		
		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");

		List<JSONObject> statusforSelectedRole = assignmentService.getReabstractAssignmentStatusByRole(roleCode, dummyBindingResult, userObject);
		
		return statusforSelectedRole;
	}		
	
	@RequestMapping(value = "/getReabstractAssignmentWorkersList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Integer, String> getReabstractAssignmentWorkersList(@RequestParam int roleCode, HttpServletRequest request) {
		
		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		Map<Integer, String> workers = null;
//		roleCode = 2;
		
		int selectedUserId = 0;
		if (roleCode == 2) {
			selectedUserId = userObject.getWorkerId();
			workers = new HashMap<Integer, String>();
			workers.put(selectedUserId, userObject.getUserFullName());
		} else {
			selectedUserId = -1;
			workers = assignmentService.getReabstractionWorkerNamesByRegion("9", dummyBindingResult, userObject, request.getSession().getId());
		}
		
		return workers;
	}			
	

	@RequestMapping(value = "/assignReabstract.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> requestAssignReabstract(
			@ModelAttribute("reabAssignVO") ReabAssignmentsAssignVO reabAssignVO, Map<String, Object> map, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(targetMap,"Sample");

		assignmentService.reabAssign(reabAssignVO, dummyBindingResult,userObject, request.getSession().getId());

		if (dummyBindingResult.hasErrors()) {
			map.put("transactionResult", "failure");
		} else {
			map.put("transactionResult", "success");
		}

		return targetMap;
	}
		
	@RequestMapping(value = "/updateReabActivityNStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> updateReabActivityNStatus(@RequestParam String assignmentId, @RequestParam String newReabActivity, @RequestParam String reabCrfDocId, 
		@RequestParam String crfDocId, @RequestParam String documentType, @RequestParam String reabClosureCategory, Map<String, Object> map, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(targetMap,"Sample");
		String comments = "";

		assignmentService.updateReabActivityNStatus(assignmentId, reabCrfDocId, crfDocId, documentType, comments, newReabActivity, reabClosureCategory, dummyBindingResult,userObject, request);

		if (dummyBindingResult.hasErrors()) {
			map.put("transactionResult", "failure");
		} else {
			map.put("transactionResult", "success");
		}

		return targetMap;
	}	
	
	@RequestMapping(value = "/getCRFDiffSummary.html", method = RequestMethod.POST)
	public String getCRFDiffSummary(@RequestParam String documentType, @RequestParam String stateNo, @RequestParam int assignmentId, 
			@RequestParam int fsCRFDocId, @RequestParam int reabCRFDocId, @RequestParam int workerRole, HttpServletRequest request, Map<String, Object> map) {

		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> dummyMindingMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		Map<String, Object> crfDiffEleMap = assignmentService.getCRFDiffSummary(assignmentId, documentType, fsCRFDocId, reabCRFDocId, 
																						dummyBindingResult, userObject, request);
		
		map.put("stateNo", stateNo);
		map.put("closureCategory", crfDiffEleMap.get("closureCategory"));
		
		map.put("fsRawCrfDoc", crfDiffEleMap.get("fsRawCrfDoc")); 
		map.put("reabRawCrfDoc", crfDiffEleMap.get("reabRawCrfDoc")); 
		
		map.put("hivImmunoNonDiffInfo", crfDiffEleMap.get("hivImmunoNonDiff"));
		map.put("hivImmunoDiffInfo", crfDiffEleMap.get("hivImmunoDiff"));
		map.put("hivDetTestQualInfo", crfDiffEleMap.get("hivDetTestQual"));
		map.put("hivDetTestQualVLInfo", crfDiffEleMap.get("hivDetTestQualVL"));
		map.put("drugResTestGenoInfo", crfDiffEleMap.get("drugResTestGeno"));
		
		map.put("closestToCurDx", crfDiffEleMap.get("closestToCurDx"));
		map.put("firstLowCd4", crfDiffEleMap.get("firstLowCd4"));
		map.put("otherCd4", crfDiffEleMap.get("otherCd4"));		
		
		map.put("arvInfo", crfDiffEleMap.get("arvInfo"));
		map.put("docType", documentType);
		map.put("workerRole", workerRole);
		
		AssignmentDetailsVO assignmentDetailsVO = assignmentService
				.getAssignmentsDetails(assignmentId, dummyBindingResult,
						userObject, request.getSession().getId());
		
		map.put("assignmentDetailsVO", assignmentDetailsVO);
		
		return "assignment/ReabAssignmentSummary";
    	
		
	}
	
	@RequestMapping(value = "/getAssignmentCompareData.json", method = RequestMethod.POST)
	public @ResponseBody List<JSONObject> getAssignmentCompareData(@RequestParam int assignmentId, HttpServletRequest request, Map<String, Object> map) {

		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();
		
		List<JSONObject> reabDiffData = assignmentService.getReabstractionDiffData(assignmentId);
		
		return reabDiffData;
		
	}
	
	@RequestMapping(value = "/saveReabReviewComments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> saveReabReviewComments(@RequestParam String assignmentId, @RequestParam String reviewComments, Map<String, Object> map, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(targetMap,"Sample");
		assignmentService.updateReabActivityNStatus(assignmentId, "", "", "", reviewComments, "AREAB09", "", dummyBindingResult,userObject, request);

		if (dummyBindingResult.hasErrors()) {
			map.put("transactionResult", "failure");
		} else {
			map.put("transactionResult", "success");
		}

		return targetMap;
	}	
	
	@RequestMapping(value = "/deleteCRFNReopenAssign.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, String> deleteCRFNReopenAssign(@ModelAttribute("assignReassignVO") AssignmentsAssignReassignVO assignReassignVO, @RequestParam int documentId, @RequestParam String documentType, 
					@RequestParam String stateno, Map<String, Object> map, HttpServletRequest request) {

		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		Map<String, String> targetMap = new HashMap<String, String>();
		BindingResult bindingResult = new MapBindingResult(targetMap,"Sample");

		assignReassignVO.setWorkerId(userObject.getWorkerId());
		System.out.println("assignmentIdsArr : " + assignReassignVO.getAssignmentIds());
		
		assignmentService.deleteCRFNReopenAssign(assignReassignVO, documentId, documentType, stateno, bindingResult, userObject, request);

		if (bindingResult.hasErrors()) {
			map.put("transactionResult", "failure");
		} else {
			map.put("transactionResult", "success");
		}

		return targetMap;
	}
	
	@RequestMapping("/getOpenCaseLoadReport")
	public String getOpenCaseLoadReport(HttpServletRequest request, Map<String, Object> map) {

		return "assignment/reports/AssignmentsOpenCaseLoadReport";

	}

	// getOpenCaseloadSummaryData.json
	// write another function to call the view query and send it to
	@RequestMapping(value = "/getOpenCaseloadSummaryData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<JSONObject> getOpenCaseloadSummaryData(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> dummyMindingMap = new HashMap<Object, Object>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");

		List<JSONObject> res = (List<JSONObject>) assignmentService.getOpenCaseloadSummaryData(userObject,
				dummyBindingResult);

		return res;

	}
}
