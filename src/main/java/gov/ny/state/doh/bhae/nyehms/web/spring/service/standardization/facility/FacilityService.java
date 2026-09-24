package gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;

public interface FacilityService {
	
	//Below are the New Page Services
	
	public List<RawSubFacilityVO> getRawSubFacilityList(BindingResult result, String pageType, boolean _search, String searchField, String searchString, String searchOper, UserObject userObject);
	
	public void insertNewStdFacility(SubFacilityVO subFacilityVO,long rawSubFacilityId,  BindingResult result, UserObject userObject);
	
	
	public void updateStandardize(long  rawSubFacilityId, long subFacilityId, BindingResult result, UserObject userObject);
	
	
	/*
	 * New Page Grand Services
	 */
	public List<MasterFacilityDetailsVO> getSubFacListForSubIds(List<Long> subIds, BindingResult result, UserObject userObject);

	public void insertNewGrdFacility(MasterFacilityVO masterFacilityVO, long subFacilityId, BindingResult result, UserObject userObject);
	
	/*
	 * Manual Review Services
	 */
	public List<Long> getPotentialRawFacList(long rawSubFacilityId, BindingResult result, UserObject userObject);
	
	// below are the Main Search Page services
	public List<RawSubFacilityVO> getRawListByStdFacilityId(long subFacilityId, List<Long> rawIds, int searchFor, BindingResult result, UserObject userObject);
	public List<Long> getStdIdsForRawIds(List<Long> rawIds, BindingResult result, UserObject userObject);
	
	public void unlinkFacilities(String facWorkType, String commaSeparatedVals, long parentId, BindingResult result, UserObject userObject);
	public void mergeSubFacilities(String commaSeparatedVals, long selToBeMergedSub, long selToBeMergedParent, BindingResult result, UserObject userObject);
	public void mergeGrandFacilities(String commaSeparatedVals, long selToBeMergedGrand, BindingResult result, UserObject userObject);
	
	//Coded Tables
	public Map<String, String> getFacilityTypeCodes(BindingResult result, UserObject userObject);

	public void createMasterAndSubFacilities(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO,
			long rawSubFacilityId, BindingResult bindingResult,
			UserObject userObject);
	
	public Long addNewStdFacility(SubFacilityVO subFacilityVO, BindingResult bindingResult, UserObject userObject);

	public Map<String, String> getSubFacilityInfo(String facilityUid,
			BindingResult bindingResult, UserObject userObject);

	public void addNewStdFacilityAndRawFacility(SubFacilityVO subFacilityVO,
			BindingResult bindingResult, UserObject userObject);
	
	public int checkRawFacilitiesForStandardFacilities(
			String standardFacilityId, BindingResult result,
			UserObject userObject, String sessionID);
	public void createRawFacilitiyForStandardFacility(
			Long standardFacilityId, String phone, BindingResult result,
			UserObject userObject, String sessionID);

	public void unableStandardizeFacilityRecord(long recordId,
			String effectedTable, BindingResult bindingResult,
			UserObject userObject);

	public void addRawAndSubProviderAddressDetails(long masterFacilityId,
			SubFacilityVO subFacilityVO, BindingResult bindingResult,
			UserObject userObject);

	public void addFacilityDetails(MasterFacilityVO masterFacilityVO, SubFacilityVO subFacilityVO,
			BindingResult bindingResult, UserObject userObject);

	public int checkRawFacilityExistance(RawSubFacilityVO rawSubFacilityVO);

	public long createRawFacility(RawSubFacilityVO rawSubFacilityVO,
			BindingResult result, UserObject userObject);
}
