package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

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
 * A run of the HIT payload loader (table HIT_LOAD_LOG): the document type, the UPHN transaction id range checked and
 * retrieved, the counts. Inserted with its key from HIT_LOAD_LOG_SEQ (as the former insert's selectKey) and changed by
 * bulk JPQL updates (HITDocumentDAO). <b>LOG_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "HIT_LOAD_LOG")
public class HitLoadLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hitLoadLogSeq")
	@SequenceGenerator(name = "hitLoadLogSeq", sequenceName = "HIT_LOAD_LOG_SEQ", allocationSize = 1)
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "DOC_TYPE")
	private String docType;

	@Column(name = "MIN_UTII_CHECKED")
	private Integer minUtiiChecked;

	@Column(name = "MIN_UTII_RETRIEVED")
	private Integer minUtiiRetrieved;

	@Column(name = "MAX_UTII_RETRIEVED")
	private Integer maxUtiiRetrieved;

	@Column(name = "MAX_UTII_CHECKED")
	private Integer maxUtiiChecked;

	@Column(name = "NO_OF_FAILED_DOCS")
	private Integer noOfFailedDocs;

	@Column(name = "NO_OF_RETRIEVED_DOCS")
	private Integer noOfRetrievedDocs;

	@Column(name = "NO_OF_SUCCESSFUL_DOCS")
	private Integer noOfSuccessfulDocs;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CREATE_ID")
	private String createId;
}
