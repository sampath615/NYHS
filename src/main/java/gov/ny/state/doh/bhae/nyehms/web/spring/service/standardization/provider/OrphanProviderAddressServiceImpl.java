package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.OrphanProviderAddressDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
	/*
	 * The service implementation class for orphan provider address operations
	 * This class is annotated as @Service in order to load as a Spring managed bean
	 */
@Service
public class OrphanProviderAddressServiceImpl implements OrphanProviderAddressService {
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(OrphanProviderAddressServiceImpl.class);
	/*
	 * Reference to custom exception handler by means of dependency injection 
	 * which logs and email error messages
	 */
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	/*
	 * Spring dependency injected reference to OrphanProviderAddressDAO 
	 */
	@Autowired
	OrphanProviderAddressDAO orphanProviderAddressDAO;
	/*
	 * Operation success message used while linking an orphan provider address to a provider
	 * Used inside {@link #linkOrphanAddressToProvider(long  providerAddressId, long  providerId,BindingResult bindingResult,UserObject userObject,String sessionId)} 
	 */
	private static final String LINK_ORPHAN_TO_PROVIDER_SUCCESS_MESSAGE="Address has been linked to provider";
	/*
	 * Operation success message used while linking a raw provider address to an orphan provider address
	 * by selecting an existing provider
	 * Used inside {@link #linkRawToOrphanProviderAddress(long  providerAddressId, long  providerId,long rawProviderAddressId,BindingResult bindingResult,UserObject userObject,String sessionId)} 
	 */
	private static final String LINK_RAW_TO_ORPHAN_AND_PROVIDER_SUCCESS_MESSAGE="Orphan address has been linked to the provider and Raw address has been linked to the address";
	/*
	 * Operation success message used while linking a raw provider address to an orphan provider address
	 * by creating a new provider
	 * Used inside {@link #addNewProviderAndLinkRawToOrphanProviderAddress(long providerAddressId,long rawProviderAddressId,SubProviderVO providerVO,BindingResult bindingResult,UserObject userObject, String sessionId)} 
	 */
	private static final String CREATE_PROVIDER_AND_LINK_RAW_TO_ORPHAN_SUCCESS_MESSAGE="Orphan address has been linked to the provider and Raw address has been linked to the address";
	private static final String ADD_PROVIDER_AND_LINK_ORPHAN_SUCCESS_MESSAGE="Orphan address has been linked to the newly added provider";
	/*
	 * Spring dependency injected reference to StandardizationTransactionHelper
	 */
	@Autowired
	ProviderStandardizationTransactionHelper standardizationTransactionHelper;
	/*
	 * Get a list of orphan provider addresses 
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
	 * @return List list of orphan addresses in SubProviderAddressVO instances 
	 */
	@Override
	public List<SubProviderAddressVO> getOrphanProviderAddresses(
			BindingResult bindingResult, UserObject userObject) {
		
		List<SubProviderAddressVO> orphanProviderAddresses=null;
		orphanProviderAddresses= orphanProviderAddressDAO.getOrphanProviderAddresses();
		return orphanProviderAddresses;
	}
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
	@Override
	public void linkOrphanAddressToProvider(long  providerAddressId, long  providerId,BindingResult bindingResult,UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParamaterMap;

		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("providerAddressId", providerAddressId);
		inOutParamaterMap.put("providerId", providerId);
		inOutParamaterMap.put("statusCode", new Integer(1));
		inOutParamaterMap.put("statusMessage", "");

		orphanProviderAddressDAO.linkOrphanAddressToProvider(inOutParamaterMap);

		statusCode=(Integer)inOutParamaterMap.get("statusCode");
		transactionMessage=statusCode==0?LINK_ORPHAN_TO_PROVIDER_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("statusMessage");
		
		if(statusCode != 0)throw new DataRetrievalFailureException(transactionMessage);
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	/*
	 * This method links a raw provider address to an orphan provider address. 
	 * Linking a raw address to an orphan address can not be completed 
	 * unless the orphan gets associated to a provider. Hence this operation first expect the user
	 * to select a provider and link the orphan address to this provider. After this sub-operation
	 * is successfully completed, the raw address is linked to the orphan address.
	 * Please see {@link #linkOrphanAddressToProvider(long  providerAddressId, long  providerId,BindingResult bindingResult,UserObject userObject,String sessionId)}    
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
	@Override
	public void linkRawToOrphanProviderAddress(long  providerAddressId, long  providerId,long rawProviderAddressId,BindingResult bindingResult,UserObject userObject){
		
		boolean isError = false; 
		try{
			standardizationTransactionHelper.linkRawToOrphanProviderAddress(providerAddressId, providerId,rawProviderAddressId, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured while linking Raw({}) to Orphan Provider Address({}) and Provider ID({})",rawProviderAddressId,providerAddressId,providerId);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while linking Raw({}) to Orphan Provider Address({}) and Provider ID({})",rawProviderAddressId,providerAddressId,providerId);
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize orphan Address details", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, LINK_ORPHAN_TO_PROVIDER_SUCCESS_MESSAGE, bindingResult);
		}
	}
	
	/*
	 * Adding New Provider and linking it to Provider Address
	 * @param providerAddressId the provider Address which needs to be linked
	 * @param providerVO the value object which holds the provider details
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
		@Override
		public void addNewProviderAndLinkingOrphanAddrs(long providerAddressId, SubProviderVO providerVO, BindingResult bindingResult, UserObject userObject){
			
			boolean isError = false; 
			try{
				standardizationTransactionHelper.addProviderAndLinkingOrphanAddrs(providerAddressId, providerVO, bindingResult,userObject);
			}catch(DataAccessException dataAccessException){
				isError = true;
				log.error("Exception occured while linking Orphan Addrs({}) tothe new Provider({})",providerAddressId,providerVO.getProviderLname());
				log.error(dataAccessException.getMessage());
			}catch(Exception exception){
				isError = true;
				log.error("Exception occured while linking Orphan Addrs({}) tothe new Provider({})",providerAddressId,providerVO.getProviderLname());
				log.error(exception.getMessage());
			}
			
			if(isError || bindingResult.hasErrors()){
				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize orphan Provider Address to the new Provider", bindingResult);
			}else{
				commonExceptionHandler.inspectDBTransacionResult(0, ADD_PROVIDER_AND_LINK_ORPHAN_SUCCESS_MESSAGE, bindingResult);
			}
		}
		/*
		 * This method links a raw provider address to an orphan provider address. 
		 * Linking a raw address to an orphan address can not be completed 
		 * unless the orphan gets associated to a provider. Hence this operation first expect the user
		 * to create a provider and link the orphan address to this provider. After this sub-operation
		 * is successfully completed, the raw address is linked to the orphan address.
		 * Please see {@link #linkRawToOrphanProviderAddress(long  providerAddressId, long  providerId,long rawProviderAddressId,BindingResult bindingResult,UserObject userObject,String sessionId)}    
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
		public void addNewProviderAndLinkRawToOrphanProviderAddress(long providerAddressId,long rawProviderAddressId,SubProviderVO providerVO,BindingResult bindingResult,UserObject userObject){
			standardizationTransactionHelper.addNewProviderAndLinkRawToOrphanProviderAddress(providerAddressId, rawProviderAddressId, providerVO, bindingResult, userObject);
			if(bindingResult.hasErrors()){
				commonExceptionHandler.addBindingResultMessage(bindingResult,AppConstants.NYEHM_TRANSACTION_RESULT.getName(), AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
				commonExceptionHandler.addBindingResultMessage(bindingResult,AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), AppConstants.NYEHM_USER_FRIENDLY_ERROR_MESSAGE.getName());
			}else{
				commonExceptionHandler.addBindingResultMessage(bindingResult,AppConstants.NYEHM_TRANSACTION_RESULT.getName(), AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName());
				commonExceptionHandler.addBindingResultMessage(bindingResult,AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), CREATE_PROVIDER_AND_LINK_RAW_TO_ORPHAN_SUCCESS_MESSAGE);
			}
		}

}
