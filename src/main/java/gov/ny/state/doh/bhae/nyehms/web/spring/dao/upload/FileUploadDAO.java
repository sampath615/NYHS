package gov.ny.state.doh.bhae.nyehms.web.spring.dao.upload;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadErrorVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadPSConfirmedSTDVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface FileUploadDAO {
	
	public int validateClusterId(String clusterId);
	
//	public List<String> getErrorClusterIds();
	
//	public List<ClusterUploadErrorVO> getClusterUploadErrors(String clusterId) throws DataAccessException;
	
//	public List<ClusterUploadErrorVO> getClusterUploadErrorsAll() throws DataAccessException;
	
	public int errorCountinUpload(int clusterIdPk);
	
/*	public int errorCountAfterUpdateUpload(long documentId);
	
	public int updateNewNyehmsStateno(Map<Object, Object> inOutParameterMap);
	
	public ClusterDocVO checkEharsStatenoInNysEharsNyehms(String stateno);
	
	public ClusterDocVO checkEharsStatenoInNycEharsNyehms(String stateno);
	
	public ClusterDocVO checkNyehmsStatenoInNysEharsNyehms(String stateno);
	
	public ClusterDocVO checkNyehmsStatenoInNycEharsNyehms(String stateno);

	public ClusterDocVO getDemographicByFirstResistance(String stateno);
	
	public ClusterDocVO getDemographicByMostRecentLab(String stateno);
	*/
//	public String getNyehmsStatenoFromNYSehars(String stateno);
	
//	public String getNyehmsStatenoFromNYCehars(String stateno);

	public void identifyCDCRidrDuplicates(String fileName);
	
	public void deleteTempSTDConfirmTableData() throws DataAccessException;
	
	public void moveSTDConfirmDocumentID() throws DataAccessException;
	
	public void moveSTDConfirmFromTempToRaw() throws DataAccessException;
	
	
	public int insertNYCSurvRejectDataRow(UploadNYCSurvRejectVO nycSurvRejectRow);

	public List<Map<String, String>> getCurrentActiveStatenoList(Map<String, List<String>> inputMap);	
	
}