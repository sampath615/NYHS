package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedActivePassive;

/** The active / passive answers (table CODED_ACTIVE_PASSIVE). */
public interface CodedActivePassiveRepository extends JpaRepository<CodedActivePassive, String> {

	/** An answer with its description (a projection: a code that repeats gives several rows, as before). */
	record Answer(String ansCode, String ansDesc) {
	}

	/** The answers whose code is not the given one (a null code is left out, as SQL's != leaves it out). */
	List<Answer> findByAnsCodeNot(String ansCode);
}
