package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdStdConfirmedDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfRefData;

/**
 * Spring Data JPA repository for the confirmed STD document (replaces STDConfDocumentDAO: getSTDConfDoc, getSTDConfRefData,
 * getDrugList; the import saves the standardized document with saveAndFlush, which replaces writeRawSTDConfDocDataToStdTable).
 */
public interface StdConfDocRepository extends JpaRepository<StdStdConfirmedDoc, Integer> {

	/**
	 * A confirmed STD (sexually transmitted disease) report for the confirmed STD document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfDocData(stdStdConfirmedDoc,
					personDocAssociation.stateno,
					format(stdStdConfirmedDoc.dob as 'MM/dd/yyyy'),
					format(stdStdConfirmedDoc.diagDate as 'MM/dd/yyyy'),
					format(stdStdConfirmedDoc.dateOfDeath as 'MM/dd/yyyy'),
					format(stdStdConfirmedDoc.x900dxdate as 'MM/dd/yyyy'),
					rawStdConfirmedDoc.personAddress,
					rawStdConfirmedDoc.provAddress)
			from RawStdConfirmedDoc rawStdConfirmedDoc
			join StdStdConfirmedDoc stdStdConfirmedDoc
			on stdStdConfirmedDoc.documentId = rawStdConfirmedDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawStdConfirmedDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawStdConfirmedDoc.documentId = :documentId
			""")
	StdConfDocData findDocument(int documentId);

	/**
	 * The code lists of the confirmed STD reports, by coded column and code.
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfRefData(refData.refColumn,
					refData.lookupName, refData.lookupCd)
			from CodedStdConfRefData refData
			order by refData.refColumn, refData.lookupCd
			""")
	List<StdConfRefData> findRefData();

	/**
	 * The codes of one coded column, in no particular order (as before).
	 *
	 * @param refColumn the coded column (e.g. DRUG_USED_LIST)
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfRefData(refData.refColumn,
					refData.lookupName, refData.lookupCd)
			from CodedStdConfRefData refData
			where refData.refColumn = :refColumn
			""")
	List<StdConfRefData> findRefDataOfColumn(String refColumn);
}
