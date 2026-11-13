package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The HIV test facility types (table CODED_HIV_TEST_FACILITY), read-only. <b>FACILITY_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_HIV_TEST_FACILITY")
public class CodedHivTestFacility {

	@Id
	@Column(name = "FACILITY_CODE")
	private String facilityCode;

	@Column(name = "FACILITY_DESC")
	private String facilityDesc;
}
