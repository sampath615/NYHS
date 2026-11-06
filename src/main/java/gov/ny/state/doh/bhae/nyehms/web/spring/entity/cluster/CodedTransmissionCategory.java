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
 * The transmission categories (table CODED_TRANSMISSION_CATEGORY), read-only. <b>CATEGORY_CODE as the key is
 * assumed.</b>
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_TRANSMISSION_CATEGORY")
public class CodedTransmissionCategory {

	@Id
	@Column(name = "CATEGORY_CODE")
	private String categoryCode;

	@Column(name = "CATEG_DESC_SHORT")
	private String categDescShort;
}
