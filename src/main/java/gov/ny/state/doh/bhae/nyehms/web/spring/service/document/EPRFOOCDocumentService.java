package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EPRFOOCDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.EPRFFollowUpDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.FileByConclusionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 *
 * This interface provides methods to interact with EPRF_OOC document.
 *
 * @author ska03
 */
public interface EPRFOOCDocumentService {

    
    
	public int writeRawePRFOOCDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);

	public EPRFOOCDocVO getEPRFOOCDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id);

	public List<JSONObject> getOOCDocumentFileNames(String fileType, int processStatus, 
			BindingResult bindingResult, UserObject userObject);

	public PersonViewVO getStdEPRFOOCDocumentForClericalReview(int documentId);

	public int writeRawePRFOOCDocData(Map<Object, Object> docVO,
			BigDecimal documentId);

	public void updateDocumentTypeForEPRFOOCDocument(String fileType, BigDecimal documentId);

	public List<Map<String, Object>> getOOCDocumentsToReview(
			String documentFileName, String documentFileType,
			BindingResult bindingResult, UserObject userObject);

	public void updateOOCDocumentReviewStatus(String documentFileName,
			String documentFileType, int processStatus, String facilityCategory,
			BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getSNPFileNames();

	public List<Map<String, Object>> loadSNPData(String documentFileName,
			BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getOOCDNLTCFileNames(String origin);

	public List<Integer> getOOCDNLTCDocumentsFromMatchReview(String documentFileName);

	public EPRFFollowUpDocVO getEprfDocData(Integer documentId);

	//public Map<String, String> getEprfEharsData(Integer documentId);

	public EPRFFollowUpDocVO getFollowUpData(String documentFileName);

	public int getDocumentsNeedToFollowUpCount(String documentFileName);

	public List<Map<String, String>> getInternalConclusionList();

	public List<Map<String, String>> getCareStatusList();

	public void updateEprfFollowUpData(EPRFFollowUpDocVO ePRFFollowUpDocVO,
			BindingResult bindingResult, UserObject userObject);

	public List<FileByConclusionVO> getPatientCareStatusInformation(
			String documentFileName);

	public void sendEmailToBureauDirector(String message,
			String documentFileName, BindingResult bindingResult,
			UserObject userObject) throws MessagingException;

	public void updateEprfFollowUpFileData(EPRFFollowUpDocVO ePRFFollowUpDocVO,
			BindingResult bindingResult, UserObject userObject);

	public Map<String, Integer> getOOCDocumentPatientInformation(
			String documentFileName);

	public List<EPRFFollowUpDocVO> getCompleteEprfDocDataWithHivInfo(
			String documentFileName);

	public Map<String, Integer> getOOCDNLTCReviewStatsByFileName(
			String documentFileName);

}
