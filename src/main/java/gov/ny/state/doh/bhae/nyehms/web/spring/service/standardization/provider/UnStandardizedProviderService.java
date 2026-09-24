package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;

/**
 * 
 * @author axp21
 * The service locator for unstandardized provider operations
 */
public interface UnStandardizedProviderService {

	/*
	 * Move the provider data and copy it to facility data.
	 * @param providerId Unique identifier of the provider data to be moved
	 * @return success or failure message based on the transaction   
	 */
	public void flagProviderDataAsFacilty(long providerId,BindingResult result, UserObject userObject);
	
	public void flagSelectedRawProviderAsFacilty(String[] rawProvAddressIds,BindingResult result, UserObject userObject);
	
	/*
	 * Adding New Provider and linking it to Provider Address
	 * @param providerAddressId the provider Address Id which needs to be linked
	 * @param providerVO the value object which holds the provider details
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	public void addNewProviderAndLinkingOrphanAddrs(long providerAddressId, SubProviderVO providerVO, BindingResult result, UserObject userObject);
	
	/*
	 * Displaying Provider and Standard Provider Address details with user search criteria
	 * @param subProviderAndAddressVO the value object which holds the provider Address details for search criteria
	 * @param searchFor this parameter gives information about the page which 
	 * requested the provider details search. Based on this parameter value, we need to
	 * filter the orphan addresses from the search result as follows
	 * For searchFor = 1 - this value is passed from the orphan address page where we
	 * dont need to show the Unstandardized provider(the imaginary parent under which 
	 * orphan addresses are grouped and shown) in the provider search results because 
	 * an orphan address cannot be associated to this Unstandardized provider.
	 * For searchFor = 3 - this value is passed from the raw address page where we 
	 * need to show the Unstandardized provider(the imaginary parent under which 
	 * orphan addresses are grouped and shown) in the provider search results.      
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	public List<ProviderDetailsVO> getSrchProviderDetailsWithAddressList(SubProviderAndAddressVO subProviderAndAddressVO , int searchFor, String searchFromNPI, BindingResult result, UserObject userObject);

}
