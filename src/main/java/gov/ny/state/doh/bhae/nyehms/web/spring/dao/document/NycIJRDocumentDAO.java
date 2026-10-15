package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycIJRDocVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * This class provides methods for processing NYC-IJR document.
 *
 * @author vxp06
 */
public interface NycIJRDocumentDAO {

    /**
     * This function will accept a parameter documentId and will query
     *
     * @param documentId
     * @return
     * @throws DataAccessException
     */
    public NycIJRDocVO getNycIJRDocument(int docId) throws DataAccessException;

	public int writeStdDocData(Map<Object, Object> docVO);

	public List<String> getFileNames();

}
