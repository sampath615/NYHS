package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;

/**
 * 
 * @author axp21
 * The service interface for unstandardized facility operations
 */
public interface UnStandardizedFacilityService {
	/*
	 * Move the facility data and copy it to provider data.
	 * @param facilityId Unique identifier of the facility data to be moved
	 * @return success or failure message based on the transaction   
	 */
	public void flagFacilityAsProvider(long facilityId,BindingResult result, UserObject userObject);
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
	public List getMasterFacilitiesWithSubFacilities(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO , String searchFor,BindingResult bindingResult, UserObject userObject);
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
	public void standardizeRawByCreatingMasterAndSubFacilities(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO,long rawSubFacilityId, BindingResult bindingResult,UserObject userObject);
}
