package gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.LaftClepAuditSurveyUpload;

/** The uploaded CLEP HIV survey rows (LAFT_CLEP_AUDIT_SURVEY_UPLOAD). */
public interface LaftClepAuditSurveyUploadRepository extends JpaRepository<LaftClepAuditSurveyUpload, Long> {
}
