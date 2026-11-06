package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * Where a document is filed (table DOCUMENT_LOCATOR); only the columns the application reads and changes.
 * <p>
 * Read in JPQL; changed only by bulk statements (the test type of a document, the deletion of an active scanned
 * document). <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 * The confirmed STD upload also files its staged documents with a JPQL insert-select (DOCUMENT_ID,
 * DOCUMENT_TYPE_CODE, IS_ACTIVE, RECEIVED_DATE).
 */
@Entity
@Getter
@Table(name = "DOCUMENT_LOCATOR")
public class DocumentLocator {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "PARENT_DOCUMENT_ID")
	private Integer parentDocumentId;

	@Column(name = "DOCUMENT_TYPE_CODE")
	private String documentTypeCode;

	@Column(name = "TEST_TYPE_CD")
	private String testTypeCd;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "RECEIVED_DATE")
	private Timestamp receivedDate;
}
