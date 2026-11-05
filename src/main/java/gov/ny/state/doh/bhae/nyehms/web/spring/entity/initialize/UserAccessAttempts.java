package gov.ny.state.doh.bhae.nyehms.web.spring.entity.initialize;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A login attempt (table USER_ACCESS_ATTEMPTS), inserted by InitializeDAO. Its key comes from
 * USER_ACCESS_ATTEMPTS_SEQ, one value per row as the former insert took it.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "USER_ACCESS_ATTEMPTS")
public class UserAccessAttempts {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ACCESS_ATTEMPTS_SEQ")
	@SequenceGenerator(name = "USER_ACCESS_ATTEMPTS_SEQ", sequenceName = "USER_ACCESS_ATTEMPTS_SEQ", allocationSize = 1)
	@Column(name = "ACCESS_ATTEMPTS_ID")
	private Long accessAttemptsId;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "HCS_ROLE")
	private String hcsRole;

	@Column(name = "LINK_CLICK_TIME")
	private Timestamp linkClickTime;

	@Column(name = "ACTIVE_MAC_ADDRESS_LIST")
	private String activeMacAddressList;

	@Column(name = "HAS_NYEHMS_ACCESS")
	private String hasNyehmsAccess;

	@Column(name = "USER_LOCKED")
	private String userLocked;

	@Column(name = "ATTEMPT_MAC_ADDRESS")
	private String attemptMacAddress;

	@Column(name = "BROWSER_USED")
	private String browserUsed;

	@Column(name = "BROWSER_VERSION")
	private String browserVersion;

	@Column(name = "HAS_XPI_PLUGIN")
	private String hasXpiPlugin;

	@Column(name = "XPI_VERSION")
	private String xpiVersion;

	@Column(name = "OS_USED")
	private String osUsed;

	@Column(name = "JRE_VERSION")
	private String jreVersion;

	@Column(name = "RECORD_CREATED_DATE")
	private Timestamp recordCreatedDate;

	@Column(name = "REASONS_ATTEMPT_FAILED")
	private String reasonsAttemptFailed;
}
