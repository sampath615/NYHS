package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The lab permit codes (table CODED_PERMITS).
 * <p>
 * Read-only. <b>PERMIT_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PERMITS")
public class CodedPermits {

	@Id
	@Column(name = "PERMIT_CD")
	private String permitCd;

	@Column(name = "PERMIT_DESCRIPTION")
	private String permitDescription;
}
