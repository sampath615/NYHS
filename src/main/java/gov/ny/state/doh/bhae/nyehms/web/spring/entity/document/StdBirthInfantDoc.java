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
 * The the infant of a birth record, standardized (table STD_BIRTH_INFANT_DOC), inserted by the birth import (BirthDocumentDAO); its person address is changed by bulk
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
@Table(name = "STD_BIRTH_INFANT_DOC")
public class StdBirthInfantDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "BIRTH_MATCH_PK")
	private String birthMatchPk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "POB_INST")
	private String pobInst;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "POB_INST_TYPE")
	private String pobInstType;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "BIRTH_FAC_PFI")
	private String birthFacPfi;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "BIRTH_ADDR_STREET")
	private String birthAddrStreet;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "BIRTH_ADDR_ZIP")
	private String birthAddrZip;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "BIRTH_CERT_NO")
	private String birthCertNo;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DEATH_CERT_NO")
	private String deathCertNo;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(?, 'MM/DD/YYYY'), 'MM/DD/YYYY')")
	@Column(name = "DEATH_DT")
	private String deathDt;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DEATH_STATE")
	private String deathState;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "FNAME")
	private String fname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MNAME")
	private String mname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LNAME")
	private String lname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SUFFIX")
	private String suffix;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(?, 'MM/DD/YYYY'), 'MM/DD/YYYY')")
	@Column(name = "DOB")
	private String dob;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOB_TIME")
	private String dobTime;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SEX")
	private String sex;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_ZIP")
	private String personZip;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_CITY")
	private String personCity;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_STATE")
	private String personState;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_COUNTRY")
	private String personCountry;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_CENSUS_TRACT")
	private String personCensusTract;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_LONGITUDE")
	private String personLongitude;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_LATITUDE")
	private String personLatitude;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PLURALITY")
	private String plurality;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "BIRTH_WEIGHT")
	private String birthWeight;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MOTHER_FNAME")
	private String motherFname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MOTHER_MNAME")
	private String motherMname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MOTHER_CURRENT_LNAME")
	private String motherCurrentLname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MOTHER_MAIDEN_LNAME")
	private String motherMaidenLname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "INFANT_TRANSFERRED")
	private String infantTransferred;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HOSP_REC_TRANSFERRED_INFANT")
	private String hospRecTransferredInfant;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "INFANT_ALIVE_REP_TIME")
	private String infantAliveRepTime;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SYPHILIS_TEST_GIVEN")
	private String syphilisTestGiven;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HOW_INFANT_FED")
	private String howInfantFed;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ABNORMAL_CONDITION_NEWBORN_NONE")
	private String abnormalConditionNewbornNone;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CONGENITAL_MALFORMATIONS_NONE")
	private String congenitalMalformationsNone;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CONGENITAL_MALFORMATIONS_UNK")
	private String congenitalMalformationsUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ANENCEPHALY")
	private String anencephaly;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SPINA_BIFIDA")
	private String spinaBifida;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CONGENITAL_HEART_DISEASE")
	private String congenitalHeartDisease;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HERNIA")
	private String hernia;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "OMPHALOCELE")
	private String omphalocele;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "GASTROSCHISIS")
	private String gastroschisis;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LIMB_REDUCT_DEFECT")
	private String limbReductDefect;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_LIP_PALATE")
	private String cleftLipPalate;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_PALATE")
	private String cleftPalate;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME")
	private String downSyndrome;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME_KARYOTYPE_CONFIRM")
	private String downSyndromeKaryotypeConfirm;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME_KARYOTYPE_PENDING")
	private String downSyndromeKaryotypePending;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_DISORDER")
	private String chromosomalDisorder;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_KARYOTYPE_CONFIRM")
	private String chromosomalKaryotypeConfirm;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_KARYOTYPE_PENDING")
	private String chromosomalKaryotypePending;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HYPOSPADIAS")
	private String hypospadias;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ANENCEPHALY_DIAG_PRENATAL")
	private String anencephalyDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ANENCEPHALY_DIAG_ULTRASOUND")
	private String anencephalyDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ANENCEPHALY_DIAG_TRIPLE_SCREEN")
	private String anencephalyDiagTripleScreen;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ANENCEPHALY_DIAG_AMNIOCENTESIS")
	private String anencephalyDiagAmniocentesis;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ANENCEPHALY_DIAG_OTHER")
	private String anencephalyDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ANENCEPHALY_DIAG_UNK")
	private String anencephalyDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SPINA_BIFIDA_DIAG_PRENATAL")
	private String spinaBifidaDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SPINA_BIFIDA_DIAG_ULTRASOUND")
	private String spinaBifidaDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SPINA_BIFIDA_DIAG_TRIPLE_SCREEN")
	private String spinaBifidaDiagTripleScreen;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SPINA_BIFIDA_DIAG_AMNIOCENTESIS")
	private String spinaBifidaDiagAmniocentesis;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SPINA_BIFIDA_DIAG_OTHER")
	private String spinaBifidaDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SPINA_BIFIDA_DIAG_UNK")
	private String spinaBifidaDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HEART_DIS_DIAG_PRENATAL")
	private String heartDisDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HEART_DIS_DIAG_ULTRASOUND")
	private String heartDisDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HEART_DIS_DIAG_OTHER")
	private String heartDisDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HEART_DIS_DIAG_UNK")
	private String heartDisDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HERNIA_DIAG_PRENATAL")
	private String herniaDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HERNIA_DIAG_ULTRASOUND")
	private String herniaDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HERNIA_DIAG_OTHER")
	private String herniaDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HERNIA_DIAG_UNK")
	private String herniaDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "OMPHALOCELE_DIAG_PRENATAL")
	private String omphaloceleDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "OMPHALOCELE_DIAG_ULTRASOUND")
	private String omphaloceleDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "OMPHALOCELE_DIAG_OTHER")
	private String omphaloceleDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "OMPHALOCELE_DIAG_UNK")
	private String omphaloceleDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "GASTROSCHISIS_DIAG_PRENATAL")
	private String gastroschisisDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "GASTROSCHISIS_DIAG_ULTRASOUND")
	private String gastroschisisDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "GASTROSCHISIS_DIAG_OTHER")
	private String gastroschisisDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "GASTROSCHISIS_DIAG_UNK")
	private String gastroschisisDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LIMB_REDUCT_DIAG_PRENATAL")
	private String limbReductDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LIMB_REDUCT_DIAG_ULTRASOUND")
	private String limbReductDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LIMB_REDUCT_DIAG_OTHER")
	private String limbReductDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LIMB_REDUCT_DIAG_UNK")
	private String limbReductDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_LIP_PALATE_DIAG_PRENATAL")
	private String cleftLipPalateDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_LIP_PALATE_DIAG_ULTRASOUND")
	private String cleftLipPalateDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_LIP_PALATE_DIAG_OTHER")
	private String cleftLipPalateDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_LIP_PALATE_DIAG_UNK")
	private String cleftLipPalateDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_PALATE_DIAG_PRENATAL")
	private String cleftPalateDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_PALATE_DIAG_ULTRASOUND")
	private String cleftPalateDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_PALATE_DIAG_OTHER")
	private String cleftPalateDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CLEFT_PALATE_DIAG_UNK")
	private String cleftPalateDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME_DIAG_PRENATAL")
	private String downSyndromeDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME_DIAG_ULTRASOUND")
	private String downSyndromeDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME_DIAG_TRIPLE_SCREEN")
	private String downSyndromeDiagTripleScreen;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME_DIAG_CVS")
	private String downSyndromeDiagCvs;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME_DIAG_AMNIOCENTESIS")
	private String downSyndromeDiagAmniocentesis;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME_DIAG_OTHER")
	private String downSyndromeDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DOWN_SYNDROME_DIAG_UNK")
	private String downSyndromeDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_DIAG_PRENATAL")
	private String chromosomalDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_DIAG_ULTRASOUND")
	private String chromosomalDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_DIAG_TRIPLE_SCREEN")
	private String chromosomalDiagTripleScreen;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_DIAG_CVS")
	private String chromosomalDiagCvs;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_DIAG_AMNIOCENTESIS")
	private String chromosomalDiagAmniocentesis;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_DIAG_OTHER")
	private String chromosomalDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CHROMOSOMAL_DIAG_UNK")
	private String chromosomalDiagUnk;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HYPOSPADIAS_DIAG_PRENATAL")
	private String hypospadiasDiagPrenatal;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HYPOSPADIAS_DIAG_ULTRASOUND")
	private String hypospadiasDiagUltrasound;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HYPOSPADIAS_DIAG_OTHER")
	private String hypospadiasDiagOther;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "HYPOSPADIAS_DIAG_UNK")
	private String hypospadiasDiagUnk;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "BIRTH_FILE")
	private String birthFile;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "RECEIVED_DATE")
	private String receivedDate;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;
}
