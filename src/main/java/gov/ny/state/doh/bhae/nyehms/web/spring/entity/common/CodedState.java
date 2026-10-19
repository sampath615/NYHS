package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The states (table CODED_STATE): code and name; the columns the application reads.
 * <p>
 * Read-only and used in JPQL projections only. <b>STATE_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_STATE")
public class CodedState {

	@Id
	@Column(name = "STATE_CODE")
	private String stateCode;

	@Column(name = "STATE_DESCRIPTION")
	private String stateDescription;
}
