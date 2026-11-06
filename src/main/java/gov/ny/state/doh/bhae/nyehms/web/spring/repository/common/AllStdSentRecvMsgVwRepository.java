package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.AllStdSentRecvMsgVw;

/** The partner services messages sent and received (view ALL_STD_SENT_RECV_MSG_VW). */
public interface AllStdSentRecvMsgVwRepository extends JpaRepository<AllStdSentRecvMsgVw, String> {

	/** A message (a projection: the view has no key). */
	record SentMessage(String triggerMsgType, Timestamp messageEnteredDate, Timestamp returnMessageEnteredDate, String latestDispositionDesc) {
	}

	/** The messages of a person with the given process indicator (both must not be null), in database order. */
	List<SentMessage> findByCurrentStatenoAndProcessInd(String currentStateno, String processInd);
}
