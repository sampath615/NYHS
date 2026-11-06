package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A link between a resistance sequence and a lab document (table RASSEQUENCELINKS): the columns the
 * resistance-document edit reads and changes (RASDocumentDAO).
 * <p>
 * Read with projections and changed by a bulk JPQL update only, never loaded as an entity: <b>(VSEQID, LAB_DOC_ID) as
 * the key is assumed</b>. VSEQDEACTIVATIONUSER is written with the worker id, as before.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RASSEQUENCELINKS")
@IdClass(RasSequenceLinksId.class)
public class RasSequenceLinks {

	@Id
	@Column(name = "VSEQID")
	private Integer vseqid;

	@Id
	@Column(name = "LAB_DOC_ID")
	private Integer labDocId;

	@Column(name = "VSEQSTATUS")
	private Integer vseqstatus;

	@Column(name = "BHAEGENESUM")
	private Integer bhaegenesum;

	@Column(name = "VSEQDEACTIVATIONDATE")
	private Timestamp vseqdeactivationdate;

	@Column(name = "VSEQDEACTIVATIONUSER")
	private Long vseqdeactivationuser;

	@Column(name = "VSEQDEACTIVATIONREASON")
	private String vseqdeactivationreason;
}
