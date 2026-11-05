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
 * A master facility (table MASTER_FACILITY): the columns MasterFacilityDetailsDAO reads; master facilities are created
 * and changed by PKG_STANDARDIZATION procedures. <b>MASTER_FACILITY_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MASTER_FACILITY")
public class MasterFacility {

	@Id
	@Column(name = "MASTER_FACILITY_ID")
	private Long masterFacilityId;

	@Column(name = "MASTER_FACILITY_NAME")
	private String masterFacilityName;
}
