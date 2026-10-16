package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;


import java.util.Map;


/**
 * This is interface for DeathDocument. The implementation of these functions is
 * in DeathDocumentDAO.xml
 *
 * @author Srikar Kummamuri
 */
public interface DeathDocumentDAO {

    public int writeStdDocData(Map<Object, Object> stdDocData);
    
    public String getFacilityName(String inst);
    
    public int insertRawDTHDocument(Map<Object, Object> rawDocData);

    public Map<String, String> getFacilityNameByPFI(String pfi);
    
    public String getNysFipsCounty(String nysCounty);

}
