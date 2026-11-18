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
    boolean hasActiveRole;
    boolean hasLocked;
    boolean hasUserCompRead;
    String browserName="";
    boolean hasValidBrowser;
    boolean hasXPI;
    String browserVer="";
    Object linkClickTime="";
    String initializeFailReasons="";
    String initializeFailReasonsDescrption="";
    List<InitFailureReasonsVO> initFailReasons;
    String appletErrorCd="";
    int cryptoFailCt = 0;
    boolean isCryptoCardAuthenticationSuccess = false;

    public String getUserPerm() {
        return userPerm;
    }
    public void setUserPerm(String userPerm) {
        this.userPerm = userPerm;
    }
    public void setHasHinPerm(boolean hasHinPerm) {
        this.hasHinPerm = hasHinPerm;
    }
    public void setHasCookiesRead(boolean hasCookiesRead) {
        this.hasCookiesRead = hasCookiesRead;
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
    public void setHasValidBrowser(boolean hasValidBrowser) {
        this.hasValidBrowser = hasValidBrowser;
    }
    public boolean isHasXPI() {
        return hasXPI;
    }
    public String getBrowserVer() {
        return browserVer;
    }
    public void setBrowserVer(String browserVer) {
        this.browserVer = browserVer;
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

}
