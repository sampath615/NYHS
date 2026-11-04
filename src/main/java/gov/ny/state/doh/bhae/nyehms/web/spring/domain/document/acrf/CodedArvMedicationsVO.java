package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf;

public class CodedArvMedicationsVO {
	private String arvSrcId;
	public String arvUseType;
	private String arvMedications="";
	private String arvMedicationsDateBegan;
	private String arvMedicationsDateOfLastUse;
	private String otherArvUseType;
	private int documentId;
	
	
	public String getArvSrcId() {
		return arvSrcId;
	}
	public void setArvSrcId(String arvSrcId) {
		this.arvSrcId = arvSrcId;
	}
	public String getArvUseType() {
		return arvUseType;
	}
	public void setArvUseType(String arvUseType) {
		this.arvUseType = arvUseType;
	}
	public String getArvMedications() {
		return arvMedications;
	}
	
	public void setArvMedications(String arvMedications) {
		this.arvMedications = arvMedications;
	}
	public String getArvMedicationsDateBegan() {
		return arvMedicationsDateBegan;
	}
	public void setArvMedicationsDateBegan(String arvMedicationsDateBegan) {
		this.arvMedicationsDateBegan = formatMaskDate(arvMedicationsDateBegan);
	}
	public String getArvMedicationsDateOfLastUse() {
		return arvMedicationsDateOfLastUse;
	}
	public void setArvMedicationsDateOfLastUse(String arvMedicationsDateOfLastUse) {
		this.arvMedicationsDateOfLastUse = formatMaskDate(arvMedicationsDateOfLastUse);
	}
	public String getOtherArvUseType() {
		return otherArvUseType;
	}
	public void setOtherArvUseType(String otherArvUseType) {
		this.otherArvUseType = otherArvUseType;
	}
	
	private String formatMaskDate(String _date) {
		
		if("../../....".equals(_date)){
			return null;
		}else{
			return _date;
		}
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	
	
}
