package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The LIS vendors of the labs (table CODED_LAB_LIS_VENDOR).
 * <p>
 * Read-only. <b>LIS_VENDOR_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAB_LIS_VENDOR")
public class CodedLabLisVendor {

	@Id
	@Column(name = "LIS_VENDOR_CD")
	private String lisVendorCd;

	@Column(name = "LIS_VENDOR_DESC")
	private String lisVendorDesc;
}
