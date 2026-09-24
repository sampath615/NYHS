package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;

public interface FacilityManualReviewService {
	public void matchRawFacility(long newRawFacilityId,long matchingRawFacilityId,BindingResult bindingResult,UserObject userObject);
	public void unMatchRawFacility(long newRawFacilityId,BindingResult bindingResult,UserObject userObject);
	public List<MasterFacilityDetailsVO> getFacilityDetailsForManualReview(long rawFacilityId,BindingResult bindingResult, UserObject userObject);
}
