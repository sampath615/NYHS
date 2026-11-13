package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The marital statuses (table CODED_MARITAL_STATUS), read-only. <b>MARITAL_STATUS_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_MARITAL_STATUS")
public class CodedMaritalStatus {

	@Id
	@Column(name = "MARITAL_STATUS_CODE")
	private String maritalStatusCode;

	@Column(name = "MARITAL_STATUS_DESC")
	private String maritalStatusDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
