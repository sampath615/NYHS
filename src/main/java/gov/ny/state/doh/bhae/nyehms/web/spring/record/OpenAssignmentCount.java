package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * Open surveillance assignments of one worker, region and document type (the open assignment report).
 *
 * @param count the open assignments
 * @param days30 those that count as older than 30 days
 */
public record OpenAssignmentCount(String workerName, String regionCode, String documentTypeCode, int count, int days30) {

	/** A row of AssignmentReportRepository.findOpenAssignments: WORKER_NAME, WORKER_ID, CURRENT_REGION_CD, DOCUMENT_TYPE_CODE, COUNT, 30DAYS. */
	public static OpenAssignmentCount of(Object[] row) {
		return new OpenAssignmentCount(text(row[0]), text(row[2]), text(row[3]), number(row[4]), number(row[5]));
	}

	/** A number column as getInt read it: 0 for null. */
	static int number(Object value) {
		return value == null ? 0 : ((Number) value).intValue();
	}

	/** A column as getString read it (whole numbers without a decimal part). */
	static String text(Object value) {
		if (value instanceof java.math.BigDecimal number) {
			return number.toPlainString();
		}
		return value == null ? null : value.toString();
	}
}
