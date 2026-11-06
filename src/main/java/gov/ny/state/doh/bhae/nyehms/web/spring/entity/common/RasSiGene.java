package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A gene of a Sierra resistance response (table RAS_SI_GENE), as the Sierra job writes it and the RAS document screen
 * reads it (SierraResponseDAO, RASDocumentDAO).
 * <p>
 * Inserted by an HQL insert and read with projections only, never loaded as an entity, so the key is only for the
 * mapping: <b>(SEQ_RESULT_ID, GENE_SEQ_ID) as the key is assumed</b>.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAS_SI_GENE")
@IdClass(RasSiGeneId.class)
public class RasSiGene {

	@Id
	@Column(name = "SEQ_RESULT_ID")
	private Integer seqResultId;

	@Id
	@Column(name = "GENE_SEQ_ID")
	private Integer geneSeqId;

	@Column(name = "GENE_NAME")
	private String geneName;

	@Column(name = "ALIGNED_NA_SEQ")
	private String alignedNaSeq;

	@Column(name = "ALIGNED_AA_SEQ")
	private String alignedAaSeq;

	@Column(name = "FIRSTAA")
	private String firstaa;

	@Column(name = "LASTAA")
	private String lastaa;

	@Column(name = "FIRSTNA")
	private String firstna;

	@Column(name = "LASTNA")
	private String lastna;

	@Column(name = "MATCH_PCT")
	private String matchPct;
}
