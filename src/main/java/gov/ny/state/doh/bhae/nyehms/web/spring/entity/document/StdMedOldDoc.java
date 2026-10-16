package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An old Medicaid report, standardized (table STD_MED_OLD_DOC); the columns the Medicaid Old document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_MED_OLD_DOC")
public class StdMedOldDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_MULTIPLE")
	private String raceMultiple;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

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

	@Column(name = "PROV_NAME")
	private String provName;

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

	@Column(name = "CRITERIA_1")
	private String criteria1;

	@Column(name = "CRITERIA_2")
	private String criteria2;

	@Column(name = "CRITERIA_3")
	private String criteria3;

	@Column(name = "CRITERIA_4")
	private String criteria4;

	@Column(name = "CRITERIA_5")
	private String criteria5;

	@Column(name = "CRITERIA_6")
	private String criteria6;

	@Column(name = "CRITERIA_7")
	private String criteria7;

	@Column(name = "CRITERIA_8")
	private String criteria8;

	@Column(name = "CRITERIA_9")
	private String criteria9;

	@Column(name = "CRITERIA_10")
	private String criteria10;

	@Column(name = "CRITERIA_11")
	private String criteria11;

	@Column(name = "CRITERIA_12")
	private String criteria12;

	@Column(name = "CRITERIA_13")
	private String criteria13;

	@Column(name = "CRITERIA_14")
	private String criteria14;

	@Column(name = "CRITERIA_15")
	private String criteria15;

	@Column(name = "CRITERIA_16")
	private String criteria16;

	@Column(name = "CRITERIA_17")
	private String criteria17;

	@Column(name = "CRITERIA_18")
	private String criteria18;

	@Column(name = "CRITERIA_19")
	private String criteria19;

	@Column(name = "CRITERIA_20")
	private String criteria20;

	@Column(name = "HIV_CRG")
	private String hivCrg;

	@Column(name = "HIV_SCREEN")
	private String hivScreen;

	@Column(name = "NYC_SHIP_DATE")
	private Date nycShipDate;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "AGE")
	private String age;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;
}
