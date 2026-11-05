package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The dispositions of rejected NYC surveillance records (table CODED_NYC_REJECT_DISPOSITION).
 * <p>
 * Read-only and used in JPQL projections only. <b>DISPOSITION_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_NYC_REJECT_DISPOSITION")
public class CodedNycRejectDisposition {

	@Id
	@Column(name = "DISPOSITION_CODE")
	private String dispositionCode;

	@Column(name = "DISPOSITION_DESC")
	private String dispositionDesc;

	@Column(name = "DISPOSITION_DESC_SHORT")
	private String dispositionDescShort;
}
