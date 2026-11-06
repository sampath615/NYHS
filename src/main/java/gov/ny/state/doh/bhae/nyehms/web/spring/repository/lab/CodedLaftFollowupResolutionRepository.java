package gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.CodedLaftFollowupResolution;

/** The LAFT follow-up resolutions (CODED_LAFT_FOLLOWUP_RESOLUTION). */
public interface CodedLaftFollowupResolutionRepository extends JpaRepository<CodedLaftFollowupResolution, Long> {

	/** The resolutions other than the given one (a NULL id is not listed, as with {@code !=}). */
	List<CodedLaftFollowupResolution> findByResolutionIdNot(Long resolutionId);
}
