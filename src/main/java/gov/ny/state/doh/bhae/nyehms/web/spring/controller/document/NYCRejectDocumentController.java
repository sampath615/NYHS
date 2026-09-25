package gov.ny.state.doh.bhae.nyehms.web.spring.controller.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.NYCRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NYCRejectDocumentService;


@Controller
public class NYCRejectDocumentController {
	
	
	@Autowired NYCRejectDocumentService nycRejectDocumentService;
	
	@RequestMapping(value = "/getNYCRejectDoc.html")
	public String getNYCRejectDocAsHTML(@RequestParam(required=false) Integer docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		List<NYCRejectVO> nycRejectList = nycRejectDocumentService.getNYCRejectDetails(docId, bindingResult, userObject);
		
		map.put("nycRejectList", nycRejectList);

	return "document/NYCRejectDetails";
	
	}
	
	@RequestMapping(value="/updateNYCRejectDocumentComments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateNYCRejectDocumentComments(HttpServletRequest request, @RequestParam int documentId, @RequestParam String comments, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		
		nycRejectDocumentService.updateNYCRejectDocumentComments(documentId, comments, userObject);
		
		return targetMap;
	}	

}
