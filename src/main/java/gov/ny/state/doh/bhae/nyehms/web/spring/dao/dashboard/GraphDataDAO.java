package gov.ny.state.doh.bhae.nyehms.web.spring.dao.dashboard;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.TypedRowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import lombok.RequiredArgsConstructor;

/**
 * The dashboard's worker tally graph (formerly a MyBatis mapper with the same name and method).
 */
@Repository
@RequiredArgsConstructor
public class GraphDataDAO {

	// native: Oracle (+) outer joins (one of them inside NVL(hiv.test_result(+), ...) and NOT LIKE on the outer-joined
	// side), a FULL OUTER JOIN of two grouped CTEs, the NLS-dependent To_Date('31-DEC-2999'), the PL/SQL function
	// fn_to_date and SYSDATE - n arithmetic with a bound number; kept unchanged in the database
	private static final String WORKER_TALLY_GRAPH_DATA = """
			WITH worker AS
			(
			    SELECT Distinct worker_region_id, worker_id, role_code, region_code
			    FROM user_region
			    WHERE worker_id =  ?1
			    AND Nvl(end_date,To_Date('31-DEC-2999')) > sysdate
			),
			query1 AS
			(
			    SELECT region_description, Count(distinct s.document_id) pri_open_cnt
			      FROM surveillance_assignment s, coded_region r, nyehms_ehars_stateno_map e , worker u, std_hiv_eclrS_Doc hiv
			     -- ,document_locator doc
			    WHERE s.current_assignment_status IN ('OPEN', 'REOPEN','REASSGN')
			        --and doc.document_id = s.TRIGGERING_DOC_ID
			       -- and doc.document_type_code in ('HIV_LAB', 'PRF')



			       AND s.current_region_cd = r.region_code
			       AND s.stateno = e.nyehms_stateno(+)

			       AND s.triggering_doc_id = hiv.document_id(+)
			       AND nvl(hiv.test_result(+), 'x') NOT IN    (    'NEG',
			                                            'NO RESULT',
			                                            'HIV NEGATIVE',
			                                            '0'
			                                        )
			       AND nvl(hiv.test_result(+), 'x') NOT LIKE '<%'




			       AND (e.hiv_cdc = 'Y' OR e.aids_cdc = 'Y')
			       AND ((SUBSTR (e.hiv_aids_dx_dt, 1, 4) in (?2,  ?3)) OR (e.hiv_aids_dx_dt IS NULL))
			       AND CASE
			                WHEN u.role_code = 1 AND s.current_region_cd = u.region_code THEN 1
			                WHEN u.role_code = 2 AND EXISTS (SELECT 1
			                                                  FROM worker_assignment w
			                                                 WHERE u.worker_region_id = w.worker_region_id
			                                                   AND w.worker_assignment_id = s.current_worker_assignment_id) THEN 1
			                WHEN u.role_code = 4 THEN 1
			                ELSE 0
			            END = 1
			  GROUP BY region_description
			)
			,query2 AS
			(
			    SELECT     region_description, Count(distinct document_id) open_cnt,
			            Sum(CASE WHEN s.CREATE_DATE < TO_DATE('01/01/2025', 'MM/DD/YYYY')  and s.CREATE_DATE > SYSDATE - 60 THEN 0
			                    WHEN s.create_date < SYSDATE-?4 THEN 1 ELSE 0
			                END
			                ) cnt_90,
			            Max(fn_to_date(e.hiv_dx_dt,'MMDDYYYY')) hiv_dx_date
			      FROM surveillance_assignment s  , coded_region r, nyehms_ehars_stateno_map e , worker u
			     WHERE current_assignment_status IN ('OPEN','REOPEN','REASSGN')
			       AND s.current_region_cd = r.region_code
			       AND s.stateno = e.nyehms_stateno(+)
			       AND CASE
			                WHEN u.role_code = 1 AND s.current_region_cd = u.region_code THEN 1
			                WHEN u.role_code = 2 AND EXISTS (SELECT 1
			                                                  FROM worker_assignment w
			                                                 WHERE u.worker_region_id = w.worker_region_id
			                                                   AND w.worker_assignment_id = s.current_worker_assignment_id) THEN 1
			                WHEN u.role_code = 4 THEN 1
			                ELSE 0
			            END = 1
			  GROUP BY region_description
			)
			SELECT NVL(a.region_description, b.region_description) region_description, NVL(a.pri_open_cnt, 0) pri_open_cnt, NVL(b.open_cnt, 0) open_cnt, NVL(b.cnt_90, 0) cnt_90
			  FROM query1 a FULL OUTER JOIN query2 b
			    ON a.region_description = b.region_description
			""";

	/** The former result map: key (= column label) -> type. */
	private static final Map<String, Class<?>> COLUMNS = new LinkedHashMap<>();

	static {
		COLUMNS.put("REGION_DESCRIPTION", String.class);
		COLUMNS.put("PRI_OPEN_CNT", Integer.class);
		COLUMNS.put("OPEN_CNT", Integer.class);
		COLUMNS.put("CNT_90", Integer.class);
	}

	private final NativeStatements statements;

	/**
	 * The open and priority-open assignment counts per region that a worker sees.
	 *
	 * @param inputParameterMap workerId, previousYear, currentYear, PRIORITY_ASSIGNMENT_PERIOD (days)
	 * @return maps REGION_DESCRIPTION (text), PRI_OPEN_CNT, OPEN_CNT, CNT_90 (Integers), null values left out
	 */
	public List<Map<String, Object>> getWorkerTallyGraphData(Map<String, Integer> inputParameterMap) {
		return statements.list(WORKER_TALLY_GRAPH_DATA, new TallyRows(),
				NativeStatements.number(inputParameterMap.get("workerId")),
				NativeStatements.number(inputParameterMap.get("previousYear")),
				NativeStatements.number(inputParameterMap.get("currentYear")),
				NativeStatements.number(inputParameterMap.get("PRIORITY_ASSIGNMENT_PERIOD")));
	}

	/** The former result map workerTallyGraphDataMap: the four columns read with their types into a HashMap. */
	private static final class TallyRows implements TypedRowMapper<Map<String, Object>> {

		@Override
		public Class<?> typeOf(String label, int index) {
			return COLUMNS.get(label.toUpperCase(Locale.ENGLISH));
		}

		@Override
		public Map<String, Object> map(Row row) {
			Map<String, Object> values = new HashMap<>();
			for (String column : COLUMNS.keySet()) {
				if (row.indexOf(column) >= 0 && row.get(column) != null) {
					values.put(column, row.get(column));
				}
			}
			return values.isEmpty() ? null : values;
		}
	}
}
