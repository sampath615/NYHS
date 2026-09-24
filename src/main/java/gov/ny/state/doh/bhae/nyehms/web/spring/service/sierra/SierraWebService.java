package gov.ny.state.doh.bhae.nyehms.web.spring.service.sierra;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import com.google.gson.JsonObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 * class to load sierra response to RAS tables 
 * 
 * @author ska03
 *
 */
public interface SierraWebService {

	void loadRASDocumentsSierraResponse();

	List<Map<String, Object>> getRasDocIdsToParse(int maxDocumentId);

	JsonObject getSierraRasResponse(Integer rasDocId, String sequence)throws IOException;

	Integer parseAndStoreHeaderElements(String version, String publishDate, JsonObject rasJsonResponse);

	void parseAndStoreValidationResults(Integer SEQ_RESULT_ID, JsonObject rasJsonResponse);

	void parseAndStoreGeneInformation(Integer sEQ_RESULT_ID, JsonObject rasJsonResponse);

	void parseAndStoreDrugInformation(Integer sEQ_RESULT_ID, JsonObject rasJsonResponse);

	void loadSierraDrugCodeMap();

	void addErrorDocument(Map<String, Object> inputParameterMap);

	String sendLogInformation(Integer minDocumentIdParsed, Integer maxDocumentIdParsed, Map<Integer, String> failedDocumentIds, int totalDocCount, String workerId, boolean insertionFailed);

	List<JSONObject> loadFailedRasDocuments(BindingResult bindingResult, UserObject userObject);

	String runRasJob(Integer dOCUMENT_ID, BindingResult bindingResult, UserObject userObject);

	int isLabDocIdExist(Integer rasDocId);

	int isLabDocIdExistInErrorTable(Integer rasDocId);

	void updateRASSelectedDocumentStatus(long[] documentIds, String comments, String actionName,
			BindingResult bindingResult, UserObject userObject);

	void processRepriseDocuemnt(Integer rasDocId);

	void parseSierraData(String version, String publishDate, JsonObject rasJsonResponse);

	void loadMissingRasDocuments();

	void loadNewSierraDataToFinalTable();
}
