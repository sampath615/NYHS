package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.NYCRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycRejectRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NycSurveillanceRejectRepository;
import lombok.RequiredArgsConstructor;

/**
 * The NYC reject documents: the rejected NYC surveillance records of a reject document and their comments (formerly a
 * MyBatis mapper with the same name and methods), through a Spring Data repository over NycSurveillanceReject,
 * DocumentLocator and CodedNycRejectDisposition. No native SQL: the current state number is the database function
 * PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO, called from JPQL.
 * <p>
 * <b>Kept as before:</b> the details are read by REJECT_DOCUMENT_ID, but the comment change is made by DOCUMENT_ID.
 */
@Repository
@RequiredArgsConstructor
public class NYCRejectDocumentDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY') */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	private final NycSurveillanceRejectRepository rejects;

	/**
	 * The records of a reject document, grouped as the former result map grouped them: one NYCRejectVO per DOCUMENT_ID
	 * (in the order the ids first appear), holding the values of the last row of that id whose values differ from the
	 * rows before it.
	 *
	 * @param inputOutputParameterMap documentId: the reject document id (REJECT_DOCUMENT_ID)
	 */
	public List<NYCRejectVO> getNYCRejectDetails(Map<Object, Object> inputOutputParameterMap) {
		Map<Integer, NYCRejectVO> documents = new LinkedHashMap<>();
		Map<Integer, Set<List<Object>>> seenValues = new LinkedHashMap<>();
		for (NycRejectRow row : rejects.findDetails(integer(inputOutputParameterMap.get("documentId")))) {
			UploadNYCSurvRejectVO reject = toRejectVO(row);
			NYCRejectVO document = documents.get(row.documentId());
			if (document == null) {
				document = new NYCRejectVO();
				if (row.documentId() != null) {
					document.setDOCUMENT_ID(row.documentId());
				}
				documents.put(row.documentId(), document);
				seenValues.put(row.documentId(), new HashSet<>());
			}
			// MyBatis set the association again only for values it had not seen for this document
			if (seenValues.get(row.documentId()).add(values(reject))) {
				document.setRejectDocumentVO(reject);
			}
		}
		return new ArrayList<>(documents.values());
	}

	/**
	 * Sets the comments of a document's records (by DOCUMENT_ID), UPDATE_DATE (the database's SYSDATE) and UPDATE_UID.
	 *
	 * @param nycRejectDocumentMap documentId, comments, workerId
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateNYCRejectDocumentComments(Map<String, Object> nycRejectDocumentMap) {
		return rejects.updateComments(text(nycRejectDocumentMap.get("comments")), text(nycRejectDocumentMap.get("workerId")),
				integer(nycRejectDocumentMap.get("documentId")));
	}

	/** The association of the former result map: every column set when not null. */
	private static UploadNYCSurvRejectVO toRejectVO(NycRejectRow row) {
		UploadNYCSurvRejectVO vo = new UploadNYCSurvRejectVO();
		if (row.documentId() != null) vo.setDOCUMENT_ID(row.documentId());
		if (row.stateno() != null) vo.setSTATENO(row.stateno());
		if (row.nycnumber() != null) vo.setNYCNUMBER(row.nycnumber());
		if (row.rejectDocumentId() != null) vo.setREJECT_DOCUMENT_ID(row.rejectDocumentId());
		if (row.dispositionCode() != null) vo.setDISPOSITION_CODE(row.dispositionCode());
		if (row.dispositionDesc() != null) vo.setDISPOSITION_DESC(row.dispositionDesc());
		if (row.dispositionDescShort() != null) vo.setDISPOSITION_DESC_SHORT(row.dispositionDescShort());
		if (row.statusnotes() != null) vo.setSTATUSNOTES(row.statusnotes());
		if (row.statusnoncase() != null) vo.setSTATUSNONCASE(row.statusnoncase());
		if (row.statusUpdateDate() != null) vo.setSTATUS_UPDATE_DATE(row.statusUpdateDate());
		if (row.stateno2() != null) vo.setSTATENO2(row.stateno2());
		if (row.uploadFilename() != null) vo.setUPLOAD_FILENAME(row.uploadFilename());
		if (row.uploadUid() != null) vo.setUPLOAD_UID(row.uploadUid());
		if (row.uploadDate() != null) vo.setUPLOAD_DATE(DATE.format(row.uploadDate().toLocalDateTime()));
		if (row.comments() != null) vo.setCOMMENTS(row.comments());
		if (row.updateUid() != null) vo.setUPDATE_UID(row.updateUid());
		if (row.updateDate() != null) vo.setUPDATE_DATE(row.updateDate());
		return vo;
	}

	/** The values of an association (MyBatis' row key of the nested result map: all its columns). */
	private static List<Object> values(UploadNYCSurvRejectVO vo) {
		return Arrays.asList(vo.getDOCUMENT_ID(), vo.getSTATENO(), vo.getNYCNUMBER(), vo.getREJECT_DOCUMENT_ID(), vo.getDISPOSITION_CODE(),
				vo.getDISPOSITION_DESC(), vo.getDISPOSITION_DESC_SHORT(), vo.getSTATUSNOTES(), vo.getSTATUSNONCASE(), vo.getSTATUS_UPDATE_DATE(),
				vo.getSTATENO2(), vo.getUPLOAD_FILENAME(), vo.getUPLOAD_UID(), vo.getUPLOAD_DATE(), vo.getCOMMENTS(), vo.getUPDATE_UID(),
				vo.getUPDATE_DATE());
	}

	private static Integer integer(Object value) {
		return value == null || "".equals(value) ? null : new BigDecimal(value.toString().trim()).intValueExact();
	}

	private static String text(Object value) {
		return value == null ? null : value.toString();
	}
}
