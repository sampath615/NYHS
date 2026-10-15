package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An NYC Partner Referral Form, standardized (table STD_NYCPRF_DOC); the columns the NYC PRF
 * document screen reads and the import writes.
 * <p>
 * Never updated: the rows are inserted with an HQL insert (NycprfDocWriter), not persisted.
 * <b>DOCUMENT_ID as the key is assumed</b> (one standardized row per document) — confirm against
 * the table's primary key (docs/JPA_MIGRATION.md).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_NYCPRF_DOC")
public class StdNycprfDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "PRF_SRC_ID")
	private Long prfSrcId;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "ALT_NAME")
	private String altName;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "DIN")
	private String din;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_OTHER_TEXT")
	private String raceOtherText;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "SWM")
	private String swm;

	@Column(name = "SWF")
	private String swf;

	@Column(name = "IDU")
	private String idu;

	@Column(name = "SEX_PARTNER_IDU")
	private String sexPartnerIdu;

	@Column(name = "SEX_PARTNER_HIV")
	private String sexPartnerHiv;

	@Column(name = "RECV_BLOOD_PROD")
	private String recvBloodProd;

	@Column(name = "PERINATAL")
	private String perinatal;

	@Column(name = "RISK_OTHER")
	private String riskOther;

	@Column(name = "RISK_UNKNOWN")
	private String riskUnknown;

	@Column(name = "SEX_PARTNER_MSM")
	private String sexPartnerMsm;

	@Column(name = "SEX_PARTNER_HEMOPHILIAC")
	private String sexPartnerHemophiliac;

	@Column(name = "HEALTHCARE_WORKER")
	private String healthcareWorker;

	@Column(name = "RISK_OTHER_TEXT")
	private String riskOtherText;

	@Column(name = "RECV_BLOOD_PROD_DATE_TEXT")
	private String recvBloodProdDateText;

	@Column(name = "RECV_BLOOD_PROD_COUNTRY")
	private String recvBloodProdCountry;

	@Column(name = "ADMISSION_DATE")
	private Date admissionDate;

	@Column(name = "INITIAL_HIV_DIAG")
	private String initialHivDiag;

	@Column(name = "INITIAL_HIV_DIAG_DATE_TEXT")
	private String initialHivDiagDateText;

	@Column(name = "INFORMED_HIV_POS")
	private String informedHivPos;

	@Column(name = "INFORMED_HIV_DATE_TEXT")
	private String informedHivDateText;

	@Column(name = "PREVIOUS_HIV_DIAG")
	private String previousHivDiag;

	@Column(name = "PREV_HIV_DIAG_DATE_TEXT")
	private String prevHivDiagDateText;

	@Column(name = "INITIAL_AIDS_DIAG")
	private String initialAidsDiag;

	@Column(name = "INIT_AIDS_DIAG_DATE_TEXT")
	private String initAidsDiagDateText;

	@Column(name = "PREVIOUS_AIDS_DIAG")
	private String previousAidsDiag;

	@Column(name = "PREV_AIDS_DIAG_DATE_TEXT")
	private String prevAidsDiagDateText;

	@Column(name = "PS_REQUESTED")
	private String psRequested;

	@Column(name = "COMPLETED_DATE")
	private Date completedDate;

	@Column(name = "CONTACT_LNAME")
	private String contactLname;

	@Column(name = "CONTACT_FNAME")
	private String contactFname;

	@Column(name = "CONTACT_MNAME")
	private String contactMname;

	@Column(name = "CONTACT_TITLE")
	private String contactTitle;

	@Column(name = "CONTACT_PHONE")
	private String contactPhone;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_MI")
	private String provMi;

	@Column(name = "PROV_DEPARTMENT")
	private String provDepartment;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "PROV_COUNTY")
	private String provCounty;

	@Column(name = "PROV_PHONE")
	private String provPhone;

	@Column(name = "PROV_EXT")
	private String provExt;

	@Column(name = "PRO_EMAIL")
	private String proEmail;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "REVIEW_DATE")
	private Date reviewDate;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "PT_INTERVIEW_DATE")
	private String ptInterviewDate;

	@Column(name = "FIRST_POS_HIV_DATE")
	private String firstPosHivDate;

	@Column(name = "FIRST_POS_ANON")
	private String firstPosAnon;

	@Column(name = "FIRST_POS_FAC_NAME")
	private String firstPosFacName;

	@Column(name = "FIRST_POS_FAC_STATE")
	private String firstPosFacState;

	@Column(name = "RSON_FIRST_POS_EXP")
	private String rsonFirstPosExp;

	@Column(name = "RSON_FIRST_POS_ROUTN")
	private String rsonFirstPosRoutn;

	@Column(name = "RSON_FIRST_POS_CHKNEG")
	private String rsonFirstPosChkneg;

	@Column(name = "RSON_FIRST_POS_REQD")
	private String rsonFirstPosReqd;

	@Column(name = "RSON_FIRST_POS_PRTNR")
	private String rsonFirstPosPrtnr;

	@Column(name = "RSON_FIRST_POS_OTHER")
	private String rsonFirstPosOther;

	@Column(name = "RSON_FIRST_POS_OTHER_TEXT")
	private String rsonFirstPosOtherText;

	@Column(name = "ARV_6_MO")
	private String arv6Mo;

	@Column(name = "ARV_6_MO_NAME")
	private String arv6MoName;

	@Column(name = "ARV_6_MO_START_DATE_TEXT")
	private String arv6MoStartDateText;

	@Column(name = "ARV_6_MO_END_DATE_TEXT")
	private String arv6MoEndDateText;

	@Column(name = "ARV_CURRENT_TEST")
	private String arvCurrentTest;

	@Column(name = "FIRST_HIV_TEST_EVER_DATE_TEXT")
	private String firstHivTestEverDateText;

	@Column(name = "NUMBER_TEST_2_YRS")
	private String numberTest2Yrs;

	@Column(name = "EVER_TEST_NEG")
	private String everTestNeg;

	@Column(name = "LAST_NEG_DATE_TEXT")
	private String lastNegDateText;

	@Column(name = "LAST_NEG_FAC_TYPE")
	private String lastNegFacType;

	@Column(name = "LAST_NEG_FAC_NAME")
	private String lastNegFacName;

	@Column(name = "LAST_NEG_FAC_STATE")
	private String lastNegFacState;

	@Column(name = "FIELD_PROC_DATE", insertable = false, updatable = false)
	private Date fieldProcDate;

	/**
	 * The same column as written by the import: the received text, which Oracle converts to a date
	 * (as the former MyBatis insert did). Use {@link #fieldProcDate} to read it.
	 */
	@Column(name = "FIELD_PROC_DATE")
	private String fieldProcDateText;

	@Column(name = "APARTMENT")
	private String apartment;

	@Column(name = "SEX_ORN")
	private String sexOrn;

	@Column(name = "SEX_ORN_ADD_ID")
	private String sexOrnAddId;

	@Column(name = "NUM_NEG_HIV_TEST")
	private String numNegHivTest;

	@Column(name = "ACUTE_INFECTION")
	private String acuteInfection;

	@Column(name = "ADDITIONAL_GENDER_IDENTITY")
	private String additionalGenderIdentity;

	@Column(name = "ALT_LNAME")
	private String altLname;

	@Column(name = "ALT_MNAME")
	private String altMname;

	@Column(name = "ARV_PREVENPREP_MED_NAMES")
	private String arvPrevenprepMedNames;

	@Column(name = "ARV_PREVEN_PREP")
	private String arvPrevenPrep;

	@Column(name = "ARV_PREVEN_PREP_END_DT")
	private String arvPrevenPrepEndDt;

	@Column(name = "ARV_PREVEN_PREP_START_DT")
	private String arvPrevenPrepStartDt;

	@Column(name = "ARV_TREATMENT")
	private String arvTreatment;

	@Column(name = "PT_ARV_NAME")
	private String ptArvName;

	@Column(name = "PT_ARV_START_DATE_TEXT")
	private String ptArvStartDateText;

	@Column(name = "PT_ARV_END_DATE_TEXT")
	private String ptArvEndDateText;

	@Column(name = "POST_EXP_ARV_PEP")
	private String postExpArvPep;

	@Column(name = "POST_EXP_ARV_PEP_END_DT")
	private String postExpArvPepEndDt;

	@Column(name = "POST_EXP_ARV_PEP_MED_NAMES")
	private String postExpArvPepMedNames;

	@Column(name = "POST_EXP_ARV_PEP_START_DT")
	private String postExpArvPepStartDt;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "EDC")
	private String edc;

	@Column(name = "FACILITY_TYPE")
	private String facilityType;

	@Column(name = "FACILITY_TYPE_OTHER_TEXT")
	private String facilityTypeOtherText;

	@Column(name = "PRIMARY_LANGUAGE")
	private String primaryLanguage;

	@Column(name = "PRIMARY_LANGUAGE_OTHER")
	private String primaryLanguageOther;

	@Column(name = "REFERRED_HIV_PROVIDER")
	private String referredHivProvider;

	@Column(name = "REFERRED_PROVIDER_NAME")
	private String referredProviderName;

	@Column(name = "REFFERED_PROVIDER_TELEPHONE")
	private String refferedProviderTelephone;

	@Column(name = "PROVIDER_NYS_LIC_NO")
	private String providerNysLicNo;

	@Column(name = "PROVIDER_NPI_NO")
	private String providerNpiNo;

	@Column(name = "PREGNANT")
	private String pregnant;

	@Column(name = "PT_ARV_EVER_TAKEN")
	private String ptArvEverTaken;

	@Column(name = "EVER_HAD_PREV_POS_TEST")
	private String everHadPrevPosTest;

	@Column(name = "PERSON_OTHER_IDENTIFIER")
	private String personOtherIdentifier;

	@Column(name = "MDPRINTNAME")
	private String mdprintname;

	@Column(name = "PRINTED_DATE")
	private String printedDate;

	@Column(name = "PROV_FACILITY")
	private String provFacility;

	@Column(name = "PROV_TITLE")
	private String provTitle;

	// written by the import (NycprfDocWriter), not shown on the document screen
	@Column(name = "FIRST_POS_LOCATION")
	private String firstPosLocation;

	@Column(name = "PERSON_CENSUS_TRACT")
	private String personCensusTract;

	@Column(name = "TSAID")
	private String tsaid;

	@Column(name = "REVTSAID")
	private String revtsaid;

	@Column(name = "RESP_HIV_TEST_2YRS")
	private String respHivTest2yrs;

	@Column(name = "WARNING")
	private String warning;

	@Column(name = "EHARS_NYC_PRF_UID")
	private String eharsNycPrfUid;

	@Column(name = "NOTSEND")
	private String notsend;

	@Column(name = "PERSON_LONGITUDE")
	private String personLongitude;

	@Column(name = "RACE_CD")
	private String raceCd;

	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "PERSON_LATITUDE")
	private String personLatitude;

	@Column(name = "EHARS")
	private String ehars;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "OLDFORM")
	private String oldform;

	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	@Column(name = "SIG_DATE")
	private Date sigDate;

	@Column(name = "EHARS_DATE")
	private Date eharsDate;

	@Column(name = "RECEIVED_DATE")
	private Date receivedDate;

	@Column(name = "ENTER_DATE")
	private Date enterDate;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "MATCH_WEIGHT")
	private String matchWeight;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private String currentProcessStatus;

	@Column(name = "MATCH_PASS")
	private String matchPass;

	@Column(name = "AGE_CATEGORY")
	private String ageCategory;

	@Column(name = "RECNUM")
	private String recnum;

	@Column(name = "AGE")
	private String age;

	@Column(name = "QS_PARENT_DOC_ID")
	private String qsParentDocId;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Date matchedProcessDate;

	@Column(name = "EHARS_DOCUMENT_SOURCE_CD")
	private String eharsDocumentSourceCd;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "FLUX_POT_STATENO")
	private String fluxPotStateno;

	@Column(name = "FLUX_MATCHED_STATENO")
	private String fluxMatchedStateno;

	@Column(name = "FLUX_QS_PARENT_DOC_ID")
	private Long fluxQsParentDocId;

	@Column(name = "FLUX_MATCHED_PROCESS_DATE")
	private Date fluxMatchedProcessDate;
}
