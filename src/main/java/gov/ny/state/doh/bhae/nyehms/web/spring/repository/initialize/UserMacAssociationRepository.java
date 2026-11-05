package gov.ny.state.doh.bhae.nyehms.web.spring.repository.initialize;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.initialize.UserMacAssociation;

/** The workers' MAC addresses (USER_MAC_ASSOCIATION). */
public interface UserMacAssociationRepository extends JpaRepository<UserMacAssociation, String> {

	/**
	 * The MAC addresses in effect at {@code now} of the current worker with this short name (ignoring case); a join
	 * of two entities, hence JPQL.
	 */
	@Query("""
			select ma.macAddress from UserMacAssociation ma, NyehmsUsersVw nu
			where upper(nu.legacyUid) = upper(?1) and nu.endDate is null and ?2 >= ma.effectiveStartDate
			  and (ma.effectiveEndDate is null or ma.effectiveEndDate > ?2) and ma.userId = nu.workerId""")
	List<String> findActiveMacAddresses(String legacyUid, Timestamp now);
}
