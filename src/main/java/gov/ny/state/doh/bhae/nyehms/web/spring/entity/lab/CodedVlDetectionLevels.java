package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The lowest viral load detection levels (table CODED_VL_DETECTION_LEVELS).
 * <p>
 * Read-only. <b>DETECTION_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_VL_DETECTION_LEVELS")
public class CodedVlDetectionLevels {

	@Id
	@Column(name = "DETECTION_CD")
	private String detectionCd;

	@Column(name = "DETECTION_DESC")
	private String detectionDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
