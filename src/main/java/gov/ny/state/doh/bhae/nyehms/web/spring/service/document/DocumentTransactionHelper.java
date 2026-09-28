package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;


import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRDataEntryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFDocumentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFPartnerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AhiAlertTrackingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftFollowupDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.RecordSearchReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.QuartzSchedulerTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.FacilityService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.ProviderService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * This class provides helper methods for document functions.
 * 
 * @author vxp06
 */
@Component
public class DocumentTransactionHelper {

	private static Logger log = LoggerFactory.getLogger(DocumentTransactionHelper.class);
	//private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();	
	
	@Autowired	CommonExceptionHandler commonExceptionHandler;	
	@Autowired	LabDocumentService labDocumentService;
	@Autowired	DoccsDocumentService doccsDocumentService;
	@Autowired	PRFDocumentService pRFDocumentService;
	@Autowired    NycPRFDocumentService nycPrfDocumentService;
	@Autowired	MedicaidDocumentService medicaidDocumentService;
	@Autowired    ManualQuartzJobService manualQuartzJobService;
	@Autowired	ProviderService providerService;
	@Autowired	EPRFOOCDocumentService ePRFOOCDocumentService;
	@Autowired    FacilityService facilityService;
	@Autowired    QuartzSchedulerTransactionHelper quartzSchedulerTransactionHelper;
	@Autowired	private PersionService personService;
	@Autowired	RecordSearchReviewService recordSearchReviewService;
	@Autowired	CommonDocumentService commonDocumentService;
	@Autowired	CommonDocumentEditService commonDocumentEditService;
	@Autowired	AHIDocumentService ahiDocumentService;
	@Autowired	ClusterDocumentService clusterDocumentService;
	@Autowired NonCaseDocumentService nonCaseDocumentService;
	@Autowired	RASDocumentService rasDocumentService;
	@Autowired	AssignmentService assignmentService;
	@Autowired ICCRDocumentService iccrDocumentService;
	@Autowired LaftFollowupDocumentService laftFollowupDocumentService;
	/*
	 * Because of AOP, we cannot call Transaction methods with in the same class as this is AOP feature.
	 * As we want the exception handling done at service level and populate error object of controller,
	 * we are using another class for transaction methods. Please visit link for more info
	 * http://stackoverflow.com/questions/3423972/spring-transaction-method-call-by-the-method-within-the-same-class-does-not-wo   
	 */
	
   

	/**
	 * 
	 * @param ecrlsLabDocVO
	 * @param requestComments
	 * @param result
	 * @param userObject
	 * @param errors
	 * @param sessionID
	 * @return
	 */
	@Transactional
	public int editLabErrorDoc(EcrlsLabDocVO ecrlsLabDocVO, String requestComments,
			BindingResult result, UserObject userObject, List<String> errors,
			String sessionID)
	{
		int currentDocumentId= ecrlsLabDocVO.getDOCUMENT_ID();
		EcrlsLabDocVO labDocVO_original = labDocumentService.getHivLabDoc(currentDocumentId, result, userObject, sessionID);
		labDocVO_original.setPIDFIRSTNAME(ecrlsLabDocVO.getPIDFIRSTNAME());
		labDocVO_original.setPIDMIDDLENAME(ecrlsLabDocVO.getPIDMIDDLENAME());
		labDocVO_original.setPIDLASTNAME(ecrlsLabDocVO.getPIDLASTNAME());
		labDocVO_original.setPIDADDRESSLINE1(ecrlsLabDocVO.getPIDADDRESSLINE1());
		labDocVO_original.setPIDADDRESSLINE2(ecrlsLabDocVO.getPIDADDRESSLINE2());
		labDocVO_original.setPIDCOUNTRYCODE(ecrlsLabDocVO.getPIDCOUNTRYCODE());
		labDocVO_original.setPIDHOMEPHONE(ecrlsLabDocVO.getPIDHOMEPHONE());
		labDocVO_original.setPIDWORKPHONE(ecrlsLabDocVO.getPIDWORKPHONE());
		labDocVO_original.setPIDSTATE(ecrlsLabDocVO.getPIDSTATE());
		labDocVO_original.setPIDZIPCODE(ecrlsLabDocVO.getPIDZIPCODE());
		labDocVO_original.setPIDCITY(ecrlsLabDocVO.getPIDCITY());
		labDocVO_original.setPIDCOUNTYCODEORIGINAL(ecrlsLabDocVO.getPIDCOUNTYCODEORIGINAL());
		labDocVO_original.setOBXOBSERVATIONRESULTTEXT(ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXT());
		labDocVO_original.setOBXOBSERVATIONRESULTTEXTSHORT(ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXT());
		labDocVO_original.setOBXUNITOFMEASURE(ecrlsLabDocVO.getOBXUNITOFMEASURE());
		labDocVO_original.setOBRPROVIDERFIRSTNAME(ecrlsLabDocVO.getOBRPROVIDERFIRSTNAME());
		labDocVO_original.setOBRPROVIDERLASTNAME(ecrlsLabDocVO.getOBRPROVIDERLASTNAME());
		labDocVO_original.setOBRPROVIDERMIDDLENAME(ecrlsLabDocVO.getOBRPROVIDERMIDDLENAME());
		labDocVO_original.setOBRPROVIDERNAMESUFFIX(ecrlsLabDocVO.getOBRPROVIDERNAMESUFFIX());
		labDocVO_original.setZLRPROVIDERADDRESSLINE1(ecrlsLabDocVO.getZLRPROVIDERADDRESSLINE1());
		labDocVO_original.setZLRPROVIDERADDRESSLINE2(ecrlsLabDocVO.getZLRPROVIDERADDRESSLINE2());
		labDocVO_original.setZLRPROVIDERCITY(ecrlsLabDocVO.getZLRPROVIDERCITY());
		labDocVO_original.setZLRPROVIDERSTATE(ecrlsLabDocVO.getZLRPROVIDERSTATE());
		labDocVO_original.setZLRPROVIDERZIPCODE(ecrlsLabDocVO.getZLRPROVIDERZIPCODE());
		labDocVO_original.setOBRCALLBACKPHONE(ecrlsLabDocVO.getOBRCALLBACKPHONE());
		labDocVO_original.setZLRFACILITYNAME(ecrlsLabDocVO.getZLRFACILITYNAME());
		labDocVO_original.setZLRFACILITYADDRESSLINE1(ecrlsLabDocVO.getZLRFACILITYADDRESSLINE1());
		labDocVO_original.setZLRFACILITYADDRESSLINE2(ecrlsLabDocVO.getZLRFACILITYADDRESSLINE2());
		labDocVO_original.setZLRFACILITYCITY(ecrlsLabDocVO.getZLRFACILITYCITY());
		labDocVO_original.setZLRFACILITYPHONE(ecrlsLabDocVO.getZLRFACILITYPHONE());
		//labDocVO_original.setZLRFACILITYPLUS4
		labDocVO_original.setZLRFACILITYSTATE(ecrlsLabDocVO.getZLRFACILITYSTATE());
		labDocVO_original.setZLRFACILITYZIPCODE(ecrlsLabDocVO.getZLRFACILITYZIPCODE());
		labDocVO_original.setOBRSPECIMENSOURCECODE(ecrlsLabDocVO.getOBRSPECIMENSOURCECODE());
		labDocVO_original.setOBRSPECIMENSOURCENAME(ecrlsLabDocVO.getOBRSPECIMENSOURCENAME());
		
		
		
		labDocVO_original.setOBRPROVIDERID(ecrlsLabDocVO.getOBRPROVIDERID());
		labDocVO_original.setPIDRACEWHITE(ecrlsLabDocVO.getPIDRACEWHITE());
		labDocVO_original.setPIDRACEBLACK(ecrlsLabDocVO.getPIDRACEBLACK());
		labDocVO_original.setPIDRACEPACIFIC(ecrlsLabDocVO.getPIDRACEPACIFIC());
		labDocVO_original.setPIDRACEASIAN(ecrlsLabDocVO.getPIDRACEASIAN());
		labDocVO_original.setPIDRACENATIVEAMERALASKAN(ecrlsLabDocVO.getPIDRACENATIVEAMERALASKAN());
		labDocVO_original.setPIDRACEOTHER(ecrlsLabDocVO.getPIDRACEOTHER());
		labDocVO_original.setPIDRACEUNKNOWN(ecrlsLabDocVO.getPIDRACEUNKNOWN());
		labDocVO_original.setPIDETHNICGROUPCODE(ecrlsLabDocVO.getPIDETHNICGROUPCODE());
		labDocVO_original.setPIDSEXCODE(ecrlsLabDocVO.getPIDSEXCODE());

		
		
		
		
		
		labDocVO_original.setCREATE_UID(Integer.toString(userObject.getWorkerId()));
		labDocVO_original.setCREATE_DATE(new Date(Calendar.getInstance().getTimeInMillis()));
		String _collectionDate = ecrlsLabDocVO.getOBRCOLLECTIONDATETEXT();
		//String _observationDate = ecrlsLabDocVO.getOBXOBSERVATIONDATETEXT();
		String _resultReportDate = ecrlsLabDocVO.getOBRRESULTREPORTDATETEXT();
		String _dateOfBirth = ecrlsLabDocVO.getPIDDATEOFBIRTHTEXT();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			if(_collectionDate != null && !"".equals(_collectionDate)){
				labDocVO_original.setOBRCOLLECTIONDATE(new java.sql.Timestamp(formatter.parse(_collectionDate).getTime()));
			}else{
				labDocVO_original.setOBRCOLLECTIONDATE(null);
			}
			if(_resultReportDate != null && !"".equals(_resultReportDate)){
				labDocVO_original.setOBRRESULTREPORTDATE(new java.sql.Timestamp(formatter.parse(_resultReportDate).getTime()));
			}else{
				labDocVO_original.setOBRRESULTREPORTDATE(null);
			}
			if(_dateOfBirth != null && !"".equals(_dateOfBirth)){
				labDocVO_original.setPIDDATEOFBIRTH(new java.sql.Timestamp(formatter.parse(_dateOfBirth).getTime()));
			}else{
				labDocVO_original.setPIDDATEOFBIRTH(null);
			}
		} catch (ParseException e) {
			throw new DataRetrievalFailureException("Failed to parse the date fields while editing the lab document  "+e.getMessage());
		}
		
		
		
		
		
		//
		
		String documentError = manualQuartzJobService.getDocumentError(currentDocumentId);
		
		int resultCount = manualQuartzJobService.deleteDocumentInErrorTable(currentDocumentId,requestComments, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, userObject);
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to update the delete status in Error table while editing the Error document  ");
		}
		resultCount = manualQuartzJobService.writeErrorCode(CommonConstants.RAW_DOCUMENT_EDITED_PROCESS_STATUS, currentDocumentId, "HIV_LAB");
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to update the delete status in Raw Hiv table while editing the Error document  ");
		}
		Map<Object, Object> inputOutputParameters = new HashMap<>();
		inputOutputParameters.put("p_document_type", "HIV_LAB");
		inputOutputParameters.put("p_new_doc_id", "");	
		inputOutputParameters.put("p_parent_doc_id", currentDocumentId);
		try {
			commonDocumentService.getNewDocId(inputOutputParameters);
		} catch (DataAccessException e) {
			throw new DataRetrievalFailureException("Failed to create a new Lab document for "+currentDocumentId+", while editing the lab error document        :   "+e.getMessage());
		} 
		int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");
		labDocVO_original.setDOCUMENT_ID(newDocumentId);
		
		if(labDocVO_original.getCURRENT_PROCESS_STATUS() == CommonConstants.RAW_HIV_MULTISTEP_ERROR_DOCUMENT){
			labDocVO_original.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_RECORD_MULTI_RECORDS_PROCESS_STATUS);
		} else if (CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getPIDSTATE()) && CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getZLRPROVIDERSTATE()) && !"NY".equals(ecrlsLabDocVO.getPIDSTATE()) && !"NY".equals(ecrlsLabDocVO.getZLRPROVIDERSTATE()) && CommonUtil.isNotNullAndEmpty(documentError)
				&& documentError.indexOf(CommonConstants.REFERRAL_LAB_ERROR) != -1) {
			labDocVO_original.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_HIV_REFERRAL_LAB_OOS_STATUS);
		}else{
			labDocVO_original.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_UNIQUE_RECORD_PROCESS_STATUS);
		}
		
		
		resultCount =  manualQuartzJobService.writeRawEcrlsData("HIV_LAB", labDocVO_original);
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to create new document, while editing the document  ");
		}
		// TODO Auto-generated method stub
		return resultCount;
	}

	
	

	public Map<String, Object> getStatenoDetailsOfDocument(int docId, UserObject userObject)
	{
		String stateno = personService.getNyehmsStatenoForTheDocument(docId);
		if(stateno != null && !"".equals(stateno)){
			return getStatenoDetailsOfStateno(docId, stateno, null, userObject);
		}else {
			return commonDocumentService.getDiscardedDocumentDetails(docId);
		}
		//return null;
	}

	/**
	 * 
	 * @param stateno
	 * @param userObject
	 * @return
	 */
	public Map<String, Object> getStatenoDetailsOfStateno(Integer docId, String stateno, String origin, UserObject userObject)
	{
		Map<String, Object> statenoDetails = new HashMap<>(); 
		List<String> nycCitnoList = personService.getNycCityNoDetails(stateno, null, userObject);
		
		statenoDetails.put("NYEHMS_STATENO", stateno);
		
		if(nycCitnoList != null && nycCitnoList.size() > 0){
			statenoDetails.put("NYC_CITYNO", nycCitnoList.get(0));
			nycCitnoList.remove(0);
			statenoDetails.put("NYC_CITYNO_LIST", nycCitnoList);
		}
		List<String> nycEharsDetails = personService.getNycEharsDetails(stateno, null, userObject);
		if(nycEharsDetails != null && nycEharsDetails.size() > 0){
			statenoDetails.put("NYC_EHARS_STATENO", nycEharsDetails.get(0));
			nycEharsDetails.remove(0);
			statenoDetails.put("NYC_EHARS_STATENO_LIST", nycEharsDetails);
		}
		List<String> eharsStatenoDetails = personService.getEharsStateno(stateno, null, userObject);
		if(eharsStatenoDetails != null && eharsStatenoDetails.size() > 0){
			statenoDetails.put("EHARS_STATENO", eharsStatenoDetails.get(0));
			eharsStatenoDetails.remove(0);
			statenoDetails.put("EHARS_STATENO_LIST", eharsStatenoDetails);
		}
		if(docId != null) {
			Map<String, Object> discardDocDetails = commonDocumentService.getDiscardedDocumentDetails(docId);
			if(discardDocDetails != null) {
				statenoDetails.put("CREATE_ID", discardDocDetails.get("CREATE_ID"));
				statenoDetails.put("CREATE_DATE", discardDocDetails.get("CREATE_DATE"));
				statenoDetails.put("REASON_DESC", discardDocDetails.get("REASON_DESC"));
			}
		}
		
		if("PERSON".equals(origin) && CommonUtil.isNotNullAndEmpty(stateno)) {
			Map<String, String> eharsPesonDetails = personService.getNYSEharsDxDetails(stateno);
			
			if(eharsPesonDetails != null) {
				
				statenoDetails.put("NYS_STAGE_ZERO_DX", EnvironmentProperties.envProperties.getProperty("STAGE_ZERO_DX-"+eharsPesonDetails.get("STAGE_ZERO_DX")));
				statenoDetails.put("NYS_STAGE_CD4_OI_DX", EnvironmentProperties.envProperties.getProperty("STAGE_CD4_OI_DX-"+eharsPesonDetails.get("STAGE_CD4_OI_DX")));
				
			}
			
			Map<String, String> nycEharsPesonDetails = personService.getNYCEharsDxDetails(stateno);
			
			if(nycEharsPesonDetails != null) {
				
				statenoDetails.put("NYC_STAGE_ZERO_DX", EnvironmentProperties.envProperties.getProperty("STAGE_ZERO_DX-"+nycEharsPesonDetails.get("STAGE_ZERO_DX")));
				statenoDetails.put("NYC_STAGE_CD4_OI_DX", EnvironmentProperties.envProperties.getProperty("STAGE_CD4_OI_DX-"+nycEharsPesonDetails.get("STAGE_CD4_OI_DX")));
				
			}
		}
		
		
		
		return statenoDetails;
	}

	@Transactional
	public void updateRawPrfDocument(PRFDocumentVO pRFDocumentVO, boolean isValidationFailed, 
			BindingResult bindingResult, UserObject userObject) {

	
			
			int currentDocumentId = pRFDocumentVO.getDOCUMENT_ID();
			
			Map<Object, Object> inputOutputParameters = new HashMap<>();
			inputOutputParameters.put("p_document_type", "PRF");
			inputOutputParameters.put("p_new_doc_id", "");	
			inputOutputParameters.put("p_parent_doc_id", currentDocumentId);
			
			try {
				commonDocumentService.getNewDocId(inputOutputParameters);
			} catch (DataAccessException e) {
				throw new DataRetrievalFailureException("Failed to Edit the PRF document  "+currentDocumentId+"        :   "+e.getMessage());
			} 
			
			int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");
			
			pRFDocumentVO.setDOCUMENT_ID(newDocumentId);
			
			List<PRFPartnerVO> partnerList = pRFDocumentVO.getPartnerList();
			
			int noOfPartners = 0;
			
			if(partnerList != null && partnerList.size() >= 0){
				noOfPartners = partnerList.size();
				
				
			}
			pRFDocumentVO.setPartnersCount(noOfPartners);
			
			String riskOtherText = pRFDocumentVO.getRISK_OTHER_TEXT();
			
			if(CommonUtil.isNotNullAndEmpty(riskOtherText)){
				pRFDocumentVO.setRiskOther("Y");
			}
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			
			String COMPLETED_DATE_TEXT = pRFDocumentVO.getCOMPLETED_DATE_TEXT();
			String PROV_SIGNED_DATE_TEXT = pRFDocumentVO.getPROV_SIGNED_DATE_TEXT();
			String CREATE_DATE_TEXT = pRFDocumentVO.getCREATE_DATE_TEXT();
			
			try {
				
		    	if(CREATE_DATE_TEXT != null){
					pRFDocumentVO.setCREATE_DATE(new java.sql.Date(formatter.parse(CREATE_DATE_TEXT).getTime()));
				}
		    	
		    	if(COMPLETED_DATE_TEXT != null){
					pRFDocumentVO.setCOMPLETED_DATE(new java.sql.Date(formatter.parse(COMPLETED_DATE_TEXT).getTime()));
				}
				
				if(PROV_SIGNED_DATE_TEXT != null){
					pRFDocumentVO.setPROV_SIGNED_DATE(new java.sql.Date(formatter.parse(PROV_SIGNED_DATE_TEXT).getTime()));
				}
				
			} catch (ParseException e) {
				throw new DataRetrievalFailureException(currentDocumentId+" : Failed to Edit the PRF document : "+e.getMessage());
			}
			
			pRFDocumentVO.setENTRY_REVIEW_UID(userObject.getUserId());
    		
			int resultCount = pRFDocumentService.insertPRFDoc(pRFDocumentVO, bindingResult, userObject);
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to Edit the PRF document "+currentDocumentId);
			}else{
				
				
				resultCount =  pRFDocumentService.updatePrfDocStatusInRawTable(currentDocumentId, CommonConstants.RAW_DOCUMENT_EDITED_PROCESS_STATUS, bindingResult, userObject);
				
				if(pRFDocumentVO.getCURRENT_PROCESS_STATUS() == 4){
				
					resultCount = manualQuartzJobService.deleteDocumentInErrorTable(currentDocumentId, "Edited the Document" , CommonConstants.RAW_DOCUMENT_EDITED_PROCESS_STATUS, userObject);
					
					if(resultCount == 0){
						throw new DataRetrievalFailureException("Failed to Edit the Document, please contact helpline  ");
					}
				}
				
				
				
				
				
				
				if(noOfPartners > 0){
					
					for (PRFPartnerVO prfPartnerVO : partnerList) {
						
						if(!CommonUtil.isNotNullAndEmpty(prfPartnerVO.getLname())){
							continue;
						}
						
						
						
						prfPartnerVO.setDocumentId(newDocumentId);
						prfPartnerVO.setPrfSrcCd(pRFDocumentVO.getPRF_SRC_ID());
						
						
						resultCount = pRFDocumentService.insertPRFPartner(prfPartnerVO, bindingResult, userObject);
						
						if(resultCount == 0){
							throw new DataRetrievalFailureException("Failed to Edit the PRF document "+currentDocumentId);
						}
					}
				
				}
				
			}
			
			
	}			
		


	@Transactional
	public void deleteRawPrfDoc(int docId, BindingResult bindingResult,
			UserObject userObject) {
		
		int resultCount = pRFDocumentService.updatePrfDocStatusInRawTable(docId, CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS, bindingResult, userObject); 
		
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to delete the PRF document "+docId);
		}
		
		commonDocumentService.deleteDocFromDocLocator(docId, userObject);
		
	}

	@Transactional
	public void editUnknownOosStdDocument(Map<String, String> formParameters,
			int documentId, String documentType, BindingResult bindingResult,
			UserObject userObject) {
		

		
		
		
		 Map<Object, Object> originalRawDoc = null;
		 
		 if("MED".equals(documentType) || "INS".equals(documentType)){
			 originalRawDoc = medicaidDocumentService.getRawMedicaidDocAndProvidersData(documentId);
		 }else{
			 originalRawDoc = commonDocumentService.getDocData(documentId, "RAW", documentType);
		 }
		  
		 Map<Object, Object> originalStdDoc = null;
		 if("MED".equals(documentType) || "INS".equals(documentType)){
			 originalStdDoc = medicaidDocumentService.getStdMedicaidDocAndProvidersData(documentId);
		 }else{
			 originalStdDoc = commonDocumentService.getDocData(documentId, "STD", documentType);
		 }
				 
		 
		 String providerCounty = null;
		 String facilityCounty = null;
		 String providerState = null;
		 String facilityState = null;
		 
				 
		 //EcrlsLabDocVO labDocVO_original = labDocumentService.getHivLabDoc(documentId, bindingResult, userObject, userObject.getSessionId());
		
		 //StdEcrlsLabDocVO stdDoc = labDocumentService.getLabDoc(documentId, "HIV_LAB", bindingResult, userObject, userObject.getSessionId());
		 
		 Map<String, String> rawDocReferenceColumns = LabCommonUtils.getRawReferenceColumnNames(documentType);
		 Map<String, String> stdDocReferenceColumns = LabCommonUtils.getStdReferenceColumnNames(documentType);
		 
		 if(formParameters.get("hasProviderData").equals("true")){
			 	
		    	String PROVIDER_ADDRESS = stdDocReferenceColumns.get("PROVIDER_ADDRESS");
		    	String PROVIDER_CITY = stdDocReferenceColumns.get("PROVIDER_CITY");
		    	String PROVIDER_STATE = stdDocReferenceColumns.get("PROVIDER_STATE");
		    	String PROVIDER_ZIPCODE = stdDocReferenceColumns.get("PROVIDER_ZIPCODE");
		    	String PROVIDER_COUNTY = stdDocReferenceColumns.get("PROVIDER_COUNTY");
		    	String PROVIDER_PHONE = stdDocReferenceColumns.get("PROVIDER_PHONE");
		    	String PROVIDER_PLUS4 = stdDocReferenceColumns.get("PROVIDER_PLUS4");
		    	String PROVIDER_FNAME = stdDocReferenceColumns.get("PROVIDER_FNAME");
		    	String PROVIDER_LNAME = stdDocReferenceColumns.get("PROVIDER_LNAME");
		    	String PROVIDER_MNAME = stdDocReferenceColumns.get("PROVIDER_MNAME");
		    	String PROVIDER_SUFFIX = stdDocReferenceColumns.get("PROVIDER_SUFFIX");
		    	String PROV_ADDR_LINE_2 = stdDocReferenceColumns.get("PROV_ADDR_LINE_2");
		    	String PROVIDER_NPI = stdDocReferenceColumns.get("PROVIDER_NPI");
		    	
			 
		    	
		    	 
				 String providerAddress = formParameters.get(PROVIDER_ADDRESS);
				 String providerCity = formParameters.get(PROVIDER_CITY);
				 providerState = formParameters.get(PROVIDER_STATE);
				 String providerZip = formParameters.get(PROVIDER_ZIPCODE);
				 providerCounty = formParameters.get(PROVIDER_COUNTY);
				 String provPhone = formParameters.get(PROVIDER_PHONE);
				 String provPlus4 = formParameters.get(PROVIDER_PLUS4);
				 String provFname = formParameters.get(PROVIDER_FNAME);
				 String provLname = formParameters.get(PROVIDER_LNAME);
				 String provMname = formParameters.get(PROVIDER_MNAME);
				 String provSuffix = formParameters.get(PROVIDER_SUFFIX);
				 String provAddress2 = formParameters.get(PROV_ADDR_LINE_2);
				 String provNPI = formParameters.get(PROVIDER_NPI);
				 
				 
				 
				 originalStdDoc.put(PROVIDER_ADDRESS, providerAddress);
				 
				 originalStdDoc.put(PROVIDER_CITY, providerCity);
				 originalStdDoc.put(PROVIDER_STATE, providerState);
				 originalStdDoc.put(PROVIDER_ZIPCODE, providerZip);
				 originalStdDoc.put(PROVIDER_COUNTY, providerCounty);
				 originalStdDoc.put(PROVIDER_PHONE, provPhone);
				 originalStdDoc.put(PROVIDER_PLUS4, provPlus4);
				 originalStdDoc.put(PROVIDER_FNAME, provFname);
				 originalStdDoc.put(PROVIDER_LNAME, provLname);
				 originalStdDoc.put(PROVIDER_MNAME, provMname);
				 originalStdDoc.put(PROVIDER_SUFFIX, provSuffix);
				 originalStdDoc.put(PROV_ADDR_LINE_2, provAddress2);
				 originalStdDoc.put(PROVIDER_NPI, provNPI);
				 
				 
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_ADDRESS"), providerAddress);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_CITY"), providerCity);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_STATE"), providerState);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_ZIPCODE"), providerZip);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_COUNTY"), providerCounty);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_PHONE"), provPhone);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_PLUS4"), provPlus4);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_FNAME"), provFname);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_LNAME"), provLname);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_MNAME"), provMname);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_SUFFIX"), provSuffix);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROV_ADDR_LINE_2"), provAddress2);
				 originalRawDoc.put(rawDocReferenceColumns.get("PROVIDER_NPI"), provNPI);
				 
				 
				 
				 
				 
			 
				 //checkAndSubProviderAddress(providerAddress, providerCity, providerState, providerZip, providerCounty, provPhone, provPlus4, provFname, provMname, provLname, provSuffix, bindingResult, userObject);
			 
			 
			 
			 
		 }
		 
		 if(formParameters.get("hasFacilityData").equals("true")){
			 
			 
				String FACILITY_ADDRESS = stdDocReferenceColumns.get("FACILITY_ADDRESS");
				String FACILITY_CITY = stdDocReferenceColumns.get("FACILITY_CITY");
				String FACILITY_STATE = stdDocReferenceColumns.get("FACILITY_STATE");
				String FACILITY_ZIP = stdDocReferenceColumns.get("FACILITY_ZIPCODE");
				String FACILITY_PHONE = stdDocReferenceColumns.get("FACILITY_PHONE");
				String FACILITY_PLUS4 = stdDocReferenceColumns.get("FACILITY_PLUS4");
				String FACILITY_COUNTY = stdDocReferenceColumns.get("FACILITY_COUNTY");
				String FACILITY_NAME = stdDocReferenceColumns.get("FACILITY_NAME");
				String FACILITY_ADDRESS_2 = stdDocReferenceColumns.get("FACILITY_ADDRESS_2"); 
				String FACILITY_DEPARTMENT = stdDocReferenceColumns.get("FACILITY_DEPARTMENT");
				String FACILITY_TYPE_OTHER_TEXT = stdDocReferenceColumns.get("FACILITY_TYPE_OTHER_TEXT");
				String FACILITY_TYPE = stdDocReferenceColumns.get("FACILITY_TYPE");
				String PFI = stdDocReferenceColumns.get("PFI");
		    	

			 
			String facilityAddress = formParameters.get(FACILITY_ADDRESS);
			 String facilityName = formParameters.get(FACILITY_NAME);
			 String facilityCity = formParameters.get(FACILITY_CITY);
			 String facilityPhone = formParameters.get(FACILITY_PHONE);
			 String facilityPlus4 = formParameters.get(FACILITY_PLUS4);
			 facilityState = formParameters.get(FACILITY_STATE);
			 String facilityZip = formParameters.get(FACILITY_ZIP);
			 facilityCounty = formParameters.get(FACILITY_COUNTY);
			 String facilityAddress2 = formParameters.get(FACILITY_ADDRESS_2);
			 String facilityDepartment = formParameters.get(FACILITY_DEPARTMENT);
			 String facilityTypeOtherText = formParameters.get(FACILITY_TYPE_OTHER_TEXT);
			 String facilityType = formParameters.get(FACILITY_TYPE);
			 String pfi = formParameters.get(PFI);
			 
			 
			 
			 originalStdDoc.put(FACILITY_ADDRESS, facilityAddress);
			 originalStdDoc.put(FACILITY_CITY, facilityCity);
			 originalStdDoc.put(FACILITY_STATE, facilityState);
			 originalStdDoc.put(FACILITY_ZIP, facilityZip);
			 originalStdDoc.put(FACILITY_PHONE, facilityPhone);
			 originalStdDoc.put(FACILITY_PLUS4, facilityPlus4);
			 originalStdDoc.put(FACILITY_COUNTY, facilityCounty);
			 originalStdDoc.put(FACILITY_NAME, facilityName);
			 originalStdDoc.put(FACILITY_ADDRESS_2, facilityAddress2);
			 originalStdDoc.put(FACILITY_TYPE, facilityType);
			 originalStdDoc.put(FACILITY_DEPARTMENT, facilityDepartment);
			 originalStdDoc.put(FACILITY_TYPE_OTHER_TEXT, facilityTypeOtherText);
			 originalStdDoc.put(PFI, pfi);
			 
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_ADDRESS"), facilityAddress);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_CITY"), facilityCity);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_STATE"), facilityState);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_ZIPCODE"), facilityZip);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_PHONE"), facilityPhone);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_PLUS4"), facilityPlus4);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_COUNTY"), facilityCounty);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_NAME"), facilityName);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_ADDRESS_2"), facilityAddress2);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_TYPE"), facilityType);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_DEPARTMENT"), facilityDepartment);
			 originalRawDoc.put(rawDocReferenceColumns.get("FACILITY_TYPE_OTHER_TEXT"), facilityTypeOtherText);
			 originalRawDoc.put(rawDocReferenceColumns.get("PFI"), pfi);
			 			
			 //checkAndAddFacilityAddress(facilityName, facilityAddress, facilityCity, facilityPhone, facilityPlus4, facilityState, facilityZip, facilityCounty, bindingResult, userObject);
			
			
		 }
		 
		//createNewStdDocument(documentId, documentType, bindingResult, userObject, originalRawDoc, originalStdDoc);
		
		String parentDocumentType = LabCommonUtils.getDocumentType(documentType);
		
		Map<Object, Object> inputOutputParameters = new HashMap<>();
		inputOutputParameters.put("p_document_type", parentDocumentType);
		inputOutputParameters.put("p_new_doc_id", "");	
		inputOutputParameters.put("p_parent_doc_id", documentId);
		
		try {
			commonDocumentService.getNewDocId(inputOutputParameters);
		} catch (DataAccessException e) {
			throw new DataRetrievalFailureException("Failed to create a new Lab document for "+documentId+"        :   "+e.getMessage());
		} 
		
		int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");
		
		
		originalRawDoc.put("DOCUMENT_ID",newDocumentId);
		originalStdDoc.put("DOCUMENT_ID",newDocumentId);
		
		originalRawDoc.put("CREATE_UID", userObject.getWorkerId());
		originalRawDoc.put("CREATE_DATE", new Date(Calendar.getInstance().getTimeInMillis()));
		
		originalStdDoc.put("CREATE_UID", userObject.getWorkerId());
		originalStdDoc.put("CREATE_DATE", new Date(Calendar.getInstance().getTimeInMillis()));
		originalStdDoc.put("EHARS_IMPORT_DATE", null);
		

		commonDocumentService.updateRawDocumentStatus(documentId, userObject, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS);

		Map<Object, Object> personDocData =  commonDocumentService.getPersonDocData(documentId);
		
		deleteStdDocumentExistence(documentId, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, "DISASSOC_EDIT",  userObject);
		
		commonDocumentService.updateRiaLabDocument(documentId, newDocumentId);
		
		originalRawDoc.put("CURRENT_PROCESS_STATUS", 5);
		commonDocumentService.writeRawDocData(parentDocumentType, originalRawDoc, documentId, userObject);
		
		
		if(CommonConstants.TEST_TYPE_RESISTANCE.equals(documentType)){
			
			
			Clob testResultsObj = (Clob)originalRawDoc.get("OBXOBSERVATIONRESULTTEXT");
			
			String testResults = null;
			
			if(testResultsObj != null){
				try{
					testResults = testResultsObj.getSubString(1, (int)testResultsObj.length());

				} catch (SQLException e) {
					throw new DataRetrievalFailureException("Unable to get the Observation result text from clob field  "+e.getMessage() );
				}
			}
			
			
			//originalStdDoc.put("TEST_RESULT", "RESISTANCE");
			
			BigDecimal OBXOBSERVATIONRESULTKEY = (BigDecimal)originalStdDoc.get("LAB_SRC_ID");
				
			
			//java.util.Date derivedCollectionDate = LabCommonUtils.getDerivedCollectionDate(COLLECTIONDATE, OBSERVATION_DATE, RESULT_REPORT_DATE, DATE_RECEIVED);
			
				
			labDocumentService.insertStandardHIVResistanceResult(newDocumentId, testResults, OBXOBSERVATIONRESULTKEY, (java.util.Date)originalStdDoc.get("CALC_COLLECTION_DATE"));

		}	
		commonDocumentService.writeStdDocData(parentDocumentType, originalStdDoc, documentId);
		
		
		commonDocumentService.moveStdDocumentToDocAttrTables((String)personDocData.get("STATENO"), newDocumentId, userObject, parentDocumentType, null);
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		BigDecimal PS_ASSIGN_STATUS = (BigDecimal)personDocData.get("PS_ASSESS_STATUS");
		
		if(PS_ASSIGN_STATUS != null && PS_ASSIGN_STATUS.intValue() != 0){
			commonDocumentService.updateTriggeringDocumentIdInNyehmsMessage(documentId, newDocumentId);
			inputParameterMap.put("PS_ASSIGN_STATUS", PS_ASSIGN_STATUS);
		}
		
		String DISCARD_STATUS = (String)personDocData.get("DISCARD_STATUS");
		
		if(CommonUtil.isNotNullAndEmpty(DISCARD_STATUS)){
			
			commonDocumentService.createNonCaseWithNewDocument(documentId, newDocumentId);
			
			inputParameterMap.put("DISCARD_STATUS", DISCARD_STATUS);
		}
		
		laftFollowupDocumentService.updateLaftFollowupDocumentId(documentId, newDocumentId);
		
		boolean isFlaggedForRecordSearch = false;
		
		
		if((CommonConstants.NY_CITY_COUNTIES.contains(providerCounty) &&
				
				(CommonUtil.isNullOrEmpty(facilityCounty) || "998".equals(facilityCounty) || "999".equals(facilityCounty) || CommonConstants.NY_CITY_COUNTIES.contains(facilityCounty)) )
				
				
				|| (CommonConstants.NY_CITY_COUNTIES.contains(facilityCounty)
						
						
						&&
						
						(CommonUtil.isNullOrEmpty(providerCounty) || "998".equals(providerCounty) || "999".equals(providerCounty) || CommonConstants.NY_CITY_COUNTIES.contains(providerCounty)) )
				
				
				){
			//Assignment status : NYC provider/facility (6)
			
			inputParameterMap.put("SURV_ASSESS_STATUS", 6);
			
			
			//commonDocumentService.updateSurvellianceAssignmentStatus(newDocumentId, 6, userObject, bindingResult);
		}
		else if((CommonUtil.isNullOrEmpty(providerCounty) || "998".equals(providerCounty) || "999".equals(providerCounty))  && (CommonUtil.isNullOrEmpty(facilityCounty) || "998".equals(facilityCounty) || "999".equals(facilityCounty))){
			//Assignment status : No assignment needed (3)
			
			
//			recordSearchReviewService.changeRecordSearchStatus((String)personDocData.get("STATENO"), comments.toString(), "Automatic", "Y", bindingResult, userObject);
			
			inputParameterMap.put("SURV_ASSESS_STATUS", 3);
			
			isFlaggedForRecordSearch = true;
			
//			commonExceptionHandler.inspectDBTransacionResult(0,
//					"Updated the Provider/Facility Address details and STATENO has been flagged for Record Search, review pending.", bindingResult);
			commonExceptionHandler.inspectDBTransacionResult(0,
					"Updated the Provider/Facility Address details", bindingResult);
			
			//commonDocumentService.updateSurvellianceAssignmentStatus(newDocumentId, 3, userObject, bindingResult);
		}
		
		
		if(inputParameterMap.size() > 0){
			inputParameterMap.put("DOCUMENT_ID", newDocumentId);
			commonDocumentEditService.updatePersonDocAssociationData(inputParameterMap);
		}
		
		if(!isFlaggedForRecordSearch){
			commonExceptionHandler.inspectDBTransacionResult(0,
				"This document will be assessed for assignment based on new address added.", bindingResult);
		}
	}

	
	@Transactional
	public void deleteStdDocumentExistence(int documentId, String documentType, int rawDocumentUpdateStatus, String associationStatus, UserObject userObject){
		
		log.error("Inactivating the Document from Raw, STD and Attribute table {}   Inactive Status : {}", documentId, rawDocumentUpdateStatus);
		
		commonDocumentService.deleteDocFromDocLocator(documentId, userObject);
		
		commonDocumentService.updateRawDocumentStatus(documentId, userObject, documentType, rawDocumentUpdateStatus);
		
		commonDocumentService.deleteEntryInPersonDocAssociation(documentId, documentType, associationStatus, userObject);
		
		//commonDocumentService.deleteEntryInDocumentPersonAttr(documentId, userObject);
		
		//commonDocumentService.deleteEntryInDocumentRace(documentId,  userObject);
		
		//commonDocumentService.deleteEntryInDocumentRiskTable(documentId,  userObject);
		
		commonDocumentService.updateStdDocumentStatus(documentId, documentType, rawDocumentUpdateStatus);
		
	}
	
	
	@Transactional
	public void moveRawToStdByStateno(int documentId, String stateno, String requestComments, List<String> errors, BindingResult bindingResult, UserObject userObject) {
		
		String _flagStatus = personService.checkStatenoIsActive(stateno, bindingResult, userObject);
		 
		 if(_flagStatus == null){
			 commonExceptionHandler.inspectDBTransacionResult(-1, "Please check, given stateno doesn't exist", bindingResult);
		 }else if("N".equalsIgnoreCase(_flagStatus)){
			 commonExceptionHandler.inspectDBTransacionResult(-1, "please check, given stateno is inactive", bindingResult);
		 }else{
		
			 EcrlsLabDocVO ecrlsLabDocVO = labDocumentService.getHivLabDoc(documentId, bindingResult, userObject, userObject.getSessionId());
			
			 StdEcrlsLabDocVO stdEcrlsLabDocVO = new StdEcrlsLabDocVO();
			 
			 Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
			 
			 List<Integer> errorCodes = new ArrayList<>();
			 
			 quartzSchedulerTransactionHelper.validatingRawRecord(ecrlsLabDocVO, errors, errorCodes, stdEcrlsLabDocVO, schedulerMetaData);
			 
			 //int errorsSize = errors.size();
			 
			 //if(errorsSize == 0 || (errorsSize == 1 && "Donor Report".equals(errors.get(0))) ){
					
					stdEcrlsLabDocVO.setMATCHED_STATENO(stateno);
					stdEcrlsLabDocVO.setMATCH_CD("M");
					
					boolean transactionStatus = quartzSchedulerTransactionHelper.moveRawDocumentToStd(ecrlsLabDocVO, stdEcrlsLabDocVO, CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS, 10, false);
					
					if(!transactionStatus){
						throw new DataRetrievalFailureException("Failed to move the document from Raw to Std table "+documentId);
					}
					
					

				commonDocumentService.moveStdDocumentToDocAttrTables(stateno, documentId, userObject, "HIV_LAB", null);
				
				int resultCount = manualQuartzJobService.deleteDocumentInErrorTable(documentId, requestComments, CommonConstants.RAW_STD_BY_PASS_DONOR_VALIDATION, userObject);
				
				if(resultCount  ==  0){
					throw new DataRetrievalFailureException("Unable to remove the entry from the Error table "+documentId);
				}
				
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully associated the document to give stateno.", bindingResult);
				
		 }
		 
				
			
		 
	}

	@Transactional
	public int writeNYCPRFDocAndPartnersData(Map<Object, Object> docVO,
			BigDecimal documentId) {

		int resultCount = nycPrfDocumentService.writeRawNycPRFRecordDataToStdTable(docVO, documentId);
		if(resultCount > 0){
			nycPrfDocumentService.writeRawNycPRFPartnerDataFromRawTable(documentId);
		}
		
		return resultCount;
	}

	@Transactional
	public int writePRFDocAndPartnersData(Map<Object, Object> docVO,
			BigDecimal documentId) {
		int resultCount = pRFDocumentService.writeRawPRFRecordDataToStdTable(docVO, documentId);
		
		
		Integer noOfPartners = Integer.parseInt((String)docVO.get("PARTNERS"));    	
    	
		if(resultCount > 0 && noOfPartners != null && noOfPartners > 0){
			pRFDocumentService.writeRawPRFPartnerDataFromRawTable(documentId);
		}
		
		return resultCount;
	}

	@Transactional
	public int writeRawePRFOOCDocData(Map<Object, Object> docVO,
			BigDecimal documentId) {
		
		int resultCount = ePRFOOCDocumentService.writeRawePRFOOCDocDataToStdTable(docVO, documentId);
		
		if(resultCount > 0){
			
			String fileType = (String)docVO.get("FILE_TYPE_CD");
			
			if(CommonUtil.isNotNullAndEmpty(fileType)){
				ePRFOOCDocumentService.updateDocumentTypeForEPRFOOCDocument(fileType, documentId);
			}else{
				throw new DataRetrievalFailureException("There is no file type code in the Raw EPRF document :  "+documentId);
			}
			
			
		}
		
		return resultCount;
		
		
		
	}

	@Transactional
	public int writeRawDoccsAndDinDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId) {
		
		int resultCount = doccsDocumentService.insertRawDoccsDataToStdTable(docVO, documentId);
		
		if(resultCount == 1){
			insertRawDinDoccsDataToStdTable(documentId);
		}else{
			throw new DataRetrievalFailureException("Failed to insert Raw Doccs Doc data :  "+documentId);
		}
		
		doccsDocumentService.insertRawDinProbCodesToStdProbCodes(documentId);
		
		return resultCount;
		
	}

	@Transactional
	private void insertRawDinDoccsDataToStdTable(BigDecimal documentId) {
		
		List<Map<String, Object>> rawDinDoccsList = doccsDocumentService.getRawDinDoccsList(documentId);
		
		for (Map<String, Object> dinDoc : rawDinDoccsList) {
			
				dinDoc.put("DOCCS_DOC_ID", documentId);
				
				doccsDocumentService.updateRawDinDocToStandardFormat(dinDoc);
			
		}
		
		int resultCount =  doccsDocumentService.insertRawDinDoccsDataToStdTable(rawDinDoccsList);
		
		if(rawDinDoccsList.size() != resultCount){
			throw new DataRetrievalFailureException("Failed to insert Raw Din Doccs to Std Din Doccs table. Doccs DocumentId:  "+documentId);
		}
		
		doccsDocumentService.updateRawDinDocStatus(documentId);
		
	}

	/**
	 * 
	 * @param documentId
	 * @param documentType
	 * @param inputData
	 * @param documentColumnMappingObj
	 * @param SOFT_ERROR_ONLY
	 * @param bindingResult
	 * @param userObject
	 */
	@Transactional
	public void storeEditedRawAndStdDocument(int documentId,
			String documentType, Map<String, String> inputData, Map<String, String> documentColumnMappingObj, String SOFT_ERROR_ONLY,
			BindingResult bindingResult, UserObject userObject)
	{
		Map<Object, Object> originalRawDoc = null;

		Map<Object, Object> originalStdDoc = null;
		
		
		
		if("MED".equals(documentType) || "INS".equals(documentType)){
			originalRawDoc = medicaidDocumentService.getRawMedicaidDocAndProvidersData(documentId);
		}else{
			originalRawDoc = commonDocumentService.getDocData(documentId, "RAW", documentType);
		}
		if("MED".equals(documentType) || "INS".equals(documentType)){
			originalStdDoc = medicaidDocumentService.getStdMedicaidDocAndProvidersData(documentId);
		}else{
			originalStdDoc = commonDocumentService.getDocData(documentId, "STD", documentType);
		}
		if("RESISTANCE".equals(documentType)) {
			//its readonly field when the document_type = 'RESISTANCE'
			documentColumnMappingObj.remove("TEST_RESULT");
		}
		
		for (String stdColumnName : documentColumnMappingObj.keySet()) {
			String stdEditedData = inputData.get(stdColumnName);
            if(CommonUtil.isNotNullAndEmpty(stdEditedData) && "../../....".equals(stdEditedData)){
				stdEditedData = null;
			}
			originalStdDoc.put(stdColumnName, stdEditedData);
			if (originalRawDoc != null) {
				originalRawDoc.put(documentColumnMappingObj.get(stdColumnName), stdEditedData);
			}
		}
		String parentDocumentType = LabCommonUtils.getDocumentType(documentType);
		Map<Object, Object> inputOutputParameters = new HashMap<>();
		inputOutputParameters.put("p_document_type", parentDocumentType);
		inputOutputParameters.put("p_new_doc_id", "");	
		inputOutputParameters.put("p_parent_doc_id", documentId);
		try {
			commonDocumentService.getNewDocId(inputOutputParameters);
		} catch (DataAccessException e) {
			throw new DataRetrievalFailureException("Failed to create a new Lab document for "
					+documentId+"        :   "+e.getMessage());
		} 
		int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");
		if(!documentType.equals(parentDocumentType)){
			commonDocumentService.writeDocumentTestType(newDocumentId, documentType);
		}
		
		
		
		
		originalStdDoc.put("DOCUMENT_ID",newDocumentId);
		originalStdDoc.put("CREATE_UID", userObject.getWorkerId());
		originalStdDoc.put("CREATEID", userObject.getWorkerId());
		originalStdDoc.put("CREATE_DATE", new Date(Calendar.getInstance().getTimeInMillis()));
		originalStdDoc.put("CREATE_DT", new Date(Calendar.getInstance().getTimeInMillis()));
		
		if (originalRawDoc != null) {
			originalRawDoc.put("DOCUMENT_ID",newDocumentId);
			originalRawDoc.put("CREATE_UID", userObject.getWorkerId());
			originalRawDoc.put("CREATE_DATE", new Date(Calendar.getInstance().getTimeInMillis()));
			originalRawDoc.put("CREATEID", userObject.getWorkerId());
			
			originalRawDoc.put("CURRENT_PROCESS_STATUS", 5);
			commonDocumentEditService.writeRawEditedDocData(parentDocumentType, originalRawDoc, documentId, userObject);
		}			

		if("HIV_LAB".equals(parentDocumentType)){
			EcrlsLabDocVO ecrlsLabDocVO = labDocumentService.getHivLabDoc(newDocumentId, bindingResult, userObject, userObject.getSessionId());
			List<String> errors = new ArrayList<>();
			List<Integer> errorCodes = new ArrayList<>();
			StdEcrlsLabDocVO stdEcrlsLabDocVO = new StdEcrlsLabDocVO();
			Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
			String errorStatus = ""; 
			manualQuartzJobService.prepareDefaultProcessQueryConditions(schedulerMetaData);
			
			ecrlsLabDocVO.setOBRCOLLECTIONDATERAW(null);
			ecrlsLabDocVO.setOBXOBSERVATIONDATERAW(null);
			ecrlsLabDocVO.setOBRRESULTREPORTDATERAW(null);
			
			boolean cliaAnaPFINotFound = manualQuartzJobService.validateCliaAndPFi(ecrlsLabDocVO, errors, errorCodes);
			if(!cliaAnaPFINotFound){
					errorStatus = quartzSchedulerTransactionHelper.validatingRawRecord(ecrlsLabDocVO, errors, errorCodes, stdEcrlsLabDocVO, schedulerMetaData);

			}else{
				errorStatus = "HARD";
			}
			if(errors.size() > 0){
				StringBuilder outputMessage = new StringBuilder();
				outputMessage.append("<span class='documentErrorMessages'>");
				for (String error : errors) {
					outputMessage.append(error);
					outputMessage.append("<br/>");
				}
				outputMessage.append("</span>");
				if("SOFT".equals(errorStatus) && "N".equals(SOFT_ERROR_ONLY)){
					outputMessage.insert(0, "Document has errors that can be bypassed and processed,"
							+ " click 'yes' if you want to bypass and process,"
							+ " click 'no' to correct the listed errors and save again. <br/> <br/>");
					commonExceptionHandler.inspectDBTransacionResult(302, outputMessage.toString(), bindingResult);
					throw new DataRetrievalFailureException("Unable to save, Lab document has soft errors. User needs to confirm");
				}else if(errorStatus.indexOf("HARD") != -1){
					outputMessage.insert(0, "Document has errors that must be corrected before saving <br/> <br/>");
					commonExceptionHandler.inspectDBTransacionResult(301, outputMessage.toString(), bindingResult);
					throw new DataRetrievalFailureException("Unable to save, Document has errors that must be corrected before saving");
				}
			}
			if(!documentType.equals(ecrlsLabDocVO.getTESTTYPE())){
				commonDocumentService.writeDocumentTestType(newDocumentId, ecrlsLabDocVO.getTESTTYPE());
			}
			originalStdDoc.put("AGE_CATEGORY", stdEcrlsLabDocVO.getAGE_CATEGORY());
			originalStdDoc.put("INTERPRET_CD", stdEcrlsLabDocVO.getINTERPRET_CD());
			originalStdDoc.put("TEST_RESULT", stdEcrlsLabDocVO.getTEST_RESULT());
			originalStdDoc.put("UNIT_MEASURE_CODE", stdEcrlsLabDocVO.getUNIT_MEASURE_CODE());
			originalStdDoc.put("SPECIMEN_SOURCE_NAME", stdEcrlsLabDocVO.getSPECIMEN_SOURCE_NAME());
			originalStdDoc.put("CALC_COLLECTION_DATE", stdEcrlsLabDocVO.getCALC_COLLECTION_DATE());

			if(CommonConstants.TEST_TYPE_RESISTANCE.equals(ecrlsLabDocVO.getTESTTYPE())){
				String testResults = ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXT();
				//originalStdDoc.put("TEST_RESULT", "RESISTANCE");
				Long OBXOBSERVATIONRESULTKEY = ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY();
				
				labDocumentService.insertStandardHIVResistanceResult(newDocumentId, testResults, OBXOBSERVATIONRESULTKEY, stdEcrlsLabDocVO.getCALC_COLLECTION_DATE());
				
				rasDocumentService.updateResistanceRasDocuments(documentId, bindingResult, userObject);
			}
		}else if("STD_LAB".equals(parentDocumentType)){
			String AGE = (String)originalStdDoc.get("AGE");
			if (CommonUtil.isNotNullAndEmpty(AGE)) {
				Integer zlrAgeValue = Integer.parseInt(AGE);
				Integer std_age = null;
				if (zlrAgeValue < 1) {
					std_age = 0;
				} else if (zlrAgeValue < 13) {
					std_age = 1;
				} else {
					std_age = 3;
				}
				originalStdDoc.put("AGE_CATEGORY", std_age);
			}
		}
		
		
		
		
		
		
		
		
		Map<Object, Object> personDocData =  commonDocumentService.getPersonDocData(documentId);
		
		deleteStdDocumentExistence(documentId, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, "DISASSOC_EDIT",  userObject);
		
		commonDocumentEditService.writeStdEditedDocData(parentDocumentType, originalStdDoc, documentId);

		commonDocumentService.moveStdDocumentToDocAttrTables((String)personDocData.get("STATENO"), newDocumentId, userObject, parentDocumentType, null);
		
		commonDocumentService.updateRiaLabDocument(documentId, newDocumentId);
		
		Map<String, Long> trigDocAddressIds = commonDocumentService.getTriggeringDocumentAddressIds(newDocumentId);
		
		Long documentProviderAddressId  = null;
		Long documentFacilityAddressId = null;
		
		if(trigDocAddressIds != null) {
			documentProviderAddressId = trigDocAddressIds.get("PROVIDER_ADDRESS_ID");
			documentFacilityAddressId = trigDocAddressIds.get("FACILITY_ADDRESS_ID");
		}
		
		BigDecimal PS_ASSIGN_STATUS = (BigDecimal)personDocData.get("PS_ASSESS_STATUS");
		BigDecimal SURV_ASSESS_STATUS = (BigDecimal)personDocData.get("SURV_ASSESS_STATUS");
		BigDecimal SURV_ASSIGNMENT_ID = (BigDecimal)personDocData.get("SURV_ASSIGNMENT_ID");
		Map<String, Object> inputParameterMap = new HashMap<>();
		if(PS_ASSIGN_STATUS != null && PS_ASSIGN_STATUS.intValue() != 0){
			commonDocumentService.updateTriggeringDocumentIdInNyehmsMessage(documentId, newDocumentId);
			inputParameterMap.put("PS_ASSIGN_STATUS", PS_ASSIGN_STATUS);
		}
		if(SURV_ASSESS_STATUS != null && SURV_ASSESS_STATUS.intValue() == 1){
			inputParameterMap.put("SURV_ASSESS_STATUS", SURV_ASSESS_STATUS);
			inputParameterMap.put("SURV_ASSIGNMENT_ID", SURV_ASSIGNMENT_ID);
			List<Integer> assignmentIds = commonDocumentEditService.getAssignmentsCreatedForDocument(documentId);
			if(assignmentIds == null || assignmentIds.size()  == 0){
				throw new DataRetrievalFailureException("No Assignments found for the Triggering Document  "+documentId);
			}
			commonDocumentEditService.updateTriggeringDocumentIdInAssignments(assignmentIds, newDocumentId);
			commonDocumentEditService.addEditedAssignmentActivity(assignmentIds, userObject);
			
			
			List<Integer> openAssignmentIds = commonDocumentService.getOpenAssignmentsForTriggeringDocIds(newDocumentId);
			
			
			if(openAssignmentIds != null) {
				
				for (Integer assignmentId : openAssignmentIds) {
					
					Map<String, Long> assignmentAddressIds = commonDocumentService.getAssignmentAddessIds(assignmentId);
					
					Long assignmentProviderAddressId = assignmentAddressIds.get("PROVIDER_ADDRESS_ID");
					Long assignmentFacilityAddressId = assignmentAddressIds.get("FACILITY_ADDRESS_ID");
					
					boolean isError = false;
					
					if(documentProviderAddressId != null && !documentProviderAddressId.equals( assignmentProviderAddressId)) {
						
						String closureCategory = null;
						
						if("998".equals(originalStdDoc.get("PROV_COUNTY"))) {
							closureCategory = "ACL102";
						}
						
						isError = assignmentService.changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(assignmentId, "FIELD", closureCategory, 0, documentProviderAddressId, "01", (String)originalStdDoc.get("PROV_COUNTY"), userObject, bindingResult);
					}else if(documentFacilityAddressId != null && !documentFacilityAddressId.equals(assignmentFacilityAddressId)) {
						String closureCategory = null;
						
						if("998".equals(originalStdDoc.get("FACILITY_COUNTY"))) {
							closureCategory = "ACL102";
						}
						isError = assignmentService.changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(assignmentId, "FIELD", closureCategory, 0, documentFacilityAddressId, "03", (String)originalStdDoc.get("FACILITY_COUNTY"), userObject, bindingResult);
					}
					
					if(isError) {
						log.error("Failed to change the address while editing the document");
						
						throw new DataRetrievalFailureException("Failed to update address details."+documentId);
						
					}
				
				}
			}
			
		}else if(SURV_ASSESS_STATUS != null && (SURV_ASSESS_STATUS.intValue() == 3 || SURV_ASSESS_STATUS.intValue() == 4 || SURV_ASSESS_STATUS.intValue() == 5 || SURV_ASSESS_STATUS.intValue() == 6)) {
			
			SURV_ASSESS_STATUS = null;
			
		}
		
		String DISCARD_STATUS = (String)personDocData.get("DISCARD_STATUS");
		
		if(CommonUtil.isNotNullAndEmpty(DISCARD_STATUS)){
			
			commonDocumentService.createNonCaseWithNewDocument(documentId, newDocumentId);
			
			inputParameterMap.put("DISCARD_STATUS", DISCARD_STATUS);
		}
		
		laftFollowupDocumentService.updateLaftFollowupDocumentId(documentId, newDocumentId);
		
		if(inputParameterMap.size() > 0){
			inputParameterMap.put("DOCUMENT_ID", newDocumentId);
			commonDocumentEditService.updatePersonDocAssociationData(inputParameterMap);
		}
		commonExceptionHandler.inspectDBTransacionResult(0, "Document successfully edited. ", bindingResult);
		//Need to write again
		//		if((CommonUtil.isNotNullAndEmpty(providerCounty) && CommonConstants.NY_CITY_COUNTIES.contains(providerCounty)) || (CommonUtil.isNotNullAndEmpty(facilityCounty) && CommonConstants.NY_CITY_COUNTIES.contains(facilityCounty))){
		//			//Assignment status : NYC provider/facility (6)
		//			//Assignment status : No assignment needed (3)
	}

	@Transactional
	public void processOOSUnknowndocument(int documentId, int processStatus, String survAssessStatus, String documentType,
			UserObject userObject, BindingResult bindingResult) {
		
		Map<Object, Object> personDocData =  commonDocumentService.getPersonDocData(documentId);
		
		String stateno = (String)personDocData.get("STATENO");
		
		if(CommonUtil.isNullOrEmpty(stateno)){
			log.error("Unable to find the stateno for the document {}",documentId);
			log.error("failed to process the OOS document");
			
			throw new DataRetrievalFailureException("failed to process the OOS document  ");
		}
		
		
		StringBuilder comments = new StringBuilder("Flagged from ");
		
		if("5".equals(survAssessStatus)){
			comments.append("OOS - ");
		}else if("4".equals(survAssessStatus)){
			comments.append("UNKNOWN - ");
		}else if("11".equals(survAssessStatus)){
			comments.append("OOS Follow-UP - ");
		}
		
		comments.append(documentType);
		
		
		
		boolean isError = recordSearchReviewService.changeRecordSearchStatus(stateno, comments.toString(), "Automatic", "Y", bindingResult, userObject);
		
		
		if(!isError){
			commonDocumentService.updateSurvellianceAssignmentStatus(documentId, processStatus, userObject, bindingResult);
		}else{
			
			log.error("{}  Failed to Flag it for Record Search, Stopped the Process to update the document Status to {}", stateno,processStatus);
			
			throw new DataRetrievalFailureException("failed to process the OOS document  ");
		}
		
		
		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the document status and STATENO has been flagged for Record Search, review pending." , bindingResult);
		
		
	}

	@Transactional
	public void insertAHIDocumentAndAssociateToStateno(AHIDocVO aHIDocVO, Integer parentDocumentId,
			BindingResult bindingResult, UserObject userObject) {
		
		
		
		Map<Object, Object> map = new HashMap<>();
		map.put("p_document_type", "AHI");
		map.put("p_new_doc_id", "");
		
		if(parentDocumentId != null){
			map.put("p_parent_doc_id", parentDocumentId);
		}
		int newDocId=0;
		
		
		try {
			commonDocumentService.getNewDocId(map);
		} catch (DataAccessException e) {
			log.error("Failed to create the AHI document  : {}",e.getMessage());
			throw new DataRetrievalFailureException("Failed to create the AHI document");
		} 
		
		
		newDocId=(Integer)map.get("p_new_doc_id");

		aHIDocVO.setDOCUMENT_ID(newDocId);
		aHIDocVO.setCREATE_UID(userObject.getWorkerId());
				
		ahiDocumentService.insertAHIDocument(aHIDocVO, bindingResult, userObject);
			
		commonDocumentService.moveStdDocumentToDocAttrTables(aHIDocVO.getSTATENO(), aHIDocVO.getDOCUMENT_ID(), userObject, "AHI", "DOC_TO_STATENO");
		
		String emailContent = null;
		
		if(parentDocumentId != null){
			assignmentService.updateAssignmentWithEditAHIDocument(parentDocumentId, newDocId, bindingResult, userObject);
			
			emailContent = "New AHI Document submitted for " + aHIDocVO.getSTATENO() + " by " + userObject.getUserFirstName() +
														" " + userObject.getUserLastName() + " " + new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
		}else{
			emailContent = "AHI Document got edited for " + aHIDocVO.getSTATENO() + " by " + userObject.getUserFirstName() +
					" " + userObject.getUserLastName() + " " + new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
		}
		
		ahiDocumentService.sendEmailNotificationToAHIUsers(userObject, EnvironmentProperties.envProperties.getProperty("EMAIL_AHI_DOCUMENT_SUBJECT"), emailContent);
		
		
	}
	

	@Transactional
	public void storeEditedAHIStdDocument(int documentId, AHIDocVO ahiDocVO,
			String documentType, BindingResult bindingResult, UserObject userObject) {
		
		
		String parentDocumentType = LabCommonUtils.getDocumentType(documentType);
		
		insertAHIDocumentAndAssociateToStateno(ahiDocVO, documentId, bindingResult, userObject);
		
		deleteStdDocumentExistence(documentId, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, "DISASSOC_EDIT",  userObject);
		
		commonExceptionHandler.inspectDBTransacionResult(0, "Document successfully edited. ", bindingResult);

		
	}
	
	@Transactional
	public void associateStateNoToCluster(Map<String, Object> clusterDetailsMap, ClusterDocVO currentDemoInfo, UserObject userObject, BindingResult bindingResult) {
		
		long documentId = clusterDocumentService.getClusterDocumentID();
		
		clusterDetailsMap.put("documentId", documentId);
		
		clusterDocumentService.insertIntoRawCluster(clusterDetailsMap, userObject);
		
		clusterDocumentService.insertIntoStdCluster(clusterDetailsMap, currentDemoInfo, userObject, bindingResult);
		
		clusterDocumentService.moveStdDocumentToDocAttrTables(documentId, userObject);
		
//		clusterDocumentService.sendEmailNotificationToClusterUsers(clusterDetailsMap.get("clusterId").toString());		
		
	}
	
/*	@Transactional
	public void updateClustEharsNyehmsStateno(Map<String, Object> clusterDetailsMap, ClusterDocVO currentDemoInfo, UserObject userObject, BindingResult bindingResult) {
		
		clusterDocumentService.updateClustEharsNyehmsStateno(clusterDetailsMap, currentDemoInfo, userObject, bindingResult);
		
		clusterDocumentService.insertIntoStdCluster(clusterDetailsMap, currentDemoInfo, userObject, bindingResult);
		
		long documentId = Long.parseLong(clusterDetailsMap.get("documentId").toString());
		
		clusterDocumentService.moveStdDocumentToDocAttrTables(documentId, userObject);
		
		clusterDocumentService.sendEmailNotificationToClusterUsers(clusterDetailsMap.get("clusterId").toString());
				
	}	*/
	
	@Transactional
	public void uploadClusterFileData(List<Map<String, Object>>  clusterDataList, ClusterUploadVO clusterUploadVO, UserObject userObject, BindingResult bindingResult, HttpSession session) throws SQLException {
		
		clusterDocumentService.insertNyehmsUploadCluster(clusterUploadVO, bindingResult, userObject, session);
		
		if (!clusterUploadVO.getClusterType().equals("BHAE_TS")) {
		
		clusterDocumentService.insertIntoRawStdCluster(clusterDataList, clusterUploadVO, bindingResult, userObject, session);
		
		clusterDocumentService.moveStdDocumentToDocAttrTables(0, userObject);
		
	//	clusterDocumentService.sendEmailNotificationToClusterUsers(clusterUploadVO.getClusterId());
		
		} else if (clusterUploadVO.getClusterType().equals("BHAE_TS")
				&& (clusterUploadVO.getTsUpdateRetire().equals("") || clusterUploadVO.getTsUpdateRetire().equals("U") || clusterUploadVO.getTsUpdateRetire().equals("A")) ) {
			
			log.error("bhaeTSCriteriaProcess called : Start : {} : {}",clusterUploadVO.getClusterId(),clusterUploadVO.getClusterIdPk());
			
			clusterDocumentService.bhaeTSCriteriaProcess(clusterUploadVO);
			
			log.error("bhaeTSCriteriaProcess called : END.");
		}
		
	}
	
	
	public void createNonCaseDocument(int triggeringDocId, String comments,
			int reason, BindingResult bindingResult, UserObject userObject) {
		
		boolean isError = false; 
		try{
			nonCaseDocumentService.createNonCaseDocument(triggeringDocId, comments, reason, bindingResult,userObject);
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to create the Non Case Document for the Document {}",triggeringDocId);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to create the Non Case Document for the Document {}",triggeringDocId);
			log.error(exception.getMessage());
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to create the Non Case Document, Please contact helpline.", bindingResult);
		}else if(!isError && !bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully created the Non Case Document", bindingResult);
		}
		
		
	}
	
//	public void discardNonCaseDocument(int triggeringDocId, String comments,


	public void changeNonCaseActiveFlagStatus(int triggeringDocId,
			String flagActive, BindingResult bindingResult,
			UserObject userObject) {
		boolean isError = false; 
		try{
			nonCaseDocumentService.changeNonCaseActiveFlagStatus(triggeringDocId, flagActive, bindingResult,userObject);
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to change the Non Case Doc Flag Status {}",triggeringDocId);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to change the Non Case Doc Flag Status {}",triggeringDocId);
			log.error(exception.getMessage());
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to change the Non Case Doc Flag Status, Please contact helpline.", bindingResult);
		}else if(!isError && !bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully change the Flag Status", bindingResult);
		}
		
	}
	
	@Transactional
	public void updateProviderAddress(int[] documentIds, AddressFieldVO addressFieldVO, String firstName,
			String lastName, String middleName, String suffix, String phone, String npi, BindingResult bindingResult, UserObject userObject) {

		for (int currentDocumentId : documentIds) {
			EcrlsLabDocVO labDocVO_original = labDocumentService.getHivLabDoc(currentDocumentId, bindingResult, userObject, userObject.getSessionId());
			
			labDocVO_original.setOBRPROVIDERFIRSTNAME(firstName);
			labDocVO_original.setOBRPROVIDERLASTNAME(lastName);
			labDocVO_original.setOBRPROVIDERMIDDLENAME(middleName);
			labDocVO_original.setOBRPROVIDERNAMESUFFIX(suffix);
			labDocVO_original.setZLRPROVIDERADDRESSLINE1(addressFieldVO.getAddress());
			labDocVO_original.setZLRPROVIDERADDRESSLINE2("");
			labDocVO_original.setZLRPROVIDERCITY(addressFieldVO.getCity());
			labDocVO_original.setZLRPROVIDERSTATE(addressFieldVO.getState());
			labDocVO_original.setZLRPROVIDERZIPCODE(addressFieldVO.getZip());
			labDocVO_original.setOBRCALLBACKPHONE(phone);
			labDocVO_original.setOBRPROVIDERID(npi);

			
			labDocVO_original.setCREATE_UID(Integer.toString(userObject.getWorkerId()));
			labDocVO_original.setCREATE_DATE(new Date(Calendar.getInstance().getTimeInMillis()));
			
			int resultCount = manualQuartzJobService.deleteDocumentInErrorTable(currentDocumentId,"Updating Provider Address", CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, userObject);
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to update the delete status in Error table while editing the Provider details");
			}
			
			resultCount = manualQuartzJobService.writeErrorCode(CommonConstants.RAW_DOCUMENT_EDITED_PROCESS_STATUS, currentDocumentId, "HIV_LAB");
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to update the delete status in Raw Hiv table while editing the Provider details");
			}
			
			Map<Object, Object> inputOutputParameters = new HashMap<>();
			inputOutputParameters.put("p_document_type", "HIV_LAB");
			inputOutputParameters.put("p_new_doc_id", "");	
			inputOutputParameters.put("p_parent_doc_id", currentDocumentId);
			try {
				commonDocumentService.getNewDocId(inputOutputParameters);
			} catch (DataAccessException e) {
				throw new DataRetrievalFailureException("Failed to create a new Lab document for "+currentDocumentId+", while editing the lab error document        :   "+e.getMessage());
			} 
			int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");
			labDocVO_original.setDOCUMENT_ID(newDocumentId);
			
			if(labDocVO_original.getCURRENT_PROCESS_STATUS() == CommonConstants.RAW_HIV_MULTISTEP_ERROR_DOCUMENT){
				labDocVO_original.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_RECORD_MULTI_RECORDS_PROCESS_STATUS);
			}else{
				labDocVO_original.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_UNIQUE_RECORD_PROCESS_STATUS);
			}
			
			
			resultCount =  manualQuartzJobService.writeRawEcrlsData("HIV_LAB", labDocVO_original);
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to create new document, while editing the document  ");
			}
		}
		
		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the Provider details for HIV Error document(s)", bindingResult);
		
	}
	
	@Transactional
	public void insertICCRRawData(ICCRDataEntryVO iccrDataEntryVO, UserObject userObject, BindingResult bindingResult) throws SQLException {
		
		Map<Object, Object> map = new HashMap<>();
		map.put("p_document_type", "ICCR");
		map.put("p_new_doc_id", "");
		map.put("p_parent_doc_id", "");

		int newDocId=0;
		
		try {
			commonDocumentService.getNewDocId(map);
		} catch (DataAccessException e) {
			log.error("Failed to create the ICCR document  : {}",e.getMessage());
			throw new DataRetrievalFailureException("Failed to create the ICCR document");
		} 
		
		newDocId=(Integer)map.get("p_new_doc_id");

		iccrDataEntryVO.setDOCUMENT_ID(newDocId);
		iccrDataEntryVO.setCREATE_UID(userObject.getWorkerId());
		
		iccrDocumentService.insertICCRRawData(iccrDataEntryVO, userObject);
		
	}
	
	@Transactional
	public void updateAhiAlertTrackingData(AhiAlertTrackingVO ahiAlertTrackingVO, UserObject userObject, BindingResult bindingResult) throws SQLException {
		
		ahiDocumentService.updateAhiAlertTracking(ahiAlertTrackingVO, userObject);
		
		ahiDocumentService.insertAhiAlertFollowup(ahiAlertTrackingVO, userObject);
		
	}	

	public void updateUnitsOfMeasure(int[] documentIds, String units, BindingResult bindingResult,
			UserObject userObject) {
		for (int currentDocumentId : documentIds) {
			EcrlsLabDocVO labDocVO_original = labDocumentService.getHivLabDoc(currentDocumentId, bindingResult, userObject, userObject.getSessionId());
			
			labDocVO_original.setOBXUNITOFMEASURE(units);
			

			
			labDocVO_original.setCREATE_UID(Integer.toString(userObject.getWorkerId()));
			labDocVO_original.setCREATE_DATE(new Date(Calendar.getInstance().getTimeInMillis()));
			
			int resultCount = manualQuartzJobService.deleteDocumentInErrorTable(currentDocumentId,"Updating the Units of measurement", CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, userObject);
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to update the delete status in Error table while editing the Units of measurement");
			}
			
			resultCount = manualQuartzJobService.writeErrorCode(CommonConstants.RAW_DOCUMENT_EDITED_PROCESS_STATUS, currentDocumentId, "HIV_LAB");
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to update the delete status in Raw Hiv table while editing the Units of measurement");
			}
			
			Map<Object, Object> inputOutputParameters = new HashMap<>();
			inputOutputParameters.put("p_document_type", "HIV_LAB");
			inputOutputParameters.put("p_new_doc_id", "");	
			inputOutputParameters.put("p_parent_doc_id", currentDocumentId);
			try {
				commonDocumentService.getNewDocId(inputOutputParameters);
			} catch (DataAccessException e) {
				throw new DataRetrievalFailureException("Failed to create a new Lab document for "+currentDocumentId+", while editing the lab error document        :   "+e.getMessage());
			} 
			int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");
			labDocVO_original.setDOCUMENT_ID(newDocumentId);
			
			if(labDocVO_original.getCURRENT_PROCESS_STATUS() == CommonConstants.RAW_HIV_MULTISTEP_ERROR_DOCUMENT){
				labDocVO_original.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_RECORD_MULTI_RECORDS_PROCESS_STATUS);
			}else{
				labDocVO_original.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_UNIQUE_RECORD_PROCESS_STATUS);
			}
			
			
			resultCount =  manualQuartzJobService.writeRawEcrlsData("HIV_LAB", labDocVO_original);
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to create new document, while editing the document  ");
			}
		}
		
		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the Units of measurement for HIV Error document(s)", bindingResult);
		
	}

	public void associateStatenoToCluster(String clusterId, String stateno, String requestComments,
			UserObject userObject, BindingResult bindingResult) {
		try {
			
			Map<String, Object> inputParameterMap = new HashMap<>();
			
			inputParameterMap.put("p_stateno", stateno);
			inputParameterMap.put("p_requestComments", requestComments);
			inputParameterMap.put("p_workerId", userObject.getWorkerId());
			inputParameterMap.put("p_clusterId", clusterId);
			
			clusterDocumentService.validateStatenoWithCluster(inputParameterMap,  bindingResult);
			
			
			clusterDocumentService.associateStatenoToCluster(inputParameterMap, bindingResult);
			
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully associated stateno with the Cluster", bindingResult);
			
		} catch (DataAccessException e) {
			log.error("Failed to associate stateno({}) with the Cluster({})", stateno, clusterId);
			log.error(e.getMessage());
			
			commonExceptionHandler.inspectDBTransacionResult(-1, e.getMessage(), bindingResult);
		} catch (Exception e) {
			log.error("Failed to associate stateno({}) with the Cluster({})", stateno, clusterId);
			log.error(e.getMessage());
			
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to associate stateno with the Cluster, please contact helpline", bindingResult);
		}
		
	}

	public void updateRawLabAddressDetails(int[] documentIds, AddressFieldVO addressFieldVO,
			BindingResult bindingResult, UserObject userObject) {
		for (int currentDocumentId : documentIds) {
			EcrlsLabDocVO labDocVO_original = labDocumentService.getHivLabDoc(currentDocumentId, bindingResult, userObject, userObject.getSessionId());
			
			labDocVO_original.setPIDADDRESSLINE1(addressFieldVO.getAddress());
			labDocVO_original.setPIDADDRESSLINE2("");
			labDocVO_original.setPIDCITY(addressFieldVO.getCity());
			labDocVO_original.setPIDSTATE(addressFieldVO.getState());
			labDocVO_original.setPIDZIPCODE(addressFieldVO.getZip());
			
			
			labDocVO_original.setCREATE_UID(Integer.toString(userObject.getWorkerId()));
			labDocVO_original.setCREATE_DATE(new Date(Calendar.getInstance().getTimeInMillis()));
			
			int resultCount = manualQuartzJobService.deleteDocumentInErrorTable(currentDocumentId,"Updating Provider Address", CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, userObject);
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to update the delete status in Error table while editing the Provider details");
			}
			
			resultCount = manualQuartzJobService.writeErrorCode(CommonConstants.RAW_DOCUMENT_EDITED_PROCESS_STATUS, currentDocumentId, "HIV_LAB");
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to update the delete status in Raw Hiv table while editing the Provider details");
			}
			
			Map<Object, Object> inputOutputParameters = new HashMap<>();
			inputOutputParameters.put("p_document_type", "HIV_LAB");
			inputOutputParameters.put("p_new_doc_id", "");	
			inputOutputParameters.put("p_parent_doc_id", currentDocumentId);
			try {
				commonDocumentService.getNewDocId(inputOutputParameters);
			} catch (DataAccessException e) {
				throw new DataRetrievalFailureException("Failed to create a new Lab document for "+currentDocumentId+", while editing the lab error document        :   "+e.getMessage());
			} 
			int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");
			labDocVO_original.setDOCUMENT_ID(newDocumentId);
			
			if(labDocVO_original.getCURRENT_PROCESS_STATUS() == CommonConstants.RAW_HIV_MULTISTEP_ERROR_DOCUMENT){
				labDocVO_original.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_RECORD_MULTI_RECORDS_PROCESS_STATUS);
			}else{
				labDocVO_original.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_UNIQUE_RECORD_PROCESS_STATUS);
			}
			
			
			resultCount =  manualQuartzJobService.writeRawEcrlsData("HIV_LAB", labDocVO_original);
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to create new document, while editing the document  ");
			}
		}
		
		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the Address details for HIV Error document(s)", bindingResult);
		
	}

}
