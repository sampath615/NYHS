package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MedicaidDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.InsDeathDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.InsDeathDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocProvidersVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;


@Service
public class MedicaidDocumentService {

	private static Logger log = LoggerFactory.getLogger(MedicaidDocumentService.class);
    private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    @Autowired
    MedicaidDocumentDAO medicaidDocumentDAO;
    @Autowired
    InsDeathDocRepository insDeathDocRepository;
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
    public MedicaidDocVO getMedicaidDoc(int docId, BindingResult result, UserObject userObject,
            String sessionID) {

        boolean rollbackFlag = false;
        MedicaidDocVO medicaidDocVO = null;
        log.debug(nyehmsLog + " fetching for docId : " + docId);
        try {
        	medicaidDocVO = medicaidDocumentDAO.getMedicaidDoc(docId);
        	
        	
        	if(medicaidDocVO != null){
        		
        		medicaidDocVO.setMedicaidDocArvsVO(medicaidDocumentDAO.getArvsForMedicaidDoc(docId));
        		
        		
        		List<MedicaidDocProvidersVO> medicaidProviders = medicaidDocumentDAO.getProvidersListForMedicaidDoc(docId);
        		
        		if(medicaidProviders == null || medicaidProviders.size() == 0) {
        			
        			medicaidProviders = new ArrayList<MedicaidDocProvidersVO>(); 
        			
        			medicaidProviders.add(new MedicaidDocProvidersVO());
        			
        		}
        		
        		medicaidDocVO.setMedicaidDocProvidersVO(medicaidProviders);
        		
        		
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
        return medicaidDocVO;
    }
    
    public int writeRawMedicaidRecordDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
    	
    	
    	
    	Map<Object, Object> stdDocData = new HashMap<>();
    	
    	
    	BigDecimal document_id = (BigDecimal)docVO.get("DOCUMENT_ID");
    	
    	stdDocData.put("DOCUMENT_ID",document_id);
    	stdDocData.put("MEDICAID_NUMBER",CommonUtil.checkMedicaidNumberFormat((String)docVO.get("CIN")));
    	stdDocData.put("FNAME",CommonUtil.NameValidation((String)docVO.get("FIRST_NAME")));
    	stdDocData.put("MNAME",CommonUtil.NameValidation((String)docVO.get("MI")));
    	stdDocData.put("LNAME",CommonUtil.NameValidation((String)docVO.get("LAST_NAME")));
    	stdDocData.put("DOB_TEXT",docVO.get("BIRTH_DT"));
    	stdDocData.put("SSN",CommonUtil.formatSSNValue((String)docVO.get("SSN")));
    	stdDocData.put("CURRENT_GENDER",docVO.get("SEX_CD"));
    	
    	stdDocData.put("CURRENT_PROCESS_STATUS", CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
    	String _raceCode = (String)docVO.get("RACE_CD");
    	
    	String _ethnicityCode =  (String)docVO.get("ETHNIC_CD");
    	stdDocData.put("ETHNICITY", LabCommonUtils.checkEthnicityData(_ethnicityCode));
    	
    	
    	
    	
    	stdDocData.put("RACE_UNKNOWN",0);
    	stdDocData.put("RACE_WHITE",0);
    	stdDocData.put("RACE_BLACK",0);
    	stdDocData.put("RACE_ASIAN",0);
    	stdDocData.put("RACE_INDIAN",0);
    	stdDocData.put("RACE_OTHER",0);

    	
    	
    	
    	if(CommonUtil.isNotNullAndEmpty(_raceCode)){
    		
    		try{
    			int race_cd = Integer.parseInt(_raceCode.trim());
    			
    			
    			switch (race_cd) {
					case 0 -> stdDocData.put("RACE_UNKNOWN",1);
					case 5 -> {
						stdDocData.put("RACE_UNKNOWN",1);
						stdDocData.put("ETHNICITY", LabCommonUtils.checkEthnicityData(_ethnicityCode));
					}
					case 1 -> stdDocData.put("RACE_WHITE",1);
					case 2 -> stdDocData.put("RACE_BLACK",1);
					case 3 -> stdDocData.put("RACE_ASIAN",1);
					case 4 -> stdDocData.put("RACE_INDIAN",1);
					case 9 -> stdDocData.put("RACE_OTHER",1);
					}
    		
    		}catch(NumberFormatException numberFormatException){
    			log.error("Unable to parse the Race code for the Insurance document  {}",docVO.get("DOCUMENT_ID"));
    		}
    	}
    	
    	
    	stdDocData.put("PERSON_ADDRESS",CommonUtil.addressValidation((String)docVO.get("MAIL_ADDR_STR")));
    	stdDocData.put("PERSON_CITY",docVO.get("MAIL_ADDR_CITY"));
    	//stdDocData.put("PERSON_STATE",docVO.get(""));
    	
    	stdDocData.put("PERSON_ZIP",CommonUtil.formatZipCode((String)docVO.get("POSTAL_CD")));
    	
    	//stdDocData.put("PERSON_COUNTY",docVO.get(""));
    	stdDocData.put("CRITERIA_1",docVO.get("CRITERIA_1"));
    	stdDocData.put("CRITERIA_2",docVO.get("CRITERIA_2"));
    	stdDocData.put("CRITERIA_3",docVO.get("CRITERIA_3"));
    	stdDocData.put("CRITERIA_4",docVO.get("CRITERIA_4"));
    	stdDocData.put("CRITERIA_5",docVO.get("CRITERIA_5"));
    	stdDocData.put("CRITERIA_6",docVO.get("CRITERIA_6"));
    	stdDocData.put("CRITERIA_7",docVO.get("CRITERIA_7"));
    	stdDocData.put("CRITERIA_8",docVO.get("CRITERIA_8"));
    	stdDocData.put("CRITERIA_9",docVO.get("CRITERIA_9"));
    	stdDocData.put("CRITERIA_10",docVO.get("CRITERIA_10"));
    	stdDocData.put("VITAL_STATUS",docVO.get("VITAL_STATUS"));
    	stdDocData.put("MEDICAID_FILE",docVO.get("MEDICAID_FILE"));
    	stdDocData.put("DOD_TEXT",docVO.get("DEATH_DATE"));
    	stdDocData.put("CREATE_UID",docVO.get("CREATE_UID"));
    	stdDocData.put("CREATE_DATE",docVO.get("CREATE_DATE"));
    	
    	
    	int resultCount =  medicaidDocumentDAO.writeStdDocData(stdDocData);
    	
    	if(resultCount == 0){
    		throw new DataRetrievalFailureException(document_id+" : Failed to store the RAW Insurance document data in the Standard Layer.");
    	}else{
    		medicaidDocumentDAO.storeRawMedicaidProvidersDataInStd(document_id.intValue());
    	}
    	
    	return resultCount;
    }

	public Map<Object, Object> getRawMedicaidDocAndProvidersData(int documentId){
		
    	return medicaidDocumentDAO.getRawMedicaidDocAndProvidersData(documentId);
	}

	public Map<Object, Object> getStdMedicaidDocAndProvidersData(int documentId){
		
		return medicaidDocumentDAO.getStdMedicaidDocAndProvidersData(documentId);
	}
	
	public InsDeathDocData getInsDthDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id){
		InsDeathDocData insDeathDocVO = insDeathDocRepository.findDocument(docId);
    	
    	return insDeathDocVO;
	}
}
