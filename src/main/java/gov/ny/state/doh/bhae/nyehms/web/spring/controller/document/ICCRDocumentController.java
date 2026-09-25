package gov.ny.state.doh.bhae.nyehms.web.spring.controller.document;

import java.util.ArrayList;
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

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRDataEntryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ICCRDocumentService;

@Controller
public class ICCRDocumentController {
	

	
	@Autowired	CommonDocumentService commonDocumentService;
	@Autowired	ICCRDocumentService iccrDocumentService;
	@Autowired	ACRFDocumentService acrfFDocumentService;
	
	@RequestMapping("/dataEntryICCRDocument")
	public String dataEntryICCRDoc(HttpServletRequest request, Map<String, Object> map, UserObject userObject) {
		
		HttpSession session = request.getSession();
		BindingResult bindingResult = new MapBindingResult(map, "targetMap");		
		
		ICCRDataEntryVO iccrDataEntryVO = new ICCRDataEntryVO();
		
		List<ICCRTestVO> iccrTestList = new ArrayList<ICCRTestVO>();
		iccrTestList.add(new ICCRTestVO());
		
		iccrDataEntryVO.setIccrTestList(iccrTestList);
		
		map.put("iccrDataEntryVO", iccrDataEntryVO);
		
		List<JSONObject> codedYesNoUnkownValues = commonDocumentService.getCodedYesNoUnkownValues();
		List<CodedAcrfTestVO> codedAcrfTestList = acrfFDocumentService.getCodedAcrfTest(bindingResult, userObject, session.getId());
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		map.put("codedYesNoUnkownValues", codedYesNoUnkownValues);
		map.put("codedAcrfTestList", codedAcrfTestList);
		
		return "dataentry/ICCRDataEntryDoc";
	}
	
	@RequestMapping(value="/saveICCRDataForm", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> saveICCRDataForm(
			@ModelAttribute("ICCRDataEntryVO") ICCRDataEntryVO iccrDataEntryVO, HttpServletRequest request, BindingResult result, UserObject userObject) {
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		iccrDocumentService.saveICCRDataEntryForm(iccrDataEntryVO, bindingResult, userObject);
		
		return targetMap;
	}	
	
	@RequestMapping(value = "/getICCRTestResult.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getICCRTestResult(@RequestParam String testType, HttpServletRequest request) {

		List<JSONObject> codedAcrfTestResults= iccrDocumentService.getCodedTestTypeResult(testType);
		
		return codedAcrfTestResults;
	}		

}
