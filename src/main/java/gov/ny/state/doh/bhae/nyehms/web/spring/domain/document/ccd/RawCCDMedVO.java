package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RawCCDMedVO {
	
	private int uphnId;
	private int ccdDocID;
	private int medSeqNo;
	private int medId;
	private String orderedMedicationName;
	private String startDateText;
	private String stopDateText;
	private String dosageValue;
	private String dosageUnit;
	private String filledMedicationName;
	private String currentMedication;
	private RawCCDNameVO orderingProvName;
	private String frequency;
	
	/*For CCD_DOC_VIEWER ONLY*/
	private String providerFullName;

	public int getUphnId() {
		return uphnId;
	}
	public void setUphnId(int uphnId) {
		this.uphnId = uphnId;
	}
	public int getCcdDocID() {
		return ccdDocID;
	}
	public void setCcdDocID(int ccdDocID) {
		this.ccdDocID = ccdDocID;
	}
	public int getMedSeqNo() {
		return medSeqNo;
	}
	public void setMedSeqNo(int medSeqNo) {
		this.medSeqNo = medSeqNo;
	}
	public int getMedId() {
		return medId;
	}
	public void setMedId(int medId) {
		this.medId = medId;
	}
	public String getOrderedMedicationName() {
		return orderedMedicationName;
	}
	public void setOrderedMedicationName(String orderedMedicationName) {
		this.orderedMedicationName = orderedMedicationName;
	}
	public String getStartDateText() {
		return startDateText;
	}
	public void setStartDateText(String startDateText) {
		this.startDateText = startDateText;
	}
	public String getStopDateText() {
		return stopDateText;
	}
	public void setStopDateText(String stopDateText) {
		this.stopDateText = stopDateText;
	}
	public String getDosageValue() {
		return dosageValue;
	}
	public void setDosageValue(String dosageValue) {
		this.dosageValue = dosageValue;
	}
	public String getDosageUnit() {
		return dosageUnit;
	}
	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}
	public String getFilledMedicationName() {
		return filledMedicationName;
	}
	public void setFilledMedicationName(String filledMedicationName) {
		this.filledMedicationName = filledMedicationName;
	}
	public String getCurrentMedication() {
		return currentMedication;
	}
	public void setCurrentMedication(String currentMedication) {
		this.currentMedication = currentMedication;
	}
	public RawCCDNameVO getOrderingProvName() {
		return orderingProvName;
	}
	public void setOrderingProvName(RawCCDNameVO orderingProvName) {
		this.orderingProvName = orderingProvName;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getProviderFullName() {
		return providerFullName;
	}
	public void setProviderFullName(String providerFullName) {
		this.providerFullName = providerFullName;
	}
	public String toString() {
			return ReflectionToStringBuilder.toString(this);
	}

}
