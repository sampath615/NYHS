package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;

public interface ProviderManualReviewService {
	public void matchRawProviderAddress(long newRawProviderAddressId,long matchingRawProviderAddressId,BindingResult bindingResult,UserObject userObject);
	public void unMatchRawProviderAddress(long newRawProviderAddressId,BindingResult bindingResult,UserObject userObject);
	public List<ProviderDetailsVO> getPotentialProviderDetailsListByRawProvider(long rawProviderAddressId,BindingResult bindingResult, UserObject userObject);
	public List<RawProviderAddressVO> getClericalReviewRawProviderAddressDetailsList(BindingResult bindingResult, UserObject userObject);
	
}
