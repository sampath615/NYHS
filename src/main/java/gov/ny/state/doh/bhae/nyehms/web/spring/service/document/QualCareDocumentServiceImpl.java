package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.QualCareDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.QualCareDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

@Service
public class QualCareDocumentServiceImpl implements QualCareDocumentService {
	
	@Autowired private QualCareDocumentDAO qualCareDocumentDAO;
	
	//private static Logger log = LoggerFactory.getLogger(QualCareDocumentServiceImpl.class);
	//private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();

	
	@Override
	public int writeRawQualCareDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {

		docVO.put("FNAME", CommonUtil.NameValidation((String)docVO.get("FIRST_NAME")));
		docVO.put("LNAME", CommonUtil.NameValidation((String)docVO.get("LAST_NAME")));
		docVO.put("MNAME", CommonUtil.NameValidation((String)docVO.get("MIDDLE_INITIAL")));
		
		docVO.put("BIRTH_SEX", LabCommonUtils.checkBirthSexData((String)docVO.get("BIRTH_SEX")));
		docVO.put("GENDER", LabCommonUtils.checkCurrentGenderData((String)docVO.get("GENDER")));
		
		
		String race = (String)docVO.get("RACE");
		
		if(CommonUtil.isNotNullAndEmpty(race)) {
			
			String[] raceArray = race.split(",");
			
			for (int index =0; index < raceArray.length; index++) {
				
				String _race = raceArray[index].trim().toUpperCase();
				
				if("W".equals(_race)){
					docVO.put("RACE_WHITE", "1");
				}else if("B".equals(_race)){
					docVO.put("RACE_BLACK", "1");
				}else if("A".equals(_race)){
					docVO.put("RACE_ASIAN", "1");
				}else if("NHPI".equals(_race)){
					docVO.put("RACE_PACIFIC", "1");
				}else if("AIAN".equals(_race)){
					docVO.put("RACE_INDIAN", "1");
				}else if("UK".equals(_race)){
					docVO.put("RACE_UNKNOWN", "1");
				} 
			}
		}
		docVO.put("ETHNICITY", LabCommonUtils.checkEthnicityData((String)docVO.get("ETHNICITY")));
		docVO.put("MED_RECORD_NUMBER", CommonUtil.normalizeString((String)docVO.get("MRN")));
		docVO.put("FACILITY_ADDRESS", (String)docVO.get("STREET"));
		docVO.put("FACILITY_CITY", (String)docVO.get("CITY"));
		docVO.put("FACILITY_ZIP", (String)docVO.get("ZIP"));
		docVO.put("HOUSING_STATUS", (String)docVO.get("HOUSING"));
		docVO.put("ORGANIZATION_NAME", (String)docVO.get("ORG_NAME"));
		docVO.put("FACILITY_NAME", (String)docVO.get("CLINIC_NAME"));
		docVO.put("RECV_BLOOD_PROD", LabCommonUtils.checkRiskData((String)docVO.get("BLOOD_RISK")));
		docVO.put("RECV_CLOTTING_FACTOR", LabCommonUtils.checkRiskData((String)docVO.get("HEMO_RISK")));
		docVO.put("SEX_PARTNER_HIV", LabCommonUtils.checkRiskData((String)docVO.get("HETERO_RISK")));
		docVO.put("IDU", LabCommonUtils.checkRiskData((String)docVO.get("IDU_RISK")));
		docVO.put("RISK_MSM", LabCommonUtils.checkRiskData((String)docVO.get("MSM_RISK")));
		docVO.put("PERINATAL", LabCommonUtils.checkRiskData((String)docVO.get("PERI_RISK")));
		docVO.put("RISK_OTHER", LabCommonUtils.checkRiskData((String)docVO.get("OTHER_RISK")));
		docVO.put("RISK_UNKNOWN", LabCommonUtils.checkRiskData((String)docVO.get("UNKNOWN_RISK")));
		
		String serviceLine = (String)docVO.get("SERVICE_LINE");
	    
		if(CommonUtil.isNotNullAndEmpty(serviceLine)) {
			docVO.put("SERVICE_LINE", serviceLine.replaceAll("\\s+", ""));
		}
				
		
		
		docVO.put("OTHER_SERVICE", (String)docVO.get("OTHER_SERVICE"));
		
		docVO.put("CURRENT_PROCESS_STATUS", 5);
		
    	return qualCareDocumentDAO.writeStdDocData(docVO);
	}


	@Override
	public QualCareDocVO getQualCareDocument(int documentId) {
		
		QualCareDocVO qualCareDocVO = qualCareDocumentDAO.getQualCareDocument(documentId);
		
		return qualCareDocVO;
	}

	

}
