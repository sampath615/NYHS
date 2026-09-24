package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;
import java.util.List;


public class ClusterDocVO  {
	
	private Integer DOCUMENT_ID;
	private Integer DEMO_DOCUMENT_ID;
	private String CLUSTER_ID;
	private String CLUSTER_TYPE;
	private String CLUSTER_DATE;
	private String CLUSTER_START_DATE;
	private String CLUSTER_END_DATE;
	private String REFERENCE_FILE_NAME;
	private String EHARS_STATENO_AT_UPLOAD;
	private String NYEHMS_STATENO_AT_UPLOAD;
	private String FIRST_NAME;
	private String MIDDLE_NAME;
	private String LAST_NAME;
	private String DOB;
	private String CREATE_DATE;	
	private String CURRENT_NYEHMS_STATENO;
	private String RSH_COUNTY_FIPS;
	private String RSH_STATE_CD;
	private String CUR_COUNTY_FIPS;
	private String CUR_STATE_CD;
	private String STATENO;
	private String HIV_DX_DT;
	private String AIDS_DX_DT;
	private String HIV_AIDS_CDC_ELIG;
	private String STATUS_FLAG;
	private int PARENT_DOCUMENT_ID;
	private String CLUSTER_TYPE_DESC;
	private String IS_RES_RECORD;
	private String DEMO_DATA_SOURCE;
	private List<ClusterDocPSMessageVO> clusterDocPSMessageList;
	private String UPLOAD_NYEHMS_STATENO;
	private String CITYNO;
	private String RACE;
	private String ETHNICITY;
	private String ADDED_BY_USER;
	private String NYC_RSH_COUNTY_FIPS;
	private String NYC_RSH_STATE_CD;
	private String NYC_CUR_COUNTY_FIPS;
	private String NYC_CUR_STATE_CD;	
	private String NYC_HIV_DX_DT;
	private String NYC_AIDS_DX_DT;
	private String NYC_HIV_AIDS_CDC_ELIG;
	private String NYC_STATUS_FLAG;
	private String NYC_RACE;
	private String NYC_ETHNICITY;	
	private String IDENTIFICATION_METHOD;
	private String IDENTIFICATION_DATE;
	private String DX_START_DATE;
	private String DX_END_DATE;
	private String CLUSTER_COUNTIES;
	private String CLUSTER_DESC;
	private String RELATEDNESS;
	private String TIMEFRAME;
	private String INACTIVE_FLAG;
	private String INACTIVE_DATE;
	private String EHARS_STATENO;
	private Date EHARS_IMPORT_DATE;
	
	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public Integer getDEMO_DOCUMENT_ID() {
		return DEMO_DOCUMENT_ID;
	}
	public void setDEMO_DOCUMENT_ID(Integer dEMO_DOCUMENT_ID) {
		DEMO_DOCUMENT_ID = dEMO_DOCUMENT_ID;
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
	public String getEHARS_STATENO_AT_UPLOAD() {
		return EHARS_STATENO_AT_UPLOAD;
	}
	public void setEHARS_STATENO_AT_UPLOAD(String eHARS_STATENO_AT_UPLOAD) {
		EHARS_STATENO_AT_UPLOAD = eHARS_STATENO_AT_UPLOAD;
	}
	public String getNYEHMS_STATENO_AT_UPLOAD() {
		return NYEHMS_STATENO_AT_UPLOAD;
	}
	public void setNYEHMS_STATENO_AT_UPLOAD(String nYEHMS_STATENO_AT_UPLOAD) {
		NYEHMS_STATENO_AT_UPLOAD = nYEHMS_STATENO_AT_UPLOAD;
	}
	public String getFIRST_NAME() {
		return FIRST_NAME;
	}
	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}
	public String getMIDDLE_NAME() {
		return MIDDLE_NAME;
	}
	public void setMIDDLE_NAME(String mIDDLE_NAME) {
		MIDDLE_NAME = mIDDLE_NAME;
	}
	public String getLAST_NAME() {
		return LAST_NAME;
	}
	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getCURRENT_NYEHMS_STATENO() {
		return CURRENT_NYEHMS_STATENO;
	}
	public void setCURRENT_NYEHMS_STATENO(String cURRENT_NYEHMS_STATENO) {
		CURRENT_NYEHMS_STATENO = cURRENT_NYEHMS_STATENO;
	}
	public String getRSH_COUNTY_FIPS() {
		return RSH_COUNTY_FIPS;
	}
	public void setRSH_COUNTY_FIPS(String rSH_COUNTY_FIPS) {
		RSH_COUNTY_FIPS = rSH_COUNTY_FIPS;
	}
	public String getRSH_STATE_CD() {
		return RSH_STATE_CD;
	}
	public void setRSH_STATE_CD(String rSH_STATE_CD) {
		RSH_STATE_CD = rSH_STATE_CD;
	}
	public String getCUR_COUNTY_FIPS() {
		return CUR_COUNTY_FIPS;
	}
	public void setCUR_COUNTY_FIPS(String cUR_COUNTY_FIPS) {
		CUR_COUNTY_FIPS = cUR_COUNTY_FIPS;
	}
	public String getCUR_STATE_CD() {
		return CUR_STATE_CD;
	}
	public void setCUR_STATE_CD(String cUR_STATE_CD) {
		CUR_STATE_CD = cUR_STATE_CD;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getHIV_DX_DT() {
		return HIV_DX_DT;
	}
	public void setHIV_DX_DT(String hIV_DX_DT) {
		HIV_DX_DT = hIV_DX_DT;
	}
	public String getAIDS_DX_DT() {
		return AIDS_DX_DT;
	}
	public void setAIDS_DX_DT(String aIDS_DX_DT) {
		AIDS_DX_DT = aIDS_DX_DT;
	}
	public String getHIV_AIDS_CDC_ELIG() {
		return HIV_AIDS_CDC_ELIG;
	}
	public void setHIV_AIDS_CDC_ELIG(String hIV_AIDS_CDC_ELIG) {
		HIV_AIDS_CDC_ELIG = hIV_AIDS_CDC_ELIG;
	}
	public int getPARENT_DOCUMENT_ID() {
		return PARENT_DOCUMENT_ID;
	}
	public void setPARENT_DOCUMENT_ID(int pARENT_DOCUMENT_ID) {
		PARENT_DOCUMENT_ID = pARENT_DOCUMENT_ID;
	}
	public String getCLUSTER_TYPE_DESC() {
		return CLUSTER_TYPE_DESC;
	}
	public void setCLUSTER_TYPE_DESC(String cLUSTER_TYPE_DESC) {
		CLUSTER_TYPE_DESC = cLUSTER_TYPE_DESC;
	}
	public String getIS_RES_RECORD() {
		return IS_RES_RECORD;
	}
	public void setIS_RES_RECORD(String iS_RES_RECORD) {
		IS_RES_RECORD = iS_RES_RECORD;
	}
	public String getSTATUS_FLAG() {
		return STATUS_FLAG;
	}
	public void setSTATUS_FLAG(String sTATUS_FLAG) {
		STATUS_FLAG = sTATUS_FLAG;
	}
	public String getDEMO_DATA_SOURCE() {
		return DEMO_DATA_SOURCE;
	}
	public void setDEMO_DATA_SOURCE(String dEMO_DATA_SOURCE) {
		DEMO_DATA_SOURCE = dEMO_DATA_SOURCE;
	}
	public List<ClusterDocPSMessageVO> getClusterDocPSMessageList() {
		return clusterDocPSMessageList;
	}
	public void setClusterDocPSMessageList(
			List<ClusterDocPSMessageVO> clusterDocPSMessageList) {
		this.clusterDocPSMessageList = clusterDocPSMessageList;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	public String getUPLOAD_NYEHMS_STATENO() {
		return UPLOAD_NYEHMS_STATENO;
	}
	public void setUPLOAD_NYEHMS_STATENO(String uPLOAD_NYEHMS_STATENO) {
		UPLOAD_NYEHMS_STATENO = uPLOAD_NYEHMS_STATENO;
	}
	public String getCITYNO() {
		return CITYNO;
	}
	public void setCITYNO(String cITYNO) {
		CITYNO = cITYNO;
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
	public String getADDED_BY_USER() {
		return ADDED_BY_USER;
	}
	public void setADDED_BY_USER(String aDDED_BY_USER) {
		ADDED_BY_USER = aDDED_BY_USER;
	}
	public String getNYC_RSH_COUNTY_FIPS() {
		return NYC_RSH_COUNTY_FIPS;
	}
	public void setNYC_RSH_COUNTY_FIPS(String nYC_RSH_COUNTY_FIPS) {
		NYC_RSH_COUNTY_FIPS = nYC_RSH_COUNTY_FIPS;
	}
	public String getNYC_RSH_STATE_CD() {
		return NYC_RSH_STATE_CD;
	}
	public void setNYC_RSH_STATE_CD(String nYC_RSH_STATE_CD) {
		NYC_RSH_STATE_CD = nYC_RSH_STATE_CD;
	}
	public String getNYC_HIV_DX_DT() {
		return NYC_HIV_DX_DT;
	}
	public void setNYC_HIV_DX_DT(String nYC_HIV_DX_DT) {
		NYC_HIV_DX_DT = nYC_HIV_DX_DT;
	}
	public String getNYC_AIDS_DX_DT() {
		return NYC_AIDS_DX_DT;
	}
	public void setNYC_AIDS_DX_DT(String nYC_AIDS_DX_DT) {
		NYC_AIDS_DX_DT = nYC_AIDS_DX_DT;
	}
	public String getNYC_HIV_AIDS_CDC_ELIG() {
		return NYC_HIV_AIDS_CDC_ELIG;
	}
	public void setNYC_HIV_AIDS_CDC_ELIG(String nYC_HIV_AIDS_CDC_ELIG) {
		NYC_HIV_AIDS_CDC_ELIG = nYC_HIV_AIDS_CDC_ELIG;
	}
	public String getNYC_STATUS_FLAG() {
		return NYC_STATUS_FLAG;
	}
	public void setNYC_STATUS_FLAG(String nYC_STATUS_FLAG) {
		NYC_STATUS_FLAG = nYC_STATUS_FLAG;
	}
	public String getNYC_RACE() {
		return NYC_RACE;
	}
	public void setNYC_RACE(String nYC_RACE) {
		NYC_RACE = nYC_RACE;
	}
	public String getNYC_ETHNICITY() {
		return NYC_ETHNICITY;
	}
	public void setNYC_ETHNICITY(String nYC_ETHNICITY) {
		NYC_ETHNICITY = nYC_ETHNICITY;
	}
	public String getNYC_CUR_COUNTY_FIPS() {
		return NYC_CUR_COUNTY_FIPS;
	}
	public void setNYC_CUR_COUNTY_FIPS(String nYC_CUR_COUNTY_FIPS) {
		NYC_CUR_COUNTY_FIPS = nYC_CUR_COUNTY_FIPS;
	}
	public String getNYC_CUR_STATE_CD() {
		return NYC_CUR_STATE_CD;
	}
	public void setNYC_CUR_STATE_CD(String nYC_CUR_STATE_CD) {
		NYC_CUR_STATE_CD = nYC_CUR_STATE_CD;
	}
	public String getIDENTIFICATION_METHOD() {
		return IDENTIFICATION_METHOD;
	}
	public void setIDENTIFICATION_METHOD(String iDENTIFICATION_METHOD) {
		IDENTIFICATION_METHOD = iDENTIFICATION_METHOD;
	}
	public String getIDENTIFICATION_DATE() {
		return IDENTIFICATION_DATE;
	}
	public void setIDENTIFICATION_DATE(String iDENTIFICATION_DATE) {
		IDENTIFICATION_DATE = iDENTIFICATION_DATE;
	}
	public String getDX_START_DATE() {
		return DX_START_DATE;
	}
	public void setDX_START_DATE(String dX_START_DATE) {
		DX_START_DATE = dX_START_DATE;
	}
	public String getDX_END_DATE() {
		return DX_END_DATE;
	}
	public void setDX_END_DATE(String dX_END_DATE) {
		DX_END_DATE = dX_END_DATE;
	}
	public String getCLUSTER_COUNTIES() {
		return CLUSTER_COUNTIES;
	}
	public void setCLUSTER_COUNTIES(String cLUSTER_COUNTIES) {
		CLUSTER_COUNTIES = cLUSTER_COUNTIES;
	}
	public String getCLUSTER_DESC() {
		return CLUSTER_DESC;
	}
	public void setCLUSTER_DESC(String cLUSTER_DESC) {
		CLUSTER_DESC = cLUSTER_DESC;
	}
	public String getRELATEDNESS() {
		return RELATEDNESS;
	}
	public void setRELATEDNESS(String rELATEDNESS) {
		RELATEDNESS = rELATEDNESS;
	}
	public String getTIMEFRAME() {
		return TIMEFRAME;
	}
	public void setTIMEFRAME(String tIMEFRAME) {
		TIMEFRAME = tIMEFRAME;
	}
	public String getINACTIVE_FLAG() {
		return INACTIVE_FLAG;
	}
	public void setINACTIVE_FLAG(String iNACTIVE_FLAG) {
		INACTIVE_FLAG = iNACTIVE_FLAG;
	}
	public String getINACTIVE_DATE() {
		return INACTIVE_DATE;
	}
	public void setINACTIVE_DATE(String iNACTIVE_DATE) {
		INACTIVE_DATE = iNACTIVE_DATE;
	}
	public String getEHARS_STATENO() {
		return EHARS_STATENO;
	}
	public void setEHARS_STATENO(String eHARS_STATENO) {
		EHARS_STATENO = eHARS_STATENO;
	}
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
}
