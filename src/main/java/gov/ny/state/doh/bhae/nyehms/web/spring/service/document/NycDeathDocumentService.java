package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DeathRecordRules.text;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms.SchedulerJobsDAO;
import gov.ny.state.doh.bhae.nyehms.utils.County;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathFacility;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycDeathDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedDeathPfiRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycDeathDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DeathRecordRules.ZipCode;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;
import lombok.RequiredArgsConstructor;

/**
 * The NYC death records (STD_NYC_DEATH_DOC): the NYC death document screen, the import that standardizes a
 * received record (RAW_NYC_DEATH_DOC), and the NYC facility of a death record (also used by
 * {@link DeathDocumentService} for NYS records that died in NYC).
 */
@Service
@RequiredArgsConstructor
public class NycDeathDocumentService {

	private static final Logger log = LoggerFactory.getLogger(NycDeathDocumentService.class);

	/** Who creates the standardized rows: the import. */
	private static final String IMPORT_USER_ID = "900";
	private static final String STANDARDIZED_PROCESS_STATUS = "5";
	private static final String YES = "Y";
	private static final String ONE = "1";
	private static final String ZERO = "0";
	private static final List<String> RACE_COLUMNS = List.of(
			"RACE_WHITE", "RACE_BLACK", "RACE_INDIAN", "RACE_ASIAN", "RACE_PACIFIC", "RACE_MULTIPLE", "RACE_OTHER", "RACE_UNKNOWN");
	/** The 1999 to 2002 files, with the old race and ethnicity codes. */
	private static final Set<String> OLD_DEATH_FILES = Set.of("dth_nyc99", "dth_nyc00", "dth_nyc01", "dth_nyc02");
	private static final Set<String> OLD_HISPANIC_CODES = Set.of("1", "2", "3", "4", "9");
	private static final String[] HISPANIC_ORIGINS = {"HISP_ORIGIN_MEXICAN", "HISP_ORIGIN_PUERTO", "HISP_ORIGIN_CUBAN", "HISP_ORIGIN_OTHER"};
	private static final String[] ASIAN_RACES = {"RACE_A", "RACE_A_OTH", "RACE_C", "RACE_F", "RACE_J", "RACE_K", "RACE_V"};
	private static final String[] PACIFIC_RACES = {"RACE_H", "RACE_P_OTH", "RACE_S", "RACE_G"};
	private static final Set<String> UNKNOWN_STATES = Set.of("--", "ZZ", "XX");
	private static final Set<String> UNKNOWN_COUNTIES = Set.of("000", "999");
	private static final Set<String> UNKNOWN_BIRTH_PLACES = Set.of("ZZ", "XX");
	private static final Set<String> UNKNOWN_NAMES = Set.of("--", "-");
	/** Institution codes that name no institution. */
	private static final Set<String> NO_INSTITUTION_CODES = Set.of("N0 000", "0000", "N0000", "N8-00");

	private final CodedDeathPfiRepository codedDeathPfiRepository;
	private final NycDeathDocRepository nycDeathDocRepository;
	private final SchedulerJobsDAO schedulerJobsDAO;
	private final DocumentDAO documentDAO;
	private final CommonExceptionHandler commonExceptionHandler;

	/**
	 * The NYC death record of the document screen, with its cause of death.
	 *
	 * @return the record, or null when there is none; when a read fails {@code result} is marked failed and the
	 *         record is returned as far as it was read (without its cause of death, or null)
	 */
	public NycDeathDocData getNycDeathDoc(int docId, BindingResult result, UserObject userObject, String sessionID) {
		log.debug("fetching NYC death document {}", docId);
		NycDeathDocData doc = null;
		try {
			doc = nycDeathDocRepository.findDocument(docId);
			if (doc == null) {
				return null;
			}
			String causeOfDeath = DeathRecordRules.causeOfDeath(doc.stdNycDeathDoc().getHcause(), doc.stdNycDeathDoc().getOiCause(),
					() -> meetsCaseDefinition(docId));
			if (causeOfDeath != null) {
				doc = doc.withCauseOfDeath(causeOfDeath);
			}
		} catch (Exception e) {
			log.error("Failed to read NYC death document {}: {}", docId, e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
			commonExceptionHandler.populateBindObjTrsnFail(result);
		}
		return doc;
	}

	private boolean meetsCaseDefinition(int docId) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("docId", docId);
		parameters.put("TABLE_NAME", "STD_NYC_DEATH_DOC");
		return YES.equals(documentDAO.checkStatenoMeetsCDC(parameters));
	}

	/**
	 * Writes the standardized record (STD_NYC_DEATH_DOC) of a received record, with its facility.
	 *
	 * @param docVO the received record (a RAW_NYC_DEATH_DOC row), value per column name; not changed
	 * @param zipCodeReferenceMap city, state and county per zip code; must not be empty
	 * @return 1
	 * @throws DataRetrievalFailureException when there is no zip code reference data
	 */
	public int writeNycRawDeathDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId,
			Map<String, Map<String, String>> zipCodeReferenceMap) {
		if (zipCodeReferenceMap == null || zipCodeReferenceMap.isEmpty()) {
			throw new DataRetrievalFailureException("There is no Zipcode reference data ");
		}
		Map<Object, Object> std = standardizedRecord(docVO, zipCodeReferenceMap);
		String pfi = DeathRecordRules.pfi(docVO);
		std.put("PFI", pfi);
		setNYCFacilityData(pfi, text(docVO, "INST_CODE"), std);

		nycDeathDocRepository.saveAndFlush(NycDeathDocMapping.toStdDoc(std));
		return 1;
	}

	/**
	 * Puts the NYC institution (INST_CODE as given, NYC_FAC_CODE and INST derived from it) and the facility found
	 * by PFI, or else by NYC facility code, into a standardized record. Only the facility columns with a value are
	 * put, so the others keep what the record has.
	 */
	public void setNYCFacilityData(String pfi, String nycInstCode, Map<Object, Object> std) {
		NycInstitution institution = NycInstitution.of(nycInstCode);
		std.put("NYC_FAC_CODE", institution.nycFacCode());
		std.put("INST", institution.inst());
		std.put("INST_CODE", nycInstCode);

		DeathFacility facility = null;
		if (CommonUtil.isNotNullAndEmpty(pfi) && !DeathRecordRules.UNKNOWN_PFI.equals(pfi)) {
			facility = codedDeathPfiRepository.findFacilityByPfi(pfi);
		} else if (institution.nycFacCode() != null) {
			facility = codedDeathPfiRepository.findFacilityByNycFacCode(institution.nycFacCode());
		}
		if (facility != null) {
			putIfNotNull(std, "FACILITY_TYPE", facility.facilityType());
			putIfNotNull(std, "FACILITY_NAME", facility.facilityName());
			putIfNotNull(std, "FACILITY_ADDRESS", facility.facilityAddress());
			putIfNotNull(std, "FACILITY_CITY", facility.facilityCity());
			putIfNotNull(std, "FACILITY_STATE", facility.facilityState());
			putIfNotNull(std, "FACILITY_ZIP", facility.facilityZip());
			putIfNotNull(std, "FACILITY_PLUS4", facility.facilityPlus4());
			putIfNotNull(std, "FACILITY_PHONE", facility.facilityPhone());
			putIfNotNull(std, "FACILITY_COUNTY", facility.facilityCounty());
		}
	}

	private static void putIfNotNull(Map<Object, Object> map, String key, String value) {
		if (value != null) {
			map.put(key, value);
		}
	}

	/**
	 * The NYC facility code and institution derived from an institution code "N1 234" or "N1234": facility code
	 * 2134, institution 234; both null for no institution or another form.
	 */
	private record NycInstitution(String nycFacCode, String inst) {

		static NycInstitution of(String instCode) {
			if (instCode == null || NO_INSTITUTION_CODES.contains(instCode)) {
				return new NycInstitution(null, null);
			}
			if (instCode.length() == 6) {   // "N1 234"
				return new NycInstitution(instCode.substring(3, 4) + instCode.substring(1, 2) + instCode.substring(4, 6), instCode.substring(3, 6));
			}
			if (instCode.length() == 5) {   // "N1234"
				return new NycInstitution(instCode.substring(2, 3) + instCode.substring(1, 2) + instCode.substring(3, 5), instCode.substring(2, 5));
			}
			return new NycInstitution(null, null);
		}
	}

	/** The standardized record of a received record, without PFI and facility. */
	private Map<Object, Object> standardizedRecord(Map<Object, Object> raw, Map<String, Map<String, String>> zipCodeReferenceMap) {
		Map<Object, Object> std = new HashMap<>();
		std.put("DOCUMENT_ID", raw.get("DOCUMENT_ID"));
		std.put("FNAME", CommonUtil.NameValidation(text(raw, "FIRST")));
		std.put("LNAME", CommonUtil.NameValidation(text(raw, "LAST")));
		std.put("MNAME", CommonUtil.NameValidation(text(raw, "MIDDLE")));
		std.put("DOB_TEXT", dateText(text(raw, "DOB")));
		std.put("SSN", CommonUtil.formatSSNValue(text(raw, "SSN")));
		putResidence(raw, zipCodeReferenceMap, std);
		std.put("CREATE_UID", IMPORT_USER_ID);
		std.put("CURRENT_PROCESS_STATUS", STANDARDIZED_PROCESS_STATUS);
		std.put("CREATE_DATE", new Date(Calendar.getInstance().getTimeInMillis()));
		String deathFile = text(raw, "DEATH_FILE");
		std.put("DEATH_FILE", deathFile);
		putEthnicityAndRace(raw, deathFile != null && OLD_DEATH_FILES.contains(deathFile), std);
		std.put("CURRENT_GENDER", LabCommonUtils.checkCurrentGenderData(text(raw, "SEX")));
		std.put("DEATH_CERT", raw.get("CERT"));
		std.put("DEATH_CERT_LICENSE", raw.get("DEATH_CERT_LICENSE"));
		// DEATH_CNTY is always 70 (NYC) here; DEATH_CITY holds the Gazetteer code of the borough (93 to 97)
		std.put("DEATH_COUNTY", County.getFipsFromGaz(text(raw, "DEATH_CITY")));
		putDeath(raw, std);
		std.put("INST_CODE", raw.get("INST_CODE"));
		std.put("NYS_COUNTY", null);
		std.put("TRANS_FAC_CD", raw.get("TRANS_FAC_CD"));
		std.put("DEATH_UNIQUE_ID", raw.get("DEATH_UNIQUE_ID"));
		std.put("DEATH_ID", raw.get("DEATH_ID"));
		DeathRecordRules.putCauses(raw, std);
		return std;
	}

	/**
	 * Address, zip and plus-4; city, state and county from the zip code reference when the zip is known there, else
	 * RES_CITY and the FIPS state and county without their unknown codes.
	 */
	private void putResidence(Map<Object, Object> raw, Map<String, Map<String, String>> zipCodeReferenceMap, Map<Object, Object> std) {
		std.put("PERSON_ADDRESS", CommonUtil.addressValidation(DeathRecordRules.residentAddress(raw)));
		ZipCode zip = ZipCode.of(text(raw, "RES_ZIP"));
		std.put("PERSON_PLUS4", zip.plus4());
		std.put("PERSON_ZIP", zip.zip());

		Map<String, String> zipPlace = zipCodeReferenceMap.get(zip.zip());
		if (CommonUtil.isNotNullAndEmpty(zip.zip()) && zipPlace != null && !zipPlace.isEmpty()) {
			std.put("PERSON_CITY", zipPlace.get("CITY"));
			std.put("PERSON_STATE", zipPlace.get("STATE_CD"));
			std.put("PERSON_COUNTY", zipPlace.get("COUNTY_FIPS_CD"));
		} else {
			std.put("PERSON_CITY", raw.get("RES_CITY"));
			String state = text(raw, "FIPS_ST");
			std.put("PERSON_STATE", state != null && UNKNOWN_STATES.contains(state) ? "" : state);
			String county = text(raw, "FIPS_CNTY");
			std.put("PERSON_COUNTY", county != null && UNKNOWN_COUNTIES.contains(county) ? "" : county);
		}
	}

	/** The race flags ("0" unless flagged) and the ethnicity ("09" unless known), by the codes of the file's years. */
	private static void putEthnicityAndRace(Map<Object, Object> raw, boolean oldFile, Map<Object, Object> std) {
		for (String race : RACE_COLUMNS) {
			std.put(race, ZERO);
		}
		std.put("ETHNICITY", "09");
		if (oldFile) {
			putOldEthnicityAndRace(raw, std);
		} else {
			putEthnicityAndRace(raw, std);
		}
	}

	/** 1999 to 2002: one race code (RACE); HISP_ORIGIN_IND 1-4 or 9 is Hispanic ("01"), 0 is not ("02"). */
	private static void putOldEthnicityAndRace(Map<Object, Object> raw, Map<Object, Object> std) {
		std.put(LabCommonUtils.getOldNycRaceColumn(text(raw, "RACE")), ONE);
		String hispanicOrigin = text(raw, "HISP_ORIGIN_IND");
		if (hispanicOrigin != null && OLD_HISPANIC_CODES.contains(hispanicOrigin)) {
			std.put("ETHNICITY", "01");
		} else if (ZERO.equals(hispanicOrigin)) {
			std.put("ETHNICITY", "02");
		}
	}

	/**
	 * 2003 and later: Hispanic ("01") when a Hispanic origin flag is 1, not Hispanic ("02") when all four are 0,
	 * else empty; the race flags, and RACE_MULTIPLE when more than one race is flagged.
	 */
	private static void putEthnicityAndRace(Map<Object, Object> raw, Map<Object, Object> std) {
		std.put("ETHNICITY", anyIsOne(raw, HISPANIC_ORIGINS) ? "01" : allAreZero(raw, HISPANIC_ORIGINS) ? "02" : "");
		int races = 0;
		races += putRaceIfOne(raw, "RACE_I", "RACE_INDIAN", std);
		races += putRaceIfOne(raw, "RACE_B", "RACE_BLACK", std);
		races += putRaceIfOne(raw, "RACE_W", "RACE_WHITE", std);
		races += putRaceIfOne(raw, "RACE_OTH", "RACE_OTHER", std);
		boolean asian = anyIsOne(raw, ASIAN_RACES);
		std.put("RACE_ASIAN", asian ? 1 : 0);
		boolean pacific = anyIsOne(raw, PACIFIC_RACES);
		std.put("RACE_PACIFIC", pacific ? 1 : 0);
		races += (asian ? 1 : 0) + (pacific ? 1 : 0);
		if (races > 1) {
			std.put("RACE_MULTIPLE", ONE);
		}
	}

	private static int putRaceIfOne(Map<Object, Object> raw, String field, String column, Map<Object, Object> std) {
		if (ONE.equals(text(raw, field))) {
			std.put(column, ONE);
			return 1;
		}
		return 0;
	}

	private static boolean anyIsOne(Map<Object, Object> raw, String... fields) {
		for (String field : fields) {
			if (ONE.equals(text(raw, field))) {
				return true;
			}
		}
		return false;
	}

	private static boolean allAreZero(Map<Object, Object> raw, String... fields) {
		for (String field : fields) {
			if (!ZERO.equals(text(raw, field))) {
				return false;
			}
		}
		return true;
	}

	/** Date of death, age, parents (as given), pregnancy, birth state and the other copied fields. */
	private void putDeath(Map<Object, Object> raw, Map<Object, Object> std) {
		std.put("DOD_TEXT", dateText(text(raw, "DOD")));
		std.put("AGE_DEATH", raw.get("AGE_DEATH"));
		std.put("SPEC_INT_CAUSE", raw.get("SPEC_INT_CAUSE"));
		std.put("UNITS_AGE", raw.get("UNITS_AGE"));
		std.put("SOUNDEX", raw.get("SOUNDEX"));
		std.put("FATHER_LNAME", parentName(text(raw, "FATHER_LNAME")));
		std.put("MOTHER_MAIDEN", parentName(text(raw, "MOTHER_MAIDEN")));
		std.put("MARITAL_STATUS", raw.get("MARITAL_STATUS"));
		std.put("PREGNANT", raw.get("PREGNANT"));
		std.put("DELIVERY_DATE_TEXT", dateText(text(raw, "DELIVERY_DT")));
		std.put("MED_RECORD_NUMBER", CommonUtil.normalizeString(text(raw, "MEDRECNO")));
		String birthPlace = text(raw, "BIRTH_PLACE");
		std.put("BIRTH_STATE", birthPlace != null && UNKNOWN_BIRTH_PLACES.contains(birthPlace) ? "" : birthPlace);
		std.put("LOCAL_REG_NO", raw.get("LOCAL_REG_NO"));
		std.put("FIPS_PLACE", raw.get("FIPS_PLACE"));
	}

	/** A parent's name as given; "" when it is "--" or "-". */
	private static String parentName(String name) {
		return CommonUtil.isNotNullAndEmpty(name) && UNKNOWN_NAMES.contains(name) ? "" : CommonUtil.removeSingleQuote(name);
	}

	/** A date of the record as MM/DD/YYYY (read with FN_TO_DATE, before 1900 not kept), or null when it is not a date. */
	private String dateText(String date) {
		if (date == null) {
			return null;
		}
		Timestamp value = schedulerJobsDAO.getDateValueFromString(date);
		Calendar day = value == null ? null : CommonUtil.resetTimeToZero(value, 1900);
		return day == null ? null : CommonUtil.convertCalenderToString(day);
	}
}
