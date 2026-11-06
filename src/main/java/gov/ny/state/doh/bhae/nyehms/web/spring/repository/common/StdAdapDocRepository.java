package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdAdapDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AdapDocRow;

/** The standardized ADAP records (table STD_ADAP_DOC). */
public interface StdAdapDocRepository extends JpaRepository<StdAdapDoc, Long> {

	/**
	 * A record for the ADAP document screen: with its received record's street and apartment, its document's parent and
	 * its open person associations (one row per open association, one row without person if there is none).
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AdapDocRow(s, r.street, r.apt, d.parentDocumentId, p.stateno, p.discardStatus)
			from RawAdapDoc r
			join StdAdapDoc s on s.documentId = r.documentId and r.documentId = ?1
			join DocumentLocator d on r.documentId = d.documentId
			left join PersonDocAssociation p on r.documentId = p.documentId and p.dissassociationDate is null
			""")
	List<AdapDocRow> findDocument(long documentId);

	/** The ADAP_FILE of a record (a list: none if there is no record). */
	@Query("select s.adapFile from StdAdapDoc s where s.documentId = ?1")
	List<String> findAdapFile(Long documentId);

	/** The files that have records waiting for review (status 17, not reviewed); a null file name is listed as null. */
	@Query("select distinct s.adapFile from StdAdapDoc s where s.reviewDt is null and s.currentProcessStatus = 17")
	List<String> findFileNamesToReview();

	/**
	 * The files none of whose records is in process (status 5 or 17) or shipped by a worker, whose records are not
	 * shipped to NYC (NYC_SHIP_DT empty).
	 */
	@Query("""
			select distinct s.adapFile from StdAdapDoc s
			where coalesce(s.adapFile, 'XX') not in (
				select distinct coalesce(s2.adapFile, 'XX') from StdAdapDoc s2 where s2.currentProcessStatus in (5, 17) or s2.nycShippedBy is not null)
			and s.nycShipDt is null
			""")
	List<String> findFileNamesNotShipped();

	/**
	 * As {@link #findFileNamesNotShipped}, for the records shipped to NYC (NYC_SHIP_DT set) of files that have no NYC
	 * feedback yet.
	 */
	@Query("""
			select distinct s.adapFile from StdAdapDoc s
			where coalesce(s.adapFile, 'XX') not in (
				select distinct coalesce(s2.adapFile, 'XX') from StdAdapDoc s2 where s2.currentProcessStatus in (5, 17) or s2.nycShippedBy is not null)
			and s.nycShipDt is not null and coalesce(s.adapFile, 'XX') not in (select distinct coalesce(f.adapFile, 'XX') from NyehmsAdapNycFeedback f)
			""")
	List<String> findFileNamesShippedWithoutFeedback();

	/** Marks records of a file as shipped to NYC now (the database's SYSDATE) by a worker. */
	@Modifying
	@Query("update StdAdapDoc d set d.nycShipDt = sysdate, d.nycShippedBy = ?1 where d.adapFile = ?2 and d.documentId in ?3")
	int markShippedToNyc(String workerId, String adapFile, Collection<Long> documentIds);

	/** Marks records (of any file) as shipped to NYC now (the database's SYSDATE) by a worker. */
	@Modifying
	@Query("update StdAdapDoc d set d.nycShipDt = sysdate, d.nycShippedBy = ?1 where d.documentId in ?2")
	int markShippedToNyc(String workerId, Collection<Long> documentIds);
}
