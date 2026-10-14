package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * A partner as received on an NYC Partner Referral Form (table RAW_NYCPRF_PARTNER); only the
 * columns the application reads.
 * <p>
 * Read-only and queried through JPQL projections only. <b>The key is assumed</b> (the same
 * partner can be received more than once), see {@link StdNycprfPartner}.
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_NYCPRF_PARTNER")
@IdClass(RawNycprfPartnerId.class)
public class RawNycprfPartner {

	@Id
	@Column(name = "PATIENTID")
	private String patientId;

	@Id
	@Column(name = "NYCPRF_DOCUMENT_ID")
	private Long nycprfDocumentId;

	@Column(name = "PRTADDRESS")
	private String address;
}
