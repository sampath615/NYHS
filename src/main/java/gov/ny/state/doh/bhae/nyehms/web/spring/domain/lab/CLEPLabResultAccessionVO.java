package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class CLEPLabResultAccessionVO {
	
	private int surveyNumber;
	private String accessionNo;
	private int accessionSerialNo;
	private String collectionDate;

	private boolean hivImmunoassay;
	private boolean hivNAT;
	private boolean cd4;
	private boolean cluture;
	private boolean supImmunoassay;
	private boolean genotypicResistance;
	private boolean other;
	
	private String resultRptNyehms;
	private String resolved;
	private String comments;
	private String laftStaff;
	private String dateResolved;

	public int getSurveyNumber() {
		return surveyNumber;
	}
	public void setSurveyNumber(int surveyNumber) {
		this.surveyNumber = surveyNumber;
	}
	public String getAccessionNo() {
		return accessionNo;
	}
	public void setAccessionNo(String accessionNo) {
		this.accessionNo = accessionNo;
	}
	public int getAccessionSerialNo() {
		return accessionSerialNo;
	}
	public void setAccessionSerialNo(int accessionSerialNo) {
		this.accessionSerialNo = accessionSerialNo;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public boolean isHivImmunoassay() {
		return hivImmunoassay;
	}
	public void setHivImmunoassay(boolean hivImmunoassay) {
		this.hivImmunoassay = hivImmunoassay;
	}
	public boolean isHivNAT() {
		return hivNAT;
	}
	public void setHivNAT(boolean hivNAT) {
		this.hivNAT = hivNAT;
	}
	public boolean isCd4() {
		return cd4;
	}
	public void setCd4(boolean cd4) {
		this.cd4 = cd4;
	}
	public boolean isCluture() {
		return cluture;
	}
	public void setCluture(boolean cluture) {
		this.cluture = cluture;
	}
	public boolean isSupImmunoassay() {
		return supImmunoassay;
	}
	public void setSupImmunoassay(boolean supImmunoassay) {
		this.supImmunoassay = supImmunoassay;
	}
	public boolean isGenotypicResistance() {
		return genotypicResistance;
	}
	public void setGenotypicResistance(boolean genotypicResistance) {
		this.genotypicResistance = genotypicResistance;
	}
	public boolean isOther() {
		return other;
	}
	public void setOther(boolean other) {
		this.other = other;
	}
	public String getResultRptNyehms() {
		return resultRptNyehms;
	}
	public void setResultRptNyehms(String resultRptNyehms) {
		this.resultRptNyehms = resultRptNyehms;
	}
	public String getResolved() {
		return resolved;
	}
	public void setResolved(String resolved) {
		this.resolved = resolved;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getLaftStaff() {
		return laftStaff;
	}
	public void setLaftStaff(String laftStaff) {
		this.laftStaff = laftStaff;
	}
	public String getDateResolved() {
		return dateResolved;
	}
	public void setDateResolved(String dateResolved) {
		this.dateResolved = dateResolved;
	}

}
