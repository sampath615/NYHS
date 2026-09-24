package gov.ny.state.doh.bhae.nyehms.webservice.oauth;

import java.util.Objects;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.HCSUserInfo;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.HCSUserPermInfo;
import gov.ny.state.doh.bhae.nyehms.webservice.webclient.filter.FilterFactory;
import reactor.core.publisher.Mono;

/**
 * A RESTful client for retrieving OAuth Access Tokens via
 * web service call. This will generally be called as a precursor
 * to some other web service which requires an authentication header.
 *
 **/
@Service
//@Slf4j
public class AccessTokenClient {

	private static final String NL = "\n\t";

	private WebServiceConstants config;

	private WebClient webClient;

	private static Logger log = LoggerFactory.getLogger(AccessTokenClient.class);
	
	
	@Autowired
	public AccessTokenClient(WebServiceConstants config) {	
		
		this.config    = Objects.requireNonNull(config, "Must supply WebServiceConstants.");
		this.webClient = Objects.requireNonNull(WebClient.builder(), "Must supply a WebClient.Builder.")
																 	.baseUrl(config.getBaseUrl())
																 	.filter(FilterFactory.errorHandlingFilter())
																 	.build();
		log.info("Config class name: {}", config.getClass().getName()); // Useful for identifying project setup issues
	}
	
	/**
	 * Makes a synchronous (blocking) call to retrieve an OAuth access token.
	 * 
	 * @return the Access Token
	 *////
	public  AccessToken getAccessToken() {
		return getAccessToken(config.getClientId(), config.getClientSecret());
	}
	
	/**
	 * @param clientId The user ID for service authentication
	 * @param clientSecret The password for service authentication
	 * @see AccessTokenClient#getAccessToken()
	 */
	private AccessToken getAccessToken(String clientId, String clientSecret) {

		StringBuilder buf = null;
		final boolean isDebug = log.isDebugEnabled();
		AccessToken token = null;
		if (isDebug) {
			buf = new StringBuilder().append(NL).append("clientId=").append(clientId).append(NL).append("config=")
					.append(config);
		}
		try {

			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("client_id", clientId);
			params.add("client_secret", clientSecret); // Don't log the secret.
			params.add("grant_type", "client_credentials");

			token = webClient.post()
							.uri(config.getTokenUri())
							.accept(MediaType.APPLICATION_JSON)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.bodyValue(params)
							.retrieve()
							.bodyToMono(AccessToken.class).block();
			
			
			
			
			if(token != null) log.info("Token generated successfully");  
		} catch(WebClientResponseException webEx){
			log.error("error while fetching token - {} {}", webEx.getRawStatusCode(), webEx.getStatusText());
			log.error("exception while calling token occured - {}", webEx.getMessage());
		} catch(Exception e) {
			log.error("error while fetching token - {}", e.getMessage());		
		}finally {
			if (isDebug) {
				log.debug(buf.toString());
			}
			
		}
		
		return token;
	}
	
	public HCSUserInfo getHCSUserInfo(String userId, AccessToken token){		
		StringBuilder buf = null; 
		final boolean isDebug = log.isDebugEnabled();
		if (isDebug) {buf = new StringBuilder().append(NL).append("token =").append(token.getAccess_token()).append(NL).append("userId =").append(userId);}
		HCSUserInfo userInfo = null;
		try{
			
			userInfo  =  webClient.get()
						 	.uri(uriBuilder -> uriBuilder.path(config.getHcsEndPointUri())
							.queryParam("userid", userId)
							.build())
					        .headers(h -> h.setBearerAuth(token.getAccess_token()) )
					        .retrieve() // method directly performs the HTTP request and retrieve the response body
					        .bodyToMono(HCSUserInfo.class) //bodyToMono() and bodyToFlux() methods always expect a response body of a given class type.
					       /* .onErrorResume(WebClientResponseException.class,
					        		ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex))*/
					        .block();
						
					if (isDebug) {buf.append(NL).append("Fetched User Info details=").append(userInfo);}
					log.error("Fetched User Information from HCS for - {}", userInfo.getUserId());
		} catch(WebClientResponseException webEx) {
			if (webEx.getRawStatusCode() == 404 && webEx.getStatusText().equalsIgnoreCase("NOT FOUND")) {
				log.error("User- {}  is not found in HCS portal.", userId);
				//Mono.empty();  //deliberately no op
			}else {
				//Mono.error(webEx);
				log.error("Error occured: {}", webEx.getMessage());
			}
		} catch (Exception ex) {
			log.error("Error occured: {}", ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			if (isDebug) {log.debug(buf.toString());}
		}
		
		
		return userInfo;		
	}

	public HCSUserPermInfo getHCSUserPermInfo(String hcsUserId,String appId, AccessToken token) {
		StringBuilder buf = null; 
		final boolean isDebug = log.isDebugEnabled();
		if (isDebug) {buf = new StringBuilder().append(NL).append("token =").append(token.getAccess_token()).append(NL).append("userId =").append(hcsUserId);}
		HCSUserPermInfo userPermInfo = null;
		
		try {
			
			userPermInfo =   webClient
								.get()
								.uri(uriBuilder -> uriBuilder.path(config.getHcsEndPointUri())
								.queryParam("userid", hcsUserId)
								.queryParam("appid", appId)
								.build())
								.accept(MediaType.APPLICATION_JSON)
								.headers(h -> h.setBearerAuth(token.getAccess_token()))
								.retrieve()
								.bodyToMono(HCSUserPermInfo.class)
								.block();
								
			
			if (isDebug) {buf.append(NL).append("Fetched User Info details=").append(userPermInfo);}
			
		}  catch(WebClientResponseException webEx) {
			if (webEx.getRawStatusCode() == 404 && webEx.getStatusText().equalsIgnoreCase("NOT FOUND")) {
				log.info("User: {} has no access to nyehms application. Creating Permission", hcsUserId);
			    //Mono.empty();  //deliberately no op
			}else {
				log.error("Error Occured while fetching nyehms application access permission- {}", webEx.getMessage());
				//Mono.error(webEx);
			}
		} catch (Exception ex) {
			log.error("Error Occured while fetching nyehms application access permission- {}", ex.getMessage());
		} finally {
			if (isDebug) {log.debug(buf.toString());}
		}
		return userPermInfo;		
	}

	
	public JSONObject addNyehmsPermForUser(HCSUserPermInfo userPermInfo, String appNameAbbrevation,
			AccessToken token) {
		StringBuilder buf = null; 
		final boolean isDebug = log.isDebugEnabled();
		if (isDebug) {buf = new StringBuilder().append(NL).append("token =").append(token.getAccess_token()).append(NL).append("userId =").append(userPermInfo.getEcommerceId());}
		JSONObject addUserPermJsonObj =  new JSONObject();
		
		try {
			
			addUserPermJsonObj = webClient
				    .post()
					.uri(uriBuilder -> uriBuilder.path(config.getHcsEndPointUri())
				    .queryParam("userid", userPermInfo.getEcommerceId())
					.queryParam("appid", appNameAbbrevation)
					.build())
					.accept(MediaType.APPLICATION_JSON)
					.headers(h -> h.setBearerAuth(token.getAccess_token()))
					.body(Mono.just(userPermInfo), HCSUserPermInfo.class)
					.retrieve()
					.bodyToMono(JSONObject.class)
					.block();
			
			if (isDebug) {buf.append(NL).append("Fetched User Info details=").append(addUserPermJsonObj);}
			
		} catch(WebClientResponseException webEx) {
			log.error("error while adding user permission {}", webEx.getMessage());
		} catch (Exception ex) {
			//ex.printStackTrace();
			//throw new RuntimeException(ex);
			log.error("error while adding user permission {}", ex.getMessage());
		} finally {
			if (isDebug) {log.debug(buf.toString());}
		}
		return addUserPermJsonObj;
	}

	public JSONObject removeNyehmsPermForUser(HCSUserPermInfo hcsUserPermInfo, String appNameAbbrevation,
			AccessToken token) {
		StringBuilder buf = null; 
		final boolean isDebug = log.isDebugEnabled();
		if (isDebug) {buf = new StringBuilder().append(NL).append("token =").append(token.getAccess_token()).append(NL).append("userId =").append(hcsUserPermInfo.getEcommerceId());}
		JSONObject removeUserPermJsonObj =  new JSONObject();
		
		try {
			
			removeUserPermJsonObj = webClient
				    .post()
					.uri(uriBuilder -> uriBuilder.path(config.getHcsEndPointUri())
				    .queryParam("userid", hcsUserPermInfo.getEcommerceId())
					.queryParam("appid", appNameAbbrevation)
					.build())
					.accept(MediaType.APPLICATION_JSON)
					.headers(h -> h.setBearerAuth(token.getAccess_token()))
					.body(Mono.just(hcsUserPermInfo), HCSUserPermInfo.class)
					.retrieve()
					.bodyToMono(JSONObject.class)
					.block();
			
			if (isDebug) {buf.append(NL).append("Fetched User Info details=").append(removeUserPermJsonObj);}
			
		} catch(WebClientResponseException webEx) {
			log.error("error while removing user permission {}", webEx.getMessage());
		} catch (Exception ex) {
			//ex.printStackTrace();
			//throw new RuntimeException(ex);
			log.error("error while removing user permission {}", ex.getMessage());
		} finally {
			if (isDebug) {log.debug(buf.toString());}
		}
		return removeUserPermJsonObj;
	}
	
}
