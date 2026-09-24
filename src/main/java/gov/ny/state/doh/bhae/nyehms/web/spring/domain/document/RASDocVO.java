package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

public class RASDocVO {
	
	private Integer DOCUMENT_ID;
	private String MATCHED_STATENO;
	private String FIRST_NAME;
	private String MIDDLE_NAME;
	private String LAST_NAME;
	private String DOB;
	private Integer VSEQID;
	private Integer	SEQ_RESULT_ID;
	private String COLLECTION_DATE;
	private String	VSEQCREATIONDATE;
	private Integer	RECORDS_GROUPED_COUNT;
	private Integer	RESISTANCE_DOC_ID_1;
	private String	RESISTANCE_TEST_DATE_1;
	private Integer	RESISTANCE_DOC_ID_2;
	private String	RESISTANCE_TEST_DATE_2;
	private Integer	RESISTANCE_DOC_ID_3;
	private String	RESISTANCE_TEST_DATE_3;
	private String	GENESFOUNDTEXT;
	private String	PHYLOSUBTYPE;
	private Integer RESISTANCE_DRUG_CLASS_PR;
	private Integer RESISTANCE_DRUG_CLASS_NRTI;
	private Integer RESISTANCE_DRUG_CLASS_NNRTI;
	private Integer RESISTANCE_DRUG_CLASS_IN;
	private String RESISTANCE_DRUG_CLASS_PR_STR="N";
	private String RESISTANCE_DRUG_CLASS_NRTI_STR="N";
	private String RESISTANCE_DRUG_CLASS_NNRTI_STR="N";
	private String RESISTANCE_DRUG_CLASS_IN_STR="N";
	
	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getMATCHED_STATENO() {
		return MATCHED_STATENO;
	}
	public void setMATCHED_STATENO(String mATCHED_STATENO) {
		MATCHED_STATENO = mATCHED_STATENO;
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
	public String getVSEQCREATIONDATE() {
		return VSEQCREATIONDATE;
	}
	public void setVSEQCREATIONDATE(String vSEQCREATIONDATE) {
		VSEQCREATIONDATE = vSEQCREATIONDATE;
	}
	public Integer getRECORDS_GROUPED_COUNT() {
		return RECORDS_GROUPED_COUNT;
	}
	public void setRECORDS_GROUPED_COUNT(Integer rECORDS_GROUPED_COUNT) {
		RECORDS_GROUPED_COUNT = rECORDS_GROUPED_COUNT;
	}
	public Integer getRESISTANCE_DOC_ID_1() {
		return RESISTANCE_DOC_ID_1;
	}
	public void setRESISTANCE_DOC_ID_1(Integer rESISTANCE_DOC_ID_1) {
		RESISTANCE_DOC_ID_1 = rESISTANCE_DOC_ID_1;
	}
	public String getRESISTANCE_TEST_DATE_1() {
		return RESISTANCE_TEST_DATE_1;
	}
	public void setRESISTANCE_TEST_DATE_1(String rESISTANCE_TEST_DATE_1) {
		RESISTANCE_TEST_DATE_1 = rESISTANCE_TEST_DATE_1;
	}
	public Integer getRESISTANCE_DOC_ID_2() {
		return RESISTANCE_DOC_ID_2;
	}
	public void setRESISTANCE_DOC_ID_2(Integer rESISTANCE_DOC_ID_2) {
		RESISTANCE_DOC_ID_2 = rESISTANCE_DOC_ID_2;
	}
	public String getRESISTANCE_TEST_DATE_2() {
		return RESISTANCE_TEST_DATE_2;
	}
	public void setRESISTANCE_TEST_DATE_2(String rESISTANCE_TEST_DATE_2) {
		RESISTANCE_TEST_DATE_2 = rESISTANCE_TEST_DATE_2;
	}
	public Integer getRESISTANCE_DOC_ID_3() {
		return RESISTANCE_DOC_ID_3;
	}
	public void setRESISTANCE_DOC_ID_3(Integer rESISTANCE_DOC_ID_3) {
		RESISTANCE_DOC_ID_3 = rESISTANCE_DOC_ID_3;
	}
	public String getRESISTANCE_TEST_DATE_3() {
		return RESISTANCE_TEST_DATE_3;
	}
	public void setRESISTANCE_TEST_DATE_3(String rESISTANCE_TEST_DATE_3) {
		RESISTANCE_TEST_DATE_3 = rESISTANCE_TEST_DATE_3;
	}
	public String getGENESFOUNDTEXT() {
		return GENESFOUNDTEXT;
	}
	public void setGENESFOUNDTEXT(String gENESFOUNDTEXT) {
		GENESFOUNDTEXT = gENESFOUNDTEXT;
	}
	public String getPHYLOSUBTYPE() {
		return PHYLOSUBTYPE;
	}
	public void setPHYLOSUBTYPE(String pHYLOSUBTYPE) {
		PHYLOSUBTYPE = pHYLOSUBTYPE;
	}
	public Integer getRESISTANCE_DRUG_CLASS_PR() {
		return RESISTANCE_DRUG_CLASS_PR;
	}
	public void setRESISTANCE_DRUG_CLASS_PR(Integer rESISTANCE_DRUG_CLASS_PR) {
		RESISTANCE_DRUG_CLASS_PR = rESISTANCE_DRUG_CLASS_PR;
	}
	public Integer getRESISTANCE_DRUG_CLASS_NRTI() {
		return RESISTANCE_DRUG_CLASS_NRTI;
	}
	public void setRESISTANCE_DRUG_CLASS_NRTI(Integer rESISTANCE_DRUG_CLASS_NRTI) {
		RESISTANCE_DRUG_CLASS_NRTI = rESISTANCE_DRUG_CLASS_NRTI;
	}
	public Integer getRESISTANCE_DRUG_CLASS_NNRTI() {
		return RESISTANCE_DRUG_CLASS_NNRTI;
	}
	public void setRESISTANCE_DRUG_CLASS_NNRTI(Integer rESISTANCE_DRUG_CLASS_NNRTI) {
		RESISTANCE_DRUG_CLASS_NNRTI = rESISTANCE_DRUG_CLASS_NNRTI;
	}
	public Integer getRESISTANCE_DRUG_CLASS_IN() {
		return RESISTANCE_DRUG_CLASS_IN;
	}
	public void setRESISTANCE_DRUG_CLASS_IN(Integer rESISTANCE_DRUG_CLASS_IN) {
		RESISTANCE_DRUG_CLASS_IN = rESISTANCE_DRUG_CLASS_IN;
	}
	public String getRESISTANCE_DRUG_CLASS_PR_STR() {
		return RESISTANCE_DRUG_CLASS_PR_STR;
	}
	public void setRESISTANCE_DRUG_CLASS_PR_STR(String rESISTANCE_DRUG_CLASS_PR_STR) {
		RESISTANCE_DRUG_CLASS_PR_STR = rESISTANCE_DRUG_CLASS_PR_STR;
	}
	public String getRESISTANCE_DRUG_CLASS_NRTI_STR() {
		return RESISTANCE_DRUG_CLASS_NRTI_STR;
	}
	public void setRESISTANCE_DRUG_CLASS_NRTI_STR(
			String rESISTANCE_DRUG_CLASS_NRTI_STR) {
		RESISTANCE_DRUG_CLASS_NRTI_STR = rESISTANCE_DRUG_CLASS_NRTI_STR;
	}
	public String getRESISTANCE_DRUG_CLASS_NNRTI_STR() {
		return RESISTANCE_DRUG_CLASS_NNRTI_STR;
	}
	public void setRESISTANCE_DRUG_CLASS_NNRTI_STR(
			String rESISTANCE_DRUG_CLASS_NNRTI_STR) {
		RESISTANCE_DRUG_CLASS_NNRTI_STR = rESISTANCE_DRUG_CLASS_NNRTI_STR;
	}
	public String getRESISTANCE_DRUG_CLASS_IN_STR() {
		return RESISTANCE_DRUG_CLASS_IN_STR;
	}
	public void setRESISTANCE_DRUG_CLASS_IN_STR(String rESISTANCE_DRUG_CLASS_IN_STR) {
		RESISTANCE_DRUG_CLASS_IN_STR = rESISTANCE_DRUG_CLASS_IN_STR;
	}
	
	public Integer getVSEQID() {
		return VSEQID;
	}
	public void setVSEQID(Integer vSEQID) {
		VSEQID = vSEQID;
	}
	public Integer getSEQ_RESULT_ID() {
		return SEQ_RESULT_ID;
	}
	public void setSEQ_RESULT_ID(Integer sEQ_RESULT_ID) {
		SEQ_RESULT_ID = sEQ_RESULT_ID;
	}
	public String getCOLLECTION_DATE() {
		return COLLECTION_DATE;
	}
	public void setCOLLECTION_DATE(String cOLLECTION_DATE) {
		COLLECTION_DATE = cOLLECTION_DATE;
	}
	
}
