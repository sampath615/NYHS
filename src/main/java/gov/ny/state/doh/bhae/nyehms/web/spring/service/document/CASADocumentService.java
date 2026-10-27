package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.CasaDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CasaDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.RawRecordStatusService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import lombok.RequiredArgsConstructor;

/**
 * The CASA records (STD_CASA_DOC): the CASA document screen and the import that standardizes a received record
 * (RAW_CASA_DOC).
 */
@Service
@RequiredArgsConstructor
public class CASADocumentService {

	private static final Logger log = LoggerFactory.getLogger(CASADocumentService.class);
	private static final String NYEHMS_LOG = AppConstants.nyehmLogDebugString.getName();
	private static final String DOCUMENT_TYPE = "CASA";

	private final CasaDocRepository casaDocRepository;
	private final RawRecordStatusService rawRecordStatusService;
	private final CommonExceptionHandler commonExceptionHandler;

	/**
	 * A CASA record for the document screen.
	 *
	 * @return the record, or null if there is none or it could not be read (the error is then logged and reported
	 *         through {@code result})
	 */
	public CasaDocData getCASADoc(int docID, BindingResult result, UserObject userObject, String sessionID) {
		log.debug("{} fetching for docID : {}", NYEHMS_LOG, docID);
		try {
			return casaDocRepository.findDocument(docID);
		} catch (Exception e) {
			log.error("{}  {}", NYEHMS_LOG, e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
			commonExceptionHandler.populateBindObjTrsnFail(result);
			return null;
		}
	}

	/**
	 * Writes the standardized record (STD_CASA_DOC) of a received record, or stops the record at the raw layer when
	 * it cannot identify a person.
	 *
	 * @param docVO the received record (a RAW_CASA_DOC row), value per column name; not changed
	 * @return 1 if written, 0 if stopped
	 */
	public int validateAndwriteRawCasaDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {
		if (!CasaStdDocMapping.isIdentifiable(docVO)) {
			rawRecordStatusService.writeErrorCode(CommonConstants.STOPPED_AT_RAW_LAYER_PROCESS_STATUS, documentId, DOCUMENT_TYPE);
			return 0;
		}
		casaDocRepository.saveAndFlush(CasaStdDocMapping.toStdDoc(docVO));
		return 1;
	}
}
