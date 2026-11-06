package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The reasons a document is flagged for LAFT follow-up (table CODED_LAFT_FOLLOWUP_REASON).
 * <p>
 * Read-only. <b>REASON_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAFT_FOLLOWUP_REASON")
public class CodedLaftFollowupReason {

	@Id
	@Column(name = "REASON_ID")
	private Long reasonId;

	@Column(name = "REASON_DESC")
	private String reasonDesc;
}
