package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.oracleText;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toDecimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toInteger;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toLong;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.upper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdStdConfirmedDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * Builds the standardized confirmed STD report (STD_STD_CONFIRMED_DOC) from a received record (a
 * RAW_STD_CONFIRMED_DOC row): which raw field each column comes from and how it is cleaned.
 * <p>
 * The cleaning the former SQL insert did in Oracle is done here: text is trimmed of spaces and upper-cased,
 * an empty text is stored as null (as Oracle does), see {@link OracleConversions}.
 */
final class StdConfDocMapping {

	/** CURRENT_PROCESS_STATUS of a standardized report. */
	private static final String STANDARDIZED_PROCESS_STATUS = "5";

	private StdConfDocMapping() {
	}

	/**
	 * The standardized report for a received record.
	 *
	 * @param raw the received record, value per raw field name; not changed
	 * @param validState the state as CommonDocumentService.validateState writes it (the code of a state name)
	 * @return the new STD_STD_CONFIRMED_DOC row; columns without a value are null
	 */
	static StdStdConfirmedDoc toStdDoc(Map<Object, Object> raw, UnaryOperator<String> validState) {
		Set<String> listedDrugs = listedDrugs(text(raw, "RISK_DRUG_USED_LIST"));

		return StdStdConfirmedDoc.builder()
				.personCensusTract(upper(raw.get("PERSON_CENSUS_TRACT")))
				.provCensusTract(upper(raw.get("PROV_CENSUS_TRACT")))
				.provFname(upper(CommonUtil.NameValidation(CommonUtil.blankOutUnknown(text(raw, "PROV_FNAME")))))
				.provLname(upper(CommonUtil.NameValidation(CommonUtil.blankOutUnknown(text(raw, "PROV_LNAME")))))
				.riskDrugOtherText(upper(raw.get("RISK_DRUG_OTHER_TEXT")))
				.riskOtherText(upper(raw.get("RISK_OTHER_TEXT")))
				.filename(upper(raw.get("FILENAME")))
				.reviewUid(upper(raw.get("REVIEW_UID")))
				.personZip(upper(CommonUtil.formatZipCode(text(raw, "PERSON_ZIP"))))
				.provZip(upper(CommonUtil.formatZipCode(text(raw, "PROV_ZIP"))))
				.fieldnote(upper(raw.get("FIELDNOTE")))
				.personCity(upper(CommonUtil.blankOutUnknown(text(raw, "PERSON_CITY"))))
				.provCity(upper(CommonUtil.blankOutUnknown(text(raw, "PROV_CITY"))))
				.currentGender(upper(LabCommonUtils.checkCurrentGenderData(text(raw, "CURRENT_GENDER"))))
				.matchCd(upper(raw.get("MATCH_CD")))
				.matchPass(upper(raw.get("MATCH_PASS")))
				.eharsStatusCd(upper(raw.get("EHARS_STATUS_CD")))
				.fname(upper(CommonUtil.NameValidation(text(raw, "FNAME"))))
				.mname(upper(CommonUtil.NameValidation(text(raw, "MNAME"))))
				.lname(upper(CommonUtil.NameValidation(text(raw, "LNAME"))))
				.personPhone(upper(raw.get("PERSON_PHONE")))
				.provPhone(upper(raw.get("PROV_PHONE")))
				.personState(upper(state(raw, "PERSON_STATE", "PERSON_STATE", validState)))
				.ethnicity(upper(LabCommonUtils.checkEthnicityData(text(raw, "ETHNICITY"))))
				.provState(upper(state(raw, "PROV_ST", "PROV_STATE", validState)))
				.riskDrugUsedCrack(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_CRACK", "1")))
				.riskDrugUsedCocaine(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_COCAINE", "2")))
				.riskDrugUsedHeroin(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_HEROIN", "3")))
				.riskDrugUsedMethamphetamines(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_METHAMPHETAMINES", "4")))
				.riskDrugUsedNitratesPoppers(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_NITRATES_POPPERS", "5")))
				.riskDrugUsedEdMedications(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_ED_MEDICATIONS", "6")))
				.riskDrugUsedOther(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_OTHER", "7")))
				.riskDrugUsedMarijuana(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_MARIJUANA", "10")))
				.riskDrugUsedPrescriptionPainkillers(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_PRESCRIPTION_PAINKILLERS", "11")))
				.riskDrugUsedAlcohol(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_ALCOHOL", "12")))
				.personCounty(upper(raw.get("PERSON_COUNTY")))
				.provCounty(upper(raw.get("PROV_COUNTY")))
				.personLongitude(upper(raw.get("PERSON_LONGITUDE")))
				.provLongitude(upper(raw.get("PROV_LONGITUDE")))
				.personAddress(upper(CommonUtil.addressValidation(CommonUtil.blankOutUnknown(text(raw, "PERSON_ADDRESS")))))
				.provAddress(upper(CommonUtil.addressValidation(CommonUtil.blankOutUnknown(CommonUtil.removeAllCharAfterLastComma(text(raw, "PROV_ADDRESS"))))))
				.personLatitude(upper(raw.get("PERSON_LATITUDE")))
				.provLatitude(upper(raw.get("PROV_LATITUDE")))
				.uploadId(upper(raw.get("UPLOAD_ID")))
				.matchedStateno(upper(raw.get("MATCHED_STATENO")))
				.birthSex(upper(LabCommonUtils.checkBirthSexData(text(raw, "BIRTH_SEX"))))
				.raceWhite(upper(LabCommonUtils.checkRaceData(text(raw, "RACE_WHITE"))))
				.raceBlack(upper(LabCommonUtils.checkRaceData(text(raw, "RACE_BLACK"))))
				.raceIndian(upper(LabCommonUtils.checkRaceData(text(raw, "RACE_AMERIND"))))
				.raceAsian(upper(LabCommonUtils.checkRaceData(text(raw, "RACE_ASIAN"))))
				.racePacific(upper(LabCommonUtils.checkRaceData(text(raw, "RACE_PACIFIC"))))
				.raceUnknown(upper(LabCommonUtils.checkRaceData(text(raw, "RACE_UNKNOWN"))))
				.raceOther(upper(LabCommonUtils.checkRaceData(text(raw, "RACE_OTHER"))))
				.riskSexMale(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_SEX_MALE"))))
				.riskSexFemale(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_SEX_FEMALE"))))
				.riskSexTransgender(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_SEX_TRANSGENDER"))))
				.riskSexIdu(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_SEX_IDU"))))
				.riskSexDrugs(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_SEX_DRUGS"))))
				.riskSexExchanged(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_SEX_EXCHANGED"))))
				.riskSexAnonymous(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_SEX_ANONYMOUS"))))
				.riskSexMsm(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_SEX_MSM"))))
				.riskSexWithoutCondom(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_SEX_WITHOUT_CONDOM"))))
				.riskDrugsUsed(upper(raw.get("RISK_DRUGS_USED")))
				.riskDrugIncarcerated(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_DRUG_INCARCERATED"))))
				.riskDrugParaphenalia(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_DRUG_PARAPHENALIA"))))
				.riskIdu(upper(LabCommonUtils.checkRiskData(text(raw, "RISK_IDU"))))
				.riskOther(CommonUtil.isNotNullAndEmpty(text(raw, "RISK_OTHER_TEXT")) ? "Y" : "N")
				.ninehundredtest(upper(raw.get("NINEHUNDREDTEST")))
				.testtechnology(upper(raw.get("TESTTECHNOLOGY")))
				.x900positive(upper(raw.get("X900POSITIVE")))
				.current900testresult(upper(raw.get("CURRENT900TESTRESULT")))
				.documentId(toInteger(raw.get("DOCUMENT_ID")))
				.casekey(toInteger(raw.get("CASEKEY")))
				.hinserialno(toLong(raw.get("HINSERIALNO")))
				.disease(toInteger(raw.get("DISEASE")))
				.currentProcessStatus(STANDARDIZED_PROCESS_STATUS)
				.matchWeight(toDecimal(raw.get("MATCH_WEIGHT")))
				.qsParentDocId(toLong(raw.get("QS_PARENT_DOC_ID")))
				.dob(date(raw.get("DOB")))
				.dateOfDeath(date(raw.get("DATE_OF_DEATH")))
				.diagDate(date(raw.get("DIAG_DATE")))
				.x900dxdate(date(raw.get("X900DXDATE")))
				.uploadDt(date(raw.get("UPLOAD_DT")))
				.eharsImportDate(date(raw.get("EHARS_IMPORT_DATE")))
				.matchedProcessDate(date(raw.get("MATCHED_PROCESS_DATE")))
				.reviewDate(date(raw.get("REVIEW_DATE")))
				.riskDrugUsedBenzodiazepines(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_BENZODIAZEPINES", "13")))
				.riskDrugUsedPrescriptionOpiods(upper(drug(raw, listedDrugs, "RISK_DRUG_USED_PRESCRIPTION_OPIODS", "14")))
				.fluxMatchCd(oracleText(raw.get("FLUX_MATCH_CD")))
				.fluxPotStateno(oracleText(raw.get("FLUX_POT_STATENO")))
				.fluxMatchedStateno(oracleText(raw.get("FLUX_MATCHED_STATENO")))
				.fluxQsParentDocId(toLong(raw.get("FLUX_QS_PARENT_DOC_ID")))
				.fluxMatchedProcessDate(date(raw.get("FLUX_MATCHED_PROCESS_DATE")))
				.build();
	}

	/**
	 * The drug codes of RISK_DRUG_USED_LIST (comma-separated, entries not trimmed): "1" crack, "2" cocaine, ...
	 * (CODED_STD_CONF_REF_DATA, DRUG_USED_LIST).
	 */
	private static Set<String> listedDrugs(String drugUsedList) {
		return CommonUtil.isNotNullAndEmpty(drugUsedList) ? new HashSet<>(Arrays.asList(drugUsedList.split(","))) : Set.of();   // a code may be listed twice
	}

	/** A drug flag: "Y" if the drug list names its code, else the flag field of the same name. */
	private static Object drug(Map<Object, Object> raw, Set<String> listedDrugs, String flag, String code) {
		return listedDrugs.contains(code) ? "Y" : raw.get(flag);
	}

	/** A state: the valid state of the state field when it is filled in, else the field of the column. */
	private static Object state(Map<Object, Object> raw, String stateField, String column, UnaryOperator<String> validState) {
		String state = text(raw, stateField);
		return CommonUtil.isNotNullAndEmpty(state) ? validState.apply(state) : raw.get(column);
	}

	/** A text field. */
	private static String text(Map<Object, Object> raw, String field) {
		return (String) raw.get(field);
	}
}
