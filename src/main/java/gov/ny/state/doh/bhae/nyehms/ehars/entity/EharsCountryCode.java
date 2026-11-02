package gov.ny.state.doh.bhae.nyehms.ehars.entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An eHARS country code (eHARS database, table country_code). Read-only. <b>The key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "country_code")
public class EharsCountryCode {

	@Id
	@Column(name = "country_cd")
	private String countryCd;

	@Column(name = "descr")
	private String descr;
}
