package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitLoadLogVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;

public interface HITCommonDocumentService {

	public void storeHitError(Throwable e, Integer uphnId, String decryptedPayload, String recordType,String repriseFlag, String comments);

	public void insertCodedRace(Set<String> raceCodeErrors);

	public List<Map<String, String>> getRaceCodes();

	public void extractNewPayloadsFromUphnToRaw(List<JSONObject> schedulerStatusObject, String recordType,
			Map<String, String> schedulerMetaData, String initiator);

	public void updateHitLoadLogAfterOneRow(HitLoadLogVO theNewLoadLogVO);

	public void updateHitLoadLogAfterAllRows(HitLoadLogVO theNewLoadLogVO);

	public void insertHitMessageCount() throws DataAccessException;

	public List<Map<String, Object>> getCountsForTodayMail() throws DataAccessException;

	public List<Map<String, Object>> getCountsForHITLiveEvent() throws DataAccessException;

	public void deleteFromUphnTranxPropsIn() throws DataAccessException;

	public Map<String, String> getHitRaceCodeMapperData();

	public Map<String, String> getHitRaceValue(String parameterName);
	
	public Map<String, String> getHitRaceData();
	
	public Map<String, String> getHitEthnicityData();
	
	public List<Map<Object, Object>> getHitErrorRepriseValue(String parameterName);

	public void updateHitRaceValue(String parameterName, String[] parameterValue1, String parameterValue2,
			BindingResult bindingResult, String UserId);

	// HIT_ERROR_REPRISE
	public void extractHitErrorFromUphnToRaw(List<JSONObject> schedulerStatusObject, String recordType,
			Map<String, String> schedulerMetaData, String initiator);
	
	public void updateHITErrorRepriseFlagStatus(int uphnId, String statusCode);

	public void updateHitDocumentsStatus(long[] documentIds, String actionName, String documentType,
			BindingResult bindingResult, UserObject userObject);
	
	public String getUphnRowsWithJdbc(int lastCheckedUtii, int maxUtiiToCheck, int rowFetchLimit, String recordType,
			HitLoadLogVO theNewHitLoadLogVO);
	
	public void updateHitErrorComments(long id, String notes,
			BindingResult bindingResult, UserObject userObject);
	
	public void writeLogToDB(String recordType, String queryType, String action);
	
}
