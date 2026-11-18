package gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class UserAccessAttemptVO {
	
	String userId = "";
	String hcsRole = "";
	Object linkClickTime = "";
	String activeNyehmsRole = "";
	String userLocked = "";
	String browserName = "";
	String browserVersion ="";
	String XPIPlugin = "";
	String XPIVersion = "";
	String attemptFailedReasons = "";
	Object recordInsertTime = "";
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHcsRole() {
		return hcsRole;
	}
	public void setHcsRole(String hcsRole) {
		this.hcsRole = hcsRole;
	}
	public Object getLinkClickTime() {
		return linkClickTime;
	}
	public void setLinkClickTime(Object linkClickTime) {
		this.linkClickTime = linkClickTime;
	}
	public String getActiveNyehmsRole() {
		return activeNyehmsRole;
	}
	public void setActiveNyehmsRole(String activeNyehmsRole) {
		this.activeNyehmsRole = activeNyehmsRole;
	}
	public String getUserLocked() {
		return userLocked;
	}
	public void setUserLocked(String userLocked) {
		this.userLocked = userLocked;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	public String getBrowserVersion() {
		return browserVersion;
	}
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	public String getXPIPlugin() {
		return XPIPlugin;
	}
	public void setXPIPlugin(String xPIPlugin) {
		XPIPlugin = xPIPlugin;
	}
	public String getXPIVersion() {
		return XPIVersion;
	}
	public void setXPIVersion(String xPIVersion) {
		XPIVersion = xPIVersion;
	}
	public String getAttemptFailedReasons() {
		return attemptFailedReasons;
	}
	public void setAttemptFailedReasons(String attemptFailedReasons) {
		this.attemptFailedReasons = attemptFailedReasons;
	}
	public Object getRecordInsertTime() {
		return recordInsertTime;
	}
	public void setRecordInsertTime(Object recordInsertTime) {
		this.recordInsertTime = recordInsertTime;
	}
	
}
