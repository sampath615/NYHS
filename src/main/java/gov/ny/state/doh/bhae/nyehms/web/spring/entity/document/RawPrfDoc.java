package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A provider report form (PRF) as entered or received (table RAW_PRF_DOC): the columns the PRF screens read and the
 * data entry, the review and the document copy write (PRFDocumentDAO).
 * <p>
 * Inserted once ({@code saveAndFlush}, always new), read with JPQL projections; its status is changed by a bulk JPQL
 * update only. Columns whose type is not known are text: what MyBatis bound (a text, a number, a boolean as 1 / 0)
 * is written as the text Oracle converts it to, the same for a text or a number column (see {@code BoundValues}).
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_PRF_DOC")
public class RawPrfDoc implements Persistable<Integer> {

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

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "ALT_FNAME")
	private String altFname;

	@Column(name = "ALT_MNAME")
	private String altMname;

	@Column(name = "ALT_LNAME")
	private String altLname;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "DIN")
	private String din;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_PLUS4")
	private String personPlus4;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_TELEPHONE")
	private String personTelephone;

	@Column(name = "GENDER")
	private String gender;

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

	@Column(name = "RISK01")
	private String risk01;

	@Column(name = "RISK02")
	private String risk02;

	@Column(name = "RISK03")
	private String risk03;

	@Column(name = "RISK04")
	private String risk04;

	@Column(name = "RISK05")
	private String risk05;

	@Column(name = "RISK06")
	private String risk06;

	@Column(name = "RISK07")
	private String risk07;

	@Column(name = "RISK08")
	private String risk08;

	@Column(name = "RISK09")
	private String risk09;

	@Column(name = "RISK10")
	private String risk10;

	@Column(name = "RISK_OTHER_TEXT")
	private String riskOtherText;

	@Column(name = "RECV_BLOOD_PROD_DATE")
	private String recvBloodProdDate;

	@Column(name = "RECV_BLOOD_PROD_COUNTRY")
	private String recvBloodProdCountry;

	@Column(name = "ADMISSION_DATE")
	private String admissionDate;

	@Column(name = "INIT_HIV_DIAG")
	private BigDecimal initHivDiag;

	@Column(name = "INIT_HIV_DIAG_DATE_TEXT")
	private String initHivDiagDateText;

	@Column(name = "INFORMED_HIV_POS")
	private String informedHivPos;

	@Column(name = "INFORMED_HIV_DATE_TEXT")
	private String informedHivDateText;

	@Column(name = "PREV_HIV_DIAG")
	private BigDecimal prevHivDiag;

	@Column(name = "PREV_HIV_DIAG_DATE_TEXT")
	private String prevHivDiagDateText;

	@Column(name = "INIT_AIDS_DIAG")
	private BigDecimal initAidsDiag;

	@Column(name = "INIT_AIDS_DIAG_DATE_TEXT")
	private String initAidsDiagDateText;

	@Column(name = "PREV_AIDS_DIAG")
	private BigDecimal prevAidsDiag;

	@Column(name = "PREV_AIDS_DIAG_DATE_TEXT")
	private String prevAidsDiagDateText;

	@Column(name = "PS_REQUESTED")
	private String psRequested;

	@Column(name = "COMPLETER_LNAME")
	private String completerLname;

	@Column(name = "COMPLETER_MNAME")
	private String completerMname;

	@Column(name = "COMPLETER_FNAME")
	private String completerFname;

	@Column(name = "COMPLETER_TITLE")
	private String completerTitle;

	@Column(name = "COMPLETER_PHONE")
	private String completerPhone;

	@Column(name = "COMPLETER_PHONE_EXT")
	private String completerPhoneExt;

	@Column(name = "COMPLETER_EMAIL")
	private String completerEmail;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_DEPT")
	private String facilityDept;

	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "FACILITY_PLUS4")
	private String facilityPlus4;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;

	@Column(name = "COMPLETED_DATE")
	private Timestamp completedDate;

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

	@Column(name = "CONTACT_PHONE_EXT")
	private String contactPhoneExt;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_MI")
	private String provMi;

	@Column(name = "PROV_SIGNED")
	private String provSigned;

	@Column(name = "PROV_SIGNED_DATE")
	private Timestamp provSignedDate;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "ENTRY_REVIEW_UID")
	private String entryReviewUid;

	@Column(name = "ENTRY_REVIEW_DATE")
	private Timestamp entryReviewDate;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "PT_INTERVIEW_DATE")
	private String ptInterviewDate;

	@Column(name = "FIRST_POS_HIV_DATE")
	private String firstPosHivDate;

	@Column(name = "FIRST_POS_TEST_ANON")
	private String firstPosTestAnon;

	@Column(name = "FIRST_POS_FAC_TYPE")
	private String firstPosFacType;

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

	@Column(name = "PT_ARV_6_MO")
	private String ptArv6Mo;

	@Column(name = "PT_ARV_NAME")
	private String ptArvName;

	@Column(name = "PT_ARV_START_DATE")
	private String ptArvStartDate;

	@Column(name = "PT_ARV_END_DATE")
	private String ptArvEndDate;

	@Column(name = "PT_ARV_TIME_OF_TEST")
	private String ptArvTimeOfTest;

	@Column(name = "FIRST_HIV_TEST_EVER_DATE")
	private String firstHivTestEverDate;

	@Column(name = "COUNT_HIV_TEST_2YRS")
	private String countHivTest2yrs;

	@Column(name = "RESP_HIV_TEST_2YRS")
	private String respHivTest2yrs;

	@Column(name = "EVER_HAD_NEG")
	private String everHadNeg;

	@Column(name = "LAST_NEG_DATE")
	private String lastNegDate;

	@Column(name = "LAST_NEG_FAC_TYPE")
	private String lastNegFacType;

	@Column(name = "LAST_NEG_FAC_NAME")
	private String lastNegFacName;

	@Column(name = "LAST_NEG_FAC_STATE")
	private String lastNegFacState;

	@Column(name = "EHARS_DOCUMENT_SOURCE_CD")
	private String eharsDocumentSourceCd;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "PARTNERS")
	private BigDecimal partners;

	@Column(name = "PERSON_ADDRESS_TYPE")
	private String personAddressType;

	@Column(name = "SEX_AT_BIRTH")
	private String sexAtBirth;

	@Column(name = "PREGNANT")
	private String pregnant;

	@Column(name = "EDC")
	private String edc;

	@Column(name = "PERSON_OTHER_IDENTIFIER")
	private String personOtherIdentifier;

	@Column(name = "PRIMARY_LANGUAGE")
	private String primaryLanguage;

	@Column(name = "PRIMARY_LANGUAGE_OTHER")
	private String primaryLanguageOther;

	@Column(name = "ACUTE_INFECTION")
	private String acuteInfection;

	@Column(name = "REFERRED_HIV_PROVIDER")
	private String referredHivProvider;

	@Column(name = "REFERRED_PROVIDER_NAME")
	private String referredProviderName;

	@Column(name = "REFERRED_PROVIDER_TELEPHONE")
	private String referredProviderTelephone;

	@Column(name = "EVER_HAD_PREV_POS_TEST")
	private String everHadPrevPosTest;

	@Column(name = "PT_ARV_EVER_TAKEN")
	private String ptArvEverTaken;

	@Column(name = "ARV_PREVEN_PREP")
	private String arvPrevenPrep;

	@Column(name = "ARV_PREVEN_PREP_START_DT")
	private String arvPrevenPrepStartDt;

	@Column(name = "ARV_PREVEN_PREP_END_DT")
	private String arvPrevenPrepEndDt;

	@Column(name = "ARV_PREVENPREP_MED_NAMES")
	private String arvPrevenprepMedNames;

	@Column(name = "POST_EXP_ARV_PEP")
	private String postExpArvPep;

	@Column(name = "POST_EXP_ARV_PEP_START_DT")
	private String postExpArvPepStartDt;

	@Column(name = "POST_EXP_ARV_PEP_END_DT")
	private String postExpArvPepEndDt;

	@Column(name = "POST_EXP_ARV_PEP_MED_NAMES")
	private String postExpArvPepMedNames;

	@Column(name = "ARV_TREATMENT")
	private String arvTreatment;

	@Column(name = "RSON_CLINICIAN_OFFERED_REC")
	private String rsonClinicianOfferedRec;

	@Column(name = "FACILITY_TYPE")
	private String facilityType;

	@Column(name = "FACILITY_TYPE_OTHER_TEXT")
	private String facilityTypeOtherText;

	@Column(name = "PROVIDER_NYS_LIC_NO")
	private String providerNysLicNo;

	@Column(name = "PROVIDER_NPI_NO")
	private String providerNpiNo;

	@Column(name = "ADDITIONAL_GENDER_IDENTITY")
	private String additionalGenderIdentity;

	@Column(name = "NYSID")
	private String nysid;

	@Column(name = "PROV_LICENSE_PROF_CD")
	private String provLicenseProfCd;

	@Column(name = "ADDRESS_MAPPING_ID")
	private String addressMappingId;

	@Column(name = "SEXUAL_ORIENTATION")
	private String sexualOrientation;

	@Column(name = "ADD_SEXUAL_ORIENATION_IDENTITY")
	private String addSexualOrienationIdentity;

	@Column(name = "PROV_FAC_TYPE_CODE")
	private String provFacTypeCode;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;

	@Override
	public Integer getId() {
		return documentId;
	}

	/** Rows are only ever inserted. */
	@Override
	public boolean isNew() {
		return true;
	}
}
