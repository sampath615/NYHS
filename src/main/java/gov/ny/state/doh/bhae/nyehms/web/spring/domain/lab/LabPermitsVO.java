package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;

public class LabPermitsVO {
	private String pfi;
	private String labPermitId;
	private String permitCode;
	private String permitDescription;
	private String dateValidFrom;
	private String dateValidTill;
	private String bhaeCertDate;
	private String permitFlagActive;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String reportingStatus;
	private String comments;
	
	private List<LabInstrumentDetailsVO>  instrumentDetails;
	
	public String getLabPermitId() {
		return labPermitId;
	}
	public void setLabPermitId(String labPermitId) {
		this.labPermitId = labPermitId;
	}
	public String getPermitCode() {
		return permitCode;
	}
	public void setPermitCode(String permitCode) {
		this.permitCode = permitCode;
	}
	public String getPermitDescription() {
		return permitDescription;
	}
	public void setPermitDescription(String permitDescription) {
		this.permitDescription = permitDescription;
	}
	public String getDateValidFrom() {
		return dateValidFrom;
	}
	public void setDateValidFrom(String dateValidFrom) {
		this.dateValidFrom = dateValidFrom;
	}
	public String getDateValidTill() {
		return dateValidTill;
	}
	public void setDateValidTill(String dateValidTill) {
		this.dateValidTill = dateValidTill;
	}
	public String getBhaeCertDate() {
		return bhaeCertDate;
	}
	public void setBhaeCertDate(String bhaeCertDate) {
		this.bhaeCertDate = bhaeCertDate;
	}
	public String getPermitFlagActive() {
		return permitFlagActive;
	}
	public void setPermitFlagActive(String permitFlagActive) {
		this.permitFlagActive = permitFlagActive;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public List<LabInstrumentDetailsVO> getInstrumentDetails() {
		return instrumentDetails;
	}
	public void setInstrumentDetails(List<LabInstrumentDetailsVO> instrumentDetails) {
		this.instrumentDetails = instrumentDetails;
	}
	public String getPfi() {
		return pfi;
	}
	public void setPfi(String pfi) {
		this.pfi = pfi;
	}
	public String getReportingStatus() {
		return reportingStatus;
	}
	public void setReportingStatus(String reportingStatus) {
		this.reportingStatus = reportingStatus;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
