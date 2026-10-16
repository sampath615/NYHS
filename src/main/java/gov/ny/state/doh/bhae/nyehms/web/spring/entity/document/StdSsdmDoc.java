package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An SSDM (Social Security Death Master) record, standardized (table STD_SSDM_DOC); the columns the SSDM document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_SSDM_DOC")
public class StdSsdmDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "SSDM_SRC_ID")
	private Long ssdmSrcId;

	@Column(name = "SSN")
	private String ssn;

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

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "BIRTH_YEAR")
	private String birthYear;

	@Column(name = "AGE_AT_DEATH")
	private Integer ageAtDeath;

	@Column(name = "PERSON_STATE_AT_DEATH")
	private String personStateAtDeath;

	@Column(name = "PERSON_ZIP_AT_DEATH")
	private String personZipAtDeath;

	@Column(name = "PERSON_COUNTY_AT_DEATH")
	private String personCountyAtDeath;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "SSDM_FILE")
	private String ssdmFile;

	@Column(name = "ORIG_LNAME")
	private String origLname;
}
