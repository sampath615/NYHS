package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A birth certificate record received for matching (table RAW_BIRTH_MATCH_DOC): the columns the birth import reads
 * for the mother and the infant (BirthDocumentDAO.getMomData / getInfantData).
 * <p>
 * Read-only. <b>BIRTH_MATCH_PK as the key is assumed</b> (one row per birth record); it is read and compared as text,
 * as the former SQL bound it. <b>Assumed column types</b> (the table definition is not in this repository): every
 * column is text except CURRENT_PROCESS_STATUS (a number), so the values come as JDBC returned them to the former
 * map results. RECEIVED_DATE is taken as text in MM/DD/YYYY: the former mother insert parsed it with
 * TO_DATE(..., 'MM/DD/YYYY').
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_BIRTH_MATCH_DOC")
public class RawBirthMatchDoc {

	@Id
	@Column(name = "BIRTH_MATCH_PK")
	private String birthMatchPk;

	@Column(name = "MOTHER_RES_COUNTRY")
	private String motherResCountry;

	@Column(name = "MOTHER_FNAME")
	private String motherFname;

	@Column(name = "MOTHER_MNAME")
	private String motherMname;

	@Column(name = "MOTHER_CURRENT_LNAME")
	private String motherCurrentLname;

	@Column(name = "MOTHER_MAIDEN_LNAME")
	private String motherMaidenLname;

	@Column(name = "MOTHER_MED_REC")
	private String motherMedRec;

	@Column(name = "MOTHER_BIRTH_STATE")
	private String motherBirthState;

	@Column(name = "MOTHER_BIRTH_COUNTRY_CD")
	private String motherBirthCountryCd;

	@Column(name = "MOTHER_WHITE")
	private String motherWhite;

	@Column(name = "MOTHER_BLACK")
	private String motherBlack;

	@Column(name = "MOTHER_ASIAN_INDIAN")
	private String motherAsianIndian;

	@Column(name = "MOTHER_CHINESE")
	private String motherChinese;

	@Column(name = "MOTHER_FILIPINO")
	private String motherFilipino;

	@Column(name = "MOTHER_JAPANESE")
	private String motherJapanese;

	@Column(name = "MOTHER_KOREAN")
	private String motherKorean;

	@Column(name = "MOTHER_VIETNAMESE")
	private String motherVietnamese;

	@Column(name = "MOTHER_ASIAN_OTHER")
	private String motherAsianOther;

	@Column(name = "MOTHER_HAWAIIAN")
	private String motherHawaiian;

	@Column(name = "MOTHER_GUAMANIAN")
	private String motherGuamanian;

	@Column(name = "MOTHER_SAMOAN")
	private String motherSamoan;

	@Column(name = "MOTHER_PACIFIC_ISLANDER_OTHER")
	private String motherPacificIslanderOther;

	@Column(name = "MOTHER_AMER_INDIAN_ALASKA_NATIVE")
	private String motherAmerIndianAlaskaNative;

	@Column(name = "MOTHER_RACE_OTHER")
	private String motherRaceOther;

	@Column(name = "MOTHER_NOT_HISPANIC")
	private String motherNotHispanic;

	@Column(name = "MOTHER_HISP_ORIGIN_MEXICAN")
	private String motherHispOriginMexican;

	@Column(name = "MOTHER_HISP_ORIGIN_PUERTO_RICO")
	private String motherHispOriginPuertoRico;

	@Column(name = "MOTHER_HISP_ORIGIN_CUBAN")
	private String motherHispOriginCuban;

	@Column(name = "MOTHER_HISP_ORIGIN_OTHER")
	private String motherHispOriginOther;

	@Column(name = "MOTHER_DOB")
	private String motherDob;

	@Column(name = "MOTHER_EDU_LEVEL")
	private String motherEduLevel;

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

	@Column(name = "MOTHER_SSN")
	private String motherSsn;

	@Column(name = "MOTHER_AGE")
	private String motherAge;

	@Column(name = "START_PRENATAL_CARE_TRIMESTER")
	private String startPrenatalCareTrimester;

	@Column(name = "MOTHER_PHONE")
	private String motherPhone;

	@Column(name = "PRENATAL_CARE_RECEIVED")
	private String prenatalCareReceived;

	@Column(name = "BIRTH_FILE")
	private String birthFile;

	@Column(name = "POB_INST")
	private String pobInst;

	@Column(name = "POB_INST_TYPE")
	private String pobInstType;

	@Column(name = "BIRTH_FAC_PFI")
	private String birthFacPfi;

	@Column(name = "BIRTH_ADDR_STREET")
	private String birthAddrStreet;

	@Column(name = "BIRTH_ADDR_ZIP")
	private String birthAddrZip;

	@Column(name = "BIRTH_CERT_NO")
	private String birthCertNo;

	@Column(name = "DEATH_CERT_NO")
	private String deathCertNo;

	@Column(name = "DEATH_DT")
	private String deathDt;

	@Column(name = "DEATH_STATE")
	private String deathState;

	@Column(name = "INFANT_FNAME")
	private String infantFname;

	@Column(name = "INFANT_MNAME")
	private String infantMname;

	@Column(name = "INFANT_LNAME")
	private String infantLname;

	@Column(name = "INFANT_SUFFIX")
	private String infantSuffix;

	@Column(name = "INFANT_MED_REC")
	private String infantMedRec;

	@Column(name = "INFANT_DOB")
	private String infantDob;

	@Column(name = "INFANT_DOB_TIME")
	private String infantDobTime;

	@Column(name = "INFANT_SEX")
	private String infantSex;

	@Column(name = "INFANT_PLURALITY")
	private String infantPlurality;

	@Column(name = "INFANT_BIRTH_WEIGHT")
	private String infantBirthWeight;

	@Column(name = "INFANT_TRANSFERRED")
	private String infantTransferred;

	@Column(name = "HOSP_REC_TRANSFERRED_INFANT")
	private String hospRecTransferredInfant;

	@Column(name = "INFANT_ALIVE_REP_TIME")
	private String infantAliveRepTime;

	@Column(name = "SYPHILIS_TEST_GIVEN")
	private String syphilisTestGiven;

	@Column(name = "HOW_INFANT_FED")
	private String howInfantFed;

	@Column(name = "ABNORMAL_CONDITION_NEWBORN_NONE")
	private String abnormalConditionNewbornNone;

	@Column(name = "CONGENITAL_MALFORMATIONS_NONE")
	private String congenitalMalformationsNone;

	@Column(name = "CONGENITAL_MALFORMATIONS_UNK")
	private String congenitalMalformationsUnk;

	@Column(name = "ANENCEPHALY")
	private String anencephaly;

	@Column(name = "SPINA_BIFIDA")
	private String spinaBifida;

	@Column(name = "CONGENITAL_HEART_DISEASE")
	private String congenitalHeartDisease;

	@Column(name = "HERNIA")
	private String hernia;

	@Column(name = "OMPHALOCELE")
	private String omphalocele;

	@Column(name = "GASTROSCHISIS")
	private String gastroschisis;

	@Column(name = "LIMB_REDUCT_DEFECT")
	private String limbReductDefect;

	@Column(name = "CLEFT_LIP_PALATE")
	private String cleftLipPalate;

	@Column(name = "CLEFT_PALATE")
	private String cleftPalate;

	@Column(name = "DOWN_SYNDROME")
	private String downSyndrome;

	@Column(name = "DOWN_SYNDROME_KARYOTYPE_CONFIRM")
	private String downSyndromeKaryotypeConfirm;

	@Column(name = "DOWN_SYNDROME_KARYOTYPE_PENDING")
	private String downSyndromeKaryotypePending;

	@Column(name = "CHROMOSOMAL_DISORDER")
	private String chromosomalDisorder;

	@Column(name = "CHROMOSOMAL_KARYOTYPE_CONFIRM")
	private String chromosomalKaryotypeConfirm;

	@Column(name = "CHROMOSOMAL_KARYOTYPE_PENDING")
	private String chromosomalKaryotypePending;

	@Column(name = "HYPOSPADIAS")
	private String hypospadias;

	@Column(name = "ANENCEPHALY_DIAG_PRENATAL")
	private String anencephalyDiagPrenatal;

	@Column(name = "ANENCEPHALY_DIAG_ULTRASOUND")
	private String anencephalyDiagUltrasound;

	@Column(name = "ANENCEPHALY_DIAG_TRIPLE_SCREEN")
	private String anencephalyDiagTripleScreen;

	@Column(name = "ANENCEPHALY_DIAG_AMNIOCENTESIS")
	private String anencephalyDiagAmniocentesis;

	@Column(name = "ANENCEPHALY_DIAG_OTHER")
	private String anencephalyDiagOther;

	@Column(name = "ANENCEPHALY_DIAG_UNK")
	private String anencephalyDiagUnk;

	@Column(name = "SPINA_BIFIDA_DIAG_PRENATAL")
	private String spinaBifidaDiagPrenatal;

	@Column(name = "SPINA_BIFIDA_DIAG_ULTRASOUND")
	private String spinaBifidaDiagUltrasound;

	@Column(name = "SPINA_BIFIDA_DIAG_TRIPLE_SCREEN")
	private String spinaBifidaDiagTripleScreen;

	@Column(name = "SPINA_BIFIDA_DIAG_AMNIOCENTESIS")
	private String spinaBifidaDiagAmniocentesis;

	@Column(name = "SPINA_BIFIDA_DIAG_OTHER")
	private String spinaBifidaDiagOther;

	@Column(name = "SPINA_BIFIDA_DIAG_UNK")
	private String spinaBifidaDiagUnk;

	@Column(name = "HEART_DIS_DIAG_PRENATAL")
	private String heartDisDiagPrenatal;

	@Column(name = "HEART_DIS_DIAG_ULTRASOUND")
	private String heartDisDiagUltrasound;

	@Column(name = "HEART_DIS_DIAG_OTHER")
	private String heartDisDiagOther;

	@Column(name = "HEART_DIS_DIAG_UNK")
	private String heartDisDiagUnk;

	@Column(name = "HERNIA_DIAG_PRENATAL")
	private String herniaDiagPrenatal;

	@Column(name = "HERNIA_DIAG_ULTRASOUND")
	private String herniaDiagUltrasound;

	@Column(name = "HERNIA_DIAG_OTHER")
	private String herniaDiagOther;

	@Column(name = "HERNIA_DIAG_UNK")
	private String herniaDiagUnk;

	@Column(name = "OMPHALOCELE_DIAG_PRENATAL")
	private String omphaloceleDiagPrenatal;

	@Column(name = "OMPHALOCELE_DIAG_ULTRASOUND")
	private String omphaloceleDiagUltrasound;

	@Column(name = "OMPHALOCELE_DIAG_OTHER")
	private String omphaloceleDiagOther;

	@Column(name = "OMPHALOCELE_DIAG_UNK")
	private String omphaloceleDiagUnk;

	@Column(name = "GASTROSCHISIS_DIAG_PRENATAL")
	private String gastroschisisDiagPrenatal;

	@Column(name = "GASTROSCHISIS_DIAG_ULTRASOUND")
	private String gastroschisisDiagUltrasound;

	@Column(name = "GASTROSCHISIS_DIAG_OTHER")
	private String gastroschisisDiagOther;

	@Column(name = "GASTROSCHISIS_DIAG_UNK")
	private String gastroschisisDiagUnk;

	@Column(name = "LIMB_REDUCT_DIAG_PRENATAL")
	private String limbReductDiagPrenatal;

	@Column(name = "LIMB_REDUCT_DIAG_ULTRASOUND")
	private String limbReductDiagUltrasound;

	@Column(name = "LIMB_REDUCT_DIAG_OTHER")
	private String limbReductDiagOther;

	@Column(name = "LIMB_REDUCT_DIAG_UNK")
	private String limbReductDiagUnk;

	@Column(name = "CLEFT_LIP_PALATE_DIAG_PRENATAL")
	private String cleftLipPalateDiagPrenatal;

	@Column(name = "CLEFT_LIP_PALATE_DIAG_ULTRASOUND")
	private String cleftLipPalateDiagUltrasound;

	@Column(name = "CLEFT_LIP_PALATE_DIAG_OTHER")
	private String cleftLipPalateDiagOther;

	@Column(name = "CLEFT_LIP_PALATE_DIAG_UNK")
	private String cleftLipPalateDiagUnk;

	@Column(name = "CLEFT_PALATE_DIAG_PRENATAL")
	private String cleftPalateDiagPrenatal;

	@Column(name = "CLEFT_PALATE_DIAG_ULTRASOUND")
	private String cleftPalateDiagUltrasound;

	@Column(name = "CLEFT_PALATE_DIAG_OTHER")
	private String cleftPalateDiagOther;

	@Column(name = "CLEFT_PALATE_DIAG_UNK")
	private String cleftPalateDiagUnk;

	@Column(name = "DOWN_SYNDROME_DIAG_PRENATAL")
	private String downSyndromeDiagPrenatal;

	@Column(name = "DOWN_SYNDROME_DIAG_ULTRASOUND")
	private String downSyndromeDiagUltrasound;

	@Column(name = "DOWN_SYNDROME_DIAG_TRIPLE_SCREEN")
	private String downSyndromeDiagTripleScreen;

	@Column(name = "DOWN_SYNDROME_DIAG_CVS")
	private String downSyndromeDiagCvs;

	@Column(name = "DOWN_SYNDROME_DIAG_AMNIOCENTESIS")
	private String downSyndromeDiagAmniocentesis;

	@Column(name = "DOWN_SYNDROME_DIAG_OTHER")
	private String downSyndromeDiagOther;

	@Column(name = "DOWN_SYNDROME_DIAG_UNK")
	private String downSyndromeDiagUnk;

	@Column(name = "CHROMOSOMAL_DIAG_PRENATAL")
	private String chromosomalDiagPrenatal;

	@Column(name = "CHROMOSOMAL_DIAG_ULTRASOUND")
	private String chromosomalDiagUltrasound;

	@Column(name = "CHROMOSOMAL_DIAG_TRIPLE_SCREEN")
	private String chromosomalDiagTripleScreen;

	@Column(name = "CHROMOSOMAL_DIAG_CVS")
	private String chromosomalDiagCvs;

	@Column(name = "CHROMOSOMAL_DIAG_AMNIOCENTESIS")
	private String chromosomalDiagAmniocentesis;

	@Column(name = "CHROMOSOMAL_DIAG_OTHER")
	private String chromosomalDiagOther;

	@Column(name = "CHROMOSOMAL_DIAG_UNK")
	private String chromosomalDiagUnk;

	@Column(name = "HYPOSPADIAS_DIAG_PRENATAL")
	private String hypospadiasDiagPrenatal;

	@Column(name = "HYPOSPADIAS_DIAG_ULTRASOUND")
	private String hypospadiasDiagUltrasound;

	@Column(name = "HYPOSPADIAS_DIAG_OTHER")
	private String hypospadiasDiagOther;

	@Column(name = "HYPOSPADIAS_DIAG_UNK")
	private String hypospadiasDiagUnk;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private BigDecimal currentProcessStatus;

	@Column(name = "RECEIVED_DATE")
	private String receivedDate;
}
