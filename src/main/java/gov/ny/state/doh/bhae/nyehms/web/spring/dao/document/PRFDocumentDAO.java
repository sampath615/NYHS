package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.dateOnly;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.decimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.integer;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.trim;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.upperTrim;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.upperTrimDate;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.upperTrimDecimal;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFDocumentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdPrfDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawPrfDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawPrfPartnerRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.StdPrfDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.StdPrfPartnerRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawPrfDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * The provider report forms (PRF): the document screens, the data entry, the review list, the import of a form and its
 * partners into the standardized tables, and the copy of a form (formerly a MyBatis mapper with the same name and
 * methods), through Spring Data repositories and JPQL over RawPrfDoc, StdPrfDoc, RawPrfPartner, StdPrfPartner and
 * PersonDocAssociation. No native SQL.
 * <p>
 * The two document reads are JPQL whose select items carry the former column labels; the rows fill PRFDocumentVO as
 * MyBatis filled it ({@link Rows#bean}: setter by label, null values not set). The completer's and the contact's names
 * are the database function PKG_TRK_COMMON.fn_get_fullname, called from JPQL.
 * <p>
 * The inserts save entities built here column by column as the former inserts wrote them: UPPER(TRIM(...)) and TRIM(...)
 * as Oracle computes them, a value as the text Oracle converts it to, #{x,jdbcType=DATE} of a java.util.Date as its day
 * (see {@link gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues}). The SYSDATE of the data entry's
 * ENTRY_REVIEW_DATE is the application server's clock; the status update keeps the database's SYSDATE. The partner
 * copies are HQL insert ... selects (the former insert ... selects); the copied form's id, formerly pasted into the SQL
 * with ${newDocumentId}, is a bound parameter.
 */
@Repository
@RequiredArgsConstructor
public class PRFDocumentDAO {

	/** The former getPRFDoc: the forms, their standardized row and their current person match. */
	private static final String PRF_DOC = """
			select std.documentId as DOCUMENT_ID, std.prfSrcId as PRF_SRC_ID, std.fname as FNAME, std.mname as MNAME,
						std.lname as LNAME, std.suffix as SUFFIX, std.altFname as ALT_FNAME, std.altMname as ALT_MNAME, std.altLname
						as ALT_LNAME, std.dobText as DOB, std.dodText as DOD_TEXT, std.medRecordNumber as MED_RECORD_NUMBER, std.din
						as DIN, raw.personAddress as rawPersonAddress, std.personAddress as PERSON_ADDRESS, std.personCity as
						PERSON_CITY, std.personState as PERSON_STATE, std.personZip as PERSON_ZIP, std.personPlus4 as PERSON_PLUS4,
						std.personCounty as PERSON_COUNTY, std.personPhone as PERSON_PHONE, std.gender as GENDER, std.ethnicity as
						ETHNICITY, std.raceWhite as RACE_WHITE, std.raceBlack as RACE_BLACK, std.raceAsian as RACE_ASIAN,
						std.raceIndian as RACE_INDIAN, std.racePacific as RACE_PACIFIC, std.raceUnknown as RACE_UNKNOWN,
						std.raceOther as RACE_OTHER, std.raceOtherText as RACE_OTHER_TEXT, std.birthCountry as BIRTH_COUNTRY, std.swm
						as swm, std.swf as swf, std.idu as idu, std.sexPartnerIdu as sexIDU, std.sexPartnerHiv as sexAIDSHIV,
						std.recvBloodProd as rcvdTransfusion, std.perinatal as perinatalExposure, std.riskOther as riskOther,
						std.riskUnknown as riskUnknown, std.sexPartnerMsm as sexBI, std.riskOtherText as RISK_OTHER_TEXT,
						std.recvBloodProdDateText as RECV_BLOOD_PROD_DATE, std.recvBloodProdCountry as RECV_BLOOD_PROD_COUNTRY,
						std.admissionDateText as ADMISSION_DATE, std.initHivDiag as INIT_HIV_DIAG, std.initHivDiagDateText as
						INIT_HIV_DIAG_DATE_TEXT, std.informedHivPos as INFORMED_HIV_POS, std.informedHivDateText as
						INFORMED_HIV_DATE_TEXT, std.prevHivDiag as PREV_HIV_DIAG, std.prevHivDiagDateText as PREV_HIV_DIAG_DATE_TEXT,
						std.initAidsDiag as INIT_AIDS_DIAG, std.initAidsDiagDateText as INIT_AIDS_DIAG_DATE_TEXT, std.prevAidsDiag as
						PREV_AIDS_DIAG, std.prevAidsDiagDateText as PREV_AIDS_DIAG_DATE_TEXT, std.psRequested as PS_REQUESTED,
						std.completerLname as COMPLETER_LNAME, std.completerMi as COMPLETER_MNAME, std.completerFname as
						COMPLETER_FNAME, function('pkg_trk_common.fn_get_fullname' as String, std.completerLname, std.completerFname,
						std.completerMi, null) as COMPLETER_NAME, std.completerTitle as COMPLETER_TITLE, std.completerPhone as
						COMPLETER_PHONE, std.completerPhoneExt as COMPLETER_PHONE_EXT, std.completerEmail as COMPLETER_EMAIL,
						std.facilityName as FACILITY_NAME, std.facilityDept as FACILITY_DEPT, std.facilityAddress as
						FACILITY_ADDRESS, std.facilityCity as FACILITY_CITY, std.facilityState as FACILITY_STATE, std.facilityZip as
						FACILITY_ZIP, std.facilityPlus4 as FACILITY_PLUS4, std.facilityCounty as FACILITY_COUNTY, std.completedDate
						as COMPLETED_DATE, std.contactLname as CONTACT_LNAME, std.contactFname as CONTACT_FNAME, std.contactMi as
						CONTACT_MNAME, function('pkg_trk_common.fn_get_fullname' as String, std.contactLname, std.contactFname,
						std.contactMi, null) as CONTACT_NAME, std.contactTitle as CONTACT_TITLE, std.contactPhone as CONTACT_PHONE,
						std.contactPhoneExt as CONTACT_PHONE_EXT, std.provLname as PROV_LNAME, std.provFname as PROV_FNAME,
						std.provMi as PROV_MI, std.provSigned as PROV_SIGNED, std.provSignedDate as PROV_SIGNED_DATE, std.createUid
						as CREATE_UID, std.createDate as CREATE_DATE, std.entryReviewUid as ENTRY_REVIEW_UID, std.entryReviewDate as
						ENTRY_REVIEW_DATE, std.comments as COMMENTS, pdoc.stateno as STATENO, pdoc.discardStatus as DISCARD_STATUS,
						std.eharsImportDate as EHARS_IMPORT_DATE, 'PRF' as TTHMAINTTHSOURCE, std.ptInterviewDateText as
						PT_INTERVIEW_DATE, std.firstPosHivDateText as FIRST_POS_HIV_DATE, std.firstPosTestAnon as
						FIRST_POS_TEST_ANON, std.firstPosFacType as FIRST_POS_FAC_TYPE, std.firstPosFacName as FIRST_POS_FAC_NAME,
						std.firstPosFacState as FIRST_POS_FAC_STATE, std.rsonFirstPosExp as RSON_FIRST_POS_EXP, std.rsonFirstPosRoutn
						as RSON_FIRST_POS_ROUTN, std.rsonFirstPosChkneg as RSON_FIRST_POS_CHKNEG, std.rsonFirstPosReqd as
						RSON_FIRST_POS_REQD, std.rsonFirstPosPrtnr as RSON_FIRST_POS_PRTNR, std.rsonFirstPosOther as
						RSON_FIRST_POS_OTHER, std.rsonFirstPosOtherText as RSON_FIRST_POS_OTHER_TEXT, std.ptArv6Mo as PT_ARV_6_MO,
						std.ptArvName as PT_ARV_NAME, std.ptArvStartDateText as PT_ARV_START_DATE, std.ptArvEndDateText as
						PT_ARV_END_DATE, std.ptArvTimeOfTest as PT_ARV_TIME_OF_TEST, std.firstHivTestEverDateText as
						FIRST_HIV_TEST_EVER_DATE, std.numberTest2Yrs as COUNT_HIV_TEST_2YRS, std.respHivTest2yrs as
						RESP_HIV_TEST_2YRS, std.everHadNeg as EVER_HAD_NEG, std.lastNegDateText as LAST_NEG_DATE, std.lastNegFacType
						as LAST_NEG_FAC_TYPE, std.lastNegFacName as LAST_NEG_FAC_NAME, std.lastNegFacState as LAST_NEG_FAC_STATE,
						std.nycShipDate as NYC_SHIP_DATE, raw.createDate as FILE_RECEVIED_DATE, raw.processDate as
						FILE_PROCESSED_DATE, std.personAddressType as PERSON_ADDRESS_TYPE, std.sexAtBirth as SEX_AT_BIRTH,
						std.pregnant as PREGNANT, std.edc as EDC, std.personOtherIdentifier as PERSON_OTHER_IDENTIFIER,
						std.primaryLanguage as PRIMARY_LANGUAGE, std.primaryLanguageOther as PRIMARY_LANGUAGE_OTHER,
						std.acuteInfection as ACUTE_INFECTION, std.referredHivProvider as REFERRED_HIV_PROVIDER,
						std.referredProviderName as REFERRED_PROVIDER_NAME, std.referredProviderTelephone as
						REFERRED_PROVIDER_TELEPHONE, std.everHadPrevPosTest as EVER_HAD_PREV_POS_TEST, std.ptArvEverTaken as
						PT_ARV_EVER_TAKEN, std.arvPrevenPrep as ARV_PREVEN_PREP, std.arvPrevenPrepStartDt as
						ARV_PREVEN_PREP_START_DT, std.arvPrevenPrepEndDt as ARV_PREVEN_PREP_END_DT, std.arvPrevenprepMedNames as
						ARV_PREVENPREP_MED_NAMES, std.postExpArvPep as POST_EXP_ARV_PEP, std.postExpArvPepStartDt as
						POST_EXP_ARV_PEP_START_DT, std.postExpArvPepEndDt as POST_EXP_ARV_PEP_END_DT, std.postExpArvPepMedNames as
						POST_EXP_ARV_PEP_MED_NAMES, std.arvTreatment as ARV_TREATMENT, std.rsonClinicianOfferedRec as
						RSON_CLINICIAN_OFFERED_REC, std.facilityType as FACILITY_TYPE, std.facilityTypeOtherText as
						FACILITY_TYPE_OTHER_TEXT, std.providerNysLicNo as PROVIDER_NYS_LIC_NO, std.providerNpiNo as PROVIDER_NPI_NO,
						std.additionalGenderIdentity as ADDITIONAL_GENDER_IDENTITY, std.nysid as NYSID, std.provLicenseProfCd as
						PROV_LICENSE_PROF_CD, std.addressMappingId as ADDRESS_MAPPING_ID, std.sexualOrientation as
						SEXUAL_ORIENTATION, std.addSexualOrienationIdentity as ADD_SEXUAL_ORIENATION_IDENTITY
				from RawPrfDoc raw join StdPrfDoc std on std.documentId = raw.documentId and raw.documentId = ?1
				left join PersonDocAssociation pdoc on raw.documentId = pdoc.documentId and pdoc.dissassociationDate is null
			""";

	/** The former getCompleteRawPRFDocumentContent. */
	private static final String RAW_PRF_DOC = """
			select raw.documentId as DOCUMENT_ID, raw.prfSrcId as PRF_SRC_ID, raw.fname as FNAME, raw.mname as MNAME,
						raw.lname as LNAME, raw.suffix as SUFFIX, raw.altFname as ALT_FNAME, raw.altMname as ALT_MNAME, raw.altLname
						as ALT_LNAME, raw.dobText as DOB, raw.dodText as DOD_TEXT, raw.medRecordNumber as MED_RECORD_NUMBER, raw.din
						as DIN, raw.personAddress as PERSON_ADDRESS, raw.personCity as PERSON_CITY, raw.personState as PERSON_STATE,
						raw.personZip as PERSON_ZIP, raw.personPlus4 as PERSON_PLUS4, raw.personCounty as PERSON_COUNTY,
						raw.personTelephone as PERSON_PHONE, raw.gender as GENDER, raw.ethnicity as ETHNICITY, raw.raceWhite as
						RACE_WHITE, raw.raceBlack as RACE_BLACK, raw.raceAsian as RACE_ASIAN, raw.raceIndian as RACE_INDIAN,
						raw.racePacific as RACE_PACIFIC, raw.raceUnknown as RACE_UNKNOWN, raw.raceOther as RACE_OTHER,
						raw.raceOtherText as RACE_OTHER_TEXT, raw.birthCountry as BIRTH_COUNTRY, raw.risk01 as swm, raw.risk02 as
						swf, raw.risk03 as idu, raw.risk04 as sexIDU, raw.risk05 as sexAIDSHIV, raw.risk06 as rcvdTransfusion,
						raw.risk07 as perinatalExposure, raw.risk08 as riskOther, raw.risk09 as riskUnknown, raw.risk10 as sexBI,
						raw.riskOtherText as RISK_OTHER_TEXT, raw.recvBloodProdDate as RECV_BLOOD_PROD_DATE, raw.recvBloodProdCountry
						as RECV_BLOOD_PROD_COUNTRY, raw.admissionDate as ADMISSION_DATE, raw.initHivDiag as INIT_HIV_DIAG,
						raw.initHivDiagDateText as INIT_HIV_DIAG_DATE_TEXT, raw.informedHivPos as INFORMED_HIV_POS,
						raw.informedHivDateText as INFORMED_HIV_DATE_TEXT, raw.prevHivDiag as PREV_HIV_DIAG, raw.prevHivDiagDateText
						as PREV_HIV_DIAG_DATE_TEXT, raw.initAidsDiag as INIT_AIDS_DIAG, raw.initAidsDiagDateText as
						INIT_AIDS_DIAG_DATE_TEXT, raw.prevAidsDiag as PREV_AIDS_DIAG, raw.prevAidsDiagDateText as
						PREV_AIDS_DIAG_DATE_TEXT, raw.psRequested as PS_REQUESTED, raw.completerLname as COMPLETER_LNAME,
						raw.completerMname as COMPLETER_MNAME, raw.completerFname as COMPLETER_FNAME, raw.completerTitle as
						COMPLETER_TITLE, raw.completerPhone as COMPLETER_PHONE, raw.completerPhoneExt as COMPLETER_PHONE_EXT,
						raw.completerEmail as COMPLETER_EMAIL, raw.facilityName as FACILITY_NAME, raw.facilityDept as FACILITY_DEPT,
						raw.facilityAddress as FACILITY_ADDRESS, raw.facilityCity as FACILITY_CITY, raw.facilityState as
						FACILITY_STATE, raw.facilityZip as FACILITY_ZIP, raw.facilityPlus4 as FACILITY_PLUS4, raw.facilityCounty as
						FACILITY_COUNTY, raw.completedDate as COMPLETED_DATE, raw.contactLname as CONTACT_LNAME, raw.contactFname as
						CONTACT_FNAME, raw.contactMname as CONTACT_MNAME, raw.contactTitle as CONTACT_TITLE, raw.contactPhone as
						CONTACT_PHONE, raw.contactPhoneExt as CONTACT_PHONE_EXT, raw.provLname as PROV_LNAME, raw.provFname as
						PROV_FNAME, raw.provMi as PROV_MI, raw.provSigned as PROV_SIGNED, raw.provSignedDate as PROV_SIGNED_DATE,
						raw.createUid as CREATE_UID, raw.createDate as CREATE_DATE, raw.entryReviewUid as ENTRY_REVIEW_UID,
						raw.entryReviewDate as ENTRY_REVIEW_DATE, raw.comments as COMMENTS, raw.ptInterviewDate as PT_INTERVIEW_DATE,
						raw.firstPosHivDate as FIRST_POS_HIV_DATE, raw.firstPosTestAnon as FIRST_POS_TEST_ANON, raw.firstPosFacType
						as FIRST_POS_FAC_TYPE, raw.firstPosFacName as FIRST_POS_FAC_NAME, raw.firstPosFacState as
						FIRST_POS_FAC_STATE, raw.rsonFirstPosExp as RSON_FIRST_POS_EXP, raw.rsonFirstPosRoutn as
						RSON_FIRST_POS_ROUTN, raw.rsonFirstPosChkneg as RSON_FIRST_POS_CHKNEG, raw.rsonFirstPosReqd as
						RSON_FIRST_POS_REQD, raw.rsonFirstPosPrtnr as RSON_FIRST_POS_PRTNR, raw.rsonFirstPosOther as
						RSON_FIRST_POS_OTHER, raw.rsonFirstPosOtherText as RSON_FIRST_POS_OTHER_TEXT, raw.ptArv6Mo as PT_ARV_6_MO,
						raw.ptArvName as PT_ARV_NAME, raw.ptArvStartDate as PT_ARV_START_DATE, raw.ptArvEndDate as PT_ARV_END_DATE,
						raw.ptArvTimeOfTest as PT_ARV_TIME_OF_TEST, raw.firstHivTestEverDate as FIRST_HIV_TEST_EVER_DATE,
						raw.countHivTest2yrs as COUNT_HIV_TEST_2YRS, raw.respHivTest2yrs as RESP_HIV_TEST_2YRS, raw.everHadNeg as
						EVER_HAD_NEG, raw.lastNegDate as LAST_NEG_DATE, raw.lastNegFacType as LAST_NEG_FAC_TYPE, raw.lastNegFacName
						as LAST_NEG_FAC_NAME, raw.lastNegFacState as LAST_NEG_FAC_STATE, raw.partners as PARTNERS,
						raw.eharsDocumentSourceCd as EHARS_DOCUMENT_SOURCE_CD, raw.provFacTypeCode as PROV_FAC_TYPE_CODE,
						raw.currentProcessStatus as CURRENT_PROCESS_STATUS, raw.personAddressType as PERSON_ADDRESS_TYPE,
						raw.sexAtBirth as SEX_AT_BIRTH, raw.pregnant as PREGNANT, raw.edc as EDC, raw.personOtherIdentifier as
						PERSON_OTHER_IDENTIFIER, raw.primaryLanguage as PRIMARY_LANGUAGE, raw.primaryLanguageOther as
						PRIMARY_LANGUAGE_OTHER, raw.acuteInfection as ACUTE_INFECTION, raw.referredHivProvider as
						REFERRED_HIV_PROVIDER, raw.referredProviderName as REFERRED_PROVIDER_NAME, raw.referredProviderTelephone as
						REFERRED_PROVIDER_TELEPHONE, raw.everHadPrevPosTest as EVER_HAD_PREV_POS_TEST, raw.ptArvEverTaken as
						PT_ARV_EVER_TAKEN, raw.arvPrevenPrep as ARV_PREVEN_PREP, raw.arvPrevenPrepStartDt as
						ARV_PREVEN_PREP_START_DT, raw.arvPrevenPrepEndDt as ARV_PREVEN_PREP_END_DT, raw.arvPrevenprepMedNames as
						ARV_PREVENPREP_MED_NAMES, raw.postExpArvPep as POST_EXP_ARV_PEP, raw.postExpArvPepStartDt as
						POST_EXP_ARV_PEP_START_DT, raw.postExpArvPepEndDt as POST_EXP_ARV_PEP_END_DT, raw.postExpArvPepMedNames as
						POST_EXP_ARV_PEP_MED_NAMES, raw.arvTreatment as ARV_TREATMENT, raw.rsonClinicianOfferedRec as
						RSON_CLINICIAN_OFFERED_REC, raw.facilityType as FACILITY_TYPE, raw.facilityTypeOtherText as
						FACILITY_TYPE_OTHER_TEXT, raw.providerNysLicNo as PROVIDER_NYS_LIC_NO, raw.providerNpiNo as PROVIDER_NPI_NO,
						raw.additionalGenderIdentity as ADDITIONAL_GENDER_IDENTITY, raw.nysid as NYSID, raw.provLicenseProfCd as
						PROV_LICENSE_PROF_CD, raw.addressMappingId as ADDRESS_MAPPING_ID, raw.sexualOrientation as
						SEXUAL_ORIENTATION, raw.addSexualOrienationIdentity as ADD_SEXUAL_ORIENATION_IDENTITY
				from RawPrfDoc raw where raw.documentId = ?1
			""";

	private final JpqlStatements jpql;
	private final RawPrfDocRepository rawDocs;
	private final StdPrfDocRepository stdDocs;
	private final RawPrfPartnerRepository rawPartners;
	private final StdPrfPartnerRepository stdPartners;

	/**
	 * A form for the PRF document screen: the standardized form, the received address and file dates, the current
	 * person match.
	 *
	 * @return the form, or null if there is none
	 * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if it has several current person matches
	 */
	public PRFDocumentVO getPRFDoc(int docId) throws DataAccessException {
		return jpql.one(PRF_DOC, Rows.bean(PRFDocumentVO.class), docId);
	}

	/**
	 * Inserts a form entered on the data entry screen (status 1; ENTRY_REVIEW_DATE now).
	 *
	 * @return 1
	 */
	@Transactional
	public int insertPrfDocData(PRFDocumentVO pRFDocumentVO) throws DataAccessException {
		PRFDocumentVO vo = pRFDocumentVO;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		rawDocs.saveAndFlush(
				RawPrfDoc.builder()
					.documentId(integer(vo.getDOCUMENT_ID()))
					.prfSrcId(longValue(vo.getPRF_SRC_ID()))
					.fname(text(vo.getFNAME()))
					.mname(text(vo.getMNAME()))
					.lname(text(vo.getLNAME()))
					.suffix(text(vo.getSUFFIX()))
					.altFname(text(vo.getALT_FNAME()))
					.altMname(text(vo.getALT_MNAME()))
					.altLname(text(vo.getALT_LNAME()))
					.dobText(text(vo.getDOB()))
					.dodText(text(vo.getDOD_TEXT()))
					.medRecordNumber(text(vo.getMED_RECORD_NUMBER()))
					.din(text(vo.getDIN()))
					.personAddress(text(vo.getPERSON_ADDRESS()))
					.personCity(text(vo.getPERSON_CITY()))
					.personState(text(vo.getPERSON_STATE()))
					.personZip(text(vo.getPERSON_ZIP()))
					.personPlus4(text(vo.getPERSON_PLUS4()))
					.personCounty(text(vo.getPERSON_COUNTY()))
					.personTelephone(text(vo.getPERSON_PHONE()))
					.gender(text(vo.getGENDER()))
					.ethnicity(text(vo.getETHNICITY()))
					.raceWhite(text(vo.isRACE_WHITE()))
					.raceBlack(text(vo.isRACE_BLACK()))
					.raceAsian(text(vo.isRACE_ASIAN()))
					.raceIndian(text(vo.isRACE_INDIAN()))
					.racePacific(text(vo.isRACE_PACIFIC()))
					.raceUnknown(text(vo.isRACE_UNKNOWN()))
					.raceOther(text(vo.isRACE_OTHER()))
					.raceOtherText(text(vo.getRACE_OTHER_TEXT()))
					.birthCountry(text(vo.getBIRTH_COUNTRY()))
					.risk01(text(vo.getSwm()))
					.risk02(text(vo.getSwf()))
					.risk03(text(vo.getIdu()))
					.risk04(text(vo.getSexIDU()))
					.risk05(text(vo.getSexAIDSHIV()))
					.risk06(text(vo.getRcvdTransfusion()))
					.risk07(text(vo.getPerinatalExposure()))
					.risk08(text(vo.getRiskOther()))
					.risk09(text(vo.getRiskUnknown()))
					.risk10(text(vo.getSexBI()))
					.riskOtherText(text(vo.getRISK_OTHER_TEXT()))
					.recvBloodProdDate(text(vo.getRECV_BLOOD_PROD_DATE()))
					.recvBloodProdCountry(text(vo.getRECV_BLOOD_PROD_COUNTRY()))
					.admissionDate(text(vo.getADMISSION_DATE()))
					.initHivDiag(decimal(vo.isINIT_HIV_DIAG()))
					.initHivDiagDateText(text(vo.getINIT_HIV_DIAG_DATE_TEXT()))
					.informedHivPos(text(vo.getINFORMED_HIV_POS()))
					.informedHivDateText(text(vo.getINFORMED_HIV_DATE_TEXT()))
					.prevHivDiag(decimal(vo.isPREV_HIV_DIAG()))
					.prevHivDiagDateText(text(vo.getPREV_HIV_DIAG_DATE_TEXT()))
					.initAidsDiag(decimal(vo.isINIT_AIDS_DIAG()))
					.initAidsDiagDateText(text(vo.getINIT_AIDS_DIAG_DATE_TEXT()))
					.prevAidsDiag(decimal(vo.isPREV_AIDS_DIAG()))
					.prevAidsDiagDateText(text(vo.getPREV_AIDS_DIAG_DATE_TEXT()))
					.psRequested(text(vo.isPS_REQUESTED()))
					.completerLname(text(vo.getCOMPLETER_LNAME()))
					.completerMname(text(vo.getCOMPLETER_MNAME()))
					.completerFname(text(vo.getCOMPLETER_FNAME()))
					.completerTitle(text(vo.getCOMPLETER_TITLE()))
					.completerPhone(text(vo.getCOMPLETER_PHONE()))
					.completerPhoneExt(text(vo.getCOMPLETER_PHONE_EXT()))
					.completerEmail(text(vo.getCOMPLETER_EMAIL()))
					.facilityName(text(vo.getFACILITY_NAME()))
					.facilityDept(text(vo.getFACILITY_DEPT()))
					.facilityAddress(text(vo.getFACILITY_ADDRESS()))
					.facilityCity(text(vo.getFACILITY_CITY()))
					.facilityState(text(vo.getFACILITY_STATE()))
					.facilityZip(text(vo.getFACILITY_ZIP()))
					.facilityPlus4(text(vo.getFACILITY_PLUS4()))
					.facilityCounty(text(vo.getFACILITY_COUNTY()))
					.completedDate(dateOnly(vo.getCOMPLETED_DATE()))
					.contactLname(text(vo.getCONTACT_LNAME()))
					.contactFname(text(vo.getCONTACT_FNAME()))
					.contactMname(text(vo.getCONTACT_MNAME()))
					.contactTitle(text(vo.getCONTACT_TITLE()))
					.contactPhone(text(vo.getCONTACT_PHONE()))
					.contactPhoneExt(text(vo.getCONTACT_PHONE_EXT()))
					.provLname(text(vo.getPROV_LNAME()))
					.provFname(text(vo.getPROV_FNAME()))
					.provMi(text(vo.getPROV_MI()))
					.provSigned(text(vo.isPROV_SIGNED()))
					.provSignedDate(dateOnly(vo.getPROV_SIGNED_DATE()))
					.createUid(text(vo.getCREATE_UID()))
					.createDate(dateOnly(vo.getCREATE_DATE()))
					.entryReviewUid(text(vo.getENTRY_REVIEW_UID()))
					.entryReviewDate(now)
					.comments(text(vo.getCOMMENTS()))
					.ptInterviewDate(text(vo.getPT_INTERVIEW_DATE()))
					.firstPosHivDate(text(vo.getFIRST_POS_HIV_DATE()))
					.firstPosTestAnon(text(vo.getFIRST_POS_TEST_ANON()))
					.firstPosFacType(text(vo.getFIRST_POS_FAC_TYPE()))
					.firstPosFacName(text(vo.getFIRST_POS_FAC_NAME()))
					.firstPosFacState(text(vo.getFIRST_POS_FAC_STATE()))
					.rsonFirstPosExp(text(vo.getRSON_FIRST_POS_EXP()))
					.rsonFirstPosRoutn(text(vo.getRSON_FIRST_POS_ROUTN()))
					.rsonFirstPosChkneg(text(vo.getRSON_FIRST_POS_CHKNEG()))
					.rsonFirstPosReqd(text(vo.getRSON_FIRST_POS_REQD()))
					.rsonFirstPosPrtnr(text(vo.getRSON_FIRST_POS_PRTNR()))
					.rsonFirstPosOther(text(vo.getRSON_FIRST_POS_OTHER()))
					.rsonFirstPosOtherText(text(vo.getRSON_FIRST_POS_OTHER_TEXT()))
					.ptArv6Mo(text(vo.getPT_ARV_6_MO()))
					.ptArvName(text(vo.getPT_ARV_NAME()))
					.ptArvStartDate(text(vo.getPT_ARV_START_DATE()))
					.ptArvEndDate(text(vo.getPT_ARV_END_DATE()))
					.ptArvTimeOfTest(text(vo.getPT_ARV_TIME_OF_TEST()))
					.firstHivTestEverDate(text(vo.getFIRST_HIV_TEST_EVER_DATE()))
					.countHivTest2yrs(text(vo.getCOUNT_HIV_TEST_2YRS()))
					.respHivTest2yrs(text(vo.getRESP_HIV_TEST_2YRS()))
					.everHadNeg(text(vo.getEVER_HAD_NEG()))
					.lastNegDate(text(vo.getLAST_NEG_DATE()))
					.lastNegFacType(text(vo.getLAST_NEG_FAC_TYPE()))
					.lastNegFacName(text(vo.getLAST_NEG_FAC_NAME()))
					.lastNegFacState(text(vo.getLAST_NEG_FAC_STATE()))
					.eharsDocumentSourceCd(text(vo.getEHARS_DOCUMENT_SOURCE_CD()))
					.currentProcessStatus(1)
					.partners(decimal(vo.getPartnersCount()))
					.personAddressType(text(vo.getPERSON_ADDRESS_TYPE()))
					.sexAtBirth(text(vo.getSEX_AT_BIRTH()))
					.pregnant(text(vo.getPREGNANT()))
					.edc(text(vo.getEDC()))
					.personOtherIdentifier(text(vo.getPERSON_OTHER_IDENTIFIER()))
					.primaryLanguage(text(vo.getPRIMARY_LANGUAGE()))
					.primaryLanguageOther(text(vo.getPRIMARY_LANGUAGE_OTHER()))
					.acuteInfection(text(vo.getACUTE_INFECTION()))
					.referredHivProvider(text(vo.getREFERRED_HIV_PROVIDER()))
					.referredProviderName(text(vo.getREFERRED_PROVIDER_NAME()))
					.referredProviderTelephone(text(vo.getREFERRED_PROVIDER_TELEPHONE()))
					.everHadPrevPosTest(text(vo.getEVER_HAD_PREV_POS_TEST()))
					.ptArvEverTaken(text(vo.getPT_ARV_EVER_TAKEN()))
					.arvPrevenPrep(text(vo.getARV_PREVEN_PREP()))
					.arvPrevenPrepStartDt(text(vo.getARV_PREVEN_PREP_START_DT()))
					.arvPrevenPrepEndDt(text(vo.getARV_PREVEN_PREP_END_DT()))
					.arvPrevenprepMedNames(text(vo.getARV_PREVENPREP_MED_NAMES()))
					.postExpArvPep(text(vo.getPOST_EXP_ARV_PEP()))
					.postExpArvPepStartDt(text(vo.getPOST_EXP_ARV_PEP_START_DT()))
					.postExpArvPepEndDt(text(vo.getPOST_EXP_ARV_PEP_END_DT()))
					.postExpArvPepMedNames(text(vo.getPOST_EXP_ARV_PEP_MED_NAMES()))
					.arvTreatment(text(vo.getARV_TREATMENT()))
					.rsonClinicianOfferedRec(text(vo.getRSON_CLINICIAN_OFFERED_REC()))
					.facilityType(text(vo.getFACILITY_TYPE()))
					.facilityTypeOtherText(text(vo.getFACILITY_TYPE_OTHER_TEXT()))
					.providerNysLicNo(text(vo.getPROVIDER_NYS_LIC_NO()))
					.providerNpiNo(text(vo.getPROVIDER_NPI_NO()))
					.additionalGenderIdentity(text(vo.getADDITIONAL_GENDER_IDENTITY()))
					.nysid(text(vo.getNYSID()))
					.provLicenseProfCd(text(vo.getPROV_LICENSE_PROF_CD()))
					.addressMappingId(text(vo.getADDRESS_MAPPING_ID()))
					.sexualOrientation(text(vo.getSEXUAL_ORIENTATION()))
					.addSexualOrienationIdentity(text(vo.getADD_SEXUAL_ORIENATION_IDENTITY()))
					.build());
		return 1;
	}

	/** The forms waiting for review: FNAME, LNAME, documentId (a BigDecimal, as the NUMBER column was read), documentType 'PRF'. */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getprfReviewDocumentsData() {
		List<JSONObject> rows = new ArrayList<>();
		for (Object[] row : rawDocs.findForReview()) {
			JSONObject json = new JSONObject();
			if (row[0] != null) {
				json.put("FNAME", row[0]);
			}
			if (row[1] != null) {
				json.put("LNAME", row[1]);
			}
			if (row[2] != null) {
				json.put("documentId", new BigDecimal(row[2].toString()));
			}
			json.put("documentType", "PRF");
			rows.add(json);
		}
		return rows;
	}

	/** A form as received, for the review screen (null if there is none). */
	public PRFDocumentVO getCompleteRawPRFDocumentContent(int docId) {
		return jpql.one(RAW_PRF_DOC, Rows.bean(PRFDocumentVO.class), docId);
	}

	/**
	 * Sets the status of a form and the reviewer.
	 *
	 * @param inputParameterMap docId, current_process_Status, userId
	 * @return the number of rows changed
	 */
	@Transactional
	public int updatePrfDocStatusInRawTable(Map<Object, Object> inputParameterMap) {
		return rawDocs.changeStatus(integer(inputParameterMap.get("current_process_Status")), text(inputParameterMap.get("userId")),
				integer(inputParameterMap.get("docId")));
	}

	/**
	 * Inserts the standardized form of the import (status 5), from the values the import prepared.
	 *
	 * @param docVO the form's values by name (not changed)
	 * @return 1
	 */
	@Transactional
	public int writeRawPRFRecordDataToStdTable(Map<Object, Object> docVO) {
		Map<Object, Object> m = docVO;
		stdDocs.saveAndFlush(
				StdPrfDoc.builder()
					.documentId(integer(m.get("DOCUMENT_ID")))
					.prfSrcId(longValue(m.get("PRF_SRC_ID")))
					.fname(upperTrim(m.get("FNAME")))
					.mname(upperTrim(m.get("MNAME")))
					.lname(upperTrim(m.get("LNAME")))
					.suffix(upperTrim(m.get("SUFFIX")))
					.altFname(upperTrim(m.get("ALT_FNAME")))
					.altMname(upperTrim(m.get("ALT_MNAME")))
					.altLname(upperTrim(m.get("ALT_LNAME")))
					.dobText(trim(m.get("DOB_TEXT")))
					.dodText(trim(m.get("DOD_TEXT")))
					.medRecordNumber(upperTrim(m.get("MED_RECORD_NUMBER")))
					.din(upperTrim(m.get("DIN")))
					.personAddress(upperTrim(m.get("PERSON_ADDRESS")))
					.personCity(upperTrim(m.get("PERSON_CITY")))
					.personState(upperTrim(m.get("PERSON_STATE")))
					.personZip(trim(m.get("PERSON_ZIP")))
					.personPlus4(upperTrim(m.get("PERSON_PLUS4")))
					.personCounty(upperTrim(m.get("PERSON_COUNTY")))
					.personPhone(upperTrim(m.get("PERSON_TELEPHONE")))
					.gender(upperTrim(m.get("GENDER")))
					.ethnicity(text(m.get("ETHNICITY")))
					.raceWhite(text(m.get("RACE_WHITE")))
					.raceBlack(text(m.get("RACE_BLACK")))
					.raceAsian(text(m.get("RACE_ASIAN")))
					.raceIndian(text(m.get("RACE_INDIAN")))
					.racePacific(text(m.get("RACE_PACIFIC")))
					.raceUnknown(text(m.get("RACE_UNKNOWN")))
					.raceOther(text(m.get("RACE_OTHER")))
					.raceOtherText(upperTrim(m.get("RACE_OTHER_TEXT")))
					.birthCountry(upperTrim(m.get("BIRTH_COUNTRY")))
					.swm(text(m.get("RISK01")))
					.swf(text(m.get("RISK02")))
					.idu(text(m.get("RISK03")))
					.sexPartnerIdu(text(m.get("RISK04")))
					.sexPartnerHiv(text(m.get("RISK05")))
					.recvBloodProd(text(m.get("RISK06")))
					.perinatal(text(m.get("RISK07")))
					.riskOther(text(m.get("RISK_OTHER")))
					.riskUnknown(text(m.get("RISK09")))
					.sexPartnerMsm(text(m.get("RISK10")))
					.riskOtherText(upperTrim(m.get("RISK_OTHER_TEXT")))
					.recvBloodProdDateText(trim(m.get("RECV_BLOOD_PROD_DATE")))
					.recvBloodProdCountry(upperTrim(m.get("RECV_BLOOD_PROD_COUNTRY")))
					.admissionDate(dateOnly(m.get("ADMISSION_DATE")))
					.initHivDiag(decimal(m.get("INIT_HIV_DIAG")))
					.initHivDiagDateText(trim(m.get("INIT_HIV_DIAG_DATE_TEXT")))
					.informedHivPos(text(m.get("INFORMED_HIV_POS")))
					.informedHivDateText(trim(m.get("INFORMED_HIV_DATE_TEXT")))
					.prevHivDiag(decimal(m.get("PREV_HIV_DIAG")))
					.prevHivDiagDateText(trim(m.get("PREV_HIV_DIAG_DATE_TEXT")))
					.initAidsDiag(decimal(m.get("INIT_AIDS_DIAG")))
					.initAidsDiagDateText(trim(m.get("INIT_AIDS_DIAG_DATE_TEXT")))
					.prevAidsDiag(decimal(m.get("PREV_AIDS_DIAG")))
					.prevAidsDiagDateText(trim(m.get("PREV_AIDS_DIAG_DATE_TEXT")))
					.psRequested(text(m.get("PS_REQUESTED")))
					.completerLname(upperTrim(m.get("COMPLETER_LNAME")))
					.completerMi(upperTrim(m.get("COMPLETER_MNAME")))
					.completerFname(upperTrim(m.get("COMPLETER_FNAME")))
					.completerTitle(upperTrim(m.get("COMPLETER_TITLE")))
					.completerPhone(upperTrim(m.get("COMPLETER_PHONE")))
					.completerPhoneExt(upperTrim(m.get("COMPLETER_PHONE_EXT")))
					.completerEmail(upperTrim(m.get("COMPLETER_EMAIL")))
					.facilityName(upperTrim(m.get("FACILITY_NAME")))
					.facilityDept(upperTrim(m.get("FACILITY_DEPT")))
					.facilityAddress(upperTrim(m.get("FACILITY_ADDRESS")))
					.facilityCity(upperTrim(m.get("FACILITY_CITY")))
					.facilityState(upperTrim(m.get("FACILITY_STATE")))
					.facilityZip(upperTrim(m.get("FACILITY_ZIP")))
					.facilityPlus4(upperTrim(m.get("FACILITY_PLUS4")))
					.facilityCounty(upperTrim(m.get("FACILITY_COUNTY")))
					.completedDate(dateOnly(m.get("COMPLETED_DATE")))
					.contactLname(upperTrim(m.get("CONTACT_LNAME")))
					.contactFname(upperTrim(m.get("CONTACT_FNAME")))
					.contactMi(upperTrim(m.get("CONTACT_MNAME")))
					.contactTitle(upperTrim(m.get("CONTACT_TITLE")))
					.contactPhone(upperTrim(m.get("CONTACT_PHONE")))
					.contactPhoneExt(upperTrim(m.get("CONTACT_PHONE_EXT")))
					.provLname(upperTrim(m.get("PROV_LNAME")))
					.provFname(upperTrim(m.get("PROV_FNAME")))
					.provMi(upperTrim(m.get("PROV_MI")))
					.provSigned(text(m.get("PROV_SIGNED")))
					.provSignedDate(dateOnly(m.get("PROV_SIGNED_DATE")))
					.createUid(upperTrim(m.get("CREATE_UID")))
					.createDate(dateOnly(m.get("CREATE_DATE")))
					.entryReviewUid(upperTrim(m.get("ENTRY_REVIEW_UID")))
					.entryReviewDate(dateOnly(m.get("ENTRY_REVIEW_DATE")))
					.comments(upperTrim(m.get("COMMENTS")))
					.ptInterviewDateText(trim(m.get("PT_INTERVIEW_DATE")))
					.firstPosHivDateText(trim(m.get("FIRST_POS_HIV_DATE")))
					.firstPosTestAnon(upperTrim(m.get("FIRST_POS_TEST_ANON")))
					.firstPosFacType(upperTrim(m.get("FIRST_POS_FAC_TYPE")))
					.firstPosFacName(upperTrim(m.get("FIRST_POS_FAC_NAME")))
					.firstPosFacState(upperTrim(m.get("FIRST_POS_FAC_STATE")))
					.rsonFirstPosExp(upperTrim(m.get("RSON_FIRST_POS_EXP")))
					.rsonFirstPosRoutn(upperTrim(m.get("RSON_FIRST_POS_ROUTN")))
					.rsonFirstPosChkneg(upperTrim(m.get("RSON_FIRST_POS_CHKNEG")))
					.rsonFirstPosReqd(upperTrim(m.get("RSON_FIRST_POS_REQD")))
					.rsonFirstPosPrtnr(upperTrim(m.get("RSON_FIRST_POS_PRTNR")))
					.rsonFirstPosOther(upperTrim(m.get("RSON_FIRST_POS_OTHER")))
					.rsonFirstPosOtherText(upperTrim(m.get("RSON_FIRST_POS_OTHER_TEXT")))
					.ptArv6Mo(upperTrim(m.get("PT_ARV_6_MO")))
					.ptArvName(upperTrim(m.get("PT_ARV_NAME")))
					.ptArvStartDateText(trim(m.get("PT_ARV_START_DATE")))
					.ptArvEndDateText(trim(m.get("PT_ARV_END_DATE")))
					.ptArvTimeOfTest(upperTrim(m.get("PT_ARV_TIME_OF_TEST")))
					.firstHivTestEverDateText(trim(m.get("FIRST_HIV_TEST_EVER_DATE")))
					.numberTest2Yrs(upperTrim(m.get("COUNT_HIV_TEST_2YRS")))
					.respHivTest2yrs(upperTrim(m.get("RESP_HIV_TEST_2YRS")))
					.everHadNeg(upperTrim(m.get("EVER_HAD_NEG")))
					.lastNegDateText(trim(m.get("LAST_NEG_DATE")))
					.lastNegFacType(upperTrim(m.get("LAST_NEG_FAC_TYPE")))
					.lastNegFacName(upperTrim(m.get("LAST_NEG_FAC_NAME")))
					.lastNegFacState(upperTrim(m.get("LAST_NEG_FAC_STATE")))
					.eharsDocumentSourceCd(upperTrim(m.get("EHARS_DOCUMENT_SOURCE_CD")))
					.personAddressType(upperTrim(m.get("PERSON_ADDRESS_TYPE")))
					.sexAtBirth(upperTrim(m.get("SEX_AT_BIRTH")))
					.pregnant(upperTrim(m.get("PREGNANT")))
					.edc(upperTrim(m.get("EDC")))
					.personOtherIdentifier(upperTrim(m.get("PERSON_OTHER_IDENTIFIER")))
					.primaryLanguage(upperTrim(m.get("PRIMARY_LANGUAGE")))
					.primaryLanguageOther(upperTrim(m.get("PRIMARY_LANGUAGE_OTHER")))
					.acuteInfection(upperTrim(m.get("ACUTE_INFECTION")))
					.referredHivProvider(upperTrim(m.get("REFERRED_HIV_PROVIDER")))
					.referredProviderName(upperTrim(m.get("REFERRED_PROVIDER_NAME")))
					.referredProviderTelephone(upperTrim(m.get("REFERRED_PROVIDER_TELEPHONE")))
					.everHadPrevPosTest(upperTrim(m.get("EVER_HAD_PREV_POS_TEST")))
					.ptArvEverTaken(upperTrim(m.get("PT_ARV_EVER_TAKEN")))
					.arvPrevenPrep(upperTrim(m.get("ARV_PREVEN_PREP")))
					.arvPrevenPrepStartDt(upperTrim(m.get("ARV_PREVEN_PREP_START_DT")))
					.arvPrevenPrepEndDt(upperTrim(m.get("ARV_PREVEN_PREP_END_DT")))
					.arvPrevenprepMedNames(upperTrim(m.get("ARV_PREVENPREP_MED_NAMES")))
					.postExpArvPep(upperTrim(m.get("POST_EXP_ARV_PEP")))
					.postExpArvPepStartDt(upperTrim(m.get("POST_EXP_ARV_PEP_START_DT")))
					.postExpArvPepEndDt(upperTrim(m.get("POST_EXP_ARV_PEP_END_DT")))
					.postExpArvPepMedNames(upperTrim(m.get("POST_EXP_ARV_PEP_MED_NAMES")))
					.arvTreatment(upperTrim(m.get("ARV_TREATMENT")))
					.rsonClinicianOfferedRec(upperTrim(m.get("RSON_CLINICIAN_OFFERED_REC")))
					.facilityType(upperTrim(m.get("FACILITY_TYPE")))
					.facilityTypeOtherText(upperTrim(m.get("FACILITY_TYPE_OTHER_TEXT")))
					.providerNysLicNo(upperTrim(m.get("PROVIDER_NYS_LIC_NO")))
					.providerNpiNo(upperTrim(m.get("PROVIDER_NPI_NO")))
					.additionalGenderIdentity(upperTrim(m.get("ADDITIONAL_GENDER_IDENTITY")))
					.nysid(upperTrim(m.get("NYSID")))
					.currentProcessStatus(5)
					.provLicenseProfCd(upperTrim(m.get("PROV_LICENSE_PROF_CD")))
					.addressMappingId(upperTrim(m.get("ADDRESS_MAPPING_ID")))
					.sexualOrientation(upperTrim(m.get("SEXUAL_ORIENTATION")))
					.addSexualOrienationIdentity(upperTrim(m.get("ADD_SEXUAL_ORIENATION_IDENTITY")))
					.build());
		return 1;
	}

	/**
	 * Standardizes the partners of a form (copies them from RAW_PRF_PARTNER).
	 *
	 * @return the number of rows inserted
	 */
	@Transactional
	public int writeRawPRFPartnerDataToStdTable(int documentId) {
		return stdPartners.insertFromRaw(documentId);
	}

	/** The number of forms waiting for review. */
	public int getPrfReviewDocsCount() {
		return (int) rawDocs.countForReview();
	}

	/**
	 * Inserts the raw row of a copied form.
	 *
	 * @param rawDocData the copy's values by column name (not changed)
	 * @return 1
	 */
	@Transactional
	public int insertRawPRFOOSDocument(Map<Object, Object> rawDocData) {
		Map<Object, Object> m = rawDocData;
		rawDocs.saveAndFlush(
				RawPrfDoc.builder()
					.nysid(upperTrim(m.get("NYSID")))
					.facilityName(upperTrim(m.get("FACILITY_NAME")))
					.provLname(upperTrim(m.get("PROV_LNAME")))
					.provFname(upperTrim(m.get("PROV_FNAME")))
					.referredProviderName(upperTrim(m.get("REFERRED_PROVIDER_NAME")))
					.rsonFirstPosOtherText(upperTrim(m.get("RSON_FIRST_POS_OTHER_TEXT")))
					.din(upperTrim(m.get("DIN")))
					.personAddressType(upperTrim(m.get("PERSON_ADDRESS_TYPE")))
					.personAddress(upperTrim(m.get("PERSON_ADDRESS")))
					.facilityAddress(upperTrim(m.get("FACILITY_ADDRESS")))
					.ptArvName(upperTrim(m.get("PT_ARV_NAME")))
					.arvPrevenprepMedNames(upperTrim(m.get("ARV_PREVENPREP_MED_NAMES")))
					.postExpArvPepMedNames(upperTrim(m.get("POST_EXP_ARV_PEP_MED_NAMES")))
					.completerEmail(upperTrim(m.get("COMPLETER_EMAIL")))
					.personOtherIdentifier(upperTrim(m.get("PERSON_OTHER_IDENTIFIER")))
					.primaryLanguageOther(upperTrim(m.get("PRIMARY_LANGUAGE_OTHER")))
					.additionalGenderIdentity(upperTrim(m.get("ADDITIONAL_GENDER_IDENTITY")))
					.suffix(upperTrim(m.get("SUFFIX")))
					.personZip(upperTrim(m.get("PERSON_ZIP")))
					.completerPhoneExt(upperTrim(m.get("COMPLETER_PHONE_EXT")))
					.facilityZip(upperTrim(m.get("FACILITY_ZIP")))
					.contactPhoneExt(upperTrim(m.get("CONTACT_PHONE_EXT")))
					.countHivTest2yrs(upperTrim(m.get("COUNT_HIV_TEST_2YRS")))
					.birthCountry(upperTrim(m.get("BIRTH_COUNTRY")))
					.recvBloodProdCountry(upperTrim(m.get("RECV_BLOOD_PROD_COUNTRY")))
					.personPlus4(upperTrim(m.get("PERSON_PLUS4")))
					.facilityPlus4(upperTrim(m.get("FACILITY_PLUS4")))
					.personCity(upperTrim(m.get("PERSON_CITY")))
					.raceOtherText(upperTrim(m.get("RACE_OTHER_TEXT")))
					.riskOtherText(upperTrim(m.get("RISK_OTHER_TEXT")))
					.completerTitle(upperTrim(m.get("COMPLETER_TITLE")))
					.facilityCity(upperTrim(m.get("FACILITY_CITY")))
					.contactTitle(upperTrim(m.get("CONTACT_TITLE")))
					.personCounty(upperTrim(m.get("PERSON_COUNTY")))
					.facilityCounty(upperTrim(m.get("FACILITY_COUNTY")))
					.firstPosTestAnon(upperTrim(m.get("FIRST_POS_TEST_ANON")))
					.respHivTest2yrs(upperTrim(m.get("RESP_HIV_TEST_2YRS")))
					.mname(upperTrim(m.get("MNAME")))
					.lname(upperTrim(m.get("LNAME")))
					.altMname(upperTrim(m.get("ALT_MNAME")))
					.altLname(upperTrim(m.get("ALT_LNAME")))
					.completerLname(upperTrim(m.get("COMPLETER_LNAME")))
					.completerMname(upperTrim(m.get("COMPLETER_MNAME")))
					.contactLname(upperTrim(m.get("CONTACT_LNAME")))
					.contactMname(upperTrim(m.get("CONTACT_MNAME")))
					.contactPhone(upperTrim(m.get("CONTACT_PHONE")))
					.provMi(upperTrim(m.get("PROV_MI")))
					.fname(upperTrim(m.get("FNAME")))
					.altFname(upperTrim(m.get("ALT_FNAME")))
					.personTelephone(upperTrim(m.get("PERSON_PHONE")))
					.completerFname(upperTrim(m.get("COMPLETER_FNAME")))
					.completerPhone(upperTrim(m.get("COMPLETER_PHONE")))
					.contactFname(upperTrim(m.get("CONTACT_FNAME")))
					.eharsDocumentSourceCd(upperTrim(m.get("EHARS_DOCUMENT_SOURCE_CD")))
					.provFacTypeCode(upperTrim(m.get("PROV_FAC_TYPE_CODE")))
					.edc(upperTrim(m.get("EDC")))
					.referredProviderTelephone(upperTrim(m.get("REFERRED_PROVIDER_TELEPHONE")))
					.arvPrevenPrepStartDt(upperTrim(m.get("ARV_PREVEN_PREP_START_DT")))
					.arvPrevenPrepEndDt(upperTrim(m.get("ARV_PREVEN_PREP_END_DT")))
					.postExpArvPepStartDt(upperTrim(m.get("POST_EXP_ARV_PEP_START_DT")))
					.postExpArvPepEndDt(upperTrim(m.get("POST_EXP_ARV_PEP_END_DT")))
					.providerNysLicNo(upperTrim(m.get("PROVIDER_NYS_LIC_NO")))
					.providerNpiNo(upperTrim(m.get("PROVIDER_NPI_NO")))
					.admissionDate(upperTrim(m.get("ADMISSION_DATE")))
					.personState(upperTrim(m.get("PERSON_STATE")))
					.ethnicity(upperTrim(m.get("ETHNICITY")))
					.facilityState(upperTrim(m.get("FACILITY_STATE")))
					.ptArv6Mo(upperTrim(m.get("PT_ARV_6_MO")))
					.ptArvTimeOfTest(upperTrim(m.get("PT_ARV_TIME_OF_TEST")))
					.everHadNeg(upperTrim(m.get("EVER_HAD_NEG")))
					.pregnant(upperTrim(m.get("PREGNANT")))
					.acuteInfection(upperTrim(m.get("ACUTE_INFECTION")))
					.referredHivProvider(upperTrim(m.get("REFERRED_HIV_PROVIDER")))
					.everHadPrevPosTest(upperTrim(m.get("EVER_HAD_PREV_POS_TEST")))
					.ptArvEverTaken(upperTrim(m.get("PT_ARV_EVER_TAKEN")))
					.arvPrevenPrep(upperTrim(m.get("ARV_PREVEN_PREP")))
					.postExpArvPep(upperTrim(m.get("POST_EXP_ARV_PEP")))
					.arvTreatment(upperTrim(m.get("ARV_TREATMENT")))
					.facilityType(upperTrim(m.get("FACILITY_TYPE")))
					.medRecordNumber(upperTrim(m.get("MED_RECORD_NUMBER")))
					.ptInterviewDate(upperTrim(m.get("PT_INTERVIEW_DATE")))
					.firstPosHivDate(upperTrim(m.get("FIRST_POS_HIV_DATE")))
					.ptArvStartDate(upperTrim(m.get("PT_ARV_START_DATE")))
					.ptArvEndDate(upperTrim(m.get("PT_ARV_END_DATE")))
					.firstHivTestEverDate(upperTrim(m.get("FIRST_HIV_TEST_EVER_DATE")))
					.lastNegDate(upperTrim(m.get("LAST_NEG_DATE")))
					.facilityDept(upperTrim(m.get("FACILITY_DEPT")))
					.firstPosFacName(upperTrim(m.get("FIRST_POS_FAC_NAME")))
					.lastNegFacName(upperTrim(m.get("LAST_NEG_FAC_NAME")))
					.facilityTypeOtherText(upperTrim(m.get("FACILITY_TYPE_OTHER_TEXT")))
					.dobText(upperTrim(m.get("DOB_TEXT")))
					.dodText(upperTrim(m.get("DOD_TEXT")))
					.recvBloodProdDate(upperTrim(m.get("RECV_BLOOD_PROD_DATE")))
					.initHivDiagDateText(upperTrim(m.get("INIT_HIV_DIAG_DATE_TEXT")))
					.informedHivDateText(upperTrim(m.get("INFORMED_HIV_DATE_TEXT")))
					.prevHivDiagDateText(upperTrim(m.get("PREV_HIV_DIAG_DATE_TEXT")))
					.initAidsDiagDateText(upperTrim(m.get("INIT_AIDS_DIAG_DATE_TEXT")))
					.prevAidsDiagDateText(upperTrim(m.get("PREV_AIDS_DIAG_DATE_TEXT")))
					.createUid(upperTrim(m.get("CREATE_UID")))
					.entryReviewUid(upperTrim(m.get("ENTRY_REVIEW_UID")))
					.firstPosFacType(upperTrim(m.get("FIRST_POS_FAC_TYPE")))
					.firstPosFacState(upperTrim(m.get("FIRST_POS_FAC_STATE")))
					.lastNegFacType(upperTrim(m.get("LAST_NEG_FAC_TYPE")))
					.lastNegFacState(upperTrim(m.get("LAST_NEG_FAC_STATE")))
					.gender(upperTrim(m.get("GENDER")))
					.raceWhite(upperTrim(m.get("RACE_WHITE")))
					.raceBlack(upperTrim(m.get("RACE_BLACK")))
					.raceAsian(upperTrim(m.get("RACE_ASIAN")))
					.raceIndian(upperTrim(m.get("RACE_INDIAN")))
					.racePacific(upperTrim(m.get("RACE_PACIFIC")))
					.raceUnknown(upperTrim(m.get("RACE_UNKNOWN")))
					.raceOther(upperTrim(m.get("RACE_OTHER")))
					.risk01(upperTrim(m.get("RISK01")))
					.risk02(upperTrim(m.get("RISK02")))
					.risk03(upperTrim(m.get("RISK03")))
					.risk04(upperTrim(m.get("RISK04")))
					.risk05(upperTrim(m.get("RISK05")))
					.risk06(upperTrim(m.get("RISK06")))
					.risk07(upperTrim(m.get("RISK07")))
					.risk08(upperTrim(m.get("RISK08")))
					.risk09(upperTrim(m.get("RISK09")))
					.risk10(upperTrim(m.get("RISK10")))
					.informedHivPos(upperTrim(m.get("INFORMED_HIV_POS")))
					.psRequested(upperTrim(m.get("PS_REQUESTED")))
					.provSigned(upperTrim(m.get("PROV_SIGNED")))
					.rsonFirstPosExp(upperTrim(m.get("RSON_FIRST_POS_EXP")))
					.rsonFirstPosRoutn(upperTrim(m.get("RSON_FIRST_POS_ROUTN")))
					.rsonFirstPosChkneg(upperTrim(m.get("RSON_FIRST_POS_CHKNEG")))
					.rsonFirstPosReqd(upperTrim(m.get("RSON_FIRST_POS_REQD")))
					.rsonFirstPosPrtnr(upperTrim(m.get("RSON_FIRST_POS_PRTNR")))
					.rsonFirstPosOther(upperTrim(m.get("RSON_FIRST_POS_OTHER")))
					.partners(upperTrimDecimal(m.get("PARTNERS")))
					.sexAtBirth(upperTrim(m.get("SEX_AT_BIRTH")))
					.primaryLanguage(upperTrim(m.get("PRIMARY_LANGUAGE")))
					.rsonClinicianOfferedRec(upperTrim(m.get("RSON_CLINICIAN_OFFERED_REC")))
					.documentId(integer(m.get("DOCUMENT_ID")))
					.prfSrcId(longValue(m.get("PRF_SRC_ID")))
					.currentProcessStatus(integer(m.get("CURRENT_PROCESS_STATUS")))
					.initHivDiag(decimal(m.get("INIT_HIV_DIAG")))
					.prevHivDiag(decimal(m.get("PREV_HIV_DIAG")))
					.initAidsDiag(decimal(m.get("INIT_AIDS_DIAG")))
					.prevAidsDiag(decimal(m.get("PREV_AIDS_DIAG")))
					.completedDate(dateOnly(m.get("COMPLETED_DATE")))
					.provSignedDate(dateOnly(m.get("PROV_SIGNED_DATE")))
					.createDate(dateOnly(m.get("CREATE_DATE")))
					.entryReviewDate(dateOnly(m.get("ENTRY_REVIEW_DATE")))
					.processDate(dateOnly(m.get("PROCESS_DATE")))
					.comments(upperTrim(m.get("COMMENTS")))
					.provLicenseProfCd(upperTrim(m.get("PROV_LICENSE_PROF_CD")))
					.addressMappingId(upperTrim(m.get("ADDRESS_MAPPING_ID")))
					.sexualOrientation(upperTrim(m.get("SEXUAL_ORIENTATION")))
					.addSexualOrienationIdentity(upperTrim(m.get("ADD_SEXUAL_ORIENATION_IDENTITY")))
					.build());
		return 1;
	}

	/**
	 * Inserts the standardized row of a copied form.
	 *
	 * @param stdDocData the copy's values by column name (not changed)
	 * @return 1
	 */
	@Transactional
	public int insertStdPRFOOSDocument(Map<Object, Object> stdDocData) {
		Map<Object, Object> m = stdDocData;
		stdDocs.saveAndFlush(
				StdPrfDoc.builder()
					.nysid(upperTrim(m.get("NYSID")))
					.facilityName(upperTrim(m.get("FACILITY_NAME")))
					.provLname(upperTrim(m.get("PROV_LNAME")))
					.provFname(upperTrim(m.get("PROV_FNAME")))
					.referredProviderName(upperTrim(m.get("REFERRED_PROVIDER_NAME")))
					.rsonFirstPosOtherText(upperTrim(m.get("RSON_FIRST_POS_OTHER_TEXT")))
					.din(upperTrim(m.get("DIN")))
					.personAddressType(upperTrim(m.get("PERSON_ADDRESS_TYPE")))
					.personAddress(upperTrim(m.get("PERSON_ADDRESS")))
					.facilityAddress(upperTrim(m.get("FACILITY_ADDRESS")))
					.personCensusTract(upperTrim(m.get("PERSON_CENSUS_TRACT")))
					.facilityCensusTract(upperTrim(m.get("FACILITY_CENSUS_TRACT")))
					.ptArvName(upperTrim(m.get("PT_ARV_NAME")))
					.arvPrevenprepMedNames(upperTrim(m.get("ARV_PREVENPREP_MED_NAMES")))
					.postExpArvPepMedNames(upperTrim(m.get("POST_EXP_ARV_PEP_MED_NAMES")))
					.completerEmail(upperTrim(m.get("COMPLETER_EMAIL")))
					.personOtherIdentifier(upperTrim(m.get("PERSON_OTHER_IDENTIFIER")))
					.primaryLanguageOther(upperTrim(m.get("PRIMARY_LANGUAGE_OTHER")))
					.additionalGenderIdentity(upperTrim(m.get("ADDITIONAL_GENDER_IDENTITY")))
					.suffix(upperTrim(m.get("SUFFIX")))
					.personZip(upperTrim(m.get("PERSON_ZIP")))
					.completerPhoneExt(upperTrim(m.get("COMPLETER_PHONE_EXT")))
					.facilityZip(upperTrim(m.get("FACILITY_ZIP")))
					.contactPhoneExt(upperTrim(m.get("CONTACT_PHONE_EXT")))
					.numberTest2Yrs(upperTrim(m.get("NUMBER_TEST_2_YRS")))
					.birthCountry(upperTrim(m.get("BIRTH_COUNTRY")))
					.recvBloodProdCountry(upperTrim(m.get("RECV_BLOOD_PROD_COUNTRY")))
					.personPlus4(upperTrim(m.get("PERSON_PLUS4")))
					.facilityPlus4(upperTrim(m.get("FACILITY_PLUS4")))
					.personCity(upperTrim(m.get("PERSON_CITY")))
					.raceOtherText(upperTrim(m.get("RACE_OTHER_TEXT")))
					.riskOtherText(upperTrim(m.get("RISK_OTHER_TEXT")))
					.completerTitle(upperTrim(m.get("COMPLETER_TITLE")))
					.facilityCity(upperTrim(m.get("FACILITY_CITY")))
					.contactTitle(upperTrim(m.get("CONTACT_TITLE")))
					.personCounty(upperTrim(m.get("PERSON_COUNTY")))
					.facilityCounty(upperTrim(m.get("FACILITY_COUNTY")))
					.matchCd(upperTrim(m.get("MATCH_CD")))
					.firstPosTestAnon(upperTrim(m.get("FIRST_POS_TEST_ANON")))
					.respHivTest2yrs(upperTrim(m.get("RESP_HIV_TEST_2YRS")))
					.mname(upperTrim(m.get("MNAME")))
					.lname(upperTrim(m.get("LNAME")))
					.altMname(upperTrim(m.get("ALT_MNAME")))
					.altLname(upperTrim(m.get("ALT_LNAME")))
					.completerLname(upperTrim(m.get("COMPLETER_LNAME")))
					.completerMi(upperTrim(m.get("COMPLETER_MI")))
					.contactLname(upperTrim(m.get("CONTACT_LNAME")))
					.contactPhone(upperTrim(m.get("CONTACT_PHONE")))
					.provMi(upperTrim(m.get("PROV_MI")))
					.fname(upperTrim(m.get("FNAME")))
					.altFname(upperTrim(m.get("ALT_FNAME")))
					.personPhone(upperTrim(m.get("PERSON_PHONE")))
					.completerFname(upperTrim(m.get("COMPLETER_FNAME")))
					.completerPhone(upperTrim(m.get("COMPLETER_PHONE")))
					.contactFname(upperTrim(m.get("CONTACT_FNAME")))
					.eharsDocumentSourceCd(upperTrim(m.get("EHARS_DOCUMENT_SOURCE_CD")))
					.eharsPrfUid(upperTrim(m.get("EHARS_PRF_UID")))
					.admissionDate(upperTrimDate(m.get("ADMISSION_DATE")))
					.edc(upperTrim(m.get("EDC")))
					.referredProviderTelephone(upperTrim(m.get("REFERRED_PROVIDER_TELEPHONE")))
					.arvPrevenPrepStartDt(upperTrim(m.get("ARV_PREVEN_PREP_START_DT")))
					.arvPrevenPrepEndDt(upperTrim(m.get("ARV_PREVEN_PREP_END_DT")))
					.postExpArvPepStartDt(upperTrim(m.get("POST_EXP_ARV_PEP_START_DT")))
					.postExpArvPepEndDt(upperTrim(m.get("POST_EXP_ARV_PEP_END_DT")))
					.providerNysLicNo(upperTrim(m.get("PROVIDER_NYS_LIC_NO")))
					.providerNpiNo(upperTrim(m.get("PROVIDER_NPI_NO")))
					.personState(upperTrim(m.get("PERSON_STATE")))
					.ethnicity(upperTrim(m.get("ETHNICITY")))
					.facilityState(upperTrim(m.get("FACILITY_STATE")))
					.matchPass(upperTrim(m.get("MATCH_PASS")))
					.ptArv6Mo(upperTrim(m.get("PT_ARV_6_MO")))
					.ptArvTimeOfTest(upperTrim(m.get("PT_ARV_TIME_OF_TEST")))
					.pregnant(upperTrim(m.get("PREGNANT")))
					.acuteInfection(upperTrim(m.get("ACUTE_INFECTION")))
					.referredHivProvider(upperTrim(m.get("REFERRED_HIV_PROVIDER")))
					.everHadPrevPosTest(upperTrim(m.get("EVER_HAD_PREV_POS_TEST")))
					.ptArvEverTaken(upperTrim(m.get("PT_ARV_EVER_TAKEN")))
					.arvPrevenPrep(upperTrim(m.get("ARV_PREVEN_PREP")))
					.postExpArvPep(upperTrim(m.get("POST_EXP_ARV_PEP")))
					.arvTreatment(upperTrim(m.get("ARV_TREATMENT")))
					.facilityType(upperTrim(m.get("FACILITY_TYPE")))
					.medRecordNumber(upperTrim(m.get("MED_RECORD_NUMBER")))
					.personLongitude(upperTrim(m.get("PERSON_LONGITUDE")))
					.facilityLongitude(upperTrim(m.get("FACILITY_LONGITUDE")))
					.facilityDept(upperTrim(m.get("FACILITY_DEPT")))
					.firstPosFacName(upperTrim(m.get("FIRST_POS_FAC_NAME")))
					.lastNegFacName(upperTrim(m.get("LAST_NEG_FAC_NAME")))
					.facilityTypeOtherText(upperTrim(m.get("FACILITY_TYPE_OTHER_TEXT")))
					.dobText(upperTrim(m.get("DOB_TEXT")))
					.dodText(upperTrim(m.get("DOD_TEXT")))
					.personLatitude(upperTrim(m.get("PERSON_LATITUDE")))
					.recvBloodProdDateText(upperTrim(m.get("RECV_BLOOD_PROD_DATE_TEXT")))
					.initHivDiagDateText(upperTrim(m.get("INIT_HIV_DIAG_DATE_TEXT")))
					.informedHivDateText(upperTrim(m.get("INFORMED_HIV_DATE_TEXT")))
					.prevHivDiagDateText(upperTrim(m.get("PREV_HIV_DIAG_DATE_TEXT")))
					.initAidsDiagDateText(upperTrim(m.get("INIT_AIDS_DIAG_DATE_TEXT")))
					.prevAidsDiagDateText(upperTrim(m.get("PREV_AIDS_DIAG_DATE_TEXT")))
					.facilityLatitude(upperTrim(m.get("FACILITY_LATITUDE")))
					.createUid(upperTrim(m.get("CREATE_UID")))
					.entryReviewUid(upperTrim(m.get("ENTRY_REVIEW_UID")))
					.matchedStateno(upperTrim(m.get("MATCHED_STATENO")))
					.reviewUid(upperTrim(m.get("REVIEW_UID")))
					.ptInterviewDateText(upperTrim(m.get("PT_INTERVIEW_DATE_TEXT")))
					.firstPosHivDateText(upperTrim(m.get("FIRST_POS_HIV_DATE_TEXT")))
					.firstPosFacType(upperTrim(m.get("FIRST_POS_FAC_TYPE")))
					.firstPosFacState(upperTrim(m.get("FIRST_POS_FAC_STATE")))
					.ptArvStartDateText(upperTrim(m.get("PT_ARV_START_DATE_TEXT")))
					.ptArvEndDateText(upperTrim(m.get("PT_ARV_END_DATE_TEXT")))
					.firstHivTestEverDateText(upperTrim(m.get("FIRST_HIV_TEST_EVER_DATE_TEXT")))
					.lastNegDateText(upperTrim(m.get("LAST_NEG_DATE_TEXT")))
					.lastNegFacType(upperTrim(m.get("LAST_NEG_FAC_TYPE")))
					.lastNegFacState(upperTrim(m.get("LAST_NEG_FAC_STATE")))
					.gender(upperTrim(m.get("GENDER")))
					.raceWhite(upperTrim(m.get("RACE_WHITE")))
					.raceBlack(upperTrim(m.get("RACE_BLACK")))
					.raceAsian(upperTrim(m.get("RACE_ASIAN")))
					.raceIndian(upperTrim(m.get("RACE_INDIAN")))
					.racePacific(upperTrim(m.get("RACE_PACIFIC")))
					.raceUnknown(upperTrim(m.get("RACE_UNKNOWN")))
					.raceOther(upperTrim(m.get("RACE_OTHER")))
					.swm(upperTrim(m.get("SWM")))
					.swf(upperTrim(m.get("SWF")))
					.idu(upperTrim(m.get("IDU")))
					.sexPartnerIdu(upperTrim(m.get("SEX_PARTNER_IDU")))
					.sexPartnerHiv(upperTrim(m.get("SEX_PARTNER_HIV")))
					.recvBloodProd(upperTrim(m.get("RECV_BLOOD_PROD")))
					.perinatal(upperTrim(m.get("PERINATAL")))
					.riskOther(upperTrim(m.get("RISK_OTHER")))
					.riskUnknown(upperTrim(m.get("RISK_UNKNOWN")))
					.sexPartnerMsm(upperTrim(m.get("SEX_PARTNER_MSM")))
					.initHivDiag(upperTrimDecimal(m.get("INIT_HIV_DIAG")))
					.informedHivPos(upperTrim(m.get("INFORMED_HIV_POS")))
					.prevHivDiag(upperTrimDecimal(m.get("PREV_HIV_DIAG")))
					.initAidsDiag(upperTrimDecimal(m.get("INIT_AIDS_DIAG")))
					.prevAidsDiag(upperTrimDecimal(m.get("PREV_AIDS_DIAG")))
					.psRequested(upperTrim(m.get("PS_REQUESTED")))
					.contactMi(upperTrim(m.get("CONTACT_MI")))
					.provSigned(upperTrim(m.get("PROV_SIGNED")))
					.processed(upperTrim(m.get("PROCESSED")))
					.rsonFirstPosExp(upperTrim(m.get("RSON_FIRST_POS_EXP")))
					.rsonFirstPosRoutn(upperTrim(m.get("RSON_FIRST_POS_ROUTN")))
					.rsonFirstPosChkneg(upperTrim(m.get("RSON_FIRST_POS_CHKNEG")))
					.rsonFirstPosReqd(upperTrim(m.get("RSON_FIRST_POS_REQD")))
					.rsonFirstPosPrtnr(upperTrim(m.get("RSON_FIRST_POS_PRTNR")))
					.rsonFirstPosOther(upperTrim(m.get("RSON_FIRST_POS_OTHER")))
					.sexAtBirth(upperTrim(m.get("SEX_AT_BIRTH")))
					.primaryLanguage(upperTrim(m.get("PRIMARY_LANGUAGE")))
					.rsonClinicianOfferedRec(upperTrim(m.get("RSON_CLINICIAN_OFFERED_REC")))
					.raceMultiple(upperTrim(m.get("RACE_MULTIPLE")))
					.documentId(integer(m.get("DOCUMENT_ID")))
					.prfSrcId(longValue(m.get("PRF_SRC_ID")))
					.matchWeight(decimal(m.get("MATCH_WEIGHT")))
					.age(decimal(m.get("AGE")))
					.currentProcessStatus(integer(m.get("CURRENT_PROCESS_STATUS")))
					.qsParentDocId(longValue(m.get("QS_PARENT_DOC_ID")))
					.completedDate(dateOnly(m.get("COMPLETED_DATE")))
					.provSignedDate(dateOnly(m.get("PROV_SIGNED_DATE")))
					.createDate(dateOnly(m.get("CREATE_DATE")))
					.entryReviewDate(dateOnly(m.get("ENTRY_REVIEW_DATE")))
					.reviewDate(dateOnly(m.get("REVIEW_DATE")))
					.eharsImportDate(dateOnly(m.get("EHARS_IMPORT_DATE")))
					.nycShipDate(dateOnly(m.get("NYC_SHIP_DATE")))
					.matchedProcessDate(dateOnly(m.get("MATCHED_PROCESS_DATE")))
					.comments(upperTrim(m.get("COMMENTS")))
					.everHadNeg(upperTrim(m.get("EVER_HAD_NEG")))
					.eharsStatusCd(upperTrim(m.get("EHARS_STATUS_CD")))
					.provLicenseProfCd(upperTrim(m.get("PROV_LICENSE_PROF_CD")))
					.addressMappingId(upperTrim(m.get("ADDRESS_MAPPING_ID")))
					.fluxMatchCd(text(m.get("FLUX_MATCH_CD")))
					.fluxPotStateno(text(m.get("FLUX_POT_STATENO")))
					.fluxMatchedStateno(text(m.get("FLUX_MATCHED_STATENO")))
					.fluxQsParentDocId(longValue(m.get("FLUX_QS_PARENT_DOC_ID")))
					.fluxMatchedProcessDate(dateOnly(m.get("FLUX_MATCHED_PROCESS_DATE")))
					.sexualOrientation(upperTrim(m.get("SEXUAL_ORIENTATION")))
					.addSexualOrienationIdentity(upperTrim(m.get("ADD_SEXUAL_ORIENATION_IDENTITY")))
					.build());
		return 1;
	}

	/**
	 * Copies the raw partners of a form to its copy (new PARTNER_IDs from SEQ_PRF_PARTNER).
	 *
	 * @param inputOutputParameterMap oldDocumentId, newDocumentId (bound; formerly pasted into the SQL)
	 */
	@Transactional
	public void storeRawPrfPartnersOOSDocument(Map<Object, Object> inputOutputParameterMap) {
		rawPartners.copyPartners(integer(inputOutputParameterMap.get("newDocumentId")),
				integer(inputOutputParameterMap.get("oldDocumentId")));
	}
}
