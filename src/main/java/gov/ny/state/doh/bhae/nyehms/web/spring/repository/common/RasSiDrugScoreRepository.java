package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RasSiDrugScore;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RasSiDrugScoreId;

/** The drug scores of the Sierra resistance responses (table RAS_SI_DRUG_SCORE). */
public interface RasSiDrugScoreRepository extends JpaRepository<RasSiDrugScore, RasSiDrugScoreId> {

	/**
	 * The drug classes of a response with a resistance: level above 3 for PI, NRTI and NNRTI, above 2 for INSTI (each
	 * class once).
	 */
	@Query("""
			select distinct score.drugClass from RasSiDrugScore score
			where ((score.resistanceLevel > 3 and score.drugClass in ('PI', 'NRTI', 'NNRTI'))
					or (score.resistanceLevel > 2 and score.drugClass in ('INSTI')))
			and score.seqResultId = ?1
			""")
	List<String> resistantDrugClasses(int seqResultId);

	/** Adds a drug score (an HQL insert: the row is written at once, as the former insert). */
	@Modifying
	@Query("""
			insert into RasSiDrugScore (seqResultId, geneSeqId, drugId, drugCode, drugClassText, drugClass, score,
					resistanceLevel, levelText, sir, geneName)
			values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11)
			""")
	int insertScore(Integer seqResultId, Integer geneSeqId, Integer drugId, String drugCode, String drugClassText,
			String drugClass, String score, BigDecimal resistanceLevel, String levelText, String sir, String geneName);
}
