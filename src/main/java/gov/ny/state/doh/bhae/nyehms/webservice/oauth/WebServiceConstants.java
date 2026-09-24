package gov.ny.state.doh.bhae.nyehms.webservice.oauth;

public class WebServiceConstants {

	private String baseUrl;
	private final String appId = "nyehms";
	private final String tokenUri ="/token";
	private final String clientId ="nyehms";
	private String clientSecret;
	private String hcsEndPointUri;
	

	
	
	public String getAppId() {
		return appId;
	}


	public String getBaseUrl() {
		return baseUrl;
	}
	
	
	public String getTokenUri() {
		return tokenUri;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	
	public String getHcsEndPointUri() {
		return hcsEndPointUri;
	}
	
	
	@Override
	public String toString() {
		return "WebServiceConstants [baseUrl=" + baseUrl + ", tokenUri=" + tokenUri + ", clientId=" + clientId
				+ ", hcsEndPointUri=" + hcsEndPointUri + "]";
	}
	
	
	
} 
 