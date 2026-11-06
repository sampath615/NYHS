package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The cluster partners loaded for review (table NYEHMS_CLUSTER_PARTNERS): the columns the cluster partner review
 * reads and changes.
 * <p>
 * <b>PARTNER_ID as the key is assumed.</b> Read through projections, changed by JPQL bulk updates. Attribute types
 * follow the VOs the rows were read into (text where the VO had text, e.g. the race columns; Integer where it had
 * Integer); the dates are DATE columns (formerly read with TO_CHAR).
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_CLUSTER_PARTNERS")
public class NyehmsClusterPartners {

	@Id
	@Column(name = "PARTNER_ID")
	private Long partnerId;

	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "CLUSTER_ID")
	private String clusterId;

	@Column(name = "PARTNER_CLUSTER_ID")
	private String partnerClusterId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "DISCARD_DATE")
	private Timestamp discardDate;

	@Column(name = "DISCARD_BY")
	private Long discardBy;

	@Column(name = "DIN")
	private String din;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "HOME_PHONE")
	private String homePhone;

	@Column(name = "CELL_PHONE")
	private String cellPhone;

	@Column(name = "STREET")
	private String street;

	@Column(name = "CITY")
	private String city;

	@Column(name = "COUNTY")
	private String county;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "MEDRECNO")
	private String medrecno;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "NYSID")
	private String nysid;

	@Column(name = "DATE_OF_BIRTH")
	private Timestamp dateOfBirth;

	@Column(name = "DATE_OF_DEATH")
	private Timestamp dateOfDeath;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_AMERIND")
	private String raceAmerind;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "COUNTRYOFBIRTH")
	private String countryofbirth;

	@Column(name = "CURRENTGENDER")
	private String currentgender;

	@Column(name = "PARTNERASSISTANCESTATUS")
	private String partnerassistancestatus;

	@Column(name = "CONFIRMPATIENTPREP")
	private String confirmpatientprep;

	@Column(name = "CURRENTONPREP")
	private Integer currentonprep;

	@Column(name = "PARTNER_TYPE")
	private Integer partnerType;

	@Column(name = "PARTNERKEY")
	private Integer partnerkey;

	@Column(name = "PARTNER_FLAG")
	private Integer partnerFlag;

	@Column(name = "PARTNER_FRNUMBER")
	private String partnerFrnumber;

	@Column(name = "PART_INACTIVE_FLAG")
	private String partInactiveFlag;

	@Column(name = "PART_INACTIVE_DATE")
	private Timestamp partInactiveDate;

	@Column(name = "DISNUMBER")
	private Integer disnumber;

	@Column(name = "LEGACY_PARTNER_ID")
	private Integer legacyPartnerId;

	@Column(name = "LAST_HIV_NEG_DATE")
	private Timestamp lastHivNegDate;

	@Column(name = "OP_STATENO")
	private String opStateno;

	@Column(name = "OP_CDESSCASEKEY")
	private Integer opCdesscasekey;

	@Column(name = "OP_FRNUMBER")
	private String opFrnumber;

	@Column(name = "OP_FIELDRECORDKEY")
	private Integer opFieldrecordkey;

	@Column(name = "OP_INACTIVE_FLAG")
	private String opInactiveFlag;

	@Column(name = "OP_DATE_CLOSED")
	private Timestamp opDateClosed;

	@Column(name = "DATEINITIATED")
	private Timestamp dateinitiated;

	@Column(name = "INTERVIEWDATE")
	private Timestamp interviewdate;

	@Column(name = "NOTIFICATIONDATEPIND")
	private String notificationdatepind;

	@Column(name = "NOTIFICATIONDATE")
	private Timestamp notificationdate;

	@Column(name = "CONDITION_1")
	private String condition1;

	@Column(name = "CONDITION_2")
	private String condition2;

	@Column(name = "CONDITION_3")
	private String condition3;

	@Column(name = "CONDITION_4")
	private String condition4;

	@Column(name = "DISPOSITION1")
	private String disposition1;

	@Column(name = "DISPOSITION2")
	private String disposition2;

	@Column(name = "DISPOSITION3")
	private String disposition3;

	@Column(name = "DISPOSITION4")
	private String disposition4;

	@Column(name = "DISPOSITIONDATE1")
	private Timestamp dispositiondate1;

	@Column(name = "DISPOSITIONDATE2")
	private Timestamp dispositiondate2;

	@Column(name = "DISPOSITIONDATE3")
	private Timestamp dispositiondate3;

	@Column(name = "DISPOSITIONDATE4")
	private Timestamp dispositiondate4;
}
