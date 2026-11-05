package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A newborn screening (NBS) report as received (table RAW_NBS_DOC): the columns the NBS search reads and the NBS
 * review changes (NBSReviewDAO); a change writes only the changed columns.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document). <b>Assumed column types</b> (the table definition
 * is not in this repository): BIRTHDT is a date (the former search formatted it with TO_CHAR(BIRTHDT, 'MM/DD/YYYY'));
 * the record columns are text (the former SQL trimmed them); CURRENT_PROCESS_STATUS and the worker ids
 * (CLIMS_MATCH_UID, REVIEW_ID) are numbers.
 */
@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RAW_NBS_DOC")
public class RawNbsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "LABID")
	private String labid;

	@Column(name = "LABNO")
	private String labno;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MEDRECNO")
	private String medrecno;

	@Column(name = "BIRTHDT")
	private Timestamp birthdt;

	@Column(name = "BIRTHWT")
	private String birthwt;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "PATIENTID")
	private String patientid;

	@Column(name = "MEMO")
	private String memo;

	@Column(name = "CLIMS_MATCH_UID")
	private Long climsMatchUid;

	@Column(name = "CLIMS_MATCH_DT")
	private Timestamp climsMatchDt;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "REVIEW_COMMENTS")
	private String reviewComments;

	@Column(name = "REVIEW_ID")
	private Long reviewId;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	/** Saves a review: the new status, the reviewer, the time and the new review comments. */
	public void review(Integer currentProcessStatus, Long reviewId, Timestamp reviewDate, String reviewComments) {
		this.currentProcessStatus = currentProcessStatus;
		this.reviewId = reviewId;
		this.reviewDate = reviewDate;
		this.reviewComments = reviewComments;
	}

	/** Saves review comments: the reviewer, the time and the comments. */
	public void comment(Long reviewId, Timestamp reviewDate, String reviewComments) {
		this.reviewId = reviewId;
		this.reviewDate = reviewDate;
		this.reviewComments = reviewComments;
	}

	/** Matches the report to a CLIMS patient by manual review (status 16, MEMO 'MANUAL_REVIEW'). */
	public void matchClims(String patientid, Long climsMatchUid, Timestamp climsMatchDt, String reviewComments) {
		this.currentProcessStatus = 16;
		this.patientid = patientid;
		this.memo = "MANUAL_REVIEW";
		this.climsMatchUid = climsMatchUid;
		this.climsMatchDt = climsMatchDt;
		this.reviewComments = reviewComments;
	}
}
