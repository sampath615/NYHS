package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.CasaDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CasaDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class CASADocumentService {
	
	@Autowired
	CasaDocRepository casaDocRepository;
	@Autowired
	private ManualQuartzJobService manualQuartzJobService; 
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

	
	private static Logger log = LoggerFactory.getLogger(CASADocumentService.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	public CasaDocData getCASADoc(int docID, BindingResult result,
			UserObject userObject, String sessionID) {
		
		boolean rollbackFlag = false;
		CasaDocData stdCASADocVO = null;
		log.debug("{} fetching for docID : {}",nyehmsLog, docID);
		try {
			stdCASADocVO = casaDocRepository.findDocument(docID);
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

		return stdCASADocVO;
		
	}

	public int validateAndwriteRawCasaDocDataToStdTable(
			Map<Object, Object> docVO, BigDecimal documentId){
		
		int resultCount = 0;

		int currentProcessStatus = 0;

		currentProcessStatus = validateCasaRecord(docVO);
		
		if(currentProcessStatus == CommonConstants.STOPPED_AT_RAW_LAYER_PROCESS_STATUS){
			manualQuartzJobService.writeErrorCode(currentProcessStatus, documentId, "CASA");
		}else{
			resultCount =  writeRawCasaDocDataToStdTable(docVO, documentId);
		}
		
		return resultCount;
		
		
	}
	
	private int validateCasaRecord(Map<Object, Object> docVO){
		
		String DOB_TEXT = (String)docVO.get("DOB");
		String SSN = (String)docVO.get("SSN");
		String FNAME = (String)docVO.get("FNAME");
		String LNAME = (String)docVO.get("LNAME");
		
		if(CommonUtil.isNullOrEmpty(DOB_TEXT) && CommonUtil.isNullOrEmpty(SSN) && CommonUtil.isNullOrEmpty(FNAME) && CommonUtil.isNullOrEmpty(LNAME)){
			return CommonConstants.STOPPED_AT_RAW_LAYER_PROCESS_STATUS; 
		}else{
			return CommonConstants.RAW_NEW_RECORD_PROCESS_STATUS;
		}
		
		
	}
	
	/**
	 * Writes the standardized record (STD_CASA_DOC) of a received record.
	 *
	 * @param docVO the received record (a RAW_CASA_DOC row), value per column name; not changed
	 * @return 1
	 */
	private int writeRawCasaDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {
		casaDocRepository.saveAndFlush(CasaStdDocMapping.toStdDoc(docVO));
		return 1;
	}
	
	
	

}
