package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.SSDMDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.SSDMDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 * This class is the implementation of SSDMDocumentService interface. This class
 * provides service methods for interacting with SSDM documents. a
 *
 * @author vxp06
 */
@Service
public class SSDMDocumentService {

	private static Logger log = LoggerFactory.getLogger(SSDMDocumentService.class);

    private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    @Autowired
    SSDMDocumentDAO ssdmDocumentDAO;
    @Autowired
    private CommonExceptionHandler commonExceptionHandler;

    /**
     * This method is called from controller to get data for a given SSDM doc
     * id.
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public SSDMDocVO getSSDMDoc(int docId, BindingResult result, UserObject userObject, String sessionID) {
        SSDMDocVO stdSSDMDocVO = null;
        boolean rollbackFlag = false;

        log.debug(nyehmsLog + " fetching for docId : " + docId);
        try {
            stdSSDMDocVO = ssdmDocumentDAO.getSSDMDoc(docId);

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

        return stdSSDMDocVO;
    }

    /**
     * Mostly unused function. Will have to find out why it is there.
     *
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public SSDMDocVO getLabDoc(int docId, BindingResult result,
            UserObject userObject, String sessionID) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public int writeRawSSDMDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
    	
    	

//   	    INSERT INTO STD_SSDM_DOC (SSN, PERSON_ZIP_AT_DEATH, SSDM_FILE, MATCH_PASS, BIRTH_YEAR, LNAME, SUFFIX, FNAME, MNAME, PERSON_COUNTY_AT_DEATH, MATCH_CD, PERSON_STATE_AT_DEATH, REVIEW_UID, DOB_TEXT, DOD_TEXT, MATCHED_STATENO, EHARS_STATUS_CD, MATCH_WEIGHT, DOCUMENT_ID, SSDM_SRC_ID, AGE_AT_DEATH, CURRENT_PROCESS_STATUS, QS_PARENT_DOC_ID, REVIEW_DATE, CREATE_DATE, EHARS_IMPORT_DATE, MATCHED_PROCESS_DATE, CREATE_UID, ORIG_LNAME)
//    	select trim(upper(SSN)), trim(upper(ZIP)), trim(SSDM_FILE), null, trim(upper(BTHYEAR)), trim(upper(LASTNAME)), trim(upper(NSUFFIX)), trim(upper(FIRSTNAME)), trim(upper(MIDDLENAME)), null, null, trim(upper(STZ)), null, TO_CHAR(FN_TO_DATE(trim(BTHDATE),'MM/DD/YYYY'), 'MM/DD/YYYY'), TO_CHAR(FN_TO_DATE(trim(DTHDATE),'MM/DD/YYYY'), 'MM/DD/YYYY'), null, null, null, DOCUMENT_ID, SSDM_SRC_ID, AGEDEATH, 5, null, null, null, null, null, null,  trim(upper(ORIGLNAME)) from raw_ssdm_doc where current_process_Status = 1

    	
    	docVO.put("CURRENT_PROCESS_STATUS", 5);
		
		
		docVO.put("FNAME", CommonUtil.NameValidation((String)docVO.get("FIRSTNAME")));
		docVO.put("MNAME", CommonUtil.NameValidation((String)docVO.get("MIDDLENAME")));
		docVO.put("LNAME", CommonUtil.NameValidation((String)docVO.get("LASTNAME")));
		
		docVO.put("SUFFIX", (String)docVO.get("NSUFFIX"));
		docVO.put("DOB_TEXT", (String)docVO.get("BTHDATE"));
		docVO.put("DOD_TEXT", (String)docVO.get("DTHDATE"));
		
		docVO.put("BIRTH_YEAR", (String)docVO.get("BTHYEAR"));
		docVO.put("AGE_AT_DEATH", docVO.get("AGEDEATH"));
		docVO.put("PERSON_STATE_AT_DEATH", (String)docVO.get("STZ"));
		docVO.put("PERSON_ZIP_AT_DEATH", (String)docVO.get("ZIP"));
		
		
		return ssdmDocumentDAO.writeStdDocData(docVO);
    }
    
    @Transactional
    public int writeAllRawSSDMDocDataToStdTable(){
    	
    	int recordsCountMovedToStd = ssdmDocumentDAO.writeAllRawSSDMDocDataToStdTable();
    	
    	int recordsCountMovedFromRaw =  ssdmDocumentDAO.updateRawDocumentStatus();
    	
    	if(recordsCountMovedToStd != recordsCountMovedFromRaw){
    		
    		String transactionMessage = "SSDM Records Count is not matching. Moved to STD layer : "+recordsCountMovedToStd+" , Records got updated in Raw Layer : "+recordsCountMovedFromRaw;
    		
    		log.error(transactionMessage);
    		
    		throw new DataRetrievalFailureException(transactionMessage);
    	}
    	
    	return recordsCountMovedToStd;
    }
    
    public void documentInitialValidation(){
    	ssdmDocumentDAO.documentInitialValidation();
    }
}
