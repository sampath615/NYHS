package gov.ny.state.doh.bhae.nyehms.web.spring.entity.cluster;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The state numbers added by hand to a BHAE space-time cluster (table BHAE_TS_CLUSTER_MANUAL), written only.
 * <p>
 * <b>The table has no known key</b>: CLUSTER_ID_PK is declared as the id, but it repeats; rows are written by a
 * JPQL insert only. CREATE_DATE is the database's SYSDATE.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BHAE_TS_CLUSTER_MANUAL")
public class BhaeTsClusterManual {

	@Id
	@Column(name = "CLUSTER_ID_PK")
	private Integer clusterIdPk;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "CREATE_UID")
	private Long createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "USER_COMMENT")
	private String userComment;
}
