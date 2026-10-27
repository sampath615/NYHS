package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The facilities of the death records (table CODED_DEATH_PFI), by PFI or NYC facility code; the columns the
 * death imports read.
 * <p>
 * Read-only and used in JPQL projections only. <b>PFI as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_DEATH_PFI")
public class CodedDeathPfi {

	@Id
	@Column(name = "PFI")
	private String pfi;

	@Column(name = "NYC_FAC_CD")
	private String nycFacCd;

	@Column(name = "FACILITY_TYPE")
	private String facilityType;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "FACILITY_PLUS4")
	private String facilityPlus4;

	@Column(name = "FACILITY_PHONE")
	private String facilityPhone;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;
}
