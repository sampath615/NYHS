package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A provider portal (ePRF out-of-care / DNLTC / SNP) upload record as received (table RAW_EPRF_OOC_UPLOAD); the columns
 * the EPRF OOC document screen, the file lists and the review-status change read and write.
 * <p>
 * Read through JPQL projections and changed by JPQL bulk updates only, so a key that repeats does not matter. <b>DOCUMENT_ID
 * as the key is assumed.</b> REVIEW_ID is written with the worker id (an int, as before). REVIEW_DT is only used by the
 * file list when it is asked for the raw table with status 17 (the former {@code ${tableName}} statement allowed it).
 */
@Entity
@Getter
@Table(name = "RAW_EPRF_OOC_UPLOAD")
public class RawEprfOocUpload {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PROV_ADDR")
	private String provAddr;

	@Column(name = "FACILITY_ADDR")
	private String facilityAddr;

	@Column(name = "UPLOAD_FILENAME")
	private String uploadFilename;

	@Column(name = "UPLOAD_DT")
	private Timestamp uploadDt;

	@Column(name = "UPLOAD_ID")
	private String uploadId;

	@Column(name = "FILE_TYPE_CD")
	private String fileTypeCd;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "REVIEW_DT")
	private Timestamp reviewDt;

	@Column(name = "REVIEW_ID")
	private Integer reviewId;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;
}
