package gov.ny.state.doh.bhae.nyehms.web.spring.service.person;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentUpdateVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFDocumentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.LabDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NonCaseDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.PRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.RASDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftFollowupDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.ClusterPartnerReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * This class provides helper methods for Person level functions.
 * 
 * @author SKA03
 */
@Component
public class PersonTransactionHelper {

	private static final Logger log = LoggerFactory.getLogger(PersonTransactionHelper.class);
	
    @Autowired
	private PersionService personService;
    
    @Autowired
	private LabDocumentService labDocumentService;
    
    @Autowired
	private PRFDocumentService pRFDocumentService;

	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired RASDocumentService rasDocumentService;
	
	@Autowired NonCaseDocumentService nonCaseDocumentService;
	
	@Autowired CommonDocumentService commonDocumentService;
	
	@Autowired MergeUnMergePersonService mergeUnMergePersonService;
	
	@Autowired LaftFollowupDocumentService laftFollowupDocumentService;	
	
	@Autowired ClusterPartnerReviewService clusterPartnerReviewService;

	@Transactional
	public void triggerPsMessage(int documentId, String stateno,
			String documentType, String messageType, String psMessageForce, String softErrorCode, AddressFieldVO addressFieldVO, String reason, String otherReason,
			BindingResult bindingResult, UserObject userObject) {

    	boolean triggerPsMessage = false;
    	
		if("PRF".equals(documentType)){
			
			triggerPsMessage = true;
				
			
		}else{
			
			Map<Object,Object> inputParameterMap = new HashMap<>();
			
			inputParameterMap.put("documentId", documentId);
			
			
			List<String> triggeredMessageTypes = personService.getTriggeredMessageTypesForDocument(inputParameterMap);
			
			if(triggeredMessageTypes.contains(messageType) && "SOFT".equals(psMessageForce)){
	    		commonExceptionHandler.inspectDBTransacionResult(200, "PS Message already triggered with the same message type, do you want to trigger a new message?", bindingResult);
	    	}else if(triggeredMessageTypes.size() > 0 && "SOFT".equals(psMessageForce)){
	    		commonExceptionHandler.inspectDBTransacionResult(200, "PS Message already triggered with <span class='italicHighlightedBoldRecords'>"+String.join(", ", triggeredMessageTypes)+"</span> Message type(s), do you want to trigger a new message?", bindingResult);
	    	}else{
	    		
	    		String parentDocumentType = LabCommonUtils.getDocumentType(documentType);
	    			
	    		
	    		if("HIV_LAB".equals(parentDocumentType) && messageType.indexOf("OOC") != -1 && ("SOFT".equals(psMessageForce) || !"300".equals(softErrorCode)) ){
	    			
	    			StdEcrlsLabDocVO stdEcrlsLabDocVO = labDocumentService.getLabDoc(documentId, documentType, bindingResult, userObject, userObject.getSessionId());
	    			
	    			
	    			Calendar startCalendar = new GregorianCalendar();
	    			
	    			java.util.Date derivedCollectionDate = stdEcrlsLabDocVO.getCALC_COLLECTION_DATE();
	    			
	    			if(derivedCollectionDate == null){
	    				
	    				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to trigger a PS Message, No Collection Date available.", bindingResult);
	    				
	    				return;
	    				
	    			}
	    			
	    			startCalendar.setTime(derivedCollectionDate);
	    			
	    			Calendar endCalendar = new GregorianCalendar();
	    			endCalendar.setTime(new java.util.Date());

	    			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
	    			int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
	    			
	    			if(diffMonth <= 13){
	    				commonExceptionHandler.inspectDBTransacionResult(300, "Collection Date is with in last 13 months, do you want to trigger an OOC message?", bindingResult);
	    			}else{
	    				triggerPsMessage = true;
	    			}
	    			
	    			
	    		}else{
	    			triggerPsMessage = true;
	    		}
	    		
	    		
	    	}
			
		}
		if(triggerPsMessage){
			if(CommonUtil.isNotNullAndEmpty(reason)) {
				
				Map<String, Object> inOutParameterMap = new HashMap<>();
				
				inOutParameterMap.put("p_cluster_documentId", documentId);
				
				inOutParameterMap.put("p_reason", reason);
				inOutParameterMap.put("p_otherReason", otherReason);
				inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
				
				clusterPartnerReviewService.updateClusterPartnerPsMessageReasons(inOutParameterMap);
			}
		
		
			personService.executeTriggerPsMessage(documentId, stateno, documentType, messageType, addressFieldVO, bindingResult, userObject);
		}
    	
    	
    	
    	
    	
    	
		
	}

	public void getPsMessageType(int documentId, String stateno,
			String documentType, BindingResult bindingResult,
			UserObject userObject) {
		
			
			Map<Object,Object> inputParameterMap = new HashMap<>();
			
			inputParameterMap.put("stateno", stateno);
			
			List<String> triggeredMessageTypes = personService.getTriggeredMessageTypesForDocument(inputParameterMap);
		
			PRFDocumentVO pRFDocumentVO = pRFDocumentService.getPRFDocument(documentId);
			
			String INIT_HIV_DIAG_DATE_TEXT = pRFDocumentVO.getINIT_HIV_DIAG_DATE_TEXT();
			
			boolean INIT_HIV_DIAG = pRFDocumentVO.isINIT_HIV_DIAG();
			
			
			
			
			
			if(INIT_HIV_DIAG || CommonUtil.isNotNullAndEmpty(INIT_HIV_DIAG_DATE_TEXT)){
				if(triggeredMessageTypes.contains("New 900 Lab") ||  triggeredMessageTypes.contains("New 900 MS Comp")  ||  triggeredMessageTypes.contains("900  MS Incomp") ||  triggeredMessageTypes.contains("Possible AHI")){
					
					commonExceptionHandler.inspectDBTransacionResult(0, "PRF Lab Sent", bindingResult);
				}else{
					
					commonExceptionHandler.inspectDBTransacionResult(0, "New 900 PRF", bindingResult);
				}
				
			}else if(!INIT_HIV_DIAG && CommonUtil.isNullOrEmpty(INIT_HIV_DIAG_DATE_TEXT)){
				commonExceptionHandler.inspectDBTransacionResult(0, "PRF PS Request", bindingResult);
				
			}else{
				commonExceptionHandler.inspectDBTransacionResult(-1, "PRF input criteria is not matching", bindingResult);
			}
		
	}
	
	@Transactional
	public void deleteDocument(int documentId, String documentType, Integer discardReason, String comments, String stateno, String deleteFlag, 
			BindingResult bindingResult, UserObject userObject) {
		
		String eharsStateno = commonDocumentService.getEharsStateno(stateno);
		
		Map<String, String> nycStatenoDetails = commonDocumentService.getNYCStatenoDetails(stateno);
		
		String nycStateno = null;
		String nycCityno = null;
		
		if(nycStatenoDetails != null && CommonUtil.isNotNullAndEmpty(nycStatenoDetails.get("EHARS_STATENO"))) {
			nycStateno = nycStatenoDetails.get("EHARS_STATENO");
		}
		
		if(nycStatenoDetails != null && CommonUtil.isNotNullAndEmpty(nycStatenoDetails.get("CITYNO"))) {
			nycCityno = nycStatenoDetails.get("CITYNO");
		}
		
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("triggeringDocId", documentId);
		inputParameterMap.put("flagActive", "Y");
		inputParameterMap.put("comments", comments);
		inputParameterMap.put("reason", discardReason);
		inputParameterMap.put("workerId", userObject.getWorkerId());
		inputParameterMap.put("workerFullName", userObject.getUserFullName());
		
		inputParameterMap.put("NYEHMS_STATENO", stateno);
		inputParameterMap.put("EHARS_STATENO", eharsStateno);
		inputParameterMap.put("NYC_STATENO", nycStateno);
		inputParameterMap.put("NYC_CITYNO", nycCityno);
		
		
		nonCaseDocumentService.discardNonCaseDocument(inputParameterMap, discardReason, bindingResult, userObject);
		
		
		if (documentType.equals("RESISTANCE")) {
			rasDocumentService.updateResistanceRasDocuments(documentId, bindingResult, userObject);
		}
		
		Integer assignmentId = assignmentService.getOpenAssignmentIdByTrgDoc(documentId);
		
		if (documentType.equals("AHI")) {
			Integer assignmentIdOfAHI = assignmentService.getOpenAssignmentIdByAhiDoc(documentId);
			assignmentService.updateAssignmentWithAHIDocument(assignmentIdOfAHI, null, bindingResult, userObject);
		}		
		
		if(assignmentId != null){
			
			AssignmentUpdateVO assignmentUpdateVO = new AssignmentUpdateVO();
			
			assignmentUpdateVO.setClosureCategory("ACL83");
			assignmentUpdateVO.setDocumentId(assignmentId);
			
			log.error("Closing the Assignment ");
			log.error("Assignment Id: {}  Triggering documentId : {}",assignmentId,documentId);
			
			assignmentService.updateAssignment(assignmentUpdateVO, bindingResult, userObject);
			
			
			if (bindingResult.hasErrors()) {
				return;
			}
			
			
			
			
		}
		
		personService.deletePendingPSMessageDocuments(documentId, userObject);
		
		personService.deleteRiaDocument(documentId, userObject);
		
		
		assignmentService.updateReviewedDateForAllClosedAssignments(documentId, userObject);
		
		laftFollowupDocumentService.updateLaftFollowupResolutionForDeletedDocs(documentId, userObject);
		
		if(CommonUtil.isNullOrEmpty(deleteFlag) || !"Y".equals(deleteFlag)) {
			

			commonExceptionHandler.inspectDBTransacionResult(0,
					"Successfully discarded the document", bindingResult);
		
			return;
		}
		
		documentTransactionHelper.deleteStdDocumentExistence(documentId, documentType, CommonConstants.DOCUMENT_DELETED_PROCESS_STATUS, "DISASSOC_DEL", userObject);

		
	}

	@Transactional
	public String unflagAsNonCase(FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult, UserObject userObject) {

		
		
		String stateno = mergeUnMergePersonService.createNewStateno(bindingResult, userObject);
		
		flaggedStatenoVO.setStateno2(stateno);
		
		flaggedStatenoVO.setOriginCategorieVal("21");
		
		String requestComments= "Unflagging as Noncase, creating as new STATENO  :"+stateno;
		
		String nonCaseComments = flaggedStatenoVO.getRequestComments();
		
		flaggedStatenoVO.setRequestComments(nonCaseComments+".    "+requestComments);
		
		//int resultCount = 
		nonCaseDocumentService.unflagStatenoAsNonCase(flaggedStatenoVO, userObject);
		
		flaggedStatenoVO.setRequestComments(requestComments);
		
		flaggedStatenoVO.setSelectedStNo("STATENO2");
		
		Integer duplicationId =  mergeUnMergePersonService.flagStatenoPairsAsSame(flaggedStatenoVO, bindingResult, userObject);
		
		if(duplicationId == null){
			log.error("Failed to flag as Same for Non Case statenos.  Non case STATENO : {}",flaggedStatenoVO.getStateno1());
			throw new DataRetrievalFailureException("Failed, while unflagging a Noncase STATENO");
		}else{
			flaggedStatenoVO.setDuplicationId(duplicationId);
			
			Integer statusCode = mergeUnMergePersonService.callMergeStatenosService(flaggedStatenoVO, bindingResult, userObject);
			
			if(statusCode != 0){
				
				log.error("Failed while merging non case stateno with newly created STATENO");
				
				throw new DataRetrievalFailureException("Failed, while unflagging a Noncase STATENO");
			}
			
			
		}
		
	
		
//		if(resultCount > 0) {
//			commonExceptionHandler.inspectDBTransacionResult(0,
		
		return stateno;
		
	}

	
}
