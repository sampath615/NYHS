package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AIRSDocVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for AIRS documents. Please see AIRSDocumentDAO.xml for its
 * implementations.
 *
 * @author vxp06
 */
public interface AIRSDocumentDAO {

    /**
     * This method returns AIRS doc data fields for a given document id in the
     * form of AIRSVo object.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public AIRSDocVO getAIRSDoc(int docId) throws DataAccessException;

    /**
     * This method will insert data into AIRS tables
     *
     * @param stdAIRSDocVO
     * @throws DataAccessException
     */
    public int writeRawAIRSRecordDataToStdTable(Map<Object, Object> docVO);

	public AIRSDocVO getRawAIRSDocument(int documentID);

//  No longer used - 6/24/2020
//	public int insertRawAirsDocument(Map<Object, Object> rawDocData);

//	public int insertStdAirsDocument(Map<Object, Object> stdDocData);

	public Map<String, Object> getLastUpgradedDate(String aGENCY_ID);

	public int stopAirsNORecordsAtRaw();

	public List<Map<String, Object>> checkForActiveErrors(Map<String, Boolean> inputParameterMap);

	public List<Long> getDocumentIdsHasValidAgency();
}