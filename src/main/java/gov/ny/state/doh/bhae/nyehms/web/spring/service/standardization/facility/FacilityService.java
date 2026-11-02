package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCodedDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.MasterFacilityDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.MasterFacilityDetailsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.MergeAndUnlinkDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.RawSubFacilityDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.SubFacilityDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityForRawFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.EharsDataService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public class FacilityService {
	@Autowired
	private RawSubFacilityDAO rawSubFacilityDAO;
	@Autowired
	private SubFacilityDAO subFacilityDAO;
	
	@Autowired
	private EharsDataService eharsDataService;
	
	/*
	 * Reference to MasterFacilityDetailsDAO by means of dependency injection    
	 */
	@Autowired
	private MasterFacilityDetailsDAO masterFacilityDetailsDAO;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	@Autowired
	private MasterFacilityDAO masterFacilityDAO; 
	@Autowired
	private MergeAndUnlinkDAO mergeAndUnlinkDAO;
	@Autowired
	private FacilityCodedDAO facilityCodedDAO;
	
	/*
	 * Reference to Log
	 */
	private static final Logger log = LoggerFactory.getLogger(FacilityService.class);

	
	@Autowired
	CommonUtil commonUtil;
	private static final String CREATE_MASTER_AND_SUB_FACILITIES_SUCCESS_MESSAGE="Master and Sub facilities created successfully";
	private static final String CREATE_SUB_FACILITIES_SUCCESS_MESSAGE="Sub facilities created successfully";
	private static final String STANDARDIZE_RAW_TO_SUB_FACILITY_SUCCESS_MESSAGE = "Raw facility has been standardized with the sub facility";
	private static final String ADD_NEW_SUB_FACILITY_AND_LINK_TO_RAW_SUCCESS_MESSAGE="Raw facility has been linked with the newly created sub facility";
	private static final String ADD_NEW_MASTER_FACILITY_SUCCESS_MESSAGE="New master facility has been created";
	private static final String UNLINK_FACILITY_SUCCESS_MESSAGE = "Facilities have been unlinked";
	private static final String MERGE_SUB_FACILITIES_SUCCESS_MESSAGE = "Sub facilities have been merged";
	private static final String MERGE_MASTER_FACILITIES_SUCCESS_MESSAGE = "Master facilities have been merged";
	@Autowired
	FacilityStandardizationTransactionHelper facilityStandardizationTransactionHelper;
	public List<RawSubFacilityVO> getRawSubFacilityList(BindingResult result, String pageType, boolean _search, String searchField, String searchString, String searchOper, UserObject userObject) {

		List<RawSubFacilityVO> rawSubFacilityList = new ArrayList<>();
			if(pageType.equals("newPage")){
				Map<Object, Object> inOutParameterMap = new HashMap<>();
				inOutParameterMap.put("_search",_search);
				inOutParameterMap.put("searchField",searchField);
				if(searchOper != null && (searchOper.equalsIgnoreCase("in") || searchOper.equalsIgnoreCase("ni"))){
					inOutParameterMap.put("searchString",searchString.split(","));
				}else{
					inOutParameterMap.put("searchString",searchString);
				}
				inOutParameterMap.put("searchOper",searchOper);
								
				rawSubFacilityList= rawSubFacilityDAO.getRawSubFacilityListNewPage(inOutParameterMap);
			}else{
				rawSubFacilityList= rawSubFacilityDAO.getRawSubFacilityListManualReview();
		}	
		return rawSubFacilityList;
	}

	public void insertNewStdFacility(SubFacilityVO subFacilityVO,long rawSubFacilityId, BindingResult bindingResult, UserObject userObject) {
		Integer statusCode=null;
		String transactionMessage=null;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_raw_sub_facility_id", rawSubFacilityId);
		inOutParameterMap.put("p_facility_name", subFacilityVO.getFacilityName());
		inOutParameterMap.put("p_facility_department", subFacilityVO.getFacilityDepartment());
		inOutParameterMap.put("p_county_fips", subFacilityVO.getCountyFips());
		inOutParameterMap.put("p_address", subFacilityVO.getAddress());
		inOutParameterMap.put("p_city", subFacilityVO.getCity());
		inOutParameterMap.put("p_state", subFacilityVO.getState());
		inOutParameterMap.put("p_zipcode", subFacilityVO.getZipcode());
		inOutParameterMap.put("p_plus4", subFacilityVO.getPlus4());
		inOutParameterMap.put("p_phone", CommonUtil.getUnMaskedPhoneNumber(subFacilityVO.getPhone()));
		inOutParameterMap.put("p_user_id", userObject.getWorkerId());
		inOutParameterMap.put("p_master_facility_id", subFacilityVO.getMasterFacilityId());
		inOutParameterMap.put("p_facility_npi", subFacilityVO.getFacilityNpi());
		

			subFacilityDAO.insertNewStdFacility(inOutParameterMap);
			
			statusCode = (Integer) inOutParameterMap.get("p_code_out");
			if(statusCode == 0){
				subFacilityVO.setSubFacilityId((Long)inOutParameterMap.get("p_sub_facility_id_out"));
			}
		transactionMessage=statusCode==0?ADD_NEW_SUB_FACILITY_AND_LINK_TO_RAW_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	
	
	public Long addNewStdFacility(SubFacilityVO subFacilityVO, BindingResult bindingResult, UserObject userObject) {
		Integer statusCode=null;
		String transactionMessage=null;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("p_facility_name", subFacilityVO.getFacilityName());
		inOutParameterMap.put("p_facility_department", subFacilityVO.getFacilityDepartment());
		inOutParameterMap.put("p_county_fips", subFacilityVO.getCountyFips());
		inOutParameterMap.put("p_address", subFacilityVO.getAddress());
		inOutParameterMap.put("p_city", subFacilityVO.getCity());
		inOutParameterMap.put("p_state", subFacilityVO.getState());
		inOutParameterMap.put("p_zipcode", subFacilityVO.getZipcode());
		inOutParameterMap.put("p_plus4", subFacilityVO.getPlus4());
		inOutParameterMap.put("p_phone", CommonUtil.getUnMaskedPhoneNumber(subFacilityVO.getPhone()));
		inOutParameterMap.put("p_user_id", userObject.getWorkerId());
		inOutParameterMap.put("p_facility_npi", subFacilityVO.getFacilityNpi());
		inOutParameterMap.put("p_master_facility_id", subFacilityVO.getMasterFacilityId());

		subFacilityDAO.addNewStdFacility(inOutParameterMap);
		
		
		statusCode = (Integer) inOutParameterMap.get("p_code_out");
		
		transactionMessage=statusCode==0?CREATE_SUB_FACILITIES_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		Long subFacilityId = (Long)inOutParameterMap.get("p_sub_facility_id_out");
		
		return subFacilityId;
		
	}
	
	public void addNewStdFacilityAndRawFacility(SubFacilityVO subFacilityVO,
			BindingResult bindingResult, UserObject userObject){
		String transactionMessage = null;
		boolean isError = false;
		
		try{
			
			
			facilityStandardizationTransactionHelper.addNewStdFacilityAndRawFacility(subFacilityVO, bindingResult, userObject);
			
		}catch(Exception e){
			
			transactionMessage = "Failed to create the Standard Facility, please contact helpline";
			log.error(transactionMessage+"       "+e.getMessage());
			isError = true;
		}
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully created the Standard Facility", bindingResult);
		}
	}

	public void updateStandardize(long  rawSubFacilityId, long subFacilityId, BindingResult bindingResult, UserObject userObject) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("rawSubFacilityId", rawSubFacilityId);
		inOutParameterMap.put("subFacilityId", subFacilityId);

			rawSubFacilityDAO.updateStandardize(inOutParameterMap);
			statusCode=(Integer)inOutParameterMap.get("p_code_out");
			transactionMessage=statusCode==0?STANDARDIZE_RAW_TO_SUB_FACILITY_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
			if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	
	/*
	 * New Page Grand Services
	 */
	public List<MasterFacilityDetailsVO> getSubFacListForSubIds(List<Long> subIds, BindingResult result, UserObject userObject) {
		 List<MasterFacilityDetailsVO> subFacilityList = new ArrayList<>();

		Map<Object, Object> map = new HashMap<>();
		map.put("subIdsList", subIds);

			subFacilityList = masterFacilityDetailsDAO.getSubFacListForSubIds(map);

		return subFacilityList;
	}

	/*
	 * adding new Grand facility to the database
	 */
	public void insertNewGrdFacility(MasterFacilityVO masterFacilityVO, long subFacilityId, BindingResult bindingResult, UserObject userObject) {
		Integer statusCode=null;
		String transactionMessage=null;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_sub_facility_id", subFacilityId);
		//TODO: Need to removed facility type related code
		inOutParameterMap.put("p_master_facility_name", masterFacilityVO.getMasterFacilityName());
		inOutParameterMap.put("p_user_id", userObject.getWorkerId());

		masterFacilityDAO.insertNewGrdFacility(inOutParameterMap);
		statusCode = (Integer) inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?ADD_NEW_MASTER_FACILITY_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
		masterFacilityVO.setMasterFacilityId((Long)inOutParameterMap.get("p_master_facility_id_out"));
	}

	public List<RawSubFacilityVO> getRawListByStdFacilityId(long subFacilityId, List<Long> rawIds, int searchFor, BindingResult result, UserObject userObject) {

		List<RawSubFacilityVO> rawSubFacilityList = new ArrayList<>();
		
		Map<Object, Object> map = new HashMap<>();
		map.put("subFacilityId", subFacilityId);
		if(searchFor == 3){
			map.put("rawFacilityIds", commonUtil.convertToCommaSeparated(rawIds));
		}
		map.put("rawFacilityRefCursor", rawSubFacilityList);

		rawSubFacilityDAO.getRawListByStdFacilityId(map);
		rawSubFacilityList = (List<RawSubFacilityVO>) map.get("rawFacilityRefCursor"); 

		return rawSubFacilityList;
	}
	
	
	public List<Long> getStdIdsForRawIds(List<Long> rawIds, BindingResult result, UserObject userObject){
		List<SubFacilityForRawFacilityVO> subFacForRawList = new ArrayList<>();
		List<Long> subFacilityIds = new ArrayList<>();
		Map<Object, Object> map = new HashMap<>();
		map.put("rawIds", commonUtil.convertToCommaSeparated(rawIds));
		map.put("subFacForRawFacRefCursor", subFacForRawList);

		subFacilityDAO.getStdIdsForRawIds(map);
		subFacForRawList= (List<SubFacilityForRawFacilityVO>) map.get("subFacForRawFacRefCursor");
		
		for(SubFacilityForRawFacilityVO subFacForRawVO : subFacForRawList){
				subFacilityIds.add(subFacForRawVO.getSubFacilityId());
		}

		return subFacilityIds;
	}

	/*
	 * Manual Review
	 * getting the potential matches for the selected raw facility id  
	 */
	public List<Long> getPotentialRawFacList(long rawSubFacilityId, BindingResult result, UserObject userObject){

		List<RawSubFacilityVO> rawSubFacilityVOList = new ArrayList<>();
		List<Long> rawPotentialMatchIdS = new ArrayList<>();
			rawSubFacilityVOList = rawSubFacilityDAO.getPotentialRawFacList(rawSubFacilityId);
			for(RawSubFacilityVO rawSubFacilityVO : rawSubFacilityVOList){
				rawPotentialMatchIdS.add(rawSubFacilityVO.getPmRawFacilityId());
			}

		return rawPotentialMatchIdS;
	}

	public void unlinkFacilities(String facWorkType, String commaSeparatedVals, long parentId, BindingResult bindingResult,
			UserObject userObject) {
		Integer statusCode=null;
		String transactionMessage=null;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_fac_id", commaSeparatedVals);
		inOutParameterMap.put("p_parent_id", parentId);
		inOutParameterMap.put("p_user_id", userObject.getWorkerId());

		if (facWorkType.equals("Sub")) {
			mergeAndUnlinkDAO.unlinkSubFacilities(inOutParameterMap);
		} else {// Raw
			mergeAndUnlinkDAO.unlinkRawFacilities(inOutParameterMap);
		}
			
		statusCode = (Integer) inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?UNLINK_FACILITY_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
//		if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}

	public void mergeSubFacilities(String commaSeparatedVals, long selToBeMergedSub, long selToBeMergedParent,
			BindingResult bindingResult, UserObject userObject) {
		Integer statusCode=null;
		String transactionMessage=null;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_sub_facility_list", commaSeparatedVals);
		inOutParameterMap.put("p_retained_sub_facility", selToBeMergedSub);
		if (selToBeMergedParent != 0) {
			inOutParameterMap.put("p_master_facility", selToBeMergedParent);
		}
		inOutParameterMap.put("p_user_id", userObject.getWorkerId());

		mergeAndUnlinkDAO.mergeSubFacilities(inOutParameterMap);
		statusCode = (Integer) inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?MERGE_SUB_FACILITIES_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}

	public void mergeGrandFacilities(String commaSeparatedVals, long selToBeMergedGrand, BindingResult bindingResult,
			UserObject userObject) {
		Integer statusCode=null;
		String transactionMessage=null;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_master_facility_list", commaSeparatedVals);
		inOutParameterMap.put("p_retained_facility", selToBeMergedGrand);
		inOutParameterMap.put("p_user_id", userObject.getWorkerId());

		mergeAndUnlinkDAO.mergeGrandFacilities(inOutParameterMap);
		statusCode = (Integer) inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?MERGE_MASTER_FACILITIES_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		//if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
	}
	
	//Coded Tables
	public Map<String, String> getFacilityTypeCodes(BindingResult result, UserObject userObject){
		Map<String, String> facilityTypeCodes = new LinkedHashMap<>();
			List<Map<String, String>> statusMapList = facilityCodedDAO.getFacilityTypeCodes();
			for(Map<String, String> statusMap : statusMapList){
				facilityTypeCodes.put(statusMap.get("code"), statusMap.get("desc"));
			}

		return facilityTypeCodes;
	}

	public void createMasterAndSubFacilities(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO,
			long rawSubFacilityId, BindingResult bindingResult,
			UserObject userObject){
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object,Object> inOutParameterMap;

		inOutParameterMap=new HashMap<Object, Object>();
		inOutParameterMap.put("p_master_facility_name", masterFacilityVO.getMasterFacilityName());
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		inOutParameterMap.put("p_facility_name", subFacilityVO.getFacilityName());
		inOutParameterMap.put("p_facility_npi", subFacilityVO.getFacilityNpi());
		
		inOutParameterMap.put("p_facility_department", subFacilityVO.getFacilityDepartment());
		inOutParameterMap.put("p_county_fips", subFacilityVO.getCountyFips());
		inOutParameterMap.put("p_address", subFacilityVO.getAddress());
		inOutParameterMap.put("p_city", subFacilityVO.getCity());
		inOutParameterMap.put("p_state", subFacilityVO.getState());
		inOutParameterMap.put("p_zipcode", subFacilityVO.getZipcode());
		inOutParameterMap.put("p_plus4", subFacilityVO.getPlus4());
		inOutParameterMap.put("p_phone", CommonUtil.getUnMaskedPhoneNumber(subFacilityVO.getPhone()));
		masterFacilityDAO.createMasterAndSubFacilities(inOutParameterMap);
		statusCode=(Integer)inOutParameterMap.get("p_code_out");
		transactionMessage=statusCode==0?CREATE_MASTER_AND_SUB_FACILITIES_SUCCESS_MESSAGE:(String)inOutParameterMap.get("p_msg_out");
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		if(statusCode!=0)throw new DataRetrievalFailureException(transactionMessage);
		masterFacilityVO.setMasterFacilityId((Long)inOutParameterMap.get("p_master_facility_id_out"));
		subFacilityVO.setSubFacilityId((Long)inOutParameterMap.get("p_sub_facility_id_out"));
	}
	
	public Map<String, String> getSubFacilityInfo(String facilityUid,
			BindingResult bindingResult, UserObject userObject){
		
		String subFacilityId =  subFacilityDAO.getSubFacilityId(facilityUid);
		
		Map<String, String> subFacility = new HashMap<>();
		
		eharsDataService.getFacilityDetails(facilityUid).ifPresent(details -> {
			subFacility.put("FACILITYID", subFacilityId);
			subFacility.put("ADDRESS", details.address());
			subFacility.put("CITY", details.city());
			subFacility.put("COUNTY_FIPS", details.countyFips());
			subFacility.put("STATE", details.state());
			subFacility.put("ZIPCODE", details.zipCode());
			subFacility.put("PHONE", details.phone());
			subFacility.put("COUNTRY", details.country());
			subFacility.put("TYPECD", details.typeDescription());
		});
		
		if(subFacility == null || subFacility.size()  == 0){
//			throw new DataRetrievalFailureException("Unable to get the Facility information, please contact helpline.");
			log.error("Unable to get the Facility information for Facility Id : {}",facilityUid);
		}
		
		return subFacility;
	}
	
	public int checkRawFacilitiesForStandardFacilities(
			String standardFacilityId, BindingResult result,
			UserObject userObject, String sessionID){
		
		int subFacilityId = Integer.parseInt(standardFacilityId);
		return subFacilityDAO.checkRawFacilitiesForStandardFacilities(subFacilityId);
	}
	
	public void createRawFacilitiyForStandardFacility(
			Long standardFacilityId, String phone, BindingResult result,
			UserObject userObject, String sessionID){
		
		Map<Object, Object> map = new HashMap<>();
		map.put("standardFacilityId", standardFacilityId);
		map.put("workerId", userObject.getWorkerId());
		map.put("phone", phone);
		
		
		
		int resultCount = subFacilityDAO.createRawFacilitiyForStandardFacility(map);
		
		if(resultCount==0)throw new DataRetrievalFailureException("Unable to create the Raw Facility for the Sub Facility id "+standardFacilityId);
	}
	
	public void unableStandardizeFacilityRecord(long recordId,
			String effectedTable, BindingResult bindingResult,
			UserObject userObject){
		String transactionMessage = "Failed to mark as Unable to Standardize.";
		Integer statusCode = -1;
		
		Map<Object, Object> map = new HashMap<>();
		map.put("recordId", recordId);
		map.put("userId", userObject.getUserId());
		if("rawFacility".equals(effectedTable)){
			int resultCount = subFacilityDAO.unableStandardizeRawFacilityRecord(map);
			if(resultCount > 0 ){
				transactionMessage = "Successfully marked as Unable to Standardize.";
				statusCode = 0;
			}
		}else if("subFacility".equals(effectedTable)){
			int resultCount = subFacilityDAO.unableStandardizeSubFacilityRecord(map);
			if(resultCount > 0 ){
				transactionMessage = "Successfully marked as Unable to Standardize.";
				statusCode = 0;
			}
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	public void addRawAndSubProviderAddressDetails(long masterFacilityId,
			SubFacilityVO subFacilityVO, BindingResult bindingResult,
			UserObject userObject){
		boolean isError = false; 
		try{
			facilityStandardizationTransactionHelper.addRawAndSubProviderAddressDetails(masterFacilityId, subFacilityVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to create the Standard Facility.");
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to create the Standard Facility.");
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to create the Standard Facility, Please contact helpline.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully created the new Standard Facility details", bindingResult);
		}
	}

	public void addFacilityDetails(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO,
			BindingResult bindingResult, UserObject userObject){
		boolean isError = false; 
		try{
			facilityStandardizationTransactionHelper.addFacilityDetails(masterFacilityVO, subFacilityVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to create the Standard Facility.");
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to create the Standard Facility.");
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to create the Standard Facility, Please contact helpline.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully created the new Standard Facility details", bindingResult);
		}
	}
	
	public int checkRawFacilityExistance(RawSubFacilityVO rawSubFacilityVO){
		return rawSubFacilityDAO.checkRawFacilityExistance(rawSubFacilityVO);
	}
	
	public long createRawFacility(RawSubFacilityVO rawSubFacilityVO,
			BindingResult result, UserObject userObject){
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_facility_fname",rawSubFacilityVO.getFacilityName());
		inOutParameterMap.put("p_subfacility_id",rawSubFacilityVO.getSubFacilityId());
		inOutParameterMap.put("p_address",rawSubFacilityVO.getAddress());
		inOutParameterMap.put("p_city",rawSubFacilityVO.getCity());
		inOutParameterMap.put("p_phone",rawSubFacilityVO.getPhone());
		inOutParameterMap.put("p_plus4",rawSubFacilityVO.getPlus4());
		inOutParameterMap.put("p_state",rawSubFacilityVO.getState());
		inOutParameterMap.put("p_county",rawSubFacilityVO.getCountyFips());
		inOutParameterMap.put("p_zipcode",rawSubFacilityVO.getZipcode());
		inOutParameterMap.put("p_userId",userObject.getUserId());
		
		long rawFacilityId = 0;
		
		int resultCount = rawSubFacilityDAO.createRawFacility(inOutParameterMap);
		
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to create the Raw Facility details");
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully created the Raw Facility", result);
			rawFacilityId= Long.valueOf(((BigDecimal)inOutParameterMap.get("rawFacilityId")).longValue());
		}
		
		return rawFacilityId;
	}
}
