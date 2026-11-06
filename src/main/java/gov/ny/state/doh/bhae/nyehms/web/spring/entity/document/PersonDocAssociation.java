package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * Link between a document and the person (STATENO) it was matched to (table
 * PERSON_DOC_ASSOCIATION); an association is current while DISSASSOCIATION_DATE is null.
 * Only the columns the application reads.
 * <p>
 * Used in JPQL projections and changed by bulk JPQL updates only (NonCaseDocumentDAO sets DISCARD_STATUS and
 * SURV_ASSESS_STATUS; not @Immutable for that reason), never loaded or saved as an entity. <b>The key (DOCUMENT_ID,
 * STATENO) is assumed</b> — confirm before loading or writing it as an entity. Shared by the document screens (NYC
 * PRF, and next Old Cases, Medicaid old, NDI, CLIMS, Black Box...).
 */
@Entity
@Getter
@Table(name = "PERSON_DOC_ASSOCIATION")
@IdClass(PersonDocAssociationId.class)
public class PersonDocAssociation {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Id
	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "DISCARD_STATUS")
	private String discardStatus;

	@Column(name = "DISSASSOCIATION_DATE")
	private Date dissassociationDate;

	@Column(name = "ASSOCIATION_STATUS_CODE")
	private String associationStatusCode;

	@Column(name = "SURV_ASSESS_STATUS")
	private Integer survAssessStatus;
}
