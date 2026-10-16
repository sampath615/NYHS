package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A Black Box record, standardized (table STD_BLACK_BOX_DOC); the columns the Black Box document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_BLACK_BOX_DOC")
public class StdBlackBoxDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "RECEIVED_DATE")
	private Date receivedDate;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "VITAL_STATUS")
	private String vitalStatus;

	@Column(name = "XLAB")
	private String xlab;

	@Column(name = "PHYS_HIV")
	private String physHiv;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "HIV_DX_DATE_TEXT")
	private String hivDxDateText;

	@Column(name = "AIDS_DX_DATE_TEXT")
	private String aidsDxDateText;

	@Column(name = "HIV_AIDS_DX_DATE_TEXT")
	private String hivAidsDxDateText;

	@Column(name = "CUR_ADDRESS_DATE_TEXT")
	private String curAddressDateText;

	@Column(name = "SAMPLE_DATE_HIV1_TEXT")
	private String sampleDateHiv1Text;

	@Column(name = "SAMPLE_DATE_HIV2_TEXT")
	private String sampleDateHiv2Text;

	@Column(name = "SAMPLE_DATE_HIV3_TEXT")
	private String sampleDateHiv3Text;

	@Column(name = "XLAB_DATE_TEXT")
	private String xlabDateText;

	@Column(name = "PHYS_HIV_DATE_TEXT")
	private String physHivDateText;

	@Column(name = "SAMPLE_DATE_AIDS_TEXT")
	private String sampleDateAidsText;

	@Column(name = "OI_DX_DATE_TEXT")
	private String oiDxDateText;

	@Column(name = "VL_RECENT_DATE_TEXT")
	private String vlRecentDateText;

	@Column(name = "CD4_RECENT_CNT_DATE_TEXT")
	private String cd4RecentCntDateText;

	@Column(name = "CD4_RECENT_PCT_DATE_TEXT")
	private String cd4RecentPctDateText;

	@Column(name = "SAMPLE_DT_NEG_TEXT")
	private String sampleDtNegText;

	@Column(name = "TTH_LAST_NEG_DATE_TEXT")
	private String tthLastNegDateText;

	@Column(name = "RSD_STREET_ADDRESS1")
	private String rsdStreetAddress1;

	@Column(name = "RSD_STREET_ADDRESS2")
	private String rsdStreetAddress2;

	@Column(name = "CUR_STREET_ADDRESS1")
	private String curStreetAddress1;

	@Column(name = "CUR_STREET_ADDRESS2")
	private String curStreetAddress2;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "RIDR_STATENO")
	private String ridrStateno;

	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "TRANS_CATEG")
	private String transCateg;

	@Column(name = "RSH_STATE_CD")
	private String rshStateCd;

	@Column(name = "RSA_STATE_CD")
	private String rsaStateCd;

	@Column(name = "RSD_STATE_CD")
	private String rsdStateCd;

	@Column(name = "RAD_STATE_CD")
	private String radStateCd;

	@Column(name = "CUR_STATE_CD")
	private String curStateCd;

	@Column(name = "STATE_CODE")
	private String stateCode;

	@Column(name = "SCORE")
	private String score;

	@Column(name = "LAB_TEST_CD_HIV1")
	private String labTestCdHiv1;

	@Column(name = "LAB_TEST_TYPE_HIV1")
	private String labTestTypeHiv1;

	@Column(name = "RESULT_HIV1")
	private String resultHiv1;

	@Column(name = "RESULT_INTERPRETATION_HIV1")
	private String resultInterpretationHiv1;

	@Column(name = "RESULT_UNITS_HIV1")
	private String resultUnitsHiv1;

	@Column(name = "LAB_TEST_CD_HIV2")
	private String labTestCdHiv2;

	@Column(name = "LAB_TEST_TYPE_HIV2")
	private String labTestTypeHiv2;

	@Column(name = "RESULT_HIV2")
	private String resultHiv2;

	@Column(name = "RESULT_INTERPRETATION_HIV2")
	private String resultInterpretationHiv2;

	@Column(name = "RESULT_UNITS_HIV2")
	private String resultUnitsHiv2;

	@Column(name = "LAB_TEST_CD_HIV3")
	private String labTestCdHiv3;

	@Column(name = "LAB_TEST_TYPE_HIV3")
	private String labTestTypeHiv3;

	@Column(name = "RESULT_HIV3")
	private String resultHiv3;

	@Column(name = "RESULT_INTERPRETATION_HIV3")
	private String resultInterpretationHiv3;

	@Column(name = "RESULT_UNITS_HIV3")
	private String resultUnitsHiv3;

	@Column(name = "LAB_TEST_CD_AIDS")
	private String labTestCdAids;

	@Column(name = "LAB_TEST_TYPE_AIDS")
	private String labTestTypeAids;

	@Column(name = "RESULT_AIDS")
	private String resultAids;

	@Column(name = "RESULT_UNITS_AIDS")
	private String resultUnitsAids;

	@Column(name = "OI_CD")
	private String oiCd;

	@Column(name = "VL_RECENT_VALUE")
	private String vlRecentValue;

	@Column(name = "CD4_RECENT_CNT_VALUE")
	private String cd4RecentCntValue;

	@Column(name = "CD4_RECENT_PCT_VALUE")
	private String cd4RecentPctValue;

	@Column(name = "LAB_TEST_CD_NEG")
	private String labTestCdNeg;

	@Column(name = "LAB_TEST_TYPE_NEG")
	private String labTestTypeNeg;

	@Column(name = "RESULT_NEG")
	private String resultNeg;

	@Column(name = "RESULT_INTERPRETATION_NEG")
	private String resultInterpretationNeg;

	@Column(name = "RESULT_UNITS_NEG")
	private String resultUnitsNeg;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "HIV_CATEG")
	private String hivCateg;

	@Column(name = "AIDS_CATEG")
	private String aidsCateg;

	@Column(name = "RSD_COUNTRY_CD")
	private String rsdCountryCd;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "RSD_CITY_NAME")
	private String rsdCityName;

	@Column(name = "CUR_CITY_NAME")
	private String curCityName;

	@Column(name = "RSH_CITY_NAME")
	private String rshCityName;

	@Column(name = "RSA_CITY_NAME")
	private String rsaCityName;

	@Column(name = "RSD_ZIP_CD")
	private String rsdZipCd;

	@Column(name = "CUR_ZIP_CD")
	private String curZipCd;

	@Column(name = "RSH_ZIP_CD")
	private String rshZipCd;

	@Column(name = "RSA_ZIP_CD")
	private String rsaZipCd;

	@Column(name = "MATCH_SUMMARY")
	private String matchSummary;

	@Column(name = "RSD_COUNTY_NAME")
	private String rsdCountyName;

	@Column(name = "CUR_COUNTY_NAME")
	private String curCountyName;

	@Column(name = "RSH_COUNTY_NAME")
	private String rshCountyName;

	@Column(name = "RSA_COUNTY_NAME")
	private String rsaCountyName;
}
