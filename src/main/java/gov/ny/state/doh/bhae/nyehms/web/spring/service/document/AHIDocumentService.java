package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AhiAlertTrackingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface AHIDocumentService {

	public List<Map<String, String>> getAHICISTIConditionList();
	public List<Map<String, String>> getAHICIReasonPatientTestedList();
	public List<Map<String, String>> getAHINegInd900TestTypeList();
	
	public void insertAHIDocument(AHIDocVO ahiDocVo, BindingResult result, UserObject userObject);
	
	public void sendEmail(BindingResult result, UserObject userObject) throws MessagingException;
	public int changeAhiFlagStatusForAssignment(int assignmentId,
			boolean ahiFlagStatus, String documentType, UserObject userObject,
			BindingResult bindingResult);
	
	public AHIDocVO getAHIFormVO(int documentId, BindingResult result, UserObject userObject);	
	
	public void sendEmailNotificationToAHIUsers(UserObject userObject, String subject, String content);
	public void saveAHIForm(AHIDocVO aHIDocVO, BindingResult bindingResult,
			UserObject userObject);
	public void storeEditedAHIStdDocument(AHIDocVO ahiDocVO, BindingResult bindingResult, UserObject userObject);
	public int validateAHIDataWithACRF(int acrfDocId, int ahiDocId, String documentType, 
			BindingResult bindingResult, UserObject userObject);
	
	public List<AhiAlertTrackingVO> getAhiFlaggedStatenoData(UserObject userObject, BindingResult bindingResult, String followupReq, String followupComp);
	public List<Map<String, String>> getAhiAlertSrc();
	public List<Map<String, String>> getYesNo();
	public List<Map<String, String>> getActivePassive();
	public List<Map<String, String>> getAhiPrfFollowup();
	public List<Map<String, String>> getPSMessageType();
	
	public void saveAHIAlertTrackingForm(AhiAlertTrackingVO ahiAlertTrackingVO, BindingResult bindingResult, UserObject userObject);
	
	public void updateAhiAlertTracking(AhiAlertTrackingVO ahiAlertTrackingVO, UserObject userObject);
	public void insertAhiAlertFollowup(AhiAlertTrackingVO ahiAlertTrackingVO, UserObject userObject);
	
	public AhiAlertTrackingVO getAhiAlertDetails(int ahiAlertSeqId);
//	public List<AhiAlertTrackingVO> getAhiAlertUserNotesHistory(int ahiAlertSeqId);
	
	public List<AhiAlertTrackingVO> getAhiAlertData(UserObject userObject, BindingResult bindingResult, String dataType, String dataSubType);
	
	public void updateAhiAlertStatus(AhiAlertTrackingVO ahiAlertTrackingVO, UserObject userObject, BindingResult bindingResult);
	
	
}
