package gov.ny.state.doh.bhae.nyehms.web.spring.dao.initialize;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.timestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.FirefoxVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.JREVersionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize.UserAccessAttemptVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.initialize.UserAccessAttempts;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsParameterRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsUsersVwRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.UserRegionRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.initialize.NyehmsFirefoxVersionsRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.initialize.NyehmsJreVersionsRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.initialize.UserAccessAttemptsRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.initialize.UserMacAssociationRepository;
import lombok.RequiredArgsConstructor;

/**
 * The login checks (formerly a MyBatis mapper with the same name and methods), through Spring Data repositories.
 * <p>
 * Short names are compared ignoring case (UPPER on both sides, as before); a null short name matched nothing. "Now"
 * in the effective-date conditions is the application server's clock (formerly the database's SYSDATE). The MAC
 * address, JRE and OS checks belonged to the removed applet: their service methods are no longer called, they are
 * kept unchanged.
 */
@Repository
@RequiredArgsConstructor
public class InitializeDAO {

	private final UserMacAssociationRepository macAddresses;
	private final NyehmsUsersVwRepository users;
	private final UserRegionRepository userRegions;
	private final NyehmsJreVersionsRepository jreVersions;
	private final NyehmsFirefoxVersionsRepository firefoxVersions;
	private final NyehmsParameterRepository parameters;
	private final UserAccessAttemptsRepository accessAttempts;

	/** The MAC addresses in effect of the current worker with this short name. */
	public List<String> getActiveMACAddressesList(String userId) throws DataAccessException {
		return new ArrayList<>(macAddresses.findActiveMacAddresses(userId, now()));
	}

	/**
	 * The lock flag of the current worker with this short name.
	 *
	 * @return the flag, or null if there is no such worker
	 * @throws IncorrectResultSizeDataAccessException if the short name has several current rows (as before)
	 */
	public String getUserLockInfo(String userId) throws DataAccessException {
		if (userId == null) {
			return null;
		}
		return one(users.findByLegacyUidIgnoreCaseAndEndDateIsNull(userId).stream().map(NyehmsUsersVwRepository.LockFlag::isLocked).toList());
	}

	/** The number of roles in effect of the current worker with this short name. */
	public int getActiveRoleCount(String userId) throws DataAccessException {
		return (int) userRegions.countActiveRoles(userId, now());
	}

	/** The Java versions in effect (a row without values was null, as before). */
	public List<JREVersionVO> getActiveJREs() throws DataAccessException {
		Timestamp now = now();
		return jreVersions
				.findByEffectiveStartDateLessThanEqualAndEffectiveEndDateIsNullOrEffectiveStartDateLessThanEqualAndEffectiveEndDateAfter(now, now, now)
				.stream().map(version -> {
					if (version.jreVersion() == null && version.rejectFlag() == null) {
						return null;
					}
					JREVersionVO vo = new JREVersionVO();
					vo.setJREVersionNo(version.jreVersion());
					vo.setRejectFlag(version.rejectFlag());
					return vo;
				}).collect(Collectors.toCollection(ArrayList::new));
	}

	/** The operating systems in effect (parameter VALID_OS). */
	public List<String> getActiveOSs() throws DataAccessException {
		Timestamp now = now();
		return parameters.findByParamNameAndEffectiveStartDateLessThanEqualAndEffectiveEndDateIsNullOrParamNameAndEffectiveStartDateLessThanEqualAndEffectiveEndDateAfter(
				"VALID_OS", now, "VALID_OS", now, now).stream().map(NyehmsParameterRepository.ParamValue::paramValue)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/** The Firefox versions in effect (a row without values was null, as before). */
	public List<FirefoxVersionVO> getValidFirefoxVer() throws DataAccessException {
		Timestamp now = now();
		return firefoxVersions
				.findByEffectiveStartDateLessThanEqualAndEffectiveEndDateIsNullOrEffectiveStartDateLessThanEqualAndEffectiveEndDateAfter(now, now, now)
				.stream().map(version -> {
					if (version.version() == null && version.rejectFlag() == null) {
						return null;
					}
					FirefoxVersionVO vo = new FirefoxVersionVO();
					vo.setFirefoxVersionNo(version.version());
					vo.setRejectFlag(version.rejectFlag());
					return vo;
				}).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * The Firefox version with the latest start date.
	 *
	 * @throws IncorrectResultSizeDataAccessException if several versions have that date (as before)
	 */
	public String getLatestFirefoxVersion() throws DataAccessException {
		return one(firefoxVersions.findLatestVersions());
	}

	/** Inserts a login attempt (its key from USER_ACCESS_ATTEMPTS_SEQ; an empty text or time is stored as NULL). */
	@Transactional
	public void insertUserAccessAttemp(UserAccessAttemptVO attempt) throws DataAccessException {
		accessAttempts.saveAndFlush(UserAccessAttempts.builder()
				.userId(emptyAsNull(attempt.getUserId()))
				.hcsRole(emptyAsNull(attempt.getHcsRole()))
				.linkClickTime(timestamp(attempt.getLinkClickTime()))
				.activeMacAddressList(emptyAsNull(attempt.getActiveMACAddresses()))
				.hasNyehmsAccess(emptyAsNull(attempt.getActiveNyehmsRole()))
				.userLocked(emptyAsNull(attempt.getUserLocked()))
				.attemptMacAddress(emptyAsNull(attempt.getAttempMACAddress()))
				.browserUsed(emptyAsNull(attempt.getBrowserName()))
				.browserVersion(emptyAsNull(attempt.getBrowserVersion()))
				.hasXpiPlugin(emptyAsNull(attempt.getXPIPlugin()))
				.xpiVersion(emptyAsNull(attempt.getXPIVersion()))
				.osUsed(emptyAsNull(attempt.getOSName()))
				.jreVersion(emptyAsNull(attempt.getJREVersion()))
				.recordCreatedDate(timestamp(attempt.getRecordInsertTime()))
				.reasonsAttemptFailed(emptyAsNull(attempt.getAttemptFailedReasons()))
				.build());
	}

	/**
	 * The Java version with the latest start date.
	 *
	 * @throws IncorrectResultSizeDataAccessException if several versions have that date (as before)
	 */
	public String getLatestJavaVersionNumber() throws DataAccessException {
		return one(jreVersions.findLatestVersions());
	}

	/** The single result of a former selectOne: null for none, an error for several. */
	private static <T> T one(List<T> results) {
		if (results.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}
		return results.isEmpty() ? null : results.get(0);
	}

	/** '' was stored as NULL by Oracle. */
	private static String emptyAsNull(Object value) {
		String text = text(value);
		return text == null || text.isEmpty() ? null : text;
	}

	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
}
