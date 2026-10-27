package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Persistable;

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
 * An NYC death certificate, as received (table RAW_NYC_DEATH_DOC); only the columns the application reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_NYC_DEATH_DOC")
public class RawNycDeathDoc implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "RES_STREET_NO")
	private String resStreetNo;

	@Column(name = "RES_STREET_DIRECT")
	private String resStreetDirect;

	@Column(name = "RES_STREET")
	private String resStreet;

	@Column(name = "RES_STREET_TYPE")
	private String resStreetType;

	@Column(name = "RES_APT_NO")
	private String resAptNo;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private String currentProcessStatus;

	@Column(name = "SPEC_INT_CAUSE")
	private String specIntCause;

	@Column(name = "UNITS_AGE")
	private String unitsAge;

	@Column(name = "MIDDLE")
	private String middle;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;

	@Column(name = "PREGNANT")
	private String pregnant;

	@Column(name = "HISP_ORIGIN_NONE")
	private String hispOriginNone;

	@Column(name = "HISP_ORIGIN_MEXICAN")
	private String hispOriginMexican;

	@Column(name = "HISP_ORIGIN_PUERTO")
	private String hispOriginPuerto;

	@Column(name = "HISP_ORIGIN_CUBAN")
	private String hispOriginCuban;

	@Column(name = "HISP_ORIGIN_OTHER")
	private String hispOriginOther;

	@Column(name = "RACE_W")
	private String raceW;

	@Column(name = "RACE_B")
	private String raceB;

	@Column(name = "RACE_A")
	private String raceA;

	@Column(name = "RACE_C")
	private String raceC;

	@Column(name = "RACE_F")
	private String raceF;

	@Column(name = "RACE_J")
	private String raceJ;

	@Column(name = "RACE_K")
	private String raceK;

	@Column(name = "RACE_V")
	private String raceV;

	@Column(name = "RACE_H")
	private String raceH;

	@Column(name = "RACE_G")
	private String raceG;

	@Column(name = "RACE_S")
	private String raceS;

	@Column(name = "RACE_I")
	private String raceI;

	@Column(name = "RACE_A_OTH")
	private String raceAOth;

	@Column(name = "RACE_P_OTH")
	private String racePOth;

	@Column(name = "RACE_OTH")
	private String raceOth;

	@Column(name = "MCRF")
	private String mcrf;

	@Column(name = "HSA_OCCURRENCE")
	private String hsaOccurrence;

	@Column(name = "HSA_RESIDENCE")
	private String hsaResidence;

	@Column(name = "NCHS_BYP_ED_SEX")
	private String nchsBypEdSex;

	@Column(name = "NCHS_BYP_ED_AGE")
	private String nchsBypEdAge;

	@Column(name = "VETERAN")
	private String veteran;

	@Column(name = "NCHS_BYP_ED_MARITAL")
	private String nchsBypEdMarital;

	@Column(name = "POD_TYPE_CD")
	private String podTypeCd;

	@Column(name = "RACE_MISSING_RESPONSE")
	private String raceMissingResponse;

	@Column(name = "NCHS_BYP_ED_EDUC")
	private String nchsBypEdEduc;

	@Column(name = "MANNER_OF_DEATH")
	private String mannerOfDeath;

	@Column(name = "TOBACCO_CONTRIB")
	private String tobaccoContrib;

	@Column(name = "AUTOPSY")
	private String autopsy;

	@Column(name = "FINDINGS_USED")
	private String findingsUsed;

	@Column(name = "CASE_REFERRED")
	private String caseReferred;

	@Column(name = "INJURY_AT_WORK")
	private String injuryAtWork;

	@Column(name = "INJURY_PLACE_TRANSAX")
	private String injuryPlaceTransax;

	@Column(name = "INJURY_TIME_AMPM")
	private String injuryTimeAmpm;

	@Column(name = "HOSPITAL_LAST_2_MOS")
	private String hospitalLast2Mos;

	@Column(name = "SPEC_INT_OCCUP")
	private String specIntOccup;

	@Column(name = "CERTIFIER_ROLE")
	private String certifierRole;

	@Column(name = "DISP_TYPE_CD")
	private String dispTypeCd;

	@Column(name = "TRANSFER_FAC")
	private String transferFac;

	@Column(name = "QUERY_CODES_QR")
	private String queryCodesQr;

	@Column(name = "EDP_STATUS_CDE")
	private String edpStatusCde;

	@Column(name = "EDP_USE")
	private String edpUse;

	@Column(name = "ACT_CD_TRANSAX")
	private String actCdTransax;

	@Column(name = "HISP_ORIGIN_IND")
	private String hispOriginInd;

	@Column(name = "RACE")
	private String race;

	@Column(name = "FIPS_ZIP_IND")
	private String fipsZipInd;

	@Column(name = "MEDRECNO")
	private String medrecno;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "DEATH_ID")
	private String deathId;

	@Column(name = "DEATH_UNIQUE_ID")
	private String deathUniqueId;

	@Column(name = "FIRST")
	private String first;

	@Column(name = "RACE_TEXT")
	private String raceText;

	@Column(name = "DEATH_CNTY")
	private String deathCnty;

	@Column(name = "RES_CNTY")
	private String resCnty;

	@Column(name = "RES_CITY")
	private String resCity;

	@Column(name = "FIPS_ST")
	private String fipsSt;

	@Column(name = "BIRTH_STATE")
	private String birthState;

	@Column(name = "RES_COUNTRY")
	private String resCountry;

	@Column(name = "DEATH_CITY")
	private String deathCity;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "EDUC")
	private String educ;

	@Column(name = "INJURY_TYPE")
	private String injuryType;

	@Column(name = "INJURY_DAY")
	private String injuryDay;

	@Column(name = "INJURY_MO")
	private String injuryMo;

	@Column(name = "ADMIT_DAY")
	private String admitDay;

	@Column(name = "ADMIT_MO")
	private String admitMo;

	@Column(name = "QUERY_CODES_QS")
	private String queryCodesQs;

	@Column(name = "MBD_BIRTH_YEAR")
	private String mbdBirthYear;

	@Column(name = "MBD_BIRTH_STATE")
	private String mbdBirthState;

	@Column(name = "RECEIPT_MM")
	private String receiptMm;

	@Column(name = "LAST")
	private String last;

	@Column(name = "FATHER_LNAME")
	private String fatherLname;

	@Column(name = "MOTHER_MAIDEN")
	private String motherMaiden;

	@Column(name = "HISP_ORIGIN_LITERAL")
	private String hispOriginLiteral;

	@Column(name = "AGE_DEATH")
	private String ageDeath;

	@Column(name = "SOUNDEX")
	private String soundex;

	@Column(name = "FIPS_CNTY")
	private String fipsCnty;

	@Column(name = "NCHS_RES")
	private String nchsRes;

	@Column(name = "NYC_COMM_DIST_CDS")
	private String nycCommDistCds;

	@Column(name = "RACE_16C")
	private String race16c;

	@Column(name = "RACE_17C")
	private String race17c;

	@Column(name = "RACE_18C")
	private String race18c;

	@Column(name = "RACE_19C")
	private String race19c;

	@Column(name = "RACE_20C")
	private String race20c;

	@Column(name = "RACE_21C")
	private String race21c;

	@Column(name = "RACE_22C")
	private String race22c;

	@Column(name = "RACE_23C")
	private String race23c;

	@Column(name = "RACE_1E")
	private String race1e;

	@Column(name = "RACE_2E")
	private String race2e;

	@Column(name = "RACE_3E")
	private String race3e;

	@Column(name = "RACE_4E")
	private String race4e;

	@Column(name = "RACE_5E")
	private String race5e;

	@Column(name = "RACE_6E")
	private String race6e;

	@Column(name = "RACE_7E")
	private String race7e;

	@Column(name = "RACE_8E")
	private String race8e;

	@Column(name = "RACE_I_1_TEXT")
	private String raceI1Text;

	@Column(name = "RACE_I_2_TEXT")
	private String raceI2Text;

	@Column(name = "RACE_A_OTH_1_TEXT")
	private String raceAOth1Text;

	@Column(name = "RACE_A_OTH_2_TEXT")
	private String raceAOth2Text;

	@Column(name = "RACE_P_OTH_1_TEXT")
	private String racePOth1Text;

	@Column(name = "RACE_P_OTH_2_TEXT")
	private String racePOth2Text;

	@Column(name = "RACE_OTH_1_TEXT")
	private String raceOth1Text;

	@Column(name = "RACE_OTH_2_TEXT")
	private String raceOth2Text;

	@Column(name = "ICD10")
	private String icd10;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "UNDERLYING")
	private String underlying;

	@Column(name = "CAUSE1")
	private String cause1;

	@Column(name = "CAUSE2")
	private String cause2;

	@Column(name = "CAUSE3")
	private String cause3;

	@Column(name = "CAUSE4")
	private String cause4;

	@Column(name = "CAUSE5")
	private String cause5;

	@Column(name = "CAUSE6")
	private String cause6;

	@Column(name = "CAUSE7")
	private String cause7;

	@Column(name = "CAUSE8")
	private String cause8;

	@Column(name = "CAUSE9")
	private String cause9;

	@Column(name = "CAUSE10")
	private String cause10;

	@Column(name = "CAUSE11")
	private String cause11;

	@Column(name = "CAUSE12")
	private String cause12;

	@Column(name = "CAUSE13")
	private String cause13;

	@Column(name = "CAUSE14")
	private String cause14;

	@Column(name = "CAUSE15")
	private String cause15;

	@Column(name = "CAUSE16")
	private String cause16;

	@Column(name = "CAUSE17")
	private String cause17;

	@Column(name = "CAUSE18")
	private String cause18;

	@Column(name = "CAUSE19")
	private String cause19;

	@Column(name = "CAUSE20")
	private String cause20;

	@Column(name = "INJURY_LOC_CD")
	private String injuryLocCd;

	@Column(name = "INJURY_YEAR")
	private String injuryYear;

	@Column(name = "ADMIT_YEAR")
	private String admitYear;

	@Column(name = "FILED_MMDD")
	private String filedMmdd;

	@Column(name = "FILED_YYYY")
	private String filedYyyy;

	@Column(name = "QUERY_CODES_QCOD")
	private String queryCodesQcod;

	@Column(name = "FIPS_MSA")
	private String fipsMsa;

	@Column(name = "SOUNDEX4")
	private String soundex4;

	@Column(name = "LOCAL_REG_NO")
	private String localRegNo;

	@Column(name = "FIPS_PLACE")
	private String fipsPlace;

	@Column(name = "TRANS_FAC_CD")
	private String transFacCd;

	@Column(name = "TOD_TEXT")
	private String todText;

	@Column(name = "HISP_ORIGIN")
	private String hispOrigin;

	@Column(name = "INJURY_TIME")
	private String injuryTime;

	@Column(name = "BIRTH_HOSPITAL")
	private String birthHospital;

	@Column(name = "HOSPITAL_LOS")
	private String hospitalLos;

	@Column(name = "FIPS_MCD5")
	private String fipsMcd5;

	@Column(name = "DEATH_FILE")
	private String deathFile;

	@Column(name = "CERT")
	private String cert;

	@Column(name = "DEATH_CERT_LICENSE")
	private String deathCertLicense;

	@Column(name = "RES_NYC")
	private String resNyc;

	@Column(name = "INST_CODE")
	private String instCode;

	@Column(name = "DEATH_CERT")
	private String deathCert;

	@Column(name = "MBD_BIRTH_CERT_NO")
	private String mbdBirthCertNo;

	@Column(name = "OOS_FILE_NO")
	private String oosFileNo;

	@Column(name = "CENSUS_TRACT")
	private String censusTract;

	@Column(name = "PFI_NEW")
	private String pfiNew;

	@Column(name = "QUERY_CODES_ALL")
	private String queryCodesAll;

	@Column(name = "DOD")
	private String dod;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "DELIVERY_DT")
	private String deliveryDt;

	@Column(name = "DATE_OF_DISP")
	private String dateOfDisp;

	@Column(name = "EDC_CASE_NO")
	private String edcCaseNo;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "RES_ZIP")
	private String resZip;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "AUTOPSY_NYC_OOS")
	private String autopsyNycOos;

	@Column(name = "CERTIFIER_ROLE_NYC_OOS")
	private String certifierRoleNycOos;

	@Column(name = "DISP_TYPE_NYC_OOS")
	private String dispTypeNycOos;

	@Column(name = "MANNER_DEATH_NYC_OOS")
	private String mannerDeathNycOos;

	@Column(name = "MARITAL_STATUS_NYC_OOS")
	private String maritalStatusNycOos;

	@Column(name = "MMZIP")
	private String mmzip;

	@Column(name = "PLACE_OF_DEATH_TYPE")
	private String placeOfDeathType;

	@Column(name = "PREGNANT_NYC_OOS")
	private String pregnantNycOos;

	@Column(name = "SEX_NYC_OOS")
	private String sexNycOos;

	@Column(name = "TOBACCO_NYC_OOS")
	private String tobaccoNycOos;

	@Column(name = "WORK_INJURY_NYC_OOS")
	private String workInjuryNycOos;

	@Override
	public Integer getId() {
		return documentId;
	}

	/** Rows are only ever inserted. */
	@Override
	public boolean isNew() {
		return true;
	}
}
