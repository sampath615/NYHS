package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedAhiPrfFollowup;

/** The follow-up steps of an AHI alert (table CODED_AHI_PRF_FOLLOWUP). */
public interface CodedAhiPrfFollowupRepository extends JpaRepository<CodedAhiPrfFollowup, String> {

	/** A step with its description (a projection: a code that repeats gives several rows, as before). */
	record FollowUpStep(String followUpCode, String followUpDesc) {
	}

	/** The steps with the given DROP_DOWN flag, by SORT_ORDER. */
	List<FollowUpStep> findByDropDownOrderBySortOrder(String dropDown);
}
