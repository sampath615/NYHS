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
 * The code lists of the cluster partner screens (table CODED_PARTNER_REF_DATA), read-only.
 * <p>
 * <b>The table has no known key</b>: LOOKUP_CD is declared as the id, but it repeats across REF_COLUMNs; the table is
 * read through projections only. The codes are assumed to be text.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_PARTNER_REF_DATA")
public class CodedPartnerRefData {

	@Id
	@Column(name = "LOOKUP_CD")
	private String lookupCd;

	@Column(name = "REF_COLUMN")
	private String refColumn;

	@Column(name = "LOOKUP_NAME")
	private String lookupName;
}
