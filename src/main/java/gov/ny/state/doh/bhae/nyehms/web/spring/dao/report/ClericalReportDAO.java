package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ClericalNonMatchRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ScanningCountRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.StdScanDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.report.NyehmsCrNonMatchVwRepository;
import lombok.RequiredArgsConstructor;

/**
 * The clerical review, scanning and clerical non-match reports (formerly a MyBatis mapper with the same name and
 * methods). Rows are JSONObjects with the former column labels as keys (null values left out, a row without values
 * null).
 * <p>
 * The scanning and non-match reports are Spring Data JPQL (StdScanDoc + NyehmsUsers, NyehmsCrNonMatchVw). The two
 * clerical review reports stay native (see {@link #getclericalReportByStaff}).
 */
@Repository
@RequiredArgsConstructor
public class ClericalReportDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY') */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	/** The standardized document tables of the clerical review report, in the former UNION's order. */
	private static final List<String> CLERICAL_TABLES = List.of("std_hiv_eclrs_doc", "std_std_eclrs_doc", "std_tb_doc",
			"std_eprf_ooc_upload", "std_ccd_doc", "std_adap_doc", "std_medicaid_doc", "std_death_doc", "std_nycijr_doc",
			"std_din_doccs_doc", "std_prf_doc", "std_nycprf_doc", "std_std_confirmed_doc", "std_airs_doc");

	/** The tables of the clerical death review report. */
	private static final List<String> DEATH_TABLES = List.of("std_death_doc", "std_ssdm_doc");

	private final NativeStatements statements;
	private final StdScanDocRepository scanDocs;
	private final NyehmsCrNonMatchVwRepository nonMatches;

	/**
	 * The clerical review report of the death documents (STD_DEATH_DOC, STD_SSDM_DOC): COUNT, REVIEW_NAME,
	 * FLUX_MATCH_CD, REVIEW_DT per reviewer, match code and review day.
	 *
	 * @param inputParameterMap fromDate, toDate (MM/DD/YYYY)
	 */
	public List<JSONObject> getclericalDeathReportByStaff(Map<String, String> inputParameterMap) {
		return clericalReport(DEATH_TABLES, inputParameterMap);
	}

	/**
	 * The clerical review report of the other documents (14 standardized document tables; the AIRS documents reviewed by
	 * 'NYEHMS' left out): COUNT, REVIEW_NAME, FLUX_MATCH_CD, REVIEW_DT.
	 * <p>
	 * Native (SQL unchanged, values bound): a UNION over the standardized document tables of their review columns
	 * (FLUX_MATCH_CD, REVIEW_DATE, REVIEW_UID), which their entities do not map and which are shared with the document
	 * modules, grouped over the union; TRUNC/TO_DATE/TO_CHAR are Oracle's.
	 *
	 * @param inputParameterMap fromDate, toDate (MM/DD/YYYY)
	 */
	public List<JSONObject> getclericalReportByStaff(Map<String, String> inputParameterMap) {
		return clericalReport(CLERICAL_TABLES, inputParameterMap);
	}

	/**
	 * The scanning report: COUNT (a number, as before), SCANNED_BY, CREATE_DATE (MM/DD/YYYY) per worker and day.
	 *
	 * @param inputParameterMap fromDate, toDate (MM/DD/YYYY)
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getScanningReportByStaff(Map<String, String> inputParameterMap) {
		List<JSONObject> rows = new ArrayList<>();
		for (ScanningCountRow count : scanDocs.findScanningCounts(inputParameterMap.get("fromDate"), inputParameterMap.get("toDate"))) {
			JSONObject row = new JSONObject();
			row.put("COUNT", BigDecimal.valueOf(count.count()));     // Oracle's COUNT, a NUMBER read as a BigDecimal
			if (count.scannedBy() != null) {
				row.put("SCANNED_BY", count.scannedBy());
			}
			if (count.createDate() != null) {
				row.put("CREATE_DATE", count.createDate());
			}
			rows.add(row);
		}
		return rows;
	}

	/**
	 * The clerical non-match report: STATENO, DOCUMENT_TYPE_CODE, REVIEWED_BY, REVIEW_DATE (MM/DD/YYYY).
	 *
	 * @param inputData p_from_date, p_to_date (MM/DD/YYYY)
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> clericalNonMatchReportData(Map<Object, Object> inputData) {
		List<JSONObject> rows = new ArrayList<>();
		for (ClericalNonMatchRow nonMatch : nonMatches.findReviewedBetween(inputData.get("p_from_date"), inputData.get("p_to_date"))) {
			JSONObject row = new JSONObject();
			if (nonMatch.stateno() != null) {
				row.put("STATENO", nonMatch.stateno());
			}
			if (nonMatch.documentTypeCode() != null) {
				row.put("DOCUMENT_TYPE_CODE", nonMatch.documentTypeCode());
			}
			if (nonMatch.reviewedBy() != null) {
				row.put("REVIEWED_BY", nonMatch.reviewedBy());
			}
			if (nonMatch.reviewDate() != null) {
				row.put("REVIEW_DATE", nonMatch.reviewDate().toLocalDateTime().format(DATE));
			}
			rows.add(row.isEmpty() ? null : row);
		}
		return rows;
	}

	/** The former clerical report SQL over the given tables (one UNION member per table), its dates bound per member. */
	private List<JSONObject> clericalReport(List<String> tables, Map<String, String> inputParameterMap) {
		StringBuilder sql = new StringBuilder("select count(*) as count, review_name, FLUX_MATCH_CD, review_dt\nfrom (\n");
		List<Object> parameters = new ArrayList<>();
		for (String table : tables) {
			if (!parameters.isEmpty()) {
				sql.append("union\n");
			}
			int from = parameters.size() + 1;
			sql.append("select s.document_id,\n")
					.append("s.flux_match_cd,\n")
					.append("d.document_type_code,\n")
					.append("to_char(s.REVIEW_DATE,'mm/dd/yyyy') as review_dt,\n")
					.append("review_date,\n")
					.append("n.first_name||' '||n.last_name as review_name\n")
					.append("from ").append(table).append(" s,nyehms_users n, document_locator d\n")
					.append("where d.document_id = s.document_id\n")
					.append("and s.flux_match_cd in ('C_M','C_N')\n")
					.append("and trunc(s.REVIEW_DATE) between to_date(?").append(from).append(",'mm/dd/yyyy') and to_date(?")
					.append(from + 1).append(",'mm/dd/yyyy')\n");
			if (table.equals("std_airs_doc")) {
				sql.append("and s.review_uid != 'NYEHMS'\n");
			}
			sql.append("and s.review_uid = n.worker_id\n");
			parameters.add(inputParameterMap.get("fromDate"));
			parameters.add(inputParameterMap.get("toDate"));
		}
		sql.append(")\ngroup by review_name, FLUX_MATCH_CD, review_dt order by (to_date(review_dt,'mm/dd/yyyy'))");
		Object[] values = parameters.stream().map(NativeStatements::text).toArray();
		return statements.list(sql.toString(), Rows.json(), values);
	}
}
