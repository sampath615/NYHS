package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.MasterFacilityDetailsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class SearchManageFacilityServiceImpl implements SearchManageFacilityService{
	/*
	 * Reference to custom exception handler by means of dependency injection 
	 * which logs and email error messages
	 */
	/*
	 * Reference to MasterFacilityDetailsDAO by Spring dependency injection
	 */
	@Autowired
	MasterFacilityDetailsDAO masterFacilityDetailsDAO;
	

	/**
	 * The method for getting Master facilities, their sub facilities and nested raw facilities
	 * by passing the Master facility search criteria
	 * @param masterFacilityVO object to hold the MasterFacility search criteria 
	 * @param bindingResult the BindingResult reference
	 * @param userObject the UserObject reference
	 * @return List<MasterFacilityDetailsVO> list of MasterFacilityDetailsVO instances   
	 */
	@Override
	public List<MasterFacilityDetailsVO> getFacilityDetailsByMasterFacility(
			MasterFacilityVO masterFacilityVO, BindingResult bindingResult,
			UserObject userObject) {
		List<MasterFacilityDetailsVO> masterFacilitiesWithSubFacilities = new ArrayList<MasterFacilityDetailsVO>();
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();
		
		//TODO: Need to remove Facility type code
		inOutParameterMap.put("p_master_fac_name",masterFacilityVO.getMasterFacilityName());
		
		masterFacilityDetailsDAO.getFacilityDetailsByMasterFacility(inOutParameterMap);

		masterFacilitiesWithSubFacilities=(List<MasterFacilityDetailsVO>)inOutParameterMap.get("p_facilities_out");
		Collections.sort(masterFacilitiesWithSubFacilities, new MasterFacilityDetailsVO());
		return masterFacilitiesWithSubFacilities;
	}
	/**
	 * The method for getting Master facilities, their sub facilities and nested raw facilities
	 * by passing the Sub facility search criteria
	 * @param subFacilityVO object to hold the SubFacility search criteria 
	 * @param bindingResult the BindingResult reference
	 * @param userObject the UserObject reference
	 * @return List<MasterFacilityDetailsVO> list of MasterFacilityDetailsVO instances   
	 */
	@Override
	public List<MasterFacilityDetailsVO> getFacilityDetailsBySubFacility(SubFacilityVO subFacilityVO,
			BindingResult bindingResult, UserObject userObject) {
		List<MasterFacilityDetailsVO> masterFacilitiesWithSubFacilities = new ArrayList<MasterFacilityDetailsVO>();
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_facname",subFacilityVO.getFacilityName());
		inOutParameterMap.put("p_fac_dept",subFacilityVO.getFacilityDepartment()); 
		inOutParameterMap.put("p_county",subFacilityVO.getCountyFips());
		inOutParameterMap.put("p_address",subFacilityVO.getAddress());
		inOutParameterMap.put("p_city",subFacilityVO.getCity());
		inOutParameterMap.put("p_state",subFacilityVO.getState());
		inOutParameterMap.put("p_zipcode",subFacilityVO.getZipcode());
		inOutParameterMap.put("p_phone",CommonUtil.getUnMaskedPhoneNumber(subFacilityVO.getPhone()));
		inOutParameterMap.put("p_npi",subFacilityVO.getFacilityNpi());
		masterFacilityDetailsDAO.getFacilityDetailsBySubFacility(inOutParameterMap);
		masterFacilitiesWithSubFacilities=(List<MasterFacilityDetailsVO>)inOutParameterMap.get("p_facilities_out");
		Collections.sort(masterFacilitiesWithSubFacilities, new MasterFacilityDetailsVO());
		return masterFacilitiesWithSubFacilities;
	}
	
	@Override
	public List<RawSubFacilityVO> getFacilityDetailsForAssignmentByRawFacility(
			RawSubFacilityVO rawSubFacilityVO, BindingResult bindingResult,
			UserObject userObject) {

		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		if(CommonUtil.isNotNullAndEmpty(rawSubFacilityVO.getFacilityName())){
			inOutParameterMap.put("p_facname",rawSubFacilityVO.getFacilityName().replaceAll("\\*", "%"));
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawSubFacilityVO.getFacilityDepartment())){
			inOutParameterMap.put("p_fac_dept",rawSubFacilityVO.getFacilityDepartment().replaceAll("\\*", "%"));
		}
		
		
		
		if(CommonUtil.isNotNullAndEmpty(rawSubFacilityVO.getCountyFips())){
			inOutParameterMap.put("p_county",rawSubFacilityVO.getCountyFips());
		}
		
		if(CommonUtil.isNotNullAndEmpty(rawSubFacilityVO.getAddress())){
			inOutParameterMap.put("p_address",rawSubFacilityVO.getAddress().replaceAll("\\*", "%"));
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawSubFacilityVO.getCity())){
			inOutParameterMap.put("p_city",rawSubFacilityVO.getCity().replaceAll("\\*", "%"));
		}
		
		
		if(CommonUtil.isNotNullAndEmpty(rawSubFacilityVO.getState())){
			inOutParameterMap.put("p_state",rawSubFacilityVO.getState());
		}
		
		if(CommonUtil.isNotNullAndEmpty(rawSubFacilityVO.getZipcode())){
			inOutParameterMap.put("p_zipcode",rawSubFacilityVO.getZipcode().replaceAll("\\*", "%"));
		}
		
		
		String phoneNumber = rawSubFacilityVO.getPhone();
		if("xxx-xxx-xxxx".equals(phoneNumber)){
			return null;
		}
		else if(phoneNumber != null && (phoneNumber.indexOf('x') != -1 || phoneNumber.indexOf('-') != -1)){
			phoneNumber = phoneNumber.replaceAll("-", "").replaceAll("x", "?").replaceAll("X", "?").replaceAll("\\*", "%");
		}
		
		if(CommonUtil.isNotNullAndEmpty(phoneNumber)){
			inOutParameterMap.put("p_phone",phoneNumber);
		}
		
		if(CommonUtil.isNotNullAndEmpty(rawSubFacilityVO.getNpi())){
			inOutParameterMap.put("p_npi",rawSubFacilityVO.getNpi().replaceAll("\\*", "%"));
		}
		
		
		return masterFacilityDetailsDAO.getAssignmentFacilityDetails(inOutParameterMap);
	}
	
	
			
	/**
	 * The method for getting Master facilities, their sub facilities and nested raw facilities
	 * by passing the Raw facility search criteria
	 * @param rawSubFacilityVO object to hold the RawSubFacility search criteria 
	 * @param bindingResult the BindingResult reference
	 * @param userObject the UserObject reference
	 * @param sessionId  session identifier 
	 * @return List<MasterFacilityDetailsVO> list of MasterFacilityDetailsVO instances   
	 */
	@Override
	public List<MasterFacilityDetailsVO> getFacilityDetailsByRawSubFacility(
			RawSubFacilityVO rawSubFacilityVO, BindingResult bindingResult,
			UserObject userObject) {
		List<MasterFacilityDetailsVO> masterFacilitiesWithSubFacilities = new ArrayList<MasterFacilityDetailsVO>();
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();
		inOutParameterMap.put("p_facname",rawSubFacilityVO.getFacilityName());
		inOutParameterMap.put("p_fac_dept",rawSubFacilityVO.getFacilityDepartment()); 
		inOutParameterMap.put("p_address",rawSubFacilityVO.getAddress());
		inOutParameterMap.put("p_city",rawSubFacilityVO.getCity());
		inOutParameterMap.put("p_state",rawSubFacilityVO.getState());
		inOutParameterMap.put("p_zipcode",rawSubFacilityVO.getZipcode());
		inOutParameterMap.put("p_phone",rawSubFacilityVO.getPhone());
		inOutParameterMap.put("p_npi",rawSubFacilityVO.getNpi());

		masterFacilityDetailsDAO.getFacilityDetailsByRawSubFacility(inOutParameterMap);

		masterFacilitiesWithSubFacilities=(List<MasterFacilityDetailsVO>)inOutParameterMap.get("p_facilities_out");
		Collections.sort(masterFacilitiesWithSubFacilities, new MasterFacilityDetailsVO());
		return masterFacilitiesWithSubFacilities;
	}
}
