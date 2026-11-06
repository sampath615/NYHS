package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The activity (closure) types (code table CODED_ACTIVITY_TYPE): code and description, as the non-case closure review
 * reads them (NonCaseDocumentDAO).
 * <p>
 * Read-only and read with projections only. <b>ACTIVITY_TYPE_CODE as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ACTIVITY_TYPE")
public class CodedActivityType {

	@Id
	@Column(name = "ACTIVITY_TYPE_CODE")
	private String activityTypeCode;

	@Column(name = "ACTIVITY_TYPE_DESCRIPTION")
	private String activityTypeDescription;

	// the columns the assignment closure and reabstraction lists read (AssignmentDAO)

	@Column(name = "ACTIVITY_CATEGORY_CODE")
	private String activityCategoryCode;

	@Column(name = "ASSIGNMENT_TYPE_CD")
	private String assignmentTypeCd;

	@Column(name = "ACCESS_LEVEL")
	private String accessLevel;

	@Column(name = "NEED_ACRF")
	private String needAcrf;

	@Column(name = "END_DATE")
	private java.sql.Timestamp endDate;
}
