package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.UploadProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.review.RecordSearchReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.RecordSearchResultsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;


@Service
public class RecordSearchReviewService {

	@Autowired
	private UploadProperties uploadProperties;
	
	
	private static Logger log = LoggerFactory.getLogger(RecordSearchReviewService.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	@Autowired
	private RecordSearchReviewDAO recordSearchReviewDAO;

	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	
	@Autowired
	private SendEmail sendEmail;

	
	public int changeRecordSearchStatusForMultipleStatenos(
			String[] statenoList, String requestComments,
			BindingResult bindingResult, UserObject userObject){
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_requestComments", requestComments);
		inOutParameterMap.put("p_workerId", userObject.getWorkerId());
		
		
		inOutParameterMap.put("p_stateno_list", statenoList);
		
		int resultCount = recordSearchReviewDAO.changeRecordSearchStatus(inOutParameterMap);
		
		if(resultCount == 0){
					
				String transactionMessage = "This STATENO(S) already Unflagged for Record Search";
			
				log.error(transactionMessage+"   Statenos  "+Arrays.toString(statenoList));
				
				commonExceptionHandler.inspectDBTransacionResult(-1,
						transactionMessage, bindingResult);
		}else{
			
			log.error(" Unflagged the Statenos :  {}",Arrays.toString(statenoList));
			
			commonExceptionHandler.inspectDBTransacionResult(0,
					"Unflagged for a Record Search", bindingResult);
			
		}
		
		return resultCount;
		
	
	}
	
	public void discardCDCFeedbackRecords(String[] cdcRecords,
			BindingResult bindingResult, UserObject userObject){
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_workerId", userObject.getWorkerId());
		
		
		inOutParameterMap.put("p_cdcRecords", cdcRecords);
		
		int resultCount = recordSearchReviewDAO.discardCDCFeedbackRecords(inOutParameterMap);
		
		if(resultCount == 0){
					
				String transactionMessage = "Unable to Discard the record(s), please contact helpline." ;
			
				log.error(transactionMessage+"   Discarded Records  "+Arrays.toString(cdcRecords));
				
				commonExceptionHandler.inspectDBTransacionResult(-1,
						transactionMessage, bindingResult);
		}else{
			
			commonExceptionHandler.inspectDBTransacionResult(0,
					"Successfully discarded the record(s)", bindingResult);
			
		}
	
	}
	
	
	
	
	
	public boolean changeRecordSearchStatus(String stateno,
			String requestComments, String origin, String flagStatus,
			BindingResult bindingResult, UserObject userObject){
		
		
		boolean isError = false;
		
		Integer statusCode = -1;
		
		String transactionMessage = null;
		
		
		try {
			
			Map<Object, Object> inOutParameterMap = new HashMap<>();

			
			inOutParameterMap.put("p_flagStatus", flagStatus);
			inOutParameterMap.put("p_requestComments", requestComments);
			inOutParameterMap.put("p_workerId", userObject.getWorkerId());
			
			
			
			
			if("Y".equals(flagStatus)){
				
				inOutParameterMap.put("p_stateno", stateno);

				int resultCount = recordSearchReviewDAO.checkForExistingRecordSearchStateno(inOutParameterMap);
				
				if(resultCount > 0){
					
					Map<String, String> inOutParameters = new HashMap<>();
					
					inOutParameters.put("p_requestComments", requestComments);
					
					inOutParameters.put("STATENO", stateno);
					

					recordSearchReviewDAO.updateAutoRecordSearchEntry(inOutParameters);
					
				}else{
					
					
					inOutParameterMap.put("FLAG_ORIGIN", origin);
					
					
					if("Manual".equals(origin)){
						
						
						resultCount = recordSearchReviewDAO.checkNewRecordsForTheSTATENO(inOutParameterMap);
						
						if(resultCount == 0){
							
							isError = true;
							
							statusCode = -100;
							
							transactionMessage = "This STATENO has been flagged and sent to CDC, theres no new information to send.  <br/><br/>Do you still want to flag it for Record Search?";
							
						}	
						
					}
					
					
					
					if(!isError){
						resultCount = recordSearchReviewDAO.addRecordSearchFlagStatusEntry(inOutParameterMap);
						
						if(resultCount == 0){
							
							isError = true;
							
							transactionMessage = "Unsuccessfully flagged for Record Search.  Please contact the helpline.";
							
						}
					}
					
				}
				
				
			}else{
				
				
				inOutParameterMap.put("p_stateno_list", stateno.split(","));
				
				int resultCount = recordSearchReviewDAO.changeRecordSearchStatus(inOutParameterMap);
				
				if(resultCount == 0){
					
					isError = true;
					
					log.error("This stateno already Unflagged as Record Search : {}",stateno);
										
					transactionMessage = "This STATENO already Unflagged for Record Search";
					
				}
				
			}
			
			
			

			

		}  catch (Exception e) {
			isError = true;
			
			log.error("{} Failed to update Record Search Flag Status {}",nyehmsLog,e.getMessage());
			
			if("Y".equals(flagStatus)){
				
				transactionMessage = "Failed to unflag the STATENO. Please contact the helpline.";
				
			}else{
				
				transactionMessage = "Failed to flag the STATENO as Record Search.";
			}
		}

		if (isError) {
			
			
			log.error("Changing the Record Search Flag Status for stateno   {}",stateno);
			
			log.error(transactionMessage);
			
			commonExceptionHandler.inspectDBTransacionResult(statusCode,
					transactionMessage, bindingResult);
			
		} else {
			
			if("Y".equals(flagStatus)){
				commonExceptionHandler.inspectDBTransacionResult(0,
						"STATENO has been flagged for Record Search, review pending.", bindingResult);
			}else{
				commonExceptionHandler.inspectDBTransacionResult(0,
						"Unflagged for a Record Search", bindingResult);
			}
			
		}
		
		return isError;
		
	}
	
	
	public void insertAutoRecordSearchEntry(
			UpdatedDispositionsVO updatedDispositionsVO){
		
		
		
		
		
		Map<String, String> inOutParameters = recordSearchReviewDAO.checkRecordSearchStatenoExists(updatedDispositionsVO);
		

		inOutParameters.put("p_requestComments", updatedDispositionsVO.getDISPOSITION_DESC());
		inOutParameters.put("p_workerId", updatedDispositionsVO.getCREATE_UID());
		
		if(inOutParameters == null || "N".equals(inOutParameters.get("FLAG"))){
			
			recordSearchReviewDAO.insertAutoRecordSearchEntry(inOutParameters);
			
		}else{
			recordSearchReviewDAO.updateAutoRecordSearchEntry(inOutParameters);
		}
		
	}
	
	
	public List<JSONObject> getRecordSearchStatenos(
			BindingResult bindingResult, UserObject userObject){
		return recordSearchReviewDAO.getRecordSearchStatenos();
	}
	
	public List<Map<String, String>> getRecordSearchStatenoFlagHistory(
			String stateno, BindingResult bindingResult, UserObject userObject){
		
		
		return recordSearchReviewDAO.getRecordSearchStatenoFlagHistory(stateno);
	}
	
	@Transactional
	public List<Map<String, String>> generateCDCReturnData(String[] statenoList, String cdcFileName,
			BindingResult bindingResult, UserObject userObject){
		
			
		
			List<Map<String, String>> cdcReturnData = null;
			
			
				
				
			Map<String, Object> inParameterMap = new HashMap<>();
			
			
							
			StringBuilder fileName = new StringBuilder();
			
			if(CommonUtil.isNullOrEmpty(cdcFileName)){
			
				fileName.append("CDCRETURNFILE");
				
				Calendar calendar = Calendar.getInstance();
				
				fileName.append(calendar.get(Calendar.YEAR)).append("_").append(calendar.get(Calendar.MONTH)+1).append("_").append(calendar.get(calendar.DATE)).append("_")
				
				.append(calendar.get(calendar.HOUR)).append("_").append(calendar.get(calendar.MINUTE)).append("_").append(calendar.get(calendar.SECOND)).append("_").append(userObject.getUserId());
				
			}else{
				fileName.append(cdcFileName.toUpperCase());
			}
			
			if(fileName.indexOf(".XLS") == -1 && fileName.indexOf(".XLSX") == -1){
				
				fileName.append(".XLS");
				
			}
			
			inParameterMap.put("fileName", fileName.toString());

			
			inParameterMap.put("workerId", userObject.getWorkerId());
			
			int resultCount = recordSearchReviewDAO.checkFileNameExists(inParameterMap);
			
			if(resultCount > 0){
				throw new DataRetrievalFailureException("File Name already exists, please enter a different name.");
			}
			
			
			inParameterMap.put("p_stateno_list", statenoList);
			
			
			cdcReturnData = recordSearchReviewDAO.generateCDCReturnData(inParameterMap);
			
			if(cdcReturnData == null || cdcReturnData.size() == 0){
				throw new DataRetrievalFailureException("There is no valid data available to generate the CDC file");
			}
			
			inParameterMap.put("cdcReturnData", cdcReturnData);
			
			
			
			
			recordSearchReviewDAO.storeCDCReturnData(inParameterMap);
			
			
			
			
			
			recordSearchReviewDAO.updateRecordSearchStatenosWithFileName(inParameterMap);
			

			recordSearchReviewDAO.updateNoCDCRecordsInFlaggingTable(inParameterMap);

				
			return cdcReturnData;
		
		
		
	}
	
	
	@Transactional
	public List<Map<String, String>> generateCDCReturnDataForAllTheRecords(String cdcFileName,
			BindingResult bindingResult, UserObject userObject){
		
			
		
			List<Map<String, String>> cdcReturnData = null;
			
			
				
				
			Map<String, Object> inParameterMap = new HashMap<>();
			
			
							
			StringBuilder fileName = new StringBuilder();
			
			if(CommonUtil.isNullOrEmpty(cdcFileName)){
			
				fileName.append("CDCRETURNFILE");
				
				Calendar calendar = Calendar.getInstance();
				
				fileName.append(calendar.get(Calendar.YEAR)).append("_").append(calendar.get(Calendar.MONTH)+1).append("_").append(calendar.get(calendar.DATE)).append("_")
				
				.append(calendar.get(calendar.HOUR)).append("_").append(calendar.get(calendar.MINUTE)).append("_").append(calendar.get(calendar.SECOND)).append("_").append(userObject.getUserId());
				
			}else{
				fileName.append(cdcFileName.toUpperCase());
			}
			
			if(fileName.indexOf(".XLS") == -1 && fileName.indexOf(".XLSX") == -1){
				
				fileName.append(".XLS");
				
			}
			
			inParameterMap.put("fileName", fileName.toString());

			
			inParameterMap.put("workerId", userObject.getWorkerId());
			
			int resultCount = recordSearchReviewDAO.checkFileNameExists(inParameterMap);
			
			if(resultCount > 0){
				throw new DataRetrievalFailureException("File Name already exists, please enter a different name.");
			}
			
			
			inParameterMap.put("selectedStatenos", "ALL");
			
			
			cdcReturnData = recordSearchReviewDAO.generateCDCReturnData(inParameterMap);
			
			if(cdcReturnData == null || cdcReturnData.size() == 0){
				throw new DataRetrievalFailureException("There is no valid data available to generate the CDC file");
			}
			
			inParameterMap.put("cdcReturnData", cdcReturnData);
			
			
			
			
			recordSearchReviewDAO.storeCDCReturnData(inParameterMap);
			
			
			recordSearchReviewDAO.updateRecordSearchStatenosWithFileNameForAllRecords(inParameterMap);
			

			recordSearchReviewDAO.updateNoCDCRecordsInFlaggingTable(inParameterMap);

				
			return cdcReturnData;
		
		
		
	}
	
	
	public List<Map<String, String>> getCDCPendingReturnData( String cdcFileName,
			BindingResult bindingResult, UserObject userObject){
		return recordSearchReviewDAO.getCDCPendingReturnData(cdcFileName);
	}
	
	public List<Map<String, String>> getCDCPendingReturnFileNames(){
		return recordSearchReviewDAO.getCDCPendingReturnFileNames();
	}
	
	@Transactional
	public void uploadCDCFileData(String inputFileName,
			MultipartFile uploadedFile, BindingResult bindingResult,
			UserObject userObject) throws IOException{
		
		
		boolean isError = false; 
		
		
		String transactionMessage = null;
		
		BufferedReader bufferedReader;
				
		List<Map<String, Object>>  fileDataList = new ArrayList<>();
			
			if(uploadedFile.isEmpty()){
				
				isError = true;
				transactionMessage = "The file uploaded contains no Data";
				
			}else{
				bufferedReader = new BufferedReader(new InputStreamReader(uploadedFile.getInputStream()));
				
				
				String rowData  = null;
				Integer sentFileSeq = null;
				
				Integer RIDR_COLUMN_NAMES_LENGTH = null;
				String[] CDC_RETURN_COLUMN_NAMES = null;
				
				Map<String, String> inputParameterMap = new HashMap<>();
				inputParameterMap.put("fileName", inputFileName);
				
				
				int rowCount = 0;
				
		
				while ((rowData = bufferedReader.readLine()) != null) {
					
					rowCount++;
					
					if(rowCount == 1) {
						CDC_RETURN_COLUMN_NAMES = uploadProperties.cdcReturnColumnNames().split(",");
						
						RIDR_COLUMN_NAMES_LENGTH = CDC_RETURN_COLUMN_NAMES.length;
						
						String[] columnData = rowData.split("\t");
						
						if(columnData.length != RIDR_COLUMN_NAMES_LENGTH) {
							
							isError = true; 
							
							transactionMessage = "Column headers must match the template";
							
							break;
							
						}
						
						for(int columnIndex = 0; columnIndex < RIDR_COLUMN_NAMES_LENGTH; columnIndex++){
							
							String columnName = columnData[columnIndex].trim();
							
							//columnNameInSheet = columnNameInSheet.replaceAll("\n", " ");
							
							if(!CDC_RETURN_COLUMN_NAMES[columnIndex].equalsIgnoreCase(columnName)){
								
								isError = true; 
								
								transactionMessage = "Column headers must match the template";
								
								break;
								
							}
							
						}
						
						if(isError){
							break;
						}else{
							continue;
						}

						
					}
					
					
					if(CommonUtil.isNullOrEmpty(rowData)){
						
						if(log.isDebugEnabled()){
							log.debug("Empty is row is there");
						}
						continue;
					}
					
					boolean hasRowData = false;
					
					if(CommonUtil.isNotNullAndEmpty(rowData)){
						
						if(rowData.startsWith("********************************")) {
							
							break;
						}else if(rowData.startsWith("*** NO MATCH FOUND ***")) {
							continue;
						}else {
							hasRowData = true;
						}
						

						
					}
					
					if(!hasRowData){
						continue;
					}
					

					
						Map<String, Object> fileData = new HashMap<>();
						
						fileData.put("fileName", inputFileName);
						
						String[] columnData = rowData.split("\t");
						
						
						String _inputStateno = columnData[0].trim();
						String _inputCityNo = columnData[1].trim();
						String _inputLastNameSoundex = columnData[2].trim();
						String _inputDob = columnData[3].trim();
						String _inputBirthSex = columnData[4].trim();
						
//						
						if(CommonUtil.isNullOrEmpty(_inputLastNameSoundex)) {
							  log.error("Input Last name soundex is blank at Rownumber : {}",rowCount);
							  throw new DataRetrievalFailureException("Input file is wrong, Input Last name soundex is blank at row number : "+rowCount);
						}
						
						if(CommonUtil.isNullOrEmpty(_inputDob)) {
							  log.error("Input DOB is blank at Rownumber : {}",rowCount);
							  throw new DataRetrievalFailureException("Input file is wrong, Input DOB is blank at row number : "+rowCount);
						}
						
						if(CommonUtil.isNullOrEmpty(_inputBirthSex)) {
							  log.error("Input Birth sex is blank at Rownumber : {}",rowCount);
							  throw new DataRetrievalFailureException("Input file is wrong, Input Birth sex is blank at row number : "+rowCount);
						}
						
						fileData.put(CDC_RETURN_COLUMN_NAMES[0], _inputStateno);
						fileData.put(CDC_RETURN_COLUMN_NAMES[1], _inputCityNo);
						fileData.put(CDC_RETURN_COLUMN_NAMES[2], _inputLastNameSoundex);
						fileData.put(CDC_RETURN_COLUMN_NAMES[3], _inputDob);
						fileData.put(CDC_RETURN_COLUMN_NAMES[4], _inputBirthSex);
						
						sentFileSeq = recordSearchReviewDAO.getRecordSearchSentSeq(fileData);
						
						if(sentFileSeq == null || sentFileSeq == 0) {
							  log.error("There is no input Reference for Rownumber : {}",rowCount);
							  throw new DataRetrievalFailureException("Input file is wrong, There is no input reference for row number : "+rowCount);
						}
						
						fileData.put("sentFileSeq", sentFileSeq);
						
												
						for(int columnIndex = 5; columnIndex < columnData.length; columnIndex++){
							
							String cellData = columnData[columnIndex].trim();
							
							
							if(CommonUtil.isNotNullAndEmpty(cellData) && !"null".equals(cellData)) {
	
								if(columnIndex == 0 ) {
									cellData=cellData.replaceAll("\\*", "");
								}
								
								fileData.put(CDC_RETURN_COLUMN_NAMES[columnIndex], cellData);
							}

	
							
						}
						
						fileDataList.add(fileData);
						
					
				}
				
			} 
			
			if(!isError){
				Map<String, Object> inputParameterMap = new HashMap<>();
				
				inputParameterMap.put("fileDataList", fileDataList);
				inputParameterMap.put("fileName", inputFileName);
				inputParameterMap.put("workerId", userObject.getWorkerId());
				
//					
//					//Where condition can accept 1000 arguments.
				
				//documentDAO.writeRidrData(inputParameterMap);
				recordSearchReviewDAO.uploadCDCFileData(inputParameterMap);

				
				
				inputParameterMap.remove("fileDataList");
				
				//inputParameterMap.put("sequenceNumbers", sequenceNumbers);
				
				
				int resultCount = recordSearchReviewDAO.updateCDCReturnDataInfo(inputParameterMap);

				if(resultCount == 0){
					
					commonExceptionHandler.inspectDBTransacionResult(-1, "This file is already uploaded.", bindingResult);
					
					throw new DataRetrievalFailureException("This file is already uploaded by other user.");
					
				}else{
					
					commonExceptionHandler.inspectDBTransacionResult(0, "Successfully uploaded CDC Return Data.", bindingResult);
				}
				
				
				log.error("uploaded the CDC Return file and checking for Duplicates");
				
				log.error("Running the query to update CDC not matches");
				
				recordSearchReviewDAO.identifyCDCNoMatches(inputParameterMap);
				
				log.error("update the CDC no matches records");
				
				recordSearchReviewDAO.identifyCDCReturnFileDuplicates(inputParameterMap);
				
				log.error("update the CDC  matches records");
				
				recordSearchReviewDAO.identifyCDCMatches(inputParameterMap);
				
				recordSearchReviewDAO.identifyCDCReturnFileDuplicates(inputParameterMap);
				
				int hasData = recordSearchReviewDAO.checkForRIDREharsData();
				
				if(hasData == 0) {
					
					try {
						
						//String[] recipientEmailAddressesTo = EnvironmentProperties.envProperties.getProperty("EHARS_LOADER_NOTIFICATIONS_TO").split(",");
						
						//sendEmail.sendSSLMessage(
						//		recipientEmailAddressesTo,
						//		"Failed : Resolving records in feedback that are already known as 'Same As' in EHARS", "There is no data in EHARS_RIDR table" ,
						//		EnvironmentProperties.envProperties.getProperty("helpLineFrom"), null,
						//		CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
						sendEmail.processEmail(
								null,
								"Failed : Resolving records in feedback that are already known as 'Same As' in EHARS", "There is no data in EHARS_RIDR table" ,
								"RECORD_SEARCH_UPLOAD",
								CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
						
					}catch(MessagingException e){
						log.error("Failed : Resolving records in feedback that are already known as 'Same As' in EHARS");
						log.error(e.getMessage());
						
					}
					
				}else {
					int sameAsRecordsCount = recordSearchReviewDAO.updateSameAsRecordsReturnFile();
					
					log.error("Total matching RIDR same as records count : {}",sameAsRecordsCount);
				}
				
				
				recordSearchReviewDAO.updateRecordsWorkedPreviously();
				
				
				
			}else{
				commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
			}
				
		
		
	}
	
	public List<String> getCDCReportingStateCodesToProcess(){
		return recordSearchReviewDAO.getCDCReportingStateCodesToProcess();
	}
	
	public List<RecordSearchResultsVO> getRecordSearchViewResults(
			String stateValue){
		

		Map<String,String> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("stateValue", stateValue);
		
		return recordSearchReviewDAO.getRecordSearchViewResults(inputParameterMap);
	}
	
	public void updateRecordSearchDocumentWithAcrf(String acrfReferenceRowId, List<String> duplicateStatenos, String stateno,
			int documentId, BindingResult bindingResult, UserObject userObject){
		

		Map<Object,Object> inputParameterMap = new HashMap<>();
		
		
		
		String sentFileName = recordSearchReviewDAO.getSentFileName(acrfReferenceRowId);
		
		
		inputParameterMap.put("sentFileName", sentFileName);
		
		
		inputParameterMap.put("recordSearchReturnRowId", acrfReferenceRowId);
		
		inputParameterMap.put("duplicateStatenos", duplicateStatenos);
		
		inputParameterMap.put("nyehmsStateno", stateno);
		
		inputParameterMap.put("documentId", documentId);
		
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		
		int resultCount = recordSearchReviewDAO.updateRecordSearchDocumentWithAcrf(inputParameterMap);
		
 		if(resultCount  > 0){
			List<String> pendingStates = recordSearchReviewDAO.getPendingResolutionStates(inputParameterMap);
			
			
			if(pendingStates == null || pendingStates.size() == 0){
				
				
				
				inputParameterMap.put("p_stateno_list", stateno.split(","));
				
				inputParameterMap.put("p_requestComments", "Resolved all pending Resolution(s)");
				   	   	   
				resultCount = recordSearchReviewDAO.changeRecordSearchStatus(inputParameterMap);
				
				
				commonExceptionHandler.addBindingResultMessage(bindingResult,"RECORD_SEARCH_STATUS_MESSAGE", "Successfully associated RECORD_SEARCH and the STATENO Unflagged for a Record Search");
				
			}else{
				commonExceptionHandler.addBindingResultMessage(bindingResult, "RECORD_SEARCH_STATUS_MESSAGE", "Person will not be Unflagged for record search, pending resolution(s) in : <br/><br/><span class='bold alignCenter'>"+pendingStates+"</span>");
			}
			
		}
		
		
		
		
	}
	
	public List<Map<String, String>> downloadCDCReturnFile(String cdcFileName,
			BindingResult bindingResult, UserObject userObject){
		return recordSearchReviewDAO.downloadCDCReturnFile(cdcFileName);
	}
	
	public void replaceRecordSearchStateno(String recSearchSourceStateno,
			String recSearchStatenoToReplace, String recSearchComments){
		
		Map<Object,Object> inputParameterMap = new HashMap<>();
		
		
		inputParameterMap.put("recSearchSourceStateno", recSearchSourceStateno);
		
		inputParameterMap.put("recSearchStatenoToReplace", recSearchStatenoToReplace);
		
		inputParameterMap.put("recSearchComments", recSearchComments);
		
		int resultCount = recordSearchReviewDAO.replaceRecordSearchStateno(inputParameterMap);
		
		if(resultCount == 0){
			
			log.error("Failed while replacing the Stateno from ({}) to ({})",recSearchSourceStateno,recSearchStatenoToReplace);
			
			throw new DataRetrievalFailureException("Failed while replacing the Stateno ");
		}else{
			
			log.error("Record Search : replacing the Stateno from ({}) to ({}) successfull",recSearchSourceStateno,recSearchStatenoToReplace);
			
		}
		
		
	}
	
	public int rejectCDCFileFromSentTable(String cdcFileName,
			UserObject userObject){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("cdcFileName", cdcFileName);
		inputParameterMap.put("workerId", userObject.getWorkerId());
		
		return recordSearchReviewDAO.rejectCDCFileFromSentTable(inputParameterMap);
	}

	public int rejectCDCFileFromFlaggingTable(String cdcFileName,
			UserObject userObject){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("cdcFileName", cdcFileName);
		inputParameterMap.put("workerId", userObject.getWorkerId());
		
		return recordSearchReviewDAO.rejectCDCFileFromFlaggingTable(inputParameterMap);
		
	}
	
	public List<Map<String, String>> getRecordSearchStatenoHistory(String stateNo,
			BindingResult bindingResult, UserObject userObject){
		
		return recordSearchReviewDAO.getRecordSearchStatenoHistory(stateNo);
		
	}
}
