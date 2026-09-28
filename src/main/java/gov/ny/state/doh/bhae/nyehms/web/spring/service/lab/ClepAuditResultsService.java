package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.ClepAuditResultsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultAccessionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPUploadSurveyVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

@Service
public class ClepAuditResultsService {

	@Autowired 
	ClepAuditResultsDAO clepAuditResultsDAO;
	
	@Autowired	
	LabReportsTransactionHelper labReportsTransactionHelper;
	
	@Autowired 
	private CommonExceptionHandler commonExceptionHandler;	
	
	private static Logger log = LoggerFactory.getLogger(ClepAuditResultsService.class);
	
	public JSONObject getPFILabDataInfo(String userEnteredPfi) {
		return clepAuditResultsDAO.getPFILabDataInfo(userEnteredPfi);
	}
	
	public void saveCLEPLabResultForm(CLEPLabResultVO clepLabResultVO, BindingResult bindingResult,	UserObject userObject) {
		
		try {
			
			labReportsTransactionHelper.saveClepLabTestResultData(clepLabResultVO, bindingResult, userObject);
			
			if (clepLabResultVO.isReviewCompletedClicked()) {
				commonExceptionHandler.inspectDBTransacionResult(0, "Survey No : " + clepLabResultVO.getSurveyNumber() + " is marked as reviewed and closed successfully.", bindingResult);
			} else {
				commonExceptionHandler.inspectDBTransacionResult(0, "CLEP Lab result form saved successfully. Survey No : " + clepLabResultVO.getSurveyNumber(), bindingResult);
			}
			
		} catch (DataAccessException dataEx) {
			
    		log.error("Data Error while Saving CLEP Lab Result Form.  {}", dataEx.getMessage());
    		
    		commonExceptionHandler.handleException(dataEx, userObject, userObject.getSessionId());
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, dataEx.getMessage(), bindingResult);
    		
		} catch (Exception ex) {
			
			log.error("Error while Saving CLEP Lab Result Form.  {}", ex.getMessage());
    		
    		commonExceptionHandler.handleException(ex, userObject, userObject.getSessionId());
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, "Error while Saving CLEP Lab Result Form, please contact the Helpline.", bindingResult);
		
		}
		
		
	}


	public JSONObject seachAccessionNoinDB(String accessionNo) {
		return clepAuditResultsDAO.seachAccessionNoinDB(accessionNo);
	}

	public void insertCLEPLabResultData(CLEPLabResultVO clepLabResultVO, BindingResult bindingResult, UserObject userObject) {
		
		clepLabResultVO.setClepUserId(userObject.getUserId());
		
		int surveyNumber = clepLabResultVO.getSurveyNumber();
		
		if (surveyNumber == 0) {
			clepAuditResultsDAO.saveLaftClepAuditSurveyData(clepLabResultVO);
			surveyNumber = clepLabResultVO.getSurveyNumber();
			if (surveyNumber == 0) {
				log.error("Failed while storing CLEP Lab Result Data.");
				throw new DataRetrievalFailureException("Error while saving CLEP Lab Result Data, please contact the Helpline.");
			}

			List <CLEPLabResultAccessionVO> accessionNoList = clepLabResultVO.getClepLabResultAccessionList();
			if (accessionNoList != null && accessionNoList.size() > 0) {
				
				List<CLEPLabResultAccessionVO> finalaccessionNoList = new ArrayList<>();
				for(CLEPLabResultAccessionVO accessionVO : accessionNoList) {
					if (CommonUtil.isNotNullAndEmpty(accessionVO.getAccessionNo())) {
						accessionVO.setAccessionSerialNo(accessionNoList.indexOf(accessionVO) + 1);
						finalaccessionNoList.add(accessionVO);
					}
				}
				
				if (finalaccessionNoList != null && finalaccessionNoList.size() > 0) {
				
					Map<String, Object> inputParameterMap  = new HashMap<>();
					inputParameterMap.put("surveyNumber", clepLabResultVO.getSurveyNumber());
					inputParameterMap.put("accessionNoList", finalaccessionNoList);
					
					int resCt = clepAuditResultsDAO.saveLaftClepAccessionData(inputParameterMap);
					if(resCt == 0){
						log.error("Failed while storing CLEP Lab Accession Data.");
						throw new DataRetrievalFailureException("Error while saving CLEP Lab Accession Data, please contact the Helpline.");
					}
				}
				
			}
		} else {
			int resCount = clepAuditResultsDAO.updateLaftClepAuditSurveyData(clepLabResultVO);
			if(resCount == 0){
				log.error("Failed while updating CLEP Audit Survey Data.");
				throw new DataRetrievalFailureException("Error while updating CLEP Audit Survey Data, please contact the Helpline.");
			}
			
			List <CLEPLabResultAccessionVO> accessionNoList = clepLabResultVO.getClepLabResultAccessionList();
			if (accessionNoList != null && accessionNoList.size() > 0) {
				
				List<CLEPLabResultAccessionVO> newDataAccessionNoList = new ArrayList<>();
				List<CLEPLabResultAccessionVO> oldDataAccessionNoList = new ArrayList<>();
				for(CLEPLabResultAccessionVO accessionVO : accessionNoList) {
					if (CommonUtil.isNotNullAndEmpty(accessionVO.getAccessionNo()) && accessionVO.getAccessionSerialNo() == 0) {
						accessionVO.setAccessionSerialNo(accessionNoList.indexOf(accessionVO) + 1);
						newDataAccessionNoList.add(accessionVO);
					} else if (CommonUtil.isNotNullAndEmpty(accessionVO.getAccessionNo()) && accessionVO.getAccessionSerialNo() != 0) {
						oldDataAccessionNoList.add(accessionVO);
					}
				}
				
				if (newDataAccessionNoList != null && newDataAccessionNoList.size() > 0) {
				
					Map<String, Object> inputParameterMap  = new HashMap<>();
					inputParameterMap.put("surveyNumber", clepLabResultVO.getSurveyNumber());
					inputParameterMap.put("accessionNoList", newDataAccessionNoList);
					
					int resCt = clepAuditResultsDAO.saveLaftClepAccessionData(inputParameterMap);
					if(resCt == 0){
						log.error("Failed while inserting new CLEP Lab Accession Data.");
						throw new DataRetrievalFailureException("Error while inserting new CLEP Lab Accession Data, please contact the Helpline.");
					}
				}
				
				if (oldDataAccessionNoList != null && oldDataAccessionNoList.size() > 0) {
					Map<String, Object> inputParameterMap  = new HashMap<>();
					inputParameterMap.put("surveyNumber", clepLabResultVO.getSurveyNumber());
					
					for (CLEPLabResultAccessionVO accessionVO : oldDataAccessionNoList ) {
						inputParameterMap.put("accessionSerialNo", accessionVO.getAccessionSerialNo());
						inputParameterMap.put("accessionVO", accessionVO);
						int resCt = clepAuditResultsDAO.updateLaftClepAccessionData(inputParameterMap);
						if(resCt == 0){
							log.error("Failed while updating CLEP Lab Accession Data.");
							throw new DataRetrievalFailureException("Error while updating CLEP Lab Accession Data, please contact the Helpline.");
						}
					}
				}
				
			}
			
			if (clepLabResultVO.isReviewCompletedClicked()) {
				Map<String, Object> inputParameterMap  = new HashMap<>();
				inputParameterMap.put("surveyNo", clepLabResultVO.getSurveyNumber());
				inputParameterMap.put("surveyReviewedBy", clepLabResultVO.getClepUserId());	
				int resCt = clepAuditResultsDAO.updateSurveyReviewComplete(inputParameterMap);
				if(resCt == 0){
					log.error("Failed while updating survey review completed.");
					throw new DataRetrievalFailureException("Error while updating survey review completed, please contact the Helpline.");
				}
			}
		}

		
	}

	public List<JSONObject> getClepLabResultForReview(BindingResult bindingResult, UserObject userObject) {
		
		List<JSONObject> list = clepAuditResultsDAO.getClepLabResultForReview();
		return list;
	}

	public CLEPLabResultVO getClepSurveyData(int surveyNo, UserObject userObject) {
		
		CLEPLabResultVO CLEPLabSurveyData = clepAuditResultsDAO.getClepSurveyData(surveyNo);
		
		List<CLEPLabResultAccessionVO> CLEPLabSurveyAccessionList = clepAuditResultsDAO.getClepSurveyAccession(surveyNo);
		
		int accessionListSize = CLEPLabSurveyAccessionList.size();
		
		if (accessionListSize < 8) {
			for (int i=accessionListSize; i < 8; i++) {
	    		CLEPLabResultAccessionVO cLEPLabResultAccessionVO = new CLEPLabResultAccessionVO();
	    		cLEPLabResultAccessionVO.setLaftStaff(userObject.getUserId());
	    		CLEPLabSurveyAccessionList.add(cLEPLabResultAccessionVO);
			}
		}
		
		CLEPLabSurveyData.setClepLabResultAccessionList(CLEPLabSurveyAccessionList);
		
		return CLEPLabSurveyData;
	}

	public Map<Object, Object> uploadHIVSurveysFile(MultipartFile uploadHIVSurveyFile, BindingResult bindingResult, UserObject userObject) throws IOException {
		
		boolean isError = false; 
		String transactionMessage = null;
		
		List<Map<String, Object>>  fileDataList = new ArrayList<>();
		Map<Object, Object> resultMap = new HashMap<>();
		String uploadFileName = uploadHIVSurveyFile.getOriginalFilename();
		
		CSVReader csvread = null;
		try{
			
			int uploadFileNameCt = 	 clepAuditResultsDAO.getHivSurveyFilenameCt(uploadFileName);
			
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
				
				csvread = new CSVReader(new InputStreamReader(uploadHIVSurveyFile.getInputStream()));
				List<String[]> rowLists = csvread.readAll();
				
				String[] columnHeader = rowLists.get(0);
				String[] UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES = EnvironmentProperties.envProperties.getProperty("UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES").split(",");
				int UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES_LENGTH = UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES.length;
				
				for(int columnIndex = 0; columnIndex < UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES_LENGTH; columnIndex++){
					
					String headerColumnName = columnHeader[columnIndex];
					headerColumnName = headerColumnName.replaceAll("[^a-zA-Z0-9_]", "");
					headerColumnName = headerColumnName.replaceAll("\n", " ");
					
					if(!UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES[columnIndex].equalsIgnoreCase(headerColumnName.trim())){
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
						for(int columnIndex = 0; columnIndex < UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES_LENGTH; columnIndex++){
							
							if(!(CommonUtil.isNullOrEmpty(recordRow[columnIndex]))){
								hasRowData = true;
								break;
							}
						}
						
						if (hasRowData) {
							for(int columnIndex=0; columnIndex<UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES_LENGTH;columnIndex++){					
								if (!(columnIndex == 3 || columnIndex == 4) ) {
									fileData.put(UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES[columnIndex], recordRow[columnIndex]);
								} else {
									if (CommonUtil.isNotNullAndEmpty(recordRow[columnIndex]) && recordRow[columnIndex].trim().length() < 10) {
										fileData.put(UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES[columnIndex], DateUtil.convertDateMMDDYYYY(recordRow[columnIndex]));
									} else {
										fileData.put(UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES[columnIndex], recordRow[columnIndex]);
									}
								}
							}
							
							fileDataList.add(fileData);
						}
					}
				}
			}
			if (fileDataList.size() == 0 && !isError) {
				isError = true;
				transactionMessage = "The selected file contains no CLEP HIV Survey Data. Please use template to create the data file.";
			}
			
			if (!isError) {
				resultMap = validateCLEPHIVSurveyData(fileDataList);
				
				if (resultMap.size() > 0) {
					isError = true;
					transactionMessage = "Following data errors are found, please fix and upload again.";
				}
			}
			
			if(!isError){
				
				labReportsTransactionHelper.uploadClepHIVSurveyData(uploadFileName, fileDataList, userObject);
				
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
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully uploaded the CLEP HIV Survey  Data of " + fileDataList.size() + " records.", bindingResult);
		}
		
		return resultMap;
	}

	private Map<Object, Object>  validateCLEPHIVSurveyData(List<Map<String, Object>>  fileDataList) {
		
		int counter = 0;
		String PFIString;
		StringBuilder errorMessage;
		Map<Object, Object> dataErrorMapList = new HashMap<>();
		
		for (Map<String, Object> dataRow : fileDataList) {
			
			errorMessage = new StringBuilder();
			counter++;
			
			if ( (dataRow.get("PFI") == null || CommonUtil.isNullOrEmpty(dataRow.get("PFI").toString()) ) || !CommonUtil.isNumeric(dataRow.get("PFI").toString()) ) {
				PFIString = "Row " + counter;
				errorMessage.append("PFI is a required field and can only be a numeric value.<br/>");
			} else {
				PFIString = dataRow.get("PFI").toString();
			}
			
			errorMessage = validateVar(dataRow, "CLIA", "STRING", errorMessage);
			errorMessage = validateVar(dataRow, "LAB_NAME", "STRING", errorMessage);
			errorMessage = validateVar(dataRow, "SURVEY_BEGIN_DATE", "DATE_REQUIRED", errorMessage);
			errorMessage = validateVar(dataRow, "SURVEY_END_DATE", "DATE_REQUIRED", errorMessage);

			if (CommonUtil.isNotNullAndEmpty(errorMessage.toString())) {
				dataErrorMapList.put(PFIString, errorMessage);
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
			if ( (currentObj == null || CommonUtil.isNullOrEmpty(currentObj.toString()) ) || !CommonUtil.isNumeric(currentObj.toString()) ) {
				errorMessage.append( passedVar + " is a required field  and can only be a numeric value.<br/>");
			}
		} else if(varType.equals("DATE_REQUIRED")) {
			if ( (currentObj == null || CommonUtil.isNullOrEmpty(currentObj.toString()) ) || !DateUtil.isValidDate(currentObj.toString()) ) {
				errorMessage.append( passedVar + " is a required field and must follow the format MM/DD/YYYY. Should be greater than 01/01/1900 and no future dates.<br/>");
			}
		} 
		
	return errorMessage;
	}

	public void uploadClepHIVSurveyData(String fileName, List<Map<String, Object>> clepHIVSurveyDataList, String userId)		throws SQLException {
		
		for (Map<String, Object> clepHIVSurveyData : clepHIVSurveyDataList) {
			clepHIVSurveyData.put("fileName", fileName);
			clepHIVSurveyData.put("userId", userId);
		}
		
		Map<String, Object> inputParameterMap  = new HashMap<>();
		inputParameterMap.put("clepHIVSurveyDataList", clepHIVSurveyDataList);
		
		clepAuditResultsDAO.insertCLEPHIVSurveyData(inputParameterMap);
		
	}

	public List<String> getUploadedHIVSurveyFilenames(UserObject userObject, BindingResult result) {
		
		List<String> filenameList = new ArrayList<>();
		
		List<JSONObject> filenameObjlist = clepAuditResultsDAO.getUploadedHIVSurveyFilenames();
		
		for (JSONObject jsonObj : filenameObjlist) {
			String filenameDetail = jsonObj.get("CLEP_FILE_NAME") + ",  " + jsonObj.get("FILE_UPLOAD_BY") + ",  " + jsonObj.get("FILE_UPLOAD_DATE");
			filenameList.add(filenameDetail);
		}
		
		return filenameList;
	}

	public List<JSONObject> getUploadedHIVSurveyData(String uploadedFilename, BindingResult bindingResult,	UserObject userObject) {
		 String[] array = uploadedFilename.split(",");
		 uploadedFilename = array[0];
		 
		Map<String, Object> inputParameterMap  = new HashMap<>();
		inputParameterMap.put("uploadedFilename", uploadedFilename);
		
		List<JSONObject> list = clepAuditResultsDAO.getUploadedHIVSurveyData(inputParameterMap);
		return list;
	}

	public CLEPUploadSurveyVO getClepSurveyUploadData(int uploadNo, UserObject userObject) {
		// TODO Auto-generated method stub
		
		CLEPUploadSurveyVO clepUploadSurvey = clepAuditResultsDAO.getClepSurveyUploadData(uploadNo);
		
		if (CommonUtil.isNullOrEmpty(clepUploadSurvey.getAssignedBy())) {
			clepUploadSurvey.setAssignedBy(userObject.getUserId());
		}
		if (CommonUtil.isNullOrEmpty(clepUploadSurvey.getSurveyCloseBy())) {
			clepUploadSurvey.setSurveyCloseBy(userObject.getUserId());
		}
		return clepUploadSurvey;
	}

	public void saveFollowupHIVSurveyData(CLEPUploadSurveyVO clepUploadSurveyVO, BindingResult bindingResult,	UserObject userObject) {
		
		int uploadNumber = clepUploadSurveyVO.getUploadNumber();
		String assignedStaffId = clepUploadSurveyVO.getAssignedStaffId();
		String surveyCloseDate = clepUploadSurveyVO.getSurveyCloseDate();
		
		if (CommonUtil.isNullOrEmpty(assignedStaffId)) {
			clepUploadSurveyVO.setAssignedDate("");
			clepUploadSurveyVO.setAssignedBy("");
		}
		
		if (surveyCloseDate.equals("../../....")) {
			clepUploadSurveyVO.setSurveyCloseBy("");
			clepUploadSurveyVO.setSurveyCloseDate("");
		}
		
		Map<String, Object> inputParameterMap  = new HashMap<>();
		inputParameterMap.put("uploadNumber", uploadNumber);
		inputParameterMap.put("assignedStaffId", assignedStaffId);
		inputParameterMap.put("assignedDate", clepUploadSurveyVO.getAssignedDate());
		inputParameterMap.put("assignedBy", clepUploadSurveyVO.getAssignedBy());
		inputParameterMap.put("surveyCloseDate", clepUploadSurveyVO.getSurveyCloseDate());
		inputParameterMap.put("surveyCloseBy", clepUploadSurveyVO.getSurveyCloseBy());
		inputParameterMap.put("comments", clepUploadSurveyVO.getComments());
		
		clepAuditResultsDAO.updateFollowupHIVSurveyData(inputParameterMap);
		
		commonExceptionHandler.inspectDBTransacionResult(0, "Followup HIV survey data saved successfully.", bindingResult);
		
	}
	
	public List<JSONObject> getLabRecruitmentAssessmentForFollowup(BindingResult bindingResult, UserObject userObject) {
		
		List<JSONObject> list = clepAuditResultsDAO.getLabRecruitmentAssessmentForFollowup();
				return list;
	}

	public CLEPLabResultVO getLabRecruitmentAssessmentData(int surveyNo, UserObject userObject) {

		CLEPLabResultVO labRecruitmentAssessmentData = clepAuditResultsDAO.getLabRecruitmentAssessmentData(surveyNo);
		
		if (CommonUtil.isNullOrEmpty(labRecruitmentAssessmentData.getRecruitAssessStaffAssignBy())) {
			labRecruitmentAssessmentData.setRecruitAssessStaffAssignBy(userObject.getUserId());
		}
		if (CommonUtil.isNullOrEmpty(labRecruitmentAssessmentData.getRecruitAssessStaffComplete())) {
			labRecruitmentAssessmentData.setRecruitAssessStaffComplete(userObject.getUserId());
		}
		return labRecruitmentAssessmentData;
	}

	public List<Map<String, String>> getRecruitAssessmentOutcome() {
		return clepAuditResultsDAO.getRecruitAssessmentOutcomeList();
	}	
	
	public void saveRecruitAssessFollowupData(CLEPLabResultVO clepLabResultVO, BindingResult bindingResult,	UserObject userObject) {
		
		String recruitAssessStaffAssign = clepLabResultVO.getRecruitAssessStaffAssign();
		String recruitAssessDateAssign = clepLabResultVO.getRecruitAssessDateAssign();
		String recruitAssessDateComplete = clepLabResultVO.getRecruitAssessDateComplete();
		
		if (CommonUtil.isNullOrEmpty(recruitAssessStaffAssign)) {
			clepLabResultVO.setRecruitAssessDateAssign("");
			clepLabResultVO.setRecruitAssessStaffAssignBy("");
		}
		
		if (recruitAssessDateAssign.equals("../../....")) {
			clepLabResultVO.setRecruitAssessStaffAssignBy("");
			clepLabResultVO.setRecruitAssessDateAssign("");
		}
		
		if (recruitAssessDateComplete.equals("../../....")) {
			clepLabResultVO.setRecruitAssessStaffComplete("");
			clepLabResultVO.setRecruitAssessDateComplete("");
		}
		
		Map<String, Object> inputParameterMap  = new HashMap<>();
		inputParameterMap.put("surveyNumber", clepLabResultVO.getSurveyNumber());
		inputParameterMap.put("recruitAssessStaffAssign", recruitAssessStaffAssign);
		inputParameterMap.put("recruitAssessDateAssign", clepLabResultVO.getRecruitAssessDateAssign());
		inputParameterMap.put("recruitAssessStaffAssignBy", clepLabResultVO.getRecruitAssessStaffAssignBy());
		inputParameterMap.put("recruitAssessOutcome", clepLabResultVO.getRecruitAssessOutcome());
		inputParameterMap.put("recruitAssessDateComplete", clepLabResultVO.getRecruitAssessDateComplete());
		inputParameterMap.put("recruitAssessStaffComplete", clepLabResultVO.getRecruitAssessStaffComplete());
		inputParameterMap.put("recruitAssessComments", clepLabResultVO.getRecruitAssessComments());
		
		clepAuditResultsDAO.updateRecruitmentAssessmentFollowupData(inputParameterMap);
		
		commonExceptionHandler.inspectDBTransacionResult(0, "Followup Recruitment Assessment data saved successfully.", bindingResult);
		
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getSurveyAuditReportData(String fromDate, String toDate, BindingResult dummyBindingResult,	UserObject userObject) {
		
		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_from_date", fromDate);
		inputData.put("p_to_date", toDate);
		
		clepAuditResultsDAO.getSurveyAuditReportData(inputData);
		
		List<JSONObject> surveyAuditData = (List<JSONObject>)inputData.get("p_survey_audit_data");
		
		for (JSONObject jsonObj : surveyAuditData) {
			jsonObj.put("SURVEY_FROM_DATE", fromDate);
			jsonObj.put("SURVEY_TO_DATE", toDate);
		}

		return surveyAuditData;
	}
}
