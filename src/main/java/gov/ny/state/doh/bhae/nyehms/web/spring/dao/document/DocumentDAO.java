package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is interface for DocumentDAO and contains common methods used by all the
 * docs. For the implementation of these function please see DocumentDAO.xml
 *
 * @author Srikar Kummamuri
 */
public interface DocumentDAO {
	
	/**
	 * For use after connecting to the database from outside of Spring,
	 * for instance from a stand-alone Java application.
	 * @throws DataAccessException
	 */
	@Transactional

    /**
     * This function is used to generate new doc id.
     *
     * @param map
     * @throws DataAccessException
     */
    @Transactional
    public void getNewDocId(Map<Object, Object> map)
    		throws DataAccessException;


    
    
    

	public int writeDocumentTestType(Map<Object, Object> inputOutputParameterMap);

	public void writeRidrData(Map<String, List<Map<String, Object>>> inputParameterMap);

	public List<String> getRidrStateCodesToProcess(Map<String, String> inputParameterMap);

	public List<Map<String, Object>> getRidrSearchViewResults(Map<String,String> inputParameterMap);

	public Integer getNewDocumentIdUsingParentDocument(Integer derivedDocumentId);

	public Integer getNewDocumentIdUsingMultiSpotDocument(
			Integer derivedDocumentId);

	public String checkStatenoMeetsCDC(Map<String, Object> inputParameterMap);

}
