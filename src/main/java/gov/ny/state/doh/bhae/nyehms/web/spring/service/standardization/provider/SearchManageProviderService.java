package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;

public interface SearchManageProviderService {
		
	/**
	 * This method will pass the Provider Address details as input(Search criteria) to the My Batis sql map
	 * 
	 * The Response object will Provide Provider details, Provider Addresses, Raw Provider Address.
	 * 
	 * Provider details:  Will render the Provider details (which matches the Provider Address criteria those parent Provider details.)
	 * Provider Addresses: Render all the Provider Addresses related to the Rendered Provider details
	 * Raw Provider Address: Render all Raw Address related to the Rendered Provider Addresses
	 * 
	 * @param subProviderAndAddressVO holding the Address Provider details holding the Search criteria
	 * @param  result  using to store error related information
	 * @param userObject, sessionID  used to track Exception related information 
	 * @return List<ProviderDetailsVO> Having the search resulted data   
	 */ 
	public List<ProviderDetailsVO> getProviderDetailsByProviderAddress(SubProviderAndAddressVO subProviderAndAddressVO , BindingResult result, UserObject userObject);

	/**
	 * This method will pass the Provider details as input(Search criteria) to the My Batis sql map
	 * 
	 * The Response object will Provide Provider details, Provider Addresses, Raw Provider Address.
	 * 
	 * Provider details:  Will return the Provider details which matches input criteria.
	 * Provider Addresses: Render all the Provider Addresses related to the Rendered Provider details
	 * Raw Provider Address: Render all Raw Address related to the Rendered Provider Addresses
	 * @param  result  using to store error related information
	 * @param SubProviderVO holding the Provider details holding the Search criteria
	 * 
	 * @param userObject, sessionID  used to track Exception related information 
	 * @return List<ProviderDetailsVO> Having the search resulted data   
	 */
	public List<ProviderDetailsVO> getProviderDetailsByProvider(SubProviderVO providerVO , BindingResult result, UserObject userObject);
	
	/**
	 * This method will pass the Provider Address details as input(Search criteria) to the My Batis sql map
	 * 
	 * The Response object will Provide Provider details, Provider Addresses, Raw Provider Address.
	 * 
	 * Provider Addresses: Render all the Provider Addresses (which matches the Raw Provider Address criteria those parent Provider Address details.)
	 * 
	 * Provider details:  Will render the Provider details related to the rendered Provider Address
	 * 
	 * Raw Provider Address: Render all Raw Address related to the Rendered Provider Addresses
	 * @param  result  using to store error related information
	 * @param RawProviderAddressVO holding the Raw Provider Address details holding the Search criteria
	 * 
	 * @param userObject, sessionID  used to track Exception related information 
	 * @return List<ProviderDetailsVO> Having the search resulted data   
	 */ 
	public List<ProviderDetailsVO> getProviderDetailsByRawProviderAddress(RawProviderAddressVO rawProviderAddressVO , BindingResult result, UserObject userObject);
	
	public List<RawProviderAddressVO> getRawProviderDetails(
			RawProviderAddressVO rawProviderAddressVO,
			BindingResult dummyBindingResult, UserObject userObject);

}
