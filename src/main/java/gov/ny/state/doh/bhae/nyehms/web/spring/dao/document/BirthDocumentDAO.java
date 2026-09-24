package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.Map;

/**
 * This is interface for Birth document . It provides methods for inserting and
 * retrieve back data for BTH documents.
 *
 * Please see BirthDocumentDAO.xml for its implementation.
 *
 * @author ska03
 */
public interface BirthDocumentDAO {

	Map<String, String> getMomData(String birthMatchId);

	int writeStdBirthMomData(Map<String, Object> inputParameterMap);
	
	int writeStdBirthInfantData(Map<String, Object> inputParameterMap);

	int isDuplicateDocumentHasAnyMatches(String documentId);

	int birthMomDocumentExists(String birthMatchId);

	int updateBirthMomDocumentMatchResults(Map<String, Object> documentMatchData);

	int birthInfantDocumentExists(String birthMatchId);

	int updateBirthInfantDocumentMatchResults(Map<String, Object> documentMatchData);

	Map<String, String> getInfantData(String birthMatchId);


}
