package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Blob;
import java.sql.Date;

public class UphnTranxInVo {

	private int uphnTranxInId;
	private Blob payloadBinaryContent;
	private int payloadSize;
	private String payloadName; //Single string, no first/middle/last
	private String uuid;
	private String payloadTrackingId;
	private String senderId;
	private String orgName;
	private String topic;
	private String transport;
	private String arguments;
	private Date receivedTime;
	private Date lastUpdateTime;
	private Date lastReadTime;
	private int maxUtiiRetrieved;
	private int minUtiiRetrieved;
	public int getUphnTranxInId() {
		return uphnTranxInId;
	}
	public void setUphnTranxInId(int uphnTranxInId) {
		this.uphnTranxInId = uphnTranxInId;
	}
	public Blob getPayloadBinaryContent() {
		return payloadBinaryContent;
	}
	public void setPayloadBinaryContent(Blob payloadBinaryContent) {
		this.payloadBinaryContent = payloadBinaryContent;
	}
	public int getPayloadSize() {
		return payloadSize;
	}
	public void setPayloadSize(int payloadSize) {
		this.payloadSize = payloadSize;
	}
	public String getPayloadName() {
		return payloadName;
	}
	public void setPayloadName(String payloadName) {
		this.payloadName = payloadName;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPayloadTrackingId() {
		return payloadTrackingId;
	}
	public void setPayloadTrackingId(String payloadTrackingId) {
		this.payloadTrackingId = payloadTrackingId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getTransport() {
		return transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	public String getArguments() {
		return arguments;
	}
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	public Date getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Date getLastReadTime() {
		return lastReadTime;
	}
	public void setLastReadTime(Date lastReadTime) {
		this.lastReadTime = lastReadTime;
	}
	public int getMaxUtiiRetrieved() {
		return maxUtiiRetrieved;
	}
	public void setMaxUtiiRetrieved(int maxUtiiRetrieved) {
		this.maxUtiiRetrieved = maxUtiiRetrieved;
	}
	public int getMinUtiiRetrieved() {
		return minUtiiRetrieved;
	}
	public void setMinUtiiRetrieved(int minUtiiRetrieved) {
		this.minUtiiRetrieved = minUtiiRetrieved;
	}
			
	
}
