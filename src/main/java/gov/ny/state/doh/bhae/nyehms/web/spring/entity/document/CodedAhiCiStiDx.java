package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The concurrent STI diagnosis codes of the AHI form (table CODED_AHI_CI_STI_DX), read-only.
 * <p>
 * <b>DIAGNOSIS_CODE as the key is assumed</b>; the codes are read through a projection (a code that repeats gives
 * several rows, as before).
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_AHI_CI_STI_DX")
public class CodedAhiCiStiDx {

	@Id
	@Column(name = "DIAGNOSIS_CODE")
	private String diagnosisCode;

	@Column(name = "DIAGNOSIS_DESC")
	private String diagnosisDesc;
}
