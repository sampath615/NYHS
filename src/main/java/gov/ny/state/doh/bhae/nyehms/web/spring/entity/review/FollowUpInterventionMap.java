package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Which interventions belong to which RIA follow-up code (table FOLLOW_UP_INTERVENTION_MAP), read only
 * (RiaRecordsReviewDAO). <b>(FOLLOW_UP_CD, INTERVENTION_CD) as the key is assumed</b>; read with projections only.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "FOLLOW_UP_INTERVENTION_MAP")
@IdClass(FollowUpInterventionMapId.class)
public class FollowUpInterventionMap {

	@Id
	@Column(name = "FOLLOW_UP_CD")
	private String followUpCd;

	@Id
	@Column(name = "INTERVENTION_CD")
	private String interventionCd;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
