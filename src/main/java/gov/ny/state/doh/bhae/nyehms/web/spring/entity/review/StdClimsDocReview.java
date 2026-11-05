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
 * The review columns of a standardized CLIMS lab report (table STD_CLIMS_DOC), which the NBS review reads and changes
 * (NBSReviewDAO). The CLIMS document screen reads the same table through the read-only entity
 * {@code entity.document.StdClimsDoc}; this one maps only the key and the review columns, and a change writes only the
 * changed columns.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (as for StdClimsDoc). REVIEW_ID is a worker id (a number).
 */
@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STD_CLIMS_DOC")
public class StdClimsDocReview {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "REVIEW_COMMENTS")
	private String reviewComments;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "REVIEW_ID")
	private Long reviewId;

	/** Saves review comments: the reviewer, the time and the comments. */
	public void comment(Long reviewId, Timestamp reviewDate, String reviewComments) {
		this.reviewId = reviewId;
		this.reviewDate = reviewDate;
		this.reviewComments = reviewComments;
	}
}
