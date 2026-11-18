package gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration;

import java.util.List;

public class SystemMessageArchive {
private long messageId;
private List<SystemMessage> systemMessageArchives;
public List<SystemMessage> getSystemMessageArchives() {
	return systemMessageArchives;
}
public void setSystemMessageArchives(List<SystemMessage> systemMessageArchives) {
	this.systemMessageArchives = systemMessageArchives;
}
public long getMessageId() {
	return messageId;
}
public void setMessageId(long messageId) {
	this.messageId = messageId;
}
}
