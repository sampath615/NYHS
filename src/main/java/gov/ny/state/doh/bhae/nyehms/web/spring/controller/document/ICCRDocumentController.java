package gov.ny.state.doh.bhae.nyehms.web.spring.controller.document;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrTest;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AcrfTestResult;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ICCRDocumentService;

@RequiredArgsConstructor
@Controller
public class ICCRDocumentController {
	

	
	private final CommonDocumentService commonDocumentService;
	private final ICCRDocumentService iccrDocumentService;
	private final ACRFDocumentService acrfFDocumentService;
	
	@RequestMapping("/dataEntryICCRDocument")
	public String dataEntryICCRDoc(HttpServletRequest request, Map<String, Object> map, UserObject userObject) {
		
		HttpSession session = request.getSession();
		BindingResult bindingResult = new MapBindingResult(map, "targetMap");		
		
		map.put("iccrDataEntryVO", RawIccrDoc.builder().iccrTestList(List.of(RawIccrTest.builder().build())).build());   // an empty form with one test row
		
		List<JSONObject> codedYesNoUnkownValues = commonDocumentService.getCodedYesNoUnkownValues();
		List<CodedAcrfTestVO> codedAcrfTestList = acrfFDocumentService.getCodedAcrfTest(bindingResult, userObject, session.getId());
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		map.put("codedYesNoUnkownValues", codedYesNoUnkownValues);
		map.put("codedAcrfTestList", codedAcrfTestList);
		
		return "dataentry/ICCRDataEntryDoc";
	}
	
	/**
	 * Saves the posted data entry form. A date that is not a valid date is a binding error in {@code result} and
	 * stays empty; the form is saved anyway, as before (the page validates the dates).
	 */
	@RequestMapping(value="/saveICCRDataForm", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> saveICCRDataForm(
			@ModelAttribute("ICCRDataEntryVO") RawIccrDoc iccrDataEntryForm, BindingResult result, HttpServletRequest request, UserObject userObject) {
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		iccrDocumentService.saveICCRDataEntryForm(iccrDataEntryForm, bindingResult, userObject);
		
		return targetMap;
	}	
	
	@RequestMapping(value = "/getICCRTestResult.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<AcrfTestResult> getICCRTestResult(@RequestParam String testType, HttpServletRequest request) {

		List<AcrfTestResult> codedAcrfTestResults= iccrDocumentService.getCodedTestTypeResult(testType);
		
		return codedAcrfTestResults;
	}		

}
