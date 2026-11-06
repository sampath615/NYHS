package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.math.BigDecimal;

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
 * A drug score of a Sierra resistance response (table RAS_SI_DRUG_SCORE), as the Sierra job writes it and the RAS
 * document screen reads it (SierraResponseDAO, RASDocumentDAO).
 * <p>
 * Inserted by an HQL insert and read with projections only, never loaded as an entity, so the key is only for the
 * mapping: <b>(SEQ_RESULT_ID, GENE_SEQ_ID, DRUG_ID) as the key is assumed</b>. RESISTANCE_LEVEL is a number (the
 * screen compares it with numbers; the job passes the level as text, converted as Oracle converted it).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAS_SI_DRUG_SCORE")
@IdClass(RasSiDrugScoreId.class)
public class RasSiDrugScore {

	@Id
	@Column(name = "SEQ_RESULT_ID")
	private Integer seqResultId;

	@Id
	@Column(name = "GENE_SEQ_ID")
	private Integer geneSeqId;

	@Id
	@Column(name = "DRUG_ID")
	private Integer drugId;

	@Column(name = "DRUG_CODE")
	private String drugCode;

	@Column(name = "DRUG_CLASS_TEXT")
	private String drugClassText;

	@Column(name = "DRUG_CLASS")
	private String drugClass;

	@Column(name = "SCORE")
	private String score;

	@Column(name = "RESISTANCE_LEVEL")
	private BigDecimal resistanceLevel;

	@Column(name = "LEVEL_TEXT")
	private String levelText;

	@Column(name = "SIR")
	private String sir;

	@Column(name = "GENE_NAME")
	private String geneName;
}
