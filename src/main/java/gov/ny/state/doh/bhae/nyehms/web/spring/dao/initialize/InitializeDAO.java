package gov.ny.state.doh.bhae.nyehms.web.spring.dao.initialize;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.FirefoxVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.InitFailureReasonsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.JREVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserAccessAttemptVO;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface InitializeDAO {
	
	public List<String> getActiveMACAddressesList(String userId) throws DataAccessException;
	public String getUserLockInfo(String userId) throws DataAccessException;
	public int getActiveRoleCount(String userId) throws DataAccessException;
	public List<JREVersionVO> getActiveJREs() throws DataAccessException;
	public List<String> getActiveOSs() throws DataAccessException;
	public List<FirefoxVersionVO> getValidFirefoxVer() throws DataAccessException;
	public String getLatestFirefoxVersion()  throws DataAccessException;
	public void insertUserAccessAttemp(UserAccessAttemptVO userAccessAttemptVO)throws DataAccessException;	
	public List<InitFailureReasonsVO> getInitFailReasons() throws DataAccessException;
	public String getLatestJavaVersionNumber()  throws DataAccessException;
}
