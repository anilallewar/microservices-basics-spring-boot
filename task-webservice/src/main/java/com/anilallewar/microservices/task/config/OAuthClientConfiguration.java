package com.anilallewar.microservices.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.security.oauth2.client.ResourceServerTokenRelayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration that sets up the OAuth2 client operation for making calls to
 * the comments-webservice.<br>
 * <br>
 * 
 * We add a {@link HandlerInterceptor} to add the JWT token relayed by the Zuul
 * gateway into the OAuth2RestTemplate. This needs to be added manually to the
 * WebMvc intercepter chain because of an existing bug in Spring boot 1.5.3.
 * 
 * @author anilallewar
 *
 */
@Configuration
@ImportAutoConfiguration(classes = { ResourceServerTokenRelayAutoConfiguration.class })
public class OAuthClientConfiguration extends WebMvcConfigurerAdapter {

	/**
	 * Issues with JWT token not getting relayed by the resource server
	 * {@linkplain https://stackoverflow.com/questions/43566515/spring-security-oauth2-jwt-token-relay-issue}
	 */
	@Autowired
	@Qualifier("tokenRelayRequestInterceptor")
	HandlerInterceptor tokenRelayHandlerInterceptor;

	/**
	 * RestTempate that relays the OAuth2 token passed to the task webservice.
	 * 
	 * @param oauth2ClientContext
	 * @return
	 */
	@Bean
	@LoadBalanced
	public OAuth2RestTemplate restTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
		return new OAuth2RestTemplate(resource, context);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.tokenRelayHandlerInterceptor);
	}

}
