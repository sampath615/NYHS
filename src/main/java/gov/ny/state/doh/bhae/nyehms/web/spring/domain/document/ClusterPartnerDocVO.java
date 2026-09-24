package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.List;

/**
 * This is POJO class for Cluster Partner document.
 *
 * @author ska03
 */
public class ClusterPartnerDocVO {
	
	
	private String STATENO;	
	private Integer DOCUMENT_ID;
	private String DATE_OF_BIRTH;
	private String DATE_OF_DEATH;
	private String INACTIVE_DATE;
	private String ADDRESS_DATE;
	private String PREPDATE;
	private Integer PARTNER_ID;
	private String PARTNER_CLUSTER_ID;
	
	private String RACE_WHITE;
	private String RACE_BLACK;
	private String RACE_INDIAN;
	private String RACE_ASIAN;
	private String RACE_PACIFIC;
	private String RACE_OTHER;
	private String RACE_UNKNOWN;
	private String PERSON_ZIP;
	private String NYSID;
	private String ADDRESS_SOURCE;
	private String PERSON_STATE;
	private String PERSON_ADDRESS;
	private String BIRTH_SEX;
	private String MEDRECNO;
	private String PERSON_COUNTY;
	private String ETHNICITY;
	private String CLUSTER_ID;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String HOME_PHONE;
	private String CELL_PHONE;
	private String PERSON_CITY;
	private String DIN;
	private String BIRTH_COUNTRY;
	private String GENDER;
	private String PARTNER_ASSISTANCE_STATUS;
	private String psMessageExist = "No";
	String  RSN_NO_PS_MSG;
	String RSN_PS_MSG;
	
	public List<PartnerInvestigationVO> partnerInvestigationList;
	
	
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getDATE_OF_BIRTH() {
		return DATE_OF_BIRTH;
	}
	public void setDATE_OF_BIRTH(String dATE_OF_BIRTH) {
		DATE_OF_BIRTH = dATE_OF_BIRTH;
	}
	public String getDATE_OF_DEATH() {
		return DATE_OF_DEATH;
	}
	public void setDATE_OF_DEATH(String dATE_OF_DEATH) {
		DATE_OF_DEATH = dATE_OF_DEATH;
	}
	public String getINACTIVE_DATE() {
		return INACTIVE_DATE;
	}
	public void setINACTIVE_DATE(String iNACTIVE_DATE) {
		INACTIVE_DATE = iNACTIVE_DATE;
	}
	public String getADDRESS_DATE() {
		return ADDRESS_DATE;
	}
	public void setADDRESS_DATE(String aDDRESS_DATE) {
		ADDRESS_DATE = aDDRESS_DATE;
	}
	public String getPREPDATE() {
		return PREPDATE;
	}
	public void setPREPDATE(String pREPDATE) {
		PREPDATE = pREPDATE;
	}
	public Integer getPARTNER_ID() {
		return PARTNER_ID;
	}
	public void setPARTNER_ID(Integer pARTNER_ID) {
		PARTNER_ID = pARTNER_ID;
	}
	
	public String getPARTNER_CLUSTER_ID() {
		return PARTNER_CLUSTER_ID;
	}
	public void setPARTNER_CLUSTER_ID(String pARTNER_CLUSTER_ID) {
		PARTNER_CLUSTER_ID = pARTNER_CLUSTER_ID;
	}
	public String getRACE_WHITE() {
		return RACE_WHITE;
	}
	public void setRACE_WHITE(String rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
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
	public String getRACE_ASIAN() {
		return RACE_ASIAN;
	}
	public void setRACE_ASIAN(String rACE_ASIAN) {
		RACE_ASIAN = rACE_ASIAN;
	}
	public String getRACE_PACIFIC() {
		return RACE_PACIFIC;
	}
	public void setRACE_PACIFIC(String rACE_PACIFIC) {
		RACE_PACIFIC = rACE_PACIFIC;
	}
	public String getRACE_OTHER() {
		return RACE_OTHER;
	}
	public void setRACE_OTHER(String rACE_OTHER) {
		RACE_OTHER = rACE_OTHER;
	}
	public String getRACE_UNKNOWN() {
		return RACE_UNKNOWN;
	}
	public void setRACE_UNKNOWN(String rACE_UNKNOWN) {
		RACE_UNKNOWN = rACE_UNKNOWN;
	}
	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}
	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}
	public String getNYSID() {
		return NYSID;
	}
	public void setNYSID(String nYSID) {
		NYSID = nYSID;
	}
	public String getADDRESS_SOURCE() {
		return ADDRESS_SOURCE;
	}
	public void setADDRESS_SOURCE(String aDDRESS_SOURCE) {
		ADDRESS_SOURCE = aDDRESS_SOURCE;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}
	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}
	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}
	public String getMEDRECNO() {
		return MEDRECNO;
	}
	public void setMEDRECNO(String mEDRECNO) {
		MEDRECNO = mEDRECNO;
	}
	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}
	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getCLUSTER_ID() {
		return CLUSTER_ID;
	}
	public void setCLUSTER_ID(String cLUSTER_ID) {
		CLUSTER_ID = cLUSTER_ID;
	}
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getMNAME() {
		return MNAME;
	}
	public void setMNAME(String mNAME) {
		MNAME = mNAME;
	}
	public String getLNAME() {
		return LNAME;
	}
	public void setLNAME(String lNAME) {
		LNAME = lNAME;
	}
	public String getHOME_PHONE() {
		return HOME_PHONE;
	}
	public void setHOME_PHONE(String hOME_PHONE) {
		HOME_PHONE = hOME_PHONE;
	}
	public String getCELL_PHONE() {
		return CELL_PHONE;
	}
	public void setCELL_PHONE(String cELL_PHONE) {
		CELL_PHONE = cELL_PHONE;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}
	public String getDIN() {
		return DIN;
	}
	public void setDIN(String dIN) {
		DIN = dIN;
	}
	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}
	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getPARTNER_ASSISTANCE_STATUS() {
		return PARTNER_ASSISTANCE_STATUS;
	}
	public void setPARTNER_ASSISTANCE_STATUS(String pARTNER_ASSISTANCE_STATUS) {
		PARTNER_ASSISTANCE_STATUS = pARTNER_ASSISTANCE_STATUS;
	}
	public List<PartnerInvestigationVO> getPartnerInvestigationList() {
		return partnerInvestigationList;
	}
	public void setPartnerInvestigationList(List<PartnerInvestigationVO> partnerInvestigationList) {
		this.partnerInvestigationList = partnerInvestigationList;
	}
	
	public String getPsMessageExist() {
		return psMessageExist;
	}
	public void setPsMessageExist(String psMessageExist) {
		this.psMessageExist = psMessageExist;
	}
	public String getRSN_NO_PS_MSG() {
		return RSN_NO_PS_MSG;
	}
	public void setRSN_NO_PS_MSG(String rSN_NO_PS_MSG) {
		RSN_NO_PS_MSG = rSN_NO_PS_MSG;
	}
	public String getRSN_PS_MSG() {
		return RSN_PS_MSG;
	}
	public void setRSN_PS_MSG(String rSN_PS_MSG) {
		RSN_PS_MSG = rSN_PS_MSG;
	}
	
	
}	
