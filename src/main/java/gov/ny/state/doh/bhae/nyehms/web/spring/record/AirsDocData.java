package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAirsDoc;

/**
 * An AIRS (AIDS Institute Reporting System) report as the AIRS document screen shows it (AIRSDocument.jsp, model attribute
 * {@code stdAIRSDocVO}: {@code stdAIRSDocVO.stdAirsDoc.<field>} etc.); replaces the former AIRSDocVO.
 *
 * @param stdAirsDoc the standardized document (STD_AIRS_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param dob DOB; (TO_CHAR(std.DOB,'MM/DD/YYYY') DOB) its default if empty, as the former primitive property
 * @param address ADDRESS; its default if empty, as the former primitive property
 * @param provAddr PROV_ADDR; its default if empty, as the former primitive property
 * @param documentSourceTable DOCUMENT_SOURCE_TABLE; ('STD' as DOCUMENT_SOURCE_TABLE) its default if empty, as the former primitive property
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document (DOCUMENT_LOCATOR); 0 if empty, as the former int property
 * @param airsVerType OLD or NEW AIRS form version, worked out by AIRSDocumentService
 */
public record AirsDocData(StdAirsDoc stdAirsDoc,
		String stateno,
		String discardStatus,
		String dob,
		String address,
		String provAddr,
		String documentSourceTable,
		Integer parentDocumentId,
		String airsVerType) {

	/** The document as read (used by the JPQL constructor expression). */
	public AirsDocData(StdAirsDoc stdAirsDoc, String stateno, String discardStatus, String dob, String address, String provAddr, String documentSourceTable, Integer parentDocumentId) {
		this(stdAirsDoc, stateno, discardStatus, dob, address, provAddr, documentSourceTable, parentDocumentId, null);
	}

	/** The same document with {@code airsVerType}. */
	public AirsDocData withAirsVerType(String airsVerType) {
		return new AirsDocData(stdAirsDoc, stateno, discardStatus, dob, address, provAddr, documentSourceTable, parentDocumentId, airsVerType);
	}
}
