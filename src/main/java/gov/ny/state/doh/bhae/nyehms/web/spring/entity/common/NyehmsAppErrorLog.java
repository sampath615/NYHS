package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An application error written to the database (table NYEHMS_APP_ERROR_LOG) by the exception handling and the HIT
 * jobs (CommonDAO.writeLogToDB): the key from APP_ERROR_LOG_SEQ (as the former insert's selectKey), the user, the
 * session, the message, the error and the stack trace (CLOBs). Only written.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "NYEHMS_APP_ERROR_LOG")
public class NyehmsAppErrorLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appErrorLogSeq")
	@SequenceGenerator(name = "appErrorLogSeq", sequenceName = "APP_ERROR_LOG_SEQ", allocationSize = 1)
	@Column(name = "APP_LOG_ID")
	private Long appLogId;

	@Column(name = "APP_LOG_DATE")
	private Timestamp appLogDate;

	@Column(name = "APP_USER_ID")
	private String appUserId;

	@Column(name = "APP_SESSION_ID")
	private String appSessionId;

	@Lob
	@Column(name = "APP_LOG_MSG")
	private String appLogMsg;

	@Lob
	@Column(name = "APP_LOG_ERROR")
	private String appLogError;

	@Lob
	@Column(name = "APP_LOG_STACKTRACE")
	private String appLogStacktrace;
}
