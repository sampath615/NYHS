package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;

import java.util.List;
import java.util.Map;


/**
* This interface containing methods related to Facility search operations
* 
*  
* @author  
* @version 
*/
public interface MasterFacilityDetailsDAO {
	
	/**
	 * Returns the complete Facility list containing the First name,
	 * Last name, middle name and the count of Address list associated to the Facility
	 * 
	 * This data will render depends on the search critera     
	 *
	 * @param  FacilityDetailsVO  Facility object related beans  
	 * @param  result  using to store error related information
	 * @param userObject, sessionID  used to track Exception related information 
	 * 
	 * @return      List of Facilities
	 * 
	 */
	 	public List<MasterFacilityDetailsVO> getMasterFacilitiesWithSubFacilities(Map<Object,Object> inOutParameterMap);

		public List<MasterFacilityDetailsVO> getFacilityDetailsByMasterFacility(Map<Object,Object> inOutParameterMap);

		public List<MasterFacilityDetailsVO> getFacilityDetailsBySubFacility(Map<Object,Object> inOutParameterMap);

		public List<MasterFacilityDetailsVO> getFacilityDetailsByRawSubFacility(Map<Object,Object> inOutParameterMap);
		
		public List<MasterFacilityDetailsVO> getSubFacListForSubIds(Map<Object,Object> inOutParameterMap);

		public List<RawSubFacilityVO> getAssignmentFacilityDetails(
				Map<Object, Object> inOutParameterMap);
}
