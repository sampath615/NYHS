package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The provider reports by year, by HIV/AIDS diagnosis date and by PRF received (formerly a MyBatis mapper with the same
 * name and methods). Each report is a procedure of PKG_PROVIDER_REPORTS (the report logic is PL/SQL) called through
 * {@link NativeStatements#call}; its cursor rows (JSONObjects, keys = the cursor's column labels) are put into the
 * caller's map under the key the MyBatis CALLABLE statement used, and the method returns an empty list as before.
 *
 * @author skg02
 */
@Repository
@RequiredArgsConstructor
public class ProviderReportDAO {

	private final NativeStatements statements;

	/** {@code pr_provider_report_hivdxdt}: p_from_year, p_to_year in; rows as {@code p_prov_report_hivdxdt}. */
	public List<JSONObject> getProviderReportByHivDxDate1(Map<Object, Object> inputParameterMap) {
		return years("PKG_PROVIDER_REPORTS.pr_provider_report_hivdxdt", "p_prov_report_hivdxdt", inputParameterMap);
	}

	/** {@code pr_provider_report_hivdxdt_year}: p_from_year, p_to_year in; rows as {@code p_prov_report_hivdxdt}. */
	public List<JSONObject> getProviderReportByHivDxDateYearly(Map<Object, Object> inputParameterMap) {
		return years("PKG_PROVIDER_REPORTS.pr_provider_report_hivdxdt_year", "p_prov_report_hivdxdt", inputParameterMap);
	}

	/** {@code pr_provider_report_prfsource}: p_from_year, p_to_year in; rows as {@code p_prov_report_prf}. */
	public List<JSONObject> getProviderReportsByPRFReceived(Map<Object, Object> inputParameterMap) {
		return years("PKG_PROVIDER_REPORTS.pr_provider_report_prfsource", "p_prov_report_prf", inputParameterMap);
	}

	/** {@code pr_provider_report_prfsource_year}: p_from_year, p_to_year in; rows as {@code p_prov_report_prf}. */
	public List<JSONObject> getProviderReportsByPRFReceivedYearly(Map<Object, Object> inputParameterMap) {
		return years("PKG_PROVIDER_REPORTS.pr_provider_report_prfsource_year", "p_prov_report_prf", inputParameterMap);
	}

	/**
	 * {@code pr_provider_report_year}: p_from_date, p_to_date, p_case_type, p_region_cd, p_prf_status, p_ahi in (text);
	 * rows as {@code p_prov_report}.
	 */
	public List<JSONObject> getProviderReportByYear(Map<Object, Object> inputParameterMap) {
		List<Object> results = statements.call("PKG_PROVIDER_REPORTS.pr_provider_report_year",
				in(inputParameterMap.get("p_from_date"), String.class),
				in(inputParameterMap.get("p_to_date"), String.class),
				in(inputParameterMap.get("p_case_type"), String.class),
				in(inputParameterMap.get("p_region_cd"), String.class),
				in(inputParameterMap.get("p_prf_status"), String.class),
				cursor(Rows.json()),
				in(inputParameterMap.get("p_ahi"), String.class));
		inputParameterMap.put("p_prov_report", results.get(5));
		return new ArrayList<>();
	}

	/** A report procedure with the parameters (p_from_year, p_to_year, cursor). */
	private List<JSONObject> years(String procedure, String cursorKey, Map<Object, Object> inputParameterMap) {
		List<Object> results = statements.call(procedure,
				in(inputParameterMap.get("p_from_year"), String.class),
				in(inputParameterMap.get("p_to_year"), String.class),
				cursor(Rows.json()));
		inputParameterMap.put(cursorKey, results.get(2));
		return new ArrayList<>();
	}
}
