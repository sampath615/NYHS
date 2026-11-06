package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The uploaded clusters (table NYEHMS_UPLOAD_CLUSTER); the column the cluster upload checks (CLUSTER_ID exists).
 * <p>
 * Read-only and only counted (JPQL count), so a key that repeats does not matter. <b>CLUSTER_ID as the key is
 * assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_UPLOAD_CLUSTER")
public class NyehmsUploadCluster {

	@Id
	@Column(name = "CLUSTER_ID")
	private String clusterId;
}
