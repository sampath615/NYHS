package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedSexualBehaviors;

/** The sexual behavior codes (table CODED_SEXUAL_BEHAVIORS). */
public interface CodedSexualBehaviorsRepository extends JpaRepository<CodedSexualBehaviors, String> {

	/** A code with its description (a projection: a code that repeats gives several rows, as before). */
	record SexualBehaviorCode(String sexualBehaviorCode, String sexualBehaviorDesc) {
	}

	/** All codes, in database order. */
	List<SexualBehaviorCode> findAllBy();
}
