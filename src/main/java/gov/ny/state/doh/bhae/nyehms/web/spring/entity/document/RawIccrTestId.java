package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Key of {@link RawIccrTest}: all its columns (see that class).
 */
public record RawIccrTestId(Integer documentId, Timestamp dateOfTest, String testType, String testResults, String testResultCopies,
		String testResultLogs) implements Serializable {
}
