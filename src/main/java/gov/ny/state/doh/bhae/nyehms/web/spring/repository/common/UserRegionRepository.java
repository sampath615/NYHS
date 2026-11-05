package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.UserRegion;

/** The workers' regions and roles (table USER_REGION). */
public interface UserRegionRepository extends JpaRepository<UserRegion, Long> {

	/**
	 * The number of roles (not ended by {@code now}) of the current worker with this short name (ignoring case); a
	 * join of two entities, hence JPQL.
	 */
	@Query("""
			select count(ur.roleCode) from UserRegion ur, NyehmsUsersVw nu
			where upper(nu.legacyUid) = upper(?1) and nu.endDate is null and (ur.endDate is null or ur.endDate > ?2)
			  and nu.workerId = ur.workerId""")
	long countActiveRoles(String legacyUid, Timestamp now);

	/** The role descriptions (distinct) of a worker region; a join of two entities, hence JPQL. */
	@Query("select distinct r.roleDescription from CodedRole r, UserRegion u where u.roleCode = r.roleCode and u.workerRegionId = ?1")
	List<String> findRoleDescriptions(Long workerRegionId);
}
