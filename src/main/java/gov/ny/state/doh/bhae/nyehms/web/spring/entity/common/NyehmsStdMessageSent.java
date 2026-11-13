package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

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
 * A partner services (PS) message sent for a document (table NYEHMS_STD_MESSAGE_SENT): the review columns the PS
 * message review screen changes (PSMessageReviewDAO).
 * <p>
 * <b>DOCUMENT_ID as the key is assumed.</b> Only the columns the review reads and writes are mapped; a change writes
 * only the changed columns. REVIEW_UID is text (the former SQL compared it with '900' and 'X'); LAFT_REVIEW_BY is a
 * worker id (joined with NYEHMS_USERS.WORKER_ID).
 */
@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_STD_MESSAGE_SENT")
public class NyehmsStdMessageSent {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "FOLLOW_UP_DOC_ID")
	private Long followUpDocId;

	@Column(name = "PROCESS_IND")
	private String processInd;

	/** The person the message is about (read only: the AHI alert list checks whether a message was sent). */
	@Column(name = "STATENO", insertable = false, updatable = false)
	private String stateno;

	@Column(name = "DELETE_IND")
	private String deleteInd;

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "LAFT_COMMENTS")
	private String laftComments;

	@Column(name = "LAFT_REASONS")
	private String laftReasons;

	@Column(name = "LAFT_REVIEW_BY")
	private Long laftReviewBy;

	@Column(name = "LAFT_REVIEW_DATE")
	private Timestamp laftReviewDate;

	/** The message type that was triggered (the person screen lists the types of a document; never changed here). */
	@Column(name = "TRIGGER_MSG_TYPE")
	private String triggerMsgType;

	/** Set by the bulk JPQL update that retires the pending messages of a deleted document (PersonDAO). */
	@Column(name = "REVIEW_COMMENTS")
	private String reviewComments;

	/** 'Y' once the follow-up document was replaced by an edited copy (CommonDocLabsDAO, a bulk update). */
	@Column(name = "EDITED_FLAG")
	private String editedFlag;

	/** Deletes the open message: PROCESS_IND 'R_X', DELETE_IND 'D', reviewed by the worker at the time. */
	public void delete(String reviewUid, Timestamp reviewDate) {
		this.processInd = "R_X";
		this.deleteInd = "D";
		this.reviewUid = reviewUid;
		this.reviewDate = reviewDate;
	}

	/** Processes the open message: PROCESS_IND 'Y', reviewed by the worker at the time. */
	public void process(String reviewUid, Timestamp reviewDate) {
		this.processInd = "Y";
		this.reviewUid = reviewUid;
		this.reviewDate = reviewDate;
	}

	/** Saves the LAFT review of the message. */
	public void laftReview(String laftComments, String laftReasons, Long laftReviewBy, Timestamp laftReviewDate) {
		this.laftComments = laftComments;
		this.laftReasons = laftReasons;
		this.laftReviewBy = laftReviewBy;
		this.laftReviewDate = laftReviewDate;
	}
}
