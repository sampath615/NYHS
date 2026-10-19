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
 * A test of an ICCR (Infection Control) report, entered on the ICCR data entry screen (table RAW_ICCR_TESTS).
 * <p>
 * The table has no known key and the same test may be entered twice, so a row is identified by all its
 * values ({@link RawIccrTestId}) and is never loaded or persisted as an entity: it is read in JPQL
 * projections and written with an HQL insert ({@code IccrTestRepository.insertTest}).
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_ICCR_TESTS")
@IdClass(RawIccrTestId.class)
public class RawIccrTest {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Id
	@Column(name = "DATE_OF_TEST")
	private Timestamp dateOfTest;

	@Id
	@Column(name = "TEST_TYPE")
	private String testType;

	@Id
	@Column(name = "TEST_RESULTS")
	private String testResults;

	@Id
	@Column(name = "TEST_RESULT_COPIES")
	private String testResultCopies;

	@Id
	@Column(name = "TEST_RESULT_LOGS")
	private String testResultLogs;
}
