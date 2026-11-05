package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawSsdmDoc;

/** The SSDM records as received (table RAW_SSDM_DOC). */
public interface RawSsdmDocRepository extends JpaRepository<RawSsdmDoc, Integer> {

	/**
	 * Marks the records waiting for the import (status 1) as moved (status 5), PROCESS_DATE the database's SYSDATE.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("update RawSsdmDoc r set r.currentProcessStatus = 5, r.processDate = sysdate where r.currentProcessStatus = 1")
	int markMoved();

	/** Rejects (status 43) the waiting records whose first and last names are both empty or 'UNKNOWN'. */
	@Modifying
	@Query("""
			update RawSsdmDoc r set r.currentProcessStatus = 43
			where (upper(trim(r.firstname)) = 'UNKNOWN' or trim(r.firstname) is null)
			and (upper(trim(r.lastname)) = 'UNKNOWN' or trim(r.lastname) is null)
			and r.currentProcessStatus = 1
			""")
	int rejectUnknownNames();
}
