package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedAhiCiReasonTested;

/** The reasons a patient was tested (table CODED_AHI_CI_REASON_TESTED). */
public interface CodedAhiCiReasonTestedRepository extends JpaRepository<CodedAhiCiReasonTested, String> {

	/** A code with its description (a projection: a code that repeats gives several rows, as before). */
	record TestedReason(String testedCode, String testedDesc) {
	}

	/** All codes, in database order. */
	List<TestedReason> findAllBy();
}
