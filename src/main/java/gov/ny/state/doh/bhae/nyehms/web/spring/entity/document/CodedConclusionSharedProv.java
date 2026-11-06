package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The care statuses of the provider portal follow-up (table CODED_CONCLUSION_SHARED_PROV).
 * <p>
 * Read-only, read through JPQL projections only. <b>CONCLUSION_ID as the key is assumed</b>, and assumed to be a number
 * (the former result map read it as DECIMAL); it is read as a BigDecimal, as JDBC getObject read it into the list maps.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_CONCLUSION_SHARED_PROV")
public class CodedConclusionSharedProv {

	@Id
	@Column(name = "CONCLUSION_ID")
	private BigDecimal conclusionId;

	@Column(name = "CONCLUSION_DESC")
	private String conclusionDesc;
}
