package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.ProviderPortalModule;

/** The provider portal modules (table PROVIDER_PORTAL_MODULES). */
public interface ProviderPortalModuleRepository extends JpaRepository<ProviderPortalModule, String> {
}
