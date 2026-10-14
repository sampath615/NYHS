package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.NycPRFDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycprfDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycprfPartnerRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.SexOrn;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class NycPRFDocumentService {

	@Autowired
	NycPRFDocumentDAO nycPRFDocumentDAO;
	@Autowired
	NycprfDocRepository nycprfDocRepository;
	@Autowired
	NycprfPartnerRepository nycprfPartnerRepository;
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;	
	
	
	
	public NycPrfDocData getNycPRFDoc(int docId, BindingResult result, UserObject userObject, String sessionID) {
		return nycprfDocRepository.findDocument(docId)
				.withPartnerList(nycprfPartnerRepository.findPartnersOfDocument(docId));
	}
	
	public int writeRawNycPRFRecordDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
    	Map<Object, Object> stdDocData = new HashMap<>();
    	
    	
    	stdDocData.put("DOCUMENT_ID",docVO.get("DOCUMENT_ID"));
    	stdDocData.put("PRF_SRC_ID",docVO.get("PRF"));
    	stdDocData.put("CITYNO",docVO.get("CITYNO"));
    	stdDocData.put("FNAME",CommonUtil.NameValidation((String)docVO.get("FNAME")));
    	stdDocData.put("MNAME",CommonUtil.NameValidation((String)docVO.get("MI")));
    	stdDocData.put("LNAME",CommonUtil.NameValidation((String)docVO.get("LNAME")));
    	stdDocData.put("ALT_NAME",docVO.get("ALIA"));
    	stdDocData.put("DOB",docVO.get("DOB"));
    	stdDocData.put("DOD_TEXT",docVO.get("DOD"));
    	stdDocData.put("PERSON_ADDRESS",CommonUtil.addressValidation((String)docVO.get("ADDRESS")));
    	stdDocData.put("PERSON_CITY",docVO.get("CITY"));
    	stdDocData.put("PERSON_STATE",docVO.get("ST"));
    	stdDocData.put("PERSON_ZIP",docVO.get("ZIP"));
    	stdDocData.put("PERSON_PHONE",docVO.get("PHONE"));
    	
    	
    	
    	//stdDocData.put("BIRTH_SEX",LabCommonUtils.checkBirthSexData((String)docVO.get("")));
    	stdDocData.put("CURRENT_GENDER",LabCommonUtils.checkCurrentGenderData((String)docVO.get("GENDER")));
    	
    	
    	
    	stdDocData.put("MED_RECORD_NUMBER",CommonUtil.normalizeString((String)docVO.get("RECNUM")));
    	stdDocData.put("ADMISSION_DATE",docVO.get("VISITDT"));
    	stdDocData.put("DIN",CommonUtil.dinValidation((String)docVO.get("DIN")));
    	stdDocData.put("ETHNICITY",LabCommonUtils.checkEthnicityData((String)docVO.get("ETHNICITY")));
    	stdDocData.put("ENTER_DATE",docVO.get("ENTER_DT"));
    	
    	String RACE_CD = (String)docVO.get("RACE");
    	
    	stdDocData.put("RACE_WHITE",LabCommonUtils.checkRaceData((String)docVO.get("RACE_WHITE")));
    	stdDocData.put("RACE_BLACK",LabCommonUtils.checkRaceData((String)docVO.get("RACE_BLACK")));
    	stdDocData.put("RACE_ASIAN",LabCommonUtils.checkRaceData((String)docVO.get("RACE_ASIAN")));
    	stdDocData.put("RACE_INDIAN",LabCommonUtils.checkRaceData((String)docVO.get("RACE_INDIAN")));
    	stdDocData.put("RACE_PACIFIC",LabCommonUtils.checkRaceData((String)docVO.get("RACE_PACIFIC")));
    	stdDocData.put("RACE_UNKNOWN",LabCommonUtils.checkRaceData((String)docVO.get("RACE_UNKNOWN")));
    	stdDocData.put("RACE_OTHER",LabCommonUtils.checkRaceData((String)docVO.get("RACE_OTHER")));
    	
    	if(CommonUtil.isNullOrEmpty(RACE_CD)){
    		stdDocData.put("RACE_CD","");
    	}else{
    		
    		RACE_CD = RACE_CD.trim().toUpperCase();
    		
    		String[] raceCodes = RACE_CD.split(",");
    		
    		for (String _raceCode : raceCodes) {
    			if ("WHITE".equals(_raceCode)) {
    	        	
    				stdDocData.put("RACE_WHITE","1");
    				
    	        } else if ("BLACK".equals(_raceCode)) {
    	        	
    	        	stdDocData.put("RACE_BLACK","1");
    	        	
    	        }  else if ("ASIAN".equals(_raceCode)) {
    	        	
    	        	stdDocData.put("RACE_ASIAN","1");
    	        	
    	        } else if (_raceCode.contains("INDIAN")) {
    	        	
    	        	stdDocData.put("RACE_INDIAN","1");
    	        	
    	        } else if ("OTHER".equals(_raceCode)) {
    	        	
    	        	stdDocData.put("RACE_OTHER","1");
    	        	
    	        } else if (_raceCode.contains("HAWAIIAN")) {  // Hawaiian, Pacific Islander
    	        	
    	        	stdDocData.put("RACE_PACIFIC","1");
    	        	
    	        } else if ( "UNKNOWN".equals(_raceCode)) {
    	        	
    	        	stdDocData.put("RACE_UNKNOWN","1");
    	        	
    	        }
    	        
			}
	    	
	    	
    	}

    	
    	
    	
    	
    	stdDocData.put("RACE_OTHER_TEXT",docVO.get("OTHERRACE"));
    	
    	stdDocData.put("BIRTH_COUNTRY",docVO.get("CTRYOFBIRTH"));
    	stdDocData.put("SWM",LabCommonUtils.checkRiskData((String)docVO.get("SEX_MALE")));
    	stdDocData.put("SWF",LabCommonUtils.checkRiskData((String)docVO.get("SEX_FEMALE")));
    	stdDocData.put("IDU",LabCommonUtils.checkRiskData((String)docVO.get("INJECTION")));
    	stdDocData.put("SEX_PARTNER_IDU",LabCommonUtils.checkRiskData((String)docVO.get("DRUG")));
    	stdDocData.put("SEX_PARTNER_HIV",LabCommonUtils.checkRiskData((String)docVO.get("HIVPSON")));
    	stdDocData.put("RECV_BLOOD_PROD",LabCommonUtils.checkRiskData((String)docVO.get("RECBLD")));
    	stdDocData.put("PERINATAL",LabCommonUtils.checkRiskData((String)docVO.get("PERIEXPO")));
    	stdDocData.put("RISK_UNKNOWN",LabCommonUtils.checkRiskData((String)docVO.get("RSKUNK")));
    	stdDocData.put("SEX_PARTNER_MSM",LabCommonUtils.checkRiskData((String)docVO.get("BXMAN")));
    	stdDocData.put("SEX_PARTNER_HEMOPHILIAC",LabCommonUtils.checkRiskData((String)docVO.get("S_HEMO")));
    	stdDocData.put("HEALTHCARE_WORKER",LabCommonUtils.checkRiskData((String)docVO.get("HCW")));
    	
    	String otherRisk = (String)docVO.get("OTHERRISK");
    	
    	if(CommonUtil.isNotNullAndEmpty(otherRisk)){
    		stdDocData.put("RISK_OTHER","Y");
    		stdDocData.put("RISK_OTHER_TEXT",otherRisk);
    	}else{
    		stdDocData.put("RISK_OTHER","N");
    	}

    	stdDocData.put("RECV_BLOOD_PROD_DATE_TEXT",docVO.get("RECBLDDT"));
    	stdDocData.put("RECV_BLOOD_PROD_COUNTRY",docVO.get("RECBLDCTRY"));
    	stdDocData.put("INITIAL_HIV_DIAG",LabCommonUtils.checkRaceData((String)docVO.get("FSTHIVDX")));
    	stdDocData.put("INITIAL_HIV_DIAG_DATE_TEXT",docVO.get("FSTHIVDT"));
    	stdDocData.put("INFORMED_HIV_POS",LabCommonUtils.checkRaceData((String)docVO.get("INFPOSSTUS")));
    	stdDocData.put("INFORMED_HIV_DATE_TEXT",docVO.get("INFPOSDT"));
    	stdDocData.put("PREVIOUS_HIV_DIAG",LabCommonUtils.checkRaceData((String)docVO.get("PREHIVDX")));
    	stdDocData.put("PREV_HIV_DIAG_DATE_TEXT",docVO.get("PREHIVDT"));
    	stdDocData.put("INITIAL_AIDS_DIAG",LabCommonUtils.checkRaceData((String)docVO.get("FSTASDX")));
    	stdDocData.put("INIT_AIDS_DIAG_DATE_TEXT",docVO.get("FSTASDXDT"));
    	stdDocData.put("PREVIOUS_AIDS_DIAG",LabCommonUtils.checkRaceData((String)docVO.get("PREASDX")));
    	stdDocData.put("PREV_AIDS_DIAG_DATE_TEXT",docVO.get("PREASDT"));
    	stdDocData.put("PS_REQUESTED",LabCommonUtils.checkRaceData((String)docVO.get("CNAP")));
    	stdDocData.put("SSN",CommonUtil.formatSSNValue((String)docVO.get("SSN")));
    	stdDocData.put("PROV_FACILITY",docVO.get("PROFALITY"));
    	stdDocData.put("PROV_LNAME",docVO.get("PROLAST"));
    	stdDocData.put("PROV_FNAME",docVO.get("PROFIST"));
    	stdDocData.put("PROV_MI",docVO.get("PROMI"));
    	stdDocData.put("PROV_TITLE",docVO.get("PROTITLE"));
    	stdDocData.put("PROV_DEPARTMENT",docVO.get("PRODEPT"));
    	stdDocData.put("PROV_ADDRESS",docVO.get("PROADD"));
    	stdDocData.put("PROV_CITY",docVO.get("PROCITY"));
    	stdDocData.put("PROV_STATE",docVO.get("PROST"));
    	stdDocData.put("PROV_ZIP",docVO.get("PROZIP"));
    	stdDocData.put("PROV_PHONE",docVO.get("PROTEL"));
    	stdDocData.put("PROV_EXT",docVO.get("PROEXT"));
    	stdDocData.put("PRO_EMAIL",docVO.get("PROEMAIL"));
    	stdDocData.put("COMPLETED_DATE",docVO.get("PROCMPDT"));
    	stdDocData.put("CONTACT_LNAME",docVO.get("CONTLAST"));
    	stdDocData.put("CONTACT_FNAME",docVO.get("CONTFIRST"));
    	stdDocData.put("CONTACT_MNAME",docVO.get("CONTMI"));
    	stdDocData.put("CONTACT_TITLE",docVO.get("CONTTITLE"));
    	stdDocData.put("CONTACT_PHONE",docVO.get("CONTTEL"));
    	stdDocData.put("TSAID",docVO.get("TSAID"));
    	stdDocData.put("REVTSAID",docVO.get("REVTSAID"));
    	stdDocData.put("UPDATE_DATE",docVO.get("UPDATE_DT"));
    	stdDocData.put("PRINTED_DATE",docVO.get("PRINTED"));
    	stdDocData.put("OLDFORM",docVO.get("OLDFORM"));
    	stdDocData.put("NOTSEND",docVO.get("NOTSEND"));
    	stdDocData.put("SIG_DATE",docVO.get("MDSIGN"));
    	stdDocData.put("MDPRINTNAME",docVO.get("MDPRINTNAME"));
    	stdDocData.put("WARNING",docVO.get("WARNING"));
    	stdDocData.put("EHARS",docVO.get("EHARS"));
    	stdDocData.put("EHARS_DATE",docVO.get("EHARSDT"));
    	stdDocData.put("RECEIVED_DATE",docVO.get("RECEIVED"));
    	stdDocData.put("PT_INTERVIEW_DATE",docVO.get("INTIVDT"));
    	stdDocData.put("FIRST_POS_HIV_DATE",docVO.get("FSTPOSHIVDT"));
    	stdDocData.put("FIRST_POS_ANON",LabCommonUtils.checkTestDataValues((String)docVO.get("FSTPOSANO")));
    	stdDocData.put("FIRST_POS_LOCATION",docVO.get("FLITYTYPE_D"));
    	stdDocData.put("FIRST_POS_FAC_NAME",docVO.get("FLITYNAME_D"));
    	stdDocData.put("FIRST_POS_FAC_STATE",docVO.get("FLITYST_D"));
    	
    	stdDocData.put("ARV_6_MO",LabCommonUtils.checkTestDataValues((String)docVO.get("ANTISIX")));
    	stdDocData.put("ARV_6_MO_NAME",docVO.get("WHICH"));
    	stdDocData.put("ARV_6_MO_START_DATE_TEXT",docVO.get("STARTDT"));
    	stdDocData.put("ARV_6_MO_END_DATE_TEXT",docVO.get("ENDDT"));
    	stdDocData.put("ARV_CURRENT_TEST",LabCommonUtils.checkTestDataValues((String)docVO.get("ANTICUR")));
    	stdDocData.put("FIRST_HIV_TEST_EVER_DATE_TEXT",docVO.get("FSTHIVTSTDT"));
    	
    	
    	
    	
    	
    	
    	stdDocData.put("LAST_NEG_FAC_TYPE",docVO.get("FLITYTYPE_J"));
    	stdDocData.put("LAST_NEG_FAC_NAME",docVO.get("FCLITYNAME_J"));
    	stdDocData.put("LAST_NEG_FAC_STATE",docVO.get("FCLITYST_J"));
    	
    	
    	stdDocData.put("EHARS_DOCUMENT_SOURCE_CD",docVO.get("SOURE_CD"));
    	stdDocData.put("EVER_HAD_PREV_POS_TEST",docVO.get("EVEP"));
    	stdDocData.put("BIRTH_SEX",LabCommonUtils.checkBirthSexData((String)docVO.get("SEXB")));
    	
    	
    	
    	
    	stdDocData.put("PREGNANT",docVO.get("PREGN"));
    	stdDocData.put("ACUTE_INFECTION",docVO.get("ACUINF"));
    	stdDocData.put("PROVIDER_NPI_NO",docVO.get("NPINO"));
    	stdDocData.put("PROVIDER_NYS_LIC_NO",docVO.get("LICNO"));
    	stdDocData.put("FACILITY_TYPE_OTHER_TEXT",docVO.get("OFACI"));
    	stdDocData.put("FACILITY_TYPE",docVO.get("FACITYPE"));
    	
    	String RECOMD = (String)docVO.get("RECOMD");
    	
    	if(CommonUtil.isNotNullAndEmpty(RECOMD)){
    		stdDocData.put("RSON_FIRST_POS_PRTNR",RECOMD);
    	}else{
    		stdDocData.put("RSON_FIRST_POS_PRTNR",docVO.get("PRTHIVPOS"));
    	}
    	
    	
    	
    	
    	
    	
    	String TREATMMED = (String)docVO.get("TREATMMED");
    	
    	if(CommonUtil.isNotNullAndEmpty(TREATMMED)){
    		stdDocData.put("PT_ARV_NAME",TREATMMED.replaceAll(",", "|"));
    	}
    	
    	
    	stdDocData.put("PT_ARV_END_DATE_TEXT",docVO.get("TREATM_EDT"));
    	stdDocData.put("PT_ARV_START_DATE_TEXT",docVO.get("TREATM_SDT"));
    	stdDocData.put("ARV_TREATMENT",docVO.get("TREATM"));
    	
    	String POSMED = (String)docVO.get("POSMED");
    	
    	if(CommonUtil.isNotNullAndEmpty(POSMED)){
    		stdDocData.put("POST_EXP_ARV_PEP_MED_NAMES",POSMED.replaceAll(",", "|"));
    	}
    	
    	
    	stdDocData.put("POST_EXP_ARV_PEP_END_DT",docVO.get("POSPEP_EDT"));
    	
    	
    	stdDocData.put("POST_EXP_ARV_PEP_START_DT",docVO.get("POSPEP_SDT"));
    	stdDocData.put("POST_EXP_ARV_PEP",docVO.get("POSPEP"));
    	
    	String ARVMED = (String)docVO.get("ARVMED");
    	
    	if(CommonUtil.isNotNullAndEmpty(ARVMED)){
    		stdDocData.put("ARV_PREVENPREP_MED_NAMES",ARVMED.replaceAll(",", "|"));
    	}
    	
    	
    	stdDocData.put("ARV_PREVEN_PREP_END_DT",docVO.get("ARVPREV_EDT"));
    	stdDocData.put("ARV_PREVEN_PREP_START_DT",docVO.get("ARVPREV_SDT"));
    	stdDocData.put("ARV_PREVEN_PREP",docVO.get("ARVPREV"));
    	
    	
    	
    	
    	stdDocData.put("PT_ARV_EVER_TAKEN",LabCommonUtils.checkTestDataValues((String)docVO.get("ARVEVRTKEN")));
    	

    	String  NUMTSTING = (String)docVO.get("NUMTSTING");
    	
    	if(CommonUtil.isNotNullAndEmpty(NUMTSTING)){
    		
    		NUMTSTING = NUMTSTING.replaceAll("_", "");
    		
    		if(CommonUtil.isNumeric(NUMTSTING) && NUMTSTING.startsWith("0") && NUMTSTING.length() > 1){
    			
    			stdDocData.put("NUMBER_TEST_2_YRS", NUMTSTING.substring(1));
    			
    		}else{
    			stdDocData.put("NUMBER_TEST_2_YRS", NUMTSTING);
    		}
    		
    	}else{
    		stdDocData.put("NUMBER_TEST_2_YRS",docVO.get("NGTST"));
    	}
    	
    	stdDocData.put("REFFERED_PROVIDER_TELEPHONE",docVO.get("REFPVTEL"));
    	stdDocData.put("REFERRED_PROVIDER_NAME",docVO.get("REFPVNAME"));
    	stdDocData.put("REFERRED_HIV_PROVIDER",docVO.get("REFPV"));
    	stdDocData.put("PRIMARY_LANGUAGE_OTHER",docVO.get("OLANG"));
    	stdDocData.put("PRIMARY_LANGUAGE",docVO.get("LANG"));
    	stdDocData.put("PERSON_OTHER_IDENTIFIER",docVO.get("OIDENTF"));
    	stdDocData.put("ADDITIONAL_GENDER_IDENTITY",docVO.get("ADTIDENTF"));
    	
    	stdDocData.put("EDC",docVO.get("EDC"));
    	stdDocData.put("ALT_LNAME",docVO.get("ALNAME"));
    	stdDocData.put("ALT_MNAME",docVO.get("AMNAME"));
    	
    	

    	
//    	EVRNGTST                           EVER_TEST_NEG
    	if(CommonUtil.isNotNullAndEmpty((String)docVO.get("EVRNGTST"))) {
    		stdDocData.put("EVER_TEST_NEG",LabCommonUtils.checkTestDataValues((String)docVO.get("EVRNGTST")));
    	}else {
    		stdDocData.put("EVER_TEST_NEG",LabCommonUtils.checkTestDataValues((String)docVO.get("TSTNGBEF")));
    	}
    	
//    	LASTNGTSTDT                    LAST_NEG_DATE_TEXT
    	if(CommonUtil.isNotNullAndEmpty((String)docVO.get("LASTNGTSTDT"))) {
    		stdDocData.put("LAST_NEG_DATE_TEXT",docVO.get("LASTNGTSTDT"));
    	}else {
    		stdDocData.put("LAST_NEG_DATE_TEXT",docVO.get("LSTNGTSTDT"));
    	}
    	
    	
//    	EXPHIV                                 RSON_FIRST_POS_EXP
    	if(CommonUtil.isNotNullAndEmpty((String)docVO.get("EXPHIV"))) {
    		stdDocData.put("RSON_FIRST_POS_EXP",LabCommonUtils.checkTestDataValues((String)docVO.get("EXPHIV")));
    	}else {
    		stdDocData.put("RSON_FIRST_POS_EXP",LabCommonUtils.checkTestDataValues((String)docVO.get("EXPSIX")));
    	}
    	
//    	ROUTN                                 RSON_FIRST_POS_ROUTN
    	if(CommonUtil.isNotNullAndEmpty((String)docVO.get("ROUTN"))) {
    		stdDocData.put("RSON_FIRST_POS_ROUTN",LabCommonUtils.checkTestDataValues((String)docVO.get("ROUTN")));
    	}else {
    		stdDocData.put("RSON_FIRST_POS_ROUTN",LabCommonUtils.checkTestDataValues((String)docVO.get("TSTAGAIN")));
    	}
    	
//    	CHKNEG                               RSON_FIRST_POS_CHKNEG
    	if(CommonUtil.isNotNullAndEmpty((String)docVO.get("CHKNEG"))) {
    		stdDocData.put("RSON_FIRST_POS_CHKNEG",LabCommonUtils.checkTestDataValues((String)docVO.get("CHKNEG")));
    	}else {
    		stdDocData.put("RSON_FIRST_POS_CHKNEG",LabCommonUtils.checkTestDataValues((String)docVO.get("CHECKING")));
    	}
    	
//    	REQD                                     RSON_FIRST_POS_REQD
    	if(CommonUtil.isNotNullAndEmpty((String)docVO.get("REQD"))) {
    		stdDocData.put("RSON_FIRST_POS_REQD",LabCommonUtils.checkTestDataValues((String)docVO.get("REQD")));
    	}else {
    		stdDocData.put("RSON_FIRST_POS_REQD",LabCommonUtils.checkTestDataValues((String)docVO.get("REQIRED")));
    	}
    	
    	
//    	ORSON                                 RSON_FIRST_POS_OTHER
    	if(CommonUtil.isNotNullAndEmpty((String)docVO.get("ORSON"))) {
    		stdDocData.put("RSON_FIRST_POS_OTHER", LabCommonUtils.checkTestDataValues((String)docVO.get("ORSON")));
    	}else {
    		stdDocData.put("RSON_FIRST_POS_OTHER", LabCommonUtils.checkTestDataValues((String)docVO.get("OTHERRSON")));
    	}
    	
//    	DESCR                                   RSON_FIRST_POS_OTHER_TXT
    	if(CommonUtil.isNotNullAndEmpty((String)docVO.get("DESCR"))) {
    		stdDocData.put("RSON_FIRST_POS_OTHER_TEXT",docVO.get("DESCR"));
    	}else {
    		stdDocData.put("RSON_FIRST_POS_OTHER_TEXT",docVO.get("SPOTHERRSON"));
    	}
    		
    	
    	//new fields
    	stdDocData.put("FIELD_PROC_DATE",(String)docVO.get("FIELDPROCDATE"));
    	stdDocData.put("APARTMENT",(String)docVO.get("APT"));
    	
    	String sexOrn = (String)docVO.get("SXORI");
    	
    	
    	
    	SexOrn sexorn = SexOrn.fromString(sexOrn);
    	if(sexorn != null) {
    		stdDocData.put("SEX_ORN",sexorn.name());
    	}
    	stdDocData.put("SEX_ORN_ADD_ID",(String)docVO.get("SXORI_ADTIDF"));
    	stdDocData.put("NUM_NEG_HIV_TEST",(String)docVO.get("NUMNGTST"));
    	
    	
    	stdDocData.put("CREATE_UID","900");
    	stdDocData.put("CREATE_DATE",new Date(Calendar.getInstance().getTimeInMillis()));
    	stdDocData.put("CURRENT_PROCESS_STATUS", CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
    	
    	int resultCount = nycPRFDocumentDAO.writeStdDocData(stdDocData);
    	
    	
    	
    	return resultCount;
    	

    	
	}
	
	public void writeRawNycPRFPartnerDataFromRawTable(BigDecimal documentId){
		
		nycPRFDocumentDAO.writeRawPRFPartnerDataToStdTable(documentId.intValue());
		
	}
	
	public PersonViewVO getNYCPRFDocumentForClericalReview(int documentId){
		
		
		PersonViewVO nycPRFDocVO = nycPRFDocumentDAO.getNYCPRFDocumentForClericalReview(documentId);
    	
    	return nycPRFDocVO;
		
	}
	
	public int writeNYCPRFDocAndPartnersData(Map<Object, Object> docVO,
			BigDecimal documentId){
		return documentTransactionHelper.writeNYCPRFDocAndPartnersData(docVO, documentId);
	}
}
