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
 * The eHARS ethnicity codes (table CODED_EHARS_ETHNICITY), read-only. <b>EHARS_ETH_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_EHARS_ETHNICITY")
public class CodedEharsEthnicity {

	@Id
	@Column(name = "EHARS_ETH_CODE")
	private String eharsEthCode;

	@Column(name = "EHARS_ETH_DESC")
	private String eharsEthDesc;
}
