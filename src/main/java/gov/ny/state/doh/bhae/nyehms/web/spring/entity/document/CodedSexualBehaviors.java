package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The sexual behavior codes of the partner services document (table CODED_SEXUAL_BEHAVIORS).
 * <p>
 * Read-only and read through projections only (a key that repeats does not matter then). <b>SEXUAL_BEHAVIOR_CODE as
 * the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_SEXUAL_BEHAVIORS")
public class CodedSexualBehaviors {

	@Id
	@Column(name = "SEXUAL_BEHAVIOR_CODE")
	private String sexualBehaviorCode;

	@Column(name = "SEXUAL_BEHAVIOR_DESC")
	private String sexualBehaviorDesc;
}
