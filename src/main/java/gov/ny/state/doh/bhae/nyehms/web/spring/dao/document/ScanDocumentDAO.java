package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.DocumentPersonAttr;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdScanDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.PersonDocAssociation;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ScanDocInfoRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.DocumentPersonAttrRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.StdScanDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.DocumentLocatorRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.PersonDocAssociationRepository;
import lombok.RequiredArgsConstructor;

/**
 * The scanned documents: their entries in STD_SCAN_DOC, PERSON_DOC_ASSOCIATION and DOCUMENT_PERSON_ATTR, and their
 * deletion (formerly a MyBatis mapper with the same name and methods), through Spring Data repositories over StdScanDoc,
 * PersonDocAssociation, DocumentPersonAttr and DocumentLocator. No native SQL.
 * <p>
 * The dates the former SQL set with SYSDATE (CREATE_DATE, ASSOCIATION_DATE, DELETED_DATE) stay the database's SYSDATE.
 */
@Repository
@RequiredArgsConstructor
public class ScanDocumentDAO {

	private final StdScanDocRepository scanDocs;
	private final PersonDocAssociationRepository associations;
	private final DocumentPersonAttrRepository personAttributes;
	private final DocumentLocatorRepository locators;

	/**
	 * The file name of a scanned document and its current STATENO, as a map (FILE_NAME, STATENO; a key only when its
	 * value is set).
	 *
	 * @param docId the document id as text (compared as a number, as Oracle converted it)
	 * @return the map, or null if there is no such document; more than one current match fails, as before
	 */
	public Map<String, String> getScanDocInfo(String docId) {
		Long documentId = documentNumber(docId);
		List<ScanDocInfoRow> rows = documentId == null ? List.of() : scanDocs.findScanDocInfo(documentId);
		if (rows.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, rows.size());
		}
		if (rows.isEmpty()) {
			return null;
		}
		Map<String, String> info = new HashMap<>();
		if (rows.get(0).fileName() != null) {
			info.put("FILE_NAME", rows.get(0).fileName());
		}
		if (rows.get(0).stateno() != null) {
			info.put("STATENO", rows.get(0).stateno());
		}
		return info.isEmpty() ? null : info;
	}

	/**
	 * Inserts the association of a new scanned document with its person (ASSOCIATION_STATUS_CODE 'ASSOC',
	 * ASSOCIATION_DATE the database's SYSDATE).
	 *
	 * @param docIdMap documentId, stateNo, workerId
	 */
	@Transactional
	public void createEntryInPersonDocAssociation(Map<Object, Object> docIdMap) {
		associations.saveAndFlush(PersonDocAssociation.builder()
				.documentId(integer(docIdMap.get("documentId")))
				.stateno(text(docIdMap.get("stateNo")))
				.associationStatusCode("ASSOC")
				.workerId(longValue(docIdMap.get("workerId")))
				.build());
	}

	/**
	 * Inserts the DOCUMENT_PERSON_ATTR row (document id only) of a new scanned document.
	 *
	 * @param docIdMap documentId
	 */
	@Transactional
	public void createEntryInDocumentPerson(Map<Object, Object> docIdMap) {
		personAttributes.saveAndFlush(DocumentPersonAttr.builder().documentId(integer(docIdMap.get("documentId"))).build());
	}

	/**
	 * Inserts the STD_SCAN_DOC row of a new scanned document (CREATE_DATE the database's SYSDATE).
	 *
	 * @param docIdMap documentId, fileName, stateNo, workerId (CREATED_BY)
	 */
	@Transactional
	public void addStdScanEntry(Map<Object, Object> docIdMap) {
		scanDocs.saveAndFlush(StdScanDoc.builder()
				.documentId(longValue(docIdMap.get("documentId")))
				.fileName(text(docIdMap.get("fileName")))
				.stateno(text(docIdMap.get("stateNo")))
				.createdBy(longValue(docIdMap.get("workerId")))
				.build());
	}

	/**
	 * Deletes every person association of a document.
	 *
	 * @param inputParameterMap DOCUMENT_ID
	 * @return the number of rows deleted
	 */
	@Transactional
	public int deleteDocumentInPersonDocAssociation(Map<String, Object> inputParameterMap) {
		return associations.deleteByDocument(integer(inputParameterMap.get("DOCUMENT_ID")));
	}

	/**
	 * Deletes the DOCUMENT_PERSON_ATTR rows of a document.
	 *
	 * @param inputParameterMap DOCUMENT_ID
	 * @return the number of rows deleted
	 */
	@Transactional
	public int deleteDocumentInDocumentPersonAttribute(Map<String, Object> inputParameterMap) {
		return personAttributes.deleteByDocument(integer(inputParameterMap.get("DOCUMENT_ID")));
	}

	/**
	 * Deletes a document from DOCUMENT_LOCATOR if it is active (IS_ACTIVE = 'Y').
	 *
	 * @param inputParameterMap DOCUMENT_ID
	 * @return the number of rows deleted
	 */
	@Transactional
	public int deleteDocumentInDocumentLocator(Map<String, Object> inputParameterMap) {
		return locators.deleteActive(integer(inputParameterMap.get("DOCUMENT_ID")));
	}

	/**
	 * Marks a scanned document deleted (DELETED_BY, DELETED_DATE the database's SYSDATE, COMMENTS).
	 *
	 * @param inputParameterMap DOCUMENT_ID, DELETED_BY, COMMENTS
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateStdScanDocTable(Map<String, Object> inputParameterMap) {
		return scanDocs.markDeleted(longValue(inputParameterMap.get("DELETED_BY")), text(inputParameterMap.get("COMMENTS")),
				longValue(inputParameterMap.get("DOCUMENT_ID")));
	}

	/**
	 * A document id given as text, as Oracle compared it with the number column: null for null, null (no row) for a
	 * number with a fraction; a text that is not a number fails, as before.
	 */
	private static Long documentNumber(String docId) {
		if (docId == null || docId.isEmpty()) {
			return null;
		}
		BigDecimal number = new BigDecimal(docId.trim());
		return number.stripTrailingZeros().scale() > 0 ? null : number.longValueExact();
	}

	private static Integer integer(Object value) {
		return value == null || "".equals(value) ? null : new BigDecimal(value.toString().trim()).intValueExact();
	}

	private static Long longValue(Object value) {
		return value == null || "".equals(value) ? null : new BigDecimal(value.toString().trim()).longValueExact();
	}

	private static String text(Object value) {
		return value == null ? null : value.toString();
	}
}
