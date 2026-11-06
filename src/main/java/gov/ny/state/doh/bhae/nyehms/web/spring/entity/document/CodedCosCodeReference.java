package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The Medicaid categories of service (table CODED_COS_CODE_REFERENCE); only the columns the Medicaid document screen
 * reads.
 * <p>
 * Read-only and read through JPQL projections only. <b>PROV_COS_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_COS_CODE_REFERENCE")
public class CodedCosCodeReference {

	@Id
	@Column(name = "PROV_COS_CODE")
	private String provCosCode;

	@Column(name = "PROV_COS_CODE_DESC")
	private String provCosCodeDesc;
}
