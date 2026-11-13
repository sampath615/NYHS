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
import java.util.Set;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import jakarta.persistence.ParameterMode;

/**
 * The person DAO: the JPQL statements and the cluster query against an in-memory H2 database in Oracle mode
 * (db/person.sql), next to their original SQL; the procedure calls and the other native statements (Oracle-only SQL)
 * checked without a database (procedure, parameter modes / types / values, the outputs put back into the map, the SQL
 * text and the bound values).
 */
@SpringJUnitConfig(PersonDaoTest.TestConfig.class)
class PersonDaoTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, PersonDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("persondao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/person.sql").build();
		}
	}

	@Autowired PersonDAO dao;
	@Autowired NativeStatements statements;

	// ---- the person and its links, next to the original SQL ------------------------------------------------------

	@Test
	void activeFlagAndEharsLinksMatchTheOriginalSelects() {
		assertThat(dao.checkStatenoIsActive("S1")).isEqualTo(statements.one("select FLAG_ACTIVE from PERSON where STATENO = ?1", Rows.scalar(String.class), "S1")).isEqualTo("Y");
		assertThat(dao.checkStatenoIsActive("S2")).isNull();
		assertThat(dao.checkStatenoIsActive("nobody")).isNull();

		String original = "select distinct EHARS_STATENO from nyehms_ehars_stateno_map where NYEHMS_STATENO = ?1 and NVL(STATUS_FLAG, 'XX') not in ('D', 'M', 'P')";
		assertThat(dao.getEharsStateno("S1")).isEqualTo(statements.list(original, Rows.scalar(String.class), "S1")).containsExactly("E1");
		assertThat(dao.getEharsStateno("S3")).isEmpty();

		Map<Object, Object> map = new HashMap<>();
		map.put("statenos", new String[] {"S1", "S2", "S3"});
		List<Map<String, String>> mappings = dao.getEharsStatenoMappings(map);
		List<Map<String, Object>> originalMappings = statements.list(
				"select EHARS_STATENO, NYEHMS_STATENO from nyehms_ehars_stateno_map where NYEHMS_STATENO in (?1, ?2, ?3) and NVL(STATUS_FLAG, 'XX') not in ('D', 'M', 'P')",
				Rows.map(), "S1", "S2", "S3");
		assertThat(new ArrayList<Object>(mappings)).containsExactlyInAnyOrderElementsOf(originalMappings);
		assertThat(mappings).hasSize(3).contains(Map.of("EHARS_STATENO", "E3", "NYEHMS_STATENO", "S2"));
		map.put("statenos", List.of("S2"));
		assertThat(dao.getEharsStatenoMappings(map)).containsExactly(Map.of("EHARS_STATENO", "E3", "NYEHMS_STATENO", "S2"));
		map.put("statenos", new String[0]);                                              // IN () failed before
		assertThatThrownBy(() -> dao.getEharsStatenoMappings(map)).isInstanceOf(InvalidDataAccessResourceUsageException.class);

		assertThat(dao.getNYSEharsDxDetails("S1")).isEqualTo(Map.of("STAGE_ZERO_DX", "0", "STAGE_CD4_OI_DX", "3"));
		assertThat(dao.getNYSEharsDxDetails("S2")).isEqualTo(Map.of("STAGE_CD4_OI_DX", "2"));    // a null is not put, as before
		assertThat(dao.getNYSEharsDxDetails("S3")).isNull();
	}

	@Test
	void nycLinksMatchTheOriginalSelects() {
		assertThat(dao.getNycEharsDetails("S1")).isEqualTo(statements.list("select distinct EHARS_STATENO from ehars_person_nyc where nyehms_stateno = ?1", Rows.scalar(String.class), "S1"))
				.containsExactlyInAnyOrder("N1", "N2");                                   // the status is not checked here, as before
		assertThat(dao.getNycCityNoDetails("S1")).containsExactlyInAnyOrder("C1", "C2");
		assertThat(dao.getNycCityNoDetails("nobody")).isEmpty();
		assertThat(dao.getNYCEharsDxDetails("S1")).isEqualTo(Map.of("STAGE_ZERO_DX", "1", "STAGE_CD4_OI_DX", "x"));
		assertThat(dao.getNYCEharsDxDetails("S2")).isNull();                               // status P
	}

	@Test
	void documentStatenoOriginsAndClustersMatchTheOriginalSelects() {
		assertThat(dao.getNyehmsStatenoForTheDocument(100)).isEqualTo("S1");
		assertThat(dao.getNyehmsStatenoForTheDocument(200)).isEqualTo("S2");              // a disassociation date does not matter here, as before
		assertThat(dao.getNyehmsStatenoForTheDocument(999)).isNull();

		List<Map<String, String>> origins = dao.getOriginCategories();
		assertThat(origins).hasSize(2);
		assertThat(origins.get(0).get("ORIGIN_DESC")).isEqualTo("Manual");               // by sort order
		assertThat(origins.get(1).get("ORIGIN_DESC")).isEqualTo("Lab match");
		assertThat(String.valueOf(origins.get(1).get("ORIGIN_CODE"))).isEqualTo("1");  // a Long now (a BigDecimal before); the JSP prints it
		assertThat(origins.get(1).get("ORIGIN_TYPE")).isEqualTo("M");

		List<ClusterUploadVO> clusters = dao.getStatenoClusterAssociation("S1");
		List<ClusterUploadVO> original = statements.list(PersonSql.STATENO_CLUSTER_ASSOCIATION, Rows.bean(ClusterUploadVO.class), "S1");
		assertThat(clusters).hasSameSizeAs(original).hasSize(2);
		for (int i = 0; i < clusters.size(); i++) {
			assertThat(clusters.get(i)).usingRecursiveComparison().isEqualTo(original.get(i));
		}
		Map<String, String> byId = new HashMap<>();
		for (ClusterUploadVO cluster : clusters) {
			byId.put(cluster.getClusterId(), cluster.getCurr_stateno() + "/" + cluster.getClusterType() + "/" + cluster.getClusterDate());
		}
		assertThat(byId).containsEntry("CL1", "S1/MOLE/2024-05-01 00:00:00").containsEntry("CL3", "null/SPACE/2024-05-03 00:00:00");   // CL2 is BHAE_TS
		assertThat(dao.getStatenoClusterAssociation("S2")).extracting(ClusterUploadVO::getCurr_stateno).containsExactly(null, null);
	}

	// ---- the partner services messages -----------------------------------------------------------------------------

	@Test
	void messageTypesAndReturnDocumentsMatchTheOriginalSelects() {
		Map<Object, Object> map = new HashMap<>();
		map.put("stateno", "S1");
		map.put("documentId", 600);
		assertThat(dao.getTriggeredMessageTypesForDocument(map)).containsExactlyInAnyOrder("NEW_DX", "VL");
		map.put("stateno", "");                                                          // an empty state number: by the follow-up document, as before
		assertThat(dao.getTriggeredMessageTypesForDocument(map)).containsExactly("DOD");
		map.remove("stateno");
		map.put("documentId", "500");
		assertThat(dao.getTriggeredMessageTypesForDocument(map)).containsExactlyInAnyOrder("NEW_DX", "VL");

		Map<String, Object> ids = new HashMap<>();
		ids.put("returnMessageDocIds", Set.of(100, 300));
		assertThat(dao.checkForExistingUpdatedMessageDocIds(ids)).containsExactly(100);
		assertThat(dao.getReturnDocIdNotExistInTable(ids)).containsExactly(400);
		ids.put("returnMessageDocIds", Set.of());
		assertThatThrownBy(() -> dao.checkForExistingUpdatedMessageDocIds(ids)).isInstanceOf(InvalidDataAccessResourceUsageException.class);

		Map<String, Object> dod = new HashMap<>();
		dod.put("docIdsDateOfDeathNull", List.of(100, 200, 300));
		List<Map<String, Object>> rows = dao.checkExistingDateOfDeathForMessageDocIds(dod);
		assertThat(rows).hasSize(1);                                                     // 200: disassociated; 300: no date of death
		assertThat(rows.get(0)).containsEntry("STATENO", "S1").containsEntry("DOCUMENT_ID", BigDecimal.valueOf(100)).containsEntry("FOLLOW_UP_DOC_ID", BigDecimal.valueOf(500));
		assertThat(rows).isEqualTo(statements.list("""
				SELECT distinct p.stateno, d.document_id, sent.FOLLOW_UP_DOC_ID FROM person_doc_association p, document_person_attr d, NYEHMS_STD_MESSAGE_SENT sent
				WHERE Date_of_Death IS NOT NULL AND p.document_id = d.document_id and d.document_id = sent.document_id and D.document_id IN (?1, ?2, ?3)
				AND DISSASSOCIATION_DATE IS NULL""", PersonDAO.DATE_OF_DEATH_ROWS, 100, 200, 300));

		assertThatThrownBy(() -> dao.checkForDuplicateDispositionCode(new UpdatedDispositionsVO())).isInstanceOf(InvalidDataAccessApiUsageException.class);
	}

	@Test
	void deletedDocumentsRetireTheirPendingMessagesAndRiaDocumentAsBefore() {
		Map<String, Object> input = new HashMap<>();
		input.put("p_workerId", 42);
		input.put("p_document_id", 500);
		dao.deletePendingPSMessageDocuments(input);
		List<Map<String, Object>> retired = statements.list("select DOCUMENT_ID, PROCESS_IND, REVIEW_COMMENTS, REVIEW_UID, REVIEW_DATE from NYEHMS_STD_MESSAGE_SENT where FOLLOW_UP_DOC_ID = 500 order by DOCUMENT_ID", Rows.map());
		assertThat(retired).hasSize(3);
		for (Map<String, Object> row : retired.subList(0, 2)) {                           // 100 and 300 were pending
			assertThat(row).containsEntry("PROCESS_IND", "R_X").containsEntry("REVIEW_COMMENTS", "Triggering Document Deleted").containsEntry("REVIEW_UID", "42");
			assertThat(row.get("REVIEW_DATE")).isInstanceOf(Timestamp.class);
		}
		assertThat(retired.get(2)).containsEntry("PROCESS_IND", "Y").doesNotContainKey("REVIEW_UID");   // 400 was processed

		input.put("p_document_id", "100");
		dao.deletePendingRiaDocument(input);
		List<Map<String, Object>> ria = statements.list("select DOCUMENT_ID, STATENO_DELETED, COMMENTS, LAST_UPDATED_BY, LAST_UPDATED_DATE from NYEHMS_RIA_DOC order by DOCUMENT_ID", Rows.map());
		assertThat(ria.get(0)).containsEntry("STATENO_DELETED", "Y").containsEntry("COMMENTS", "Triggering Document Deleted").containsEntry("LAST_UPDATED_BY", "42");
		assertThat(ria.get(0).get("LAST_UPDATED_DATE")).isInstanceOf(Timestamp.class);
		assertThat(ria.get(1)).doesNotContainKey("STATENO_DELETED");                     // completed
		assertThat(ria.get(2)).doesNotContainKey("STATENO_DELETED");                     // another lab document
	}

	// ---- the procedures, without a database ------------------------------------------------------------------------

	private record Call(String procedure, CallParam[] parameters) {
	}

	/** A DAO over a mocked NativeStatements whose call returns the given values (one per parameter). */
	private static Object[] mocked(List<Object> results) {
		NativeStatements statements = mock(NativeStatements.class);
		when(statements.call(any(), any(CallParam[].class))).thenReturn(results);
		return new Object[] {new PersonDAO(statements, null), statements};
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

	private static void assertTextCursor(CallParam parameter, RowMapper<?> rows) {
		assertThat(parameter.mode()).isEqualTo(ParameterMode.REF_CURSOR);
		assertThat(parameter.type()).isEqualTo(String.class);
		assertThat(parameter.cursorRows()).isSameAs(rows);
	}

	private static List<Object> results(int count, Map<Integer, Object> outputs) {
		List<Object> results = new ArrayList<>(Collections.nCopies(count, null));
		outputs.forEach(results::set);
		return results;
	}

	@Test
	void personDetailsCallTheProcedureAndPutTheCursorsBack() {
		List<Object> persons = List.of(new PersonViewVO());
		List<Object> timeline = List.of();
		Object[] mocked = mocked(results(8, Map.of(3, persons, 4, timeline)));
		PersonDAO dao = (PersonDAO) mocked[0];
		Map<Object, Object> map = new HashMap<>();
		map.put("p_data_req_type", "V");
		map.put("p_state_no", "S1");
		map.put("p_date", new java.util.Date(Timestamp.valueOf("2024-01-02 03:04:05").getTime()));
		map.put("p_calling_app_name", "NYEHMS");
		map.put("p_module_id", 16);
		map.put("p_worker_id", null);

		assertThat(dao.getPersonViewData(map)).isEmpty();                                 // the rows are in the map, as before

		Call call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("PKG_PERSON.PR_NYEHMS_PERSON_DETAILS");
		assertThat(call.parameters()).hasSize(8);
		assertParameter(call.parameters()[0], ParameterMode.IN, String.class, "V");
		assertParameter(call.parameters()[2], ParameterMode.IN, Timestamp.class, Timestamp.valueOf("2024-01-02 03:04:05"));   // a java.util.Date with its time
		assertTextCursor(call.parameters()[3], PersonResultMaps.PERSON_VIEW);
		assertTextCursor(call.parameters()[4], PersonResultMaps.PERSON_DOC_HISTORY);
		assertParameter(call.parameters()[6], ParameterMode.IN, Integer.class, 16);
		assertParameter(call.parameters()[7], ParameterMode.IN, String.class, null);      // a null bound as VARCHAR, the mapper's jdbcType
		assertThat(map).containsEntry("refCursor", persons).containsEntry("p_doc_timeline", timeline);
	}

	@Test
	void fieldValuesRaceRisksAndHistoryCallTheirProceduresWithTheFormerResultMaps() {
		List<Object> rows = List.of("row");
		Object[] mocked = mocked(results(6, Map.of(3, rows)));
		PersonDAO dao = (PersonDAO) mocked[0];
		Map<Object, Object> map = new HashMap<>();
		map.put("tblField", "FIRST_NAME");
		map.put("stateNo", "S1");
		map.put("p_calling_app_name", "NYEHMS");
		map.put("p_module_id", "16");
		map.put("p_worker_id", 42);
		assertThat(dao.getFieldValsWithBestIndicator(map)).isEmpty();
		Call call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("PKG_PERSON.PR_SHOW_MULTIVALUES");
		assertThat(call.parameters()).hasSize(6);
		assertTextCursor(call.parameters()[3], PersonResultMaps.PERSON_FIELD);
		assertParameter(call.parameters()[4], ParameterMode.IN, String.class, "16");      // a text for a number parameter, converted by the database as before
		assertParameter(call.parameters()[5], ParameterMode.IN, Integer.class, 42);
		assertThat(map).containsEntry("refCursor", rows);

		mocked = mocked(results(6, Map.of(3, rows)));
		dao = (PersonDAO) mocked[0];
		map.remove("refCursor");
		dao.getFieldDocsWithBestIndicator(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("PKG_PERSON.PR_SHOW_MULTIVALUES");
		assertTextCursor(call.parameters()[3], PersonResultMaps.PERSON_FIELD_DOCS);
		assertThat(map).containsEntry("refCursor", rows);

		mocked = mocked(results(3, Map.of(2, rows)));
		dao = (PersonDAO) mocked[0];
		map.clear();
		map.put("p_state_no", "S1");
		map.put("p_date", null);
		dao.getPersonRaceData(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("PKG_PERSON.PR_GETRACE");
		assertParameter(call.parameters()[0], ParameterMode.IN, String.class, "S1");
		assertParameter(call.parameters()[1], ParameterMode.IN, Timestamp.class, null);
		assertTextCursor(call.parameters()[2], PersonResultMaps.PERSON_RACE);
		assertThat(map).containsEntry("refCursor", rows);

		mocked = mocked(results(4, Map.of(2, rows)));
		dao = (PersonDAO) mocked[0];
		map.clear();
		map.put("p_state_no", "S1");
		map.put("p_date", Timestamp.valueOf("2024-01-02 00:00:00"));
		map.put("p_calling_app_name", "NYEHMS");
		dao.getPersonRisksData(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("PKG_PERSON.PR_GETRISKS");
		assertParameter(call.parameters()[1], ParameterMode.IN, Timestamp.class, Timestamp.valueOf("2024-01-02 00:00:00"));
		assertTextCursor(call.parameters()[2], PersonResultMaps.PERSON_RISKS);
		assertParameter(call.parameters()[3], ParameterMode.IN, String.class, "NYEHMS");
		assertThat(map).containsEntry("refCursor", rows);

		mocked = mocked(results(2, Map.of(1, List.of("S0", "S1"))));
		dao = (PersonDAO) mocked[0];
		map.clear();
		map.put("p_state_no", "S1");
		assertThat(dao.getStatenoHistory(map)).isEmpty();
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_consolidation.pr_get_stateno_history");
		assertTextCursor(call.parameters()[1], PersonResultMaps.COLUMN_VALUE);
		assertThat(map).containsEntry("p_stateno_hist", List.of("S0", "S1"));
	}

	@Test
	void deleteAndMessageTriggerPutTheirOutputsBack() {
		Object[] mocked = mocked(results(4, Map.of(2, 0, 3, "deleted")));
		PersonDAO dao = (PersonDAO) mocked[0];
		Map<Object, Object> map = new HashMap<>();
		map.put("p_document_id", "100");
		map.put("p_worker_id", 42);
		dao.deleteDocument(map);
		Call call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("PKG_DOCUMENT_MANAGEMENT.pr_delete_indicate_document");
		assertParameter(call.parameters()[0], ParameterMode.IN, String.class, "100");
		assertParameter(call.parameters()[1], ParameterMode.IN, Integer.class, 42);
		assertParameter(call.parameters()[2], ParameterMode.OUT, Integer.class, null);
		assertParameter(call.parameters()[3], ParameterMode.OUT, String.class, null);
		assertThat(map).containsEntry("p_code_out", 0).containsEntry("p_msg_out", "deleted");

		mocked = mocked(results(15, Map.of(5, 1, 6, "no message")));
		dao = (PersonDAO) mocked[0];
		map.clear();
		map.put("p_stateno", "S1");
		map.put("p_document_id", 100);
		map.put("p_document_type", "HIV");
		map.put("p_TRIGGER_MSG_TYPE", "NEW_DX");
		map.put("p_workerId", "42");
		map.put("p_change_address", "N");
		dao.triggerPsMessage(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("PKG_PS_MESSAGE_PROCESS.pr_manual_create_ps_message");
		assertThat(call.parameters()).hasSize(15);
		assertParameter(call.parameters()[1], ParameterMode.IN, Integer.class, 100);
		assertParameter(call.parameters()[5], ParameterMode.OUT, Integer.class, null);
		assertParameter(call.parameters()[6], ParameterMode.OUT, String.class, null);
		assertParameter(call.parameters()[7], ParameterMode.IN, String.class, null);      // p_test_type_code not given
		assertParameter(call.parameters()[9], ParameterMode.IN, String.class, "N");
		assertThat(map).containsEntry("p_code_out", 1).containsEntry("p_msg_out", "no message");
	}

	// ---- the native statements, without a database -------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	private static Object[] capturedQuery(NativeStatements statements) {
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(statements).list(sql.capture(), any(RowMapper.class), parameters.capture());
		return new Object[] {sql.getValue(), parameters.getValue()};
	}

	private static NativeStatements mockedQueries() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		return mocked;
	}

	@Test
	void personDocumentsFilterByTypeOnlyWhenOneIsGiven() {
		NativeStatements mocked = mockedQueries();
		Map<String, Object> input = new HashMap<>();
		input.put("p_stateNo", "S1");
		input.put("p_module_id", 16);
		input.put("p_worker_id", "42");
		input.put("p_documentType", "");
		new PersonDAO(mocked, null).getPersonDocumentDetails(input);
		Object[] query = capturedQuery(mocked);
		String sql = ((String) query[0]).replaceAll("\\s+", " ").trim();
		assertThat(sql).startsWith("select DOCUMENT_ID documentId, DOCUMENT_TYPE_CODE documentType, TO_CHAR(RECIEVE_DT, 'MM/DD/YYYY') recievedDate")
				.contains("from NYEHMS_DOCUMENT_TIMELINE_VW where STATENO = ?1 and doc_type_code IN (SELECT * FROM TABLE(get_user_permissions(?2, ?3)))")
				.doesNotContain("?4").endsWith("order by RECIEVE_DT desc nulls last");
		assertThat((Object[]) query[1]).containsExactly("S1", 16, "42");

		mocked = mockedQueries();
		input.put("p_documentType", "HIV");
		new PersonDAO(mocked, null).getPersonDocumentDetails(input);
		query = capturedQuery(mocked);
		assertThat(((String) query[0]).replaceAll("\\s+", " ")).contains("get_user_permissions(?2, ?3))) and doc_type_code = ?4 order by RECIEVE_DT desc nulls last");
		assertThat((Object[]) query[1]).containsExactly("S1", 16, "42", "HIV");

		mocked = mockedQueries();
		new PersonDAO(mocked, null).getHIVDocumentDetails(input);
		query = capturedQuery(mocked);
		assertThat((String) query[0]).isSameAs(PersonSql.HIV_DOCUMENT_DETAILS).contains("from  NYEHMS_DOC_TIMELINE_HIV_VW").contains("order by COLLECTION_DATE desc nulls last");
		assertThat((Object[]) query[1]).containsExactly("S1", 16, "42");
	}

	@Test
	void labDocumentDetailsReadOnlyTheAllowedTables() {
		NativeStatements mocked = mockedQueries();
		Map<Object, Object> map = new HashMap<>();
		map.put("tableName", "STD_STD_ECLRS_DOC");
		map.put("documentType", "STD");
		map.put("documentId", 100);
		new PersonDAO(mocked, null).getLabDocumentDetails(map);
		Object[] query = capturedQuery(mocked);
		assertThat((String) query[0]).contains("from  STD_STD_ECLRS_DOC , CODED_ETHNICITY e where  DOCUMENT_ID = ?2").doesNotContain("${");
		assertThat((Object[]) query[1]).containsExactly("STD", 100);

		map.put("tableName", "STD_HIV_ECLRS_DOC; drop table PERSON");
		assertThatThrownBy(() -> new PersonDAO(mockedQueries(), null).getLabDocumentDetails(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);
		map.put("tableName", null);
		assertThatThrownBy(() -> new PersonDAO(mockedQueries(), null).getLabDocumentDetails(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		mocked = mockedQueries();
		map.put("documentId", 7);
		new PersonDAO(mocked, null).getNBSDocumentDetails(map);
		query = capturedQuery(mocked);
		assertThat((String) query[0]).isSameAs(PersonSql.NBS_DOCUMENT_DETAILS).contains("and nbs.DOCUMENT_ID = ?1");
		assertThat((Object[]) query[1]).containsExactly(7);

		mocked = mockedQueries();
		new PersonDAO(mocked, null).getClusterDocumentDetailsFromStageTable(map);
		query = capturedQuery(mocked);
		assertThat((String) query[0]).isSameAs(PersonSql.CLUSTER_DOCUMENT_DETAILS_FROM_STAGE).contains("where  stage.partner_id = ?1");
		assertThat((Object[]) query[1]).containsExactly(7);

		mocked = mockedQueries();
		new PersonDAO(mocked, null).getDocumentDetailsFromStageTable(map);
		query = capturedQuery(mocked);
		assertThat((String) query[0]).isSameAs(PersonSql.DOCUMENT_DETAILS_FROM_STAGE).contains("?1 documentType,").contains("stage.DOCUMENT_ID = ?2");
		assertThat((Object[]) query[1]).containsExactly("STD", 7);

		assertThatThrownBy(() -> new PersonDAO(mockedQueries(), null).getDocumentDetails(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);
	}
}
