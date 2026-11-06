package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The partner services messages sent and received (view ALL_STD_SENT_RECV_MSG_VW), read-only. The columns the former
 * AHI statement used.
 * <p>
 * <b>The view has no known key</b>: CURRENT_STATENO is declared as the id, but it repeats; the view is read through
 * projections only.
 */
@Entity
@Immutable
@Getter
@Table(name = "ALL_STD_SENT_RECV_MSG_VW")
public class AllStdSentRecvMsgVw {

	@Id
	@Column(name = "CURRENT_STATENO")
	private String currentStateno;

	@Column(name = "TRIGGER_MSG_TYPE")
	private String triggerMsgType;

	@Column(name = "MESSAGE_ENTERED_DATE")
	private Timestamp messageEnteredDate;

	@Column(name = "RETURN_MESSAGE_ENTERED_DATE")
	private Timestamp returnMessageEnteredDate;

	@Column(name = "LATEST_DISPOSITION_DESC")
	private String latestDispositionDesc;

	@Column(name = "PROCESS_IND")
	private String processInd;
}
