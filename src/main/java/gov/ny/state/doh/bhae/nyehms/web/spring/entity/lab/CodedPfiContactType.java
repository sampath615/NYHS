package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The types of lab contact (table CODED_PFI_CONTACT_TYPE).
 * <p>
 * Read-only. <b>CONTACT_TYPE_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_PFI_CONTACT_TYPE")
public class CodedPfiContactType {

	@Id
	@Column(name = "CONTACT_TYPE_CODE")
	private String contactTypeCode;

	@Column(name = "CONTACT_TYPE_DESC")
	private String contactTypeDesc;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
