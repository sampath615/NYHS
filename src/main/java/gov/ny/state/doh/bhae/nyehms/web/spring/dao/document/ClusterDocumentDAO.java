package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocPSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterTSManualAddedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterTemplateVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClustereHARSImportFlagVO;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

public interface ClusterDocumentDAO {
	
	public ClusterDocVO getClusterDoc(Map<Object, Object> inputData) throws DataAccessException;
	
//	public int updateClusterDocument(Map<String, String> inputData);
	
	public List<JSONObject> getClusterIdsByType(String clusterType);
	public List<JSONObject> getExistingClusterIdsByType(Map<String, String>  existingClusterType);
	public JSONObject getClusterData(String clusterId);
	
	public List<ClusterDocReportVO> getClusterReportsByTypeAndId(Map<String, String> ReportInputData) throws DataAccessException;
	
	public void getClusterIdReport(Map<Object,Object> map) throws DataAccessException;
	
	public String getDocumentClusterType(int documentId) throws DataAccessException;
	
	public String isResistanceRecord(String stateno) throws DataAccessException;
	
	public int insertDataIntoClusterRaw(Map<String, Object> clusterDataDetails);
	
	public int insertDataIntoClusterStd(Map<String, Object> clusterDataDetails);
	
//	public int updateRawClusterStatus(Map<String, Object> clusterDataDetails);
	
	public int insertNyehmsUploadCluster(Map<String, Object> clusterDataDetails);
	
	public int insertNyehmsUploadClusterFiles(Map<String, Object> clusterDataDetails);	
	
	public List<ClusterDocPSMessageVO> getClusterPSMessageInfo(String stateno) throws DataAccessException;
	
//	public Map<String, String> getBHAETSDates(String clusterId);
	
	public int getClusterPkNumber(String clusterId);
	
	public List<String> getRelatednessData();
	
	public List<Map<String, String>> getMoleTimeFrameData() throws DataAccessException;
	
	public int insertNyehmsTimeSpaceCriteria(Map<String, Object> clusterDataDetails);
	
	public int retireNyehmsTimeSpaceCluster(Map<String, Object> clusterDataDetails);
		
	public void bhaeTSCriteriaProcess(String clusterIdPk) throws DataAccessException;
	
//	public List<String> getManualAddedStatenosTSClusterIds();
	
//	public List<ClusterTSManualAddedStatenoVO> getManualAddedStatenosTSClusterInfo(String clusterId) throws DataAccessException;
	
//	public int updateTSManualAddedStateno(Map<String, Object> manaulAddedStatenoMap);
	
	public List<Map<String, String>> getClusterIDentificationMethod();
	
	public List<ClusterTemplateVO> getAlreadyUploadedClusterData(int clusterIdPk) throws DataAccessException;
	
	public int updateClustereHARSImportFlag(Map<String, Object> eHARSImportFlagMap);	
	
	public List<ClustereHARSImportFlagVO> geteHARSImportFlagData() throws DataAccessException;
	
	public JSONObject getClusterDisplayInfo(String clusterId);
	
	public int validateClusterDocumentId(Map<String, Object> clusterDataDetails);
	
	public ClusterDocVO getStatenoDemographic(Map<String, Object> inputParameter);
	
	public List<JSONObject> getMolClusterDemoDocumentIds(Map<String, Object> inputParameter);
	
	public List<Integer> getLinkedMolClusterGenoTypeDocuments(int documentId);
	
	public List<JSONObject> getCDCMolClusterRecordSearchRIDRDemoDocumentIds(Map<String, Object> inputParameter);
	
	public List<Map<String, String>> getTransmissionCategoryData() throws DataAccessException;
	
	public List<Map<String, String>> getEharsRaceData() throws DataAccessException;
	
	public List<Map<String, String>> getEharsEthnicityData() throws DataAccessException;

	public List<String> isEharsStatenoExist(Map<String, Object> inputParameterMap);

	public int isStatenoWithCluster(Map<String, Object> inputParameterMap);

	public int associateStatenoWithCluster(Map<String, Object> inputParameterMap);

	public int isStatenoActive(Map<String, Object> inputParameterMap);

	public int isStatenoAssociated(Map<String, Object> inputParameterMap);

}
