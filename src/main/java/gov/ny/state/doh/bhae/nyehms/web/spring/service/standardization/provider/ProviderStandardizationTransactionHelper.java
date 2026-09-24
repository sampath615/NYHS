package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
	/*
	 * A helper class to manage all operations which should be executed in a transaction.
	 * The methods are annotated with Spring @Transactional to ensure the transaction.
	 * This class is annotated as @Service in order to load as a Spring managed bean.
	 */
@Service
public class ProviderStandardizationTransactionHelper {
	/*
	 * Spring dependency injected reference to CommonExceptionHandler
	 */
	/*
	 * Spring dependency injected reference to ProviderService
	 */
	@Autowired
	ProviderService providerService;
	/*
	 * Spring dependency injected reference to OrphanProviderAddressService
	 */
	@Autowired
	OrphanProviderAddressService orphanProviderAddressService;
	/* This method facilitate the multiple calls involved in the transaction 
	 * while adding New Provider Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @result the binding result reference to hold the result
	 * @param subProviderAndAddressVO the value object which holds the provider address
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	@Transactional
	public void addingAddrsAndlinkingTrns(long rawProviderAddressId, long providerId,SubProviderAndAddressVO providerAndAddressVO, BindingResult result, 
			UserObject userObject) {
		providerService.addingAddressToProvider(providerId,providerAndAddressVO, result, userObject);
		long providerAddressId = providerAndAddressVO.getSubProviderAddressId();
		if(providerAddressId!=0){
			providerService.linkRawProvToSubProvAddrs(rawProviderAddressId, providerAddressId,result,userObject);
		}
	}
	
	@Transactional
	public void addingProvAddrsAndlinkingTrns(String[] rawProviderAddressIds, long providerId,SubProviderAndAddressVO providerAndAddressVO, BindingResult result, 
			UserObject userObject) {
		providerService.addingAddressToProvider(providerId,providerAndAddressVO, result, userObject);
		long providerAddressId = providerAndAddressVO.getSubProviderAddressId();
		if(providerAddressId!=0){
			providerService.linkRawProvidersToSubProvAddrs(rawProviderAddressIds, providerAddressId,result,userObject);
		}
	}
	/*
	 * This method facilitate the multiple calls involved in the transaction
	 * while adding New Provider and Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param providerAndAddressVO the value object which holds the provider address
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	@Transactional
	public void addNewSubProvAddrsAndlinkingTrns(long rawProviderAddressId,SubProviderAndAddressVO providerAndAddressVO, BindingResult result, 
			UserObject userObject){
		providerService.addNewSubProvAddrs(providerAndAddressVO, result, userObject);
		long providerAddressId = providerAndAddressVO.getSubProviderAddressId();
		if(providerAddressId!=0){
			providerService.linkRawProvToSubProvAddrs(rawProviderAddressId, providerAddressId,result,userObject);
		}
	}
	
	@Transactional
	public void addNewProvNAddrsAndlinkingTrns(String[] rawProviderAddressIds,SubProviderAndAddressVO providerAndAddressVO, BindingResult result, 
			UserObject userObject){
		providerService.addNewSubProvAddrs(providerAndAddressVO, result, userObject);
		long providerAddressId = providerAndAddressVO.getSubProviderAddressId();
		if(providerAddressId!=0){
			providerService.linkRawProvidersToSubProvAddrs(rawProviderAddressIds, providerAddressId,result,userObject);
		}
	}
	/*
	 * This method facilitate the multiple calls involved in the transaction
	 * while linking a raw provider to an orphan provider address by selecting an existing provider
	 * @param providerAddressId the orphan provider address to which the raw provider address should be linked
	 * @param providerId the provider to which the orphan provider address should be linked
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param bindingResult the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionId the unique session identifier
	 */
	@Transactional
	public void linkRawToOrphanProviderAddress(long  providerAddressId, long  providerId,long rawProviderAddressId,BindingResult bindingResult,UserObject userObject){
		orphanProviderAddressService.linkOrphanAddressToProvider(providerAddressId, providerId, bindingResult, userObject);
		providerService.linkRawProvToSubProvAddrs(rawProviderAddressId, providerAddressId, bindingResult, userObject);
	}
	/*
	 * This method facilitate the multiple calls involved in the transaction
	 * while adding New Provider and  linking it to Provider Address ID
	 * @param providerAddressId the Provider Address which needs to be linked
	 * @param providerVO the value object which holds the provider details
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	@Transactional
	public void addProviderAndLinkingOrphanAddrs(long providerAddressId,SubProviderVO providerVO, BindingResult result, 
			UserObject userObject){
		providerService.addNewProvider(providerVO, result, userObject);
		long providerId = providerVO.getProviderId();
		if(providerAddressId!=0 && providerId!=0){
			orphanProviderAddressService.linkOrphanAddressToProvider(providerAddressId,providerId, result, userObject);
		}
	}
	/*
	 * This method facilitate the multiple calls involved in the transaction
	 * while linking a raw provider to an orphan provider address by creating a new provider
	 * @param providerAddressId the orphan provider address to which the raw provider address should be linked
	 * @param providerId the provider to which the orphan provider address should be linked
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param bindingResult the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionId the unique session identifier
	 */
	@Transactional
	public void addNewProviderAndLinkRawToOrphanProviderAddress(long providerAddressId,long rawProviderAddressId,SubProviderVO providerVO,BindingResult bindingResult,UserObject userObject){
		providerService.addNewProvider(providerVO, bindingResult, userObject);
		long providerId = providerVO.getProviderId();
		if(providerAddressId!=0 && providerId!=0){
			orphanProviderAddressService.linkRawToOrphanProviderAddress(providerAddressId, providerId, rawProviderAddressId, bindingResult, userObject);
		}
	}
	
	@Transactional
	public void addProviderDetails(
			SubProviderAndAddressVO subProviderAndAddressVO,
			BindingResult bindingResult, UserObject userObject) {
		providerService.addNewSubProvAddrs(subProviderAndAddressVO, bindingResult, userObject);
		
		if(subProviderAndAddressVO.getSubProviderAddressId() == 0){
			throw new DataRetrievalFailureException("Failed to create the Standard Provider Address.");
		}else{
			
			providerService.createRawProviderAddress(subProviderAndAddressVO, bindingResult, userObject);
			
			
			
		}
		
	}
	
	@Transactional
	public void addRawAndSubProviderAddressDetails(long providerId,
			SubProviderAndAddressVO subProviderAndAddressVO,
			BindingResult bindingResult, UserObject userObject) {
		providerService.addingAddressToProvider(providerId,subProviderAndAddressVO, bindingResult, userObject);
		long providerAddressId = subProviderAndAddressVO.getSubProviderAddressId();
		if(providerAddressId!=0){
			providerService.createRawProviderAddress(subProviderAndAddressVO, bindingResult, userObject);
		}
		
	}
}
