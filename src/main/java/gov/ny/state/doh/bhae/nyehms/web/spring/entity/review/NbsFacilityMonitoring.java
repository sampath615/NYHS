package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The perinatal (NBS) facility monitoring figures of a facility and birth quarter (NBS_FACILITY_MONITORING): the
 * columns the facility list of the perinatal letters reads (PerinatalReviewDAO.getFacilityNamesByBirthCriteria).
 * <p>
 * Read-only, and only read through projections: <b>PFI as the key is assumed</b> only to satisfy JPA (a facility has
 * one row per birth quarter, so the key repeats; that does not matter as no entity is loaded). PFI, FACILITY_NAME
 * and BIRTHQTR are text (the letter code reads them as Strings); BIRTHYR is compared with the text given, as before.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NBS_FACILITY_MONITORING")
public class NbsFacilityMonitoring {

	@Id
	@Column(name = "PFI")
	private String pfi;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "BIRTHQTR")
	private String birthqtr;

	@Column(name = "BIRTHYR")
	private String birthyr;
}
