package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An AIRS (AIDS Institute Reporting System) report, standardized (table STD_AIRS_DOC); the columns the AIRS document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_AIRS_DOC")
public class StdAirsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "NYC_SHIP_DATE")
	private Date nycShipDate;

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

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "AGE")
	private String age;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "BIRTH_COUNTRY_OTHER")
	private String birthCountryOther;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

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

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "SWM")
	private String swm;

	@Column(name = "SWF")
	private String swf;

	@Column(name = "IDU")
	private String idu;

	@Column(name = "SEX_PARTNER_IDU")
	private String sexPartnerIdu;

	@Column(name = "RECV_BLOOD_PROD")
	private String recvBloodProd;

	@Column(name = "SEX_PARTNER_HIV")
	private String sexPartnerHiv;

	@Column(name = "PERINATAL")
	private String perinatal;

	@Column(name = "RISK_OTHER")
	private String riskOther;

	@Column(name = "RISK_UNKNOWN")
	private String riskUnknown;

	@Column(name = "TC_ID")
	private String tcId;

	@Column(name = "URN")
	private String urn;

	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;

	@Column(name = "CASE_NO")
	private String caseNo;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "FED_ID")
	private String fedId;

	@Column(name = "DUNS_ID")
	private String dunsId;

	@Column(name = "ADMISSION_DATE_TEXT")
	private String admissionDateText;

	@Column(name = "RECV_BLOOD_PROD_DATE_TEXT")
	private String recvBloodProdDateText;

	@Column(name = "INIT_HIV_DIAG")
	private String initHivDiag;

	@Column(name = "INIT_HIV_DIAG_DATE_TEXT")
	private String initHivDiagDateText;

	@Column(name = "INFORMED_HIV_POS")
	private String informedHivPos;

	@Column(name = "INFORMED_HIV_DATE_TEXT")
	private String informedHivDateText;

	@Column(name = "PREV_HIV_DIAG")
	private String prevHivDiag;

	@Column(name = "PREV_HIV_DIAG_DATE_TEXT")
	private String prevHivDiagDateText;

	@Column(name = "INIT_AIDS_DIAG")
	private String initAidsDiag;

	@Column(name = "INIT_AIDS_DIAG_DATE_TEXT")
	private String initAidsDiagDateText;

	@Column(name = "PREV_AIDS_DIAG")
	private String prevAidsDiag;

	@Column(name = "PREV_AIDS_DIAG_DATE_TEXT")
	private String prevAidsDiagDateText;

	@Column(name = "PT_INTERVIEW_DATE")
	private Date ptInterviewDate;

	@Column(name = "FIRST_POS_HIV_DATE")
	private Date firstPosHivDate;

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

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "PROVIDER_DESCRIPTION")
	private String providerDescription;

	@Column(name = "AGENCY_ID")
	private String agencyId;

	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;

	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@Column(name = "FACILITY_PHONE")
	private String facilityPhone;

	@Column(name = "FACILITY_PLUS4")
	private String facilityPlus4;

	@Column(name = "NUM_PARTNERS")
	private String numPartners;

	@Column(name = "SIG_DATE_TEXT")
	private String sigDateText;

	@Column(name = "ARV_6_MO_NAME2")
	private String arv6MoName2;

	@Column(name = "ARV_6_MO_NAME3")
	private String arv6MoName3;

	@Column(name = "ARV_6_MO_NAME4")
	private String arv6MoName4;

	@Column(name = "IS_MEDICAID")
	private String isMedicaid;

	@Column(name = "AIRS_CALC_FILE_DATE")
	private Date airsCalcFileDate;

	@Column(name = "RECORD_TYPE")
	private String recordType;

	@Column(name = "CTR_FLAG")
	private String ctrFlag;

	@Column(name = "EXTRALOGID")
	private String extralogid;

	@Column(name = "AIRS_VERSION")
	private String airsVersion;
}
