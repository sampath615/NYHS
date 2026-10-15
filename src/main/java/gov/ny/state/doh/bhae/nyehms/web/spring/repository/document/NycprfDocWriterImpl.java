package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * {@link NycprfDocWriter} with an HQL insert on the {@code StdNycprfDoc} entity.
 */
class NycprfDocWriterImpl implements NycprfDocWriter {

	/**
	 * Same columns and conversions as the former MyBatis statement: text is upper-cased and trimmed,
	 * three dates are converted with the database function FN_TO_DATE, all in Oracle. Each parameter
	 * is named after its column, i.e. after its key in the map.
	 */
	private static final String INSERT_STD_DOCUMENT = """
			insert into StdNycprfDoc (
				provFacility, provLname, provExt, rsonFirstPosOtherText, provAddress, personAddress, riskOtherText,
				firstPosLocation, lastNegFacType, personCensusTract, arv6MoName, personZip, provZip, numberTest2Yrs,
				birthCountry, recvBloodProdCountry, provDepartment, tsaid, revtsaid, personCity, raceOtherText, provCity,
				arvCurrentTest, personCounty, currentGender, ethnicity, firstPosAnon, respHivTest2yrs, fname, mname, lname,
				altName, provFname, provMi, provTitle, contactLname, contactFname, contactMname, warning, personPhone,
				provPhone, contactPhone, eharsNycPrfUid, personState, swm, swf, idu, sexPartnerIdu, sexPartnerHiv,
				recvBloodProd, perinatal, riskOther, riskUnknown, sexPartnerMsm, sexPartnerHemophiliac, healthcareWorker,
				psRequested, provState, notsend, arv6Mo, everTestNeg, medRecordNumber, ptInterviewDate, firstPosHivDate,
				arv6MoStartDateText, arv6MoEndDateText, firstHivTestEverDateText, lastNegDateText, ssn, personLongitude,
				raceCd, proEmail, contactTitle, mdprintname, firstPosFacName, lastNegFacName, cityno, dodText, personLatitude,
				din, recvBloodProdDateText, initialHivDiagDateText, informedHivDateText, prevHivDiagDateText,
				initAidsDiagDateText, prevAidsDiagDateText, ehars, firstPosFacState, lastNegFacState, matchedStateno,
				birthSex, raceWhite, raceBlack, raceAsian, raceIndian, racePacific, raceUnknown, raceOther, oldform,
				rsonFirstPosExp, rsonFirstPosRoutn, rsonFirstPosChkneg, rsonFirstPosReqd, rsonFirstPosOther, dob,
				admissionDate, completedDate, updateDate, printedDate, sigDate, eharsDate, receivedDate, reviewDate,
				createDate, eharsImportDate, enterDate, reviewUid, matchCd, createUid, eharsStatusCd, documentId, matchWeight,
				prfSrcId, initialHivDiag, informedHivPos, previousHivDiag, initialAidsDiag, previousAidsDiag,
				currentProcessStatus, matchPass, ageCategory, recnum, age, qsParentDocId, matchedProcessDate, altMname,
				altLname, edc, additionalGenderIdentity, personOtherIdentifier, primaryLanguage, primaryLanguageOther,
				referredHivProvider, referredProviderName, refferedProviderTelephone, ptArvEverTaken, arvPrevenPrep,
				arvPrevenPrepStartDt, arvPrevenPrepEndDt, arvPrevenprepMedNames, postExpArvPep, postExpArvPepStartDt,
				postExpArvPepEndDt, postExpArvPepMedNames, arvTreatment, ptArvStartDateText, ptArvEndDateText, ptArvName,
				rsonFirstPosPrtnr, facilityType, facilityTypeOtherText, providerNysLicNo, providerNpiNo, acuteInfection,
				pregnant, everHadPrevPosTest, eharsDocumentSourceCd, fluxMatchCd, fluxPotStateno, fluxMatchedStateno,
				fluxQsParentDocId, fluxMatchedProcessDate, fieldProcDateText, apartment, sexOrn, sexOrnAddId, numNegHivTest)
			values (
				upper(trim(:PROV_FACILITY)), upper(trim(:PROV_LNAME)), upper(trim(:PROV_EXT)),
				upper(trim(:RSON_FIRST_POS_OTHER_TEXT)), upper(trim(:PROV_ADDRESS)), upper(trim(:PERSON_ADDRESS)),
				upper(trim(:RISK_OTHER_TEXT)), upper(trim(:FIRST_POS_LOCATION)), upper(trim(:LAST_NEG_FAC_TYPE)),
				upper(trim(:PERSON_CENSUS_TRACT)), upper(trim(:ARV_6_MO_NAME)), upper(trim(:PERSON_ZIP)),
				upper(trim(:PROV_ZIP)), upper(trim(:NUMBER_TEST_2_YRS)), upper(trim(:BIRTH_COUNTRY)),
				upper(trim(:RECV_BLOOD_PROD_COUNTRY)), upper(trim(:PROV_DEPARTMENT)), upper(trim(:TSAID)),
				upper(trim(:REVTSAID)), upper(trim(:PERSON_CITY)), upper(trim(:RACE_OTHER_TEXT)), upper(trim(:PROV_CITY)),
				upper(trim(:ARV_CURRENT_TEST)), upper(trim(:PERSON_COUNTY)), upper(trim(:CURRENT_GENDER)),
				upper(trim(:ETHNICITY)), upper(trim(:FIRST_POS_ANON)), upper(trim(:RESP_HIV_TEST_2YRS)), upper(trim(:FNAME)),
				upper(trim(:MNAME)), upper(trim(:LNAME)), upper(trim(:ALT_NAME)), upper(trim(:PROV_FNAME)),
				upper(trim(:PROV_MI)), upper(trim(:PROV_TITLE)), upper(trim(:CONTACT_LNAME)), upper(trim(:CONTACT_FNAME)),
				upper(trim(:CONTACT_MNAME)), upper(trim(:WARNING)), upper(trim(:PERSON_PHONE)), upper(trim(:PROV_PHONE)),
				upper(trim(:CONTACT_PHONE)), upper(trim(:EHARS_NYC_PRF_UID)), upper(trim(:PERSON_STATE)), upper(trim(:SWM)),
				upper(trim(:SWF)), upper(trim(:IDU)), upper(trim(:SEX_PARTNER_IDU)), upper(trim(:SEX_PARTNER_HIV)),
				upper(trim(:RECV_BLOOD_PROD)), upper(trim(:PERINATAL)), upper(trim(:RISK_OTHER)), upper(trim(:RISK_UNKNOWN)),
				upper(trim(:SEX_PARTNER_MSM)), upper(trim(:SEX_PARTNER_HEMOPHILIAC)), upper(trim(:HEALTHCARE_WORKER)),
				upper(trim(:PS_REQUESTED)), upper(trim(:PROV_STATE)), upper(trim(:NOTSEND)), upper(trim(:ARV_6_MO)),
				upper(trim(:EVER_TEST_NEG)), upper(trim(:MED_RECORD_NUMBER)), upper(trim(:PT_INTERVIEW_DATE)),
				upper(trim(:FIRST_POS_HIV_DATE)), upper(trim(:ARV_6_MO_START_DATE_TEXT)),
				upper(trim(:ARV_6_MO_END_DATE_TEXT)), upper(trim(:FIRST_HIV_TEST_EVER_DATE_TEXT)),
				upper(trim(:LAST_NEG_DATE_TEXT)), upper(trim(:SSN)), upper(trim(:PERSON_LONGITUDE)), upper(trim(:RACE_CD)),
				upper(trim(:PRO_EMAIL)), upper(trim(:CONTACT_TITLE)), upper(trim(:MDPRINTNAME)),
				upper(trim(:FIRST_POS_FAC_NAME)), upper(trim(:LAST_NEG_FAC_NAME)), upper(trim(:CITYNO)),
				upper(trim(:DOD_TEXT)), upper(trim(:PERSON_LATITUDE)), upper(trim(:DIN)),
				upper(trim(:RECV_BLOOD_PROD_DATE_TEXT)), upper(trim(:INITIAL_HIV_DIAG_DATE_TEXT)),
				upper(trim(:INFORMED_HIV_DATE_TEXT)), upper(trim(:PREV_HIV_DIAG_DATE_TEXT)),
				upper(trim(:INIT_AIDS_DIAG_DATE_TEXT)), upper(trim(:PREV_AIDS_DIAG_DATE_TEXT)), upper(trim(:EHARS)),
				upper(trim(:FIRST_POS_FAC_STATE)), upper(trim(:LAST_NEG_FAC_STATE)), upper(trim(:MATCHED_STATENO)),
				upper(trim(:BIRTH_SEX)), upper(trim(:RACE_WHITE)), upper(trim(:RACE_BLACK)), upper(trim(:RACE_ASIAN)),
				upper(trim(:RACE_INDIAN)), upper(trim(:RACE_PACIFIC)), upper(trim(:RACE_UNKNOWN)), upper(trim(:RACE_OTHER)),
				upper(trim(:OLDFORM)), upper(trim(:RSON_FIRST_POS_EXP)), upper(trim(:RSON_FIRST_POS_ROUTN)),
				upper(trim(:RSON_FIRST_POS_CHKNEG)), upper(trim(:RSON_FIRST_POS_REQD)), upper(trim(:RSON_FIRST_POS_OTHER)),
				:DOB, function('fn_to_date' as Date, trim(:ADMISSION_DATE), 'mm/dd/yyyy'), :COMPLETED_DATE, :UPDATE_DATE, :PRINTED_DATE,
				function('fn_to_date' as Date, trim(:SIG_DATE), 'mm/dd/yyyy'), function('fn_to_date' as Date, trim(:EHARS_DATE), 'mm/dd/yyyy'), :RECEIVED_DATE,
				:REVIEW_DATE, :CREATE_DATE, :EHARS_IMPORT_DATE, :ENTER_DATE, upper(trim(:REVIEW_UID)), upper(trim(:MATCH_CD)),
				upper(trim(:CREATE_UID)), upper(trim(:EHARS_STATUS_CD)), cast(trim(:DOCUMENT_ID) as Integer), trim(:MATCH_WEIGHT),
				cast(trim(:PRF_SRC_ID) as Long), trim(:INITIAL_HIV_DIAG), trim(:INFORMED_HIV_POS), trim(:PREVIOUS_HIV_DIAG),
				trim(:INITIAL_AIDS_DIAG), trim(:PREVIOUS_AIDS_DIAG), trim(:CURRENT_PROCESS_STATUS), trim(:MATCH_PASS),
				trim(:AGE_CATEGORY), trim(:RECNUM), trim(:AGE), trim(:QS_PARENT_DOC_ID), :MATCHED_PROCESS_DATE,
				upper(trim(:ALT_MNAME)), upper(trim(:ALT_LNAME)), upper(trim(:EDC)), upper(trim(:ADDITIONAL_GENDER_IDENTITY)),
				upper(trim(:PERSON_OTHER_IDENTIFIER)), upper(trim(:PRIMARY_LANGUAGE)), upper(trim(:PRIMARY_LANGUAGE_OTHER)),
				upper(trim(:REFERRED_HIV_PROVIDER)), upper(trim(:REFERRED_PROVIDER_NAME)),
				upper(trim(:REFFERED_PROVIDER_TELEPHONE)), upper(trim(:PT_ARV_EVER_TAKEN)), upper(trim(:ARV_PREVEN_PREP)),
				upper(trim(:ARV_PREVEN_PREP_START_DT)), upper(trim(:ARV_PREVEN_PREP_END_DT)),
				upper(trim(:ARV_PREVENPREP_MED_NAMES)), upper(trim(:POST_EXP_ARV_PEP)),
				upper(trim(:POST_EXP_ARV_PEP_START_DT)), upper(trim(:POST_EXP_ARV_PEP_END_DT)),
				upper(trim(:POST_EXP_ARV_PEP_MED_NAMES)), upper(trim(:ARV_TREATMENT)), upper(trim(:PT_ARV_START_DATE_TEXT)),
				upper(trim(:PT_ARV_END_DATE_TEXT)), upper(trim(:PT_ARV_NAME)), upper(trim(:RSON_FIRST_POS_PRTNR)),
				upper(trim(:FACILITY_TYPE)), upper(trim(:FACILITY_TYPE_OTHER_TEXT)), upper(trim(:PROVIDER_NYS_LIC_NO)),
				upper(trim(:PROVIDER_NPI_NO)), upper(trim(:ACUTE_INFECTION)), upper(trim(:PREGNANT)),
				upper(trim(:EVER_HAD_PREV_POS_TEST)), upper(trim(:EHARS_DOCUMENT_SOURCE_CD)), :FLUX_MATCH_CD,
				:FLUX_POT_STATENO, :FLUX_MATCHED_STATENO, :FLUX_QS_PARENT_DOC_ID, :FLUX_MATCHED_PROCESS_DATE,
				upper(trim(:FIELD_PROC_DATE)), upper(trim(:APARTMENT)), upper(trim(:SEX_ORN)), upper(trim(:SEX_ORN_ADD_ID)),
				upper(trim(:NUM_NEG_HIV_TEST)))
			""";

	/** The parameters used as text, i.e. inside trim(...). */
	private static final Set<String> TEXT_PARAMETERS = Pattern.compile("trim\\(:(\\w+)\\)").matcher(INSERT_STD_DOCUMENT)
			.results().map(match -> match.group(1)).collect(Collectors.toUnmodifiableSet());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public int insertStdDocument(Map<Object, Object> stdDocData) {
		MutationQuery insert = entityManager.unwrap(Session.class).createMutationQuery(INSERT_STD_DOCUMENT);
		for (String column : insert.getParameterMetadata().getNamedParameterNames()) {
			bind(insert, column, stdDocData.get(column));
		}
		return insert.executeUpdate();
	}

	/**
	 * Binds a value with its own Java type, as MyBatis did: the map holds what was read from the raw
	 * table (BigDecimal, Timestamp, String ...), e.g. a Timestamp keeps its time of day. A number
	 * given for a text parameter (inside upper/trim) is passed as the text Oracle made of it.
	 */
	@SuppressWarnings("unchecked")
	private static <T> void bind(MutationQuery query, String name, T value) {
		if (value == null) {
			query.setParameter(name, null);
			return;
		}
		boolean textParameter = TEXT_PARAMETERS.contains(name);
		if (textParameter && value instanceof Number number) {
			query.setParameter(name, oracleText(number));
		} else if (textParameter && value instanceof java.util.Date) {
			// Oracle would have formatted it with the session's NLS date format; not reproduced here
			throw new IllegalArgumentException("STD_NYCPRF_DOC." + name + " expects text but got a date: " + value);
		} else {
			query.setParameter(name, value, (Class<T>) value.getClass());
		}
	}

	/** A number as Oracle's implicit TO_CHAR writes it: no trailing zeros, no leading zero ("0.5" is ".5"). */
	static String oracleText(Number number) {
		String text = new BigDecimal(number.toString()).stripTrailingZeros().toPlainString();
		if (text.startsWith("0.")) {
			return text.substring(1);
		}
		return text.startsWith("-0.") ? "-" + text.substring(2) : text;
	}
}
