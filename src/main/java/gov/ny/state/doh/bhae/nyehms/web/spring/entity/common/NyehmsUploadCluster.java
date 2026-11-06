package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The uploaded clusters (table NYEHMS_UPLOAD_CLUSTER): the columns the cluster upload writes and checks, the eHARS
 * import flag and the resolve date of the cluster partner review.
 * <p>
 * CLUSTER_ID_PK is filled by the database on insert (the former insert did not write it), so it is an identity here
 * (<b>assumed key</b>). CREATE_DATE is the database's SYSDATE on insert (left null here). CLUSTER_IDENTIFY_DATE is
 * text, as it was written (bound as VARCHAR). The flag and resolve columns are not written by the insert.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "NYEHMS_UPLOAD_CLUSTER")
public class NyehmsUploadCluster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLUSTER_ID_PK")
	private Integer clusterIdPk;

	@Column(name = "CLUSTER_ID")
	private String clusterId;

	@Column(name = "CLUSTER_TYPE")
	private String clusterType;

	@Column(name = "RELATEDNESS")
	private String relatedness;

	@Column(name = "TIMEFRAME_CODE")
	private String timeframeCode;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CLUSTER_IDENTIFY_DATE")
	private String clusterIdentifyDate;

	@Column(name = "EHARS_IMPORT_FLAG", insertable = false)
	private String eharsImportFlag;

	@Column(name = "EHARS_IMPORT_FLAG_SET_ID", insertable = false)
	private Long eharsImportFlagSetId;

	@Column(name = "EHARS_IMPORT_FLAG_SET_DATE", insertable = false)
	private Timestamp eharsImportFlagSetDate;

	@Column(name = "RESOLVED_BY", insertable = false)
	private Long resolvedBy;

	@Column(name = "RESOLVED_DATE", insertable = false)
	private Timestamp resolvedDate;
}
