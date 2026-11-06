package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The reasons a patient was tested, for the AHI form (table CODED_AHI_CI_REASON_TESTED), read-only.
 * <p>
 * <b>TESTED_CODE as the key is assumed</b>; the codes are read through a projection (a code that repeats gives
 * several rows, as before).
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_AHI_CI_REASON_TESTED")
public class CodedAhiCiReasonTested {

	@Id
	@Column(name = "TESTED_CODE")
	private String testedCode;

	@Column(name = "TESTED_DESC")
	private String testedDesc;
}
