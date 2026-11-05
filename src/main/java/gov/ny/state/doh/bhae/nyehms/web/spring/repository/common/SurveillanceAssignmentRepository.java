package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.SurveillanceAssignment;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAssignmentRow;

/** The surveillance assignments (table SURVEILLANCE_ASSIGNMENT). */
public interface SurveillanceAssignmentRepository extends JpaRepository<SurveillanceAssignment, Long> {

	/**
	 * The assignments of a region whose triggering document has the document type (or test type) and whose status (or
	 * activity type) is the given one, with an AHI flag.
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAssignmentRow(s.stateno, s.ahiUpdateDate, s.ahiUpdateUid)
			from SurveillanceAssignment s, DocumentLocator d
			where s.triggeringDocId = d.documentId
			  and (d.documentTypeCode = :documentType or d.testTypeCd = :documentType)
			  and s.currentRegionCd = :regionId
			  and (s.currentAssignmentStatus = :assignmentStatus or s.currentActivityTypeCode = :assignmentStatus)
			  and s.ahiFlag = :ahiFlag
			""")
	List<AhiAssignmentRow> findAhiAssignments(@Param("documentType") String documentType, @Param("regionId") String regionId,
			@Param("assignmentStatus") String assignmentStatus, @Param("ahiFlag") String ahiFlag);

	/** As {@link #findAhiAssignments}, for the assignments flagged AHI ('Y') that were created after the given time. */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAssignmentRow(s.stateno, s.ahiUpdateDate, s.ahiUpdateUid)
			from SurveillanceAssignment s, DocumentLocator d
			where s.triggeringDocId = d.documentId
			  and (d.documentTypeCode = :documentType or d.testTypeCd = :documentType)
			  and s.currentRegionCd = :regionId
			  and (s.currentAssignmentStatus = :assignmentStatus or s.currentActivityTypeCode = :assignmentStatus)
			  and s.ahiFlag = 'Y' and s.createDate > :createdAfter
			""")
	List<AhiAssignmentRow> findRecentAhiAssignments(@Param("documentType") String documentType, @Param("regionId") String regionId,
			@Param("assignmentStatus") String assignmentStatus, @Param("createdAfter") Timestamp createdAfter);
}
