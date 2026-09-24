package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import java.util.Map;

public interface MergeAndUnlinkProvDAO {

	public void unlinkRawProviderAndAddress(Map<Object, Object> map);
	public void unlinkSubProviderAddress(Map<Object, Object> map);
	public void mergeSubProviderAddress(Map<Object, Object> map);
	public void mergeSubProviders(Map<Object, Object> map);
}
