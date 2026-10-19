package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.oracleText;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toInteger;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toLong;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.trimmed;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.upper;

import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdSyphilisDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * Builds the standardized syphilis report (STD_SYPHILIS_DOC) from a received record (a RAW_SYPHILIS_DOC row):
 * which raw field each column comes from and how it is cleaned.
 * <p>
 * The cleaning the former SQL insert did in Oracle is done here: text is trimmed of spaces (and upper-cased
 * where the insert did), an empty text is stored as null (as Oracle does), see {@link OracleConversions}.
 */
final class SyphStdDocMapping {

	/** CURRENT_PROCESS_STATUS of a standardized report. */
	private static final int STANDARDIZED_PROCESS_STATUS = 5;

	/** A race flag that is not set. */
	private static final String NOT_LISTED = "0";

	private SyphStdDocMapping() {
	}

	/**
	 * The standardized report for a received record.
	 *
	 * @param raw the received record, value per raw field name; not changed
	 * @return the new STD_SYPHILIS_DOC row; columns without a value are null
	 */
	static StdSyphilisDoc toStdDoc(Map<Object, Object> raw) {
		String raceCode = raceCode(raw);

		return StdSyphilisDoc.builder()
				.documentId(toInteger(raw.get("DOCUMENT_ID")))
				.casekey(oracleText(raw.get("CASEKEY")))
				.fname(upper(CommonUtil.NameValidation(text(raw, "FIRSTNAME"))))
				.mname(upper(CommonUtil.NameValidation(text(raw, "MIDDLENAME"))))
				.lname(upper(CommonUtil.NameValidation(text(raw, "LASTNAME"))))
				.dob(date(raw.get("DOB_DATE")))
				.dod(date(raw.get("DEATH_DATE")))
				.birthSex(upper(LabCommonUtils.checkBirthSexData(text(raw, "SEX"))))
				.ethnicity(oracleText(LabCommonUtils.checkEthnicityData(text(raw, "ETHNICITY_CD"))))
				.raceAsian(oracleText(flag(raceCode, "4")))
				.raceBlack(oracleText(flag(raceCode, "2")))
				.raceIndian(oracleText(flag(raceCode, "3")))
				.racePacific(oracleText(NOT_LISTED))
				.raceWhite(oracleText(flag(raceCode, "1")))
				.raceOther(oracleText(flag(raceCode, "5")))
				.raceUnknown(oracleText(flag(raceCode, "9")))
				.personAddress(upper(CommonUtil.addressValidation(text(raw, "STREET"))))
				.personZip(trimmed(CommonUtil.zipValidation(text(raw, "ZIPCODE"))))
				.personPhone(trimmed(personPhone(raw)))
				.provFname(upper(knownName(text(raw, "PROVIDERFNAME"))))
				.provLname(upper(knownName(text(raw, "PROVIDERLNAME"))))
				.provAddress(upper(CommonUtil.addressValidation(text(raw, "PROVIDERADDRESS"))))
				.provCity(upper(raw.get("PROVIDERCITY")))
				.provState(upper(raw.get("PROVIDERSTATE")))
				.provZip(trimmed(providerZip(raw)))
				.provPhone(trimmed(raw.get("PROVIDERPHONE")))
				.diseaseCode(trimmed(raw.get("DISEASE_CD")))
				.syphilisDiagnosisDate(date(raw.get("DIAGNOSIS_DATE")))
				.personCity(upper(raw.get("LOCALITY")))
				.receivedDate(date(raw.get("RECEIVED_DATE")))
				.currentProcessStatus(STANDARDIZED_PROCESS_STATUS)
				.matchedProcessDate(date(raw.get("MATCHED_PROCESS_DATE")))
				.eharsStatusCd(upper(raw.get("EHARS_STATUS_CD")))
				.eharsImportDate(date(raw.get("EHARS_IMPORT_DATE")))
				.personState(upper(raw.get("PERSON_STATE")))
				.personCounty(trimmed(raw.get("PERSON_COUNTY")))
				.provCounty(trimmed(raw.get("PROV_COUNTY")))
				.matchedStateno(upper(raw.get("MATCHED_STATENO")))
				.qsParentDocId(toLong(raw.get("QS_PARENT_DOC_ID")))
				.reviewUid(upper(raw.get("REVIEW_UID")))
				.reviewDate(date(raw.get("REVIEW_DATE")))
				.fluxMatchCd(oracleText(raw.get("FLUX_MATCH_CD")))
				.fluxPotStateno(oracleText(raw.get("FLUX_POT_STATENO")))
				.fluxMatchedStateno(oracleText(raw.get("FLUX_MATCHED_STATENO")))
				.fluxQsParentDocId(toLong(raw.get("FLUX_QS_PARENT_DOC_ID")))
				.fluxMatchedProcessDate(date(raw.get("FLUX_MATCHED_PROCESS_DATE")))
				.build();
	}

	/** The race code of the report (RACE_CD, trimmed), or null when empty. */
	private static String raceCode(Map<Object, Object> raw) {
		String race = text(raw, "RACE_CD");
		return CommonUtil.isNotNullAndEmpty(race) ? race.trim() : null;
	}

	/** A race flag: "1" if the race code is the code of the flag, else "0". */
	private static String flag(String raceCode, String code) {
		return code.equals(raceCode) ? "1" : NOT_LISTED;
	}

	/**
	 * The person's phone: PHONE as digits when it has 10 of them, or AREA_CD and PHONE when PHONE has 7; else the
	 * PERSON_PHONE field.
	 */
	private static Object personPhone(Map<Object, Object> raw) {
		String phone = text(raw, "PHONE");
		if (phone != null) {
			String digits = phone.replaceAll("[^\\d]", "");
			if (digits.length() == 7) {
				String areaCode = text(raw, "AREA_CD");
				if (CommonUtil.isNotNullAndEmpty(areaCode)) {
					return areaCode.replaceAll("[^\\d]", "") + digits;
				}
			} else if (digits.length() == 10) {
				return digits;
			}
		}
		return raw.get("PERSON_PHONE");
	}

	/** A provider name, cleaned (CommonUtil.NameValidation); null when empty or "UNKNOWN". */
	private static String knownName(String name) {
		String cleaned = CommonUtil.NameValidation(name);
		return CommonUtil.isNotNullAndEmpty(cleaned) && !"UNKNOWN".equals(cleaned.trim().toUpperCase()) ? cleaned : null;
	}

	/** The provider zip: PROVIDERZIPCODE as digits when it is filled in, else the PROV_ZIP field. */
	private static Object providerZip(Map<Object, Object> raw) {
		String zip = text(raw, "PROVIDERZIPCODE");
		return CommonUtil.isNotNullAndEmpty(zip) ? zip.replaceAll("[^\\d]", "") : raw.get("PROV_ZIP");
	}

	/** A text field. */
	private static String text(Map<Object, Object> raw, String field) {
		return (String) raw.get(field);
	}
}
