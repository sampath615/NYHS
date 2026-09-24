package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.Map;

/**
 * This provides methods for interacting with Birth document.
 *
 * @author ska03
 */
public interface BirthDocumentService {

	int insertRawBirthMomDataToStdTable(int bthDocumentId, String birthMatchId, Map<String, Object> documentMatchData);

	int isDuplicateDocumentHasAnyMatches(String documentId);

	int birthDocumentExists(String birthMatchId, String documentType);

	int updateBirthDocumentMatchResults(Map<String, Object> documentMatchData, String documentType);

	int insertRawBirthInfantDataToStdTable(int bthDocumentId, String birthMatchId,
			Map<String, Object> documentMatchData);

  
    
}
