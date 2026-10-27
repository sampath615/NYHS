package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdQualityCareDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareRefCode;

/**
 * Spring Data JPA repository for the quality of care records (replaces QualCareDocumentDAO.getQualCareDocument; the
 * import saves the standardized record with saveAndFlush, which replaces QualCareDocumentDAO.writeStdDocData).
 */
public interface QualCareDocRepository extends JpaRepository<StdQualityCareDoc, Integer> {

	/**
	 * A quality of care record for the document screen: the standardized record with the names of its coded columns
	 * and its current person match (if any). The sub-group names are added by QualCareDocumentService (see
	 * {@link #findSubGroupCodes()}).
	 *
	 * @param documentId document id of the record
	 * @return the record, or null if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareDocData(stdQualityCareDoc,
					rawQualityCareDoc.street,
					(select refData.lookupName from CodedQualCareRefData refData
						where refData.refColumn = 'HOUSING_STATUS' and refData.lookupCd = stdQualityCareDoc.housingStatus),
					(select refData.lookupName from CodedQualCareRefData refData
						where refData.refColumn = 'INSURANCE' and refData.lookupCd = stdQualityCareDoc.insurance),
					(select refData.lookupName from CodedQualCareRefData refData
						where refData.refColumn = 'ENROLLMENT' and refData.lookupCd = stdQualityCareDoc.enrollment),
					(select refData.lookupName from CodedQualCareRefData refData
						where refData.refColumn = 'DIAGNOSIS' and refData.lookupCd = stdQualityCareDoc.diagnosis),
					format(stdQualityCareDoc.diagnosisDate as 'MM/dd/yyyy'),
					format(stdQualityCareDoc.dischargeDate as 'MM/dd/yyyy'),
					personDocAssociation.stateno)
			from RawQualityCareDoc rawQualityCareDoc
			join StdQualityCareDoc stdQualityCareDoc
			on stdQualityCareDoc.documentId = rawQualityCareDoc.documentId
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawQualityCareDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawQualityCareDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawQualityCareDoc.documentId = :documentId
			""")
	QualCareDocData findDocument(int documentId);

	/** The codes of the Asian and Hispanic sub-groups. */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareRefCode(refData.refColumn, refData.lookupCd,
					refData.lookupName)
			from CodedQualCareRefData refData
			where refData.refColumn in ('ASIAN_SUB_GROUP', 'HISPANIC_SUB_GROUP')
			""")
	List<QualCareRefCode> findSubGroupCodes();
}
