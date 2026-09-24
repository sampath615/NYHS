package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
	/*
	 * A helper class to manage all operations which should be executed in a transaction.
	 * The methods are annotated with Spring @Transactional to ensure the transaction.
	 * This class is annotated as @Service in order to load as a Spring managed bean.
	 */
@Service
public class FacilityStandardizationTransactionHelper {
	/*
	 * Spring dependency injected reference to FacilityService
	 */
	@Autowired
	FacilityService facilityService;
	/*
	 * Spring dependency injected reference to OrphanSubFacilityService
	 */
	@Autowired
	OrphanSubFacilityService orphanSubFacilityService;
	 /*
	 * This method facilitate the multiple calls involved in the transaction
	 * while linking a raw sub facility to an orphan sub facility by selecting an existing master facility
	 * @param subFacilityId the orphan sub facility for which master facility linking is needed
	 * @param masterFacilityId the master facility to which this orphan sub facility should be linked
	 * @param rawSubFacilityId the raw sub facility which needs to be linked to the orphan sub facility
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void linkRawToMasterAndOrphanSubFacility(long subFacilityId,long masterFacilityId,long rawSubFacilityId,BindingResult bindingResult, UserObject userObject){
		orphanSubFacilityService.linkOrphanSubFacilityToMasterFacility(subFacilityId, masterFacilityId, bindingResult, userObject);
		facilityService.updateStandardize(rawSubFacilityId, subFacilityId, bindingResult, userObject);
	}
	/*
	 * This method facilitate the multiple calls involved in the transaction
	 * while adding New master facility and  linking it to orphan sub facility
	 * @param providerAddressId the Provider Address which needs to be linked
	 * @param providerVO the value object which holds the provider details
	 * @param result the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionID the unique session identifier
	 */
	@Transactional
	public void addNewMasterFacilityAndLinkOrphanSubFacility(long subFacilityId,MasterFacilityVO masterFacilityVO,BindingResult bindingResult, UserObject userObject){
		facilityService.insertNewGrdFacility(masterFacilityVO, subFacilityId, bindingResult, userObject);
		long masterFacilityId =masterFacilityVO.getMasterFacilityId();
		if(subFacilityId!=0 && masterFacilityId!=0){
			orphanSubFacilityService.linkOrphanSubFacilityToMasterFacility(subFacilityId, masterFacilityId, bindingResult, userObject);
		}
	}
	/*
	 * This method facilitate the multiple calls involved in the transaction
	 * while linking a raw provider to an orphan provider address by creating a new provider
	 * @param providerAddressId the orphan provider address to which the raw provider address should be linked
	 * @param providerId the provider to which the orphan provider address should be linked
	 * @param rawProviderAddressId the raw provider to be linked
	 * @param bindingResult the binding result reference to hold the result
	 * @param userObject the user reference
	 * @param sessionId the unique session identifier
	 */
	@Transactional
	public void addNewMasterFacilityAndLinkRawToOrphanSubFacility(long subFacilityId,long rawSubFacilityId,MasterFacilityVO masterFacilityVO, BindingResult bindingResult,UserObject userObject){
		facilityService.insertNewGrdFacility(masterFacilityVO, subFacilityId, bindingResult, userObject);
		long masterFacilityId = masterFacilityVO.getMasterFacilityId();
		if(subFacilityId!=0 && masterFacilityId!=0){
			orphanSubFacilityService.linkRawToMasterAndOrphanSubFacility(subFacilityId, masterFacilityId, rawSubFacilityId, bindingResult, userObject);
		}
	}
	/*
	 * This method ensure transaction integrity while standardizing a raw sub facility
	 * by creating a new master facility and sub facility
	 * @param masterFacilityVO the master facility details
	 * @param subFacilityVO the sub facility details
	 * @param rawSubFacilityId the raw sub facility to be standardized
	 * @param request the HTTPRequest reference
	 * @param bindingResult the Spring MVC BindingResult reference
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier 
	 */
	@Transactional
	public void standardizeRawByCreatingMasterAndSubFacilities(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO,long rawSubFacilityId, BindingResult bindingResult,UserObject userObject){
		facilityService.createMasterAndSubFacilities(masterFacilityVO, subFacilityVO, rawSubFacilityId, bindingResult, userObject);
		long subFacilityId=subFacilityVO.getSubFacilityId();
		long masterFacilityId=masterFacilityVO.getMasterFacilityId();
		if(subFacilityId!=0&&masterFacilityId!=0){
			orphanSubFacilityService.linkRawToMasterAndOrphanSubFacility(subFacilityId, masterFacilityId, rawSubFacilityId, bindingResult, userObject);
		}
	}
	
	@Transactional
	public void addNewStdFacilityAndRawFacility(SubFacilityVO subFacilityVO,
			BindingResult bindingResult, UserObject userObject) {
		
			subFacilityVO.setMasterFacilityId(-999);
			
			Long subFacilityId = facilityService.addNewStdFacility(subFacilityVO, bindingResult, userObject);
			
			if(subFacilityId != 0){
				
				String phone = "";
				List<String> subFacilityPhoneList = subFacilityVO.getPhone();
				if(subFacilityPhoneList != null && subFacilityPhoneList.size() > 0){
					phone = subFacilityPhoneList.get(0);
				}
				facilityService.createRawFacilitiyForStandardFacility(subFacilityId, phone, bindingResult, userObject, userObject.getSessionId());
			}else{
				throw new DataRetrievalFailureException("Failed to create the Standard Facility.");
			}
			
	}
	
	@Transactional
	public void addRawAndSubProviderAddressDetails(long masterFacilityId,
			SubFacilityVO subFacilityVO, BindingResult bindingResult,
			UserObject userObject) {
		
		subFacilityVO.setMasterFacilityId(masterFacilityId);
		Long subFacilityId = facilityService.addNewStdFacility(subFacilityVO, bindingResult, userObject);
		
		if(subFacilityId != 0){
			
			String phone = "";
			List<String> subFacilityPhoneList = subFacilityVO.getPhone();
			if(subFacilityPhoneList != null && subFacilityPhoneList.size() > 0){
				phone = subFacilityPhoneList.get(0);
			}
			facilityService.createRawFacilitiyForStandardFacility(subFacilityId, phone, bindingResult, userObject, userObject.getSessionId());
		}else{
			throw new DataRetrievalFailureException("Failed to create the Standard Facility.");
		}

		
	}
	
	@Transactional
	public void addFacilityDetails(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO,
			BindingResult bindingResult, UserObject userObject) {
		
		facilityService.createMasterAndSubFacilities(masterFacilityVO, subFacilityVO, 0, bindingResult, userObject);
		
		if(subFacilityVO.getSubFacilityId() == 0){
			throw new DataRetrievalFailureException("Failed to create the Standard Facility.");
		}else{
			
			String phone = "";
			List<String> subFacilityPhoneList = subFacilityVO.getPhone();
			if(subFacilityPhoneList != null && subFacilityPhoneList.size() > 0){
				phone = subFacilityPhoneList.get(0);
			}
			
			facilityService.createRawFacilitiyForStandardFacility(subFacilityVO.getSubFacilityId(), phone, bindingResult, userObject, userObject.getSessionId());
			
			
			
		}
		
	}
}
