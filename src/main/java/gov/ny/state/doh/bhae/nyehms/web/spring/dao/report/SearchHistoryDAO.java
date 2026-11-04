package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The search history report (formerly a MyBatis mapper with the same name and methods; its SQL is unchanged and runs
 * as JPA native queries through {@link NativeStatements}).
 */
@Repository
@RequiredArgsConstructor
public class SearchHistoryDAO {

	private static final String SEARCHED_WORKER_NAMES = """
			SELECT distinct b.worker_id, pkg_trk_common.fn_get_fullname (b.last_name,
			                                       b.first_name,
			                                       b.middle_name,
			                                       NULL) worker_name
			  FROM nyehms_search_history a
			  JOIN nyehms_users b ON b.worker_id = a.worker_id
			ORDER BY worker_name
			""";

	private final NativeStatements statements;

	/** The workers who searched: WORKER_ID, WORKER_NAME. */
	public List<JSONObject> getSearchedWorkerNames() {
		return statements.list(SEARCHED_WORKER_NAMES, Rows.json());
	}

	/**
	 * The search history report: calls {@code pr_get_search_history} and puts its rows (JSONObjects) into the map
	 * as {@code p_search_history}, as the MyBatis CALLABLE statement did.
	 *
	 * @param map p_from_date, p_to_date (text), p_worker_id, p_request_worker_id (whole numbers)
	 * @return an empty list (the procedure returns its rows through the cursor)
	 */
	public List<JSONObject> getSearchHistoryReportData(Map<Object, Object> map) {
		List<Object> results = statements.call("pr_get_search_history",
				in(map.get("p_from_date"), String.class),
				in(map.get("p_to_date"), String.class),
				in(map.get("p_worker_id"), Integer.class),
				in(map.get("p_request_worker_id"), Integer.class),
				cursor(Rows.json()));
		map.put("p_search_history", results.get(4));
		return new java.util.ArrayList<>();
	}
}
