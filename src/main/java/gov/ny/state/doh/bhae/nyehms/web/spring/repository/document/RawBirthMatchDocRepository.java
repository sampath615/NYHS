package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawBirthMatchDoc;

/** The birth records received for matching (table RAW_BIRTH_MATCH_DOC). */
public interface RawBirthMatchDocRepository extends JpaRepository<RawBirthMatchDoc, String> {

	/** The record(s) with this BIRTH_MATCH_PK (compared as the text given, as before). */
	List<RawBirthMatchDoc> findAllByBirthMatchPk(String birthMatchPk);
}
