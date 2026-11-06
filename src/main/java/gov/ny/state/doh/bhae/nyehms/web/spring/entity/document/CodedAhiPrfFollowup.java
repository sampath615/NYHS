package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The follow-up steps of an AHI alert (table CODED_AHI_PRF_FOLLOWUP), read-only.
 * <p>
 * <b>FOLLOW_UP_CODE as the key is assumed</b>; the list is read through a projection.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_AHI_PRF_FOLLOWUP")
public class CodedAhiPrfFollowup {

	@Id
	@Column(name = "FOLLOW_UP_CODE")
	private String followUpCode;

	@Column(name = "FOLLOW_UP_DESC")
	private String followUpDesc;

	@Column(name = "DROP_DOWN")
	private String dropDown;

	@Column(name = "SORT_ORDER")
	private BigDecimal sortOrder;
}
