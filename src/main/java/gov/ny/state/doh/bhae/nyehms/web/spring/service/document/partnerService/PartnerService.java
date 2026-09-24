package gov.ny.state.doh.bhae.nyehms.web.spring.service.document.partnerService;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService.PsAssignmentView;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService.PsPartnerView;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 * Interface for Historic Partner Search Service
 * @author vxp06
 */
public interface PartnerService {

    /**
     * This will return JSON object for Person investigation details view
     * @param result 
     * @param userObject 
     *
     * @param reportId
     * @return
     */
    public PsAssignmentView getInvestDetailsData(long documentId, UserObject userObject, BindingResult result);

    /**
     * This will return JSON object for Person partner risk data view
     *
     * @param partnerId
     * @return
     */
    public PsPartnerView getPartnerRiskData(String partnerId,String assignment_id);

	public List<Map<String, String>> getCodedSexualBehaviors();

}
