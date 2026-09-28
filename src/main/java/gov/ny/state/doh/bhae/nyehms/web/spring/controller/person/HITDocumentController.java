package gov.ny.state.doh.bhae.nyehms.web.spring.controller.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDEncounterVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDMedVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPayerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProblemVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp.StdHIEDTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7AddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7INSVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CCDDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HIEDTHDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HL7DocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.validation.document.DocumentValidator;

@Controller
public class HITDocumentController {



	@Autowired
	HL7DocumentService hl7DocumentService;
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;
	@Autowired
	CCDDocumentService ccdDocumentService;
	@Autowired
	HIEDTHDocumentService HIEDTHDocumentService;
	@Autowired
	GetGridObject getGridObject;
	BindingResult dummyBindingResult = BindingResults.newDummy();

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new DocumentValidator());
	}

	/* CCD Document Viewer Section Starts */
	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCCDDoc.html")
	public String getCCDDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		RawCCDDocVO rawCCDDocVO = ccdDocumentService.getCCDDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdRawCCDDocVO", rawCCDDocVO);
		return "document/CCDDocument";
	}

	@RequestMapping(value = "/getCCDAddressList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RawCCDPersonAddressVO> getCCDAddressList(@RequestParam int documentID,
			HttpServletRequest request) {
		return ccdDocumentService.getCCDAddressList(documentID);
	}

	@RequestMapping(value = "/getCCDProvidersList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RawCCDProviderVO> getCCDProvidersList(@RequestParam int documentID,
			HttpServletRequest request) {
		return ccdDocumentService.getCCDProvidersList(documentID);
	}

	@RequestMapping(value = "/getCCDProblemsList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RawCCDProblemVO> getCCDProblemsList(@RequestParam int documentID,
			HttpServletRequest request) {
		return ccdDocumentService.getCCDProblemsList(documentID);
	}

	@RequestMapping(value = "/getCCDEncountersList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getCCDEncountersList(@RequestParam int documentID, HttpServletRequest request) {
		List<RawCCDEncounterVO> lstRawCCDEncounterVOVO = ccdDocumentService.getCCDEncountersList(documentID);
		GridObject jsonResponse = getGridObject.GetGrid(lstRawCCDEncounterVOVO);
		return jsonResponse;
	}

	@RequestMapping(value = "/getCCDLabTestsList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getCCDLabTestsList(@RequestParam int documentID, HttpServletRequest request) {
		List<RawCCDLabTestVO> lstRawCCDLabTestVO = ccdDocumentService.getCCDLabTestsList(documentID);
		GridObject jsonResponse = getGridObject.GetGrid(lstRawCCDLabTestVO);
		return jsonResponse; 
	}

	@RequestMapping(value = "/getCCDMedicationsList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RawCCDMedVO> getCCDMedicationsList(@RequestParam int documentID,
			HttpServletRequest request) {
		return ccdDocumentService.getCCDMedicationsList(documentID);
	}

	@RequestMapping(value = "/getCCDPayersList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RawCCDPayerVO> getCCDPayersList(@RequestParam int documentID,
			HttpServletRequest request) {
		return ccdDocumentService.getCCDPayersList(documentID);
	}

	/* CCD Document Viewer Section Ends */

	/* EXP Document Viewer Section Starts */

	/**
	 * 
	 * @param docId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getEXPDoc.html")
	public String getHIEDTHDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		StdHIEDTHDocVO stdExpDocVO = HIEDTHDocumentService.getHIEDTHDoc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdRawEXPDocVO", stdExpDocVO);
		return "document/HIEDTHDocument";
	}

	/* EXP Document Viewer Section Ends */

	// DocId in the below method is CCD_DOCUMENT_ID since we are getting data based
	// on CCD_DOCUMENT_ID
	/* The Below section is for HL7 Document Viewer : Start */
	@RequestMapping(value = "/getHL7Doc.html")
	public String getHL7Doc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		RawHL7DocVO rawHL7DocVO = hl7DocumentService.getHL7Doc(docId, dummyBindingResult, userObject,
				request.getSession().getId());
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfDocument(docId,
				userObject);
		rawHL7DocVO.setCcdMatchDocId(docId);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);
		map.put("stdRawHL7DocVO", rawHL7DocVO);
		return "document/HL7Document";
	}

	@RequestMapping(value = "/getFilteredHL7Doc.html")
	public String getFilteredHL7Doc(@RequestParam int documentID, Map<String, Object> map,
			@RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request, UserObject userObject) {
		RawHL7DocVO rawHL7DocVO = new RawHL7DocVO();
		rawHL7DocVO = hl7DocumentService.getFilteredHL7Doc(documentID, startDate, endDate);
		rawHL7DocVO.setCcdMatchDocId(documentID);
		map.put("stdRawHL7DocVO", rawHL7DocVO);
		return "document/HL7Demographics";
	}

	@RequestMapping(value = "/getHL7AddressList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RawHL7AddressVO> getHL7AddressList(@RequestParam int documentID,
			HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		return hl7DocumentService.getHL7AddressList(documentID, startDate, endDate);
	}

	@RequestMapping(value = "/getHL7InsuranceList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RawHL7INSVO> getHL7InsuranceList(@RequestParam int documentID,
			HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		return hl7DocumentService.getHL7InsuranceList(documentID, startDate, endDate);
	}

	@RequestMapping(value = "/getHL7Diagnosis.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<Object, Object>> getHL7Diagnosis(@RequestParam int documentID,
			@RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request) {
		return hl7DocumentService.getHL7Diagnosis(documentID, startDate, endDate);
	}

	@RequestMapping(value = "/getHL7Medication.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<Object, Object>> getHL7Medication(@RequestParam int documentID,
			@RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request) {
		return hl7DocumentService.getHL7Medication(documentID, startDate, endDate);
	}

	@RequestMapping(value = "/getHL7Encounter.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<Object, Object>> getHL7Encounter(@RequestParam int documentID,
			@RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request) {
		return hl7DocumentService.getHL7Encounter(documentID, startDate, endDate);
	}

	@RequestMapping(value = "/getHL7LabData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getHL7LabData(@RequestParam int documentID, @RequestParam String startDate,
			@RequestParam String endDate, HttpServletRequest request, UserObject userObject) {
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		List<RawHL7ObrVO> lstRawHL7ObrVO = hl7DocumentService.getHL7LabData(documentID, startDate, endDate);
		GridObject jsonResponse = getGridObject.GetGrid(lstRawHL7ObrVO);
		return jsonResponse;
	}

	@RequestMapping(value = "/getHL7DemogrphicFieldMultiValues.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getFieldValsWithDistinct(@RequestParam int documentID,
			@RequestParam String startDate, @RequestParam String endDate, @RequestParam String colsName,
			HttpServletRequest request, UserObject userObject) {
		if (userObject == null)
			userObject = new UserObject();
		GridObject jsonResponse = getGridObject
				.GetGrid(hl7DocumentService.getHL7DemogrphicFieldMultiValues(documentID, startDate, endDate, colsName));
		return jsonResponse;
	}
	/* The Above section is for HL7 Document Viewer : END */

}