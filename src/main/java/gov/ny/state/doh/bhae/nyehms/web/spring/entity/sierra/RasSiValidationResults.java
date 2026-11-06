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
 * A validation message of a Sierra resistance response (table RAS_SI_VALIDATION_RESULTS), as the Sierra job writes it
 * (SierraResponseDAO).
 * <p>
 * Inserted by HQL inserts only, never loaded as an entity: the key is only for the mapping (<b>assumed</b>; a response
 * may have several messages of one level).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAS_SI_VALIDATION_RESULTS")
@IdClass(RasSiValidationResultsId.class)
public class RasSiValidationResults {

	@Id
	@Column(name = "SEQ_RESULT_ID")
	private Integer seqResultId;

	@Id
	@Column(name = "VALIDATION_LEVEL")
	private String validationLevel;

	@Id
	@Column(name = "MESSAGE")
	private String message;
}
