package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A syphilis report, standardized (table STD_SYPHILIS_DOC); the columns the syphilis document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_SYPHILIS_DOC")
public class StdSyphilisDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "DOD")
	private Date dod;

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

	@Column(name = "CASEKEY")
	private String casekey;

	@Column(name = "DISEASE_CODE")
	private String diseaseCode;

	@Column(name = "SYPHILIS_DIAGNOSIS_DATE")
	private Date syphilisDiagnosisDate;

	@Column(name = "RECEIVED_DATE")
	private Date receivedDate;

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

	@Column(name = "PROV_COUNTY")
	private String provCounty;
}
