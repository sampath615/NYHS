package gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra;

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
 * A partial score of a drug of a Sierra resistance response (table RAS_SI_DRUG_PARTIAL_SCORE), as the Sierra job writes
 * it (SierraResponseDAO).
 * <p>
 * Inserted by HQL inserts only, never loaded as an entity: the key is only for the mapping (<b>assumed</b>).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAS_SI_DRUG_PARTIAL_SCORE")
@IdClass(RasSiDrugPartialScoreId.class)
public class RasSiDrugPartialScore {

	@Id
	@Column(name = "SEQ_RESULT_ID")
	private Integer seqResultId;

	@Id
	@Column(name = "DRUG_ID")
	private Integer drugId;

	@Id
	@Column(name = "MUTATION")
	private String mutation;

	@Column(name = "PARTIAL_SCORE")
	private String partialScore;
}
