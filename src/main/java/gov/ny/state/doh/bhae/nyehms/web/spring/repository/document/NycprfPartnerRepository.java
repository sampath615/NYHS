package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.repository.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartnerId;

/**
 * Spring Data JPA repository for NYC PRF partners (replaces the MyBatis NycPRFPartnerDAO).
 */
public interface NycprfPartnerRepository extends Repository<StdNycprfPartner, StdNycprfPartnerId>, NycprfPartnerQueries {
}
