package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An old STD (sexually transmitted disease) report, standardized (table STD_STD_OLD_DOC); the columns the STD old document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_STD_OLD_DOC")
public class StdStdOldDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "STD_SRC_ID")
	private Long stdSrcId;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "INSURANCE_NUMBER")
	private String insuranceNumber;

	@Column(name = "INSURANCE_CD")
	private String insuranceCd;

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

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "PROV_PLUS4")
	private String provPlus4;

	@Column(name = "PROV_COUNTY")
	private String provCounty;

	@Column(name = "PROV_PHONE")
	private String provPhone;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "PROV_NAME")
	private String provName;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "SWM")
	private String swm;

	@Column(name = "SWF")
	private String swf;

	@Column(name = "IDU")
	private String idu;

	@Column(name = "SEX_PARTNER_IDU")
	private String sexPartnerIdu;

	@Column(name = "SEX_PARTNER_MSM")
	private String sexPartnerMsm;

	@Column(name = "RECEIVED_DATE")
	private Date receivedDate;

	@Column(name = "AGE")
	private Integer age;

	@Column(name = "RACE_INDIAN")
	private Boolean raceIndian;

	@Column(name = "RACE_BLACK")
	private Boolean raceBlack;

	@Column(name = "RACE_ASIAN")
	private Boolean raceAsian;

	@Column(name = "RACE_PACIFIC")
	private Boolean racePacific;

	@Column(name = "RACE_WHITE")
	private Boolean raceWhite;

	@Column(name = "RACE_MULTIPLE")
	private Boolean raceMultiple;

	@Column(name = "RACE_OTHER")
	private Boolean raceOther;

	@Column(name = "RACE_UNKNOWN")
	private Boolean raceUnknown;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "VITAL_STATUS")
	private String vitalStatus;

	@Column(name = "SEXUAL_PREFERENCE")
	private String sexualPreference;

	@Column(name = "SWA")
	private String swa;

	@Column(name = "SWT")
	private String swt;

	@Column(name = "SEX_WHILE_INTOX")
	private String sexWhileIntox;

	@Column(name = "EXCHANGE_SEX")
	private String exchangeSex;

	@Column(name = "SEX_WOUT_CONDOM")
	private String sexWoutCondom;

	@Column(name = "INCARCERATED")
	private String incarcerated;

	@Column(name = "SHARED_DRUG_EQUIP")
	private String sharedDrugEquip;

	@Column(name = "DRUG_USE")
	private String drugUse;

	@Column(name = "DISEASE_CD")
	private String diseaseCd;

	@Column(name = "DISEASE_DATE")
	private String diseaseDate;

	@Column(name = "STD_FILE")
	private String stdFile;
}
