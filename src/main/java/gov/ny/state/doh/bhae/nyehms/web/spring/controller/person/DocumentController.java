package gov.ny.state.doh.bhae.nyehms.web.spring.controller.person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.JsonObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.bo.ProcessACRFForm;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ADAPDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AIRSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DinDoccsDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EPRFOOCDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NBSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYCLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NonCaseDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycIJRDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFDocumentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFPartnerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TBDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAddressDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAliasDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfRidrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.CodedArvMedicationsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.PcrfSubstanceDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LaftFollowupDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.CasaDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DoccsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.IccrDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.InsDeathDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycDeathDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycEharsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SsdmDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfRefData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdOldDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SyphDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxRefData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ClimsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.MedicaidOldDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiCauseDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.OldCaseDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserAuthorization;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ADAPDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.AIRSDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.BlackBoxDocService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CASADocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CCDDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CLIMSDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CancerDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DeathDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DoccsDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.EPRFOOCDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HIEDTHDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ICCRDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.LabDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.MedicaidDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.MedicaidOldDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NDIDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NonCaseDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NycDeathDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NycIJRDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NycPRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OldCaseDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.PRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.QualCareDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.SSDMDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.STDConfDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.SYPHDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.TBDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.TrackingDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftDetailsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftFollowupDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.ClusterPartnerReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.NBSReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.FacilityService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.validation.document.DocumentValidator;


/**
 * This is controller class for various types documents. This listens to all the
 * web requests coming from documents.
 *
 * @author vxp06
 */
@SessionAttributes(value = "stdPRFDocVO") // using this to store object as
											// session variable
@Controller
public class DocumentController {

	private static Logger log = LoggerFactory.getLogger(DocumentController.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	@Autowired	DeathDocumentService deathDocumentSrvice;
	@Autowired	NycDeathDocumentService nycDeathDocumentService;
	@Autowired	CASADocumentService casaDocumentService;
	@Autowired	SYPHDocumentService syphDocumentService;
	@Autowired	TBDocumentService tbDocumentSrvice;
	@Autowired	TrackingDocumentService trackingDocumentService;
	@Autowired	OldCaseDocumentService oldCaseDocumentService;
	@Autowired	MedicaidDocumentService medicaidDocumentSrvice;
	@Autowired	MedicaidOldDocumentService medicaidOldDocumentSrvice;
	@Autowired	DoccsDocumentService doccsDocumentService;
	@Autowired	LabDocumentService labDocumentService;
	@Autowired	SSDMDocumentService ssdmDocumentService;
	@Autowired	ADAPDocumentService adapDocumentService;
	@Autowired	DeathDocumentService deathDocumentService;
	@Autowired	PRFDocumentService pRFDocumentService;
	@Autowired	NycPRFDocumentService nycPRFDocumentService;
	@Autowired	DocumentTransactionHelper documentTransactionHelper;
	@Autowired	UserAuthorization userAuthorization;
	@Autowired	ProcessACRFForm processACRFForm;
	@Autowired	ACRFDocumentService acrfFDocumentService;
	@Autowired	GetGridObject getGridObject;
	@Autowired	AIRSDocumentService aIRSDocumentService;
	@Autowired	NycIJRDocumentService nycIJRDocumentService;
	@Autowired	NDIDocumentService nDIDocumentService;
	@Autowired	EPRFOOCDocumentService ePRFOOCDocumentService;
	@Autowired  CLIMSDocumentService cLIMSDocumentService;
	@Autowired	LaftDetailsService laftDetailsService;
	@Autowired	CommonDocumentService commonDocumentService;
	@Autowired	CCDDocumentService ccdDocumentService;
	@Autowired	STDConfDocumentService stdConfDocumentService;
	@Autowired  ICCRDocumentService iccrDocumentService;
	@Autowired  LaftFollowupDocumentService laftFollowupDocumentService;
	@Autowired	NonCaseDocumentService nonCaseDocumentService;
	@Autowired	HIEDTHDocumentService HIEDTHDocumentService;
	@Autowired	BlackBoxDocService blackBoxDocService;
	@Autowired	FacilityService facilityService;
	@Autowired	private CommonExceptionHandler commonExceptionHandler;
	@Autowired	CommonService commonService;
	@Autowired QualCareDocumentService qualCareDocumentService;
	@Autowired CancerDocumentService cancerDocumentService;
	@Autowired NBSReviewService nbsReviewService;
	@Autowired ClusterPartnerReviewService clusterPartnerReviewService;
	@Autowired AssignmentService assignmentService;

	// Crate a dummy BindingResult for the JSON calls as they don't have that
	// object to pass to service layer
	// This done as all service layer depended on DAO accepts binding result and
	// json calls dont have it.
	BindingResult dummyBindingResult = BindingResults.newDummy();

	/**
	 * This is the common Validator for all methods in this controller. As we
	 * can have only one Validator per @Controller to use @Valid annotation (JSR
	 * 303), This is common Validator which checks for incoming object type and
	 * then call individual Validators. Check for DocumentValidator to see the
	 * navigation to other Individual Validators. By using this, each time we
	 * annotate bean with @Valid attribute, the @InitBinder Validator is called.
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new DocumentValidator());
	}

	/**
	 * This method returns the CASA document for a given docId.
	 *
	 * @param docId document ID of the document requested
	 * @param map   map of all the values
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCASADoc.html")
	public String getCASADocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		CasaDocData stdCASADocVO = casaDocumentService.getCASADoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdCASADocVO", stdCASADocVO);
		return "document/CASADocument";
	}
	
	/**
	 * This method returns the SYPH document (Syphilis) for a given docId.
	 *
	 * @param docId document ID of the document requested
	 * @param map   map of all the values
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSYPHDoc.html")
	public String getSYPHDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		SyphDocData stdSYPHDocVO = syphDocumentService.getSYPHDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdSYPHDocVO", stdSYPHDocVO);
		return "document/SYPHDocument";
	}
	
	/**
	 * This method returns the death document for a given docId.
	 *
	 * @param docId document ID of the document requested
	 * @param map   map of all the values
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDeathDoc.html")
	public String getDeathDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		DeathDocData stdDeathDocVO = deathDocumentSrvice.getDeathDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdDeathDocVO", stdDeathDocVO);
		return "document/DeathDocument";
	}

	/**
	 * This method returns the NYC death document for a given docId.
	 *
	 * @param docId   document ID of the document requested
	 * @param map     map of all the values
	 * @param request The HttpServletRequest object
	 * @return The identifier of the JSP for Spring to invoke
	 */
	@RequestMapping(value = "/getNycDeathDoc.html")
	public String getNycDeathDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		NycDeathDocData stdNycDeathDocVO = nycDeathDocumentService.getNycDeathDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdNycDeathDocVO", stdNycDeathDocVO);
		return "document/NycDeathDocument";
	}

	/**
	 * This method returns the EPRF_OOC document for a given docId.
	 *
	 * @param docId    document ID of the document requested
	 * @param map      map of all the values
	 * @param request  The HttpServletRequest object
	 * @return The identifier of the JSP for Spring to invoke
	 */
	@RequestMapping(value = "/getEPRFOOCDoc.html")
	public String getEPRFOOCDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		EPRFOOCDocVO stdEPRFOOCDocVO = ePRFOOCDocumentService.getEPRFOOCDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		List<JSONObject> codedSexAtBirthValues = commonDocumentService.getCodedSexAtBirthValues();
		map.put("codedSexAtBirthValues", codedSexAtBirthValues);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdEPRFOOCDocVO", stdEPRFOOCDocVO);
		return "document/EPRFOOCDocument";
	}

	/**
	 * This method will return the TB document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getTBDoc.html")
	public String getTBDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		TBDocVO tBDocVO = tbDocumentSrvice.getTBDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdTBDocVO", tBDocVO);
		return "document/TBDocument";
	}

	/**
	 * This method will return the NYC eHARS document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNYCEHARS.html")
	public String getNYCEHARSDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		NycEharsDocData nYCEHARSDocVO = commonDocumentService.getNycEHARSDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		
		
		List<Map<String, String>> hivCategories = commonDocumentService.getHIVCategories();
		
		List<Map<String, String>> aidsCategories = commonDocumentService.getAIDSCategories();
		
		map.put("hivCategories", hivCategories);
		map.put("aidsCategories", aidsCategories);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("StdNYCEHARSDocVO", nYCEHARSDocVO);
		return "document/NycEharsDocument";
	}

	/**
	 * This method will return the NYC Lab document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNYCLab.html")
	public String getNYCLabDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		NYCLabDocVO nYCLabDocVO = commonDocumentService.getNycLabDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("StdNYCLabDocVO", nYCLabDocVO);
		List<Map<String, String>> occupations = acrfFDocumentService.getCodedValues("OCCUPATION");
		map.put("occupations", occupations);
		List<Map<String, String>> subFacilities = acrfFDocumentService.getStandardFacilityDetails();
		map.put("subFacilities", subFacilities);
		if (CommonUtil.isNotNullAndEmpty(nYCLabDocVO.getFACILITY_UID())) {
			Map<String, String> facilityDetails = facilityService.getSubFacilityInfo(nYCLabDocVO.getFACILITY_UID(),
					null, userObject);
			map.put("facilityDetails", facilityDetails);
		}
		return "document/NycLabDocument";
	}

	/**
	 * This method will return the INS Death document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getInsDeathDoc.html")
	public String getInsDeathDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject) {

		InsDeathDocData insDeathDocVO = medicaidDocumentSrvice.getInsDthDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("StdInsDeathDocVO", insDeathDocVO);
		return "document/InsDthDocument";
	}

	/**
	 * This method will return the Tracking Address document for a given
	 * document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getTrkAddressDoc.html")
	public String getTrkAddressDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		TRKAddressDocVO trkAddressDocVO = trackingDocumentService.getTRKAddressDoc(docId, dummyBindingResult,
				userObject, request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdTrkAddressDocVO", trkAddressDocVO);
		return "document/TrackingAddressDocument";
	}

	/**
	 * This method will return the Tracking Address document for a given
	 * document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getTrkAliasDoc.html")
	public String getTrkAliasDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		TRKAliasDocVO trkAliasDocVO = trackingDocumentService.getTRKAliasDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdtrkAliasDocVO", trkAliasDocVO);
		return "document/TrackingAliasDocument";
	}

	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOldCasesDoc.html")
	public String getOldCasesDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		OldCaseDocData oldCaseDocVO = oldCaseDocumentService.getOldCaseDocVO(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdOldCaseDocVO", oldCaseDocVO);
		return "document/OldCaseDocument";
	}

	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMedicaidDoc.html")
	public String getMedicaidDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		MedicaidDocVO medicaidDocVO = medicaidDocumentSrvice.getMedicaidDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdMedicaidDocVO", medicaidDocVO);
		return "document/MedDocument";
	}

	@RequestMapping(value = "/getMedicaidOldDoc.html")
	public String getMedicaidOldDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		MedicaidOldDocData medicaidOldDocVO = medicaidOldDocumentSrvice.getMedicaidOldDoc(docId, dummyBindingResult,
				userObject, request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdMedicaidDocVO", medicaidOldDocVO);
		return "document/MedOldDocument";
	}

	/**
	 * This method returns Docs document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDoccsDoc.html")
	public String getDoccsDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		DoccsDocData stdDoccsDocVO = doccsDocumentService.getDoccsDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdDoccsDocVO", stdDoccsDocVO);
		return "document/DoccsDocument";
	}

	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDoccsDocForDinDoc.html")
	public String getDoccsDocForDinDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		DinDoccsDocVO dinDoccsDocVO = doccsDocumentService.getDinDoccsDocContent(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdDinDoccsDocVO", dinDoccsDocVO);
		return "document/DinDoccsDocument";
	}

	/**
	 * This method returns Lab doc for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLabDoc.html")
	public String getLabDocAsHTML(@RequestParam int docId, @RequestParam String documentType, Map<String, Object> map,
			HttpServletRequest request, UserObject userObject)
	{
		StdEcrlsLabDocVO stdLabDocVO = labDocumentService.getLabDoc(docId, documentType, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		
		Map<String, String> psMessageReasonDetails = clusterPartnerReviewService.getPsMessageDetails(docId, null, userObject);
		
		map.put("psMessageReasonDetails", psMessageReasonDetails);
		
		map.put("stdLabDocVO", stdLabDocVO);
		return "document/LabDocument";
	}

	/**
	 * 
	 * @param documentId
	 * @param documentType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDocumentData.json", method = RequestMethod.POST)
	public @ResponseBody JSONObject getDocumentData(@RequestParam String documentId, @RequestParam String documentType,
			HttpServletRequest request, UserObject userObject)
	{
		return commonService.getDocumentData(documentId, documentType, dummyBindingResult, userObject);
	}

	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getHivLabDoc.html")
	public String getHivLabDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		EcrlsLabDocVO stdLabDocVO = labDocumentService.getHivLabDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		List<String> std_units = commonDocumentService.getUnitsOfMeasurement();
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		String errorMessage = laftDetailsService.getErrorMessage(docId);
		if (CommonUtil.isNotNullAndEmpty(errorMessage)) {
			boolean hasProviderError = false;
			boolean hasFacilityError = false;
			if (errorMessage.contains("Provider address")) {
				hasProviderError = true;
			}
			if (errorMessage.contains("Facility address")) {
				hasFacilityError = true;
			}
			map.put("hasProviderError", hasProviderError);
			map.put("hasFacilityError", hasFacilityError);
		}
		
		String clusterId = request.getParameter("clusterId");
		
		Map<String, String> psMessageReasonDetails = clusterPartnerReviewService.getPsMessageDetails(docId, clusterId, userObject);
		
		map.put("psMessageReasonDetails", psMessageReasonDetails);
		
		map.put("stdLabDocVO", stdLabDocVO);
		map.put("unitsList", std_units);
		return "document/HivLabDocument";
	}

	/**
	 * This method is called for submitting edited Lab doc.
	 *
	 * @param stdLabDocVO
	 * @param result
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/editLabErrorDoc", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> editLabErrorDoc(
			@ModelAttribute("ecrlsLabDocVO") EcrlsLabDocVO ecrlsLabDocVO, HttpServletRequest request,
			Map<String, Object> map, @RequestParam String requestComments, UserObject userObject)
	{
		String retPage = "document/LabDocument";
		List<String> errors = new ArrayList<>();
		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult result = new MapBindingResult(targetMap, "targetMap");
		if (log.isDebugEnabled()) {
			log.debug("{} saving Labs Doc....", nyehmsLog);
			log.debug("{} {}",nyehmsLog,ecrlsLabDocVO.toString());
		}
		// if (result.hasErrors()) {
		// log.debug(nyehmsLog + " Result got eerror sending to JSP page....." +
		labDocumentService.editLabErrorDoc(ecrlsLabDocVO, requestComments, result, userObject,
				request.getSession().getId());
		return targetMap;
	}

	/**
	 * This document returns SSDM document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSSDMDoc.html")
	public String getSSDMDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		SsdmDocData stdSSDMDocVO = ssdmDocumentService.getSSDMDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdSSDMDocVO", stdSSDMDocVO);
		return "document/SSDMDocument";
	}

	/**
	 * This method returns ADAP doc for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getADAPDoc.html")
	public String getADAPDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		ADAPDocVO stdADAPDocVO = adapDocumentService.getADAPDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdADAPDocVO", stdADAPDocVO);
		return "document/ADAPDocument";
	}
	
	@RequestMapping(value = "/getADAPNYCDoc.html")
	public String getADAPNYCDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		ADAPDocVO stdADAPDocVO = adapDocumentService.getADAPNYCDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());

		map.put("stdADAPDocVO", stdADAPDocVO);
		return "document/ADAPDocument";
	}
	
	

	/**
	 * This method returns PRF Review doc.
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPRFReview.html")
	public String getPRFReview(HttpServletRequest request)
	{
		return "review/document/prfReview";
	}

	/**
	 * This method returns PRF Review doc for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPrfDoc.html")
	public String getPrfDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request)
	{
		// UserObject userObject = (UserObject)
		// request.getSession().getAttribute("UserObj");
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		PRFDocumentVO pRFDocumentVO = pRFDocumentService.getPRFDocument(docId);
		map.put("stdPRFDocVO", pRFDocumentVO);
	//	String sexAtBirth = pRFDocumentVO.getSEX_AT_BIRTH();
		List<JSONObject> arvList = commonDocumentService.getCodedArvs();
		List<JSONObject> hivFacilityTypes = commonDocumentService.getCodedHivFacilityTypes();
		List<JSONObject> partnerAssistanceStatusList = commonDocumentService.getPartnerAssistanceStatus();
		List<JSONObject> codedMaritalStatusList = commonDocumentService.getCodedMaritalStatus();
		List<JSONObject> codedFacilityTypeValues = commonDocumentService.getCodedFacilityTypeValues();
		map.put("codedMaritalStatusList", codedMaritalStatusList);
		map.put("arvList", arvList);
		map.put("hivFacilityTypes", hivFacilityTypes);
		map.put("partnerAssistanceStatusList", partnerAssistanceStatusList);
		map.put("codedFacilityTypeValues", codedFacilityTypeValues);
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		
		List<JSONObject> addressTypes = commonDocumentService.getAddressTypes();
		List<JSONObject> codedSexAtBirthValues = commonDocumentService.getCodedSexAtBirthValues();
		List<JSONObject> codedYesNoUnkownValues = commonDocumentService.getCodedYesNoUnkownValues();
		List<JSONObject> codedYesNoRefUnkownValues = commonDocumentService.getCodedYesNoRefUnkownValues();
		List<JSONObject> codedPrimaryLanguageValues = commonDocumentService.getCodedPrimaryLanguageValues();
		List<JSONObject> codedTelephoneTypeValues = commonDocumentService.getCodedTelephoneTypeValues();
		List<JSONObject> codedProfessionValues = commonDocumentService.getCodedProfessionValues();
		map.put("codedYesNoUnkownValues", codedYesNoUnkownValues);
		map.put("codedYesNoRefUnkownValues", codedYesNoRefUnkownValues);
		map.put("codedPrimaryLanguageValues", codedPrimaryLanguageValues);
		map.put("codedTelephoneTypeValues", codedTelephoneTypeValues);
		map.put("codedSexAtBirthValues", codedSexAtBirthValues);
		map.put("codedAddressTypes", addressTypes);
		map.put("codedProfessionValues", codedProfessionValues);
		
		String sexualOrientation = pRFDocumentVO.getSEXUAL_ORIENTATION();
		if (CommonUtil.isNotNullAndEmpty(sexualOrientation)) {
			List<JSONObject> codedSexualOrientationValues = commonDocumentService.getCodedSexualOrientationPrfValues();
			map.put("codedSexualOrientationValues", codedSexualOrientationValues);
			return "document/PrfDocument2023";
		} else {
			return "document/PrfDocument2016";
		}
		
	}

	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getRawAirsDoc.html")
	public String getRawAirsDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request)
	{
		AIRSDocVO aIRSDocVO = aIRSDocumentService.getRawAIRSDocument(docId);
		map.put("stdAIRSDocVO", aIRSDocVO);
		return "document/AIRSDocument";
	}

	@RequestMapping(value = "/getRawPrfDoc.html")
	public String getRawPrfDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request) 
	{
		// UserObject userObject = (UserObject)
		// request.getSession().getAttribute("UserObj");
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		PRFDocumentVO pRFDocumentVO = pRFDocumentService.getCompleteRawPRFDocumentContent(docId);
		map.put("stdPRFDocVO", pRFDocumentVO);
		List<JSONObject> arvList = commonDocumentService.getCodedArvs();
		List<JSONObject> hivFacilityTypes = commonDocumentService.getCodedHivFacilityTypes();
		List<JSONObject> partnerAssistanceStatusList = commonDocumentService.getPartnerAssistanceStatus();
		List<JSONObject> codedMaritalStatusList = commonDocumentService.getCodedMaritalStatus();
		List<JSONObject> codedFacilityTypeValues = commonDocumentService.getCodedFacilityTypeValues();
		
		map.put("codedMaritalStatusList", codedMaritalStatusList);
		map.put("hasEditFunctionality", true);
		map.put("arvList", arvList);
		map.put("hivFacilityTypes", hivFacilityTypes);
		map.put("partnerAssistanceStatusList", partnerAssistanceStatusList);
		map.put("codedFacilityTypeValues", codedFacilityTypeValues);
		
		List<JSONObject> addressTypes = commonDocumentService.getAddressTypes();
		List<JSONObject> codedSexAtBirthValues = commonDocumentService.getCodedSexAtBirthValues();
		List<JSONObject> codedYesNoUnkownValues = commonDocumentService.getCodedYesNoUnkownValues();
		List<JSONObject> codedYesNoRefUnkownValues = commonDocumentService.getCodedYesNoRefUnkownValues();
		List<JSONObject> codedPrimaryLanguageValues = commonDocumentService.getCodedPrimaryLanguageValues();
		List<JSONObject> codedTelephoneTypeValues = commonDocumentService.getCodedTelephoneTypeValues();
		List<JSONObject> codedProfessionValues = commonDocumentService.getCodedProfessionValues();
		map.put("codedYesNoUnkownValues", codedYesNoUnkownValues);
		map.put("codedYesNoRefUnkownValues", codedYesNoRefUnkownValues);
		map.put("codedPrimaryLanguageValues", codedPrimaryLanguageValues);
		map.put("codedTelephoneTypeValues", codedTelephoneTypeValues);
		map.put("codedSexAtBirthValues", codedSexAtBirthValues);
		map.put("codedAddressTypes", addressTypes);
		map.put("codedProfessionValues", codedProfessionValues);
		
		String sexualOrientation = pRFDocumentVO.getSEXUAL_ORIENTATION();
		if (CommonUtil.isNotNullAndEmpty(sexualOrientation)) {
			List<JSONObject> codedSexualOrientationValues = commonDocumentService.getCodedSexualOrientationPrfValues();
			map.put("codedSexualOrientationValues", codedSexualOrientationValues);
			codedTelephoneTypeValues.remove(2);
			map.put("codedTelephoneTypeValues", codedTelephoneTypeValues);
			return "document/PrfDocument2023";
		} else {
			return "document/PrfDocument2016";
		}
		
	}

	/**
	 * This method returns NYC PRF doc for a given docID.
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNycPrfDoc.html")
	public String getNycPrfDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		NycPrfDocData nycPRFDocVO = nycPRFDocumentService.getNycPRFDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		map.put("nycPRFDocVO", nycPRFDocVO);
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		
		List<JSONObject> codedYesNoUnkownValues = commonDocumentService.getCodedYesNoUnkownValues();
		map.put("codedYesNoUnkownValues", codedYesNoUnkownValues);
		List<JSONObject> codedYesNoRefUnkownValues = commonDocumentService.getCodedYesNoRefUnkownValues();
		map.put("codedYesNoRefUnkownValues", codedYesNoRefUnkownValues);
		List<JSONObject> codedSexualOrientationValues = commonDocumentService.getCodedSexualOrientationPrfValues();
		map.put("codedSexualOrientationValues", codedSexualOrientationValues);
		
		List<JSONObject> arvList = commonDocumentService.getCodedArvs();
		map.put("arvList", arvList);
		
		List<JSONObject> codedPrimaryLanguageValues = commonDocumentService.getCodedPrimaryLanguageValues();
		map.put("codedPrimaryLanguageValues", codedPrimaryLanguageValues);
		
		return "document/NycPrfDocument";
	}
	
	/**
	 * This method returns NYC PRF doc for a given docID.
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSTDConfDoc.html")
	public String getSTDConfDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		StdConfDocData stdConfDocVO = stdConfDocumentService.getSTDConfDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		map.put("stdConfDocVO", stdConfDocVO);
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		
		
		
		List<StdConfRefData> documentRefData = stdConfDocumentService.getSTDConfRefData();
		
		map.put("documentRefData", documentRefData);
		
		
		
		
		
		
		return "document/STDConfDocument";
	}
	
	
	
	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getStdOldDoc.html")
	public String getStdOldDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		StdOldDocData stdOldDocVO = labDocumentService.getStdOldDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		map.put("stdOldDocVO", stdOldDocVO);
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		return "document/StdOldDocument";
	}

	/**
	 * This method adds the partner object and send back the same form.
	 *
	 * Logic for adding partner: 1. Include empty partner object while user gets
	 * the PRF form using getPrfDoc.html. 2. This extra empty object will be
	 * used as empty form when user clicks add partner. 3. The present method
	 * /addPRFPartner will retain the user entered new partner information and
	 * populate new empty partner object if user wants to add 2nd new partner.
	 * 4. This process will continue until user submits the whole form for DB
	 * saving. 5. During the saving to DB, we remove the empty object created by
	 * this method (/addPRFPartner) or the initial getPrfDoc.html
	 *
	 * @param prfFormVO
	 * @param result
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/addPRFPartner", method = RequestMethod.POST)
	public String addPRFPartner(@ModelAttribute("prfFormVO") PrfFormVO prfFormVO, BindingResult result,
			HttpServletRequest request, Map<String, Object> map)
	{
		String retPage = "document/PrfDocument2023";
		LinkedList<PRFPartnerVO> stdPRFPartnerVOList = prfFormVO.getStdPRFPartnerVOList();
		// Add empty partner object for Add New Partner Functionality.
		PRFPartnerVO emptyStdPRFPartnerVO = new PRFPartnerVO();
		stdPRFPartnerVOList.add(emptyStdPRFPartnerVO);
		prfFormVO.setStdPRFPartnerVOList(stdPRFPartnerVOList);
		return retPage;
	}

	/**
	 * This method is called to delete PRF partner.
	 *
	 * @param prfFormVO
	 * @param result
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/deletePRFPartner", method = RequestMethod.POST)
	public String deletePRFPartner(@ModelAttribute("prfFormVO") PrfFormVO prfFormVO, BindingResult result,
			HttpServletRequest request, Map<String, Object> map)
	{
		String retPage = "document/PrfDocument2023";
		LinkedList<PRFPartnerVO> stdPRFPartnerVOList = prfFormVO.getStdPRFPartnerVOList();
		// Delete the object marked for delete
		for (int i = 0; i < stdPRFPartnerVOList.size(); i++) {
			if ("true".equalsIgnoreCase(stdPRFPartnerVOList.get(i).getTempDelete())) {
				log.debug("{} removing the object form list with first name : {}",nyehmsLog, stdPRFPartnerVOList.get(i).getFname());
				stdPRFPartnerVOList.remove(i);
			}
		}
		prfFormVO.setStdPRFPartnerVOList(stdPRFPartnerVOList);
		return retPage;
	}

	@RequestMapping(value = "/loadFacilityLabWidget.html")
	public String loadFacilityLabWidget(Map<String, Object> map, HttpServletRequest request)
	{
				
		List<Map<String, String>> labNames = acrfFDocumentService.getClinicalLaboratoryDetails();
		
		List<Map<String, String>> subFacilities = acrfFDocumentService.getStandardFacilityDetails();
		
		
		map.put("subFacilities", subFacilities);
		
		
		map.put("labNames", labNames);
		
		return "common/FacilityLabWidget";
	}
	

	/**
	 * This method returns ACRF doc for a given docID.
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getACRFForm.html")
	public String getACRFFormAsHTML(@RequestParam String docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		log.debug("{}  incoming ACRF Doc ID is {}",nyehmsLog, docId);
		List<Map<String, String>> subFacilities = acrfFDocumentService.getStandardFacilityDetails();
		List<Map<String, String>> manufacutrers = acrfFDocumentService.getCodedValues("MANUFACTURER");
		List<Map<String, String>> ethnicities = acrfFDocumentService.getCodedValues("ETHNICITY");
		List<Map<String, String>> stateCodes = acrfFDocumentService.getCodedValues("SITE_CD");
		List<Map<String, String>> occupations = acrfFDocumentService.getCodedValues("OCCUPATION");
		List<Map<String, String>> providerSpecialities = acrfFDocumentService.getCodedValues("SPECIALTY_CD");
		Map<String, String> codedCountries = commonDocumentService.getEharsCodedCountryValues();
		List<Map<String, String>> documentSourceList = acrfFDocumentService.getDocumentSourceList();
		
		
		
		
		
		
		List<Map<String, String>> acrfOtherIdList = acrfFDocumentService.getAcrfOtherIdTypeList();
		List<Map<String, String>> completeRegionsList = acrfFDocumentService.getCompleteRegionsList();
		List<JSONObject> codedArvsMedications = commonDocumentService.getCodedArvs();
		List<JSONObject> codedExpandedRaceList = commonDocumentService.getExpandedRaceList();
		
		String regionCode = request.getParameter("regionCode");
		
		if(CommonUtil.isNotNullAndEmpty(regionCode)) {
			Map<Integer, String> workers = assignmentService.getWorkerNamesByRegion(regionCode,
					dummyBindingResult, userObject, request.getSession()
							.getId());
			map.put("regionWorkers", workers);
		}
		
		
		AcrfFormVO acrfFormVO = null;
		
		String documentType = request.getParameter("documentType");
		
		// For the first time ACRF Form View
		if (docId.length() == 0) {
			// Get the Model Object for the first time JSP view.
			log.debug(nyehmsLog + "  First time ACERF Form being initialized...");
			acrfFormVO = processACRFForm.getFirstTimeAcrfVO(documentType, dummyBindingResult, userObject,
					request.getSession().getId());
			
			
			String stateNo = request.getParameter("stateNo");
			
			if(CommonUtil.isNotNullAndEmpty(stateNo) && CommonUtil.isNotNullAndEmpty(documentType) && ("A06.29".equals(documentType) || "A06.18".equals(documentType))){
				
				RawAcrfDocVO rawAcrfDoc = acrfFDocumentService.getAcrfFormDataFromAcrf(stateNo);
				
				if(rawAcrfDoc != null){
					acrfFormVO.setRawAcrfDoc(rawAcrfDoc);
				}
				
			}
			
			acrfFormVO.getRawAcrfDoc().setAcrfRevison(2023);
			
			// String regionCode = request.getParameter("regionCode");
		} else { // To fetch existing ACRF Form
			log.debug(nyehmsLog + "  ACRF Form VO is being fecthed from DB");
			acrfFormVO = acrfFDocumentService.getAcrfFormVO(docId, documentType, dummyBindingResult, userObject,
					request.getSession().getId());
			
			Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(Integer.parseInt(docId),
					userObject);
			map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
			
			// String regionCode =
			// acrfFormVO.getRawAcrfDoc().getReportingHealthDeptCityCounty();
		}
		List<String> arvUseTypeList = acrfFDocumentService.getArvUserTypeList();
		List<CodedArvMedicationsVO> arvMedicationsList = acrfFormVO.getArvMedicationsList();
		if (arvMedicationsList != null && arvMedicationsList.size() != 0) {
			for (CodedArvMedicationsVO codedArvMedicationsVO : arvMedicationsList) {
				arvUseTypeList.remove(codedArvMedicationsVO.getArvUseType());
			}
		}
		List<AcrfRidrVO> acrfRidrList = acrfFormVO.getAcrfRidrList();
		if (acrfRidrList == null || acrfRidrList.size() == 0) {
			AcrfRidrVO acrfRidrVO = new AcrfRidrVO();
			acrfRidrList = new ArrayList<AcrfRidrVO>();
			acrfRidrList.add(acrfRidrVO);
			acrfFormVO.setAcrfRidrList(acrfRidrList);
		}
		map.put("arvUseTypeList", arvUseTypeList);
		String accessLevel = request.getParameter("accessLevel");
		if (!"view".equals(accessLevel)) {
			acrfFormVO.getRawAcrfDoc().setIsOldAcrf("N");
		}
		map.put("acrfFormVO", acrfFormVO);
		map.put("documentSourceList", documentSourceList);
		map.put("subFacilities", subFacilities);
		map.put("manufacutrers", manufacutrers);
		map.put("ethnicities", ethnicities);
		map.put("occupations", occupations);
		map.put("acrfOtherIdList", acrfOtherIdList);
		map.put("completeRegionsList", completeRegionsList);
		map.put("codedArvsMedications", codedArvsMedications);
		map.put("codedExpandedRaceList", codedExpandedRaceList);
		map.put("providerSpecialities", providerSpecialities);
		map.put("codedCountries", codedCountries);
		map.put("stateCodes", stateCodes);
		
		map.put("acrfRevison", acrfFormVO.getRawAcrfDoc().getAcrfRevison());
		
		//map.put("diagnosticAlgorithmRoles", diagnosticAlgorithmList);
		
		if("PCRF".equals(documentType) || "NBS_PCRF".equals(documentType)){
			List<Map<String, String>> birthDefectTypeList = acrfFDocumentService.getACRFReferenceData("BIRTH_DEFECTS_TYPES");
			map.put("birthDefectTypeList", birthDefectTypeList);
						
			List<PcrfSubstanceDocVO> screenSubstance = acrfFDocumentService.getScreenSubstanceData(docId);
			acrfFormVO.getRawAcrfDoc().setScreenSubstanceList(screenSubstance);
			List<PcrfSubstanceDocVO> bpUseSubstanceList = acrfFDocumentService.getBpUseSubstanceList(docId);
			acrfFormVO.getRawAcrfDoc().setBpUseSubstanceList(bpUseSubstanceList);
			List<PcrfSubstanceDocVO> bpScreenSubstanceList = acrfFDocumentService.getBpScreenSubstanceList(docId);
			acrfFormVO.getRawAcrfDoc().setBpScreenSubstanceList(bpScreenSubstanceList);
			
			
			List<PcrfSubstanceDocVO> bpScreenConditionsList = acrfFDocumentService.getBpScreenConditionsList(docId);
			acrfFormVO.getRawAcrfDoc().setBpScreenConditionsList(bpScreenConditionsList);
			List<PcrfSubstanceDocVO> bpDxScreenConditionsList = acrfFDocumentService.getBpDxScreenConditionsList(docId);
			acrfFormVO.getRawAcrfDoc().setBpDxScreenConditionsList(bpDxScreenConditionsList);
			
			List<PcrfSubstanceDocVO> bpPregOutcomeList = acrfFDocumentService.getBpPregOutcomeList(docId);
			acrfFormVO.getRawAcrfDoc().setBpPregOutcomeList(bpPregOutcomeList);
			
			if(acrfFormVO.getRawAcrfDoc().getAcrfRevison() > 2022) {
				return "worker/PCRFForm23";
			}else {
				return "worker/PCRFForm";
			}
			
			
		}else{
			return "worker/ACRFForm";
		}
		
	}

	/**
	 * This method returns ACRF Lab test results as JSON
	 *
	 * @param pParentTestType
	 * @param pAcrfTestCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAcrfLabTsts.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonObject getAcrfLabTestResultsAsJson(@RequestParam String pParentTestType,
			@RequestParam String pAcrfTestCode, HttpServletRequest request, UserObject userObject)
	{
		JsonObject jsonResponse = getGridObject.GetJsonObject(acrfFDocumentService.getCodedAcrfResultTypes(
				pParentTestType, pAcrfTestCode, "yes", 0, dummyBindingResult, userObject, request.getSession().getId()));
		return jsonResponse;
	}

	/**
	 * This method is called to add new ACRF form.
	 *
	 * @param acrfFormVO
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/addACRFForm", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> addACRFForm(@ModelAttribute("acrfFormVO") AcrfFormVO acrfFormVO,
			HttpServletRequest request, Map<String, Object> map, @RequestParam boolean havingNoErrorsinForm,
			@RequestParam String actionName,
			@RequestParam(required = false) String closureCategory, UserObject userObject)
	{
		Integer statusCode = 0;
		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		String documentType = request.getParameter("documentType");
		
		if(CommonUtil.isNullOrEmpty(documentType)){
			documentType = "ACRF";
		}
		
		String transactionMessage = "Successfully saved the "+documentType+" Form";
		
		
		// Populate the Map objects for the LAB Tests.
		processACRFForm.populateLabTestInAcrfVO(documentType, acrfFormVO, bindingResult, userObject, request.getSession().getId());
		// Populate the Map objects for LAB Results for selected Lab Tests.
		processACRFForm.preserveLabResults(acrfFormVO, bindingResult, userObject, request.getSession().getId());
		boolean hasLabDataChange = false;
		// Validate the Form
		if (bindingResult.hasErrors()) {
			statusCode = -1;
			
			log.error("CRF form has errors while saving the form  : {}", bindingResult.getAllErrors());
			log.error(nyehmsLog + "Error occured while trying to save the ACRF form page..... {}",documentType );
			
			transactionMessage = "Failed to save the "+documentType+" form, please contact helpline";
		} else {
			String assignmentId = request.getParameter("assignmentId");
			hasLabDataChange = acrfFDocumentService.addACRFDoc(acrfFormVO, documentType, actionName, havingNoErrorsinForm,
					assignmentId, bindingResult, userObject, request.getSession().getId(), closureCategory);
		}
		if (bindingResult.hasErrors()) {
			statusCode = -1;
			log.error("CRF form has errors while saving the form  : {}", bindingResult.getAllErrors());
			log.error(nyehmsLog + "Error occured while trying to save the CRF form page.....{}",documentType );
			transactionMessage = "Failed to save the "+documentType+" form, please contact helpline";
		} else {
			if (!havingNoErrorsinForm) {
				transactionMessage = documentType+" form has Errors, but saved Successfully.";
			}
			targetMap.put("DOCUMENT_ID", acrfFormVO.getRawAcrfDoc().getDocumentId());
			targetMap.put("DATE_COMPLETED", acrfFormVO.getRawAcrfDoc().getDateCompleted());
			targetMap.put("HAS_LAB_DATA_CHANGE", hasLabDataChange);
		}
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		return targetMap;
	}

	/**
	 * This method is called to discard ACRF form
	 *
	 * @param documentId
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/discardACRFForm.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> discardACRFForm(@RequestParam String assignmentId,
			@RequestParam String acrfDocId, @RequestParam String reabstract, HttpServletRequest request, Map<String, Object> map, UserObject userObject)
	{
		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		String documentType = request.getParameter("documentType");
		
		acrfFDocumentService.discardACRFForm(acrfDocId, documentType, assignmentId, reabstract, bindingResult, userObject,
				userObject.getSessionId());
		return targetMap;
	}
	
	/**
	 * This method is called to save ACRF Form/ update existing record.
	 *
	 * @param acrfFormVO
	 * @param acrf
	 * @param result
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/saveACRFForm", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> saveACRFForm(@ModelAttribute("acrfFormVO") AcrfFormVO acrfFormVO,
			BindingResult result, HttpServletRequest request, Map<String, Object> map,
			@RequestParam boolean havingNoErrorsinForm, @RequestParam String actionName, @RequestParam(required = false) String closureCategory, UserObject userObject)
	{
		Integer statusCode = 0;
		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		log.debug(nyehmsLog + "  Updating ACRF VO with Doc ID {}", acrfFormVO.getRawAcrfDoc().getAcrfSrcId());
		String retPage = "worker/ACRFForm";
		
		String documentType = request.getParameter("documentType");
		
		if(CommonUtil.isNullOrEmpty(documentType)){
			documentType = "ACRF";
		}
		
		String transactionMessage = "Successfully saved the "+documentType+" Form";
		
		
		boolean hasLabDataChange = false;
		if (result.hasErrors()) {
			statusCode = -1;
			log.error("Input CRF form has errors  : {}", result.getAllErrors());
			transactionMessage = "Failed to save the "+documentType+" form, please contact helpline";
		} else {
			hasLabDataChange = acrfFDocumentService.updateACRFDoc(acrfFormVO, documentType, actionName, havingNoErrorsinForm, result,
					userObject, request.getSession().getId(), closureCategory);
		}
		log.debug(nyehmsLog + "  After Update call -- ");
		// Populate the Map objects for the LAB Tests.
		processACRFForm.populateLabTestInAcrfVO(documentType, acrfFormVO, result, userObject, request.getSession().getId());
		// Populate the Map objects for LAB Results for selected Lab Tests.
		processACRFForm.preserveLabResults(acrfFormVO, result, userObject, request.getSession().getId());
		if (result.hasErrors()) {
			log.error("CRF form has errors while saving the form  : {}", result.getAllErrors());
			log.error(nyehmsLog + "Error occured while trying to save the CRF form page..... {}",retPage);
			statusCode = -1;
			transactionMessage = "Failed to save the "+documentType+" form, please contact helpline";
		} else {
			if (!havingNoErrorsinForm) {
				transactionMessage = documentType+" form has Errors, but saved Successfully.";
			}
			targetMap.put("DATE_COMPLETED", acrfFormVO.getRawAcrfDoc().getDateCompleted());
			targetMap.put("HAS_LAB_DATA_CHANGE", hasLabDataChange);
		}
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		return targetMap;
	}

	/**
	 * This method will return the AIRS document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAIRSForm.html")
	public String getAIRSDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		AirsDocData aIRSDocVO = aIRSDocumentService.getAIRSDocument(docId);
		map.put("stdAIRSDocVO", aIRSDocVO);
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		return "document/AIRSDocument";
	}
	
	/**
	 * This method will return the CLIMS document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCLIMSForm.html")
	public String getCLIMSDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		ClimsDocData cLIMSDocVO = cLIMSDocumentService.getCLIMSDocument(docId);
		map.put("stdCLIMSDocVO", cLIMSDocVO);
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		return "document/CLIMSDocument";
	}	

	/**
	 * This method will return the NYC-IJR document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNYCIJRForm.html")
	public String getNYCIJRDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		NycIJRDocVO nycIJRDocVO = nycIJRDocumentService.getNYCIJRDocument(docId);
		map.put("stdNYCIJRDocVO", nycIJRDocVO);
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		return "document/NycIJRDocument";
	}

	/**
	 * This method will return the ICCR document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getICCRForm.html")
	public String getICCRDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		IccrDocData iccrDocVo = iccrDocumentService.getICCRDocument(docId);		
		map.put("stdICCRDocVO", iccrDocVo);
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		return "document/ICCRDocument";
	}

	/**
	 * This method will return the NDI document for a given document ID
	 *
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNDIDoc.html")
	public String getNDIDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		if (userObject == null) {
			userObject = new UserObject();
		}
		NdiDocData nDIDocumentVO = nDIDocumentService.getNDIDoc(docId);
		if (nDIDocumentVO != null) {
			documentTransactionHelper.getStatenoDetailsOfDocument(docId, userObject);
		}
		map.put("stdNDIDocVO", nDIDocumentVO);
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		return "document/NDIDocument";
	}

	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNDICauseDoc.html")
	public String getNDICasueDocAsHTML(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		if (userObject == null) {
			userObject = new UserObject();
		}
		NdiCauseDocData nDICauseDocVO = nDIDocumentService.getNDICauseDoc(docId);
		if (nDICauseDocVO != null) {
			Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
					userObject);
			map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		}
		map.put("stdNDICauseDocVO", nDICauseDocVO);
		return "document/NDICauseDocument";
	}
	
	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBlackBoxDoc.html")
	public String getBlackBoxDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		if (userObject == null) {
			userObject = new UserObject();
		}
		BlackBoxDocData blackBoxDocVO = blackBoxDocService.getBlackBoxDoc(docId);
		if (blackBoxDocVO != null) {
			Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
					userObject);
			map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		}
		map.put("stdBlackBoxDocVO", blackBoxDocVO);
		
		List<BlackBoxRefData> documentRefData = blackBoxDocService.getBlackBoxRefData();
		
		map.put("documentRefData", documentRefData);
		
		return "document/BlackBoxDocument";
	}
	
	
	
	/**
	 * 
	 * @param documentId
	 * @param documentType
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getClericalDocumentAsPerson.html")
	public String getClericalDocumentAsPerson(@RequestParam int documentId, @RequestParam String documentType,
			Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		if (userObject == null) {
			userObject = new UserObject();
		}
		
		if("NBS".equals(documentType)) {
			
			Map<Object, Object> targetMap= new HashMap<>();
			BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
			
			NBSDocVO nbsDocVO = nbsReviewService.getNBSDoc(documentId, "STD_REVIEW", bindingResult, userObject);
			map.put("stdNBSDocVO", nbsDocVO);
			return "document/NBSDocument";
		}else if("CLIMS".equals(documentType)) {
			
			ClimsDocData climsDocVO = cLIMSDocumentService.getCLIMSDocument(documentId);;
			map.put("stdCLIMSDocVO", climsDocVO);
			return "document/CLIMSDocument";
		}
		
		PersonViewVO nYEHMSDocVO = null;
		
		if("CANCER".equals(documentType)) {
			nYEHMSDocVO = cancerDocumentService.getClericalDocumentAsPerson(documentId);
		}
		else if("dataflux".equals(request.getParameter("page"))){
			nYEHMSDocVO = commonDocumentService.getClericalDocumentAsPerson(documentId, documentType);
			
			Map<String, Object> statenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(documentId,
					userObject);
			
			if(statenoDetails != null){
			
				if(statenoDetails.get("NYC_CITYNO") != null){
					nYEHMSDocVO.setNYC_CITYNO((String)statenoDetails.get("NYC_CITYNO"));
				}
				
				if(statenoDetails.get("NYC_CITYNO_LIST") != null){
					nYEHMSDocVO.setNYC_CITYNO_LIST((List<String>)statenoDetails.get("NYC_CITYNO_LIST"));
				}
				if(statenoDetails.get("NYC_EHARS_STATENO") != null){
					nYEHMSDocVO.setNYC_EHARS_STATENO((String)statenoDetails.get("NYC_EHARS_STATENO"));
				}
				if(statenoDetails.get("NYC_EHARS_STATENO_LIST") != null){
					nYEHMSDocVO.setNYC_EHARS_STATENO_LIST((List<String>)statenoDetails.get("NYC_EHARS_STATENO_LIST"));
				}
				if(statenoDetails.get("EHARS_STATENO") != null){
					nYEHMSDocVO.setEHARS_STATENO((String)statenoDetails.get("EHARS_STATENO"));
				}
				if(statenoDetails.get("EHARS_STATENO_LIST") != null){
					nYEHMSDocVO.setEHARS_STATENO_LIST((List<String>)statenoDetails.get("EHARS_STATENO_LIST"));
				}

				if(statenoDetails.get("NYEHMS_STATENO") != null){
					nYEHMSDocVO.setStateNo((String)statenoDetails.get("NYEHMS_STATENO"));
				}
			}
			
		}
		PersonViewVO personViewVo = new PersonViewVO();
		BeanUtils.copyProperties(nYEHMSDocVO, personViewVo);
		PersonRaceVO personRaceVO = new PersonRaceVO();
		BeanUtils.copyProperties(nYEHMSDocVO, personRaceVO);
		personViewVo.setPersonRaceVO(personRaceVO);
		map.put("personViewObj", personViewVo);
		// personViewVo.setStateNo("");
		return "person/PersonView";
	}

	/**
	 * 
	 * @param partnerIndex
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPrfPartner.html", method = RequestMethod.POST, produces = "text/html")
	public String getPrfPartner(@RequestParam(required = false) int partnerIndex, Map<String, Object> map,
			HttpServletRequest request)
	{
		map.put("partnerIndex", partnerIndex);
		PRFPartnerVO partner = new PRFPartnerVO();
		List<PRFPartnerVO> partnersList = new ArrayList<>();
		partnersList.add(partner);
		PRFDocumentVO prfDocumentVO = new PRFDocumentVO();
		prfDocumentVO.setPartnerList(partnersList);
		List<JSONObject> codedMaritalStatusList = commonDocumentService.getCodedMaritalStatus();
		map.put("codedMaritalStatusList", codedMaritalStatusList);
		List<JSONObject> partnerAssistanceStatusList = commonDocumentService.getPartnerAssistanceStatus();
		map.put("partnerAssistanceStatusList", partnerAssistanceStatusList);
		List<JSONObject> codedTelephoneTypeValues = commonDocumentService.getCodedTelephoneTypeValues();
		map.put("codedTelephoneTypeValues", codedTelephoneTypeValues);
		List<JSONObject> codedYesNoUnkownValues = commonDocumentService.getCodedYesNoUnkownValues();
		map.put("codedYesNoUnkownValues", codedYesNoUnkownValues);
		map.put("stdPRFDocVO", prfDocumentVO);
		return "document/prfPartnerInitalize";
	}

	/**
	 * 
	 * @param docId
	 * @param documentType
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPSReturnMessageDetails.html", method = RequestMethod.POST, produces = "text/html")
	public String getPSReturnMessageDetails(@RequestParam Integer docId, @RequestParam String documentType,
			Map<String, Object> map, HttpServletRequest request)
	{
		List<PSMessageVO> psMessageList = commonDocumentService.getPSMessageDetails(docId, documentType);
		map.put("psMessageList", psMessageList);
		List<JSONObject> dispositionCodeValues = commonDocumentService.getDispositionCodeValues(false);
		map.put("dispositionCodeValues", dispositionCodeValues);
		
		
		List<Map<String, String>> diagnosisLocations = commonDocumentService.getPSMessageReferenceData("DIAGNOSISLOCATION");
		
		map.put("codedSexAtBirthValues", diagnosisLocations);
		
		List<Map<String, String>> currentPrepList = commonDocumentService.getPSMessageReferenceData("CURRENTONPREP");
		
		map.put("currentPrepList", currentPrepList);
		
		// need to fetch list of reasons and send it to returnMessageDetails
		List<JSONObject> laftReasonsList = commonDocumentService.getLAFTReasonslist();
		
		map.put("laftReasonsList", laftReasonsList);
		
		return "document/returnMessageDetails";
	}
	
	
	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNonCaseDoc.html")
	public String getNonCaseDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		if (userObject == null) {
			userObject = new UserObject();
		}
		
		NonCaseDocVO nonCaseDocVO = nonCaseDocumentService.getNonCaseDoc(docId);
		
		if (nonCaseDocVO != null) {
			Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
					userObject);
			map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		}
		map.put("nonCaseDocVO", nonCaseDocVO);
		return "document/NonCaseDocument";
	}
	
	@RequestMapping(value = "/loadAidaData.html")
	public String loadAidaData(@RequestParam String tc_id, @RequestParam String parameterName, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		if (userObject == null) {
			userObject = new UserObject();
		}
		
		List<Map<String, String>> aidaData = aIRSDocumentService.loadAirsAIDAData(tc_id, parameterName, userObject);
		
		String dataHeader = EnvironmentProperties.envProperties.getProperty(parameterName+"_HEADERS");
		
		
		map.put("aidaData", aidaData);
		map.put("headers", dataHeader.split(","));
		
		//map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		if("ARV".equals(parameterName)) {
			return "document/AIDAData";
		}else {
			return "document/loadAIDATabData";
		}
	}
	
	@RequestMapping(value = "/getLAFTFollowupDetails.html", method = RequestMethod.POST, produces = "text/html")
	public String getLAFTFollowupDetails(@RequestParam Integer docId, @RequestParam String documentType,
			Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		if (userObject == null) {
			userObject = new UserObject();
		}
		
		List<LaftFollowupDetailsVO> laftFollowupList = laftFollowupDocumentService.getLAFTFollowupDetails(docId, userObject);
		
		map.put("laftFollowupList", laftFollowupList);
		
		return "document/LAFTFollowUpDetails";
	}
		
	
	@RequestMapping(value = "/getQualCareDoc.html", method = RequestMethod.POST, produces = "text/html")
	public String getQualCareDoc(@RequestParam Integer docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		if (userObject == null) {
			userObject = new UserObject();
		}
		
		QualCareDocData qualCareDocVO = qualCareDocumentService.getQualCareDocument(docId);
		
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId, userObject);
		
		map.put("qualCareDocVO", qualCareDocVO);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		
		return "document/QualCareDocument";
	}
	
	@RequestMapping(value = "/getDocDelDiscardHistory.html")
	public String getDocDelDiscardHistory(@RequestParam int documentId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		Map<String, String> docDelDiscardHistory = nonCaseDocumentService.getDocDelDiscardHistory(documentId,  bindingResult,  userObject);
		
		map.put("docDelDiscardHistory", docDelDiscardHistory);
		
		return "review/DocumentDelDiscardHistory";
	}	
}
