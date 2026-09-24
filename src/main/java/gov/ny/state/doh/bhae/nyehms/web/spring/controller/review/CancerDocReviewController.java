package gov.ny.state.doh.bhae.nyehms.web.spring.controller.review;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CancerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CancerDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
/*
 * Spring controller class for system message
 */
@Controller
public class CancerDocReviewController {
	/*
	 * Reference to GetGridObject
	 */
	
	@Autowired
	CancerDocumentService cancerDocumentService;
	
	
	
	@RequestMapping(value = "/getCancerDoc.html")
	public String getCancerDoc(@RequestParam int docId, Map<String, Object> map, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		CancerDocVO cancerDocVO = cancerDocumentService.getCancerDoc(docId, bindingResult, userObject);
		map.put("stdCancerDocVO", cancerDocVO);
		return "document/CancerDocument";
	}
	
	
	@RequestMapping("/getCancerDataFluxClericalReview")
	public String getCancerDataFluxClericalReview(Map<String, Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("FILTER_CRITERIA_STATUS", null);
		session.setAttribute("FILTER_CRITERIA_FILE_NAME", null);
		
		map.put("DOCUMENT_TYPE", "CANCER");
		
		
		
		return "person/ClericalReviewDatafluxStatus";
		
	}
	
	
	
	
	
	
}

