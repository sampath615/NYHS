package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The cluster person records of the SAS database owner (table NYEHMS_SASDB_OWNER.ER_PERSON_CLUSTERS): a document's
 * fuzzy cluster, match code and review. Read through projections and changed by a bulk JPQL update (the clerical
 * review's not-a-match, ClericalReviewDAO). <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ER_PERSON_CLUSTERS", schema = "NYEHMS_SASDB_OWNER")
public class ErPersonClusters {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "FUZZY_CLUSTER_ID")
	private Long fuzzyClusterId;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "REVIEW_UID")
	private Long reviewUid;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;
}
