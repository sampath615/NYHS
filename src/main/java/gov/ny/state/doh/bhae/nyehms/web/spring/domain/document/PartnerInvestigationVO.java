package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;



/**
 * This is POJO class for Cluster Partner document.
 *
 * @author ska03
 */
public class PartnerInvestigationVO {
	
	
	private Integer PARTNER_TYPE;
	private String PARTNERASSISTANCESTATUS;
	private Integer PARTNERKEY;
	private Integer PARTNER_FLAG;
	private String PARTNER_FRNUMBER;
	private String PART_INACTIVE_FLAG;
	private String PART_INACTIVE_DATE;
	private Integer DISNUMBER;
	private Integer LEGACY_PARTNER_ID;
	private String LAST_HIV_NEG_DATE;
	private String OP_STATENO;
	private Integer OP_CDESSCASEKEY;
	private String OP_FRNUMBER;
	private Integer OP_FIELDRECORDKEY;
	private String OP_INACTIVE_FLAG;
	private String OP_DATE_CLOSED;
	private String DATEINITIATED;
	private String INTERVIEWDATE;
	private Integer CURRENTONPREP;
	private String CONFIRMPATIENTPREP;
	private String NOTIFICATIONDATEPIND;
	private String NOTIFICATIONDATE;
	
	private String FIELDNOTE;
	private String DISPOSITIONDATE1;
	private String DISPOSITIONDATE2;
	private String DISPOSITIONDATE3;
	private String DISPOSITIONDATE4;
	private String DISPOSITION1;
	private String DISPOSITION2;
	private String DISPOSITION3;
	private String DISPOSITION4;
	private String CONDITION_1;
	private String CONDITION_2;
	private String CONDITION_3;
	private String CONDITION_4;
	
	
	public String getPART_INACTIVE_DATE() {
		return PART_INACTIVE_DATE;
	}
	public void setPART_INACTIVE_DATE(String pART_INACTIVE_DATE) {
		PART_INACTIVE_DATE = pART_INACTIVE_DATE;
	}
	public String getOP_DATE_CLOSED() {
		return OP_DATE_CLOSED;
	}
	public void setOP_DATE_CLOSED(String oP_DATE_CLOSED) {
		OP_DATE_CLOSED = oP_DATE_CLOSED;
	}
	
	public String getNOTIFICATIONDATE() {
		return NOTIFICATIONDATE;
	}
	public void setNOTIFICATIONDATE(String nOTIFICATIONDATE) {
		NOTIFICATIONDATE = nOTIFICATIONDATE;
	}
	public String getLAST_HIV_NEG_DATE() {
		return LAST_HIV_NEG_DATE;
	}
	public void setLAST_HIV_NEG_DATE(String lAST_HIV_NEG_DATE) {
		LAST_HIV_NEG_DATE = lAST_HIV_NEG_DATE;
	}
	
	public String getDISPOSITIONDATE1() {
		return DISPOSITIONDATE1;
	}
	public void setDISPOSITIONDATE1(String dISPOSITIONDATE1) {
		DISPOSITIONDATE1 = dISPOSITIONDATE1;
	}
	public String getDISPOSITIONDATE2() {
		return DISPOSITIONDATE2;
	}
	public void setDISPOSITIONDATE2(String dISPOSITIONDATE2) {
		DISPOSITIONDATE2 = dISPOSITIONDATE2;
	}
	public String getDISPOSITIONDATE3() {
		return DISPOSITIONDATE3;
	}
	public void setDISPOSITIONDATE3(String dISPOSITIONDATE3) {
		DISPOSITIONDATE3 = dISPOSITIONDATE3;
	}
	public String getDISPOSITIONDATE4() {
		return DISPOSITIONDATE4;
	}
	public void setDISPOSITIONDATE4(String dISPOSITIONDATE4) {
		DISPOSITIONDATE4 = dISPOSITIONDATE4;
	}
	public String getDATEINITIATED() {
		return DATEINITIATED;
	}
	public void setDATEINITIATED(String dATEINITIATED) {
		DATEINITIATED = dATEINITIATED;
	}
	public String getINTERVIEWDATE() {
		return INTERVIEWDATE;
	}
	public void setINTERVIEWDATE(String iNTERVIEWDATE) {
		INTERVIEWDATE = iNTERVIEWDATE;
	}
	
	public String getOP_INACTIVE_FLAG() {
		return OP_INACTIVE_FLAG;
	}
	public void setOP_INACTIVE_FLAG(String oP_INACTIVE_FLAG) {
		OP_INACTIVE_FLAG = oP_INACTIVE_FLAG;
	}
	public String getPART_INACTIVE_FLAG() {
		return PART_INACTIVE_FLAG;
	}
	public void setPART_INACTIVE_FLAG(String pART_INACTIVE_FLAG) {
		PART_INACTIVE_FLAG = pART_INACTIVE_FLAG;
	}
	public String getNOTIFICATIONDATEPIND() {
		return NOTIFICATIONDATEPIND;
	}
	public void setNOTIFICATIONDATEPIND(String nOTIFICATIONDATEPIND) {
		NOTIFICATIONDATEPIND = nOTIFICATIONDATEPIND;
	}
	
	public String getOP_STATENO() {
		return OP_STATENO;
	}
	public void setOP_STATENO(String oP_STATENO) {
		OP_STATENO = oP_STATENO;
	}
	
	public String getDISPOSITION1() {
		return DISPOSITION1;
	}
	public void setDISPOSITION1(String dISPOSITION1) {
		DISPOSITION1 = dISPOSITION1;
	}
	public String getDISPOSITION2() {
		return DISPOSITION2;
	}
	public void setDISPOSITION2(String dISPOSITION2) {
		DISPOSITION2 = dISPOSITION2;
	}
	public String getDISPOSITION3() {
		return DISPOSITION3;
	}
	public void setDISPOSITION3(String dISPOSITION3) {
		DISPOSITION3 = dISPOSITION3;
	}
	public String getDISPOSITION4() {
		return DISPOSITION4;
	}
	public void setDISPOSITION4(String dISPOSITION4) {
		DISPOSITION4 = dISPOSITION4;
	}
	
	public String getPARTNERASSISTANCESTATUS() {
		return PARTNERASSISTANCESTATUS;
	}
	public void setPARTNERASSISTANCESTATUS(String pARTNERASSISTANCESTATUS) {
		PARTNERASSISTANCESTATUS = pARTNERASSISTANCESTATUS;
	}
	public String getOP_FRNUMBER() {
		return OP_FRNUMBER;
	}
	public void setOP_FRNUMBER(String oP_FRNUMBER) {
		OP_FRNUMBER = oP_FRNUMBER;
	}
	public String getPARTNER_FRNUMBER() {
		return PARTNER_FRNUMBER;
	}
	public void setPARTNER_FRNUMBER(String pARTNER_FRNUMBER) {
		PARTNER_FRNUMBER = pARTNER_FRNUMBER;
	}
	
	public String getCONDITION_1() {
		return CONDITION_1;
	}
	public void setCONDITION_1(String cONDITION_1) {
		CONDITION_1 = cONDITION_1;
	}
	public String getCONDITION_2() {
		return CONDITION_2;
	}
	public void setCONDITION_2(String cONDITION_2) {
		CONDITION_2 = cONDITION_2;
	}
	public String getCONDITION_3() {
		return CONDITION_3;
	}
	public void setCONDITION_3(String cONDITION_3) {
		CONDITION_3 = cONDITION_3;
	}
	public String getCONDITION_4() {
		return CONDITION_4;
	}
	public void setCONDITION_4(String cONDITION_4) {
		CONDITION_4 = cONDITION_4;
	}
	public String getCONFIRMPATIENTPREP() {
		return CONFIRMPATIENTPREP;
	}
	public void setCONFIRMPATIENTPREP(String cONFIRMPATIENTPREP) {
		CONFIRMPATIENTPREP = cONFIRMPATIENTPREP;
	}
	
	public String getFIELDNOTE() {
		return FIELDNOTE;
	}
	public void setFIELDNOTE(String fIELDNOTE) {
		FIELDNOTE = fIELDNOTE;
	}
	public Integer getPARTNER_TYPE() {
		return PARTNER_TYPE;
	}
	public void setPARTNER_TYPE(Integer pARTNER_TYPE) {
		PARTNER_TYPE = pARTNER_TYPE;
	}
	public Integer getPARTNERKEY() {
		return PARTNERKEY;
	}
	public void setPARTNERKEY(Integer pARTNERKEY) {
		PARTNERKEY = pARTNERKEY;
	}
	public Integer getPARTNER_FLAG() {
		return PARTNER_FLAG;
	}
	public void setPARTNER_FLAG(Integer pARTNER_FLAG) {
		PARTNER_FLAG = pARTNER_FLAG;
	}
	public Integer getDISNUMBER() {
		return DISNUMBER;
	}
	public void setDISNUMBER(Integer dISNUMBER) {
		DISNUMBER = dISNUMBER;
	}
	public Integer getLEGACY_PARTNER_ID() {
		return LEGACY_PARTNER_ID;
	}
	public void setLEGACY_PARTNER_ID(Integer lEGACY_PARTNER_ID) {
		LEGACY_PARTNER_ID = lEGACY_PARTNER_ID;
	}
	public Integer getOP_CDESSCASEKEY() {
		return OP_CDESSCASEKEY;
	}
	public void setOP_CDESSCASEKEY(Integer oP_CDESSCASEKEY) {
		OP_CDESSCASEKEY = oP_CDESSCASEKEY;
	}
	public Integer getOP_FIELDRECORDKEY() {
		return OP_FIELDRECORDKEY;
	}
	public void setOP_FIELDRECORDKEY(Integer oP_FIELDRECORDKEY) {
		OP_FIELDRECORDKEY = oP_FIELDRECORDKEY;
	}
	public Integer getCURRENTONPREP() {
		return CURRENTONPREP;
	}
	public void setCURRENTONPREP(Integer cURRENTONPREP) {
		CURRENTONPREP = cURRENTONPREP;
	}
	
	
	
}	
