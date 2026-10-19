package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The ACRF test types (table CODED_ACRF_TEST); the columns the ICCR document screen reads.
 * <p>
 * Read-only and used in JPQL projections only. <b>ACRF_TEST_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ACRF_TEST")
public class CodedAcrfTest {

	@Id
	@Column(name = "ACRF_TEST_CODE")
	private String acrfTestCode;

	@Column(name = "ACRF_TEST_DESC")
	private String acrfTestDesc;
}
