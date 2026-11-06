package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The RIA intervention codes (table CODED_RIA_INTERVENTION_CODES), read only (RiaRecordsReviewDAO).
 * <b>INTERVENTION_CD as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_RIA_INTERVENTION_CODES")
public class CodedRiaInterventionCodes {

	@Id
	@Column(name = "INTERVENTION_CD")
	private String interventionCd;

	@Column(name = "INTERVENTION_DESC")
	private String interventionDesc;
}
