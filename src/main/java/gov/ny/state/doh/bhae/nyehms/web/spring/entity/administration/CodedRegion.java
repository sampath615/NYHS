package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The regions (code table CODED_REGION). Read-only and read through projections only. <b>REGION_CODE as the key is
 * assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_REGION")
public class CodedRegion {

	@Id
	@Column(name = "REGION_CODE")
	private String regionCode;

	@Column(name = "REGION_DESCRIPTION")
	private String regionDescription;

	@Column(name = "ASSIGNMENT_TYPE_CODE")
	private String assignmentTypeCode;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
