package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A STATENO flagged for a CDC record search (table NYEHMS_FLAG_RECORD_SEARCH): who flagged it, when it was unflagged,
 * and the CDC file it was sent and returned in (RecordSearchReviewDAO).
 * <p>
 * Inserted by HQL inserts, changed by JPQL bulk updates and read with projections only, never loaded as an entity, so
 * the key is only for the mapping: <b>(STATENO, CREATE_DATE) as the key is assumed</b>. The worker id columns
 * (CREATE_UID, UNFLAG_UID, GENERATE_UID, UPLOAD_UID) are numbers (they are joined with NYEHMS_USERS.WORKER_ID).
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_FLAG_RECORD_SEARCH")
@IdClass(NyehmsFlagRecordSearchId.class)
public class NyehmsFlagRecordSearch {

	@Id
	@Column(name = "STATENO")
	private String stateno;

	@Id
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "FLAG_ORIGIN")
	private String flagOrigin;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "CREATE_UID")
	private Long createUid;

	@Column(name = "UNFLAG_UID")
	private Long unflagUid;

	@Column(name = "UNFLAG_COMMENTS")
	private String unflagComments;

	@Column(name = "UNFLAG_DATE")
	private Timestamp unflagDate;

	@Column(name = "NO_CDC_DATA")
	private String noCdcData;

	@Column(name = "SENT_FILE_NAME")
	private String sentFileName;

	@Column(name = "GENERATE_UID")
	private Long generateUid;

	@Column(name = "GENERATE_DATE")
	private Timestamp generateDate;

	@Column(name = "UPLOAD_UID")
	private Long uploadUid;

	@Column(name = "UPLOAD_DATE")
	private Timestamp uploadDate;
}
