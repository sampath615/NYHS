package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The review actions of the viral load review (code table CODED_VL_ASSIGN_REVIEW). Read-only and read through
 * projections only. <b>REVIEW_ACTION as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_VL_ASSIGN_REVIEW")
public class CodedVlAssignReview {

	@Id
	@Column(name = "REVIEW_ACTION")
	private String reviewAction;

	@Column(name = "REVIEW_DESC")
	private String reviewDesc;

	/** 'Y' for the actions offered in the drop-down. */
	@Column(name = "DROP_DOWN")
	private String dropDown;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
