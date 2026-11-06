package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An activity done on a surveillance assignment (table ASSIGNMENT_ACTIVITY): its type, comments, date and worker, as
 * AssignmentDAO.addAssignmentActivity writes it.
 * <p>
 * Inserted with its key from ASSIGNMENT_ACTIVITY_SEQ (as the former insert took assignment_activity_seq.NEXTVAL);
 * ACTIVITY_DATE is the database's SYSDATE (left null), as before. <b>ASSIGNMENT_ACTIVITY_ID as the key is assumed</b>
 * (filled from the sequence).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "ASSIGNMENT_ACTIVITY")
public class AssignmentActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ASSIGNMENT_ACTIVITY_SEQ")
	@SequenceGenerator(name = "ASSIGNMENT_ACTIVITY_SEQ", sequenceName = "ASSIGNMENT_ACTIVITY_SEQ", allocationSize = 1)
	@Column(name = "ASSIGNMENT_ACTIVITY_ID")
	private Long assignmentActivityId;

	@Column(name = "ACTIVITY_TYPE_CODE")
	private String activityTypeCode;

	@Column(name = "COMMENTS")
	private String comments;

	/** SYSDATE when inserted (left null). */
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "ACTIVITY_DATE")
	private Timestamp activityDate;

	/** The worker id. */
	@Column(name = "ACTIVITY_DONE_BY")
	private Long activityDoneBy;

	/** The assignment (SURVEILLANCE_ASSIGNMENT.DOCUMENT_ID). */
	@Column(name = "DOCUMENT_ID")
	private Long documentId;
}
