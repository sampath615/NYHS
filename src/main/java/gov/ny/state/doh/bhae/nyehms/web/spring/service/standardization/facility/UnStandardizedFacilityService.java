package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.MasterFacilityDetailsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.RawSubFacilityDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/*
 * The service implementation class for facility operations
 * This class is annotated as @Service in order to load as a Spring managed bean
 */
@RequiredArgsConstructor
@Service
public class UnStandardizedFacilityService {
	/*
	 * Reference to Log
	 */
	private static final Logger log = LoggerFactory.getLogger(UnStandardizedFacilityService.class);
	/*
	 * Reference to custom exception handler by means of dependency injection 
	 * which logs and email error messages
	 */
	private final CommonExceptionHandler commonExceptionHandler;

	/*
	 * Reference to RawProviderAddressDAO by means of dependency injection    
	 */
	private final RawSubFacilityDAO rawSubFacilityDAO;
	/*
	 * Success message when a facility is flagged as provider 
	 */
	private final static String FLAG_FACILITY_AS_PROVIDER_SUCCESS_MESSAGE="Facility has been flagged as provider";
	/*
	 * Reference to MasterFacilityDetailsDAO by means of dependency injection    
	 */
	private final MasterFacilityDetailsDAO masterFacilityDetailsDAO;
	/*
	 * Flag the facility data as a provider and copy it to facility data.
	 * @param facilityId Unique identifier of the facility data to be moved
	 * @return success or failure message based on the transaction   
	 */
	private final FacilityStandardizationTransactionHelper facilityStandardizationTransactionHelper;
	private static final String STANDARDIZE_BY_CREATING_MASTER_AND_SUB_FACILITIES_SUCCESS_MESSAGE="The facilities have been created and the raw sub facility has been standardized";
	/*
	 * Move the facility data and copy it to provider data.
	 * @param facilityId Unique identifier of the facility data to be moved
	 * @return success or failure message based on the transaction   
	 */
	public void flagFacilityAsProvider(long facilityId,BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParamaterMap;

		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("facilityId", facilityId);
		inOutParamaterMap.put("workerId", userObject.getWorkerId());
		rawSubFacilityDAO.flagFacilityAsProvider(inOutParamaterMap);
		statusCode=(Integer)inOutParamaterMap.get("statusCode");
		transactionMessage=statusCode==0?FLAG_FACILITY_AS_PROVIDER_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("statusMessage");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	/**
	 * Get the facility and sub facility addresses based on the search criteria.
	 * @param subFacilityVO the model which holds the search attribute values
	 * @param searchFor this parameter gives information about the page which 
	 * requested the facility details search. Based on this parameter value, we need to
	 * filter the orphan addresses from the search result as follows
	 * If searchfor is passed from the orphan address page where we
	 * dont need to show the Unstandardized facility(the imaginary parent under which 
	 * orphan addresses are grouped and shown) in the facility search results because 
	 * an orphan address cannot be associated to this Unstandardized facility.
	 * If searchFor is passed from the raw address page, we 
	 * need to show the Unstandardized facility(the imaginary parent under which 
	 * orphan addresses are grouped and shown) in the facility search results.
	 * @param bindingResult the BindingResult reference
	 * @param userObject UserObject reference      
	 * @return list of  facility and sub facility addresses encapsulated in a GridObject   
	 */
	public List<MasterFacilityDetailsVO> getMasterFacilitiesWithSubFacilities(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO , String searchFor,BindingResult result, UserObject userObject){
		List<MasterFacilityDetailsVO> masterFacilitiesWithSubFacilities = new ArrayList<>();

		Map<Object, Object> inOutParameterMap = new HashMap<>();
		//TODO: Need to remove the facility type code
		inOutParameterMap.put("p_master_fac_name",masterFacilityVO.getMasterFacilityName());
		inOutParameterMap.put("p_sub_fac_name", subFacilityVO.getFacilityName());
		inOutParameterMap.put("p_sub_fac_npi", subFacilityVO.getFacilityNpi());
		inOutParameterMap.put("p_sub_fac_dept",subFacilityVO.getFacilityDepartment());
		inOutParameterMap.put("p_county_fips",subFacilityVO.getCountyFips());
		inOutParameterMap.put("p_address",subFacilityVO.getAddress());
		inOutParameterMap.put("p_city",subFacilityVO.getCity());
		inOutParameterMap.put("p_state",subFacilityVO.getState());
		inOutParameterMap.put("p_zipcode",subFacilityVO.getZipcode());
		inOutParameterMap.put("p_plus4",subFacilityVO.getPlus4());
		inOutParameterMap.put("p_phone",CommonUtil.getUnMaskedPhoneNumber(subFacilityVO.getPhone()));
		inOutParameterMap.put("p_sub_prov_out",masterFacilitiesWithSubFacilities);
		masterFacilityDetailsDAO.getMasterFacilitiesWithSubFacilities(inOutParameterMap);
		masterFacilitiesWithSubFacilities= (List<MasterFacilityDetailsVO>) inOutParameterMap.get("p_sub_prov_out");
		if("orphanFacility".equals(searchFor)){
			masterFacilitiesWithSubFacilities.remove(new MasterFacilityDetailsVO(-999)); 
		}
		Collections.sort(masterFacilitiesWithSubFacilities, new MasterFacilityDetailsVO());

		return masterFacilitiesWithSubFacilities;
	}
	/*
	 * The method to standardize a raw sub facility by creating a new master facility
	 * and sub facility
	 * @param masterFacilityVO the master facility details
	 * @param subFacilityVO the sub facility details
	 * @param rawSubFacilityId the raw sub facility to be standardized
	 * @param request the HTTPRequest reference
	 * @param bindingResult the Spring MVC BindingResult reference
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier 
	 */
	public void standardizeRawByCreatingMasterAndSubFacilities(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO,long rawSubFacilityId, BindingResult bindingResult,UserObject userObject) {
		
		boolean isError = false; 
		try{
			facilityStandardizationTransactionHelper.standardizeRawByCreatingMasterAndSubFacilities(masterFacilityVO, subFacilityVO, rawSubFacilityId, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured occured while Standardizing Raw Facility to the new Master and Sub Facility details");
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured occured while Standardizing Raw Facility to the new Master and Sub Facility details");
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Exception occured occured while Standardizing Raw Facility to the new Master and Sub Facility details, Please contact helpline.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, STANDARDIZE_BY_CREATING_MASTER_AND_SUB_FACILITIES_SUCCESS_MESSAGE, bindingResult);
		}
	}

}
