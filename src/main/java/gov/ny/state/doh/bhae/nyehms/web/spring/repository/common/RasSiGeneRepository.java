package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RasSiGene;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RasSiGeneId;

/** The genes of the Sierra resistance responses (table RAS_SI_GENE). */
public interface RasSiGeneRepository extends JpaRepository<RasSiGene, RasSiGeneId> {

	/**
	 * The gene names of a response joined with '_' (the HQL listagg, rendered as the database's LISTAGG ... WITHIN GROUP,
	 * ordered by SEQ_RESULT_ID as before); null if there are none.
	 */
	@Query("""
			select listagg(gene.geneName, '_') within group (order by gene.seqResultId)
			from RasSiGene gene where gene.seqResultId = ?1
			""")
	String geneNames(int seqResultId);

	/** Adds a gene (an HQL insert: the row is written at once, as the former insert). */
	@Modifying
	@Query("""
			insert into RasSiGene (seqResultId, geneSeqId, geneName, alignedNaSeq, alignedAaSeq, firstaa, lastaa, firstna,
					lastna, matchPct)
			values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10)
			""")
	int insertGene(Integer seqResultId, Integer geneSeqId, String geneName, String alignedNaSeq, String alignedAaSeq,
			String firstaa, String lastaa, String firstna, String lastna, String matchPct);
}
