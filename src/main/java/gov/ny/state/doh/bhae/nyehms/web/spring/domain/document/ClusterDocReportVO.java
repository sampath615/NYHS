package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

public class ClusterDocReportVO  {
	
	private Integer DOCUMENT_ID;
	private String CLUSTER_ID;
	private String CLUSTER_TYPE;
	private String CLUSTER_DATE;
	private String CLUSTER_START_DATE;
	private String CLUSTER_END_DATE;
	private String REFERENCE_FILE_NAME;
	private String STATENO;
	private String NYS_HIV_DX_DT;		
	private String STATUS_FLAG;
	private String CITY_NO;				
	private String NYC_HIV_DX_DT;		
	private String RECENT_PS_MESSAGE_SENT_DT;
	private String PS_DISPO_DESC;
	private String RECENT_SURV_ASSIGN_DT;
	private String SURV_ClOSURE_MESSAGE; // Detail message not only closed
	private String BHAE_TS_OUT_OF_TIME_FRAME;
	
	private String NYS_AIDS_DX_DT;	
	private String NYC_AIDS_DX_DT;	
	private String DOB;
	private String RACE;
	private String ETHNICITY;
	private String BIRTH_SEX;
	private String VITAL_STATUS;
	private String RSH_COUNTY;
	private String HF_COUNTY;
	private String CUR_COUNTY;
	private String COLLECTION_DATE;
	private String TEST_RESULT;
	
	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getCLUSTER_ID() {
		return CLUSTER_ID;
	}
	public void setCLUSTER_ID(String cLUSTER_ID) {
		CLUSTER_ID = cLUSTER_ID;
	}
	public String getCLUSTER_TYPE() {
		return CLUSTER_TYPE;
	}
	public void setCLUSTER_TYPE(String cLUSTER_TYPE) {
		CLUSTER_TYPE = cLUSTER_TYPE;
	}
	public String getCLUSTER_DATE() {
		return CLUSTER_DATE;
	}
	public void setCLUSTER_DATE(String cLUSTER_DATE) {
		CLUSTER_DATE = cLUSTER_DATE;
	}
	public String getCLUSTER_START_DATE() {
		return CLUSTER_START_DATE;
	}
	public void setCLUSTER_START_DATE(String cLUSTER_START_DATE) {
		CLUSTER_START_DATE = cLUSTER_START_DATE;
	}
	public String getCLUSTER_END_DATE() {
		return CLUSTER_END_DATE;
	}
	public void setCLUSTER_END_DATE(String cLUSTER_END_DATE) {
		CLUSTER_END_DATE = cLUSTER_END_DATE;
	}
	public String getREFERENCE_FILE_NAME() {
		return REFERENCE_FILE_NAME;
	}
	public void setREFERENCE_FILE_NAME(String rEFERENCE_FILE_NAME) {
		REFERENCE_FILE_NAME = rEFERENCE_FILE_NAME;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getNYS_HIV_DX_DT() {
		return NYS_HIV_DX_DT;
	}
	public void setNYS_HIV_DX_DT(String nYS_HIV_DX_DT) {
		NYS_HIV_DX_DT = nYS_HIV_DX_DT;
	}
	public String getSTATUS_FLAG() {
		return STATUS_FLAG;
	}
	public void setSTATUS_FLAG(String sTATUS_FLAG) {
		STATUS_FLAG = sTATUS_FLAG;
	}
	public String getCITY_NO() {
		return CITY_NO;
	}
	public void setCITY_NO(String cITY_NO) {
		CITY_NO = cITY_NO;
	}
	public String getNYC_HIV_DX_DT() {
		return NYC_HIV_DX_DT;
	}
	public void setNYC_HIV_DX_DT(String nYC_HIV_DX_DT) {
		NYC_HIV_DX_DT = nYC_HIV_DX_DT;
	}
	public String getRECENT_PS_MESSAGE_SENT_DT() {
		return RECENT_PS_MESSAGE_SENT_DT;
	}
	public void setRECENT_PS_MESSAGE_SENT_DT(String rECENT_PS_MESSAGE_SENT_DT) {
		RECENT_PS_MESSAGE_SENT_DT = rECENT_PS_MESSAGE_SENT_DT;
	}
	public String getPS_DISPO_DESC() {
		return PS_DISPO_DESC;
	}
	public void setPS_DISPO_DESC(String pS_DISPO_DESC) {
		PS_DISPO_DESC = pS_DISPO_DESC;
	}
	public String getRECENT_SURV_ASSIGN_DT() {
		return RECENT_SURV_ASSIGN_DT;
	}
	public void setRECENT_SURV_ASSIGN_DT(String rECENT_SURV_ASSIGN_DT) {
		RECENT_SURV_ASSIGN_DT = rECENT_SURV_ASSIGN_DT;
	}
	public String getSURV_ClOSURE_MESSAGE() {
		return SURV_ClOSURE_MESSAGE;
	}
	public void setSURV_ClOSURE_MESSAGE(String sURV_ClOSURE_MESSAGE) {
		SURV_ClOSURE_MESSAGE = sURV_ClOSURE_MESSAGE;
	}
	public String getBHAE_TS_OUT_OF_TIME_FRAME() {
		return BHAE_TS_OUT_OF_TIME_FRAME;
	}
	public void setBHAE_TS_OUT_OF_TIME_FRAME(String bHAE_TS_OUT_OF_TIME_FRAME) {
		BHAE_TS_OUT_OF_TIME_FRAME = bHAE_TS_OUT_OF_TIME_FRAME;
	}
	public String getNYS_AIDS_DX_DT() {
		return NYS_AIDS_DX_DT;
	}
	public void setNYS_AIDS_DX_DT(String nYS_AIDS_DX_DT) {
		NYS_AIDS_DX_DT = nYS_AIDS_DX_DT;
	}
	public String getNYC_AIDS_DX_DT() {
		return NYC_AIDS_DX_DT;
	}
	public void setNYC_AIDS_DX_DT(String nYC_AIDS_DX_DT) {
		NYC_AIDS_DX_DT = nYC_AIDS_DX_DT;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getRACE() {
		return RACE;
	}
	public void setRACE(String rACE) {
		RACE = rACE;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}
	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}
	public String getVITAL_STATUS() {
		return VITAL_STATUS;
	}
	public void setVITAL_STATUS(String vITAL_STATUS) {
		VITAL_STATUS = vITAL_STATUS;
	}
	public String getRSH_COUNTY() {
		return RSH_COUNTY;
	}
	public void setRSH_COUNTY(String rSH_COUNTY) {
		RSH_COUNTY = rSH_COUNTY;
	}
	public String getHF_COUNTY() {
		return HF_COUNTY;
	}
	public void setHF_COUNTY(String hF_COUNTY) {
		HF_COUNTY = hF_COUNTY;
	}
	public String getCUR_COUNTY() {
		return CUR_COUNTY;
	}
	public void setCUR_COUNTY(String cUR_COUNTY) {
		CUR_COUNTY = cUR_COUNTY;
	}
	public String getCOLLECTION_DATE() {
		return COLLECTION_DATE;
	}
	public void setCOLLECTION_DATE(String cOLLECTION_DATE) {
		COLLECTION_DATE = cOLLECTION_DATE;
	}
	public String getTEST_RESULT() {
		return TEST_RESULT;
	}
	public void setTEST_RESULT(String tEST_RESULT) {
		TEST_RESULT = tEST_RESULT;
	}
	
	
}
