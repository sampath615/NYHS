package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DeathDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for DeathDocument. The implementation of these functions is
 * in DeathDocumentDAO.xml
 *
 * @author Srikar Kummamuri
 */
public interface DeathDocumentDAO {

    /**
     * This will return death doc for a given doc id.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public DeathDocVO getDeathDoc(int docId) throws DataAccessException;

    public int writeStdDocData(Map<Object, Object> stdDocData);
    
    public PersonViewVO getStdDeathDocumentForClericalReview(int documentId);
    
    public String getFacilityName(String inst);
    
    public int insertRawDTHDocument(Map<Object, Object> rawDocData);

    public Map<String, String> getFacilityNameByPFI(String pfi);
    
    public String getNysFipsCounty(String nysCounty);

}
