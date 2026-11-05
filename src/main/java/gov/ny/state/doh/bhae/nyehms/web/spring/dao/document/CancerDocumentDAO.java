package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CancerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawCancerDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.CancerDocRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.CancerReviewRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawCancerDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * The cancer registry records (RAW_CANCER_DOC) for the cancer document screen, the person's document history and the
 * cancer clerical review (formerly a MyBatis mapper with the same name and methods), through a Spring Data repository
 * over RawCancerDoc and CodedCounty. No native SQL.
 * <p>
 * Kept in the database, as before: the record is found by TO_NUMBER(PATIENTIDNUMBER) (a text column; a non-numeric
 * value in any row makes the queries fail, as before), the dates are TO_CHAR(FN_TO_DATE(...)), REVIEW_DATE is SYSDATE.
 * Done in Java: the sex, race and Hispanic origin texts (the former CASE lists), the address concatenation, the VO
 * mapping. A '' select item of the former SQL was NULL in Oracle, so those properties are not set, as before.
 */
@Repository
@RequiredArgsConstructor
public class CancerDocumentDAO {

	/** The former CASE of RACE1. */
	private static final Map<String, String> RACES = Map.ofEntries(
			Map.entry("01", "White"),
			Map.entry("02", "Black"),
			Map.entry("03", "American Indian, Aleutian, or Eskimo (includes all indigenous populations of the Western hemisphere)"),
			Map.entry("04", "Chinese"),
			Map.entry("05", "Japanese"),
			Map.entry("06", "Filipino"),
			Map.entry("07", "Hawaiian"),
			Map.entry("08", "Korean"),
			Map.entry("10", "Vietnamese"),
			Map.entry("11", "Laotian"),
			Map.entry("12", "Hmong"),
			Map.entry("13", "Kampuchean (Cambodian)"),
			Map.entry("14", "Thai"),
			Map.entry("15", "Asian Indian or Pakistani, NOS (code 09 prior to Version 12)"),
			Map.entry("16", "Asian Indian"),
			Map.entry("17", "Pakistani"),
			Map.entry("20", "Micronesian, NOS"),
			Map.entry("21", "Chamorro/Chamoru"),
			Map.entry("22", "Guamanian, NOS"),
			Map.entry("25", "Polynesian, NOS"),
			Map.entry("26", "Tahitian"),
			Map.entry("27", "Samoan"),
			Map.entry("28", "Tongan"),
			Map.entry("30", "Melanesian, NOS"),
			Map.entry("31", "Fiji Islander"),
			Map.entry("32", "New Guinean"),
			Map.entry("96", "Other Asian, including Asian, NOS and Oriental, NOS"),
			Map.entry("97", "Pacific Islander, NOS"),
			Map.entry("98", "Other"),
			Map.entry("99", "Unknown"));

	/** The former CASE of SPANISHHISPANICORIGIN. */
	private static final Map<String, String> ETHNICITIES = Map.of(
			"0", "Non-Spanish",
			"1", "Mexican",
			"2", "Puerto Rican",
			"3", "Cuban",
			"4", "South or Central American (except Brazil)",
			"5", "Other Spanish",
			"6", "Spanish, NOS",
			"7", "Spanish surname only",
			"8", "Dominican Republic (2005+)",
			"9", "Unknown");

	private final RawCancerDocRepository cancerDocs;

	/** A cancer record as a person, for the clerical review (null if there is none; several fail, as before). */
	public PersonViewVO getClericalDocumentAsPerson(int documentId) {
		CancerDocRow row = one(cancerDocs.findWithCounty(BigDecimal.valueOf(documentId)));
		if (row == null) {
			return null;
		}
		RawCancerDoc doc = row.doc();
		PersonViewVO vo = new PersonViewVO();
		if (doc.getNamefirst() != null) vo.setFNAME(doc.getNamefirst());
		if (doc.getNamemiddle() != null) vo.setMNAME(doc.getNamemiddle());
		if (doc.getNamelast() != null) vo.setLNAME(doc.getNamelast());
		vo.setGENDER(sex(doc.getSex()));
		vo.setBirthSex(sex(doc.getSex()));
		if (row.dateOfDeath() != null) vo.setDateOfDeath(row.dateOfDeath());
		if (doc.getSocialsecuritynumber() != null) vo.setSSN(doc.getSocialsecuritynumber());
		if (row.dateOfBirth() != null) vo.setDOB(row.dateOfBirth());
		if (doc.getPersonAddress() != null) vo.setPERSON_ADDRESS(doc.getPersonAddress());
		if (doc.getPersonState() != null) vo.setPERSON_STATE(doc.getPersonState());
		if (doc.getPersonCity() != null) vo.setPERSON_CITY(doc.getPersonCity());
		if (row.countyName() != null) vo.setPERSON_COUNTY(row.countyName());
		if (doc.getPersonZip() != null) vo.setPERSON_ZIP(doc.getPersonZip());
		if (doc.getTelephone() != null) {
			vo.setHomePhone(doc.getTelephone());
			vo.setCellPhone(doc.getTelephone());
			vo.setWorkPhone(doc.getTelephone());
		}
		return vo;
	}

	/**
	 * A cancer record as an entry of the person's document history.
	 *
	 * @param map documentId (the number), documentType (returned as given)
	 */
	public List<PersonDocHistoryVO> getCancerDocumentDetails(Map<Object, Object> map) {
		String documentType = text(map.get("documentType"));
		List<PersonDocHistoryVO> history = new ArrayList<>();
		for (CancerDocRow row : cancerDocs.findHistory(number(map.get("documentId")))) {
			RawCancerDoc doc = row.doc();
			PersonDocHistoryVO vo = new PersonDocHistoryVO();
			if (row.documentNumber() != null) vo.setDocumentId(row.documentNumber());
			if (documentType != null && !documentType.isEmpty()) vo.setDocumentType(documentType);     // '' was NULL in Oracle
			if (doc.getNamefirst() != null) vo.setFirstName(doc.getNamefirst());
			if (doc.getNamelast() != null) vo.setLastName(doc.getNamelast());
			if (row.dateOfBirth() != null) vo.setDateOfBirth(row.dateOfBirth());
			if (row.dateOfDeath() != null) vo.setDateOfDeath(row.dateOfDeath());
			if (race(doc) != null) vo.setRace(race(doc));
			if (ethnicity(doc) != null) vo.setEthnicity(ethnicity(doc));
			if (doc.getSocialsecuritynumber() != null) vo.setSsn(doc.getSocialsecuritynumber());
			vo.setAddress(address(doc));
			history.add(vo);
		}
		return history;
	}

	/** A cancer record for the cancer document screen (null if there is none; several fail, as before). */
	public CancerDocVO getCancerDocData(int docId) {
		CancerDocRow row = one(cancerDocs.findDocument(BigDecimal.valueOf(docId)));
		if (row == null) {
			return null;
		}
		RawCancerDoc doc = row.doc();
		CancerDocVO vo = new CancerDocVO();
		if (row.patientIdNumber() != null) vo.setPATIENT_ID_NUMBER(row.patientIdNumber());
		if (doc.getNamefirst() != null) vo.setFNAME(doc.getNamefirst());
		if (doc.getNamelast() != null) vo.setLNAME(doc.getNamelast());
		if (doc.getNamemiddle() != null) vo.setMNAME(doc.getNamemiddle());
		vo.setSEX(sex(doc.getSex()));
		if (doc.getSocialsecuritynumber() != null) vo.setSSN(doc.getSocialsecuritynumber());
		if (row.dateOfBirth() != null) vo.setDATE_OF_BIRTH(row.dateOfBirth());
		if (row.dateOfDeath() != null) vo.setDATE_OF_DEATH(row.dateOfDeath());
		if (race(doc) != null) vo.setRACE(race(doc));
		if (ethnicity(doc) != null) vo.setETHNICITY(ethnicity(doc));
		if (doc.getPersonAddress() != null) vo.setPERSON_ADDRESS(doc.getPersonAddress());
		if (doc.getPersonCity() != null) vo.setPERSON_CITY(doc.getPersonCity());
		if (doc.getPersonState() != null) vo.setPERSON_STATE(doc.getPersonState());
		if (doc.getPersonZip() != null) vo.setPERSON_ZIP(doc.getPersonZip());
		if (doc.getPersonCounty() != null) vo.setPERSON_COUNTY(doc.getPersonCounty());
		return vo;
	}

	/**
	 * Records the matched state number of a record still to review.
	 *
	 * @param inputParameterMap DOCUMENT_ID, STATENO, p_worker_id
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateCancerMotherStatenoDetails(Map<String, Object> inputParameterMap) {
		return cancerDocs.updateMatch(text(inputParameterMap.get("STATENO")), text(inputParameterMap.get("p_worker_id")),
				number(inputParameterMap.get("DOCUMENT_ID")));
	}

	/**
	 * Marks a record still to review as not a match.
	 *
	 * @param inOutParameterMap DOCUMENT_ID, p_worker_id
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateNotaMatchDetails(Map<String, Object> inOutParameterMap) {
		return cancerDocs.updateNotAMatch(text(inOutParameterMap.get("p_worker_id")), number(inOutParameterMap.get("DOCUMENT_ID")));
	}

	/** The number of records to review (status 17, match code 'C'). */
	public int getTotalCancerDocCount() {
		return (int) cancerDocs.countByCurrentProcessStatusAndFluxMatchCd(17, "C");
	}

	/**
	 * A record to review, picked pseudo-randomly: DOCUMENT_ID, MATCH_CD, FUZZY_CLUSTER_ID, CLUSTER_ID (all three
	 * TO_NUMBER(PATIENTIDNUMBER)), POT_STATENO, DOC_TYPE ('CANCER'); null values left out; null if there is none.
	 */
	public Map<String, Object> getCancerDocumentToReview(Map<Object, Object> inOutParamaterMap) {
		List<CancerReviewRow> rows = cancerDocs.findOneToReview();
		if (rows.isEmpty()) {
			return null;
		}
		CancerReviewRow row = rows.get(0);
		Map<String, Object> result = new HashMap<>();
		put(result, "DOCUMENT_ID", row.documentId());
		put(result, "MATCH_CD", row.matchCd());
		put(result, "FUZZY_CLUSTER_ID", row.documentId());
		put(result, "CLUSTER_ID", row.documentId());
		put(result, "POT_STATENO", row.potStateno());
		result.put("DOC_TYPE", "CANCER");
		return result;
	}

	/** The former CASE of SEX: 'Male', 'Female', else ' '. */
	private static String sex(String sex) {
		if ("1".equals(sex)) {
			return "Male";
		}
		return "2".equals(sex) ? "Female" : " ";
	}

	private static String race(RawCancerDoc doc) {
		return doc.getRace1() == null ? null : RACES.get(doc.getRace1());
	}

	private static String ethnicity(RawCancerDoc doc) {
		return doc.getSpanishhispanicorigin() == null ? null : ETHNICITIES.get(doc.getSpanishhispanicorigin());
	}

	/** PERSON_ADDRESS || ', ' || PERSON_CITY || ', ' || PERSON_STATE || ', ' || PERSON_ZIP (Oracle: a NULL part adds nothing). */
	private static String address(RawCancerDoc doc) {
		return part(doc.getPersonAddress()) + ", " + part(doc.getPersonCity()) + ", " + part(doc.getPersonState()) + ", " + part(doc.getPersonZip());
	}

	private static String part(String text) {
		return text == null ? "" : text;
	}

	/** The only row, null for none; more than one fails as the former selectOne did. */
	private static <T> T one(List<T> rows) {
		if (rows.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, rows.size());
		}
		return rows.isEmpty() ? null : rows.get(0);
	}

	private static void put(Map<String, Object> row, String key, Object value) {
		if (value != null) {
			row.put(key, value);
		}
	}

	private static BigDecimal number(Object value) {
		return value == null || "".equals(value) ? null : new BigDecimal(value.toString().trim());
	}

	private static String text(Object value) {
		return value == null ? null : value.toString();
	}
}
