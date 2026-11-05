package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The the mother of a birth record, standardized (table STD_BIRTH_MOTHER_DOC), inserted by the birth import (BirthDocumentDAO); its person address is changed by bulk
 * updates. The columns the former insert wrote.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (given by the application). The conversions the former insert left to the
 * database stay there (@ColumnTransformer: UPPER/TRIM, FN_TO_DATE/TO_CHAR, TO_DATE); MATCHED_PROCESS_DATE is the
 * database's SYSDATE (left null on insert). BIRTH_MATCH_PK is written as text, as it was bound before.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_BIRTH_MOTHER_DOC")
public class StdBirthMotherDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "BIRTH_MATCH_PK")
	private String birthMatchPk;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_COUNTRY")
	private String personCountry;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "CURRENT_LNAME")
	private String currentLname;

	@Column(name = "MAIDEN_LNAME")
	private String maidenLname;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "BIRTH_STATE")
	private String birthState;

	@Column(name = "BIRTH_COUNTRY_CD")
	private String birthCountryCd;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(?, 'MM/DD/YYYY'), 'MM/DD/YYYY')")
	@Column(name = "DOB")
	private String dob;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "AGE")
	private String age;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "EDU_LEVEL")
	private String eduLevel;

	@Column(name = "FIRST_PRENATAL_VISIT_DT")
	private String firstPrenatalVisitDt;

	@Column(name = "LAST_PRENATAL_VISIT_DT")
	private String lastPrenatalVisitDt;

	@Column(name = "NO_PRENATAL_VISITS")
	private String noPrenatalVisits;

	@Column(name = "GESTATION_WKS")
	private String gestationWks;

	@Column(name = "NO_PREV_LIVE_BIRTHS_LIVING")
	private String noPrevLiveBirthsLiving;

	@Column(name = "NO_PREV_LIVE_BIRTHS_DEAD")
	private String noPrevLiveBirthsDead;

	@Column(name = "NO_PREV_PREG_TOTAL")
	private String noPrevPregTotal;

	@Column(name = "INFECTIONS_NONE")
	private String infectionsNone;

	@Column(name = "GONORRHEA")
	private String gonorrhea;

	@Column(name = "SYPHILIS")
	private String syphilis;

	@Column(name = "GENITAL_HERPES")
	private String genitalHerpes;

	@Column(name = "CHLAMYDIA")
	private String chlamydia;

	@Column(name = "HEP_B")
	private String hepB;

	@Column(name = "HEP_C")
	private String hepC;

	@Column(name = "TB")
	private String tb;

	@Column(name = "RUBELLA")
	private String rubella;

	@Column(name = "BACTERIAL_VAGINOSIS")
	private String bacterialVaginosis;

	@Column(name = "INFECTIONS_UNK")
	private String infectionsUnk;

	@Column(name = "START_PRENATAL_CARE_TRIMESTER")
	private String startPrenatalCareTrimester;

	@Column(name = "PRENATAL_CARE_RECEIVED")
	private String prenatalCareReceived;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "BIRTH_FILE")
	private String birthFile;

	@ColumnTransformer(write = "TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "RECEIVED_DATE")
	private String receivedDate;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "PERSON_LATITUDE")
	private String personLatitude;

	@Column(name = "PERSON_LONGITUDE")
	private String personLongitude;

	@Column(name = "PERSON_CENSUS_TRACT")
	private String personCensusTract;
}
