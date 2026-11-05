package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsUsersVw;

/** The workers' view (NYEHMS_USERS_VW); short names are compared ignoring case (UPPER on both sides). */
public interface NyehmsUsersVwRepository extends JpaRepository<NyehmsUsersVw, Long> {

	/** A lock flag. */
	record LockFlag(String isLocked) {
	}

	/** A worker id. */
	record WorkerId(Long workerId) {
	}

	/** The lock flags of the current (not ended) rows of a short name. */
	List<LockFlag> findByLegacyUidIgnoreCaseAndEndDateIsNull(String legacyUid);

	/** The worker ids of a short name not ended by {@code now}. */
	List<WorkerId> findByLegacyUidIgnoreCaseAndEndDateIsNullOrLegacyUidIgnoreCaseAndEndDateAfter(String legacyUid, String legacyUid2, Timestamp now);
}
