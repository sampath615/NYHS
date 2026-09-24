package gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;




public interface FileUploadService {

//	Map<Object, Object> uploadUpdatedDispositionsFile(MultipartFile updatedDispositionFile,
//			BindingResult bindingResult, UserObject userObject);

	List<Integer> checkForExistingUpdatedMessageDocIds(
			Set<Integer> returnMessageDocIds);

	List<Integer> getReturnDocIdNotExistInTable(Set<Integer> returnMessageDocIds);

//	void insertUpdatedDispositionCode(
//			UpdatedDispositionsVO updatedDispositionsVO)throws SQLException;

//	List<Map<String, Object>> checkExistingDateOfDeathForMessageDocIds(
//			List<Integer> docIdsDateOfDeathNull);

	void uploadRIDRData(MultipartFile fileData, BindingResult bindingResult,
			UserObject userObject);

	@Transactional
	void uploadRidrDataAsBatch(String fileName, List<Map<String, Object>> ridrDataList, int workerId) throws SQLException;
	
	public Map<Object, Object> validateAndScanFile(MultipartFile fileData, BindingResult bindingResult) throws IOException;
	
	public 	Map<Object, Object> uploadClusterData(MultipartFile fileData, ClusterUploadVO clusterUploadVO, BindingResult bindingResult, UserObject userObject, HttpSession session);
	
	boolean insertTSCriteria(ClusterUploadVO clusterUploadVO, BindingResult bindingResult, UserObject userObject, HttpSession session);
	
	public boolean validateClusterId(String clusterId);
	
//	public List<String> getErrorClusterIds();
	
//	public List<ClusterUploadErrorVO> getClusterUploadErrors(String clusterId, BindingResult bindingResult, UserObject userObject);
	
//	public void updateClustEharsNyehmsStateno(long id, String clusterType, String clusterId, String uploadedEharsStateno, String uploadedNyehmsStateno, BindingResult bindingResult, UserObject userObject, HttpSession session,
//			String identificationMethod, String identificationDate);

//	public int checkForDuplicateDispositionCode(
//			UpdatedDispositionsVO updatedDispositionsVO);

	public Map<String, String> uploadRecordSearchFile(MultipartFile fileData,
			BindingResult bindingResult, UserObject userObject);

	public Map<String, List<String>> getSTATENOHistoryDataFile(MultipartFile fileData, 
			BindingResult dummyBindingResult, UserObject userObject);

	public Map<String, String> getCurrentStatenoDataFile(MultipartFile fileData,
			BindingResult bindingResult, UserObject userObject);

	public void updatedHIVDataWithNYCData(MultipartFile fileData,
			BindingResult bindingResult, UserObject userObject);

	public void identifyCDCRidrDuplicates(String fileName);	
	
	public 	Map<Object, Object> uploadPSConfirmedStdsFile(MultipartFile uploadPSConfirmSTDFile,BindingResult bindingResult, UserObject userObject) throws IOException;
	
	@Transactional
	void uploadPSConfirmedSTDData(String fileName, List<Map<String, Object>> stdConfirmDataList, int workerId) throws SQLException;
	
	public int getStdConfDocumentID();
	
	public void moveSTDConfirmFromTempToRaw();
	
	public 	Map<Object, Object> uploadNYCSurvRejectFile(MultipartFile uploadNYCRejectFile,BindingResult bindingResult, UserObject userObject) throws IOException;
	
	public int getNYCSurvRejectDocumentID();
	
	@Transactional
	void uploadNYCSurvRejectData(List<UploadNYCSurvRejectVO> nycSurvRejectList);	
}
