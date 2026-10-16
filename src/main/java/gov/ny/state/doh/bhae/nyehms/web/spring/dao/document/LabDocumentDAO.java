package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.LabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface LabDocumentDAO {	
	public StdEcrlsLabDocVO  getLabDoc(Map<Object, Object> map)throws DataAccessException;
	public void insertLabDocData(LabDocVO stdLabDocVO)throws DataAccessException;
	public EcrlsLabDocVO getHivLabDoc(int docId);
	
	
	
	public int insertRawHivDocument(Map<Object, Object> rawDocData);

	public int insertRawStdDocument(Map<Object, Object> rawDocData);

	public int insertStdHivDocument(Map<Object, Object> stdDocData);

	public int insertStdStdDocument(Map<Object, Object> stdDocData);
	public void deleteResistanceData(int documentId);

	public int insertStandardHIVResistanceResult(Map<Object, Object> inputOutputParameterMap);
	public int restageStdDocumentNonMatches(Map<String, String> inputParameterMap);
	
	public List<Map<String, Object>> getMultiSpotDocuments(Map<String, Object> inputParameterMap);
	public List<EcrlsLabDocVO> getRawDocumentsData(
			Map<Object, Object> inputOutputParameterMap);

	public int updateStatusCodeForHivDocuments(Map<Object, Object> inputOutputParameterMap);
	
	
	public int insertStandardHIVResistanceResultFromOldDocument(
			Map<String, Integer> inputParameterMap);
	public Integer getMultiStepDocumentId(Integer documentId);
	public Map<String, Object> hivDocumentCurrentProcessStatusDetails(Integer documentId);
	public Map<String, Object> getRawDataAsObject(Integer documentId);
	public Map<String, Object> getStdDataAsObject(Integer documentId);
	public int insertRawEditedHIVLabDocumentWithUpdateData(
			Map<String, Object> originalRawData);
	public int insertStdEditedHivLabDocumentWithUpdateData(
			Map<String, Object> originalStdData);
	public void updateHashUniqueNumber(Integer documentId);
	
	public int insertStandardEcrlsRecord(StdEcrlsLabDocVO stdEcrlsLabDocVO);
	public Map<String, String> getOOSCliaDetails(String FACILITY_NAME);
	public List<Map<String, String>> getLabCombiningDocData();
	
	
}
