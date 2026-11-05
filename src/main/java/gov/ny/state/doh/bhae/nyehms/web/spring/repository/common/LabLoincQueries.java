package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.LoincTestTypeRow;

/** The LOINC lookup queries whose conditions depend on the filters given (a fragment of {@link LabLoincRepository}). */
public interface LabLoincQueries {

	/**
	 * The LOINC codes with their test types (codes whose test type is not in CODED_LAB_TEST_TYPE are left out), filtered
	 * by the non-null values: the code and the description as LIKE patterns, the test type and the unit exactly;
	 * {@code noUnit} keeps only the test types without a unit. Only the conditions of the given filters are in the
	 * query, as in the former statement (so the database reads the rows the same way and returns them in the same
	 * order).
	 */
	List<LoincTestTypeRow> findWithTestType(String loincCode, String description, String testType, String unit, boolean noUnit);
}
