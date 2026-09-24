package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AIRSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

/**
 * This interface provides methods to interact with AIRS document.
 *
 * @author ska03
 */
public interface AIRSDocumentService {

    /**
     * This method will return the AIRS document for a given document id.
     *
     * @param documentID
     * @return
     */
    public AIRSDocVO getAIRSDocument(int documentID);

	public int writeRawAIRSRecordDataToStdTable(Map<Object, Object> docVO);
	
	public int validateAndWriteRawAIRSRecordDataToStdTable
		(Map<Object, Object> docVO,
		 BigDecimal documentId);

	public int validateAirsRecord(Map<Object, Object> docVO,
			List<String> errors);

	public AIRSDocVO getRawAIRSDocument(int documentID);

	public PersonViewVO getStdAIRSDocumentForClericalReview(int documentId);

	/**
	 * 
	 * Stopping Raw AIRS documents at Raw layer only with Current process status as 18
	 * 
	 * CRITERIA :  TC_ID = 'NO_RECORDS' or URN = 'NO_RECORD'
	 * @param schedulerStatusObject
	 */
	public void stopAirsNORecordsAtRaw(List<JSONObject> schedulerStatusObject);

	public List<Map<String, String>> loadAirsAIDAData(String tc_id, String parameterName, UserObject userObject);

	public void checkForActiveErrors(Boolean checkForToday);

	public void repriseAgencyErrorDocuments();

}
