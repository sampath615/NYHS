package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;

import org.springframework.validation.BindingResult;

public interface OrphanSubFacilityService {
	/*
	 * Get a list of orphan sub facilities 
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
	 * @return list of orphan sub facilities 
	 */
	public List<SubFacilityVO> getOrphanSubFacilities(BindingResult bindingResult, UserObject userObject);
	/*
	 * This method links an orphan sub facility to a master facility. 
	 * After this operation the orphan sub facility is associated the master facility and no longer an orphan
	 * @param subFacilityId the orphan sub facility for which master facility linking is needed
	 * @param masterFacilityId the master facility to which this orphan sub facility should be linked
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
 	 * @return String the transaction result
	 */
	public void linkOrphanSubFacilityToMasterFacility(long  subFacilityId, long  masterFacilityId,BindingResult bindingResult,UserObject userObject);
	/*
	 * This method links a raw sub facility to an orphan sub facility. 
	 * Linking a raw sub facility to an orphan sub facility can not be completed 
	 * unless the orphan gets associated to a master facility. Hence this operation first expect the user
	 * to select a master facility and link the orphan sub facility to this master facility. After this sub-operation
	 * is successfully completed, the raw sub facility is linked to the orphan sub facility.
	 * Please see {@link #linkOrphanSubFacilityToMasterFacility(long  subFacilityId, long  masterFacilityId,BindingResult bindingResult,UserObject userObject)    
	 * After this operation the orphan sub facility is associated to this master facility and no longer an orphan
	 * and the raw sub facility is associated to orphan sub facility and no longer a raw sub facility.
	 * @param subFacilityId the orphan sub facility for which master facility linking is needed
	 * @param masterFacilityId the master facility to which this orphan sub facility should be linked
	 * @param rawSubFacilityId the raw sub facility which needs to be linked to the orphan sub facility
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
 	 * @return String the transaction result
	 */
	public void linkRawToMasterAndOrphanSubFacility(long subFacilityId,long masterFacilityId,long rawSubFacilityId,BindingResult bindingResult,UserObject userObject);
	/*
	 * Adding New master facility and  link it to orphan sub facility.
	 * @param subFacilityId the sub facility id which needs to be linked
	 * @param masterFacilityVO the value object which holds the master facility details
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
	 */
	public void addNewMasterFacilityAndLinkOrphanSubFacility(long subFacilityId, MasterFacilityVO masterFacilityVO,BindingResult bindingResult, UserObject userObject);
	/*
	 * This method links a raw sub facility to an orphan sub facility. 
	 * Linking a raw sub facility to an orphan sub facility can not be completed 
	 * unless the orphan gets associated to a master facility. Hence this operation first expect the user
	 * to create a master facility and link the orphan sub facility to this master facility. After this sub-operation
	 * is successfully completed, the raw sub facility is linked to the orphan sub facility.
	 * Please see {@link #linkRawToOrphanSubFacility(@RequestParam long  subFacilityId, @RequestParam long  masterFacilityId,@RequestParam long  rawSubFacilityId, HttpServletRequest request)    
	 * After this operation a new master facility is created and the orphan sub facility is associated to this master sub facility and no longer an orphan.
	 * The raw sub facility is associated to orphan sub facility and no longer a raw sub facility.
	 * @param subFacilityId the orphan sub facility for which parent master facility linking is needed
	 * @param rawSubFacilityId the raw sub facility which needs to be linked to the orphan sub facility
 	 * @param masterFacilityVO the parent master facility to be created
	 */
	public void addNewMasterFacilityAndLinkRawToOrphanSubFacility(long subFacilityId,long rawSubFacilityId,MasterFacilityVO masterFacilityVO, BindingResult bindingResult,UserObject userObject);
}
