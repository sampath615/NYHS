package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.BlackBoxDocVO;

/**
 * This is interface for Black Box documents. Please see BlackBoxDocDAO.xml for its
 * implementations.
 *
 * @author ska03
 */
public interface BlackBoxDocDAO {

    public BlackBoxDocVO getBlackBoxDoc(int docId) throws DataAccessException;
    
	public List<Map<String, String>> getBlackBoxRefData();

}