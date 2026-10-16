package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdSyphilisDoc;

/**
 * A syphilis report as the syphilis document screen shows it (SYPHDocument.jsp, model attribute
 * {@code stdSYPHDocVO}: {@code stdSYPHDocVO.stdSyphilisDoc.<field>} etc.); replaces the former SYPHDocVO.
 *
 * @param stdSyphilisDoc the standardized document (STD_SYPHILIS_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param dob DOB; (TO_CHAR(std.DOB,'MM/DD/YYYY') DOB) its default if empty, as the former primitive property
 * @param dod DOD; (TO_CHAR(std.DOD,'MM/DD/YYYY') DOD) its default if empty, as the former primitive property
 * @param street STREET; its default if empty, as the former primitive property
 * @param syphilisDiagnosisDate SYPHILIS_DIAGNOSIS_DATE; (TO_CHAR(std.SYPHILIS_DIAGNOSIS_DATE,'MM/DD/YYYY') SYPHILIS_DIAGNOSIS_DATE) its default if empty, as the former primitive property
 * @param receivedDate RECEIVED_DATE; (TO_CHAR(std.RECEIVED_DATE,'MM/DD/YYYY') RECEIVED_DATE) its default if empty, as the former primitive property
 * @param provideraddress PROVIDERADDRESS; its default if empty, as the former primitive property
 * @param documentSourceTable DOCUMENT_SOURCE_TABLE; ('STD' as DOCUMENT_SOURCE_TABLE) its default if empty, as the former primitive property
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document (DOCUMENT_LOCATOR); 0 if empty, as the former int property
 */
public record SyphDocData(StdSyphilisDoc stdSyphilisDoc,
		String stateno,
		String dob,
		String dod,
		String street,
		String syphilisDiagnosisDate,
		String receivedDate,
		String provideraddress,
		String documentSourceTable,
		Integer parentDocumentId) {
}
