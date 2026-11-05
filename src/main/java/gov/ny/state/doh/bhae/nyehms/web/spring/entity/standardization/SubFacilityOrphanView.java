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
 * A sub facility without a master facility (view NYEHMS_SUB_FACILITY_ORPHAN_VW), one row per phone: the columns
 * OrphanSubFacilityDAO reads. Read only through projections (a select of columns), so <b>SUB_FACILITY_ID as the key
 * is assumed</b> only to satisfy JPA: it repeats (one row per phone), no entity is loaded.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_SUB_FACILITY_ORPHAN_VW")
public class SubFacilityOrphanView {

	@Id
	@Column(name = "SUB_FACILITY_ID")
	private Long subFacilityId;

	@Column(name = "FACILITY_TYPE_CODE")
	private String facilityTypeCode;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_DEPARTMENT")
	private String facilityDepartment;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "PHONE")
	private String phone;
}
