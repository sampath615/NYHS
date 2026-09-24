package gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration;

public class SystemMessage {
	private long messageId;
	private String messageBody;
	private long workerId;
	private String effectiveStartDate;
	private String effectiveEndDate;
	private char deleted;
	private String messageAction;
	private String createdBy;
	private String lastUpdatedDate;
	private long lastUpdatedBy;
	private String lastUpdatedByName;
	private char historyFlag;
	private String createdByName;
	private String lastUpdatedByShortName;
	private String createdByShortName;
	
	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}

	public String getMessageAction() {
		return messageAction;
	}

	public void setMessageAction(String messageAction) {
		this.messageAction = messageAction;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	public char getDeleted() {
		return deleted;
	}
	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}

	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public String getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public char getHistoryFlag() {
		return historyFlag;
	}
	public void setHistoryFlag(char historyFlag) {
		this.historyFlag = historyFlag;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedByShortName() {
		return lastUpdatedByShortName;
	}

	public void setLastUpdatedByShortName(String lastUpdatedByShortName) {
		this.lastUpdatedByShortName = lastUpdatedByShortName;
	}

	public String getCreatedByShortName() {
		return createdByShortName;
	}

	public void setCreatedByShortName(String createdByShortName) {
		this.createdByShortName = createdByShortName;
	}
	
}
