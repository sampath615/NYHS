package gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A standardized provider address (table SUB_PROVIDER_ADDRESS): the columns SubProviderDAO changes. Not inserted here
 * (the PKG_STANDARDIZATION procedures create the rows). A change writes only the changed columns.
 * <b>SUB_PROVIDER_ADDRESS_ID as the key is assumed.</b> LAST_UPDATED_BY is read and written as text (a number column
 * takes the worker id text by the database's implicit conversion, as it took the bound number before).
 */
@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SUB_PROVIDER_ADDRESS")
public class SubProviderAddress {

	@Id
	@Column(name = "SUB_PROVIDER_ADDRESS_ID")
	private Long subProviderAddressId;

	@Column(name = "PROVIDER_ID")
	private Long providerId;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	/** Marks the address as "unable to standardize": linked to the provider 0. */
	public void unableToStandardize(Timestamp lastUpdatedDate, String lastUpdatedBy) {
		this.providerId = 0L;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
}
