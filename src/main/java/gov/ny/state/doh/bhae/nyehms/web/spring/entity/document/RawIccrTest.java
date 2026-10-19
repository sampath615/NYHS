package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A test of an ICCR (Infection Control) report (a row of table RAW_ICCR_TESTS): an element of
 * {@link RawIccrDoc#getIccrTestList()}, entered on the ICCR data entry screen (row {@code iccrTestList[i]}) and
 * inserted together with the report.
 * <p>
 * An element of a collection, not an entity: the table has no key and the same test may be entered twice.
 */
@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor   // public: the posted data entry form is bound through it
public class RawIccrTest {

	@Column(name = "DATE_OF_TEST")
	@DateTimeFormat(pattern = "M/d/yyyy")   // as entered; not a valid date: left empty, as FN_TO_DATE did
	private LocalDate dateOfTest;

	@Column(name = "TEST_TYPE")
	private String testType;

	@Column(name = "TEST_RESULTS")
	private String testResults;

	@Column(name = "TEST_RESULT_COPIES")
	private String testResultCopies;

	@Column(name = "TEST_RESULT_LOGS")
	private String testResultLogs;
}
