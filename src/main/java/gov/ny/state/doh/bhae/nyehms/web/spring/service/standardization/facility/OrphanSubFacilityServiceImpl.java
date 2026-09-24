package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.OrphanSubFacilityDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
@Service
public class OrphanSubFacilityServiceImpl implements OrphanSubFacilityService {
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(OrphanSubFacilityServiceImpl.class);

	/*
	 * Reference to custom exception handler by means of dependency injection 
	 * which logs and email error messages
	 */
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	/*
	 * Spring dependency injected reference to OrphanProviderAddressDAO 
	 */
	@Autowired
	OrphanSubFacilityDAO orphanSubFacilityDAO;
	/*
	 * Reference to transaction helper class
	 */
	@Autowired
	FacilityStandardizationTransactionHelper facilityStandardizationTransactionHelper;
	/*
	 * Operation success message used while linking an orphan provider address to a provider
	 * Used inside {@link #linkOrphanSubFacilityToMasterFacility(long  subFacilityId, long  masterFacilityId,BindingResult bindingResult,UserObject userObject,String sessionId)} 
	 */
	private static final String LINK_ORPHAN_TO_MASTER_SUCCESS_MESSAGE="Sub Facility has been linked to Master Facility";
	/*
	 * Operation success message used while linking a raw provider address to an orphan provider address
	 * by selecting an existing provider
	 * Used inside {@link #linkRawToOrphanSubFacility(long subFacilityId,long masterFacilityId,long rawSubFacilityId,BindingResult bindingResult,UserObject userObject,String sessionId)} 
	 */
	private static final String LINK_RAW_TO_ORPHAN_AND_MASTER_SUCCESS_MESSAGE="Orphan sub facility has been linked to the master facility and Raw sub facility has been linked to the sub facility";
	/*
	 * Operation success message used while linking a raw provider address to an orphan provider address
	 * by creating a new provider
	 * Used inside {@link #addNewMasterFacilityAndLinkRawToOrphanSubFacility(long subFacilityId,long rawSubFacilityId,MasterFacilityVO masterFacilityVO, BindingResult bindingResult,UserObject userObject, String sessionId)} 
	 */
	private static final String CREATE_MASTER_AND_LINK_TO_ORPHAN_SUB_FACILITY_SUCCESS_MESSAGE="Master facility has been created and Orphan sub facility has been linked to the master facility";
	/*
	 * Operation success message used while linking a raw provider address to an orphan provider address
	 * by creating a new provider
	 * Used inside {@link #addNewMasterFacilityAndLinkRawToOrphanSubFacility(long subFacilityId,long rawSubFacilityId,MasterFacilityVO masterFacilityVO, BindingResult bindingResult,UserObject userObject, String sessionId)} 
	 */
	private static final String CREATE_MASTER_AND_LINK_RAW_TO_ORPHAN_SUB_FACILITY_SUCCESS_MESSAGE="Master facility has been created and Orphan sub facility has been linked to the master facility and raw facility";

	/*
	 * Get a list of orphan sub facilities 
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
	 * @return list of orphan sub facilities 
	 */
	public List<SubFacilityVO> getOrphanSubFacilities(BindingResult bindingResult, UserObject userObject) {

		return orphanSubFacilityDAO.getOrphanSubFacilities();
	}

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
	public void linkOrphanSubFacilityToMasterFacility(long  subFacilityId, long  masterFacilityId,BindingResult bindingResult,UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParamaterMap;

		inOutParamaterMap=new HashMap<Object, Object>();
		inOutParamaterMap.put("subFacilityId", subFacilityId);
		inOutParamaterMap.put("masterFacilityId", masterFacilityId);
		inOutParamaterMap.put("statusCode", new Integer(1));
		inOutParamaterMap.put("statusMessage", "");
		orphanSubFacilityDAO.linkOrphanSubFacilityToMasterFacility(inOutParamaterMap);
		statusCode=(Integer)inOutParamaterMap.get("statusCode");
		transactionMessage=statusCode==0?LINK_ORPHAN_TO_MASTER_SUCCESS_MESSAGE:(String)inOutParamaterMap.get("statusMessage");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		if(statusCode != 0)throw new DataRetrievalFailureException(transactionMessage);
	 }

	/*
	 * This method links a raw sub facility to an orphan sub facility. 
	 * Linking a raw sub facility to an orphan sub facility can not be completed 
	 * unless the orphan gets associated to a master facility. Hence this operation first expect the user
	 * to select a master facility and link the orphan sub facility to this master facility. After this sub-operation
	 * is successfully completed, the raw sub facility is linked to the orphan sub facility.
	 * Please see {@link #linkOrphanSubFacilityToMasterFacility(long  subFacilityId, long  masterFacilityId,BindingResult bindingResult,UserObject userObject,String sessionId)    
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
	@Override
	public void linkRawToMasterAndOrphanSubFacility(long subFacilityId,long masterFacilityId,long rawSubFacilityId,BindingResult bindingResult,UserObject userObject) {
		boolean isError = false; 
		try{
			facilityStandardizationTransactionHelper.linkRawToMasterAndOrphanSubFacility(subFacilityId, masterFacilityId, rawSubFacilityId, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured while linking Raw SubFacility("+rawSubFacilityId+") to orphan Sub Facility("+subFacilityId+") to the Master Facility("+masterFacilityId+")");
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while linking Raw SubFacility("+rawSubFacilityId+") to orphan Sub Facility("+subFacilityId+") to the Master Facility("+masterFacilityId+")");
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize Facility details.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, LINK_RAW_TO_ORPHAN_AND_MASTER_SUCCESS_MESSAGE, bindingResult);
		}
	}
	/*
	 * Adding New master facility and  link it to orphan sub facility.
	 * @param subFacilityId the sub facility id which needs to be linked
	 * @param masterFacilityVO the value object which holds the master facility details
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
	 */
	@Override
	public void addNewMasterFacilityAndLinkOrphanSubFacility(long subFacilityId,MasterFacilityVO masterFacilityVO,BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		try{
			facilityStandardizationTransactionHelper.addNewMasterFacilityAndLinkOrphanSubFacility(subFacilityId, masterFacilityVO, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured while linking Orphan Sub Facility("+subFacilityId+") to the new Master Facility("+masterFacilityVO.getMasterFacilityName()+")");
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while linking Orphan Sub Facility("+subFacilityId+") to the new Master Facility("+masterFacilityVO.getMasterFacilityName()+")");
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize Orphan Sub Facility to the new Master Facility", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, CREATE_MASTER_AND_LINK_TO_ORPHAN_SUB_FACILITY_SUCCESS_MESSAGE, bindingResult);
		}
	}
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
	 * @param bindingResult the spring MVC BindingResult reference to hold the results
	 * @param userObject the UserObject reference
	 * @param sessionId the session identifier
 	 * @return String the transaction result
	 */
	@Override
	public void addNewMasterFacilityAndLinkRawToOrphanSubFacility(long subFacilityId,long rawSubFacilityId,MasterFacilityVO masterFacilityVO, BindingResult bindingResult,UserObject userObject){
		
		boolean isError = false; 
		try{
			facilityStandardizationTransactionHelper.addNewMasterFacilityAndLinkRawToOrphanSubFacility(subFacilityId, rawSubFacilityId, masterFacilityVO, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Exception occured while linking raw("+rawSubFacilityId+") and Orphan Sub Facility("+subFacilityId+") to the new Master Facility("+masterFacilityVO.getMasterFacilityName()+")");
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while linking raw("+rawSubFacilityId+") and Orphan Sub Facility("+subFacilityId+") to the new Master Facility("+masterFacilityVO.getMasterFacilityName()+")");
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to Standardize Raw and Orphan Sub Facility to the new Master Facility", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, CREATE_MASTER_AND_LINK_RAW_TO_ORPHAN_SUB_FACILITY_SUCCESS_MESSAGE, bindingResult);
		}

	}
}
