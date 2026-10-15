package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.SYPHDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.SYPHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

@Service
public class SYPHDocumentService {
	
	@Autowired
	SYPHDocumentDAO	syphDocumentDAO;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

	
	private static Logger log = LoggerFactory.getLogger(SYPHDocumentService.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	public SYPHDocVO getSYPHDoc(int docID, BindingResult result,
			UserObject userObject, String sessionID) {
		
		boolean rollbackFlag = false;
		SYPHDocVO stdSYPHDocVO = null;
		log.debug(nyehmsLog + " fetching for docID : " + docID);
		try {
			stdSYPHDocVO = syphDocumentDAO.getSYPHDoc(docID);
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

	
	public int writeRawSYPHDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
		
		// Person				
		docVO.put("FNAME",CommonUtil.NameValidation((String)docVO.get("FIRSTNAME")));
    	docVO.put("LNAME",CommonUtil.NameValidation((String)docVO.get("LASTNAME")));
    	docVO.put("MNAME",CommonUtil.NameValidation((String)docVO.get("MIDDLENAME")));
    	docVO.put("BIRTH_SEX",LabCommonUtils.checkBirthSexData((String)docVO.get("SEX")));
    	docVO.put("ETHNICITY",LabCommonUtils.checkEthnicityData((String)docVO.get("ETHNICITY_CD")));
    	docVO.put("PERSON_ADDRESS",CommonUtil.addressValidation((String)docVO.get("STREET")));
    	docVO.put("PERSON_CITY",docVO.get("LOCALITY"));
    	docVO.put("PERSON_ZIP",CommonUtil.zipValidation((String)docVO.get("ZIPCODE")));
      	
        if(docVO.get("PHONE") != null){    		
    		String person_phone = (String)docVO.get("PHONE");    		
    		person_phone = person_phone.replaceAll("[^\\d]", "");
    		if(person_phone.length() == 7){
    			String PERSON_AREA_CD = (String)docVO.get("AREA_CD"); 
    			if(CommonUtil.isNotNullAndEmpty(PERSON_AREA_CD)){
    				PERSON_AREA_CD = PERSON_AREA_CD.replaceAll("[^\\d]", "");
    				docVO.put("PERSON_PHONE",PERSON_AREA_CD+person_phone);
    			}
    		}else if(person_phone.length() == 10){
    			docVO.put("PERSON_PHONE",person_phone);
    		}    		
    	}
    	
        docVO.put("CASEKEY",docVO.get("CASEKEY"));
        docVO.put("DISEASE_CODE",docVO.get("DISEASE_CD"));
		docVO.put("SYPHILIS_DIAGNOSIS_DATE",docVO.get("DIAGNOSIS_DATE"));
        docVO.put("DOB",docVO.get("DOB_DATE"));
        docVO.put("DOD",docVO.get("DEATH_DATE"));  	
    	
    	
    	//  Provider    	
        String providerFname = CommonUtil.NameValidation((String)docVO.get("PROVIDERFNAME"));    	
    	if(CommonUtil.isNotNullAndEmpty(providerFname)  && !"UNKNOWN".equals(providerFname.trim().toUpperCase())){    		
    		docVO.put("PROV_FNAME", providerFname);    		
    	}else{
    		docVO.put("PROV_FNAME", null);
    	}
    	
    	String providerLname = CommonUtil.NameValidation((String)docVO.get("PROVIDERLNAME"));    	
    	if(CommonUtil.isNotNullAndEmpty(providerLname)  && !"UNKNOWN".equals(providerLname.trim().toUpperCase())){    		
    		docVO.put("PROV_LNAME", providerLname);    		
    	}else{
    		docVO.put("PROV_LNAME", null);
    	}
    	
    	docVO.put("PROV_ADDRESS",CommonUtil.addressValidation((String)docVO.get("PROVIDERADDRESS")));
    	docVO.put("PROV_CITY",docVO.get("PROVIDERCITY"));
    	docVO.put("PROV_STATE",docVO.get("PROVIDERSTATE"));    	
    	   	
        String providerZipCode = (String)docVO.get("PROVIDERZIPCODE");
    	if(CommonUtil.isNotNullAndEmpty(providerZipCode)){    		
    		providerZipCode = providerZipCode.replaceAll("[^\\d]", "");
    		docVO.put("PROV_ZIP", providerZipCode);
    	}
    	
    	docVO.put("PROV_PHONE",docVO.get("PROVIDERPHONE"));
    	
    	
		docVO.put("RACE_INDIAN", "0");
		docVO.put("RACE_BLACK", "0");
		docVO.put("RACE_ASIAN", "0");
		docVO.put("RACE_PACIFIC", "0");
		docVO.put("RACE_WHITE", "0");
		docVO.put("RACE_MULTIPLE", "0");
		docVO.put("RACE_OTHER", "0");
		docVO.put("RACE_UNKNOWN", "0");
		
		if(CommonUtil.isNotNullAndEmpty((String)docVO.get("RACE_CD"))){
			String race = ((String)docVO.get("RACE_CD")).trim();
			
			if("4".equals(race)){				
				docVO.put("RACE_ASIAN", "1");
			}else if("2".equals(race)){				
				docVO.put("RACE_BLACK", "1");
			}else if("3".equals(race)){
				docVO.put("RACE_INDIAN", "1");				
			}else if("1".equals(race)){				
				docVO.put("RACE_WHITE", "1");
			}else if("5".equals(race)){				
				docVO.put("RACE_OTHER", "1");
			}else if("9".equals(race)){				
				docVO.put("RACE_UNKNOWN", "1");
			}	
		}
			
			
		docVO.put("CURRENT_PROCESS_STATUS", 5);
		
		return  syphDocumentDAO.writeRawSYPHDocDataToStdTable(docVO);
			
	}
	
	
	

}
