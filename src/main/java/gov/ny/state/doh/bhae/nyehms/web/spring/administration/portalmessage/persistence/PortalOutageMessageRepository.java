package gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * The two lists use the database clock (SYSDATE), like the old SQL, so they are native queries.
 * Ordered by MSG_ID (the old SQL had no ORDER BY, which in practice gave insertion order).
 */
public interface PortalOutageMessageRepository extends JpaRepository<PortalOutageMessage, Long> {

    /** Not deleted and not ended (no end date, or end date still ahead). */
    @Query(value = "SELECT * FROM PORTAL_MODULE_OUTAGE WHERE FLAG_DELETED = 'N'"
            + " AND (END_DATE IS NULL OR END_DATE > SYSDATE) ORDER BY MSG_ID", nativeQuery = true)
    List<PortalOutageMessage> findActive();

    /** Deleted or already ended. */
    @Query(value = "SELECT * FROM PORTAL_MODULE_OUTAGE WHERE FLAG_DELETED = 'Y' OR SYSDATE > END_DATE"
            + " ORDER BY MSG_ID", nativeQuery = true)
    List<PortalOutageMessage> findInactive();
}
