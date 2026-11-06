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
 * The cluster person staging records of the SAS database owner (table NYEHMS_SASDB_OWNER.ER_PERSON_CLUSTERS_STAGE);
 * the columns the (unused) discard of the cluster partner review changes.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed.</b> Changed by a JPQL bulk update only.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ER_PERSON_CLUSTERS_STAGE", schema = "NYEHMS_SASDB_OWNER")
public class ErPersonClustersStage {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "REVIEW_UID")
	private Long reviewUid;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;
}
