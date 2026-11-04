package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import java.util.Map;

public interface MergeAndUnlinkDAO {

	public void unlinkRawFacilities(Map<Object, Object> map);
	public void unlinkSubFacilities(Map<Object, Object> map);
	public void mergeSubFacilities(Map<Object, Object> map);
	public void mergeGrandFacilities(Map<Object, Object> map);
}
