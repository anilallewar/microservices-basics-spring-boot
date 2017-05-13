package com.anilallewar.microservices.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * Configuration that sets up the OAuth2 client operation for making calls to
 * the comments-webservice.
 * 
 * @author anilallewar
 *
 */
@Configuration
@EnableOAuth2Client
public class OAuthClientConfiguration {

	/**
	 * RestTempate that relays the OAuth2 token passed to the task webservice.
	 * 
	 * @param oauth2ClientContext
	 * @return
	 */
	@Bean
	public OAuth2RestTemplate restTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
		  return new OAuth2RestTemplate(resource, context);
	}
}
