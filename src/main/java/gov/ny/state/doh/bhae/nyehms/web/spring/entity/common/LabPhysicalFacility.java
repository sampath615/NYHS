package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A laboratory (table LAB_PHYSICAL_FACILITY): PFI, CLIA number, name, address, LAFT staff, approval status and active
 * flag (the columns the case report forms' laboratory list and the LAFT / CLEP statements read).
 * <p>
 * Read-only, used in JPQL projections and joins only. <b>CLIA as the key is assumed.</b> PFI is mapped as text: the
 * former LAFT / CLEP statements compared it with text as it was ({@code ls.pfi = lpf.pfi},
 * {@code MSHLABPFI = TO_CHAR(l.PFI)}), so the JPQL compares it the same way and the SQL stays the same whatever the
 * column's type is.
 */
@Entity
@Immutable
@Getter
@Table(name = "LAB_PHYSICAL_FACILITY")
public class LabPhysicalFacility {

	@Id
	@Column(name = "CLIA")
	private String clia;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "LAB_NAME")
	private String labName;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "LAFT_STAFF")
	private String laftStaff;

	@Column(name = "APPROVAL_STATUS_CODE")
	private Long approvalStatusCode;

	@Column(name = "FLAG_ACTIVE")
	private String flagActive;
}
