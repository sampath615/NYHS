package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.oracleText;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toInteger;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toLong;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.upper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrTest;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

/**
 * Builds the ICCR (Infection Control) report rows: the report as entered on the data entry screen
 * (RAW_ICCR_DOC) with its tests (RAW_ICCR_TESTS) and the standardized report the import writes from it (STD_ICCR_DOC).
 * <p>
 * The cleaning the former SQL inserts did in Oracle is done here: text is trimmed of spaces and
 * upper-cased, an empty text is stored as null (as Oracle does), see {@link OracleConversions}; the entered dates
 * were read when the form was bound (an invalid date is null, as FN_TO_DATE made it).
 */
final class IccrDocMapping {

	/** CURRENT_PROCESS_STATUS of a report just entered. */
	private static final int ENTERED_PROCESS_STATUS = 1;

	/** CURRENT_PROCESS_STATUS of a standardized report. */
	private static final String STANDARDIZED_PROCESS_STATUS = "5";

	private IccrDocMapping() {
	}

	/**
	 * The report as entered, with the test rows that were filled in.
	 *
	 * @param form the posted data entry form; only its form fields are taken
	 * @param documentId the new document id of the report
	 * @param workerId the worker who entered it
	 * @return the new RAW_ICCR_DOC row with its RAW_ICCR_TESTS rows
	 */
	static RawIccrDoc toRawDoc(RawIccrDoc form, int documentId, int workerId) {
		return RawIccrDoc.builder()
				.documentId(documentId)
				.firstName(upper(form.getFirstName()))
				.middleName(upper(form.getMiddleName()))
				.lastName(upper(form.getLastName()))
				.dob(form.getDob())
				.personAddress(upper(form.getPersonAddress()))
				.personCity(upper(form.getPersonCity()))
				.personState(upper(form.getPersonState()))
				.personZip(upper(form.getPersonZip()))
				.personCounty(upper(form.getPersonCounty()))
				.personPhone(upper(form.getPersonPhone()))
				.jurisdictionProvidingInfo(upper(form.getJurisdictionProvidingInfo()))
				.provName(upper(form.getProvName()))
				.provAddress(upper(form.getProvAddress()))
				.provCity(upper(form.getProvCity()))
				.provState(upper(form.getProvState()))
				.provZip(upper(form.getProvZip()))
				.provCounty(upper(form.getProvCounty()))
				.provPhone(upper(form.getProvPhone()))
				.resultsReceived(upper(form.getResultsReceived()))
				.comments(upper(form.getComments()))
				.createUid(workerId)
				.createDate(new Timestamp(System.currentTimeMillis()))
				.currentProcessStatus(ENTERED_PROCESS_STATUS)
				.iccrTestList(filledInTests(form.getIccrTestList()))
				.build();
	}

	/**
	 * The test rows to save: those whose type, date or result is filled in. Their text is stored as entered
	 * (the former insert did not trim or upper-case it).
	 */
	private static List<RawIccrTest> filledInTests(List<RawIccrTest> tests) {
		if (tests == null) {
			return List.of();
		}
		return tests.stream()
				.filter(test -> test != null && (CommonUtil.isNotNullAndEmpty(test.getTestType())
						|| test.getDateOfTest() != null
						|| CommonUtil.isNotNullAndEmpty(test.getTestResults())))
				.toList();
	}

	/**
	 * The standardized report for a received report: the names and addresses are cleaned, every other
	 * column is copied from the raw field of the same name.
	 *
	 * @param raw the received report (a RAW_ICCR_DOC row), value per column name
	 * @return the new STD_ICCR_DOC row; columns without a value are null
	 */
	static StdIccrDoc toStdDoc(Map<Object, Object> raw) {
		return StdIccrDoc.builder()
				.documentId(toInteger(raw.get("DOCUMENT_ID")))

				// person
				.fname(upper(CommonUtil.NameValidation(text(raw, "FIRST_NAME"))))
				.mname(upper(CommonUtil.NameValidation(text(raw, "MIDDLE_NAME"))))
				.lname(upper(CommonUtil.NameValidation(text(raw, "LAST_NAME"))))
				.dob(date(raw.get("DOB")))
				.personAddress(upper(CommonUtil.addressValidation(text(raw, "PERSON_ADDRESS"))))
				.personCity(upper(raw.get("PERSON_CITY")))
				.personState(upper(raw.get("PERSON_STATE")))
				.personZip(upper(raw.get("PERSON_ZIP")))
				.personCounty(upper(raw.get("PERSON_COUNTY")))
				.personPhone(upper(raw.get("PERSON_PHONE")))
				.personCensusTract(upper(raw.get("PERSON_CENSUS_TRACT")))
				.personLatitude(upper(raw.get("PERSON_LATITUDE")))
				.personLongitude(upper(raw.get("PERSON_LONGITUDE")))

				// provider
				.provName(upper(CommonUtil.NameValidation(text(raw, "PROV_NAME"))))
				.jurisdictionProvidingInfo(upper(raw.get("JURISDICTION_PROVIDING_INFO")))
				.provAddress(upper(CommonUtil.addressValidation(text(raw, "PROV_ADDRESS"))))
				.provCity(upper(raw.get("PROV_CITY")))
				.provState(upper(raw.get("PROV_STATE")))
				.provZip(upper(raw.get("PROV_ZIP")))
				.provCounty(upper(raw.get("PROV_COUNTY")))
				.provPhone(upper(raw.get("PROV_PHONE")))
				.provCensusTract(upper(raw.get("PROV_CENSUS_TRACT")))
				.provLatitude(upper(raw.get("PROV_LATITUDE")))
				.provLongitude(upper(raw.get("PROV_LONGITUDE")))

				// report, eHARS, matching
				.resultsReceived(upper(raw.get("RESULTS_RECEIVED")))
				.comments(upper(raw.get("COMMENTS")))
				.eharsStatusCd(upper(raw.get("EHARS_STATUS_CD")))
				.eharsImportDate(date(raw.get("EHARS_IMPORT_DATE")))
				.matchCd(upper(raw.get("MATCH_CD")))
				.matchedStateno(upper(raw.get("MATCHED_STATENO")))
				.matchPass(upper(raw.get("MATCH_PASS")))
				.matchWeight(upper(raw.get("MATCH_WEIGHT")))
				.matchedProcessDate(date(raw.get("MATCHED_PROCESS_DATE")))
				.fluxMatchCd(oracleText(raw.get("FLUX_MATCH_CD")))
				.fluxPotStateno(oracleText(raw.get("FLUX_POT_STATENO")))
				.fluxMatchedStateno(oracleText(raw.get("FLUX_MATCHED_STATENO")))
				.fluxQsParentDocId(toLong(raw.get("FLUX_QS_PARENT_DOC_ID")))
				.fluxMatchedProcessDate(date(raw.get("FLUX_MATCHED_PROCESS_DATE")))
				.reviewUid(upper(raw.get("REVIEW_UID")))
				.reviewDt(date(raw.get("REVIEW_DT")))

				// as received, except the status
				.createUid(upper(raw.get("CREATE_UID")))
				.createDate(date(raw.get("CREATE_DATE")))
				.currentProcessStatus(STANDARDIZED_PROCESS_STATUS)
				.build();
	}

	/** A text field. */
	private static String text(Map<Object, Object> raw, String field) {
		return (String) raw.get(field);
	}
}
