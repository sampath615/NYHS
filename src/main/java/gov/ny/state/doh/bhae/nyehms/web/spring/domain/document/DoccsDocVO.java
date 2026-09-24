package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;
import java.util.List;

public class DoccsDocVO {
	
	
	private String NYSID;
	private Date DATE_RECEIVED;
	private String MATCHED_STATENO;
	private String STATENO;
	private int DOCUMENT_ID;
	
	private List<DinDoccsDocVO> dinDoccsDocVO;

	public String getNYSID() {
		return NYSID;
	}

	public void setNYSID(String nYSID) {
		NYSID = nYSID;
	}

	public Date getDATE_RECEIVED() {
		return DATE_RECEIVED;
	}

	public void setDATE_RECEIVED(Date dATE_RECEIVED) {
		DATE_RECEIVED = dATE_RECEIVED;
	}

	public String getMATCHED_STATENO() {
		return MATCHED_STATENO;
	}

	public void setMATCHED_STATENO(String mATCHED_STATENO) {
		MATCHED_STATENO = mATCHED_STATENO;
	}

	public String getSTATENO() {
		return STATENO;
	}

	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}

	public List<DinDoccsDocVO> getDinDoccsDocVO() {
		return dinDoccsDocVO;
	}

	public void setDinDoccsDocVO(List<DinDoccsDocVO> dinDoccsDocVO) {
		this.dinDoccsDocVO = dinDoccsDocVO;
	} 
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}

	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}

	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	
	
		
		
		
			
		
}
