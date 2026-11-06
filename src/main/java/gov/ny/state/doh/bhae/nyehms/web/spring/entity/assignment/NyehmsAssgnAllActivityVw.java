package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The activities of the assignments with their descriptions and workers (view NYEHMS_ASSGN_ALL_ACTIVITY_VW): the
 * columns the assignment comment lists read.
 * <p>
 * Read-only and read through projections only, so a key that repeats does not matter. <b>DOCUMENT_ID as the key is
 * assumed</b> (it is not one: an assignment has many activities).
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_ASSGN_ALL_ACTIVITY_VW")
public class NyehmsAssgnAllActivityVw {

	/** The assignment. */
	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "ACTIVITY_DATE")
	private Timestamp activityDate;

	@Column(name = "ACTIVITY_DONE_BY")
	private String activityDoneBy;

	@Column(name = "ACTIVITY_DONE_BY_SHORT_NAME")
	private String activityDoneByShortName;

	@Column(name = "ACTIVITY_TYPE_CODE")
	private String activityTypeCode;

	@Column(name = "ACTIVITY_TYPE_DESCRIPTION")
	private String activityTypeDescription;

	@Column(name = "COMMENTS")
	private String comments;
}
