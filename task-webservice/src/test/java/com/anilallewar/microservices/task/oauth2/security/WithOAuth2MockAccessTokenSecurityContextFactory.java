package com.anilallewar.microservices.task.oauth2.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * Security context factory that will help us in mocking up the OAuth2 workflow
 * so that we can embed an mock access token in the security context.<br>
 * <br>
 * 
 * @author anilallewar
 *
 */
public class WithOAuth2MockAccessTokenSecurityContextFactory
		implements WithSecurityContextFactory<WithMockOAuth2Token> {

	// Default OAuth2 access token
	private static final String TEST_ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0OTgwMDA0MDEsInVzZXJfbmFtZSI6ImFuaWwiLCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImp0aSI6IjkzY2Y3Y2U0LWY2ZDgtNGJkNi04NGE5LWQ4NDViYmEwZGY2ZCIsImNsaWVudF9pZCI6ImFjbWUiLCJzY29wZSI6WyJvcGVuaWQiXX0.NdAHIna-8GCRGmvaDqO5iOGy3gkjaVaAZqXkDvpjKiQqSVrM_1j1xvPBwuy2iWjyD_crkw_zehE_jKfLpcLxw2JLJZPOtLaUeCYs6euUvboOAVKpmg62mi81PgV3tpEBaqSi5MmATtVcerXFf64LgS1ZPnsw8WIogGlqkhziSzOR4yH2tAzY0aIheL7AWxAgxfBe6I7Lej9ld1Fx6xHodIz8TzmSD-wlZ18e40WBpd_0wc7M8VE_uK18f39btM2VS02FxVm9fdxxwwDXzDT-ODJOs0L_NoKHmZx-JF72bjNUmac81ZcH4fU-fVdme5b-oJowFPgfhZZZ4_nKFv71Ww";

	@Autowired
	MockHttpSession session;

	@Override
	public SecurityContext createSecurityContext(WithMockOAuth2Token withMockOAuth2Token) {
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = this.getOauthTestAuthentication(withMockOAuth2Token);
		context.setAuthentication(authentication);

		this.session.setAttribute("scopedTarget.oauth2ClientContext", this.getOauth2ClientContext());
		return context;
	}

	/**
	 * Create the authentication object that we need to setup in context
	 * 
	 * @param withMockOAuth2Token
	 * @return
	 */
	private Authentication getOauthTestAuthentication(WithMockOAuth2Token withMockOAuth2Token) {
		return new OAuth2Authentication(getOauth2Request(withMockOAuth2Token), getAuthentication(withMockOAuth2Token));
	}

	/**
	 * Mock OAuth2Request
	 * 
	 * @param withMockOAuth2Token
	 * @return
	 */
	private OAuth2Request getOauth2Request(WithMockOAuth2Token withMockOAuth2Token) {
		String clientId = withMockOAuth2Token.clientId();
		Map<String, String> requestParameters = Collections.emptyMap();
		boolean approved = true;
		String redirectUrl = withMockOAuth2Token.redirectUrl();
		Set<String> responseTypes = Collections.emptySet();
		Set<String> scopes = new HashSet<>(Arrays.asList(withMockOAuth2Token.scopes()));
		Set<String> resourceIds = Collections.emptySet();
		Map<String, Serializable> extensionProperties = Collections.emptyMap();
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(withMockOAuth2Token.authorities());

		OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId, authorities, approved, scopes,
				resourceIds, redirectUrl, responseTypes, extensionProperties);

		return oAuth2Request;
	}

	/**
	 * Provide the mock user information to be used
	 * 
	 * @param withMockOAuth2Token
	 * @return
	 */
	private Authentication getAuthentication(WithMockOAuth2Token withMockOAuth2Token) {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(withMockOAuth2Token.authorities());

		User userPrincipal = new User(withMockOAuth2Token.userName(), withMockOAuth2Token.password(), true, true, true,
				true, authorities);

		HashMap<String, String> details = new HashMap<String, String>();
		details.put("user_name", withMockOAuth2Token.userName());
		details.put("email", "anilallewar@yahoo.co.in");
		details.put("name", "Anil Allewar");

		TestingAuthenticationToken token = new TestingAuthenticationToken(userPrincipal, null, authorities);
		token.setAuthenticated(true);
		token.setDetails(details);

		return token;
	}

	/**
	 * Create the mock {@link OAuth2ClientContext} object that will be injected
	 * into the session associated with the request.<br>
	 * <br>
	 * 
	 * Without this object in the session, Spring security will attempt to make
	 * a request to obtain the token if the controller object attempts to use it
	 * (as in our case)
	 * 
	 * @return
	 */
	private OAuth2ClientContext getOauth2ClientContext() {
		OAuth2ClientContext mockClient = mock(OAuth2ClientContext.class);
		when(mockClient.getAccessToken()).thenReturn(new DefaultOAuth2AccessToken(TEST_ACCESS_TOKEN));

		return mockClient;
	}
}
