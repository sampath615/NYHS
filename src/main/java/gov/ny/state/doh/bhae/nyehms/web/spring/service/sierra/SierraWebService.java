package gov.ny.state.doh.bhae.nyehms.web.spring.service.sierra;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.LabProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.sierra.SierraResponseDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;




/**
 * class to load sierra response to RAS tables 
 * 
 * @author ska03
 *
 */
@Service
public class SierraWebService {

	@Autowired
	private LabProperties labProperties;

	@Autowired
	private AppProperties appProperties;

	private static Logger log = LoggerFactory.getLogger(SierraWebService.class);
    //private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    @Autowired
    SierraResponseTransactionHelper sierraResponseTransactionHelper;
    
    @Autowired
    CommonExceptionHandler commonExceptionHandler;
    
    @Autowired
    SendEmail sendEmail;
    
    @Autowired
    SierraResponseDAO sierraResponseDAO;
    
    final String PostUrl = "http://sgwout.health.state.ny.us:7071/siennahivdb/graphql";
	
	final String query = "query example($sequences: [UnalignedSequenceInput]!) {  currentVersion { text, publishDate },  sequenceAnalysis(sequences: $sequences) {       inputSequence {      header,      sequence,      MD5,      SHA512    },    validationResults {      level,      message    },    alignedGeneSequences {      gene {        name,        refSequence,        length,        drugClasses {          name,          fullName,          drugs {            name,            displayAbbr,            fullName          }        },        mutationTypes      },      firstAA,      lastAA,      firstNA,      lastNA,      matchPcnt,      prettyPairwise {        positionLine,        refAALine,        alignedNAsLine,        mutationLine      },      mutations {        reference,        position,        AAs,        triplet,        insertedNAs,        isInsertion,        isDeletion,        isIndel,        isAmbiguous,        isApobecMutation,        isApobecDRM,        hasStop,        isUnusual,        types,        primaryType,        comments {          boundMutation { AAs },          type,          text        },        text,        shortText      },      APOBEC: mutations(filterOptions: [APOBEC]) {        position,        AAs,        text      },      APOBEC_DRM: mutations(filterOptions: [APOBEC_DRM]) {        text      },      DRM: mutations(filterOptions: [DRM]) {        text      },      SDRM: mutations(filterOptions: [SDRM]) {        text      },      unusualMutations: mutations(filterOptions: [UNUSUAL]) {        text      },      treatmentSelectedMutations: mutations(        filterOptions: [PI_TSM, NRTI_TSM, NNRTI_TSM, INSTI_TSM]      ) {        text      },      frameShifts {        position,        isInsertion,        isDeletion,        size,        NAs,        text      }    },    firstTenCloseSubtypes: subtypesV2(first: 10) {      displayWithoutDistance,      distancePcnt,      referenceAccession    },    bestMatchingSubtype { display },    mixturePcnt,    mutations {      position,      AAs,      shortText    },    frameShifts {      text    },    drugResistance {      gene {        name,        refSequence,        length,        drugClasses { name },        mutationTypes      },      drugScores {        drugClass {          name,          fullName,          drugs {            name,            displayAbbr,            fullName          }        },        drug { displayAbbr },        SIR,        score,        level,        text,        partialScores {          mutations {            text          },          score        }      },      mutationsByTypes {        mutationType,        mutations {          reference,          position,          AAs,          triplet,          insertedNAs,          isInsertion,          isDeletion,          isIndel,          isAmbiguous,          isApobecMutation,          isApobecDRM,          hasStop,          isUnusual,          text,          shortText        }      }      commentsByTypes {        commentType,        comments {          type,          text,          highlightText        }      }    }      }}";
	
	HttpTransport HTTP_TRANSPORT = new ApacheHttpTransport();
	
	HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
	
	GenericUrl url = new GenericUrl(PostUrl.replaceAll(" ", "%20"));
	
	Map<String, Object> resistanceObj = new HashMap<>();
	
	HttpRequest request = null;
	
	HttpContent content = null;
	
	HttpResponse response = null;
	
	private Map<String, Integer> geneMap = new HashMap<>();
	
	private Map<String, Integer> drugCodes = new HashMap<>();
	
	SierraWebService(){		
		geneMap.put("PR", 1);
		geneMap.put("RT", 2);
		geneMap.put("IN", 3);
	}
	
	public void loadSierraDrugCodeMap() {
		List<Map<String, Object>> drugCodedList = sierraResponseDAO.getDrugCodedList();
		
		for (Map<String, Object> map : drugCodedList) {
			
			drugCodes.put((String)map.get("ABBREV"), (Integer)map.get("ID"));
			
		}
	}
	
	
   public void loadRASDocumentsSierraResponse() {
	   
	   
	   try{
		   int maxDocumentId =  sierraResponseDAO.getLastParsedRasDocId();
		   
		   sierraResponseTransactionHelper.loadSierraResponseData(maxDocumentId, null);
		}catch(DataAccessException dataAccessException){
			
			log.error("Exception occured while running the RAS Job ",dataAccessException.getMessage());
	
		}catch(Exception exception){
			log.error("Exception occured while running the RAS Job",exception.getMessage());
			
		}
	   
	   
	   
   }
   
   public String runRasJob(Integer dOCUMENT_ID, BindingResult bindingResult, UserObject userObject){
	   

	   try{
		   return sierraResponseTransactionHelper.loadSierraResponseData(dOCUMENT_ID, userObject);
		}catch(DataAccessException dataAccessException){
			
			log.error("Exception occured while running the RAS Job",dataAccessException.getMessage());
			return "<h2 class='errorTransactionMessage' style='font-weight:bold'>Job Failed, please contact helpline</h2>";
		}catch(Exception exception) {
			log.error("Exception occured while running the RAS Job {}",exception.getMessage());
			return "<h2 class='errorTransactionMessage' style='font-weight:bold'>Job Failed, please contact helpline</h2>";
		}
	   
	   
	   
   }
   
   public List<Map<String, Object>> getRasDocIdsToParse(int maxDocumentId){
	   
	   Map<String, Integer> inputParameterMap = new HashMap<>();
	   
	   inputParameterMap.put("DOC_ID", maxDocumentId);
	   inputParameterMap.put("ROW_LIMIT", appProperties.rasFetchRowLimit());
	   
	   return sierraResponseDAO.getRasDocIdsToParse(inputParameterMap);
	   
   }
   
   public JsonObject getSierraRasResponse(Integer rasDocId, String sequence) throws IOException{
	   	
	
	
	Map<String, List<Map<String, Object>>> resistanceMap = new HashMap<>();
	
	Map<String, Object> resistance1 = new HashMap<>();
	
	List<Map<String, Object>> resistanceList = new ArrayList<>();

	resistance1.put("header", rasDocId);
	resistance1.put("sequence", sequence);

	resistanceList.add(resistance1);
	
	resistanceMap.put("sequences", resistanceList);
	

	resistanceObj.put("query", labProperties.sierraQuery());
	
	resistanceObj.put("operationName", "example");
	
	
	resistanceObj.put("variables", resistanceMap);
	
	content = new JsonHttpContent(new JacksonFactory(), resistanceObj);
	
	request = requestFactory.buildPostRequest(url, content);
	
	//System.out.println("HttpRequest request" + request);
	
	response = request.execute();
	
	//System.out.println(response.getStatusCode());
	
	if(response.getStatusCode() == 200) {
		
		JsonElement jsonElement = new JsonParser().parse(new InputStreamReader(response.getContent(), "utf-8"));
		JsonObject jsonObj = jsonElement.getAsJsonObject();
		if(!jsonObj.isJsonNull()) {
			return jsonObj.getAsJsonObject("data");
		}else {
			throw new DataRetrievalFailureException("Invalid Sierra response format");
		}
		
	}else {
		log.error("Unable to get the sierra response {}",response.getStatusCode());
		throw new DataRetrievalFailureException("No Sierra response");
	}
	

	   
   }
   
   public Integer parseAndStoreHeaderElements(String version, String publishDate, JsonObject rasJsonResponse) {
	   
	   JsonObject bestMatchSubTypeObj = null;
		
		if(!rasJsonResponse.get("bestMatchingSubtype").isJsonNull()) {
			bestMatchSubTypeObj = rasJsonResponse.getAsJsonObject("bestMatchingSubtype");
		}
		
		
		String subType = null;
		
		if(bestMatchSubTypeObj != null && bestMatchSubTypeObj.get("display") != null) {
			subType = bestMatchSubTypeObj.get("display").toString().replaceAll("\"", "").split(" ")[0];
		}
		
		String firstNA = null;//rasJsonResponse.get("absoluteFirstNA").toString().replaceAll("\"", "");
		
		JsonObject inputSequence = rasJsonResponse.getAsJsonObject("inputSequence");
		
		if(inputSequence.isJsonNull()) {
			throw new DataRetrievalFailureException("No inputSequence element in Sierra response");
		}
		
		
		String header = inputSequence.get("header").toString().replaceAll("\"", "");
		//String sequence = inputSequence.get("sequence").toString().replaceAll("\"", "");
		String MD5 = inputSequence.get("MD5").toString().replaceAll("\"", "");
		String SHA512 = inputSequence.get("SHA512").toString().replaceAll("\"", "");
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("version", version);
		inputParameterMap.put("publishDate", publishDate);
		inputParameterMap.put("firstNA", firstNA);
		inputParameterMap.put("header", header);
		//inputParameterMap.put("sequence", sequence);
		inputParameterMap.put("MD5", MD5);
		inputParameterMap.put("SHA512", SHA512);
		inputParameterMap.put("subType", subType);
		
		
	
		int resultCount = sierraResponseDAO.storeSequenceHeaderInformation(inputParameterMap);
		
		if(resultCount == 0) {
			throw new DataRetrievalFailureException("Failed while inserting the Header information");
		}
		
		return ((BigDecimal)inputParameterMap.get("SEQ_RESULT_ID")).intValue();
				
		
		
   }
   
   public void parseAndStoreValidationResults(Integer SEQ_RESULT_ID, JsonObject rasJsonResponse) {
	   
	   	JsonArray validationResults = rasJsonResponse.getAsJsonArray("validationResults");
		
		List<Map<String, String>> valiationResults = new ArrayList<>();
		
		if(validationResults != null && validationResults.size() > 0) {
			
			for (JsonElement jsonElement : validationResults) {
				
				Map<String, String> _validationResult = new HashMap<>(); 
				
				_validationResult.put("level", jsonElement.getAsJsonObject().get("level").toString().replaceAll("\"", ""));
				_validationResult.put("message", jsonElement.getAsJsonObject().get("message").toString().replaceAll("\"", ""));
				
				valiationResults.add(_validationResult);
				
			}
			
			Map<String, Object> inputParameterMap = new HashMap<>();
			
			
			inputParameterMap.put("VALIDATION_RESULTS", valiationResults);
			inputParameterMap.put("SEQ_RESULT_ID", SEQ_RESULT_ID);
			
			
			int resultCount = sierraResponseDAO.storeValidationResults(inputParameterMap);
			

			if(resultCount == 0) {
				throw new DataRetrievalFailureException("Failed while inserting the Validation results");
			}
			
		}
   }
   
   public void parseAndStoreGeneInformation(Integer sEQ_RESULT_ID, JsonObject rasJsonResponse) {
	   
	   JsonArray alignedGeneSequences = rasJsonResponse.getAsJsonArray("alignedGeneSequences");
	
	   if(alignedGeneSequences.isJsonNull() || alignedGeneSequences.size() == 0) {
			throw new DataRetrievalFailureException("No alignedGeneSequences element in Sierra response");
		}
	   
		
		for (JsonElement jsonElement : alignedGeneSequences) {
			
			JsonObject genSequence =  jsonElement.getAsJsonObject();
			
			JsonObject geneResponse = genSequence.getAsJsonObject("gene");
			

			if(geneResponse.isJsonNull()) {
				throw new DataRetrievalFailureException("No gene element in alignedGeneSequences");
			}
			
			Map<String, Object> geneSeqInfo = new HashMap<>(); 
			
			String _geneName = geneResponse.get("name").toString().replaceAll("\"", "");
			
			geneSeqInfo.put("name", _geneName);
			geneSeqInfo.put("GENE_SEQ_ID", geneMap.get(_geneName));
			geneSeqInfo.put("consensus", geneResponse.get("refSequence").toString().replaceAll("\"", ""));
			geneSeqInfo.put("length", geneResponse.get("length").toString().replaceAll("\"", ""));
			geneSeqInfo.put("firstAA", genSequence.get("firstAA").toString().replaceAll("\"", ""));
			geneSeqInfo.put("lastAA", genSequence.get("lastAA").toString().replaceAll("\"", "").replaceAll(",", ""));
			geneSeqInfo.put("firstNA", genSequence.get("firstNA").toString().replaceAll("\"", ""));
			geneSeqInfo.put("lastNA", genSequence.get("lastNA").toString().replaceAll("\"", ""));
			geneSeqInfo.put("matchPcnt", genSequence.get("matchPcnt").toString().replaceAll("\"", ""));
			geneSeqInfo.put("SEQ_RESULT_ID", sEQ_RESULT_ID);

			
			//Reading ALIGNED_NA_SEQ, ALIGNED_AA_SEQ storing it to a different table
			
			JsonObject prettyPairwise = genSequence.getAsJsonObject("prettyPairwise");
			
			if(!prettyPairwise.isJsonNull()) {
			
				String refAALine = prettyPairwise.get("refAALine").toString().replaceAll("[\", \\[\\]]", "");
				String alignedNAsLine = prettyPairwise.get("alignedNAsLine").toString().replaceAll("[\", \\[\\]]", "");
				
				geneSeqInfo.put("refAALine", refAALine);
				geneSeqInfo.put("alignedNAsLine", alignedNAsLine);
			}
			
			//store these information
			
			int resultCount = sierraResponseDAO.storeGenSeqInfromation(geneSeqInfo);
			
			if(resultCount == 0) {
				throw new DataRetrievalFailureException("Failed while storing Gene "+_geneName+" information");
			}
			
			
			
			
			
			JsonArray mutationsList = genSequence.getAsJsonArray("mutations");
			
			if(mutationsList.isJsonNull() || mutationsList.size() == 0) {
					continue;
			}
			
			for (JsonElement mutation : mutationsList) {
				
				JsonObject mutationObj = mutation.getAsJsonObject();
				
				Map<String, Object> inputMutation = new HashMap<>();
				inputMutation.put("GENE_NAME", _geneName);
				inputMutation.put("GENE_SEQ_ID", geneMap.get(_geneName));
				inputMutation.put("WILD_TYPE", mutationObj.get("reference").toString().replaceAll("\"", ""));
				inputMutation.put("POSITION", mutationObj.get("position").toString().replaceAll("\"", ""));
				inputMutation.put("TRANSLATEDAA", mutationObj.get("AAs").toString().replaceAll("\"", ""));
				inputMutation.put("NUCLE_ACID", mutationObj.get("triplet").toString().replaceAll("\"", ""));
				inputMutation.put("INSERTEDNAS", mutationObj.get("insertedNAs").toString().replaceAll("\"", ""));
				inputMutation.put("ISINSERTION", mutationObj.get("isInsertion").toString().replaceAll("\"", ""));
				inputMutation.put("ISDELETION", mutationObj.get("isDeletion").toString().replaceAll("\"", ""));
				inputMutation.put("ISINDEL", mutationObj.get("isIndel").toString().replaceAll("\"", ""));
				inputMutation.put("ISAMBIGUOUS", mutationObj.get("isAmbiguous").toString().replaceAll("\"", ""));
				inputMutation.put("ISAPOBECMUTATION", mutationObj.get("isApobecMutation").toString().replaceAll("\"", ""));
				inputMutation.put("ISAPOBECDRM", mutationObj.get("isApobecDRM").toString().replaceAll("\"", ""));
				inputMutation.put("HASSTOP", mutationObj.get("hasStop").toString().replaceAll("\"", ""));
				inputMutation.put("ISUNUSUAL", mutationObj.get("isUnusual").toString().replaceAll("\"", ""));
				inputMutation.put("MUTATION_TYPE", mutationObj.get("primaryType").toString().replaceAll("\"", ""));
				inputMutation.put("NAME", mutationObj.get("text").toString().replaceAll("\"", ""));
				inputMutation.put("SEQ_RESULT_ID", sEQ_RESULT_ID);
				
				//int mutationResultCount = storeMutationAndComments(inputMutation, mutationCommentsList);
				int mutationResultCount = sierraResponseDAO.storeMutation(inputMutation);
				
				if(mutationResultCount == 0) {
					throw new DataRetrievalFailureException("Failed while storing Mutations for Gene "+_geneName+" element");
					
				}
			}
		}
   }
   
   public void parseAndStoreDrugInformation(Integer sEQ_RESULT_ID, JsonObject rasJsonResponse) {
	   JsonArray drugResistanceList = rasJsonResponse.getAsJsonArray("drugResistance");
	   
	   if(drugResistanceList.isJsonNull() || drugResistanceList.size() == 0) {
		   throw new DataRetrievalFailureException("No drugResistance element in Sierra response");
	   }
		
		for (JsonElement drugResistance : drugResistanceList) {
			
			JsonObject drugResistanceObj = drugResistance.getAsJsonObject();
			
			JsonObject geneObject = drugResistanceObj.getAsJsonObject("gene");
			
			String geneName = geneObject.get("name").toString().replaceAll("\"", "");
			
			
			JsonArray drugScoresList = drugResistanceObj.getAsJsonArray("drugScores");
			
			for (JsonElement drugScore : drugScoresList) {
				
				JsonObject drugScoreObj = drugScore.getAsJsonObject();
				
				JsonObject drugClassObj = drugScoreObj.getAsJsonObject("drugClass");
				
				Map<String, Object> inputDrug = new HashMap<>();
				
				String DRUG_CODE = drugScoreObj.getAsJsonObject("drug").get("displayAbbr").toString().replaceAll("\"", "");
				inputDrug.put("SEQ_RESULT_ID", sEQ_RESULT_ID);
				inputDrug.put("DRUG_CODE", DRUG_CODE);
				inputDrug.put("NAME", drugClassObj.get("fullName").toString().replaceAll("\"", ""));
				inputDrug.put("TYPE", drugClassObj.get("name").toString().replaceAll("\"", ""));
				inputDrug.put("SIR", drugScoreObj.get("SIR").toString().replaceAll("\"", ""));
				inputDrug.put("RESISTANCE_LEVEL", drugScoreObj.get("level").toString().replaceAll("\"", ""));
				inputDrug.put("LEVEL_TEXT", drugScoreObj.get("text").toString().replaceAll("\"", ""));
				inputDrug.put("SCORE", drugScoreObj.get("score").toString().replaceAll("\"", ""));
				inputDrug.put("GENE_NAME", geneName);
				inputDrug.put("GENE_SEQ_ID", geneMap.get(geneName));
				inputDrug.put("DRUG_ID", drugCodes.get(DRUG_CODE));
				
				
				
				List<Map<String, String>> paritalScoreMutationList = new ArrayList<>();
				
				JsonArray partialScoresJsonArray = drugScoreObj.getAsJsonArray("partialScores");
				
				
				
				if(partialScoresJsonArray != null && partialScoresJsonArray.size() > 0) {
					
					for (JsonElement _partialScore : partialScoresJsonArray) {
						
						Map<String, String> partialScoreMap = new HashMap<>();
						
						JsonObject partialScoreObject =  _partialScore.getAsJsonObject();
						
						String score = partialScoreObject.get("score").toString().replaceAll("\"", "");
						
						JsonArray partialMutationsList = partialScoreObject.getAsJsonArray("mutations");
						
						StringBuilder existingpartialMutationText = new StringBuilder("");
						
						//Map<String, String> partialMutationMap = new HashMap<String, String>();
						
						int _index = 0;
						
						for (JsonElement partialMutationElement : partialMutationsList) {
							
							
						
							String partialMutationText = partialMutationElement.getAsJsonObject().get("text").toString().replaceAll("\"", "");
							
							if(_index >0 ) {
								existingpartialMutationText.append(", ");
							}
							
							existingpartialMutationText.append(partialMutationText);
							
							_index++;
						}
						partialScoreMap.put(score, existingpartialMutationText.toString());
						
						paritalScoreMutationList.add(partialScoreMap);
						
					}
					
				}
				
				int drugResultCount = sierraResponseDAO.storeDrugDetails(inputDrug);
				
				if(drugResultCount == 0) {
					throw new DataRetrievalFailureException("Failed while storing Drug details for Gene "+geneName+" element");
					
				}
				
				
				
				if(paritalScoreMutationList != null && paritalScoreMutationList.size() > 0) {
					
					for (Map<String, String> partialScoreMap : paritalScoreMutationList) {
						
						
						Map<String, Object> drupgPartialListMap = new HashMap<>();
						
						drupgPartialListMap.put("SEQ_RESULT_ID", sEQ_RESULT_ID);
						
						for(String score : partialScoreMap.keySet()) {
							
							drupgPartialListMap.put("SCORE", score);
							drupgPartialListMap.put("MUTATION_TEXT", partialScoreMap.get(score));
						}
						
						
						drupgPartialListMap.put("DRUG_ID", drugCodes.get(DRUG_CODE));
						
						int drugPartialCount = sierraResponseDAO.storeDrugPartialData(drupgPartialListMap);
						
						if(drugPartialCount == 0) {
							throw new DataRetrievalFailureException("Failed while storing Drug Partial score data for Gene "+geneName+" element");
						}
												
					}
					
					
					
				}
				
				
				
			}
			
			
			
			
			
		}
   }
   
	public void addErrorDocument(Map<String, Object> inputParameterMap) {
	   sierraResponseDAO.addErrorDocument(inputParameterMap);
   }
   
   public String sendLogInformation(Integer minDocumentIdParsed, Integer maxDocumentIdParsed, Map<Integer, String> failedDocumentIds, int totalDocCount, String workerId, boolean insertionFailed) {
	   Map<String, Object> logParameter = new HashMap<>();
       
       logParameter.put("DOCUMENT_ID_MIN", minDocumentIdParsed);
       logParameter.put("DOCUMENT_ID_MAX", maxDocumentIdParsed);
       logParameter.put("NO_OF_ERRORS", failedDocumentIds.size());
       logParameter.put("TOTAL_NO_RECORDS", totalDocCount);
       
       sierraResponseDAO.addSierraLoadLog(logParameter);
       
       
       StringBuilder mailContent = new StringBuilder("");

       mailContent.append("<div class='pB20'>");
       if(totalDocCount == failedDocumentIds.size()) {
    	   mailContent.append("<h2 class='errorTransactionMessage' style='font-weight:bold'>All the documents failed</h2>");
       }else if(failedDocumentIds.size() > 0) {
    	   mailContent.append("<h2 class='errorTransactionMessage' style='font-weight:bold'>Few documents failed</h2>");
       }else if(failedDocumentIds.size() == 0) {
    	   mailContent.append("<h2 class='successTransactionMessage' style='font-weight:bold'>Successfully parsed all the documents</h2>");
       }
       
       if(insertionFailed) {
    	   mailContent.append("<h2 class='errorTransactionMessage' style='font-weight:bold'>Job failed while inserting into sierra_resistance_response table</h2>");
       }
       
       mailContent.append("</div>");
       
		
		mailContent.append("<table class=\" bordered\" cellspacing=\"0\" border=\"1\" >");
		mailContent.append("<tr><th class=\"width25Perc\">MIN DOCID</th><th class=\"width25Perc\">MAX DOCID</th><th class=\"width25Perc\">Total Documents</th><th class=\"width25Perc\">Failed Doc Count</th></tr>");
			mailContent.append("<tr><td class=\"width25Perc\">");
			mailContent.append(minDocumentIdParsed);
			mailContent.append("</td><td class=\"width25Perc\">");
			mailContent.append(maxDocumentIdParsed);
			mailContent.append("</td><td class=\"width25Perc\">");
			mailContent.append(totalDocCount);
			mailContent.append("</td><td class=\"width25Perc\">");
			mailContent.append(failedDocumentIds.size());
			mailContent.append("</td></tr>");
			
		
		mailContent.append("</table><br/><br/><br/>");
		
		if(failedDocumentIds.size() > 0) {
		
			mailContent.append("<table class=\" bordered\" cellspacing=\"0\" border=\"1\">");
			mailContent.append("<tr><th class=\"width25Perc\">DOCID</th><th class=\"width25Perc\">Failed Reason</th></tr>");
				for (Integer rasDocId : failedDocumentIds.keySet()) {
					mailContent.append("<tr><td class=\"width25Perc\">");
					mailContent.append(rasDocId);
					mailContent.append("</td><td class=\"width25Perc\">");
					mailContent.append(failedDocumentIds.get(rasDocId));
					mailContent.append("</td></tr>");
					
				}
			mailContent.append("</table><br/><br/><br/>");
		}
		
		try {
			
			sendEmail.processEmail(null, "Sierra Job Summary", mailContent.toString(), "RAS_NOTIFICATION", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			
		} catch (MessagingException e) {
			log.error(mailContent.toString());

			log.error("failed to send the Sierra job summary to Users ", e);

		}
		
		return mailContent.toString();
       
   }
   
   public List<JSONObject> loadFailedRasDocuments(BindingResult bindingResult, UserObject userObject){
	   return sierraResponseDAO.loadActiveFailedRasDocuments();
   }
   
   public int isLabDocIdExist(Integer rasDocId) {
	   return sierraResponseDAO.isLabDocIdExist(rasDocId);
   }
   
   public int isLabDocIdExistInErrorTable(Integer rasDocId) {
	   return sierraResponseDAO.isLabDocIdExistInErrorTable(rasDocId);
   }
   
   public void updateRASSelectedDocumentStatus(long[] documentIds, String comments, String actionName,
			BindingResult bindingResult, UserObject userObject) {
	   
		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_document_list", documentIds);
		if("reprise".equals(actionName)) {
			inOutParameterMap.put("ACTIVE_FLAG_STATUS", "REPRISE");
		}else {
			inOutParameterMap.put("ACTIVE_FLAG_STATUS", "D");
		}
		inOutParameterMap.put("p_user_id", userObject.getWorkerId());
		inOutParameterMap.put("p_comments", comments);
		
		int resultCount = sierraResponseDAO.updateRASSelectedDocumentStatus(inOutParameterMap);
		
		if(resultCount > 0) {
			commonExceptionHandler.inspectDBTransacionResult(0,
					   "Successfully updated document status", bindingResult);
		}else {
			commonExceptionHandler.inspectDBTransacionResult(-1,
					   "Failed to update the document status", bindingResult);
		}

	   
	   
   }
   
   public void processRepriseDocuemnt(Integer rasDocId) {
	   sierraResponseDAO.processRepriseDocuemnt(rasDocId);
   }
   
   public void parseSierraData(String version, String publishDate, JsonObject rasJsonResponse) {
	   sierraResponseTransactionHelper.parseSierraData(version, publishDate, rasJsonResponse);
   }
   
   public void loadMissingRasDocuments() {
	   sierraResponseDAO.loadMissingRasDocuments();
   }
   
   public void loadNewSierraDataToFinalTable() {
	   sierraResponseDAO.loadNewSierraDataToFinalTable();
   }
}

