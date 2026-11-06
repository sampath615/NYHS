package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

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
 * The NYC feedback on the ADAP records shipped to NYC (table NYEHMS_ADAP_NYC_FEEDBACK): one row per record of an
 * uploaded NYC return file (ADAPDocumentDAO.uploadNYCReturnFileData), reviewed on the NYC review screen. The columns the
 * former statements used.
 * <p>
 * <b>STDRPTS_ID (the ADAP document id) as the key is assumed, but it may repeat</b> (a record uploaded twice): the
 * rows are inserted one by one and detached, and read through projections and bulk updates only. The text columns
 * are written as the former insert bound them (trimmed in Java as Oracle's TRIM did); UPLOAD_DATE is the database's
 * SYSDATE (left null on insert). REVIEWED_CITYNO and REVIEW_COMMENTS are set by the review only.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "NYEHMS_ADAP_NYC_FEEDBACK")
public class NyehmsAdapNycFeedback {

	@Id
	@Column(name = "STDRPTS_ID")
	private Long stdrptsId;

	@Column(name = "ADAP_FILE")
	private String adapFile;

	@Column(name = "INS_NO")
	private String insNo;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "MEDRECNO")
	private String medrecno;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "PLUS4")
	private String plus4;

	@Column(name = "COUNTY_RES")
	private String countyRes;

	@Column(name = "TRACT_CENS")
	private String tractCens;

	@Column(name = "RES_LAT")
	private String resLat;

	@Column(name = "RES_LONG")
	private String resLong;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_MNAME")
	private String provMname;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "PROV_ADDRE")
	private String provAddre;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "PROV_PLUS4")
	private String provPlus4;

	@Column(name = "COUNTY_PRO")
	private String countyPro;

	@Column(name = "PROV_PHONE")
	private String provPhone;

	@Column(name = "PROV_LATIT")
	private String provLatit;

	@Column(name = "PROV_LONGI")
	private String provLongi;

	@Column(name = "TRACT_CEN0")
	private String tractCen0;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "MTCH_PASS")
	private BigDecimal mtchPass;

	@Column(name = "RACE_CD")
	private String raceCd;

	@Column(name = "ETHNICCD")
	private String ethniccd;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "EVENT_DT")
	private String eventDt;

	@Column(name = "RISK01")
	private String risk01;

	@Column(name = "RISK02")
	private String risk02;

	@Column(name = "RISK03")
	private String risk03;

	@Column(name = "RISK04")
	private String risk04;

	@Column(name = "RISK05")
	private String risk05;

	@Column(name = "RISK06")
	private String risk06;

	@Column(name = "RISK07")
	private String risk07;

	@Column(name = "RISK08")
	private String risk08;

	@Column(name = "RISK09")
	private String risk09;

	@Column(name = "RACE_IND")
	private String raceInd;

	@Column(name = "RACE_BLA")
	private String raceBla;

	@Column(name = "RACE_ASI")
	private String raceAsi;

	@Column(name = "RACE_PAC")
	private String racePac;

	@Column(name = "RACE_WHI")
	private String raceWhi;

	@Column(name = "RACE_MUL")
	private String raceMul;

	@Column(name = "RACE_OTH")
	private String raceOth;

	@Column(name = "RACE_UNK")
	private String raceUnk;

	@Column(name = "NYC_SHIP_D")
	private String nycShipD;

	@Column(name = "RECV_DT")
	private String recvDt;

	@Column(name = "NYCNUMBER")
	private String nycnumber;

	@Column(name = "UPLOAD_UID")
	private String uploadUid;

	@Column(name = "UPLOAD_DATE")
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	private Timestamp uploadDate;

	@Column(name = "REVIEWED_ID")
	private String reviewedId;

	@Column(name = "REVIEWED_DATE")
	private Timestamp reviewedDate;

	@Column(name = "REVIEW_STATUS")
	private String reviewStatus;

	@Column(name = "REVIEWED_CITYNO", insertable = false)
	private String reviewedCityno;

	@Column(name = "REVIEW_COMMENTS", insertable = false)
	private String reviewComments;
}
