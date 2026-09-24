package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;

public interface SearchManageFacilityService {
		
	/**
	 * The method for getting Master facilities, their sub facilities and nested raw facilities
	 * by passing the Master facility search criteria
	 * @param masterFacilityVO object to hold the MasterFacility search criteria 
	 * @param bindingResult the BindingResult reference
	 * @param userObject the UserObject reference
	 * @return List<MasterFacilityDetailsVO> list of MasterFacilityDetailsVO instances   
	 */
	public List<MasterFacilityDetailsVO> getFacilityDetailsByMasterFacility(
			MasterFacilityVO masterFacilityVO, BindingResult bindingResult,
			UserObject userObject);
	/**
	 * The method for getting Master facilities, their sub facilities and nested raw facilities
	 * by passing the Sub facility search criteria
	 * @param subFacilityVO object to hold the SubFacility search criteria 
	 * @param bindingResult the BindingResult reference
	 * @param userObject the UserObject reference
	 * @return List<MasterFacilityDetailsVO> list of MasterFacilityDetailsVO instances   
	 */
	public List<MasterFacilityDetailsVO> getFacilityDetailsBySubFacility(SubFacilityVO subFacilityVO,
			BindingResult bindingResult, UserObject userObject);
	/**
	 * The method for getting Master facilities, their sub facilities and nested raw facilities
	 * by passing the Raw facility search criteria
	 * @param rawSubFacilityVO object to hold the RawSubFacility search criteria 
	 * @param bindingResult the BindingResult reference
	 * @param userObject the UserObject reference
	 * @param sessionId  session identifier 
	 * @return List<MasterFacilityDetailsVO> list of MasterFacilityDetailsVO instances   
	 */
	public List getFacilityDetailsByRawSubFacility(
			RawSubFacilityVO rawSubFacilityVO, BindingResult bindingResult,
			UserObject userObject);
	public List<RawSubFacilityVO> getFacilityDetailsForAssignmentByRawFacility(
			RawSubFacilityVO rawSubFacilityVO, BindingResult bindingResult,
			UserObject userObject);
}
