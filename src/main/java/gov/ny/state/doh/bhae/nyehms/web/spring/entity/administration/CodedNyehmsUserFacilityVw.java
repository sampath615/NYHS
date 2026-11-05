package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The facility groups a user can belong to (view CODED_NYEHMS_USER_FACILITY_VW). Read-only and read through
 * projections only (a key that repeats does not matter then). <b>FACILITY_GROUP_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_NYEHMS_USER_FACILITY_VW")
public class CodedNyehmsUserFacilityVw {

	@Id
	@Column(name = "FACILITY_GROUP_CD")
	private String facilityGroupCd;

	@Column(name = "FACILITY_DISP_NAME")
	private String facilityDispName;
}
