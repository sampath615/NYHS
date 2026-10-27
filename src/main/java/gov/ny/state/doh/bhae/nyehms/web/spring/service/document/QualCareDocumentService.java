package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareRefCode;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.QualCareDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * The quality of care records (STD_QUALITY_CARE_DOC): the quality of care document screen and the import that
 * standardizes a received record (RAW_QUALITY_CARE_DOC).
 */
@Service
@RequiredArgsConstructor
public class QualCareDocumentService {

	private static final String ASIAN_SUB_GROUP = "ASIAN_SUB_GROUP";
	private static final String HISPANIC_SUB_GROUP = "HISPANIC_SUB_GROUP";

	private final QualCareDocRepository qualCareDocRepository;

	/**
	 * Writes the standardized record (STD_QUALITY_CARE_DOC) of a received record.
	 *
	 * @param docVO the received record (a RAW_QUALITY_CARE_DOC row), value per column name; not changed
	 * @return 1
	 */
	public int writeRawQualCareDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {
		qualCareDocRepository.saveAndFlush(QualCareStdDocMapping.toStdDoc(docVO));
		return 1;
	}

	/**
	 * A quality of care record for the document screen, with the names of its Asian and Hispanic sub-groups.
	 *
	 * @param documentId document id of the record
	 * @return the record, or null if there is none
	 */
	public QualCareDocData getQualCareDocument(int documentId) {
		QualCareDocData document = qualCareDocRepository.findDocument(documentId);
		if (document == null) {
			return null;
		}
		List<QualCareRefCode> subGroupCodes = qualCareDocRepository.findSubGroupCodes();
		return document.withSubGroups(
				subGroupNames(document.stdQualityCareDoc().getAsianSubGroup(), ASIAN_SUB_GROUP, subGroupCodes),
				subGroupNames(document.stdQualityCareDoc().getHispanicSubGroup(), HISPANIC_SUB_GROUP, subGroupCodes));
	}

	/**
	 * The names of the codes of a sub-group column, as the former query listed them: the column split at commas, each
	 * code trimmed of spaces, every listed code that has a name in the code list (a code listed twice is named twice),
	 * by code, separated by ", "; null when none has a name.
	 *
	 * @param codes the column's value, e.g. "02, 01"
	 * @param refColumn the column (REF_COLUMN of the code list)
	 * @param refCodes the code list
	 */
	static String subGroupNames(String codes, String refColumn, List<QualCareRefCode> refCodes) {
		if (codes == null) {
			return null;
		}
		List<String> listed = Arrays.stream(codes.split(",", -1)).map(OracleConversions::trimmed).filter(Objects::nonNull).toList();
		String names = refCodes.stream()
				.filter(refCode -> refColumn.equals(refCode.refColumn()) && refCode.lookupName() != null)
				.flatMap(refCode -> listed.stream().filter(code -> code.equals(refCode.lookupCd())).map(code -> refCode))
				.sorted(Comparator.comparing(QualCareRefCode::lookupCd))
				.map(QualCareRefCode::lookupName)
				.collect(Collectors.joining(", "));
		return names.isEmpty() ? null : names;
	}
}
