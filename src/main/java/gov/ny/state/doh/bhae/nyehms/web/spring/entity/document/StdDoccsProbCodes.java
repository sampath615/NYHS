package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The problem codes of a DIN record, standardized (table STD_DOCCS_PROB_CODES): the columns the DIN DOCCS screen reads
 * and the DOCCS import copies from the received codes (DoccsDocumentDAO).
 * <p>
 * Read in JPQL; written by an HQL insert ... select only. The table has no known key: <b>the key (DIN_DOCCS_DOC_ID,
 * PROBLEM_CD) is assumed</b> (it is never loaded as an entity, so a repeated key does not matter).
 */
@Entity
@Getter
@Table(name = "STD_DOCCS_PROB_CODES")
@IdClass(StdDoccsProbCodes.Key.class)
public class StdDoccsProbCodes {

	/** The assumed key. */
	public record Key(Long dinDoccsDocId, String problemCd) implements Serializable {
	}

	@Id
	@Column(name = "DIN_DOCCS_DOC_ID")
	private Long dinDoccsDocId;

	@Id
	@Column(name = "PROBLEM_CD")
	private String problemCd;

	@Column(name = "PROBLEM_CD_DATE")
	private String problemCdDate;

	@Column(name = "HIV_DIAG")
	private BigDecimal hivDiag;

	@Column(name = "HIV_DIAG_DATE_TEXT")
	private String hivDiagDateText;

	@Column(name = "AIDS_DIAG")
	private BigDecimal aidsDiag;

	@Column(name = "AIDS_DIAG_DATE_TEXT")
	private String aidsDiagDateText;
}
