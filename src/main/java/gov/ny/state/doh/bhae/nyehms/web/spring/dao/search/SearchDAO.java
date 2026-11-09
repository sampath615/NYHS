package gov.ny.state.doh.bhae.nyehms.web.spring.dao.search;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.textCursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.search.SearchPersonCriteriaVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The person search (formerly a MyBatis mapper with the same name and methods; the callers are unchanged).
 * <p>
 * The two searches call the PL/SQL procedures {@code PKG_SEARCH.PR_PERSON_SEARCH} / {@code PR_PERSON_SEARCH_QUICK}
 * and read their result cursor ({@link NativeStatements#call}); the cursor columns are read as text, as MyBatis read
 * them into the text properties of {@link SearchPersonCriteriaVO} and into Strings (the ROWIDs). The city-number
 * lookup is JPQL over {@code EharsPersonNyc}. Native SQL, with the reason: {@link #getTheActiveStateNo} selects a
 * PL/SQL function and a scalar subquery FROM DUAL (JPQL has no select without an entity);
 * {@link #personSearchResultsWithQuickSearch} reads STATENO_DOC_ATTR_METADATA by ROWID with REGEXP_SUBSTR (no entity;
 * an Oracle pseudo column and function). Its {@code order by ${sortByColumn} ${sortOrder}}, pasted from the request
 * before, now accepts only the result columns and ASC / DESC.
 */
@Repository
@RequiredArgsConstructor
public class SearchDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	/** The cursor of PR_PERSON_SEARCH as the former result map searchPersonResult: columns to VO properties. */
	static final RowMapper<SearchPersonCriteriaVO> SEARCH_PERSON_ROWS = Rows.resultMap(SearchPersonCriteriaVO.class,
			"STATENO", "stateNo", "FIRST_NAME", "firstName", "LAST_NAME", "lastName", "DATE_OF_BIRTH", "dateOfBirth",
			"SSN", "ssn", "DIN", "din", "NYSID", "nysid", "DATE_OF_DEATH", "dateOfDeath",
			"VISIBLE_TO_WORKER", "visibleToWorker", "flag_bad_or_same", "flaggedSameOrBadInd");

	/** The cursor of PR_PERSON_SEARCH_QUICK as the former result map rowIdMap (type String): the RWID column of each row. */
	static final RowMapper<String> ROW_ID_ROWS = row -> (String) row.get("RWID");

	/**
	 * The person search: {@code PKG_SEARCH.PR_PERSON_SEARCH}; its rows ({@link SearchPersonCriteriaVO}) are put into
	 * the map as {@code refCursor}, as the MyBatis CALLABLE statement did.
	 *
	 * @param map p_stateno, p_first_name, p_last_name, p_ssn, p_nysid, p_din, p_cityno (text), p_date_of_birth,
	 *        p_date_of_death (dates, given as text as the screen sends them), p_worker_id (a whole number)
	 * @return an empty list (the rows are in the map)
	 */
	public List<SearchPersonCriteriaVO> searchPersonFields(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_SEARCH.PR_PERSON_SEARCH", searchParameters(map, textCursor(SEARCH_PERSON_ROWS)));
		map.put("refCursor", results.get(8));
		return new ArrayList<>();
	}

	/**
	 * The quick person search: {@code PKG_SEARCH.PR_PERSON_SEARCH_QUICK}; the ROWIDs of its rows (Strings) are put
	 * into the map as {@code refCursor}.
	 *
	 * @param map as for {@link #searchPersonFields}
	 */
	public void searchPersonFieldsWithQuickSearch(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_SEARCH.PR_PERSON_SEARCH_QUICK", searchParameters(map, textCursor(ROW_ID_ROWS)));
		map.put("refCursor", results.get(8));
	}

	/** The 11 parameters of both search procedures; the cursor is the ninth. */
	private static CallParam[] searchParameters(Map<Object, Object> map, CallParam cursor) {
		return new CallParam[] {
				in(map.get("p_stateno"), String.class),
				in(map.get("p_first_name"), String.class),
				in(map.get("p_last_name"), String.class),
				in(map.get("p_date_of_birth"), java.sql.Date.class),             // #{..,jdbcType=DATE}: the text as given, a DATE null
				in(map.get("p_date_of_death"), java.sql.Date.class),
				in(map.get("p_ssn"), String.class),
				in(map.get("p_nysid"), String.class),
				in(map.get("p_din"), String.class),
				cursor,
				in(map.get("p_worker_id"), Integer.class),
				in(map.get("p_cityno"), String.class)};
	}

	// native: a PL/SQL function and a scalar subquery selected FROM DUAL (JPQL has no select without an entity)
	private static final String ACTIVE_STATENO = """
			SELECT  PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO (?1) currentStateno, (select flag_active from person where stateno = ?2) as flagActive from DUAL""";

	/**
	 * The current state number of a state number ({@code PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO}) and the person's
	 * FLAG_ACTIVE: CURRENTSTATENO, FLAGACTIVE (a null value is left out; null if both are null).
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getTheActiveStateNo(String _stateno) throws DataAccessException {
		return (Map) statements.one(ACTIVE_STATENO, Rows.map(), text(_stateno), text(_stateno));
	}

	// native: STATENO_DOC_ATTR_METADATA has no entity and is read by ROWID; REGEXP_SUBSTR and a CASE FROM DUAL
	private static final String QUICK_SEARCH_RESULTS = """
			SELECT stateNo,
			                 ( SELECT CASE WHEN ( SELECT COUNT(*) FROM  nyehms_duplicate_statenos d WHERE     (   s.stateno = d.stateno_1 OR s.stateno = d.stateno_2) AND status_cd = 'NEW') >0
			                          THEN 'S'
			                          WHEN ( SELECT COUNT(*) FROM  nyehms_new_flagged_badmerge_vw d WHERE  d.stateno = s.stateno ) >0
			                          THEN 'B'
			                          ELSE NULL
			                          END
			     FROM DUAL)
			          AS flaggedSameOrBadInd,
			                Trim(TO_CHAR (REGEXP_SUBSTR (first_name, '[-a-z-A-Z-0-9 ''.]+', 1, 2)))
			                   firstName,

			                Trim(TO_CHAR (REGEXP_SUBSTR (last_name, '[-a-z-A-Z-0-9 ''.]+', 1, 2)))
			                   lastName,

			                TO_CHAR (REGEXP_SUBSTR (date_of_birth, '[a-z-A-Z-0-9 |/-]+'))
			                   dateOfBirth,

			                TO_CHAR (REGEXP_SUBSTR (date_of_death, '[a-z-A-Z-0-9 |/-]+'))
			                   dateOfDeath,

			                TO_CHAR (REGEXP_SUBSTR (ssn, '[-a-z-A-Z-0-9 ''.]+')) ssn,

			                TO_CHAR (REGEXP_SUBSTR (din, '[-a-z-A-Z-0-9 ''.]+')) din,

			                TO_CHAR (REGEXP_SUBSTR (nysid, '[-a-z-A-Z-0-9 ''.]+')) nysid

			           FROM stateno_doc_attr_metadata s
			           WHERE ROWID IN
			""";

	/** The result columns a quick search may be sorted by (the former {@code ${sortByColumn}}), as the grid names them. */
	static final Set<String> SORT_COLUMNS = Set.of("STATENO", "FLAGGEDSAMEORBADIND", "FIRSTNAME", "LASTNAME", "DATEOFBIRTH",
			"DATEOFDEATH", "SSN", "DIN", "NYSID");

	/** The sort orders (the former {@code ${sortOrder}}); an empty one leaves the database's default (ascending), as before. */
	static final Set<String> SORT_ORDERS = Set.of("ASC", "DESC", "");

	/**
	 * The persons of a quick search's rows: STATENO_DOC_ATTR_METADATA by ROWID (at most 1,000 at a time — the caller
	 * splits the list), the names, dates and numbers reduced to their plain characters, each flagged as a same person
	 * (S) or a bad merge (B).
	 *
	 * @param inputOutputParameterMap rowIdList (the ROWIDs as text; an empty list fails, as {@code IN ()} did),
	 *        sortByColumn (a result column; null or empty for no order), sortOrder (ASC, DESC or empty; another value,
	 *        also a missing one, fails — as the SQL text it produced did)
	 */
	public List<SearchPersonCriteriaVO> personSearchResultsWithQuickSearch(Map<Object, Object> inputOutputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(QUICK_SEARCH_RESULTS)
				.append(inPlaceholders(inList(inputOutputParameterMap.get("rowIdList"), "rowIdList"), parameters));
		Object sortByColumn = inputOutputParameterMap.get("sortByColumn");
		if (sortByColumn != null && !"".equals(sortByColumn)) {
			sql.append("\n order by ").append(allowed(sortByColumn, SORT_COLUMNS, "search result column"))
					.append(' ').append(allowed(inputOutputParameterMap.get("sortOrder"), SORT_ORDERS, "sort order"));
		}
		return statements.list(sql.toString(), Rows.bean(SearchPersonCriteriaVO.class), parameters.toArray());
	}

	/**
	 * The current state number ({@code PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO}) of the NYEHMS state number of a
	 * NYC city number, from EHARS_PERSON_NYC (any one of its rows, as {@code rownum < 2} chose): CURRENTSTATENO; null
	 * if there is no row, or no number.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getNyehmsStatenoUsingCityNo(String cityNo) {
		return (Map) jpql.one("""
				select function('PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO' as String, e.nyehmsStateno) as CURRENTSTATENO
				from EharsPersonNyc e where e.cityno = ?1
				fetch first 1 rows only""", Rows.map(), cityNo);
	}

	/** A name from the request, as it was given, if it is one of the allowed ones (ignoring case); otherwise the call fails. */
	private static String allowed(Object name, Set<String> allowed, String what) {
		if (name instanceof String text && allowed.contains(text.trim().toUpperCase(Locale.ENGLISH))) {
			return text;
		}
		throw new InvalidDataAccessApiUsageException("Not a " + what + ": " + name);
	}

	/** The values of an array or collection for {@code IN (...)}: an empty or missing one failed as {@code IN ()}. */
	private static List<Object> inList(Object values, String name) {
		List<Object> list = new ArrayList<>();
		if (values instanceof Object[] array) {
			list.addAll(Arrays.asList(array));
		} else if (values instanceof Collection<?> collection) {
			list.addAll(collection);
		}
		if (list.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No values for IN () in " + name + ": " + values);
		}
		return list;
	}

	/** (?n, ?n+1, ...) for the values, which are added to the parameters. */
	private static String inPlaceholders(List<Object> values, List<Object> parameters) {
		StringBuilder text = new StringBuilder("(");
		for (Object value : values) {
			parameters.add(value);
			text.append(text.length() > 1 ? ", " : "").append('?').append(parameters.size());
		}
		return text.append(")").toString();
	}
}
