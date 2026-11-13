package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The dispositions of a sent partner services message (table CODED_SENT_MESSAGE_DISPOSITION), read-only.
 * <b>SENT_DISPOSITION_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_SENT_MESSAGE_DISPOSITION")
public class CodedSentMessageDisposition {

	@Id
	@Column(name = "SENT_DISPOSITION_CODE")
	private String sentDispositionCode;

	@Column(name = "SENT_DISPOSITION_DESC")
	private String sentDispositionDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;

	@Column(name = "DROP_DOWN")
	private String dropDown;

	@Column(name = "RECORD_SEARCH_FLAG")
	private String recordSearchFlag;
}
