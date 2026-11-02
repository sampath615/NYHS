package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderDetailsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class SearchManageProviderService {

	private final ProviderDetailsDAO providerDetailsDAO;
	/*
	 * Reference to custom exception handler by means of dependency injection 
	 * which logs and email error messages
	 */

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
	public List<ProviderDetailsVO> getProviderDetailsByProvider(SubProviderVO providerVO , BindingResult bindingResult, UserObject userObject){
		
		List<ProviderDetailsVO> providerDetailsList = new ArrayList<>();
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("p_fname",providerVO.getProviderFname());
		inputOutputParameterMap.put("p_lname",providerVO.getProviderLname());
		inputOutputParameterMap.put("p_mname",providerVO.getProviderMname());
		inputOutputParameterMap.put("p_npi",providerVO.getProviderNPI());
		inputOutputParameterMap.put("p_sub_prov_out",providerDetailsList);

		providerDetailsDAO.getProviderDetailsByProvider(inputOutputParameterMap);
		
		providerDetailsList= (List<ProviderDetailsVO>) inputOutputParameterMap.get("p_sub_prov_out");
		Collections.sort(providerDetailsList,new ProviderDetailsVO());
		return providerDetailsList;
		
		
	}
	
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
	public List<ProviderDetailsVO> getProviderDetailsByProviderAddress(SubProviderAndAddressVO subProviderAndAddressVO , BindingResult result, UserObject userObject){
		List<ProviderDetailsVO> providerDetailsList = new ArrayList<>();
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("p_address",subProviderAndAddressVO.getAddress());
		inputOutputParameterMap.put("p_city",subProviderAndAddressVO.getCity());
		inputOutputParameterMap.put("p_state",subProviderAndAddressVO.getState());
		inputOutputParameterMap.put("p_zipcode",subProviderAndAddressVO.getZipcode());
		inputOutputParameterMap.put("p_county_fips",subProviderAndAddressVO.getCountyFips());
		inputOutputParameterMap.put("p_phone",subProviderAndAddressVO.getPhone());
		inputOutputParameterMap.put("p_sub_prov_out",providerDetailsList);

		providerDetailsDAO.getProviderDetailsByProviderAddress(inputOutputParameterMap);
		
		providerDetailsList= (List<ProviderDetailsVO>) inputOutputParameterMap.get("p_sub_prov_out");
		Collections.sort(providerDetailsList,new ProviderDetailsVO());
		return providerDetailsList;
		
	}
	
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
	public List<ProviderDetailsVO> getProviderDetailsByRawProviderAddress(RawProviderAddressVO rawProviderAddressVO , BindingResult result, UserObject userObject){
		List<ProviderDetailsVO> providerDetailsList = new ArrayList<>();
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("p_raw_fname",rawProviderAddressVO.getRawProviderFname());
		inputOutputParameterMap.put("p_raw_lname",rawProviderAddressVO.getRawProviderLname());
		inputOutputParameterMap.put("p_raw_mname",rawProviderAddressVO.getRawProviderMname());
		inputOutputParameterMap.put("p_raw_address",rawProviderAddressVO.getAddress());
		inputOutputParameterMap.put("p_raw_city",rawProviderAddressVO.getCity());
		inputOutputParameterMap.put("p_raw_state",rawProviderAddressVO.getState());
		inputOutputParameterMap.put("p_raw_zipcode",rawProviderAddressVO.getZipcode());
		inputOutputParameterMap.put("p_raw_county_fips",rawProviderAddressVO.getCountyFips());
		inputOutputParameterMap.put("p_raw_phone",rawProviderAddressVO.getPhone());
		inputOutputParameterMap.put("p_raw_npi",rawProviderAddressVO.getRawProviderNPI());
		inputOutputParameterMap.put("p_sub_prov_out",providerDetailsList);

		providerDetailsDAO.getProviderDetailsByRawProviderAddress(inputOutputParameterMap);
		
		providerDetailsList= (List<ProviderDetailsVO>) inputOutputParameterMap.get("p_sub_prov_out");
		Collections.sort(providerDetailsList,new ProviderDetailsVO());
		return providerDetailsList;
		
	}
	
	public List<RawProviderAddressVO> getRawProviderDetails(
			RawProviderAddressVO rawProviderAddressVO,
			BindingResult dummyBindingResult, UserObject userObject){
		
		Map<Object, Object> map = new HashMap<>();

		if(CommonUtil.isNotNullAndEmpty(rawProviderAddressVO.getRawProviderFname())){
			map.put("p_provider_fname",rawProviderAddressVO.getRawProviderFname().replaceAll("\\*", "%"));
		}
		
		if(CommonUtil.isNotNullAndEmpty(rawProviderAddressVO.getRawProviderMname())){
			map.put("p_provider_mname",rawProviderAddressVO.getRawProviderMname().replaceAll("\\*", "%"));
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawProviderAddressVO.getRawProviderLname())){
			map.put("p_provider_lname",rawProviderAddressVO.getRawProviderLname().replaceAll("\\*", "%"));
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawProviderAddressVO.getCountyFips())){
			map.put("p_county_fips",rawProviderAddressVO.getCountyFips());
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawProviderAddressVO.getAddress())){
			map.put("p_address",rawProviderAddressVO.getAddress().replaceAll("\\*", "%"));
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawProviderAddressVO.getCity())){
			map.put("p_city",rawProviderAddressVO.getCity().replaceAll("\\*", "%"));
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawProviderAddressVO.getState())){
			map.put("p_state",rawProviderAddressVO.getState());
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawProviderAddressVO.getZipcode())){
			map.put("p_zipcode",rawProviderAddressVO.getZipcode().replaceAll("\\*", "%"));
		}
		
		String phoneNumber = rawProviderAddressVO.getPhone();
		if("xxx-xxx-xxxx".equals(phoneNumber)){
			return null;
		}
		else if(phoneNumber != null && (phoneNumber.indexOf('x') != -1 || phoneNumber.indexOf('-') != -1)){
			phoneNumber = phoneNumber.replaceAll("-", "").replaceAll("x", "?").replaceAll("X", "?").replaceAll("\\*", "%");
		}
		
		if(CommonUtil.isNotNullAndEmpty(phoneNumber)){
			map.put("p_phone",phoneNumber.replaceAll("\\*", "%"));
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawProviderAddressVO.getRawProviderNPI())){
			map.put("p_provider_npi",rawProviderAddressVO.getRawProviderNPI().replaceAll("\\*", "%"));
		}
		
		
		return providerDetailsDAO.getRawProviderDetails(map);

	}
}
