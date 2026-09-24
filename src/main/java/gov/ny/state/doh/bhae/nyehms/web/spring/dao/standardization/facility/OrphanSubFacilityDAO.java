package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;

import java.util.List;
import java.util.Map;

public interface OrphanSubFacilityDAO {
	public List<SubFacilityVO> getOrphanSubFacilities();
	
	public void linkOrphanSubFacilityToMasterFacility(Map<Object,Object> inOutParameterMap);
}
