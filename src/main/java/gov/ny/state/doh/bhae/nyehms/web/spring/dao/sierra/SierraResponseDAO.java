package gov.ny.state.doh.bhae.nyehms.web.spring.dao.sierra;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.decimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.integer;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RasSiResponse;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.Ras3Drug;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSiMutation;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.GenSequenceRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RasSiDrugScoreRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RasSiGeneRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RasSiResponseRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra.Ras3DrugRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra.RasSiDrugPartialScoreRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra.RasSiMutationRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra.RasSiValidationResultsRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra.RasSierraLogRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra.RasSierraProcessErrorsRepository;
import lombok.RequiredArgsConstructor;

/**
 * The Sierra (HIVdb) job: the lab documents to send, the responses it stores (header, validation messages, genes,
 * mutations, drug scores), its errors and log, and the RAS error screen (formerly a MyBatis mapper with the same name
 * and methods), through Spring Data repositories over the RAS_SI_* / RAS_SIERRA_* entities and GenSequence.
 * <p>
 * Kept as before: {@link #getRasDocIdsToParse} returns ROW_LIMIT - 1 rows (the former {@code rownum < ROW_LIMIT}); the
 * keys of the response header and of the mutations come from RAS_SEQ_ID / MUTATION_SEQ; the dates the former SQL set
 * with SYSDATE stay the database's SYSDATE. Rows of tables without a known key are written with HQL inserts.
 * <p>
 * Native SQL (one statement): {@link #loadNewSierraDataToFinalTable}, an anonymous PL/SQL block that loops over the new
 * documents, copies them from SIERRA_RESISTANCE_STG_VW and <b>COMMITs after each document</b>, inside the caller's
 * transaction (as before: the commit ends the transaction the JPA session shares, so what the caller wrote before is
 * committed too and is not rolled back by a later failure).
 */
@Repository
@RequiredArgsConstructor
public class SierraResponseDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY') */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	private final NativeStatements statements;
	private final RasSierraLogRepository logs;
	private final GenSequenceRepository sequences;
	private final RasSiResponseRepository responses;
	private final RasSiValidationResultsRepository validationResults;
	private final RasSiGeneRepository genes;
	private final RasSiMutationRepository mutations;
	private final Ras3DrugRepository drugs;
	private final RasSiDrugScoreRepository drugScores;
	private final RasSiDrugPartialScoreRepository partialScores;
	private final RasSierraProcessErrorsRepository errors;

	/**
	 * The highest lab document id the job has parsed.
	 *
	 * @throws IllegalStateException if the job has never run (MyBatis could not return null as an int)
	 */
	public int getLastParsedRasDocId() {
		Integer last = logs.lastParsedDocumentId();
		if (last == null) {
			throw new IllegalStateException("RAS_SIERRA_LOG has no DOCUMENT_ID_MAX (null for an int)");
		}
		return last;
	}

	/**
	 * The RAS eligible sequences to parse: after DOC_ID, or marked for reprise; by lab document id, ROW_LIMIT - 1 of them
	 * (as the former {@code rownum < ROW_LIMIT}; none for a missing or a too small limit).
	 *
	 * @param inputParameterMap DOC_ID, ROW_LIMIT
	 * @return maps of LAB_DOC_ID (Integer) and GEN_SEQ (String), a null value left out
	 */
	public List<Map<String, Object>> getRasDocIdsToParse(Map<String, Integer> inputParameterMap) {
		Integer rowLimit = inputParameterMap.get("ROW_LIMIT");
		List<Map<String, Object>> rows = new ArrayList<>();
		if (rowLimit == null || rowLimit - 1 <= 0) {
			return rows;
		}
		for (Object[] row : sequences.findToParse(inputParameterMap.get("DOC_ID"), Limit.of(rowLimit - 1))) {
			Map<String, Object> map = new HashMap<>();
			if (row[0] != null) {
				map.put("LAB_DOC_ID", row[0]);
			}
			if (row[1] != null) {
				map.put("GEN_SEQ", row[1]);
			}
			rows.add(map.isEmpty() ? null : map);
		}
		return rows;
	}

	/**
	 * Inserts the header of a response; its new SEQ_RESULT_ID (from RAS_SEQ_ID, a BigDecimal as MyBatis returned the
	 * generated key) is put into the map.
	 *
	 * @param inputParameterMap header (the lab document id, written as HEADER and DOCUMENT_ID), version, publishDate,
	 *        MD5, SHA512, firstNA, subType
	 * @return 1
	 */
	@Transactional
	public int storeSequenceHeaderInformation(Map<String, Object> inputParameterMap) {
		Map<String, Object> m = inputParameterMap;
		RasSiResponse response = responses.saveAndFlush(RasSiResponse.builder()
				.header(text(m.get("header")))
				.documentId(integer(m.get("header")))
				.version(text(m.get("version")))
				.publishDate(text(m.get("publishDate")))
				.md5(text(m.get("MD5")))
				.sha521(text(m.get("SHA512")))
				.firstna(text(m.get("firstNA")))
				.subType(text(m.get("subType")))
				.build());
		inputParameterMap.put("SEQ_RESULT_ID", BigDecimal.valueOf(response.getSeqResultId()));
		return 1;
	}

	/**
	 * Inserts the validation messages of a response.
	 *
	 * @param inputParameterMap SEQ_RESULT_ID, VALIDATION_RESULTS (maps of level and message)
	 * @return the number of rows inserted
	 * @throws IllegalArgumentException if there are no messages (the former statement was invalid SQL then)
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public int storeValidationResults(Map<String, Object> inputParameterMap) {
		Collection<Map<String, Object>> results = (Collection<Map<String, Object>>) inputParameterMap.get("VALIDATION_RESULTS");
		if (results == null || results.isEmpty()) {
			throw new IllegalArgumentException("No VALIDATION_RESULTS to insert");
		}
		Integer seqResultId = integer(inputParameterMap.get("SEQ_RESULT_ID"));
		int count = 0;
		for (Map<String, Object> result : results) {
			count += validationResults.insertResult(seqResultId, text(result.get("level")), text(result.get("message")));
		}
		return count;
	}

	/**
	 * Inserts a gene of a response.
	 *
	 * @param geneSeqInfo SEQ_RESULT_ID, GENE_SEQ_ID, name, alignedNAsLine, refAALine, firstAA, lastAA, firstNA, lastNA,
	 *        matchPcnt
	 * @return 1
	 */
	@Transactional
	public int storeGenSeqInfromation(Map<String, Object> geneSeqInfo) {
		Map<String, Object> m = geneSeqInfo;
		return genes.insertGene(integer(m.get("SEQ_RESULT_ID")), integer(m.get("GENE_SEQ_ID")), text(m.get("name")),
				text(m.get("alignedNAsLine")), text(m.get("refAALine")), text(m.get("firstAA")), text(m.get("lastAA")),
				text(m.get("firstNA")), text(m.get("lastNA")), text(m.get("matchPcnt")));
	}

	/**
	 * Inserts a mutation of a gene (MUTATION_SEQ from the sequence MUTATION_SEQ).
	 *
	 * @param inputMutation SEQ_RESULT_ID, GENE_SEQ_ID, MUTATION_TYPE, NAME, WILD_TYPE, POSITION, NUCLE_ACID, TRANSLATEDAA,
	 *        INSERTEDNAS and the flags ISINSERTION ... ISUNUSUAL
	 * @return 1
	 */
	@Transactional
	public int storeMutation(Map<String, Object> inputMutation) {
		Map<String, Object> m = inputMutation;
		mutations.saveAndFlush(RasSiMutation.builder()
				.seqResultId(integer(m.get("SEQ_RESULT_ID")))
				.geneSeqId(integer(m.get("GENE_SEQ_ID")))
				.mutationType(text(m.get("MUTATION_TYPE")))
				.mutationName(text(m.get("NAME")))
				.wildType(text(m.get("WILD_TYPE")))
				.position(text(m.get("POSITION")))
				.nucleAcid(text(m.get("NUCLE_ACID")))
				.translatedaa(text(m.get("TRANSLATEDAA")))
				.insertednas(text(m.get("INSERTEDNAS")))
				.isinsertion(text(m.get("ISINSERTION")))
				.isdeletion(text(m.get("ISDELETION")))
				.isindel(text(m.get("ISINDEL")))
				.isambiguous(text(m.get("ISAMBIGUOUS")))
				.isapobecmutation(text(m.get("ISAPOBECMUTATION")))
				.isapobecdrm(text(m.get("ISAPOBECDRM")))
				.hasstop(text(m.get("HASSTOP")))
				.isunusual(text(m.get("ISUNUSUAL")))
				.build());
		return 1;
	}

	/** The drugs of the algorithm: maps of ID (Integer) and ABBREV (String), a null value left out. */
	public List<Map<String, Object>> getDrugCodedList() {
		List<Map<String, Object>> rows = new ArrayList<>();
		for (Ras3Drug drug : drugs.findAll()) {
			Map<String, Object> map = new HashMap<>();
			if (drug.getId() != null) {
				map.put("ID", drug.getId());
			}
			if (drug.getAbbrev() != null) {
				map.put("ABBREV", drug.getAbbrev());
			}
			rows.add(map.isEmpty() ? null : map);
		}
		return rows;
	}

	/**
	 * Inserts a drug score of a response.
	 *
	 * @param inputDrug SEQ_RESULT_ID, GENE_SEQ_ID, DRUG_ID, DRUG_CODE, NAME (the class name), TYPE (the class), SCORE,
	 *        RESISTANCE_LEVEL, LEVEL_TEXT, SIR, GENE_NAME
	 * @return 1
	 */
	@Transactional
	public int storeDrugDetails(Map<String, Object> inputDrug) {
		Map<String, Object> m = inputDrug;
		return drugScores.insertScore(integer(m.get("SEQ_RESULT_ID")), integer(m.get("GENE_SEQ_ID")), integer(m.get("DRUG_ID")),
				text(m.get("DRUG_CODE")), text(m.get("NAME")), text(m.get("TYPE")), text(m.get("SCORE")),
				decimal(m.get("RESISTANCE_LEVEL")), text(m.get("LEVEL_TEXT")), text(m.get("SIR")), text(m.get("GENE_NAME")));
	}

	/**
	 * Inserts a partial score of a drug.
	 *
	 * @param drupgPartialListMap SEQ_RESULT_ID, DRUG_ID, MUTATION_TEXT, SCORE
	 * @return 1
	 */
	@Transactional
	public int storeDrugPartialData(Map<String, Object> drupgPartialListMap) {
		Map<String, Object> m = drupgPartialListMap;
		return partialScores.insertPartialScore(integer(m.get("SEQ_RESULT_ID")), integer(m.get("DRUG_ID")),
				text(m.get("MUTATION_TEXT")), text(m.get("SCORE")));
	}

	/**
	 * Inserts a failed document.
	 *
	 * @param inputParameterMap DOCUMENT_ID, ERROR_DESC, ACTIVE_FLAG, CREATE_ID
	 */
	@Transactional
	public void addErrorDocument(Map<String, Object> inputParameterMap) {
		Map<String, Object> m = inputParameterMap;
		errors.insertError(longValue(m.get("DOCUMENT_ID")), text(m.get("ERROR_DESC")), text(m.get("ACTIVE_FLAG")),
				text(m.get("CREATE_ID")));
	}

	/**
	 * Inserts the log of a run.
	 *
	 * @param logParameter DOCUMENT_ID_MIN, DOCUMENT_ID_MAX, NO_OF_ERRORS, TOTAL_NO_RECORDS
	 */
	@Transactional
	public void addSierraLoadLog(Map<String, Object> logParameter) {
		Map<String, Object> m = logParameter;
		logs.insertRun(integer(m.get("DOCUMENT_ID_MIN")), integer(m.get("DOCUMENT_ID_MAX")), integer(m.get("NO_OF_ERRORS")),
				integer(m.get("TOTAL_NO_RECORDS")));
	}

	/**
	 * The active failed documents for the RAS error screen: documentId (a BigDecimal, as the NUMBER column was read),
	 * sequence, documentType ('HIV_LAB'), errorDesc, createdDate (MM/DD/YYYY); a null value left out.
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> loadActiveFailedRasDocuments() {
		List<JSONObject> rows = new ArrayList<>();
		for (Object[] row : errors.findActiveFailed()) {
			JSONObject json = new JSONObject();
			if (row[0] != null) {
				json.put("documentId", new BigDecimal(row[0].toString()));
			}
			if (row[1] != null) {
				json.put("sequence", row[1]);
			}
			json.put("documentType", "HIV_LAB");
			if (row[2] != null) {
				json.put("errorDesc", row[2]);
			}
			if (row[3] != null) {
				json.put("createdDate", DATE.format(((Timestamp) row[3]).toLocalDateTime()));
			}
			rows.add(json);
		}
		return rows;
	}

	/** The number of Sierra responses of a lab document. */
	public int isLabDocIdExist(Integer rasDocId) {
		return (int) responses.countOfDocument(rasDocId);
	}

	/** The number of rows of a lab document marked for reprise ('R'). */
	public int isLabDocIdExistInErrorTable(Integer rasDocId) {
		return (int) errors.countMarkedForReprise(rasDocId == null ? null : rasDocId.longValue());
	}

	/**
	 * Marks the active rows of the selected documents for reprise ('R', when ACTIVE_FLAG_STATUS is 'REPRISE') or as
	 * done ('D', otherwise), with the comments and the worker.
	 *
	 * @param inOutParameterMap p_document_list (the document ids), ACTIVE_FLAG_STATUS, p_user_id, p_comments
	 * @return the number of rows changed
	 * @throws IllegalArgumentException if there are no documents (the former statement was invalid SQL then)
	 */
	@Transactional
	public int updateRASSelectedDocumentStatus(Map<Object, Object> inOutParameterMap) {
		List<Long> documentIds = longs(inOutParameterMap.get("p_document_list"));
		if (documentIds.isEmpty()) {
			throw new IllegalArgumentException("No p_document_list to update");
		}
		String flag = "REPRISE".equals(inOutParameterMap.get("ACTIVE_FLAG_STATUS")) ? "R" : "D";
		return errors.changeActiveRows(text(inOutParameterMap.get("p_comments")), text(inOutParameterMap.get("p_user_id")),
				flag, documentIds);
	}

	/** Marks every row of a lab document as reprised ('R_P'). */
	@Transactional
	public void processRepriseDocuemnt(Integer rasDocId) {
		errors.markReprised(rasDocId == null ? null : rasDocId.longValue());
	}

	/** Adds a reprise row for every RAS eligible sequence (from lab document 89073264 on) without a Sierra response. */
	@Transactional
	public void loadMissingRasDocuments() {
		errors.insertMissingDocuments();
	}

	/**
	 * Copies the new documents of RAS_SI_RESPONSE from SIERRA_RESISTANCE_STG_VW into SIERRA_RESISTANCE_RESPONSE.
	 * <p>
	 * Native: an anonymous PL/SQL block (a loop with a COMMIT per document), the former SQL unchanged. The COMMIT commits
	 * the caller's transaction too, as before.
	 */
	@Transactional
	public void loadNewSierraDataToFinalTable() {
		statements.update(LOAD_NEW_SIERRA_DATA);
	}

	/** The document ids of p_document_list: a long[] (as the screen passes them), an array or a collection. */
	private static List<Long> longs(Object list) {
		List<Long> ids = new ArrayList<>();
		if (list instanceof long[] array) {
			for (long id : array) {
				ids.add(id);
			}
		} else if (list instanceof Object[] array) {
			for (Object id : array) {
				ids.add(longValue(id));
			}
		} else if (list instanceof Collection<?> collection) {
			for (Object id : collection) {
				ids.add(longValue(id));
			}
		} else if (list != null) {
			throw new IllegalArgumentException("Not a list of document ids: " + list);
		}
		return ids;
	}

	/** The former PL/SQL block, unchanged. */
	private static final String LOAD_NEW_SIERRA_DATA = """
				BEGIN
    FOR i IN (SELECT Distinct document_id
                FROM ras_si_response a
               WHERE NOT EXISTS (SELECT 1 FROM sierra_resistance_response b WHERE b.document_id = a.document_id)
            )
    LOOP
        INSERT INTO sierra_resistance_response
        (stateno, document_id, seq_result_id, run_date, algo, sub_type, pr_aligned_na_seq, pr_aligned_aa_seq, rt_aligned_na_seq, rt_aligned_aa_seq, in_aligned_na_seq, in_aligned_aa_seq, pr_major_muts_found, pr_acc_muts_found, pr_other_muts_found, rt_nrti_muts_found, rt_nnrti_muts_found, rt_other_muts_found, in_major_muts_found, in_acc_muts_found, in_other_muts_found, nfv_drug_class, nfv_score, nfv_resistance_level, nfv_level_text, nfv_sir, nfv_scoredmuts, abc_drug_class, abc_score, abc_resistance_level, abc_level_text, abc_sir, abc_scoredmuts, ddi_drug_class, ddi_score, ddi_resistance_level, ddi_level_text, ddi_sir, ddi_scoredmuts, ftc_drug_class, ftc_score, ftc_resistance_level, ftc_level_text, ftc_sir, ftc_scoredmuts, threetc_drug_class, threetc_score, threetc_resistance_level, threetc_level_text, threetc_sir, threetc_scoredmuts, d4t_drug_class, d4t_score, d4t_resistance_level, d4t_level_text, d4t_sir, d4t_scoredmuts, tdf_drug_class, tdf_score, tdf_resistance_level, tdf_level_text, tdf_sir, tdf_scoredmuts, azt_drug_class, azt_score, azt_resistance_level, azt_level_text, azt_sir, azt_scoredmuts, efv_drug_class, efv_score, efv_resistance_level, efv_level_text, efv_sir, efv_scoredmuts, nvp_drug_class, nvp_score, nvp_resistance_level, nvp_level_text, nvp_sir, nvp_scoredmuts, atv_r_drug_class, atv_r_score, atv_r_resistance_level, atv_r_level_text, atv_r_sir, atv_r_scoredmuts, idv_r_drug_class, idv_r_score, idv_r_resistance_level, idv_r_level_text, idv_r_sir, idv_r_scoredmuts, lpv_r_drug_class, lpv_r_score, lpv_r_resistance_level, lpv_r_level_text, lpv_r_sir, lpv_r_scoredmuts, sqv_r_drug_class, sqv_r_score, sqv_r_resistance_level, sqv_r_level_text, sqv_r_sir, sqv_r_scoredmuts, tpv_r_drug_class, tpv_r_score, tpv_r_resistance_level, tpv_r_level_text, tpv_r_sir, tpv_r_scoredmuts, drv_r_drug_class, drv_r_score, drv_r_resistance_level, drv_r_level_text, drv_r_sir, drv_r_scoredmuts, etr_drug_class, etr_score, etr_resistance_level, etr_level_text, etr_sir, etr_scoredmuts, fpv_r_drug_class, fpv_r_score, fpv_r_resistance_level, fpv_r_level_text, fpv_r_sir, fpv_r_scoredmuts, ral_drug_class, ral_score, ral_resistance_level, ral_level_text, ral_sir, ral_scoredmuts, evg_drug_class, evg_score, evg_resistance_level, evg_level_text, evg_sir, evg_scoredmuts, dtg_drug_class, dtg_score, dtg_resistance_level, dtg_level_text, dtg_sir, dtg_scoredmuts, rpv_drug_class, rpv_score, rpv_resistance_level, rpv_level_text, rpv_sir, rpv_scoredmuts, dor_drug_class, dor_score, dor_resistance_level, dor_level_text, dor_sir, dor_scoredmuts, bic_drug_class, bic_score, bic_resistance_level, bic_level_text, bic_sir, bic_scoredmuts, CAB_DRUG_CLASS, CAB_SCORE, CAB_RESISTANCE_LEVEL, CAB_LEVEL_TEXT, CAB_SIR, CAB_SCOREDMUTS, piclassres, nrticlassres, nnrticlassres, iniclassres, classres, gene_name)
        SELECT stateno, document_id, seq_result_id, run_date, algo, sub_type, pr_aligned_na_seq, pr_aligned_aa_seq, rt_aligned_na_seq, rt_aligned_aa_seq, in_aligned_na_seq, in_aligned_aa_seq, pr_major_muts_found, pr_acc_muts_found, pr_other_muts_found, rt_nrti_muts_found, rt_nnrti_muts_found, rt_other_muts_found, in_major_muts_found, in_acc_muts_found, in_other_muts_found, nfv_drug_class, nfv_score, nfv_resistance_level, nfv_level_text, nfv_sir, nfv_scoredmuts, abc_drug_class, abc_score, abc_resistance_level, abc_level_text, abc_sir, abc_scoredmuts, ddi_drug_class, ddi_score, ddi_resistance_level, ddi_level_text, ddi_sir, ddi_scoredmuts, ftc_drug_class, ftc_score, ftc_resistance_level, ftc_level_text, ftc_sir, ftc_scoredmuts, threetc_drug_class, threetc_score, threetc_resistance_level, threetc_level_text, threetc_sir, threetc_scoredmuts, d4t_drug_class, d4t_score, d4t_resistance_level, d4t_level_text, d4t_sir, d4t_scoredmuts, tdf_drug_class, tdf_score, tdf_resistance_level, tdf_level_text, tdf_sir, tdf_scoredmuts, azt_drug_class, azt_score, azt_resistance_level, azt_level_text, azt_sir, azt_scoredmuts, efv_drug_class, efv_score, efv_resistance_level, efv_level_text, efv_sir, efv_scoredmuts, nvp_drug_class, nvp_score, nvp_resistance_level, nvp_level_text, nvp_sir, nvp_scoredmuts, atv_r_drug_class, atv_r_score, atv_r_resistance_level, atv_r_level_text, atv_r_sir, atv_r_scoredmuts, idv_r_drug_class, idv_r_score, idv_r_resistance_level, idv_r_level_text, idv_r_sir, idv_r_scoredmuts, lpv_r_drug_class, lpv_r_score, lpv_r_resistance_level, lpv_r_level_text, lpv_r_sir, lpv_r_scoredmuts, sqv_r_drug_class, sqv_r_score, sqv_r_resistance_level, sqv_r_level_text, sqv_r_sir, sqv_r_scoredmuts, tpv_r_drug_class, tpv_r_score, tpv_r_resistance_level, tpv_r_level_text, tpv_r_sir, tpv_r_scoredmuts, drv_r_drug_class, drv_r_score, drv_r_resistance_level, drv_r_level_text, drv_r_sir, drv_r_scoredmuts, etr_drug_class, etr_score, etr_resistance_level, etr_level_text, etr_sir, etr_scoredmuts, fpv_r_drug_class, fpv_r_score, fpv_r_resistance_level, fpv_r_level_text, fpv_r_sir, fpv_r_scoredmuts, ral_drug_class, ral_score, ral_resistance_level, ral_level_text, ral_sir, ral_scoredmuts, evg_drug_class, evg_score, evg_resistance_level, evg_level_text, evg_sir, evg_scoredmuts, dtg_drug_class, dtg_score, dtg_resistance_level, dtg_level_text, dtg_sir, dtg_scoredmuts, rpv_drug_class, rpv_score, rpv_resistance_level, rpv_level_text, rpv_sir, rpv_scoredmuts, dor_drug_class, dor_score, dor_resistance_level, dor_level_text, dor_sir, dor_scoredmuts, bic_drug_class, bic_score, bic_resistance_level, bic_level_text, bic_sir, bic_scoredmuts, CAB_DRUG_CLASS, CAB_SCORE, CAB_RESISTANCE_LEVEL, CAB_LEVEL_TEXT, CAB_SIR, CAB_SCOREDMUTS, piclassres, nrticlassres, nnrticlassres, iniclassres, classres, gene_name
          FROM sierra_resistance_stg_vw
         WHERE document_id = i.document_id;
         
        COMMIT;
    END LOOP;
	END;
""";
}
