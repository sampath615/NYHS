package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.GenSequence;

/** The gene sequences of the HIV lab documents (table GEN_SEQUENCE). */
public interface GenSequenceRepository extends JpaRepository<GenSequence, Integer> {

	/** The number of sequences of a lab document that are RAS eligible ('Y') and linked (VLINK_DATE set). */
	@Query("""
			select count(*) from GenSequence seq
			where seq.labDocId = ?1 and seq.rasElig = 'Y' and seq.vlinkDate is not null
			""")
	long countLinked(int labDocId);

	/**
	 * Sets RAS_ELIG and clears VLINK_DATE of every sequence of a lab document.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("update GenSequence seq set seq.rasElig = ?1, seq.vlinkDate = null where seq.labDocId = ?2")
	int changeRasElig(String rasElig, Integer labDocId);

	/**
	 * The RAS eligible sequences to parse: after a lab document id, or of a document marked for reprise ('R') in
	 * RAS_SIERRA_PROCESS_ERRORS; by lab document id, at most {@code limit} rows. Rows of [LAB_DOC_ID, GEN_SEQ].
	 */
	@Query("""
			select seq.labDocId, seq.genSeq from GenSequence seq
			where seq.rasElig = 'Y'
			and (seq.labDocId > ?1 or seq.labDocId in (select errors.documentId from RasSierraProcessErrors errors
					where errors.activeFlag = 'R'))
			order by seq.labDocId asc
			""")
	List<Object[]> findToParse(Integer afterLabDocId, Limit limit);
}
