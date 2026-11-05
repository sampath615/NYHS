package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsUsersActivity;

/** The comments on the workers' profiles (NYEHMS_USERS_ACTIVITY). */
public interface NyehmsUsersActivityRepository extends JpaRepository<NyehmsUsersActivity, NyehmsUsersActivity.Key> {

	/** A comment. */
	record Comment(Timestamp activityDate, String activityBy, String comments) {
	}

	/** A worker's comments, latest first. */
	List<Comment> findByWorkerIdOrderByActivityDateDesc(Long workerId);
}
