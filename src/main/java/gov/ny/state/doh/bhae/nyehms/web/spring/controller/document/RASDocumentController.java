package gov.ny.state.doh.bhae.nyehms.web.spring.controller.document;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RASDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.RASDocumentService;

@Controller
public class RASDocumentController {
	
	private static Logger log = LoggerFactory.getLogger(RASDocumentController.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	RASDocumentService rasDocumentService;
	
	
	@RequestMapping(value = "/getRASForm.html")
	public String getRASDocAsHTML(@RequestParam(required=false) Integer docId, Map<String, Object> map, HttpServletRequest request)
	{
		
		log.debug(nyehmsLog + "  incoming RAS Doc ID is {}",docId);
		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		

		RASDocVO rasDocVO = rasDocumentService.getRASDocVO(docId, false, bindingResult, userObject);
		
		log.debug(nyehmsLog + "  RAS VO is being fecthed from DB");
		
		map.put("rasDocVO", rasDocVO);

	return "document/RASDocument";
	
	}
	
	@RequestMapping(value = "/getSierraRASForm.html")
	public String getSierraRASForm(@RequestParam(required=false) Integer docId, Map<String, Object> map, HttpServletRequest request)
	{
		
		log.debug(nyehmsLog + "  incoming RAS Doc ID is {}",docId);
		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		

		RASDocVO rasDocVO = rasDocumentService.getRASDocVO(docId, true,  bindingResult, userObject);
		
		log.debug(nyehmsLog + "  RAS VO is being fecthed from DB");
		
		map.put("rasDocVO", rasDocVO);
		
		map.put("SIERRA_FLAG", "Y");

	return "document/RASDocument";
	
	}
	
	

	@RequestMapping(value="/getLinkedResistanceDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Object, Object> getLinkedResistanceDocuments(HttpServletRequest request, @RequestParam int documentId) {
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		String linkedDocumentIds = rasDocumentService.getLinkedResDocuments(documentId);
		
		targetMap.put("linkedDocumentIds", linkedDocumentIds);
		
		return targetMap;
	
	}	
}
