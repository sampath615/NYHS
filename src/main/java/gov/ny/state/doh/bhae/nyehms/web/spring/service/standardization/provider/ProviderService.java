package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;



public interface ProviderService {
	
	//get the RawProviderAddress for Standardize
	public List<RawProviderAddressVO> getRawProvAddrsList(BindingResult result, boolean _search, String searchField, String searchString, String searchOper, UserObject userObject);
	
	/*
	 * linking Raw Provider Address To Sub Provider Address ID
	 * @param rawProviderAddressId the raw provider to be standardized
	 * @param providerAddressId the target provider to which this raw provider address should be linked
	 * @param result BindingResult reference to hold the results
	 * @param userObject User reference
	 * @sessionID the unique session identifier
	 */
	public void linkRawProvToSubProvAddrs(long rawProviderAddressId,long providerAddressId, BindingResult result, UserObject userObject);
	public void linkRawProvidersToSubProvAddrs(String[] rawProvSelectedAddresses,long providerAddressId, BindingResult result, UserObject userObject);
	
	/*
	 * Adding New Provider and Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param providerAndAddressVO the value object which holds the provider address
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	public void addNewSubProvAddrsAndlinking(long rawProviderAddressId,SubProviderAndAddressVO providerAndAddressVO, BindingResult result, UserObject userObject);
	public void addNewProvNAddrsAndlinking(String[] rawProviderAddressIds,SubProviderAndAddressVO providerAndAddressVO, BindingResult result, UserObject userObject);
	/*
	 * Adding New Address to a Provider
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param providerAndAddressVO the value object which holds the provider address
	 * @result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	public void addNewSubProvAddrs(SubProviderAndAddressVO providerAndAddressVO, BindingResult result, UserObject userObject);
	
	/*
	 * Adding New Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param providerId the provider to which the new address should be linked to
	 * @param providerAndAddressVO the value object which holds the provider address
	 * @param result the binding result to hold the results
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier  
	 */
	public void addingAddrsAndlinking(long rawProviderAddressId, long providerId,SubProviderAndAddressVO providerAndAddressVO, BindingResult result, UserObject userObject);
	
	public void addingProvAddrsAndlinking(String[] rawProviderAddressIds, long providerId,SubProviderAndAddressVO providerAndAddressVO, BindingResult result, UserObject userObject);
	/* Adding New Provider Address to an existing Provider
	 * @param providerId the provider for which new address is created
	 * @param providerAndAddressVO the value object which holds the provider address
	 * @result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 * @return providerAddressId the newly created address ID for this provider
	 */
	public void addingAddressToProvider(long providerId,SubProviderAndAddressVO providerAndAddressVO, BindingResult result, UserObject userObject);
	
	//SubGrid of Sub Grid of Sub Provider i.e Raw Provider Address Id. getting the Raw Provider Addresses Using Sub Provider Address Id
	public List<RawProviderAddressVO> getRawProvAdrsListBySubprovAdrsId(long subProviderAdrsId, BindingResult result, UserObject userObject);
	
	public void unlinkProviderAddress(String provWorkType, String commaSeparatedVals, long parentId, BindingResult result, UserObject userObject);
	public void mergeSubProvAddrs(String commaSeparatedVals, long selToBeMergedSubAddrs, long selToBeMergedParent, BindingResult result, UserObject userObject);
	public void mergeSubProviders(String commaSeparatedVals, long selToBeMergedSubProv, BindingResult result, UserObject userObject);
	
	/*
	 * Adding New Provider
	 * @param ProviderVO the value object which holds the provider details
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 * @return the provider ID of the newly created provider
	 */
	public void addNewProvider(SubProviderVO providerVO, BindingResult result, UserObject userObject);
	
	
	public List<Map<String, String>> getActiveSubProviders(BindingResult bindingResult,UserObject userObject,String providerName);

	public void unableStandardizeProviderRecord(long recordId,
			String effectedTable, BindingResult bindingResult,
			UserObject userObject);

	public void addProviderDetails(
			SubProviderAndAddressVO subProviderAndAddressVO,
			BindingResult bindingResult, UserObject userObject);

	public long createRawProviderAddress(
			SubProviderAndAddressVO subProviderAndAddressVO,
			BindingResult bindingResult, UserObject userObject);

	public void addRawAndSubProviderAddressDetails(long providerId,
			SubProviderAndAddressVO subProviderAndAddressVO,
			BindingResult bindingResult, UserObject userObject);

	public int checkRawProviderAddressExistance(
			SubProviderAndAddressVO  subProviderAndAddressVO);
	
	public List<RawProviderAddressVO> getRawProvAddrsNewList(BindingResult result, boolean selected, long[] selectedIds, UserObject userObject);

	public List<JSONObject> getMasterProvRecords();

	public JSONObject getMasterProvData(String providerId);
	
}
