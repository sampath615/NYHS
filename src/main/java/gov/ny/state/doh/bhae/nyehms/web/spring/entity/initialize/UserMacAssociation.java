package gov.ny.state.doh.bhae.nyehms.web.spring.entity.initialize;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The MAC addresses a worker may log in from (table USER_MAC_ASSOCIATION; from the removed applet login check).
 * <p>
 * Read-only and read through projections only (a key that repeats does not matter then). <b>MAC_ADDRESS as the key
 * is assumed</b> only because JPA needs one.
 */
@Entity
@Immutable
@Getter
@Table(name = "USER_MAC_ASSOCIATION")
public class UserMacAssociation {

	@Id
	@Column(name = "MAC_ADDRESS")
	private String macAddress;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "EFFECTIVE_START_DATE")
	private Timestamp effectiveStartDate;

	@Column(name = "EFFECTIVE_END_DATE")
	private Timestamp effectiveEndDate;
}
