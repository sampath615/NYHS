package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A rejected NYC surveillance record (table NYC_SURVEILLANCE_REJECT); the columns the NYC reject document screen reads
 * and its comment change writes (the table is filled by the NYC reject upload, still in MyBatis).
 * <p>
 * Read through projections and changed by bulk updates only, so a key that repeats does not matter. <b>DOCUMENT_ID as
 * the key is assumed.</b> UPDATE_DATE is mapped twice: as text (read with getString, as the screen's VO read it) and as
 * a date (written).
 */
@Entity
@Getter
@Table(name = "NYC_SURVEILLANCE_REJECT")
public class NycSurveillanceReject {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "NYCNUMBER")
	private String nycnumber;

	@Column(name = "REJECT_DOCUMENT_ID")
	private Integer rejectDocumentId;

	@Column(name = "DISPOSITION_CODE")
	private String dispositionCode;

	@Column(name = "STATUSNOTES")
	private String statusnotes;

	@Column(name = "STATUSNONCASE")
	private String statusnoncase;

	/** Read as text, as the screen's VO read it. */
	@Column(name = "STATUS_UPDATE_DATE")
	private String statusUpdateDate;

	@Column(name = "STATENO2")
	private String stateno2;

	@Column(name = "UPLOAD_FILENAME")
	private String uploadFilename;

	@Column(name = "UPLOAD_UID")
	private String uploadUid;

	@Column(name = "UPLOAD_DATE")
	private Timestamp uploadDate;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "UPDATE_UID")
	private String updateUid;

	/** UPDATE_DATE read as text (getString), as the screen's VO read it. */
	@Column(name = "UPDATE_DATE", insertable = false, updatable = false)
	private String updateDateText;

	/** UPDATE_DATE as a date (written by the comment change). */
	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;
}
