package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.fnToDate;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.trimmed;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.upper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.SexOrn;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * Builds the standardized NYC Partner Referral Form (STD_NYCPRF_DOC) from a received record (a
 * RAW_NYCPRF_DOC row): which raw field each column comes from and how it is cleaned.
 * <p>
 * The cleaning the former SQL insert did in Oracle is done here: text is trimmed of spaces and
 * upper-cased, an empty text is stored as null (as Oracle does), dates on the form go through
 * FN_TO_DATE (see {@link OracleConversions}).
 */
final class NycPrfStdDocMapping {

	/** Who creates the standardized rows: the import. */
	private static final String IMPORT_USER_ID = "900";

	private NycPrfStdDocMapping() {
	}

	/**
	 * The standardized form for a received record.
	 *
	 * @param raw the received record, value per raw field name
	 * @return the new STD_NYCPRF_DOC row; columns without a value are null
	 */
	static StdNycprfDoc toStdDoc(Map<Object, Object> raw) {
		Set<String> listedRaces = listedRaces(text(raw, "RACE"));
		String otherRisk = text(raw, "OTHERRISK");
		SexOrn sexualOrientation = SexOrn.fromString(text(raw, "SXORI"));

		// RACE_CD stays null: the former insert wrote '' or nothing, which Oracle stores as NULL
		return StdNycprfDoc.builder()
				// person
				.documentId(toInteger(raw.get("DOCUMENT_ID")))
				.prfSrcId(toLong(raw.get("PRF")))
				.cityno(upper(raw.get("CITYNO")))
				.fname(upper(CommonUtil.NameValidation(text(raw, "FNAME"))))
				.mname(upper(CommonUtil.NameValidation(text(raw, "MI"))))
				.lname(upper(CommonUtil.NameValidation(text(raw, "LNAME"))))
				.altName(upper(raw.get("ALIA")))
				.altLname(upper(raw.get("ALNAME")))
				.altMname(upper(raw.get("AMNAME")))
				.dob(date(raw.get("DOB")))
				.dodText(upper(raw.get("DOD")))
				.ssn(upper(CommonUtil.formatSSNValue(text(raw, "SSN"))))
				.din(upper(CommonUtil.dinValidation(text(raw, "DIN"))))
				.medRecordNumber(upper(CommonUtil.normalizeString(text(raw, "RECNUM"))))
				.personAddress(upper(CommonUtil.addressValidation(text(raw, "ADDRESS"))))
				.apartment(upper(text(raw, "APT")))
				.personCity(upper(raw.get("CITY")))
				.personState(upper(raw.get("ST")))
				.personZip(upper(raw.get("ZIP")))
				.personPhone(upper(raw.get("PHONE")))
				.birthCountry(upper(raw.get("CTRYOFBIRTH")))
				.primaryLanguage(upper(raw.get("LANG")))
				.primaryLanguageOther(upper(raw.get("OLANG")))
				.personOtherIdentifier(upper(raw.get("OIDENTF")))
				.edc(upper(raw.get("EDC")))
				.pregnant(upper(raw.get("PREGN")))

				// sex, gender, sexual orientation, ethnicity, race (the race flags are completed by the race list)
				.birthSex(upper(LabCommonUtils.checkBirthSexData(text(raw, "SEXB"))))
				.currentGender(upper(LabCommonUtils.checkCurrentGenderData(text(raw, "GENDER"))))
				.additionalGenderIdentity(upper(raw.get("ADTIDENTF")))
				.sexOrnAddId(upper(text(raw, "SXORI_ADTIDF")))
				.ethnicity(upper(LabCommonUtils.checkEthnicityData(text(raw, "ETHNICITY"))))
				.raceWhite(upper(race(raw, listedRaces, "RACE_WHITE")))
				.raceBlack(upper(race(raw, listedRaces, "RACE_BLACK")))
				.raceAsian(upper(race(raw, listedRaces, "RACE_ASIAN")))
				.raceIndian(upper(race(raw, listedRaces, "RACE_INDIAN")))
				.racePacific(upper(race(raw, listedRaces, "RACE_PACIFIC")))
				.raceUnknown(upper(race(raw, listedRaces, "RACE_UNKNOWN")))
				.raceOther(upper(race(raw, listedRaces, "RACE_OTHER")))
				.raceOtherText(upper(raw.get("OTHERRACE")))

				// risk factors (RISK_OTHER follows from OTHERRISK)
				.swm(upper(LabCommonUtils.checkRiskData(text(raw, "SEX_MALE"))))
				.swf(upper(LabCommonUtils.checkRiskData(text(raw, "SEX_FEMALE"))))
				.idu(upper(LabCommonUtils.checkRiskData(text(raw, "INJECTION"))))
				.sexPartnerIdu(upper(LabCommonUtils.checkRiskData(text(raw, "DRUG"))))
				.sexPartnerHiv(upper(LabCommonUtils.checkRiskData(text(raw, "HIVPSON"))))
				.sexPartnerMsm(upper(LabCommonUtils.checkRiskData(text(raw, "BXMAN"))))
				.sexPartnerHemophiliac(upper(LabCommonUtils.checkRiskData(text(raw, "S_HEMO"))))
				.recvBloodProd(upper(LabCommonUtils.checkRiskData(text(raw, "RECBLD"))))
				.recvBloodProdDateText(upper(raw.get("RECBLDDT")))
				.recvBloodProdCountry(upper(raw.get("RECBLDCTRY")))
				.perinatal(upper(LabCommonUtils.checkRiskData(text(raw, "PERIEXPO"))))
				.healthcareWorker(upper(LabCommonUtils.checkRiskData(text(raw, "HCW"))))
				.riskUnknown(upper(LabCommonUtils.checkRiskData(text(raw, "RSKUNK"))))

				// HIV and AIDS diagnosis
				.initialHivDiag(trimmed(LabCommonUtils.checkRaceData(text(raw, "FSTHIVDX"))))
				.initialHivDiagDateText(upper(raw.get("FSTHIVDT")))
				.informedHivPos(trimmed(LabCommonUtils.checkRaceData(text(raw, "INFPOSSTUS"))))
				.informedHivDateText(upper(raw.get("INFPOSDT")))
				.previousHivDiag(trimmed(LabCommonUtils.checkRaceData(text(raw, "PREHIVDX"))))
				.prevHivDiagDateText(upper(raw.get("PREHIVDT")))
				.initialAidsDiag(trimmed(LabCommonUtils.checkRaceData(text(raw, "FSTASDX"))))
				.initAidsDiagDateText(upper(raw.get("FSTASDXDT")))
				.previousAidsDiag(trimmed(LabCommonUtils.checkRaceData(text(raw, "PREASDX"))))
				.prevAidsDiagDateText(upper(raw.get("PREASDT")))
				.acuteInfection(upper(raw.get("ACUINF")))
				.psRequested(upper(LabCommonUtils.checkRaceData(text(raw, "CNAP"))))

				// HIV testing history (newer forms have a second name for some fields: first one filled wins)
				.ptInterviewDate(upper(raw.get("INTIVDT")))
				.firstPosHivDate(upper(raw.get("FSTPOSHIVDT")))
				.firstPosAnon(upper(LabCommonUtils.checkTestDataValues(text(raw, "FSTPOSANO"))))
				.firstPosLocation(upper(raw.get("FLITYTYPE_D")))
				.firstPosFacName(upper(raw.get("FLITYNAME_D")))
				.firstPosFacState(upper(raw.get("FLITYST_D")))
				.firstHivTestEverDateText(upper(raw.get("FSTHIVTSTDT")))
				.everHadPrevPosTest(upper(raw.get("EVEP")))
				.everTestNeg(upper(LabCommonUtils.checkTestDataValues((String) firstFilled(raw, "EVRNGTST", "TSTNGBEF"))))
				.lastNegDateText(upper(firstFilled(raw, "LASTNGTSTDT", "LSTNGTSTDT")))
				.lastNegFacType(upper(raw.get("FLITYTYPE_J")))
				.lastNegFacName(upper(raw.get("FCLITYNAME_J")))
				.lastNegFacState(upper(raw.get("FCLITYST_J")))
				.numNegHivTest(upper(text(raw, "NUMNGTST")))
				.rsonFirstPosExp(upper(LabCommonUtils.checkTestDataValues((String) firstFilled(raw, "EXPHIV", "EXPSIX"))))
				.rsonFirstPosRoutn(upper(LabCommonUtils.checkTestDataValues((String) firstFilled(raw, "ROUTN", "TSTAGAIN"))))
				.rsonFirstPosChkneg(upper(LabCommonUtils.checkTestDataValues((String) firstFilled(raw, "CHKNEG", "CHECKING"))))
				.rsonFirstPosReqd(upper(LabCommonUtils.checkTestDataValues((String) firstFilled(raw, "REQD", "REQIRED"))))
				.rsonFirstPosOther(upper(LabCommonUtils.checkTestDataValues((String) firstFilled(raw, "ORSON", "OTHERRSON"))))
				.rsonFirstPosOtherText(upper(firstFilled(raw, "DESCR", "SPOTHERRSON")))
				.rsonFirstPosPrtnr(upper(firstFilled(raw, "RECOMD", "PRTHIVPOS")))

				// antiretrovirals (the medication lists follow further down)
				.arv6Mo(upper(LabCommonUtils.checkTestDataValues(text(raw, "ANTISIX"))))
				.arv6MoName(upper(raw.get("WHICH")))
				.arv6MoStartDateText(upper(raw.get("STARTDT")))
				.arv6MoEndDateText(upper(raw.get("ENDDT")))
				.arvCurrentTest(upper(LabCommonUtils.checkTestDataValues(text(raw, "ANTICUR"))))
				.ptArvEverTaken(upper(LabCommonUtils.checkTestDataValues(text(raw, "ARVEVRTKEN"))))
				.arvTreatment(upper(raw.get("TREATM")))
				.ptArvStartDateText(upper(raw.get("TREATM_SDT")))
				.ptArvEndDateText(upper(raw.get("TREATM_EDT")))
				.postExpArvPep(upper(raw.get("POSPEP")))
				.postExpArvPepStartDt(upper(raw.get("POSPEP_SDT")))
				.postExpArvPepEndDt(upper(raw.get("POSPEP_EDT")))
				.arvPrevenPrep(upper(raw.get("ARVPREV")))
				.arvPrevenPrepStartDt(upper(raw.get("ARVPREV_SDT")))
				.arvPrevenPrepEndDt(upper(raw.get("ARVPREV_EDT")))

				// provider, facility, referral
				.provFacility(upper(raw.get("PROFALITY")))
				.provLname(upper(raw.get("PROLAST")))
				.provFname(upper(raw.get("PROFIST")))
				.provMi(upper(raw.get("PROMI")))
				.provTitle(upper(raw.get("PROTITLE")))
				.provDepartment(upper(raw.get("PRODEPT")))
				.provAddress(upper(raw.get("PROADD")))
				.provCity(upper(raw.get("PROCITY")))
				.provState(upper(raw.get("PROST")))
				.provZip(upper(raw.get("PROZIP")))
				.provPhone(upper(raw.get("PROTEL")))
				.provExt(upper(raw.get("PROEXT")))
				.proEmail(upper(raw.get("PROEMAIL")))
				.providerNpiNo(upper(raw.get("NPINO")))
				.providerNysLicNo(upper(raw.get("LICNO")))
				.facilityType(upper(raw.get("FACITYPE")))
				.facilityTypeOtherText(upper(raw.get("OFACI")))
				.referredHivProvider(upper(raw.get("REFPV")))
				.referredProviderName(upper(raw.get("REFPVNAME")))
				.refferedProviderTelephone(upper(raw.get("REFPVTEL")))
				.mdprintname(upper(raw.get("MDPRINTNAME")))
				.sigDate(fnToDate(raw.get("MDSIGN")))
				.completedDate(date(raw.get("PROCMPDT")))
				.contactLname(upper(raw.get("CONTLAST")))
				.contactFname(upper(raw.get("CONTFIRST")))
				.contactMname(upper(raw.get("CONTMI")))
				.contactTitle(upper(raw.get("CONTTITLE")))
				.contactPhone(upper(raw.get("CONTTEL")))

				// form handling and eHARS
				.admissionDate(fnToDate(raw.get("VISITDT")))
				.fieldProcDate(date(upper(text(raw, "FIELDPROCDATE"))))
				.enterDate(date(raw.get("ENTER_DT")))
				.updateDate(date(raw.get("UPDATE_DT")))
				.printedDateValue(date(raw.get("PRINTED")))
				.receivedDate(date(raw.get("RECEIVED")))
				.tsaid(upper(raw.get("TSAID")))
				.revtsaid(upper(raw.get("REVTSAID")))
				.oldform(upper(raw.get("OLDFORM")))
				.notsend(upper(raw.get("NOTSEND")))
				.warning(upper(raw.get("WARNING")))
				.ehars(upper(raw.get("EHARS")))
				.eharsDate(fnToDate(raw.get("EHARSDT")))
				.eharsDocumentSourceCd(upper(raw.get("SOURE_CD")))

				// rules
				.riskOther(upper(CommonUtil.isNotNullAndEmpty(otherRisk) ? "Y" : "N"))
				.riskOtherText(upper(CommonUtil.isNotNullAndEmpty(otherRisk) ? otherRisk : null))
				.ptArvName(upper(medicationNames(text(raw, "TREATMMED"))))
				.postExpArvPepMedNames(upper(medicationNames(text(raw, "POSMED"))))
				.arvPrevenprepMedNames(upper(medicationNames(text(raw, "ARVMED"))))
				.numberTest2Yrs(upper(numberOfTestsIn2Years(raw)))
				.sexOrn(upper(sexualOrientation == null ? null : sexualOrientation.name()))

				// written by the import
				.createUid(IMPORT_USER_ID)
				.createDate(new Timestamp(System.currentTimeMillis()))
				.currentProcessStatus(trimmed(CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS))
				.build();
	}

	/**
	 * The race flag columns ("RACE_WHITE", ...) named by the comma-separated race list
	 * ("WHITE,BLACK", ...); entries are not trimmed.
	 */
	private static Set<String> listedRaces(String races) {
		Set<String> flags = new HashSet<>();
		if (CommonUtil.isNotNullAndEmpty(races)) {
			for (String race : races.trim().toUpperCase().split(",")) {
				String flag = raceFlag(race);
				if (flag != null) {
					flags.add(flag);
				}
			}
		}
		return flags;
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

	/** A race flag: "1" if the race list names it, else the flag field of the same name. */
	private static String race(Map<Object, Object> raw, Set<String> listedRaces, String flag) {
		String fromField = LabCommonUtils.checkRaceData(text(raw, flag));
		return listedRaces.contains(flag) ? "1" : fromField;
	}

	/** A comma-separated medication list, separated by "|" instead; null when empty. */
	private static String medicationNames(String medications) {
		return CommonUtil.isNotNullAndEmpty(medications) ? medications.replace(',', '|') : null;
	}

	/** NUMTSTING without "_" and without one leading zero ("05" is "5"), or else NGTST. */
	private static Object numberOfTestsIn2Years(Map<Object, Object> raw) {
		String tests = text(raw, "NUMTSTING");
		if (CommonUtil.isNullOrEmpty(tests)) {
			return raw.get("NGTST");
		}
		tests = tests.replace("_", "");
		boolean leadingZero = CommonUtil.isNumeric(tests) && tests.startsWith("0") && tests.length() > 1;
		return leadingZero ? tests.substring(1) : tests;
	}

	/** A text field. */
	private static String text(Map<Object, Object> raw, String field) {
		return (String) raw.get(field);
	}

	/** The first field, or the second one when the first is empty (newer and older form versions). */
	private static Object firstFilled(Map<Object, Object> raw, String field, String otherwise) {
		Object value = raw.get(field);
		return CommonUtil.isNotNullAndEmpty((String) value) ? value : raw.get(otherwise);
	}

	private static Integer toInteger(Object value) {
		String text = trimmed(value);
		return text == null ? null : new BigDecimal(text).intValueExact();
	}

	private static Long toLong(Object value) {
		String text = trimmed(value);
		return text == null ? null : new BigDecimal(text).longValueExact();
	}
}
