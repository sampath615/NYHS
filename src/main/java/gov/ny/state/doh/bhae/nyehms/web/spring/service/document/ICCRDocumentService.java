package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AcrfTestResult;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.IccrDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.AcrfTestResultRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.IccrDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawIccrDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import lombok.RequiredArgsConstructor;

/**
 * The ICCR reports: the data entry screen (RAW_ICCR_DOC with its tests), the ICCR document screen, and the import
 * that standardizes a received report (STD_ICCR_DOC).
 */
@Service
@RequiredArgsConstructor
public class ICCRDocumentService {

	private static final Logger log = LoggerFactory.getLogger(ICCRDocumentService.class);
	private static final String DOCUMENT_TYPE = "ICCR";

	private final CommonExceptionHandler commonExceptionHandler;
	private final CommonDocumentService commonDocumentService;
	private final PlatformTransactionManager transactionManager;
	private final IccrDocRepository iccrDocRepository;
	private final RawIccrDocRepository rawIccrDocRepository;
	private final AcrfTestResultRepository acrfTestResultRepository;

	/**
	 * Saves an ICCR report entered on the data entry screen as a new document: a new document id, then the report
	 * (RAW_ICCR_DOC) with the tests filled in on it (RAW_ICCR_TESTS), in one transaction. The outcome (success or
	 * the error) is reported through {@code bindingResult}.
	 */
	public void saveICCRDataEntryForm(RawIccrDoc iccrDataEntryForm, BindingResult bindingResult, UserObject userObject) {
		try {
			new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
				int documentId = newDocumentId();
				rawIccrDocRepository.saveAndFlush(IccrDocMapping.toRawDoc(iccrDataEntryForm, documentId, userObject.getWorkerId()));
			});
			commonExceptionHandler.inspectDBTransacionResult(0, "ICCR Document Form saved successfully.", bindingResult);
		} catch (DataAccessException exception) {
			log.error("Error while Saving ICCR Document Form.  {}", exception.getMessage());
			commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
			commonExceptionHandler.inspectDBTransacionResult(-1, exception.getMessage(), bindingResult);
		} catch (Exception exception) {
			log.error("Error while Saving ICCR Document Form.  {}", exception.getMessage());
			commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
			commonExceptionHandler.inspectDBTransacionResult(-1, "Error while Saving ICCR Document Form, please contact the Helpline.", bindingResult);
		}
	}

	/** A new ICCR document id (the database's document locator procedure). */
	private int newDocumentId() {
		Map<Object, Object> map = new HashMap<>();
		map.put("p_document_type", DOCUMENT_TYPE);
		map.put("p_new_doc_id", "");
		map.put("p_parent_doc_id", "");
		try {
			commonDocumentService.getNewDocId(map);
		} catch (DataAccessException e) {
			log.error("Failed to create the ICCR document  : {}", e.getMessage());
			throw new DataRetrievalFailureException("Failed to create the ICCR document");
		}
		return (Integer) map.get("p_new_doc_id");
	}

	/** The current results of a test type, for the data entry screen's result list. */
	public List<AcrfTestResult> getCodedTestTypeResult(String testType) {
		return acrfTestResultRepository.findCurrentResultsOfTest(testType);
	}

	/** An ICCR report with its tests for the document screen. */
	public IccrDocData getICCRDocument(int docId) {
		return iccrDocRepository.findDocument(docId).withIccrTestList(rawIccrDocRepository.findTestsOfDocument(docId));
	}

	/**
	 * Writes the standardized report (STD_ICCR_DOC) of a received report.
	 *
	 * @param docVO the received report (a RAW_ICCR_DOC row), value per column name; not changed
	 * @return 1
	 */
	public int writeRawICCRDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {
		iccrDocRepository.saveAndFlush(IccrDocMapping.toStdDoc(docVO));
		return 1;
	}
}
