package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ecrls;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;

import java.util.List;
import java.util.Map;



/**
 * This DAO is used to give the required information for the Quartz scheduler
 * @author ska03
 *
 */
public interface EcrlsDataDAO {
	
	/**
	 * This method is used to check the Permissions to access the Quartz controller page.
	 * 
	 * If user is having a Active Coordinator Role, then will give the access to show the page.
	 * 
	 *  If user is having the role will return count as 1(We can have only one Coordinator role per user).
	 *  
	 *  If user is not having the role will return 0
	 * @param workerId 
	 * @return
	 */
	public List<EcrlsLabDocVO> getEclrsRawDatatoClean(Map<Object, Object> inOutParameterMap);
	
	
	public List<EcrlsLabDocVO> getEcrlsReAssignDocData(Map<Object, Object> inOutParameterMap);


	public List<EcrlsLabDocVO> getPIDUpdatedRecordsFromEclrs(
			Map<Object, Object> inOutParameterMap);


	public List<EcrlsLabDocVO> getErroredPIDDocumentsListFromEclrs(
			Map<String, Object> inOutParameterMap);


	public void updateEcrlsProcessedRecords(Map<Object, Object> inOutParameterMap);
	
}
