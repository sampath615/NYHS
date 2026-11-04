package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.SystemMessageHistoryView;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SystemMessageHistoryRow;

/** The earlier versions of the system messages (view NYEHMS_SYSTEM_MSG_HISTORY_VW). */
public interface SystemMessageHistoryViewRepository extends JpaRepository<SystemMessageHistoryView, Long> {

	/** The versions of one message, each row as it is (a projection: the view has several rows per message). */
	List<SystemMessageHistoryRow> findByMsgId(Long msgId);
}
