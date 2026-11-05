package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabReportsCountVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.TypedRowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The weekly lab report counts and the monthly LOINC / region lab reports (formerly a MyBatis mapper with the same name
 * and methods).
 */
@Repository
@RequiredArgsConstructor
public class StatusLabReportsDAO {

	/** The former result map: column label -> property of LabReportsCountVO, in the result map's order. */
	private static final Map<String, String> MAPPED_COLUMNS = new LinkedHashMap<>();

	/** The properties of LabReportsCountVO and their types (the result map's javaTypes). */
	private static final Map<String, Class<?>> TYPES = new LinkedHashMap<>();

	static {
		MAPPED_COLUMNS.put("DATASET", "dataSet");
		MAPPED_COLUMNS.put("WEEK_START_DT", "weekStartDate");
		MAPPED_COLUMNS.put("WEEK_END_DT", "weekEndDate");
		MAPPED_COLUMNS.put("REPORT_TEST_NAME", "reportTestType");
		MAPPED_COLUMNS.put("WEEK_NO", "weekNumber");
		MAPPED_COLUMNS.put("CNT", "count");
		MAPPED_COLUMNS.put("PERCENTAGE", "percentage");
		TYPES.put("dataSet", Integer.class);
		TYPES.put("weekStartDate", String.class);
		TYPES.put("weekEndDate", String.class);
		TYPES.put("reportTestType", String.class);
		TYPES.put("weekNumber", Integer.class);
		TYPES.put("count", Integer.class);
		TYPES.put("percentage", Float.class);
	}

	private final NativeStatements statements;

	/**
	 * Runs the configured count query (LAB_REPORT_COUNT_SQL) and reads its rows into LabReportsCountVOs as the former
	 * result map did: DATASET, WEEK_START_DT, WEEK_END_DT, REPORT_TEST_NAME, WEEK_NO, CNT and PERCENTAGE to their
	 * properties (other columns are ignored: the result map maps every property, so MyBatis auto-mapped nothing), null
	 * values not set, a row without values null.
	 * <p>
	 * Native: the SQL text is not in the application; it comes from the scheduler configuration and is run unchanged.
	 *
	 * @param inputMap SQL_QUERY: the query (null runs an empty statement, which fails, as before)
	 */
	public List<LabReportsCountVO> getLabReportCount(Map<String, String> inputMap) {
		String sql = inputMap.get("SQL_QUERY");
		return statements.list(sql == null ? "" : sql, new LabReportsCountRows());
	}

	/** {@code monthly_loinc_test_report}: its cursor rows (maps) are put into the map as {@code loincReportData}. */
	public void monthlyLoincTestReport(Map<String, Object> inputParameterMap) {
		inputParameterMap.put("loincReportData", statements.call("monthly_loinc_test_report", cursor(Rows.map())).get(0));
	}

	/** {@code monthly_labs_by_region_report}: its cursor rows (maps) are put into the map as {@code loincReportData}. */
	public void monthlyLabReportByRegion(Map<String, Object> inputParameterMap) {
		inputParameterMap.put("loincReportData", statements.call("monthly_labs_by_region_report", cursor(Rows.map())).get(0));
	}

	/** The former result map labReportsCountMap. */
	private static final class LabReportsCountRows implements TypedRowMapper<LabReportsCountVO> {

		@Override
		public Class<?> typeOf(String label, int index) {
			String property = MAPPED_COLUMNS.get(label.toUpperCase(Locale.ENGLISH));
			return property == null ? null : TYPES.get(property);
		}

		@Override
		public LabReportsCountVO map(Row row) {
			LabReportsCountVO vo = new LabReportsCountVO();
			boolean found = false;
			// only the result map's columns: MyBatis' auto-mapping skips properties the result map maps, and it maps
			// every property of LabReportsCountVO
			for (Map.Entry<String, String> mapped : MAPPED_COLUMNS.entrySet()) {
				if (row.indexOf(mapped.getKey()) >= 0) {
					found |= set(vo, mapped.getValue(), row.get(mapped.getKey()));
				}
			}
			return found ? vo : null;
		}

		private static boolean set(LabReportsCountVO vo, String property, Object value) {
			if (value == null) {
				return false;
			}
			switch (property) {
				case "dataSet" -> vo.setDataSet((Integer) value);
				case "weekStartDate" -> vo.setWeekStartDate((String) value);
				case "weekEndDate" -> vo.setWeekEndDate((String) value);
				case "reportTestType" -> vo.setReportTestType((String) value);
				case "weekNumber" -> vo.setWeekNumber((Integer) value);
				case "count" -> vo.setCount((Integer) value);
				case "percentage" -> vo.setPercentage((Float) value);
				default -> throw new IllegalStateException(property);
			}
			return true;
		}
	}
}
