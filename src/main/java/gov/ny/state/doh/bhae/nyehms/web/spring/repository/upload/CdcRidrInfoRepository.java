package gov.ny.state.doh.bhae.nyehms.web.spring.repository.upload;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.upload.CdcRidrInfo;

/**
 * The rows of the uploaded CDC RIDR files (replaces the raw JDBC batch insert in FileUploadService).
 */
public interface CdcRidrInfoRepository extends JpaRepository<CdcRidrInfo, Long> {
}
