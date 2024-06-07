package com.mb.common.constant;

/**
 * A constant class for storing all exception's or error's messages. Use
 * exception.properties file to store and get message using key
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
public class ExceptionMessage {

	private ExceptionMessage() {
	}

	// General
	public static final String INTERNAL_SERVER_ERROR = "internal.server.error";

	// Mapper
	public static final String SOURCE_OR_DESTINATION_IS_NULL = "mapper.source.destination.is.null";

	// Validation
	public static final String VALIDATION_ERROR = "validation.error";
	public static final String INVALID_REQUEST_PARAMS = "invalid.request.params";

	// User
	public static final String USER_NOT_FOUND_BY_UUID = "user.not.found.by.uuid";
	public static final String USER_NOT_FOUND_BY_USERNAME = "user.not.found.by.username";


	// JWT TOKEN
	public static final String TOKEN_INVALID = "token.invalid";
	public static final String ACCESS_TOKEN_EXPIRED = "access.token.expired";
	public static final String SIGNATURE_INVALID = "token.signature.invalid";
	public static final String REFRESH_TOKEN_EXPIRED = "refresh.token.expired";

	// AUTH
	public static final String BAD_CREDENTIALS = "bad.credentials";

	// Role
	public static final String ROLE_NOT_FOUND = "role.not.found";
	
	//project
	public static final String PROJECT_NOT_FOUND="project.not.found";

}
