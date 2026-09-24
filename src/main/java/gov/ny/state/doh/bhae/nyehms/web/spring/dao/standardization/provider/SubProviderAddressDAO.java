package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProvAddrsForRawProvAddrsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAddressVO;

import java.util.List;
import java.util.Map;

public interface SubProviderAddressDAO {
	
	// get the Sub Provider Address Id's List
	public List<Long> getSubProvAdrsIdsForSearch(SubProviderAddressVO subProviderAddressVO);
	
	// get the  Sub Provider Address Id's using Raw Provider Address Id's	
	public List<SubProvAddrsForRawProvAddrsVO> getSubProvAdrsIdsForRawProvAdrsIds(Map<Object, Object> map);
}
