package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * A partner named on an NYC Partner Referral Form, standardized (table STD_NYCPRF_PARTNER).
 * <p>
 * Never updated: queried through JPQL projections and inserted with an HQL insert ... select
 * (see NycprfPartnerRepository). <b>The key (PATIENT_ID, NYCPRF_DOCUMENT_ID) is
 * assumed and must be confirmed against the table's primary key before this entity is loaded or
 * written as an entity</b> (docs/JPA_MIGRATION.md).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_NYCPRF_PARTNER")
@IdClass(StdNycprfPartnerId.class)
public class StdNycprfPartner {

	@Id
	@Column(name = "PATIENT_ID")
	private String patientId;

	@Id
	@Column(name = "NYCPRF_DOCUMENT_ID")
	private String nycprfDocumentId;

	@Column(name = "FNAME")
	private String firstName;

	@Column(name = "MNAME")
	private String middleName;

	@Column(name = "LNAME")
	private String lastName;

	@Column(name = "ALT_NAME")
	private String altName;

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

	@Column(name = "HOME_PHONE")
	private String homePhone;

	@Column(name = "CELL_PHONE")
	private String cellPhone;

	@Column(name = "PAGER")
	private String pager;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "AGE")
	private String age;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_OTHER_TEXT")
	private String raceOtherText;

	@Column(name = "COMPLEXION_PARTNER")
	private String complexion;

	@Column(name = "HAIR_PARTNER")
	private String hair;

	@Column(name = "HEIGHT")
	private String height;

	@Column(name = "WEIGHT")
	private String weight;

	@Column(name = "PLACE_OF_EMPLOYMENT")
	private String placeOfEmployment;

	@Column(name = "WORK_HOURS")
	private String workHours;

	@Column(name = "WORK_PHONE")
	private String workPhone;

	@Column(name = "MARITAL_CODE")
	private String maritalCode;

	@Column(name = "PREGNANT")
	private String pregnant;

	@Column(name = "SPOUSE")
	private String spouse;

	@Column(name = "DVSCREEN")
	private String dvScreen;

	@Column(name = "RISK_VIOLENCE")
	private String riskViolence;

	@Column(name = "PARTNER_ASSISTANCE_STATUS")
	private String partnerAssistanceStatus;

	@Column(name = "PARTNER_TEST_DATE_TEXT")
	private String partnerTestDateText;

	@Column(name = "PARTNER_STATUS")
	private String partnerStatus;

	@Column(name = "HDISP_DT")
	private Date hdispDate;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "INFORMED_DATE")
	private Date informedDate;

	@Column(name = "HIGH_PRIORITY")
	private String highPriority;

	@Column(name = "ELICITED_BY_PNAP")
	private String elicitedByPnap;

	@Column(name = "ELICITED_BY_ACT")
	private String elicitedByAct;



	// written by the import (NycprfPartnerRepository.insertStdPartnersOfDocument), not shown on the screen
	@Column(name = "RACE_CD")
	private String raceCd;

	@Column(name = "PARTNRID")
	private String partnrid;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "NOTIFIED_DATE")
	private String notifiedDate;

	@Column(name = "ENTER_DATE")
	private String enterDate;

	@Column(name = "EHARS_DIAG_DATE_TEXT")
	private String eharsDiagDateText;

	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "PRIORITY")
	private String priority;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "PARTNERS")
	private String partners;

	@Column(name = "REFERRAL")
	private String referral;

	@Column(name = "DISEASE_1")
	private String disease1;

	@Column(name = "DISEASE_2")
	private String disease2;

	@Column(name = "CODE")
	private String code;

	@Column(name = "INTERVIEW")
	private String interview;

	@Column(name = "INTERVIEW_TYPE")
	private String interviewType;

	@Column(name = "REFERRAL_TYPE")
	private String referralType;

	@Column(name = "PRINTED")
	private String printed;

	@Column(name = "NOTSEND")
	private String notsend;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "INDEX_CITYNO")
	private String indexCityno;

	@Column(name = "DISPO")
	private String dispo;

	@Column(name = "HIVDISPO_DATE_TEXT")
	private String hivdispoDateText;
}
