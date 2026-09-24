package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycDeathDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for NYC DeathDocument.
 *  The implementation of these functions is
 * in NYCDeathDocumentDAO.xml
 *
 * @author Srikar Kummamuri
 */
public interface NycDeathDocumentDAO {

    /**
     * This will return NYC death doc for a given doc id.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public NycDeathDocVO getNycDeathDoc(int docId) throws DataAccessException;
    
    public int writeNycStdDeathDocData(Map<Object, Object> nycStdDeathDocData);
    
    public PersonViewVO getNycStdDeathDocumentForClericalReview(int documentId);
    
    public String getProvName(String inst);
    
    public int insertNycRawDTHDocument(Map<Object, Object> rawDocData);

    public Map<String, String> getFacilityInfoFromPFI(String pfi);

    public Map<String, String> getFacilityInfoFromNycFacCode(String nycFacCode);
    
//    public Map<Object, Object> getNycFacilityInfoFromPFIorNycFacCode(Map<Object, Object> params);
    
    public String getNycFipsCounty(String nycGazCounty);
    
}
