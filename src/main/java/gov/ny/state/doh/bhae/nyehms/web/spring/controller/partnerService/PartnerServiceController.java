package gov.ny.state.doh.bhae.nyehms.web.spring.controller.partnerService;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService.PsAssignmentView;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService.PsPartnerView;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.partnerService.PartnerService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller will listen to request from front end (JSP) and will return
 * JSON Object with the PS Search data.
 *
 * @author vxp06
 */
@RequiredArgsConstructor
@Controller
public class PartnerServiceController {
	
	private final PartnerService partnerService;
	
	private final CommonDocumentService commonDocumentService;

    /**
     * This will be called from the menu and we will return the web page link
     *
     * @return
     */
    @RequestMapping("/psSearch")
    public String psSearch(Map<String, Object> map, HttpServletRequest request) {
        // return the web page link 
        return "psSearch/";
    }

    /**
     * This function will call the method to query database and return JSON
     * object as response
     *
     * @param map
     * @param request
     * @param reportId
     * @return
     */
    @RequestMapping(value="/getPSInvestigationData.html")
    public String getPSInvestigationData(Map<String, Object> map, HttpServletRequest request, @RequestParam long docId, UserObject userObject) {



        
         Map<Object, Object> targetMap = new HashMap<>();
         BindingResult result = new MapBindingResult(targetMap, "targetMap");
         
         
         
         List<Map<String, String>> codedSexualBehaviors = partnerService.getCodedSexualBehaviors();
         
         Map<String, String> codedSexualBehaviorsMap = new HashMap<>();
         
         for (Map<String, String> sexualBehaviors : codedSexualBehaviors) {
        	 codedSexualBehaviorsMap.put(sexualBehaviors.get("SEXUAL_BEHAVIOR_CODE"), sexualBehaviors.get("SEXUAL_BEHAVIOR_DESC"));
		}
         
         
		PsAssignmentView psAssignmentView = partnerService.getInvestDetailsData(docId, userObject, result);
		
		List<JSONObject> investigationStatusList= commonDocumentService.getCodedInvestigationStatus();
		
		
		map.put("investigationStatusList", investigationStatusList);
        map.put("psAssignmentView", psAssignmentView);
        map.put("riskList", codedSexualBehaviorsMap);
        return "document/PartnerService";
    }

    /**
     * This function will call the method to query database and return JSON
     * object as response
     *
     * @param map
     * @param request
     * @param partnerId
     * @return
     */
    @RequestMapping(value="/getPSRiskData.html")
    public String getPSRiskData(Map<String, Object> map, HttpServletRequest request, @RequestParam String partnerId,@RequestParam String assignmentId) {
        
        PsPartnerView psPartnerView = partnerService.getPartnerRiskData(partnerId,assignmentId);
        map.put("psPartnerView", psPartnerView);
        return "document/PartnerServiceIndex";
    }
}
