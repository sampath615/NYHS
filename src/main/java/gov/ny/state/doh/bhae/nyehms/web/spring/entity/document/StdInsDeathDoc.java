package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An insurance death record, standardized (table STD_INS_DEATH_DOC); the columns the insurance death document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_INS_DEATH_DOC")
public class StdInsDeathDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_MULTIPLE")
	private String raceMultiple;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "DATE_OF_DEATH")
	private Date dateOfDeath;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "INS_DEATH_FILE_NAME")
	private String insDeathFileName;

	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;

	@Column(name = "DECEASED_STATUS")
	private String deceasedStatus;

	@Column(name = "SSN")
	private String ssn;
}
