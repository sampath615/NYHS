package gov.ny.state.doh.bhae.nyehms.web.spring.entity.person;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The origins a flagged pair of state numbers can come from (table CODED_MERGE_ORIGINS). Read-only, read in a JPQL
 * projection by {@code PersonDAO.getOriginCategories} (the merge reports read the table natively). <b>ORIGIN_CODE as
 * the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_MERGE_ORIGINS")
public class CodedMergeOrigins {

	@Id
	@Column(name = "ORIGIN_CODE")
	private Long originCode;

	@Column(name = "ORIGIN_DESC")
	private String originDesc;

	@Column(name = "ORIGIN_TYPE")
	private String originType;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
