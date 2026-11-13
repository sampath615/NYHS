package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/** The sexes at birth (table CODED_SEX_AT_BIRTH), read-only. <b>SEX_CODE as the key is assumed.</b> */
@Entity
@Immutable
@Getter
@Table(name = "CODED_SEX_AT_BIRTH")
public class CodedSexAtBirth {

	@Id
	@Column(name = "SEX_CODE")
	private String sexCode;

	@Column(name = "SEX_DESCRIPTION")
	private String sexDescription;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
