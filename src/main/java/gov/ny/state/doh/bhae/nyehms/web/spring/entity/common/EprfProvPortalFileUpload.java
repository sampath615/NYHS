package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The files uploaded through the provider portal (table EPRF_PROV_PORTAL_FILE_UPLOAD); the columns the provider portal
 * reports read: who uploaded a file and its file type.
 * <p>
 * Read-only and read through JPQL subqueries only. <b>FILENAME as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "EPRF_PROV_PORTAL_FILE_UPLOAD")
public class EprfProvPortalFileUpload {

	@Id
	@Column(name = "FILENAME")
	private String filename;

	@Column(name = "UPLOAD_UID")
	private String uploadUid;

	@Column(name = "FILE_TYPE")
	private String fileType;
}
