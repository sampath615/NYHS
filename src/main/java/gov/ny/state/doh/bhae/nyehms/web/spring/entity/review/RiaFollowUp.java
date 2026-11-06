package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A follow-up of an RIA document (table RIA_FOLLOW_UP), inserted by the RIA screen and read with its interventions
 * (RiaRecordsReviewDAO). The key comes from RIA_FOLLOWUP_SEQ, as the former insert took it; CREATE_DATE is the
 * database's SYSDATE (left null on insert).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RIA_FOLLOW_UP")
public class RiaFollowUp {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RIA_FOLLOWUP_SEQ")
	@SequenceGenerator(name = "RIA_FOLLOWUP_SEQ", sequenceName = "RIA_FOLLOWUP_SEQ", allocationSize = 1)
	@Column(name = "RIA_FOLLOW_UP_ID")
	private Long riaFollowUpId;

	@Column(name = "RIA_DOC_ID")
	private Long riaDocId;

	@Column(name = "FOLLOW_UP_CD")
	private String followUpCd;

	@Column(name = "FOLLOWUP_OTHER_TEXT")
	private String followupOtherText;

	@Column(name = "CREATE_UID")
	private Long createUid;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
}
