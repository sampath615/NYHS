package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * Closed surveillance assignments of one worker, region and month (the assignment report matrix).
 *
 * @param month the month, e.g. JAN2025
 */
public record ClosedAssignmentCount(String regionCode, String workerName, int closedCount, int closedAdminCount, int closedUnassignedCount,
		String month) {

	/** A row of AssignmentReportRepository.findClosedAssignments: ACTUAL_REGION_CD, WORKER_NAME, CLOSED_CNT, CLS_ADM_CNT, CLOSED_UNASSIGNED_CNT, MONTH. */
	public static ClosedAssignmentCount of(Object[] row) {
		return new ClosedAssignmentCount(text(row[0]), text(row[1]), number(row[2]), number(row[3]), number(row[4]), text(row[5]));
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
