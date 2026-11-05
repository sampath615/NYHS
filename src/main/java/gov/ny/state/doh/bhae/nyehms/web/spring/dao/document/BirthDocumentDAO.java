package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawBirthMatchDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdBirthInfantDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdBirthMotherDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawDocumentMatchResultsRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawBirthMatchDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdBirthInfantDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdBirthMotherDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * The birth (BTH) documents of the birth import: the received birth record read for the mother and the infant, the
 * standardized mother and infant rows written, and their match results (formerly a MyBatis mapper with the same name
 * and methods), through Spring Data repositories over the entities RawBirthMatchDoc, StdBirthMotherDoc,
 * StdBirthInfantDoc and RawDocumentMatchResults. No native SQL.
 * <p>
 * The reads return the former map rows (keys = the former column labels, nulls left out, a row of nulls null). The
 * inserts keep the conversions of the former SQL in the database (the entities' write transformers: UPPER/TRIM,
 * FN_TO_DATE/TO_CHAR, TO_DATE). MATCHED_PROCESS_DATE stays the database's SYSDATE, on insert and on update (HQL
 * {@code sysdate}).
 *
 * @author ska03
 */
@Repository
@RequiredArgsConstructor
public class BirthDocumentDAO {

	private final RawBirthMatchDocRepository rawBirthDocs;
	private final StdBirthMotherDocRepository motherDocs;
	private final StdBirthInfantDocRepository infantDocs;
	private final RawDocumentMatchResultsRepository matchResults;

	/** The mother columns of a received birth record (null if there is none). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getMomData(String birthMatchId) {
		RawBirthMatchDoc doc = one(rawBirthDocs.findAllByBirthMatchPk(birthMatchId));
		return doc == null ? null : (Map) momData(doc);
	}

	/** The infant columns of a received birth record (null if there is none). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getInfantData(String birthMatchId) {
		RawBirthMatchDoc doc = one(rawBirthDocs.findAllByBirthMatchPk(birthMatchId));
		return doc == null ? null : (Map) infantData(doc);
	}

	/** Inserts the standardized mother row from the import's map (keys as the former insert read them). */
	@Transactional
	public int writeStdBirthMomData(Map<String, Object> inputParameterMap) {
		motherDocs.saveAndFlush(mother(inputParameterMap));
		return 1;
	}

	/** Inserts the standardized infant row from the import's map (keys as the former insert read them). */
	@Transactional
	public int writeStdBirthInfantData(Map<String, Object> inputParameterMap) {
		infantDocs.saveAndFlush(infant(inputParameterMap));
		return 1;
	}

	/** The number of 'M' / 'C' match results of the quality-stage parent documents of a document. */
	public int isDuplicateDocumentHasAnyMatches(String documentId) {
		return (int) matchResults.countParentMatches(longValue(documentId));
	}

	/** The number of standardized mother rows of a birth record. */
	public int birthMomDocumentExists(String birthMatchId) {
		return (int) motherDocs.countByBirthMatchPk(birthMatchId);
	}

	/** The number of standardized infant rows of a birth record. */
	public int birthInfantDocumentExists(String birthMatchId) {
		return (int) infantDocs.countByBirthMatchPk(birthMatchId);
	}

	/**
	 * Sets the matched address of the standardized mother rows of a birth record (documentId is its BIRTH_MATCH_PK,
	 * as before) and MATCHED_PROCESS_DATE.
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateBirthMomDocumentMatchResults(Map<String, Object> documentMatchData) {
		Map<String, Object> m = documentMatchData;
		return motherDocs.updateMatchResults(text(m.get("personAddress")), text(m.get("personCity")), text(m.get("personState")),
				text(m.get("personZip")), text(m.get("personCounty")), text(m.get("personLatitude")), text(m.get("personLongitude")),
				text(m.get("personCensusTract")), text(m.get("documentId")));
	}

	/**
	 * Sets the matched address of the standardized infant rows of a birth record (documentId is its BIRTH_MATCH_PK,
	 * as before) and MATCHED_PROCESS_DATE; the values are written as given (not upper-cased as on insert).
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateBirthInfantDocumentMatchResults(Map<String, Object> documentMatchData) {
		Map<String, Object> m = documentMatchData;
		return infantDocs.updateMatchResults(text(m.get("personAddress")), text(m.get("personCity")), text(m.get("personState")),
				text(m.get("personZip")), text(m.get("personCounty")), text(m.get("personLatitude")), text(m.get("personLongitude")),
				text(m.get("personCensusTract")), text(m.get("documentId")));
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

	private static String text(Object value) {
		return oracleText(value);
	}

	/** A value written to a text column as Oracle converted it implicitly (numbers without trailing zeros, 0.5 as ".5"). */
	private static String oracleText(Object value) {
		if (value == null || value instanceof String) {
			return (String) value;
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

	private static Integer integer(Object value) {
		return value == null || "".equals(value) ? null : new BigDecimal(value.toString().trim()).intValueExact();
	}

	/** The mother columns of getMomData, as the former map: nulls left out, a row of nulls null. */
	private static Map<String, Object> momData(RawBirthMatchDoc doc) {
		Map<String, Object> row = new HashMap<>();
		put(row, "MOTHER_RES_COUNTRY", doc.getMotherResCountry());
		put(row, "MOTHER_FNAME", doc.getMotherFname());
		put(row, "MOTHER_MNAME", doc.getMotherMname());
		put(row, "MOTHER_CURRENT_LNAME", doc.getMotherCurrentLname());
		put(row, "MOTHER_MAIDEN_LNAME", doc.getMotherMaidenLname());
		put(row, "MOTHER_MED_REC", doc.getMotherMedRec());
		put(row, "MOTHER_BIRTH_STATE", doc.getMotherBirthState());
		put(row, "MOTHER_BIRTH_COUNTRY_CD", doc.getMotherBirthCountryCd());
		put(row, "MOTHER_WHITE", doc.getMotherWhite());
		put(row, "MOTHER_BLACK", doc.getMotherBlack());
		put(row, "MOTHER_ASIAN_INDIAN", doc.getMotherAsianIndian());
		put(row, "MOTHER_CHINESE", doc.getMotherChinese());
		put(row, "MOTHER_FILIPINO", doc.getMotherFilipino());
		put(row, "MOTHER_JAPANESE", doc.getMotherJapanese());
		put(row, "MOTHER_KOREAN", doc.getMotherKorean());
		put(row, "MOTHER_VIETNAMESE", doc.getMotherVietnamese());
		put(row, "MOTHER_ASIAN_OTHER", doc.getMotherAsianOther());
		put(row, "MOTHER_HAWAIIAN", doc.getMotherHawaiian());
		put(row, "MOTHER_GUAMANIAN", doc.getMotherGuamanian());
		put(row, "MOTHER_SAMOAN", doc.getMotherSamoan());
		put(row, "MOTHER_PACIFIC_ISLANDER_OTHER", doc.getMotherPacificIslanderOther());
		put(row, "MOTHER_AMER_INDIAN_ALASKA_NATIVE", doc.getMotherAmerIndianAlaskaNative());
		put(row, "MOTHER_RACE_OTHER", doc.getMotherRaceOther());
		put(row, "MOTHER_NOT_HISPANIC", doc.getMotherNotHispanic());
		put(row, "MOTHER_HISP_ORIGIN_MEXICAN", doc.getMotherHispOriginMexican());
		put(row, "MOTHER_HISP_ORIGIN_PUERTO_RICO", doc.getMotherHispOriginPuertoRico());
		put(row, "MOTHER_HISP_ORIGIN_CUBAN", doc.getMotherHispOriginCuban());
		put(row, "MOTHER_HISP_ORIGIN_OTHER", doc.getMotherHispOriginOther());
		put(row, "MOTHER_DOB", doc.getMotherDob());
		put(row, "MOTHER_EDU_LEVEL", doc.getMotherEduLevel());
		put(row, "FIRST_PRENATAL_VISIT_DT", doc.getFirstPrenatalVisitDt());
		put(row, "LAST_PRENATAL_VISIT_DT", doc.getLastPrenatalVisitDt());
		put(row, "NO_PRENATAL_VISITS", doc.getNoPrenatalVisits());
		put(row, "GESTATION_WKS", doc.getGestationWks());
		put(row, "NO_PREV_LIVE_BIRTHS_LIVING", doc.getNoPrevLiveBirthsLiving());
		put(row, "NO_PREV_LIVE_BIRTHS_DEAD", doc.getNoPrevLiveBirthsDead());
		put(row, "NO_PREV_PREG_TOTAL", doc.getNoPrevPregTotal());
		put(row, "INFECTIONS_NONE", doc.getInfectionsNone());
		put(row, "GONORRHEA", doc.getGonorrhea());
		put(row, "SYPHILIS", doc.getSyphilis());
		put(row, "GENITAL_HERPES", doc.getGenitalHerpes());
		put(row, "CHLAMYDIA", doc.getChlamydia());
		put(row, "HEP_B", doc.getHepB());
		put(row, "HEP_C", doc.getHepC());
		put(row, "TB", doc.getTb());
		put(row, "RUBELLA", doc.getRubella());
		put(row, "BACTERIAL_VAGINOSIS", doc.getBacterialVaginosis());
		put(row, "INFECTIONS_UNK", doc.getInfectionsUnk());
		put(row, "MOTHER_SSN", doc.getMotherSsn());
		put(row, "MOTHER_AGE", doc.getMotherAge());
		put(row, "START_PRENATAL_CARE_TRIMESTER", doc.getStartPrenatalCareTrimester());
		put(row, "MOTHER_PHONE", doc.getMotherPhone());
		put(row, "PRENATAL_CARE_RECEIVED", doc.getPrenatalCareReceived());
		put(row, "BIRTH_FILE", doc.getBirthFile());
		return row.isEmpty() ? null : row;
	}

	/** The infant columns of getInfantData, as the former map: nulls left out, a row of nulls null. */
	private static Map<String, Object> infantData(RawBirthMatchDoc doc) {
		Map<String, Object> row = new HashMap<>();
		put(row, "POB_INST", doc.getPobInst());
		put(row, "POB_INST_TYPE", doc.getPobInstType());
		put(row, "BIRTH_FAC_PFI", doc.getBirthFacPfi());
		put(row, "BIRTH_ADDR_STREET", doc.getBirthAddrStreet());
		put(row, "BIRTH_ADDR_ZIP", doc.getBirthAddrZip());
		put(row, "BIRTH_CERT_NO", doc.getBirthCertNo());
		put(row, "DEATH_CERT_NO", doc.getDeathCertNo());
		put(row, "DEATH_DT", doc.getDeathDt());
		put(row, "DEATH_STATE", doc.getDeathState());
		put(row, "INFANT_FNAME", doc.getInfantFname());
		put(row, "INFANT_MNAME", doc.getInfantMname());
		put(row, "INFANT_LNAME", doc.getInfantLname());
		put(row, "INFANT_SUFFIX", doc.getInfantSuffix());
		put(row, "INFANT_MED_REC", doc.getInfantMedRec());
		put(row, "INFANT_DOB", doc.getInfantDob());
		put(row, "INFANT_DOB_TIME", doc.getInfantDobTime());
		put(row, "INFANT_SEX", doc.getInfantSex());
		put(row, "INFANT_PLURALITY", doc.getInfantPlurality());
		put(row, "INFANT_BIRTH_WEIGHT", doc.getInfantBirthWeight());
		put(row, "MOTHER_FNAME", doc.getMotherFname());
		put(row, "MOTHER_MNAME", doc.getMotherMname());
		put(row, "MOTHER_CURRENT_LNAME", doc.getMotherCurrentLname());
		put(row, "MOTHER_MAIDEN_LNAME", doc.getMotherMaidenLname());
		put(row, "INFANT_TRANSFERRED", doc.getInfantTransferred());
		put(row, "HOSP_REC_TRANSFERRED_INFANT", doc.getHospRecTransferredInfant());
		put(row, "INFANT_ALIVE_REP_TIME", doc.getInfantAliveRepTime());
		put(row, "SYPHILIS_TEST_GIVEN", doc.getSyphilisTestGiven());
		put(row, "HOW_INFANT_FED", doc.getHowInfantFed());
		put(row, "ABNORMAL_CONDITION_NEWBORN_NONE", doc.getAbnormalConditionNewbornNone());
		put(row, "CONGENITAL_MALFORMATIONS_NONE", doc.getCongenitalMalformationsNone());
		put(row, "CONGENITAL_MALFORMATIONS_UNK", doc.getCongenitalMalformationsUnk());
		put(row, "ANENCEPHALY", doc.getAnencephaly());
		put(row, "SPINA_BIFIDA", doc.getSpinaBifida());
		put(row, "CONGENITAL_HEART_DISEASE", doc.getCongenitalHeartDisease());
		put(row, "HERNIA", doc.getHernia());
		put(row, "OMPHALOCELE", doc.getOmphalocele());
		put(row, "GASTROSCHISIS", doc.getGastroschisis());
		put(row, "LIMB_REDUCT_DEFECT", doc.getLimbReductDefect());
		put(row, "CLEFT_LIP_PALATE", doc.getCleftLipPalate());
		put(row, "CLEFT_PALATE", doc.getCleftPalate());
		put(row, "DOWN_SYNDROME", doc.getDownSyndrome());
		put(row, "DOWN_SYNDROME_KARYOTYPE_CONFIRM", doc.getDownSyndromeKaryotypeConfirm());
		put(row, "DOWN_SYNDROME_KARYOTYPE_PENDING", doc.getDownSyndromeKaryotypePending());
		put(row, "CHROMOSOMAL_DISORDER", doc.getChromosomalDisorder());
		put(row, "CHROMOSOMAL_KARYOTYPE_CONFIRM", doc.getChromosomalKaryotypeConfirm());
		put(row, "CHROMOSOMAL_KARYOTYPE_PENDING", doc.getChromosomalKaryotypePending());
		put(row, "HYPOSPADIAS", doc.getHypospadias());
		put(row, "ANENCEPHALY_DIAG_PRENATAL", doc.getAnencephalyDiagPrenatal());
		put(row, "ANENCEPHALY_DIAG_ULTRASOUND", doc.getAnencephalyDiagUltrasound());
		put(row, "ANENCEPHALY_DIAG_TRIPLE_SCREEN", doc.getAnencephalyDiagTripleScreen());
		put(row, "ANENCEPHALY_DIAG_AMNIOCENTESIS", doc.getAnencephalyDiagAmniocentesis());
		put(row, "ANENCEPHALY_DIAG_OTHER", doc.getAnencephalyDiagOther());
		put(row, "ANENCEPHALY_DIAG_UNK", doc.getAnencephalyDiagUnk());
		put(row, "SPINA_BIFIDA_DIAG_PRENATAL", doc.getSpinaBifidaDiagPrenatal());
		put(row, "SPINA_BIFIDA_DIAG_ULTRASOUND", doc.getSpinaBifidaDiagUltrasound());
		put(row, "SPINA_BIFIDA_DIAG_TRIPLE_SCREEN", doc.getSpinaBifidaDiagTripleScreen());
		put(row, "SPINA_BIFIDA_DIAG_AMNIOCENTESIS", doc.getSpinaBifidaDiagAmniocentesis());
		put(row, "SPINA_BIFIDA_DIAG_OTHER", doc.getSpinaBifidaDiagOther());
		put(row, "SPINA_BIFIDA_DIAG_UNK", doc.getSpinaBifidaDiagUnk());
		put(row, "HEART_DIS_DIAG_PRENATAL", doc.getHeartDisDiagPrenatal());
		put(row, "HEART_DIS_DIAG_ULTRASOUND", doc.getHeartDisDiagUltrasound());
		put(row, "HEART_DIS_DIAG_OTHER", doc.getHeartDisDiagOther());
		put(row, "HEART_DIS_DIAG_UNK", doc.getHeartDisDiagUnk());
		put(row, "HERNIA_DIAG_PRENATAL", doc.getHerniaDiagPrenatal());
		put(row, "HERNIA_DIAG_ULTRASOUND", doc.getHerniaDiagUltrasound());
		put(row, "HERNIA_DIAG_OTHER", doc.getHerniaDiagOther());
		put(row, "HERNIA_DIAG_UNK", doc.getHerniaDiagUnk());
		put(row, "OMPHALOCELE_DIAG_PRENATAL", doc.getOmphaloceleDiagPrenatal());
		put(row, "OMPHALOCELE_DIAG_ULTRASOUND", doc.getOmphaloceleDiagUltrasound());
		put(row, "OMPHALOCELE_DIAG_OTHER", doc.getOmphaloceleDiagOther());
		put(row, "OMPHALOCELE_DIAG_UNK", doc.getOmphaloceleDiagUnk());
		put(row, "GASTROSCHISIS_DIAG_PRENATAL", doc.getGastroschisisDiagPrenatal());
		put(row, "GASTROSCHISIS_DIAG_ULTRASOUND", doc.getGastroschisisDiagUltrasound());
		put(row, "GASTROSCHISIS_DIAG_OTHER", doc.getGastroschisisDiagOther());
		put(row, "GASTROSCHISIS_DIAG_UNK", doc.getGastroschisisDiagUnk());
		put(row, "LIMB_REDUCT_DIAG_PRENATAL", doc.getLimbReductDiagPrenatal());
		put(row, "LIMB_REDUCT_DIAG_ULTRASOUND", doc.getLimbReductDiagUltrasound());
		put(row, "LIMB_REDUCT_DIAG_OTHER", doc.getLimbReductDiagOther());
		put(row, "LIMB_REDUCT_DIAG_UNK", doc.getLimbReductDiagUnk());
		put(row, "CLEFT_LIP_PALATE_DIAG_PRENATAL", doc.getCleftLipPalateDiagPrenatal());
		put(row, "CLEFT_LIP_PALATE_DIAG_ULTRASOUND", doc.getCleftLipPalateDiagUltrasound());
		put(row, "CLEFT_LIP_PALATE_DIAG_OTHER", doc.getCleftLipPalateDiagOther());
		put(row, "CLEFT_LIP_PALATE_DIAG_UNK", doc.getCleftLipPalateDiagUnk());
		put(row, "CLEFT_PALATE_DIAG_PRENATAL", doc.getCleftPalateDiagPrenatal());
		put(row, "CLEFT_PALATE_DIAG_ULTRASOUND", doc.getCleftPalateDiagUltrasound());
		put(row, "CLEFT_PALATE_DIAG_OTHER", doc.getCleftPalateDiagOther());
		put(row, "CLEFT_PALATE_DIAG_UNK", doc.getCleftPalateDiagUnk());
		put(row, "DOWN_SYNDROME_DIAG_PRENATAL", doc.getDownSyndromeDiagPrenatal());
		put(row, "DOWN_SYNDROME_DIAG_ULTRASOUND", doc.getDownSyndromeDiagUltrasound());
		put(row, "DOWN_SYNDROME_DIAG_TRIPLE_SCREEN", doc.getDownSyndromeDiagTripleScreen());
		put(row, "DOWN_SYNDROME_DIAG_CVS", doc.getDownSyndromeDiagCvs());
		put(row, "DOWN_SYNDROME_DIAG_AMNIOCENTESIS", doc.getDownSyndromeDiagAmniocentesis());
		put(row, "DOWN_SYNDROME_DIAG_OTHER", doc.getDownSyndromeDiagOther());
		put(row, "DOWN_SYNDROME_DIAG_UNK", doc.getDownSyndromeDiagUnk());
		put(row, "CHROMOSOMAL_DIAG_PRENATAL", doc.getChromosomalDiagPrenatal());
		put(row, "CHROMOSOMAL_DIAG_ULTRASOUND", doc.getChromosomalDiagUltrasound());
		put(row, "CHROMOSOMAL_DIAG_TRIPLE_SCREEN", doc.getChromosomalDiagTripleScreen());
		put(row, "CHROMOSOMAL_DIAG_CVS", doc.getChromosomalDiagCvs());
		put(row, "CHROMOSOMAL_DIAG_AMNIOCENTESIS", doc.getChromosomalDiagAmniocentesis());
		put(row, "CHROMOSOMAL_DIAG_OTHER", doc.getChromosomalDiagOther());
		put(row, "CHROMOSOMAL_DIAG_UNK", doc.getChromosomalDiagUnk());
		put(row, "HYPOSPADIAS_DIAG_PRENATAL", doc.getHypospadiasDiagPrenatal());
		put(row, "HYPOSPADIAS_DIAG_ULTRASOUND", doc.getHypospadiasDiagUltrasound());
		put(row, "HYPOSPADIAS_DIAG_OTHER", doc.getHypospadiasDiagOther());
		put(row, "HYPOSPADIAS_DIAG_UNK", doc.getHypospadiasDiagUnk());
		put(row, "CURRENT_PROCESS_STATUS", doc.getCurrentProcessStatus());
		put(row, "BIRTH_FILE", doc.getBirthFile());
		put(row, "RECEIVED_DATE", doc.getReceivedDate());
		put(row, "MOTHER_RES_COUNTRY", doc.getMotherResCountry());
		return row.isEmpty() ? null : row;
	}

	/** The row of writeStdBirthMomData: the former insert's columns from the caller's map (its keys as the insert read them). */
	private static StdBirthMotherDoc mother(Map<String, Object> values) {
		return StdBirthMotherDoc.builder()
				.documentId(longValue(values.get("DOCUMENT_ID")))
				.birthMatchPk(oracleText(values.get("BIRTH_MATCH_PK")))
				.personAddress(oracleText(values.get("PERSON_ADDRESS")))
				.personZip(oracleText(values.get("PERSON_ZIP")))
				.personCity(oracleText(values.get("PERSON_CITY")))
				.personState(oracleText(values.get("PERSON_STATE")))
				.personCountry(oracleText(values.get("PERSON_COUNTRY")))
				.personCounty(oracleText(values.get("PERSON_COUNTY")))
				.fname(oracleText(values.get("FNAME")))
				.mname(oracleText(values.get("MNAME")))
				.currentLname(oracleText(values.get("CURRENT_LNAME")))
				.maidenLname(oracleText(values.get("MAIDEN_LNAME")))
				.medRecordNumber(oracleText(values.get("MED_RECORD_NUMBER")))
				.birthState(oracleText(values.get("BIRTH_STATE")))
				.birthCountryCd(oracleText(values.get("BIRTH_COUNTRY_CD")))
				.raceWhite(oracleText(values.get("RACE_WHITE")))
				.raceBlack(oracleText(values.get("RACE_BLACK")))
				.raceAsian(oracleText(values.get("RACE_ASIAN")))
				.racePacific(oracleText(values.get("RACE_PACIFIC")))
				.raceIndian(oracleText(values.get("RACE_INDIAN")))
				.raceOther(oracleText(values.get("RACE_OTHER")))
				.ethnicity(oracleText(values.get("ETHNICITY")))
				.dob(oracleText(values.get("DOB")))
				.ssn(oracleText(values.get("SSN")))
				.age(oracleText(values.get("AGE")))
				.personPhone(oracleText(values.get("PERSON_PHONE")))
				.eduLevel(oracleText(values.get("EDU_LEVEL")))
				.firstPrenatalVisitDt(oracleText(values.get("FIRST_PRENATAL_VISIT_DT")))
				.lastPrenatalVisitDt(oracleText(values.get("LAST_PRENATAL_VISIT_DT")))
				.noPrenatalVisits(oracleText(values.get("NO_PRENATAL_VISITS")))
				.gestationWks(oracleText(values.get("GESTATION_WKS")))
				.noPrevLiveBirthsLiving(oracleText(values.get("NO_PREV_LIVE_BIRTHS_LIVING")))
				.noPrevLiveBirthsDead(oracleText(values.get("NO_PREV_LIVE_BIRTHS_DEAD")))
				.noPrevPregTotal(oracleText(values.get("NO_PREV_PREG_TOTAL")))
				.infectionsNone(oracleText(values.get("INFECTIONS_NONE")))
				.gonorrhea(oracleText(values.get("GONORRHEA")))
				.syphilis(oracleText(values.get("SYPHILIS")))
				.genitalHerpes(oracleText(values.get("GENITAL_HERPES")))
				.chlamydia(oracleText(values.get("CHLAMYDIA")))
				.hepB(oracleText(values.get("HEP_B")))
				.hepC(oracleText(values.get("HEP_C")))
				.tb(oracleText(values.get("TB")))
				.rubella(oracleText(values.get("RUBELLA")))
				.bacterialVaginosis(oracleText(values.get("BACTERIAL_VAGINOSIS")))
				.infectionsUnk(oracleText(values.get("INFECTIONS_UNK")))
				.startPrenatalCareTrimester(oracleText(values.get("START_PRENATAL_CARE_TRIMESTER")))
				.prenatalCareReceived(oracleText(values.get("PRENATAL_CARE_RECEIVED")))
				.currentProcessStatus(integer(values.get("CURRENT_PROCESS_STATUS")))
				.birthFile(oracleText(values.get("BIRTH_FILE")))
				.receivedDate(oracleText(values.get("RECEIVED_DATE")))
				.personLatitude(oracleText(values.get("personLatitude")))
				.personLongitude(oracleText(values.get("personLongitude")))
				.personCensusTract(oracleText(values.get("personCensusTract")))
				.build();
	}

	/** The row of writeStdBirthInfantData: the former insert's columns from the caller's map (its keys as the insert read them). */
	private static StdBirthInfantDoc infant(Map<String, Object> values) {
		return StdBirthInfantDoc.builder()
				.documentId(longValue(values.get("DOCUMENT_ID")))
				.birthMatchPk(oracleText(values.get("BIRTH_MATCH_PK")))
				.pobInst(oracleText(values.get("POB_INST")))
				.pobInstType(oracleText(values.get("POB_INST_TYPE")))
				.birthFacPfi(oracleText(values.get("BIRTH_FAC_PFI")))
				.birthAddrStreet(oracleText(values.get("BIRTH_ADDR_STREET")))
				.birthAddrZip(oracleText(values.get("BIRTH_ADDR_ZIP")))
				.birthCertNo(oracleText(values.get("BIRTH_CERT_NO")))
				.deathCertNo(oracleText(values.get("DEATH_CERT_NO")))
				.deathDt(oracleText(values.get("DEATH_DT")))
				.deathState(oracleText(values.get("DEATH_STATE")))
				.fname(oracleText(values.get("FNAME")))
				.mname(oracleText(values.get("MNAME")))
				.lname(oracleText(values.get("LNAME")))
				.suffix(oracleText(values.get("SUFFIX")))
				.medRecordNumber(oracleText(values.get("MED_RECORD_NUMBER")))
				.dob(oracleText(values.get("DOB")))
				.dobTime(oracleText(values.get("DOB_TIME")))
				.sex(oracleText(values.get("SEX")))
				.personAddress(oracleText(values.get("PERSON_ADDRESS")))
				.personZip(oracleText(values.get("PERSON_ZIP")))
				.personCity(oracleText(values.get("PERSON_CITY")))
				.personState(oracleText(values.get("PERSON_STATE")))
				.personCountry(oracleText(values.get("PERSON_COUNTRY")))
				.personCounty(oracleText(values.get("PERSON_COUNTY")))
				.personCensusTract(oracleText(values.get("PERSON_CENSUS_TRACT")))
				.personLongitude(oracleText(values.get("PERSON_LONGITUDE")))
				.personLatitude(oracleText(values.get("PERSON_LATITUDE")))
				.plurality(oracleText(values.get("INFANT_PLURALITY")))
				.birthWeight(oracleText(values.get("INFANT_BIRTH_WEIGHT")))
				.motherFname(oracleText(values.get("MOTHER_FNAME")))
				.motherMname(oracleText(values.get("MOTHER_MNAME")))
				.motherCurrentLname(oracleText(values.get("MOTHER_CURRENT_LNAME")))
				.motherMaidenLname(oracleText(values.get("MOTHER_MAIDEN_LNAME")))
				.infantTransferred(oracleText(values.get("INFANT_TRANSFERRED")))
				.hospRecTransferredInfant(oracleText(values.get("HOSP_REC_TRANSFERRED_INFANT")))
				.infantAliveRepTime(oracleText(values.get("INFANT_ALIVE_REP_TIME")))
				.syphilisTestGiven(oracleText(values.get("SYPHILIS_TEST_GIVEN")))
				.howInfantFed(oracleText(values.get("HOW_INFANT_FED")))
				.abnormalConditionNewbornNone(oracleText(values.get("ABNORMAL_CONDITION_NEWBORN_NONE")))
				.congenitalMalformationsNone(oracleText(values.get("CONGENITAL_MALFORMATIONS_NONE")))
				.congenitalMalformationsUnk(oracleText(values.get("CONGENITAL_MALFORMATIONS_UNK")))
				.anencephaly(oracleText(values.get("ANENCEPHALY")))
				.spinaBifida(oracleText(values.get("SPINA_BIFIDA")))
				.congenitalHeartDisease(oracleText(values.get("CONGENITAL_HEART_DISEASE")))
				.hernia(oracleText(values.get("HERNIA")))
				.omphalocele(oracleText(values.get("OMPHALOCELE")))
				.gastroschisis(oracleText(values.get("GASTROSCHISIS")))
				.limbReductDefect(oracleText(values.get("LIMB_REDUCT_DEFECT")))
				.cleftLipPalate(oracleText(values.get("CLEFT_LIP_PALATE")))
				.cleftPalate(oracleText(values.get("CLEFT_PALATE")))
				.downSyndrome(oracleText(values.get("DOWN_SYNDROME")))
				.downSyndromeKaryotypeConfirm(oracleText(values.get("DOWN_SYNDROME_KARYOTYPE_CONFIRM")))
				.downSyndromeKaryotypePending(oracleText(values.get("DOWN_SYNDROME_KARYOTYPE_PENDING")))
				.chromosomalDisorder(oracleText(values.get("CHROMOSOMAL_DISORDER")))
				.chromosomalKaryotypeConfirm(oracleText(values.get("CHROMOSOMAL_KARYOTYPE_CONFIRM")))
				.chromosomalKaryotypePending(oracleText(values.get("CHROMOSOMAL_KARYOTYPE_PENDING")))
				.hypospadias(oracleText(values.get("HYPOSPADIAS")))
				.anencephalyDiagPrenatal(oracleText(values.get("ANENCEPHALY_DIAG_PRENATAL")))
				.anencephalyDiagUltrasound(oracleText(values.get("ANENCEPHALY_DIAG_ULTRASOUND")))
				.anencephalyDiagTripleScreen(oracleText(values.get("ANENCEPHALY_DIAG_TRIPLE_SCREEN")))
				.anencephalyDiagAmniocentesis(oracleText(values.get("ANENCEPHALY_DIAG_AMNIOCENTESIS")))
				.anencephalyDiagOther(oracleText(values.get("ANENCEPHALY_DIAG_OTHER")))
				.anencephalyDiagUnk(oracleText(values.get("ANENCEPHALY_DIAG_UNK")))
				.spinaBifidaDiagPrenatal(oracleText(values.get("SPINA_BIFIDA_DIAG_PRENATAL")))
				.spinaBifidaDiagUltrasound(oracleText(values.get("SPINA_BIFIDA_DIAG_ULTRASOUND")))
				.spinaBifidaDiagTripleScreen(oracleText(values.get("SPINA_BIFIDA_DIAG_TRIPLE_SCREEN")))
				.spinaBifidaDiagAmniocentesis(oracleText(values.get("SPINA_BIFIDA_DIAG_AMNIOCENTESIS")))
				.spinaBifidaDiagOther(oracleText(values.get("SPINA_BIFIDA_DIAG_OTHER")))
				.spinaBifidaDiagUnk(oracleText(values.get("SPINA_BIFIDA_DIAG_UNK")))
				.heartDisDiagPrenatal(oracleText(values.get("HEART_DIS_DIAG_PRENATAL")))
				.heartDisDiagUltrasound(oracleText(values.get("HEART_DIS_DIAG_ULTRASOUND")))
				.heartDisDiagOther(oracleText(values.get("HEART_DIS_DIAG_OTHER")))
				.heartDisDiagUnk(oracleText(values.get("HEART_DIS_DIAG_UNK")))
				.herniaDiagPrenatal(oracleText(values.get("HERNIA_DIAG_PRENATAL")))
				.herniaDiagUltrasound(oracleText(values.get("HERNIA_DIAG_ULTRASOUND")))
				.herniaDiagOther(oracleText(values.get("HERNIA_DIAG_OTHER")))
				.herniaDiagUnk(oracleText(values.get("HERNIA_DIAG_UNK")))
				.omphaloceleDiagPrenatal(oracleText(values.get("OMPHALOCELE_DIAG_PRENATAL")))
				.omphaloceleDiagUltrasound(oracleText(values.get("OMPHALOCELE_DIAG_ULTRASOUND")))
				.omphaloceleDiagOther(oracleText(values.get("OMPHALOCELE_DIAG_OTHER")))
				.omphaloceleDiagUnk(oracleText(values.get("OMPHALOCELE_DIAG_UNK")))
				.gastroschisisDiagPrenatal(oracleText(values.get("GASTROSCHISIS_DIAG_PRENATAL")))
				.gastroschisisDiagUltrasound(oracleText(values.get("GASTROSCHISIS_DIAG_ULTRASOUND")))
				.gastroschisisDiagOther(oracleText(values.get("GASTROSCHISIS_DIAG_OTHER")))
				.gastroschisisDiagUnk(oracleText(values.get("GASTROSCHISIS_DIAG_UNK")))
				.limbReductDiagPrenatal(oracleText(values.get("LIMB_REDUCT_DIAG_PRENATAL")))
				.limbReductDiagUltrasound(oracleText(values.get("LIMB_REDUCT_DIAG_ULTRASOUND")))
				.limbReductDiagOther(oracleText(values.get("LIMB_REDUCT_DIAG_OTHER")))
				.limbReductDiagUnk(oracleText(values.get("LIMB_REDUCT_DIAG_UNK")))
				.cleftLipPalateDiagPrenatal(oracleText(values.get("CLEFT_LIP_PALATE_DIAG_PRENATAL")))
				.cleftLipPalateDiagUltrasound(oracleText(values.get("CLEFT_LIP_PALATE_DIAG_ULTRASOUND")))
				.cleftLipPalateDiagOther(oracleText(values.get("CLEFT_LIP_PALATE_DIAG_OTHER")))
				.cleftLipPalateDiagUnk(oracleText(values.get("CLEFT_LIP_PALATE_DIAG_UNK")))
				.cleftPalateDiagPrenatal(oracleText(values.get("CLEFT_PALATE_DIAG_PRENATAL")))
				.cleftPalateDiagUltrasound(oracleText(values.get("CLEFT_PALATE_DIAG_ULTRASOUND")))
				.cleftPalateDiagOther(oracleText(values.get("CLEFT_PALATE_DIAG_OTHER")))
				.cleftPalateDiagUnk(oracleText(values.get("CLEFT_PALATE_DIAG_UNK")))
				.downSyndromeDiagPrenatal(oracleText(values.get("DOWN_SYNDROME_DIAG_PRENATAL")))
				.downSyndromeDiagUltrasound(oracleText(values.get("DOWN_SYNDROME_DIAG_ULTRASOUND")))
				.downSyndromeDiagTripleScreen(oracleText(values.get("DOWN_SYNDROME_DIAG_TRIPLE_SCREEN")))
				.downSyndromeDiagCvs(oracleText(values.get("DOWN_SYNDROME_DIAG_CVS")))
				.downSyndromeDiagAmniocentesis(oracleText(values.get("DOWN_SYNDROME_DIAG_AMNIOCENTESIS")))
				.downSyndromeDiagOther(oracleText(values.get("DOWN_SYNDROME_DIAG_OTHER")))
				.downSyndromeDiagUnk(oracleText(values.get("DOWN_SYNDROME_DIAG_UNK")))
				.chromosomalDiagPrenatal(oracleText(values.get("CHROMOSOMAL_DIAG_PRENATAL")))
				.chromosomalDiagUltrasound(oracleText(values.get("CHROMOSOMAL_DIAG_ULTRASOUND")))
				.chromosomalDiagTripleScreen(oracleText(values.get("CHROMOSOMAL_DIAG_TRIPLE_SCREEN")))
				.chromosomalDiagCvs(oracleText(values.get("CHROMOSOMAL_DIAG_CVS")))
				.chromosomalDiagAmniocentesis(oracleText(values.get("CHROMOSOMAL_DIAG_AMNIOCENTESIS")))
				.chromosomalDiagOther(oracleText(values.get("CHROMOSOMAL_DIAG_OTHER")))
				.chromosomalDiagUnk(oracleText(values.get("CHROMOSOMAL_DIAG_UNK")))
				.hypospadiasDiagPrenatal(oracleText(values.get("HYPOSPADIAS_DIAG_PRENATAL")))
				.hypospadiasDiagUltrasound(oracleText(values.get("HYPOSPADIAS_DIAG_ULTRASOUND")))
				.hypospadiasDiagOther(oracleText(values.get("HYPOSPADIAS_DIAG_OTHER")))
				.hypospadiasDiagUnk(oracleText(values.get("HYPOSPADIAS_DIAG_UNK")))
				.currentProcessStatus(5)
				.birthFile(oracleText(values.get("BIRTH_FILE")))
				.receivedDate(oracleText(values.get("RECEIVED_DATE")))
				.build();
	}
}
