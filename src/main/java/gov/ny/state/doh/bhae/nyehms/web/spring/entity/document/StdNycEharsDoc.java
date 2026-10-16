package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An NYC eHARS case report, standardized (table STD_NYC_EHARS_DOC); the columns the NYC eHARS document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_NYC_EHARS_DOC")
public class StdNycEharsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "NYEHMS_STATENO")
	private String nyehmsStateno;

	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "NYC_EHARS_UID")
	private String nycEharsUid;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_ADDRESS_DATE")
	private String personAddressDate;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "VITAL_STATUS")
	private String vitalStatus;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "SWM")
	private String swm;

	@Column(name = "SWF")
	private String swf;

	@Column(name = "IDU")
	private String idu;

	@Column(name = "SEX_PARTNER_IDU")
	private String sexPartnerIdu;

	@Column(name = "RECV_BLOOD_PROD")
	private String recvBloodProd;

	@Column(name = "SEX_PARTNER_MSM")
	private String sexPartnerMsm;

	@Column(name = "RECV_CLOTTING_FACTOR")
	private String recvClottingFactor;

	@Column(name = "SEX_PARTNER_TRANSFUSION")
	private String sexPartnerTransfusion;

	@Column(name = "SEX_PARTNER_TRANSPLANT")
	private String sexPartnerTransplant;

	@Column(name = "RECV_TRANSPLANT")
	private String recvTransplant;

	@Column(name = "HEALTHCARE_WORKER")
	private String healthcareWorker;

	@Column(name = "CREATE_DT")
	private Date createDt;

	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "CREATEID")
	private String createid;

	@Column(name = "HIV_CATEG")
	private String hivCateg;

	@Column(name = "AIDS_CATEG")
	private String aidsCateg;

	@Column(name = "HIV_DX_DT")
	private String hivDxDt;

	@Column(name = "AIDS_DX_DT")
	private String aidsDxDt;
}
