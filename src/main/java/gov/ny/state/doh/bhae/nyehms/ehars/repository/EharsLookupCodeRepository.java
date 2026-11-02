package gov.ny.state.doh.bhae.nyehms.ehars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.ehars.entity.EharsLookupCode;
import gov.ny.state.doh.bhae.nyehms.ehars.entity.EharsLookupCodeId;
import gov.ny.state.doh.bhae.nyehms.ehars.record.EharsCode;

/**
 * The eHARS codes of the coded fields.
 */
public interface EharsLookupCodeRepository extends JpaRepository<EharsLookupCode, EharsLookupCodeId> {

	/** The codes and names of a coded field, by name. */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.ehars.record.EharsCode(lookup.lookupCd, lookup.lookupName)
			from EharsLookupCode lookup
			where lookup.lookupType = :lookupType
			order by lookup.lookupName
			""")
	List<EharsCode> findCodesOfType(String lookupType);
}
