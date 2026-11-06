package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.date;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.decimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.integer;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MapValues.text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DinDoccsDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DoccsProbCodesVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdDinDoccsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdDoccsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

/**
 * The DOCCS (Department of Corrections) documents: the DIN records of a report for the screens, and the import of a
 * received report, its DIN records and their problem codes into the standardized tables (formerly a MyBatis mapper
 * with the same name and methods).
 * <p>
 * JPQL over StdDoccsDoc, RawDoccsDoc, StdDinDoccsDoc, RawDinDoccsDoc, StdDoccsProbCodes, RawDoccsProbCodes,
 * CodedDoccsProblemCodes, CodedDoccsFacilityXref, CodedDoccsFacility and CodedCountry; the database function FN_TO_DATE
 * (and TO_CHAR) is called from JPQL. No native SQL.
 * <p>
 * The import's insert of the DIN records (formerly one INSERT ... SELECT of all records) writes one entity per record
 * and facility match; it keeps the conversions in the database (StdDinDoccsDoc's write transformers: TRIM(UPPER),
 * TO_CHAR(FN_TO_DATE(...))), the country code of the birth country and the facility lookups (the former CASE and outer
 * joins) are done in Java with the same results.
 * <p>
 * <b>Kept as before:</b> the DIN DOCCS screen lists problem codes by their description (a code without description
 * shows as none, and codes with the same description and dates show once); an import with no DIN record fails (the
 * former insert had no SELECT); a facility name mapped more than once writes one DIN row per match;
 * {@link #getDoccsDocumentId} (not called) fails when there is no such document.
 */
@Repository
@RequiredArgsConstructor
public class DoccsDocumentDAO {

	private final JpqlStatements jpql;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The DOCCS report of a DIN document.
	 *
	 * @throws EmptyResultDataAccessException if there is no such document (or its report is not set): the former mapper
	 *         refused to return null for an int
	 */
	public int getDoccsDocumentId(int docId) {
		Integer doccsDocId = jpql.one("select s.doccsDocId from StdDinDoccsDoc s where s.documentId = ?1", Rows.scalar(Integer.class), (long) docId);
		if (doccsDocId == null) {
			throw new EmptyResultDataAccessException("No DOCCS report for the DIN document " + docId, 1);
		}
		return doccsDocId;
	}

	/**
	 * Inserts the standardized record of a received DOCCS report (the import's map).
	 *
	 * @return 1
	 */
	@Transactional
	public int insertRawDoccsDataToStdTable(Map<Object, Object> docVO) {
		return jpql.insert(StdDoccsDoc.builder()
				.documentId(integer(docVO.get("DOCUMENT_ID")))
				.nysid(text(docVO.get("NYSID")))
				.fileReceivedDateWritten(date(docVO.get("FILE_RECEIVED_DATE")))
				.matchedStateno(text(docVO.get("MATCHED_STATENO")))
				.doccsFile(text(docVO.get("DOCCS_FILE")))
				.currentProcessStatus(decimal(docVO.get("CURRENT_PROCESS_STATUS")))
				.build());
	}

	/**
	 * The received DIN records of a report that are not stopped (status 43), as maps keyed by the former column names (a
	 * key only when its value is set; the numbers as BigDecimal, PROCESS_DATE as a timestamp, the rest text).
	 */
	public List<Map<String, Object>> getRawDinDoccsList(BigDecimal documentId) {
		return jpql.list("""
				select r.documentId as DOCUMENT_ID, r.doccsDocId as DOCCS_DOC_ID, r.recnum as RECNUM, r.nysid as NYSID, r.din as DIN,
				r.admdate as ADMDATE, r.ladmdate as LADMDATE, r.fname as FNAME, r.mname as MNAME, r.lname as LNAME, r.suffix as SUFFIX,
				r.birth as BIRTH, r.pob as POB, r.aliens2 as ALIENS2, r.usdate as USDATE, r.resco as RESCO, r.legresc as LEGRESC, r.sex as SEX,
				r.race as RACE, r.ethnicity as ETHNICITY, r.lrelda as LRELDA, r.drug1 as DRUG1, r.drug2 as DRUG2, r.drug3 as DRUG3, r.uc as UC,
				r.lstreet as LSTREET, r.lcity as LCITY, r.lstate as LSTATE, r.lzip as LZIP, r.lzip9 as LZIP9, r.street as STREET, r.city as CITY,
				r.state as STATE, r.zip as ZIP, r.zip9 as ZIP9, r.srp as SRP, r.prevStreet as PREV_STREET, r.prevCity as PREV_CITY,
				r.prevState as PREV_STATE, r.prevZip9 as PREV_ZIP9, r.death as DEATH, r.ssn as SSN, r.cin as CIN, r.lreldate as LRELDATE,
				r.ldischrgdate as LDISCHRGDATE, r.currentFacility as CURRENT_FACILITY, r.owningFacility as OWNING_FACILITY,
				r.currentProcessStatus as CURRENT_PROCESS_STATUS, r.erdate as ERDATE, r.doccsFile as DOCCS_FILE, r.processDate as PROCESS_DATE,
				r.prlrelcnty as PRLRELCNTY
				from RawDinDoccsDoc r where r.doccsDocId = ?1 and r.currentProcessStatus != 43""", Rows.map(), documentId);
	}

	/**
	 * Inserts the standardized DIN records of a report (the import's maps, recoded): the birth country as its country
	 * code, the current and owning facility with the details of CODED_DOCCS_FACILITY (through CODED_DOCCS_FACILITY_XREF).
	 *
	 * @param inputParameterMap dinDoccsList: the DIN records
	 * @return the number of rows inserted (one per record and facility match)
	 * @throws InvalidDataAccessResourceUsageException if there is no record (the former INSERT ... SELECT had no SELECT)
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public int insertRawDinDoccsDataToStdTable(Map<String, Object> inputParameterMap) {
		List<Map<String, Object>> dinDoccsList = (List<Map<String, Object>>) inputParameterMap.get("dinDoccsList");
		if (dinDoccsList == null || dinDoccsList.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No DIN record to insert (the former INSERT ... SELECT had no SELECT)");
		}
		int inserted = 0;
		for (Map<String, Object> m : dinDoccsList) {
			String birthCountry = birthCountry(m.get("BIRTH_COUNTRY"));
			List<Facility> currentFacilities = facilities(m.get("CURRENT_FACILITY_NAME"));
			for (Facility owning : facilities(m.get("OWNING_FACILITY_NAME"))) {
				for (Facility current : currentFacilities) {
					StdDinDoccsDoc doc = StdDinDoccsDoc.builder()
							.documentId(longValue(m.get("DOCUMENT_ID")))
							.doccsDocId(longValue(m.get("DOCCS_DOC_ID")))
							.nysid(text(m.get("NYSID")))
							.ssn(text(m.get("SSN")))
							.provname(text(m.get("PROVNAME")))
							.medicaidNumber(text(m.get("MEDICAID_NUMBER")))
							.din(text(m.get("DIN")))
							.personAddress(text(m.get("PERSON_ADDRESS")))
							.legalPersonAddress(text(m.get("LEGAL_PERSON_ADDRESS")))
							.prevPersonAddress(text(m.get("PREV_PERSON_ADDRESS")))
							.provAddr(text(m.get("PROV_ADDR")))
							.personCensusTract(text(m.get("PERSON_CENSUS_TRACT")))
							.owningFacilityCensusTract(text(m.get("OWNING_FACILITY_CENSUS_TRACT")))
							.currentFacilityCensusTract(text(m.get("CURRENT_FACILITY_CENSUS_TRACT")))
							.doccsFile(text(m.get("DOCCS_FILE")))
							.suffix(text(m.get("SUFFIX")))
							.personZip(text(m.get("PERSON_ZIP")))
							.legalPersonZip(text(m.get("LEGAL_PERSON_ZIP")))
							.prevPersonZip(text(m.get("PREV_PERSON_ZIP")))
							.provZip(text(m.get("PROV_ZIP")))
							.personPlus4(text(m.get("PERSON_PLUS4")))
							.legalPersonPlus4(text(m.get("LEGAL_PERSON_PLUS4")))
							.prevPersonPlus4(text(m.get("PREV_PERSON_PLUS4")))
							.provPlus4(text(m.get("PROV_PLUS4")))
							.provCensusTract(text(m.get("PROV_CENSUS_TRACT")))
							.matchPass(text(m.get("MATCH_PASS")))
							.personCity(text(m.get("PERSON_CITY")))
							.legalPersonCity(text(m.get("LEGAL_PERSON_CITY")))
							.prevPersonCity(text(m.get("PREV_PERSON_CITY")))
							.provCity(text(m.get("PROV_CITY")))
							.personCounty(text(m.get("PERSON_COUNTY")))
							.countyProv(text(m.get("COUNTY_PROV")))
							.altProvider(text(m.get("ALT_PROVIDER")))
							.lname(text(m.get("LNAME")))
							.mname(text(m.get("MNAME")))
							.birthCountry(birthCountry)
							.drug1(text(m.get("DRUG1")))
							.drug2(text(m.get("DRUG2")))
							.drug3(text(m.get("DRUG3")))
							.paroleReleaseCounty(text(m.get("PAROLE_RELEASE_COUNTY")))
							.fname(text(m.get("FNAME")))
							.provPhone(text(m.get("PROV_PHONE")))
							.ethnicity(text(m.get("ETHNICITY")))
							.personState(text(m.get("PERSON_STATE")))
							.legalPersonState(text(m.get("LEGAL_PERSON_STATE")))
							.prevPersonState(text(m.get("PREV_PERSON_STATE")))
							.provSt(text(m.get("PROV_ST")))
							.provCensusTractSuffix(text(m.get("PROV_CENSUS_TRACT_SUFFIX")))
							.raceCd(text(m.get("RACE_CD")))
							.alienStatus(text(m.get("ALIEN_STATUS")))
							.legalPersonCountyName(text(m.get("LEGAL_PERSON_COUNTY_NAME")))
							.resco(text(m.get("RESCO")))
							.personLongitude(text(m.get("PERSON_LONGITUDE")))
							.owningFacilityLongitude(text(m.get("OWNING_FACILITY_LONGITUDE")))
							.currentFacilityLongitude(text(m.get("CURRENT_FACILITY_LONGITUDE")))
							.provLong(text(m.get("PROV_LONG")))
							.stateno(text(m.get("STATENO")))
							.dobText(text(m.get("DOB_TEXT")))
							.enteredUsDateText(text(m.get("ENTERED_US_DATE_TEXT")))
							.personLatitude(text(m.get("PERSON_LATITUDE")))
							.owningFacilityLatitude(text(m.get("OWNING_FACILITY_LATITUDE")))
							.currentFacilityLatitude(text(m.get("CURRENT_FACILITY_LATITUDE")))
							.adminDateText(text(m.get("ADMIN_DATE_TEXT")))
							.latestAdminDateText(text(m.get("LATEST_ADMIN_DATE_TEXT")))
							.earliestReleaseDateText(text(m.get("EARLIEST_RELEASE_DATE_TEXT")))
							.latestReleaseDateText(text(m.get("LATEST_RELEASE_DATE_TEXT")))
							.latestDischargeDateText(text(m.get("LATEST_DISCHARGE_DATE_TEXT")))
							.dodText(text(m.get("DOD_TEXT")))
							.provLat(text(m.get("PROV_LAT")))
							.providerType(text(m.get("PROVIDER_TYPE")))
							.lrelda(text(m.get("LRELDA")))
							.matchedStateno(text(m.get("MATCHED_STATENO")))
							.currentGender(text(m.get("CURRENT_GENDER")))
							.underCustody(text(m.get("UNDER_CUSTODY")))
							.underSupervision(text(m.get("UNDER_SUPERVISION")))
							.processed(text(m.get("PROCESSED")))
							.assessAssign(text(m.get("ASSESS_ASSIGN")))
							.matchWeight(decimal(m.get("MATCH_WEIGHT")))
							.currentProcessStatus(decimal(m.get("CURRENT_PROCESS_STATUS")))
							.qsParentDocId(decimal(m.get("QS_PARENT_DOC_ID")))
							.reviewDate(date(m.get("REVIEW_DATE")))
							.createDate(date(m.get("CREATE_DATE")))
							.eharsImportDate(date(m.get("EHARS_IMPORT_DATE")))
							.nycShipDate(date(m.get("NYC_SHIP_DATE")))
							.matchedProcessDate(date(m.get("MATCHED_PROCESS_DATE")))
							.reviewDt(date(m.get("REVIEW_DT")))
							.reviewUid(text(m.get("REVIEW_UID")))
							.matchCd(text(m.get("MATCH_CD")))
							.createUid(text(m.get("CREATE_UID")))
							.raceIndian(text(m.get("RACE_INDIAN")))
							.raceBlack(text(m.get("RACE_BLACK")))
							.raceAsian(text(m.get("RACE_ASIAN")))
							.racePacific(text(m.get("RACE_PACIFIC")))
							.raceWhite(text(m.get("RACE_WHITE")))
							.raceMultiple(text(m.get("RACE_MULTIPLE")))
							.raceOther(text(m.get("RACE_OTHER")))
							.raceUnknown(text(m.get("RACE_UNKNOWN")))
							.eharsStatusCd(text(m.get("EHARS_STATUS_CD")))
							.owningFacilityName(owning.facilityName())
							.owningFacilityAddress(owning.address())
							.owningFacilityCity(owning.city())
							.owningFacilityState(owning.state())
							.owningFacilityZip(owning.zipcode())
							.owningFacilityPlus4(owning.plus4())
							.owningFacilityCounty(owning.countyFips())
							.owningFacilityPhone(owning.phone())
							.currentFacilityName(current.facilityName())
							.currentFacilityAddress(current.address())
							.currentFacilityCity(current.city())
							.currentFacilityState(current.state())
							.currentFacilityZip(current.zipcode())
							.currentFacilityPlus4(current.plus4())
							.currentFacilityCounty(current.countyFips())
							.currentFacilityPhone(current.phone())
							.build();
					// several rows of one DIN document (a facility mapped more than once) are written like the former insert:
					// each is written at once and not kept in the persistence context
					entityManager.persist(doc);
					entityManager.flush();
					entityManager.detach(doc);
					inserted++;
				}
			}
		}
		return inserted;
	}

	/** A facility of CODED_DOCCS_FACILITY as the former insert wrote it (ADDRESS: ADDRESS1 || ' ' || ADDRESS2). */
	private record Facility(String facilityName, String address, String city, String state, String zipcode, String plus4, String countyFips,
			String phone) {

		/** No facility (the outer joins found none): the address is ' ', as Oracle's concatenation of nulls and a space. */
		static final Facility NONE = new Facility(null, " ", null, null, null, null, null, null);
	}

	/**
	 * The facilities a DOCCS facility name maps to (the former outer joins of TRIM(UPPER(name)) with
	 * CODED_DOCCS_FACILITY_XREF and CODED_DOCCS_FACILITY): one per mapping, a mapping without facility as
	 * {@link Facility#NONE} details with the name null, no mapping as {@link Facility#NONE}.
	 */
	private List<Facility> facilities(Object doccsFacilityName) {
		List<Facility> facilities = jpql.list("""
				select f.facilityName as FACILITY_NAME, f.address1 as ADDRESS1, f.address2 as ADDRESS2, f.city as CITY, f.state as STATE,
				f.zipcode as ZIPCODE, f.plus4 as PLUS4, f.countyFips as COUNTY_FIPS, f.phone as PHONE
				from CodedDoccsFacilityXref x left join CodedDoccsFacility f on x.facilityName = f.facilityName
				where x.doccsFacilityName = trim(upper(?1))""", FACILITY, text(doccsFacilityName));
		return facilities.isEmpty() ? List.of(Facility.NONE) : facilities;
	}

	private static final RowMapper<Facility> FACILITY = row -> new Facility((String) row.get("FACILITY_NAME"),
			concat((String) row.get("ADDRESS1"), (String) row.get("ADDRESS2")), (String) row.get("CITY"), (String) row.get("STATE"),
			(String) row.get("ZIPCODE"), (String) row.get("PLUS4"), (String) row.get("COUNTY_FIPS"), (String) row.get("PHONE"));

	/** ADDRESS1 || ' ' || ADDRESS2 as Oracle concatenates (a null as nothing). */
	private static String concat(String address1, String address2) {
		return (address1 == null ? "" : address1) + " " + (address2 == null ? "" : address2);
	}

	/**
	 * The country code of a birth country, as the former CASE computed it: the code of the country whose upper-case
	 * name it is (more than one such country fails, as the former scalar subquery did), else the code of one of the
	 * names DOCCS uses, else null.
	 */
	private String birthCountry(Object value) {
		String country = text(value);
		if (country == null) {
			return null;
		}
		List<String> codes = jpql.list("select c.countryCode as COUNTRY_CODE from CodedCountry c where upper(c.countryDesc) = ?1",
				Rows.scalar(String.class), country);
		if (codes.size() > 1) {
			throw new IncorrectResultSizeDataAccessException("More than one country named " + country, 1, codes.size());
		}
		return codes.isEmpty() ? DOCCS_COUNTRY_CODES.get(country) : codes.get(0);
	}

	/** The country codes of the names DOCCS uses that are not country names (the former CASE, in its order). */
	private static final Map<String, String> DOCCS_COUNTRY_CODES = Map.ofEntries(
			Map.entry("ZAIRE", "COD"),
			Map.entry("ANTIGUA", "ATG"),
			Map.entry("BAHAMA ISLANDS", "BHS"),
			Map.entry("BOSNIA", "BIH"),
			Map.entry("BRIT.VIRG.ISLE", "VGB"),
			Map.entry("BRITISH VIRGIN IS", "VGB"),
			Map.entry("BRUNEI", "BRN"),
			Map.entry("BURMA", "MMR"),
			Map.entry("CANAL ZONE", "PAN"),
			Map.entry("CAYMAN ISLES", "CYM"),
			Map.entry("CZECHOSLOVAKIA", "CZE"),
			Map.entry("DOMINICAN REP.", "DOM"),
			Map.entry("WEST GERMANY", "DEU"),
			Map.entry("EAST GERMANY", "DEU"),
			Map.entry("ENGLAND", "GBR"),
			Map.entry("ENGLAND,SCOT.", "GBR"),
			Map.entry("SCOTLAND", "GBR"),
			Map.entry("WALES", "GBR"),
			Map.entry("EQUADOR", "ECU"),
			Map.entry("FIJI ISLANDS", "FJI"),
			Map.entry("GGAMBIA", "GMB"),
			Map.entry("GRENEDA", "GRD"),
			Map.entry("GUATAMALA", "GTM"),
			Map.entry("IRAN", "IRN"),
			Map.entry("ITALY,CORSICA", "ITA"),
			Map.entry("IVORY COAST", "CIV"),
			Map.entry("LAOS", "LAO"),
			Map.entry("LYBIA", "LBY"),
			Map.entry("NETH. ANTILLES", "ANT"),
			Map.entry("NEVIS-ANGUILLA", "KNA"),
			Map.entry("NORTH KOREA", "PRK"),
			Map.entry("NEW HEBRIDES", "VUT"),
			Map.entry("PAP.NEW GUINEA", "PNG"),
			Map.entry("PAPAU NEW GUINEA", "PNG"),
			Map.entry("PHILLIPPINES", "PHL"),
			Map.entry("SOVIET UNION", "RUS"),
			Map.entry("RUSSIA", "RUS"),
			Map.entry("USSR", "RUS"),
			Map.entry("S.W. AFRICA", "ZAF"),
			Map.entry("SOUTH KOREA", "KOR"),
			Map.entry("SPAIN,PORTUGAL", "ESP"),
			Map.entry("SURINAM", "SUR"),
			Map.entry("SYRIA", "SYR"),
			Map.entry("TAIWAN", "TWN"),
			Map.entry("TANZANIA", "TZA"),
			Map.entry("TIBET", "CHN"),
			Map.entry("TRINIDAD", "TTO"),
			Map.entry("TURKS - CAICOS", "TCA"),
			Map.entry("UNITED ARAB EM", "ARE"),
			Map.entry("VIETNAM", "VNM"),
			Map.entry("VIRGIN ISLANDS", "VIR"),
			Map.entry("WEST SAHARA", "ESH"),
			Map.entry("AB", "CAN"),
			Map.entry("BC", "CAN"),
			Map.entry("MB", "CAN"),
			Map.entry("NB", "CAN"),
			Map.entry("NL", "CAN"),
			Map.entry("NS", "CAN"),
			Map.entry("NT", "CAN"),
			Map.entry("ON", "CAN"),
			Map.entry("PE", "CAN"),
			Map.entry("QC", "CAN"),
			Map.entry("SK", "CAN"),
			Map.entry("YT", "CAN"),
			Map.entry("CANADA", "CAN"),
			Map.entry("AK", "USA"),
			Map.entry("AL", "USA"),
			Map.entry("AR", "USA"),
			Map.entry("AZ", "USA"),
			Map.entry("CA", "USA"),
			Map.entry("CO", "USA"),
			Map.entry("CT", "USA"),
			Map.entry("DC", "USA"),
			Map.entry("DE", "USA"),
			Map.entry("FL", "USA"),
			Map.entry("GA", "USA"),
			Map.entry("HI", "USA"),
			Map.entry("IA", "USA"),
			Map.entry("ID", "USA"),
			Map.entry("IL", "USA"),
			Map.entry("IN", "USA"),
			Map.entry("KS", "USA"),
			Map.entry("KY", "USA"),
			Map.entry("LA", "USA"),
			Map.entry("MA", "USA"),
			Map.entry("MD", "USA"),
			Map.entry("ME", "USA"),
			Map.entry("MI", "USA"),
			Map.entry("MN", "USA"),
			Map.entry("MO", "USA"),
			Map.entry("MS", "USA"),
			Map.entry("MT", "USA"),
			Map.entry("NC", "USA"),
			Map.entry("ND", "USA"),
			Map.entry("NE", "USA"),
			Map.entry("NH", "USA"),
			Map.entry("NJ", "USA"),
			Map.entry("NM", "USA"),
			Map.entry("NV", "USA"),
			Map.entry("NY", "USA"),
			Map.entry("OH", "USA"),
			Map.entry("OK", "USA"),
			Map.entry("OR", "USA"),
			Map.entry("PA", "USA"),
			Map.entry("RI", "USA"),
			Map.entry("SC", "USA"),
			Map.entry("SD", "USA"),
			Map.entry("TN", "USA"),
			Map.entry("TX", "USA"),
			Map.entry("UT", "USA"),
			Map.entry("VA", "USA"),
			Map.entry("VT", "USA"),
			Map.entry("WA", "USA"),
			Map.entry("WI", "USA"),
			Map.entry("WV", "USA"),
			Map.entry("WY", "USA"),
			Map.entry("ALABAMA", "USA"),
			Map.entry("ALASKA", "USA"),
			Map.entry("ARIZONA", "USA"),
			Map.entry("ARKANSAS", "USA"),
			Map.entry("CALIFORNIA", "USA"),
			Map.entry("COLORADO", "USA"),
			Map.entry("CONNECTICUT", "USA"),
			Map.entry("DELAWARE", "USA"),
			Map.entry("DISTRICT OF COLUMBIA", "USA"),
			Map.entry("FLORIDA", "USA"),
			Map.entry("GEORGIA", "USA"),
			Map.entry("HAWAII", "USA"),
			Map.entry("IDAHO", "USA"),
			Map.entry("ILLINOIS", "USA"),
			Map.entry("INDIANA", "USA"),
			Map.entry("IOWA", "USA"),
			Map.entry("KANSAS", "USA"),
			Map.entry("KENTUCKY", "USA"),
			Map.entry("LOS ANGELES COUNTY", "USA"),
			Map.entry("LOUISIANA", "USA"),
			Map.entry("MAINE", "USA"),
			Map.entry("MARYLAND", "USA"),
			Map.entry("MASSACHUSETTS", "USA"),
			Map.entry("MICHIGAN", "USA"),
			Map.entry("MINNESOTA", "USA"),
			Map.entry("MISSISSIPPI", "USA"),
			Map.entry("MISSOURI", "USA"),
			Map.entry("MONTANA", "USA"),
			Map.entry("NEBRASKA", "USA"),
			Map.entry("NEVADA", "USA"),
			Map.entry("NEW HAMPSHIRE", "USA"),
			Map.entry("NEW JERSEY", "USA"),
			Map.entry("NEW MEXICO", "USA"),
			Map.entry("NEW YORK", "USA"),
			Map.entry("NEW YORK CITY", "USA"),
			Map.entry("NORTH CAROLINA", "USA"),
			Map.entry("NORTH DAKOTA", "USA"),
			Map.entry("OHIO", "USA"),
			Map.entry("OKLAHOMA", "USA"),
			Map.entry("OREGON", "USA"),
			Map.entry("PENNSYLVANIA", "USA"),
			Map.entry("PHILADELPHIA", "USA"),
			Map.entry("PUERTO RICO", "USA"),
			Map.entry("RHODE ISLAND", "USA"),
			Map.entry("SAN FRANCISCO", "USA"),
			Map.entry("SOUTH CAROLINA", "USA"),
			Map.entry("SOUTH DAKOTA", "USA"),
			Map.entry("TENNESSEE", "USA"),
			Map.entry("TEXAS", "USA"),
			Map.entry("UTAH", "USA"),
			Map.entry("VERMONT", "USA"),
			Map.entry("VIRGINIA", "USA"),
			Map.entry("WASHINGTON", "USA"),
			Map.entry("WEST VIRGINIA", "USA"),
			Map.entry("WISCONSIN", "USA"),
			Map.entry("WYOMING", "USA"),
			Map.entry("NEW YORK STATE", "USA"),
			Map.entry("WASHINGTON D C", "USA"));

	/**
	 * Copies the received problem codes of a report's DIN records to the standardized table (the date texts checked and
	 * written as MM/DD/YYYY by FN_TO_DATE and TO_CHAR).
	 *
	 * @return the number of rows inserted
	 */
	@Transactional
	public int insertRawDinProbCodesToStdProbCodes(BigDecimal documentId) {
		return jpql.update("""
				insert into StdDoccsProbCodes (dinDoccsDocId, problemCd, problemCdDate, hivDiag, hivDiagDateText, aidsDiag, aidsDiagDateText)
				select r.dinDoccsDocId, r.problemCd,
				function('TO_CHAR' as String, function('FN_TO_DATE' as Timestamp, trim(upper(r.problemCdDate)), 'MM/DD/YYYY'), 'MM/DD/YYYY'),
				r.hivDiag,
				function('TO_CHAR' as String, function('FN_TO_DATE' as Timestamp, trim(upper(r.hivDiagDateText)), 'MM/DD/YYYY'), 'MM/DD/YYYY'),
				r.aidsDiag,
				function('TO_CHAR' as String, function('FN_TO_DATE' as Timestamp, trim(upper(r.aidsDiagDateText)), 'MM/DD/YYYY'), 'MM/DD/YYYY')
				from RawDoccsProbCodes r
				where r.dinDoccsDocId in (select s.documentId from StdDinDoccsDoc s where s.doccsDocId = ?1)""", longValue(documentId));
	}

	/** The DIN document ids of a report. */
	public List<Integer> getDinDoccsIds(int document_ID) {
		return jpql.list("select s.documentId as DOCUMENT_ID from StdDinDoccsDoc s where s.doccsDocId = ?1", Rows.scalar(Integer.class),
				(long) document_ID);
	}

	/**
	 * Stops (status 43) the received DIN records of a report whose first and last names are both unknown (UNKNOWN or
	 * empty).
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int documentInitialValidation(BigDecimal documentId) {
		return jpql.update("""
				update RawDinDoccsDoc r set r.currentProcessStatus = 43
				where r.doccsDocId = ?1 and (upper(trim(r.fname)) = 'UNKNOWN' or trim(r.fname) is null)
				and (upper(trim(r.lname)) = 'UNKNOWN' or trim(r.lname) is null)""", documentId);
	}

	/**
	 * Stops (status 43) a received report when none of its DIN records is open (every one stopped).
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int stopDoccsDocumentAtRaw(BigDecimal documentId) {
		return jpql.update("""
				update RawDoccsDoc d set d.currentProcessStatus = 43
				where d.documentId = ?1
				and not exists (select 1 from RawDinDoccsDoc r where r.doccsDocId = d.documentId and coalesce(r.currentProcessStatus, 1) != 43)""",
				documentId);
	}

	/** Marks the waiting received DIN records (status 1) of a report as standardized (status 5). */
	@Transactional
	public void updateRawDinDocStatus(BigDecimal documentId) {
		jpql.update("update RawDinDoccsDoc r set r.currentProcessStatus = 5 where r.currentProcessStatus = 1 and r.doccsDocId = ?1", documentId);
	}

	/** The DIN records with their problem codes, as the screens read them (the former columns and aliases). */
	private static final String DIN_DOCUMENTS = """
			select std.documentId as DOCUMENT_ID, std.fname as FNAME, std.lname as LNAME, std.mname as MNAME, std.dobText as DOB,
			raw1.lstreet as LSTREET, std.currentGender as GENDER, std.ethnicity as ETHNICITY, std.din as DIN, std.nysid as NYSID,
			std.dodText as DOD_TEXT, std.underCustody as UNDER_CUSTODY, std.underSupervision as UNDER_SUPERVISION,
			std.birthCountry as BIRTH_COUNTRY, std.enteredUsDateText as ENTERED_US_DATE_TEXT, std.adminDateText as ADMIN_DATE_TEXT,
			std.latestAdminDateText as LATEST_ADMIN_DATE_TEXT, std.earliestReleaseDateText as EARLIEST_RELEASE_DATE_TEXT,
			std.latestDischargeDateText as LATEST_DISCHARGE_DATE_TEXT, std.latestReleaseDateText as LATEST_RELEASE_DATE_TEXT,
			std.personCity as PERSON_CITY, std.personState as PERSON_STATE, std.personZip as PERSON_ZIP, std.personPlus4 as PERSON_PLUS4,
			std.personCounty as PERSON_COUNTY, std.resco as RESCO, std.lrelda as LRELDA, std.currentFacilityName as CURRENT_FACILITY_NAME,
			std.currentFacilityAddress as CURRENT_FACILITY_ADDRESS, std.currentFacilityCity as CURRENT_FACILITY_CITY,
			std.currentFacilityState as CURRENT_FACILITY_STATE, std.currentFacilityZip as CURRENT_FACILITY_ZIP,
			std.currentFacilityPlus4 as CURRENT_FACILITY_PLUS4, std.currentFacilityCounty as CURRENT_FACILITY_COUNTY,
			std.currentFacilityPhone as CURRENT_FACILITY_PHONE, std.owningFacilityName as OWNING_FACILITY_NAME,
			std.owningFacilityAddress as OWNING_FACILITY_ADDRESS, std.owningFacilityCity as OWNING_FACILITY_CITY,
			std.owningFacilityState as OWNING_FACILITY_STATE, std.owningFacilityZip as OWNING_FACILITY_ZIP,
			std.owningFacilityPlus4 as OWNING_FACILITY_PLUS4, std.owningFacilityCounty as OWNING_FACILITY_COUNTY,
			std.owningFacilityPhone as OWNING_FACILITY_PHONE, raw1.prevStreet as PREV_STREET, std.prevPersonAddress as PREV_PERSON_ADDRESS,
			std.prevPersonCity as PREV_PERSON_CITY, std.prevPersonState as PREV_PERSON_STATE, std.prevPersonZip as PREV_PERSON_ZIP,
			std.drug1 as DRUG1, std.drug2 as DRUG2, std.drug3 as DRUG3, std.paroleReleaseCounty as PAROLE_RELEASE_COUNTY,
			std.alienStatus as ALIEN_STATUS, std.doccsFile as DOCCS_FILE, std.raceIndian as RACE_INDIAN, std.raceBlack as RACE_BLACK,
			std.raceAsian as RACE_ASIAN, std.racePacific as RACE_PACIFIC, std.raceWhite as RACE_WHITE, std.raceMultiple as RACE_MULTIPLE,
			std.raceOther as RACE_OTHER, std.raceUnknown as RACE_UNKNOWN, std.ssn as SSN, std.suffix as SUFFIX,
			std.personAddress as PERSON_ADDRESS, std.eharsImportDate as EHARS_IMPORT_DATE, std.nycShipDate as NYC_SHIP_DATE,
			std.legalPersonAddress as LEGAL_PERSON_ADDRESS, std.legalPersonCity as LEGAL_PERSON_CITY,
			std.legalPersonState as LEGAL_PERSON_STATE, std.legalPersonZip as LEGAL_PERSON_ZIP,
			std.legalPersonCountyName as LEGAL_PERSON_COUNTY_NAME, std.medicaidNumber as MEDICAID_NUMBER, raw1.street as STREET,
			c.problemDescription as PROBLEM_CD, p.problemCdDate as PROBLEM_CD_DATE, p.hivDiag as HIV_DIAG,
			p.hivDiagDateText as HIV_DIAG_DATE_TEXT, p.aidsDiag as AIDS_DIAG, p.aidsDiagDateText as AIDS_DIAG_DATE_TEXT
			from StdDinDoccsDoc std
			join RawDinDoccsDoc raw1 on std.documentId = raw1.documentId
			left join StdDoccsProbCodes p on std.documentId = p.dinDoccsDocId
			left join CodedDoccsProblemCodes c on p.problemCd = c.problemCd
			where %s
			order by function('FN_TO_DATE' as Timestamp, std.latestAdminDateText, 'MM/DD/YYYY') desc""";

	/** The columns of a problem code (the last ones of {@link #DIN_DOCUMENTS}). */
	private static final int PROBLEM_CODE_COLUMNS = 6;

	private static final RowMapper<DinDoccsDocVO> DIN_DOCUMENT = Rows.bean(DinDoccsDocVO.class);
	private static final RowMapper<DoccsProbCodesVO> PROBLEM_CODE = Rows.bean(DoccsProbCodesVO.class);

	/**
	 * The DIN records of a report (byDoccsDocId true: DoccsDocId) or one DIN record (docId), latest admission first,
	 * each with its problem codes, grouped as the former result map grouped them: one VO per distinct set of record
	 * values (in the order of first appearance), its codes once per distinct set of code values, a row without code
	 * adding none (an empty list).
	 *
	 * @param inputParameterMap byDoccsDocId, DoccsDocId, docId
	 */
	public List<DinDoccsDocVO> getDinDoccsDocContent(Map<String, Object> inputParameterMap) {
		boolean byReport = Boolean.TRUE.equals(inputParameterMap.get("byDoccsDocId"));
		List<Row> rows = byReport
				? jpql.list(DIN_DOCUMENTS.formatted("std.doccsDocId = ?1"), row -> row, longValue(inputParameterMap.get("DoccsDocId")))
				: jpql.list(DIN_DOCUMENTS.formatted("std.documentId = ?1"), row -> row, longValue(inputParameterMap.get("docId")));
		Map<List<Object>, DinDoccsDocVO> documents = new LinkedHashMap<>();
		Map<DinDoccsDocVO, Set<List<Object>>> codesOfDocument = new IdentityHashMap<>();
		for (Row row : rows) {
			int split = row.size() - PROBLEM_CODE_COLUMNS;
			List<Object> documentValues = new ArrayList<>(row.values().subList(0, split));
			DinDoccsDocVO document = documents.get(documentValues);
			if (document == null) {
				document = DIN_DOCUMENT.map(new Row(row.labels().subList(0, split), documentValues));
				if (document == null) {
					continue;
				}
				document.setDoccsProbCodes(new ArrayList<>());
				documents.put(documentValues, document);
				codesOfDocument.put(document, new HashSet<>());
			}
			List<Object> codeValues = new ArrayList<>(row.values().subList(split, row.size()));
			DoccsProbCodesVO code = PROBLEM_CODE.map(new Row(row.labels().subList(split, row.size()), codeValues));
			if (code != null && codesOfDocument.get(document).add(codeValues)) {
				document.getDoccsProbCodes().add(code);
			}
		}
		return new ArrayList<>(documents.values());
	}
}
