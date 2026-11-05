package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.timestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdSsdmDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawSsdmDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.SsdmDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * The SSDM (Social Security Death Master) import: the received records (RAW_SSDM_DOC) written as standardized records
 * (STD_SSDM_DOC), one by one or all at once (formerly a MyBatis mapper with the same name and methods), through Spring
 * Data repositories over StdSsdmDoc and RawSsdmDoc. No native SQL.
 * <p>
 * The single insert saves the entity; its conversions stay in the database (the entity's write transformers, as the
 * former SQL: TRIM/UPPER, TO_CHAR(TO_DATE(...)) of the date texts, which fails on an invalid date as before). The bulk
 * import is an HQL insert ... select with the former expressions (ORA_HASH and FN_TO_DATE called by name). PROCESS_DATE
 * stays the database's SYSDATE.
 *
 * @author vxp06
 */
@Repository
@RequiredArgsConstructor
public class SSDMDocumentDAO {

	private final SsdmDocRepository stdDocs;
	private final RawSsdmDocRepository rawDocs;

	/**
	 * Inserts one standardized record from the import's map (keys as the former insert read them; ORIG_LNAME from
	 * ORIGLNAME). HASH_NUMBER is not written (as before).
	 *
	 * @return 1
	 */
	@Transactional
	public int writeStdDocData(Map<Object, Object> docVO) {
		Map<Object, Object> m = docVO;
		stdDocs.saveAndFlush(StdSsdmDoc.builder()
				.ssn(text(m.get("SSN")))
				.personZipAtDeath(text(m.get("PERSON_ZIP_AT_DEATH")))
				.ssdmFile(text(m.get("SSDM_FILE")))
				.matchPass(text(m.get("MATCH_PASS")))
				.birthYear(text(m.get("BIRTH_YEAR")))
				.lname(text(m.get("LNAME")))
				.suffix(text(m.get("SUFFIX")))
				.fname(text(m.get("FNAME")))
				.mname(text(m.get("MNAME")))
				.personCountyAtDeath(text(m.get("PERSON_COUNTY_AT_DEATH")))
				.matchCd(text(m.get("MATCH_CD")))
				.personStateAtDeath(text(m.get("PERSON_STATE_AT_DEATH")))
				.reviewUid(text(m.get("REVIEW_UID")))
				.dobText(text(m.get("DOB_TEXT")))
				.dodText(text(m.get("DOD_TEXT")))
				.matchedStateno(text(m.get("MATCHED_STATENO")))
				.eharsStatusCd(text(m.get("EHARS_STATUS_CD")))
				.matchWeight(decimal(m.get("MATCH_WEIGHT")))
				.documentId(integer(m.get("DOCUMENT_ID")))
				.ssdmSrcId(longValue(m.get("SSDM_SRC_ID")))
				.ageAtDeath(integer(m.get("AGE_AT_DEATH")))
				.currentProcessStatus(integer(m.get("CURRENT_PROCESS_STATUS")))
				.qsParentDocId(longValue(m.get("QS_PARENT_DOC_ID")))
				.reviewDate(date(m.get("REVIEW_DATE")))
				.createDate(date(m.get("CREATE_DATE")))
				.eharsImportTime(date(m.get("EHARS_IMPORT_DATE")))
				.matchedProcessDate(date(m.get("MATCHED_PROCESS_DATE")))
				.createUid(text(m.get("CREATE_UID")))
				.origLname(text(m.get("ORIGLNAME")))
				.fluxMatchCd(text(m.get("FLUX_MATCH_CD")))
				.fluxPotStateno(text(m.get("FLUX_POT_STATENO")))
				.fluxMatchedStateno(text(m.get("FLUX_MATCHED_STATENO")))
				.fluxQsParentDocId(longValue(m.get("FLUX_QS_PARENT_DOC_ID")))
				.fluxMatchedProcessDate(date(m.get("FLUX_MATCHED_PROCESS_DATE")))
				.build());
		return 1;
	}

	/**
	 * Inserts every waiting record (status 1) of RAW_SSDM_DOC as a standardized record.
	 *
	 * @return the number of rows inserted
	 */
	@Transactional
	public int writeAllRawSSDMDocDataToStdTable() {
		return stdDocs.insertWaitingRawRecords();
	}

	/**
	 * Marks the waiting records (status 1) as moved (status 5).
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateRawDocumentStatus() {
		return rawDocs.markMoved();
	}

	/** Rejects (status 43) the waiting records whose first and last names are both empty or 'UNKNOWN'. */
	@Transactional
	public void documentInitialValidation() {
		rawDocs.rejectUnknownNames();
	}

	/**
	 * A value written to a text column, as Oracle converted what MyBatis bound: text as it is, a number as its text
	 * (no trailing zeros, 0.5 as ".5"). A date would have been formatted with the session's NLS format, so it is refused.
	 */
	private static String text(Object value) {
		if (value == null || value instanceof String) {
			return (String) value;
		}
		if (value instanceof java.util.Date) {
			throw new IllegalArgumentException("A date where text is expected: " + value);
		}
		if (value instanceof Number number) {
			String text = new BigDecimal(number.toString()).stripTrailingZeros().toPlainString();
			if (text.startsWith("0.")) {
				return text.substring(1);
			}
			return text.startsWith("-0.") ? "-" + text.substring(2) : text;
		}
		return value.toString();
	}

	/**
	 * A value written to a date column as MyBatis bound #{x,jdbcType=DATE}: a java.util.Date (the class itself) as its
	 * day only (MyBatis' DateOnlyTypeHandler), a java.sql.Date as its day, a Timestamp with its time; null and '' as null.
	 */
	private static Timestamp date(Object value) {
		if (value != null && value.getClass() == java.util.Date.class) {
			return Timestamp.valueOf(new java.sql.Date(((java.util.Date) value).getTime()).toLocalDate().atStartOfDay());
		}
		return timestamp(value);
	}

	/** A value written to a number column: a number as it is, a text as the number it holds; null or '' as null. */
	private static BigDecimal decimal(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		return value instanceof BigDecimal number ? number : new BigDecimal(value.toString().trim());
	}

	/** A whole-number column (a fraction fails rather than being cut). */
	private static Integer integer(Object value) {
		BigDecimal number = decimal(value);
		return number == null ? null : number.intValueExact();
	}

	/** A whole-number column (a fraction fails rather than being cut). */
	private static Long longValue(Object value) {
		BigDecimal number = decimal(value);
		return number == null ? null : number.longValueExact();
	}
}
