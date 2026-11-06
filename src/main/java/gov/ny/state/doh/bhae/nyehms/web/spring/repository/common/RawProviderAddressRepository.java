package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawProviderAddress;

/** The raw provider addresses (table RAW_PROVIDER_ADDRESS); searched with specifications built by the DAOs. */
public interface RawProviderAddressRepository extends JpaRepository<RawProviderAddress, Long>, JpaSpecificationExecutor<RawProviderAddress> {

	/** The addresses of providers with one of the NPIs that were not flipped to a facility (ADAPDocumentDAO). */
	List<RawProviderAddress> findByFlippedToFacilityDateIsNullAndNpiIn(Collection<String> npis);
}
