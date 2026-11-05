package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsUsers;

/** The application's workers (table NYEHMS_USERS). */
public interface NyehmsUsersRepository extends JpaRepository<NyehmsUsers, Long> {

	/** A worker's name (a projection: a worker id that repeats gives several names, as a join did). */
	record WorkerName(Long workerId, String lastName, String firstName) {
	}

	/** An e-mail address. */
	record EmailAddr(String emailAddr) {
	}

	/** A short name. */
	record LegacyUid(String legacyUid) {
	}

	/** A worker id. */
	record WorkerId(Long workerId) {
	}

	/** The names of these workers. */
	List<WorkerName> findNamesByWorkerIdIn(Collection<Long> workerIds);

	/** The e-mail addresses of the workers not ended by {@code now} (none without an address). */
	List<EmailAddr> findByEndDateIsNullAndEmailAddrIsNotNullOrEndDateAfterAndEmailAddrIsNotNull(Timestamp now);

	/** The short names of the unlocked workers not ended by {@code now}. */
	List<LegacyUid> findByEndDateIsNullAndIsLockedOrEndDateAfterAndIsLocked(String isLocked, Timestamp now, String isLocked2);

	/** The ids of the workers not ended by {@code now} with this e-mail address. */
	List<WorkerId> findByEndDateIsNullAndEmailAddrOrEndDateAfterAndEmailAddr(String emailAddr, Timestamp now, String emailAddr2);

	/**
	 * The trainers: short name and full name ({@code pkg_trk_common.fn_get_fullname}, a database function, called by
	 * name as before).
	 */
	@Query("select u.legacyUid, function('pkg_trk_common.fn_get_fullname', u.lastName, u.firstName, u.middleName, null) from NyehmsUsers u where u.isTrainer = 'Y'")
	List<Object[]> findTrainers();

	/**
	 * Saves a worker's profile (the current row: END_DATE is null). The three training / approval dates are text
	 * converted by the database's TO_DATE(..., 'MM/DD/YYYY'), as before (null text: null); LAST_UPDATED_DATE is the
	 * database's SYSDATE.
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	@Modifying
	@Query("""
			update NyehmsUsers u set u.firstName = :firstName, u.lastName = :lastName, u.lastUpdatedDate = sysdate,
			  u.lastUpdatedBy = :lastUpdatedBy, u.emailAddr = :emailAddr, u.vpn = :vpn, u.telecommute = :telecommute,
			  u.bureauStartDate = :bureauStartDate, u.positionStartDate = :positionStartDate,
			  u.pcAccessAtLocation = :pcAccessAtLocation, u.bureauName = :bureauName,
			  u.remoteTerminalAccess = :remoteTerminalAccess, u.telecommuteTrngAttestRec = :telecommuteTrngAttestRec,
			  u.confSecurityTrngAttestRec = :confSecurityTrngAttestRec, u.telecommuteTrngBy = :telecommuteTrngBy,
			  u.confSecurityTrngBy = :confSecurityTrngBy, u.adminCountyRegionCd = :adminCountyRegionCd,
			  u.lastConfSecurityTrngDate = function('to_date' as Timestamp, :lastConfSecurityTrngDate, 'MM/DD/YYYY'),
			  u.vpnApproveDate = function('to_date' as Timestamp, :vpnApproveDate, 'MM/DD/YYYY'),
			  u.telecommuteTrngDate = function('to_date' as Timestamp, :telecommuteTrngDate, 'MM/DD/YYYY'),
			  u.isLocked = :isLocked
			where u.workerId = :workerId and u.endDate is null""")
	int updateProfile(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("lastUpdatedBy") Long lastUpdatedBy,
			@Param("emailAddr") String emailAddr, @Param("vpn") String vpn, @Param("telecommute") String telecommute,
			@Param("bureauStartDate") String bureauStartDate, @Param("positionStartDate") String positionStartDate,
			@Param("pcAccessAtLocation") String pcAccessAtLocation, @Param("bureauName") String bureauName,
			@Param("remoteTerminalAccess") String remoteTerminalAccess, @Param("telecommuteTrngAttestRec") String telecommuteTrngAttestRec,
			@Param("confSecurityTrngAttestRec") String confSecurityTrngAttestRec, @Param("telecommuteTrngBy") String telecommuteTrngBy,
			@Param("confSecurityTrngBy") String confSecurityTrngBy, @Param("adminCountyRegionCd") String adminCountyRegionCd,
			@Param("lastConfSecurityTrngDate") String lastConfSecurityTrngDate, @Param("vpnApproveDate") String vpnApproveDate,
			@Param("telecommuteTrngDate") String telecommuteTrngDate, @Param("isLocked") String isLocked, @Param("workerId") Long workerId);
}
