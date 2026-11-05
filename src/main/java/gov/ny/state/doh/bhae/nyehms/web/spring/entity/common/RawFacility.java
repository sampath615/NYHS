package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A facility as received (table RAW_FACILITY): the columns the facility standardization DAOs read and write
 * (RawSubFacilityDAO, SubFacilityDAO, MasterFacilityDetailsDAO).
 * <p>
 * <b>RAW_SUB_FACILITY_ID as the key is assumed</b>; new rows take it from RAW_FACILITY_SEQ, as the former inserts did
 * (RAW_FACILITY_SEQ.NEXTVAL). LAST_UPDATED_DATE / LAST_UPDATED_BY are not written on insert (the former inserts did
 * not list them). A change writes only the changed columns. SUB_FACILITY_ID 0 marks a raw facility that cannot be
 * standardized (no foreign key).
 */
@Entity
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_FACILITY")
public class RawFacility {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rawFacilitySeq")
	@SequenceGenerator(name = "rawFacilitySeq", sequenceName = "RAW_FACILITY_SEQ", allocationSize = 1)
	@Column(name = "RAW_SUB_FACILITY_ID")
	private Long rawSubFacilityId;

	@Column(name = "SUB_FACILITY_ID")
	private Long subFacilityId;

	@Column(name = "FACILITY_TYPE_CODE")
	private String facilityTypeCode;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_DEPARTMENT")
	private String facilityDepartment;

	@Column(name = "LEGACY_FACILITY_ID")
	private String legacyFacilityId;

	@Column(name = "LEGACY_FACILITY_TYPE")
	private String legacyFacilityType;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "PLUS4")
	private String plus4;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "COUNTY_FIPS")
	private String countyFips;

	@Column(name = "NPI")
	private String npi;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "DATE_CREATED")
	private Timestamp dateCreated;

	@Column(name = "LAST_UPDATED_DATE", insertable = false)
	private Timestamp lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY", insertable = false)
	private String lastUpdatedBy;

	/** Marks the raw facility as one that cannot be standardized (SUB_FACILITY_ID 0). */
	public void unableToStandardize(String lastUpdatedBy, Timestamp lastUpdatedDate) {
		this.subFacilityId = 0L;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
}
