package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The gene sequence of an HIV lab document (table GEN_SEQUENCE): the columns the Sierra job and the resistance-document
 * edit read and write (SierraResponseDAO, RASDocumentDAO), and the ones the HIV lab import writes (LabDocumentDAO: the
 * sequence segments of a document, the copy of a document's rows for a new document).
 * <p>
 * Read with JPQL projections and changed by bulk JPQL statements only (updates, deletes, {@code insert ... values} and
 * {@code insert ... select}); never loaded or saved as an entity. <b>LAB_DOC_ID as the key is assumed</b> (it is not
 * unique: a document has one row per 4,000-character segment of its sequence; the bulk statements work on every row of
 * a lab document, as before).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "GEN_SEQUENCE")
public class GenSequence {

	@Id
	@Column(name = "LAB_DOC_ID")
	private Integer labDocId;

	@Column(name = "GEN_SEQ")
	private String genSeq;

	@Column(name = "RAS_ELIG")
	private String rasElig;

	@Column(name = "VLINK_DATE")
	private Timestamp vlinkDate;

	@Column(name = "SEQ_LEN")
	private Integer seqLen;

	@Column(name = "SRC_ID_NUM")
	private Long srcIdNum;

	@Column(name = "SEGMENTNO")
	private Integer segmentno;

	@Column(name = "SPECIMEN_DATE")
	private Timestamp specimenDate;

	@Column(name = "STDRPTS_ID")
	private Long stdrptsId;
}
