package com.anilallewar.microservices.comments.contracts;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;

/**
 * Base class for the generated contract provider tests<br>
 * <br>
 * 
 * The name of the class if defined by 2 things
 * <ol>
 * <li>Sub-folder under the <b>contractsDir</b> while defaults to
 * <code>/src/test/resources/contracts</code></li>
 * <li><code>packageWithBaseClasses</code> defined in build.gradle</li>
 * <li>Since the package for base classes is defined as
 * <code>com.anilallewar.microservices.comments.contracts</code> and the
 * contract is defined under the <i>task/comments</i> folder under
 * <b>test/resources/contracts</b>, the base class name is TaskCommentsBase</li>
 * </ol>
 * 
 * @author anilallewar
 *
 */
@Ignore
public abstract class TaskCommentsBase {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Return the formatted date for validation
	 * 
	 * @param timeValue
	 * @return
	 */
	public String convertTimeValueToDate(Long timeValue) {
		return dateFormat.format(new Date(timeValue));
	}
}
