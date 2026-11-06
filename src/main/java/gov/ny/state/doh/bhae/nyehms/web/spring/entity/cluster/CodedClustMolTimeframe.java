package gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The molecular cluster time frames (table CODED_CLUST_MOL_TIMEFRAME), read-only. <b>TIMEFRAME_CODE as the key is
 * assumed.</b>
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_CLUST_MOL_TIMEFRAME")
public class CodedClustMolTimeframe {

	@Id
	@Column(name = "TIMEFRAME_CODE")
	private String timeframeCode;

	@Column(name = "TIMEFRAME_DESC")
	private String timeframeDesc;
}
