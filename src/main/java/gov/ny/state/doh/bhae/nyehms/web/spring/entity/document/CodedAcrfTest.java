package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The ACRF test types (table CODED_ACRF_TEST); the columns the ICCR document screen and the case report form labs
 * (AcrfDocLabsDAO) read.
 * <p>
 * Read-only and used in JPQL projections only (a test code can occur under several parent test types, so whole
 * entities would collapse such rows). <b>ACRF_TEST_CODE as the key is assumed.</b>
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

	@Column(name = "PARENT_TEST_TYPE_CODE")
	private String parentTestTypeCode;

	@Column(name = "TEST_SECTION")
	private String testSection;

	/** Null while the test type is in use. */
	@Column(name = "END_DATE")
	private Timestamp endDate;

	/** Only ordered by (its column type is not known). */
	@Column(name = "SORT_ORDER")
	private String sortOrder;
}
