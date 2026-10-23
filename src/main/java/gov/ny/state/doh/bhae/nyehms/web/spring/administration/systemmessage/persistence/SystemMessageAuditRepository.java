package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemMessageAuditRepository extends JpaRepository<SystemMessageAudit, Long> {

    /** History of one message, newest first (same order as the view). */
    List<SystemMessageAudit> findByMessageIdOrderByLastUpdatedDbDateDesc(Long messageId);
}
