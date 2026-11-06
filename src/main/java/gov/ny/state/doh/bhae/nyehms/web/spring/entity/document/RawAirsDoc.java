package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An AIRS (AIDS Institute Reporting System) report, as received (table RAW_AIRS_DOC); only the columns the application
 * reads (the AIRS document screens) and changes (the process status, by bulk updates of AIRSDocumentDAO).
 * <p>
 * Read in JPQL; never inserted through this entity. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 * The received values are text.
 */
@Entity
@Getter
@Table(name = "RAW_AIRS_DOC")
public class RawAirsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PROV_ADDR")
	private String provAddr;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "AKA")
	private String aka;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "AGE")
	private String age;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "COUNTRY_BIRTH")
	private String countryBirth;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "TELEPHONE")
	private String telephone;

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

	@Column(name = "ETHNICCD")
	private String ethniccd;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "GENDER")
	private String gender;

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

	@Column(name = "TC_ID")
	private String tcId;

	@Column(name = "URN")
	private String urn;

	@Column(name = "MEDICAIDNO")
	private String medicaidno;

	@Column(name = "CASE_NO")
	private String caseNo;

	@Column(name = "MEDRECNO")
	private String medrecno;

	@Column(name = "FED_ID")
	private String fedId;

	@Column(name = "DUNS_ID")
	private String dunsId;

	@Column(name = "ADMISS_DT")
	private String admissDt;

	@Column(name = "DATE_RECV_BLD_PROD")
	private String dateRecvBldProd;

	@Column(name = "I_DIAG_DT")
	private String iDiagDt;

	@Column(name = "INFORMED_OF_DIAG")
	private String informedOfDiag;

	@Column(name = "INFORMED_OF_DIAG_DT")
	private String informedOfDiagDt;

	@Column(name = "PREV_I_DIAG_DT")
	private String prevIDiagDt;

	@Column(name = "D_DIAG")
	private String dDiag;

	@Column(name = "D_DIAG_DT")
	private String dDiagDt;

	@Column(name = "PREV_D_DIAG")
	private String prevDDiag;

	@Column(name = "PREV_D_DIAG_DT")
	private String prevDDiagDt;

	@Column(name = "DT_PT_INTERVIEW")
	private String dtPtInterview;

	@Column(name = "DT_FST_POS_HIV")
	private String dtFstPosHiv;

	@Column(name = "ARV_6_MO")
	private String arv6Mo;

	@Column(name = "ARV_6_MO_NAME")
	private String arv6MoName;

	@Column(name = "ARV_6_MO_START")
	private String arv6MoStart;

	@Column(name = "ARV_6_MO_END")
	private String arv6MoEnd;

	@Column(name = "ARV_CURRENT_TEST")
	private String arvCurrentTest;

	@Column(name = "DT_FST_HIV_EVER")
	private String dtFstHivEver;

	@Column(name = "NUMBER_TEST_2_YRS")
	private String numberTest2Yrs;

	@Column(name = "EVER_TEST_NEGATIVE")
	private String everTestNegative;

	@Column(name = "DT_LAST_NEGATIVE")
	private String dtLastNegative;

	@Column(name = "PROVNAME")
	private String provname;

	@Column(name = "PROVIDER_DESCRIPTION")
	private String providerDescription;

	@Column(name = "AGENCY_ID")
	private String agencyId;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_ST")
	private String provSt;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
}
