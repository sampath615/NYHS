package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.AssignmentActivity;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import lombok.RequiredArgsConstructor;

/**
 * Editing a document: the edited copy of a document written into its raw and standardized tables, the person
 * association and the assignments moved to the copy (formerly a MyBatis mapper with the same name and methods; the
 * callers are unchanged).
 * <p>
 * The 29 copy inserts stay native ({@link DocumentEditLabsSql}, the mapper's text with bound values): each writes
 * 15 to 150 columns of a raw or standardized table, converting every value in the database ({@code upper(trim(...))},
 * {@code FN_TO_DATE}, {@code SYSDATE}); the tables have no entities with these columns, and the conversions would have
 * to be re-implemented in Java for each. JPQL over the entities PersonDocAssociation, SurveillanceAssignment and
 * AssignmentActivity for the rest; the former {@code insert ... select ... union all} of the activities is one entity
 * insert per assignment in one transaction (ASSIGNMENT_ACTIVITY_ID from the sequence, ACTIVITY_DATE SYSDATE, as before).
 */
@Repository
@RequiredArgsConstructor
public class DocumentEditLabsDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	public int insertRawEditedTbDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_TB_DOCUMENT, DocumentEditLabsSql.insertRawEditedTbDocumentArgs(rawDocData));
	}

	public int insertStdEditedTbDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_TB_DOCUMENT, DocumentEditLabsSql.insertStdEditedTbDocumentArgs(stdDocData));
	}

	public int insertRawEditedStdDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_STD_DOCUMENT, DocumentEditLabsSql.insertRawEditedStdDocumentArgs(rawDocData));
	}

	public int insertRawEditedAirsDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_AIRS_DOCUMENT, DocumentEditLabsSql.insertRawEditedAirsDocumentArgs(rawDocData));
	}

	public int insertStdEditedStdDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_STD_DOCUMENT, DocumentEditLabsSql.insertStdEditedStdDocumentArgs(stdDocData));
	}

	public int insertStdEditedAirsDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_AIRS_DOCUMENT, DocumentEditLabsSql.insertStdEditedAirsDocumentArgs(stdDocData));
	}

	public int insertRawEditedNycIJRDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_NYC_IJR_DOCUMENT, DocumentEditLabsSql.insertRawEditedNycIJRDocumentArgs(rawDocData));
	}

	public int insertStdEditedNycIJRDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_NYC_IJR_DOCUMENT, DocumentEditLabsSql.insertStdEditedNycIJRDocumentArgs(stdDocData));
	}

	public int insertRawEditedNDIDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_NDI_DOCUMENT, DocumentEditLabsSql.insertRawEditedNDIDocumentArgs(rawDocData));
	}

	public int insertRawEditedSSDMDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_SSDM_DOCUMENT, DocumentEditLabsSql.insertRawEditedSSDMDocumentArgs(rawDocData));
	}

	public int insertStdEditedNDIDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_NDI_DOCUMENT, DocumentEditLabsSql.insertStdEditedNDIDocumentArgs(stdDocData));
	}

	public int insertStdEditedSSDMDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_SSDM_DOCUMENT, DocumentEditLabsSql.insertStdEditedSSDMDocumentArgs(stdDocData));
	}

	public int insertRawEditedInsVitalDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_INS_VITAL_DOCUMENT, DocumentEditLabsSql.insertRawEditedInsVitalDocumentArgs(rawDocData));
	}

	public int insertStdEditedInsVitalDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_INS_VITAL_DOCUMENT, DocumentEditLabsSql.insertStdEditedInsVitalDocumentArgs(stdDocData));
	}

	public int insertRawEditedCasaDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_CASA_DOCUMENT, DocumentEditLabsSql.insertRawEditedCasaDocumentArgs(rawDocData));
	}

	public int insertStdEditedCasaDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_CASA_DOCUMENT, DocumentEditLabsSql.insertStdEditedCasaDocumentArgs(stdDocData));
	}

	public int insertRawEditedEprfOOCDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_EPRF_OOC_DOCUMENT, DocumentEditLabsSql.insertRawEditedEprfOOCDocumentArgs(rawDocData));
	}

	public int insertStdEditedEprfOOCDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_EPRF_OOC_DOCUMENT, DocumentEditLabsSql.insertStdEditedEprfOOCDocumentArgs(stdDocData));
	}

	public int insertRawEditedNycLabDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_NYC_LAB_DOCUMENT, DocumentEditLabsSql.insertRawEditedNycLabDocumentArgs(rawDocData));
	}

	public int insertStdEditedNycLabDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_NYC_LAB_DOCUMENT, DocumentEditLabsSql.insertStdEditedNycLabDocumentArgs(stdDocData));
	}

	public int insertRawEditedNycEharsDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_NYC_EHARS_DOCUMENT, DocumentEditLabsSql.insertRawEditedNycEharsDocumentArgs(rawDocData));
	}

	public int insertStdEditedNycEharsDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_NYC_EHARS_DOCUMENT, DocumentEditLabsSql.insertStdEditedNycEharsDocumentArgs(stdDocData));
	}

	public int insertRawEditedHIVLabDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_HIV_LAB_DOCUMENT, DocumentEditLabsSql.insertRawEditedHIVLabDocumentArgs(rawDocData));
	}

	public int insertStdEditedHivLabDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_HIV_LAB_DOCUMENT, DocumentEditLabsSql.insertStdEditedHivLabDocumentArgs(stdDocData));
	}

	public int insertRawEditedClusterDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_CLUSTER_DOCUMENT, DocumentEditLabsSql.insertRawEditedClusterDocumentArgs(rawDocData));
	}

	public int insertStdEditedClusterDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_CLUSTER_DOCUMENT, DocumentEditLabsSql.insertStdEditedClusterDocumentArgs(stdDocData));
	}

	public int insertRawEditedOldCasesDocument(Map<Object, Object> rawDocData) {
		return statements.update(DocumentEditLabsSql.RAW_EDITED_OLD_CASES_DOCUMENT, DocumentEditLabsSql.insertRawEditedOldCasesDocumentArgs(rawDocData));
	}

	public int insertStdEditedOldCasesDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.STD_EDITED_OLD_CASES_DOCUMENT, DocumentEditLabsSql.insertStdEditedOldCasesDocumentArgs(stdDocData));
	}

	public int insertEditedTrkAliasDocument(Map<Object, Object> stdDocData) {
		return statements.update(DocumentEditLabsSql.EDITED_TRK_ALIAS_DOCUMENT, DocumentEditLabsSql.insertEditedTrkAliasDocumentArgs(stdDocData));
	}

	// ---- the association and the assignments ------------------------------------------------------------------------

	/**
	 * Sets the assignment columns of a document's person association.
	 *
	 * @param inputParameterMap PS_ASSIGN_STATUS, SURV_ASSIGNMENT_ID, SURV_ASSESS_STATUS, DISCARD_STATUS, DOCUMENT_ID
	 * @return the rows changed
	 */
	public int updatePersonDocAssociationData(Map<String, Object> inputParameterMap) {
		return jpql.update("""
				update PersonDocAssociation a set a.psAssessStatus = ?1, a.survAssignmentId = ?2, a.survAssessStatus = ?3, a.discardStatus = ?4
				where a.documentId = ?5""",
				BoundValues.integer(inputParameterMap.get("PS_ASSIGN_STATUS")), BoundValues.longValue(inputParameterMap.get("SURV_ASSIGNMENT_ID")),
				BoundValues.integer(inputParameterMap.get("SURV_ASSESS_STATUS")), BoundValues.text(inputParameterMap.get("DISCARD_STATUS")),
				BoundValues.integer(inputParameterMap.get("DOCUMENT_ID")));
	}

	/**
	 * Points the assignments of a list to a new triggering document.
	 *
	 * @param inputParameterMap NEW_DOCUMENT_ID, assignmentIds (an array or collection of assignment document ids; an
	 *        empty one fails, as {@code IN ()} did)
	 * @return the rows changed
	 */
	public int updateTriggeringDocumentIdInAssignments(Map<String, Object> inputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(BoundValues.integer(inputParameterMap.get("NEW_DOCUMENT_ID")));
		StringBuilder ids = new StringBuilder();
		for (Object id : inList(inputParameterMap.get("assignmentIds"), "assignmentIds")) {
			parameters.add(BoundValues.longValue(id));
			ids.append(ids.length() > 0 ? ", " : "").append('?').append(parameters.size());
		}
		return jpql.update("update SurveillanceAssignment s set s.triggeringDocId = ?1 where s.documentId in (" + ids + ")", parameters.toArray());
	}

	/** The assignments (their document ids) triggered by a document. */
	public List<Integer> getAssignmentsCreatedForDocument(int documentId) {
		return jpql.list("select s.documentId from SurveillanceAssignment s where s.triggeringDocId = ?1", Rows.scalar(Integer.class), documentId);
	}

	/**
	 * Adds an activity (type, comments, by the worker, now) to each assignment of a list.
	 *
	 * @param inputOutputParameterMap activityType, comments, workerId, assignmentIds (an array or collection of
	 *        assignment document ids)
	 * @return the number of activities added
	 */
	@Transactional
	public int addEditedAssignmentActivity(Map<Object, Object> inputOutputParameterMap) {
		int count = 0;
		Object ids = inputOutputParameterMap.get("assignmentIds");
		for (Object assignmentId : ids == null ? List.of() : inList(ids, "assignmentIds")) {
			count += jpql.insert(AssignmentActivity.builder()
					.activityTypeCode(Values.text(inputOutputParameterMap.get("activityType")))
					.comments(Values.text(inputOutputParameterMap.get("comments")))
					.activityDoneBy(Values.longValue(inputOutputParameterMap.get("workerId")))
					.documentId(Values.longValue(assignmentId))
					.build());
		}
		return count;
	}

	/** The values of an array (also of a primitive type) or collection for {@code IN (...)}: an empty or missing one failed as {@code IN ()}. */
	private static List<Object> inList(Object values, String name) {
		List<Object> list = new ArrayList<>();
		if (values instanceof Collection<?> collection) {
			list.addAll(collection);
		} else if (values != null && values.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(values); i++) {
				list.add(Array.get(values, i));
			}
		}
		if (list.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No values for IN () in " + name + ": " + values);
		}
		return list;
	}
}
