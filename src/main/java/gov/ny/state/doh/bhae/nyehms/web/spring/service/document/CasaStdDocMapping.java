package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.oracleText;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.textOrZero;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toDecimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toInteger;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toLong;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.upper;

import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdCasaDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * Builds the standardized CASA record (STD_CASA_DOC) from a received record (a RAW_CASA_DOC row): which raw
 * field each column comes from and how it is cleaned.
 * <p>
 * The cleaning the former SQL insert did in Oracle is done here: text is trimmed of spaces and upper-cased,
 * an empty text is stored as null (as Oracle does), an empty count is 0 (NVL(..., 0)), see {@link OracleConversions}.
 */
final class CasaStdDocMapping {

	/** CURRENT_PROCESS_STATUS of a standardized record. */
	private static final int STANDARDIZED_PROCESS_STATUS = 5;

	/** A race flag that is not set. */
	private static final String NOT_LISTED = "0";

	private CasaStdDocMapping() {
	}

	/**
	 * The standardized record for a received record.
	 *
	 * @param raw the received record, value per raw field name; not changed
	 * @return the new STD_CASA_DOC row; columns without a value are null
	 */
	static StdCasaDoc toStdDoc(Map<Object, Object> raw) {
		String raceCode = raceCode(raw);

		return StdCasaDoc.builder()
				.ssn(upper(CommonUtil.formatSSNValue(text(raw, "SSN"))))
				.narrow(upper(raw.get("NARROW")))
				.personZip(upper(CommonUtil.zipValidation(text(raw, "ZIP"))))
				.matchPass(upper(raw.get("MATCH_PASS")))
				.fname(upper(CommonUtil.NameValidation(text(raw, "FNAME"))))
				.lname(upper(CommonUtil.NameValidation(text(raw, "LNAME"))))
				.fincrg(upper(raw.get("FINCRG")))
				.personCounty(upper(text(raw, "COUNTY_FIPS")))
				.matchCd(upper(raw.get("MATCH_CD")))
				.rate(upper(raw.get("RATE")))
				.rate1(upper(raw.get("RATE1")))
				.casaFile(oracleText(raw.get("CASA_FILE")))
				.ethnicity(upper(ethnicity(raw, raceCode)))
				.medicaidNumber(upper(CommonUtil.checkMedicaidNumberFormat(text(raw, "MEDICAID_NUMBER"))))
				.matchedStateno(upper(raw.get("MATCHED_STATENO")))
				.dobText(upper(text(raw, "DOB")))
				.eligDate(upper(raw.get("ELIG_DATE")))
				.reviewUid(upper(raw.get("REVIEW_UID")))
				.gender(upper(LabCommonUtils.checkCurrentGenderData(text(raw, "GENDER"))))
				.mname(upper(CommonUtil.NameValidation(text(raw, "MNAME"))))
				.raceIndian(upper(flag(raceCode, "4")))
				.raceBlack(upper(flag(raceCode, "2")))
				.raceAsian(upper(flag(raceCode, "3")))
				.racePacific(upper(NOT_LISTED))
				.raceWhite(upper(flag(raceCode, "1")))
				.raceMultiple(upper(NOT_LISTED))
				.raceOther(upper(flag(raceCode, "9")))
				.raceUnknown(upper(flag(raceCode, "5", "0")))
				.eharsStatusCd(upper(raw.get("EHARS_STATUS_CD")))
				.documentId(toInteger(raw.get("DOCUMENT_ID")))
				.inpat(textOrZero(raw.get("INPAT")))
				.claimcount(oracleText(raw.get("CLAIMCOUNT")))
				.outpat(textOrZero(raw.get("OUTPAT")))
				.diagallclaims(textOrZero(raw.get("DIAGALLCLAIMS")))
				.hivhospice(textOrZero(raw.get("HIVHOSPICE")))
				.hivsnp(textOrZero(raw.get("HIVSNP")))
				.arv(textOrZero(raw.get("ARV")))
				.outpAdioi(textOrZero(raw.get("OUTP_ADIOI")))
				.outpservDiag(textOrZero(raw.get("OUTPSERV_DIAG")))
				.specPoss(textOrZero(raw.get("SPEC_POSS")))
				.specAdi(textOrZero(raw.get("SPEC_ADI")))
				.arvPoss(textOrZero(raw.get("ARV_POSS")))
				.arvAdi(textOrZero(raw.get("ARV_ADI")))
				.arvOutpat(textOrZero(raw.get("ARV_OUTPAT")))
				.procedures(textOrZero(raw.get("PROCEDURES")))
				.outpatSelect(textOrZero(raw.get("OUTPAT_SELECT")))
				.matchWeight(toDecimal(raw.get("MATCH_WEIGHT")))
				.currentProcessStatus(STANDARDIZED_PROCESS_STATUS)
				.qsParentDocId(toLong(raw.get("QS_PARENT_DOC_ID")))
				.reviewDt(date(raw.get("REVIEW_DT")))
				.eharsImportDate(date(raw.get("EHARS_IMPORT_DATE")))
				.createDate(date(raw.get("CREATE_DATE")))
				.matchedProcessDate(date(raw.get("MATCHED_PROCESS_DATE")))
				.fluxMatchCd(oracleText(raw.get("FLUX_MATCH_CD")))
				.fluxPotStateno(oracleText(raw.get("FLUX_POT_STATENO")))
				.fluxMatchedStateno(oracleText(raw.get("FLUX_MATCHED_STATENO")))
				.fluxQsParentDocId(toLong(raw.get("FLUX_QS_PARENT_DOC_ID")))
				.fluxMatchedProcessDate(date(raw.get("FLUX_MATCHED_PROCESS_DATE")))
				.build();
	}

	/** The race code of the record (RACE, trimmed), or null when empty. */
	private static String raceCode(Map<Object, Object> raw) {
		String race = text(raw, "RACE");
		return CommonUtil.isNotNullAndEmpty(race) ? race.trim() : null;
	}

	/** A race flag: "1" if the race code is one of the codes of the flag, else "0". */
	private static String flag(String raceCode, String... codes) {
		for (String code : codes) {
			if (code.equals(raceCode)) {
				return "1";
			}
		}
		return NOT_LISTED;
	}

	/** The ethnicity that goes with the race code, or the ETHNICITY field when the race code is none of them. */
	private static Object ethnicity(Map<Object, Object> raw, String raceCode) {
		if (raceCode == null) {
			return raw.get("ETHNICITY");
		}
		return switch (raceCode) {
			case "1", "2" -> "02";
			case "5" -> "01";
			case "0", "3", "4", "9" -> "09";
			default -> raw.get("ETHNICITY");
		};
	}

	/** A text field. */
	private static String text(Map<Object, Object> raw, String field) {
		return (String) raw.get(field);
	}
}
