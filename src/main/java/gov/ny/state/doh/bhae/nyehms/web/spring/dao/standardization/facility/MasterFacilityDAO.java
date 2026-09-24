package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import java.util.Map;

public interface MasterFacilityDAO {
	
	
	 public void insertNewGrdFacility(Map<Object, Object> map);
	 
	 public void createMasterAndSubFacilities(Map<Object,Object> inOutParameterMap);
}
