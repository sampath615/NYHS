package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms.SchedulerJobsDAO;
import gov.ny.state.doh.bhae.nyehms.utils.County;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathFacility;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycDeathDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedDeathPfiRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycDeathDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * {@inheritDoc}
 * 
 * @author vxp06
 */
@Service
public class NycDeathDocumentService {

	@Autowired
	CodedDeathPfiRepository codedDeathPfiRepository;
	@Autowired
	NycDeathDocRepository nycDeathDocRepository;
	@Autowired
	private SchedulerJobsDAO schedulerJobsDAO;
	@Autowired
	DocumentDAO documentDAO;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	private static Logger log = LoggerFactory.getLogger(NycDeathDocumentService.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();
	
	public static final List<String> nycDthStdRaceColumns = Arrays.asList(
			"RACE_WHITE", "RACE_BLACK", "RACE_INDIAN", "RACE_ASIAN", "RACE_PACIFIC",
			"RACE_MULTIPLE", "RACE_OTHER", "RACE_UNKNOWN");
    public static final String[] oldNycDeathFileNames = {"dth_nyc99", "dth_nyc00", "dth_nyc01", "dth_nyc02"};
    public static final String[] oldNycDeathHispanicIndCodes = {"1", "2", "3", "4", "9"};
    public static final String[] causeColumnNames = {
    		"CAUSE1","CAUSE2","CAUSE3","CAUSE4","CAUSE5","CAUSE6","CAUSE7","CAUSE8",
    		"CAUSE9","CAUSE10","CAUSE11","CAUSE12","CAUSE14","CAUSE14","CAUSE15","CAUSE16","CAUSE17",
    		"CAUSE18","CAUSE19","CAUSE20"};
    public static final Map<String, String> causePrefixMap = new HashMap<>();
    static{
    	causePrefixMap.put("B20", "H");
    	causePrefixMap.put("B21", "H");
    	causePrefixMap.put("B22", "H");
    	causePrefixMap.put("B23", "H");
    	causePrefixMap.put("B24", "H");
    	causePrefixMap.put("R75", "H");
    	causePrefixMap.put("Z21", "H");
    	causePrefixMap.put("A15", "A");
    	causePrefixMap.put("A16", "A");
    	causePrefixMap.put("A17", "A");
    	causePrefixMap.put("A18", "A");
    	causePrefixMap.put("A19", "A");
    	causePrefixMap.put("A31", "A");
    	causePrefixMap.put("B58", "A");
    	causePrefixMap.put("B59", "A");
    	causePrefixMap.put("C46", "A");
    }
    
	
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
	public NycDeathDocData getNycDeathDoc(int docId, BindingResult result,
			UserObject userObject, String sessionID) {

		boolean rollbackFlag = false;
		NycDeathDocData stdDeathDocVO = null;
		log.debug(nyehmsLog + " fetching for docId : " + docId);
		try {
			stdDeathDocVO = nycDeathDocRepository.findDocument(docId);
			
			if(stdDeathDocVO == null){
				return null;
			}
			
			String H_CAUSE = stdDeathDocVO.stdNycDeathDoc().getHcause();
			String OI_CAUSE = stdDeathDocVO.stdNycDeathDoc().getOiCause();
			
			if("Y".equals(H_CAUSE) && "Y".equals(OI_CAUSE)){
				stdDeathDocVO = stdDeathDocVO.withCauseOfDeath("Opportunistic Infection (OI) Cause of Death");
			}else if("Y".equals(H_CAUSE) && "N".equals(OI_CAUSE)){
				stdDeathDocVO = stdDeathDocVO.withCauseOfDeath("HIV Cause of Death");
			}else{
				
				Map<String, Object> inputParameterMap = new HashMap<>();
				
				inputParameterMap.put("docId", docId);
				inputParameterMap.put("TABLE_NAME", "STD_NYC_DEATH_DOC");
				
				String meetsCDC = documentDAO.checkStatenoMeetsCDC(inputParameterMap);
				
				if("Y".equals(meetsCDC)){
					stdDeathDocVO = stdDeathDocVO.withCauseOfDeath("Meets Case Definition");
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

	/**
	 * Takes the raw NYC death data,
	 * calls {@link NycDeathDocumentService#getNycFacilityInfo(Map, String, String, String, String)
	 * to get facility information,
	 *  calls
	 * {@link NycDeathDocumentService#getStandardizedNycDeathData(Map, BigDecimal)}
	 *  to create the
	 * standardized data, then calls
	 * {@code NycDeathDocMapping.toStdDoc} and {@code nycDeathDocRepository.saveAndFlush}
	 *  a method to insert the standardized
	 * data into the database
	 */
	public int writeNycRawDeathDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId, Map<String, Map<String, String>> zipCodeReferenceMap) {
		
		// All the fields directly from RAW_NYC_DEATH_DOC that are going into
		// STD_NYC_DEATH_DOC, plus some derived fields
		Map<Object, Object> stdDocData = new HashMap<>();
		// All the FACILITY_XXX columns from the CODED_DEATH_PFI table,
		// to insert into STD_NYC_DEATH_DOC
		
		// Two fields derived from INST_CODE: NYC_FAC_CODE and INST
		
		
		String nycInstCode = (String)docVO.get("INST_CODE");
		
		
		stdDocData = getStandardizedNycDeathData(docVO, documentId, zipCodeReferenceMap);
		
		String pFI = (String)docVO.get("PFI");
		
		if (CommonUtil.isNullOrEmpty(pFI) || "9999".equals(pFI)) {
			pFI = (String)docVO.get("PFI_NEW");
		}
		
		if (CommonUtil.isNotNullAndEmpty(pFI) && pFI.length() == 6) {
			pFI = pFI.substring(2); // truncate two leading zeros
		}
		
		stdDocData.put("PFI",  pFI);
		
		setNYCFacilityData(pFI, nycInstCode, stdDocData);
		
		nycDeathDocRepository.saveAndFlush(NycDeathDocMapping.toStdDoc(stdDocData));
		return 1;
	}
	
	public void setNYCFacilityData(String pFI, String nycInstCode, Map<Object, Object>  stdDocData){
		
		Map<String, String> instCodeDerived = null;
		String nycFacCode = null;
		String inst = null;
		Map<String, String> nycFacilityInfo = null;
		
		instCodeDerived =  getNycFacCodeAndInst(nycInstCode); 
		if (instCodeDerived != null) {	
			nycFacCode = instCodeDerived.get("NYC_FAC_CODE");
			inst = instCodeDerived.get("INST");
		}
		
		nycFacilityInfo = getNycFacilityInfo(pFI, nycFacCode);
		
		stdDocData.put("NYC_FAC_CODE", nycFacCode);
		stdDocData.put("INST", inst);
		stdDocData.put("INST_CODE", nycInstCode);
		stdDocData.putAll(nycFacilityInfo);
		
	}

	public static boolean isOldNycDeathFile (String fileName) {
		return Arrays.asList(oldNycDeathFileNames).contains(fileName);
	}

	/**
	 * Receives a row from RAW_NYC_DEATH_DOC and returns the corresponding
	 * values to insert into STD_NYC_DEATH_DOC.
	 * TODO Figure out how to unit test this, seeing as how it hits the 
	 * database to get the Facility Information.
	 * 
	 * @param docVO The incoming values from RAW_NYC_DEATH_DOC; not an actual
	 * VO object but a map containing the needed fields.
	 * @param documentId The DOCUMENT_ID value of the row in RAW_NYC_DEATH_DOC
	 * that we are standardizing.
	 * @return The standardized values ready to populate an INSERT INTO STD_NYC_DEATH_DOC
	 */
	
	public Map<Object, Object> getStandardizedNycDeathData (
			Map<Object, Object> docVO,
			BigDecimal documentId, Map<String, Map<String, String>> zipCodeReferenceMap) {

		final String ONE = "1";
		final String ZERO = "0";
		final String XX = "XX";
		final String ZZ = "ZZ";
		final String DASH = "-";
		final String DASHDASH = "--";

		Map<Object, Object> stdDocData = new HashMap<>();

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
				CommonUtil.addressValidation(new StringBuilder().append(resStreetNo)
						.append(resStreet)
						.append(resStreetType)
						.append(aptNo).toString())); // Coming from 5 fields of RAW Table
		
		
		if(zipCodeReferenceMap == null || zipCodeReferenceMap.size() == 0){
			
			throw new DataRetrievalFailureException(
					"There is no Zipcode reference data ");
		}
		
		String personZip = null;
		
		String RES_ZIP = CommonUtil.formatZipAndPlus4Code((String) docVO
				.get("RES_ZIP"));
		if (CommonUtil.isNotNullAndEmpty(RES_ZIP)) {
			if (RES_ZIP.length() > 5) {
				
				personZip = RES_ZIP.substring(0, 5);
				
				stdDocData.put("PERSON_PLUS4", RES_ZIP.substring(5));
			} else {
				personZip =  RES_ZIP;
			}
		}
		
		Map<String, String> zipCodeAddressDetails = zipCodeReferenceMap.get(personZip);

		stdDocData.put("PERSON_ZIP", personZip);
		
		if(CommonUtil.isNotNullAndEmpty(personZip) && zipCodeAddressDetails != null && zipCodeAddressDetails.size() != 0){
		
			stdDocData.put("PERSON_CITY", zipCodeAddressDetails.get("CITY"));
	
			stdDocData.put("PERSON_STATE", zipCodeAddressDetails.get("STATE_CD"));
			
			stdDocData.put("PERSON_COUNTY", zipCodeAddressDetails.get("COUNTY_FIPS_CD"));
		}else{
			
			stdDocData.put("PERSON_CITY", docVO.get("RES_CITY"));
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

		stdDocData.put("CREATE_UID", "900");
		stdDocData.put("CURRENT_PROCESS_STATUS", "5");
		stdDocData.put("CREATE_DATE", new Date(Calendar.getInstance()
				.getTimeInMillis()));
		String deathFileName = (String) docVO.get("DEATH_FILE");
		stdDocData.put("DEATH_FILE", deathFileName);

		//*************** RACE AND ETHNICITY ********************
		// Initializations
		for (String raceCol: nycDthStdRaceColumns) { //Initialize all 8 RACE_XXX to 0
			String raceColumnNameToInitialize = (String)raceCol;
			stdDocData.put(raceColumnNameToInitialize, "0");
		}
		stdDocData.put("ETHNICITY", "09");
		
		if (isOldNycDeathFile(deathFileName) ) {
			//******************** 1999 to 2002 *******************
			//*********** RACE **************
			//Set 6 RACE variables; ignore RACE_MULTIPLE and RACE_OTHER
			//Initialize the 6 to "0"
			String rawRace = (String) docVO.get("RACE");
			String stdRaceColumnToSet = LabCommonUtils.getOldNycRaceColumn(rawRace);
			stdDocData.put(stdRaceColumnToSet, "1");

			//************ ETHNICITY *************
			String oldNycHispOriginInd = (String) docVO.get("HISP_ORIGIN_IND");
			if (oldNycHispOriginInd == null) {
				oldNycHispOriginInd = "X";} // guard against NullPointerException
			if (oldNycHispOriginInd.equals("1") ||
					oldNycHispOriginInd.equals("2")||
					oldNycHispOriginInd.equals("3") ||
					oldNycHispOriginInd.equals("4") ||
					oldNycHispOriginInd.equals("9")) {
				stdDocData.put("ETHNICITY",  "01");
			} else if (oldNycHispOriginInd.equals("0")) {
				stdDocData.put("ETHNICITY",  "02");
			}
		} else {
				//***************** 2003 and later *****************
				//***************** Ethnicity **********************
				String ethnicity = "";
				if (ONE.equals((String) docVO.get("HISP_ORIGIN_MEXICAN"))
						|| ONE.equals((String) docVO.get("HISP_ORIGIN_PUERTO"))
						|| ONE.equals((String) docVO.get("HISP_ORIGIN_CUBAN"))
						|| ONE.equals((String) docVO.get("HISP_ORIGIN_OTHER"))) {
					ethnicity = "01";
				} else if (ZERO.equals((String) docVO.get("HISP_ORIGIN_MEXICAN"))
						&& ZERO.equals((String) docVO.get("HISP_ORIGIN_PUERTO"))
						&& ZERO.equals((String) docVO.get("HISP_ORIGIN_CUBAN"))
						&& ZERO.equals((String) docVO.get("HISP_ORIGIN_OTHER"))) {
					ethnicity = "02";
				}
				stdDocData.put("ETHNICITY", ethnicity);
				// REVIEW_UID
				// REVIEW_DATE
				// MATCH_CD
				//********************* RACE *******************
				int raceCount = 0;
				String raceI = (String)docVO.get("RACE_I");
				if (raceI != null && raceI.equals("1")) {
					stdDocData.put("RACE_INDIAN",  "1");
					raceCount++;
				}
				String raceB = (String)docVO.get("RACE_B");
				if (raceB != null && raceB.equals("1")) {
					stdDocData.put("RACE_BLACK",  "1");
					raceCount++;
				}
				String raceW = (String)docVO.get("RACE_W");
				if (raceW != null && raceW.equals("1")) {
					stdDocData.put("RACE_WHITE",  "1");
					raceCount++;
				}
				String raceO = (String)docVO.get("RACE_OTH");
				if (raceO != null && raceO.equals("1")) {
					stdDocData.put("RACE_OTHER", "1");
					raceCount++;
				}
				
				stdDocData.put("RACE_ASIAN", 0);
				if (ONE.equals((String) docVO.get("RACE_A"))
						|| ONE.equals((String) docVO.get("RACE_A_OTH"))
						|| ONE.equals((String) docVO.get("RACE_C"))
						|| ONE.equals((String) docVO.get("RACE_F"))
						|| ONE.equals((String) docVO.get("RACE_J"))
						|| ONE.equals((String) docVO.get("RACE_K"))
						|| ONE.equals((String) docVO.get("RACE_V"))) {
					stdDocData.put("RACE_ASIAN", 1);
					raceCount++;
				}

				stdDocData.put("RACE_PACIFIC", 0);
				if (ONE.equals((String) docVO.get("RACE_H"))
						|| ONE.equals((String) docVO.get("RACE_P_OTH"))
						|| ONE.equals((String) docVO.get("RACE_S"))
						|| ONE.equals((String) docVO.get("RACE_G"))) {
					stdDocData.put("RACE_PACIFIC", 1);
					raceCount++;
				}
			
				if (raceCount > 1) {
					stdDocData.put("RACE_MULTIPLE", "1");
				}
			}// End of Race and Ethnicity
				
		// ADU_CD
		stdDocData.put("CURRENT_GENDER", LabCommonUtils
		.checkCurrentGenderData((String) docVO.get("SEX")));

		stdDocData.put("DEATH_CERT", docVO.get("CERT"));
		stdDocData.put("DEATH_CERT_LICENSE", docVO.get("DEATH_CERT_LICENSE"));

		/* ******************* STD_NYC_DEATH_DOC.DEATH_COUNTY ******************** */
		/*For NYC, DEATH_CNTY (D1A) is useless because it is always "70", the 
		 * Gazetteer code for "NYC".  In NYC death docs, "DEATH_CITY" (D1B)
		 * contains the Gazetteer code for NYC boroughs, 93-97.  So that is
		 * how we get the county.
		 */
		String nycDeathBoroughGaz =(String) docVO.get("DEATH_CITY");
		String nycDeathCountyFips = County.getFipsFromGaz(nycDeathBoroughGaz);
		stdDocData.put("DEATH_COUNTY", nycDeathCountyFips);
		
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

		String fatherLName = (String) docVO.get("FATHER_LNAME");
		if (CommonUtil.isNotNullAndEmpty(fatherLName)
				&& (DASHDASH.equals(fatherLName) || DASH.equals(fatherLName))) {
			fatherLName = "";
		} else {
			fatherLName = CommonUtil.removeSingleQuote(fatherLName);
		}
		stdDocData.put("FATHER_LNAME", fatherLName);

		String motherMaidenName = (String) docVO.get("MOTHER_MAIDEN");
		if (CommonUtil.isNotNullAndEmpty(motherMaidenName)
				&& (DASHDASH.equals(motherMaidenName) || DASH
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

		String birthPlace = (String) docVO.get("BIRTH_PLACE");
		if (CommonUtil.isNotNullAndEmpty(birthPlace)
				&& (ZZ.equals(birthPlace) || XX.equals(birthPlace))) {
			birthPlace = "";
		}
		stdDocData.put("BIRTH_STATE", birthPlace);
		stdDocData.put("LOCAL_REG_NO", docVO.get("LOCAL_REG_NO"));
		stdDocData.put("FIPS_PLACE", docVO.get("FIPS_PLACE"));
		String instCode = (String) docVO.get("INST_CODE");
		stdDocData.put("INST_CODE", instCode);
		
		String nysCountyFipsCd = null;
		stdDocData.put("NYS_COUNTY", nysCountyFipsCd);
		stdDocData.put("TRANS_FAC_CD", docVO.get("TRANS_FAC_CD"));
		stdDocData.put("DEATH_UNIQUE_ID", docVO.get("DEATH_UNIQUE_ID"));
		stdDocData.put("DEATH_ID", docVO.get("DEATH_ID"));

		updateStdCausesAndAlsoHCause(docVO, stdDocData);
		
//		getFacilityInfo(stdDocData, pfi, instCode, nysCountyCd, nysCountyFipsCd);
		return stdDocData;
		
	}
	
	/**
	 * Reformats the RAW_NYC_DEATH_DOC.INST_CODE value to match the
	 * CODED_DEATH_PFI.NYC_FAC_CD value.
	 * TODO: confirm that this transformation is correct.  The current assumption
	 * is based on observation of the PFI/INST_CODE pairings in the data: where
	 * both exist in a row of data, the same institution is found in CODED_DEATH_PFI and the 
	 * NYC Master Institution List; subsequently, entries from the NYC List were added
	 * to CODED_DEATH_PFI.
	 * TODO: user java.util.regex to properly handle random input strings;
	 * the current solution assumes only the codes of expected values and forms based on
	 * values occurring in the 1999-2015 data.
	 * @param instCode
	 * @return
	 */
//		if(instCode.equals("N0 000") ||
//           instCode.equals("0000") ||
//           instCode.equals("N0000") ||
//		} else { //assume format is "N_ ____" or "N____"
//			if(instCode.length() == 6) {
//				nycFacCode = 
//						instCode.substring(3,4)
//						+ instCode.substring(1, 2)
//				nycFacCode =
//						instCode.substring(2,3)
//						+ instCode.substring(1,2)
	
	/**
	 * After eliminating invalid values for instCode, creates the following
	 * derived fields:
	 * <ul>
	 * <li>nycFacCode is calculated by mapping either N1 234 and N1234 to 2134</li>
	 * <li>inst is calculated by mapping either N1 234 or N1234 to 234</li>
	 * </ul>
	 * @param instCode
	 * @return
	 */
	public static Map<String, String> getNycFacCodeAndInst(String instCode) {

		Map<String, String> results = new HashMap<>();
		results.put("NYC_FAC_CODE", null);
		results.put("INST",  null);
		String nycFacCode = null;
		String inst = null;
		if (instCode != null) {
			if(instCode.equals("N0 000") ||
					instCode.equals("0000") ||
					instCode.equals("N0000") ||
					instCode.equals("N8-00")) {
				// do nothing; leave results null
			} else { //assume format is "N_ ___" or "N____"
				if(instCode.length() == 6) {
					nycFacCode = 
							instCode.substring(3,4)
							+ instCode.substring(1, 2)
							+ instCode.substring(4,6);
					inst = instCode.substring(3,6);
				} else if (instCode.length() == 5) {
					nycFacCode =
							instCode.substring(2,3)
							+ instCode.substring(1,2)
							+ instCode.substring(3,5);
					inst = instCode.substring(2,5);
				} 
				results.put("NYC_FAC_CODE", nycFacCode);
				results.put("INST",  inst);
			}
		} // end "if (instCode != null)"
		return results;
	}

	/**
	 * Based on the facility-identifying information, retrieves the correct facility
	 * information from CODE_DEATH_PFI
	 * 
	 * @param pfi The Permanent Facility Identifier from RAW_NYC_DEATH_DOC.PFI
	 * @param pfiNew The PFI from RAW_NYC_DEATH_DOC.PFI_NEW.  This field was new
	 * as of the 2015 file and is a 6-character string where the first two characters
	 * are '00' and the last four characters are a normal PFI (until we start to get some
	 * PFIs that have a numerical value > 9999).
	 * @param nycFacCode The key into the NYC Master Institution Master List, 
	 * derived from the INST_CODE as performed by 
	 * {@link this#getNycFacCode(String)}
	 * @return A Map of as many of the FACILITY fields as are obtainable;
	 * empty if none of the facility-identifying information matches into the
	 * CODED_DEATH_PFI table.
	 */
	public Map<String, String> getNycFacilityInfo(String pfi, String nycFacCode) {
		
		DeathFacility facility = null;
		if (CommonUtil.isNotNullAndEmpty(pfi) && !pfi.equals("9999")) {
			facility = codedDeathPfiRepository.findFacilityByPfi(pfi);
		} else if (nycFacCode != null) {
			facility = codedDeathPfiRepository.findFacilityByNycFacCode(nycFacCode);
		}
		// only the columns with a value, as the former MyBatis map (a missing column does not overwrite)
		Map<String, String> deathDocFacilityInfo = new HashMap<>();
		if (facility != null) {
			putIfNotNull(deathDocFacilityInfo, "FACILITY_TYPE", facility.facilityType());
			putIfNotNull(deathDocFacilityInfo, "FACILITY_NAME", facility.facilityName());
			putIfNotNull(deathDocFacilityInfo, "FACILITY_ADDRESS", facility.facilityAddress());
			putIfNotNull(deathDocFacilityInfo, "FACILITY_CITY", facility.facilityCity());
			putIfNotNull(deathDocFacilityInfo, "FACILITY_STATE", facility.facilityState());
			putIfNotNull(deathDocFacilityInfo, "FACILITY_ZIP", facility.facilityZip());
			putIfNotNull(deathDocFacilityInfo, "FACILITY_PLUS4", facility.facilityPlus4());
			putIfNotNull(deathDocFacilityInfo, "FACILITY_PHONE", facility.facilityPhone());
			putIfNotNull(deathDocFacilityInfo, "FACILITY_COUNTY", facility.facilityCounty());
		}
		return deathDocFacilityInfo;
	}

	private static void putIfNotNull(Map<String, String> map, String key, String value) {
		if (value != null) {
			map.put(key, value);
		}
	}


	/**
	 * Reads the values of 22 cause-of-death related variables from 
	 * docVO (which came from RAW_NYC_DEATH_DOC) and transfers them to
	 * stdDocData (headed for STD_NYC_DEATH_DOC), and also calculates and
	 * populates "H_CAUSE" into stdDocData.  The 22 columns from RAW_NYC_DEATH_DOC
	 * are ICD10, UNDERLYING, and CAUSE1 through CAUSE20.  The logic of 
	 * calculating H_CAUSE is: translate each of the 22 input variables to
	 * an "A" (AIDS-caused), "H" (HIV-caused) or null (neither).  If at least
	 * one is "A", then H_CAUSE is "A"; else if at least one is "H" then
	 * H_CAUSE is "H"; else (neither A nor H is found) H_CAUSE is "N".
	 * @param docVo input fields from RAW_NYC_DEATH_DOC
	 * @param stdDocData output fields to store into STD_NYC_DEATH_DOC
	 */
	public static void updateStdCausesAndAlsoHCause(Map<Object, Object> docVo, Map<Object, Object> stdDocData) {
		Set<String> hCauseSet = new HashSet<>(); 
		String icd10Code = (String)docVo.get("ICD10");
		if (CommonUtil.isNotNullAndEmpty(icd10Code)) {hCauseSet.add(updateStdCauseAndGetHCause("ICD10", icd10Code, stdDocData));}
		String underlyingCode = (String)docVo.get("UNDERLYING");
		if (CommonUtil.isNotNullAndEmpty(underlyingCode)) {hCauseSet.add(updateStdCauseAndGetHCause("UNDERLYING", underlyingCode, stdDocData));}
		for (String causeName: causeColumnNames) {
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
	 * @param columnName The columnName from RAW_NYC_DEATH_DOC (and STD...)
	 * @param code the contents of the column in RAW_NYC_DEATH_DOC (and STD...),
	 * assumed to be non-null
	 * @param stdDocData The map of values to store into STD_NYC_DEATH_DOC
	 * @return "A", "H" or "N"
	 */
	public static String updateStdCauseAndGetHCause(String columnName, String code, Map<Object, Object> stdDocData) {
		String hCause = null;
		
		int codeLength = code.length(); 
		
		if (codeLength > 3) {
			hCause = causePrefixMap.get(code.substring(0, 3));
			stdDocData.put(columnName,  code.substring(0, 3)+"."+code.substring(3, codeLength));
		}else{
			hCause = causePrefixMap.get(code);
			stdDocData.put(columnName,  code);
		}
		if (hCause == null) hCause = "N";
		return hCause;
	}

	

}
