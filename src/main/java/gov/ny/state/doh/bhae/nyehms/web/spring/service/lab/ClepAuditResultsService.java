package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.UploadProperties;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.JsonRows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.JsonRows.Column;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;

@Service
public class ClepAuditResultsService {

	@Autowired
	private UploadProperties uploadProperties;

	@Autowired 
	ClepAuditResultsDAO clepAuditResultsDAO;
	
	@Autowired	
	LabReportsTransactionHelper labReportsTransactionHelper;
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

	@Autowired
	private JsonRows jsonRows;

	private static final Logger log = LoggerFactory.getLogger(ClepAuditResultsService.class);

	/*
	 * The read queries of the former ClepAuditResultsDAO, run as JPA native queries (JsonRows): the SQL is unchanged,
	 * values are bound, and every column is read with getObject as MyBatis read it (the screens' JavaScript reads the
	 * JSON). The survey form reads into VOs and the writes are still in ClepAuditResultsDAO.
	 */

	private static final String PFI_LAB_DATA = """
			select PFI, CLIA, LAB_NAME, ADDRESS, CITY, STATE, ZIP, COUNTRY, LAFT_STAFF, CLA.APPROVAL_STATUS_DESC
			from lab_physical_facility lpf, CODED_LAB_APPROVAL_STATUS cla
			where PFI = ?1
			and LPF.APPROVAL_STATUS_CODE = CLA.APPROVAL_STATUS_CODE (+)
			""";

	private static final List<Column> PFI_LAB_DATA_COLUMNS = values("PFI", "CLIA", "LAB_NAME", "ADDRESS", "CITY", "STATE", "ZIP", "COUNTRY",
			"LAFT_STAFF", "APPROVAL_STATUS_DESC");

	private static final String ACCESSION_COUNT = "select count(ACCESSION_NUMBER) count from STD_HIV_ECLRS_DOC where ACCESSION_NUMBER = ?1";

	private static final String CLEP_LAB_RESULTS_FOR_REVIEW = """
			  SELECT survey_number, To_Char(receive_date, 'MM/DD/YYYY') receive_date, ls.pfi, clia, lab_name, lpf.approval_status_code, cla.approval_status_desc, laft_staff,
			                          review_completed, review_completed_by, To_Char(review_completed_date, 'MM/DD/YYYY') review_completed_date,
			                          To_Char(survey_date, 'MM/DD/YYYY') survey_date, To_Char(create_date, 'MM/DD/YYYY') create_date , address, city, state, zip, country
			                          ,         CASE
			                                                  WHEN NOT EXISTS (SELECT 1
			                                                                                                     FROM laft_clep_audit_accession_data a
			                                                                                                    WHERE a.survey_number = ls.survey_number) THEN 'Y'
			                                                  WHEN (SELECT COUNT(*)
			                                                                          FROM laft_clep_audit_accession_data a
			                                                                 WHERE a.survey_number = ls.survey_number
			                                                                           AND a.accession_number IS NOT NULL
			                                                                           AND Nvl(reported_in_nyehms, 'N') != 'Y') > 0 THEN 'Y'
			                                                  ELSE 'N'
			                                      END attention_needed
			    FROM laft_clep_audit_survey_form ls, lab_physical_facility lpf, coded_lab_approval_status cla
			   WHERE ls.pfi = lpf.pfi
			     AND lpf.approval_status_code = cla.approval_status_code (+)
			ORDER BY survey_number DESC
			""";

	private static final List<Column> CLEP_LAB_RESULTS_FOR_REVIEW_COLUMNS = values("SURVEY_NUMBER", "RECEIVE_DATE", "PFI", "CLIA", "LAB_NAME",
			"APPROVAL_STATUS_CODE", "APPROVAL_STATUS_DESC", "LAFT_STAFF", "REVIEW_COMPLETED", "REVIEW_COMPLETED_BY", "REVIEW_COMPLETED_DATE",
			"SURVEY_DATE", "CREATE_DATE", "ADDRESS", "CITY", "STATE", "ZIP", "COUNTRY", "ATTENTION_NEEDED");

	private static final String HIV_SURVEY_FILE_COUNT = "SELECT COUNT(*) CT FROM LAFT_CLEP_AUDIT_SURVEY_UPLOAD WHERE CLEP_FILE_NAME = ?1";

	private static final String UPLOADED_HIV_SURVEY_FILES = """
			select distinct CLEP_FILE_NAME, FILE_UPLOAD_BY,  TO_CHAR(FILE_UPLOAD_DATE, 'MM/DD/YYYY') FILE_UPLOAD_DATE
			from LAFT_CLEP_AUDIT_SURVEY_UPLOAD
			""";

	private static final List<Column> UPLOADED_HIV_SURVEY_FILES_COLUMNS = values("CLEP_FILE_NAME", "FILE_UPLOAD_BY", "FILE_UPLOAD_DATE");

	/** The uploaded surveys; "and CLEP_FILE_NAME = ?1" follows for one file. */
	private static final String UPLOADED_HIV_SURVEYS = """
			select  u.PFI_UPLOAD_PK, u.PFI, u.CLIA, u.LAB_NAME, To_Char(u.SURVEY_BEGIN_DATE, 'MM/DD/YYYY') SURVEY_BEGIN_DATE, To_Char(u.SURVEY_END_DATE, 'MM/DD/YYYY') SURVEY_END_DATE,
			    u.ASSIGNED_STAFF_ID, To_Char(u.ASSIGNED_DATE, 'MM/DD/YYYY') ASSIGNED_DATE, u.ASSIGNED_BY, To_Char(u.SURVEY_CLOSE_DATE, 'MM/DD/YYYY') SURVEY_CLOSE_DATE, u.SURVEY_CLOSE_BY, u.COMMENTS,
			    CASE
			        WHEN u.SURVEY_NUMBER != 0 THEN 'Yes'
			        ELSE 'No'
			    END AUDIT_FORM_RECEIVED,
			    NVL(u.SURVEY_NUMBER, 0)  SURVEY_NUMBER, To_Char(f.RECEIVE_DATE, 'MM/DD/YYYY') RECEIVE_DATE,
			    CLEP_FILE_NAME, FILE_UPLOAD_BY, To_Char(FILE_UPLOAD_DATE, 'MM/DD/YYYY') FILE_UPLOAD_DATE
			from LAFT_CLEP_AUDIT_SURVEY_UPLOAD u, LAFT_CLEP_AUDIT_SURVEY_FORM f
			where u.SURVEY_NUMBER  = f.SURVEY_NUMBER (+)
			""";

	private static final List<Column> UPLOADED_HIV_SURVEYS_COLUMNS = values("PFI_UPLOAD_PK", "PFI", "CLIA", "LAB_NAME", "SURVEY_BEGIN_DATE",
			"SURVEY_END_DATE", "ASSIGNED_STAFF_ID", "ASSIGNED_DATE", "ASSIGNED_BY", "SURVEY_CLOSE_DATE", "SURVEY_CLOSE_BY", "COMMENTS",
			"AUDIT_FORM_RECEIVED", "SURVEY_NUMBER", "RECEIVE_DATE", "CLEP_FILE_NAME", "FILE_UPLOAD_BY", "FILE_UPLOAD_DATE");

	private static final String LAB_RECRUITMENT_FOR_FOLLOWUP = """
			SELECT a.survey_number, a.pfi, b.clia, b.lab_name, c.approval_status_desc, a.inst_based_reportable_hiv, a.lab_uses_eclrs,
			    a.recruit_assess_staff_assign, TO_CHAR(a.recruit_assess_date_assign, 'MM/DD/YYYY') recruit_assess_date_assign, d.recruit_assess_outcome_desc,
			    a.recruit_assess_comments, TO_CHAR(a.recruit_assess_date_complete, 'MM/DD/YYYY') recruit_assess_date_complete, a.recruit_assess_staff_complete
			  FROM laft_clep_audit_survey_form a
			  JOIN lab_physical_facility b ON b.pfi = a.pfi
			  LEFT OUTER JOIN coded_lab_approval_status c ON Nvl(b.approval_status_code, 0) = c.approval_status_code
			  LEFT OUTER JOIN coded_lab_recruit_assess_outcome d ON Nvl(a.recruit_assess_outcome , 0) = d.recruit_assess_outcome
			WHERE (a.inst_based_reportable_hiv = 'Y' OR a.lab_uses_eclrs = 'Y')
			   AND Nvl(b.approval_status_code, 2) IN (2, 4, 5)
			   ORDER BY survey_number DESC
			""";

	private static final List<Column> LAB_RECRUITMENT_FOR_FOLLOWUP_COLUMNS = values("SURVEY_NUMBER", "PFI", "CLIA", "LAB_NAME",
			"APPROVAL_STATUS_DESC", "INST_BASED_REPORTABLE_HIV", "LAB_USES_ECLRS", "RECRUIT_ASSESS_STAFF_ASSIGN", "RECRUIT_ASSESS_DATE_ASSIGN",
			"RECRUIT_ASSESS_OUTCOME_DESC", "RECRUIT_ASSESS_COMMENTS", "RECRUIT_ASSESS_DATE_COMPLETE", "RECRUIT_ASSESS_STAFF_COMPLETE");

	private static final String RECRUIT_ASSESS_OUTCOMES = "select RECRUIT_ASSESS_OUTCOME, RECRUIT_ASSESS_OUTCOME_DESC from CODED_LAB_RECRUIT_ASSESS_OUTCOME";

	private static List<Column> values(String... labels) {
		return Arrays.stream(labels).map(JsonRows::value).toList();
	}

	public JSONObject getPFILabDataInfo(String userEnteredPfi) {
		return jsonRows.one(PFI_LAB_DATA, PFI_LAB_DATA_COLUMNS, userEnteredPfi);
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
		return jsonRows.one(ACCESSION_COUNT, values("COUNT"), accessionNo);
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
		
		return jsonRows.list(CLEP_LAB_RESULTS_FOR_REVIEW, CLEP_LAB_RESULTS_FOR_REVIEW_COLUMNS);
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
			
			int uploadFileNameCt = ((Number) jsonRows.one(HIV_SURVEY_FILE_COUNT, values("CT"), uploadFileName).get("CT")).intValue();
			
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
				String[] UPLOAD_CLEP_HIV_SURVEY_COLUMN_NAMES = uploadProperties.clepHivSurveyColumnNames().split(",");
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
		
		List<JSONObject> filenameObjlist = jsonRows.list(UPLOADED_HIV_SURVEY_FILES, UPLOADED_HIV_SURVEY_FILES_COLUMNS);
		
		for (JSONObject jsonObj : filenameObjlist) {
			String filenameDetail = jsonObj.get("CLEP_FILE_NAME") + ",  " + jsonObj.get("FILE_UPLOAD_BY") + ",  " + jsonObj.get("FILE_UPLOAD_DATE");
			filenameList.add(filenameDetail);
		}
		
		return filenameList;
	}

	public List<JSONObject> getUploadedHIVSurveyData(String uploadedFilename, BindingResult bindingResult,	UserObject userObject) {
		 String[] array = uploadedFilename.split(",");
		 uploadedFilename = array[0];
		 
		if ("All".equals(uploadedFilename)) {
			return jsonRows.list(UPLOADED_HIV_SURVEYS, UPLOADED_HIV_SURVEYS_COLUMNS);
		}
		return jsonRows.list(UPLOADED_HIV_SURVEYS + " and CLEP_FILE_NAME = ?1", UPLOADED_HIV_SURVEYS_COLUMNS, uploadedFilename);
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
		
		return jsonRows.list(LAB_RECRUITMENT_FOR_FOLLOWUP, LAB_RECRUITMENT_FOR_FOLLOWUP_COLUMNS);
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
		// plain maps, as the former resultType="java.util.Map" (a row of nulls only stays a null element)
		return jsonRows.list(RECRUIT_ASSESS_OUTCOMES, values("RECRUIT_ASSESS_OUTCOME", "RECRUIT_ASSESS_OUTCOME_DESC")).stream()
				.map(row -> row == null ? null : (Map<String, String>) new HashMap<String, String>(row)).collect(Collectors.toCollection(ArrayList::new));
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
