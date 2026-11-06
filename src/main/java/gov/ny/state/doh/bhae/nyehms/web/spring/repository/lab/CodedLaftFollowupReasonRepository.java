package gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.CodedLaftFollowupReason;

/** The LAFT follow-up reasons (CODED_LAFT_FOLLOWUP_REASON). */
public interface CodedLaftFollowupReasonRepository extends JpaRepository<CodedLaftFollowupReason, Long> {
}
