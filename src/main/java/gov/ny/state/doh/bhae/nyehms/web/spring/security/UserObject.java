package gov.ny.state.doh.bhae.nyehms.web.spring.security;

import java.util.Map;

public class UserObject implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userFirstName="";
	private String userLastName="";
	private String userId="";
	private String lastLogin="";
	private int workerId;
	private String userName="";
	private String userMiddleName="";
	private String userEnv="";
	private String sessionId;
	private String userFullName;
	private String emailAddress;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	private Map<String, String> regionMap;
	
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public int getWorkerId() {
		return workerId;
	}
	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getUserMiddleName() {
		return userMiddleName;
	}
	public void setUserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}
	public String getUserEnv() {
		return userEnv;
	}
	public void setUserEnv(String userEnv) {
		this.userEnv = userEnv;
	}
	
	public Map<String, String> getRegionMap() {
		return regionMap;
	}
	public void setRegionMap(Map<String, String> regionMap) {
		this.regionMap = regionMap;
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	

	
}
