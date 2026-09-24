package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitErrorVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitLoadLogVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 * All DAO/MyBatis method signatures used in common by the Health Information
 * Technology documents (CCD, EXP, HL7)
 * @author cdf02
 *
 */
public interface HITDocumentDAO {
	
	public int getMaxUphnTranxInId() throws DataAccessException;
	
	public void insertHitLoadLog(HitLoadLogVO hitloadlog) throws DataAccessException;
	
	public void updateHitLoadLogAfterOneRow(HitLoadLogVO hitloadlog) throws DataAccessException;
	
//	public void updateHitLoadLogAfterZeroRows(HitLoadLogVO hitloadlog) throws DataAccessException;
	
	public void updateHitLoadLogAfterAllRows(HitLoadLogVO hitloadlog) throws DataAccessException;
	
	public void insertHitError(HitErrorVO hitErrorVO) throws DataAccessException;
	
	public int getMaxUphnTranxInIdCheckedFromLog(String recordType) throws DataAccessException;
	
	
	public int insertCodedRace(Map<String, Object> inputParameters);
	
	public List<Map<String, String>> getRaceCodes();
	
	public void insertHitMessageCount() throws DataAccessException;

	public List<Map<String, Object>> getCountsForTodayMail() throws DataAccessException;

	public List<Map<String, Object>> getCountsForHITLiveEvent() throws DataAccessException;
	
	public void deleteFromUphnTranxPropsIn() throws DataAccessException;
	
	public List<Map<String, String>> getHitRaceCodeMapperData();
	
	public Map<String, String> getHitRaceValue(String parameterName);
	
	public Map<String, String> getHitRaceData();
	
	public Map<String, String> getHitEthnicityData();
	
	public List<Map<Object, Object>> getHitErrorRepriseValue(String parameterName);
	
	public void updateHitDocumentsStatus(long[] documentIds,
			String comments, String actionName, String documentType, BindingResult bindingResult,
			UserObject userObject);
		
	public int updateHitaRaceValue(Map<Object, Object> inputOutputParameterMap);
	
	public int updateHITErrorRepriseFlagStatus(Map<Object, Object> inputOutputParameterMap);
	
	public int updateHitDocumentsStatusinHITERRROR(Map<Object, Object> inputOutputParameterMap);
	
	public int checkIfRaceExists(String str);	
	
	public int updateHitErrorComments(Map<Object, Object> inputOutputParameterMap);
	
	

}
