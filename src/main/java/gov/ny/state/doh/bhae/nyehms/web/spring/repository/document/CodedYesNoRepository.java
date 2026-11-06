package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedYesNo;

/** The yes / no answers (table CODED_YES_NO). */
public interface CodedYesNoRepository extends JpaRepository<CodedYesNo, String> {

	/** An answer with its description (a projection: a code that repeats gives several rows, as before). */
	record Answer(String ansCode, String ansDesc) {
	}

	/** All answers, in database order. */
	List<Answer> findAllBy();
}
