package com.anilallewar.microservices.task;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.anilallewar.microservices.task.apis.CommentsService;
import com.anilallewar.microservices.task.model.CommentCollectionResource;
import com.anilallewar.microservices.task.oauth2.config.OAuth2ClientTestConfiguration;
import com.anilallewar.microservices.task.oauth2.security.WithMockOAuth2Token;

/**
 * @author anilallewar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TaskApplication.class }, webEnvironment = WebEnvironment.MOCK, properties = {
		"spring.cloud.discovery.enabled=false", "spring.cloud.config.enabled=false",
		"stubrunner.idsToServiceIds.basic-comments-webservice-stubs=comments-webservice",
		"spring.zipkin.enabled=false" })
@Import(OAuth2ClientTestConfiguration.class)
@AutoConfigureStubRunner(ids = { "anilallewar:basic-comments-webservice-stubs:+:stubs:9083" }, workOffline = true)
@DirtiesContext
public class CommentsServiceTests {

	@Autowired
	private CommentsService commentsService;
	
	private static final String TEST_TASK_ID = "task11";
	private static final String REQUEST_TASK_ID = "task12";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	@WithMockOAuth2Token(userName = "dave")
	public void testGetCommentsForTask() {
		CommentCollectionResource comments = this.commentsService.getCommentsForTask(REQUEST_TASK_ID);

		Assert.assertEquals(2, comments.getTaskComments().size());
		Assert.assertTrue(comments.getTaskComments().get(0).getTaskId().equals(TEST_TASK_ID));
	}

}
