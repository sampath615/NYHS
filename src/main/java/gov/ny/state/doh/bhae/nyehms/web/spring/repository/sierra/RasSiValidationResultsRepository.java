package gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSiValidationResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSiValidationResultsId;

/** The validation messages of the Sierra resistance responses (table RAS_SI_VALIDATION_RESULTS). */
public interface RasSiValidationResultsRepository extends JpaRepository<RasSiValidationResults, RasSiValidationResultsId> {

	/** Adds a message (an HQL insert: the table has no known key and a message may repeat). */
	@Modifying
	@Query("insert into RasSiValidationResults (seqResultId, validationLevel, message) values (?1, ?2, ?3)")
	int insertResult(Integer seqResultId, String validationLevel, String message);
}
