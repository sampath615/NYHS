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
 * The standardized cluster records (table STD_CLUSTER_DOC): the columns the cluster upload writes, the cluster screens
 * read and the cluster partner review changes (the PS message reasons).
 * <p>
 * <b>DOCUMENT_ID as the key is assumed.</b> CREATE_DATE and PROCESS_DATE are the database's SYSDATE on insert (left
 * null here). DOB is written as before through the database function FN_TO_DATE(text, 'MM/DD/YYYY'); it is not read
 * through this entity (only through projections of other attributes). The review columns are not written by the insert
 * (database defaults stay).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_CLUSTER_DOC")
public class StdClusterDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "NYEHMS_STATENO_AT_UPLOAD")
	private String nyehmsStatenoAtUpload;

	@Column(name = "IDENTIFICATION_METHOD_CODE")
	private String identificationMethodCode;

	@Column(name = "IDENTIFICATION_DATE")
	private String identificationDate;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	/** Written as FN_TO_DATE(text, 'MM/DD/YYYY'), as before. */
	@ColumnTransformer(write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "DOB")
	private String dob;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "DEMO_DATA_SOURCE")
	private String demoDataSource;

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

	@Column(name = "EHARS_STATENO")
	private String eharsStateno;

	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "DEMO_DOCUMENT_ID")
	private Long demoDocumentId;

	@Column(name = "RSN_NO_PS_MSG", insertable = false)
	private String rsnNoPsMsg;

	@Column(name = "RSN_PS_MSG", insertable = false)
	private String rsnPsMsg;

	@Column(name = "OTHER_REASON", insertable = false)
	private String otherReason;

	@Column(name = "REVIEW_ID", insertable = false)
	private Long reviewId;

	@Column(name = "REVIEW_DATE", insertable = false)
	private Timestamp reviewDate;
}
