package com.anilallewar.microservices.comments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
/**
 * The test runner which in turn leads to the contract tests being executed
 * 
 * @author aniallewar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, properties = { "spring.cloud.discovery.enabled=false",
		"spring.cloud.config.enabled=false", "logging.level.org.apache.http.wire=DEBUG" })
public class CommentsApplicationTests {

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		RestAssuredMockMvc.webAppContextSetup(context);
	}
	
	@Test
	public void contextLoads() {
	}
}
