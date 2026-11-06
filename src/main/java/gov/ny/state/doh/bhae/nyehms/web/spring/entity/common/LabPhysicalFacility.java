package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A laboratory (table LAB_PHYSICAL_FACILITY): PFI, CLIA number, name, address, LAFT staff, approval status, flags and
 * the last change (the columns the case report forms' laboratory list, the LAFT / CLEP statements and the lab
 * maintenance screens of LabReportsDAO read and write).
 * <p>
 * Used in JPQL projections and joins, inserted by a JPQL insert and changed by bulk JPQL updates (LabReportsDAO); never
 * loaded and saved as an entity. <b>PFI as the key is assumed</b> (one CLIA has several PFIs: the lab search groups the
 * PFIs by CLIA, and a PFI is checked for existence before it is added). PFI is mapped as text: the former LAFT / CLEP
 * statements compared it with text as it was ({@code ls.pfi = lpf.pfi}, {@code MSHLABPFI = TO_CHAR(l.PFI)}) and the lab
 * maintenance statements bound it as text, so the JPQL compares and binds it the same way and the SQL stays the same
 * whatever the column's type is.
 */
@Entity
@Getter
@Table(name = "LAB_PHYSICAL_FACILITY")
public class LabPhysicalFacility {

	@Id
	@Column(name = "PFI")
	private String pfi;

	@Column(name = "CLIA")
	private String clia;

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

	@Column(name = "FLAG_SERVES_NYC")
	private String flagServesNyc;

	@Column(name = "FLAG_BLOOD_BANK")
	private String flagBloodBank;

	@Column(name = "LOW_VOLUME")
	private String lowVolume;

	@Column(name = "PHY_OFF_LAB")
	private String phyOffLab;

	@Column(name = "REFERS_DTA_TESTING_TO")
	private String refersDtaTestingTo;

	@Column(name = "PERFORMS_DTA")
	private String performsDta;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;
}
