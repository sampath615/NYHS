package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawHieDeathDoc;

/** The received HIE death records (table RAW_HIE_DEATH_DOC) and the scheduler's checks of them. */
public interface RawHieDeathDocRepository extends JpaRepository<RawHieDeathDoc, Integer> {

	/**
	 * Marks as duplicates (status 2) the waiting records (status 1) of an MPI that is already standardized and that was
	 * received more than once with the same names, MPI, dates, medical record number and facility.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("""
			update RawHieDeathDoc d set d.currentProcessStatus = 2
			where d.mpiid in (select r.mpiid from RawHieDeathDoc r
			                  where exists (select 1 from StdHieDeathDoc s where s.mpiid = r.mpiid)
			                  group by r.fname, r.lname, r.mpiid, r.dodText, r.medicalRecordNumber, r.sourceFacility, r.dobText
			                  having count(*) > 1)
			and d.currentProcessStatus = 1
			""")
	int markDuplicatesOfStandardized();

	/**
	 * Records the MPI pairs (received MPI, CCD MPI) of a person with the same names and date of birth but another MPI,
	 * unless the PL/SQL function MPI_PAIR_EXISTS knows the pair or MPI_CHANGE holds it; DATE_RECEIVED the database's
	 * SYSDATE.
	 *
	 * @return the number of rows inserted
	 */
	@Modifying
	@Query("""
			insert into MpiChange (currentMpi, oldMpi, dateReceived)
			select distinct r.mpiid, s.mpi, sysdate
			from RawHieDeathDoc r, StdCcdDoc s
			where r.fname = s.fname and r.lname = s.lname and r.dobText = s.dobText and s.mpi != r.mpiid
			and function('mpi_pair_exists' as Integer, r.mpiid, s.mpi) != 1
			and not exists (select 1 from MpiChange m where m.currentMpi = r.mpiid and m.oldMpi = s.mpi)
			""")
	int insertNewMpiPairs();

	/**
	 * Sets the status of the open records (1, 36, 37) from the CCD documents: 36 if a CCD of the same MPI, or of the
	 * same person under another MPI, is a dataflux non-match (N, C_N, P_N); else 37 if no CCD has the person or the MPI;
	 * else 1.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("""
			update RawHieDeathDoc r set r.currentProcessStatus = case
			  when exists (select 1 from StdCcdDoc c where c.mpi = r.mpiid and c.fluxMatchCd in ('N', 'C_N', 'P_N'))
			    or exists (select 1 from StdCcdDoc c where r.fname = c.fname and r.lname = c.lname and r.dobText = c.dobText
			               and r.mpiid != c.mpi and c.fluxMatchCd in ('N', 'C_N', 'P_N'))
			  then 36
			  when not exists (select 1 from StdCcdDoc c where (c.fname = r.fname and c.lname = r.lname and c.dobText = r.dobText)
			                   or (c.mpi = r.mpiid))
			  then 37
			  else 1
			  end
			where r.currentProcessStatus in (1, 36, 37)
			""")
	int updateStatusFromCcd();
}
