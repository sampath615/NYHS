package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityManualReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class FacilityManualReviewService {
	@Autowired
	FacilityManualReviewDAO facilityManualReviewDAO;
	@Autowired
	CommonExceptionHandler commonExceptionHandler;
	private static final String MATCH_RAW_FACILITY_SUCCESS_MESSAGE="Raw facility has been Standardized";
	private static final String UNMATCH_RAW_FACILITY_SUCCESS_MESSAGE="Raw facility has been moved from Clerical to Unstandardized list";
	public void matchRawFacility(long newRawFacilityId,
			long matchingRawFacilityId, BindingResult bindingResult,
			UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParamaterMap;

		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("p_raw_facility_id", newRawFacilityId);
		inOutParamaterMap.put("p_match_raw_facility_id", matchingRawFacilityId);
		inOutParamaterMap.put("p_worker_id", userObject.getWorkerId());
		facilityManualReviewDAO.matchRawFacility(inOutParamaterMap);
		statusCode=(Integer)inOutParamaterMap.get("statusCode");
		transactionMessage=statusCode==0?MATCH_RAW_FACILITY_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("statusMessage");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	public void unMatchRawFacility(long newRawFacilityId,
			BindingResult bindingResult,
			UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParamaterMap;

		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("p_raw_facility_id", newRawFacilityId);
		inOutParamaterMap.put("p_worker_id", userObject.getWorkerId());
		facilityManualReviewDAO.unMatchRawFacility(inOutParamaterMap);
		statusCode=(Integer)inOutParamaterMap.get("statusCode");
		transactionMessage=statusCode==0?UNMATCH_RAW_FACILITY_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("statusMessage");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	public List<MasterFacilityDetailsVO> getFacilityDetailsForManualReview(
			long rawFacilityId, BindingResult bindingResult, UserObject userObject) {
		Map<Object,Object> inOutParamaterMap;
		List<MasterFacilityDetailsVO> facilityDetailsForManualReview;
		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("p_raw_facility_id", rawFacilityId);
		facilityManualReviewDAO.getFacilityDetailsForManualReview(inOutParamaterMap);
		facilityDetailsForManualReview=(List<MasterFacilityDetailsVO>)inOutParamaterMap.get("p_facilities_out");
		return facilityDetailsForManualReview;
	}
}
