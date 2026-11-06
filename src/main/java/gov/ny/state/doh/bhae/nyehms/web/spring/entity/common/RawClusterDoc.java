package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The uploaded cluster records (table RAW_CLUSTER_DOC); the columns the cluster upload counts its errors by (the
 * cluster and the process status).
 * <p>
 * Read-only and only counted (JPQL count), so a key that repeats does not matter. <b>CLUSTER_ID_PK as the key is
 * assumed</b> (it is not unique: several records per cluster).
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_CLUSTER_DOC")
public class RawClusterDoc {

	@Id
	@Column(name = "CLUSTER_ID_PK")
	private Integer clusterIdPk;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;
}
