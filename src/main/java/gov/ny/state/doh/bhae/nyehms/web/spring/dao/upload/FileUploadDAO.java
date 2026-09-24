package gov.ny.state.doh.bhae.nyehms.web.spring.dao.upload;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadErrorVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface FileUploadDAO {
	
	public int validateClusterId(String clusterId);
	
//	public List<String> getErrorClusterIds();
	
//	public List<ClusterUploadErrorVO> getClusterUploadErrors(String clusterId) throws DataAccessException;
	
//	public List<ClusterUploadErrorVO> getClusterUploadErrorsAll() throws DataAccessException;
	
	public int errorCountinUpload(int clusterIdPk);
	
//	public String getNyehmsStatenoFromNYSehars(String stateno);
	
//	public String getNyehmsStatenoFromNYCehars(String stateno);

	public void identifyCDCRidrDuplicates(String fileName);
	
	public void deleteTempSTDConfirmTableData() throws DataAccessException;
	
	public void moveSTDConfirmDocumentID() throws DataAccessException;
	
	public void moveSTDConfirmFromTempToRaw() throws DataAccessException;
	
	
	public int insertNYCSurvRejectDataRow(UploadNYCSurvRejectVO nycSurvRejectRow);

	public List<Map<String, String>> getCurrentActiveStatenoList(Map<String, List<String>> inputMap);	
	
}