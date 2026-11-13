package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The telephone types (table CODED_TELEPHONE_TYPE), read-only. <b>ANS_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_TELEPHONE_TYPE")
public class CodedTelephoneType {

	@Id
	@Column(name = "ANS_CODE")
	private String ansCode;

	@Column(name = "ANS_DESC")
	private String ansDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
