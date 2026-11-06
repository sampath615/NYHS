package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A laboratory (table LAB_PHYSICAL_FACILITY): its CLIA number, name and active flag (the columns the case report
 * forms' laboratory list reads).
 * <p>
 * Read-only, used in JPQL projections only. <b>CLIA as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "LAB_PHYSICAL_FACILITY")
public class LabPhysicalFacility {

	@Id
	@Column(name = "CLIA")
	private String clia;

	@Column(name = "LAB_NAME")
	private String labName;

	@Column(name = "FLAG_ACTIVE")
	private String flagActive;
}
