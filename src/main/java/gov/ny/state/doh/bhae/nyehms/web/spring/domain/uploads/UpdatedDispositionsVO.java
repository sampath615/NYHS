package gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads;

import java.util.Date;


public class UpdatedDispositionsVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer DOCUMENT_ID;
//	private Date DATE_OF_DEATH;
	private Integer DISPOSITION_CODE;
	private String FIELDNOTES;
	private String CREATE_UID;
	private String DISPOSITION_DESC;
	private String DISPOSITION_DATE;
	private String TABLE_NAME;
	private String UPDATED_BY;
	private Date UPDATED_DATE;
	
	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getCREATE_UID() {
		return CREATE_UID;
	}
	public void setCREATE_UID(String cREATE_UID) {
		CREATE_UID = cREATE_UID;
	}
	public String getDISPOSITION_DESC() {
		return DISPOSITION_DESC;
	}
	public void setDISPOSITION_DESC(String dISPOSITION_DESC) {
		DISPOSITION_DESC = dISPOSITION_DESC;
	}
	public String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}
	public Integer getDISPOSITION_CODE() {
		return DISPOSITION_CODE;
	}
	public void setDISPOSITION_CODE(Integer dISPOSITION_CODE) {
		DISPOSITION_CODE = dISPOSITION_CODE;
	}
	public String getFIELDNOTES() {
		return FIELDNOTES;
	}
	public void setFIELDNOTES(String fIELDNOTES) {
		FIELDNOTES = fIELDNOTES;
	}
	public String getDISPOSITION_DATE() {
		return DISPOSITION_DATE;
	}
	public void setDISPOSITION_DATE(String dISPOSITION_DATE) {
		DISPOSITION_DATE = dISPOSITION_DATE;
	}
	public String getUPDATED_BY() {
		return UPDATED_BY;
	}
	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}
	public Date getUPDATED_DATE() {
		return UPDATED_DATE;
	}
	public void setUPDATED_DATE(Date uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
	}
	
	
	
	
	

}
