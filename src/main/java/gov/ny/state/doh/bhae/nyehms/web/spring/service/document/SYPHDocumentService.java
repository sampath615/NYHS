package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.SyphDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.SyphDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

@Service
public class SYPHDocumentService {
	
	@Autowired
	SyphDocRepository syphDocRepository;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

	
	private static Logger log = LoggerFactory.getLogger(SYPHDocumentService.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	public SyphDocData getSYPHDoc(int docID, BindingResult result,
			UserObject userObject, String sessionID) {
		
		boolean rollbackFlag = false;
		SyphDocData stdSYPHDocVO = null;
		log.debug(nyehmsLog + " fetching for docID : " + docID);
		try {
			stdSYPHDocVO = syphDocRepository.findDocument(docID);
		} catch (DataAccessException e) {
			rollbackFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			rollbackFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (rollbackFlag) {
				commonExceptionHandler.populateBindObjTrsnFail(result);
			}
		}

		return stdSYPHDocVO;
		
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
