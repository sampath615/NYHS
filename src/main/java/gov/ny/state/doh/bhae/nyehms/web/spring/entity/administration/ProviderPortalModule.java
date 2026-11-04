package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A provider portal module (table PROVIDER_PORTAL_MODULES), the choices of the portal outage message screen.
 * Read-only. <b>PP_MODULE_CD as the key is assumed</b>; it is text (the screen posts it, and the service compares it
 * with "ADMIN_PAGE", "EPRF_DE", ...).
 */
@Entity
@Immutable
@Getter
@Table(name = "PROVIDER_PORTAL_MODULES")
public class ProviderPortalModule {

	@Id
	@Column(name = "PP_MODULE_CD")
	private String ppModuleCd;

	@Column(name = "PP_MODULE_NAME")
	private String ppModuleName;
}
