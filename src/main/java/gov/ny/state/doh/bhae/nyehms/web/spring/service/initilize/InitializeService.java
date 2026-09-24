package gov.ny.state.doh.bhae.nyehms.web.spring.service.initilize;

import java.util.List;

import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.FirefoxVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.InitFailureReasonsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.JREVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserInitObject;

public interface InitializeService {
	
	public List<String> getActiveMACAddressesList(BindingResult result, UserInitObject userInitObject, String sessionID);
	public String getUserLockInfo(BindingResult result, UserInitObject userInitObject, String sessionID);
	public int getActiveRoleCount(BindingResult result, UserInitObject userInitObject, String sessionID);
	public List<JREVersionVO> getActiveJREs(BindingResult result, UserInitObject userInitObject,String sessionID);
	public List<String> getActiveOSs(BindingResult result, UserInitObject userInitObject, String sessionID);
	public List<FirefoxVersionVO> getValidFirefoxVer(BindingResult result, UserInitObject userInitObject,String sessionID);
	public void insertUserAccessAttemp(BindingResult result, UserInitObject userInitObject, String sessionID);
	public List<InitFailureReasonsVO> getInitFailReasons(BindingResult result, UserInitObject userInitObject,String sessionID);
	public String getLatestFirefoxVersion(BindingResult result, UserInitObject userInitObject, String sessionID) ;
	public String getLatestJavaVersionNumber(BindingResult result, UserInitObject userInitObject, String sessionID) ;
}
