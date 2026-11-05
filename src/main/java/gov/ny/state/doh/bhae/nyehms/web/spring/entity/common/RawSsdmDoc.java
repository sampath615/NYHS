package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An SSDM (Social Security Death Master) record as received (table RAW_SSDM_DOC); the columns the SSDM import reads and
 * changes (the table is also written by the document edit, still in MyBatis).
 * <p>
 * Read by the bulk import's insert ... select and changed by bulk updates only. <b>DOCUMENT_ID as the key is
 * assumed.</b> The dates of birth and death are text.
 */
@Entity
@Getter
@Table(name = "RAW_SSDM_DOC")
public class RawSsdmDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "SSDM_SRC_ID")
	private Long ssdmSrcId;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "SSDM_FILE")
	private String ssdmFile;

	@Column(name = "BTHYEAR")
	private String bthyear;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "ORIGLNAME")
	private String origlname;

	@Column(name = "NSUFFIX")
	private String nsuffix;

	@Column(name = "FIRSTNAME")
	private String firstname;

	@Column(name = "MIDDLENAME")
	private String middlename;

	/** The state of residence at death. */
	@Column(name = "STZ")
	private String stz;

	@Column(name = "BTHDATE")
	private String bthdate;

	@Column(name = "DTHDATE")
	private String dthdate;

	@Column(name = "AGEDEATH")
	private Integer agedeath;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;
}
