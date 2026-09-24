package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.ArrayList;
import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfRidrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaFollowupDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaProviderFollowupVO;

public class RiaDocVO extends NYEHMSDocVO {
	private Integer DOCUMENT_ID;
	private Integer CDESSCASEKEY;
	private Integer LAB_DOC_ID;
	private String DEATH_SOURCE;
	
	private String SSN;
	private String BHAE_NOTES;
	private String PS_NOTES;
	private String OMD_NOTES;
	private String PERSON_CITY;
	private String NYS_DISEASE_CATEG_DX;
	private String NYC_DISEASE_CATEG_DX;
	private String PERSON_ZIP;
	private String INITIAL_PROV_ZIP;
	private String RECENT_VL_UNITS;
	private String RECENT_VL_PROV_ZIP;
	private String RECENT_CD4_UNITS;
	private String FIRST_NAME;
	private String LAST_NAME;
	private String INITIAL_PROV_CITY;
	private String RECENT_VL_PROV_CITY;
	private String BIRTH_COUNTRY;
	private String ETHNICITY;
	private String PERSON_COUNTY;
	private String INITIAL_PROV_COUNTY;
	private String INITIAL_FACILITY_COUNTY;
	private String BIRTH_SEX;
	private String CURRENT_GENDER;
	private String PERSON_STATE;
	private String INITIAL_PROV_STATE;
	private String RECENT_VL_PROV_STATE;
	private String PERSON_ADDRESS;
	private String INITIAL_PROVNAME;
	private String INITIAL_PROV_ADDRESS;
	private String RECENT_VL_PROVNAME;
	private String RECENT_VL_PROV_ADDRESS;
	private String AIRS_FACILITY;
	private String AIRS_TC_ID;
	private String PS_CLOSURE_DISPO;
	private String NYEHMS_STATENO;
	private String RIA_ELIGIBILITY_UID;
	private String DOB_TEXT;
	private String DOD_TEXT;
	private String RECENT_VL_VALUE;
	private String FIRST_VL_DATE;
	private String FIRST_VL_UNITS;
	private String FIRST_VL_VALUE;
	private String RECENT_CD4_VALUE;
	private String NYS_EHARS_HIV_DX_DT;
	private String NYC_EHARS_HIV_DX_DT;
	private String CALC_EHARS_HIV_DX_DT;
	private String NYS_LAST_NEG_DT;
	private String CANDLUNG_DATE_TEXT;
	private String CANDE_DATE_TEXT;
	private String CERVCANC_DATE_TEXT;
	private String COCCI_DATE_TEXT;
	private String CRYPTOCO_DATE_TEXT;
	private String CRYPTOSP_DATE_TEXT;
	private String CMV_DATE_TEXT;
	private String CMVRET_DATE_TEXT;
	private String HIVNCEPH_DATE_TEXT;
	private String HERPES_DATE_TEXT;
	private String HISTO_DATE_TEXT;
	private String ISO_DATE_TEXT;
	private String KS_DATE_TEXT;
	private String BURKL_DATE_TEXT;
	private String LYMPHIM_DATE_TEXT;
	private String LYMPHB_DATE_TEXT;
	private String MYCOAV_DATE_TEXT;
	private String MYCOPULM_DATE_TEXT;
	private String MYCODISM_DATE_TEXT;
	private String MYCOTH_DATE_TEXT;
	private String PCP_DATE_TEXT;
	private String PNEUM_DATE_TEXT;
	private String PML_DATE_TEXT;
	private String SALMNELA_DATE_TEXT;
	private String TOXO_DATE_TEXT;
	private String LIP_DATE_TEXT;
	private String WASTING_DATE_TEXT;
	private String ARV_BEGIN_DATE;
//	private String ARV_END_DATE;
	private String PS_WORKER_ASSIGNED;
	private String DISNUMBER;
	private String RIA_ELIGIBLE;
	private String RACE_ASIAN;
	private String RACE_BLACK;
	private String RACE_INDIAN;
	private String RACE_PACIFIC;
	private String RACE_WHITE;
	private String EHARS_ELIG;
	private String AHI_FLAG;
	private String AIRS_DOC;
	private String RECORD_SEARCH;
	//private String RIDR_DUPLICATE_STATUS;
	private String ARVS_TAKEN;
	private String PREP_TAKEN;
	private String NO_PREVIOUS_TEST;
	private String PKP_FLAG;
	private String RECV_CARE_OOS;
	private String RECV_CARE_STATE_CD;
	private String OOS_FACILITY_NAME;
	private String PREP_ADHERENCE;
	private String PREP_NON_ADHERENCE_REASON;
	private String PT_ON_PREP_FLAG;
	private String NON_PROMPT_FLAG;
	private String FEDERAL_FACILITY;
	private String DECEASED;
	private String NDVL_AT_DX;
	private String PERINATAL;
	private String NOT_HIV_FLAG;
	private String OOJ_CASE_FLAG;
	private String OOJ_PROVIDER_FLAG;
	
	
	private String DIAGNOSIS_SETTING_TYPE;
	private String DIAGNOSIS_FACILITY;
	private String ARV_MEDICATION;
	private String TREATMENT_SOURCE;
	private String BARRIERS_TO_CARE_OTHER_TEXT;
	private String SOUNDEX_CHECK_DATE;
	private String RIA_FLAGGED_DATE;
	private String RIA_ELIGIBILITY_DATE;
	private String ADDRESS_DATE;
	private String INITIAL_COLLECTION_DATE;
	private String RECENT_VL_DATE;
	private String RECENT_CD4_DATE;
	private String FIRST_GENOTYPE_DATE;
	private String FIRST_PRF_DATE;
	private String PS_MESSAGE_SENT_DATE;
	private String PS_CLOSURE_DATE;
	private String PS_LAST_UPDATE_DATE;
	private String INITIAL_APPOINTMENT_DATE;
	private String MOVED_TO_NY_DATE;
	private String MOVED_FROM_NY_DATE;
	private String RIDR_INFO;
	private Integer LAST_UPDATED_BY;
	private String HOUSING_INSTABILITY;
	private String FOOD_INSECURITY;
	private String HEALTH_LITERACY;
	private String INSURANCE_BARRIER;
	private String LANGUAGE_BARRIER;
	private String TRANSPORTATION_BARRIERS;
	private String MENTAL_HEALTH_BARRIER;
	private String SUBSTANCE_USE;
	private String INCARCERATION;
	private String COMPLETE_DATE;
	private String CURR_NYEHMS_STATENO;
	private String DEATH_SOURCE_OTHER_TEXT;
	private String DIAGNOSIS_SETTING_OTHER_TEXT;
	private String PKP_DX_COUNTRY;
	private String PKP_DX_STATE;
	private String PKP_DX_DATE;
	private String RAP_PROVIDER;
	private String RAP_PROVIDER_FNAME;
	private String RAP_PROVIDER_LNAME;
	private String RAP_PROVIDER_MNAME;
	private String TTH_RAP_PROVIDER;
	private String TTH_RAP_PROVIDER_FNAME;
	private String TTH_RAP_PROVIDER_LNAME;
	private String TTH_RAP_PROVIDER_MNAME;
	
	private String TREATMENT_SOURCE_OTHER_TEXT;
	private String ADVERSE_EFFECTS_TO_MEDS;
	private String TREATMENT_FACILITY_NAME; 
	private String TREATMENT_FACILITY_TYPE;
	private String CASE_CLOSURE_CD;
	private String TREATMENT_FAC_TYPE_OTHER_TEXT;
	private String PREP_NON_ADHER_RSN_OTHER_TEXT;
	



	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getBHAE_NOTES() {
		return BHAE_NOTES;
	}
	public void setBHAE_NOTES(String bHAE_NOTES) {
		BHAE_NOTES = bHAE_NOTES;
	}
	public String getPS_NOTES() {
		return PS_NOTES;
	}
	public void setPS_NOTES(String pS_NOTES) {
		PS_NOTES = pS_NOTES;
	}
	public String getOMD_NOTES() {
		return OMD_NOTES;
	}
	public void setOMD_NOTES(String oMD_NOTES) {
		OMD_NOTES = oMD_NOTES;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}
	public String getNYS_DISEASE_CATEG_DX() {
		return NYS_DISEASE_CATEG_DX;
	}
	public void setNYS_DISEASE_CATEG_DX(String nYS_DISEASE_CATEG_DX) {
		NYS_DISEASE_CATEG_DX = nYS_DISEASE_CATEG_DX;
	}
	public String getNYC_DISEASE_CATEG_DX() {
		return NYC_DISEASE_CATEG_DX;
	}
	public void setNYC_DISEASE_CATEG_DX(String nYC_DISEASE_CATEG_DX) {
		NYC_DISEASE_CATEG_DX = nYC_DISEASE_CATEG_DX;
	}
	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}
	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}
	public String getINITIAL_PROV_ZIP() {
		return INITIAL_PROV_ZIP;
	}
	public void setINITIAL_PROV_ZIP(String iNITIAL_PROV_ZIP) {
		INITIAL_PROV_ZIP = iNITIAL_PROV_ZIP;
	}
	public String getRECENT_VL_UNITS() {
		return RECENT_VL_UNITS;
	}
	public void setRECENT_VL_UNITS(String rECENT_VL_UNITS) {
		RECENT_VL_UNITS = rECENT_VL_UNITS;
	}
	public String getRECENT_VL_PROV_ZIP() {
		return RECENT_VL_PROV_ZIP;
	}
	public void setRECENT_VL_PROV_ZIP(String rECENT_VL_PROV_ZIP) {
		RECENT_VL_PROV_ZIP = rECENT_VL_PROV_ZIP;
	}
	public String getRECENT_CD4_UNITS() {
		return RECENT_CD4_UNITS;
	}
	public void setRECENT_CD4_UNITS(String rECENT_CD4_UNITS) {
		RECENT_CD4_UNITS = rECENT_CD4_UNITS;
	}
	public String getFIRST_NAME() {
		return FIRST_NAME;
	}
	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}
	public String getLAST_NAME() {
		return LAST_NAME;
	}
	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}
	public String getINITIAL_PROV_CITY() {
		return INITIAL_PROV_CITY;
	}
	public void setINITIAL_PROV_CITY(String iNITIAL_PROV_CITY) {
		INITIAL_PROV_CITY = iNITIAL_PROV_CITY;
	}
	public String getRECENT_VL_PROV_CITY() {
		return RECENT_VL_PROV_CITY;
	}
	public void setRECENT_VL_PROV_CITY(String rECENT_VL_PROV_CITY) {
		RECENT_VL_PROV_CITY = rECENT_VL_PROV_CITY;
	}
	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}
	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}
	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}
	public String getINITIAL_PROV_COUNTY() {
		return INITIAL_PROV_COUNTY;
	}
	public void setINITIAL_PROV_COUNTY(String iNITIAL_PROV_COUNTY) {
		INITIAL_PROV_COUNTY = iNITIAL_PROV_COUNTY;
	}
	public String getINITIAL_FACILITY_COUNTY() {
		return INITIAL_FACILITY_COUNTY;
	}
	public void setINITIAL_FACILITY_COUNTY(String iNITIAL_FACILITY_COUNTY) {
		INITIAL_FACILITY_COUNTY = iNITIAL_FACILITY_COUNTY;
	}
	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}
	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}
	public String getINITIAL_PROV_STATE() {
		return INITIAL_PROV_STATE;
	}
	public void setINITIAL_PROV_STATE(String iNITIAL_PROV_STATE) {
		INITIAL_PROV_STATE = iNITIAL_PROV_STATE;
	}
	public String getRECENT_VL_PROV_STATE() {
		return RECENT_VL_PROV_STATE;
	}
	public void setRECENT_VL_PROV_STATE(String rECENT_VL_PROV_STATE) {
		RECENT_VL_PROV_STATE = rECENT_VL_PROV_STATE;
	}
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}
	public String getINITIAL_PROVNAME() {
		return INITIAL_PROVNAME;
	}
	public void setINITIAL_PROVNAME(String iNITIAL_PROVNAME) {
		INITIAL_PROVNAME = iNITIAL_PROVNAME;
	}
	public String getINITIAL_PROV_ADDRESS() {
		return INITIAL_PROV_ADDRESS;
	}
	public void setINITIAL_PROV_ADDRESS(String iNITIAL_PROV_ADDRESS) {
		INITIAL_PROV_ADDRESS = iNITIAL_PROV_ADDRESS;
	}
	public String getRECENT_VL_PROVNAME() {
		return RECENT_VL_PROVNAME;
	}
	public void setRECENT_VL_PROVNAME(String rECENT_VL_PROVNAME) {
		RECENT_VL_PROVNAME = rECENT_VL_PROVNAME;
	}
	public String getRECENT_VL_PROV_ADDRESS() {
		return RECENT_VL_PROV_ADDRESS;
	}
	public void setRECENT_VL_PROV_ADDRESS(String rECENT_VL_PROV_ADDRESS) {
		RECENT_VL_PROV_ADDRESS = rECENT_VL_PROV_ADDRESS;
	}
	public String getAIRS_FACILITY() {
		return AIRS_FACILITY;
	}
	public void setAIRS_FACILITY(String aIRS_FACILITY) {
		AIRS_FACILITY = aIRS_FACILITY;
	}
	
	public String getAIRS_TC_ID() {
		return AIRS_TC_ID;
	}
	public void setAIRS_TC_ID(String aIRS_TC_ID) {
		AIRS_TC_ID = aIRS_TC_ID;
	}
	public String getPS_CLOSURE_DISPO() {
		return PS_CLOSURE_DISPO;
	}
	public void setPS_CLOSURE_DISPO(String pS_CLOSURE_DISPO) {
		PS_CLOSURE_DISPO = pS_CLOSURE_DISPO;
	}
	public String getNYEHMS_STATENO() {
		return NYEHMS_STATENO;
	}
	public void setNYEHMS_STATENO(String nYEHMS_STATENO) {
		NYEHMS_STATENO = nYEHMS_STATENO;
	}
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public String getDOD_TEXT() {
		return DOD_TEXT;
	}
	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = defaultDateValidation(dOD_TEXT);
	}
	public String getRECENT_VL_VALUE() {
		return RECENT_VL_VALUE;
	}
	public void setRECENT_VL_VALUE(String rECENT_VL_VALUE) {
		RECENT_VL_VALUE = rECENT_VL_VALUE;
	}
	public String getRECENT_CD4_VALUE() {
		return RECENT_CD4_VALUE;
	}
	public void setRECENT_CD4_VALUE(String rECENT_CD4_VALUE) {
		RECENT_CD4_VALUE = rECENT_CD4_VALUE;
	}
	public String getNYS_EHARS_HIV_DX_DT() {
		return NYS_EHARS_HIV_DX_DT;
	}
	public void setNYS_EHARS_HIV_DX_DT(String nYS_EHARS_HIV_DX_DT) {
		NYS_EHARS_HIV_DX_DT = defaultDateValidation(nYS_EHARS_HIV_DX_DT);
	}
	public String getNYC_EHARS_HIV_DX_DT() {
		return NYC_EHARS_HIV_DX_DT;
	}
	public void setNYC_EHARS_HIV_DX_DT(String nYC_EHARS_HIV_DX_DT) {
		NYC_EHARS_HIV_DX_DT = defaultDateValidation(nYC_EHARS_HIV_DX_DT);
	}
	public String getCALC_EHARS_HIV_DX_DT() {
		return CALC_EHARS_HIV_DX_DT;
	}
	public void setCALC_EHARS_HIV_DX_DT(String cALC_EHARS_HIV_DX_DT) {
		CALC_EHARS_HIV_DX_DT = defaultDateValidation(cALC_EHARS_HIV_DX_DT);
	}
	public String getNYS_LAST_NEG_DT() {
		return NYS_LAST_NEG_DT;
	}
	public void setNYS_LAST_NEG_DT(String nYS_LAST_NEG_DT) {
		NYS_LAST_NEG_DT = defaultDateValidation(nYS_LAST_NEG_DT);
	}
	public String getCANDLUNG_DATE_TEXT() {
		return CANDLUNG_DATE_TEXT;
	}
	public void setCANDLUNG_DATE_TEXT(String cANDLUNG_DATE_TEXT) {
		CANDLUNG_DATE_TEXT = defaultDateValidation(cANDLUNG_DATE_TEXT);
	}
	public String getCANDE_DATE_TEXT() {
		return CANDE_DATE_TEXT;
	}
	public void setCANDE_DATE_TEXT(String cANDE_DATE_TEXT) {
		CANDE_DATE_TEXT = defaultDateValidation(cANDE_DATE_TEXT);
	}
	public String getCERVCANC_DATE_TEXT() {
		return CERVCANC_DATE_TEXT;
	}
	public void setCERVCANC_DATE_TEXT(String cERVCANC_DATE_TEXT) {
		CERVCANC_DATE_TEXT = defaultDateValidation(cERVCANC_DATE_TEXT);
	}
	public String getCOCCI_DATE_TEXT() {
		return COCCI_DATE_TEXT;
	}
	public void setCOCCI_DATE_TEXT(String cOCCI_DATE_TEXT) {
		COCCI_DATE_TEXT = defaultDateValidation(cOCCI_DATE_TEXT);
	}
	public String getCRYPTOCO_DATE_TEXT() {
		return CRYPTOCO_DATE_TEXT;
	}
	public void setCRYPTOCO_DATE_TEXT(String cRYPTOCO_DATE_TEXT) {
		CRYPTOCO_DATE_TEXT = defaultDateValidation(cRYPTOCO_DATE_TEXT);
	}
	public String getCRYPTOSP_DATE_TEXT() {
		return CRYPTOSP_DATE_TEXT;
	}
	public void setCRYPTOSP_DATE_TEXT(String cRYPTOSP_DATE_TEXT) {
		CRYPTOSP_DATE_TEXT = defaultDateValidation(cRYPTOSP_DATE_TEXT);
	}
	public String getCMV_DATE_TEXT() {
		return CMV_DATE_TEXT;
	}
	public void setCMV_DATE_TEXT(String cMV_DATE_TEXT) {
		CMV_DATE_TEXT = defaultDateValidation(cMV_DATE_TEXT);
	}
	public String getCMVRET_DATE_TEXT() {
		return CMVRET_DATE_TEXT;
	}
	public void setCMVRET_DATE_TEXT(String cMVRET_DATE_TEXT) {
		CMVRET_DATE_TEXT = defaultDateValidation(cMVRET_DATE_TEXT);
	}
	public String getHIVNCEPH_DATE_TEXT() {
		return HIVNCEPH_DATE_TEXT;
	}
	public void setHIVNCEPH_DATE_TEXT(String hIVNCEPH_DATE_TEXT) {
		HIVNCEPH_DATE_TEXT = defaultDateValidation(hIVNCEPH_DATE_TEXT);
	}
	public String getHERPES_DATE_TEXT() {
		return HERPES_DATE_TEXT;
	}
	public void setHERPES_DATE_TEXT(String hERPES_DATE_TEXT) {
		HERPES_DATE_TEXT = defaultDateValidation(hERPES_DATE_TEXT);
	}
	public String getHISTO_DATE_TEXT() {
		return HISTO_DATE_TEXT;
	}
	public void setHISTO_DATE_TEXT(String hISTO_DATE_TEXT) {
		HISTO_DATE_TEXT = defaultDateValidation(hISTO_DATE_TEXT);
	}
	public String getISO_DATE_TEXT() {
		return ISO_DATE_TEXT;
	}
	public void setISO_DATE_TEXT(String iSO_DATE_TEXT) {
		ISO_DATE_TEXT = defaultDateValidation(iSO_DATE_TEXT);
	}
	public String getKS_DATE_TEXT() {
		return KS_DATE_TEXT;
	}
	public void setKS_DATE_TEXT(String kS_DATE_TEXT) {
		KS_DATE_TEXT = defaultDateValidation(kS_DATE_TEXT);
	}
	public String getBURKL_DATE_TEXT() {
		return BURKL_DATE_TEXT;
	}
	public void setBURKL_DATE_TEXT(String bURKL_DATE_TEXT) {
		BURKL_DATE_TEXT = defaultDateValidation(bURKL_DATE_TEXT);
	}
	public String getLYMPHIM_DATE_TEXT() {
		return LYMPHIM_DATE_TEXT;
	}
	public void setLYMPHIM_DATE_TEXT(String lYMPHIM_DATE_TEXT) {
		LYMPHIM_DATE_TEXT = defaultDateValidation(lYMPHIM_DATE_TEXT);
	}
	public String getLYMPHB_DATE_TEXT() {
		return LYMPHB_DATE_TEXT;
	}
	public void setLYMPHB_DATE_TEXT(String lYMPHB_DATE_TEXT) {
		LYMPHB_DATE_TEXT = defaultDateValidation(lYMPHB_DATE_TEXT);
	}
	public String getMYCOAV_DATE_TEXT() {
		return MYCOAV_DATE_TEXT;
	}
	public void setMYCOAV_DATE_TEXT(String mYCOAV_DATE_TEXT) {
		MYCOAV_DATE_TEXT = defaultDateValidation(mYCOAV_DATE_TEXT);
	}
	public String getMYCOPULM_DATE_TEXT() {
		return MYCOPULM_DATE_TEXT;
	}
	public void setMYCOPULM_DATE_TEXT(String mYCOPULM_DATE_TEXT) {
		MYCOPULM_DATE_TEXT = defaultDateValidation(mYCOPULM_DATE_TEXT);
	}
	public String getMYCODISM_DATE_TEXT() {
		return MYCODISM_DATE_TEXT;
	}
	public void setMYCODISM_DATE_TEXT(String mYCODISM_DATE_TEXT) {
		MYCODISM_DATE_TEXT = defaultDateValidation(mYCODISM_DATE_TEXT);
	}
	public String getMYCOTH_DATE_TEXT() {
		return MYCOTH_DATE_TEXT;
	}
	public void setMYCOTH_DATE_TEXT(String mYCOTH_DATE_TEXT) {
		MYCOTH_DATE_TEXT = defaultDateValidation(mYCOTH_DATE_TEXT);
	}
	public String getPCP_DATE_TEXT() {
		return PCP_DATE_TEXT;
	}
	public void setPCP_DATE_TEXT(String pCP_DATE_TEXT) {
		PCP_DATE_TEXT = defaultDateValidation(pCP_DATE_TEXT);
	}
	public String getPNEUM_DATE_TEXT() {
		return PNEUM_DATE_TEXT;
	}
	public void setPNEUM_DATE_TEXT(String pNEUM_DATE_TEXT) {
		PNEUM_DATE_TEXT = defaultDateValidation(pNEUM_DATE_TEXT);
	}
	public String getPML_DATE_TEXT() {
		return PML_DATE_TEXT;
	}
	public void setPML_DATE_TEXT(String pML_DATE_TEXT) {
		PML_DATE_TEXT = defaultDateValidation(pML_DATE_TEXT);
	}
	public String getSALMNELA_DATE_TEXT() {
		return SALMNELA_DATE_TEXT;
	}
	public void setSALMNELA_DATE_TEXT(String sALMNELA_DATE_TEXT) {
		SALMNELA_DATE_TEXT = defaultDateValidation(sALMNELA_DATE_TEXT);
	}
	public String getTOXO_DATE_TEXT() {
		return TOXO_DATE_TEXT;
	}
	public void setTOXO_DATE_TEXT(String tOXO_DATE_TEXT) {
		TOXO_DATE_TEXT = defaultDateValidation(tOXO_DATE_TEXT);
	}
	public String getWASTING_DATE_TEXT() {
		return WASTING_DATE_TEXT;
	}
	public void setWASTING_DATE_TEXT(String wASTING_DATE_TEXT) {
		WASTING_DATE_TEXT = defaultDateValidation(wASTING_DATE_TEXT);
	}
	public String getARV_BEGIN_DATE() {
		return ARV_BEGIN_DATE;
	}
	public void setARV_BEGIN_DATE(String aRV_BEGIN_DATE) {
		ARV_BEGIN_DATE = defaultDateValidation(aRV_BEGIN_DATE);
	}
	public String getPS_WORKER_ASSIGNED() {
		return PS_WORKER_ASSIGNED;
	}
	public void setPS_WORKER_ASSIGNED(String pS_WORKER_ASSIGNED) {
		PS_WORKER_ASSIGNED = pS_WORKER_ASSIGNED;
	}
	public String getDISNUMBER() {
		return DISNUMBER;
	}
	public void setDISNUMBER(String dISNUMBER) {
		DISNUMBER = dISNUMBER;
	}
	public String getRIA_ELIGIBLE() {
		return RIA_ELIGIBLE;
	}
	public void setRIA_ELIGIBLE(String rIA_ELIGIBLE) {
		RIA_ELIGIBLE = rIA_ELIGIBLE;
	}
	public String getRACE_ASIAN() {
		return RACE_ASIAN;
	}
	public void setRACE_ASIAN(String rACE_ASIAN) {
		RACE_ASIAN = rACE_ASIAN;
	}
	public String getRACE_BLACK() {
		return RACE_BLACK;
	}
	public void setRACE_BLACK(String rACE_BLACK) {
		RACE_BLACK = rACE_BLACK;
	}
	public String getRACE_INDIAN() {
		return RACE_INDIAN;
	}
	public void setRACE_INDIAN(String rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
	}
	public String getRACE_PACIFIC() {
		return RACE_PACIFIC;
	}
	public void setRACE_PACIFIC(String rACE_PACIFIC) {
		RACE_PACIFIC = rACE_PACIFIC;
	}
	public String getRACE_WHITE() {
		return RACE_WHITE;
	}
	public void setRACE_WHITE(String rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
	}
	public String getEHARS_ELIG() {
		return EHARS_ELIG;
	}
	public void setEHARS_ELIG(String eHARS_ELIG) {
		EHARS_ELIG = eHARS_ELIG;
	}
	public String getAHI_FLAG() {
		return AHI_FLAG;
	}
	public void setAHI_FLAG(String aHI_FLAG) {
		AHI_FLAG = aHI_FLAG;
	}
	public String getAIRS_DOC() {
		return AIRS_DOC;
	}
	public void setAIRS_DOC(String aIRS_DOC) {
		AIRS_DOC = aIRS_DOC;
	}
	public String getRECORD_SEARCH() {
		return RECORD_SEARCH;
	}
	public void setRECORD_SEARCH(String rECORD_SEARCH) {
		RECORD_SEARCH = rECORD_SEARCH;
	}
	public String getARVS_TAKEN() {
		return ARVS_TAKEN;
	}
	public void setARVS_TAKEN(String aRVS_TAKEN) {
		ARVS_TAKEN = aRVS_TAKEN;
	}
	public String getPREP_TAKEN() {
		return PREP_TAKEN;
	}
	public void setPREP_TAKEN(String pREP_TAKEN) {
		PREP_TAKEN = pREP_TAKEN;
	}
	
	public String getNO_PREVIOUS_TEST() {
		return NO_PREVIOUS_TEST;
	}
	public void setNO_PREVIOUS_TEST(String nO_PREVIOUS_TEST) {
		NO_PREVIOUS_TEST = nO_PREVIOUS_TEST;
	}
	public String getPKP_FLAG() {
		return PKP_FLAG;
	}
	public void setPKP_FLAG(String pKP_FLAG) {
		PKP_FLAG = pKP_FLAG;
	}
	public String getRECV_CARE_OOS() {
		return RECV_CARE_OOS;
	}
	public void setRECV_CARE_OOS(String rECV_CARE_OOS) {
		RECV_CARE_OOS = rECV_CARE_OOS;
	}
	public String getNOT_HIV_FLAG() {
		return NOT_HIV_FLAG;
	}
	public void setNOT_HIV_FLAG(String nOT_HIV_FLAG) {
		NOT_HIV_FLAG = nOT_HIV_FLAG;
	}
	public String getPT_ON_PREP_FLAG() {
		return PT_ON_PREP_FLAG;
	}
	public void setPT_ON_PREP_FLAG(String pT_ON_PREP_FLAG) {
		PT_ON_PREP_FLAG = pT_ON_PREP_FLAG;
	}
	public String getNON_PROMPT_FLAG() {
		return NON_PROMPT_FLAG;
	}
	public void setNON_PROMPT_FLAG(String nON_PROMPT_FLAG) {
		NON_PROMPT_FLAG = nON_PROMPT_FLAG;
	}
	public String getFEDERAL_FACILITY() {
		return FEDERAL_FACILITY;
	}
	public void setFEDERAL_FACILITY(String fEDERAL_FACILITY) {
		FEDERAL_FACILITY = fEDERAL_FACILITY;
	}
	public String getDECEASED() {
		return DECEASED;
	}
	public void setDECEASED(String dECEASED) {
		DECEASED = dECEASED;
	}
	public String getNDVL_AT_DX() {
		return NDVL_AT_DX;
	}
	public void setNDVL_AT_DX(String nDVL_AT_DX) {
		NDVL_AT_DX = nDVL_AT_DX;
	}
	
	public String getPERINATAL() {
		return PERINATAL;
	}
	public void setPERINATAL(String pERINATAL) {
		PERINATAL = pERINATAL;
	}
	public String getOOJ_CASE_FLAG() {
		return OOJ_CASE_FLAG;
	}
	public void setOOJ_CASE_FLAG(String oOJ_CASE_FLAG) {
		OOJ_CASE_FLAG = oOJ_CASE_FLAG;
	}
	public String getOOJ_PROVIDER_FLAG() {
		return OOJ_PROVIDER_FLAG;
	}
	public void setOOJ_PROVIDER_FLAG(String oOJ_PROVIDER_FLAG) {
		OOJ_PROVIDER_FLAG = oOJ_PROVIDER_FLAG;
	}
	public Integer getLAB_DOC_ID() {
		return LAB_DOC_ID;
	}
	public void setLAB_DOC_ID(Integer lAB_DOC_ID) {
		LAB_DOC_ID = lAB_DOC_ID;
	}
	public String getDEATH_SOURCE() {
		return DEATH_SOURCE;
	}
	public void setDEATH_SOURCE(String dEATH_SOURCE) {
		DEATH_SOURCE = dEATH_SOURCE;
	}
	public Integer getCDESSCASEKEY() {
		return CDESSCASEKEY;
	}
	public void setCDESSCASEKEY(Integer cDESSCASEKEY) {
		CDESSCASEKEY = cDESSCASEKEY;
	}
	public String getDIAGNOSIS_SETTING_TYPE() {
		return DIAGNOSIS_SETTING_TYPE;
	}
	public void setDIAGNOSIS_SETTING_TYPE(String dIAGNOSIS_SETTING_TYPE) {
		DIAGNOSIS_SETTING_TYPE = dIAGNOSIS_SETTING_TYPE;
	}
	public String getDIAGNOSIS_FACILITY() {
		return DIAGNOSIS_FACILITY;
	}
	public void setDIAGNOSIS_FACILITY(String dIAGNOSIS_FACILITY) {
		DIAGNOSIS_FACILITY = dIAGNOSIS_FACILITY;
	}
	public String getARV_MEDICATION() {
		return ARV_MEDICATION;
	}
	public void setARV_MEDICATION(String aRV_MEDICATION) {
		ARV_MEDICATION = aRV_MEDICATION;
	}
	public String getTREATMENT_SOURCE() {
		return TREATMENT_SOURCE;
	}
	public void setTREATMENT_SOURCE(String tREATMENT_SOURCE) {
		TREATMENT_SOURCE = tREATMENT_SOURCE;
	}
	public String getBARRIERS_TO_CARE_OTHER_TEXT() {
		return BARRIERS_TO_CARE_OTHER_TEXT;
	}
	public void setBARRIERS_TO_CARE_OTHER_TEXT(String bARRIERS_TO_CARE_OTHER_TEXT) {
		BARRIERS_TO_CARE_OTHER_TEXT = bARRIERS_TO_CARE_OTHER_TEXT;
	}
	public String getSOUNDEX_CHECK_DATE() {
		return SOUNDEX_CHECK_DATE;
	}
	public void setSOUNDEX_CHECK_DATE(String sOUNDEX_CHECK_DATE) {
		SOUNDEX_CHECK_DATE = sOUNDEX_CHECK_DATE;
	}
	public String getRIA_FLAGGED_DATE() {
		return RIA_FLAGGED_DATE;
	}
	public void setRIA_FLAGGED_DATE(String rIA_FLAGGED_DATE) {
		RIA_FLAGGED_DATE = rIA_FLAGGED_DATE;
	}
	
	public String getRIA_ELIGIBILITY_UID() {
		return RIA_ELIGIBILITY_UID;
	}
	public void setRIA_ELIGIBILITY_UID(String rIA_ELIGIBILITY_UID) {
		RIA_ELIGIBILITY_UID = rIA_ELIGIBILITY_UID;
	}
	public String getRIA_ELIGIBILITY_DATE() {
		return RIA_ELIGIBILITY_DATE;
	}
	public void setRIA_ELIGIBILITY_DATE(String rIA_ELIGIBILITY_DATE) {
		RIA_ELIGIBILITY_DATE = rIA_ELIGIBILITY_DATE;
	}
	public String getADDRESS_DATE() {
		return ADDRESS_DATE;
	}
	public void setADDRESS_DATE(String aDDRESS_DATE) {
		ADDRESS_DATE = aDDRESS_DATE;
	}
	public String getINITIAL_COLLECTION_DATE() {
		return INITIAL_COLLECTION_DATE;
	}
	public void setINITIAL_COLLECTION_DATE(String iNITIAL_COLLECTION_DATE) {
		INITIAL_COLLECTION_DATE = iNITIAL_COLLECTION_DATE;
	}
	public String getRECENT_VL_DATE() {
		return RECENT_VL_DATE;
	}
	public void setRECENT_VL_DATE(String rECENT_VL_DATE) {
		RECENT_VL_DATE = rECENT_VL_DATE;
	}
	public String getRECENT_CD4_DATE() {
		return RECENT_CD4_DATE;
	}
	public void setRECENT_CD4_DATE(String rECENT_CD4_DATE) {
		RECENT_CD4_DATE = rECENT_CD4_DATE;
	}
	public String getFIRST_GENOTYPE_DATE() {
		return FIRST_GENOTYPE_DATE;
	}
	public void setFIRST_GENOTYPE_DATE(String fIRST_GENOTYPE_DATE) {
		FIRST_GENOTYPE_DATE = fIRST_GENOTYPE_DATE;
	}
	public String getFIRST_PRF_DATE() {
		return FIRST_PRF_DATE;
	}
	public void setFIRST_PRF_DATE(String fIRST_PRF_DATE) {
		FIRST_PRF_DATE = fIRST_PRF_DATE;
	}
	public String getPS_MESSAGE_SENT_DATE() {
		return PS_MESSAGE_SENT_DATE;
	}
	public void setPS_MESSAGE_SENT_DATE(String pS_MESSAGE_SENT_DATE) {
		PS_MESSAGE_SENT_DATE = pS_MESSAGE_SENT_DATE;
	}
	public String getPS_CLOSURE_DATE() {
		return PS_CLOSURE_DATE;
	}
	public void setPS_CLOSURE_DATE(String pS_CLOSURE_DATE) {
		PS_CLOSURE_DATE = pS_CLOSURE_DATE;
	}
	public String getPS_LAST_UPDATE_DATE() {
		return PS_LAST_UPDATE_DATE;
	}
	public void setPS_LAST_UPDATE_DATE(String pS_LAST_UPDATE_DATE) {
		PS_LAST_UPDATE_DATE = pS_LAST_UPDATE_DATE;
	}
	public String getINITIAL_APPOINTMENT_DATE() {
		return INITIAL_APPOINTMENT_DATE;
	}
	public void setINITIAL_APPOINTMENT_DATE(String iNITIAL_APPOINTMENT_DATE) {
		INITIAL_APPOINTMENT_DATE = iNITIAL_APPOINTMENT_DATE;
	}
	public String getMOVED_TO_NY_DATE() {
		return MOVED_TO_NY_DATE;
	}
	public void setMOVED_TO_NY_DATE(String mOVED_TO_NY_DATE) {
		MOVED_TO_NY_DATE = mOVED_TO_NY_DATE;
	}
	public String getMOVED_FROM_NY_DATE() {
		return MOVED_FROM_NY_DATE;
	}
	public void setMOVED_FROM_NY_DATE(String mOVED_FROM_NY_DATE) {
		MOVED_FROM_NY_DATE = mOVED_FROM_NY_DATE;
	}
	public String getRIDR_INFO() {
		return RIDR_INFO;
	}
	public void setRIDR_INFO(String rIDR_INFO) {
		RIDR_INFO = rIDR_INFO;
	}
	public Integer getLAST_UPDATED_BY() {
		return LAST_UPDATED_BY;
	}
	public void setLAST_UPDATED_BY(Integer lAST_UPDATED_BY) {
		LAST_UPDATED_BY = lAST_UPDATED_BY;
	}
	public String getHOUSING_INSTABILITY() {
		return HOUSING_INSTABILITY;
	}
	public void setHOUSING_INSTABILITY(String hOUSING_INSTABILITY) {
		HOUSING_INSTABILITY = hOUSING_INSTABILITY;
	}
	public String getFOOD_INSECURITY() {
		return FOOD_INSECURITY;
	}
	public void setFOOD_INSECURITY(String fOOD_INSECURITY) {
		FOOD_INSECURITY = fOOD_INSECURITY;
	}
	public String getHEALTH_LITERACY() {
		return HEALTH_LITERACY;
	}
	public void setHEALTH_LITERACY(String hEALTH_LITERACY) {
		HEALTH_LITERACY = hEALTH_LITERACY;
	}
	public String getINSURANCE_BARRIER() {
		return INSURANCE_BARRIER;
	}
	public void setINSURANCE_BARRIER(String iNSURANCE_BARRIER) {
		INSURANCE_BARRIER = iNSURANCE_BARRIER;
	}
	
	public String getLANGUAGE_BARRIER() {
		return LANGUAGE_BARRIER;
	}
	public void setLANGUAGE_BARRIER(String lANGUAGE_BARRIER) {
		LANGUAGE_BARRIER = lANGUAGE_BARRIER;
	}
	public String getTRANSPORTATION_BARRIERS() {
		return TRANSPORTATION_BARRIERS;
	}
	public void setTRANSPORTATION_BARRIERS(String tRANSPORTATION_BARRIERS) {
		TRANSPORTATION_BARRIERS = tRANSPORTATION_BARRIERS;
	}
	public String getMENTAL_HEALTH_BARRIER() {
		return MENTAL_HEALTH_BARRIER;
	}
	public void setMENTAL_HEALTH_BARRIER(String mENTAL_HEALTH_BARRIER) {
		MENTAL_HEALTH_BARRIER = mENTAL_HEALTH_BARRIER;
	}
	public String getSUBSTANCE_USE() {
		return SUBSTANCE_USE;
	}
	public void setSUBSTANCE_USE(String sUBSTANCE_USE) {
		SUBSTANCE_USE = sUBSTANCE_USE;
	}
	public String getINCARCERATION() {
		return INCARCERATION;
	}
	public void setINCARCERATION(String iNCARCERATION) {
		INCARCERATION = iNCARCERATION;
	}
	public String getCOMPLETE_DATE() {
		return COMPLETE_DATE;
	}
	public void setCOMPLETE_DATE(String cOMPLETE_DATE) {
		COMPLETE_DATE = cOMPLETE_DATE;
	}
	public String getCURR_NYEHMS_STATENO() {
		return CURR_NYEHMS_STATENO;
	}
	public void setCURR_NYEHMS_STATENO(String cURR_NYEHMS_STATENO) {
		CURR_NYEHMS_STATENO = cURR_NYEHMS_STATENO;
	}
	public String getDEATH_SOURCE_OTHER_TEXT() {
		return DEATH_SOURCE_OTHER_TEXT;
	}
	public void setDEATH_SOURCE_OTHER_TEXT(String dEATH_SOURCE_OTHER_TEXT) {
		DEATH_SOURCE_OTHER_TEXT = dEATH_SOURCE_OTHER_TEXT;
	}
	public String getDIAGNOSIS_SETTING_OTHER_TEXT() {
		return DIAGNOSIS_SETTING_OTHER_TEXT;
	}
	public void setDIAGNOSIS_SETTING_OTHER_TEXT(String dIAGNOSIS_SETTING_OTHER_TEXT) {
		DIAGNOSIS_SETTING_OTHER_TEXT = dIAGNOSIS_SETTING_OTHER_TEXT;
	}
	public String getPKP_DX_COUNTRY() {
		return PKP_DX_COUNTRY;
	}
	public void setPKP_DX_COUNTRY(String pKP_DX_COUNTRY) {
		PKP_DX_COUNTRY = pKP_DX_COUNTRY;
	}
	public String getPKP_DX_STATE() {
		return PKP_DX_STATE;
	}
	public void setPKP_DX_STATE(String pKP_DX_STATE) {
		PKP_DX_STATE = pKP_DX_STATE;
	}
	
	public String getPKP_DX_DATE() {
		return PKP_DX_DATE;
	}
	public void setPKP_DX_DATE(String pKP_DX_DATE) {
		PKP_DX_DATE = defaultDateValidation(pKP_DX_DATE);
	}
	public String getRAP_PROVIDER() {
		return RAP_PROVIDER;
	}
	public void setRAP_PROVIDER(String rAP_PROVIDER) {
		RAP_PROVIDER = rAP_PROVIDER;
	}
	public String getRAP_PROVIDER_FNAME() {
		return RAP_PROVIDER_FNAME;
	}
	public void setRAP_PROVIDER_FNAME(String rAP_PROVIDER_FNAME) {
		RAP_PROVIDER_FNAME = rAP_PROVIDER_FNAME;
	}
	public String getRAP_PROVIDER_LNAME() {
		return RAP_PROVIDER_LNAME;
	}
	public void setRAP_PROVIDER_LNAME(String rAP_PROVIDER_LNAME) {
		RAP_PROVIDER_LNAME = rAP_PROVIDER_LNAME;
	}
	public String getRAP_PROVIDER_MNAME() {
		return RAP_PROVIDER_MNAME;
	}
	public void setRAP_PROVIDER_MNAME(String rAP_PROVIDER_MNAME) {
		RAP_PROVIDER_MNAME = rAP_PROVIDER_MNAME;
	}
	
	public String getTTH_RAP_PROVIDER() {
		return TTH_RAP_PROVIDER;
	}
	public void setTTH_RAP_PROVIDER(String tTH_RAP_PROVIDER) {
		TTH_RAP_PROVIDER = tTH_RAP_PROVIDER;
	}
	public String getTTH_RAP_PROVIDER_FNAME() {
		return TTH_RAP_PROVIDER_FNAME;
	}
	public void setTTH_RAP_PROVIDER_FNAME(String tTH_RAP_PROVIDER_FNAME) {
		TTH_RAP_PROVIDER_FNAME = tTH_RAP_PROVIDER_FNAME;
	}
	public String getTTH_RAP_PROVIDER_LNAME() {
		return TTH_RAP_PROVIDER_LNAME;
	}
	public void setTTH_RAP_PROVIDER_LNAME(String tTH_RAP_PROVIDER_LNAME) {
		TTH_RAP_PROVIDER_LNAME = tTH_RAP_PROVIDER_LNAME;
	}
	public String getTTH_RAP_PROVIDER_MNAME() {
		return TTH_RAP_PROVIDER_MNAME;
	}
	public void setTTH_RAP_PROVIDER_MNAME(String tTH_RAP_PROVIDER_MNAME) {
		TTH_RAP_PROVIDER_MNAME = tTH_RAP_PROVIDER_MNAME;
	}
	public String getTREATMENT_SOURCE_OTHER_TEXT() {
		return TREATMENT_SOURCE_OTHER_TEXT;
	}
	public void setTREATMENT_SOURCE_OTHER_TEXT(String tREATMENT_SOURCE_OTHER_TEXT) {
		TREATMENT_SOURCE_OTHER_TEXT = tREATMENT_SOURCE_OTHER_TEXT;
	}
	
	public String getFIRST_VL_DATE() {
		return FIRST_VL_DATE;
	}
	public void setFIRST_VL_DATE(String fIRST_VL_DATE) {
		FIRST_VL_DATE = fIRST_VL_DATE;
	}
	public String getFIRST_VL_UNITS() {
		return FIRST_VL_UNITS;
	}
	public void setFIRST_VL_UNITS(String fIRST_VL_UNITS) {
		FIRST_VL_UNITS = fIRST_VL_UNITS;
	}
	public String getFIRST_VL_VALUE() {
		return FIRST_VL_VALUE;
	}
	public void setFIRST_VL_VALUE(String fIRST_VL_VALUE) {
		FIRST_VL_VALUE = fIRST_VL_VALUE;
	}
	public String getLIP_DATE_TEXT() {
		return LIP_DATE_TEXT;
	}
	public void setLIP_DATE_TEXT(String lIP_DATE_TEXT) {
		LIP_DATE_TEXT = defaultDateValidation(lIP_DATE_TEXT);
	}

	public String getRECV_CARE_STATE_CD() {
		return RECV_CARE_STATE_CD;
	}
	public void setRECV_CARE_STATE_CD(String rECV_CARE_STATE_CD) {
		RECV_CARE_STATE_CD = rECV_CARE_STATE_CD;
	}
	public String getOOS_FACILITY_NAME() {
		return OOS_FACILITY_NAME;
	}
	public void setOOS_FACILITY_NAME(String oOS_FACILITY_NAME) {
		OOS_FACILITY_NAME = oOS_FACILITY_NAME;
	}
	public String getPREP_ADHERENCE() {
		return PREP_ADHERENCE;
	}
	public void setPREP_ADHERENCE(String pREP_ADHERENCE) {
		PREP_ADHERENCE = pREP_ADHERENCE;
	}
	public String getPREP_NON_ADHERENCE_REASON() {
		return PREP_NON_ADHERENCE_REASON;
	}
	public void setPREP_NON_ADHERENCE_REASON(String pREP_NON_ADHERENCE_REASON) {
		PREP_NON_ADHERENCE_REASON = pREP_NON_ADHERENCE_REASON;
	}
	public String getADVERSE_EFFECTS_TO_MEDS() {
		return ADVERSE_EFFECTS_TO_MEDS;
	}
	public void setADVERSE_EFFECTS_TO_MEDS(String aDVERSE_EFFECTS_TO_MEDS) {
		ADVERSE_EFFECTS_TO_MEDS = aDVERSE_EFFECTS_TO_MEDS;
	}
	public String getTREATMENT_FACILITY_NAME() {
		return TREATMENT_FACILITY_NAME;
	}
	public void setTREATMENT_FACILITY_NAME(String tREATMENT_FACILITY_NAME) {
		TREATMENT_FACILITY_NAME = tREATMENT_FACILITY_NAME;
	}
	public String getTREATMENT_FACILITY_TYPE() {
		return TREATMENT_FACILITY_TYPE;
	}
	public void setTREATMENT_FACILITY_TYPE(String tREATMENT_FACILITY_TYPE) {
		TREATMENT_FACILITY_TYPE = tREATMENT_FACILITY_TYPE;
	}
	public String getCASE_CLOSURE_CD() {
		return CASE_CLOSURE_CD;
	}
	public void setCASE_CLOSURE_CD(String cASE_CLOSURE_CD) {
		CASE_CLOSURE_CD = cASE_CLOSURE_CD;
	}
	public String getTREATMENT_FAC_TYPE_OTHER_TEXT() {
		return TREATMENT_FAC_TYPE_OTHER_TEXT;
	}
	public void setTREATMENT_FAC_TYPE_OTHER_TEXT(String tREATMENT_FAC_TYPE_OTHER_TEXT) {
		TREATMENT_FAC_TYPE_OTHER_TEXT = tREATMENT_FAC_TYPE_OTHER_TEXT;
	}
	public String getPREP_NON_ADHER_RSN_OTHER_TEXT() {
		return PREP_NON_ADHER_RSN_OTHER_TEXT;
	}
	public void setPREP_NON_ADHER_RSN_OTHER_TEXT(String pREP_NON_ADHER_RSN_OTHER_TEXT) {
		PREP_NON_ADHER_RSN_OTHER_TEXT = pREP_NON_ADHER_RSN_OTHER_TEXT;
	}



	private List<AcrfRidrVO> acrfRidrList = new ArrayList<AcrfRidrVO>();
	
	public List<AcrfRidrVO> getAcrfRidrList() {
		return acrfRidrList;
	}
	public void setAcrfRidrList(List<AcrfRidrVO> acrfRidrList) {
		this.acrfRidrList = acrfRidrList;
	}
	
	private List<RiaFollowupDocVO> followUpDocList = new ArrayList<RiaFollowupDocVO>();


	public List<RiaFollowupDocVO> getFollowUpDocList() {
		return followUpDocList;
	}
	public void setFollowUpDocList(List<RiaFollowupDocVO> followUpDocList) {
		this.followUpDocList = followUpDocList;
	}
	
	private final String DEFAULT_DATE = "../../....";
	private String defaultDateValidation(String _date) {
		if(DEFAULT_DATE.equals(_date)) {
			return null;
		}else {
			return _date;
		}
		
	}
	
	
	
	private List<RiaProviderFollowupVO> riaProviderFollowupList = new ArrayList<>();


	public List<RiaProviderFollowupVO> getRiaProviderFollowupList() {
		return riaProviderFollowupList;
	}
	public void setRiaProviderFollowupList(List<RiaProviderFollowupVO> riaProviderFollowupList) {
		this.riaProviderFollowupList = riaProviderFollowupList;
	}
	

}
