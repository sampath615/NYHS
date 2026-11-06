package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The internal conclusions of the provider portal follow-up (table CODED_PP_INQURIES_INT_CONCL).
 * <p>
 * Read-only, read through JPQL projections only. <b>INTERNAL_CONCLUSION_ID as the key is assumed</b>, and assumed to be a
 * number; it is read as a BigDecimal, as JDBC getObject read it into the list maps.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PP_INQURIES_INT_CONCL")
public class CodedPpInquriesIntConcl {

	@Id
	@Column(name = "INTERNAL_CONCLUSION_ID")
	private BigDecimal internalConclusionId;

	@Column(name = "INTERNAL_CONCLUSION_DESC")
	private String internalConclusionDesc;
}
