package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms.SchedulerJobsDAO;
import gov.ny.state.doh.bhae.nyehms.utils.County;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DeathDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DeathDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * {@inheritDoc}
 * 
 * @author vxp06
 */
@Service
public class DeathDocumentService {

	@Autowired
	DeathDocumentDAO deathDocumentDAO;
	@Autowired
	private SchedulerJobsDAO schedulerJobsDAO;
	@Autowired
	DocumentDAO documentDAO;
	@Autowired
	NycDeathDocumentService nycDeathDocumentService;
	@Autowired
	CommonDocumentService commonDocumentService; 
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;


	private static Logger log = LoggerFactory.getLogger(DeathDocumentService.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	/**
	 * This method is called from document controller and this returns the death
	 * doc object for given document id
	 * 
	 * {@inheritDoc}
	 * 
	 * @param docId
	 * @param result
	 * @param userObject
	 * @param sessionID
	 * @return
	 */
	public DeathDocVO getDeathDoc(int docId, BindingResult result,
			UserObject userObject, String sessionID) {

		boolean rollbackFlag = false;
		DeathDocVO stdDeathDocVO = null;
		log.debug("{} fetching for docId : {}", nyehmsLog, docId);
		try {
			stdDeathDocVO = deathDocumentDAO.getDeathDoc(docId);
			
			if(stdDeathDocVO == null){
				return null;
			}
			
			String H_CAUSE = stdDeathDocVO.getH_cause();
			String OI_CAUSE = stdDeathDocVO.getOi_cause();
			
			if("Y".equals(H_CAUSE) && "Y".equals(OI_CAUSE)){
				stdDeathDocVO.setCauseOfDeath("Opportunistic Infection (OI) Cause of Death");
			}else if("Y".equals(H_CAUSE) && "N".equals(OI_CAUSE)){
				stdDeathDocVO.setCauseOfDeath("HIV Cause of Death");
			}else {
				
				Map<String, Object> inputParameterMap = new HashMap<>();
				
				inputParameterMap.put("docId", docId);
				inputParameterMap.put("TABLE_NAME", "STD_DEATH_DOC");
				
				String meetsCDC = documentDAO.checkStatenoMeetsCDC(inputParameterMap);
				
				if("Y".equals(meetsCDC)){
					stdDeathDocVO.setCauseOfDeath("Meets Case Definition");
				}
				
				
			}
			
			
		} catch (DataAccessException e) {
			rollbackFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			rollbackFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (rollbackFlag) {
				commonExceptionHandler.populateBindObjTrsnFail(result);
			}
		}

		return stdDeathDocVO;
	}

	public int writeRawDeathDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId, Map<String, Map<String, String>> zipCodeReferenceMap) {

		final String ONE = "1";
		final String ZERO = "0";
		final String XX = "XX";
		final String ZZ = "ZZ";
		final String DASH = "-";
		final String DASHDASH = "--";

		Map<Object, Object> stdDocData = new HashMap<>();
		String NCHS_FORMAT = (String) docVO.get("NCHS_FORMAT");

		stdDocData.put("DOCUMENT_ID", docVO.get("DOCUMENT_ID"));
		// DEATH_SRC_ID Historical PK

		stdDocData.put("FNAME", 
				CommonUtil.NameValidation((String) docVO.get("FIRST")));

		stdDocData.put("LNAME",
				CommonUtil.NameValidation((String) docVO.get("LAST")));

		stdDocData.put("MNAME", CommonUtil.NameValidation((String) docVO.get("MIDDLE")));
		// SUFFIX NOT IN RAW

		Calendar deathDOBCalender = null;
		if (docVO.get("DOB") != null) {
			Timestamp deathDOBRawValue = schedulerJobsDAO
					.getDateValueFromString((String) docVO.get("DOB"));
			if (deathDOBRawValue != null) {
				deathDOBCalender = CommonUtil.resetTimeToZero(deathDOBRawValue,
						1900);
			}
		}
		if (deathDOBCalender != null) {
			stdDocData.put("DOB_TEXT",
					CommonUtil.convertCalenderToString(deathDOBCalender));
		}

		stdDocData.put("SSN",
				CommonUtil.formatSSNValue((String) docVO.get("SSN")));

		String aptNo = "";

		String RES_APT_NO = (String) docVO.get("RES_APT_NO");

		if (CommonUtil.isNotNullAndEmpty(RES_APT_NO)) {
			aptNo = " APT " + RES_APT_NO;
		}
		
		String resStreetNo = "";
		if (CommonUtil.isNotNullAndEmpty((String) docVO.get("RES_STREET_NO"))) {
			resStreetNo = (String) docVO.get("RES_STREET_NO") + " ";
		}
		String resStreet = "";
		if (CommonUtil.isNotNullAndEmpty((String) docVO.get("RES_STREET"))) {
			resStreet = (String) docVO.get("RES_STREET") + " ";
		}
		String resStreetType = "";
		if (CommonUtil.isNotNullAndEmpty((String) docVO.get("RES_STREET_TYPE"))) {
			resStreetType = (String) docVO.get("RES_STREET_TYPE") + " ";
		}
		// No use of RES_STREET_DIRECT
		stdDocData.put("PERSON_ADDRESS",
				CommonUtil.addressValidation((String)new StringBuilder().append(resStreetNo)
						.append(resStreet)
						.append(resStreetType)
						.append(aptNo).toString())); // Coming from 5 fields of RAW Table
		
		
		String personZip = null;
		
		String RES_ZIP = CommonUtil.formatZipAndPlus4Code((String) docVO
				.get("RES_ZIP"));
		if (CommonUtil.isNotNullAndEmpty(RES_ZIP)) {
			if (RES_ZIP.length() > 5) {
				//stdDocData.put("PERSON_ZIP", RES_ZIP.substring(0, 5));
				personZip = RES_ZIP.substring(0, 5);
				
				stdDocData.put("PERSON_PLUS4", RES_ZIP.substring(5));
			} else {
				personZip = RES_ZIP;
				//stdDocData.put("PERSON_ZIP", RES_ZIP);
			}
		}
		
		Map<String, String> zipCodeAddressDetails = zipCodeReferenceMap.get(personZip);

		stdDocData.put("PERSON_ZIP", personZip);
		
		if(CommonUtil.isNotNullAndEmpty(personZip) && zipCodeAddressDetails != null && zipCodeAddressDetails.size() != 0){
		
			stdDocData.put("PERSON_CITY", zipCodeAddressDetails.get("CITY"));
	
			stdDocData.put("PERSON_STATE", zipCodeAddressDetails.get("STATE_CD"));
			
			stdDocData.put("PERSON_COUNTY", zipCodeAddressDetails.get("COUNTY_FIPS_CD"));
		}else{
			String fipsState = (String) docVO.get("FIPS_ST");
			if (DASHDASH.equals(fipsState) || ZZ.equals(fipsState)
					|| XX.equals(fipsState)) {
				fipsState = "";
			}
			stdDocData.put("PERSON_STATE", fipsState);
			
			String fipsCounty = (String) docVO.get("FIPS_CNTY");
			if (CommonUtil.isNotNullAndEmpty(fipsCounty)
					&& (fipsCounty.equals("000") || fipsCounty.equals("999"))) {
				fipsCounty = "";
			}
			stdDocData.put("PERSON_COUNTY", fipsCounty);
		}
		
		
//		if (DASHDASH.equals(fipsState) || ZZ.equals(fipsState)
//		if (CommonUtil.isNotNullAndEmpty(fipsCounty)

		// PERSON_CENSUS_TRACT NOT IN RAW
		// PERSON_LATITUDE NOT IN RAW
		// PERSON_LONGITUDE NOT IN RAW
		// FACILITY_CENSUS_TRACT
		// FACILITY_LATITUDE
		// FACILITY_LONGITUDE
		// MATCH_WEIGHT
		// MATCH_PASS
		stdDocData.put("CREATE_UID", "900");
		stdDocData.put("CURRENT_PROCESS_STATUS", CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
		stdDocData.put("CREATE_DATE", new Date(Calendar.getInstance()
				.getTimeInMillis()));
		stdDocData.put("DEATH_FILE", (String)docVO.get("DEATH_FILE"));

		String ethnicity = "";
		if (ONE.equals((String) docVO.get("HISP_ORIGIN_NONE"))
				|| ONE.equals((String) docVO.get("HISP_ORIGIN_MEXICAN"))
				|| ONE.equals((String) docVO.get("HISP_ORIGIN_PUERTO"))
				|| ONE.equals((String) docVO.get("HISP_ORIGIN_CUBAN"))
				|| ONE.equals((String) docVO.get("HISP_ORIGIN_OTHER"))) {
			ethnicity = "01";
		} else if ((ZERO.equals((String) docVO.get("HISP_ORIGIN_NONE")) || docVO.get("HISP_ORIGIN_NONE") == null)
				&& (ZERO.equals((String) docVO.get("HISP_ORIGIN_MEXICAN")) || docVO.get("HISP_ORIGIN_MEXICAN") == null)
				&& (ZERO.equals((String) docVO.get("HISP_ORIGIN_PUERTO")) || docVO.get("HISP_ORIGIN_PUERTO") == null)
				&& (ZERO.equals((String) docVO.get("HISP_ORIGIN_CUBAN")) || docVO.get("HISP_ORIGIN_CUBAN") == null)
				&& (ZERO.equals((String) docVO.get("HISP_ORIGIN_OTHER")) || docVO.get("HISP_ORIGIN_OTHER") == null)) {
			ethnicity = "02";
		} else {
			ethnicity = "09";
		}

		stdDocData.put("ETHNICITY", ethnicity);

		stdDocData.put("CURRENT_GENDER", LabCommonUtils
				.checkCurrentGenderData((String) docVO.get("SEX")));

		// REVIEW_UID
		// REVIEW_DATE
		// MATCH_CD
		stdDocData.put("RACE_INDIAN",
				LabCommonUtils.checkRaceData((String) docVO.get("RACE_I")));
		stdDocData.put("RACE_BLACK",
				LabCommonUtils.checkRaceData((String) docVO.get("RACE_B")));
		stdDocData.put("RACE_WHITE",
				LabCommonUtils.checkRaceData((String) docVO.get("RACE_W")));

		stdDocData.put("RACE_ASIAN", 0);
		if (ONE.equals((String) docVO.get("RACE_A"))
				|| ONE.equals((String) docVO.get("RACE_A_OTH"))
				|| ONE.equals((String) docVO.get("RACE_C"))
				|| ONE.equals((String) docVO.get("RACE_F"))
				|| ONE.equals((String) docVO.get("RACE_J"))
				|| ONE.equals((String) docVO.get("RACE_K"))
				|| ONE.equals((String) docVO.get("RACE_V"))) {
			stdDocData.put("RACE_ASIAN", 1);
		}

		stdDocData.put("RACE_PACIFIC", 0);
		if (ONE.equals((String) docVO.get("RACE_H"))
				|| ONE.equals((String) docVO.get("RACE_P_OTH"))
				|| ONE.equals((String) docVO.get("RACE_S"))
				|| ONE.equals((String) docVO.get("RACE_G"))) {
			stdDocData.put("RACE_PACIFIC", 1);
		}
		// RACE_MULTIPLE
		stdDocData.put("RACE_OTHER",
				LabCommonUtils.checkRaceData((String) docVO.get("RACE_OTH")));
		// RACE_UNKNOWN
		// ADU_CD
		stdDocData.put("DEATH_CERT", docVO.get("CERT"));
		stdDocData.put("DEATH_CERT_LICENSE", docVO.get("DEATH_CERT_LICENSE"));
//		stdDocData.put("DEATH_COUNTY", docVO.get("DEATH_CNTY"));
		
		if("Y".equals(NCHS_FORMAT)) {
			stdDocData.put("DEATH_COUNTY", (String) docVO.get("DEATH_CNTY"));
			
			stdDocData.put("FACILITY_CITY", (String) docVO.get("DEATH_CITY_TEXT"));
			stdDocData.put("FACILITY_NAME", (String) docVO.get("DEATH_FACILITY_NAME"));
			stdDocData.put("FACILITY_ADDRESS", (String) docVO.get("DEATH_FACILITY_ADDRESS"));
			String _facilityState = (String) docVO.get("DEATH_FACILITY_STATE"); 
			if("New York".equals(_facilityState)) {
				stdDocData.put("FACILITY_STATE", "NY");
			}else {
				stdDocData.put("FACILITY_STATE", commonDocumentService
						.validateState(_facilityState, null, null, null));
			}
			String _facilityZip = CommonUtil.formatZipAndPlus4Code((String) docVO.get("DEATH_FACILITY_ZIP"));
			
			if (CommonUtil.isNotNullAndEmpty(_facilityZip)) 
			{
				if (_facilityZip.length() > 5) 
				{
					stdDocData.put("FACILITY_ZIP", _facilityZip.substring(0, 5));
					stdDocData.put("FACILITY_PLUS4", _facilityZip.substring(5));
				} 
				else
				{
					stdDocData.put("FACILITY_ZIP", _facilityZip);
				}
			}
			
			stdDocData.put("FACILITY_TYPE", (String) docVO.get("DEATH_FACILITY_TYPE"));
			//stdDocData.put("", (String) docVO.get("DEATH_FACILITY_CITY_CD"));

			
			
		}else {
			String deathCity = (String) docVO.get("DEATH_CITY");
			
			String deathCounty = (String) docVO.get("DEATH_CNTY");
			if (CommonUtil.isNotNullAndEmpty(deathCounty) && !"70".equals(deathCounty)) {
				stdDocData.put("DEATH_COUNTY", deathDocumentDAO.getNysFipsCounty(deathCounty));
			}else if("70".equals(deathCounty) && CommonUtil.isNotNullAndEmpty(deathCity)){
				stdDocData.put("DEATH_COUNTY", County.getFipsFromGaz(deathCity));
			}else if("80".equals(deathCounty)){
				stdDocData.put("DEATH_COUNTY", "998");
			}else{
				stdDocData.put("DEATH_COUNTY", "999");
			}
			
			
			String pfi = (String) docVO.get("PFI");
			if (CommonUtil.isNullOrEmpty(pfi) || "9999".equals(pfi)) {
				pfi = (String) docVO.get("PFI_NEW");
			}
			
			if (CommonUtil.isNotNullAndEmpty(pfi) && pfi.length() == 6) {
				pfi = pfi.substring(2); // truncate two leading zeros
			}

			stdDocData.put("PFI", pfi);
			
			String instCode = (String) docVO.get("INST_CODE");

			
			if("70".equals(deathCounty)){
				
				stdDocData.put("NYS_COUNTY", deathCounty);
				
				nycDeathDocumentService.setNYCFacilityData(pfi, instCode, stdDocData);
			}else{
				setFacilityData(instCode, pfi, stdDocData);
			}
		}
		
		

		Calendar deathDODCalender = null;
		if (docVO.get("DOD") != null) {
			Timestamp deathDODRawValue = schedulerJobsDAO
					.getDateValueFromString((String) docVO.get("DOD"));
			if (deathDODRawValue != null) {
				deathDODCalender = CommonUtil.resetTimeToZero(deathDODRawValue,
						1900);
			}
		}
		if (deathDODCalender != null) {
			stdDocData.put("DOD_TEXT",
					CommonUtil.convertCalenderToString(deathDODCalender));
		}

		stdDocData.put("AGE_DEATH", docVO.get("AGE_DEATH"));

		stdDocData.put("SPEC_INT_CAUSE", docVO.get("SPEC_INT_CAUSE"));
		stdDocData.put("UNITS_AGE", docVO.get("UNITS_AGE"));
		stdDocData.put("SOUNDEX", docVO.get("SOUNDEX"));

		String fatherLName = CommonUtil.NameValidation((String) docVO.get("FATHER_LNAME"));
		if (CommonUtil.isNotNullAndEmpty(fatherLName)
				&& (DASHDASH.equals(fatherLName) || DASH.equals(fatherLName) || "Unknown"
						.equals(fatherLName))) {
			fatherLName = "";
		} else {
			fatherLName = CommonUtil.removeSingleQuote(fatherLName);
		}
		stdDocData.put("FATHER_LNAME", fatherLName);

		String motherMaidenName = CommonUtil.NameValidation((String) docVO.get("MOTHER_MAIDEN"));
		
		
		if (CommonUtil.isNotNullAndEmpty(motherMaidenName)
				&& (DASHDASH.equals(motherMaidenName) || DASH
						.equals(motherMaidenName)|| "Unknown"
						.equals(motherMaidenName))) {
			motherMaidenName = "";
		} else {
			motherMaidenName = CommonUtil.removeSingleQuote(motherMaidenName);
		}
		stdDocData.put("MOTHER_MAIDEN", motherMaidenName);

		stdDocData.put("MARITAL_STATUS", docVO.get("MARITAL_STATUS"));
		stdDocData.put("PREGNANT", docVO.get("PREGNANT"));

		Calendar deathDeliveryDateCalender = null;
		if (docVO.get("DELIVERY_DT") != null) {
			Timestamp deathDeliveryDtRawValue = schedulerJobsDAO
					.getDateValueFromString((String) docVO.get("DELIVERY_DT"));
			if (deathDeliveryDtRawValue != null) {
				deathDeliveryDateCalender = CommonUtil.resetTimeToZero(
						deathDeliveryDtRawValue, 1900);
			}
		}
		if (deathDeliveryDateCalender != null) {
			stdDocData.put("DELIVERY_DATE_TEXT", CommonUtil
					.convertCalenderToString(deathDeliveryDateCalender));
		}

		stdDocData.put("MED_RECORD_NUMBER", CommonUtil.normalizeString((String)docVO.get("MEDRECNO")));

		String birthPlace = (String) docVO.get("BIRTH_STATE");
		if (CommonUtil.isNotNullAndEmpty(birthPlace)
				&& (ZZ.equals(birthPlace) || XX.equals(birthPlace))) {
			birthPlace = "";
		}
		stdDocData.put("BIRTH_STATE", birthPlace);

		stdDocData.put("LOCAL_REG_NO", docVO.get("LOCAL_REG_NO"));
		stdDocData.put("FIPS_PLACE", docVO.get("FIPS_PLACE"));

		

		stdDocData.put("TRANS_FAC_CD", docVO.get("TRANS_FAC_CD"));
		stdDocData.put("DEATH_UNIQUE_ID", docVO.get("DEATH_UNIQUE_ID"));
		stdDocData.put("DEATH_ID", docVO.get("DEATH_ID"));

		updateStdCausesAndAlsoHCause(docVO, stdDocData);
		
		stdDocData.put("DEATH_FILE", docVO.get("DEATH_FILE"));
		
		
		// EHARS_STATUS_CD
		// UPS_CASE_DEF
		// UPS_ELIG
		// NYC_CASE_DEF
		// NYC_ELIG
		// PROCESS_STAGED
		// MATCHED_STATENO
		// EHARS_IMPORT_DATE
		// ASSESS_ASSIGN
		// CURRENT_PROCESS_STATUS
		// QS_PARENT_DOC_ID
		// NYC_SHIP_DATE

		
		
		
		int resultCount = deathDocumentDAO.writeStdDocData(stdDocData);
		
		return resultCount;
	}
	
	private void setFacilityData(String instCode, String pfi, Map<Object, Object> stdDocData){
		
		
		stdDocData.put("INST_CODE", instCode);

		String nysCountyCd = null;
		String nysCountyFipsCd = null;
		if (CommonUtil.isNotNullAndEmpty(instCode)) {
			instCode = instCode.trim();
			if (instCode.startsWith("U") && instCode.length() == 6) {
				instCode = instCode.substring(1);
			}
			
			if (instCode.length() >= 5) {
				nysCountyCd = instCode.substring(0, 2);
				String inst = instCode.substring(2, 5);
				stdDocData.put("INST", inst);
				//nysCountyFipsCd = deathDocumentDAO.getNysFipsCounty(nysCountyCd);
				nysCountyFipsCd = County.getFipsFromGaz(nysCountyCd);
			}
		}

		stdDocData.put("NYS_COUNTY", nysCountyFipsCd);
		
		getFacilityInfo(stdDocData, pfi, instCode, nysCountyCd, nysCountyFipsCd);
		
	}

	public PersonViewVO getStdDeathDocumentForClericalReview(int documentId) {

		PersonViewVO deathDocVO = deathDocumentDAO
				.getStdDeathDocumentForClericalReview(documentId);

		return deathDocVO;
	}

	

	
	public void getFacilityInfo(Map<Object, Object> stdDocData, String pfi, String instCode, String nysCounty, String nysCountyFipsCd) {

		boolean hasFacilityInfo = false;
		if (CommonUtil.isNotNullAndEmpty(pfi) && !"9999".equals(pfi)) {
			Map<String, String>  deathDocFacility = deathDocumentDAO.getFacilityNameByPFI(pfi.trim());
			
			if(deathDocFacility != null && deathDocFacility.size() > 0){
				stdDocData.put("FACILITY_NAME", deathDocFacility.get("FACILITY_NAME"));
				stdDocData.put("FACILITY_TYPE", deathDocFacility.get("FACILITY_TYPE"));
				stdDocData.put("FACILITY_PHONE", deathDocFacility.get("FACILITY_PHONE"));
				stdDocData.put("FACILITY_ADDRESS", deathDocFacility.get("FACILITY_ADDRESS"));
				stdDocData.put("FACILITY_CITY", deathDocFacility.get("FACILITY_CITY"));
				stdDocData.put("FACILITY_STATE", deathDocFacility.get("FACILITY_STATE"));
				stdDocData.put("FACILITY_ZIP", deathDocFacility.get("FACILITY_ZIP"));
				stdDocData.put("FACILITY_PLUS4", deathDocFacility.get("FACILITY_PLUS4"));
				stdDocData.put("FACILITY_COUNTY", deathDocFacility.get("FACILITY_COUNTY"));
				hasFacilityInfo = true;
			} 
			
		} 
		
		if (!hasFacilityInfo && CommonUtil.isNotNullAndEmpty(nysCounty)) {
			if (nysCounty.equals("80")) {
				stdDocData.put("FACILITY_NAME", "OUT OF STATE");
			}else if (CommonUtil.isNotNullAndEmpty(instCode)) {

				if (instCode.equals("188") && !nysCounty.equals("70") && !nysCounty.equals("71") && !nysCounty.equals("72")) {
					stdDocData.put("FACILITY_NAME", "OUT OF STATE HOSPITAL (FOR UPSTATE RECORDS)");
				} else if (instCode.equals("188") && (nysCounty.equals("70") || nysCounty.equals("71") || nysCounty.equals("72"))) {
					stdDocData.put("FACILITY_NAME", "NYC HOSPITAL (NYC RECORDS)");
				} else {
					stdDocData.put("FACILITY_NAME", deathDocumentDAO.getFacilityName(instCode));
					stdDocData.put("FACILITY_COUNTY", nysCountyFipsCd);
				}

			}
		}

	}
	


	/**
	 * Reads the values of 22 cause-of-death related variables from 
	 * docVO (which came from RAW_DEATH_DOC) and transfers them to
	 * stdDocData (headed for STD_DEATH_DOC), and also calculates and
	 * populates "H_CAUSE" into stdDocData.  The 22 columns from RAW_DEATH_DOC
	 * are ICD10, UNDERLYING, and CAUSE1 through CAUSE20.  The logic of 
	 * calculating H_CAUSE is: translate each of the 22 input variables to
	 * an "A" (AIDS-caused), "H" (HIV-caused) or null (neither).  If at least
	 * one is "A", then H_CAUSE is "A"; else if at least one is "H" then
	 * H_CAUSE is "H"; else (neither A nor H is found) H_CAUSE is "N".
	 * @param docVo input fields from RAW_DEATH_DOC
	 * @param stdDocData output fields to store into STD_DEATH_DOC
	 */
	public static void updateStdCausesAndAlsoHCause(Map<Object, Object> docVo, Map<Object, Object> stdDocData) {
		Set<String> hCauseSet = new HashSet<>(); 
		String icd10Code = (String)docVo.get("ICD10");
		if (CommonUtil.isNotNullAndEmpty(icd10Code)) {hCauseSet.add(updateStdCauseAndGetHCause("ICD10", icd10Code, stdDocData));}
		String underlyingCode = (String)docVo.get("UNDERLYING");
		if (CommonUtil.isNotNullAndEmpty(underlyingCode)) {hCauseSet.add(updateStdCauseAndGetHCause("UNDERLYING", underlyingCode, stdDocData));}
		for (String causeName: NycDeathDocumentService.causeColumnNames) {
			String causeCode = (String)docVo.get(causeName);
			if (CommonUtil.isNullOrEmpty(causeCode)) break; // no more causes to test, end of looping
			hCauseSet.add(updateStdCauseAndGetHCause(causeName, causeCode, stdDocData));
		}
		String hCause="N";
		String oiCause = "N";
		
		
		if (hCauseSet.contains("A")){
			oiCause = "Y";
		} 
		if (hCauseSet.contains("H")) {
			hCause = "Y";
		} 
		
		
		
		stdDocData.put("H_CAUSE", hCause);
		stdDocData.put("OI_CAUSE", oiCause);
	}

	/**
	 * Stores code into stdDocData; translates the code into "A", "H" or "N"
	 * using the map in {@link NYCDeathDocServiceImpl#causePrefixMap}.
	 * @param columnName The columnName from RAW_DEATH_DOC (and STD...)
	 * @param code the contents of the column in RAW_DEATH_DOC (and STD...)
	 * @param stdDocData The map of values to store into STD_DEATH_DOC
	 * @return "A", "H" or "N"
	 */
	public static String updateStdCauseAndGetHCause(String columnName, String code, Map<Object, Object> stdDocData) {
		String hCause = null;
		int codeLength = code.length();
		
		if (codeLength > 3) {
			hCause = NycDeathDocumentService.causePrefixMap.get(code.substring(0, 3));
			stdDocData.put(columnName,  code.substring(0, 3)+"."+code.substring(3, codeLength));
		}else{
			hCause = NycDeathDocumentService.causePrefixMap.get(code);
			stdDocData.put(columnName,  code);
		}
		if (hCause == null) hCause = "N";
		return hCause;
	}

}
