package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.MergeAndUnlinkProvDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.RawProviderAddressDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.SubProviderAndAddressDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.SubProviderDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;


/**
* This class is service interface between the user data to Database(MyBatis related configuration)
* 
* It renders the data from Database in the form List, Int, Sting etc uinsg MyBatis configuation file 
*  
* @author  
* @version 
*/
@Service
public class ProviderServiceImpl implements ProviderService {
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);
	@Autowired
	ProviderStandardizationTransactionHelper standardizationTransactionHelper;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	@Autowired
	private RawProviderAddressDAO rawProviderAddressDAO;  
	@Autowired
	private SubProviderAndAddressDAO subProviderAndAddressDAO;
	@Autowired
	private SubProviderDAO subProviderDAO ;

	@Autowired
	private MergeAndUnlinkProvDAO mergeAndUnlinkDAO;
	
	
	
	@Autowired
	CommonService commonService;
	
	private final static String NEW_PROVIDER_CREATED="New Provider created successfully";
	private final static String LINK_RAW_TO_PROVIDER_ADDRESS_SUCCESS_MESSAGE="Raw provider has been linked to provider address";
	private final static String ADD_NEW_PROVIDER_AND_ADDRESS_AND_LINK_TO_RAW_SUCCESS_MESSAGE="Raw provider has been linked to newly created provider and address";
	private final static String ADD_NEW_PROVIDER_AND_ADDRESS="New provider with address has been added";
	private final static String ADD_NEW_PROVIDER_ADDRESS_AND_LINK_TO_RAW_SUCCESS_MESSAGE="Raw provider has been linked to newly created provider address";
	private final static String ADD_NEW_ADDRESS_TO_PROVIDER="Address has been added to the provider";
	private final static String UNLINK_ADDRESS_SUCCESS_MESSAGE="Address has been unlinked successfully";
	private final static String MERGE_ADDRESS_SUCCESS_MESSAGE="Addresses have been merged ";
	private final static String MERGE_PROVIDERS_SUCCESS_MESSAGE="Providers have been merged";
	@Override
	public List<RawProviderAddressVO> getRawProvAddrsList(BindingResult result, boolean _search, String searchField, String searchString, String searchOper, UserObject userObject){
		
		
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();


		inOutParameterMap.put("_search",_search);
		inOutParameterMap.put("searchField",searchField);
		if(searchOper != null && (searchOper.equalsIgnoreCase("in") || searchOper.equalsIgnoreCase("ni"))){
			inOutParameterMap.put("searchString",searchString.split(","));
		}else{
			inOutParameterMap.put("searchString",searchString);
		}
		inOutParameterMap.put("searchOper",searchOper);
		
		
		List<RawProviderAddressVO> rawProviderAddressList = new ArrayList<RawProviderAddressVO>();
		rawProviderAddressList = rawProviderAddressDAO.getRawProvAddrsList(inOutParameterMap);
			
		return rawProviderAddressList;
	}
	
	
	/*
	 * linking Raw Provider Address To Sub Provider Address ID
	 * @param rawProviderAddressId the raw provider to be standardized
	 * @param providerAddressId the target provider to which this raw provider address should be linked
	 * @param result BindingResult reference to hold the results
	 * @param userObject User reference
	 * @sessionID the unique session identifier
	 */
	@Override
	public void linkRawProvToSubProvAddrs(long rawProviderAddressId,long providerAddressId, BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;

		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_raw_provider_address_id",rawProviderAddressId);
		inOutParameterMap.put("p_sub_provider_address_id",providerAddressId);
		inOutParameterMap.put("p_worker_id",userObject.getWorkerId());

		rawProviderAddressDAO.linkRawProvToSubProvAddrs(inOutParameterMap);

		statusCode=(Integer)inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?LINK_RAW_TO_PROVIDER_ADDRESS_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		if(statusCode != 0)throw new DataRetrievalFailureException(transactionMessage);
	}
	/*
	 * Adding New Provider and Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param providerAndAddressVO the value object which holds the provider address
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	@Override
	public void addNewSubProvAddrsAndlinking(long rawProviderAddressId,SubProviderAndAddressVO providerAndAddressVO, 
			BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		try{
			standardizationTransactionHelper.addNewSubProvAddrsAndlinkingTrns(rawProviderAddressId,providerAndAddressVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured while linking raw ({}) to the new Standard Address({}) and Provider({})",rawProviderAddressId,providerAndAddressVO.getAddress(),providerAndAddressVO.getProviderFname());
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while linking raw ({}) to the new Standard Address({}) and Provider({})",rawProviderAddressId,providerAndAddressVO.getAddress(),providerAndAddressVO.getProviderFname());
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize Raw Provider Address to the New Standard Provider Address and Provider.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, ADD_NEW_PROVIDER_AND_ADDRESS_AND_LINK_TO_RAW_SUCCESS_MESSAGE, bindingResult);
		}
		
	}
	
	@Override
	public void addNewProvNAddrsAndlinking(String[] rawProviderAddressIds,SubProviderAndAddressVO providerAndAddressVO, BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		try{
			standardizationTransactionHelper.addNewProvNAddrsAndlinkingTrns(rawProviderAddressIds,providerAndAddressVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured while linking raw ({}) to the new Standard Address({}) and Provider({})",rawProviderAddressIds,providerAndAddressVO.getAddress(),providerAndAddressVO.getProviderFname());
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while linking raw ({}) to the new Standard Address({}) and Provider({})",rawProviderAddressIds,providerAndAddressVO.getAddress(),providerAndAddressVO.getProviderFname());
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			@SuppressWarnings("unchecked")
			Map<Object,Object> resultMap=((Map<Object,Object>)bindingResult.getTarget());
			int statusCode =0;
			if ((resultMap.get("TRANSACTION_CODE") != null)) {
				statusCode = (int)resultMap.get("TRANSACTION_CODE");
			}
			if (statusCode == -501 && resultMap.get("TRANSACTION_RESULT").equals("TRANSACTION_FAILURE")) {
				commonExceptionHandler.inspectDBTransacionResult(-1, resultMap.get("TRANSACTION_MESSAGE").toString(), bindingResult);
			} else {
				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize Raw Provider Address to the New Standard Provider Address and Provider, please contact helpline.", bindingResult);
			}			
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, ADD_NEW_PROVIDER_AND_ADDRESS_AND_LINK_TO_RAW_SUCCESS_MESSAGE, bindingResult);
		}
		
	}
		
	/*
	 * Adding New Address to a Provider
	 * @param providerAndAddressVO the value object which holds the provider address
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 * @return the provider address ID of the newly created provider address
	 */
	@Override
	public void addNewSubProvAddrs(SubProviderAndAddressVO providerAndAddressVO, BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();
		inOutParameterMap.put("p_provider_fname",providerAndAddressVO.getProviderFname());
		inOutParameterMap.put("p_provider_mname",providerAndAddressVO.getProviderMname());
		inOutParameterMap.put("p_provider_lname",providerAndAddressVO.getProviderLname());
		inOutParameterMap.put("p_provider_suffix",providerAndAddressVO.getProviderSuffix());
		inOutParameterMap.put("p_county_fips",providerAndAddressVO.getCountyFips());
		inOutParameterMap.put("p_address",providerAndAddressVO.getAddress());
		inOutParameterMap.put("p_city",providerAndAddressVO.getCity());
		inOutParameterMap.put("p_state",providerAndAddressVO.getState());
		inOutParameterMap.put("p_zipcode",providerAndAddressVO.getZipcode());
		inOutParameterMap.put("p_plus4",providerAndAddressVO.getPlus4());
		inOutParameterMap.put("p_phone",providerAndAddressVO.getPhone());
		inOutParameterMap.put("p_npi",providerAndAddressVO.getProviderNPI());
		inOutParameterMap.put("p_speciality",providerAndAddressVO.getProviderSpeciality());
		inOutParameterMap.put("p_extension",providerAndAddressVO.getExtension());
		inOutParameterMap.put("p_worker_id",userObject.getWorkerId());

		subProviderAndAddressDAO.addNewSubProvAddrs(inOutParameterMap);

		statusCode=(Integer)inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?ADD_NEW_PROVIDER_AND_ADDRESS:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
		providerAndAddressVO.setSubProviderAddressId((Long) inOutParameterMap.get("p_sub_provider_address_id_out"));
	}
	
	/*
	 * Adding New Provider
	 * @param ProviderVO the value object which holds the provider details
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 * @return the provider ID of the newly created provider
	 */
	@Override
	public void addNewProvider(SubProviderVO providerVO, BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_provider_fname",providerVO.getProviderFname());
		inOutParameterMap.put("p_provider_mname",providerVO.getProviderMname());
		inOutParameterMap.put("p_provider_lname",providerVO.getProviderLname());
		inOutParameterMap.put("p_provider_suffix",providerVO.getProviderSuffix());
		inOutParameterMap.put("p_worker_id",userObject.getWorkerId());

		subProviderDAO.addNewProvider(inOutParameterMap);

		statusCode=(Integer)inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?NEW_PROVIDER_CREATED:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
		providerVO.setProviderId((Long)inOutParameterMap.get("p_provider_id_out"));
	}
	
	/*
	 * Adding New Address and  linking it to Raw Provider ID
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param providerId the provider to which the new address should be linked to
	 * @param providerAndAddressVO the value object which holds the provider address
	 * @param result the binding result to hold the results
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier  
	 */
	@Override
	public void addingAddrsAndlinking(long rawProviderAddressId, long providerId,SubProviderAndAddressVO providerAndAddressVO, 
			BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		try{
			standardizationTransactionHelper.addingAddrsAndlinkingTrns(rawProviderAddressId, providerId, providerAndAddressVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured while linking raw ({}) to the new Standard Address({})",rawProviderAddressId,providerAndAddressVO.getAddress());
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while linking raw ({}) to the new Standard Address({})",rawProviderAddressId,providerAndAddressVO.getAddress());
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize Raw Provider Address to the New Standard Provider Address, please contact helpline.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, ADD_NEW_PROVIDER_ADDRESS_AND_LINK_TO_RAW_SUCCESS_MESSAGE, bindingResult);
		}
	}
	
	@Override
	public void addingProvAddrsAndlinking(String[] rawProviderAddressIds, long providerId,SubProviderAndAddressVO providerAndAddressVO, 
			BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		try{
			standardizationTransactionHelper.addingProvAddrsAndlinkingTrns(rawProviderAddressIds, providerId, providerAndAddressVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured while linking raw ({}) to the new Standard Address({})",rawProviderAddressIds,providerAndAddressVO.getAddress());
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while linking raw ({}) to the new Standard Address({})",rawProviderAddressIds,providerAndAddressVO.getAddress());
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			@SuppressWarnings("unchecked")
			Map<Object,Object> resultMap=((Map<Object,Object>)bindingResult.getTarget());
			int statusCode =0;
			if ((resultMap.get("TRANSACTION_CODE") != null)) {
				statusCode = (int)resultMap.get("TRANSACTION_CODE");
			}
			if (statusCode == -501 && resultMap.get("TRANSACTION_RESULT").equals("TRANSACTION_FAILURE")) {
				commonExceptionHandler.inspectDBTransacionResult(-1, resultMap.get("TRANSACTION_MESSAGE").toString(), bindingResult);
			} else {
				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize Raw Provider Address to the New Standard Provider Address, please contact helpline.", bindingResult);
			}
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, ADD_NEW_PROVIDER_ADDRESS_AND_LINK_TO_RAW_SUCCESS_MESSAGE, bindingResult);
		}
	}	
	/* Adding New Provider Address to an existing Provider
	 * @param providerId the provider for which new address is created
	 * @param providerAndAddressVO the value object which holds the provider address
	 * @result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 * @return providerAddressId the newly created address ID for this provider
	 */
		@Override
		public void addingAddressToProvider(long providerId,SubProviderAndAddressVO providerAndAddressVO, 
				BindingResult bindingResult, UserObject userObject){
			String transactionMessage=null;
			Integer statusCode=null;
			Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();
			
			inOutParameterMap.put("p_provider_id",providerId);
			inOutParameterMap.put("p_county_fips",providerAndAddressVO.getCountyFips());
			inOutParameterMap.put("p_address",providerAndAddressVO.getAddress());
			inOutParameterMap.put("p_city",providerAndAddressVO.getCity());
			inOutParameterMap.put("p_state",providerAndAddressVO.getState());
			inOutParameterMap.put("p_zipcode",providerAndAddressVO.getZipcode());
			inOutParameterMap.put("p_plus4",providerAndAddressVO.getPlus4());
			inOutParameterMap.put("p_phone",providerAndAddressVO.getPhone());
			inOutParameterMap.put("p_extension",providerAndAddressVO.getExtension());
			inOutParameterMap.put("p_worker_id",userObject.getWorkerId());

			subProviderAndAddressDAO.addNewAddrs(inOutParameterMap);

			statusCode=(Integer)inOutParameterMap.get("p_code_out");
			transactionMessage=statusCode==0?ADD_NEW_ADDRESS_TO_PROVIDER:(String)inOutParameterMap.get("p_msg_out");
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
			if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
			providerAndAddressVO.setSubProviderAddressId((Long)inOutParameterMap.get("p_sub_provider_address_id_out"));
		}
	
	//SubGrid of Sub Grid of Sub Provider i.e Raw Provider Address Id. getting the Raw Provider Addresses Using Sub Provider Address Id
	@SuppressWarnings("unchecked")
	@Override
	public List<RawProviderAddressVO> getRawProvAdrsListBySubprovAdrsId(long subProviderAdrsId, BindingResult result, UserObject userObject){

		List<RawProviderAddressVO> rawProvAdrsList = new ArrayList<RawProviderAddressVO>();
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("subProviderAdrsId", subProviderAdrsId);
		map.put("rawProvAdrsListRefCursor", rawProvAdrsList);

		rawProviderAddressDAO.getRawProvAdrsListBySubprovAdrsId(map);
		rawProvAdrsList= (List<RawProviderAddressVO>) map.get("rawProvAdrsListRefCursor");
		return rawProvAdrsList;
	}

	@Override
	public void unlinkProviderAddress(String provWorkType, String commaSeparatedVals, long parentId, BindingResult bindingResult,
			UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();
		inOutParameterMap.put("p_tounlink_ids", commaSeparatedVals);
		inOutParameterMap.put("p_parent_id", parentId);
		inOutParameterMap.put("p_user_id",userObject.getWorkerId());
		if(provWorkType.equals("SubAddrs")){
			mergeAndUnlinkDAO.unlinkSubProviderAddress(inOutParameterMap);
		}else{//Raw
			mergeAndUnlinkDAO.unlinkRawProviderAndAddress(inOutParameterMap);
		}
		statusCode=(Integer)inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?UNLINK_ADDRESS_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}

	@Override
	public void mergeSubProvAddrs(String commaSeparatedVals, long selToBeMergedSubAddrs, long selToBeMergedParent,
			BindingResult bindingResult, UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_sub_prov_addrs_list", commaSeparatedVals);
		inOutParameterMap.put("p_retained_sub_prov_addrs", selToBeMergedSubAddrs);
		if (selToBeMergedParent != 0) {
			inOutParameterMap.put("p_sub_prov", selToBeMergedParent);
		}
		inOutParameterMap.put("p_user_id", userObject.getWorkerId());

		mergeAndUnlinkDAO.mergeSubProviderAddress(inOutParameterMap);
		statusCode=(Integer)inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?MERGE_ADDRESS_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}

	@Override
	public void mergeSubProviders(String commaSeparatedVals, long selToBeMergedSubProv, BindingResult bindingResult,
			UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_sub_prov_list", commaSeparatedVals);
		inOutParameterMap.put("p_retained_sub_prov", selToBeMergedSubProv);
		inOutParameterMap.put("p_user_id", userObject.getWorkerId());
		mergeAndUnlinkDAO.mergeSubProviders(inOutParameterMap);
		statusCode=(Integer)inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?MERGE_PROVIDERS_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	
	
	@Override
	public List<Map<String, String>> getActiveSubProviders(BindingResult bindingResult,UserObject userObject, String providerName) {
		
		providerName = providerName+"%";
		
		List<Map<String, String>> activeProvidersList = subProviderDAO.getActiveProviders(providerName);
	
		
		return activeProvidersList;
		
	}
	
	@Override
	public void unableStandardizeProviderRecord(long recordId,
			String effectedTable, BindingResult bindingResult,
			UserObject userObject){
		
		
		String transactionMessage = "Failed to mark as Unable to Standardize.";
		Integer statusCode = -1;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("recordId", recordId);
		
		
		if("rawProviderAddress".equals(effectedTable)){
			map.put("userId", userObject.getUserId());
			int resultCount = subProviderDAO.unableStandardizeRawProviderRecord(map);
			if(resultCount > 0 ){
				transactionMessage = "Successfully marked as Unable to Standardize.";
				statusCode = 0;
			}
		}else if("subProviderAddress".equals(effectedTable)){
			map.put("userId", userObject.getWorkerId());
			int resultCount = subProviderDAO.unableStandardizeProviderAddressRecord(map);
			if(resultCount > 0 ){
				transactionMessage = "Successfully marked as Unable to Standardize.";
				statusCode = 0;
			}
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		
	}
	
	public void addProviderDetails(
			SubProviderAndAddressVO subProviderAndAddressVO,
			BindingResult bindingResult, UserObject userObject){
		boolean isError = false; 
		try{
			standardizationTransactionHelper.addProviderDetails(subProviderAndAddressVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to create the Standard Provider Address.");
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to create the Standard Provider Address.");
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to create the Standard Provider Address, Please contact helpline.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully created the new Standard provider address", bindingResult);
		}
	}
	
	@Override
	public long createRawProviderAddress(
			SubProviderAndAddressVO providerAndAddressVO,
			BindingResult bindingResult, UserObject userObject){
			
			Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();
			inOutParameterMap.put("p_provider_fname",providerAndAddressVO.getProviderFname());
			inOutParameterMap.put("p_provider_mname",providerAndAddressVO.getProviderMname());
			inOutParameterMap.put("p_provider_lname",providerAndAddressVO.getProviderLname());
			inOutParameterMap.put("p_provider_suffix",providerAndAddressVO.getProviderSuffix());
			inOutParameterMap.put("p_county_fips",providerAndAddressVO.getCountyFips());
			inOutParameterMap.put("p_address",providerAndAddressVO.getAddress());
			inOutParameterMap.put("p_city",providerAndAddressVO.getCity());
			inOutParameterMap.put("p_state",providerAndAddressVO.getState());
			inOutParameterMap.put("p_zipcode",providerAndAddressVO.getZipcode());
			inOutParameterMap.put("p_plus4",providerAndAddressVO.getPlus4());
			inOutParameterMap.put("p_phone",providerAndAddressVO.getPhone());
			inOutParameterMap.put("p_npi",providerAndAddressVO.getProviderNPI());
			inOutParameterMap.put("p_extension",providerAndAddressVO.getExtension());
			inOutParameterMap.put("p_subprovideraddress_id",providerAndAddressVO.getSubProviderAddressId());
			inOutParameterMap.put("p_worker_id",userObject.getWorkerId());
	
			long rawProviderId = 0;
			int resultCount = subProviderAndAddressDAO.createRawProviderAddress(inOutParameterMap);
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to create the Raw Provider Address");
			}else{
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully created the Raw Provider Address", bindingResult);
				rawProviderId= Long.valueOf(((BigDecimal)inOutParameterMap.get("rawProviderId")).longValue());
			}
			
			return rawProviderId;
	
	}
	
	public void addRawAndSubProviderAddressDetails(long providerId,
			SubProviderAndAddressVO subProviderAndAddressVO,
			BindingResult bindingResult, UserObject userObject){
		boolean isError = false; 
		try{
			standardizationTransactionHelper.addRawAndSubProviderAddressDetails(providerId, subProviderAndAddressVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to create the Standard Provider Address.");
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to create the Standard Provider Address.");
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to create the Standard Provider Address, Please contact helpline.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully created the new Standard provider address", bindingResult);
		}
	}
	
	@Override
	public int checkRawProviderAddressExistance(
			SubProviderAndAddressVO  subProviderAndAddressVO){
		return subProviderAndAddressDAO.checkRawProviderAddressExistance(subProviderAndAddressVO);
	}


	@Override
	public List<RawProviderAddressVO> getRawProvAddrsNewList(BindingResult result, boolean selected, long[] selectedIds, UserObject userObject) {
		
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();


		inOutParameterMap.put("selected",selected);
		inOutParameterMap.put("selectedIds",selectedIds);
		
		List<RawProviderAddressVO> rawProviderAddressList = new ArrayList<RawProviderAddressVO>();
		rawProviderAddressList = rawProviderAddressDAO.getRawProvAddrsNewList(inOutParameterMap);
		
		for (RawProviderAddressVO curData : rawProviderAddressList) {
			if (CommonUtil.isNotNullAndEmpty(curData.getZipcode())) {
				Map<String, String> zipCodeValues = commonService.getCountyNameByZip(curData.getZipcode());
				if (zipCodeValues != null) {
					String _state = zipCodeValues.get("STATE_CD");
					String _county = zipCodeValues.get("COUNTY_FIPS_CD");
					if(CommonUtil.isNotNullAndEmpty(_state) && !"NY".equals(_state)) {
						_county = "998";
					}else if(CommonUtil.isNullOrEmpty(_state)) {
						_county = "999";
					}
					curData.setCountyFips(_county);
				}
			}
		}
		
		return rawProviderAddressList;
	}


	@Override
	public void linkRawProvidersToSubProvAddrs(String[] rawProvSelectedAddresses, long providerAddressId, BindingResult result, UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;

		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();
		
		inOutParameterMap.put("p_raw_provider_addr_id_list",String.join(",", rawProvSelectedAddresses));
		inOutParameterMap.put("p_sub_provider_address_id",providerAddressId);
		inOutParameterMap.put("p_worker_id",userObject.getWorkerId());
		
		rawProviderAddressDAO.linkRawProvAddressesToSubProvAddrs(inOutParameterMap);

		statusCode=(Integer)inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?LINK_RAW_TO_PROVIDER_ADDRESS_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, result);
		if(statusCode != 0)throw new DataRetrievalFailureException(transactionMessage);
		
	}
	
	@Override
	public List<JSONObject> getMasterProvRecords(){
		return rawProviderAddressDAO.getMasterProvRecords();
	}
	
	@Override
	public JSONObject getMasterProvData(String providerId) {
		return rawProviderAddressDAO.getMasterProvData(providerId);
	}
	
}
