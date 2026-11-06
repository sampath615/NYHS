package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The ARV use types of the adult case report form (table CODED_ACRF_ARVS).
 * <p>
 * Read-only, used in JPQL projections only. <b>ARV_USE_TYPE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ACRF_ARVS")
public class CodedAcrfArvs {

	@Id
	@Column(name = "ARV_USE_TYPE")
	private String arvUseType;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
