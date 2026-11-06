package gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The standardized cluster partner documents (table STD_CLUSTER_PARTNERS): the columns the cluster partner screen
 * reads and the address change writes.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed.</b> Read through projections, changed by JPQL bulk updates. The race
 * columns are read as text (as the screen's VO read them).
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STD_CLUSTER_PARTNERS")
public class StdClusterPartners {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "PARTNER_ID")
	private Integer partnerId;

	@Column(name = "CLUSTER_ID")
	private String clusterId;

	@Column(name = "DIN")
	private String din;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "HOME_PHONE")
	private String homePhone;

	@Column(name = "CELL_PHONE")
	private String cellPhone;

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

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "MEDRECNO")
	private String medrecno;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "NYSID")
	private String nysid;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "DATE_OF_DEATH")
	private Timestamp dateOfDeath;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "RSN_NO_PS_MSG")
	private String rsnNoPsMsg;

	@Column(name = "RSN_PS_MSG")
	private String rsnPsMsg;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "PARTNER_ASSISTANCE_STATUS")
	private String partnerAssistanceStatus;
}
