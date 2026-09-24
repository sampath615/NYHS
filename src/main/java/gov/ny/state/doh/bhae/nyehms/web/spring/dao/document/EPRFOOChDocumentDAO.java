package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EPRFOOCDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.EPRFFollowUpDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.FileByConclusionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

/**
 * This is interface for EPRF OOCDocument.
 *  The implementation of these functions is
 * in EPRFOOChDocumentDAO.xml
 *
 * @author ska03
 */
public interface EPRFOOChDocumentDAO {

	public int writeStdDocData(Map<Object, Object> docVO);

	public EPRFOOCDocVO getEPRFOOCDoc(int docId);

	public List<JSONObject> getOOCDocumentFileNames(Map<String, Object> inputParameterMap);

	public PersonViewVO getStdEPRFOOCDocumentForClericalReview(
			int documentId);

	public List<Map<String, Object>> getOOCDocumentsToReview(
			Map<String, String> inputParameterMap);

	public int updateOOCDocumentReviewStatus(
			Map<String, Object> inputParameterMap);

	public List<Map<String, String>> getSNPFileNames();

	public List<Map<String, Object>> loadSNPData(String documentFileName);

	public int updateOOCFacilityCategory(Map<String, Object> inputParameterMap);

	public List<Map<String, String>> getOOCDNLTCFileNames(Map<String, String> inputParameter);

	public List<Integer> getOOCDNLTCDocumentsFromMatchReview(
			String documentFileName);

	public EPRFFollowUpDocVO getEprfDocData(Integer documentId);

	//public Map<String, String> getEprfEharsData(Integer documentId);

	public EPRFFollowUpDocVO getFollowUpData(String documentFileName);

	public int getDocumentsNeedToFollowUpCount(String documentFileName);
	
	public int updateFileMatchReviewDate(long documentId);

	public List<Map<String, String>> getInternalConclusionList();

	public List<Map<String, String>> getCareStatusList();

	public int updateEprfFollowUpData(EPRFFollowUpDocVO ePRFFollowUpDocVO);

	public List<FileByConclusionVO> getPatientCareStatusInformation(
			String documentFileName);

	public int updateBureauDirectorEmailDate(String documentFileName);

	public int updateEprfFollowUpFileData(EPRFFollowUpDocVO ePRFFollowUpDocVO);

	public void updateFileFollowUpCompleteDate(String filename);

	public Map<String, Integer> getOOCDocumentPatientInformation(String documentFileName);

	public List<EPRFFollowUpDocVO> getCompleteEprfDocDataWithHivInfo(
			String documentFileName);

	public List<String> getAllFileNamesByFileType(String documentFileType);

	public Map<String, Integer> getOOCDNLTCReviewStatsByFileName(
			String documentFileName);

	
}
