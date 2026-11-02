package gov.ny.state.doh.bhae.nyehms.ehars.entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An eHARS code of a coded field (eHARS database, table lookup_code). Read-only. <b>The key (lookup_type, lookup_cd)
 * is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "lookup_code")
@IdClass(EharsLookupCodeId.class)
public class EharsLookupCode {

	@Id
	@Column(name = "lookup_type")
	private String lookupType;

	@Id
	@Column(name = "lookup_cd")
	private String lookupCd;

	@Column(name = "lookup_name")
	private String lookupName;

	@Column(name = "lookup_descr")
	private String lookupDescr;
}
