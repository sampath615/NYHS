package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RawTTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawTthDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawAcrfDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawTthDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * The HIV testing and treatment history (TTH) of a case report form (formerly a MyBatis mapper with the same name and
 * methods), through Spring Data repositories over RawTthDoc and RawAcrfDoc. No native SQL.
 * <p>
 * <b>Kept as before:</b> {@link #updateTTHDocData} changes RAW_ACRF_DOC, not RAW_TTH_DOC; the insert writes
 * numNegTestBeforePos into both RESP_HIV_TEST_2YRS and COUNT_HIV_TEST_2YRS (numNegTestUnkRef is not written). Nothing
 * calls TTHDocumentService at present.
 */
@Repository
@RequiredArgsConstructor
public class TTHDocumentDAO {

	private final RawTthDocRepository tthDocs;
	private final RawAcrfDocRepository acrfDocs;

	/** Inserts the history of a form. */
	@Transactional
	public int insertTTHDocData(RawTTHDocVO rawTTHDocVO) throws DataAccessException {
		RawTTHDocVO vo = rawTTHDocVO;
		tthDocs.saveAndFlush(RawTthDoc.builder()
				.documentId(vo.getDocumentId())
				.acrfDocId(vo.getAcrfDocId())
				.mainTthSource(vo.getTestTreatmentHistorySource())
				.ptInterviewDate(vo.getDateReported())
				.everHadPos(vo.getPrevPosHivTest())
				.firstHivTestEver(vo.getDateFirstPosHivTest())
				.everHadNeg(vo.getNegHivTest())
				.lastNegDate(vo.getDateLastNegHivTest())
				.respHivTest2yrs(String.valueOf(vo.getNumNegTestBeforePos()))
				.countHivTest2yrs(vo.getNumNegTestBeforePos())
				.ptArv6Mo(vo.getTakenArvs())
				.ptArvName(vo.getArvMeds())
				.ptArvStartDate(vo.getDateArvsBegan())
				.ptArvEndDate(vo.getDateLastUsedArvs())
				.build());
		return 1;
	}

	/**
	 * Sets the history columns of the form (DOCUMENT_ID and any of the ACRF, PRF, AIRS form ids) in RAW_ACRF_DOC.
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateTTHDocData(RawTTHDocVO rawTTHDocVO) throws DataAccessException {
		RawTTHDocVO vo = rawTTHDocVO;
		return acrfDocs.updateTestTreatmentHistory(vo.getTestTreatmentHistorySource(), vo.getDateReported(), vo.getPrevPosHivTest(),
				vo.getDateFirstPosHivTest(), vo.getNegHivTest(), vo.getDateLastNegHivTest(), vo.getNumNegTestUnkRef(),
				vo.getNumNegTestBeforePos(), vo.getTakenArvs(), vo.getArvMeds(), vo.getDateArvsBegan(), vo.getDateLastUsedArvs(),
				vo.getDocumentId(), vo.getAcrfDocId(), vo.getPrfDocId(), vo.getAirsDocId());
	}

	/**
	 * The history of any of the VO's forms (ACRF, PRF or AIRS form id; an id of 0 matches 0, as before).
	 *
	 * @return the history, or null if there is none; more than one fails, as before
	 */
	public RawTTHDocVO getTTHDocData(RawTTHDocVO rawTTHDocVO) throws DataAccessException {
		List<RawTthDoc> rows = tthDocs.findByAcrfDocIdOrPrfDocIdOrAirsDocId(rawTTHDocVO.getAcrfDocId(), rawTTHDocVO.getPrfDocId(),
				rawTTHDocVO.getAirsDocId());
		if (rows.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, rows.size());
		}
		return rows.isEmpty() ? null : toVO(rows.get(0));
	}

	/** The VO as MyBatis filled it: only the values that are not null are set; a row without values is null. */
	private static RawTTHDocVO toVO(RawTthDoc doc) {
		RawTTHDocVO vo = new RawTTHDocVO();
		boolean set = false;
		if (doc.getDocumentId() != null) { vo.setDocumentId(doc.getDocumentId()); set = true; }
		if (doc.getAcrfDocId() != null) { vo.setAcrfDocId(doc.getAcrfDocId()); set = true; }
		if (doc.getPrfDocId() != null) { vo.setPrfDocId(doc.getPrfDocId()); set = true; }
		if (doc.getAirsDocId() != null) { vo.setAirsDocId(doc.getAirsDocId()); set = true; }
		if (doc.getMainTthSource() != null) { vo.setTestTreatmentHistorySource(doc.getMainTthSource()); set = true; }
		if (doc.getPtInterviewDate() != null) { vo.setDateReported(doc.getPtInterviewDate()); set = true; }
		if (doc.getEverHadPos() != null) { vo.setPrevPosHivTest(doc.getEverHadPos()); set = true; }
		if (doc.getFirstHivTestEver() != null) { vo.setDateFirstPosHivTest(doc.getFirstHivTestEver()); set = true; }
		if (doc.getEverHadNeg() != null) { vo.setNegHivTest(doc.getEverHadNeg()); set = true; }
		if (doc.getLastNegDate() != null) { vo.setDateLastNegHivTest(doc.getLastNegDate()); set = true; }
		if (doc.getRespHivTest2yrs() != null) { vo.setNumNegTestUnkRef(doc.getRespHivTest2yrs()); set = true; }
		if (doc.getCountHivTest2yrs() != null) { vo.setNumNegTestBeforePos(doc.getCountHivTest2yrs()); set = true; }
		if (doc.getPtArv6Mo() != null) { vo.setTakenArvs(doc.getPtArv6Mo()); set = true; }
		if (doc.getPtArvName() != null) { vo.setArvMeds(doc.getPtArvName()); set = true; }
		if (doc.getPtArvStartDate() != null) { vo.setDateArvsBegan(doc.getPtArvStartDate()); set = true; }
		if (doc.getPtArvEndDate() != null) { vo.setDateLastUsedArvs(doc.getPtArvEndDate()); set = true; }
		return set ? vo : null;
	}
}
