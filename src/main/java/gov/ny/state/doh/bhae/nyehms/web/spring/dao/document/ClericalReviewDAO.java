package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import lombok.RequiredArgsConstructor;

/**
 * The clerical review of matched documents: the quality-stage (QS) review of the standardized tables, the dataflux
 * review of the SAS owner's cluster staging, the cluster partner review, and the procedures that record a review
 * (formerly a MyBatis mapper with the same name and methods; the callers are unchanged).
 * <p>
 * JPQL over the entities RawDocumentMatchSummary, RawDocumentMatchResults, DocumentLocator, StdDinDoccsDoc,
 * ErPersonClustersStage, ErPersonClusters, PersonDocAssociation, NyehmsClusterPartners and
 * NyehmsDocumentClericRvwVw. The procedures of {@code pkg_clerical_review} and {@code pkg_process_dataflux_results}
 * run through {@link NativeStatements#call}, their OUT values put back into the caller's map under the former keys.
 * The former "one random row" selections ({@code ORDER BY MOD(id * SYSTIMESTAMP fraction) ... ROWNUM < 2}) of the
 * dataflux staging are a random pick in Java over the same candidates; the random order of a document's potential
 * state numbers is a shuffle. "Now" (formerly SYSDATE) is the application server's clock. Native SQL (the mapper's
 * text with bound values), with the reason:
 * <ul>
 * <li>the QS review statements ({@link #getClusterPartnerReviewDocuments},
 * {@link #getDocumentsCountToReviewWithSearchCriteria}, {@link #getDocumentsCountToReviewWithSearchCriteriaByParent},
 * {@link #getQualityStagedDocumentToReview}, {@link #getQualityStagedDocumentToReviewByParent},
 * {@link #getMatchingStatnoForDocument}, {@link #getQualityStageStatusCodesForTheFileName}): the table and the
 * file-name column are chosen per call (allow-listed in {@link StdTableNames}; anything else is refused), and the
 * random pick uses {@code SYSTIMESTAMP} and {@code ROWNUM};</li>
 * <li>{@link #getDatafluxAvailabeDocumentTypes}: the pipelined table function {@code TABLE(get_user_permissions(...))}.</li>
 * </ul>
 */
@Repository
@RequiredArgsConstructor
public class ClericalReviewDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	private static final Random RANDOM = new Random();

	// ---- the clerical review (quality-stage match summary) --------------------------------------------------------

	/**
	 * The parent documents flagged with a match code (of a document type, unless 'ALL' or empty).
	 *
	 * @param inOutParameterMap documentToReview, reviewType
	 */
	public List<Integer> getClericalDocuments(Map<String, Object> inOutParameterMap) throws DataAccessException {
		Object documentType = inOutParameterMap.get("documentToReview");
		List<Object> parameters = new ArrayList<>();
		String jpqlText = "select distinct s.documentId from RawDocumentMatchSummary s where ";
		if (!"ALL".equals(documentType) && !"".equals(documentType)) {
			jpqlText += "s.documentTypeCode = ?1 and ";
			parameters.add(BoundValues.text(documentType));
		}
		parameters.add(BoundValues.text(inOutParameterMap.get("reviewType")));
		jpqlText += "s.flagMatchCd = ?" + parameters.size() + " and s.documentId = s.qsParentDocId";
		return jpql.list(jpqlText, Rows.scalar(Integer.class), parameters.toArray());
	}

	/** The potential state numbers (match code C) of the DIN/DOCCS documents sharing a DOCCS document with a document. */
	public List<String> getPotentialStatenosForDinDoccsDocument(Integer document_id) {
		return jpql.list("""
				select distinct s.potStateno from ErPersonClustersStage s where s.documentId in
				(select d.documentId from StdDinDoccsDoc d where d.doccsDocId in (select d2.doccsDocId from StdDinDoccsDoc d2 where d2.documentId = ?1))
				and s.matchCd = 'C'""", Rows.scalar(String.class), BoundValues.longValue(document_id));
	}

	/**
	 * A document's match results awaiting review, as the former nested result map gave them: documentId (a BigDecimal),
	 * documentType, testTypeCd (absent when null) and stateno (the potential state numbers, in random order; nulls left
	 * out); null when the document has no results (the caller fails on it, as before).
	 *
	 * @param inputParameterMap reviewType, documentId
	 */
	public Map<String, Object> getDocumentIdAndPotentialMatchesForStatenos(Map<String, Object> inputParameterMap) throws DataAccessException {
		String reviewType = BoundValues.text(inputParameterMap.get("reviewType"));
		List<Row> rows = jpql.list("""
				select r.documentId, d.documentTypeCode, d.testTypeCd, r.potStateno
				from RawDocumentMatchResults r join DocumentLocator d on d.documentId = r.documentId
				where r.matchCd = ?1 and d.documentId = ?2 and exists (select 1 from RawDocumentMatchSummary s
				    where s.documentId = s.qsParentDocId and s.matchSummId = r.matchSummId and s.flagMatchCd = ?1 and s.reviewedDate is null)""",
				row -> row, reviewType, BoundValues.integer(inputParameterMap.get("documentId")));
		if (rows.isEmpty()) {
			return null;
		}
		Map<String, Object> result = new HashMap<>();
		Row first = rows.get(0);
		result.put("documentId", BigDecimal.valueOf(Values.longValue(first.get(0))));
		putIfPresent(result, "documentType", first.get(1));
		putIfPresent(result, "testTypeCd", first.get(2));
		List<String> statenos = new ArrayList<>();
		for (Row row : rows) {
			if (row.get(3) != null) {
				statenos.add(Values.text(row.get(3)));
			}
		}
		Collections.shuffle(statenos, RANDOM);
		result.put("stateno", statenos);
		return result;
	}

	/** The distinct potential state numbers of a document's match results. */
	public List<String> getStatnosForClericalDocument(BigDecimal document_id) {
		return jpql.list("select distinct r.potStateno from RawDocumentMatchResults r where r.documentId = ?1 and r.potStateno is not null",
				Rows.scalar(String.class), BoundValues.longValue(document_id));
	}

	/** The document types awaiting clerical review (NYEHMS_DOCUMENT_CLERIC_RVW_VW). */
	public List<String> getClericalReviewAvailabeDocumentTypes() {
		return jpql.list("select distinct v.documentTypeCode from NyehmsDocumentClericRvwVw v", Rows.scalar(String.class));
	}

	/** The document types of the parent documents flagged P (lab / death review). */
	public List<String> getClericalLabDeathReviewAvailabeDocumentTypes() {
		return jpql.list("select distinct s.documentTypeCode from RawDocumentMatchSummary s where s.flagMatchCd = 'P' and s.documentId = s.qsParentDocId",
				Rows.scalar(String.class));
	}

	// ---- the quality-stage review of a standardized table (native, per-call table) -------------------------------

	/** The former {@code <if test="x != null and x != '' and x != 'ALL'">}. */
	private static boolean given(Object value) {
		return value != null && !"".equals(value) && !"ALL".equals(value);
	}

	/** The optional MATCH_CD, file-name column and FILE_TYPE_CD filters of the QS review statements, appended with their values. */
	private static String qsFilters(Map<?, ?> map, List<Object> parameters, boolean withFileName) {
		StringBuilder sql = new StringBuilder();
		if (given(map.get("filterCriteriaForStatusCode"))) {
			parameters.add(map.get("filterCriteriaForStatusCode"));
			sql.append(" AND MATCH_CD =  ?").append(parameters.size());
		}
		if (withFileName && given(map.get("filterCriteriaForFileName"))) {
			parameters.add(map.get("filterCriteriaForFileName"));
			sql.append(" AND ").append(StdTableNames.fileNameColumn(map.get("fileName"))).append(" =  ?").append(parameters.size());
		}
		if (withFileName && given(map.get("filterCriteriaForFileType"))) {
			parameters.add(map.get("filterCriteriaForFileType"));
			sql.append(" AND FILE_TYPE_CD =  ?").append(parameters.size());
		}
		return sql.toString();
	}

	/** The review-date column of a table: REVIEW_DATE for RESISTANCE documents, REVIEW_DT otherwise (the former {@code <choose>}). */
	private static String reviewDateColumn(Object documentType) {
		return "RESISTANCE".equals(documentType) ? "REVIEW_DATE" : "REVIEW_DT";
	}

	/**
	 * The count of a table's documents awaiting review (as a one-element list, as the former int result list).
	 *
	 * @param inOutParameterMap tableName (allow-listed), filterCriteriaForStatusCode
	 */
	public List<Integer> getClusterPartnerReviewDocuments(Map<String, Object> inOutParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String sql = "select count(DOCUMENT_ID) from " + StdTableNames.table(inOutParameterMap.get("tableName"))
				+ " where REVIEW_DT is null and current_process_Status in (17) " + qsFilters(inOutParameterMap, parameters, false);
		return statements.list(sql, Rows.scalar(Integer.class), parameters.toArray());
	}

	/** The count of a table's documents awaiting review with the filters. @param inOutParamaterMap tableName, fileName (allow-listed), filterCriteria* */
	public int getDocumentsCountToReviewWithSearchCriteria(Map<Object, Object> inOutParamaterMap) {
		List<Object> parameters = new ArrayList<>();
		String sql = "select count(DOCUMENT_ID) from " + StdTableNames.table(inOutParamaterMap.get("tableName"))
				+ " where REVIEW_DT is null and current_process_Status in (17) " + qsFilters(inOutParamaterMap, parameters, true);
		return statements.one(sql, Rows.scalar(Integer.class), parameters.toArray());
	}

	/** The count of a table's parent documents awaiting review with the filters. @param inOutParamaterMap tableName, fileName, documentType, filterCriteria* */
	public int getDocumentsCountToReviewWithSearchCriteriaByParent(Map<Object, Object> inOutParamaterMap) {
		List<Object> parameters = new ArrayList<>();
		String sql = "select count(distinct(QS_PARENT_DOC_ID)) from " + StdTableNames.table(inOutParamaterMap.get("tableName")) + " where "
				+ reviewDateColumn(inOutParamaterMap.get("documentType")) + " is null and current_process_Status in (17) "
				+ qsFilters(inOutParamaterMap, parameters, true);
		return statements.one(sql, Rows.scalar(Integer.class), parameters.toArray());
	}

	/** One random document of a table awaiting review: DOCUMENT_ID, MATCH_CD; null if none. @param inOutParamaterMap tableName, fileName, filterCriteria* */
	public Map<String, Object> getQualityStagedDocumentToReview(Map<Object, Object> inOutParamaterMap) {
		List<Object> parameters = new ArrayList<>();
		String sql = "select DOCUMENT_ID, MATCH_CD from (select DOCUMENT_ID, MATCH_CD, MOD (DOCUMENT_ID * TO_NUMBER(to_char(SYSTIMESTAMP,'FF')), 1500) rand_order from "
				+ StdTableNames.table(inOutParamaterMap.get("tableName")) + " where REVIEW_DT is null and current_process_Status in (17) "
				+ qsFilters(inOutParamaterMap, parameters, true) + " ORDER BY rand_order ) where ROWNUM < 2";
		return statements.one(sql, Rows.map(), parameters.toArray());
	}

	/** One random parent document of a table awaiting review: DOCUMENT_ID (the parent), MATCH_CD; null if none. @param inOutParamaterMap tableName, fileName, documentType, filterCriteria* */
	public Map<String, Object> getQualityStagedDocumentToReviewByParent(Map<Object, Object> inOutParamaterMap) {
		List<Object> parameters = new ArrayList<>();
		String sql = "select QS_PARENT_DOC_ID DOCUMENT_ID, MATCH_CD from (select QS_PARENT_DOC_ID, MATCH_CD, MOD (QS_PARENT_DOC_ID * TO_NUMBER(to_char(SYSTIMESTAMP,'FF')), 1500) rand_order from "
				+ StdTableNames.table(inOutParamaterMap.get("tableName")) + " where " + reviewDateColumn(inOutParamaterMap.get("documentType"))
				+ " is null and current_process_Status in (17) " + qsFilters(inOutParamaterMap, parameters, true) + " ORDER BY rand_order ) where ROWNUM < 2";
		return statements.one(sql, Rows.map(), parameters.toArray());
	}

	/** The matched state number(s) of a document in a table. @param map tableName (allow-listed), DOCUMENT_ID */
	public List<String> getMatchingStatnoForDocument(Map<Object, Object> map) {
		return statements.list("select MATCHED_STATENO from " + StdTableNames.table(map.get("tableName")) + " where document_id = ?1",
				Rows.scalar(String.class), map.get("DOCUMENT_ID"));
	}

	/** The match codes awaiting review in a table (of a file, unless 'ALL' or empty). @param inputParameterMap stdTableName, fileNameRefColumn (allow-listed), fileName */
	public List<String> getQualityStageStatusCodesForTheFileName(Map<String, String> inputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String sql = "select distinct(MATCH_CD) from " + StdTableNames.table(inputParameterMap.get("stdTableName"))
				+ " where REVIEW_DT is null and current_process_Status = 17";
		if (given(inputParameterMap.get("fileName"))) {
			parameters.add(inputParameterMap.get("fileName"));
			sql += " AND " + StdTableNames.fileNameColumn(inputParameterMap.get("fileNameRefColumn")) + " =  ?1";
		}
		return statements.list(sql, Rows.scalar(String.class), parameters.toArray());
	}

	// ---- the review procedures ----------------------------------------------------------------------------------------

	/** pkg_clerical_review.pr_match_document_to_stateno. @param map p_raw_document_id, p_mtch_stateno, p_worker_id; afterwards also p_code_out, p_msg_out */
	public void matchDocumentToStateno(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_clerical_review.pr_match_document_to_stateno",
				param(map.get("p_raw_document_id"), Integer.class), param(map.get("p_mtch_stateno"), String.class),
				param(map.get("p_worker_id"), String.class), out(Integer.class), out(String.class));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}

	/** pkg_clerical_review.pr_no_match_for_document. @param map p_raw_document_id, p_worker_id, P_document_type; afterwards also p_stateno_out, p_code_out, p_msg_out */
	public void noMatchForADocument(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_clerical_review.pr_no_match_for_document",
				param(map.get("p_raw_document_id"), Integer.class), param(map.get("p_worker_id"), Integer.class),
				param(map.get("P_document_type"), String.class), out(String.class), out(Integer.class), out(String.class));
		map.put("p_stateno_out", results.get(3));
		map.put("p_code_out", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/** pkg_clerical_review.pr_EPRF_QS_review. @param inOutParamaterMap p_raw_document_id, p_mtch_stateno, p_mtch_cd, p_worker_id; afterwards also p_code_out, p_msg_out */
	public void qualityStagedDocumentManualReviewForEPRFOOC(Map<Object, Object> inOutParamaterMap) {
		qsReview("pkg_clerical_review.pr_EPRF_QS_review", inOutParamaterMap);
	}

	/** pkg_clerical_review.PR_NYCIJR_QS_REVIEW (parameters as {@link #qualityStagedDocumentManualReviewForEPRFOOC}). */
	public void qualityStagedDocumentManualReviewForNycIJR(Map<Object, Object> inOutParamaterMap) {
		qsReview("pkg_clerical_review.PR_NYCIJR_QS_REVIEW", inOutParamaterMap);
	}

	/** pkg_clerical_review.pr_iccr_qs_review (parameters as {@link #qualityStagedDocumentManualReviewForEPRFOOC}). */
	public void qualityStagedDocumentManualReviewForICCR(Map<Object, Object> inOutParamaterMap) {
		qsReview("pkg_clerical_review.pr_iccr_qs_review", inOutParamaterMap);
	}

	/** pkg_clerical_review.PR_ADAP_QS_REVIEW (parameters as {@link #qualityStagedDocumentManualReviewForEPRFOOC}). */
	public void qualityStagedDocumentManualReviewForADAP(Map<Object, Object> inOutParamaterMap) {
		qsReview("pkg_clerical_review.PR_ADAP_QS_REVIEW", inOutParamaterMap);
	}

	/** pkg_clerical_review.pr_resistance_qs_review (parameters as {@link #qualityStagedDocumentManualReviewForEPRFOOC}). */
	public void qualityStagedDocumentManualReviewForResistance(Map<Object, Object> inOutParamaterMap) {
		qsReview("pkg_clerical_review.pr_resistance_qs_review", inOutParamaterMap);
	}

	private void qsReview(String procedure, Map<Object, Object> map) {
		List<Object> results = statements.call(procedure,
				param(map.get("p_raw_document_id"), Integer.class), param(map.get("p_mtch_stateno"), String.class),
				param(map.get("p_mtch_cd"), String.class), param(map.get("p_worker_id"), Integer.class), out(Integer.class), out(String.class));
		map.put("p_code_out", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/** pkg_process_dataflux_results.pr_process_match_results. @param inOutParameterMap p_cluster_id, p_fuzzy_cluster_id, p_worker_id; afterwards also p_code_out, p_err_msg, p_user_msg */
	public void processDatafluxStageDocument(Map<String, Object> inOutParameterMap) {
		List<Object> results = statements.call("pkg_process_dataflux_results.pr_process_match_results",
				param(inOutParameterMap.get("p_cluster_id"), Integer.class), param(inOutParameterMap.get("p_fuzzy_cluster_id"), Integer.class),
				param(inOutParameterMap.get("p_worker_id"), Integer.class), out(Integer.class), out(String.class), out(String.class));
		inOutParameterMap.put("p_code_out", results.get(3));
		inOutParameterMap.put("p_err_msg", results.get(4));
		inOutParameterMap.put("p_user_msg", results.get(5));
	}

	// ---- the dataflux review (the SAS owner's cluster staging) -------------------------------------------------------

	/** The former {@code <if>} filters on the staging table's MATCH_CD, FILE_NAME and FILE_TYPE_CD, appended with their values. */
	private static String stageFilters(Map<?, ?> map, List<Object> parameters) {
		StringBuilder jpqlText = new StringBuilder();
		if (given(map.get("filterCriteriaForStatusCode"))) {
			parameters.add(BoundValues.text(map.get("filterCriteriaForStatusCode")));
			jpqlText.append(" and s.matchCd = ?").append(parameters.size());
		}
		if (given(map.get("filterCriteriaForFileName"))) {
			parameters.add(BoundValues.text(map.get("filterCriteriaForFileName")));
			jpqlText.append(" and s.fileName = ?").append(parameters.size());
		}
		if (given(map.get("filterCriteriaForFileType"))) {
			parameters.add(BoundValues.text(map.get("filterCriteriaForFileType")));
			jpqlText.append(" and s.fileTypeCd = ?").append(parameters.size());
		}
		return jpqlText.toString();
	}

	/** The count of staged documents of a type awaiting review (status 17) with the filters. @param inOutParamaterMap documentType, filterCriteria* */
	public int getTotalDocCountToReviewInDataFluxStage(Map<Object, Object> inOutParamaterMap) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(BoundValues.text(inOutParamaterMap.get("documentType")));
		return jpql.one("select count(s.documentId) from ErPersonClustersStage s where s.reviewDate is null and s.currentProcessStatus in (17) and s.docType = ?1"
				+ stageFilters(inOutParamaterMap, parameters), Rows.scalar(Integer.class), parameters.toArray());
	}

	/**
	 * One random staged document of a type awaiting review: DOCUMENT_ID, MATCH_CD, FUZZY_CLUSTER_ID, CLUSTER_ID,
	 * POT_STATENO, DOC_TYPE (the ids as BigDecimals, nulls absent, as before); null if none.
	 *
	 * @param inOutParamaterMap documentType, filterCriteria*
	 */
	public Map<String, Object> getDatafluxStagedDocumentToReview(Map<Object, Object> inOutParamaterMap) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(BoundValues.text(inOutParamaterMap.get("documentType")));
		List<Row> candidates = jpql.list("select s.documentId, s.matchCd, s.fuzzyClusterId, s.clusterId, s.potStateno, s.docType from ErPersonClustersStage s"
				+ " where s.reviewDate is null and s.currentProcessStatus in (17) and s.docType = ?1" + stageFilters(inOutParamaterMap, parameters),
				row -> row, parameters.toArray());
		if (candidates.isEmpty()) {
			return null;
		}
		Row row = candidates.get(RANDOM.nextInt(candidates.size()));
		Map<String, Object> result = new HashMap<>();
		putNumber(result, "DOCUMENT_ID", row.get(0));
		putIfPresent(result, "MATCH_CD", row.get(1));
		putNumber(result, "FUZZY_CLUSTER_ID", row.get(2));
		putNumber(result, "CLUSTER_ID", row.get(3));
		putIfPresent(result, "POT_STATENO", row.get(4));
		putIfPresent(result, "DOC_TYPE", row.get(5));
		return result.isEmpty() ? null : result;
	}

	/** The staged document types with a match code the worker may review (not ADAP). @param inputParameterMap p_reviewType, p_module_id, p_worker_id */
	public List<String> getDatafluxAvailabeDocumentTypes(Map<String, Object> inputParameterMap) {
		return statements.list("""
				select distinct doc_type from nyehms_Sasdb_owner.ER_PERSON_CLUSTERS_STAGE where match_cd = ?1 and current_process_Status = 17 and doc_type not in ('ADAP')
				and doc_type IN (SELECT * FROM TABLE(get_user_permissions(?2, ?3)))""", Rows.scalar(String.class),
				inputParameterMap.get("p_reviewType"), inputParameterMap.get("p_module_id"), inputParameterMap.get("p_worker_id"));
	}

	/** The staged documents with a match code (of a type; of all types but ADAP for 'ALL'; of any type when empty). @param inOutParameterMap documentToReview, reviewType */
	public List<Integer> getDatafluxClericalDocuments(Map<String, Object> inOutParameterMap) {
		Object documentType = inOutParameterMap.get("documentToReview");
		List<Object> parameters = new ArrayList<>();
		String jpqlText = "select distinct s.documentId from ErPersonClustersStage s where ";
		if ("ALL".equals(documentType)) {
			jpqlText += "s.docType not in ('ADAP') and ";
		} else if (!"".equals(documentType)) {
			jpqlText += "s.docType = ?1 and ";
			parameters.add(BoundValues.text(documentType));
		}
		parameters.add(BoundValues.text(inOutParameterMap.get("reviewType")));
		return jpql.list(jpqlText + "s.matchCd = ?" + parameters.size(), Rows.scalar(Integer.class), parameters.toArray());
	}

	/**
	 * A staged document with its document type: FUZZY_CLUSTER_ID, CLUSTER_ID, MATCH_CD, DOCUMENT_ID, POT_STATENO,
	 * DOCUMENT_TYPE_CODE, TEST_TYPE_CD (the ids as BigDecimals, nulls absent); null if none (the caller fails on it, as before).
	 *
	 * @param inOutParameterMap documentId
	 */
	public Map<String, Object> getDatafluxClericalReviewDocumentDetails(Map<String, Object> inOutParameterMap) {
		Row row = jpql.one("""
				select distinct a.fuzzyClusterId, a.clusterId, a.matchCd, a.documentId, a.potStateno, d.documentTypeCode, d.testTypeCd
				from ErPersonClustersStage a, DocumentLocator d where a.documentId = ?1 and a.documentId = d.documentId""",
				r -> r, BoundValues.longValue(inOutParameterMap.get("documentId")));
		if (row == null) {
			return null;
		}
		Map<String, Object> result = new HashMap<>();
		putNumber(result, "FUZZY_CLUSTER_ID", row.get(0));
		putNumber(result, "CLUSTER_ID", row.get(1));
		putIfPresent(result, "MATCH_CD", row.get(2));
		putNumber(result, "DOCUMENT_ID", row.get(3));
		putIfPresent(result, "POT_STATENO", row.get(4));
		putIfPresent(result, "DOCUMENT_TYPE_CODE", row.get(5));
		putIfPresent(result, "TEST_TYPE_CD", row.get(6));
		return result.isEmpty() ? null : result;
	}

	/**
	 * A cluster partner for review, shaped like a staged document: FUZZY_CLUSTER_ID and DOCUMENT_ID (the partner id),
	 * MATCH_CD (the flux match code), POT_STATENO, DOCUMENT_TYPE_CODE 'CLUST_PART' (CLUSTER_ID and TEST_TYPE_CD were
	 * selected as '', which Oracle gave as null: absent); null if none.
	 *
	 * @param inOutParameterMap documentId
	 */
	public Map<String, Object> getClusterPartnerReviewDocumentDetails(Map<String, Object> inOutParameterMap) {
		List<Row> rows = jpql.list("select distinct p.partnerId, p.fluxMatchCd, p.fluxPotStateno from NyehmsClusterPartners p where p.documentId = ?1",
				r -> r, BoundValues.longValue(inOutParameterMap.get("documentId")));
		if (rows.size() > 1) {
			throw new org.springframework.dao.IncorrectResultSizeDataAccessException(1, rows.size());
		}
		if (rows.isEmpty()) {
			return null;
		}
		Row row = rows.get(0);
		Map<String, Object> result = new HashMap<>();
		putNumber(result, "FUZZY_CLUSTER_ID", row.get(0));
		putIfPresent(result, "MATCH_CD", row.get(1));
		putNumber(result, "DOCUMENT_ID", row.get(0));
		putIfPresent(result, "POT_STATENO", row.get(2));
		result.put("DOCUMENT_TYPE_CODE", "CLUST_PART");
		return result;
	}

	/** The state numbers of the current documents in the fuzzy clusters of a staged document. @param inOutParameterMap documentId */
	public List<String> getDatafluxDocumentIdAndPotentialMatchesForStatenos(Map<String, Object> inOutParameterMap) {
		return jpql.list("""
				select pdoc.stateno from ErPersonClusters clust, PersonDocAssociation pdoc, DocumentLocator doc_loc
				where doc_loc.documentId = pdoc.documentId and pdoc.documentId = clust.documentId and pdoc.dissassociationDate is null
				and clust.fuzzyClusterId in (select s.fuzzyClusterId from ErPersonClustersStage s where s.documentId = ?1)""",
				Rows.scalar(String.class), BoundValues.longValue(inOutParameterMap.get("documentId")));
	}

	/** The files of a staged document type awaiting review. */
	public List<String> getDatafluxFileNamesToReview(String docType) {
		return jpql.list("select distinct s.fileName from ErPersonClustersStage s where s.docType = ?1 and s.currentProcessStatus = 17",
				Rows.scalar(String.class), docType);
	}

	/** The match codes of the staged documents of a type awaiting review (of a file, unless 'ALL' or empty). @param inputParameterMap documentType, fileName */
	public List<String> getDFStatusCodesForTheFileName(Map<String, String> inputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(inputParameterMap.get("documentType"));
		String jpqlText = "select distinct s.matchCd from ErPersonClustersStage s where s.reviewDate is null and s.currentProcessStatus = 17 and s.docType = ?1";
		if (given(inputParameterMap.get("fileName"))) {
			parameters.add(inputParameterMap.get("fileName"));
			jpqlText += " and s.fileName = ?2";
		}
		return jpql.list(jpqlText, Rows.scalar(String.class), parameters.toArray());
	}

	/** The former {@code <choose>}: by fuzzy cluster if p_fuzzy_cluster_id is given, else by cluster if p_cluster_id is; neither failed (no condition). */
	private static String clusterCondition(Map<String, Object> map, List<Object> parameters) {
		if (map.get("p_fuzzy_cluster_id") != null) {
			parameters.add(BoundValues.longValue(map.get("p_fuzzy_cluster_id")));
			return "s.fuzzyClusterId = ?" + parameters.size();
		}
		if (map.get("p_cluster_id") != null) {
			parameters.add(BoundValues.longValue(map.get("p_cluster_id")));
			return "s.clusterId = ?" + parameters.size();
		}
		throw new InvalidDataAccessApiUsageException("Neither p_fuzzy_cluster_id nor p_cluster_id is given");
	}

	/**
	 * Records the review of a cluster's staged documents: the process status, matched state number and match code,
	 * the worker and now.
	 *
	 * @param inOutParameterMap CURRENT_PROCESS_STATUS, stateno, UPDATED_MATCH_CD, p_worker_id, p_fuzzy_cluster_id or
	 *        p_cluster_id, ACTUAL_MATCH_CD, p_document_id (optional)
	 */
	public void updateMatchDetailsInStageTable(Map<String, Object> inOutParameterMap) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(BoundValues.integer(inOutParameterMap.get("CURRENT_PROCESS_STATUS")));
		parameters.add(BoundValues.text(inOutParameterMap.get("stateno")));
		parameters.add(BoundValues.text(inOutParameterMap.get("UPDATED_MATCH_CD")));
		parameters.add(BoundValues.longValue(inOutParameterMap.get("p_worker_id")));
		parameters.add(now());
		String jpqlText = "update ErPersonClustersStage s set s.currentProcessStatus = ?1, s.matchedStateno = ?2, s.matchCd = ?3, s.reviewUid = ?4, s.reviewDate = ?5 where "
				+ clusterCondition(inOutParameterMap, parameters);
		parameters.add(BoundValues.text(inOutParameterMap.get("ACTUAL_MATCH_CD")));
		jpqlText += " and s.matchCd = ?" + parameters.size() + " and s.currentProcessStatus = 17";
		if (inOutParameterMap.get("p_document_id") != null) {
			parameters.add(BoundValues.longValue(inOutParameterMap.get("p_document_id")));
			jpqlText += " and s.documentId = ?" + parameters.size();
		}
		jpql.update(jpqlText, parameters.toArray());
	}

	/** 'Y' if the cluster's staged documents (status 17) have a valid parent or are of a type reviewed without one; else null. @param inOutParameterMap p_fuzzy_cluster_id or p_cluster_id */
	public String isTheDocumentValidToProcess(Map<String, Object> inOutParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String condition = clusterCondition(inOutParameterMap, parameters);
		return jpql.one("select distinct 'Y' from ErPersonClustersStage s where " + condition
				+ " and s.currentProcessStatus = 17 and (s.validParent in ('Y', 'P') or s.docType in ('HIV_LAB', 'PRF', 'NYC_PRF', 'ADAP'))",
				Rows.scalar(String.class), parameters.toArray());
	}

	/** Marks a fuzzy cluster's staged C documents (status 17) as not a match (C_N) by the worker, now. @param inOutParameterMap worker_id, fuzzyClusterId */
	public void updateNotaMatchDetailsInStageTable(Map<String, Object> inOutParameterMap) {
		jpql.update("update ErPersonClustersStage s set s.matchCd = 'C_N', s.reviewUid = ?1, s.reviewDate = ?2 where s.fuzzyClusterId = ?3 and s.matchCd = 'C' and s.currentProcessStatus = 17",
				BoundValues.longValue(inOutParameterMap.get("worker_id")), now(), BoundValues.longValue(inOutParameterMap.get("fuzzyClusterId")));
	}

	/** Marks a fuzzy cluster's C documents (status 17) as not a match (C_N, status 47) by the worker, now. @param inOutParameterMap worker_id, fuzzyClusterId */
	public void updateNotaMatchDetailsInClustersTable(Map<String, Object> inOutParameterMap) {
		jpql.update("update ErPersonClusters c set c.matchCd = 'C_N', c.currentProcessStatus = 47, c.reviewUid = ?1, c.reviewDate = ?2 where c.fuzzyClusterId = ?3 and c.matchCd = 'C' and c.currentProcessStatus = 17",
				BoundValues.longValue(inOutParameterMap.get("worker_id")), now(), BoundValues.longValue(inOutParameterMap.get("fuzzyClusterId")));
	}

	// ---- helpers ------------------------------------------------------------------------------------------------------

	/** An IN parameter bound as MyBatis bound it: with the value's own type, a null with the mapper's jdbcType. */
	private static CallParam param(Object value, Class<?> nullType) {
		return in(value, value == null ? nullType : value.getClass());
	}

	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	private static void putIfPresent(Map<String, Object> map, String key, Object value) {
		if (value != null) {
			map.put(key, Values.text(value));
		}
	}

	/** A number as the former getObject gave it (a BigDecimal), when present. */
	private static void putNumber(Map<String, Object> map, String key, Object value) {
		if (value != null) {
			map.put(key, new BigDecimal(value.toString()));
		}
	}
}
