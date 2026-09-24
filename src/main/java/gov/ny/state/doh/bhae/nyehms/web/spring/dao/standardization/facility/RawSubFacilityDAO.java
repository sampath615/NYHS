package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;

public interface RawSubFacilityDAO {

    public List<RawSubFacilityVO> getRawSubFacilityListNewPage(Map<Object,Object> inOutParameterMap);
    public List<RawSubFacilityVO> getRawSubFacilityListManualReview();
    
    public List<RawSubFacilityVO> getRawListByStdFacilityId(Map<Object, Object> map);
    
    public void updateStandardize(Map<Object, Object> map);
    
	/*
	 * Manual Review
	 * getting the potential matches for the selected raw facility id  
	 */
	public List<RawSubFacilityVO> getPotentialRawFacList(long rawSubFacilityId);

    public void flagFacilityAsProvider(Map<Object,Object> inOutParameterMap);
	public int checkRawFacilityExistance(RawSubFacilityVO rawSubFacilityVO);
	public int createRawFacility(Map<Object, Object> inOutParameterMap);
}