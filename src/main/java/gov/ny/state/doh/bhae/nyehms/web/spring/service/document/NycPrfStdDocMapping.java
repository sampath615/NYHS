package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static java.util.Map.entry;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.SexOrn;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * Builds the standardized NYC Partner Referral Form (the STD_NYCPRF_DOC columns) from a received
 * record (a RAW_NYCPRF_DOC row): which raw field each column comes from and how it is cleaned.
 */
final class NycPrfStdDocMapping {

	/** Who creates the standardized rows: the import. */
	private static final String IMPORT_USER_ID = "900";

	/** Standardized column -> where its value comes from (all but the few rules further down). */
	private static final Map<String, From> COLUMNS = Map.ofEntries(
			// person
			entry("DOCUMENT_ID", copy("DOCUMENT_ID")),
			entry("PRF_SRC_ID", copy("PRF")),
			entry("CITYNO", copy("CITYNO")),
			entry("FNAME", convert("FNAME", CommonUtil::NameValidation)),
			entry("MNAME", convert("MI", CommonUtil::NameValidation)),
			entry("LNAME", convert("LNAME", CommonUtil::NameValidation)),
			entry("ALT_NAME", copy("ALIA")),
			entry("ALT_LNAME", copy("ALNAME")),
			entry("ALT_MNAME", copy("AMNAME")),
			entry("DOB", copy("DOB")),
			entry("DOD_TEXT", copy("DOD")),
			entry("SSN", convert("SSN", CommonUtil::formatSSNValue)),
			entry("DIN", convert("DIN", CommonUtil::dinValidation)),
			entry("MED_RECORD_NUMBER", convert("RECNUM", CommonUtil::normalizeString)),
			entry("PERSON_ADDRESS", convert("ADDRESS", CommonUtil::addressValidation)),
			entry("APARTMENT", text("APT")),
			entry("PERSON_CITY", copy("CITY")),
			entry("PERSON_STATE", copy("ST")),
			entry("PERSON_ZIP", copy("ZIP")),
			entry("PERSON_PHONE", copy("PHONE")),
			entry("BIRTH_COUNTRY", copy("CTRYOFBIRTH")),
			entry("PRIMARY_LANGUAGE", copy("LANG")),
			entry("PRIMARY_LANGUAGE_OTHER", copy("OLANG")),
			entry("PERSON_OTHER_IDENTIFIER", copy("OIDENTF")),
			entry("EDC", copy("EDC")),
			entry("PREGNANT", copy("PREGN")),

			// sex, gender, sexual orientation, ethnicity, race (the race flags are completed by the race list)
			entry("BIRTH_SEX", convert("SEXB", LabCommonUtils::checkBirthSexData)),
			entry("CURRENT_GENDER", convert("GENDER", LabCommonUtils::checkCurrentGenderData)),
			entry("ADDITIONAL_GENDER_IDENTITY", copy("ADTIDENTF")),
			entry("SEX_ORN_ADD_ID", text("SXORI_ADTIDF")),
			entry("ETHNICITY", convert("ETHNICITY", LabCommonUtils::checkEthnicityData)),
			entry("RACE_WHITE", convert("RACE_WHITE", LabCommonUtils::checkRaceData)),
			entry("RACE_BLACK", convert("RACE_BLACK", LabCommonUtils::checkRaceData)),
			entry("RACE_ASIAN", convert("RACE_ASIAN", LabCommonUtils::checkRaceData)),
			entry("RACE_INDIAN", convert("RACE_INDIAN", LabCommonUtils::checkRaceData)),
			entry("RACE_PACIFIC", convert("RACE_PACIFIC", LabCommonUtils::checkRaceData)),
			entry("RACE_UNKNOWN", convert("RACE_UNKNOWN", LabCommonUtils::checkRaceData)),
			entry("RACE_OTHER", convert("RACE_OTHER", LabCommonUtils::checkRaceData)),
			entry("RACE_OTHER_TEXT", copy("OTHERRACE")),

			// risk factors (RISK_OTHER follows from OTHERRISK)
			entry("SWM", convert("SEX_MALE", LabCommonUtils::checkRiskData)),
			entry("SWF", convert("SEX_FEMALE", LabCommonUtils::checkRiskData)),
			entry("IDU", convert("INJECTION", LabCommonUtils::checkRiskData)),
			entry("SEX_PARTNER_IDU", convert("DRUG", LabCommonUtils::checkRiskData)),
			entry("SEX_PARTNER_HIV", convert("HIVPSON", LabCommonUtils::checkRiskData)),
			entry("SEX_PARTNER_MSM", convert("BXMAN", LabCommonUtils::checkRiskData)),
			entry("SEX_PARTNER_HEMOPHILIAC", convert("S_HEMO", LabCommonUtils::checkRiskData)),
			entry("RECV_BLOOD_PROD", convert("RECBLD", LabCommonUtils::checkRiskData)),
			entry("RECV_BLOOD_PROD_DATE_TEXT", copy("RECBLDDT")),
			entry("RECV_BLOOD_PROD_COUNTRY", copy("RECBLDCTRY")),
			entry("PERINATAL", convert("PERIEXPO", LabCommonUtils::checkRiskData)),
			entry("HEALTHCARE_WORKER", convert("HCW", LabCommonUtils::checkRiskData)),
			entry("RISK_UNKNOWN", convert("RSKUNK", LabCommonUtils::checkRiskData)),

			// HIV and AIDS diagnosis
			entry("INITIAL_HIV_DIAG", convert("FSTHIVDX", LabCommonUtils::checkRaceData)),
			entry("INITIAL_HIV_DIAG_DATE_TEXT", copy("FSTHIVDT")),
			entry("INFORMED_HIV_POS", convert("INFPOSSTUS", LabCommonUtils::checkRaceData)),
			entry("INFORMED_HIV_DATE_TEXT", copy("INFPOSDT")),
			entry("PREVIOUS_HIV_DIAG", convert("PREHIVDX", LabCommonUtils::checkRaceData)),
			entry("PREV_HIV_DIAG_DATE_TEXT", copy("PREHIVDT")),
			entry("INITIAL_AIDS_DIAG", convert("FSTASDX", LabCommonUtils::checkRaceData)),
			entry("INIT_AIDS_DIAG_DATE_TEXT", copy("FSTASDXDT")),
			entry("PREVIOUS_AIDS_DIAG", convert("PREASDX", LabCommonUtils::checkRaceData)),
			entry("PREV_AIDS_DIAG_DATE_TEXT", copy("PREASDT")),
			entry("ACUTE_INFECTION", copy("ACUINF")),
			entry("PS_REQUESTED", convert("CNAP", LabCommonUtils::checkRaceData)),

			// HIV testing history (newer forms have a second name for some fields: first one filled wins)
			entry("PT_INTERVIEW_DATE", copy("INTIVDT")),
			entry("FIRST_POS_HIV_DATE", copy("FSTPOSHIVDT")),
			entry("FIRST_POS_ANON", convert("FSTPOSANO", LabCommonUtils::checkTestDataValues)),
			entry("FIRST_POS_LOCATION", copy("FLITYTYPE_D")),
			entry("FIRST_POS_FAC_NAME", copy("FLITYNAME_D")),
			entry("FIRST_POS_FAC_STATE", copy("FLITYST_D")),
			entry("FIRST_HIV_TEST_EVER_DATE_TEXT", copy("FSTHIVTSTDT")),
			entry("EVER_HAD_PREV_POS_TEST", copy("EVEP")),
			entry("EVER_TEST_NEG", convert("EVRNGTST", "TSTNGBEF", LabCommonUtils::checkTestDataValues)),
			entry("LAST_NEG_DATE_TEXT", copy("LASTNGTSTDT", "LSTNGTSTDT")),
			entry("LAST_NEG_FAC_TYPE", copy("FLITYTYPE_J")),
			entry("LAST_NEG_FAC_NAME", copy("FCLITYNAME_J")),
			entry("LAST_NEG_FAC_STATE", copy("FCLITYST_J")),
			entry("NUM_NEG_HIV_TEST", text("NUMNGTST")),
			entry("RSON_FIRST_POS_EXP", convert("EXPHIV", "EXPSIX", LabCommonUtils::checkTestDataValues)),
			entry("RSON_FIRST_POS_ROUTN", convert("ROUTN", "TSTAGAIN", LabCommonUtils::checkTestDataValues)),
			entry("RSON_FIRST_POS_CHKNEG", convert("CHKNEG", "CHECKING", LabCommonUtils::checkTestDataValues)),
			entry("RSON_FIRST_POS_REQD", convert("REQD", "REQIRED", LabCommonUtils::checkTestDataValues)),
			entry("RSON_FIRST_POS_OTHER", convert("ORSON", "OTHERRSON", LabCommonUtils::checkTestDataValues)),
			entry("RSON_FIRST_POS_OTHER_TEXT", copy("DESCR", "SPOTHERRSON")),
			entry("RSON_FIRST_POS_PRTNR", copy("RECOMD", "PRTHIVPOS")),

			// antiretrovirals (the medication lists follow further down)
			entry("ARV_6_MO", convert("ANTISIX", LabCommonUtils::checkTestDataValues)),
			entry("ARV_6_MO_NAME", copy("WHICH")),
			entry("ARV_6_MO_START_DATE_TEXT", copy("STARTDT")),
			entry("ARV_6_MO_END_DATE_TEXT", copy("ENDDT")),
			entry("ARV_CURRENT_TEST", convert("ANTICUR", LabCommonUtils::checkTestDataValues)),
			entry("PT_ARV_EVER_TAKEN", convert("ARVEVRTKEN", LabCommonUtils::checkTestDataValues)),
			entry("ARV_TREATMENT", copy("TREATM")),
			entry("PT_ARV_START_DATE_TEXT", copy("TREATM_SDT")),
			entry("PT_ARV_END_DATE_TEXT", copy("TREATM_EDT")),
			entry("POST_EXP_ARV_PEP", copy("POSPEP")),
			entry("POST_EXP_ARV_PEP_START_DT", copy("POSPEP_SDT")),
			entry("POST_EXP_ARV_PEP_END_DT", copy("POSPEP_EDT")),
			entry("ARV_PREVEN_PREP", copy("ARVPREV")),
			entry("ARV_PREVEN_PREP_START_DT", copy("ARVPREV_SDT")),
			entry("ARV_PREVEN_PREP_END_DT", copy("ARVPREV_EDT")),

			// provider, facility, referral
			entry("PROV_FACILITY", copy("PROFALITY")),
			entry("PROV_LNAME", copy("PROLAST")),
			entry("PROV_FNAME", copy("PROFIST")),
			entry("PROV_MI", copy("PROMI")),
			entry("PROV_TITLE", copy("PROTITLE")),
			entry("PROV_DEPARTMENT", copy("PRODEPT")),
			entry("PROV_ADDRESS", copy("PROADD")),
			entry("PROV_CITY", copy("PROCITY")),
			entry("PROV_STATE", copy("PROST")),
			entry("PROV_ZIP", copy("PROZIP")),
			entry("PROV_PHONE", copy("PROTEL")),
			entry("PROV_EXT", copy("PROEXT")),
			entry("PRO_EMAIL", copy("PROEMAIL")),
			entry("PROVIDER_NPI_NO", copy("NPINO")),
			entry("PROVIDER_NYS_LIC_NO", copy("LICNO")),
			entry("FACILITY_TYPE", copy("FACITYPE")),
			entry("FACILITY_TYPE_OTHER_TEXT", copy("OFACI")),
			entry("REFERRED_HIV_PROVIDER", copy("REFPV")),
			entry("REFERRED_PROVIDER_NAME", copy("REFPVNAME")),
			entry("REFFERED_PROVIDER_TELEPHONE", copy("REFPVTEL")),
			entry("MDPRINTNAME", copy("MDPRINTNAME")),
			entry("SIG_DATE", copy("MDSIGN")),
			entry("COMPLETED_DATE", copy("PROCMPDT")),
			entry("CONTACT_LNAME", copy("CONTLAST")),
			entry("CONTACT_FNAME", copy("CONTFIRST")),
			entry("CONTACT_MNAME", copy("CONTMI")),
			entry("CONTACT_TITLE", copy("CONTTITLE")),
			entry("CONTACT_PHONE", copy("CONTTEL")),

			// form handling and eHARS
			entry("ADMISSION_DATE", copy("VISITDT")),
			entry("FIELD_PROC_DATE", text("FIELDPROCDATE")),
			entry("ENTER_DATE", copy("ENTER_DT")),
			entry("UPDATE_DATE", copy("UPDATE_DT")),
			entry("PRINTED_DATE", copy("PRINTED")),
			entry("RECEIVED_DATE", copy("RECEIVED")),
			entry("TSAID", copy("TSAID")),
			entry("REVTSAID", copy("REVTSAID")),
			entry("OLDFORM", copy("OLDFORM")),
			entry("NOTSEND", copy("NOTSEND")),
			entry("WARNING", copy("WARNING")),
			entry("EHARS", copy("EHARS")),
			entry("EHARS_DATE", copy("EHARSDT")),
			entry("EHARS_DOCUMENT_SOURCE_CD", copy("SOURE_CD")));

	private NycPrfStdDocMapping() {
	}

	/**
	 * The standardized columns for a received record.
	 *
	 * @param raw the received record, value per raw field name
	 * @return value per STD_NYCPRF_DOC column; a column without a value is left out
	 */
	static Map<Object, Object> toStdDocData(Map<Object, Object> raw) {
		Map<Object, Object> std = new HashMap<>(256);
		COLUMNS.forEach((column, from) -> std.put(column, from.valueIn(raw)));

		markListedRaces(std, (String) raw.get("RACE"));
		putOtherRisk(std, (String) raw.get("OTHERRISK"));
		putMedicationNames(std, "PT_ARV_NAME", (String) raw.get("TREATMMED"));
		putMedicationNames(std, "POST_EXP_ARV_PEP_MED_NAMES", (String) raw.get("POSMED"));
		putMedicationNames(std, "ARV_PREVENPREP_MED_NAMES", (String) raw.get("ARVMED"));
		std.put("NUMBER_TEST_2_YRS", numberOfTestsIn2Years(raw));

		SexOrn sexualOrientation = SexOrn.fromString((String) raw.get("SXORI"));
		if (sexualOrientation != null) {
			std.put("SEX_ORN", sexualOrientation.name());
		}

		std.put("CREATE_UID", IMPORT_USER_ID);
		std.put("CREATE_DATE", new Date(System.currentTimeMillis()));
		std.put("CURRENT_PROCESS_STATUS", CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
		return std;
	}

	/**
	 * Sets the race flag of every race in the comma-separated race list ("WHITE,BLACK", ...) on top
	 * of the separate race fields; RACE_CD is set to "" when there is no list.
	 */
	private static void markListedRaces(Map<Object, Object> std, String races) {
		if (CommonUtil.isNullOrEmpty(races)) {
			std.put("RACE_CD", "");
			return;
		}
		for (String race : races.trim().toUpperCase().split(",")) {
			String flag = raceFlag(race);
			if (flag != null) {
				std.put(flag, "1");
			}
		}
	}

	/** The race flag column of one entry of the race list, or null if it is none of them. */
	private static String raceFlag(String race) {
		if ("WHITE".equals(race)) {
			return "RACE_WHITE";
		} else if ("BLACK".equals(race)) {
			return "RACE_BLACK";
		} else if ("ASIAN".equals(race)) {
			return "RACE_ASIAN";
		} else if (race.contains("INDIAN")) {
			return "RACE_INDIAN";
		} else if ("OTHER".equals(race)) {
			return "RACE_OTHER";
		} else if (race.contains("HAWAIIAN")) {   // Hawaiian, Pacific Islander
			return "RACE_PACIFIC";
		} else if ("UNKNOWN".equals(race)) {
			return "RACE_UNKNOWN";
		}
		return null;
	}

	/** RISK_OTHER is "Y" with the described risk as RISK_OTHER_TEXT, or "N" if none is described. */
	private static void putOtherRisk(Map<Object, Object> std, String otherRisk) {
		if (CommonUtil.isNotNullAndEmpty(otherRisk)) {
			std.put("RISK_OTHER", "Y");
			std.put("RISK_OTHER_TEXT", otherRisk);
		} else {
			std.put("RISK_OTHER", "N");
		}
	}

	/** A comma-separated medication list, stored separated by "|" (left out when empty). */
	private static void putMedicationNames(Map<Object, Object> std, String column, String medications) {
		if (CommonUtil.isNotNullAndEmpty(medications)) {
			std.put(column, medications.replace(',', '|'));
		}
	}

	/** NUMTSTING without "_" and without one leading zero ("05" is "5"), or else NGTST. */
	private static Object numberOfTestsIn2Years(Map<Object, Object> raw) {
		String tests = (String) raw.get("NUMTSTING");
		if (CommonUtil.isNullOrEmpty(tests)) {
			return raw.get("NGTST");
		}
		tests = tests.replace("_", "");
		boolean leadingZero = CommonUtil.isNumeric(tests) && tests.startsWith("0") && tests.length() > 1;
		return leadingZero ? tests.substring(1) : tests;
	}

	private static From copy(String field) {
		return new From(field, null, null);
	}

	/** The first of the two fields that is filled, as it is. */
	private static From copy(String field, String otherwise) {
		return new From(field, otherwise, null);
	}

	/** A text field, as it is. */
	private static From text(String field) {
		return new From(field, null, UnaryOperator.identity());
	}

	private static From convert(String field, UnaryOperator<String> conversion) {
		return new From(field, null, conversion);
	}

	/** The first of the two fields that is filled, converted. */
	private static From convert(String field, String otherwise, UnaryOperator<String> conversion) {
		return new From(field, otherwise, conversion);
	}

	/**
	 * Where a column's value comes from: a raw field, or the {@code otherwise} field when the first
	 * one is empty; text fields are passed through {@code conversion} (none: the value as it is).
	 */
	private record From(String field, String otherwise, UnaryOperator<String> conversion) {

		Object valueIn(Map<Object, Object> raw) {
			Object value = raw.get(field);
			if (otherwise != null && !CommonUtil.isNotNullAndEmpty((String) value)) {
				value = raw.get(otherwise);
			}
			return conversion == null ? value : conversion.apply((String) value);
		}
	}
}
