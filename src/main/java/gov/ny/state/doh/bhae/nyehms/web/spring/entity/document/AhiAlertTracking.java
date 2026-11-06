package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * An AHI alert (table AHI_ALERT_TRACKING): read by the AHI alert screens, its PRF collection, follow-up completion
 * and review set by bulk updates (AHIDocumentDAO). The columns the former statements used (the unqualified columns of
 * the former joins belong to this table).
 * <p>
 * <b>AHI_ALERT_SEQ_ID as the key is assumed.</b> The worker columns (AHI_CREATE_UID, LAST_UPDATED_BY,
 * ALERT_COMPLETED_BY, AHI_ALERT_REVIEWED_BY) are assumed to be numbers: they are joined to NYEHMS_USERS.WORKER_ID and
 * were written with the worker id (an int).
 */
@Entity
@Getter
@Table(name = "AHI_ALERT_TRACKING")
public class AhiAlertTracking {

	@Id
	@Column(name = "AHI_ALERT_SEQ_ID")
	private Long ahiAlertSeqId;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "AHI_FLAG")
	private String ahiFlag;

	@Column(name = "AHI_DOCUMENT_ID")
	private Long ahiDocumentId;

	@Column(name = "AHI_CREATE_UID")
	private Long ahiCreateUid;

	@Column(name = "AHI_CREATE_DATE")
	private Timestamp ahiCreateDate;

	@Column(name = "AHI_ALERT_CODE")
	private String ahiAlertCode;

	@Column(name = "PRF_COLLECTION")
	private String prfCollection;

	@Column(name = "PRF_COLLECTION_DATE")
	private Timestamp prfCollectionDate;

	@Column(name = "PRF_FLAGGED_AHI")
	private String prfFlaggedAhi;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "ALERT_COMPLETED_BY")
	private Long alertCompletedBy;

	@Column(name = "ALERT_COMPLETED_DATE")
	private Timestamp alertCompletedDate;

	@Column(name = "AHI_ALERT_STATUS")
	private String ahiAlertStatus;

	@Column(name = "AHI_ALERT_REVIEWED_DATE")
	private Timestamp ahiAlertReviewedDate;

	@Column(name = "AHI_ALERT_REVIEWED_BY")
	private Long ahiAlertReviewedBy;
}
