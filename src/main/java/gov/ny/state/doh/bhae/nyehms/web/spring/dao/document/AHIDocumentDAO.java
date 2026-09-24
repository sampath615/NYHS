package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHICiStiVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHINegInd900TestHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AHIAlertPSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AhiAlertTrackingVO;

public interface AHIDocumentDAO {
	
	public List<Map<String, String>> getAHICISTIConditionList();
	public List<Map<String, String>> getAHICIReasonPatientTestedList();
	public List<Map<String, String>> getAHINegInd900TestTypeList();
	
	public int insertAHIDocData(AHIDocVO ahiDocVO)throws DataAccessException;
	public int insertAHICiStiData(Map<String, Object> inputParameterMap)throws DataAccessException;
	public int insertAHINegInd900TestHistoryData(Map<String, Object> inputParameterMap)throws DataAccessException;
	public int changeAhiFlagStatusForAssignment(
			Map<String, Object> inputParameterMap);
	
    public AHIDocVO getAHIDoc(int documentId) throws DataAccessException;
    public List<AHICiStiVO> getCiStiConditionData(int documentId) throws DataAccessException;
    public List<AHINegInd900TestHistoryVO> getNegInd900TestHistoryData(int documentId) throws DataAccessException;
	public int validateAHIDataWithACRF(Map<String, Object> inputParameterMap);
	public int checkAhiFlagEligibility(int assignmentId);

	public List<AhiAlertTrackingVO> getAhiFlaggedStatenoData() throws DataAccessException;
	public List<Map<String, String>> getAhiAlertSrc();
	public List<Map<String, String>> getYesNo();
	public List<Map<String, String>> getActivePassive();
	public List<Map<String, String>> getAhiPrfFollowup();
	public List<Map<String, String>> getPSMessageType();
	
	public int updateAhiAlertTrackingData(Map<String, Object> inputParameterMap);
	public int insertAhiAlertTrackingFollowupData(Map<String, Object> inputParameterMap);
	
	public AhiAlertTrackingVO getAhiAlertDetails(int ahiAlertSeqId);
//	public List<AhiAlertTrackingVO> getAhiAlertUserNotesHistory(int ahiAlertSeqId);
	public List<AHIAlertPSMessageVO> getAHIAlertPSMessageInfo(String stateno) throws DataAccessException;
	
	public int updateAhiAlertStatus(Map<String, Object> inputParameterMap);
}
