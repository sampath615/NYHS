package gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.LaftClepAuditSurveyForm;

/** The CLEP audit survey forms (LAFT_CLEP_AUDIT_SURVEY_FORM). */
public interface LaftClepAuditSurveyFormRepository extends JpaRepository<LaftClepAuditSurveyForm, Long> {
}
