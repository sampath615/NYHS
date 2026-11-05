package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdSsdmDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SsdmDocData;

/**
 * Spring Data JPA repository for the SSDM document (replaces SSDMDocumentDAO.getSSDMDoc).
 */
public interface SsdmDocRepository extends JpaRepository<StdSsdmDoc, Integer> {

	/**
	 * An SSDM (Social Security Death Master) record for the SSDM document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.SsdmDocData(stdSsdmDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					coalesce(documentLocator.parentDocumentId, 0))
			from StdSsdmDoc stdSsdmDoc
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = stdSsdmDoc.documentId
			and personDocAssociation.dissassociationDate is null
			join DocumentLocator documentLocator
			on documentLocator.documentId = stdSsdmDoc.documentId
			where stdSsdmDoc.documentId = :documentId
			""")
	SsdmDocData findDocument(int documentId);

	/**
	 * The SSDM bulk import: every record waiting in RAW_SSDM_DOC (status 1) inserted as a standardized record (status
	 * 5), with the conversions of the former INSERT ... SELECT, unchanged and done by the database: an SSN that is not
	 * 9 digits (or has a run of five equal digits) is dropped; a date that FN_TO_DATE does not read and is 8 characters
	 * long is written as MM/DD/YYYY with '00' parts as '..'; HASH_NUMBER is ORA_HASH of the names, dates, SSN and
	 * suffix.
	 *
	 * @return the number of rows inserted
	 */
	@Modifying
	@Query("""
			insert into StdSsdmDoc (ssn, personZipAtDeath, ssdmFile, matchPass, birthYear, lname, suffix, fname, mname,
					personCountyAtDeath, matchCd, personStateAtDeath, reviewUid, dobText, dodText, matchedStateno, eharsStatusCd,
					matchWeight, documentId, ssdmSrcId, ageAtDeath, currentProcessStatus, qsParentDocId, reviewDate, createDate,
					eharsImportTime, matchedProcessDate, createUid, origLname, hashNumber)
			select
					case when length(r.ssn) < 9 then ''
						when upper(r.ssn) != lower(r.ssn) then ''
						when r.ssn like '%00000%' or r.ssn like '%11111%' or r.ssn like '%22222%' or r.ssn like '%33333%'
							or r.ssn like '%44444%' or r.ssn like '%55555%' or r.ssn like '%66666%' or r.ssn like '%77777%'
							or r.ssn like '%88888%' or r.ssn like '%99999%' then ''
						when length(r.ssn) = 9 then r.ssn
						else '' end,
					trim(upper(r.zip)),
					trim(r.ssdmFile),
					cast(null as String),
					trim(upper(r.bthyear)),
					trim(upper(r.lastname)),
					trim(upper(r.nsuffix)),
					trim(upper(r.firstname)),
					trim(upper(r.middlename)),
					cast(null as String),
					cast(null as String),
					trim(upper(r.stz)),
					cast(null as String),
					case when r.bthdate is not null and function('FN_TO_DATE', trim(r.bthdate), 'MM/DD/YYYY') is not null
							then function('TO_CHAR' as String, function('FN_TO_DATE', trim(r.bthdate), 'MM/DD/YYYY'), 'MM/DD/YYYY')
						when r.bthdate is not null and function('FN_TO_DATE', trim(r.bthdate), 'MM/DD/YYYY') is null and length(r.bthdate) = 8
							then replace(substring(r.bthdate, 1, 2), '00', '..') || '/' || replace(substring(r.bthdate, 3, 2), '00', '..')
								|| '/' || replace(substring(r.bthdate, 5, 4), '00', '..')
						else null end,
					case when r.dthdate is not null and function('FN_TO_DATE', trim(r.dthdate), 'MM/DD/YYYY') is not null
							then function('TO_CHAR' as String, function('FN_TO_DATE', trim(r.dthdate), 'MM/DD/YYYY'), 'MM/DD/YYYY')
						when r.dthdate is not null and function('FN_TO_DATE', trim(r.dthdate), 'MM/DD/YYYY') is null and length(r.dthdate) = 8
							then replace(substring(r.dthdate, 1, 2), '00', '..') || '/' || replace(substring(r.dthdate, 3, 2), '00', '..')
								|| '/' || replace(substring(r.dthdate, 5, 4), '00', '..')
						else null end,
					cast(null as String),
					cast(null as String),
					cast(null as BigDecimal),
					r.documentId,
					r.ssdmSrcId,
					r.agedeath,
					5,
					cast(null as Long),
					cast(null as Timestamp),
					cast(null as Timestamp),
					cast(null as Timestamp),
					cast(null as Timestamp),
					cast(null as String),
					trim(upper(r.origlname)),
					function('ORA_HASH' as Long,
						trim(upper(r.lastname))
						|| trim(upper(r.firstname))
						|| case when r.bthdate is not null and function('FN_TO_DATE', trim(r.bthdate), 'MM/DD/YYYY') is not null
								then function('TO_CHAR' as String, function('FN_TO_DATE', trim(r.bthdate), 'MM/DD/YYYY'), 'MM/DD/YYYY')
							when r.bthdate is not null and function('FN_TO_DATE', trim(r.bthdate), 'MM/DD/YYYY') is null and length(r.bthdate) = 8
								then replace(substring(r.bthdate, 1, 2), '00', '..') || '/' || replace(substring(r.bthdate, 3, 2), '00', '..')
									|| '/' || replace(substring(r.bthdate, 5, 4), '00', '..')
							else null end
						|| case when r.dthdate is not null and function('FN_TO_DATE', trim(r.dthdate), 'MM/DD/YYYY') is not null
								then function('TO_CHAR' as String, function('FN_TO_DATE', trim(r.dthdate), 'MM/DD/YYYY'), 'MM/DD/YYYY')
							when r.dthdate is not null and function('FN_TO_DATE', trim(r.dthdate), 'MM/DD/YYYY') is null and length(r.dthdate) = 8
								then replace(substring(r.dthdate, 1, 2), '00', '..') || '/' || replace(substring(r.dthdate, 3, 2), '00', '..')
									|| '/' || replace(substring(r.dthdate, 5, 4), '00', '..')
							else null end
						|| trim(upper(r.ssn))
						|| trim(upper(r.nsuffix)))
			from RawSsdmDoc r
			where r.currentProcessStatus = 1
			""")
	int insertWaitingRawRecords();
}
