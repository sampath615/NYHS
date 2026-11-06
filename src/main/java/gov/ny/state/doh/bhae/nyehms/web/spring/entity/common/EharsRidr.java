package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The eHARS RIDR (same-as) records loaded into the main database (table EHARS_RIDR; RecordSearchReviewDAO counts
 * them, EharsDataLoaderDAO loads them). The table has no known key and may hold duplicates; it is only counted, never
 * loaded as an entity, so the key is only for the mapping: <b>(RIDR_STATE_CD, RIDR_STATENO, EHARS_STATENO) as the
 * key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "EHARS_RIDR")
@IdClass(EharsRidrId.class)
public class EharsRidr {

	@Id
	@Column(name = "RIDR_STATE_CD")
	private String ridrStateCd;

	@Id
	@Column(name = "RIDR_STATENO")
	private String ridrStateno;

	@Id
	@Column(name = "EHARS_STATENO")
	private String eharsStateno;
}
