package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.RecordSearchResultsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;


public interface RecordSearchReviewDAO {
	
	
	public int changeRecordSearchStatus(Map<Object, Object> inOutParameterMap);

	public int checkForExistingRecordSearchStateno(
			Map<Object, Object> inOutParameterMap);

	public int addRecordSearchFlagStatusEntry(
			Map<Object, Object> inOutParameterMap);

	public void insertAutoRecordSearchEntry(
			Map<String, String> inOutParameters);

	public Map<String, String> checkRecordSearchStatenoExists(
			UpdatedDispositionsVO updatedDispositionsVO);

	public void updateAutoRecordSearchEntry(Map<String, String> inOutParameters);

	public List<JSONObject> getRecordSearchStatenos();

	public List<Map<String, String>> getRecordSearchStatenoFlagHistory(String stateno);

	public List<Map<String, String>> generateCDCReturnData(
			Map<String, Object> inParameterMap);

	public void storeCDCReturnData(Map<String, Object> inParameterMap);

	public void updateRecordSearchStatenosWithFileName(
			Map<String, Object> inParameterMap);

	public List<Map<String, String>> getCDCPendingReturnData(String cdcFileName);

	public List<Map<String, String>> getCDCPendingReturnFileNames();

	public void uploadCDCFileData(Map<String, Object> inputParameterMap);

	public int updateCDCReturnDataInfo(Map<String, Object> inputParameterMap);

	public List<String> getCDCReportingStateCodesToProcess();

	public List<RecordSearchResultsVO> getRecordSearchViewResults(
			Map<String,String> inputParameterMap);

	public int updateRecordSearchDocumentWithAcrf(
			Map<Object, Object> inputParameterMap);

	public List<Map<String, String>> downloadCDCReturnFile(String cdcFileName);

	public int checkFileNameExists(Map<String, Object> inParameterMap);

	public String getSentFileName(String acrfReferenceRowId);

	public List<String> getPendingResolutionStates(
			Map<Object, Object> inputParameterMap);

	public int replaceRecordSearchStateno(Map<Object, Object> inputParameterMap);

	public int rejectCDCFileFromSentTable(Map<String, Object> inputParameterMap);

	public int rejectCDCFileFromFlaggingTable(
			Map<String, Object> inputParameterMap);

	public int discardCDCFeedbackRecords(Map<Object, Object> inOutParameterMap);

	public void updateNoCDCRecordsInFlaggingTable(
			Map<String, Object> inParameterMap);

	public int checkNewRecordsForTheSTATENO(
			Map<Object, Object> inOutParameterMap);

	public void updateRecordSearchStatenosWithFileNameForAllRecords(
			Map<String, Object> inParameterMap);

	public List<Map<String, String>> getRecordSearchStatenoHistory(String stateNo);

	public void identifyCDCReturnFileDuplicates(Map<String, Object> inputParameterMap);

	public int checkForRIDREharsData();

	public int updateSameAsRecordsReturnFile();

	public Integer getRecordSearchSentSeq(Map<String, Object> inputParameterMap);


	public void updateRecordsWorkedPreviously();

	public void identifyCDCNoMatches(Map<String, Object> inputParameterMap);

	public void identifyCDCMatches(Map<String, Object> inputParameterMap);

	
	
}
