package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The bureaus (code table CODED_BUREAU_NAME). Read-only and read through projections only. <b>BUREAU_CD as the key
 * is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_BUREAU_NAME")
public class CodedBureauName {

	@Id
	@Column(name = "BUREAU_CD")
	private String bureauCd;

	@Column(name = "BUREAU_DESC")
	private String bureauDesc;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
