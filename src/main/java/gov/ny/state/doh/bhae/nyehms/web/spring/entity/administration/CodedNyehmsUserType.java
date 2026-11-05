package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The user types (code table CODED_NYEHMS_USER_TYPE). Read-only and read through projections only.
 * <b>USER_TYPE_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_NYEHMS_USER_TYPE")
public class CodedNyehmsUserType {

	@Id
	@Column(name = "USER_TYPE_CODE")
	private String userTypeCode;

	@Column(name = "USER_TYPE_DESC")
	private String userTypeDesc;
}
