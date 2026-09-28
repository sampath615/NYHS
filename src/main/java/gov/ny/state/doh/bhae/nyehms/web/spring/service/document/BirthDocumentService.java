package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.BirthDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * This is implementation of the BirthDocumentService interface. This provides
 * implementation for all the methods for BTH documents.
 *
 * @author ska03
 */
@Service
public class BirthDocumentService {

    
    final String ONE = "1";
    final String ZERO = "0";    
    @Autowired
    BirthDocumentDAO birthDocumentDAO;

    public int insertRawBirthMomDataToStdTable(int bthDocumentId, String birthMatchId, Map<String, Object> documentMatchData) {
    	
    	Map<String, String> rawBthMomData = birthDocumentDAO.getMomData(birthMatchId);
    	
    	Map<String, Object> inputParameterMap = new HashMap<String, Object>();
    	
    	inputParameterMap.put("DOCUMENT_ID", bthDocumentId);
    	
    	inputParameterMap.put("BIRTH_MATCH_PK", birthMatchId);
    	
    	
    	inputParameterMap.put("PERSON_ADDRESS", documentMatchData.get("personAddress"));
    	inputParameterMap.put("PERSON_ZIP", documentMatchData.get("personZip"));
    	inputParameterMap.put("PERSON_CITY", documentMatchData.get("personCity"));
    	inputParameterMap.put("PERSON_STATE", documentMatchData.get("personState"));
    	inputParameterMap.put("PERSON_COUNTY", documentMatchData.get("personCounty"));
    			 
    	inputParameterMap.put("PERSON_LATITUDE", documentMatchData.get("personLatitude"));
    	inputParameterMap.put("PERSON_LONGITUDE", documentMatchData.get("personLongitude"));
    	inputParameterMap.put("PERSON_CENSUS_TRACT", documentMatchData.get("personCensusTract"));
	
    	
    	inputParameterMap.put("PERSON_COUNTRY", rawBthMomData.get("MOTHER_RES_COUNTRY"));
    	
    	inputParameterMap.put("FNAME", CommonUtil.NameValidation(rawBthMomData.get("MOTHER_FNAME")));
    	inputParameterMap.put("MNAME", CommonUtil.NameValidation(rawBthMomData.get("MOTHER_MNAME")));
    	inputParameterMap.put("CURRENT_LNAME", CommonUtil.NameValidation(rawBthMomData.get("MOTHER_CURRENT_LNAME")));
    	inputParameterMap.put("MAIDEN_LNAME", CommonUtil.NameValidation(rawBthMomData.get("MOTHER_MAIDEN_LNAME")));
    	
    	inputParameterMap.put("MED_RECORD_NUMBER", CommonUtil.normalizeString(rawBthMomData.get("MOTHER_MED_REC")));
    	
    	//Need to convert from code to state value
    	inputParameterMap.put("BIRTH_STATE",rawBthMomData.get("MOTHER_BIRTH_STATE"));
    	inputParameterMap.put("BIRTH_COUNTRY_CD",rawBthMomData.get("MOTHER_BIRTH_COUNTRY_CD"));
    		
    	inputParameterMap.put("RACE_WHITE",LabCommonUtils.checkRaceData(rawBthMomData.get("MOTHER_WHITE")));
    	inputParameterMap.put("RACE_BLACK",LabCommonUtils.checkRaceData(rawBthMomData.get("MOTHER_BLACK")));
    	
    	inputParameterMap.put("RACE_ASIAN", "0");
    	
    	
    	if (ONE.equals(rawBthMomData.get("MOTHER_ASIAN_INDIAN"))
				|| ONE.equals( rawBthMomData.get("MOTHER_ASIAN_OTHER"))
				|| ONE.equals( rawBthMomData.get("MOTHER_CHINESE"))
				|| ONE.equals( rawBthMomData.get("MOTHER_FILIPINO"))
				|| ONE.equals( rawBthMomData.get("MOTHER_JAPANESE"))
				|| ONE.equals( rawBthMomData.get("MOTHER_KOREAN"))
				|| ONE.equals( rawBthMomData.get("MOTHER_VIETNAMESE"))) {
    	  		inputParameterMap.put("RACE_ASIAN", 1);
		}
    	    		
    	inputParameterMap.put("RACE_PACIFIC", 0);
		if (ONE.equals( rawBthMomData.get("MOTHER_HAWAIIAN"))
				|| ONE.equals( rawBthMomData.get("MOTHER_PACIFIC_ISLANDER_OTHER"))
				|| ONE.equals( rawBthMomData.get("MOTHER_SAMOAN"))
				|| ONE.equals( rawBthMomData.get("MOTHER_GUAMANIAN"))) {
			inputParameterMap.put("RACE_PACIFIC", 1);
		}	
    		
		inputParameterMap.put("RACE_INDIAN",LabCommonUtils.checkRaceData(rawBthMomData.get("MOTHER_AMER_INDIAN_ALASKA_NATIVE")));
		inputParameterMap.put("RACE_OTHER",LabCommonUtils.checkRaceData(rawBthMomData.get("MOTHER_RACE_OTHER")));
    	
		
		inputParameterMap.put("RACE_OTHER",LabCommonUtils.checkRaceData(rawBthMomData.get("MOTHER_RACE_OTHER")));
		
		String ethnicity = "";
		if (ONE.equals(rawBthMomData.get("MOTHER_HISP_ORIGIN_MEXICAN"))
				|| ONE.equals(rawBthMomData.get("MOTHER_HISP_ORIGIN_PUERTO_RICO"))
				|| ONE.equals(rawBthMomData.get("MOTHER_HISP_ORIGIN_CUBAN"))
				|| ONE.equals(rawBthMomData.get("MOTHER_HISP_ORIGIN_OTHER"))
				) {
			ethnicity = "01";
		} else if (ZERO.equals(rawBthMomData.get("MOTHER_HISP_ORIGIN_MEXICAN"))
				&& ZERO.equals(rawBthMomData.get("MOTHER_HISP_ORIGIN_PUERTO_RICO"))
				&& ZERO.equals(rawBthMomData.get("MOTHER_HISP_ORIGIN_CUBAN"))
				&& ZERO.equals(rawBthMomData.get("MOTHER_HISP_ORIGIN_OTHER"))
				&& ONE.equals(rawBthMomData.get("MOTHER_NOT_HISPANIC"))) {
			ethnicity = "02";
		} else {
			ethnicity = "09";
		}

		inputParameterMap.put("ETHNICITY", ethnicity);
		//FN_TO_DATE
		inputParameterMap.put("DOB", rawBthMomData.get("MOTHER_DOB"));
		inputParameterMap.put("EDU_LEVEL", rawBthMomData.get("MOTHER_EDU_LEVEL"));
		//convert date format 
		inputParameterMap.put("FIRST_PRENATAL_VISIT_DT", rawBthMomData.get("FIRST_PRENATAL_VISIT_DT"));
		inputParameterMap.put("LAST_PRENATAL_VISIT_DT", rawBthMomData.get("LAST_PRENATAL_VISIT_DT"));
		inputParameterMap.put("NO_PRENATAL_VISITS", rawBthMomData.get("NO_PRENATAL_VISITS"));
		inputParameterMap.put("GESTATION_WKS", rawBthMomData.get("GESTATION_WKS"));
		inputParameterMap.put("NO_PREV_LIVE_BIRTHS_LIVING", rawBthMomData.get("NO_PREV_LIVE_BIRTHS_LIVING"));
		inputParameterMap.put("NO_PREV_LIVE_BIRTHS_DEAD", rawBthMomData.get("NO_PREV_LIVE_BIRTHS_DEAD"));
		inputParameterMap.put("NO_PREV_PREG_TOTAL", rawBthMomData.get("NO_PREV_PREG_TOTAL"));
		inputParameterMap.put("INFECTIONS_NONE", rawBthMomData.get("INFECTIONS_NONE"));
		inputParameterMap.put("GONORRHEA", rawBthMomData.get("GONORRHEA"));
		inputParameterMap.put("SYPHILIS", rawBthMomData.get("SYPHILIS"));
		inputParameterMap.put("GENITAL_HERPES", rawBthMomData.get("GENITAL_HERPES"));
		inputParameterMap.put("CHLAMYDIA", rawBthMomData.get("CHLAMYDIA"));
		inputParameterMap.put("HEP_B", rawBthMomData.get("HEP_B"));
		inputParameterMap.put("HEP_C", rawBthMomData.get("HEP_C"));
		inputParameterMap.put("TB", rawBthMomData.get("TB"));
		inputParameterMap.put("RUBELLA", rawBthMomData.get("RUBELLA"));
		inputParameterMap.put("BACTERIAL_VAGINOSIS", rawBthMomData.get("BACTERIAL_VAGINOSIS"));
		inputParameterMap.put("INFECTIONS_UNK", rawBthMomData.get("INFECTIONS_UNK"));
		inputParameterMap.put("SSN", CommonUtil.formatSSNValue(rawBthMomData.get("MOTHER_SSN")));
		inputParameterMap.put("AGE", rawBthMomData.get("MOTHER_AGE"));
		inputParameterMap.put("START_PRENATAL_CARE_TRIMESTER", rawBthMomData.get("START_PRENATAL_CARE_TRIMESTER"));
		inputParameterMap.put("PERSON_PHONE", rawBthMomData.get("MOTHER_PHONE"));
		inputParameterMap.put("PRENATAL_CARE_RECEIVED", rawBthMomData.get("PRENATAL_CARE_RECEIVED"));
		inputParameterMap.put("CURRENT_PROCESS_STATUS", 5);
		inputParameterMap.put("BIRTH_FILE", rawBthMomData.get("BIRTH_FILE"));
		//convert to Date formate
		inputParameterMap.put("RECEIVED_DATE", rawBthMomData.get("RECEIVED_DATE"));
			
			
		return birthDocumentDAO.writeStdBirthMomData(inputParameterMap);
    }
    
    public int insertRawBirthInfantDataToStdTable(int bthDocumentId, String birthMatchId,
			Map<String, Object> documentMatchData) {
    	
    	Map<String, String> rawBthMomData = birthDocumentDAO.getInfantData(birthMatchId);
    	
    	Map<String, Object> inputParameterMap = new HashMap<String, Object>();
    	
    	inputParameterMap.put("DOCUMENT_ID", bthDocumentId);
    	
    	inputParameterMap.put("BIRTH_MATCH_PK", birthMatchId);
    	
    	inputParameterMap.put("PERSON_ADDRESS", documentMatchData.get("personAddress"));
    	inputParameterMap.put("PERSON_ZIP", documentMatchData.get("personZip"));
    	inputParameterMap.put("PERSON_CITY", documentMatchData.get("personCity"));
    	inputParameterMap.put("PERSON_STATE", documentMatchData.get("personState"));
    	inputParameterMap.put("PERSON_COUNTY", documentMatchData.get("personCounty"));
    			 
    	inputParameterMap.put("PERSON_LATITUDE", documentMatchData.get("personLatitude"));
    	inputParameterMap.put("PERSON_LONGITUDE", documentMatchData.get("personLongitude"));
    	inputParameterMap.put("PERSON_CENSUS_TRACT", documentMatchData.get("personCensusTract"));
    	inputParameterMap.put("PERSON_COUNTRY", rawBthMomData.get("MOTHER_RES_COUNTRY"));
    	
    	
    	inputParameterMap.put("POB_INST", rawBthMomData.get("POB_INST"));
    	inputParameterMap.put("POB_INST_TYPE", rawBthMomData.get("POB_INST_TYPE"));
    	inputParameterMap.put("BIRTH_FAC_PFI", rawBthMomData.get("BIRTH_FAC_PFI"));
    	inputParameterMap.put("BIRTH_ADDR_STREET", rawBthMomData.get("BIRTH_ADDR_STREET"));
    	inputParameterMap.put("BIRTH_ADDR_ZIP", rawBthMomData.get("BIRTH_ADDR_ZIP"));
    	inputParameterMap.put("BIRTH_CERT_NO", rawBthMomData.get("BIRTH_CERT_NO"));
    	inputParameterMap.put("DEATH_CERT_NO", rawBthMomData.get("DEATH_CERT_NO"));
    	//convert it to date formate
    	inputParameterMap.put("DEATH_DT", rawBthMomData.get("DEATH_DT"));
    	inputParameterMap.put("DEATH_STATE", rawBthMomData.get("DEATH_STATE"));
    	inputParameterMap.put("FNAME", CommonUtil.NameValidation(rawBthMomData.get("INFANT_FNAME")));
    	inputParameterMap.put("MNAME", CommonUtil.NameValidation(rawBthMomData.get("INFANT_MNAME")));
    	inputParameterMap.put("LNAME", CommonUtil.NameValidation(rawBthMomData.get("INFANT_LNAME")));
    	inputParameterMap.put("SUFFIX", CommonUtil.NameValidation(rawBthMomData.get("INFANT_SUFFIX")));
    	inputParameterMap.put("MED_RECORD_NUMBER", CommonUtil.normalizeString(rawBthMomData.get("INFANT_MED_REC")));
    	inputParameterMap.put("DOB", rawBthMomData.get("INFANT_DOB"));
    	inputParameterMap.put("DOB_TIME", rawBthMomData.get("INFANT_DOB_TIME"));
    	inputParameterMap.put("SEX", LabCommonUtils.checkBirthSexData(rawBthMomData.get("INFANT_SEX")));
    	//inputParameterMap.put("INFANT_PLURALITY", rawBthMomData.get("INFANT_PLURALITY"));
    	
    	
    	inputParameterMap.put("MOTHER_FNAME", CommonUtil.NameValidation(rawBthMomData.get("MOTHER_FNAME")));
    	inputParameterMap.put("MOTHER_MNAME", CommonUtil.NameValidation(rawBthMomData.get("MOTHER_MNAME")));
    	inputParameterMap.put("MOTHER_CURRENT_LNAME", CommonUtil.NameValidation(rawBthMomData.get("MOTHER_CURRENT_LNAME")));
    	inputParameterMap.put("MOTHER_MAIDEN_LNAME", CommonUtil.NameValidation(rawBthMomData.get("MOTHER_MAIDEN_LNAME")));
    	inputParameterMap.put("INFANT_TRANSFERRED", rawBthMomData.get("INFANT_TRANSFERRED"));
    	inputParameterMap.put("HOSP_REC_TRANSFERRED_INFANT", rawBthMomData.get("HOSP_REC_TRANSFERRED_INFANT"));
    	inputParameterMap.put("INFANT_ALIVE_REP_TIME", rawBthMomData.get("INFANT_ALIVE_REP_TIME"));
    	inputParameterMap.put("SYPHILIS_TEST_GIVEN", rawBthMomData.get("SYPHILIS_TEST_GIVEN"));
    	inputParameterMap.put("HOW_INFANT_FED", rawBthMomData.get("HOW_INFANT_FED"));
    	inputParameterMap.put("ABNORMAL_CONDITION_NEWBORN_NONE", rawBthMomData.get("ABNORMAL_CONDITION_NEWBORN_NONE"));
    	inputParameterMap.put("CONGENITAL_MALFORMATIONS_NONE", rawBthMomData.get("CONGENITAL_MALFORMATIONS_NONE"));
    	inputParameterMap.put("CONGENITAL_MALFORMATIONS_UNK", rawBthMomData.get("CONGENITAL_MALFORMATIONS_UNK"));
    	inputParameterMap.put("ANENCEPHALY", rawBthMomData.get("ANENCEPHALY"));
    	inputParameterMap.put("SPINA_BIFIDA", rawBthMomData.get("SPINA_BIFIDA"));
    	inputParameterMap.put("CONGENITAL_HEART_DISEASE", rawBthMomData.get("CONGENITAL_HEART_DISEASE"));
    	inputParameterMap.put("HERNIA", rawBthMomData.get("HERNIA"));
    	inputParameterMap.put("OMPHALOCELE", rawBthMomData.get("OMPHALOCELE"));
    	inputParameterMap.put("GASTROSCHISIS", rawBthMomData.get("GASTROSCHISIS"));
    	inputParameterMap.put("LIMB_REDUCT_DEFECT", rawBthMomData.get("LIMB_REDUCT_DEFECT"));
    	inputParameterMap.put("CLEFT_LIP_PALATE", rawBthMomData.get("CLEFT_LIP_PALATE"));
    	inputParameterMap.put("CLEFT_PALATE", rawBthMomData.get("CLEFT_PALATE"));
    	inputParameterMap.put("DOWN_SYNDROME", rawBthMomData.get("DOWN_SYNDROME"));
    	inputParameterMap.put("DOWN_SYNDROME_KARYOTYPE_CONFIRM", rawBthMomData.get("DOWN_SYNDROME_KARYOTYPE_CONFIRM"));
    	inputParameterMap.put("DOWN_SYNDROME_KARYOTYPE_PENDING", rawBthMomData.get("DOWN_SYNDROME_KARYOTYPE_PENDING"));
    	inputParameterMap.put("CHROMOSOMAL_DISORDER", rawBthMomData.get("CHROMOSOMAL_DISORDER"));
    	inputParameterMap.put("CHROMOSOMAL_KARYOTYPE_CONFIRM", rawBthMomData.get("CHROMOSOMAL_KARYOTYPE_CONFIRM"));
    	inputParameterMap.put("CHROMOSOMAL_KARYOTYPE_PENDING", rawBthMomData.get("CHROMOSOMAL_KARYOTYPE_PENDING"));
    	inputParameterMap.put("HYPOSPADIAS", rawBthMomData.get("HYPOSPADIAS"));
    	inputParameterMap.put("ANENCEPHALY_DIAG_PRENATAL", rawBthMomData.get("ANENCEPHALY_DIAG_PRENATAL"));
    	inputParameterMap.put("ANENCEPHALY_DIAG_ULTRASOUND", rawBthMomData.get("ANENCEPHALY_DIAG_ULTRASOUND"));
    	inputParameterMap.put("ANENCEPHALY_DIAG_TRIPLE_SCREEN", rawBthMomData.get("ANENCEPHALY_DIAG_TRIPLE_SCREEN"));
    	inputParameterMap.put("ANENCEPHALY_DIAG_AMNIOCENTESIS", rawBthMomData.get("ANENCEPHALY_DIAG_AMNIOCENTESIS"));
    	inputParameterMap.put("ANENCEPHALY_DIAG_OTHER", rawBthMomData.get("ANENCEPHALY_DIAG_OTHER"));
    	inputParameterMap.put("ANENCEPHALY_DIAG_UNK", rawBthMomData.get("ANENCEPHALY_DIAG_UNK"));
    	inputParameterMap.put("SPINA_BIFIDA_DIAG_PRENATAL", rawBthMomData.get("SPINA_BIFIDA_DIAG_PRENATAL"));
    	inputParameterMap.put("SPINA_BIFIDA_DIAG_ULTRASOUND", rawBthMomData.get("SPINA_BIFIDA_DIAG_ULTRASOUND"));
    	inputParameterMap.put("SPINA_BIFIDA_DIAG_TRIPLE_SCREEN", rawBthMomData.get("SPINA_BIFIDA_DIAG_TRIPLE_SCREEN"));
    	inputParameterMap.put("SPINA_BIFIDA_DIAG_AMNIOCENTESIS", rawBthMomData.get("SPINA_BIFIDA_DIAG_AMNIOCENTESIS"));
    	inputParameterMap.put("SPINA_BIFIDA_DIAG_OTHER", rawBthMomData.get("SPINA_BIFIDA_DIAG_OTHER"));
    	inputParameterMap.put("SPINA_BIFIDA_DIAG_UNK", rawBthMomData.get("SPINA_BIFIDA_DIAG_UNK"));
    	inputParameterMap.put("HEART_DIS_DIAG_PRENATAL", rawBthMomData.get("HEART_DIS_DIAG_PRENATAL"));
    	inputParameterMap.put("HEART_DIS_DIAG_ULTRASOUND", rawBthMomData.get("HEART_DIS_DIAG_ULTRASOUND"));
    	inputParameterMap.put("HEART_DIS_DIAG_OTHER", rawBthMomData.get("HEART_DIS_DIAG_OTHER"));
    	inputParameterMap.put("HEART_DIS_DIAG_UNK", rawBthMomData.get("HEART_DIS_DIAG_UNK"));
    	inputParameterMap.put("HERNIA_DIAG_PRENATAL", rawBthMomData.get("HERNIA_DIAG_PRENATAL"));
    	inputParameterMap.put("HERNIA_DIAG_ULTRASOUND", rawBthMomData.get("HERNIA_DIAG_ULTRASOUND"));
    	inputParameterMap.put("HERNIA_DIAG_OTHER", rawBthMomData.get("HERNIA_DIAG_OTHER"));
    	inputParameterMap.put("HERNIA_DIAG_UNK", rawBthMomData.get("HERNIA_DIAG_UNK"));
    	inputParameterMap.put("OMPHALOCELE_DIAG_PRENATAL", rawBthMomData.get("OMPHALOCELE_DIAG_PRENATAL"));
    	inputParameterMap.put("OMPHALOCELE_DIAG_ULTRASOUND", rawBthMomData.get("OMPHALOCELE_DIAG_ULTRASOUND"));
    	inputParameterMap.put("OMPHALOCELE_DIAG_OTHER", rawBthMomData.get("OMPHALOCELE_DIAG_OTHER"));
    	inputParameterMap.put("OMPHALOCELE_DIAG_UNK", rawBthMomData.get("OMPHALOCELE_DIAG_UNK"));
    	inputParameterMap.put("GASTROSCHISIS_DIAG_PRENATAL", rawBthMomData.get("GASTROSCHISIS_DIAG_PRENATAL"));
    	inputParameterMap.put("GASTROSCHISIS_DIAG_ULTRASOUND", rawBthMomData.get("GASTROSCHISIS_DIAG_ULTRASOUND"));
    	inputParameterMap.put("GASTROSCHISIS_DIAG_OTHER", rawBthMomData.get("GASTROSCHISIS_DIAG_OTHER"));
    	inputParameterMap.put("GASTROSCHISIS_DIAG_UNK", rawBthMomData.get("GASTROSCHISIS_DIAG_UNK"));
    	inputParameterMap.put("LIMB_REDUCT_DIAG_PRENATAL", rawBthMomData.get("LIMB_REDUCT_DIAG_PRENATAL"));
    	inputParameterMap.put("LIMB_REDUCT_DIAG_ULTRASOUND", rawBthMomData.get("LIMB_REDUCT_DIAG_ULTRASOUND"));
    	inputParameterMap.put("LIMB_REDUCT_DIAG_OTHER", rawBthMomData.get("LIMB_REDUCT_DIAG_OTHER"));
    	inputParameterMap.put("LIMB_REDUCT_DIAG_UNK", rawBthMomData.get("LIMB_REDUCT_DIAG_UNK"));
    	inputParameterMap.put("CLEFT_LIP_PALATE_DIAG_PRENATAL", rawBthMomData.get("CLEFT_LIP_PALATE_DIAG_PRENATAL"));
    	inputParameterMap.put("CLEFT_LIP_PALATE_DIAG_ULTRASOUND", rawBthMomData.get("CLEFT_LIP_PALATE_DIAG_ULTRASOUND"));
    	inputParameterMap.put("CLEFT_LIP_PALATE_DIAG_OTHER", rawBthMomData.get("CLEFT_LIP_PALATE_DIAG_OTHER"));
    	inputParameterMap.put("CLEFT_LIP_PALATE_DIAG_UNK", rawBthMomData.get("CLEFT_LIP_PALATE_DIAG_UNK"));
    	inputParameterMap.put("CLEFT_PALATE_DIAG_PRENATAL", rawBthMomData.get("CLEFT_PALATE_DIAG_PRENATAL"));
    	inputParameterMap.put("CLEFT_PALATE_DIAG_ULTRASOUND", rawBthMomData.get("CLEFT_PALATE_DIAG_ULTRASOUND"));
    	inputParameterMap.put("CLEFT_PALATE_DIAG_OTHER", rawBthMomData.get("CLEFT_PALATE_DIAG_OTHER"));
    	inputParameterMap.put("CLEFT_PALATE_DIAG_UNK", rawBthMomData.get("CLEFT_PALATE_DIAG_UNK"));
    	inputParameterMap.put("DOWN_SYNDROME_DIAG_PRENATAL", rawBthMomData.get("DOWN_SYNDROME_DIAG_PRENATAL"));
    	inputParameterMap.put("DOWN_SYNDROME_DIAG_ULTRASOUND", rawBthMomData.get("DOWN_SYNDROME_DIAG_ULTRASOUND"));
    	inputParameterMap.put("DOWN_SYNDROME_DIAG_TRIPLE_SCREEN", rawBthMomData.get("DOWN_SYNDROME_DIAG_TRIPLE_SCREEN"));
    	inputParameterMap.put("DOWN_SYNDROME_DIAG_CVS", rawBthMomData.get("DOWN_SYNDROME_DIAG_CVS"));
    	inputParameterMap.put("DOWN_SYNDROME_DIAG_AMNIOCENTESIS", rawBthMomData.get("DOWN_SYNDROME_DIAG_AMNIOCENTESIS"));
    	inputParameterMap.put("DOWN_SYNDROME_DIAG_OTHER", rawBthMomData.get("DOWN_SYNDROME_DIAG_OTHER"));
    	inputParameterMap.put("DOWN_SYNDROME_DIAG_UNK", rawBthMomData.get("DOWN_SYNDROME_DIAG_UNK"));
    	inputParameterMap.put("CHROMOSOMAL_DIAG_PRENATAL", rawBthMomData.get("CHROMOSOMAL_DIAG_PRENATAL"));
    	inputParameterMap.put("CHROMOSOMAL_DIAG_ULTRASOUND", rawBthMomData.get("CHROMOSOMAL_DIAG_ULTRASOUND"));
    	inputParameterMap.put("CHROMOSOMAL_DIAG_TRIPLE_SCREEN", rawBthMomData.get("CHROMOSOMAL_DIAG_TRIPLE_SCREEN"));
    	inputParameterMap.put("CHROMOSOMAL_DIAG_CVS", rawBthMomData.get("CHROMOSOMAL_DIAG_CVS"));
    	inputParameterMap.put("CHROMOSOMAL_DIAG_AMNIOCENTESIS", rawBthMomData.get("CHROMOSOMAL_DIAG_AMNIOCENTESIS"));
    	inputParameterMap.put("CHROMOSOMAL_DIAG_OTHER", rawBthMomData.get("CHROMOSOMAL_DIAG_OTHER"));
    	inputParameterMap.put("CHROMOSOMAL_DIAG_UNK", rawBthMomData.get("CHROMOSOMAL_DIAG_UNK"));
    	inputParameterMap.put("HYPOSPADIAS_DIAG_PRENATAL", rawBthMomData.get("HYPOSPADIAS_DIAG_PRENATAL"));
    	inputParameterMap.put("HYPOSPADIAS_DIAG_ULTRASOUND", rawBthMomData.get("HYPOSPADIAS_DIAG_ULTRASOUND"));
    	inputParameterMap.put("HYPOSPADIAS_DIAG_OTHER", rawBthMomData.get("HYPOSPADIAS_DIAG_OTHER"));
    	inputParameterMap.put("HYPOSPADIAS_DIAG_UNK", rawBthMomData.get("HYPOSPADIAS_DIAG_UNK"));
    	inputParameterMap.put("CURRENT_PROCESS_STATUS", rawBthMomData.get("CURRENT_PROCESS_STATUS"));
    	inputParameterMap.put("BIRTH_FILE", rawBthMomData.get("BIRTH_FILE"));
    	inputParameterMap.put("RECEIVED_DATE", rawBthMomData.get("RECEIVED_DATE"));
    	inputParameterMap.put("MOTHER_RES_COUNTRY", rawBthMomData.get("MOTHER_RES_COUNTRY"));
    	
    	
    		
		return birthDocumentDAO.writeStdBirthInfantData(inputParameterMap);
    }
  
    public int isDuplicateDocumentHasAnyMatches(String documentId) {
    	return birthDocumentDAO.isDuplicateDocumentHasAnyMatches(documentId);
    }
    
    public int birthDocumentExists(String birthMatchId, String documentType) {
    	if("BTH_MOM".equals(documentType)) {
    		return birthDocumentDAO.birthMomDocumentExists(birthMatchId);
    	}else {
    		return birthDocumentDAO.birthInfantDocumentExists(birthMatchId);
    	}
    }
    
    public int updateBirthDocumentMatchResults(Map<String, Object> documentMatchData, String documentType) {
    	if("BTH_MOM".equals(documentType)) {
    		return birthDocumentDAO.updateBirthMomDocumentMatchResults(documentMatchData);
    	}else {
    		return birthDocumentDAO.updateBirthInfantDocumentMatchResults(documentMatchData);
    	}
    }
}
