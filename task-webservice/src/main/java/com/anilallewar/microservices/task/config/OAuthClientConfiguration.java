package com.anilallewar.microservices.task.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

/**
 * Configuration that sets up the OAuth2 client operation for making calls to
 * the comments-webservice.<br>
 * <br>
 * 
 * @author anilallewar
 *
 */
@Configuration
public class OAuthClientConfiguration {

	/**
	 * RestTempate that relays the OAuth2 token passed to the task webservice.
	 * 
	 * @param oauth2ClientContext
	 * @return
	 */
	@Bean(name = "oAuth2RestTemplate")
	@LoadBalanced
	@Primary
	public OAuth2RestTemplate restTemplate(OAuth2ClientContext context) {
		return new OAuth2RestTemplate(authServer(), context);
	}

	private OAuth2ProtectedResourceDetails authServer() {
		ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails = new ResourceOwnerPasswordResourceDetails();
		// Need to set the access token URI since RestTemplate tries to access it first
		// time
		resourceOwnerPasswordResourceDetails.setAccessTokenUri("/userauth/oauth/token");
		return resourceOwnerPasswordResourceDetails;
	}
}
