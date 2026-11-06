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
 * Who viewed, saved or completed an RIA document when (table RIA_ACTIVITY; RiaRecordsReviewDAO). The table has no
 * known key; it is inserted by an HQL insert and read with projections only, so the key is only for the mapping:
 * <b>all four columns as the key are assumed</b>.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RIA_ACTIVITY")
@IdClass(RiaActivityId.class)
public class RiaActivity {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Id
	@Column(name = "ACTIVITY_DESC")
	private String activityDesc;

	@Id
	@Column(name = "ACTIVITY_BY")
	private Long activityBy;

	@Id
	@Column(name = "ACTIVITY_DATE")
	private Timestamp activityDate;
}
