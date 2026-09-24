package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;

import java.util.List;
import java.util.Map;

public interface FacilityManualReviewDAO {
	public void matchRawFacility(Map<Object,Object> inOutParameterMap);
	public void unMatchRawFacility(Map<Object,Object> inOutParameterMap);
	public List<MasterFacilityDetailsVO> getFacilityDetailsForManualReview(Map<Object,Object> inOutParameterMap);
}
