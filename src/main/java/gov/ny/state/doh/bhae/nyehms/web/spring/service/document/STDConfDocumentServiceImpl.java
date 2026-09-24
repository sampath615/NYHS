package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.STDConfDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.STDConfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * This is implementation of the TBService interface. This provides
 * implementation for all the methods for TB document.
 *
 * @author vxp06
 */
@Service
public class STDConfDocumentServiceImpl implements STDConfDocumentService {

	//private static Logger log = LoggerFactory.getLogger(STDConfDocumentServiceImpl.class);
   // private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    @Autowired
    STDConfDocumentDAO stdConfDocumentDAO;
    @Autowired
    CommonExceptionHandler commonExceptionHandler;
    @Autowired
    CommonDocumentService commonDocumentService;
    
    @Autowired
    CommonUtil commonUtil;
    private Map<String, String> drugListMap = new HashMap<String, String>();
    
    @Override
    public STDConfDocVO getSTDConfDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id){
    	
    	return stdConfDocumentDAO.getSTDConfDoc(docId);
    }
   

	@Override
    public int writeRawSTDConfDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
    	
		docVO.put("ETHNICITY",LabCommonUtils.checkEthnicityData((String)docVO.get("ETHNICITY")));
    	docVO.put("BIRTH_SEX",LabCommonUtils.checkBirthSexData((String)docVO.get("BIRTH_SEX")));
		docVO.put("CURRENT_GENDER", LabCommonUtils.checkCurrentGenderData((String)docVO.get("CURRENT_GENDER")));
		
    	docVO.put("FNAME",CommonUtil.NameValidation((String)docVO.get("FNAME")));
    	docVO.put("LNAME",CommonUtil.NameValidation((String)docVO.get("LNAME")));
    	docVO.put("MNAME",CommonUtil.NameValidation((String)docVO.get("MNAME")));
    	
    	docVO.put("PERSON_ADDRESS",CommonUtil.addressValidation(CommonUtil.blankOutUnknown((String)docVO.get("PERSON_ADDRESS"))));
    	docVO.put("PERSON_CITY",CommonUtil.blankOutUnknown((String)docVO.get("PERSON_CITY")));
		if (CommonUtil.isNotNullAndEmpty((String)docVO.get("PERSON_STATE"))) {
	    	docVO.put("PERSON_STATE",commonDocumentService.validateState((String)docVO.get("PERSON_STATE"), null, null, null));
		}
    	docVO.put("PERSON_ZIP",CommonUtil.formatZipCode((String)docVO.get("PERSON_ZIP")));
    	
    	docVO.put("PROV_FNAME",CommonUtil.NameValidation(CommonUtil.blankOutUnknown((String)docVO.get("PROV_FNAME"))));
    	docVO.put("PROV_LNAME",CommonUtil.NameValidation(CommonUtil.blankOutUnknown((String)docVO.get("PROV_LNAME"))));
    	docVO.put("PROV_ADDRESS",CommonUtil.addressValidation(CommonUtil.blankOutUnknown(CommonUtil.removeAllCharAfterLastComma((String)docVO.get("PROV_ADDRESS")))));
    	docVO.put("PROV_CITY",CommonUtil.blankOutUnknown((String)docVO.get("PROV_CITY")));    	
		if (CommonUtil.isNotNullAndEmpty((String)docVO.get("PROV_ST"))) {
	    	docVO.put("PROV_STATE",commonDocumentService.validateState((String)docVO.get("PROV_ST"), null, null, null));
		}
    	docVO.put("PROV_ZIP",CommonUtil.formatZipCode((String)docVO.get("PROV_ZIP")));
    	
    	docVO.put("RACE_ASIAN",LabCommonUtils.checkRaceData((String)docVO.get("RACE_ASIAN")));
    	docVO.put("RACE_BLACK",LabCommonUtils.checkRaceData((String)docVO.get("RACE_BLACK")));
    	docVO.put("RACE_INDIAN",LabCommonUtils.checkRaceData((String)docVO.get("RACE_AMERIND")));
    	docVO.put("RACE_OTHER",LabCommonUtils.checkRaceData((String)docVO.get("RACE_OTHER")));
    	docVO.put("RACE_PACIFIC",LabCommonUtils.checkRaceData((String)docVO.get("RACE_PACIFIC")));
    	docVO.put("RACE_UNKNOWN",LabCommonUtils.checkRaceData((String)docVO.get("RACE_UNKNOWN")));
    	docVO.put("RACE_WHITE",LabCommonUtils.checkRaceData((String)docVO.get("RACE_WHITE")));
    	
    	docVO.put("RISK_SEX_MALE",LabCommonUtils.checkRiskData((String)docVO.get("RISK_SEX_MALE")));
    	docVO.put("RISK_SEX_FEMALE",LabCommonUtils.checkRiskData((String)docVO.get("RISK_SEX_FEMALE")));
    	docVO.put("RISK_SEX_TRANSGENDER",LabCommonUtils.checkRiskData((String)docVO.get("RISK_SEX_TRANSGENDER")));
    	docVO.put("RISK_SEX_ANONYMOUS",LabCommonUtils.checkRiskData((String)docVO.get("RISK_SEX_ANONYMOUS")));
    	docVO.put("RISK_SEX_IDU",LabCommonUtils.checkRiskData((String)docVO.get("RISK_SEX_IDU")));
    	docVO.put("RISK_SEX_DRUGS",LabCommonUtils.checkRiskData((String)docVO.get("RISK_SEX_DRUGS")));
    	docVO.put("RISK_SEX_EXCHANGED",LabCommonUtils.checkRiskData((String)docVO.get("RISK_SEX_EXCHANGED")));
    	docVO.put("RISK_SEX_MSM",LabCommonUtils.checkRiskData((String)docVO.get("RISK_SEX_MSM")));
    	docVO.put("RISK_DRUG_USED",LabCommonUtils.checkRiskData((String)docVO.get("RISK_DRUG_USED")));
    	docVO.put("RISK_DRUG_INCARCERATED",LabCommonUtils.checkRiskData((String)docVO.get("RISK_DRUG_INCARCERATED")));
    	docVO.put("RISK_SEX_WITHOUT_CONDOM",LabCommonUtils.checkRiskData((String)docVO.get("RISK_SEX_WITHOUT_CONDOM")));
    	docVO.put("RISK_DRUG_PARAPHENALIA",LabCommonUtils.checkRiskData((String)docVO.get("RISK_DRUG_PARAPHENALIA")));
    	docVO.put("RISK_IDU",LabCommonUtils.checkRiskData((String)docVO.get("RISK_IDU")));
    	
    	String otherRiskText = (String)docVO.get("RISK_OTHER_TEXT");
    	
    	if(CommonUtil.isNotNullAndEmpty(otherRiskText)){
    		docVO.put("RISK_OTHER","Y");
    		docVO.put("RISK_OTHER_TEXT",otherRiskText);
    	}else{
    		docVO.put("RISK_OTHER","N");
    	}
    	
//      STD group is concerned about the data quality of the drug used list submitted  -- 1/14/2019
    	String drugUsedList = (String)docVO.get("RISK_DRUG_USED_LIST");
    	
    	if(CommonUtil.isNotNullAndEmpty(drugUsedList)){
    		
    		String[] codedDrugValues = drugUsedList.split(",");
    		
    		for (String _drugCd : codedDrugValues) {
    			
    			switch (_drugCd) {
    			case "1":
    				docVO.put("RISK_DRUG_USED_CRACK","Y");
    				break;
    			case "2":
    				docVO.put("RISK_DRUG_USED_COCAINE","Y");
    				break;
    			case "3":
    				docVO.put("RISK_DRUG_USED_HEROIN","Y");
    				break;
    			case "4":
    				docVO.put("RISK_DRUG_USED_METHAMPHETAMINES","Y");
    				break;
    			case "5":
    				docVO.put("RISK_DRUG_USED_NITRATES_POPPERS","Y");
    				break;
    			case "6":
    				docVO.put("RISK_DRUG_USED_ED_MEDICATIONS","Y");
    				break;
    			case "7":
    				docVO.put("RISK_DRUG_USED_OTHER","Y");
    				break;
    			case "10":
    				docVO.put("RISK_DRUG_USED_MARIJUANA","Y");
    				break;
    			case "11":
    				docVO.put("RISK_DRUG_USED_PRESCRIPTION_PAINKILLERS","Y");
    				break;
    			case "12":
    				docVO.put("RISK_DRUG_USED_ALCOHOL","Y");
    				break;
    			case "13":
    				docVO.put("RISK_DRUG_USED_BENZODIAZEPINES","Y");
    				break;
    			case "14":
    				docVO.put("RISK_DRUG_USED_PRESCRIPTION_OPIODS","Y");
    				break;
    			default:
    				break;
    			}
    		}
    		
    	}
    	
    	docVO.put("CURRENT_PROCESS_STATUS",5);
    	
    	    	
    	return stdConfDocumentDAO.writeRawSTDConfDocDataToStdTable(docVO);
    }        

	@Override
	public PersonViewVO getStdConfDocumentForClericalReview(int documentId) {
		return stdConfDocumentDAO.getStdConfDocumentForClericalReview(documentId);
	}
	
	
	@Override
	public void loadSTDConfReferenceVariables() {
	    	List<Map<String, String>> drugList = stdConfDocumentDAO.getDrugList("DRUG_USED_LIST");
	    	
	    	for (Map<String, String> map : drugList) {
	    		drugListMap.put(map.get("LOOKUP_CD"), map.get("LOOKUP_NAME"));
			}
	}
	
	@Override
	public void resetDocumentReferenceVariables(){
		if(drugListMap != null && drugListMap.size() > 0){
			drugListMap.clear();
		}
	}
	
	@Override
	public List<Map<String, String>> getSTDConfRefData(){
		return stdConfDocumentDAO.getSTDConfRefData();
	}
	
	@Override
	public Map<String, List<String>> getStdConfLookupData(){
		List<Map<String, String>> stdRefConfData = stdConfDocumentDAO.getSTDConfRefData(); 
		
		Map<String, List<String>> stdRefConfDataMap = stdRefConfData.stream().collect(Collectors.groupingBy(
				m -> m.get("REF_COLUMN"), Collectors.mapping(m -> m.get("LOOKUP_CD"), Collectors.toList())));
	   

		
		return stdRefConfDataMap;
		
	}
}
