package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The resolutions of a LAFT follow-up (table CODED_LAFT_FOLLOWUP_RESOLUTION).
 * <p>
 * Read-only. <b>RESOLUTION_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAFT_FOLLOWUP_RESOLUTION")
public class CodedLaftFollowupResolution {

	@Id
	@Column(name = "RESOLUTION_ID")
	private Long resolutionId;

	@Column(name = "RESOLUTION_DESC")
	private String resolutionDesc;
}
