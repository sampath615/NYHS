package gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSiDrugPartialScore;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSiDrugPartialScoreId;

/** The partial scores of the drugs of the Sierra resistance responses (table RAS_SI_DRUG_PARTIAL_SCORE). */
public interface RasSiDrugPartialScoreRepository extends JpaRepository<RasSiDrugPartialScore, RasSiDrugPartialScoreId> {

	/** Adds a partial score (an HQL insert: the table has no known key). */
	@Modifying
	@Query("insert into RasSiDrugPartialScore (seqResultId, drugId, mutation, partialScore) values (?1, ?2, ?3, ?4)")
	int insertPartialScore(Integer seqResultId, Integer drugId, String mutation, String partialScore);
}
