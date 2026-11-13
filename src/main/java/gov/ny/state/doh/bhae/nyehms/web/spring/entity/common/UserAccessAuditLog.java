package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A worker's acceptance of the access agreement (table USER_ACCESS_AUDIT_LOG): the worker and when. Written by an
 * HQL insert only (CommonDAO.insertNyehmsAgreedTime); the table has no known key, so <b>WORKER_ID as the key is
 * assumed</b> (a worker can have several rows, which the insert does not mind).
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_ACCESS_AUDIT_LOG")
public class UserAccessAuditLog {

	@Id
	@Column(name = "WORKER_ID")
	private Long workerId;

	@Column(name = "AGREED_DATE")
	private Timestamp agreedDate;
}
