package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYEHMSDocVO;


/**
 * This is pojo class for EPRF Followup document.
 * This is used by mybatis for creating
 * object map for EPRF Followup document .
 *
 * @author ska03
 */
public class EPRFFollowUpDocVO extends NYEHMSDocVO {

	 private int DOCUMENT_ID;
	 private String CARE_STATUS;
	 private String INTERNAL_CONCLUSION;
	 private String REVIEW_COMMENT;
	 private String SUBMITTING_PROVIDER_PRESENT;
	 private String CONFIRMATORY_RESULT_REPORTED;
	 private String ACTION;
	 
	 private String MED_RECORD_NUMBER;
	 private String FNAME;
	 private String LNAME;
	 private String DOB;
	 private String PROV_LAST_VISIT_DATE;
	 private String LAST_VIRAL_LOAD_DATE;
	 private String LAST_VIRAL_LOAD_RESULT;
	 private String  LAST_CD4_DATE;
	 private String LAST_CD4_RESULT_COUNT;
	 private String LAST_CD4_RESULT_PERCENT;
	 private String PROV_COUNTY;
	 private String MATCH_CD;
	 private String QS_MATCH_CD;
	 
	 private String FILENAME;
	 private String FILE_FOLLOW_UP_COMPLETE_DATE;
	 private String FILE_EMAIL_DIRECTOR_DATE;
	 //private String FILE_AI_DIRECTOR_SIGN_OFF_DATE;
	 private String FILE_CLIENT_FEEDBACK_DATE;
	 
	 private String FILE_TYPE;
	 private String FILE_CATEGORY;
	 private String FACILITY_NAME;
	 private String FILE_TYPE_CD;
	 
	 
	 
	 
	 //ehars Data
	 private String MEETS_CDC_ELIG_ID;
	 private String MEETS_CASE_DEFINITION_IN;
	 private String NYC_VITAL_STATUS;
	 private String NYS_VITAL_STATUS;
	 private String MONTHS_SINCE_LAST_HIV_LAB;
	 private String LAST_HIV_LAB_RESULT;
	 private String LAST_HIV_LAB_DATE;
	 private String LAST_HIV_LAB_TYPE;
	 
	 private String NYC_EHARS_STATENO;
	 private String NYS_EHARS_STATENO;
	 private String NYEHMS_STATENO;
	 
	 
	 private String NYC_CITY_NO;
	 
	 private String INTERNAL_CONCLUSION_DESC;
	 private String CONCLUSION_DESC;
	 
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getCARE_STATUS() {
		return CARE_STATUS;
	}
	public void setCARE_STATUS(String cARE_STATUS) {
		CARE_STATUS = cARE_STATUS;
	}
	public String getINTERNAL_CONCLUSION() {
		return INTERNAL_CONCLUSION;
	}
	public void setINTERNAL_CONCLUSION(String iNTERNAL_CONCLUSION) {
		INTERNAL_CONCLUSION = iNTERNAL_CONCLUSION;
	}
	public String getREVIEW_COMMENT() {
		return REVIEW_COMMENT;
	}
	public void setREVIEW_COMMENT(String rEVIEW_COMMENT) {
		REVIEW_COMMENT = rEVIEW_COMMENT;
	}
	public String getSUBMITTING_PROVIDER_PRESENT() {
		return SUBMITTING_PROVIDER_PRESENT;
	}
	public void setSUBMITTING_PROVIDER_PRESENT(String sUBMITTING_PROVIDER_PRESENT) {
		SUBMITTING_PROVIDER_PRESENT = sUBMITTING_PROVIDER_PRESENT;
	}
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	public String getCONFIRMATORY_RESULT_REPORTED() {
		return CONFIRMATORY_RESULT_REPORTED;
	}
	public void setCONFIRMATORY_RESULT_REPORTED(String cONFIRMATORY_RESULT_REPORTED) {
		CONFIRMATORY_RESULT_REPORTED = cONFIRMATORY_RESULT_REPORTED;
	}
	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}
	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
	}
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getLNAME() {
		return LNAME;
	}
	public void setLNAME(String lNAME) {
		LNAME = lNAME;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getPROV_LAST_VISIT_DATE() {
		return PROV_LAST_VISIT_DATE;
	}
	public void setPROV_LAST_VISIT_DATE(String pROV_LAST_VISIT_DATE) {
		PROV_LAST_VISIT_DATE = pROV_LAST_VISIT_DATE;
	}
	public String getLAST_VIRAL_LOAD_DATE() {
		return LAST_VIRAL_LOAD_DATE;
	}
	public void setLAST_VIRAL_LOAD_DATE(String lAST_VIRAL_LOAD_DATE) {
		LAST_VIRAL_LOAD_DATE = lAST_VIRAL_LOAD_DATE;
	}
	public String getLAST_VIRAL_LOAD_RESULT() {
		return LAST_VIRAL_LOAD_RESULT;
	}
	public void setLAST_VIRAL_LOAD_RESULT(String lAST_VIRAL_LOAD_RESULT) {
		LAST_VIRAL_LOAD_RESULT = lAST_VIRAL_LOAD_RESULT;
	}
	public String getLAST_CD4_DATE() {
		return LAST_CD4_DATE;
	}
	public void setLAST_CD4_DATE(String lAST_CD4_DATE) {
		LAST_CD4_DATE = lAST_CD4_DATE;
	}
	public String getLAST_CD4_RESULT_COUNT() {
		return LAST_CD4_RESULT_COUNT;
	}
	public void setLAST_CD4_RESULT_COUNT(String lAST_CD4_RESULT_COUNT) {
		LAST_CD4_RESULT_COUNT = lAST_CD4_RESULT_COUNT;
	}
	public String getLAST_CD4_RESULT_PERCENT() {
		return LAST_CD4_RESULT_PERCENT;
	}
	public void setLAST_CD4_RESULT_PERCENT(String lAST_CD4_RESULT_PERCENT) {
		LAST_CD4_RESULT_PERCENT = lAST_CD4_RESULT_PERCENT;
	}
	public String getPROV_COUNTY() {
		return PROV_COUNTY;
	}
	public void setPROV_COUNTY(String pROV_COUNTY) {
		PROV_COUNTY = pROV_COUNTY;
	}
	public String getMATCH_CD() {
		return MATCH_CD;
	}
	public void setMATCH_CD(String mATCH_CD) {
		MATCH_CD = mATCH_CD;
	}
	public String getQS_MATCH_CD() {
		return QS_MATCH_CD;
	}
	public void setQS_MATCH_CD(String qS_MATCH_CD) {
		QS_MATCH_CD = qS_MATCH_CD;
	}
	public String getFILE_FOLLOW_UP_COMPLETE_DATE() {
		return FILE_FOLLOW_UP_COMPLETE_DATE;
	}
	public void setFILE_FOLLOW_UP_COMPLETE_DATE(String fILE_FOLLOW_UP_COMPLETE_DATE) {
		FILE_FOLLOW_UP_COMPLETE_DATE = fILE_FOLLOW_UP_COMPLETE_DATE;
	}
	public String getFILE_EMAIL_DIRECTOR_DATE() {
		return FILE_EMAIL_DIRECTOR_DATE;
	}
	public void setFILE_EMAIL_DIRECTOR_DATE(String fILE_EMAIL_DIRECTOR_DATE) {
		FILE_EMAIL_DIRECTOR_DATE = fILE_EMAIL_DIRECTOR_DATE;
	}
	
	public String getFILE_CLIENT_FEEDBACK_DATE() {
		return FILE_CLIENT_FEEDBACK_DATE;
	}
	public void setFILE_CLIENT_FEEDBACK_DATE(String fILE_CLIENT_FEEDBACK_DATE) {
		FILE_CLIENT_FEEDBACK_DATE = fILE_CLIENT_FEEDBACK_DATE;
	}
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getFILE_TYPE() {
		return FILE_TYPE;
	}
	public void setFILE_TYPE(String fILE_TYPE) {
		FILE_TYPE = fILE_TYPE;
	}
	public String getFILE_CATEGORY() {
		return FILE_CATEGORY;
	}
	public void setFILE_CATEGORY(String fILE_CATEGORY) {
		FILE_CATEGORY = fILE_CATEGORY;
	}
	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}
	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
	}
	
	public String getNYC_VITAL_STATUS() {
		return NYC_VITAL_STATUS;
	}
	public void setNYC_VITAL_STATUS(String nYC_VITAL_STATUS) {
		NYC_VITAL_STATUS = nYC_VITAL_STATUS;
	}
	public String getNYS_VITAL_STATUS() {
		return NYS_VITAL_STATUS;
	}
	public void setNYS_VITAL_STATUS(String nYS_VITAL_STATUS) {
		NYS_VITAL_STATUS = nYS_VITAL_STATUS;
	}
	public String getMONTHS_SINCE_LAST_HIV_LAB() {
		return MONTHS_SINCE_LAST_HIV_LAB;
	}
	public void setMONTHS_SINCE_LAST_HIV_LAB(String mONTHS_SINCE_LAST_HIV_LAB) {
		MONTHS_SINCE_LAST_HIV_LAB = mONTHS_SINCE_LAST_HIV_LAB;
	}
	public String getLAST_HIV_LAB_RESULT() {
		return LAST_HIV_LAB_RESULT;
	}
	public void setLAST_HIV_LAB_RESULT(String lAST_HIV_LAB_RESULT) {
		LAST_HIV_LAB_RESULT = lAST_HIV_LAB_RESULT;
	}
	public String getLAST_HIV_LAB_DATE() {
		return LAST_HIV_LAB_DATE;
	}
	public void setLAST_HIV_LAB_DATE(String lAST_HIV_LAB_DATE) {
		LAST_HIV_LAB_DATE = lAST_HIV_LAB_DATE;
	}
	public String getLAST_HIV_LAB_TYPE() {
		return LAST_HIV_LAB_TYPE;
	}
	public void setLAST_HIV_LAB_TYPE(String lAST_HIV_LAB_TYPE) {
		LAST_HIV_LAB_TYPE = lAST_HIV_LAB_TYPE;
	}
	
	
	public String getMEETS_CDC_ELIG_ID() {
		return MEETS_CDC_ELIG_ID;
	}
	public void setMEETS_CDC_ELIG_ID(String mEETS_CDC_ELIG_ID) {
		MEETS_CDC_ELIG_ID = mEETS_CDC_ELIG_ID;
	}
	public String getMEETS_CASE_DEFINITION_IN() {
		return MEETS_CASE_DEFINITION_IN;
	}
	public void setMEETS_CASE_DEFINITION_IN(String mEETS_CASE_DEFINITION_IN) {
		MEETS_CASE_DEFINITION_IN = mEETS_CASE_DEFINITION_IN;
	}
	public String getNYC_CITY_NO() {
		return NYC_CITY_NO;
	}
	public void setNYC_CITY_NO(String nYC_CITY_NO) {
		NYC_CITY_NO = nYC_CITY_NO;
	}
	public String getNYC_EHARS_STATENO() {
		return NYC_EHARS_STATENO;
	}
	public void setNYC_EHARS_STATENO(String nYC_EHARS_STATENO) {
		NYC_EHARS_STATENO = nYC_EHARS_STATENO;
	}
	public String getNYS_EHARS_STATENO() {
		return NYS_EHARS_STATENO;
	}
	public void setNYS_EHARS_STATENO(String nYS_EHARS_STATENO) {
		NYS_EHARS_STATENO = nYS_EHARS_STATENO;
	}
	public String getNYEHMS_STATENO() {
		return NYEHMS_STATENO;
	}
	public void setNYEHMS_STATENO(String nYEHMS_STATENO) {
		NYEHMS_STATENO = nYEHMS_STATENO;
	}
	public String getINTERNAL_CONCLUSION_DESC() {
		return INTERNAL_CONCLUSION_DESC;
	}
	public void setINTERNAL_CONCLUSION_DESC(String iNTERNAL_CONCLUSION_DESC) {
		INTERNAL_CONCLUSION_DESC = iNTERNAL_CONCLUSION_DESC;
	}
	public String getCONCLUSION_DESC() {
		return CONCLUSION_DESC;
	}
	public void setCONCLUSION_DESC(String cONCLUSION_DESC) {
		CONCLUSION_DESC = cONCLUSION_DESC;
	}
	public String getFILE_TYPE_CD() {
		return FILE_TYPE_CD;
	}
	public void setFILE_TYPE_CD(String fILE_TYPE_CD) {
		FILE_TYPE_CD = fILE_TYPE_CD;
	}
	
	
	
	
}
