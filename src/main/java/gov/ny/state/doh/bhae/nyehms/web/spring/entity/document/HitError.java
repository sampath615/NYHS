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
 * A HIT payload that failed to load (table HIT_ERROR): the UPHN transaction, the payload, the error, the record type
 * and the reprise flag / date / comments the HIT error screen sets. Inserted with its key from HIT_ERROR_SEQ (as the
 * former insert) and changed by bulk JPQL updates (HITDocumentDAO). <b>HIT_ERROR_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "HIT_ERROR")
public class HitError {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hitErrorSeq")
	@SequenceGenerator(name = "hitErrorSeq", sequenceName = "HIT_ERROR_SEQ", allocationSize = 1)
	@Column(name = "HIT_ERROR_ID")
	private Long hitErrorId;

	@Column(name = "UPHN_TRANX_IN_ID")
	private Integer uphnTranxInId;

	@Column(name = "PAYLOAD_CLEARTEXT")
	private String payloadCleartext;

	@Column(name = "ERROR_MESSAGE")
	private String errorMessage;

	@Column(name = "CREATE_DATETIME")
	private Timestamp createDatetime;

	@Column(name = "RECORD_TYPE")
	private String recordType;

	@Column(name = "REPRISE_FLAG")
	private String repriseFlag;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "DATE_OF_REPRISE", insertable = false)
	private Timestamp dateOfReprise;
}
