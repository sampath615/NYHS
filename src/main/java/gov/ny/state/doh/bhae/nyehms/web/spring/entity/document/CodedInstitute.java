package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The institutions of the death records by range of institution codes (table CODED_INSTITUTE); the columns the
 * death import reads.
 * <p>
 * Read-only and used in JPQL projections only. <b>INS_CODE_START as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_INSTITUTE")
public class CodedInstitute {

	@Id
	@Column(name = "INS_CODE_START")
	private String insCodeStart;

	@Column(name = "INS_CODE_END")
	private String insCodeEnd;

	@Column(name = "INS_DESCRIPTION")
	private String insDescription;
}
