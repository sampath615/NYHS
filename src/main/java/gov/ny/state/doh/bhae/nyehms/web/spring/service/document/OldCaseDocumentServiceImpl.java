package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.OldCasesDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.OldCaseDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * This is implementation of the TBService interface. This provides
 * implementation for all the methods for TB document.
 *
 * @author vxp06
 */
@Service
public class OldCaseDocumentServiceImpl implements OldCaseDocumentService {

	private static Logger log = LoggerFactory.getLogger(OldCaseDocumentServiceImpl.class);
    private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    @Autowired
    OldCasesDocumentDAO oldCasesDocumentDAO;
    @Autowired
    CommonExceptionHandler commonExceptionHandler;
    
    @Override
    public OldCaseDocVO getOldCaseDocVO(int docId, BindingResult result, UserObject userObject,
            String sessionID) {

        boolean rollbackFlag = false;
        OldCaseDocVO oldCaseDocVO = null;
        log.debug("{} fetching for docId : {}",nyehmsLog, docId);
        try {
        	oldCaseDocVO = oldCasesDocumentDAO.getOldCaseDocVO(docId);
            
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
