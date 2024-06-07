package com.mb.common.constant;

/**
 * A constant class for storing all http response messages. Use
 * message.properties file to store and get message using key
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
public class ResponseMessage {

	private ResponseMessage() {
	}

	// General
	public static final String SUCCESS = "success.message";

	// USER
	public static final String USER_CREATED = "user.create.success";
	public static final String USER_UPDATED = "user.update.success";

}
