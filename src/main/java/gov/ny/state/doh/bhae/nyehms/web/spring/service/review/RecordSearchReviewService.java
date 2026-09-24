package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.RecordSearchResultsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface RecordSearchReviewService {


	public boolean changeRecordSearchStatus(String stateno,
			String requestComments, String origin, String flagStatus,
			BindingResult bindingResult, UserObject userObject);

	public void insertAutoRecordSearchEntry(
			UpdatedDispositionsVO updatedDispositionsVO);

	public List<JSONObject> getRecordSearchStatenos(
			BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getRecordSearchStatenoFlagHistory(
			String stateno, BindingResult bindingResult, UserObject userObject);

	public int changeRecordSearchStatusForMultipleStatenos(
			String[] statenoList, String requestComments,
			BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> generateCDCReturnData(String[] statenoList, String cdcFileName,
			BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getCDCPendingReturnData(String cdcFileName,
			BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getCDCPendingReturnFileNames();

	public void uploadCDCFileData(String inputFileName,
			MultipartFile uploadedFile, BindingResult bindingResult,
			UserObject userObject) throws IOException;

	public List<String> getCDCReportingStateCodesToProcess();

	public List<RecordSearchResultsVO> getRecordSearchViewResults(
			String stateValue);

	public void updateRecordSearchDocumentWithAcrf(String acrfReferenceRowId,
			List<String> duplicateStatenos, String stateno, int documentId, BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> downloadCDCReturnFile(String cdcFileName,
			BindingResult bindingResult, UserObject userObject);

	public void replaceRecordSearchStateno(String recSearchSourceStateno,
			String recSearchStatenoToReplace, String recSearchComments);

	public int rejectCDCFileFromSentTable(String cdcFileName,
			UserObject userObject);

	public int rejectCDCFileFromFlaggingTable(String cdcFileName,
			UserObject userObject);

	public void discardCDCFeedbackRecords(String[] cdcRecords,
			BindingResult bindingResult, UserObject userObject);
	
	public List<Map<String, String>> generateCDCReturnDataForAllTheRecords(String cdcFileName,
			BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getRecordSearchStatenoHistory(String stateNo,
			BindingResult bindingResult, UserObject userObject);
}
