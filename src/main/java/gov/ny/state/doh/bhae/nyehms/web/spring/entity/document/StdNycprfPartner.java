package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * A partner named on an NYC Partner Referral Form (table STD_NYCPRF_PARTNER).
 * <p>
 * Read-only for now: the partner list is read by
 * {@link gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycprfPartnerQueries}
 * with a native query, and the rows are still written by NycPRFDocumentDAO (MyBatis).
 * <b>The key (PATIENT_ID, NYCPRF_DOCUMENT_ID) is assumed and must be confirmed against the
 * table's primary key before this entity is used for writes or entity queries</b>
 * (see docs/JPA_MIGRATION.md).
 */
@Entity
@Immutable
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
	private LocalDateTime hdispDate;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private LocalDateTime createDate;

	@Column(name = "INFORMED_DATE")
	private LocalDateTime informedDate;

	@Column(name = "HIGH_PRIORITY")
	private String highPriority;

	@Column(name = "ELICITED_BY_PNAP")
	private String elicitedByPnap;

	@Column(name = "ELICITED_BY_ACT")
	private String elicitedByAct;

	public String getPatientId() {
		return patientId;
	}

	public String getNycprfDocumentId() {
		return nycprfDocumentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAltName() {
		return altName;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getPlus4() {
		return plus4;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public String getPager() {
		return pager;
	}

	public String getEmail() {
		return email;
	}

	public String getDobText() {
		return dobText;
	}

	public String getAge() {
		return age;
	}

	public String getCurrentGender() {
		return currentGender;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public String getRaceWhite() {
		return raceWhite;
	}

	public String getRaceBlack() {
		return raceBlack;
	}

	public String getRaceAsian() {
		return raceAsian;
	}

	public String getRaceIndian() {
		return raceIndian;
	}

	public String getRacePacific() {
		return racePacific;
	}

	public String getRaceUnknown() {
		return raceUnknown;
	}

	public String getRaceOther() {
		return raceOther;
	}

	public String getRaceOtherText() {
		return raceOtherText;
	}

	public String getComplexion() {
		return complexion;
	}

	public String getHair() {
		return hair;
	}

	public String getHeight() {
		return height;
	}

	public String getWeight() {
		return weight;
	}

	public String getPlaceOfEmployment() {
		return placeOfEmployment;
	}

	public String getWorkHours() {
		return workHours;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public String getMaritalCode() {
		return maritalCode;
	}

	public String getPregnant() {
		return pregnant;
	}

	public String getSpouse() {
		return spouse;
	}

	public String getDvScreen() {
		return dvScreen;
	}

	public String getRiskViolence() {
		return riskViolence;
	}

	public String getPartnerAssistanceStatus() {
		return partnerAssistanceStatus;
	}

	public String getPartnerTestDateText() {
		return partnerTestDateText;
	}

	public String getPartnerStatus() {
		return partnerStatus;
	}

	public LocalDateTime getHdispDate() {
		return hdispDate;
	}

	public String getCreateUid() {
		return createUid;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getInformedDate() {
		return informedDate;
	}

	public String getHighPriority() {
		return highPriority;
	}

	public String getElicitedByPnap() {
		return elicitedByPnap;
	}

	public String getElicitedByAct() {
		return elicitedByAct;
	}
}
