package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderManualReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ProviderManualReviewService {
	@Autowired
	ProviderManualReviewDAO providerManualReviewDAO;
	@Autowired
	CommonExceptionHandler commonExceptionHandler;
	private static final String MATCH_RAW_FACILITY_SUCCESS_MESSAGE="Raw provider address has been standardized";
	private static final String UNMATCH_RAW_FACILITY_SUCCESS_MESSAGE="Raw provider address has been moved from Clerical to Unstandardized list";
	public void matchRawProviderAddress(long newRawProviderAddressId,
			long matchingRawProviderAddressId, BindingResult bindingResult,
			UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParamaterMap;

		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("p_raw_prov_addr_id", newRawProviderAddressId);
		inOutParamaterMap.put("p_mtch_raw_prov_addr_id", matchingRawProviderAddressId);
		inOutParamaterMap.put("p_worker_id", userObject.getWorkerId());
		providerManualReviewDAO.matchRawProviderAddress(inOutParamaterMap);
		statusCode=(Integer)inOutParamaterMap.get("statusCode");
		transactionMessage=statusCode==0?MATCH_RAW_FACILITY_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("statusMessage");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	public void unMatchRawProviderAddress(long newRawProviderAddressId,
			BindingResult bindingResult,
			UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParamaterMap;

		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("p_raw_prov_addr_id", newRawProviderAddressId);
		inOutParamaterMap.put("p_worker_id", userObject.getWorkerId());
		providerManualReviewDAO.unMatchRawProviderAddress(inOutParamaterMap);
		statusCode=(Integer)inOutParamaterMap.get("statusCode");
		transactionMessage=statusCode==0?UNMATCH_RAW_FACILITY_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("statusMessage");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	public List<ProviderDetailsVO> getPotentialProviderDetailsListByRawProvider(
			long rawProviderAddressId, BindingResult bindingResult, UserObject userObject) {
		Map<Object,Object> inOutParamaterMap;
		List<ProviderDetailsVO> providerDetailsForManualReview;
		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("p_raw_provider_address_id", rawProviderAddressId);
		providerManualReviewDAO.getProviderDetailsForManualReview(inOutParamaterMap);
		providerDetailsForManualReview=(List<ProviderDetailsVO>)inOutParamaterMap.get("p_providers_out");
		return providerDetailsForManualReview;
	}
	
	
	public List<RawProviderAddressVO> getClericalReviewRawProviderAddressDetailsList(BindingResult bindingResult, UserObject userObject){
		List<RawProviderAddressVO> rawSubFacilityList = new ArrayList<RawProviderAddressVO>();
		
		rawSubFacilityList= providerManualReviewDAO.getClericalReviewRawProviderAddressList();
		
		return rawSubFacilityList;
		

	}
}
