package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderDetailsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.RawProviderAddressDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

/**
 * 
 * @author axp21
 * The implementation class for UnStandardizedProviderService for unstandardized provider operations
 * Annotated as a spring managed service bean 
 */
@RequiredArgsConstructor
@Service
public class UnStandardizedProviderService {
	/*
	 * Reference to Log
	 */
	private static final Logger log = LoggerFactory.getLogger(UnStandardizedProviderService.class);


	/*
	 * Reference to custom exception handler by means of dependency injection 
	 * which logs and email error messages
	 */
	private final CommonExceptionHandler commonExceptionHandler;

	/*
	 * Reference to RawProviderAddressDAO by means of dependency injection    
	 */
	private final RawProviderAddressDAO rawProviderAddressDAO;
	
	private final ProviderDetailsDAO providerDetailsDAO;
	
	private final ProviderStandardizationTransactionHelper standardizationTransactionHelper;
	
	private final CommonService commonService;
	
	/*
	 * Success message when a provider is flagged as facility 
	 */
	private final static String FLAG_PROVIDER_AS_FACILITY_SUCCESS_MESSAGE="Provider has been flagged as facility";
	/*
	 * 
	 */
	private final static String ADD_NEW_PROVIDER_AND_LINK_ORPHAN_ADDRESS_SUCCESS_MESSAGE="Orphan address has been linked to the newly created provider";
	/*
	 * Flag the provider data as a facility and copy it to facility data.
	 * @param providerId Unique identifier of the provider data to be moved
	 * @return success or failure message based on the transaction   
	 */
	public void flagProviderDataAsFacilty(long providerId,BindingResult bindingResult, UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParamaterMap;
		
		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("providerId", providerId);
		inOutParamaterMap.put("workerId", userObject.getWorkerId());
		inOutParamaterMap.put("statusCode", Integer.valueOf(1));
		inOutParamaterMap.put("statusMessage", "");
		
		rawProviderAddressDAO.flagProviderDataAsFacilty(inOutParamaterMap);
		
		statusCode=(Integer)inOutParamaterMap.get("statusCode");
		transactionMessage=statusCode==0?FLAG_PROVIDER_AS_FACILITY_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("statusMessage");
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	
	
	/*
	 * Adding New Provider and linking it to Provider Address
	 * @param providerAddressId the provider Address which needs to be linked
	 * @param providerVO the value object which holds the provider details
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	public void addNewProviderAndLinkingOrphanAddrs(long providerAddressId, SubProviderVO providerVO, BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		try{
			standardizationTransactionHelper.addProviderAndLinkingOrphanAddrs(providerAddressId, providerVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured while linking Orphan Addrs({}) to the new Provider({})",providerAddressId,providerVO.getProviderLname());
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while linking Orphan Addrs({}) to the new Provider({})",providerAddressId,providerVO.getProviderLname());
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize orphan Provider Address to the new Provider", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, ADD_NEW_PROVIDER_AND_LINK_ORPHAN_ADDRESS_SUCCESS_MESSAGE, bindingResult);
		}
	}

	/**
	 * Returns the complete Provider list containing the First name,
	 * Last name, middle name and the count of Address list associated to the Provider
	 * 
	 * This data will render depends on the search critera     
	 *
	 * @param  subProviderAndAddressVO  Provider object related beans 
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
	 * @param  bindingResult  using to store error related information
	 * @param userObject, sessionID  used to track Exception related information 
	 * 
	 * @return      List of Providers
	 */
	@SuppressWarnings("unchecked")
    public List<ProviderDetailsVO> getSrchProviderDetailsWithAddressList(SubProviderAndAddressVO subProviderAndAddressVO ,int searchFor, String searchFromNPI, BindingResult bindingResult, UserObject userObject){
    	
    	List<ProviderDetailsVO> providerDetailsList = new ArrayList<>();
		Map<Object, Object> map = new HashMap<>();

		map.put("p_provider_fname",subProviderAndAddressVO.getProviderFname());
		map.put("p_provider_mname",subProviderAndAddressVO.getProviderMname());
		map.put("p_provider_lname",subProviderAndAddressVO.getProviderLname());
		map.put("p_county_fips",subProviderAndAddressVO.getCountyFips());
		map.put("p_address",subProviderAndAddressVO.getAddress());
		map.put("p_city",subProviderAndAddressVO.getCity());
		map.put("p_state",subProviderAndAddressVO.getState());
		map.put("p_zipcode",subProviderAndAddressVO.getZipcode());
		map.put("p_phone",subProviderAndAddressVO.getPhone());
		map.put("p_sub_prov_out",providerDetailsList);
		map.put("p_provider_npi",subProviderAndAddressVO.getProviderNPI());
		
		if (CommonUtil.isNotNullAndEmpty(searchFromNPI) && searchFromNPI.equals("N")) {
			providerDetailsDAO.getSrchProviderDetailsWithAddressList(map);
			providerDetailsList= (List<ProviderDetailsVO>) map.get("p_sub_prov_out");
		} else if (CommonUtil.isNotNullAndEmpty(searchFromNPI) && searchFromNPI.equals("Y")) {
			providerDetailsList = getSrchProviderDetailsFromNPIRegistry(subProviderAndAddressVO);
		}
		
		if(searchFor==1){
			providerDetailsList.remove(new ProviderDetailsVO(-999));
		}
		Collections.sort(providerDetailsList,new ProviderDetailsVO());
		
		return providerDetailsList;
    }
	
	public List<ProviderDetailsVO> getSrchProviderDetailsFromNPIRegistry(SubProviderAndAddressVO subProviderAndAddressVO){
		
		if (CommonUtil.isNotNullAndEmpty(subProviderAndAddressVO.getProviderNPI()) && subProviderAndAddressVO.getProviderNPI().length() == 10) {
			subProviderAndAddressVO.setProviderFname("");
			subProviderAndAddressVO.setProviderLname("");
			subProviderAndAddressVO.setCity("");
			subProviderAndAddressVO.setZipcode("");
			subProviderAndAddressVO.setState("");
		}
		
		List<ProviderDetailsVO> providerDetailsList = new ArrayList<>();
		
//		StringBuilder s = new StringBuilder("https://npiregistry.cms.hhs.gov/api/?number="+subProviderAndAddressVO.getProviderNPI()
		StringBuilder s = new StringBuilder("http://sgwout.health.state.ny.us:7071/cms_nppes_rest_api/api/?number="+subProviderAndAddressVO.getProviderNPI()
						+"&enumeration_type=&taxonomy_description=&first_name=").append(subProviderAndAddressVO.getProviderFname())
						.append("&use_first_name_alias=&last_name=").append(subProviderAndAddressVO.getProviderLname())
						.append("&organization_name=&address_purpose=&city=").append(subProviderAndAddressVO.getCity())
						.append("&state=").append(subProviderAndAddressVO.getState())
						.append("&postal_code=").append(subProviderAndAddressVO.getZipcode())
						.append("&country_code=&limit=&skip=&version=2.1");
		String urlString = s.toString().replaceAll(" ", "%20");
		
		JSONParser parser = new JSONParser();
		
		String response;
		try {
			response = readUrl(urlString);
			
			Object obj = parser.parse(response);
			JSONObject npiData = (JSONObject)obj;
			
			if(npiData.get("results") != null) {
				
				JSONArray results = (JSONArray)npiData.get("results");
				
				Iterator i = results.iterator();
				
				while(i.hasNext()) {
				 
					JSONObject tableRefData = (JSONObject)i.next();
					
					ProviderDetailsVO provDetailsFromRegistry = new ProviderDetailsVO();
					List<SubProviderAddressVO> subProviderAddressesList = new ArrayList<>();
					
					String FIRST_NAME_JSON = "";
					String LAST_NAME_JSON = ""; 
					String MIDDLE_NAME_JSON = "";
					String SUFFIX_JSON = "";
					String ADDRESS_JSON = "";
					String CITY_JSON = "";
					String STATE_JSON = "";
					String ZIP_JSON = "";
					String ADDRESS_TYPE_JSON = "";
					String PHONE_JSON = "";
					Long NPI_JSON = 0l;
					String COUNTY_JSON = "";
					String ZIPPLUS4_JSON = "";
					String SPECIALITY_JSON = "";
					
					if(tableRefData.get("number")!= null) {
						NPI_JSON = (Long)tableRefData.get("number");
						provDetailsFromRegistry.setProviderNPI(String.valueOf(NPI_JSON));
						provDetailsFromRegistry.setProviderId(NPI_JSON);
					}
					
					if(tableRefData.get("basic") != null) {
						
						JSONObject basicInformation = (JSONObject)tableRefData.get("basic") ;
						
						if(basicInformation.get("first_name")!= null) {
							FIRST_NAME_JSON = ((String)basicInformation.get("first_name")).replaceAll("'", "''");
							provDetailsFromRegistry.setProviderFname(FIRST_NAME_JSON);
						}
						if(basicInformation.get("last_name")!= null) {
							LAST_NAME_JSON = ((String)basicInformation.get("last_name")).replaceAll("'", "''");
							provDetailsFromRegistry.setProviderLname(LAST_NAME_JSON);
						}
						
						if(basicInformation.get("middle_name")!= null) {
							MIDDLE_NAME_JSON = ((String)basicInformation.get("middle_name")).replaceAll("'", "''");
							provDetailsFromRegistry.setProviderMname(MIDDLE_NAME_JSON);
						}
						
						provDetailsFromRegistry.setProviderFullName(LAST_NAME_JSON + " " + FIRST_NAME_JSON + " " + MIDDLE_NAME_JSON);
						provDetailsFromRegistry.setFromNPIRegistry(true);

					}
					
					if(tableRefData.get("taxonomies") != null) {
						
						JSONArray taxonomiesInfo = (JSONArray)tableRefData.get("taxonomies") ;
						
						for (Object object : taxonomiesInfo) {
							JSONObject taxonomiesObj= (JSONObject)object ;
							
							if(taxonomiesObj.get("desc")!= null) {
								SPECIALITY_JSON = (String)taxonomiesObj.get("desc");
								if (SPECIALITY_JSON.contains("'")) {
									SPECIALITY_JSON = SPECIALITY_JSON.replaceAll("'", "\\'||chr(39)||\\'");
								}
								provDetailsFromRegistry.setProviderSpeciality(SPECIALITY_JSON);
							}
						}
					}
					
					if(tableRefData.get("addresses") != null) {
						
						JSONArray addressInfromation = (JSONArray)tableRefData.get("addresses") ;
						subProviderAddressesList = new ArrayList<SubProviderAddressVO>();
						int addressNo = 0;
						for (Object object : addressInfromation) {
							JSONObject addressData = (JSONObject)object;
							
							if(addressData.get("address_purpose")!= null) {
								ADDRESS_TYPE_JSON = (String)addressData.get("address_purpose");
							}
							
							if (!"".equals(ADDRESS_TYPE_JSON) && ADDRESS_TYPE_JSON.equalsIgnoreCase("LOCATION")) {
								SubProviderAddressVO subProviderAddresses = new SubProviderAddressVO();
								List<String> phoneList = new ArrayList<>();
								
						//		subProviderAddresses.setSubProviderAddressId(++addressNo);
								subProviderAddresses.setSubProviderAddressId(0);
								if(addressData.get("address_1")!= null) {
									ADDRESS_JSON = ((String)addressData.get("address_1")).replaceAll("'", "''").replaceAll("&", "chr(38)") ;
								}
								
								String address2 = (String)addressData.get("address_2");
								if(address2 != null && !"".equals(address2.trim())) {
									ADDRESS_JSON = ADDRESS_JSON +", "+address2.replaceAll("'", "''").replaceAll("&", "chr(38)");
								}
								subProviderAddresses.setAddress(ADDRESS_JSON);
								
								if(addressData.get("city")!= null) {
									CITY_JSON = (String)addressData.get("city");
									subProviderAddresses.setCity(CITY_JSON);
								}
								
								if(addressData.get("state")!= null) {
									STATE_JSON = (String)addressData.get("state");
									subProviderAddresses.setState(STATE_JSON);
								}
								
								if(addressData.get("postal_code")!= null) {
									ZIP_JSON = (String)addressData.get("postal_code");
									subProviderAddresses.setZipcode(ZIP_JSON.substring(0,5));
									if (ZIP_JSON.length() == 9) {
										subProviderAddresses.setPlus4(ZIP_JSON.substring(5,9));
									}
								}
								
								if(addressData.get("county")== null && CommonUtil.isNotNullAndEmpty(ZIP_JSON)) {
									Map<String, String> zipCodeValues = commonService.getCountyNameByZip(subProviderAddresses.getZipcode());
									if (zipCodeValues != null) {
										String _state = zipCodeValues.get("STATE_CD");
										String _county = zipCodeValues.get("COUNTY_FIPS_CD");
										if(CommonUtil.isNotNullAndEmpty(_state) && !"NY".equals(_state)) {
											_county = "998";
										}else if(CommonUtil.isNullOrEmpty(_state)) {
											_county = "999";
										}
										subProviderAddresses.setCountyFips(_county);
									}
								}
								
								if(addressData.get("telephone_number")!= null) {
									PHONE_JSON = (String)addressData.get("telephone_number");
									phoneList.add(PHONE_JSON);
									subProviderAddresses.setPhone(phoneList);
								}
								
								subProviderAddresses.setProviderId(NPI_JSON);
								subProviderAddressesList.add(subProviderAddresses);
							}
						}
						provDetailsFromRegistry.setSubProviderAddresses(subProviderAddressesList);
					}
					
					providerDetailsList.add(provDetailsFromRegistry);
				}	
			}			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return providerDetailsList;
	}
	
	private static String readUrl(String urlString) throws Exception {
		URL url = new URL(urlString);

		// Open a connection(?) on the URL(??) and cast the response(???)
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// Now it's "open", we can set the request method, headers etc.
		connection.setRequestProperty("accept", "application/json");

		// This line makes the request
		InputStream responseStream = connection.getInputStream();
		
		String text = IOUtils.toString(responseStream, StandardCharsets.UTF_8.name());
		
		return text;
	}


	public void flagSelectedRawProviderAsFacilty(String[] rawProvAddressIds, BindingResult result, UserObject userObject) {
		
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParamaterMap;
		
		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("p_raw_provider_addr_id_list", String.join(",", rawProvAddressIds));
		inOutParamaterMap.put("p_user_id", userObject.getWorkerId());
		inOutParamaterMap.put("p_code_out", Integer.valueOf(1));
		inOutParamaterMap.put("p_msg_out", "");
		
		rawProviderAddressDAO.flagSelectedRawProviderAsFacilty(inOutParamaterMap);
		
		statusCode=(Integer)inOutParamaterMap.get("p_code_out");
		transactionMessage=statusCode==0?FLAG_PROVIDER_AS_FACILITY_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("p_msg_out");
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, result);
		
		
	}

}
