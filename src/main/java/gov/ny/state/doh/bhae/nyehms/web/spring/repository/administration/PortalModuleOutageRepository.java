package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.PortalModuleOutage;

/** The provider portal outage messages (table PORTAL_MODULE_OUTAGE). */
public interface PortalModuleOutageRepository extends JpaRepository<PortalModuleOutage, Long> {

	/** Not deleted and not ended: flagDeleted and (no end date, or the end after the time). */
	List<PortalModuleOutage> findByFlagDeletedAndEndDateIsNullOrFlagDeletedAndEndDateAfter(String flagDeleted, String sameFlagDeleted, Timestamp time);

	/** Deleted or ended: the flag, or the end before the time. */
	List<PortalModuleOutage> findByFlagDeletedOrEndDateBefore(String flagDeleted, Timestamp time);
}
