package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.EPRFOOChDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EPRFOOCDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.EPRFFollowUpDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.FileByConclusionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * {@inheritDoc}
 * 
 * @author ska03
 */
@Service
public class EPRFOOCDocumentService {

	
	@Autowired
	EPRFOOChDocumentDAO ePRFOOCDocumentDAO;
	
	@Autowired
	DocumentDAO documentDAO;
	
	@Autowired
	CommonExceptionHandler commonExceptionHandler;

	
	@Autowired
	SendEmail sendEmail;
	
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;
	
	private static Logger log = LoggerFactory.getLogger(EPRFOOCDocumentService.class);

	
	
	public int writeRawePRFOOCDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId) {
		docVO.put("CURRENT_PROCESS_STATUS", 5);
		
		
		docVO.put("FNAME", CommonUtil.NameValidation((String)docVO.get("FNAME")));
		docVO.put("MNAME", CommonUtil.NameValidation((String)docVO.get("MNAME")));
		docVO.put("LNAME", CommonUtil.NameValidation((String)docVO.get("LNAME")));
		
		docVO.put("DIN", CommonUtil.dinValidation((String)docVO.get("DIN")));
		docVO.put("PERSON_ADDRESS", CommonUtil.addressValidation((String)docVO.get("ADDRESS")));
		docVO.put("NYSID", CommonUtil.nysidValidation((String)docVO.get("NYSID")));
		
		docVO.put("SSN", CommonUtil.formatSSNValue((String)docVO.get("SSN")));
		docVO.put("BIRTH_SEX", LabCommonUtils.checkBirthSexData((String)docVO.get("BIRTH_SEX")));
		docVO.put("CURRENT_GENDER", LabCommonUtils.checkCurrentGenderData((String)docVO.get("GENDER")));
		docVO.put("ETHNICITY", LabCommonUtils.checkEthnicityData((String)docVO.get("ETHNICCD")));
		docVO.put("MED_RECORD_NUMBER", CommonUtil.normalizeString((String)docVO.get("MEDRECNO")));
		docVO.put("MEDICAID_NUMBER", CommonUtil.checkMedicaidNumberFormat((String)docVO.get("CIN")));
		
		docVO.put("PERSON_CITY", (String)docVO.get("CITY"));
		docVO.put("PERSON_STATE", (String)docVO.get("STATE"));
		docVO.put("PERSON_ZIP", (String)docVO.get("ZIPCODE"));
		docVO.put("PERSON_PHONE", (String)docVO.get("TELEPHONE"));
		
		String RACE_CD = (String)docVO.get("RACE_CD");
		
		if(CommonUtil.isNotNullAndEmpty(RACE_CD)){
			
			String[] raceCodes = RACE_CD.split("\\|");
			
			
			for (String raceCode : raceCodes) {
				

				if("01".equals(raceCode.trim())){
					docVO.put("RACE_WHITE", 1);
				}else if("02".equals(raceCode.trim())){
					docVO.put("RACE_BLACK", 1);
				}else if("03".equals(raceCode.trim())){
					docVO.put("RACE_ASIAN", 1);
				}else if("04".equals(raceCode.trim())){
					docVO.put("RACE_PACIFIC", 1);
				}else if("05".equals(raceCode.trim())){
					docVO.put("RACE_INDIAN", 1);
				}else if("06".equals(raceCode.trim())){
					docVO.put("RACE_OTHER", 1);
				}else if("09".equals(raceCode.trim())){
					docVO.put("RACE_UNKNOWN", 1);
				}
			
			}
			
		}
		
		return ePRFOOCDocumentDAO.writeStdDocData(docVO);
	}
	

	public EPRFOOCDocVO getEPRFOOCDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id){
		EPRFOOCDocVO stdEPRFOOCDocVO = null;
		
		boolean errorFlag = false;
		
		if(log.isDebugEnabled()){
			log.debug(" fetching for EPRF document : {}", docId);
		}
        try {
        	stdEPRFOOCDocVO = ePRFOOCDocumentDAO.getEPRFOOCDoc(docId);
        } catch (DataAccessException e) {
        	errorFlag = true;
            log.error("{} : Failed to load the EPRF OOC Document viewer {}",docId,e.getMessage());
        } catch (Exception e) {
        	errorFlag = true;
        	log.error("{} : Failed to load the EPRF OOC Document viewer {}",docId,e.getMessage());
        } 
        
        if(errorFlag){
        	throw new DataRetrievalFailureException("Failed to load the EPRF OOC Document viewer "+docId);
        }

        return stdEPRFOOCDocVO;

	}
	
	public List<JSONObject> getOOCDocumentFileNames(String fileType, int processStatus,
			BindingResult bindingResult, UserObject userObject){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("fileType", fileType);
		inputParameterMap.put("processStatus", processStatus);
		
		
		if(processStatus == 16){
			inputParameterMap.put("tableName", LabCommonUtils.getRawTableName("EPRF_OOC"));
		}else if(processStatus == 17){
			inputParameterMap.put("tableName", LabCommonUtils.getStdTableName("EPRF_OOC"));
		}
		
		return ePRFOOCDocumentDAO.getOOCDocumentFileNames(inputParameterMap);
	}
	
	public PersonViewVO getStdEPRFOOCDocumentForClericalReview(int documentId){
		
		PersonViewVO EPRFOOCDocVO = ePRFOOCDocumentDAO.getStdEPRFOOCDocumentForClericalReview(documentId);
		
		return EPRFOOCDocVO;
	}
	
	public int writeRawePRFOOCDocData(Map<Object, Object> docVO,
			BigDecimal documentId){
		return documentTransactionHelper.writeRawePRFOOCDocData(docVO, documentId);
	}
	
	public void updateDocumentTypeForEPRFOOCDocument(String fileType,
			BigDecimal documentId){
		
		String document_type = null;
		
		if("O".equals(fileType)){
			document_type = "PROVIDER_OOC";
		}else if("D".equals(fileType)){
			document_type = "PROVIDER_DNLTC";
		}else if("P".equals(fileType)){
			document_type = "PROVIDER_SNP";
		}else{
			throw new DataRetrievalFailureException("Invalid File type for document : "+documentId);
		}
		
		Map<Object, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("testType", document_type);
		
		inputParameterMap.put("documentID", documentId);
		
		documentDAO.writeDocumentTestType(inputParameterMap);
		
		
	}
	
	
	public List<Map<String, Object>> getOOCDocumentsToReview(
			String documentFileName, String documentFileType,
			BindingResult bindingResult, UserObject userObject){
		
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("documentFileName", documentFileName);
		inputParameterMap.put("documentFileType", documentFileType);
		
		
		return ePRFOOCDocumentDAO.getOOCDocumentsToReview(inputParameterMap);
		
	}
	
	@Transactional
	public void updateOOCDocumentReviewStatus(String documentFileName,
			String documentFileType, int processStatus, String facilityCategory,
			BindingResult bindingResult, UserObject userObject){
		
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		
		inputParameterMap.put("documentFileType", documentFileType);
		
		inputParameterMap.put("facilityCategory", facilityCategory);
		
		inputParameterMap.put("processStatus", processStatus);
		inputParameterMap.put("workerId", userObject.getWorkerId());
		
		
		List<String> fileNames = null;
		
		if("ALL".equals(documentFileName)){
			fileNames = ePRFOOCDocumentDAO.getAllFileNamesByFileType(documentFileType);
		}else{
			fileNames = new ArrayList<String>();
			
			fileNames.add(documentFileName);
		}
		
		
		inputParameterMap.put("documentFileNames", fileNames);
		
		
		int resultCount = ePRFOOCDocumentDAO.updateOOCDocumentReviewStatus(inputParameterMap);
		
		if(resultCount > 0){
			
			int resultCount_2 = ePRFOOCDocumentDAO.updateOOCFacilityCategory(inputParameterMap);
			
			if(resultCount_2 == 0){
				
				
				log.error("There is no entry in table (EPRF_PROV_PORTAL_FILE_UPLOAD) for the File name : {}",documentFileName);
				
							
				throw new DataRetrievalFailureException("Failed to change the status of the File "+documentFileName);
			}
			
		}
		
		
		if(resultCount > 0){
			
			if(processStatus == 1){
				
				if("ALL".equals(documentFileName)){
					commonExceptionHandler.inspectDBTransacionResult(0, "The file(s) has been accepted. These file(s) will continue to the Quality Stage matching process<br/><br/>Total Rows Submitted: "+resultCount, bindingResult);
				}else{
					commonExceptionHandler.inspectDBTransacionResult(0, "The file "+documentFileName+" has been accepted. The file will continue to the Quality Stage matching process<br/><br/>Total Rows Submitted: "+resultCount, bindingResult);
				}
				
				
				
			}else{
				
				if("ALL".equals(documentFileName)){
					commonExceptionHandler.inspectDBTransacionResult(0, "The file(s) has been rejected. These file(s) has been flagged as deleted.<br/><br/>Total Rows Rejected: "+resultCount, bindingResult);
				}else{
				
					commonExceptionHandler.inspectDBTransacionResult(0, "The file "+documentFileName+" has been rejected. The file has been flagged as deleted.<br/><br/>Total Rows Rejected: "+resultCount, bindingResult);
				}
			}
			
		}else{
			
			if("ALL".equals(documentFileName)){
				commonExceptionHandler.inspectDBTransacionResult(0, "The file(s) were already reviewed by other user.", bindingResult);
			}else{
				commonExceptionHandler.inspectDBTransacionResult(-1, "The file "+documentFileName+" was already reviewed by other user.", bindingResult);
			}
			
		}
		
		
	}
	
	public List<Map<String, String>> getSNPFileNames(){
		
		return ePRFOOCDocumentDAO.getSNPFileNames();
		
	}
	
	public List<Map<String, String>> getOOCDNLTCFileNames(String origin){
		
		Map<String, String> inputParameter = new HashMap<>();
		
		inputParameter.put("origin", origin);
		
		return ePRFOOCDocumentDAO.getOOCDNLTCFileNames(inputParameter);
		
	}
	
	public List<Map<String, Object>> loadSNPData(String documentFileName,
			BindingResult bindingResult, UserObject userObject){
		
		
		return ePRFOOCDocumentDAO.loadSNPData(documentFileName);
	}
	
	public List<Integer> getOOCDNLTCDocumentsFromMatchReview(String documentFileName){
		return ePRFOOCDocumentDAO.getOOCDNLTCDocumentsFromMatchReview(documentFileName);
	}
	
	
	public EPRFFollowUpDocVO getEprfDocData(Integer documentId){
		return ePRFOOCDocumentDAO.getEprfDocData(documentId);
	}
	
//	@Override
	
	public EPRFFollowUpDocVO getFollowUpData(String documentFileName){
		return ePRFOOCDocumentDAO.getFollowUpData(documentFileName);
	}


	public int getDocumentsNeedToFollowUpCount(String documentFileName) {
		// TODO Auto-generated method stub
		return ePRFOOCDocumentDAO.getDocumentsNeedToFollowUpCount(documentFileName);
	}
	
	public List<Map<String, String>> getInternalConclusionList(){
		return ePRFOOCDocumentDAO.getInternalConclusionList();
	}

	public List<Map<String, String>> getCareStatusList(){
		return ePRFOOCDocumentDAO.getCareStatusList();
	}
	
	public void updateEprfFollowUpData(EPRFFollowUpDocVO ePRFFollowUpDocVO,
			BindingResult bindingResult, UserObject userObject){
		
		boolean errorFlag = false;
		
		String transactionMessage = null;
		
		String action = ePRFFollowUpDocVO.getACTION();
		
	    try {
	    	
	    	
        	int resultCount = ePRFOOCDocumentDAO.updateEprfFollowUpData(ePRFFollowUpDocVO);
        	
        	
        	if(resultCount == 0){
        		errorFlag = true;
        		transactionMessage = "This Document was already completed by other user.";
        	}else if("complet".equals(action)){
        		resultCount = getDocumentsNeedToFollowUpCount(ePRFFollowUpDocVO.getFILENAME());
        		
        		if(resultCount == 0){
        			
        			ePRFOOCDocumentDAO.updateFileFollowUpCompleteDate(ePRFFollowUpDocVO.getFILENAME());
        			
        		}
        	}
        } catch (DataAccessException e) {
        	errorFlag = true;
        	
        	transactionMessage = "Data did not save successfully.  Please contact the helpline.";;
        	
            log.error(ePRFFollowUpDocVO.getDOCUMENT_ID()+"    Action : "+action+"  "+transactionMessage);
            log.error(e.getMessage());
        } catch (Exception e) {
        	errorFlag = true;

        	transactionMessage = "Data did not save successfully.  Please contact the helpline.";;
        	
        	log.error(ePRFFollowUpDocVO.getDOCUMENT_ID()+"    Action : "+action+"  "+transactionMessage);
            log.error(e.getMessage());
        } 
        
        if(errorFlag){
        	commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
        }else{
        	commonExceptionHandler.inspectDBTransacionResult(0, "Data saved successfully.", bindingResult);
        }
		
	}
	
	public void updateEprfFollowUpFileData(EPRFFollowUpDocVO ePRFFollowUpDocVO,
			BindingResult bindingResult, UserObject userObject){
		boolean errorFlag = false;
		
		
		try {
        	int resultCount = ePRFOOCDocumentDAO.updateEprfFollowUpFileData(ePRFFollowUpDocVO);
        	
        	if(resultCount == 0){
        		errorFlag = true;
        		
        		log.error("Result Count is 0 while updating followup information for the File  {}",ePRFFollowUpDocVO.getFILENAME());
        	}
        } catch (Exception e) {
        	errorFlag = true;
        	
            log.error("{}   Failed to update the follow up information", ePRFFollowUpDocVO.getFILENAME());
            
            log.error(e.getMessage());
        } 
        
        if(errorFlag){
        	commonExceptionHandler.inspectDBTransacionResult(-1, "Data did not save successfully.  Please contact the helpline.", bindingResult);
        }else{
        	
        	commonExceptionHandler.inspectDBTransacionResult(0, "Data saved successfully.", bindingResult);
        }
	}
	
	public List<FileByConclusionVO> getPatientCareStatusInformation(
			String documentFileName){
		return ePRFOOCDocumentDAO.getPatientCareStatusInformation(documentFileName);
	}
	
	@Transactional
	public void sendEmailToBureauDirector(String message,
			String documentFileName, BindingResult bindingResult,
			UserObject userObject) throws MessagingException{
		
		
			
			

			
			int resultCount = ePRFOOCDocumentDAO.updateBureauDirectorEmailDate(documentFileName);
			
			
			if(resultCount > 0){
				
				String subject = EnvironmentProperties.envProperties.getProperty("EMAIL_BUREAU_DIRECTOR_SUBJECT");
				
				//String address_to = EnvironmentProperties.envProperties.getProperty("EMAIL_BUREAU_DIRECTOR_TO");
				
				//String address_cc = EnvironmentProperties.envProperties.getProperty("EMAIL_BUREAU_DIRECTOR_CC");
				
				
				//String[] recipientEmailAddresses = address_to.split(",");
				
			
				//sendEmail.sendSSLMessage(recipientEmailAddresses, subject, message,
				//			userObject.getEmailAddress(), address_cc.split(","), CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
				sendEmail.processEmail(null, subject, message, "EPRF_OOC_SUMMARY", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			}else{
				
				log.error("Failed to update the Email Date in the Database : {}",documentFileName);;
				
				throw new DataRetrievalFailureException("Email unsuccessful, please contact the helpline. ");
			}
				
	}


	public Map<String, Integer> getOOCDocumentPatientInformation(
			String documentFileName) {
		return ePRFOOCDocumentDAO.getOOCDocumentPatientInformation(documentFileName);
	}
	
	public List<EPRFFollowUpDocVO> getCompleteEprfDocDataWithHivInfo(
			String documentFileName){
		
		try{
			
			return ePRFOOCDocumentDAO.getCompleteEprfDocDataWithHivInfo(documentFileName);
			
		} catch (Exception exception) {
			
			log.error(exception.getMessage());
			
			log.error("unable to get the complete doc data for the File : {}",documentFileName);
		}
		
		return null;
		
	}
	
	public Map<String, Integer> getOOCDNLTCReviewStatsByFileName(
			String documentFileName){
		return ePRFOOCDocumentDAO.getOOCDNLTCReviewStatsByFileName(documentFileName);
	}
}
