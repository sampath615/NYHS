package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SyphDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.SyphDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import lombok.RequiredArgsConstructor;

/**
 * The syphilis reports (STD_SYPHILIS_DOC): the syphilis document screen and the import that standardizes a received
 * report (RAW_SYPHILIS_DOC).
 */
@Service
@RequiredArgsConstructor
public class SYPHDocumentService {

	private static final Logger log = LoggerFactory.getLogger(SYPHDocumentService.class);
	private static final String NYEHMS_LOG = AppConstants.nyehmLogDebugString.getName();

	private final SyphDocRepository syphDocRepository;
	private final CommonExceptionHandler commonExceptionHandler;

	/**
	 * A syphilis report for the document screen.
	 *
	 * @return the report, or null if there is none or it could not be read (the error is then logged and reported
	 *         through {@code result})
	 */
	public SyphDocData getSYPHDoc(int docID, BindingResult result, UserObject userObject, String sessionID) {
		log.debug("{} fetching for docID : {}", NYEHMS_LOG, docID);
		try {
			return syphDocRepository.findDocument(docID);
		} catch (Exception e) {
			log.error("{}  {}", NYEHMS_LOG, e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
			commonExceptionHandler.populateBindObjTrsnFail(result);
			return null;
		}
	}

	/**
	 * Writes the standardized report (STD_SYPHILIS_DOC) of a received report.
	 *
	 * @param docVO the received report (a RAW_SYPHILIS_DOC row), value per column name; not changed
	 * @return 1
	 */
	public int writeRawSYPHDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {
		syphDocRepository.saveAndFlush(SyphStdDocMapping.toStdDoc(docVO));
		return 1;
	}
}
