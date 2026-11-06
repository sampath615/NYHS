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
 * edit read and write (SierraResponseDAO, RASDocumentDAO).
 * <p>
 * Read with JPQL projections and changed by bulk JPQL updates only. <b>LAB_DOC_ID as the key is assumed</b> (it is
 * not known to be unique; the updates change every row of a lab document, as before).
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
}
