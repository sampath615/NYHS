package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The assignment statuses (code table CODED_ASSIGNMENT_STATUS). Read-only and read through projections only.
 * <b>STATUS_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ASSIGNMENT_STATUS")
public class CodedAssignmentStatus {

	@Id
	@Column(name = "STATUS_CD")
	private String statusCd;

	@Column(name = "STATUS_DESCRIPTION")
	private String statusDescription;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
