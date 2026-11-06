package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A provider address as a source document named it (table RAW_PROVIDER_ADDRESS): the columns the provider
 * standardization reads and writes (ProviderDetailsDAO, SubProviderAndAddressDAO, SubProviderDAO).
 * <p>
 * Inserted with its key from RAW_PROVIDER_ADDRESS_SEQ (as the former insert took RAW_PROVIDER_ADDRESS_SEQ.NEXTVAL);
 * LAST_UPDATED_DATE / LAST_UPDATED_BY are not written on insert, so the database's defaults and triggers apply as
 * before. A change writes only the changed columns. <b>RAW_PROVIDER_ADDRESS_ID as the key is assumed</b> (filled from
 * the sequence). LAST_UPDATED_BY is read and written as text (the former update bound the user id text there).
 */
@Entity
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_PROVIDER_ADDRESS")
public class RawProviderAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RAW_PROVIDER_ADDRESS_SEQ")
	@SequenceGenerator(name = "RAW_PROVIDER_ADDRESS_SEQ", sequenceName = "RAW_PROVIDER_ADDRESS_SEQ", allocationSize = 1)
	@Column(name = "RAW_PROVIDER_ADDRESS_ID")
	private Long rawProviderAddressId;

	@Column(name = "SUB_PROVIDER_ADDRESS_ID")
	private Long subProviderAddressId;

	@Column(name = "COUNTY_FIPS")
	private String countyFips;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "PLUS4")
	private String plus4;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "PROVIDER_FNAME")
	private String providerFname;

	@Column(name = "PROVIDER_MNAME")
	private String providerMname;

	@Column(name = "PROVIDER_LNAME")
	private String providerLname;

	@Column(name = "PROVIDER_SUFFIX")
	private String providerSuffix;

	@Column(name = "NPI")
	private String npi;

	@Column(name = "LAST_UPDATED_DATE", insertable = false)
	private Timestamp lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY", insertable = false)
	private String lastUpdatedBy;

	/** Set when the provider was flipped to a facility (read only: ADAPDocumentDAO skips those addresses). */
	@Column(name = "FLIPPED_TO_FACILITY_DATE", insertable = false, updatable = false)
	private Timestamp flippedToFacilityDate;

	/** Marks the raw address as "unable to standardize": linked to the sub provider address 0. */
	public void unableToStandardize(Timestamp lastUpdatedDate, String lastUpdatedBy) {
		this.subProviderAddressId = 0L;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
}
