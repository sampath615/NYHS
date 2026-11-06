package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

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
 * The drop-down codes of the RIA document fields (table CODED_RIA_DOC), read only (RiaRecordsReviewDAO).
 * <b>(REF_COLUMN, LOOKUP_CD) as the key is assumed</b>; read with projections only. The code and name are text.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_RIA_DOC")
@IdClass(CodedRiaDocId.class)
public class CodedRiaDoc {

	@Id
	@Column(name = "REF_COLUMN")
	private String refColumn;

	@Id
	@Column(name = "LOOKUP_CD")
	private String lookupCd;

	@Column(name = "LOOKUP_NAME")
	private String lookupName;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
