package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The RIDR information of case report forms (table RAW_ACRF_RIDR_INFO), read-only; the columns the cluster demo
 * document search uses.
 * <p>
 * <b>The table has no known key</b>: ACRF_DOC_ID is declared as the id, but it may repeat; the table is read
 * through projections only.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RAW_ACRF_RIDR_INFO")
public class RawAcrfRidrInfo {

	@Id
	@Column(name = "ACRF_DOC_ID")
	private Long acrfDocId;

	@Column(name = "DUPLICATE_STATUS")
	private Integer duplicateStatus;

	@Column(name = "STATE_CD")
	private String stateCd;

	@Column(name = "STATENO")
	private String stateno;
}
