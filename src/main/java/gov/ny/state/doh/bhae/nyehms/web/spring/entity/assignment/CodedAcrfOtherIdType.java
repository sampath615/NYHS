package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The other id types of the case report forms (table CODED_ACRF_OTHER_ID_TYPE); the codes are text.
 * <p>
 * Read-only, used in JPQL projections only. <b>OTHER_ID_TYPE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ACRF_OTHER_ID_TYPE")
public class CodedAcrfOtherIdType {

	@Id
	@Column(name = "OTHER_ID_TYPE")
	private String otherIdType;

	@Column(name = "OTHER_ID_TYPE_DESC")
	private String otherIdTypeDesc;

	@Column(name = "SORT_ORDER")
	private String sortOrder;
}
