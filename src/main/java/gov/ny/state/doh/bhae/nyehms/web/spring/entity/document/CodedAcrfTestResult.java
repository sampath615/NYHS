package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The results each ACRF test type can have (table CODED_ACRF_TEST_RESULT); the columns the ICCR screens read.
 * <p>
 * Read-only and used in JPQL projections only. <b>The key (ACRF_TEST_CODE, ACRF_RESULT_CODE) is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ACRF_TEST_RESULT")
@IdClass(CodedAcrfTestResultId.class)
public class CodedAcrfTestResult {

	@Id
	@Column(name = "ACRF_TEST_CODE")
	private String acrfTestCode;

	@Id
	@Column(name = "ACRF_RESULT_CODE")
	private String acrfResultCode;

	@Column(name = "ACRF_RESULT_DESC")
	private String acrfResultDesc;

	/** Null while the result is in use. */
	@Column(name = "END_DATE")
	private Timestamp endDate;
}
