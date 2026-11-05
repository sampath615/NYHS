package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A standardized (sub) facility (table SUB_FACILITY): the columns the facility standardization DAOs read and change
 * (SubFacilityDAO, MasterFacilityDetailsDAO). New sub facilities are created by PKG_STANDARDIZATION procedures.
 * <p>
 * <b>SUB_FACILITY_ID as the key is assumed.</b> MASTER_FACILITY_ID 0 marks a sub facility that cannot be
 * standardized (no foreign key). A change writes only the changed columns.
 */
@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SUB_FACILITY")
public class SubFacility {

	@Id
	@Column(name = "SUB_FACILITY_ID")
	private Long subFacilityId;

	@Column(name = "MASTER_FACILITY_ID")
	private Long masterFacilityId;

	@Column(name = "EHARS_FAC_UID")
	private String eharsFacUid;

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

	@Column(name = "COUNTY_FIPS")
	private String countyFips;

	@Column(name = "NPI")
	private String npi;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	/** Marks the sub facility as one that cannot be standardized (MASTER_FACILITY_ID 0). */
	public void unableToStandardize(String lastUpdatedBy, Timestamp lastUpdatedDate) {
		this.masterFacilityId = 0L;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
}
