package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycIjrDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycijrDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * This is implementation of NycIJRDocumentService interface. This provides
 * implementation of the methods to interact with NYC_IJR document.
 *
 * @author vxp06
 */
@RequiredArgsConstructor
@Component
public class NycIJRDocumentService {

	private static final Logger log = LoggerFactory.getLogger(NycIJRDocumentService.class);
    //private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();


    private final NycijrDocRepository nycijrDocRepository;

    /**
     * This method will return NYC_IJR document for a given document id
     * {@inheritDoc}
     *
     * @param documentID
     * @return
     */
    public NycIjrDocData getNYCIJRDocument(int docId) {
        return nycijrDocRepository.findDocument(docId);
    }
    
    public int writeRawNycIJRDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
    	
		docVO.put("NYC_SENT_STATENO", (String)docVO.get("STATENO"));
		docVO.put("LNAME", CommonUtil.NameValidation((String)docVO.get("LNAME")));
		docVO.put("FNAME", CommonUtil.NameValidation((String)docVO.get("FNAME")));
		docVO.put("MNAME", CommonUtil.NameValidation((String)docVO.get("MNAME")));
		docVO.put("SSN", CommonUtil.formatSSNValue((String)docVO.get("SSN")));
		
		
		String DOB = (String)docVO.get("DOB");
		Timestamp CREATED_DATE = (Timestamp)docVO.get("CREATE_DATE");
		
		docVO.put("DOB_TEXT", DOB);
		
		if(CommonUtil.isNotNullAndEmpty(DOB) && CREATED_DATE != null){
			
			
			
			
			try {
			
			
			Calendar startCalendar = new GregorianCalendar();
			
			startCalendar.setTime(CREATED_DATE);
			
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	        
			java.util.Date dateOfBirthParsed = format.parse(DOB);
	        
			Calendar dobCalender = new GregorianCalendar();
			
			dobCalender.setTime(dateOfBirthParsed);
			
			
			 int diff = startCalendar.get(Calendar.YEAR) - dobCalender.get(Calendar.YEAR);
			    if (startCalendar.get(Calendar.MONTH) < dobCalender.get(Calendar.MONTH) || 
			        (startCalendar.get(Calendar.MONTH) == dobCalender.get(Calendar.MONTH) && startCalendar.get(Calendar.DATE) < dobCalender.get(Calendar.DATE))) {
			        diff--;
			    }
				        
		        docVO.put("AGE", diff);
			
			} catch (ParseException parseException) {
				log.error(parseException.getMessage());
				
				throw new DataRetrievalFailureException("Failed to calcualte the Age for : "+documentId);
			}
		}
		
		docVO.put("CURRENT_GENDER", LabCommonUtils.checkCurrentGenderData((String)docVO.get("GENDER")));
		docVO.put("ETHNICITY", LabCommonUtils.checkEthnicityData((String)docVO.get("ETHNICCD")));
		
		
		docVO.put("RACE_INDIAN", LabCommonUtils.checkRaceData((String)docVO.get("RACE_INDIAN")));
		docVO.put("RACE_BLACK", LabCommonUtils.checkRaceData((String)docVO.get("RACE_BLACK")));
		docVO.put("RACE_ASIAN", LabCommonUtils.checkRaceData((String)docVO.get("RACE_ASIAN")));
		docVO.put("RACE_PACIFIC", LabCommonUtils.checkRaceData((String)docVO.get("RACE_PACIFIC")));
		docVO.put("RACE_WHITE", LabCommonUtils.checkRaceData((String)docVO.get("RACE_WHITE")));
		docVO.put("RACE_MULTIPLE", LabCommonUtils.checkRaceData((String)docVO.get("RACE_MULTIPLE")));
		docVO.put("RACE_OTHER", LabCommonUtils.checkRaceData((String)docVO.get("RACE_OTHER")));
		docVO.put("RACE_UNKNOWN", LabCommonUtils.checkRaceData((String)docVO.get("RACE_UNKNOWN")));
		
		String RACE_CD = (String)docVO.get("RACE_CD");
		if(CommonUtil.isNotNullAndEmpty(RACE_CD)){
			
			for(String _raceValue : RACE_CD.split(",")){
			
				_raceValue = _raceValue.trim().toUpperCase();
				
				if("BLACK".equals(_raceValue)){
					docVO.put("RACE_BLACK", 1);
				}else if("WHITE".equals(_raceValue)){
					docVO.put("RACE_WHITE", 1);
				}else if("MULTI-RACE".equals(_raceValue)){
					docVO.put("RACE_MULTIPLE", 1);
				}else if("OTHER".equals(_raceValue)){
					docVO.put("RACE_OTHER", 1);
				}else if("AMER INDIA".equals(_raceValue)){
					docVO.put("RACE_INDIAN", 1);
				}else if("ASIAN".equals(_raceValue)){
					docVO.put("RACE_ASIAN", 1);
				}else if("HAWAIIAN/PACIFIC ISLANDER".equals(_raceValue) || _raceValue.contains("HAWAIIAN")){
					docVO.put("RACE_PACIFIC", 1);
				}else if("UNKNOWN".equals(_raceValue)){
					docVO.put("RACE_UNKNOWN", 1);
				}
			}
			
		}
		
		
		docVO.put("MED_RECORD_NUMBER", CommonUtil.normalizeString((String)docVO.get("MEDRECNO")));
		docVO.put("DIN", CommonUtil.dinValidation((String)docVO.get("DIN")));
		docVO.put("NYSID", CommonUtil.nysidValidation((String)docVO.get("NYSID")));
		docVO.put("DX_PERSON_ADDRESS", CommonUtil.addressValidation((String)docVO.get("ADDRESS")));
		docVO.put("DX_PERSON_CITY", (String)docVO.get("CITY"));
		docVO.put("DX_PERSON_STATE", (String)docVO.get("STATE"));
		docVO.put("DX_PERSON_ZIP", (String)docVO.get("ZIPCODE"));
		docVO.put("DX_PERSON_TELEPHONE", (String)docVO.get("TELEPHONE"));
		docVO.put("PERSON_CELLPHONE", (String)docVO.get("CELLPHONE"));
		docVO.put("PERSON_WORKPHONE", (String)docVO.get("WORKPHONE"));
		docVO.put("CUR_PERSON_ADDRESS", CommonUtil.addressValidation((String)docVO.get("CUR_STREET_ADDRESS1")));
		docVO.put("CUR_PERSON_CITY", (String)docVO.get("CUR_CITY_NAME"));
		docVO.put("CUR_PERSON_COUNTY_NAME", (String)docVO.get("CUR_COUNTY_NAME"));
		docVO.put("CUR_PERSON_STATE", (String)docVO.get("CUR_STATE_CD"));
		docVO.put("CUR_PERSON_ZIP", (String)docVO.get("CUR_ZIP_CD"));
		docVO.put("CUR_PERSON_PHONE", (String)docVO.get("CUR_PHONE"));
		docVO.put("PROV_ADDRESS", CommonUtil.addressValidation((String)docVO.get("PROV_ADDR")));
		docVO.put("PROV_STATE", (String)docVO.get("PROV_ST"));
		docVO.put("PROV_NAME", (String)docVO.get("PROVNAME"));
		docVO.put("ADMISSION_DATE_TEXT", (String)docVO.get("ADMISS_DT"));
		docVO.put("INIT_HIV_DIAG", LabCommonUtils.checkRaceData((String)docVO.get("I_ILL")));
		docVO.put("INIT_HIV_DIAG_DATE_TEXT", (String)docVO.get("I_DIAG_DT"));
		docVO.put("INIT_AIDS_DIAG", LabCommonUtils.checkRaceData((String)docVO.get("D_DIAG")));
		docVO.put("INIT_AIDS_DIAG_DATE_TEXT", (String)docVO.get("D_DIAG_DT"));
		docVO.put("INFORMED_HIV_POS", LabCommonUtils.checkRaceData((String)docVO.get("INFORMED_OF_DIAG")));
		docVO.put("INFORMED_HIV_DATE_TEXT", (String)docVO.get("INFORMED_OF_DIAG_DT"));
		docVO.put("INDX_INTERVIEWED_DATE_TEXT", (String)docVO.get("INDX_INTERVIEWED_DT"));
		docVO.put("INDX_INTERVIEWED", LabCommonUtils.checkRaceData((String)docVO.get("INDX_INTERVIEWED")));
		docVO.put("SWM", LabCommonUtils.checkRiskData((String)docVO.get("RISK01")));
		docVO.put("SWF", LabCommonUtils.checkRiskData((String)docVO.get("RISK02")));
		docVO.put("IDU", LabCommonUtils.checkRiskData((String)docVO.get("RISK03")));
		docVO.put("SEX_PARTNER_IDU", LabCommonUtils.checkRiskData((String)docVO.get("RISK04")));
		docVO.put("SEX_PARTNER_HIV", LabCommonUtils.checkRiskData((String)docVO.get("RISK05")));
		docVO.put("RECV_BLOOD_PROD", LabCommonUtils.checkRiskData((String)docVO.get("RISK06")));
		docVO.put("PERINATAL", LabCommonUtils.checkRiskData((String)docVO.get("RISK07")));
		
		
		String RISK_OTHER = (String)docVO.get("RISK08");
		
		docVO.put("RISK_OTHER_TEXT", (String)docVO.get("RISK08"));
		
		if(CommonUtil.isNotNullAndEmpty(RISK_OTHER)){
			docVO.put("RISK_OTHER", "Y");
		}
		
		docVO.put("RISK_UNKNOWN", LabCommonUtils.checkRiskData((String)docVO.get("RISK09")));
		docVO.put("SEX_PARTNER_MSM", LabCommonUtils.checkRiskData((String)docVO.get("RISK10")));
		docVO.put("RECV_BLOOD_PROD_DATE_TEXT", (String)docVO.get("DATE_RECV_BLD_PROD"));
		docVO.put("RECV_BLOOD_PROD_COUNTRY", (String)docVO.get("COUNTRY_RECV_BLD_PROD"));
		docVO.put("PROV_SIGNED_DATE", (String)docVO.get("SIG_DT"));
		docVO.put("PROV_SIGNED", (String)docVO.get("SIGNATURE"));
		docVO.put("COMPLETER_FNAME", CommonUtil.NameValidation((String)docVO.get("FNAME_COMPLETER")));
		docVO.put("COMPLETER_MI", CommonUtil.NameValidation((String)docVO.get("MI_COMPLETER")));
		docVO.put("COMPLETER_LNAME", CommonUtil.NameValidation((String)docVO.get("LNAME_COMPLETER")));
		docVO.put("COMPLETER_TITLE", (String)docVO.get("TITLE_COMPLETER"));
		docVO.put("COMPLETER_EMAIL", (String)docVO.get("EMAIL_COMPLETER"));
		docVO.put("COMPLETED_DATE", (String)docVO.get("DT_COMPLETED"));
		docVO.put("CONTACT_LNAME", CommonUtil.NameValidation((String)docVO.get("CONTACT_LNAME")));
		docVO.put("CONTACT_FNAME", CommonUtil.NameValidation((String)docVO.get("CONTACT_FNAME")));
		docVO.put("CONTACT_MNAME", CommonUtil.NameValidation((String)docVO.get("CONTACT_MNAME")));
		docVO.put("CONTACT_TITLE", (String)docVO.get("CONTACT_TITLE"));
		docVO.put("CONTACT_PHONE", (String)docVO.get("CONTACT_PHONE"));
		docVO.put("NUMBEROFPARTNERS", docVO.get("NUMBEROFPARTNERS"));
		docVO.put("PS_REQUESTED", LabCommonUtils.checkRaceData((String)docVO.get("PNAP_REQUESTED")));
		docVO.put("FILE_NAME", (String)docVO.get("FILE_NAME"));
		docVO.put("FILE_DATE", (String)docVO.get("FILE_DT"));
		docVO.put("RSH_PHONE", (String)docVO.get("RSH_PHONE"));
		docVO.put("HIV_DX_DATE_TEXT", (String)docVO.get("HIVDXDT"));
		docVO.put("AIDS_DX_DATE_TEXT", (String)docVO.get("AIDSDXDT"));
		docVO.put("HIV_FACILITY_NAME", (String)docVO.get("HF_NAME2"));
		docVO.put("NYC_IJR_SRC_ID", docVO.get("NYC_IJR_SRC_ID"));
		
		
		
		
		
		
		docVO.put("CURRENT_PROCESS_STATUS", 5);
		
		
    	nycijrDocRepository.saveAndFlush(NycIjrStdDocMapping.toStdDoc(docVO));
    	return 1;
    }
    
    public List<String> getFileNames(){
    	return nycijrDocRepository.findFileNamesToReview();
    }
    
    
}
