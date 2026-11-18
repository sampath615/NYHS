package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.StatusEmailProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.AidaProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.MailProperties;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ursdb.AIDADataDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.AIRSDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AIRSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.AirsDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftDetailsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.RawRecordStatusService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;
import lombok.RequiredArgsConstructor;

/**
 * The AIRS records: the AIRS document screen, the import that validates a received record (RAW_AIRS_DOC) and
 * standardizes it, the AIDA data shown with it, and the scheduler's agency checks.
 *
 * @author vxp06
 */
@Service
@RequiredArgsConstructor
public class AIRSDocumentService {

	private static Logger log = LoggerFactory.getLogger(AIRSDocumentService.class);
	private final StatusEmailProperties statusEmailProperties;
	private final AidaProperties aidaProperties;
	private final MailProperties mailProperties;
	private static final String DOCUMENT_TYPE = "AIRS";
	/** The worker id of the scheduler. */
	private static final int SCHEDULER_WORKER_ID = 900;
	/** The date format of DT_CREATED and DT_MODIFIED. */
	private static final String AIRS_DATE_FORMAT = "MMddyyyy";

	/** The recoded risk columns and the received field each comes from. */
	private static final String[][] RISKS = {{"SWM", "RISK01"}, {"SWF", "RISK02"}, {"IDU", "RISK03"}, {"SEX_PARTNER_IDU", "RISK04"},
			{"SEX_PARTNER_HIV", "RISK05"}, {"RECV_BLOOD_PROD", "RISK06"}, {"PERINATAL", "RISK07"}, {"RISK_OTHER", "RISK08"},
			{"RISK_UNKNOWN", "RISK09"}};
	/** Race flags and the detail field that sets the flag when the flag itself is not set (null: none). */
	private static final String[][] RACES = {{"RACE_INDIAN", null}, {"RACE_BLACK", "BLACK_DET"}, {"RACE_ASIAN", "ASIAN_DET"},
			{"RACE_PACIFIC", "HAWAIIAN_DET"}, {"RACE_WHITE", "WHITE_DET"}, {"RACE_OTHER", null}, {"RACE_UNKNOWN", null}};
	/** Yes/no fields recoded like the race flags. */
	private static final String[] YES_NO_FIELDS = {"I_ILL", "INFORMED_OF_DIAG", "PREV_I_ILL", "D_DIAG", "PREV_D_DIAG"};

	private final AIRSDocumentDAO airsDocumentDAO;
	private final AirsDocRepository airsDocRepository;
	private final CommonService commonService;
	private final AIDADataDAO aIDADataDAO;
	private final RawRecordStatusService rawRecordStatusService;
	/** Resolved when used: LaftDetailsService depends on this service through the scheduler helpers. */
	private final ObjectProvider<LaftDetailsService> laftDetailsService;

	/**
	 * An AIRS record for the document screen, with its form version: "NEW" for a 9.x version other than 9.0.4 and
	 * 9.0.5, else "OLD".
	 */
	public AirsDocData getAIRSDocument(int documentID) {
		AirsDocData document = airsDocRepository.findDocument(documentID);
		return document.withAirsVerType(isNewAirsVersion(document.stdAirsDoc().getAirsVersion()) ? "NEW" : "OLD");
	}

	private static boolean isNewAirsVersion(String airsVersion) {
		if (!CommonUtil.isNotNullAndEmpty(airsVersion)) {
			return false;
		}
		String digits = airsVersion.replace(".", "");
		int version = Integer.parseInt(digits);
		return digits.startsWith("9") && version != 904 && version != 905;
	}

	/** An AIRS record as received. */
	public AIRSDocVO getRawAIRSDocument(int documentID) {
		return airsDocumentDAO.getRawAIRSDocument(documentID);
	}

	/**
	 * Validates a received AIRS record and acts on the outcome: a delete record deactivates the document, a "Z"
	 * record stops at the raw layer, a valid record is standardized, an invalid one gets its validation error.
	 *
	 * @param docVO the received record (a RAW_AIRS_DOC row), value per column name; recoded in place when valid
	 * @return the number of standardized rows written (0 if the record was not standardized)
	 * @throws DataRetrievalFailureException if a status could not be written
	 */
	public int validateAndWriteRawAIRSRecordDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {
		List<String> errors = new ArrayList<>();
		BigDecimal existingProcessStatus = (BigDecimal) docVO.get("CURRENT_PROCESS_STATUS");
		int processStatus = validateAirsRecord(docVO, errors);

		if (processStatus == CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS) {
			if (rawRecordStatusService.makeDocumentAsInactivate(documentId.intValue()) == 0
					|| rawRecordStatusService.writeErrorCode(processStatus, documentId, DOCUMENT_TYPE) == 0) {
				throw new DataRetrievalFailureException("Unable to delete the document " + documentId);
			}
			return 0;
		}
		if (processStatus == CommonConstants.STOPPED_AT_RAW_LAYER_PROCESS_STATUS) {
			writeStatus(processStatus, documentId, "Unable to update the 18 status for the document ");
			return 0;
		}
		if (processStatus == CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS) {
			return writeRawAIRSRecordDataToStdTable(docVO);
		}
		String errorDesc = errors.toString().replace("[", "").replace("]", "");
		rawRecordStatusService.addValidationError(documentId, errorDesc, processStatus, false, existingProcessStatus, DOCUMENT_TYPE);
		writeStatus(processStatus, documentId, "Unable to update the validation error status for the document  ");
		return 0;
	}

	/** Sets the record's process status; throws with the message (and the document id) if no row was updated. */
	private void writeStatus(int processStatus, BigDecimal documentId, String failureMessage) {
		if (rawRecordStatusService.writeErrorCode(processStatus, documentId, DOCUMENT_TYPE) == 0) {
			throw new DataRetrievalFailureException(failureMessage + documentId);
		}
	}

	/**
	 * Recodes a valid received record in place and writes its standardized record (STD_AIRS_DOC).
	 *
	 * @return the number of rows written
	 */
	private int writeRawAIRSRecordDataToStdTable(Map<Object, Object> docVO) {
		docVO.put("SSN", CommonUtil.formatSSNValue(text(docVO, "SSN")));

		for (String[] risk : RISKS) {
			recode(docVO, risk[0], risk[1], LabCommonUtils::checkRiskData);
		}
		for (String[] race : RACES) {
			flagWithDetail(docVO, race[0], race[1], "0", "1");
		}
		for (String field : YES_NO_FIELDS) {
			recode(docVO, field, field, LabCommonUtils::checkRaceData);
		}

		String sex = text(docVO, "SEX");
		docVO.put("SEX", LabCommonUtils.checkBirthSexData(sex));
		docVO.put("GENDER", LabCommonUtils.checkCurrentGenderData(text(docVO, "GENDER")));

		String ethnicity = text(docVO, "ETHNICCD");
		docVO.put("ETHNICCD", LabCommonUtils.checkEthnicityData(ethnicity));
		if ((CommonUtil.isNullOrEmpty(ethnicity) || ethnicity.equals("09")) && CommonUtil.isNotNullAndEmpty(text(docVO, "HISPANIC_DET"))) {
			docVO.put("ETHNICCD", "01");
		}

		docVO.put("CURRENT_PROCESS_STATUS", CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
		docVO.put("EVER_TEST_NEGATIVE", LabCommonUtils.checkTestDataValues(text(docVO, "EVER_TEST_NEGATIVE")));
		docVO.put("FNAME", CommonUtil.NameValidation(text(docVO, "FNAME")));
		docVO.put("MNAME", CommonUtil.NameValidation(text(docVO, "MNAME")));
		docVO.put("LNAME", CommonUtil.NameValidation(text(docVO, "LNAME")));
		docVO.put("ADDRESS", CommonUtil.addressValidation(text(docVO, "ADDRESS")));
		docVO.put("MEDRECNO", CommonUtil.normalizeString(text(docVO, "MEDRECNO")));
		docVO.put("MEDICAID_NUMBER", CommonUtil.checkMedicaidNumberFormat(text(docVO, "MEDICAIDNO")));
		docVO.put("RECV_BLOOD_PROD_DATE_TEXT", text(docVO, "DATE_RECV_BLD_PROD"));

		// a birth country given only as text is "other"
		String birthCountry = text(docVO, "COUNTRY_BIRTH");
		String birthCountryOther = text(docVO, "BIRTH_COUNTRY");
		docVO.put("BIRTH_COUNTRY", CommonUtil.isNullOrEmpty(birthCountry) && CommonUtil.isNotNullAndEmpty(birthCountryOther) ? "OTH" : birthCountry);
		docVO.put("BIRTH_COUNTRY_OTHER", birthCountryOther);

		String sexAtBirth = text(docVO, "SEX_AT_BIRTH");
		docVO.put("BIRTH_SEX", LabCommonUtils.checkBirthSexData(CommonUtil.isNullOrEmpty(sexAtBirth) ? sex : sexAtBirth));

		String age = text(docVO, "AGE");
		docVO.put("AGE", CommonUtil.isNotNullAndEmpty(age) && CommonUtil.isNumeric(age.trim()) ? age : null);

		docVO.put("GREATER_DATE", greaterDate(docVO));

		int resultCount = airsDocumentDAO.writeRawAIRSRecordDataToStdTable(docVO);
		if (resultCount == 0) {
			throw new DataRetrievalFailureException("Unable to store the document in STD AIRS table  " + docVO.get("DOCUMENT_ID"));
		}
		return resultCount;
	}

	/**
	 * GREATER_DATE: the later of DT_CREATED and DT_MODIFIED (the one given if only one is; none if neither is).
	 */
	private static Date greaterDate(Map<Object, Object> docVO) {
		try {
			Date created = airsDate(text(docVO, "DT_CREATED"));
			Date modified = airsDate(text(docVO, "DT_MODIFIED"));
			if (created == null) {
				return modified;
			}
			if (modified == null) {
				return created;
			}
			return created.before(modified) ? modified : created;
		} catch (ParseException e) {
			throw new DataRetrievalFailureException("Unable to parse the Dates the documentId  " + docVO.get("DOCUMENT_ID"));
		}
	}

	/** An AIRS date (MMddyyyy, parsed leniently as before), or null. */
	private static Date airsDate(String text) throws ParseException {
		return text == null ? null : new Date(new SimpleDateFormat(AIRS_DATE_FORMAT).parse(text.trim()).getTime());
	}

	/** Replaces a column by the recoded value of a received field. */
	private static void recode(Map<Object, Object> docVO, String column, String field, UnaryOperator<String> code) {
		docVO.put(column, code.apply(text(docVO, field)));
	}

	/**
	 * Recodes a flag (LabCommonUtils.checkRaceData); when the received flag is empty or {@code unset} and the detail
	 * field is filled in, the flag becomes {@code set}.
	 */
	private static void flagWithDetail(Map<Object, Object> docVO, String flag, String detailField, String unset, String set) {
		String received = text(docVO, flag);
		docVO.put(flag, LabCommonUtils.checkRaceData(received));
		if (detailField != null && (CommonUtil.isNullOrEmpty(received) || received.equals(unset))
				&& CommonUtil.isNotNullAndEmpty(text(docVO, detailField))) {
			docVO.put(flag, set);
		}
	}

	private static String text(Map<Object, Object> docVO, String field) {
		return (String) docVO.get(field);
	}

	/**
	 * The process status of a received record: delete and "Z" records first, then the first error in this order:
	 * agency not in the reference table, last name, TC id, agency id, create date, URN; else valid. Every error found
	 * is added to {@code errors}.
	 */
	private int validateAirsRecord(Map<Object, Object> docVO, List<String> errors) {
		String recordType = text(docVO, "RECORD_TYPE");
		if (recordType != null && "D".equalsIgnoreCase(recordType.trim())) {
			return CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS;
		}
		if (recordType != null && "Z".equalsIgnoreCase(recordType.trim())) {
			return CommonConstants.STOPPED_AT_RAW_LAYER_PROCESS_STATUS;
		}

		boolean hasAgencyIdError = false;
		boolean hasAgencyReferenceTableError = false;
		String agencyId = text(docVO, "AGENCY_ID");
		if (CommonUtil.isNullOrEmpty(agencyId)) {
			errors.add("Agency Id is required");
			hasAgencyIdError = true;
		} else if (airsDocumentDAO.getLastUpgradedDate(agencyId) == null) {
			errors.add("Agency Id not in the reference table : " + agencyId);
			hasAgencyReferenceTableError = true;
		}

		boolean hasLnameError = required(docVO, "LNAME", "Last name is required", errors);
		boolean hasTcIdError = required(docVO, "TC_ID", "TC Id is required", errors);
		boolean hasUrnError = required(docVO, "URN", "URN is required", errors);

		// Changed on 9/26/24 to process the missing 1471 records on request from Ron. A record without CTR_FLAG fails
		// here (NullPointerException), as before.
		String ctrFlag = text(docVO, "CTR_FLAG");
		boolean hasDateError = !(ctrFlag.equals("1") && recordType.equals("U"))
				&& CommonUtil.isNullOrEmpty(text(docVO, "DT_CREATED")) && CommonUtil.isNullOrEmpty(text(docVO, "DT_MODIFIED"));
		if (hasDateError) {
			errors.add("Create Date is required");
		}

		if (hasAgencyReferenceTableError) {
			return CommonConstants.AIRS_AGENCY_NOT_IN_REFERNCE_TABLE;
		}
		if (hasLnameError) {
			return CommonConstants.AIRS_LNAME_NOT_AVAILABE;
		}
		if (hasTcIdError) {
			return CommonConstants.AIRS_TC_ID_NOT_AVAILABLE;
		}
		if (hasAgencyIdError) {
			return CommonConstants.AIRS_AGENCY_NOT_AVAILABE;
		}
		if (hasDateError) {
			return CommonConstants.AIRS_DATE_CREATED_NOT_AVAILABLE;
		}
		if (hasUrnError) {
			return CommonConstants.AIRS_URN_NOT_AVAILABLE;
		}
		return CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS;
	}

	/** Adds the error if the field is empty; returns whether it was. */
	private static boolean required(Map<Object, Object> docVO, String field, String error, List<String> errors) {
		if (CommonUtil.isNullOrEmpty(text(docVO, field))) {
			errors.add(error);
			return true;
		}
		return false;
	}

	/**
	 * Stops the received AIRS records that hold no data (TC_ID = 'NO_RECORDS' or URN = 'NO_RECORD') at the raw
	 * layer (process status 18), and reports it in the scheduler status.
	 */
	@SuppressWarnings("unchecked")
	public void stopAirsNORecordsAtRaw(List<JSONObject> schedulerStatusObject) {
		JSONObject statusObject = new JSONObject();
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Checking AIRS 'NO Records' data in Raw table");
		int resultCount = airsDocumentDAO.stopAirsNORecordsAtRaw();
		statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_SUCCESS);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, "Successfully checked the NO Records data in the Raw AIRS table");
		statusObject.put("No Records Documents count", resultCount);
		schedulerStatusObject.add(statusObject);
	}

	/**
	 * The AIDA data of a client, from the table and columns configured for the parameter
	 * ({@code <parameterName>_TABLE}, {@code <parameterName>_COLUMNS}).
	 *
	 * @throws DataRetrievalFailureException if the data could not be read
	 */
	public List<Map<String, String>> loadAirsAIDAData(String tcId, String parameterName, UserObject userObject) {
		try {
			AidaProperties.View aidaView = aidaProperties.view(parameterName);
			String tableName = aidaView.table();
			String columns = aidaView.columns();
			Map<String, String> inputParameterMap = new HashMap<>();
			inputParameterMap.put("tableName", tableName);
			inputParameterMap.put("columns", columns);
			inputParameterMap.put("tc_id", tcId);
			return aIDADataDAO.loadAirsAIDAData(inputParameterMap);
		} catch (RuntimeException | Error e) {
			log.error("Failed to fetch the data from AIDA table  {}", e.getMessage());
			throw new DataRetrievalFailureException("Failed to fetch the data from AIDA table  " + e.getMessage());
		}
	}

	/** Emails the agency status summary if there are active agency errors (of today only, if asked). */
	public void checkForActiveErrors(Boolean checkForToday) {
		Map<String, Boolean> inputParameterMap = new HashMap<>();
		inputParameterMap.put("checkForToday", checkForToday);
		List<Map<String, Object>> errorList = airsDocumentDAO.checkForActiveErrors(inputParameterMap);
		if (!errorList.isEmpty()) {
			commonService.sendStatusSummary(errorList, statusEmailProperties.airsAgency(), "AIRS_AGENCY", mailProperties.airsAgency().subject(), mailProperties.airsAgency().bodyMsg());
		}
	}

	/** Reprocesses the documents stopped for an unknown agency whose agency is now in the reference table. */
	public void repriseAgencyErrorDocuments() {
		List<Long> documentIds = airsDocumentDAO.getDocumentIdsHasValidAgency();
		if (documentIds != null && !documentIds.isEmpty()) {
			UserObject scheduler = new UserObject();
			scheduler.setWorkerId(SCHEDULER_WORKER_ID);
			laftDetailsService.getObject().updateDocumentsStatus(documentIds, CommonConstants.AIRS_AGENCY_NOT_IN_REFERNCE_TABLE, "AGENCY ID added", "reprise",
					DOCUMENT_TYPE, null, scheduler);
		}
	}
}
