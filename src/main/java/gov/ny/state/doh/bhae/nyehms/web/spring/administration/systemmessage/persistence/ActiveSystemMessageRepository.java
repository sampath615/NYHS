package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActiveSystemMessageRepository extends JpaRepository<ActiveSystemMessage, Long> {

    /**
     * Texts for the scrolling banner: active messages that have already started.
     * Native on purpose: compares with the database clock (SYSDATE), exactly like the old SQL,
     * so the app server's clock/time zone can never make a message start early or late.
     */
    @Query(value = "SELECT MSG_BODY FROM NYEHMS_ACTIVE_SYSTEM_MSG_VW WHERE SYSDATE > EFFECTIVE_START_DATE"
            + " ORDER BY MSG_ID DESC, LAST_UPDATED_DATE DESC", nativeQuery = true)
    List<String> findStartedMessageBodies();
}
