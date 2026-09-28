package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.BlackBoxDocDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.BlackBoxDocVO;

/**
 * This is implementation of BlackBoxDocService interface. This provides
 * implementation of the methods to interact with Black Box document.
 *
 * @author ska03
 */
@Component
public class BlackBoxDocService {

    //private Log log = LogFactory.getLog(this.getClass());
 
	@Autowired
    BlackBoxDocDAO blackBoxDocDAO;
 
    /**
     * {@inheritDoc}
     *
     * @param documentId
     * @return
     */
    public BlackBoxDocVO getBlackBoxDoc(int docId) {
    	BlackBoxDocVO blackBoxDocVO = blackBoxDocDAO.getBlackBoxDoc(docId);
        
        return blackBoxDocVO;
    }
    
    
	public List<Map<String, String>> getBlackBoxRefData(){
		return blackBoxDocDAO.getBlackBoxRefData();
	}

}
