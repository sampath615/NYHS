package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterIdReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterTSManualAddedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClustereHARSImportFlagVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

public interface ClusterDocumentService {
	
	public Map<String, String> getClusterTypeList();
	
	public ClusterDocVO getClusterDocVO(int documentId, BindingResult result, UserObject userObject);
	
//	public void updateClusterDocument(Map<String, String> inputData, int documentId, String documentType, String SOFT_ERROR_ONLY, BindingResult bindingResult, UserObject userObject);
	
	public List<JSONObject> getClusterIdsByType(String clusterType);
	public List<JSONObject> getExistingClusterIdsByType(String clusterType);
	public JSONObject getClusterData(String clusterId);
	
	public List<ClusterDocReportVO> getClusterReportsByTypeAndId(String clusterType, String clusterId, BindingResult bindingResult, UserObject userObject);
	
	@Transactional
	public ClusterIdReportVO getClusterIdReports(String clusterType, String clusterId, String showDocuments, BindingResult bindingResult, UserObject userObject);
	
//	public void insertClusterDataFromPersonView(String stateno, String clusterDetails, BindingResult bindingResult, UserObject userObject, HttpSession session);
	
	public int getClusterDocumentID();
	
	public void insertIntoRawCluster(Map<String, Object> clusterDetailsMap, UserObject userObject);
	
	public void insertIntoStdCluster(Map<String, Object> clusterDetailsMap, ClusterDocVO currentDemoInfo, UserObject userObject, BindingResult bindingResult);
	
	public void moveStdDocumentToDocAttrTables(long documentId, UserObject userObject);
	
//	public void updateClustEharsNyehmsStateno(Map<String, Object> clusterDetailsMap, ClusterDocVO currentDemoInfo, UserObject userObject, BindingResult bindingResult);
	
//	public ClusterDocVO getDemographicFromEhars(String clusterType, String uploadedStateno);
	
//	public ClusterDocVO getStatenoDemographicInfo(String clusterType, String uploadedStateno, String clusterId);	
	
//	public ClusterDocVO getStatenoDemographicInfoToaddCluster(String clusterType, String uploadedStateno, String clusterId);		
	
	public void insertNyehmsUploadCluster(ClusterUploadVO clusterUploadVO, BindingResult bindingResult,UserObject userObject, HttpSession session); 
	
	public void insertIntoRawStdCluster(List<Map<String, Object>> clusterDataList, ClusterUploadVO clusterUploadVO, BindingResult bindingResult, UserObject userObject, HttpSession session);
	
//	public String checkEharsStatenoValidity(String uploadedStateno);
	
//	public void sendEmailNotificationToClusterUsers(String clusterId);

	public List<String> getRelatednessData();
	
	public Map<String, String> getMoleTimeFrameData();	
	
	public void bhaeTSCriteriaProcess(ClusterUploadVO clusterUploadVO);
	
//	public List<String> getManualAddedStatenosTSClusterIds();
	
//	public List<ClusterTSManualAddedStatenoVO> getManualAddedStatenosTSClusterInfo(String clusterId, BindingResult bindingResult, UserObject userObject);
	
//	public void updateTSManualAddedStateno(String clusterId, String[] selectedManualAddedtatenos, UserObject userObject, BindingResult bindingResult);
	
	public List<Map<String, String>> getClusterIDentificationMethod();
	
	public void updateClustereHARSImportFlag(int[] clusterIdPks, UserObject userObject, BindingResult bindingResult);	
	
	public List<ClustereHARSImportFlagVO> geteHARSImportFlagData(UserObject userObject, BindingResult bindingResult);
	
	public JSONObject getClusterDisplayInfo(String clusterId);
	
	public List<JSONObject> getMolClusterDemoDocumentIds(String stateNo, String identificationCd, String notAssocClusterDetails);	
	
	public List<Integer> getLinkedMolClusterGenoTypeDocuments(int documentId);
	
	public Map<String, String> getTransmissionCategoryData();	
	
	public Map<String, String> getEharsRaceValues();	
	
	public Map<String, String> getEharsEthnicityValues();
	
	public void validateStatenoWithCluster(Map<String, Object> inputParameterMap, BindingResult bindingResult);

	public void associateStatenoToCluster(Map<String, Object> inputParameterMap, BindingResult bindingResult);	
	
}
