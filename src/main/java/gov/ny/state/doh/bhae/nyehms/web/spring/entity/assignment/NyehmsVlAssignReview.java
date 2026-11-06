package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The state numbers flagged for the viral load review (table NYEHMS_VL_ASSIGN_REVIEW) and their review.
 * <p>
 * Read through projections and changed by a JPQL bulk update only (no entity is loaded or saved).
 * <b>STATENO as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "NYEHMS_VL_ASSIGN_REVIEW")
public class NyehmsVlAssignReview {

	@Id
	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "FLAGGED_DATE")
	private Timestamp flaggedDate;

	/** The review action (CODED_VL_ASSIGN_REVIEW.REVIEW_ACTION). */
	@Column(name = "REVIEW_ACTION")
	private String reviewAction;

	/** The worker id of the reviewer. */
	@Column(name = "REVIEW_BY")
	private Long reviewBy;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "REVIEW_COMMENTS")
	private String reviewComments;
}
