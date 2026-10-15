package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An old case report, standardized (table STD_OLD_CASES_DOC); the columns the Old Cases document
 * screen reads.
 * <p>
 * Read-only. All columns but the key are read as text (also the DATE columns DATE_OF_BIRTH,
 * DATE_OF_DEATH and CREATE_DATE), as the former MyBatis mapper did, so the screen shows the same
 * values. <b>DOCUMENT_ID as the key is assumed</b> (one standardized row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_OLD_CASES_DOC")
public class StdOldCasesDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "OLDCASE_SRC_ID")
	private String oldcaseSrcId;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "DATE_OF_BIRTH")
	private String dateOfBirth;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "DATE_OF_DEATH")
	private String dateOfDeath;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "DIN")
	private String din;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "RACE")
	private String race;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "CREATE_DATE")
	private String createDate;

	@Column(name = "CREATE_UID")
	private String createUid;

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

	@Column(name = "COMMENTS")
	private String comments;
}
