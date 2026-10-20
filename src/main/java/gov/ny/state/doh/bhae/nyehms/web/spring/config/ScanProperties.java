package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Scanned-document SFTP storage, bound from nyehms.scan.* .
 */
@ConfigurationProperties(prefix = "nyehms.scan")
public record ScanProperties(
        /* SFTP folder for encrypted scans; differs per environment */
        String encryptLocation,
        /* SFTP password, encoded (CommonUtil.decryptValue). Can be overridden with env var NYEHMS_SCAN_PASSWORD */
        String password) {
}
