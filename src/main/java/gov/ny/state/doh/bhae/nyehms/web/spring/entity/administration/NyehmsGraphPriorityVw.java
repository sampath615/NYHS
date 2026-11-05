package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The landing-page graphs by role, in rank order (view NYEHMS_GRAPH_PRIORITY_VW). Read-only and read through
 * projections only (a key that repeats does not matter then). <b>GRAPH_DESCRIPTION as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_GRAPH_PRIORITY_VW")
public class NyehmsGraphPriorityVw {

	@Id
	@Column(name = "GRAPH_DESCRIPTION")
	private String graphDescription;

	@Column(name = "ROLE_CODE")
	private Long roleCode;

	@Column(name = "GRAPH_ROLE_RANK")
	private Long graphRoleRank;
}
