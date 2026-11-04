package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The application's workers (table NYEHMS_USERS): their names.
 * <p>
 * Read-only and read through projections only (a key that repeats does not matter then). <b>WORKER_ID as the key is
 * assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_USERS")
public class NyehmsUsers {

	@Id
	@Column(name = "WORKER_ID")
	private Long workerId;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "FIRST_NAME")
	private String firstName;
}
