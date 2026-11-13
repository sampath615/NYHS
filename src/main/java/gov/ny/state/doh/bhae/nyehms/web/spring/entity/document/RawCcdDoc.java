package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A CCD payload as received (table RAW_CCD_DOC); only the UPHN transaction id the document viewer reads (the loader's
 * insert into this table stays native, see CCDDocumentDAO). Read-only. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_CCD_DOC")
public class RawCcdDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "UPHN_TRANX_IN_ID")
	private Long uphnTranxInId;
}
