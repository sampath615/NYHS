package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ADAPDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * This is interface for ADAP document service. This provides methods to
 * interact with ADAP document.
 *
 * @author vxp06
 */
public interface ADAPDocumentService {

    /**
     * This returns the ADAP doc for a given doc id.
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public ADAPDocVO getADAPDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

	public int writeRawADAPRecordDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);

	public PersonViewVO getStdADAPDocumentForClericalReview(int documentId);

	public List<String> getFileNames();

	public List<String> getFileNameNotShippedToNYC(String nycShipStatus);

	public List<Map<String, String>> generateADAPNYCFile(String fileName, String nycShiStatus,
			BindingResult bindingResult, UserObject userObject);

	public void uploadNYCReturnFileData(MultipartFile uploadedFile,
			BindingResult bindingResult, UserObject userObject)throws IOException;

	public PersonViewVO getNYCADAPDocumentForClericalReview(int documentId);

	public Map<String, Object> getADAPDocumentIdToReview(
			BindingResult bindingResult, UserObject userObject);

	public PersonRisksVO loadADAPRiskContent(int documentId, String origin,
			BindingResult bindingResult, UserObject userObject, String id);

	public void updateNYCADAPDocumentMatchStatus(int documentId,
			String reviewCd, String cityno, String comments, BindingResult bindingResult, UserObject userObject);

	public List<String> getADAPNYCFileNames();

	public List<Map<String, String>> generateNYCFileSummary(String fileName,
			BindingResult bindingResult, UserObject userObject);
	
	public List<Map<String, String>> generateADAPNYCQualityStageMatchSummary(String fileName,
			BindingResult bindingResult, UserObject userObject);

	public ADAPDocVO getADAPNYCDoc(int docId, BindingResult dummyBindingResult,
			UserObject userObject, String id);

	public List<Map<String, String>> generateADAPNYCShippedMatchSummary(
			String fileName, BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> generateADAPNYCDiscrepancyFile(
			String fileName, BindingResult bindingResult, UserObject userObject);

	public void checkCityNoExists(String cityno, BindingResult bindingResult,
			UserObject userObject);
	
	public void loadReferenceVariables();

	public void resetDocumentReferenceVariables();
	

}
