package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class HitLoadLogVO 
{
	private int logID;
	private String docType = "HIT";
	private int minUtiiChecked = 0;
	private int minUtiiRetrieved = 0;
	private int maxUtiiRetrieved = 0;
	private int maxUtiiChecked = 0;
	private int noOfFailedDocs = 0;
	private int noOfRetrievedDocs = 0;
	private int noOfSuccessfulDocs = 0;
	private Date createDate = new Date();
	private String createId = "NYEHMS";

	public int getLogID() {
		return logID;
	}
	/**
	 * The HIT_LOAD_LOG table has a trigger that automatically sets the
	 * LOG_ID field on insert, so there should never be any reason to use
	 * this method.
	 * @param logID
	 */
	public void setLogID(int logID) {
		this.logID = logID;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public int getMinUtiiChecked() {
		return minUtiiChecked;
	}
	public void setMinUtiiChecked(int minUtiiChecked) {
		this.minUtiiChecked = minUtiiChecked;
	}
	public int getMinUtiiRetrieved() {
		return minUtiiRetrieved;
	}
	public void setMinUtiiRetrieved(int minUtiiRetrieved) {
		this.minUtiiRetrieved = minUtiiRetrieved;
	}
	public int getMaxUtiiRetrieved() {
		return maxUtiiRetrieved;
	}
	public void setMaxUtiiRetrieved(int maxUtiiRetrieved) {
		this.maxUtiiRetrieved = maxUtiiRetrieved;
	}
	public int getMaxUtiiChecked() {
		return maxUtiiChecked;
	}
	public void setMaxUtiiChecked(int maxUtiiChecked) {
		this.maxUtiiChecked = maxUtiiChecked;
	}
	public int getNoOfFailedDocs() {
		return noOfFailedDocs;
	}
	public void setNoOfFailedDocs(int noOfFailedDocs) {
		this.noOfFailedDocs = noOfFailedDocs;
	}
	public int getNoOfRetrievedDocs() {
		return noOfRetrievedDocs;
	}
	public void setNoOfRetrievedDocs(int noOfRetrievedDocs) {
		this.noOfRetrievedDocs = noOfRetrievedDocs;
		if (noOfRetrievedDocs == 0) {
			noOfSuccessfulDocs = 0;
			noOfFailedDocs = 0;
		}
	}
	public int getNoOfSuccessfulDocs() {
		return noOfSuccessfulDocs;
	}
	public void setNoOfSuccessfulDocs(int noOfSuccessfulDocs) {
		this.noOfSuccessfulDocs = noOfSuccessfulDocs;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this)
				 + "\n*************************************";
	}
}
