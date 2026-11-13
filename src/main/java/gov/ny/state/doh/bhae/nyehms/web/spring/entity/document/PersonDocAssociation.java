package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Link between a document and the person (STATENO) it was matched to (table
 * PERSON_DOC_ASSOCIATION); an association is current while DISSASSOCIATION_DATE is null.
 * Only the columns the application reads and writes.
 * <p>
 * Read in JPQL projections (the document screens: NYC PRF, Old Cases, Medicaid old, NDI, CLIMS, Black Box...),
 * inserted by the scanning (ScanDocumentDAO) and deleted by document id. <b>PERSON_DOC_ASSOCIATION_ID as the key is
 * assumed</b>; new rows take it from PERSON_DOC_ASSOCIATION_SEQ, as the former insert did (NEXTVAL). ASSOCIATION_DATE
 * of a new row is the database's SYSDATE (left null); DISCARD_STATUS and DISSASSOCIATION_DATE are not written on insert
 * (the former insert did not list them).
 * NonCaseDocumentDAO also changes DISCARD_STATUS and SURV_ASSESS_STATUS by bulk JPQL updates (hence not @Immutable).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "PERSON_DOC_ASSOCIATION")
public class PersonDocAssociation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personDocAssociationSeq")
	@SequenceGenerator(name = "personDocAssociationSeq", sequenceName = "PERSON_DOC_ASSOCIATION_SEQ", allocationSize = 1)
	@Column(name = "PERSON_DOC_ASSOCIATION_ID")
	private Long personDocAssociationId;

	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "ASSOCIATION_STATUS_CODE")
	private String associationStatusCode;

	@Column(name = "WORKER_ID")
	private Long workerId;

	/** SYSDATE when inserted (left null). */
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "ASSOCIATION_DATE")
	private Timestamp associationDate;

	@Column(name = "DISCARD_STATUS", insertable = false)
	private String discardStatus;

	/** Set with its time (formerly SYSDATE) when an association ends (CommonDocLabsDAO, bulk updates); not written on insert. */
	@Column(name = "DISSASSOCIATION_DATE", insertable = false)
	private Timestamp dissassociationDate;


	@Column(name = "SURV_ASSESS_STATUS")
	private Integer survAssessStatus;

	/** Set with the assignment of an edited document's copy (DocumentEditLabsDAO, a bulk update); not written on insert. */
	@Column(name = "PS_ASSESS_STATUS", insertable = false)
	private Integer psAssessStatus;

	/** Set with the assignment of an edited document's copy (DocumentEditLabsDAO, a bulk update); not written on insert. */
	@Column(name = "SURV_ASSIGNMENT_ID", insertable = false)
	private Long survAssignmentId;
}
