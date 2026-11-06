package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The approval statuses of the labs (table CODED_LAB_APPROVAL_STATUS).
 * <p>
 * Read-only. <b>APPROVAL_STATUS_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAB_APPROVAL_STATUS")
public class CodedLabApprovalStatus {

	@Id
	@Column(name = "APPROVAL_STATUS_CODE")
	private Long approvalStatusCode;

	@Column(name = "APPROVAL_STATUS_DESC")
	private String approvalStatusDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
