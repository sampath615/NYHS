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
 * The RIA follow-up codes by program (BHAE, OMD, PS) (table CODED_RIA_FOLLOW_UP_CODES), read only
 * (RiaRecordsReviewDAO). <b>FOLLOW_UP_CD as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_RIA_FOLLOW_UP_CODES")
public class CodedRiaFollowUpCodes {

	@Id
	@Column(name = "FOLLOW_UP_CD")
	private String followUpCd;

	@Column(name = "FOLLOW_UP_DESC")
	private String followUpDesc;

	@Column(name = "PROGRAM_TYPE")
	private String programType;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
