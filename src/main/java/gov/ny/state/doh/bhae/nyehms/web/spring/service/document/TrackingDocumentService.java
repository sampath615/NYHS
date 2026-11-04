package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.TrackingDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAddressDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAliasDocVO;
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
public class TrackingDocumentService {

	private static Logger log = LoggerFactory.getLogger(TrackingDocumentService.class);

    private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();
    
    @Autowired
    TrackingDocumentDAO trackingDocumentDAO;

    @Autowired
    CommonExceptionHandler commonExceptionHandler;
    /**
     * This function will return the Tracking Address document for a given docId
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public TRKAddressDocVO getTRKAddressDoc(int docId, BindingResult result, UserObject userObject,
            String sessionID) {

        
        TRKAddressDocVO tRKAddressDocVO = null;
        
        if(log.isDebugEnabled()){
        	log.debug(nyehmsLog + " fetching for docId : " + docId);
        }
        
        try {
        	tRKAddressDocVO = trackingDocumentDAO.getTRKAddressDoc(docId);
            
        } catch (DataAccessException e) {
            commonExceptionHandler.handleException(e, userObject, sessionID);
            log.error(nyehmsLog + "  " + e.getMessage());
        } catch (Exception e) {
            commonExceptionHandler.handleException(e, userObject, sessionID);
            log.error(nyehmsLog + "  " + e.getMessage());
        }
        return tRKAddressDocVO;
    }
    
    
    /**
     * This function will return the Tracking Alias document for a given docId
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public TRKAliasDocVO getTRKAliasDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id){
    	
    	TRKAliasDocVO tRKAliasDocVO = null;
        
        if(log.isDebugEnabled()){
        	log.debug(nyehmsLog + " fetching for docId : " + docId);
        }
        
        try {
        	tRKAliasDocVO = trackingDocumentDAO.getTRKAliasDoc(docId);
            
        } catch (DataAccessException e) {
            commonExceptionHandler.handleException(e, userObject, id);
            log.error(nyehmsLog + "  " + e.getMessage());
        } catch (Exception e) {
            commonExceptionHandler.handleException(e, userObject, id);
            log.error(nyehmsLog + "  " + e.getMessage());
        }
        return tRKAliasDocVO;
    }
}
