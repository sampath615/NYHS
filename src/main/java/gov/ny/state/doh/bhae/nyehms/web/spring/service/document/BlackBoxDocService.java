package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxRefData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.BlackBoxDocRepository;

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
    BlackBoxDocRepository blackBoxDocRepository;
 
    /**
     * {@inheritDoc}
     *
     * @param documentId
     * @return
     */
    public BlackBoxDocData getBlackBoxDoc(int docId) {
    	return blackBoxDocRepository.findDocument(docId);
    }
    
    
	public List<BlackBoxRefData> getBlackBoxRefData(){
		return blackBoxDocRepository.findRefData();
	}

}
