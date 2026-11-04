package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.fnToDate;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.oracleText;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toDecimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toInteger;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.toLong;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.trimmed;
import static gov.ny.state.doh.bhae.nyehms.web.spring.service.document.OracleConversions.upper;

import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycijrDoc;

/**
 * Builds the standardized NYC IJR record (STD_NYCIJR_DOC) from the values NycIJRDocumentService prepared, column by
 * column as the former SQL insert wrote them: text trimmed of spaces and upper-cased (FILE_NAME only trimmed, the
 * FLUX_* texts as they are), an empty text stored as null, the form dates through FN_TO_DATE(..., 'MM/DD/YYYY'), see
 * {@link OracleConversions}.
 */
final class NycIjrStdDocMapping {

	private NycIjrStdDocMapping() {
	}

	/**
	 * The standardized record.
	 *
	 * @param raw the prepared values, by column name; not changed
	 * @return the new STD_NYCIJR_DOC row; columns without a value are null
	 */
	static StdNycijrDoc toStdDoc(Map<Object, Object> raw) {
		return StdNycijrDoc.builder()
				.reviewUid(upper(raw.get("REVIEW_UID")))
				.din(upper(raw.get("DIN")))
				.altLname(upper(raw.get("ALT_LNAME")))
				.dxPersonAddress(upper(raw.get("DX_PERSON_ADDRESS")))
				.curPersonAddress(upper(raw.get("CUR_PERSON_ADDRESS")))
				.provAddress(upper(raw.get("PROV_ADDRESS")))
				.provName(upper(raw.get("PROV_NAME")))
				.provDepartment(upper(raw.get("PROV_DEPARTMENT")))
				.provSigned(upper(raw.get("PROV_SIGNED")))
				.completerFname(upper(raw.get("COMPLETER_FNAME")))
				.completerMi(upper(raw.get("COMPLETER_MI")))
				.completerLname(upper(raw.get("COMPLETER_LNAME")))
				.completerTitle(upper(raw.get("COMPLETER_TITLE")))
				.completerEmail(upper(raw.get("COMPLETER_EMAIL")))
				.contactLname(upper(raw.get("CONTACT_LNAME")))
				.contactFname(upper(raw.get("CONTACT_FNAME")))
				.contactMname(upper(raw.get("CONTACT_MNAME")))
				.contactTitle(upper(raw.get("CONTACT_TITLE")))
				.dxPersonCensusTract(upper(raw.get("DX_PERSON_CENSUS_TRACT")))
				.curPersonCensusTract(upper(raw.get("CUR_PERSON_CENSUS_TRACT")))
				.provCensusTract(upper(raw.get("PROV_CENSUS_TRACT")))
				.dxPersonZip(upper(raw.get("DX_PERSON_ZIP")))
				.curPersonZip(upper(raw.get("CUR_PERSON_ZIP")))
				.provZip(upper(raw.get("PROV_ZIP")))
				.dxPersonCity(upper(raw.get("DX_PERSON_CITY")))
				.curPersonCity(upper(raw.get("CUR_PERSON_CITY")))
				.provCity(upper(raw.get("PROV_CITY")))
				.hivFacilityName(upper(raw.get("HIV_FACILITY_NAME")))
				.dxPersonCounty(upper(raw.get("DX_PERSON_COUNTY")))
				.curPersonCounty(upper(raw.get("CUR_PERSON_COUNTY")))
				.provCounty(upper(raw.get("PROV_COUNTY")))
				.fileName(trimmed(raw.get("FILE_NAME")))
				.lname(upper(raw.get("LNAME")))
				.fname(upper(raw.get("FNAME")))
				.mname(upper(raw.get("MNAME")))
				.medRecordNumber(upper(raw.get("MED_RECORD_NUMBER")))
				.provPhone(upper(raw.get("PROV_PHONE")))
				.riskOtherText(upper(raw.get("RISK_OTHER_TEXT")))
				.matchCd(upper(raw.get("MATCH_CD")))
				.ethnicity(upper(raw.get("ETHNICITY")))
				.dxPersonState(upper(raw.get("DX_PERSON_STATE")))
				.curPersonState(upper(raw.get("CUR_PERSON_STATE")))
				.provState(upper(raw.get("PROV_STATE")))
				.birthCountry(upper(raw.get("BIRTH_COUNTRY")))
				.dxPersonTelephone(upper(raw.get("DX_PERSON_TELEPHONE")))
				.personCellphone(upper(raw.get("PERSON_CELLPHONE")))
				.personWorkphone(upper(raw.get("PERSON_WORKPHONE")))
				.curPersonCountyName(upper(raw.get("CUR_PERSON_COUNTY_NAME")))
				.curPersonPhone(upper(raw.get("CUR_PERSON_PHONE")))
				.recvBloodProdCountry(upper(raw.get("RECV_BLOOD_PROD_COUNTRY")))
				.contactPhone(upper(raw.get("CONTACT_PHONE")))
				.rshPhone(upper(raw.get("RSH_PHONE")))
				.ssn(upper(raw.get("SSN")))
				.dxPersonLongitude(upper(raw.get("DX_PERSON_LONGITUDE")))
				.curPersonLongitude(upper(raw.get("CUR_PERSON_LONGITUDE")))
				.provLongitude(upper(raw.get("PROV_LONGITUDE")))
				.cityno(upper(raw.get("CITYNO")))
				.nycSentStateno(upper(raw.get("NYC_SENT_STATENO")))
				.dobText(upper(raw.get("DOB_TEXT")))
				.raceCd(upper(raw.get("RACE_CD")))
				.nysid(upper(raw.get("NYSID")))
				.admissionDateText(upper(raw.get("ADMISSION_DATE_TEXT")))
				.initHivDiagDateText(upper(raw.get("INIT_HIV_DIAG_DATE_TEXT")))
				.initAidsDiagDateText(upper(raw.get("INIT_AIDS_DIAG_DATE_TEXT")))
				.informedHivDateText(upper(raw.get("INFORMED_HIV_DATE_TEXT")))
				.indxInterviewedDateText(upper(raw.get("INDX_INTERVIEWED_DATE_TEXT")))
				.recvBloodProdDateText(upper(raw.get("RECV_BLOOD_PROD_DATE_TEXT")))
				.matchedStateno(upper(raw.get("MATCHED_STATENO")))
				.hivDxDateText(upper(raw.get("HIV_DX_DATE_TEXT")))
				.aidsDxDateText(upper(raw.get("AIDS_DX_DATE_TEXT")))
				.dxPersonLatitude(upper(raw.get("DX_PERSON_LATITUDE")))
				.curPersonLatitude(upper(raw.get("CUR_PERSON_LATITUDE")))
				.provLatitude(upper(raw.get("PROV_LATITUDE")))
				.currentGender(upper(raw.get("CURRENT_GENDER")))
				.initHivDiag(upper(raw.get("INIT_HIV_DIAG")))
				.initAidsDiag(upper(raw.get("INIT_AIDS_DIAG")))
				.informedHivPos(upper(raw.get("INFORMED_HIV_POS")))
				.indxInterviewed(upper(raw.get("INDX_INTERVIEWED")))
				.swm(upper(raw.get("SWM")))
				.swf(upper(raw.get("SWF")))
				.idu(upper(raw.get("IDU")))
				.sexPartnerIdu(upper(raw.get("SEX_PARTNER_IDU")))
				.sexPartnerHiv(upper(raw.get("SEX_PARTNER_HIV")))
				.recvBloodProd(upper(raw.get("RECV_BLOOD_PROD")))
				.perinatal(upper(raw.get("PERINATAL")))
				.riskOther(upper(raw.get("RISK_OTHER")))
				.riskUnknown(upper(raw.get("RISK_UNKNOWN")))
				.sexPartnerMsm(upper(raw.get("SEX_PARTNER_MSM")))
				.psRequested(upper(raw.get("PS_REQUESTED")))
				.processed(upper(raw.get("PROCESSED")))
				.numberofpartners(toInteger(raw.get("NUMBEROFPARTNERS")))
				.nycIjrSrcId(toLong(raw.get("NYC_IJR_SRC_ID")))
				.documentId(toInteger(raw.get("DOCUMENT_ID")))
				.currentProcessStatus(toInteger(raw.get("CURRENT_PROCESS_STATUS")))
				.age(toInteger(raw.get("AGE")))
				.matchPass(toInteger(raw.get("MATCH_PASS")))
				.matchWeight(toDecimal(raw.get("MATCH_WEIGHT")))
				.raceIndian(upper(raw.get("RACE_INDIAN")))
				.raceBlack(upper(raw.get("RACE_BLACK")))
				.raceAsian(upper(raw.get("RACE_ASIAN")))
				.racePacific(upper(raw.get("RACE_PACIFIC")))
				.raceWhite(upper(raw.get("RACE_WHITE")))
				.raceMultiple(upper(raw.get("RACE_MULTIPLE")))
				.raceOther(upper(raw.get("RACE_OTHER")))
				.raceUnknown(upper(raw.get("RACE_UNKNOWN")))
				.provSignedDate(fnToDate(raw.get("PROV_SIGNED_DATE")))
				.completedDate(fnToDate(raw.get("COMPLETED_DATE")))
				.fileDate(fnToDate(raw.get("FILE_DATE")))
				.createDate(date(raw.get("CREATE_DATE")))
				.reviewDt(date(raw.get("REVIEW_DT")))
				.matchedProcessDate(date(raw.get("MATCHED_PROCESS_DATE")))
				.eharsImportDate(date(raw.get("EHARS_IMPORT_DATE")))
				.nycShipDate(date(raw.get("NYC_SHIP_DATE")))
				.createUid(upper(raw.get("CREATE_UID")))
				.fluxMatchCd(oracleText(raw.get("FLUX_MATCH_CD")))
				.fluxPotStateno(oracleText(raw.get("FLUX_POT_STATENO")))
				.fluxMatchedStateno(oracleText(raw.get("FLUX_MATCHED_STATENO")))
				.fluxQsParentDocId(toLong(raw.get("FLUX_QS_PARENT_DOC_ID")))
				.fluxMatchedProcessDate(date(raw.get("FLUX_MATCHED_PROCESS_DATE")))
				.build();
	}
}
