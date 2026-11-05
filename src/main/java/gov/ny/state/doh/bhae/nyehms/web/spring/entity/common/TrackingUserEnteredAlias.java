package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An alias entered in the tracking system (table TRACKING_USER_ENTERED_ALIAS); the columns the tracking alias document
 * screen reads (the table is also written by the document edit, still in MyBatis).
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (the screen reads one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "TRACKING_USER_ENTERED_ALIAS")
public class TrackingUserEnteredAlias {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "ALIAS_ID")
	private Integer aliasId;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "REPSRCCD")
	private String repsrccd;

	@Column(name = "ALIAS_NO")
	private Integer aliasNo;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "SUFFIX")
	private String suffix;

	/** The legacy id (NYEHMS_USERS.LEGACY_UID) of the worker who entered it. */
	@Column(name = "CREATEID")
	private String createid;

	@Column(name = "CREATEDT")
	private Date createdt;

	@Column(name = "UPDATE_UID")
	private String updateUid;

	@Column(name = "UPDATE_DT")
	private Date updateDt;

	@Column(name = "COMMENTS")
	private String comments;
}
