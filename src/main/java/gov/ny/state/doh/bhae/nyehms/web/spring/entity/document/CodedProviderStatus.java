package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The partner assistance (provider) statuses (table CODED_PROVIDER_STATUS), read-only. <b>PROV_STATUS_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PROVIDER_STATUS")
public class CodedProviderStatus {

	@Id
	@Column(name = "PROV_STATUS_CODE")
	private String provStatusCode;

	@Column(name = "PROV_STATUS_DESC")
	private String provStatusDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
