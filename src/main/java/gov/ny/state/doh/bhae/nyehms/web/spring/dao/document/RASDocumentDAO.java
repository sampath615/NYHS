package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RASDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdRasDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RasDocRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SierraResponseRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.GenSequenceRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RasSiDrugScoreRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RasSiGeneRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RasSiResponseRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.StdRasDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RasSequenceLinksRepository;
import lombok.RequiredArgsConstructor;

/**
 * The resistance (RAS) documents: the document screen (from STD_RAS_DOC, or from a Sierra response) and the changes when
 * a resistance document is edited or deleted (formerly a MyBatis mapper with the same name and methods), through Spring
 * Data repositories over StdRasDoc, RasSiResponse, RasSiGene, RasSiDrugScore, GenSequence and RasSequenceLinks. No
 * native SQL.
 * <p>
 * Kept as before (bugs included): {@link #getLinkedResDocs} fails when the lab document has more than one active link
 * (the sequence is a scalar subquery, ORA-01427); {@link #getLinkedRecCount} fails when the lab document has no link
 * (null for an int, as MyBatis failed) or several. Dates are formatted as TO_CHAR(..., 'MM/DD/YYYY') formatted them;
 * VSEQDEACTIVATIONDATE stays the database's SYSDATE.
 */
@Repository
@RequiredArgsConstructor
public class RASDocumentDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY') */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	private final StdRasDocRepository rasDocs;
	private final RasSiResponseRepository responses;
	private final RasSiGeneRepository genes;
	private final RasSiDrugScoreRepository drugScores;
	private final GenSequenceRepository sequences;
	private final RasSequenceLinksRepository links;

	/**
	 * A resistance document for the document screen, with the STATENO of its current person match.
	 *
	 * @return the document, or null if there is none (or it has no DOCUMENT_LOCATOR row)
	 * @throws IncorrectResultSizeDataAccessException if it has several current person matches, as before
	 */
	public RASDocVO getRASDoc(int documentId) throws DataAccessException {
		RasDocRow row = one(rasDocs.findWithMatchedStateno(documentId));
		if (row == null) {
			return null;
		}
		StdRasDoc doc = row.doc();
		RASDocVO vo = new RASDocVO();
		boolean set = false;
		if (doc.getDocumentId() != null) { vo.setDOCUMENT_ID(doc.getDocumentId()); set = true; }
		if (row.matchedStateno() != null) { vo.setMATCHED_STATENO(row.matchedStateno()); set = true; }
		if (doc.getFirstName() != null) { vo.setFIRST_NAME(doc.getFirstName()); set = true; }
		if (doc.getMiddleName() != null) { vo.setMIDDLE_NAME(doc.getMiddleName()); set = true; }
		if (doc.getLastName() != null) { vo.setLAST_NAME(doc.getLastName()); set = true; }
		if (doc.getDob() != null) { vo.setDOB(date(doc.getDob())); set = true; }
		if (doc.getVseqid() != null) { vo.setVSEQID(doc.getVseqid()); set = true; }
		if (doc.getVseqcreationdate() != null) { vo.setVSEQCREATIONDATE(date(doc.getVseqcreationdate())); set = true; }
		if (doc.getRecordsGroupedCount() != null) { vo.setRECORDS_GROUPED_COUNT(doc.getRecordsGroupedCount()); set = true; }
		if (doc.getResistanceDocId1() != null) { vo.setRESISTANCE_DOC_ID_1(doc.getResistanceDocId1()); set = true; }
		if (doc.getResistanceTestDate1() != null) { vo.setRESISTANCE_TEST_DATE_1(date(doc.getResistanceTestDate1())); set = true; }
		if (doc.getResistanceDocId2() != null) { vo.setRESISTANCE_DOC_ID_2(doc.getResistanceDocId2()); set = true; }
		if (doc.getResistanceTestDate2() != null) { vo.setRESISTANCE_TEST_DATE_2(date(doc.getResistanceTestDate2())); set = true; }
		if (doc.getResistanceDocId3() != null) { vo.setRESISTANCE_DOC_ID_3(doc.getResistanceDocId3()); set = true; }
		if (doc.getResistanceTestDate3() != null) { vo.setRESISTANCE_TEST_DATE_3(date(doc.getResistanceTestDate3())); set = true; }
		if (doc.getGenesfoundtext() != null) { vo.setGENESFOUNDTEXT(doc.getGenesfoundtext()); set = true; }
		if (doc.getPhylosubtype() != null) { vo.setPHYLOSUBTYPE(doc.getPhylosubtype()); set = true; }
		if (doc.getResistanceDrugClassPr() != null) { vo.setRESISTANCE_DRUG_CLASS_PR(doc.getResistanceDrugClassPr()); set = true; }
		if (doc.getResistanceDrugClassNrti() != null) { vo.setRESISTANCE_DRUG_CLASS_NRTI(doc.getResistanceDrugClassNrti()); set = true; }
		if (doc.getResistanceDrugClassNnrti() != null) { vo.setRESISTANCE_DRUG_CLASS_NNRTI(doc.getResistanceDrugClassNnrti()); set = true; }
		if (doc.getResistanceDrugClassIn() != null) { vo.setRESISTANCE_DRUG_CLASS_IN(doc.getResistanceDrugClassIn()); set = true; }
		return set ? vo : null;
	}

	/** The gene names of a Sierra response joined with '_' (null if there are none). */
	public String getRASDocGeneNames(int SEQ_RESULT_ID) {
		return genes.geneNames(SEQ_RESULT_ID);
	}

	/** The drug classes of a Sierra response with a resistance (PI, NRTI, NNRTI above level 3, INSTI above 2). */
	public List<String> getRASDocDrugClasses(int SEQ_RESULT_ID) {
		return drugScores.resistantDrugClasses(SEQ_RESULT_ID);
	}

	/**
	 * Never implemented: the former mapper had no statement for it, so a call failed (MyBatis' "invalid bound
	 * statement"). Nothing calls it.
	 *
	 * @throws UnsupportedOperationException always
	 */
	public JSONObject getLinkedResistanceDocuments(int documentId) {
		throw new UnsupportedOperationException("RASDocumentDAO.getLinkedResistanceDocuments has no statement");
	}

	/** The number of RAS eligible, linked sequences of a lab document. */
	public int isDocumentLinked(int documentId) {
		return (int) sequences.countLinked(documentId);
	}

	/**
	 * Sets RAS_ELIG of the sequences of a lab document and clears their link date.
	 *
	 * @param inputOutputParameterMap rasElig, documentId
	 */
	@Transactional
	public void updateGenSeqRasElig(Map<Object, Object> inputOutputParameterMap) {
		sequences.changeRasElig(BoundValues.text(inputOutputParameterMap.get("rasElig")),
				BoundValues.integer(inputOutputParameterMap.get("documentId")));
	}

	/**
	 * BHAEGENESUM of the link of a lab document.
	 *
	 * @throws IllegalStateException if there is no link (MyBatis could not return null as an int)
	 * @throws IncorrectResultSizeDataAccessException if there are several links
	 */
	public int getLinkedRecCount(int documentId) {
		List<Integer> sums = links.geneSums(documentId);
		if (sums.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, sums.size());
		}
		if (sums.isEmpty() || sums.get(0) == null) {
			throw new IllegalStateException("No BHAEGENESUM for lab document " + documentId + " (null for an int)");
		}
		return sums.get(0);
	}

	/**
	 * Deactivates every link of the sequences a lab document is linked to.
	 *
	 * @param inputOutputParameterMap user_id (the worker), documentId
	 */
	@Transactional
	public void updateRasSeqLinkStatus(Map<Object, Object> inputOutputParameterMap) {
		links.deactivateSequencesOf(BoundValues.longValue(inputOutputParameterMap.get("user_id")),
				BoundValues.integer(inputOutputParameterMap.get("documentId")));
	}

	/** Sets status 9 on the linked documents (status 5) that name this resistance document. */
	@Transactional
	public void updateStdRasDoc(int documentId) {
		rasDocs.deactivateLinkedDocuments(documentId);
	}

	/** The lab documents linked to the active sequence of a lab document (fails if it has several active links). */
	public List<Integer> getLinkedResDocs(int documentId) {
		return links.labDocsOfActiveSequence(documentId);
	}

	/**
	 * A Sierra response of a lab document with the person of the lab report, for the document screen.
	 *
	 * @return the response, or null if there is none
	 * @throws IncorrectResultSizeDataAccessException if there are several, as before
	 */
	public RASDocVO getSierraResponseData(int documentId) {
		SierraResponseRow row = one(responses.findWithLabReport(documentId));
		if (row == null) {
			return null;
		}
		RASDocVO vo = new RASDocVO();
		boolean set = false;
		if (row.fname() != null) { vo.setFIRST_NAME(row.fname()); set = true; }
		if (row.lname() != null) { vo.setLAST_NAME(row.lname()); set = true; }
		if (row.mname() != null) { vo.setMIDDLE_NAME(row.mname()); set = true; }
		if (row.dobText() != null) { vo.setDOB(row.dobText()); set = true; }
		if (row.seqResultId() != null) { vo.setSEQ_RESULT_ID(row.seqResultId().intValue()); set = true; }
		if (row.documentId() != null) { vo.setDOCUMENT_ID(row.documentId()); set = true; }
		if (row.collectionDate() != null) { vo.setCOLLECTION_DATE(date(row.collectionDate())); set = true; }
		if (row.subType() != null) { vo.setPHYLOSUBTYPE(row.subType()); set = true; }
		return set ? vo : null;
	}

	private static String date(Timestamp time) {
		return DATE.format(time.toLocalDateTime());
	}

	private static <T> T one(List<T> rows) {
		if (rows.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, rows.size());
		}
		return rows.isEmpty() ? null : rows.get(0);
	}
}
