package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DoccsDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DinDoccsDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DoccsDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


@Service
public class DoccsDocumentService {
	
	private static Logger log = LoggerFactory.getLogger(DoccsDocumentService.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();	
	
	@Autowired
	DoccsDocumentDAO doccsDocumentDAO;	
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;		
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

    @Autowired
    CommonService commonService;
	
	private Map<String, String> countyCodeReferenceMap = new HashMap<>();



	public DoccsDocVO getDoccsDoc(int documentId, 
										BindingResult result, 
										UserObject userObject, 
										String sessionID) {
		boolean rollbackFlag=false;
		DoccsDocVO stdDoccsDocVO=null;
		log.debug("{} fetching for docId : {}", nyehmsLog,documentId);
		try{
			
			
			stdDoccsDocVO=doccsDocumentDAO.getDoccsDoc(documentId);
			
			if(stdDoccsDocVO != null){
				
				Map<String, Object> inputParemeterMap = new HashMap<>();
				
				inputParemeterMap.put("byDoccsDocId", true);
				inputParemeterMap.put("DoccsDocId", documentId);
				
				List<DinDoccsDocVO> dinDoccs = doccsDocumentDAO.getDinDoccsDocContent(inputParemeterMap);
				
				stdDoccsDocVO.setDinDoccsDocVO(dinDoccs);
				
			}
			
			
		} catch (DataAccessException e) {
			rollbackFlag=true;
			log.error(nyehmsLog+"  "+e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			rollbackFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(rollbackFlag)
				commonExceptionHandler.populateBindObjTrsnFail(result);
		}		
		
		return stdDoccsDocVO;
	}
	
	public DinDoccsDocVO getDinDoccsDocContent(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id){
		
		
		Map<String, Object> inputParemeterMap = new HashMap<>();
		
		inputParemeterMap.put("byDoccsDocId", false);
		inputParemeterMap.put("docId", docId);
		
		List<DinDoccsDocVO> dinDoccs = doccsDocumentDAO.getDinDoccsDocContent(inputParemeterMap);
		
		if(dinDoccs != null && dinDoccs.size() > 0 && dinDoccs.get(0) != null){
			return dinDoccs.get(0);
		}else{
			return null;
		}
		
		
		
	}
	
	
	
	public int getDoccsDocumentId(int docId){
		return doccsDocumentDAO.getDoccsDocumentId(docId);
	}

	public int writeRawDoccsAndDinDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
		
		return  documentTransactionHelper.writeRawDoccsAndDinDocDataToStdTable(docVO, documentId);
		
		
	}
	
	public int insertRawDoccsDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
		
		docVO.put("NYSID", CommonUtil.nysidValidation((String)docVO.get("NYSID")));
		docVO.put("CURRENT_PROCESS_STATUS", 5);
		
		return doccsDocumentDAO.insertRawDoccsDataToStdTable(docVO);
		
		
	}
	
	
	public List<Map<String, Object>> getRawDinDoccsList(BigDecimal documentId){
		return doccsDocumentDAO.getRawDinDoccsList(documentId);
	}
	
	
	public void updateRawDinDocToStandardFormat(Map<String, Object> dinDoc){
		
		dinDoc.put("NYSID", CommonUtil.nysidValidation((String)dinDoc.get("NYSID")));
		dinDoc.put("DIN", CommonUtil.dinValidation((String)dinDoc.get("DIN")));
		dinDoc.put("FNAME", CommonUtil.NameValidation((String)dinDoc.get("FNAME")));
		dinDoc.put("LNAME", CommonUtil.NameValidation((String)dinDoc.get("LNAME")));
		dinDoc.put("MNAME", CommonUtil.NameValidation((String)dinDoc.get("MNAME")));
		dinDoc.put("SUFFIX", CommonUtil.NameValidation((String)dinDoc.get("SUFFIX")));
		dinDoc.put("DOB_TEXT", dinDoc.get("BIRTH"));
		dinDoc.put("SSN", CommonUtil.formatSSNValue((String)dinDoc.get("SSN")));
		dinDoc.put("MEDICAID_NUMBER", CommonUtil.checkMedicaidNumberFormat((String)dinDoc.get("CIN")));
		dinDoc.put("CURRENT_GENDER", LabCommonUtils.checkCurrentGenderData((String)dinDoc.get("SEX")));
		
		
		
		dinDoc.put("RACE_INDIAN", 0 );
		dinDoc.put("RACE_BLACK", 0 );
		dinDoc.put("RACE_ASIAN", 0 );
		dinDoc.put("RACE_PACIFIC", 0 );
		dinDoc.put("RACE_WHITE", 0 );
		dinDoc.put("RACE_MULTIPLE", 0 );
		dinDoc.put("RACE_OTHER", 0 );
		dinDoc.put("RACE_UNKNOWN", 0 );
		
		String RACE_CD = (String)dinDoc.get("RACE");
		
		dinDoc.put("RACE_CD", null);
		
		
		if(dinDoc.get("RACE") != null){
			
			String race = (RACE_CD).trim().toUpperCase();
			
			if("NATIVE AM.".equals(race) || "NATIVE AMERICAN".equals(race)){
				dinDoc.put("RACE_INDIAN", 1 );
			}else if("WHITE".equals(race)){
				dinDoc.put("RACE_WHITE", 1 );
			}else if("BLACK".equals(race)){
				dinDoc.put("RACE_BLACK", 1 );
			}else if("ASIAN".equals(race)){
				dinDoc.put("RACE_ASIAN", 1 );
			}else if("OTHER".equals(race)){
				dinDoc.put("RACE_OTHER", 1 );
			}else if("UNKNOWN".equals(race)){
				dinDoc.put("RACE_UNKNOWN", 1 );
			}
		}
		
		
		String ethnicity =LabCommonUtils.checkEthnicityData((String)dinDoc.get("ETHNICITY"));
		
		if(CommonUtil.isNullOrEmpty(ethnicity)){
			dinDoc.put("ETHNICITY", "09" );
		}else{
			dinDoc.put("ETHNICITY", ethnicity );
		}
		
		if(CommonUtil.isNotNullAndEmpty((String)dinDoc.get("POB")))
		dinDoc.put("BIRTH_COUNTRY", ((String)dinDoc.get("POB")).trim().toUpperCase());
		
		
		if(dinDoc.get("ALIENS2") != null){
			
			String ALIENS2 = ((String)dinDoc.get("ALIENS2")).trim();
			
			if("NAT''LIZED".equals(ALIENS2) || "NATURALIZED".equals(ALIENS2)){
				dinDoc.put("ALIEN_STATUS", "NATURALIZED");
			}else if("NOT YET KNOWN".equals(ALIENS2) || "UNKNOWN".equals(ALIENS2) || "NOT STATED".equals(ALIENS2)){
				dinDoc.put("ALIEN_STATUS", "UNKNOWN");
			}else if("RES. ALIEN".equals(ALIENS2)){
				dinDoc.put("ALIEN_STATUS", "RESIDENT ALIEN");
			}else if("RESIDENT".equals(ALIENS2)){
				dinDoc.put("ALIEN_STATUS", "RESIDENT");
			}else if("FOREIGN CITIZEN".equals(ALIENS2) || "FOR. CIT.".equals(ALIENS2)){
				dinDoc.put("ALIEN_STATUS", "FOREIGN CITIZEN");
			}else if("NAT. (MIL)".equals(ALIENS2)){
				dinDoc.put("ALIEN_STATUS", "NATURALIZED MILITARY");
			}else if("ILLEGAL".equals(ALIENS2) || "ILLG ALIEN".equals(ALIENS2)){
				dinDoc.put("ALIEN_STATUS", "ILLEGAL");
			}else if("MARIEL CUB".equals(ALIENS2) || "INS MARIEL".equals(ALIENS2)){
				dinDoc.put("ALIEN_STATUS", "CUBAN IMMIGRANT");
			}
		}
		
		
		dinDoc.put("ENTERED_US_DATE_TEXT", dinDoc.get("USDATE"));
		
		dinDoc.put("PERSON_ADDRESS", CommonUtil.addressValidation((String)dinDoc.get("STREET")));
		dinDoc.put("PERSON_CITY", dinDoc.get("CITY"));
		dinDoc.put("PERSON_STATE", dinDoc.get("STATE"));
		
		dinDoc.put("PERSON_ZIP", dinDoc.get("ZIP"));
		
		
		
		String zip = (String)dinDoc.get("ZIP9");
		
		if(CommonUtil.isNotNullAndEmpty(zip)){
			zip = zip.trim();
			
			int length = zip.length();
			
			if(length >= 9){
				
				dinDoc.put("PERSON_ZIP", CommonUtil.zipValidation(zip.substring(0, 5)));
				dinDoc.put("PERSON_PLUS4", zip.substring(5, 9));
				
			}else if(length >= 5){
				dinDoc.put("PERSON_ZIP", zip.substring(0, 5));
			}
		}
		
		
		

		dinDoc.put("OWNING_FACILITY_NAME", dinDoc.get("OWNING_FACILITY"));
		dinDoc.put("CURRENT_FACILITY_NAME", dinDoc.get("CURRENT_FACILITY"));
		
		dinDoc.put("UNDER_CUSTODY", LabCommonUtils.checkTestDataValues((String)dinDoc.get("UC")));
		dinDoc.put("UNDER_SUPERVISION", LabCommonUtils.checkTestDataValues((String)dinDoc.get("SRP")));
		dinDoc.put("ADMIN_DATE_TEXT", dinDoc.get("ADMDATE"));
		dinDoc.put("LATEST_ADMIN_DATE_TEXT", dinDoc.get("LADMDATE"));
		dinDoc.put("EARLIEST_RELEASE_DATE_TEXT", dinDoc.get("ERDATE"));
		dinDoc.put("LATEST_RELEASE_DATE_TEXT", dinDoc.get("LRELDA"));
		dinDoc.put("LATEST_DISCHARGE_DATE_TEXT", dinDoc.get("LDISCHRGDATE"));
		dinDoc.put("DOD_TEXT", dinDoc.get("DEATH"));
		
		dinDoc.put("LEGAL_PERSON_ADDRESS", dinDoc.get("LSTREET"));
		dinDoc.put("LEGAL_PERSON_CITY", dinDoc.get("LCITY"));
		dinDoc.put("LEGAL_PERSON_STATE", dinDoc.get("LSTATE"));
		dinDoc.put("LEGAL_PERSON_ZIP", dinDoc.get("LZIP"));
		
		String lzip = (String)dinDoc.get("LZIP9");
		
		if(CommonUtil.isNotNullAndEmpty(lzip)){
			lzip = lzip.trim();
			
			int length = lzip.length();
			
			if(length >= 9){
				
				dinDoc.put("LEGAL_PERSON_ZIP", CommonUtil.zipValidation(lzip.substring(0, 5)));
				dinDoc.put("LEGAL_PERSON_PLUS4", lzip.substring(5, 9));
				
			}else if(length >= 5){
				dinDoc.put("LEGAL_PERSON_ZIP", lzip.substring(0, 5));
			}
		}
		
		
		
		dinDoc.put("LEGAL_PERSON_COUNTY_NAME", dinDoc.get("LEGRESC"));
		
		
		
		dinDoc.put("PREV_PERSON_ADDRESS", dinDoc.get("PREV_STREET"));
		dinDoc.put("PREV_PERSON_CITY", dinDoc.get("PREV_CITY"));
		dinDoc.put("PREV_PERSON_STATE", dinDoc.get("PREV_STATE"));
		
		
		String pzip = (String)dinDoc.get("PREV_ZIP9");
		
		if(CommonUtil.isNotNullAndEmpty(pzip)){
			pzip = pzip.trim();
			
			int length = pzip.length();
			
			if(length >= 9){
				
				dinDoc.put("PREV_PERSON_ZIP", CommonUtil.zipValidation(pzip.substring(0, 5)));
				dinDoc.put("PREV_PERSON_PLUS4", pzip.substring(5, 9));
				
			}else if(length >= 5){
				dinDoc.put("PREV_PERSON_ZIP", pzip.substring(0, 5));
			}
		}
		
		
		
		
		
		dinDoc.put("PREV_PERSON_CITY", dinDoc.get("PREV_CITY"));
		dinDoc.put("PREV_PERSON_CITY", dinDoc.get("PREV_CITY"));
		
		dinDoc.put("LRELDA", dinDoc.get("LRELDATE"));
		dinDoc.put("CURRENT_PROCESS_STATUS", 5);
		
		
		dinDoc.put("DRUG1", checkDrugValue((String)dinDoc.get("DRUG1")));
		dinDoc.put("DRUG2", checkDrugValue((String)dinDoc.get("DRUG2")));
		dinDoc.put("DRUG3", checkDrugValue((String)dinDoc.get("DRUG3")));
		
		if(CommonUtil.isNotNullAndEmpty((String)dinDoc.get("PRLRELCNTY"))) {
			dinDoc.put("PAROLE_RELEASE_COUNTY", countyCodeReferenceMap.get((String)dinDoc.get("PRLRELCNTY")));
		}
	}
	
	private String checkDrugValue(String _drugValue){
		if(CommonUtil.isNotNullAndEmpty(_drugValue)){
			
			_drugValue = _drugValue.trim().toUpperCase();
			if(!"MISSING DATA".equals(_drugValue)){
				
				return _drugValue;
			}
			
		}
		
		return null;
	}
	
	public int insertRawDinDoccsDataToStdTable(
			List<Map<String, Object>> rawDinDoccsList){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("dinDoccsList", rawDinDoccsList);
		
		return doccsDocumentDAO.insertRawDinDoccsDataToStdTable(inputParameterMap);
		
		
	}
	
	public void insertRawDinProbCodesToStdProbCodes(BigDecimal documentId){
		doccsDocumentDAO.insertRawDinProbCodesToStdProbCodes(documentId);
	}
	
	public PersonViewVO getStdDinDocumentForClericalReview(int documentId){
		
		PersonViewVO dinDoccsDocVO = doccsDocumentDAO.getStdDinDocumentForClericalReview(documentId);
    	
    	return dinDoccsDocVO;
	}
	
	public int documentInitialValidation(BigDecimal documentId){
		
		int resultCount = doccsDocumentDAO.documentInitialValidation(documentId);
		
		if(resultCount > 0){
			int doccsResultCount = doccsDocumentDAO.stopDoccsDocumentAtRaw(documentId);
			
			if(doccsResultCount > 0){
				return doccsResultCount;
			}
		}
		
		return 0;
		
		
	}
	
	public void updateRawDinDocStatus(BigDecimal documentId) {
		doccsDocumentDAO.updateRawDinDocStatus(documentId);
	}
	
	public void loadReferenceVariables() {
		countyCodeReferenceMap = commonService.getCountyCodeReferenceData();
	}
    
    public void resetDocumentReferenceVariables() {
    	countyCodeReferenceMap.clear();
    }
	
}
