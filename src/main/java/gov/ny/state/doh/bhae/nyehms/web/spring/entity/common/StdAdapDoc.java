package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.math.BigDecimal;
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
 * A standardized ADAP (AIDS Drug Assistance Program) record (table STD_ADAP_DOC): inserted by the ADAP import and the
 * document copy (ADAPDocumentDAO.writeStdDocData), read by the ADAP document screen, the risk view and the NYC file
 * screens, its NYC ship date set when the NYC file is generated. The columns the former statements used.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (given by the application). The conversions the former insert left to the
 * database stay there (@ColumnTransformer: UPPER(TRIM(...)) of the text columns, except ADAP_FILE and the FLUX
 * columns, which were written as they were). The risk columns RISK09 .. RISK15 are only read; NYC_SHIP_DT and
 * NYC_SHIPPED_BY were not written by the insert (database default) and are set by a bulk update.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_ADAP_DOC")
public class StdAdapDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "BIRTH_SEX")
	@ColumnTransformer(write = "upper(trim(?))")
	private String birthSex;

	@Column(name = "CURRENT_GENDER")
	@ColumnTransformer(write = "upper(trim(?))")
	private String currentGender;

	@Column(name = "EHARS_STATUS_CD")
	@ColumnTransformer(write = "upper(trim(?))")
	private String eharsStatusCd;

	@Column(name = "ASSESS_ASSIGN")
	@ColumnTransformer(write = "upper(trim(?))")
	private String assessAssign;

	@Column(name = "DOB_TEXT")
	@ColumnTransformer(write = "upper(trim(?))")
	private String dobText;

	@Column(name = "PERSON_LATITUDE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personLatitude;

	@Column(name = "CERT_PROV_LATITUDE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvLatitude;

	@Column(name = "PCP_PROV_LATITUDE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvLatitude;

	@Column(name = "RX_PROVIDER_TYPE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProviderType;

	@Column(name = "RX_PROV_LATITUDE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvLatitude;

	@Column(name = "FIRST_HIV_TEST_DATE_TEXT")
	@ColumnTransformer(write = "upper(trim(?))")
	private String firstHivTestDateText;

	@Column(name = "REVIEW_UID")
	@ColumnTransformer(write = "upper(trim(?))")
	private String reviewUid;

	@Column(name = "CITYNO")
	@ColumnTransformer(write = "upper(trim(?))")
	private String cityno;

	@Column(name = "PERSON_LONGITUDE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personLongitude;

	@Column(name = "CERT_PROV_LONGITUDE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvLongitude;

	@Column(name = "PCP_PROV_LONGITUDE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvLongitude;

	@Column(name = "RX_PROV_LONGITUDE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvLongitude;

	@Column(name = "CERT_PROV_NPI")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvNpi;

	@Column(name = "PCP_PROV_NPI")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvNpi;

	@Column(name = "RX_PROV_NPI")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvNpi;

	@Column(name = "PERSON_STATE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personState;

	@Column(name = "CERT_PROV_STATE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvState;

	@Column(name = "PCP_PROV_STATE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvState;

	@Column(name = "PROV_CENSUS_TRACT_SUFFIX_PCP")
	@ColumnTransformer(write = "upper(trim(?))")
	private String provCensusTractSuffixPcp;

	@Column(name = "RX_PROV_STATE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvState;

	@Column(name = "PERSON_COUNTY")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personCounty;

	@Column(name = "PERSON_CITY")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personCity;

	@Column(name = "CERT_PROV_CITY")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvCity;

	@Column(name = "PCP_PROV_CITY")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvCity;

	@Column(name = "RX_PROV_CITY")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvCity;

	@Column(name = "PERSON_PLUS4")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personPlus4;

	@Column(name = "PERSON_CENSUS_TRACT")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personCensusTract;

	@Column(name = "CERT_PROV_PLUS4")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvPlus4;

	@Column(name = "CERT_PROV_CENSUS_TRACT")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvCensusTract;

	@Column(name = "PCP_PROV_PLUS4")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvPlus4;

	@Column(name = "PCP_PROV_CENSUS_TRACT")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvCensusTract;

	@Column(name = "RX_PROV_PLUS4")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvPlus4;

	@Column(name = "RX_PROV_CENSUS_TRACT")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvCensusTract;

	@Column(name = "CARE_OF")
	@ColumnTransformer(write = "upper(trim(?))")
	private String careOf;

	@Column(name = "ADAP_FILE")
	private String adapFile;

	@Column(name = "PERSON_ZIP")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personZip;

	@Column(name = "CERT_PROV_ZIP")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvZip;

	@Column(name = "PCP_PROV_ZIP")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvZip;

	@Column(name = "RX_PROV_ZIP")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvZip;

	@Column(name = "SSN")
	@ColumnTransformer(write = "upper(trim(?))")
	private String ssn;

	@Column(name = "REVIEW_DT")
	private Timestamp reviewDt;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "RECEIVED_DATE")
	private Timestamp receivedDate;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "RAW_PROVIDER_ADDRESS_ID")
	private Long rawProviderAddressId;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "QS_PARENT_DOC_ID")
	private Long qsParentDocId;

	@Column(name = "RACE_INDIAN")
	@ColumnTransformer(write = "upper(trim(?))")
	private String raceIndian;

	@Column(name = "RACE_BLACK")
	@ColumnTransformer(write = "upper(trim(?))")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	@ColumnTransformer(write = "upper(trim(?))")
	private String raceAsian;

	@Column(name = "RACE_PACIFIC")
	@ColumnTransformer(write = "upper(trim(?))")
	private String racePacific;

	@Column(name = "RACE_WHITE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String raceWhite;

	@Column(name = "RACE_MULTIPLE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String raceMultiple;

	@Column(name = "RACE_OTHER")
	@ColumnTransformer(write = "upper(trim(?))")
	private String raceOther;

	@Column(name = "RACE_UNKNOWN")
	@ColumnTransformer(write = "upper(trim(?))")
	private String raceUnknown;

	@Column(name = "CREATE_UID")
	@ColumnTransformer(write = "upper(trim(?))")
	private String createUid;

	@Column(name = "MATCHED_STATENO")
	@ColumnTransformer(write = "upper(trim(?))")
	private String matchedStateno;

	@Column(name = "MEDICAID_NUMBER")
	@ColumnTransformer(write = "upper(trim(?))")
	private String medicaidNumber;

	@Column(name = "ETHNICCD")
	@ColumnTransformer(write = "upper(trim(?))")
	private String ethniccd;

	@Column(name = "RACE_CD")
	@ColumnTransformer(write = "upper(trim(?))")
	private String raceCd;

	@Column(name = "SWM")
	@ColumnTransformer(write = "upper(trim(?))")
	private String swm;

	@Column(name = "SWF")
	@ColumnTransformer(write = "upper(trim(?))")
	private String swf;

	@Column(name = "IDU")
	@ColumnTransformer(write = "upper(trim(?))")
	private String idu;

	@Column(name = "SEX_PARTNER_IDU")
	@ColumnTransformer(write = "upper(trim(?))")
	private String sexPartnerIdu;

	@Column(name = "SEX_PARTNER_HIV")
	@ColumnTransformer(write = "upper(trim(?))")
	private String sexPartnerHiv;

	@Column(name = "RECV_BLOOD_PROD")
	@ColumnTransformer(write = "upper(trim(?))")
	private String recvBloodProd;

	@Column(name = "PERINATAL")
	@ColumnTransformer(write = "upper(trim(?))")
	private String perinatal;

	@Column(name = "RISK_OTHER")
	@ColumnTransformer(write = "upper(trim(?))")
	private String riskOther;

	@Column(name = "HEALTHCARE_WORKER")
	@ColumnTransformer(write = "upper(trim(?))")
	private String healthcareWorker;

	@Column(name = "FNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String fname;

	@Column(name = "PERSON_PHONE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personPhone;

	@Column(name = "CERT_PROV_FNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvFname;

	@Column(name = "CERT_PROV_MNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvMname;

	@Column(name = "CERT_PROV_COUNTY")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvCounty;

	@Column(name = "CERT_PROV_PHONE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvPhone;

	@Column(name = "PCP_PROV_FNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvFname;

	@Column(name = "PCP_PROV_MNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvMname;

	@Column(name = "PCP_PROV_COUNTY")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvCounty;

	@Column(name = "PCP_PROV_PHONE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvPhone;

	@Column(name = "RX_PROV_FNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvFname;

	@Column(name = "RX_PROV_MNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvMname;

	@Column(name = "RX_PROV_COUNTY")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvCounty;

	@Column(name = "RX_PROV_PHONE")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvPhone;

	@Column(name = "CERT_PROV_ADDRESS")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvAddress;

	@Column(name = "PCP_PROV_ADDRESS")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvAddress;

	@Column(name = "RX_PROV_ADDRESS")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvAddress;

	@Column(name = "LNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String lname;

	@Column(name = "MNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String mname;

	@Column(name = "ADAP_ID")
	@ColumnTransformer(write = "upper(trim(?))")
	private String adapId;

	@Column(name = "CERT_PROV_LNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String certProvLname;

	@Column(name = "PCP_PROV_LNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String pcpProvLname;

	@Column(name = "RX_PROV_LNAME")
	@ColumnTransformer(write = "upper(trim(?))")
	private String rxProvLname;

	@Column(name = "ADAP_SRC_ID")
	@ColumnTransformer(write = "upper(trim(?))")
	private String adapSrcId;

	@Column(name = "MATCH_CD")
	@ColumnTransformer(write = "upper(trim(?))")
	private String matchCd;

	@Column(name = "FLAG_CHOSEN_PROVIDER")
	@ColumnTransformer(write = "upper(trim(?))")
	private String flagChosenProvider;

	@Column(name = "MATCH_PASS")
	@ColumnTransformer(write = "upper(trim(?))")
	private String matchPass;

	@Column(name = "SUFFIX")
	@ColumnTransformer(write = "upper(trim(?))")
	private String suffix;

	@Column(name = "PERSON_ADDRESS")
	@ColumnTransformer(write = "upper(trim(?))")
	private String personAddress;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "FLUX_POT_STATENO")
	private String fluxPotStateno;

	@Column(name = "FLUX_MATCHED_STATENO")
	private String fluxMatchedStateno;

	@Column(name = "FLUX_QS_PARENT_DOC_ID")
	private Long fluxQsParentDocId;

	@Column(name = "FLUX_MATCHED_PROCESS_DATE")
	private Timestamp fluxMatchedProcessDate;

	@Column(name = "RISK09", insertable = false, updatable = false)
	private String risk09;

	@Column(name = "RISK10", insertable = false, updatable = false)
	private String risk10;

	@Column(name = "RISK12", insertable = false, updatable = false)
	private String risk12;

	@Column(name = "RISK13", insertable = false, updatable = false)
	private String risk13;

	@Column(name = "RISK14", insertable = false, updatable = false)
	private String risk14;

	@Column(name = "RISK15", insertable = false, updatable = false)
	private String risk15;

	@Column(name = "NYC_SHIP_DT", insertable = false)
	private Timestamp nycShipDt;

	@Column(name = "NYC_SHIPPED_BY", insertable = false)
	private String nycShippedBy;
}
