package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The reabstraction statuses (code table CODED_REAB_STATUS). Read-only and read through projections only.
 * <b>REAB_STATUS_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_REAB_STATUS")
public class CodedReabStatus {

	@Id
	@Column(name = "REAB_STATUS_CODE")
	private String reabStatusCode;

	@Column(name = "REAB_STATUS_CODE_DESC")
	private String reabStatusCodeDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
