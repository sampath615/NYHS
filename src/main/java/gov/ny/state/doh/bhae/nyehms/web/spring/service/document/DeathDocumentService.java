package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DeathRecordRules.text;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms.SchedulerJobsDAO;
import gov.ny.state.doh.bhae.nyehms.utils.County;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathFacility;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedCountyRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedDeathPfiRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedInstituteRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.DeathDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DeathRecordRules.ZipCode;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;
import lombok.RequiredArgsConstructor;

/**
 * The NYS death records (STD_DEATH_DOC): the death document screen and the import that standardizes a received
 * record (RAW_DEATH_DOC). Records that died in New York City (county 70) take their facility from
 * {@link NycDeathDocumentService}.
 */
@Service
@RequiredArgsConstructor
public class DeathDocumentService {

	private static final Logger log = LoggerFactory.getLogger(DeathDocumentService.class);

	/** Who creates the standardized rows: the import. */
	private static final String IMPORT_USER_ID = "900";
	private static final String YES = "Y";
	private static final String ONE = "1";
	private static final String ZERO = "0";
	private static final String NYC_COUNTY = "70";
	private static final String OUT_OF_STATE_COUNTY = "80";
	private static final Set<String> NYC_COUNTIES = Set.of("70", "71", "72");
	private static final String NYC_OR_OUT_OF_STATE_HOSPITAL = "188";
	private static final Set<String> UNKNOWN_STATES = Set.of("--", "ZZ", "XX");
	private static final Set<String> UNKNOWN_COUNTIES = Set.of("000", "999");
	private static final Set<String> UNKNOWN_BIRTH_PLACES = Set.of("ZZ", "XX");
	private static final Set<String> UNKNOWN_NAMES = Set.of("--", "-", "Unknown");
	private static final String[] HISPANIC_ORIGINS = {"HISP_ORIGIN_NONE", "HISP_ORIGIN_MEXICAN", "HISP_ORIGIN_PUERTO", "HISP_ORIGIN_CUBAN",
			"HISP_ORIGIN_OTHER"};
	private static final String[] ASIAN_RACES = {"RACE_A", "RACE_A_OTH", "RACE_C", "RACE_F", "RACE_J", "RACE_K", "RACE_V"};
	private static final String[] PACIFIC_RACES = {"RACE_H", "RACE_P_OTH", "RACE_S", "RACE_G"};

	private final CodedCountyRepository codedCountyRepository;
	private final CodedDeathPfiRepository codedDeathPfiRepository;
	private final CodedInstituteRepository codedInstituteRepository;
	private final DeathDocRepository deathDocRepository;
	private final SchedulerJobsDAO schedulerJobsDAO;
	private final DocumentDAO documentDAO;
	private final NycDeathDocumentService nycDeathDocumentService;
	private final CommonDocumentService commonDocumentService;
	private final CommonExceptionHandler commonExceptionHandler;

	/**
	 * The death record of the document screen, with its cause of death.
	 *
	 * @return the record, or null when there is none; when a read fails {@code result} is marked failed and the
	 *         record is returned as far as it was read (without its cause of death, or null)
	 */
	public DeathDocData getDeathDoc(int docId, BindingResult result, UserObject userObject, String sessionID) {
		log.debug("fetching death document {}", docId);
		DeathDocData doc = null;
		try {
			doc = deathDocRepository.findDocument(docId);
			if (doc == null) {
				return null;
			}
			String causeOfDeath = DeathRecordRules.causeOfDeath(doc.stdDeathDoc().getHcause(), doc.stdDeathDoc().getOiCause(),
					() -> meetsCaseDefinition(docId));
			if (causeOfDeath != null) {
				doc = doc.withCauseOfDeath(causeOfDeath);
			}
		} catch (Exception e) {
			log.error("Failed to read death document {}: {}", docId, e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
			commonExceptionHandler.populateBindObjTrsnFail(result);
		}
		return doc;
	}

	private boolean meetsCaseDefinition(int docId) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("docId", docId);
		parameters.put("TABLE_NAME", "STD_DEATH_DOC");
		return YES.equals(documentDAO.checkStatenoMeetsCDC(parameters));
	}

	/**
	 * Writes the standardized record (STD_DEATH_DOC) of a received record.
	 *
	 * @param docVO the received record (a RAW_DEATH_DOC row), value per column name; not changed
	 * @param zipCodeReferenceMap city, state and county per zip code
	 * @return 1
	 */
	public int writeRawDeathDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId,
			Map<String, Map<String, String>> zipCodeReferenceMap) {
		Map<Object, Object> std = new HashMap<>();
		std.put("DOCUMENT_ID", docVO.get("DOCUMENT_ID"));
		putPerson(docVO, std);
		putResidence(docVO, zipCodeReferenceMap, std);
		std.put("CREATE_UID", IMPORT_USER_ID);
		std.put("CURRENT_PROCESS_STATUS", CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
		std.put("CREATE_DATE", new Date(Calendar.getInstance().getTimeInMillis()));
		std.put("DEATH_FILE", docVO.get("DEATH_FILE"));
		putEthnicityAndRace(docVO, std);
		std.put("DEATH_CERT", docVO.get("CERT"));
		std.put("DEATH_CERT_LICENSE", docVO.get("DEATH_CERT_LICENSE"));
		if (YES.equals(text(docVO, "NCHS_FORMAT"))) {
			putNchsDeathPlace(docVO, std);
		} else {
			putDeathPlace(docVO, std);
		}
		putDeath(docVO, std);
		DeathRecordRules.putCauses(docVO, std);

		deathDocRepository.saveAndFlush(DeathDocMapping.toStdDoc(std));
		return 1;
	}

	/** Name, date of birth and SSN. */
	private void putPerson(Map<Object, Object> raw, Map<Object, Object> std) {
		std.put("FNAME", CommonUtil.NameValidation(text(raw, "FIRST")));
		std.put("LNAME", CommonUtil.NameValidation(text(raw, "LAST")));
		std.put("MNAME", CommonUtil.NameValidation(text(raw, "MIDDLE")));
		std.put("DOB_TEXT", dateText(text(raw, "DOB")));
		std.put("SSN", CommonUtil.formatSSNValue(text(raw, "SSN")));
	}

	/**
	 * Address, zip and plus-4; city, state and county from the zip code reference when the zip is known there, else
	 * the FIPS state and county without their unknown codes.
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
			String state = text(raw, "FIPS_ST");
			std.put("PERSON_STATE", state != null && UNKNOWN_STATES.contains(state) ? "" : state);
			String county = text(raw, "FIPS_CNTY");
			std.put("PERSON_COUNTY", county != null && UNKNOWN_COUNTIES.contains(county) ? "" : county);
		}
	}

	/** Ethnicity from the Hispanic origin flags, gender, and the race flags. */
	private void putEthnicityAndRace(Map<Object, Object> raw, Map<Object, Object> std) {
		std.put("ETHNICITY", ethnicity(raw));
		std.put("CURRENT_GENDER", LabCommonUtils.checkCurrentGenderData(text(raw, "SEX")));
		std.put("RACE_INDIAN", LabCommonUtils.checkRaceData(text(raw, "RACE_I")));
		std.put("RACE_BLACK", LabCommonUtils.checkRaceData(text(raw, "RACE_B")));
		std.put("RACE_WHITE", LabCommonUtils.checkRaceData(text(raw, "RACE_W")));
		std.put("RACE_ASIAN", anyIsOne(raw, ASIAN_RACES) ? 1 : 0);
		std.put("RACE_PACIFIC", anyIsOne(raw, PACIFIC_RACES) ? 1 : 0);
		std.put("RACE_OTHER", LabCommonUtils.checkRaceData(text(raw, "RACE_OTH")));
	}

	/** "01" Hispanic (a flag is 1), "02" not Hispanic (every flag 0 or empty), else "09" unknown. */
	private static String ethnicity(Map<Object, Object> raw) {
		if (anyIsOne(raw, HISPANIC_ORIGINS)) {
			return "01";
		}
		for (String origin : HISPANIC_ORIGINS) {
			if (raw.get(origin) != null && !ZERO.equals(text(raw, origin))) {
				return "09";
			}
		}
		return "02";
	}

	private static boolean anyIsOne(Map<Object, Object> raw, String... fields) {
		for (String field : fields) {
			if (ONE.equals(text(raw, field))) {
				return true;
			}
		}
		return false;
	}

	/** County and facility of death of an NCHS-format record, as given. */
	private void putNchsDeathPlace(Map<Object, Object> raw, Map<Object, Object> std) {
		std.put("DEATH_COUNTY", raw.get("DEATH_CNTY"));
		std.put("FACILITY_CITY", raw.get("DEATH_CITY_TEXT"));
		std.put("FACILITY_NAME", raw.get("DEATH_FACILITY_NAME"));
		std.put("FACILITY_ADDRESS", raw.get("DEATH_FACILITY_ADDRESS"));
		String facilityState = text(raw, "DEATH_FACILITY_STATE");
		std.put("FACILITY_STATE", "New York".equals(facilityState) ? "NY" : commonDocumentService.validateState(facilityState));
		ZipCode facilityZip = ZipCode.of(text(raw, "DEATH_FACILITY_ZIP"));
		std.put("FACILITY_ZIP", facilityZip.zip());
		std.put("FACILITY_PLUS4", facilityZip.plus4());
		std.put("FACILITY_TYPE", raw.get("DEATH_FACILITY_TYPE"));
	}

	/**
	 * County of death (FIPS code; NYC from the city of death, 998 out of state, 999 unknown) and the facility: from
	 * the NYC institution list for NYC, else from the PFI or the institution code.
	 */
	private void putDeathPlace(Map<Object, Object> raw, Map<Object, Object> std) {
		String deathCounty = text(raw, "DEATH_CNTY");
		std.put("DEATH_COUNTY", deathCountyFips(deathCounty, text(raw, "DEATH_CITY")));

		String pfi = DeathRecordRules.pfi(raw);
		std.put("PFI", pfi);
		String instCode = text(raw, "INST_CODE");
		if (NYC_COUNTY.equals(deathCounty)) {
			std.put("NYS_COUNTY", deathCounty);
			nycDeathDocumentService.setNYCFacilityData(pfi, instCode, std);
		} else {
			putFacility(instCode, pfi, std);
		}
	}

	private String deathCountyFips(String deathCounty, String deathCity) {
		if (CommonUtil.isNotNullAndEmpty(deathCounty) && !NYC_COUNTY.equals(deathCounty)) {
			return codedCountyRepository.findFipsCodeOfCounty(deathCounty);
		}
		if (NYC_COUNTY.equals(deathCounty) && CommonUtil.isNotNullAndEmpty(deathCity)) {
			return County.getFipsFromGaz(deathCity);
		}
		return OUT_OF_STATE_COUNTY.equals(deathCounty) ? "998" : "999";
	}

	/**
	 * The institution (INST_CODE, INST: its 3-digit institution, NYS_COUNTY: the FIPS code of its 2-digit county;
	 * a 6-character code starting with U without the U) and the facility.
	 */
	private void putFacility(String instCode, String pfi, Map<Object, Object> std) {
		std.put("INST_CODE", instCode);
		String nysCounty = null;
		String nysCountyFips = null;
		if (CommonUtil.isNotNullAndEmpty(instCode)) {
			instCode = instCode.trim();
			if (instCode.startsWith("U") && instCode.length() == 6) {
				instCode = instCode.substring(1);
			}
			if (instCode.length() >= 5) {
				nysCounty = instCode.substring(0, 2);
				std.put("INST", instCode.substring(2, 5));
				nysCountyFips = County.getFipsFromGaz(nysCounty);
			}
		}
		std.put("NYS_COUNTY", nysCountyFips);
		putFacilityInfo(std, pfi, instCode, nysCounty, nysCountyFips);
	}

	/** The facility of the PFI; else, from the county and institution code, a facility name. */
	private void putFacilityInfo(Map<Object, Object> std, String pfi, String instCode, String nysCounty, String nysCountyFips) {
		if (CommonUtil.isNotNullAndEmpty(pfi) && !DeathRecordRules.UNKNOWN_PFI.equals(pfi)) {
			DeathFacility facility = codedDeathPfiRepository.findFacilityByPfi(pfi.trim());
			if (facility != null && !facility.isEmpty()) {
				std.put("FACILITY_NAME", facility.facilityName());
				std.put("FACILITY_TYPE", facility.facilityType());
				std.put("FACILITY_PHONE", facility.facilityPhone());
				std.put("FACILITY_ADDRESS", facility.facilityAddress());
				std.put("FACILITY_CITY", facility.facilityCity());
				std.put("FACILITY_STATE", facility.facilityState());
				std.put("FACILITY_ZIP", facility.facilityZip());
				std.put("FACILITY_PLUS4", facility.facilityPlus4());
				std.put("FACILITY_COUNTY", facility.facilityCounty());
				return;
			}
		}
		if (CommonUtil.isNullOrEmpty(nysCounty)) {
			return;
		}
		if (OUT_OF_STATE_COUNTY.equals(nysCounty)) {
			std.put("FACILITY_NAME", "OUT OF STATE");
		} else if (NYC_OR_OUT_OF_STATE_HOSPITAL.equals(instCode)) {
			std.put("FACILITY_NAME", NYC_COUNTIES.contains(nysCounty) ? "NYC HOSPITAL (NYC RECORDS)" : "OUT OF STATE HOSPITAL (FOR UPSTATE RECORDS)");
		} else if (CommonUtil.isNotNullAndEmpty(instCode)) {
			std.put("FACILITY_NAME", codedInstituteRepository.findDescriptionOfInstitute(instCode));
			std.put("FACILITY_COUNTY", nysCountyFips);
		}
	}

	/** Date of death, age, parents, pregnancy, birth state and the other copied fields. */
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
		String birthPlace = text(raw, "BIRTH_STATE");
		std.put("BIRTH_STATE", birthPlace != null && UNKNOWN_BIRTH_PLACES.contains(birthPlace) ? "" : birthPlace);
		std.put("LOCAL_REG_NO", raw.get("LOCAL_REG_NO"));
		std.put("FIPS_PLACE", raw.get("FIPS_PLACE"));
		std.put("TRANS_FAC_CD", raw.get("TRANS_FAC_CD"));
		std.put("DEATH_UNIQUE_ID", raw.get("DEATH_UNIQUE_ID"));
		std.put("DEATH_ID", raw.get("DEATH_ID"));
	}

	/** A parent's name, cleaned; "" when it is "--", "-" or "Unknown". */
	private static String parentName(String name) {
		String cleaned = CommonUtil.NameValidation(name);
		return CommonUtil.isNotNullAndEmpty(cleaned) && UNKNOWN_NAMES.contains(cleaned) ? "" : CommonUtil.removeSingleQuote(cleaned);
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
