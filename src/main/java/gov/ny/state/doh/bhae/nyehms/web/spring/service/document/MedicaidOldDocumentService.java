package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MedicaidOldDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidOldDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


@Service
public class MedicaidOldDocumentService {

	private static Logger log = LoggerFactory.getLogger(MedicaidOldDocumentService.class);
    private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    @Autowired
    MedicaidOldDocumentDAO medicaidOldDocumentDAO;
    @Autowired
    CommonExceptionHandler commonExceptionHandler;

        /**
     * This function will return the TB document for a given docId
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public MedicaidOldDocVO getMedicaidOldDoc(int docId, BindingResult result, UserObject userObject,
            String sessionID) {

        boolean rollbackFlag = false;
        MedicaidOldDocVO medicaidOldDocVO = null;
        log.debug(nyehmsLog + " fetching for docId : " + docId);
        try {
        	medicaidOldDocVO = medicaidOldDocumentDAO.getMedicaidOldDoc(docId);
            
            if(medicaidOldDocVO == null){
            	medicaidOldDocVO = new MedicaidOldDocVO();
            }
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
        return medicaidOldDocVO;
    }

}
