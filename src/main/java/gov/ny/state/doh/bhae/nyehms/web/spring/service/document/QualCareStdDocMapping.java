package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.dateText;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.oracleText;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toDecimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toInteger;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.upper;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdQualityCareDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * Builds the standardized quality of care record (STD_QUALITY_CARE_DOC) from a received record (a
 * RAW_QUALITY_CARE_DOC row): which raw field each column comes from and how it is cleaned.
 * <p>
 * The cleaning the former SQL insert did in Oracle is done here: text is trimmed of spaces and upper-cased, an empty
 * text is stored as null (as Oracle does), DOB_TEXT is the received DOB as MM/DD/YYYY, see {@link OracleConversions}.
 */
final class QualCareStdDocMapping {

	/** CURRENT_PROCESS_STATUS of a standardized record. */
	private static final int STANDARDIZED_PROCESS_STATUS = 5;

	/** A race flag that is set. */
	private static final String LISTED = "1";

	private QualCareStdDocMapping() {
	}

	/**
	 * The standardized record for a received record.
	 *
	 * @param raw the received record, value per raw field name; not changed
	 * @return the new STD_QUALITY_CARE_DOC row; columns without a value are null
	 */
	static StdQualityCareDoc toStdDoc(Map<Object, Object> raw) {
		Set<String> races = races(raw);

		return StdQualityCareDoc.builder()
				.linkageDays(upper(raw.get("LINKAGE_DAYS")))
				.suppressionDays(upper(raw.get("SUPPRESSION_DAYS")))
				.suppressionDaysDischarge(upper(raw.get("SUPPRESSION_DAYS_DISCHARGE")))
				.suppressionDaysHivCare(upper(raw.get("SUPPRESSION_DAYS_HIV_CARE")))
				.linkage30days(upper(raw.get("LINKAGE_30DAYS")))
				.linkage3days(upper(raw.get("LINKAGE_3DAYS")))
				.linkage7days(upper(raw.get("LINKAGE_7DAYS")))
				.linkage90days(upper(raw.get("LINKAGE_90DAYS")))
				.onArvAllPrevDxActive(upper(raw.get("ON_ARV_ALL_PREV_DX_ACTIVE")))
				.onArvEstActive(upper(raw.get("ON_ARV_EST_ACTIVE")))
				.onArvNewlyDx(upper(raw.get("ON_ARV_NEWLY_DX")))
				.onArvOpenPts(upper(raw.get("ON_ARV_OPEN_PTS")))
				.onArvOtherNewToCare(upper(raw.get("ON_ARV_OTHER_NEW_TO_CARE")))
				.everSuppressedNewlyDx(upper(raw.get("EVER_SUPPRESSED_NEWLY_DX")))
				.suppression91daysFromCare(upper(raw.get("SUPPRESSION_91DAYS_FROM_CARE")))
				.suppression91daysFromDis(upper(raw.get("SUPPRESSION_91DAYS_FROM_DIS")))
				.vlSuppressionAllPrevDxAct(upper(raw.get("VL_SUPPRESSION_ALL_PREV_DX_ACT")))
				.vlSuppressionEstActive(upper(raw.get("VL_SUPPRESSION_EST_ACTIVE")))
				.vlSuppressionNewlyDx(upper(raw.get("VL_SUPPRESSION_NEWLY_DX")))
				.vlSuppressionOpenPts(upper(raw.get("VL_SUPPRESSION_OPEN_PTS")))
				.vlSuppressionOtherNewToCare(upper(raw.get("VL_SUPPRESSION_OTHER_NEW_TO_CARE")))
				.vlTestingAllPrevDxActive(upper(raw.get("VL_TESTING_ALL_PREV_DX_ACTIVE")))
				.vlTestingEstActive(upper(raw.get("VL_TESTING_EST_ACTIVE")))
				.vlTestingNewlyDx(upper(raw.get("VL_TESTING_NEWLY_DX")))
				.vlTestingOpenPts(upper(raw.get("VL_TESTING_OPEN_PTS")))
				.vlTestingOtherNewToCare(upper(raw.get("VL_TESTING_OTHER_NEW_TO_CARE")))
				.facilityCensusTract(upper(raw.get("FACILITY_CENSUS_TRACT")))
				.medRecordNumber(upper(CommonUtil.normalizeString(text(raw, "MRN"))))
				.asianSubGroup(upper(raw.get("ASIAN_SUB_GROUP")))
				.facilityZip(upper(text(raw, "ZIP")))
				.patientStatus(upper(raw.get("PATIENT_STATUS")))
				.inpatient(upper(raw.get("INPATIENT")))
				.linked(upper(raw.get("LINKED")))
				.hivCare(upper(raw.get("HIV_CARE")))
				.arv(upper(raw.get("ARV")))
				.onArv(upper(raw.get("ON_ARV")))
				.suppressedEverReviewYear(upper(raw.get("SUPPRESSED_EVER_REVIEW_YEAR")))
				.suppressedFinalReviewYear(upper(raw.get("SUPPRESSED_FINAL_REVIEW_YEAR")))
				.suppressedFinalVl(upper(raw.get("SUPPRESSED_FINAL_VL")))
				.vlReviewed(upper(raw.get("VL_REVIEWED")))
				.vlSuppressed(upper(raw.get("VL_SUPPRESSED")))
				.vlTestReviewYear(upper(raw.get("VL_TEST_REVIEW_YEAR")))
				.vlTested(upper(raw.get("VL_TESTED")))
				.facilityCounty(upper(raw.get("FACILITY_COUNTY")))
				.fluxMatchCd(upper(raw.get("FLUX_MATCH_CD")))
				.matchCd(upper(raw.get("MATCH_CD")))
				.hispanicSubGroup(upper(raw.get("HISPANIC_SUB_GROUP")))
				.serviceLine(upper(serviceLine(raw)))
				.otherService(upper(text(raw, "OTHER_SERVICE")))
				.diagnosis(upper(raw.get("DIAGNOSIS")))
				.ethnicity(upper(LabCommonUtils.checkEthnicityData(text(raw, "ETHNICITY"))))
				.housingStatus(upper(text(raw, "HOUSING")))
				.facilityState(upper(raw.get("FACILITY_STATE")))
				.facilityCity(upper(text(raw, "CITY")))
				.qualCareFile(oracleText(raw.get("QUAL_CARE_FILE")))
				.facilityLongitude(upper(raw.get("FACILITY_LONGITUDE")))
				.fname(upper(CommonUtil.NameValidation(text(raw, "FIRST_NAME"))))
				.lname(upper(CommonUtil.NameValidation(text(raw, "LAST_NAME"))))
				.facilityAddress(upper(text(raw, "STREET")))
				.organizationName(upper(text(raw, "ORG_NAME")))
				.facilityName(upper(text(raw, "CLINIC_NAME")))
				.fluxPotStateno(upper(raw.get("FLUX_POT_STATENO")))
				.matchedStateno(upper(raw.get("MATCHED_STATENO")))
				.dobText(dateText(raw.get("DOB")))
				.insurance(upper(raw.get("INSURANCE")))
				.enrollment(upper(raw.get("ENROLLMENT")))
				.facilityLatitude(upper(raw.get("FACILITY_LATITUDE")))
				.fluxMatchedStateno(upper(raw.get("FLUX_MATCHED_STATENO")))
				.createUid(upper(raw.get("CREATE_UID")))
				.mname(upper(CommonUtil.NameValidation(text(raw, "MIDDLE_INITIAL"))))
				.birthSex(upper(LabCommonUtils.checkBirthSexData(text(raw, "BIRTH_SEX"))))
				.gender(upper(LabCommonUtils.checkCurrentGenderData(text(raw, "GENDER"))))
				.raceWhite(upper(race(raw, races, "W", "RACE_WHITE")))
				.raceUnknown(upper(race(raw, races, "UK", "RACE_UNKNOWN")))
				.racePacific(upper(race(raw, races, "NHPI", "RACE_PACIFIC")))
				.raceOther(upper(raw.get("RACE_OTHER")))
				.raceIndian(upper(race(raw, races, "AIAN", "RACE_INDIAN")))
				.raceBlack(upper(race(raw, races, "B", "RACE_BLACK")))
				.raceAsian(upper(race(raw, races, "A", "RACE_ASIAN")))
				.recvBloodProd(upper(LabCommonUtils.checkRiskData(text(raw, "BLOOD_RISK"))))
				.recvClottingFactor(upper(LabCommonUtils.checkRiskData(text(raw, "HEMO_RISK"))))
				.sexPartnerHiv(upper(LabCommonUtils.checkRiskData(text(raw, "HETERO_RISK"))))
				.idu(upper(LabCommonUtils.checkRiskData(text(raw, "IDU_RISK"))))
				.riskMsm(upper(LabCommonUtils.checkRiskData(text(raw, "MSM_RISK"))))
				.perinatal(upper(LabCommonUtils.checkRiskData(text(raw, "PERI_RISK"))))
				.riskOther(upper(LabCommonUtils.checkRiskData(text(raw, "OTHER_RISK"))))
				.riskUnknown(upper(LabCommonUtils.checkRiskData(text(raw, "UNKNOWN_RISK"))))
				.matchWeight(toDecimal(raw.get("MATCH_WEIGHT")))
				.documentId(toInteger(raw.get("DOCUMENT_ID")))
				.recordId(toInteger(raw.get("ID")))
				.age(toInteger(raw.get("AGE")))
				.currentProcessStatus(STANDARDIZED_PROCESS_STATUS)
				.matchPass(toInteger(raw.get("MATCH_PASS")))
				.diagnosisDate(date(raw.get("DIAGNOSIS_DATE")))
				.dischargeDate(date(raw.get("DISCHARGE_DATE")))
				.hivCareDateValue(date(raw.get("HIV_CARE_DATE")))
				.firstSuppressedDateNewlyDxValue(date(raw.get("FIRST_SUPPRESSED_DATE_NEWLY_DX")))
				.firstVlDateNewlyDxValue(date(raw.get("FIRST_VL_DATE_NEWLY_DX")))
				.fluxMatchedProcessDate(date(raw.get("FLUX_MATCHED_PROCESS_DATE")))
				.createDate(date(raw.get("CREATE_DATE")))
				.build();
	}

	/** The race codes of the record: RACE split at commas, each trimmed and upper-cased; empty when RACE is empty. */
	private static Set<String> races(Map<Object, Object> raw) {
		String race = text(raw, "RACE");
		if (!CommonUtil.isNotNullAndEmpty(race)) {
			return Set.of();
		}
		return Arrays.stream(race.split(",")).map(code -> code.trim().toUpperCase()).collect(Collectors.toSet());
	}

	/** A race flag: "1" if the record lists the race's code, else the flag's own field as received. */
	private static Object race(Map<Object, Object> raw, Set<String> races, String code, String flagField) {
		return races.contains(code) ? LISTED : raw.get(flagField);
	}

	/** SERVICE_LINE without any whitespace when it is filled in, else as received. */
	private static String serviceLine(Map<Object, Object> raw) {
		String serviceLine = text(raw, "SERVICE_LINE");
		return CommonUtil.isNotNullAndEmpty(serviceLine) ? serviceLine.replaceAll("\\s+", "") : serviceLine;
	}

	/** A text field. */
	private static String text(Map<Object, Object> raw, String field) {
		return (String) raw.get(field);
	}
}
