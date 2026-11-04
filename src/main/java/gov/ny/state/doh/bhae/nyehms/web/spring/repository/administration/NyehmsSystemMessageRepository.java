package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.NyehmsSystemMessage;

/** The system messages (table NYEHMS_SYSTEM_MESSAGES). */
public interface NyehmsSystemMessageRepository extends JpaRepository<NyehmsSystemMessage, Long> {
}
