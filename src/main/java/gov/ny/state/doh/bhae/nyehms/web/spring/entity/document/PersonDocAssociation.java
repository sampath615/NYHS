package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

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
 * Read-only and used in JPQL projections only. <b>The key (DOCUMENT_ID, STATENO) is assumed</b> —
 * confirm before loading or writing it as an entity. Shared by the document screens (NYC PRF, and
 * next Old Cases, Medicaid old, NDI, CLIMS, Black Box...).
 */
@Entity
@Immutable
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
}
