package gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize;

import java.util.List;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class UserInitObject extends gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userPerm="";
	boolean hasHinPerm;
	boolean hasCookiesRead;
	boolean hasActiveMAC;
	boolean hasActiveRole;
	boolean hasLocked;
	boolean hasUserCompRead;
	String browserName="";
	boolean hasValidBrowser;
	boolean hasXPI;
	String browserVer="";
	boolean hasValidFirefoxVer;
	String loginEnv="";
	String activeMACAddresses="";
	String loginMACAddress="";
	String JREVersion="";
	String OS="";
	boolean hasAuthorizedComputer;
	boolean hasAuthorizedOS;
	boolean hasAuthorizedJRE;
	boolean hasAllInitParamOk;
	Object linkClickTime="";
	String initializeFailReasons="";
	String initializeFailReasonsDescrption="";
	List<InitFailureReasonsVO> initFailReasons;
	String appletErrorCd="";
	int cryptoFailCt = 0;
	boolean isCryptoCardAuthenticationSuccess = false;
	String XPIVer="";
	
	public String getUserPerm() {
		return userPerm;
	}
	public void setUserPerm(String userPerm) {
		this.userPerm = userPerm;
	}
	public boolean isHasHinPerm() {
		return hasHinPerm;
	}
	public void setHasHinPerm(boolean hasHinPerm) {
		this.hasHinPerm = hasHinPerm;
	}
	public boolean isHasCookiesRead() {
		return hasCookiesRead;
	}
	public void setHasCookiesRead(boolean hasCookiesRead) {
		this.hasCookiesRead = hasCookiesRead;
	}
	public boolean isHasActiveMAC() {
		return hasActiveMAC;
	}
	public void setHasActiveMAC(boolean hasActiveMAC) {
		this.hasActiveMAC = hasActiveMAC;
	}
	public boolean isHasActiveRole() {
		return hasActiveRole;
	}
	public void setHasActiveRole(boolean hasActiveRole) {
		this.hasActiveRole = hasActiveRole;
	}
	public boolean isHasLocked() {
		return hasLocked;
	}
	public void setHasLocked(boolean hasLocked) {
		this.hasLocked = hasLocked;
	}
	public boolean isHasUserCompRead() {
		return hasUserCompRead;
	}
	public void setHasUserCompRead(boolean hasUserCompRead) {
		this.hasUserCompRead = hasUserCompRead;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	public boolean isHasValidBrowser() {
		return hasValidBrowser;
	}
	public void setHasValidBrowser(boolean hasValidBrowser) {
		this.hasValidBrowser = hasValidBrowser;
	}
	public boolean isHasXPI() {
		return hasXPI;
	}
	public void setHasXPI(boolean hasXPI) {
		this.hasXPI = hasXPI;
	}
	public String getBrowserVer() {
		return browserVer;
	}
	public void setBrowserVer(String browserVer) {
		this.browserVer = browserVer;
	}
	public boolean isHasValidFirefoxVer() {
		return hasValidFirefoxVer;
	}
	public void setHasValidFirefoxVer(boolean hasValidFirefoxVer) {
		this.hasValidFirefoxVer = hasValidFirefoxVer;
	}
	public String getLoginEnv() {
		return loginEnv;
	}
	public void setLoginEnv(String loginEnv) {
		this.loginEnv = loginEnv;
	}
	
	public String getActiveMACAddresses() {
		return activeMACAddresses;
	}
	public void setActiveMACAddresses(String activeMACAddresses) {
		this.activeMACAddresses = activeMACAddresses;
	}
	public String getLoginMACAddress() {
		return loginMACAddress;
	}
	public void setLoginMACAddress(String loginMACAddress) {
		this.loginMACAddress = loginMACAddress;
	}
	public String getJREVersion() {
		return JREVersion;
	}
	public void setJREVersion(String jREVersion) {
		JREVersion = jREVersion;
	}
	public String getOS() {
		return OS;
	}
	public void setOS(String oS) {
		OS = oS;
	}
	public boolean isHasAuthorizedComputer() {
		return hasAuthorizedComputer;
	}
	public void setHasAuthorizedComputer(boolean hasAuthorizedComputer) {
		this.hasAuthorizedComputer = hasAuthorizedComputer;
	}
	public boolean isHasAuthorizedOS() {
		return hasAuthorizedOS;
	}
	public void setHasAuthorizedOS(boolean hasAuthorizedOS) {
		this.hasAuthorizedOS = hasAuthorizedOS;
	}
	public boolean isHasAuthorizedJRE() {
		return hasAuthorizedJRE;
	}
	public void setHasAuthorizedJRE(boolean hasAuthorizedJRE) {
		this.hasAuthorizedJRE = hasAuthorizedJRE;
	}
	public boolean isHasAllInitParamOk() {
		return hasAllInitParamOk;
	}
	public void setHasAllInitParamOk(boolean hasAllInitParamOk) {
		this.hasAllInitParamOk = hasAllInitParamOk;
	}
	public Object getLinkClickTime() {
		return linkClickTime;
	}
	public void setLinkClickTime(Object linkClickTime) {
		this.linkClickTime = linkClickTime;
	}
	public String getInitializeFailReasons() {
		return initializeFailReasons;
	}
	public void setInitializeFailReasons(String initializeFailReasons) {
		this.initializeFailReasons = initializeFailReasons;
	}
	public String getInitializeFailReasonsDescrption() {
		return initializeFailReasonsDescrption;
	}
	public void setInitializeFailReasonsDescrption(
			String initializeFailReasonsDescrption) {
		this.initializeFailReasonsDescrption = initializeFailReasonsDescrption;
	}
	public List<InitFailureReasonsVO> getInitFailReasons() {
		return initFailReasons;
	}
	public void setInitFailReasons(List<InitFailureReasonsVO> initFailReasons) {
		this.initFailReasons = initFailReasons;
	}
	public String getAppletErrorCd() {
		return appletErrorCd;
	}
	public void setAppletErrorCd(String appletErrorCd) {
		this.appletErrorCd = appletErrorCd;
	}
	public int getCryptoFailCt() {
		return cryptoFailCt;
	}
	public void setCryptoFailCt(int cryptoFailCt) {
		this.cryptoFailCt = cryptoFailCt;
	}
	public boolean isCryptoCardAuthenticationSuccess() {
		return isCryptoCardAuthenticationSuccess;
	}
	public void setCryptoCardAuthenticationSuccess(
			boolean isCryptoCardAuthenticationSuccess) {
		this.isCryptoCardAuthenticationSuccess = isCryptoCardAuthenticationSuccess;
	}
	public String getXPIVer() {
		return XPIVer;
	}
	public void setXPIVer(String xPIVer) {
		XPIVer = xPIVer;
	}
	
}
