package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.EPRFFollowUpDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.FileByConclusionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AhiAlertTrackingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ADAPDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.AHIDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CancerDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ClericalReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.EPRFOOCDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NonCaseDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

@Service
public class DocumentReviewTransactionHelper {
	
	private static Logger log = LoggerFactory.getLogger(DocumentReviewTransactionHelper.class);
	
	@Autowired
	EPRFOOCDocumentService ePRFOOCDocumentService;
	

	@Autowired
	RecordSearchReviewService recordSearchReviewService;
	
	@Autowired 	
	DocumentsReviewService documentsReviewService; 		
	
	@Autowired 
	AHIDocumentService ahiDocumentService;
	
	@Autowired 	
	DocumentTransactionHelper documentTransactionHelper;
	
	@Autowired
	CommonExceptionHandler commonExceptionHandler;
	
	
	@Autowired
	ADAPDocumentService aDAPDocumentService;

	@Autowired
	ClericalReviewService clericalReviewService;
	
	@Autowired
	ClusterPartnerReviewService clusterPartnerReviewService;
	
	@Autowired
	NBSReviewService nBSReviewService;
	
	@Autowired
	CancerDocumentService cancerDocumentService;
	
	@Autowired 
	NonCaseDocumentService nonCaseDocumentService;
	@Autowired private PersionService personService;
	@Autowired
	private AssignmentService assignmentService;
	
	@Transactional
	public String reviewAndUpdateAndFlagNonAsCaseTheStatusOfClosedAssignment(String colsureCategory, String stateno,
			int assignmentId, String status, BindingResult bindingResult, UserObject userObject) {

		String nonCasetransactionMessage = null;
		Map<Object, Object> output = null;

		reviewAndUpdateTheStatusOfClosedAssignment(assignmentId, status, bindingResult, userObject);

		// check whether stateno is flagged as non case
		int resultCount = nonCaseDocumentService.checkForActiveNonCaseStateno(stateno);

		if (resultCount > 0) {
			nonCasetransactionMessage = ". This Stateno is already flagged as Non-case.";
		} else {
			// getting reason code and non case flag by passing activity type code for non
			// case reasons
			output = nonCaseDocumentService.getReasonCodeForNonCase(colsureCategory, bindingResult, userObject);

			if (output != null && output.get("NON_CASE_FLAG").equals("NON_CASE")
					&& CommonUtil.isNotNullAndEmpty(output.get("REASON_CODE").toString())) {

				String reason = output.get("REASON_CODE").toString();

				FlaggedStatenoVO flaggedStatenoVO = new FlaggedStatenoVO();

				flaggedStatenoVO.setStateno1(stateno);
				flaggedStatenoVO.setRequestComments("Non-case closure review");
				flaggedStatenoVO.setNonCaseFlag("Y");

				// calling to flag as non-case for closed assignment
				nonCasetransactionMessage = closedAssignmentMarkingAsNonCase(flaggedStatenoVO, reason, bindingResult,
						userObject);

			}

		}
		return nonCasetransactionMessage;

	}
	
	


    /**
     * 
     * @param flaggedStatenoVO
     * @param reason
     * @param bindingResult
     * @param userObject
     * @return transactionMessage (transaction message - getting from flagAsNonCase method)
     */
    		
	private String closedAssignmentMarkingAsNonCase(FlaggedStatenoVO flaggedStatenoVO, String reason,
			BindingResult bindingResult, UserObject userObject) {
		String transactionMessage = null;
		try {
			transactionMessage = nonCaseDocumentService.flagAsNonCase(flaggedStatenoVO, reason, bindingResult,
					userObject);
		} catch (Exception e) {
			log.error("Unable to flag the closed Assignment as Non-Case for reason code {}", reason);
			log.error(e.getMessage());
		}

		return transactionMessage;

	}



	public void reviewAndUpdateTheStatusOfClosedAssignment(int assignmentId,
			String status, BindingResult bindingResult, UserObject userObject) {

		
		int resultCount  = documentsReviewService.updateTheReviewStatusInSurvAssignment(assignmentId, userObject);
		
		if(resultCount == 0 ){
			throw new DataRetrievalFailureException("Failed to update the Review status in Surveillance Assignment");
		}else{
			
			String comments = null;
			String activityType = null;
			
			if("Approve".equals(status)){
				comments = "Review Approved";
				activityType = "AR08";
			}else{
				comments = "Review Agree";
				activityType = "AR09";
			}
			
			
			resultCount = documentsReviewService.addAssignmentActivity(assignmentId, comments, activityType, userObject);
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to add the Assignment reviewed entry in Activity table.");
			}
		}
		
		
	}

	@Transactional
	public void agreeAndDeleteDocumentForClosedAssignment(int assignmentId,
			int documentId, String documentType, BindingResult bindingResult,
			UserObject userObject) {

		reviewAndUpdateTheStatusOfClosedAssignment(assignmentId, "Agree", bindingResult, userObject);
		
		
		documentTransactionHelper.deleteStdDocumentExistence(documentId, documentType, CommonConstants.DOCUMENT_DELETED_PROCESS_STATUS, "DISASSOC_DEL", userObject);
		
		
	}
	
	public void updateOOCDocumentReviewStatus(String documentFileName,
			String documentFileType, int processStatus, String facilityCategory,
			BindingResult bindingResult, UserObject userObject){
		
		try{
			
			ePRFOOCDocumentService.updateOOCDocumentReviewStatus(documentFileName, documentFileType, processStatus, facilityCategory, bindingResult, userObject);
			
		}catch(DataAccessException exception){
			
			
			
			log.error(exception.getMessage());
			
			
			if(bindingResult.getAllErrors() == null || bindingResult.getAllErrors().size() == 0 ){
				
				
				if(processStatus == 1){
					log.error("Failed to accept the file : {}",documentFileName);
					commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to accept the file "+documentFileName+". Please contact helpline", bindingResult);
				}else{
					log.error("Failed to reject the file  : {}",documentFileName);
					commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to reject the file "+documentFileName+". Please contact helpline", bindingResult);
				}	

			}
		}
		
		
		
	}


	public String getOOCDNLTCReviewData(Map<String, Object> map,
			String documentFileName, String origin, BindingResult bindingResult, HttpServletRequest request) {
		
		

		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		
		List<Integer> reviewDocumentIds = null;
		
		Integer documentId = null;
		
		Integer reviewDocumentIdIndex = null;
		
		if(CommonUtil.isNotNullAndEmpty(origin) && "skip".equals(origin)){
			 reviewDocumentIds = (List<Integer>)session.getAttribute("reviewDocumentIds");
			 
			 reviewDocumentIdIndex = (Integer)session.getAttribute("reviewDocumentIdIndex");
			 
			 if(reviewDocumentIds != null && reviewDocumentIds.size() > 0 && reviewDocumentIdIndex !=null){
				 
				 ++reviewDocumentIdIndex;
				 
				 if(reviewDocumentIds.size() <= reviewDocumentIdIndex){
					 
					 reviewDocumentIdIndex = 0;
					 
				 }
				 
				 
				documentId =  reviewDocumentIds.get(reviewDocumentIdIndex);
				 
				
				 
			 }
			
		}
		
		if(documentId == null){
			reviewDocumentIds = ePRFOOCDocumentService.getOOCDNLTCDocumentsFromMatchReview(documentFileName);
			
			reviewDocumentIdIndex = 0;
			
			if(reviewDocumentIds !=null && reviewDocumentIds.size() > 0){
				documentId =  reviewDocumentIds.get(reviewDocumentIdIndex);
			}
			
		}
		
		
		session.setAttribute("reviewDocumentIds", reviewDocumentIds);
		
		session.setAttribute("reviewDocumentIdIndex", reviewDocumentIdIndex);
		
		
		if(reviewDocumentIds != null && reviewDocumentIds.size() > 0){
			
			
			EPRFFollowUpDocVO eprfDocData = ePRFOOCDocumentService.getEprfDocData(documentId);
			
			//Map<String, String> eharsData = ePRFOOCDocumentService.getEprfEharsData(documentId);
			
			
//			if(eprfDocData == null || eharsData == null){
			if(eprfDocData == null){
				
				log.error("Failed to get the Data for the Document {}",documentId);
				
				map.put("TRANSACTION_MESSAGE", "Data retrieval unsuccessful. Please contact helpline.");
				
				return "review/statusMessage";
			}
			
			map.put("ePRFFollowUpDocVO", eprfDocData);
			
			
			
			
			
			Map<String, Integer> reviewStatus = ePRFOOCDocumentService.getOOCDNLTCReviewStatsByFileName(documentFileName);
			
			
			map.put("reviewStatus", reviewStatus);
			
			
			//map.put("eharsData", eharsData);
			
			return "review/eprf/updateEprfFollowUpDocData";
			
		}else{
			
			
			int count = ePRFOOCDocumentService.getDocumentsNeedToFollowUpCount(documentFileName);
			
			if(count == 0){
			
				EPRFFollowUpDocVO fileFollowUpData =  ePRFOOCDocumentService.getFollowUpData(documentFileName);
				
				if(fileFollowUpData == null){
					
					log.error("Failed to get the Data for the followup {}",documentId);
					
					map.put("TRANSACTION_MESSAGE", "Data retrieval unsuccessful. Please contact helpline.");
					
					return "review/statusMessage";
				}
				
				
				
				map.put("ePRFFollowUpDocVO", fileFollowUpData);
				
				if(fileFollowUpData.getFILE_CLIENT_FEEDBACK_DATE() != null){
					
					map.put("TRANSACTION_MESSAGE", "Feedback was already provided on this file.<br/><br/>  <span><a class='actionButtons a_demo_search a_demo_save'  onClick='generateSummarySpreadsheet()' style='color: #FFFFFF; margin-left: 5px;'> Generate Summary Spreadsheet</a></span>");
					
					return "review/statusMessage";
					
				}else if(fileFollowUpData.getFILE_EMAIL_DIRECTOR_DATE() == null){
					
					
					if(CommonUtil.isNullOrEmpty(fileFollowUpData.getFILE_CATEGORY())){
						
						log.error("file Category is missing for this file : {}",documentFileName);
						
						map.put("TRANSACTION_MESSAGE", "File Category is missing for this file, please contact helpline.");
						
						return "review/statusMessage";
						
					}else if(CommonUtil.isNullOrEmpty(fileFollowUpData.getFACILITY_NAME())){
						log.error("Facility name is missing for few records(s) {}",documentFileName);
						
						map.put("TRANSACTION_MESSAGE", "Facility name is missing for few records(s), please contact helpline.");
						
						return "review/statusMessage";

					}else if(CommonUtil.isNullOrEmpty(fileFollowUpData.getPROV_COUNTY())){
						log.error("Provider County is missing for few records(s) {}",documentFileName);
						
						map.put("TRANSACTION_MESSAGE", "Provider County is missing for few records(s), please contact helpline.");
						
						return "review/statusMessage";
					}
					
					
					String messageContent = null;
					
					if(fileFollowUpData.getFILE_CATEGORY().indexOf("HICAPP") != -1){
						
						messageContent = EnvironmentProperties.envProperties.getProperty("HICAPP_EMAIL_BUREAU_DIRECTOR_MSG_CONTENT");

					}else{
						
						messageContent = EnvironmentProperties.envProperties.getProperty("GENERAL_EMAIL_BUREAU_DIRECTOR_MSG_CONTENT");
					}
					
					
					messageContent = messageContent.replaceAll("_FACILITY_NAME_", fileFollowUpData.getFACILITY_NAME());
					messageContent = messageContent.replaceAll("_PROV_COUNTY_", fileFollowUpData.getPROV_COUNTY());
					
					List<FileByConclusionVO> patientCareStatusInformation = ePRFOOCDocumentService.getPatientCareStatusInformation(documentFileName);
					
					
					int totalPatients = 0;
					
					
					StringBuilder patientsInormation = new StringBuilder();
					
					patientsInormation.append("<ul>");
					
					for (FileByConclusionVO conclusion : patientCareStatusInformation) {
						
						
						//CONCLUSION_DESC   COUNT
						
						patientsInormation.append("<li>");

						totalPatients = totalPatients+conclusion.getCOUNT();
						
						patientsInormation.append(conclusion.getCOUNT());

						if(conclusion.getCOUNT() == 0){
						
							log.error("Care Status is not populated for few Records in the File {}",documentFileName);
							
							map.put("TRANSACTION_MESSAGE", "Care Status is not populated properly, please contact helpline.");
							
							return "review/statusMessage";
						}
						
						if(conclusion.getCOUNT() > 1){
							patientsInormation.append(" patients: ");
						}else{
							patientsInormation.append(" patient: ");
						}
						
						patientsInormation.append(conclusion.getCONCLUSION_DESC());
							
						
						patientsInormation.append("</li>");
						
					}
					
					
					patientsInormation.append("</ul>");
					
					messageContent = messageContent.replaceAll("_TOTAL_PATIENTS_COUNT_", Integer.toString(totalPatients));
					
					messageContent = messageContent.replaceAll("_PATIENT_INFORMATION_", patientsInormation.toString());
					
					messageContent = messageContent.replaceAll("_USER_FULL_NAME_", userObject.getUserFirstName()+" "+userObject.getUserLastName());
					

					map.put("emailMessageContent", messageContent);
					
					map.put("EMAIL_BUREAU_DIRECTOR_HEADER", EnvironmentProperties.envProperties.getProperty("EMAIL_BUREAU_DIRECTOR_HEADER"));
					
					map.put("EMAIL_BUREAU_DIRECTOR_WARNING_MSG", EnvironmentProperties.envProperties.getProperty("EMAIL_BUREAU_DIRECTOR_WARNING_MSG"));
					
				}
				
				return "review/eprf/updateEprfFileFollowUpData";
			}else{
				
				
				log.error("Failed to load the remiaining documents {}",documentFileName);
				
				map.put("TRANSACTION_MESSAGE", "Data retrieval unsuccessful. Please contact helpline.");
				
				return "review/statusMessage";
				
			}
			
		}
		
	}
	
	
	public void sendEmailToBureauDirector(String message,
			String documentFileName, BindingResult bindingResult,
			UserObject userObject){
		
		
		boolean errorFlag = false;
		
		String transactionMessage = null;
		
	
		
			try{
				
				
				ePRFOOCDocumentService.sendEmailToBureauDirector(message, documentFileName, bindingResult, userObject);
			
			
        	
        } catch (Exception exception) {
        	
        		transactionMessage = "Email unsuccessful, please contact the helpline.";
        		
        		log.error("Sending Email to Bureau Director failed  {}",exception.getMessage());
        	
        	
        		log.error("File name  : {}",documentFileName);
        	
        		commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
        	
        } 
		
		
		if(errorFlag){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Email sent successfully.", bindingResult);
		}
		
		
	}
	
	
	public List<Map<String, String>> generateCDCReturnData(String statenoList, String cdcFileName,
			BindingResult bindingResult, UserObject userObject){
			
			List<Map<String, String>> cdcReturnData = null;
			
			
			Integer statusCode = 0;
			
			String transactionMessage = null;
			
			try{
				
				
				if("ALL".equals(statenoList)){
					
					cdcReturnData = recordSearchReviewService.generateCDCReturnDataForAllTheRecords(cdcFileName, bindingResult, userObject);
					
				}else{
				
					String[] statenoListArray = statenoList.split(",");
					
					
					cdcReturnData = recordSearchReviewService.generateCDCReturnData(statenoListArray, cdcFileName, bindingResult, userObject);
					
				}
				
				
				
				
				if(cdcReturnData == null || cdcReturnData.size() == 0){
					
					statusCode = -1;
					
					transactionMessage = "There is no data to generate from the selected STATENO(S)"; 
							
				}else{
					transactionMessage = "Successfully generated CDC Return file.";
				}
			
			
			}catch(DataAccessException dataAccessException){
				
				statusCode = -1;
				
				transactionMessage = dataAccessException.getMessage();
				
				log.error(transactionMessage+"     "+statenoList);
				
			}catch(Exception e){
				
				statusCode = -1;
				
				transactionMessage = "Failed to generate the CDC Return file for the selected STATENO(S)";
				
				log.error(transactionMessage+"     "+statenoList);
				
				log.error(e.getMessage());
			}

			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
			
			
			return cdcReturnData;

	}
	
	public void uploadCDCFileData(String inputFileName,
			MultipartFile uploadedFile, BindingResult bindingResult,
			UserObject userObject){
		
		boolean isError = false;
		
		String transactionMessage = null;
		
		try{
			
			recordSearchReviewService.uploadCDCFileData(inputFileName, uploadedFile, bindingResult, userObject);
			
			
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = dataAccessException.getMessage();
			
			log.error("Failed to read the file.  {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "Failed while uploading the data, please contact helpline";
			
			log.error("Failed to read the file.  {}",exception.getMessage());
		}
	
		if(isError && (bindingResult.getAllErrors() == null || bindingResult.getAllErrors().size() == 0 )){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	
	}
	
	
	@Transactional
	public void changeAhiFlagStatusForAssignment(String stateNo, int assignmentId, boolean ahiFlagStatus, String comments, String documentType, 
			String ahiDocIdForAssignment, int ahiAlertSeqId, UserObject userObject, BindingResult bindingResult) {
		
		if (!ahiFlagStatus) {
			AhiAlertTrackingVO ahiAlertTrackingVO = new AhiAlertTrackingVO();
			ahiAlertTrackingVO.setAhiAlertSeqId(ahiAlertSeqId);
			ahiDocumentService.updateAhiAlertStatus(ahiAlertTrackingVO, userObject, bindingResult);
		}
		
		
		
		int resultCount = ahiDocumentService.changeAhiFlagStatusForAssignment(assignmentId, ahiFlagStatus, documentType, userObject, bindingResult);
		String ahiFlagSubject="";
		String ahiFlagContent="";
		
		if(resultCount > 0){
			
			if(!ahiFlagStatus){
				documentsReviewService.addAssignmentActivity(assignmentId, comments, "AR12", userObject);
			}else {
				documentsReviewService.addAssignmentActivity(assignmentId, null, "AR11", userObject);
			}
		
		
		if (ahiFlagStatus) {
			ahiFlagSubject = "Flagged as Potential AHI";
			ahiFlagContent = stateNo + " flagged by " + userObject.getUserFirstName() +  " " + userObject.getUserLastName() + " " + new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date()); 
		} else {
			ahiFlagSubject = "Potential AHI Un-flagged";
			ahiFlagContent = stateNo + " un-flagged by " + userObject.getUserFirstName() +  " " + userObject.getUserLastName() + " " + new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
			
			if (CommonUtil.isNotNullAndEmpty(ahiDocIdForAssignment) && ahiDocIdForAssignment.matches("[0-9]+") && Integer.parseInt(ahiDocIdForAssignment) != 0) {
				
			personService.deleteDocument(Integer.parseInt(ahiDocIdForAssignment), "AHI", 31, "AHI flag changed from Y to N", "Removed AHI flag for assignment", stateNo, "Y", bindingResult, userObject);
			
			assignmentService.updateAssignmentWithAHIDocument(assignmentId, null, bindingResult, userObject);
			
			}
		}

		ahiDocumentService.sendEmailNotificationToAHIUsers(userObject, ahiFlagSubject, ahiFlagContent);
	}
		
		
	}
	
	public List<Map<String, String>> generateADAPNYCFile(String fileName, String nycShiStatus,
			BindingResult bindingResult, UserObject userObject) {
			
		List<Map<String, String>> adpaNYCData = null;
		
		
		Integer statusCode = 0;
		
		String transactionMessage = null;
		
		try{
			
			adpaNYCData = aDAPDocumentService.generateADAPNYCFile(fileName, nycShiStatus, bindingResult, userObject);
			
			if(adpaNYCData == null || adpaNYCData.size() == 0){
				
				statusCode = -1;
				
				transactionMessage = "There is no data to generate for the Selected Criteria"; 
						
			}else{
				transactionMessage = "Successfully generated NYC File.";
			}
		
		
		}catch(DataAccessException dataAccessException){
			
			statusCode = -1;
			
			transactionMessage = dataAccessException.getMessage();
			
			log.error(transactionMessage+"     File Name : "+fileName);
			
		}catch(Exception e){
			
			statusCode = -1;
			
			transactionMessage = "Failed to generate the NYC File.";
			
			log.error(transactionMessage+"     File Name :  "+fileName);
			
			log.error(e.getMessage());
		}

		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		
		return adpaNYCData;
		
	}

	public void uploadNYCReturnFileData(MultipartFile uploadedFile, 
			BindingResult bindingResult, UserObject userObject) {
		
		boolean isError = false;
		
		String transactionMessage = null;
		
		try{
			
			aDAPDocumentService.uploadNYCReturnFileData(uploadedFile, bindingResult, userObject);
			
			
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed while uploading the data, please contact helpline";
			
			log.error("Failed to read the file.  {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "Failed while uploading the data, please contact helpline";
			
			log.error("Failed to read the file.  {}",exception.getMessage());
		}
	
		if(isError && (bindingResult.getAllErrors() == null || bindingResult.getAllErrors().size() == 0 )){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	
		
	}
	
	public void rejectCDCFile(String cdcFileName,
			BindingResult bindingResult, UserObject userObject) {
		
		boolean isError = false;

		int resultCount = recordSearchReviewService.rejectCDCFileFromSentTable(cdcFileName, userObject);
		
		if(resultCount == 0){
			
			log.error("There is no Pending records in Sent Table for the CDC File {}",cdcFileName);
						
			commonExceptionHandler.inspectDBTransacionResult(-1, "There is no Pending records for the CDC File "+cdcFileName, bindingResult);
			
			isError = true;
		}else{
			
			resultCount = recordSearchReviewService.rejectCDCFileFromFlaggingTable(cdcFileName, userObject);
			
			if(resultCount == 0){
				
				log.error("There is no Pending records in Sent Table for the CDC File {}",cdcFileName);
							
				commonExceptionHandler.inspectDBTransacionResult(-1, "There is no Pending records for the CDC File "+cdcFileName, bindingResult);
				
				isError = true;
				
			}
		}
		
		
		if(!isError){
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully rejected the CDC File "+cdcFileName, bindingResult);
		}
	}

	public List<Map<String, String>> generateADAPNYCDiscrepancyFile(
			String fileName, BindingResult bindingResult, UserObject userObject) {
		List<Map<String, String>> adpaNYCData = null;
		
		
		Integer statusCode = 0;
		
		String transactionMessage = null;
		
		try{
			
			adpaNYCData = aDAPDocumentService.generateADAPNYCDiscrepancyFile(fileName, bindingResult, userObject);
			
			if(adpaNYCData == null || adpaNYCData.size() == 0){
				
				statusCode = -1;
				
				transactionMessage = "There is no Discrepancy data to generate."; 
						
			}else{
				transactionMessage = "Successfully generated UCP NYC Discrepancy File.";
			}
		
		
		}catch(DataAccessException dataAccessException){
			
			statusCode = -1;
			
			transactionMessage = dataAccessException.getMessage();
			
			log.error(transactionMessage+"     File Name : "+fileName);
			
		}catch(Exception e){
			
			statusCode = -1;
			
			transactionMessage = "Failed to generate the NYC File.";
			
			log.error(transactionMessage+"     File Name :  "+fileName);
			
			log.error(e.getMessage());
		}

		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		
		return adpaNYCData;
	}

	@Transactional
	public void reviewDatafluxStagedDocuments(long documentId, String stateno, Long clusterId, Long fuzzyClusterId, String match_cd, String documentType, String comments,
			BindingResult bindingResult, UserObject userObject) {
		
		if("NBS".equals(documentType)) {
			nBSReviewService.processNBSDocument(documentId, stateno, clusterId, match_cd, comments, 
					bindingResult, userObject);
		}else if("CANCER".equals(documentType)) {
			cancerDocumentService.processCancerDocument(documentId, stateno, clusterId, match_cd, 
					bindingResult, userObject);
		}else {
			clericalReviewService.updateMatchResultsToDatafluxReviewDocument(documentId, stateno, clusterId, fuzzyClusterId, match_cd, 
					bindingResult, userObject);
	}
		
		
	}
	
	@Transactional
	public void processClusterPartnerDocument(long documentId, String stateno, String match_cd, String reviewStatus, 
			String INS_CLUSTER, BindingResult bindingResult, UserObject userObject) {
		
		
		Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
		
		inOutParameterMap.put("p_document_id", documentId);
		
		inOutParameterMap.put("ACTUAL_MATCH_CD", match_cd);
		 
		 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 17);
		 
		 inOutParameterMap.put("stateno", stateno);
		 
		 inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		 
		 inOutParameterMap.put("p_ins_cluster", INS_CLUSTER);
		 
		 inOutParameterMap.put("p_triggerPsMessage", "N");
		 
		 if(CommonUtil.isNotNullAndEmpty(stateno) && "Y".equals(INS_CLUSTER)) {
			 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_M");
			 
			 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 17);
			 
			 //int existingMatches = clusterPartnerReviewService.checkForExistingMatches(inOutParameterMap);
			 
			 
		 }else if(CommonUtil.isNotNullAndEmpty(stateno) && "N".equals(INS_CLUSTER)) {
			 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_M");
			 
			 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 48);
			 
		 }else if(CommonUtil.isNullOrEmpty(stateno) && "N".equals(INS_CLUSTER)) { 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_N");
			 
			 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 48);
		 }else if(CommonUtil.isNullOrEmpty(stateno) && "Y".equals(INS_CLUSTER)) { 
			 inOutParameterMap.put("UPDATED_MATCH_CD", match_cd+"_N");
			 
			 inOutParameterMap.put("CURRENT_PROCESS_STATUS", 17);
		 }

		 int resultCount =  clusterPartnerReviewService.updateClusterPartnerMatchSummary(inOutParameterMap);
		
		if(resultCount==0){
			throw new DataRetrievalFailureException("Document is already reviewed, please refesh the page");
		}else if(CommonUtil.isNotNullAndEmpty(stateno)) {
			 commonExceptionHandler.inspectDBTransacionResult(0, "Document mapped to STATENO:"+stateno, bindingResult);
		}else if(CommonUtil.isNullOrEmpty(stateno) ) {
			 commonExceptionHandler.inspectDBTransacionResult(0, "Marked document as Not a match", bindingResult);
		}
		
		if("N".equals(INS_CLUSTER)) {
			return;
		}
		
		
		clusterPartnerReviewService.processClusterparterDocumentToClusterTables(inOutParameterMap);
		
		Integer statusCode=(Integer)inOutParameterMap.get("p_code_out");
		
		if(statusCode==0) {
			return;

		}else {
			log.error((String)inOutParameterMap.get("p_msg_out"));
			throw new DataRetrievalFailureException("Failed to update the Cluster partner document status to "+reviewStatus);

		}
		 
		 
		
			 
		
		
		
	}

	
}
