package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;

import java.util.List;
import java.util.Map;

public interface ProviderManualReviewDAO {
	public void matchRawProviderAddress(Map<Object,Object> inOutParameterMap);
	public void unMatchRawProviderAddress(Map<Object,Object> inOutParameterMap);
	public List<ProviderDetailsVO> getProviderDetailsForManualReview(Map<Object,Object> inOutParameterMap);
	
	public List<RawProviderAddressVO> getClericalReviewRawProviderAddressList();
	
}
