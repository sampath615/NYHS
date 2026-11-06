package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
 * A partner named on a provider report form, standardized (table STD_PRF_PARTNER): the columns the partner screen
 * reads and the import copies from RAW_PRF_PARTNER (PRFPartnerDAO, PRFDocumentDAO).
 * <p>
 * Written by an HQL insert ... select only, read with JPQL projections. ENTRY_REVIEW_DATE is also read as text
 * ({@code entryReviewDateText}, read-only), as the screen read it with getString. <b>PARTNER_ID as the key is
 * assumed</b> (copied from the raw partner).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_PRF_PARTNER")
public class StdPrfPartner implements Persistable<Integer> {

	@Id
	@Column(name = "PARTNER_ID")
	private Integer partnerId;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "AGE")
	private BigDecimal age;

	@Column(name = "ALIAS_FNAME")
	private String aliasFname;

	@Column(name = "ALIAS_LNAME")
	private String aliasLname;

	@Column(name = "ALIAS_MNAME")
	private String aliasMname;

	@Column(name = "CELL_PHONE")
	private String cellPhone;

	@Column(name = "CITY")
	private String city;

	@Column(name = "COMPLEXION")
	private String complexion;

	@Column(name = "COUNTY")
	private String county;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "DV_RISK")
	private String dvRisk;

	@Column(name = "DV_SCREEN")
	private String dvScreen;

	@Column(name = "ELICITED_BY_ACT")
	private String elicitedByAct;

	@Column(name = "ELICITED_BY_PNAP")
	private String elicitedByPnap;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ENTRY_REVIEW_DATE")
	private Timestamp entryReviewDate;

	@Column(name = "ENTRY_REVIEW_ID")
	private String entryReviewId;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "HAIR")
	private String hair;

	@Column(name = "HEIGHT")
	private String height;

	@Column(name = "HIGH_PRIORITY")
	private String highPriority;

	@Column(name = "HOME_PHONE")
	private String homePhone;

	@Column(name = "IDENTIFIED_BY_HD_PS")
	private String identifiedByHdPs;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "OTHER_CHARACTERISTICS")
	private String otherCharacteristics;

	@Column(name = "PAGER")
	private String pager;

	@Column(name = "PARTNER_ASSISTANCE_STATUS")
	private String partnerAssistanceStatus;

	@Column(name = "PARTNER_NOTIFIED_DATE_TEXT")
	private String partnerNotifiedDateText;

	@Column(name = "PARTNER_STATUS")
	private String partnerStatus;

	@Column(name = "PARTNER_TEST_DATE_TEXT")
	private String partnerTestDateText;

	@Column(name = "PLACE_OF_EMPLOYMENT")
	private String placeOfEmployment;

	@Column(name = "PLUS4")
	private String plus4;

	@Column(name = "PREGNANT")
	private String pregnant;

	@Column(name = "PRF_DOC_ID")
	private Integer prfDocId;

	@Column(name = "PRF_SRC_ID")
	private Long prfSrcId;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_OTHER_TEXT")
	private String raceOtherText;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "SPOUSE")
	private String spouse;

	@Column(name = "STATE")
	private String state;

	@Column(name = "WEIGHT")
	private String weight;

	@Column(name = "WORK_HOURS")
	private String workHours;

	@Column(name = "WORK_PHONE")
	private String workPhone;

	@Column(name = "WORK_PHONE_EXT")
	private String workPhoneExt;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "TELEPHONE_NO_1")
	private String telephoneNo1;

	@Column(name = "TELEPHONE_NO_1_TYPE")
	private String telephoneNo1Type;

	@Column(name = "TELEPHONE_NO_2")
	private String telephoneNo2;

	@Column(name = "TELEPHONE_NO_2_TYPE")
	private String telephoneNo2Type;

	@Column(name = "WEBSITE_MOB_APP_1")
	private String websiteMobApp1;

	@Column(name = "WEBSITE_MOB_APP_1_USERNAME")
	private String websiteMobApp1Username;

	@Column(name = "WEBSITE_MOB_APP_2")
	private String websiteMobApp2;

	@Column(name = "WEBSITE_MOB_APP_2_USERNAME")
	private String websiteMobApp2Username;

	@Column(name = "PHYSICAL_DESCRIPTION")
	private String physicalDescription;

	@Column(name = "SEX_AT_BIRTH")
	private String sexAtBirth;

	@Column(name = "ADDITIONAL_GENDER_IDENTITY")
	private String additionalGenderIdentity;

	@Column(name = "ENTRY_REVIEW_DATE", insertable = false, updatable = false)
	private String entryReviewDateText;

	@Override
	public Integer getId() {
		return partnerId;
	}

	/** Rows are only ever inserted. */
	@Override
	public boolean isNew() {
		return true;
	}
}
