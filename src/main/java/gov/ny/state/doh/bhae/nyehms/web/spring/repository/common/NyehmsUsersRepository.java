package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsUsers;

/** The application's workers (table NYEHMS_USERS). */
public interface NyehmsUsersRepository extends JpaRepository<NyehmsUsers, Long> {

	/** A worker's name (a projection: a worker id that repeats gives several names, as a join did). */
	record WorkerName(Long workerId, String lastName, String firstName) {
	}

	/** The names of these workers. */
	List<WorkerName> findNamesByWorkerIdIn(Collection<Long> workerIds);
}
