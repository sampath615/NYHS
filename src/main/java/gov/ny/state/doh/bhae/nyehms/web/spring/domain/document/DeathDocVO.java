package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This is pojo class for Death document. This is used by mybatis for creating
 * object map for death document .
 *
 * @author vxp06
 */
public class DeathDocVO extends NYEHMSDocVO {

    private int DOCUMENT_ID;
    private String STATENO = "";
    private Date EHARS_IMPORT_DATE;
    private String CERT = "";
    private String DEATH_CERT_LICENSE = "";
    private Date NYC_SHIP_DATE;
    private String DEATH_CNTY = "";
    private String DOD = "";
    private String AGE_DEATH = "";
    private String ICD10 = "";
    private String SPEC_INT_CAUSE = "";
    private String UNITS_AGE = "";
    private String FNAME = "";
    private String LNAME = "";
    private String MNAME = "";
    private String SOUNDEX = "";
    private String FATHER_LNAME = "";
    private String MOTHER_MAIDEN = "";
    private String SSN = "";
    private String DOB = "";
    private String SEX = "";
    private String MARITAL_STATUS = "";
    private String PREGNANT = "";
    private String DELIVERY_DT = "";
    private String BIRTH_PLACE = "";
    private String RES_APT_NO = "";
    private String RES_STREET_NO = "";
    private String RES_STREET = "";
    private String RES_STREET_DIRECT = "";
    private String PERSON_ADDRESS = "";
    private String RES_STREET_TYPE = "";
    private String PERSON_ZIP = "";
    private String PERSON_COUNTY = "";
    private String PERSON_CITY = "";
    private String RES_NYC = "";
    private String LOCAL_REG_NO = "";
    private String FIPS_CNTY = "";
    private String FIPS_PLACE = "";
    private String PERSON_STATE = "";
    private String PFI = "";
    private String FACILITY_TYPE = "";
    private String FACILITY_NAME = "";
    private String RAW_FACILITY_ADDRESS = "";
    private String FACILITY_ADDRESS = "";
    private String FACILITY_CITY = "";
    private String FACILITY_STATE = "";
    private String FACILITY_ZIP = "";
    private String FACILITY_PLUS4 = "";
    private String FACILITY_PHONE = "";
    private String FACILITY_COUNTY = "";
    private String INST_CODE = "";
    private String TRANS_FAC_CD = "";
    private String ETHNICITY = "";
    private String RACE_WHITE = "";
    private String RACE_BLACK = "";
    private String RACE_ASIAN = "";
    private String RACE_PACIFIC = "";
    private String RACE_INDIAN = "";
    private String RACE_OTHER = "";
    private String RACE_UNKNOWN = "";
    private String DEATH_UNIQUE_ID = "";
    private String DEATH_ID = "";
    private String UNDERLYING = "";
    private String CAUSE1 = "";
    private String CAUSE2 = "";
    private String CAUSE3 = "";
    private String CAUSE4 = "";
    private String CAUSE5 = "";
    private String CAUSE6 = "";
    private String CAUSE7 = "";
    private String CAUSE8 = "";
    private String CAUSE9 = "";
    private String CAUSE10 = "";
    private String CAUSE11 = "";
    private String CAUSE12 = "";
    private String CAUSE13 = "";
    private String CAUSE14 = "";
    private String CAUSE15 = "";
    private String CAUSE16 = "";
    private String CAUSE17 = "";
    private String CAUSE18 = "";
    private String CAUSE19 = "";
    private String CAUSE20 = "";
    private String PERSON_PLUS4;
    private String h_cause;
    private String oi_cause;
    private String causeOfDeath;
    
    
    //Clerical review fields
    private String GENDER;
    private String MED_RECORD_NUMBER;
    private String dateOfDeath;
    private String SUFFIX;
    
    public int getDOCUMENT_ID() {
        return DOCUMENT_ID;
    }

    public void setDOCUMENT_ID(int DOCUMENT_ID) {
        this.DOCUMENT_ID = DOCUMENT_ID;
    }

    public String getSTATENO() {
        return STATENO;
    }

    public void setSTATENO(String STATENO) {
        this.STATENO = STATENO;
    }

    public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}

	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}

	

	public Date getNYC_SHIP_DATE() {
		return NYC_SHIP_DATE;
	}

	public void setNYC_SHIP_DATE(Date nYC_SHIP_DATE) {
		NYC_SHIP_DATE = nYC_SHIP_DATE;
	}

	public String getCERT() {
        return CERT;
    }

    public void setCERT(String CERT) {
        this.CERT = CERT;
    }

    public String getDEATH_CERT_LICENSE() {
        return DEATH_CERT_LICENSE;
    }

    public void setDEATH_CERT_LICENSE(String DEATH_CERT_LICENSE) {
        this.DEATH_CERT_LICENSE = DEATH_CERT_LICENSE;
    }

    public String getDEATH_CNTY() {
        return DEATH_CNTY;
    }

    public void setDEATH_CNTY(String DEATH_CNTY) {
        this.DEATH_CNTY = DEATH_CNTY;
    }

    public String getDOD() {
        return DOD;
    }

    public void setDOD(String DOD) {
        this.DOD = DOD;
    }

    public String getAGE_DEATH() {
        return AGE_DEATH;
    }

    public void setAGE_DEATH(String AGE_DEATH) {
        this.AGE_DEATH = AGE_DEATH;
    }

    public String getICD10() {
        return ICD10;
    }

    public void setICD10(String ICD10) {
        this.ICD10 = ICD10;
    }

    public String getSPEC_INT_CAUSE() {
        return SPEC_INT_CAUSE;
    }

    public void setSPEC_INT_CAUSE(String SPEC_INT_CAUSE) {
        this.SPEC_INT_CAUSE = SPEC_INT_CAUSE;
    }

    public String getUNITS_AGE() {
        return UNITS_AGE;
    }

    public void setUNITS_AGE(String UNITS_AGE) {
        this.UNITS_AGE = UNITS_AGE;
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

	public String getMNAME() {
		return MNAME;
	}

	public void setMNAME(String mNAME) {
		MNAME = mNAME;
	}

	public String getSOUNDEX() {
        return SOUNDEX;
    }

    public void setSOUNDEX(String SOUNDEX) {
        this.SOUNDEX = SOUNDEX;
    }

    public String getFATHER_LNAME() {
        return FATHER_LNAME;
    }

    public void setFATHER_LNAME(String FATHER_LNAME) {
        this.FATHER_LNAME = FATHER_LNAME;
    }

    public String getMOTHER_MAIDEN() {
        return MOTHER_MAIDEN;
    }

    public void setMOTHER_MAIDEN(String MOTHER_MAIDEN) {
        this.MOTHER_MAIDEN = MOTHER_MAIDEN;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getMARITAL_STATUS() {
        return MARITAL_STATUS;
    }

    public void setMARITAL_STATUS(String MARITAL_STATUS) {
        this.MARITAL_STATUS = MARITAL_STATUS;
    }

    public String getPREGNANT() {
        return PREGNANT;
    }

    public void setPREGNANT(String PREGNANT) {
        this.PREGNANT = PREGNANT;
    }

    public String getDELIVERY_DT() {
        return DELIVERY_DT;
    }

    public void setDELIVERY_DT(String DELIVERY_DT) {
        this.DELIVERY_DT = DELIVERY_DT;
    }

    
    public String getBIRTH_PLACE() {
        return BIRTH_PLACE;
    }

    public void setBIRTH_PLACE(String BIRTH_PLACE) {
        this.BIRTH_PLACE = BIRTH_PLACE;
    }

    public String getRES_APT_NO() {
        return RES_APT_NO;
    }

    public void setRES_APT_NO(String RES_APT_NO) {
        this.RES_APT_NO = RES_APT_NO;
    }

    public String getRES_STREET_NO() {
        return RES_STREET_NO;
    }

    public void setRES_STREET_NO(String RES_STREET_NO) {
        this.RES_STREET_NO = RES_STREET_NO;
    }

    public String getRES_STREET() {
        return RES_STREET;
    }

    public void setRES_STREET(String RES_STREET) {
        this.RES_STREET = RES_STREET;
    }

    public String getRES_STREET_DIRECT() {
        return RES_STREET_DIRECT;
    }

    public void setRES_STREET_DIRECT(String RES_STREET_DIRECT) {
        this.RES_STREET_DIRECT = RES_STREET_DIRECT;
    }

  

    public String getRES_STREET_TYPE() {
        return RES_STREET_TYPE;
    }

    public void setRES_STREET_TYPE(String RES_STREET_TYPE) {
        this.RES_STREET_TYPE = RES_STREET_TYPE;
    }



    public String getRES_NYC() {
        return RES_NYC;
    }

    public void setRES_NYC(String RES_NYC) {
        this.RES_NYC = RES_NYC;
    }

    public String getLOCAL_REG_NO() {
        return LOCAL_REG_NO;
    }

    public void setLOCAL_REG_NO(String LOCAL_REG_NO) {
        this.LOCAL_REG_NO = LOCAL_REG_NO;
    }

    public String getFIPS_CNTY() {
        return FIPS_CNTY;
    }

    public void setFIPS_CNTY(String FIPS_CNTY) {
        this.FIPS_CNTY = FIPS_CNTY;
    }

    public String getFIPS_PLACE() {
        return FIPS_PLACE;
    }

    public void setFIPS_PLACE(String FIPS_PLACE) {
        this.FIPS_PLACE = FIPS_PLACE;
    }

   

    public String getPFI() {
        return PFI;
    }

    public void setPFI(String PFI) {
        this.PFI = PFI;
    }

    public String getFACILITY_TYPE() {
        return FACILITY_TYPE;
    }

    public void setFACILITY_TYPE(String FACILITY_TYPE) {
        this.FACILITY_TYPE = FACILITY_TYPE;
    }

    public String getFACILITY_NAME() {
        return FACILITY_NAME;
    }

    public void setFACILITY_NAME(String FACILITY_NAME) {
        this.FACILITY_NAME = FACILITY_NAME;
    }

    



    public String getRAW_FACILITY_ADDRESS() {
		return RAW_FACILITY_ADDRESS;
	}

	public void setRAW_FACILITY_ADDRESS(String rAW_FACILITY_ADDRESS) {
		RAW_FACILITY_ADDRESS = rAW_FACILITY_ADDRESS;
	}

	public String getFACILITY_ADDRESS() {
		return FACILITY_ADDRESS;
	}

	public void setFACILITY_ADDRESS(String fACILITY_ADDRESS) {
		FACILITY_ADDRESS = fACILITY_ADDRESS;
	}

	public String getFACILITY_CITY() {
        return FACILITY_CITY;
    }

    public void setFACILITY_CITY(String FACILITY_CITY) {
        this.FACILITY_CITY = FACILITY_CITY;
    }

    public String getFACILITY_STATE() {
        return FACILITY_STATE;
    }

    public void setFACILITY_STATE(String FACILITY_STATE) {
        this.FACILITY_STATE = FACILITY_STATE;
    }

    public String getFACILITY_ZIP() {
        return FACILITY_ZIP;
    }

    public void setFACILITY_ZIP(String FACILITY_ZIP) {
        this.FACILITY_ZIP = FACILITY_ZIP;
    }

    public String getFACILITY_PLUS4() {
        return FACILITY_PLUS4;
    }

    public void setFACILITY_PLUS4(String FACILITY_PLUS4) {
        this.FACILITY_PLUS4 = FACILITY_PLUS4;
    }

    public String getFACILITY_PHONE() {
        return FACILITY_PHONE;
    }

    public void setFACILITY_PHONE(String FACILITY_PHONE) {
        this.FACILITY_PHONE = FACILITY_PHONE;
    }

    public String getFACILITY_COUNTY() {
        return FACILITY_COUNTY;
    }

    public void setFACILITY_COUNTY(String FACILITY_COUNTY) {
        this.FACILITY_COUNTY = FACILITY_COUNTY;
    }

    public String getINST_CODE() {
        return INST_CODE;
    }

    public void setINST_CODE(String INST_CODE) {
        this.INST_CODE = INST_CODE;
    }

    public String getTRANS_FAC_CD() {
        return TRANS_FAC_CD;
    }

    public void setTRANS_FAC_CD(String TRANS_FAC_CD) {
        this.TRANS_FAC_CD = TRANS_FAC_CD;
    }



    public String getRACE_WHITE() {
        return RACE_WHITE;
    }

    public void setRACE_WHITE(String RACE_WHITE) {
        this.RACE_WHITE = RACE_WHITE;
    }

    public String getRACE_BLACK() {
        return RACE_BLACK;
    }

    public void setRACE_BLACK(String RACE_BLACK) {
        this.RACE_BLACK = RACE_BLACK;
    }

    public String getRACE_ASIAN() {
        return RACE_ASIAN;
    }

    public void setRACE_ASIAN(String RACE_ASIAN) {
        this.RACE_ASIAN = RACE_ASIAN;
    }

    public String getRACE_PACIFIC() {
        return RACE_PACIFIC;
    }

    public void setRACE_PACIFIC(String RACE_PACIFIC) {
        this.RACE_PACIFIC = RACE_PACIFIC;
    }

    public String getRACE_INDIAN() {
        return RACE_INDIAN;
    }

    public void setRACE_INDIAN(String RACE_INDIAN) {
        this.RACE_INDIAN = RACE_INDIAN;
    }

    public String getRACE_OTHER() {
        return RACE_OTHER;
    }

    public void setRACE_OTHER(String RACE_OTHER) {
        this.RACE_OTHER = RACE_OTHER;
    }
    
    
    public String getRACE_UNKNOWN() {
		return RACE_UNKNOWN;
	}

	public void setRACE_UNKNOWN(String rACE_UNKNOWN) {
		RACE_UNKNOWN = rACE_UNKNOWN;
	}

	public String getDEATH_UNIQUE_ID() {
        return DEATH_UNIQUE_ID;
    }

    public void setDEATH_UNIQUE_ID(String DEATH_UNIQUE_ID) {
        this.DEATH_UNIQUE_ID = DEATH_UNIQUE_ID;
    }

    public String getDEATH_ID() {
        return DEATH_ID;
    }

    public void setDEATH_ID(String DEATH_ID) {
        this.DEATH_ID = DEATH_ID;
    }

    public String getUNDERLYING() {
        return UNDERLYING;
    }

    public void setUNDERLYING(String UNDERLYING) {
        this.UNDERLYING = UNDERLYING;
    }

    public String getCAUSE1() {
        return CAUSE1;
    }

    public void setCAUSE1(String CAUSE1) {
        this.CAUSE1 = CAUSE1;
    }

    public String getCAUSE2() {
        return CAUSE2;
    }

    public void setCAUSE2(String CAUSE2) {
        this.CAUSE2 = CAUSE2;
    }

    public String getCAUSE3() {
        return CAUSE3;
    }

    public void setCAUSE3(String CAUSE3) {
        this.CAUSE3 = CAUSE3;
    }

    public String getCAUSE4() {
        return CAUSE4;
    }

    public void setCAUSE4(String CAUSE4) {
        this.CAUSE4 = CAUSE4;
    }

    public String getCAUSE5() {
        return CAUSE5;
    }

    public void setCAUSE5(String CAUSE5) {
        this.CAUSE5 = CAUSE5;
    }

    public String getCAUSE6() {
        return CAUSE6;
    }

    public void setCAUSE6(String CAUSE6) {
        this.CAUSE6 = CAUSE6;
    }

    public String getCAUSE7() {
        return CAUSE7;
    }

    public void setCAUSE7(String CAUSE7) {
        this.CAUSE7 = CAUSE7;
    }

    public String getCAUSE8() {
        return CAUSE8;
    }

    public void setCAUSE8(String CAUSE8) {
        this.CAUSE8 = CAUSE8;
    }

    public String getCAUSE9() {
        return CAUSE9;
    }

    public void setCAUSE9(String CAUSE9) {
        this.CAUSE9 = CAUSE9;
    }

    public String getCAUSE10() {
        return CAUSE10;
    }

    public void setCAUSE10(String CAUSE10) {
        this.CAUSE10 = CAUSE10;
    }

    public String getCAUSE11() {
        return CAUSE11;
    }

    public void setCAUSE11(String CAUSE11) {
        this.CAUSE11 = CAUSE11;
    }

    public String getCAUSE12() {
        return CAUSE12;
    }

    public void setCAUSE12(String CAUSE12) {
        this.CAUSE12 = CAUSE12;
    }

    public String getCAUSE13() {
        return CAUSE13;
    }

    public void setCAUSE13(String CAUSE13) {
        this.CAUSE13 = CAUSE13;
    }

    public String getCAUSE14() {
        return CAUSE14;
    }

    public void setCAUSE14(String CAUSE14) {
        this.CAUSE14 = CAUSE14;
    }

    public String getCAUSE15() {
        return CAUSE15;
    }

    public void setCAUSE15(String CAUSE15) {
        this.CAUSE15 = CAUSE15;
    }

    public String getCAUSE16() {
        return CAUSE16;
    }

    public void setCAUSE16(String CAUSE16) {
        this.CAUSE16 = CAUSE16;
    }

    public String getCAUSE17() {
        return CAUSE17;
    }

    public void setCAUSE17(String CAUSE17) {
        this.CAUSE17 = CAUSE17;
    }

    public String getCAUSE18() {
        return CAUSE18;
    }

    public void setCAUSE18(String CAUSE18) {
        this.CAUSE18 = CAUSE18;
    }

    public String getCAUSE19() {
        return CAUSE19;
    }

    public void setCAUSE19(String CAUSE19) {
        this.CAUSE19 = CAUSE19;
    }

    public String getCAUSE20() {
        return CAUSE20;
    }

    public void setCAUSE20(String CAUSE20) {
        this.CAUSE20 = CAUSE20;
    }
    
    
    public String getPERSON_PLUS4() {
		return PERSON_PLUS4;
	}

	public void setPERSON_PLUS4(String pERSON_PLUS4) {
		PERSON_PLUS4 = pERSON_PLUS4;
	}

	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}

	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}

	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}

	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}

	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}

	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}

	public String getPERSON_CITY() {
		return PERSON_CITY;
	}

	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}

	public String getPERSON_STATE() {
		return PERSON_STATE;
	}

	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}

	public String getETHNICITY() {
		return ETHNICITY;
	}

	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}

	public String getGENDER() {
		return GENDER;
	}

	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}

	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
	}

	public String getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public String getSUFFIX() {
		return SUFFIX;
	}

	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}

	public String getH_cause() {
		return h_cause;
	}

	public void setH_cause(String h_cause) {
		this.h_cause = h_cause;
	}

	public String getOi_cause() {
		return oi_cause;
	}

	public void setOi_cause(String oi_cause) {
		this.oi_cause = oi_cause;
	}

	public String getCauseOfDeath() {
		return causeOfDeath;
	}

	public void setCauseOfDeath(String causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}
	
	
}
