package gov.ny.state.doh.bhae.nyehms.web.spring.entity.upload;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.sql.Timestamp;

/**
 * The staging table of the confirmed STD (PS confirmed STDs) upload (table STG_STD_CONFIRMED_DOC): the uploaded rows,
 * filed as documents (DOCUMENT_LOCATOR) and copied to RAW_STD_CONFIRMED_DOC by JPQL insert-selects, then deleted.
 * <p>
 * Not loaded as an entity: only the insert-selects and the bulk delete use it. <b>DOCUMENT_ID as the key is assumed.</b>
 * The attribute types only serve the copy (the types of RawStdConfirmedDoc, so the copy is a plain column copy and the
 * database converts as before): text, except DOCUMENT_ID and UPLOAD_DT; DOB, DATE_OF_DEATH, DIAG_DATE and X900DXDATE
 * are text (MM/DD/YYYY).
 */
@Entity
@Getter
@Table(name = "STG_STD_CONFIRMED_DOC")
public class StgStdConfirmedDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "CASEKEY")
	private String casekey;

	@Column(name = "HINSERIALNO")
	private String hinserialno;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

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

	/** Text (MM/DD/YYYY), converted with TO_DATE by the move to the raw table. */
	@Column(name = "DOB")
	private String dob;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	/** Text (MM/DD/YYYY), converted with TO_DATE by the move to the raw table. */
	@Column(name = "DATE_OF_DEATH")
	private String dateOfDeath;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "PROV_PHONE")
	private String provPhone;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_ST")
	private String provSt;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	/** Text (MM/DD/YYYY), converted with TO_DATE by the move to the raw table. */
	@Column(name = "DIAG_DATE")
	private String diagDate;

	@Column(name = "DISEASE")
	private String disease;

	@Column(name = "RISK_SEX_MALE")
	private String riskSexMale;

	@Column(name = "RISK_SEX_FEMALE")
	private String riskSexFemale;

	@Column(name = "RISK_SEX_TRANSGENDER")
	private String riskSexTransgender;

	@Column(name = "RISK_SEX_IDU")
	private String riskSexIdu;

	@Column(name = "RISK_SEX_DRUGS")
	private String riskSexDrugs;

	@Column(name = "RISK_SEX_EXCHANGED")
	private String riskSexExchanged;

	@Column(name = "RISK_SEX_ANONYMOUS")
	private String riskSexAnonymous;

	@Column(name = "RISK_SEX_MSM")
	private String riskSexMsm;

	@Column(name = "RISK_SEX_WITHOUT_CONDOM")
	private String riskSexWithoutCondom;

	@Column(name = "RISK_DRUGS_USED")
	private String riskDrugsUsed;

	@Column(name = "RISK_DRUG_USED_LIST")
	private String riskDrugUsedList;

	@Column(name = "RISK_DRUG_OTHER_TEXT")
	private String riskDrugOtherText;

	@Column(name = "RISK_DRUG_INCARCERATED")
	private String riskDrugIncarcerated;

	@Column(name = "RISK_DRUG_PARAPHENALIA")
	private String riskDrugParaphenalia;

	@Column(name = "RISK_IDU")
	private String riskIdu;

	@Column(name = "RISK_OTHER_TEXT")
	private String riskOtherText;

	@Column(name = "FIELDNOTE")
	private String fieldnote;

	@Column(name = "NINEHUNDREDTEST")
	private String ninehundredtest;

	@Column(name = "TESTTECHNOLOGY")
	private String testtechnology;

	@Column(name = "X900POSITIVE")
	private String x900positive;

	/** Text (MM/DD/YYYY), converted with TO_DATE by the move to the raw table. */
	@Column(name = "X900DXDATE")
	private String x900dxdate;

	@Column(name = "CURRENT900TESTRESULT")
	private String current900testresult;

	@Column(name = "UPLOAD_ID")
	private String uploadId;

	@Column(name = "UPLOAD_DT")
	private Timestamp uploadDt;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private String currentProcessStatus;

	@Column(name = "FILENAME")
	private String filename;
}
