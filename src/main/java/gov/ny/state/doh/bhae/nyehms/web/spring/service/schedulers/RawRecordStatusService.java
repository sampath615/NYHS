package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms.SchedulerJobsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;
import lombok.RequiredArgsConstructor;

/**
 * The process status of received (raw) records: setting the status, recording a validation error, deactivating a
 * document. A bean of its own, so that the document services can use it without depending on
 * {@link ManualQuartzJobService} (which depends on them through the import).
 */
@Service
@RequiredArgsConstructor
public class RawRecordStatusService {

	private final SchedulerJobsDAO schedulerJobsDAO;

	/**
	 * Sets CURRENT_PROCESS_STATUS of a received record.
	 *
	 * @param statusCode the new process status
	 * @param documentId the record's document id
	 * @param documentType its document type (selects the raw table)
	 * @return the number of rows updated
	 */
	public int writeErrorCode(int statusCode, Number documentId, String documentType) {
		Map<Object, Object> inputOutputParamters = new HashMap<>();
		inputOutputParamters.put("rawTableName", LabCommonUtils.getRawTableName(documentType));
		inputOutputParamters.put("statusCode", statusCode);
		inputOutputParamters.put("documentId", documentId);
		return schedulerJobsDAO.writeErrorCode(inputOutputParamters);
	}

	/**
	 * Marks a document as inactive.
	 *
	 * @return the number of rows updated
	 */
	public int makeDocumentAsInactivate(int documentId) {
		return schedulerJobsDAO.makeDocumentAsInactivate(documentId);
	}

	/**
	 * Records a validation error of a received record.
	 *
	 * @param errors the error text
	 * @param currentProcessStatus the status the record gets (a proficiency error is recorded as inactive)
	 * @param havingSoftCheckValidationErrors whether the errors are soft checks (reprise flag S) or hard (H)
	 * @return the id of the recorded error
	 * @throws DataRetrievalFailureException if nothing was recorded
	 */
	public int addValidationError(Number documentId, String errors, int currentProcessStatus,
			boolean havingSoftCheckValidationErrors, Number previousProcessStatus, String documentType) {
		Map<Object, Object> inputOutputParamters = new HashMap<>();
		inputOutputParamters.put("currentProcessStatus", currentProcessStatus);
		inputOutputParamters.put("errorMessage", errors);
		inputOutputParamters.put("documentId", documentId);
		inputOutputParamters.put("activeFlag", currentProcessStatus == CommonConstants.RAW_RECORD_PROFICIENCY_ERROR_PROCESS_STATUS ? 'N' : 'Y');
		inputOutputParamters.put("createdId", "NYEHMS");
		inputOutputParamters.put("previousProcessStatus", previousProcessStatus);
		inputOutputParamters.put("documentType", documentType);
		inputOutputParamters.put("repiseFlag", havingSoftCheckValidationErrors ? "S" : "H");

		if (schedulerJobsDAO.addValidationError(inputOutputParamters) == 0) {
			throw new DataRetrievalFailureException("Failed to add the validation error for ");
		}
		int processErrorId = ((BigDecimal) inputOutputParamters.get("processErrorId")).intValue();
		if (processErrorId == 0) {
			throw new DataRetrievalFailureException("Failed to add the validation error, unable to get the ErrorId for ");
		}
		return processErrorId;
	}
}
