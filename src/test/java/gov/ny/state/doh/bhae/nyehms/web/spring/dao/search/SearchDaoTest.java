package gov.ny.state.doh.bhae.nyehms.web.spring.dao.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.search.SearchPersonCriteriaVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import jakarta.persistence.ParameterMode;

/**
 * The person search DAO against its former MyBatis mapper (SearchDAO.xml, from Git): the two procedure calls and the
 * ROWID query checked without a database (the procedure, parameter modes, types and values; the SQL text and bound
 * values), the two lookups run on an in-memory H2 database in Oracle mode (db/search.sql) next to their original SQL.
 */
@SpringJUnitConfig(SearchDaoTest.TestConfig.class)
public class SearchDaoTest {          // public: H2 calls the FN_GIVE_CURRENT_STATENO stand-in below by reflection

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, SearchDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("searchdao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/search.sql").build();
		}
	}

	/** H2 stand-in for PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO (CREATE ALIAS in db/search.sql). */
	public static String currentStateno(String stateno) {
		if (stateno == null) {
			return null;
		}
		if (stateno.startsWith("OLD")) {
			return "NEW" + stateno.substring(3);
		}
		return "GONE".equals(stateno) ? "-1" : stateno;
	}

	@Autowired SearchDAO searchDAO;
	@Autowired NativeStatements statements;

	// ---- the two search procedures (former CALLABLE statements), checked without a database ----------------------

	private static Map<Object, Object> searchMap() {
		Map<Object, Object> map = new HashMap<>();
		map.put("p_stateno", "100001");
		map.put("p_first_name", "JOHN");
		map.put("p_last_name", null);
		map.put("p_date_of_birth", "01/02/1980");
		map.put("p_date_of_death", null);
		map.put("p_ssn", "123456789");
		map.put("p_nysid", null);
		map.put("p_din", "99A1234");
		map.put("p_worker_id", 42);
		map.put("refCursor", new ArrayList<>());
		map.put("p_flag_doc_only_search", "N");     // in the map, never a parameter of the procedures (as before)
		map.put("p_calling_app_name", "NYEHMS");
		map.put("p_cityno", "C1");
		return map;
	}

	/** The parameters the XML bound, in order; the cursor is the ninth. */
	private static void assertSearchParameters(CallParam[] parameters, RowMapper<?> cursorRows) {
		assertThat(parameters).hasSize(11);
		Object[][] expected = {
				{ParameterMode.IN, String.class, "100001"}, {ParameterMode.IN, String.class, "JOHN"}, {ParameterMode.IN, String.class, null},
				{ParameterMode.IN, java.sql.Date.class, "01/02/1980"}, {ParameterMode.IN, java.sql.Date.class, null},
				{ParameterMode.IN, String.class, "123456789"}, {ParameterMode.IN, String.class, null}, {ParameterMode.IN, String.class, "99A1234"},
				{ParameterMode.REF_CURSOR, String.class, null},                   // read as text, as the result maps did
				{ParameterMode.IN, Integer.class, 42}, {ParameterMode.IN, String.class, "C1"}};
		for (int i = 0; i < expected.length; i++) {
			assertThat(parameters[i].mode()).as("mode of parameter " + (i + 1)).isEqualTo(expected[i][0]);
			assertThat(parameters[i].type()).as("type of parameter " + (i + 1)).isEqualTo(expected[i][1]);
			assertThat(parameters[i].value()).as("value of parameter " + (i + 1)).isEqualTo(expected[i][2]);
		}
		assertThat(parameters[8].cursorRows()).isSameAs(cursorRows);
	}

	@Test
	void personSearchCallsTheProcedureAndPutsTheRowsIntoTheMap() {
		NativeStatements mocked = mock(NativeStatements.class);
		List<Object> rows = List.of(new SearchPersonCriteriaVO());
		List<Object> results = new ArrayList<>(java.util.Collections.nCopies(11, null));
		results.set(8, rows);
		when(mocked.call(eq("PKG_SEARCH.PR_PERSON_SEARCH"), any(CallParam[].class))).thenReturn(results);
		Map<Object, Object> map = searchMap();

		List<SearchPersonCriteriaVO> returned = new SearchDAO(mocked, null).searchPersonFields(map);

		ArgumentCaptor<CallParam[]> captor = ArgumentCaptor.forClass(CallParam[].class);
		verify(mocked).call(eq("PKG_SEARCH.PR_PERSON_SEARCH"), captor.capture());
		assertSearchParameters(captor.getValue(), SearchDAO.SEARCH_PERSON_ROWS);
		assertThat(map.get("refCursor")).isSameAs(rows);
		assertThat(returned).isEmpty();
	}

	@Test
	void quickSearchCallsTheQuickProcedureAndPutsTheRowIdsIntoTheMap() {
		NativeStatements mocked = mock(NativeStatements.class);
		List<Object> rows = List.of("AAAR3sAAEAAAACXAAA", "AAAR3sAAEAAAACXAAB");
		List<Object> results = new ArrayList<>(java.util.Collections.nCopies(11, null));
		results.set(8, rows);
		when(mocked.call(eq("PKG_SEARCH.PR_PERSON_SEARCH_QUICK"), any(CallParam[].class))).thenReturn(results);
		Map<Object, Object> map = searchMap();

		new SearchDAO(mocked, null).searchPersonFieldsWithQuickSearch(map);

		ArgumentCaptor<CallParam[]> captor = ArgumentCaptor.forClass(CallParam[].class);
		verify(mocked).call(eq("PKG_SEARCH.PR_PERSON_SEARCH_QUICK"), captor.capture());
		assertSearchParameters(captor.getValue(), SearchDAO.ROW_ID_ROWS);
		assertThat(map.get("refCursor")).isSameAs(rows);
	}

	@Test
	void mapsTheCursorRowsAsTheFormerResultMapsDid() {
		// searchPersonResult: named columns to properties, other columns auto-mapped, nulls not set, VISIBLE_TO_WORKER
		// (a property the VO does not have) ignored
		Row row = new Row(
				List.of("STATENO", "FIRST_NAME", "LAST_NAME", "DATE_OF_BIRTH", "SSN", "DIN", "NYSID", "DATE_OF_DEATH", "VISIBLE_TO_WORKER", "FLAG_BAD_OR_SAME", "CITYNO"),
				Arrays.asList("100001", "JOHN", null, "1980-01-02 00:00:00", "123456789", "99A1234", "N1", null, "Y", "S", "C1"));
		SearchPersonCriteriaVO vo = SearchDAO.SEARCH_PERSON_ROWS.map(row);
		assertThat(vo.getStateNo()).isEqualTo("100001");
		assertThat(vo.getFirstName()).isEqualTo("JOHN");
		assertThat(vo.getLastName()).isNull();
		assertThat(vo.getDateOfBirth()).isEqualTo("1980-01-02 00:00:00");
		assertThat(vo.getSsn()).isEqualTo("123456789");
		assertThat(vo.getDin()).isEqualTo("99A1234");
		assertThat(vo.getNysid()).isEqualTo("N1");
		assertThat(vo.getDateOfDeath()).isNull();
		assertThat(vo.getFlaggedSameOrBadInd()).isEqualTo("S");
		assertThat(vo.getCityNo()).isEqualTo("C1");
		assertThat(SearchDAO.SEARCH_PERSON_ROWS.map(new Row(List.of("STATENO", "SSN"), Arrays.asList(null, null)))).isNull();

		// rowIdMap: the RWID column as a String
		assertThat(SearchDAO.ROW_ID_ROWS.map(new Row(List.of("RWID"), List.of("AAAR3sAAEAAAACXAAA")))).isEqualTo("AAAR3sAAEAAAACXAAA");
	}

	// ---- the ROWID query (native), checked without a database against the XML text ------------------------------

	/** personSearchResultsWithQuickSearch as the XML had it, the foreach and the order by as the placeholders. */
	private static final String ORIGINAL_QUICK_SEARCH_RESULTS = """
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
			           %s

			            %s
			""";

	private static String normalized(String sql) {
		return sql.replaceAll("\\s+", " ").trim();
	}

	@SuppressWarnings("unchecked")
	private static List<Object[]> capturedList(NativeStatements mocked, int times) {
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked, org.mockito.Mockito.times(times)).list(sql.capture(), any(RowMapper.class), parameters.capture());
		List<Object[]> calls = new ArrayList<>();
		for (int i = 0; i < times; i++) {
			calls.add(new Object[] {sql.getAllValues().get(i), parameters.getAllValues().get(i)});
		}
		return calls;
	}

	@Test
	void quickSearchResultsRunTheOriginalSqlWithBoundRowIdsAndAnAllowedOrder() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		SearchDAO dao = new SearchDAO(mocked, null);
		List<String> rowIds = List.of("AAAR3sAAEAAAACXAAA", "AAAR3sAAEAAAACXAAB", "AAAR3sAAEAAAACXAAC");

		Map<Object, Object> map = new HashMap<>();
		map.put("rowIdList", rowIds);
		map.put("sortByColumn", "lastName");
		map.put("sortOrder", "desc");
		dao.personSearchResultsWithQuickSearch(map);

		map.put("sortByColumn", "");                                                          // no order
		dao.personSearchResultsWithQuickSearch(map);

		map.put("sortByColumn", "dateOfBirth");
		map.put("sortOrder", "");                                                              // the database's default order
		dao.personSearchResultsWithQuickSearch(map);

		List<Object[]> calls = capturedList(mocked, 3);
		assertThat(normalized((String) calls.get(0)[0]))
				.isEqualTo(normalized(ORIGINAL_QUICK_SEARCH_RESULTS.formatted("(?1, ?2, ?3)", "order by lastName desc")));
		assertThat((Object[]) calls.get(0)[1]).containsExactly(rowIds.toArray());
		assertThat(normalized((String) calls.get(1)[0])).isEqualTo(normalized(ORIGINAL_QUICK_SEARCH_RESULTS.formatted("(?1, ?2, ?3)", "")));
		assertThat(normalized((String) calls.get(2)[0]))
				.isEqualTo(normalized(ORIGINAL_QUICK_SEARCH_RESULTS.formatted("(?1, ?2, ?3)", "order by dateOfBirth")));
	}

	@Test
	void quickSearchResultsRefuseWhatWasPastedIntoTheSqlBefore() {
		SearchDAO dao = new SearchDAO(mock(NativeStatements.class), null);
		Map<Object, Object> map = new HashMap<>();
		map.put("rowIdList", List.of("AAAR3sAAEAAAACXAAA"));
		map.put("sortByColumn", "lastName; delete from person");
		map.put("sortOrder", "asc");
		assertThatThrownBy(() -> dao.personSearchResultsWithQuickSearch(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		map.put("sortByColumn", "lastName");
		map.put("sortOrder", "asc, 1=1");
		assertThatThrownBy(() -> dao.personSearchResultsWithQuickSearch(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		map.put("sortOrder", null);                                                            // "order by lastName null" failed before
		assertThatThrownBy(() -> dao.personSearchResultsWithQuickSearch(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		map.put("sortOrder", "ASC");
		map.put("rowIdList", List.of());                                                       // IN () failed before
		assertThatThrownBy(() -> dao.personSearchResultsWithQuickSearch(map)).isInstanceOf(InvalidDataAccessResourceUsageException.class);
	}

	// ---- the two lookups, on H2 next to their original SQL ------------------------------------------------------

	@Test
	void activeStatenoLookupGivesTheCurrentNumberAndTheActiveFlag() {
		assertThat(searchDAO.getTheActiveStateNo("100001")).containsOnly(Map.entry("CURRENTSTATENO", "100001"), Map.entry("FLAGACTIVE", "Y"));
		assertThat(searchDAO.getTheActiveStateNo("OLD200")).containsOnly(Map.entry("CURRENTSTATENO", "NEW200"), Map.entry("FLAGACTIVE", "N"));
		assertThat(searchDAO.getTheActiveStateNo("300003")).containsOnly(Map.entry("CURRENTSTATENO", "300003"));    // null flag left out
		assertThat(searchDAO.getTheActiveStateNo("GONE")).containsOnly(Map.entry("CURRENTSTATENO", "-1"));           // no person: no flag
		assertThat(searchDAO.getTheActiveStateNo("100001")).isExactlyInstanceOf(HashMap.class);
	}

	@Test
	void cityNumberLookupMatchesTheOriginalSql() {
		String original = "select PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO (NYEHMS_STATENO) currentStateno from EHARS_PERSON_NYC where CITYNO = ?1 and rownum < 2";
		for (String cityNo : List.of("C1", "C2", "C3", "C4", "none")) {
			Map<String, String> expected = (Map) statements.one(original, Rows.map(), cityNo);
			assertThat(searchDAO.getNyehmsStatenoUsingCityNo(cityNo)).as("city number " + cityNo).isEqualTo(expected);
		}
		assertThat(searchDAO.getNyehmsStatenoUsingCityNo("C1")).containsOnly(Map.entry("CURRENTSTATENO", "100001"));
		assertThat(searchDAO.getNyehmsStatenoUsingCityNo("C2")).containsOnly(Map.entry("CURRENTSTATENO", "NEW200"));
		assertThat(searchDAO.getNyehmsStatenoUsingCityNo("C3")).isNull();                      // a null number: a null row, as before
		assertThat(searchDAO.getNyehmsStatenoUsingCityNo("C4")).containsOnly(Map.entry("CURRENTSTATENO", "-1"));
		assertThat(searchDAO.getNyehmsStatenoUsingCityNo("none")).isNull();
	}
}
