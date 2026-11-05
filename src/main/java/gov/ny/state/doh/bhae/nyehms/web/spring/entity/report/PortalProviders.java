package gov.ny.state.doh.bhae.nyehms.web.spring.entity.report;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The provider portal's providers (table PORTAL_PROVIDERS): their portal id, name and HCS id; the columns the provider
 * portal reports read.
 * <p>
 * Read-only and read through JPQL projections only (a key that repeats does not matter then). <b>PORTAL_PROV_ID as the
 * key is assumed.</b> PORTAL_PROV_ID is a number, read as a BigDecimal (as JDBC getObject read it).
 */
@Entity
@Immutable
@Getter
@Table(name = "PORTAL_PROVIDERS")
public class PortalProviders {

	@Id
	@Column(name = "PORTAL_PROV_ID")
	private BigDecimal portalProvId;

	@Column(name = "PROVIDER_FNAME")
	private String providerFname;

	@Column(name = "PROVIDER_MNAME")
	private String providerMname;

	@Column(name = "PROVIDER_LNAME")
	private String providerLname;

	@Column(name = "PROVIDER_HCSID")
	private String providerHcsid;
}
