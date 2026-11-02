package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.OldCaseDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.OldCasesDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * The Old Cases document screen.
 *
 * @author vxp06
 */
@RequiredArgsConstructor
@Service
public class OldCaseDocumentService {

	private static final Logger log = LoggerFactory.getLogger(OldCaseDocumentService.class);
    private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    private final OldCasesDocRepository oldCasesDocRepository;
    private final CommonExceptionHandler commonExceptionHandler;
    
    public OldCaseDocData getOldCaseDocVO(int docId, BindingResult result, UserObject userObject,
            String sessionID) {

        boolean rollbackFlag = false;
        OldCaseDocData oldCaseDocVO = null;
        log.debug("{} fetching for docId : {}",nyehmsLog, docId);
        try {
        	oldCaseDocVO = oldCasesDocRepository.findDocument(docId);
            
        } catch (DataAccessException e) {
            rollbackFlag = true;
            commonExceptionHandler.handleException(e, userObject, sessionID);
            log.error(nyehmsLog + "  " + e.getMessage());
        } catch (Exception e) {
            rollbackFlag = true;
            commonExceptionHandler.handleException(e, userObject, sessionID);
            log.error(nyehmsLog + "  " + e.getMessage());
        } finally {
            if (rollbackFlag) {
                commonExceptionHandler.populateBindObjTrsnFail(result);
            }
        }
        return oldCaseDocVO;
    }

}
