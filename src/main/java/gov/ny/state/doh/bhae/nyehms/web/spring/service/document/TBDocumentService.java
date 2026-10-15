package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.TBDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TBDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
public class TBDocumentService {

	private static Logger log = LoggerFactory.getLogger(TBDocumentService.class);
    private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    @Autowired
    TBDocumentDAO tbDocumentDAO;
    @Autowired
    CommonExceptionHandler commonExceptionHandler;
    
    private final static Map<String, String> stateCodeMap = new HashMap<>();
    
    static{
    	
    	stateCodeMap.put("1","NY");
    	stateCodeMap.put("8","CT");
    	stateCodeMap.put("6","CA");
    	stateCodeMap.put("35","NJ");
    	stateCodeMap.put("55","VA");
    	stateCodeMap.put("51","TX");
    	stateCodeMap.put("41","OH");
    	stateCodeMap.put("26","MA");
    	stateCodeMap.put("4","AZ");
    	stateCodeMap.put("12","FL");
    	stateCodeMap.put("13","GA");
    	
    }

   /**
     * This function will return the TB document for a given docId
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public TBDocVO getTBDoc(int docId, BindingResult result, UserObject userObject,
            String sessionID) {

        boolean rollbackFlag = false;
        TBDocVO tBDocVO = null;
        log.debug(nyehmsLog + " fetching for docId : " + docId);
        try {
            tBDocVO = tbDocumentDAO.getTBDoc(docId);
            
            if(tBDocVO == null){
            	tBDocVO = new TBDocVO();
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
        return tBDocVO;
    }
    
    public int writeRawTBDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
    	
    	
    	
    	
    	docVO.put("FNAME",CommonUtil.NameValidation((String)docVO.get("FIRSTNAME")));
    	docVO.put("LNAME",CommonUtil.NameValidation((String)docVO.get("LASTNAME")));
    	docVO.put("MNAME",CommonUtil.NameValidation((String)docVO.get("MI")));
    	

    	docVO.put("BIRTH_SEX",LabCommonUtils.checkBirthSexData((String)docVO.get("SEX")));
    	docVO.put("ETHNICITY",LabCommonUtils.checkEthnicityData((String)docVO.get("ETHNICITY_CD")));
    	
    	docVO.put("DOB_TEXT",docVO.get("DOB_DATE"));
    	
    	
    	
    	
    	docVO.put("RACE_ASIAN",LabCommonUtils.checkRaceData((String)docVO.get("RACE_ASIAN")));
    	docVO.put("RACE_BLACK",LabCommonUtils.checkRaceData((String)docVO.get("RACE_BLACK")));
    	docVO.put("RACE_INDIAN",LabCommonUtils.checkRaceData((String)docVO.get("RACE_AMERIND")));
    	//docVO.put("RACE_MULTIPLE",LabCommonUtils.checkRaceData((String)docVO.get("RACE_MULTIPLE")));
    	docVO.put("RACE_OTHER",LabCommonUtils.checkRaceData((String)docVO.get("RACE_OTHER")));
    	docVO.put("RACE_PACIFIC",LabCommonUtils.checkRaceData((String)docVO.get("RACE_PACIFIC")));
    	docVO.put("RACE_UNKNOWN",LabCommonUtils.checkRaceData((String)docVO.get("RACE_UNKNOWN")));
    	docVO.put("RACE_WHITE",LabCommonUtils.checkRaceData((String)docVO.get("RACE_WHITE")));
    	
    	
    	
    	docVO.put("PERSON_ADDRESS",CommonUtil.addressValidation((String)docVO.get("STREET")));
    	docVO.put("PERSON_CITY",docVO.get("LOCALITY"));
    	docVO.put("PERSON_STATE",docVO.get("STATE"));
    	docVO.put("PERSON_PLUS4",docVO.get("PLUS4"));
    	docVO.put("PERSON_COUNTY",docVO.get("COUNTY"));
    	
    	docVO.put("ATYPICAL_MYCO",docVO.get("ATYPICAL"));
    	docVO.put("DISEASE_TYPE",docVO.get("DISTYPE"));
    	docVO.put("DX_DATE",docVO.get("DIAGNOSIS_DATE"));
    	
    	
    	
    	
    	docVO.put("FACILITY_ADDRESS",CommonUtil.addressValidation((String)docVO.get("FACILITY_ADDRESS")));
    	
    	
    	String providerAddress = (String)docVO.get("PROVIDERADDRESS");
    	
    	
    	docVO.put("PROVIDER_CITY",docVO.get("PROVIDERCITY"));
    	docVO.put("PROVIDER_PHONE",docVO.get("PROVIDERPHONE"));
    	
    	docVO.put("VITAL_STATUS_AT_DIAGNOSIS",docVO.get("STATUSATDIAGNOSIS"));
    	
    	docVO.put("HIV_IND",docVO.get("CLINICALMANAGEA"));
    	
    	docVO.put("CASE_STATUS",docVO.get("CASESTATUS"));
    	
    	
    	
    	
    	if(CommonUtil.isNotNullAndEmpty(providerAddress)){
    		
    		
    		String rawProviderAddress = CommonUtil.addressValidation(providerAddress.split(",")[0]);
    		
    		docVO.put("PROVIDER_ADDRESS",rawProviderAddress);
    		docVO.put("RAW_PROVIDER_ADDRESS",rawProviderAddress);
    		
    	}
    	
    	
    	
    	String providerFname = CommonUtil.NameValidation((String)docVO.get("PROVIDERFNAME"));
    	
    	if(CommonUtil.isNotNullAndEmpty(providerFname)  && !"UNKNOWN".equals(providerFname.trim().toUpperCase())){
    		
    		docVO.put("PROVIDER_FNAME", providerFname);
    		
    	}else{
    		docVO.put("PROVIDER_FNAME", null);
    	}
    	
    	String providerLname = CommonUtil.NameValidation((String)docVO.get("PROVIDERLNAME"));
    	
    	if(CommonUtil.isNotNullAndEmpty(providerLname)  && !"UNKNOWN".equals(providerLname.trim().toUpperCase())){
    		
    		docVO.put("PROVIDER_LNAME", providerLname);
    		
    	}else{
    		docVO.put("PROVIDER_LNAME", null);
    	}
    	
    	docVO.put("DOD_TEXT",(String)docVO.get("DEATH_DATE"));
    	
    	    	
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
    	
    	docVO.put("PERSON_ZIP",CommonUtil.zipValidation((String)docVO.get("ZIPCODE")));
    	docVO.put("FACILITY_ZIP",CommonUtil.zipValidation((String)docVO.get("FACILITY_ZIP")));
    	
    	String providerZipCode = (String)docVO.get("PROVIDERZIPCODE");
    	
    	if(CommonUtil.isNotNullAndEmpty(providerZipCode)){
    		
    		providerZipCode = providerZipCode.replaceAll("[^\\d]", "");
    		
    		if(providerZipCode.length() > 5){
    			docVO.put("PROVIDER_ZIP", providerZipCode.substring(0, 5));
    			docVO.put("PROVIDER_PLUS4", providerZipCode.substring(5));
    		}else{
    			docVO.put("PROVIDER_ZIP", providerZipCode);
    		}
    		
    	}

    	String provider_stateCode = (String)docVO.get("PROVIDERSTATECODE");
    	
    	if(CommonUtil.isNotNullAndEmpty(provider_stateCode)){
    		docVO.put("PROVIDER_STATE", stateCodeMap.get(provider_stateCode.trim()));
    	}
    	
    	docVO.put("DIN",CommonUtil.dinValidation((String)docVO.get("DIN")));
    	docVO.put("CURRENT_PROCESS_STATUS",5);
    	

    	return tbDocumentDAO.writeRawTBDocDataToStdTable(docVO);
    }

}
