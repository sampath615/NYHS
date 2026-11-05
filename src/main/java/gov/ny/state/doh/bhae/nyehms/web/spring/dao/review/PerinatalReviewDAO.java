package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.review.NbsFacilityMonitoringRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.review.NbsFacilityMonitoringRepository.Facility;
import lombok.RequiredArgsConstructor;

/**
 * The perinatal (NBS) facility monitoring letters: facility figures, prenatal percentages, designees, facility
 * addresses and demographics (formerly a MyBatis mapper with the same name and methods).
 * <p>
 * The facility list is a JPQL query over the entity NbsFacilityMonitoring. The other statements stay native SQL
 * (unchanged, values bound): their rows go as maps into the FreeMarker letter templates, which print each value by
 * its JDBC type (a NUMBER as a formatted number, text as is), and the column types of NBS_FACILITY_MONITORING,
 * NBS_FACILITY_DEMOGRAPHICS, CODED_NBS_HIV_DESIGNEES and CODED_NBS_PFI_NAMES are not known (their definitions are not
 * in this repository); two of them are {@code select *}, whose map keys are the tables' columns. See each method.
 * <p>
 * <b>Bug kept:</b> {@link #getFacilityMonitoringReportDataRange} selects MIN / MAX with an ORDER BY and no GROUP BY,
 * which Oracle rejects (ORA-00979), as before.
 */
@Repository
@RequiredArgsConstructor
public class PerinatalReviewDAO {

	/** The two birth quarters' condition of the monitoring statements (parameters ?1 .. ?4). */
	private static final String QUARTERS = " and ((BIRTHQTR = ?1 and BIRTHYR = ?2) or (BIRTHQTR = ?3 and BIRTHYR = ?4))";

	// native: the quarter name is built with the PL/SQL function fn_to_date and TO_CHAR(..., 'Mon'), and the figures
	// (column types unknown) must keep their JDBC types for the letter templates
	private static final String MONITORING_DATA = """
			select distinct 'Q'||BIRTHQTR||' ('|| To_char(fn_to_date(REPORT_FROM_DATE, 'MM/DD/YYYY'), 'Mon')||'-'||To_char(fn_to_date(REPORT_TO_DATE, 'MM/DD/YYYY'), 'Mon')||') '||BIRTHYR "QUARTER_NAME", PFI, BIRTHQTR,  FACILITY_NAME, REPORT_FROM_DATE, REPORT_TO_DATE, TOTAL_SPECIMEN, TESTED_PRENATALLY, TESTED_PRENATALLY_PERCENT, EXPEDITED_TESTS_INDICATED, EXPEDITED_TESTS_DONE, EXPEDITED_TESTS_DONE_MOM, EXPEDITED_TESTS_DONE_NEWBORN, EXPEDITED_TEST_NOT_DONE, EXPEDITED_TEST_NOT_DONE_PERCENT,SEC_PRENATAL_DONE, SEC_PRENATAL_DONE_PERCENT, ATTACHMENT_A, ATTACHMENT_B, ATTACHMENT_C, ATTACHMENT_D, ATTACHMENT_E, ATTACHMENT_F, BIRTHYR from NBS_FACILITY_MONITORING  where FACILITY_NAME is not null
			 and PFI = ?5""" + QUARTERS + " order by BIRTHYR asc, BIRTHQTR asc";

	// native: kept exactly as it was, including its bug (MIN / MAX with ORDER BY and no GROUP BY)
	private static final String MONITORING_DATA_RANGE = "select min(REPORT_FROM_DATE) from_date, max(REPORT_TO_DATE) to_date from NBS_FACILITY_MONITORING  where FACILITY_NAME is not null"
			+ QUARTERS + " order by BIRTHYR asc, BIRTHQTR asc";

	// native: Oracle TO_CHAR of the rounded averages over columns of unknown type (implicit conversions included)
	private static final String PRENATAL_DATA = "select to_char((round(sum(TESTED_PRENATALLY_PERCENT )/count(TOTAL_SPECIMEN), 0))) PRENATAL_PERCENTAGE,"
			+ "  to_char((round(sum(SEC_PRENATAL_DONE_PERCENT )/count(TOTAL_SPECIMEN), 0))) SEC_PRENATAL_PERCENTAGE from NBS_FACILITY_MONITORING"
			+ " where ((BIRTHQTR = ?1 and BIRTHYR = ?2) or (BIRTHQTR = ?3 and BIRTHYR = ?4))";

	// native: select * (the map keys are the table's columns, not known here) and the date text of Oracle's 'Month'
	private static final String DESIGNEES = "select nbs.*, Trim(To_Char(sysdate, 'Month')) || ' ' || To_Char(sysdate,'dd, yyyy') TODAY_DATE"
			+ " from CODED_NBS_HIV_DESIGNEES nbs where pfi = to_number(?1)";

	// native: select * (the map keys are the table's columns, not known here)
	private static final String FACILITY_ADDRESS = "select * from CODED_NBS_PFI_NAMES where pfi = to_number(?1)";

	// native: the column types of NBS_FACILITY_DEMOGRAPHICS are not known and the values go to the letter template
	private static final String DEMOGRAPHICS = "select pfi, facility_name, sex, labid, birthdt, medrecno from nbs_facility_demographics"
			+ " where pfi = ?1 and BIRTHQTR = ?2 and BIRTHYR = ?3";

	/** The columns the former result map read as Strings (javaType="String"). */
	private static final List<String> TEXT_COLUMNS = List.of("ATTACHMENT_A", "ATTACHMENT_B", "ATTACHMENT_C", "ATTACHMENT_D", "ATTACHMENT_E",
			"ATTACHMENT_F", "TODAY_DATE");

	private final NativeStatements statements;
	private final NbsFacilityMonitoringRepository monitoring;

	/** The figures of one facility (PFI) for the two birth quarters, by year and quarter. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getFacilityMonitoringReportData(Map<String, String> inputParameterMap) {
		return (List) statements.list(MONITORING_DATA, PerinatalReviewDAO::monitoringRow, quarters(inputParameterMap, inputParameterMap.get("PFI")));
	}

	/** The first REPORT_FROM_DATE and the last REPORT_TO_DATE of the two birth quarters (FROM_DATE, TO_DATE). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getFacilityMonitoringReportDataRange(Map<String, String> inputParameterMap) {
		return (Map) statements.one(MONITORING_DATA_RANGE, PerinatalReviewDAO::monitoringRow, quarters(inputParameterMap));
	}

	/**
	 * The facilities (PFI, FACILITY_NAME) with figures for the two birth quarters, by name: all of them, or the one
	 * whose PFI is given as FACILITY_NAME (any value other than "ALL").
	 */
	public List<Map<String, String>> getFacilityNamesByBirthCriteria(Map<String, String> inputParameterMap) {
		Map<String, String> p = inputParameterMap;
		String facility = p.get("FACILITY_NAME");
		List<Facility> facilities = !"ALL".equals(facility)
				? monitoring.findFacility(p.get("birthFirstQuarter"), p.get("birthFirstYear"), p.get("birthSecondQuarter"), p.get("birthSecondYear"), facility)
				: monitoring.findFacilities(p.get("birthFirstQuarter"), p.get("birthFirstYear"), p.get("birthSecondQuarter"), p.get("birthSecondYear"));
		List<Map<String, String>> rows = new ArrayList<>();
		for (Facility f : facilities) {
			Map<String, String> row = new HashMap<>();
			if (f.pfi() != null) {
				row.put("PFI", f.pfi());
			}
			if (f.facilityName() != null) {
				row.put("FACILITY_NAME", f.facilityName());
			}
			rows.add(row.isEmpty() ? null : row);
		}
		return rows;
	}

	/** The rounded average prenatal and second prenatal test percentages of all facilities for the two quarters. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getPrenatalData(Map<String, String> inputParameterMap) {
		return (Map) statements.one(PRENATAL_DATA, Rows.map(), quarters(inputParameterMap));
	}

	/** The HIV designees of a facility (every column of CODED_NBS_HIV_DESIGNEES) and today's date as TODAY_DATE. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getFacilityDesignees(String pfi) {
		return (Map) statements.one(DESIGNEES, Rows.map(), NativeStatements.text(pfi));
	}

	/** The name and address of a facility (every column of CODED_NBS_PFI_NAMES). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getFacilityAddressDetails(String pfi) {
		return (Map) statements.one(FACILITY_ADDRESS, Rows.map(), NativeStatements.text(pfi));
	}

	/** The newborns of a facility (PFI) in one birth quarter (BIRTHQTR, BIRTHYR). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getFacilityDemographicsData(Map<String, String> inputParameterMap) {
		return (List) statements.list(DEMOGRAPHICS, Rows.map(), NativeStatements.text(inputParameterMap.get("PFI")),
				NativeStatements.text(inputParameterMap.get("BIRTHQTR")), NativeStatements.text(inputParameterMap.get("BIRTHYR")));
	}

	/** The values of ?1 .. ?4 (the two birth quarters), and any further values. */
	private static Object[] quarters(Map<String, String> p, String... more) {
		Object[] values = new Object[4 + more.length];
		values[0] = NativeStatements.text(p.get("birthFirstQuarter"));
		values[1] = NativeStatements.text(p.get("birthFirstYear"));
		values[2] = NativeStatements.text(p.get("birthSecondQuarter"));
		values[3] = NativeStatements.text(p.get("birthSecondYear"));
		for (int i = 0; i < more.length; i++) {
			values[4 + i] = NativeStatements.text(more[i]);
		}
		return values;
	}

	/**
	 * A row of the former result map facilityMonitoringReportData: every column by its JDBC type, except the
	 * attachment flags and TODAY_DATE, which it read as text; nulls left out, a row of nulls null.
	 */
	private static Map<String, Object> monitoringRow(Row row) {
		Map<String, Object> map = MAP.map(row);
		if (map != null) {
			for (String column : TEXT_COLUMNS) {
				map.computeIfPresent(column, (key, value) -> value instanceof BigDecimal number ? number.toPlainString() : value.toString());
			}
		}
		return map;
	}

	private static final RowMapper<Map<String, Object>> MAP = Rows.map();
}
