package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An intervention of an RIA follow-up (table RIA_INTERVENTION), inserted by the RIA screen (RiaRecordsReviewDAO).
 * Inserted by an HQL insert and read with projections only, so the key is only for the mapping:
 * <b>(RIA_FOLLOW_UP_ID, INTERVENTION_CD) as the key is assumed</b>.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RIA_INTERVENTION")
@IdClass(RiaInterventionId.class)
public class RiaIntervention {

	@Id
	@Column(name = "RIA_FOLLOW_UP_ID")
	private Long riaFollowUpId;

	@Id
	@Column(name = "INTERVENTION_CD")
	private String interventionCd;

	@Column(name = "INTERVENTION_UID")
	private Long interventionUid;

	@Column(name = "INTERVENTION_DATE")
	private Timestamp interventionDate;

	@Column(name = "INTERVENTION_OTHER_TEXT")
	private String interventionOtherText;
}
