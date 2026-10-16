package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;


import java.util.Map;


/**
 * This is interface for NYC DeathDocument.
 *  The implementation of these functions is
 * in NYCDeathDocumentDAO.xml
 *
 * @author Srikar Kummamuri
 */
public interface NycDeathDocumentDAO {

    public int writeNycStdDeathDocData(Map<Object, Object> nycStdDeathDocData);
    
    
    public int insertNycRawDTHDocument(Map<Object, Object> rawDocData);

    public Map<String, String> getFacilityInfoFromPFI(String pfi);

    public Map<String, String> getFacilityInfoFromNycFacCode(String nycFacCode);
    
//    public Map<Object, Object> getNycFacilityInfoFromPFIorNycFacCode(Map<Object, Object> params);
    
    
}
