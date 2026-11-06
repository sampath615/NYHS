package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The uploaded cluster records (table RAW_CLUSTER_DOC): the columns the cluster upload writes and counts its errors by,
 * and the already uploaded state numbers of a cluster.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (each record gets a new document id). CREATE_DATE and PROCESS_DATE are the
 * database's SYSDATE on insert (left null here). IDENTIFICATION_DATE is text, as it was written (bound as VARCHAR).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAW_CLUSTER_DOC")
public class RawClusterDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "UPLOAD_NYEHMS_STATENO")
	private String uploadNyehmsStateno;

	@Column(name = "UPLOAD_CURRENT_NYEHMS_STATENO")
	private String uploadCurrentNyehmsStateno;

	@Column(name = "IDENTIFICATION_METHOD_CODE")
	private String identificationMethodCode;

	@Column(name = "IDENTIFICATION_DATE")
	private String identificationDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "CREATE_UID")
	private String createUid;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;

	@Column(name = "CLUSTER_ID_PK")
	private Integer clusterIdPk;

	@Column(name = "DEMO_DOCUMENT_ID")
	private Long demoDocumentId;
}
