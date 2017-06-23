package com.anilallewar.microservices.comments.contracts;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.anilallewar.microservices.comments.CommentsApplication;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommentsApplication.class)
public class BaseContractVerifier {
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setUp() throws Exception{
		RestAssuredMockMvc.webAppContextSetup(context);
	}

}
