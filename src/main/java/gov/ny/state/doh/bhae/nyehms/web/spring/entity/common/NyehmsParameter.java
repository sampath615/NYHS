package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The application parameters (table NYEHMS_PARAMETER): a name may have several values, each with its effective dates.
 * <p>
 * Read-only and read through projections only (a key that repeats does not matter then). <b>PARAM_NAME as the key
 * is assumed</b> only because JPA needs one; it repeats.
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_PARAMETER")
public class NyehmsParameter {

	@Id
	@Column(name = "PARAM_NAME")
	private String paramName;

	@Column(name = "PARAM_VALUE")
	private String paramValue;

	@Column(name = "EFFECTIVE_START_DATE")
	private Timestamp effectiveStartDate;

	@Column(name = "EFFECTIVE_END_DATE")
	private Timestamp effectiveEndDate;
}
