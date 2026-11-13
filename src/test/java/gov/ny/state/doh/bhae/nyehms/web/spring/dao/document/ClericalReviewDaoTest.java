package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

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
import java.util.HashSet;
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
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import jakarta.persistence.ParameterMode;

/**
 * The clerical review DAO: the JPQL statements and the quality-stage count / matched-stateno statements against an
 * in-memory H2 database in Oracle mode (db/clerical.sql), next to their data; the random picks over their candidates;
 * the procedures and the Oracle-only native statements checked without a database (procedure, parameters, outputs
 * put back, SQL text, allow-lists).
 */
@SpringJUnitConfig(ClericalReviewDaoTest.TestConfig.class)
class ClericalReviewDaoTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, ClericalReviewDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("clericaldao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/clerical.sql").build();
		}
	}

	@Autowired ClericalReviewDAO dao;
	@Autowired NativeStatements statements;

	// ---- the clerical review ------------------------------------------------------------------------------------------

	@Test
	void clericalDocumentsAndTheirPotentialStatenos() {
		Map<String, Object> map = new HashMap<>();
		map.put("documentToReview", "ALL");
		map.put("reviewType", "C");
		assertThat(dao.getClericalDocuments(map)).containsExactlyInAnyOrder(100, 200, 500);   // 400 is a child, 300 is flagged P
		map.put("documentToReview", "TB");
		assertThat(dao.getClericalDocuments(map)).containsExactlyInAnyOrder(100, 500);
		map.put("documentToReview", "");
		assertThat(dao.getClericalDocuments(map)).containsExactlyInAnyOrder(100, 200, 500);
		map.put("documentToReview", null);
		assertThat(dao.getClericalDocuments(map)).isEmpty();                              // document_type_code = NULL, as before

		map.put("reviewType", "C");
		map.put("documentId", 100);
		Map<String, Object> details = dao.getDocumentIdAndPotentialMatchesForStatenos(map);
		assertThat(details).containsEntry("documentId", BigDecimal.valueOf(100)).containsEntry("documentType", "TB").doesNotContainKey("testTypeCd");
		assertThat((List<String>) details.get("stateno")).containsExactlyInAnyOrder("S1", "S2");   // C results only; the null left out
		map.put("documentId", 500);
		assertThat(dao.getDocumentIdAndPotentialMatchesForStatenos(map)).isNull();            // its summary is reviewed
		map.put("documentId", 999);
		assertThat(dao.getDocumentIdAndPotentialMatchesForStatenos(map)).isNull();

		assertThat(dao.getStatnosForClericalDocument(BigDecimal.valueOf(100))).containsExactlyInAnyOrder("S1", "S2", "S9");
		assertThat(dao.getStatnosForClericalDocument(BigDecimal.valueOf(999))).isEmpty();
		assertThat(dao.getPotentialStatenosForDinDoccsDocument(200)).containsExactlyInAnyOrder("S3", "S4");   // documents 200 and 201 share DOCCS document 20
		assertThat(dao.getPotentialStatenosForDinDoccsDocument(202)).isEmpty();              // 202's match code is N
		assertThat(dao.getClericalReviewAvailabeDocumentTypes()).containsExactlyInAnyOrder("TB", "DOCCS");
		assertThat(dao.getClericalLabDeathReviewAvailabeDocumentTypes()).containsExactly("TB");
	}

	// ---- the quality-stage review of a standardized table ---------------------------------------------------------

	private static Map<Object, Object> qs(String statusCode, String fileName, String fileType) {
		Map<Object, Object> map = new HashMap<>();
		map.put("tableName", "STD_TB_DOC");
		map.put("fileName", "TB_FILE");
		map.put("documentType", "TB");
		map.put("filterCriteriaForStatusCode", statusCode);
		map.put("filterCriteriaForFileName", fileName);
		map.put("filterCriteriaForFileType", fileType);
		return map;
	}

	@Test
	void qualityStageCountsAndStatenosReadTheAllowedTableOnly() {
		assertThat(dao.getDocumentsCountToReviewWithSearchCriteria(qs("ALL", "", null))).isEqualTo(3);   // 103 reviewed, 104 not in status 17
		assertThat(dao.getDocumentsCountToReviewWithSearchCriteria(qs("C", "tb1.csv", "T"))).isEqualTo(1);
		assertThat(dao.getDocumentsCountToReviewWithSearchCriteria(qs("M", null, "U"))).isZero();
		assertThat(dao.getDocumentsCountToReviewWithSearchCriteriaByParent(qs("C", null, null))).isEqualTo(2);   // parents 100 and 102
		Map<Object, Object> resistance = qs("C", null, null);
		resistance.put("documentType", "RESISTANCE");
		assertThatThrownBy(() -> dao.getDocumentsCountToReviewWithSearchCriteriaByParent(resistance))
				.hasMessageContaining("REVIEW_DATE");                                       // the RESISTANCE column, which STD_TB_DOC does not have
		Map<String, Object> partners = new HashMap<>();
		partners.put("tableName", "STD_TB_DOC");
		assertThat(dao.getClusterPartnerReviewDocuments(partners)).containsExactly(3);
		partners.put("tableName", null);                                                  // the caller gives no table: fails, as before
		assertThatThrownBy(() -> dao.getClusterPartnerReviewDocuments(partners)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		Map<Object, Object> matching = new HashMap<>();
		matching.put("tableName", "std_tb_doc");
		matching.put("DOCUMENT_ID", BigDecimal.valueOf(101));
		assertThat(dao.getMatchingStatnoForDocument(matching)).containsExactly("S1");
		matching.put("tableName", "STD_TB_DOC; drop table STD_TB_DOC");
		assertThatThrownBy(() -> dao.getMatchingStatnoForDocument(matching)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		Map<String, String> codes = new HashMap<>();
		codes.put("stdTableName", "STD_TB_DOC");
		codes.put("fileNameRefColumn", "TB_FILE");
		codes.put("fileName", "ALL");
		assertThat(dao.getQualityStageStatusCodesForTheFileName(codes)).containsExactlyInAnyOrder("C", "M");
		codes.put("fileName", "tb2.csv");
		assertThat(dao.getQualityStageStatusCodesForTheFileName(codes)).containsExactly("C");
		codes.put("fileNameRefColumn", "TB_FILE = TB_FILE OR 1=1 --");
		assertThatThrownBy(() -> dao.getQualityStageStatusCodesForTheFileName(codes)).isInstanceOf(InvalidDataAccessApiUsageException.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	void randomQualityStagePicksKeepTheirSql() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		ClericalReviewDAO dao = new ClericalReviewDAO(mocked, null);
		assertThat(dao.getQualityStagedDocumentToReview(qs("C", "tb1.csv", "T"))).isNull();   // one row (ROWNUM < 2), picked at random by Oracle
		Map<Object, Object> resistance = qs(null, "ALL", "");
		resistance.put("documentType", "RESISTANCE");
		assertThat(dao.getQualityStagedDocumentToReviewByParent(resistance)).isNull();
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked, org.mockito.Mockito.times(2)).one(sql.capture(), any(RowMapper.class), parameters.capture());
		assertThat(sql.getAllValues().get(0)).isEqualTo("select DOCUMENT_ID, MATCH_CD from (select DOCUMENT_ID, MATCH_CD, MOD (DOCUMENT_ID * TO_NUMBER(to_char(SYSTIMESTAMP,'FF')), 1500) rand_order from STD_TB_DOC"
				+ " where REVIEW_DT is null and current_process_Status in (17)  AND MATCH_CD =  ?1 AND TB_FILE =  ?2 AND FILE_TYPE_CD =  ?3 ORDER BY rand_order ) where ROWNUM < 2");
		assertThat(parameters.getAllValues().get(0)).containsExactly("C", "tb1.csv", "T");
		assertThat(sql.getAllValues().get(1)).isEqualTo("select QS_PARENT_DOC_ID DOCUMENT_ID, MATCH_CD from (select QS_PARENT_DOC_ID, MATCH_CD, MOD (QS_PARENT_DOC_ID * TO_NUMBER(to_char(SYSTIMESTAMP,'FF')), 1500) rand_order from STD_TB_DOC"
				+ " where REVIEW_DATE is null and current_process_Status in (17)  ORDER BY rand_order ) where ROWNUM < 2");
		assertThat(parameters.getAllValues().get(1)).isEmpty();

		Map<String, Object> types = new HashMap<>();
		types.put("p_reviewType", "C");
		types.put("p_module_id", "16");
		types.put("p_worker_id", 42);
		assertThat(dao.getDatafluxAvailabeDocumentTypes(types)).isEmpty();
		verify(mocked).list(sql.capture(), any(RowMapper.class), parameters.capture());
		assertThat(sql.getValue().replaceAll("\\s+", " ")).contains("from nyehms_Sasdb_owner.ER_PERSON_CLUSTERS_STAGE where match_cd = ?1 and current_process_Status = 17 and doc_type not in ('ADAP')")
				.contains("doc_type IN (SELECT * FROM TABLE(get_user_permissions(?2, ?3)))");
		assertThat(parameters.getValue()).containsExactly("C", "16", 42);
	}

	// ---- the dataflux review -------------------------------------------------------------------------------------------

	@Test
	void datafluxStagedDocumentsAreCountedPickedAndDescribed() {
		Map<Object, Object> stage = new HashMap<>();
		stage.put("documentType", "DIN_DOCCS");
		stage.put("filterCriteriaForStatusCode", "ALL");
		assertThat(dao.getTotalDocCountToReviewInDataFluxStage(stage)).isEqualTo(3);
		stage.put("filterCriteriaForStatusCode", "C");
		stage.put("filterCriteriaForFileName", "doccs1.csv");
		stage.put("filterCriteriaForFileType", "F");
		assertThat(dao.getTotalDocCountToReviewInDataFluxStage(stage)).isEqualTo(2);
		stage.put("documentType", "PRF");
		assertThat(dao.getTotalDocCountToReviewInDataFluxStage(stage)).isZero();        // reviewed

		stage.put("documentType", "DIN_DOCCS");
		Set<Object> picked = new HashSet<>();
		for (int i = 0; i < 30; i++) {
			Map<String, Object> document = dao.getDatafluxStagedDocumentToReview(stage);
			assertThat(document).containsEntry("MATCH_CD", "C").containsEntry("DOC_TYPE", "DIN_DOCCS").containsEntry("FUZZY_CLUSTER_ID", BigDecimal.valueOf(1))
					.doesNotContainKey("CLUSTER_ID");
			picked.add(document.get("DOCUMENT_ID"));
		}
		assertThat(picked).containsExactlyInAnyOrder(BigDecimal.valueOf(200), BigDecimal.valueOf(201));   // a random one of the candidates
		stage.put("documentType", "PRF");
		assertThat(dao.getDatafluxStagedDocumentToReview(stage)).isNull();

		Map<String, Object> list = new HashMap<>();
		list.put("documentToReview", "ALL");
		list.put("reviewType", "C");
		assertThat(dao.getDatafluxClericalDocuments(list)).containsExactlyInAnyOrder(200, 201, 700);   // not ADAP
		list.put("documentToReview", "");
		assertThat(dao.getDatafluxClericalDocuments(list)).containsExactlyInAnyOrder(200, 201, 700, 703);
		list.put("documentToReview", "HIV_LAB");
		assertThat(dao.getDatafluxClericalDocuments(list)).containsExactly(700);

		Map<String, Object> details = new HashMap<>();
		details.put("documentId", 700);
		assertThat(dao.getDatafluxClericalReviewDocumentDetails(details)).containsEntry("FUZZY_CLUSTER_ID", BigDecimal.valueOf(3))
				.containsEntry("CLUSTER_ID", BigDecimal.valueOf(30)).containsEntry("MATCH_CD", "C").containsEntry("DOCUMENT_ID", BigDecimal.valueOf(700))
				.containsEntry("POT_STATENO", "S7").containsEntry("DOCUMENT_TYPE_CODE", "HIV_LAB").containsEntry("TEST_TYPE_CD", "VL");
		details.put("documentId", 999);
		assertThat(dao.getDatafluxClericalReviewDocumentDetails(details)).isNull();
		details.put("documentId", 800);
		assertThat(dao.getClusterPartnerReviewDocumentDetails(details)).isEqualTo(Map.of("FUZZY_CLUSTER_ID", BigDecimal.valueOf(900), "MATCH_CD", "C",
				"DOCUMENT_ID", BigDecimal.valueOf(900), "POT_STATENO", "S6", "DOCUMENT_TYPE_CODE", "CLUST_PART"));
		details.put("documentId", 700);
		assertThat(dao.getDatafluxDocumentIdAndPotentialMatchesForStatenos(details)).containsExactly("S1");   // cluster 3: 100 is current, 500 disassociated, 700 unassociated
		assertThat(dao.getDatafluxFileNamesToReview("DIN_DOCCS")).containsExactlyInAnyOrder("doccs1.csv", "doccs2.csv");
		Map<String, String> codes = new HashMap<>();
		codes.put("documentType", "DIN_DOCCS");
		codes.put("fileName", "");
		assertThat(dao.getDFStatusCodesForTheFileName(codes)).containsExactlyInAnyOrder("C", "N");
		codes.put("fileName", "doccs2.csv");
		assertThat(dao.getDFStatusCodesForTheFileName(codes)).containsExactly("N");
	}

	@Test
	@Transactional                                                                       // rolled back: the other tests count on the script's rows
	void datafluxReviewUpdatesTheStagingAndClusters() {
		Map<String, Object> valid = new HashMap<>();
		valid.put("p_fuzzy_cluster_id", 1L);
		assertThat(dao.isTheDocumentValidToProcess(valid)).isEqualTo("Y");                 // document 200 has a valid parent
		valid.put("p_fuzzy_cluster_id", 2L);
		assertThat(dao.isTheDocumentValidToProcess(valid)).isNull();
		valid.remove("p_fuzzy_cluster_id");
		valid.put("p_cluster_id", 30L);
		assertThat(dao.isTheDocumentValidToProcess(valid)).isEqualTo("Y");                 // HIV_LAB needs no parent
		valid.remove("p_cluster_id");
		assertThatThrownBy(() -> dao.isTheDocumentValidToProcess(valid)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		Map<String, Object> match = new HashMap<>();
		match.put("p_fuzzy_cluster_id", 1L);
		match.put("p_document_id", 200L);
		match.put("ACTUAL_MATCH_CD", "C");
		match.put("CURRENT_PROCESS_STATUS", 17);
		match.put("UPDATED_MATCH_CD", "C_M");
		match.put("stateno", "S3");
		match.put("p_worker_id", 42);
		dao.updateMatchDetailsInStageTable(match);
		Map<String, Object> row = statements.one("select MATCH_CD, MATCHED_STATENO, REVIEW_UID, REVIEW_DATE, CURRENT_PROCESS_STATUS from NYEHMS_SASDB_OWNER.ER_PERSON_CLUSTERS_STAGE where DOCUMENT_ID = 200", Rows.map());
		assertThat(row).containsEntry("MATCH_CD", "C_M").containsEntry("MATCHED_STATENO", "S3");
		assertThat(row.get("REVIEW_UID")).hasToString("42");
		assertThat((Timestamp) row.get("REVIEW_DATE")).isCloseTo(new Timestamp(System.currentTimeMillis()), 60_000);
		assertThat(statements.one("select MATCH_CD from NYEHMS_SASDB_OWNER.ER_PERSON_CLUSTERS_STAGE where DOCUMENT_ID = 201", Rows.scalar(String.class))).isEqualTo("C");   // only the given document
		match.remove("p_document_id");
		match.remove("p_fuzzy_cluster_id");
		match.put("p_cluster_id", 30L);
		match.put("CURRENT_PROCESS_STATUS", 14);
		match.put("UPDATED_MATCH_CD", "C_N");
		match.put("stateno", null);
		dao.updateMatchDetailsInStageTable(match);
		row = statements.one("select MATCH_CD, MATCHED_STATENO, CURRENT_PROCESS_STATUS from NYEHMS_SASDB_OWNER.ER_PERSON_CLUSTERS_STAGE where DOCUMENT_ID = 700", Rows.map());
		assertThat(row).containsEntry("MATCH_CD", "C_N").doesNotContainKey("MATCHED_STATENO");
		assertThat(row.get("CURRENT_PROCESS_STATUS")).hasToString("14");

		Map<String, Object> notAMatch = new HashMap<>();
		notAMatch.put("fuzzyClusterId", 4L);
		notAMatch.put("worker_id", 43);
		dao.updateNotaMatchDetailsInStageTable(notAMatch);                                // 701 is P: untouched
		assertThat(statements.one("select MATCH_CD from NYEHMS_SASDB_OWNER.ER_PERSON_CLUSTERS_STAGE where DOCUMENT_ID = 701", Rows.scalar(String.class))).isEqualTo("P");
		dao.updateNotaMatchDetailsInClustersTable(notAMatch);
		row = statements.one("select MATCH_CD, CURRENT_PROCESS_STATUS, REVIEW_UID from NYEHMS_SASDB_OWNER.ER_PERSON_CLUSTERS where DOCUMENT_ID = 701", Rows.map());
		assertThat(row).containsEntry("MATCH_CD", "C_N");
		assertThat(row.get("CURRENT_PROCESS_STATUS")).hasToString("47");
		assertThat(row.get("REVIEW_UID")).hasToString("43");
	}

	// ---- the procedures, without a database ------------------------------------------------------------------------

	private record Call(String procedure, CallParam[] parameters) {
	}

	private static Object[] mocked(List<Object> results) {
		NativeStatements statements = mock(NativeStatements.class);
		when(statements.call(any(), any(CallParam[].class))).thenReturn(results);
		return new Object[] {new ClericalReviewDAO(statements, null), statements};
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
	void reviewProceduresBindTheirValuesAndPutTheOutputsBack() {
		Object[] mocked = mocked(results(5, Map.of(3, 0, 4, "matched")));
		ClericalReviewDAO dao = (ClericalReviewDAO) mocked[0];
		Map<Object, Object> map = new HashMap<>();
		map.put("p_raw_document_id", 100L);
		map.put("p_mtch_stateno", "S1");
		map.put("p_worker_id", 42);
		dao.matchDocumentToStateno(map);
		Call call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_clerical_review.pr_match_document_to_stateno");
		assertParameter(call.parameters()[0], ParameterMode.IN, Long.class, 100L);        // bound with its own type, as MyBatis bound a long
		assertParameter(call.parameters()[2], ParameterMode.IN, Integer.class, 42);
		assertParameter(call.parameters()[3], ParameterMode.OUT, Integer.class, null);
		assertParameter(call.parameters()[4], ParameterMode.OUT, String.class, null);
		assertThat(map).containsEntry("p_code_out", 0).containsEntry("p_msg_out", "matched");

		mocked = mocked(results(6, Map.of(3, "S777", 4, 0, 5, "ok")));
		dao = (ClericalReviewDAO) mocked[0];
		map.clear();
		map.put("p_raw_document_id", 100L);
		map.put("p_worker_id", 42);
		dao.noMatchForADocument(map);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_clerical_review.pr_no_match_for_document");
		assertParameter(call.parameters()[2], ParameterMode.IN, String.class, null);       // P_document_type not given: a VARCHAR null
		assertThat(map).containsEntry("p_stateno_out", "S777").containsEntry("p_code_out", 0).containsEntry("p_msg_out", "ok");

		String[] procedures = {"pkg_clerical_review.pr_EPRF_QS_review", "pkg_clerical_review.PR_NYCIJR_QS_REVIEW", "pkg_clerical_review.pr_iccr_qs_review",
				"pkg_clerical_review.PR_ADAP_QS_REVIEW", "pkg_clerical_review.pr_resistance_qs_review"};
		for (int i = 0; i < procedures.length; i++) {
			mocked = mocked(results(6, Map.of(4, i, 5, "done " + i)));
			dao = (ClericalReviewDAO) mocked[0];
			map.clear();
			map.put("p_raw_document_id", 100L);
			map.put("p_mtch_stateno", "S1");
			map.put("p_mtch_cd", "C_M");
			map.put("p_worker_id", 42);
			switch (i) {
				case 0 -> dao.qualityStagedDocumentManualReviewForEPRFOOC(map);
				case 1 -> dao.qualityStagedDocumentManualReviewForNycIJR(map);
				case 2 -> dao.qualityStagedDocumentManualReviewForICCR(map);
				case 3 -> dao.qualityStagedDocumentManualReviewForADAP(map);
				default -> dao.qualityStagedDocumentManualReviewForResistance(map);
			}
			call = captured((NativeStatements) mocked[1]);
			assertThat(call.procedure()).isEqualTo(procedures[i]);
			assertThat(call.parameters()).hasSize(6);
			assertParameter(call.parameters()[2], ParameterMode.IN, String.class, "C_M");
			assertParameter(call.parameters()[3], ParameterMode.IN, Integer.class, 42);
			assertThat(map).containsEntry("p_code_out", i).containsEntry("p_msg_out", "done " + i);
		}

		mocked = mocked(results(6, Map.of(3, 99, 4, "error", 5, "user message")));
		dao = (ClericalReviewDAO) mocked[0];
		Map<String, Object> dataflux = new HashMap<>();
		dataflux.put("p_fuzzy_cluster_id", 3L);
		dataflux.put("p_worker_id", 42);
		dao.processDatafluxStageDocument(dataflux);
		call = captured((NativeStatements) mocked[1]);
		assertThat(call.procedure()).isEqualTo("pkg_process_dataflux_results.pr_process_match_results");
		assertParameter(call.parameters()[0], ParameterMode.IN, Integer.class, null);       // p_cluster_id not given: an INTEGER null
		assertParameter(call.parameters()[1], ParameterMode.IN, Long.class, 3L);
		assertThat(dataflux).containsEntry("p_code_out", 99).containsEntry("p_err_msg", "error").containsEntry("p_user_msg", "user message");
	}
}
