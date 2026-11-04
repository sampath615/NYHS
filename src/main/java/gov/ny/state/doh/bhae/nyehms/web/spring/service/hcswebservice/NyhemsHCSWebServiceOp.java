package gov.ny.state.doh.bhae.nyehms.web.spring.service.hcswebservice;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.HCSUserInfo;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.HCSUserPermInfo;
import gov.ny.state.doh.bhae.nyehms.webservice.oauth.AccessToken;
import gov.ny.state.doh.bhae.nyehms.webservice.oauth.AccessTokenClient;
import gov.ny.state.doh.bhae.nyehms.webservice.oauth.WebServiceConstants;

@Service
public class NyhemsHCSWebServiceOp {

	private AccessTokenClient client;
	private AccessToken token;

	public HCSUserInfo getHCSUserInfo(String hcsUserId, WebServiceConstants wsConst) {

		HCSUserInfo hcsUserInfo = null;

		client = new AccessTokenClient(wsConst);
		token = client.getAccessToken();
		hcsUserInfo = client.getHCSUserInfo(hcsUserId, token);

		return hcsUserInfo;
	}

	public HCSUserPermInfo getHCSUserPermInfo(String hcsUserId, WebServiceConstants wsConstants) {

		HCSUserPermInfo userPermInfo = null;

		client = new AccessTokenClient(wsConstants);
		token = client.getAccessToken();
		userPermInfo = client.getHCSUserPermInfo(hcsUserId, wsConstants.getAppId(), token);

		return userPermInfo;
	}

	public JSONObject addNyehmsPermForUser(HCSUserPermInfo userPermInfo, String appNameAbbrevation,
			WebServiceConstants wsConstants) {

		client = new AccessTokenClient(wsConstants);
		token = client.getAccessToken();
		return client.addNyehmsPermForUser(userPermInfo, appNameAbbrevation, token);
	}

	public JSONObject removeNyehmsPermForUser(HCSUserPermInfo hcsUserPermInfo, String appNameAbbrevation,
			WebServiceConstants wsConstants) {
		client = new AccessTokenClient(wsConstants);
		token = client.getAccessToken();
		return client.removeNyehmsPermForUser(hcsUserPermInfo, appNameAbbrevation, token);
	}

}
