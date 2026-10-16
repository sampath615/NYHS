package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A confirmed STD (sexually transmitted disease) report, standardized (table STD_STD_CONFIRMED_DOC); the columns the confirmed STD document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_STD_CONFIRMED_DOC")
public class StdStdConfirmedDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "CASEKEY")
	private Integer casekey;

	@Column(name = "HINSERIALNO")
	private Long hinserialno;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "DIAG_DATE")
	private Date diagDate;

	@Column(name = "DATE_OF_DEATH")
	private Date dateOfDeath;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "PROV_PHONE")
	private String provPhone;

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

	@Column(name = "DISEASE")
	private Integer disease;

	@Column(name = "RISK_SEX_MALE")
	private String riskSexMale;

	@Column(name = "RISK_SEX_FEMALE")
	private String riskSexFemale;

	@Column(name = "RISK_SEX_TRANSGENDER")
	private String riskSexTransgender;

	@Column(name = "RISK_SEX_ANONYMOUS")
	private String riskSexAnonymous;

	@Column(name = "RISK_SEX_IDU")
	private String riskSexIdu;

	@Column(name = "RISK_SEX_DRUGS")
	private String riskSexDrugs;

	@Column(name = "RISK_SEX_EXCHANGED")
	private String riskSexExchanged;

	@Column(name = "RISK_SEX_MSM")
	private String riskSexMsm;

	@Column(name = "RISK_DRUGS_USED")
	private String riskDrugsUsed;

	@Column(name = "RISK_DRUG_USED_CRACK")
	private String riskDrugUsedCrack;

	@Column(name = "RISK_DRUG_USED_COCAINE")
	private String riskDrugUsedCocaine;

	@Column(name = "RISK_DRUG_USED_HEROIN")
	private String riskDrugUsedHeroin;

	@Column(name = "RISK_DRUG_USED_METHAMPHETAMINES")
	private String riskDrugUsedMethamphetamines;

	@Column(name = "RISK_DRUG_USED_NITRATES_POPPERS")
	private String riskDrugUsedNitratesPoppers;

	@Column(name = "RISK_DRUG_USED_ED_MEDICATIONS")
	private String riskDrugUsedEdMedications;

	@Column(name = "RISK_DRUG_USED_OTHER")
	private String riskDrugUsedOther;

	@Column(name = "RISK_DRUG_USED_MARIJUANA")
	private String riskDrugUsedMarijuana;

	@Column(name = "RISK_DRUG_USED_PRESCRIPTION_PAINKILLERS")
	private String riskDrugUsedPrescriptionPainkillers;

	@Column(name = "RISK_DRUG_USED_ALCOHOL")
	private String riskDrugUsedAlcohol;

	@Column(name = "RISK_DRUG_USED_BENZODIAZEPINES")
	private String riskDrugUsedBenzodiazepines;

	@Column(name = "RISK_DRUG_USED_PRESCRIPTION_OPIODS")
	private String riskDrugUsedPrescriptionOpiods;

	@Column(name = "RISK_DRUG_INCARCERATED")
	private String riskDrugIncarcerated;

	@Column(name = "RISK_SEX_WITHOUT_CONDOM")
	private String riskSexWithoutCondom;

	@Column(name = "RISK_DRUG_PARAPHENALIA")
	private String riskDrugParaphenalia;

	@Column(name = "RISK_IDU")
	private String riskIdu;

	@Column(name = "RISK_OTHER")
	private String riskOther;

	@Column(name = "RISK_OTHER_TEXT")
	private String riskOtherText;

	@Column(name = "RISK_DRUG_OTHER_TEXT")
	private String riskDrugOtherText;

	@Column(name = "FIELDNOTE")
	private String fieldnote;

	@Column(name = "NINEHUNDREDTEST")
	private String ninehundredtest;

	@Column(name = "TESTTECHNOLOGY")
	private String testtechnology;

	@Column(name = "X900POSITIVE")
	private String x900positive;

	@Column(name = "X900DXDATE")
	private Date x900dxdate;

	@Column(name = "CURRENT900TESTRESULT")
	private String current900testresult;

	@Column(name = "UPLOAD_DT")
	private String uploadDt;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private String currentProcessStatus;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PROV_COUNTY")
	private String provCounty;
}
