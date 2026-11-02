package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A partner of a legacy partner services assignment (view LEGACY_PS_PERSON_PARTNER_VW); the columns NYEHMS reads.
 * Read-only and read with projections only. <b>The key (PARTNRID, ASSIGNMENT_ID) is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "LEGACY_PS_PERSON_PARTNER_VW")
@IdClass(LegacyPsPersonPartnerId.class)
public class LegacyPsPersonPartner {

	@Id
	@Column(name = "PARTNRID")
	private Integer partnrid;

	@Id
	@Column(name = "ASSIGNMENT_ID")
	private Integer assignmentId;
}
