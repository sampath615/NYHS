package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.ActiveSystemMessageView;

/** The active system messages (view NYEHMS_ACTIVE_SYSTEM_MSG_VW). */
public interface ActiveSystemMessageViewRepository extends JpaRepository<ActiveSystemMessageView, Long> {

	/** The messages already in effect at a time (effective start before it). */
	List<ActiveSystemMessageView> findByEffectiveStartDateBefore(Timestamp time);
}
