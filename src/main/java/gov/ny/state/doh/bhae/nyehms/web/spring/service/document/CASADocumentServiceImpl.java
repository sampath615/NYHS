package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CASADocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CASADocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class CASADocumentServiceImpl implements CASADocumentService {
	
	@Autowired
	CASADocumentDAO	casaDocumentDAO;
	@Autowired
	private ManualQuartzJobService manualQuartzJobService; 
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

	
	private static Logger log = LoggerFactory.getLogger(CASADocumentServiceImpl.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	@Override
	public CASADocVO getCASADoc(int docID, BindingResult result,
			UserObject userObject, String sessionID) {
		
		boolean rollbackFlag = false;
		CASADocVO stdCASADocVO = null;
		log.debug("{} fetching for docID : {}",nyehmsLog, docID);
		try {
			stdCASADocVO = casaDocumentDAO.getCASADoc(docID);
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

	@Override
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
	
	private int writeRawCasaDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
		
		docVO.put("GENDER", LabCommonUtils.checkCurrentGenderData((String)docVO.get("GENDER")));
		
		
		docVO.put("RACE_INDIAN", "0");
		docVO.put("RACE_BLACK", "0");
		docVO.put("RACE_ASIAN", "0");
		docVO.put("RACE_PACIFIC", "0");
		docVO.put("RACE_WHITE", "0");
		docVO.put("RACE_MULTIPLE", "0");
		docVO.put("RACE_OTHER", "0");
		docVO.put("RACE_UNKNOWN", "0");
		
		if(CommonUtil.isNotNullAndEmpty((String)docVO.get("RACE"))){
			String race = ((String)docVO.get("RACE")).trim();
			
			if("4".equals(race)){
				docVO.put("ETHNICITY", "09");
				docVO.put("RACE_INDIAN", "1");
			}else if("2".equals(race)){
				docVO.put("ETHNICITY", "02");
				docVO.put("RACE_BLACK", "1");
			}else if("3".equals(race)){
				docVO.put("RACE_ASIAN", "1");
				docVO.put("ETHNICITY", "09");
			}else if("1".equals(race)){
				docVO.put("ETHNICITY", "02");
				docVO.put("RACE_WHITE", "1");
			}else if("5".equals(race)){
				docVO.put("ETHNICITY", "01");
				docVO.put("RACE_UNKNOWN", "1");
			}else if("9".equals(race)){
				docVO.put("ETHNICITY", "09");
				docVO.put("RACE_OTHER", "1");
			}else if("0".equals(race)){
				docVO.put("ETHNICITY", "09");
				docVO.put("RACE_UNKNOWN", "1");
			}
			
		}
		
		docVO.put("DOB_TEXT", (String)docVO.get("DOB"));
		docVO.put("SSN", CommonUtil.formatSSNValue((String)docVO.get("SSN")));
		docVO.put("FNAME",CommonUtil.NameValidation((String)docVO.get("FNAME")));
		docVO.put("LNAME",CommonUtil.NameValidation((String)docVO.get("LNAME")));
		docVO.put("MNAME",CommonUtil.NameValidation((String)docVO.get("MNAME")));
		
		docVO.put("PERSON_ZIP", CommonUtil.zipValidation((String)docVO.get("ZIP")));
		
		
		docVO.put("PERSON_COUNTY", (String)docVO.get("COUNTY_FIPS"));
		
		docVO.put("MEDICAID_NUMBER", CommonUtil.checkMedicaidNumberFormat((String)docVO.get("MEDICAID_NUMBER")));		
		
		
		docVO.put("CURRENT_PROCESS_STATUS", 5);
		
		
		return  casaDocumentDAO.writeStdDocData(docVO);
		
		
	}
	
	
	@Override
	public PersonViewVO getStdCASADocumentForClericalReview(int documentId) {

		PersonViewVO casaDocVO = casaDocumentDAO.getStdCASADocumentForClericalReview(documentId);

		return casaDocVO;
	}
	
	

}
