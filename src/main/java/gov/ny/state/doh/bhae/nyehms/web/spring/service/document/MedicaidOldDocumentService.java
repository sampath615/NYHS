package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.MedicaidOldDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.MedicaidOldDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


@RequiredArgsConstructor
@Service
public class MedicaidOldDocumentService {

	private static final Logger log = LoggerFactory.getLogger(MedicaidOldDocumentService.class);
    private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    private final MedicaidOldDocRepository medicaidOldDocRepository;
    private final CommonExceptionHandler commonExceptionHandler;

        /**
     * This function will return the TB document for a given docId
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public MedicaidOldDocData getMedicaidOldDoc(int docId, BindingResult result, UserObject userObject,
            String sessionID) {

        boolean rollbackFlag = false;
        MedicaidOldDocData medicaidOldDocVO = null;
        log.debug(nyehmsLog + " fetching for docId : " + docId);
        try {
        	medicaidOldDocVO = medicaidOldDocRepository.findDocument(docId);
            
            if(medicaidOldDocVO == null){
            	medicaidOldDocVO = MedicaidOldDocData.empty();
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
