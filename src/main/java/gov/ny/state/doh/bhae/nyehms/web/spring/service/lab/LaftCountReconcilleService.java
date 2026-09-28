package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import au.com.bytecode.opencsv.CSVReader;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftCountReconcileDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LAFT_LAB_RECON_BHAE_COMMENTS;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LAFT_LAB_RECON_SUMM_REC;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

@Service
public class LaftCountReconcilleService {

	@Autowired LaftCountReconcileDAO laftCountReconcileDAO;
	
	@Autowired LabReportsTransactionHelper labReportsTransactionHelper;
	
	@Autowired private CommonExceptionHandler commonExceptionHandler;	
	
	@Autowired SendEmail sendEmail;
	
	private static Logger log = LoggerFactory.getLogger(LaftCountReconcilleService.class);
	
	String surveyStDay;
	String surveyStMonth;
	String surveyStYear;
	String surveyEndDay;
	String surveyEndMonth;		
	String surveyEndYear;
	String surveyCompDay;
	String surveyCompMonth;		
	String surveyCompYear;
	String surveyCompDayText;


	@SuppressWarnings("unchecked")
	public Map<String, String> getPFICollectionPeriods(String userEnteredPfi) {

		Map<String, Object> inputParameterMap = new HashMap<>();
		inputParameterMap.put("userEnteredPfi", userEnteredPfi);

		List<JSONObject> list = laftCountReconcileDAO.getPFICountCollectionPeriod(inputParameterMap);
		
		Map<String, String> collPeriodList = new LinkedHashMap<>();
		
    	for (Map<String, String> period: list) {
    		collPeriodList.put(period.get("LAFT_RECON_ID"), period.get("COLLECTIONPERIOD"));
    	}
		
		return collPeriodList;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getReconcileDashboardData(UserObject userObject, BindingResult bindingResult,
			String reconcileId) {

		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_laft_recon_id", reconcileId);
		inputData.put("p_pfi", null);

		laftCountReconcileDAO.getReconcileDashboardData(inputData);

		List<JSONObject> reconcileDashboardData = (List<JSONObject>) inputData.get("p_recon_dash");

		return reconcileDashboardData;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getCurOpenSurveysData(UserObject userObject, BindingResult bindingResult,
			String reconcileId, String userId, String labReconciled) {

		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_laft_recon_id", reconcileId);

		if (userId.equals("undefined")) {
			userId = null;
		}
		inputData.put("p_worker_id", userId);

		laftCountReconcileDAO.getCurOpenSurveysData(inputData);

		List<JSONObject> curOpenSurveyData = (List<JSONObject>) inputData.get("p_pfi_cur");
		List<JSONObject> curLabData = new ArrayList<>();
		
		if (labReconciled.equals("O")) {
			 for (JSONObject curObj : curOpenSurveyData) {
				 if (!curObj.get("RECONCILED").equals("Y")) {
					 curLabData.add(curObj);
				 }
			 }
		}
		else if (labReconciled.equals("R")) {
			 for (JSONObject curObj : curOpenSurveyData) {
				 if (curObj.get("RECONCILED").equals("Y")) {
					 curLabData.add(curObj);
				 }
			 }			 
		 }

		return curLabData;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getLabReportedSurveyData(UserObject userObject, BindingResult bindingResult,
			String reconcileId, String pfi) {

		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_laft_recon_id", reconcileId);
		inputData.put("p_pfi", pfi);

		laftCountReconcileDAO.getReconcileDashboardData(inputData);

		List<JSONObject> reconcileSummaryOut = (List<JSONObject>) inputData.get("p_recon_summ_out");
		List<JSONObject> reconcileListPfis = (List<JSONObject>) inputData.get("p_recon_list_pfis");		
		List<JSONObject> surveyResponse = (List<JSONObject>) inputData.get("p_survey_response");
		List<LAFT_LAB_RECON_BHAE_COMMENTS> bhaeCommentsList = (List<LAFT_LAB_RECON_BHAE_COMMENTS>) inputData.get("p_bhae_comments_cur");
		
		Map<String, Object> agGridData = new HashMap<>();
		
		agGridData.put("reconcileSummaryOut", reconcileSummaryOut);
		agGridData.put("reconcileListPfis", reconcileListPfis);
		agGridData.put("surveyResponse", surveyResponse);
		agGridData.put("bhaeCommentsList", bhaeCommentsList);
		
		return agGridData;
	}

	public String updateLabReconcileData(String agGridDataStr, String reconId, String pfi, String labClia, 
			String contactDate, String reconciled, String accessionCompare, String bhaeComments, String workerId, BindingResult bindingResult, UserObject userObject) {
		
			JsonParser parser = new JsonParser();
		    JsonElement jsonElement = parser.parse(agGridDataStr);
		    JsonArray dataAsJsonArr = jsonElement.getAsJsonArray();
	    
		    Gson gson = new Gson(); 
		    Type listType = new TypeToken<List<LAFT_LAB_RECON_SUMM_REC>>() {}.getType();
			List<LAFT_LAB_RECON_SUMM_REC> surveyList = gson.fromJson(dataAsJsonArr, listType);
			
			 List<LAFT_LAB_RECON_SUMM_REC> editSurveyDataList = new ArrayList<>();
			 
			 for (LAFT_LAB_RECON_SUMM_REC curRec : surveyList) {
				 if (curRec.getINITIAL_LAB_COUNT() != 0 || curRec.getCURRENT_LAB_COUNT() != 0 
						|| curRec.getSURVEY_LAB_COUNT() != 0 || curRec.getADJUSTED_SURVEY_LAB_COUNT() != 0 || curRec.getCOUNT_DIFFERENCE() != 0) {
					 curRec.setLAFT_RECON_ID(reconId);
					 curRec.setPFI(pfi);
					 curRec.setCLIA(labClia);
					 curRec.setUPDATE_WORKER_ID(workerId);
					 if (curRec.getLAFT_RECON_SUMM_PK() == 0) {
						 curRec.setINITIAL_LAB_COUNT(0);
						 curRec.setCURRENT_LAB_COUNT(0);
					 }
					 editSurveyDataList.add(curRec);
				 }
			 }
			 
			  Map<Object, Object> inputData = new HashMap<>();
			  inputData.put("p_laft_recon_id", reconId);
			  inputData.put("p_pfi", pfi); 
			  inputData.put("p_bhae_comments", bhaeComments); 
			  inputData.put("p_update_worker_id", workerId);
			  inputData.put("p_initial_contact_date",contactDate);
			  inputData.put("p_reconciled", reconciled);
			  inputData.put("p_accession_compare", accessionCompare); 
			  labReportsTransactionHelper.saveLaftReconcileSurveyData(editSurveyDataList, inputData, bindingResult, userObject);
			  
		return "Success";
	}

	public void insertEditedSurveyData(List<LAFT_LAB_RECON_SUMM_REC> surveyDataList, Map<Object, Object> inputData, BindingResult bindingResult, UserObject userObject) {
		
		Map<String, Object> inputParameterMap  = new HashMap<>();
		inputParameterMap.put("surveyEditDataList", surveyDataList);
		
		if (surveyDataList.size() !=0) {
			int resCt = laftCountReconcileDAO.insertEditSurveyData(inputParameterMap);
			
			if(resCt == 0){
				log.error("Error while saving edited survey data, please contact the Helpline.");
				throw new DataRetrievalFailureException("Error while saving edited survey data, please contact the Helpline.");
			}
		}
		  laftCountReconcileDAO.updateLabReconcileData(inputData);
		  
	}

	public List<JSONObject> getSendSurveyEmailData(BindingResult bindingResult, UserObject userObject) {
		Map<String, Object> emailMap = new HashMap<>();
		
		laftCountReconcileDAO.getSurveyEmailRecipients(emailMap);
		
		@SuppressWarnings("unchecked")
		List<JSONObject> surEmailRecList = (List<JSONObject>) emailMap.get("p_survey_email");
		String mess = (String) emailMap.get("p_message");

		return surEmailRecList;
	}

	public String sendSurveyEmailToLab(String surveyEmailData, String surveyLink, String surveyCompleteDt, String hcsUserId) throws MessagingException, ParseException {
		
		JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(surveyEmailData);
	    JsonArray dataAsJsonArr = jsonElement.getAsJsonArray();
	    
	    Gson gson = new Gson(); 
	    Type listType = new TypeToken<List<JSONObject>>() {}.getType();
		List<JSONObject> surveyEmailDataList = gson.fromJson(dataAsJsonArr, listType);
		
		String[] surveyDuration = surveyEmailDataList.get(0).get("LAFT_RECON_ID").toString().substring(13).split("_");
		String surveyStartStr = surveyDuration[0].substring(0,2) + "/" + surveyDuration[0].substring(2,4) + "/" + surveyDuration[0].substring(4);
		String surveyEndStr = surveyDuration[1].substring(0,2) + "/" + surveyDuration[1].substring(2,4) + "/" + surveyDuration[1].substring(4);
		
		surveyStDay = surveyDuration[0].substring(2,4);
		surveyEndDay = surveyDuration[1].substring(2,4);
		surveyCompDay = surveyCompleteDt.substring(3, 5);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date surveyStDt = sdf.parse(surveyStartStr);
		Date surveyEndDt = sdf.parse(surveyEndStr);
		Date surveyCompDt = sdf.parse(surveyCompleteDt);
		
		SimpleDateFormat sdFormat = new SimpleDateFormat("MMMM");
		surveyStMonth = sdFormat.format(surveyStDt);
		surveyEndMonth = sdFormat.format(surveyEndDt);
		surveyCompMonth = sdFormat.format(surveyCompDt);
		sdFormat = new SimpleDateFormat("YYYY");
		surveyStYear = sdFormat.format(surveyStDt);
		surveyEndYear = sdFormat.format(surveyStDt);
		surveyCompYear = sdFormat.format(surveyCompDt);

		surveyCompDayText = new SimpleDateFormat("EEEE").format(surveyCompDt);
		
		List<String> surveyEmailList = new ArrayList<>();
		List<Integer > surveyTrackPkList = new ArrayList<>();
		
		for (JSONObject surveyEmailDataObj : surveyEmailDataList) {
			if (surveyEmailDataObj.get("EMAIL") != null && CommonUtil.isNotNullAndEmpty(surveyEmailDataObj.get("EMAIL").toString())) {
				surveyEmailList.add(surveyEmailDataObj.get("EMAIL").toString());
				int surveyTrackPk = (int) Math.round((Double)surveyEmailDataObj.get("SURVEY_TRACK_PK"));
				surveyTrackPkList.add(surveyTrackPk);
			}
		}
		
		String userStage = System.getProperty("stage");
		String[] recipientEmailAddressesTo = new String[]{"bhaelab@health.ny.gov"}; // "bhaelab@health.ny.gov";
		String mailSubject = "Time Sensitive: Reconciliation of Counts " 
				+ surveyStMonth + " " + surveyStYear + "-" + surveyEndMonth + " " + surveyEndYear;
		
		if (userStage.equalsIgnoreCase("Local") || userStage.equalsIgnoreCase("DEV") || userStage.equalsIgnoreCase("EVAL") ) {
			
			recipientEmailAddressesTo = new String[]{"sanjay.garg@health.ny.gov"};
			mailSubject = mailSubject + " - System :" + userStage;
			surveyEmailList.clear();
			surveyEmailList.add("sanjay.garg@health.ny.gov");
			surveyEmailList.add("jyothsna.katari@health.ny.gov");
			surveyEmailList.add("varun.gandhi@health.ny.gov");
		}
		String[] recipientEmailAddressesBcc = surveyEmailList.toArray(new String[0]);
		
		sendEmail.sendSSLMessage(recipientEmailAddressesTo, mailSubject, surveyEmailBody(surveyLink), "bhaelab@health.ny.gov", null, recipientEmailAddressesBcc, CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
		
		String trackPkList = surveyTrackPkList.stream()
									        .map(i -> i.toString())
									        .collect(Collectors.joining(", "));
        
		Map<String, Object> surveyEmailSent = new HashMap<>();
		surveyEmailSent.put("hcsUserId", hcsUserId);
		surveyEmailSent.put("trackPkList", trackPkList.split(","));
		
		laftCountReconcileDAO.updateSurveyEmailSendData(surveyEmailSent);
		
		return "Email sent to selected labs successfully.";
	} 
	
	public String surveyEmailBody(String surveyLink) {
		
		String durationStr1 = surveyStMonth + " " + surveyStDay + ", " + surveyStYear + " through " + surveyEndMonth + " " + surveyEndDay + ", "+ surveyEndYear;
		String durationStr2 = surveyStMonth + "-" + surveyEndMonth + " " + surveyEndYear;
		String surveyCompStr = "<b><u>" + surveyCompDayText + "," + surveyCompMonth + " " + surveyCompDay + ", " + surveyCompYear + "</u></b>";
				
		StringBuilder messageBody = new StringBuilder();
		
		String body =   "Dear Lab Liaison,<br><br>";
		
		body += "All HIV-related test results reportable to the NYS Department of Health's Electronic Clinical Laboratory Reporting System (ECLRS) by public health law are assessed for completeness bi-annually by reconciling the number of reportable test results performed on-site with the number of tests received in ECLRS. The survey below is designed to collect the number of reportable HIV-related tests performed on-site with <b>specimen collection dates " + durationStr1 + "</b>. Please complete this 'Reconciliation of Reportable HIV test results " + durationStr2 + " Counts' survey by " + surveyCompStr + " using the following link: <br><br>";
		
		body += "<a>" + surveyLink + "</a> <br><br>";
		
		body += "Ensure the following: <br>";
		body += "<ul>";				
		body += "<li>Count the number of <b>reportable</b> HIV tests by test type for specimen collection dates (NOT observation dates) from " + durationStr1 + ". </li>";
		body += "<li>Provide the number of reportable test results for each test type. </li>";		
		body += "<li>If your laboratory has a low volume and has no reportable HIV-related test results during this time frame, please complete the survey and enter '0' for each test type.</li>";		
		body += "<li>If your reportable HIV-related laboratory test result does not fit in the categories provided, please note the test type and the total number of reports submitted in the comments section.</li>";		
		body += "<li>If you perform HIV tests for donor specimens (e.g. blood donations, organ procurement, etc), please only count the number of test results for specimens with reactive final confirmation test results.</li>";		
		body += "</ul><br>";		
		
		body += "For your reference, below are links to useful guidance documents from NYSDOH.<br><br>";

		body += "<ol>";				
		body += "<li>New York State 2024 Guidelines for the use of HIV Diagnostic Testing Algorithm for Laboratories. 2024 Guidelines for the use of the HIV Diagnostic Testing Algorithm for Laboratories (ny.gov) <a>http://www.health.ny.gov/diseases/aids/providers/testing/docs/guidelines.pdf</a></li>";
		body += "<li>NYSDOH Laboratory Reporting of Communicable Diseases - 2020 <br><a>https://www.wadsworth.org/sites/default/files/WebDoc/CDRG%20NYState%202020_101920%202.pdf</a></li>";
		body += "</ol><br>";			

		body += "Please note that if we do not receive a response to the survey, we will continue to follow-up until we have ensured that there are no gaps in reportable test results as required by public health law. If you are not the correct person to receive this correspondence, please let us know immediately so that we may update our database and direct the survey request to the appropriate laboratory contact.<br><br>";
		
		body += "Please also note that the HIV Laboratory Reporting Compliance Reports are available for your review on ECLRS. This report summarizes HIV laboratory reporting submission timeliness and the quality of reported information on critical data elements. Data quality warnings are indicated when timeliness and quality expectations are not met. Laboratories with data quality warnings will be asked to submit Corrective Action Plans.<br><br>";

		body += "Should you have any questions, please contact Youngeun Nam at <a>bhaelab@health.ny.gov</a> or (518) 474-4284.<br><br>";
		
		body += "Thank you in advance for your time and assistance.<br><br>";
		
		body += "Sincerely,<br><br>";
		
		body += "<b><strong>Youngeun Nam, PhD</b></strong><br>";
		body += "Research Scientist<br>";
		body += "Laboratory Analysis Follow-up Team Lead<br>";
		body += "Pronouns: she/her/hers<br><br>";
		
		body += "<p style='color:blue;'><b>New York State Department of Health</b><p>";
		body += "<b>ESP, Corning Tower</b><br>";
		body += "<b>Albany, NY 12237</b><br>";
		body += "<b>518-474-4284</b><br><br>";
		
		body += "***Please do NOT share patient names or other identifying information via email, fax or voicemail ***<br>";
		
		messageBody.append(body);
		return messageBody.toString();
	}

	public Map<Object, Object> uploadLaftSurveyResponseFile(MultipartFile uploadSurveyResponseFile, String collectionPeriod, BindingResult bindingResult, UserObject userObject) throws IOException {

		boolean isError = false; 
		String transactionMessage = null;
		
		List<Map<String, Object>>  fileDataList = new ArrayList<>();
		Map<Object, Object> resultMap = new HashMap<>();
		String uploadFileName = uploadSurveyResponseFile.getOriginalFilename();
		
		CSVReader csvread = null;
		try{
			
			int uploadFileNameCt = laftCountReconcileDAO.getLaftSurveyResponseFilenameCt(uploadFileName);
			
 			if(!uploadFileName.endsWith(".csv")) {
 				isError = true;
 				transactionMessage = "Only Comma Separated Values file with .csv extension are allowed. Please use the given template.";			
 			} else if(uploadFileName.isEmpty()){
				isError = true;
				transactionMessage = "The file uploaded contains no data.";
 			} else if(uploadFileNameCt > 0){
				isError = true; 
				transactionMessage = "The selected file name " +  uploadFileName + " already exist. Please use unique filename to upload data.";
			}else{			
				
				csvread = new CSVReader(new InputStreamReader(uploadSurveyResponseFile.getInputStream()));
				List<String[]> rowLists = csvread.readAll();
				
				String[] csvRowHeader = rowLists.get(0);
				
	            int csvRowHeaderLen = csvRowHeader.length;
				String[] csvRowHeaderNew = new String[csvRowHeaderLen] ;
				for (int i=0; i<csvRowHeaderLen; i++) {
				//	csvRowHeaderNew[i] = csvRowHeader[i].replaceAll("[^a-zA-Z0-9 _(+)/\\-]", "").trim();
					csvRowHeaderNew[i] = csvRowHeader[i].replaceAll("[^a-zA-Z0-9_(+)/\\-]", "").trim();
				}
				csvRowHeader = csvRowHeaderNew;				
				
				String[] UPLOAD_LAFT_SURVEY_RESPONSE_COLUMN_NAMES = EnvironmentProperties.envProperties.getProperty("UPLOAD_LAFT_SURVEY_RESPONSE_COLUMN_NAMES").split(",");
				int UPLOAD_LAFT_SURVEY_RESPONSE_COLUMN_NAMES_LENGTH = UPLOAD_LAFT_SURVEY_RESPONSE_COLUMN_NAMES.length;
				
				for(int columnIndex = 0; columnIndex < UPLOAD_LAFT_SURVEY_RESPONSE_COLUMN_NAMES_LENGTH; columnIndex++){
					
					String headerColumnName = csvRowHeader[columnIndex];
			//		headerColumnName = headerColumnName.replaceAll("[^a-zA-Z0-9_(/)-]", "");
					headerColumnName = headerColumnName.replaceAll("\n", " ");
					
					String envHeaderColName = UPLOAD_LAFT_SURVEY_RESPONSE_COLUMN_NAMES[columnIndex];
					
					if(!envHeaderColName.equalsIgnoreCase(headerColumnName.trim())){
						isError = true; 
						transactionMessage = "Column headers must match the template.";
						break;
					}
				}
				
				if (!isError) {
					
					for(int i=1; i<rowLists.size(); i++){
						Map<String, Object> fileData = new HashMap<>();
						String[] recordRow = rowLists.get(i);
	
						// Check if row is null, then ignored
						boolean hasRowData = false;
						for(int columnIndex = 0; columnIndex < UPLOAD_LAFT_SURVEY_RESPONSE_COLUMN_NAMES_LENGTH; columnIndex++){
							
							if(!(CommonUtil.isNullOrEmpty(recordRow[columnIndex]))){
								hasRowData = true;
								break;
							}
						}
						
						if (hasRowData) {
							for(int columnIndex=0; columnIndex<UPLOAD_LAFT_SURVEY_RESPONSE_COLUMN_NAMES_LENGTH;columnIndex++){					
									fileData.put(UPLOAD_LAFT_SURVEY_RESPONSE_COLUMN_NAMES[columnIndex], recordRow[columnIndex].trim());
							}
							fileDataList.add(fileData);
						}
					}
				}
			}
			if (fileDataList.size() == 0 && !isError) {
				isError = true;
				transactionMessage = "The selected file contains no LAFT survey response Data. Please use template to create the data file.";
			}
			
			if (!isError) {
				resultMap = validateLaftSurveyResponseData(fileDataList, collectionPeriod);
				
				if (resultMap.size() > 0) {
					isError = true;
					transactionMessage = "Following data errors are found, please fix and upload again.";
				}
			}
			
			if(!isError){
				
				labReportsTransactionHelper.uploadLaftSurveyResponseData(uploadFileName, fileDataList, collectionPeriod, userObject);
				
			}
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = "Error occured, please contact helpline." + dataAccessException.getCause();
			log.error("Failed to read the file, DataAccessException : {}",dataAccessException.getMessage());
			dataAccessException.printStackTrace();
		}catch(Exception exception){
			isError = true;
			transactionMessage = "Failed to read the file, please contact helpline. " + exception.toString() ;
			log.error("Failed to read the file.   {}",exception.getMessage());
			exception.printStackTrace();
		}finally{
			try {
				if (csvread != null) { csvread.close();}
			} catch (Exception e) {
				log.error("EXCEPTION while closing CSV Reader:  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully uploaded the count reconciliation data of " + fileDataList.size() + " records.", bindingResult);
		}
		
		return resultMap;
	}
	
	private Map<Object, Object>  validateLaftSurveyResponseData(List<Map<String, Object>>  fileDataList, String collectionPeriod) {
		
		int counter = 0;
		String PFIString;
		StringBuilder errorMessage = new StringBuilder();
		Map<Object, Object> dataErrorMapList = new HashMap<>();
		
		List<String> pfiList = new ArrayList<>();
		
		for (Map<String, Object> dataRow : fileDataList) {
			if ( dataRow.get("PFI") != null || CommonUtil.isNotNullAndEmpty(dataRow.get("PFI").toString()) ) {
				pfiList.add(dataRow.get("PFI").toString());
			}
		}
		
		Set<String> dupPFI = pfiList.stream().filter(i -> Collections.frequency(pfiList, i) > 1).collect(Collectors.toSet());
		if (dupPFI.size() > 0) {
			errorMessage.append("File contains duplicate PFIs : " + dupPFI);
		} else {
		
			// check for duplicate PFI in already uploaded response for the same collection period
			List<String> uploadedPFIs = laftCountReconcileDAO.getDuplicatePFIFromSurveyResponse(collectionPeriod);
			List<String> allPFIs = Stream.concat(pfiList.stream(),uploadedPFIs.stream()).collect(Collectors.toList());
			Set<String> dupUploadedPFIs = allPFIs.stream().filter(i -> Collections.frequency(allPFIs, i) > 1).collect(Collectors.toSet());
			if (dupUploadedPFIs.size() > 0) {
				errorMessage.append("These PFIs are already uploaded : " + dupUploadedPFIs);
			}
		}
		
		if (CommonUtil.isNotNullAndEmpty(errorMessage.toString())) {
			dataErrorMapList.put("Duplicate PFI", errorMessage);
		} else {
		
		for (Map<String, Object> dataRow : fileDataList) {
			
			errorMessage = new StringBuilder();
			counter++;
			
			if ( dataRow.get("PFI") == null || CommonUtil.isNullOrEmpty(dataRow.get("PFI").toString()) ) {
				PFIString = "Row " + counter;
				errorMessage.append("PFI is a required field and can only be a numeric value.<br/>");
			} else {
				PFIString = dataRow.get("PFI").toString();
			}
			errorMessage = validateVar(dataRow, "CLIA", "STRING", errorMessage);
			errorMessage = validateVar(dataRow, "Western_Blot(Pos/IN/Neg)", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "RNA_NAAT_HIV_1/2_RNA_QL", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "RNA_NAAT/HIV_1_RNA_QL", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "RNA_NAAT/HIV_2_RNA_QL", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "Viral_Load_(copies/ml)", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "Viral_Load_(log_copies)", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "CD4_Percent", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "CD4_Absolute", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "Genotype_Resistance", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "IFA", "INT", errorMessage);		
			errorMessage = validateVar(dataRow, "HIV_1_DNA", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "HIV_2_DNA", "INT", errorMessage);			
			errorMessage = validateVar(dataRow, "Abbott_Determine", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "HIV_1_HIV_2_Immunoassay", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "HIV1_Ag", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "HIV1_Ab", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "HIV2_Ab", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "HIV1+2_Ab", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "Geenius_Assay_Interpretation", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "Geenius_HIV_1", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "Geenius_HIV_2", "INT", errorMessage);
		//	errorMessage = validateVar(dataRow, "Open_Ended_Response", "STRING", errorMessage);
			errorMessage = validateVar(dataRow, "Survey_Completor_Name", "STRING", errorMessage);
			errorMessage = validateVar(dataRow, "Survey_Completor_Email_Id", "STRING", errorMessage);
		//	errorMessage = validateVar(dataRow, "Survey_Completor_Phone", "STRING", errorMessage);
			errorMessage = validateVar(dataRow, "Survey_Completed_Date", "DATE", errorMessage);

			if (CommonUtil.isNotNullAndEmpty(errorMessage.toString())) {
				dataErrorMapList.put(PFIString, errorMessage);
			}
		}
		}
		return dataErrorMapList;
	}	
	
	public StringBuilder validateVar (Map<String, Object> dataRow , String passedVar, String varType, StringBuilder errorMessage) {
		Object currentObj = dataRow.get(passedVar);
		
		if(varType.equals("STRING")) {
			if ( (currentObj == null || CommonUtil.isNullOrEmpty(currentObj.toString()) ) ) {
				errorMessage.append( passedVar + "  is a required field.<br/>");
			}
		} else if(varType.equals("INT")) {
			if ( CommonUtil.isNotNullAndEmpty(currentObj.toString()) && (CommonUtil.isNumeric(currentObj.toString())
					&& Integer.valueOf(currentObj.toString()) < 0)  ) {
				errorMessage.append( passedVar + " is a numeric field  and can only be a value of greater than 0.<br/>");
			}
		} else if(varType.equals("DATE")) {
			if ( currentObj != null && CommonUtil.isNotNullAndEmpty((currentObj.toString()) ) && !DateUtil.isValidDate(currentObj.toString()) ) {
				errorMessage.append( passedVar + " must follow the format MM/DD/YYYY. Should be greater than 01/01/1900 and no future dates.<br/>");
			}
		} 
		
	return errorMessage;
	}

	public void uploadLaftSurveyResponseData(String fileName, List<Map<String, Object>> laftSurveyResponseDataList,	String collectionPeriod, String userId) throws SQLException {
		
		for (Map<String, Object> laftSurveyResponseData : laftSurveyResponseDataList) {
			laftSurveyResponseData.put("collectionPeriod", collectionPeriod);
			laftSurveyResponseData.put("fileName", fileName);
			laftSurveyResponseData.put("userId", userId);
		}
		
		Map<String, Object> inputParameterMap  = new HashMap<>();
		inputParameterMap.put("laftSurveyResponseDataList", laftSurveyResponseDataList);
		int resCt = laftCountReconcileDAO.insertLaftSurveyResponseData(inputParameterMap);
		
		if(resCt == 0){
			log.error("Error while uploading survey response data, please contact the Helpline.");
			throw new DataRetrievalFailureException("Error while uploading survey response data, please contact the Helpline.");
		}
		
		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_laft_recon_id", collectionPeriod);
		inputData.put("p_survey_filename", fileName);
		
		laftCountReconcileDAO.processUploadedSurveyResponse(inputData);
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getAccessionList(String reconId, String pfi) throws MessagingException, ParseException {
		
		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_laft_recon_id", reconId);
		inputData.put("p_pfi", pfi);

		laftCountReconcileDAO.getAccessionList(inputData);

		List<JSONObject> accessionList = (List<JSONObject>) inputData.get("p_accession_list");
		
		return accessionList;
	}

	public String refreshNyehmsLabCount(String reconId, String pfi, String workerId) throws MessagingException, ParseException {
		
		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_recon_id", reconId);
		inputData.put("p_pfi", pfi);
		inputData.put("p_worker_id", workerId);
		
		laftCountReconcileDAO.refreshNyehmsLabCount(inputData);
		
		return "NYEHMS count refreshed successfully.";
	}

	public Map<String, Object> saveAddReconcilePFI(String reconId, String pfi, String laftUserId, String workerId,
			BindingResult bindingResult, UserObject userObject) {
		
		Map<String, Object> inputParameterMap  = new HashMap<>();
		inputParameterMap.put("p_recon_id", reconId);
		inputParameterMap.put("p_pfi", pfi);
		inputParameterMap.put("p_laft_staff", laftUserId);
		inputParameterMap.put("p_worker_id", workerId);
		
		laftCountReconcileDAO.saveAddReconcilePFI(inputParameterMap);
		
		return inputParameterMap;
	}

	public void deleteReconcilePFI(String reconId, String pfi, String workerId, BindingResult bindingResult,
			UserObject userObject) {
		
		Map<String, Object> inputParameterMap  = new HashMap<>();
		inputParameterMap.put("p_recon_id", reconId);
		inputParameterMap.put("p_pfi", pfi);
		inputParameterMap.put("p_worker_id", workerId);
		
		laftCountReconcileDAO.deleteReconcilePFI(inputParameterMap);
		
		commonExceptionHandler.inspectDBTransacionResult(0, "PFI " + pfi + " deleted successfully.", bindingResult);
	}	

}
