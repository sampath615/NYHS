package gov.ny.state.doh.bhae.nyehms.web.spring.entity.report;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The users (HCS ids) who upload for a portal provider (table PORTAL_PROVIDER_USER_MAPPING).
 * <p>
 * Read-only and read through JPQL subqueries only. <b>USER_HCSID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "PORTAL_PROVIDER_USER_MAPPING")
public class PortalProviderUserMapping {

	@Id
	@Column(name = "USER_HCSID")
	private String userHcsid;

	@Column(name = "PORTAL_PROV_ID")
	private BigDecimal portalProvId;
}
