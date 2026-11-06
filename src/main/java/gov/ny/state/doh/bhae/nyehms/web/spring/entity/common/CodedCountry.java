package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The countries (table CODED_COUNTRY); only the columns the DOCCS import reads (the country code of a country name).
 * <p>
 * Read-only and read through JPQL projections only. <b>COUNTRY_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_COUNTRY")
public class CodedCountry {

	@Id
	@Column(name = "COUNTRY_CODE")
	private String countryCode;

	@Column(name = "COUNTRY_DESC")
	private String countryDesc;
}
