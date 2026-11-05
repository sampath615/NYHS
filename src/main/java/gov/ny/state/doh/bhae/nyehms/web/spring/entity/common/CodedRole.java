package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The worker roles (code table CODED_ROLE). Read-only. <b>ROLE_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ROLE")
public class CodedRole {

	@Id
	@Column(name = "ROLE_CODE")
	private Long roleCode;

	@Column(name = "ROLE_DESCRIPTION")
	private String roleDescription;

	@Column(name = "IS_EXPANDABLE")
	private String isExpandable;
}
