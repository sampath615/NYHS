package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAhiDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiDocRow;

/** The AHI forms (table STD_AHI_DOC). */
public interface StdAhiDocRepository extends JpaRepository<StdAhiDoc, Long> {

	/**
	 * A form for the AHI document screen: with its document's parent and the AHI flag of the assignment whose AHI_DOC_ID
	 * is the form (a scalar subquery: more than one such assignment fails, as before); one row per open person
	 * association of the document (one row if there is none).
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiDocRow(s, d.parentDocumentId,
					(select sa.ahiFlag from SurveillanceAssignment sa where sa.ahiDocId = ?1))
			from StdAhiDoc s
			join DocumentLocator d on s.documentId = d.documentId and s.documentId = ?1
			left join PersonDocAssociation p on s.documentId = p.documentId and p.dissassociationDate is null
			""")
	List<AhiDocRow> findDocument(Long documentId);
}
