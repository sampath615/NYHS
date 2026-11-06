package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RasSequenceLinks;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RasSequenceLinksId;

/** The links between resistance sequences and lab documents (table RASSEQUENCELINKS). */
public interface RasSequenceLinksRepository extends JpaRepository<RasSequenceLinks, RasSequenceLinksId> {

	/** BHAEGENESUM of the links of a lab document (one value per link). */
	@Query("select link.bhaegenesum from RasSequenceLinks link where link.labDocId = ?1")
	List<Integer> geneSums(int labDocId);

	/**
	 * Deactivates (status 2, reason RES_DEL_EDIT, the database's SYSDATE) every link of the sequences a lab document is
	 * linked to.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("""
			update RasSequenceLinks link set link.vseqstatus = 2, link.vseqdeactivationdate = sysdate,
					link.vseqdeactivationuser = ?1, link.vseqdeactivationreason = 'RES_DEL_EDIT'
			where link.vseqid in (select other.vseqid from RasSequenceLinks other where other.labDocId = ?2)
			""")
	int deactivateSequencesOf(Long workerId, Integer labDocId);

	/**
	 * The lab documents linked to the active sequence (status 1) of a lab document. The sequence is a scalar subquery,
	 * as before: it fails (ORA-01427) when the lab document has more than one active link.
	 */
	@Query("""
			select link.labDocId from RasSequenceLinks link
			where link.vseqid = (select active.vseqid from RasSequenceLinks active
					where active.labDocId = ?1 and active.vseqstatus = 1)
			""")
	List<Integer> labDocsOfActiveSequence(int labDocId);
}
