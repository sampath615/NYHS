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
 * The files uploaded for a molecular cluster (table NYEHMS_UPLOAD_CLUSTER_FILES): the columns the cluster upload
 * writes and the cluster screens read.
 * <p>
 * <b>The table has no known key</b>: CLUSTER_ID_PK is declared as the id, but it repeats (one row per file); rows
 * are written by a JPQL insert and read through projections only. UPLOAD_DATE is the database's SYSDATE.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_UPLOAD_CLUSTER_FILES")
public class NyehmsUploadClusterFiles {

	@Id
	@Column(name = "CLUSTER_ID_PK")
	private Integer clusterIdPk;

	@Column(name = "REFERENCE_FILE_NAME")
	private String referenceFileName;

	@Column(name = "UPLOAD_UID")
	private Long uploadUid;

	@Column(name = "UPLOAD_DATE")
	private Timestamp uploadDate;

	@Column(name = "UPLOAD_TYPE")
	private String uploadType;

	@Column(name = "DESCRIPTION")
	private String description;
}
