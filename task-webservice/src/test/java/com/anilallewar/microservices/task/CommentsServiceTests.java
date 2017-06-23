package com.anilallewar.microservices.task;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.anilallewar.microservices.task.apis.CommentsService;
import com.anilallewar.microservices.task.model.CommentCollectionResource;
import com.anilallewar.microservices.task.oauth2.config.OAuth2ClientTestConfiguration;
import com.anilallewar.microservices.task.oauth2.security.WithMockOAuth2Token;

/**
 * @author synerzip
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, properties = { "spring.cloud.discovery.enabled=false",
		"spring.cloud.config.enabled=false", "eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/" })
@Import(OAuth2ClientTestConfiguration.class)
public class CommentsServiceTests {

	@Autowired
	private CommentsService commentsService;

	private static final String TEST_TASK_ID = "task11";

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
		CommentCollectionResource comments = this.commentsService.getCommentsForTask(TEST_TASK_ID);

		Assert.assertEquals(2, comments.getTaskComments().size());
		Assert.assertTrue(comments.getTaskComments().get(0).getTaskId().equals(TEST_TASK_ID));
	}

}
