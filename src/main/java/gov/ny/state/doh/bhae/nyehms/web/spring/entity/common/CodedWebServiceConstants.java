package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The HCS web service settings (table CODED_WEB_SERVICE_CONSTANTS): per application and stage, the token URL, the
 * client secret and the endpoint. Read-only, read through a projection (CommonDAO.getWebServiceConstants), so a key
 * that repeats does not matter. <b>ENDPOINTS as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_WEB_SERVICE_CONSTANTS")
public class CodedWebServiceConstants {

	@Id
	@Column(name = "ENDPOINTS")
	private String endpoints;

	@Column(name = "APP_NAME")
	private String appName;

	@Column(name = "APP_STAGE")
	private String appStage;

	@Column(name = "URL_ACCESS_TOKEN")
	private String urlAccessToken;

	@Column(name = "CLIENT_SECRET")
	private String clientSecret;
}
