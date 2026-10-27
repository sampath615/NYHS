package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.ADAPDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CommonDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DoccsDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.LabDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MedicaidDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.PRFDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.TBDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYCLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycEharsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedStateRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.DeathDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycDeathDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycEharsDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawDeathDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawNycDeathDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;
import lombok.RequiredArgsConstructor;

/**
 * Operations shared by the document types: code lists, the document locator and person associations, raw and
 * standardized document status, copies of a document (raw and standardized rows), new document ids, and the state
 * lookup.
 */
@Service
@RequiredArgsConstructor
public class CommonDocumentService {

	private static final Logger log = LoggerFactory.getLogger(CommonDocumentService.class);
	private static final String NYEHMS_LOG = AppConstants.nyehmLogDebugString.getName();

	/** The association status of a document disassociated because it is deleted. */
	private static final String DISASSOCIATED_DELETED = "DISASSOC_DEL";
	/** The module whose document types a worker may see. */
	private static final int DOCUMENT_TYPES_MODULE_ID = 16;
	/** The surveillance assignment process of the OOS follow-up bucket. */
	private static final int OOS_FOLLOW_UP_ASSIGN_PROCESS = 11;

	private final CommonExceptionHandler commonExceptionHandler;
	private final CommonDocLabsDAO commonDocLabsDAO;
	private final NycEharsDocRepository nycEharsDocRepository;
	private final CodedStateRepository codedStateRepository;
	private final CommonDAO commonDAO;
	private final DocumentDAO documentDAO;
	private final ConnectionDAO connectionDAO;
	private final LabDocumentDAO labDocumentDAO;
	private final ADAPDocumentDAO adapDocumentDAO;
	private final MedicaidDocumentDAO medicaidDocumentDAO;
	private final DeathDocRepository deathDocRepository;
	private final NycDeathDocRepository nycDeathDocRepository;
	private final RawDeathDocRepository rawDeathDocRepository;
	private final RawNycDeathDocRepository rawNycDeathDocRepository;
	private final DoccsDocumentDAO doccsDocumentDAO;
	private final PRFDocumentDAO prfDocumentDAO;
	private final TBDocumentDAO tbDocumentDAO;

	// ---------- code lists

	public List<JSONObject> getCodedArvs() {
		return commonDocLabsDAO.getCodedArvs();
	}

	public List<JSONObject> getCodedHivFacilityTypes() {
		return commonDocLabsDAO.getCodedHivFacilityTypes();
	}

	public List<JSONObject> getCodedInvestigationStatus() {
		return commonDocLabsDAO.getCodedInvestigationStatus();
	}

	public List<JSONObject> getDispositionCodeValues(boolean filterRows) {
		Map<String, Boolean> inputParameterMap = new HashMap<>();
		inputParameterMap.put("filterRows", filterRows);
		return commonDocLabsDAO.getDispositionCodeValues(inputParameterMap);
	}

	public List<JSONObject> getCOSCodesData() {
		return commonDocLabsDAO.getCOSCodesData();
	}

	public List<JSONObject> getAddressTypes() {
		return commonDocLabsDAO.getAddressTypes();
	}

	public List<JSONObject> getCodedSexAtBirthValues() {
		return commonDocLabsDAO.getCodedSexAtBirthValues();
	}

	public List<JSONObject> getCodedYesNoUnkownValues() {
		return commonDocLabsDAO.getCodedYesNoUnkownValues();
	}

	public List<JSONObject> getCodedPrimaryLanguageValues() {
		return commonDocLabsDAO.getCodedPrimaryLanguageValues();
	}

	public List<JSONObject> getCodedYesNoRefUnkownValues() {
		return commonDocLabsDAO.getCodedYesNoRefUnkownValues();
	}

	public List<JSONObject> getCodedTelephoneTypeValues() {
		return commonDocLabsDAO.getCodedTelephoneTypeValues();
	}

	public List<JSONObject> getCodedFacilityTypeValues() {
		return commonDocLabsDAO.getCodedFacilityTypeValues();
	}

	public List<JSONObject> getPartnerAssistanceStatus() {
		return commonDocLabsDAO.getPartnerAssistanceStatus();
	}

	public List<JSONObject> getCodedMaritalStatus() {
		return commonDocLabsDAO.getCodedMaritalStatus();
	}

	public List<JSONObject> getFileNamesForDocumentType(String documentType) {
		return commonDocLabsDAO.getFileNamesForDocumentType(documentType);
	}

	public List<JSONObject> getCodedProfessionValues() {
		return commonDocLabsDAO.getCodedProfessionValues();
	}

	public List<JSONObject> getLAFTReasonslist() {
		return commonDocLabsDAO.getLAFTReasonslist();
	}

	public List<JSONObject> getCodedSexualOrientationValues() {
		return commonDocLabsDAO.getCodedSexualOrientationValues();
	}

	public List<JSONObject> getCodedSexualOrientationPrfValues() {
		return commonDocLabsDAO.getCodedSexualOrientationPrfValues();
	}

	public List<JSONObject> getExpandedRaceList() {
		return commonDocLabsDAO.getExpandedRaceList();
	}

	public List<String> getUnitsOfMeasurement() {
		return commonDocLabsDAO.getUnitsOfMeasurement();
	}

	public List<Map<String, String>> getHIVCategories() {
		return commonDocLabsDAO.getHIVCategories();
	}

	public List<Map<String, String>> getAIDSCategories() {
		return commonDocLabsDAO.getAIDSCategories();
	}

	public List<Map<String, String>> getPSMessageReferenceData(String columnName) {
		return commonDocLabsDAO.getPSMessageReferenceData(columnName);
	}

	/**
	 * The eHARS country codes (code to description, by description), read from the eHARS database. An error is logged
	 * and gives the codes read so far.
	 */
	public Map<String, String> getEharsCodedCountryValues() {
		Map<String, String> countryCodes = new LinkedHashMap<>();
		try (Connection connection = connectionDAO.getSqlServerConnectionObj()) {
			if (connection == null) {
				return countryCodes;
			}
			log.debug("SQL server database Connection is available {}", connection);
			try (Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("select country_cd, descr from country_code order by descr")) {
				while (resultSet.next()) {
					countryCodes.put(resultSet.getString(1), resultSet.getString(2));
				}
			}
			log.debug("Ehars Country codes  : {}", countryCodes);
		} catch (SQLException sqlException) {
			log.error("Exception while retrieving the Ehars data from SQL Server {}", sqlException.getMessage());
		}
		return countryCodes;
	}

	// ---------- document screens

	/** A NYC eHARS record for the document screen, or null if there is none. */
	public NycEharsDocData getNycEHARSDoc(int docId) {
		return nycEharsDocRepository.findDocument(docId);
	}

	/** A NYC lab record for the document screen. */
	public NYCLabDocVO getNycLabDoc(int docId) {
		return commonDocLabsDAO.getNycLabDoc(docId);
	}

	public PersonViewVO getClericalDocumentAsPerson(int documentId, String documentType) {
		return commonDocLabsDAO.getClericalDocumentAsPerson(documentId);
	}

	// ---------- document locator, person tables and associations

	/** Deletes a document from the document locator; throws if there was none. */
	public void deleteDocFromDocLocator(int docId, UserObject userObject) {
		if (commonDocLabsDAO.deleteDocFromDocLocator(docId) == 0) {
			throw new DataRetrievalFailureException(docId + "  : Failed to delete the document Document Locator ");
		}
	}

	public List<PSMessageVO> getPSMessageDetails(int docId, String documentType) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("documentId", docId);
		inputOutputParameterMap.put("documentType", documentType);
		return commonDocLabsDAO.getPSMessageDetails(inputOutputParameterMap);
	}

	/**
	 * Copies a standardized document into the person tables of the (currently active) state number.
	 *
	 * @throws DataRetrievalFailureException if the database procedure reports an error
	 */
	public void moveStdDocumentToDocAttrTables(String stateNo, int documentId, UserObject userObject, String documentType, String origin) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("p_stateno", commonDAO.getCurrentActiveStateNo(stateNo));
		inputOutputParameterMap.put("p_user_id", userObject.getWorkerId());
		inputOutputParameterMap.put("p_documentId", documentId);
		inputOutputParameterMap.put("p_document_type", documentType);
		inputOutputParameterMap.put("p_called_from", origin);

		commonDocLabsDAO.moveStdDocumentToDocAttrTables(inputOutputParameterMap);

		Integer statusCode = (Integer) inputOutputParameterMap.get("p_code_out");
		if (statusCode != 0) {
			log.error("Exception occured while inserting Std document in Person tables : {}", inputOutputParameterMap.get("p_msg_out"));
			throw new DataRetrievalFailureException("Failed while associating the document to STATENO :  " + stateNo);
		}
	}

	/**
	 * Ends the person association of a document (and, for DOCCS, of its DIN documents). When the document is deleted
	 * ({@code DISASSOC_DEL}), its PS messages go too, and a state number left without documents is inactivated with
	 * its open merge pairs rejected.
	 */
	@Transactional
	public void deleteEntryInPersonDocAssociation(int documentId, String documentType, String associationStatus, UserObject userObject) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("associationStatus", associationStatus);
		inputOutputParameterMap.put("p_user_id", userObject.getWorkerId());
		inputOutputParameterMap.put("p_documentId", documentId);

		boolean deleted = DISASSOCIATED_DELETED.equals(associationStatus);
		String stateno = null;
		if (deleted) {
			Map<String, Object> statenoDetails = commonDocLabsDAO.getStatenoDetailsByDocumentId(documentId);
			stateno = statenoDetails.get("STATENO").toString();
			if (statenoDetails.get("PS_ASSESS_STATUS") != null) {
				commonDocLabsDAO.deletePsMessageForTriggeringDocument(inputOutputParameterMap);
				commonDocLabsDAO.deleteIndicatePsMessage(inputOutputParameterMap);
			}
		}

		List<Integer> documentIds = "DOCCS".equals(documentType) ? doccsDocumentDAO.getDinDoccsIds(documentId) : new ArrayList<>();
		documentIds.add(documentId);
		inputOutputParameterMap.put("p_documentId_list", documentIds);
		commonDocLabsDAO.deleteEntryInPersonDocAssociation(inputOutputParameterMap);

		if (deleted) {
			inputOutputParameterMap.put("p_stateno", stateno);
			if (commonDocLabsDAO.checkAndInactiveStateno(inputOutputParameterMap) > 0) {
				inputOutputParameterMap.put("p_reviewComments", stateno + " Inactivated");
				commonDocLabsDAO.rejectMergePairForInactiveStateno(inputOutputParameterMap);
				commonDocLabsDAO.rejectBadMergeStatneForInactiveStateno(inputOutputParameterMap);
				log.error("Inactivating the Stateno {}", stateno);
			}
		}
	}

	public void deleteEntryInDocumentPersonAttr(int documentId, UserObject userObject) {
		commonDocLabsDAO.deleteEntryInDocumentPersonAttr(documentId);
	}

	public void deleteEntryInDocumentRiskTable(int documentId, UserObject userObject) {
		commonDocLabsDAO.deleteEntryInDocumentRiskTable(documentId);
	}

	public void deleteEntryInDocumentRace(int documentId, UserObject userObject) {
		commonDocLabsDAO.deleteEntryInDocumentRace(documentId);
	}

	public String getColumnDataFromTable(String tableName, String targetColumn, String refColumn, int refValue) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("targetColumn", targetColumn);
		inputOutputParameterMap.put("refColumn", refColumn);
		inputOutputParameterMap.put("refValue", refValue);
		return commonDocLabsDAO.getColumnDataFromTable(inputOutputParameterMap);
	}

	/** Sets the PS assignment status of a document; throws if there is no such document. */
	public void updateDocumentPsAssignStatus(int psAssignStatus, int newDocumentId) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("documentId", newDocumentId);
		inputOutputParameterMap.put("pS_ASSIGN_STATUS", psAssignStatus);
		if (commonDocLabsDAO.updateDocumentPsAssignStatus(inputOutputParameterMap) == 0) {
			throw new DataRetrievalFailureException("Failed to PS Assign status for the new Document  :" + newDocumentId);
		}
	}

	public void updateTriggeringDocumentIdInNyehmsMessage(int documentId, int newDocumentId) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("oldDocumentId", documentId);
		inputOutputParameterMap.put("newDocumentId", newDocumentId);
		commonDocLabsDAO.updateTriggeringDocumentIdInNyehmsMessage(inputOutputParameterMap);
	}

	// ---------- raw and standardized document status

	/**
	 * Sets the process status of a received document of a type; does nothing for a type without a raw table.
	 *
	 * @throws DataRetrievalFailureException if no row was updated
	 */
	public void updateRawDocumentStatus(int currentDocumentId, UserObject userObject, String documentType, int statusCode) {
		String tableName = LabCommonUtils.getRawTableNameFast(documentType);
		if (CommonUtil.isNotNullAndEmpty(tableName) && updateRawStatus(tableName, statusCode, currentDocumentId) == 0) {
			log.error("Failed to update the Raw document deleted status {}", currentDocumentId);
			throw new DataRetrievalFailureException(
					"Failed to update the " + statusCode + " status code for the Raw " + documentType + " document  :" + currentDocumentId);
		}
	}

	/**
	 * Sets the process status of a received document in a raw table.
	 *
	 * @throws DataRetrievalFailureException if no row was updated
	 */
	public void updateRawDocumentStatus(int currentDocumentId, String tableName, int statusCode) {
		if (updateRawStatus(tableName, statusCode, currentDocumentId) == 0) {
			throw new DataRetrievalFailureException(
					"Failed to update CURRENT_STATUS_CODE in " + tableName + " for DOCUMENT_ID = " + currentDocumentId);
		}
	}

	private int updateRawStatus(String tableName, int statusCode, int documentId) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("statusCode", statusCode);
		inputOutputParameterMap.put("document_ID", documentId);
		return commonDocLabsDAO.updateRawDocumentStatusCode(inputOutputParameterMap);
	}

	/** Deletes a standardized document (for an eCLRS HIV lab, with its resistance data). */
	public void deleteDocumentFromStdTable(String parentDocumentType, int documentId, UserObject userObject) {
		String tableName = LabCommonUtils.getStdTableName(parentDocumentType);
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("document_ID", documentId);
		if ("STD_HIV_ECLRS_DOC".equals(tableName)) {
			labDocumentDAO.deleteResistanceData(documentId);
		}
		commonDocLabsDAO.deleteDocumentFromStdTable(inputOutputParameterMap);
	}

	/**
	 * Sets the process status of a standardized document.
	 *
	 * @throws DataRetrievalFailureException if no row was updated
	 */
	public void updateStdDocumentStatus(int currentDocumentId, String documentType, int statusCode) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("tableName", LabCommonUtils.getStdTableName(documentType));
		inputOutputParameterMap.put("statusCode", statusCode);
		inputOutputParameterMap.put("document_ID", currentDocumentId);
		if (commonDocLabsDAO.updateStdDocumentStatusCode(inputOutputParameterMap) == 0) {
			throw new DataRetrievalFailureException(
					"Failed to update the " + statusCode + " status code for the Std " + documentType + " document  :" + currentDocumentId);
		}
	}

	/**
	 * A document's row, from the raw table ({@code tableIdentification} "RAW") or the standardized table of its type.
	 *
	 * @return the row, or null for a type without such a table
	 */
	public Map<Object, Object> getDocData(int documentId, String tableIdentification, String documentType) {
		String tableName = "RAW".equals(tableIdentification) ? LabCommonUtils.getRawTableName(documentType)
				: LabCommonUtils.getStdTableName(documentType);
		if (!CommonUtil.isNotNullAndEmpty(tableName)) {
			return null;
		}
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("document_ID", documentId);
		return commonDocLabsDAO.getDocData(inputOutputParameterMap);
	}

	// ---------- copies of a document

	/**
	 * Writes the raw row(s) of a copied document (Medicaid/insurance: also its ARVs and providers; PRF: also its
	 * partners).
	 *
	 * @param rawDocData the copy's raw row, value per column name (Medicaid: MEDICAID_DOC_ID is set)
	 * @param oldDocumentId the copied document
	 * @throws DataRetrievalFailureException if nothing was written, or for a type that cannot be copied
	 */
	@Transactional
	public void writeRawDocData(String documentType, Map<Object, Object> rawDocData, int oldDocumentId, UserObject userObject) {
		int resultcount = switch (Objects.toString(documentType, "")) {
			case "HIV_LAB" -> labDocumentDAO.insertRawHivDocument(rawDocData);
			case "STD_LAB" -> labDocumentDAO.insertRawStdDocument(rawDocData);
			case "DTH" -> {
				rawDeathDocRepository.saveAndFlush(DeathDocMapping.toRawDoc(rawDocData));
				yield 1;
			}
			case "NYC_DTH" -> {
				rawNycDeathDocRepository.saveAndFlush(NycDeathDocMapping.toRawDoc(rawDocData));
				yield 1;
			}
			case "ADAP" -> adapDocumentDAO.insertRawADAPDocument(rawDocData);
			case "TB" -> tbDocumentDAO.insertRawEditedTBDocument(rawDocData);
			case "MED", "INS" -> writeRawMedicaidCopy(rawDocData, oldDocumentId, userObject);
			case "PRF" -> writeRawPrfCopy(rawDocData, oldDocumentId);
			default -> 0;
		};
		if (resultcount == 0) {
			throw new DataRetrievalFailureException("Failed to insert the Raw " + documentType + " document  :" + rawDocData.get("DOCUMENT_ID"));
		}
	}

	private int writeRawMedicaidCopy(Map<Object, Object> rawDocData, int oldDocumentId, UserObject userObject) {
		if (medicaidDocumentDAO.insertRawMedDocument(rawDocData) == 0) {
			return 0;
		}
		int documentId = (Integer) rawDocData.get("DOCUMENT_ID");
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("oldDocumentId", oldDocumentId);
		inputOutputParameterMap.put("newDocumentId", documentId);
		inputOutputParameterMap.put("createId", userObject.getWorkerId());
		medicaidDocumentDAO.insertRawMedicaidArvsData(inputOutputParameterMap);
		rawDocData.put("MEDICAID_DOC_ID", documentId);
		return medicaidDocumentDAO.insertRawMedProvidersData(rawDocData);
	}

	private int writeRawPrfCopy(Map<Object, Object> rawDocData, int oldDocumentId) {
		int resultcount = prfDocumentDAO.insertRawPRFOOSDocument(rawDocData);
		if (resultcount > 0) {
			Map<Object, Object> inputOutputParameterMap = new HashMap<>();
			inputOutputParameterMap.put("oldDocumentId", oldDocumentId);
			inputOutputParameterMap.put("newDocumentId", (Integer) rawDocData.get("DOCUMENT_ID"));
			prfDocumentDAO.storeRawPrfPartnersOOSDocument(inputOutputParameterMap);
		}
		return resultcount;
	}

	/**
	 * Writes the standardized row(s) of a copied document (ADAP: chosen provider RX; Medicaid/insurance: also its
	 * providers; PRF: also its partners).
	 *
	 * @param stdDocData the copy's standardized row, value per column name (ADAP: FLAG_CHOSEN_PROVIDER is set)
	 * @throws DataRetrievalFailureException if nothing was written, or for a type that cannot be copied
	 */
	@Transactional
	public void writeStdDocData(String documentType, Map<Object, Object> stdDocData, int oldDocumentId) {
		int resultcount = switch (Objects.toString(documentType, "")) {
			case "HIV_LAB" -> labDocumentDAO.insertStdHivDocument(stdDocData);
			case "STD_LAB" -> labDocumentDAO.insertStdStdDocument(stdDocData);
			case "DTH" -> {
				deathDocRepository.saveAndFlush(DeathDocMapping.toStdDoc(stdDocData));
				yield 1;
			}
			case "NYC_DTH" -> {
				nycDeathDocRepository.saveAndFlush(NycDeathDocMapping.toStdDoc(stdDocData));
				yield 1;
			}
			case "ADAP" -> {
				stdDocData.put("FLAG_CHOSEN_PROVIDER", "RX");
				yield adapDocumentDAO.writeStdDocData(stdDocData);
			}
			case "TB" -> tbDocumentDAO.insertStdEditedTBDocument(stdDocData);
			case "MED", "INS" -> medicaidDocumentDAO.writeStdDocData(stdDocData) > 0
					? medicaidDocumentDAO.storeRawMedicaidProvidersDataInStd((Integer) stdDocData.get("DOCUMENT_ID"))
					: 0;
			case "PRF" -> writeStdPrfCopy(stdDocData);
			default -> 0;
		};
		if (resultcount == 0) {
			throw new DataRetrievalFailureException("Failed to insert the Std " + documentType + " document  :" + stdDocData.get("DOCUMENT_ID"));
		}
	}

	private int writeStdPrfCopy(Map<Object, Object> stdDocData) {
		int resultcount = prfDocumentDAO.insertStdPRFOOSDocument(stdDocData);
		if (resultcount > 0) {
			prfDocumentDAO.writeRawPRFPartnerDataToStdTable((Integer) stdDocData.get("DOCUMENT_ID"));
		}
		return resultcount;
	}

	// ---------- statuses set from the screens

	/**
	 * Sets the surveillance assessment status of a document and reports the outcome through {@code bindingResult}.
	 *
	 * @throws DataRetrievalFailureException if there is no such document
	 */
	public void updateSurvellianceAssignmentStatus(int documentId, int assignProcess, UserObject userObject, BindingResult bindingResult) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("documentId", documentId);
		inputOutputParameterMap.put("assignProcess", assignProcess);
		inputOutputParameterMap.put("p_user_id", userObject.getWorkerId());

		if (commonDocLabsDAO.updateSurvellianceAssignmentStatus(inputOutputParameterMap) == 0) {
			String errorMessage = "Failed to update the SURV_ASSES_STATUS for :" + documentId;
			log.error(errorMessage);
			throw new DataRetrievalFailureException(errorMessage);
		}
		commonExceptionHandler.inspectDBTransacionResult(0, assignProcess == OOS_FOLLOW_UP_ASSIGN_PROCESS
				? "Document moved to OOS Follow-Up bucket." : "Successfully updated the document status", bindingResult);
	}

	/** The document types the worker may see. */
	public List<String> getDocumentTypes(UserObject userObject) {
		Map<String, Object> map = new HashMap<>();
		map.put("p_module_id", DOCUMENT_TYPES_MODULE_ID);
		map.put("p_worker_id", userObject.getWorkerId());
		return commonDocLabsDAO.getDocumentTypes(map);
	}

	public Map<Object, Object> getPersonDocData(int documentId) {
		return commonDocLabsDAO.getPersonDocData(documentId);
	}

	/** The matched state number and source id of a standardized document (the columns of its type). */
	public Map<String, String> getStatenoAndSourceCodeFromStdDocData(String parentDocumentType, int documentId, UserObject userObject) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("documentId", documentId);
		inputOutputParameterMap.put("tableName", LabCommonUtils.getStdTableName(parentDocumentType));
		Map<String, String> stdTableFieldObj = LabCommonUtils.getStdReferenceColumnNames(parentDocumentType);
		if (stdTableFieldObj != null) {
			inputOutputParameterMap.put("STATENO", stdTableFieldObj.get("MATCHED_STATENO"));
			inputOutputParameterMap.put("SOURCE_ID", stdTableFieldObj.get("SOURCE_ID"));
		}
		return commonDocLabsDAO.getStatenoAndSourceCodeFromStdDocData(inputOutputParameterMap);
	}

	/**
	 * Sets the process status of received documents (a delete also deactivates them) and reports the outcome
	 * through {@code bindingResult}.
	 */
	@Transactional
	public void updateDocumentsStatus(Long[] documentIds, String documentType, int documentStatus, BindingResult bindingResult,
			UserObject userObject) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("rawTableName", LabCommonUtils.getRawTableName(documentType));
		inputOutputParameterMap.put("documentIds", documentIds);
		inputOutputParameterMap.put("processStatus", documentStatus);
		inputOutputParameterMap.put("workerId", userObject.getUserId());
		commonDocLabsDAO.updateDocumentsStatus(inputOutputParameterMap);

		if (documentStatus == CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS) {
			commonDocLabsDAO.inActivateDocument(inputOutputParameterMap);
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully deleted the document(s)", bindingResult);
		} else {
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the document(s) status", bindingResult);
		}
	}

	/**
	 * Sets the test type of a document.
	 *
	 * @return the number of rows updated
	 * @throws DataRetrievalFailureException if there is no such document
	 */
	public int writeDocumentTestType(int documentId, String testType) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("documentID", documentId);
		inputOutputParameterMap.put("testType", testType);
		int resultCount = documentDAO.writeDocumentTestType(inputOutputParameterMap);
		if (resultCount == 0) {
			throw new DataRetrievalFailureException("Unable to update the Test Type " + testType);
		}
		return resultCount;
	}

	public int updateRIDRDocumentWithAcrf(String ridrDocumentId, int acrfDocId, UserObject userObject) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("acrfDocId", acrfDocId);
		inputOutputParameterMap.put("ridrDocumentId", ridrDocumentId);
		inputOutputParameterMap.put("workerId", userObject.getWorkerId());
		return commonDocLabsDAO.updateRIDRDocumentWithAcrf(inputOutputParameterMap);
	}

	/**
	 * Creates a new document id with the database procedure; the id is returned in {@code map} (p_new_doc_id).
	 *
	 * @param map the procedure's parameters (p_document_type, p_new_doc_id, p_parent_doc_id)
	 * @throws DataRetrievalFailureException if the procedure reports an error
	 */
	public void getNewDocId(Map<Object, Object> map) {
		log.debug("{} Getting New Doc ID...", NYEHMS_LOG);
		documentDAO.getNewDocId(map);
		log.debug("{} After Getting New Doc ID call, Doc ID is: {}", NYEHMS_LOG, map.get("p_new_doc_id"));
		if ((Integer) map.get("p_return_code") == -1) {
			throw new DataRetrievalFailureException((String) map.get("p_msg_out"));
		}
	}

	// ---------- states

	/**
	 * A state as the documents store it: letters and spaces only, and a state name (more than 2 letters)
	 * replaced by its code.
	 *
	 * @param state the state as received (not null)
	 * @return the state code, or "" for a state name that is not known
	 */
	public String validateState(String state) {
		return findStateCode(state).orElse("");
	}

	/**
	 * A state as the documents store it (see {@link #validateState}).
	 *
	 * @param state the state as received (not null)
	 * @return the state code, or empty for a state name that is not known (CODED_STATE)
	 */
	public Optional<String> findStateCode(String state) {
		String cleaned = state.replaceAll("[^a-zA-Z ]", "").trim();
		if (cleaned.length() <= 2) {
			return Optional.of(cleaned);
		}
		String stateCode = codedStateRepository.findStateCodeByName(cleaned);
		return CommonUtil.isNotNullAndEmpty(stateCode) ? Optional.of(stateCode) : Optional.empty();
	}

	// ---------- other lookups and updates

	public String getSubDocumentType(Integer documentId) {
		return commonDocLabsDAO.getSubDocumentType(documentId);
	}

	public void createNonCaseWithNewDocument(int documentId, int newDocumentId) {
		Map<String, Integer> inputParameterMap = new HashMap<>();
		inputParameterMap.put("oldDocumentId", documentId);
		inputParameterMap.put("newDocumentId", newDocumentId);
		commonDocLabsDAO.createNonCaseWithNewDocument(inputParameterMap);
	}

	public int checkForActiveDocumentInPersonTables(int documentId) {
		return commonDocLabsDAO.checkForActiveDocumentInPersonTables(documentId);
	}

	public Map<String, Long> getTriggeringDocumentAddressIds(int newDocumentId) {
		return commonDocLabsDAO.getTriggeringDocumentAddressIds(newDocumentId);
	}

	public List<Integer> getOpenAssignmentsForTriggeringDocIds(int newDocumentId) {
		return commonDocLabsDAO.getOpenAssignmentsForTriggeringDocIds(newDocumentId);
	}

	public Map<String, Long> getAssignmentAddessIds(Integer assignmentId) {
		return commonDocLabsDAO.getAssignmentAddessIds(assignmentId);
	}

	public String getEharsStateno(String stateno) {
		return commonDAO.getEharsStateno(stateno);
	}

	public Map<String, String> getNYCStatenoDetails(String stateno) {
		return commonDAO.getNYCStatenoDetails(stateno);
	}

	public Map<String, Object> getDiscardedDocumentDetails(int docId) {
		return commonDocLabsDAO.getDiscardedDocumentDetails(docId);
	}

	public void updateRiaLabDocument(int documentId, int newDocumentId) {
		Map<String, Integer> inputMap = new HashMap<>();
		inputMap.put("OLD_DOCUMENTID", documentId);
		inputMap.put("NEW_DOCUMENTID", newDocumentId);
		commonDocLabsDAO.updateRiaLabDocument(inputMap);
	}
}
