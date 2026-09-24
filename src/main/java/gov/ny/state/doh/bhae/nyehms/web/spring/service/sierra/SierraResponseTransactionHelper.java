package gov.ny.state.doh.bhae.nyehms.web.spring.service.sierra;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

/**
 * This class provides helper methods for Parsing Sierra response.
 * 
 * @author SKA03
 */
@Component
public class SierraResponseTransactionHelper {

	private static Logger log = LoggerFactory.getLogger(SierraResponseTransactionHelper.class);

	//private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();	
	
	@Autowired	CommonExceptionHandler commonExceptionHandler;	
	
	@Autowired SierraWebService sierraWebService;
	


	public  String loadSierraResponseData(int maxDocumentId, UserObject userObject) {
		
		
		sierraWebService.loadMissingRasDocuments();
		
		
		List<Map<String, Object>> rasDocListToParse = sierraWebService.getRasDocIdsToParse(maxDocumentId);
		
		if(rasDocListToParse.size() == 0) {
			//return message to the Users
			return "<h2 class='errorTransactionMessage' style='font-weight:bold'>There is no more documents to Parse</h2>";
		}
		
		Map<Integer, String> failedDocumentIds = new HashMap<Integer, String>();
		
		Integer maxDocumentIdParsed =maxDocumentId;
		Integer minDocumentIdParsed =null;
		
		String callingSource = null;
		
		
		sierraWebService.loadSierraDrugCodeMap();
		
		String workerId = "NYEHMS";
		
		if(userObject != null) {
			workerId = userObject.getUserId();
		}
		
		
		for (Map<String, Object> rasData : rasDocListToParse) {
			boolean isError = false;
			String transactionMessage = null;
			Integer rasDocId = null;
			String sequence = null;
			
			callingSource = "AUTO_PROCESS";
			
			SIERRA_PARSER:{
			try {
			
			rasDocId = (Integer)rasData.get("LAB_DOC_ID");
			
			if(rasDocId > maxDocumentIdParsed) {
				maxDocumentIdParsed = rasDocId;
			}
			if(minDocumentIdParsed == null || rasDocId < minDocumentIdParsed) {
				minDocumentIdParsed = rasDocId;
			}
			
			sequence = (String)rasData.get("GEN_SEQ");
			
			int resultCount = sierraWebService.isLabDocIdExist(rasDocId);
			
			if(resultCount > 0) {
				throw new DataRetrievalFailureException("DocumentId already parsed and stored in Sierra tables");
			}
			
			resultCount = sierraWebService.isLabDocIdExistInErrorTable(rasDocId);
			
			if(resultCount > 0) {
				
				sierraWebService.processRepriseDocuemnt(rasDocId);
				
			}
			
			
			if(CommonUtil.isNotNullAndEmpty(sequence)) {
				
				JsonObject sierraRasResponseObject = sierraWebService.getSierraRasResponse(rasDocId, sequence);
				
				if(sierraRasResponseObject.isJsonNull()) {
					throw new DataRetrievalFailureException("No Data element in the Sierra response");
				}
				
				//JsonObject viewerResponse = sierraRasResponseObject.getAsJsonObject("viewer");
				
				
				JsonObject algorithDetails = sierraRasResponseObject.getAsJsonObject("currentVersion");
				JsonArray allSeqResponse = sierraRasResponseObject.getAsJsonArray("sequenceAnalysis");
				
				String version = algorithDetails.get("text").toString().replaceAll("\"", "");
				String publishDate = algorithDetails.get("publishDate").toString().replaceAll("\"", "");;
				
				if(allSeqResponse.isJsonNull()) {
					throw new DataRetrievalFailureException("No sequenceAnalysis element in the Sierra response");
				}else if(allSeqResponse.size() > 1) {
					throw new DataRetrievalFailureException("Multiple sequenceAnalysis elements in the Sierra response");
				}
				
				
				for (JsonElement _response : allSeqResponse) {
					
					if(_response.isJsonNull()) {
						throw new DataRetrievalFailureException("No sequenceAnalysis element in the Sierra response");
					}
					
					JsonObject rasJsonResponse = _response.getAsJsonObject();
					
					
					sierraWebService.parseSierraData(version, publishDate, rasJsonResponse);
					
				}
				
			}else {
				throw new DataRetrievalFailureException("Sequence doesn't exist");
			}
			
			}catch(Exception e) {
				isError = true;
				transactionMessage = e.getMessage();
				
				if(!"ERROR_BUCKET".equals(callingSource) && "The target server failed to respond".equals(transactionMessage)) {
					callingSource = "ERROR_BUCKET";
					
					break SIERRA_PARSER;
				}
				
				e.printStackTrace();
				failedDocumentIds.put(rasDocId, transactionMessage);
			}
			}
			
			if(isError) {
				
				Map<String, Object> inputParameterMap = new HashMap<String, Object>();
				
				inputParameterMap.put("DOCUMENT_ID", rasDocId);
				inputParameterMap.put("ERROR_DESC", transactionMessage);
				inputParameterMap.put("ACTIVE_FLAG", "Y");
				inputParameterMap.put("CREATE_ID", workerId);
				
				sierraWebService.addErrorDocument(inputParameterMap);
			}
			
			
		}
		
		String mailContent = "";
		
		boolean insertionFailed = false;

		try {
			
			sierraWebService.loadNewSierraDataToFinalTable();
			
		}catch(Exception e){
			insertionFailed = true;
			log.error("Failed while inserting into sierra_resistance_response  table: {}",e.getMessage());
		}finally {
				
			mailContent =  sierraWebService.sendLogInformation(minDocumentIdParsed, maxDocumentIdParsed, failedDocumentIds, rasDocListToParse.size(), workerId, insertionFailed);
			
			 
		}
		
		return mailContent;
		
	}
	
	@Transactional
	public void parseSierraData(String version, String publishDate, JsonObject rasJsonResponse) {
		
		Integer SEQ_RESULT_ID = sierraWebService.parseAndStoreHeaderElements(version, publishDate, rasJsonResponse);

		
		
		sierraWebService.parseAndStoreValidationResults(SEQ_RESULT_ID, rasJsonResponse);
		
		sierraWebService.parseAndStoreGeneInformation(SEQ_RESULT_ID, rasJsonResponse);
		
		sierraWebService.parseAndStoreDrugInformation(SEQ_RESULT_ID, rasJsonResponse);
	}

}
