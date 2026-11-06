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
 * The criteria of a BHAE space-time cluster (table BHAE_TS_CLUSTER_CRITERIA): the columns the cluster upload writes
 * and retires and the cluster screens read.
 * <p>
 * <b>The table has no known key</b>: CLUSTER_ID_PK is declared as the id, but it repeats (one row per criteria
 * version); rows are written by JPQL insert / bulk update and read through projections only. DX_START_DATE,
 * DX_END_DATE, AGE_FROM and AGE_TO are text, as they were written (bound as VARCHAR). CRITERIA_CREATE_DATE and
 * CLUST_END_DATE are the database's SYSDATE.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BHAE_TS_CLUSTER_CRITERIA")
public class BhaeTsClusterCriteria {

	@Id
	@Column(name = "CLUSTER_ID_PK")
	private Integer clusterIdPk;

	@Column(name = "CLUSTER_COUNTIES")
	private String clusterCounties;

	@Column(name = "DX_START_DATE")
	private String dxStartDate;

	@Column(name = "DX_END_DATE")
	private String dxEndDate;

	@Column(name = "CLUSTER_DESC")
	private String clusterDesc;

	@Column(name = "CRITERIA_CREATE_UID")
	private Long criteriaCreateUid;

	@Column(name = "CRITERIA_CREATE_DATE")
	private Timestamp criteriaCreateDate;

	@Column(name = "TRANS_RISK")
	private String transRisk;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "RACE")
	private String race;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "AGE_FROM")
	private String ageFrom;

	@Column(name = "AGE_TO")
	private String ageTo;

	@Column(name = "CLUST_END_DATE")
	private Timestamp clustEndDate;

	@Column(name = "CLUST_END_UID")
	private Long clustEndUid;
}
