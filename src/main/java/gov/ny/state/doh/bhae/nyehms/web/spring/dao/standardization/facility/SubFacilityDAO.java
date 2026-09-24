package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityForRawFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;

import java.util.List;
import java.util.Map;

public interface SubFacilityDAO {
	
	public List<SubFacilityVO> getSubFacilityList(Map<Object, Object> map);
	
	public void insertNewStdFacility(Map<Object, Object> map);
	
	public long getMasterfacilityID(long subFacilityId);
	
	public List<SubFacilityForRawFacilityVO> getStdIdsForRawIds(Map<Object, Object> map);

	public List<SubFacilityVO> getSubFacListForSubIds(Map<Object, Object> map);

	public void addNewStdFacility(Map<Object, Object> inOutParameterMap);

	public String getSubFacilityId(String facilityUid);
	
	public int checkRawFacilitiesForStandardFacilities(int subFacilityId);
	public int createRawFacilitiyForStandardFacility(Map<Object, Object> map);

	public int unableStandardizeRawFacilityRecord(Map<Object, Object> map);

	public int unableStandardizeSubFacilityRecord(Map<Object, Object> map);

}
