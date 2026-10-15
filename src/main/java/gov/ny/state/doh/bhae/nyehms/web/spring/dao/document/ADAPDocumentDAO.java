package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ADAPDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for ADAP document. It is associated with
 * ADAPDocumentDAO.xml for the implementation of this functions.
 *
 * @author Srikar Kummamuri
 */
public interface ADAPDocumentDAO {

    /**
     * This will query the database for the given doc id and return data in form
     * of ADAPDocVO obj.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public ADAPDocVO getADAPDoc(int docId) throws DataAccessException;

	public int writeStdDocData(Map<Object, Object> stdDocData);

	public List<Map<String, String>> getSubProviderAddressesForTheNPS(
			Map<Object, Object> inputParameters);

	public int insertRawADAPDocument(Map<Object, Object> rawDocData);


	public List<String> getFileNames();

	public List<String> getFileNameNotShippedToNYC(Map<String, Object> inParameterMap);

	public List<Map<String, String>> generateNYCData(Map<String, Object> inParameterMap);

	public int updateNYCDateForTheFile(Map<String, Object> inParameterMap);

	public int uploadNYCReturnFileData(Map<String, Object> inputParameterMap);

	public List<Integer> getNCYADAPDocumentsToREview();

	public PersonRisksVO loadADAPRiskContentForNYCDocument(int documentId);

	public PersonRisksVO loadADAPRiskContentForNYEHMSDocument(int documentId);

	public int updateNYCADAPDocumentMatchStatus(
			Map<String, Object> inOutParameterMap);

	public List<String> getADAPNYCFileNames();

	public List<Map<String, String>> generateNYCFileSummary(String fileName);
	
	public List<Map<String, String>> generateADAPNYCQualityStageMatchSummary(String fileName);
	

	public int compareNYCCitynoWithNYEHMS(Map<String, Object> inputParameterMap);

	public ADAPDocVO getADAPNYCDoc(int docId);

	public List<Map<String, String>> generateADAPNYCShippedMatchSummary(
			String fileName);

	public List<Map<String, String>> generateADAPNYCDiscrepancyFile(
			Map<String, Object> inParameterMap);

	public int checkCityNoExists(String cityno);
}
