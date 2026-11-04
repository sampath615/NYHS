package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.InactiveSystemMessageView;

/** The inactive system messages (view NYEHMS_INACTIVE_SYSTEM_MSG_VW). */
public interface InactiveSystemMessageViewRepository extends JpaRepository<InactiveSystemMessageView, Long> {
}
