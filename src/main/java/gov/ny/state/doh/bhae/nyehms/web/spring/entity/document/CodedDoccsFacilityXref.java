package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The facility names DOCCS uses mapped to the facility names of CODED_DOCCS_FACILITY (table
 * CODED_DOCCS_FACILITY_XREF); only the columns the DOCCS import reads.
 * <p>
 * Read-only and read through JPQL projections only. <b>DOCCS_FACILITY_NAME as the key is assumed</b>; a name mapped
 * more than once gives one row each, as before.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_DOCCS_FACILITY_XREF")
public class CodedDoccsFacilityXref {

	@Id
	@Column(name = "DOCCS_FACILITY_NAME")
	private String doccsFacilityName;

	@Column(name = "FACILITY_NAME")
	private String facilityName;
}
