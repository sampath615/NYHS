package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.fnToDate;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.trimmed;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.upper;
import static java.util.Map.entry;

import java.sql.Timestamp;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;

/**
 * Builds a standardized partner (STD_NYCPRF_PARTNER) from a partner as received on an NYC Partner
 * Referral Form (RAW_NYCPRF_PARTNER): race and gender codes, upper-cased and trimmed text, form dates
 * through FN_TO_DATE (the rules of the former SQL insert ... select, see {@link OracleConversions}).
 */
final class NycPrfStdPartnerMapping {

	/** Who creates the standardized rows: the import. */
	private static final String IMPORT_USER_ID = "900";

	/** RACE_CD of a received race. */
	private static final Map<String, String> RACE_CODES = Map.of(
			"WHITE", "01", "BLACK", "02", "ASIAN", "04", "3", "06", "OTHER", "06", "UNKNOWN", "09", "5", "05");

	/** ETHNICITY of a received ethnicity (sent in the gender field). */
	private static final Map<String, String> ETHNICITY_CODES = Map.of(
			"01", "01", "02", "02", "09", "09", "Y", "01", "N", "02", "UN", "09", "UNK", "09", "U", "09");

	/** CURRENT_GENDER of a received gender. */
	private static final Map<String, String> GENDER_CODES = Map.ofEntries(
			entry("M", "M"), entry("F", "F"), entry("T", "T"), entry("U", "U"), entry("R", "R"), entry("X", "X"),
			entry("Y", "Y"), entry("H", "H"), entry("1", "M"), entry("2", "F"), entry("MTF", "F"), entry("FTM", "M"));

	/** PARTNER_STATUS of a received HIV status. */
	private static final Map<String, String> HIV_STATUS_CODES = Map.of("UNKNOWN", "U", "NEGATIVE", "N", "POSITIVE", "P");

	private NycPrfStdPartnerMapping() {
	}

	/**
	 * The standardized partner for a received partner.
	 *
	 * @param raw the partner as received
	 * @return the new STD_NYCPRF_PARTNER row
	 */
	static StdNycprfPartner toStdPartner(RawNycprfPartner raw) {
		String race = upper(raw.getPrtrace());
		String gender = upper(raw.getPrtcurgender());

		return StdNycprfPartner.builder()
				.patientId(trimmed(raw.getPatientId()))
				.nycprfDocumentId(trimmed(raw.getNycprfDocumentId()))
				.partnrid(trimmed(raw.getPartnerid()))

				// person
				.lastName(upper(raw.getPrtlname()))
				.firstName(upper(raw.getPrtfname()))
				.middleName(upper(raw.getPrtmi()))
				.altName(upper(raw.getPrtalias()))
				.dobText(upper(raw.getPrtdob()))
				.age(trimmed(raw.getPrtage()))
				.birthSex(upper(raw.getPrtsexbirth()))
				.currentGender(code(GENDER_CODES, gender))
				.ethnicity("3".equals(race) ? "01" : code(ETHNICITY_CODES, gender))   // a race of 3 sets ethnicity 01
				.raceWhite(flag("WHITE".equals(race)))
				.raceBlack(flag("BLACK".equals(race)))
				.raceAsian(flag("ASIAN".equals(race)))
				.raceOther(flag("3".equals(race) || "OTHER".equals(race)))
				.raceUnknown(flag("UNKNOWN".equals(race)))
				.racePacific(flag(race != null && race.contains("HAWAIIAN")))
				.raceIndian(flag("5".equals(race)))
				.raceCd(raceCode(race))
				.complexion(upper(raw.getPrtcomplex()))
				.hair(upper(raw.getPrthair()))
				.height(upper(raw.getPrtheiwei()))
				.maritalCode(upper(raw.getPrtmaritalstus()))
				.pregnant(upper(raw.getPrtpregnant()))
				.spouse(upper(raw.getPrfSpouse()))

				// contact
				.address(upper(raw.getAddress()))
				.city(upper(raw.getPrtcity()))
				.state(upper(raw.getPrtstate()))
				.zip(upper(raw.getPrtzip()))
				.email(upper(raw.getPrtemail()))
				.homePhone(upper(raw.getPrthphone()))
				.cellPhone(upper(raw.getPrtcphone()))
				.workPhone(upper(raw.getWrktel()))

				// partner services
				.dvScreen(upper(raw.getPrtdviolencedone()))
				.riskViolence(upper(raw.getPrtrskofdviolence()))
				.notifiedDate(upper(raw.getPrtdtnofitied()))
				.partnerAssistanceStatus(upper(raw.getPrtassisstatus()))
				.partnerStatus(code(HIV_STATUS_CODES, upper(raw.getPrfHivstus())))
				.partnerTestDateText(upper(raw.getPrfTstdt()))
				.elicitedByPnap(upper(raw.getPrfElicited()))
				.informedDate(fnToDate(raw.getPridtinf()))
				.updatedDate(fnToDate(raw.getUpdated()))
				.enterDate(upper(raw.getPrtenterdt()))
				.eharsDiagDateText(upper(raw.getPrtdtharsdx()))
				.cityno(upper(raw.getCityno()))
				.indexCityno(upper(raw.getIndexcityno()))
				.priority(upper(raw.getPriority()))
				.highPriority(upper(raw.getHpriority()))
				.source(raw.getSource())
				.partners(raw.getP())
				.referral(upper(raw.getReferral()))
				.referralType(upper(raw.getTyperef()))
				.disease1(upper(raw.getDisease1()))
				.disease2(upper(raw.getDisease2()))
				.code(upper(raw.getCode()))
				.interview(upper(raw.getInterv()))
				.interviewType(upper(raw.getTypeinterv()))
				.printed(upper(raw.getPrinted()))
				.notsend(upper(raw.getNotsend()))
				.dispo(upper(raw.getPrthvdispo()))
				.hivdispoDateText(upper(raw.getPrthvdispodt()))

				// written by the import
				.createUid(IMPORT_USER_ID)
				.createDate(new Timestamp(System.currentTimeMillis()))
				.build();
	}

	/** The code of a received value, or null (also for no value: Map.of does not accept null keys). */
	private static String code(Map<String, String> codes, String received) {
		return received == null ? null : codes.get(received);
	}

	/** A race flag column: "1" or "0". */
	private static String flag(boolean set) {
		return set ? "1" : "0";
	}

	/** RACE_CD: the code of the received race (Hawaiian / Pacific Islander as Asian, "04"), else null. */
	private static String raceCode(String race) {
		if (race == null) {
			return null;
		}
		return RACE_CODES.getOrDefault(race, race.contains("HAWAIIAN") ? "04" : null);
	}
}
