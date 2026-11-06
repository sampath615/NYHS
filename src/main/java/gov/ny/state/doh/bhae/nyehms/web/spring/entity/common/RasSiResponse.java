package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

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
 * The header of a Sierra (HIVdb) resistance response for a lab document (table RAS_SI_RESPONSE), as the Sierra job
 * writes it and the RAS document screen reads it (SierraResponseDAO, RASDocumentDAO).
 * <p>
 * Inserted once with its key from RAS_SEQ_ID (as the former insert took RAS_SEQ_ID.NEXTVAL), never changed;
 * RUN_DATE is the database's SYSDATE, as before. HEADER is written as the text the job passes (as MyBatis bound it);
 * DOCUMENT_ID is the same header as a number. <b>SEQ_RESULT_ID as the key is assumed</b> (filled from the sequence).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAS_SI_RESPONSE")
public class RasSiResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RAS_SEQ_ID")
	@SequenceGenerator(name = "RAS_SEQ_ID", sequenceName = "RAS_SEQ_ID", allocationSize = 1)
	@Column(name = "SEQ_RESULT_ID")
	private Long seqResultId;

	@Column(name = "HEADER")
	private String header;

	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "VERSION")
	private String version;

	@Column(name = "PUBLISH_DATE")
	private String publishDate;

	@Column(name = "MD5")
	private String md5;

	@Column(name = "SHA521")
	private String sha521;

	@Column(name = "FIRSTNA")
	private String firstna;

	@Column(name = "SUB_TYPE")
	private String subType;

	@Column(name = "RUN_DATE")
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	private Timestamp runDate;
}
