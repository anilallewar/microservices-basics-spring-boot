package com.anilallewar.microservices.comments.apis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anilallewar.microservices.comments.dtos.CommentDTO;

/**
 * REST endpoint for the comments functionality<br>
 * <br>
 * 
 * Note that this endpoint is supposed to be consumed by the Task webservice and
 * is not accessible to the general public; i.e. the api-gateway doesn't handle
 * requests for comments-webservice.
 * 
 * @author anilallewar
 *
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

	private static final Logger LOGGER = Logger.getLogger(CommentsController.class.getName());

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	private List<CommentDTO> comments = null;

	/**
	 * Public constructor to initialize the comments and handle the
	 * ParseException
	 * 
	 * @throws ParseException
	 */
	public CommentsController() throws ParseException {
		this.comments = Arrays.asList(new CommentDTO("task11", "comment on task11", formatter.parse("2015-04-23")),
				new CommentDTO("task12", "comment on task12", formatter.parse("2015-05-12")),
				new CommentDTO("task11", "new comment on task11", formatter.parse("2015-04-27")),
				new CommentDTO("task21", "comment on task21", formatter.parse("2015-01-15")),
				new CommentDTO("task22", "comment on task22", formatter.parse("2015-03-05")));
	}

	/**
	 * Get comments for specific taskid that is passed in the path.
	 * 
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/{taskId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<CommentDTO> getCommentsByTaskId(@PathVariable("taskId") String taskId) {
		List<CommentDTO> commentListToReturn = new ArrayList<>();
		for (CommentDTO currentComment : comments) {
			if (currentComment.getTaskId().equalsIgnoreCase(taskId)) {
				if (LOGGER.isLoggable(Level.INFO)) {
					LOGGER.info(String.format("Found matching comments for task [%s] with comment [%s]", taskId,
							currentComment.getComment()));
				}
				commentListToReturn.add(currentComment);
			}
		}

		return commentListToReturn;
	}
}
