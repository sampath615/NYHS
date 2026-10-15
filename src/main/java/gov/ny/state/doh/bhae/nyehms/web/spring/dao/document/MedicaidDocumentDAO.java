package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.InsDeathDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocArvsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocProvidersVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for TB document . It provides methods for inserting and
 * retrieve back data for TB doc.
 *
 * Please see TBDocumentDAO.xml for its implementation.
 *
 * @author Srikar Kummamuri
 */
public interface MedicaidDocumentDAO {

    /**
     * This method returns data for TB document for a given document ID.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public MedicaidDocVO getMedicaidDoc(int docId) throws DataAccessException;
    
    public List<MedicaidDocArvsVO> getArvsForMedicaidDoc(int docId) throws DataAccessException;
    
    public List<MedicaidDocProvidersVO> getProvidersListForMedicaidDoc(int docId) throws DataAccessException;

	public int writeStdDocData(Map<Object, Object> stdDocData);

	public int storeRawMedicaidProvidersDataInStd(int intValue);

	public int insertRawMedDocument(Map<Object, Object> rawDocData);
	
	public int insertRawMedProvidersData(Map<Object, Object> rawDocData);

	public Map<Object, Object> getRawMedicaidDocAndProvidersData(int documentId);

	public Map<Object, Object> getStdMedicaidDocAndProvidersData(int documentId);

	public void insertRawMedicaidArvsData(Map<Object, Object> rawDocData);

	public InsDeathDocVO getInsDthDoc(int docId);

	
	
}
