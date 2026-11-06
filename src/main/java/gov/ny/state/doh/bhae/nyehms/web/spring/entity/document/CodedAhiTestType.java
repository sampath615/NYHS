package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The test types of the AHI form's negative / indeterminate test history (table CODED_AHI_TEST_TYPE), read-only.
 * <p>
 * <b>TEST_TYPE_CODE as the key is assumed</b>; the codes are read through a projection (a code that repeats gives
 * several rows, as before).
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_AHI_TEST_TYPE")
public class CodedAhiTestType {

	@Id
	@Column(name = "TEST_TYPE_CODE")
	private String testTypeCode;

	@Column(name = "TEST_TYPE_DESC")
	private String testTypeDesc;
}
