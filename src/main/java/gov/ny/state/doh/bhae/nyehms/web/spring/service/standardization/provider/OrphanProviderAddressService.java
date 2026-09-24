package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;
/*
 * The service interface for orphan provider address operations
 */
public interface OrphanProviderAddressService {
	/*
	 * Get a list of orphan provider addresses 
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
	 * @return List list of orphan addresses in SubProviderAddressVO instances 
	 */
	public List<SubProviderAddressVO> getOrphanProviderAddresses(BindingResult bindingResult, UserObject userObject);
	/*
	 * This method links an orphan address to a provider. 
	 * After this operation the orphan address is associated to this provider and no longer an orphan
	 * @param providerAddressId the orphan address for which parent provider linking is needed
	 * @param providerId the parent provider to which this orphan address should be linked
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
 	 * @return String the transaction result
	 */
	public void linkOrphanAddressToProvider(long  providerAddressId, long  providerId,BindingResult bindingResult,UserObject userObject);
	/*
	 * This method links a raw provider address to an orphan provider address. 
	 * Linking a raw address to an orphan address can not be completed 
	 * unless the orphan gets associated to a provider. Hence this operation first expect the user
	 * to select a provider and link the orphan address to this provider. After this sub-operation
	 * is successfully completed, the raw address is linked to the orphan address.
	 * Please see {@link #linkOrphanAddressToProvider(long  providerAddressId, long  providerId,BindingResult bindingResult,UserObject userObject)}    
	 * After this operation the orphan address is associated to this provider and no longer an orphan
	 * and the raw provider address is associated to orphan provider address and no longer a raw provider address.
	 * @param providerAddressId the orphan address for which parent provider linking is needed
	 * @param providerId the parent provider to which this orphan address should be linked
	 * @rawProviderAddressId the raw provider address which needs to be linked to the orphan address
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
 	 * @return String the transaction result
	 */
	public void linkRawToOrphanProviderAddress(long  providerAddressId, long  providerId,long rawProviderAddressId,BindingResult bindingResult,UserObject userObject);
	
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
	 * This method links a raw provider address to an orphan provider address. 
	 * Linking a raw address to an orphan address can not be completed 
	 * unless the orphan gets associated to a provider. Hence this operation first expect the user
	 * to create a provider and link the orphan address to this provider. After this sub-operation
	 * is successfully completed, the raw address is linked to the orphan address.
	 * Please see {@link #linkRawToOrphanProviderAddress(long  providerAddressId, long  providerId,long rawProviderAddressId,BindingResult bindingResult,UserObject userObject)}    
	 * After this operation a new provider is created and the orphan address is associated to this provider and no longer an orphan.
	 * The raw provider address is associated to orphan provider address and no longer a raw provider address.
	 * @param providerAddressId the orphan address for which parent provider linking is needed
	 * @rawProviderAddressId the raw provider address which needs to be linked to the orphan address
 	 * @param providerVO the parent provider to be created and this orphan address should be linked
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
 	 * @return String the transaction result
	 */
	public void addNewProviderAndLinkRawToOrphanProviderAddress(long providerAddressId,long rawProviderAddressId,SubProviderVO providerVO,BindingResult bindingResult,UserObject userObject);
}
