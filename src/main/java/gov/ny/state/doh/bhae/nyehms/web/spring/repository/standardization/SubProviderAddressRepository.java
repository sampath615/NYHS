package gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization.SubProviderAddress;

/** The standardized provider addresses (table SUB_PROVIDER_ADDRESS). */
public interface SubProviderAddressRepository extends JpaRepository<SubProviderAddress, Long> {
}
