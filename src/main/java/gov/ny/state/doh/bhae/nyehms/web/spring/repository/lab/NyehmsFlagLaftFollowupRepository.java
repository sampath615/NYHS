package gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.NyehmsFlagLaftFollowup;

/** The documents flagged for LAFT follow-up (NYEHMS_FLAG_LAFT_FOLLOWUP). */
public interface NyehmsFlagLaftFollowupRepository extends JpaRepository<NyehmsFlagLaftFollowup, Long> {

	/** The number of a document's follow-ups that are not resolved. */
	long countByDocumentIdAndResolutionIdIsNull(Long documentId);
}
