package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.STDConfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for STD_CONF . It provides methods for inserting and
 * retrieve back data for STD_CONF doc.
 *
 * Please see STDConfDocumentDAO.xml for its implementation.
 *
 * @author SKA03
 */
public interface STDConfDocumentDAO {

    
    /**
     * This method inserts data for TB docs in respective RAW tables.
     *
     * @param tBDocVO
     * @throws DataAccessException
     */
    //@Transactional
    //public void insertTBDocData(TBDocVO tBDocVO) throws DataAccessException;

	public int writeRawSTDConfDocDataToStdTable(Map<Object, Object> docVO);

	public PersonViewVO getStdConfDocumentForClericalReview(int documentId);

	public List<Map<String, String>> getDrugList(String refColumnName);

	public STDConfDocVO getSTDConfDoc(int docId);

	public List<Map<String, String>> getSTDConfRefData();
}
