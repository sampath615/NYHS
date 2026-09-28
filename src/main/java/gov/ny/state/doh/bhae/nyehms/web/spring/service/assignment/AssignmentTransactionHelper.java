package gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import gov.ny.state.doh.bhae.nyehms.web.spring.bo.ProcessACRFForm;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.AssignmentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentUpdateVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsAssignReassignVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfRidrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.exception.NyehmsException;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.RecordSearchReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.FacilityService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.ProviderService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

@Service
public class AssignmentTransactionHelper {
	
	private static final  Logger log = LoggerFactory.getLogger(AssignmentTransactionHelper.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
			
	@Autowired
	private ProcessACRFForm processACRFForm;
	@Autowired
	ACRFDocumentService acrfFDocumentService;
	
	@Autowired
	private AssignmentService assignmentService;

	@Autowired
	private RecordSearchReviewService recordSearchReviewService;
	
	@Autowired
	FacilityService facilityService;
	
	@Autowired
	ProviderService providerService;

	 @Autowired
	  private MessageSource messageSource;
	 
	/*
	 * Reference to SendEmail Java Mail component
	 */
	@Autowired
	SendEmail sendEmail;
	
	
	@Autowired
	CommonDocumentService commonDocumentService;
	
	@Autowired
	CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;
	
	
	@Autowired
	private AssignmentDAO assignmentDAO;
	
	@Transactional
	public void addACRFFormAndAssociateToStateno(String acrfReferenceRowId, AcrfFormVO acrfFormVO, @RequestParam String testType, 
			String stateno, BindingResult bindingResult, UserObject userObject, int parentDocumentId)throws NyehmsException, UnexpectedRollbackException {
		
		addACRFDocTrans(acrfFormVO, "ACRF", null, "ASSOC", true, bindingResult, userObject, userObject.getSessionId(), testType, null);
		
		
		if(parentDocumentId != 0){
			
			documentTransactionHelper.deleteStdDocumentExistence(parentDocumentId, "ACRF", CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, "DISASSOC_EDIT",  userObject);
		}
		
		commonDocumentService.moveStdDocumentToDocAttrTables(stateno, acrfFormVO.getRawAcrfDoc().getDocumentId(), userObject, "ACRF", "DOC_TO_STATENO");
		
		
		if("RIDR_ACRF".equals(testType) && CommonUtil.isNotNullAndEmpty(acrfReferenceRowId)){
			
			int resultCount = commonDocumentService.updateRIDRDocumentWithAcrf(acrfReferenceRowId, acrfFormVO.getRawAcrfDoc().getDocumentId(), userObject);
			
		}else if("RECORD_SEARCH".equals(testType) && CommonUtil.isNotNullAndEmpty(acrfReferenceRowId)){
			
			
			List<AcrfRidrVO> duplicateReviewList = acrfFormVO.getAcrfRidrList();
			
			List<String> duplicateStatenos = new ArrayList<>();
			
			for (AcrfRidrVO acrfRidrVO : duplicateReviewList) {
			
				if(CommonUtil.isNotNullAndEmpty(acrfRidrVO.getSTATENO())){
					duplicateStatenos.add(acrfRidrVO.getSTATENO());
				}
			}
			
			
			recordSearchReviewService.updateRecordSearchDocumentWithAcrf(acrfReferenceRowId, duplicateStatenos, stateno, acrfFormVO.getRawAcrfDoc().getDocumentId(), bindingResult, userObject);
		}
		
	}
	
	@Transactional
	public boolean addACRFDocTrans(AcrfFormVO acrfFormVO, String documentType, String assignmentId, String actionName, boolean havingNoErrorsinForm,  
			BindingResult result, 
			UserObject userObject, 
			String sessionID, String testType, String closureCateogry) throws NyehmsException, UnexpectedRollbackException {
		
		boolean hasLabDataChanges = false;
		boolean hasLabCd4Changes = false;
		boolean hasArvMedChanges = false;
		boolean hasRidrChanges = false;
			//This is 3 step transaction. First is ACRF doc insert, 2nd is ACRF labs insert and 3rd is CD4 Insert.
			//Get the ACRF DOC From VO and then generated PK, finally insert into table.
		acrfFDocumentService.addRawACRFDoc(processACRFForm.getRawAcrfDocInFormModelObject(acrfFormVO, result, testType, documentType), documentType, 
																						result,
																						userObject, 
																						sessionID);
		
		
		
			//compile labs records and insert.		
		hasLabDataChanges = acrfFDocumentService.addRawACRFLabsList(acrfFormVO, documentType, 
												result,
												userObject, 
												sessionID);
			//Add Cd4 Data.
		hasLabCd4Changes = acrfFDocumentService.addRawAcrfCd4Data(acrfFormVO, documentType, 
				result,
				userObject, 
				sessionID);	
		
		hasArvMedChanges = acrfFDocumentService.addRawArvMedData(acrfFormVO,  documentType, 
				result,
				userObject, 
				sessionID);	
		
		if(!"PCRF".equals(documentType)){
			hasRidrChanges = acrfFDocumentService.addRawAcrfRidrData(acrfFormVO, 
					result,
					userObject, 
					sessionID);
		}
		
		
		if("PCRF".equals(documentType)){
			acrfFDocumentService.updateSubStanceList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getScreenSubstanceList(), "SCREEN_SUBSTANCE", result,userObject);
			acrfFDocumentService.updateSubStanceList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpScreenSubstanceList(), "BP_SCREEN_SUBSTANCE", result,userObject);
			acrfFDocumentService.updateSubStanceList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpUseSubstanceList(), "BP_USE_SUBSTANCE", result,userObject);
			
			acrfFDocumentService.updateScreenConditionsList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpScreenConditionsList(), "BP_SCREEN_COND", result,userObject);
			acrfFDocumentService.updateScreenConditionsList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpDxScreenConditionsList(), "BP_DX_COND", result,userObject);
			
			acrfFDocumentService.updatePregOutcomeList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpPregOutcomeList(), "PREG_OUTCOME", result, userObject);
		}
		
		
		
		if(!"ASSOC".equals(actionName)){
			acrfFDocumentService.updateSurveillanceAssignmentWithAcrfDocId(acrfFormVO, documentType, assignmentId, result, userObject, sessionID);
		}
		
		if(CommonUtil.isNullOrEmpty(closureCateogry) || !"Y".equals(assignmentDAO.checkForNonCaseClosureCateogory(closureCateogry))){
			checkRiskAndNotify(acrfFormVO.getRawAcrfDoc(), documentType, userObject);
		}
		

		if (havingNoErrorsinForm && ("CLOSE".equals(actionName) ||  "ASSOC".equals(actionName) )) {
			checkAndCreateRawFacilities(acrfFormVO, result, userObject);
		}
		
		if(hasLabDataChanges || hasLabCd4Changes || hasArvMedChanges || hasRidrChanges){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	private void checkRiskAndNotify(RawAcrfDocVO acrfDoc, String documentType, UserObject userObject){
		 
		StringBuilder riskSelected = new StringBuilder();
		String _tempParagraphStart = "<p style='display:inline; font-weight:bold'>";
		String _tempParagraphEnd = "</p>";
		
		
		if("PCRF".equals(documentType)){

			if("Y".equals(acrfDoc.getIduBeforeDiag())){
				riskSelected.append(_tempParagraphStart).append((messageSource.getMessage("injectedNonprescriptionDrugsText", null, Locale.US))).append(_tempParagraphEnd);
			}
			if("Y".equals(acrfDoc.getRecvClottingFactorBeforeDiag())){
				riskSelected.append(_tempParagraphStart).append((messageSource.getMessage("recvClottingFactorText", null, Locale.US))).append(_tempParagraphEnd);
			}
			if("Y".equals(acrfDoc.getRecvBloodTransfusionBeforeDiag())){
				riskSelected.append(_tempParagraphStart).append((messageSource.getMessage("recvBloodTransfusionText", null, Locale.US))).append(_tempParagraphEnd);
			}
			if("Y".equals(acrfDoc.getRecvTransplantBeforeDiag())){
				riskSelected.append(_tempParagraphStart).append((messageSource.getMessage("recvTransplantChildText", null, Locale.US))).append(_tempParagraphEnd);
			}
			if("Y".equals(acrfDoc.getSexWithMaleBeforeDiag())){
				riskSelected.append(_tempParagraphStart).append((messageSource.getMessage("sexualContactWithMaleText", null, Locale.US))).append(_tempParagraphEnd);
			}
			if("Y".equals(acrfDoc.getSexWithFemaleBeforeDiag())){
				riskSelected.append(_tempParagraphStart).append((messageSource.getMessage("sexualContactWithMaleText", null, Locale.US))).append(_tempParagraphEnd);
			}
			if("Y".equals(acrfDoc.getOtherRiskBeforeDiag())){
				riskSelected.append(_tempParagraphStart).append((messageSource.getMessage("otherRiskText", null, Locale.US))).append(_tempParagraphEnd);
			}
			
		}else {
			
			if("Y".equals(acrfDoc.getRecvBloodTransfusion())){
				riskSelected.append(_tempParagraphStart).append((messageSource.getMessage("recvBloodTransfusionText", null, Locale.US))).append(_tempParagraphEnd);
			}
			
			if("Y".equals(acrfDoc.getRecvTransplant())){
				riskSelected.append(_tempParagraphStart).append(messageSource.getMessage("recvTransplantText", null, Locale.US)).append(_tempParagraphEnd);
			}
			
			if("Y".equals(acrfDoc.getHealthcareWorker())){
				riskSelected.append(_tempParagraphStart).append(messageSource.getMessage("healthcareWorkerText", null, Locale.US)).append(_tempParagraphEnd);
			}
			
			if("Y".equals(acrfDoc.getOtherRisk())){
				riskSelected.append(_tempParagraphStart).append(messageSource.getMessage("otherRiskText", null, Locale.US)).append(_tempParagraphEnd);
			}
			
			if("Y".equals(acrfDoc.getRecvClottingFactor())){
				riskSelected.append(_tempParagraphStart).append(messageSource.getMessage("recvClottingFactorText", null, Locale.US)).append(_tempParagraphEnd);
			}
			

			
		}
		
		
		
		if(CommonUtil.isNotNullAndEmpty(riskSelected.toString())){
			
			
			
			try {
			
				sendEmail.processEmail(null, "Potential COPHI", userObject.getUserFullName() +"has selected the following risk for stateno: "+acrfDoc.getStateno()+" "+riskSelected.toString(), "ACRF_RISK", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
				
				//String ccList = EnvironmentProperties.envProperties.getProperty("acrfNotificationEmailCC");
				
				
				//if (recipientEmailAddressesTo.length > 0) {
					//sendEmail.sendSSLMessage(
					//		recipientEmailAddressesTo,
					//		 "Potential COPHI", userObject.getUserFullName() +"has selected the following risk for stateno: "+acrfDoc.getStateno()+" "+riskSelected.toString(),
					//		 EnvironmentProperties.envProperties.getProperty("helpLineFrom"), recipientEmailAddressesCC,
			} catch (MessagingException messagingException) {
				
				log.error(nyehmsLog + "   Failed to send the Risk notification    ");
				log.error(ExceptionUtils.getStackFrames(messagingException).toString());
				
				throw new DataRetrievalFailureException("Potential COPHI  :   " + userObject.getUserFullName() +" has selected the following risk for stateno: "+acrfDoc.getStateno()+" "+riskSelected.toString());
			}
		}
	}
	
	
	@Transactional
	public boolean updateACRFDocTrans(AcrfFormVO acrfFormVO, String documentType, String actionName, boolean havingNoErrorsinForm,
								BindingResult result, 
								UserObject userObject, 
								String sessionID, String closureCategory) throws UnexpectedRollbackException{
		
		boolean hasLabDataChange = false;
		boolean hasLabCD4DataChange = false;
		boolean hasArvMedDataChange = false;
		boolean hasAcrfRidrDataChange = false;
		
		if(!"QUAL_ACRF".equals(actionName)){
			//Update ACRF DOC. This is also 3 step update transaction like 3 step add. 
			acrfFDocumentService.updateRawACRFDoc(acrfFormVO, documentType, result,userObject, sessionID);
		}
		


		hasLabDataChange = acrfFDocumentService.updateRawACRFLabs(acrfFormVO, documentType, result,userObject, sessionID);
		hasLabCD4DataChange = acrfFDocumentService.updateRawAcrfCd4Data(acrfFormVO, documentType, result,userObject, sessionID);
		if(!"QUAL_ACRF".equals(actionName)){
			hasArvMedDataChange = acrfFDocumentService.updateRawMedicationsData(acrfFormVO, documentType, result,userObject, sessionID);
		}
		
		if(!"QUAL_ACRF".equals(actionName)){
		
			if("PCRF".equals(documentType)){
				acrfFDocumentService.updateSubStanceList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getScreenSubstanceList(), "SCREEN_SUBSTANCE", result,userObject);
				acrfFDocumentService.updateSubStanceList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpScreenSubstanceList(), "BP_SCREEN_SUBSTANCE", result,userObject);
				acrfFDocumentService.updateSubStanceList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpUseSubstanceList(), "BP_USE_SUBSTANCE", result,userObject);
				
				acrfFDocumentService.updateScreenConditionsList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpScreenConditionsList(), "BP_SCREEN_COND", result,userObject);
				acrfFDocumentService.updateScreenConditionsList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpDxScreenConditionsList(), "BP_DX_COND", result,userObject);
				
				acrfFDocumentService.updatePregOutcomeList(acrfFormVO.getRawAcrfDoc().getDocumentId(), acrfFormVO.getRawAcrfDoc().getBpPregOutcomeList(), "PREG_OUTCOME", result, userObject);
			}else {
				
				hasAcrfRidrDataChange = acrfFDocumentService.updateRawAcrfRidrData(acrfFormVO, result,userObject, sessionID);
			}
			
			
			if(CommonUtil.isNullOrEmpty(closureCategory) || !"Y".equals(assignmentDAO.checkForNonCaseClosureCateogory(closureCategory))){
				checkRiskAndNotify(acrfFormVO.getRawAcrfDoc(), documentType, userObject);
			}
		
		}
		
		if (havingNoErrorsinForm && "CLOSE".equals(actionName)) {
			checkAndCreateRawFacilities(acrfFormVO, result, userObject);
		}
		
		if(hasLabDataChange || hasLabCD4DataChange || hasArvMedDataChange || hasAcrfRidrDataChange){
			return true;
		}else{
			return false;
		}
		
	}

	@Transactional
	public void changeAssignmentAddressDetailsByNewRawFacility(
			int assignmentId, String orgin, String closureCategory, long supervisorRegionId, RawSubFacilityVO rawSubFacilityVO,
			String addressType, UserObject userObject,
			BindingResult bindingResult){
		
		long rawFacilityId = facilityService.checkRawFacilityExistance(rawSubFacilityVO);
		
		if(rawFacilityId == 0){
			rawFacilityId = facilityService.createRawFacility(rawSubFacilityVO, bindingResult, userObject);
			
			if(rawFacilityId == 0){
				throw new DataRetrievalFailureException("Failed to create the new Raw Facility for the Assignment, please contact helpline.");
			}
		}
		
		changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(assignmentId, orgin, closureCategory, supervisorRegionId, rawFacilityId, addressType, rawSubFacilityVO.getCountyFips(), userObject, bindingResult);
		
	}

	@Transactional
	public void changeAssignmentAddressDetailsByNewRawProvider(
			int assignmentId, String orgin, String closureCategory, long supervisorRegionId, SubProviderAndAddressVO subProviderAndAddressVO,
			String addressType, UserObject userObject,
			BindingResult bindingResult) {
		
		long rawProviderId = providerService.checkRawProviderAddressExistance(subProviderAndAddressVO);
		
		if(rawProviderId == 0){
			rawProviderId = providerService.createRawProviderAddress(subProviderAndAddressVO, bindingResult, userObject);
			
			if(rawProviderId == 0){
				throw new DataRetrievalFailureException("Failed to create the new Raw Provider Address for the Assignment, please contact helpline.");
			}
		}
		
		
		changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(assignmentId, orgin, closureCategory, supervisorRegionId, rawProviderId, addressType, subProviderAndAddressVO.getCountyFips(), userObject, bindingResult);
		
	}

	
	private void checkAndCreateRawFacilities(AcrfFormVO acrfFormVO, BindingResult result, UserObject userObject){
		List<String> standardFacilityIds = new ArrayList<>();
		
		String providingFacilityName = acrfFormVO.getRawAcrfDoc().getProvidingFacilityId();
		String facilityHivDxName = acrfFormVO.getRawAcrfDoc().getFacilityHivDxId();
		String facilityAidsDxName = acrfFormVO.getRawAcrfDoc().getFacilityAidsDxId();
		
		if(providingFacilityName != null && !"".equals(providingFacilityName)){
			standardFacilityIds.add(providingFacilityName);
		}
		if(facilityHivDxName != null && !"".equals(facilityHivDxName)){
			standardFacilityIds.add(facilityHivDxName);
		}
		if(facilityAidsDxName != null && !"".equals(facilityAidsDxName)){
			standardFacilityIds.add(facilityAidsDxName);
		}
		
		for (String standardFacilityId : standardFacilityIds) {
			int resultCount = facilityService.checkRawFacilitiesForStandardFacilities(standardFacilityId, result,userObject, userObject.getSessionId());
			
			if(resultCount == 0){
				Long subFacilityId = Long.parseLong(standardFacilityId);
				
				facilityService.createRawFacilitiyForStandardFacility(subFacilityId, "", result,userObject, userObject.getSessionId());
			}
		}
	}

	@Transactional
	public void closeNeedsAssignment(String assignmentIds, String comments,
			UserObject userObject) {
		
		
			String[] assignmentIdsArr = assignmentIds.split(",");
			
			
			
			Map<Object, Object> map = new HashMap<>();
			
			map.put("p_user_id", userObject.getWorkerId());
			
			map.put("p_closure_category", "ACL87");
			
			map.put("p_comments", comments);
			
			for (String assignmentId : assignmentIdsArr) {
				
				map.put("p_document_id", Long.parseLong(assignmentId));
				
				assignmentService.closeAssignment(map);
				
				
				if ((map.get("p_code_out") == null) || (Integer) map.get("p_code_out") != 0) {
					
					log.error("{}  :  Failed to Close the Assignment  :  {}",assignmentId,map.get("p_msg_out"));
					
					
					throw new DataRetrievalFailureException((String)map.get("p_msg_out"));
					
					
				}
				
				
			}
		
	}

	@Transactional
	public void updateAssignment(AssignmentUpdateVO assignmentUpdateVO,
			AHIDocVO ahiDocData, BindingResult dummyBindingResult,
			UserObject userObject) {
		
		String closureCategory = assignmentUpdateVO.getClosureCategory();
		
		boolean isAHIDocAvailable = false;
		
		boolean isFlaggedForRecordSearch = false;
		
		if(CommonUtil.isNotNullAndEmpty(closureCategory) && CommonUtil.isNotNullAndEmpty(ahiDocData.getFNAME()) ){
			
			isAHIDocAvailable = true;
			
			if(ahiDocData.getDOCUMENT_ID() == null){
				documentTransactionHelper.insertAHIDocumentAndAssociateToStateno(ahiDocData, null, dummyBindingResult, userObject);
			}else{
				documentTransactionHelper.storeEditedAHIStdDocument(ahiDocData.getDOCUMENT_ID(), ahiDocData, "AHI", dummyBindingResult, userObject);
			}
			
			
			assignmentService.updateAssignmentWithAHIDocument(assignmentUpdateVO.getDocumentId(), ahiDocData.getDOCUMENT_ID(), dummyBindingResult, userObject);
			
		}
		
		assignmentService.updateAssignment(assignmentUpdateVO, dummyBindingResult, userObject);
		
		
		
		if(CommonUtil.isNotNullAndEmpty(closureCategory)){
			
			String stateno = assignmentService.getStatenoForAssignment(assignmentUpdateVO.getDocumentId());
			
			boolean isError = false;
			
			if("ACL29".equals(closureCategory)){
				
				isError = recordSearchReviewService.changeRecordSearchStatus(stateno, "Surveillance closed military testing", "Automatic", "Y", dummyBindingResult, userObject);
				
				isFlaggedForRecordSearch = true;
				
			}else if("ACL34".equals(closureCategory)){
				
				isError= recordSearchReviewService.changeRecordSearchStatus(stateno, "Surveillance closed Lost to Follow-up", "Automatic", "Y", dummyBindingResult, userObject);
				
				isFlaggedForRecordSearch = true;
			}
			
			
			if(isError){
				
				log.error("{}  Failed to Flag it for Record Search, unable to Close the Assignment ", stateno);
				
				throw new DataRetrievalFailureException("failed to Close the Assignment.");
				
			}
			
			
		}
		
		String transactionMessage = null;		
		
		if(isAHIDocAvailable){
			
			transactionMessage = "Assignment successfully closed with AHI document ";
			
		}else if(CommonUtil.isNotNullAndEmpty(closureCategory)){
			
			transactionMessage = "Successfully closed the Assignment ";
			
		}else{
			
			transactionMessage = "Successfully added the comment ";
			
		}
		
		if(isFlaggedForRecordSearch){
			transactionMessage = transactionMessage + " and STATENO has been flagged for Record Search, review pending.";
		}
		
		commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, dummyBindingResult);
		
		
		
	}

	@Transactional
	public void changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(
			int assignmentId, String origin, String closureCategory,
			long supervisorRegionId, long addressId, String addressType,
			String countyCode, UserObject userObject,
			BindingResult bindingResult) {

			int statusCode = assignmentService.changeAssignmentAddressDetails(assignmentId, origin, closureCategory, supervisorRegionId, addressId, addressType, countyCode, userObject, bindingResult);
			
			if(statusCode != 0){
				
				Map<Object,Object> resultMap=((Map<Object,Object>)bindingResult.getTarget());
				
				throw new DataRetrievalFailureException((String)resultMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
			}
			
			
			if("999".equals(countyCode) || "998".equals(countyCode)){
				
				String stateno = assignmentService.getStatenoForAssignment(assignmentId);
				
				
				Map<String, String> targetMap = new HashMap<>();
				BindingResult dummyBindingResult = new MapBindingResult(targetMap,
						"Sample");
				
				recordSearchReviewService.changeRecordSearchStatus(stateno, "Surv. Field Staff change address UNK/OOS", "Automatic", "Y", dummyBindingResult, userObject);
				
				if(dummyBindingResult.hasErrors()){
					
					log.error("Record Search Flagging failed while changing the Assignment Address to OOS/Unknown");
					
					throw new DataRetrievalFailureException("Record Search Flagging failed while changing the Assignment Address to OOS/Unknown");
					
				}
				
				
				
				Map<Object,Object> resultMap=((Map<Object,Object>)bindingResult.getTarget());
				
				if(resultMap != null && resultMap.get("TRANSACTION_MESSAGE") != null){
					String transactionMessage = (String)resultMap.get("TRANSACTION_MESSAGE");
					
					
					commonExceptionHandler.inspectDBTransacionResult(0,
							transactionMessage+" and STATENO has been flagged for Record Search, review pending.", bindingResult);
				}
				
				
			}
			
			
			
	}

	@Transactional
	public void discardACRFForm(String acrfDocId, String documentType,
			String assignmentId, String reabstract, UserObject userObject, BindingResult bindingResult) {

		Map<Object, Object> docIdMap = new HashMap<>();
		
		docIdMap.put("documentId", assignmentId);
		docIdMap.put("lastUpdatedBy", userObject.getWorkerId());
		
		if (reabstract.equals("N")) {
			assignmentService.discardAcrfDocIdFromSurveillanceAssignment(docIdMap);
		} else {
			assignmentService.discardReabAcrfDocIdFromSurveillanceAssignment(docIdMap);
		}
		
		int acrfDocumentId = Integer.parseInt(acrfDocId);
		
		int resultCount = commonDocumentService.checkForActiveDocumentInPersonTables(acrfDocumentId);
		
		if(resultCount > 0){
			
			documentTransactionHelper.deleteStdDocumentExistence(acrfDocumentId, documentType , CommonConstants.DOCUMENT_DELETED_PROCESS_STATUS, "DISASSOC_DEL", userObject);
			
		}else{
			
			commonDocumentService.deleteDocFromDocLocator(Integer.parseInt(acrfDocId), userObject);
			
			commonDocumentService.updateRawDocumentStatus(Integer.parseInt(acrfDocId), userObject, documentType, CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS);
			
			
		}
	
		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully discarded Acrf Form", bindingResult);
		
	
			
		
		
	}
	
	@Transactional
	public void updateReabActivityNStatus( String assignmentId, String reabCrfDocId, String crfDocId, String documentType, String comments, String newReabActivity,  String reabClosureCategory,
			UserObject userObject,	BindingResult bindingResult, HttpServletRequest request){
		
		String reabStatus = "";
		if (newReabActivity.equals("AREAB05")) {
			reabStatus = "REAB_C";
			assignmentService.saveFSAndReabCRFDiff(assignmentId, reabCrfDocId, crfDocId, documentType, bindingResult, userObject, request);
		} else if (newReabActivity.equals("AREAB09")) {
			reabStatus = "REAB_CR";
		} else if (newReabActivity.equals("AREAB06")) {
			reabStatus = "REAB_N";
		} else {
			reabStatus = "REAB_A";
		} 
			
		if (newReabActivity.equals("AREAB06") && !reabCrfDocId.equals("0")) {
			discardACRFForm(reabCrfDocId, documentType, assignmentId, "Y", userObject, bindingResult);
		}
		int resultCount = assignmentService.updateReabActivity(assignmentId, newReabActivity, comments, bindingResult, userObject);
		
		if (resultCount != 0 ) {
			resultCount = assignmentService.updateReabStatus(assignmentId, reabStatus, reabClosureCategory, bindingResult, userObject);
		}

		if(resultCount == 0 ){
			throw new DataRetrievalFailureException("Failed to update the Reabstraction Activity or Status for the Assignment");
		}
	
	}
	
	@Transactional
	public void deleteCRFNReopenAssign(AssignmentsAssignReassignVO assignReassignVO, int documentId, String documentType, String stateno, BindingResult bindingResult, UserObject userObject, HttpServletRequest request){
		
		documentTransactionHelper.deleteStdDocumentExistence(documentId, documentType, CommonConstants.DOCUMENT_DELETED_PROCESS_STATUS, "DISASSOC_DEL", userObject);
		
		if (documentType.equals("ACRF")) {
			assignReassignVO.setDelCrf("A");
		} else if (documentType.equals("PCRF")) {
			assignReassignVO.setDelCrf("P");
		}
		
		Map<Object, Object> map = new HashMap<>();
		map.put("p_assignment_list", assignReassignVO.getAssignmentIds());
		map.put("p_worker_id", assignReassignVO.getWorkerId());
		map.put("p_comments", assignReassignVO.getComments());
		map.put("p_review", "");
		map.put("p_workerId", userObject.getWorkerId());
		map.put("p_supervisor_region_id", assignReassignVO.getSupervisorRegionId());
		map.put("p_deleted_crf", assignReassignVO.getDelCrf());
		
		assignmentDAO.reOpenAssignments(map);

		Integer statusCode = (Integer) map.get("p_code_out");
		if (statusCode != 0) {
			throw new DataRetrievalFailureException("Error when reopen Assignment, please contact the Helpline. ");
		}
		
	}	
}
