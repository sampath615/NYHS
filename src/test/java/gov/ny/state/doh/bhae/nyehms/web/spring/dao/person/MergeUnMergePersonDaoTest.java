package gov.ny.state.doh.bhae.nyehms.web.spring.dao.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.BadlyMergedStatenosVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import jakarta.persistence.ParameterMode;

/**
 * The merge / un-merge DAO: the JPQL statements against an in-memory H2 database in Oracle mode (db/merge.sql), next
 * to their original SQL; the procedure calls, the dynamic validation and the dependent-document query checked without
 * a database (procedure, parameter modes / types / values, the outputs put back into the map, the SQL text).
 */
@SpringJUnitConfig(MergeUnMergePersonDaoTest.TestConfig.class)
class MergeUnMergePersonDaoTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, MergeUnMergePersonDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("mergedao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/merge.sql").build();
		}
	}

	@Autowired MergeUnMergePersonDAO dao;
	@Autowired NativeStatements statements;

	// ---- the review lists, next to the original SQL --------------------------------------------------------------

	private static Map<String, Object> values(Object vo) throws Exception {
		Map<String, Object> values = new HashMap<>();
		for (var method : vo.getClass().getMethods()) {
			if (method.getName().startsWith("get") && method.getParameterCount() == 0 && method.getDeclaringClass() != Object.class) {
				values.put(method.getName(), method.invoke(vo));
			}
		}
		return values;
	}

	@Test
	void flaggedPairsMatchTheOriginalSelect() throws Exception {
		List<FlaggedStatenoVO> pairs = dao.getFlaggedStatenosList();
		List<FlaggedStatenoVO> original = statements.list(
				"SELECT duplication_id, flagged_by, flagged_date, request_comments, stateno_1, stateno_2, flagged_earlier, stateno_1_bad_flag, stateno_2_bad_flag, origin, FOLLOWUP FROM nyehms_new_flagged_same_vw",
				MergeUnMergePersonDAO.STATENO_PAIRS);

		assertThat(pairs).hasSize(2);
		for (int i = 0; i < pairs.size(); i++) {
			assertThat(values(pairs.get(i))).as("pair " + i).isEqualTo(values(original.get(i)));
		}
		FlaggedStatenoVO first = pairs.get(0);
		assertThat(first.getDuplicationId()).isEqualTo(1L);
		assertThat(first.getFlaggedBy()).isEqualTo("Jane Doe");
		assertThat(first.getFlaggedDate()).isEqualTo("2021-03-04 10:11:12");         // the date as the database's text, as before
		assertThat(first.getStateno1()).isEqualTo("S1");
		assertThat(first.getStateno2BadFlag()).isEqualTo("Y");
		assertThat(first.getOriginCategorieVal()).isEqualTo("Lab match");
		assertThat(first.getFollowUp()).isEqualTo("Y");
		assertThat(pairs.get(1).getRequestComments()).isNull();
		assertThat(pairs.get(1).getStatusCode()).isNull();                          // not selected, as before
	}

	@Test
	void badMergesMatchTheOriginalSelect() throws Exception {
		List<BadlyMergedStatenosVO> list = dao.getBadlyMergedStatenosList();
		List<BadlyMergedStatenosVO> original = statements.list(
				"SELECT unmerge_id, stateno, flagged_by,  flagged_date, status_cd,  request_comments, flagged_earlier FROM nyehms_new_flagged_badmerge_vw",
				MergeUnMergePersonDAO.BADLY_MERGED_STATENOS);

		assertThat(list).hasSize(1);
		assertThat(values(list.get(0))).isEqualTo(values(original.get(0)));
		assertThat(list.get(0).getUnMergeId()).isEqualTo(10L);
		assertThat(list.get(0).getStateno()).isEqualTo("S9");
		assertThat(list.get(0).getFlaggedDate()).isEqualTo("2022-01-02 08:00:00");
		assertThat(list.get(0).getStatusCode()).isEqualTo("NEW");
	}

	// ---- the update and the insert ----------------------------------------------------------------------------------

	@Test
	void followUpUpdateAndDuplicateReviewRowWriteAsBefore() {
		Map<Object, Object> update = new HashMap<>();
		update.put("duplicationId", 2L);
		update.put("followUp", "Y");
		update.put("workerId", 42);
		assertThat(dao.updateMergeFollowUp(update)).isEqualTo(1);
		Map<String, Object> row = statements.one("select FOLLOWUP, LAST_UPDATED_BY, LAST_UPDATED_DATE from NYEHMS_DUPLICATE_STATENOS where DUPLICATION_ID = 2", Rows.map());
		assertThat(row.get("FOLLOWUP")).isEqualTo("Y");
		assertThat((BigDecimal) row.get("LAST_UPDATED_BY")).isEqualByComparingTo(BigDecimal.valueOf(42));
		assertThat(row.get("LAST_UPDATED_DATE")).isInstanceOf(Timestamp.class);
		update.put("duplicationId", 999L);
		assertThat(dao.updateMergeFollowUp(update)).isZero();

		Map<String, Object> review = new HashMap<>();
		review.put("DOCUMENT_ID", 500);
		review.put("NYEHMS_STATENO", "S2");
		dao.addACRFDuplicateReviewData(review);
		Map<String, Object> ridr = statements.one("select * from RAW_ACRF_RIDR_INFO where ACRF_DOC_ID = 500", Rows.map());
		assertThat((BigDecimal) ridr.get("DUPLICATE_STATUS")).isEqualByComparingTo(BigDecimal.valueOf(2));
		assertThat(ridr.get("STATE_CD")).isEqualTo("NY");
		assertThat(ridr.get("COMMENTS")).isEqualTo("Duplicate Review NYEHMS");
		assertThat(ridr.get("STATENO")).isEqualTo("E250");                            // the highest mapped eHARS state number

		review.put("DOCUMENT_ID", 501);
		review.put("NYEHMS_STATENO", "unknown");
		dao.addACRFDuplicateReviewData(review);
		assertThat(statements.one("select * from RAW_ACRF_RIDR_INFO where ACRF_DOC_ID = 501", Rows.map())).doesNotContainKey("STATENO");   // no mapping: a null state number, as before
	}

	// ---- the procedures, without a database ------------------------------------------------------------------------

	private record Call(String procedure, CallParam[] parameters) {
	}

	/** A DAO over a mocked NativeStatements whose call returns the given values (one per parameter). */
	private static Object[] mocked(List<Object> results) {
		NativeStatements statements = mock(NativeStatements.class);
		when(statements.call(any(), any(CallParam[].class))).thenReturn(results);
		return new Object[] {new MergeUnMergePersonDAO(statements, null), statements};
	}

	private static Call captured(NativeStatements statements) {
		ArgumentCaptor<String> procedure = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<CallParam[]> parameters = ArgumentCaptor.forClass(CallParam[].class);
		verify(statements).call(procedure.capture(), parameters.capture());
		return new Call(procedure.getValue(), parameters.getValue());
	}

	private static void assertParameter(CallParam parameter, ParameterMode mode, Class<?> type, Object value) {
		assertThat(parameter.mode()).isEqualTo(mode);
		assertThat(parameter.type()).isEqualTo(type);
		assertThat(parameter.value()).isEqualTo(value);
	}

	private static List<Object> results(int count, Map<Integer, Object> outputs) {
		List<Object> results = new ArrayList<>(Collections.nCopies(count, null));
		outputs.forEach(results::set);
		return results;
	}

	@Test
	void flagSameCallsTheProcedureWithTheValuesTypesAndPutsTheOutputsBack() {
		Object[] mocked = mocked(results(8, Map.of(5, 77, 6, 0, 7, "ok")));
		MergeUnMergePersonDAO dao = (MergeUnMergePersonDAO) mocked[0];
		Map<Object, Object> map = new HashMap<>();
		map.put("p_stateno1", "S1");
		map.put("p_stateno2", "S2");
		map.put("p_flagged_by", 42);
		map.put("p_request_comments", null);
		map.put("p_originCategorie", "3");                                           // a text for a number parameter, converted by the database as before

		dao.flagStatenoPairsAsSame(map);

		Call call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.pr_flag_same");
		assertThat(call.parameters()).hasSize(8);
		assertParameter(call.parameters()[0], ParameterMode.IN, String.class, "S1");
		assertParameter(call.parameters()[2], ParameterMode.IN, Integer.class, 42);
		assertParameter(call.parameters()[3], ParameterMode.IN, String.class, null);  // a null bound as VARCHAR, the mapper's jdbcType
		assertParameter(call.parameters()[4], ParameterMode.IN, String.class, "3");
		assertParameter(call.parameters()[5], ParameterMode.OUT, Integer.class, null);
		assertParameter(call.parameters()[6], ParameterMode.OUT, Integer.class, null);
		assertParameter(call.parameters()[7], ParameterMode.OUT, String.class, null);
		assertThat(map).containsEntry("p_duplication_id", 77).containsEntry("p_code_out", 0).containsEntry("p_msg_out", "ok");
	}

	@Test
	void mergeRejectUnmergeAndNewStatenoPutTheirOutputsBack() {
		Object[] mocked = mocked(results(7, Map.of(4, "Y", 5, 0, 6, "merged")));
		MergeUnMergePersonDAO dao = (MergeUnMergePersonDAO) mocked[0];
		Map<Object, Object> map = new HashMap<>();
		map.put("p_duplication_id", 77L);
		map.put("p_review_comments", "ok");
		map.put("p_worker_id", 42);
		map.put("p_mergedState_No", "S1");
		dao.mergeStatenos(map);
		Call call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.PR_MERGE_STATENOS");
		assertParameter(call.parameters()[0], ParameterMode.IN, Long.class, 77L);       // bound with its own type, as MyBatis bound a long
		assertParameter(call.parameters()[4], ParameterMode.OUT, String.class, null);
		assertThat(map).containsEntry("p_similar_pairs_exist", "Y").containsEntry("p_code_out", 0).containsEntry("p_msg_out", "merged");

		mocked = mocked(results(6, Map.of(3, "N", 4, 1, 5, "no")));
		dao = (MergeUnMergePersonDAO) mocked[0];
		map.clear();
		map.put("p_duplication_id", 77L);
		dao.rejectStatenoFlagPair(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.pr_reject_flag_same");
		assertThat(call.parameters()).hasSize(6);
		assertThat(map).containsEntry("p_similar_pairs_exist", "N").containsEntry("p_code_out", 1).containsEntry("p_msg_out", "no");

		mocked = mocked(results(7, Map.of(4, 0, 5, "moved")));
		dao = (MergeUnMergePersonDAO) mocked[0];
		map.clear();
		map.put("p_document_ids", "1,2");
		map.put("p_from_stateno", "S1");
		map.put("p_to_stateno", "S2");
		map.put("p_worker_id", 42);
		map.put("p_doccs_document_list", "9");
		dao.unMergeTheDocuments(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.PR_UNMERGE_DOCUMENTS");
		assertParameter(call.parameters()[6], ParameterMode.IN, String.class, "9");     // the trailing IN parameter after the OUTs
		assertThat(map).containsEntry("p_code_out", 0).containsEntry("p_msg_out", "moved");

		mocked = mocked(results(2, Map.of(1, "S777")));
		dao = (MergeUnMergePersonDAO) mocked[0];
		map.clear();
		map.put("p_caller", "Unmerge");
		dao.createNewStateno(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_document_management.pr_generate_new_stateno");
		assertThat(map).containsEntry("p_new_stateno_out", "S777");
	}

	@Test
	void badMergeFlagsPutTheirOutputsBack() {
		Object[] mocked = mocked(results(6, Map.of(3, 55, 4, 0, 5, "flagged")));
		MergeUnMergePersonDAO dao = (MergeUnMergePersonDAO) mocked[0];
		Map<Object, Object> map = new HashMap<>();
		map.put("p_stateno", "S9");
		map.put("p_flagged_by", 42);
		map.put("p_request_comments", "bad");
		dao.flagBadMerge(map);
		Call call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.pr_flag_bad_merge");
		assertThat(map).containsEntry("p_unmerge_id", 55).containsEntry("p_code_out", 0).containsEntry("p_msg_out", "flagged");

		mocked = mocked(results(5, Map.of(3, 0, 4, "unflagged")));
		dao = (MergeUnMergePersonDAO) mocked[0];
		map.clear();
		map.put("p_stateno", "S9");
		map.put("p_worker_id", 42);
		map.put("p_review_comments", "fine");
		dao.unflagBadMerge(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.pr_unflag_bad_merge");
		assertThat(call.parameters()).hasSize(5);
		assertThat(map).containsEntry("p_code_out", 0).containsEntry("p_msg_out", "unflagged");
	}

	@Test
	void cursorsAreReadAsTextWithTheFormerResultMaps() {
		List<Object> history = List.of(new FlaggedStatenoVO());
		Object[] mocked = mocked(results(5, Map.of(2, history, 3, 0, 4, "ok")));
		MergeUnMergePersonDAO dao = (MergeUnMergePersonDAO) mocked[0];
		Map<Object, Object> map = new HashMap<>();
		map.put("p_stateno_1", "S1");
		map.put("p_stateno_2", "S2");
		dao.flagHistory(map);
		Call call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.pr_get_flag_history");
		assertThat(call.parameters()[2].mode()).isEqualTo(ParameterMode.REF_CURSOR);
		assertThat(call.parameters()[2].type()).isEqualTo(String.class);                // read as text
		assertThat(call.parameters()[2].cursorRows()).isSameAs(MergeUnMergePersonDAO.STATENO_PAIRS);
		assertThat(map).containsEntry("p_flag_history", history).containsEntry("p_code_out", 0).containsEntry("p_msg_out", "ok");

		mocked = mocked(results(2, Map.of(1, history)));
		dao = (MergeUnMergePersonDAO) mocked[0];
		map.clear();
		map.put("p_duplication_id", "77");
		dao.getSimilarFlaggedPairs(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.pr_get_similar_flagged_pairs");
		assertParameter(call.parameters()[0], ParameterMode.IN, String.class, "77");
		assertThat(map).containsEntry("p_similar_pairs_to_review", history);

		List<Object> badHistory = List.of(new BadlyMergedStatenosVO());
		mocked = mocked(results(4, Map.of(1, badHistory, 2, 0, 3, "ok")));
		dao = (MergeUnMergePersonDAO) mocked[0];
		map.clear();
		map.put("p_stateno", "S9");
		dao.getBadMergeFlagHistory(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.pr_get_bad_merge_flag_history");
		assertThat(call.parameters()[1].cursorRows()).isSameAs(MergeUnMergePersonDAO.BADLY_MERGED_STATENOS);
		assertThat(map).containsEntry("p_flag_history", badHistory);

		// the ten cursors of the merge screen, in order
		Map<Integer, Object> outputs = new HashMap<>();
		for (int i = 1; i <= 10; i++) {
			outputs.put(i, List.of("cursor " + i));
		}
		outputs.put(11, 0);
		outputs.put(12, "ok");
		mocked = mocked(results(15, outputs));
		dao = (MergeUnMergePersonDAO) mocked[0];
		map.clear();
		map.put("p_duplication_id", "77");
		map.put("p_module_id", "16");
		map.put("p_access_user_id", 42);
		dao.fetchPersonObjects(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.pr_fetch_to_merge_statenos");
		assertThat(call.parameters()).hasSize(15);
		RowMapper<?>[] cursors = {PersonResultMaps.PERSON_VIEW, PersonResultMaps.PERSON_VIEW, PersonResultMaps.PERSON_DOC_HISTORY,
				PersonResultMaps.PERSON_DOC_HISTORY, PersonResultMaps.PERSON_RACE, PersonResultMaps.PERSON_RACE, PersonResultMaps.PERSON_RISKS,
				PersonResultMaps.PERSON_RISKS, MergeUnMergePersonDAO.STATENO_HISTORY_RECORDS, MergeUnMergePersonDAO.STATENO_HISTORY_RECORDS};
		for (int i = 0; i < cursors.length; i++) {
			assertThat(call.parameters()[i + 1].mode()).as("cursor " + (i + 1)).isEqualTo(ParameterMode.REF_CURSOR);
			assertThat(call.parameters()[i + 1].cursorRows()).as("cursor " + (i + 1)).isSameAs(cursors[i]);
		}
		assertParameter(call.parameters()[13], ParameterMode.IN, String.class, "16");
		assertParameter(call.parameters()[14], ParameterMode.IN, Integer.class, 42);
		assertThat(map).containsEntry("p_stateno_1_details", List.of("cursor 1")).containsEntry("p_stateno_2_mrg_history", List.of("cursor 10"))
				.containsEntry("p_doc_2_timeline", List.of("cursor 4")).containsEntry("p_code_out", 0).containsEntry("p_msg_out", "ok");
	}

	@Test
	void personResultMapsSetTheNamedColumnsAndAutoMapTheRest() {
		PersonViewVO person = PersonResultMaps.PERSON_VIEW.map(new Row(List.of("FIRST_NAME", "MED_RECORD", "BIRTH_DATE", "NYC_CITYNO", "UNKNOWN_COLUMN"),
				List.of("JOHN", "MR1", "01/02/1980", "C1", "x")));
		assertThat(person.getFNAME()).isEqualTo("JOHN");
		assertThat(person.getMED_RECORD_NUMBER()).isEqualTo("MR1");
		assertThat(person.getDOB()).isEqualTo("01/02/1980");
		assertThat(person.getNYC_CITYNO()).isEqualTo("C1");                           // auto-mapped
		assertThat(person.getLNAME()).isEmpty();                                       // not in the row: the VO's "" stays
		assertThat(PersonResultMaps.PERSON_VIEW.typeOf("BIRTH_DATE", 2)).isEqualTo(String.class);
		assertThat(PersonResultMaps.PERSON_VIEW.typeOf("UNKNOWN_COLUMN", 4)).isNull();
		assertThat(MergeUnMergePersonDAO.STATENO_HISTORY_RECORDS.map(new Row(List.of("sno"), List.of("S1")))).isEqualTo("S1");
		assertThat(MergeUnMergePersonDAO.STATENO_PAIRS.typeOf("duplication_id", 0)).isEqualTo(long.class);
	}

	// ---- the native statements, without a database -------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	private static Object[] capturedQuery(NativeStatements statements) {
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(statements).list(sql.capture(), any(RowMapper.class), parameters.capture());
		return new Object[] {sql.getValue(), parameters.getValue()};
	}

	@SuppressWarnings("unchecked")
	private static Object[] capturedOne(NativeStatements statements) {
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(statements).one(sql.capture(), any(RowMapper.class), parameters.capture());
		return new Object[] {sql.getValue(), parameters.getValue()};
	}

	@Test
	void dodValidationBindsTheListsAndTheStateNumber() {
		NativeStatements mocked = mock(NativeStatements.class);
		MergeUnMergePersonDAO dao = new MergeUnMergePersonDAO(mocked, null);
		Map<String, Object> input = new HashMap<>();
		input.put("doccsDocumentIds", new String[] {"0"});
		input.put("unmergeDocuments", "11,12".split(","));
		input.put("stateno_target", "S2");

		assertThat(dao.validateDodAgainstCollectionDate(input)).isNull();              // one row at most: no row is null

		Object[] query = capturedOne(mocked);
		String sql = ((String) query[0]).replaceAll("\\s+", " ");
		assertThat(sql).contains("WHERE doccs_doc_id IN (?1)").contains("AND (a.document_id IN (?2, ?3) OR p.stateno = ?4)")
				.contains("FROM nyehms_owner.std_din_doccs_doc").contains("AND a.document_id = d.document_id(+)")
				.contains("GREATEST(Nvl(hiv_col_date,To_Date('01/01/1900','MM/DD/YYYY')), Nvl(std_col_date,To_Date('01/01/1900','MM/DD/YYYY'))) > Nvl(dod,To_Date('12/31/2999','MM/DD/YYYY'))");
		assertThat((Object[]) query[1]).containsExactly("0", "11", "12", "S2");

		input.put("unmergeDocuments", null);                                           // IN () failed before
		assertThatThrownBy(() -> dao.validateDodAgainstCollectionDate(input)).isInstanceOf(InvalidDataAccessResourceUsageException.class);
	}

	@Test
	void dependentDocumentsBindTheOneIdEverywhere() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		new MergeUnMergePersonDAO(mocked, null).getDependentDocumentList(123);
		Object[] query = capturedQuery(mocked);
		assertThat(((String) query[0]).split("\\?1", -1)).hasSize(20);                  // the 19 occurrences of #{documentId}
		assertThat((String) query[0]).doesNotContain("?2").contains("UNPIVOT (document_id FOR data IN (resistance_doc_id_1, resistance_doc_id_2, resistance_doc_id_3))");
		assertThat((Object[]) query[1]).containsExactly(123);

		assertThat(MergeUnMergePersonSql.COMMIT_CURRENT_TRANSACTION.replaceAll("\\s+", " ").trim()).isEqualTo("begin COMMIT; END;");
		Map<String, String> dates = new HashMap<>();
		dates.put("fromDate", "01/01/2024");
		dates.put("toDate", "01/31/2024");
		assertThat(MergeUnMergePersonSql.getMergeReportsByDailyStaffArgs(dates)).containsExactly("01/01/2024", "01/31/2024");
		assertThat(MergeUnMergePersonSql.MERGE_REPORTS_BY_DAILY_STAFF).contains("between to_date(?1, 'MM/DD/YYYY') and to_date(?2, 'MM/DD/YYYY')+1");
	}
}
