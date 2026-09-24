package gov.ny.state.doh.bhae.nyehms.web.spring.dao.sierra;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public interface SierraResponseDAO {

	int getLastParsedRasDocId();

	List<Map<String, Object>> getRasDocIdsToParse(Map<String, Integer> inputParameterMap);

	int storeSequenceHeaderInformation(Map<String, Object> inputParameterMap);

	int storeValidationResults(Map<String, Object> inputParameterMap);

	int storeGenSeqInfromation(Map<String, Object> geneSeqInfo);

	int storeMutation(Map<String, Object> inputMutation);

	List<Map<String, Object>> getDrugCodedList();

	int storeDrugDetails(Map<String, Object> inputDrug);

	int storeDrugPartialData(Map<String, Object> drupgPartialListMap);

	void addErrorDocument(Map<String, Object> inputParameterMap);

	void addSierraLoadLog(Map<String, Object> logParameter);

	List<JSONObject> loadActiveFailedRasDocuments();

	int isLabDocIdExist(Integer rasDocId);

	int isLabDocIdExistInErrorTable(Integer rasDocId);

	int updateRASSelectedDocumentStatus(Map<Object, Object> inOutParameterMap);

	void processRepriseDocuemnt(Integer rasDocId);

	void loadMissingRasDocuments();

	void loadNewSierraDataToFinalTable();	
}
