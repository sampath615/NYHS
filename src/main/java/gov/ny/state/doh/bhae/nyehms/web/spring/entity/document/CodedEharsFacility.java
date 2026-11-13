package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The eHARS facility types (table CODED_EHARS_FACILITY): the EPRF ones have an EPRF sort order. Read-only. <b>FACILITY_TYPE_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_EHARS_FACILITY")
public class CodedEharsFacility {

	@Id
	@Column(name = "FACILITY_TYPE_CODE")
	private String facilityTypeCode;

	@Column(name = "EPRF_FACILITY_TYPE_DESCRIPTION")
	private String eprfFacilityTypeDescription;

	@Column(name = "EPRF_SORT_ORDER")
	private Long eprfSortOrder;
}
