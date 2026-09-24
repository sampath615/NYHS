package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This is POJO class for NDI document.
 *
 * @author vxp06
 */
public class NDIDocumentVO {
	private String MNAME;
	private String LNAME;
	private String FNAME;
	private String NDI_FILE;
	private String STATENO;
	private int TIME_DISCREP;
	private int DOCUMENT_ID;
	private Date RECEIVED_DATE;
	private Date CREATE_DATE;
	private Date NYC_SHIP_DATE;
	private String SSN;
	private String NDI_DOD;
	private String DEATH_CERT;
	private String YEAR_DEATH;
	private String ICD_CODE;
	private String COD1;
	private String COD2;
	private String COD3;
	private String COD4;
	private String COD5;
	private String COD6;
	private String COD7;
	private String COD8;
	private String COD9;
	private String COD10;
	private String COD11;
	private String COD12;
	private String COD13;
	private String COD14;
	private String COD15;
	private String COD16;
	private String COD17;
	private String COD18;
	private String COD19;
	private String COD20;
	private String HIVCAUSE;
	private String TDISCREP_CAT;
	private String DOC_TYPE;
	private String UNIQ_NO;
	private String STATE_RES;
	private String STATE_DEATH;
	private String MONTH_DEATH;
	private String DAY_DEATH;
	
	private String DOD_TEXT;
	
	private String DOB_TEXT;
	private String CREATE_UID;
	private String EHARS_IMPORT_DATE;
	private String BIRTH_SEX;
	private String SURV_METHOD;
	private String NIF1;
	private String NIF2;
	private String NIF3;
	private String NIF4;
	private String NIF5;
	private String NIF6;
	private String NIF7;
	private String NIF8;
	private String NIF9;
	private String NIF10;
	private String NIF11;
	private String NIF12;
	private String NIF13;
	private String NIF14;
	private String NIF15;
	private String NIF16;
	private String NIF17;
	private String NIF18;
	private String NIF19;
	private String NIF20;
	private String EHARS_STATUS_CD;
	private String indxCaseDOD;
	private String NDIFILE_CAT;
	
	private int PARENT_DOCUMENT_ID;

	public int getPARENT_DOCUMENT_ID() {
		return PARENT_DOCUMENT_ID;
	}

	public void setPARENT_DOCUMENT_ID(int pARENT_DOCUMENT_ID) {
		PARENT_DOCUMENT_ID = pARENT_DOCUMENT_ID;
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
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getNDI_FILE() {
		return NDI_FILE;
	}
	public void setNDI_FILE(String nDI_FILE) {
		NDI_FILE = nDI_FILE;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public int getTIME_DISCREP() {
		return TIME_DISCREP;
	}
	public void setTIME_DISCREP(int tIME_DISCREP) {
		TIME_DISCREP = tIME_DISCREP;
	}
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public Date getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}
	public void setRECEIVED_DATE(Date rECEIVED_DATE) {
		RECEIVED_DATE = rECEIVED_DATE;
	}
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public Date getNYC_SHIP_DATE() {
		return NYC_SHIP_DATE;
	}
	public void setNYC_SHIP_DATE(Date nYC_SHIP_DATE) {
		NYC_SHIP_DATE = nYC_SHIP_DATE;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getNDI_DOD() {
		return NDI_DOD;
	}
	public void setNDI_DOD(String nDI_DOD) {
		NDI_DOD = nDI_DOD;
	}
	public String getDEATH_CERT() {
		return DEATH_CERT;
	}
	public void setDEATH_CERT(String dEATH_CERT) {
		DEATH_CERT = dEATH_CERT;
	}
	public String getYEAR_DEATH() {
		return YEAR_DEATH;
	}
	public void setYEAR_DEATH(String yEAR_DEATH) {
		YEAR_DEATH = yEAR_DEATH;
	}
	public String getICD_CODE() {
		return ICD_CODE;
	}
	public void setICD_CODE(String iCD_CODE) {
		ICD_CODE = iCD_CODE;
	}
	public String getCOD1() {
		return COD1;
	}
	public void setCOD1(String cOD1) {
		COD1 = cOD1;
	}
	public String getCOD2() {
		return COD2;
	}
	public void setCOD2(String cOD2) {
		COD2 = cOD2;
	}
	public String getCOD3() {
		return COD3;
	}
	public void setCOD3(String cOD3) {
		COD3 = cOD3;
	}
	public String getCOD4() {
		return COD4;
	}
	public void setCOD4(String cOD4) {
		COD4 = cOD4;
	}
	public String getCOD5() {
		return COD5;
	}
	public void setCOD5(String cOD5) {
		COD5 = cOD5;
	}
	public String getCOD6() {
		return COD6;
	}
	public void setCOD6(String cOD6) {
		COD6 = cOD6;
	}
	public String getCOD7() {
		return COD7;
	}
	public void setCOD7(String cOD7) {
		COD7 = cOD7;
	}
	public String getCOD8() {
		return COD8;
	}
	public void setCOD8(String cOD8) {
		COD8 = cOD8;
	}
	public String getCOD9() {
		return COD9;
	}
	public void setCOD9(String cOD9) {
		COD9 = cOD9;
	}
	public String getCOD10() {
		return COD10;
	}
	public void setCOD10(String cOD10) {
		COD10 = cOD10;
	}
	public String getCOD11() {
		return COD11;
	}
	public void setCOD11(String cOD11) {
		COD11 = cOD11;
	}
	public String getCOD12() {
		return COD12;
	}
	public void setCOD12(String cOD12) {
		COD12 = cOD12;
	}
	public String getCOD13() {
		return COD13;
	}
	public void setCOD13(String cOD13) {
		COD13 = cOD13;
	}
	public String getCOD14() {
		return COD14;
	}
	public void setCOD14(String cOD14) {
		COD14 = cOD14;
	}
	public String getCOD15() {
		return COD15;
	}
	public void setCOD15(String cOD15) {
		COD15 = cOD15;
	}
	public String getCOD16() {
		return COD16;
	}
	public void setCOD16(String cOD16) {
		COD16 = cOD16;
	}
	public String getCOD17() {
		return COD17;
	}
	public void setCOD17(String cOD17) {
		COD17 = cOD17;
	}
	public String getCOD18() {
		return COD18;
	}
	public void setCOD18(String cOD18) {
		COD18 = cOD18;
	}
	public String getCOD19() {
		return COD19;
	}
	public void setCOD19(String cOD19) {
		COD19 = cOD19;
	}
	public String getCOD20() {
		return COD20;
	}
	public void setCOD20(String cOD20) {
		COD20 = cOD20;
	}
	public String getHIVCAUSE() {
		return HIVCAUSE;
	}
	public void setHIVCAUSE(String hIVCAUSE) {
		HIVCAUSE = hIVCAUSE;
	}
	public String getTDISCREP_CAT() {
		return TDISCREP_CAT;
	}
	public void setTDISCREP_CAT(String tDISCREP_CAT) {
		TDISCREP_CAT = tDISCREP_CAT;
	}
	public String getDOC_TYPE() {
		return DOC_TYPE;
	}
	public void setDOC_TYPE(String dOC_TYPE) {
		DOC_TYPE = dOC_TYPE;
	}
	public String getUNIQ_NO() {
		return UNIQ_NO;
	}
	public void setUNIQ_NO(String uNIQ_NO) {
		UNIQ_NO = uNIQ_NO;
	}
	public String getSTATE_RES() {
		return STATE_RES;
	}
	public void setSTATE_RES(String sTATE_RES) {
		STATE_RES = sTATE_RES;
	}
	public String getSTATE_DEATH() {
		return STATE_DEATH;
	}
	public void setSTATE_DEATH(String sTATE_DEATH) {
		STATE_DEATH = sTATE_DEATH;
	}
	public String getMONTH_DEATH() {
		return MONTH_DEATH;
	}
	public void setMONTH_DEATH(String mONTH_DEATH) {
		MONTH_DEATH = mONTH_DEATH;
	}
	public String getDAY_DEATH() {
		return DAY_DEATH;
	}
	public void setDAY_DEATH(String dAY_DEATH) {
		DAY_DEATH = dAY_DEATH;
	}
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public String getCREATE_UID() {
		return CREATE_UID;
	}
	public void setCREATE_UID(String cREATE_UID) {
		CREATE_UID = cREATE_UID;
	}
	public String getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(String eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}
	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}
	public String getSURV_METHOD() {
		return SURV_METHOD;
	}
	public void setSURV_METHOD(String sURV_METHOD) {
		SURV_METHOD = sURV_METHOD;
	}
	public String getNIF1() {
		return NIF1;
	}
	public void setNIF1(String nIF1) {
		NIF1 = nIF1;
	}
	public String getNIF2() {
		return NIF2;
	}
	public void setNIF2(String nIF2) {
		NIF2 = nIF2;
	}
	public String getNIF3() {
		return NIF3;
	}
	public void setNIF3(String nIF3) {
		NIF3 = nIF3;
	}
	public String getNIF4() {
		return NIF4;
	}
	public void setNIF4(String nIF4) {
		NIF4 = nIF4;
	}
	public String getNIF5() {
		return NIF5;
	}
	public void setNIF5(String nIF5) {
		NIF5 = nIF5;
	}
	public String getNIF6() {
		return NIF6;
	}
	public void setNIF6(String nIF6) {
		NIF6 = nIF6;
	}
	public String getNIF7() {
		return NIF7;
	}
	public void setNIF7(String nIF7) {
		NIF7 = nIF7;
	}
	public String getNIF8() {
		return NIF8;
	}
	public void setNIF8(String nIF8) {
		NIF8 = nIF8;
	}
	public String getNIF9() {
		return NIF9;
	}
	public void setNIF9(String nIF9) {
		NIF9 = nIF9;
	}
	public String getNIF10() {
		return NIF10;
	}
	public void setNIF10(String nIF10) {
		NIF10 = nIF10;
	}
	public String getNIF11() {
		return NIF11;
	}
	public void setNIF11(String nIF11) {
		NIF11 = nIF11;
	}
	public String getNIF12() {
		return NIF12;
	}
	public void setNIF12(String nIF12) {
		NIF12 = nIF12;
	}
	public String getNIF13() {
		return NIF13;
	}
	public void setNIF13(String nIF13) {
		NIF13 = nIF13;
	}
	public String getNIF14() {
		return NIF14;
	}
	public void setNIF14(String nIF14) {
		NIF14 = nIF14;
	}
	public String getNIF15() {
		return NIF15;
	}
	public void setNIF15(String nIF15) {
		NIF15 = nIF15;
	}
	public String getNIF16() {
		return NIF16;
	}
	public void setNIF16(String nIF16) {
		NIF16 = nIF16;
	}
	public String getNIF17() {
		return NIF17;
	}
	public void setNIF17(String nIF17) {
		NIF17 = nIF17;
	}
	public String getNIF18() {
		return NIF18;
	}
	public void setNIF18(String nIF18) {
		NIF18 = nIF18;
	}
	public String getNIF19() {
		return NIF19;
	}
	public void setNIF19(String nIF19) {
		NIF19 = nIF19;
	}
	public String getNIF20() {
		return NIF20;
	}
	public void setNIF20(String nIF20) {
		NIF20 = nIF20;
	}
	public String getEHARS_STATUS_CD() {
		return EHARS_STATUS_CD;
	}
	public void setEHARS_STATUS_CD(String eHARS_STATUS_CD) {
		EHARS_STATUS_CD = eHARS_STATUS_CD;
	}
	public String getIndxCaseDOD() {
		return indxCaseDOD;
	}
	public void setIndxCaseDOD(String indxCaseDOD) {
		this.indxCaseDOD = indxCaseDOD;
	}
	public String getDOD_TEXT() {
		return DOD_TEXT;
	}
	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = dOD_TEXT;
	}
	public String getNDIFILE_CAT() {
		return NDIFILE_CAT;
	}
	public void setNDIFILE_CAT(String nDIFILE_CAT) {
		NDIFILE_CAT = nDIFILE_CAT;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	
	
}
