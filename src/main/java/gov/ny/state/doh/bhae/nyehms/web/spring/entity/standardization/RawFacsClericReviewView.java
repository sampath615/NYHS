package gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A raw facility for clerical (manual) review (view NYEHMS_RAW_FACS_CLERIC_RVW_VW): the columns RawSubFacilityDAO
 * reads. Read only through projections (a select of columns), so <b>RAW_SUB_FACILITY_ID as the key is assumed</b> only
 * to satisfy JPA: a repeating or null key does not matter, no entity is loaded.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_RAW_FACS_CLERIC_RVW_VW")
public class RawFacsClericReviewView {

	@Id
	@Column(name = "RAW_SUB_FACILITY_ID")
	private Long rawSubFacilityId;

	@Column(name = "FACILITY_TYPE_DESC")
	private String facilityTypeDesc;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_DEPARTMENT")
	private String facilityDepartment;

	@Column(name = "LEGACY_FACILITY_ID")
	private String legacyFacilityId;

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
}
