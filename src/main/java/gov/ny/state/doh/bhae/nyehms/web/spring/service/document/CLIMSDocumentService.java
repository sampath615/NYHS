package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.ClimsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.ClimsDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author skg02
 *
 */
@RequiredArgsConstructor
@Service
public class CLIMSDocumentService {

    private final CommonExceptionHandler commonExceptionHandler;
    /**
     * This method will return CLIMS document for a given document id
     * {@inheritDoc}
     *
     * @param documentID
     * @return
     */
    
    private final ClimsDocRepository climsDocRepository;
    
	public ClimsDocData getCLIMSDocument(int documentID) throws DataAccessException {
		return climsDocRepository.findDocument(documentID);
	}
	

}
