package com.anilallewar.microservices.task.oauth2.config;

import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;


@TestConfiguration
@Import({OAuth2AutoConfiguration.class})
public class OAuth2ClientTestConfiguration {
	// The OAuth2 Rest template is configured with the bean
}
