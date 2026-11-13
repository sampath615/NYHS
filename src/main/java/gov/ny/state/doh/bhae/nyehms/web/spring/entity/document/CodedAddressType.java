package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The address types (table CODED_ADDRESS_TYPE), read-only. <b>ADDRESS_TYPE_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ADDRESS_TYPE")
public class CodedAddressType {

	@Id
	@Column(name = "ADDRESS_TYPE_CODE")
	private String addressTypeCode;

	@Column(name = "ADDRESS_TYPE_DESCRIPTION")
	private String addressTypeDescription;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
