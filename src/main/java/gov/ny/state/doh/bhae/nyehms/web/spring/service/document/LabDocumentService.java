package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.LabProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.LabDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.LabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdOldDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdOldDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.QuartzSchedulerTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class LabDocumentService {

	@Autowired
	private LabProperties labProperties;

	private static Logger log = LoggerFactory.getLogger(LabDocumentService.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();	
	
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;	
	@Autowired
	LabDocumentDAO labDocumentDAO;
	@Autowired
	StdOldDocRepository stdOldDocRepository;
	@Autowired
	CommonDocumentService commonDocumentService;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;	
	@Autowired
	private QuartzSchedulerTransactionHelper quartzSchedulerTransactionHelper;
	
	
	
	
	
	
	
	
	private final String OOS_CLIA = "33DHIVMAIL";


	public StdEcrlsLabDocVO getLabDoc(int docId, String documentType, 
									BindingResult result, 
									UserObject userObject, 
									String sessionID) {
		StdEcrlsLabDocVO stdLabDocVO=null;
		boolean rollbackFlag=false;
		log.debug("{} fetching for docId : {}", nyehmsLog,docId);
		try{
			
			Map<Object, Object> map = new HashMap<>();
			
			if("STD_LAB".equals(documentType)){
				map.put("raw_table_name", "raw_std_eclrs_doc");
				map.put("std_table_name", "std_std_eclrs_doc");	
				
			}else{
				map.put("raw_table_name", "raw_hiv_eclrs_doc");
				map.put("std_table_name", "std_hiv_eclrs_doc");	
				
			}
			map.put("docId", docId);
			
			
			stdLabDocVO=labDocumentDAO.getLabDoc(map);
			
			if(stdLabDocVO != null && CommonUtil.isNotNullAndEmpty(stdLabDocVO.getTEST_RESULT())) {
				
				
				
				
				String loinc = stdLabDocVO.getLOINC();
				String clia = stdLabDocVO.getSENDING_FACILITY_CLIA();
				String rawTestResult = stdLabDocVO.getRAW_OBXOBSERVATIONRESULTTEXT();
				String obxNote = stdLabDocVO.getRAW_OBXNOTE();
				
				if((labProperties.quest().clias().indexOf(clia) != -1 &&
						labProperties.quest().testResults().indexOf(rawTestResult) != -1 &&
							labProperties.quest().loincCodes().indexOf(loinc) != -1)
						|| 
							(labProperties.labcorp().clias().indexOf(clia) != -1 &&
						labProperties.labcorp().testResults().indexOf(rawTestResult) != -1 &&
							labProperties.labcorp().loincCodes().indexOf(loinc) != -1) ) {
					
					
					if(CommonUtil.isNotNullAndEmpty(obxNote) && obxNote.replaceAll("\\s+", " ").toUpperCase().contains(CommonConstants.NOT_DETECTED)) {
						stdLabDocVO.setTEST_RESULT((new StringBuilder(rawTestResult).append(" ").append(CommonConstants.NOT_DETECTED)).toString());
					}else if(CommonUtil.isNotNullAndEmpty(obxNote) && obxNote.replaceAll("\\s+", " ").toUpperCase().contains(CommonConstants.DETECTED)){
						stdLabDocVO.setTEST_RESULT((new StringBuilder(rawTestResult).append(" ").append(CommonConstants.DETECTED)).toString());
					}
					
				}
				
				
					String testResults = stdLabDocVO.getTEST_RESULT();
					
					String formatedTestResults = labProperties.testResultLabels().get(testResults);
					
					if(CommonUtil.isNotNullAndEmpty(formatedTestResults)) {
						
						stdLabDocVO.setTEST_RESULT(formatedTestResults);
						
					}
				
			}
			
		} catch (DataAccessException e) {
			rollbackFlag=true;
			log.error("{}  DOCUMENT_ID : {}   Exception : {}",nyehmsLog,docId,e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			rollbackFlag=true;
			log.error("{}  DOCUMENT_ID : {}   Exception : {}",nyehmsLog,docId,e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(rollbackFlag)
				commonExceptionHandler.populateBindObjTrsnFail(result);
		}		
		
		return stdLabDocVO;
	}

	@Transactional
	public void insertLabDocData(LabDocVO stdLabDocVO, 
								BindingResult result, 
								UserObject userObject, 
								String sessionID) throws UnexpectedRollbackException {
		boolean rollbackFlag=false;
		int newDocId=0;
		
		Map<Object, Object> map = new HashMap<>();
		map.put("p_document_type", "ECLRS");
		map.put("p_new_doc_id", "");	
		map.put("p_parent_doc_id", stdLabDocVO.getDocumentId());		
		log.debug("{} -- nyehmsLog {}",nyehmsLog,stdLabDocVO.getDocumentId());
		try{			
			commonDocumentService.getNewDocId(map);				
			newDocId=(Integer)map.get("p_new_doc_id");
			stdLabDocVO.setDocumentId(newDocId);			
			labDocumentDAO.insertLabDocData(stdLabDocVO);		
		}catch (DataAccessException e) {
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: "+e.toString());	
			commonExceptionHandler.handleException(e, userObject,sessionID);				
		} finally{						
			if(rollbackFlag){					
				commonExceptionHandler.populateBindObjTrsnFail(result);
				log.error(nyehmsLog+" Exception thown and added to Binding Result Object---- ");
			}
		}
	}
	
	public EcrlsLabDocVO getHivLabDoc(int docId, BindingResult dummyBindingResult,
			UserObject userObject, String sessionID){
		
		EcrlsLabDocVO stdLabDocVO=null;
		log.debug("fetching for docId : {}",docId);
		try{
			stdLabDocVO=labDocumentDAO.getHivLabDoc(docId);			
		} catch (DataAccessException e) {
			log.error(nyehmsLog+"  "+e.getMessage());
		} catch (Exception e) {
			log.error(nyehmsLog+"  "+ e.getMessage());
		}finally{						
			if(stdLabDocVO == null){
				stdLabDocVO = new EcrlsLabDocVO();
				commonExceptionHandler.inspectDBTransacionResult(-1, AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName(), dummyBindingResult);
			}else{
				commonExceptionHandler.inspectDBTransacionResult(0, AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName(), dummyBindingResult);
			}
		}		
		
		return stdLabDocVO;
		
		
	}

	
	public void editLabErrorDoc(EcrlsLabDocVO ecrlsLabDocVO, String requestComments,
			BindingResult result, UserObject userObject, String id){
		boolean isError = false; 
		List<String> errors = new ArrayList<>();
		try{
			documentTransactionHelper.editLabErrorDoc(ecrlsLabDocVO, requestComments, result, userObject, errors, id );
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to update the a Error Document.   DOCUMENT_ID : {}",ecrlsLabDocVO.getDOCUMENT_ID());
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to update the a Error Document.   DOCUMENT_ID : {}",ecrlsLabDocVO.getDOCUMENT_ID());
			log.error(exception.getMessage());
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the lab error document, please contact helpline.", result);
		}else if(errors != null && errors.size() > 0 ){
			commonExceptionHandler.inspectDBTransacionResult(-1, errors.toString(), result);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the lab document.", result);
		}
		
	
	}
	
	public void moveRawToStdByStateno(int documentId, String stateno, String requestComments, BindingResult bindingResult, UserObject userObject){
		boolean isError = false; 
		List<String> errors = new ArrayList<>();
		
		try{
			documentTransactionHelper.moveRawToStdByStateno(documentId, stateno , requestComments, errors,  bindingResult, userObject);
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to move the document from Raw to Std table {}",documentId);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to move the document from Raw to Std table {}",documentId);
			log.error(exception.getMessage());
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to add the given stateno to document, please contact helpline.", bindingResult);
		}
	}
	
	public int insertStandardHIVResistanceResult(Number documentId, String  OBXOBSERVATIONRESULTTEXT, Number OBXOBSERVATIONRESULTKEY, 
			java.util.Date collectionDate) {
		
		String obxObservationResultText = OBXOBSERVATIONRESULTTEXT;
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("documentId",
				documentId);
		String[] sequenceArray = obxObservationResultText.split("(?<=\\G.{4000})");
	
		inputOutputParameterMap.put("eclrs_key",
				OBXOBSERVATIONRESULTKEY);
		inputOutputParameterMap.put("collection_date", collectionDate);
		
		
		int resultCount = 0;
		
		int index = 0;
		for (String sequence : sequenceArray) {
			
			inputOutputParameterMap.put("sequence",
					sequence);
			
			int length = sequence.length();
			
			inputOutputParameterMap.put("sequence_length",
					length);
			
			String rasElig = "S";
			
			if(length > 255){
				rasElig = "Y";
			}
			inputOutputParameterMap.put("rasElig",
					rasElig);
			
			inputOutputParameterMap.put("currentIndex", ++index);
			resultCount = labDocumentDAO
					.insertStandardHIVResistanceResult(inputOutputParameterMap);
			
		}
		if (resultCount == 0) {
			throw new DataRetrievalFailureException(
					"Unable insert the HIV standard resistance results  "
							+ documentId);
		}
		return resultCount;
	}
	
	
	public void restageStdDocumentNonMatches(
			List<JSONObject> schedulerStatusObject, Map<String,
			String> schedulerMetaData){
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;

		JSONObject statusObject = new JSONObject();

		try {

			statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Restaging STD Document Non matches fot last 30 days");
			
			String  STD_NON_MATCHES_RESTAGE_QUERY = schedulerMetaData.get("STD_NON_MATCHES_RESTAGE_QUERY");
			
			Map<String, String> inputParameterMap = new HashMap<>();
			
			inputParameterMap.put("sqlQuery", STD_NON_MATCHES_RESTAGE_QUERY);
			
			int resultCount = labDocumentDAO.restageStdDocumentNonMatches(inputParameterMap);
			
			transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
			
			if(resultCount == 0){
				
				transactionMessage = "There is no documents to Re stage for the Quality stage match";
				
			}else{
				
				transactionMessage = resultCount+"  documents staged for Quality stage match";
				
			}
			
			
		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception occured while restaging the STD document non matches" + e.getMessage();
			
			log.error(transactionMessage);
			
		}
		
			statusObject.put(CommonConstants.TRANSACTION_STATUS,
					transactionStatus);

			statusObject.put(CommonConstants.TRANSACTION_MESSAGE,
					transactionMessage);

			schedulerStatusObject.add(statusObject);
		

	}
	
	public List<Map<String, Object>> loadMultiSpotDocuments(
			BindingResult bindingResult, UserObject userObject, String pfi, String loincs){
		
		
		
		
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		inputParameterMap.put("loincCodes", loincs.split(","));
		inputParameterMap.put("pfiList", pfi.split(","));
		
		return labDocumentDAO.getMultiSpotDocuments(inputParameterMap);
	}
	
	
	public void processMultiSpotDocuments(
			BindingResult bindingResult, UserObject userObject,
			int[] multiSpotDocuments, int retainDocumentId, int loincGroupId){
		
		boolean isError = false;
		
		try{
			
			log.error("Grouping Multi spot Documents {}   Retain DocumentId : {} loincGroupId : {}",Arrays.toString(multiSpotDocuments),retainDocumentId,loincGroupId);
			
			Map<Object, Object> inputOutputParameterMap = new HashMap<>();
			
			inputOutputParameterMap.put("documentIds", multiSpotDocuments);
			List<EcrlsLabDocVO> multiStepDocumentsForPFI = labDocumentDAO.getRawDocumentsData(inputOutputParameterMap);
			List<String> errors = new ArrayList<>();
			
			String errorMessage = quartzSchedulerTransactionHelper.processMultispotDocuments(multiStepDocumentsForPFI, errors, retainDocumentId, loincGroupId);
			
			if(StringUtils.isNotBlank(errorMessage)) {
				commonExceptionHandler.inspectDBTransacionResult(-1, errorMessage, bindingResult);
			}else {
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully processed the documents", bindingResult);
			}
			
			
			
			
			
			
			
			
			
		
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to group the documents together. DocumentIds {}",multiSpotDocuments);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to group the documents together. DocumentIds {}",multiSpotDocuments);
			log.error(exception.getMessage());
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to group the process the documents, please contact helpline.", bindingResult);
		}
		
	}
	
	public int updateStatusCodeForHivDocuments(List<Integer> documentIds,
			int statusCode) {
		Map<Object, Object> inputOutputParamters = new HashMap<>();
		inputOutputParamters.put("statusCode", statusCode);
		inputOutputParamters.put("documentIds", documentIds);

		int resultCount = labDocumentDAO
				.updateStatusCodeForHivDocuments(inputOutputParamters);

		return resultCount;
	}
	
	public StdOldDocData getStdOldDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id){
		return stdOldDocRepository.findDocument(docId);
	}
	
	public void rollbackStdDocumentNonMatches( 
			Map<String, String> schedulerMetaData){
		
		try {

			
			String  STD_NON_MATCHES_ROLLBACK_QUERY = schedulerMetaData.get("STD_NON_MATCHES_ROLLBACK_QUERY");
			
			Map<String, String> inputParameterMap = new HashMap<>();
			
			inputParameterMap.put("sqlQuery", STD_NON_MATCHES_ROLLBACK_QUERY);
			
			labDocumentDAO.restageStdDocumentNonMatches(inputParameterMap);
			
			
			
			
		} catch (Exception e) {
			
			log.error("Exception occured while rollbacking the STD document non matches {}", e.getMessage());
			
			
			UserObject userObject = new UserObject();
			
			userObject.setUserId("NYEHMS");
			
			commonExceptionHandler.handleException(e, userObject, null);
			
		}
		
	}
	
	public void insertStandardHIVResistanceResultFromOldDocument(
			int newDocumentId, Integer documentId){
		
		Map<String, Integer> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("CURRENT_DOCUMENT_ID", documentId);
		inputParameterMap.put("NEW_DOCUMENT_ID", newDocumentId);
		
		int resultCount = labDocumentDAO.insertStandardHIVResistanceResultFromOldDocument(inputParameterMap);
		
		if(resultCount == 0){
			//throw error
		}
		
	}
	
	public Integer getMultiStepDocumentId(Integer documentId){
		return labDocumentDAO.getMultiStepDocumentId(documentId);
	}
	
	
	public Map<String, Object> getRawDataAsObject(Integer documentId){
		return labDocumentDAO.getRawDataAsObject(documentId);
	}
	
	public Map<String, Object> getStdDataAsObject(Integer documentId){
		return labDocumentDAO.getStdDataAsObject(documentId);
	}
	
	public void insertRawEditedHIVLabDocumentWithUpdateData(String documentType,
			Map<String, Object> originalRawData, Integer documentId,
			UserObject userObject){
		int resultcount = labDocumentDAO.insertRawEditedHIVLabDocumentWithUpdateData(originalRawData);
		
		if(resultcount == 0){
			throw new DataRetrievalFailureException(new StringBuilder("Failed to insert the Raw ").append(documentType).append(" document  ").toString());
		}else{
			
			
			labDocumentDAO.updateHashUniqueNumber((Integer)originalRawData.get("DOCUMENT_ID"));
			
		}
	}
	
	public void insertStdEditedHivLabDocumentWithUpdateData(String documentType,
			Map<String, Object> originalStdData, Integer documentId){
		
		int resultcount = labDocumentDAO.insertStdEditedHivLabDocumentWithUpdateData(originalStdData);
		
		if(resultcount == 0){
			throw new DataRetrievalFailureException(new StringBuilder("Failed to insert the Std ").append(documentType).append(" document  ").toString());
		}
		
	}
	
	public void writeStandardDataFromRaw(EcrlsLabDocVO ecrlsLabDocVO, StdEcrlsLabDocVO stdEcrlsLabDocVO) {

		stdEcrlsLabDocVO.setDOCUMENT_ID(ecrlsLabDocVO.getDOCUMENT_ID());
		stdEcrlsLabDocVO.setCURRENT_PROCESS_STATUS(ecrlsLabDocVO.getCURRENT_PROCESS_STATUS());
		stdEcrlsLabDocVO.setLAB_SRC_ID(ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY());
		stdEcrlsLabDocVO.setFNAME(CommonUtil.NameValidation(ecrlsLabDocVO.getPIDFIRSTNAME()));
		stdEcrlsLabDocVO.setLNAME(CommonUtil.NameValidation(ecrlsLabDocVO.getPIDLASTNAME()));
		stdEcrlsLabDocVO.setMNAME(CommonUtil.NameValidation(ecrlsLabDocVO.getPIDMIDDLENAME()));
		stdEcrlsLabDocVO.setSUFFIX(ecrlsLabDocVO.getPIDNAMESUFFIX());
		stdEcrlsLabDocVO.setPFI(ecrlsLabDocVO.getMSHLABPFI());

		stdEcrlsLabDocVO.setBIRTH_COUNTRY(ecrlsLabDocVO.getPIDCOUNTRYOFORIGIN());

		stdEcrlsLabDocVO.setPERSON_ADDRESS(
				ecrlsLabDocVO.getPIDADDRESSLINE1().trim() + " " + ecrlsLabDocVO.getPIDADDRESSLINE2().trim());

		stdEcrlsLabDocVO.setPERSON_CITY(ecrlsLabDocVO.getPIDCITY());

		stdEcrlsLabDocVO.setPERSON_PHONE(ecrlsLabDocVO.getPIDHOMEPHONE());
		stdEcrlsLabDocVO.setMED_RECORD_NUMBER(CommonUtil.normalizeString(ecrlsLabDocVO.getPIDMEDICALRECORDNUMBER()));
		stdEcrlsLabDocVO.setINSURANCE_CD(ecrlsLabDocVO.getPIDTYPEOFINSURANCE());

		stdEcrlsLabDocVO.setINSURANCE_NUMBER(ecrlsLabDocVO.getPIDPATIENTACCOUNTNUM());

		stdEcrlsLabDocVO.setLENGTH_RESIDENCY_IN_USA(ecrlsLabDocVO.getPIDLENGTHOFRESIDENCY());

		stdEcrlsLabDocVO.setNYSID(CommonUtil.nysidValidation(ecrlsLabDocVO.getPIDNYSID()));

		stdEcrlsLabDocVO.setPROV_LNAME(ecrlsLabDocVO.getOBRPROVIDERLASTNAME());
		stdEcrlsLabDocVO.setPROV_FNAME(ecrlsLabDocVO.getOBRPROVIDERFIRSTNAME());
		stdEcrlsLabDocVO.setPROV_MNAME(ecrlsLabDocVO.getOBRPROVIDERMIDDLENAME());
		stdEcrlsLabDocVO.setPROV_SUFFIX(ecrlsLabDocVO.getOBRPROVIDERNAMESUFFIX());
		stdEcrlsLabDocVO.setPROV_ADDRESS(
				ecrlsLabDocVO.getZLRPROVIDERADDRESSLINE1() + " " + ecrlsLabDocVO.getZLRPROVIDERADDRESSLINE2());
		stdEcrlsLabDocVO.setPROV_CITY(ecrlsLabDocVO.getZLRPROVIDERCITY());

		stdEcrlsLabDocVO.setPROV_PHONE(ecrlsLabDocVO.getOBRCALLBACKPHONE());
		
		
		stdEcrlsLabDocVO.setPROV_NPI(CommonUtil.formatNumbericField(ecrlsLabDocVO.getOBRPROVIDERID(), 10));
		stdEcrlsLabDocVO.setFACILITY_NPI(CommonUtil.formatNumbericField(ecrlsLabDocVO.getZLRFACILITYCODE(), 10));
		
		stdEcrlsLabDocVO.setFACILITY_NAME(ecrlsLabDocVO.getZLRFACILITYNAME());
		stdEcrlsLabDocVO.setFACILITY_ADDRESS(
				ecrlsLabDocVO.getZLRFACILITYADDRESSLINE1() + " " + ecrlsLabDocVO.getZLRFACILITYADDRESSLINE2());
		stdEcrlsLabDocVO.setFACILITY_CITY(ecrlsLabDocVO.getZLRFACILITYCITY());

		stdEcrlsLabDocVO.setFACILITY_PHONE(ecrlsLabDocVO.getZLRFACILITYPHONE());

		//
		// OBXOBSERVATIONDATECalender =
		// CommonUtil.resetTimeToZero(ecrlsLabDocVO.getOBXOBSERVATIONDATE(),
		// Timestamp observationDateRaw =
		//
		// OBXOBSERVATIONDATECalender =
		// stdEcrlsLabDocVO.setOBSERVATION_DATE(new Date(

		stdEcrlsLabDocVO.setHIV_VACCINE_RECIP(ecrlsLabDocVO.getPIDVACCINETRIALIND());

		stdEcrlsLabDocVO.setLOINC_DESC(ecrlsLabDocVO.getOBXLOINCDESCSHORT());

		stdEcrlsLabDocVO.setPERSON_INTERNAL_ID(ecrlsLabDocVO.getPIDPATIENTIDINTERNAL());
		stdEcrlsLabDocVO.setPERSON_EXTERNAL_ID(ecrlsLabDocVO.getPIDPATIENTIDEXTERNAL());
		
		stdEcrlsLabDocVO.setSPECIMEN_SOURCE_CD(ecrlsLabDocVO.getOBRSPECIMENSOURCECODE());
		// stdEcrlsLabDocVO.setSPECIMEN_SOURCE_NAME(ecrlsLabDocVO
		// .getOBRSPECIMENSOURCENAME());

		stdEcrlsLabDocVO.setREFERRING_FACILITY(ecrlsLabDocVO.getPIDFACILITYIDEXTERNAL());
		
		String _inputClia = ecrlsLabDocVO.getMSHSENDINGFACILITYCLIA();
		String _facilityName = ecrlsLabDocVO.getZLRFACILITYNAME();
		
		if(OOS_CLIA.equals(_inputClia)) {
		
			Map<String, String> outParameterMap = labDocumentDAO.getOOSCliaDetails(_facilityName);
			
			if(outParameterMap == null) {
				throw new DataRetrievalFailureException("Didn't find the OOS CLIA details for the Facility name :"+_facilityName);
			}
			
			stdEcrlsLabDocVO.setSENDING_FACILITY_CLIA(outParameterMap.get("CLIA"));
			stdEcrlsLabDocVO.setTESTING_FACILITY(outParameterMap.get("CLIA"));
			stdEcrlsLabDocVO.setSENDING_FACILITY_NAME(outParameterMap.get("FACILITY_NAME"));
			
		}else {

			
			stdEcrlsLabDocVO.setSENDING_FACILITY_CLIA(_inputClia);
			stdEcrlsLabDocVO.setTESTING_FACILITY(ecrlsLabDocVO.getPIDFACILITYIDINTERNAL());
			stdEcrlsLabDocVO.setSENDING_FACILITY_NAME(ecrlsLabDocVO.getMSHSENDINGFACILITYNAME());
		}
		
		stdEcrlsLabDocVO.setPERSON_COUNTRY(ecrlsLabDocVO.getPIDCOUNTRYCODE());
		stdEcrlsLabDocVO.setACCESSION_NUMBER(ecrlsLabDocVO.getOBRACCESSIONNUM());
		stdEcrlsLabDocVO.setMEDICAID_NUMBER(CommonUtil.checkMedicaidNumberFormat(ecrlsLabDocVO.getPIDMEDICAIDNUMBER()));
		stdEcrlsLabDocVO.setLOCAL_TEST_CODE(ecrlsLabDocVO.getOBXLOCALTESTCODE());
		stdEcrlsLabDocVO.setLOCAL_TEST_DESC(ecrlsLabDocVO.getOBXLOCALTESTDESC());

	}
	
	public int writeStandardDataToDatabase(StdEcrlsLabDocVO stdEcrlsLabDocVO) {

		int resultCount = labDocumentDAO.insertStandardEcrlsRecord(stdEcrlsLabDocVO);

		if (resultCount == 0) {
			log.error("Failed to insert the data into Standard table  {}", stdEcrlsLabDocVO.getDOCUMENT_ID());
		}

		return resultCount;
	}
	
	public List<Map<String, String>> getLabCombiningDocData(){
		return labDocumentDAO.getLabCombiningDocData();
	}

}
